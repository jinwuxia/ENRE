package multiparser.goextractor.visitor.secondpass;

import multiparser.entity.*;
import multiparser.goextractor.ConstantString;
import multiparser.goextractor.Signature;
import multiparser.util.Tuple;
import multiparser.extractor.SingleCollect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * second pass to fill the relationName with entityId
 */
public class BasicDepVisitor {
    SingleCollect singleCollect = SingleCollect.getSingleCollectInstance();


    public void setUnsuredDeps() {
        setImport();
        setEmbedStruct();
        setEmbedInterface();
        setReceiver();
        setImplementation();
        }


    private void setImport() {
        ArrayList<Tuple<String, String>> importDeps = new ArrayList<Tuple<String, String>>();
        for(Entity fileEntity : singleCollect.getEntities()) {
            if (fileEntity instanceof FileEntity) {
                String fileName = fileEntity.getName();
                int fileId = fileEntity.getId();
                if (! ((FileEntity) fileEntity).getImports().isEmpty()) {
                    for (Tuple<String, String> oneImport : ((FileEntity) fileEntity).getImports()) {
                        String importedPackageNameAlias = oneImport.x;
                        String importedPackageName = oneImport.y;
                        //System.out.println("setImport:" + fileName + "," +  importedPackageName);
                        int importedPackageId = searchPackageIndex(importedPackageName);
                        if (importedPackageId != -1) {
                            saveRelation(fileId, importedPackageId,
                                    ConstantString.RELATION_IMPORT, ConstantString.RELATION_IMPORTED_BY);
                            ((FileEntity) singleCollect.getEntities().get(fileId)).addImportAlias(importedPackageNameAlias, importedPackageId);
                            //System.out.println("imported package:" + ((PackageEntity) singleCollect.getEntities().get(importedPackageId)).getFullPath());
                        }

                        //maybe the imported package is not included in the source code.
                        // it is system package or other 3rd package.
                        else {
                            //System.out.println("imported package: -1");
                        }
                    }
                }
            }
        }

    }

    private void setEmbedStruct() {
        for (Entity entity : singleCollect.getEntities()) {
            if (entity instanceof StructEntity) {
                int entityId = entity.getId();
                for (int fieldId : entity.getChildrenIds()) {
                    Entity fieldEntity = singleCollect.getEntities().get(fieldId);
                    if (fieldEntity instanceof StructFieldEntity) {
                        if (fieldEntity.getName().equals(ConstantString.STRUCT_FIELD_IS_ANONYMOUS)) {
                            String fileName1 = "";
                            if (entity.getParentId() != -1) {
                                fileName1 = singleCollect.getEntities().get(entity.getParentId()).getName();
                            }
                            //System.out.println("StructRelation: struct:"+ multiparser.entity.getName() + ", file:" +  fileName1);
                            String embededStructName = ((StructFieldEntity) fieldEntity).getType();
                            int embededEntityId = searchEmbededStruct(entityId, embededStructName);
                            if (embededEntityId != -1) {
                                saveRelation(entityId, embededEntityId,
                                        ConstantString.RELATION_EMBED, ConstantString.RELATION_EMBEDED_BY);
                                //String entityName2 = singleCollect.getEntities().get(embededEntityId).getName();
                                int entity2ParentId = singleCollect.getEntities().get(embededEntityId).getParentId();
                                String fileName2 = "";
                                if (entity2ParentId != -1) {
                                    fileName2 = singleCollect.getEntities().get(entity2ParentId).getName();
                                }
                                //System.out.println("StructRelation: embeded:"+ embededStructName + ", file:" +  fileName2);
                            }
                            else {
                                //System.out.println("StructRelation: embeded:" + embededStructName + ", file:-1");
                            }
                        }
                    }
                }
            }
        } // end big for
    }


