package implicitstatistic;

import uerr.*;

import java.util.*;

/**
 * StatisticForInfer: do the basic implicitstatistic for further infer the type of object in the forms of object.v or object.m()
 */
public class StatisticForInfer {

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
     * when initialize, do statistics.
     */
    public StatisticForInfer() {
        groupMemberWithSameName();

    }


    public Map<String, ArrayList<Integer>> getFieldName2ClassMap() {
        return fieldName2ClassMap;
    }

    public Map<String, Map<Integer, ArrayList<Integer>>> getMethodName2ClassMap() {
        return methodName2ClassMap;
    }

    /**
     * for the name of public variable member and method member,
     * it may belong to different class.
     *
     * traverse all public variable member and method member, find all possible classIDs.
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
