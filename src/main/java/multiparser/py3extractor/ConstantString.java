package multiparser.py3extractor;

public class ConstantString {


    public static final String NULL_STRING = "";
    public static final String COMMA = ",";
    public static final String LEFT_PARENTHESES = "(";
    public static final String RIGHT_PARENTHESES = ")";
    public static final String DOT = ".";
    public static final String SEMICOLON = ";";
    public static final String STAR = "*";

    public static final String DOT_PY = ".py";
    public static final String INIT_FILE_NAME = "__init__.py";
    public static final String CLASS_STATIC_METHOD = "staticmethod";
    public static final String CLASS_METHOD = "classmethod";
    public static final String INIT_METHOD_NAME = "__init__";
    public static final String SELF_DOT = "self.";
    public static final String IF_NAME = "__name__";
    public static final String MAIN_NAME = "__main__";

    public static final String COMMENT = "'''";
    public static final String STRING_LETERAL_1 = "\"";
    public static final String STRING_LETERAL_2  = "'";

    public static final String NAME_USAGE_USE = "Use";
    public static final String NAME_USAGE_SET = "Set";


    public static final String ENTITY_CLASS_METHOD = "class_method";
    public static final String ENTITY_CLASS_STATIC_METHOD = "class_static_method";
    public static final String ENTITY_INST_METHOD = "instance_method";
    public static final String ENTITY_FUNCTION = "function";


    //module/function->package/module/object
    public static final String RELATION_IMPORT = "Import";
    public static final String RELATION_IMPORTED_BY = "Imported by";

    //function-function; function->method; method->function; method->method
    public static final String RELATION_CALL = "Call";
    public static final String RELATION_CALLED_BY = "Called by";

    //class->class
    public static final String RELATION_INHERIT = "Inherit";
    public static final String RELATION_INHERITED = "Inherited by";


    public static final String [] KEYWORDS =  {
            "False",
            "class",
            "finally",
            "is",
            "return",
            "None",
            "continue",
            "for",
            "lambda",
            "try",
            "True",
            "def",
            "from",
            "nonlocal",
            "while",
            "and",
            "del",
            "global",
            "not",
            "with",
            "as",
            "elif",
            "if",
            "or",
            "yield",
            "assert",
            "else",
            "import",
            "pass",
            "break",
            "except",
            "in",
            "raise"
    };

}
