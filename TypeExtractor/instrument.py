import os
from pathlib import Path
from typing import Any, List, Tuple


tempFileName = "tempFile.py"
REVEAL_TYPE_START = 'reveal_type(('
REVEAL_TYPE_END = '))'


def write_til(source_lines, one_pass_instr, have_inserted, loc):
    if have_inserted != loc[2]:
        one_pass_instr.writelines(source_lines[have_inserted:loc[2]])
    one_pass_instr.write(" " * loc[3])
    return loc[2]


def instrument(loc_list, file_path: Path) -> Tuple[Path, List[Any]]:
    tempFile: str = os.path.join(Path(file_path).parent, tempFileName)
    one_pass_instr = open(tempFile, "w", encoding="utf-8")
    need_reveal = []
    with open(file_path, "r", encoding="utf-8") as f:
        lines = f.readlines()
        instrumented = 0
        inserted = 0
        loc_list.sort(key = lambda lst : lst[2])
        for loc in loc_list:
            need_reveal.append((loc[0], loc[2] + instrumented + 1, loc[4], loc[5], loc[6], loc[7]))
            write_til(lines, one_pass_instr, inserted, loc)
            one_pass_instr.write(REVEAL_TYPE_START + loc[0] + REVEAL_TYPE_END + '\n')
            one_pass_instr.flush()
            instrumented += 1
            inserted = loc[2]
        if inserted < len(lines):
            one_pass_instr.writelines(lines[inserted:])
            one_pass_instr.flush()
    one_pass_instr.close()
    return Path(tempFile), need_reveal

