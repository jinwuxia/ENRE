package multiparser.py3extractor;

import multiparser.py3extractor.antlr4.Python3BaseVisitor;
import multiparser.py3extractor.antlr4.Python3Parser;

public class PyEntityVisitor extends Python3BaseVisitor<String> {
    private String fileFullPath;
    private PyProcessTask processTask = new PyProcessTask();
    private PyContextHelper contextHelper = new PyContextHelper();

    private int moduleId = -1;
    private int classId = -1;
    private int functionId = -1;

    private String classDecoration = ""; // once the classentity forms, clear it
    private String methodDecoration = ""; //once the methodentity forms, clear it

    public PyEntityVisitor(String fileFullPath) {
        this.fileFullPath = fileFullPath;

        //the directory of this file is a package
        if(contextHelper.isInitFile(fileFullPath)) {
            //its parent is a package or none. after finishing all packages, we should set the parentId for each package
            //save into singlecollection.entities.
            int packageId = processTask.processPackage(fileFullPath);
        }
        //this file is a module
        else {
            //its parent is a package or none. after finishing all files, we should set the parentId for each module
            //save into singlecollection.entities.
            moduleId = processTask.processModule(fileFullPath);
        }
    }


    /**
     * save classEntity with parentId, baseClassNames
     * grammar: classdef: 'class' NAME ('(' (arglist)? ')')? ':' suite;
     * @param ctx
     * @return
     */
    @Override
    public String visitClassdef(Python3Parser.ClassdefContext ctx) {
        String str = "";

        String className = ctx.NAME().getText();
        String baseStrs = "";
        //baseClass
        if(ctx.arglist() != null) {
            baseStrs = visitArglist(ctx.arglist());
        }

        if(contextHelper.isOneComStmAtTopLevel(ctx) && moduleId != -1) {
            classId = processTask.processClass(moduleId, className, baseStrs);
            classDecoration = "";
        }
        else {
            //classId = processTask.processClass(blockId, className, baseStrs);
            //classDecoration = "";
        }
        //visit class body
        if(ctx.suite() != null) {
            visitSuite(ctx.suite());
        }
        classId = -1;

        str += ("class " + className + "(" + baseStrs + ")");
        return str;
    }

    /**
     * grammar: funcdef: 'def' NAME parameters ('->' test)? ':' suite;
     * @param ctx
     * @return
     */
    @Override
    public String visitFuncdef(Python3Parser.FuncdefContext ctx) {
        String functionName = ctx.NAME().getText();
        String paraStrs = "";
        if(ctx.parameters() != null) {
            paraStrs = visitParameters(ctx.parameters());
        }
        if(ctx.test() != null) {
            visitTest(ctx.test());
        }

        //a top-level function
        if(contextHelper.isOneComStmAtTopLevel(ctx) && moduleId != -1 && classId == -1) {
            functionId = processTask.processFunction(moduleId, functionName, paraStrs);
        }
        // a class method, class static method, or instance method
        else if(classId != -1) {
            functionId = processTask.processMethod(methodDecoration, classId, functionName, paraStrs);
        }
        methodDecoration = "";

        if(ctx.suite() != null) {
            visitSuite(ctx.suite());
        }
        functionId = -1;

        String str = "";
        str += ("def" + functionName + paraStrs);
        return str;
    }


    /**
     * decorated: decorators (classdef | funcdef | async_funcdef);
     * @param ctx
     * @return
     */
    @Override
    public String visitDecorated(Python3Parser.DecoratedContext ctx) {
        String str = "";
        if(ctx == null) {
            return str;
        }
        if(ctx.decorators() != null) {
            str =  visitDecorators(ctx.decorators());
        }
        if(ctx.classdef() != null) {
            classDecoration = str;
            visitClassdef(ctx.classdef());
        }
        if(ctx.funcdef() != null) {
            methodDecoration = str;
            visitFuncdef(ctx.funcdef());
        }
        if(ctx.async_funcdef() != null) {
            visitAsync_funcdef(ctx.async_funcdef());
        }

        return str;
    }

    /**
     * decorators: decorator+;
     * @param ctx
     * @return
     */
    @Override
    public String visitDecorators(Python3Parser.DecoratorsContext ctx) {
        String str = "";
        if(ctx == null) {
            return str;
        }
        if(ctx.decorator() != null && !ctx.decorator().isEmpty()) {
            str += visitDecorator(ctx.decorator(0));
            for (int i = 1; i < ctx.decorator().size(); i++) {
                str += ",";
                str += visitDecorator(ctx.decorator(i));
            }
        }
        //System.out.println("visitDecorators: " + str);
        return str;
    }

