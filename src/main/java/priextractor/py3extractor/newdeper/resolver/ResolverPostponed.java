package priextractor.py3extractor.newdeper.resolver;

import expression.ExpressionAtom;
import expression.ExpressionCollect;
import priextractor.py3extractor.newdeper.SequencecUtil;
import uerr.SingleCollect;
import util.Configure;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static java.lang.System.exit;

public class ResolverPostponed {

    private ExpressionCollect expressionCollect = ExpressionCollect.getExpressionCollect();
    private SingleCollect singleCollect = SingleCollect.getSingleCollectInstance();
    /**
     * only see the types of current atom
     * resolve the atoms bind  after the atoms in baseLaterList
     *
     * beacuse only entity Id is updated, typeId will not, so the second next atoms will not be affected.
     */
    public void updateNextAtom(int containerId, int expId, int atomId) {

        List<ExpressionAtom> atomList = expressionCollect.getContainerById(containerId).getExpressionList().get(expId).getExpressionAtomList();
        int parentAtomId = atomId;
        if (parentAtomId + 1 <  atomList.size())  { //if has the subsequent atoms
            int currentId = parentAtomId + 1;
            List<Integer> parentIdList =expressionCollect.getContainerById(containerId).getExpressionList().get(expId).getExpressionAtomList().get(parentAtomId).getTypeIdList();
            String preStr = atomList.get(parentAtomId).getStr();
            String currentStr = atomList.get(currentId).getStr();
            assertStartsWith(preStr, currentStr);

            List<Integer> updatedBindIdList = filterBinds(containerId, expId, currentId, parentIdList);

            //if updatedBindIdList != oldBindIdList, then update bindids, update parentId
            //else break;
            Set<Integer> updatedBindSet = SequencecUtil.transformList(updatedBindIdList);
            Set<Integer> oldBindSet = SequencecUtil.transformList(expressionCollect.getContainerById(containerId).getExpressionList().get(expId).getExpressionAtomList().get(currentId).getBindIdList());
            if(!updatedBindSet.isEmpty() && !SequencecUtil.isSetEqual(oldBindSet, updatedBindSet)) {
                expressionCollect.getContainerById(containerId).getExpressionList().get(expId).getExpressionAtomList().get(currentId).setBindIdList(new ArrayList<>(updatedBindSet));
                expressionCollect.getContainerById(containerId).getExpressionList().get(expId).getExpressionAtomList().get(currentId).setResolvedManner("implicit_postpone");
                //System.out.println("postpone: str= " + currentStr + ", old=" + oldBindSet + ", new=" + updatedBindSet);
                //parentAtomId = currentId;
            }

        }

    }

    private List<Integer> filterBinds(int containerId, int expId, int currentId, List<Integer> parentIdList) {
        List<Integer> updatedBindIdList = new ArrayList<>();
        for (Integer bindId : expressionCollect.getContainerById(containerId).getExpressionList().get(expId).getExpressionAtomList().get(currentId).getBindIdList()) {
            if(bindId != -1 ) {
                int possibleParentId = singleCollect.getEntityById(bindId).getParentId();
                if(possibleParentId != -1 && parentIdList.indexOf(possibleParentId) != -1 ) {
                    //keep this one.
                    updatedBindIdList.add(bindId);
                }
            }
        }
        return updatedBindIdList;
    }


    private void assertStartsWith(String preStr, String currentStr) {
        if(!currentStr.startsWith(preStr)) {
            try {
                throw new Exception();
            } catch (Exception e) {
                System.out.println("Error: postpone " + preStr + ", " +  currentStr);
                exit(1);
            }

        }
    }


    private String getStr(int containerId, int expId, int atomId) {
        String str = Integer.toString(containerId) + "_" + Integer.toString(expId) + "_" + Integer.toString(atomId);
        return str;
    }

    private List<Integer> getIntegers(String str) {
        List<Integer> aList = new ArrayList<>();
        String[] tmp = str.split("_");
        for (String each : tmp) {
            aList.add(Integer.parseInt(each));
        }
        return aList;
    }
}
