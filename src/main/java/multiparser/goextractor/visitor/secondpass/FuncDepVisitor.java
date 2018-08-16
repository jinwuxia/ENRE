package multiparser.goextractor.visitor.secondpass;

import multiparser.entity.*;
import multiparser.goextractor.search.*;
import multiparser.goextractor.ConstantString;
import multiparser.util.Tuple;
import multiparser.extractor.SingleCollect;

import java.util.ArrayList;
import java.util.Map;

public class FuncDepVisitor {
    private SingleCollect singleCollect = SingleCollect.getSingleCollectInstance();
    private NameSearchPackage nameSearchPackage = new NameSearchPackage();
    private NameSearchFunction nameSearchFunction = new NameSearchFunction();
    private NameSearchStruct nameSearchStruct = new NameSearchStruct();
    private NameSearchAliasType nameSearchAliasType = new NameSearchAliasType();
    private NameSearchInterface nameSearchInterface = new NameSearchInterface();


    public void setFuncDeps() {
        setCalls();
        setParameters();
        setReturns();
        setUses();
        setSets();
    }

    /**
     * find add function-use-varId relations
     */
    private void setUses() {
        for (Entity entity : singleCollect.getEntities()) {
            if(entity instanceof FunctionEntity) {
                int functionId = entity.getId();
                Map<String, ArrayList<String>> name2usage = ((FunctionEntity) entity).getName2UsageMap();
                Map<String, Integer> name2Id = ((FunctionEntity) entity).getName2IdMap();
                for (Map.Entry<String, ArrayList<String>> entry : name2usage.entrySet()) {
                    String varName = entry.getKey();
                    for (String usage : entry.getValue()) {
                        if (usage.equals(ConstantString.OPERAND_NAME_USAGE_USE)) {
                            if (name2Id.containsKey(varName)) {
                                int varId = name2Id.get(varName);
                                saveRelation(functionId, varId, ConstantString.RELATION_USE, ConstantString.RELATION_USED_BY);
                            }
                        }
                    }
                }
            }
        }
    }


    /**
     * find add function-set-varId relations
     */
    private void setSets() {
        for (Entity entity : singleCollect.getEntities()) {
            if(entity instanceof FunctionEntity) {
                int functionId = entity.getId();
                Map<String, ArrayList<String>> name2usage = ((FunctionEntity) entity).getName2UsageMap();
                Map<String, Integer> name2Id = ((FunctionEntity) entity).getName2IdMap();
                for (Map.Entry<String, ArrayList<String>> entry : name2usage.entrySet()) {
                    String varName = entry.getKey();
                    for (String usage : entry.getValue()) {
                        if (usage.equals(ConstantString.OPERAND_NAME_USAGE_SET)) {
                            if (name2Id.containsKey(varName)) {
                                int varId = name2Id.get(varName);
                                saveRelation(functionId, varId, ConstantString.RELATION_SET, ConstantString.RELATION_SETED_BY);
                            }
                        }
                    }
                }
            }
        }
    }


    /**
     * find add function-parameterType relations
     */
    private void setParameters() {
        for(Entity entity : singleCollect.getEntities()) {
            if(entity instanceof FunctionEntity) {
                int functionId = entity.getId();
                for (int parameterId : ((FunctionEntity) entity).getParameters()) {
                    if(((VarEntity) singleCollect.getEntities().get(parameterId)).getTypeId() != -1) {
                        int typeId = ((VarEntity) singleCollect.getEntities().get(parameterId)).getTypeId();
                        saveRelation(functionId, typeId, ConstantString.RELATION_PARAMETER, ConstantString.RELATION_PARAMETERED_BY);
                    }
                }
            }
        }
    }


    /**
     * find add function-returnType relations
     */
    private void setReturns() {
        for (Entity entity : singleCollect.getEntities()) {
            if(entity instanceof FunctionEntity) {
                int functionId = entity.getId();
                for (int returnId : ((FunctionEntity) entity).getReturns()) {
                    if(((VarEntity) singleCollect.getEntities().get(returnId)).getTypeId() != -1) {
                        int typeId = ((VarEntity) singleCollect.getEntities().get(returnId)).getTypeId();
                        saveRelation(functionId, typeId, ConstantString.RELATION_RETURN, ConstantString.RELATION_RETURNED_BY);
                    }
                }
            }
        }
    }



