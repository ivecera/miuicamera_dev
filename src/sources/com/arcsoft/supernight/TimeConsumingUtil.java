package com.arcsoft.supernight;

import java.util.HashMap;

public class TimeConsumingUtil {
    public static boolean DEBUG = false;

    /* renamed from: a  reason: collision with root package name */
    private static HashMap<String, Long> f276a = new HashMap<>();

    /* renamed from: b  reason: collision with root package name */
    private static final String f277b = "Performance";

    public static void startTheTimer(String str) {
        HashMap<String, Long> hashMap;
        if (DEBUG && (hashMap = f276a) != null) {
            hashMap.put(str, Long.valueOf(System.currentTimeMillis()));
        }
    }

    public static void stopTiming(String str) {
        HashMap<String, Long> hashMap;
        if (DEBUG && (hashMap = f276a) != null && hashMap.containsKey(str)) {
            long longValue = f276a.get(str).longValue();
            LOG.d(f277b, "" + str + " : " + (System.currentTimeMillis() - longValue) + " ms");
        }
    }

    public static void stopTiming(String str, String str2) {
        HashMap<String, Long> hashMap;
        if (DEBUG && (hashMap = f276a) != null && hashMap.containsKey(str2)) {
            long longValue = f276a.get(str2).longValue();
            LOG.d(str, "" + str2 + " : " + (System.currentTimeMillis() - longValue));
        }
    }
}
