package com.android.camera.data.cloud;

import android.content.SharedPreferences;
import com.android.camera.data.data.config.SupportedConfigs;
import java.util.Collection;

public interface DataCloud {

    public interface CloudFeature {
        SharedPreferences.Editor editor();

        SupportedConfigs filterFeature(SupportedConfigs supportedConfigs);

        Collection<String> getAllDisabledFeatures();

        String provideKey();

        void setReady(boolean z);
    }

    public interface CloudItem {
        SharedPreferences.Editor editor();

        boolean getCloudBooleanDefault(String str, boolean z);

        float getCloudFloatDefault(String str, float f2);

        int getCloudIntDefault(String str, int i);

        long getCloudLongDefault(String str, long j);

        String getCloudStringDefault(String str, String str2);

        String provideKey();

        void setReady(boolean z);
    }

    public interface CloudManager {
        CloudFeature DataCloudFeature();

        void fillCloudValues();

        CloudItem provideDataCloudConfig(int i);

        CloudItem provideDataCloudGlobal();
    }
}
