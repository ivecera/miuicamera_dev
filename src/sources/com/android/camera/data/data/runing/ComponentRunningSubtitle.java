package com.android.camera.data.data.runing;

import android.support.annotation.NonNull;
import com.android.camera.R;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.ComponentData;
import com.android.camera.data.data.ComponentDataItem;
import java.util.List;

public class ComponentRunningSubtitle extends ComponentData {
    private boolean isSupported;
    private boolean mIsNormalIntent;

    public ComponentRunningSubtitle(DataItemRunning dataItemRunning) {
        super(dataItemRunning);
    }

    public void clearArrayMap() {
        this.isSupported = false;
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
            return "pref_video_subtitle_key";
        }
        return "pref_video_subtitle_key_" + Integer.toHexString(i);
    }

    public int getResIcon(boolean z) {
        return z ? R.drawable.ic_config_subtitle_on : R.drawable.ic_config_subtitle;
    }

    public int getResText() {
        return R.string.pref_video_subtitle;
    }

    public boolean isEnabled(int i) {
        if (DataRepository.dataItemFeature().Pd() && i == 162 && this.mIsNormalIntent) {
            return this.isSupported;
        }
        return false;
    }

    public void reInit(int i, boolean z) {
        this.mIsNormalIntent = z;
    }

    public void reInitIntentType(boolean z) {
        this.mIsNormalIntent = z;
    }

    public void setEnabled(int i, boolean z) {
        if (i != 162) {
            this.isSupported = false;
        } else {
            this.isSupported = z;
        }
    }
}
