package com.ss.android.medialib.log;

import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import com.ss.android.medialib.common.LogUtil;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class BaseMonitor {
    private static final String TAG = "BaseMonitor";
    public static IMonitor sMonitor;

    public static boolean monitorVELog(IMonitor iMonitor, String str, String str2, Map map) {
        if (iMonitor == null) {
            LogUtil.d(TAG, "No monitor callback, return");
            return false;
        }
        JSONObject jSONObject = new JSONObject();
        try {
            for (String str3 : map.keySet()) {
                jSONObject.put(str3, map.get(str3));
                if (!TextUtils.isEmpty(str2)) {
                    jSONObject.put(NotificationCompat.CATEGORY_SERVICE, str2);
                }
            }
            if (iMonitor == null) {
                return true;
            }
            iMonitor.monitorLog(str, jSONObject);
            return true;
        } catch (JSONException unused) {
            LogUtil.d(TAG, "No monitor callback, skip");
            return false;
        }
    }

    public static boolean monitorVELog(String str, String str2, float f2) {
        return monitorVELog(str, str2, String.valueOf(f2));
    }

    public static boolean monitorVELog(String str, String str2, long j) {
        return monitorVELog(str, str2, String.valueOf(j));
    }

    public static boolean monitorVELog(String str, String str2, String str3) {
        HashMap hashMap = new HashMap();
        hashMap.put(str2, str3);
        return monitorVELog(str, str2, hashMap);
    }

    public static boolean monitorVELog(String str, String str2, Map map) {
        return monitorVELog(sMonitor, str, str2, map);
    }

    public static void register(IMonitor iMonitor) {
        sMonitor = iMonitor;
    }

    public static void unRegister() {
        sMonitor = null;
    }
}
