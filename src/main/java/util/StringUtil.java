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
}
