package priextractor.goextractor.godeper;

import entitybuilder.gobuilder.GoConstantString;
import uerr.*;
import entitybuilder.gobuilder.goentity.MethodEntity;
import util.Configure;
import util.Tuple;
import uerr.SingleCollect;

public class MapInFun {

    SingleCollect singleCollect = SingleCollect.getSingleCollectInstance();
    /**
     * to refine localNames,
     * if localVar(var, shortvar, const), parameter, return, global var, function, then find id, and role
     *
     */
    public void buildNameSearchTable() {
        for(AbsEntity functionEntity : singleCollect.getEntities()) {
            if(functionEntity instanceof AbsFUNEntity) {
                int functionId = functionEntity.getId();
                for(LocalName localName : ((AbsFUNEntity) singleCollect.getEntityById(functionId)).getLocalNames()) {
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

                    int receiverVarId = getIdIfReceiver(localName.getName(), functionId);
                    if(receiverVarId != -1) {
                        processAsReceiver(localName, receiverVarId, functionId);
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

                    int localVarId = getIdIfLocalVar(localName, functionId);
                    if(localVarId != -1) {//modify role, add 3 map without usages
                        processAsLocalVar(localName, localVarId, functionId);
                        continue;
                    }

                    int globalVarId = getIdIfGlobalVar(localName.getName(), functionId);
                    if(globalVarId != -1) {//modify role, add 3 map without usages
                        processAsGlobalVar(localName, globalVarId, functionId);
                        continue;
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
        String role = GoConstantString.OPERAND_NAME_ROLE_RET;

        ((AbsFUNEntity) singleCollect.getEntityById(functionId)).addName2Id(name, returnId);
        ((AbsFUNEntity) singleCollect.getEntityById(functionId)).addName2Role(name, role);
        for(String usage : localName.getUsages()) {
            ((AbsFUNEntity) singleCollect.getEntityById(functionId)).addName2Usage(name, usage);
        }
    }

    /**
     * if returns, added three maps
     * @param localName
     * @param parameterId
     */
    private void processAsPara(LocalName localName, int parameterId, int functionId) {
        String name = localName.getName();
        String role = GoConstantString.OPERAND_NAME_ROLE_PAR;

        ((AbsFUNEntity) singleCollect.getEntityById(functionId)).addName2Id(name, parameterId);
        ((AbsFUNEntity) singleCollect.getEntityById(functionId)).addName2Role(name, role);
        for(String usage : localName.getUsages()) {
            ((AbsFUNEntity) singleCollect.getEntityById(functionId)).addName2Usage(name, usage);
        }
    }

    /**
     * if receiver, added three maps
     */
    private void  processAsReceiver(LocalName localName, int receiverVarId, int functionId) {
        String name = localName.getName();
        String role = GoConstantString.OPERAND_NAME_ROLE_REC;

        ((MethodEntity) singleCollect.getEntityById(functionId)).addName2Id(name, receiverVarId);
        ((MethodEntity) singleCollect.getEntityById(functionId)).addName2Role(name, role);
        for(String usage : localName.getUsages()) {
            ((MethodEntity) singleCollect.getEntityById(functionId)).addName2Usage(name, usage);
        }
    }


    /**
     * if package, added two maps without usage
     * @param localName
     * @param packageId
     */
    private void processAsPkg(LocalName localName, int packageId, int functionId) {
        String name = localName.getName();
        String role = GoConstantString.OPERAND_NAME_ROLE_PKG;

        ((AbsFUNEntity) singleCollect.getEntityById(functionId)).addName2Id(name, packageId);
        ((AbsFUNEntity) singleCollect.getEntityById(functionId)).addName2Role(name, role);
    }

    /**
     * if function, added two maps without usage
     * @param localName
     * @param localFunctionId
     * @param functionId
     */
    private void processAsFun(LocalName localName, int localFunctionId, int functionId) {
        String name = localName.getName();
        String role = GoConstantString.OPERAND_NAME_ROLE_FUN;

        ((AbsFUNEntity) singleCollect.getEntityById(functionId)).addName2Id(name, localFunctionId);
        ((AbsFUNEntity) singleCollect.getEntityById(functionId)).addName2Role(name, role);
    }


    /**
     * if local var(var, constant) added three maps
     * @param localName
     * @param functionId
     */
    private void processAsLocalVar(LocalName localName, int localVarId, int functionId) {
        String name = localName.getName();
        String role = GoConstantString.OPERAND_NAME_ROLE_LOC_VAR;

        ((AbsFUNEntity) singleCollect.getEntityById(functionId)).addName2Id(name, localVarId);
        ((AbsFUNEntity) singleCollect.getEntityById(functionId)).addName2Role(name, role);
        for(String usage : localName.getUsages()) {
            ((AbsFUNEntity) singleCollect.getEntityById(functionId)).addName2Usage(name, usage);
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
        String role = GoConstantString.OPERAND_NAME_ROLE_GLO_VAR;

        ((AbsFUNEntity) singleCollect.getEntityById(functionId)).addName2Id(name, globalVarId);
        ((AbsFUNEntity) singleCollect.getEntityById(functionId)).addName2Role(name, role);
        for(String usage : localName.getUsages()) {
            ((AbsFUNEntity) singleCollect.getEntityById(functionId)).addName2Usage(name, usage);
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
                || !(singleCollect.getEntityById(functionId) instanceof AbsFUNEntity)) {
            return -1;
        }
        AbsFUNEntity functionEntity = (AbsFUNEntity) singleCollect.getEntityById(functionId);
        for (int parameterId : functionEntity.getParameters()) {
            AbsVAREntity varEntity = (AbsVAREntity) singleCollect.getEntityById(parameterId);
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
                || !(singleCollect.getEntityById(functionId) instanceof AbsFUNEntity)) {
            return -1;
        }
        AbsFUNEntity functionEntity = (AbsFUNEntity) singleCollect.getEntityById(functionId);
        for (int returnId : functionEntity.getReturns()) {
            AbsVAREntity varEntity = (AbsVAREntity) singleCollect.getEntityById(returnId);
            if(varEntity.getName().equals(name)) {
                return returnId;
            }
        }
        return -1;
    }

    /**
     * get id if it is a receiver of methodId
     * @param name
     * @param functionId
     * @return
     */
    private int getIdIfReceiver(String name, int functionId) {
        if(functionId == -1
                || !(singleCollect.getEntityById(functionId) instanceof MethodEntity)) {
            return -1;
        }
        MethodEntity methodEntity = (MethodEntity) singleCollect.getEntityById(functionId);
        int receiverVarId = methodEntity.getReceiverVarId();
        AbsVAREntity varEntity = (AbsVAREntity) singleCollect.getEntityById(receiverVarId);
        if(varEntity.getName().equals(name)) {
            return receiverVarId;
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
                || !(singleCollect.getEntityById(functionId) instanceof AbsFUNEntity)) {
            return -1;
        }
        AbsFUNEntity functionEntity = (AbsFUNEntity) singleCollect.getEntityById(functionId);
        int fileId = functionEntity.getParentId();
        if(fileId == -1 || !(singleCollect.getEntityById(fileId) instanceof AbsFILEntity)) {
            return -1;
        }
        AbsFILEntity fileEntity = (AbsFILEntity) singleCollect.getEntityById(fileId);
        for (Tuple<String, Integer> oneImport : fileEntity.getRelations()) {
            if (oneImport.x.equals(Configure.RELATION_IMPORT)) {
                int thisImportedPackageId = oneImport.y;
                String thisImportedPackageName = singleCollect.getEntityById(thisImportedPackageId).getName();
                String thisImportedAliasName = ((AbsFILEntity) singleCollect.getEntityById(fileId)).getImportsAlias().get(thisImportedPackageId);
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
                || !(singleCollect.getEntityById(functionId) instanceof AbsFUNEntity)) {
            return -1;
        }
        AbsFUNEntity functionEntity = (AbsFUNEntity) singleCollect.getEntityById(functionId);
        int fileId = functionEntity.getParentId();
        if(fileId == -1 || !(singleCollect.getEntityById(fileId) instanceof AbsFILEntity)) {
            return -1;
        }
        AbsFILEntity fileEntity = (AbsFILEntity) singleCollect.getEntityById(fileId);
        int packageId = fileEntity.getParentId();
        if(packageId == -1) {
            return -1;
        }
        for (int id : singleCollect.getEntityById(packageId).getChildrenIds()) {
            if (singleCollect.getEntityById(id) instanceof AbsFILEntity) {
                for (int usedFunctionId : singleCollect.getEntityById(id).getChildrenIds()) {
                    AbsEntity usedFunctionEntity = singleCollect.getEntityById(usedFunctionId);
                    if (usedFunctionEntity instanceof AbsFUNEntity
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
                || !(singleCollect.getEntityById(functionId) instanceof AbsFUNEntity)) {
            return -1;
        }
        AbsFUNEntity functionEntity = (AbsFUNEntity) singleCollect.getEntityById(functionId);
        int fileId = functionEntity.getParentId();
        if(fileId == -1 || !(singleCollect.getEntityById(fileId) instanceof AbsFILEntity)) {
            return -1;
        }
        AbsFILEntity fileEntity = (AbsFILEntity) singleCollect.getEntityById(fileId);
        int packageId = fileEntity.getParentId();
        if(packageId == -1) {
            return -1;
        }
        for (int id : singleCollect.getEntityById(packageId).getChildrenIds()) {
            if (singleCollect.getEntityById(id) instanceof AbsFILEntity) {
                for (int varId : singleCollect.getEntityById(id).getChildrenIds()) {
                    if(singleCollect.getEntityById(varId) instanceof AbsVAREntity) {
                        if(singleCollect.getEntityById(varId).getName().equals(name)) {
                            return varId;
                        }
                    }
                }
            }
        }
        return -1;
    }


    /**
     * get id if it is a local var(var, constant) in the package for functionId
     * @param localName  localName
     * @param functionId the functionId that contains localName
     * @return
     */
    private int getIdIfLocalVar(LocalName localName, int functionId) {
        if(functionId == -1 || !(singleCollect.getEntityById(functionId) instanceof AbsFUNEntity)) {
            return -1;
        }

        //System.out.println("function:" + singleCollect.getEntityById(functionId).getName());
        //System.out.println("find: " + localName);

        String currentName = localName.getName();
        int currentBlockId = localName.getLocalBlockId();
        int currentBlockDepth = ((AbsFUNEntity) singleCollect.getEntityById(functionId)).getLocalBlocks().get(currentBlockId).getDepth();

        int resVarId = -1;
        int resBlockId = -1;
        for (int varId : singleCollect.getEntityById(functionId).getChildrenIds()) {
            if (singleCollect.getEntityById(varId) instanceof AbsVAREntity) {
                String candidateName = singleCollect.getEntityById(varId).getName();
                int candidateBlockId = ((AbsVAREntity) singleCollect.getEntityById(varId)).getLocalBlockId();
                int candidateBlockDepth =  ((AbsFUNEntity) singleCollect.getEntityById(functionId)).getLocalBlocks().get(candidateBlockId).getDepth();
                // case 1
                if(currentName.equals(candidateName) && currentBlockId == candidateBlockId) {
                    //System.out.println("found: " + singleCollect.getEntityById(varId));
                    return varId;
                }
                //case 2
                if(currentName.equals(candidateName) && candidateBlockId < currentBlockId && candidateBlockDepth < currentBlockDepth) {
                    if(resBlockId == -1 || resBlockId < candidateBlockId) {
                        resBlockId = candidateBlockId;
                        resVarId = varId;
                    }
                }
            }
        }
        if (resVarId != -1) {
            //System.out.println("found: " + singleCollect.getEntityById(resVarId));
        }
        return resVarId;
    }



}
