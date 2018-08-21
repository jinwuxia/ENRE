package multiparser.py3extractor;

import multiparser.goextractor.antlr4.GolangParser;
import multiparser.py3extractor.antlr4.Python3BaseVisitor;
import multiparser.py3extractor.antlr4.Python3Parser;
import org.antlr.v4.runtime.RuleContext;

public class PyContextHelper {

    public boolean isInitFile(String fileFullPath) {
        if(fileFullPath.endsWith(ConstantString.INIT_FILE_NAME)) {
            return true;
        }
        return false;
    }


    /**
     * judge funcdef or classDef,... are at top level in a file
     * file_input: (NEWLINE | stmt)* EOF;
     * stmt: simple_stmt | compound_stmt;
     * compound_stmt: if_stmt | while_stmt | for_stmt | try_stmt | with_stmt | funcdef | classdef | decorated | async_stmt;
     * @param ctx
     * @return
     */
    public boolean isOneComStmAtTopLevel(RuleContext ctx) {
        if(ctx != null
                && ctx.parent != null
                && ctx.parent.parent != null
                && ctx.parent.parent.parent != null
                && ctx.parent instanceof Python3Parser.File_inputContext) {
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
    public boolean isInDecorator(Python3Parser.FuncdefContext ctx) {
        if(ctx != null && ctx.parent != null && ctx.parent instanceof Python3Parser.DecoratedContext) {
            return true;
        }
        return false;
    }



}
