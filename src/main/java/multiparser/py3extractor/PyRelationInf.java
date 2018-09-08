package multiparser.py3extractor;

import multiparser.entity.Entity;
import multiparser.entity.PackageEntity;
import multiparser.extractor.RelationInterface;
import multiparser.py3extractor.pyentity.*;
import multiparser.util.Configure;
import multiparser.util.Tuple;

import java.util.ArrayList;

public class PyRelationInf extends RelationInterface {

    @Override
    public String basicStatis() {
        int packageCount = 0;
        int fileCount = 0;
        int classCount = 0;
        int functionCount = 0;
        int classmethodCount = 0;
        int classtaticmethodCount = 0;
        int instmethodCount = 0;

        for(Entity entity : singleCollect.getEntities()) {
            if(entity instanceof PackageEntity) {
                packageCount ++;
            }
            else if(entity instanceof ModuleEntity) {
                fileCount ++;
            }
            else if(entity instanceof PyFunctionEntity && !(entity instanceof PyMethodEntity)) {
                functionCount ++;
            }
            else if(entity instanceof ClassMethodEntity) {
                classmethodCount ++;
            }
            else if(entity instanceof ClassStaticMethodEntity) {
                classtaticmethodCount ++;
            }
            else if(entity instanceof InstMethodEntity) {
                instmethodCount ++;
            }

            else if(entity instanceof ClassEntity) {
                classCount ++;
            }
        }
        String str = Configure.NULL_STRING;
        str += ("Package:      " + Integer.toString(packageCount) + "\n");
        str += ("File/module:  " + Integer.toString(fileCount) + "\n");
        str += ("Class:        " + Integer.toString(classCount) + "\n");
        str += ("Function:     " + Integer.toString(functionCount) + "\n");
        str += ("InstMethod:   " + Integer.toString(instmethodCount) + "\n");
        str += ("classMethod:  " + Integer.toString(classmethodCount) + "\n");
        str += ("staticMethod: " + Integer.toString(classtaticmethodCount) + "\n");
        return str;
    }

    /**
     * get funciton-call-function relations
     * @param level
     * @return
     */
    @Override
    public ArrayList<Tuple<String, String>> getFunctionCalls(String level) {
        ArrayList<Tuple<String, String>> deps = new ArrayList<Tuple<String, String>>();
        for(Entity entity : singleCollect.getEntities()) {
            if (entity instanceof PyFunctionEntity) {
                ArrayList<Tuple<String, String>> dep = getFunctionCallForEntity(entity.getId(), level);
                deps.addAll(dep);
            }
        }
        return deps;
    }



    @Override
    public ArrayList<Tuple<String, String>> getFunctionParas(String level) {
        ArrayList<Tuple<String, String>> deps = new ArrayList<Tuple<String, String>>();
        return deps;
    }

    @Override
    public ArrayList<Tuple<String, String>> getFunctionRets(String level) {
        ArrayList<Tuple<String, String>> deps = new ArrayList<Tuple<String, String>>();
        return deps;
    }

    /**
     *
     * @param level
     * @return
     */
    @Override
    public ArrayList<Tuple<String, String>> getFunctionSets(String level) {
        ArrayList<Tuple<String, String>> deps = new ArrayList<Tuple<String, String>>();
        for(Entity entity : singleCollect.getEntities()) {
            if (entity instanceof PyFunctionEntity) {
                ArrayList<Tuple<String, String>> dep = getFunctionSetsForEntity(entity.getId(), level);
                deps.addAll(dep);
            }
        }
        return deps;
    }



    /**
     * get funciton-use-var relations
     * @param level
     * @return
     */
    @Override
    public ArrayList<Tuple<String, String>> getFunctionUses(String level) {
        ArrayList<Tuple<String, String>> deps = new ArrayList<Tuple<String, String>>();
        for (Entity entity : singleCollect.getEntities()) {
            if (entity instanceof PyFunctionEntity) {
                ArrayList<Tuple<String, String>> dep = getFunctionUseForEntity(entity.getId(), level);
                deps.addAll(dep);
            }
        }
        return deps;
    }


    /**
     * @param level
     * @return
     */
    @Override
    public ArrayList<Tuple<String, String>> getImplementDeps(String level) {
        ArrayList<Tuple<String, String>> deps = new ArrayList<Tuple<String, String>>();
        return deps;
    }

