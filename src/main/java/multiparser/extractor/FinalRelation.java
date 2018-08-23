package multiparser.extractor;

import com.sun.org.apache.xpath.internal.operations.Mod;
import multiparser.entity.*;
import multiparser.goextractor.goentity.AliasTypeEntity;
import multiparser.goextractor.goentity.InterfaceEntity;
import multiparser.goextractor.goentity.MethodEntity;
import multiparser.goextractor.goentity.StructEntity;
import multiparser.py3extractor.pyentity.*;
import multiparser.util.Tuple;
import multiparser.goextractor.ConstantString;

import java.util.ArrayList;

public class FinalRelation {
    SingleCollect singleCollect = SingleCollect.getSingleCollectInstance();



    public String basicStatis() {
        int fileCount = 0;
        int packageCount = 0;
        int functionCount = 0;
        int methodCount = 0;
        int interfaceCount = 0;
        int structCount = 0;
        int classCount = 0;
        int classmethodCount = 0;
        int staticmethodCount = 0;
        int instmethodCount = 0;


        for(Entity entity : singleCollect.getEntities()) {
            if(entity instanceof PackageEntity) {
                packageCount ++;
            }
            else if(entity instanceof FileEntity) {
                fileCount ++;
            }
            else if(entity instanceof FunctionEntity
                    && !(entity instanceof MethodEntity)
                    && !(entity instanceof ClassMethodEntity)
                    && !(entity instanceof ClassStaticMethodEntity)
                    && !(entity instanceof InstMethodEntity)) {
                functionCount ++;
            }
            else if(entity instanceof ClassMethodEntity) {
                classmethodCount ++;
            }
            else if(entity instanceof ClassStaticMethodEntity) {
                staticmethodCount ++;
            }
            else if(entity instanceof InstMethodEntity) {
                instmethodCount ++;
            }
            else if (entity instanceof MethodEntity) {
                methodCount ++;
            }
            else if(entity instanceof StructEntity) {
                structCount ++;
            }
            else if(entity instanceof ClassEntity) {
                classCount ++;
            }
            else if(entity instanceof InterfaceEntity) {
                interfaceCount ++;
            }
        }
        String str = "";
        if(packageCount != 0) {
            str += ("Package:     " + Integer.toString(packageCount) + "\n");
        }
        if(fileCount != 0) {
            str += ("File/module: " + Integer.toString(fileCount) + "\n");
        }
        if(classCount != 0) {
            str += ("Class:       " + Integer.toString(classCount) + "\n");
        }
        if(functionCount != 0) {
            str += ("Function:    " + Integer.toString(functionCount) + "\n");
        }
        if(methodCount != 0) {
            str += ("Method:      " + Integer.toString(methodCount) + "\n");
        }
        if(instmethodCount != 0) {
            str += ("InstMethod:  " + Integer.toString(instmethodCount) + "\n");
        }
        if(classmethodCount != 0) {
            str += ("classMethod:  " + Integer.toString(classmethodCount) + "\n");
        }
        if(staticmethodCount != 0) {
            str += ("staticMethod:  " + Integer.toString(staticmethodCount) + "\n");
        }
        if(structCount != 0) {
            str += ("Struct:      " + Integer.toString(structCount) + "\n");
        }
        if(interfaceCount != 0) {
            str += ("Interface:   " + Integer.toString(interfaceCount) + "\n");
        }

        return str;
    }


    public ArrayList<String> outputFiles() {
        ArrayList<String> files = new ArrayList<String>();
        for (Entity entity : singleCollect.getEntities()) {
            if(entity instanceof FileEntity) {
                String fileName = entity.getName();
                files.add(fileName);
            }
        }
        return files;
    }

    public void outputAllEntities() {
        //the following is the test.
        System.out.println("\nall entities:");
        SingleCollect singleCollect = SingleCollect.getSingleCollectInstance();
        for(Entity entity : singleCollect.getEntities()) {
            System.out.println(entity);
        }
    }

    public void outputAllpackages() {
        System.out.println("\npackages:");
        SingleCollect singleCollect = SingleCollect.getSingleCollectInstance();
        for(Entity entity : singleCollect.getEntities()) {
            if(entity instanceof PackageEntity) {
                System.out.println(entity);
            }
        }
    }

