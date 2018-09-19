package entitytreebuilder.pybuilder.pyentity;


public class ClassMethodEntity extends PyMethodEntity{

    public ClassMethodEntity(int id, String name) {
        this.id = id;
        this.name = name;
        setSimpleName();
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
