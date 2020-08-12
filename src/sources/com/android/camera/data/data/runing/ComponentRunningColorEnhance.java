package com.android.camera.data.data.runing;

import android.support.annotation.NonNull;
import com.android.camera.R;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.ComponentData;
import com.android.camera.data.data.ComponentDataItem;
import com.android.camera.data.data.DataItemBase;
import com.android.camera2.CameraCapabilities;
import java.util.List;

public class ComponentRunningColorEnhance extends ComponentData {
    private boolean mIsEnabled = false;
    private boolean mRecordValue = false;

    public <D extends DataItemBase> ComponentRunningColorEnhance(D d2) {
        super(d2);
    }

    private boolean getValue(int i, int i2, CameraCapabilities cameraCapabilities) {
        if (cameraCapabilities == null) {
            return false;
        }
        if (i != 163 && i != 165) {
            return false;
        }
        if (!(i2 == 0) || !cameraCapabilities.isSupportedColorEnhance() || !supportColorEnhance()) {
            return false;
        }
        return this.mRecordValue;
    }

    @Override // com.android.camera.data.data.ComponentData
    @NonNull
    public String getDefaultValue(int i) {
        return String.valueOf(false);
    }

    @Override // com.android.camera.data.data.ComponentData
    public int getDisplayTitleString() {
        return R.string.pro_color_mode;
    }

    @Override // com.android.camera.data.data.ComponentData
    public List<ComponentDataItem> getItems() {
        return ((ComponentData) this).mItems;
    }

    @Override // com.android.camera.data.data.ComponentData
    public String getKey(int i) {
        return "pref_color_enhance";
    }

    public int getResIcon(boolean z) {
        return z ? R.drawable.ic_color_enhance_on : R.drawable.ic_color_enhance_off;
    }

    public boolean isEnabled(int i) {
        if (i == 163 || i == 165) {
            return this.mIsEnabled;
        }
        return false;
    }

    public void reInit(int i, int i2, CameraCapabilities cameraCapabilities) {
        this.mIsEnabled = getValue(i, i2, cameraCapabilities);
    }

    public void reset(boolean z) {
        this.mIsEnabled = z;
        this.mRecordValue = z;
    }

    public void setEnabled(boolean z, int i) {
        this.mIsEnabled = z;
        if (i == 1) {
            this.mRecordValue = this.mIsEnabled;
        }
    }

    public boolean supportColorEnhance() {
        return DataRepository.dataItemFeature().supportColorEnhance();
    }
}
