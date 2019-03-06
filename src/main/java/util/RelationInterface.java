package util;

import sun.security.krb5.Config;
import uerr.*;


import java.util.ArrayList;

public abstract class RelationInterface {

    protected SingleCollect singleCollect = SingleCollect.getSingleCollectInstance();
    protected AtomDependCollect atomDependCollect = AtomDependCollect.getInstance();

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

        if( depType.equals(Configure.RELATION_ATOM_EXPLICIT)
                || depType.equals(Configure.RELATION_ATOM_IMPLICIT_P1) || depType.equals(Configure.RELATION_ATOM_IMPLICIT_P2)
                || depType.equals(Configure.RELATION_ATOM_IMPLICIT_P3) || depType.equals(Configure.RELATION_ATOM_IMPLICIT_P4)
                || depType.equals(Configure.RELATION_ATOM_IMPLICIT_P5)
                || depType.equals(Configure.RELATION_ATOM_IMPLICIT_P6) || depType.equals(Configure.RELATION_ATOM_IMPLICIT_P7) || depType.equals(Configure.RELATION_ATOM_IMPLICIT_P8)
                || depType.equals(Configure.RELATION_ATOM_IMPLICIT_P9)|| depType.equals(Configure.RELATION_ATOM_IMPLICIT_P10)|| depType.equals(Configure.RELATION_ATOM_IMPLICIT_P11)

        ) {
            return getDepByCategory(level, depType);
        }
        return null;

    }


    public ArrayList<String> getAllNodes(String level) {
        ArrayList<String> nodes = new ArrayList<String>();
        for (AbsEntity entity : singleCollect.getEntities()) {
            if(level.equals(Configure.RELATION_LEVEL_FILE)) {
                if (entity instanceof AbsFILEntity) {
                    String fileName = entity.getName();
                    nodes.add(fileName);
                }
            }
            else if (level.equals(Configure.RELATION_LEVEL_FUNCTION)) {
                if(entity instanceof AbsFUNEntity) {
                    String functionName = SingleCollect.getSingleCollectInstance().getLongName(entity.getId());
                    nodes.add(functionName);
                }
            }
        }
        return nodes;
    }

    public abstract ArrayList<Tuple<String, String>> getImportDeps(String level);
    public abstract ArrayList<Tuple<String, String>> getImplementDeps(String level);
    public abstract ArrayList<Tuple<String, String>> getInheritDeps(String level);
    public abstract ArrayList<Tuple<String, String>> getFunctionCalls(String level);
    public abstract ArrayList<Tuple<String, String>> getFunctionSets(String level);
    public abstract ArrayList<Tuple<String, String>> getFunctionUses(String level);
    public abstract ArrayList<Tuple<String, String>> getFunctionParas(String level);
    public abstract ArrayList<Tuple<String, String>> getFunctionRets(String level);
    public abstract ArrayList<Tuple<String, String>> getDepByCategory(String level, String deptype);

}
