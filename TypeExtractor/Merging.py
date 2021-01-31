import ast
import builtins
import os
import pathlib
import subprocess
import sys
import autopep8 as ap
from os.path import basename
from typing import Any
from ast import iter_fields
from ast import AST, ClassDef, FunctionDef
from shutil import copyfile

import asttokens
from pathlib import Path
from distutils.dir_util import copy_tree


class Filter:
    def __init__(self):
        self.locs = []

    def add_to_locs(self, start_pos, length, replace):
        self.locs.append((start_pos, length, replace))

    def dbg_print(self):
        for loc in self.locs:
            print(loc)




def get_signature(funcNode: ast.FunctionDef, tokens):
    print("---------------")
    func = tokens.get_text(funcNode)
    body = tokens.get_text(funcNode.body)
    signature = func[:0 - len(body)]
    # print(tokens.get_text_range(funcNode))
    # if not hasattr(funcNode, 'first_token'):
    signature = tokens._text[funcNode.args.first_token.startpos: funcNode.body[0].first_token.startpos]
    while signature[-1] == ' ' or signature[-1] == '\n':
        signature = signature[:-1]
    print(signature)
    # print(func)
    print("---------------")

    return funcNode.args.first_token.startpos, signature


class MergeVisitor(ast.NodeVisitor):
    def __init__(self, peer, peer_tokens, self_tokens):
        self.peers = [peer]
        self.my_tokens = self_tokens
        self.tokens_of_peer = peer_tokens
        self.filter = Filter()

    def find_same_in_peer(self, peer_fields, item, visited):
        res = None
        if self.peers[-1] is None:
            return None
        if peer_fields is not None and (isinstance(item, ClassDef) or isinstance(item, FunctionDef)):
            for field, value in peer_fields:
                if isinstance(value, builtins.list):
                    for peer_item in value:
                        if isinstance(peer_item, AST):
                            if isinstance(peer_item, ast.ClassDef) and peer_item.name == item.name:
                                print(peer_item.name)
                                return peer_item
                            elif isinstance(peer_item, ast.FunctionDef) and peer_item.name == item.name:
                                print(peer_item.name)
                                print(self.tokens_of_peer.get_text(peer_item.args))
                                print("***********")
                                stub_signature = get_signature(item, self.my_tokens)
                                source_signature = get_signature(peer_item, self.tokens_of_peer)
                                self.filter.add_to_locs(source_signature[0], len(source_signature[1]),
                                                        stub_signature[1])
                                print(self.my_tokens.get_text(item.args))
                                # print("functype: " + str(peer_item.type_comment))
                                return peer_item
                elif isinstance(value, AST):
                    if isinstance(value, ClassDef) and value.name == item.name:
                        print(self.tokens_of_peer.get_text(value))
                        return value
                    elif isinstance(value, FunctionDef) and value.name == item.name:
                        print(self.tokens_of_peer.get_text(value))
                        print(peer_item.type_comment)
                        stub_signature = get_signature((item, self.my_tokens))
                        source_signature = get_signature(value, self.tokens_of_peer)
                        self.filter.add_to_locs(source_signature[0], len(source_signature[1]),
                                                stub_signature[1])
                        return value
        return None

    def generic_visit(self, node: AST) -> Any:
        visited = set()
        for field, value in iter_fields(node):
            if isinstance(value, list):
                for item in value:
                    if isinstance(item, AST):
                        node_of_peer = self.find_same_in_peer(iter_fields(self.peers[-1]), item, visited)
                        self.peers.append(node_of_peer)
                        visited.add(node_of_peer)
                        self.visit(item)
                        self.peers.pop()
            elif isinstance(value, AST):
                node_of_peer = self.find_same_in_peer(iter_fields(self.peers[-1]), value, visited)
                self.peers.append(node_of_peer)
                visited.add(node_of_peer)
                self.visit(value)
                self.peers.pop()


