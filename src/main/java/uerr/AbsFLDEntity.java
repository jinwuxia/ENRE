package uerr;

public class AbsFLDEntity extends AbsEntity {
    protected String fullPath;  //key

    public AbsFLDEntity(int id, String fullPath, String name) {
        this.id = id;
        this.fullPath = fullPath;
        this.name = name;
        setSimpleName();
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
