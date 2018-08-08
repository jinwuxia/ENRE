package visitor.firstpass;

import antlr4.GolangParser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RuleContext;

public class ContextHelper {


    /**
     * topLevelDecl: declaration | functionDecl | methodDecl;
     declaration: constDecl | typeDecl | varDecl;
     * @param ctx
     * @return
     */
    public boolean isTopLevelDecl(ParserRuleContext ctx) {

        if(ctx instanceof GolangParser.ConstDeclContext
                || ctx instanceof GolangParser.TypeDeclContext
                || ctx instanceof GolangParser.VarDeclContext) {
            if (ctx.parent != null
                    && ctx.parent.parent != null
                    && ctx.parent.parent instanceof GolangParser.TopLevelDeclContext) {
                return true;
            }
        }

        if(ctx instanceof GolangParser.FunctionDeclContext || ctx instanceof  GolangParser.MethodDeclContext) {
            if(ctx.parent != null && ctx.parent instanceof  GolangParser.TopLevelDeclContext) {
                return false;
            }
        }

        return false;
    }



    public  boolean isStructTypeInTypeDecl(ParserRuleContext ctx) {
        if (ctx.parent != null
                && ctx.parent.parent != null
                && ctx.parent.parent.parent != null
                && ctx.parent.parent.parent.parent != null
                && ctx.parent.parent.parent.parent instanceof  GolangParser.TypeDeclContext) {
            return true;
        }
        return false;
    }

    public boolean isInterfaceypeInTypeDecl(ParserRuleContext ctx) {
        if (ctx.parent != null
                && ctx.parent.parent != null
                && ctx.parent.parent.parent != null
                && ctx.parent.parent.parent.parent != null
                && ctx.parent.parent.parent.parent instanceof  GolangParser.TypeDeclContext) {
            return true;
        }
        return false;
    }

    /**
     * leftAssignment: expressionList;
     * @param ctx
     * @return
     */
    public boolean isPrimaryExprInLeftAssignment (GolangParser.PrimaryExprContext ctx) {
        RuleContext ruleContext= getPrimaryExprInExpressionList(ctx);
        if (ruleContext != null
                && ruleContext.parent != null
                && ruleContext.parent instanceof GolangParser.LeftAssignmentContext) {
            return true;
        }
        return false;
    }

    /**
     * rightAssignment: expressionList;
     * @param ctx
     * @return
     */
    public boolean isPrimaryExprInRightAssignment (GolangParser.PrimaryExprContext ctx) {
        RuleContext ruleContext = getPrimaryExprInExpressionList(ctx);
        if (ruleContext != null
                && ruleContext.parent!= null
                && ruleContext.parent instanceof GolangParser.RightAssignmentContext) {
            return true;
        }
        return false;
    }

    /**
     * rightShortVarDecl: expressionList;
     * @param ctx
     * @return
    */
    public boolean isPrimaryExprInRightShortVarDecl (GolangParser.PrimaryExprContext ctx) {
        RuleContext ruleContext = getPrimaryExprInExpressionList(ctx);
        if (ruleContext != null
                && ruleContext.parent != null
                && ruleContext.parent instanceof GolangParser.RightShortVarDeclContext) {
            return true;
        }
        return false;
    }



    /**
     * leftShortVarDecl: identifierList;
     * identifierList: IDENTIFIER ( ',' IDENTIFIER )*;
     * @param ctx
     * @return
    */
    public boolean isIdentifierListInLeftShortVarDecl(ParserRuleContext ctx) {
        if (ctx.parent != null
                && ctx.parent instanceof GolangParser.LeftShortVarDeclContext) {
            return true;
        }
        return false;
    }

    /**
     * expressionList: expression ( ',' expression )*;
     * expression: unaryExpr | expression ('||' | '&&' | '==' | '!=' | '<' | '<=' | '>' | '>=' | '+' | '-' | '|' | '^' | '*' | '/' | '%' | '<<' | '>>' | '&' | '&^') expression;
     * unaryExpr: primaryExpr| ('+'|'-'|'!'|'^'|'*'|'&'|'<-') unaryExpr;
     * primaryExpr: operand | conversion | primaryExpr selector | primaryExpr index | primaryExpr slice  | primaryExpr typeAssertion | primaryExpr arguments;
     * operand : literal | operandName | methodExpr | '(' expression ')';
     * @param ctx
     * @return

    private RuleContext getOperandInExpressionList(ParserRuleContext ctx) {
        RuleContext ruleContext = ctx;
        while (ruleContext != null
                && ruleContext.parent != null
                && ruleContext.parent instanceof GolangParser.PrimaryExprContext) {
            ruleContext = ruleContext.parent;
        }
        while (ruleContext != null
                && ruleContext.parent != null
                && ruleContext.parent instanceof GolangParser.UnaryExprContext) {
            ruleContext = ruleContext.parent;
        }

        while (ruleContext != null
                && ruleContext.parent != null
                && ruleContext.parent instanceof GolangParser.ExpressionContext) {
            ruleContext = ruleContext.parent;
        }
        if (ruleContext.parent != null
                && ruleContext.parent instanceof GolangParser.ExpressionListContext) {
            return ruleContext.parent;
        }
        return null;
    }
    */


