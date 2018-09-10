package multiparser.util;


public final class OsUtil {
    private static String osName= null;
    public static String getOsName() {
       if(osName == null) {
           osName = System.getProperty("os.name").toLowerCase();
       }
       return osName;
    }

    public static boolean isWindows() {
        return getOsName().startsWith(Configure.WINDOWS);
    }

    public static boolean isLinux() {
        return getOsName().startsWith(Configure.LINUX);
    }

    public static boolean isMac() {
        return getOsName().startsWith(Configure.MAC);
    }

}
