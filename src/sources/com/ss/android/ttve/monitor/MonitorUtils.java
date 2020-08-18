package com.ss.android.ttve.monitor;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;
import android.text.TextUtils;
import android.util.Log;
import com.ss.android.vesdk.keyvaluepair.VEKeyValue;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

@RestrictTo({RestrictTo.Scope.LIBRARY})
public class MonitorUtils {
    public static final String KEY_APP_VERSION = "app_version";
    public static final String KEY_CHANNEL = "channel";
    public static final String KEY_DEVICE_ID = "device_id";
    public static final String KEY_EFFECT_VERSION = "effect_version";
    public static final String KEY_MODEL = "model";
    public static final String KEY_PACKAGE_NAME = "package_name";
    public static final String KEY_USER_ID = "user_id";
    public static final String KEY_VERSION_CODE = "version_code";
    public static final String KEY_VE_VERSION = "ve_version";
    public static final String MONITOR_AID = "1357";
    public static final String MONITOR_SERVICE_NAME = "sdk_video_edit_compose";
    public static final String SERVICE_NAME_TE_SENSOR_REPORT = "iesve_veeditor_sensor_report";
    public static final String SERVICE_NAME_VIDEO_EDITOR_COMPOSITION = "iesve_veeditor_composition_finish";
    public static final String SERVICE_NAME_VIDEO_EDITOR_RECORD = "iesve_veeditor_record_finish";
    private static final String TAG = "MonitorUtils";
    private static boolean sEnable = true;
    private static IMonitorStatisticsListener sExternalMonitorListener;

    public interface IMonitorStatisticsListener {
        void onMonitorStatistics(String str, int i, String str2);
    }

