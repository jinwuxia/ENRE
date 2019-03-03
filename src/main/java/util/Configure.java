package util;

import java.io.File;

public class Configure {

    public static final String RELATION_IMPLICIT_P= "P";
    public static final String RELATION_IMPLICIT_P1 = "P1";
    public static final String RELATION_IMPLICIT_P2 = "P2";
    public static final String RELATION_IMPLICIT_P3 = "P3";
    public static final String RELATION_IMPLICIT_P4 = "P4";
    public static final String RELATION_IMPLICIT_P5 = "P5";
    public static final String RELATION_IMPLICIT_P6 = "P6";
    public static final String RELATION_IMPLICIT_P7 = "P7";
    public static final String RELATION_IMPLICIT_P8 = "P8";
    public static final String RELATION_IMPLICIT_P9 = "P9";
    public static final String RELATION_IMPLICIT_P10 = "P10";
    public static final String RELATION_IMPLICIT_P11 = "P>10";
    private Configure() {}

    public static final String RELATION_IMPLICIT_ALL = "implicit";
    public static final String RELATION_IMPLICIT_ALLED = "implicited_";


    public static final String EXPRESSION_CALL = "call";
    public static final String EXPRESSION_SET = "set";
    public static final String EXPRESSION_USE = "use";
    public static final String EXPRESSION_DOT = "dot";


    public static final String PRE_SUPER = "super";
    //expression atom resolve type
    public static final String RESOLVED_TYPE_SUPER = "super";
    public static final String RESOLVED_TYPE_BUILTIN = "builtin";
    public static final String RESOLVED_TYPE_REGULAR = "regular";
    public static final String RESOLVED_TYPE_LIBRARY = "library";
    public static final String RESOLVED_TYPE_IMPLICIT = "implicit";
    public static final String RESOLVED_TYPE_IMPLICIT_POSSIBLE = "implicit_possible";
    public static final String RESOLVED_TYPE_IMPLICIT_REFINE = "implicit_refine";
    public static final String RESOLVED_TYPE_IMPLICIT_ABSTRACT = "implicit_abstract";
    public static final String RESOLVED_TYPE_IMPLICIT_EXTENSION = "implicit_extension";
    public static final String RESOLVED_TYPE_UNKNOWN = "unknown";



    public static final String WINDOWS = "windows";
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

    public static final String EXTERNAL_DATA_SOURCE = "datasource";

    public static final String GO_PRO_SUFFIX = ".go";
    public static final String PY_PRO_SUFFIX = ".py";
    public static final String OS_DOT_NAME = "os.name";


    public static final String BASIC_ENTITY_FUNCTION = "function";
    public static final String BASIC_ENTITY_CLASS = "class";
    public static final String BASIC_ENTITY_FILE = "file";
    public static final String BASIC_ENTITY_FOLDER = "folder";
    public static final String BASIC_ENTITY_VARIABLE = "variable";
    public static final String IMPLICIT_DEPENDENCY = "Implicit";
    public static final String EXPLICIT_DEPENDENCY = "Explicit";

    // struct->method
    public static final String RELATION_RECEIVED_BY = "Received by";
    public static final String RELATION_RECEIVE = "Receive";

    //structType/aliasType->interface
    public static final String RELATION_IMPLEMENT = "Implement";
    public static final String RELATION_IMPLEMENTED_BY = "Implemented by";

    //file->package
    public static final String RELATION_IMPORT = "Import";
    public static final String RELATION_IMPORTED_BY = "Imported by";

    //function-function; function->method; method->function; method->method
    public static final String RELATION_CALL = "Call";
    public static final String RELATION_CALLED_BY = "Called by";

    public static final String RELATION_IMPLICIT_INTERNAL_CALL = "Nternal Implicit Call";
    public static final String RELATION_IMPLICIT_EXTERNAL_CALL = "External Implicit Call";

    public static final String RELATION_DYNAMIC_TRACE_CLASS_CALL = "Dynamic Trace Call";

    //function/method->var
    public static final String RELATION_PARAMETER = "Parameter";
    public static final String RELATION_PARAMETERED_BY = "Parametered by";

    //function/method->var
    public static final String RELATION_RETURN = "Return";
    public static final String RELATION_RETURNED_BY = "Returned by";

    //function/method->OperandVar
    public static final String RELATION_SET = "Set";
    public static final String RELATION_SETED_BY = "Seted by";

    //function/method->OperandVar
    public static final String RELATION_USE = "Use";
    public static final String RELATION_USED_BY = "Used by";

    //class->class in python
    //struct-> struct , interface->interface in golang
    public static final String RELATION_INHERIT = "Inherit";
    public static final String RELATION_INHERITED_BY = "Inherited by";

    public static final String RELATION_EXTEND = "Extend";

    //struct1->struct2, interface1->interface2
    //public static final String RELATION_INHERIT = "Embed";
    //public static final String RELATION_INHERITED_BY = "Embeded by";

    public static final String RELATION_LEVEL_FILE = "File";
    public static final String RELATION_LEVEL_FUNCTION = "FUNCTION";

    private static Configure configure = new Configure();
    public static Configure getConfigureInstance() {
        return configure;
    }



    private String inputSrcPath;
    private String usageSrcPath;
    private String analyzedProjectName = "beego";
    private String lang = "golang";
    private String curr_pro_suffix = ".go";

    private String outputDotFile = analyzedProjectName + ".dot";
    private String outputCsvNodeFile = analyzedProjectName + "_node.csv";
    private String outputCsvEdgeFile = analyzedProjectName + "_edge.csv";
    private String outputJsonFile = analyzedProjectName  + "_dep.json";
    private String outputXmlFile = analyzedProjectName + "_dep.xml";
    private String attributeName = analyzedProjectName + "-sdsm";
    private String schemaVersion = "1.0";


    public void setDefault() {
        outputJsonFile = analyzedProjectName  + "_dep.json";
        outputDotFile = analyzedProjectName + ".dot";
        outputXmlFile = analyzedProjectName + "_dep.xml";
        outputCsvNodeFile = analyzedProjectName + "_node.csv";
        outputCsvEdgeFile = analyzedProjectName + "_edge.csv";
        attributeName = analyzedProjectName + "-sdsm";
    }

    public String getInputSrcPath() {
        return inputSrcPath;
    }

    /**
     * used by golang
     * @return
     */
    public String getUnifiedInputSrcpath() {
        return StringUtil.unifyPath(inputSrcPath);
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

    /**
     * os-relevant
     * @param analyzedProjectName
     */
    public void setAnalyzedProjectName(String analyzedProjectName) {
        new File(analyzedProjectName).mkdir();
        if(OsUtil.isWindows()) {
            this.analyzedProjectName = analyzedProjectName + "\\" + analyzedProjectName;
        }
        if(OsUtil.isMac() || OsUtil.isLinux()) {
            this.analyzedProjectName = analyzedProjectName + "/" + analyzedProjectName;
        }
    }


    public String getOutputJsonFile() {
        return outputJsonFile;
    }

    public String getOutputCsvEdgeFile() {
        return outputCsvEdgeFile;
    }

    public String getOutputCsvNodeFile() {
        return outputCsvNodeFile;
    }


    public String getOutputXmlFile() {
        return outputXmlFile;
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


    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;

        if(lang.equals(GO_LANG)) {
            curr_pro_suffix = GO_PRO_SUFFIX;
        }
        else if(lang.equals(PYTHON_LANG)){
            curr_pro_suffix = PY_PRO_SUFFIX;
        }
    }

    public String getCurr_pro_suffix() {
        return curr_pro_suffix;
    }

}

