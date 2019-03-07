package entitybuilder.pybuilder.pyentity;

import uerr.AbsFILEntity;
import uerr.LocalName;

import java.util.ArrayList;
import java.util.HashMap;

public class ModuleEntity extends AbsFILEntity {
    private String moduleSimpleName; // without path, a simple name
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
        str += ("imports:" + importStmts + ",");
        str += ("relations:" + relations + ")\n");
        //str += ("calledFunctions with weight" + calledWeightedFunctions + ")\n");
        return str;
    }
}
