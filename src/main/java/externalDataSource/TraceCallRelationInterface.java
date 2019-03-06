package externalDataSource;

import util.Configure;
import util.RelationInterface;
import util.Tuple;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class TraceCallRelationInterface extends RelationInterface {
    private Configure configure = Configure.getConfigureInstance();

    //files which contains a class / file list.
    private String entityfilename = configure.getInputSrcPath();

    //file which contains class-class /file-file dep list.
    private String depfilename = configure.getUsageSrcPath();

    /**
     * implement our getAllNodes, the data not from uerr, it is from outside workflow like.
     * @return
     */
    @Override
    public ArrayList<String> getAllNodes(String level) {
        ArrayList<String> classList = new ArrayList<>();

        String classname = Configure.NULL_STRING;
        try(BufferedReader br = new BufferedReader(new FileReader(entityfilename))) {
            while((classname = br.readLine()) != null) {
                classList.add(classname);
                //System.out.println(classname);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("end class list");
        return classList;
    }


    /**
     * implement our getDepByType. the data not from uerr, it is from outside workflow like.
     * @param level
     * @param depType
     * @return
     */
    @Override
    public ArrayList<Tuple<String, String>> getDepByType(String level, String depType) {
        ArrayList<Tuple<String, String>> deps = new ArrayList<>();

        String line = Configure.NULL_STRING;
        try(BufferedReader br = new BufferedReader(new FileReader(depfilename))) {
            while((line = br.readLine()) != null) {
                System.out.println("line= " + line);
                String[] arr = line.split(Configure.COMMA);
                //System.out.println( Integer.toString(arr.length) + " " + arr.toString());
                deps.add(new Tuple<String, String>(arr[0], arr[1]));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return deps;
    }



    @Override
    public String entityStatis() {
        return null;
    }

    @Override
    public String dependencyStatis() {
        return null;
    }


    @Override
    public ArrayList<Tuple<String, String>> getImportDeps(String level) {
        return null;
    }

    @Override
    public ArrayList<Tuple<String, String>> getImplementDeps(String level) {
        return null;
    }

    @Override
    public ArrayList<Tuple<String, String>> getInheritDeps(String level) {
        return null;
    }

    @Override
    public ArrayList<Tuple<String, String>> getFunctionUses(String level) {
        return null;
    }

    @Override
    public ArrayList<Tuple<String, String>> getFunctionSets(String level) {
        return null;
    }

    @Override
    public ArrayList<Tuple<String, String>> getFunctionRets(String level) {
        return null;
    }

    @Override
    public ArrayList<Tuple<String, String>> getFunctionParas(String level) {
        return null;
    }

    @Override
    public ArrayList<Tuple<String, String>> getFunctionCalls(String level) {
        return null;
    }

    @Override
    public ArrayList<Tuple<String, String>> getDepByCategory(String level, String deptype) {
        return null;
    }
}
