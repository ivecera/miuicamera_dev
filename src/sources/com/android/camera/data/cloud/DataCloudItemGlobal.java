package com.android.camera.data.cloud;

public class DataCloudItemGlobal extends DataCloudItemBase {
    private static final String KEY = "cloud_global";

    @Override // com.android.camera.data.cloud.DataCloud.CloudItem
    public String provideKey() {
        return KEY;
    }
}