    /**
     * get funciton-use-var relations
     * @param level
     * @return
     */
    @Override
    public ArrayList<Tuple<String, String>> getInheritDeps(String level) {
        ArrayList<Tuple<String, String>> deps = new ArrayList<Tuple<String, String>>();
        for (Entity entity : singleCollect.getEntities()) {
            if (entity instanceof ClassEntity) {
                ArrayList<Tuple<String, String>> dep = getInheritDepForEntity(entity.getId(), level);
                deps.addAll(dep);
            }
        }
        return deps;
    }

    /**
     * get import relations
     * @param level
     * @return
     */
    @Override
    public ArrayList<Tuple<String, String>> getImportDeps(String level) {
        ArrayList<Tuple<String, String>> deps = new ArrayList<Tuple<String, String>>();
        for (Entity entity : singleCollect.getEntities()) {
            if (entity instanceof PyFunctionEntity
                    || entity instanceof ModuleEntity) {
                ArrayList<Tuple<String, String>> fundeps = getImportDepsForEntity(entity.getId(), level);
                deps.addAll(fundeps);
            }
        }
        return deps;
    }


    /**
     * get class-inherit-class relations
     * @param level
     * @return
     */
    private ArrayList<Tuple<String, String>> getInheritDepForEntity(int classId, String level) {
        ArrayList<Tuple<String, String>> deps = new ArrayList<Tuple<String, String>>();

        Entity entity = singleCollect.getEntities().get(classId);
        String className1 = entity.getName();
        String fileName1 = getEntityFileName(classId);

        for (Tuple<String, Integer> relation : entity.getRelations()) {
            if (relation.x.equals(Configure.RELATION_INHERIT)) {
                String className2 = singleCollect.getEntities().get(relation.y).getName();
                String fileName2 = getEntityFileName(relation.y);
                if (level.equals(Configure.RELATION_LEVEL_FILE)) {
                    if (!fileName1.equals(Configure.NULL_STRING)
                            && !fileName2.equals(Configure.NULL_STRING)) {
                        Tuple<String, String> dep = new Tuple<String, String>(fileName1, fileName2);
                        deps.add(dep);
                    }
                    System.out.println("inheritRelation:" + fileName1 + Configure.COMMA + fileName2);
                } else {
                    Tuple<String, String> dep = new Tuple<String, String>(className1, className2);
                    deps.add(dep);
                }
            }
        }
        return deps;
    }




    /**
     * get module/function-import-function/module/class relations
     * @param functionOrModuelId
     * @param level
     * @return
     */
    private ArrayList<Tuple<String,String>> getImportDepsForEntity(int functionOrModuelId, String level) {
        ArrayList<Tuple<String, String>> deps = new ArrayList<Tuple<String, String>>();
        String name1 = singleCollect.getEntities().get(functionOrModuelId).getName();
        String fileName1 = getEntityFileName(functionOrModuelId);

        for(Tuple<String, Integer> relation : singleCollect.getEntities().get(functionOrModuelId).getRelations()) {
            if(relation.x.equals(Configure.RELATION_IMPORT)) {
                int id2 = relation.y;
                String name2 = singleCollect.getEntities().get(id2).getName();
                //System.out.println("name1=" + name1);
                //System.out.println("name2=" + name2);
                String fileName2 = getEntityFileName(id2);

                if(level.equals(Configure.RELATION_LEVEL_FILE)) {
                    if (!fileName1.equals(Configure.NULL_STRING)
                            && !fileName2.equals(Configure.NULL_STRING)) {
                        Tuple<String, String> dep = new Tuple<String, String>(fileName1, fileName2);
                        deps.add(dep);
                    }
                    System.out.println("importRelation:" + fileName1 + Configure.COMMA + fileName2);
                }
                else  {
                    Tuple<String, String >dep = new Tuple<String, String>(name1, name2);
                    deps.add(dep);
                }
            }
        }
        return deps;
    }


    /**
     * get file's name which the entityId) is inside
     * @param entityId  packageId, moduleId, classId, functionId, methodId, varId
     * @return
     */
    private String getEntityFileName(int entityId) {
        String fileName = Configure.NULL_STRING;
        if(entityId == -1) {
            return fileName;
        }

        if(singleCollect.getEntities().get(entityId) instanceof PackageEntity) {
            int initFileId = getInitForPackage(entityId);
            if(initFileId != -1) {
                return singleCollect.getEntities().get(initFileId).getName();
            }
        }

        int fileId = entityId;
        while(fileId != -1
            && !(singleCollect.getEntities().get(fileId) instanceof ModuleEntity)) {
            fileId = singleCollect.getEntities().get(fileId).getParentId();
        }
        if(fileId != -1 && singleCollect.getEntities().get(fileId) instanceof ModuleEntity) {
            return singleCollect.getEntities().get(fileId).getName();
        }
        return fileName;
    }

