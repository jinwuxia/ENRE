package priextractor.py3extractor.newdeper;

public class ResolveResult {
    protected String resolvedManner;
    protected boolean isResolved;
    protected int bindId = -1;
    protected int typeId = -1;

    protected ResolveResult(String resolvedManner, boolean isResolved, int bindId, int typeId) {
        this.resolvedManner = resolvedManner;
        this.isResolved = isResolved;
        this.bindId = bindId;
        this.typeId = typeId;
    }
}