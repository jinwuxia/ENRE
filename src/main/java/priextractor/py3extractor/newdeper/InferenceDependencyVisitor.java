package priextractor.py3extractor.newdeper;

import expression.Expression;
import expression.ExpressionAtom;
import priextractor.py3extractor.DepVisitor;
import uerr.AbsEntity;
import util.Configure;

import java.util.List;
import java.util.Set;

public class InferenceDependencyVisitor extends DepVisitor {

    /**
     * no use
     * atom usage = EXPRESSION_CALL, XX_SET, XX_DOT, XX_USE
     */
    @Override
    public void setDep() {
        for (AbsEntity entity : singleCollect.getEntities()) {
            int entiyId = entity.getId();

            int containerId = entity.getExpContainerId();
            if (containerId == -1) {
                continue;
            }

            List<Expression> expressionsList = expressionCollect.getContainerById(containerId).getExpressionList();
            for (Expression expression : expressionsList) {
                int atomCount = expression.getExpressionAtomList().size();

                for (int atomId = 0; atomId < atomCount; atomId++) {
                    ExpressionAtom atom = expression.getExpressionAtomList().get(atomId);
                    String resolveManner = atom.getResolvedManner();
                    if (resolveManner.startsWith(Configure.RESOLVED_TYPE_IMPLICIT)) {
                        List<Integer> possibleTypeOrBinds;
                        if (atomId == atomCount - 1) { //depend on entity
                            possibleTypeOrBinds = atom.getBindIdList();
                        } else {
                            possibleTypeOrBinds = atom.getTypeIdList();
                        }
                        String dependencyType = Configure.RELATION_IMPLICIT_ALL + "_" + atom.getUsageType();
                        for (Integer id : possibleTypeOrBinds) {
                            if(id != -1) {
                                saveRelation(entiyId, id, dependencyType, Configure.RELATION_IMPLICIT_ALLED + "_" + atom.getUsageType());
                            }
                        }
                    }
                }
            }
        }
    }


    /**
     * store all source-code inner dependencies into atomDependCollect: exp, P1,P2,..., P10, P>10
     */
    public void setDepByCategory() {
        for (AbsEntity entity : singleCollect.getEntities()) {
            int entiyId = entity.getId();
            int containerId = entity.getExpContainerId();
            if (containerId == -1) {
                continue;
            }
            List<Expression> expressionsList = expressionCollect.getContainerById(containerId).getExpressionList();
            for (Expression expression : expressionsList) {
                int atomCount = expression.getExpressionAtomList().size();
                for (int atomId = 0; atomId < atomCount; atomId++) {
                    ExpressionAtom atom = expression.getExpressionAtomList().get(atomId);
                    String resolveManner = atom.getResolvedManner();
                    if(!resolveManner.equals(Configure.RESOLVED_TYPE_BUILTIN)
                            && !resolveManner.equals(Configure.RESOLVED_TYPE_LIBRARY)
                            && !resolveManner.equals(Configure.RESOLVED_TYPE_SUPER)
                    ) {
                        List<Integer> possibleTypeOrBinds;
                        if(atomId == atomCount - 1) { //depend on entity
                            possibleTypeOrBinds = atom.getBindIdList();
                        }else {
                            possibleTypeOrBinds = atom.getTypeIdList();
                        }
                        Set<Integer> possibleTypeOrBindSet = SequencecUtil.transformList(possibleTypeOrBinds);
                        if(possibleTypeOrBindSet.isEmpty()) {
                            continue;
                        }
                        String type = getProbabilityType(resolveManner, atomId, atomCount, atom);
                        for (Integer id2 : possibleTypeOrBindSet) {
                            atomDependCollect.addOnedep(type, entiyId, id2);
                        }
                    }
                }
            }
        }
    }


    /**
     * return exp, p1,2,3,4,5,6,7,8,9,10,>10
     * @return
     */
    public static String getProbabilityType(String resolvedManner, int atomId, int atomCount, ExpressionAtom atom) {
        String possible_count_string;
        List<Integer> possibleTypeOrBinds;

        if(atomId == atomCount - 1) { //depend on entity
            possibleTypeOrBinds = atom.getBindIdList();
        }else {
            possibleTypeOrBinds = atom.getTypeIdList();
        }
        Set<Integer> possibleTypeOrBindSet = SequencecUtil.transformList(possibleTypeOrBinds);
        possible_count_string = constructTypeP(resolvedManner, possibleTypeOrBindSet.size());
        return possible_count_string;

    }


    /**
     * classfoy src-code atom dependency into exp, and,  implicit(p0, p1, p2, p3,...)
     * @param resolvedManner
     * @param p
     * @return
     */
    private static String constructTypeP(String resolvedManner, int p) {
        String possible_count_string;
        if(resolvedManner.equals(Configure.RESOLVED_TYPE_REGULAR) && p == 1) {
            return Configure.RELATION_ATOM_EXPLICIT;

        }
        switch (p) {
            case 0:
                possible_count_string = Configure.RELATION_ATOM_IMPLICIT_P; //impossible
                break;
            case 1:
                possible_count_string = Configure.RELATION_ATOM_IMPLICIT_P1;
                break;
            case 2:
                possible_count_string = Configure.RELATION_ATOM_IMPLICIT_P2;
                break;
            case 3:
                possible_count_string = Configure.RELATION_ATOM_IMPLICIT_P3;
                break;
            case 4:
                possible_count_string = Configure.RELATION_ATOM_IMPLICIT_P4;
                break;
            case 5:
                possible_count_string = Configure.RELATION_ATOM_IMPLICIT_P5;
                break;
            case 6:
                possible_count_string = Configure.RELATION_ATOM_IMPLICIT_P6;
                break;
            case 7:
                possible_count_string = Configure.RELATION_ATOM_IMPLICIT_P7;
                break;
            case 8:
                possible_count_string = Configure.RELATION_ATOM_IMPLICIT_P8;
                break;
            case 9:
                possible_count_string = Configure.RELATION_ATOM_IMPLICIT_P9;
                break;
            case 10:
                possible_count_string = Configure.RELATION_ATOM_IMPLICIT_P10;
                break;
            default: //>10
                possible_count_string = Configure.RELATION_ATOM_IMPLICIT_P11;
                break;
        }
        return possible_count_string;
    }


}
