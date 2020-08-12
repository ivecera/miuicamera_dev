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

public class ComponentConfigMacro extends ComponentData {
    public static final String MACRO_VALUE_OFF = "off";
    public static final String MACRO_VALUE_ON = "on";
    private SparseBooleanArray mIsClosed;

    public ComponentConfigMacro(DataItemConfig dataItemConfig) {
        super(dataItemConfig);
    }

    public void clearClosed() {
        SparseBooleanArray sparseBooleanArray = this.mIsClosed;
        if (sparseBooleanArray != null) {
            sparseBooleanArray.clear();
        }
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
        return "pref_camera_macro_scene_mode_key";
    }

    public int getValueSelectedStringIdIgnoreClose(int i) {
        String componentValue = getComponentValue(i);
        if ("on".equals(componentValue)) {
            return R.string.accessibility_flash_on;
        }
        if ("off".equals(componentValue)) {
            return R.string.accessibility_flash_auto;
        }
        return -1;
    }

    public boolean isClosed(int i) {
        SparseBooleanArray sparseBooleanArray = this.mIsClosed;
        if (sparseBooleanArray == null) {
            return false;
        }
        return sparseBooleanArray.get(i);
    }

    public boolean isMacroOn(int i) {
        if (!isEmpty() && !isClosed(i)) {
            return ((ComponentData) this).mParentDataItem.getBoolean(getKey(i), DataRepository.dataItemFeature().a_e_d());
        }
        return false;
    }

    public List<ComponentDataItem> reInit(int i, int i2, CameraCapabilities cameraCapabilities) {
        ArrayList arrayList = new ArrayList();
        if (!(i == 162 || i == 163 || i == 165)) {
        }
        ((ComponentData) this).mItems = Collections.unmodifiableList(arrayList);
        return ((ComponentData) this).mItems;
    }

    public void setClosed(boolean z, int i) {
        if (this.mIsClosed == null) {
            this.mIsClosed = new SparseBooleanArray();
        }
        this.mIsClosed.put(i, z);
    }

    public void setMacroScene(int i, boolean z) {
        if (!isEmpty()) {
            setClosed(false, i);
            ((ComponentData) this).mParentDataItem.editor().putBoolean(getKey(i), z).apply();
        }
    }
}
