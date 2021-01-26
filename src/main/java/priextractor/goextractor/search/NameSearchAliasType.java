package priextractor.goextractor.search;

import entitybuilder.gobuilder.goentity.AliasTypeEntity;
import util.Configure;
import util.Tuple;
import uerr.SingleCollect;


public class NameSearchAliasType {
    SingleCollect singleCollect = SingleCollect.getSingleCollectInstance();

    /**
     * find  methodId by methdName within AliasTypeId
     * I think AliasType don not have embeded one, because golang only support embedInterface and embedStruct.
     * @param methodName
     * @param AliasTypeId
     * @return
     */
    public int findMethodInAliasType(String methodName, int AliasTypeId) {
        if (AliasTypeId == -1) {
            return -1;
        }
        if (!(singleCollect.getEntityById(AliasTypeId) instanceof AliasTypeEntity)) {
            return -1;
        }
        for(Tuple<String, Integer> relation : singleCollect.getEntityById(AliasTypeId).getRelations()) {
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
