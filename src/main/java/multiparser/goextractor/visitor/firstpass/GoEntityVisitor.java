package multiparser.goextractor.visitor.firstpass;

import multiparser.goextractor.antlr4.GolangBaseVisitor;
import multiparser.goextractor.antlr4.GolangParser;
import multiparser.util.Configure;
import org.antlr.v4.runtime.tree.TerminalNode;
import multiparser.goextractor.ConstantString;
import multiparser.extractor.SingleCollect;
import sun.security.krb5.Config;

import java.util.ArrayList;
import java.util.Stack;

/**
 * nodes multiparser.goextractor.visitor in the first visit
 */
public class GoEntityVisitor extends GolangBaseVisitor<String> {

    private ContextHelper helperVisitor = new ContextHelper();
    private ProcessTask processTask = new ProcessTask();
    SingleCollect singleCollect = SingleCollect.getSingleCollectInstance();

    private String fileFullPath;
    private int packageIndex;
    private int fileIndex;
    private int functionIndex = -1;

    //blockstack
    private Stack<Integer> blockStackForAFuncMeth = new Stack<Integer>();

    //// such as structFields, or interface Fields.
    private ArrayList<Integer> tmpEntitiesIds = new ArrayList<Integer>();

    public GoEntityVisitor(String fileFullPath) {
        this.fileFullPath = fileFullPath;
    }

    /*
    packageClause : 'package' IDENTIFIER;
     */
    @Override
    public String visitPackageClause(GolangParser.PackageClauseContext ctx) {
        if(ctx == null) {
            return null;
        }
        if(ctx.IDENTIFIER() == null) {
            return null;
        }
        // process packageEntity
        String packageName = ctx.IDENTIFIER().getText();
        String packagePath = processTask.getPackagePath(fileFullPath);

        int index = processTask.searchPackageIndex(packagePath);
        if (index != -1) {
            packageIndex = index;
        } else {
            packageIndex = processTask.processPackageDecl(packagePath, packageName);
        }

        fileIndex = processTask.processFile(packageIndex, fileFullPath);
        return null;
    }


    /**
     * importDecl: 'import' ( importSpec | '(' ( importSpec eos )* ')' );
     *
     * @param ctx
     * @return
     */
    @Override
    public String visitImportDecl(GolangParser.ImportDeclContext ctx) {
        for (GolangParser.ImportSpecContext importSpecContext : ctx.importSpec()) {
            String importNameAndPath = visitImportSpec(importSpecContext);
            processTask.processImport(importNameAndPath, fileIndex);
        }
        return null;
    }


    /**
     * importSpec: ( '.' | IDENTIFIER )? importPath;
     *
     * @param ctx
     * @return
     */
    @Override
    public String visitImportSpec(GolangParser.ImportSpecContext ctx) {
        String importName = Configure.NULL_STRING;
        String importPath = Configure.NULL_STRING;

        if (ctx.getChild(0).equals(Configure.DOT)) {
            importName = Configure.DOT;
        } else if (ctx.IDENTIFIER() != null) {
            importName = ctx.IDENTIFIER().getText();
        }

        importPath = visitImportPath(ctx.importPath());
        return importName + Configure.SEMICOLON + importPath;

    }

    /**
     * importPath: STRING_LIT;
     *
     * @param ctx
     * @return
     */
    @Override
    public String visitImportPath(GolangParser.ImportPathContext ctx) {
        return ctx.STRING_LIT().getText();
    }

    /**
     * grammar: constSpec: identifierList ( type? '=' expressionList )?;
     *
     * @param ctx
     * @return
     */
    @Override
    public String visitConstSpec(GolangParser.ConstSpecContext ctx) {
        String type = Configure.NULL_STRING;
        if (ctx.type() != null) {
            type = visitType(ctx.type());
        }
        //in file scope
        if (ctx.getParent() != null && helperVisitor.isTopLevelDecl(ctx.getParent())) {
            processTask.processConstInFile(ctx, type, fileIndex);
        }
        //in function scope
        else if(functionIndex != -1) {
            int localBlockId = -1;
            if(!blockStackForAFuncMeth.isEmpty()) {
                localBlockId = blockStackForAFuncMeth.peek();
            }
            processTask.processConstInFunction(ctx, type, functionIndex, localBlockId);
        }
        return null;

    }



    /**
     * grammar
     * varDecl: 'var' ( varSpec | '(' ( varSpec eos )* ')' );
     * varSpec: identifierList ( type ( '=' expressionList )? | '=' expressionList );
     * identifierList: IDENTIFIER ( ',' IDENTIFIER )*;
     *
     * @param ctx
     * @return
     */
    @Override
    public String visitVarDecl(GolangParser.VarDeclContext ctx) {
        for (GolangParser.VarSpecContext varSpecContext : ctx.varSpec()) {
            String type;

            //get type of var
            if (varSpecContext.type() != null) {
                type = varSpecContext.type().getText();
            } else {
                type = Configure.NULL_STRING;
            }

            for (TerminalNode node : varSpecContext.identifierList().IDENTIFIER())
            {
                //value has a problem????????????
                String value = Configure.NULL_STRING;
                if (varSpecContext.expressionList() != null) {
                    value = visitExpressionList(varSpecContext.expressionList());
                }

                //the vars appears in file scope
                if (helperVisitor.isTopLevelDecl(ctx)) {
                    processTask.processVarDeclInFile(varSpecContext, type, fileIndex);
                }
                // the vars appear in function/method scope
                else if (functionIndex != -1) {
                    int localBlockId = -1;
                    if (!blockStackForAFuncMeth.isEmpty()) {
                        localBlockId = blockStackForAFuncMeth.peek();
                    }
                    processTask.processVarInFunction(node, type, value,  functionIndex, localBlockId);
                } else {
                    //unknown
                }
            }
        }
        return null;
    }


    /**
     * typeSpec: IDENTIFIER type;
     *
     * @param ctx
     * @return
     */
    @Override
    public String visitTypeSpec(GolangParser.TypeSpecContext ctx) {

        //If it is StructType declaration.
        if (ctx.type().typeLit() != null && ctx.type().typeLit().structType() != null) {
            GolangParser.StructTypeContext structTypeContext = ctx.type().typeLit().structType();
            //find its struct fields, store into tmpEntitiesIds.
            visitStructType(structTypeContext);
            processTask.processTypeSpec(ctx, ConstantString.STRUCT_TYPE, tmpEntitiesIds, fileIndex);
            tmpEntitiesIds.clear();
        }

        //If it is InterfaceType declaration
        else if (ctx.type().typeLit() != null && ctx.type().typeLit().interfaceType() != null) {
            GolangParser.InterfaceTypeContext interfaceTypeContext = ctx.type().typeLit().interfaceType();
            //find its interface fields, store into tmpEntitiesIds.
            visitInterfaceType(interfaceTypeContext);
            processTask.processTypeSpec(ctx, ConstantString.INTERFACE_TYPE, tmpEntitiesIds, fileIndex);
            tmpEntitiesIds.clear();
        }


        //if it is AliasType declaration (typeName() - basicType)
        else if (ctx.type().typeName() != null && helperVisitor.isTopLevelDecl(ctx.getParent())) {
            String type = visitTypeName(ctx.type().typeName());
            String name = ctx.IDENTIFIER().getText();
            processTask.processAliasType(fileIndex, type, name);
        }

        //if it is AliasType declaration: (typeList() - slice/map/func type)
        else if (ctx.type().typeLit() != null
                && helperVisitor.isTopLevelDecl(ctx.getParent())) {
            String type = visitTypeLit(ctx.type().typeLit());
            String name = ctx.IDENTIFIER().getText();
            processTask.processAliasType(fileIndex, type, name);
        }

        return null;
    }



