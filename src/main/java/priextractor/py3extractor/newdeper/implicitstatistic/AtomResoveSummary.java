package priextractor.py3extractor.newdeper.implicitstatistic;

import expression.Expression;
import expression.ExpressionAtom;
import expression.ExpressionCollect;
import priextractor.py3extractor.newdeper.InferenceDependencyVisitor;
import priextractor.py3extractor.newdeper.SequencecUtil;
import priextractor.py3extractor.newdeper.UnknownResolver;
import uerr.SingleCollect;
import util.Configure;

import java.util.*;

public class AtomResoveSummary {

    // Statistic the all types: unknown, built-in, super, library, regular, refine, extension, abstractions, postpone.
    // output P1-P10
    private Map<String, Integer> resovledTypeMap = new HashMap<>();

    //Statistic the possible_X types: refine, extension, abstractions, postpone, output P1-P10
    private Map<String, Integer> possibleResolvedTypeMap = new HashMap<>();


    //Statistic the types (i.e., others) except for builtin, super, library.
    // that is regular, refine, extension, abstractions, postpone,
    // output P1-P10
    private Map<String, Integer> otherResolvedTypeMap = new HashMap<>();


    private ExpressionCollect expressionCollect = ExpressionCollect.getExpressionCollect();




    public void doSummary() {
        for (int containerId = 0; containerId < expressionCollect.getCurrentIndex(); containerId ++) {
            List<Expression> expressionList = expressionCollect.getContainerById(containerId).getExpressionList();
            for (int expId = 0; expId < expressionList.size(); expId++) {

                summaryForTotalMap(expressionList.get(expId)); //resovledTypeMap
                summaryExceptThree(expressionList.get(expId)); //otherResolvedTypeMap
            }
        }


        //output
        Map<String, Integer> resMap = new HashMap<>();
        for (Map.Entry<String, Integer> entry : resovledTypeMap.entrySet()) {
            String manner = entry.getKey();
            int value = entry.getValue();
            if(manner.startsWith(Configure.RESOLVED_TYPE_IMPLICIT)) {
                if(!manner.equals(Configure.RESOLVED_TYPE_IMPLICIT_POSSIBLE)) {
                    manner = Configure.RESOLVED_TYPE_IMPLICIT_REFINE;
                }
            }
            if (!resMap.containsKey(manner)) {
                resMap.put(manner,0);
            }
            resMap.put(manner, resMap.get(manner) + value);
        }
        System.out.println("resovledTypeMap: " + resMap);

        System.out.println("otherCount: " + otherResolvedTypeMap);
    }

    /**
     * print unknown detail
     * unknown entity detail
     */
    public void summaryUnknown() {
        System.out.println("\nunknown dependencies....");
        SingleCollect singleCollect = SingleCollect.getSingleCollectInstance();
        for (int containerId = 0; containerId < expressionCollect.getCurrentIndex(); containerId++) {
            int entityId = expressionCollect.getContainerById(containerId).getParentId();
            String entityName = singleCollect.getLongName(entityId);

            List<Expression> expressionList = expressionCollect.getContainerById(containerId).getExpressionList();
            for (int expId = 0; expId < expressionList.size(); expId++) {
                Expression expression = expressionList.get(expId);
                int atomCount = expression.getExpressionAtomList().size();
                for (int atomId = 0; atomId < atomCount; atomId++) {
                    ExpressionAtom atom = expression.getExpressionAtomList().get(atomId);
                    if (UnknownResolver.isUnknownResolve(atom, atomId, atomCount)) {
                        System.out.println("entity name: " + entityName + "; atom: " + atom);
                    }
                }
            }
        }
    }


    public void summarizeFinalPossibeOnes() {
        for (int containerId = 0; containerId < expressionCollect.getCurrentIndex(); containerId ++) {
            List<Expression> expressionList = expressionCollect.getContainerById(containerId).getExpressionList();
            for (int expId = 0; expId < expressionList.size(); expId++) {
                summaryForPossible(expressionList.get(expId));
            }
        }
        System.out.println("final possible: " + possibleResolvedTypeMap);
    }

    /**
     * Statistic the possible_X: refine, extension, abstractions, postpone, output P1-P10
     *
     * in the possible_xx resolved type,
     *          if itslocated atomindex = atomsize -1, look at its entity
     *          else: look at its type
     *
     * @param expression
     */
    private void summaryForPossible(Expression expression) {
        int atomCount = expression.getExpressionAtomList().size();

        for (int atomId = 0; atomId < atomCount; atomId ++) {
            ExpressionAtom atom = expression.getExpressionAtomList().get(atomId);
            String resolveManner = atom.getResolvedManner();
            if(resolveManner.startsWith(Configure.RESOLVED_TYPE_IMPLICIT)) {
                String possible_count_string = InferenceDependencyVisitor.getProbabilityType(atomId, atomCount, atom);
                if(!possibleResolvedTypeMap.containsKey(possible_count_string)) {
                    possibleResolvedTypeMap.put(possible_count_string, 0);
                }
                int old = possibleResolvedTypeMap.get(possible_count_string);
                possibleResolvedTypeMap.put(possible_count_string, old + 1);
            }

        }
    }



    /**
     * except the built-in, super, library,
     * statistic the P0-P10
     * @param expression
     */
    private void summaryExceptThree(Expression expression) {
        int atomCount = expression.getExpressionAtomList().size();

        for (int atomId = 0; atomId < atomCount; atomId ++) {
            ExpressionAtom atom = expression.getExpressionAtomList().get(atomId);
            String resolveManner = atom.getResolvedManner();
            if(!resolveManner.equals(Configure.RESOLVED_TYPE_BUILTIN)
                    && !resolveManner.equals(Configure.RESOLVED_TYPE_LIBRARY)
                    && !resolveManner.equals(Configure.RESOLVED_TYPE_SUPER)
            ) {
                String possible_count_string = InferenceDependencyVisitor.getProbabilityType(atomId, atomCount, atom);
                if(!otherResolvedTypeMap.containsKey(possible_count_string)) {
                    otherResolvedTypeMap.put(possible_count_string, 0);
                }
                int old = otherResolvedTypeMap.get(possible_count_string);
                otherResolvedTypeMap.put(possible_count_string, old + 1);
            }

        }
    }



    /**
     *
     *
     * update resovledTypeMap
     * @param
     */
    private void summaryForTotalMap(Expression expression) {
        int atomCount = expression.getExpressionAtomList().size();

        for (int atomId = 0; atomId < atomCount; atomId ++) {
            ExpressionAtom atom = expression.getExpressionAtomList().get(atomId);
            String resolvedManner = atom.getResolvedManner();

            if(UnknownResolver.isUnknownResolve(atom, atomId, atomCount)) {
                resolvedManner = Configure.RESOLVED_TYPE_UNKNOWN;
            }

            if (!resovledTypeMap.containsKey(resolvedManner)) {
                resovledTypeMap.put(resolvedManner, 0);
            }
            int old = resovledTypeMap.get(resolvedManner);
            resovledTypeMap.put(resolvedManner, old + 1);
        }
    }





    private ArrayList<String[]> toList(Map<String, Integer> aMap) {
        ArrayList<String[]> aList = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : aMap.entrySet()) {
            String manner = entry.getKey();
            String count = Integer.toString(entry.getValue());
            aList.add(new String[]{manner, count});
        }
        return aList;
    }


}
