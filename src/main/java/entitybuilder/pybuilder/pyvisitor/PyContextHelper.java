package entitybuilder.pybuilder.pyvisitor;

import entitybuilder.pybuilder.PyConstantString;
import parser.parsepy.Python3Parser;
import org.antlr.v4.runtime.RuleContext;

public class PyContextHelper {

    public boolean isInitFile(String fileFullPath) {
        if(fileFullPath.endsWith(PyConstantString.INIT_FILE_NAME)) {
            return true;
        }
        return false;
    }


    /**
     * judge funcdef or classDef,... are at top level in a file
     * file_input: (NEWLINE | stmt)* EOF;
     * stmt: simple_stmt | compound_stmt;
     * compound_stmt: if_stmt | while_stmt | for_stmt | try_stmt | with_stmt
     *             | funcdef | classdef | decorated | async_stmt;
     * decorated: decorators (classdef | funcdef | async_funcdef);
     * @param ctx
     * @return
     */
    public boolean isOneComStmAtTopLevel(RuleContext ctx) {
        if(ctx != null
                && ctx.parent != null
                && ctx.parent.parent != null
                && ctx.parent.parent.parent != null
                && ctx.parent.parent.parent instanceof Python3Parser.File_inputContext) {
            return true;
        }
        if(ctx != null
                && ctx.parent != null && ctx.parent instanceof Python3Parser.DecoratedContext
                && ctx.parent.parent != null
                && ctx.parent.parent.parent != null
                && ctx.parent.parent.parent.parent != null
                && ctx.parent.parent.parent.parent instanceof Python3Parser.File_inputContext) {
            return true;
        }
        return false;
    }



    /**
     * decorated: decorators (classdef | funcdef | async_funcdef);
     * decorators: decorator+;
     * decorator: '@' dotted_name ( '(' (arglist)? ')' )? NEWLINE;
     *
     * judge the funcdef is under decoration or not
     * @param ctx
     * @return
     */
    public boolean isInDecorated(Python3Parser.FuncdefContext ctx) {
        if(ctx != null && ctx.parent != null && ctx.parent instanceof Python3Parser.DecoratedContext) {
            return true;
        }
        return false;
    }


    /**
     * judge the atom_expr is in left "="assignment or in left augassignment.
     *
     * expr_stmt: testlist_star_expr_annaassign=testlist_star_expr annassign
     | testlist_star_expr_augaassign=testlist_star_expr augassign (yield_expr|testlist)
     | testlist_star_expr_leftassign=testlist_star_expr ('=' (yield_expr|testlist_star_expr_rightassign=testlist_star_expr))*
     ;
     * @param ctx
     * @return
     */
    public boolean isAtomExprInLeft(Python3Parser.Atom_exprContext ctx) {
        if (isAtomExprInLeftAssignment(ctx)) {
            return true;
        }
        if(isAtomExprInLeftAugassignment(ctx)) {
            return true;
        }
        return false;
    }


    /**
     * expr_stmt: testlist_star_expr_annaassign
     | testlist_star_expr_augaassign
     | testlist_star_expr_equaassign
     ;
     testlist_star_expr_equaassign: testlist_star_expr_leftassign ('=' (yield_expr|testlist_star_expr_rightassign))*;
     testlist_star_expr_leftassign: testlist_star_expr;
     testlist_star_expr_rightassign: testlist_star_expr;

     * @param ctx
     * @return
     */
    public boolean isAtomExprInLeftAssignment(Python3Parser.Atom_exprContext ctx) {
        RuleContext testlistStarExpr = getTestliststarExprForAtomExpr(ctx);
        if(testlistStarExpr == null) {
            return false;
        }

        if(testlistStarExpr.parent != null
                && testlistStarExpr.parent instanceof Python3Parser.Testlist_star_expr_leftassignContext
                && testlistStarExpr.parent.parent != null
                && testlistStarExpr.parent.parent instanceof Python3Parser.Testlist_star_expr_equaassignContext
                && ((Python3Parser.Testlist_star_expr_equaassignContext) testlistStarExpr.parent.parent).testlist_star_expr_rightassign() != null) {
            return true;
        }
        return false;
    }


    /**
     * similar to above:  isAtomExprInLeftAssignment()
     * @param ctx
     * @return
     */
    public boolean isAtomExprInRightAssignment(Python3Parser.Atom_exprContext ctx) {
        RuleContext testlistStarExpr = getTestliststarExprForAtomExpr(ctx);
        if(testlistStarExpr == null) {
            return false;
        }

        if(testlistStarExpr.parent != null
                && testlistStarExpr.parent instanceof Python3Parser.Testlist_star_expr_rightassignContext) {
            return true;
        }
        return false;
    }


