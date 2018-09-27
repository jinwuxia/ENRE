package entitytreebuilder.gobuilder.goentity;


import udr.AbsEntity;
import udr.AbsVAREntity;

import java.util.ArrayList;

public class InterfaceFieldEntity extends AbsEntity {
    private String type; //"METHOD" or "TYPE"
    //private String parameters; //if type = METHOD, it maybe will have
    //private String returns;    //if type = METHOD, it maybe will have
    private ArrayList<AbsVAREntity> parameters;
    private ArrayList<AbsVAREntity> returns;

    public InterfaceFieldEntity(String type, String name) {
        this.type = type;
        this.name = name;
        setSimpleName();

    }

    public InterfaceFieldEntity(String type, String name, ArrayList<AbsVAREntity> parameters, ArrayList<AbsVAREntity> returns) {
        this.type = type;
        this.name = name;
        this.parameters = parameters;
        this.returns = returns;
        setSimpleName();
    }

    public String getType() {
        return type;
    }

    public ArrayList<AbsVAREntity> getParameters() {
        return parameters;
    }

    public void setParameters(ArrayList<AbsVAREntity> parameters) {
        this.parameters = parameters;
    }

    public ArrayList<AbsVAREntity> getReturns() {
        return returns;
    }

    public void setReturns(ArrayList<AbsVAREntity> returns) {
        this.returns = returns;
    }

    @Override
    public String toString() {
        String str = "";
        if(type.equals("TYPE")) {
            str += "(InterfaceField:";
            str += ("id:" + id + ",");
            str += ("type:" + type + ",");
            str += ("name:" + name);
            str += ("parentId:" + parentId);
            str += ")";
        }
        else if (type.equals("METHOD")) {
            str += "(InfField:";
            str += ("id:" + id + ",");
            str += ("type:" + type + ",");
            str += ("name:" + name + ",");
            str += ("paras:" + parameters + ",");
            str += ("rets:" + returns + ",");
            str += ("parentId:" + parentId);
            str += ")";
        }
        return str;
    }
}
