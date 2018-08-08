package infer;

import entity.*;
import search.NameSearchFile;
import search.NameSearchPackage;
import util.ConstantString;
import visitor.SingleCollect;


public class TypeInfer {
    SingleCollect singleCollect = SingleCollect.getSingleCollectInstance();
    NameSearchPackage nameSearchPackage = new NameSearchPackage();
    NameSearchFile nameSearchFile = new NameSearchFile();

    /**
     * for each varEntity, infer its typeId
     */
    public void inferTypeForVarEntity() {
        for (Entity entity : singleCollect.getEntities()) {
            if(entity instanceof VarEntity) {
                String type = ((VarEntity) entity).getType();
                String keyType = getKeyType(type);
                int typeId = -1;
                int fileId = findFileId(entity);

                if(!isBuiltInType(keyType)) {
                    typeId = searchTypeId(keyType, fileId);
                }
                if(((VarEntity) singleCollect.getEntities().get(entity.getId())).getTypeId() == -1) {
                    ((VarEntity) singleCollect.getEntities().get(entity.getId())).setTypeId(typeId);
                }
                //System.out.println("old_type:" + type + ";  new_type:" + keyType + ";  typeId:" + Integer.toString(typeId));
            }
        }
    }


    /**
     * find the varentity's(local,global, para, structField, returns) localized file Id
     * @param entity
     * @return
     */
    private int findFileId(Entity entity) {
        int id = entity.getId();
        while (id != -1
                && !(singleCollect.getEntities().get(id) instanceof FileEntity) ) {
            id = singleCollect.getEntities().get(id).getParentId();
        }
        if(id != -1
                && singleCollect.getEntities().get(id) instanceof FileEntity) {
            return id;
        }
        return -1;
    }

    /**
     * delete the [], ..., {}, map,....extract the real type
     * @param typeName
     * @return
     */
    private String getKeyType(String typeName) {
        String str = typeName;
        if(str.equals("")) {
            return str;
        }
        while (str.contains(ConstantString.POINTER)) {
            str = substituate(str, ConstantString.POINTER, "");
        }
        while (str.contains(ConstantString.ANY_RETURN)) {
            str = substituate(str, ConstantString.ANY_RETURN, "");
        }
        while (str.contains(ConstantString.SQUARE_BRACKETS)) {
            str = substituate(str, ConstantString.SQUARE_BRACKETS, "");
        }
        while (str.contains(ConstantString.LEFT_SQUARE_BRACKET) && str.contains(ConstantString.RIGHT_SQUARE_BRACKET)) {
            int i = str.indexOf(ConstantString.LEFT_SQUARE_BRACKET);
            int j = str.indexOf(ConstantString.RIGHT_SQUARE_BRACKET);
            String substr = str.substring(i, j + 1);
            str  = substituate(str, substr, "");
        }
        while(str.contains(ConstantString.ELLIPSIS)) {
            str = substituate(str, ConstantString.ELLIPSIS, "");
        }
        while(str.contains(ConstantString.MAP)) {
            str = substituate(str, ConstantString.MAP, "");
        }
        return str;
    }

    /**
     * substituate substr1 with substr2 in str.
     * @param str
     * @param substr1
     * @param substr2
     * @return
     */
    private String substituate(String str, String substr1, String substr2) {
        int index = str.indexOf(substr1); //index, index + substr1.length
        str = str.substring(0, index) + substr2 + str.substring(index + substr1.length(), str.length());
        return str;
    }

    /**
     * judge the type is basic or built-in type or not
     * @param typeName
     * @return
     */
    private Boolean isBuiltInType(String typeName) {
        for (int i = 0; i < ConstantString.BUILTIN_WORDS.length; i++) {
            if (ConstantString.BUILTIN_WORDS[i].equals(typeName)) {
                return true;
            }
        }
        return false;
    }

    /** now we can infer X or X.X .  but not X.X.X,,,,,,
     * according to the typeName used in this file, search the typeId
     * @param typeName
     * @param fileId
     * @return
     */
    private int searchTypeId(String typeName, int fileId) {
        int typeId;
        int packageId = -1;
        String[] arr = typeName.split("\\.");

        if(arr.length == 1) {
            //search Type in this package
            if(fileId != -1 && singleCollect.getEntities().get(fileId) instanceof FileEntity) {
                packageId = singleCollect.getEntities().get(fileId).getParentId();
            }
        }
        if(arr.length == 2) {
            typeName = arr[1];
            String packageName = arr[0];
            packageId = nameSearchFile.findImportedPackageInFile(packageName, fileId);
        }
        else if (arr.length > 2) {
            //System.out.println("typeinfer: searchTypeId: arr.length > 2");
        }

        typeId = getSearchType(typeName, packageId);
        return typeId;
    }


    /**
     * find typeName's id in the specifed package
     * @param typeName
     * @param packageId
     * @return
     */
    private int getSearchType(String typeName, int packageId) {
        if(packageId == -1
                || !(singleCollect.getEntities().get(packageId) instanceof PackageEntity)) {
            return -1;
        }
        int typeId = nameSearchPackage.findStructTypeInPackage(typeName, packageId);
        if (typeId != -1) {
            return typeId;
        }
        typeId = nameSearchPackage.findAliasTypeInPackage(typeName, packageId);
        if(typeId !=  -1) {
            return typeId;
        }
        typeId = nameSearchPackage.findInterfaceTypeInPackage(typeName, packageId);
        return typeId;
    }

    /**
     * test for out put
     */
    public void output() {
        for (Entity entity : singleCollect.getEntities()) {
            if(entity instanceof VarEntity) {
                int typeId = ((VarEntity) singleCollect.getEntities().get(entity.getId())).getTypeId();
                int fileId = findFileId(entity);
                String fileName = "";
                if(fileId != -1) {
                    fileName = singleCollect.getEntities().get(fileId).getName();
                }
                if(typeId != -1) {
                    System.out.println("Var name:" + entity.getName() +
                            ", type_ori:" + ((VarEntity) entity).getType() +
                            ", type_get:" + singleCollect.getEntities().get(typeId).getName() +
                            ", file:" + fileName);
                }
                else {
                    System.out.println("Var name:" + entity.getName() +
                            ", type_ori:" + ((VarEntity) entity).getType() +
                            ", type_get:" + "-1" +
                            ", file:" + fileName);
                }
            }
        }
    }


}
