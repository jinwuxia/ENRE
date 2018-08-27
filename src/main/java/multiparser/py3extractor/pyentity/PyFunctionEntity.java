package multiparser.py3extractor.pyentity;

import multiparser.entity.FunctionEntity;

import java.util.ArrayList;
import java.util.HashMap;

public class PyFunctionEntity extends FunctionEntity{
    protected ArrayList<ImportStmt> importStmts = new ArrayList<ImportStmt>();
    //imported id->above list index
    protected HashMap<Integer, Integer> importedId2Indexs = new HashMap<Integer, Integer>();

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

}
