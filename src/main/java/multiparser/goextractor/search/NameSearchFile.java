package multiparser.goextractor.search;

import multiparser.entity.*;
import multiparser.goextractor.ConstantString;
import multiparser.goextractor.goentity.AliasTypeEntity;
import multiparser.goextractor.goentity.InterfaceEntity;
import multiparser.goextractor.goentity.MethodEntity;
import multiparser.goextractor.goentity.StructEntity;
import multiparser.util.Configure;
import multiparser.util.Tuple;
import multiparser.extractor.SingleCollect;
import sun.security.krb5.Config;

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
                || !(singleCollect.getEntities().get(fileId) instanceof FileEntity)
                ) {
            return -1;
        }
        for(int entityId: singleCollect.getEntities().get(fileId).getChildrenIds()) {
            if (singleCollect.getEntities().get(entityId) instanceof VarEntity
                    && singleCollect.getEntities().get(entityId).getName().equals(varName)) {
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
                || !(singleCollect.getEntities().get(fileId) instanceof FileEntity)
                ) {
            return -1;
        }
        for(int entityId: singleCollect.getEntities().get(fileId).getChildrenIds()) {
            if (singleCollect.getEntities().get(entityId) instanceof StructEntity
                    && singleCollect.getEntities().get(entityId).getName().equals(typeName)) {
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
                || !(singleCollect.getEntities().get(fileId) instanceof FileEntity)
                ) {
            return -1;
        }
        for(int entityId: singleCollect.getEntities().get(fileId).getChildrenIds()) {
            if (singleCollect.getEntities().get(entityId) instanceof AliasTypeEntity
                    && singleCollect.getEntities().get(entityId).getName().equals(typeName)) {
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
                || !(singleCollect.getEntities().get(fileId) instanceof FileEntity)
                ) {
            return -1;
        }
        for(int entityId: singleCollect.getEntities().get(fileId).getChildrenIds()) {
            if (singleCollect.getEntities().get(entityId) instanceof InterfaceEntity
                    && singleCollect.getEntities().get(entityId).getName().equals(typeName)) {
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
                || !(singleCollect.getEntities().get(fileId) instanceof FileEntity)
                ) {
            return -1;
        }
        for(int entityId: singleCollect.getEntities().get(fileId).getChildrenIds()) {
            if (singleCollect.getEntities().get(entityId) instanceof MethodEntity
                    && singleCollect.getEntities().get(entityId).getName().equals(methodName)) {
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
                || !(singleCollect.getEntities().get(fileId) instanceof FileEntity)
                ) {
            return -1;
        }
        for(int entityId: singleCollect.getEntities().get(fileId).getChildrenIds()) {
            if (!(singleCollect.getEntities().get(entityId) instanceof MethodEntity)
                    && singleCollect.getEntities().get(entityId) instanceof FunctionEntity
                    && singleCollect.getEntities().get(entityId).getName().equals(functionName)) {
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
                singleCollect.getEntities().get(fileId) instanceof FileEntity) {
            ArrayList<Tuple<String, Integer>> relations = singleCollect.getEntities().get(fileId).getRelations();
            for (Tuple<String, Integer> oneImport : relations) {
                if (oneImport.x.equals(Configure.RELATION_IMPORT)) {
                    int thisImportedPackageId = oneImport.y;
                    String thisImportedPackageName = singleCollect.getEntities().get(thisImportedPackageId).getName();
                    String thisImportedAliasName = ((FileEntity) singleCollect.getEntities().get(fileId)).getImportsAlias().get(thisImportedPackageId);
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
