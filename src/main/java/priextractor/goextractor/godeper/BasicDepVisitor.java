package priextractor.goextractor.godeper;

import entitybuilder.gobuilder.GoConstantString;
import uerr.*;
import entitybuilder.gobuilder.goentity.Signature;
import entitybuilder.gobuilder.goentity.*;
import util.Configure;
import util.Tuple;
import uerr.SingleCollect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * second pass to fill the relationName with entityId
 */
public class BasicDepVisitor {
    private SingleCollect singleCollect = SingleCollect.getSingleCollectInstance();


    public void setUnsuredDeps() {
        setImport();
        setEmbedStruct();
        setEmbedInterface();
        setReceiver();
        setImplementation();
        }


    private void setImport() {
        ArrayList<Tuple<String, String>> importDeps = new ArrayList<Tuple<String, String>>();
        for(AbsEntity fileEntity : singleCollect.getEntities()) {
            if (fileEntity instanceof AbsFILEntity) {
                String fileName = fileEntity.getName();
                int fileId = fileEntity.getId();
                if (! ((AbsFILEntity) fileEntity).getImports().isEmpty()) {
                    for (Tuple<String, String> oneImport : ((AbsFILEntity) fileEntity).getImports()) {
                        String importedPackageNameAlias = oneImport.x;
                        String importedPackageName = oneImport.y;
                        //System.out.println("setImport:" + fileName + "," +  importedPackageName);
                        int importedPackageId = searchPackageIndex(importedPackageName);
                        if (importedPackageId != -1) {
                            saveRelation(fileId, importedPackageId,
                                    Configure.RELATION_IMPORT, Configure.RELATION_IMPORTED_BY);
                            ((AbsFILEntity) singleCollect.getEntityById(fileId)).addImportAlias(importedPackageNameAlias, importedPackageId);
                            //System.out.println("imported package:" + ((AbsFLDEntity) singleCollect.getEntityById(importedPackageId)).getFullPath());
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
        for (AbsEntity entity : singleCollect.getEntities()) {
            if (entity instanceof StructEntity) {
                int entityId = entity.getId();
                for (int fieldId : entity.getChildrenIds()) {
                    AbsEntity fieldEntity = singleCollect.getEntityById(fieldId);
                    if (fieldEntity instanceof StructFieldEntity) {
                        if (fieldEntity.getName().equals(GoConstantString.STRUCT_FIELD_IS_ANONYMOUS)) {
                            String fileName1 = Configure.NULL_STRING;
                            if (entity.getParentId() != -1) {
                                fileName1 = singleCollect.getEntityById(entity.getParentId()).getName();
                            }
                            //System.out.println("StructRelation: struct:"+ uerr.getName() + ", file:" +  fileName1);
                            String embededStructName = ((StructFieldEntity) fieldEntity).getType();
                            int embededEntityId = searchEmbededStruct(entityId, embededStructName);
                            if (embededEntityId != -1) {
                                saveRelation(entityId, embededEntityId,
                                        Configure.RELATION_INHERIT, Configure.RELATION_INHERITED_BY);
                                //String entityName2 = singleCollect.getEntityById(embededEntityId).getName();
                                int entity2ParentId = singleCollect.getEntityById(embededEntityId).getParentId();
                                String fileName2 = Configure.NULL_STRING;
                                if (entity2ParentId != -1) {
                                    fileName2 = singleCollect.getEntityById(entity2ParentId).getName();
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
        for (AbsEntity interfaceEntity : singleCollect.getEntities()) {
            if (interfaceEntity instanceof InterfaceEntity) {
                int interfaceEntityId = interfaceEntity.getId();
                for (int fieldId : interfaceEntity.getChildrenIds()) {
                    AbsEntity fieldEntity = singleCollect.getEntityById(fieldId);
                    if (fieldEntity instanceof InterfaceFieldEntity) {
                        if (((InterfaceFieldEntity) fieldEntity).getType().equals(GoConstantString.INTERFACE_FIELD_IS_TYPE)) {
                            String fileName1 = Configure.NULL_STRING;
                            if (singleCollect.getEntityById(interfaceEntityId).getParentId() != -1) {
                                fileName1 = singleCollect.getEntityById(singleCollect.getEntityById(interfaceEntityId).getParentId()).getName();
                            }
                            //System.out.println("InterfaceRelation: interface:"+ interfaceEntity.getName() + ", file:" +  fileName1);
                            String embededInterfaceName = fieldEntity.getName();
                            int embededEntityId = searchEmbededInterface(interfaceEntityId, embededInterfaceName);
                            if (embededEntityId != -1) {
                                String fileName2 = Configure.NULL_STRING;
                                if(singleCollect.getEntityById(embededEntityId).getParentId() != -1) {
                                    fileName2 = singleCollect.getEntityById(singleCollect.getEntityById(embededEntityId).getParentId()).getName();
                                }
                                //System.out.println("InterfaceRelation: embeded:"+ embededInterfaceName + ", file:" +  fileName2);
                                saveRelation(interfaceEntityId, embededEntityId,
                                        Configure.RELATION_INHERIT, Configure.RELATION_INHERITED_BY);

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


    /**
     * it is a receiver relation, also and the receiver relation into parent-child
     */
    private void setReceiver() {
        for (AbsEntity methodEntity : singleCollect.getEntities()) {
            if (methodEntity instanceof MethodEntity) {
                int methodEntityId = methodEntity.getId();
                String methodEntityName = methodEntity.getName();
                String fileName1 = singleCollect.getEntityById(singleCollect.getEntityById(methodEntityId).getParentId()).getName();
                //System.out.println("method_receive_relation: methodName:" + methodEntityName + ", file: " + fileName1);
                int receiverVarId =((MethodEntity) methodEntity).getReceiverVarId();
                String receiverType = ((AbsVAREntity) singleCollect.getEntityById(receiverVarId)).getType();
                //System.out.print("receiver pre: " + receiverType);
                if (receiverType.startsWith(Configure.POINTER)) {
                    receiverType = receiverType.substring(1, receiverType.length());
                }
                //System.out.print(";  post: " + receiverType);
                int receiverTypeId = searchReceiverType(methodEntityId, receiverType);
                if (receiverTypeId != -1) {
                    ((AbsVAREntity) singleCollect.getEntityById(receiverVarId)).setTypeId(receiverTypeId);

                    singleCollect.getEntityById(methodEntityId).setParentId(receiverTypeId);
                    singleCollect.getEntityById(receiverTypeId).addChildId(methodEntityId);

                    saveRelation(methodEntityId, receiverTypeId, Configure.RELATION_RECEIVE, Configure.RELATION_RECEIVED_BY);
                    //System.out.println("method_receive_relation: receiver:" + receiverType + ", file: " + singleCollect.getEntityById(singleCollect.getEntityById(receiverId).getParentId()).getName());
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
    private void setImplementation1() {
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
                        saveRelation(typeId, interfaceId, Configure.RELATION_IMPLEMENT, Configure.RELATION_IMPLEMENTED_BY);
                    }
                }
            }
        }
    }

    /**
     * structType/asliasType-implement interface
     */
    private void setImplementation() {
        Map<Integer, ArrayList<Signature>> inf2Sig = buildInterface2Methods();
        Map<Integer, ArrayList<Signature>> type2Sig = buildType2Method();
        ArrayList<Tuple<Integer, Integer>> tmps = new ArrayList<Tuple<Integer, Integer>>();

        for (Map.Entry<Integer, ArrayList<Signature>> entry1 : type2Sig.entrySet()) {
            int typeId = entry1.getKey();
            ArrayList<Signature> sig1 = entry1.getValue();
            for (Map.Entry<Integer, ArrayList<Signature>> entry2: inf2Sig.entrySet()) {
                int interfaceId = entry2.getKey();
                ArrayList<Signature> sig2 = entry2.getValue();
                if(isExtended(sig1, sig2)) { //sig1 >= sig2
                    saveRelation(typeId, interfaceId, Configure.RELATION_IMPLEMENT, Configure.RELATION_IMPLEMENTED_BY);
                }
            }
        }
    }

    /**
     * if an interface's all api in implemnted by a struct, then it is an extension
     * @param structM
     * @param intfM
     * @return
     */
    private boolean isExtended(ArrayList<Signature> structM, ArrayList<Signature> intfM) {
        for(Signature s1 : intfM) {
            for(Signature s2: structM) {
                if(!s1.isEqual(s2)) {
                    return false;
                }
            }
        }
        return true;
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
        for(AbsEntity entity :singleCollect.getEntities()) {
            if (entity instanceof StructEntity || entity instanceof AliasTypeEntity) {
                int typeId = entity.getId();
                ArrayList<Tuple<String, Integer>> relations = entity.getRelations();
                for (Tuple<String, Integer> relation : relations) {
                    if (relation.x.equals(Configure.RELATION_RECEIVED_BY)) {
                        int methodId = relation.y;
                        String methodName = singleCollect.getEntityById(methodId).getName();
                        ArrayList<Integer> paraIds = ((MethodEntity) singleCollect.getEntityById(methodId)).getParameters();
                        ArrayList<Integer> returns = ((MethodEntity) singleCollect.getEntityById(methodId)).getReturns();
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
            AbsVAREntity varEntity = (AbsVAREntity) singleCollect.getEntityById(id);
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
        for (AbsEntity entity : singleCollect.getEntities()) {
            if (entity instanceof InterfaceFieldEntity) {
                InterfaceFieldEntity interfaceFieldEntity = (InterfaceFieldEntity) entity;
                if(interfaceFieldEntity.getType().equals(GoConstantString.INTERFACE_FIELD_IS_METHOD)) {
                    String methodName = interfaceFieldEntity.getName();
                    ArrayList<AbsVAREntity> paras = interfaceFieldEntity.getParameters();
                    ArrayList<AbsVAREntity> returns = interfaceFieldEntity.getReturns();
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

    private Map<Integer, ArrayList<Signature>> buildInterface2Methods(){
        Map<Integer, ArrayList<Signature>> interf2Sigs = new HashMap<Integer, ArrayList<Signature>>();
        for (AbsEntity entity : singleCollect.getEntities()) {
            if (entity instanceof InterfaceFieldEntity) {
                InterfaceFieldEntity interfaceFieldEntity = (InterfaceFieldEntity) entity;
                if(interfaceFieldEntity.getType().equals(GoConstantString.INTERFACE_FIELD_IS_METHOD)) {
                    String methodName = interfaceFieldEntity.getName();
                    ArrayList<AbsVAREntity> paras = interfaceFieldEntity.getParameters();
                    ArrayList<AbsVAREntity> returns = interfaceFieldEntity.getReturns();
                    ArrayList<String> paraTypes = getTypeListFromVars(paras);
                    ArrayList<String> retTypes = getTypeListFromVars(returns);
                    Signature signature = new Signature(methodName, paraTypes, retTypes);
                    int interfaceId = interfaceFieldEntity.getParentId();
                    if (!interf2Sigs.containsKey(interfaceId)) {
                        interf2Sigs.put(interfaceId, new ArrayList<Signature>());
                    }
                    interf2Sigs.get(interfaceId).add(signature);
                }
            }
        }
        return interf2Sigs;
    }


    private ArrayList<String> getTypeListFromVars(ArrayList<AbsVAREntity> vars) {
        ArrayList<String> types = new ArrayList<String>();
        for (AbsVAREntity varEntity : vars) {
            String type = varEntity.getType();
            types.add(type);
        }
        return types;
    }


    /**
     * priextractor.goextractor.searcher the uerr (struct type or alias type) id of receiver type for methodEntityId
     * @param methodEntityId
     * @param receiverType
     * @return
     */
    private int searchReceiverType(int methodEntityId, String receiverType) {
        int receiverId = -1;
        int fileId = singleCollect.getEntityById(methodEntityId).getParentId();
        if (fileId != -1) {
            int packageId = singleCollect.getEntityById(fileId).getParentId();
            receiverId = findNameLocal(packageId, receiverType, GoConstantString.ENTITY_STRUCT_ALIAS);
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
        singleCollect.getEntityById(entityId1).addRelation(relation1);

        Tuple<String, Integer> relation2 =
                new Tuple<String, Integer>(relationType2, entityId1);
        singleCollect.getEntityById(entityId2).addRelation(relation2);
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
        int fileId = singleCollect.getEntityById(structId).getParentId();
        //System.out.println("XXXXXXXXXfileID:" + fileId);
        if (fileId != -1 && singleCollect.getEntityById(fileId) instanceof AbsFILEntity) {
            packageId = singleCollect.getEntityById(fileId).getParentId();
            importedPackageIds = findImportPackages(fileId);
            importedPackageAliasMap = ((AbsFILEntity) singleCollect.getEntityById(fileId)).getImportsAlias();
        }

        if(packageId != -1) {
            //find name in local package
            int res = findNameLocal(packageId, embededStructName, GoConstantString.ENTITY_STRUCT);
            if(res != -1 ){
                return res;
            }
            //find name in remote/import package
            for (int importedPackageId : importedPackageIds) {
                String localEmbededStructName = transformRemote2LocalName(importedPackageAliasMap.get(importedPackageId),importedPackageId, embededStructName);
                res = findNameLocal(importedPackageId, localEmbededStructName, GoConstantString.ENTITY_STRUCT);
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
        int fileId = singleCollect.getEntityById(interfaceId).getParentId();
        if(fileId == -1) {
            return -1;
        }
        if (singleCollect.getEntityById(fileId) instanceof AbsFILEntity) {
            packageId = singleCollect.getEntityById(fileId).getParentId();
            importedPackageIds = findImportPackages(fileId);
            importedPackageAliasMap = ((AbsFILEntity) singleCollect.getEntityById(fileId)).getImportsAlias();
        }

        if(packageId != -1) {
            //find name in local package
            int res = findNameLocal(packageId, embededInterfaceName, GoConstantString.ENTITY_INTERFACE);
            if(res != -1 ){
                return res;
            }
            //find name in remote/import package
            for (int importedPackageId : importedPackageIds) {
                String localEmbededInterfaceName = transformRemote2LocalName(importedPackageAliasMap.get(importedPackageId),importedPackageId, embededInterfaceName);
                res = findNameLocal(importedPackageId, localEmbededInterfaceName, GoConstantString.ENTITY_INTERFACE);
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
        String localName = Configure.NULL_STRING;
        if (alias.equals(Configure.NULL_STRING)) {   // the reference way is packageName.X, so return X
            String packageName = singleCollect.getEntityById(packageId).getName();
            if(remoteName.startsWith(packageName)) {
                localName = remoteName.substring(packageName.length(), remoteName.length());
            }

        }
        else if (alias.equals(Configure.DOT)) {// the reference way is X, so return X
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
        ArrayList<Integer> fileIds = singleCollect.getEntityById(packageId).getChildrenIds();

        //find the unknownName, without naming transformation
        for (int fileId : fileIds) {
            for (int entityId : singleCollect.getEntityById(fileId).getChildrenIds()) {
                if (entityType.equals(GoConstantString.ENTITY_STRUCT)
                        && singleCollect.getEntityById(entityId) instanceof StructEntity
                        && singleCollect.getEntityById(entityId).getName().equals(unknownName)) {
                    return entityId;
                } else if (entityType.equals(GoConstantString.ENTITY_INTERFACE)
                        && singleCollect.getEntityById(entityId) instanceof InterfaceEntity
                        && singleCollect.getEntityById(entityId).getName().equals(unknownName)) {
                    return entityId;
                }
                else if (entityType.equals(GoConstantString.ENTITY_STRUCT_ALIAS)
                        && (singleCollect.getEntityById(entityId) instanceof StructEntity
                        || singleCollect.getEntityById(entityId) instanceof AliasTypeEntity)
                        && singleCollect.getEntityById(entityId).getName().equals(unknownName)) {
                    return entityId;
                }
                else if (entityType.equals(GoConstantString.ENTITY_FUNCTION)
                        && singleCollect.getEntityById(entityId) instanceof AbsFUNEntity
                        && ! (singleCollect.getEntityById(entityId) instanceof MethodEntity)
                        && singleCollect.getEntityById(entityId).getName().equals(unknownName)) {
                    return entityId;
                }
                else if (entityType.equals(GoConstantString.ENTITY_METHOD)
                        && ! (singleCollect.getEntityById(entityId) instanceof MethodEntity)
                        && singleCollect.getEntityById(entityId).getName().equals(unknownName)) {
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
        for (Tuple<String, Integer> relation : singleCollect.getEntityById(fileId).getRelations()) {
            if (relation.x.equals(Configure.RELATION_IMPORT)) {
                ids.add(relation.y);
            }
        }
        return ids;
    }


    private int searchPackageIndex(String packagePath) {
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

}
