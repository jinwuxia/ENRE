package entitybuilder.pybuilder.pyentity;

import uerr.AbsFILEntity;
import uerr.LocalName;

import java.util.ArrayList;
import java.util.HashMap;

public class ModuleEntity extends AbsFILEntity {
    private String moduleSimpleName; // without path, a simple name

    //init form of functioncalls
    private ArrayList<String> calledFunctions = new ArrayList<String>();
    //private HashMap<String, Integer> calledWeightedFunctions = new HashMap<String, Integer>();
    private ArrayList<LocalName> localNames = new ArrayList<LocalName>(); //the initial Names appear in a function

    private ArrayList<ImportStmt> importStmts = new ArrayList<ImportStmt>();
    private HashMap<Integer, Integer> importedId2Indexs = new HashMap<Integer, Integer>(); //[importedId, aboveIndex]



    public ModuleEntity(int moduleId, String name) {
        this.id = moduleId;
        this.name = name;
        setSimpleName();
    }

    public void setModuleSimpleName(String moduleSimpleName) {
        this.moduleSimpleName = moduleSimpleName;
    }

    public String getModuleSimpleName() {
        return moduleSimpleName;
    }

    public ArrayList<String> getCalledFunctions() {
        return calledFunctions;
    }


    public void setCalledFunctions(ArrayList<String> calledFunctions) {
        this.calledFunctions.clear();
        this.calledFunctions.addAll(calledFunctions);
    }

    /*public HashMap<String, Integer> getCalledWeightedFunctions() {
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
     * even if calleeStr is already added, it still be added
     * @param calleeStr
     */
    public void addCalledFunction(String calleeStr) {
        this.calledFunctions.add(calleeStr);
    }

    public void addLocalName(LocalName localName) {
        this.localNames.add(localName);
    }

    public ArrayList<LocalName> getLocalNames() {
        return localNames;
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

    @Override
    public String toString() {
        String str = "";
        str += "\n(Module:";
        str += ("id:" + id + ",");
        str += ("name:" + name + ",");
        //str += ("packageId:" + packageId + ",");
        //str += ("includes:" + includedEntities + "\n");
        str += ("parentId:" + parentId + ",");
        str += ("childrenIds:" + childrenIds + ",");
        str += ("calledFunctions:" + calledFunctions + ",");
        str += ("imports:" + importStmts + ",");
        str += ("relations:" + relations + ")\n");
        //str += ("calledFunctions with weight" + calledWeightedFunctions + ")\n");
        return str;
    }
}
