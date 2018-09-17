package hidepanalyzer;

import util.Configure;

import java.util.HashMap;
import java.util.Map;

public class HiDepData {
    private static HiDepData ourInstance = new HiDepData();
    private Map<Integer, Map<Integer, Map<String, Map<String, Integer>>>> funcDeps = new HashMap<Integer, Map<Integer, Map<String, Map<String, Integer>>>>();
    private Map<Integer, Map<Integer, Map<String, Map<String, Integer>>>> classDeps = new HashMap<Integer, Map<Integer, Map<String, Map<String, Integer>>>>();
    private Map<Integer, Map<Integer, Map<String, Map<String, Integer>>>> fileDeps = new HashMap<Integer, Map<Integer, Map<String, Map<String, Integer>>>>();
    private Map<Integer, Map<Integer, Map<String, Map<String, Integer>>>> folderDeps = new HashMap<Integer, Map<Integer, Map<String, Map<String, Integer>>>>();


    public static HiDepData getInstance() {
        return ourInstance;
    }

    private HiDepData() {
    }

    public void addDep(String entityType, int id1, int id2, String depType, String primitiveType, int weight) {
        if(entityType.equals(Configure.BASIC_ENTITY_FUNCTION)) {
            addOperation(funcDeps, id1, id2, depType, primitiveType, weight);
        }
        else if (entityType.equals(Configure.BASIC_ENTITY_CLASS)) {
            addOperation(classDeps, id1, id2, depType, primitiveType, weight);
        }
        else if (entityType.equals(Configure.BASIC_ENTITY_FILE)) {
            addOperation(fileDeps, id1, id2, depType, primitiveType, weight);
        }
        else if (entityType.equals(Configure.BASIC_ENTITY_FOLDER)) {
            addOperation(folderDeps, id1, id2, depType, primitiveType, weight);
        }

    }

    private void addOperation(Map<Integer, Map<Integer, Map<String, Map<String, Integer>>>> deps,
                              int id1, int id2, String depType, String primitiveType, int weight) {
        if(!deps.containsKey(id1)) {
           deps.put(id1, new HashMap<Integer, Map<String, Map<String, Integer>>>());
        }
        if(!deps.get(id1).containsKey(id2)) {
            deps.get(id1).put(id2, new HashMap<String, Map<String, Integer>>());
        }
        if(!deps.get(id1).get(id2).containsKey(depType)) {
            deps.get(id1).get(id2).put(depType, new HashMap<String, Integer>());
        }
        if(!deps.get(id1).get(id2).get(depType).containsKey(primitiveType)) {
            deps.get(id1).get(id2).get(depType).put(primitiveType, 0);
        }
        int oldweight = deps.get(id1).get(id2).get(depType).get(primitiveType);
        deps.get(id1).get(id2).get(depType).put(primitiveType, weight + oldweight);
    }

    public Map<Integer, Map<Integer, Map<String, Map<String, Integer>>>> getFuncDeps() {
        return funcDeps;
    }

    public Map<Integer, Map<Integer, Map<String, Map<String, Integer>>>> getClassDeps() {
        return classDeps;
    }

    public Map<Integer, Map<Integer, Map<String, Map<String, Integer>>>> getFileDeps() {
        return fileDeps;
    }

    public Map<Integer, Map<Integer, Map<String, Map<String, Integer>>>> getFolderDeps() {
        return folderDeps;
    }
}