    private void setEmbedInterface() {
        for (Entity interfaceEntity : singleCollect.getEntities()) {
            if (interfaceEntity instanceof InterfaceEntity) {
                int interfaceEntityId = interfaceEntity.getId();
                for (int fieldId : interfaceEntity.getChildrenIds()) {
                    Entity fieldEntity = singleCollect.getEntities().get(fieldId);
                    if (fieldEntity instanceof InterfaceFieldEntity) {
                        if (((InterfaceFieldEntity) fieldEntity).getType().equals(ConstantString.INTERFACE_FIELD_IS_TYPE)) {
                            String fileName1 = "";
                            if (singleCollect.getEntities().get(interfaceEntityId).getParentId() != -1) {
                                fileName1 = singleCollect.getEntities().get(singleCollect.getEntities().get(interfaceEntityId).getParentId()).getName();
                            }
                            //System.out.println("InterfaceRelation: interface:"+ interfaceEntity.getName() + ", file:" +  fileName1);
                            String embededInterfaceName = fieldEntity.getName();
                            int embededEntityId = searchEmbededInterface(interfaceEntityId, embededInterfaceName);
                            if (embededEntityId != -1) {
                                String fileName2 = "";
                                if(singleCollect.getEntities().get(embededEntityId).getParentId() != -1) {
                                    fileName2 = singleCollect.getEntities().get(singleCollect.getEntities().get(embededEntityId).getParentId()).getName();
                                }
                                //System.out.println("InterfaceRelation: embeded:"+ embededInterfaceName + ", file:" +  fileName2);
                                saveRelation(interfaceEntityId, embededEntityId,
                                        ConstantString.RELATION_EMBED, ConstantString.RELATION_EMBEDED_BY);

                            }
                            else {
                                //System.out.println("InterfaceRelation: embeded:" + embededInterfaceName + ", file:-1");
                            }
                        }
                    }
                }
            }
        } // end big for
    }



    private void setReceiver() {
        for (Entity methodEntity : singleCollect.getEntities()) {
            if (methodEntity instanceof MethodEntity) {
                int methodEntityId = methodEntity.getId();
                String methodEntityName = methodEntity.getName();
                String fileName1 = singleCollect.getEntities().get(singleCollect.getEntities().get(methodEntityId).getParentId()).getName();
                //System.out.println("method_receive_relation: methodName:" + methodEntityName + ", file: " + fileName1);
                int receiverVarId =((MethodEntity) methodEntity).getReceiverVarId();
                String receiverType = ((VarEntity) singleCollect.getEntities().get(receiverVarId)).getType();
                //System.out.print("receiver pre: " + receiverType);
                if (receiverType.startsWith(ConstantString.POINTER)) {
                    receiverType = receiverType.substring(1, receiverType.length());
                }
                //System.out.print(";  post: " + receiverType);
                int receiverTypeId = searchReceiverType(methodEntityId, receiverType);
                if (receiverTypeId != -1) {
                    ((VarEntity) singleCollect.getEntities().get(receiverVarId)).setTypeId(receiverTypeId);
                    saveRelation(methodEntityId, receiverTypeId, ConstantString.RELATION_RECEIVE, ConstantString.RELATION_RECEIVED_BY);
                    //System.out.println("method_receive_relation: receiver:" + receiverType + ", file: " + singleCollect.getEntities().get(singleCollect.getEntities().get(receiverId).getParentId()).getName());
                }
                else {
                    //System.out.println("method_receive_relation: receiver:" + receiverType + ", file:-1");
                }
            }
        }// end big for
    }


    /**
     * structType/asliasType-implement interface
     */
    private void setImplementation() {
        Map<Signature, Integer> sig2Interface = buildMethod2Interface();
        Map<Integer, ArrayList<Signature>> type2Sig = buildType2Method();
        ArrayList<Tuple<Integer, Integer>> tmps = new ArrayList<Tuple<Integer, Integer>>();

        for (Map.Entry<Integer, ArrayList<Signature>> entry1 : type2Sig.entrySet()) {
            int typeId = entry1.getKey();
            for (Signature signature1 : entry1.getValue()) {
                for(Map.Entry<Signature, Integer> entry2 : sig2Interface.entrySet()) {
                    int interfaceId = entry2.getValue();
                    Signature signature2 = entry2.getKey();
                    Tuple<Integer, Integer> tmp = new Tuple<Integer, Integer>(typeId, interfaceId);
                    if(signature1.isEqual(signature2) && !isExist(tmp, tmps)) {
                        tmps.add(tmp);
                        saveRelation(typeId, interfaceId, ConstantString.RELATION_IMPLEMENT, ConstantString.RELATION_IMPLEMENTED_BY);
                    }
                }
            }
        }
    }


