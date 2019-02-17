package uerr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AbsFUNEntity extends AbsEntity {
    protected ArrayList<Integer> parameters = new ArrayList<Integer>();
    protected ArrayList<Integer> returns = new ArrayList<Integer>();
    protected ArrayList<String> calledFunctions = new ArrayList<String>();

    //generate in the first visit. will be further processed in the second visit.
    //in the second visit, all these information is atored in name2IdMap, name2UsageMap, name2RoleMap.
    protected ArrayList<LocalName> localNames = new ArrayList<LocalName>(); //the initial Names appear in a function
    protected Map<String, Integer> name2IdMap = new HashMap<String, Integer>(); //map from the usedName inside function into the entityId.

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

    /**
     * go python
     * @param functionName
     */
    public void addCalledFunction(String functionName) {
        calledFunctions.add(functionName);
    }

    /**
     * go python
     * @return
     */
    public ArrayList<String> getCalledFunctions() {
        return calledFunctions;
    }

    /**
     * python
     * @param calledFunctions
     */
    public void setCalledFunctions(ArrayList<String> calledFunctions) {
        this.calledFunctions.clear();
        this.calledFunctions.addAll(calledFunctions);
    }




    /**
     * go python
     * @param oneLocalName
     */
    public void addLocalName(LocalName oneLocalName) {
        localNames.add(oneLocalName);
    }

    /**
     * go python
      * @return
     */
    public ArrayList<LocalName> getLocalNames() {
        return localNames;
    }

    /** go python
     *
     * @return
     */
    public Map<String, Integer> getName2IdMap() {
        return name2IdMap;
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
