package multiparser.py3extractor.visitor.secondpass;

import multiparser.entity.Entity;
import multiparser.py3extractor.ConstantString;
import multiparser.py3extractor.pyentity.ModuleEntity;
import multiparser.py3extractor.pyentity.PyFunctionEntity;
import multiparser.py3extractor.search.NameSearch;
import multiparser.util.Configure;
import multiparser.util.Tuple;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CallVisitor extends DepVisitor {
    private NameSearch nameSearch = NameSearch.getNameSearchInstance();

    @Override
    public void setDep() {
        for (Entity entity : singleCollect.getEntities()) {
            if (entity instanceof PyFunctionEntity
                    || entity instanceof ModuleEntity) {
                System.out.println("inside: " + entity.getName());
                setCallDep(entity.getId());
            }
        }
    }


    /**
     * process the callee for this function or method
     * @param modOrFunId
     */
    private void setCallDep(int modOrFunId) {
        //modify calleeStr list
        modifyCalledFuncs(modOrFunId);

        ArrayList<String> calledFuns = getCalledFunctions(modOrFunId);
        if(calledFuns == null) {
            return;
        }

        ArrayList<Integer> idList = new ArrayList<Integer>();
        for(int index = 0; index < calledFuns.size(); index++) {
            String calleeStr = calledFuns.get(index);
            System.out.println("oldCallee= " + calleeStr);
            String simpleCalleeStr = simplifyCalleeStr(index, calledFuns, idList);
            int calleeId = searchCallee(simpleCalleeStr, modOrFunId);
            idList.add(calleeId);
            if(calleeId != -1) {
                saveRelation(modOrFunId, calleeId, ConstantString.RELATION_CALL, ConstantString.RELATION_CALLED_BY);
                System.out.println("find=      " +  singleCollect.getEntities().get(calleeId) + "\n");
            }
            else {
                System.out.println("find=      " + calleeId + "\n\n");
            }
        }
    }


    /**
     *
     * @param simpleCalleeStr    x1.x2()  or x2()  or custompre_id.x3()
     * @param modOrFunId
     * @return
     */
    private int searchCallee(String simpleCalleeStr, int modOrFunId) {
        String destStr = simpleCalleeStr.split("\\(")[0];
        System.out.println("newCallee= " + destStr);

        if(isBuiltinFunction(destStr)) {
            System.out.println("1.it is builtin function. scopeId= " + modOrFunId + "; str= " + destStr);
            return -1;
        }
        if(destStr.equals(ConstantString.SUPER)) {
            return searchCalleeIfSuper(destStr, modOrFunId);
        }
        else {
            return searchCalleeOthercases(destStr, modOrFunId);
        }
    }

    /**
     * if super(),call parent.init()
     * if super().method1(),  and if parent are more than one,
     * we don't know which parent the super will refer to, until we see method1().
     * beacuse not every parent has method1() method member.
     * @param str  super
     * @param modOrFunId
     * @return
     */
    private int searchCalleeIfSuper(String str, int modOrFunId) {
        return -1;
    }

    /**
     * search callee which are not "super", not builtin functions.
     * @param destStr
     * @param modOrFunId
     * @return
     */
    private int searchCalleeOthercases(String destStr, int modOrFunId) {
        int scopeId = modOrFunId;
        int flag = 0;
        while(!destStr.equals(Configure.NULL_STRING)) {
            if(destStr.startsWith(Configure.DOT)) {
                destStr = destStr.substring(1, destStr.length());
            }
            if (destStr.startsWith(ConstantString.CUSTOM_PRE)) {
                //update scopeID
                scopeId = identifyCustomId(destStr);
                //update str
                destStr = destStr.substring(ConstantString.CUSTOM_PRE.length() + Integer.toString(scopeId).length(), destStr.length());
                System.out.println("1.scopeId= " + scopeId + "; str= " + destStr);
            } else if (scopeId != -1) {
                Tuple<Integer, String> matchedRes = findMatchInScope(flag, destStr, scopeId);
                scopeId = matchedRes.x;
                String matchedSubStr = matchedRes.y;
                destStr = destStr.substring(matchedSubStr.length(), destStr.length());
                System.out.println("2.scopeId= " + scopeId + "; str= " + destStr);

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
     * @param str
     * @return
     */
    private boolean isBuiltinFunction(String str) {
        if(str.contains(Configure.DOT)) {
            return false;
        }
        for(int i = 0; i < ConstantString.BUILT_IN_FUNCTIONS.length; i++) {
            if(str.equals(ConstantString.BUILT_IN_FUNCTIONS[i])) {
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
            System.out.println("submap in scope " + scopeId + ": " + submap );
        }

        Tuple<Integer, String> tuple;
        if(flag == 0) {
            tuple = findNameWithDotInScope(str, scopeId);
            if(tuple.x != -1) {
                System.out.println("find with dot: " + tuple.x + "; " + tuple.y);
                return tuple;
            }
        }
        tuple = findNameWithoutDotInScope(str, scopeId);
        System.out.println("find without dot: " + tuple.x + "; " + tuple.y);
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
        String intStr = newStr.substring(ConstantString.CUSTOM_PRE.length(), newStr.length());
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
     * simplify calleeStr as having only one "()".
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
                String replacedStr = ConstantString.CUSTOM_PRE + Integer.toString(idList.get(index));
                newStr = ( newStr.substring(0, i)  +  replacedStr  +  newStr.substring(i + substr.length(), newStr.length()) );
            }
            //if not found, please go on search.
        }

        if(countAppearNumber(newStr, Configure.LEFT_PARENTHESES) == 1
                && countAppearNumber(newStr, Configure.RIGHT_PARENTHESES) == 1) {
            //System.out.println("Simplifying old: " + oldStr + "; new: " + newStr);
        }
        else {
            //System.out.println("Wow Simplifying old: " + oldStr + "; new: " + newStr);
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
        if(singleCollect.getEntities().get(modOrFunId) instanceof PyFunctionEntity) {
            ((PyFunctionEntity) singleCollect.getEntities().get(modOrFunId)).setCalledFunctions(newCalledStrs);
        }
        else if(singleCollect.getEntities().get(modOrFunId) instanceof ModuleEntity) {
            ((ModuleEntity) singleCollect.getEntities().get(modOrFunId)).setCalledFunctions(newCalledStrs);
        }

    }


    /**
     * get function call list from module or function entity
     * @param modOrFunId
     * @return
     */
    private ArrayList<String> getCalledFunctions(int modOrFunId) {
        ArrayList<String> calledStrs = null;
        if(singleCollect.getEntities().get(modOrFunId) instanceof PyFunctionEntity) {
            calledStrs = ((PyFunctionEntity) singleCollect.getEntities().get(modOrFunId)).getCalledFunctions();
        }
        else if(singleCollect.getEntities().get(modOrFunId) instanceof ModuleEntity) {
            calledStrs = ((ModuleEntity) singleCollect.getEntities().get(modOrFunId)).getCalledFunctions();
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
