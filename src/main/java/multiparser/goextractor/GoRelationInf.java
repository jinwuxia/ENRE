package multiparser.goextractor;

import multiparser.entity.Entity;
import multiparser.entity.FileEntity;
import multiparser.entity.FunctionEntity;
import multiparser.entity.PackageEntity;
import multiparser.extractor.RelationInterface;
import multiparser.goextractor.goentity.AliasTypeEntity;
import multiparser.goextractor.goentity.InterfaceEntity;
import multiparser.goextractor.goentity.MethodEntity;
import multiparser.goextractor.goentity.StructEntity;

import multiparser.util.Configure;
import multiparser.util.Tuple;

import java.util.ArrayList;

public class GoRelationInf extends RelationInterface {
    @Override
    public String basicStatis() {
        int fileCount = 0;
        int packageCount = 0;
        int functionCount = 0;
        int methodCount = 0;
        int interfaceCount = 0;
        int structCount = 0;

        for(Entity entity : singleCollect.getEntities()) {
            if(entity instanceof PackageEntity) {
                packageCount ++;
            }
            else if(entity instanceof FileEntity) {
                fileCount ++;
            }
            else if(entity instanceof FunctionEntity
                    && !(entity instanceof MethodEntity)) {
                functionCount ++;
            }
            else if (entity instanceof MethodEntity) {
                methodCount ++;
            }
            else if(entity instanceof StructEntity) {
                structCount ++;
            }
            else if(entity instanceof InterfaceEntity) {
                interfaceCount ++;
            }
        }
        String str = "";
        str += ("Package:     " + Integer.toString(packageCount) + "\n");
        str += ("File:        " + Integer.toString(fileCount) + "\n");
        str += ("Function:    " + Integer.toString(functionCount) + "\n");
        str += ("Method:      " + Integer.toString(methodCount) + "\n");
        str += ("Struct:      " + Integer.toString(structCount) + "\n");
        str += ("Interface:   " + Integer.toString(interfaceCount) + "\n");
        return str;
    }

    @Override
    public ArrayList<Tuple<String, String>> getFunctionCalls(String level) {
        ArrayList<Tuple<String, String>> deps = new ArrayList<Tuple<String, String>>();
        for (Entity entity : singleCollect.getEntities()) {
            if (entity instanceof FunctionEntity) {
                String methodName1 =entity.getName();
                String fileName1 = singleCollect.getEntities().get(entity.getParentId()).getName();

                for (Tuple<String, Integer> relation : entity.getRelations()) {
                    String relationType = relation.x;
                    int entityId2 = relation.y;
                    if(relationType.equals(Configure.RELATION_CALL)) {
                        Entity entity2 = singleCollect.getEntities().get(entityId2);
                        String methodName2 = entity2.getName();
                        String fileName2 = singleCollect.getEntities().get(entity2.getParentId()).getName();
                        Tuple<String, String> oneCall;
                        if(level.equals(Configure.RELATION_LEVEL_FILE)) {
                            oneCall = new Tuple<String, String>(fileName1, fileName2);
                        }
                        else {
                            oneCall = new Tuple<String, String>(methodName1, methodName2);
                        }
                        deps.add(oneCall);
                    }
                }
            }
        } // end big for
        return deps;
    }

    @Override
    public ArrayList<Tuple<String, String>> getFunctionParas(String level) {
        ArrayList<Tuple<String, String>> deps = new ArrayList<Tuple<String, String>>();
        for (Entity entity : singleCollect.getEntities()) {
            if(entity instanceof FunctionEntity) {
                int functionId = entity.getId();
                String functionName = entity.getName();
                int fileId1 = singleCollect.getEntities().get(functionId).getParentId();
                String fileName1 = singleCollect.getEntities().get(fileId1).getName();

                for(Tuple<String, Integer> relation : entity.getRelations()) {
                    if(relation.x.equals(Configure.RELATION_PARAMETER)) {
                        int varTypeId2 = relation.y;
                        String varTypeName2 = singleCollect.getEntities().get(varTypeId2).getName();
                        if(varTypeId2 != -1) {
                            int fileId2 = singleCollect.getEntities().get(varTypeId2).getParentId();
                            String fileName2 = singleCollect.getEntities().get(fileId2).getName();

                            Tuple<String, String> oneSet;
                            if(level.equals(Configure.RELATION_LEVEL_FILE)) {
                                oneSet = new Tuple<String, String>(fileName1, fileName2);
                            }
                            else {
                                oneSet = new Tuple<String, String>(functionName, varTypeName2);
                            }
                            deps.add(oneSet);
                        }
                    }
                }
            }
        }
        return deps;
    }