    /**
     * Get all fileds of interface, and store in tmpInterfacefields.
     * grammar:
     * structType: 'struct' '{' ( fieldDecl eos )* '}';
     * fieldDecl: (identifierList type | anonymousField) STRING_LIT?;
     * anonymousField: '*'? typeName;
     * identifierList: IDENTIFIER ( ',' IDENTIFIER )*;
     *
     * @param ctx
     * @return
     */
    @Override
    public String visitStructType(GolangParser.StructTypeContext ctx) {
        if (!helperVisitor.isStructTypeInTypeDecl(ctx)) {
            String str = ConstantString.STRING_STRUCT + Configure.LEFT_CURLY_BRACE;
            if (ctx.fieldDecl() != null && !ctx.fieldDecl().isEmpty()) {
                str += visitFieldDecl(ctx.fieldDecl(0));
                for (int i = 1; i < ctx.fieldDecl().size(); i++) {
                    str += (Configure.SEMICOLON + visitFieldDecl(ctx.fieldDecl(i)));
                }
            }
            str += Configure.RIGHT_CURLY_BRACE;
            return str;
        } else {
            tmpEntitiesIds.clear();
            if (ctx.fieldDecl() != null) {
                for (GolangParser.FieldDeclContext fieldDeclContext : ctx.fieldDecl()) {
                    String fieldType = null;
                    String fieldName = null;
                    if (fieldDeclContext.identifierList() != null) {
                        fieldType = fieldDeclContext.type().getText();
                        for (TerminalNode node : fieldDeclContext.identifierList().IDENTIFIER()) {
                            fieldName = node.getText();
                            int fieldIndex = processTask.processStructFieldAsNormal(fieldType, fieldName);
                            tmpEntitiesIds.add(fieldIndex);
                        } //end for
                    } //end if

                    else if (fieldDeclContext.anonymousField() != null) {
                        fieldName = ConstantString.STRUCT_FIELD_IS_ANONYMOUS; //default
                        fieldType = visitTypeName(fieldDeclContext.anonymousField().typeName());
                        int fieldIndex = processTask.processStructFieldAsAnonymous(fieldType, fieldName);
                        tmpEntitiesIds.add(fieldIndex);
                    } // end else
                } //end for
            }
            return null;
        }
    }


    /**
     * fieldDecl: (identifierList type | anonymousField) STRING_LIT?;
     *
     * @param ctx
     * @return
     */
    @Override
    public String visitFieldDecl(GolangParser.FieldDeclContext ctx) {
        String str = Configure.NULL_STRING;
        if (ctx.type() != null) {
            str += visitIdentifierList(ctx.identifierList());
            str += visitType(ctx.type());
        } else {
            str += visitAnonymousField(ctx.anonymousField());
        }
        if (ctx.STRING_LIT() != null) {
            str += ctx.STRING_LIT().getText();
        }
        return str;
    }


    /**
     * anonymousField: '*'? typeName;
     *
     * @param ctx
     * @return
     */
    @Override
    public String visitAnonymousField(GolangParser.AnonymousFieldContext ctx) {
        String str = visitTypeName(ctx.typeName());
        if (ctx.getChild(0).equals(Configure.STAR)) {
            return (Configure.STAR + str);
        } else {
            return str;
        }
    }

    /**
     * Get all fields of interface, and store in tmpInterfacefields.
     * grammar:
     * interfaceType: 'interface' '{' ( methodSpec eos )* '}';
     * methodSpec: IDENTIFIER signature  |  typeName;
     *
     * @param ctx
     * @return
     */
    @Override
    public String visitInterfaceType(GolangParser.InterfaceTypeContext ctx) {
        //it is not for interface type declaration
        if (!helperVisitor.isInterfaceypeInTypeDecl(ctx)) {
            String str = ConstantString.INTERFACE + Configure.LEFT_CURLY_BRACE;
            if (ctx.methodSpec().size() != 0) {
                str += visitMethodSpec(ctx.methodSpec(0));
                for (int i = 1; i < ctx.methodSpec().size(); i++) {
                    str += (Configure.SEMICOLON + visitMethodSpec(ctx.methodSpec(i)));
                }
            }
            str += Configure.RIGHT_CURLY_BRACE;
            return str;
        }
        //it is for interface type declaration
        else {
            //tmpEntities.clear();
            tmpEntitiesIds.clear();
            if (ctx.methodSpec() != null) {
                for (GolangParser.MethodSpecContext methodSpecContext : ctx.methodSpec()) {
                    String type = null; // "TYPE" or "METHOD"
                    String name = null;
                    //TypeDecl as a field
                    if (methodSpecContext.typeName() != null) {
                        type = ConstantString.INTERFACE_FIELD_IS_TYPE; //"TYPE"
                        name = visitTypeName(methodSpecContext.typeName());
                        int fieldIndex = processTask.processInterfaceFieldAsType(type, name);
                        tmpEntitiesIds.add(fieldIndex);
                    }
                    //methodDecl as a field
                    else {
                        type = ConstantString.INTERFACE_FIELD_IS_METHOD; //"METHOD";
                        name = methodSpecContext.IDENTIFIER().getText();
                        //parse methodSignature, grammar: signature: parameters result?;
                        String methodSignatureParas = visitParameters(methodSpecContext.signature().parameters());
                        String methodSignatureReturns = Configure.NULL_STRING;
                        if (methodSpecContext.signature().result() != null) {
                            methodSignatureReturns = visitResult(methodSpecContext.signature().result());
                        }
                        int fieldIndex = processTask.processInterfaceFieldAsMethod(type, name, methodSignatureParas, methodSignatureReturns);
                        tmpEntitiesIds.add(fieldIndex);
                    }
                }
            }
        } //end else
        return null;
    }




    /**
     * methodSpec: IDENTIFIER signature  |  typeName;
     *
     * @param ctx
     * @return
     */
    @Override
    public String visitMethodSpec(GolangParser.MethodSpecContext ctx) {
        if (ctx == null) {
            System.out.println("ctx is null");
        }
        if (ctx.typeName() != null) {
            return visitTypeName(ctx.typeName());
        } else {
            return ctx.IDENTIFIER().getText() + visitSignature(ctx.signature());
        }
    }

    /**
     * typeLit: arrayType | structType | pointerType | functionType | interfaceType | sliceType | mapType | channelType ;
     *
     * @param ctx
     * @return
     */
    @Override
    public String visitTypeLit(GolangParser.TypeLitContext ctx) {
        if (ctx.arrayType() != null) {
            return visitArrayType(ctx.arrayType());
        } else if (ctx.structType() != null) {
            return visitStructType(ctx.structType());
        } else if (ctx.interfaceType() != null) {
            return visitInterfaceType(ctx.interfaceType());
        } else if (ctx.pointerType() != null) {
            return visitPointerType(ctx.pointerType());
        } else if (ctx.functionType() != null) {
            return visitFunctionType(ctx.functionType());
        } else if (ctx.sliceType() != null) {
            return visitSliceType(ctx.sliceType());
        } else if (ctx.mapType() != null) {
            return visitMapType(ctx.mapType());
        } else if (ctx.channelType() != null) {
            return visitChannelType(ctx.channelType());
        }
        return null;
    }

