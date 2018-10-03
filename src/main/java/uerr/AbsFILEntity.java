package uerr;

import util.Tuple;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AbsFILEntity extends AbsEntity {
    protected ArrayList<Tuple<String, String>> imports = new ArrayList<Tuple<String, String>>(); //one string is import name, another string is import path
    protected Map<Integer, String> importsAlias =  new HashMap<Integer, String>();

    public void addImport(Tuple<String, String> oneImport) {
        imports.add(oneImport);
    }

    public ArrayList<Tuple<String, String>> getImports() {
        return imports;
    }

    public Map<Integer, String> getImportsAlias() {
        return importsAlias;
    }


    public void addImportAlias(String alias, int packageId) {
        importsAlias.put(packageId, alias);
    }

    @Override
    public String toString() {
        String str = "";
        str += "\n(File:";
        str += ("id:" + id + ",");
        str += ("name:" + name + ",");
        //str += ("packageId:" + packageId + ",");
        //str += ("includes:" + includedEntities + "\n");
        str += ("parentId:" + parentId + ",");
        str += ("childrenIds:" + childrenIds + ")\n");
        return str;
    }

    @Deprecated
    protected int packageId;

    @Deprecated
    public void setPackageId(int packageId) {
        this.packageId = packageId;
    }

    @Deprecated
    public int getPackageId() {
        return packageId;
    }
}