    @Override
    public ArrayList<Tuple<String, String>> getFunctionRets(String level) {
        ArrayList<Tuple<String, String>> deps = new ArrayList<Tuple<String, String>>();
        for (Entity entity : singleCollect.getEntities()) {
            if(entity instanceof FunctionEntity) {
                int functionId = entity.getId();
                String functionName = entity.getName();
                int fileId1 = singleCollect.getEntities().get(functionId).getParentId();
                String fileName1 = singleCollect.getEntities().get(fileId1).getName();

                for(Tuple<String, Integer> relation : entity.getRelations()) {
                    if(relation.x.equals(Configure.RELATION_RETURN)) {
                        int varTypeId2 = relation.y;
                        String varTypeName2 = singleCollect.getEntities().get(varTypeId2).getName();
                        if(varTypeId2 != -1) {
                            int fileId2 = singleCollect.getEntities().get(varTypeId2).getParentId();
                            String fileName2 = singleCollect.getEntities().get(fileId2).getName();

                            Tuple<String, String> oneSet;
                            if(level.equals(Configure.RELATION_LEVEL_FILE)) {
                                oneSet = new Tuple<String, String>(fileName1, fileName2);
                            }
                            else {
                                oneSet = new Tuple<String, String>(functionName, varTypeName2);
                            }
                            deps.add(oneSet);
                        }
                    }
                }
            }
        }
        return deps;
    }

    @Override
    public ArrayList<Tuple<String, String>> getFunctionSets(String level) {
        ArrayList<Tuple<String, String>> deps = new ArrayList<Tuple<String, String>>();
        for (Entity entity : singleCollect.getEntities()) {
            if (entity instanceof FunctionEntity) {
                String methodName1 =entity.getName();
                String fileName1 = singleCollect.getEntities().get(entity.getParentId()).getName();

                for (Tuple<String, Integer> relation : entity.getRelations()) {
                    String relationType = relation.x;
                    int entityId2 = relation.y;
                    if(relationType.equals(Configure.RELATION_SET)) {
                        Entity entity2 = singleCollect.getEntities().get(entityId2);
                        String varName2 = entity2.getName();
                        int fileId2 = getFileForVar(entityId2);
                        String fileName2 = "";
                        if(fileId2 != -1){
                            fileName2 = singleCollect.getEntities().get(fileId2).getName();
                        }
                        Tuple<String, String> oneSet;
                        if(level.equals(Configure.RELATION_LEVEL_FILE)) {
                            oneSet = new Tuple<String, String>(fileName1, fileName2);
                        }
                        else {
                            oneSet = new Tuple<String, String>(methodName1, varName2);
                        }
                        deps.add(oneSet);
                    }
                }
            }
        } // end big for
        return deps;
    }

    @Override
    public ArrayList<Tuple<String, String>> getFunctionUses(String level) {
        ArrayList<Tuple<String, String>> deps = new ArrayList<Tuple<String, String>>();
        for (Entity entity : singleCollect.getEntities()) {
            if (entity instanceof FunctionEntity) {
                String methodName1 =entity.getName();
                String fileName1 = singleCollect.getEntities().get(entity.getParentId()).getName();

                for (Tuple<String, Integer> relation : entity.getRelations()) {
                    String relationType = relation.x;
                    int entityId2 = relation.y;
                    if(relationType.equals(Configure.RELATION_USE)) {
                        Entity entity2 = singleCollect.getEntities().get(entityId2);
                        String varName2 = entity2.getName();
                        int fileId2 = getFileForVar(entityId2);
                        String fileName2 = "";
                        if(fileId2 != -1){
                            fileName2 = singleCollect.getEntities().get(fileId2).getName();
                        }

                        Tuple<String, String> oneSet;
                        if(level.equals(Configure.RELATION_LEVEL_FILE)) {
                            oneSet = new Tuple<String, String>(fileName1, fileName2);
                        }
                        else {
                            oneSet = new Tuple<String, String>(methodName1, varName2);
                        }
                        deps.add(oneSet);
                    }
                }
            }
        } // end big for
        return deps;
    }

