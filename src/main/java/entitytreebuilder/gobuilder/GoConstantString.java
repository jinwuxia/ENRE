package entitytreebuilder.gobuilder;

public class GoConstantString {

    public static final String STRUCT_FIELD_IS_ANONYMOUS = "ANONYMOUS";
    public static final String INTERFACE_FIELD_IS_TYPE =  "TYPE";
    public static final String INTERFACE_FIELD_IS_METHOD = "METHOD";

    public static final String STRUCT_TYPE = "STRUCT";
    public static final String INTERFACE_TYPE = "INTERFACE";
    public static final String UNKNOWN_TYPE = "UNKNOWN_TYPE";
    public static final String CUSTOME_TYPE = "CUSTOM_TYPE";

    public static final String ENTITY_STRUCT= "STRUCT";
    public static final String ENTITY_INTERFACE = "INTERFACE";
    public static final String ENTITY_STRUCT_ALIAS = "RECEIVER";
    public static final String ENTITY_FUNCTION = "FUNCTION";
    public static final String ENTITY_METHOD= "METHOD";

    public static final String SAVE_TYPE_PARAMETER = "Parameter";
    public static final String SAVE_TYPE_RETURN = "Return";

    public static final String OPERAND_NAME_USAGE_USE = "Use";
    public static final String OPERAND_NAME_USAGE_SET = "Set";

    public static final String OPERAND_NAME_ROLE_PKG = "Package";
    public static final String OPERAND_NAME_ROLE_FUN = "Function";
    public static final String OPERAND_NAME_ROLE_PAR = "Parameter";
    public static final String OPERAND_NAME_ROLE_RET = "Return";
    public static final String OPERAND_NAME_ROLE_REC = "Receiver";
    public static final String OPERAND_NAME_ROLE_GLO_VAR = "GlobalVar";
    public static final String OPERAND_NAME_ROLE_LOC_VAR = "LocalVar";
    public static final String OPERAND_NAME_ROLE_UNKNOWN = "Unknown";

    //kinds of local block, which are inside function/method body.
    public static final String LOCAL_BLOCK_FUNCTION = "FunctionBlock";
    public static final String LOCAL_BLOCK_UNNAMED_BLOCK = "UnknownBlock";
    public static final String LOCAL_BLOCK_FOR = "ForBlock";
    public static final String LOCAL_BLOCK_IF = "IfBlock";
    public static final String LOCAL_BLOCK_ELSE = "ElseBlock";
    public static final String LOCAL_BLOCK_SWITCH = "SwitchBlock";
    public static final String LOCAL_BLOCK_SWITCH_CASE_CLAUSE = "SwitchCaseClauseBlock"; //include default
    public static final String LOCAL_BLOCK_SELECT = "SelectBlock";
    public static final String LOCAL_BLOCK_SELECT_CASE_CLAUSE = "SelectCaseClauseBlock"; //include default


    public static final String [] BUILTIN_TYPE = {
            "bool",
            "byte",
            "complex128",
            "complex64",
            "error",
            "float32",
            "float64",
            "int",
            "int16",
            "int32",
            "int64",
            "int8",
            "rune",
            "string",
            "uint",
            "uint16",
            "uint32",
            "uint64",
            "uint8",
            "uintptr"
    };


    public static final String [] BUILTIN_FUNCTION = {
            "append",
            "cap",
            "close",
            "complex",
            "copy",
            "delete",
            "imag",
            "len",
            "make",
            "new",
            "panic",
            "print",
            "println",
            "real",
            "recover"
    };

    public static final String[] BUILTIN_CONSTANTS = {
            "true",
            "false",
            "iota",
            "nil"
    };


    public static final String NIL = "nil";
    public static final String ANY_RETURN = "interface{}";
    public static final String MAP = "map";
    public static final String SCOPE_ONE = "Scope_1";


    public static final String STRING_IF = "if";
    public static final String STRING_ELSE = "else";
    public static final String STRING_STRUCT = "struct";
    public static final String INTERFACE = "interface";
    public static final String SELECT = "select";
    public static final String STRING_FOR = "for";
    public static final String STRING_SWITCH = "switch";
    public static final String STRING_FUNC = "func";

}
