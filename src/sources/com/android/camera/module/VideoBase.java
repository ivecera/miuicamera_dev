package com.android.camera.module;

import a.a.a;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.hardware.SensorEvent;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CaptureResult;
import android.location.Location;
import android.media.AudioSystem;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.os.ParcelFileDescriptor;
import android.os.SystemClock;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import com.android.camera.BasePreferenceActivity;
import com.android.camera.Camera;
import com.android.camera.CameraAppImpl;
import com.android.camera.CameraPreferenceActivity;
import com.android.camera.CameraSettings;
import com.android.camera.CameraSize;
import com.android.camera.ChangeManager;
import com.android.camera.FileCompat;
import com.android.camera.LocationManager;
import com.android.camera.OnClickAttr;
import com.android.camera.R;
import com.android.camera.RotateDialogController;
import com.android.camera.SensorStateManager;
import com.android.camera.ThermalDetector;
import com.android.camera.Thumbnail;
import com.android.camera.ToastUtils;
import com.android.camera.Util;
import com.android.camera.constant.AutoFocus;
import com.android.camera.constant.GlobalConstant;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.config.ComponentConfigSlowMotion;
import com.android.camera.effect.EffectController;
import com.android.camera.effect.FaceAnalyzeInfo;
import com.android.camera.fragment.beauty.BeautyValues;
import com.android.camera.log.Log;
import com.android.camera.module.loader.FunctionParseAsdFace;
import com.android.camera.module.loader.camera2.FocusManager2;
import com.android.camera.module.loader.camera2.FocusTask;
import com.android.camera.permission.PermissionManager;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera.statistic.CameraStatUtils;
import com.android.camera.statistic.MistatsConstants;
import com.android.camera.storage.Storage;
import com.android.camera2.Camera2Proxy;
import com.android.camera2.CameraCapabilities;
import com.android.camera2.CameraHardwareFace;
import com.mi.config.b;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.disposables.Disposable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public abstract class VideoBase extends BaseModule implements Camera2Proxy.FaceDetectionCallback, Camera2Proxy.FocusCallback, Camera2Proxy.CameraPreviewCallback, FocusManager2.Listener, ModeProtocol.CameraAction, ModeProtocol.PlayVideoProtocol {
    protected static final int FILE_NUMBER_SINGLE = -1;
    private static final boolean HOLD_WHEN_SAVING_VIDEO = false;
    private static final int MILLIS_PER_MINUTE = 60000;
    private static final int MIN_BACK_RECORDING_MINUTE = 20;
    private static final int MIN_FRONT_RECORDING_MINUTE = 10;
    private static final boolean SHOW_FACE_VIEW = false;
    public static final String START_VIDEO_RECORDING_ACTION = "com.android.camera.action.start_video_recording";
    public static final String STOP_VIDEO_RECORDING_ACTION = "com.android.camera.action.stop_video_recording";
    protected static String TAG = null;
    private static final int THREE_MINUTE = 3;
    public boolean m3ALocked;
    private AudioController mAudioController;
    protected String mBaseFileName;
    protected BeautyValues mBeautyValues;
    protected CameraCaptureSession mCurrentSession;
    protected String mCurrentVideoFilename;
    protected Uri mCurrentVideoUri;
    protected ContentValues mCurrentVideoValues;
    private AlertDialog mDialog;
    protected boolean mFaceDetected;
    protected boolean mFaceDetectionEnabled;
    protected boolean mFaceDetectionStarted;
    protected FocusManager2 mFocusManager;
    protected boolean mInStartingFocusRecording;
    protected long mIntentRequestSize;
    private boolean mIsSessionReady;
    private boolean mIsVideoCaptureIntent;
    protected int mMaxVideoDurationInMs;
    protected volatile boolean mMediaRecorderRecording;
    protected boolean mMediaRecorderRecordingPaused;
    private int mMessageId;
    private Disposable mMetaDataDisposable;
    /* access modifiers changed from: private */
    public FlowableEmitter<CaptureResult> mMetaDataFlowableEmitter;
    protected long mOnResumeTime;
    protected int mOrientationCompensationAtRecordStart;
    protected int mOriginalMusicVolume;
    protected int mOutputFormat;
    protected boolean mPreviewing;
    protected long mRecordingStartTime;
    protected boolean mSavePowerMode;
    protected SensorStateManager.SensorStateListener mSensorStateListener = new SensorStateManager.SensorStateListener() {
        /* class com.android.camera.module.VideoBase.AnonymousClass4 */

        @Override // com.android.camera.SensorStateManager.SensorStateListener
        public boolean isWorking() {
            return VideoBase.this.isAlive() && VideoBase.this.mPreviewing;
        }

        @Override // com.android.camera.SensorStateManager.SensorStateListener
        public void notifyDevicePostureChanged() {
            ((BaseModule) VideoBase.this).mActivity.getEdgeShutterView().onDevicePostureChanged();
        }

        @Override // com.android.camera.SensorStateManager.SensorStateListener
        public void onDeviceBecomeStable() {
            Log.v(VideoBase.TAG, "onDeviceBecomeStable");
        }

        @Override // com.android.camera.SensorStateManager.SensorStateListener
        public void onDeviceBeginMoving() {
        }

        @Override // com.android.camera.SensorStateManager.SensorStateListener
        public void onDeviceKeepMoving(double d2) {
            FocusManager2 focusManager2;
            if (!((BaseModule) VideoBase.this).mMainProtocol.isEvAdjusted(true) && !((BaseModule) VideoBase.this).mPaused && Util.isTimeout(System.currentTimeMillis(), VideoBase.this.mTouchFocusStartingTime, 3000) && !VideoBase.this.is3ALocked() && (focusManager2 = VideoBase.this.mFocusManager) != null && focusManager2.isNeedCancelAutoFocus() && !VideoBase.this.isRecording()) {
                VideoBase.this.mFocusManager.onDeviceKeepMoving(d2);
            }
        }

        @Override // com.android.camera.SensorStateManager.SensorStateListener
        public void onDeviceKeepStable() {
        }

        @Override // com.android.camera.SensorStateManager.SensorStateListener
        public void onDeviceLieChanged(boolean z) {
        }

        @Override // com.android.camera.SensorStateManager.SensorStateListener
        public void onDeviceOrientationChanged(float f2, boolean z) {
            VideoBase videoBase = VideoBase.this;
            if (z) {
                f2 = (float) ((BaseModule) videoBase).mOrientation;
            }
            ((BaseModule) videoBase).mDeviceRotation = f2;
            if (CameraSettings.isGradienterOn()) {
                EffectController instance = EffectController.getInstance();
                VideoBase videoBase2 = VideoBase.this;
                instance.setDeviceRotation(z, Util.getShootRotation(((BaseModule) videoBase2).mActivity, ((BaseModule) videoBase2).mDeviceRotation));
            }
        }

        @Override // com.android.camera.SensorStateManager.SensorStateListener
        public void onDeviceRotationChanged(float[] fArr) {
        }

        @Override // com.android.camera.SensorStateManager.SensorStateListener
        public void onSensorChanged(SensorEvent sensorEvent) {
        }
    };
    protected boolean mSnapshotInProgress;
    protected StereoSwitchThread mStereoSwitchThread;
    protected TelephonyManager mTelephonyManager;
    private int mTitleId;
    protected long mTouchFocusStartingTime;
    protected ParcelFileDescriptor mVideoFileDescriptor;
    protected String mVideoFocusMode;
    protected CameraSize mVideoSize;

    private static class MainHandler extends Handler {
        private WeakReference<VideoBase> mModule;

        public MainHandler(VideoBase videoBase) {
            this.mModule = new WeakReference<>(videoBase);
        }

        public void handleMessage(Message message) {
            VideoBase videoBase = this.mModule.get();
            if (videoBase != null) {
                if (!videoBase.isCreated()) {
                    removeCallbacksAndMessages(null);
                } else if (videoBase.getActivity() != null) {
                    int i = message.what;
                    if (i != 2) {
                        if (i == 4) {
                            if (Util.getDisplayRotation(((BaseModule) videoBase).mActivity) != ((BaseModule) videoBase).mDisplayRotation && !videoBase.mMediaRecorderRecording) {
                                videoBase.startPreview();
                            }
                            if (SystemClock.uptimeMillis() - videoBase.mOnResumeTime < 5000) {
                                sendEmptyMessageDelayed(4, 100);
                                return;
                            }
                            return;
                        } else if (i != 17) {
                            boolean z = false;
                            if (i == 35) {
                                boolean z2 = message.arg1 > 0;
                                if (message.arg2 > 0) {
                                    z = true;
                                }
                                videoBase.handleUpdateFaceView(z2, z);
                                return;
                            } else if (i == 42) {
                                videoBase.updateRecordingTime();
                                return;
                            } else if (i == 55) {
                                Log.e(VideoBase.TAG, "autoFocus timeout!");
                                videoBase.mFocusManager.resetFocused();
                                videoBase.onWaitingFocusFinished();
                                return;
                            } else if (i == 9) {
                                videoBase.onPreviewStart();
                                videoBase.mStereoSwitchThread = null;
                                return;
                            } else if (i == 10) {
                                videoBase.stopVideoRecording(true, false);
                                ((BaseModule) videoBase).mOpenCameraFail = true;
                                videoBase.onCameraException();
                                return;
                            } else if (i == 45) {
                                videoBase.setActivity(null);
                                return;
                            } else if (i == 46) {
                                videoBase.onWaitStopCallbackTimeout();
                                return;
                            } else if (i == 51) {
                                videoBase.stopVideoRecording(true, false);
                                if (!((BaseModule) videoBase).mActivity.isActivityPaused()) {
                                    ((BaseModule) videoBase).mOpenCameraFail = true;
                                    videoBase.onCameraException();
                                    return;
                                }
                                return;
                            } else if (i == 52) {
                                videoBase.enterSavePowerMode();
                                return;
                            } else if (!BaseModule.DEBUG) {
                                Log.e(VideoBase.TAG, "no consumer for this message: " + message.what);
                            } else {
                                throw new RuntimeException("no consumer for this message: " + message.what);
                            }
                        } else {
                            removeMessages(17);
                            removeMessages(2);
                            videoBase.getWindow().addFlags(128);
                            sendEmptyMessageDelayed(2, (long) videoBase.getScreenDelay());
                            return;
                        }
                    }
                    videoBase.getWindow().clearFlags(128);
                }
            }
        }
    }

    protected class StereoSwitchThread extends Thread {
        private volatile boolean mCancelled;

        protected StereoSwitchThread() {
        }

        public void cancel() {
            this.mCancelled = true;
        }

        public void run() {
            VideoBase.this.closeCamera();
            if (!this.mCancelled) {
                VideoBase.this.openCamera();
                if (VideoBase.this.hasCameraException()) {
                    VideoBase.this.onCameraException();
                } else if (!this.mCancelled) {
                    CameraSettings.resetZoom();
                    CameraSettings.resetExposure();
                    VideoBase.this.onCameraOpened();
                    VideoBase.this.readVideoPreferences();
                    VideoBase.this.resizeForPreviewAspectRatio();
                    if (!this.mCancelled) {
                        VideoBase.this.startPreview();
                        VideoBase.this.onPreviewStart();
                        ((BaseModule) VideoBase.this).mHandler.sendEmptyMessage(9);
                    }
                }
            }
        }
    }

    public VideoBase(String str) {
        TAG = str;
        ((BaseModule) this).mHandler = new MainHandler(this);
    }

    private String createName(long j, int i) {
        if (i > 0) {
            return this.mBaseFileName;
        }
        this.mBaseFileName = new SimpleDateFormat(getString(R.string.video_file_name_format), Locale.ENGLISH).format(new Date(j));
        return this.mBaseFileName;
    }

    private void deleteCurrentVideo() {
        String str = this.mCurrentVideoFilename;
        if (str != null) {
            deleteVideoFile(str);
            this.mCurrentVideoFilename = null;
            Uri uri = this.mCurrentVideoUri;
            if (uri != null) {
                Util.safeDelete(uri, null, null);
                this.mCurrentVideoUri = null;
            }
        }
        ((BaseModule) this).mActivity.getScreenHint().updateHint();
    }

    private Bitmap getReviewBitmap() {
        Bitmap bitmap;
        ParcelFileDescriptor parcelFileDescriptor = this.mVideoFileDescriptor;
        if (parcelFileDescriptor != null) {
            bitmap = Thumbnail.createVideoThumbnailBitmap(parcelFileDescriptor.getFileDescriptor(), ((BaseModule) this).mDisplayRect.width(), ((BaseModule) this).mDisplayRect.height());
        } else {
            String str = this.mCurrentVideoFilename;
            bitmap = str != null ? Thumbnail.createVideoThumbnailBitmap(str, ((BaseModule) this).mDisplayRect.width(), ((BaseModule) this).mDisplayRect.height()) : null;
        }
        if (bitmap == null) {
            return bitmap;
        }
        return Util.rotateAndMirror(bitmap, -this.mOrientationCompensationAtRecordStart, isFrontCamera() && !b.hl() && (!DataRepository.dataItemFeature().s_v_f_m() || !CameraSettings.isFrontMirror()));
    }

    /* access modifiers changed from: private */
    public void handleUpdateFaceView(boolean z, boolean z2) {
        Camera2Proxy camera2Proxy;
        boolean isFrontCamera = isFrontCamera();
        if (!z) {
            ((BaseModule) this).mMainProtocol.updateFaceView(z, z2, isFrontCamera, false, -1);
        } else if (this.mFaceDetectionStarted && (camera2Proxy = ((BaseModule) this).mCamera2Device) != null && 1 != camera2Proxy.getFocusMode()) {
            ((BaseModule) this).mMainProtocol.updateFaceView(z, true, isFrontCamera, true, ((BaseModule) this).mCameraDisplayOrientation);
        }
    }

    private void hideAlert() {
        if (((BaseModule) this).mCamera2Device.isSessionReady()) {
            resumePreview();
        } else {
            startPreview();
        }
        ((BaseModule) this).mMainProtocol.hideReviewViews();
        enableCameraControls(true);
        ((ModeProtocol.BaseDelegate) ModeCoordinatorImpl.getInstance().getAttachProtocol(160)).delegateEvent(6);
    }

    private void initMetaParser() {
        this.mMetaDataDisposable = Flowable.create(new FlowableOnSubscribe<CaptureResult>() {
            /* class com.android.camera.module.VideoBase.AnonymousClass1 */

            @Override // io.reactivex.FlowableOnSubscribe
            public void subscribe(FlowableEmitter<CaptureResult> flowableEmitter) throws Exception {
                FlowableEmitter unused = VideoBase.this.mMetaDataFlowableEmitter = flowableEmitter;
            }
        }, BackpressureStrategy.DROP).map(new FunctionParseAsdFace(this, isFrontCamera())).subscribe();
    }

    private boolean isFaceBeautyOn(BeautyValues beautyValues) {
        if (beautyValues == null) {
            return false;
        }
        return beautyValues.isFaceBeautyOn();
    }

    private void onStereoModeChanged() {
        enableCameraControls(false);
        ((BaseModule) this).mActivity.getSensorStateManager().setFocusSensorEnabled(false);
        cancelFocus(false);
        this.mStereoSwitchThread = new StereoSwitchThread();
        this.mStereoSwitchThread.start();
    }

    private void restorePreferences() {
        if (isZoomSupported()) {
            setZoomRatio(1.0f);
        }
        onSharedPreferenceChanged();
    }

    private void setOrientationParameter() {
        if (!isDeparted() && ((BaseModule) this).mCamera2Device != null && ((BaseModule) this).mOrientation != -1) {
            if (CameraSettings.isAutoZoomEnabled(DataRepository.dataItemGlobal().getCurrentMode()) || isVideoBokehEnabled()) {
                if (this.mPreviewing) {
                    updatePreferenceInWorkThread(35);
                } else {
                    GlobalConstant.sCameraSetupScheduler.scheduleDirect(new D(this));
                }
            }
            AudioSystem.setParameters("video_rotation=" + ((BaseModule) this).mOrientation);
        }
    }

    private void startPlayVideoActivity() {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setDataAndType(this.mCurrentVideoUri, Util.convertOutputFormatToMimeType(this.mOutputFormat));
        intent.setFlags(1);
        try {
            ((BaseModule) this).mActivity.startActivity(intent);
        } catch (ActivityNotFoundException e2) {
            String str = TAG;
            Log.e(str, "failed to view video " + this.mCurrentVideoUri, e2);
        }
    }

    private void updateFaceView(boolean z, boolean z2) {
        if (((BaseModule) this).mHandler.hasMessages(35)) {
            ((BaseModule) this).mHandler.removeMessages(35);
        }
        ((BaseModule) this).mHandler.obtainMessage(35, z ? 1 : 0, z2 ? 1 : 0).sendToTarget();
    }

    public /* synthetic */ void Uf() {
        ((BaseModule) this).mCamera2Device.setDeviceOrientation(((BaseModule) this).mOrientation);
    }

    /* access modifiers changed from: protected */
    public void animateHold() {
    }

    /* access modifiers changed from: protected */
    public void animateSlide() {
    }

    @Override // com.android.camera.module.loader.camera2.FocusManager2.Listener
    public void cancelFocus(boolean z) {
        if (isDeviceAlive()) {
            if (!isFrameAvailable()) {
                Log.e(TAG, "cancelFocus: frame not available");
                return;
            }
            String str = TAG;
            Log.v(str, "cancelFocus: " + z);
            if (z) {
                setVideoFocusMode(AutoFocus.LEGACY_CONTINUOUS_VIDEO, true);
            }
            ((BaseModule) this).mCamera2Device.cancelFocus(((BaseModule) this).mModuleIndex);
        }
    }

    /* access modifiers changed from: protected */
    public boolean checkCallingState() {
        if (2 != this.mTelephonyManager.getCallState()) {
            return true;
        }
        showConfirmMessage(R.string.confirm_recording_fail_title, R.string.confirm_recording_fail_calling_alert);
        return false;
    }

    @Override // com.android.camera.module.BaseModule
    public void checkDisplayOrientation() {
        if (isCreated()) {
            super.checkDisplayOrientation();
            FocusManager2 focusManager2 = this.mFocusManager;
            if (focusManager2 != null) {
                focusManager2.setDisplayOrientation(((BaseModule) this).mCameraDisplayOrientation);
            }
            Camera2Proxy camera2Proxy = ((BaseModule) this).mCamera2Device;
            if (camera2Proxy != null) {
                camera2Proxy.setDisplayOrientation(((BaseModule) this).mCameraDisplayOrientation);
            }
            ((BaseModule) this).mMainProtocol.setCameraDisplayOrientation(((BaseModule) this).mCameraDisplayOrientation);
        }
    }

    /* access modifiers changed from: protected */
    public void cleanupEmptyFile() {
        String str = this.mCurrentVideoFilename;
        if (str != null) {
            File file = new File(str);
            if (!file.exists()) {
                String str2 = TAG;
                Log.d(str2, "no video file: " + this.mCurrentVideoFilename);
                this.mCurrentVideoFilename = null;
            } else if (file.length() == 0) {
                if (!Storage.isUseDocumentMode()) {
                    file.delete();
                } else {
                    FileCompat.deleteFile(this.mCurrentVideoFilename);
                }
                String str3 = TAG;
                Log.d(str3, "delete empty video file: " + this.mCurrentVideoFilename);
                this.mCurrentVideoFilename = null;
            }
        }
    }

    @Override // com.android.camera.module.Module
    public void closeCamera() {
        Log.v(TAG, "closeCamera: E");
        this.mPreviewing = false;
        this.mSnapshotInProgress = false;
        FlowableEmitter<CaptureResult> flowableEmitter = this.mMetaDataFlowableEmitter;
        if (flowableEmitter != null) {
            flowableEmitter.onComplete();
        }
        Disposable disposable = this.mMetaDataDisposable;
        if (disposable != null) {
            disposable.dispose();
        }
        Camera2Proxy camera2Proxy = ((BaseModule) this).mCamera2Device;
        if (camera2Proxy != null) {
            camera2Proxy.setMetaDataCallback(null);
            ((BaseModule) this).mCamera2Device.setFocusCallback(null);
            ((BaseModule) this).mCamera2Device.setErrorCallback(null);
            unlockAEAF();
            ((BaseModule) this).mCamera2Device = null;
        }
        FocusManager2 focusManager2 = this.mFocusManager;
        if (focusManager2 != null) {
            focusManager2.destroy();
        }
        Log.v(TAG, "closeCamera: X");
    }

    /* access modifiers changed from: protected */
    public void closeVideoFileDescriptor() {
        ParcelFileDescriptor parcelFileDescriptor = this.mVideoFileDescriptor;
        if (parcelFileDescriptor != null) {
            try {
                parcelFileDescriptor.close();
            } catch (IOException e2) {
                Log.e(TAG, "fail to close fd", e2);
            }
            this.mVideoFileDescriptor = null;
        }
    }

    /* access modifiers changed from: protected */
    public void deleteVideoFile(String str) {
        String str2 = TAG;
        Log.v(str2, "delete invalid video " + str);
        if (!new File(str).delete()) {
            String str3 = TAG;
            Log.v(str3, "fail to delete " + str);
        }
    }

    /* access modifiers changed from: protected */
    public void doLaterReleaseIfNeed() {
        Camera camera = ((BaseModule) this).mActivity;
        if (camera != null && camera.isActivityPaused()) {
            Log.d(TAG, "doLaterRelease");
            ((BaseModule) this).mActivity.pause();
            ((BaseModule) this).mActivity.releaseAll(true, true);
        }
    }

    /* access modifiers changed from: protected */
    public void doReturnToCaller(boolean z) {
        int i;
        Intent intent = new Intent();
        if (z) {
            i = -1;
            intent.setData(this.mCurrentVideoUri);
            intent.setFlags(1);
        } else {
            i = 0;
        }
        ((BaseModule) this).mActivity.setResult(i, intent);
        ((BaseModule) this).mActivity.finish();
    }

    /* access modifiers changed from: protected */
    public boolean enableFaceDetection() {
        return DataRepository.dataItemGlobal().getBoolean("pref_camera_facedetection_key", getResources().getBoolean(R.bool.pref_camera_facedetection_default));
    }

    @Override // com.android.camera.MutexModeManager.MutexCallBack, com.android.camera.module.BaseModule
    public void enterMutexMode(int i) {
        setZoomRatio(1.0f);
        ((BaseModule) this).mSettingsOverrider.overrideSettings(CameraSettings.KEY_WHITE_BALANCE, null, CameraSettings.KEY_COLOR_EFFECT, null);
        onSharedPreferenceChanged();
    }

    /* access modifiers changed from: protected */
    public void enterSavePowerMode() {
        String str = TAG;
        Log.d(str, "currentBrightness: " + ((BaseModule) this).mActivity.getCurrentBrightness());
        Camera camera = ((BaseModule) this).mActivity;
        if (camera != null && camera.getCurrentBrightness() == 255) {
            Log.d(TAG, "enterSavePowerMode");
            ((BaseModule) this).mHandler.post(new Runnable() {
                /* class com.android.camera.module.VideoBase.AnonymousClass2 */

                public void run() {
                    Camera camera = ((BaseModule) VideoBase.this).mActivity;
                    if (camera != null) {
                        camera.setWindowBrightness(81);
                        VideoBase.this.mSavePowerMode = true;
                    }
                }
            });
        }
    }

    @Override // com.android.camera.MutexModeManager.MutexCallBack, com.android.camera.module.BaseModule
    public void exitMutexMode(int i) {
        ((BaseModule) this).mSettingsOverrider.restoreSettings();
        onSharedPreferenceChanged();
    }

    /* access modifiers changed from: protected */
    public void exitSavePowerMode() {
        ((BaseModule) this).mHandler.removeMessages(52);
        if (this.mSavePowerMode) {
            Log.d(TAG, "exitSavePowerMode");
            ((BaseModule) this).mHandler.post(new Runnable() {
                /* class com.android.camera.module.VideoBase.AnonymousClass3 */

                public void run() {
                    Camera camera = ((BaseModule) VideoBase.this).mActivity;
                    if (camera != null) {
                        camera.restoreWindowBrightness();
                        VideoBase.this.mSavePowerMode = false;
                    }
                }
            });
        }
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0064, code lost:
        if (r8.equals(com.android.camera.data.data.config.ComponentConfigSlowMotion.DATA_CONFIG_NEW_SLOW_MOTION_120) != false) goto L_0x0068;
     */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x006a  */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x007f  */
    public ContentValues genContentValues(int i, int i2, String str, boolean z, boolean z2) {
        String str2;
        String str3;
        String createName = createName(System.currentTimeMillis(), i2);
        boolean z3 = false;
        if (i2 > 0) {
            createName = createName + String.format(Locale.ENGLISH, "_%d", Integer.valueOf(i2));
        }
        if (z) {
            createName = createName + Storage.VIDEO_8K_SUFFIX;
        }
        if (!TextUtils.isEmpty(str)) {
            int hashCode = str.hashCode();
            if (hashCode != -1150307548) {
                if (hashCode == -1150306525 && str.equals(ComponentConfigSlowMotion.DATA_CONFIG_NEW_SLOW_MOTION_240)) {
                    z3 = true;
                    if (z3) {
                        createName = createName + Storage.HSR_120_SUFFIX;
                    } else if (z3) {
                        createName = createName + Storage.HSR_240_SUFFIX;
                    }
                }
            }
            z3 = true;
            if (z3) {
            }
        }
        String str4 = createName + Util.convertOutputFormatToFileExt(i);
        String convertOutputFormatToMimeType = Util.convertOutputFormatToMimeType(i);
        if (!TextUtils.isEmpty(str) && str.equals(ComponentConfigSlowMotion.DATA_CONFIG_NEW_SLOW_MOTION_960)) {
            String generatePrimaryTempFile = Storage.isUseDocumentMode() ? Storage.generatePrimaryTempFile() : Storage.generateTempFilepath();
            str2 = generatePrimaryTempFile + '/' + str4;
            Util.createFile(new File(generatePrimaryTempFile + File.separator + Storage.AVOID_SCAN_FILE_NAME));
        } else if (z2) {
            str2 = Storage.generateFilepath(str4);
        } else {
            String generatePrimaryDirectoryPath = Storage.generatePrimaryDirectoryPath();
            Util.mkdirs(new File(generatePrimaryDirectoryPath), 511, -1, -1);
            if (Util.isPathExist(generatePrimaryDirectoryPath)) {
                str3 = Storage.generatePrimaryFilepath(str4);
            } else {
                str3 = Storage.DIRECTORY + '/' + str4;
            }
            str2 = str3;
        }
        Log.d(TAG, "genContentValues: path=" + str2);
        ContentValues contentValues = new ContentValues(8);
        contentValues.put("title", createName);
        contentValues.put("_display_name", str4);
        contentValues.put("mime_type", convertOutputFormatToMimeType);
        contentValues.put("_data", str2);
        contentValues.put("resolution", Integer.toString(this.mVideoSize.width) + "x" + Integer.toString(this.mVideoSize.height));
        Location currentLocation = LocationManager.instance().getCurrentLocation();
        if (!(currentLocation == null || (currentLocation.getLatitude() == 0.0d && currentLocation.getLongitude() == 0.0d))) {
            contentValues.put("latitude", Double.valueOf(currentLocation.getLatitude()));
            contentValues.put("longitude", Double.valueOf(currentLocation.getLongitude()));
        }
        return contentValues;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.BaseModule
    public int getCameraRotation() {
        return ((((BaseModule) this).mOrientationCompensation - ((BaseModule) this).mDisplayRotation) + 360) % 360;
    }

    public CameraSize getVideoSize() {
        return this.mVideoSize;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.BaseModule
    public void initializeCapabilities() {
        super.initializeCapabilities();
        ((BaseModule) this).mContinuousFocusSupported = Util.isSupported(3, ((BaseModule) this).mCameraCapabilities.getSupportedFocusModes());
    }

    /* access modifiers changed from: protected */
    public void initializeFocusManager() {
        this.mFocusManager = new FocusManager2(((BaseModule) this).mCameraCapabilities, this, isFrontCamera(), ((BaseModule) this).mActivity.getMainLooper());
        Rect renderRect = ((BaseModule) this).mActivity.getCameraScreenNail() != null ? ((BaseModule) this).mActivity.getCameraScreenNail().getRenderRect() : null;
        if (renderRect == null || renderRect.width() <= 0) {
            this.mFocusManager.setRenderSize(Util.sWindowWidth, Util.sWindowHeight);
            this.mFocusManager.setPreviewSize(Util.sWindowWidth, Util.sWindowHeight);
            return;
        }
        this.mFocusManager.setRenderSize(((BaseModule) this).mActivity.getCameraScreenNail().getRenderWidth(), ((BaseModule) this).mActivity.getCameraScreenNail().getRenderHeight());
        this.mFocusManager.setPreviewSize(renderRect.width(), renderRect.height());
    }

    /* access modifiers changed from: protected */
    public boolean is3ALocked() {
        return this.m3ALocked;
    }

    /* access modifiers changed from: protected */
    public boolean isAEAFLockSupported() {
        return true;
    }

    public boolean isCameraEnabled() {
        return this.mPreviewing;
    }

    /* access modifiers changed from: protected */
    public boolean isCameraSessionReady() {
        Camera2Proxy camera2Proxy = ((BaseModule) this).mCamera2Device;
        return camera2Proxy != null && camera2Proxy.isSessionReady();
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public boolean isCaptureIntent() {
        return this.mIsVideoCaptureIntent;
    }

    @Override // com.android.camera.module.Module, com.android.camera.protocol.ModeProtocol.CameraAction
    public boolean isDoingAction() {
        return this.mMediaRecorderRecording && !this.mMediaRecorderRecordingPaused && !ModuleManager.isProVideoModule();
    }

    @Override // com.android.camera2.Camera2Proxy.FaceDetectionCallback
    public boolean isFaceDetectStarted() {
        return this.mFaceDetectionStarted;
    }

    @Override // com.android.camera.module.BaseModule, com.android.camera.ui.FocusView.ExposureViewListener
    public boolean isMeteringAreaOnly() {
        return !((BaseModule) this).mFocusAreaSupported && ((BaseModule) this).mMeteringAreaSupported && !((BaseModule) this).mFocusOrAELockSupported;
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule, com.android.camera.protocol.ModeProtocol.CameraAction
    public boolean isRecording() {
        return this.mMediaRecorderRecording;
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public boolean isSelectingCapturedResult() {
        return isCaptureIntent() && ((ModeProtocol.BaseDelegate) ModeCoordinatorImpl.getInstance().getAttachProtocol(160)).getActiveFragment(R.id.bottom_action) == 4083;
    }

    /* access modifiers changed from: protected */
    public boolean isSessionReady() {
        return this.mIsSessionReady;
    }

    @Override // com.android.camera.module.BaseModule, com.android.camera.ui.FocusView.ExposureViewListener
    public boolean isShowAeAfLockIndicator() {
        return this.m3ALocked;
    }

    @Override // com.android.camera.module.BaseModule
    public boolean isThermalThreshold() {
        if (!this.mMediaRecorderRecording) {
            return false;
        }
        long uptimeMillis = (SystemClock.uptimeMillis() - this.mRecordingStartTime) / 60000;
        return isFrontCamera() ? uptimeMillis >= 10 : (b.Xu || b.Zu) ? uptimeMillis >= 3 : uptimeMillis >= 20;
    }

    @Override // com.android.camera.module.Module
    public boolean isUnInterruptable() {
        ((BaseModule) this).mUnInterruptableReason = null;
        if (isPostProcessing()) {
            ((BaseModule) this).mUnInterruptableReason = "post process";
        }
        return ((BaseModule) this).mUnInterruptableReason != null;
    }

    @Override // com.android.camera2.Camera2Proxy.FaceDetectionCallback
    public boolean isUseFaceInfo() {
        return false;
    }

    /* access modifiers changed from: protected */
    public boolean isVideoBokehEnabled() {
        return CameraSettings.isVideoBokehOn() || (DataRepository.dataItemRunning().getComponentRunningShine().isVideoBeautyOpen(DataRepository.dataItemGlobal().getCurrentMode()) && ((BaseModule) this).mCameraCapabilities.isSupportVideoBokehAdjust());
    }

    @Override // com.android.camera.module.BaseModule
    public boolean isZoomEnabled() {
        return !CameraSettings.isFrontCamera() && !CameraSettings.isVideoBokehOn() && isCameraEnabled() && isFrameAvailable();
    }

    /* access modifiers changed from: protected */
    public void keepPowerSave() {
        Log.d(TAG, "keepPowerSave");
        exitSavePowerMode();
        ((BaseModule) this).mHandler.sendEmptyMessageDelayed(52, 1500000);
    }

    /* access modifiers changed from: protected */
    public void keepScreenOn() {
        ((BaseModule) this).mHandler.removeMessages(17);
        ((BaseModule) this).mHandler.removeMessages(2);
        ((BaseModule) this).mHandler.removeMessages(52);
        getWindow().addFlags(128);
    }

    /* access modifiers changed from: protected */
    public void keepScreenOnAwhile() {
        ((BaseModule) this).mHandler.sendEmptyMessageDelayed(17, 1000);
    }

    /* access modifiers changed from: protected */
    public void lockAEAF() {
        Camera2Proxy camera2Proxy;
        Log.d(TAG, "lockAEAF");
        if (((BaseModule) this).mAeLockSupported && (camera2Proxy = ((BaseModule) this).mCamera2Device) != null) {
            camera2Proxy.setAELock(true);
        }
        this.mFocusManager.setAeAwbLock(true);
        this.m3ALocked = true;
    }

    @Override // com.android.camera.module.loader.camera2.FocusManager2.Listener
    public boolean multiCapture() {
        Log.v(TAG, "multiCapture");
        return false;
    }

    /* access modifiers changed from: protected */
    public boolean needChooseVideoBeauty(BeautyValues beautyValues) {
        if (!((BaseModule) this).mCameraCapabilities.isSupportVideoBeauty()) {
            return false;
        }
        int currentMode = DataRepository.dataItemGlobal().getCurrentMode();
        return DataRepository.dataItemRunning().getComponentRunningShine().isSmoothBarVideoBeautyVersion(currentMode) ? DataRepository.dataItemRunning().getComponentRunningShine().isVideoBeautyOpen(currentMode) : isFaceBeautyOn(beautyValues);
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void notifyError() {
        if (currentIsMainThread() && isRecording() && !isPostProcessing()) {
            stopVideoRecording(true, false);
        }
        super.notifyError();
    }

    @Override // com.android.camera.module.loader.camera2.FocusManager2.Listener
    public void notifyFocusAreaUpdate() {
        updatePreferenceTrampoline(3);
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public boolean onBackPressed() {
        if (!isFrameAvailable() || this.mStereoSwitchThread != null) {
            return false;
        }
        if (!this.mMediaRecorderRecording) {
            return super.onBackPressed();
        }
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis - ((BaseModule) this).mLastBackPressedTime > 3000) {
            ((BaseModule) this).mLastBackPressedTime = currentTimeMillis;
            ToastUtils.showToast((Context) ((BaseModule) this).mActivity, (int) R.string.record_back_pressed_hint, true);
        } else {
            stopVideoRecording(true, false);
        }
        return true;
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void onBroadcastReceived(Context context, Intent intent) {
        super.onBroadcastReceived(context, intent);
        String action = intent.getAction();
        if ("android.intent.action.MEDIA_EJECT".equals(action)) {
            if (Storage.isCurrentStorageIsSecondary()) {
                Storage.switchToPhoneStorage();
                stopVideoRecording(true, false);
            }
        } else if ("android.intent.action.ACTION_SHUTDOWN".equals(action) || "android.intent.action.REBOOT".equals(action)) {
            Log.i(TAG, "onBroadcastReceived: device shutdown or reboot");
            stopVideoRecording(true, false);
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.BaseModule
    public void onCameraOpened() {
        super.onCameraOpened();
        initMetaParser();
    }

    @Override // com.android.camera2.Camera2Proxy.FaceDetectionCallback
    public void onFaceDetected(CameraHardwareFace[] cameraHardwareFaceArr, FaceAnalyzeInfo faceAnalyzeInfo) {
        if (isCreated() && cameraHardwareFaceArr != null && b.hl()) {
            boolean z = cameraHardwareFaceArr.length > 0;
            if (z != this.mFaceDetected && isFrontCamera() && ((BaseModule) this).mModuleIndex == 162) {
                ((BaseModule) this).mCamera2Device.resumePreview();
            }
            this.mFaceDetected = z;
        }
    }

    @Override // com.android.camera.module.BaseModule, com.android.camera.protocol.ModeProtocol.EvChangedProtocol
    public void onFocusAreaChanged(int i, int i2) {
        Camera camera = ((BaseModule) this).mActivity;
        if (camera != null && !camera.isActivityPaused() && isAlive()) {
            Rect cropRegion = getCropRegion();
            Rect activeArraySize = getActiveArraySize();
            ((BaseModule) this).mActivity.getSensorStateManager().setFocusSensorEnabled(this.mFocusManager.getMeteringAreas(cropRegion, activeArraySize) != null);
            ((BaseModule) this).mCamera2Device.setAFRegions(this.mFocusManager.getMeteringOrFocusAreas(i, i2, cropRegion, activeArraySize, true));
            ((BaseModule) this).mCamera2Device.startFocus(FocusTask.create(1), ((BaseModule) this).mModuleIndex);
        }
    }

    @Override // com.android.camera2.Camera2Proxy.FocusCallback
    public void onFocusStateChanged(FocusTask focusTask) {
        if (isCreated() && !isDeparted()) {
            int focusTrigger = focusTask.getFocusTrigger();
            if (focusTrigger == 1) {
                String str = TAG;
                Log.v(str, "focusTime=" + focusTask.getElapsedTime() + "ms focused=" + focusTask.isSuccess() + " waitForRecording=" + this.mFocusManager.isFocusingSnapOnFinish());
                ((BaseModule) this).mMainProtocol.setFocusViewType(true);
                this.mFocusManager.onFocusResult(focusTask);
                ((BaseModule) this).mActivity.getSensorStateManager().reset();
                if (focusTask.isSuccess() && this.m3ALocked) {
                    ((BaseModule) this).mCamera2Device.lockExposure(true);
                }
            } else if (focusTrigger == 2 && !this.mMediaRecorderRecording && !this.m3ALocked) {
                this.mFocusManager.onFocusResult(focusTask);
            }
        }
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void onHostStopAndNotifyActionStop() {
        if (this.mInStartingFocusRecording) {
            this.mInStartingFocusRecording = false;
            ModeProtocol.RecordState recordState = (ModeProtocol.RecordState) ModeCoordinatorImpl.getInstance().getAttachProtocol(212);
            if (recordState != null) {
                recordState.onFinish();
            }
        }
        if (isRecording() && isCameraSessionReady()) {
            stopVideoRecording(true, true);
        }
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (((BaseModule) this).mPaused) {
            return true;
        }
        boolean z = false;
        if (!isFrameAvailable()) {
            return false;
        }
        if (!(i == 24 || i == 25)) {
            if (i == 27 || i == 66) {
                if (keyEvent.getRepeatCount() == 0) {
                    if (isIgnoreTouchEvent()) {
                        return true;
                    }
                    if (!Util.isFingerPrintKeyEvent(keyEvent)) {
                        performKeyClicked(40, getString(R.string.pref_camera_volumekey_function_entryvalue_shutter), keyEvent.getRepeatCount(), true);
                    } else if (CameraSettings.isFingerprintCaptureEnable() && !((BaseModule) this).mMainProtocol.isShowReviewViews()) {
                        performKeyClicked(30, getString(R.string.pref_camera_volumekey_function_entryvalue_shutter), keyEvent.getRepeatCount(), true);
                    }
                    return true;
                }
                return super.onKeyDown(i, keyEvent);
            } else if (!(i == 87 || i == 88)) {
                if (i != 700) {
                    if (i == 701 && isRecording() && !isPostProcessing()) {
                        if (!isFrontCamera()) {
                            return false;
                        }
                        stopVideoRecording(true, false);
                    }
                } else if (isRecording() && !isPostProcessing()) {
                    if (!isBackCamera()) {
                        return false;
                    }
                    stopVideoRecording(true, false);
                }
                return super.onKeyDown(i, keyEvent);
            }
        }
        if (isIgnoreTouchEvent() || !isCameraEnabled()) {
            Log.w(TAG, "preview stop or need ignore this touch event.");
            return true;
        }
        ModeProtocol.LiveVVChooser liveVVChooser = (ModeProtocol.LiveVVChooser) ModeCoordinatorImpl.getInstance().getAttachProtocol(229);
        if (liveVVChooser != null && liveVVChooser.isShow()) {
            return false;
        }
        if (i == 24 || i == 88) {
            z = true;
        }
        if (handleVolumeKeyEvent(z, true, keyEvent.getRepeatCount(), keyEvent.getDevice().isExternal())) {
            return true;
        }
        return super.onKeyDown(i, keyEvent);
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        if (i != 4) {
            if (i == 27 || i == 66) {
                return true;
            }
        } else if (((ModeProtocol.BackStack) ModeCoordinatorImpl.getInstance().getAttachProtocol(171)).handleBackStackFromKeyBack()) {
            return true;
        }
        return super.onKeyUp(i, keyEvent);
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void onLongPress(float f2, float f3) {
        int i = (int) f2;
        int i2 = (int) f3;
        if (isInTapableRect(i, i2)) {
            onSingleTapUp(i, i2, true);
            if (isAEAFLockSupported() && CameraSettings.isAEAFLockSupport()) {
                lockAEAF();
            }
            ((BaseModule) this).mMainProtocol.performHapticFeedback(0);
        }
    }

    @Override // com.android.camera.module.BaseModule, com.android.camera.protocol.ModeProtocol.EvChangedProtocol
    public void onMeteringAreaChanged(int i, int i2) {
        Camera camera = ((BaseModule) this).mActivity;
        if (camera != null && !camera.isActivityPaused() && isAlive()) {
            Rect cropRegion = getCropRegion();
            Rect activeArraySize = getActiveArraySize();
            ((BaseModule) this).mActivity.getSensorStateManager().setFocusSensorEnabled(this.mFocusManager.getMeteringAreas(cropRegion, activeArraySize) != null);
            ((BaseModule) this).mCamera2Device.setAERegions(this.mFocusManager.getMeteringOrFocusAreas(i, i2, cropRegion, activeArraySize, false));
            ((BaseModule) this).mCamera2Device.resumePreview();
        }
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void onNewIntent() {
        setCaptureIntent(((BaseModule) this).mActivity.getCameraIntentManager().isVideoCaptureIntent());
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void onOrientationChanged(int i, int i2, int i3) {
        if (i != -1) {
            ((BaseModule) this).mOrientation = i;
            EffectController.getInstance().setOrientation(Util.getShootOrientation(((BaseModule) this).mActivity, ((BaseModule) this).mOrientation));
            checkActivityOrientation();
            if (((BaseModule) this).mOrientationCompensation != i2) {
                ((BaseModule) this).mOrientationCompensation = i2;
                setOrientationParameter();
            }
        }
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void onPause() {
        super.onPause();
    }

    @Override // com.android.camera.module.BaseModule, com.android.camera2.Camera2Proxy.CameraMetaDataCallback
    public void onPreviewMetaDataUpdate(CaptureResult captureResult) {
        if (captureResult != null) {
            super.onPreviewMetaDataUpdate(captureResult);
            FlowableEmitter<CaptureResult> flowableEmitter = this.mMetaDataFlowableEmitter;
            if (flowableEmitter != null) {
                flowableEmitter.onNext(captureResult);
            }
        }
    }

    @Override // com.android.camera2.Camera2Proxy.CameraPreviewCallback
    public void onPreviewSessionClosed(CameraCaptureSession cameraCaptureSession) {
        String str = TAG;
        Log.d(str, "onPreviewSessionClosed: " + cameraCaptureSession);
        CameraCaptureSession cameraCaptureSession2 = this.mCurrentSession;
        if (cameraCaptureSession2 != null && cameraCaptureSession2.equals(cameraCaptureSession)) {
            this.mCurrentSession = null;
            setSessionReady(false);
        }
    }

    @Override // com.android.camera2.Camera2Proxy.CameraPreviewCallback
    public void onPreviewSessionFailed(CameraCaptureSession cameraCaptureSession) {
        if (!isTextureExpired() || !retryOnceIfCameraError(((BaseModule) this).mHandler)) {
            String str = TAG;
            Log.d(str, "onPreviewSessionFailed: " + cameraCaptureSession);
            CameraCaptureSession cameraCaptureSession2 = this.mCurrentSession;
            if (cameraCaptureSession2 != null && cameraCaptureSession2.equals(cameraCaptureSession)) {
                this.mCurrentSession = null;
                setSessionReady(false);
            }
            ((BaseModule) this).mHandler.sendEmptyMessage(51);
            return;
        }
        Log.d(TAG, "sessionFailed due to surfaceTexture expired, retry");
    }

    @Override // com.android.camera2.Camera2Proxy.CameraPreviewCallback
    public void onPreviewSessionSuccess(CameraCaptureSession cameraCaptureSession) {
        String str = TAG;
        Log.d(str, "onPreviewSessionSuccess: " + cameraCaptureSession);
        if (cameraCaptureSession != null && isAlive()) {
            this.mCurrentSession = cameraCaptureSession;
            setSessionReady(true);
        }
    }

    @Override // com.android.camera.module.BaseModule
    public void onPreviewSizeChanged(int i, int i2) {
        Log.v(TAG, String.format(Locale.ENGLISH, "onPreviewSizeChanged: %dx%d", Integer.valueOf(i), Integer.valueOf(i2)));
        FocusManager2 focusManager2 = this.mFocusManager;
        if (focusManager2 != null) {
            focusManager2.setPreviewSize(i, i2);
        }
    }

    /* access modifiers changed from: protected */
    public void onPreviewStart() {
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void onResume() {
        super.onResume();
        if (!isRecording() && !((BaseModule) this).mOpenCameraFail && !((BaseModule) this).mCameraDisabled && PermissionManager.checkCameraLaunchPermissions()) {
            if (!this.mPreviewing) {
                startPreview();
            }
            ((BaseModule) this).mHandler.sendEmptyMessage(9);
            keepScreenOnAwhile();
            onSettingsBack();
            if (this.mPreviewing) {
                this.mOnResumeTime = SystemClock.uptimeMillis();
                ((BaseModule) this).mHandler.sendEmptyMessageDelayed(4, 100);
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.CameraAction
    @OnClickAttr
    public void onReviewCancelClicked() {
        if (isSelectingCapturedResult()) {
            deleteCurrentVideo();
            hideAlert();
            return;
        }
        stopVideoRecording(true, false);
        doReturnToCaller(false);
    }

    @Override // com.android.camera.protocol.ModeProtocol.CameraAction
    @OnClickAttr
    public void onReviewDoneClicked() {
        ((BaseModule) this).mMainProtocol.hideReviewViews();
        ModeProtocol.BaseDelegate baseDelegate = (ModeProtocol.BaseDelegate) ModeCoordinatorImpl.getInstance().getAttachProtocol(160);
        if (baseDelegate != null) {
            baseDelegate.delegateEvent(6);
        }
        doReturnToCaller(true);
    }

    @OnClickAttr
    public void onReviewPlayClicked(View view) {
        startPlayVideoActivity();
    }

    /* access modifiers changed from: protected */
    public void onSettingsBack() {
        ChangeManager changeManager = CameraSettings.sCameraChangeManager;
        if (changeManager.check(3)) {
            changeManager.clear(3);
            restorePreferences();
        } else if (changeManager.check(1)) {
            changeManager.clear(1);
            onSharedPreferenceChanged();
        }
    }

    @Override // com.android.camera.module.BaseModule
    public void onShineChanged(int i) {
        if (i == 196) {
            updatePreferenceInWorkThread(68, 69);
        } else if (i == 239) {
            updatePreferenceInWorkThread(13);
        } else if (i == 243) {
            updatePreferenceInWorkThread(67);
        } else {
            throw new RuntimeException("unknown configItem changed");
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.CameraAction
    public void onShutterButtonClick(int i) {
    }

    @Override // com.android.camera.protocol.ModeProtocol.CameraAction
    public void onShutterButtonFocus(boolean z, int i) {
    }

    @Override // com.android.camera.protocol.ModeProtocol.CameraAction
    public boolean onShutterButtonLongClick() {
        Log.v(TAG, "onShutterButtonLongClick");
        return false;
    }

    @Override // com.android.camera.protocol.ModeProtocol.CameraAction
    public void onShutterButtonLongClickCancel(boolean z) {
        onShutterButtonFocus(false, 2);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.BaseModule
    public void onThermalConstrained() {
        super.onThermalConstrained();
        if (this.mMediaRecorderRecording) {
            stopVideoRecording(true, false);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.CameraAction
    @OnClickAttr
    public void onThumbnailClicked(View view) {
        if (!this.mMediaRecorderRecording && ((BaseModule) this).mActivity.getThumbnailUpdater().getThumbnail() != null) {
            ((BaseModule) this).mActivity.gotoGallery();
        }
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void onUserInteraction() {
        super.onUserInteraction();
        if (!this.mMediaRecorderRecording) {
            keepScreenOnAwhile();
        }
    }

    /* access modifiers changed from: protected */
    public void onWaitStopCallbackTimeout() {
    }

    @Override // com.android.camera.module.loader.camera2.FocusManager2.Listener
    public boolean onWaitingFocusFinished() {
        if (!isFrameAvailable()) {
            return false;
        }
        Log.v(TAG, MistatsConstants.BaseEvent.CAPTURE);
        ((BaseModule) this).mHandler.removeMessages(55);
        if (!this.mInStartingFocusRecording) {
            return false;
        }
        this.mInStartingFocusRecording = false;
        startVideoRecording();
        return true;
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
        String str = TAG;
        Log.d(str, "onWindowFocusChanged: " + z);
        if (!this.mMediaRecorderRecording) {
            return;
        }
        if (z) {
            keepPowerSave();
        } else {
            exitSavePowerMode();
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.BaseModule
    public void openSettingActivity() {
        Intent intent = new Intent();
        intent.setClass(((BaseModule) this).mActivity, CameraPreferenceActivity.class);
        intent.putExtra(BasePreferenceActivity.FROM_WHERE, ((BaseModule) this).mModuleIndex);
        intent.putExtra(a.zf, getResources().getString(R.string.pref_camera_settings_category));
        if (((BaseModule) this).mActivity.startFromKeyguard()) {
            intent.putExtra(a.mf, true);
        }
        ((BaseModule) this).mActivity.startActivity(intent);
        ((BaseModule) this).mActivity.setJumpFlag(2);
        CameraStatUtils.trackGotoSettings(((BaseModule) this).mModuleIndex);
    }

    /* access modifiers changed from: protected */
    public void parseIntent(Intent intent) {
        if (intent.getExtras() != null) {
            this.mIntentRequestSize = ((BaseModule) this).mActivity.getCameraIntentManager().getRequestSize();
            Uri extraSavedUri = ((BaseModule) this).mActivity.getCameraIntentManager().getExtraSavedUri();
            if (extraSavedUri != null) {
                try {
                    this.mVideoFileDescriptor = CameraAppImpl.getAndroidContext().getContentResolver().openFileDescriptor(extraSavedUri, "rw");
                    this.mCurrentVideoUri = extraSavedUri;
                    String str = TAG;
                    Log.d(str, "parseIntent: outputUri=" + extraSavedUri);
                } catch (FileNotFoundException e2) {
                    Log.e(TAG, e2.getMessage());
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.BaseModule
    public void performKeyClicked(int i, String str, int i2, boolean z) {
        if (i2 == 0 && z) {
            if (isIgnoreTouchEvent()) {
                Log.w(TAG, "ignore volume key");
                return;
            }
            ModeProtocol.LiveVVChooser liveVVChooser = (ModeProtocol.LiveVVChooser) ModeCoordinatorImpl.getInstance().getAttachProtocol(229);
            if (liveVVChooser == null || !liveVVChooser.isShow()) {
                restoreBottom();
                onShutterButtonClick(i);
                return;
            }
            liveVVChooser.startShot();
        }
    }

    @Override // com.android.camera.module.loader.camera2.FocusManager2.Listener
    public void playFocusSound(int i) {
        playCameraSound(i);
    }

    @Override // com.android.camera.protocol.ModeProtocol.PlayVideoProtocol
    public void playVideo() {
        startPlayVideoActivity();
    }

    /* access modifiers changed from: protected */
    public void readVideoPreferences() {
    }

    /* access modifiers changed from: protected */
    public void resetScreenOn() {
        ((BaseModule) this).mHandler.removeMessages(17);
        ((BaseModule) this).mHandler.removeMessages(2);
        ((BaseModule) this).mHandler.removeMessages(52);
    }

    /* access modifiers changed from: protected */
    public void resizeForPreviewAspectRatio() {
    }

    /* access modifiers changed from: protected */
    public void restoreOuterAudio() {
        if (this.mAudioController == null) {
            this.mAudioController = new AudioController(((BaseModule) this).mActivity.getApplicationContext());
        }
        this.mAudioController.restoreAudio();
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.BaseModule
    public void sendOpenFailMessage() {
        ((BaseModule) this).mHandler.sendEmptyMessage(10);
    }

    /* access modifiers changed from: protected */
    public void setCaptureIntent(boolean z) {
        this.mIsVideoCaptureIntent = z;
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void setFrameAvailable(boolean z) {
        super.setFrameAvailable(z);
        if (z && CameraSettings.isCameraSoundOpen()) {
            ((BaseModule) this).mActivity.loadCameraSound(1);
            ((BaseModule) this).mActivity.loadCameraSound(0);
            ((BaseModule) this).mActivity.loadCameraSound(2);
            ((BaseModule) this).mActivity.loadCameraSound(3);
        }
    }

    /* access modifiers changed from: protected */
    public void setSessionReady(boolean z) {
        this.mIsSessionReady = z;
    }

    /* access modifiers changed from: protected */
    public void setVideoFocusMode(String str, boolean z) {
        this.mVideoFocusMode = str;
        if (z) {
            updateVideoFocusMode();
        }
    }

    @Override // com.android.camera.module.loader.camera2.FocusManager2.Listener
    public boolean shouldCaptureDirectly() {
        return false;
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public boolean shouldReleaseLater() {
        return this.mInStartingFocusRecording || isRecording();
    }

    /* access modifiers changed from: protected */
    public void showAlert() {
        pausePreview();
        ((BaseModule) this).mMainProtocol.showReviewViews(getReviewBitmap());
        enableCameraControls(false);
        ((ModeProtocol.BaseDelegate) ModeCoordinatorImpl.getInstance().getAttachProtocol(160)).delegateEvent(6);
    }

    /* access modifiers changed from: protected */
    public void showConfirmMessage(int i, int i2) {
        AlertDialog alertDialog = this.mDialog;
        if (alertDialog != null && alertDialog.isShowing()) {
            if (this.mTitleId != i && this.mMessageId != i2) {
                this.mDialog.dismiss();
            } else {
                return;
            }
        }
        this.mTitleId = i;
        this.mMessageId = i2;
        Camera camera = ((BaseModule) this).mActivity;
        this.mDialog = RotateDialogController.showSystemAlertDialog(camera, camera.getString(i), ((BaseModule) this).mActivity.getString(i2), ((BaseModule) this).mActivity.getString(17039370), null, null, null);
    }

    /* access modifiers changed from: protected */
    public void silenceOuterAudio() {
        if (this.mAudioController == null) {
            this.mAudioController = new AudioController(((BaseModule) this).mActivity.getApplicationContext());
        }
        this.mAudioController.silenceAudio();
    }

    @Override // com.android.camera.module.loader.camera2.FocusManager2.Listener
    public void startFaceDetection() {
        Camera2Proxy camera2Proxy;
        if (this.mFaceDetectionEnabled && !this.mFaceDetectionStarted && isAlive() && ((BaseModule) this).mMaxFaceCount > 0 && (camera2Proxy = ((BaseModule) this).mCamera2Device) != null) {
            this.mFaceDetectionStarted = true;
            camera2Proxy.startFaceDetection();
        }
    }

    @Override // com.android.camera.module.loader.camera2.FocusManager2.Listener
    public void startFocus() {
        if (isDeviceAlive() && isFrameAvailable()) {
            Log.v(TAG, "startFocus");
            if (((BaseModule) this).mFocusOrAELockSupported) {
                setVideoFocusMode("auto", true);
                ((BaseModule) this).mCamera2Device.startFocus(FocusTask.create(1), ((BaseModule) this).mModuleIndex);
                return;
            }
            ((BaseModule) this).mCamera2Device.resumePreview();
        }
    }

    /* access modifiers changed from: protected */
    public void startVideoRecording() {
    }

    @Override // com.android.camera.module.loader.camera2.FocusManager2.Listener
    public void stopFaceDetection(boolean z) {
        if (this.mFaceDetectionEnabled && this.mFaceDetectionStarted) {
            this.mFaceDetectionStarted = false;
            Camera2Proxy camera2Proxy = ((BaseModule) this).mCamera2Device;
            if (camera2Proxy != null) {
                camera2Proxy.stopFaceDetection();
            }
        }
    }

    @Override // com.android.camera.module.loader.camera2.FocusManager2.Listener
    public void stopObjectTracking(boolean z) {
    }

    public void stopVideoRecording(boolean z, boolean z2) {
    }

    /* access modifiers changed from: protected */
    public boolean supportTouchFocus() {
        return !isFrontCamera();
    }

    /* access modifiers changed from: protected */
    public void switchMutexHDR() {
        if ("off".equals(DataRepository.dataItemConfig().getComponentHdr().getComponentValue(((BaseModule) this).mModuleIndex))) {
            resetMutexModeManually();
        } else {
            ((BaseModule) this).mMutexModePicker.setMutexMode(2);
        }
    }

    /* access modifiers changed from: protected */
    public void unlockAEAF() {
        Log.d(TAG, "unlockAEAF");
        this.m3ALocked = false;
        if (((BaseModule) this).mAeLockSupported) {
            ((BaseModule) this).mCamera2Device.setAELock(false);
        }
        FocusManager2 focusManager2 = this.mFocusManager;
        if (focusManager2 != null) {
            focusManager2.setAeAwbLock(false);
        }
    }

    /* access modifiers changed from: protected */
    public void updateBeauty() {
        int i;
        CameraCapabilities cameraCapabilities = ((BaseModule) this).mCameraCapabilities;
        if (cameraCapabilities == null || !cameraCapabilities.isSupportVideoBeauty() || !((i = ((BaseModule) this).mModuleIndex) == 162 || i == 161)) {
            this.mBeautyValues = null;
            return;
        }
        if (this.mBeautyValues == null) {
            this.mBeautyValues = new BeautyValues();
        }
        CameraSettings.initBeautyValues(this.mBeautyValues, ((BaseModule) this).mModuleIndex);
        ((BaseModule) this).mCamera2Device.setBeautyValues(this.mBeautyValues);
    }

    /* access modifiers changed from: protected */
    public void updateDeviceOrientation() {
        ((BaseModule) this).mCamera2Device.setDeviceOrientation(((BaseModule) this).mOrientation);
    }

    /* access modifiers changed from: protected */
    public void updateFace() {
        boolean enableFaceDetection = enableFaceDetection();
        ModeProtocol.MainContentProtocol mainContentProtocol = ((BaseModule) this).mMainProtocol;
        if (mainContentProtocol != null) {
            mainContentProtocol.setSkipDrawFace(!enableFaceDetection);
        }
        if (enableFaceDetection) {
            if (!this.mFaceDetectionEnabled) {
                this.mFaceDetectionEnabled = true;
                startFaceDetection();
            }
        } else if (this.mFaceDetectionEnabled) {
            stopFaceDetection(true);
            this.mFaceDetectionEnabled = false;
        }
    }

    @Override // com.android.camera.module.BaseModule
    public void updateFlashPreference() {
        if (!((BaseModule) this).mMutexModePicker.isNormal() && !((BaseModule) this).mMutexModePicker.isSupportedFlashOn() && !((BaseModule) this).mMutexModePicker.isSupportedTorch()) {
            resetMutexModeManually();
        }
        setFlashMode(DataRepository.dataItemConfig().getComponentFlash().getComponentValue(((BaseModule) this).mModuleIndex));
    }

    /* access modifiers changed from: protected */
    public void updateFocusArea() {
        Camera camera = ((BaseModule) this).mActivity;
        if (camera != null && !camera.isActivityPaused() && isAlive()) {
            if (((BaseModule) this).mCamera2Device == null) {
                Log.e(TAG, "updateFocusArea: null camera device");
                return;
            }
            Rect cropRegion = getCropRegion();
            Rect activeArraySize = getActiveArraySize();
            ((BaseModule) this).mActivity.getSensorStateManager().setFocusSensorEnabled(this.mFocusManager.getMeteringAreas(cropRegion, activeArraySize) != null);
            ((BaseModule) this).mCamera2Device.setAERegions(this.mFocusManager.getMeteringAreas(cropRegion, activeArraySize));
            if (((BaseModule) this).mFocusAreaSupported) {
                ((BaseModule) this).mCamera2Device.setAFRegions(this.mFocusManager.getFocusAreas(cropRegion, activeArraySize));
            }
            String focusMode = CameraSettings.getFocusMode();
            if (!((BaseModule) this).mFocusAreaSupported || "manual".equals(focusMode)) {
                ((BaseModule) this).mCamera2Device.resumePreview();
            }
        }
    }

    /* access modifiers changed from: protected */
    public void updateFocusCallback() {
        Camera2Proxy camera2Proxy = ((BaseModule) this).mCamera2Device;
        if (camera2Proxy == null) {
            Log.e(TAG, "updateFocusCallback: null camera device");
        } else if (((BaseModule) this).mContinuousFocusSupported) {
            if (AutoFocus.LEGACY_CONTINUOUS_VIDEO.equals(this.mVideoFocusMode)) {
                ((BaseModule) this).mCamera2Device.setFocusCallback(this);
            }
        } else if (((BaseModule) this).mAELockOnlySupported) {
            camera2Proxy.setFocusCallback(this);
        }
    }

    /* access modifiers changed from: protected */
    public void updateMotionFocusManager() {
        ((BaseModule) this).mActivity.getSensorStateManager().setFocusSensorEnabled("auto".equals(this.mVideoFocusMode));
    }

    /* access modifiers changed from: protected */
    public void updateRecordingTime() {
        if (isThermalThreshold() && !"0".equals(CameraSettings.getFlashMode(((BaseModule) this).mModuleIndex))) {
            ThermalDetector.getInstance().onThermalNotification();
        }
    }

    /* access modifiers changed from: protected */
    public void updateVideoFocusMode() {
        if (((BaseModule) this).mCamera2Device == null) {
            Log.e(TAG, "updateVideoFocusMode: null camera device");
            return;
        }
        int[] supportedFocusModes = ((BaseModule) this).mCameraCapabilities.getSupportedFocusModes();
        int convertToFocusMode = AutoFocus.convertToFocusMode(this.mVideoFocusMode);
        if (Util.isSupported(convertToFocusMode, supportedFocusModes)) {
            ((BaseModule) this).mCamera2Device.setFocusMode(convertToFocusMode);
            updateMotionFocusManager();
            updateFocusCallback();
        }
        String focusMode = CameraSettings.getFocusMode();
        if (((BaseModule) this).mModuleIndex == 180 && focusMode.equals("manual")) {
            this.mFocusManager.setFocusMode(focusMode);
            setFocusMode(focusMode);
            int focusPosition = CameraSettings.getFocusPosition();
            ((BaseModule) this).mCamera2Device.setFocusDistance((((BaseModule) this).mCameraCapabilities.getMinimumFocusDistance() * ((float) focusPosition)) / 1000.0f);
        }
    }

    /* access modifiers changed from: protected */
    public void waitStereoSwitchThread() {
        try {
            if (this.mStereoSwitchThread != null) {
                this.mStereoSwitchThread.cancel();
                this.mStereoSwitchThread.join();
                this.mStereoSwitchThread = null;
            }
        } catch (InterruptedException e2) {
            Log.e(TAG, e2.getMessage(), e2);
        }
    }
}
