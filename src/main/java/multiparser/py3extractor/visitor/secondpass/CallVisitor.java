package multiparser.py3extractor.visitor.secondpass;

import multiparser.entity.Entity;
import multiparser.py3extractor.ConstantString;
import multiparser.py3extractor.pyentity.ModuleEntity;
import multiparser.py3extractor.pyentity.PyFunctionEntity;

import java.util.ArrayList;

public class CallVisitor extends DepVisitor {

    @Override
    public void setDep() {
        for (Entity entity : singleCollect.getEntities()) {
            if (entity instanceof PyFunctionEntity || entity instanceof ModuleEntity) {
                    setCallDep(entity.getId());
                }
        }
    }


    /**
     * process the callee for function or method
     * @param modOrFunId
     */
    private void setCallDep(int modOrFunId) {
        //modify calleeStr list
        modifyCalledFuncs(modOrFunId);

        //for()


    }

    /**
     * since x().y() is only a callee in called function,
     * it should be split into x() and x().y(), then added into the existing arr, or wholly replace
     * @param modOrFunId
     */
    private void modifyCalledFuncs(int modOrFunId) {
        ArrayList<String> oldCalledStrs = null;
        if(singleCollect.getEntities().get(modOrFunId) instanceof PyFunctionEntity) {
            oldCalledStrs = ((PyFunctionEntity) singleCollect.getEntities().get(modOrFunId)).getCalledFunctions();
        }
        else if(singleCollect.getEntities().get(modOrFunId) instanceof ModuleEntity) {
            oldCalledStrs = ((ModuleEntity) singleCollect.getEntities().get(modOrFunId)).getCalledFunctions();
        }
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
     * split process
     * @param oldCalledStrs
     * @return
     */
    private ArrayList<String> getNewListBySplit(ArrayList<String> oldCalledStrs) {
        ArrayList<String> newCalledStrs = new ArrayList<String>();
        for(String callee : oldCalledStrs) {
            System.out.println("callee:" + callee);
            String[] arr = callee.split("\\.");
            for (int index = 0; index < arr.length; index++) {
                if(arr[index].contains(ConstantString.LEFT_PARENTHESES)
                        && arr[index].contains(ConstantString.RIGHT_PARENTHESES)) {
                    String pre = ConstantString.NULL_STRING;
                    if(index != 0) {
                        for (int i = 0; i < index; i++) {
                            pre += arr[i];
                            pre += ConstantString.DOT;
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
        int leftParenthesis = countAppearNumber(str, ConstantString.LEFT_PARENTHESES);
        int rightParenthesis = countAppearNumber(str, ConstantString.RIGHT_PARENTHESES);

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
