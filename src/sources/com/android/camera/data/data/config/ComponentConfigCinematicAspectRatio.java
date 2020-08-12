package com.android.camera.data.data.config;

import android.support.annotation.NonNull;
import com.android.camera.R;
import com.android.camera.data.data.ComponentData;
import com.android.camera.data.data.ComponentDataItem;
import com.android.camera.data.provider.DataProvider;
import com.android.camera2.CameraCapabilities;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ComponentConfigCinematicAspectRatio extends ComponentData {
    public ComponentConfigCinematicAspectRatio(DataItemConfig dataItemConfig) {
        super(dataItemConfig);
    }

    @Override // com.android.camera.data.data.ComponentData
    @NonNull
    public String getDefaultValue(int i) {
        return null;
    }

    @Override // com.android.camera.data.data.ComponentData
    public int getDisplayTitleString() {
        return R.string.moive_frame;
    }

    @Override // com.android.camera.data.data.ComponentData
    public List<ComponentDataItem> getItems() {
        return ((ComponentData) this).mItems;
    }

    @Override // com.android.camera.data.data.ComponentData
    public String getKey(int i) {
        if (i == 162 || i == 169) {
            return "is_cinematic_162";
        }
        if (i != 171 && i != 180) {
            return "is_cinematic_unsupported";
        }
        return "is_cinematic_" + i;
    }

    public boolean isEnabled(int i) {
        if (i == 171 || i == 180 || i == 162 || i == 169) {
            return ((ComponentData) this).mParentDataItem.getBoolean(getKey(i), false);
        }
        return false;
    }

    public void reInit(int i, int i2, CameraCapabilities cameraCapabilities) {
        ArrayList arrayList = new ArrayList();
        if (i == 162 || i == 169 || i == 171 || i == 180) {
            arrayList.add(new ComponentDataItem((int) R.drawable.ic_cinematic_aspect_ratio_off, (int) R.drawable.ic_cinematic_aspect_ratio_on, (int) R.string.accessibility_mimovie_on, "on"));
        }
        ((ComponentData) this).mItems = Collections.unmodifiableList(arrayList);
    }

    public void reset(DataProvider.ProviderEditor providerEditor) {
        providerEditor.putBoolean(getKey(162), false);
        providerEditor.putBoolean(getKey(180), false);
        providerEditor.putBoolean(getKey(171), false);
    }

    public void setEnabled(int i, boolean z) {
        ((ComponentData) this).mParentDataItem.putBoolean(getKey(i), z);
    }
}