    /**
     * find all function calls
     */
    private void setCalls() {
        for (Entity functionOrMethodEntity : singleCollect.getEntities()) {
            if(functionOrMethodEntity instanceof FunctionEntity) {
                int callerEntityId = functionOrMethodEntity.getId(); //caller multiparser.entity id

                //for debug
                int callerFileId = functionOrMethodEntity.getParentId();
                String callerFileName = singleCollect.getEntities().get(callerFileId).getName();

                //this tmpCalleeEntityIds for save all id (including -1); because saveRelation only save ones without -1.
                ArrayList<Integer> tmpCalleeEntityIds = new ArrayList<Integer>();
                for (int calleeIndex = 0; calleeIndex < ((FunctionEntity) functionOrMethodEntity).getCalledFunctions().size(); calleeIndex++) {
                    //System.out.println("call relation: callerName: " + functionOrMethodEntity.getName() + ";  file: " + callerFileName);

                    String originalCalleeStr = ((FunctionEntity) functionOrMethodEntity).getCalledFunctions().get(calleeIndex);
                    //if f1(f2()) or f1().f2(), substitute the first call, make the str has only one call with one ().
                    String newCalleeStr = simplifyCalleeStr(originalCalleeStr, calleeIndex, ((FunctionEntity) functionOrMethodEntity).getCalledFunctions(), tmpCalleeEntityIds);
                    String[] tmp = newCalleeStr.split("\\("); //delete parameter(..)
                    //System.out.println("tmp split: " + tmp);
                    String newCalleeName = tmp[0];
                    int calleeEntityId = searchFunctionOrMethod(callerEntityId, newCalleeName);
                    tmpCalleeEntityIds.add(calleeEntityId);

                    if (calleeEntityId != -1) {
                        saveRelation(callerEntityId, calleeEntityId, ConstantString.RELATION_CALL, ConstantString.RELATION_CALLED_BY);
                        //for debug
                        String calleeFileName = "";
                        int calleeFileId = singleCollect.getEntities().get(calleeEntityId).getParentId();
                        if (calleeFileId != -1) {
                            calleeFileName = singleCollect.getEntities().get(calleeFileId).getName();
                        }
                        //System.out.println("calleeName: orig=" + originalCalleeStr  + ",  new=" + newCalleeStr + ", new=" + newCalleeName + ", file=" + calleeFileName);
                    }
                    else { //it is not found , or the callee is a system method/function //for debug
                        //System.out.println("calleeName: orig=" + originalCalleeStr  + ",  new=" + newCalleeStr + ", new=" + newCalleeName + ", file=-1");
                    }
                }
            }
        }
    }


    /**
     * if f1(f2()) or f1().f2(), substitute the first call.
     * After processing, the newCalleeStr has only one call with one ().
     * @param originalCalleeStr original text form of the calleeStr
     * @param calleeIndex
     * @param calleeFunctionStrs
     * @param calleeFunctionIds
     * @return return the outlayer callee form, which has only one ().
     */
    private String simplifyCalleeStr(String originalCalleeStr, int calleeIndex, ArrayList<String> calleeFunctionStrs, ArrayList<Integer> calleeFunctionIds) {
        String newCalleeStr = originalCalleeStr;

        //the return Type for substituting the matchedStr
        String newSubStr = ConstantString.UNKNOWN_TYPE;
        String preStr = "";
        if (calleeIndex > 0) {
            //because the order of functionsStr in calledFunctions, we only need look at the currentIndex -1
            preStr = calleeFunctionStrs.get(calleeIndex - 1);
            int preEntityId = calleeFunctionIds.get(calleeIndex - 1);
            if (preEntityId != -1) {
                // use the first return as the return type.???????????????????? may have some issues.
                // ConstantString.CUSTOME_TYPE is used to label that this type is the return type of a function or methods.
                //int firstReturnId = ((FunctionEntity) singleCollect.getEntities().get(preEntityId)).getReturns().get(0);
                newSubStr = ConstantString.CUSTOME_TYPE + Integer.toString(preEntityId);
            }
        }
        if(!preStr.equals("")) {
            int startIndex = originalCalleeStr.indexOf(preStr);
            //if the full match, it means the same function is called several times.
            if (startIndex != -1 && !originalCalleeStr.equals(preStr)) {
                int endIndex = startIndex + preStr.length(); //open range
                newCalleeStr = originalCalleeStr.substring(0, startIndex) + newSubStr + originalCalleeStr.substring(endIndex, originalCalleeStr.length());
            }
        }
        return newCalleeStr;
    }


