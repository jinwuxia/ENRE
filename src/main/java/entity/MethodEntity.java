package entity;

import sun.jvm.hotspot.runtime.VMReg;

import java.util.ArrayList;

public class MethodEntity extends FunctionEntity {
    private VarEntity receiver;

/*    public MethodEntity(String name, String parameters, String returns, String receiver) {
        this.name = name;
        this.parameters = parameters;
        this.returns = returns;
        this.receiver = receiver;
    }*/


    public MethodEntity(String name, VarEntity receiver) {
        this.name = name;
        this.receiver = receiver;
    }

    public VarEntity getReceiver() {
        return receiver;
    }

    public void setReceiver(VarEntity receiver) {
        this.receiver = receiver;
    }

    @Override
    public String toString() {
        String str = "";
        str += "\n(Method:";
        str += ("id:" + id + ",");
        str += ("name:" + name + ",");
        str += ("receiver:" + receiver + ",");
        str += ("parameters:" + parameters + ",");
        str += ("returns:" + returns + ",");
        str += ("parentId:" + parentId + ",");
        str += ("childrenIds:" + childrenIds);
        str += ")\n";
        return str;
    }
}
