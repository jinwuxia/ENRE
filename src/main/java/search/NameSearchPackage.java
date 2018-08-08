package search;

import entity.PackageEntity;
import visitor.SingleCollect;

public class NameSearchPackage {

    SingleCollect singleCollect = SingleCollect.getSingleCollectInstance();
    NameSearchFile nameSearchFile = new NameSearchFile();

    /**
     * find the varId in a package
     * @param varName
     * @param packageId
     * @return
     */
    public int findVarInPackage(String varName, int packageId){
        if(packageId == -1
                || !(singleCollect.getEntities().get(packageId) instanceof PackageEntity) ) {
            return -1;
        }
        for (int fileId : singleCollect.getEntities().get(packageId).getChildrenIds()) {
            int varId = nameSearchFile.findVarInFile(varName, fileId);
            if (varId != -1) {
                return varId;
            }
        }
        return -1;
    }

    /**
     * find struct typeId in a package
     * @param typeName
     * @param packageId
     * @return
     */
    public int findStructTypeInPackage(String typeName, int packageId) {
        if(packageId == -1
                || !(singleCollect.getEntities().get(packageId) instanceof PackageEntity) ) {
            return -1;
        }
        for (int fileId : singleCollect.getEntities().get(packageId).getChildrenIds()) {
            int structId = nameSearchFile.findStructTypeInFile(typeName, fileId);
            if (structId != -1) {
                return structId;
            }
        }
        return -1;
    }


    /**
     * find alias typeId in a package
     * @param typeName
     * @param packageId
     * @return
     */
    public int findAliasTypeInPackage(String typeName, int packageId) {
        if(packageId == -1
                || !(singleCollect.getEntities().get(packageId) instanceof PackageEntity) ) {
            return -1;
        }
        for (int fileId : singleCollect.getEntities().get(packageId).getChildrenIds()) {
            int structId = nameSearchFile.findAliasTypeInFile(typeName, fileId);
            if (structId != -1) {
                return structId;
            }
        }
        return -1;
    }


    /**
     * find interface typeId in a package
     * @param typeName
     * @param packageId
     * @return
     */
    public int findInterfaceTypeInPackage(String typeName, int packageId) {
        if(packageId == -1
                || !(singleCollect.getEntities().get(packageId) instanceof PackageEntity) ) {
            return -1;
        }
        for (int fileId : singleCollect.getEntities().get(packageId).getChildrenIds()) {
            int interfaceId = nameSearchFile.findInterfaceTypeInFile(typeName, fileId);
            if (interfaceId != -1) {
                return interfaceId;
            }
        }
        return -1;
    }



    //find functionId in a package
    public int findFunctionInPackage(String functionName, int packageId) {
        if(packageId == -1
                || !(singleCollect.getEntities().get(packageId) instanceof PackageEntity) ) {
            return -1;
        }
        for (int fileId : singleCollect.getEntities().get(packageId).getChildrenIds()) {
            int functionId = nameSearchFile.findFunctionInFile(functionName, fileId);
            if (functionId != -1) {
                return functionId;
            }
        }
        return -1;
    }
}
