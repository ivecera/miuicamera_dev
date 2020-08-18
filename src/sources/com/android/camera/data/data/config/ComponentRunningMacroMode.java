package com.android.camera.data.data.config;

import android.support.annotation.NonNull;
import android.util.ArrayMap;
import com.android.camera.R;
import com.android.camera.data.data.ComponentData;
import com.android.camera.data.data.ComponentDataItem;
import com.android.camera.data.data.runing.DataItemRunning;
import java.util.List;
import java.util.Map;

public class ComponentRunningMacroMode extends ComponentData {
    public static final String VALUE_OFF = "OFF";
    public static final String VALUE_ON = "ON";
    private Map<String, Boolean> mCacheValues = new ArrayMap();
    private int mCameraId;
    public List<ComponentDataItem> mItems;

    public ComponentRunningMacroMode(DataItemRunning dataItemRunning) {
        super(dataItemRunning);
    }

    private String getMode(int i, int i2) {
        if (165 == i) {
            i = 163;
        }
        if (169 == i) {
            i = 162;
        }
        return String.valueOf(i) + "_" + i2;
    }

    public void clearArrayMap() {
        this.mCacheValues.clear();
    }

    @Override // com.android.camera.data.data.ComponentData
    @NonNull
    public String getDefaultValue(int i) {
        return "OFF";
    }

    @Override // com.android.camera.data.data.ComponentData
    public int getDisplayTitleString() {
        return 0;
    }

    @Override // com.android.camera.data.data.ComponentData
    public List<ComponentDataItem> getItems() {
        return null;
    }

    @Override // com.android.camera.data.data.ComponentData
    public String getKey(int i) {
        return "pref_camera_macro_scene_mode_key";
    }

    public int getResIcon(boolean z) {
        return z ? R.drawable.ic_menu_macro_mode_on : R.drawable.ic_menu_macro_mode_off;
    }

    public int getResText() {
        return R.string.macro_mode;
    }

    public boolean isCacheSwitchOn(int i, int i2) {
        Boolean bool = this.mCacheValues.get(getMode(i, i2));
        if (bool == null) {
            return false;
        }
        return bool.booleanValue();
    }

    public boolean isSwitchOn(int i) {
        return isCacheSwitchOn(i, this.mCameraId);
    }

    public boolean isSwitchOn(int i, int i2) {
        return isCacheSwitchOn(i, i2);
    }

    public void reInit(int i, boolean z) {
        this.mCameraId = i;
    }

    public void setSwitchOff(int i) {
        this.mCacheValues.put(getMode(i, this.mCameraId), false);
    }

    public void setSwitchOn(int i) {
        this.mCacheValues.put(getMode(i, this.mCameraId), true);
    }
}
