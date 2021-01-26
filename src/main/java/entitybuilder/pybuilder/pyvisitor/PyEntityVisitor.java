package entitybuilder.pybuilder.pyvisitor;

import entitybuilder.pybuilder.PyConstantString;
import parser.parsepy.Python3BaseVisitor;
import parser.parsepy.Python3Parser;
import util.Configure;

public class PyEntityVisitor extends Python3BaseVisitor<String> {
    private String fileFullPath;
    private PyProcessTask processTask = new PyProcessTask();
    private PyContextHelper contextHelper = new PyContextHelper();

    private int moduleId = -1;
    private int classId = -1;
    private int functionId = -1;
    private int leftVarId = -1;

    private String classDecoration = ""; // once the classentity forms, clear it
    private String methodDecoration = ""; //once the methodentity forms, clear it

    public PyEntityVisitor(String fileFullPath) {
        this.fileFullPath = fileFullPath;

        //the directory of this file is a package
        if(contextHelper.isInitFile(fileFullPath)) {
            //its parent is a package or none. after finishing all packages, we should set the parentId for each package
            //save into singlecollection.entities.
            int packageId = processTask.processPackage(fileFullPath);
            moduleId = processTask.processModule(fileFullPath);
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

        if(contextHelper.isOneComStmAtTopLevel(ctx) && moduleId != -1
                || (moduleId != -1 && functionId == -1 && classId == -1) ) {
            classId = processTask.processClass(moduleId, className, baseStrs);
            classDecoration = "";
        }
        else { //process inner class
            if(classId !=-1 && functionId == -1) {
                processTask.processClass(classId, className, baseStrs);
            }
            else if(functionId != -1 && classId == -1) {
                processTask.processClass(functionId, className, baseStrs);
            }

            classDecoration = "";
            return str;
        }
        //visit class body
        if(ctx.suite() != null) {
            visitSuite(ctx.suite());
        }
        if(classId != -1) {
            processTask.supplementInitMethod(classId);
            classId = -1;
        }


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
            if(ctx.suite() != null) {
                visitSuite(ctx.suite());
            }
            functionId = -1;
        }
        //function in module, top if, for...
        else if(moduleId != -1 && classId == -1 && functionId == -1) {
            functionId = processTask.processFunction(moduleId, functionName, paraStrs);
            if(ctx.suite() != null) {
                visitSuite(ctx.suite());
            }
            functionId = -1;
        }

        //nested function, we treated nested function'a parent as function
        else if(moduleId != -1 && functionId != -1) {
            processTask.processFunction(functionId, functionName, paraStrs);
        }

        // a class method, class static method, or instance method
        else if(classId != -1 && functionId == -1) {
            functionId = processTask.processMethod(methodDecoration, classId, functionName, paraStrs);
            if(ctx.suite() != null) {
                visitSuite(ctx.suite());
            }
            functionId = -1;
        }
        methodDecoration = "";


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
        if(!str.equals(PyConstantString.IF_NAME)) {
            furtherVisitAtomExpr(str, ctx);
        }
        return str;
    }

    /**
     * process atom_expr, it maybe var name, callee, localname,....
     * @param str
     * @param ctx
     */
    private void furtherVisitAtomExpr(String str, Python3Parser.Atom_exprContext ctx) {
        //get code line number
        int lineno = ctx.getStart().getLine();
        //if it is "", it must bse literal string, number, [...], (...), none, true, false,..
        if(!str.equals(Configure.NULL_STRING)) {
            String location = "right";
            //String usage = PyConstantString.NAME_USAGE_USE; //default usage
            boolean isLeftAssign = contextHelper.isAtomExprInLeftAssignment(ctx);
            boolean isLeftAugAssign = contextHelper.isAtomExprInLeftAugassignment(ctx);
            //System.out.println(str + ", isLeftAssignment= " +  isLeftAssign + "; isLeftAugAssign= " + isLeftAugAssign);
            if(isLeftAssign || isLeftAugAssign) {
                //location = PyConstantString.NAME_USAGE_SET;
                location = "left";
            }
            int nameId = processTask.processAtomExpr(isLeftAssign, moduleId, classId, functionId, str, location, lineno);
            //System.out.println(str + " " + nameId);
            //the following is for post-processing the existed leftVar with rightValue
            if(isLeftAssign) {
                leftVarId = nameId;
            }
            else {
                boolean isRightAssign = contextHelper.isAtomExprInRightAssignment(ctx);
                if (isRightAssign && leftVarId != -1) {
                    processTask.processRightAssignValue(str, leftVarId);
                }
            }
        }
    }

