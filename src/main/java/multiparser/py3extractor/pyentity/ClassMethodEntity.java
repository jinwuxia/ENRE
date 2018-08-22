package multiparser.py3extractor.pyentity;

import multiparser.entity.FunctionEntity;

public class ClassMethodEntity extends FunctionEntity{

    public ClassMethodEntity(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        String str = "";
        str += "\n(Classmethod:";
        str += ("id:" + Integer.toString(id) + ",");
        str += ("name:" + name + ",");
        str += ("parentId:" + parentId + ",");
        str += ("childrenIds:" + childrenIds + ",");
        str += ("relations:" + relations);
        str += ")\n";
        return str;

    }
}
