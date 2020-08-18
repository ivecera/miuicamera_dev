package com.xiaomi.mistatistic.sdk;

import android.content.Context;
import java.util.Map;

public class MiStatInterface {
    public static int UPLOAD_POLICY_INTERVAL = 1;

    public static void enableExceptionCatcher(boolean z) {
    }

    public static void initialize(Context context, String str, String str2, String str3) {
    }

    public static void recordCalculateEvent(String str, String str2, long j) {
    }

    public static void recordCalculateEvent(String str, String str2, long j, Map<String, String> map) {
    }

    public static void recordCalculateEventAnonymous(String str, String str2, long j) {
    }

    public static void recordCalculateEventAnonymous(String str, String str2, long j, Map<String, String> map) {
    }

    public static void recordCountEvent(String str, String str2) {
    }

    public static void recordCountEvent(String str, String str2, Map<String, String> map) {
    }

    public static void recordCountEventAnonymous(String str, String str2) {
    }

    public static void recordCountEventAnonymous(String str, String str2, Map<String, String> map) {
    }

    public static void recordPageEnd() {
    }

    public static void recordPageStart(Context context, String str) {
    }

    public static void recordStringPropertyEvent(String str, String str2, String str3) {
    }

    public static void recordStringPropertyEventAnonymous(String str, String str2, String str3) {
    }

    public static void setUploadPolicy(int i, long j) {
    }
}
