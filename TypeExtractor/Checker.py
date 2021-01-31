import subprocess
from pathlib import Path
from typing import List, Optional


class Checker:
    def __init__(self, command: str, options: List[str], fomat, name):
        self.command = command
        self.options = options
        self.formatter = fomat
        self.buffer: Optional[str] = None
        self.name = name

    def check(self, filepath: Path):
        proc = subprocess.run([self.command] + self.options + [str(filepath)], shell=True, stdout=subprocess.PIPE)
        self.buffer = proc.stdout.decode(encoding="utf-8")


def mypy_gen(options=None):
    if options is None:
        options = ["--check-untyped-def"]
    # format, file group, lineno group, type group
    mypy_format = r"(.+?):(\d+): note: Revealed type is '(.*)'$", 1, 2, 3
    return Checker("mypy", options, mypy_format, "mypy")


def pyright_gen(options=None):
    if options is None:
        options = ["--verbose"]
    pyright_format = "  (\d+):(\d+) - info: Type of \"(.+)\" is \"(.+)\"$", -1, 1, 4
    return Checker("pyright", options, pyright_format, "pyright")