    /**
     * arrayType: '[' arrayLength ']' elementType;
     *
     * @param ctx
     * @return
     */
    @Override
    public String visitArrayType(GolangParser.ArrayTypeContext ctx) {
        return Configure.LEFT_SQUARE_BRACKET + visitArrayLength(ctx.arrayLength()) + Configure.RIGHT_SQUARE_BRACKET + visitElementType(ctx.elementType());
    }

    /**
     * pointerType: '*' type;
     *
     * @param ctx
     * @return
     */
    @Override
    public String visitPointerType(GolangParser.PointerTypeContext ctx) {
        return Configure.STAR + visitType(ctx.type());
    }

    /**
     * functionType: 'func' signature;
     *
     * @param ctx
     * @return
     */
    @Override
    public String visitFunctionType(GolangParser.FunctionTypeContext ctx) {
        return ConstantString.STRING_FUNC + Configure.ONE_SPACE_STRING + visitSignature(ctx.signature());
    }

    /**
     * sliceType: '[' ']' elementType;
     *
     * @param ctx
     * @return
     */
    @Override
    public String visitSliceType(GolangParser.SliceTypeContext ctx) {
        return Configure.LEFT_SQUARE_BRACKET + Configure.RIGHT_SQUARE_BRACKET + visitElementType(ctx.elementType());
    }

    /**
     * mapType: 'map' '[' type ']' elementType;
     *
     * @param ctx
     * @return
     */
    @Override
    public String visitMapType(GolangParser.MapTypeContext ctx) {
        return ConstantString.MAP + Configure.LEFT_SQUARE_BRACKET + visitType(ctx.type()) + Configure.RIGHT_SQUARE_BRACKET + visitElementType(ctx.elementType());
    }

    /**
     * channelType: ( 'chan' | 'chan' '<-' | '<-' 'chan' ) elementType;
     *
     * @param ctx
     * @return
     */
    @Override
    public String visitChannelType(GolangParser.ChannelTypeContext ctx) {
        return ctx.getChild(0).getText() + visitElementType(ctx.elementType());
    }

    /**
     * elementType: type;
     *
     * @param ctx
     * @return
     */
    @Override
    public String visitElementType(GolangParser.ElementTypeContext ctx) {
        return visitType(ctx.type());
    }

    /**
     * type: typeName | typeLit | '(' type ')';
     *
     * @param ctx
     * @return
     */
    @Override
    public String visitType(GolangParser.TypeContext ctx) {
        if (ctx.typeName() != null) {
            return visitTypeName(ctx.typeName());
        } else if (ctx.typeLit() != null) {
            return visitTypeLit(ctx.typeLit());
        } else if (ctx.type() != null) {
            return (Configure.LEFT_PARENTHESES + visitType(ctx.type()) + Configure.RIGHT_PARENTHESES);
        }
        return null;
    }

    /**
     * typeName: IDENTIFIER | qualifiedIdent;
     *
     * @param ctx
     * @return
     */
    @Override
    public String visitTypeName(GolangParser.TypeNameContext ctx) {
        if (ctx.IDENTIFIER() != null) {
            return ctx.IDENTIFIER().getText();
        } else if (ctx.qualifiedIdent() != null) {
            return visitQualifiedIdent(ctx.qualifiedIdent());
        }
        return null;
    }

    /**
     * qualifiedIdent: IDENTIFIER '.' IDENTIFIER;
     *
     * @param ctx
     * @return
     */
    @Override
    public String visitQualifiedIdent(GolangParser.QualifiedIdentContext ctx) {
        return ctx.IDENTIFIER(0).getText() + Configure.DOT + ctx.IDENTIFIER(1).getText();
    }

    /**
     * signature: parameters result?;
     *
     * @param ctx
     * @return
     */
    @Override
    public String visitSignature(GolangParser.SignatureContext ctx) {
        if (ctx.result() != null) {
            return visitParameters(ctx.parameters()) + Configure.ONE_SPACE_STRING + visitResult(ctx.result());
        } else {
            return visitParameters(ctx.parameters());
        }
    }

    /**
     * parameters: '(' ( parameterList ','? )? ')';
     *
     * @param ctx
     * @return
     */
    @Override
    public String visitParameters(GolangParser.ParametersContext ctx) {
        if (ctx.parameterList() != null) {
            String str = Configure.LEFT_PARENTHESES;
            str += visitParameterList(ctx.parameterList());
            if (ctx.getChildCount() == 4) {
                str += Configure.COMMA;
            }
            str += Configure.RIGHT_PARENTHESES;
            return str;
        } else {
            return Configure.LEFT_PARENTHESES + Configure.RIGHT_PARENTHESES;
        }
    }

    /**
     * parameterList: parameterDecl ( ',' parameterDecl )*
     *
     * @param ctx
     * @return
     */
    @Override
    public String visitParameterList(GolangParser.ParameterListContext ctx) {
        String str = Configure.NULL_STRING;
        str += visitParameterDecl(ctx.parameterDecl(0));
        if (ctx.parameterDecl().size() > 1) {
            for (int i = 1; i < ctx.parameterDecl().size(); i++) {
                str += (Configure.COMMA + visitParameterDecl(ctx.parameterDecl(i)));
            }
        }
        return str;
    }

    /**
     * parameterDecl: identifierList? '...'? type;
     *
     * @param ctx
     * @return
     */
    @Override
    public String visitParameterDecl(GolangParser.ParameterDeclContext ctx) {
        String str = Configure.NULL_STRING;
        //parameters (having identifierList)
        if (ctx.identifierList() != null) {
            str += visitIdentifierList(ctx.identifierList());

            if (ctx.getChildCount() >= 2 &&
                    (ctx.getChild(0).getText().equals(Configure.ELLIPSIS) || ctx.getChild(1).getText().equals(Configure.ELLIPSIS))) {
                str += (Configure.ONE_SPACE_STRING + Configure.ELLIPSIS);
                str += visitType(ctx.type());
            } else {
                str += (Configure.ONE_SPACE_STRING + visitType(ctx.type()));
            }
        }
        //returns (having no identifierList, just having type)
        else {
            str += visitType(ctx.type());
        }

        return str;
    }





    /**
     * identifierList: IDENTIFIER ( ',' IDENTIFIER )*;
     * @param ctx
     * @return
     */
    @Override
    public String visitIdentifierList(GolangParser.IdentifierListContext ctx) {
        //processTask.processIdentifierList(ctx, functionIndex);

        String str = Configure.NULL_STRING;
        str += ctx.IDENTIFIER(0).getText();
        if (ctx.IDENTIFIER().size() > 1) {
            for (int i = 1; i < ctx.IDENTIFIER().size(); i++) {
                str += (Configure.COMMA + ctx.IDENTIFIER(i).getText());
            }
        }
        return str;
    }

