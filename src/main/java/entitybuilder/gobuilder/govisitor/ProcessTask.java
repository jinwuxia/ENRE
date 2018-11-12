package entitybuilder.gobuilder.govisitor;

import parser.parsego.GolangParser;
import entitybuilder.gobuilder.GoConstantString;
import uerr.*;
import entitybuilder.gobuilder.goentity.*;
import util.StringUtil;
import org.antlr.v4.runtime.tree.TerminalNode;
import util.Configure;
import util.Tuple;
import uerr.SingleCollect;


import java.util.ArrayList;

/**
 * further process the tree node which is being visiting in the first visit.
 */
public class ProcessTask {

    SingleCollect singleCollect = SingleCollect.getSingleCollectInstance();
    Configure configure = Configure.getConfigureInstance();
    ContextHelper helperVisitor = new ContextHelper();


    public AbsVAREntity getReceiver(String receiverStr) {
        String type = Configure.NULL_STRING;
        String name = Configure.NULL_STRING;
        if (receiverStr.startsWith(Configure.LEFT_PARENTHESES) && receiverStr.endsWith(Configure.RIGHT_PARENTHESES)) {
            receiverStr = receiverStr.substring(1, receiverStr.length() - 1);
        }
        String[] tmp = receiverStr.split(Configure.ONE_SPACE_STRING);
        //System.out.println(receiverStr + "; " + tmp);
        if (tmp.length == 2) {
            name = tmp[0];
            type = tmp[1];
        } else {
            type = tmp[0];
        }
        AbsVAREntity varEntity = new AbsVAREntity(-1, type, name);
        return varEntity;
    }

    /**
     *
     * @param methodIndex
     * @param receiverVar
     * @return
     */
    public void saveReceiver(int methodIndex, AbsVAREntity receiverVar) {
        int id = singleCollect.getCurrentIndex();
        receiverVar.setId(id);
        receiverVar.setParentId(methodIndex);
        receiverVar.setLocalBlockId(methodIndex);
        singleCollect.addEntity(receiverVar);

        ((MethodEntity) singleCollect.getEntities().get(methodIndex)).setReceiverVarId(id);

    }



    /**
     * precondition: functionEntity has been added
     * set var Id,
     * set parent id is functionIndex,
     * and save into uerr
     * add them to function's parameterList or return List
     *
     * @param functionIndex
     * @param vars
     */
    public void saveParameters(int functionIndex, ArrayList<AbsVAREntity> vars, String isParaOrRet) {
        for (AbsVAREntity varEntity : vars) {
            int varEntityIndex = singleCollect.getCurrentIndex();
            varEntity.setId(varEntityIndex);
            varEntity.setParentId(functionIndex);
            varEntity.setLocalBlockId(functionIndex);
            singleCollect.addEntity(varEntity);
            if (isParaOrRet.equals(GoConstantString.SAVE_TYPE_PARAMETER)) {
                ((AbsFUNEntity) singleCollect.getEntities().get(functionIndex)).addParameter(varEntityIndex);
            } else {
                ((AbsFUNEntity) singleCollect.getEntities().get(functionIndex)).addReturn(varEntityIndex);
            }
        }
    }


    /** add interfaceField into uerr,
     * and return its id.
     * its parentId is unknown.
     * @param type
     * @param name
     * @return
     */
    public int processInterfaceFieldAsType(String type, String name) {
        InterfaceFieldEntity interfaceFieldEntity = new InterfaceFieldEntity(type, name);
        int fieldIndex = singleCollect.getCurrentIndex();
        interfaceFieldEntity.setId(fieldIndex);
        interfaceFieldEntity.setParentId(-1);
        singleCollect.addEntity(interfaceFieldEntity);
        return fieldIndex;
    }


    /**
     * add interfaceField into uerr,
     * and return its id.
     * its parentId is unknown.
     * @param type
     * @param name
     * @param methodSignatureParas
     * @param methodSignatureReturns
     * @return
     */
    public int processInterfaceFieldAsMethod(String type, String name, String methodSignatureParas, String methodSignatureReturns) {
        ArrayList<AbsVAREntity> parameters = getVarFromParameters(methodSignatureParas);
        ArrayList<AbsVAREntity> returns = getVarFromParameters(methodSignatureReturns);

        InterfaceFieldEntity interfaceFieldEntity = new InterfaceFieldEntity(type, name, parameters, returns);
        int fieldIndex = singleCollect.getCurrentIndex();
        interfaceFieldEntity.setId(fieldIndex);
        interfaceFieldEntity.setParentId(-1);
        singleCollect.addEntity(interfaceFieldEntity);
        return fieldIndex;
    }


