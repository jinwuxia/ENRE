package uerr;

import java.util.List;

public class ExpressionAtom {
    private String str;
    private boolean isResolved = false;
    private List<Integer> bindIdList; //possible typeIds
    private String usageType; //use, set, dot, call
    private String resolvedManner; //builtin, library, super, regular, implicit

    public ExpressionAtom() {

    }

    public ExpressionAtom(String str) {
        this.str = str;
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

    public List<Integer> getBindIdList() {
        return bindIdList;
    }

    public String getUsageType() {
        return usageType;
    }

    public String getResolvedManner() {
        return resolvedManner;
    }
}
