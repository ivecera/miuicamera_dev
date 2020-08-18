package com.android.camera.module.impl.component;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.SurfaceTexture;
import android.media.Image;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import com.android.camera.ActivityBase;
import com.android.camera.CameraScreenNail;
import com.android.camera.CameraSettings;
import com.android.camera.CameraSize;
import com.android.camera.SurfaceTextureScreenNail;
import com.android.camera.Util;
import com.android.camera.data.DataRepository;
import com.android.camera.effect.EffectController;
import com.android.camera.effect.draw_mode.DrawExtTexAttribute;
import com.android.camera.fragment.beauty.LiveBeautyFilterFragment;
import com.android.camera.log.Log;
import com.android.camera.module.AudioController;
import com.android.camera.module.MiLiveModule;
import com.android.camera.module.impl.component.ILive;
import com.android.camera.module.impl.component.MiLiveRecorder;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera2.Camera2Proxy;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class MiLiveConfigChangesImpl implements ModeProtocol.MiLiveConfigChanges, ILive.ILiveRecorderStateListener {
    private static final int DEFAULT_FPS = 30;
    private static final int DEFAULT_RECORD_BITRATE = 31457280;
    private static final float DEFAULT_SPEED = 1.0f;
    private static final long RECORD_TIME_COMPENSATION = 100;
    private final float[] SPEEDS = {0.33f, 0.5f, 1.0f, 2.0f, 3.0f};
    private final String TAG = (MiLiveConfigChangesImpl.class.getSimpleName() + "@" + hashCode());
    /* access modifiers changed from: private */
    public ActivityBase mActivity;
    private AudioController mAudioController;
    private Context mContext;
    private String mCurAudioPath;
    private float mCurSpeed;
    private int mCurrentOrientation = -1;
    private String mFilterBitmapPath;
    private int mRecordState = 0;
    private ILive.ILiveRecorder mRecorder;
    private ModeProtocol.MiLiveRecorderControl.IRecorderListener mRecorderListener;
    private ILive.ILiveRecordingTimeListener mRecordingTimeListener = new ILive.ILiveRecordingTimeListener() {
        /* class com.android.camera.module.impl.component.MiLiveConfigChangesImpl.AnonymousClass1 */

        @Override // com.android.camera.module.impl.component.ILive.ILiveRecordingTimeListener
        public void onRecordingTimeFinish() {
            if (MiLiveConfigChangesImpl.this.mActivity != null && MiLiveConfigChangesImpl.this.mActivity.getCurrentModule() != null && (MiLiveConfigChangesImpl.this.mActivity.getCurrentModule() instanceof MiLiveModule)) {
                ((MiLiveModule) MiLiveConfigChangesImpl.this.mActivity.getCurrentModule()).stopVideoRecording(false, true);
            }
        }

        @Override // com.android.camera.module.impl.component.ILive.ILiveRecordingTimeListener
        public void onRecordingTimeUpdate(long j, float f2) {
            ModeProtocol.ActionProcessing actionProcessing = (ModeProtocol.ActionProcessing) ModeCoordinatorImpl.getInstance().getAttachProtocol(162);
            if (actionProcessing != null) {
                actionProcessing.updateRecordingTime(Util.millisecondToTimeString((long) ((((float) j) * 1.0f) / f2), false));
            }
        }
    };
    private SurfaceTextureScreenNail.SurfaceTextureScreenNailCallback mRender;
    private Handler mUIHandler;

    private MiLiveConfigChangesImpl(ActivityBase activityBase) {
        this.mActivity = activityBase;
        this.mContext = activityBase.getApplicationContext();
        this.mAudioController = new AudioController(this.mActivity.getApplicationContext());
    }

    public static MiLiveConfigChangesImpl create(ActivityBase activityBase) {
        return new MiLiveConfigChangesImpl(activityBase);
    }

    public /* synthetic */ void Xf() {
        ILive.ILiveRecorder iLiveRecorder;
        ModeProtocol.MiLiveRecorderControl.IRecorderListener iRecorderListener = this.mRecorderListener;
        if (iRecorderListener != null && (iLiveRecorder = this.mRecorder) != null) {
            iRecorderListener.onRecorderPaused(iLiveRecorder.getLiveSegments());
        }
    }

    public /* synthetic */ void Yf() {
        ILive.ILiveRecorder iLiveRecorder;
        ModeProtocol.MiLiveRecorderControl.IRecorderListener iRecorderListener = this.mRecorderListener;
        if (iRecorderListener != null && (iLiveRecorder = this.mRecorder) != null) {
            iRecorderListener.onRecorderFinish(iLiveRecorder.getLiveSegments(), this.mCurAudioPath);
            this.mRecorder.getLiveSegments().clear();
        }
    }

    public /* synthetic */ void Zf() {
        ModeProtocol.MiLiveRecorderControl.IRecorderListener iRecorderListener = this.mRecorderListener;
        if (iRecorderListener != null && this.mRecorder != null) {
            iRecorderListener.onRecorderError();
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.LiveRecordState
    public boolean canRecordingStop() {
        return this.mRecorder != null && ((float) (System.currentTimeMillis() - this.mRecorder.getStartTime())) > this.mCurSpeed * 500.0f;
    }

    @Override // com.android.camera.protocol.ModeProtocol.LiveAudioChanges
    public void clearAudio() {
        this.mCurAudioPath = "";
        ILive.ILiveRecorder iLiveRecorder = this.mRecorder;
        if (iLiveRecorder != null) {
            iLiveRecorder.setAudioPath(this.mCurAudioPath);
            CameraSettings.setCurrentLiveMusic(null, null);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.MiLiveRecorderControl
    public void deleteLastFragment() {
        ModeProtocol.MiLiveRecorderControl.IRecorderListener iRecorderListener;
        ILive.ILiveRecorder iLiveRecorder = this.mRecorder;
        if (iLiveRecorder != null) {
            iLiveRecorder.deletePreSegment();
            if (this.mRecorder.getLiveSegments().isEmpty() && (iRecorderListener = this.mRecorderListener) != null) {
                iRecorderListener.onRecorderCancel();
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.LiveGenericControl
    public CameraSize getAlgorithmPreviewSize(List<CameraSize> list) {
        return Util.getOptimalVideoSnapshotPictureSize(list, (double) getPreviewRatio(), 176, 144);
    }

    @Override // com.android.camera.protocol.ModeProtocol.LiveAudioChanges
    public String getAudioPath() {
        return this.mCurAudioPath;
    }

    @Override // com.android.camera.protocol.ModeProtocol.LiveGenericControl
    @SuppressLint({"WrongConstant"})
    public int getAuthResult() {
        return 0;
    }

    @Override // com.android.camera.protocol.ModeProtocol.MiLiveRecorderControl
    public int getCurState() {
        int i = this.mRecordState;
        int i2 = 1;
        if (i != 1) {
            i2 = 2;
            if (i != 2) {
                if (i != 3) {
                    if (i != 4) {
                        if (i != 6) {
                            if (i != 7) {
                                return 0;
                            }
                        }
                    }
                }
                return 3;
            }
        }
        return i2;
    }

    @Override // com.android.camera.protocol.ModeProtocol.LiveModuleSub
    public SurfaceTexture getInputSurfaceTexture() {
        ILive.ILiveRecorder iLiveRecorder = this.mRecorder;
        if (iLiveRecorder != null) {
            return iLiveRecorder.genInputSurfaceTexture();
        }
        Log.e(this.TAG, "genInputSurfaceTexture null");
        return null;
    }

    @Override // com.android.camera.protocol.ModeProtocol.LiveGenericControl
    public float getPreviewRatio() {
        return 1.7777777f;
    }

    @Override // com.android.camera.protocol.ModeProtocol.LiveSpeedChanges
    public float getRecordSpeed() {
        return this.mCurSpeed;
    }

    @Override // com.android.camera.protocol.ModeProtocol.LiveRecordState
    public int getSegmentSize() {
        ILive.ILiveRecorder iLiveRecorder = this.mRecorder;
        if (iLiveRecorder != null) {
            return iLiveRecorder.getLiveSegments().size();
        }
        return 0;
    }

    @Override // com.android.camera.protocol.ModeProtocol.LiveSpeedChanges
    public long getStartRecordingTime() {
        ILive.ILiveRecorder iLiveRecorder = this.mRecorder;
        if (iLiveRecorder != null) {
            return iLiveRecorder.getStartTime();
        }
        return 0;
    }

    @Override // com.android.camera.protocol.ModeProtocol.LiveSpeedChanges
    public String getTimeValue() {
        return Util.millisecondToTimeString(Util.clamp(getTotalRecordingTime(), 1000, 15000), false, true);
    }

    @Override // com.android.camera.protocol.ModeProtocol.LiveSpeedChanges
    public long getTotalRecordingTime() {
        ILive.ILiveRecorder iLiveRecorder = this.mRecorder;
        if (iLiveRecorder != null) {
            return ILive.getTotalDuration(iLiveRecorder.getLiveSegments());
        }
        return 0;
    }

    @Override // com.android.camera.protocol.ModeProtocol.LiveModuleSub
    public void initPreview(int i, int i2, int i3, CameraScreenNail cameraScreenNail) {
        if (this.mRecorder == null) {
            MiLiveRecorder build = new MiLiveRecorder.Builder(this.mActivity, i, i2).setStateListener(this).setHandler(this.mUIHandler).setRecordingTimeListener(this.mRecordingTimeListener).setMaxDuration(15100).setBitrate(DEFAULT_RECORD_BITRATE).setFps(30).setVideoSaveDirPath(FileUtils.VIDEO_TMP).setSegmentData(DataRepository.dataItemLive().getMiLiveSegmentData()).build();
            this.mRender = build;
            this.mRecorder = build;
        }
        this.mRecorder.init(i, i2);
        LiveBeautyFilterFragment.LiveFilterItem findLiveFilter = EffectController.getInstance().findLiveFilter(this.mActivity, DataRepository.dataItemLive().getLiveFilter());
        String str = "";
        setFilter(true, findLiveFilter != null ? findLiveFilter.directoryName : str);
        setRecordSpeed(Integer.valueOf(CameraSettings.getCurrentLiveSpeed()).intValue());
        String[] currentLiveMusic = CameraSettings.getCurrentLiveMusic();
        if (!currentLiveMusic[0].isEmpty()) {
            str = currentLiveMusic[0];
        }
        setAudioPath(str);
    }

    @Override // com.android.camera.protocol.ModeProtocol.LiveGenericControl
    public void initResource() {
        Log.d(this.TAG, "initResource");
        if (!FileUtils.hasDir(FileUtils.ROOT_DIR) || !FileUtils.makeSureNoMedia(FileUtils.RESOURCE_DIR)) {
            FileUtils.makeNoMediaDir(FileUtils.RESOURCE_DIR);
            FileUtils.makeNoMediaDir(FileUtils.VIDEO_TMP);
            FileUtils.makeNoMediaDir(FileUtils.MUSIC_LOCAL);
            FileUtils.makeNoMediaDir(FileUtils.MUSIC_ONLINE);
            FileUtils.makeNoMediaDir(FileUtils.ROOT_DIR);
        }
        String str = Util.isGlobalVersion() ? "music_global.zip" : "music_cn.zip";
        try {
            Context context = this.mContext;
            Util.verifyAssetZip(context, "live/" + str, FileUtils.MUSIC_LOCAL, 32768);
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.LiveRecordState
    public boolean isRecording() {
        return getCurState() == 2;
    }

    @Override // com.android.camera.protocol.ModeProtocol.LiveRecordState
    public boolean isRecordingPaused() {
        return getCurState() == 3;
    }

    @Override // com.android.camera.protocol.ModeProtocol.LiveModuleSub
    public boolean onBackPressed() {
        return false;
    }

    @Override // com.android.camera.protocol.ModeProtocol.LiveGenericControl
    public void onOrientationChanged(int i, int i2, int i3) {
        if (this.mCurrentOrientation != i2 && !isRecording()) {
            this.mCurrentOrientation = i2;
        }
    }

    @Override // com.android.camera2.Camera2Proxy.PreviewCallback
    public boolean onPreviewFrame(Image image, Camera2Proxy camera2Proxy, int i) {
        return true;
    }

    @Override // com.android.camera.module.impl.component.ILive.ILiveRecorderStateListener
    public void onStateChange(int i) {
        this.mRecordState = i;
        int i2 = this.mRecordState;
        if (i2 == 3) {
            this.mUIHandler.post(new v(this));
        } else if (i2 == 8) {
            this.mUIHandler.post(new w(this));
        } else if (i2 == 9) {
            this.mUIHandler.post(new x(this));
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.MiLiveRecorderControl
    public void onSurfaceTextureReleased() {
        SurfaceTextureScreenNail.SurfaceTextureScreenNailCallback surfaceTextureScreenNailCallback = this.mRender;
        if (surfaceTextureScreenNailCallback != null) {
            surfaceTextureScreenNailCallback.onSurfaceTextureReleased();
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.MiLiveRecorderControl
    public void onSurfaceTextureUpdated(DrawExtTexAttribute drawExtTexAttribute) {
        SurfaceTextureScreenNail.SurfaceTextureScreenNailCallback surfaceTextureScreenNailCallback = this.mRender;
        if (surfaceTextureScreenNailCallback != null) {
            surfaceTextureScreenNailCallback.onSurfaceTextureUpdated(drawExtTexAttribute);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.MiLiveRecorderControl
    public void pauseRecording() {
        ILive.ILiveRecorder iLiveRecorder = this.mRecorder;
        if (iLiveRecorder != null) {
            iLiveRecorder.pauseRecording();
            this.mAudioController.restoreAudio();
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.LiveGenericControl
    public void prepare() {
        Log.d(this.TAG, "prepare");
        this.mUIHandler = new Handler(Looper.getMainLooper());
    }

    @Override // com.android.camera.protocol.ModeProtocol.BaseProtocol
    public void registerProtocol() {
        ModeCoordinatorImpl.getInstance().attachProtocol(241, this);
        ModeCoordinatorImpl.getInstance().attachProtocol(232, this);
        ModeCoordinatorImpl.getInstance().attachProtocol(243, this);
        ModeCoordinatorImpl.getInstance().attachProtocol(244, this);
        ModeCoordinatorImpl.getInstance().attachProtocol(211, this);
        ModeCoordinatorImpl.getInstance().attachProtocol(245, this);
    }

    @Override // com.android.camera.protocol.ModeProtocol.LiveGenericControl
    public void release() {
        Log.d(this.TAG, "release");
        ILive.ILiveRecorder iLiveRecorder = this.mRecorder;
        if (iLiveRecorder != null) {
            iLiveRecorder.release();
        }
        Handler handler = this.mUIHandler;
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.MiLiveRecorderControl
    public void resumeRecording() {
        if (this.mRecorder != null && isRecordingPaused()) {
            this.mAudioController.silenceAudio();
            this.mRecorder.resumeRecording();
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.LiveAudioChanges
    public void setAudioPath(String str) {
        this.mCurAudioPath = str;
        ILive.ILiveRecorder iLiveRecorder = this.mRecorder;
        if (iLiveRecorder != null) {
            iLiveRecorder.setAudioPath(str);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.LiveFilterChangers
    public void setFilter(boolean z, String str) {
        if (!z || TextUtils.isEmpty(str)) {
            this.mFilterBitmapPath = "";
        } else {
            this.mFilterBitmapPath = FileUtils.FILTER_DIR + str + File.separator + str + File.separator + str + FileUtils.FILTER_FILE_SUFFIX;
        }
        ILive.ILiveRecorder iLiveRecorder = this.mRecorder;
        if (iLiveRecorder != null) {
            iLiveRecorder.setFilterPath(this.mFilterBitmapPath);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:7:0x0015  */
    /* JADX WARNING: Removed duplicated region for block: B:9:? A[RETURN, SYNTHETIC] */
    @Override // com.android.camera.protocol.ModeProtocol.LiveSpeedChanges
    public void setRecordSpeed(int i) {
        ILive.ILiveRecorder iLiveRecorder;
        if (i >= 0) {
            float[] fArr = this.SPEEDS;
            if (i < fArr.length) {
                this.mCurSpeed = fArr[i];
                iLiveRecorder = this.mRecorder;
                if (iLiveRecorder == null) {
                    iLiveRecorder.setSpeed(this.mCurSpeed);
                    return;
                }
                return;
            }
        }
        this.mCurSpeed = 1.0f;
        iLiveRecorder = this.mRecorder;
        if (iLiveRecorder == null) {
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.MiLiveRecorderControl
    public void setRecorderListener(ModeProtocol.MiLiveRecorderControl.IRecorderListener iRecorderListener) {
        this.mRecorderListener = iRecorderListener;
    }

    @Override // com.android.camera.protocol.ModeProtocol.MiLiveRecorderControl
    public void startRecording() {
        if (this.mRecorder != null && !isRecording()) {
            FileUtils.deleteSubFiles(FileUtils.VIDEO_TMP);
            this.mAudioController.silenceAudio();
            this.mRecorder.setOrientation((this.mCurrentOrientation + 90) % 360);
            this.mRecorder.startRecording();
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.MiLiveRecorderControl
    public void stopRecording() {
        ILive.ILiveRecorder iLiveRecorder = this.mRecorder;
        if (iLiveRecorder != null) {
            iLiveRecorder.stopRecording();
            this.mAudioController.restoreAudio();
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.LiveModuleSub
    public void trackVideoParams() {
    }

    @Override // com.android.camera.protocol.ModeProtocol.BaseProtocol
    public void unRegisterProtocol() {
        ModeCoordinatorImpl.getInstance().detachProtocol(211, this);
        ModeCoordinatorImpl.getInstance().detachProtocol(244, this);
        ModeCoordinatorImpl.getInstance().detachProtocol(243, this);
        ModeCoordinatorImpl.getInstance().detachProtocol(232, this);
        ModeCoordinatorImpl.getInstance().detachProtocol(245, this);
        ModeCoordinatorImpl.getInstance().detachProtocol(241, this);
        release();
    }
}
