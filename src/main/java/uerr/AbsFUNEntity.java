package uerr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AbsFUNEntity extends AbsEntity {
    protected ArrayList<Integer> parameters = new ArrayList<Integer>();
    protected ArrayList<Integer> returns = new ArrayList<Integer>();



    public AbsFUNEntity() {}


    public AbsFUNEntity(int id, String name) {
        this.id = id;
        this.name = name;
        setSimpleName();
    }


    public ArrayList<Integer> getParameters() {
        return parameters;
    }

    public ArrayList<Integer> getReturns() {
        return returns;
    }

    public void setParameters(ArrayList<Integer> parameters) {
        this.parameters = parameters;
    }

    public void setReturns(ArrayList<Integer> returns) {
        this.returns = returns;
    }

    public void addParameter(int parameterId) {
        parameters.add(parameterId);
    }

    public void addReturn(int returnId) {
        returns.add(returnId);
    }


    @Override
    public String toString() {
        String str = "";
        str += "\n(Function:";
        str += ("id:" + id + ",");
        str += ("name:" + name + ",");
        str += ("parameters:" + parameters + ",");
        str += ("returns:" + returns + ",");
        str += ("parentId:" + parentId + ",");
        str += ("childrenIds:" + childrenIds + ",");
        str += ("relations:" + relations);
        str += ")\n";

        return str;
    }
}
