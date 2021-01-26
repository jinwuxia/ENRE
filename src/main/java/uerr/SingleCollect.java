package uerr;


import entitybuilder.pybuilder.pyentity.ModuleEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    public AbsEntity getEntityById(int id) {
        return entities.get(id);
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



    public List<Integer> findParentIds(List<Integer> childIds) {
        List<Integer> parentIds = new ArrayList<>();
        //System.out.println("child: " + childIds);
        for (Integer chilId : childIds) {
            int parentId = singleCollectInstance.getEntityById(chilId).getParentId();
            if(parentId != -1) {
                parentIds.add(parentId);
            }
        }
        //System.out.println("parent: " + parentIds);
        return parentIds;
    }

    public List<Integer> findTypeIds(List<Integer> entityIds) {
        List<Integer> typeIds = new ArrayList<>();
        for (Integer entityId : entityIds ) {
            int typeId = -1;
            if (entityId != -1) {
                typeId = getTypeId(entityId);
            }
            typeIds.add(typeId);
        }
        return typeIds;
    }


    public int getTypeId(int entityId) {
        AbsEntity entity = singleCollectInstance.getEntityById(entityId);
        int typeId = entity.getId();//for class, import, folder, type=bind
        if (entity instanceof AbsVAREntity) {
            typeId = ((AbsVAREntity) entity).getTypeId();
        }
        else if (entity instanceof AbsFUNEntity) {
            typeId = ((AbsFUNEntity) entity).getTypeId();
        }
        return typeId;
    }


    /**
     * if module, it longname = fileName(without .py)
     * if others, it longname = parentsimplename.parentsimplename....
     * @param id
     * @return
     */
    public String getLongName(int id) {
        String longname = "";
        while(id != -1) {
            //System.out.println("name:" + singleCollect.getEntityById(id).getName());
            //System.out.println("simplename:" + singleCollect.getEntityById(id).getSimpleName());
            AbsEntity entity = singleCollectInstance.getEntityById(id);
            String name = entity.getSimpleName();
            if(entity instanceof ModuleEntity) {
                name = ((ModuleEntity) entity).getModuleSimpleName();
            }

            if(!longname.equals("")) {
                longname = name + "." + longname;
            }
            else {
                longname = name;
            }
            id = singleCollectInstance.getEntityById(id).getParentId();
        }
        return longname;
    }


    public void printAllEntity() {
        for (AbsEntity entity : singleCollectInstance.getEntities()) {
            System.out.println(getLongName(entity.getId()));
        }
    }


    /**
     * @param rootname has no '.', has no '/', is a folder
     * @return
     */
    public int getRoot(String rootname) {
        for (AbsEntity entity: singleCollectInstance.getEntities()) {
            if(entity instanceof AbsFLDEntity
                    && entity.getParentId() == -1
                    && entity.getSimpleName().equals(rootname)) {
                return entity.getId();
            }
        }
        return -1;
    }

}