    /**
     * decorator: '@' dotted_name ( '(' (arglist)? ')' )? NEWLINE;
     * @param ctx
     * @return
     */
    @Override
    public String visitDecorator(Python3Parser.DecoratorContext ctx) {
        String str = "";
        if(ctx == null) {
            return str;
        }
        if(ctx.dotted_name() != null) {
            str =  visitDotted_name(ctx.dotted_name());
        }
        if(ctx.arglist() != null) {
            visitArglist(ctx.arglist());
        }
        return str;
    }

    /**
     * dotted_name: NAME ('.' NAME)*;
     * @param ctx
     * @return
     */
    @Override
    public String visitDotted_name(Python3Parser.Dotted_nameContext ctx) {
        String str = "";
        if(ctx != null && ctx.NAME() != null && !ctx.NAME().isEmpty()) {
            str += ctx.NAME(0).getText();
            for (int i = 1; i < ctx.NAME().size(); i++) {
                str += ".";
                str += ctx.NAME(i).getText();
            }
        }
        return str;
    }

    /**
     * classdef: 'class' NAME ('(' (arglist)? ')')? ':' suite;
     * funcdef: 'def' NAME parameters ('->' test)? ':' suite;
     * if_stmt, while_stmt, try_stmt,for_stmt....
     * @param ctx
     * @return
     */
    @Override
    public String visitSuite(Python3Parser.SuiteContext ctx) {
        /*if (ctx == null) {
            return str;
        }

        //process class suite
        if (contextHelper.isSuiteInClass(ctx)) {

        }
        //process func suite
        else if (contextHelper.isSuiteInFunc(ctx)) {
            if(processTask.isInitMethod(functionId)) {
                //init method, need to save the instance's variable

            }
            else { //regular function

            }

        }
        //process if,for,while, try suite..
        else{
        }
        */

        return super.visitSuite(ctx);
    }

    /**
     * grammar: arglist: argument (',' argument)*  (',')?;
     * @param ctx
     * @return
     */
    @Override
    public String visitArglist(Python3Parser.ArglistContext ctx) {
        String str = "";
        if(ctx.argument() != null && !ctx.argument().isEmpty()) {
            str += visitArgument(ctx.argument(0));
            for (int i = 1; i < ctx.argument().size(); i++) {
                str += ",";
                str += visitArgument(ctx.argument(i));
            }
        }
        return str;
    }

    /**
     * grammar: atom_expr: (AWAIT)? atom trailer*;
     * trailer:
     '(' (arglist)? ')'         #arglisttrailer
     | '[' subscriptlist ']'    #subscriptlisttrailer
     | '.' NAME                 #attributetrailer
     * @param ctx
     * @return
     */
    @Override
    public String visitAtom_expr(Python3Parser.Atom_exprContext ctx) {
        String str = "";
        if(ctx == null) {
            return str;
        }
        if(ctx.AWAIT() != null) {
            str += ctx.AWAIT().getText();
            str += " ";
        }
        //only get name, not includes literal tring, number, [...], (...), none, true, false,..
        if(ctx.atom() != null) {
            str += visitAtom(ctx.atom());
        }
        if(ctx.trailer() != null && !ctx.trailer().isEmpty()) {
            for (Python3Parser.TrailerContext trailerContext : ctx.trailer()) {
                if(trailerContext instanceof Python3Parser.AttributetrailerContext) {
                    str += visitAttributetrailer((Python3Parser.AttributetrailerContext) trailerContext);
                }
                else if (trailerContext instanceof Python3Parser.ArglisttrailerContext){
                    str += visitArglisttrailer((Python3Parser.ArglisttrailerContext) trailerContext);
                }
                else if (trailerContext instanceof  Python3Parser.SubscriptlisttrailerContext) {
                    str += visitSubscriptlisttrailer((Python3Parser.SubscriptlisttrailerContext) trailerContext);
                }
            }
        }

        //if it is "", it must bse literal string, number, [...], (...), none, true, false,..
        if(!str.equals(ConstantString.NULL_STRING)) {
            String usage = ConstantString.NAME_USAGE_USE; //default usage
            boolean isLeftAssign = contextHelper.isAtomExprInLeftAssignment(ctx);
            boolean isLeftAugAssign = contextHelper.isAtomExprInLeftAugassignment(ctx);
            //System.out.println(str + ", isLeftAssignment= " +  isLeftAssign + "; isLeftAugAssign= " + isLeftAugAssign);
            if(isLeftAssign || isLeftAugAssign) {
                usage = ConstantString.NAME_USAGE_SET;
            }
            processTask.processAtomExpr(isLeftAssign, moduleId, classId, functionId, str, usage);
        }
        return str;
    }

