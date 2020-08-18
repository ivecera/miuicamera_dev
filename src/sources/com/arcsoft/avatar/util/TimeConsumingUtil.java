package com.arcsoft.avatar.util;

import java.util.HashMap;

public class TimeConsumingUtil {
    public static boolean DEBUG = false;

    /* renamed from: a  reason: collision with root package name */
    private static HashMap<String, Long> f202a = new HashMap<>();

    /* renamed from: b  reason: collision with root package name */
    private static final String f203b = "PERFORMANCE";

    public static void startTheTimer(String str) {
        HashMap<String, Long> hashMap;
        if (DEBUG && (hashMap = f202a) != null) {
            hashMap.put(str, Long.valueOf(System.currentTimeMillis()));
        }
    }

    public static void stopTiming(String str) {
        HashMap<String, Long> hashMap;
        if (DEBUG && (hashMap = f202a) != null && hashMap.containsKey(str)) {
            long longValue = f202a.get(str).longValue();
            LOG.d(f203b, "" + str + " : " + (System.currentTimeMillis() - longValue));
        }
    }

    public static void stopTiming(String str, String str2) {
        HashMap<String, Long> hashMap;
        if (DEBUG && (hashMap = f202a) != null && hashMap.containsKey(str2)) {
            long longValue = f202a.get(str2).longValue();
            LOG.d(str, "" + str2 + " : " + (System.currentTimeMillis() - longValue));
        }
    }
}
