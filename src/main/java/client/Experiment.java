package client;

import expression.ExpressionAtom;
import expression.ExpressionCollect;
import priextractor.py3extractor.PyRelationInf;
import priextractor.py3extractor.newdeper.implicitstatistic.OutputStatistic;
import formator.Formator;
import formator.fjson.JDepObject;
import priextractor.py3extractor.newdeper.implicitstatistic.StatisticMember;
import uerr.AbsEntity;
import uerr.SingleCollect;
import util.Tuple;
import writer.CsvWriter;
import writer.JsonWriter;
import writer.UndWriter;
import util.Configure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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

        // all function calls has been saved into entity,
        // export all explciit and implicit function calls
        generateAllFunctionCall();

        //generateUnderstandFormatsForExperiments();

        generateAtomResolvings();

        generateP1AsCSV();
    }


    private void generateAllFunctionCall() {
        String[] all_dep_exp = new String[] {Configure.RELATION_CALL};
        String expJsonFile = configure.getAnalyzedProjectName() + "_allExpImp_call.json";
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
        String expJsonFile = configure.getAnalyzedProjectName() + "_explicitDep.json";
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
            String partialJsonfile = configure.getAnalyzedProjectName() + "_implicitDep_P" + Integer.toString(i+1) + ".json";
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

    private void generateAtomResolvings() {
        List<String[]> res = new ArrayList<>();
        SingleCollect singleCollect = SingleCollect.getSingleCollectInstance();
        ExpressionCollect expressionCollect = ExpressionCollect.getExpressionCollect();
        for (AbsEntity entity1 : singleCollect.getEntities()) {
            int entityId1 = entity1.getId();
            String entityName1 = singleCollect.getLongName(entityId1);
            int containerId = entity1.getExpContainerId();
            if (containerId == -1) {
                continue;
            }
            List<ExpressionAtom> atoms = expressionCollect.getContainerById(containerId).getExpressionAtomList();
            for (ExpressionAtom atom : atoms) {
                if(atom.getResolvedManner().startsWith(Configure.RESOLVED_TYPE_IMPLICIT)
                    && !atom.getBindIdList().isEmpty() && atom.getBindIdList().size() == 1) {
                    int entityId2 = atom.getBindIdList().get(0);
                    String entityName2 = singleCollect.getLongName(entityId2);
                    res.add(new String[]{entityName1.replaceAll(",", ";"), atom.getStr().replaceAll(",", ";"), entityName2.replaceAll(",", ";")});

                }
            }
        }
        CsvWriter csvWriter = new CsvWriter();
        csvWriter.writeCsv(res, configure.getAnalyzedProjectName() + "_atomDetail.csv");
    }



    private void generateP1AsCSV() {
        class Result {
            Result(String type, int weight) {
                this.type = type;
                this.weight = weight;
            }
            String type;
            int weight;
        }

        String level = Configure.RELATION_LEVEL_FILE;
        PyRelationInf relationInf = new PyRelationInf();
        //List<String> files = relationInf.getAllNodes(level);
        ArrayList<Tuple<String, String>> deps = relationInf.getDepByType(level, Configure.RELATION_ATOM_IMPLICIT_P1);
        Map<String, Map<String, Result>> mapMap = new HashMap<>();
        for (Tuple<String, String> dep : deps) {
            String file1 = dep.x;
            String file2 = dep.y;
            String isCross = "cross-file";
            if (file1.equals(file2)) {
                isCross = "inside-file";
            }
            if(!mapMap.containsKey(file1)) {
                mapMap.put(file1, new HashMap<>());
            }
            if(!mapMap.get(file1).containsKey(file2)) {
                mapMap.get(file1).put(file2, new Result(isCross, 0));
            }
            int oldWeight = mapMap.get(file1).get(file2).weight;
            mapMap.get(file1).put(file2, new Result(isCross, oldWeight + 1));
        }

        List<String[]> resList = new ArrayList<>();
        for (Map.Entry<String, Map<String, Result>> entry : mapMap.entrySet()) {
            String file1 = entry.getKey();
            for (Map.Entry<String, Result> entry2 : entry.getValue().entrySet()) {
                String file2 = entry2.getKey();
                String type = entry2.getValue().type;
                int weight = entry2.getValue().weight;
                resList.add(new String[] {file1, file2, type, Integer.toString(weight)});
            }
        }

        CsvWriter csvWriter = new CsvWriter();
        csvWriter.writeCsv(resList, configure.getAnalyzedProjectName() + "_implicit_dep_P1.csv");

    }




}
