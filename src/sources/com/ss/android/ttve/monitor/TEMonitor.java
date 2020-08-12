package com.ss.android.ttve.monitor;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;
import com.ss.android.medialib.log.VEMonitorKeys;
import com.ss.android.vesdk.VELogUtil;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class TEMonitor {
    public static int MONITOR_ACTION_CANCEL = 0;
    public static int MONITOR_ACTION_COMPILE = 1;
    public static final int MONITOR_COMPOSITION = 1;
    public static final int MONITOR_RECORD = 0;
    private static final String TAG = "TEMonitor";
    private static WeakReference<IMonitor> sMonitor = null;
    private static String sVid = "";

    public static void clear() {
        TEMonitorInvoker.nativeReset();
    }

    public static void clearWithType(int i) {
        TEMonitorInvoker.nativeReset(i);
    }

    public static String getAppVersion() {
        return MonitorUtils.getAppVersion();
    }

    public static String getDeviceId() {
        return MonitorUtils.getDeviceId();
    }

    private static int getIsComplete(JSONObject jSONObject) {
        try {
            if (jSONObject.has("completed")) {
                return jSONObject.getInt("completed");
            }
            return 0;
        } catch (JSONException e2) {
            VELogUtil.e(TAG, "get complete filed error!");
            e2.printStackTrace();
            return 0;
        }
    }

    public static String getUserId() {
        return MonitorUtils.getUserId();
    }

    public static void init(@NonNull Context context, String str) {
        MonitorUtils.init(context.getApplicationContext(), str, null, null);
        TEMonitorInvoker.nativeInit();
    }

    public static void initStats(int i) {
        if (i == 0) {
            perfLong(0, TEMonitorNewKeys.TE_RECORD_ERR_CODE, 0);
        } else if (i == 1) {
            perfLong(1, TEMonitorNewKeys.TE_EDIT_ERR_CODE, 0);
            perfLong(1, TEMonitorNewKeys.TE_COMPOSITION_ERR_CODE, 0);
        }
    }

    public static boolean monitorTELog(String str, String str2, float f2) {
        HashMap hashMap = new HashMap();
        hashMap.put(str2, Float.valueOf(f2));
        return monitorTELog(str, str2, hashMap);
    }

    public static boolean monitorTELog(String str, String str2, long j) {
        HashMap hashMap = new HashMap();
        hashMap.put(str2, Long.valueOf(j));
        return monitorTELog(str, str2, hashMap);
    }

    public static boolean monitorTELog(String str, String str2, String str3) {
        HashMap hashMap = new HashMap();
        hashMap.put(str2, str3);
        return monitorTELog(str, str2, hashMap);
    }

    public static boolean monitorTELog(String str, String str2, Map map) {
        return monitorTELog(sMonitor, str, str2, map);
    }

    private static boolean monitorTELog(WeakReference<IMonitor> weakReference, String str, String str2, Map map) {
        JSONObject jSONObject = new JSONObject();
        try {
            if (!TextUtils.isEmpty(str2)) {
                jSONObject.put(NotificationCompat.CATEGORY_SERVICE, str2);
            }
            if (!str2.equals("iesve_veeditor_record_finish")) {
                if (!str2.equals("iesve_veeditor_composition_finish")) {
                    putAll(map, jSONObject);
                    reportMonitor(weakReference, str, jSONObject);
                    return true;
                }
            }
            putAll(str2, map, jSONObject);
            reportMonitor(weakReference, str, jSONObject);
            return true;
        } catch (JSONException unused) {
            VELogUtil.d(TAG, "No monitor callback, skip");
            return false;
        }
    }

    public static void monitorTELogJson(String str, String str2, String str3) {
        JSONObject jSONObject;
        try {
            jSONObject = new JSONObject(str3);
            try {
                jSONObject.put(NotificationCompat.CATEGORY_SERVICE, str2);
            } catch (JSONException e2) {
                e = e2;
            }
        } catch (JSONException e3) {
            e = e3;
            jSONObject = null;
            e.printStackTrace();
            reportMonitor(sMonitor, str, jSONObject);
        }
        reportMonitor(sMonitor, str, jSONObject);
    }

    private static void monitorVELog(JSONObject jSONObject) {
        Map<String, String> nativeGetMap;
        if (jSONObject == null) {
            VELogUtil.e(TAG, "monitorVELog error, param is null");
            return;
        }
        String str = "";
        try {
            if (jSONObject.has(NotificationCompat.CATEGORY_SERVICE)) {
                str = jSONObject.getString(NotificationCompat.CATEGORY_SERVICE);
            }
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        if (!"iesve_veeditor_record_finish".equals(str) && !"iesve_veeditor_composition_finish".equals(str) && (nativeGetMap = TEMonitorInvoker.nativeGetMap()) != null) {
            try {
                putAll(nativeGetMap, jSONObject);
            } catch (JSONException e3) {
                VELogUtil.e(TAG, "merge monitor logs error");
                e3.printStackTrace();
            }
        }
        getIsComplete(jSONObject);
        reportMonitor(sMonitor, "sdk_video_edit_compose", jSONObject);
    }

    public static void perfDouble(int i, String str, double d2) {
        if (TextUtils.isEmpty(str)) {
            VELogUtil.w(TAG, "perfDouble: key is null");
        } else {
            TEMonitorInvoker.nativePerfDouble(i, str, d2);
        }
    }

    public static void perfDouble(String str, double d2) {
        if (TextUtils.isEmpty(str)) {
            VELogUtil.w(TAG, "perfDouble: key is null");
        } else {
            TEMonitorInvoker.nativePerfDouble(str, d2);
        }
    }

    public static void perfLong(int i, String str, long j) {
        if (TextUtils.isEmpty(str)) {
            Log.w(TAG, "perfLong: key is null");
        } else {
            TEMonitorInvoker.nativePerfLong(i, str, j);
        }
    }

    public static void perfLong(String str, long j) {
        if (TextUtils.isEmpty(str)) {
            VELogUtil.w(TAG, "perfLong: key is null");
        } else {
            TEMonitorInvoker.nativePerfLong(str, j);
        }
    }

    public static void perfRational(String str, float f2, float f3) {
        if (TextUtils.isEmpty(str)) {
            VELogUtil.w(TAG, "perfLong: key is null");
        } else {
            TEMonitorInvoker.nativePerfRational(str, f2, f3);
        }
    }

    public static void perfString(int i, String str, String str2) {
        if (TextUtils.isEmpty(str)) {
            VELogUtil.w(TAG, "perfString: key is null");
            return;
        }
        if (str2 == null) {
            str2 = "";
        }
        TEMonitorInvoker.nativePerfString(i, str, str2);
    }

    public static void perfString(String str, String str2) {
        if (TextUtils.isEmpty(str)) {
            VELogUtil.w(TAG, "perfString: key is null");
            return;
        }
        if (str2 == null) {
            str2 = "";
        }
        TEMonitorInvoker.nativePerfString(str, str2);
    }

    private static void putAll(String str, Map map, JSONObject jSONObject) throws JSONException {
        for (String str2 : map.keySet()) {
            int type = (str.equals("iesve_veeditor_record_finish") || str.equals("iesve_veeditor_composition_finish")) ? TEMonitorNewKeys.getType(str2) : TEMonitorKeys.getType(str2);
            if (type == TEMonitorKeys.TYPE_INT) {
                try {
                    jSONObject.put(str2, Integer.parseInt((String) map.get(str2)));
                } catch (Exception unused) {
                    VELogUtil.d(TAG, "Parse int error:" + map.get(str2));
                }
            } else if (type == TEMonitorKeys.TYPE_DOUBLE) {
                try {
                    jSONObject.put(str2, (double) Float.parseFloat((String) map.get(str2)));
                } catch (Exception unused2) {
                    VELogUtil.d(TAG, "Parse float error");
                }
            } else {
                jSONObject.put(str2, map.get(str2));
            }
        }
    }

    private static void putAll(Map map, JSONObject jSONObject) throws JSONException {
        for (String str : map.keySet()) {
            int type = str.startsWith("iesve_") ? VEMonitorKeys.getType(str) : TEMonitorKeys.getType(str);
            if (type == TEMonitorKeys.TYPE_INT) {
                try {
                    jSONObject.put(str, Integer.parseInt((String) map.get(str)));
                } catch (Exception unused) {
                    VELogUtil.d(TAG, "Parse int error:" + map.get(str));
                }
            } else if (type == TEMonitorKeys.TYPE_DOUBLE) {
                try {
                    jSONObject.put(str, (double) Float.parseFloat((String) map.get(str)));
                } catch (Exception unused2) {
                    VELogUtil.d(TAG, "Parse float error");
                }
            } else {
                jSONObject.put(str, map.get(str));
            }
        }
    }

    public static void register(IMonitor iMonitor) {
        sMonitor = new WeakReference<>(iMonitor);
    }

    public static void report(int i) {
        TEMonitorInvoker.nativeMonitorPerf(i);
    }

    private static void reportMonitor(WeakReference<IMonitor> weakReference, String str, JSONObject jSONObject) {
        int i;
        String str2 = "sdk_video_edit_compose";
        if (jSONObject != null) {
            i = getIsComplete(jSONObject);
            try {
                if (jSONObject.has(NotificationCompat.CATEGORY_SERVICE)) {
                    str2 = jSONObject.getString(NotificationCompat.CATEGORY_SERVICE);
                }
                if (str2.equals("iesve_veeditor_record_finish") || str2.equals("iesve_veeditor_composition_finish")) {
                    if ("".equals(sVid)) {
                        sVid = getDeviceId() + "_" + System.currentTimeMillis();
                    }
                    jSONObject.put(TEMonitorNewKeys.TE_RECORD_COMPOSE_VID, sVid);
                }
                if (str2.equals("iesve_veeditor_composition_finish")) {
                    sVid = "";
                }
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        } else {
            i = 0;
        }
        MonitorUtils.monitorStatusRate(str2, i, jSONObject);
        if (weakReference != null && weakReference.get() != null) {
            try {
                weakReference.get().monitorLog(str, jSONObject);
            } catch (Exception e3) {
                Log.e(TAG, "Something happened when monitor log", e3);
            }
        }
    }

    public static void reportWithType(int i) {
        TEMonitorInvoker.nativeMonitorPerfWithType(i);
    }

    public static void setAppVersion(String str) {
        MonitorUtils.setAppVersion(str);
    }

    public static void setDeviceId(String str) {
        MonitorUtils.setDeviceId(str);
    }

    public static void setSDKMonitorEnable(boolean z) {
        MonitorUtils.setEnable(z);
    }

    public static void setUserId(String str) {
        MonitorUtils.setUserId(str);
    }
}
