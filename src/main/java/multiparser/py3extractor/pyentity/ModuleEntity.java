package multiparser.py3extractor.pyentity;

import multiparser.entity.FileEntity;
import multiparser.entity.LocalName;

import java.util.ArrayList;
import java.util.HashMap;

public class ModuleEntity extends FileEntity{
    private String moduleSimpleName; // without path, a simple name

    //init form of functioncalls
    private ArrayList<String> calledFunctions = new ArrayList<String>();
    //private HashMap<String, Integer> calledWeightedFunctions = new HashMap<String, Integer>();
    protected ArrayList<LocalName> localNames = new ArrayList<LocalName>(); //the initial Names appear in a function

    public ModuleEntity(int moduleId, String name) {
        this.id = moduleId;
        this.name = name;
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
        this.calledFunctions = calledFunctions;
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
    public void addFunctionCall(String calleeStr) {
        this.calledFunctions.add(calleeStr);
    }

    public void addLocalName(LocalName localName) {
        this.localNames.add(localName);
    }

    public ArrayList<LocalName> getLocalNames() {
        return localNames;
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
        str += ("calledFunctions" + calledFunctions + ")\n");
        //str += ("calledFunctions with weight" + calledWeightedFunctions + ")\n");
        return str;
    }
}
