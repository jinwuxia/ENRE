package expression;

import util.Configure;

import java.util.ArrayList;
import java.util.List;

import static java.lang.System.exit;

public class Expression {
    private String rawStr;
    private String aliasStr; // simplify rawStr into aliasStr, then use aliasStr to expand into atoms
    private String location; //right or left
    private int freq;
    private List<Integer> lineno = new ArrayList<>(); //code line number, because the same string expression put together, so its lineno is a list
    private List<ExpressionAtom> expressionAtomList = new ArrayList<>();

    public Expression(String exp, String aliasStr, String location, int freq, List<Integer> lineno) {
        this.rawStr = exp;
        this.location = location;
        this.aliasStr = aliasStr;
        this.freq = freq;
        this.lineno = lineno;
        extendExpressionToAtoms();


    }

    public int getFreq() {
        return freq;
    }

    public void setFreq(int freq) {
        this.freq = freq;
    }

    public String getRawStr() {
        return rawStr;
    }

    public List<ExpressionAtom> getExpressionAtomList() {
        return expressionAtomList;
    }

    public List<Integer> getLineno(){
        return this.lineno;
    }
    public void addLineno(int num) {
        this.lineno.add(num);
    }

    /**alias name: even if have parameter, the para in alias  does not contain () and ".".
     * extend rawStr into expression atom by using "dot"
     */
    private void extendExpressionToAtoms () {
        //System.out.println("raw:" + rawStr);
        String str = aliasStr;
        String[] arr = str.split("\\.");
        for (int index = 0; index < arr.length; index++) {
            String pre = Configure.NULL_STRING;
            if(index != 0) {
                for (int i = 0; i < index; i++) {
                    pre += arr[i];
                    pre += Configure.DOT;
                }
            }
            String newStr = pre + arr[index];

            //""
            if(newStr.equals(Configure.NULL_STRING)) {
                continue;
            }
            //.join()
            if(newStr.startsWith(".")) {
                newStr = newStr.substring(1);
            }
            if(isMatchedParenthese(newStr)) {
                int freq = this.freq;  // the freq of atom = the freq of expression
                String usageType = inferUsage(newStr);
                ExpressionAtom atom = new ExpressionAtom(newStr, usageType, freq, this.lineno);
                this.expressionAtomList.add(atom);
                //System.out.println("extend:" + newStr);
            }
            else {
                System.out.println("error isMatchedParenthese: " + newStr);
                try {
                    throw new Exception();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }


    /**
     * if has no dot, left, then set.
     * if last has (, then cal.   z.y() or y()
     * if has dot, last has no (, then dot.  z.y or z.y().x
     * other is use
     * @param newStr
     * @return
     */
    private String inferUsage(String newStr) {
        String[] arr = newStr.split("\\.");
        String last = arr[arr.length - 1];

        if(last.contains("(")) {
            return Configure.EXPRESSION_CALL;
        }
        else if(location.equals("left") && !newStr.contains(".")) {
            return Configure.EXPRESSION_SET;
        }
        else if(newStr.contains(".")) {
            return Configure.EXPRESSION_DOT;
        }
        return Configure.EXPRESSION_USE;
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
