import builtins
from io import TextIOWrapper
from pathlib import Path
from typing import List, Any


class CsvWriter(object):
    def __init__(self, location: Path, head: str):
        self.loc = location
        self.head = head
        location.mkdir(exist_ok=True)
        self.file: TextIOWrapper = None

    def write(self, content: str):
        self.file.write(content)

    def writeLine(self, content: str):
        self.write(content)
        self.write("\n")
        self.file.flush()

    def writeLines(self, list: List[str]):
        self.file.writelines(list)
        self.file.flush()

    def writeTuples(self, list: List[Any]):
        for a_tuple in list:
            content = ""
            if len(a_tuple[-2]) != 0:
                for elem in a_tuple:
                    if isinstance(elem, builtins.list):
                        cell = ""
                        for v in elem:
                            cell += str(v) + ";"
                        content += "\"" + cell[:-1] + "\","
                    else:
                        content += "\"" + str(elem) + "\"" + ","
            if content != "":
                self.writeLine(content[:-1].replace("\n", ""))

    def newFile(self, file_name: str):
        if self.file is not None:
            self.file.close()
        self.file = open(self.loc.joinpath(file_name + ".csv"), "w", encoding="utf-8")
        self.file.write(self.head)

    def exist(self, file_name: str) -> bool:
        file_path = self.loc.joinpath(file_name + ".csv")
        return file_path.is_file()

    def __del__(self):
        if self.file is not None:
            self.file.close()
