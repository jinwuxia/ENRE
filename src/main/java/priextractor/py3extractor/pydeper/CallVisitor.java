package priextractor.py3extractor.pydeper;

import uerr.AbsEntity;
import entitybuilder.pybuilder.PyConstantString;
import entitybuilder.pybuilder.pyentity.ModuleEntity;
import entitybuilder.pybuilder.pyentity.PyFunctionEntity;
import priextractor.py3extractor.searcher.NameSearch;
import util.Configure;
import util.Tuple;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CallVisitor extends DepVisitor {
    private NameSearch nameSearch = NameSearch.getNameSearchInstance();

    public CallVisitor() {
        //call this for search possible (implicit) function calls.
        singleCollect.identifySameMethodName();
    }


    @Override
    public void setDep() {
        for (AbsEntity entity : singleCollect.getEntities()) {
            if (entity instanceof PyFunctionEntity
                    || entity instanceof ModuleEntity) {
                System.out.println("inside: " + entity.getName());

                //modify calleeStr list: split the form of x().y() into x() and x().y().
                int modOrFunId = entity.getId();
                modifyCalledFuncs(modOrFunId);

                //call dep inference
                setCallDep(modOrFunId);
            }
        }
    }



    /**
     * process the callee for this function or method
     * @param modOrFunId
     */
    private void setCallDep(int modOrFunId) {
        ArrayList<String> calledFuns = getCalledFunctions(modOrFunId);
        if(calledFuns == null) {
            return;
        }
        ArrayList<Integer> idList = new ArrayList<Integer>();
        for(int index = 0; index < calledFuns.size(); index ++) {
            String calleeStr = calledFuns.get(index);
            //System.out.println("oldCallee= " + calleeStr);
            //simplify calleeStr, making it have only one "()
            String simpleCalleeStr = simplifyCalleeStr(index, calledFuns, idList);

            //case 1: callee is built-in function/Exception or super.m()
            boolean isResolved = isResoveCase1(calleeStr, simpleCalleeStr);
            int calleeId = -1;
            if(isResolved) {
                idList.add(calleeId);
            }

            //case 2: regular cases (same to static-typing language), it includes implicit_internal_call and regular explciit call.
            calleeId = resolveCase2(modOrFunId, calleeStr, simpleCalleeStr);
            idList.add(calleeId);
            if(calleeId != -1) {
                continue;
            }

            //case 3: others, try to resolve use implict-resolution strategy
            isResolved = isResolveCase3(modOrFunId, calleeStr, simpleCalleeStr);

            //case 4: the ones which are not resolved.
            if (!isResolved) {
                System.out.println("call resolve,unknown," + calleeStr + "," + simpleCalleeStr);
            }
        }
    }


    /**
     * case 1: callee is built-in function/Exception or super.m()
     * @param calleeStr
     * @param simpleCalleeStr
     * @return
     */
    private  boolean isResoveCase1(String calleeStr, String simpleCalleeStr) {
        if (isBuiltinFunction(simpleCalleeStr) || isBuiltinException(simpleCalleeStr)) {
            System.out.println("call resolve,built-in," + calleeStr + "," + simpleCalleeStr);
            return true;
        }
        if(isSuperCallee(simpleCalleeStr)) {
            System.out.println("call resolve,super," + calleeStr + "," + simpleCalleeStr);
            return true;
        }
        return false;
    }

    /**
     * case 2: regular cases (same to static-typing language):
     *        importedX.y();  x.y() x with explicit type;  y()
     *        it includes implicit_internal_call and regular explciit call.
     * @param modOrFunId
     * @param calleeStr
     * @param simpleCalleeStr
     * @return
     */
    private int resolveCase2(int modOrFunId, String calleeStr, String simpleCalleeStr) {
        int calleeId = searchCalleeRegularCase(simpleCalleeStr, modOrFunId);
        //save
        if(calleeId != -1) {
            if (isLocalInitVarCallee(simpleCalleeStr, modOrFunId)) {
                saveRelation(modOrFunId, calleeId, Configure.RELATION_IMPLICIT_INTERNAL_CALL, Configure.RELATION_IMPLICIT_INTERNAL_CALLED_BY);
                System.out.println("call resolve,internal," + calleeStr + "," + simpleCalleeStr);

            } else {
                saveRelation(modOrFunId, calleeId, Configure.RELATION_CALL, Configure.RELATION_CALLED_BY);
                System.out.println("call resolve,explicit," + calleeStr + "," + simpleCalleeStr);
            }
        }
        return calleeId;
    }


    /**
     * case 3: others, try to resolve use implict-resolution strategy
     */
    private boolean isResolveCase3(int modOrFunId, String calleeStr, String simpleCalleeStr) {
        ImplicitCallResolver implicitCallResolver = new ImplicitCallResolver();
        ArrayList<Integer> possibleCallees = implicitCallResolver.resolveCallee(modOrFunId, calleeStr, simpleCalleeStr);
        if(possibleCallees.size() != 0) {
            //save
            System.out.println("call resolve,implicit," + calleeStr + "," + simpleCalleeStr);
            for (int possibleCalleeId : possibleCallees) {
                saveRelation(modOrFunId, possibleCalleeId, Configure.RELATION_IMPLICIT_EXTERNAL_CALL, Configure.RELATION_IMPLICIT_EXTERNAL_CALLED_BY);
            }
            return true;
        }
        return false;
    }




    /**
     * in the following two cases, it return true
     * 1) the callee is in the form of var.func() && var is initilazed inside explicit type.
     * 2) the callee is in the form of self.func()
     *
     *
     * judge it is form of var.callee() or not. the form can be resolve by var known type.
     * in this case, var is a  variable initialized inside the var's visible scope, or, self.
     *
     * note: in var.x.y() form, it has more than one dot,
     *       we cannot resolve it, it is not localinitvar callee.
     * @param simpleCalleeStr
     * @param modOrFunId
     * @return
     */
    private boolean isLocalInitVarCallee(String simpleCalleeStr, int modOrFunId) {
        //case 1
        if(simpleCalleeStr.split("\\(")[0].startsWith("self.")) {
            //System.out.println("isLocalInit: "  + simpleCalleeStr  + " " + true);
            return true;
        }

        //case 2
        String prefixName = simpleCalleeStr.split("\\.")[0];
        int nameId = nameSearch.getIdByNameInScope(prefixName, modOrFunId);
        //System.out.println("search prefix name: " + simpleCalleeStr + " " + Integer.toString(nameId));
        if(singleCollect.isVariable(nameId) && singleCollect.isVarTypeResolved(nameId)) {
            //System.out.println("isLocalInit: "  + simpleCalleeStr  + " " + res);
            return true;
        }


        if(simpleCalleeStr.split("\\(")[0].split("\\.").length >=2) {
            return false;
        }
        if(!simpleCalleeStr.split("\\(")[0].contains(".")) {
            //System.out.println("isLocalInit: "  + simpleCalleeStr  + " " + false);
            return false;
        }
        return false;
    }


    /**
     * if super(),call parent.init()
     *      * if super().method1(),  and if parent are more than one,
     *      * we don't know which parent the super will refer to, until we see method1().
     *      * beacuse not every parent has method1() method member.
     * @param simpleCalleeStr
     * @return
     */
    private boolean isSuperCallee(String simpleCalleeStr) {
        String destStr = simpleCalleeStr.split("\\(")[0];
        //System.out.println("newCallee= " + destStr);
        if(destStr.equals(PyConstantString.SUPER)) {
            return true;
        }
        return false;
    }


    /**
     * searcher callee which are not "super", not builtin functions.
     * x.y() static typing can resolve. x is var initialized in the visible scope.
     * or y()  or importedOne.y()
     * @param simpleCalleeStr
     * @param modOrFunId
     * @return
     */
    private int searchCalleeRegularCase(String simpleCalleeStr, int modOrFunId) {
        String destStr = simpleCalleeStr.split("\\(")[0];
        //System.out.println("del paras, newCallee= " + destStr);
        int scopeId = modOrFunId;
        int flag = 0;
        while(!destStr.equals(Configure.NULL_STRING)) {
            if(destStr.startsWith(Configure.DOT)) {
                destStr = destStr.substring(1, destStr.length());
            }
            if (destStr.startsWith(PyConstantString.CUSTOM_PRE)) {
                //update scopeID
                scopeId = identifyCustomId(destStr);
                //update str
                destStr = destStr.substring(PyConstantString.CUSTOM_PRE.length() + Integer.toString(scopeId).length(), destStr.length());
                //System.out.println("1.scopeId= " + scopeId + "; str= " + destStr);
            } else if (scopeId != -1) {
                Tuple<Integer, String> matchedRes = findMatchInScope(flag, destStr, scopeId);
                scopeId = matchedRes.x;
                String matchedSubStr = matchedRes.y;
                destStr = destStr.substring(matchedSubStr.length(), destStr.length());
                //System.out.println("2.scopeId= " + scopeId + "; str= " + destStr);

            } else {
                break;
            }
            if(flag == 0) {
                flag = 1;
            }
        } //end while
        return scopeId;
    }



    /**
     * judge whether str is builtin function or not.
     * @param simpleCalleeStr
     * @return
     */
    private boolean isBuiltinFunction(String simpleCalleeStr) {
        String str = simpleCalleeStr.split("\\(")[0];
        if(str.contains(Configure.DOT)) {
            return false;
        }
        for(int i = 0; i < PyConstantString.BUILT_IN_FUNCTIONS.length; i++) {
            if(str.equals(PyConstantString.BUILT_IN_FUNCTIONS[i])) {
                return true;
            }
        }
        return false;
    }

    private boolean isBuiltinException(String simpleCalleeStr) {
        String str = simpleCalleeStr.split("\\(")[0];
        if(str.contains(Configure.DOT)) {
            return false;
        }
        for(int i = 0; i < PyConstantString.BUILT_IN_EXCEPTIONS.length; i++) {
            if(str.equals(PyConstantString.BUILT_IN_EXCEPTIONS[i])) {
                return true;
            }
        }
        return false;
    }

    /**
     * in scope with id, find the matched Str.
     * str may starts with importedName or class,function,method,var name
     * @param str
     * @param scopeId
     * @param flag  =0, means it maybe imported name.
     * @return the matchedId and matchedStr
     */
    private Tuple<Integer, String> findMatchInScope(int flag, String str, int scopeId) {
        if(scopeId != -1) {
            HashMap<String, Integer> submap = nameSearch.getNameMapOfScope(scopeId);
            //System.out.println("submap in scope " + scopeId + ": " + submap );
        }

        Tuple<Integer, String> tuple;
        if(flag == 0) {
            tuple = findNameWithDotInScope(str, scopeId);
            if(tuple.x != -1) {
                //System.out.println("find with dot: " + tuple.x + "; " + tuple.y);
                return tuple;
            }
        }
        tuple = findNameWithoutDotInScope(str, scopeId);
        //System.out.println("find without dot: " + tuple.x + "; " + tuple.y);
        return tuple;
    }

    /**
     * the matchedStr contains DOT
     * @param str
     * @param scopeId
     * @return the matchedId and matchedStr
     */
    private Tuple<Integer, String> findNameWithDotInScope(String str, int scopeId) {
        Tuple<Integer, String> tuple = new Tuple<Integer, String>(-1, "");
        if(scopeId == -1) {
            return tuple;
        }
        HashMap<String, Integer> submap = nameSearch.getNameMapOfScope(scopeId);
        if(submap == null) {
            return tuple;
        }
        for(Map.Entry<String, Integer> entry : submap.entrySet()) {
            String matchedName = entry.getKey();
            int matchedId = entry.getValue();
            if(matchedName.contains(Configure.DOT) && str.startsWith(matchedName)) {
                tuple.x = matchedId;
                tuple.y = matchedName;
                return tuple;
            }
        }
        return tuple;
    }


    /**
     * the matchedStr does not contain DOT
     * @param str
     * @param scopeId
     * @return the matchedId and matchedStr
     */
    private Tuple<Integer, String> findNameWithoutDotInScope(String str, int scopeId) {
        Tuple<Integer, String> tuple = new Tuple<Integer, String>(-1, "");
        if(scopeId == -1) {
            return tuple;
        }
        String matchedStr = str.split("\\.")[0];
        int matchedId = nameSearch.getIdByNameInScope(matchedStr, scopeId);
        if(matchedId != -1) {
            tuple.x = matchedId;
            tuple.y = matchedStr;
        }
        return tuple;
    }


    /**
     * extract id from CUSTOM_PRE_ID
     * @param str
     * @return
     */
    private int identifyCustomId(String str) {
        String newStr = str.split("\\.")[0];
        String intStr = newStr.substring(PyConstantString.CUSTOM_PRE.length(), newStr.length());
        if(isIntegerParsable(intStr)) {
            return Integer.valueOf(intStr);
        }
        return -1;
    }

    /**
     * check whether a string can be converted into integer
     * @param str
     * @return
     */
    private boolean isIntegerParsable(String str) {
        try{
            Integer.valueOf(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


    /**
     * simplify calleeStr, making it have only one "()".
     * simplify x().y() as a.y().
     * simplify x(y()) as x(a).
     *
     * @param currentIndex
     * @param calledFuns
     * @param idList
     * @return
     */
    private String simplifyCalleeStr(int currentIndex, ArrayList<String> calledFuns, ArrayList<Integer> idList) {
        String oldStr = calledFuns.get(currentIndex); //current objective callee
        String newStr = oldStr;
        int index = currentIndex;
        while(index > 0
                && newStr.contains(Configure.RIGHT_PARENTHESES)
                && newStr.contains(Configure.LEFT_PARENTHESES)) {
            index = index - 1;
            String substr = calledFuns.get(index);
            int i = newStr.indexOf(substr);
            if(i != -1) { //found
                String replacedStr = PyConstantString.CUSTOM_PRE + Integer.toString(idList.get(index));
                newStr = ( newStr.substring(0, i)  +  replacedStr  +  newStr.substring(i + substr.length(), newStr.length()) );
            }
            //if not found, please go on searcher.
        }

        if(countAppearNumber(newStr, Configure.LEFT_PARENTHESES) == 1
                && countAppearNumber(newStr, Configure.RIGHT_PARENTHESES) == 1) {
            //System.out.println("Simplifying old: " + oldStr + "; new: " + newStr);
        }
        else {
            //System.out.println("Now Simplifying old: " + oldStr + "; new: " + newStr);
        }

        return newStr;
    }

    /**
     * since x().y() is only a callee in called function,
     * it should be split into x() and x().y(), then added into the existing arr, or wholly replace
     * @param modOrFunId
     */
    private void modifyCalledFuncs(int modOrFunId) {
        ArrayList<String> oldCalledStrs = getCalledFunctions(modOrFunId);
        if(oldCalledStrs == null) {
            return;
        }

        //split process
        ArrayList<String> newCalledStrs = getNewListBySplit(oldCalledStrs);
        //System.out.println("old= " + oldCalledStrs);
        //System.out.println("new= " + newCalledStrs);

        //update calledFunctions
        if(singleCollect.getEntityById(modOrFunId) instanceof PyFunctionEntity) {
            ((PyFunctionEntity) singleCollect.getEntityById(modOrFunId)).setCalledFunctions(newCalledStrs);
        }
        else if(singleCollect.getEntityById(modOrFunId) instanceof ModuleEntity) {
            ((ModuleEntity) singleCollect.getEntityById(modOrFunId)).setCalledFunctions(newCalledStrs);
        }

    }


    /**
     * get function call list from module or function uerr
     * @param modOrFunId
     * @return
     */
    private ArrayList<String> getCalledFunctions(int modOrFunId) {
        ArrayList<String> calledStrs = null;
        if(singleCollect.getEntityById(modOrFunId) instanceof PyFunctionEntity) {
            calledStrs = ((PyFunctionEntity) singleCollect.getEntityById(modOrFunId)).getCalledFunctions();
        }
        else if(singleCollect.getEntityById(modOrFunId) instanceof ModuleEntity) {
            calledStrs = ((ModuleEntity) singleCollect.getEntityById(modOrFunId)).getCalledFunctions();
        }
        return calledStrs;
    }


    /**
     * split process
     * @param oldCalledStrs
     * @return
     */
    private ArrayList<String> getNewListBySplit(ArrayList<String> oldCalledStrs) {
        ArrayList<String> newCalledStrs = new ArrayList<String>();
        for(String callee : oldCalledStrs) {
            //System.out.println("callee:" + callee);
            String[] arr = callee.split("\\.");
            for (int index = 0; index < arr.length; index++) {
                if(arr[index].contains(Configure.LEFT_PARENTHESES) && arr[index].contains(Configure.RIGHT_PARENTHESES)) {
                    String pre = Configure.NULL_STRING;
                    if(index != 0) {
                        for (int i = 0; i < index; i++) {
                            pre += arr[i];
                            pre += Configure.DOT;
                        }
                    }
                    String newStr = pre + arr[index];
                    if(isMatchedParenthese(newStr)) {
                        newCalledStrs.add(newStr);
                        //System.out.println("newStr:" + newStr);
                    }
                }
                else if(index == arr.length - 1) {
                    newCalledStrs.add(callee);
                    //System.out.println("newStr:" + callee);
                }

            }
        }
        return newCalledStrs;
    }


    /**
     * judge the left parentheses is equal to right parenthesis or not
     * @param str
     * @return
     */
    private boolean isMatchedParenthese(String str) {
        int leftParenthesis = countAppearNumber(str, Configure.LEFT_PARENTHESES);
        int rightParenthesis = countAppearNumber(str, Configure.RIGHT_PARENTHESES);

        if(leftParenthesis == rightParenthesis) {
            return true;
        }
        return false;
    }

    /**
     * count the number of substr appearing in str.
     * @param str
     * @param subStr
     * @return
     */
    private int countAppearNumber(String str, String subStr) {
        int count = 0;
        int start = 0;
        while ((start = str.indexOf(subStr, start)) != -1) {
            start = start + subStr.length();
            count++;
        }
        return count;
    }


}
