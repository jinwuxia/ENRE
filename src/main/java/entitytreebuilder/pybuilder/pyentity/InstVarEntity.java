package entitytreebuilder.pybuilder.pyentity;

import udr.AbsVAREntity;

public class InstVarEntity extends AbsVAREntity {
    public InstVarEntity(int id, String name) {
        this.id = id;
        this.name = name;
        setSimpleName();
    }


    @Override
    public String toString() {
        String str = "";
        str += "\n(InstVar:";
        str += ("id:" + Integer.toString(id) + ",");
        str += ("name:" + name + ",");
        str += ("parentId:" + parentId);
        //str += ("childrenIds:" + childrenIds + ",");
        //str += ("relations:" + relations);
        str += ")\n";
        return str;

    }
}
