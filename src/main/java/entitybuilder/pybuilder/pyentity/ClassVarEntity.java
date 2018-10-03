package entitybuilder.pybuilder.pyentity;

import uerr.AbsVAREntity;

public class ClassVarEntity extends AbsVAREntity {
    public ClassVarEntity(int id, String name) {
        this.id = id;
        this.name = name;
        setSimpleName();
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