    @Override
    public ArrayList<Tuple<String, String>> getImplementDeps(String level) {
        ArrayList<Tuple<String, String>> deps = new ArrayList<Tuple<String, String>>();
        for (Entity entity : singleCollect.getEntities()) {
            if(entity instanceof StructEntity || entity instanceof AliasTypeEntity) {
                //int typeId = multiparser.entity.getId();
                String typeName = entity.getName();
                int fileId = entity.getParentId();
                if(fileId != -1) {
                    String fileName1 = singleCollect.getEntities().get(fileId).getName();
                    for(Tuple<String, Integer> relation : entity.getRelations()) {
                        if(relation.x.equals(Configure.RELATION_IMPLEMENT)) {
                            int interfaceId = relation.y;
                            String interfaceName = singleCollect.getEntities().get(interfaceId).getName();
                            int fileId2 = singleCollect.getEntities().get(interfaceId).getParentId();
                            String fileName2 = "";
                            if(fileId2 != -1) {
                                fileName2 = singleCollect.getEntities().get(fileId2).getName();
                            }
                            if(level.equals(Configure.RELATION_LEVEL_FILE)) {
                                deps.add(new Tuple<String, String>(fileName1, fileName2));
                            }
                            else {
                                deps.add(new Tuple<String, String>(typeName, interfaceName));
                            }
                        }
                    }
                }
            }
        } // end big for
        return deps;
    }


    @Override
    public ArrayList<Tuple<String, String>> getInheritDeps(String level) {
        ArrayList<Tuple<String, String>> embedDeps = new ArrayList<Tuple<String, String>>();
        embedDeps.addAll(getEmbedInterfaceDep(level));
        embedDeps.addAll(getEmbedStructDep(level));
        return embedDeps;
    }

    @Override
    public ArrayList<Tuple<String, String>> getImportDeps(String level) {
        ArrayList<Tuple<String, String>> importDeps = new ArrayList<Tuple<String, String>>();
        for(Entity fileEntity : singleCollect.getEntities()) {
            if (fileEntity instanceof FileEntity) {
                String fileName = fileEntity.getName();
                ArrayList<Tuple<String, Integer>> relations = fileEntity.getRelations();
                if (!relations.isEmpty()) {
                    for (Tuple<String, Integer> oneRelation : relations) {
                        if (oneRelation.x.equals(Configure.RELATION_IMPORT)) {
                            String importedPackageName = ((PackageEntity) singleCollect.getEntities().get(oneRelation.y)).getFullPath();
                            if(level.equals(Configure.RELATION_LEVEL_FILE)) {
                                ArrayList<Integer> fileIds2 = singleCollect.getEntities().get(oneRelation.y).getChildrenIds();
                                for (int fileId2 : fileIds2) {
                                    if(singleCollect.getEntities().get(fileId2) instanceof FileEntity) {
                                        importDeps.add(new Tuple<String, String>(fileName, singleCollect.getEntities().get(fileId2).getName()));
                                    }
                                }
                            }
                            else {
                                importDeps.add(new Tuple<String, String>(fileName, importedPackageName));
                            }

                        }
                    }
                }
            }
        }
        return importDeps;
    }