    /**
     * add structfield into uerr,
     * and return its id.
     * its parentId is unknown.
     * @param fieldType
     * @param fieldName
     * @return
     */
    public int processStructFieldAsNormal(String fieldType, String fieldName) {
        int fieldIndex = singleCollect.getCurrentIndex();
        StructFieldEntity structFieldEntity = new StructFieldEntity(fieldIndex, fieldType, fieldName);
        structFieldEntity.setParentId(-1);
        singleCollect.addEntity(structFieldEntity);
        return fieldIndex;
    }


    /**
     * add structfield into uerr,
     * and return its id.
     * its parentId is unknown.
     * @param fieldType
     * @param fieldName
     * @return
     */
    public int processStructFieldAsAnonymous(String fieldType, String fieldName) {
        int fieldIndex = singleCollect.getCurrentIndex();
        StructFieldEntity structFieldEntity = new StructFieldEntity(fieldIndex, fieldType, fieldName);
        structFieldEntity.setParentId(-1);
        singleCollect.addEntity(structFieldEntity);
        return fieldIndex;
    }


    public void processTypeSpec(GolangParser.TypeSpecContext ctx, String type, ArrayList<Integer> tmpEntitiesIds, int fileIndex) {
        String name = ctx.IDENTIFIER().getText();
        //System.out.println("operateTypeSpec: " + name);
        AbsEntity entity = null;
        if (type.equals(GoConstantString.STRUCT_TYPE)) {
            entity = new StructEntity(singleCollect.getCurrentIndex(), name);
            singleCollect.addEntity(entity);
            //System.out.println("operateTypeSpec1: " + name);
        } else if (type.equals(GoConstantString.INTERFACE_TYPE)) {
            entity = new InterfaceEntity(singleCollect.getCurrentIndex(), name);
            singleCollect.addEntity(entity);
        }

        if (entity != null) {
            //add its Fields
            if (!tmpEntitiesIds.isEmpty()) {
                singleCollect.getEntities().get(entity.getId()).addChildrenIds(tmpEntitiesIds);
                //for each field, set its belonging struct/interface id
                for (int fieldIndex : tmpEntitiesIds) {
                    singleCollect.getEntities().get(fieldIndex).setParentId(entity.getId());
                }
                //tmpEntitiesIds.clear();
            }
            //if this type is in the file scope
            if (ctx.parent != null && helperVisitor.isTopLevelDecl(ctx.getParent())) {
                singleCollect.getEntities().get(entity.getId()).setParentId(fileIndex);
                singleCollect.getEntities().get(fileIndex).addChildId(entity.getId());
            } else {
                singleCollect.getEntities().get(entity.getId()).setParentId(-1);
            }

        }
    }


    /**
     * add aliasType into uerr
     * @param fileIndex
     * @param type
     * @param name
     */
    public void processAliasType(int fileIndex, String type, String name) {
        int entityIndex = singleCollect.getCurrentIndex();
        AliasTypeEntity aliasTypeEntity = new AliasTypeEntity(entityIndex, type, name);
        aliasTypeEntity.setParentId(fileIndex);
        singleCollect.addEntity(aliasTypeEntity);
        singleCollect.getEntities().get(fileIndex).addChildId(entityIndex);
    }


    /**varSpec: identifierList ( type ( '=' expressionList )? | '=' expressionList )
     * process var in the file scope
     * @param varSpecContext
     * @param type
     */
    public void processVarDeclInFile(GolangParser.VarSpecContext varSpecContext, String type, int fileIndex) {
        //get name of var
        for (TerminalNode node : varSpecContext.identifierList().IDENTIFIER()) {
            AbsVAREntity varEntity = new AbsVAREntity(singleCollect.getCurrentIndex(), type, node.getText());
            varEntity.setParentId(fileIndex);
            varEntity.setLocalBlockId(fileIndex);
            singleCollect.addEntity(varEntity);
            singleCollect.getEntities().get(fileIndex).addChildId(varEntity.getId());
        }
    }


