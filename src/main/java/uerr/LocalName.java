package uerr;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * LocalNameEntity is for the name appearing inside a function or method
 * it corresponds to the operandName, identifierList of shortVarDecl and VarDecl in grammar.
 * it maybe a local variable/constant, a file variable/constant, a packagename, a functioname, or system's method/pack,func/key.
 *
 * LocalName is valid only inside a function/method.
 * So, We bind a LocalName list to its functionEntity, please see AbsFUNEntity class declaration.
 */
public class LocalName{
    private String name;    //operandName
    private String type;
    private String value;
    private int localBlockId;
    private ArrayList<String> usages = new ArrayList<String>(); //{"use", "set"} or {package}
    //map={set, number}
    private HashMap<String, Integer> weightedUsages = new HashMap<String, Integer>();


    public LocalName(String name, int localBlockId, String type, String value) {
        this.name = name;
        this.localBlockId = localBlockId;
        this.type = type;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getLocalBlockId() {
        return localBlockId;
    }

    public String getValue() {
        return value;
    }


    public ArrayList<String> getUsages() {
        return usages;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLocalBlockId(int localBlockId) {
        this.localBlockId = localBlockId;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void updateUsage(String usage) {
        for (String oneUsage : this.usages) {
            if (usage.equals(oneUsage)) {
                return;
            }
        }
        this.usages.add(usage);
    }

    public HashMap<String, Integer> getWeightedUsages() {
        return weightedUsages;
    }

    public void updateWeighedUsage(String usage) {
        if(weightedUsages.containsKey(usage)) {
            weightedUsages.put(usage, weightedUsages.get(usage) + 1);
        }
        else {
            weightedUsages.put(usage, 1);
        }
    }

    @Override
    public String toString() {
        String str = "";
        str += "LocalName(";
        str += ("name:" + name + ',');
        str += ("localBlockId:" + localBlockId + ",");
        str += ("type:" + type + ",");
        str += ("value:" + value + ",");
        str += ("usages:" + usages + ",");
        str += ("usages with weight:" + weightedUsages);
        str += ")";
        return str;
    }
}