    /**
     * testlist_star_expr_equaassign:
                  testlist_star_expr_leftassign
                  ('=' (yield_expr  |   testlist_star_expr_rightassign))*;

     * @param ctx
     * @return
     */
    @Override
    public String visitTestlist_star_expr_equaassign(Python3Parser.Testlist_star_expr_equaassignContext ctx) {
        super.visitTestlist_star_expr_equaassign(ctx);
        leftVarId = -1;
        return "";
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
        str += Configure.LEFT_PARENTHESES;
        if(ctx != null && ctx.arglist() != null) {
            str += visitArglist(ctx.arglist());
        }
        str += Configure.RIGHT_PARENTHESES;
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


    /**
     * grammar:
     * if_stmt: 'if' test ':' suite ('elif' test ':' suite)* ('else' ':' suite)?;
     * @param ctx
     * @return
     */
    @Override
    public String visitIf_stmt(Python3Parser.If_stmtContext ctx) {
        boolean isMainFunc = false;
        String str = "";
        if(ctx == null) {
            return str;
        }
        if(ctx.test() != null) {
            str = visitTest(ctx.test(0)); //if test
        }
        if(contextHelper.isOneComStmAtTopLevel(ctx)
                && str.equals(PyConstantString.IF_NAME)) {
            functionId = processTask.processFunction(moduleId, PyConstantString.MAIN_NAME, "");
            isMainFunc = true;
        }

        if(ctx.test() != null) {
            for (int i = 1; i < ctx.test().size(); i++) {
                visitTest(ctx.test(i));
            }
        }
        if(ctx.suite() != null) {
            for (Python3Parser.SuiteContext suiteContext : ctx.suite()) {
                visitSuite(suiteContext);
            }
        }
        if(isMainFunc) {
            functionId = -1;
        }
        return str;
    }

    /**
     * return firstExpr value
     * comparison: expr (comp_op expr)*;
     * @param ctx
     * @return
     */
    @Override
    public String visitComparison(Python3Parser.ComparisonContext ctx) {
        String firstExprStr = "";
        if(ctx == null) {
            return firstExprStr;
        }
        if(ctx.expr() != null) {
            if(!ctx.expr().isEmpty()) {
                firstExprStr = visitExpr(ctx.expr(0));
            }
        }
        if(ctx.expr() != null) {
            for (int i = 1; i < ctx.expr().size(); i++) {
                visitExpr(ctx.expr(i));
            }
        }
        if(ctx.comp_op() != null) {
            for (int i = 0; i < ctx.comp_op().size(); i++) {
                visitComp_op(ctx.comp_op(i));
            }
        }
        return firstExprStr;
    }


    /**
     *
     import_from: ('from' (    ('.' | '...')* dotted_name        |         ('.' | '...')+      )
                 'import' ('*' | '(' import_as_names ')' | import_as_names));
     */
    @Override
    public String visitImport_from(Python3Parser.Import_fromContext ctx) {
        String from = "";
        String importStr = "";
        String str = "";

        if(ctx == null) {
            return str;
        }
        if(ctx.dotted_name() != null) {
            from = visitDotted_name(ctx.dotted_name());
        }
        if(ctx.children.get(3).getText().equals(Configure.STAR)) {
            importStr = Configure.STAR;
        }
        if(ctx.import_as_names() != null) {
            importStr = visitImport_as_names(ctx.import_as_names());
        }
        if(functionId != -1) {
            processTask.processFromImport(from, importStr, functionId);
        }
        else {
            //System.out.println("process in from module: " + importStr);
            processTask.processFromImport(from, importStr, moduleId);
        }
        //System.out.println("import " + from + ":" +  importStr);
        return str;
    }

    /**
     * import_as_names: import_as_name (',' import_as_name)* (',')?;
     * @param ctx
     * @return
     */
    @Override
    public String visitImport_as_names(Python3Parser.Import_as_namesContext ctx) {
        String str = "";
        if(ctx == null) {
            return str;
        }
        if(ctx.import_as_name() != null && !ctx.import_as_name().isEmpty()) {
            str = visitImport_as_name(ctx.import_as_name(0));
            for (int i = 1; i < ctx.import_as_name().size(); i ++) {
                str += Configure.COMMA;
                str += visitImport_as_name(ctx.import_as_name(i));
            }
        }
        return str;
    }

    /**
     * import_as_name: NAME ('as' NAME)?;
     * x;y
     * @param ctx
     * @return
     */
    @Override
    public String visitImport_as_name(Python3Parser.Import_as_nameContext ctx) {
        String str = "";
        if(ctx == null) {
            return str;
        }
        if(ctx.NAME() != null && !ctx.NAME().isEmpty()) {
            str += ctx.NAME(0);
            if(ctx.NAME().size() > 1) {
                str += Configure.SEMICOLON;
                str += ctx.NAME(1);
            }
        }
        return str;
    }

    /**
     * import_stmt: import_name | import_from;
     * import_name: 'import' dotted_as_names;
     * @param ctx
     * @return
     */
    @Override
    public String visitImport_name(Python3Parser.Import_nameContext ctx) {
        String str = "";
        if(ctx == null) {
            return str;
        }
        if(ctx.dotted_as_names() != null) {
            str = visitDotted_as_names(ctx.dotted_as_names());
            if(functionId != -1) {
                //System.out.println("process in function: " + str);
                processTask.processImportName(str, functionId);
            }
            else {
                //System.out.println("process in module: " + str);
                processTask.processImportName(str, moduleId);
            }
            //System.out.println("import " +  str);
        }
        return str;
    }

    /**
     * dotted_as_names: dotted_as_name (',' dotted_as_name)*;
     * @param ctx
     * @return x;y, m;n, z,l
     */
    @Override
    public String visitDotted_as_names(Python3Parser.Dotted_as_namesContext ctx) {
        String str = "";
        if(ctx == null) {
            return str;
        }
        if(ctx.dotted_as_name() == null) {
            return str;
        }
        if(ctx.dotted_as_name().isEmpty()) {
            return str;
        }

        str += visitDotted_as_name(ctx.dotted_as_name(0));
        for (int i = 1; i < ctx.dotted_as_name().size(); i ++) {
            str += (Configure.COMMA + visitDotted_as_name(ctx.dotted_as_name(i)));
        }
        return str;
    }

    /**
     * dotted_as_name: dotted_name ('as' NAME)?;
     * @param ctx
     * @return xx;yy (import xx as yy)
     */
    @Override
    public String visitDotted_as_name(Python3Parser.Dotted_as_nameContext ctx) {
        String str = "";
        if(ctx == null) {
            return str;
        }
        if(ctx.dotted_name() != null) {
            str += visitDotted_name(ctx.dotted_name());
        }
        if(ctx.NAME() != null) {
            str += (Configure.SEMICOLON + ctx.NAME().getText());
        }
        return str;
    }





}
