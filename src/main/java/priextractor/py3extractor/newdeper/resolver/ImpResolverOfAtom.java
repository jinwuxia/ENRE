package priextractor.py3extractor.newdeper.resolver;


import expression.Expression;
import expression.ExpressionAtom;
import util.Configure;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ImpResolverOfAtom implements Resolver {

    private static ImpResolverSetup impResolverSetup;

    public ImpResolverOfAtom(ImpResolverSetup impResolverSetup){
        this.impResolverSetup = impResolverSetup;
    }


    @Override
    public void resolve() {

        //first resolve implicit_possible cases
        possibleResolve();

    }


    private void possibleResolve() {
        for (int containerId = 0; containerId < expressionCollect.getCurrentIndex(); containerId++) {
            List<Expression> expList = expressionCollect.getContainerById(containerId).getExpressionList();
            for (int expId = 0; expId < expList.size(); expId++) {
                if (!isConsideredExp(containerId, expId)) {
                    continue;
                }
                possibleResolveForExpression(containerId, expId);
            }
        }

    }


    /**
     * resolve for one expression
     *
     */
    private void possibleResolveForExpression(int containerId, int expId) {
        Expression exp = expressionCollect.getContainerById(containerId).getExpressionList().get(expId);
        //resolve the expression
        int len = exp.getExpressionAtomList().size();
        int atomId = len - 1;
        while(atomId >= 0) {
            ExpressionAtom tmpAtom = exp.getExpressionAtomList().get(atomId);
            //System.out.println("possible: before atom= " + tmpAtom );
            if(!isConsideredAtom(tmpAtom)) {
                atomId--;
                continue;
            }
            String atomStr = tmpAtom.getStr();
            if(atomId > 0 && !isValid(atomStr,  exp.getExpressionAtomList().get(atomId - 1).getStr())) {
                System.out.println("ERROR, " + "last: " + atomStr + ", former: " + exp.getExpressionAtomList().get(atomId - 1).getStr());
                //exit(1);
                atomId--;
                continue;

            }

            List<Integer> bindIdList; //it may be [-1]
            List<Integer> typeIdList;
            boolean isInferEntity = false;
            boolean isInferType = false;

            //atom is not resolved && has dot (e.g., non-root node)
            //if(!tmpAtom.getResolved() && atomStr.contains(".") ) {
            if(!tmpAtom.getResolved()) { // if not resolved, it must have dot.
                bindIdList = inferEntity(getPureName(atomStr)); //it may be [-1]
                typeIdList = singleCollect.findTypeIds(bindIdList);
                if (bindIdList.size() != 0) {
                    isInferEntity = true;
                }
            }
            else {
                bindIdList = tmpAtom.getBindIdList();
                typeIdList = tmpAtom.getTypeIdList();
            }
            //infer type if it is not leaf
            if((typeIdList.size() == 0 || typeIdList.get(0) == -1) && atomId != len - 1) {
                ExpressionAtom childAtom = expressionCollect.getContainerById(containerId).getExpressionList().get(expId).getExpressionAtomList().get(atomId + 1);
                typeIdList= inferType(childAtom.getBindIdList());
                if(typeIdList.size()!=0 && typeIdList.get(0) != -1) {
                    isInferType = true;
                }
            }

            if(isInferEntity || isInferType) {
                saveToAtom(containerId, expId, atomId, bindIdList, typeIdList);
            }
            //System.out.println("possible: after atom= " + expressionCollect.getContainerById(containerId).getExpressionList().get(expId).getExpressionAtomList().get(atomId));
            //System.out.println("\n");
            atomId --;
        }//end while


    }

    /**
     * judge whether an expression will be resolved in implicit way
     *
     * the object expression: for the atom expression that meets the both:
     *  1)x, has been resolved as regular one.
     *  2)x.y/x.y.z or x.z()/x.z().a() form and  not resolved.
     * @return
     */
    private boolean isConsideredExp(int containerId, int expId) {
        List<ExpressionAtom> atoms = expressionCollect.getContainerById(containerId).getExpressionList().get(expId).getExpressionAtomList();
        if(atoms.size() < 2) {
            return false;
        }
        if (atoms.get(0).getResolvedManner().equals(Configure.RESOLVED_TYPE_REGULAR)) {
            for (int i = 1; i < atoms.size(); i++) {
                if(!atoms.get(i).getResolved()) {
                    return true;
                }
            }
        }
        return false;
    }


    private boolean isConsideredAtom(ExpressionAtom atom) {
        if(atom.getResolvedManner().equals(Configure.RESOLVED_TYPE_SUPER)
                || atom.getResolvedManner().equals(Configure.RESOLVED_TYPE_BUILTIN)
                || atom.getResolvedManner().equals(Configure.RESOLVED_TYPE_LIBRARY)) {
            return false;
        }
        return true;
    }
    /**
     * str = x.y.z or x.y.z(), return z or z()
     * @param str
     * @return
     */
    public static String getPureName(String str) {
        String[] tmp = str.split("\\.");
        return tmp[tmp.length - 1];
    }


    /**
     * x.y.z 's prefix should be equal to formerStr (atom precedent)
     * @param lastStr
     * @param formerStr
     * @return
     */
    private boolean isValid(String lastStr, String formerStr) {
        int index = lastStr.lastIndexOf(".");
        if(index == -1) {
            return false;
        }
        if(lastStr.substring(0, index).equals(formerStr)) {
            return true;
        }
        return false;
    }



    public static List<Integer> inferEntity(String pureName) {
        List<Integer> bindIdList;
        if (pureName.contains("(")) { //purename is m ( "()" is deleted from m )
            pureName = pureName.split("\\(")[0];
            bindIdList = impResolverSetup.searchIdsByName(pureName, Configure.BASIC_ENTITY_FUNCTION);
        } else {
            bindIdList = impResolverSetup.searchIdsByName(pureName, Configure.BASIC_ENTITY_VARIABLE);
        }
        return bindIdList;
    }


    private List<Integer> inferType(List<Integer> bindListOfChildNode ) {
        List<Integer> typeIdList = new ArrayList<>();
        //for x.y or y : refer y based on its child atoms x.y.z or y.z
        if(bindListOfChildNode.size() != 0 && bindListOfChildNode.get(0) != -1) {
            typeIdList = singleCollect.findParentIds(bindListOfChildNode); //it found out type
        }
        return typeIdList;
    }


    /**
     * if both are not empty , and intersection is not empty, return intersection
     *   else return union section.
     * @param listA
     * @param listB
     * @return
     */
    private List<Integer> decideOnSet(List<Integer> listA, List<Integer> listB) {
        Set<Integer> intersection = new HashSet<>(listA);
        Set<Integer> union = new HashSet<>(listA);
        intersection.retainAll(new HashSet<>(listB));
        union.addAll(new HashSet<>(listB));

        if(!listA.isEmpty() && !listB.isEmpty() && !intersection.isEmpty()) {
            return new ArrayList<>(intersection);
        }
        else {
            return new ArrayList<>(union);
        }
    }


    private void saveToAtom(int containerId, int expId, int atomId, List<Integer> bindIdList, List<Integer> typeIdList) {
        expressionCollect.getContainerById(containerId).getExpressionList().get(expId).getExpressionAtomList().get(atomId).updateAtomBySet(true, Configure.RESOLVED_TYPE_IMPLICIT_POSSIBLE, bindIdList, typeIdList);
    }




}
