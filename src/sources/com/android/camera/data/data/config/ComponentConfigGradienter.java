package com.android.camera.data.data.config;

import android.support.annotation.NonNull;
import com.android.camera.data.data.ComponentData;
import com.android.camera.data.data.ComponentDataItem;
import com.android.camera.data.provider.DataProvider;
import java.util.List;

public class ComponentConfigGradienter extends ComponentData {
    private static final String SCOPE_NS_PHOTO = "photo";
    private static final String SCOPE_NS_PHOTO_PRO = "photo_pro";
    private static final String SCOPE_NS_UNSUPPORTED = "unsupported";
    private static final String SCOPE_NS_VIDEO = "video";
    private static final String SCOPE_NS_VIDEO_PRO = "video_pro";
    private static final String TAG = "CCGradienter";
    public static final String VALUE_GRADIENTER_OFF = "off";
    public static final String VALUE_GRADIENTER_ON = "on";
    private int mCameraId;
    private int mCapturingMode;

    public ComponentConfigGradienter(DataItemConfig dataItemConfig, int i) {
        super(dataItemConfig);
        this.mCameraId = i;
    }

    @Override // com.android.camera.data.data.ComponentData
    public String getComponentValue(int i) {
        return (this.mCameraId != 1 && !getKey(i).endsWith(SCOPE_NS_UNSUPPORTED)) ? super.getComponentValue(i) : "off";
    }

    @Override // com.android.camera.data.data.ComponentData
    @NonNull
    public String getDefaultValue(int i) {
        return "off";
    }

    @Override // com.android.camera.data.data.ComponentData
    public int getDisplayTitleString() {
        return 0;
    }

    @Override // com.android.camera.data.data.ComponentData
    public List<ComponentDataItem> getItems() {
        throw new UnsupportedOperationException("CCGradienter#getItems() not supported");
    }

    @Override // com.android.camera.data.data.ComponentData
    public String getKey(int i) {
        return (i == 163 || i == 165) ? "pref_camera_gradienter_key_photo" : i == 167 ? "pref_camera_gradienter_key_photo_pro" : (i == 169 || i == 168 || i == 162) ? "pref_camera_gradienter_key_video" : i == 180 ? "pref_camera_gradienter_key_video_pro" : "pref_camera_gradienter_key_unsupported";
    }

    public void reInit(int i, int i2) {
        this.mCapturingMode = i;
        this.mCameraId = i2;
    }

    public void reset(DataProvider.ProviderEditor providerEditor) {
        providerEditor.putString(getKey(163), "off");
        providerEditor.putString(getKey(167), "off");
        providerEditor.putString(getKey(162), "off");
        providerEditor.putString(getKey(180), "off");
    }

    @Override // com.android.camera.data.data.ComponentData
    public void setComponentValue(int i, String str) {
        if (this.mCameraId != 1 && !getKey(i).endsWith(SCOPE_NS_UNSUPPORTED)) {
            super.setComponentValue(i, str);
        }
    }
}
