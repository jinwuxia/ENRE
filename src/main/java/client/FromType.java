package client;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class FromType {
    static String typeExtractor = "E:\\TypeExtractor\\main.py";
    static String cvs2json = "E:\\TypeExtractor\\csv2json.py";
    static void workflow(String [] args) throws IOException {

        final String out_dir = args[5];
        final String proj_dir = args[2];
        final String stub_dir = args[3];
        System.out.println("");
        File proj = new File(proj_dir);
        if(proj.exists()){
            String merged = merge(proj_dir,stub_dir);
            String proj_path = proj.getAbsolutePath();
            ProcessBuilder builder = new ProcessBuilder(
                    "python",
                    typeExtractor,
                    "-o",
                    out_dir,
                    merged,
                    proj_path
            );
            builder.redirectErrorStream(true);
            Process process = builder.start();
            BufferedReader r = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while (true) {
                line = r.readLine();
                if (line == null) { break; }
                System.out.println(line);
            }
        }
        else{

        }
        toJson(proj, out_dir);
    }

    private static void toJson(File proj, String out_dir) throws IOException {

        ProcessBuilder builder = new ProcessBuilder(
                "python",
                cvs2json,
                proj.getAbsolutePath(),
                out_dir,
                "result-of-"+proj.getName()+".json"
        );
        builder.start();
    }

    private static String merge(String proj_dir, String stub_dir) throws IOException{
        ProcessBuilder builder = new ProcessBuilder(
                "python",
                typeExtractor,
                stub_dir,
                proj_dir,
                "--merge"
        );
        File f = new File(proj_dir);
        builder.redirectErrorStream(true);
        Process process = builder.start();
        BufferedReader r = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        while (true) {
            line = r.readLine();
            if (line == null) { break; }
            System.out.println(line);
        }
        return f.getName();
    }
}
