package multiparser.py3extractor.pyentity;

public class InstMethodEntity extends PyFunctionEntity{

    public InstMethodEntity(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        String str = "";
        str += "\n(InstMethod:";
        str += ("id:" + Integer.toString(id) + ",");
        str += ("name:" + name + ",");
        str += ("parentId:" + parentId + ",");
        str += ("childrenIds:" + childrenIds + ",");
        str += ("relations:" + relations);
        str += ")\n";
        return str;

    }

}
