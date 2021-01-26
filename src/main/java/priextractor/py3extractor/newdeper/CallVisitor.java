package priextractor.py3extractor.newdeper;

import expression.ExpressionAtom;
import priextractor.py3extractor.DepVisitor;
import uerr.SingleCollect;
import util.Configure;

import java.util.ArrayList;
import java.util.List;

/**
 * save calling function dependency (only P1 possible calls) into entity
 *
 */
public class CallVisitor extends DepVisitor {

    @Override
    public void setDep() {
        for (int entityId = 0; entityId < singleCollect.getCurrentIndex(); entityId ++) {
            int containerId = singleCollect.getEntityById(entityId).getExpContainerId();
            if (containerId == -1) {
                continue;
            }
            List<ExpressionAtom> atomList = expressionCollect.getContainerById(containerId).getExpressionAtomList();
            for (ExpressionAtom atom : atomList) {
                List<Integer> calleeEntityIds = findCalleeAtomIds(atom);
                for(int calleeEntityId : calleeEntityIds) {
                    if (calleeEntityId != -1) {
                        saveRelation(entityId, calleeEntityId, Configure.RELATION_CALL, Configure.RELATION_CALLED_BY);
                    }
                }
            }

        }
    }

    /**
     * find explicit and possible callee Ids
     *  if called is a.b().c() then ->b() ->c()
     * @param atom
     * @return
     */
    private List<Integer> findCalleeAtomIds(ExpressionAtom atom) {
        List<Integer> entityIds = new ArrayList<>();
        String usage = atom.getUsageType();

        if(usage.equals(Configure.EXPRESSION_CALL)) {
            entityIds = atom.getBindIdList();
        }
        return entityIds;
    }


}
