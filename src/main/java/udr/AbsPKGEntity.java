package udr;

public class AbsPKGEntity extends AbsEntity {
    protected String fullPath;  //key

    public AbsPKGEntity(int id, String fullPath, String name) {
        this.id = id;
        this.fullPath = fullPath;
        this.name = name;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }

    public String getFullPath() {
        return fullPath;
    }

    @Override
    public String toString() {
        String str = "";
        str += "\n(Package:";
        str += ("id:" + id + ",");
        str += ("fullpath:" + fullPath + ",");
        str += ("name:" + name + ",");
        str += ("childrenIds:" + childrenIds);
        str += ("parentId:" + parentId);
        str += ")\n";
        return str;
    }
}
