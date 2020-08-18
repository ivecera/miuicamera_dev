package com.android.camera.statistic;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.os.SystemProperties;
import android.text.TextUtils;
import com.android.camera.CameraSettings;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.data.DataRepository;
import com.android.camera.fragment.beauty.BeautyValues;
import com.android.camera.log.Log;
import com.android.camera.statistic.MistatsConstants;
import com.xiaomi.stat.MiStat;
import com.xiaomi.stat.MiStatParams;
import d.d.a;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MistatsWrapper {
    private static final String APP_ID = "2882303761517373386";
    private static final String APP_KEY = "5641737344386";
    private static final String CHANNEL = SystemProperties.get("ro.product.mod_device", Build.DEVICE);
    private static final String TAG = "MistatsWrapper";
    private static boolean sDumpStatEvent;
    private static boolean sInitialized;
    private static boolean sIsAnonymous;
    private static boolean sIsCounterEventEnabled;
    private static boolean sIsEnabled = true;

    @Retention(RetentionPolicy.SOURCE)
    public @interface ModuleName {
    }

    public static class PictureTakenParameter {
        public String aiSceneName;
        public BeautyValues beautyValues;
        public boolean burst;
        public boolean isASDBacklitTip;
        public boolean isASDPortraitTip;
        public boolean isEnteringMoon;
        public boolean isSelectMoonMode;
        public boolean isSuperNightInCaptureMode;
        public boolean location;
        public int takenNum;
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface TriggerMode {
    }

    public static void commonKeyTriggerEvent(String str, Object obj, String str2) {
        if (sIsEnabled) {
            MiStatParams miStatParams = new MiStatParams();
            HashMap hashMap = new HashMap();
            if (TextUtils.isEmpty(str2)) {
                miStatParams.putString("attr_trigger_mode", "click");
                hashMap.put("attr_trigger_mode", "click");
            } else {
                miStatParams.putString("attr_trigger_mode", str2);
                hashMap.put("attr_trigger_mode", str2);
            }
            String statsModuleKey = getStatsModuleKey(DataRepository.dataItemGlobal().getCurrentMode());
            miStatParams.putString(MistatsConstants.BaseEvent.MODULE_NAME, statsModuleKey);
            miStatParams.putString(MistatsConstants.BaseEvent.FEATURE_NAME, str);
            hashMap.put(MistatsConstants.BaseEvent.MODULE_NAME, statsModuleKey);
            hashMap.put(MistatsConstants.BaseEvent.FEATURE_NAME, str);
            if (obj != null) {
                String valueOf = obj instanceof Boolean ? ((Boolean) obj).booleanValue() ? "on" : "off" : String.valueOf(obj);
                miStatParams.putString(MistatsConstants.BaseEvent.VALUE, valueOf);
                hashMap.put(MistatsConstants.BaseEvent.VALUE, valueOf);
            }
            String str3 = "front";
            miStatParams.putString(MistatsConstants.BaseEvent.SENSOR_ID, CameraSettings.isFrontCamera() ? str3 : MistatsConstants.BaseEvent.BACK);
            if (!CameraSettings.isFrontCamera()) {
                str3 = MistatsConstants.BaseEvent.BACK;
            }
            hashMap.put(MistatsConstants.BaseEvent.SENSOR_ID, str3);
            MiStat.trackEvent(MistatsConstants.FeatureName.KEY_COMMON, miStatParams);
            if (sDumpStatEvent) {
                dumpEvent(MistatsConstants.FeatureName.KEY_COMMON, hashMap);
            }
        }
    }

    private static void dumpEvent(String str, String str2) {
        Log.d(TAG, "mapKey:" + str + "  " + "mapValue:" + str2);
    }

    private static void dumpEvent(String str, Map<String, String> map) {
        StringBuilder sb = new StringBuilder();
        Set<String> keySet = map.keySet();
        sb.append("functionKey:");
        sb.append(str);
        for (String str2 : keySet) {
            sb.append("\n");
            sb.append("mapKey:");
            sb.append(str2);
            sb.append("  ");
            String valueOf = String.valueOf(map.get(str2));
            sb.append("mapValue:");
            sb.append(valueOf);
        }
        Log.d(TAG, sb.toString());
    }

    public static void featureTriggerEvent(String str, Object obj, String str2) {
        if (sIsEnabled) {
            MiStatParams miStatParams = new MiStatParams();
            HashMap hashMap = new HashMap();
            if (TextUtils.isEmpty(str2)) {
                miStatParams.putString("attr_trigger_mode", "click");
                hashMap.put("attr_trigger_mode", "click");
            } else {
                miStatParams.putString("attr_trigger_mode", str2);
                hashMap.put("attr_trigger_mode", str2);
            }
            String valueOf = obj instanceof Boolean ? ((Boolean) obj).booleanValue() ? "on" : "off" : String.valueOf(obj);
            String statsModuleKey = getStatsModuleKey(DataRepository.dataItemGlobal().getCurrentMode());
            miStatParams.putString(MistatsConstants.BaseEvent.MODULE_NAME, statsModuleKey);
            miStatParams.putString(MistatsConstants.BaseEvent.VALUE, valueOf);
            String str3 = "front";
            miStatParams.putString(MistatsConstants.BaseEvent.SENSOR_ID, CameraSettings.isFrontCamera() ? str3 : MistatsConstants.BaseEvent.BACK);
            hashMap.put(MistatsConstants.BaseEvent.MODULE_NAME, statsModuleKey);
            hashMap.put(MistatsConstants.BaseEvent.VALUE, valueOf);
            if (!CameraSettings.isFrontCamera()) {
                str3 = MistatsConstants.BaseEvent.BACK;
            }
            hashMap.put(MistatsConstants.BaseEvent.SENSOR_ID, str3);
            MiStat.trackEvent(str, miStatParams);
            if (sDumpStatEvent) {
                dumpEvent(str, hashMap);
            }
        }
    }

    public static String getStatsModuleKey(int i) {
        switch (i) {
            case 160:
                return MistatsConstants.ModuleName.UNSPECIFIED;
            case 161:
                return MistatsConstants.ModuleName.FUN;
            case 162:
                return MistatsConstants.ModuleName.RECORD_VIDEO;
            case 163:
                return MistatsConstants.ModuleName.CAPTURE;
            case 164:
            case 180:
            case 181:
            default:
                return null;
            case 165:
                return MistatsConstants.ModuleName.SQUARE;
            case 166:
                return MistatsConstants.ModuleName.PANORAMA;
            case 167:
                return MistatsConstants.ModuleName.MANUAL;
            case 168:
                return MistatsConstants.ModuleName.SLOW_MOTION;
            case 169:
                return MistatsConstants.ModuleName.FAST_MOTION;
            case 170:
                return MistatsConstants.ModuleName.VIDEO_HFR;
            case 171:
                return MistatsConstants.ModuleName.PORTRAIT;
            case 172:
                return MistatsConstants.ModuleName.NEW_SLOW_MOTION;
            case 173:
                return MistatsConstants.ModuleName.SUPER_NIGHT;
            case 174:
                return MistatsConstants.ModuleName.LIVE;
            case 175:
                return MistatsConstants.ModuleName.PIXEL;
            case 176:
                return MistatsConstants.ModuleName.WIDE_SELFIE;
            case 177:
                return MistatsConstants.ModuleName.FUN_AR;
            case 178:
                return MistatsConstants.ModuleName.STANDALONE_MACRO;
            case 179:
                return MistatsConstants.ModuleName.LIVE_VV;
            case 182:
                return MistatsConstants.ModuleName.ID_CARD;
            case 183:
                return MistatsConstants.ModuleName.MI_LIVE;
            case 184:
                return MistatsConstants.ModuleName.FUN_AR2;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0065, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0067, code lost:
        return;
     */
    public static synchronized void initialize(Context context) {
        synchronized (MistatsWrapper.class) {
            if (!sInitialized) {
                if (Util.isUserUnlocked(context)) {
                    sInitialized = true;
                    boolean z = false;
                    sDumpStatEvent = SystemProperties.getBoolean("camera.debug.dump_stat_event", false);
                    Resources resources = context.getResources();
                    sIsEnabled = resources.getBoolean(R.bool.pref_camera_statistic_default);
                    if (sIsEnabled) {
                        sIsCounterEventEnabled = resources.getBoolean(R.bool.pref_camera_statistic_counter_event_default);
                        sIsAnonymous = a.lh;
                        MiStat.initialize(context, APP_ID, APP_KEY, false, CHANNEL);
                        if (!sIsAnonymous) {
                            z = true;
                        }
                        MiStat.setExceptionCatcherEnabled(z);
                        if (sDumpStatEvent) {
                            MiStat.setDebugModeEnabled(true);
                        }
                        MiStat.setUploadInterval(90000);
                        MiStat.setUseSystemUploadingService(true, true);
                        if (a.lh) {
                            MiStat.setInternationalRegion(true, a.getRegion());
                        }
                    }
                }
            }
        }
    }

    public static boolean isCounterEventDisabled() {
        return !sIsCounterEventEnabled;
    }

    public static void keyTriggerEvent(String str, String str2, Object obj) {
        keyTriggerEvent(str, str2, obj, "none");
    }

    public static void keyTriggerEvent(String str, String str2, Object obj, String str3) {
        if (sIsEnabled) {
            MiStatParams miStatParams = new MiStatParams();
            HashMap hashMap = new HashMap();
            if (TextUtils.isEmpty(str3)) {
                miStatParams.putString("attr_trigger_mode", "click");
                hashMap.put("attr_trigger_mode", "click");
            } else {
                miStatParams.putString("attr_trigger_mode", str3);
                hashMap.put("attr_trigger_mode", str3);
            }
            String valueOf = obj instanceof Boolean ? ((Boolean) obj).booleanValue() ? "on" : "off" : String.valueOf(obj);
            String statsModuleKey = getStatsModuleKey(DataRepository.dataItemGlobal().getCurrentMode());
            miStatParams.putString(MistatsConstants.BaseEvent.MODULE_NAME, statsModuleKey);
            miStatParams.putString(str2, valueOf);
            String str4 = "front";
            miStatParams.putString(MistatsConstants.BaseEvent.SENSOR_ID, CameraSettings.isFrontCamera() ? str4 : MistatsConstants.BaseEvent.BACK);
            hashMap.put(MistatsConstants.BaseEvent.MODULE_NAME, statsModuleKey);
            hashMap.put(str2, valueOf);
            if (!CameraSettings.isFrontCamera()) {
                str4 = MistatsConstants.BaseEvent.BACK;
            }
            hashMap.put(MistatsConstants.BaseEvent.SENSOR_ID, str4);
            MiStat.trackEvent(str, miStatParams);
            if (sDumpStatEvent) {
                dumpEvent(str, hashMap);
            }
        }
    }

    public static void mistatEvent(String str, Map<String, String> map) {
        if (sIsEnabled) {
            MiStatParams miStatParams = new MiStatParams();
            HashMap hashMap = new HashMap();
            String statsModuleKey = getStatsModuleKey(DataRepository.dataItemGlobal().getCurrentMode());
            miStatParams.putString(MistatsConstants.BaseEvent.MODULE_NAME, statsModuleKey);
            String str2 = CameraSettings.isFrontCamera() ? "front" : MistatsConstants.BaseEvent.BACK;
            miStatParams.putString(MistatsConstants.BaseEvent.SENSOR_ID, str2);
            hashMap.put(MistatsConstants.BaseEvent.MODULE_NAME, statsModuleKey);
            hashMap.put(MistatsConstants.BaseEvent.SENSOR_ID, str2);
            for (String str3 : map.keySet()) {
                String valueOf = String.valueOf(map.get(str3));
                miStatParams.putString(str3, valueOf);
                hashMap.put(str3, valueOf);
            }
            MiStat.trackEvent(str, miStatParams);
            if (sDumpStatEvent) {
                dumpEvent(str, hashMap);
            }
        }
    }

    public static void mistatEventSimple(String str, Map<String, String> map) {
        if (sIsEnabled) {
            MiStatParams miStatParams = new MiStatParams();
            for (String str2 : map.keySet()) {
                miStatParams.putString(str2, String.valueOf(map.get(str2)));
            }
            MiStat.trackEvent(str, miStatParams);
            if (sDumpStatEvent) {
                dumpEvent(str, map);
            }
        }
    }

    public static void modeMistatsEvent(int i, Map<String, String> map) {
        moduleMistatsEvent(getStatsModuleKey(i), map);
    }

    public static void moduleCaptureEvent(String str, Map<String, String> map) {
        if (sIsEnabled) {
            MiStatParams miStatParams = new MiStatParams();
            HashMap hashMap = new HashMap();
            miStatParams.putString(MistatsConstants.BaseEvent.MODE, MistatsConstants.BaseEvent.PHOTO);
            String str2 = "front";
            miStatParams.putString(MistatsConstants.BaseEvent.SENSOR_ID, CameraSettings.isFrontCamera() ? str2 : MistatsConstants.BaseEvent.BACK);
            hashMap.put(MistatsConstants.BaseEvent.MODE, MistatsConstants.BaseEvent.PHOTO);
            if (!CameraSettings.isFrontCamera()) {
                str2 = MistatsConstants.BaseEvent.BACK;
            }
            hashMap.put(MistatsConstants.BaseEvent.SENSOR_ID, str2);
            for (String str3 : map.keySet()) {
                String valueOf = String.valueOf(map.get(str3));
                miStatParams.putString(str3, valueOf);
                hashMap.put(str3, valueOf);
            }
            MiStat.trackEvent(str, miStatParams);
            if (sDumpStatEvent) {
                dumpEvent(str, hashMap);
            }
        }
    }

    public static void moduleMistatsEvent(String str, Map<String, String> map) {
        if (sIsEnabled) {
            MiStatParams miStatParams = new MiStatParams();
            HashMap hashMap = new HashMap();
            String str2 = "front";
            miStatParams.putString(MistatsConstants.BaseEvent.SENSOR_ID, CameraSettings.isFrontCamera() ? str2 : MistatsConstants.BaseEvent.BACK);
            if (!CameraSettings.isFrontCamera()) {
                str2 = MistatsConstants.BaseEvent.BACK;
            }
            hashMap.put(MistatsConstants.BaseEvent.SENSOR_ID, str2);
            for (String str3 : map.keySet()) {
                String valueOf = String.valueOf(map.get(str3));
                miStatParams.putString(str3, valueOf);
                hashMap.put(str3, valueOf);
            }
            MiStat.trackEvent(str, miStatParams);
            if (sDumpStatEvent) {
                dumpEvent(str, hashMap);
            }
        }
    }

    public static void moduleUIClickEvent(int i, String str, Object obj) {
        moduleUIClickEvent(getStatsModuleKey(i), str, obj);
    }

    public static void moduleUIClickEvent(String str, String str2, Object obj) {
        if (sIsEnabled) {
            MiStatParams miStatParams = new MiStatParams();
            HashMap hashMap = new HashMap();
            String valueOf = obj instanceof Boolean ? ((Boolean) obj).booleanValue() ? "on" : "off" : String.valueOf(obj);
            miStatParams.putString("attr_trigger_mode", "click");
            miStatParams.putString(MistatsConstants.BaseEvent.FEATURE_NAME, str2);
            miStatParams.putString(MistatsConstants.BaseEvent.VALUE, valueOf);
            String str3 = "front";
            miStatParams.putString(MistatsConstants.BaseEvent.SENSOR_ID, CameraSettings.isFrontCamera() ? str3 : MistatsConstants.BaseEvent.BACK);
            hashMap.put("attr_trigger_mode", "click");
            hashMap.put(MistatsConstants.BaseEvent.FEATURE_NAME, str2);
            hashMap.put(MistatsConstants.BaseEvent.VALUE, valueOf);
            if (!CameraSettings.isFrontCamera()) {
                str3 = MistatsConstants.BaseEvent.BACK;
            }
            hashMap.put(MistatsConstants.BaseEvent.SENSOR_ID, str3);
            MiStat.trackEvent(str, miStatParams);
            if (sDumpStatEvent) {
                dumpEvent(str, hashMap);
            }
        }
    }

    public static void recordPageEnd(String str) {
        if (sIsEnabled) {
            MiStat.trackPageEnd(str, new MiStatParams());
        }
    }

    public static void recordPageStart(String str) {
        if (sIsEnabled) {
            MiStat.trackPageStart(str);
        }
    }

    public static void recordPropertyEvent(String str, String str2) {
        if (sIsEnabled) {
            MiStat.setUserProperty(str, str2);
        }
    }

    public static void settingClickEvent(String str, Object obj) {
        if (str != null) {
            String valueOf = obj instanceof Boolean ? ((Boolean) obj).booleanValue() ? "on" : "off" : String.valueOf(obj);
            char c2 = 65535;
            if (str.hashCode() == 1694617033 && str.equals(MistatsConstants.Setting.PARAM_VIDEO_TIME_LAPSE_FRAME_INTERVAL)) {
                c2 = 0;
            }
            if (c2 == 0) {
                try {
                    valueOf = CameraStatUtils.timeLapseIntervalToName(Integer.parseInt(valueOf));
                } catch (NumberFormatException unused) {
                    String str2 = TAG;
                    Log.e(str2, "invalid interval " + valueOf);
                }
            }
            if (sIsEnabled) {
                MiStatParams miStatParams = new MiStatParams();
                miStatParams.putString("attr_trigger_mode", "click");
                miStatParams.putString(MistatsConstants.BaseEvent.FEATURE_NAME, str);
                if (valueOf != null) {
                    miStatParams.putString(MistatsConstants.BaseEvent.VALUE, valueOf);
                }
                MiStat.trackEvent(MistatsConstants.Setting.KEY_SETTING, miStatParams);
                if (sDumpStatEvent) {
                    HashMap hashMap = new HashMap();
                    hashMap.put(MistatsConstants.BaseEvent.FEATURE_NAME, str);
                    hashMap.put(MistatsConstants.BaseEvent.VALUE, valueOf);
                    dumpEvent(MistatsConstants.Setting.KEY_SETTING, hashMap);
                }
            }
        }
    }

    public static void settingSchedualEvent(Map<String, String> map) {
        if (sIsEnabled) {
            HashMap hashMap = new HashMap();
            MiStatParams miStatParams = new MiStatParams();
            miStatParams.putString("attr_trigger_mode", MistatsConstants.BaseEvent.SCHEDULE);
            hashMap.put("attr_trigger_mode", MistatsConstants.BaseEvent.SCHEDULE);
            for (String str : map.keySet()) {
                String valueOf = String.valueOf(map.get(str));
                miStatParams.putString(str, valueOf);
                hashMap.put(str, valueOf);
            }
            MiStat.trackEvent(MistatsConstants.Setting.KEY_SETTING, miStatParams);
            if (sDumpStatEvent) {
                dumpEvent(MistatsConstants.Setting.KEY_SETTING, hashMap);
            }
        }
    }
}
