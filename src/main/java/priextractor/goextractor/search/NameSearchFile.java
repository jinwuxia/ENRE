package priextractor.goextractor.search;

import uerr.*;
import entitybuilder.gobuilder.goentity.AliasTypeEntity;
import entitybuilder.gobuilder.goentity.InterfaceEntity;
import entitybuilder.gobuilder.goentity.MethodEntity;
import entitybuilder.gobuilder.goentity.StructEntity;
import util.Configure;
import util.Tuple;
import uerr.SingleCollect;

import java.util.ArrayList;


public class NameSearchFile {
    SingleCollect singleCollect = SingleCollect.getSingleCollectInstance();

    /**
     * find the varId in a file
     * @param varName in the scope of file
     * @param fileId
     * @return
     */
    public int findVarInFile(String varName, int fileId) {
        if(fileId == -1
                || !(singleCollect.getEntityById(fileId) instanceof AbsFILEntity)
                ) {
            return -1;
        }
        for(int entityId: singleCollect.getEntityById(fileId).getChildrenIds()) {
            if (singleCollect.getEntityById(entityId) instanceof AbsVAREntity
                    && singleCollect.getEntityById(entityId).getName().equals(varName)) {
                return entityId;
            }
        }
        return -1;
    }

    /**
     * find struct typeId in a file
     * @param typeName
     * @param fileId
     * @return
     */
    public int findStructTypeInFile(String typeName, int fileId) {
        if(fileId == -1
                || !(singleCollect.getEntityById(fileId) instanceof AbsFILEntity)
                ) {
            return -1;
        }
        for(int entityId: singleCollect.getEntityById(fileId).getChildrenIds()) {
            if (singleCollect.getEntityById(entityId) instanceof StructEntity
                    && singleCollect.getEntityById(entityId).getName().equals(typeName)) {
                return entityId;
            }
        }
        return -1;
    }


    /**
     * find aliasType in a file
     * @param typeName
     * @param fileId
     * @return
     */
    public int findAliasTypeInFile(String typeName, int fileId){
        if(fileId == -1
                || !(singleCollect.getEntityById(fileId) instanceof AbsFILEntity)
                ) {
            return -1;
        }
        for(int entityId: singleCollect.getEntityById(fileId).getChildrenIds()) {
            if (singleCollect.getEntityById(entityId) instanceof AliasTypeEntity
                    && singleCollect.getEntityById(entityId).getName().equals(typeName)) {
                return entityId;
            }
        }
        return -1;
    }

    /**
     * find interface typeId in a file
     * @param typeName
     * @param fileId
     * @return
     */
    public int findInterfaceTypeInFile(String typeName, int fileId) {
        if(fileId == -1
                || !(singleCollect.getEntityById(fileId) instanceof AbsFILEntity)
                ) {
            return -1;
        }
        for(int entityId: singleCollect.getEntityById(fileId).getChildrenIds()) {
            if (singleCollect.getEntityById(entityId) instanceof InterfaceEntity
                    && singleCollect.getEntityById(entityId).getName().equals(typeName)) {
                return entityId;
            }
        }
        return -1;
    }

    /**
     * find methodId in a file
     * @param methodName
     * @param fileId
     * @return
     */
    public int findMethodInFile(String methodName, int fileId) {
        if(fileId == -1
                || !(singleCollect.getEntityById(fileId) instanceof AbsFILEntity)
                ) {
            return -1;
        }
        for(int entityId: singleCollect.getEntityById(fileId).getChildrenIds()) {
            if (singleCollect.getEntityById(entityId) instanceof MethodEntity
                    && singleCollect.getEntityById(entityId).getName().equals(methodName)) {
                return entityId;
            }
        }
        return -1;
    }

    /**
     * find functionId in a file
     * @param functionName
     * @param fileId
     * @return
     */
    public int findFunctionInFile(String functionName, int fileId) {
        if(fileId == -1
                || !(singleCollect.getEntityById(fileId) instanceof AbsFILEntity)
                ) {
            return -1;
        }
        for(int entityId: singleCollect.getEntityById(fileId).getChildrenIds()) {
            if (!(singleCollect.getEntityById(entityId) instanceof MethodEntity)
                    && singleCollect.getEntityById(entityId) instanceof AbsFUNEntity
                    && singleCollect.getEntityById(entityId).getName().equals(functionName)) {
                return entityId;
            }
        }
        return -1;
    }



    /**
     * find importedPackageId by the usedName in fileId's file
     * @param packageName  packageName may be the packageName or alias of packageName
     * @param fileId
     * @return
     */
    public int findImportedPackageInFile(String packageName, int fileId) {
        if(fileId != -1 &&
                singleCollect.getEntityById(fileId) instanceof AbsFILEntity) {
            ArrayList<Tuple<String, Integer>> relations = singleCollect.getEntityById(fileId).getRelations();
            for (Tuple<String, Integer> oneImport : relations) {
                if (oneImport.x.equals(Configure.RELATION_IMPORT)) {
                    int thisImportedPackageId = oneImport.y;
                    String thisImportedPackageName = singleCollect.getEntityById(thisImportedPackageId).getName();
                    String thisImportedAliasName = ((AbsFILEntity) singleCollect.getEntityById(fileId)).getImportsAlias().get(thisImportedPackageId);
                    if (packageName.equals(thisImportedPackageName)
                            || packageName.equals(thisImportedAliasName)) {
                        return thisImportedPackageId;
                    }
                }
            }
        }
        return -1;
    }


}
