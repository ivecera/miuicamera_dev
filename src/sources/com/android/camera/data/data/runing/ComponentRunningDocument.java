package com.android.camera.data.data.runing;

import android.support.annotation.NonNull;
import com.android.camera.R;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.ComponentData;
import com.android.camera.data.data.ComponentDataItem;
import java.util.List;

public class ComponentRunningDocument extends ComponentData {
    public static final String DOCUMENT_BLACK_WHITE = "bin";
    public static final String DOCUMENT_ORIGIN = "raw";
    public static final String DOCUMENT_STRENGTHEN = "color";
    private int mCameraId;
    private DataItemRunning mDataItemRunning;
    private boolean mIsNormalIntent;

    public ComponentRunningDocument(DataItemRunning dataItemRunning) {
        super(dataItemRunning);
        this.mDataItemRunning = dataItemRunning;
    }

    @Override // com.android.camera.data.data.ComponentData
    @NonNull
    public String getDefaultValue(int i) {
        return "raw";
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
        return "pref_document_mode_value_key";
    }

    public int getResIcon(boolean z) {
        return z ? R.drawable.document_mode_selected : R.drawable.document_mode_normal;
    }

    public int getResText() {
        return R.string.pref_document_mode;
    }

    public boolean isEnabled(int i) {
        if (!DataRepository.dataItemFeature().ed()) {
            return false;
        }
        if ((i == 165 || i == 163) && this.mCameraId == 0 && this.mIsNormalIntent) {
            return this.mDataItemRunning.isSwitchOn("pref_document_mode_key");
        }
        return false;
    }

    public void reInit(int i, boolean z) {
        this.mCameraId = i;
        this.mIsNormalIntent = z;
    }

    public void setEnabled(int i, boolean z) {
        if (!z || !(i == 165 || i == 163)) {
            this.mDataItemRunning.switchOff("pref_document_mode_key");
        } else {
            this.mDataItemRunning.switchOn("pref_document_mode_key");
        }
    }
}
