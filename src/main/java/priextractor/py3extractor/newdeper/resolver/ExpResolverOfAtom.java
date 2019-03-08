package priextractor.py3extractor.newdeper.resolver;

import entitybuilder.pybuilder.PyConstantString;
import expression.ExpressionAtom;
import util.Configure;
import util.StringUtil;

import java.util.List;
import java.util.Map;


/**
 * tranverse the atomlist for each entity,
 * resolve the bind entity id, type id, resolve manner, isresolved, then set to expression atoms.
 */
public class ExpResolverOfAtom implements Resolver {

    //private SingleCollect singleCollect = SingleCollect.getSingleCollectInstance();
    //private ExpressionCollect expressionCollect = ExpressionCollect.getExpressionCollect();
    //private NameSearch nameSearch = NameSearch.getNameSearchInstance();

    @Override
    public void resolve() {
        int containerSize = expressionCollect.getCurrentIndex();
        for (int containerId = 0; containerId < containerSize; containerId ++) {
            int parentEntityid = expressionCollect.getContainerById(containerId).getParentId();
            for (int atomId = 0; atomId < expressionCollect.getContainerById(containerId).getExpressionAtomList().size(); atomId++) {
                //System.out.println("expConID: " + Integer.toString(containerId) + ", atom: " + expressionCollect.getContainerById(containerId).getExpressionAtomList().get(atomId).getStr());

                resolveAtom(parentEntityid, containerId, atomId);
                //System.out.println(expressionCollect.getContainerById(containerId).getExpressionAtomList().get(atomId) + "\n");

            }
        }
    }

    /**
     *  resolve the bind entity id, type id, resolve manner, isresolved, then set to expression atoms.
     *
     * the resolve order (five cases) is important, cannot change.
     *  step1: if contains more than one dot, find the prefix, scopeID = typeID
     *         if scopeId = -1 && resolved as super/built-in, then resolved as super/built-in.
     *  step2: if scopeId != -1 find the remaining name in this scopeID. update typeID and bindId.
     * @param parentEntityId
     * @param expContainerId
     * @param expAtomId
     */
    private void resolveAtom(int parentEntityId, int expContainerId, int expAtomId) {
        ExpressionAtom tmpAtom = expressionCollect.getContainerById(expContainerId).getExpressionAtomList().get(expAtomId);
        String atomStr = tmpAtom.getStr();
        /**
         * if the atomStr has dot like x.y.z, then the prefix x.y must have been resolved before,
         * find the atom index of x.y.
         * change the x.y.z to z, and change the scopeid
         */
        int scopeId = parentEntityId;
        int prefixAtomIndex = -1;
        if(atomStr.contains(Configure.DOT)) {
            int endIndex = atomStr.lastIndexOf(Configure.DOT);
            String prefix = atomStr.substring(0, endIndex);
            atomStr = atomStr.substring(endIndex + 1, atomStr.length()); //update as the postfix
            prefixAtomIndex = searchAtomexpr(expContainerId, expAtomId - 1, prefix);
            if(prefixAtomIndex != -1) {
                ExpressionAtom prefixAtom = expressionCollect.getContainerById(expContainerId).getExpressionAtomList().get(prefixAtomIndex);
                if (prefixAtom.getResolved() && prefixAtom.getTypeIdList().size() == 1) {
                    scopeId = prefixAtom.getTypeIdList().get(0);
                }
                //else scopeId = -1, it maybe because it is an imported one(in library), or type is unknown.
            }
        }
        //resolve atomStr in scopeId
        tryFourCases(atomStr, scopeId, expContainerId, expAtomId, prefixAtomIndex);
    }


    private void tryFourCases(String atomStr, int scopeId, int expContainerId, int expAtomId, int prefixAtomIndex) {
        //resolve atomStr in scopeId
        //case 1
        ResolveResult result  = resolveAsSuper(atomStr);
        //case 2 regular
        if(!result.isResolved) {
            result = resolveAsRegular(scopeId, atomStr);
        }
        //case 3
        if(!result.isResolved) {
            result = resolveAsBuiltIn(prefixAtomIndex, atomStr);
        }
        //case 4
        if(!result.isResolved) {
            result = resolveAsLibrary(expContainerId, scopeId, prefixAtomIndex, atomStr);
        }
        //
        if(result.isResolved) {
            expressionCollect.getContainerById(expContainerId).getExpressionAtomList().get(expAtomId).updateAtom(result.isResolved, result.resolvedManner, result.bindId, result.typeId);
        }
    }




    /**
     * case 0: callee is super()
     * if super(),call parent.init()
     *      *      * if super().method1(),  and if parent are more than one,
     *      *      * we don't know which parent the super will refer to, until we see method1().
     *      *      * beacuse not every parent has method1() method member.
     * @param atomStr
     * @return
     */
    private  ResolveResult resolveAsSuper(String atomStr) {
        if(!atomStr.contains("(")) {
            return new ResolveResult("", false, -1, -1);
        }
        String destStr = atomStr.split("\\(")[0];
        if(destStr.equals(PyConstantString.SUPER)) {

            return new ResolveResult(Configure.RESOLVED_TYPE_SUPER, true, -1, -1);
        }
        return new ResolveResult("", false, -1, -1);
    }