    /**
     * result : parameters | type;
     *
     * @param ctx
     * @return
     */
    @Override
    public String visitResult(GolangParser.ResultContext ctx) {
        if (ctx.parameters() != null) {
            return visitParameters(ctx.parameters());
        } else if (ctx.type() != null) {
            return visitType(ctx.type());
        }
        return null;
    }

    /**
     * arrayLength: expression;
     *
     * @param ctx
     * @return
     */
    @Override
    public String visitArrayLength(GolangParser.ArrayLengthContext ctx) {
        return visitExpression(ctx.expression());
    }

    /**
     * expression: unaryExpr
     * | expression ('||' | '&&' | '==' | '!=' | '<' | '<=' | '>' | '>=' | '+' | '-' | '|' | '^' | '*' | '/' | '%' | '<<' | '>>' | '&' | '&^') expression
     * ;
     *
     * @param ctx
     * @return
     */
    @Override
    public String visitExpression(GolangParser.ExpressionContext ctx) {
        if (ctx == null) {
            System.out.println("visitExpression null");
        }
        String str;
        if (ctx.unaryExpr() != null) {
            str = visitUnaryExpr(ctx.unaryExpr());
        } else {
            str = visitExpression(ctx.expression(0)) + ctx.getChild(1).getText() + visitExpression(ctx.expression(1));
        }
        //visitChildren(ctx);
        return str;
    }

    /**
     * unaryExpr: primaryExpr   | ('+'|'-'|'!'|'^'|'*'|'&'|'<-') unaryExpr;
     *
     * @param ctx
     * @return
     */
    @Override
    public String visitUnaryExpr(GolangParser.UnaryExprContext ctx) {
        if (ctx == null) {
            System.out.println("visitUnaryExpr null");
        }
        if (ctx.primaryExpr() != null) {
            return visitPrimaryExpr(ctx.primaryExpr());
        } else if (ctx.unaryExpr() != null) {
            return ctx.getChild(0).getText() + visitUnaryExpr(ctx.unaryExpr());
        }
        return null;
    }


    /**
     * primaryExpr: operand                     #operandPrimaryExpr;
     *
     * @param ctx
     * @return
     */
    @Override
    public String visitOperandPrimaryExpr(GolangParser.OperandPrimaryExprContext ctx) {
        String str = Configure.NULL_STRING;
        if (ctx != null) {
            str = visitOperand(ctx.operand());
            //processTask.processOperandInFunctionEntity(ctx, str, functionIndex);
        }
        return str;
    }

    /**
     * primaryExpr: conversion                  #conversionPrimaryExpr;
     *
     * @param ctx
     * @return
     */
    @Override
    public String visitConversionPrimaryExpr(GolangParser.ConversionPrimaryExprContext ctx) {
        String str = Configure.NULL_STRING;
        if (ctx != null) {
            str = visitConversion(ctx.conversion());
            //processTask.processOperandInFunctionEntity(ctx, str, functionIndex);
        }
        return str;
    }

    /**
     * primaryExpr: primaryExpr selector        #selectorPrimaryExpr;
     *
     * @param ctx
     * @return
     */
    @Override
    public String visitSelectorPrimaryExpr(GolangParser.SelectorPrimaryExprContext ctx) {
        String str = Configure.NULL_STRING;
        if (ctx != null) {
            str = visitPrimaryExpr(ctx.primaryExpr()) + visitSelector(ctx.selector());
            //processTask.processOperandInFunctionEntity(ctx, str, functionIndex);
        }
        return str;
    }

    /**
     * primaryExpr: primaryExpr index           #indexPrimaryExpr;
     *
     * @param ctx
     * @return
     */
    @Override
    public String visitIndexPrimaryExpr(GolangParser.IndexPrimaryExprContext ctx) {
        String str = Configure.NULL_STRING;
        if (ctx != null) {
            str = visitPrimaryExpr(ctx.primaryExpr()) + visitIndex(ctx.index());
            //processTask.processOperandInFunctionEntity(ctx, str, functionIndex);
        }
        return str;
    }

    /**
     * primaryExpr: primaryExpr slice           #slicePrimaryExpr;
     *
     * @param ctx
     * @return
     */
    @Override
    public String visitSlicePrimaryExpr(GolangParser.SlicePrimaryExprContext ctx) {
        String str = Configure.NULL_STRING;
        if (ctx != null) {
            str = visitPrimaryExpr(ctx.primaryExpr()) + visitSlice(ctx.slice());
        }
        return str;
    }

    /**
     * primaryExpr: primaryExpr typeAssertion   #typeAssertionPrimaryExpr;
     *
     * @param ctx
     * @return
     */
    @Override
    public String visitTypeAssertionPrimaryExpr(GolangParser.TypeAssertionPrimaryExprContext ctx) {
        String str = Configure.NULL_STRING;
        if (ctx != null) {
            str = visitPrimaryExpr(ctx.primaryExpr()) + visitTypeAssertion(ctx.typeAssertion());
        }
        return str;
    }

    /**
     * primaryExpr:| primaryExpr arguments       #methodCallPrimaryExpr;
     *
     * @param ctx
     * @return
     */
    @Override
    public String visitMethodCallPrimaryExpr(GolangParser.MethodCallPrimaryExprContext ctx) {
        String str = Configure.NULL_STRING;
        if (ctx != null) {
            str = visitPrimaryExpr(ctx.primaryExpr()) + visitArguments(ctx.arguments());
        }

        processTask.processMethodCallPrimaryExpr(functionIndex, str);
        return str;
    }


    /**
     * primaryExpr
     * : operand                     #operandPrimaryExpr
     * | conversion                  #conversionPrimaryExpr
     * | primaryExpr selector        #selectorPrimaryExpr
     * | primaryExpr index           #indexPrimaryExpr
     * | primaryExpr slice           #slicePrimaryExpr
     * | primaryExpr typeAssertion   #typeAssertionPrimaryExpr
     * | primaryExpr arguments       #methodCallPrimaryExpr
     * ;
     **/
    private String visitPrimaryExpr(GolangParser.PrimaryExprContext ctx) {
        if (ctx instanceof GolangParser.OperandPrimaryExprContext) {
            return visitOperandPrimaryExpr((GolangParser.OperandPrimaryExprContext) ctx);
        } else if (ctx instanceof GolangParser.ConversionPrimaryExprContext) {
            return visitConversionPrimaryExpr((GolangParser.ConversionPrimaryExprContext) ctx);
        } else if (ctx instanceof GolangParser.SelectorPrimaryExprContext) {
            return visitSelectorPrimaryExpr((GolangParser.SelectorPrimaryExprContext) ctx);
        } else if (ctx instanceof GolangParser.IndexPrimaryExprContext) {
            return visitIndexPrimaryExpr((GolangParser.IndexPrimaryExprContext) ctx);
        } else if (ctx instanceof GolangParser.SlicePrimaryExprContext) {
            return visitSlicePrimaryExpr((GolangParser.SlicePrimaryExprContext) ctx);
        } else if (ctx instanceof GolangParser.TypeAssertionPrimaryExprContext) {
            return visitTypeAssertionPrimaryExpr((GolangParser.TypeAssertionPrimaryExprContext) ctx);
        } else if (ctx instanceof GolangParser.MethodCallPrimaryExprContext) {
            return visitMethodCallPrimaryExpr((GolangParser.MethodCallPrimaryExprContext) ctx);
        }
        return null;
    }


