package multiparser.py3extractor.pyentity;

import multiparser.entity.FunctionEntity;

import java.util.ArrayList;

public class PyFunctionEntity extends FunctionEntity{
    protected ArrayList<ImportStmt> importStmts = new ArrayList<ImportStmt>();
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

}
