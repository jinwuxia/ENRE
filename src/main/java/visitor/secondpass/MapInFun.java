package visitor.secondpass;

import entity.*;
import util.ConstantString;
import util.Tuple;
import visitor.SingleCollect;

public class MapInFun {

    SingleCollect singleCollect = SingleCollect.getSingleCollectInstance();
    /**
     * to refine localNames,
     * if localVar(var, shortvar, const), parameter, return, global var, function, then find id, and role
     *
     */
    public void buildNameSearchTable() {
        for(Entity functionEntity : singleCollect.getEntities()) {
            if(functionEntity instanceof FunctionEntity) {
                int functionId = functionEntity.getId();
                for(LocalName localName : ((FunctionEntity) singleCollect.getEntities().get(functionId)).getLocalNames()) {
                    int parameterId = getIdIfParameter(localName.getName(), functionId);
                    if(parameterId != -1) {//modify role, add 3 maps
                        processAsPara(localName, parameterId, functionId);
                        continue;
                    }

                    int returnId = getIdIfReturn(localName.getName(), functionId);
                    if (returnId != -1) {//modify role, add 3 amps
                        processAsRet(localName, returnId, functionId);
                        continue;
                    }

                    int packageId = getIdIfPackage(localName.getName(), functionId);
                    if(packageId != -1) { //modify role, add  2 maps without usage
                        processAsPkg(localName, packageId, functionId);
                        continue;
                    }

                    int usedfuncId = getIdIfFunction(localName.getName(), functionId);
                    if(usedfuncId != -1) {   //modify role, add 2 map without usages
                        processAsFun(localName, usedfuncId, functionId);
                        continue;

                    }

                    int localVarId = getIdIfLocalVar(localName.getName(), functionId);
                    if(localVarId != -1) {//modify role, add 3 map without usages
                        processAsLocalVar(localName, localVarId, functionId);
                    }

                    int globalVarId = getIdIfGlobalVar(localName.getName(), functionId);
                    if(globalVarId != -1) {//modify role, add 3 map without usages
                        processAsGlobalVar(localName, globalVarId, functionId);
                    }
                }
            }
        }
    }



    /**
     * if parameter, added three maps
     * @param localName
     * @param returnId
     */
    private void processAsRet(LocalName localName, int returnId, int functionId) {
        String name = localName.getName();
        String role = ConstantString.OPERAND_NAME_ROLE_RET;

        ((FunctionEntity) singleCollect.getEntities().get(functionId)).addName2Id(name, returnId);
        ((FunctionEntity) singleCollect.getEntities().get(functionId)).addName2Role(name, role);
        for(String usage : localName.getUsages()) {
            ((FunctionEntity) singleCollect.getEntities().get(functionId)).addName2Usage(name, usage);
        }
    }

    /**
     * if returns, added three maps
     * @param localName
     * @param parameterId
     */
    private void processAsPara(LocalName localName, int parameterId, int functionId) {
        String name = localName.getName();
        String role = ConstantString.OPERAND_NAME_ROLE_PAR;

        ((FunctionEntity) singleCollect.getEntities().get(functionId)).addName2Id(name, parameterId);
        ((FunctionEntity) singleCollect.getEntities().get(functionId)).addName2Role(name, role);
        for(String usage : localName.getUsages()) {
            ((FunctionEntity) singleCollect.getEntities().get(functionId)).addName2Usage(name, usage);
        }
    }


    /**
     * if package, added two maps without usage
     * @param localName
     * @param packageId
     */
    private void processAsPkg(LocalName localName, int packageId, int functionId) {
        String name = localName.getName();
        String role = ConstantString.OPERAND_NAME_ROLE_PKG;

        ((FunctionEntity) singleCollect.getEntities().get(functionId)).addName2Id(name, packageId);
        ((FunctionEntity) singleCollect.getEntities().get(functionId)).addName2Role(name, role);
    }

    /**
     * if function, added two maps without usage
     * @param localName
     * @param localFunctionId
     * @param functionId
     */
    private void processAsFun(LocalName localName, int localFunctionId, int functionId) {
        String name = localName.getName();
        String role = ConstantString.OPERAND_NAME_ROLE_FUN;

        ((FunctionEntity) singleCollect.getEntities().get(functionId)).addName2Id(name, localFunctionId);
        ((FunctionEntity) singleCollect.getEntities().get(functionId)).addName2Role(name, role);
    }


    /**
     * if local var(var, constant) added three maps
     * @param localName
     * @param functionId
     */
    private void processAsLocalVar(LocalName localName, int localVarId, int functionId) {
        String name = localName.getName();
        String role = ConstantString.OPERAND_NAME_ROLE_LOC_VAR;

        ((FunctionEntity) singleCollect.getEntities().get(functionId)).addName2Id(name, localVarId);
        ((FunctionEntity) singleCollect.getEntities().get(functionId)).addName2Role(name, role);
        for(String usage : localName.getUsages()) {
            ((FunctionEntity) singleCollect.getEntities().get(functionId)).addName2Usage(name, usage);
        }
    }

    /**
     * if global var(var, constant) added three maps
     * @param localName
     * @param globalVarId
     * @param functionId
     */
    private void processAsGlobalVar(LocalName localName, int globalVarId, int functionId) {
        String name = localName.getName();
        String role = ConstantString.OPERAND_NAME_ROLE_GLO_VAR;

        ((FunctionEntity) singleCollect.getEntities().get(functionId)).addName2Id(name, globalVarId);
        ((FunctionEntity) singleCollect.getEntities().get(functionId)).addName2Role(name, role);
        for(String usage : localName.getUsages()) {
            ((FunctionEntity) singleCollect.getEntities().get(functionId)).addName2Usage(name, usage);
        }
    }

