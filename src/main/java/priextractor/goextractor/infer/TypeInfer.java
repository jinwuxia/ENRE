package priextractor.goextractor.infer;

import entitybuilder.gobuilder.GoConstantString;
import uerr.*;
import priextractor.goextractor.search.NameSearchFile;
import priextractor.goextractor.search.NameSearchPackage;
import uerr.SingleCollect;
import util.Configure;


public class TypeInfer {
    SingleCollect singleCollect = SingleCollect.getSingleCollectInstance();
    NameSearchPackage nameSearchPackage = new NameSearchPackage();
    NameSearchFile nameSearchFile = new NameSearchFile();

    /**
     * for each varEntity, priextractor.goextractor.infer its typeId
     */
    public void inferTypeForVarEntity() {
        for (AbsEntity entity : singleCollect.getEntities()) {
            if(entity instanceof AbsVAREntity) {
                if(((AbsVAREntity) entity).getTypeId() != -1) {
                    continue;
                }
                String type = ((AbsVAREntity) entity).getType();
                String keyType = getKeyType(type);
                int typeId = -1;
                int fileId = findFileId(entity);

                if(!isBuiltInType(keyType)) {
                    typeId = searchTypeId(keyType, fileId);
                }
                if(((AbsVAREntity) singleCollect.getEntities().get(entity.getId())).getTypeId() == -1) {
                    ((AbsVAREntity) singleCollect.getEntities().get(entity.getId())).setTypeId(typeId);
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
    private int findFileId(AbsEntity entity) {
        int id = entity.getId();
        while (id != -1
                && !(singleCollect.getEntities().get(id) instanceof AbsFILEntity) ) {
            id = singleCollect.getEntities().get(id).getParentId();
        }
        if(id != -1
                && singleCollect.getEntities().get(id) instanceof AbsFILEntity) {
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
        while (str.contains(Configure.POINTER)) {
            str = substituate(str, Configure.POINTER, "");
        }
        while (str.contains(GoConstantString.ANY_RETURN)) {
            str = substituate(str, GoConstantString.ANY_RETURN, "");
        }
        while (str.contains(Configure.SQUARE_BRACKETS)) {
            str = substituate(str, Configure.SQUARE_BRACKETS, "");
        }
        while (str.contains(Configure.LEFT_SQUARE_BRACKET) && str.contains(Configure.RIGHT_SQUARE_BRACKET)) {
            int i = str.indexOf(Configure.LEFT_SQUARE_BRACKET);
            int j = str.indexOf(Configure.RIGHT_SQUARE_BRACKET);
            String substr = str.substring(i, j + 1);
            str  = substituate(str, substr, "");
        }
        while(str.contains(Configure.ELLIPSIS)) {
            str = substituate(str, Configure.ELLIPSIS, "");
        }
        while(str.contains(GoConstantString.MAP)) {
            str = substituate(str, GoConstantString.MAP, "");
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
        for (int i = 0; i < GoConstantString.BUILTIN_TYPE.length; i++) {
            if (GoConstantString.BUILTIN_TYPE[i].equals(typeName)) {
                return true;
            }
        }
        return false;
    }

    /** now we can priextractor.goextractor.infer X or X.X .  but not X.X.X,,,,,,
     * according to the typeName used in this file, priextractor.goextractor.searcher the typeId
     * @param typeName
     * @param fileId
     * @return
     */
    private int searchTypeId(String typeName, int fileId) {
        int typeId;
        int packageId = -1;
        String[] arr = typeName.split("\\.");

        if(arr.length == 1) {
            //priextractor.goextractor.searcher Type in this package
            if(fileId != -1 && singleCollect.getEntities().get(fileId) instanceof AbsFILEntity) {
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
                || !(singleCollect.getEntities().get(packageId) instanceof AbsFLDEntity)) {
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
        for (AbsEntity entity : singleCollect.getEntities()) {
            if(entity instanceof AbsVAREntity) {
                int typeId = ((AbsVAREntity) singleCollect.getEntities().get(entity.getId())).getTypeId();
                int fileId = findFileId(entity);
                String fileName = "";
                if(fileId != -1) {
                    fileName = singleCollect.getEntities().get(fileId).getName();
                }
                if(typeId != -1) {
                    System.out.println("Var name:" + entity.getName() +
                            ", type_ori:" + ((AbsVAREntity) entity).getType() +
                            ", type_get:" + singleCollect.getEntities().get(typeId).getName() +
                            ", file:" + fileName);
                }
                else {
                    System.out.println("Var name:" + entity.getName() +
                            ", type_ori:" + ((AbsVAREntity) entity).getType() +
                            ", type_get:" + "-1" +
                            ", file:" + fileName);
                }
            }
        }
    }


}
