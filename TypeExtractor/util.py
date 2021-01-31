import os
import re
import subprocess
from typing import List, Optional

from Checker import Checker


def run_mypy(mypy_and_args: List[str], tmp_name: str) -> str:
    proc = subprocess.run(mypy_and_args + [tmp_name], stdout=subprocess.PIPE)
    assert (isinstance(proc.stdout, bytes))  # Guaranteed to be true because we called run with universal_newlines=False
    return proc.stdout.decode(encoding="utf-8")


# D:\Program Analysis\projects-with-type-annotation\mypy\mypy\build.py:71: note: Revealed type is 'Literal[False]?'
def get_revealed_type(line: str, relevant_file: str, relevant_line: int, checker: Checker) -> Optional[str]:
    format, file_group, lineno_group, type_group = checker.formatter
    m = re.match(format, line)
    if file_group != -1:
        if (m and
                int(m.group(lineno_group)) == relevant_line and
                os.path.samefile(relevant_file, m.group(file_group))):
            return m.group(type_group)
        else:
            return None
    else:
        if (m and
                int(m.group(lineno_group)) == relevant_line):
            return m.group(type_group)
        else:
            return None


def exist_in_error(file_path, lines: List[str], lineno: int):  # lines is splited mypy output
    for line in lines:
        # django\views\generic\dates.py:711: error: Incompatible types in assignment (expression has type "date", variable has type "datetime")
        m = re.match(
            "(.+?):(\d+): error: Incompatible types in assignment \(expression has type \"(.*)\", variable has type \"(.*)\"\)$",
            line);
        n = re.match(
            "(.+?):(\d+): error: Incompatible types in assignment \(expression has type \"(.*)\", base class \"(.*)\" defined the type as \"(.*)\"\)$",
            line)
        if m is not None and m.group(1) == file_path and lineno == int(m.group(2)):
            return m.group(3)
        elif n is not None and n.group(1) == file_path and lineno == int(n.group(2)):
            return n.group(3)


def syntax_error_check(line, need_reveal):
    m = re.match(r"(.+?):(\d+): error: invalid syntax$", line)
    n = re.match(r"(.+?):(\d+): error: unexpected indent$", line)
    if m:
        for elem in need_reveal:
            if str(elem[1]) == m.group(2):
                raise Exception("Syntax error at " + str(elem[2]) + " of the source file!")
        raise Exception("Syntax error at " + str(m.group(2)) + " of tempFile.py in directory!")
    if n:
        for elem in need_reveal:
            if str(elem[1]) == n.group(2):
                raise Exception("Unexpected indentation at " + str(elem[2]) + " of the source file!")
        raise Exception("Unexpected indentation at " + n.group(2) + ", to check the tempFile.py in directory!", )
