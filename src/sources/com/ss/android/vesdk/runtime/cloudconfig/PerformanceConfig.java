package com.ss.android.vesdk.runtime.cloudconfig;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RestrictTo;
import android.util.Log;
import com.ss.android.ttve.monitor.DeviceInfoDetector;
import com.ss.android.ttve.monitor.MonitorUtils;
import com.ss.android.vesdk.runtime.VERuntime;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

public class PerformanceConfig {
    public static final String BASE_URL_CHINA = "https://effect.snssdk.com/devicehub/getSettings?";
    public static final String BASE_URL_SINGAPORE = "https://sg-effect.byteoversea.com/devicehub/getSetting?";
    public static final String BASE_URL_TEST = "https://effect.snssdk.com/devicehub/getSettings/?";
    public static final String BASE_URL_US = "https://va-effect.byteoversea.com/devicehub/getSettings?";
    public static final int DISABLE = 2;
    public static final int ENABLE = 1;
    private static final String TAG = "PerfConfig";
    public static final int UNDEFINED = 0;
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public static int gServerLocation = 0;
    private static final String sConfigs = "ShortVideoConfig";
    /* access modifiers changed from: private */
    public static IInjector sInjector = new AutoInjector();
    private static final String sPerfConfigPrefix = "PerfConfig_";
    public static final VECloudConfig sVECloudConfig = new VECloudConfig();

    private static class ConfigAsyncTask extends AsyncTask<Void, Void, Void> {
        private ConfigAsyncTask() {
        }

        /* access modifiers changed from: protected */
        public Void doInBackground(Void... voidArr) {
            try {
                Context context = VERuntime.getInstance().getContext();
                Map<String, String> map = DeviceInfoDetector.toMap();
                map.put("package_name", context.getPackageName());
                map.put(MonitorUtils.KEY_MODEL, Build.MODEL);
                map.put("os_version", Build.VERSION.RELEASE);
                Locale locale = Locale.getDefault();
                if (locale != null) {
                    map.put("locale", (locale.getCountry() == null ? "" : locale.getCountry()).toLowerCase());
                }
                map.put("platform", "android");
                String body = HttpRequest.get((CharSequence) PerformanceConfig.getServerUrl(), (Map<?, ?>) map, true).body();
                Log.d(PerformanceConfig.TAG, "cloud config result = " + body);
                try {
                    JSONObject jSONObject = new JSONObject(body);
                    if (PerformanceConfig.sInjector != null) {
                        Map<String, String> parse = PerformanceConfig.sInjector.parse(jSONObject);
                        if (parse != null) {
                            PerformanceConfig.setPerformanceConfig(VERuntime.getInstance().getContext(), parse);
                            return null;
                        }
                        Log.e(PerformanceConfig.TAG, "Parse json result failed! ");
                        return null;
                    }
                    Log.e(PerformanceConfig.TAG, "Injector == null. VECloudConfig is not initialized!");
                    throw new IllegalStateException("Injector == null. VECloudConfig is not initialized!");
                } catch (JSONException e2) {
                    Log.e(PerformanceConfig.TAG, "Parse json result failed! ", e2);
                    e2.printStackTrace();
                    return null;
                }
            } catch (Exception e3) {
                Log.e(PerformanceConfig.TAG, "Fetch config failed! ", e3);
                return null;
            }
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Void voidR) {
        }
    }

    public static void fetch() {
        new ConfigAsyncTask().execute(new Void[0]);
    }

    public static Map<String, String> getPerformanceConfig(Context context) {
        HashMap hashMap = new HashMap();
        for (Map.Entry<String, ?> entry : context.getSharedPreferences(sConfigs, 0).getAll().entrySet()) {
            if (entry.getKey().startsWith(sPerfConfigPrefix)) {
                hashMap.put(entry.getKey().substring(11), (String) entry.getValue());
            }
        }
        return hashMap;
    }

    /* access modifiers changed from: private */
    public static String getServerUrl() {
        int i = gServerLocation;
        return i != 0 ? i != 2 ? i != 3 ? BASE_URL_CHINA : BASE_URL_SINGAPORE : BASE_URL_US : BASE_URL_CHINA;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public static void restoreFromCache() {
        if (VERuntime.getInstance().getContext() != null) {
            setConfigsFromMap(getPerformanceConfig(VERuntime.getInstance().getContext()));
            return;
        }
        throw new IllegalStateException("Must call VideoSdkCore.init() before.");
    }

    private static void setConfigsFromMap(Map<String, String> map) {
        IInjector iInjector = sInjector;
        if (iInjector != null) {
            iInjector.inject(map, sVECloudConfig);
        } else {
            Log.e(TAG, "Injector == null. VECloudConfig is not initialized!");
            throw new IllegalStateException("CompileTimeBUG: Injector == null. VECloudConfig won't be initialized!. Consider specify an IInjector instance before compile code.");
        }
    }

    public static void setPerformanceConfig(Context context, Map<String, String> map) {
        if (map != null) {
            Set<Map.Entry<String, String>> entrySet = map.entrySet();
            SharedPreferences.Editor edit = context.getSharedPreferences(sConfigs, 0).edit();
            for (Map.Entry<String, String> entry : entrySet) {
                edit.putString(sPerfConfigPrefix + entry.getKey(), entry.getValue());
            }
            edit.apply();
        }
    }

    public static void setServerLocation(int i) {
        if (i == 0 || i == 2 || i == 3) {
            gServerLocation = i;
        } else {
            Log.e(TAG, "SetServerLocation error", new IllegalArgumentException("Only china, us, and sea server is acceptable"));
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public static void updateConfig(Map<String, String> map) {
        setConfigsFromMap(map);
        setPerformanceConfig(VERuntime.getInstance().getContext(), map);
    }
}
