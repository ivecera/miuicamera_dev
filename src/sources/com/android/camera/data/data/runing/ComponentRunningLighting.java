package com.android.camera.data.data.runing;

import com.android.camera.CameraSettings;
import com.android.camera.R;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.ComponentData;
import com.android.camera.data.data.ComponentDataItem;
import com.android.camera.module.loader.camera2.Camera2DataContainer;
import java.util.ArrayList;
import java.util.List;

public class ComponentRunningLighting extends ComponentData {
    public ComponentRunningLighting(DataItemRunning dataItemRunning) {
        super(dataItemRunning);
    }

    @Override // com.android.camera.data.data.ComponentData
    public String getDefaultValue(int i) {
        return "0";
    }

    @Override // com.android.camera.data.data.ComponentData
    public int getDisplayTitleString() {
        return 0;
    }

    @Override // com.android.camera.data.data.ComponentData
    public List<ComponentDataItem> getItems() {
        if (((ComponentData) this).mItems == null) {
            initItems();
        }
        return ((ComponentData) this).mItems;
    }

    @Override // com.android.camera.data.data.ComponentData
    public String getKey(int i) {
        return "pref_portrait_lighting";
    }

    public void initItems() {
        if (((ComponentData) this).mItems == null) {
            ((ComponentData) this).mItems = new ArrayList();
        } else {
            ((ComponentData) this).mItems.clear();
        }
        ((ComponentData) this).mItems.add(new ComponentDataItem((int) R.drawable.ic_lighting_none, (int) R.drawable.ic_lighting_none, (int) R.string.lighting_pattern_null, "0"));
        if (CameraSettings.isBackCamera() || Camera2DataContainer.getInstance().getBokehFrontCameraId() != -1) {
            ((ComponentData) this).mItems.add(new ComponentDataItem((int) R.drawable.ic_lighting_nature, (int) R.drawable.ic_lighting_nature, (int) R.string.lighting_pattern_nature, "1"));
        }
        ((ComponentData) this).mItems.add(new ComponentDataItem((int) R.drawable.ic_lighting_stage, (int) R.drawable.ic_lighting_stage, (int) R.string.lighting_pattern_stage, "2"));
        ((ComponentData) this).mItems.add(new ComponentDataItem((int) R.drawable.ic_lighting_movie, (int) R.drawable.ic_lighting_movie, (int) R.string.lighting_pattern_movie, "3"));
        ((ComponentData) this).mItems.add(new ComponentDataItem((int) R.drawable.ic_lighting_rainbow, (int) R.drawable.ic_lighting_rainbow, (int) R.string.lighting_pattern_rainbow, "4"));
        ((ComponentData) this).mItems.add(new ComponentDataItem((int) R.drawable.ic_lighting_shutter, (int) R.drawable.ic_lighting_shutter, (int) R.string.lighting_pattern_shutter, "5"));
        ((ComponentData) this).mItems.add(new ComponentDataItem((int) R.drawable.ic_lighting_dot, (int) R.drawable.ic_lighting_dot, (int) R.string.lighting_pattern_dot, "6"));
        ((ComponentData) this).mItems.add(new ComponentDataItem((int) R.drawable.ic_lighting_leaf, (int) R.drawable.ic_lighting_leaf, (int) R.string.lighting_pattern_leaf, "7"));
        if (DataRepository.dataItemFeature().Ye()) {
            ((ComponentData) this).mItems.add(new ComponentDataItem((int) R.drawable.ic_lighting_holi, (int) R.drawable.ic_lighting_holi, (int) R.string.lighting_pattern_holi, "8"));
        }
    }

    public boolean isSwitchOn(int i) {
        return !getComponentValue(i).equals("0");
    }
}
