package client;

import util.Configure;

import static java.lang.System.exit;


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
        experiment.experimentWorkflow();
        if(lang.equals(Configure.EXTERNAL_DATA_SOURCE)) {
            experiment.generateTraceClassCallsForExperiments();
        }
    }


    private static void checkInput(String[] args) {
        System.out.println("\ninput parameters:" + "srcDir usageDir projectName deps=[111111111]");
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

