import os
import sys
from pathlib import Path
from typing import List, Any, Optional, Tuple

from Indexer import collet_locs
from Merging import merge_dir
from CsvWriter import CsvWriter
from extract_type import extract
from instrument import instrument, tempFileName

problemFile = None

csvHead0 = "file path,expression,nested scope,start line,end line,start col,end col,start line(unmerged)," \
           "end line(unmerged),start col(unmerged),end col(unmerged)"
csvHead1 = "file path,expression,nested scope,start line,end line,start col,end col,type set,file set\n"
csvHead2 = "file path,expression,nested scope,start line,end line,start col,end col,start line(unmerged)," \
           "end line(unmerged),start col(unmerged),end col(unmerged),type set,file set\n"

proj_root = None


def process_file(file_path: Path, checkers):
    print("checking " + str(file_path))
    with open(str(file_path), encoding='utf-8') as file:
        locations = collet_locs(str(file_path), file)
        temp_file_path, need_reveal = instrument(locations, file_path)
        global proj_root
        relative_file_path: Path = file_path.relative_to(proj_root.parent)
        # relative_file_path = relative_file_path.parent
        return extract(relative_file_path, temp_file_path, need_reveal, checkers)


#
def visitDir(dir: Path, writer: CsvWriter):
    with os.scandir(dir) as entries:
        for entry in entries:
            if entry.is_dir():
                print(entry.name)
                visitDir(dir.joinpath(entry.name), writer)
            elif entry.name.endswith(".py") and entry.name != tempFileName:
                type_infos = process_file(Path(entry.path))
                writer.newFile("type_info_of-" + entry.path[:-3].replace("\\", "-"))
                writer.writeTuples(type_infos)


def add_locs_into(unmerged_locs, type_info):
    res = []

    def find(head: int, id: str, l: List[Any]) -> Optional[Tuple]:
        for i in range(head, len(l)):
            if l[i][0] == id:
                return head, l[i]

    if unmerged_locs is not None:
        j = 0
        for elem in type_info:
            found = find(j, elem[1], unmerged_locs)
            if found is not None:
                j = found[0] + 1
                peer_loc = found[1]
                res.append((elem[0], elem[1], peer_loc[1], elem[2], elem[3], elem[4], elem[5], peer_loc[4], peer_loc[5],
                            peer_loc[6],
                            peer_loc[7]) + elem[6:])
            else:
                pass
    else:
        res = type_info

    return res


def visitDir_with_unmerged(source_dir: Path, unmerge_dir: Path, writer: CsvWriter, checkers):
    with os.scandir(source_dir) as entries:
        for entry in entries:
            from Merging import find
            peer = find(entry.name, unmerge_dir)
            if entry.is_dir():
                print(entry.path)
                if peer is None:
                    pass
                else:
                    visitDir_with_unmerged(Path(entry.path), Path(peer), writer, checkers)
            elif entry.name.endswith(".py") and entry.name != tempFileName:
                try:
                    with open(peer, encoding="utf-8") as file2:
                        file_name = str(entry.path).replace("\\", "-").replace(":", "").replace(" ", "")[:-3]
                        result_file_name = "type_info_of-" + file_name
                        if writer.exist(result_file_name):
                            continue
                        type_info = process_file(Path(entry.path), checkers)
                        unmerged_locs = collet_locs(peer, file2)
                        result = add_locs_into(unmerged_locs, type_info)
                        writer.newFile(result_file_name)
                        writer.writeTuples(result)
                except Exception as error:
                    print(error.args)
                    print("**************")
                    print("Please modify the file " + entry.path + ". " + str(error.args[0]))
                    print("**************")
                    # os.system("pause")
                    problemFile.write(entry.path + "\n")
                    problemFile.flush()


def main():
    args = sys.argv[1:]
    global problemFile
    problemFile = open("ProblemFile.txt", "w")
    options = []
    parameters = []
    target_dir = "Result"
    for arg in args:
        if arg in {"--merge", "-o", "--test"}:
            options.append(arg)
        else:
            parameters.append(arg)

    if len(options) != 0:
        for op in options:
            if op == "--merge":
                stub_dir = Path(parameters.pop(0))
                source_dir = Path(parameters.pop(0))
                print("Creating directory " + source_dir.name + ": ")
                merged_dir = Path("./" + source_dir.name)
                merged_dir.mkdir(exist_ok=True)
                merge_dir(source_dir, stub_dir, merged_dir)
            elif op == "-o":
                target_dir = parameters.pop(0)
            elif op == "--test":
                test_file = Path(parameters.pop(0))
                type_info = process_file(test_file)
                for i in type_info:
                    print(i)

    if len(parameters) == 1:
        source_dir = Path(parameters[0])
        visitDir(source_dir, CsvWriter(Path(target_dir), csvHead1))
    # If target project is an directory merged with stub files, to include location information of variables,
    # add the source directory(the unmerged one) to arguments.

    elif len(parameters) == 2:
        global proj_root
        source_dir = Path(parameters[0])
        proj_root = source_dir
        unmerged_dir = Path(parameters[1])
        import Checker
        mypy = Checker.mypy_gen()
        pyright = Checker.pyright_gen()
        checkers = [mypy]
        head_tail = ""
        for checker in checkers:
            head_tail += ",type set of " + checker.name + ",file set of " + checker.name
        head_line = csvHead0 + head_tail + "\n"

        visitDir_with_unmerged(source_dir, unmerged_dir, CsvWriter(Path(target_dir), head_line), checkers)


if __name__ == "__main__":
    exit(main())
