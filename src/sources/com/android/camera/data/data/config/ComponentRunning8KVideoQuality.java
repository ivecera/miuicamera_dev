package com.android.camera.data.data.config;

import android.support.annotation.NonNull;
import android.util.ArrayMap;
import com.android.camera.R;
import com.android.camera.data.data.ComponentData;
import com.android.camera.data.data.ComponentDataItem;
import com.android.camera.data.data.runing.DataItemRunning;
import com.android.camera2.CameraCapabilities;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ComponentRunning8KVideoQuality extends ComponentData {
    private Map<String, Boolean> mCacheValues = new ArrayMap();
    private int mCameraId = 0;

    public ComponentRunning8KVideoQuality(DataItemRunning dataItemRunning) {
        super(dataItemRunning);
    }

    public void clearArrayMap() {
        this.mCacheValues.clear();
    }

    @Override // com.android.camera.data.data.ComponentData
    @NonNull
    public String getDefaultValue(int i) {
        return null;
    }

    @Override // com.android.camera.data.data.ComponentData
    public int getDisplayTitleString() {
        return R.string.video_ultra_clear_tip_8k;
    }

    @Override // com.android.camera.data.data.ComponentData
    public List<ComponentDataItem> getItems() {
        return ((ComponentData) this).mItems;
    }

    @Override // com.android.camera.data.data.ComponentData
    public String getKey(int i) {
        if (i == 162 || i == 180) {
            return "pref_camera_video_8k_" + i + "_" + this.mCameraId;
        }
        return "pref_camera_video_8k_unsupported_" + this.mCameraId;
    }

    public boolean isEnabled(int i) {
        Boolean bool = this.mCacheValues.get(getKey(i));
        if (bool == null) {
            return false;
        }
        return bool.booleanValue();
    }

    public void reInit(int i, int i2, CameraCapabilities cameraCapabilities) {
        this.mCameraId = i2;
        ArrayList arrayList = new ArrayList();
        if (i == 162 || i == 180) {
            arrayList.add(new ComponentDataItem((int) R.drawable.ic_config_video_8k_highlight, (int) R.drawable.ic_config_video_8k_normal, (int) R.string.video_ultra_clear_tip_8k, "on"));
        }
        ((ComponentData) this).mItems = Collections.unmodifiableList(arrayList);
    }

    public void setEnabled(int i, boolean z) {
        this.mCacheValues.put(getKey(i), Boolean.valueOf(z));
    }
}
