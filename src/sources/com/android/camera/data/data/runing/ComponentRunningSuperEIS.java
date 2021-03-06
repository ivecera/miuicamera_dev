package com.android.camera.data.data.runing;

import android.support.annotation.NonNull;
import android.util.ArrayMap;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.ComponentData;
import com.android.camera.data.data.ComponentDataItem;
import java.util.List;
import java.util.Map;

public class ComponentRunningSuperEIS extends ComponentData {
    private int mCameraId;
    private boolean mIsNormalIntent;
    private Map<String, Boolean> mValues = new ArrayMap();

    public ComponentRunningSuperEIS(DataItemRunning dataItemRunning) {
        super(dataItemRunning);
    }

    public void clearArrayMap() {
        this.mValues.clear();
    }

    @Override // com.android.camera.data.data.ComponentData
    @NonNull
    public String getDefaultValue(int i) {
        return Boolean.toString(false);
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
        if (i == 162 || i == 169) {
            return "pref_camera_super_eis";
        }
        return "pref_camera_super_eis_" + Integer.toHexString(i);
    }

    public boolean isEnabled(int i) {
        Boolean bool;
        if (DataRepository.dataItemFeature().c_22367_0x0007() && this.mIsNormalIntent && this.mCameraId == 0 && i == 162 && (bool = this.mValues.get(getKey(i))) != null) {
            return bool.booleanValue();
        }
        return false;
    }

    public void reInit(int i, boolean z) {
        this.mCameraId = i;
        this.mIsNormalIntent = z;
    }

    public void reInitIntentType(boolean z) {
        this.mIsNormalIntent = z;
    }

    public void setEnabled(int i, boolean z) {
        this.mValues.put(getKey(i), Boolean.valueOf(z));
    }
}
