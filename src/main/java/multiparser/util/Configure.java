package multiparser.util;

public class Configure {
    private Configure() {}

    public static final String GO_LANG = "golang";
    public static final String PYTHON_LANG = "python";
    public static final String GO_PRO_SUFFIX = ".go";
    public static final String PY_PRO_SUFFIX = ".py";

    private static Configure configure = new Configure();
    public static Configure getConfigureInstance() {
        return configure;
    }



    private String  inputSrcPath = "../../../../go-workspace/beego-master";
    private String  usageSrcPath = "github.com/astaxie/beego";
    private String  analyzedProjectName = "beego";
    private String  lang = "golang";

    private String  outputJsonFile = analyzedProjectName  + "_dep.json";
    private String  outputXmlFile = analyzedProjectName + "_dep.xml";
    private String  attributeName = analyzedProjectName + "-sdsm";
    private String  schemaVersion = "1.0";

    public void setDefault() {
        outputJsonFile = analyzedProjectName  + "_dep.json";
        outputXmlFile = analyzedProjectName + "_dep.xml";
        attributeName = analyzedProjectName + "-sdsm";
    }

    public String getInputSrcPath() {
        return inputSrcPath;
    }

    public void setInputSrcPath(String inputSrcPath) {
        this.inputSrcPath = inputSrcPath;
    }

    public String getUsageSrcPath() {
        return usageSrcPath;
    }

    public void setUsageSrcPath(String usageSrcPath) {
        this.usageSrcPath = usageSrcPath;
    }

    public String getAnalyzedProjectName() {
        return analyzedProjectName;
    }

    public void setAnalyzedProjectName(String analyzedProjectName) {
        this.analyzedProjectName = analyzedProjectName;
    }

    public String getOutputJsonFile() {
        return outputJsonFile;
    }

    public void setOutputJsonFile(String outputJsonFile) {
        this.outputJsonFile = outputJsonFile;
    }

    public String getOutputXmlFile() {
        return outputXmlFile;
    }

    public void setOutputXmlFile(String outputXmlFile) {
        this.outputXmlFile = outputXmlFile;
    }

    public void setSchemaVersion(String schemaVersion) {
        this.schemaVersion = schemaVersion;
    }

    public String getSchemaVersion() {
        return schemaVersion;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }
}

