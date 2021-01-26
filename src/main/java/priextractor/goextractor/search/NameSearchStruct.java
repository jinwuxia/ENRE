package priextractor.goextractor.search;

import entitybuilder.gobuilder.goentity.StructEntity;
import entitybuilder.gobuilder.goentity.StructFieldEntity;
import util.Configure;
import util.Tuple;
import uerr.SingleCollect;

import java.util.ArrayList;

public class NameSearchStruct {

    SingleCollect singleCollect = SingleCollect.getSingleCollectInstance();

    /**
     * find fieldId by fieldName within struct and its embededStruct
     * it is like a breadSearch, first priextractor.goextractor.searcher the member fields.(outlayer hide the inner one when the same memberName)
     * if not,  then priextractor.goextractor.searcher the first layer children structs,
     * if not, then priextractor.goextractor.searcher the second layer children structs,
     * go on .....
     * @param fieldName
     * @param structId
     * @return
     */
    public int findFieldInStructAndEmbededStructs(String fieldName, int structId) {
        if(structId == -1
                || !(singleCollect.getEntityById(structId) instanceof StructEntity)
                ) {
            return -1;
        }
        ArrayList<Integer> structIds = new ArrayList<Integer>();
        int thisStructId = -1;
        structIds.add(structId);
        while(!structIds.isEmpty()) {
            thisStructId = structIds.get(0);
            structIds.remove(0);
            int fieldId = findFieldInStruct(fieldName, thisStructId);
            if (fieldId != -1) {
                return fieldId;
            }
            else{
                for(Tuple<String, Integer> relation : singleCollect.getEntityById(thisStructId).getRelations()) {
                    if (relation.x.equals(Configure.RELATION_INHERIT)) {
                        int embededStructId = relation.y;
                        structIds.add(embededStructId);
                    }
                }
            }
        }
        return -1; //return -1 or return the latest found id?
    }

    /**
     * find fieldId by fieldName within struct but not in its embededStruct
     * @param fieldName
     * @param structId
     * @return
     */
    public int findFieldInStruct(String fieldName, int structId) {
        if(structId == -1
                || !(singleCollect.getEntityById(structId) instanceof StructEntity)
                ) {
            return -1;
        }
        for(int entityId : singleCollect.getEntityById(structId).getChildrenIds()) {
            if (singleCollect.getEntityById(entityId) instanceof StructFieldEntity) {
                String thisFieldName = singleCollect.getEntityById(entityId).getName();
                if (thisFieldName.equals(fieldName)) {
                    return entityId;
                }
            }
        }
        return -1;
    }


    /**
     * find methodName by fieldName within struct and its embededStruct
     * it is like a breadSearch, first priextractor.goextractor.searcher the member methodName.(outlayer hide the inner one when the same memberName)
     * if not,  then priextractor.goextractor.searcher the first layer children structs,
     * if not, then priextractor.goextractor.searcher the second layer children structs,
     * go on .....
     * @param methodName
     * @param structId
     * @return
     */
    public int findMethodInStructAndEmbededStructs(String methodName, int structId){
        if(structId == -1
                || !(singleCollect.getEntityById(structId) instanceof StructEntity)
                ) {
            return -1;
        }
        ArrayList<Integer> structIds = new ArrayList<Integer>();
        int thisStructId = -1;
        structIds.add(structId);
        while(!structIds.isEmpty()) {
            thisStructId = structIds.get(0);
            structIds.remove(0);
            if (findMethodInStruct(methodName, thisStructId) != -1) {
                return thisStructId;
            }
            else{
                for(Tuple<String, Integer> relation : singleCollect.getEntityById(thisStructId).getRelations()) {
                    if (relation.x.equals(Configure.RELATION_INHERIT)) {
                        int embededStructId = relation.y;
                        structIds.add(embededStructId);
                    }
                }
            }
        }
        return -1; //return -1 or return the latest found id?
    }

    /**
     * find fieldId by methdName within struct but not in its embededStruct
     * @param methodName
     * @param structId
     * @return
     */
    public int findMethodInStruct(String methodName, int structId) {
        if(structId == -1
                || !(singleCollect.getEntityById(structId) instanceof StructEntity)
                ) {
            return -1;
        }
        for(Tuple<String, Integer> relation : singleCollect.getEntityById(structId).getRelations()) {
            if (relation.x.equals(Configure.RELATION_RECEIVED_BY)) {
                int methodId = relation.y;
                if (singleCollect.getEntityById(methodId).getName().equals(methodName)) {
                    return methodId;
                }
            }
        }
        return -1;
    }



}
