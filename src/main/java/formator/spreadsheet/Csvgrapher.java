package formator.spreadsheet;

import java.util.ArrayList;
import java.util.Map;

public class Csvgrapher {
    private ArrayList<String[]> nodes = new ArrayList<String[]>();
    private ArrayList<String[]> edges = new ArrayList<String[]>();

    public void buildProcess(Map<Integer, String[]> entities,
                             Map<Integer, Map<Integer, Map<String, Map<String, Integer>>>> deps) {
        processNodes(entities);
        processDeps(deps);

    }

    //id, name, type, parent
    private void processNodes(Map<Integer, String[]> entities) {
        nodes.add(new String[] {"Id", "Label", "Type", "Parent"});
        for (Map.Entry<Integer, String[]> entry : entities.entrySet()) {
            int id = entry.getKey();
            String[] attrs = entry.getValue();
            String[] arr = new String[]{Integer.toString(id), attrs[0], attrs[1], attrs[2]};
            nodes.add(arr);
        }
    }

    //src, dst, deptype, primitivetype, weight
    private void processDeps(Map<Integer, Map<Integer, Map<String, Map<String, Integer>>>> deps) {
        edges.add(new String[]{"Source", "Target", "Type", "PriType", "Weight"});
        for (Map.Entry<Integer, Map<Integer, Map<String, Map<String, Integer>>>> entry1 : deps.entrySet()) {
            int src = entry1.getKey();
            for (Map.Entry<Integer, Map<String, Map<String, Integer>>> entry2 : entry1.getValue().entrySet()) {
                int dst = entry2.getKey();
                for (Map.Entry<String, Map<String, Integer>> entry3 : entry2.getValue().entrySet()) {
                    String depType = entry3.getKey();
                    for (Map.Entry<String, Integer> entry4 : entry3.getValue().entrySet()) {
                        String primitiveType = entry4.getKey();
                        int weight = entry4.getValue();
                        String arr[] = new String[]{Integer.toString(src),
                                Integer.toString(dst), depType, primitiveType, Integer.toString(weight)};
                        edges.add(arr);
                    }
                }
            }
        }
    }


    public ArrayList<String[]> getNodes() {
        return nodes;
    }

    public ArrayList<String[]> getEdges() {
        return edges;
    }
}


