package entitybuilder.pybuilder;

public class PyConstantString {

    public static final String DOT_PY = "\\.py";
    public static final String INIT_FILE_NAME = "__init__.py";
    public static final String INIT_MODULE_NAME = "__init__";
    public static final String CLASS_STATIC_METHOD = "staticmethod";
    public static final String CLASS_METHOD = "classmethod";
    public static final String INIT_METHOD_NAME = "__init__";
    public static final String SELF_DOT = "self.";
    public static final String SELF = "self";
    public static final String IF_NAME = "__name__";
    public static final String MAIN_NAME = "__main__";
    public static final String CLASS_METHOD_CLS_PARAMETER = "cls";
    public static final String SUPER = "super";

    public static final String CUSTOM_PRE = "CUSTOM_PRE";

    public static final String COMMENT = "'''";
    public static final String STRING_LETERAL_1 = "\"";
    public static final String STRING_LETERAL_2  = "'";

    public static final String NAME_USAGE_USE = "Use";
    public static final String NAME_USAGE_SET = "Set";


    public static final String ENTITY_CLASS_METHOD = "class_method";
    public static final String ENTITY_CLASS_STATIC_METHOD = "class_static_method";
    public static final String ENTITY_INST_METHOD = "instance_method";
    public static final String ENTITY_FUNCTION = "function";


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


    public static String[] BUILT_IN_FUNCTIONS = {
            "abs",	"delattr",	"hash",	"memoryview",	"set",
            "all",	"dict",	"help",	"min",	"setattr",
            "any",	"dir",	"hex",	"next",	"slice",
            "ascii",	"divmod",	"id",	"object",	"sorted",
            "bin",	"enumerate",	"input",	"oct",	"staticmethod",
            "bool",	"eval",	"int",	"open",	"str",
            "breakpoint",	"exec",	"isinstance",	"ord",	"sum",
            "bytearray",	"filter",	"issubclass",	"pow",	"super",
            "bytes",	"float",	"iter",	"print",	"tuple",
            "callable", "format",	"len",	"property",	"type",
            "chr",	"frozenset",	"list",	"range",	"vars",
            "classmethod",	"getattr",	"locals",	"repr",	"zip",
            "compile",	"globals",	"map",	"reversed",	"__import__",
            "complex",	"hasattr",	"max",	"round"
};

}