    /**
     * For callerEntity, find its callee's entityId according to calleeName
     * @param callerEntityId  the multiparser.entity id of caller
     * @param calleeName     function or method name, without parameter "(..)"
     * @return               the multiparser.entity id of callee
     */
    private int searchFunctionOrMethod(int callerEntityId, String calleeName) {
        int calleeEntityId = -1;
        String[] calleeNameArr = calleeName.split("\\.");

        //CaseA: f; selector = 0, calleeName is just functionName
        if (calleeNameArr.length == 1 && nameSearchFunction.isFunctionName(calleeName, callerEntityId)) {
            calleeEntityId = nameSearchFunction.getIdByName(calleeName, callerEntityId);
        }
        //CaseB: package.function(); selector = 1, and x0 is package, x1
        else if (calleeNameArr.length == 2 && nameSearchFunction.isPackageName(calleeNameArr[0], callerEntityId)){
            int calleePackageId = nameSearchFunction.getIdByName(calleeNameArr[0], callerEntityId);
            calleeEntityId = nameSearchPackage.findFunctionInPackage(calleeNameArr[1], calleePackageId);
        }
        //CaseC: var.method() or var1.var2.method()
        else if (calleeNameArr.length >= 2 && nameSearchFunction.isVarName(calleeNameArr[0], callerEntityId)) {
            calleeEntityId = searchFunctionOrMethodCaseC(calleeNameArr, callerEntityId);
        }

        //CaseD: MYT1.method();// MyT1 is a previous function()'s id
        else if (isLabeledBefore(calleeNameArr[0])) {
            calleeEntityId = searchFunctionOrMethodCaseD(calleeNameArr);
        }

        //CaseE: package.var.method() or package.var1.var2.method()
        else if (calleeNameArr.length >= 3 && nameSearchFunction.isPackageName(calleeNameArr[0], callerEntityId)){
            calleeEntityId = searchFunctionOrMethodCaseE(calleeNameArr, callerEntityId);
        }
        else {
            //System.out.println("searchFunctionOrMethod: no such case!");
        }
        if(calleeEntityId != -1 && singleCollect.getEntities().get(calleeEntityId) instanceof FunctionEntity) {
            return calleeEntityId;
        }
        return -1;
    }




    /**
     * find the methodId by name.
     * //CaseC: var.method() or var1.member.method() or var1.member1.member2.method()
     * // var may be a structType, aliasType, interfaceType.
     * @param calleeNameArr
     * @param callerEntityId
     * @return
     */
    public int searchFunctionOrMethodCaseC(String[] calleeNameArr, int callerEntityId) {
        int methodId = -1;
        int varId = nameSearchFunction.getIdByName(calleeNameArr[0], callerEntityId);
        int typeId = ((VarEntity) singleCollect.getEntities().get(varId)).getTypeId();

        for (int i = 1; i < calleeNameArr.length - 1; i++) {
            String fieldName = calleeNameArr[i];
            if(typeId != -1) {
                int structFieldId = nameSearchStruct.findFieldInStructAndEmbededStructs(fieldName, typeId);
                if (structFieldId != -1) {
                    typeId = ((StructFieldEntity) singleCollect.getEntities().get(structFieldId)).getTypeId();
                }

            }
            else {
                return methodId;
            }
        }
        String methodName = calleeNameArr[calleeNameArr.length - 1];
        methodId = searchMethodInType(methodName, typeId);
        return methodId;
    }