    /**
     * constSpec: identifierList ( type? '=' expressionList )?;
     * process constVar in file scope
     * @param ctx
     */
    public void processConstInFile(GolangParser.ConstSpecContext ctx, String type, int fileIndex) {
        for (TerminalNode terminalNode : ctx.identifierList().IDENTIFIER()) {
            ConstEntity constEntity = new ConstEntity(singleCollect.getCurrentIndex(), type, terminalNode.getText());
            constEntity.setParentId(fileIndex);
            constEntity.setLocalBlockId(fileIndex);
            singleCollect.addEntity(constEntity);
            singleCollect.getEntities().get(fileIndex).addChildId(constEntity.getId());
        }
    }


    public String getPackagePath(String fileFullPath) {
        String packagePath = StringUtil.deleteLastStrByPathDelimiter(fileFullPath);
        System.out.println("test: " + packagePath);
        String unifiedInputSrcPath = configure.getUnifiedInputSrcpath();
        int startIndex = packagePath.indexOf(unifiedInputSrcPath);

        /*
        substitute the input package dir by the imported form.
        this way can help to priextractor.goextractor.searcher the package which is imported in code.
        */
        String newPackagePath = configure.getUsageSrcPath()
                + packagePath.substring(startIndex + configure.getUnifiedInputSrcpath().length(), packagePath.length());
        //System.out.println("new package path = " + newPackagePath);
        return newPackagePath;
    }

    public String getNewFileFullPath(String fileFullPath) {
        String unifiedInputSrcPath = configure.getUnifiedInputSrcpath();
        int startIndex = fileFullPath.indexOf(unifiedInputSrcPath);
        String newFileFullPath = configure.getUsageSrcPath()
                + fileFullPath.substring(startIndex + unifiedInputSrcPath.length(), fileFullPath.length());
        //System.out.println("new file path: " + newFileFullPath);
        return newFileFullPath;
    }


    /**
     * varName type,varName (...)type.   or varName1 varName2 type,
     * @param parameterStr
     * @return
     */
    public ArrayList<AbsVAREntity> getVarFromParameters(String parameterStr) {
        if (parameterStr.indexOf(Configure.LEFT_PARENTHESES) == 0
                && parameterStr.indexOf(Configure.RIGHT_PARENTHESES) == parameterStr.length() -1 ) {
            parameterStr = parameterStr.substring(1, parameterStr.length() - 1);
        }
        ArrayList<AbsVAREntity> varEntities = new ArrayList<AbsVAREntity>();
        if(parameterStr.equals(Configure.NULL_STRING)) {
            return varEntities;
        }
        ArrayList<Tuple<String, String>> tmpVarList = new ArrayList<Tuple<String, String>>();
        String [] strArr = parameterStr.split(Configure.COMMA);
        for (String var : strArr) {
            String varName = Configure.NULL_STRING;
            String varType = Configure.NULL_STRING;
            //have type
            if (var.contains(Configure.ONE_SPACE_STRING)) {
                String [] tmp = var.split(Configure.ONE_SPACE_STRING);
                if (tmp.length == 2) {
                    varName = tmp[0];
                    varType = tmp[1];
                }
            }
            //not have type
            else {
                varName = var;
            }
            tmpVarList.add(new Tuple<String, String>(varType, varName));
        }
        tmpVarList = supplementType(tmpVarList);
        for (Tuple<String, String> var : tmpVarList) {
            AbsVAREntity varEntity = new AbsVAREntity(-1, var.x, var.y);
            varEntities.add(varEntity);
        }
        return varEntities;
    }

    /**
     * some parameter like : x,y int . so we need to supplement type for x.
     * if reuturn, it may only contains type without name, so we need to change it into type
     * @param tupleArrayList
     * @return
     */
    private ArrayList<Tuple<String, String>> supplementType(ArrayList<Tuple<String, String>> tupleArrayList) {
        //is like type1,type2,type3
        if(isReturnTypeList(tupleArrayList)) {
            for (int i = 0; i < tupleArrayList.size(); i++) {
                String type = tupleArrayList.get(i).y; // name is acturally the type
                tupleArrayList.get(i).setX(type);
                tupleArrayList.get(i).setY(Configure.NULL_STRING);
            }
            return tupleArrayList;
        }

        for (int i = 0; i < tupleArrayList.size(); i++) {
            String type = tupleArrayList.get(i).x;
            String possibleType = Configure.NULL_STRING;
            if (type.equals(Configure.NULL_STRING)) {
                for (int j = i + 1; j < tupleArrayList.size(); j++) {
                    possibleType = tupleArrayList.get(j).x;
                    if (!possibleType.equals(Configure.NULL_STRING)) {
                        break;
                    }
                }
            }
            if(!possibleType.equals(Configure.NULL_STRING)) {
                tupleArrayList.get(i).setX(possibleType);
            }
        }

        return tupleArrayList;
    }