    private boolean isExist(Tuple<Integer, Integer> oneTuple, ArrayList<Tuple<Integer, Integer>> tuples) {
        for (Tuple<Integer, Integer> tuple : tuples) {
            if(tuple.x == oneTuple.x && tuple.y == oneTuple.y) {
                return true;
            }
        }
        return false;
    }



     /**
     *
     * @return
     */
    private Map<Integer, ArrayList<Signature>> buildType2Method() {
        Map<Integer, ArrayList<Signature>> type2Method = new HashMap<Integer, ArrayList<Signature>>();
        for(Entity entity :singleCollect.getEntities()) {
            if (entity instanceof StructEntity || entity instanceof AliasTypeEntity) {
                int typeId = entity.getId();
                ArrayList<Tuple<String, Integer>> relations = entity.getRelations();
                for (Tuple<String, Integer> relation : relations) {
                    if (relation.x.equals(ConstantString.RELATION_RECEIVED_BY)) {
                        int methodId = relation.y;
                        String methodName = singleCollect.getEntities().get(methodId).getName();
                        ArrayList<Integer> paraIds = ((MethodEntity) singleCollect.getEntities().get(methodId)).getParameters();
                        ArrayList<Integer> returns = ((MethodEntity) singleCollect.getEntities().get(methodId)).getReturns();
                        ArrayList<String> inputs = getTypeListFromIDs(paraIds);
                        ArrayList<String> outputs = getTypeListFromIDs(returns);
                        Signature signature = new Signature(methodName, inputs, outputs);
                        if(!type2Method.containsKey(typeId)) {
                            type2Method.put(typeId, new ArrayList<Signature>());
                        }
                        type2Method.get(typeId).add(signature);
                    }
                }
            }
        } //end bug for
        return type2Method;
    }


    private ArrayList<String> getTypeListFromIDs(ArrayList<Integer> ids) {
        ArrayList<String> types = new ArrayList<String>();
        for(int id : ids) {
            VarEntity varEntity = (VarEntity) singleCollect.getEntities().get(id);
            String type = varEntity.getType();
            types.add(type);
        }
        return types;
    }



    /**
     * build method->interfaceId map, for implement relations
     * @return  [methodName]-> list(interfaceId)
     */
    private Map<Signature, Integer> buildMethod2Interface(){
        Map<Signature, Integer> signature2Interface = new HashMap<Signature, Integer>();
        for (Entity entity : singleCollect.getEntities()) {
            if (entity instanceof InterfaceFieldEntity) {
                InterfaceFieldEntity interfaceFieldEntity = (InterfaceFieldEntity) entity;
                if(interfaceFieldEntity.getType().equals(ConstantString.INTERFACE_FIELD_IS_METHOD)) {
                    String methodName = interfaceFieldEntity.getName();
                    ArrayList<VarEntity> paras = interfaceFieldEntity.getParameters();
                    ArrayList<VarEntity> returns = interfaceFieldEntity.getReturns();
                    ArrayList<String> paraTypes = getTypeListFromVars(paras);
                    ArrayList<String> retTypes = getTypeListFromVars(returns);
                    Signature signature = new Signature(methodName, paraTypes, retTypes);
                    int interfaceId = interfaceFieldEntity.getParentId();
                    signature2Interface.put(signature, interfaceId);
                }
            }
        }
        return signature2Interface;
    }

    private ArrayList<String> getTypeListFromVars(ArrayList<VarEntity> vars) {
        ArrayList<String> types = new ArrayList<String>();
        for (VarEntity varEntity : vars) {
            String type = varEntity.getType();
            types.add(type);
        }
        return types;
    }