    /** only returns the name,
     * if a number, string, none, true, false, it will not return
     *
     * atom: ('(' (yield_expr|testlist_comp)? ')'
     * |'[' (testlist_comp)? ']'
     * | '{' (dictorsetmaker)? '}'
     * | NAME | NUMBER | STRING+ | '...' | 'None' | 'True' | 'False');
     * @param ctx
     * @return
     */
    @Override
    public String visitAtom(Python3Parser.AtomContext ctx) {
        String str = "";
        if(ctx == null) {
            return str;
        }
        if(ctx.yield_expr() != null) {
            visitYield_expr(ctx.yield_expr());
        }
        if(ctx.testlist_comp() != null) {
            visitTestlist_comp(ctx.testlist_comp());
        }
        if(ctx.dictorsetmaker() != null) {
            visitDictorsetmaker(ctx.dictorsetmaker());
        }
        if(ctx.NAME() != null) {
            str += ctx.NAME().getText();
        }
        return str;
    }

    /**
     * trailer:
     '(' (arglist)? ')'         #arglisttrailer
     | '[' subscriptlist ']'    #subscriptlisttrailer
     | '.' NAME                 #attributetrailer
     ;
     * @param ctx
     * @return
     */
    @Override
    public String visitAttributetrailer(Python3Parser.AttributetrailerContext ctx) {
        String str = "";
        if(ctx.NAME() != null) {
            str += ".";
            str += ctx.NAME().getText();
        }
        return str;
    }

    /**
     * trailer:
     '(' (arglist)? ')'         #arglisttrailer
     | '[' subscriptlist ']'    #subscriptlisttrailer
     | '.' NAME                 #attributetrailer
     * @param ctx
     * @return
     */
    @Override
    public String visitArglisttrailer(Python3Parser.ArglisttrailerContext ctx) {
        String str = "";
        str += ConstantString.LEFT_PARENTHESES;
        if(ctx != null && ctx.arglist() != null) {
            str += visitArglist(ctx.arglist());
        }
        str += ConstantString.RIGHT_PARENTHESES;
        return str;
    }


    /**
     * parameters: '(' (typedargslist)? ')';
     * @param ctx
     * @return
     */
    @Override
    public String visitParameters(Python3Parser.ParametersContext ctx) {
        String str = "(";
        if(ctx != null && ctx.typedargslist() != null) {
            str += visitTypedargslist(ctx.typedargslist());
        }
        str += ")";
        return str;
    }

    /**
     * //jwx:typedargslist is the argument list of a function
     typedargslist: (tfpdef ('=' test)? (',' tfpdef ('=' test)?)* (',' (
     '*' (tfpdef)? (',' tfpdef ('=' test)?)* (',' ('**' tfpdef (',')?)?)?
     | '**' tfpdef (',')?)?)?
     | '*' (tfpdef)? (',' tfpdef ('=' test)?)* (',' ('**' tfpdef (',')?)?)?
     | '**' tfpdef (',')?);

     tfpdef: NAME (':' test)?;
     * @param ctx
     * @return
     */
    @Override
    public String visitTypedargslist(Python3Parser.TypedargslistContext ctx) {
        String str = "";
        if(ctx == null) {
            return str;
        }
        //ignore test's content. test is the default value of an argument in a function
        if(ctx.tfpdef() != null && !ctx.tfpdef().isEmpty()) {
            str += ctx.tfpdef(0).NAME().getText();
            for (int i = 1; i < ctx.tfpdef().size(); i++) {
                str += ",";
                str += ctx.tfpdef(i).NAME().getText();
            }
        }
        if(ctx.test() != null && !ctx.test().isEmpty()) {
            for (Python3Parser.TestContext testContext : ctx.test()) {
                visitTest(testContext);
            }
        }

        return str;
    }


}