    /**
     * operand : literal | operandName | methodExpr | '(' expression ')';
     * @param ctx
     * @return
     */
    @Override
    public String visitOperand(GolangParser.OperandContext ctx) {
        if (ctx.literal() != null) {
            return visitLiteral(ctx.literal());
        } else if (ctx.operandName() != null) {
            String operandName = visitOperandName(ctx.operandName());
            return operandName;
        } else if (ctx.methodExpr() != null) {
            return visitMethodExpr(ctx.methodExpr());
        } else if (ctx.expression() != null) {
            return Configure.LEFT_PARENTHESES + visitExpression(ctx.expression()) + Configure.RIGHT_PARENTHESES;
        } else {
            return null;
        }
    }

    /**
     * operandName: IDENTIFIER | qualifiedIdent;
     * @param ctx
     * @return
     */
    @Override
    public String visitOperandName(GolangParser.OperandNameContext ctx) {
        String str = Configure.NULL_STRING;
        if (ctx.IDENTIFIER() != null) {
            str = ctx.IDENTIFIER().getText();
        } else {
            str = visitQualifiedIdent(ctx.qualifiedIdent());
        }
        if(functionIndex != -1) {
            int localBlockId = -1;
            if (!blockStackForAFuncMeth.isEmpty()) {
                localBlockId = blockStackForAFuncMeth.peek();
            }
            processTask.processOperandNameInFunction(str, ctx, functionIndex, localBlockId);
        }

        return str;
    }



    /**
     * methodExpr: receiverType '.' IDENTIFIER;
     *
     * @param ctx
     * @return
     */
    @Override
    public String visitMethodExpr(GolangParser.MethodExprContext ctx) {
        return visitReceiverType(ctx.receiverType()) + Configure.DOT + ctx.IDENTIFIER().getText();
    }

    /**
     * receiverType: typeName   | '(' '*' typeName ')'   |    '(' receiverType ')';
     *
     * @param ctx
     * @return
     */
    @Override
    public String visitReceiverType(GolangParser.ReceiverTypeContext ctx) {
        if (ctx.getChildCount() == 1 && ctx.typeName() != null) {
            return visitTypeName(ctx.typeName());
        } else if (ctx.getChildCount() > 1 && ctx.typeName() != null) {
            return Configure.LEFT_PARENTHESES + Configure.STAR + visitTypeName(ctx.typeName()) + Configure.RIGHT_PARENTHESES;
        } else if (ctx.getChildCount() > 1 && ctx.receiverType() != null) {
            return Configure.LEFT_PARENTHESES + visitReceiverType(ctx.receiverType()) + Configure.RIGHT_PARENTHESES;
        }
        return null;
    }

    /**
     * literal : basicLit | compositeLit  | functionLit;
     *
     * @param ctx
     * @return
     */
    @Override
    public String visitLiteral(GolangParser.LiteralContext ctx) {
        if (ctx.basicLit() != null) {
            return visitBasicLit(ctx.basicLit());
        } else if (ctx.compositeLit() != null) {
            return visitCompositeLit(ctx.compositeLit());
        } else if (ctx.functionLit() != null) {
            return visitFunctionLit(ctx.functionLit());
        }
        return null;
    }

    /**
     * basicLit : INT_LIT  | FLOAT_LIT  | IMAGINARY_LIT  | RUNE_LIT   | STRING_LIT;
     *
     * @param ctx
     * @return
     */
    @Override
    public String visitBasicLit(GolangParser.BasicLitContext ctx) {
        if (ctx.INT_LIT() != null) {
            return ctx.INT_LIT().getText();
        } else if (ctx.FLOAT_LIT() != null) {
            return ctx.FLOAT_LIT().getText();
        } else if (ctx.IMAGINARY_LIT() != null) {
            return ctx.IMAGINARY_LIT().getText();
        } else if (ctx.RUNE_LIT() != null) {
            return ctx.RUNE_LIT().getText();
        } else if (ctx.STRING_LIT() != null) {
            return ctx.STRING_LIT().getText();
        }
        return null;
    }

    /**
     * compositeLit: literalType literalValue;
     *
     * @param ctx
     * @return
     */
    @Override
    public String visitCompositeLit(GolangParser.CompositeLitContext ctx) {
        return visitLiteralType(ctx.literalType()) + visitLiteralValue(ctx.literalValue());
    }

    /**
     * literalType : structType | arrayType | '[' '...' ']' elementType | sliceType | mapType | typeName;
     *
     * @param ctx
     * @return
     */
    @Override
    public String visitLiteralType(GolangParser.LiteralTypeContext ctx) {
        if (ctx.structType() != null) {
            return visitStructType(ctx.structType());
        } else if (ctx.arrayType() != null) {
            return visitArrayType(ctx.arrayType());
        } else if (ctx.elementType() != null) {
            return Configure.LEFT_SQUARE_BRACKET + Configure.ELLIPSIS + Configure.RIGHT_SQUARE_BRACKET + visitElementType(ctx.elementType());
        } else if (ctx.sliceType() != null) {
            return visitSliceType(ctx.sliceType());
        } else if (ctx.mapType() != null) {
            return visitMapType(ctx.mapType());
        } else if (ctx.typeName() != null) {
            return visitTypeName(ctx.typeName());
        }
        return null;
    }

    /**
     * literalValue : '{' ( elementList ','? )? '}'  ;
     *
     * @param ctx
     * @return
     */
    @Override
    public String visitLiteralValue(GolangParser.LiteralValueContext ctx) {
        if (ctx.elementList() != null) {
            return (Configure.LEFT_CURLY_BRACE + visitElementList(ctx.elementList()) + Configure.RIGHT_CURLY_BRACE);
        } else {
            return Configure.LEFT_CURLY_BRACE + Configure.RIGHT_CURLY_BRACE;
        }
    }

    /**
     * elementList: keyedElement (',' keyedElement)*;
     *
     * @param ctx
     * @return
     */
    @Override
    public String visitElementList(GolangParser.ElementListContext ctx) {
        String str = visitKeyedElement(ctx.keyedElement(0));
        if (ctx.keyedElement().size() > 1) {
            for (int i = 1; i < ctx.keyedElement().size(); i++) {
                str += (Configure.COMMA + visitKeyedElement(ctx.keyedElement(i)));
            }
        }
        return str;
    }

    /**
     * keyedElement: (key ':')? element;
     *
     * @param ctx
     * @return
     */
    @Override
    public String visitKeyedElement(GolangParser.KeyedElementContext ctx) {
        String str = Configure.NULL_STRING;
        if (ctx.key() != null) {
            str += ((visitKey(ctx.key())) + Configure.STRING_COLON);
        }
        if (ctx.element() != null) {
            str += visitElement(ctx.element());
        }
        return str;
    }

    /**
     * key: IDENTIFIER  | expression  | literalValue;
     *
     * @param ctx
     * @return
     */
    @Override
    public String visitKey(GolangParser.KeyContext ctx) {
        if (ctx.IDENTIFIER() != null) {
            return ctx.IDENTIFIER().getText();
        } else if (ctx.expression() != null) {
            return visitExpression(ctx.expression());
        } else if (ctx.literalValue() != null) {
            return visitLiteralValue(ctx.literalValue());
        }
        return null;
    }

