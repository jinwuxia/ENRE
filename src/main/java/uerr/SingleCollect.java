package uerr;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SingleCollect {
    // all entities.  set id = index
    private ArrayList<AbsEntity> entities = new ArrayList<AbsEntity>();

    private static SingleCollect singleCollectInstance = new SingleCollect();

    //collect functions with same name.
    private Map<String, ArrayList<Integer>> methodsWithSameName = new HashMap<String, ArrayList<Integer>>();

    private SingleCollect(){}

    public static SingleCollect getSingleCollectInstance() {
        return singleCollectInstance;
    }

    public int getCurrentIndex() {
        return entities.size();
    }

    public ArrayList<AbsEntity> getEntities() {
        return entities;
    }

    public void addEntity(AbsEntity entity) {
        entities.add(entity);
    }



    public boolean isFolder(int id) {
        if(id == -1) {
            return false;
        }
        if (singleCollectInstance.getEntities().get(id) instanceof AbsFLDEntity) {
            return true;
        }
        return false;
    }

    public boolean isFile(int id) {
        if(id == -1) {
            return false;
        }
        if(singleCollectInstance.getEntities().get(id) instanceof AbsFILEntity) {
            return true;
        }
        return false;
    }

    public boolean isClass(int id) {
        if(id != -1 && singleCollectInstance.getEntities().get(id) instanceof AbsCLSEntity) {
            return true;
        }
        return false;
    }

    public boolean isFunction(int id) {
        if(id != -1 && singleCollectInstance.getEntities().get(id) instanceof AbsFUNEntity) {
            return true;
        }
        return false;

    }

    public boolean isVariable(int id) {
        if(id != -1 && singleCollectInstance.getEntities().get(id) instanceof AbsVAREntity) {
            return true;
        }
        return false;
    }

    public boolean isVarTypeResolved(int id) {
        if(isVariable(id)
                && ((AbsVAREntity)singleCollectInstance.getEntities().get(id)).getTypeId() != -1) {
            return true;
        }
        return false;
    }

    /**
     * find all method or function which has functionName
     * @param functionName
     * @return
     */
    public ArrayList<Integer> searchFunctionByName(String functionName) {
        ArrayList<Integer> ids = new ArrayList<Integer>();
        if(methodsWithSameName.containsKey(functionName)) {
            return methodsWithSameName.get(functionName);
        }
        return ids;
    }

    /**
     * find all function ids with same name,
     * maybe we should consider the number of parameters in future
     */
    public void identifySameMethodName() {
        for (AbsEntity entity : singleCollectInstance.getEntities()) {
            if(entity instanceof AbsFUNEntity) {
                String name = entity.getName();
                if(name.equals("__init__")) {
                    continue;
                }
                int parentId = entity.getParentId();
                if(parentId != -1 && singleCollectInstance.getEntities().get(parentId) instanceof AbsCLSEntity) {
                    if(!methodsWithSameName.containsKey(name)) {
                        methodsWithSameName.put(name, new ArrayList<Integer>());
                    }
                    methodsWithSameName.get(name).add(entity.getId());
                }
            }
        }

        /*
        for(Map.Entry<String, ArrayList<Integer>> entry : methodsWithSameName.entrySet()) {
            String name = entry.getKey();
            int len = entry.getValue().size();
            if(len > 1) {
                System.out.print(name + ":");
                System.out.print(len);
                System.out.print(",");
            }
        }
        System.out.println("\n");
        */
    }




}