    public void outputAllClasses() {
        System.out.println("\nclasses:");
        SingleCollect singleCollect = SingleCollect.getSingleCollectInstance();
        for(Entity entity : singleCollect.getEntities()) {
            if(entity instanceof ClassEntity) {
                System.out.println(entity);
            }
        }
    }

    public void outputAllModules() {
        System.out.println("\nmodules:");
        SingleCollect singleCollect = SingleCollect.getSingleCollectInstance();
        for(Entity entity : singleCollect.getEntities()) {
            if(entity instanceof ModuleEntity) {
                System.out.println(entity);
            }
        }
    }

    public void outputAllInstMethods() {
        System.out.println("\ninstance methods:");
        SingleCollect singleCollect = SingleCollect.getSingleCollectInstance();
        for(Entity entity : singleCollect.getEntities()) {
            if(entity instanceof InstMethodEntity) {
                System.out.println(entity);
            }
        }
    }

    public void outputAllClassMethods() {
        System.out.println("\nclass methods:");
        SingleCollect singleCollect = SingleCollect.getSingleCollectInstance();
        for(Entity entity : singleCollect.getEntities()) {
            if(entity instanceof ClassMethodEntity) {
                System.out.println(entity);
            }
        }
    }

    public void outputAllClassStaticMethods() {
        System.out.println("\nclass static methods:");
        SingleCollect singleCollect = SingleCollect.getSingleCollectInstance();
        for(Entity entity : singleCollect.getEntities()) {
            if(entity instanceof ClassStaticMethodEntity) {
                System.out.println(entity);
            }
        }
    }


    public void outputClassVarDetail() {
        System.out.println("\nClass detail:");
        SingleCollect singleCollect = SingleCollect.getSingleCollectInstance();
        for(Entity entity : singleCollect.getEntities()) {
            if(entity instanceof ClassEntity) {
                System.out.println(entity);
                System.out.println("parent: " + singleCollect.getEntities().get(entity.getParentId()).getName());
                for (int childId : entity.getChildrenIds()) {
                    if(singleCollect.getEntities().get(childId) instanceof VarEntity) {
                        System.out.println("child: " + singleCollect.getEntities().get(childId));
                    }
                }
            }
        }
    }


    public void outputGloVars() {
        System.out.println("\nGlobal vars in modules:");
        SingleCollect singleCollect = SingleCollect.getSingleCollectInstance();
        for(Entity entity : singleCollect.getEntities()) {
            if( entity instanceof ModuleEntity) {
                System.out.println(entity);
                for (int childId : entity.getChildrenIds()) {
                    if(singleCollect.getEntities().get(childId) instanceof VarEntity) {
                        System.out.println("child var: " + singleCollect.getEntities().get(childId));
                    }
                }
            }
        }
    }



    public void outputAllFunctions() {
        for (Entity functionEntity : singleCollect.getEntities()) {
            if (functionEntity instanceof FunctionEntity) {
                System.out.println("Function: " + functionEntity.getName());
                System.out.println("in file: " + singleCollect.getEntities().get(functionEntity.getParentId()).getName());
                for(int id : ((FunctionEntity) functionEntity).getParameters()) {
                    VarEntity varEntity = (VarEntity) singleCollect.getEntities().get(id);
                    System.out.println("---parameter: " + varEntity.getType() + ":" + varEntity.getName());
                }
                for(int id : ((FunctionEntity) functionEntity).getReturns()) {
                    VarEntity varEntity = (VarEntity) singleCollect.getEntities().get(id);
                    System.out.println("---return: " + varEntity.getType() + ":" + varEntity.getName());
                }
                for (int id: functionEntity.getChildrenIds()) {
                    VarEntity varEntity = (VarEntity) singleCollect.getEntities().get(id);
                    System.out.println("---localVar: " + varEntity.getType() + ":" + varEntity.getName());
                }
                for(LocalName localName: ((FunctionEntity) functionEntity).getLocalNames()) {
                    System.out.println("---" + localName);
                }
                for(LocalBlock localBlock: ((FunctionEntity) functionEntity).getLocalBlocks()) {
                    System.out.println("---" + localBlock);
                }

                System.out.println("---calledfunctions" + ((FunctionEntity) functionEntity).getCalledFunctions());
                //System.out.println("---calledfunctions with weight" + ((FunctionEntity) functionEntity).getCalledWeightedFunctions());

                //System.out.println("---name2IDmap:" + ((FunctionEntity) functionEntity).getName2IdMap());
                //System.out.println("---name2Rolemap:" + ((FunctionEntity) functionEntity).getName2RoleMap());
                //System.out.println("---name2Usagemap:" + ((FunctionEntity) functionEntity).getName2UsageMap());
                System.out.println("\n");
            }// end if
        }//end for
    }