    private boolean isReturnTypeList(ArrayList<Tuple<String, String>> tupleArrayList) {
        for (int i = 0; i < tupleArrayList.size(); i++) {
            String type = tupleArrayList.get(i).x;
            if (!type.equals(Configure.NULL_STRING)) {
                return false;
            }
        }
        return true;
    }


    public void processImport(String importNameAndPath, int fileIndex){
        String[] tmp = importNameAndPath.split(Configure.SEMI_COLON);
        String importName = tmp[0];
        String importPath = tmp[1];
        importPath = importPath.substring(1, importPath.length() - 1); //delete " and "
        ((AbsFILEntity) singleCollect.getEntities().get(fileIndex)).addImport(
                new Tuple<String, String>(importName, importPath));
    }



    public int searchPackageIndex(String packagePath) {
        //System.out.println("searchPackageIndex: searched-" + packagePath);
        ArrayList<AbsEntity> entities = singleCollect.getEntities();
        for (int i = 0; i < entities.size(); i++) {
            if (entities.get(i) instanceof AbsFLDEntity) {
                //System.out.println("searchPackageIndex: ing-" + ((AbsFLDEntity) entities.get(i)).getFullPath());
                if (((AbsFLDEntity) entities.get(i)).getFullPath().equals(packagePath)) {
                    return i;
                }
            }
        }
        return -1;
    }


    /**
     * process var in variableDeclaration the function
     * var here is surely a new variable, so save to uerr
     * @param node
     * @param type
     * @param value
     * @param functionIndex
     */
    public void processVarInFunction(TerminalNode node, String type, String value, int functionIndex, int localBlockId) {
        String name = node.getText();
        if(name.equals(Configure.BLANK_IDENTIFIER) || name.equals(GoConstantString.NIL)) {
            return;
        }
        String usage = GoConstantString.OPERAND_NAME_USAGE_SET;
        //it is not in localName, save
        saveLocalName(functionIndex, name, localBlockId, type, value, usage);

        AbsVAREntity varEntity = new AbsVAREntity(singleCollect.getCurrentIndex(), type, name);
        varEntity.setValue(value);
        varEntity.setParentId(functionIndex);
        varEntity.setLocalBlockId(localBlockId);
        singleCollect.addEntity(varEntity);
        singleCollect.getEntities().get(functionIndex).addChildId(varEntity.getId());

    }


    /**
     * process name (appear in identifierList of shortDecl)
     *  if inside a function, we need to add this uerr.
     *  it must be in function since shortDecl is valid only in function.
     *  var here is surely a new variable, so save to uerr
     * @param leftOperands
     * @param rightExps
     */
    public void processShortDeclVarInFunction(String leftOperands, String rightExps, int functionIndex, int localBlockId) {
        String[] leftNames = leftOperands.split(Configure.COMMA);
        String[] rightValues = rightExps.split(Configure.COMMA);
        for (int i = 0; i < leftNames.length; i++) {
            String name = leftNames[i];
            if(name.equals(Configure.BLANK_IDENTIFIER) || name.equals(GoConstantString.NIL)) {
                return;
            }
            String value = Configure.NULL_STRING;
            String usage = GoConstantString.OPERAND_NAME_USAGE_SET;
            String type = Configure.NULL_STRING;
            if (rightValues.length <= i) {
                value = rightValues[0];
            } else {
                value = rightValues[i];
            }
            //it 's not in localName, add this new uerr
            saveLocalName(functionIndex, name, localBlockId, type, value, usage);

            AbsVAREntity varEntity = new AbsVAREntity(singleCollect.getCurrentIndex(), type, name);
            varEntity.setValue(value);
            varEntity.setParentId(functionIndex);
            varEntity.setLocalBlockId(localBlockId);
            singleCollect.addEntity(varEntity);
            singleCollect.getEntities().get(functionIndex).addChildId(varEntity.getId());
        }
    }


