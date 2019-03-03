package priextractor.py3extractor.newdeper;

import expression.Expression;
import expression.ExpressionAtom;
import util.Configure;

import java.util.*;

public class ResolverOfRefinement implements Resolver {
    private ResolverPostponed resolverPostponed = new ResolverPostponed();

    //produce map{containerID}{"function"}{"pure method/variable name"} = [wrapper1, wrapper2, ... ]
    private Map<Integer, HashMap<String, HashMap<String, List<ResultWrapper>>>>  atomMap = new HashMap<>();

    //map{"method"}{"pure method/variable name"} = [set1, set2, ...]
    //Map<String, HashMap<String, Set<Integer>>> name2AtomIndexMap = new HashMap<>();

    /**
     * first, init this.atomMap:  map{containerID}{type}{name} =[wrapper(expId, atomId), wrapper(expId, atomId), ....]
     */

    @Override
    public void resolve() {
        //first, group atoms in the container
        groupAtomsByName();

        //printMap();

        //infer process
        inferProcess();
    }

    /**
     * Map<Integer, HashMap<String, HashMap<String, List<ResultWrapper>>>>
     */
    private void printMap() {
        for(Map.Entry<Integer, HashMap<String, HashMap<String, List<ResultWrapper>>>> entry1: this.atomMap.entrySet()) {
            for (Map.Entry<String, HashMap<String, List<ResultWrapper>>> entry2: entry1.getValue().entrySet()) {
                for (Map.Entry<String, List<ResultWrapper>> entry3: entry2.getValue().entrySet()) {
                    System.out.println(entry3);
                }
            }
        }
    }


    private class ResultWrapper {
        private int expId;
        private int atomId;
        private ResultWrapper(int expId, int atomId) {
            this.expId = expId;
            this.atomId = atomId;
        }

        @Override
        public String toString() {
            return Integer.toString(expId) + '_' + Integer.toString(atomId);
        }
    }

    private void inferProcess() {
        //second, tranverse the map, for each <container, type, atomname>, try to resolve
        for (Map.Entry<Integer, HashMap<String, HashMap<String, List<ResultWrapper>>>> entry1: this.atomMap.entrySet()) {
            int containerId = entry1.getKey();
            for (Map.Entry<String, HashMap<String, List<ResultWrapper>>> entry2: entry1.getValue().entrySet()) {
                //String type = entry2.getKey();
                for (Map.Entry<String, List<ResultWrapper>> entry3: entry2.getValue().entrySet()) {
                    String atomStr = entry3.getKey();
                    List<ResultWrapper> wrapperList = entry3.getValue();

                    List<Set<Integer>> typesetList = new ArrayList<>();
                    List<Set<Integer>> bindsetList = new ArrayList<>();
                    collectTwoList(containerId, wrapperList, bindsetList, typesetList);

                    //infer type and entity
                    if(atomStr.contains(".")) {
                        Set<Integer> interType = new HashSet<>();
                        Set<Integer> interBind = new HashSet<>();
                        if( !bindsetList.isEmpty()) {
                            interBind = intersetOnSet(bindsetList);
                        }
                        if(!typesetList.isEmpty()) {
                            interType = intersetOnSet(typesetList);
                        }
                        if (!interType.isEmpty() || !interBind.isEmpty()) {
                            updateAtoms(atomStr, containerId, wrapperList, interBind, interType);
                        }
                    }
                    else if(!atomStr.contains(".") && !typesetList.isEmpty()) {  //only infer type
                        Set<Integer> interType = intersetOnSet(typesetList);
                        if(!interType.isEmpty()) { //update atoms is the type is updated
                            updateAtomsOnlyType(atomStr, containerId, wrapperList, interType);
                        }
                    }
                }
            }
        }

    }


    private void printWrapper(int containerId, List<ResultWrapper> wrapperList) {
        System.out.println("\n");
        for (ResultWrapper wrapper :wrapperList) {
            int expId = wrapper.expId;
            int atomId = wrapper.atomId;
            ExpressionAtom atom = expressionCollect.getContainerById(containerId).getExpressionList().get(expId).getExpressionAtomList().get(atomId);
            System.out.println("wrapper: " + atom);
        }
    }

