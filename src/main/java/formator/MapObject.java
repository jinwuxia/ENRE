package formator;

import externalDataSource.TraceCallRelationInterface;
import util.RelationInterface;
import priextractor.goextractor.GoRelationInf;
import priextractor.py3extractor.PyRelationInf;
import util.Configure;
import util.Tuple;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static java.lang.System.console;
import static java.lang.System.exit;

public class MapObject {
    private RelationInterface relationInterface;
    private ArrayList<String> nodes;
    private Map<Integer, Map<Integer, Map<String, Integer>>> finalRes = new HashMap<Integer, Map<Integer, Map<String, Integer>>>();

    public MapObject(String[] depStrs, String level) {

        init(level);  //init "nodes" and "relationInterface"
        buildDepMap(depStrs, level);  // read data from relationInterface and stores into "fileRes".

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

    private void init(String level) {
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
        nodes =  relationInterface.getAllNodes(level);
    }


    public ArrayList<String> getNodes() {
        return nodes;
    }


    public Map<Integer, Map<Integer, Map<String, Integer>>> getFinalRes() {
        return finalRes;
    }


    /**
     * build map from nodeName to new id
     * store into nodeName2Id.
     * @param
     */
    private Map<String, Integer> buildNodeMap() {
        Map<String, Integer> nodeName2Id = new HashMap<String, Integer>();
        int index = 0;
        for (String nodeName : nodes) {
            nodeName2Id.put(nodeName, index);
            index ++;
        }
        return nodeName2Id;
    }

    /**
     * build fileDeps into a map.
     * @param
     */
    private void buildDepMap(String[] depStrs, String level) {

        Map<String, Integer> nodeName2Id =  buildNodeMap();

        for (int i = 0; i < depStrs.length; i++) {
            String depType = depStrs[i];
            //System.out.println(depType);
            ArrayList<Tuple<String, String>> deps = relationInterface.getDepByType(level, depType);
            if (deps != null){
                addDepsInMap(deps, depType, nodeName2Id);
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
     * @param nodeName2Id
     */
    private void addDepsInMap(ArrayList<Tuple<String, String>> deps, String depType, Map<String, Integer> nodeName2Id) {
        for(Tuple<String, String> dep : deps) {
            String name1 = dep.x;
            String name2 = dep.y;
            int index1 = -1;
            int index2 = -1;
            if(nodeName2Id.containsKey(name1)) {
                index1 = nodeName2Id.get(name1);
            }
            if (nodeName2Id.containsKey(name2)) {
                index2 = nodeName2Id.get(name2);
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