    /**
     * constSpec: identifierList ( type? '=' expressionList )?;
     * var here is surely a new variable, so save to uerr
     * process constVar in function scope
     * @param ctx
     */
    public void processConstInFunction(GolangParser.ConstSpecContext ctx, String type, int functionIndex, int localBlockId) {
        for (TerminalNode terminalNode : ctx.identifierList().IDENTIFIER()) {
            ConstEntity constEntity = new ConstEntity(singleCollect.getCurrentIndex(), type, terminalNode.getText());
            constEntity.setParentId(functionIndex);
            constEntity.setLocalBlockId(localBlockId);
            singleCollect.addEntity(constEntity);
            singleCollect.getEntities().get(functionIndex).addChildId(constEntity.getId());

            //it 's not in localName, add this new uerr
            String name = terminalNode.getText();
            String value = Configure.NULL_STRING;
            String usage = GoConstantString.OPERAND_NAME_USAGE_USE;
            //it 's not in localName, add this new uerr
            saveLocalName(functionIndex, name, localBlockId, type, value, usage);
        }
    }


    private void saveLocalName(int functionIndex, String name, int localBlockId, String type, String value, String usage) {
        LocalName localNameEntity = new LocalName(name, localBlockId, type, value);
        localNameEntity.updateUsage(usage);//add to function's localNames
        ((AbsFUNEntity) singleCollect.getEntities().get(functionIndex)).addLocalName(localNameEntity);
    }

    /** we don't know the role of the operand.
     * process the operandName inside a funnction,
     * (1) if in leftAssignment and in LocalNameList, then update usage "Set"
     *     if in leftAssignment and not in LocalNameList,  then add and update usage "Set"
     *
     *
     * (2) if in rightAssignemnt/, and in LocalNameList, update usage: "USE"
     *     if in rightAssignemnt/, and not in LocalNameList, then add and pdate usage: "USE"
     * (3) if not in assignment(maybe a shortVarDecl or a varDecl's right), and a name appear in LocalNameList, update usage: "USE"
     *    if not in assignment, and a name not appear in LocalNameList, add and update "USE"
     *           others: a parameter, a return, a package, or  function
     */
    public void processOperandNameInFunction(String str, GolangParser.OperandNameContext ctx,
                                             int functionIndex, int localBlockId) {
        String name = str.split("\\.")[0];
        if(name.equals(Configure.BLANK_IDENTIFIER) || name.equals(GoConstantString.NIL) ) {
            return;
        }
        AbsFUNEntity functionEntity = (AbsFUNEntity) singleCollect.getEntities().get(functionIndex);
        //if 1
        if (helperVisitor.isOperandNameInLeftAssignment(ctx)) {
            String usage = GoConstantString.OPERAND_NAME_USAGE_SET;
            int id = functionEntity.searchLocalName(name, localBlockId);
            if(id == -1) {
                LocalName localName = new LocalName(name, localBlockId, Configure.NULL_STRING, Configure.NULL_STRING);
                localName.updateUsage(usage);
                ((AbsFUNEntity) singleCollect.getEntities().get(functionIndex)).addLocalName(localName);
            }
            else
            {
                ((AbsFUNEntity) singleCollect.getEntities().get(functionIndex)).getLocalNames().get(id).updateUsage(usage);
            }
        }
        else if (helperVisitor.isOperandNameInRightAssignment(ctx) ||
                !helperVisitor.isOperandNameInAssignment(ctx)) {
            int id = functionEntity.searchLocalName(name, localBlockId);
            String usage = GoConstantString.OPERAND_NAME_USAGE_USE;
            if(id == -1) {
                String scope = GoConstantString.SCOPE_ONE;
                String type = Configure.NULL_STRING;
                String value = Configure.NULL_STRING;
                saveLocalName(functionIndex, name, localBlockId, type, value, usage);
            }
            else
            {
                ((AbsFUNEntity) singleCollect.getEntities().get(functionIndex)).getLocalNames().get(id).updateUsage(usage);
            }
        }
    }


    public int processPackageDecl(String packagePath, String packageName) {
        //new packageEntity
        int packageIndex = singleCollect.getCurrentIndex();
        //System.out.println("packagePath:" + packagePath);
        //System.out.println("packageName:" + packageName);
        //System.out.println("packageIndex:" + Integer.toString(packageIndex));

        AbsFLDEntity currentPackageEntity = new AbsFLDEntity(packageIndex, packagePath, packageName);
        currentPackageEntity.setParentId(-1);
        singleCollect.addEntity(currentPackageEntity);
        return packageIndex;
    }

