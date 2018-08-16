package multiparser.entity;

public class AliasTypeEntity extends TypeEntity{
    String type; //it real type
    //String name; //it's alias type

    public AliasTypeEntity(int id, String type, String name) {
        this.id = id;
        this.type = type;
        this.name = name;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        String str = "";
        str += "\nAliasType(";
        str += ("name:" + name + ',');
        str += ("id:" + id + ',');
        str += ("type:" + type + ',');
        str += ("parentId:" + parentId + ",");
        str += ("childrenIds:" + childrenIds + ",");
        str += ("relations:" + relations);
        str += ")\n";
        return str;
    }

}
