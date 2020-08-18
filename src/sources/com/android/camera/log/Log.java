package com.android.camera.log;

import android.content.ComponentName;
import android.content.Intent;
import android.hardware.camera2.CaptureRequest;
import android.os.BadParcelableException;
import android.os.Bundle;
import android.os.SystemProperties;
import com.android.camera.Util;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class Log {
    public static final int ASSERT = 7;
    private static final String CAMERA_LOGTAG_PREFIX = SystemProperties.get(PROP_KEY, "CAM_");
    public static final String CONTINUAL = "C_";
    public static final int DEBUG = 3;
    private static final int DEFAULT = -1;
    private static final boolean DUMP_ENABLE = android.util.Log.isLoggable("camera_dump_request", 3);
    public static final String EFFECT_TAG = "EFFECT_";
    public static final int ERROR = 6;
    public static final String GESTURE_TAG = "GESTURE_";
    public static final int INFO = 4;
    private static final int MAX_TAG_LEN = (23 - CAMERA_LOGTAG_PREFIX.length());
    private static final String PROP_KEY = "CAMERA_APP_LOG_TAG_PREFIX";
    public static final String SETTING_TAG = "SETTING_";
    public static final String SWITCH_TAG = "SWITCH_";
    private static final String TAG_DUMP_CAMERA_INTENT = "DUMP_CAMERA_INTENT";
    public static final String UI_THREAD_TAG = "UI_";
    public static final int VERBOSE = 2;
    public static final String VIEW_TAG = "VIEW_";
    public static final int WARN = 5;
    private static int sDebugPriority;

    @Retention(RetentionPolicy.SOURCE)
    public @interface DebugPriority {
    }

    static {
        int i = 2;
        sDebugPriority = 2;
        try {
            int overrideLevel = getOverrideLevel();
            if (overrideLevel != 0) {
                sDebugPriority = overrideLevel;
            } else {
                if (!Util.isDebugOsBuild()) {
                    i = Integer.MAX_VALUE;
                }
                sDebugPriority = i;
            }
        } catch (IllegalArgumentException e2) {
            System.out.println(e2.getMessage());
        }
    }

    public static int c(String str, String str2) {
        String tagFormat = tagFormat(CONTINUAL + str);
        if (shouldLogForDebug(CAMERA_LOGTAG_PREFIX + CONTINUAL, 2)) {
            return android.util.Log.v(tagFormat, str2);
        }
        return -1;
    }

    public static int d(String str, String str2) {
        String tagFormat = tagFormat(str);
        if (isLoggable(tagFormat, 3)) {
            return android.util.Log.d(tagFormat, str2);
        }
        return -1;
    }

    public static int d(String str, String str2, Throwable th) {
        String tagFormat = tagFormat(str);
        if (isLoggable(tagFormat, 3)) {
            return android.util.Log.d(tagFormat, str2, th);
        }
        return -1;
    }

    public static int d(String str, String str2, Object... objArr) {
        return d(str, String.format(str2, objArr));
    }

    private static void dumpComponentName(String str, ComponentName componentName) {
        if (componentName != null) {
            android.util.Log.v(str, "Component: " + componentName.getPackageName() + "/" + componentName.getClassName());
            return;
        }
        android.util.Log.v(str, "Component: null");
    }

    private static void dumpExtras(String str, Bundle bundle) {
        if (bundle != null) {
            for (String str2 : bundle.keySet()) {
                Object obj = bundle.get(str2);
                if (obj instanceof Bundle) {
                    dumpExtras(str, (Bundle) obj);
                } else {
                    try {
                        android.util.Log.v(str, "Extra[" + str2 + "] :" + String.valueOf(bundle.get(str2)));
                    } catch (BadParcelableException e2) {
                        android.util.Log.w(str, "Extra contains unknown class instance for [" + str2 + "]: ", e2);
                    }
                }
            }
        }
    }

    public static void dumpIntent(String str, Intent intent) {
        if (!android.util.Log.isLoggable(TAG_DUMP_CAMERA_INTENT, 3)) {
            android.util.Log.v(str, "dumpIntent is not enable on this device.");
        } else if (intent == null) {
            android.util.Log.v(str, "intent is null.");
        } else {
            android.util.Log.v(str, "=================================================================");
            android.util.Log.v(str, "Intent[@" + Integer.toHexString(intent.hashCode()) + "] content:");
            StringBuilder sb = new StringBuilder();
            sb.append("Action   : ");
            sb.append(intent.getAction());
            android.util.Log.v(str, sb.toString());
            android.util.Log.v(str, "Category : " + intent.getCategories());
            android.util.Log.v(str, "Data     : " + intent.getDataString());
            dumpComponentName(str, intent.getComponent());
            Bundle extras = intent.getExtras();
            android.util.Log.v(str, "HasExtras: " + hasExtras(extras));
            dumpExtras(str, extras);
            android.util.Log.v(str, "=================================================================");
        }
    }

    public static void dumpRequest(String str, CaptureRequest captureRequest) {
        if (DUMP_ENABLE) {
            d("CAM_dump_request", str);
            for (CaptureRequest.Key<?> key : captureRequest.getKeys()) {
                if (captureRequest.get(key) != null) {
                    d("dump_request", String.format(" key: %s  value: %s\n", key.getName(), CameraMetadataSerializer.metadataValueToString(captureRequest.get(key))));
                }
            }
        }
    }

    public static int e(String str, String str2) {
        String tagFormat = tagFormat(str);
        if (isLoggable(tagFormat, 6)) {
            return android.util.Log.e(tagFormat, str2);
        }
        return -1;
    }

    public static int e(String str, String str2, Throwable th) {
        String tagFormat = tagFormat(str);
        if (isLoggable(tagFormat, 6)) {
            return android.util.Log.e(tagFormat, str2, th);
        }
        return -1;
    }

    public static int e(String str, String str2, Object... objArr) {
        return e(str, String.format(str2, objArr));
    }

    public static int getOverrideLevel() {
        return SystemProperties.getInt("camera.debug.log_level", 2);
    }

    private static boolean hasExtras(Bundle bundle) {
        if (bundle != null) {
            try {
                if (!bundle.isEmpty()) {
                    return true;
                }
            } catch (BadParcelableException e2) {
                android.util.Log.w("IntentLogger", "Extra contains unknown class instance: ", e2);
                return true;
            }
        }
        return false;
    }

    public static int i(String str, String str2) {
        String tagFormat = tagFormat(str);
        if (isLoggable(tagFormat, 4)) {
            return android.util.Log.i(tagFormat, str2);
        }
        return -1;
    }

    public static int i(String str, String str2, Throwable th) {
        String tagFormat = tagFormat(str);
        if (isLoggable(tagFormat, 4)) {
            return android.util.Log.i(tagFormat, str2, th);
        }
        return -1;
    }

    private static boolean isLoggable(String str, int i) {
        return sDebugPriority <= i || shouldLog(str, i);
    }

    private static boolean shouldLog(String str, int i) {
        return android.util.Log.isLoggable(CAMERA_LOGTAG_PREFIX, i) || android.util.Log.isLoggable(str, i);
    }

    private static boolean shouldLogForDebug(String str, int i) {
        return android.util.Log.isLoggable(str, i);
    }

    private static String tagFormat(String str) {
        int length = str.length() - MAX_TAG_LEN;
        StringBuilder sb = new StringBuilder();
        sb.append(CAMERA_LOGTAG_PREFIX);
        if (length > 0) {
            str = str.substring(0, MAX_TAG_LEN);
        }
        sb.append(str);
        return sb.toString();
    }

    public static int v(String str, String str2) {
        String tagFormat = tagFormat(str);
        if (isLoggable(tagFormat, 2)) {
            return android.util.Log.v(tagFormat, str2);
        }
        return -1;
    }

    public static int v(String str, String str2, Throwable th) {
        String tagFormat = tagFormat(str);
        if (isLoggable(tagFormat, 2)) {
            return android.util.Log.v(tagFormat, str2, th);
        }
        return -1;
    }

    public static int w(String str, String str2) {
        String tagFormat = tagFormat(str);
        if (isLoggable(tagFormat, 5)) {
            return android.util.Log.w(tagFormat, str2);
        }
        return -1;
    }

    public static int w(String str, String str2, Throwable th) {
        String tagFormat = tagFormat(str);
        if (isLoggable(tagFormat, 5)) {
            return android.util.Log.w(tagFormat, str2, th);
        }
        return -1;
    }

    public static int w(String str, String str2, Object... objArr) {
        return w(str, String.format(str2, objArr));
    }

    public static int w(String str, Throwable th) {
        String tagFormat = tagFormat(str);
        if (isLoggable(tagFormat, 5)) {
            return android.util.Log.w(tagFormat, th);
        }
        return -1;
    }
}
