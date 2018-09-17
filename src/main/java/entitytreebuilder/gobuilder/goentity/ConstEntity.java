package entitytreebuilder.gobuilder.goentity;

import udr.AbsVAREntity;

public class ConstEntity extends AbsVAREntity {
    public ConstEntity(int i, String type, String name) {
        super(i,type,name);
    }

    @Override
    public String toString() {
        String str = "";
        str += "\n(Const:";
        str += ("id:" + id + ",");
        str += ("type:" + type + ",");
        str += ("name:" + name + ",");
        str += ("parentId:" + parentId);
        str += ")\n";
        return str;
    }
}