def merge(stub_file: Path, source: Path, merge_dir: Path):
    if not os.path.isdir(merge_dir):
        merge_dir.mkdir(exist_ok=True)
    file1 = open(stub_file, "r", encoding="utf-8")
    file2 = open(source, "r", encoding="utf-8")
    tokens1 = asttokens.ASTTokens(file1.read(), parse=True)
    tokens2 = asttokens.ASTTokens(file2.read(), parse=True)
    merger = MergeVisitor(tokens2.tree, tokens2, tokens1)
    merger.visit(tokens1.tree)
    merger.filter.locs.sort(key=lambda loc: loc[0])
    merger.filter.dbg_print()
    res = open(os.path.join(merge_dir, basename(source.name)), "w", encoding="utf-8")

    for field, value in iter_fields(tokens1.tree):
        if isinstance(value, list):
            for item in value:
                if isinstance(item, AST):
                    if not isinstance(item, ClassDef) and not isinstance(item, FunctionDef):
                        res.write(tokens1.get_text(item) + "\n")
        elif isinstance(value, AST):
            if not isinstance((item, ClassDef)) and not isinstance(item, FunctionDef):
                res.write(tokens1.get_text(item) + "\n")

    # res = open(".\\github\\github.py", "w")
    p1 = p2 = 0
    file2.seek(0)
    file2_content = file2.read()

    while p1 < len(file2_content):
        if p2 < len(merger.filter.locs) and p1 == merger.filter.locs[p2][0]:
            # print(merger.filter.locs[p2][2])

            print("->" + file2_content[p1:p1 + merger.filter.locs[p2][1]])
            res.write(merger.filter.locs[p2][2])
            p1 += merger.filter.locs[p2][1]
            p2 += 1
        else:
            res.write(file2_content[p1])
            p1 += 1

    file1.close()
    file2.close()
    res.close()


def find(name, path: Path) -> Path:
    for entry in os.scandir(path):
        if entry.name == name:
            if basename(entry) == "django":
                print("pause here")
            return Path(os.path.join(path, name))
    return None


def merge_to(stub_file: Path, source: Path, merge_dir: Path):
    proc = subprocess.run("merge-pyi \"" + str(source) + "\" \"" + str(stub_file) + "\"", stdout=subprocess.PIPE)
    res: str = proc.stdout.decode(encoding="ISO-8859-1")
    if res == "":
        # merge(stub_file, source, merge_dir)
        copyfile(source, merge_dir.joinpath(source.name))
        return
    result = open(os.path.join(merge_dir, basename(source.name)), "w", encoding="utf-8")
    result.write(res.replace("\r", ""))
    subprocess.call("black " + os.path.join(merge_dir, basename(source.name)))
    print("merge succeed: " + str(source) + " and " + str(stub_file))


def merge_dir(source_dir_path: Path, stub_dir_path: Path, merged_dir_path: Path):
    with os.scandir(source_dir_path) as entries0:
        with os.scandir(stub_dir_path) as entries1:
            for entry in entries0:
                if entry.is_dir():
                    stub_dir_path1 = find(entry.name, stub_dir_path)
                    Path(os.path.join(merged_dir_path, basename(entry))).mkdir(exist_ok=True)
                    if stub_dir_path1 is None:
                        copy_tree(entry.path, os.path.join(merged_dir_path, basename(entry)))
                        continue
                    merge_dir(entry, stub_dir_path1, os.path.join(Path(merged_dir_path), entry.name))
                elif entry.name.endswith(".py"):
                    stub_file = find(entry.name[:-3] + ".pyi", stub_dir_path)
                    if stub_file is not None:
                        merge_to(stub_file, Path(entry.path), Path(merged_dir_path))
                    else:
                        copyfile(entry, os.path.join(merged_dir_path, basename(entry)))
                else:
                    copyfile(entry, os.path.join(merged_dir_path, basename(entry)))
