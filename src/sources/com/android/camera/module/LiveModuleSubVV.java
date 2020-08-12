package com.android.camera.module;

import a.a.a;
import android.app.AlertDialog;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.content.ContentValues;
import android.content.DialogInterface;
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
import com.android.camera.Util;
import com.android.camera.constant.GlobalConstant;
import com.android.camera.constant.UpdateConstant;
import com.android.camera.data.DataRepository;
import com.android.camera.data.observeable.RxData;
import com.android.camera.data.observeable.VMProcessing;
import com.android.camera.effect.EffectController;
import com.android.camera.effect.FaceAnalyzeInfo;
import com.android.camera.fragment.CtaNoticeFragment;
import com.android.camera.fragment.beauty.BeautyValues;
import com.android.camera.log.Log;
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
import com.android.camera2.Camera2Proxy;
import com.android.camera2.CameraHardwareFace;
import com.mi.config.b;
import com.xiaomi.camera.core.ParallelTaskData;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class LiveModuleSubVV extends BaseModule implements Camera2Proxy.CameraPreviewCallback, Camera2Proxy.FocusCallback, Camera2Proxy.FaceDetectionCallback, ModeProtocol.CameraAction, Camera2Proxy.PictureCallback, FocusManager2.Listener, LifecycleOwner {
    private static final int BEAUTY_SWITCH = 8;
    private static final int FILTER_SWITCH = 2;
    private static final int FRAME_RATE = 30;
    private static final int STICKER_SWITCH = 4;
    /* access modifiers changed from: private */
    public static final String TAG = "LiveModuleSubVV";
    private boolean m3ALocked;
    private String mBaseFileName;
    protected BeautyValues mBeautyValues;
    private String mCaptureWaterMarkStr;
    private CtaNoticeFragment mCtaNoticeFragment;
    private AlertDialog mDialog;
    protected boolean mFaceDetectionEnabled;
    protected boolean mFaceDetectionStarted;
    /* access modifiers changed from: private */
    public FocusManager2 mFocusManager;
    private boolean mIsPreviewing = false;
    private LifecycleRegistry mLifecycleRegistry = new LifecycleRegistry(this);
    private ModeProtocol.LiveConfigVV mLiveConfigChanges;
    private int mMessageId;
    private Disposable mMetaDataDisposable;
    /* access modifiers changed from: private */
    public FlowableEmitter<CaptureResult> mMetaDataFlowableEmitter;
    private int mOldOriginVolumeStream;
    /* access modifiers changed from: private */
    public long mOnResumeTime;
    private int mQuality = 5;
    private boolean mSaved;
    protected SensorStateManager.SensorStateListener mSensorStateListener = new SensorStateManager.SensorStateListener() {
        /* class com.android.camera.module.LiveModuleSubVV.AnonymousClass3 */

        @Override // com.android.camera.SensorStateManager.SensorStateListener
        public boolean isWorking() {
            return LiveModuleSubVV.this.isAlive() && LiveModuleSubVV.this.getCameraState() != 0;
        }

        @Override // com.android.camera.SensorStateManager.SensorStateListener
        public void notifyDevicePostureChanged() {
            ((BaseModule) LiveModuleSubVV.this).mActivity.getEdgeShutterView().onDevicePostureChanged();
        }

        @Override // com.android.camera.SensorStateManager.SensorStateListener
        public void onDeviceBecomeStable() {
            Log.v(LiveModuleSubVV.TAG, "onDeviceBecomeStable");
        }

        @Override // com.android.camera.SensorStateManager.SensorStateListener
        public void onDeviceBeginMoving() {
        }

        @Override // com.android.camera.SensorStateManager.SensorStateListener
        public void onDeviceKeepMoving(double d2) {
            if (!((BaseModule) LiveModuleSubVV.this).mMainProtocol.isEvAdjusted(true) && !((BaseModule) LiveModuleSubVV.this).mPaused && Util.isTimeout(System.currentTimeMillis(), LiveModuleSubVV.this.mTouchFocusStartingTime, 3000) && !LiveModuleSubVV.this.is3ALocked() && LiveModuleSubVV.this.mFocusManager != null && LiveModuleSubVV.this.mFocusManager.isNeedCancelAutoFocus() && !LiveModuleSubVV.this.isRecording()) {
                LiveModuleSubVV.this.mFocusManager.onDeviceKeepMoving(d2);
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
    private VMProcessing mVmProcessing;

    private class LiveAsdConsumer implements Consumer<Integer> {
        private LiveAsdConsumer() {
        }

        public void accept(Integer num) throws Exception {
            LiveModuleSubVV.this.consumeAsdSceneResult(num.intValue());
        }
    }

    private class MainHandler extends Handler {
        public MainHandler(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message message) {
            int i = message.what;
            if (i == 2) {
                LiveModuleSubVV.this.getWindow().clearFlags(128);
            } else if (i == 4) {
                LiveModuleSubVV.this.checkActivityOrientation();
                if (SystemClock.uptimeMillis() - LiveModuleSubVV.this.mOnResumeTime < 5000) {
                    ((BaseModule) LiveModuleSubVV.this).mHandler.sendEmptyMessageDelayed(4, 100);
                }
            } else if (i == 9) {
                LiveModuleSubVV liveModuleSubVV = LiveModuleSubVV.this;
                ((BaseModule) liveModuleSubVV).mMainProtocol.initializeFocusView(liveModuleSubVV);
            } else if (i == 17) {
                ((BaseModule) LiveModuleSubVV.this).mHandler.removeMessages(17);
                ((BaseModule) LiveModuleSubVV.this).mHandler.removeMessages(2);
                LiveModuleSubVV.this.getWindow().addFlags(128);
                LiveModuleSubVV liveModuleSubVV2 = LiveModuleSubVV.this;
                ((BaseModule) liveModuleSubVV2).mHandler.sendEmptyMessageDelayed(2, (long) liveModuleSubVV2.getScreenDelay());
            } else if (i == 31) {
                LiveModuleSubVV.this.setOrientationParameter();
            } else if (i == 35) {
                LiveModuleSubVV liveModuleSubVV3 = LiveModuleSubVV.this;
                boolean z = false;
                boolean z2 = message.arg1 > 0;
                if (message.arg2 > 0) {
                    z = true;
                }
                liveModuleSubVV3.handleUpdateFaceView(z2, z);
            } else if (i == 51 && !((BaseModule) LiveModuleSubVV.this).mActivity.isActivityPaused()) {
                LiveModuleSubVV liveModuleSubVV4 = LiveModuleSubVV.this;
                ((BaseModule) liveModuleSubVV4).mOpenCameraFail = true;
                liveModuleSubVV4.onCameraException();
            }
        }
    }

    static /* synthetic */ void Rf() {
        ModeProtocol.MainContentProtocol mainContentProtocol = (ModeProtocol.MainContentProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(166);
        if (mainContentProtocol != null) {
            mainContentProtocol.setCenterHint(8, null, null, 0);
        }
    }

    /* access modifiers changed from: private */
    public void consumeAsdSceneResult(int i) {
    }

    private String createName(long j, int i) {
        if (i > 0) {
            return this.mBaseFileName;
        }
        this.mBaseFileName = new SimpleDateFormat(getString(R.string.video_file_name_format), Locale.ENGLISH).format(new Date(j));
        return this.mBaseFileName;
    }

    private void doLaterReleaseIfNeed() {
        Camera camera = ((BaseModule) this).mActivity;
        if (camera != null && camera.isActivityPaused()) {
            ((BaseModule) this).mActivity.pause();
            ((BaseModule) this).mActivity.releaseAll(true, true);
        }
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
        Log.v(TAG, "genContentValues: path=" + str);
        ContentValues contentValues = new ContentValues(8);
        contentValues.put("title", createName);
        contentValues.put("_display_name", str2);
        contentValues.put("mime_type", convertOutputFormatToMimeType);
        contentValues.put("_data", str);
        contentValues.put("resolution", Integer.toString(((BaseModule) this).mPreviewSize.width) + "x" + Integer.toString(((BaseModule) this).mPreviewSize.height));
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

    private int initLiveConfig() {
        this.mLiveConfigChanges = (ModeProtocol.LiveConfigVV) ModeCoordinatorImpl.getInstance().getAttachProtocol(228);
        if (this.mLiveConfigChanges == null) {
            getActivity().getImplFactory().initModulePersistent(getActivity(), 228);
            this.mLiveConfigChanges = (ModeProtocol.LiveConfigVV) ModeCoordinatorImpl.getInstance().getAttachProtocol(228);
            this.mLiveConfigChanges.prepare();
        }
        this.mLiveConfigChanges.initResource();
        return this.mLiveConfigChanges.getAuthResult();
    }

    private void initMetaParser() {
        this.mMetaDataDisposable = Flowable.create(new FlowableOnSubscribe<CaptureResult>() {
            /* class com.android.camera.module.LiveModuleSubVV.AnonymousClass2 */

            @Override // io.reactivex.FlowableOnSubscribe
            public void subscribe(FlowableEmitter<CaptureResult> flowableEmitter) throws Exception {
                FlowableEmitter unused = LiveModuleSubVV.this.mMetaDataFlowableEmitter = flowableEmitter;
            }
        }, BackpressureStrategy.DROP).map(new FunctionParseAsdFace(this, isFrontCamera())).map(new FunctionParseAsdScene(this)).subscribe(new LiveAsdConsumer());
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
        VMProcessing vMProcessing = this.mVmProcessing;
        return vMProcessing != null && vMProcessing.getCurrentState() == 7;
    }

    private void onProcessingSateChanged(int i) {
        if (i == 7) {
            pausePreview();
        }
    }

    private void previewWhenSessionSuccess() {
        setCameraState(1);
        updatePreferenceInWorkThread(UpdateConstant.FUN_TYPES_ON_PREVIEW_SUCCESS);
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
                GlobalConstant.sCameraSetupScheduler.scheduleDirect(new x(this));
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

    private void showAuthError() {
        ((BaseModule) this).mHandler.post(new Runnable() {
            /* class com.android.camera.module.LiveModuleSubVV.AnonymousClass1 */

            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(((BaseModule) LiveModuleSubVV.this).mActivity);
                builder.setTitle(R.string.live_error_title);
                builder.setMessage(R.string.live_error_message);
                builder.setCancelable(false);
                builder.setPositiveButton(R.string.live_error_confirm, new DialogInterface.OnClickListener() {
                    /* class com.android.camera.module.LiveModuleSubVV.AnonymousClass1.AnonymousClass1 */

                    public void onClick(DialogInterface dialogInterface, int i) {
                        ((BaseModule) LiveModuleSubVV.this).mActivity.startActivity(new Intent("android.settings.DATE_SETTINGS"));
                    }
                });
                builder.setNegativeButton(R.string.snap_cancel, new DialogInterface.OnClickListener() {
                    /* class com.android.camera.module.LiveModuleSubVV.AnonymousClass1.AnonymousClass2 */

                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                builder.show();
            }
        });
    }

    private void showPreview() {
        this.mSaved = false;
        ((ModeProtocol.LiveVVProcess) ModeCoordinatorImpl.getInstance().getAttachProtocol(230)).onCombinePrepare(genContentValues(2, 0, false));
        this.mIsPreviewing = true;
    }

    @MainThread
    private void startVideoRecording() {
        keepScreenOn();
        ModeProtocol.RecordState recordState = (ModeProtocol.RecordState) ModeCoordinatorImpl.getInstance().getAttachProtocol(212);
        recordState.onPrepare();
        ModeProtocol.LiveConfigVV liveConfigVV = this.mLiveConfigChanges;
        if (liveConfigVV != null) {
            liveConfigVV.startRecordingNewFragment();
            this.mOldOriginVolumeStream = getActivity().getVolumeControlStream();
            getActivity().setVolumeControlStream(3);
        }
        recordState.onStart();
        if (HybridZoomingSystem.IS_3_OR_MORE_SAT && isBackCamera()) {
            if (isUltraWideBackCamera()) {
                setMinZoomRatio(0.6f);
                setMaxZoomRatio(2.0f);
            } else {
                setMinZoomRatio(1.0f);
                setMaxZoomRatio(Math.min(6.0f, ((BaseModule) this).mCameraCapabilities.getMaxZoomRatio()));
            }
        }
        this.mTelephonyManager.listen(((BaseModule) this).mPhoneStateListener, 32);
        Log.v(TAG, "listen call state");
        HashMap hashMap = new HashMap();
        hashMap.put(MistatsConstants.Manual.PARAM_3A_LOCKED, Boolean.valueOf(this.m3ALocked));
        trackGeneralInfo(hashMap, 1, false, this.mBeautyValues, false, 0);
    }

    private void trackLiveRecordingParams() {
    }

    private void trackLiveVideoParams() {
        this.mLiveConfigChanges.trackVideoParams();
    }

    private void updateBeauty() {
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
        String str = TAG;
        Log.v(str, "updateFilter: 0x" + Integer.toHexString(shaderEffect));
        EffectController.getInstance().setEffect(shaderEffect);
    }

    private void updateFocusMode() {
        setFocusMode(this.mFocusManager.setFocusMode(CameraSettings.getFocusMode()));
    }

    private void updateFpsRange() {
        ((BaseModule) this).mCamera2Device.setFpsRange(new Range<>(30, 30));
    }

    private void updateLiveRelated() {
        if (((BaseModule) this).mAlgorithmPreviewSize != null) {
            ((BaseModule) this).mCamera2Device.startPreviewCallback(this.mLiveConfigChanges);
        }
    }

    private void updatePictureAndPreviewSize() {
        List<CameraSize> supportedOutputSizeWithAssignedMode = ((BaseModule) this).mCameraCapabilities.getSupportedOutputSizeWithAssignedMode(SurfaceTexture.class);
        float previewRatio = this.mLiveConfigChanges.getPreviewRatio();
        Util.getOptimalPreviewSize(false, ((BaseModule) this).mBogusCameraId, supportedOutputSizeWithAssignedMode, (double) previewRatio);
        ((BaseModule) this).mPreviewSize = new CameraSize(3840, 2160);
        String str = TAG;
        Log.d(str, "previewSize: " + ((BaseModule) this).mPreviewSize.toString());
        ((BaseModule) this).mPictureSize = null;
        CameraSize optimalVideoSnapshotPictureSize = Util.getOptimalVideoSnapshotPictureSize(supportedOutputSizeWithAssignedMode, (double) CameraSettings.getPreviewAspectRatio(previewRatio), Util.sWindowHeight, Util.sWindowWidth);
        String str2 = TAG;
        Log.d(str2, "displaySize: " + optimalVideoSnapshotPictureSize.toString());
        if (((BaseModule) this).mAlgorithmPreviewSize != null) {
            String str3 = TAG;
            Log.d(str3, "AlgorithmPreviewSize: " + ((BaseModule) this).mAlgorithmPreviewSize.toString());
            ((BaseModule) this).mCamera2Device.setAlgorithmPreviewSize(((BaseModule) this).mAlgorithmPreviewSize);
            ((BaseModule) this).mCamera2Device.setAlgorithmPreviewFormat(35);
            ((BaseModule) this).mCamera2Device.setPreviewMaxImages(1);
        }
        updateCameraScreenNailSize(optimalVideoSnapshotPictureSize.width, optimalVideoSnapshotPictureSize.height);
    }

    private void updateUltraWideLDC() {
        ((BaseModule) this).mCamera2Device.setUltraWideLDC(shouldApplyUltraWideLDC());
    }

    private void updateVideoStabilization() {
        if (isDeviceAlive()) {
            if (isEisOn()) {
                Log.d(TAG, "videoStabilization: EIS");
                ((BaseModule) this).mCamera2Device.setEnableEIS(true);
                ((BaseModule) this).mCamera2Device.setEnableOIS(false);
                if (!((BaseModule) this).mCameraCapabilities.isEISPreviewSupported()) {
                    ((BaseModule) this).mActivity.getCameraScreenNail().setVideoStabilizationCropped(true);
                    return;
                }
                return;
            }
            Log.d(TAG, "videoStabilization: OIS");
            ((BaseModule) this).mCamera2Device.setEnableEIS(false);
            ((BaseModule) this).mCamera2Device.setEnableOIS(true);
            ((BaseModule) this).mActivity.getCameraScreenNail().setVideoStabilizationCropped(false);
        }
    }

    public /* synthetic */ void Sf() {
        ((BaseModule) this).mCamera2Device.setDeviceOrientation(((BaseModule) this).mOrientation);
    }

    public /* synthetic */ void b(RxData.DataWrap dataWrap) throws Exception {
        onProcessingSateChanged(((Integer) dataWrap.get()).intValue());
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
        Camera camera = ((BaseModule) this).mActivity;
        if (camera != null) {
            camera.getCameraScreenNail().setExternalFrameProcessor(null);
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
        this.mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY);
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

    public void doReverse() {
        ModeProtocol.LiveConfigVV liveConfigVV = this.mLiveConfigChanges;
        if (liveConfigVV != null && !liveConfigVV.isRecording()) {
            this.mLiveConfigChanges.deleteLastFragment();
        }
    }

    /* access modifiers changed from: protected */
    public boolean enableFaceDetection() {
        return DataRepository.dataItemGlobal().getBoolean("pref_camera_facedetection_key", getResources().getBoolean(R.bool.pref_camera_facedetection_default));
    }

    @Override // android.arch.lifecycle.LifecycleOwner
    @NonNull
    public Lifecycle getLifecycle() {
        return this.mLifecycleRegistry;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.BaseModule
    public int getOperatingMode() {
        return 32780;
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
        ModeProtocol.LiveConfigVV liveConfigVV;
        return HybridZoomingSystem.IS_3_OR_MORE_SAT && (i = ((BaseModule) this).mModuleIndex) == 174 && !CameraSettings.isMacroModeEnabled(i) && isBackCamera() && (liveConfigVV = this.mLiveConfigChanges) != null && !liveConfigVV.isRecording();
    }

    @Override // com.android.camera.module.Module, com.android.camera.protocol.ModeProtocol.CameraAction
    public boolean isDoingAction() {
        return isRecording() || getCameraState() == 3;
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
        ModeProtocol.LiveConfigVV liveConfigVV = this.mLiveConfigChanges;
        return liveConfigVV != null && liveConfigVV.isRecording();
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public boolean isSelectingCapturedResult() {
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
        return false;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.BaseModule
    public boolean judgeTapableRectByUiStyle() {
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
        Log.d(TAG, "lockAEAF");
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
            stopVideoRecording(true, true);
        }
        super.notifyError();
    }

    @Override // com.android.camera.module.loader.camera2.FocusManager2.Listener
    public void notifyFocusAreaUpdate() {
        updatePreferenceTrampoline(3);
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public boolean onBackPressed() {
        ModeProtocol.LiveVVProcess liveVVProcess = (ModeProtocol.LiveVVProcess) ModeCoordinatorImpl.getInstance().getAttachProtocol(230);
        if (liveVVProcess == null) {
            return super.onBackPressed();
        }
        VMProcessing vMProcessing = this.mVmProcessing;
        if (vMProcessing == null || vMProcessing.getCurrentState() == 7) {
            return true;
        }
        liveVVProcess.showExitConfirm();
        return true;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.BaseModule
    public void onCameraOpened() {
        super.onCameraOpened();
        int initLiveConfig = initLiveConfig();
        if (initLiveConfig == 0 || initLiveConfig == 1 || !(initLiveConfig == 2 || initLiveConfig == 3 || initLiveConfig == 4)) {
            initializeFocusManager();
            updatePreferenceTrampoline(UpdateConstant.FUN_TYPES_INIT);
            startPreview();
            if (DataRepository.dataItemGlobal().isFirstShowCTAConCollect() && ((BaseModule) this).mModuleIndex == 174) {
                this.mCtaNoticeFragment = CtaNoticeFragment.showCta(getActivity().getFragmentManager(), false, null, 1);
            }
            this.mVmProcessing = (VMProcessing) DataRepository.dataItemObservable().get(VMProcessing.class);
            this.mVmProcessing.startObservable(this, new w(this));
            this.mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START);
            this.mOnResumeTime = SystemClock.uptimeMillis();
            ((BaseModule) this).mHandler.sendEmptyMessage(4);
            ((BaseModule) this).mHandler.sendEmptyMessage(31);
            initMetaParser();
            return;
        }
        showAuthError();
    }

    @Override // com.android.camera2.Camera2Proxy.PictureCallback
    public void onCaptureShutter(boolean z) {
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
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void onDestroy() {
        super.onDestroy();
        CtaNoticeFragment ctaNoticeFragment = this.mCtaNoticeFragment;
        if (ctaNoticeFragment != null) {
            ctaNoticeFragment.dismiss();
        }
        ((BaseModule) this).mHandler.post(y.INSTANCE);
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
                Log.v(TAG, String.format(Locale.ENGLISH, "FocusTime=%1$dms focused=%2$b", Long.valueOf(focusTask.getElapsedTime()), Boolean.valueOf(focusTask.isSuccess())));
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
                    Log.v(TAG, str);
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
            stopVideoRecording(true, true);
        } else if (isSaving()) {
            return;
        }
        doLaterReleaseIfNeed();
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
                        ModeProtocol.LiveVVProcess liveVVProcess = (ModeProtocol.LiveVVProcess) ModeCoordinatorImpl.getInstance().getAttachProtocol(230);
                        if (liveVVProcess != null) {
                            liveVVProcess.onKeyCodeCamera();
                        }
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
    public void onNewUriArrived(final Uri uri, final String str) {
        super.onNewUriArrived(uri, str);
        if (isAlive()) {
            ((BaseModule) this).mHandler.post(new Runnable() {
                /* class com.android.camera.module.LiveModuleSubVV.AnonymousClass4 */

                public void run() {
                    ContentValues saveContentValues;
                    ModeProtocol.LiveVVProcess liveVVProcess = (ModeProtocol.LiveVVProcess) ModeCoordinatorImpl.getInstance().getAttachProtocol(230);
                    if (liveVVProcess != null && (saveContentValues = liveVVProcess.getSaveContentValues()) != null) {
                        String asString = saveContentValues.getAsString("title");
                        String access$600 = LiveModuleSubVV.TAG;
                        Log.d(access$600, "newUri: " + str + " | " + asString);
                        if (asString.equals(str)) {
                            liveVVProcess.onLiveSaveToLocalFinished(uri);
                        }
                    }
                }
            });
        }
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void onOrientationChanged(int i, int i2, int i3) {
        setOrientation(i, i2);
        ModeProtocol.LiveConfigVV liveConfigVV = this.mLiveConfigChanges;
        if (liveConfigVV != null) {
            liveConfigVV.onOrientationChanged(i, i2, i3);
        }
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
        FocusManager2 focusManager2 = this.mFocusManager;
        if (focusManager2 != null) {
            focusManager2.removeMessages();
        }
        if (getActivity().startFromKeyguard()) {
            DataRepository.dataItemLive().getMimojiStatusManager().reset();
        }
        tryRemoveCountDownMessage();
        ((BaseModule) this).mActivity.getSensorStateManager().reset();
        resetScreenOn();
        closeCamera();
        Handler handler = ((BaseModule) this).mHandler;
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }

    public void onPauseButtonClick() {
        ModeProtocol.RecordState recordState = (ModeProtocol.RecordState) ModeCoordinatorImpl.getInstance().getAttachProtocol(212);
        if (this.mLiveConfigChanges.isRecording()) {
            this.mLiveConfigChanges.onRecordingNewFragmentFinished();
            if (HybridZoomingSystem.IS_3_OR_MORE_SAT && isBackCamera()) {
                updateZoomRatioToggleButtonState(false);
                setMinZoomRatio(0.6f);
                setMaxZoomRatio(((BaseModule) this).mCameraCapabilities.getMaxZoomRatio());
            }
            if (recordState != null) {
                recordState.onPause();
                return;
            }
            return;
        }
        if (HybridZoomingSystem.IS_3_OR_MORE_SAT && isBackCamera()) {
            updateZoomRatioToggleButtonState(true);
            if (isUltraWideBackCamera()) {
                setMinZoomRatio(0.6f);
                setMaxZoomRatio(((BaseModule) this).mCameraCapabilities.getMaxZoomRatio() * 0.6f);
            } else {
                setMinZoomRatio(1.0f);
                setMaxZoomRatio(Math.min(6.0f, ((BaseModule) this).mCameraCapabilities.getMaxZoomRatio()));
            }
        }
        trackLiveRecordingParams();
        this.mLiveConfigChanges.startRecordingNextFragment();
        if (recordState != null) {
            recordState.onResume();
        }
    }

    @Override // com.android.camera2.Camera2Proxy.PictureCallback
    public void onPictureTaken(byte[] bArr, CaptureResult captureResult) {
    }

    @Override // com.android.camera2.Camera2Proxy.PictureCallback
    public void onPictureTakenFinished(boolean z) {
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
            Log.d(TAG, "sessionFailed due to surfaceTexture expired, retry");
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
    }

    @Override // com.android.camera.protocol.ModeProtocol.CameraAction
    public void onReviewCancelClicked() {
        getActivity().setVolumeControlStream(this.mOldOriginVolumeStream);
        ((ModeProtocol.RecordState) ModeCoordinatorImpl.getInstance().getAttachProtocol(212)).onFinish();
        this.mIsPreviewing = false;
        ((ModeProtocol.ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164)).configLiveVV(null, false, false);
        doLaterReleaseIfNeed();
    }

    @Override // com.android.camera.protocol.ModeProtocol.CameraAction
    public void onReviewDoneClicked() {
        getActivity().setVolumeControlStream(this.mOldOriginVolumeStream);
        ((ModeProtocol.RecordState) ModeCoordinatorImpl.getInstance().getAttachProtocol(212)).onFinish();
        this.mIsPreviewing = false;
        startSaveToLocal();
        ((ModeProtocol.ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164)).configLiveVV(null, false, true);
        doLaterReleaseIfNeed();
    }

    @Override // com.android.camera.module.BaseModule
    public void onShineChanged(int i) {
        if (i == 239) {
            updatePreferenceInWorkThread(13);
            return;
        }
        throw new RuntimeException("unknown configItem changed");
    }

    @Override // com.android.camera.protocol.ModeProtocol.CameraAction
    public void onShutterButtonClick(int i) {
        ModeProtocol.LiveConfigVV liveConfigVV = this.mLiveConfigChanges;
        int nextRecordStep = liveConfigVV != null ? liveConfigVV.getNextRecordStep() : 1;
        if (nextRecordStep == 1) {
            return;
        }
        if (nextRecordStep != 2) {
            if (nextRecordStep == 3) {
                stopVideoRecording(true, false);
            }
        } else if (!checkCallingState()) {
            Log.d(TAG, "ignore in calling state");
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
                Log.w(TAG, "onSingleTapUp: frame not available");
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
            if (i == 2 || i == 1) {
                ModeProtocol.LiveConfigVV liveConfigVV = this.mLiveConfigChanges;
                if ((liveConfigVV == null || (!liveConfigVV.isRecording() && !this.mLiveConfigChanges.isRecordingPaused())) && !CameraSettings.isMacroModeEnabled(((BaseModule) this).mModuleIndex)) {
                    dualController.setImmersiveModeEnabled(false);
                }
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
                Log.w(TAG, "ignore volume key");
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
        ModeCoordinatorImpl.getInstance().attachProtocol(161, this);
        ModeCoordinatorImpl.getInstance().attachProtocol(169, this);
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
            Log.v(TAG, "startFocus");
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
            ((BaseModule) this).mCamera2Device.setPreviewSize(((BaseModule) this).mPreviewSize);
            this.mQuality = Util.convertSizeToQuality(((BaseModule) this).mPreviewSize);
            ((BaseModule) this).mSurfaceCreatedTimestamp = ((BaseModule) this).mActivity.getCameraScreenNail().getSurfaceCreatedTimestamp();
            Log.d(TAG, "LiveModule, startPreview");
            checkDisplayOrientation();
            new Surface(((BaseModule) this).mActivity.getCameraScreenNail().getSurfaceTexture());
            boolean z = ((BaseModule) this).mAlgorithmPreviewSize != null;
            ModeProtocol.LiveConfigVV liveConfigVV = this.mLiveConfigChanges;
            CameraSize cameraSize = ((BaseModule) this).mPreviewSize;
            liveConfigVV.initPreview(cameraSize.width, cameraSize.height, ((BaseModule) this).mBogusCameraId, ((BaseModule) this).mActivity.getCameraScreenNail());
            Surface surface = new Surface(this.mLiveConfigChanges.getInputSurfaceTexture());
            isEisOn();
            if (!isSelectingCapturedResult()) {
                ((BaseModule) this).mCamera2Device.startPreviewSession(surface, z, false, false, getOperatingMode(), false, this);
            }
        }
    }

    public void startSaveToLocal() {
        ContentValues saveContentValues;
        if (!this.mSaved && (saveContentValues = ((ModeProtocol.LiveVVProcess) ModeCoordinatorImpl.getInstance().getAttachProtocol(230)).getSaveContentValues()) != null) {
            this.mSaved = true;
            saveContentValues.put("datetaken", Long.valueOf(System.currentTimeMillis()));
            getActivity().getImageSaver().addVideo(saveContentValues.getAsString("_data"), saveContentValues, true);
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
        ModeProtocol.RecordState recordState = (ModeProtocol.RecordState) ModeCoordinatorImpl.getInstance().getAttachProtocol(212);
        if (isAlive() && recordState != null) {
            keepScreenOnAwhile();
            recordState.onPause();
            this.mLiveConfigChanges.onRecordingNewFragmentFinished();
            if (this.mLiveConfigChanges.canFinishRecording()) {
                trackLiveVideoParams();
                showPreview();
                recordState.onFinish();
            }
            this.mTelephonyManager.listen(((BaseModule) this).mPhoneStateListener, 0);
            Log.v(TAG, "listen none");
        }
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void unRegisterModulePersistProtocol() {
        super.unRegisterModulePersistProtocol();
        Log.d(TAG, "unRegisterModulePersistProtocol");
        getActivity().getImplFactory().detachModulePersistent();
    }

    @Override // com.android.camera.protocol.ModeProtocol.BaseProtocol, com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void unRegisterProtocol() {
        super.unRegisterProtocol();
        ModeCoordinatorImpl.getInstance().detachProtocol(161, this);
        ModeCoordinatorImpl.getInstance().detachProtocol(169, this);
        getActivity().getImplFactory().detachAdditional();
    }

    /* access modifiers changed from: protected */
    public void unlockAEAF() {
        Log.d(TAG, "unlockAEAF");
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
            Log.e(TAG, "updateFocusArea: null camera device");
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
