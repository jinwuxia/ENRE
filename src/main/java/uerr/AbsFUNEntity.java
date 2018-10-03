package uerr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AbsFUNEntity extends AbsEntity {
    protected ArrayList<Integer> parameters = new ArrayList<Integer>();
    protected ArrayList<Integer> returns = new ArrayList<Integer>();
    protected ArrayList<String> calledFunctions = new ArrayList<String>();
    //protected HashMap<String, Integer> calledWeightedFunctions = new HashMap<String, Integer>();

    //generate in the first visit. will be further processed in the second visit.
    //in the second visit, all these information is atored in name2IdMap, name2UsageMap, name2RoleMap.
    protected ArrayList<LocalName> localNames = new ArrayList<LocalName>(); //the initial Names appear in a function
    protected Map<String, Integer> name2IdMap = new HashMap<String, Integer>(); //map from the usedName inside function into the entityId.
    //local name -> role (parameter, return , globalvar, localVar, function, package)
    protected Map<String, String> name2RoleMap = new HashMap<String, String>();
    //local name -> usage (set, use)
    protected Map<String, ArrayList<String>> name2Usage = new HashMap<String, ArrayList<String>>();


    //generate in the first visit. it will be used for localName priextractor.goextractor.searcher.
    protected ArrayList<LocalBlock> localBlocks = new ArrayList<LocalBlock>();


    public AbsFUNEntity() {}

    public AbsFUNEntity(String name) {
        this.name = name;
        setSimpleName();
    }
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

    public void addCalledFunction(String functionName) {
        calledFunctions.add(functionName);
    }

    public ArrayList<String> getCalledFunctions() {
        return calledFunctions;
    }

    public void setCalledFunctions(ArrayList<String> calledFunctions) {
        this.calledFunctions.clear();
        this.calledFunctions.addAll(calledFunctions);
    }

   /* public HashMap<String, Integer> getCalledWeightedFunctions() {
        return calledWeightedFunctions;
    }

    public void updateCalledWeightedFunction(String calleeStr) {
        if(calledWeightedFunctions.containsKey(calleeStr)) {
            calledWeightedFunctions.put(calleeStr, calledWeightedFunctions.get(calleeStr) + 1);
        }
        else {
            calledWeightedFunctions.put(calleeStr, 1);
        }
    }*/

    /**
     * priextractor.goextractor.searcher the localName under same localBlock or parentBlock,
     * @param name
     * @param localBlockId : the blockId of the searched name.
     * @return
     */
    public int searchLocalName(String name, int localBlockId) {
        if(localBlockId == -1) {
            return -1;
        }
        /**
         * look at the nearest one, close-rule.
         * It's not usable in static language,
         * but it is important in dynamic typing language.
         */
        for (int i = localNames.size() - 1; i >= 0; i--) {
            LocalName candidateLocalName = localNames.get(i);
            int candidateLocalBlockId = candidateLocalName.getLocalBlockId();
            if(candidateLocalName.getName().equals(name)
                    && isCandidateBlockCoverThisBlock(candidateLocalBlockId, localBlockId)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * when blockID is same, or currentBlockDepth > candidiateBlockDepth
     * @param candidateBlockId
     * @param currentBlockId
     * @return
     */
    private boolean isCandidateBlockCoverThisBlock(int candidateBlockId, int currentBlockId) {
        if(candidateBlockId == -1 ||candidateBlockId == -1) {
            return false;
        }
        if (candidateBlockId == currentBlockId) {
            return true;
        }

        if(localBlocks.get(currentBlockId).getDepth() > localBlocks.get(candidateBlockId).getDepth()) {
            return true;
        }
        return false;
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
        str += ("childrenIds:" + childrenIds + ",");
        str += ("relations:" + relations);
        str += ")\n";

        return str;
    }
}
