package expression;

import org.omg.PortableInterceptor.INACTIVE;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ExpressionAtom {
    private String str;
    private int freq=0;
    private boolean isResolved = false;
    private List<Integer> bindIdList = new ArrayList<>(); //possible Ids
    private List<Integer> typeIdList = new ArrayList<>(); //bind to var, it is var type;  bind to method, it is method return type.
    private String usageType=""; //use, set, dot, call
    private String resolvedManner=""; //builtin, library, super, regular, implicit


    public ExpressionAtom(String str, String usageType, int freq) {
        this.str = str;
        this.usageType = usageType;
        this.freq = freq;
    }

    public void setTypeIdList(List<Integer> typeIdList) {
        this.typeIdList = typeIdList;
    }

    public void setResolved(boolean resolved) {
        isResolved = resolved;
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