    /**
     * find init file id for this package
     * @param pkgId
     * @return
     */
    private int getInitForPackage(int pkgId) {
        for (int childId : singleCollect.getEntities().get(pkgId).getChildrenIds()) {
            if(singleCollect.getEntities().get(childId) instanceof ModuleEntity) {
                String childName = singleCollect.getEntities().get(childId).getName();
                if(childName.endsWith(ConstantString.INIT_FILE_NAME)) {
                    return  childId;
                }
            }
        }
        return -1;
    }

    private ArrayList<Tuple<String, String>> getFunctionCallForEntity(int functionId, String level) {
        ArrayList<Tuple<String, String>> deps = new ArrayList<Tuple<String, String>>();

        Entity entity = singleCollect.getEntities().get(functionId);
        String callerName = entity.getName();
        String callerFileName = getEntityFileName(functionId);

        for (Tuple<String, Integer> relation : entity.getRelations()) {
            if(relation.x.equals(Configure.RELATION_CALL)) {
                int calleeId = relation.y;
                String calleeName = singleCollect.getEntities().get(calleeId).getName();
                String calleeFileName = getEntityFileName(calleeId);
                Tuple<String, String> dep;
                if(level.equals(Configure.RELATION_LEVEL_FILE)) {
                    if(!callerFileName.equals(Configure.NULL_STRING)
                            && !calleeFileName.equals(Configure.NULL_STRING)) {
                        dep = new Tuple<String, String>(callerFileName, calleeFileName);
                        deps.add(dep);
                    }
                    System.out.println("FunctionCall: " + callerFileName + Configure.COMMA +  calleeFileName);
                }
                else {
                    dep = new Tuple<String, String>(callerName, calleeName);
                    deps.add(dep);
                }

            }
        }
        return deps;
    }


    /**
     * get funciton-set-var relations
     * @param level
     * @return
     */
    private ArrayList<Tuple<String, String>> getFunctionSetsForEntity(int functionId, String level) {
        ArrayList<Tuple<String, String>> deps = new ArrayList<Tuple<String, String>>();

        Entity entity = singleCollect.getEntities().get(functionId);
        String callerName = entity.getName();
        String callerFileName = getEntityFileName(functionId);

        for (Tuple<String, Integer> relation : entity.getRelations()) {
            if (relation.x.equals(Configure.RELATION_SET)) {
                int varId = relation.y;
                String varName = singleCollect.getEntities().get(varId).getName();
                String varFileName = getEntityFileName(varId);
                Tuple<String, String> dep;
                if(level.equals(Configure.RELATION_LEVEL_FILE)) {
                    if(!callerFileName.equals(Configure.NULL_STRING)
                         && !varFileName.equals(Configure.NULL_STRING)) {
                        dep = new Tuple<String, String>(callerFileName, varFileName);
                        deps.add(dep);
                    }
                    System.out.println("setRelation:" + callerFileName + Configure.COMMA + varFileName);
                }
                else {
                    dep = new Tuple<String, String>(callerName, varName);
                    deps.add(dep);
                }

            }
        }
        return deps;
    }

    /**
     *
     * @param functionId
     * @param level
     * @return
     */
    private ArrayList<Tuple<String, String>> getFunctionUseForEntity(int functionId, String level) {
        ArrayList<Tuple<String, String>> deps = new ArrayList<Tuple<String, String>>();

        Entity entity = singleCollect.getEntities().get(functionId);
        String callerName = entity.getName();
        String callerFileName = getEntityFileName(functionId);

        for (Tuple<String, Integer> relation : entity.getRelations()) {
            if (relation.x.equals(Configure.RELATION_USE)) {
                int varId = relation.y;
                String varName = singleCollect.getEntities().get(varId).getName();
                String varFileName = getEntityFileName(varId);
                Tuple<String, String> dep;
                if(level.equals(Configure.RELATION_LEVEL_FILE)) {
                    if(!callerFileName.equals(Configure.NULL_STRING)
                         && !varFileName.equals(Configure.NULL_STRING)) {
                        dep = new Tuple<String, String>(callerFileName, varFileName);
                        deps.add(dep);
                    }
                    System.out.println("useRelation:" + callerFileName + Configure.COMMA + varFileName);
                }
                else {
                    dep = new Tuple<String, String>(callerName, varName);
                    deps.add(dep);
                }
            }
        }
        return deps;
    }





}
