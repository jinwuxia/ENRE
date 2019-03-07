package client;

import priextractor.py3extractor.newdeper.implicitstatistic.OutputStatistic;
import formator.Formator;
import formator.fjson.JDepObject;
import priextractor.py3extractor.newdeper.implicitstatistic.StatisticMember;
import writer.JsonWriter;
import writer.UndWriter;
import util.Configure;

import java.util.ArrayList;
import java.util.List;


/**
 * it should be template method pattern, so that the steps and oder of processing are fixed.
 */
public class Experiment {

    protected static Configure configure = Configure.getConfigureInstance();

    public void experimentWorkflow() {
        StatisticMember statisticMember = new StatisticMember();
        OutputStatistic outputStatistic = new OutputStatistic(statisticMember);
        outputStatistic.doSummary();


        //P1, P1+P2, P1+p2+p3, ...., Pall for all atom dependencies
        //generateAccumlatedDepByCategory();

        //generate implicit P1, implicit p2, implicit p3, ......P11
        generateImplicitPx();

        //generate all explicit dependency
        generateAllExplicit();

        //atom exp + atom p1
        //generateAtomDepAtFunctionLevel();

        //generateUnderstandFormatsForExperiments();

    }


    private void generateAtomDepAtFunctionLevel() {
        String[] all_dep_exp = new String[] {Configure.RELATION_ATOM_EXPLICIT,  Configure.RELATION_ATOM_IMPLICIT_P1};
        String expJsonFile = configure.getAnalyzedProjectName() + "_enre_atom_exp_p1_function_call.json";
        String level = Configure.RELATION_LEVEL_FUNCTION;
        outDepToJsonFile(expJsonFile, all_dep_exp, level);

    }

    /** in the future, substiutate exp into set, use, dot,...
     *
     */
    private void generateAllExplicit() {
        //export all explicit dependencies
        String[] all_dep_exp = new String[] {Configure.RELATION_ATOM_EXPLICIT,
                Configure.RELATION_EXTEND, Configure.RELATION_INHERIT,
                Configure.RELATION_IMPORT};
        String expJsonFile = configure.getAnalyzedProjectName() + "_enre_allExplicitDep.json";
        String level = Configure.RELATION_LEVEL_FILE;
        outDepToJsonFile(expJsonFile, all_dep_exp, level);

    }

    private void generateImplicitPx() {
        String[] deps1 = new String[] {Configure.RELATION_ATOM_IMPLICIT_P1};
        String[] deps2 = new String[] {Configure.RELATION_ATOM_IMPLICIT_P2};
        String[] deps3 = new String[] {Configure.RELATION_ATOM_IMPLICIT_P3};
        String[] deps4 = new String[] { Configure.RELATION_ATOM_IMPLICIT_P4};
        String[] deps5 = new String[] {Configure.RELATION_ATOM_IMPLICIT_P5};
        String[] deps6 = new String[] {Configure.RELATION_ATOM_IMPLICIT_P6};
        String[] deps7 = new String[] {Configure.RELATION_ATOM_IMPLICIT_P7};
        String[] deps8 = new String[] {Configure.RELATION_ATOM_IMPLICIT_P8};
        String[] deps9 = new String[] {Configure.RELATION_ATOM_IMPLICIT_P9};
        String[] deps10 = new String[] {Configure.RELATION_ATOM_IMPLICIT_P10};
        String[] deps11 = new String[] {Configure.RELATION_ATOM_IMPLICIT_P11};

        List<String[]> depsList = new ArrayList<>();
        depsList.add(deps1);
        depsList.add(deps2);
        depsList.add(deps3);
        depsList.add(deps4);
        depsList.add(deps5);
        depsList.add(deps6);
        depsList.add(deps7);
        depsList.add(deps8);
        depsList.add(deps9);
        depsList.add(deps10);
        depsList.add(deps11);

        //export  implicit deps at file level
        for (int i = 0; i < depsList.size(); i++) {
            String[] depType = depsList.get(i);
            String partialJsonfile = configure.getAnalyzedProjectName() + "_enreImplicitDep_P" + Integer.toString(i+1) + ".json";
            String level = Configure.RELATION_LEVEL_FILE;
            outDepToJsonFile(partialJsonfile, depType, level);
        }

    }




    private void outDepToJsonFile(String depFile, String[] depType, String level) {
        Formator partialFormator = new Formator(depType, level);
        JDepObject partialJDepObject = partialFormator.getfJsonDataModel();
        JsonWriter jsonWriter = new JsonWriter();
        jsonWriter.toJson(partialJDepObject, depFile);
        System.out.println("Export " + depFile);
    }