    public int processFile(int packageIndex, String fileFullPath) {
        //System.out.println("fileFullPath:" + fileFullPath);
        AbsFILEntity fileEntity = new AbsFILEntity();
        fileEntity.setParentId(packageIndex);
        fileEntity.setId(singleCollect.getCurrentIndex());
        fileEntity.setName(getNewFileFullPath(fileFullPath));
        fileEntity.setSimpleName();
        singleCollect.addEntity(fileEntity);
        int fileIndex = fileEntity.getId();

        singleCollect.getEntities().get(packageIndex).addChildId(fileIndex);
        return fileIndex;
    }


    /**
     * process Function , save uerr
     * @param functionName
     * @param parameters
     * @param returns
     * @param fileIndex
     */
    public int processFunction(String functionName, String parameters, String returns, int fileIndex) {
        ArrayList<AbsVAREntity> parameterVars = getVarFromParameters(parameters);
        ArrayList<AbsVAREntity> returnVars = getVarFromParameters(returns);

        int functionIndex = singleCollect.getCurrentIndex();
        AbsFUNEntity functionEntity = new AbsFUNEntity(functionName);
        functionEntity.setId(functionIndex);
        functionEntity.setParentId(fileIndex); //functionDecl only appear in the topLevel
        singleCollect.addEntity(functionEntity);

        //set id, parentId and save into uerr, add into function's parameter
        saveParameters(functionIndex, parameterVars, GoConstantString.SAVE_TYPE_PARAMETER);
        //set id, parentId and save into uerr, add into function's return
        saveParameters(functionIndex, returnVars, GoConstantString.SAVE_TYPE_RETURN);
        return functionIndex;
    }


    /**
     * process method, store into uerr
     * @param functionName
     * @param receiverStr
     * @param parameters
     * @param returns
     * @param fileIndex
     * @return
     */
    public int processMethod(String functionName, String receiverStr, String parameters, String returns, int fileIndex) {
        AbsVAREntity receiverVar = getReceiver(receiverStr);
        ArrayList<AbsVAREntity> parameterVars = getVarFromParameters(parameters);
        ArrayList<AbsVAREntity> returnVars = getVarFromParameters(returns);

        int functionIndex = singleCollect.getCurrentIndex();
        MethodEntity functionEntity = new MethodEntity(functionName);
        functionEntity.setId(functionIndex);
        functionEntity.setParentId(fileIndex); //MethodDecl only appear in the topLevel
        singleCollect.addEntity(functionEntity);

        //set id, parentId, blockId, and save into uerr, add into function's receiver
        saveReceiver(functionIndex, receiverVar);
        //set id, parentId, blockId, and save into uerr, add into function's parameter
        saveParameters(functionIndex, parameterVars, GoConstantString.SAVE_TYPE_PARAMETER);
        //set id, parentId, blockId, and save into uerr, add into function's return
        saveParameters(functionIndex, returnVars, GoConstantString.SAVE_TYPE_RETURN);
        return functionIndex;
    }


    /**
     * process the called original str
     * @param functionIndex
     * @param str
     */
    public void processMethodCallPrimaryExpr(int functionIndex, String str) {
        if (functionIndex != -1) {
            if (singleCollect.getEntities().get(functionIndex) instanceof AbsFUNEntity) {
                ((AbsFUNEntity) singleCollect.getEntities().get(functionIndex)).addCalledFunction(str);
            } else if (singleCollect.getEntities().get(functionIndex) instanceof MethodEntity) {
                ((MethodEntity) singleCollect.getEntities().get(functionIndex)).addCalledFunction(str);
            }
        }
    }


    /**
     * genearate and add localBlock into functionEntity's member, and return blockId
     * @param functionIndex
     * @param parentBlockId
     * @param depth
     * @param blockName
     * @return
     */
    public int processLocalBlock(int functionIndex, int parentBlockId, int depth, String blockName)
    {
        AbsFUNEntity functionEntity = (AbsFUNEntity) singleCollect.getEntities().get(functionIndex);
        int blockId = functionEntity.getLocalBlocks().size();
        LocalBlock localBlock = new LocalBlock(blockId, blockName, parentBlockId, depth);
        ((AbsFUNEntity) singleCollect.getEntities().get(functionIndex)).addLocalBlock(localBlock);
        return blockId;
    }


}