    /**
     * built-in type or function
     * case 1: callee is built-in function, Exception(),
     * case 2: callee is otherType's method like String
     * @param atomStr
     * @return
     */
    private ResolveResult resolveAsBuiltIn(int preAtomIndex, String atomStr) {
        if(!atomStr.contains("(")) {
            return new ResolveResult("", false, -1, -1);
        }
        String str = atomStr.split("\\(")[0];

        //the orignal version of atomStr contains no dot. So atomStr is a function.
        if(preAtomIndex == -1) {
            if(StringUtil.isContained(str, PyConstantString.BUILT_IN_FUNCTIONS)
                    || StringUtil.isContained(str, PyConstantString.BUILT_IN_EXCEPTIONS)) {
                return new ResolveResult(Configure.RESOLVED_TYPE_BUILTIN, true, -1, -1);
            }
        }
        else { //the orginal version of atomStr has dot. So atomStr is a method
            if (StringUtil.isContained(str, PyConstantString.BUILT_IN_LIST_METHODS)
                || StringUtil.isContained(str, PyConstantString.BUILT_IN_DICT_METHODS)
                || StringUtil.isContained(str, PyConstantString.BUILT_IN_FILE_METHOD)
                || StringUtil.isContained(str, PyConstantString.BUILT_IN_SET_METHODS)
                || StringUtil.isContained(str, PyConstantString.BUILT_IN_STRING_METHODS)
                || StringUtil.isContained(str, PyConstantString.BUILT_IN_TUPLE_METHODS)) {
                return new ResolveResult(Configure.RESOLVED_TYPE_BUILTIN, true, -1, -1);
            }
        }

        return new ResolveResult("", false, -1, -1);
    }



    /**
     * case3:
     * 1)if preAtomIndex = -1, and  atomStr not in namespace of scopeId
     * then the atomStr is imported python library.
     *
     * 1)if preAtomIndex != -1, preAtom is resolved As library,
     * then the atomStr is imported.atomStr or imported.atom()
     *
     * @param scopeId
     * @param preAtomIndex
     * @param atomStr
     * @return
     */
    private ResolveResult resolveAsLibrary(int containerId, int scopeId,  int preAtomIndex, String atomStr) {
        if(preAtomIndex == -1) {
            Map<String, Integer> mapreuslt = nameSearch.getNameMapOfScope(scopeId);
            if (mapreuslt != null && !mapreuslt.containsKey(atomStr)) {
                return new ResolveResult(Configure.RESOLVED_TYPE_LIBRARY, true, -1, -1);
            }
        }
        else {
            ExpressionAtom preAtom = expressionCollect.getContainerById(containerId).getExpressionAtomList().get(preAtomIndex);
            if(preAtom.getResolvedManner().equals(Configure.RESOLVED_TYPE_LIBRARY)) {
                return new ResolveResult(Configure.RESOLVED_TYPE_LIBRARY, true, -1, -1);
            }
        }
        return new ResolveResult("", false, -1, -1);
    }


    /**
     * according to namescope to search
     * note: the var and method()/function() 's typeId/scopeId != bindId.
     * @param scopeId
     * @param atomStr
     * @return
     */
    private ResolveResult resolveAsRegular(int scopeId, String atomStr) {
        if (atomStr.contains("(")) {
            atomStr = atomStr.split("\\(")[0];
        }

        if (scopeId != -1 ) {
            //when scope is if-first level, the namespace is null,so the bindId can be -1
            int bindId = nameSearch.getIdByNameInScope(atomStr, scopeId);
            //System.out.println(singleCollect.getEntityById(scopeId).getName() + "," + nameSearch.getNameMapOfScope(scopeId));
            int typeId = bindId;

            if(bindId != -1) {
                typeId = singleCollect.getTypeId(bindId);
                return new ResolveResult(Configure.RESOLVED_TYPE_REGULAR,true, bindId, typeId);
            }
            else if (nameSearch.isNameInScope(atomStr, scopeId)) {
                return new ResolveResult(Configure.RESOLVED_TYPE_LIBRARY,true, bindId, typeId);
            }
        }
        return new ResolveResult("", false, -1, -1);
    }

    /**
     * in the atomlist of a container, from the location backwards, find the atomStr's index in atomList
     * @param expContainerId
     * @param location
     * @param atomStr
     * @return
     */
    private int searchAtomexpr(int expContainerId, int location, String atomStr) {
        List<ExpressionAtom> atomList = expressionCollect.getContainerById(expContainerId).getExpressionAtomList();
        int index = location;
        while(index >= 0) {
            String str = atomList.get(index).getStr();
            if(atomStr.equals(str)) {
                return index;
            }
            index--;

        }
        return -1;
    }

}
