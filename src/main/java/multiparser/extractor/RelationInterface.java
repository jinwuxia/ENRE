package multiparser.extractor;


import multiparser.entity.Entity;
import multiparser.entity.FileEntity;
import multiparser.util.Configure;
import multiparser.util.Tuple;

import java.util.ArrayList;

public abstract class RelationInterface {

    protected SingleCollect singleCollect = SingleCollect.getSingleCollectInstance();

    public abstract String basicStatis();

    public ArrayList<Tuple<String, String>> getDepByType(String level, String depType) {
        if(depType.equals(Configure.RELATION_IMPLEMENT)) {
            return getImplementDeps(level);
        }
        if(depType.equals(Configure.RELATION_INHERIT)) {
            //ArrayList<Tuple<String, String>> deps;
            //deps =  getEmbedInterfaceDep(level);
            //deps.addAll(getEmbedStructDep(level));
            //return deps;
            return getInheritDeps(level);
        }
        if(depType.equals(Configure.RELATION_SET)) {
            return getFunctionSets(level);
        }
        if(depType.equals(Configure.RELATION_USE)) {
            return getFunctionUses(level);
        }
        if(depType.equals(Configure.RELATION_PARAMETER)) {
            return getFunctionParas(level);
        }
        if(depType.equals(Configure.RELATION_RETURN)) {
            return getFunctionRets(level);
        }
        //if(depType.equals(Configure.RELATION_RECEIVE)) {
        //    return getMethodReceiveDeps(level);
        //}
        if(depType.equals(Configure.RELATION_CALL)) {
            return getFunctionCalls(level);
        }
        if(depType.equals(Configure.RELATION_IMPORT)) {
            return getImportDeps(level);
        }
        return null;

    }


    public ArrayList<String> getAllFiles() {
        ArrayList<String> files = new ArrayList<String>();
        for (Entity entity : singleCollect.getEntities()) {
            if(entity instanceof FileEntity) {
                String fileName = entity.getName();
                files.add(fileName);
            }
        }
        return files;
    }

    public abstract ArrayList<Tuple<String, String>> getImportDeps(String level);
    public abstract ArrayList<Tuple<String, String>> getImplementDeps(String level);
    public abstract ArrayList<Tuple<String, String>> getInheritDeps(String level);
    public abstract ArrayList<Tuple<String, String>> getFunctionCalls(String level);
    public abstract ArrayList<Tuple<String, String>> getFunctionSets(String level);
    public abstract ArrayList<Tuple<String, String>> getFunctionUses(String level);
    public abstract ArrayList<Tuple<String, String>> getFunctionParas(String level);
    public abstract ArrayList<Tuple<String, String>> getFunctionRets(String level);
}