    public void outputMethods() {
        for (Entity methodEntity : singleCollect.getEntities()) {
            if(methodEntity instanceof MethodEntity) {
                System.out.println("Method: " + methodEntity.getName());
                System.out.println("receiver:" + singleCollect.getEntities().get(((MethodEntity) methodEntity).getReceiverVarId()));
                System.out.println("in file: " + singleCollect.getEntities().get(methodEntity.getParentId()).getName());
                for(int id : ((FunctionEntity) methodEntity).getParameters()) {
                    VarEntity varEntity = (VarEntity) singleCollect.getEntities().get(id);
                    System.out.println("---parameter: " + varEntity.getType() + ":" + varEntity.getName());
                }
                for(int id : ((FunctionEntity) methodEntity).getReturns()) {
                    VarEntity varEntity = (VarEntity) singleCollect.getEntities().get(id);
                    System.out.println("---return: " + varEntity.getType() + ":" + varEntity.getName());
                }
                for (int id: methodEntity.getChildrenIds()) {
                    VarEntity varEntity = (VarEntity) singleCollect.getEntities().get(id);
                    System.out.println("---localVar: " + varEntity.getType() + ":" + varEntity.getName());
                }
                for(LocalName localName: ((MethodEntity) methodEntity).getLocalNames()) {
                    System.out.println("---" + localName);
                }
                for(LocalBlock localBlock: ((MethodEntity) methodEntity).getLocalBlocks()) {
                    System.out.println("---" + localBlock);
                }
                System.out.println("---name2IDmap:" + ((FunctionEntity) methodEntity).getName2IdMap());
                System.out.println("---name2Rolemap:" + ((FunctionEntity) methodEntity).getName2RoleMap());
                System.out.println("---name2Usagemap:" + ((FunctionEntity) methodEntity).getName2UsageMap());
                System.out.println("\n");
            }// end if
        }//end for
    }



    public ArrayList<Tuple<String, String>> getDepByType(String level, String depType) {
        if(depType.equals(ConstantString.RELATION_IMPLEMENT)) {
            return getImplementDeps(level);
        }
        if(depType.equals(ConstantString.RELATION_EMBED)) {
            ArrayList<Tuple<String, String>> deps;
            deps =  getEmbedInterfaceDep(level);
            deps.addAll(getEmbedStructDep(level));
            return deps;
        }
        if(depType.equals(ConstantString.RELATION_SET)) {
            return getFunctionSets(level);
        }
        if(depType.equals(ConstantString.RELATION_USE)) {
            return getFunctionUses(level);
        }
        if(depType.equals(ConstantString.RELATION_PARAMETER)) {
            return getFunctionParas(level);
        }
        if(depType.equals(ConstantString.RELATION_RETURN)) {
            return getFunctionRets(level);
        }
        if(depType.equals(ConstantString.RELATION_RECEIVE)) {
            return getMethodReceiveDep(level);
        }
        if(depType.equals(ConstantString.RELATION_CALL)) {
            return getFunctionCalls(level);
        }
        if(depType.equals(ConstantString.RELATION_IMPORT)) {
            return getImportDep(level);
        }
        return null;
    }


