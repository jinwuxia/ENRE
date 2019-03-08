package priextractor.py3extractor.newdeper.resolver;

import expression.Expression;
import expression.ExpressionAtom;
import priextractor.py3extractor.newdeper.SequencecUtil;
import uerr.SingleCollect;
import util.Configure;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class UnknownResolver implements Resolver{
    @Override
    public void resolve() {
        resolveUnknown();
    }

    /**
     * for atom which are in atomcount > 1.
     *
     */
    private void resolveUnknown() {
        SingleCollect singleCollect = SingleCollect.getSingleCollectInstance();
        for (int containerId = 0; containerId < expressionCollect.getCurrentIndex(); containerId++) {
            int entityId = expressionCollect.getContainerById(containerId).getParentId();

            for (int expId = 0; expId < expressionCollect.getContainerById(containerId).getExpressionList().size(); expId++) {
                Expression expression = expressionCollect.getContainerById(containerId).getExpressionList().get(expId);
                int atomCount = expression.getExpressionAtomList().size();
                if(atomCount == 1) {
                    continue;
                }

                resolveBuiltin(containerId, expId, atomCount);
                resolveLibrary(containerId, expId, atomCount);
                resolveSuper(containerId, expId, atomCount);

            }
        }
    }

    /**
     *  super.x, but x cannot found
     * x maybe defined implicitly.
     * @param containerId
     * @param expId
     * @param atomCount
     */
    private void resolveSuper(int containerId, int expId, int atomCount) {
        Expression expression = expressionCollect.getContainerById(containerId).getExpressionList().get(expId);
        for (int atomId = 0; atomId < atomCount; atomId++) {
            ExpressionAtom atom = expression.getExpressionAtomList().get(atomId);
            if (isUnknownResolve(atom, atomId, atomCount)) {
                String atomStr = atom.getStr();
                if((atomStr.startsWith(Configure.PRE_SUPER)
                        //|| atomStr.startsWith(PyConstantString.CLASS_METHOD_CLS_PARAMETER) || atomStr.startsWith(PyConstantString.SELF)
                        )
                        && atomStr.indexOf(".") == atomStr.lastIndexOf(".")
                        && atomStr.lastIndexOf(".") != -1) {
                    expressionCollect.getContainerById(containerId).getExpressionList().get(expId).getExpressionAtomList().get(atomId).setResolvedManner(Configure.RESOLVED_TYPE_LIBRARY);
                    //System.out.println("unknown library: " + atom);
                }
            }
        }
    }


    /**
     * from begin to end
     *
     * for a.b, if b is resolved as built-in, then a is built-in type.
     * @param containerId
     * @param expId
     * @param atomCount
     */
    private void resolveBuiltin(int containerId, int expId, int atomCount) {
        Expression expression = expressionCollect.getContainerById(containerId).getExpressionList().get(expId);
        //from to end, refer the object when obj.library()
        for (int atomId = 0; atomId <atomCount; atomId++) {
            ExpressionAtom atom = expression.getExpressionAtomList().get(atomId);
            if (isUnknownResolve(atom, atomId, atomCount)) {
                int nextId = atomId + 1;
                if (nextId < atomCount && isAtomBuiltin(nextId, expression.getExpressionAtomList())) {
                    expressionCollect.getContainerById(containerId).getExpressionList().get(expId).getExpressionAtomList().get(atomId).setResolvedManner(Configure.RESOLVED_TYPE_BUILTIN);
                    //System.out.println("unknown built-in: " + atom);
                }
            }
        }

    }





    private boolean isAtomBuiltin(int atomId, List<ExpressionAtom> atoms) {
        String manner = atoms.get(atomId).getResolvedManner();
        if(manner.equals(Configure.RESOLVED_TYPE_BUILTIN))  {
            return true;
        }
        return false;
    }




    /**
     * from begin to end
     *
     * a.b.sort() ,
     * for a, if cannot find b. then a type is library
     * for b, if cannot find sort, then b  type is library
     * for sort, if cannott find sort , then sort bind is library.
     * @param containerId
     * @param expId
     * @param atomCount
     */
    private void resolveLibrary(int containerId, int expId, int atomCount) {
        Expression expression = expressionCollect.getContainerById(containerId).getExpressionList().get(expId);
        for (int atomId = 0; atomId < atomCount; atomId++) {
            ExpressionAtom atom = expression.getExpressionAtomList().get(atomId);
            if (!isUnknownResolve(atom, atomId, atomCount)) {
                continue;
            }
            String atomStr = atom.getStr();
            List<Integer> entityIds = new ArrayList<>();
            if (atomId != atomCount -1) {
                String usage = ImpResolverOfAtom.getPureName(expression.getExpressionAtomList().get(atomId + 1).getStr());
                entityIds = ImpResolverOfAtom.inferEntity(usage);
            } else {//last
                String usage = ImpResolverOfAtom.getPureName(atomStr);
                entityIds = ImpResolverOfAtom.inferEntity(usage);
            }

            if (entityIds.isEmpty()) {
                expressionCollect.getContainerById(containerId).getExpressionList().get(expId).getExpressionAtomList().get(atomId).setResolvedManner(Configure.RESOLVED_TYPE_LIBRARY);
                //System.out.println("unknown library: " + atom);
            } else if(atomStr.startsWith(Configure.PRE_SUPER) && atomStr.indexOf(".") == atomStr.lastIndexOf(".") && atomStr.lastIndexOf(".") != -1) {
                expressionCollect.getContainerById(containerId).getExpressionList().get(expId).getExpressionAtomList().get(atomId).setResolvedManner(Configure.RESOLVED_TYPE_LIBRARY);
                //System.out.println("unknown library: " + atom);
            } else { //can be resolved
                if(atomId == atomCount -1) {
                    expressionCollect.getContainerById(containerId).getExpressionList().get(expId).getExpressionAtomList().get(atomId).setResolvedManner(Configure.RESOLVED_TYPE_IMPLICIT_POSSIBLE);
                    expressionCollect.getContainerById(containerId).getExpressionList().get(expId).getExpressionAtomList().get(atomId).setBindIdList(entityIds);
                    //System.out.println("unknown : " + atom);
                }
                else {
                    List<Integer> parentIds = singleCollect.findParentIds(entityIds);
                    if(!parentIds.isEmpty()) {
                        expressionCollect.getContainerById(containerId).getExpressionList().get(expId).getExpressionAtomList().get(atomId).setResolvedManner(Configure.RESOLVED_TYPE_IMPLICIT_POSSIBLE);
                        expressionCollect.getContainerById(containerId).getExpressionList().get(expId).getExpressionAtomList().get(atomId).setTypeIdList(parentIds);
                        //System.out.println("unknown : " + atom);
                    }
                }
            }

        }
    }


        /**
         *  if resolve_manner = "", unknown
         *  if resolve_manner = regualr/implicit, unknown
         *
         * @param atom
         * @param atomId
         * @param atomCount
         * @return
         */
    public static boolean isUnknownResolve(ExpressionAtom atom, int atomId, int atomCount) {
        //case 1
        if(atom.getResolvedManner().equals("")) {
            return true;
        }

        //case2
        String resolvedManner = atom.getResolvedManner();
        List<Integer> possibleTypeOrBinds;
        if(atomId == atomCount - 1) { //depend on entity
            possibleTypeOrBinds = atom.getBindIdList();
        }else {
            possibleTypeOrBinds = atom.getTypeIdList();
        }
        Set<Integer> possibleTypeOrBindSet = SequencecUtil.transformList(possibleTypeOrBinds);
        if((resolvedManner.equals(Configure.RESOLVED_TYPE_REGULAR)
                || resolvedManner.startsWith(Configure.RESOLVED_TYPE_IMPLICIT))
                && possibleTypeOrBindSet.size() == 0) {
            return true;
        }

        return false;
    }
}
