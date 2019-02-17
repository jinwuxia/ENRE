package entitybuilder.gobuilder.goentity;

import uerr.AbsFUNEntity;
import uerr.LocalBlock;
import uerr.LocalName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GoFunEntity extends AbsFUNEntity {

    //generate in the first visit. will be further processed in the second visit.
    //in the second visit, all these information is atored in name2IdMap, name2UsageMap, name2RoleMap.
    //local name -> role (parameter, return , globalvar, localVar, function, package)
    protected Map<String, String> name2RoleMap = new HashMap<String, String>();
    //local name -> usage (set, use)
    protected Map<String, ArrayList<String>> name2Usage = new HashMap<String, ArrayList<String>>();


    //generate in the first visit. it will be used for localName priextractor.goextractor.searcher.
    protected ArrayList<LocalBlock> localBlocks = new ArrayList<LocalBlock>();


    protected ArrayList<String> calledFunctions = new ArrayList<String>();

    //generate in the first visit. will be further processed in the second visit.
    //in the second visit, all these information is atored in name2IdMap, name2UsageMap, name2RoleMap.
    protected ArrayList<LocalName> localNames = new ArrayList<LocalName>(); //the initial Names appear in a function
    protected Map<String, Integer> name2IdMap = new HashMap<String, Integer>(); //map from the usedName inside function into the entityId.


    public GoFunEntity() {}

    public GoFunEntity(String name) {
        this.name = name;
        setSimpleName();
    }

    /**
     * go
     * goextractor.searcher the localName under same localBlock or parentBlock,
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

    /**
     * go
     * @return
     */
    public Map<String, String> getName2RoleMap() {
        return name2RoleMap;
    }

    /**
     * go
     * @return
     */
    public Map<String, ArrayList<String>> getName2UsageMap() {
        return name2Usage;
    }

    /**
     * go
     * @param name
     * @param id
     */
    public void addName2Id(String name, int id) {
        if(!name2IdMap.containsKey(name)) {
            name2IdMap.put(name, id);
        }
    }

    /**
     * go
     * @param name
     * @param usage
     */
    public void addName2Usage(String name, String usage) {
        if (!name2Usage.containsKey(name)) {
            name2Usage.put(name, new ArrayList<String>());
        }
        if(!name2Usage.get(name).contains(usage)) {
            name2Usage.get(name).add(usage);
        }
    }

    /**
     * go
     * @param name
     * @param role
     */
    public void addName2Role(String name, String role) {
        if(!name2RoleMap.containsKey(name)) {
            name2RoleMap.put(name, role);
        }
    }


    /**
     * go
     * @return
     */
    public ArrayList<LocalBlock> getLocalBlocks() {
        return localBlocks;
    }

    /**
     * go
     * @param block
     */
    public void addLocalBlock(LocalBlock block) {
        localBlocks.add(block);
    }


    /**
     * go
     * @param functionName
     */
    public void addCalledFunction(String functionName) {
        calledFunctions.add(functionName);
    }

    /**
     * go
     * @return
     */
    public ArrayList<String> getCalledFunctions() {
        return calledFunctions;
    }





    /**
     * go
     * @param oneLocalName
     */
    public void addLocalName(LocalName oneLocalName) {
        localNames.add(oneLocalName);
    }

    /**
     * go
     * @return
     */
    public ArrayList<LocalName> getLocalNames() {
        return localNames;
    }

    /** go
     *
     * @return
     */
    public Map<String, Integer> getName2IdMap() {
        return name2IdMap;
    }




}
