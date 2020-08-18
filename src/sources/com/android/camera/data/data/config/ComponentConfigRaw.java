package com.android.camera.data.data.config;

import android.support.annotation.NonNull;
import android.util.SparseBooleanArray;
import com.android.camera.R;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.ComponentData;
import com.android.camera.data.data.ComponentDataItem;
import com.android.camera2.CameraCapabilities;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class ComponentConfigRaw extends ComponentData {
    private SparseBooleanArray mIsClosed;

    public ComponentConfigRaw(DataItemConfig dataItemConfig) {
        super(dataItemConfig);
    }

    @Override // com.android.camera.data.data.ComponentData
    @NonNull
    public String getDefaultValue(int i) {
        return null;
    }

    @Override // com.android.camera.data.data.ComponentData
    public int getDisplayTitleString() {
        return 0;
    }

    @Override // com.android.camera.data.data.ComponentData
    public List<ComponentDataItem> getItems() {
        return ((ComponentData) this).mItems;
    }

    @Override // com.android.camera.data.data.ComponentData
    public String getKey(int i) {
        return "pref_camera_raw_key";
    }

    public boolean isClosed(int i) {
        SparseBooleanArray sparseBooleanArray = this.mIsClosed;
        if (sparseBooleanArray == null) {
            return false;
        }
        return sparseBooleanArray.get(i);
    }

    public boolean isSwitchOn(int i) {
        if (!isEmpty() && !isClosed(i)) {
            return ((ComponentData) this).mParentDataItem.getBoolean(getKey(i), false);
        }
        return false;
    }

    public void reInit(int i, int i2, CameraCapabilities cameraCapabilities) {
        ArrayList arrayList = new ArrayList();
        if (i == 167 && cameraCapabilities.isSupportRaw() && DataRepository.dataItemFeature().c_r_i_m_m()) {
            arrayList.add(new ComponentDataItem((int) R.drawable.ic_raw_off, (int) R.drawable.ic_raw_on, -1, "on"));
        }
        ((ComponentData) this).mItems = Collections.unmodifiableList(arrayList);
    }

    public void setClosed(boolean z, int i) {
        if (this.mIsClosed == null) {
            this.mIsClosed = new SparseBooleanArray();
        }
        this.mIsClosed.put(i, z);
    }

    public void setRaw(int i, boolean z) {
        if (!isEmpty()) {
            setClosed(false, i);
            ((ComponentData) this).mParentDataItem.editor().putBoolean(getKey(i), z).apply();
        }
    }
}
