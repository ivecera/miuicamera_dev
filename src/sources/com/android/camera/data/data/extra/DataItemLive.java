package com.android.camera.data.data.extra;

import com.android.camera.CameraSettings;
import com.android.camera.data.data.DataItemBase;
import com.android.camera.effect.FilterInfo;
import com.android.camera.features.mimoji2.widget.helper.MimojiStatusManager2;
import com.android.camera.fragment.vv.VVItem;
import com.android.camera.module.impl.component.ILive;
import com.android.camera.module.impl.component.MimojiStatusManager;
import com.android.camera.module.impl.component.TimeSpeedModel;
import java.util.List;

public class DataItemLive extends DataItemBase {
    public static final String DATA_LIVE_ACTIVATION = "live_activation";
    public static final String DATA_LIVE_START_ORIENTATION = "live_start_orientation";
    public static final String DATA_VV_VERSION = "vv_version";
    private static final String KEY = "camera_settings_live";
    private String mKaleidoscope = "0";
    private String mMiLiveFilterId = String.valueOf(FilterInfo.FILTER_ID_NONE);
    private List<ILive.ILiveSegmentData> mMiLiveSegmentData;
    private MimojiStatusManager mMimojiStatusManager;
    private MimojiStatusManager2 mMimojiStatusManager2;
    private List<TimeSpeedModel> mRecordSegmentTimeInfo;
    private VVItem mVVItem;

    public void clearAll() {
        this.mRecordSegmentTimeInfo = null;
        MimojiStatusManager mimojiStatusManager = this.mMimojiStatusManager;
        if (mimojiStatusManager != null) {
            mimojiStatusManager.reset();
        }
        MimojiStatusManager2 mimojiStatusManager2 = this.mMimojiStatusManager2;
        if (mimojiStatusManager2 != null) {
            mimojiStatusManager2.reset();
        }
        List<ILive.ILiveSegmentData> list = this.mMiLiveSegmentData;
        if (list != null) {
            list.clear();
            this.mMiLiveSegmentData = null;
        }
        this.mMiLiveFilterId = String.valueOf(FilterInfo.FILTER_ID_NONE);
        this.mKaleidoscope = "0";
    }

    public String getActivation() {
        return getString(DATA_LIVE_ACTIVATION, "");
    }

    public VVItem getCurrentVVItem() {
        return this.mVVItem;
    }

    public String getKaleidoscope() {
        return this.mKaleidoscope;
    }

    public int getLiveFilter() {
        return getInt(CameraSettings.KEY_LIVE_FILTER, 0);
    }

    public int getLiveStartOrientation() {
        return getInt(DATA_LIVE_START_ORIENTATION, 0);
    }

    public String getMiLiveFilterId() {
        return this.mMiLiveFilterId;
    }

    public List<ILive.ILiveSegmentData> getMiLiveSegmentData() {
        return this.mMiLiveSegmentData;
    }

    public MimojiStatusManager getMimojiStatusManager() {
        if (this.mMimojiStatusManager == null) {
            this.mMimojiStatusManager = new MimojiStatusManager();
        }
        return this.mMimojiStatusManager;
    }

    public MimojiStatusManager2 getMimojiStatusManager2() {
        if (this.mMimojiStatusManager2 == null) {
            this.mMimojiStatusManager2 = new MimojiStatusManager2();
        }
        return this.mMimojiStatusManager2;
    }

    public List<TimeSpeedModel> getRecordSegmentTimeInfo() {
        return this.mRecordSegmentTimeInfo;
    }

    public String getVVVersion() {
        return getString(DATA_VV_VERSION, "");
    }

    @Override // com.android.camera.data.provider.DataProvider.ProviderEvent
    public boolean isTransient() {
        return false;
    }

    @Override // com.android.camera.data.provider.DataProvider.ProviderEvent
    public String provideKey() {
        return KEY;
    }

    public void resetAll() {
        editor().clear().apply();
        clearAll();
    }

    public void setActivation(String str) {
        editor().putString(DATA_LIVE_ACTIVATION, str).apply();
    }

    public void setCurrentVVItem(VVItem vVItem) {
        this.mVVItem = vVItem;
    }

    public void setKaleidoscope(String str) {
        this.mKaleidoscope = str;
    }

    public void setLiveFilter(int i) {
        putInt(CameraSettings.KEY_LIVE_FILTER, i);
    }

    public void setLiveStartOrientation(int i) {
        putInt(DATA_LIVE_START_ORIENTATION, i);
    }

    public void setMiLiveFilterId(String str) {
        this.mMiLiveFilterId = str;
    }

    public void setMiLiveSegmentData(List<ILive.ILiveSegmentData> list) {
        this.mMiLiveSegmentData = list;
    }

    public void setRecordSegmentTimeInfo(List<TimeSpeedModel> list) {
        this.mRecordSegmentTimeInfo = list;
    }

    public void setVVVersion(String str) {
        editor().putString(DATA_VV_VERSION, str).apply();
    }
}
