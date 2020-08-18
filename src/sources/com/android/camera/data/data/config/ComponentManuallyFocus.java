package com.android.camera.data.data.config;

import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import com.android.camera.CameraSettings;
import com.android.camera.R;
import com.android.camera.data.data.ComponentData;
import com.android.camera.data.data.ComponentDataItem;
import java.util.ArrayList;
import java.util.List;

public class ComponentManuallyFocus extends ComponentData {
    private boolean mIsFixedFocus;

    public ComponentManuallyFocus(DataItemConfig dataItemConfig) {
        super(dataItemConfig);
    }

    private List<ComponentDataItem> initItems() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new ComponentDataItem(-1, -1, (int) R.string.pref_camera_focusmode_entry_auto_abbr, "1000"));
        arrayList.add(new ComponentDataItem(-1, -1, (int) R.string.pref_camera_focusmode_entry_auto_abbr, "manual"));
        return arrayList;
    }

    @Override // com.android.camera.data.data.ComponentData
    public boolean disableUpdate() {
        return this.mIsFixedFocus;
    }

    @Override // com.android.camera.data.data.ComponentData
    public String getDefaultValue(int i) {
        return "1000";
    }

    @Override // com.android.camera.data.data.ComponentData
    @StringRes
    public int getDefaultValueDisplayString(int i) {
        return R.string.pref_camera_focusmode_entry_auto_abbr;
    }

    @Override // com.android.camera.data.data.ComponentData
    public int getDisplayTitleString() {
        return R.string.pref_qc_focus_position_title_abbr;
    }

    @Override // com.android.camera.data.data.ComponentData
    public List<ComponentDataItem> getItems() {
        if (((ComponentData) this).mItems == null) {
            ((ComponentData) this).mItems = initItems();
        }
        return ((ComponentData) this).mItems;
    }

    @Override // com.android.camera.data.data.ComponentData
    public String getKey(int i) {
        return i != 167 ? CameraSettings.KEY_QC_PRO_VIDEO_FOCUS_POSITION : CameraSettings.KEY_QC_FOCUS_POSITION;
    }

    @Override // com.android.camera.data.data.ComponentData
    @StringRes
    public int getValueDisplayString(int i) {
        if (Integer.valueOf(getComponentValue(i)).intValue() == 1000) {
            return R.string.pref_camera_focusmode_entry_auto_abbr;
        }
        return -1;
    }

    @Override // com.android.camera.data.data.ComponentData
    @DrawableRes
    public int getValueSelectedDrawable(int i) {
        int intValue = Integer.valueOf(getComponentValue(i)).intValue();
        if (intValue == 1000) {
            return -1;
        }
        double d2 = (double) intValue;
        return d2 >= 600.0d ? R.drawable.ic_focusmode_macro : d2 >= 200.0d ? R.drawable.ic_focusmode_normal : R.drawable.ic_focusmode_infinity;
    }

    public void setFixedFocusLens(boolean z) {
        this.mIsFixedFocus = z;
    }
}
