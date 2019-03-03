package util;

import sun.security.krb5.Config;
import sun.security.krb5.Confounder;
import uerr.AbsEntity;
import uerr.AbsFILEntity;
import uerr.DependCollect;
import uerr.SingleCollect;
import util.Configure;
import util.Tuple;

import java.util.ArrayList;
import java.util.concurrent.locks.Condition;

public abstract class RelationInterface {

    protected SingleCollect singleCollect = SingleCollect.getSingleCollectInstance();
    protected DependCollect dependCollect = DependCollect.getInstance();

    public abstract String entityStatis();

    public abstract String dependencyStatis();

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
        if(depType.equals(Configure.RELATION_IMPLICIT_EXTERNAL_CALL)) {
            return getImplicitExternalCalls(level);
        }
        if(depType.equals(Configure.RELATION_IMPLICIT_ALL)) {
            return getImplicitAll(level);
        }
        if(depType.equals(Configure.RELATION_IMPLICIT_P1) || depType.equals(Configure.RELATION_IMPLICIT_P2)
                || depType.equals(Configure.RELATION_IMPLICIT_P3) || depType.equals(Configure.RELATION_IMPLICIT_P4)
                || depType.equals(Configure.RELATION_IMPLICIT_P5)
                || depType.equals(Configure.RELATION_IMPLICIT_P6) || depType.equals(Configure.RELATION_IMPLICIT_P7) || depType.equals(Configure.RELATION_IMPLICIT_P8)
                || depType.equals(Configure.RELATION_IMPLICIT_P9)|| depType.equals(Configure.RELATION_IMPLICIT_P10)|| depType.equals(Configure.RELATION_IMPLICIT_P11)

        ) {
            return getImplicitByCategory(level, depType);
        }
        return null;

    }


    public ArrayList<String> getAllFiles() {
        ArrayList<String> files = new ArrayList<String>();
        for (AbsEntity entity : singleCollect.getEntities()) {
            if(entity instanceof AbsFILEntity) {
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
    public abstract ArrayList<Tuple<String, String>> getImplicitExternalCalls(String level);
    public abstract ArrayList<Tuple<String, String>> getImplicitAll(String level);
    public abstract ArrayList<Tuple<String, String>> getImplicitByCategory(String level, String deptype);

}
