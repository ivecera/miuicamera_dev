package com.android.camera.data.cloud;

import android.content.SharedPreferences;
import com.android.camera.CameraAppImpl;
import com.android.camera.data.cloud.DataCloud;
import com.android.camera.data.data.config.SupportedConfigFactory;
import com.android.camera.data.data.config.SupportedConfigs;
import com.android.camera.log.Log;
import java.util.Collection;

public class DataCloudFeatureController implements DataCloud.CloudFeature {
    public static final String KEY = "cloud_feature";
    private static final String TAG = "DataCloudFeatureController";
    private SharedPreferences mPreferences;
    private boolean mReady;

    private SharedPreferences getSharedPreferences() {
        if (this.mPreferences == null) {
            initPreferences();
        }
        return this.mPreferences;
    }

    private void initPreferences() {
        this.mPreferences = CameraAppImpl.getAndroidContext().getSharedPreferences(provideKey(), 0);
    }

    @Override // com.android.camera.data.cloud.DataCloud.CloudFeature
    public SharedPreferences.Editor editor() {
        return getSharedPreferences().edit();
    }

    @Override // com.android.camera.data.cloud.DataCloud.CloudFeature
    public SupportedConfigs filterFeature(SupportedConfigs supportedConfigs) {
        if (!this.mReady) {
            return supportedConfigs;
        }
        SupportedConfigs supportedConfigs2 = new SupportedConfigs();
        SharedPreferences sharedPreferences = getSharedPreferences();
        for (int i = 0; i < supportedConfigs.getLength(); i++) {
            int config = supportedConfigs.getConfig(i);
            boolean z = true;
            try {
                z = sharedPreferences.getBoolean(SupportedConfigFactory.getConfigKey(config), true);
            } catch (RuntimeException unused) {
                String str = TAG;
                Log.w(str, "unknown config " + config);
            }
            if (z) {
                supportedConfigs2.add(config);
            }
        }
        return supportedConfigs2;
    }

    @Override // com.android.camera.data.cloud.DataCloud.CloudFeature
    public Collection<String> getAllDisabledFeatures() {
        return getSharedPreferences().getAll().keySet();
    }

    @Override // com.android.camera.data.cloud.DataCloud.CloudFeature
    public String provideKey() {
        return KEY;
    }

    @Override // com.android.camera.data.cloud.DataCloud.CloudFeature
    public void setReady(boolean z) {
        this.mReady = z;
    }
}
