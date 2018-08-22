package multiparser.py3extractor.pyentity;

import multiparser.entity.VarEntity;

public class ClassVarEntity extends VarEntity{
    public ClassVarEntity(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        String str = "";
        str += "\n(ClassVar:";
        str += ("id:" + Integer.toString(id) + ",");
        str += ("name:" + name + ",");
        str += ("parentId:" + parentId);
        //str += ("childrenIds:" + childrenIds + ",");
        //str += ("relations:" + relations);
        str += ")\n";
        return str;

    }
}
