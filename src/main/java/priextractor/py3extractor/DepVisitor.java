package priextractor.py3extractor;

import expression.ExpressionCollect;
import uerr.AtomDependCollect;
import uerr.SingleCollect;
import util.Tuple;

public abstract class DepVisitor {

    protected SingleCollect singleCollect = SingleCollect.getSingleCollectInstance();
    protected ExpressionCollect expressionCollect = ExpressionCollect.getExpressionCollect();
    protected AtomDependCollect atomDependCollect = AtomDependCollect.getInstance();
    /**
     * relationType1: entityId1 -> entityId2
     * relationType2: entityId2 -> entityId1
     * @param entityId1
     * @param entityId2
     * @param relationType1
     * @param relationType2
     */
    protected void saveRelation(int entityId1, int entityId2, String relationType1, String relationType2) {
        //System.out.println(singleCollect.getEntityById(entityId1).getName() + " depend " + singleCollect.getEntityById(entityId2).getName() );
        Tuple<String, Integer> relation1 =
                new Tuple<String, Integer>(relationType1, entityId2);
        singleCollect.getEntityById(entityId1).addRelation(relation1);

        Tuple<String, Integer> relation2 =
                new Tuple<String, Integer>(relationType2, entityId1);
        singleCollect.getEntityById(entityId2).addRelation(relation2);
    }

    public abstract void setDep();

}