    /**
     * element : expression  | literalValue;
     *
     * @param ctx
     * @return
     */
    @Override
    public String visitElement(GolangParser.ElementContext ctx) {
        if (ctx == null) {
            System.out.println("visitElement null");
        }
        if (ctx.expression() != null) {
            return visitExpression(ctx.expression());
        } else if (ctx.literalValue() != null) {
            return visitLiteralValue(ctx.literalValue());
        }
        return null;
    }

    /**
     * functionLit: 'func' function;
     *
     * @param ctx
     * @return
     */
    @Override
    public String visitFunctionLit(GolangParser.FunctionLitContext ctx) {
        return ConstantString.STRING_FUNC + visitFunction(ctx.function());
    }

    /**
     * function: signature block;
     * only when function is a top-level declaration, we label the block
     * else case: var = func() {}. we ignore this case.
     * @param ctx
     * @return
     */
    @Override
    public String visitFunction(GolangParser.FunctionContext ctx) {
        if(functionIndex != -1) {
            String localBlockName = ConstantString.LOCAL_BLOCK_FUNCTION;
            int localBlockId = newABlock(localBlockName);
            //push block stack
            blockStackForAFuncMeth.push(localBlockId);
        }

        String str = Configure.NULL_STRING;
        str += visitSignature(ctx.signature());
        str += visitBlock(ctx.block());

        //pop block stack
        if(functionIndex != -1) {
            blockStackForAFuncMeth.pop();
        }

        return str;
    }

    /**
     * block: '{' statementList '}';
     *
     * @param ctx
     * @return
     */
    @Override
    public String visitBlock(GolangParser.BlockContext ctx) {
        //only the block is unnamed, nested, then new a block.
        if(helperVisitor.isBlockInStatement(ctx)) {
            if(functionIndex != -1) {
                String localBlockName = ConstantString.LOCAL_BLOCK_UNNAMED_BLOCK;
                int localBlockId = newABlock(localBlockName);
                //push block stack
                blockStackForAFuncMeth.push(localBlockId);
            }
        }

        visitChildren(ctx);

        if(functionIndex != -1) {
            //pop block stack
            if (helperVisitor.isBlockInStatement(ctx)) {
                blockStackForAFuncMeth.pop();
            }
        }
        return Configure.LEFT_CURLY_BRACE + Configure.RIGHT_CURLY_BRACE;
    }

    /**
     * conversion: type '(' expression ','? ')';
     *
     * @param ctx
     * @return
     */
    @Override
    public String visitConversion(GolangParser.ConversionContext ctx) {
        String str = Configure.NULL_STRING;
        str += visitType(ctx.type());
        str += Configure.LEFT_PARENTHESES;
        str += visitExpression(ctx.expression());
        if (ctx.getChild(3) != null && ctx.getChild(3).getText().equals(Configure.COMMA)) {
            str += Configure.COMMA;
        }
        str += Configure.RIGHT_PARENTHESES;
        return str;
    }

    /**
     * selector: '.' IDENTIFIER;
     *
     * @param ctx
     * @return
     */
    @Override
    public String visitSelector(GolangParser.SelectorContext ctx) {
        return (Configure.DOT + ctx.IDENTIFIER().getText());
    }

    /**
     * index: '[' expression ']';
     *
     * @param ctx
     * @return
     */
    @Override
    public String visitIndex(GolangParser.IndexContext ctx) {
        return (Configure.LEFT_SQUARE_BRACKET + visitExpression(ctx.expression()) + Configure.RIGHT_SQUARE_BRACKET);
    }

    /**
     * slice: '[' (( expression? ':' expression? ) | ( expression? ':' expression ':' expression )) ']';
     *
     * @param ctx
     * @return
     */
    @Override
    public String visitSlice(GolangParser.SliceContext ctx) {
        if (ctx.expression().size() == 0) {
            return Configure.LEFT_SQUARE_BRACKET + Configure.STRING_COLON +Configure.RIGHT_SQUARE_BRACKET;
        } else if (ctx.expression().size() == 1) {
            if (ctx.getChild(0).getText().equals(Configure.STRING_COLON)) {
                return Configure.LEFT_SQUARE_BRACKET + Configure.STRING_COLON + visitExpression(ctx.expression(0)) + Configure.RIGHT_SQUARE_BRACKET;
            } else {
                return Configure.LEFT_SQUARE_BRACKET + visitExpression(ctx.expression(0)) + Configure.STRING_COLON + Configure.RIGHT_SQUARE_BRACKET;
            }
        } else if (ctx.expression().size() == 2) {
            if (ctx.getChildCount() == 3) {
                return Configure.LEFT_SQUARE_BRACKET + visitExpression(ctx.expression(0)) + Configure.STRING_COLON + visitExpression(ctx.expression(1)) + Configure.RIGHT_SQUARE_BRACKET;
            } else {
                return Configure.LEFT_SQUARE_BRACKET + Configure.STRING_COLON + visitExpression(ctx.expression(0)) + Configure.STRING_COLON + visitExpression(ctx.expression(1)) + Configure.RIGHT_SQUARE_BRACKET;
            }
        } else if (ctx.expression().size() == 3) {
            return Configure.LEFT_SQUARE_BRACKET
                    + visitExpression(ctx.expression(0)) + Configure.STRING_COLON
                    + visitExpression(ctx.expression(1)) + Configure.STRING_COLON
                    + visitExpression(ctx.expression(2)) + Configure.RIGHT_SQUARE_BRACKET;
        } else {
            return null;
        }
    }

    /**
     * typeAssertion: '.' '(' type ')';
     *
     * @param ctx
     * @return
     */
    @Override
    public String visitTypeAssertion(GolangParser.TypeAssertionContext ctx) {
        return ( Configure.STAR + Configure.LEFT_PARENTHESES + visitType(ctx.type()) + Configure.RIGHT_PARENTHESES);
    }

    /**
     * arguments: '(' ( ( expressionList | type ( ',' expressionList )? ) '...'? ','? )? ')';
     *
     * @param ctx
     * @return
     */
    @Override
    public String visitArguments(GolangParser.ArgumentsContext ctx) {
        if (ctx == null) {
            System.out.println("visitArguments null");
        }
        String str = Configure.LEFT_PARENTHESES;

        if (ctx.type() != null) {
            str += visitType(ctx.type());
            if (ctx.expressionList() != null) {
                str += Configure.COMMA;
                str += visitExpressionList(ctx.expressionList());
            }
        } else if (ctx.expressionList() != null) {
            str += visitExpressionList(ctx.expressionList());
        }
        str += Configure.RIGHT_PARENTHESES;
        return str;
    }

    /**
     * expressionList   : expression ( ',' expression )*;
     *
     * @param ctx
     * @return
     */
    @Override
    public String visitExpressionList(GolangParser.ExpressionListContext ctx) {
        if (ctx == null) {
            System.out.println("visitExpressionList  null");
        }
        String str = visitExpression(ctx.expression(0));
        if (ctx.expression().size() > 1) {
            for (int i = 1; i < ctx.expression().size(); i++) {
                str += (Configure.COMMA + visitExpression(ctx.expression(i)));
            }
        }
        return str;
    }


