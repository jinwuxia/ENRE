package entity;

import sun.jvm.hotspot.runtime.VMReg;

import java.util.ArrayList;

public class MethodEntity extends FunctionEntity {
    //private VarEntity receiver;
    private int receiverVarId;

/*    public MethodEntity(String name, String parameters, String returns, String receiver) {
        this.name = name;
        this.parameters = parameters;
        this.returns = returns;
        this.receiver = receiver;
    }*/


    public MethodEntity(String name) {
        this.name = name;
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
