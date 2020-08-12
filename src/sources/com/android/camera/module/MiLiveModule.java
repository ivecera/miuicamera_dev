package com.android.camera.module;

import a.a.a;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.hardware.SensorEvent;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CaptureResult;
import android.location.Location;
import android.media.Image;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.telephony.TelephonyManager;
import android.util.Range;
import android.view.KeyEvent;
import android.view.Surface;
import android.view.View;
import com.android.camera.BasePreferenceActivity;
import com.android.camera.Camera;
import com.android.camera.CameraPreferenceActivity;
import com.android.camera.CameraSettings;
import com.android.camera.CameraSize;
import com.android.camera.HybridZoomingSystem;
import com.android.camera.LocationManager;
import com.android.camera.R;
import com.android.camera.RotateDialogController;
import com.android.camera.SensorStateManager;
import com.android.camera.ToastUtils;
import com.android.camera.Util;
import com.android.camera.constant.GlobalConstant;
import com.android.camera.constant.UpdateConstant;
import com.android.camera.data.DataRepository;
import com.android.camera.effect.EffectController;
import com.android.camera.effect.FaceAnalyzeInfo;
import com.android.camera.effect.draw_mode.DrawExtTexAttribute;
import com.android.camera.fragment.CtaNoticeFragment;
import com.android.camera.fragment.beauty.BeautyValues;
import com.android.camera.log.Log;
import com.android.camera.module.impl.component.ILive;
import com.android.camera.module.loader.FunctionParseAsdFace;
import com.android.camera.module.loader.FunctionParseAsdScene;
import com.android.camera.module.loader.camera2.Camera2DataContainer;
import com.android.camera.module.loader.camera2.FocusManager2;
import com.android.camera.module.loader.camera2.FocusTask;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera.statistic.CameraStatUtils;
import com.android.camera.statistic.MistatsConstants;
import com.android.camera.storage.Storage;
import com.android.camera.ui.V6CameraGLSurfaceView;
import com.android.camera2.Camera2Proxy;
import com.android.camera2.CameraCapabilities;
import com.android.camera2.CameraHardwareFace;
import com.android.gallery3d.ui.GLCanvasImpl;
import com.mi.config.b;
import com.xiaomi.camera.core.ParallelTaskData;
import com.xiaomi.recordmediaprocess.SystemUtil;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MiLiveModule extends BaseModule implements ILiveModule, Camera2Proxy.CameraPreviewCallback, Camera2Proxy.FocusCallback, Camera2Proxy.FaceDetectionCallback, ModeProtocol.CameraAction, ModeProtocol.KaleidoscopeProtocol, FocusManager2.Listener, Camera2Proxy.PictureCallback {
    public static int LIB_LOAD_CALLER_PLAYER = 2;
    public static int LIB_LOAD_CALLER_RECORDER = 1;
    private static int sLibLoaded;
    /* access modifiers changed from: private */
    public final String TAG = (MiLiveModule.class.getSimpleName() + "@" + hashCode());
    private boolean m3ALocked;
    private String mBaseFileName;
    protected BeautyValues mBeautyValues;
    private V6CameraGLSurfaceView mCameraView;
    private CtaNoticeFragment mCtaNoticeFragment;
    private AlertDialog mDialog;
    protected boolean mFaceDetectionEnabled;
    protected boolean mFaceDetectionStarted;
    /* access modifiers changed from: private */
    public FocusManager2 mFocusManager;
    private boolean mIsPreviewing = false;
    /* access modifiers changed from: private */
    public ModeProtocol.MiLiveConfigChanges mLiveConfigChanges;
    private int mMessageId;
    private Disposable mMetaDataDisposable;
    private FlowableEmitter<CaptureResult> mMetaDataFlowableEmitter;
    private int mOldOriginVolumeStream;
    /* access modifiers changed from: private */
    public long mOnResumeTime;
    private ModeProtocol.MiLiveRecorderControl.IRecorderListener mRecorderListener = new ModeProtocol.MiLiveRecorderControl.IRecorderListener() {
        /* class com.android.camera.module.MiLiveModule.AnonymousClass1 */

        @Override // com.android.camera.protocol.ModeProtocol.MiLiveRecorderControl.IRecorderListener
        public void onRecorderCancel() {
            Log.d(MiLiveModule.this.TAG, "onRecorderCancel");
            MiLiveModule.this.resetToIdle();
        }

        @Override // com.android.camera.protocol.ModeProtocol.MiLiveRecorderControl.IRecorderListener
        public void onRecorderError() {
            Log.d(MiLiveModule.this.TAG, "onRecorderError");
            MiLiveModule.this.resetToIdle();
        }

        @Override // com.android.camera.protocol.ModeProtocol.MiLiveRecorderControl.IRecorderListener
        public void onRecorderFinish(List<ILive.ILiveSegmentData> list, String str) {
            boolean z = list != null && list.size() > 0 && MiLiveModule.this.mLiveConfigChanges.getTotalRecordingTime() >= 500;
            if (!z) {
                Log.d(MiLiveModule.this.TAG, "onFinish of no segments !!");
                MiLiveModule.this.resetToIdle();
            } else {
                MiLiveModule miLiveModule = MiLiveModule.this;
                miLiveModule.mTelephonyManager.listen(((BaseModule) miLiveModule).mPhoneStateListener, 0);
                Log.v(MiLiveModule.this.TAG, "listen none");
                MiLiveModule.this.trackLiveVideoParams();
                MiLiveModule.this.initReview(list, str);
            }
            boolean z2 = !z;
            MiLiveModule miLiveModule2 = MiLiveModule.this;
            miLiveModule2.onInterceptZoomingEvent(((BaseModule) miLiveModule2).mPreZoomRation, miLiveModule2.getZoomRatio(), -1);
            if (z2 && HybridZoomingSystem.IS_3_OR_MORE_SAT && MiLiveModule.this.isBackCamera()) {
                MiLiveModule.this.updateZoomRatioToggleButtonState(false);
                if (MiLiveModule.this.isUltraWideBackCamera()) {
                    MiLiveModule.this.setMinZoomRatio(0.6f);
                    MiLiveModule miLiveModule3 = MiLiveModule.this;
                    miLiveModule3.setMaxZoomRatio(((BaseModule) miLiveModule3).mCameraCapabilities.getMaxZoomRatio() * 0.6f);
                    return;
                }
                MiLiveModule.this.setMinZoomRatio(0.6f);
                MiLiveModule miLiveModule4 = MiLiveModule.this;
                miLiveModule4.setMaxZoomRatio(Math.min(6.0f, ((BaseModule) miLiveModule4).mCameraCapabilities.getMaxZoomRatio()));
            }
        }

        @Override // com.android.camera.protocol.ModeProtocol.MiLiveRecorderControl.IRecorderListener
        public void onRecorderPaused(@NonNull List<ILive.ILiveSegmentData> list) {
        }
    };
    protected SensorStateManager.SensorStateListener mSensorStateListener = new SensorStateManager.SensorStateListener() {
        /* class com.android.camera.module.MiLiveModule.AnonymousClass2 */

        @Override // com.android.camera.SensorStateManager.SensorStateListener
        public boolean isWorking() {
            return MiLiveModule.this.isAlive() && MiLiveModule.this.getCameraState() != 0;
        }

        @Override // com.android.camera.SensorStateManager.SensorStateListener
        public void notifyDevicePostureChanged() {
            ((BaseModule) MiLiveModule.this).mActivity.getEdgeShutterView().onDevicePostureChanged();
        }

        @Override // com.android.camera.SensorStateManager.SensorStateListener
        public void onDeviceBecomeStable() {
            Log.v(MiLiveModule.this.TAG, "onDeviceBecomeStable");
        }

        @Override // com.android.camera.SensorStateManager.SensorStateListener
        public void onDeviceBeginMoving() {
        }

        @Override // com.android.camera.SensorStateManager.SensorStateListener
        public void onDeviceKeepMoving(double d2) {
            if (!((BaseModule) MiLiveModule.this).mMainProtocol.isEvAdjusted(true) && !((BaseModule) MiLiveModule.this).mPaused && Util.isTimeout(System.currentTimeMillis(), MiLiveModule.this.mTouchFocusStartingTime, 3000) && !MiLiveModule.this.is3ALocked() && MiLiveModule.this.mFocusManager != null && MiLiveModule.this.mFocusManager.isNeedCancelAutoFocus() && !MiLiveModule.this.isRecording()) {
                MiLiveModule.this.mFocusManager.onDeviceKeepMoving(d2);
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
        }

        @Override // com.android.camera.SensorStateManager.SensorStateListener
        public void onDeviceRotationChanged(float[] fArr) {
        }

        @Override // com.android.camera.SensorStateManager.SensorStateListener
        public void onSensorChanged(SensorEvent sensorEvent) {
        }
    };
    protected boolean mShowFace = false;
    protected TelephonyManager mTelephonyManager;
    private int mTitleId;
    /* access modifiers changed from: private */
    public long mTouchFocusStartingTime;
    private CameraSize mVideoSize;

    private class LiveAsdConsumer implements Consumer<Integer> {
        private LiveAsdConsumer() {
        }

        public void accept(Integer num) throws Exception {
        }
    }

    private class MainHandler extends Handler {
        public MainHandler(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message message) {
            int i = message.what;
            if (i == 2) {
                MiLiveModule.this.getWindow().clearFlags(128);
            } else if (i == 4) {
                MiLiveModule.this.checkActivityOrientation();
                if (SystemClock.uptimeMillis() - MiLiveModule.this.mOnResumeTime < 5000) {
                    ((BaseModule) MiLiveModule.this).mHandler.sendEmptyMessageDelayed(4, 100);
                }
            } else if (i == 9) {
                MiLiveModule miLiveModule = MiLiveModule.this;
                ((BaseModule) miLiveModule).mMainProtocol.initializeFocusView(miLiveModule);
            } else if (i == 17) {
                ((BaseModule) MiLiveModule.this).mHandler.removeMessages(17);
                ((BaseModule) MiLiveModule.this).mHandler.removeMessages(2);
                MiLiveModule.this.getWindow().addFlags(128);
                MiLiveModule miLiveModule2 = MiLiveModule.this;
                ((BaseModule) miLiveModule2).mHandler.sendEmptyMessageDelayed(2, (long) miLiveModule2.getScreenDelay());
            } else if (i == 31) {
                MiLiveModule.this.setOrientationParameter();
            } else if (i == 35) {
                MiLiveModule miLiveModule3 = MiLiveModule.this;
                boolean z = false;
                boolean z2 = message.arg1 > 0;
                if (message.arg2 > 0) {
                    z = true;
                }
                miLiveModule3.handleUpdateFaceView(z2, z);
            } else if (i == 51 && !((BaseModule) MiLiveModule.this).mActivity.isActivityPaused()) {
                MiLiveModule miLiveModule4 = MiLiveModule.this;
                ((BaseModule) miLiveModule4).mOpenCameraFail = true;
                miLiveModule4.onCameraException();
            }
        }
    }

    static /* synthetic */ void Bf() {
        ModeProtocol.MainContentProtocol mainContentProtocol = (ModeProtocol.MainContentProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(166);
        if (mainContentProtocol != null) {
            mainContentProtocol.setCenterHint(8, null, null, 0);
        }
    }

    private boolean configReview(boolean z) {
        ModeProtocol.ConfigChanges configChanges = (ModeProtocol.ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164);
        ModeProtocol.BaseDelegate baseDelegate = (ModeProtocol.BaseDelegate) ModeCoordinatorImpl.getInstance().getAttachProtocol(160);
        boolean z2 = false;
        if (configChanges == null || baseDelegate == null) {
            Log.w(this.TAG, "configChanges is null");
            return false;
        }
        if (baseDelegate.getActiveFragment(R.id.full_screen_feature) == 65529) {
            z2 = true;
        }
        if (z ^ z2) {
            Log.d(this.TAG, "config live review~");
            configChanges.configLiveReview();
        } else {
            Log.d(this.TAG, "skip config live review~");
        }
        return true;
    }

    private String createName(long j, int i) {
        if (i > 0) {
            return this.mBaseFileName;
        }
        this.mBaseFileName = new SimpleDateFormat(getString(R.string.video_file_name_format), Locale.ENGLISH).format(new Date(j));
        return this.mBaseFileName;
    }

    private boolean doLaterReleaseIfNeed() {
        Camera camera = ((BaseModule) this).mActivity;
        if (camera == null || !camera.isActivityPaused()) {
            return false;
        }
        ((BaseModule) this).mActivity.pause();
        ((BaseModule) this).mActivity.releaseAll(true, true);
        return true;
    }

    private ContentValues genContentValues(int i, int i2, boolean z) {
        String str;
        String createName = createName(System.currentTimeMillis(), i2);
        if (i2 > 0) {
            createName = createName + String.format(Locale.ENGLISH, "_%d", Integer.valueOf(i2));
        }
        String str2 = createName + Util.convertOutputFormatToFileExt(i);
        String convertOutputFormatToMimeType = Util.convertOutputFormatToMimeType(i);
        if (z) {
            str = Storage.CAMERA_TEMP_DIRECTORY + '/' + str2;
            Util.createFile(new File(Storage.CAMERA_TEMP_DIRECTORY + File.separator + Storage.AVOID_SCAN_FILE_NAME));
        } else {
            str = Storage.DIRECTORY + '/' + str2;
        }
        Log.v(this.TAG, "genContentValues: path=" + str);
        ContentValues contentValues = new ContentValues(8);
        contentValues.put("title", createName);
        contentValues.put("_display_name", str2);
        contentValues.put("mime_type", convertOutputFormatToMimeType);
        contentValues.put("_data", str);
        contentValues.put("resolution", this.mVideoSize.width + "x" + this.mVideoSize.height);
        Location currentLocation = LocationManager.instance().getCurrentLocation();
        if (!(currentLocation == null || (currentLocation.getLatitude() == 0.0d && currentLocation.getLongitude() == 0.0d))) {
            contentValues.put("latitude", Double.valueOf(currentLocation.getLatitude()));
            contentValues.put("longitude", Double.valueOf(currentLocation.getLongitude()));
        }
        return contentValues;
    }

    /* access modifiers changed from: private */
    public void handleUpdateFaceView(boolean z, boolean z2) {
        boolean isFrontCamera = isFrontCamera();
        if (!z) {
            ((BaseModule) this).mMainProtocol.updateFaceView(z, z2, isFrontCamera, false, -1);
        } else if (this.mFaceDetectionStarted && 1 != ((BaseModule) this).mCamera2Device.getFocusMode()) {
            ((BaseModule) this).mMainProtocol.updateFaceView(z, true, isFrontCamera, true, ((BaseModule) this).mCameraDisplayOrientation);
        }
    }

    private void initLiveConfig() {
        this.mLiveConfigChanges = (ModeProtocol.MiLiveConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(241);
        if (this.mLiveConfigChanges == null) {
            getActivity().getImplFactory().initModulePersistent(getActivity(), 241);
            this.mLiveConfigChanges = (ModeProtocol.MiLiveConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(241);
            this.mLiveConfigChanges.prepare();
        }
        this.mLiveConfigChanges.initResource();
        this.mLiveConfigChanges.setRecorderListener(this.mRecorderListener);
    }

    private void initMetaParser() {
        this.mMetaDataDisposable = Flowable.create(new B(this), BackpressureStrategy.DROP).map(new FunctionParseAsdFace(this, isFrontCamera())).map(new FunctionParseAsdScene(this)).subscribe(new LiveAsdConsumer());
    }

    /* access modifiers changed from: private */
    public void initReview(List<ILive.ILiveSegmentData> list, String str) {
        ContentValues genContentValues = genContentValues(2, 0, false);
        ModeProtocol.MiLivePlayerControl miLivePlayerControl = (ModeProtocol.MiLivePlayerControl) ModeCoordinatorImpl.getInstance().getAttachProtocol(242);
        if (miLivePlayerControl != null) {
            miLivePlayerControl.prepare(genContentValues, list, str);
        } else {
            Log.d(this.TAG, "show review fail~");
            resetToIdle();
        }
        this.mIsPreviewing = true;
    }

    private void initializeFocusManager() {
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

    private boolean isEisOn() {
        return isBackCamera() && CameraSettings.isMovieSolidOn() && ((BaseModule) this).mCameraCapabilities.isEISPreviewSupported();
    }

    private boolean isSaving() {
        return false;
    }

    public static void loadLibs(Context context, int i) {
        if (sLibLoaded == 0) {
            System.loadLibrary("vvc++_shared");
            System.loadLibrary("ffmpeg");
            System.loadLibrary("record_video");
            SystemUtil.Init(context, 50011);
        }
        sLibLoaded |= i;
        Log.d("MiLiveModule", "loadLibs sLibLoaded : " + sLibLoaded);
    }

    private void pauseVideoRecording(boolean z) {
        if (isAlive() && this.mLiveConfigChanges != null) {
            String str = this.TAG;
            Log.d(str, "pauseVideoRecording formRelease " + z);
            if (this.mLiveConfigChanges.canRecordingStop() || z) {
                CameraStatUtils.trackMiLiveClick(MistatsConstants.MiLive.VALUE_MI_LIVE_CLICK_PAUSE);
                ModeProtocol.RecordState recordState = (ModeProtocol.RecordState) ModeCoordinatorImpl.getInstance().getAttachProtocol(212);
                if (recordState != null) {
                    recordState.onPause();
                } else {
                    Log.d(this.TAG, "recordState pause fail~");
                }
                this.mLiveConfigChanges.pauseRecording();
                if (HybridZoomingSystem.IS_3_OR_MORE_SAT && isBackCamera()) {
                    updateZoomRatioToggleButtonState(false);
                    setMinZoomRatio(0.6f);
                    setMaxZoomRatio(((BaseModule) this).mCameraCapabilities.getMaxZoomRatio() * 0.6f);
                    return;
                }
                return;
            }
            Log.d(this.TAG, "too fast to pause recording.");
        }
    }

    private void previewWhenSessionSuccess() {
        setCameraState(1);
        updatePreferenceInWorkThread(UpdateConstant.FUN_TYPES_ON_PREVIEW_SUCCESS);
        updatePreferenceInWorkThread(71);
    }

    /* access modifiers changed from: private */
    public void resetToIdle() {
        Log.d(this.TAG, "resetToIdle");
        configReview(false);
        DataRepository.dataItemLive().setMiLiveSegmentData(null);
        ModeProtocol.RecordState recordState = (ModeProtocol.RecordState) ModeCoordinatorImpl.getInstance().getAttachProtocol(212);
        if (recordState != null) {
            recordState.onFinish();
        }
        getActivity().setVolumeControlStream(this.mOldOriginVolumeStream);
        this.mIsPreviewing = false;
        if (HybridZoomingSystem.IS_3_OR_MORE_SAT && isBackCamera()) {
            updateZoomRatioToggleButtonState(false);
            if (isUltraWideBackCamera()) {
                setMinZoomRatio(0.6f);
                setMaxZoomRatio(((BaseModule) this).mCameraCapabilities.getMaxZoomRatio() * 0.6f);
            } else {
                setMinZoomRatio(0.6f);
                setMaxZoomRatio(Math.min(6.0f, ((BaseModule) this).mCameraCapabilities.getMaxZoomRatio()));
            }
        }
        if (doLaterReleaseIfNeed()) {
            Log.d(this.TAG, "onReviewDoneClicked -- ");
        } else if (((BaseModule) this).mCamera2Device.isSessionReady()) {
            resumePreview();
        } else {
            startPreview();
        }
    }

    private void resetZoom(boolean z) {
        if (HybridZoomingSystem.IS_3_OR_MORE_SAT && isBackCamera()) {
            updateZoomRatioToggleButtonState(z);
            if (isUltraWideBackCamera()) {
                setMinZoomRatio(0.6f);
                setMaxZoomRatio(2.0f);
            } else if (isAuxCamera()) {
                setMinZoomRatio(HybridZoomingSystem.FLOAT_ZOOM_RATIO_TELE);
                setMaxZoomRatio(Math.min(6.0f, ((BaseModule) this).mCameraCapabilities.getMaxZoomRatio()));
            } else {
                setMinZoomRatio(1.0f);
                setMaxZoomRatio(Math.min(6.0f, ((BaseModule) this).mCameraCapabilities.getMaxZoomRatio()));
            }
        }
    }

    private void resumeVideoRecording() {
        if (isAlive() && this.mLiveConfigChanges != null) {
            resetZoom(true);
            this.mLiveConfigChanges.resumeRecording();
            ModeProtocol.RecordState recordState = (ModeProtocol.RecordState) ModeCoordinatorImpl.getInstance().getAttachProtocol(212);
            if (recordState != null) {
                recordState.onResume();
            } else {
                Log.d(this.TAG, "recordState resume fail~");
            }
        }
    }

    private void setOrientation(int i, int i2) {
        if (i != -1) {
            ((BaseModule) this).mOrientation = i;
            checkActivityOrientation();
            if (((BaseModule) this).mOrientationCompensation != i2) {
                ((BaseModule) this).mOrientationCompensation = i2;
                setOrientationParameter();
            }
        }
    }

    /* access modifiers changed from: private */
    public void setOrientationParameter() {
        if (!isDeparted() && ((BaseModule) this).mCamera2Device != null && ((BaseModule) this).mOrientation != -1) {
            if (!isFrameAvailable() || getCameraState() != 1) {
                GlobalConstant.sCameraSetupScheduler.scheduleDirect(new A(this));
                return;
            }
            updatePreferenceInWorkThread(35);
        }
    }

    private boolean shouldApplyUltraWideLDC() {
        if (CameraSettings.shouldUltraWideVideoLDCBeVisibleInMode(((BaseModule) this).mModuleIndex) && ((BaseModule) this).mActualCameraId == Camera2DataContainer.getInstance().getUltraWideCameraId()) {
            return CameraSettings.isUltraWideVideoLDCEnabled();
        }
        return false;
    }

    private void showReview() {
        ModeProtocol.MiLivePlayerControl miLivePlayerControl = (ModeProtocol.MiLivePlayerControl) ModeCoordinatorImpl.getInstance().getAttachProtocol(242);
        if (miLivePlayerControl != null) {
            miLivePlayerControl.show();
            pausePreview();
            return;
        }
        Log.d(this.TAG, "show review fail~");
        resetToIdle();
    }

    @MainThread
    private void startVideoRecording() {
        keepScreenOn();
        if (this.mLiveConfigChanges != null) {
            ModeProtocol.RecordState recordState = (ModeProtocol.RecordState) ModeCoordinatorImpl.getInstance().getAttachProtocol(212);
            recordState.onPrepare();
            this.mLiveConfigChanges.startRecording();
            CameraStatUtils.trackMiLiveClick(MistatsConstants.MiLive.VALUE_MI_LIVE_CLICK_START);
            this.mOldOriginVolumeStream = getActivity().getVolumeControlStream();
            getActivity().setVolumeControlStream(3);
            recordState.onStart();
            configReview(true);
            resetZoom(true);
            ((BaseModule) this).mPreZoomRation = getZoomRatio();
            this.mTelephonyManager.listen(((BaseModule) this).mPhoneStateListener, 32);
            Log.v(this.TAG, "listen call state");
        }
    }

    /* access modifiers changed from: private */
    public void trackLiveVideoParams() {
        int i;
        int i2;
        int i3;
        boolean z;
        ModeProtocol.MiLiveConfigChanges miLiveConfigChanges = this.mLiveConfigChanges;
        int segmentSize = miLiveConfigChanges != null ? miLiveConfigChanges.getSegmentSize() : 0;
        String str = CameraSettings.getCurrentLiveMusic()[1];
        int shaderEffect = CameraSettings.getShaderEffect();
        int intValue = Integer.valueOf(CameraSettings.getCurrentLiveSpeed()).intValue();
        int preferVideoQuality = CameraSettings.getPreferVideoQuality(((BaseModule) this).mActualCameraId, ((BaseModule) this).mModuleIndex);
        if (CameraSettings.isMiLiveBeautyOpen()) {
            int faceBeautyRatio = CameraSettings.getFaceBeautyRatio("key_live_shrink_face_ratio");
            int faceBeautyRatio2 = CameraSettings.getFaceBeautyRatio("key_live_enlarge_eye_ratio");
            int faceBeautyRatio3 = CameraSettings.getFaceBeautyRatio("key_live_smooth_strength");
            if (faceBeautyRatio > 0 || faceBeautyRatio2 > 0 || faceBeautyRatio3 > 0) {
                i2 = faceBeautyRatio2;
                i = faceBeautyRatio3;
                z = true;
            } else {
                i2 = faceBeautyRatio2;
                i = faceBeautyRatio3;
                z = false;
            }
            i3 = faceBeautyRatio;
        } else {
            z = false;
            i3 = 0;
            i2 = 0;
            i = 0;
        }
        CameraStatUtils.trackMiLiveRecordingParams(segmentSize, str, shaderEffect, intValue, isFrontCamera(), z, i3, i2, i, preferVideoQuality, EffectController.getInstance().getCurrentKaleidoscope());
    }

    public static void unloadLibs(int i) {
        sLibLoaded = (~i) & sLibLoaded;
        Log.d("MiLiveModule", "unloadLibs sLibLoaded : " + sLibLoaded);
        if (sLibLoaded == 0) {
            SystemUtil.UnInit();
        }
    }

    private void updateBeauty() {
        if (CameraSettings.isMiLiveBeautyOpen()) {
            if (this.mBeautyValues == null) {
                this.mBeautyValues = new BeautyValues();
            }
            CameraSettings.initBeautyValues(this.mBeautyValues, ((BaseModule) this).mModuleIndex);
            this.mBeautyValues.mBeautySlimFace = CameraSettings.getFaceBeautyRatio("key_live_shrink_face_ratio");
            this.mBeautyValues.mBeautyEnlargeEye = CameraSettings.getFaceBeautyRatio("key_live_enlarge_eye_ratio");
            this.mBeautyValues.mBeautySkinSmooth = CameraSettings.getFaceBeautyRatio("key_live_smooth_strength");
            String str = this.TAG;
            Log.d(str, "updateBeauty(): " + this.mBeautyValues);
            ((BaseModule) this).mCamera2Device.setBeautyValues(this.mBeautyValues);
        }
    }

    private void updateDeviceOrientation() {
        ((BaseModule) this).mCamera2Device.setDeviceOrientation(((BaseModule) this).mOrientation);
    }

    private void updateFaceView(boolean z, boolean z2) {
        if (((BaseModule) this).mHandler.hasMessages(35)) {
            ((BaseModule) this).mHandler.removeMessages(35);
        }
        ((BaseModule) this).mHandler.obtainMessage(35, z ? 1 : 0, z2 ? 1 : 0).sendToTarget();
    }

    private void updateFilter() {
        int shaderEffect = CameraSettings.getShaderEffect();
        String str = this.TAG;
        Log.v(str, "updateFilter: 0x" + Integer.toHexString(shaderEffect));
        EffectController.getInstance().setEffect(shaderEffect);
    }

    private void updateFocusMode() {
        setFocusMode(this.mFocusManager.setFocusMode(CameraSettings.getFocusMode()));
    }

    private void updateFpsRange() {
        ((BaseModule) this).mCamera2Device.setFpsRange(new Range<>(30, 30));
    }

    private void updateKaleidoscope() {
        String kaleidoscopeValue = DataRepository.dataItemRunning().getComponentRunningKaleidoscope().getKaleidoscopeValue();
        V6CameraGLSurfaceView v6CameraGLSurfaceView = this.mCameraView;
        if (v6CameraGLSurfaceView != null) {
            GLCanvasImpl gLCanvas = v6CameraGLSurfaceView.getGLCanvas();
            if (gLCanvas instanceof GLCanvasImpl) {
                gLCanvas.setKaleidoscope(kaleidoscopeValue);
            }
        }
        EffectController.getInstance().setKaleidoscope(kaleidoscopeValue);
    }

    private void updateLiveRelated() {
        if (((BaseModule) this).mAlgorithmPreviewSize != null) {
            ((BaseModule) this).mCamera2Device.startPreviewCallback(this.mLiveConfigChanges);
        }
    }

    private void updatePictureAndPreviewSize() {
        List<CameraSize> supportedOutputSizeWithAssignedMode = ((BaseModule) this).mCameraCapabilities.getSupportedOutputSizeWithAssignedMode(SurfaceTexture.class);
        float previewRatio = this.mLiveConfigChanges.getPreviewRatio();
        int preferVideoQuality = CameraSettings.getPreferVideoQuality(((BaseModule) this).mActualCameraId, ((BaseModule) this).mModuleIndex);
        this.mVideoSize = null;
        if (preferVideoQuality != 5) {
            this.mVideoSize = new CameraSize(1920, 1080);
        } else {
            this.mVideoSize = new CameraSize(1280, Util.LIMIT_SURFACE_WIDTH);
        }
        ((BaseModule) this).mPreviewSize = Util.getOptimalPreviewSize(false, ((BaseModule) this).mBogusCameraId, supportedOutputSizeWithAssignedMode, (double) previewRatio, this.mVideoSize);
        String str = this.TAG;
        Log.d(str, "previewSize: " + ((BaseModule) this).mPreviewSize.toString());
        CameraSize cameraSize = ((BaseModule) this).mPreviewSize;
        ((BaseModule) this).mPictureSize = cameraSize;
        updateCameraScreenNailSize(cameraSize.width, cameraSize.height);
    }

    private void updateUltraWideLDC() {
        ((BaseModule) this).mCamera2Device.setUltraWideLDC(shouldApplyUltraWideLDC());
    }

    private void updateVideoStabilization() {
        if (isDeviceAlive()) {
            if (isEisOn()) {
                Log.d(this.TAG, "videoStabilization: EIS");
                ((BaseModule) this).mCamera2Device.setEnableEIS(true);
                ((BaseModule) this).mCamera2Device.setEnableOIS(false);
                if (!((BaseModule) this).mCameraCapabilities.isEISPreviewSupported()) {
                    ((BaseModule) this).mActivity.getCameraScreenNail().setVideoStabilizationCropped(true);
                    return;
                }
                return;
            }
            Log.d(this.TAG, "videoStabilization: OIS");
            ((BaseModule) this).mCamera2Device.setEnableEIS(false);
            ((BaseModule) this).mCamera2Device.setEnableOIS(true);
            ((BaseModule) this).mActivity.getCameraScreenNail().setVideoStabilizationCropped(false);
        }
    }

    public /* synthetic */ void Tf() {
        ((BaseModule) this).mCamera2Device.setDeviceOrientation(((BaseModule) this).mOrientation);
    }

    public /* synthetic */ void a(FlowableEmitter flowableEmitter) throws Exception {
        this.mMetaDataFlowableEmitter = flowableEmitter;
    }

    public /* synthetic */ void a(String str, Uri uri) {
        ContentValues saveContentValues;
        ModeProtocol.MiLivePlayerControl miLivePlayerControl = (ModeProtocol.MiLivePlayerControl) ModeCoordinatorImpl.getInstance().getAttachProtocol(242);
        if (miLivePlayerControl != null && (saveContentValues = miLivePlayerControl.getSaveContentValues()) != null) {
            String asString = saveContentValues.getAsString("title");
            String str2 = this.TAG;
            Log.d(str2, "newUri: " + str + " | " + asString);
            if (asString.equals(str)) {
                miLivePlayerControl.onLiveSaveToLocalFinished(uri);
            }
        }
    }

    @Override // com.android.camera.module.loader.camera2.FocusManager2.Listener
    public void cancelFocus(boolean z) {
        if (isAlive() && isFrameAvailable() && getCameraState() != 0) {
            Camera2Proxy camera2Proxy = ((BaseModule) this).mCamera2Device;
            if (camera2Proxy != null) {
                if (z) {
                    camera2Proxy.setFocusMode(4);
                }
                ((BaseModule) this).mCamera2Device.cancelFocus(((BaseModule) this).mModuleIndex);
            }
            if (getCameraState() != 3) {
                setCameraState(1);
            }
        }
    }

    /* access modifiers changed from: protected */
    public boolean checkCallingState() {
        if (Storage.isLowStorageAtLastPoint()) {
            ((BaseModule) this).mActivity.getScreenHint().updateHint();
            return false;
        } else if (2 != this.mTelephonyManager.getCallState()) {
            return true;
        } else {
            showConfirmMessage(R.string.confirm_recording_fail_title, R.string.confirm_recording_fail_calling_alert);
            return false;
        }
    }

    @Override // com.android.camera.module.BaseModule
    public void checkDisplayOrientation() {
        if (isCreated()) {
            super.checkDisplayOrientation();
            ModeProtocol.MainContentProtocol mainContentProtocol = ((BaseModule) this).mMainProtocol;
            if (mainContentProtocol != null) {
                mainContentProtocol.setCameraDisplayOrientation(((BaseModule) this).mCameraDisplayOrientation);
            }
            FocusManager2 focusManager2 = this.mFocusManager;
            if (focusManager2 != null) {
                focusManager2.setDisplayOrientation(((BaseModule) this).mCameraDisplayOrientation);
            }
        }
    }

    @Override // com.android.camera.module.Module
    public void closeCamera() {
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
            camera2Proxy.releaseCameraPreviewCallback(null);
            ((BaseModule) this).mCamera2Device.setFocusCallback(null);
            ((BaseModule) this).mCamera2Device.setErrorCallback(null);
            ((BaseModule) this).mCamera2Device.setMetaDataCallback(null);
            ((BaseModule) this).mCamera2Device.stopPreviewCallback(true);
            ((BaseModule) this).mCamera2Device = null;
        }
        FocusManager2 focusManager2 = this.mFocusManager;
        if (focusManager2 != null) {
            focusManager2.setAeAwbLock(false);
            this.mFocusManager.destroy();
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.BaseModule
    public void consumePreference(int... iArr) {
        for (int i : iArr) {
            if (i == 1) {
                updatePictureAndPreviewSize();
            } else if (i == 2) {
                updateFilter();
            } else if (i == 3) {
                updateFocusArea();
            } else if (i == 5) {
                updateFace();
            } else if (i == 50) {
                continue;
            } else if (i == 58) {
                updateBackSoftLightPreference();
            } else if (i == 66) {
                updateThermalLevel();
            } else if (i == 71) {
                updateKaleidoscope();
            } else if (i == 19) {
                updateFpsRange();
            } else if (i == 20) {
                continue;
            } else if (i == 24) {
                applyZoomRatio();
            } else if (i == 25) {
                focusCenter();
            } else if (i == 34) {
                continue;
            } else if (i == 35) {
                updateDeviceOrientation();
            } else if (!(i == 42 || i == 43 || i == 46)) {
                if (i == 47) {
                    updateUltraWideLDC();
                } else if (i == 54) {
                    updateLiveRelated();
                } else if (i != 55) {
                    switch (i) {
                        case 9:
                            updateAntiBanding(CameraSettings.getAntiBanding());
                            continue;
                        case 10:
                            updateFlashPreference();
                            continue;
                        case 11:
                            continue;
                        case 12:
                            setEvValue();
                            continue;
                        case 13:
                            updateBeauty();
                            continue;
                        case 14:
                            updateFocusMode();
                            continue;
                        default:
                            switch (i) {
                                case 29:
                                    updateExposureMeteringMode();
                                    continue;
                                case 30:
                                    continue;
                                case 31:
                                    updateVideoStabilization();
                                    continue;
                                default:
                                    throw new RuntimeException("no consumer for this updateType: " + i);
                            }
                    }
                } else {
                    updateModuleRelated();
                }
            }
        }
    }

    @Override // com.android.camera.module.ILiveModule
    public void doReverse() {
        if (this.mLiveConfigChanges != null && !isRecording()) {
            this.mLiveConfigChanges.deleteLastFragment();
            if (this.mLiveConfigChanges.getSegmentSize() == 0) {
                ModeProtocol.BackStack backStack = (ModeProtocol.BackStack) ModeCoordinatorImpl.getInstance().getAttachProtocol(171);
                if (backStack != null) {
                    backStack.handleBackStackFromKeyBack();
                }
                ModeProtocol.RecordState recordState = (ModeProtocol.RecordState) ModeCoordinatorImpl.getInstance().getAttachProtocol(212);
                if (recordState != null) {
                    recordState.onFinish();
                }
                stopVideoRecording(false, false);
            }
        }
    }

    /* access modifiers changed from: protected */
    public boolean enableFaceDetection() {
        return DataRepository.dataItemGlobal().getBoolean("pref_camera_facedetection_key", getResources().getBoolean(R.bool.pref_camera_facedetection_default));
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.BaseModule
    public int getOperatingMode() {
        int i = isEisOn() ? 32772 : (!CameraSettings.isVideoBokehOn() || !isFrontCamera()) ? ((BaseModule) this).mCameraCapabilities.isSupportVideoBeauty() ? CameraCapabilities.SESSION_OPERATION_MODE_VIDEO_BEAUTY : DataRepository.dataItemFeature().Cc() ? CameraCapabilities.SESSION_OPERATION_MODE_MCTF : 0 : 32770;
        String str = this.TAG;
        Log.d(str, "getOperatingMode: " + Integer.toHexString(i));
        return i;
    }

    @Override // com.android.camera.module.BaseModule
    public void initializeCapabilities() {
        super.initializeCapabilities();
        ((BaseModule) this).mContinuousFocusSupported = Util.isSupported(4, ((BaseModule) this).mCameraCapabilities.getSupportedFocusModes());
        ((BaseModule) this).mMaxFaceCount = ((BaseModule) this).mCameraCapabilities.getMaxFaceCount();
    }

    /* access modifiers changed from: protected */
    public boolean is3ALocked() {
        return this.m3ALocked;
    }

    /* access modifiers changed from: protected */
    public boolean isAEAFLockSupported() {
        return true;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.BaseModule
    public boolean isCameraSwitchingDuringZoomingAllowed() {
        int i;
        return HybridZoomingSystem.IS_3_OR_MORE_SAT && (i = ((BaseModule) this).mModuleIndex) == 183 && !CameraSettings.isMacroModeEnabled(i) && isBackCamera() && !isRecording();
    }

    @Override // com.android.camera.module.Module, com.android.camera.protocol.ModeProtocol.CameraAction
    public boolean isDoingAction() {
        ModeProtocol.MiLivePlayerControl miLivePlayerControl = (ModeProtocol.MiLivePlayerControl) ModeCoordinatorImpl.getInstance().getAttachProtocol(242);
        return isRecording() || (miLivePlayerControl != null && miLivePlayerControl.isShowing());
    }

    @Override // com.android.camera2.Camera2Proxy.FaceDetectionCallback
    public boolean isFaceDetectStarted() {
        return this.mFaceDetectionStarted;
    }

    @Override // com.android.camera.module.BaseModule
    public boolean isNeedMute() {
        return isRecording();
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule, com.android.camera.protocol.ModeProtocol.CameraAction
    public boolean isRecording() {
        ModeProtocol.MiLiveConfigChanges miLiveConfigChanges = this.mLiveConfigChanges;
        return miLiveConfigChanges != null && miLiveConfigChanges.getCurState() == 2;
    }

    public boolean isRecordingPaused() {
        ModeProtocol.MiLiveConfigChanges miLiveConfigChanges = this.mLiveConfigChanges;
        return miLiveConfigChanges != null && miLiveConfigChanges.getCurState() == 3;
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public boolean isSelectingCapturedResult() {
        ModeProtocol.MiLivePlayerControl miLivePlayerControl = (ModeProtocol.MiLivePlayerControl) ModeCoordinatorImpl.getInstance().getAttachProtocol(242);
        if (miLivePlayerControl != null) {
            return miLivePlayerControl.isShowing();
        }
        return false;
    }

    @Override // com.android.camera.module.BaseModule, com.android.camera.ui.FocusView.ExposureViewListener
    public boolean isShowCaptureButton() {
        return isSupportFocusShoot();
    }

    @Override // com.android.camera.module.BaseModule, com.android.camera.ui.FocusView.ExposureViewListener
    public boolean isSupportFocusShoot() {
        return false;
    }

    @Override // com.android.camera.module.Module
    public boolean isUnInterruptable() {
        return false;
    }

    @Override // com.android.camera2.Camera2Proxy.FaceDetectionCallback
    public boolean isUseFaceInfo() {
        return false;
    }

    @Override // com.android.camera.module.BaseModule
    public boolean isZoomEnabled() {
        boolean z = getCameraState() != 3 && !isFrontCamera() && !DataRepository.dataItemLive().getMimojiStatusManager().IsInMimojiCreate() && isFrameAvailable();
        ModeProtocol.DualController dualController = (ModeProtocol.DualController) ModeCoordinatorImpl.getInstance().getAttachProtocol(182);
        if (!z || dualController == null || !dualController.isZoomVisible() || !dualController.isSlideVisible()) {
            return z;
        }
        return false;
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
        Log.d(this.TAG, "lockAEAF");
        if (((BaseModule) this).mAeLockSupported) {
            ((BaseModule) this).mCamera2Device.setAELock(true);
        }
        this.mFocusManager.setAeAwbLock(true);
        this.m3ALocked = true;
    }

    @Override // com.android.camera.module.loader.camera2.FocusManager2.Listener
    public boolean multiCapture() {
        return false;
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void notifyError() {
        if (currentIsMainThread() && isRecording()) {
            stopVideoRecording(false, false);
        }
        super.notifyError();
    }

    @Override // com.android.camera.module.loader.camera2.FocusManager2.Listener
    public void notifyFocusAreaUpdate() {
        updatePreferenceTrampoline(3);
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public boolean onBackPressed() {
        if (!isFrameAvailable()) {
            return isSelectingCapturedResult();
        }
        if (this.mLiveConfigChanges == null) {
            return false;
        }
        if (!isRecording() && !isRecordingPaused()) {
            return super.onBackPressed();
        }
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis - ((BaseModule) this).mLastBackPressedTime > 3000) {
            ((BaseModule) this).mLastBackPressedTime = currentTimeMillis;
            ToastUtils.showToast((Context) ((BaseModule) this).mActivity, (int) R.string.record_back_pressed_hint, true);
        } else {
            stopVideoRecording(true, true);
        }
        return true;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.BaseModule
    public void onCameraOpened() {
        super.onCameraOpened();
        initLiveConfig();
        initializeFocusManager();
        updatePreferenceTrampoline(UpdateConstant.FUN_TYPES_INIT);
        startPreview();
        if (DataRepository.dataItemGlobal().isFirstShowCTAConCollect()) {
            this.mCtaNoticeFragment = CtaNoticeFragment.showCta(getActivity().getFragmentManager(), false, null, 1);
        }
        this.mOnResumeTime = SystemClock.uptimeMillis();
        ((BaseModule) this).mHandler.sendEmptyMessage(4);
        ((BaseModule) this).mHandler.sendEmptyMessage(31);
        initMetaParser();
    }

    @Override // com.android.camera2.Camera2Proxy.PictureCallback
    public void onCaptureCompleted(boolean z) {
    }

    @Override // com.android.camera2.Camera2Proxy.PictureCallback
    public void onCaptureShutter(boolean z) {
        ((BaseModule) this).mActivity.getCameraScreenNail().requestFullReadPixels();
        CameraStatUtils.trackKaleidoscopeClick(MistatsConstants.MiLive.VALUE_MI_LIVE_CLICK_KALEIDOSCOPE_CAPTURE);
    }

    @Override // com.android.camera2.Camera2Proxy.PictureCallback
    public ParallelTaskData onCaptureStart(ParallelTaskData parallelTaskData, CameraSize cameraSize, boolean z) {
        return null;
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void onCreate(int i, int i2) {
        super.onCreate(i, i2);
        ((BaseModule) this).mActivity.getSensorStateManager().setSensorStateListener(this.mSensorStateListener);
        ((BaseModule) this).mHandler = new MainHandler(((BaseModule) this).mActivity.getMainLooper());
        this.mTelephonyManager = (TelephonyManager) ((BaseModule) this).mActivity.getSystemService("phone");
        onCameraOpened();
        ((BaseModule) this).mHandler.sendEmptyMessage(4);
        ((BaseModule) this).mHandler.sendEmptyMessage(31);
        ((BaseModule) this).mActivity.getSensorStateManager().setRotationVectorEnabled(true);
        this.mCameraView = ((BaseModule) this).mActivity.getGLView();
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void onDestroy() {
        super.onDestroy();
        Log.d(this.TAG, "onDestroy");
        CtaNoticeFragment ctaNoticeFragment = this.mCtaNoticeFragment;
        if (ctaNoticeFragment != null) {
            ctaNoticeFragment.dismiss();
        }
        ((BaseModule) this).mHandler.post(z.INSTANCE);
        Handler handler = ((BaseModule) this).mHandler;
        if (handler != null) {
            handler.sendEmptyMessage(45);
        }
        ((BaseModule) this).mActivity.getSensorStateManager().setRotationVectorEnabled(false);
        ((BaseModule) this).mActivity.getSensorStateManager().setTTARSensorEnabled(false);
    }

    @Override // com.android.camera2.Camera2Proxy.FaceDetectionCallback
    public void onFaceDetected(CameraHardwareFace[] cameraHardwareFaceArr, FaceAnalyzeInfo faceAnalyzeInfo) {
        if (!isCreated() || cameraHardwareFaceArr == null) {
            return;
        }
        if (!b.cm() || cameraHardwareFaceArr.length <= 0 || cameraHardwareFaceArr[0].faceType != 64206) {
            if (!((BaseModule) this).mMainProtocol.setFaces(1, cameraHardwareFaceArr, getActiveArraySize(), getDeviceBasedZoomRatio())) {
            }
        } else if (((BaseModule) this).mObjectTrackingStarted) {
            ((BaseModule) this).mMainProtocol.setFaces(3, cameraHardwareFaceArr, getActiveArraySize(), getDeviceBasedZoomRatio());
        }
    }

    @Override // com.android.camera2.Camera2Proxy.FocusCallback
    public void onFocusStateChanged(FocusTask focusTask) {
        if (isCreated() && !isDeparted()) {
            int focusTrigger = focusTask.getFocusTrigger();
            if (focusTrigger == 1) {
                Log.v(this.TAG, String.format(Locale.ENGLISH, "FocusTime=%1$dms focused=%2$b", Long.valueOf(focusTask.getElapsedTime()), Boolean.valueOf(focusTask.isSuccess())));
                if (!this.mFocusManager.isFocusingSnapOnFinish() && getCameraState() != 3) {
                    setCameraState(1);
                }
                this.mFocusManager.onFocusResult(focusTask);
                ((BaseModule) this).mActivity.getSensorStateManager().reset();
                if (focusTask.isSuccess() && this.m3ALocked) {
                    ((BaseModule) this).mCamera2Device.lockExposure(true);
                }
            } else if (focusTrigger == 2 || focusTrigger == 3) {
                String str = null;
                if (focusTask.isFocusing()) {
                    str = "onAutoFocusMoving start";
                }
                if (Util.sIsDumpLog && str != null) {
                    Log.v(this.TAG, str);
                }
                if ((getCameraState() != 3 || focusTask.getFocusTrigger() == 3) && !this.m3ALocked) {
                    this.mFocusManager.onFocusResult(focusTask);
                }
            }
        }
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void onHostStopAndNotifyActionStop() {
        if (isRecording()) {
            pauseVideoRecording(true);
        } else if (isSaving()) {
            return;
        }
        doLaterReleaseIfNeed();
    }

    @Override // com.android.camera.protocol.ModeProtocol.KaleidoscopeProtocol
    public void onKaleidoscopeChanged(String str) {
        updatePreferenceTrampoline(71);
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
                    if (this.mIsPreviewing) {
                        ((ModeProtocol.MiLivePlayerControl) ModeCoordinatorImpl.getInstance().getAttachProtocol(242)).startLiveRecordSaving();
                    } else if (!Util.isFingerPrintKeyEvent(keyEvent)) {
                        performKeyClicked(40, getString(R.string.pref_camera_volumekey_function_entryvalue_shutter), keyEvent.getRepeatCount(), true);
                    } else if (CameraSettings.isFingerprintCaptureEnable()) {
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
                        stopVideoRecording(true, true);
                    }
                } else if (isRecording() && !isPostProcessing()) {
                    if (!isBackCamera()) {
                        return false;
                    }
                    stopVideoRecording(true, true);
                }
                return super.onKeyDown(i, keyEvent);
            }
        }
        if (!this.mIsPreviewing) {
            if (i == 24 || i == 88) {
                z = true;
            }
            if (handleVolumeKeyEvent(z, true, keyEvent.getRepeatCount(), keyEvent.getDevice().isExternal())) {
                return true;
            }
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
    public void onNewUriArrived(Uri uri, String str) {
        super.onNewUriArrived(uri, str);
        if (isAlive()) {
            ((BaseModule) this).mHandler.post(new C(this, str, uri));
        }
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void onOrientationChanged(int i, int i2, int i3) {
        setOrientation(i, i2);
        ModeProtocol.MiLiveConfigChanges miLiveConfigChanges = this.mLiveConfigChanges;
        if (miLiveConfigChanges != null) {
            miLiveConfigChanges.onOrientationChanged(i, i2, i3);
        }
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void onPause() {
        super.onPause();
        Log.d(this.TAG, "onPause");
        FocusManager2 focusManager2 = this.mFocusManager;
        if (focusManager2 != null) {
            focusManager2.removeMessages();
        }
        ((BaseModule) this).mActivity.getSensorStateManager().reset();
        resetScreenOn();
        closeCamera();
        Handler handler = ((BaseModule) this).mHandler;
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }

    @Override // com.android.camera.module.ILiveModule
    public void onPauseButtonClick() {
        if (isRecording()) {
            pauseVideoRecording(false);
        } else {
            resumeVideoRecording();
        }
    }

    @Override // com.android.camera2.Camera2Proxy.PictureCallback
    public void onPictureTaken(byte[] bArr, CaptureResult captureResult) {
    }

    @Override // com.android.camera2.Camera2Proxy.PictureCallback
    public void onPictureTakenFinished(boolean z) {
        ((BaseModule) this).mActivity.getCameraScreenNail().animateCapture(((BaseModule) this).mOrientation);
        ((BaseModule) this).mActivity.getCameraScreenNail().setPreviewSaveListener(null);
        setCameraState(1);
    }

    @Override // com.android.camera2.Camera2Proxy.PictureCallback
    public boolean onPictureTakenImageConsumed(Image image) {
        return false;
    }

    @Override // com.android.camera.module.BaseModule
    public void onPreviewLayoutChanged(Rect rect) {
        ((BaseModule) this).mActivity.onLayoutChange(rect);
        if (this.mFocusManager != null && ((BaseModule) this).mActivity.getCameraScreenNail() != null) {
            this.mFocusManager.setRenderSize(((BaseModule) this).mActivity.getCameraScreenNail().getRenderWidth(), ((BaseModule) this).mActivity.getCameraScreenNail().getRenderHeight());
            this.mFocusManager.setPreviewSize(rect.width(), rect.height());
        }
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
    }

    @Override // com.android.camera2.Camera2Proxy.CameraPreviewCallback
    public void onPreviewSessionFailed(CameraCaptureSession cameraCaptureSession) {
        if (!isTextureExpired() || !retryOnceIfCameraError(((BaseModule) this).mHandler)) {
            ((BaseModule) this).mHandler.sendEmptyMessage(51);
        } else {
            Log.d(this.TAG, "sessionFailed due to surfaceTexture expired, retry");
        }
    }

    @Override // com.android.camera2.Camera2Proxy.CameraPreviewCallback
    public void onPreviewSessionSuccess(CameraCaptureSession cameraCaptureSession) {
        if (cameraCaptureSession != null && isAlive()) {
            ((BaseModule) this).mHandler.sendEmptyMessage(9);
            previewWhenSessionSuccess();
        }
    }

    @Override // com.android.camera.module.BaseModule
    public void onPreviewSizeChanged(int i, int i2) {
        FocusManager2 focusManager2 = this.mFocusManager;
        if (focusManager2 != null) {
            focusManager2.setPreviewSize(i, i2);
        }
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void onResume() {
        super.onResume();
        keepScreenOnAwhile();
        Log.d(this.TAG, "onResume");
    }

    @Override // com.android.camera.protocol.ModeProtocol.CameraAction
    public void onReviewCancelClicked() {
        Log.d(this.TAG, "onReviewCancelClicked");
        resetToIdle();
    }

    @Override // com.android.camera.protocol.ModeProtocol.CameraAction
    public void onReviewDoneClicked() {
        Log.d(this.TAG, "onReviewDoneClicked");
        ModeProtocol.MiLiveConfigChanges miLiveConfigChanges = this.mLiveConfigChanges;
        if (miLiveConfigChanges != null) {
            miLiveConfigChanges.stopRecording();
        }
        resetToIdle();
    }

    @Override // com.android.camera.module.BaseModule
    public void onShineChanged(int i) {
        if (i == 196) {
            updatePreferenceInWorkThread(2);
        } else if (i == 239) {
            updatePreferenceInWorkThread(13);
        } else {
            throw new RuntimeException("unknown configItem changed");
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.CameraAction
    public void onShutterButtonClick(int i) {
        if (getCameraState() == 0) {
            Log.d(this.TAG, "skip shutter caz preview paused.");
            return;
        }
        int i2 = 0;
        ModeProtocol.MiLiveConfigChanges miLiveConfigChanges = this.mLiveConfigChanges;
        if (miLiveConfigChanges != null) {
            i2 = miLiveConfigChanges.getCurState();
        }
        String str = this.TAG;
        Log.d(str, "onShutterButtonClick " + i2);
        if (i2 != 1) {
            if (i2 == 2 || i2 == 3) {
                stopVideoRecording(true, true);
            }
        } else if (!checkCallingState()) {
            Log.d(this.TAG, "ignore in calling state");
            ModeProtocol.RecordState recordState = (ModeProtocol.RecordState) ModeCoordinatorImpl.getInstance().getAttachProtocol(212);
            recordState.onPrepare();
            recordState.onFailed();
        } else {
            startVideoRecording();
            ModeProtocol.BackStack backStack = (ModeProtocol.BackStack) ModeCoordinatorImpl.getInstance().getAttachProtocol(171);
            if (backStack != null) {
                backStack.handleBackStackFromShutter();
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.CameraAction
    public void onShutterButtonFocus(boolean z, int i) {
    }

    @Override // com.android.camera.protocol.ModeProtocol.CameraAction
    public boolean onShutterButtonLongClick() {
        return false;
    }

    @Override // com.android.camera.protocol.ModeProtocol.CameraAction
    public void onShutterButtonLongClickCancel(boolean z) {
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void onSingleTapUp(int i, int i2, boolean z) {
        Camera2Proxy camera2Proxy;
        if (!((BaseModule) this).mPaused && (camera2Proxy = ((BaseModule) this).mCamera2Device) != null && camera2Proxy.isSessionReady() && isInTapableRect(i, i2) && getCameraState() != 3 && getCameraState() != 0) {
            if (!isFrameAvailable()) {
                Log.w(this.TAG, "onSingleTapUp: frame not available");
            } else if ((!isFrontCamera() || !((BaseModule) this).mActivity.isScreenSlideOff()) && !((ModeProtocol.BackStack) ModeCoordinatorImpl.getInstance().getAttachProtocol(171)).handleBackStackFromTapDown(i, i2)) {
                ((BaseModule) this).mMainProtocol.setFocusViewType(true);
                this.mTouchFocusStartingTime = System.currentTimeMillis();
                Point point = new Point(i, i2);
                mapTapCoordinate(point);
                unlockAEAF();
                this.mFocusManager.onSingleTapUp(point.x, point.y, z);
            }
        }
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void onStop() {
        super.onStop();
        Handler handler = ((BaseModule) this).mHandler;
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void onSurfaceTextureReleased() {
        ModeProtocol.MiLiveConfigChanges miLiveConfigChanges = this.mLiveConfigChanges;
        if (miLiveConfigChanges != null) {
            miLiveConfigChanges.onSurfaceTextureReleased();
        }
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void onSurfaceTextureUpdated(DrawExtTexAttribute drawExtTexAttribute) {
        ModeProtocol.MiLiveConfigChanges miLiveConfigChanges = this.mLiveConfigChanges;
        if (miLiveConfigChanges != null) {
            miLiveConfigChanges.onSurfaceTextureUpdated(drawExtTexAttribute);
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.BaseModule
    public void onThermalConstrained() {
        super.onThermalConstrained();
        onReviewCancelClicked();
    }

    @Override // com.android.camera.protocol.ModeProtocol.CameraAction
    public void onThumbnailClicked(View view) {
        if (!isDoingAction() && ((BaseModule) this).mActivity.getThumbnailUpdater().getThumbnail() != null) {
            ((BaseModule) this).mActivity.gotoGallery();
        }
    }

    @Override // com.android.camera.module.loader.camera2.FocusManager2.Listener
    public boolean onWaitingFocusFinished() {
        return !isBlockSnap() && isAlive();
    }

    @Override // com.android.camera.module.BaseModule
    public void onZoomingActionEnd(int i) {
        ModeProtocol.DualController dualController = (ModeProtocol.DualController) ModeCoordinatorImpl.getInstance().getAttachProtocol(182);
        if (dualController != null && !dualController.isSlideVisible()) {
            if ((i == 2 || i == 1) && !isRecording() && !isRecordingPaused() && !CameraSettings.isMacroModeEnabled(((BaseModule) this).mModuleIndex)) {
                dualController.setImmersiveModeEnabled(false);
            }
        }
    }

    @Override // com.android.camera.module.BaseModule
    public void onZoomingActionStart(int i) {
        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        if (topAlert != null && topAlert.isExtraMenuShowing()) {
            topAlert.hideExtraMenu();
        }
        ModeProtocol.DualController dualController = (ModeProtocol.DualController) ModeCoordinatorImpl.getInstance().getAttachProtocol(182);
        if (dualController == null) {
            return;
        }
        if (i == 2 || i == 1) {
            dualController.setImmersiveModeEnabled(true);
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

    @Override // com.android.camera.module.Module
    public void pausePreview() {
        if (((BaseModule) this).mCamera2Device.getFlashMode() == 2 || ((BaseModule) this).mCamera2Device.getFlashMode() == 5) {
            ((BaseModule) this).mCamera2Device.forceTurnFlashOffAndPausePreview();
        } else {
            ((BaseModule) this).mCamera2Device.pausePreview();
        }
        setCameraState(0);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.BaseModule
    public void performKeyClicked(int i, String str, int i2, boolean z) {
        if (i2 == 0 && z) {
            if (isIgnoreTouchEvent()) {
                Log.w(this.TAG, "ignore volume key");
            } else {
                onShutterButtonClick(i);
            }
        }
    }

    @Override // com.android.camera.module.loader.camera2.FocusManager2.Listener
    public void playFocusSound(int i) {
        playCameraSound(i);
    }

    @Override // com.android.camera.protocol.ModeProtocol.BaseProtocol, com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void registerProtocol() {
        super.registerProtocol();
        Log.d(this.TAG, "registerProtocol");
        ModeCoordinatorImpl.getInstance().attachProtocol(161, this);
        ModeCoordinatorImpl.getInstance().attachProtocol(169, this);
        ModeCoordinatorImpl.getInstance().attachProtocol(236, this);
        if (HybridZoomingSystem.IS_3_OR_MORE_SAT) {
            getActivity().getImplFactory().initAdditional(getActivity(), 164, 174, 234, 212);
        } else {
            getActivity().getImplFactory().initAdditional(getActivity(), 164, 234, 212);
        }
    }

    /* access modifiers changed from: protected */
    public void resetScreenOn() {
        ((BaseModule) this).mHandler.removeMessages(17);
        ((BaseModule) this).mHandler.removeMessages(2);
        ((BaseModule) this).mHandler.removeMessages(52);
    }

    @Override // com.android.camera.module.Module
    public void resumePreview() {
        previewWhenSessionSuccess();
    }

    @Override // com.android.camera.module.loader.camera2.FocusManager2.Listener
    public boolean shouldCaptureDirectly() {
        return false;
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public boolean shouldReleaseLater() {
        return isRecording() || isSaving();
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

    @Override // com.android.camera.module.loader.camera2.FocusManager2.Listener
    public void startFaceDetection() {
        Camera2Proxy camera2Proxy;
        if (this.mFaceDetectionEnabled && !this.mFaceDetectionStarted && isAlive() && ((BaseModule) this).mMaxFaceCount > 0 && (camera2Proxy = ((BaseModule) this).mCamera2Device) != null) {
            this.mFaceDetectionStarted = true;
            camera2Proxy.startFaceDetection();
            updateFaceView(true, true);
        }
    }

    @Override // com.android.camera.module.loader.camera2.FocusManager2.Listener
    public void startFocus() {
        if (isDeviceAlive() && isFrameAvailable()) {
            Log.v(this.TAG, "startFocus");
            if (((BaseModule) this).mFocusOrAELockSupported) {
                ((BaseModule) this).mCamera2Device.startFocus(FocusTask.create(1), ((BaseModule) this).mModuleIndex);
            } else {
                ((BaseModule) this).mCamera2Device.resumePreview();
            }
        }
    }

    @Override // com.android.camera.module.Module
    public void startPreview() {
        if (isDeviceAlive()) {
            ((BaseModule) this).mCamera2Device.setFocusCallback(this);
            ((BaseModule) this).mCamera2Device.setMetaDataCallback(this);
            ((BaseModule) this).mCamera2Device.setErrorCallback(((BaseModule) this).mErrorCallback);
            ((BaseModule) this).mCamera2Device.setPictureSize(((BaseModule) this).mPictureSize);
            ((BaseModule) this).mCamera2Device.setPreviewSize(((BaseModule) this).mPreviewSize);
            ((BaseModule) this).mSurfaceCreatedTimestamp = ((BaseModule) this).mActivity.getCameraScreenNail().getSurfaceCreatedTimestamp();
            Log.d(this.TAG, "LiveModule, startPreview");
            checkDisplayOrientation();
            ModeProtocol.MiLiveConfigChanges miLiveConfigChanges = this.mLiveConfigChanges;
            CameraSize cameraSize = this.mVideoSize;
            miLiveConfigChanges.initPreview(cameraSize.width, cameraSize.height, ((BaseModule) this).mBogusCameraId, ((BaseModule) this).mActivity.getCameraScreenNail());
            SurfaceTexture inputSurfaceTexture = this.mLiveConfigChanges.getInputSurfaceTexture();
            String str = this.TAG;
            Log.d(str, "InputSurfaceTexture " + inputSurfaceTexture);
            Surface surface = inputSurfaceTexture == null ? new Surface(((BaseModule) this).mActivity.getCameraScreenNail().getSurfaceTexture()) : new Surface(inputSurfaceTexture);
            if (!isSelectingCapturedResult()) {
                ((BaseModule) this).mCamera2Device.startPreviewSession(surface, false, false, false, getOperatingMode(), false, this);
            }
        }
    }

    @Override // com.android.camera.module.loader.camera2.FocusManager2.Listener
    public void stopFaceDetection(boolean z) {
        if (this.mFaceDetectionEnabled && this.mFaceDetectionStarted) {
            this.mFaceDetectionStarted = false;
            Camera2Proxy camera2Proxy = ((BaseModule) this).mCamera2Device;
            if (camera2Proxy != null) {
                camera2Proxy.stopFaceDetection();
            }
            ((BaseModule) this).mMainProtocol.setActiveIndicator(2);
            updateFaceView(false, z);
        }
    }

    @Override // com.android.camera.module.loader.camera2.FocusManager2.Listener
    public void stopObjectTracking(boolean z) {
    }

    public void stopVideoRecording(boolean z, boolean z2) {
        if (isAlive() && this.mLiveConfigChanges != null) {
            String str = this.TAG;
            Log.d(str, "stopVideoRecording checkRecordingTime " + z + ", showReview = " + z2);
            keepScreenOnAwhile();
            ModeProtocol.RecordState recordState = (ModeProtocol.RecordState) ModeCoordinatorImpl.getInstance().getAttachProtocol(212);
            if (this.mLiveConfigChanges.canRecordingStop() || !z) {
                if (z2) {
                    if (recordState != null) {
                        recordState.onPostPreview();
                        showReview();
                    } else {
                        Log.d(this.TAG, "record state post preview fail~");
                    }
                }
                this.mLiveConfigChanges.stopRecording();
                return;
            }
            Log.d(this.TAG, "too fast to stop recording.");
        }
    }

    public void takePreviewSnapShoot() {
        if (getCameraState() != 3) {
            setCameraState(3);
            ((BaseModule) this).mCamera2Device.setShotType(-8);
            ((BaseModule) this).mCamera2Device.takeSimplePicture(this, ((BaseModule) this).mActivity.getImageSaver(), ((BaseModule) this).mActivity.getCameraScreenNail());
        }
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void unRegisterModulePersistProtocol() {
        super.unRegisterModulePersistProtocol();
        Log.d(this.TAG, "unRegisterModulePersistProtocol");
        getActivity().getImplFactory().detachModulePersistent();
    }

    @Override // com.android.camera.protocol.ModeProtocol.BaseProtocol, com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void unRegisterProtocol() {
        super.unRegisterProtocol();
        Log.d(this.TAG, "unRegisterProtocol");
        ModeCoordinatorImpl.getInstance().detachProtocol(161, this);
        ModeCoordinatorImpl.getInstance().detachProtocol(169, this);
        ModeCoordinatorImpl.getInstance().detachProtocol(236, this);
        getActivity().getImplFactory().detachAdditional();
    }

    /* access modifiers changed from: protected */
    public void unlockAEAF() {
        Log.d(this.TAG, "unlockAEAF");
        this.m3ALocked = false;
        if (((BaseModule) this).mAeLockSupported) {
            ((BaseModule) this).mCamera2Device.setAELock(false);
        }
        this.mFocusManager.setAeAwbLock(false);
    }

    /* access modifiers changed from: protected */
    public void updateFace() {
        boolean enableFaceDetection = enableFaceDetection();
        ModeProtocol.MainContentProtocol mainContentProtocol = ((BaseModule) this).mMainProtocol;
        if (mainContentProtocol != null) {
            mainContentProtocol.setSkipDrawFace(!enableFaceDetection || !this.mShowFace);
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
        setFlashMode(DataRepository.dataItemConfig().getComponentFlash().getComponentValue(((BaseModule) this).mModuleIndex));
    }

    /* access modifiers changed from: protected */
    public void updateFocusArea() {
        if (((BaseModule) this).mCamera2Device == null) {
            Log.e(this.TAG, "updateFocusArea: null camera device");
            return;
        }
        Rect cropRegion = getCropRegion();
        Rect activeArraySize = getActiveArraySize();
        ((BaseModule) this).mActivity.getSensorStateManager().setFocusSensorEnabled(this.mFocusManager.getMeteringAreas(cropRegion, activeArraySize) != null);
        ((BaseModule) this).mCamera2Device.setAERegions(this.mFocusManager.getMeteringAreas(cropRegion, activeArraySize));
        if (((BaseModule) this).mFocusAreaSupported) {
            ((BaseModule) this).mCamera2Device.setAFRegions(this.mFocusManager.getFocusAreas(cropRegion, activeArraySize));
        } else {
            ((BaseModule) this).mCamera2Device.resumePreview();
        }
    }
}
