package com.ss.android.ttve.utils;

public class TEStringUtils {
    public static String getFilename(String str) {
        if (isEmpty(str)) {
            return null;
        }
        int lastIndexOf = str.lastIndexOf("/");
        return lastIndexOf == -1 ? str : str.substring(lastIndexOf + 1, str.length());
    }

    public static String getFilenameWithoutSuffix(String str) {
        if (isEmpty(str)) {
            return null;
        }
        int lastIndexOf = str.lastIndexOf("/") + 1;
        int lastIndexOf2 = str.lastIndexOf(".");
        return lastIndexOf2 == -1 ? str.substring(0, lastIndexOf - 1) : lastIndexOf2 > lastIndexOf ? str.substring(lastIndexOf, lastIndexOf2) : str;
    }

    public static String getFolderPath(String str) {
        if (isEmpty(str)) {
            return null;
        }
        int lastIndexOf = str.lastIndexOf("/");
        return lastIndexOf == -1 ? str : str.substring(0, lastIndexOf + 1);
    }

    public static boolean isEmpty(String str) {
        return str == null || str == "";
    }
}
