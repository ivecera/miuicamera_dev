package com.android.camera.module.impl.component;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Pair;
import android.view.Surface;
import com.android.camera.ActivityBase;
import com.android.camera.Camera;
import com.android.camera.CameraSettings;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.constant.DurationConstant;
import com.android.camera.data.DataRepository;
import com.android.camera.effect.EffectController;
import com.android.camera.fragment.beauty.LiveBeautyFilterFragment;
import com.android.camera.log.Log;
import com.android.camera.module.AudioController;
import com.android.camera.module.LiveModule;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.android.gallery3d.ui.ExtTexture;
import com.ss.android.vesdk.TERecorder;
import com.ss.android.vesdk.VEAudioEncodeSettings;
import com.ss.android.vesdk.VECameraSettings;
import com.ss.android.vesdk.VEListener;
import com.ss.android.vesdk.VEPreviewSettings;
import com.ss.android.vesdk.VESDK;
import com.ss.android.vesdk.VESize;
import com.ss.android.vesdk.VEVideoEncodeSettings;
import com.ss.android.vesdk.runtime.VERuntime;
import com.ss.android.vesdk.runtime.oauth.TEOAuthResult;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class LiveConfigChangeTTImpl implements ModeProtocol.LiveConfigChanges {
    private static final long MIN_RECORD_TIME = 500;
    private static final long START_OFFSET_MS = 450;
    /* access modifiers changed from: private */
    public static final String TAG = "LiveConfigChangeTTImpl";
    private static final float WHITE_INTENSITY = 0.2f;
    private final float[] SPEEDS = {0.33f, 0.5f, 1.0f, 2.0f, 3.0f};
    /* access modifiers changed from: private */
    public ActivityBase mActivity;
    private AudioController mAudioController;
    private VEAudioEncodeSettings mAudioEncodeSettings;
    private TEOAuthResult mAuthResult;
    private String mBGMPath;
    private ModeProtocol.OnShineChangedProtocol mBeautyImpl;
    private String mConcatVideoPath;
    private String mConcatWavPath;
    /* access modifiers changed from: private */
    public Context mContext;
    private CountDownTimer mCountDownTimer;
    /* access modifiers changed from: private */
    public float mCurrentSpeed;
    private ModeProtocol.FilterProtocol mFilterImpl;
    private Handler mHandler;
    private boolean mInitialized;
    /* access modifiers changed from: private */
    public boolean mInputSurfaceReady;
    /* access modifiers changed from: private */
    public SurfaceTexture mInputSurfaceTexture;
    private boolean mIsFrontCamera;
    private int mMaxVideoDurationInMs;
    /* access modifiers changed from: private */
    public boolean mMediaRecorderRecording = false;
    private boolean mMediaRecorderRecordingPaused;
    private VEPreviewSettings mPreviewSettings;
    private List<TimeSpeedModel> mRecordSegmentTimeInfo;
    /* access modifiers changed from: private */
    public TERecorder mRecorder;
    private final Object mRecorderLock = new Object();
    /* access modifiers changed from: private */
    public boolean mReleased;
    private long mStartTime;
    private ModeProtocol.StickerProtocol mStickerImpl;
    /* access modifiers changed from: private */
    public String mStickerPath;
    /* access modifiers changed from: private */
    public boolean mTTNativeIsInit = false;
    private long mTotalRecordingTime = 0;
    private VEVideoEncodeSettings mVideoEncodeSettings;

    /* renamed from: com.android.camera.module.impl.component.LiveConfigChangeTTImpl$5  reason: invalid class name */
    static /* synthetic */ class AnonymousClass5 {
        static final /* synthetic */ int[] $SwitchMap$com$ss$android$vesdk$runtime$oauth$TEOAuthResult = new int[TEOAuthResult.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(12:0|1|2|3|4|5|6|7|8|9|10|12) */
        /* JADX WARNING: Code restructure failed: missing block: B:13:?, code lost:
            return;
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001f */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x002a */
        /* JADX WARNING: Missing exception handler attribute for start block: B:9:0x0035 */
        static {
            $SwitchMap$com$ss$android$vesdk$runtime$oauth$TEOAuthResult[TEOAuthResult.OK.ordinal()] = 1;
            $SwitchMap$com$ss$android$vesdk$runtime$oauth$TEOAuthResult[TEOAuthResult.TBD.ordinal()] = 2;
            $SwitchMap$com$ss$android$vesdk$runtime$oauth$TEOAuthResult[TEOAuthResult.EXPIRED.ordinal()] = 3;
            $SwitchMap$com$ss$android$vesdk$runtime$oauth$TEOAuthResult[TEOAuthResult.FAIL.ordinal()] = 4;
            $SwitchMap$com$ss$android$vesdk$runtime$oauth$TEOAuthResult[TEOAuthResult.NOT_MATCH.ordinal()] = 5;
        }
    }

    private LiveConfigChangeTTImpl(ActivityBase activityBase) {
        this.mActivity = activityBase;
        this.mContext = activityBase.getCameraAppImpl().getApplicationContext();
        this.mHandler = new Handler(this.mActivity.getMainLooper());
        this.mRecorder = new TERecorder(FileUtils.ROOT_DIR, this.mContext, this.mHandler);
        this.mFilterImpl = new LiveFilterChangeImpl(this.mRecorder);
        this.mBeautyImpl = new LiveBeautyChangeImpl(this.mRecorder);
        this.mStickerImpl = new LiveStickerChangeImpl(this.mRecorder);
        this.mInputSurfaceTexture = new SurfaceTexture(false);
        this.mMaxVideoDurationInMs = DurationConstant.DURATION_LIVE_RECORD;
        this.mAudioController = new AudioController(this.mActivity.getApplicationContext());
    }

    public static LiveConfigChangeTTImpl create(ActivityBase activityBase) {
        return new LiveConfigChangeTTImpl(activityBase);
    }

    private void deleteLastSegment() {
        TERecorder tERecorder = this.mRecorder;
        if (tERecorder != null) {
            tERecorder.deleteLastFrag();
        }
    }

    private double getTimestamp(SensorEvent sensorEvent) {
        long nanoTime = System.nanoTime();
        return (double) (nanoTime - Math.min(Math.abs(nanoTime - sensorEvent.timestamp), Math.abs(SystemClock.elapsedRealtimeNanos() - sensorEvent.timestamp)));
    }

    private boolean hasSegments() {
        List<TimeSpeedModel> list = this.mRecordSegmentTimeInfo;
        return list != null && !list.isEmpty();
    }

    private void resumeEffect() {
        String[] currentLiveMusic = CameraSettings.getCurrentLiveMusic();
        if (!currentLiveMusic[0].isEmpty()) {
            setAudioPath(currentLiveMusic[0]);
        }
        this.mStickerPath = FileUtils.STICKER_RESOURCE_DIR + CameraSettings.getCurrentLiveSticker();
        setRecordSpeed(Integer.valueOf(CameraSettings.getCurrentLiveSpeed()).intValue());
    }

    /* access modifiers changed from: private */
    public void updateBeauty() {
        float faceBeautyRatio = ((float) CameraSettings.getFaceBeautyRatio("key_live_shrink_face_ratio")) / 100.0f;
        float faceBeautyRatio2 = ((float) CameraSettings.getFaceBeautyRatio("key_live_enlarge_eye_ratio")) / 100.0f;
        float faceBeautyRatio3 = ((float) CameraSettings.getFaceBeautyRatio("key_live_smooth_strength")) / 100.0f;
        if (faceBeautyRatio > 0.0f || faceBeautyRatio2 > 0.0f || faceBeautyRatio3 > 0.0f) {
            setBeautyFaceReshape(true, faceBeautyRatio2, faceBeautyRatio);
            setBeautify(true, faceBeautyRatio3);
            return;
        }
        setBeautyFaceReshape(false, 0.0f, 0.0f);
        setBeautify(false, 0.0f);
    }

    /* access modifiers changed from: private */
    public void updateRecordingTime(long j, float f2) {
        ModeProtocol.ActionProcessing actionProcessing = (ModeProtocol.ActionProcessing) ModeCoordinatorImpl.getInstance().getAttachProtocol(162);
        if (actionProcessing != null) {
            actionProcessing.updateRecordingTime(Util.millisecondToTimeString((long) ((((float) j) * 1.0f) / f2), false));
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.LiveRecordState
    public boolean canRecordingStop() {
        return hasSegments();
    }

    @Override // com.android.camera.protocol.ModeProtocol.LiveAudioChanges
    public void clearAudio() {
        TERecorder tERecorder = this.mRecorder;
        if (tERecorder != null) {
            this.mBGMPath = null;
            tERecorder.setRecordBGM("", 0, 1);
            CameraSettings.setCurrentLiveMusic(null, null);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.LiveConfigChanges
    public int getAuthResult() {
        TEOAuthResult tEOAuthResult = this.mAuthResult;
        if (tEOAuthResult == null) {
            return 3;
        }
        int i = AnonymousClass5.$SwitchMap$com$ss$android$vesdk$runtime$oauth$TEOAuthResult[tEOAuthResult.ordinal()];
        if (i == 1) {
            return 0;
        }
        if (i == 2) {
            return 1;
        }
        if (i != 3) {
            return (i == 4 || i != 5) ? 3 : 4;
        }
        return 2;
    }

    @Override // com.android.camera.protocol.ModeProtocol.LiveConfigChanges
    public Pair<String, String> getConcatResult() {
        String str = this.mConcatWavPath;
        String str2 = this.mBGMPath;
        if (str2 != null) {
            str = str2;
        }
        return new Pair<>(this.mConcatVideoPath, str);
    }

    @Override // com.android.camera.protocol.ModeProtocol.LiveConfigChanges
    public SurfaceTexture getInputSurfaceTexture() {
        return this.mInputSurfaceTexture;
    }

    @Override // com.android.camera.protocol.ModeProtocol.LiveSpeedChanges
    public float getRecordSpeed() {
        return this.mCurrentSpeed;
    }

    @Override // com.android.camera.protocol.ModeProtocol.LiveRecordState
    public int getSegmentSize() {
        List<TimeSpeedModel> list = this.mRecordSegmentTimeInfo;
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    @Override // com.android.camera.protocol.ModeProtocol.LiveSpeedChanges
    public long getStartRecordingTime() {
        return this.mStartTime;
    }

    @Override // com.android.camera.protocol.ModeProtocol.LiveSpeedChanges
    public String getTimeValue() {
        return Util.millisecondToTimeString(Util.clamp(this.mTotalRecordingTime, 1000, 15000), false, true);
    }

    @Override // com.android.camera.protocol.ModeProtocol.LiveSpeedChanges
    public long getTotalRecordingTime() {
        return this.mTotalRecordingTime;
    }

    @Override // com.android.camera.protocol.ModeProtocol.LiveConfigChanges
    public void initPreview(int i, int i2, boolean z, int i3) {
        Log.e("live initPreview:", i3 + "");
        this.mIsFrontCamera = z;
        this.mPreviewSettings = new VEPreviewSettings.Builder().setRenderSize(new VESize(i, i2)).build();
        this.mInputSurfaceTexture.setDefaultBufferSize(i2, i);
        VECameraSettings vECameraSettings = new VECameraSettings(z ? VECameraSettings.CAMERA_FACING_ID.FACING_FRONT : VECameraSettings.CAMERA_FACING_ID.FACING_BACK, i3, new VESize(i, i2));
        this.mRecordSegmentTimeInfo = DataRepository.dataItemLive().getRecordSegmentTimeInfo();
        if (this.mInitialized) {
            this.mRecorder.setCameraSettings(vECameraSettings);
            List<TimeSpeedModel> list = this.mRecordSegmentTimeInfo;
            if (list != null) {
                this.mTotalRecordingTime = TimeSpeedModel.calculateRealTime(list);
            } else {
                this.mRecordSegmentTimeInfo = new ArrayList();
                this.mTotalRecordingTime = 0;
            }
        } else {
            this.mVideoEncodeSettings = new VEVideoEncodeSettings.Builder(1).setHwEnc(true).setEncodeProfile(VEVideoEncodeSettings.ENCODE_PROFILE.ENCODE_PROFILE_MAIN).setVideoRes(i, i2).build();
            this.mRecorder.init(this.mVideoEncodeSettings, null, this.mPreviewSettings, vECameraSettings);
            List<TimeSpeedModel> list2 = this.mRecordSegmentTimeInfo;
            if (list2 != null) {
                this.mRecorder.tryRestore(list2.size());
                this.mTotalRecordingTime = TimeSpeedModel.calculateRealTime(this.mRecordSegmentTimeInfo);
                this.mMediaRecorderRecordingPaused = true;
                this.mMediaRecorderRecording = false;
            } else {
                this.mRecordSegmentTimeInfo = new ArrayList();
                this.mRecorder.clearEnv();
            }
            this.mInitialized = true;
        }
        ModeProtocol.FullScreenProtocol fullScreenProtocol = (ModeProtocol.FullScreenProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(196);
        if (fullScreenProtocol != null) {
            fullScreenProtocol.onRecordSegmentCreated();
        }
        resumeEffect();
    }

    @Override // com.android.camera.protocol.ModeProtocol.LiveConfigChanges
    public void initResource() {
        List<String> list;
        String str;
        VESDK.setExternalMonitorListener(MyOwnMonitor.Instance);
        VESDK.init(this.mContext, FileUtils.ROOT_DIR);
        this.mAuthResult = VERuntime.activate(this.mContext, this.mContext.getString(R.string.live_activation_license), this.mContext.getString(R.string.live_activation_id), DataRepository.dataItemLive().getActivation());
        TEOAuthResult tEOAuthResult = this.mAuthResult;
        if (tEOAuthResult == TEOAuthResult.OK || tEOAuthResult == TEOAuthResult.TBD) {
            DataRepository.dataItemLive().setActivation(VERuntime.getActivationCode());
            Log.d(TAG, "activation success: " + this.mAuthResult.name());
        } else {
            Log.d(TAG, "activation failed: " + this.mAuthResult.name());
        }
        if (!FileUtils.hasDir(FileUtils.ROOT_DIR) || !FileUtils.makeSureNoMedia(FileUtils.RESOURCE_DIR)) {
            FileUtils.makeNoMediaDir(FileUtils.FILTER_DIR);
            FileUtils.makeNoMediaDir(FileUtils.RESOURCE_DIR);
            FileUtils.makeNoMediaDir(FileUtils.RESHAPE_DIR_NAME);
            FileUtils.makeNoMediaDir(FileUtils.VIDEO_TMP);
            FileUtils.makeNoMediaDir(FileUtils.CONCAT_VIDEO_DIR);
            FileUtils.makeNoMediaDir(FileUtils.MUSIC_LOCAL);
            FileUtils.makeNoMediaDir(FileUtils.MUSIC_ONLINE);
            FileUtils.makeNoMediaDir(FileUtils.ROOT_DIR);
        }
        try {
            if (Util.isGlobalVersion()) {
                str = "music_global.zip";
                list = FileUtils.RESOURCE_LIST_GLOBAL;
            } else {
                str = "music_cn.zip";
                list = FileUtils.RESOURCE_LIST_CN;
            }
            Util.verifyAssetZip(this.mContext, "live/" + str, FileUtils.MUSIC_LOCAL, 32768);
            FileUtils.makeDir(FileUtils.MUSIC_ONLINE);
            for (String str2 : list) {
                Util.verifyAssetZip(this.mContext, "live/" + str2 + ".zip", FileUtils.STICKER_RESOURCE_DIR + str2, 32768);
            }
            Util.verifyAssetZip(this.mContext, "live/Beauty_12.zip", FileUtils.BEAUTY_12_DIR, 32768);
            Util.verifyAssetZip(this.mContext, "live/filter.zip", FileUtils.RESOURCE_DIR + "filter", 32768);
            Util.verifyAssetZip(this.mContext, "live/FaceReshape_V2.zip", FileUtils.RESHAPE_DIR_NAME, 32768);
        } catch (Exception e2) {
            Log.e(TAG, "verify asset zip failed...", e2);
        }
        if (VESDK.needUpdateEffectModelFiles()) {
            VESDK.updateEffectModelFiles();
        }
        FileUtils.makeNoMediaDir(FileUtils.MODELS_DIR);
    }

    @Override // com.android.camera.protocol.ModeProtocol.LiveRecordState
    public boolean isRecording() {
        return this.mMediaRecorderRecording;
    }

    @Override // com.android.camera.protocol.ModeProtocol.LiveRecordState
    public boolean isRecordingPaused() {
        return this.mMediaRecorderRecordingPaused;
    }

    @Override // com.android.camera.protocol.ModeProtocol.LiveConfigChanges
    public void onDeviceRotationChange(float[] fArr) {
        synchronized (this.mRecorderLock) {
            if (this.mRecorder != null) {
                this.mRecorder.setDeviceRotation(fArr);
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.LiveConfigChanges
    public boolean onRecordConcat(TERecorder.OnConcatFinishedListener onConcatFinishedListener) {
        if (!hasSegments()) {
            Log.e(TAG, "record segments is empty, stop concat");
            return false;
        }
        File file = new File(FileUtils.CONCAT_VIDEO_DIR);
        if (!file.exists()) {
            file.mkdirs();
        }
        this.mConcatVideoPath = new File(FileUtils.CONCAT_VIDEO_DIR, "concat.mp4").getAbsolutePath();
        this.mConcatWavPath = new File(FileUtils.CONCAT_VIDEO_DIR, "concat.wav").getAbsolutePath();
        this.mRecorder.concatAsync(this.mConcatVideoPath, this.mConcatWavPath, onConcatFinishedListener);
        return true;
    }

    @Override // com.android.camera.protocol.ModeProtocol.LiveConfigChanges
    public void onRecordPause() {
        CountDownTimer countDownTimer = this.mCountDownTimer;
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        TERecorder tERecorder = this.mRecorder;
        if (tERecorder != null && !this.mMediaRecorderRecordingPaused) {
            int stopRecord = tERecorder.stopRecord();
            String str = TAG;
            Log.d(str, "stopRecordResult onPause: " + stopRecord);
            long endFrameTime = this.mRecorder.getEndFrameTime() / 1000;
            if (endFrameTime > 500 || endFrameTime < 0) {
                this.mRecordSegmentTimeInfo.add(new TimeSpeedModel(endFrameTime, (double) this.mCurrentSpeed));
                this.mTotalRecordingTime = TimeSpeedModel.calculateRealTime(this.mRecordSegmentTimeInfo);
            } else {
                deleteLastSegment();
                String str2 = TAG;
                Log.d(str2, "recording time = " + endFrameTime + ", it's too short");
            }
            DataRepository.dataItemLive().setRecordSegmentTimeInfo(this.mRecordSegmentTimeInfo);
            this.mMediaRecorderRecordingPaused = true;
            this.mMediaRecorderRecording = false;
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.LiveConfigChanges
    public void onRecordResume() {
        if (this.mRecorder != null && this.mMediaRecorderRecordingPaused) {
            this.mAudioController.silenceAudio();
            this.mMediaRecorderRecordingPaused = false;
            this.mMediaRecorderRecording = true;
            int startRecord = this.mRecorder.startRecord(this.mCurrentSpeed, this.mTotalRecordingTime);
            String str = TAG;
            Log.d(str, "startRecordResult onResume: " + startRecord);
            updateRecordingTime();
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.LiveConfigChanges
    public void onRecordReverse() {
        Log.d(TAG, "delete last frag !!!");
        if (this.mRecorder != null) {
            if (this.mRecordSegmentTimeInfo.size() > 0) {
                List<TimeSpeedModel> list = this.mRecordSegmentTimeInfo;
                list.remove(list.size() - 1);
                this.mTotalRecordingTime = TimeSpeedModel.calculateRealTime(this.mRecordSegmentTimeInfo);
                updateRecordingTime(Math.min(((long) this.mMaxVideoDurationInMs) - this.mTotalRecordingTime, 15000L), 1.0f);
            }
            this.mRecorder.deleteLastFrag();
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.LiveConfigChanges
    public void onRecordStart() {
        if (this.mRecorder != null) {
            this.mAudioController.silenceAudio();
            DataRepository.dataItemLive().setRecordSegmentTimeInfo(this.mRecordSegmentTimeInfo);
            String str = this.mBGMPath;
            if (str != null) {
                this.mRecorder.setRecordBGM(str, 0, 1);
            }
            int startRecord = this.mRecorder.startRecord(this.mCurrentSpeed, this.mTotalRecordingTime);
            String str2 = TAG;
            Log.d(str2, "startRecordResult onStart: " + startRecord);
            this.mMediaRecorderRecordingPaused = false;
            this.mMediaRecorderRecording = true;
            updateRecordingTime();
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.LiveConfigChanges
    public void onRecordStop() {
        if (this.mRecorder != null) {
            this.mAudioController.restoreAudio();
            onRecordPause();
            DataRepository.dataItemLive().setRecordSegmentTimeInfo(null);
            this.mTotalRecordingTime = 0;
            this.mRecordSegmentTimeInfo.clear();
            this.mMediaRecorderRecordingPaused = false;
            this.mMediaRecorderRecording = false;
            this.mRecorder.clearEnv();
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0086, code lost:
        return;
     */
    @Override // com.android.camera.protocol.ModeProtocol.LiveConfigChanges
    public void onSensorChanged(SensorEvent sensorEvent) {
        synchronized (this.mRecorderLock) {
            if (this.mRecorder != null) {
                double timestamp = getTimestamp(sensorEvent);
                int type = sensorEvent.sensor.getType();
                if (type == 1) {
                    this.mRecorder.slamProcessIngestAcc((double) sensorEvent.values[0], (double) sensorEvent.values[1], (double) sensorEvent.values[2], timestamp);
                } else if (type == 4) {
                    this.mRecorder.slamProcessIngestGyr((double) sensorEvent.values[0], (double) sensorEvent.values[1], (double) sensorEvent.values[2], timestamp);
                } else if (type == 9) {
                    this.mRecorder.slamProcessIngestGra((double) sensorEvent.values[0], (double) sensorEvent.values[1], (double) sensorEvent.values[2], timestamp);
                } else if (type == 15) {
                    float[] fArr = new float[9];
                    SensorManager.getRotationMatrixFromVector(fArr, sensorEvent.values);
                    double[] dArr = new double[9];
                    for (int i = 0; i < fArr.length; i++) {
                        dArr[i] = (double) fArr[i];
                    }
                    this.mRecorder.slamProcessIngestOri(dArr, timestamp);
                }
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.BaseProtocol
    public void registerProtocol() {
        ModeCoordinatorImpl.getInstance().attachProtocol(201, this);
        ModeCoordinatorImpl.getInstance().attachProtocol(232, this);
        ModeCoordinatorImpl.getInstance().attachProtocol(243, this);
        ModeCoordinatorImpl.getInstance().attachProtocol(244, this);
        ModeCoordinatorImpl.getInstance().attachProtocol(211, this);
        ModeCoordinatorImpl.getInstance().attachProtocol(245, this);
        this.mStickerImpl.registerProtocol();
    }

    @Override // com.android.camera.protocol.ModeProtocol.LiveConfigChanges
    public void release() {
        this.mReleased = true;
        synchronized (this.mRecorderLock) {
            Log.d(TAG, "release");
            if (this.mRecorder != null) {
                int stopRecord = this.mRecorder.stopRecord();
                String str = TAG;
                Log.d(str, "stopRecordResult onRelease: " + stopRecord);
                int stopPreview = this.mRecorder.stopPreview();
                String str2 = TAG;
                Log.d(str2, "stopPreviewResult onRelease: " + stopPreview);
                this.mRecorder.setNativeInitListener(null);
                this.mRecorder.setRenderCallback(null);
                this.mRecorder.destroy();
                this.mInputSurfaceReady = false;
                this.mInputSurfaceTexture.release();
                this.mRecorder = null;
                this.mHandler.removeCallbacksAndMessages(null);
                this.mHandler = null;
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.LiveAudioChanges
    public void setAudioPath(String str) {
        TERecorder tERecorder = this.mRecorder;
        if (tERecorder != null) {
            this.mBGMPath = str;
            tERecorder.setRecordBGM(str, 0, 1);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.LiveConfigChanges
    public void setBeautify(boolean z, float f2) {
        TERecorder tERecorder = this.mRecorder;
        if (tERecorder != null && this.mTTNativeIsInit) {
            if (z) {
                tERecorder.setBeautyFace(3, FileUtils.BEAUTY_12_DIR);
                this.mRecorder.setBeautyFaceIntensity(f2, WHITE_INTENSITY);
                return;
            }
            tERecorder.setBeautyFace(0, "");
            this.mRecorder.setBeautyFaceIntensity(0.0f, 0.0f);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.LiveConfigChanges
    public void setBeautyFaceReshape(boolean z, float f2, float f3) {
        TERecorder tERecorder = this.mRecorder;
        if (tERecorder != null && this.mTTNativeIsInit) {
            if (z) {
                tERecorder.setFaceReshape(FileUtils.RESHAPE_DIR_NAME, f2, f3);
            } else {
                tERecorder.setFaceReshape("", 0.0f, 0.0f);
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.LiveConfigChanges
    public void setEffectAudio(boolean z) {
        TERecorder tERecorder = this.mRecorder;
        if (tERecorder != null && this.mTTNativeIsInit) {
            tERecorder.pauseEffectAudio(!z);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:0x003a, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x003c, code lost:
        return;
     */
    @Override // com.android.camera.protocol.ModeProtocol.LiveFilterChangers
    public void setFilter(boolean z, String str) {
        synchronized (this.mRecorderLock) {
            if (this.mRecorder != null) {
                if (this.mTTNativeIsInit) {
                    if (!z || TextUtils.isEmpty(str)) {
                        this.mRecorder.setFilter("", 1.0f);
                    } else {
                        this.mRecorder.setFilter(FileUtils.FILTER_DIR + str + File.separator, 1.0f);
                    }
                }
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.LiveSpeedChanges
    public void setRecordSpeed(int i) {
        this.mCurrentSpeed = this.SPEEDS[i];
    }

    @Override // com.android.camera.protocol.ModeProtocol.LiveConfigChanges
    public void startPreview(Surface surface) {
        if (this.mInputSurfaceReady) {
            Log.d(TAG, "startPreview return");
            return;
        }
        Log.d(TAG, "startPreview");
        AnonymousClass1 r0 = new TERecorder.IRenderCallback() {
            /* class com.android.camera.module.impl.component.LiveConfigChangeTTImpl.AnonymousClass1 */

            @Override // com.ss.android.vesdk.TERecorder.IRenderCallback
            public TERecorder.Texture onCreateTexture() {
                if (LiveConfigChangeTTImpl.this.mReleased) {
                    return null;
                }
                Log.d(LiveConfigChangeTTImpl.TAG, "TTRenderCallback, onCreateTexture");
                boolean unused = LiveConfigChangeTTImpl.this.mInputSurfaceReady = true;
                ExtTexture extTexture = new ExtTexture();
                extTexture.onBind(null);
                LiveConfigChangeTTImpl.this.mInputSurfaceTexture.attachToGLContext(extTexture.getId());
                return new TERecorder.Texture(extTexture.getId(), LiveConfigChangeTTImpl.this.mInputSurfaceTexture);
            }

            @Override // com.ss.android.vesdk.TERecorder.IRenderCallback
            public boolean onDestroy() {
                Log.d(LiveConfigChangeTTImpl.TAG, "TTRenderCallback onDestroy");
                try {
                    LiveConfigChangeTTImpl.this.mInputSurfaceTexture.detachFromGLContext();
                } catch (Exception e2) {
                    String access$100 = LiveConfigChangeTTImpl.TAG;
                    Log.e(access$100, "detachFromGLContext exception " + e2.getMessage());
                }
                boolean unused = LiveConfigChangeTTImpl.this.mInputSurfaceReady = false;
                return false;
            }

            @Override // com.ss.android.vesdk.TERecorder.IRenderCallback
            public void onTextureCreated(TERecorder.Texture texture) {
            }
        };
        this.mRecorder.setNativeInitListener(new VEListener.VERecorderNativeInitListener() {
            /* class com.android.camera.module.impl.component.LiveConfigChangeTTImpl.AnonymousClass2 */

            @Override // com.ss.android.vesdk.VEListener.VERecorderNativeInitListener
            public void onHardEncoderInit(boolean z) {
            }

            @Override // com.ss.android.vesdk.VEListener.VERecorderNativeInitListener
            public void onNativeInit(int i, String str) {
                TERecorder access$400 = LiveConfigChangeTTImpl.this.mRecorder;
                if (access$400 != null) {
                    boolean unused = LiveConfigChangeTTImpl.this.mTTNativeIsInit = true;
                    int slamDeviceConfig = access$400.slamDeviceConfig(true, true, true, true);
                    String access$100 = LiveConfigChangeTTImpl.TAG;
                    Log.e(access$100, "slam config result = " + slamDeviceConfig);
                    access$400.setUseLargeMattingModel(true);
                    if (LiveConfigChangeTTImpl.this.mStickerPath != null) {
                        access$400.switchEffect(LiveConfigChangeTTImpl.this.mStickerPath);
                    }
                    LiveBeautyFilterFragment.LiveFilterItem findLiveFilter = EffectController.getInstance().findLiveFilter(LiveConfigChangeTTImpl.this.mContext, DataRepository.dataItemLive().getLiveFilter());
                    LiveConfigChangeTTImpl.this.setFilter(true, findLiveFilter != null ? findLiveFilter.directoryName : "");
                    LiveConfigChangeTTImpl.this.updateBeauty();
                }
            }
        });
        this.mRecorder.setRenderCallback(r0);
        int startPreview = this.mRecorder.startPreview(surface);
        String str = TAG;
        Log.d(str, "previewResult: " + startPreview);
        this.mRecorder.addSlamDetectListener(new TERecorder.OnSlamDetectListener() {
            /* class com.android.camera.module.impl.component.LiveConfigChangeTTImpl.AnonymousClass3 */

            @Override // com.ss.android.vesdk.TERecorder.OnSlamDetectListener
            public void onSlam(boolean z) {
                if (z) {
                    Log.d(LiveConfigChangeTTImpl.TAG, "onSlam open, register tt ar sensor");
                    ((Camera) LiveConfigChangeTTImpl.this.mActivity).getSensorStateManager().setTTARSensorEnabled(true);
                    return;
                }
                Log.d(LiveConfigChangeTTImpl.TAG, "onSlam close, unregister tt ar sensor");
                ((Camera) LiveConfigChangeTTImpl.this.mActivity).getSensorStateManager().setTTARSensorEnabled(false);
            }
        });
    }

    @Override // com.android.camera.protocol.ModeProtocol.BaseProtocol
    public void unRegisterProtocol() {
        ModeCoordinatorImpl.getInstance().detachProtocol(245, this);
        ModeCoordinatorImpl.getInstance().detachProtocol(211, this);
        ModeCoordinatorImpl.getInstance().detachProtocol(244, this);
        ModeCoordinatorImpl.getInstance().detachProtocol(243, this);
        ModeCoordinatorImpl.getInstance().detachProtocol(232, this);
        ModeCoordinatorImpl.getInstance().detachProtocol(201, this);
        this.mStickerImpl.unRegisterProtocol();
        release();
    }

    @Override // com.android.camera.protocol.ModeProtocol.LiveConfigChanges
    public void updateRecordingTime() {
        if (this.mMediaRecorderRecording) {
            CountDownTimer countDownTimer = this.mCountDownTimer;
            if (countDownTimer != null) {
                countDownTimer.cancel();
            }
            float f2 = this.mCurrentSpeed;
            this.mCountDownTimer = new CountDownTimer((long) (((float) (((long) this.mMaxVideoDurationInMs) - this.mTotalRecordingTime)) * f2), (long) (f2 * 1000.0f)) {
                /* class com.android.camera.module.impl.component.LiveConfigChangeTTImpl.AnonymousClass4 */

                public void onFinish() {
                    if (LiveConfigChangeTTImpl.this.mMediaRecorderRecording && LiveConfigChangeTTImpl.this.mActivity != null && LiveConfigChangeTTImpl.this.mActivity.getCurrentModule() != null && (LiveConfigChangeTTImpl.this.mActivity.getCurrentModule() instanceof LiveModule)) {
                        ((LiveModule) LiveConfigChangeTTImpl.this.mActivity.getCurrentModule()).stopVideoRecording(true, false);
                    }
                }

                public void onTick(long j) {
                    if (LiveConfigChangeTTImpl.this.mMediaRecorderRecording) {
                        LiveConfigChangeTTImpl liveConfigChangeTTImpl = LiveConfigChangeTTImpl.this;
                        liveConfigChangeTTImpl.updateRecordingTime(j, liveConfigChangeTTImpl.mCurrentSpeed);
                    }
                }
            };
            this.mStartTime = System.currentTimeMillis();
            this.mCountDownTimer.start();
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.LiveConfigChanges
    public void updateRotation(float f2, float f3, float f4) {
        TERecorder tERecorder = this.mRecorder;
        if (tERecorder != null) {
            tERecorder.updateRotation(f2, f3, f4);
        }
    }
}
