package com.android.camera.features.mimoji2.module;

import a.a.a;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.hardware.SensorEvent;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CaptureResult;
import android.media.Image;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.support.annotation.MainThread;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Range;
import android.view.KeyEvent;
import android.view.Surface;
import android.view.View;
import com.android.camera.BasePreferenceActivity;
import com.android.camera.Camera;
import com.android.camera.CameraAppImpl;
import com.android.camera.CameraPreferenceActivity;
import com.android.camera.CameraSettings;
import com.android.camera.CameraSize;
import com.android.camera.HybridZoomingSystem;
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
import com.android.camera.features.mimoji2.module.impl.MimojiAvatarEngine2Impl;
import com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol;
import com.android.camera.features.mimoji2.widget.helper.MimojiHelper2;
import com.android.camera.fragment.beauty.BeautyValues;
import com.android.camera.fragment.beauty.LiveBeautyFilterFragment;
import com.android.camera.log.Log;
import com.android.camera.module.BaseModule;
import com.android.camera.module.impl.component.FileUtils;
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
import com.android.camera2.CameraCapabilities;
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
import java.io.IOException;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class MimojiModule extends BaseModule implements Camera2Proxy.CameraPreviewCallback, Camera2Proxy.FocusCallback, Camera2Proxy.FaceDetectionCallback, ModeProtocol.CameraAction, Camera2Proxy.PictureCallback, FocusManager2.Listener {
    private static final int BEAUTY_SWITCH = 8;
    private static final int FILTER_SWITCH = 2;
    private static final int STICKER_SWITCH = 4;
    /* access modifiers changed from: private */
    public static final String TAG = "MimojiModule";
    private static final int VALID_VIDEO_TIME = 500;
    private boolean m3ALocked;
    private String mBaseFileName;
    protected BeautyValues mBeautyValues;
    private String mCaptureWaterMarkStr;
    private int mDeviceOrientation = 90;
    private AlertDialog mDialog;
    private boolean mDisableSingleTapUp = false;
    protected boolean mFaceDetectionEnabled;
    protected boolean mFaceDetectionStarted;
    /* access modifiers changed from: private */
    public FocusManager2 mFocusManager;
    private boolean mIsLowLight;
    private int mMessageId;
    private Disposable mMetaDataDisposable;
    /* access modifiers changed from: private */
    public FlowableEmitter<CaptureResult> mMetaDataFlowableEmitter;
    private MimojiModeProtocol.MimojiVideoEditor mMimojiVideoEditor;
    /* access modifiers changed from: private */
    public long mOnResumeTime;
    private int mQuality = 5;
    private long mRecordTime;
    protected SensorStateManager.SensorStateListener mSensorStateListener = new SensorStateManager.SensorStateListener() {
        /* class com.android.camera.features.mimoji2.module.MimojiModule.AnonymousClass3 */

        @Override // com.android.camera.SensorStateManager.SensorStateListener
        public boolean isWorking() {
            return MimojiModule.this.isAlive() && MimojiModule.this.getCameraState() != 0;
        }

        @Override // com.android.camera.SensorStateManager.SensorStateListener
        public void notifyDevicePostureChanged() {
            ((BaseModule) MimojiModule.this).mActivity.getEdgeShutterView().onDevicePostureChanged();
        }

        @Override // com.android.camera.SensorStateManager.SensorStateListener
        public void onDeviceBecomeStable() {
            Log.v(MimojiModule.TAG, "onDeviceBecomeStable");
        }

        @Override // com.android.camera.SensorStateManager.SensorStateListener
        public void onDeviceBeginMoving() {
        }

        @Override // com.android.camera.SensorStateManager.SensorStateListener
        public void onDeviceKeepMoving(double d2) {
            if (!((BaseModule) MimojiModule.this).mMainProtocol.isEvAdjusted(true) && !((BaseModule) MimojiModule.this).mPaused && Util.isTimeout(System.currentTimeMillis(), MimojiModule.this.mTouchFocusStartingTime, 3000) && !MimojiModule.this.is3ALocked() && MimojiModule.this.mFocusManager != null && MimojiModule.this.mFocusManager.isNeedCancelAutoFocus() && !MimojiModule.this.isRecording()) {
                MimojiModule.this.mFocusManager.onDeviceKeepMoving(d2);
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
    private MimojiAvatarEngine2Impl mimojiAvatarEngine2;
    private boolean misFaceLocationOk;

    private class LiveAsdConsumer implements Consumer<Integer> {
        private LiveAsdConsumer() {
        }

        public void accept(Integer num) throws Exception {
            MimojiModule.this.mimojiLightDetect(num);
        }
    }

    private class MainHandler extends Handler {
        public MainHandler(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message message) {
            int i = message.what;
            if (i == 2) {
                MimojiModule.this.getWindow().clearFlags(128);
            } else if (i == 4) {
                MimojiModule.this.checkActivityOrientation();
                if (SystemClock.uptimeMillis() - MimojiModule.this.mOnResumeTime < 5000) {
                    ((BaseModule) MimojiModule.this).mHandler.sendEmptyMessageDelayed(4, 100);
                }
            } else if (i == 9) {
                ((BaseModule) MimojiModule.this).mMainProtocol.initializeFocusView(MimojiModule.this);
            } else if (i == 17) {
                ((BaseModule) MimojiModule.this).mHandler.removeMessages(17);
                ((BaseModule) MimojiModule.this).mHandler.removeMessages(2);
                MimojiModule.this.getWindow().addFlags(128);
                ((BaseModule) MimojiModule.this).mHandler.sendEmptyMessageDelayed(2, (long) MimojiModule.this.getScreenDelay());
            } else if (i == 31) {
                MimojiModule.this.setOrientationParameter();
            } else if (i == 35) {
                MimojiModule mimojiModule = MimojiModule.this;
                boolean z = false;
                boolean z2 = message.arg1 > 0;
                if (message.arg2 > 0) {
                    z = true;
                }
                mimojiModule.handleUpdateFaceView(z2, z);
            } else if (i == 51 && !((BaseModule) MimojiModule.this).mActivity.isActivityPaused()) {
                boolean unused = ((BaseModule) MimojiModule.this).mOpenCameraFail = true;
                MimojiModule.this.onCameraException();
            }
        }
    }

    static /* synthetic */ void Bf() {
        ModeProtocol.MainContentProtocol mainContentProtocol = (ModeProtocol.MainContentProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(166);
        if (mainContentProtocol != null) {
            mainContentProtocol.setCenterHint(8, null, null, 0);
        }
    }

    private String createName(long j, int i) {
        if (i > 0) {
            return this.mBaseFileName;
        }
        this.mBaseFileName = new SimpleDateFormat(getString(R.string.video_file_name_format), Locale.ENGLISH).format(new Date(j));
        return this.mBaseFileName;
    }

    private void deleteMimojiCache() {
        MimojiAvatarEngine2Impl mimojiAvatarEngine2Impl = this.mimojiAvatarEngine2;
        if (mimojiAvatarEngine2Impl != null) {
            mimojiAvatarEngine2Impl.deleteMimojiCache(0);
        } else {
            Log.e(TAG, "mimoji void deleteMimojiCache[] null");
        }
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

    /* access modifiers changed from: private */
    public void handleUpdateFaceView(boolean z, boolean z2) {
        boolean isFrontCamera = isFrontCamera();
        if (!z) {
            ((BaseModule) this).mMainProtocol.updateFaceView(z, z2, isFrontCamera, false, -1);
        } else if (this.mFaceDetectionStarted && 1 != ((BaseModule) this).mCamera2Device.getFocusMode()) {
            ((BaseModule) this).mMainProtocol.updateFaceView(z, true, isFrontCamera, true, ((BaseModule) this).mCameraDisplayOrientation);
        }
    }

    private void initMetaParser() {
        this.mMetaDataDisposable = Flowable.create(new FlowableOnSubscribe<CaptureResult>() {
            /* class com.android.camera.features.mimoji2.module.MimojiModule.AnonymousClass1 */

            @Override // io.reactivex.FlowableOnSubscribe
            public void subscribe(FlowableEmitter<CaptureResult> flowableEmitter) throws Exception {
                FlowableEmitter unused = MimojiModule.this.mMetaDataFlowableEmitter = flowableEmitter;
            }
        }, BackpressureStrategy.DROP).map(new FunctionParseAsdFace(this, isFrontCamera())).map(new FunctionParseAsdScene(this)).subscribe(new LiveAsdConsumer());
    }

    private void initMimojiEngine() {
        this.mimojiAvatarEngine2 = (MimojiAvatarEngine2Impl) ModeCoordinatorImpl.getInstance().getAttachProtocol(246);
        if (this.mimojiAvatarEngine2 == null) {
            getActivity().getImplFactory().initModulePersistent(getActivity(), 246);
            this.mimojiAvatarEngine2 = (MimojiAvatarEngine2Impl) ModeCoordinatorImpl.getInstance().getAttachProtocol(246);
            this.mimojiAvatarEngine2.onDeviceRotationChange(this.mDeviceOrientation);
        }
        this.mMimojiVideoEditor = (MimojiModeProtocol.MimojiVideoEditor) ModeCoordinatorImpl.getInstance().getAttachProtocol(252);
        if (this.mMimojiVideoEditor == null) {
            getActivity().getImplFactory().initModulePersistent(getActivity(), 252);
            this.mMimojiVideoEditor = (MimojiModeProtocol.MimojiVideoEditor) ModeCoordinatorImpl.getInstance().getAttachProtocol(252);
        }
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

    private boolean isPreviewEisOn() {
        return isBackCamera() && CameraSettings.isMovieSolidOn() && ((BaseModule) this).mCameraCapabilities.isEISPreviewSupported();
    }

    private boolean isSaving() {
        if (((MimojiModeProtocol.MimojiFullScreenProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(249)) == null) {
        }
        return false;
    }

    /* access modifiers changed from: private */
    public void mimojiLightDetect(Integer num) {
        MimojiAvatarEngine2Impl mimojiAvatarEngine2Impl = this.mimojiAvatarEngine2;
        if (mimojiAvatarEngine2Impl != null && mimojiAvatarEngine2Impl.isOnCreateMimoji()) {
            if (!this.misFaceLocationOk) {
                ModeProtocol.MainContentProtocol mainContentProtocol = ((BaseModule) this).mMainProtocol;
                if (mainContentProtocol != null) {
                    mainContentProtocol.updateMimojiFaceDetectResultTip(false);
                    return;
                }
                return;
            }
            ModeProtocol.MainContentProtocol mainContentProtocol2 = ((BaseModule) this).mMainProtocol;
            if (mainContentProtocol2 != null) {
                mainContentProtocol2.setMimojiDetectTipType(162);
            }
            int intValue = num.intValue();
            if (intValue == 6 || intValue == 9) {
                this.mIsLowLight = true;
                ModeProtocol.MainContentProtocol mainContentProtocol3 = ((BaseModule) this).mMainProtocol;
                if (mainContentProtocol3 != null) {
                    mainContentProtocol3.updateMimojiFaceDetectResultTip(true);
                    return;
                }
                return;
            }
            this.mIsLowLight = false;
            ModeProtocol.MainContentProtocol mainContentProtocol4 = ((BaseModule) this).mMainProtocol;
            if (mainContentProtocol4 != null) {
                mainContentProtocol4.updateMimojiFaceDetectResultTip(false);
            }
        }
    }

    private void onMimojiCapture() {
        ((BaseModule) this).mCamera2Device.setShotType(-1);
        ((BaseModule) this).mCamera2Device.takePicture(this, ((BaseModule) this).mActivity.getImageSaver());
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
                GlobalConstant.sCameraSetupScheduler.scheduleDirect(new b(this));
                return;
            }
            updatePreferenceInWorkThread(35);
        }
    }

    private void setWaterMark() {
        if (isDeviceAlive()) {
            if (CameraSettings.isDualCameraWaterMarkOpen()) {
                ((BaseModule) this).mCamera2Device.setDualCamWaterMarkEnable(true);
            } else {
                ((BaseModule) this).mCamera2Device.setDualCamWaterMarkEnable(false);
            }
            if (CameraSettings.isTimeWaterMarkOpen()) {
                ((BaseModule) this).mCamera2Device.setTimeWaterMarkEnable(true);
                this.mCaptureWaterMarkStr = Util.getTimeWatermark();
                ((BaseModule) this).mCamera2Device.setTimeWatermarkValue(this.mCaptureWaterMarkStr);
                return;
            }
            this.mCaptureWaterMarkStr = null;
            ((BaseModule) this).mCamera2Device.setTimeWaterMarkEnable(false);
        }
    }

    private boolean shouldApplyUltraWideLDC() {
        if (b.isMTKPlatform()) {
            return ((BaseModule) this).mModuleIndex == 174 && ((BaseModule) this).mActualCameraId == Camera2DataContainer.getInstance().getUltraWideCameraId();
        }
        if (CameraSettings.shouldUltraWideVideoLDCBeVisibleInMode(((BaseModule) this).mModuleIndex) && ((BaseModule) this).mActualCameraId == Camera2DataContainer.getInstance().getUltraWideCameraId()) {
            return CameraSettings.isUltraWideVideoLDCEnabled();
        }
        return false;
    }

    private void showPreview() {
        ModeProtocol.RecordState recordState = (ModeProtocol.RecordState) ModeCoordinatorImpl.getInstance().getAttachProtocol(212);
        if (isAlive() && recordState != null) {
            keepScreenOnAwhile();
            recordState.onPostPreview();
            pausePreview();
            if (!FileUtils.checkFileConsist(this.mimojiAvatarEngine2.getVideoCache())) {
                onReviewCancelClicked();
            } else if (this.mimojiAvatarEngine2 == null) {
                Log.e(TAG, " mimoji  showPreview contentValues null error");
            } else if (!CameraSettings.isGifOn()) {
                ((MimojiModeProtocol.MimojiFullScreenProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(249)).startMimojiRecordPreview();
            }
        }
    }

    private boolean startScreenLight(int i, int i2) {
        if (((BaseModule) this).mPaused) {
            return false;
        }
        Camera camera = ((BaseModule) this).mActivity;
        if (camera != null) {
            camera.setWindowBrightness(i2);
        }
        ModeProtocol.FullScreenProtocol fullScreenProtocol = (ModeProtocol.FullScreenProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(196);
        if (fullScreenProtocol == null) {
            return true;
        }
        fullScreenProtocol.setScreenLightColor(i);
        return fullScreenProtocol.showScreenLight();
    }

    @MainThread
    private void startVideoRecording() {
        keepScreenOn();
        ModeProtocol.RecordState recordState = (ModeProtocol.RecordState) ModeCoordinatorImpl.getInstance().getAttachProtocol(212);
        recordState.onPrepare();
        recordState.onStart();
        if (HybridZoomingSystem.IS_3_OR_MORE_SAT && isBackCamera()) {
            updateZoomRatioToggleButtonState(true);
            if (isUltraWideBackCamera()) {
                setMinZoomRatio(0.6f);
                setMaxZoomRatio(2.0f);
            } else if (isAuxCamera()) {
                setMinZoomRatio(HybridZoomingSystem.FLOAT_ZOOM_RATIO_TELE);
                setMaxZoomRatio(Math.min(6.0f, ((BaseModule) this).mCameraCapabilities.getMaxZoomRatio()));
            } else {
                setMinZoomRatio(1.0f);
                setMaxZoomRatio(((BaseModule) this).mCameraCapabilities.getMaxZoomRatio());
            }
        }
        this.mTelephonyManager.listen(((BaseModule) this).mPhoneStateListener, 32);
        Log.v(TAG, "listen call state");
        MimojiAvatarEngine2Impl mimojiAvatarEngine2Impl = this.mimojiAvatarEngine2;
        if (mimojiAvatarEngine2Impl != null) {
            mimojiAvatarEngine2Impl.onRecordStart();
            this.mRecordTime = System.currentTimeMillis();
        }
        HashMap hashMap = new HashMap();
        hashMap.put(MistatsConstants.Manual.PARAM_3A_LOCKED, Boolean.valueOf(this.m3ALocked));
        trackGeneralInfo(hashMap, 1, false, this.mBeautyValues, false, -1);
    }

    private void stopScreenLight() {
        ((BaseModule) this).mHandler.post(new c(this));
    }

    private void trackLiveRecordingParams() {
        boolean z;
        boolean z2;
        boolean z3;
        int liveAllSwitchAllValue = CameraSettings.getLiveAllSwitchAllValue();
        String str = CameraSettings.getCurrentLiveMusic()[1];
        boolean z4 = !str.isEmpty();
        LiveBeautyFilterFragment.LiveFilterItem findLiveFilter = EffectController.getInstance().findLiveFilter(CameraAppImpl.getAndroidContext(), DataRepository.dataItemLive().getLiveFilter());
        if (!findLiveFilter.directoryName.isEmpty()) {
            if ((liveAllSwitchAllValue & 2) == 0) {
                liveAllSwitchAllValue += 2;
            }
            z = true;
        } else {
            z = false;
        }
        String currentLiveStickerName = CameraSettings.getCurrentLiveStickerName();
        if (!currentLiveStickerName.isEmpty()) {
            if ((liveAllSwitchAllValue & 4) == 0) {
                liveAllSwitchAllValue += 4;
            }
            z2 = true;
        } else {
            z2 = false;
        }
        String currentLiveSpeedText = CameraSettings.getCurrentLiveSpeedText();
        int faceBeautyRatio = CameraSettings.getFaceBeautyRatio("key_live_shrink_face_ratio");
        int faceBeautyRatio2 = CameraSettings.getFaceBeautyRatio("key_live_enlarge_eye_ratio");
        int faceBeautyRatio3 = CameraSettings.getFaceBeautyRatio("key_live_smooth_strength");
        if (faceBeautyRatio > 0 || faceBeautyRatio2 > 0 || faceBeautyRatio3 > 0) {
            if ((liveAllSwitchAllValue & 8) == 0) {
                liveAllSwitchAllValue += 8;
            }
            z3 = true;
        } else {
            z3 = false;
        }
        CameraStatUtils.trackLiveRecordingParams(z4, str, z, findLiveFilter.directoryName, z2, currentLiveStickerName, currentLiveSpeedText, z3, faceBeautyRatio, faceBeautyRatio2, faceBeautyRatio3, this.mQuality, isFrontCamera());
        CameraSettings.setLiveAllSwitchAddValue(liveAllSwitchAllValue);
    }

    private void updateBeauty() {
        if (this.mBeautyValues == null) {
            this.mBeautyValues = new BeautyValues();
        }
        CameraSettings.initBeautyValues(this.mBeautyValues, ((BaseModule) this).mModuleIndex);
        String str = TAG;
        Log.d(str, "updateBeauty(): " + this.mBeautyValues);
        ((BaseModule) this).mCamera2Device.setBeautyValues(this.mBeautyValues);
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

    private void updateGif() {
        final boolean isGifOn = CameraSettings.isGifOn();
        MimojiModeProtocol.MimojiAvatarEngine2 mimojiAvatarEngine22 = (MimojiModeProtocol.MimojiAvatarEngine2) ModeCoordinatorImpl.getInstance().getAttachProtocol(246);
        if (mimojiAvatarEngine22 != null) {
            mimojiAvatarEngine22.changeToSquare(isGifOn);
        }
        ((BaseModule) this).mHandler.post(new Runnable() {
            /* class com.android.camera.features.mimoji2.module.MimojiModule.AnonymousClass2 */

            public void run() {
                ((ModeProtocol.ActionProcessing) ModeCoordinatorImpl.getInstance().getAttachProtocol(162)).entryOrExitMiMojiGif(isGifOn);
            }
        });
    }

    private void updateLiveRelated() {
        MimojiAvatarEngine2Impl mimojiAvatarEngine2Impl = this.mimojiAvatarEngine2;
        int i = ((BaseModule) this).mCameraDisplayOrientation;
        int i2 = isFrontCamera() ? 270 : 90;
        CameraSize cameraSize = ((BaseModule) this).mPreviewSize;
        mimojiAvatarEngine2Impl.initAvatarEngine(i, i2, cameraSize.width, cameraSize.height, isFrontCamera());
        ((BaseModule) this).mCamera2Device.startPreviewCallback(this.mimojiAvatarEngine2);
        boolean isGifOn = CameraSettings.isGifOn();
        int currentMode = DataRepository.dataItemGlobal().getCurrentMode();
        MimojiModeProtocol.MimojiAvatarEngine2 mimojiAvatarEngine22 = (MimojiModeProtocol.MimojiAvatarEngine2) ModeCoordinatorImpl.getInstance().getAttachProtocol(217);
        if (mimojiAvatarEngine22 != null && currentMode == 184) {
            mimojiAvatarEngine22.changeToSquare(isGifOn);
        }
    }

    private void updateMimojiVideoCache() {
        if (!DataRepository.dataItemLive().getMimojiStatusManager2().isInMimojiEmoticon()) {
            String str = null;
            if (FileUtils.checkFileConsist(MimojiHelper2.VIDEO_DEAL_CACHE_FILE)) {
                str = MimojiHelper2.VIDEO_DEAL_CACHE_FILE;
            } else if (FileUtils.checkFileConsist(MimojiHelper2.VIDEO_NORMAL_CACHE_FILE)) {
                str = MimojiHelper2.VIDEO_NORMAL_CACHE_FILE;
            }
            if (!TextUtils.isEmpty(str)) {
                Log.d(TAG, "mimoji void updateMimojiVideoCache[]");
                String str2 = Storage.DIRECTORY + File.separator + FileUtils.createtFileName("MIMOJI_", "mp4");
                try {
                    FileUtils.copyFile(new File(str), new File(str2));
                    startSaveToLocal(str2);
                } catch (IOException e2) {
                    Log.e(TAG, "mimoji void updateMimojiVideoCache[] " + e2.getMessage());
                }
                deleteMimojiCache();
            }
        }
    }

    private void updatePictureAndPreviewSize() {
        List<CameraSize> supportedOutputSizeWithAssignedMode = ((BaseModule) this).mCameraCapabilities.getSupportedOutputSizeWithAssignedMode(SurfaceTexture.class);
        String pictureSizeRatioString = CameraSettings.getPictureSizeRatioString();
        boolean isGifOn = CameraSettings.isGifOn();
        CameraSize optimalPreviewSize = Util.getOptimalPreviewSize(false, ((BaseModule) this).mBogusCameraId, supportedOutputSizeWithAssignedMode, (double) Util.getRatio(pictureSizeRatioString), new CameraSize(1280, Util.LIMIT_SURFACE_WIDTH));
        if (isGifOn) {
            ((BaseModule) this).mPreviewSize = new CameraSize(500, 500);
        } else {
            ((BaseModule) this).mPreviewSize = optimalPreviewSize;
        }
        String str = TAG;
        Log.d(str, "previewSize: " + ((BaseModule) this).mPreviewSize.toString());
        CameraSize optimalVideoSnapshotPictureSize = Util.getOptimalVideoSnapshotPictureSize(supportedOutputSizeWithAssignedMode, (double) CameraSettings.getPreviewAspectRatio(16, 9), Util.sWindowHeight, Util.sWindowWidth);
        String str2 = TAG;
        Log.d(str2, "displaySize: " + optimalVideoSnapshotPictureSize.toString());
        if (isGifOn) {
            optimalPreviewSize = new CameraSize(500, 500);
        }
        updateGif();
        ((BaseModule) this).mCamera2Device.setAlgorithmPreviewSize(optimalPreviewSize);
        ((BaseModule) this).mCamera2Device.setAlgorithmPreviewFormat(35);
        updateCameraScreenNailSize(optimalPreviewSize.width, optimalPreviewSize.height);
    }

    private void updateUltraWideLDC() {
        ((BaseModule) this).mCamera2Device.setUltraWideLDC(shouldApplyUltraWideLDC());
    }

    private void updateVideoStabilization() {
        if (isDeviceAlive()) {
            if (isPreviewEisOn()) {
                Log.d(TAG, "videoStabilization: EIS");
                ((BaseModule) this).mCamera2Device.setEnableEIS(true);
                ((BaseModule) this).mCamera2Device.setEnableOIS(false);
                ((BaseModule) this).mActivity.getCameraScreenNail().setVideoStabilizationCropped(false);
                return;
            }
            Log.d(TAG, "videoStabilization: OIS");
            ((BaseModule) this).mCamera2Device.setEnableEIS(false);
            ((BaseModule) this).mCamera2Device.setEnableOIS(true);
            ((BaseModule) this).mActivity.getCameraScreenNail().setVideoStabilizationCropped(false);
        }
    }

    public /* synthetic */ void Cf() {
        ((BaseModule) this).mCamera2Device.setDeviceOrientation(((BaseModule) this).mOrientation);
    }

    public /* synthetic */ void Df() {
        Camera camera = ((BaseModule) this).mActivity;
        if (camera != null) {
            camera.restoreWindowBrightness();
        }
        ModeProtocol.FullScreenProtocol fullScreenProtocol = (ModeProtocol.FullScreenProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(196);
        String str = TAG;
        Log.d(str, "stopScreenLight: protocol = " + fullScreenProtocol + ", mHandler = " + ((BaseModule) this).mHandler);
        if (fullScreenProtocol != null) {
            fullScreenProtocol.hideScreenLight();
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
            } else if (i == 72) {
                updateGif();
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
            } else if (!(i == 42 || i == 43)) {
                if (i == 54) {
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
                                    switch (i) {
                                        case 46:
                                        case 48:
                                            continue;
                                        case 47:
                                            updateUltraWideLDC();
                                            continue;
                                        default:
                                            throw new RuntimeException("no consumer for this updateType: " + i);
                                    }
                            }
                    }
                } else {
                    updateModuleRelated();
                }
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
        return CameraCapabilities.SESSION_OPERATION_MODE_MIMOJI;
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
        return HybridZoomingSystem.IS_3_OR_MORE_SAT && (i = ((BaseModule) this).mModuleIndex) == 174 && !CameraSettings.isMacroModeEnabled(i) && isBackCamera();
    }

    @Override // com.android.camera.module.Module, com.android.camera.protocol.ModeProtocol.CameraAction
    public boolean isDoingAction() {
        MimojiModeProtocol.MimojiFullScreenProtocol mimojiFullScreenProtocol = (MimojiModeProtocol.MimojiFullScreenProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(249);
        return isRecording() || getCameraState() == 3 || (mimojiFullScreenProtocol != null && mimojiFullScreenProtocol.isMimojiRecordPreviewShowing());
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
        MimojiAvatarEngine2Impl mimojiAvatarEngine2Impl = this.mimojiAvatarEngine2;
        return mimojiAvatarEngine2Impl != null && mimojiAvatarEngine2Impl.isRecording();
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public boolean isSelectingCapturedResult() {
        MimojiModeProtocol.MimojiFullScreenProtocol mimojiFullScreenProtocol = (MimojiModeProtocol.MimojiFullScreenProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(249);
        return mimojiFullScreenProtocol != null && mimojiFullScreenProtocol.isMimojiRecordPreviewShowing();
    }

    @Override // com.android.camera.module.BaseModule, com.android.camera.ui.FocusView.ExposureViewListener
    public boolean isShowCaptureButton() {
        return isSupportFocusShoot();
    }

    @Override // com.android.camera.module.BaseModule, com.android.camera.ui.FocusView.ExposureViewListener
    public boolean isSupportFocusShoot() {
        return DataRepository.dataItemGlobal().isGlobalSwitchOn("pref_camera_focus_shoot_key");
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
        boolean z = getCameraState() != 3 && !isFrontCamera() && !DataRepository.dataItemLive().getMimojiStatusManager2().isInMimojiCreate() && isFrameAvailable();
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
            stopVideoRecording(true);
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
        if (this.mimojiAvatarEngine2 != null && DataRepository.dataItemLive().getMimojiStatusManager2().isInMimojiCreate()) {
            ((ModeProtocol.RecordState) ModeCoordinatorImpl.getInstance().getAttachProtocol(212)).onMimojiCreateBack();
            CameraStatUtils.trackMimojiClick(MistatsConstants.Mimoji.MIMOJI_CLICK_CREATE_SOFT_BACK, MistatsConstants.BaseEvent.CREATE);
            return true;
        } else if (isRecording()) {
            long currentTimeMillis = System.currentTimeMillis();
            if (currentTimeMillis - ((BaseModule) this).mLastBackPressedTime > 3000) {
                ((BaseModule) this).mLastBackPressedTime = currentTimeMillis;
                ToastUtils.showToast((Context) ((BaseModule) this).mActivity, (int) R.string.record_back_pressed_hint, true);
            } else {
                stopVideoRecording(true);
            }
            return true;
        } else if (!DataRepository.dataItemLive().getMimojiStatusManager2().isInMimojiEmoticon()) {
            return super.onBackPressed();
        } else {
            long currentTimeMillis2 = System.currentTimeMillis();
            if (currentTimeMillis2 - ((BaseModule) this).mLastBackPressedTime > 3000) {
                ((BaseModule) this).mLastBackPressedTime = currentTimeMillis2;
                ToastUtils.showToast((Context) ((BaseModule) this).mActivity, (int) R.string.preview_back_pressed_hint, true);
            } else {
                MimojiModeProtocol.MimojiEditor2.MimojiEmoticon mimojiEmoticon = (MimojiModeProtocol.MimojiEditor2.MimojiEmoticon) ModeCoordinatorImpl.getInstance().getAttachProtocol(250);
                if (mimojiEmoticon != null) {
                    mimojiEmoticon.backToPreview();
                }
            }
            return true;
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.BaseModule
    public void onCameraOpened() {
        super.onCameraOpened();
        initializeFocusManager();
        updatePreferenceTrampoline(UpdateConstant.FUN_TYPES_INIT);
        initMimojiEngine();
        startPreview();
        this.mOnResumeTime = SystemClock.uptimeMillis();
        ((BaseModule) this).mHandler.sendEmptyMessage(4);
        ((BaseModule) this).mHandler.sendEmptyMessage(31);
        initMetaParser();
        updateMimojiVideoCache();
    }

    @Override // com.android.camera2.Camera2Proxy.PictureCallback
    public void onCaptureShutter(boolean z) {
        setWaterMark();
        ((BaseModule) this).mActivity.getCameraScreenNail().animateCapture(((BaseModule) this).mOrientation);
        playCameraSound(0);
        this.mimojiAvatarEngine2.onCaptureImage();
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
        ((BaseModule) this).mHandler.post(a.INSTANCE);
        Handler handler = ((BaseModule) this).mHandler;
        if (handler != null) {
            handler.sendEmptyMessage(45);
        }
        ((BaseModule) this).mActivity.getSensorStateManager().setRotationVectorEnabled(false);
        ((BaseModule) this).mActivity.getSensorStateManager().setTTARSensorEnabled(false);
    }

    @Override // com.android.camera2.Camera2Proxy.FaceDetectionCallback
    public void onFaceDetected(CameraHardwareFace[] cameraHardwareFaceArr, FaceAnalyzeInfo faceAnalyzeInfo) {
        if (isCreated() && cameraHardwareFaceArr != null) {
            if (!b.cm() || cameraHardwareFaceArr.length <= 0 || cameraHardwareFaceArr[0].faceType != 64206) {
                if (!((BaseModule) this).mMainProtocol.setFaces(1, cameraHardwareFaceArr, getActiveArraySize(), getDeviceBasedZoomRatio())) {
                    return;
                }
            } else if (((BaseModule) this).mObjectTrackingStarted) {
                ((BaseModule) this).mMainProtocol.setFaces(3, cameraHardwareFaceArr, getActiveArraySize(), getDeviceBasedZoomRatio());
            }
            MimojiAvatarEngine2Impl mimojiAvatarEngine2Impl = this.mimojiAvatarEngine2;
            if (mimojiAvatarEngine2Impl != null && mimojiAvatarEngine2Impl.isOnCreateMimoji()) {
                ((BaseModule) this).mMainProtocol.lightingDetectFace(cameraHardwareFaceArr, true);
                this.misFaceLocationOk = ((BaseModule) this).mMainProtocol.isFaceLocationOK();
            }
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
            stopVideoRecording(true);
        }
        if (!isSaving()) {
            doLaterReleaseIfNeed();
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
                    if (isSelectingCapturedResult()) {
                        ((MimojiModeProtocol.MimojiFullScreenProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(249)).startMimojiRecordSaving();
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
                        stopVideoRecording(false);
                    }
                } else if (isRecording() && !isPostProcessing()) {
                    if (!isBackCamera()) {
                        return false;
                    }
                    stopVideoRecording(false);
                }
                return super.onKeyDown(i, keyEvent);
            }
        }
        if (!isSelectingCapturedResult()) {
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

    public void onMimojiCaptureCallback() {
        setCameraState(1);
        Camera2Proxy camera2Proxy = ((BaseModule) this).mCamera2Device;
        if (camera2Proxy != null) {
            camera2Proxy.onParallelImagePostProcStart();
        }
        if (isFrontCamera() && ((BaseModule) this).mCamera2Device.isNeedFlashOn()) {
            stopScreenLight();
        }
    }

    public void onMimojiCreateCompleted(boolean z) {
        setCameraState(1);
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void onNewUriArrived(Uri uri, String str) {
        MimojiModeProtocol.MimojiFullScreenProtocol mimojiFullScreenProtocol;
        super.onNewUriArrived(uri, str);
        if (isAlive() && (mimojiFullScreenProtocol = (MimojiModeProtocol.MimojiFullScreenProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(249)) != null) {
            mimojiFullScreenProtocol.onMimojiSaveToLocalFinished(uri);
        }
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void onOrientationChanged(int i, int i2, int i3) {
        setOrientation(i, i2);
        this.mDeviceOrientation = i;
        MimojiAvatarEngine2Impl mimojiAvatarEngine2Impl = this.mimojiAvatarEngine2;
        if (mimojiAvatarEngine2Impl != null) {
            mimojiAvatarEngine2Impl.onDeviceRotationChange(i);
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
        getActivity();
        tryRemoveCountDownMessage();
        ((BaseModule) this).mActivity.getSensorStateManager().reset();
        resetScreenOn();
        closeCamera();
        Handler handler = ((BaseModule) this).mHandler;
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
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

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void onPreviewPixelsRead(byte[] bArr, int i, int i2) {
        super.onPreviewPixelsRead(bArr, i, i2);
        Bitmap createBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
        createBitmap.copyPixelsFromBuffer(ByteBuffer.wrap(bArr));
        MimojiModeProtocol.MimojiFullScreenProtocol mimojiFullScreenProtocol = (MimojiModeProtocol.MimojiFullScreenProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(249);
        if (mimojiFullScreenProtocol != null) {
            mimojiFullScreenProtocol.setPreviewCover(createBitmap);
            String str = TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("mimoji void onPreviewPixelsRead[pixels, width, height] bitmap mPreviewCover");
            sb.append(createBitmap == null);
            Log.d(str, sb.toString());
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
        ((ModeProtocol.RecordState) ModeCoordinatorImpl.getInstance().getAttachProtocol(212)).onFinish();
        if (!doLaterReleaseIfNeed()) {
            if (((BaseModule) this).mCamera2Device.isSessionReady()) {
                resumePreview();
            } else {
                startPreview();
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.CameraAction
    public void onReviewDoneClicked() {
        Log.d(TAG, "mimoji void onReviewDoneClicked[]");
        ((ModeProtocol.RecordState) ModeCoordinatorImpl.getInstance().getAttachProtocol(212)).onFinish();
        startSaveToLocal(null);
        deleteMimojiCache();
        if (!doLaterReleaseIfNeed()) {
            if (((BaseModule) this).mCamera2Device.isSessionReady()) {
                resumePreview();
            } else {
                startPreview();
            }
        }
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
        if (isRecording()) {
            stopVideoRecording(false);
        } else if (!isDoingAction()) {
            if (this.mimojiAvatarEngine2 == null) {
                Log.e(TAG, "onShutterButtonClick  mimojiAvatarEngine2 NULL");
            } else if (DataRepository.dataItemLive().getMimojiStatusManager2().isInMimojiPreview()) {
                if (DataRepository.dataItemLive().getMimojiStatusManager2().getMimojiRecordState() == 1) {
                    ModeProtocol.BackStack backStack = (ModeProtocol.BackStack) ModeCoordinatorImpl.getInstance().getAttachProtocol(171);
                    if (backStack != null) {
                        backStack.handleBackStackFromShutter();
                    }
                    startVideoRecording();
                } else if (turnOnFlash()) {
                    onMimojiCapture();
                }
                HashMap hashMap = new HashMap();
                hashMap.put(MistatsConstants.Manual.PARAM_3A_LOCKED, Boolean.valueOf(this.m3ALocked));
                trackGeneralInfo(hashMap, 1, false, this.mBeautyValues, false, 0);
            } else if (DataRepository.dataItemLive().getMimojiStatusManager2().isInMimojiCreate()) {
                Log.d(TAG, "start create mimoji");
                if (this.mIsLowLight) {
                    Log.d(TAG, "mimoji create low light!");
                } else if (this.mimojiAvatarEngine2.onCreateCapture()) {
                    Log.d(TAG, "create mimoji success");
                    setCameraState(3);
                }
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
        ModeProtocol.BackStack backStack;
        if (!((BaseModule) this).mPaused && !this.mDisableSingleTapUp && ((BaseModule) this).mCamera2Device != null && !hasCameraException() && ((BaseModule) this).mCamera2Device.isSessionReady() && isInTapableRect(i, i2) && getCameraState() != 3 && getCameraState() != 0) {
            if (!isFrameAvailable()) {
                Log.w(TAG, "onSingleTapUp: frame not available");
            } else if ((!isFrontCamera() || !((BaseModule) this).mActivity.isScreenSlideOff()) && (backStack = (ModeProtocol.BackStack) ModeCoordinatorImpl.getInstance().getAttachProtocol(171)) != null && !backStack.handleBackStackFromTapDown(i, i2)) {
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
        if (isRecording()) {
            stopVideoRecording(true);
            onReviewCancelClicked();
        }
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
            if ((i == 2 || i == 1) && !CameraSettings.isMacroModeEnabled(((BaseModule) this).mModuleIndex)) {
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

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void preTransferOrientation(int i, int i2) {
        super.preTransferOrientation(i, i2);
        this.mDeviceOrientation = i;
    }

    @Override // com.android.camera.protocol.ModeProtocol.BaseProtocol, com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void registerProtocol() {
        super.registerProtocol();
        ModeCoordinatorImpl.getInstance().attachProtocol(161, this);
        ModeCoordinatorImpl.getInstance().attachProtocol(167, this);
        ModeCoordinatorImpl.getInstance().attachProtocol(169, this);
        ModeCoordinatorImpl.getInstance().attachProtocol(167, this);
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

    public void setCameraStatePublic(int i) {
        setCameraState(i);
    }

    public void setDisableSingleTapUp(boolean z) {
        this.mDisableSingleTapUp = z;
    }

    @Override // com.android.camera.module.loader.camera2.FocusManager2.Listener
    public boolean shouldCaptureDirectly() {
        return false;
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public boolean shouldReleaseLater() {
        return isRecording() || isSelectingCapturedResult();
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
            ((BaseModule) this).mCamera2Device.setPictureSize(((BaseModule) this).mPictureSize);
            ((BaseModule) this).mCamera2Device.setPreviewSize(((BaseModule) this).mPreviewSize);
            this.mQuality = Util.convertSizeToQuality(((BaseModule) this).mPreviewSize);
            ((BaseModule) this).mSurfaceCreatedTimestamp = ((BaseModule) this).mActivity.getCameraScreenNail().getSurfaceCreatedTimestamp();
            Log.d(TAG, "MimojiModule, startPreview");
            checkDisplayOrientation();
            isFrontCamera();
            Surface surface = new Surface(((BaseModule) this).mActivity.getCameraScreenNail().getSurfaceTexture());
            ((BaseModule) this).mActivity.getCameraScreenNail().setExternalFrameProcessor(this.mimojiAvatarEngine2);
            if (!isSelectingCapturedResult()) {
                ((BaseModule) this).mCamera2Device.startPreviewSession(surface, true, false, false, getOperatingMode(), false, this);
            }
        }
    }

    public void startSaveToLocal(String str) {
        MimojiAvatarEngine2Impl mimojiAvatarEngine2Impl = this.mimojiAvatarEngine2;
        if (mimojiAvatarEngine2Impl == null || mimojiAvatarEngine2Impl.isRecordStopping()) {
            Log.e(TAG, "mimoji void startSaveToLocal[] error");
            return;
        }
        Log.d(TAG, "mimoji void startSaveToLocal[]");
        if (TextUtils.isEmpty(str)) {
            str = ((MimojiModeProtocol.MimojiFullScreenProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(249)).getMimojiVideoSavePath();
        }
        if (TextUtils.isEmpty(str)) {
            Log.e(TAG, "mimoji void startSaveToLocal[videoSavePath] null");
            return;
        }
        CameraSize cameraSize = ((BaseModule) this).mPreviewSize;
        ContentValues genContentValues = Util.genContentValues(2, str, cameraSize.width, cameraSize.height);
        genContentValues.put("datetaken", Long.valueOf(System.currentTimeMillis()));
        getActivity().getImageSaver().addVideo(genContentValues.getAsString("_data"), genContentValues, true);
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

    public void stopVideoRecording(boolean z) {
        this.mTelephonyManager.listen(((BaseModule) this).mPhoneStateListener, 0);
        Log.v(TAG, "listen none");
        if (z) {
            this.mimojiAvatarEngine2.onRecordStop(z);
            onReviewCancelClicked();
            return;
        }
        MimojiModeProtocol.MimojiVideoEditor mimojiVideoEditor = this.mMimojiVideoEditor;
        CameraSize cameraSize = ((BaseModule) this).mPreviewSize;
        mimojiVideoEditor.setRecordParameter(cameraSize.height, cameraSize.width, ((BaseModule) this).mOrientation);
        if (System.currentTimeMillis() - this.mRecordTime >= 500) {
            this.mimojiAvatarEngine2.onRecordStop(z);
            showPreview();
        }
    }

    public boolean turnOnFlash() {
        if (!((BaseModule) this).mCamera2Device.isNeedFlashOn() || 101 != ((BaseModule) this).mCamera2Device.getFlashMode()) {
            return true;
        }
        return startScreenLight(Util.getScreenLightColor(SystemProperties.getInt("camera_screen_light_wb", 0)), CameraSettings.getScreenLightBrightness());
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void unRegisterModulePersistProtocol() {
        super.unRegisterModulePersistProtocol();
        getActivity().getImplFactory().detachModulePersistent();
    }

    @Override // com.android.camera.protocol.ModeProtocol.BaseProtocol, com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void unRegisterProtocol() {
        super.unRegisterProtocol();
        ModeCoordinatorImpl.getInstance().detachProtocol(161, this);
        ModeCoordinatorImpl.getInstance().detachProtocol(167, this);
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