    /**
     * check whether a string can be converted into integer
     * @param str
     * @return
     */
    private boolean isIntegerParsable(String str) {
        try{
            Integer.valueOf(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


    /**
     * //CaseD: MYT1.method();// MyT1 is CUSTOME_TYPE + previous_function()_id
     * @param calleeNameArr
     * @return
     */
    public int searchFunctionOrMethodCaseD(String[] calleeNameArr) {
        if(calleeNameArr.length != 2) {
            return -1;
        }
        int methodId = -1;
        String mycustomeType = calleeNameArr[0];
        String calleeMethodName = calleeNameArr[1];
        int previousFunctionId = -1;
        if(mycustomeType.startsWith(ConstantString.CUSTOME_TYPE)) {
            String tmp = mycustomeType.substring(ConstantString.CUSTOME_TYPE.length(), mycustomeType.length());
            if(tmp.equals("-1")) {
                previousFunctionId = -1;
            }
            else if (isIntegerParsable(tmp)){
                //System.out.println("mycustomeType: " + mycustomeType + ";  tmp: " + tmp);
                previousFunctionId = Integer.parseInt(tmp);
            }
            else {
                previousFunctionId = -1;
            }
        }
        if(previousFunctionId != -1 &&
                !(((FunctionEntity) singleCollect.getEntities().get(previousFunctionId)).getReturns().isEmpty())
                ) {
            int firstReturnId = ((FunctionEntity) singleCollect.getEntities().get(previousFunctionId)).getReturns().get(0);
            int firstReturnTypeId = ((VarEntity) singleCollect.getEntities().get(firstReturnId)).getTypeId();
            if(firstReturnTypeId != -1) {
                methodId = searchMethodInType(calleeMethodName, firstReturnTypeId);
            }
        }
        return methodId;
    }


    /**
     * //CaseE: package.var.method() or package.var1.member.method()
     * @param calleeNameArr
     * @param callerEntityId
     * @return
     */
    public int searchFunctionOrMethodCaseE(String[] calleeNameArr, int callerEntityId) {
        int methodId = -1;
        String packageName = calleeNameArr[0];
        int packageId = nameSearchFunction.getIdByName(packageName, callerEntityId);
        int varId = -1;
        int typeId = -1;
        if(packageId != -1) {
            String varName = calleeNameArr[1];
            varId = nameSearchPackage.findVarInPackage(varName, packageId);
            if(varId == -1) {
                return methodId;
            }
            typeId = ((VarEntity) singleCollect.getEntities().get(varId)).getTypeId();
        }

        for (int i = 2; i < calleeNameArr.length - 1; i++) {
            if(typeId != -1) {
                String fieldName = calleeNameArr[i];
                int structFieldId = nameSearchStruct.findFieldInStructAndEmbededStructs(fieldName, typeId);
                typeId = ((StructFieldEntity) singleCollect.getEntities().get(structFieldId)).getTypeId();
            }
            else {
                return methodId;
            }
        }

        String methodName = calleeNameArr[calleeNameArr.length - 1];
        methodId = searchMethodInType(methodName, typeId);
        return methodId;
    }


    /**
     * whether the string is added or changed by us or not
     * @param typeName
     * @return
     */
    private boolean isLabeledBefore(String typeName) {
        if (typeName.startsWith(ConstantString.CUSTOME_TYPE)) {
            return true;
        }
        return false;
    }

    /**
     * multiparser.goextractor.search method in a Type (structType, AliasType, InterfaceType)
     * @param methodName
     * @param typeId
     * @return
     */
    private int searchMethodInType(String methodName, int typeId) {
        int methodId = -1;
        if (typeId == -1) {
            return -1;
        }
        if(singleCollect.getEntities().get(typeId) instanceof StructEntity) {
            methodId = nameSearchStruct.findMethodInStructAndEmbededStructs(methodName, typeId);
        }
        else if (singleCollect.getEntities().get(typeId) instanceof AliasTypeEntity) {
            methodId = nameSearchAliasType.findMethodInAliasType(methodName, typeId);
        }
        else if(singleCollect.getEntities().get(typeId) instanceof InterfaceEntity) {
            methodId = nameSearchInterface.findMethodInInfAndEmbededInfs(methodName, typeId);
        }
        return methodId;
    }


    /**
     * relationType1: entityId1 -> entityId2
     * relationType2: entityId2 -> entityId1
     * @param entityId1
     * @param entityId2
     * @param relationType1
     * @param relationType2
     */
    private void saveRelation(int entityId1, int entityId2, String relationType1, String relationType2) {
        Tuple<String, Integer> relation1 =
                new Tuple<String, Integer>(relationType1, entityId2);
        singleCollect.getEntities().get(entityId1).addRelation(relation1);

        Tuple<String, Integer> relation2 =
                new Tuple<String, Integer>(relationType2, entityId1);
        singleCollect.getEntities().get(entityId2).addRelation(relation2);
    }


}
