package multiparser.py3extractor.pyentity;

import multiparser.entity.FunctionEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PyFunctionEntity extends FunctionEntity{
    protected ArrayList<ImportStmt> importStmts = new ArrayList<ImportStmt>();
    //imported id->above list index
    protected HashMap<Integer, Integer> importedId2Indexs = new HashMap<Integer, Integer>();


    //store the final usagewithweight ("usage", (nameEntityId, weight))/
    // in python parser, setdep and usedep will use this to save relations
    protected Map<String, Map<Integer, Integer>> finalUsageMap = new HashMap<String, Map<Integer, Integer>>();



    public PyFunctionEntity() {}

    public PyFunctionEntity(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public ArrayList<ImportStmt> getImportStmts() {
        return importStmts;
    }

    public void addImportStmt(ImportStmt stmt) {
        importStmts.add(stmt);
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

    public Map<String, Map<Integer, Integer>> getFinalUsageMap() {
        return finalUsageMap;
    }

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

}
