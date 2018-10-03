package entitybuilder.gobuilder.goentity;

import uerr.AbsFUNEntity;

public class MethodEntity extends AbsFUNEntity {
    //private AbsVAREntity receiver;
    private int receiverVarId;

/*    public MethodEntity(String name, String parameters, String returns, String receiver) {
        this.name = name;
        this.parameters = parameters;
        this.returns = returns;
        this.receiver = receiver;
    }*/


    public MethodEntity(String name) {
        this.name = name;
        setSimpleName();
    }

    public int getReceiverVarId() {
        return receiverVarId;
    }

    public void setReceiverVarId(int receiverVarId) {
        this.receiverVarId = receiverVarId;
    }

    @Override
    public String toString() {
        String str = "";
        str += "\n(Method:";
        str += ("id:" + id + ",");
        str += ("name:" + name + ",");
        str += ("receiverVarId:" + receiverVarId + ",");
        str += ("parameters:" + parameters + ",");
        str += ("returns:" + returns + ",");
        str += ("parentId:" + parentId + ",");
        str += ("childrenIds:" + childrenIds);
        str += ")\n";
        return str;
    }
}
