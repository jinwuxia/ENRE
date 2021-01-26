package expression;

import uerr.SingleCollect;

import java.util.ArrayList;

public class ExpressionCollect {
    // all expression container.  set id = index
    private ArrayList<ExpressionContainer> expressionContainers = new ArrayList<>();

    private static ExpressionCollect expressionCollect = new ExpressionCollect();


    private ExpressionCollect(){}

    public static ExpressionCollect getExpressionCollect() {
        return expressionCollect;
    }

    public ArrayList<ExpressionContainer> getContainers() {
        return expressionContainers;
    }

    public int getCurrentIndex() {
        return expressionContainers.size();
    }

    public void addContainer(ExpressionContainer container) {
        expressionContainers.add(container);
    }

    public ExpressionContainer getContainerById(int id) {
        return expressionContainers.get(id);
    }

    public void printAllAtoms() {
        int containerSize = expressionCollect.getCurrentIndex();
        for (int containerId = 0; containerId < containerSize; containerId ++) {
            int parentEntityid = expressionCollect.getContainerById(containerId).getParentId();
            String entityName = SingleCollect.getSingleCollectInstance().getLongName(parentEntityid);
            System.out.println("in " + entityName);
            for (int atomId = 0; atomId < expressionCollect.getContainerById(containerId).getExpressionAtomList().size(); atomId++) {
                System.out.println(expressionCollect.getContainerById(containerId).getExpressionAtomList().get(atomId) + "\n");
            }
        }
    }
}
