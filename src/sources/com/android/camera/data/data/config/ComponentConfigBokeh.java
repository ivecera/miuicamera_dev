package com.android.camera.data.data.config;

import android.support.annotation.NonNull;
import com.android.camera.CameraSettings;
import com.android.camera.R;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.ComponentData;
import com.android.camera.data.data.ComponentDataItem;
import java.util.ArrayList;
import java.util.List;

public class ComponentConfigBokeh extends ComponentData {
    public static final String BOKEH_VALUE_OFF = "off";
    public static final String BOKEH_VALUE_ON = "on";

    public ComponentConfigBokeh(DataItemConfig dataItemConfig) {
        super(dataItemConfig);
    }

    private static List<ComponentDataItem> createItems(int i, int i2) {
        ArrayList arrayList = new ArrayList();
        boolean z = true;
        if (i2 != 1 || !DataRepository.dataItemFeature().c_0x38()) {
            z = false;
        }
        if (z) {
            arrayList.add(new ComponentDataItem((int) R.drawable.ic_portrait_button_normal, (int) R.drawable.ic_portrait_button_normal, (int) R.string.pref_camera_front_bokeh_entry_off, "off"));
            arrayList.add(new ComponentDataItem((int) R.drawable.ic_portrait_button_normal, (int) R.drawable.ic_portrait_button_normal, (int) R.string.pref_camera_front_bokeh_entry_on, "on"));
        }
        return arrayList;
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
        return ((ComponentData) this).mItems;
    }

    @Override // com.android.camera.data.data.ComponentData
    public String getKey(int i) {
        return CameraSettings.KEY_CAMERA_BOKEH + i;
    }

    /* access modifiers changed from: package-private */
    public void reInit(int i, int i2) {
        ((ComponentData) this).mItems = createItems(i, i2);
    }

    public void toggle(int i) {
        if ("on".equals(getComponentValue(i))) {
            setComponentValue(i, "off");
        } else {
            setComponentValue(i, "on");
        }
    }
}
