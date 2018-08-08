package entity;

import java.util.ArrayList;

/**
 * LocalNameEntity is for the name appearing inside a function or method
 * it corresponds to the operandName, identifierList of shortVarDecl and VarDecl in grammar.
 * it maybe a local variable/constant, a file variable/constant, a packagename, a functioname, or system's method/pack,func/key.
 *
 * LocalName is valid only inside a function/method.
 * So, We bind a LocalName list to its functionEntity, please see FunctionEntity class declaration.
 */
public class LocalName{
    private String name;    //operandName
    private String type;
    private String value;
    private String scope;
    private ArrayList<String> usages = new ArrayList<String>(); //{"use", "set"} or {package}


    public LocalName(String name, String scope, String type, String value) {
        this.name = name;
        this.scope = scope;
        this.type = type;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getScope() {
        return scope;
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

    public void setScope(String scope) {
        this.scope = scope;
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

    @Override
    public String toString() {
        String str = "";
        str += "\nLocalName(";
        str += ("name:" + name + ',');
        str += ("scope:" + scope + ",");
        str += ("type:" + type + ",");
        str += ("value:" + value);
        str += ")\n";
        return str;
    }
}
