package entitytreebuilder.pybuilder.pyentity;

public class ImportStmt {
    private String from;
    private String impor;
    private String as;

    public ImportStmt(String from, String impor, String as) {
        this.from = from;
        this.impor = impor;
        this.as = as;
    }
    public String getImpor() {
        return impor;
    }

    public String getFrom() {
        return from;
    }

    public String getAs() {
        return as;
    }

    public void setImpor(String impor) {
        this.impor = impor;
    }

    public void setAs(String as) {
        this.as = as;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    @Override
    public String toString() {
        String str = "from ";
        str += from + " ";
        str += "import ";
        str += impor + " ";
        str += "as ";
        str += as;
        return str;
    }
}