    /**
     *
     * expr_stmt: testlist_star_expr_annaassign
     | testlist_star_expr_augaassign
     | testlist_star_expr_equaassign
     ;
     testlist_star_expr_augaassign: testlist_star_expr augassign (yield_expr|testlist);
     * @param ctx
     * @return
     */
    public boolean isAtomExprInLeftAugassignment(Python3Parser.Atom_exprContext ctx) {
        RuleContext testlistStarExpr = getTestliststarExprForAtomExpr(ctx);
        if(testlistStarExpr == null) {
            return false;
        }

       if(testlistStarExpr.parent != null
               && testlistStarExpr.parent instanceof Python3Parser.Testlist_star_expr_augaassignContext) {
            return true;
       }
        return false;
    }


    /**
     * for atom_expr, get its testlist_star_expr
     *
     * @param ctx
     * @return
     */
    private RuleContext getTestliststarExprForAtomExpr(RuleContext ctx) {
        RuleContext newCtx = getTermCtxForAtomExpr(ctx);
        //System.out.println("getTermCtxForAtomExpr:" + newCtx.getClass());

        newCtx = getExprCtxForTerm(newCtx);
        //System.out.println("getExprCtxForTerm:" + newCtx.getClass());

        newCtx = getCompCtxForExpr(newCtx);
        //System.out.println("getCompCtxForExpr:" + newCtx.getClass());

        newCtx = getOrtestCtxForComp(newCtx);
        //System.out.println("getOrtestCtxForComp:" + newCtx.getClass());

        newCtx = getTestCtxForOrtest(newCtx);
        //System.out.println("getTestCtxForOrtest:" + newCtx.getClass());

        newCtx = getTestListStarExprCtxForTest(newCtx);
        //System.out.println("getTestListStarExprCtxForTest:" + newCtx.getClass());
        return newCtx;
    }



    /**
     * get testlist_star_expr for test
     *
     * testlist_star_expr: (test|star_expr) (',' (test|star_expr))* (',')?;
     * otherrules: test.....
     * @param ctx
     * @return
     */
    private RuleContext getTestListStarExprCtxForTest(RuleContext ctx) {
        if(ctx != null
                && ctx.parent != null
                && ctx.parent instanceof Python3Parser.Testlist_star_exprContext) {
            return ctx.parent;
        }
        return null;
    }

    /**
     * get TermContext of AtomExpr.
     * term: factor (('*'|'@'|'/'|'%'|'//') factor)*;
     factor: ('+'|'-'|'~') factor | power;
     power: atom_expr ('**' factor)?;
     * @return
     */
    private RuleContext getTermCtxForAtomExpr(RuleContext ctx) {
        while(ctx != null && !(ctx instanceof Python3Parser.TermContext)) {
            ctx = ctx.parent;
        }
        if(ctx != null) {
            return ctx;
        }
        return null;
    }


    /**
     * get ExprContext for Term
     *
     * expr: xor_expr ('|' xor_expr)*;
     xor_expr: and_expr ('^' and_expr)*;
     and_expr: shift_expr ('&' shift_expr)*;
     shift_expr: arith_expr (('<<'|'>>') arith_expr)*;
     arith_expr: term (('+'|'-') term)*;
     * @param ctx
     * @return
     */
    private RuleContext getExprCtxForTerm(RuleContext ctx)
    {
        while(ctx != null && !(ctx instanceof Python3Parser.ExprContext)) {
            ctx = ctx.parent;
        }
        if(ctx != null) {
            return ctx;
        }
        return null;
    }

    /**
     * getComparisonContext for expr.
     *
     * comparison: expr (comp_op expr)*;
     * otherRules: expr ....
     * @param ctx
     * @return
     */
    private RuleContext getCompCtxForExpr(RuleContext ctx) {
        if(ctx != null
                && ctx.parent != null
                && ctx.parent instanceof Python3Parser.ComparisonContext) {
            return ctx.parent;
        }
        return null;
    }

    /**
     * get or_test context for comparison.
     * Each rule is in unique usage.
     *
     or_test: and_test ('or' and_test)*;
     and_test: not_test ('and' not_test)*;
     not_test: 'not' not_test | comparison;
      * @param ctx
     * @return
     */
    private RuleContext getOrtestCtxForComp(RuleContext ctx) {
        while(ctx != null && !(ctx instanceof Python3Parser.Or_testContext)) {
            ctx = ctx.parent;
        }
        if(ctx != null) {
            return ctx;
        }
        return null;
    }

    /**
     *
     * test: or_test ('if' or_test 'else' test)? | lambdef;  //test->test
     comp_for: or_test.....
     other rules: or_test
     * @return
     */
    private RuleContext getTestCtxForOrtest (RuleContext ctx) {
        if(ctx == null) {
            return null;
        }
        if(ctx.parent != null && !(ctx.parent instanceof Python3Parser.TestContext)) {
            return null;
        }

        //find the first testContext
        if(ctx.parent!= null) {
            ctx = ctx.parent;
        }

        //find the outest testContext
        while(ctx.parent != null && ctx.parent instanceof Python3Parser.TestContext) {
            ctx = ctx.parent;
        }
        //ctx.parent not instance of TestContest
        if(ctx.parent != null) {
            //ctx is outest TestContext
            return ctx;
        }
        return null;
    }

}
