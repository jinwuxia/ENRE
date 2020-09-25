package client;

import formator.Formator;
import formator.fjson.JDepObject;
import formator.fxml.XDepObject;
import formator.spreadsheet.Csvgrapher;
import hianalyzer.HiDepData;
import hianalyzer.HiDeper;
import priextractor.AnayzerIntf;
import entitybuilder.BuilderIntf;
import expression.Expression;
import expression.ExpressionCollect;
import priextractor.goextractor.GoRelationInf;
import priextractor.py3extractor.PyRelationInf;
import uerr.AbsEntity;
import uerr.SingleCollect;
import util.RelationInterface;
import writer.UndWriter;
import util.Configure;
import writer.WriterIntf;

import java.util.ArrayList;
import java.util.List;

/**
 * it should be template method pattern, so that the steps and oder of processing are fixed.
 */
public class DepWork {
    protected static Configure configure = Configure.getConfigureInstance();

    public void deperWorkflow(String depMask) {
        String[] depTypes = getDepType(depMask);

        long startTime = System.currentTimeMillis();

        
        //identify Entities
        BuilderIntf entityTreeBuilder = new BuilderIntf();
        
        entityTreeBuilder.run();

        
        //extract Deps
        AnayzerIntf entityDepAnalyzer = new AnayzerIntf();
        entityDepAnalyzer.run();

        long endTime = System.currentTimeMillis();
        System.out.println("\nConsumed time: " + (float) ((endTime - startTime) / 1000.00) + " s,  or " + (float) ((endTime - startTime) / 60000.00) + " min.\n");



        //build hierarchical dependencies
        HiDeper hiDeper = new HiDeper();
        hiDeper.run();
        //hiDeper.tmpOutput();
        HiDepData hiDepData = HiDepData.getInstance();

        Formator formator = new Formator(depTypes, Configure.RELATION_LEVEL_FILE);
        JDepObject jDepObject = formator.getfJsonDataModel();
        XDepObject xDepObject = formator.getfXmlDataModel();

        Csvgrapher csvgrapher = new Csvgrapher();
        csvgrapher.buildProcess();
        ArrayList<String[]> allNodes = csvgrapher.getNodes();
        ArrayList<String[]> allEdges = csvgrapher.getEdges();

        WriterIntf writer = new WriterIntf();
        writer.run(jDepObject, xDepObject, allNodes, allEdges);

        //output the summary of the acquired results.


        summary();

    }


    private String[] getDepType(String depMask) {
        ArrayList<String> depStrs = new ArrayList<String>();
        for (int i = 0; i < depMask.toCharArray().length; i++) {
            if (depMask.toCharArray()[i] == '1') {
                if (i == 0) {
                    depStrs.add(Configure.RELATION_IMPORT);
                } else if (i == 1) {
                    depStrs.add(Configure.RELATION_INHERIT);
                } else if (i == 2) {
                    depStrs.add(Configure.RELATION_IMPLEMENT);
                } else if (i == 3) {
                    depStrs.add(Configure.RELATION_RECEIVE);
                } else if (i == 4) {
                    depStrs.add(Configure.RELATION_CALL);
                } else if (i == 5) {
                    depStrs.add(Configure.RELATION_SET);
                } else if (i == 6) {
                    depStrs.add(Configure.RELATION_USE);
                } else if (i == 7) {
                    depStrs.add(Configure.RELATION_PARAMETER);
                } else if (i == 8) {
                    depStrs.add(Configure.RELATION_RETURN);
                }
            }
        }
        String[] depStrArr = depStrs.toArray(new String[depStrs.size()]);
        return depStrArr;
    }

    private void summary() {
        Configure configure = Configure.getConfigureInstance();
        RelationInterface relationInterface = null;
        if (configure.getLang().equals(Configure.GO_LANG)) {
            relationInterface = new GoRelationInf();
        } else if (configure.getLang().equals(Configure.PYTHON_LANG)) {
            relationInterface = new PyRelationInf();
        }
        if(relationInterface !=  null) {
            System.out.println("\nSummarize the entity's results:");
            System.out.println(relationInterface.entityStatis());
            System.out.println("\nSummarize the dependency's results:");
            //System.out.println(relationInterface.dependencyStatis());
            UndWriter undWriter = new UndWriter();
            System.out.println(undWriter.priDepStatis()+ "\n");
        }
    }
    private static void test() {
    	SingleCollect singleCollect = SingleCollect.getSingleCollectInstance();
        ExpressionCollect expressionCollect = ExpressionCollect.getExpressionCollect();
        int i = 0 ;
        for (AbsEntity entity1 : singleCollect.getEntities()) {
        	if(i >= 1)  break;
            int containerId = entity1.getExpContainerId();
            if (containerId == -1) continue;
            List<Expression> exps = expressionCollect.getContainerById(containerId).getExpressionList();
            for (Expression exp : exps) {
                if(true) {
                    List<String> testlist = exp.gettext();
                    String[] test = new String[testlist.size()];
                    testlist.toArray(test);
                    List<Integer> line = exp.getLineno();
                    Integer[] lineno = new Integer[line.size()];
                    line.toArray(lineno);
                    List<Integer> lastline = exp.getLastLineno();
                    Integer[] lastlineno = new Integer[lastline.size()];
                    lastline.toArray(lastlineno);
                    List<Integer> row = exp.getrowNo();
                    Integer[] rowno = new Integer[row.size()];
                    lastline.toArray(rowno);
                    List<Integer> lastrow = exp.getlastrowNo();
                    Integer[] lastrowno = new Integer[lastrow.size()];
                    lastline.toArray(lastrowno);
                    System.out.println("test0824"+test[0]+Integer.toString(lineno[0])+Integer.toString(lastlineno[0])+Integer.toString(rowno[0])+Integer.toString(lastrowno[0])+"readyforchange");
                    
                    
                }
            }
            i++;
        }
    }

    
    

}
