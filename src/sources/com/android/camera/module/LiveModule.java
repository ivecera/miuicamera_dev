package com.android.camera.module;

import a.a.a;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
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
import android.os.SystemProperties;
import android.support.annotation.MainThread;
import android.telephony.TelephonyManager;
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
import com.android.camera.fragment.CtaNoticeFragment;
import com.android.camera.fragment.beauty.BeautyValues;
import com.android.camera.fragment.beauty.LiveBeautyFilterFragment;
import com.android.camera.log.Log;
import com.android.camera.module.impl.component.MimojiAvatarEngineImpl;
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
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class LiveModule extends BaseModule implements ILiveModule, Camera2Proxy.CameraPreviewCallback, Camera2Proxy.FocusCallback, Camera2Proxy.FaceDetectionCallback, ModeProtocol.CameraAction, Camera2Proxy.PictureCallback, FocusManager2.Listener {
    private static final int BEAUTY_SWITCH = 8;
    private static final int FILTER_SWITCH = 2;
    private static final int FRAME_RATE = 30;
    private static final int STICKER_SWITCH = 4;
    /* access modifiers changed from: private */
    public static final String TAG = "LiveModule";
    private static final int VALID_VIDEO_TIME = 500;
    private boolean m3ALocked;
    private String mBaseFileName;
    protected BeautyValues mBeautyValues;
    private String mCaptureWaterMarkStr;
    private CtaNoticeFragment mCtaNoticeFragment;
    private int mDeviceOrientation = 90;
    private AlertDialog mDialog;
    private boolean mDisableSingleTapUp = false;
    protected boolean mFaceDetectionEnabled;
    protected boolean mFaceDetectionStarted;
    /* access modifiers changed from: private */
    public FocusManager2 mFocusManager;
    private boolean mIsLowLight;
    private boolean mIsPreviewing = false;
    /* access modifiers changed from: private */
    public ModeProtocol.LiveConfigChanges mLiveConfigChanges;
    private ModeProtocol.LiveVideoEditor mLiveVideoEditor;
    private int mMessageId;
    private Disposable mMetaDataDisposable;
    /* access modifiers changed from: private */
    public FlowableEmitter<CaptureResult> mMetaDataFlowableEmitter;
    private MimojiAvatarEngineImpl mMimojiAvatarEngine;
    private int mOldOriginVolumeStream;
    /* access modifiers changed from: private */
    public long mOnResumeTime;
    private boolean mOpenFlash = false;
    private int mQuality = 5;
    private boolean mSaved;
    protected SensorStateManager.SensorStateListener mSensorStateListener = new SensorStateManager.SensorStateListener() {
        /* class com.android.camera.module.LiveModule.AnonymousClass5 */

        @Override // com.android.camera.SensorStateManager.SensorStateListener
        public boolean isWorking() {
            return LiveModule.this.isAlive() && LiveModule.this.getCameraState() != 0;
        }

        @Override // com.android.camera.SensorStateManager.SensorStateListener
        public void notifyDevicePostureChanged() {
            ((BaseModule) LiveModule.this).mActivity.getEdgeShutterView().onDevicePostureChanged();
        }

        @Override // com.android.camera.SensorStateManager.SensorStateListener
        public void onDeviceBecomeStable() {
            Log.v(LiveModule.TAG, "onDeviceBecomeStable");
        }

        @Override // com.android.camera.SensorStateManager.SensorStateListener
        public void onDeviceBeginMoving() {
        }

        @Override // com.android.camera.SensorStateManager.SensorStateListener
        public void onDeviceKeepMoving(double d2) {
            if (!((BaseModule) LiveModule.this).mMainProtocol.isEvAdjusted(true) && !((BaseModule) LiveModule.this).mPaused && Util.isTimeout(System.currentTimeMillis(), LiveModule.this.mTouchFocusStartingTime, 3000) && !LiveModule.this.is3ALocked() && LiveModule.this.mFocusManager != null && LiveModule.this.mFocusManager.isNeedCancelAutoFocus() && !LiveModule.this.isRecording()) {
                LiveModule.this.mFocusManager.onDeviceKeepMoving(d2);
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
            if (LiveModule.this.mLiveConfigChanges != null) {
                LiveModule.this.mLiveConfigChanges.onDeviceRotationChange(fArr);
            }
        }

        @Override // com.android.camera.SensorStateManager.SensorStateListener
        public void onSensorChanged(SensorEvent sensorEvent) {
            if (LiveModule.this.mLiveConfigChanges != null) {
                LiveModule.this.mLiveConfigChanges.onSensorChanged(sensorEvent);
            }
        }
    };
    protected boolean mShowFace = false;
    protected TelephonyManager mTelephonyManager;
    private int mTitleId;
    /* access modifiers changed from: private */
    public long mTouchFocusStartingTime;
    private volatile boolean mVideoRecordStopped = false;
    private boolean misFaceLocationOk;

    private class LiveAsdConsumer implements Consumer<Integer> {
        private LiveAsdConsumer() {
        }

        public void accept(Integer num) throws Exception {
            LiveModule.this.mimojiLightDetect(num);
        }
    }

    private static class MainHandler extends Handler {
        private WeakReference<LiveModule> mModule;

        public MainHandler(LiveModule liveModule, Looper looper) {
            super(looper);
            this.mModule = new WeakReference<>(liveModule);
        }

        public void handleMessage(Message message) {
            LiveModule liveModule = this.mModule.get();
            if (liveModule != null) {
                if (!liveModule.isCreated()) {
                    removeCallbacksAndMessages(null);
                } else if (liveModule.getActivity() != null) {
                    int i = message.what;
                    if (i == 2) {
                        liveModule.getWindow().clearFlags(128);
                    } else if (i == 4) {
                        liveModule.checkActivityOrientation();
                        if (SystemClock.uptimeMillis() - liveModule.mOnResumeTime < 5000) {
                            sendEmptyMessageDelayed(4, 100);
                        }
                    } else if (i == 9) {
                        ((BaseModule) liveModule).mMainProtocol.initializeFocusView(liveModule);
                    } else if (i == 17) {
                        removeMessages(17);
                        removeMessages(2);
                        liveModule.getWindow().addFlags(128);
                        sendEmptyMessageDelayed(2, (long) liveModule.getScreenDelay());
                    } else if (i == 31) {
                        liveModule.setOrientationParameter();
                    } else if (i == 35) {
                        boolean z = false;
                        boolean z2 = message.arg1 > 0;
                        if (message.arg2 > 0) {
                            z = true;
                        }
                        liveModule.handleUpdateFaceView(z2, z);
                    } else if (i == 51 && liveModule.getActivity() != null && liveModule.getActivity().isActivityPaused()) {
                        ((BaseModule) liveModule).mOpenCameraFail = true;
                        liveModule.onCameraException();
                    }
                }
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
        Camera2Proxy camera2Proxy;
        boolean isFrontCamera = isFrontCamera();
        if (!z) {
            ((BaseModule) this).mMainProtocol.updateFaceView(z, z2, isFrontCamera, false, -1);
        } else if (this.mFaceDetectionStarted && (camera2Proxy = ((BaseModule) this).mCamera2Device) != null && 1 != camera2Proxy.getFocusMode()) {
            ((BaseModule) this).mMainProtocol.updateFaceView(z, true, isFrontCamera, true, ((BaseModule) this).mCameraDisplayOrientation);
        }
    }

    private int initLiveConfig() {
        this.mLiveConfigChanges = (ModeProtocol.LiveConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(201);
        this.mLiveVideoEditor = (ModeProtocol.LiveVideoEditor) ModeCoordinatorImpl.getInstance().getAttachProtocol(209);
        if (this.mLiveConfigChanges == null) {
            getActivity().getImplFactory().initModulePersistent(getActivity(), 201, 209);
            this.mLiveConfigChanges = (ModeProtocol.LiveConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(201);
            this.mLiveVideoEditor = (ModeProtocol.LiveVideoEditor) ModeCoordinatorImpl.getInstance().getAttachProtocol(209);
            this.mLiveConfigChanges.initResource();
        }
        return this.mLiveConfigChanges.getAuthResult();
    }

    private void initMetaParser() {
        this.mMetaDataDisposable = Flowable.create(new FlowableOnSubscribe<CaptureResult>() {
            /* class com.android.camera.module.LiveModule.AnonymousClass2 */

            @Override // io.reactivex.FlowableOnSubscribe
            public void subscribe(FlowableEmitter<CaptureResult> flowableEmitter) throws Exception {
                FlowableEmitter unused = LiveModule.this.mMetaDataFlowableEmitter = flowableEmitter;
            }
        }, BackpressureStrategy.DROP).map(new FunctionParseAsdFace(this, isFrontCamera())).map(new FunctionParseAsdScene(this)).subscribe(new LiveAsdConsumer());
    }

    private void initMimojiEngine() {
        this.mMimojiAvatarEngine = (MimojiAvatarEngineImpl) ModeCoordinatorImpl.getInstance().getAttachProtocol(217);
        if (this.mMimojiAvatarEngine == null) {
            getActivity().getImplFactory().initModulePersistent(getActivity(), 217);
            this.mMimojiAvatarEngine = (MimojiAvatarEngineImpl) ModeCoordinatorImpl.getInstance().getAttachProtocol(217);
            this.mMimojiAvatarEngine.onDeviceRotationChange(this.mDeviceOrientation);
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
        ModeProtocol.FullScreenProtocol fullScreenProtocol = (ModeProtocol.FullScreenProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(196);
        if (fullScreenProtocol == null) {
            return false;
        }
        return fullScreenProtocol.isLiveRecordSaving();
    }

    /* access modifiers changed from: private */
    public void mimojiLightDetect(Integer num) {
        MimojiAvatarEngineImpl mimojiAvatarEngineImpl = this.mMimojiAvatarEngine;
        if (mimojiAvatarEngineImpl != null && mimojiAvatarEngineImpl.isOnCreateMimoji()) {
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
                GlobalConstant.sCameraSetupScheduler.scheduleDirect(new v(this));
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

    private void showAuthError() {
        ((BaseModule) this).mHandler.post(new Runnable() {
            /* class com.android.camera.module.LiveModule.AnonymousClass1 */

            public void run() {
                Camera camera = ((BaseModule) LiveModule.this).mActivity;
                if (camera != null && !camera.isFinishing()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(((BaseModule) LiveModule.this).mActivity);
                    builder.setTitle(R.string.live_error_title);
                    builder.setMessage(R.string.live_error_message);
                    builder.setCancelable(false);
                    builder.setPositiveButton(R.string.live_error_confirm, new DialogInterface.OnClickListener() {
                        /* class com.android.camera.module.LiveModule.AnonymousClass1.AnonymousClass1 */

                        public void onClick(DialogInterface dialogInterface, int i) {
                            ((BaseModule) LiveModule.this).mActivity.startActivity(new Intent("android.settings.DATE_SETTINGS"));
                        }
                    });
                    builder.setNegativeButton(R.string.snap_cancel, new DialogInterface.OnClickListener() {
                        /* class com.android.camera.module.LiveModule.AnonymousClass1.AnonymousClass2 */

                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });
                    builder.show();
                }
            }
        });
    }

    private void showPreview() {
        pausePreview();
        this.mSaved = false;
        ((ModeProtocol.FullScreenProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(196)).startLiveRecordPreview(genContentValues(2, 0, false));
        this.mIsPreviewing = true;
    }

    private void startScreenLight(final int i, final int i2) {
        if (!((BaseModule) this).mPaused) {
            ((BaseModule) this).mHandler.post(new Runnable() {
                /* class com.android.camera.module.LiveModule.AnonymousClass3 */

                public void run() {
                    Camera camera = ((BaseModule) LiveModule.this).mActivity;
                    if (camera != null) {
                        camera.setWindowBrightness(i2);
                    }
                    ModeProtocol.FullScreenProtocol fullScreenProtocol = (ModeProtocol.FullScreenProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(196);
                    if (fullScreenProtocol != null) {
                        fullScreenProtocol.setScreenLightColor(i);
                        fullScreenProtocol.showScreenLight();
                    }
                }
            });
        }
    }

    @MainThread
    private void startVideoRecording() {
        keepScreenOn();
        ModeProtocol.RecordState recordState = (ModeProtocol.RecordState) ModeCoordinatorImpl.getInstance().getAttachProtocol(212);
        recordState.onPrepare();
        ModeProtocol.LiveConfigChanges liveConfigChanges = this.mLiveConfigChanges;
        if (liveConfigChanges != null) {
            liveConfigChanges.onRecordStart();
            DataRepository.dataItemLive().setLiveStartOrientation(((BaseModule) this).mOrientation);
            CameraStatUtils.trackLiveClick(MistatsConstants.Live.LIVE_CLICK_START);
            trackLiveRecordingParams();
            this.mOldOriginVolumeStream = getActivity().getVolumeControlStream();
            getActivity().setVolumeControlStream(3);
        }
        recordState.onStart();
        if (HybridZoomingSystem.IS_3_OR_MORE_SAT && isBackCamera()) {
            ((BaseModule) this).mPreZoomRation = getZoomRatio();
            updateZoomRatioToggleButtonState(true);
            if (isUltraWideBackCamera()) {
                setMinZoomRatio(0.6f);
                setMaxZoomRatio(2.0f);
            } else if (isAuxCamera()) {
                setMinZoomRatio(HybridZoomingSystem.FLOAT_ZOOM_RATIO_TELE);
                setMaxZoomRatio(Math.min(6.0f, ((BaseModule) this).mCameraCapabilities.getMaxZoomRatio()));
            } else {
                setMinZoomRatio(1.0f);
                setMaxZoomRatio(((BaseModule) this).mModuleIndex == 177 ? ((BaseModule) this).mCameraCapabilities.getMaxZoomRatio() : Math.min(6.0f, ((BaseModule) this).mCameraCapabilities.getMaxZoomRatio()));
            }
        }
        this.mTelephonyManager.listen(((BaseModule) this).mPhoneStateListener, 32);
        Log.v(TAG, "listen call state");
        if (this.mMimojiAvatarEngine != null) {
            this.mMimojiAvatarEngine.onRecordStart(genContentValues(2, 0, false));
        }
        this.mVideoRecordStopped = false;
        HashMap hashMap = new HashMap();
        hashMap.put(MistatsConstants.Manual.PARAM_3A_LOCKED, Boolean.valueOf(this.m3ALocked));
        trackGeneralInfo(hashMap, 1, false, this.mBeautyValues, false, -1);
    }

    private void stopScreenLight() {
        ((BaseModule) this).mHandler.post(new Runnable() {
            /* class com.android.camera.module.LiveModule.AnonymousClass4 */

            public void run() {
                Camera camera = ((BaseModule) LiveModule.this).mActivity;
                if (camera != null) {
                    camera.restoreWindowBrightness();
                }
                ModeProtocol.FullScreenProtocol fullScreenProtocol = (ModeProtocol.FullScreenProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(196);
                String access$600 = LiveModule.TAG;
                Log.d(access$600, "stopScreenLight: protocol = " + fullScreenProtocol + ", mHandler = " + ((BaseModule) LiveModule.this).mHandler);
                if (fullScreenProtocol != null) {
                    fullScreenProtocol.hideScreenLight();
                }
            }
        });
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

    private void trackLiveVideoParams() {
        int segmentSize = this.mLiveConfigChanges.getSegmentSize();
        float totalRecordingTime = (float) this.mLiveConfigChanges.getTotalRecordingTime();
        int liveAllSwitchAllValue = CameraSettings.getLiveAllSwitchAllValue();
        boolean z = true;
        boolean z2 = (liveAllSwitchAllValue & 2) != 0;
        boolean z3 = (liveAllSwitchAllValue & 4) != 0;
        if ((liveAllSwitchAllValue & 8) == 0) {
            z = false;
        }
        CameraSettings.setLiveAllSwitchAddValue(0);
        CameraStatUtils.trackLiveVideoParams(segmentSize, totalRecordingTime / 1000.0f, z2, z3, z);
    }

    private void updateBeauty() {
        if (((BaseModule) this).mModuleIndex != 174) {
            if (this.mBeautyValues == null) {
                this.mBeautyValues = new BeautyValues();
            }
            CameraSettings.initBeautyValues(this.mBeautyValues, ((BaseModule) this).mModuleIndex);
            String str = TAG;
            Log.d(str, "updateBeauty(): " + this.mBeautyValues);
            ((BaseModule) this).mCamera2Device.setBeautyValues(this.mBeautyValues);
            return;
        }
        float faceBeautyRatio = ((float) CameraSettings.getFaceBeautyRatio("key_live_shrink_face_ratio")) / 100.0f;
        float faceBeautyRatio2 = ((float) CameraSettings.getFaceBeautyRatio("key_live_enlarge_eye_ratio")) / 100.0f;
        float faceBeautyRatio3 = ((float) CameraSettings.getFaceBeautyRatio("key_live_smooth_strength")) / 100.0f;
        if (faceBeautyRatio > 0.0f || faceBeautyRatio2 > 0.0f || faceBeautyRatio3 > 0.0f) {
            CameraSettings.setLiveBeautyStatus(true);
            ModeProtocol.LiveConfigChanges liveConfigChanges = this.mLiveConfigChanges;
            if (liveConfigChanges != null) {
                liveConfigChanges.setBeautyFaceReshape(true, faceBeautyRatio2, faceBeautyRatio);
                this.mLiveConfigChanges.setBeautify(true, faceBeautyRatio3);
            }
        } else {
            CameraSettings.setLiveBeautyStatus(false);
            ModeProtocol.LiveConfigChanges liveConfigChanges2 = this.mLiveConfigChanges;
            if (liveConfigChanges2 != null) {
                liveConfigChanges2.setBeautyFaceReshape(false, faceBeautyRatio2, faceBeautyRatio);
                this.mLiveConfigChanges.setBeautify(false, faceBeautyRatio3);
            }
        }
        String str2 = TAG;
        Log.d(str2, "shrinkFaceRatio->" + faceBeautyRatio + ",enlargeEyeRatio->" + faceBeautyRatio2 + ",smoothStrengthRatio->" + faceBeautyRatio3);
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
        int i = ((BaseModule) this).mModuleIndex;
        if (i == 177 || i == 184) {
            MimojiAvatarEngineImpl mimojiAvatarEngineImpl = this.mMimojiAvatarEngine;
            int i2 = ((BaseModule) this).mCameraDisplayOrientation;
            int i3 = isFrontCamera() ? 270 : 90;
            CameraSize cameraSize = ((BaseModule) this).mPreviewSize;
            mimojiAvatarEngineImpl.initAvatarEngine(i2, i3, cameraSize.width, cameraSize.height, isFrontCamera());
            ((BaseModule) this).mCamera2Device.startPreviewCallback(this.mMimojiAvatarEngine);
        }
    }

    private void updatePictureAndPreviewSize() {
        List<CameraSize> supportedOutputSizeWithAssignedMode = ((BaseModule) this).mCameraCapabilities.getSupportedOutputSizeWithAssignedMode(SurfaceTexture.class);
        CameraSize optimalPreviewSize = Util.getOptimalPreviewSize(false, ((BaseModule) this).mBogusCameraId, supportedOutputSizeWithAssignedMode, (double) (((BaseModule) this).mModuleIndex == 177 ? Util.getRatio(CameraSettings.getPictureSizeRatioString()) : 1.7777777f), new CameraSize(1280, Util.LIMIT_SURFACE_WIDTH));
        ((BaseModule) this).mPreviewSize = optimalPreviewSize;
        ((BaseModule) this).mPictureSize = ((BaseModule) this).mPreviewSize;
        String str = TAG;
        Log.d(str, "previewSize: " + ((BaseModule) this).mPreviewSize.toString());
        CameraSize optimalVideoSnapshotPictureSize = Util.getOptimalVideoSnapshotPictureSize(supportedOutputSizeWithAssignedMode, (double) CameraSettings.getPreviewAspectRatio(16, 9), Util.sWindowHeight, Util.sWindowWidth);
        String str2 = TAG;
        Log.d(str2, "displaySize: " + optimalVideoSnapshotPictureSize.toString());
        int i = ((BaseModule) this).mModuleIndex;
        if (i == 177) {
            ((BaseModule) this).mCamera2Device.setAlgorithmPreviewSize(optimalPreviewSize);
            ((BaseModule) this).mCamera2Device.setAlgorithmPreviewFormat(35);
            updateCameraScreenNailSize(optimalPreviewSize.width, optimalPreviewSize.height);
        } else if (i == 174) {
            updateCameraScreenNailSize(optimalVideoSnapshotPictureSize.height, optimalVideoSnapshotPictureSize.width);
        } else if (i == 184) {
            ((BaseModule) this).mCamera2Device.setAlgorithmPreviewSize(optimalPreviewSize);
            ((BaseModule) this).mCamera2Device.setAlgorithmPreviewFormat(35);
            updateCameraScreenNailSize(optimalPreviewSize.width, optimalPreviewSize.height);
        }
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

    public /* synthetic */ void Qf() {
        ((BaseModule) this).mCamera2Device.setDeviceOrientation(((BaseModule) this).mOrientation);
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
            if (((BaseModule) this).mModuleIndex == 177) {
                ((BaseModule) this).mCamera2Device.stopPreviewCallback(true);
            }
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

    @Override // com.android.camera.module.ILiveModule
    public void doReverse() {
        ModeProtocol.LiveConfigChanges liveConfigChanges = this.mLiveConfigChanges;
        if (liveConfigChanges != null && !liveConfigChanges.isRecording()) {
            this.mLiveConfigChanges.onRecordReverse();
            if (!this.mLiveConfigChanges.canRecordingStop()) {
                ModeProtocol.BackStack backStack = (ModeProtocol.BackStack) ModeCoordinatorImpl.getInstance().getAttachProtocol(171);
                if (backStack != null) {
                    backStack.handleBackStackFromKeyBack();
                }
                stopVideoRecording(true, false);
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
        return ((BaseModule) this).mModuleIndex == 177 ? CameraCapabilities.SESSION_OPERATION_MODE_MIMOJI : isPreviewEisOn() ? 32772 : DataRepository.dataItemFeature().s_m_c_t_f() ? CameraCapabilities.SESSION_OPERATION_MODE_MCTF : 0;
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
        ModeProtocol.LiveConfigChanges liveConfigChanges;
        return HybridZoomingSystem.IS_3_OR_MORE_SAT && (i = ((BaseModule) this).mModuleIndex) == 174 && !CameraSettings.isMacroModeEnabled(i) && isBackCamera() && (liveConfigChanges = this.mLiveConfigChanges) != null && !liveConfigChanges.isRecording();
    }

    @Override // com.android.camera.module.Module, com.android.camera.protocol.ModeProtocol.CameraAction
    public boolean isDoingAction() {
        ModeProtocol.FullScreenProtocol fullScreenProtocol = (ModeProtocol.FullScreenProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(196);
        return isRecording() || getCameraState() == 3 || (fullScreenProtocol != null && fullScreenProtocol.isLiveRecordPreviewShown()) || this.mOpenFlash;
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
        MimojiAvatarEngineImpl mimojiAvatarEngineImpl;
        ModeProtocol.LiveConfigChanges liveConfigChanges = this.mLiveConfigChanges;
        return (liveConfigChanges != null && liveConfigChanges.isRecording()) || ((mimojiAvatarEngineImpl = this.mMimojiAvatarEngine) != null && mimojiAvatarEngineImpl.isRecording());
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public boolean isSelectingCapturedResult() {
        ModeProtocol.FullScreenProtocol fullScreenProtocol = (ModeProtocol.FullScreenProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(196);
        return fullScreenProtocol != null && fullScreenProtocol.isLiveRecordPreviewShown();
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
        if (!isFrameAvailable()) {
            if (isSelectingCapturedResult()) {
                return true;
            }
            if (DataRepository.dataItemLive().getRecordSegmentTimeInfo() == null) {
                return false;
            }
            Log.d(TAG, "onBackPressed skip caz recorder paused.");
            return true;
        } else if (this.mMimojiAvatarEngine == null || !DataRepository.dataItemLive().getMimojiStatusManager().IsInMimojiCreate()) {
            ModeProtocol.LiveConfigChanges liveConfigChanges = this.mLiveConfigChanges;
            if (liveConfigChanges == null) {
                return false;
            }
            if (((BaseModule) this).mModuleIndex == 177) {
                if (isRecording()) {
                    stopVideoRecording(true, true);
                }
                return true;
            } else if (!liveConfigChanges.isRecording() && !this.mLiveConfigChanges.isRecordingPaused()) {
                return super.onBackPressed();
            } else {
                long currentTimeMillis = System.currentTimeMillis();
                if (currentTimeMillis - ((BaseModule) this).mLastBackPressedTime > 3000) {
                    ((BaseModule) this).mLastBackPressedTime = currentTimeMillis;
                    ToastUtils.showToast((Context) ((BaseModule) this).mActivity, (int) R.string.record_back_pressed_hint, true);
                } else {
                    stopVideoRecording(true, false);
                }
                return true;
            }
        } else {
            ((ModeProtocol.RecordState) ModeCoordinatorImpl.getInstance().getAttachProtocol(212)).onMimojiCreateBack();
            CameraStatUtils.trackMimojiClick(MistatsConstants.Mimoji.MIMOJI_CLICK_CREATE_SOFT_BACK, MistatsConstants.BaseEvent.CREATE);
            return true;
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.BaseModule
    public void onCameraOpened() {
        super.onCameraOpened();
        initializeFocusManager();
        updatePreferenceTrampoline(UpdateConstant.FUN_TYPES_INIT);
        int i = ((BaseModule) this).mModuleIndex;
        if (i == 174) {
            int initLiveConfig = initLiveConfig();
            if (!(initLiveConfig == 0 || initLiveConfig == 1 || (initLiveConfig != 2 && initLiveConfig != 3 && initLiveConfig != 4))) {
                showAuthError();
                return;
            }
        } else if (i == 177) {
            initMimojiEngine();
        }
        startPreview();
        if (DataRepository.dataItemGlobal().isFirstShowCTAConCollect() && ((BaseModule) this).mModuleIndex == 174) {
            this.mCtaNoticeFragment = CtaNoticeFragment.showCta(getActivity().getFragmentManager(), false, null, 1);
        }
        this.mOnResumeTime = SystemClock.uptimeMillis();
        ((BaseModule) this).mHandler.sendEmptyMessage(4);
        ((BaseModule) this).mHandler.sendEmptyMessage(31);
        initMetaParser();
    }

    @Override // com.android.camera2.Camera2Proxy.PictureCallback
    public void onCaptureShutter(boolean z) {
        setWaterMark();
        ((BaseModule) this).mActivity.getCameraScreenNail().animateCapture(((BaseModule) this).mOrientation);
        playCameraSound(0);
        this.mMimojiAvatarEngine.onCaptureImage();
    }

    @Override // com.android.camera2.Camera2Proxy.PictureCallback
    public ParallelTaskData onCaptureStart(ParallelTaskData parallelTaskData, CameraSize cameraSize, boolean z) {
        return null;
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void onCreate(int i, int i2) {
        super.onCreate(i, i2);
        ((BaseModule) this).mActivity.getSensorStateManager().setSensorStateListener(this.mSensorStateListener);
        ((BaseModule) this).mHandler = new MainHandler(this, ((BaseModule) this).mActivity.getMainLooper());
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
        ((BaseModule) this).mHandler.post(u.INSTANCE);
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
            MimojiAvatarEngineImpl mimojiAvatarEngineImpl = this.mMimojiAvatarEngine;
            if (mimojiAvatarEngineImpl != null && mimojiAvatarEngineImpl.isOnCreateMimoji()) {
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
            onPauseButtonClick();
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
                        ((ModeProtocol.FullScreenProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(196)).startLiveRecordSaving();
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

    public void onMimojiCaptureCallback() {
        setCameraState(1);
        Camera2Proxy camera2Proxy = ((BaseModule) this).mCamera2Device;
        if (camera2Proxy != null) {
            camera2Proxy.onParallelImagePostProcStart();
        }
        if (this.mOpenFlash) {
            this.mOpenFlash = false;
            if (isFrontCamera()) {
                stopScreenLight();
            }
        }
    }

    public void onMimojiCreateCompleted(boolean z) {
        setCameraState(1);
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void onNewUriArrived(final Uri uri, final String str) {
        super.onNewUriArrived(uri, str);
        if (isAlive()) {
            ((BaseModule) this).mHandler.post(new Runnable() {
                /* class com.android.camera.module.LiveModule.AnonymousClass6 */

                public void run() {
                    ContentValues saveContentValues;
                    ModeProtocol.FullScreenProtocol fullScreenProtocol = (ModeProtocol.FullScreenProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(196);
                    if (fullScreenProtocol != null && (saveContentValues = fullScreenProtocol.getSaveContentValues()) != null) {
                        String asString = saveContentValues.getAsString("title");
                        String access$600 = LiveModule.TAG;
                        Log.d(access$600, "newUri: " + str + " | " + asString);
                        if (asString.equals(str)) {
                            fullScreenProtocol.onLiveSaveToLocalFinished(uri);
                        }
                    }
                }
            });
        }
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void onOrientationChanged(int i, int i2, int i3) {
        setOrientation(i, i2);
        if (this.mLiveConfigChanges != null) {
            if (i <= 45 || i > 315) {
                i = 0;
            }
            if (i > 45 && i <= 135) {
                i = 90;
            }
            if (i > 135 && i <= 225) {
                i = 180;
            }
            if (i > 225) {
                i = 270;
            }
            this.mLiveConfigChanges.updateRotation(0.0f, 0.0f, (float) i);
        }
        this.mDeviceOrientation = i;
        MimojiAvatarEngineImpl mimojiAvatarEngineImpl = this.mMimojiAvatarEngine;
        if (mimojiAvatarEngineImpl != null) {
            mimojiAvatarEngineImpl.onDeviceRotationChange(i);
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

    @Override // com.android.camera.module.ILiveModule
    public void onPauseButtonClick() {
        if (((BaseModule) this).mModuleIndex == 177) {
            if (isRecording()) {
                stopVideoRecording(true, true);
            }
        } else if (!this.mVideoRecordStopped) {
            ModeProtocol.RecordState recordState = (ModeProtocol.RecordState) ModeCoordinatorImpl.getInstance().getAttachProtocol(212);
            if (this.mLiveConfigChanges.isRecording()) {
                this.mLiveConfigChanges.onRecordPause();
                if (HybridZoomingSystem.IS_3_OR_MORE_SAT && isBackCamera()) {
                    updateZoomRatioToggleButtonState(false);
                    setMinZoomRatio(0.6f);
                    setMaxZoomRatio(((BaseModule) this).mCameraCapabilities.getMaxZoomRatio() * 0.6f);
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
                } else if (isAuxCamera()) {
                    setMinZoomRatio(HybridZoomingSystem.FLOAT_ZOOM_RATIO_TELE);
                    setMaxZoomRatio(Math.min(6.0f, ((BaseModule) this).mCameraCapabilities.getMaxZoomRatio()));
                } else {
                    setMinZoomRatio(1.0f);
                    setMaxZoomRatio(Math.min(6.0f, ((BaseModule) this).mCameraCapabilities.getMaxZoomRatio()));
                }
            }
            trackLiveRecordingParams();
            this.mLiveConfigChanges.onRecordResume();
            if (recordState != null) {
                recordState.onResume();
            }
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
        ModeProtocol.LiveConfigChanges liveConfigChanges = this.mLiveConfigChanges;
        if (liveConfigChanges != null) {
            liveConfigChanges.onRecordStop();
        }
        ((ModeProtocol.RecordState) ModeCoordinatorImpl.getInstance().getAttachProtocol(212)).onFinish();
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
        ModeProtocol.LiveConfigChanges liveConfigChanges = this.mLiveConfigChanges;
        if (liveConfigChanges != null) {
            liveConfigChanges.onRecordStop();
        }
        ((ModeProtocol.RecordState) ModeCoordinatorImpl.getInstance().getAttachProtocol(212)).onFinish();
        getActivity().setVolumeControlStream(this.mOldOriginVolumeStream);
        this.mIsPreviewing = false;
        startSaveToLocal();
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
        ModeProtocol.LiveConfigChanges liveConfigChanges;
        if (isRecording() || ((liveConfigChanges = this.mLiveConfigChanges) != null && liveConfigChanges.isRecordingPaused())) {
            stopVideoRecording(true, false);
        } else if (((BaseModule) this).mModuleIndex != 177 && !checkCallingState()) {
            Log.d(TAG, "ignore in calling state");
            ModeProtocol.RecordState recordState = (ModeProtocol.RecordState) ModeCoordinatorImpl.getInstance().getAttachProtocol(212);
            recordState.onPrepare();
            recordState.onFailed();
        } else if (this.mMimojiAvatarEngine == null) {
            startVideoRecording();
            ModeProtocol.BackStack backStack = (ModeProtocol.BackStack) ModeCoordinatorImpl.getInstance().getAttachProtocol(171);
            if (backStack != null) {
                backStack.handleBackStackFromShutter();
            }
        } else if (DataRepository.dataItemLive().getMimojiStatusManager().IsInMimojiPreview()) {
            turnOnFlashIfNeed();
            onMimojiCapture();
            HashMap hashMap = new HashMap();
            hashMap.put(MistatsConstants.Manual.PARAM_3A_LOCKED, Boolean.valueOf(this.m3ALocked));
            trackGeneralInfo(hashMap, 1, false, this.mBeautyValues, false, 0);
        } else if (DataRepository.dataItemLive().getMimojiStatusManager().IsInMimojiCreate()) {
            Log.d(TAG, "start create mimoji");
            if (this.mIsLowLight) {
                Log.d(TAG, "mimoji create low light!");
            } else if (this.mMimojiAvatarEngine.onCreateCapture()) {
                Log.d(TAG, "create mimoji success");
                setCameraState(3);
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.CameraAction
    public void onShutterButtonFocus(boolean z, int i) {
    }

    @Override // com.android.camera.protocol.ModeProtocol.CameraAction
    public boolean onShutterButtonLongClick() {
        MimojiAvatarEngineImpl mimojiAvatarEngineImpl;
        if (!checkCallingState()) {
            Log.d(TAG, "ignore onShutterButtonLongClick in calling state");
            ModeProtocol.RecordState recordState = (ModeProtocol.RecordState) ModeCoordinatorImpl.getInstance().getAttachProtocol(212);
            recordState.onPrepare();
            recordState.onFailed();
            return false;
        } else if (isRecording() || (mimojiAvatarEngineImpl = this.mMimojiAvatarEngine) == null || mimojiAvatarEngineImpl.isOnCreateMimoji()) {
            return false;
        } else {
            startVideoRecording();
            return true;
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.CameraAction
    public void onShutterButtonLongClickCancel(boolean z) {
        MimojiAvatarEngineImpl mimojiAvatarEngineImpl;
        if (isRecording() && (mimojiAvatarEngineImpl = this.mMimojiAvatarEngine) != null && !mimojiAvatarEngineImpl.isRecordStopping()) {
            stopVideoRecording(true, false);
        }
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
        ModeProtocol.LiveConfigChanges liveConfigChanges;
        super.onThermalConstrained();
        if (isRecording() || ((liveConfigChanges = this.mLiveConfigChanges) != null && liveConfigChanges.isRecordingPaused())) {
            stopVideoRecording(true, false);
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
            if (i == 2 || i == 1) {
                ModeProtocol.LiveConfigChanges liveConfigChanges = this.mLiveConfigChanges;
                if ((liveConfigChanges == null || (!liveConfigChanges.isRecording() && !this.mLiveConfigChanges.isRecordingPaused())) && !CameraSettings.isMacroModeEnabled(((BaseModule) this).mModuleIndex)) {
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
        ModeProtocol.LiveConfigChanges liveConfigChanges = this.mLiveConfigChanges;
        if (liveConfigChanges != null) {
            liveConfigChanges.setEffectAudio(false);
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
        ModeProtocol.LiveConfigChanges liveConfigChanges = this.mLiveConfigChanges;
        if (liveConfigChanges != null) {
            liveConfigChanges.setEffectAudio(true);
        }
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
            Log.d(TAG, "LiveModule, startPreview");
            checkDisplayOrientation();
            int i = isFrontCamera() ? 270 : 90;
            Surface surface = new Surface(((BaseModule) this).mActivity.getCameraScreenNail().getSurfaceTexture());
            Surface surface2 = null;
            boolean z = false;
            int i2 = ((BaseModule) this).mModuleIndex;
            if (i2 == 174) {
                surface2 = new Surface(this.mLiveConfigChanges.getInputSurfaceTexture());
                ModeProtocol.LiveConfigChanges liveConfigChanges = this.mLiveConfigChanges;
                CameraSize cameraSize = ((BaseModule) this).mPreviewSize;
                liveConfigChanges.initPreview(cameraSize.height, cameraSize.width, isFrontCamera(), i);
                ModeProtocol.LiveVideoEditor liveVideoEditor = this.mLiveVideoEditor;
                CameraSize cameraSize2 = ((BaseModule) this).mPreviewSize;
                liveVideoEditor.setRecordParameter(cameraSize2.height, cameraSize2.width, DataRepository.dataItemLive().getLiveStartOrientation());
                this.mLiveConfigChanges.startPreview(surface);
            } else if (i2 == 177) {
                ((BaseModule) this).mActivity.getCameraScreenNail().setExternalFrameProcessor(this.mMimojiAvatarEngine);
                z = true;
            }
            if (((BaseModule) this).mModuleIndex == 174) {
                ((BaseModule) this).mCamera2Device.setFpsRange(new Range<>(30, 30));
            }
            if (!isSelectingCapturedResult()) {
                ((BaseModule) this).mCamera2Device.startPreviewSession(surface2 == null ? surface : surface2, z, false, false, getOperatingMode(), false, this);
            }
        }
    }

    public void startSaveToLocal() {
        ContentValues saveContentValues;
        if (!this.mSaved && (saveContentValues = ((ModeProtocol.FullScreenProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(196)).getSaveContentValues()) != null) {
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
            if (this.mMimojiAvatarEngine != null) {
                recordState.onFinish();
                this.mMimojiAvatarEngine.onRecordStop(z2);
                return;
            }
            this.mLiveConfigChanges.onRecordPause();
            int liveStartOrientation = DataRepository.dataItemLive().getLiveStartOrientation();
            ModeProtocol.LiveVideoEditor liveVideoEditor = this.mLiveVideoEditor;
            CameraSize cameraSize = ((BaseModule) this).mPreviewSize;
            liveVideoEditor.setRecordParameter(cameraSize.height, cameraSize.width, liveStartOrientation);
            boolean canRecordingStop = this.mLiveConfigChanges.canRecordingStop();
            boolean z3 = this.mLiveConfigChanges.getTotalRecordingTime() < 500;
            if (!canRecordingStop) {
                Log.d(TAG, "onFinish of no segments !!");
                this.mLiveConfigChanges.onRecordStop();
                recordState.onFinish();
            } else if (z3) {
                String str = TAG;
                Log.d(str, "Discard , total capture time is :" + this.mLiveConfigChanges.getTotalRecordingTime());
                this.mLiveConfigChanges.onRecordStop();
                recordState.onFinish();
            } else {
                recordState.onPostPreview();
                this.mTelephonyManager.listen(((BaseModule) this).mPhoneStateListener, 0);
                Log.v(TAG, "listen none");
                trackLiveVideoParams();
                CameraStatUtils.trackVideoRecorded(isFrontCamera(), getActualCameraId(), getModuleIndex(), false, false, CameraSettings.isUltraWideConfigOpen(getModuleIndex()), "live", this.mQuality, ((BaseModule) this).mCamera2Device.getFlashMode(), 30, 0, null, this.mLiveConfigChanges.getTotalRecordingTime() / 1000, false);
                if (!z2) {
                    showPreview();
                }
            }
            boolean z4 = z2 || !canRecordingStop || z3;
            onInterceptZoomingEvent(((BaseModule) this).mPreZoomRation, getZoomRatio(), -1);
            if (z4 && HybridZoomingSystem.IS_3_OR_MORE_SAT && isBackCamera()) {
                updateZoomRatioToggleButtonState(false);
                if (isUltraWideBackCamera()) {
                    setMinZoomRatio(0.6f);
                    setMaxZoomRatio(((BaseModule) this).mCameraCapabilities.getMaxZoomRatio() * 0.6f);
                } else {
                    setMinZoomRatio(0.6f);
                    setMaxZoomRatio(Math.min(6.0f, ((BaseModule) this).mCameraCapabilities.getMaxZoomRatio()));
                }
            }
            this.mVideoRecordStopped = true;
        }
    }

    public void turnOnFlashIfNeed() {
        if (((BaseModule) this).mCamera2Device.isNeedFlashOn()) {
            this.mOpenFlash = true;
            if (101 == ((BaseModule) this).mCamera2Device.getFlashMode()) {
                startScreenLight(Util.getScreenLightColor(SystemProperties.getInt("camera_screen_light_wb", 0)), CameraSettings.getScreenLightBrightness());
            }
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
        ModeCoordinatorImpl.getInstance().detachProtocol(167, this);
        ModeCoordinatorImpl.getInstance().detachProtocol(169, this);
        ModeCoordinatorImpl.getInstance().detachProtocol(167, this);
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