    private ArrayList<Tuple<String, String>> getEmbedStructDep(String level) {
        ArrayList<Tuple<String, String>> embedDeps = new ArrayList<Tuple<String, String>>();
        for(Entity structEntity : singleCollect.getEntities()) {
            if (structEntity instanceof StructEntity) {
                String structName = structEntity.getName();
                ArrayList<Tuple<String, Integer>> relations = structEntity.getRelations();
                if (!relations.isEmpty()) {
                    String fileName1 = singleCollect.getEntities().get(structEntity.getParentId()).getName();
                    for (Tuple<String, Integer> oneRelation : relations) {
                        if (oneRelation.x.equals(Configure.RELATION_INHERIT)) {
                            String embededStructName = singleCollect.getEntities().get(oneRelation.y).getName();
                            int embededFileId = singleCollect.getEntities().get(oneRelation.y).getParentId();
                            String fileName2 = singleCollect.getEntities().get(embededFileId).getName();
                            if (level.equals(Configure.RELATION_LEVEL_FILE)) {
                                embedDeps.add(new Tuple<String, String>(fileName1, fileName2));
                            }
                            else {
                                embedDeps.add(new Tuple<String, String>(structName, embededStructName));
                            }
                        }
                    }
                }
            }
        }
        return embedDeps;
    }

    private ArrayList<Tuple<String, String>> getEmbedInterfaceDep(String level) {
        ArrayList<Tuple<String, String>> embedDeps = new ArrayList<Tuple<String, String>>();
        for(Entity interfaceEntity : singleCollect.getEntities()) {
            if (interfaceEntity instanceof InterfaceEntity) {
                String interfaceName = interfaceEntity.getName();
                ArrayList<Tuple<String, Integer>> relations = interfaceEntity.getRelations();
                String fileName1 = "";
                if (interfaceEntity.getParentId() != -1) {
                    fileName1 = singleCollect.getEntities().get(interfaceEntity.getParentId()).getName();
                }
                if (!relations.isEmpty()) {
                    for (Tuple<String, Integer> oneRelation : relations) {
                        if (oneRelation.x.equals(Configure.RELATION_INHERIT)) {
                            String embededInterfaceName = singleCollect.getEntities().get(oneRelation.y).getName();
                            int embededFileId = singleCollect.getEntities().get(oneRelation.y).getParentId();
                            String fileName2 = "";
                            if (embededFileId != -1) {
                                fileName2 = singleCollect.getEntities().get(embededFileId).getName();
                            }
                            if (level.equals(Configure.RELATION_LEVEL_FILE)) {
                                embedDeps.add(new Tuple<String, String>(fileName1, fileName2));
                            }
                            else {
                                embedDeps.add(new Tuple<String, String>(interfaceName, embededInterfaceName));
                            }
                        }
                    }
                }
            }
        }
        return embedDeps;
    }



    /**
     * get the fileId of a local or global var
     * @param varId
     * @return
     */
    private int getFileForVar(int varId) {
        if(varId == -1) {
            return -1;
        }
        int parentId = singleCollect.getEntities().get(varId).getParentId();
        while (parentId != -1 &&
                !(singleCollect.getEntities().get(parentId) instanceof FileEntity)) {
            parentId = singleCollect.getEntities().get(parentId).getParentId();
        }
        if(parentId != -1 &&
                singleCollect.getEntities().get(parentId) instanceof FileEntity) {
            return parentId;
        }
        return -1;
    }

    public ArrayList<Tuple<String, String>> getMethodReceiveDep(String level) {
        ArrayList<Tuple<String, String>> receiveDeps = new ArrayList<Tuple<String, String>>();
        for(Entity methodEntity : singleCollect.getEntities()) {
            if (methodEntity instanceof MethodEntity) {
                String methodEntityName = methodEntity.getName();
                ArrayList<Tuple<String, Integer>> relations = methodEntity.getRelations();
                if (!relations.isEmpty()) {
                    String fileName1 = singleCollect.getEntities().get(methodEntity.getParentId()).getName();
                    for (Tuple<String, Integer> oneRelation : relations) {
                        if (oneRelation.x.equals(Configure.RELATION_RECEIVE)) {
                            String structAliasName = singleCollect.getEntities().get(oneRelation.y).getName();
                            int structAliasFileId = singleCollect.getEntities().get(oneRelation.y).getParentId();
                            String fileName2 = singleCollect.getEntities().get(structAliasFileId).getName();
                            if (level.equals(Configure.RELATION_LEVEL_FILE)) {
                                receiveDeps.add(new Tuple<String, String>(fileName1, fileName2));
                            }
                            else {
                                receiveDeps.add(new Tuple<String, String>(methodEntityName, structAliasName));
                            }
                        }
                    }
                }
            }
        }
        return receiveDeps;
    }
}