    /**
     * update bindId = intersection of bindId
     * update typeId = intersection of typeId
     *
     * todo:
     * typeId should not only reply on intersection , it maybe influenced by the updated bindId's type.
     * @param containerId
     * @param wrapperList
     * @param interBind
     * @param interType
     */
    private void updateAtoms(String atomStr, int containerId, List<ResultWrapper> wrapperList, Set<Integer> interBind, Set<Integer> interType) {
        if(wrapperList.size() == 1) {
            return;
        }
        //printWrapper(containerId, wrapperList);
        //System.out.println("refine atomStr: " + atomStr);
        for (ResultWrapper wrapper : wrapperList){
            int expId = wrapper.expId;
            int atomId = wrapper.atomId;
            ExpressionAtom atom = expressionCollect.getContainerById(containerId).getExpressionList().get(expId).getExpressionAtomList().get(atomId);
            Set<Integer> originalBind = SequencecUtil.transformList(atom.getBindIdList());
            Set<Integer> originalType = SequencecUtil.transformList(atom.getTypeIdList());
            String type = "";
            boolean isBindUpdate = false;
            boolean isTypeUpdate = false;
            //update bind Ids. , it seems never be used.
            if(!interBind.isEmpty() && !SequencecUtil.isSetEqual(originalBind, interBind)){
                isBindUpdate =  true;
                type = judgeResolvedType(originalBind, containerId, expId, atomId);
                //update bind
                //System.out.println("update bind: old: " + originalBind + ", new: " + interBind);
                expressionCollect.getContainerById(containerId).getExpressionList().get(expId).getExpressionAtomList().get(atomId).updateAtomBySet(atom.getResolved(), type, new ArrayList<>(interBind), atom.getTypeIdList());
            }

            //update type Ids
            if(!interType.isEmpty() && ! SequencecUtil.isSetEqual(originalType, interType)) {
                isTypeUpdate = true;
                type = judgeResolvedType(originalType, containerId, expId, atomId);
                //System.out.println("update type: old: " + originalType + ", new: " + interType);
                expressionCollect.getContainerById(containerId).getExpressionList().get(expId).getExpressionAtomList().get(atomId).updateAtomBySet(atom.getResolved(), type, atom.getBindIdList(), new ArrayList<>(interType));
            }

            if(isBindUpdate || isTypeUpdate) {
                //update the atom who use it
                //save the atoms that will be updated, and whose atomId > current atomId
                resolverPostponed.updateNextAtom(containerId, expId, atomId);
            }
        }
    }


    /**
     * if original resolved = [], then type = extension
     * if original resolved =[XXX,XXX,...],
     *           type = originamanner if atomID is the last,
     *           other wise type=refine.
     * @param originalTypeOrBind
     * @param containerId
     * @param expId
     * @param atomId
     * @return
     */
    private String judgeResolvedType(Set<Integer> originalTypeOrBind, int containerId, int expId, int atomId) {
        ExpressionAtom atom = expressionCollect.getContainerById(containerId).getExpressionList().get(expId).getExpressionAtomList().get(atomId);
        String type;
        if(!originalTypeOrBind.isEmpty()) {
            type = Configure.RESOLVED_TYPE_IMPLICIT_REFINE;
            if(atomId == expressionCollect.getContainerById(containerId).getExpressionList().get(expId).getExpressionAtomList().size() - 1) {
                type = atom.getResolvedManner();
            }

        } else {
            type = Configure.RESOLVED_TYPE_IMPLICIT_EXTENSION;
        }

        return type;
    }



    /**
     * if the interType != atoms type,
     *    if atoms type.set == null (after remove -1), then implicit_extend
     *    if atoms type.set != null (after remove -1), then implicit_refine
     *
     *    if current atom is updated, then the usage of this this atom should be again refined.
     * @param containerId
     * @param wrapperList
     * @param interType
     */
    private void updateAtomsOnlyType(String atomStr, int containerId, List<ResultWrapper> wrapperList, Set<Integer> interType) {
        if(wrapperList.size() == 1) {
            return;
        }
        //printWrapper(containerId, wrapperList);
        //System.out.println("refine atomStr: " + atomStr);
        for (ResultWrapper wrapper : wrapperList){
            int expId = wrapper.expId;
            int atomId = wrapper.atomId;
            ExpressionAtom atom = expressionCollect.getContainerById(containerId).getExpressionList().get(expId).getExpressionAtomList().get(atomId);
            Set<Integer> originalType = SequencecUtil.transformList(atom.getTypeIdList());
            if (!originalType.isEmpty() && SequencecUtil.isSetEqual(originalType, interType)) {
                continue;
            }
            String type = judgeResolvedType(originalType, containerId, expId, atomId); //resolved manner
            //update current atom
            //System.out.println("update type: old: " + originalType + ", new: " + interType);
            expressionCollect.getContainerById(containerId).getExpressionList().get(expId).getExpressionAtomList().get(atomId).updateAtomBySet(atom.getResolved(), type, atom.getBindIdList(), new ArrayList<>(interType));
            //update the atom who use it
            //postponeUpdateNextAtoms(containerId, expId, atomId, false, true); //the atom next the atomId
            resolverPostponed.updateNextAtom(containerId,expId,atomId);
        }
    }


