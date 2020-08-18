package com.android.camera.module.impl.component;

import android.content.Context;
import android.view.SurfaceView;
import android.view.TextureView;
import com.android.camera.ActivityBase;
import com.android.camera.log.Log;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.ss.android.vesdk.VECommonCallback;
import com.ss.android.vesdk.VEEditor;
import com.ss.android.vesdk.VEVideoEncodeSettings;

public class LiveVideoEditorTTImpl implements ModeProtocol.LiveVideoEditor {
    private static final String TAG = "LiveVideoEditorTTImpl";
    private Context mContext;
    private VEEditor mEditor;
    private int mEncodeHeight;
    private int mEncodeWidth;
    private boolean mNeedPrepare = true;
    private int mOrientation;
    private SurfaceView mSurfaceView;
    private String mVideoSavePath;

    private LiveVideoEditorTTImpl(ActivityBase activityBase) {
        this.mContext = activityBase.getCameraAppImpl().getApplicationContext();
    }

    public static LiveVideoEditorTTImpl create(ActivityBase activityBase) {
        return new LiveVideoEditorTTImpl(activityBase);
    }

    @Override // com.android.camera.protocol.ModeProtocol.LiveVideoEditor
    public void combineVideoAudio(String str, VECommonCallback vECommonCallback, VECommonCallback vECommonCallback2) {
        if (this.mEditor == null) {
            Log.d(TAG, "mEditor destroyed. skip");
            return;
        }
        this.mNeedPrepare = true;
        String str2 = TAG;
        Log.d(str2, "combine video, width = " + this.mEncodeWidth + ", height = " + this.mEncodeHeight + "orientation = " + this.mOrientation);
        this.mEditor.compile(str, null, new VEVideoEncodeSettings.Builder(2).setCompileType(VEVideoEncodeSettings.COMPILE_TYPE.COMPILE_TYPE_MP4).setVideoRes(this.mEncodeWidth, this.mEncodeHeight).setRotate(this.mOrientation).setHwEnc(true).setGopSize(30).setVideoBitrate(VEVideoEncodeSettings.ENCODE_BITRATE_MODE.ENCODE_BITRATE_ABR, 4194304).setFps(30).build());
    }

    @Override // com.android.camera.protocol.ModeProtocol.LiveVideoEditor
    public boolean init(TextureView textureView, String str, String str2, VECommonCallback vECommonCallback, VECommonCallback vECommonCallback2) {
        Log.v(TAG, "VEEditor init");
        this.mEditor = new VEEditor(FileUtils.ROOT_DIR, textureView);
        int init = this.mEditor.init(new String[]{str}, null, str2.equals("") ^ true ? new String[]{str2} : null, VEEditor.VIDEO_RATIO.VIDEO_OUT_RATIO_ORIGINAL);
        if (init != 0) {
            String str3 = TAG;
            Log.e(str3, "Video editor init failed, ret = " + init);
            VEEditor vEEditor = this.mEditor;
            if (vEEditor != null) {
                vEEditor.destroy();
                this.mEditor = null;
            }
            return false;
        }
        this.mEditor.setOnInfoListener(vECommonCallback);
        this.mEditor.setOnErrorListener(vECommonCallback2);
        this.mEditor.setLoopPlay(false);
        this.mEditor.setScaleMode(VEEditor.SCALE_MODE.SCALE_MODE_CENTER_CROP);
        this.mEditor.prepare();
        this.mNeedPrepare = false;
        this.mEditor.addAudioTrack(str2, 0, 15000, true);
        this.mEditor.seek(0, VEEditor.SEEK_MODE.EDITOR_SEEK_FLAG_LastSeek);
        return true;
    }

    @Override // com.android.camera.protocol.ModeProtocol.LiveVideoEditor
    public void onDestory() {
        Log.v(TAG, "VEEditor onDestory");
        VEEditor vEEditor = this.mEditor;
        if (vEEditor != null) {
            vEEditor.setOnErrorListener(null);
            this.mEditor.setOnInfoListener(null);
            this.mEditor.destroy();
            this.mEditor = null;
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.LiveVideoEditor
    public void pausePlay() {
        VEEditor vEEditor = this.mEditor;
        if (vEEditor != null) {
            vEEditor.pause();
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.BaseProtocol
    public void registerProtocol() {
        ModeCoordinatorImpl.getInstance().attachProtocol(209, this);
    }

    @Override // com.android.camera.protocol.ModeProtocol.LiveVideoEditor
    public void resumePlay() {
        VEEditor vEEditor = this.mEditor;
        if (vEEditor != null) {
            vEEditor.seek(0, VEEditor.SEEK_MODE.EDITOR_SEEK_FLAG_LastSeek);
            this.mEditor.play();
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.LiveVideoEditor
    public void setRecordParameter(int i, int i2, int i3) {
        String str = TAG;
        Log.d(str, "setRecordParameter:  " + i + " | " + i2 + " | " + i3);
        this.mOrientation = Math.max(0, i3);
        int i4 = this.mOrientation;
        if (i4 == 90 || i4 == 270) {
            this.mEncodeWidth = i2;
            this.mEncodeHeight = i;
            return;
        }
        this.mEncodeWidth = i;
        this.mEncodeHeight = i2;
    }

    @Override // com.android.camera.protocol.ModeProtocol.LiveVideoEditor
    public void startPlay() {
        VEEditor vEEditor = this.mEditor;
        if (vEEditor != null) {
            if (this.mNeedPrepare) {
                vEEditor.prepare();
                this.mNeedPrepare = false;
            }
            this.mEditor.setLoopPlay(true);
            this.mEditor.play();
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.BaseProtocol
    public void unRegisterProtocol() {
        ModeCoordinatorImpl.getInstance().detachProtocol(209, this);
        onDestory();
    }
}
