import entitydepanalyzer.AnayzerIntf;
import entitytreebuilder.BuilderIntf;
import formator.Formator;
import formator.fjson.JDepObject;
import formator.fxml.XDepObject;
import formator.spreadsheet.Csvgrapher;
import hidepanalyzer.HiDepData;
import hidepanalyzer.HiDeper;
import hidepwriter.WriterIntf;
import util.Configure;

import java.util.ArrayList;

public class TemplateWork {

    protected static Configure configure = Configure.getConfigureInstance();


    public final void workflow(String[] args) {
        String lang = args[0];
        String inputDir = args[1];
        String usageDir = args[2];
        String projectName = args[3];
        String depMask = args[4];

        config(lang, inputDir, usageDir, projectName);
        String[] depTypes = getDepType(depMask);

        //identify Entities
        BuilderIntf entityTreeBuilder = new BuilderIntf();
        entityTreeBuilder.run();

        //extract Deps
        AnayzerIntf entityDepAnalyzer = new AnayzerIntf();
        entityDepAnalyzer.run();

        //build hierarchical dependencies
        HiDeper hiDeper = new HiDeper();
        hiDeper.run();
        hiDeper.tmpOutput();
        HiDepData hiDepData = HiDepData.getInstance();

        Formator formator = new Formator(depTypes, hiDepData.getAllEntities(), hiDepData.getAllDeps());
        JDepObject jDepObject = formator.getfJsonDataModel();
        XDepObject xDepObject = formator.getfXmlDataModel();
        ArrayList<String[]> nodes = formator.getNodeModel();
        ArrayList<String[]> edges = formator.getEdgeModel();

        WriterIntf writer = new WriterIntf();
        writer.run(jDepObject, xDepObject, nodes, edges);

    }

    /**
     * parse the input parameter, save into configure
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
        for(int i = 0; i < depMask.toCharArray().length; i++) {
            if(depMask.toCharArray()[i] == '1') {
                if(i == 0) {
                    depStrs.add(Configure.RELATION_IMPORT);
                }
                else if (i == 1) {
                    depStrs.add(Configure.RELATION_INHERIT);
                }
                else if (i == 2) {
                    depStrs.add(Configure.RELATION_IMPLEMENT);
                }
                else if (i == 3) {
                    depStrs.add(Configure.RELATION_RECEIVE);
                }
                else if (i == 4) {
                    depStrs.add(Configure.RELATION_CALL);
                }
                else if (i == 5) {
                    depStrs.add(Configure.RELATION_SET);
                }
                else if (i == 6) {
                    depStrs.add(Configure.RELATION_USE);
                }
                else if (i == 7) {
                    depStrs.add(Configure.RELATION_PARAMETER);
                }
                else if (i == 8) {
                    depStrs.add(Configure.RELATION_RETURN);
                }
            }
        }
        String[] depStrArr = depStrs.toArray(new String[depStrs.size()]);
        return depStrArr;
    }



    public final void testRun() {
        //SingleCollect singleCollect = SingleCollect.getSingleCollectInstance();
        //for (AbsEntity udr : singleCollect.getEntities()) {
        //    System.out.println(udr);
        //}
        //FinalRelation relationOutput = new FinalRelation();

        //relationOutput.outputAllpackages();
        //relationOutput.outputAllModules();

        //relationOutput.outputAllClasses();
        //relationOutput.outputAllClassStaticMethods();
        //relationOutput.outputAllClassMethods();
        //relationOutput.outputAllInstMethods();


        //test vars
        //relationOutput.outputClassVarDetail();
        //relationOutput.outputGloVars();
        //relationOutput.outputAllFunctions();



        //relationOutput.outputAllFunctions();
        //relationOutput.outputAllClassVars();
        //relationOutput.outputAllInstVars();
        //relationOutput.outputAllLocOrGloVars();


        //test blocks
        //relationOutput.outputFunctions();
        //relationOutput.outputMethods();

        /*//test imports
        System.out.println("\nimports:");
        for (Tuple<String, String> dep: relationOutput.getImportDep("other")) {
            System.out.println(dep);
        }
        System.out.println("\nimports: file level");
        for (Tuple<String, String> dep: relationOutput.getImportDep(Configure.RELATION_LEVEL_FILE)) {
            System.out.println(dep);
        }

