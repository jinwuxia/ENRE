package implicitstatistic;

import uerr.*;
import util.Configure;
import util.Tuple;

import java.util.*;

/**
 * StatisticMember: do the basic implicitstatistic for further infer the type of object in the forms of object.v or object.m()
 */
public class StatisticMember {

    private SingleCollect singleCollectInstance = SingleCollect.getSingleCollectInstance();

    /**
     * map{methodname, {para_count, classID_list}}
     */
    private Map<String, Map<Integer, ArrayList<Integer>>> methodName2ClassMap = new HashMap<>();

    /**
     * map{fieldname, classID_list}
     */
    private Map<String, ArrayList<Integer>> fieldName2ClassMap = new HashMap<>();

    /**
     * map{baseID, childID_list}
     */
    private Map<Integer, ArrayList<Integer>> inheritMap = new HashMap<>();

    /**
     * when initialize, do statistics.
     */
    public StatisticMember() {
        groupMemberWithSameName();
        groupClassByParent();

    }



    public Map<String, ArrayList<Integer>> getFieldName2ClassMap() {
        return fieldName2ClassMap;
    }

    public Map<String, Map<Integer, ArrayList<Integer>>> getMethodName2ClassMap() {
        return methodName2ClassMap;
    }

    public Map<Integer, ArrayList<Integer>> getInheritMap() {
        return inheritMap;
    }

    /**
     * group class based on its parent class.
     * map[parentId] = childIdList
     *
     * set inheritMap
     */
    private void groupClassByParent() {
        Configure configure = Configure.getConfigureInstance();
        for(AbsEntity entity :  singleCollectInstance.getEntities()) {
            int entityId = entity.getId();

            if( !(entity instanceof AbsCLSEntity)) {
                continue;
            }

            for(Tuple<String, Integer> relation :  entity.getRelations()) {
                if(!(relation.x.equals(configure.RELATION_INHERIT))) {
                    continue;
                }
                int baseId = relation.y;
                if (!inheritMap.containsKey(baseId)) {
                    inheritMap.put(baseId, new ArrayList<>());
                }
                inheritMap.get(baseId).add(entityId);
            }
        }
        /**
        for (Map.Entry<Integer, ArrayList<Integer>> entry: inheritMap.entrySet()) {
            int baseId = entry.getKey();
            String baseName = singleCollectInstance.getEntityById(baseId).getName();
            System.out.println("baseName: "+ baseName);
            for (int childId: entry.getValue()) {
                String childName = singleCollectInstance.getEntityById(childId).getName();
                System.out.println("childName: " + childName);
            }
        }
         */
    }


    /**
     * for the name of public variable member and method member,
     * it may belong to different class.
     *
     * traverse all public variable member and method member, find all possible classIDs.
     *
     * set  methodName2ClassMap and fieldName2ClassMap
     */
    private void groupMemberWithSameName() {
        for (AbsEntity entity : singleCollectInstance.getEntities()) {
            int parentId = entity.getParentId();
            if (parentId != -1 && singleCollectInstance.getEntityById(parentId) instanceof AbsCLSEntity) {
                /**
                 * if entity is a method Entity
                 */
                if (entity instanceof AbsFUNEntity) {
                    addMethodToMap(entity, parentId);
                }
                /**
                 * if entity is a field Entity
                 */
                else if (entity instanceof AbsVAREntity) {
                    addFieldToMap(entity, parentId);
                }

            }
        }
    }

    /**
     * judge a member is private or public of a class
     * based on memeber_name
     * @param name
     * @return
     */
    private boolean isPublicMember(String name) {
        if (name.startsWith("_")) {
            return false;
        }
        return true;
    }

    /**
     * add {methodname, {para_count, classId}} into methodName2ClassMap
     * @param entity
     * @param parentId
     */
    private void addMethodToMap(AbsEntity entity, int parentId) {
        String methodname = entity.getName();
        int para_count = ((AbsFUNEntity)entity).getParameters().size();

        if (isPublicMember(methodname)) {
            if(!methodName2ClassMap.containsKey(methodname)) {
                methodName2ClassMap.put(methodname, new HashMap<Integer, ArrayList<Integer>>());
            }
            if(!methodName2ClassMap.get(methodname).containsKey(para_count)) {
                methodName2ClassMap.get(methodname).put(para_count, new ArrayList<Integer>());
            }
            methodName2ClassMap.get(methodname).get(para_count).add(parentId);
        }
    }

    /**
     * add {fieldname, classId} into fieldName2ClassMap
     * @param entity
     * @param parentId
     */
    private void addFieldToMap(AbsEntity entity, int parentId) {
        String fieldName = entity.getName();
        if(isPublicMember(fieldName)) {
            if(!fieldName2ClassMap.containsKey(fieldName)) {
                fieldName2ClassMap.put(fieldName, new ArrayList<Integer>());
            }
            fieldName2ClassMap.get(fieldName).add(parentId);
        }
    }





}
