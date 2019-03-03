package priextractor.py3extractor.newdeper.implicitstatistic;

import expression.Expression;
import expression.ExpressionAtom;
import expression.ExpressionCollect;
import priextractor.py3extractor.newdeper.InferenceDependencyVisitor;
import priextractor.py3extractor.newdeper.UnknownResolver;
import sun.security.krb5.Config;
import uerr.SingleCollect;
import util.Configure;
import writer.CsvWriter;

import java.util.*;

public class AtomResoveSummary {

    // Statistic the all types: unknown, built-in, library(super + library), regular, implicit(refine+extension+abstractions+postpone).
    private Map<String, Integer> resovledTypeMap = new HashMap<>();

    //Statistic the types (i.e., others) except for builtin, super, library.
    // output P1-P10
    private Map<String, Integer> categoryByPInSrc = new HashMap<>();


    private ExpressionCollect expressionCollect = ExpressionCollect.getExpressionCollect();

    //Statistic the possible_X types: refine, extension, abstractions, postpone, output P1-P10
    private Map<String, Integer> possibleResolvedTypeMap = new HashMap<>();


    public AtomResoveSummary() {
        for (int containerId = 0; containerId < expressionCollect.getCurrentIndex(); containerId ++) {
            List<Expression> expressionList = expressionCollect.getContainerById(containerId).getExpressionList();
            for (int expId = 0; expId < expressionList.size(); expId++) {
                //for all atoms, sum up the resolved map.
                summaryForTotalMap(expressionList.get(expId)); //resovledTypeMap

                //for only project code, except for built-in, super, library,
                // sum up the src code resolved type in P1-P10, P>10
                summaryPcategoryInSrc(expressionList.get(expId)); //categoryByPInSrc
            }
        }
    }

    /**
     *
     */
    public void doSummary() {
        List<String[]> categorySrcByP = outputCategoryP();
        List<String[]> resolvedMannersForAll = outputAllByManners();
        Configure configure = Configure.getConfigureInstance();
        String path = configure.getAnalyzedProjectName();
        CsvWriter csvWriter = new CsvWriter();

        csvWriter.writeCsv(resolvedMannersForAll, path + "_allAtomManners.csv");
        csvWriter.writeCsv(categorySrcByP, path + "_onlySrcPcategories.csv");
    }


    /**
     * builtin, library(library+super), explicit(regular), implicit(implicit_)
     * resovledTypeMap
     * @return
     */
    private  List<String[]> outputAllByManners() {
        String[] types  = {Configure.RESOLVED_TYPE_BUILTIN, Configure.RESOLVED_TYPE_LIBRARY,
                Configure.RESOLVED_TYPE_REGULAR, Configure.RESOLVED_TYPE_IMPLICIT, Configure.RESOLVED_TYPE_UNKNOWN};

        int[] values = new int[types.length];
        for (Map.Entry<String, Integer> entry : resovledTypeMap.entrySet()) {
            String key = entry.getKey();
            int value = entry.getValue();
            if (key.equals(types[0])) {
                values[0] += value;
            }
            else if (key.equals(types[1]) || key.equals(Configure.RESOLVED_TYPE_SUPER))  {
                values[1] += value;
            }
            else if (key.equals(types[2])) {
                values[2] += value;
            }
            else if (key.startsWith(types[3])) {
                values[3] += value;
            }
            else if(key.equals(types[4])) {
                values[4] += value;
            }
        }
        String[] valueStrings = new String[values.length];
        for(int i = 0; i < valueStrings.length; i++) {
            valueStrings[i] = Integer.toString(values[i]);
        }
        ArrayList<String[]> aList = new ArrayList<>();
        aList.add(types);
        aList.add(valueStrings);
        return aList;
    }


    /**
     * categoryByPInSrc
     */
    private List<String[]> outputCategoryP() {
        String types[] = {Configure.RELATION_IMPLICIT_P1,
        Configure.RELATION_IMPLICIT_P2,
        Configure.RELATION_IMPLICIT_P3,
        Configure.RELATION_IMPLICIT_P4,
        Configure.RELATION_IMPLICIT_P5,
        Configure.RELATION_IMPLICIT_P6,
        Configure.RELATION_IMPLICIT_P7,
        Configure.RELATION_IMPLICIT_P8,
        Configure.RELATION_IMPLICIT_P9,
        Configure.RELATION_IMPLICIT_P10,
        Configure.RELATION_IMPLICIT_P11};

        List<String> valueList = new ArrayList<>();
        for (String type : types) {
            if(categoryByPInSrc.containsKey(type)) {
                int value = categoryByPInSrc.get(type);
                valueList.add(Integer.toString(value));
            }else {
                valueList.add("0");
            }
        }
        String[] values = valueList.toArray(new String[0]);

        ArrayList<String[]> aList = new ArrayList<>();
        aList.add(types);
        aList.add(values);
        return aList;
    }


    /**
     * except the built-in, super, library,
     * statistic the P0-P10
     * @param expression
     */
    private void summaryPcategoryInSrc(Expression expression) {
        int atomCount = expression.getExpressionAtomList().size();

        for (int atomId = 0; atomId < atomCount; atomId ++) {
            ExpressionAtom atom = expression.getExpressionAtomList().get(atomId);
            String resolveManner = atom.getResolvedManner();
            if(!resolveManner.equals(Configure.RESOLVED_TYPE_BUILTIN)
                    && !resolveManner.equals(Configure.RESOLVED_TYPE_LIBRARY)
                    && !resolveManner.equals(Configure.RESOLVED_TYPE_SUPER)
            ) {
                String possible_count_string = InferenceDependencyVisitor.getProbabilityType(atomId, atomCount, atom);
                if(!categoryByPInSrc.containsKey(possible_count_string)) {
                    categoryByPInSrc.put(possible_count_string, 0);
                }
                int old = categoryByPInSrc.get(possible_count_string);
                categoryByPInSrc.put(possible_count_string, old + 1);
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





}
