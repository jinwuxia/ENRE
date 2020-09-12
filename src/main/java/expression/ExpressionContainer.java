package expression;

import java.util.ArrayList;
import java.util.List;

import uerr.SingleCollect;

/**
 * hold expression list for an entity
 * one entity has on expressionContainer
 */
public class ExpressionContainer {
    private int parentId; //entityId in which this container is contained
    private int id;
    private List<Expression> expressionList = new ArrayList<>();

    public ExpressionContainer(int parentId, int id) {
        this.parentId = parentId;
        this.id = id;

    }

    public int getParentId() {
        return parentId;
    }

    public void addExpression(Expression  exp) {
        expressionList.add(exp);
    }
    public void setExpressionList(List<Expression> expressionList) {
        this.expressionList = expressionList;
    }

    public List<Expression> getExpressionList() {
        return expressionList;
    }


    public int getExprByName(String name) {
        for (int index=0; index < expressionList.size(); index++) {
            Expression expression = expressionList.get(index);
            String expressionStr = expression.getRawStr();
            if (name.equals(expressionStr)) {
                return index;
            }
        }
        return -1;
    }

    /**
     * wrap the functions of Expression
     * @return
     */
    public List<ExpressionAtom> getExpressionAtomList() {
        List<ExpressionAtom> expressionAtomList = new ArrayList<>();
        for (Expression expression : expressionList) {
            expressionAtomList.addAll(expression.getExpressionAtomList());
        }
        return expressionAtomList;
    }

    /*
     * get the corresponding  file location 
     */
    
    public String getLocatedFile(int id) {
    	SingleCollect singleCollect = SingleCollect.getSingleCollectInstance();
    	while(id != -1 && !singleCollect.isFile(id)) {
    		id = singleCollect.getEntityById(id).getParentId();
    	}
    	if(singleCollect.isFile(id)) {
    		return singleCollect.getLongName(id);
    	}
    	else return "";
    }


}