    /**
     * multiparser.goextractor.search the multiparser.entity (struct type or alias type) id of receiver type for methodEntityId
     * @param methodEntityId
     * @param receiverType
     * @return
     */
    private int searchReceiverType(int methodEntityId, String receiverType) {
        int receiverId = -1;
        int fileId = singleCollect.getEntities().get(methodEntityId).getParentId();
        if (fileId != -1) {
            int packageId = singleCollect.getEntities().get(fileId).getParentId();
            receiverId = findNameLocal(packageId, receiverType, ConstantString.ENTITY_STRUCT_ALIAS);
        }

        return receiverId;
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


    /**
     * for structId' s structEntity, find the structId embeded in it, based on the embededStructName
     * @param structId: structEntityId
     * @param embededStructName
     * @return
     */
    private int searchEmbededStruct(int structId, String embededStructName) {
        //find the local packageId and importedPackageIds
        int packageId = -1;
        ArrayList<Integer> importedPackageIds = new ArrayList<Integer>();
        Map<Integer,String> importedPackageAliasMap = new HashMap<Integer, String>();
        int fileId = singleCollect.getEntities().get(structId).getParentId();
        //System.out.println("XXXXXXXXXfileID:" + fileId);
        if (fileId != -1 && singleCollect.getEntities().get(fileId) instanceof FileEntity) {
            packageId = singleCollect.getEntities().get(fileId).getParentId();
            importedPackageIds = findImportPackages(fileId);
            importedPackageAliasMap = ((FileEntity) singleCollect.getEntities().get(fileId)).getImportsAlias();
        }

        if(packageId != -1) {
            //find name in local package
            int res = findNameLocal(packageId, embededStructName, ConstantString.ENTITY_STRUCT);
            if(res != -1 ){
                return res;
            }
            //find name in remote/import package
            for (int importedPackageId : importedPackageIds) {
                String localEmbededStructName = transformRemote2LocalName(importedPackageAliasMap.get(importedPackageId),importedPackageId, embededStructName);
                res = findNameLocal(importedPackageId, localEmbededStructName, ConstantString.ENTITY_STRUCT);
                if(res != -1) {
                    return res;
                }
            }
        }
        return -1;
    }


    /**
     * for interfaceId, find its embeded interfaceId based on embded interfaName
     * @param interfaceId
     * @param embededInterfaceName
     * @return
     */
    private int searchEmbededInterface(int interfaceId, String embededInterfaceName) {
        //find the local packageId and importedPackageIds
        int packageId = -1;
        ArrayList<Integer> importedPackageIds = new ArrayList<Integer>();
        Map<Integer,String> importedPackageAliasMap = new HashMap<Integer, String>();
        int fileId = singleCollect.getEntities().get(interfaceId).getParentId();
        if(fileId == -1) {
            return -1;
        }
        if (singleCollect.getEntities().get(fileId) instanceof FileEntity) {
            packageId = singleCollect.getEntities().get(fileId).getParentId();
            importedPackageIds = findImportPackages(fileId);
            importedPackageAliasMap = ((FileEntity) singleCollect.getEntities().get(fileId)).getImportsAlias();
        }

        if(packageId != -1) {
            //find name in local package
            int res = findNameLocal(packageId, embededInterfaceName, ConstantString.ENTITY_INTERFACE);
            if(res != -1 ){
                return res;
            }
            //find name in remote/import package
            for (int importedPackageId : importedPackageIds) {
                String localEmbededInterfaceName = transformRemote2LocalName(importedPackageAliasMap.get(importedPackageId),importedPackageId, embededInterfaceName);
                res = findNameLocal(importedPackageId, localEmbededInterfaceName, ConstantString.ENTITY_INTERFACE);
                if(res != -1) {
                    return res;
                }
            }
        }
        return -1;
    }

    /**
     *
     * @param alias: the imported package's alias
     * @param packageId: the imported packageId
     * @param remoteName: the referenced name of var /type, declared in the importedpackage, but referenced in another package.
     * @return
     */
    private String transformRemote2LocalName(String alias, int packageId, String remoteName) {
        String localName = "";
        if (alias.equals("")) {   // the reference way is packageName.X, so return X
            String packageName = singleCollect.getEntities().get(packageId).getName();
            if(remoteName.startsWith(packageName)) {
                localName = remoteName.substring(packageName.length(), remoteName.length());
            }

        }
        else if (alias.equals(".")) {// the reference way is X, so return X
            localName = remoteName;
        }
        else {                     //the reference way is alias.X, so return x
            if(remoteName.startsWith(alias)){
                localName = remoteName.substring(alias.length(), remoteName.length());
            }
        }
        return localName;
    }


    /**
     * find TypeName's entityId in the located package- packageId
     * @param packageId
     * @param unknownName the searched TypeName. there is no selector
     * @param entityType  the searched Type
     * @return
     */
    private int findNameLocal(int packageId, String unknownName, String entityType) {
        //find the files in this package
        ArrayList<Integer> fileIds = singleCollect.getEntities().get(packageId).getChildrenIds();

        //find the unknownName, without naming transformation
        for (int fileId : fileIds) {
            for (int entityId : singleCollect.getEntities().get(fileId).getChildrenIds()) {
                if (entityType.equals(ConstantString.ENTITY_STRUCT)
                        && singleCollect.getEntities().get(entityId) instanceof StructEntity
                        && singleCollect.getEntities().get(entityId).getName().equals(unknownName)) {
                    return entityId;
                } else if (entityType.equals(ConstantString.ENTITY_INTERFACE)
                        && singleCollect.getEntities().get(entityId) instanceof InterfaceEntity
                        && singleCollect.getEntities().get(entityId).getName().equals(unknownName)) {
                    return entityId;
                }
                else if (entityType.equals(ConstantString.ENTITY_STRUCT_ALIAS)
                        && (singleCollect.getEntities().get(entityId) instanceof StructEntity
                        || singleCollect.getEntities().get(entityId) instanceof AliasTypeEntity)
                        && singleCollect.getEntities().get(entityId).getName().equals(unknownName)) {
                    return entityId;
                }
                else if (entityType.equals(ConstantString.ENTITY_FUNCTION)
                        && singleCollect.getEntities().get(entityId) instanceof FunctionEntity
                        && ! (singleCollect.getEntities().get(entityId) instanceof MethodEntity)
                        && singleCollect.getEntities().get(entityId).getName().equals(unknownName)) {
                    return entityId;
                }
                else if (entityType.equals(ConstantString.ENTITY_METHOD)
                        && ! (singleCollect.getEntities().get(entityId) instanceof MethodEntity)
                        && singleCollect.getEntities().get(entityId).getName().equals(unknownName)) {
                    return entityId;
                }
            }
        }
        return -1;
    }


    /**
     * find imported packages id, which is only the source code package,ignoring the system/3rd package
     * @param fileId
     * @return
     */
    private ArrayList<Integer> findImportPackages(int fileId) {
        ArrayList<Integer> ids = new ArrayList<Integer>();
        for (Tuple<String, Integer> relation : singleCollect.getEntities().get(fileId).getRelations()) {
            if (relation.x.equals(ConstantString.RELATION_IMPORT)) {
                ids.add(relation.y);
            }
        }
        return ids;
    }


    private int searchPackageIndex(String packagePath) {
        //System.out.println("searchPackageIndex: searched-" + packagePath);
        ArrayList<Entity> entities = singleCollect.getEntities();
        for (int i = 0; i < entities.size(); i++) {
            if (entities.get(i) instanceof PackageEntity) {
                //System.out.println("searchPackageIndex: ing-" + ((PackageEntity) entities.get(i)).getFullPath());
                if (((PackageEntity) entities.get(i)).getFullPath().equals(packagePath)) {
                    return i;
                }
            }
        }
        return -1;
    }

}
