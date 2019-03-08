package priextractor.py3extractor.newdeper.resolver;

import expression.Expression;
import expression.ExpressionAtom;
import priextractor.py3extractor.newdeper.SequencecUtil;
import uerr.AbsCLSEntity;
import uerr.AbsEntity;
import util.Configure;
import util.Tuple;

import java.util.*;

public class ResolverOfAbstraction implements Resolver{

    private ResolverPostponed resolverPostponed = new ResolverPostponed();


    /**
     * map{baseID, childID_list}
     */
    private Map<Integer, List<Integer>> inheritMap = new HashMap<>();

    /**
     * map{old typeID set} = new_typeId_set
     */
    private Map<String, Set<Integer>> tmpUpdateMap = new HashMap<>();

    public ResolverOfAbstraction() {
        groupClassByParent();
    }



    /**
     * group class based on its parent class.
     * map[parentId] = childIdList
     *
     * set inheritMap
     */
    private void groupClassByParent() {
        for(AbsEntity entity :  singleCollect.getEntities()) {
            int entityId = entity.getId();

            if( !(entity instanceof AbsCLSEntity)) {
                continue;
            }

            for(Tuple<String, Integer> relation :  entity.getRelations()) {
                if(!(relation.x.equals(Configure.RELATION_INHERIT))) {
                    continue;
                }
                int baseId = relation.y;
                if (!inheritMap.containsKey(baseId)) {
                    inheritMap.put(baseId, new ArrayList<>());
                }
                inheritMap.get(baseId).add(entityId);
            }
        }

        /*
        if (inheritMap.isEmpty()) {
            System.out.println("inheritMap is empty");
        }
        for (Map.Entry<Integer, List<Integer>> entry : inheritMap.entrySet()) {
            int baseId = entry.getKey();
            String baseName = singleCollect.getEntityById(baseId).getName();
            System.out.println("baseName: " + baseName);
            for (int childId : entry.getValue()) {
                String childName = singleCollect.getEntityById(childId).getName();
                System.out.println("childName: " + childName);
            }
        }
        */

    }


    /**
     * for atoms  if its typeList >2,
     * then try to depend on abstractions.
     */
    @Override
    public void resolve() {
        int containerCount = expressionCollect.getCurrentIndex();
        for (int containerId = 0; containerId < containerCount; containerId++)
        {
            List<Expression> expressionList = expressionCollect.getContainerById(containerId).getExpressionList();
            int expressionCount = expressionList.size();
            for (int expId = 0; expId < expressionCount; expId++) {
                List<ExpressionAtom> atoms = expressionList.get(expId).getExpressionAtomList();
                for(int atomId = 0; atomId < atoms.size(); atomId++) {
                   Set<Integer> typeSet = SequencecUtil.transformList(atoms.get(atomId).getTypeIdList());
                   if(typeSet.size() < 2) {
                       continue;
                   }
                   String key = SequencecUtil.toOrderedString(typeSet);
                   Set<Integer> updatedTypeSet =  inferAbstraction(typeSet, key);
                   if(!updatedTypeSet.isEmpty() && !SequencecUtil.isSetEqual(typeSet, updatedTypeSet)) {
                       saveToAtom(containerId, expId, atomId, updatedTypeSet);

                       tmpUpdateMap.put(key, updatedTypeSet);
                       //System.out.println("abstract:  oldtype: " + typeSet + ", newtype: " + updatedTypeSet );
                       //the atom next the atomId
                       resolverPostponed.updateNextAtom(containerId,expId,atomId);

                   }

                }
            }
        }
    }


    /**
     * if it has been inferred before, then
     * @param typeSet
     * @return
     */
    private Set<Integer> inferAbstraction(Set<Integer> typeSet, String str) {
        if(tmpUpdateMap.containsKey(str)) {
            return tmpUpdateMap.get(str);
        }

        //produce deletedList
        Map<Integer, Boolean> deletedMap = new HashMap<>(); // store the typeId should be deleted
        for (Integer parentId: typeSet) {
            if(deletedMap.containsKey(parentId)) {
                continue;
            }
            for (Integer childId :  typeSet) {
                if(parentId.equals(childId) || deletedMap.containsKey(childId)) {
                    continue;
                }
                //starts the inferences
                if(inheritMap.containsKey(parentId)) {
                    if (inheritMap.get(parentId).indexOf(childId) != -1) {
                        deletedMap.put(childId, true);
                    }
                }
            }
        }

        //save the un-deleted typeIds to set.
        Set<Integer> res = new HashSet<>();
        for (Integer typeId :  typeSet) {
            if (!deletedMap.containsKey(typeId)) {
                res.add(typeId);
            }
        }
        return res;
    }





    /**
     * save the abstraction resolution in atom.
     * @param containerId
     * @param expId
     * @param atomId
     * @param newSet
     */
    private void saveToAtom(int containerId, int expId, int atomId, Set<Integer> newSet) {
        expressionCollect.getContainerById(containerId).getExpressionList().get(expId).getExpressionAtomList().get(atomId).setTypeIdList(new ArrayList<>(newSet));
        expressionCollect.getContainerById(containerId).getExpressionList().get(expId).getExpressionAtomList().get(atomId).setResolvedManner(Configure.RESOLVED_TYPE_IMPLICIT_ABSTRACT);
    }
}
