package entity;


import java.util.ArrayList;

public class InterfaceFieldEntity extends Entity {
    private String type; //"METHOD" or "TYPE"
    //private String parameters; //if type = METHOD, it maybe will have
    //private String returns;    //if type = METHOD, it maybe will have
    private ArrayList<VarEntity> parameters;
    private ArrayList<VarEntity> returns;

    public InterfaceFieldEntity(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public InterfaceFieldEntity(String type, String name, ArrayList<VarEntity> parameters, ArrayList<VarEntity> returns) {
        this.type = type;
        this.name = name;
        this.parameters = parameters;
        this.returns = returns;
    }

    public String getType() {
        return type;
    }

    public ArrayList<VarEntity> getParameters() {
        return parameters;
    }

    public void setParameters(ArrayList<VarEntity> parameters) {
        this.parameters = parameters;
    }

    public ArrayList<VarEntity> getReturns() {
        return returns;
    }

    public void setReturns(ArrayList<VarEntity> returns) {
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
