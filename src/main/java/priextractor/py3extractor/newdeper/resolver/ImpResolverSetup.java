package priextractor.py3extractor.newdeper.resolver;

import uerr.*;
import util.Configure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ImpResolverSetup {
    private SingleCollect singleCollectInstance = SingleCollect.getSingleCollectInstance();

    /**
     * map{methodname} = methodId list
     */
    private Map<String, List<Integer>> name2MethodIdMap = new HashMap<>();

    /**
     * map{fieldname} = fieldId List
     */
    private Map<String, List<Integer>> name2FieldIdMap = new HashMap<>();



    /**
     * when initialize, do statistics.
     */
    public ImpResolverSetup() {
        groupMemberIdByName();

    }



    /**
     * set methodName2MethodIdMap and fieldName2MethodIdMap
     */
    private void groupMemberIdByName() {
        for (AbsEntity entity : singleCollectInstance.getEntities()) {
            int parentId = entity.getParentId();
            if (parentId != -1 && singleCollectInstance.getEntityById(parentId) instanceof AbsCLSEntity) {
                /**
                 * if entity is a method Entity
                 */

                if (entity instanceof AbsFUNEntity) {
                    String methodName  = entity.getName();
                    int methodId = entity.getId();
                    addToMap(methodName, methodId, name2MethodIdMap);
                }
                /**
                 * if entity is a field Entity
                 */
                else if (entity instanceof AbsVAREntity) {
                    String fieldName  = entity.getName();
                    int fieldId = entity.getId();
                    addToMap(fieldName, fieldId, name2FieldIdMap);
                }

            }
        }

    }







    /**
     * search  name2MethodIdMap or name2FieldIdMap
     * @return
     */
    public List<Integer> searchIdsByName(String memberName, String type) {
        Map<String, List<Integer>> oneMap = null;
        if(type.equals(Configure.BASIC_ENTITY_FUNCTION)) {
            oneMap = name2MethodIdMap;
        }
        else if (type.equals(Configure.BASIC_ENTITY_VARIABLE)) {
            oneMap = name2FieldIdMap;
        }
        if (oneMap != null && oneMap.containsKey(memberName)) {
            return oneMap.get(memberName);
        }
        else {
            return new ArrayList<>();
        }
    }




    /**
     * add name, id to map<String, List<Integer>
     * @param name
     * @param id
     * @param oneMap
     */
    private void addToMap(String name, int id, Map<String, List<Integer>> oneMap) {
        if(!oneMap.containsKey(name)) {
            oneMap.put(name, new ArrayList<>());
        }
        oneMap.get(name).add(id);
    }
}
