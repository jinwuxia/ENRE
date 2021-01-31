import os
import subprocess
import sys
from pathlib import Path

from main import tempFileName


def main():
    args = sys.argv[1:]
    src_path = Path(args[0])
    if src_path.is_file():
        subprocess.call("black " + str(src_path))
    elif src_path.is_dir():
        reformatDir(src_path)


def reformatDir(dir: Path):
    with os.scandir(dir) as entries:
        for entry in entries:
            if entry.is_dir():
                reformatDir(Path(entry.path))
            elif entry.name.endswith(".py") and entry.name != tempFileName:
                subprocess.call("black " + entry.path)


if __name__ == '__main__':
    exit(main())
