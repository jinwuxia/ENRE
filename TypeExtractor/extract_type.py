import os
from pathlib import Path
from typing import List, Any, Tuple, Optional

from Checker import Checker
from util import run_mypy, get_revealed_type, exist_in_error, syntax_error_check


class Networkerror(RuntimeError):
    def __init__(self, problem_file: Path):
        self.args = problem_file


def get_type_info(checker: Checker, output: str, need_reveal: List[Tuple[str, int, int, int, int, int]],
                  relative_file_path: Path, indexed_file_name: Path):
    lines = output.splitlines()
    syntax_error_check(lines[0], need_reveal)
    type_infos_list = []
    for elem in need_reveal:
        for line in lines:
            from instrument import tempFileName
            t = get_revealed_type(line, str(indexed_file_name), elem[1], checker)
            if t is not None:
                t = t.replace(tempFileName[:-3], relative_file_path.name[:-3])
                typeset = [t]
                incompatible = exist_in_error(relative_file_path, lines, elem[1] - 1)
                if incompatible is not None:
                    typeset.append(incompatible)
                fileset = get_fileset_form_type(Path(relative_file_path), typeset)
                type_infos_list.append(
                    (relative_file_path, elem[0], elem[2], elem[3], elem[4], elem[5], typeset, fileset))
    return type_infos_list


def compose(output, temp_res):
    if len(output) == 0:
        return temp_res
    else:
        i = 0
        j = 0
        res = []
        while i < len(output) and j < len(temp_res):
            if output[i][2] == temp_res[j][2]:
                res.append(output[i])
                res[-1] = res[-1] + temp_res[j][6:]
                j += 1
                i += 1
            elif output[i][2] < temp_res[j][2]:
                i += 1
            else:
                j += 1
        return res


def extract(relative_file_path: Path,
            indexed_file_path: Path,
            need_reveal: List[Any],
            checkers: List[Checker]) -> List[Any]:
    output = []
    for checker in checkers:
        checker.check(indexed_file_path)
        temp_res = get_type_info(checker, checker.buffer, need_reveal, relative_file_path, indexed_file_path)
        output = compose(output, temp_res)

    # from Checker import pyright_gen, mypy_gen
    # checker = mypy_gen()
    # checker.check(indexed_file_path)
    # output = checker.buffer
    # type_info = get_type_info(checker, output, need_reveal, relative_file_path, indexed_file_path)
    os.remove(indexed_file_path)
    return output


def get_fileset_form_type(rel_file_path: Path, typeset: List[str]) -> List[str]:
    project_name = rel_file_path.parts[0]
    import re
    file_set = []
    for type_str in typeset:
        i = 0
        classSet = re.findall(project_name + "((\.[_a-zA-Z]*)*)(\.[_a-zA-Z]*)", type_str)
        for matched in classSet:
            class_path = Path(project_name + matched[0].replace(".", "\\"))
            if class_path.is_dir():
                file_set.append(str(class_path) + "\\__init__.py")
            else:
                file_set.append(str(class_path) + ".py")
    return file_set
