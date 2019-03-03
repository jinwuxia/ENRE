package priextractor.py3extractor.newdeper;

import java.util.*;

public  class SequencecUtil {
    /**
     * change list to set, and delete -1 from set.
     * @param alist
     * @return
     */
    public static Set<Integer> transformList(List<Integer> alist) {
        Set<Integer> aset = new HashSet<>(alist);
        aset.remove(-1);
        return aset;
    }

    /**
     * judge if two set is equal
     * @param set1
     * @param set2
     * @return
     */
    public static boolean isSetEqual(Set<Integer> set1, Set<Integer> set2) {
        if(set1.size() != set2.size()) {
            return false;
        }
        return set1.containsAll(set2);
    }

    /**
     *  transform set as the list, then sort, then to string.
     * @param aSet
     */
    public static String toOrderedString(Set<Integer> aSet) {
        Set<String> newSet = new HashSet<>();
        for (Integer id : aSet) {
            newSet.add(Integer.toString(id));
        }
        List<String> aList = new ArrayList<>(newSet);
        Collections.sort(aList);

        String res = String.join("_", aList);
        return res;
    }

}
