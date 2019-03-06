package uerr;

import util.Tuple;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DependCollect {
    //{deptype, id1}= list[ id2]
    private Map<String, List<Tuple<Integer, Integer>>> depends = new HashMap<>();
    private static DependCollect dependCollectInstance = new DependCollect();

    private DependCollect(){}

    public static DependCollect getInstance() {
        return dependCollectInstance;
    }

    public void addOnedep(String type, int id1, int id2) {
        if(!this.depends.containsKey(type)) {
            this.depends.put(type, new ArrayList<>());
        }
        this.depends.get(type).add(new Tuple<>(id1, id2));
    }

    public Map<String, List<Tuple<Integer, Integer>>> getDepends() {
        return depends;
    }

    /**
     * note: if there is X expressionAtom is P10, then the depends will have X.10 possible dependencies.
     */
    public void printDependCollect() {
        for (Map.Entry<String, List<Tuple<Integer, Integer>>> entry: depends.entrySet()) {
            String key= entry.getKey();
            int len = entry.getValue().size();
            System.out.println(key + ", " + Integer.toString(len));
        }
    }
}
