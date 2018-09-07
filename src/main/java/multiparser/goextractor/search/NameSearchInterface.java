package multiparser.goextractor.search;


import multiparser.goextractor.goentity.InterfaceEntity;
import multiparser.goextractor.goentity.InterfaceFieldEntity;
import multiparser.goextractor.ConstantString;
import multiparser.util.Configure;
import multiparser.util.Tuple;
import multiparser.extractor.SingleCollect;
import sun.security.krb5.Config;

import java.util.ArrayList;

public class NameSearchInterface {

    SingleCollect singleCollect = SingleCollect.getSingleCollectInstance();

    /**
     * find methodName  within interface and its embededInterfaces
     * it is like a breadSearch, first multiparser.goextractor.search the member methodName.(outlayer hide the inner one when the same memberName)
     * if not,  then multiparser.goextractor.search the first layer children Interfaces,
     * if not, then multiparser.goextractor.search the second layer children interfaces,
     * go on .....
     * @param methodName
     * @param interfaceId
     * @return
     */
    public int findMethodInInfAndEmbededInfs(String methodName, int interfaceId) {
        if(interfaceId == -1
                || !(singleCollect.getEntities().get(interfaceId) instanceof InterfaceEntity)
                ) {
            return -1;
        }
        ArrayList<Integer> interfaceIds = new ArrayList<Integer>();
        int thisInterfaceId = -1;
        interfaceIds.add(interfaceId);
        while(!interfaceIds.isEmpty()) {
            thisInterfaceId = interfaceIds.get(0);
            interfaceIds.remove(0);
            if (findMethodInInf(methodName, thisInterfaceId) != -1) {
                return thisInterfaceId;
            }
            else{
                for(Tuple<String, Integer> relation : singleCollect.getEntities().get(thisInterfaceId).getRelations()) {
                    if (relation.x.equals(Configure.RELATION_EMBED)) {
                        int embededInterfaceId = relation.y;
                        interfaceIds.add(embededInterfaceId);
                    }
                }
            }
        }
        return -1; //return -1 or return the latest found id?
    }



    /**
     * find fieldId by methodName within interface but not in its embededInterface
     * @param methodName
     * @param interfaceId
     * @return
     */
    public int findMethodInInf(String methodName, int interfaceId) {
        if(interfaceId == -1
                || !(singleCollect.getEntities().get(interfaceId) instanceof InterfaceEntity)
                ) {
            return -1;
        }
        for(int childId : singleCollect.getEntities().get(interfaceId).getChildrenIds()) {
            if(singleCollect.getEntities().get(childId) instanceof InterfaceFieldEntity) {
                InterfaceFieldEntity interfaceFieldEntity = (InterfaceFieldEntity) singleCollect.getEntities().get(childId);
                if(interfaceFieldEntity.getType().equals(ConstantString.INTERFACE_FIELD_IS_METHOD)
                        && interfaceFieldEntity.getName().equals(methodName)) {
                    return interfaceFieldEntity.getId();
                }
            }
        }
        return -1;
    }

}
