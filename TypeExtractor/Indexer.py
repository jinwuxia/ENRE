import builtins
from ast import NodeVisitor, Assign, Name, arg, FunctionDef, arguments, Expr, Attribute, ClassDef
from typing import Any

import ast, asttokens


def nestedlist2location(nested):
    res = ""
    for name in nested:
        res += name + "::"
    res = res[:-2]
    return res


class var_loc:
    def __init__(self,reveal_line, reveal_offset ,nested_loc, start_line, end_line, start_col, end_col):
        self.reveal_line = reveal_line
        self.reveal_offset = reveal_offset
        self.nested_loc = nested_loc
        self.start_line = start_line
        self.end_line = end_line
        self.start_col = start_col
        self.end_col = end_col


class CoordinateGenVisitor(NodeVisitor):
    tokens = None
    var_locs = None

    def __init__(self, filepath):
        self.filePath = filepath
        self.nested_loc = []

    def visit_ClassDef(self, node: ClassDef) -> Any:
        self.nested_loc.append(node.name)
        self.generic_visit(node)
        self.nested_loc.pop()

    def visit_FunctionDef(self, node: FunctionDef) -> Any:
        self.nested_loc.append(node.name)
        instred_line = node.body[0].first_token.start[0] - 1
        instred_indent = node.col_offset + 4
        nested_location = nestedlist2location(self.nested_loc)

        def loc_of_arg(an_arg: ast.arg):
            return (an_arg.arg, nested_location, instred_line, instred_indent, an_arg.lineno, an_arg.end_lineno,
                    an_arg.col_offset + 1,
                    an_arg.col_offset + len(an_arg.arg) + 1)

        self.var_locs.append((("self." if
                               len(node.args.args) != 0 and node.args.args[0].arg == "self"
                               else "") + node.name,
                              nested_location,
                              instred_line,
                              instred_indent,
                              node.lineno,
                              node.lineno
                              if len(node.args.args) == 0
                              else node.args.args[-1].end_lineno,
                              node.col_offset + 4 + 1,
                              node.col_offset + 4 + 1 + len(node.name)))
        for an_arg in node.args.args:
            self.var_locs.append(loc_of_arg(an_arg))

        for an_arg in node.args.kwonlyargs:
            self.var_locs.append(loc_of_arg(an_arg))

        for an_arg in node.args.posonlyargs:
            self.var_locs.append(loc_of_arg(an_arg))

        if node.args.kwarg is not None:
            self.var_locs.append(loc_of_arg(node.args.kwarg))

        if node.args.vararg is not None:
            self.var_locs.append(loc_of_arg(node.args.vararg))

        # if node.name == "__init__":
        #    for stmt in node.body:
        #        if isinstance(stmt, Assign):
        #            self.visit(stmt)
        # else:
        self.generic_visit(node)
        self.nested_loc.pop()

    def visit_Assign(self, node: Assign) -> Any:
        indentation = node.col_offset
        nested_location = nestedlist2location(self.nested_loc)
        for target in node.targets:
            print(self.nested_loc)
            expression = self.tokens.get_text(target)
            self.var_locs.append((expression, nested_location, node.end_lineno, node.col_offset,
                                  target.lineno, target.end_lineno, target.col_offset + 1, target.end_col_offset + 1))


class LeafVisitor(NodeVisitor):

    def visit_Name(self, node: Name) -> Any:
        # print(node.id + ": " + "%d, %d, %d, %d" % (node.lineno, node.end_lineno, node.col_offset,
        # node.end_col_offset))
        return node.id, node.lineno, node.end_lineno, node.col_offset, node.end_col_offset

    def visit_Attribute(self, node: Attribute) -> Any:
        lhs = self.visit(node.value)
        attr = node.attr
        return lhs[0] + '.' + attr, lhs[1], lhs[2], lhs[3], lhs[4] + len(attr) + 1


def collet_locs(path, file):
    CoordinateGenVisitor.tokens = asttokens.ASTTokens(file.read(), parse=True)
    CoordinateGenVisitor.var_locs = []
    tree = CoordinateGenVisitor.tokens.tree
    CoordinateGenVisitor(path).visit(tree)
    return CoordinateGenVisitor.var_locs
