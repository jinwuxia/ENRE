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

    

}
