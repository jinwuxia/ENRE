package multiparser.util;

public class Configure {
    private Configure() {}

    public static final String WINDOWS = "Windows";
    public static final String LINUX = "linux";
    public static final String MAC = "mac";

    public static final String NULL_STRING = "";
    public static final String COMMA = ",";
    public static final String LEFT_PARENTHESES = "(";
    public static final String RIGHT_PARENTHESES = ")";
    public static final String DOT = ".";
    public static final String SEMICOLON = ";";
    public static final String STAR = "*";
    public static final String POINTER = "*";
    public static final String ONE_SPACE_STRING = " ";
    public static final String SEMI_COLON = ";";
    public static final String BLANK_IDENTIFIER = "_";
    public static final String SQUARE_BRACKETS = "[]";
    public static final String LEFT_SQUARE_BRACKET = "[";
    public static final String RIGHT_SQUARE_BRACKET = "]";
    public static final String ELLIPSIS = "...";
    public static final String LEFT_CURLY_BRACE = "{";
    public static final String RIGHT_CURLY_BRACE = "}";
    public static final String STRING_COLON = ":";
    public static final String EQUAL = "=";




    public static final String GO_LANG = "golang";
    public static final String PYTHON_LANG = "python";
    public static final String GO_PRO_SUFFIX = ".go";
    public static final String PY_PRO_SUFFIX = ".py";

    private static Configure configure = new Configure();
    public static Configure getConfigureInstance() {
        return configure;
    }



    private String  inputSrcPath;
    private String  usageSrcPath;
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

