package client;

import implicitstatistic.OutputStatistic;
import formator.Formator;
import formator.fjson.JDepObject;
import implicitstatistic.StatisticMember;
import writer.ImplicitCallWriter;
import writer.JsonWriter;
import writer.UndWriter;
import util.Configure;


/**
 * it should be template method pattern, so that the steps and oder of processing are fixed.
 */
public class Experiment {

    protected static Configure configure = Configure.getConfigureInstance();

    public void experimentWorkflow() {
        StatisticMember statisticMember = new StatisticMember();
        OutputStatistic outputStatistic = new OutputStatistic(statisticMember);
        outputStatistic.doSummary();

        generateImplicitExternalCallsForExperiments();

        generateUnderstandFormatsForExperiments();

    }


    //export formats consistent with understand, to compare with understand tool
    private void generateUnderstandFormatsForExperiments() {
        UndWriter undWriter = new UndWriter();
        undWriter.writeUnd();
    }

    /**
    export external  implicit calls at file level as csv file
    export external implicit calls at file level as json file
     */
    private void generateImplicitExternalCallsForExperiments() {
        //export external  implicit calls at file level as csv file
        ImplicitCallWriter implicitCallWriter = new ImplicitCallWriter();
        implicitCallWriter.writeImplicitCalls();

        //export external implicit calls at file level
        String[] partialDepType = new String[]{Configure.RELATION_IMPLICIT_EXTERNAL_CALL};
        Formator partialFormator = new Formator(partialDepType);
        JDepObject partialJDepObject = partialFormator.getfJsonDataModel();
        JsonWriter jsonWriter = new JsonWriter();
        String partialJsonfile = configure.getAnalyzedProjectName() + "_implicit_dep.json";
        jsonWriter.toJson(partialJDepObject, partialJsonfile);
        System.out.println("Export " + partialJsonfile);
    }

    public void generateTraceClassCallsForExperiments() {
        String[] partialDepType = new String[]{Configure.RELATION_DYNAMIC_TRACE_CLASS_CALL};
        Formator partialFormator = new Formator(partialDepType);
        JDepObject partialJDepObject = partialFormator.getfJsonDataModel();
        JsonWriter jsonWriter = new JsonWriter();
        String partialJsonfile = configure.getAnalyzedProjectName() + "_tracecall_dep.json";
        jsonWriter.toJson(partialJDepObject, partialJsonfile);
        System.out.println("Export " + partialJsonfile);

    }

}
