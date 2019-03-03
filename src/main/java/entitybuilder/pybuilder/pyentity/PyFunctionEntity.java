package entitybuilder.pybuilder.pyentity;

import uerr.AbsFUNEntity;
import uerr.LocalName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PyFunctionEntity extends AbsFUNEntity {

    protected ArrayList<ImportStmt> importStmts = new ArrayList<ImportStmt>();
    //imported id->above list index
    protected HashMap<Integer, Integer> importedId2Indexs = new HashMap<Integer, Integer>();


    public PyFunctionEntity() {
    }

    public PyFunctionEntity(int id, String name) {
        this.id = id;
        this.name = name;
        setSimpleName();
    }

    public ArrayList<ImportStmt> getImportStmts() {
        return importStmts;
    }


    public void addImportStmts(ArrayList<ImportStmt> stmts) {
        importStmts.addAll(stmts);
    }

    public void updateImportedId2Indexs(int importedId, int index)  {
        importedId2Indexs.put(importedId, index);
    }

    public HashMap<Integer, Integer> getImportedId2Indexs() {
        return importedId2Indexs;
    }




    @Deprecated
    //store the final usagewithweight ("usage", (nameEntityId, weight))/
    // in python parser, setdep and usedep will use this to save relations
    protected Map<String, Map<Integer, Integer>> finalUsageMap = new HashMap<String, Map<Integer, Integer>>();

    //generate in the first visit. will be further processed in the second visit.
    //in the second visit, all these information is atored in name2IdMap, name2UsageMap, name2RoleMap.
    @Deprecated
    protected ArrayList<LocalName> localNames = new ArrayList<LocalName>(); //the initial Names appear in a function
    @Deprecated
    protected Map<String, Integer> name2IdMap = new HashMap<String, Integer>(); //map from the usedName inside function into the entityId.

    @Deprecated
    public Map<String, Map<Integer, Integer>> getFinalUsageMap() {
        return finalUsageMap;
    }

    @Deprecated
    public void updateFinalUsageMap(String usage, int nameEntityId, int weight) {
        if(!finalUsageMap.containsKey(usage)) {
            finalUsageMap.put(usage, new HashMap<Integer, Integer>());
        }
        if(!finalUsageMap.get(usage).containsKey(nameEntityId)) {
            finalUsageMap.get(usage).put(nameEntityId, 0);
        }
        int oldWeight = finalUsageMap.get(usage).get(nameEntityId);
        finalUsageMap.get(usage).put(nameEntityId, oldWeight + weight);
    }

    @Deprecated
    public ArrayList<LocalName> getLocalNames() {
        return localNames;
    }

    @Deprecated
    public Map<String, Integer> getName2IdMap() {
        return name2IdMap;
    }




}