    static JSONObject generateHeaderInfo(@NonNull Context context, @NonNull String str, @NonNull String str2, String str3) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("device_id", str);
            try {
                jSONObject.put("app_version", context.getPackageManager().getPackageInfo(context.getPackageName(), 128).versionName);
            } catch (PackageManager.NameNotFoundException e2) {
                jSONObject.put("app_version", "vesdk:4.4.0.34_7");
                Log.e(TAG, "PackageManager.NameNotFoundException", e2);
            }
            jSONObject.put(KEY_VE_VERSION, "4.4.0.34_7");
            jSONObject.put("effect_version", "debug");
            jSONObject.put(KEY_MODEL, Build.MODEL);
            jSONObject.put("channel", "release");
            jSONObject.put("package_name", context.getPackageName());
            jSONObject.put(KEY_USER_ID, str2);
            jSONObject.put(KEY_VERSION_CODE, str3);
        } catch (JSONException e3) {
            e3.printStackTrace();
        }
        return jSONObject;
    }

    public static String getAppVersion() {
        return getHeaderInfo("app_version");
    }

    public static String getDeviceId() {
        return getHeaderInfo("device_id");
    }

    private static String getHeaderInfo(String str) {
        if (sEnable) {
            return MonitorCompat.getHeaderInfo(str);
        }
        Log.w(TAG, "getHeaderInfo: Monitor not enabled just return.");
        return null;
    }

    public static String getUserId() {
        return getHeaderInfo(KEY_USER_ID);
    }

    public static void init(@NonNull Context context, String str, String str2, String str3) {
        if (!sEnable) {
            Log.w(TAG, "init: Monitor not enabled just return.");
        } else {
            MonitorCompat.init(context, str, str2, str3);
        }
    }

    public static void monitorStatistics(String str, int i, VEKeyValue vEKeyValue) {
        if (!sEnable) {
            Log.w(TAG, "monitorStatistics: Monitor not enabled just return.");
            return;
        }
        String str2 = null;
        JSONObject parseJsonObj = vEKeyValue == null ? null : vEKeyValue.parseJsonObj();
        IMonitorStatisticsListener iMonitorStatisticsListener = sExternalMonitorListener;
        if (iMonitorStatisticsListener != null) {
            if (parseJsonObj != null) {
                str2 = parseJsonObj.toString();
            }
            iMonitorStatisticsListener.onMonitorStatistics(str, i, str2);
        }
    }

    public static void monitorStatusAndDuration(String str, int i, JSONObject jSONObject, JSONObject jSONObject2) {
        MonitorCompat.monitorStatusAndDuration(str, i, jSONObject, jSONObject2);
    }

    public static void monitorStatusRate(String str, int i, JSONObject jSONObject) {
        if (!sEnable) {
            Log.w(TAG, "monitorStatusRate: Monitor not enabled just return.");
        } else if (jSONObject == 0) {
            Log.w(TAG, "monitorStatusRate: empty log data!");
        } else {
            if (TextUtils.isEmpty(getHeaderInfo("device_id"))) {
                setHeaderInfo("device_id", "Unknown");
                Log.e(TAG, "Device id is empty, please set device id with 1. TEMonitor.setDeviceId(deviceid) before using SDK. \n 2. Use TEMonitor.setSDKMonitorEnable(false) to disable SDKMonitor.");
            }
            if (TextUtils.isEmpty(getHeaderInfo(KEY_USER_ID))) {
                setHeaderInfo(KEY_USER_ID, "Unknown");
                Log.e(TAG, "User id is empty, please set user id with TEMonitor.setUserId(userid) before using SDK\n 2. Use TEMonitor.setSDKMonitorEnable(false) to disable SDKMonitor.");
            }
            if (TextUtils.isEmpty(getHeaderInfo("app_version"))) {
                setHeaderInfo("app_version", "Unknown");
                Log.e(TAG, "App version is empty, please set app version with TEMonitor.setAppVersion(version) before using SDK\n 2. Use TEMonitor.setSDKMonitorEnable(false) to disable SDKMonitor.");
            }
            try {
                for (Map.Entry<String, String> entry : DeviceInfoDetector.toMap().entrySet()) {
                    String value = entry.getValue();
                    if (!DeviceInfoDetector.sNumberKeys.contains(entry.getKey())) {
                        jSONObject.put(TEMonitorKeys.TETRACKER_DEVICE_INFO_PREFIX + entry.getKey(), value);
                    } else if (!TextUtils.isEmpty(value)) {
                        try {
                            float parseFloat = Float.parseFloat(value);
                            jSONObject.put(TEMonitorKeys.TETRACKER_DEVICE_INFO_PREFIX + entry.getKey(), (double) parseFloat);
                        } catch (Exception unused) {
                            Log.e(TAG, "Device info value is error key = " + entry.getKey());
                        }
                    }
                }
            } catch (JSONException e2) {
                Log.e(TAG, "monitorStatusRate: json exception!", e2);
            }
            MonitorCompat.monitorStatusAndDuration(str, i, jSONObject);
        }
    }

    public static void sensorReport(Context context) {
        HashMap hashMap = new HashMap();
        SensorManager sensorManager = (SensorManager) context.getSystemService("sensor");
        Sensor defaultSensor = sensorManager.getDefaultSensor(4);
        boolean z = false;
        hashMap.put(TEMonitorNewKeys.TE_SENSOR_TYPE_GYROSCOPE_EXIST, Boolean.valueOf(defaultSensor != null));
        String str = null;
        hashMap.put(TEMonitorNewKeys.TE_SENSOR_TYPE_GYROSCOPE_NAME, defaultSensor != null ? defaultSensor.getName() : null);
        Sensor defaultSensor2 = sensorManager.getDefaultSensor(15);
        hashMap.put(TEMonitorNewKeys.TE_SENSOR_TYPE_GAME_ROTATION_VECTOR_EXIST, Boolean.valueOf(defaultSensor2 != null));
        hashMap.put(TEMonitorNewKeys.TE_SENSOR_TYPE_GAME_ROTATION_VECTOR_NAME, defaultSensor2 != null ? defaultSensor2.getName() : null);
        Sensor defaultSensor3 = sensorManager.getDefaultSensor(11);
        hashMap.put(TEMonitorNewKeys.TE_SENSOR_TYPE_ROTATION_VECTOR_EXIST, Boolean.valueOf(defaultSensor3 != null));
        hashMap.put(TEMonitorNewKeys.TE_SENSOR_TYPE_ROTATION_VECTOR_NAME, defaultSensor3 != null ? defaultSensor3.getName() : null);
        Sensor defaultSensor4 = sensorManager.getDefaultSensor(9);
        hashMap.put(TEMonitorNewKeys.TE_SENSOR_TYPE_GRAVITY_EXIST, Boolean.valueOf(defaultSensor4 != null));
        hashMap.put(TEMonitorNewKeys.TE_SENSOR_TYPE_GRAVITY_NAME, defaultSensor4 != null ? defaultSensor4.getName() : null);
        Sensor defaultSensor5 = sensorManager.getDefaultSensor(1);
        if (defaultSensor5 != null) {
            z = true;
        }
        hashMap.put(TEMonitorNewKeys.TE_SENSOR_TYPE_ACCELEROMETER_EXIST, Boolean.valueOf(z));
        if (defaultSensor5 != null) {
            str = defaultSensor5.getName();
        }
        hashMap.put(TEMonitorNewKeys.TE_SENSOR_TYPE_ACCELEROMETER_NAME, str);
        TEMonitor.monitorTELog(SERVICE_NAME_TE_SENSOR_REPORT, SERVICE_NAME_TE_SENSOR_REPORT, hashMap);
    }

    public static void setAppVersion(String str) {
        setHeaderInfo("app_version", str);
    }

    public static void setDeviceId(String str) {
        setHeaderInfo("device_id", str);
    }

    public static void setEnable(boolean z) {
        sEnable = z;
    }

    public static void setExternalMonitorListener(IMonitorStatisticsListener iMonitorStatisticsListener) {
        sExternalMonitorListener = iMonitorStatisticsListener;
    }

    private static void setHeaderInfo(String str, String str2) {
        if (!sEnable) {
            Log.w(TAG, "setHeaderInfo: Monitor not enabled just return.");
        } else {
            MonitorCompat.setHeaderInfo(str, str2);
        }
    }

    public static void setServerLocation(int i) {
        MonitorCompat.setServerLocation(i);
    }

    public static void setUserId(String str) {
        setHeaderInfo(KEY_USER_ID, str);
    }
}