        //test embeded truct
        System.out.println("\nembed struct:");
        for (Tuple<String, String> dep: relationOutput.getEmbedStructDep("other")) {
            System.out.println(dep);
        }
        System.out.println("\nembed struct: file level");
        for (Tuple<String, String> dep: relationOutput.getEmbedStructDep(Configure.RELATION_LEVEL_FILE)) {
            System.out.println(dep);
        }


        // test embed interfaces
        System.out.println("\nembed interface:");
        for (Tuple<String, String> dep: relationOutput.getEmbedInterfaceDep("other")) {
            System.out.println(dep);
        }
        System.out.println("\nembed interface: file level");
        for (Tuple<String, String> dep: relationOutput.getEmbedInterfaceDep(Configure.RELATION_LEVEL_FILE)) {
            System.out.println(dep);
        }


        //test receiver
        System.out.println("\nreceiver:");
        for (Tuple<String, String> dep: relationOutput.getMethodReceiveDep("other")) {
            System.out.println(dep);
        }
        System.out.println("\nreceiver: file level");
        for (Tuple<String, String> dep: relationOutput.getMethodReceiveDep(Configure.RELATION_LEVEL_FILE)) {
            System.out.println(dep);
        }


        //test struct-interface implement
        System.out.println("\nimplementation:");
        for (Tuple<String, String> dep: relationOutput.getImplementDeps("other")) {
            System.out.println(dep);
        }
        System.out.println("\nimplementation: file level");
        for (Tuple<String, String> dep: relationOutput.getImplementDeps(Configure.RELATION_LEVEL_FILE)) {
            System.out.println(dep);
        }


        //test calls
        System.out.println("\nfunction call:");
        for (Tuple<String, String> dep: relationOutput.getFunctionCalls("other")) {
            System.out.println(dep);
        }
        System.out.println("\nfunction call: file level");
        for (Tuple<String, String> dep: relationOutput.getFunctionCalls(Configure.RELATION_LEVEL_FILE)) {
            System.out.println(dep);
        }


        //test sets
        System.out.println("\nfunction sets:");
        for (Tuple<String, String> dep: relationOutput.getFunctionSets("other")) {
            System.out.println(dep);
        }
        System.out.println("\nfunction sets: file level");
        for (Tuple<String, String> dep: relationOutput.getFunctionSets(Configure.RELATION_LEVEL_FILE)) {
            System.out.println(dep);
        }



        //test uses
        System.out.println("\nfunction uses:");
        for (Tuple<String, String> dep: relationOutput.getFunctionUses("other")) {
            System.out.println(dep);
        }
        System.out.println("\nfunction uses: file level");
        for (Tuple<String, String> dep: relationOutput.getFunctionUses(Configure.RELATION_LEVEL_FILE)) {
            System.out.println(dep);
        }



        //test Parameters
        System.out.println("\nfunction parameters:");
        for (Tuple<String, String> dep: relationOutput.getFunctionParas("other")) {
            System.out.println(dep);
        }
        System.out.println("\nfunction parameters: file level");
        for (Tuple<String, String> dep: relationOutput.getFunctionParas(Configure.RELATION_LEVEL_FILE)) {
            System.out.println(dep);
        }



        //test Returns
        System.out.println("\nfunction returns:");
        for (Tuple<String, String> dep: relationOutput.getFunctionRets("other")) {
            System.out.println(dep);
        }
        System.out.println("\nfunction returns: file level");
        for (Tuple<String, String> dep: relationOutput.getFunctionRets(Configure.RELATION_LEVEL_FILE)) {
            System.out.println(dep);
        }*/

    }
}