    public ArrayList<Tuple<String, String>> getImportDep(String level) {
        ArrayList<Tuple<String, String>> importDeps = new ArrayList<Tuple<String, String>>();
        for(Entity fileEntity : singleCollect.getEntities()) {
            if (fileEntity instanceof FileEntity) {
                String fileName = fileEntity.getName();
                ArrayList<Tuple<String, Integer>> relations = fileEntity.getRelations();
                if (!relations.isEmpty()) {
                    for (Tuple<String, Integer> oneRelation : relations) {
                        if (oneRelation.x.equals(ConstantString.RELATION_IMPORT)) {
                            String importedPackageName = ((PackageEntity) singleCollect.getEntities().get(oneRelation.y)).getFullPath();
                            if(level.equals(ConstantString.RELATION_LEVEL_FILE)) {
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

    public ArrayList<Tuple<String, String>> getEmbedStructDep(String level) {
        ArrayList<Tuple<String, String>> embedDeps = new ArrayList<Tuple<String, String>>();
        for(Entity structEntity : singleCollect.getEntities()) {
            if (structEntity instanceof StructEntity) {
                String structName = structEntity.getName();
                ArrayList<Tuple<String, Integer>> relations = structEntity.getRelations();
                if (!relations.isEmpty()) {
                    String fileName1 = singleCollect.getEntities().get(structEntity.getParentId()).getName();
                    for (Tuple<String, Integer> oneRelation : relations) {
                        if (oneRelation.x.equals(ConstantString.RELATION_EMBED)) {
                            String embededStructName = singleCollect.getEntities().get(oneRelation.y).getName();
                            int embededFileId = singleCollect.getEntities().get(oneRelation.y).getParentId();
                            String fileName2 = singleCollect.getEntities().get(embededFileId).getName();
                            if (level.equals(ConstantString.RELATION_LEVEL_FILE)) {
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

    public ArrayList<Tuple<String, String>> getEmbedInterfaceDep(String level) {
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
                        if (oneRelation.x.equals(ConstantString.RELATION_EMBED)) {
                            String embededInterfaceName = singleCollect.getEntities().get(oneRelation.y).getName();
                            int embededFileId = singleCollect.getEntities().get(oneRelation.y).getParentId();
                            String fileName2 = "";
                            if (embededFileId != -1) {
                                fileName2 = singleCollect.getEntities().get(embededFileId).getName();
                            }
                            if (level.equals(ConstantString.RELATION_LEVEL_FILE)) {
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


    public ArrayList<Tuple<String, String>> getMethodReceiveDep(String level) {
        ArrayList<Tuple<String, String>> receiveDeps = new ArrayList<Tuple<String, String>>();
        for(Entity methodEntity : singleCollect.getEntities()) {
            if (methodEntity instanceof MethodEntity) {
                String methodEntityName = methodEntity.getName();
                ArrayList<Tuple<String, Integer>> relations = methodEntity.getRelations();
                if (!relations.isEmpty()) {
                    String fileName1 = singleCollect.getEntities().get(methodEntity.getParentId()).getName();
                    for (Tuple<String, Integer> oneRelation : relations) {
                        if (oneRelation.x.equals(ConstantString.RELATION_RECEIVE)) {
                            String structAliasName = singleCollect.getEntities().get(oneRelation.y).getName();
                            int structAliasFileId = singleCollect.getEntities().get(oneRelation.y).getParentId();
                            String fileName2 = singleCollect.getEntities().get(structAliasFileId).getName();
                            if (level.equals(ConstantString.RELATION_LEVEL_FILE)) {
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
                        if(relation.x.equals(ConstantString.RELATION_IMPLEMENT)) {
                            int interfaceId = relation.y;
                            String interfaceName = singleCollect.getEntities().get(interfaceId).getName();
                            int fileId2 = singleCollect.getEntities().get(interfaceId).getParentId();
                            String fileName2 = "";
                            if(fileId2 != -1) {
                                fileName2 = singleCollect.getEntities().get(fileId2).getName();
                            }
                            if(level.equals(ConstantString.RELATION_LEVEL_FILE)) {
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


    public ArrayList<Tuple<String, String>> getFunctionCalls(String level) {
        ArrayList<Tuple<String, String>> deps = new ArrayList<Tuple<String, String>>();
        for (Entity entity : singleCollect.getEntities()) {
            if (entity instanceof FunctionEntity) {
                String methodName1 =entity.getName();
                String fileName1 = singleCollect.getEntities().get(entity.getParentId()).getName();

                for (Tuple<String, Integer> relation : entity.getRelations()) {
                    String relationType = relation.x;
                    int entityId2 = relation.y;
                    if(relationType.equals(ConstantString.RELATION_CALL)) {
                        Entity entity2 = singleCollect.getEntities().get(entityId2);
                        String methodName2 = entity2.getName();
                        String fileName2 = singleCollect.getEntities().get(entity2.getParentId()).getName();
                        Tuple<String, String> oneCall;
                        if(level.equals(ConstantString.RELATION_LEVEL_FILE)) {
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


    /**
     * function-set-var
     * @param level
     * @return
     */
    public ArrayList<Tuple<String, String>> getFunctionSets(String level) {
        ArrayList<Tuple<String, String>> deps = new ArrayList<Tuple<String, String>>();
        for (Entity entity : singleCollect.getEntities()) {
            if (entity instanceof FunctionEntity) {
                String methodName1 =entity.getName();
                String fileName1 = singleCollect.getEntities().get(entity.getParentId()).getName();

                for (Tuple<String, Integer> relation : entity.getRelations()) {
                    String relationType = relation.x;
                    int entityId2 = relation.y;
                    if(relationType.equals(ConstantString.RELATION_SET)) {
                        Entity entity2 = singleCollect.getEntities().get(entityId2);
                        String varName2 = entity2.getName();
                        int fileId2 = getFileOfVar(entityId2);
                        String fileName2 = "";
                        if(fileId2 != -1){
                            fileName2 = singleCollect.getEntities().get(fileId2).getName();
                        }
                        Tuple<String, String> oneSet;
                        if(level.equals(ConstantString.RELATION_LEVEL_FILE)) {
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


    /**
     * function-use-var
     * @param level
     * @return
     */
    public ArrayList<Tuple<String, String>> getFunctionUses(String level) {
        ArrayList<Tuple<String, String>> deps = new ArrayList<Tuple<String, String>>();
        for (Entity entity : singleCollect.getEntities()) {
            if (entity instanceof FunctionEntity) {
                String methodName1 =entity.getName();
                String fileName1 = singleCollect.getEntities().get(entity.getParentId()).getName();

                for (Tuple<String, Integer> relation : entity.getRelations()) {
                    String relationType = relation.x;
                    int entityId2 = relation.y;
                    if(relationType.equals(ConstantString.RELATION_USE)) {
                        Entity entity2 = singleCollect.getEntities().get(entityId2);
                        String varName2 = entity2.getName();
                        int fileId2 = getFileOfVar(entityId2);
                        String fileName2 = "";
                        if(fileId2 != -1){
                            fileName2 = singleCollect.getEntities().get(fileId2).getName();
                        }

                        Tuple<String, String> oneSet;
                        if(level.equals(ConstantString.RELATION_LEVEL_FILE)) {
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



    public ArrayList<Tuple<String, String>> getFunctionParas(String level) {
        ArrayList<Tuple<String, String>> deps = new ArrayList<Tuple<String, String>>();
        for (Entity entity : singleCollect.getEntities()) {
            if(entity instanceof FunctionEntity) {
                int functionId = entity.getId();
                String functionName = entity.getName();
                int fileId1 = singleCollect.getEntities().get(functionId).getParentId();
                String fileName1 = singleCollect.getEntities().get(fileId1).getName();

                for(Tuple<String, Integer> relation : entity.getRelations()) {
                    if(relation.x.equals(ConstantString.RELATION_PARAMETER)) {
                        int varTypeId2 = relation.y;
                        String varTypeName2 = singleCollect.getEntities().get(varTypeId2).getName();
                        if(varTypeId2 != -1) {
                            int fileId2 = singleCollect.getEntities().get(varTypeId2).getParentId();
                            String fileName2 = singleCollect.getEntities().get(fileId2).getName();

                            Tuple<String, String> oneSet;
                            if(level.equals(ConstantString.RELATION_LEVEL_FILE)) {
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


    public ArrayList<Tuple<String, String>> getFunctionRets(String level) {
        ArrayList<Tuple<String, String>> deps = new ArrayList<Tuple<String, String>>();
        for (Entity entity : singleCollect.getEntities()) {
            if(entity instanceof FunctionEntity) {
                int functionId = entity.getId();
                String functionName = entity.getName();
                int fileId1 = singleCollect.getEntities().get(functionId).getParentId();
                String fileName1 = singleCollect.getEntities().get(fileId1).getName();

                for(Tuple<String, Integer> relation : entity.getRelations()) {
                    if(relation.x.equals(ConstantString.RELATION_RETURN)) {
                        int varTypeId2 = relation.y;
                        String varTypeName2 = singleCollect.getEntities().get(varTypeId2).getName();
                        if(varTypeId2 != -1) {
                            int fileId2 = singleCollect.getEntities().get(varTypeId2).getParentId();
                            String fileName2 = singleCollect.getEntities().get(fileId2).getName();

                            Tuple<String, String> oneSet;
                            if(level.equals(ConstantString.RELATION_LEVEL_FILE)) {
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

    /**
     * get the fileId of a local or global var
     * @param varId
     * @return
     */
    private int getFileOfVar(int varId) {
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




}