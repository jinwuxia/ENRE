package util;

public final class StringUtil {

    /**
     * for a string with /, delete the last substr, return the prefix.
     * @param str "a/b/c"
     * @return   a/b
     */
    public static String deleteLastStrByPathDelimiter(String str) {
        String substr;
        String[] tmp = str.split("/");
        substr = tmp[0];
        for (int i = 1; i < tmp.length -1; i++) {
            substr += "/";
            substr += tmp[i];
        }
        return substr;
    }

    /**
     * get last substr, splittin by ""/
     * @param str a/b/c
     * @return    c
     */
    public static String getLastStrByPathDelimiter(String str) {
        String [] arr = str.split("/");
        return arr[arr.length - 1];
    }

    /**
     * unify filepath into a unified represenation "/"
     * all path in the code are unified "/"
     * @param path   "a/b/c" or "a\b\c"
     * @return   "a/b/c"
     */
    public static String unifyPath(String path) {
        if (path.contains("\\")) {
            String [] tmp = path.split("\\\\");
            return String.join("/", tmp);
        }
        return path;
    }
}
