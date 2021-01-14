package client;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class FromType {
    static void workflow(String [] args) throws IOException {

        final String out_dir = args[5];
        final String proj_dir = args[2];
        final String stub_dir = args[3];
        System.out.println("");
        File proj = new File(proj_dir);
        if(proj.exists()){
            String proj_path = proj.getAbsolutePath();
            ProcessBuilder builder = new ProcessBuilder(
                    "python",
                    "E:\\TypeExtractor\\main.py",
                    "-o",
                    out_dir,
                    proj_path,
                    stub_dir
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
    }
}
