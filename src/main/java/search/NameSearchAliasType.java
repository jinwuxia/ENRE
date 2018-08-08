package search;

import entity.AliasTypeEntity;
import util.ConstantString;
import util.Tuple;
import visitor.SingleCollect;


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
        if (!(singleCollect.getEntities().get(AliasTypeId) instanceof AliasTypeEntity)) {
            return -1;
        }
        for(Tuple<String, Integer> relation : singleCollect.getEntities().get(AliasTypeId).getRelations()) {
            if (relation.x.equals(ConstantString.RELATION_RECEIVED_BY)) {
                int methodId = relation.y;
                if (singleCollect.getEntities().get(methodId).getName().equals(methodName)) {
                    return methodId;
                }
            }
        }
        return -1;
    }
}
