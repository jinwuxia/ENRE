
import format.MapObject;
import format.json.JsonFormat;
import format.xml.XBuildObject;
import format.xml.XDepObject;
import format.xml.XmlFormat;
import format.json.JBuildObject;
import format.json.JDepObject;
import infer.TypeInfer;
import util.Configure;
import util.ConstantString;
import util.FileUtil;
import util.Tuple;
import visitor.FinalRelation;
import visitor.secondpass.BasicDepVisitor;
import visitor.secondpass.FuncDepVisitor;
import visitor.secondpass.MapInFun;

import java.io.IOException;
import java.util.ArrayList;

import static java.lang.System.exit;


public class Main {

    static Configure configure = Configure.getConfigureInstance();

    private static void process() throws IOException {
        FileUtil fileUtil = new FileUtil(configure.getInputSrcPath());
        for (String fileFullPath : fileUtil.getGoFileNameList()) {
            System.out.println(fileFullPath);
            FileParser fileParser = new FileParser(fileFullPath);
            fileParser.parserOneFile();
        }
        System.out.println("Tree travel end...");

        BasicDepVisitor basicDepVisitor = new BasicDepVisitor();
        MapInFun mapInFun = new MapInFun();
        FuncDepVisitor funcDepVisitor = new FuncDepVisitor();
        TypeInfer typeSearch = new TypeInfer();

        //imports, embeded, receiver relations
        basicDepVisitor.setUnsuredDeps();
        System.out.println("BasicDepVisitor built end...");
        // build map for operandName inside a function.
        mapInFun.buildNameSearchTable();
        System.out.println("NameSearchTable built end...");

        //infer type for all varEntities
        typeSearch.inferTypeForVarEntity();
        System.out.println("TypeInference end...");

        //typeSearch.output();
        // function Call, Set, Dep, Parameter, Return relations
        funcDepVisitor.setFuncDeps();
        System.out.println("FunctionDep built end...");

        System.out.println("Dep search end...");
    }

    private static void config(String[] args) {
        String inputDir = args[0];
        String usageDir = args[1];
        String projectName = args[2];
        configure.setInputSrcPath(inputDir);
        configure.setUsageSrcPath(usageDir);
        configure.setAnalyzedProjectName(projectName);
        configure.setDefault();
    }

    private static String[] getDepType(String depMask) {
        ArrayList<String> depStrs = new ArrayList<String>();
        for(int i = 0; i < depMask.toCharArray().length; i++) {
            if(depMask.toCharArray()[i] == '1') {
                if(i == 0) {
                    depStrs.add(ConstantString.RELATION_IMPORT);
                }
                else if (i == 1) {
                    depStrs.add(ConstantString.RELATION_EMBED);
                }
                else if (i == 2) {
                    depStrs.add(ConstantString.RELATION_IMPLEMENT);
                }
                else if (i == 3) {
                    depStrs.add(ConstantString.RELATION_RECEIVE);
                }
                else if (i == 4) {
                    depStrs.add(ConstantString.RELATION_CALL);
                }
                else if (i == 5) {
                    depStrs.add(ConstantString.RELATION_SET);
                }
                else if (i == 6) {
                    depStrs.add(ConstantString.RELATION_USE);
                }
                else if (i == 7) {
                    depStrs.add(ConstantString.RELATION_PARAMETER);
                }
                else if (i == 8) {
                    depStrs.add(ConstantString.RELATION_RETURN);
                }
            }
        }
        String[] depStrArr = depStrs.toArray(new String[depStrs.size()]);
        return depStrArr;
    }


    private static void genOutFile(String[] depTypes) {
        MapObject mapObject = new MapObject(depTypes);

        JBuildObject jBuildObject = new JBuildObject();
        JDepObject jDepObject = jBuildObject.buildObjectProcess(mapObject);
        JsonFormat jasonFormat = new JsonFormat();
        jasonFormat.toJson(jDepObject);

        XBuildObject xBuildObject = new XBuildObject();
        XDepObject xDepObject = xBuildObject.buildObjectProcess(mapObject);
        XmlFormat xmlFormat = new XmlFormat();
        xmlFormat.toXml(xDepObject);
    }


