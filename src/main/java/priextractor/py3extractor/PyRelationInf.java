package priextractor.py3extractor;

import entitybuilder.pybuilder.PyConstantString;
import uerr.*;
import entitybuilder.pybuilder.pyentity.*;
import util.Configure;
import util.RelationInterface;
import util.Tuple;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PyRelationInf extends RelationInterface {

    private boolean isInnerClass(AbsEntity entity) {
        int parentId = entity.getParentId();
        if(parentId != -1 && (
                singleCollect.getEntityById(parentId) instanceof AbsCLSEntity
                        || singleCollect.getEntityById(parentId) instanceof PyFunctionEntity)) {
            return true;
        }
        return false;
    }

    private boolean isInnerFunction(AbsEntity entity) {
        int parentId = entity.getParentId();
        if(parentId != -1 && (singleCollect.getEntityById(parentId) instanceof AbsCLSEntity)) {
            return true;
        }
        if(parentId != -1 && singleCollect.getEntityById(parentId) instanceof PyFunctionEntity) {
            return true;
        }
        return false;
    }

    private boolean isInnerMethod(AbsEntity entity) {
        int parentId = entity.getParentId();
        if(parentId != -1 && singleCollect.getEntityById(parentId) instanceof PyFunctionEntity) {
            return true;
        }
        return false;
    }

    @Override
    public String entityStatis() {
        int packageCount = 0;
        int fileCount = 0;
        int classCount = 0;
        int functionCount = 0;
        int methodCount = 0;
        //int classmethodCount = 0;
        //int classtaticmethodCount = 0;
        //int instmethodCount = 0;
        //int varCount = 0;

        for(AbsEntity entity : singleCollect.getEntities()) {
            if(entity instanceof AbsFLDEntity) {
                packageCount ++;
            }
            else if(entity instanceof ModuleEntity) {
                fileCount ++;
            }
            else if(entity instanceof PyFunctionEntity && !(entity instanceof PyMethodEntity)
                && !isInnerFunction(entity)
            && !entity.getSimpleName().equals("__main__")) {
                functionCount ++;
            }
            else if(entity instanceof PyMethodEntity && !isInnerMethod(entity)) {
                methodCount ++;
            }
            //else if(entity instanceof ClassMethodEntity) {
            //    classmethodCount ++;
            //}
            //else if(entity instanceof ClassStaticMethodEntity) {
            //    classtaticmethodCount ++;
            //}
            //else if(entity instanceof InstMethodEntity) {
            //    instmethodCount ++;
            //}

            else if(entity instanceof ClassEntity && !isInnerClass(entity)) {
                classCount ++;
            }
            //else if (entity instanceof AbsVAREntity) {
            //    int parentId = entity.getParentId();
            //    if(parentId != -1) {
            //        if(!(singleCollect.getEntityById(parentId) instanceof ClassEntity)) {
            //            varCount ++;
            //        }
            //    }
            //}
        }
        String str = Configure.NULL_STRING;
        str += ("Package:      " + Integer.toString(packageCount) + "\n");
        str += ("File/module:  " + Integer.toString(fileCount) + "\n");
        str += ("Class:        " + Integer.toString(classCount) + "\n");
        str += ("Function:     " + Integer.toString(functionCount) + "\n");
        str += ("Method:       " + Integer.toString(methodCount) + "\n");
        //str += ("InstMethod:   " + Integer.toString(instmethodCount) + "\n");
        //str += ("classMethod:  " + Integer.toString(classmethodCount) + "\n");
        //str += ("staticMethod: " + Integer.toString(classtaticmethodCount) + "\n");
        //str += ("variable:     " + Integer.toString(varCount) + "\n");
        return str;
    }


    @Override
    public String dependencyStatis() {
        Map<String, Integer> depMap = new HashMap<String, Integer>();
        depMap.put(Configure.RELATION_IMPORT, 0);
        depMap.put(Configure.RELATION_IMPLEMENT, 0);
        depMap.put(Configure.RELATION_INHERIT, 0);
        depMap.put(Configure.RELATION_CALL, 0);
        depMap.put(Configure.RELATION_SET, 0);
        depMap.put(Configure.RELATION_USE, 0);
        depMap.put(Configure.RELATION_PARAMETER, 0);
        depMap.put(Configure.RELATION_RETURN, 0);
        depMap.put(Configure.RELATION_RECEIVE, 0);
        for (AbsEntity entity :singleCollect.getEntities()) {
            for (Tuple<String, Integer> re : entity.getRelations()) {
                if(re.x.equals(Configure.RELATION_IMPORT) ||
                        re.x.equals(Configure.RELATION_INHERIT) ||
                        re.x.equals(Configure.RELATION_IMPLEMENT) ||
                        re.x.equals(Configure.RELATION_SET) ||
                        re.x.equals(Configure.RELATION_USE) ||
                        re.x.equals(Configure.RELATION_CALL) ||
                        re.x.equals(Configure.RELATION_PARAMETER) ||
                        re.x.equals(Configure.RELATION_RETURN)
                ) {
                    int old = depMap.get(re.x);
                    depMap.put(re.x, old + 1);
                }
            }
        }
        String str = Configure.NULL_STRING;
        for(Map.Entry<String, Integer> entry : depMap.entrySet()) {
            str += entry.getKey();
            str += ":    ";
            str += Integer.toString(entry.getValue());
            str += "\n";
        }
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
        for(AbsEntity entity : singleCollect.getEntities()) {
            if (entity instanceof PyFunctionEntity) {
                ArrayList<Tuple<String, String>> dep = getFunctionCallForEntity(entity.getId(), level);
                deps.addAll(dep);
            }
        }
        return deps;
    }


    @Override
    public ArrayList<Tuple<String, String>> getImplicitExternalCalls(String level) {
        ArrayList<Tuple<String, String>> deps = new ArrayList<Tuple<String, String>>();
        for(AbsEntity entity : singleCollect.getEntities()) {
            ArrayList<Tuple<String, String>> dep = getImplicitExternalCallForEntity(entity.getId(), level);
            deps.addAll(dep);
        }
        return deps;
    }

    @Override
    public ArrayList<Tuple<String, String>> getImplicitAll(String level) {
        ArrayList<Tuple<String, String>> deps = new ArrayList<Tuple<String, String>>();
        for(AbsEntity entity : singleCollect.getEntities()) {
            ArrayList<Tuple<String, String>> dep = getImplicitAllForEntity(entity.getId(), level);
            deps.addAll(dep);
        }
        return deps;
    }

    @Override
    public ArrayList<Tuple<String, String>> getImplicitByCategory(String level, String deptype) {
        ArrayList<Tuple<String, String>> deps = new ArrayList<Tuple<String, String>>();
        if(!dependCollect.getDepends().containsKey(deptype)) {
            return deps;
        }
        for (Tuple<Integer, Integer> relation : dependCollect.getDepends().get(deptype)) {
            int id1 = relation.x;
            int id2 = relation.y;
            AbsEntity entity = singleCollect.getEntityById(id1);
            String fileName1 = getEntityFileName(id1);
            String name1 = entity.getName();
            String fileName2 = getEntityFileName(id2);
            String name2 = singleCollect.getEntityById(id2).getName();
            Tuple<String, String> dep;
            if (level.equals(Configure.RELATION_LEVEL_FILE)) {
                if (!fileName1.equals(Configure.NULL_STRING) && !fileName2.equals(Configure.NULL_STRING)) {
                    dep = new Tuple<String, String>(fileName1, fileName2);
                    deps.add(dep);
                }
            } else {
                dep = new Tuple<String, String>(name1, name2);
                deps.add(dep);
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
        for(AbsEntity entity : singleCollect.getEntities()) {
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
        for (AbsEntity entity : singleCollect.getEntities()) {
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
        for (AbsEntity entity : singleCollect.getEntities()) {
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
        for (AbsEntity entity : singleCollect.getEntities()) {
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

        AbsEntity entity = singleCollect.getEntityById(classId);
        String className1 = entity.getName();
        String fileName1 = getEntityFileName(classId);

        for (Tuple<String, Integer> relation : entity.getRelations()) {
            if (relation.x.equals(Configure.RELATION_INHERIT)) {
                String className2 = singleCollect.getEntityById(relation.y).getName();
                String fileName2 = getEntityFileName(relation.y);
                if (level.equals(Configure.RELATION_LEVEL_FILE)) {
                    if (!fileName1.equals(Configure.NULL_STRING)
                            && !fileName2.equals(Configure.NULL_STRING)) {
                        Tuple<String, String> dep = new Tuple<String, String>(fileName1, fileName2);
                        deps.add(dep);
                    }
                    //System.out.println("inheritRelation:" + fileName1 + Configure.COMMA + fileName2);
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
        String name1 = singleCollect.getEntityById(functionOrModuelId).getName();
        String fileName1 = getEntityFileName(functionOrModuelId);

        for(Tuple<String, Integer> relation : singleCollect.getEntityById(functionOrModuelId).getRelations()) {
            if(relation.x.equals(Configure.RELATION_IMPORT)) {
                int id2 = relation.y;
                String name2 = singleCollect.getEntityById(id2).getName();
                //System.out.println("name1=" + name1);
                //System.out.println("name2=" + name2);
                String fileName2 = getEntityFileName(id2);

                if(level.equals(Configure.RELATION_LEVEL_FILE)) {
                    if (!fileName1.equals(Configure.NULL_STRING)
                            && !fileName2.equals(Configure.NULL_STRING)) {
                        Tuple<String, String> dep = new Tuple<String, String>(fileName1, fileName2);
                        deps.add(dep);
                    }
                    //System.out.println("importRelation:" + fileName1 + Configure.COMMA + fileName2);
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

        if(singleCollect.getEntityById(entityId) instanceof AbsFLDEntity) {
            int initFileId = getInitForPackage(entityId);
            if(initFileId != -1) {
                return singleCollect.getEntityById(initFileId).getName();
            }
        }

        int fileId = entityId;
        while(fileId != -1
            && !(singleCollect.getEntityById(fileId) instanceof ModuleEntity)) {
            fileId = singleCollect.getEntityById(fileId).getParentId();
        }
        if(fileId != -1 && singleCollect.getEntityById(fileId) instanceof ModuleEntity) {
            return singleCollect.getEntityById(fileId).getName();
        }
        return fileName;
    }

    /**
     * find init file id for this package
     * @param pkgId
     * @return
     */
    private int getInitForPackage(int pkgId) {
        for (int childId : singleCollect.getEntityById(pkgId).getChildrenIds()) {
            if(singleCollect.getEntityById(childId) instanceof ModuleEntity) {
                String childName = singleCollect.getEntityById(childId).getName();
                if(childName.endsWith(PyConstantString.INIT_FILE_NAME)) {
                    return  childId;
                }
            }
        }
        return -1;
    }

    private ArrayList<Tuple<String, String>> getFunctionCallForEntity(int functionId, String level) {
        ArrayList<Tuple<String, String>> deps = new ArrayList<Tuple<String, String>>();

        AbsEntity entity = singleCollect.getEntityById(functionId);
        String callerName = entity.getName();
        String callerFileName = getEntityFileName(functionId);

        for (Tuple<String, Integer> relation : entity.getRelations()) {
            if(relation.x.equals(Configure.RELATION_CALL)) {
                int calleeId = relation.y;
                String calleeName = singleCollect.getEntityById(calleeId).getName();
                String calleeFileName = getEntityFileName(calleeId);
                Tuple<String, String> dep;
                if(level.equals(Configure.RELATION_LEVEL_FILE)) {
                    if(!callerFileName.equals(Configure.NULL_STRING)
                            && !calleeFileName.equals(Configure.NULL_STRING)) {
                        dep = new Tuple<String, String>(callerFileName, calleeFileName);
                        deps.add(dep);
                    }
                    //System.out.println("FunctionCall: " + callerFileName + Configure.COMMA +  calleeFileName);
                }
                else {
                    dep = new Tuple<String, String>(callerName, calleeName);
                    deps.add(dep);
                }

            }
        }
        return deps;
    }


    ArrayList<Tuple<String, String>>  getImplicitExternalCallForEntity(int id1, String level) {
        ArrayList<Tuple<String, String>> deps = new ArrayList<Tuple<String, String>>();
        AbsEntity entity = singleCollect.getEntityById(id1);
        String fileName1 = getEntityFileName(id1);
        String name1 = entity.getName();
        for (Tuple<String, Integer> relation : entity.getRelations()) {
            int id2 = relation.y;
            String deptype = relation.x;
            if (deptype.equals(Configure.RELATION_IMPLICIT_EXTERNAL_CALL)) {
                String fileName2 = getEntityFileName(id2);
                String name2 = singleCollect.getEntityById(id2).getName();
                Tuple<String, String> dep;

                if (level.equals(Configure.RELATION_LEVEL_FILE)) {
                    if (!fileName1.equals(Configure.NULL_STRING)
                            && !fileName2.equals(Configure.NULL_STRING)) {
                        dep = new Tuple<String, String>(fileName1, fileName2);
                        deps.add(dep);
                    }
                    //System.out.println("FunctionCall: " + callerFileName + Configure.COMMA +  calleeFileName);
                } else {
                    dep = new Tuple<String, String>(name1, name2);
                    deps.add(dep);
                }
            }
        }

        return deps;
    }



    ArrayList<Tuple<String, String>>  getImplicitAllForEntity(int id1, String level) {
        ArrayList<Tuple<String, String>> deps = new ArrayList<Tuple<String, String>>();
        AbsEntity entity = singleCollect.getEntityById(id1);
        String fileName1 = getEntityFileName(id1);
        String name1 = entity.getName();
        for (Tuple<String, Integer> relation : entity.getRelations()) {
            int id2 = relation.y;
            String deptype = relation.x;
            if (deptype.startsWith(Configure.RELATION_IMPLICIT_ALL) && !deptype.startsWith(Configure.RELATION_IMPLICIT_ALLED)) {
                String fileName2 = getEntityFileName(id2);
                String name2 = singleCollect.getEntityById(id2).getName();
                Tuple<String, String> dep;

                if (level.equals(Configure.RELATION_LEVEL_FILE)) {
                    if (!fileName1.equals(Configure.NULL_STRING)
                            && !fileName2.equals(Configure.NULL_STRING)) {
                        dep = new Tuple<String, String>(fileName1, fileName2);
                        deps.add(dep);
                    }
                    //System.out.println("FunctionCall: " + callerFileName + Configure.COMMA +  calleeFileName);
                } else {
                    dep = new Tuple<String, String>(name1, name2);
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

        AbsEntity entity = singleCollect.getEntityById(functionId);
        String callerName = entity.getName();
        String callerFileName = getEntityFileName(functionId);

        for (Tuple<String, Integer> relation : entity.getRelations()) {
            if (relation.x.equals(Configure.RELATION_SET)) {
                int varId = relation.y;
                String varName = singleCollect.getEntityById(varId).getName();
                String varFileName = getEntityFileName(varId);
                Tuple<String, String> dep;
                if(level.equals(Configure.RELATION_LEVEL_FILE)) {
                    if(!callerFileName.equals(Configure.NULL_STRING)
                         && !varFileName.equals(Configure.NULL_STRING)) {
                        dep = new Tuple<String, String>(callerFileName, varFileName);
                        deps.add(dep);
                    }
                    //System.out.println("setRelation:" + callerFileName + Configure.COMMA + varFileName);
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

        AbsEntity entity = singleCollect.getEntityById(functionId);
        String callerName = entity.getName();
        String callerFileName = getEntityFileName(functionId);

        for (Tuple<String, Integer> relation : entity.getRelations()) {
            if (relation.x.equals(Configure.RELATION_USE)) {
                int varId = relation.y;
                String varName = singleCollect.getEntityById(varId).getName();
                String varFileName = getEntityFileName(varId);
                Tuple<String, String> dep;
                if(level.equals(Configure.RELATION_LEVEL_FILE)) {
                    if(!callerFileName.equals(Configure.NULL_STRING)
                         && !varFileName.equals(Configure.NULL_STRING)) {
                        dep = new Tuple<String, String>(callerFileName, varFileName);
                        deps.add(dep);
                    }
                    //System.out.println("useRelation:" + callerFileName + Configure.COMMA + varFileName);
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
