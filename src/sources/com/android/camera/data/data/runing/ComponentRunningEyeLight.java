package com.android.camera.data.data.runing;

import android.support.annotation.NonNull;
import com.android.camera.data.data.ComponentData;
import com.android.camera.data.data.ComponentDataItem;
import java.util.List;

public class ComponentRunningEyeLight extends ComponentData {
    private boolean mIsClosed;

    public ComponentRunningEyeLight(DataItemRunning dataItemRunning) {
        super(dataItemRunning);
    }

    @Override // com.android.camera.data.data.ComponentData
    public String getComponentValue(int i) {
        return isClosed() ? "-1" : super.getComponentValue(i);
    }

    @Override // com.android.camera.data.data.ComponentData
    @NonNull
    public String getDefaultValue(int i) {
        return "-1";
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
        return "pref_eye_light_type_key";
    }

    public boolean isClosed() {
        return this.mIsClosed;
    }

    public void setClosed(boolean z) {
        this.mIsClosed = z;
    }
}
