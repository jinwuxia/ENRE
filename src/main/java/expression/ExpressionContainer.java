package uerr;

import java.util.ArrayList;
import java.util.List;

/**
 * hold expression list for an entity
 * one entity has on expressionContainer
 */
public class ExpressionContainer {
    private int id;
    private List<Expression> expressionList = new ArrayList<>();

    public ExpressionContainer() {

    }

    public void setId(int id) {
        this.id = id;
    }

    public void addExpression(Expression  exp) {
        expressionList.add(exp);
    }
    public void setExpressionList(List<Expression> expressionList) {
        this.expressionList = expressionList;
    }
}