    /**
     * get id if it is a parameter of functionId
     * @param name
     * @param functionId
     * @return
     */
    private int getIdIfParameter(String name, int functionId) {
        if(functionId == -1
                || !(singleCollect.getEntities().get(functionId) instanceof FunctionEntity)) {
            return -1;
        }
        FunctionEntity functionEntity = (FunctionEntity) singleCollect.getEntities().get(functionId);
        for (int parameterId : functionEntity.getParameters()) {
            VarEntity varEntity = (VarEntity) singleCollect.getEntities().get(parameterId);
            if(varEntity.getName().equals(name)) {
                return parameterId;
            }
        }
        return -1;
    }

    /**
     * get id if it is a return of functionId
     * @param name
     * @param functionId
     * @return
     */
    private int getIdIfReturn(String name, int functionId) {
        if(functionId == -1
                || !(singleCollect.getEntities().get(functionId) instanceof FunctionEntity)) {
            return -1;
        }
        FunctionEntity functionEntity = (FunctionEntity) singleCollect.getEntities().get(functionId);
        for (int returnId : functionEntity.getReturns()) {
            VarEntity varEntity = (VarEntity) singleCollect.getEntities().get(returnId);
            if(varEntity.getName().equals(name)) {
                return returnId;
            }
        }
        return -1;
    }


    /**
     * get id if it is a used packageName of functionId
     * may be the packageName or alias of packageName
     * @param name
     * @param functionId
     * @return
     */
    private int getIdIfPackage(String name, int functionId) {
        if(functionId == -1
                || !(singleCollect.getEntities().get(functionId) instanceof FunctionEntity)) {
            return -1;
        }
        FunctionEntity functionEntity = (FunctionEntity) singleCollect.getEntities().get(functionId);
        int fileId = functionEntity.getParentId();
        if(fileId == -1) {
            return -1;
        }
        FileEntity fileEntity = (FileEntity) singleCollect.getEntities().get(fileId);
        for (Tuple<String, Integer> oneImport : fileEntity.getRelations()) {
            if (oneImport.x.equals(ConstantString.RELATION_IMPORT)) {
                int thisImportedPackageId = oneImport.y;
                String thisImportedPackageName = singleCollect.getEntities().get(thisImportedPackageId).getName();
                String thisImportedAliasName = ((FileEntity) singleCollect.getEntities().get(fileId)).getImportsAlias().get(thisImportedPackageId);
                if (name.equals(thisImportedPackageName)
                        || name.equals(thisImportedAliasName)) {
                    return thisImportedPackageId;
                }
            }
        }
        return -1;
    }

    /**
     * get id if it is a functionName of the package for functionId
     * @param name
     * @param functionId
     * @return
     */
    private int getIdIfFunction(String name, int functionId) {
        if(functionId == -1
                || !(singleCollect.getEntities().get(functionId) instanceof FunctionEntity)) {
            return -1;
        }
        FunctionEntity functionEntity = (FunctionEntity) singleCollect.getEntities().get(functionId);
        int fileId = functionEntity.getParentId();
        if(fileId == -1) {
            return -1;
        }
        FileEntity fileEntity = (FileEntity) singleCollect.getEntities().get(fileId);
        int packageId = fileEntity.getParentId();
        if(packageId == -1) {
            return -1;
        }
        for (int id : singleCollect.getEntities().get(packageId).getChildrenIds()) {
            if (singleCollect.getEntities().get(id) instanceof FileEntity) {
                for (int usedFunctionId : singleCollect.getEntities().get(id).getChildrenIds()) {
                    Entity usedFunctionEntity = singleCollect.getEntities().get(usedFunctionId);
                    if (usedFunctionEntity instanceof FunctionEntity
                            && !(usedFunctionEntity instanceof MethodEntity)) {
                        if(usedFunctionEntity.getName().equals(name)) {
                            return usedFunctionEntity.getId();
                        }
                    }
                }
            }
        }
        return -1;
    }


    /**
     * get id if it is a global var(var, constant) in the package for functionId
     * @param name
     * @param functionId
     * @return
     */
    private int getIdIfGlobalVar(String name, int functionId) {
        if(functionId == -1
                || !(singleCollect.getEntities().get(functionId) instanceof FunctionEntity)) {
            return -1;
        }
        FunctionEntity functionEntity = (FunctionEntity) singleCollect.getEntities().get(functionId);
        int fileId = functionEntity.getParentId();
        if(fileId == -1) {
            return -1;
        }
        FileEntity fileEntity = (FileEntity) singleCollect.getEntities().get(fileId);
        int packageId = fileEntity.getParentId();
        if(packageId == -1) {
            return -1;
        }
        for (int id : singleCollect.getEntities().get(packageId).getChildrenIds()) {
            if (singleCollect.getEntities().get(id) instanceof FileEntity) {
                for (int varId : singleCollect.getEntities().get(id).getChildrenIds()) {
                    if(singleCollect.getEntities().get(varId) instanceof VarEntity) {
                        if(singleCollect.getEntities().get(varId).getName().equals(name)) {
                            return varId;
                        }
                    }
                }
            }
        }
        return -1;
    }


    /**
     * get id if it is a global var(var, constant) in the package for functionId
     * @param name
     * @param functionId
     * @return
     */
    private int getIdIfLocalVar(String name, int functionId) {
        if(functionId == -1
                || !(singleCollect.getEntities().get(functionId) instanceof FunctionEntity)) {
            return -1;
        }
        for (int varId : singleCollect.getEntities().get(functionId).getChildrenIds()) {
            if (singleCollect.getEntities().get(varId) instanceof VarEntity) {
                if(singleCollect.getEntities().get(varId).getName().equals(name)) {
                    return varId;
                }
            }
        }
        return -1;
    }


}
