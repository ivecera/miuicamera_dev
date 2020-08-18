package com.android.camera.data.data.config;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.android.camera.data.data.ComponentData;
import com.android.camera.data.data.ComponentDataItem;
import com.android.camera.data.provider.DataProvider;
import java.util.List;

@Deprecated
public class ComponentConfigBeautyBody extends ComponentData {
    private static final int DEF_BEAUTY_BODY_VALUE = 0;
    private boolean mIsClosed;

    public ComponentConfigBeautyBody(DataItemConfig dataItemConfig) {
        super(dataItemConfig);
    }

    public int getBeautyBodyValue(String str, int i) {
        return isClosed() ? i : ((ComponentData) this).mParentDataItem.getInt(str, i);
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
        return null;
    }

    @Override // com.android.camera.data.data.ComponentData
    public String getKey(int i) {
        if (i == 161 || i == 162) {
            return "_video";
        }
        return null;
    }

    public boolean isBeautyBodyKey(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        char c2 = 65535;
        switch (str.hashCode()) {
            case -1735290593:
                if (str.equals("pref_beauty_body_slim_ratio")) {
                    c2 = 1;
                    break;
                }
                break;
            case -1518569458:
                if (str.equals("pref_beauty_butt_slim_ratio")) {
                    c2 = 5;
                    break;
                }
                break;
            case -1262324777:
                if (str.equals("pref_beauty_whole_body_slim_ratio")) {
                    c2 = 4;
                    break;
                }
                break;
            case -146567779:
                if (str.equals("key_beauty_leg_slim_ratio")) {
                    c2 = 3;
                    break;
                }
                break;
            case 1709402593:
                if (str.equals("pref_beauty_shoulder_slim_ratio")) {
                    c2 = 2;
                    break;
                }
                break;
            case 1945143841:
                if (str.equals("pref_beauty_head_slim_ratio")) {
                    c2 = 0;
                    break;
                }
                break;
        }
        return c2 == 0 || c2 == 1 || c2 == 2 || c2 == 3 || c2 == 4 || c2 == 5;
    }

    public boolean isClosed() {
        return this.mIsClosed;
    }

    public void resetBeautyBody(int i, DataProvider.ProviderEditor providerEditor) {
        if (((ComponentData) this).mParentDataItem.getInt("pref_beauty_head_slim_ratio", 0) > 0) {
            providerEditor.putInt("pref_beauty_head_slim_ratio", 0);
        }
        if (((ComponentData) this).mParentDataItem.getInt("pref_beauty_body_slim_ratio", 0) > 0) {
            providerEditor.putInt("pref_beauty_body_slim_ratio", 0);
        }
        if (((ComponentData) this).mParentDataItem.getInt("pref_beauty_shoulder_slim_ratio", 0) > 0) {
            providerEditor.putInt("pref_beauty_shoulder_slim_ratio", 0);
        }
        if (((ComponentData) this).mParentDataItem.getInt("key_beauty_leg_slim_ratio", 0) > 0) {
            providerEditor.putInt("key_beauty_leg_slim_ratio", 0);
        }
        if (((ComponentData) this).mParentDataItem.getInt("pref_beauty_whole_body_slim_ratio", 0) > 0) {
            providerEditor.putInt("pref_beauty_whole_body_slim_ratio", 0);
        }
        if (((ComponentData) this).mParentDataItem.getInt("pref_beauty_butt_slim_ratio", 0) > 0) {
            providerEditor.putInt("pref_beauty_butt_slim_ratio", 0);
        }
    }

    public void setBeautyBodyValue(String str, int i) {
        setClosed(false);
        ((ComponentData) this).mParentDataItem.putInt(str, i).apply();
    }

    public void setClosed(boolean z) {
        this.mIsClosed = z;
    }
}