    /**
     * judge wheter this operand is not var, but is a methodCall
     * @param ctx
     * @return

    public boolean isOperandInMethodCallPrimaryExpr(GolangParser.OperandContext ctx) {
        if (ctx != null
                && ctx.parent != null && ctx.parent instanceof GolangParser.OperandPrimaryExprContext
                && ctx.parent.parent != null && ctx.parent.parent instanceof GolangParser.MethodCallPrimaryExprContext) {
            return true;
        }
        return false;
    }
    */


    /**
     * expressionList: expression ( ',' expression )*;
     * expression: unaryExpr | expression ('||' | '&&' | '==' | '!=' | '<' | '<=' | '>' | '>=' | '+' | '-' | '|' | '^' | '*' | '/' | '%' | '<<' | '>>' | '&' | '&^') expression;
     * unaryExpr: primaryExpr| ('+'|'-'|'!'|'^'|'*'|'&'|'<-') unaryExpr;
     * primaryExpr
     : operand                     #operandPrimaryExpr
     | conversion                  #conversionPrimaryExpr
     | primaryExpr selector        #selectorPrimaryExpr
     | primaryExpr index           #indexPrimaryExpr
     | primaryExpr slice           #slicePrimaryExpr
     | primaryExpr typeAssertion   #typeAssertionPrimaryExpr
     | primaryExpr arguments       #methodCallPrimaryExpr
     ;
     **/
    public RuleContext getPrimaryExprInExpressionList(RuleContext ctx) {
        RuleContext ruleContext = ctx;
        /*
        delete this, because the outlayer type of primaryexpr decides the type,
        so we don't care about the inner primaryExpr.
        also, we can obtain var as A.B.C but not A, A.B, and A.B.C
        also, we can remove the selector which is .func() but not A.B var.
        */

       /* RuleContext ruleContext = ctx;
        while (ruleContext != null
                && ruleContext.parent != null
                && ruleContext.parent instanceof GolangParser.PrimaryExprContext) {
            ruleContext = ruleContext.parent;
        }
        */

        while (ruleContext != null
                && ruleContext.parent != null
                && ruleContext.parent instanceof GolangParser.UnaryExprContext) {
            ruleContext = ruleContext.parent;
        }

        while (ruleContext != null
                && ruleContext.parent != null
                && ruleContext.parent instanceof GolangParser.ExpressionContext) {
            ruleContext = ruleContext.parent;
        }
        if (ruleContext.parent != null
                && ruleContext.parent instanceof GolangParser.ExpressionListContext) {
            return ruleContext.parent;
        }
        return null;

    }



    /**
     * primaryExpr
     : operand                     #operandPrimaryExpr
     | conversion                  #conversionPrimaryExpr
     | primaryExpr selector        #selectorPrimaryExpr
     | primaryExpr index           #indexPrimaryExpr
     | primaryExpr slice           #slicePrimaryExpr
     | primaryExpr typeAssertion   #typeAssertionPrimaryExpr
     | primaryExpr arguments       #methodCallPrimaryExpr
     * is the operandname in left Assignment
     * @param ctx
     * @return
     */
    public boolean isOperandNameInLeftAssignment(GolangParser.OperandNameContext ctx) {
        //find the outest primaryExpr;
        RuleContext newCtx = null;
        if(ctx.parent != null && ctx.parent instanceof GolangParser.OperandContext) {
            newCtx = ctx.parent;
        }
        while (newCtx != null && newCtx.parent instanceof GolangParser.PrimaryExprContext) {
            newCtx = newCtx.parent;
        }

        if (newCtx != null
                && newCtx instanceof GolangParser.PrimaryExprContext
                && isPrimaryExprInLeftAssignment((GolangParser.PrimaryExprContext)newCtx)) {
            return true;
        }
        return false;
    }


    public boolean isOperandNameInRightAssignment(GolangParser.OperandNameContext ctx) {
        //find the outest primaryExpr;
        RuleContext newCtx = null;
        if(ctx.parent != null && ctx.parent instanceof GolangParser.OperandContext) {
            newCtx = ctx.parent;
        }
        while (newCtx != null && newCtx.parent instanceof GolangParser.PrimaryExprContext) {
            newCtx = newCtx.parent;
        }

        if (newCtx != null
                && newCtx instanceof GolangParser.PrimaryExprContext
                && isPrimaryExprInRightAssignment((GolangParser.PrimaryExprContext)newCtx)) {
            return true;
        }
        return false;
    }


    public boolean isOperandNameInAssignment(GolangParser.OperandNameContext ctx) {
        if (isOperandNameInLeftAssignment(ctx)
                || isOperandNameInRightAssignment(ctx)){
            return true;
        }
        return false;
    }


    public boolean isOperandNameInRightShortVarDecl(GolangParser.OperandNameContext ctx) {
        //find the outest primaryExpr;
        RuleContext newCtx = null;
        if(ctx.parent != null && ctx.parent instanceof GolangParser.OperandContext) {
            newCtx = ctx.parent;
        }
        while (newCtx != null && newCtx.parent instanceof GolangParser.PrimaryExprContext) {
            newCtx = newCtx.parent;
        }

        if (newCtx != null
                && newCtx instanceof GolangParser.PrimaryExprContext
                && isPrimaryExprInRightShortVarDecl((GolangParser.PrimaryExprContext) newCtx)) {
            return true;
        }
        return false;
    }



}
