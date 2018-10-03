package entitybuilder.gobuilder.goentity;

public class StructEntity extends TypeEntity {

    public StructEntity(int id, String name) {
        this.id = id;
        this.name = name;
        setSimpleName();

    }

    @Override
    public String toString() {
        String str = "";
        str += "\n(Struct:";
        str += ("id:" + Integer.toString(id) + ",");
        str += ("name:" + name + ",");
        str += ("parentId:" + parentId + ",");
        str += ("childrenIds:" + childrenIds + ",");
        str += ("relations:" + relations);
        str += ")\n";
        return str;

    }
}
