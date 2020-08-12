package com.android.camera.data.data.runing;

import android.support.annotation.NonNull;
import com.android.camera.R;
import com.android.camera.data.data.ComponentData;
import com.android.camera.data.data.ComponentDataItem;
import com.android.camera.data.data.DataItemBase;
import java.util.List;

public class ComponentRunningDualVideo extends ComponentData {
    private boolean mIsEnabled = false;

    public <D extends DataItemBase> ComponentRunningDualVideo(D d2) {
        super(d2);
    }

    private static int getDrawableId(String str) {
        try {
            return R.drawable.class.getField(str).getInt(null);
        } catch (Exception e2) {
            e2.printStackTrace();
            return -1;
        }
    }

    private static int getStringId(String str) {
        try {
            return R.string.class.getField(str).getInt(null);
        } catch (Exception e2) {
            e2.printStackTrace();
            return -1;
        }
    }

    @Override // com.android.camera.data.data.ComponentData
    @NonNull
    public String getDefaultValue(int i) {
        return String.valueOf(false);
    }

    @Override // com.android.camera.data.data.ComponentData
    public int getDisplayTitleString() {
        return getStringId("pref_dual_video");
    }

    @Override // com.android.camera.data.data.ComponentData
    public List<ComponentDataItem> getItems() {
        return null;
    }

    @Override // com.android.camera.data.data.ComponentData
    public String getKey(int i) {
        return "pref_dual_video_key";
    }

    public int getResIcon(boolean z) {
        return z ? getDrawableId("ic_config_dual_video_on") : getDrawableId("ic_config_dual_video_off");
    }

    public boolean isEnabled(int i) {
        return this.mIsEnabled;
    }

    public void setEnabled(boolean z) {
        this.mIsEnabled = z;
    }
}
