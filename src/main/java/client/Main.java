package client;

import util.Configure;

import static java.lang.System.exit;


/**
 *  cmd1:  extract relations from source code for python and golang
 *  java -jar *.jar  python/lang sourcedir usagedir projectname 111111111
 *
 *  cmd2: extract relations from input data source/external data source:
 *  java jar *.jar  datasource   entity.csv dep.csv
 *  it outputs the dependency in datasource into json format.
 */
public class Main {

    public static void main(String[] args) {
        //check args
        checkInput(args);

        //configure
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

        //start work for depend_parser
        if(!lang.equals(Configure.EXTERNAL_DATA_SOURCE)){
            DepWork depWork = new DepWork();
            depWork.deperWorkflow(depMask);
        }

        //start work for experiment
        Experiment experiment = new Experiment();
        if(lang.equals(Configure.EXTERNAL_DATA_SOURCE)) {
            experiment.generateTraceClassCallsForExperiments();
            exit(0);
        }
        experiment.experimentWorkflow();

    }


    private static void checkInput(String[] args) {
        //System.out.println("\ninput parameters:" + "srcDir usageDir projectName deps=[111111111]");
        System.out.println(
                "cmd1:  extract relations from source code for python and golang\n" +
                        "   java -jar *.jar  python/lang sourcedir usagedir projectname 111111111\n" +
                        " \n" +
                        "cmd2: extract relations from input data source/external data source:\n" +
                        "   java jar *.jar  datasource   entity.csv dep.csv\n" +
                        "   it outputs the dependency in datasource into json format.\n\n");
        if(args.length < 2) {
            System.out.println("Not enough parameters!");
            exit(1);
        }
        if(!args[0].equals(Configure.GO_LANG)
                && !args[0].equals(Configure.PYTHON_LANG)
                && !args[0].equals(Configure.EXTERNAL_DATA_SOURCE)) {
            System.out.println("Not support this language: " + args[0]);
            exit(1);
        }
    }


    /**
     * parse the input parameter, save into configure
     *
     * @param inputDir
     * @param usageDir
     * @param projectName
     */
    private static void config(String lang, String inputDir, String usageDir, String projectName) {
        Configure configure = Configure.getConfigureInstance();

        configure.setLang(lang);
        configure.setInputSrcPath(inputDir);
        configure.setUsageSrcPath(usageDir);
        configure.setAnalyzedProjectName(projectName);
        configure.setDefault();
    }

}

