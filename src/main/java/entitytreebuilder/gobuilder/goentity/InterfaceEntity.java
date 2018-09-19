package entitytreebuilder.gobuilder.goentity;

public class InterfaceEntity extends TypeEntity {

    public InterfaceEntity(int id, String name) {
        this.id = id;
        this.name = name;
        setSimpleName();

    }

    @Override
    public String toString() {
        String str = "";
        str += "\n(Interface:";
        str += ("id:" + Integer.toString(id) + ",");
        str += ("name:" + name + ",");
        str += ("parentId:" + parentId + ",");
        str += ("childrenIds:" + childrenIds + ",");
        str += ("relations:" + relations);
        str += ")\n";
        return str;
    }
}
