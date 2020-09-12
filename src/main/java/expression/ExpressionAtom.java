package expression;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ExpressionAtom {
	
    private String str;
    private int freq=0;
    private List<Integer> lineno = new ArrayList<>();
    private List<Integer> lastlineno = new ArrayList<>();
    private List<Integer> rowNo = new ArrayList<>();
    private List<Integer> lastrowNo = new ArrayList<>();
    private List<String> text = new ArrayList<>();
    private boolean isResolved = false;
    private List<Integer> bindIdList = new ArrayList<>(); //possible Ids
    private List<Integer> typeIdList = new ArrayList<>(); //bind to var, it is var type;  bind to method, it is method return type.
    private String usageType=""; //use, set, dot, call//print
    private String resolvedManner=""; //builtin, library, super, regular, implicit



    public ExpressionAtom(String str, String usageType, int freq, List<Integer> lineno,List<Integer> lastlineno, List<Integer> rowNo, List<Integer> lastrowNo,List<String> text) {
        this.str = str;
        this.usageType = usageType;
        this.freq = freq;
        this.lineno = lineno;
        this.lastlineno = lastlineno;
        this.rowNo  = rowNo;
        this.lastrowNo = lastrowNo;
        this.text = text;
        
    }

    public void setTypeIdList(List<Integer> typeIdList) {
        this.typeIdList = typeIdList;
    }

    public void setResolved(boolean resolved) {
        this.isResolved = resolved;
    }

    public void setBindIdList(List<Integer> bindIdList) {
        this.bindIdList = bindIdList;
    }

    public void setResolvedManner(String resolvedManner) {
        this.resolvedManner = resolvedManner;
    }

    public void setUsageType(String usageType) {
        this.usageType = usageType;
    }

    public String getStr() {
        return str;
    }

    public boolean getResolved() {
        return isResolved;
    }

    public List<Integer> getBindIdList() {
        return bindIdList;
    }

    public String getUsageType() {
        return usageType;
    }

    public String getResolvedManner() {
        return resolvedManner;
    }

    public List<Integer> getTypeIdList() {
        return typeIdList;
    }

    public void updateAtom(boolean isResolved, String resolvedManner, int bindId, int typeId) {
        this.isResolved = isResolved;
        this.resolvedManner = resolvedManner;
        this.bindIdList.add(bindId);
        this.typeIdList.add(typeId);
    }

    public void updateAtomBySet(boolean isResolved, String resolvedManner, List<Integer> bindIds, List<Integer> typeIds) {
        //remove the dupliciated one
        Set<Integer> tmp = new HashSet<>(typeIds);
        typeIds = new ArrayList<>(tmp);

        this.isResolved = isResolved;
        this.resolvedManner = resolvedManner;
        this.bindIdList = bindIds;
        this.typeIdList = typeIds;
    }

    public List<Integer> getLineno() {
        return lineno;
    }
    public List<Integer> getLastLineno() {
        return lastlineno;
    }
    public List<Integer> getRowNo(){
    	return rowNo;
    }
    public List<Integer> getLastRowNo(){
    	return lastrowNo;
    }
    public List<String> getText(){
    	return text;
    }
    

    @Override
    public String toString() {
        String str = "";
        str += ("atomstr:" + this.str + ",");
        str += ("manner:" + this.resolvedManner + ",");
        str += ("bind:" + this.bindIdList + ",");
        str += ("type:" + this.typeIdList + ",");
        str += ("usage:" + this.usageType + ",");
        str += ("freq:" + this.freq);
        return str;
    }
}

