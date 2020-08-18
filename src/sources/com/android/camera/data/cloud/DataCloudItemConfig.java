package com.android.camera.data.cloud;

public class DataCloudItemConfig extends DataCloudItemBase {
    public static final String KEY = "cloud_item_";
    private int mCameraId;

    public DataCloudItemConfig(int i) {
        this.mCameraId = i;
    }

    @Override // com.android.camera.data.cloud.DataCloud.CloudItem
    public String provideKey() {
        return KEY + this.mCameraId;
    }
}
