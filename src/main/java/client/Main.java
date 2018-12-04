package client;

import util.Configure;

import static java.lang.System.exit;


public class Main {

    public static void main(String[] args) {
        //System.out.println("\ninput parameters:" + "srcDir usageDir projectName deps=[111111111]");
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

        TemplateWork templateWork = new TemplateWork();
        //long startTime = System.currentTimeMillis();

        templateWork.workflow(args);



        //long endTime = System.currentTimeMillis();
        //System.out.println("Consumed time: " + (float) ((endTime-startTime)/1000.00) + " s,  or " + (float) ((endTime-startTime)/60000.00) + " min." );
    }
}

