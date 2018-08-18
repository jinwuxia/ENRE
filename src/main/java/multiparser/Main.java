package multiparser;

import multiparser.py3extractor.PythonWork;
import multiparser.extractor.TemplateWork;
import multiparser.goextractor.GolangWork;
import multiparser.util.Configure;

import static java.lang.System.exit;


public class Main {

    public static void main(String[] args) {
        //System.out.println("\ninput parameters:" + "srcDir usageDir projectName deps=[111111111]");
        if(args.length < 4) {
            System.out.println("Not enough parameters!");
            exit(1);
        }
        TemplateWork worker = null;
        if (args[0].equals(Configure.GO_LANG)) {
            worker = new GolangWork();
        }
        else if(args[0].equals(Configure.PYTHON_LANG)) {
            worker = new PythonWork();
        }
        else {
            System.out.println("Not support this language: " + args[0]);
            exit(1);
        }

        long startTime = System.currentTimeMillis();
        worker.workflow(args);
        worker.testRun();
        long endTime = System.currentTimeMillis();
        System.out.println("Consumed time: " + (float) ((endTime-startTime)/1000.00) + " s,  or " + (float) ((endTime-startTime)/60000.00) + " min." );

    }

}
