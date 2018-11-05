package writer;

import uerr.AbsEntity;
import uerr.SingleCollect;
import util.Configure;
import util.Tuple;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ImplicitCallWriter {
    private SingleCollect singleCollect = SingleCollect.getSingleCollectInstance();

    public void writeImplicitCalls() {
        CsvWriter writer = new CsvWriter();
        Configure configure = Configure.getConfigureInstance();

        String fileName = configure.getAnalyzedProjectName() +"implicit_external_file_deps.csv";
        Map<String, Map<String, Integer>> depmap = getImpFileDepMap();
        List<String[]> depList = getImpFileDepList(depmap);
        writer.writeCsv(depList, fileName);
    }

    /**
     * output: [deptype, file1, file2, weight]
     * @return
     */
    private Map<String, Map<String, Integer>> getImpFileDepMap() {
        Map<String, Map<String, Integer>> mapMap = new HashMap<String, Map<String, Integer>>();
        for(AbsEntity entity : singleCollect.getEntities()) {
            int id1 = entity.getId();
            String fileName1 = getContainFile(id1);
            for (Tuple<String, Integer> relation : entity.getRelations()) {
                int id2 = relation.y;
                String deptype = relation.x;
                if(deptype.equals(Configure.RELATION_IMPLICIT_EXTERNAL_CALL)) {
                    String fileName2 = getContainFile(id2);
                    if(fileName1.equals(Configure.NULL_STRING)
                            || fileName2.equals(Configure.NULL_STRING)) {
                        continue;
                    }
                    if(!mapMap.containsKey(fileName1)) {
                        mapMap.put(fileName1, new HashMap<String, Integer>());
                    }
                    if(!mapMap.get(fileName1).containsKey(fileName2)) {
                        mapMap.get(fileName1).put(fileName2, 0);
                    }
                    int oldWeight = mapMap.get(fileName1).get(fileName2);
                    mapMap.get(fileName1).put(fileName2, oldWeight + 1);
                }
            }
        }
        return mapMap;
    }

    /**
     * transform depmap into list
     * @param depMap
     * @return
     */
    private List<String[]> getImpFileDepList(Map<String, Map<String, Integer>> depMap) {
        List<String[]> depList = new ArrayList<String[]>();
        for (Map.Entry<String, Map<String, Integer>> entry : depMap.entrySet()) {
            String filename1 = entry.getKey();
            for (Map.Entry<String, Integer> entry2 : entry.getValue().entrySet()) {
                String filename2 = entry2.getKey();
                String weight = Integer.toString(entry2.getValue());
                depList.add(new String[]{filename1, filename2, weight});
            }
        }
        return depList;
    }


    private String getContainFile(int id) {
        while (id != -1) {
            if (singleCollect.isFile(id)) {
                return singleCollect.getEntities().get(id).getName();
            }
            else {
                id = singleCollect.getEntities().get(id).getParentId();
            }
        }
        return "";
    }
}


