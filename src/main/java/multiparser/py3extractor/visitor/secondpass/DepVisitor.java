package multiparser.py3extractor.visitor.secondpass;

import multiparser.extractor.SingleCollect;
import multiparser.util.Tuple;

public abstract class DepVisitor {

    protected SingleCollect singleCollect = SingleCollect.getSingleCollectInstance();
    /**
     * relationType1: entityId1 -> entityId2
     * relationType2: entityId2 -> entityId1
     * @param entityId1
     * @param entityId2
     * @param relationType1
     * @param relationType2
     */
    protected void saveRelation(int entityId1, int entityId2, String relationType1, String relationType2) {
        Tuple<String, Integer> relation1 =
                new Tuple<String, Integer>(relationType1, entityId2);
        singleCollect.getEntities().get(entityId1).addRelation(relation1);

        Tuple<String, Integer> relation2 =
                new Tuple<String, Integer>(relationType2, entityId1);
        singleCollect.getEntities().get(entityId2).addRelation(relation2);
    }

    public abstract void setDep();

}