    /**
     * grammar: functionDecl: 'func' IDENTIFIER ( function | signature );
     * function: signature block;
     * signature: parameters result?;
     *
     * @param ctx
     * @return
     */
    @Override
    public String visitFunctionDecl(GolangParser.FunctionDeclContext ctx) {
        String functionName = ctx.IDENTIFIER().getText();
        String parameters = Configure.NULL_STRING;
        String returns = Configure.NULL_STRING;
        if (ctx.function() != null) {
            parameters = visitParameters(ctx.function().signature().parameters());
            if (ctx.function().signature().result() != null) {
                returns = visitResult(ctx.function().signature().result());
            }
        } else if (ctx.signature() != null) {
            parameters = visitParameters(ctx.signature().parameters());
            if (ctx.signature().result() != null) {
                returns = visitResult(ctx.signature().result());
            }
        }
        functionIndex = processTask.processFunction(functionName, parameters, returns, fileIndex);
        blockStackForAFuncMeth.clear();

        if (ctx.function() != null) {
            visitFunction(ctx.function()); //add operandVar into function
        }
        singleCollect.getEntities().get(fileIndex).addChildId(functionIndex);
        functionIndex = -1;
        blockStackForAFuncMeth.clear();
        return null;
    }




    /**
     * Grammar:   methodDecl: 'func' receiver IDENTIFIER ( function | signature );
     *
     * @param ctx
     * @return
     */
    @Override
    public String visitMethodDecl(GolangParser.MethodDeclContext ctx) {
        if(ctx == null) {
            return null;
        }
        String functionName = Configure.NULL_STRING;
        String receiverStr = Configure.NULL_STRING;
        String parameters = Configure.NULL_STRING;
        String returns = Configure.NULL_STRING;
        if(ctx.IDENTIFIER() != null) {
            functionName = ctx.IDENTIFIER().getText();
        }
        if(ctx.receiver() != null) {
            receiverStr = visitReceiver(ctx.receiver());
        }

        if (ctx.function() != null) {
            parameters = visitParameters(ctx.function().signature().parameters());
            if (ctx.function().signature().result() != null) {
                returns = visitResult(ctx.function().signature().result());
            }
        } else if (ctx.signature() != null) {
            parameters = visitParameters(ctx.signature().parameters());
            if (ctx.signature().result() != null) {
                returns = visitResult(ctx.signature().result());
            }
        }
        functionIndex =  processTask.processMethod(functionName, receiverStr, parameters, returns, fileIndex);
        blockStackForAFuncMeth.clear();

        if (ctx.function() != null) {
            visitFunction(ctx.function()); //add operandVar into function
        }
        singleCollect.getEntities().get(fileIndex).addChildId(functionIndex);
        functionIndex = -1;
        blockStackForAFuncMeth.clear();
        return null;
    }


    /**
     * receiver: parameters;
     * @param ctx
     * @return
     */
    @Override
    public String visitReceiver(GolangParser.ReceiverContext ctx) {
        String str = Configure.NULL_STRING;
        if (ctx.parameters() != null) {
            str = visitParameters(ctx.parameters());
        }
        return str;
    }


    /**
     * shortVarDecl: leftShortVarDecl ':=' rightShortVarDecl;
     * leftShortVarDecl: identifierList;
     * rightShortVarDecl: expressionList;
     *
     * @param ctx
     * @return
     */
    @Override
    public String visitShortVarDecl(GolangParser.ShortVarDeclContext ctx) {
        String leftOperands = visitLeftShortVarDecl(ctx.leftShortVarDecl());
        String rightExps = visitRightShortVarDecl(ctx.rightShortVarDecl());
        if (functionIndex != -1 && leftOperands != null && rightExps != null) {
            int localBlockId = -1;
            if (!blockStackForAFuncMeth.isEmpty()) {
                localBlockId = blockStackForAFuncMeth.peek();
            }
            processTask.processShortDeclVarInFunction(leftOperands, rightExps, functionIndex, localBlockId);
        }
        return (leftOperands + Configure.STRING_COLON + Configure.EQUAL + rightExps);
    }


    /**
     * leftShortVarDecl: identifierList;
     * @param ctx
     * @return
     */
    @Override
    public String visitLeftShortVarDecl(GolangParser.LeftShortVarDeclContext ctx) {
        String str = Configure.NULL_STRING;
        if(ctx != null) {
            str = visitIdentifierList(ctx.identifierList());
        }
        return str;
    }

    /**
     * rightShortVarDecl: expressionList;
     * @param ctx
     * @return
     */
    @Override
    public String visitRightShortVarDecl(GolangParser.RightShortVarDeclContext ctx) {
        String str = Configure.NULL_STRING;
        if(ctx != null) {
            str = visitExpressionList(ctx.expressionList());
        }
        return str;
    }

    /**
     * assignment: leftAssignment assign_op rightAssignment;
     * leftAssignment: expressionList;
     * rightAssignment: expressionList;
     * @param ctx
     * @return
     */
    @Override
    public String visitAssignment(GolangParser.AssignmentContext ctx) {
        String op = visitAssign_op(ctx.assign_op());
        String left = visitLeftAssignment(ctx.leftAssignment());
        String right = visitRightAssignment(ctx.rightAssignment());
        return (left + op + right);
    }


    /**
     * forStmt: 'for' ( expression | forClause | rangeClause )? block;
     * when entering, create a new for block and push blockStack, store this block.
     * when existing, pop blockStack.
     * @param ctx
     * @return
     */
    @Override
    public String visitForStmt(GolangParser.ForStmtContext ctx) {
        if(functionIndex != -1) {
            String localBlockName = ConstantString.LOCAL_BLOCK_FOR;
            int localBlockId = newABlock(localBlockName);
            //push block stack
            blockStackForAFuncMeth.push(localBlockId);
        }

        //visit children
        String str = ConstantString.STRING_FOR;
        if (ctx == null) {
            return str;
        }
        str += Configure.LEFT_PARENTHESES;
        if(ctx.expression() != null) {
            str += visitExpression(ctx.expression());
        }
        if(ctx.forClause() != null) {
            str += visitForClause(ctx.forClause());
        }
        if(ctx.rangeClause() != null) {
            str += visitRangeClause(ctx.rangeClause());
        }
        str += Configure.RIGHT_PARENTHESES;
        if(ctx.block() != null) {
            str += visitBlock(ctx.block());
        }

        if(functionIndex != -1) {
            //pop block stack
            blockStackForAFuncMeth.pop();
        }
        return str;
    }

    /**
     * ifStmtIf: 'if' (simpleStmt ';')? expression block;
     * when entering, create a new for if and push blockStack, store this block.
     * when existing, pop blockStack.
     * @param ctx
     * @return
     */
    @Override
    public String visitIfStmtIf(GolangParser.IfStmtIfContext ctx) {
        if(functionIndex != -1) {
            //new block
            String localBlockName = ConstantString.LOCAL_BLOCK_IF;
            int localBlockId = newABlock(localBlockName);
            //push block stack
            blockStackForAFuncMeth.push(localBlockId);
        }

        //visit children
        String str = ConstantString.STRING_IF;
        if (ctx == null) {
            return str;
        }
        str += ConstantString.STRING_IF;
        if (ctx.simpleStmt() != null) {
            str += visitSimpleStmt(ctx.simpleStmt());
        }
        if(ctx.expression() != null) {
            str += visitExpression(ctx.expression());
        }
        if(ctx.block() != null) {
            str += visitBlock(ctx.block());
        }

        if(functionIndex != -1) {
            //pop block stack
            blockStackForAFuncMeth.pop();
        }

        return str;
    }

