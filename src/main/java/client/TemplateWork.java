package client;

import formator.spreadsheet.Csvgrapher;
import priextractor.AnayzerIntf;
import entitybuilder.BuilderIntf;
import formator.Formator;
import formator.fjson.JDepObject;
import formator.fxml.XDepObject;
import hianalyzer.HiDepData;
import hianalyzer.HiDeper;
import priextractor.goextractor.GoRelationInf;
import priextractor.py3extractor.PyRelationInf;
import util.RelationInterface;
import writer.JsonWriter;
import writer.UndWriter;
import writer.WriterIntf;
import util.Configure;

import java.util.ArrayList;

public class TemplateWork {

    protected static Configure configure = Configure.getConfigureInstance();


    public final void workflow(String[] args) {
        String lang = args[0];
        String inputDir = args[1];
        String usageDir = args[2];
        String projectName = usageDir;
        String depMask = "111111111";
        if (args.length > 3) {
            projectName = args[3];
        }
        if (args.length > 4) {
            depMask = args[4];
        }

        config(lang, inputDir, usageDir, projectName);

        if(lang.equals(Configure.EXTERNAL_DATA_SOURCE)) {
            generateTraceClassCallsForExperiments();
        }
        else {
            deperWorkflow(depMask);
        }
    }



    private void deperWorkflow(String depMask) {
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

        Formator formator = new Formator(depTypes);
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

        //the followings are for experiments
        generateUnderstandFormatsForExperiments(writer);
        generateImplicitExternalCallsForExperiments(writer);
    }


    /**
     * parse the input parameter, save into configure
     *
     * @param inputDir
     * @param usageDir
     * @param projectName
     */
    private void config(String lang, String inputDir, String usageDir, String projectName) {
        configure.setLang(lang);
        configure.setInputSrcPath(inputDir);
        configure.setUsageSrcPath(usageDir);
        configure.setAnalyzedProjectName(projectName);
        configure.setDefault();
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


    //export formats consistent with understand, to compare with understand tool
    private void generateUnderstandFormatsForExperiments(WriterIntf writer) {
        writer.undTest();
    }

    //export external  implicit calls at file level as csv file
    //export external implicit calls at file level
    private void generateImplicitExternalCallsForExperiments(WriterIntf writer) {
        //export external  implicit calls at file level as csv file
        writer.exportImplicitExternalAtFileLevel();

        //export external implicit calls at file level
        String[] partialDepType = new String[]{Configure.RELATION_IMPLICIT_EXTERNAL_CALL};
        Formator partialFormator = new Formator(partialDepType);
        JDepObject partialJDepObject = partialFormator.getfJsonDataModel();
        JsonWriter jsonWriter = new JsonWriter();
        String partialJsonfile = configure.getAnalyzedProjectName() + "_implicit_dep.json";
        jsonWriter.toJson(partialJDepObject, partialJsonfile);
        System.out.println("Export " + partialJsonfile);
    }

    private void generateTraceClassCallsForExperiments() {
        String[] partialDepType = new String[]{Configure.RELATION_DYNAMIC_TRACE_CLASS_CALL};
        Formator partialFormator = new Formator(partialDepType);
        JDepObject partialJDepObject = partialFormator.getfJsonDataModel();
        JsonWriter jsonWriter = new JsonWriter();
        String partialJsonfile = configure.getAnalyzedProjectName() + "_tracecall_dep.json";
        jsonWriter.toJson(partialJDepObject, partialJsonfile);
        System.out.println("Export " + partialJsonfile);

    }

}