    /**
     * if has only one set, return this set.
     * else, return the intersection
     * @param setList
     * @return
     */
    private Set<Integer> intersetOnSet(List<Set<Integer>> setList) {
        Set<Integer> resSet = setList.get(0);
        for (int index = 1; index < setList.size(); index ++) {
            resSet.retainAll(setList.get(index));
        }
        return resSet;
    }

    /**
     * collec the non-empty types and binds
     * @param containerId
     * @param wrapperList
     * @param bindsetList
     * @param typesetList
     */
    private void collectTwoList(int containerId, List<ResultWrapper> wrapperList, List<Set<Integer>> bindsetList, List<Set<Integer>> typesetList) {
        for (ResultWrapper resultWrapper : wrapperList) {
            int expId = resultWrapper.expId;
            int atomId = resultWrapper.atomId;
            ExpressionAtom atom = expressionCollect.getContainerById(containerId).getExpressionList().get(expId).getExpressionAtomList().get(atomId);
            Set<Integer> typeset = SequencecUtil.transformList(atom.getTypeIdList());
            Set<Integer> bindset = SequencecUtil.transformList(atom.getBindIdList());
            if (!typeset.isEmpty()) {
                typesetList.add(typeset);
            }
            if(!bindset.isEmpty()) {
                bindsetList.add(bindset);
            }
        }
    }

    /** for one container inside,
     * produce map{"function"}{"pure method/variable name"} = [wrapper1, wrapper2, ... ]
     * @param
     * @return
     */
    private void groupAtomsByName() {
        int containerSize = expressionCollect.getCurrentIndex();
        for (int containerId = 0; containerId < containerSize; containerId ++) {
            List<Expression> expressionList = expressionCollect.getContainerById(containerId).getExpressionList();
            for (int expId = 0; expId < expressionList.size(); expId++) {
                List<ExpressionAtom> expressionAtomList = expressionList.get(expId).getExpressionAtomList();
                for (int atomId = 0; atomId < expressionAtomList.size(); atomId++) {
                    ExpressionAtom atom = expressionAtomList.get(atomId);
                    String atomStr = atom.getStr();
                    String type = Configure.BASIC_ENTITY_VARIABLE;
                    if (isFunction(atomStr)) {
                        atomStr = simplify(atomStr);
                        type = Configure.BASIC_ENTITY_FUNCTION;
                    }
                    ResultWrapper resultWrapper = new ResultWrapper(expId, atomId);
                    updateMap(containerId, type, atomStr, resultWrapper); //add into resMap, the reference is passed, so it can be modified
                }
            }
        }
    }


    /**
     * a().b is not function
     * fun()
     * a().b()
     * a.b()
     * @param atomStr
     * @return
     */
    private boolean isFunction(String atomStr) {
        if(atomStr.contains(".")) {
            String [] tmp = atomStr.split("\\.");
            atomStr = tmp[tmp.length - 1];
        }
        if(atomStr.contains("(")) {
            return true;
        }
        return false;
    }


    /**
     * from a().b() -> a().b
     * from a.b() -> a.b
     * from b() -> b
     * @param atomStr
     * @return
     */
    private String simplify(String atomStr) {
        String pre = "";
        if(atomStr.contains(".")) {
            String [] tmp = atomStr.split("\\.");
            String postStr = tmp[tmp.length - 1];
            pre = atomStr.substring(0, atomStr.length() - postStr.length()); // contains dot
            atomStr = postStr;
        }
        return pre + atomStr.split("\\(")[0];

    }


    /**
     * update this.atomAmp
     * @param containerId
     * @param type
     * @param atomStr
     * @param resultWrapper
     */
    private void updateMap(int containerId, String type, String atomStr, ResultWrapper resultWrapper) {
        if(!this.atomMap.containsKey(containerId)) {
            this.atomMap.put(containerId, new HashMap<>());
        }
        if (!this.atomMap.get(containerId).containsKey(type)) {
            this.atomMap.get(containerId).put(type, new HashMap<>());
        }
        if (!this.atomMap.get(containerId).get(type).containsKey(atomStr)) {
            this.atomMap.get(containerId).get(type).put(atomStr, new ArrayList<>());
        }
        this.atomMap.get(containerId).get(type).get(atomStr).add(resultWrapper);
    }




}