    /**
     * fStmtElse: 'else' ( ifStmt | block );
     * when entering, create a new for else and push blockStack, store this block.
     * when existing, pop blockStack.
     * @param ctx
     * @return
     */
    @Override
    public String visitIfStmtElse(GolangParser.IfStmtElseContext ctx) {
        if(functionIndex != -1) {
            String localBlockName = ConstantString.LOCAL_BLOCK_ELSE;
            int localBlockId = newABlock(localBlockName);
            //push block stack
            blockStackForAFuncMeth.push(localBlockId);
        }

        //visit children
        String str = ConstantString.STRING_ELSE;
        if (ctx == null) {
            return str;
        }
        if(ctx.ifStmt() != null) {
            str += visitIfStmt(ctx.ifStmt());
        }
        if(ctx.block() != null) {
            str += visitBlock(ctx.block());
        }

        if(functionIndex != -1) {
            //pop block stack
            blockStackForAFuncMeth.pop();
        }
        return str;
    }

    /**
     * switchStmt: exprSwitchStmt | typeSwitchStmt;
     * when entering, create a new for switch and push blockStack, store this block.
     * when existing, pop blockStack.
     * @param ctx
     * @return
     */
    @Override
    public String visitSwitchStmt(GolangParser.SwitchStmtContext ctx) {
        if(functionIndex != -1) {
            String localBlockName = ConstantString.LOCAL_BLOCK_SWITCH;
            int localBlockId = newABlock(localBlockName);
            //push block stack
            blockStackForAFuncMeth.push(localBlockId);
        }

        //visit children
        String str = ConstantString.STRING_SWITCH;
        if (ctx == null) {
            return str;
        }
        if(ctx.exprSwitchStmt() != null) {
            str += visitExprSwitchStmt(ctx.exprSwitchStmt());
        }
        if(ctx.typeSwitchStmt() != null) {
            str += visitTypeSwitchStmt(ctx.typeSwitchStmt());
        }

        if(functionIndex != -1) {
            //pop block stack
            blockStackForAFuncMeth.pop();
        }
        return str;
    }

    /**
     * exprCaseClause: exprSwitchCase ':' statementList;
     * when entering, create a new swithc-case-clause block and push blockStack, store this block.
     * when existing, pop blockStack.
     * @param ctx
     * @return
     */
    @Override
    public String visitExprCaseClause(GolangParser.ExprCaseClauseContext ctx) {
        if(functionIndex != -1) {
            String localBlockName = ConstantString.LOCAL_BLOCK_SWITCH_CASE_CLAUSE;
            int localBlockId = newABlock(localBlockName);
            //push block stack
            blockStackForAFuncMeth.push(localBlockId);
        }

        //visit children
        String str = Configure.NULL_STRING;
        if (ctx == null) {
            return str;
        }
        if(ctx.exprSwitchCase() != null) {
            str += visitExprSwitchCase(ctx.exprSwitchCase());
        }
        str+= Configure.STRING_COLON;
        if(ctx.statementList() != null) {
            str += visitStatementList(ctx.statementList());
        }

        if(functionIndex != -1) {
            //pop block stack
            blockStackForAFuncMeth.pop();
        }
        return str;
    }

    /**
     * typeCaseClause: typeSwitchCase ':' statementList;
     * when entering, create a new swithc-case-clause block and push blockStack, store this block.
     * when existing, pop blockStack.
     * @param ctx
     * @return
     */
    @Override
    public String visitTypeCaseClause(GolangParser.TypeCaseClauseContext ctx) {
        if(functionIndex != -1) {
            String localBlockName = ConstantString.LOCAL_BLOCK_SWITCH_CASE_CLAUSE;
            int localBlockId = newABlock(localBlockName);
            //push block stack
            blockStackForAFuncMeth.push(localBlockId);
        }

        //visit children
        String str = Configure.NULL_STRING;
        if (ctx == null) {
            return str;
        }
        if(ctx.typeSwitchCase() != null) {
            str += visitTypeSwitchCase(ctx.typeSwitchCase());
        }
        str += Configure.STRING_COLON;
        if(ctx.statementList() != null) {
            str += visitStatementList(ctx.statementList());
        }

        if(functionIndex != -1) {
            //pop block stack
            blockStackForAFuncMeth.pop();
        }
        return str;
    }

    /**
     * selectStmt: 'select' '{' commClause* '}';
     * when entering, create a new select block and push blockStack, store this block.
     * when existing, pop blockStack.
     * @param ctx
     * @return
     */
    @Override
    public String visitSelectStmt(GolangParser.SelectStmtContext ctx) {
        if(functionIndex != -1) {
            String localBlockName = ConstantString.LOCAL_BLOCK_SELECT;
            int localBlockId = newABlock(localBlockName);
            //push block stack
            blockStackForAFuncMeth.push(localBlockId);
        }

        //visit children
        String str = ConstantString.SELECT;
        if (ctx == null) {
            return str;
        }
        if (ctx.commClause() != null && !ctx.commClause().isEmpty()) {
            for (GolangParser.CommClauseContext commClauseContext : ctx.commClause()) {
                str += visitCommClause(commClauseContext);
            }
        }

        if(functionIndex != -1) {
            //pop block stack
            blockStackForAFuncMeth.pop();
        }
        return str;
    }

    /**
     * commClause: commCase ':' statementList;
     * when entering, create a new select-clause block and push blockStack, store this block.
     * when existing, pop blockStack.
     * @param ctx
     * @return
     */
    @Override
    public String visitCommClause(GolangParser.CommClauseContext ctx) {
        if(functionIndex != -1) {
            String localBlockName = ConstantString.LOCAL_BLOCK_SELECT_CASE_CLAUSE;
            int localBlockId = newABlock(localBlockName);
            //push block stack
            blockStackForAFuncMeth.push(localBlockId);
        }

        //visit children
        String str = Configure.NULL_STRING;
        if (ctx == null) {
            return str;
        }

        if(ctx.commCase() != null) {
            str += visitCommCase(ctx.commCase());
        }
        str += Configure.STRING_COLON;
        if(ctx.statementList() != null) {
            str += visitStatementList(ctx.statementList());
        }

        if(functionIndex != -1) {
            //pop block stack
            blockStackForAFuncMeth.pop();
        }
        return str;
    }


    /**
     * new a localBlock
     * @param blockName
     * @return blockId
     */
    private int newABlock(String blockName) {
        //new block
        int parentBlockId = -1;
        if (!blockStackForAFuncMeth.isEmpty()) {
            parentBlockId = blockStackForAFuncMeth.peek();
        }
        int depth = blockStackForAFuncMeth.size();
        int blockId = processTask.processLocalBlock(functionIndex, parentBlockId, depth, blockName);

        return blockId;
    }


}//end class


