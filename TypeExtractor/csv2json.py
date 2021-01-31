import json
import os
import sys
from pathlib import Path
import csv
from typing import List
import bisect


def csv2json(folder: Path, out_file: str, src_array: List[str]):
    src_array.sort()
    deps = {}
    out_json = {}
    out_json["@schemaVersion"] = 1.0
    out_json["name"] = out_file
    out_json["variables"] = src_array
    with os.scandir(folder) as entries:
        for entry in entries:
            assert not entry.is_dir()
            if not entry.name.endswith(".csv"):
                continue
            with open(entry.path, "r", encoding="utf-8") as csv_file:
                reader = csv.reader(csv_file)
                reader.__next__()
                for row in reader:
                    src = row[0].replace("\"", "").replace(" ", "").replace("\\", "/")
                    dests = row[-1].replace("\"", "").replace("\\", "/").split(";")
                    if len(row) < 2:
                        continue
                    # print(dests)
                    add_to_deps(src, dests, deps)
    cells = []
    for key, value in deps.items():
        src_index = bisect.bisect_left(src_array, key[0])
        dest_index = bisect.bisect_left(src_array, key[1])
        if src_index != dest_index:
            an_obj = {"src": src_index, "dest": dest_index,
                      "values": {"type-use": float(value)}}
            cells.append(an_obj)

    out_json["cells"] = cells
    return out_json


def add_to_deps(src, dests, deps):
    for dest in dests:
        if dest == "":
            continue
        if (src, dest) in deps:
            deps[(src, dest)] += 1
        else:
            deps[(src, dest)] = 1


def folder_detect(root: Path, folder: Path, file_list: List[str]):
    with os.scandir(folder) as entries:
        for entry in entries:
            if entry.is_dir():
                folder_detect(root, Path(entry.path), file_list)
            elif entry.name.endswith(".py"):
                file_path = Path(entry.path)
                relative = file_path.relative_to(root.parent)
                file_list.append(str(relative).replace("\\", "/"))


def main():
    list = []
    args = sys.argv[1:]
    src_dir = Path(args[0])
    csv_dir = Path(args[1])
    out_filename = args[2]
    folder_detect(src_dir, src_dir, list)
    json_object = csv2json(csv_dir, out_filename[:-5], list)

    json_str = json.dumps(json_object, indent=4)
    # print(json_str)
    file = open(out_filename, "w")
    file.write(json_str)
    file.close()


if __name__ == "__main__":
    exit(main())
