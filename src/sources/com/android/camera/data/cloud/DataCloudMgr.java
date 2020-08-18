package com.android.camera.data.cloud;

import android.app.Application;
import android.content.SharedPreferences;
import android.os.Build;
import android.text.TextUtils;
import android.util.SparseArray;
import com.android.camera.CameraAppImpl;
import com.android.camera.CameraSettings;
import com.android.camera.aiwatermark.util.WatermarkConstant;
import com.android.camera.data.DataRepository;
import com.android.camera.data.cloud.DataCloud;
import com.android.camera.log.Log;
import com.android.camera.network.util.NetworkUtils;
import com.android.camera.sensitive.SensitiveFilter;
import com.android.camera.statistic.MistatsConstants;
import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import java.io.File;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DataCloudMgr implements DataCloud.CloudManager {
    private static final String CLOUD_DATA_MODULE_NAME = "camera_settings_v3";
    private static final long CLOUD_DATA_UPDATE_INTERVAL = 86400000;
    private static final String FEA_SENSI_WORD = "pref_senstive_words";
    private static final String KEY_CLOUD_DATA_DEVICE_VERSION = "cloud_data_device_version";
    private static final String KEY_CLOUD_DATA_LAST_UPDATE = "cloud_data_last_update";
    private static final String KEY_CLOUD_DATA_VERSION = "cloud_data_version";
    private static final String TAG = "DataCloudMgr";
    private static final String VALUE = "value";
    private static final String VERSION = "version";
    private DataCloudFeatureController mDataCloudFeatureController;
    private DataCloudItemGlobal mDataCloudGlobal = new DataCloudItemGlobal();
    private SparseArray<DataCloudItemConfig> mDataCloudItemConfigs = new SparseArray<>(2);

    private static void checkLastUpdateTimeFormat() {
        try {
            getCloudDataLastUpdateTime();
        } catch (Exception unused) {
            Log.w(TAG, "get cloud_data_last_update long value failed, try String type");
            try {
                String string = DataRepository.dataItemGlobal().getString(KEY_CLOUD_DATA_LAST_UPDATE, "0");
                Log.i(TAG, "try to convert the string value type to long for field [cloud_data_last_update]");
                DataRepository.dataItemGlobal().editor().remove(KEY_CLOUD_DATA_LAST_UPDATE).putLong(KEY_CLOUD_DATA_LAST_UPDATE, TextUtils.isEmpty(string) ? 0 : Long.parseLong(string)).commit();
            } catch (Exception unused2) {
                Log.w(TAG, "get cloud_data_last_update string value failed");
            }
        }
    }

    /* access modifiers changed from: private */
    public void fillPreference() {
        DataCloud.CloudItem provideDataCloudConfig = provideDataCloudConfig(1);
        DataCloud.CloudItem provideDataCloudConfig2 = provideDataCloudConfig(0);
        getCloudSensitiveWords();
        getCloudDataString(CLOUD_DATA_MODULE_NAME, "version", null);
        getCloudDataCommonVersion();
        getCloudDataString("camera_settings_v3_" + Build.DEVICE, "version", null);
        getCloudDataDeviceVersion();
        this.mDataCloudGlobal.setReady(false);
        DataCloudFeature().setReady(false);
        provideDataCloudConfig.setReady(false);
        provideDataCloudConfig2.setReady(false);
        this.mDataCloudGlobal.setReady(true);
        DataCloudFeature().setReady(true);
        provideDataCloudConfig.setReady(true);
        provideDataCloudConfig2.setReady(true);
    }

    private static String getCloudDataCommonVersion() {
        return DataRepository.dataItemGlobal().getString(KEY_CLOUD_DATA_VERSION, null);
    }

    private static String getCloudDataDeviceVersion() {
        return DataRepository.dataItemGlobal().getString(KEY_CLOUD_DATA_DEVICE_VERSION, null);
    }

    private static final int getCloudDataInt(String str, String str2, int i) {
        CameraAppImpl.getAndroidContext().getContentResolver();
        return i;
    }

    private static long getCloudDataLastUpdateTime() {
        return DataRepository.dataItemGlobal().getLong(KEY_CLOUD_DATA_LAST_UPDATE, 0);
    }

    private static final String getCloudDataString(String str, String str2, String str3) {
        CameraAppImpl.getAndroidContext().getContentResolver();
        return str3;
    }

    private void getCloudSensitiveWords() {
        getCloudDataString(CLOUD_DATA_MODULE_NAME, FEA_SENSI_WORD, "");
        if (!TextUtils.isEmpty("")) {
            try {
                JSONObject jSONObject = new JSONObject("");
                updateSensitiveWords(jSONObject.optInt("version"), jSONObject.optString("value"));
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
    }

    private static void setCloudDataCommonVersion(String str) {
        DataRepository.dataItemGlobal().editor().putString(KEY_CLOUD_DATA_VERSION, str).apply();
    }

    private static void setCloudDataDeviceVersion(String str) {
        DataRepository.dataItemGlobal().editor().putString(KEY_CLOUD_DATA_DEVICE_VERSION, str).apply();
    }

    private static void setCloudDataLastUpdateTime(long j) {
        DataRepository.dataItemGlobal().editor().putLong(KEY_CLOUD_DATA_LAST_UPDATE, j).apply();
    }

    private void updateSensitiveWords(int i, String str) {
        String keyCloudSenstiveWordsPath = CameraSettings.getKeyCloudSenstiveWordsPath(str);
        File file = new File(SensitiveFilter.CLOUD_FILE_PATH);
        if (i > CameraSettings.getLocalWordsVersion() || (!file.exists() && i != 0)) {
            NetworkUtils.bind((Application) CameraAppImpl.getAndroidContext());
            if (NetworkUtils.isNetworkConnected() && SensitiveFilter.loadSensitiveWords(keyCloudSenstiveWordsPath, SensitiveFilter.LOCAL_FILE_PATH)) {
                CameraSettings.setLocalWordsVersion(i);
            }
        }
    }

    private void updateSettingsFromCloudData(String str) {
        try {
            getCloudDataString(str, "content", null);
            if (!TextUtils.isEmpty(null)) {
                JSONArray jSONArray = new JSONArray((String) null);
                for (int i = 0; i < jSONArray.length(); i++) {
                    DataCloudItemBase dataCloudItemBase = this.mDataCloudGlobal;
                    JSONObject jSONObject = jSONArray.getJSONObject(i);
                    if (jSONObject.has("type")) {
                        String string = jSONObject.getString("type");
                        if (string.equals("front")) {
                            dataCloudItemBase = this.mDataCloudItemConfigs.get(1);
                        } else if (string.equals(MistatsConstants.BaseEvent.BACK)) {
                            dataCloudItemBase = this.mDataCloudItemConfigs.get(0);
                        }
                    }
                    String string2 = jSONObject.getString(WatermarkConstant.ITEM_KEY);
                    SharedPreferences.Editor editor = dataCloudItemBase.editor();
                    Object obj = jSONObject.get("value");
                    if (obj instanceof Boolean) {
                        editor.putBoolean(string2, ((Boolean) obj).booleanValue());
                    } else if (obj instanceof Integer) {
                        editor.putInt(string2, ((Integer) obj).intValue());
                    } else if (obj instanceof Long) {
                        editor.putLong(string2, ((Long) obj).longValue());
                    } else if (obj instanceof Float) {
                        editor.putFloat(string2, ((Float) obj).floatValue());
                    } else if (obj instanceof String) {
                        editor.putString(string2, (String) obj);
                    } else {
                        Log.e(TAG, "Wrong camera setting type " + string2 + ": " + obj);
                    }
                    editor.apply();
                }
            }
            getCloudDataString(str, "feature", null);
            if (!TextUtils.isEmpty(null)) {
                SharedPreferences.Editor editor2 = DataCloudFeature().editor();
                JSONArray jSONArray2 = new JSONArray((String) null);
                for (int i2 = 0; i2 < jSONArray2.length(); i2++) {
                    JSONObject jSONObject2 = jSONArray2.getJSONObject(i2);
                    editor2.putBoolean(jSONObject2.getString(WatermarkConstant.ITEM_KEY), jSONObject2.getBoolean("value"));
                }
                editor2.apply();
            }
        } catch (JSONException e2) {
            Log.e(TAG, "JSONException when get camera settings !", e2);
        }
    }

    @Override // com.android.camera.data.cloud.DataCloud.CloudManager
    public DataCloud.CloudFeature DataCloudFeature() {
        if (this.mDataCloudFeatureController == null) {
            this.mDataCloudFeatureController = new DataCloudFeatureController();
        }
        return this.mDataCloudFeatureController;
    }

    @Override // com.android.camera.data.cloud.DataCloud.CloudManager
    public void fillCloudValues() {
        long currentTimeMillis = System.currentTimeMillis();
        checkLastUpdateTimeFormat();
        if (currentTimeMillis - getCloudDataLastUpdateTime() < 86400000) {
            this.mDataCloudGlobal.setReady(true);
            DataCloudFeature().setReady(true);
            provideDataCloudConfig(1).setReady(true);
            provideDataCloudConfig(0).setReady(true);
            return;
        }
        setCloudDataLastUpdateTime(currentTimeMillis);
        Completable.create(new CompletableOnSubscribe() {
            /* class com.android.camera.data.cloud.DataCloudMgr.AnonymousClass1 */

            @Override // io.reactivex.CompletableOnSubscribe
            public void subscribe(@NonNull CompletableEmitter completableEmitter) throws Exception {
                DataCloudMgr.this.fillPreference();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe();
    }

    @Override // com.android.camera.data.cloud.DataCloud.CloudManager
    public DataCloud.CloudItem provideDataCloudConfig(int i) {
        DataCloudItemConfig dataCloudItemConfig = this.mDataCloudItemConfigs.get(i);
        if (dataCloudItemConfig != null) {
            return dataCloudItemConfig;
        }
        DataCloudItemConfig dataCloudItemConfig2 = new DataCloudItemConfig(i);
        this.mDataCloudItemConfigs.put(i, dataCloudItemConfig2);
        return dataCloudItemConfig2;
    }

    @Override // com.android.camera.data.cloud.DataCloud.CloudManager
    public DataCloud.CloudItem provideDataCloudGlobal() {
        if (this.mDataCloudGlobal == null) {
            this.mDataCloudGlobal = new DataCloudItemGlobal();
        }
        return this.mDataCloudGlobal;
    }
}