    private void generateAccumlatedDepByCategory() {
        String[] deps1 = new String[] {Configure.RELATION_ATOM_IMPLICIT_P1};
        String[] deps2 = new String[] {Configure.RELATION_ATOM_IMPLICIT_P1, Configure.RELATION_ATOM_IMPLICIT_P2};
        String[] deps3 = new String[] {Configure.RELATION_ATOM_IMPLICIT_P1, Configure.RELATION_ATOM_IMPLICIT_P2, Configure.RELATION_ATOM_IMPLICIT_P3};
        String[] deps4 = new String[] {Configure.RELATION_ATOM_IMPLICIT_P1, Configure.RELATION_ATOM_IMPLICIT_P2, Configure.RELATION_ATOM_IMPLICIT_P3, Configure.RELATION_ATOM_IMPLICIT_P4};
        String[] deps5 = new String[] {Configure.RELATION_ATOM_IMPLICIT_P1, Configure.RELATION_ATOM_IMPLICIT_P2, Configure.RELATION_ATOM_IMPLICIT_P3, Configure.RELATION_ATOM_IMPLICIT_P4, Configure.RELATION_ATOM_IMPLICIT_P5};
        String[] deps6 = new String[] {Configure.RELATION_ATOM_IMPLICIT_P1, Configure.RELATION_ATOM_IMPLICIT_P2, Configure.RELATION_ATOM_IMPLICIT_P3, Configure.RELATION_ATOM_IMPLICIT_P4, Configure.RELATION_ATOM_IMPLICIT_P5, Configure.RELATION_ATOM_IMPLICIT_P6};
        String[] deps7 = new String[] {Configure.RELATION_ATOM_IMPLICIT_P1, Configure.RELATION_ATOM_IMPLICIT_P2, Configure.RELATION_ATOM_IMPLICIT_P3, Configure.RELATION_ATOM_IMPLICIT_P4, Configure.RELATION_ATOM_IMPLICIT_P5, Configure.RELATION_ATOM_IMPLICIT_P6, Configure.RELATION_ATOM_IMPLICIT_P7};
        String[] deps8 = new String[] {Configure.RELATION_ATOM_IMPLICIT_P1, Configure.RELATION_ATOM_IMPLICIT_P2, Configure.RELATION_ATOM_IMPLICIT_P3, Configure.RELATION_ATOM_IMPLICIT_P4, Configure.RELATION_ATOM_IMPLICIT_P5, Configure.RELATION_ATOM_IMPLICIT_P6,Configure.RELATION_ATOM_IMPLICIT_P7, Configure.RELATION_ATOM_IMPLICIT_P8};
        String[] deps9 = new String[] {Configure.RELATION_ATOM_IMPLICIT_P1, Configure.RELATION_ATOM_IMPLICIT_P2, Configure.RELATION_ATOM_IMPLICIT_P3, Configure.RELATION_ATOM_IMPLICIT_P4, Configure.RELATION_ATOM_IMPLICIT_P5, Configure.RELATION_ATOM_IMPLICIT_P6, Configure.RELATION_ATOM_IMPLICIT_P7, Configure.RELATION_ATOM_IMPLICIT_P8, Configure.RELATION_ATOM_IMPLICIT_P9};
        String[] deps10 = new String[] {Configure.RELATION_ATOM_IMPLICIT_P1, Configure.RELATION_ATOM_IMPLICIT_P2, Configure.RELATION_ATOM_IMPLICIT_P3, Configure.RELATION_ATOM_IMPLICIT_P4, Configure.RELATION_ATOM_IMPLICIT_P5, Configure.RELATION_ATOM_IMPLICIT_P6, Configure.RELATION_ATOM_IMPLICIT_P7, Configure.RELATION_ATOM_IMPLICIT_P8,Configure.RELATION_ATOM_IMPLICIT_P9,Configure.RELATION_ATOM_IMPLICIT_P10};
        String[] deps11 = new String[] {Configure.RELATION_ATOM_IMPLICIT_P1, Configure.RELATION_ATOM_IMPLICIT_P2, Configure.RELATION_ATOM_IMPLICIT_P3, Configure.RELATION_ATOM_IMPLICIT_P4, Configure.RELATION_ATOM_IMPLICIT_P5, Configure.RELATION_ATOM_IMPLICIT_P6, Configure.RELATION_ATOM_IMPLICIT_P7, Configure.RELATION_ATOM_IMPLICIT_P8,Configure.RELATION_ATOM_IMPLICIT_P9,Configure.RELATION_ATOM_IMPLICIT_P10, Configure.RELATION_ATOM_IMPLICIT_P11};

        List<String[]> depsList = new ArrayList<>();
        depsList.add(deps1);
        depsList.add(deps2);
        depsList.add(deps3);
        depsList.add(deps4);
        depsList.add(deps5);
        depsList.add(deps6);
        depsList.add(deps7);
        depsList.add(deps8);
        depsList.add(deps9);
        depsList.add(deps10);
        depsList.add(deps11);

        String level = Configure.RELATION_LEVEL_FILE;
        //export external implicit calls at file level
        for (int i = 0; i < depsList.size(); i++) {
            String[] depType = depsList.get(i);
            Formator partialFormator = new Formator(depType, level);
            JDepObject partialJDepObject = partialFormator.getfJsonDataModel();
            JsonWriter jsonWriter = new JsonWriter();
            String partialJsonfile = configure.getAnalyzedProjectName() + "_implicit_dep_" + Integer.toString(i+1) + ".json";
            jsonWriter.toJson(partialJDepObject, partialJsonfile);
            System.out.println("Export " + partialJsonfile);
        }
    }


    //export formats consistent with understand, to compare with understand tool
    private void generateUnderstandFormatsForExperiments() {
        UndWriter undWriter = new UndWriter();
        undWriter.writeUnd();
    }


    public void generateTraceClassCallsForExperiments() {
        String[] partialDepType = new String[]{Configure.RELATION_DYNAMIC_TRACE_CLASS_CALL};
        Formator partialFormator = new Formator(partialDepType, Configure.RELATION_LEVEL_FILE);
        JDepObject partialJDepObject = partialFormator.getfJsonDataModel();
        JsonWriter jsonWriter = new JsonWriter();
        String partialJsonfile = configure.getAnalyzedProjectName() + "_tracecall_dep.json";
        jsonWriter.toJson(partialJDepObject, partialJsonfile);
        System.out.println("Export " + partialJsonfile);

    }

}
