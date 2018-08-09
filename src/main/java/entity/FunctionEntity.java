package entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FunctionEntity extends Entity {
    protected ArrayList<Integer> parameters = new ArrayList<Integer>();
    protected ArrayList<Integer> returns = new ArrayList<Integer>();
    protected ArrayList<String> calledFunctions = new ArrayList<String>();

    //generate in the first visit. will be further processed in the second visit.
    //in the second visit, all these information is atored in name2IdMap, name2UsageMap, name2RoleMap.
    protected ArrayList<LocalName> localNames = new ArrayList<LocalName>(); //the initial Names appear in a function
    protected Map<String, Integer> name2IdMap = new HashMap<String, Integer>(); //map from the usedName inside function into the entityId.
    //local name -> role (parameter, return , globalvar, localVar, function, package)
    protected Map<String, String> name2RoleMap = new HashMap<String, String>();
    //local name -> usage (set, use)
    protected Map<String, ArrayList<String>> name2Usage = new HashMap<String, ArrayList<String>>();



    //generate in the first visit. it will be used for localName search.
    protected ArrayList<LocalBlock> localBlocks = new ArrayList<LocalBlock>();


    public FunctionEntity() {}

    public FunctionEntity(String name) {
        this.name = name;
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

    public void addCalledFunction(String functionName) {
        calledFunctions.add(functionName);
    }

    public ArrayList<String> getCalledFunctions() {
        return calledFunctions;
    }

    public int searchLocalName(String name, String scope) {
        for (int i = 0; i <localNames.size(); i++) {
            LocalName localName = localNames.get(i);
            if(localName.getName().equals(name) && localName.getScope().equals(scope)) {
                return i;
            }
        }
        return -1;
    }

    public void addLocalName(LocalName oneLocalName) {
        localNames.add(oneLocalName);
    }

    public ArrayList<LocalName> getLocalNames() {
        return localNames;
    }

    public Map<String, Integer> getName2IdMap() {
        return name2IdMap;
    }

    public Map<String, String> getName2RoleMap() {
        return name2RoleMap;
    }

    public Map<String, ArrayList<String>> getName2UsageMap() {
        return name2Usage;
    }

    public void addName2Id(String name, int id) {
        if(!name2IdMap.containsKey(name)) {
            name2IdMap.put(name, id);
        }
    }

    public void addName2Usage(String name, String usage) {
        if (!name2Usage.containsKey(name)) {
            name2Usage.put(name, new ArrayList<String>());
        }
        if(!name2Usage.get(name).contains(usage)) {
            name2Usage.get(name).add(usage);
        }
    }

    public void addName2Role(String name, String role) {
        if(!name2RoleMap.containsKey(name)) {
            name2RoleMap.put(name, role);
        }
    }


    public ArrayList<LocalBlock> getLocalBlocks() {
        return localBlocks;
    }

    public void addLocalBlock(LocalBlock block) {
        localBlocks.add(block);
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
        str += ("childrenIds:" +childrenIds);
        str += ")\n";

        return str;
    }
}
