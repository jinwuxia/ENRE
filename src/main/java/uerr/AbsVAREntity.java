package uerr;

public class AbsVAREntity extends AbsEntity {
    protected String type;
    protected String value;
    /**
     * the id of the type if the type (structType or AliasType) is defined in the source code
     */
    protected int typeId = -1;
    /**
     * record the local block id. If the var is not in localblock, it's set to be the -1.
     */
    protected int localBlockId;

    public AbsVAREntity(int id, String type, String name) {
        this.id = id;
        this.type = type;
        this.name = name;
        setSimpleName();
    }

    public AbsVAREntity() {}

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public void setLocalBlockId(int localBlockId) {
        this.localBlockId = localBlockId;
    }

    public int getLocalBlockId() {
        return localBlockId;
    }

    @Override
    public String toString() {
        String str = "";
        str += "(Var:";
        str += ("id:" + id + ",");
        str += ("type:" + type + ",");
        str += ("name:" + name + ",");
        str += ("localBlockId:" + localBlockId + ",");
        str += ("parentId:" + parentId);
        str += ")";
        return str;
    }
}
