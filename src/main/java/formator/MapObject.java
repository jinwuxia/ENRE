package formator;

import ExternalDataSource.TraceCallRelationInterface;
import util.RelationInterface;
import priextractor.goextractor.GoRelationInf;
import priextractor.py3extractor.PyRelationInf;
import util.Configure;
import util.Tuple;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static java.lang.System.exit;

public class MapObject {
    private RelationInterface relationInterface;
    private ArrayList<String> files;
    private Map<Integer, Map<Integer, Map<String, Integer>>> finalRes = new HashMap<Integer, Map<Integer, Map<String, Integer>>>();
    private String[] depStrs;

    public MapObject(String[] depStrs) {
        this.depStrs = depStrs; //init "depStrs"
        init();  //init "files" and "relationInterface"
        buildDepMap();  // read data from relationInterface and stores into "fileRes".

        //summarize the entity and dependency
        //System.out.println(relationInterface.entityStatis());
        //System.out.println(relationInterface.dependencyStatis());
        //System.out.println(depSummaryWithNoWeight());
    }

    /**
     * based on Map<Integer, Map<Integer, Map<String, Integer>>> finalRes
     */
    private String depSummaryWithNoWeight() {
        Map<String, Integer> res = new HashMap<String, Integer>();
        for(Map.Entry<Integer, Map<Integer, Map<String, Integer>>> entry1 : finalRes.entrySet()) {
            for(Map.Entry<Integer, Map<String, Integer>> entry2: entry1.getValue().entrySet()) {
                for (Map.Entry<String, Integer> entry3: entry2.getValue().entrySet()) {
                    String dep = entry3.getKey();
                    if(!res.containsKey(dep)) {
                        res.put(dep, 1);
                    }
                    res.put(dep, 1 + res.get(dep));
                }
            }
        }
        String str = "";
        for(Map.Entry<String, Integer> entry : res.entrySet()) {
            str += entry.getKey();
            str += ":           ";
            str += Integer.toString(entry.getValue());
            str += "\n";
        }
        return str;
    }

    private void init() {
        Configure configure = Configure.getConfigureInstance();
        if(configure.getLang().equals(Configure.GO_LANG)) {
            relationInterface = new GoRelationInf();
        }
        else if(configure.getLang().equals(Configure.PYTHON_LANG)) {
            relationInterface = new PyRelationInf();
        }
        else if(configure.getLang().equals(Configure.EXTERNAL_DATA_SOURCE)) {
            relationInterface = new TraceCallRelationInterface();
        }
        else {
            System.out.println("Not support this language!\n");
            exit(0);
        }
        files =  relationInterface.getAllFiles();
    }


    public ArrayList<String> getFiles() {
        return files;
    }

    public void setFiles(ArrayList<String> files) {
        this.files = files;
    }


    public String[] getDepStrs() {
        return depStrs;
    }

    public void setDepStrs(String[] depStrs) {
        this.depStrs = depStrs;
    }

    public Map<Integer, Map<Integer, Map<String, Integer>>> getFinalRes() {
        return finalRes;
    }

    public void setFinalRes(Map<Integer, Map<Integer, Map<String, Integer>>> finalRes) {
        this.finalRes = finalRes;
    }

    /**
     * build map from fileName to new id
     * store into fileName2Id.
     * @param
     */
    private Map<String, Integer> buildFileMap() {
        Map<String, Integer> fileName2Id = new HashMap<String, Integer>();
        int index = 0;
        for (String fileName : files) {
            fileName2Id.put(fileName, index);
            index ++;
        }
        return fileName2Id;
    }

    /**
     * build fileDeps into a map.
     * @param
     */
    private void buildDepMap() {

        Map<String, Integer> fileName2Id =  buildFileMap();

        for (int i = 0; i < depStrs.length; i++) {
            String depType = depStrs[i];
            //System.out.println(depType);
            ArrayList<Tuple<String, String>> deps = relationInterface.getDepByType(Configure.RELATION_LEVEL_FILE, depType);
            if (deps != null){
                addDepsInMap(deps, depType, fileName2Id);
                //System.out.println("dep not null: " + depType);
            }
            //else {
            //    System.out.println("dep is null: " + depType);
            //}
        }

    }

    /**
     *
     * @param deps
     * @param depType
     * @param fileName2Id
     */
    private void addDepsInMap(ArrayList<Tuple<String, String>> deps, String depType, Map<String, Integer> fileName2Id) {
        for(Tuple<String, String> dep : deps) {
            String name1 = dep.x;
            String name2 = dep.y;
            int index1 = -1;
            int index2 = -1;
            if(fileName2Id.containsKey(name1)) {
                index1 = fileName2Id.get(name1);
            }
            if (fileName2Id.containsKey(name2)) {
                index2 = fileName2Id.get(name2);
            }

            if(name1.equals(name2) || index1 == -1 || index2 == -1) {
                continue;
            }
            if(!finalRes.containsKey(index1)) {
                finalRes.put(index1, new HashMap<Integer, Map<String, Integer>>());
            }
            if(!finalRes.get(index1).containsKey(index2)) {
                finalRes.get(index1).put(index2, new HashMap<String, Integer>());
            }
            if(!finalRes.get(index1).get(index2).containsKey(depType)) {
                finalRes.get(index1).get(index2).put(depType, 0);
            }

            int newWeight = finalRes.get(index1).get(index2).get(depType) + 1;
            finalRes.get(index1).get(index2).put(depType, newWeight);

        }
    }
}
