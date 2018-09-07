package multiparser.py3extractor;

import multiparser.entity.Entity;
import multiparser.entity.FileEntity;
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
        String str = "";
        if(packageCount != 0) {
            str += ("Package:      " + Integer.toString(packageCount) + "\n");
        }
        if(fileCount != 0) {
            str += ("File/module:  " + Integer.toString(fileCount) + "\n");
        }
        if(classCount != 0) {
            str += ("Class:        " + Integer.toString(classCount) + "\n");
        }
        if(functionCount != 0) {
            str += ("Function:     " + Integer.toString(functionCount) + "\n");
        }
        if(instmethodCount != 0) {
            str += ("InstMethod:   " + Integer.toString(instmethodCount) + "\n");
        }
        if(classmethodCount != 0) {
            str += ("classMethod:  " + Integer.toString(classmethodCount) + "\n");
        }
        if(classtaticmethodCount != 0) {
            str += ("staticMethod: " + Integer.toString(classtaticmethodCount) + "\n");
        }
        return str;
    }

    @Override
    public ArrayList<Tuple<String, String>> getFunctionCalls(String level) {
        ArrayList<Tuple<String, String>> deps = new ArrayList<Tuple<String, String>>();
        for(Entity entity : singleCollect.getEntities()) {
            if(entity instanceof PyFunctionEntity) {
                String callerName = entity.getName();
                String callerFileName = singleCollect.getEntities().get(entity.getParentId()).getName();
                for (Tuple<String, Integer> relation : entity.getRelations()) {
                    if(relation.x.equals(Configure.RELATION_CALL)) {
                        int calleeId = relation.y;
                        String calleeName = singleCollect.getEntities().get(calleeId).getName();
                        String calleeFileName = singleCollect.getEntities().get(entity.getParentId()).getName();
                        Tuple<String, String> dep;
                        if(level.equals(Configure.RELATION_LEVEL_FILE)) {
                            dep = new Tuple<String, String>(callerFileName, calleeFileName);
                        }
                        else {
                            dep = new Tuple<String, String>(callerName, calleeName);
                        }
                        deps.add(dep);
                    }
                }
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

    @Override
    public ArrayList<Tuple<String, String>> getFunctionSets(String level) {
        ArrayList<Tuple<String, String>> deps = new ArrayList<Tuple<String, String>>();
        for (Entity entity : singleCollect.getEntities()) {
            if (entity instanceof PyFunctionEntity) {
                String callerName = entity.getName();
                String callerFileName = singleCollect.getEntities().get(entity.getParentId()).getName();
                for (Tuple<String, Integer> relation : entity.getRelations()) {
                    if (relation.x.equals(Configure.RELATION_SET)) {
                        int varId = relation.y;
                        String varName = singleCollect.getEntities().get(varId).getName();
                        String varFileName = getVarFileName(varId);
                        Tuple<String, String> dep;
                        if(level.equals(Configure.RELATION_LEVEL_FILE)) {
                            dep = new Tuple<String, String>(callerFileName, varFileName);
                        }
                        else {
                            dep = new Tuple<String, String>(callerName, varName);
                        }
                        deps.add(dep);
                    }
                }
            }
        }
        return deps;
    }

    @Override
    public ArrayList<Tuple<String, String>> getFunctionUses(String level) {
        return null;
    }

    @Override
    public ArrayList<Tuple<String, String>> getImplementDeps(String level) {
        ArrayList<Tuple<String, String>> deps = new ArrayList<Tuple<String, String>>();
        return deps;
    }

    @Override
    public ArrayList<Tuple<String, String>> getInheritDeps(String level) {
        return null;
    }

    @Override
    public ArrayList<Tuple<String, String>> getImportDeps(String level) {
        return null;
    }


    private String getVarFileName(int varId) {
        String fileName = Configure.NULL_STRING;
        if(varId == -1) {
            return fileName;
        }
        int fileId = varId;
        while(fileId != -1
            && !(singleCollect.getEntities().get(fileId) instanceof ModuleEntity)) {
            fileId = singleCollect.getEntities().get(fileId).getParentId();
        }
        if(fileId != -1 && singleCollect.getEntities().get(fileId) instanceof ModuleEntity) {
            return singleCollect.getEntities().get(fileId).getName();
        }
        return fileName;
    }
}