    public static  void testRun() {
        FinalRelation relationOutput = new FinalRelation();
        //relationOutput.outputAllEntities();

        //test blocks
        //relationOutput.outputFunctions();
        //relationOutput.outputMethods();

        /*//test imports
        System.out.println("\nimports:");
        for (Tuple<String, String> dep: relationOutput.getImportDep("other")) {
            System.out.println(dep);
        }
        System.out.println("\nimports: file level");
        for (Tuple<String, String> dep: relationOutput.getImportDep(ConstantString.RELATION_LEVEL_FILE)) {
            System.out.println(dep);
        }

        //test embeded truct
        System.out.println("\nembed struct:");
        for (Tuple<String, String> dep: relationOutput.getEmbedStructDep("other")) {
            System.out.println(dep);
        }
        System.out.println("\nembed struct: file level");
        for (Tuple<String, String> dep: relationOutput.getEmbedStructDep(ConstantString.RELATION_LEVEL_FILE)) {
            System.out.println(dep);
        }


        // test embed interfaces
        System.out.println("\nembed interface:");
        for (Tuple<String, String> dep: relationOutput.getEmbedInterfaceDep("other")) {
            System.out.println(dep);
        }
        System.out.println("\nembed interface: file level");
        for (Tuple<String, String> dep: relationOutput.getEmbedInterfaceDep(ConstantString.RELATION_LEVEL_FILE)) {
            System.out.println(dep);
        }


        //test receiver
        System.out.println("\nreceiver:");
        for (Tuple<String, String> dep: relationOutput.getMethodReceiveDep("other")) {
            System.out.println(dep);
        }
        System.out.println("\nreceiver: file level");
        for (Tuple<String, String> dep: relationOutput.getMethodReceiveDep(ConstantString.RELATION_LEVEL_FILE)) {
            System.out.println(dep);
        }


        //test struct-interface implement
        System.out.println("\nimplementation:");
        for (Tuple<String, String> dep: relationOutput.getImplementDeps("other")) {
            System.out.println(dep);
        }
        System.out.println("\nimplementation: file level");
        for (Tuple<String, String> dep: relationOutput.getImplementDeps(ConstantString.RELATION_LEVEL_FILE)) {
            System.out.println(dep);
        }


        //test calls
        System.out.println("\nfunction call:");
        for (Tuple<String, String> dep: relationOutput.getFunctionCalls("other")) {
            System.out.println(dep);
        }
        System.out.println("\nfunction call: file level");
        for (Tuple<String, String> dep: relationOutput.getFunctionCalls(ConstantString.RELATION_LEVEL_FILE)) {
            System.out.println(dep);
        }


        //test sets
        System.out.println("\nfunction sets:");
        for (Tuple<String, String> dep: relationOutput.getFunctionSets("other")) {
            System.out.println(dep);
        }
        System.out.println("\nfunction sets: file level");
        for (Tuple<String, String> dep: relationOutput.getFunctionSets(ConstantString.RELATION_LEVEL_FILE)) {
            System.out.println(dep);
        }



        //test uses
        System.out.println("\nfunction uses:");
        for (Tuple<String, String> dep: relationOutput.getFunctionUses("other")) {
            System.out.println(dep);
        }
        System.out.println("\nfunction uses: file level");
        for (Tuple<String, String> dep: relationOutput.getFunctionUses(ConstantString.RELATION_LEVEL_FILE)) {
            System.out.println(dep);
        }



        //test Parameters
        System.out.println("\nfunction parameters:");
        for (Tuple<String, String> dep: relationOutput.getFunctionParas("other")) {
            System.out.println(dep);
        }
        System.out.println("\nfunction parameters: file level");
        for (Tuple<String, String> dep: relationOutput.getFunctionParas(ConstantString.RELATION_LEVEL_FILE)) {
            System.out.println(dep);
        }



        //test Returns
        System.out.println("\nfunction returns:");
        for (Tuple<String, String> dep: relationOutput.getFunctionRets("other")) {
            System.out.println(dep);
        }
        System.out.println("\nfunction returns: file level");
        for (Tuple<String, String> dep: relationOutput.getFunctionRets(ConstantString.RELATION_LEVEL_FILE)) {
            System.out.println(dep);
        }*/

    }
    public static void main(String[] args) throws Exception{
        long startTime = System.currentTimeMillis();

        //System.out.println("\ninput parameters:" + "srcDir usageDir projectName deps=[111111111]");
        if(args.length < 4) {
            System.out.println("Not enough parameters!");
            exit(1);
        }
        config(args);
        String[] depTypes = getDepType(args[3]);
        process();
        genOutFile(depTypes);

        long endTime = System.currentTimeMillis();
        System.out.println("Export: " + configure.getOutputJsonFile());
        System.out.println("Export: " + configure.getOutputXmlFile());
        System.out.println("Consumed time: " + (float) ((endTime-startTime)/1000.00) + " s,  or " + (float) ((endTime-startTime)/60000.00) + " min." );

        testRun();


    }

}
