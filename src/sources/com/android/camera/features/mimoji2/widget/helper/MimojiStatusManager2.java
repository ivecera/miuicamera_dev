package com.android.camera.features.mimoji2.widget.helper;

import com.android.camera.CameraSettings;
import com.android.camera.data.data.DataItemBase;
import com.android.camera.features.mimoji2.bean.MimojiBgInfo;
import com.android.camera.features.mimoji2.bean.MimojiInfo2;
import com.android.camera.features.mimoji2.bean.MimojiTimbreInfo;

public class MimojiStatusManager2 extends DataItemBase {
    public static final String DATA_MIMOJI_RECORD_STATE = "mimoji_record_state";
    private static final String KEY = "camera_settings_mimoji";
    public static final int MIMOJI_CREATE = 4;
    public static final int MIMOJI_EDIT = 6;
    public static final int MIMOJI_EMOTICON = 8;
    public static final int MIMOJI_NONE = 0;
    public static final int MIMOJI_PANEL_AVATAR = 1;
    public static final int MIMOJI_PANEL_BG = 2;
    public static final int MIMOJI_PANEL_CLOSE = 0;
    public static final int MIMOJI_PANEL_TIMBRE = 3;
    public static final int MIMOJI_PHOTO = 0;
    public static final int MIMOJI_PREVIEW = 2;
    public static final int MIMOJI_VIDEO = 1;
    private MimojiBgInfo mCurrentMimojiBgInfo;
    private MimojiInfo2 mCurrentMimojiInfo2;
    private MimojiTimbreInfo mCurrentMimojiTimbreInfo;
    private volatile boolean mIsGifState = false;
    private volatile boolean mIsLoading = false;
    private int mMode = 0;
    private volatile int mPanelState = 0;

    public boolean IsLoading() {
        return this.mIsLoading;
    }

    public MimojiBgInfo getCurrentMimojiBgInfo() {
        return this.mCurrentMimojiBgInfo;
    }

    public MimojiInfo2 getCurrentMimojiInfo() {
        return this.mCurrentMimojiInfo2;
    }

    public synchronized String getCurrentMimojiState() {
        if (this.mCurrentMimojiInfo2 == null) {
            return "close_state";
        }
        String str = this.mCurrentMimojiInfo2.mConfigPath;
        return (str == null || str.isEmpty()) ? "close_state" : str;
    }

    public MimojiTimbreInfo getCurrentMimojiTimbreInfo() {
        return this.mCurrentMimojiTimbreInfo;
    }

    public int getMimojiPanelState() {
        return this.mPanelState;
    }

    public int getMimojiRecordState() {
        return getInt(DATA_MIMOJI_RECORD_STATE, 0);
    }

    public synchronized int getMode() {
        return this.mMode;
    }

    public boolean isGifState() {
        return this.mIsGifState;
    }

    public boolean isInMimojiCreate() {
        return this.mMode == 4;
    }

    public boolean isInMimojiEdit() {
        return this.mMode == 6;
    }

    public boolean isInMimojiEmoticon() {
        return this.mMode == 8;
    }

    public boolean isInMimojiPreview() {
        return this.mMode <= 2;
    }

    public boolean isInPreviewSurface() {
        return this.mMode <= 4;
    }

    @Override // com.android.camera.data.provider.DataProvider.ProviderEvent
    public boolean isTransient() {
        return false;
    }

    @Override // com.android.camera.data.provider.DataProvider.ProviderEvent
    public String provideKey() {
        return KEY;
    }

    public synchronized void reset() {
        this.mMode = 0;
        setMimojiPanelState(0);
        this.mCurrentMimojiBgInfo = null;
        this.mCurrentMimojiInfo2 = null;
        this.mCurrentMimojiTimbreInfo = null;
        if (!CameraSettings.retainCameraMode()) {
            setMimojiRecordState(0);
        }
    }

    public void setCurrentMimojiBgInfo(MimojiBgInfo mimojiBgInfo) {
        this.mCurrentMimojiBgInfo = mimojiBgInfo;
    }

    public void setCurrentMimojiCloseState() {
        if (this.mCurrentMimojiInfo2 == null) {
            this.mCurrentMimojiInfo2 = new MimojiInfo2();
        }
        this.mCurrentMimojiInfo2.mConfigPath = "close_state";
    }

    public void setCurrentMimojiInfo(MimojiInfo2 mimojiInfo2) {
        this.mCurrentMimojiInfo2 = mimojiInfo2;
    }

    public void setCurrentMimojiTimbreInfo(MimojiTimbreInfo mimojiTimbreInfo) {
        this.mCurrentMimojiTimbreInfo = mimojiTimbreInfo;
    }

    public void setIsGifState(boolean z) {
        this.mIsGifState = z;
    }

    public synchronized void setIsLoading(boolean z) {
        this.mIsLoading = z;
    }

    public void setMimojiPanelState(int i) {
        this.mPanelState = i;
    }

    public void setMimojiRecordState(int i) {
        editor().putInt(DATA_MIMOJI_RECORD_STATE, i).apply();
    }

    public synchronized void setMode(int i) {
        this.mMode = i;
    }
}
