package entitytreebuilder.gobuilder.goentity;

import udr.AbsVAREntity;

public class StructFieldEntity extends AbsVAREntity {
    public StructFieldEntity(int id, String type, String name) {
        this.id = id;
        this.type = type;
        this.name = name;//if name="ANONYMOUS", it is the embed structure.
    }

    @Override
    public String toString() {
        String str = "";
        str += "(StructField:";
        str += ("id:" + this.id + ",");
        str += ("type:" + this.type + ",");
        str += ("name:" + this.name + ",");
        str += ("parentId:" + parentId);
        str += (")");
        return str;
    }
}
