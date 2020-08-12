package com.android.camera.module;

import a.a.a;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.hardware.SensorEvent;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.params.MeteringRectangle;
import android.location.Location;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.text.TextUtils;
import android.util.Range;
import android.util.Size;
import android.view.KeyEvent;
import android.view.Surface;
import android.view.View;
import com.android.camera.BasePreferenceActivity;
import com.android.camera.Camera;
import com.android.camera.CameraAppImpl;
import com.android.camera.CameraIntentManager;
import com.android.camera.CameraPreferenceActivity;
import com.android.camera.CameraSettings;
import com.android.camera.CameraSize;
import com.android.camera.EncodingQuality;
import com.android.camera.HybridZoomingSystem;
import com.android.camera.LocalParallelService;
import com.android.camera.LocationManager;
import com.android.camera.Manifest;
import com.android.camera.PictureSizeManager;
import com.android.camera.R;
import com.android.camera.SensorStateManager;
import com.android.camera.Thumbnail;
import com.android.camera.ToastUtils;
import com.android.camera.Util;
import com.android.camera.aiwatermark.data.WatermarkItem;
import com.android.camera.aiwatermark.util.WatermarkConstant;
import com.android.camera.constant.AutoFocus;
import com.android.camera.constant.BeautyConstant;
import com.android.camera.constant.CameraScene;
import com.android.camera.constant.GlobalConstant;
import com.android.camera.constant.UpdateConstant;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.config.ComponentConfigBokeh;
import com.android.camera.data.data.config.ComponentConfigFlash;
import com.android.camera.data.data.config.ComponentConfigHdr;
import com.android.camera.data.data.config.ComponentRunningMacroMode;
import com.android.camera.data.data.config.ComponentRunningUltraPixel;
import com.android.camera.data.data.config.SupportedConfigFactory;
import com.android.camera.data.data.global.DataItemGlobal;
import com.android.camera.data.data.runing.ComponentRunningShine;
import com.android.camera.data.data.runing.ComponentRunningTiltValue;
import com.android.camera.data.data.runing.DataItemRunning;
import com.android.camera.effect.EffectController;
import com.android.camera.effect.FaceAnalyzeInfo;
import com.android.camera.effect.FilterInfo;
import com.android.camera.effect.draw_mode.DrawExtTexAttribute;
import com.android.camera.effect.renders.DeviceWatermarkParam;
import com.android.camera.fragment.GoogleLensFragment;
import com.android.camera.fragment.beauty.BeautyValues;
import com.android.camera.fragment.top.FragmentTopConfig;
import com.android.camera.lib.compatibility.related.vibrator.ViberatorContext;
import com.android.camera.lib.compatibility.util.CompatibilityUtils;
import com.android.camera.log.Log;
import com.android.camera.module.impl.component.CameraClickObservableImpl;
import com.android.camera.module.loader.FunctionParseAiScene;
import com.android.camera.module.loader.FunctionParseAsdFace;
import com.android.camera.module.loader.FunctionParseAsdHdr;
import com.android.camera.module.loader.FunctionParseAsdLivePhoto;
import com.android.camera.module.loader.FunctionParseAsdScene;
import com.android.camera.module.loader.FunctionParseAsdUltraWide;
import com.android.camera.module.loader.FunctionParseBeautyBodySlimCount;
import com.android.camera.module.loader.FunctionParseSuperNight;
import com.android.camera.module.loader.PredicateFilterAiScene;
import com.android.camera.module.loader.camera2.Camera2DataContainer;
import com.android.camera.module.loader.camera2.FocusManager2;
import com.android.camera.module.loader.camera2.FocusTask;
import com.android.camera.module.loader.camera2.ParallelSnapshotManager;
import com.android.camera.parallel.AlgoConnector;
import com.android.camera.preferences.CameraSettingPreferences;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera.scene.FunctionMiAlgoASDEngine;
import com.android.camera.scene.MiAlgoAsdSceneProfile;
import com.android.camera.statistic.CameraStatUtils;
import com.android.camera.statistic.MistatsConstants;
import com.android.camera.statistic.MistatsWrapper;
import com.android.camera.statistic.ScenarioTrackUtil;
import com.android.camera.storage.ImageSaver;
import com.android.camera.storage.Storage;
import com.android.camera.ui.ObjectView;
import com.android.camera.ui.RotateTextToast;
import com.android.camera.ui.zoom.ZoomingAction;
import com.android.camera.watermark.WaterMarkData;
import com.android.camera2.Camera2Proxy;
import com.android.camera2.CameraCapabilities;
import com.android.camera2.CameraConfigs;
import com.android.camera2.CameraHardwareFace;
import com.android.camera2.CaptureResultParser;
import com.android.camera2.vendortag.struct.MarshalQueryableASDScene;
import com.android.camera2.vendortag.struct.MarshalQueryableSuperNightExif;
import com.android.gallery3d.exif.ExifHelper;
import com.android.lens.LensAgent;
import com.android.zxing.PreviewDecodeManager;
import com.google.android.apps.photos.api.PhotosOemApi;
import com.mi.config.b;
import com.xiaomi.camera.base.CameraDeviceUtil;
import com.xiaomi.camera.base.PerformanceTracker;
import com.xiaomi.camera.core.BaseBoostFramework;
import com.xiaomi.camera.core.MtkBoost;
import com.xiaomi.camera.core.ParallelDataZipper;
import com.xiaomi.camera.core.ParallelTaskData;
import com.xiaomi.camera.core.ParallelTaskDataParameter;
import com.xiaomi.camera.core.PictureInfo;
import com.xiaomi.camera.liveshot.CircularMediaRecorder;
import com.xiaomi.camera.liveshot.LivePhotoResult;
import com.xiaomi.engine.BufferFormat;
import com.xiaomi.engine.GraphDescriptorBean;
import com.xiaomi.protocol.ISessionStatusCallBackListener;
import com.xiaomi.stat.d;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

@TargetApi(21)
public class Camera2Module extends BaseModule implements FocusManager2.Listener, ObjectView.ObjectViewListener, Camera2Proxy.CameraMetaDataCallback, ModeProtocol.CameraAction, ModeProtocol.TopConfigProtocol, Camera2Proxy.CameraPreviewCallback, Camera2Proxy.HdrCheckerCallback, Camera2Proxy.ScreenLightCallback, Camera2Proxy.PictureCallback, Camera2Proxy.FaceDetectionCallback, Camera2Proxy.FocusCallback, Camera2Proxy.BeautyBodySlimCountCallback, Camera2Proxy.SuperNightCallback, Camera2Proxy.LivePhotoResultCallback, Camera2Proxy.MagneticDetectedCallback {
    private static final int BURST_SHOOTING_DELAY = 0;
    private static final long CAPTURE_DURATION_THRESHOLD = 12000;
    private static final boolean DEBUG_ENABLE_DYNAMIC_HHT_FAST_SHOT = SystemProperties.getBoolean("debug.vendor.camera.app.dynamic.hht.quickshot.enable", true);
    private static final float MOON_AF_DISTANCE = 0.5f;
    private static final int REQUEST_CROP = 1000;
    /* access modifiers changed from: private */
    public static final String TAG = "Camera2Module";
    private static final int UW_MAX_BURST_SHOT_NUM = 30;
    private static boolean mIsBeautyFrontOn = false;
    private static final String sTempCropFilename = "crop-temp";
    /* access modifiers changed from: private */
    public float[] curGyroscope;
    private volatile boolean isDetectedInHdr;
    /* access modifiers changed from: private */
    public volatile boolean isResetFromMutex = false;
    private boolean isSilhouette;
    /* access modifiers changed from: private */
    public float[] lastGyroscope;
    private boolean m3ALocked;
    private float mAECLux;
    private int mAFEndLogTimes;
    private boolean mAIWatermarkEnable = false;
    private Disposable mAiSceneDisposable;
    private boolean mAiSceneEnabled;
    /* access modifiers changed from: private */
    public FlowableEmitter<CaptureResult> mAiSceneFlowableEmitter;
    /* access modifiers changed from: private */
    public String mAlgorithmName;
    private float[] mApertures;
    private MarshalQueryableASDScene.ASDScene[] mAsdScenes;
    private BeautyValues mBeautyValues;
    private boolean mBlockQuickShot = (!CameraSettings.isCameraQuickShotEnable());
    private Intent mBroadcastIntent;
    /* access modifiers changed from: private */
    public Disposable mBurstDisposable;
    /* access modifiers changed from: private */
    public ObservableEmitter mBurstEmitter;
    private long mBurstNextDelayTime = 0;
    /* access modifiers changed from: private */
    public long mBurstStartTime;
    private ModeProtocol.CameraClickObservable.ClickObserver mCameraClickObserverAction = new ModeProtocol.CameraClickObservable.ClickObserver() {
        /* class com.android.camera.module.Camera2Module.AnonymousClass1 */

        @Override // com.android.camera.protocol.ModeProtocol.CameraClickObservable.ClickObserver
        public void action() {
            ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
            if (bottomPopupTips != null) {
                bottomPopupTips.directlyHideTips();
            }
            ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
            if (topAlert != null) {
                topAlert.alertAiDetectTipHint(8, 0, 0);
            }
        }

        @Override // com.android.camera.protocol.ModeProtocol.CameraClickObservable.ClickObserver
        public int getObserver() {
            return 161;
        }
    };
    private final Object mCameraDeviceLock = new Object();
    private long mCaptureStartTime;
    private String mCaptureWaterMarkStr;
    private CircularMediaRecorder mCircularMediaRecorder = null;
    private final Object mCircularMediaRecorderStateLock = new Object();
    private boolean mConfigRawStream;
    /* access modifiers changed from: private */
    public Disposable mCountdownDisposable;
    private BaseBoostFramework mCpuBoost;
    private String mCropValue;
    private int mCurrentAiScene;
    /* access modifiers changed from: private */
    public int mCurrentAsdScene = -1;
    private int mCurrentDetectedScene;
    /* access modifiers changed from: private */
    public boolean mEnableParallelSession;
    private boolean mEnableShot2Gallery;
    private boolean mEnabledPreviewThumbnail;
    private boolean mEnteringMoonMode;
    protected boolean mFaceDetected;
    private boolean mFaceDetectionEnabled;
    private boolean mFaceDetectionStarted;
    private FaceAnalyzeInfo mFaceInfo;
    private int mFilterId;
    private int mFixedShot2ShotTime = -1;
    private float[] mFocalLengths;
    /* access modifiers changed from: private */
    public FocusManager2 mFocusManager;
    private FunctionParseAiScene mFunctionParseAiScene;
    private boolean mHasAiSceneFilterEffect;
    private boolean mHdrCheckEnabled;
    private String[] mIDCardPaths = new String[2];
    private boolean mIsAiConflict;
    private boolean mIsBeautyBodySlimOn;
    private boolean mIsCurrentLensEnabled;
    private boolean mIsFaceConflict;
    private boolean mIsGenderAgeOn;
    private volatile boolean mIsGoogleLensAvailable;
    private boolean mIsISORight4HWMFNR = false;
    /* access modifiers changed from: private */
    public boolean mIsImageCaptureIntent;
    private boolean mIsInHDR;
    private boolean mIsLLSNeeded;
    private boolean mIsMacroModeEnable;
    private boolean mIsMagicMirrorOn;
    private boolean mIsMicrophoneEnabled = true;
    /* access modifiers changed from: private */
    public boolean mIsMoonMode;
    private boolean mIsPortraitLightingOn;
    private boolean mIsSaveCaptureImage;
    /* access modifiers changed from: private */
    public int mIsShowLyingDirectHintStatus = -1;
    private boolean mIsTheShutterTime = false;
    private boolean mIsUltraWideConflict;
    /* access modifiers changed from: private */
    public int mJpegRotation;
    private boolean mKeepBitmapTexture;
    private long mLastAsdSceneShowTime = 0;
    private long mLastCaptureTime;
    private long mLastChangeSceneTime = 0;
    private String mLastFlashMode;
    private String mLastHdrMode;
    private Queue<LivePhotoResult> mLivePhotoQueue = new LinkedBlockingQueue(120);
    private boolean mLiveShotEnabled;
    /* access modifiers changed from: private */
    public Location mLocation;
    private boolean mLongPressedAutoFocus;
    private ModeProtocol.MagneticSensorDetect mMagneticSensorDetect;
    private Disposable mMetaDataDisposable;
    /* access modifiers changed from: private */
    public FlowableEmitter<CaptureResult> mMetaDataFlowableEmitter;
    private boolean mMotionDetected;
    /* access modifiers changed from: private */
    public boolean mMultiSnapStatus = false;
    /* access modifiers changed from: private */
    public boolean mMultiSnapStopRequest = false;
    private boolean mNeedAutoFocus;
    /* access modifiers changed from: private */
    public long mOnResumeTime;
    private int mOperatingMode;
    private boolean mParallelSessionConfigured = false;
    private final Object mParallelSessionLock = new Object();
    private boolean mPendingMultiCapture;
    private MarshalQueryableSuperNightExif.SuperNightExif mPreviewSuperNightExifInfo;
    private boolean mQuickCapture;
    private boolean mQuickShotAnimateEnable = false;
    /* access modifiers changed from: private */
    public int mReceivedJpegCallbackNum = 0;
    private Uri mSaveUri;
    private String mSceneMode;
    private SensorStateManager.SensorStateListener mSensorStateListener = new SensorStateManager.SensorStateListener() {
        /* class com.android.camera.module.Camera2Module.AnonymousClass14 */
        private ModeProtocol.TopAlert mTopAlert;

        @Override // com.android.camera.SensorStateManager.SensorStateListener
        public boolean isWorking() {
            return Camera2Module.this.isAlive() && Camera2Module.this.getCameraState() != 0;
        }

        @Override // com.android.camera.SensorStateManager.SensorStateListener
        public void notifyDevicePostureChanged() {
        }

        @Override // com.android.camera.SensorStateManager.SensorStateListener
        public void onDeviceBecomeStable() {
        }

        @Override // com.android.camera.SensorStateManager.SensorStateListener
        public void onDeviceBeginMoving() {
            if (!((BaseModule) Camera2Module.this).mPaused && CameraSettings.isEdgePhotoEnable()) {
                ((BaseModule) Camera2Module.this).mActivity.getEdgeShutterView().onDeviceMoving();
            }
        }

        @Override // com.android.camera.SensorStateManager.SensorStateListener
        public void onDeviceKeepMoving(double d2) {
            if (!((BaseModule) Camera2Module.this).mPaused && Camera2Module.this.mFocusManager != null && !Camera2Module.this.mMultiSnapStatus && !Camera2Module.this.is3ALocked() && !((BaseModule) Camera2Module.this).mMainProtocol.isEvAdjusted(true) && !Camera2Module.this.mIsMoonMode) {
                Camera2Module.this.mFocusManager.onDeviceKeepMoving(d2);
            }
        }

        @Override // com.android.camera.SensorStateManager.SensorStateListener
        public void onDeviceKeepStable() {
        }

        /* JADX WARN: Failed to insert an additional move for type inference into block B:11:0x0044 */
        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r6v0, types: [boolean, int] */
        /* JADX WARN: Type inference failed for: r6v1 */
        /* JADX WARN: Type inference failed for: r6v7 */
        @Override // com.android.camera.SensorStateManager.SensorStateListener
        public void onDeviceLieChanged(boolean z) {
            if (!((BaseModule) Camera2Module.this).mPaused) {
                int access$4300 = Camera2Module.this.mIsShowLyingDirectHintStatus;
                Camera2Module camera2Module = Camera2Module.this;
                int i = ((BaseModule) camera2Module).mOrientationCompensation;
                int i2 = z == true ? 1 : 0;
                int i3 = z == true ? 1 : 0;
                if (access$4300 != i2 + i) {
                    int unused = camera2Module.mIsShowLyingDirectHintStatus = i + z;
                    ((BaseModule) Camera2Module.this).mHandler.removeMessages(58);
                    if (this.mTopAlert == null) {
                        this.mTopAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
                    }
                    ModeProtocol.TopAlert topAlert = this.mTopAlert;
                    if (topAlert != null ? topAlert.isContainAlertRecommendTip(R.string.dirty_tip_toast, R.string.pic_flaw_blink_one, R.string.pic_flaw_blink_more, R.string.pic_flaw_cover) : false) {
                        z = 0;
                    }
                    if (z != 0) {
                        Camera2Module camera2Module2 = Camera2Module.this;
                        Handler handler = ((BaseModule) camera2Module2).mHandler;
                        handler.sendMessageDelayed(handler.obtainMessage(58, 1, ((BaseModule) camera2Module2).mOrientationCompensation), 400);
                        Camera2Module camera2Module3 = Camera2Module.this;
                        Handler handler2 = ((BaseModule) camera2Module3).mHandler;
                        handler2.sendMessageDelayed(handler2.obtainMessage(58, 0, ((BaseModule) camera2Module3).mOrientationCompensation), 5000);
                        return;
                    }
                    Camera2Module camera2Module4 = Camera2Module.this;
                    Handler handler3 = ((BaseModule) camera2Module4).mHandler;
                    handler3.sendMessageDelayed(handler3.obtainMessage(58, 0, ((BaseModule) camera2Module4).mOrientationCompensation), 500);
                }
            }
        }

        @Override // com.android.camera.SensorStateManager.SensorStateListener
        public void onDeviceOrientationChanged(float f2, boolean z) {
            Camera2Module camera2Module = Camera2Module.this;
            ((BaseModule) camera2Module).mDeviceRotation = !z ? f2 : (float) ((BaseModule) camera2Module).mOrientation;
            if (Camera2Module.this.getCameraState() != 3 || CameraSettings.isGradienterOn()) {
                EffectController instance = EffectController.getInstance();
                Camera2Module camera2Module2 = Camera2Module.this;
                instance.setDeviceRotation(z, Util.getShootRotation(((BaseModule) camera2Module2).mActivity, ((BaseModule) camera2Module2).mDeviceRotation));
            }
            ((BaseModule) Camera2Module.this).mHandler.removeMessages(33);
            if (!((BaseModule) Camera2Module.this).mPaused && !z && f2 != -1.0f) {
                int roundOrientation = Util.roundOrientation(Math.round(f2), ((BaseModule) Camera2Module.this).mOrientation);
                ((BaseModule) Camera2Module.this).mHandler.obtainMessage(33, roundOrientation, (Util.getDisplayRotation(((BaseModule) Camera2Module.this).mActivity) + roundOrientation) % 360).sendToTarget();
            }
        }

        @Override // com.android.camera.SensorStateManager.SensorStateListener
        public void onDeviceRotationChanged(float[] fArr) {
        }

        @Override // com.android.camera.SensorStateManager.SensorStateListener
        public void onSensorChanged(SensorEvent sensorEvent) {
            ModeProtocol.MagneticSensorDetect magneticSensorDetect;
            int type = sensorEvent.sensor.getType();
            if (type == 4) {
                Camera2Module camera2Module = Camera2Module.this;
                float[] unused = camera2Module.lastGyroscope = camera2Module.curGyroscope;
                float[] unused2 = Camera2Module.this.curGyroscope = sensorEvent.values;
            } else if (type == 14 && (magneticSensorDetect = (ModeProtocol.MagneticSensorDetect) ModeCoordinatorImpl.getInstance().getAttachProtocol(2576)) != null) {
                magneticSensorDetect.onMagneticSensorChanged(sensorEvent);
            }
        }
    };
    private LocalParallelService.ServiceStatusListener mServiceStatusListener;
    private ISessionStatusCallBackListener mSessionStatusCallbackListener = new ISessionStatusCallBackListener.Stub() {
        /* class com.android.camera.module.Camera2Module.AnonymousClass22 */

        @Override // com.xiaomi.protocol.ISessionStatusCallBackListener
        public void onSessionStatusFlawResultData(int i, final int i2) throws RemoteException {
            String access$400 = Camera2Module.TAG;
            Log.d(access$400, "resultId:" + i + ",flawResult:" + i2);
            if (1.0f == Camera2Module.this.getZoomRatio() && !CameraSettings.isMacroModeEnabled(Camera2Module.this.getModuleIndex())) {
                ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
                if (bottomPopupTips == null || !bottomPopupTips.isQRTipVisible()) {
                    final FragmentTopConfig fragmentTopConfig = (FragmentTopConfig) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
                    if (fragmentTopConfig == null || !fragmentTopConfig.isCurrentRecommendTipText(R.string.super_night_hint)) {
                        Camera2Proxy camera2Proxy = ((BaseModule) Camera2Module.this).mCamera2Device;
                        if (camera2Proxy == null || !camera2Proxy.isCaptureBusy(true)) {
                            AndroidSchedulers.mainThread().scheduleDirect(new Runnable() {
                                /* class com.android.camera.module.Camera2Module.AnonymousClass22.AnonymousClass1 */

                                public void run() {
                                    FragmentTopConfig fragmentTopConfig;
                                    int i = i2;
                                    if (i == 0) {
                                        return;
                                    }
                                    if (i == 1) {
                                        FragmentTopConfig fragmentTopConfig2 = fragmentTopConfig;
                                        if (fragmentTopConfig2 != null) {
                                            fragmentTopConfig2.alertAiDetectTipHint(0, R.string.pic_flaw_cover, 3000);
                                        }
                                    } else if (i == 2) {
                                        FragmentTopConfig fragmentTopConfig3 = fragmentTopConfig;
                                        if (fragmentTopConfig3 != null) {
                                            fragmentTopConfig3.alertAiDetectTipHint(0, R.string.pic_flaw_blink_one, 3000);
                                        }
                                    } else if (i == 3 && (fragmentTopConfig = fragmentTopConfig) != null) {
                                        fragmentTopConfig.alertAiDetectTipHint(0, R.string.pic_flaw_blink_more, 3000);
                                    }
                                }
                            });
                        }
                    }
                }
            }
        }
    };
    /* access modifiers changed from: private */
    public int mShootOrientation;
    /* access modifiers changed from: private */
    public float mShootRotation;
    private boolean mShouldDoMFNR;
    private boolean mShowLLSHint;
    private boolean mShowSuperNightHint;
    private long mShutterCallbackTime;
    private long mShutterLag;
    private Disposable mSuperNightDisposable;
    private Consumer<Integer> mSuperNightEventConsumer;
    /* access modifiers changed from: private */
    public int mTotalJpegCallbackNum = 1;
    private volatile boolean mUltraWideAELocked;
    /* access modifiers changed from: private */
    public boolean mUpdateImageTitle = false;
    private CameraSize mVideoSize;
    private boolean mVolumeLongPress = false;
    /* access modifiers changed from: private */
    public volatile boolean mWaitSaveFinish;
    /* access modifiers changed from: private */
    public volatile boolean mWaitingSnapshot;
    /* access modifiers changed from: private */
    public boolean mWaitingSuperNightResult;

    private static class AsdSceneConsumer implements Consumer<Integer> {
        private WeakReference<BaseModule> mModule;

        public AsdSceneConsumer(BaseModule baseModule) {
            this.mModule = new WeakReference<>(baseModule);
        }

        public void accept(Integer num) throws Exception {
            WeakReference<BaseModule> weakReference = this.mModule;
            if (weakReference != null && weakReference.get() != null) {
                BaseModule baseModule = this.mModule.get();
                if (baseModule instanceof Camera2Module) {
                    ((Camera2Module) baseModule).consumeAsdSceneResult(num.intValue());
                }
            }
        }
    }

    private final class JpegQuickPictureCallback extends Camera2Proxy.PictureCallbackWrapper {
        String mBurstShotTitle;
        boolean mDropped;
        Location mLocation;
        String mPressDownTitle;
        int mSavedJpegCallbackNum;

        public JpegQuickPictureCallback(Location location) {
            this.mLocation = location;
        }

        private String getBurstShotTitle() {
            String str;
            String str2;
            if (Camera2Module.this.mUpdateImageTitle && (str2 = this.mBurstShotTitle) != null && this.mSavedJpegCallbackNum == 1) {
                this.mPressDownTitle = str2;
                this.mBurstShotTitle = null;
            }
            if (this.mBurstShotTitle == null) {
                long currentTimeMillis = System.currentTimeMillis();
                this.mBurstShotTitle = Util.createJpegName(currentTimeMillis);
                if (this.mBurstShotTitle.length() != 19) {
                    this.mBurstShotTitle = Util.createJpegName(currentTimeMillis + 1000);
                }
            }
            StringBuilder sb = new StringBuilder();
            sb.append(this.mBurstShotTitle);
            if (((BaseModule) Camera2Module.this).mMutexModePicker.isUbiFocus()) {
                str = Storage.UBIFOCUS_SUFFIX + (this.mSavedJpegCallbackNum - 1);
            } else {
                str = "_BURST" + this.mSavedJpegCallbackNum;
            }
            sb.append(str);
            return sb.toString();
        }

        @Override // com.android.camera2.Camera2Proxy.PictureCallbackWrapper, com.android.camera2.Camera2Proxy.PictureCallback
        public void onPictureTaken(byte[] bArr, CaptureResult captureResult) {
            int i;
            int i2;
            if (!((BaseModule) Camera2Module.this).mPaused && bArr != null && Camera2Module.this.mReceivedJpegCallbackNum < Camera2Module.this.mTotalJpegCallbackNum && Camera2Module.this.mMultiSnapStatus) {
                if (this.mSavedJpegCallbackNum == 1 && !Camera2Module.this.mMultiSnapStopRequest && !((BaseModule) Camera2Module.this).mMutexModePicker.isUbiFocus()) {
                    ((BaseModule) Camera2Module.this).mActivity.getImageSaver().updateImage(getBurstShotTitle(), this.mPressDownTitle);
                }
                if (!Storage.isLowStorageAtLastPoint()) {
                    Camera2Module.access$904(Camera2Module.this);
                    if (!((BaseModule) Camera2Module.this).mActivity.getImageSaver().isSaveQueueFull()) {
                        this.mSavedJpegCallbackNum++;
                        if (!((BaseModule) Camera2Module.this).mMutexModePicker.isUbiFocus()) {
                            Camera2Module.this.playCameraSound(4);
                        }
                        ViberatorContext.getInstance(Camera2Module.this.getActivity().getApplicationContext()).performBurstCapture();
                        Camera2Module.this.mBurstEmitter.onNext(Integer.valueOf(this.mSavedJpegCallbackNum));
                        boolean z = ((BaseModule) Camera2Module.this).mMutexModePicker.isUbiFocus() && Camera2Module.this.mReceivedJpegCallbackNum <= Camera2Module.this.mTotalJpegCallbackNum;
                        int orientation = z ? 0 : ExifHelper.getOrientation(bArr);
                        if ((Camera2Module.this.mJpegRotation + orientation) % 180 == 0) {
                            i2 = ((BaseModule) Camera2Module.this).mPictureSize.getWidth();
                            i = ((BaseModule) Camera2Module.this).mPictureSize.getHeight();
                        } else {
                            i2 = ((BaseModule) Camera2Module.this).mPictureSize.getHeight();
                            i = ((BaseModule) Camera2Module.this).mPictureSize.getWidth();
                        }
                        String burstShotTitle = getBurstShotTitle();
                        boolean z2 = ((BaseModule) Camera2Module.this).mMutexModePicker.isUbiFocus() && Camera2Module.this.mReceivedJpegCallbackNum == Camera2Module.this.mTotalJpegCallbackNum - 1;
                        if (!((BaseModule) Camera2Module.this).mMutexModePicker.isUbiFocus() || Camera2Module.this.mReceivedJpegCallbackNum != Camera2Module.this.mTotalJpegCallbackNum) {
                            ((BaseModule) Camera2Module.this).mActivity.getImageSaver().addImage(bArr, (Camera2Module.this.mReceivedJpegCallbackNum != 1 || Camera2Module.this.mMultiSnapStopRequest) && (Camera2Module.this.mReceivedJpegCallbackNum == Camera2Module.this.mTotalJpegCallbackNum || Camera2Module.this.mMultiSnapStopRequest || this.mDropped), burstShotTitle, null, System.currentTimeMillis(), null, this.mLocation, i2, i, null, orientation, z, z2, true, false, false, null, Camera2Module.this.getPictureInfo(), -1, captureResult);
                            this.mDropped = false;
                        }
                    } else {
                        Log.e(Camera2Module.TAG, "CaptureBurst queue full and drop " + Camera2Module.this.mReceivedJpegCallbackNum);
                        this.mDropped = true;
                        if (Camera2Module.this.mReceivedJpegCallbackNum >= Camera2Module.this.mTotalJpegCallbackNum) {
                            ((BaseModule) Camera2Module.this).mActivity.getThumbnailUpdater().getLastThumbnailUncached();
                        }
                    }
                    if (Camera2Module.this.mReceivedJpegCallbackNum >= Camera2Module.this.mTotalJpegCallbackNum || Camera2Module.this.mMultiSnapStopRequest || this.mDropped) {
                        Camera2Module.this.stopMultiSnap();
                    }
                } else if (!((BaseModule) Camera2Module.this).mMutexModePicker.isUbiFocus() && Camera2Module.this.mMultiSnapStatus) {
                    Camera2Module.this.stopMultiSnap();
                }
            }
        }

        @Override // com.android.camera2.Camera2Proxy.PictureCallbackWrapper, com.android.camera2.Camera2Proxy.PictureCallback
        public void onPictureTakenFinished(boolean z) {
            Camera2Module.this.stopMultiSnap();
            Camera2Module.this.mBurstEmitter.onComplete();
        }
    }

    private final class JpegRepeatingCaptureCallback extends Camera2Proxy.PictureCallbackWrapper {
        String mBurstShotTitle;
        private boolean mDropped;
        private WeakReference<Camera2Module> mModule;
        ParallelTaskDataParameter mParallelParameter = null;
        String mPressDownTitle;

        public JpegRepeatingCaptureCallback(Camera2Module camera2Module) {
            this.mModule = new WeakReference<>(camera2Module);
        }

        private String getBurstShotTitle() {
            String str;
            if (Camera2Module.this.mUpdateImageTitle && this.mBurstShotTitle != null && Camera2Module.this.mReceivedJpegCallbackNum == 1) {
                this.mPressDownTitle = this.mBurstShotTitle;
                this.mBurstShotTitle = null;
            }
            if (this.mBurstShotTitle == null) {
                long currentTimeMillis = System.currentTimeMillis();
                this.mBurstShotTitle = Util.createJpegName(currentTimeMillis);
                if (this.mBurstShotTitle.length() != 19) {
                    this.mBurstShotTitle = Util.createJpegName(currentTimeMillis + 1000);
                }
            }
            StringBuilder sb = new StringBuilder();
            sb.append(this.mBurstShotTitle);
            if (((BaseModule) Camera2Module.this).mMutexModePicker.isUbiFocus()) {
                str = Storage.UBIFOCUS_SUFFIX + (Camera2Module.this.mReceivedJpegCallbackNum - 1);
            } else {
                str = "_BURST" + Camera2Module.this.mReceivedJpegCallbackNum;
            }
            sb.append(str);
            return sb.toString();
        }

        private boolean tryCheckNeedStop() {
            if (!Storage.isLowStorageAtLastPoint()) {
                return false;
            }
            if (((BaseModule) Camera2Module.this).mMutexModePicker.isUbiFocus() || !Camera2Module.this.mMultiSnapStatus) {
                return true;
            }
            Camera2Module.this.stopMultiSnap();
            return true;
        }

        @Override // com.android.camera2.Camera2Proxy.PictureCallbackWrapper, com.android.camera2.Camera2Proxy.PictureCallback
        public ParallelTaskData onCaptureStart(ParallelTaskData parallelTaskData, CameraSize cameraSize, boolean z) {
            boolean z2 = true;
            if (!Camera2Module.this.mEnableParallelSession || ((BaseModule) Camera2Module.this).mPaused || Camera2Module.this.mReceivedJpegCallbackNum >= Camera2Module.this.mTotalJpegCallbackNum || !Camera2Module.this.mMultiSnapStatus) {
                Log.d(Camera2Module.TAG, "onCaptureStart: revNum = " + Camera2Module.this.mReceivedJpegCallbackNum + " paused = " + ((BaseModule) Camera2Module.this).mPaused + " status = " + Camera2Module.this.mMultiSnapStatus);
                parallelTaskData.setAbandoned(true);
                return parallelTaskData;
            }
            if (Camera2Module.this.mReceivedJpegCallbackNum == 1 && !Camera2Module.this.mMultiSnapStopRequest) {
                if (!Camera2Module.this.is3ALocked()) {
                    Camera2Module.this.mFocusManager.onShutter();
                }
                if (!((BaseModule) Camera2Module.this).mMutexModePicker.isUbiFocus()) {
                    ((BaseModule) Camera2Module.this).mActivity.getImageSaver().updateImage(getBurstShotTitle(), this.mPressDownTitle);
                }
            }
            if (tryCheckNeedStop()) {
                Log.d(Camera2Module.TAG, "onCaptureStart: need stop multi capture, return null");
                return null;
            }
            if (this.mParallelParameter == null) {
                CameraSize cameraSize2 = ((BaseModule) Camera2Module.this).mOutputPictureSize;
                if (cameraSize2 == null || !cameraSize2.equals(cameraSize)) {
                    ((BaseModule) Camera2Module.this).mOutputPictureSize = cameraSize;
                }
                Size sizeObject = ((BaseModule) Camera2Module.this).mOutputPictureSize.toSizeObject();
                boolean isHeicImageFormat = CompatibilityUtils.isHeicImageFormat(((BaseModule) Camera2Module.this).mOutputPictureFormat);
                int access$1600 = Camera2Module.this.clampQuality(CameraSettings.getEncodingQuality(true).toInteger(isHeicImageFormat));
                Log.d(Camera2Module.TAG, "onCaptureStart: isHeic = " + isHeicImageFormat + ", quality = " + access$1600);
                Location access$1700 = ((BaseModule) Camera2Module.this).mActivity.getCameraIntentManager().checkIntentLocationPermission(((BaseModule) Camera2Module.this).mActivity) ? Camera2Module.this.mLocation : null;
                ParallelTaskDataParameter.Builder filterId = new ParallelTaskDataParameter.Builder(((BaseModule) Camera2Module.this).mPreviewSize.toSizeObject(), cameraSize.toSizeObject(), sizeObject, ((BaseModule) Camera2Module.this).mOutputPictureFormat).setHasDualWaterMark(false).setMirror(Camera2Module.this.isFrontMirror()).setLightingPattern(CameraSettings.getPortraitLightingPattern()).setFilterId(FilterInfo.FILTER_ID_NONE);
                int i = ((BaseModule) Camera2Module.this).mOrientation;
                if (-1 == i) {
                    i = 0;
                }
                this.mParallelParameter = filterId.setOrientation(i).setJpegRotation(Camera2Module.this.mJpegRotation).setShootRotation(Camera2Module.this.mShootRotation).setShootOrientation(Camera2Module.this.mShootOrientation).setLocation(access$1700).setFrontCamera(Camera2Module.this.isFrontCamera()).setBokehFrontCamera(Camera2Module.this.isPictureUseDualFrontCamera()).setAlgorithmName(Camera2Module.this.mAlgorithmName).setPictureInfo(Camera2Module.this.getPictureInfo()).setSuffix(Camera2Module.this.getSuffix()).setSaveGroupshotPrimitive(false).setDeviceWatermarkParam(Camera2Module.this.getDeviceWaterMarkParam()).setJpegQuality(access$1600).setReprocessBurstShotPicture(Camera2Module.this.isZoomRatioBetweenUltraAndWide() && DataRepository.dataItemFeature().ac()).build();
            }
            parallelTaskData.fillParameter(this.mParallelParameter);
            if (!((BaseModule) Camera2Module.this).mActivity.getImageSaver().isSaveQueueFull()) {
                Camera2Module.access$904(Camera2Module.this);
                if (!((BaseModule) Camera2Module.this).mMutexModePicker.isUbiFocus()) {
                    Camera2Module.this.playCameraSound(4);
                }
                ViberatorContext.getInstance(Camera2Module.this.getActivity().getApplicationContext()).performBurstCapture();
                Log.d(Camera2Module.TAG, "onCaptureStart: revNum = " + Camera2Module.this.mReceivedJpegCallbackNum);
                Camera2Module.this.mBurstEmitter.onNext(Integer.valueOf(Camera2Module.this.mReceivedJpegCallbackNum));
                if (!((BaseModule) Camera2Module.this).mMutexModePicker.isUbiFocus() && Camera2Module.this.mReceivedJpegCallbackNum <= Camera2Module.this.mTotalJpegCallbackNum) {
                    String generateFilepath4Image = Storage.generateFilepath4Image(getBurstShotTitle(), CompatibilityUtils.isHeicImageFormat(((BaseModule) Camera2Module.this).mOutputPictureFormat));
                    Log.d(Camera2Module.TAG, "onCaptureStart: savePath = " + generateFilepath4Image);
                    parallelTaskData.setSavePath(generateFilepath4Image);
                    if (Camera2Module.this.mReceivedJpegCallbackNum != Camera2Module.this.mTotalJpegCallbackNum && !Camera2Module.this.mMultiSnapStopRequest && !this.mDropped) {
                        z2 = false;
                    }
                    parallelTaskData.setNeedThumbnail(z2);
                    Camera2Module.this.beginParallelProcess(parallelTaskData, false);
                    this.mDropped = false;
                    if (Camera2Module.this.mReceivedJpegCallbackNum >= Camera2Module.this.mTotalJpegCallbackNum || Camera2Module.this.mMultiSnapStopRequest || this.mDropped) {
                        Camera2Module.this.stopMultiSnap();
                    }
                    return parallelTaskData;
                }
            } else {
                Log.e(Camera2Module.TAG, "onCaptureStart: queue full and drop " + Camera2Module.this.mReceivedJpegCallbackNum);
                this.mDropped = true;
                if (Camera2Module.this.mReceivedJpegCallbackNum >= Camera2Module.this.mTotalJpegCallbackNum) {
                    ((BaseModule) Camera2Module.this).mActivity.getThumbnailUpdater().getLastThumbnailUncached();
                }
            }
            parallelTaskData = null;
            Camera2Module.this.stopMultiSnap();
            return parallelTaskData;
        }

        @Override // com.android.camera2.Camera2Proxy.PictureCallbackWrapper, com.android.camera2.Camera2Proxy.PictureCallback
        public void onPictureTakenFinished(boolean z) {
            if (this.mModule.get() != null) {
                this.mModule.get().onBurstPictureTakenFinished(z);
            } else {
                Log.e(Camera2Module.TAG, "callback onShotFinished null");
            }
        }
    }

    private static class MainHandler extends Handler {
        private WeakReference<Camera2Module> mModule;

        public MainHandler(Camera2Module camera2Module, Looper looper) {
            super(looper);
            this.mModule = new WeakReference<>(camera2Module);
        }

        public void handleMessage(Message message) {
            Camera2Proxy camera2Proxy;
            Camera2Module camera2Module = this.mModule.get();
            if (camera2Module != null) {
                if (!camera2Module.isCreated()) {
                    removeCallbacksAndMessages(null);
                } else if (camera2Module.getActivity() != null) {
                    int i = message.what;
                    if (i == 2) {
                        camera2Module.getWindow().clearFlags(128);
                    } else if (i == 4) {
                        camera2Module.checkActivityOrientation();
                        if (SystemClock.uptimeMillis() - camera2Module.mOnResumeTime < 5000) {
                            sendEmptyMessageDelayed(4, 100);
                        }
                    } else if (i == 17) {
                        removeMessages(17);
                        removeMessages(2);
                        camera2Module.getWindow().addFlags(128);
                        sendEmptyMessageDelayed(2, (long) camera2Module.getScreenDelay());
                    } else if (i == 31) {
                        camera2Module.setOrientationParameter();
                    } else if (i != 33) {
                        boolean z = true;
                        if (i == 35) {
                            boolean z2 = message.arg1 > 0;
                            if (message.arg2 <= 0) {
                                z = false;
                            }
                            camera2Module.handleUpdateFaceView(z2, z);
                        } else if (i == 44) {
                            camera2Module.restartModule();
                        } else if (i != 45) {
                            switch (i) {
                                case 9:
                                    ((BaseModule) camera2Module).mMainProtocol.initializeFocusView(camera2Module);
                                    return;
                                case 10:
                                    if (!((BaseModule) camera2Module).mActivity.isActivityPaused()) {
                                        ((BaseModule) camera2Module).mOpenCameraFail = true;
                                        camera2Module.onCameraException();
                                        return;
                                    }
                                    return;
                                case 11:
                                    return;
                                default:
                                    switch (i) {
                                        case 48:
                                            camera2Module.setCameraState(1);
                                            return;
                                        case 49:
                                            if (camera2Module.isAlive()) {
                                                camera2Module.stopMultiSnap();
                                                camera2Module.mBurstEmitter.onComplete();
                                                return;
                                            }
                                            return;
                                        case 50:
                                            Log.w(Camera2Module.TAG, "Oops, capture timeout later release timeout!");
                                            camera2Module.onPictureTakenFinished(false);
                                            return;
                                        case 51:
                                            if (!((BaseModule) camera2Module).mActivity.isActivityPaused()) {
                                                ((BaseModule) camera2Module).mOpenCameraFail = true;
                                                camera2Module.onCameraException();
                                                return;
                                            }
                                            return;
                                        case 52:
                                            camera2Module.onShutterButtonClick(camera2Module.getTriggerMode());
                                            return;
                                        default:
                                            switch (i) {
                                                case 56:
                                                    ModeProtocol.MainContentProtocol mainContentProtocol = ((BaseModule) camera2Module).mMainProtocol;
                                                    if (mainContentProtocol != null && mainContentProtocol.isFaceExists(1) && ((BaseModule) camera2Module).mMainProtocol.isFocusViewVisible() && (camera2Proxy = ((BaseModule) camera2Module).mCamera2Device) != null && 4 == camera2Proxy.getFocusMode()) {
                                                        ((BaseModule) camera2Module).mMainProtocol.clearFocusView(7);
                                                        return;
                                                    }
                                                    return;
                                                case 57:
                                                    PreviewDecodeManager.getInstance().reset();
                                                    return;
                                                case 58:
                                                    ModeProtocol.ConfigChanges configChanges = (ModeProtocol.ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164);
                                                    if (configChanges != null) {
                                                        int i2 = message.arg2;
                                                        configChanges.configRotationChange(message.arg1, (360 - (i2 >= 0 ? i2 % 360 : (i2 % 360) + 360)) % 360);
                                                        return;
                                                    }
                                                    return;
                                                case 59:
                                                    Log.d(Camera2Module.TAG, "receive MSG_FIXED_SHOT2SHOT_TIME_OUT");
                                                    camera2Module.resetStatusToIdle();
                                                    return;
                                                case 60:
                                                    Log.d(Camera2Module.TAG, "fallback timeout");
                                                    ((BaseModule) camera2Module).mIsSatFallback = 0;
                                                    ((BaseModule) camera2Module).mFallbackProcessed = false;
                                                    ((BaseModule) camera2Module).mLastSatFallbackRequestId = -1;
                                                    if (camera2Module.mWaitingSnapshot && camera2Module.getCameraState() == 1) {
                                                        boolean unused = camera2Module.mWaitingSnapshot = false;
                                                        sendEmptyMessage(62);
                                                        return;
                                                    }
                                                    return;
                                                case 61:
                                                    Log.d(Camera2Module.TAG, "wait save finish timeout");
                                                    boolean unused2 = camera2Module.mWaitSaveFinish = false;
                                                    camera2Module.showOrHideLoadingProgress(false);
                                                    return;
                                                case 62:
                                                    camera2Module.onWaitingFocusFinished();
                                                    return;
                                                default:
                                                    throw new RuntimeException("no consumer for this message: " + message.what);
                                            }
                                    }
                            }
                        } else {
                            camera2Module.setActivity(null);
                        }
                    } else {
                        camera2Module.setOrientation(message.arg1, message.arg2);
                    }
                }
            }
        }
    }

    private static class SuperNightEventConsumer implements Consumer<Integer> {
        private final WeakReference<Camera2Module> mCamera2ModuleRef;

        private SuperNightEventConsumer(Camera2Module camera2Module) {
            this.mCamera2ModuleRef = new WeakReference<>(camera2Module);
        }

        public void accept(Integer num) throws Exception {
            Camera2Module camera2Module = this.mCamera2ModuleRef.get();
            if (camera2Module != null && camera2Module.isAlive()) {
                int intValue = num.intValue();
                if (intValue == 300) {
                    Log.d(Camera2Module.TAG, "SuperNight: show capture instruction hint");
                    ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
                    if (bottomPopupTips != null) {
                        bottomPopupTips.showTips(11, R.string.super_night_toast, 4);
                    }
                } else if (intValue == 2000) {
                    Log.d(Camera2Module.TAG, "SuperNight: trigger shutter animation, sound and post saving");
                    boolean unused = camera2Module.mWaitingSuperNightResult = true;
                    camera2Module.animateCapture();
                    camera2Module.playCameraSound(0);
                    ModeProtocol.RecordState recordState = (ModeProtocol.RecordState) ModeCoordinatorImpl.getInstance().getAttachProtocol(212);
                    if (recordState != null) {
                        recordState.onPostSavingStart();
                    }
                }
            }
        }
    }

    static /* synthetic */ void Jf() {
        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        if (topAlert != null) {
            topAlert.alertAiSceneSelector(8);
        }
    }

    static /* synthetic */ int access$904(Camera2Module camera2Module) {
        int i = camera2Module.mReceivedJpegCallbackNum + 1;
        camera2Module.mReceivedJpegCallbackNum = i;
        return i;
    }

    /* access modifiers changed from: private */
    public void animateCapture() {
        if (!this.mIsImageCaptureIntent) {
            ((BaseModule) this).mActivity.getCameraScreenNail().animateCapture(getCameraRotation());
        }
    }

    private void applyBacklightEffect() {
        trackAISceneChanged(((BaseModule) this).mModuleIndex, 23);
        setAiSceneEffect(23);
        updateHDR("normal");
        ((BaseModule) this).mCamera2Device.setASDScene(23);
        resetEvValue();
    }

    /* access modifiers changed from: private */
    public void beginParallelProcess(ParallelTaskData parallelTaskData, boolean z) {
        String str = TAG;
        Log.i(str, "algo begin: " + parallelTaskData.getSavePath() + " | " + Thread.currentThread().getName());
        if (this.mServiceStatusListener == null) {
            this.mServiceStatusListener = new LocalParallelService.ServiceStatusListener() {
                /* class com.android.camera.module.Camera2Module.AnonymousClass12 */

                @Override // com.android.camera.LocalParallelService.ServiceStatusListener
                public void onImagePostProcessEnd(ParallelTaskData parallelTaskData) {
                    if (parallelTaskData != null && parallelTaskData.isJpegDataReady() && Camera2Module.this.mIsImageCaptureIntent) {
                        Camera2Module.this.onPictureTakenFinished(true);
                    }
                }

                @Override // com.android.camera.LocalParallelService.ServiceStatusListener
                public void onImagePostProcessStart(ParallelTaskData parallelTaskData) {
                    if (4 != parallelTaskData.getAlgoType()) {
                        if (!Camera2Module.this.mIsImageCaptureIntent) {
                            Camera2Module.this.onPictureTakenFinished(true);
                        }
                        PerformanceTracker.trackPictureCapture(1);
                        Camera2Module camera2Module = Camera2Module.this;
                        Camera2Proxy camera2Proxy = ((BaseModule) camera2Module).mCamera2Device;
                        if (camera2Proxy != null) {
                            camera2Proxy.onParallelImagePostProcStart();
                            return;
                        }
                        Camera camera = ((BaseModule) camera2Module).mActivity;
                        if (camera != null) {
                            camera.removeShotAfterPictureTaken();
                        }
                    }
                }
            };
            AlgoConnector.getInstance().setServiceStatusListener(this.mServiceStatusListener);
        }
    }

    private void blockSnapClickUntilSaveFinish() {
        Log.i(TAG, "blockSnapClickUntilFinish");
        this.mWaitSaveFinish = true;
        ((BaseModule) this).mHandler.sendEmptyMessageDelayed(61, 5000);
    }

    private long calculateTimeout(int i) {
        if (i == 167) {
            return (Long.parseLong(getManualValue(CameraSettings.KEY_QC_EXPOSURETIME, getString(R.string.pref_camera_exposuretime_default))) / 1000000) + CAPTURE_DURATION_THRESHOLD;
        }
        if (i == 173 || CameraSettings.isSuperNightOn()) {
            return 24000;
        }
        return CAPTURE_DURATION_THRESHOLD;
    }

    private void callGalleryDocumentPage(String str, String str2) {
        String str3 = TAG;
        Log.i(str3, "callGalleryDocumentPage effect: " + str2);
        Intent intent = new Intent();
        intent.setAction(CameraIntentManager.ACTION_EDIT_DOCOCUMENT_IMAGE);
        intent.setData(Util.photoUri(str));
        intent.putExtra(CameraIntentManager.DOCUMENT_IMAGE_EFFECT, str2);
        if (((BaseModule) this).mActivity.startFromKeyguard()) {
            intent.putExtra(a.mf, true);
        }
        if (Util.startActivityForResultCatchException(((BaseModule) this).mActivity, intent, Util.REQUEST_CODE_OPEN_MIUI_EXTRA_PHOTO)) {
            ((BaseModule) this).mActivity.setJumpFlag(6);
        }
    }

    private void callGalleryIDCardPage(String[] strArr) {
        Log.i(TAG, "callGalleryIDCardPage");
        Intent intent = new Intent();
        intent.setAction(CameraIntentManager.ACTION_EDIT_IDCARD_IMAGE);
        ArrayList<? extends Parcelable> arrayList = new ArrayList<>();
        arrayList.add(Util.photoUri(strArr[0]));
        arrayList.add(Util.photoUri(strArr[1]));
        intent.putParcelableArrayListExtra("android.intent.extra.STREAM", arrayList);
        if (((BaseModule) this).mActivity.startFromKeyguard()) {
            intent.putExtra(a.mf, true);
        }
        if (Util.startActivityForResultCatchException(((BaseModule) this).mActivity, intent, Util.REQUEST_CODE_OPEN_MIUI_EXTRA_PHOTO)) {
            ((BaseModule) this).mActivity.setJumpFlag(6);
            ((DataItemGlobal) DataRepository.provider().dataGlobal()).setCurrentMode(163);
        }
    }

    private void checkLLS(CaptureResult captureResult) {
        boolean isLLSNeeded = CaptureResultParser.isLLSNeeded(captureResult);
        if (isLLSNeeded != this.mIsLLSNeeded) {
            String str = TAG;
            Log.d(str, "is lls needed = " + isLLSNeeded);
            this.mIsLLSNeeded = isLLSNeeded;
            ((BaseModule) this).mCamera2Device.setLLS(this.mIsLLSNeeded);
        }
    }

    private void checkMoreFrameCaptureLockAFAE(boolean z) {
        if (((BaseModule) this).mCamera2Device == null) {
            Log.w(TAG, "mCamera2Device == null, return");
        } else if (DataRepository.dataItemFeature().Se()) {
            if (!ModuleManager.isSuperNightScene() && !this.mShowSuperNightHint && !((BaseModule) this).mMutexModePicker.isHdr() && !this.mIsLLSNeeded && !((BaseModule) this).mCamera2Device.getCameraConfigs().isSuperResolutionEnabled()) {
                return;
            }
            if ((!ModuleManager.isSuperNightScene() && !this.mShowSuperNightHint) || DataRepository.dataItemFeature().lf()) {
                if (((BaseModule) this).mMutexModePicker.isHdr()) {
                    boolean z2 = true;
                    if (!b.jv || ((BaseModule) this).mCamera2Device.getCapabilities().getFacing() != 1 || ((BaseModule) this).mCamera2Device.isMacroMode() || ((BaseModule) this).mCamera2Device.getCameraConfigs().isRearBokehEnabled()) {
                        z2 = false;
                    }
                    if (z2) {
                        return;
                    }
                }
                if (!is3ALocked()) {
                    ((BaseModule) this).mCamera2Device.setMFLockAfAe(z);
                }
            }
        }
    }

    private void checkSatFallback(CaptureResult captureResult) {
        boolean isSatFallbackDetected = CaptureResultParser.isSatFallbackDetected(captureResult);
        if (((BaseModule) this).mIsSatFallback != 2 && isSatFallbackDetected && !this.mWaitingSnapshot) {
            int sendSatFallbackRequest = ((BaseModule) this).mCamera2Device.sendSatFallbackRequest();
            String str = TAG;
            Log.d(str, "checkSatFallback: lastFallbackRequestId = " + sendSatFallbackRequest);
            if (sendSatFallbackRequest >= 0) {
                ((BaseModule) this).mIsSatFallback = 2;
                ((BaseModule) this).mFallbackProcessed = false;
                ((BaseModule) this).mLastSatFallbackRequestId = sendSatFallbackRequest;
                ((BaseModule) this).mHandler.removeMessages(60);
                ((BaseModule) this).mHandler.sendEmptyMessageDelayed(60, 1500);
            }
        } else if (((BaseModule) this).mLastSatFallbackRequestId >= 0) {
            if (((BaseModule) this).mLastSatFallbackRequestId <= captureResult.getSequenceId()) {
                ((BaseModule) this).mFallbackProcessed = true;
            }
            String str2 = TAG;
            Log.d(str2, "checkSatFallback: fallbackDetected = " + isSatFallbackDetected + " requestId = " + captureResult.getSequenceId() + "|" + captureResult.getFrameNumber());
            if (((BaseModule) this).mFallbackProcessed && !isSatFallbackDetected) {
                ((BaseModule) this).mIsSatFallback = 0;
                ((BaseModule) this).mFallbackProcessed = false;
                ((BaseModule) this).mLastSatFallbackRequestId = -1;
                ((BaseModule) this).mHandler.removeMessages(60);
                if (this.mWaitingSnapshot && getCameraState() == 1) {
                    this.mWaitingSnapshot = false;
                    ((BaseModule) this).mHandler.sendEmptyMessage(62);
                }
            }
        }
    }

    private boolean checkShutterCondition() {
        ModeProtocol.DualController dualController;
        if (isBlockSnap() || isIgnoreTouchEvent()) {
            String str = TAG;
            Log.w(str, "checkShutterCondition: blockSnap=" + isBlockSnap() + " ignoreTouchEvent=" + isIgnoreTouchEvent());
            return false;
        } else if (Storage.isLowStorageAtLastPoint()) {
            Log.w(TAG, "checkShutterCondition: low storage");
            return false;
        } else if (isFrontCamera() && ((BaseModule) this).mActivity.isScreenSlideOff()) {
            Log.w(TAG, "checkShutterCondition: screen is slide off");
            return false;
        } else if (!isIn3OrMoreSatMode() || (dualController = (ModeProtocol.DualController) ModeCoordinatorImpl.getInstance().getAttachProtocol(182)) == null || dualController.isZoomSliderViewIdle()) {
            ModeProtocol.BackStack backStack = (ModeProtocol.BackStack) ModeCoordinatorImpl.getInstance().getAttachProtocol(171);
            if (backStack == null) {
                return true;
            }
            backStack.handleBackStackFromShutter();
            return true;
        } else {
            Log.w(TAG, "checkShutterCondition: 3SAT zooming");
            return false;
        }
    }

    /* access modifiers changed from: private */
    public int clampQuality(int i) {
        return DataRepository.dataItemRunning().getComponentUltraPixel().isRear108MPSwitchOn() ? Util.clamp(i, 0, 90) : i;
    }

    /* access modifiers changed from: private */
    public void configParallelSession() {
        GraphDescriptorBean graphDescriptorBean;
        int cameraCombinationMode = CameraDeviceUtil.getCameraCombinationMode(Camera2DataContainer.getInstance().getRoleIdByActualId(((BaseModule) this).mActualCameraId));
        if (isPortraitMode()) {
            int i = ((!isDualFrontCamera() || DataRepository.dataItemFeature().mc()) && !isDualCamera() && !isBokehUltraWideBackCamera()) ? 1 : 2;
            String str = TAG;
            Log.d(str, "configParallelSession: inputStreamNum = " + i);
            graphDescriptorBean = new GraphDescriptorBean(32770, i, true, cameraCombinationMode);
        } else {
            int i2 = ((BaseModule) this).mModuleIndex;
            if (i2 == 167) {
                graphDescriptorBean = new GraphDescriptorBean(32771, 1, true, cameraCombinationMode);
            } else if (i2 == 175) {
                graphDescriptorBean = new GraphDescriptorBean(33011, 1, true, cameraCombinationMode);
            } else {
                if (cameraCombinationMode == 0) {
                    cameraCombinationMode = 513;
                }
                graphDescriptorBean = new GraphDescriptorBean(0, 1, true, cameraCombinationMode);
            }
        }
        String str2 = TAG;
        Log.d(str2, "configParallelSession: pictureSize = " + ((BaseModule) this).mPictureSize);
        String str3 = TAG;
        Log.d(str3, "configParallelSession: outputSize = " + ((BaseModule) this).mOutputPictureSize);
        String str4 = TAG;
        Log.d(str4, "configParallelSession: outputFormat = " + ((BaseModule) this).mOutputPictureFormat);
        CameraSize cameraSize = ((BaseModule) this).mPictureSize;
        BufferFormat bufferFormat = new BufferFormat(cameraSize.width, cameraSize.height, 35, graphDescriptorBean);
        LocalParallelService.LocalBinder localBinder = AlgoConnector.getInstance().getLocalBinder(true);
        localBinder.configCaptureSession(bufferFormat);
        localBinder.setImageSaver(((BaseModule) this).mActivity.getImageSaver());
        CameraSize cameraSize2 = ((BaseModule) this).mOutputPictureSize;
        localBinder.setOutputPictureSpec(cameraSize2.width, cameraSize2.height, ((BaseModule) this).mOutputPictureFormat);
        localBinder.setSRRequireReprocess(DataRepository.dataItemFeature().isSRRequireReprocess());
        synchronized (this.mParallelSessionLock) {
            this.mParallelSessionConfigured = true;
        }
    }

    /* access modifiers changed from: private */
    public void consumeAiSceneResult(int i, boolean z) {
        ModeProtocol.AIWatermarkDetect aIWatermarkDetect;
        if (this.mAIWatermarkEnable && (aIWatermarkDetect = (ModeProtocol.AIWatermarkDetect) ModeCoordinatorImpl.getInstance().getAttachProtocol(254)) != null) {
            aIWatermarkDetect.onASDChange(i);
        }
        if (this.mAiSceneEnabled) {
            realConsumeAiSceneResult(i, z);
            int i2 = this.mCurrentAiScene;
            if (!(i2 == -1 || i2 == 23 || i2 == 24 || i2 == 35)) {
                ((BaseModule) this).mCamera2Device.setASDScene(0);
            }
            resumePreviewInWorkThread();
        }
    }

    /* access modifiers changed from: private */
    public void consumeAsdSceneResult(int i) {
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis - this.mLastAsdSceneShowTime > 500 && this.mCurrentAsdScene != i && !isDoingAction() && isAlive() && !((BaseModule) this).mActivity.isActivityPaused()) {
            exitAsdScene(this.mCurrentAsdScene);
            enterAsdScene(i);
            this.mCurrentAsdScene = i;
            this.mLastAsdSceneShowTime = currentTimeMillis;
        }
    }

    /* JADX WARNING: Missing exception handler attribute for start block: B:42:0x00f0 */
    private void doAttach() {
        FileOutputStream fileOutputStream;
        if (!((BaseModule) this).mPaused) {
            byte[] storedJpegData = ((BaseModule) this).mActivity.getImageSaver().getStoredJpegData();
            if (this.mIsSaveCaptureImage) {
                ((BaseModule) this).mActivity.getImageSaver().saveStoredData();
            }
            fileOutputStream = null;
            if (this.mCropValue != null) {
                try {
                    File fileStreamPath = ((BaseModule) this).mActivity.getFileStreamPath(sTempCropFilename);
                    fileStreamPath.delete();
                    FileOutputStream openFileOutput = ((BaseModule) this).mActivity.openFileOutput(sTempCropFilename, 0);
                    try {
                        openFileOutput.write(storedJpegData);
                        openFileOutput.close();
                        Uri fromFile = Uri.fromFile(fileStreamPath);
                        Util.closeSilently(null);
                        Bundle bundle = new Bundle();
                        if (ComponentRunningTiltValue.TILT_CIRCLE.equals(this.mCropValue)) {
                            bundle.putString("circleCrop", MistatsConstants.BaseEvent.VALUE_TRUE);
                        }
                        Uri uri = this.mSaveUri;
                        if (uri != null) {
                            bundle.putParcelable("output", uri);
                        } else {
                            bundle.putBoolean("return-data", true);
                        }
                        Intent intent = new Intent("com.android.camera.action.CROP");
                        intent.setData(fromFile);
                        intent.putExtras(bundle);
                        ((BaseModule) this).mActivity.startActivityForResult(intent, 1000);
                    } catch (FileNotFoundException unused) {
                        fileOutputStream = openFileOutput;
                        ((BaseModule) this).mActivity.setResult(0);
                        ((BaseModule) this).mActivity.finish();
                        Util.closeSilently(fileOutputStream);
                        return;
                    } catch (IOException unused2) {
                        fileOutputStream = openFileOutput;
                        try {
                            ((BaseModule) this).mActivity.setResult(0);
                            ((BaseModule) this).mActivity.finish();
                            Util.closeSilently(fileOutputStream);
                            return;
                        } catch (Throwable th) {
                            th = th;
                            Util.closeSilently(fileOutputStream);
                            throw th;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        fileOutputStream = openFileOutput;
                        Util.closeSilently(fileOutputStream);
                        throw th;
                    }
                } catch (FileNotFoundException unused3) {
                    ((BaseModule) this).mActivity.setResult(0);
                    ((BaseModule) this).mActivity.finish();
                    Util.closeSilently(fileOutputStream);
                    return;
                } catch (IOException unknown) {
                    ((BaseModule) this).mActivity.setResult(0);
                    ((BaseModule) this).mActivity.finish();
                    Util.closeSilently(fileOutputStream);
                    return;
                }
            } else if (this.mSaveUri != null) {
                try {
                    fileOutputStream = CameraAppImpl.getAndroidContext().getContentResolver().openOutputStream(this.mSaveUri);
                    fileOutputStream.write(storedJpegData);
                    fileOutputStream.close();
                    ((BaseModule) this).mActivity.setResult(-1);
                } catch (Exception e2) {
                    Log.e(TAG, "Exception when doAttach: ", e2);
                } catch (Throwable th3) {
                    ((BaseModule) this).mActivity.finish();
                    Util.closeSilently(fileOutputStream);
                    throw th3;
                }
                ((BaseModule) this).mActivity.finish();
                Util.closeSilently(fileOutputStream);
            } else {
                ((BaseModule) this).mActivity.setResult(-1, new Intent("inline-data").putExtra(PhotosOemApi.PATH_SPECIAL_TYPE_DATA, Util.rotate(Util.makeBitmap(storedJpegData, 51200), ExifHelper.getOrientation(storedJpegData))));
                ((BaseModule) this).mActivity.finish();
            }
            ((BaseModule) this).mActivity.getImageSaver().releaseStoredJpegData();
        }
    }

    private void doLaterReleaseIfNeed() {
        if (((BaseModule) this).mActivity == null) {
            Log.w(TAG, "doLaterReleaseIfNeed: mActivity is null...");
            return;
        }
        Handler handler = ((BaseModule) this).mHandler;
        if (handler != null) {
            handler.removeMessages(50);
        }
        if (((BaseModule) this).mActivity.isActivityPaused()) {
            Camera2Proxy camera2Proxy = ((BaseModule) this).mCamera2Device;
            boolean z = camera2Proxy != null && camera2Proxy.isSessionReady();
            if (z) {
                Log.d(TAG, "doLaterRelease");
            } else {
                Log.d(TAG, "doLaterRelease but session is closed");
            }
            ((BaseModule) this).mActivity.releaseAll(true, z);
        } else if (isDeparted()) {
            Log.w(TAG, "doLaterReleaseIfNeed: isDeparted...");
        } else {
            ((BaseModule) this).mHandler.post(new H(this));
            if (isTextureExpired()) {
                Log.d(TAG, "doLaterReleaseIfNeed: surfaceTexture expired, restartModule");
                ((BaseModule) this).mHandler.post(new I(this));
            }
        }
    }

    private boolean enableFrontMFNR() {
        int i;
        if (b.isMTKPlatform()) {
            return b.Dl() && DataRepository.dataItemFeature().sc();
        }
        if (!b.Dl()) {
            return false;
        }
        if (this.mOperatingMode == 32773) {
            return true;
        }
        if (DataRepository.dataItemFeature().md() && ((i = this.mOperatingMode) == 32770 || i == 36864)) {
            return true;
        }
        if (DataRepository.dataItemFeature().sc()) {
            int i2 = this.mOperatingMode;
            if (i2 == 36865) {
                return true;
            }
            return i2 == 36867 ? DataRepository.dataItemFeature().qe() : isFrontCamera() && this.mOperatingMode == 36869;
        }
    }

    private boolean enablePreviewAsThumbnail() {
        Camera2Proxy camera2Proxy;
        if (!isAlive()) {
            return false;
        }
        if (((BaseModule) this).mModuleIndex == 175) {
            return DataRepository.dataItemFeature().ff();
        }
        if (CameraSettings.isUltraPixelOn()) {
            return false;
        }
        if (this.mEnableParallelSession) {
            return true;
        }
        if (this.mIsPortraitLightingOn) {
            return false;
        }
        if (CameraSettings.isLiveShotOn() || CameraSettings.isPortraitModeBackOn()) {
            return true;
        }
        int i = ((BaseModule) this).mModuleIndex;
        return i != 167 && i != 173 && !CameraSettings.isSuperNightOn() && !CameraSettings.showGenderAge() && !CameraSettings.isMagicMirrorOn() && !CameraSettings.isTiltShiftOn() && (camera2Proxy = ((BaseModule) this).mCamera2Device) != null && camera2Proxy.isNeedPreviewThumbnail();
    }

    private void enterAsdScene(int i) {
        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        if (i != 0) {
            if (i == 9) {
                String componentValue = DataRepository.dataItemConfig().getComponentFlash().getComponentValue(((BaseModule) this).mModuleIndex);
                if ("3".equals(componentValue)) {
                    topAlert.alertFlash(0, "1", false);
                    updatePreferenceInWorkThread(10);
                } else if (ComponentConfigFlash.FLASH_VALUE_SCREEN_LIGHT_AUTO.equals(componentValue)) {
                    topAlert.alertFlash(0, "1", false);
                    Log.d(TAG, "enterAsdScene(): turn off HDR as FLASH has higher priority than HDR");
                    onHdrSceneChanged(false);
                    updatePreferenceInWorkThread(10);
                }
            } else if (i != 4) {
                if (i != 5) {
                    if (i != 6) {
                        if (i == 7 && !this.m3ALocked) {
                            setPortraitSuccessHintVisible(0);
                        }
                    } else if (!this.m3ALocked) {
                        updateTipMessage(6, R.string.portrait_mode_lowlight_hint, 4);
                    }
                } else if (!this.m3ALocked) {
                    updateTipMessage(6, R.string.portrait_mode_too_far_hint, 4);
                }
            } else if (!this.m3ALocked) {
                updateTipMessage(6, R.string.portrait_mode_too_close_hint, 4);
            }
        } else if (!CameraSettings.isSuperNightOn()) {
            topAlert.alertFlash(0, "1", false);
        }
    }

    private void exitAsdScene(int i) {
        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        if (i == 0) {
            String componentValue = DataRepository.dataItemConfig().getComponentFlash().getComponentValue(((BaseModule) this).mModuleIndex);
            if (!"1".equals(componentValue) && !ComponentConfigFlash.FLASH_VALUE_SCREEN_LIGHT_ON.equals(componentValue) && !"2".equals(componentValue) && !"5".equals(componentValue)) {
                topAlert.alertFlash(8, "1", false);
            }
        } else if (i == 9) {
            String componentValue2 = DataRepository.dataItemConfig().getComponentFlash().getComponentValue(((BaseModule) this).mModuleIndex);
            if ("3".equals(componentValue2) || ComponentConfigFlash.FLASH_VALUE_SCREEN_LIGHT_AUTO.equals(componentValue2)) {
                topAlert.alertFlash(8, "1", false);
            }
            updatePreferenceInWorkThread(10);
        } else if (i != 4) {
            if (i != 5) {
                if (i != 6) {
                    if (i == 7 && !this.m3ALocked) {
                        setPortraitSuccessHintVisible(8);
                    }
                } else if (!this.m3ALocked) {
                    hideTipMessage(R.string.portrait_mode_lowlight_hint);
                }
            } else if (!this.m3ALocked) {
                hideTipMessage(R.string.portrait_mode_too_far_hint);
            }
        } else if (!this.m3ALocked) {
            hideTipMessage(R.string.portrait_mode_too_close_hint);
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: finishSuperNightState */
    public void t(boolean z) {
        ModeProtocol.RecordState recordState = (ModeProtocol.RecordState) ModeCoordinatorImpl.getInstance().getAttachProtocol(212);
        if (recordState != null) {
            if (z) {
                animateCapture();
                playCameraSound(0);
                recordState.onPostSavingStart();
            }
            recordState.onPostSavingFinish();
        }
    }

    private String generateFileTitle() {
        if (CameraSettings.isDocumentModeOn(((BaseModule) this).mModuleIndex)) {
            blockSnapClickUntilSaveFinish();
            return Storage.DOCUMENT_PICTURE;
        } else if (((BaseModule) this).mModuleIndex == 182) {
            blockSnapClickUntilSaveFinish();
            return getCurrentIDCardPictureName();
        } else {
            return getPrefix() + Util.createJpegName(System.currentTimeMillis()) + getSuffix();
        }
    }

    private String getCalibrationDataFileName(int i) {
        return isFrontCamera() ? "front_dual_camera_caldata.bin" : i == Camera2DataContainer.getInstance().getUltraWideBokehCameraId() ? "back_dual_camera_caldata_wu.bin" : "back_dual_camera_caldata.bin";
    }

    private int getCountDownTimes(int i) {
        Intent intent = this.mBroadcastIntent;
        int timerDurationSeconds = intent != null ? CameraIntentManager.getInstance(intent).getTimerDurationSeconds() : ((BaseModule) this).mActivity.getCameraIntentManager().getTimerDurationSeconds();
        if (timerDurationSeconds != -1) {
            Intent intent2 = this.mBroadcastIntent;
            if (intent2 != null) {
                intent2.removeExtra(CameraIntentManager.CameraExtras.TIMER_DURATION_SECONDS);
            } else {
                ((BaseModule) this).mActivity.getIntent().removeExtra(CameraIntentManager.CameraExtras.TIMER_DURATION_SECONDS);
            }
            if (timerDurationSeconds != 0) {
                return timerDurationSeconds != 5 ? 3 : 5;
            }
            return 0;
        } else if (i != 100 || !CameraSettings.isHandGestureOpen()) {
            return CameraSettings.getCountDownTimes();
        } else {
            int countDownTimes = CameraSettings.getCountDownTimes();
            if (countDownTimes != 0) {
                return countDownTimes;
            }
            return 3;
        }
    }

    private String getCurrentAiSceneName() {
        int i = this.mCurrentAiScene;
        int i2 = ((BaseModule) this).mModuleIndex;
        if (i2 != 163 && i2 != 167) {
            return null;
        }
        if (!CameraSettings.getAiSceneOpen(((BaseModule) this).mModuleIndex)) {
            return "off";
        }
        if (i == -1) {
            i = this.isSilhouette ? 24 : 23;
        }
        TypedArray obtainTypedArray = getResources().obtainTypedArray(R.array.ai_scene_names);
        String string = (i < 0 || i >= obtainTypedArray.length()) ? MistatsConstants.BaseEvent.UNSPECIFIED : obtainTypedArray.getString(i);
        obtainTypedArray.recycle();
        return string;
    }

    private String getCurrentIDCardPictureName() {
        return ((ModeProtocol.IDCardModeProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(233)).getCurrentPictureName();
    }

    /* access modifiers changed from: private */
    public DeviceWatermarkParam getDeviceWaterMarkParam() {
        float f2;
        float f3;
        float f4;
        float resourceFloat;
        float resourceFloat2;
        float resourceFloat3;
        boolean isDualCameraWaterMarkOpen = CameraSettings.isDualCameraWaterMarkOpen();
        boolean isFrontCameraWaterMarkOpen = CameraSettings.isFrontCameraWaterMarkOpen();
        if (isDualCameraWaterMarkOpen) {
            resourceFloat = CameraSettings.getResourceFloat(R.dimen.dualcamera_watermark_size_ratio, 0.0f);
            resourceFloat2 = CameraSettings.getResourceFloat(R.dimen.dualcamera_watermark_padding_x_ratio, 0.0f);
            resourceFloat3 = CameraSettings.getResourceFloat(R.dimen.dualcamera_watermark_padding_y_ratio, 0.0f);
        } else if (isFrontCameraWaterMarkOpen) {
            resourceFloat = CameraSettings.getResourceFloat(R.dimen.global_frontcamera_watermark_size_ratio, 0.0f);
            if (!Util.isGlobalVersion() || resourceFloat == 0.0f) {
                resourceFloat = CameraSettings.getResourceFloat(R.dimen.frontcamera_watermark_size_ratio, 0.0f);
            }
            resourceFloat2 = CameraSettings.getResourceFloat(R.dimen.frontcamera_watermark_padding_x_ratio, 0.0f);
            resourceFloat3 = CameraSettings.getResourceFloat(R.dimen.frontcamera_watermark_padding_y_ratio, 0.0f);
        } else {
            f4 = 0.0f;
            f3 = 0.0f;
            f2 = 0.0f;
            return new DeviceWatermarkParam(isDualCameraWaterMarkOpen, isFrontCameraWaterMarkOpen, CameraSettings.isUltraPixelRearOn(), CameraSettings.isCinematicAspectRatioEnabled(((BaseModule) this).mModuleIndex), CameraSettings.getDualCameraWaterMarkFilePathVendor(), f4, f3, f2);
        }
        f2 = resourceFloat3;
        f4 = resourceFloat;
        f3 = resourceFloat2;
        return new DeviceWatermarkParam(isDualCameraWaterMarkOpen, isFrontCameraWaterMarkOpen, CameraSettings.isUltraPixelRearOn(), CameraSettings.isCinematicAspectRatioEnabled(((BaseModule) this).mModuleIndex), CameraSettings.getDualCameraWaterMarkFilePathVendor(), f4, f3, f2);
    }

    private CameraSize getLimitSize(List<CameraSize> list) {
        Rect activeArraySize = ((BaseModule) this).mCameraCapabilities.getActiveArraySize();
        String str = TAG;
        Log.d(str, "getLimitSize: maxSize = " + activeArraySize.width() + "x" + activeArraySize.height());
        PictureSizeManager.initialize(list, activeArraySize.width() * activeArraySize.height(), ((BaseModule) this).mModuleIndex, ((BaseModule) this).mBogusCameraId);
        return PictureSizeManager.getBestPictureSize();
    }

    private String getManualValue(String str, String str2) {
        return ModuleManager.isProPhotoModule() ? CameraSettingPreferences.instance().getString(str, str2) : str2;
    }

    private int getPictureFormatSuitableForShot(int i) {
        if (CameraSettings.isDocumentModeOn(((BaseModule) this).mModuleIndex)) {
            return 256;
        }
        if (CameraSettings.isLiveShotOn() && isLiveShotAvailable(i)) {
            return 256;
        }
        return ((BaseModule) this).mOutputPictureFormat;
    }

    /* access modifiers changed from: private */
    public PictureInfo getPictureInfo() {
        FaceAnalyzeInfo faceAnalyzeInfo;
        PictureInfo opMode = new PictureInfo().setFrontMirror(isFrontMirror()).setSensorType(isFrontCamera()).setBokehFrontCamera(isPictureUseDualFrontCamera()).setHdrType(DataRepository.dataItemConfig().getComponentHdr().getComponentValue(((BaseModule) this).mModuleIndex)).setOpMode(getOperatingMode());
        opMode.setAiEnabled(this.mAiSceneEnabled);
        opMode.setAiType(this.mCurrentAiScene);
        int i = ((BaseModule) this).mModuleIndex;
        if (i == 166) {
            opMode.setPanorama(true);
        } else if (i == 167) {
            opMode.setProfession(true);
        }
        opMode.setShotBurst(this.mMultiSnapStatus);
        opMode.setFilter(CameraSettings.getShaderEffect());
        CameraSettings.getCameraLensType(((BaseModule) this).mModuleIndex);
        if (isFrontCamera()) {
            opMode.setLensType("front");
        } else {
            int actualCameraId = getActualCameraId();
            if (actualCameraId == Camera2DataContainer.getInstance().getUltraWideCameraId()) {
                opMode.setLensType(actualCameraId + "_RearUltra");
            } else if (actualCameraId == 22) {
                opMode.setLensType(actualCameraId + "_RearMacro");
            } else if (actualCameraId == 20) {
                opMode.setLensType(actualCameraId + PictureInfo.SENSOR_TYPE_REAR_TELE);
            } else if (actualCameraId == 23) {
                opMode.setLensType(actualCameraId + "_RearTele4x");
            } else if (actualCameraId == Camera2DataContainer.getInstance().getMainBackCameraId()) {
                opMode.setLensType(actualCameraId + "_RearWide");
            } else if (actualCameraId == Camera2DataContainer.getInstance().getSATCameraId()) {
                opMode.setLensType(String.valueOf(actualCameraId) + "_" + PictureInfo.SENSOR_TYPE_REAR);
            }
        }
        float[] fArr = this.mFocalLengths;
        if (fArr != null && fArr.length > 0) {
            opMode.setLensfocal(fArr[0]);
        }
        String superNightExif = DebugInfoUtil.getSuperNightExif(this.mPreviewSuperNightExifInfo);
        if (!TextUtils.isEmpty(superNightExif)) {
            opMode.setPreviewSuperNightExif(superNightExif);
        }
        float[] fArr2 = this.mApertures;
        if (fArr2 != null && fArr2.length > 0) {
            opMode.setLensApertues(fArr2[0]);
        }
        String faceInfoString = DebugInfoUtil.getFaceInfoString(((BaseModule) this).mMainProtocol.getFaces());
        if (!TextUtils.isEmpty(faceInfoString)) {
            opMode.setFaceRoi(faceInfoString);
        }
        opMode.setOperateMode(this.mOperatingMode);
        opMode.setZoomMulti(getZoomRatio());
        Camera2Proxy camera2Proxy = ((BaseModule) this).mCamera2Device;
        if (camera2Proxy != null) {
            opMode.setEvValue(camera2Proxy.getExposureCompensation());
            MeteringRectangle[] aFRegions = ((BaseModule) this).mCamera2Device.getCameraConfigs().getAFRegions();
            if (aFRegions != null && aFRegions.length > 0) {
                opMode.setTouchRoi(aFRegions[0]);
            }
        }
        if (this.mBeautyValues != null && !BeautyConstant.LEVEL_CLOSE.equals(CameraSettings.getFaceBeautifyLevel()) && DataRepository.dataItemRunning().getComponentRunningShine().getBeautyVersion() == 2) {
            opMode.setBeautyLevel(this.mBeautyValues.mBeautyLevel);
        }
        if (this.mFaceDetectionEnabled && (faceAnalyzeInfo = this.mFaceInfo) != null) {
            opMode.setGender(faceAnalyzeInfo.mGender);
            opMode.setBaby(this.mFaceInfo.mAge);
        }
        if (((BaseModule) this).mModuleIndex == 173) {
            opMode.setNightScene(1);
        }
        opMode.end();
        return opMode;
    }

    private CameraSize getPictureSize(int i, int i2, CameraSize cameraSize) {
        CameraSize cameraSize2;
        CameraCapabilities capabilities = Camera2DataContainer.getInstance().getCapabilities(i);
        if (capabilities != null) {
            capabilities.setOperatingMode(this.mOperatingMode);
            List<CameraSize> supportedOutputSizeWithAssignedMode = capabilities.getSupportedOutputSizeWithAssignedMode(i2);
            if (cameraSize != null) {
                ArrayList arrayList = new ArrayList(0);
                for (int i3 = 0; i3 < supportedOutputSizeWithAssignedMode.size(); i3++) {
                    CameraSize cameraSize3 = supportedOutputSizeWithAssignedMode.get(i3);
                    if (cameraSize3.compareTo(cameraSize) <= 0) {
                        arrayList.add(cameraSize3);
                    }
                }
                supportedOutputSizeWithAssignedMode = arrayList;
            }
            String str = TAG;
            Log.d(str, "getPictureSize: matchSizes = " + supportedOutputSizeWithAssignedMode);
            cameraSize2 = PictureSizeManager.getBestPictureSize(supportedOutputSizeWithAssignedMode);
        } else {
            cameraSize2 = null;
        }
        String str2 = TAG;
        Log.d(str2, "getPictureSize: cameraId = " + i + " size = " + cameraSize2);
        return cameraSize2;
    }

    private String getPrefix() {
        return isLivePhotoStarted() ? Storage.LIVE_SHOT_PREFIX : "";
    }

    private String getRequestFlashMode() {
        if (isSupportSceneMode()) {
            String flashModeByScene = CameraSettings.getFlashModeByScene(this.mSceneMode);
            if (!TextUtils.isEmpty(flashModeByScene)) {
                return flashModeByScene;
            }
        }
        if (!((BaseModule) this).mMutexModePicker.isSupportedFlashOn() && !((BaseModule) this).mMutexModePicker.isSupportedTorch()) {
            return "0";
        }
        if (ModuleManager.isProPhotoModule() && !CameraSettings.isFlashSupportedInManualMode()) {
            return ComponentConfigFlash.FLASH_VALUE_MANUAL_OFF;
        }
        String componentValue = DataRepository.dataItemConfig().getComponentFlash().getComponentValue(((BaseModule) this).mModuleIndex);
        if (this.mCurrentAsdScene == 9) {
            if (componentValue.equals("3")) {
                return "2";
            }
            if (componentValue.equals(ComponentConfigFlash.FLASH_VALUE_SCREEN_LIGHT_AUTO)) {
                return ComponentConfigFlash.FLASH_VALUE_SCREEN_LIGHT_ON;
            }
        }
        return componentValue;
    }

    private CameraSize getSatPictureSize() {
        int satMasterCameraId = ((BaseModule) this).mCamera2Device.getSatMasterCameraId();
        if (satMasterCameraId == 1) {
            return ((BaseModule) this).mUltraWidePictureSize;
        }
        if (satMasterCameraId == 2) {
            return ((BaseModule) this).mWidePictureSize;
        }
        if (satMasterCameraId == 3) {
            return ((BaseModule) this).mTelePictureSize;
        }
        if (satMasterCameraId == 4) {
            return ((BaseModule) this).mUltraTelePictureSize;
        }
        String str = TAG;
        Log.e(str, "getSatPictureSize: invalid satMasterCameraId " + satMasterCameraId);
        return ((BaseModule) this).mWidePictureSize;
    }

    /* access modifiers changed from: private */
    public String getSuffix() {
        return !((BaseModule) this).mMutexModePicker.isNormal() ? ((BaseModule) this).mMutexModePicker.getSuffix() : "";
    }

    private static String getTiltShiftMode() {
        if (CameraSettings.isTiltShiftOn()) {
            return DataRepository.dataItemRunning().getComponentRunningTiltValue().getComponentValue(160);
        }
        return null;
    }

    private void handleLLSResultInCaptureMode() {
        if (this.mShowLLSHint) {
            ((BaseModule) this).mHandler.post(new s(this));
        }
    }

    private void handleSaveFinishIfNeed(String str) {
        this.mWaitSaveFinish = false;
        if (str != null) {
            String str2 = TAG;
            Log.i(str2, "handleSaveFinishIfNeed title: " + str);
            if (Storage.isDocumentPicture(str)) {
                ((BaseModule) this).mHandler.removeMessages(61);
                AndroidSchedulers.mainThread().scheduleDirect(new j(this));
                callGalleryDocumentPage(Storage.generateFilepath(str, Storage.JPEG_SUFFIX), DataRepository.dataItemRunning().getComponentRunningDocument().getComponentValue(((BaseModule) this).mModuleIndex));
            } else if (Storage.isIdCardPicture(str)) {
                String generateFilepath = Storage.generateFilepath(str, Storage.JPEG_SUFFIX);
                if (Storage.isIdCardPictureOne(str)) {
                    this.mIDCardPaths[0] = generateFilepath;
                    AndroidSchedulers.mainThread().scheduleDirect(h.INSTANCE);
                    return;
                }
                ((BaseModule) this).mHandler.removeMessages(61);
                AndroidSchedulers.mainThread().scheduleDirect(new n(this));
                String[] strArr = this.mIDCardPaths;
                strArr[1] = generateFilepath;
                callGalleryIDCardPage(strArr);
            }
        }
    }

    private boolean handleSuperNightResultIfNeed() {
        Disposable disposable = this.mSuperNightDisposable;
        if (disposable == null) {
            return false;
        }
        if (!disposable.isDisposed()) {
            this.mSuperNightDisposable.dispose();
        }
        this.mSuperNightDisposable = null;
        boolean z = !this.mWaitingSuperNightResult;
        this.mWaitingSuperNightResult = false;
        if (z) {
            Log.d(TAG, "SuperNight: force trigger shutter animation, sound and post saving");
        }
        stopCpuBoost();
        if (currentIsMainThread()) {
            t(z);
        } else {
            AndroidSchedulers.mainThread().scheduleDirect(new p(this, z));
        }
        return true;
    }

    private void handleSuperNightResultInCaptureMode() {
        if (this.mShowSuperNightHint) {
            ((BaseModule) this).mHandler.post(new k(this));
        }
    }

    /* access modifiers changed from: private */
    public void handleUpdateFaceView(boolean z, boolean z2) {
        boolean isFrontCamera = isFrontCamera();
        if (!z) {
            ((BaseModule) this).mMainProtocol.updateFaceView(z, z2, isFrontCamera, false, -1);
        } else if ((this.mFaceDetectionStarted || isFaceBeautyMode()) && 1 != ((BaseModule) this).mCamera2Device.getFocusMode()) {
            ((BaseModule) this).mMainProtocol.updateFaceView(z, true, isFrontCamera, true, ((BaseModule) this).mCameraDisplayOrientation);
        }
    }

    private void hidePostCaptureAlert() {
        enableCameraControls(true);
        if (((BaseModule) this).mCamera2Device.isSessionReady()) {
            resumePreview();
        } else {
            startPreview();
        }
        ((BaseModule) this).mMainProtocol.setEffectViewVisible(true);
        ((ModeProtocol.BaseDelegate) ModeCoordinatorImpl.getInstance().getAttachProtocol(160)).delegateEvent(6);
    }

    private void hideSceneSelector() {
        ((BaseModule) this).mHandler.post(q.INSTANCE);
    }

    private void initAiSceneParser() {
        this.mFunctionParseAiScene = new FunctionParseAiScene(((BaseModule) this).mModuleIndex, getCameraCapabilities());
        this.mAiSceneDisposable = Flowable.create(new FlowableOnSubscribe<CaptureResult>() {
            /* class com.android.camera.module.Camera2Module.AnonymousClass17 */

            @Override // io.reactivex.FlowableOnSubscribe
            public void subscribe(FlowableEmitter<CaptureResult> flowableEmitter) throws Exception {
                FlowableEmitter unused = Camera2Module.this.mAiSceneFlowableEmitter = flowableEmitter;
            }
        }, BackpressureStrategy.DROP).observeOn(GlobalConstant.sCameraSetupScheduler).map(this.mFunctionParseAiScene).filter(new PredicateFilterAiScene(this, DataRepository.dataItemFeature().qd())).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Integer>() {
            /* class com.android.camera.module.Camera2Module.AnonymousClass16 */

            public void accept(Integer num) {
                Camera2Module.this.consumeAiSceneResult(num.intValue(), false);
            }
        });
    }

    private void initFlashAutoStateForTrack(boolean z) {
        ((BaseModule) this).mFlashAutoModeState = null;
        String componentValue = DataRepository.dataItemConfig().getComponentFlash().getComponentValue(((BaseModule) this).mModuleIndex);
        if (!componentValue.equals("3") && !componentValue.equals(ComponentConfigFlash.FLASH_VALUE_SCREEN_LIGHT_AUTO)) {
            return;
        }
        if (this.mCurrentAsdScene == 9 || z) {
            ((BaseModule) this).mFlashAutoModeState = MistatsConstants.BaseEvent.AUTO_ON;
        } else {
            ((BaseModule) this).mFlashAutoModeState = MistatsConstants.BaseEvent.AUTO_OFF;
        }
    }

    private void initMetaParser() {
        this.mMetaDataDisposable = Flowable.create(new FlowableOnSubscribe<CaptureResult>() {
            /* class com.android.camera.module.Camera2Module.AnonymousClass15 */

            @Override // io.reactivex.FlowableOnSubscribe
            public void subscribe(FlowableEmitter<CaptureResult> flowableEmitter) throws Exception {
                FlowableEmitter unused = Camera2Module.this.mMetaDataFlowableEmitter = flowableEmitter;
            }
        }, BackpressureStrategy.DROP).observeOn(GlobalConstant.sCameraSetupScheduler).map(new FunctionParseAsdFace(this, isFrontCamera())).map(new FunctionParseAsdHdr(this)).map(new FunctionParseAsdUltraWide(((BaseModule) this).mModuleIndex, this, getCameraDevice().getCapabilities().getMiAlgoASDVersion())).map(new FunctionParseAsdLivePhoto(this)).map(new FunctionParseSuperNight(this)).map(new FunctionMiAlgoASDEngine(this)).sample(500, TimeUnit.MILLISECONDS).observeOn(GlobalConstant.sCameraSetupScheduler).map(new FunctionParseAsdScene(this)).observeOn(AndroidSchedulers.mainThread()).onTerminateDetach().subscribe(new AsdSceneConsumer(this));
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

    /* access modifiers changed from: private */
    public boolean is3ALocked() {
        return this.m3ALocked;
    }

    private boolean isCannotGotoGallery() {
        return ((BaseModule) this).mPaused || ((BaseModule) this).isZooming || isKeptBitmapTexture() || this.mMultiSnapStatus || getCameraState() == 0 || isQueueFull() || isInCountDown();
    }

    private boolean isCurrentRawDomainBasedSuperNight() {
        return ((BaseModule) this).mModuleIndex == 173 && DataRepository.dataItemFeature().cf();
    }

    private boolean isEnableQcfaForAlgoUp() {
        if (!((BaseModule) this).mCameraCapabilities.isSupportedQcfa() || !this.mEnableParallelSession) {
            return false;
        }
        if (b.isMTKPlatform()) {
            return CameraSettings.isUltraPixelOn();
        }
        if (isInQCFAMode()) {
            return true;
        }
        return CameraSettings.isUltraPixelOn() && DataRepository.dataItemFeature().nf();
    }

    private boolean isFaceBeautyOn(BeautyValues beautyValues) {
        if (beautyValues == null) {
            return false;
        }
        return beautyValues.isFaceBeautyOn();
    }

    /* access modifiers changed from: private */
    public boolean isFrontMirror() {
        if (!isFrontCamera()) {
            return false;
        }
        if (CameraSettings.isLiveShotOn()) {
            return true;
        }
        return CameraSettings.isFrontMirror();
    }

    private boolean isHeicPreferred() {
        if (this.mIsImageCaptureIntent || !((BaseModule) this).mCameraCapabilities.isHeicSupported() || !DataRepository.dataItemFeature().nb() || !CameraSettings.isHeicImageFormatSelected() || !this.mEnableParallelSession) {
            return false;
        }
        int i = ((BaseModule) this).mModuleIndex;
        return i == 163 || i == 165;
    }

    private boolean isImageSaverFull() {
        ImageSaver imageSaver = ((BaseModule) this).mActivity.getImageSaver();
        if (imageSaver == null) {
            Log.w(TAG, "isParallelQueueFull: ImageSaver is null");
            return false;
        } else if (!imageSaver.isSaveQueueFull()) {
            return false;
        } else {
            Log.d(TAG, "isParallelQueueFull: ImageSaver queue is full");
            return true;
        }
    }

    private boolean isIn3OrMoreSatMode() {
        return 36866 == this.mOperatingMode && HybridZoomingSystem.IS_3_OR_MORE_SAT;
    }

    private boolean isInCountDown() {
        Disposable disposable = this.mCountdownDisposable;
        return disposable != null && !disposable.isDisposed();
    }

    private boolean isInMultiSurfaceSatMode() {
        return ((BaseModule) this).mCamera2Device.isInMultiSurfaceSatMode();
    }

    private boolean isInQCFAMode() {
        return (getModuleIndex() == 163 || getModuleIndex() == 165) && ((BaseModule) this).mCameraCapabilities.isSupportedQcfa() && isFrontCamera() && !DataRepository.dataItemFeature().lc();
    }

    private boolean isLaunchedByMainIntent() {
        Intent intent;
        Camera camera = ((BaseModule) this).mActivity;
        return "android.intent.action.MAIN".equals((camera == null || (intent = camera.getIntent()) == null) ? null : intent.getAction());
    }

    private boolean isLimitSize() {
        return isBackCamera() && !CameraSettings.isUltraPixelOn() && DataRepository.dataItemFeature().Fc();
    }

    private static boolean isLiveShotAvailable(int i) {
        return i == 0 || i == 5 || i == 8;
    }

    private boolean isNeedFixedShotTime() {
        int i;
        boolean z = isParallelSessionEnable() && ((i = ((BaseModule) this).mModuleIndex) == 163 || i == 165) && !isFrontCamera() && ((double) getZoomRatio()) == 1.0d && !isInCountDown() && !((BaseModule) this).mCamera2Device.isNeedFlashOn() && !this.mIsImageCaptureIntent && !CameraSettings.isLiveShotOn() && !this.mIsISORight4HWMFNR && (DataRepository.dataItemFeature().zb() != 0 || DEBUG_ENABLE_DYNAMIC_HHT_FAST_SHOT);
        String str = TAG;
        Log.d(str, "isNeedFixedShotTime nfst:" + z + ", mIsISORight4HWMFNR:" + this.mIsISORight4HWMFNR);
        return z;
    }

    private boolean isParallelCameraSessionMode() {
        return ((BaseModule) this).mCamera2Device != null && ParallelSnapshotManager.getInstance().isParallelCameraDeviceConfiged(((BaseModule) this).mCamera2Device.getCapabilities()) && ((BaseModule) this).mCamera2Device.getSATSubCameraIds() != null && getZoomRatio() < HybridZoomingSystem.FLOAT_ZOOM_RATIO_TELE;
    }

    private boolean isParallelQueueFull() {
        boolean z = false;
        if (!this.mEnableParallelSession || ((BaseModule) this).mActivity.getImageSaver() == null) {
            return false;
        }
        if (isImageSaverFull()) {
            return true;
        }
        LocalParallelService.LocalBinder localBinder = AlgoConnector.getInstance().getLocalBinder();
        if (localBinder != null) {
            z = localBinder.needWaitProcess();
        } else {
            Log.w(TAG, "isParallelQueueFull: NOTICE: CHECK WHY BINDER IS NULL!");
        }
        String str = TAG;
        Log.w(str, "isParallelQueueFull: isNeedWaitProcess:" + z);
        return z;
    }

    private boolean isParallelSessionConfigured() {
        boolean z;
        if (!this.mEnableParallelSession) {
            return true;
        }
        synchronized (this.mParallelSessionLock) {
            z = this.mParallelSessionConfigured;
        }
        return z;
    }

    private boolean isPortraitSuccessHintShowing() {
        return ((ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175)).isPortraitHintVisible();
    }

    private boolean isPreviewThumbnailWhenFlash() {
        if (!((BaseModule) this).mUseLegacyFlashMode) {
            return true;
        }
        return !"3".equals(this.mLastFlashMode) && !"1".equals(this.mLastFlashMode);
    }

    private boolean isQueueFull() {
        return this.mEnableParallelSession ? isParallelQueueFull() : isImageSaverFull();
    }

    private boolean isSensorRawStreamRequired() {
        int i = ((BaseModule) this).mModuleIndex;
        if (i == 167) {
            return DataRepository.dataItemConfig().getComponentConfigRaw().isSwitchOn(167);
        }
        if (i != 173) {
            return false;
        }
        return DataRepository.dataItemFeature().cf();
    }

    private boolean isSuperNightSeEnable() {
        ModeProtocol.MainContentProtocol mainContentProtocol;
        String componentValue = DataRepository.dataItemConfig().getComponentFlash().getComponentValue(((BaseModule) this).mModuleIndex);
        if (componentValue == "1" || componentValue == "2") {
            return false;
        }
        return componentValue != "3" || (mainContentProtocol = ((BaseModule) this).mMainProtocol) == null || !mainContentProtocol.isFaceExists(1);
    }

    private boolean isTestImageCaptureWithoutLocation() {
        Uri uri = this.mSaveUri;
        if (uri == null || !uri.toString().contains("android.providerui.cts.fileprovider")) {
            return !((BaseModule) this).mActivity.getCameraIntentManager().checkIntentLocationPermission(((BaseModule) this).mActivity);
        }
        Log.d(TAG, "isTestImageCaptureWithoutLocation");
        return true;
    }

    private boolean isTriggerQcfaModeChange(boolean z, boolean z2) {
        if (!((BaseModule) this).mCameraCapabilities.isSupportedQcfa()) {
            return false;
        }
        if ((((BaseModule) this).mModuleIndex != 171 || !isBokehFrontCamera()) && DataRepository.dataItemFeature().Ab() < 0 && z && !mIsBeautyFrontOn) {
            if (this.mOperatingMode == 32775) {
                return true;
            }
            DataRepository.dataItemConfig().getComponentHdr().getComponentValue(((BaseModule) this).mModuleIndex);
        }
        return false;
    }

    private boolean isUseSwMfnr() {
        int i;
        if (CameraSettings.isGroupShotOn()) {
            Log.d(TAG, "GroupShot is on");
            return false;
        } else if (!DataRepository.dataItemFeature().wc() && (isUltraWideBackCamera() || isZoomRatioBetweenUltraAndWide())) {
            Log.d(TAG, "SwMfnr force off for ultra wide camera");
            return false;
        } else if (!CameraSettings.isMfnrSatEnable()) {
            Log.d(TAG, "Mfnr not enabled");
            return false;
        } else if (!DataRepository.dataItemFeature().mf()) {
            Log.d(TAG, "SwMfnr is not supported");
            return false;
        } else if (!((BaseModule) this).mMutexModePicker.isNormal()) {
            Log.d(TAG, "Mutex mode is not normal");
            return false;
        } else if (DataRepository.dataItemFeature().wc() && (i = ((BaseModule) this).mModuleIndex) != 167 && i != 173 && !CameraSettings.isSuperNightOn()) {
            Log.d(TAG, "For the devices does not have hardware MFNR, use software MFNR");
            return true;
        } else if (!isFrontCamera() || isDualFrontCamera()) {
            return false;
        } else {
            if (this.mOperatingMode == 32773 && b.Dl()) {
                return true;
            }
            if (this.mOperatingMode != 32773 || b.Dl()) {
                return DataRepository.dataItemFeature().isSupportUltraWide() || b.Ou || b.Tu;
            }
            return false;
        }
    }

    private void keepScreenOnAwhile() {
        ((BaseModule) this).mHandler.sendEmptyMessageDelayed(17, 1000);
    }

    private void lockAEAF() {
        Log.d(TAG, "lockAEAF");
        FocusManager2 focusManager2 = this.mFocusManager;
        if (focusManager2 != null) {
            focusManager2.setAeAwbLock(true);
        }
        this.m3ALocked = true;
    }

    private boolean needActiveASD() {
        return DataRepository.dataItemRunning().getComponentRunningAIWatermark().needActive() && (163 == getModuleIndex());
    }

    private boolean needQuickShot() {
        int i;
        BeautyValues beautyValues;
        boolean z = false;
        if (!this.mBlockQuickShot && !this.mIsImageCaptureIntent && CameraSettings.isCameraQuickShotEnable()) {
            if (enablePreviewAsThumbnail() && (((i = ((BaseModule) this).mModuleIndex) == 163 || i == 165) && getZoomRatio() == 1.0f && !isFrontCamera() && !CameraSettings.isUltraWideConfigOpen(((BaseModule) this).mModuleIndex) && !CameraSettings.isMacroModeEnabled(((BaseModule) this).mModuleIndex) && !((BaseModule) this).mCamera2Device.isNeedFlashOn() && !CameraSettings.isUltraPixelOn() && !CameraSettings.isLiveShotOn() && ((beautyValues = this.mBeautyValues) == null || !beautyValues.isFaceBeautyOn()))) {
                z = true;
            }
            String str = TAG;
            Log.d(str, "needQuickShot bRet:" + z);
        }
        return z;
    }

    private boolean needShowThumbProgressImmediately() {
        return Long.parseLong(getManualValue(CameraSettings.KEY_QC_EXPOSURETIME, getString(R.string.pref_camera_exposuretime_default))) > 250000000 && ((BaseModule) this).mModuleIndex != 173;
    }

    /* access modifiers changed from: private */
    public void onBurstPictureTakenFinished(boolean z) {
        stopMultiSnap();
        ObservableEmitter observableEmitter = this.mBurstEmitter;
        if (observableEmitter != null) {
            observableEmitter.onComplete();
        }
        onPictureTakenFinished(z);
        PerformanceTracker.trackPictureCapture(1);
    }

    private void onShutter(boolean z) {
        if (getCameraState() == 0) {
            Log.d(TAG, "onShutter: preview stopped");
            return;
        }
        this.mShutterCallbackTime = System.currentTimeMillis();
        this.mShutterLag = this.mShutterCallbackTime - this.mCaptureStartTime;
        String str = TAG;
        Log.v(str, "mShutterLag = " + this.mShutterLag + d.H);
        updateEnablePreviewThumbnail(z);
        if (this.mEnabledPreviewThumbnail) {
            ((BaseModule) this).mActivity.getCameraScreenNail().requestReadPixels();
        } else if (((BaseModule) this).mModuleIndex != 173) {
            updateThumbProgress(false);
            animateCapture();
            playCameraSound(0);
        }
        if (!isKeptBitmapTexture() && !this.mMultiSnapStatus && this.mBlockQuickShot && !CameraSettings.isGroupShotOn() && this.mFixedShot2ShotTime == 0) {
            resetStatusToIdle();
        }
    }

    private void parseIntent() {
        CameraIntentManager cameraIntentManager = ((BaseModule) this).mActivity.getCameraIntentManager();
        this.mIsImageCaptureIntent = cameraIntentManager.isImageCaptureIntent();
        if (this.mIsImageCaptureIntent) {
            this.mSaveUri = cameraIntentManager.getExtraSavedUri();
            this.mCropValue = cameraIntentManager.getExtraCropValue();
            this.mIsSaveCaptureImage = cameraIntentManager.getExtraShouldSaveCapture().booleanValue();
            this.mQuickCapture = cameraIntentManager.isQuickCapture().booleanValue();
        }
    }

    private void prepareLLSInCaptureMode() {
        if (this.mIsLLSNeeded) {
            this.mShowLLSHint = true;
            ((ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172)).alertAiDetectTipHint(0, R.string.super_night_hint, -1);
        }
    }

    private void prepareMultiCapture() {
        Log.d(TAG, "prepareMultiCapture");
        this.mFocusManager.removeMessages();
        this.mMultiSnapStatus = true;
        this.mMultiSnapStopRequest = false;
        Util.clearMemoryLimit();
        prepareNormalCapture();
        int burstShootCount = CameraCapabilities.getBurstShootCount();
        if (isUltraWideBackCamera() || isZoomRatioBetweenUltraAndWide()) {
            burstShootCount = Math.min(burstShootCount, 30);
        }
        this.mTotalJpegCallbackNum = burstShootCount;
        ((BaseModule) this).mHandler.removeMessages(49);
        if (!is3ALocked()) {
            this.mFocusManager.onShutter();
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:75:0x01de  */
    private void prepareNormalCapture() {
        boolean z;
        Log.d(TAG, "prepareNormalCapture");
        Location location = null;
        if (this.mMagneticSensorDetect != null && ((BaseModule) this).mActivity.getSensorStateManager().isMagneticFieldUncalibratedEnable()) {
            this.mMagneticSensorDetect.updateMagneticDetection();
            if (isHdrSceneDetectionStarted() && !this.mMagneticSensorDetect.isLockHDRChecker()) {
                if (((BaseModule) this).mMutexModePicker.isHdr()) {
                    ((BaseModule) this).mCamera2Device.getCameraConfigs().setHdrCheckerEvValue(null);
                    ((BaseModule) this).mCamera2Device.getCameraConfigs().setHdrCheckerSceneType(0);
                    ((BaseModule) this).mCamera2Device.getCameraConfigs().setHdrCheckerAdrc(0);
                    if (!this.mIsInHDR) {
                        onHdrSceneChanged(false);
                    }
                } else if (this.mIsInHDR) {
                    onHdrSceneChanged(true);
                }
            }
            this.mMagneticSensorDetect.recordMagneticInfo();
        }
        initFlashAutoStateForTrack(((BaseModule) this).mCamera2Device.isNeedFlashOn());
        this.mEnabledPreviewThumbnail = false;
        this.mTotalJpegCallbackNum = 1;
        this.mReceivedJpegCallbackNum = 0;
        this.mCaptureStartTime = System.currentTimeMillis();
        ((BaseModule) this).mCamera2Device.setCaptureTime(this.mCaptureStartTime);
        ScenarioTrackUtil.trackCaptureTimeStart(isFrontCamera(), ((BaseModule) this).mModuleIndex);
        ScenarioTrackUtil.trackShotToGalleryStart(isFrontCamera(), ((BaseModule) this).mModuleIndex, this.mCaptureStartTime);
        ScenarioTrackUtil.trackShotToViewStart(isFrontCamera(), ((BaseModule) this).mModuleIndex, this.mCaptureStartTime);
        this.mLastCaptureTime = this.mCaptureStartTime;
        setCameraState(3);
        if (((BaseModule) this).mModuleIndex == 182) {
            this.mJpegRotation = 0;
        } else {
            this.mJpegRotation = Util.getJpegRotation(((BaseModule) this).mBogusCameraId, ((BaseModule) this).mOrientation);
        }
        Log.d(TAG, "prepareNormalCapture: mOrientation = " + ((BaseModule) this).mOrientation + ", mJpegRotation = " + this.mJpegRotation);
        ((BaseModule) this).mCamera2Device.setJpegRotation(this.mJpegRotation);
        if (!isTestImageCaptureWithoutLocation()) {
            location = LocationManager.instance().getCurrentLocation();
        }
        ((BaseModule) this).mCamera2Device.setGpsLocation(location);
        this.mLocation = location;
        boolean z2 = SystemProperties.getBoolean("FACE_EXIST", false);
        ModeProtocol.MainContentProtocol mainContentProtocol = ((BaseModule) this).mMainProtocol;
        if ((mainContentProtocol == null || !mainContentProtocol.isFaceExists(1)) && !z2) {
            ((BaseModule) this).mCamera2Device.setIsFaceExist(false);
        } else {
            ((BaseModule) this).mCamera2Device.setIsFaceExist(true);
        }
        updateSuperNight();
        updateFrontMirror();
        updateBeauty();
        updateSRAndMFNR();
        updateShotDetermine();
        updateCaptureTriggerFlow();
        int i = CameraSettings.isLiveShotOn() && isLiveShotAvailable(((BaseModule) this).mCamera2Device.getCameraConfigs().getShotType()) ? 256 : ((BaseModule) this).mOutputPictureFormat;
        String generateFileTitle = generateFileTitle();
        Log.d(TAG, "prepareNormalCapture title = " + generateFileTitle);
        ((BaseModule) this).mCamera2Device.setShotSavePath(Storage.generateFilepath4Image(generateFileTitle, CompatibilityUtils.isHeicImageFormat(i)), !this.mMultiSnapStatus && (this.mEnableParallelSession || this.mEnableShot2Gallery));
        int i2 = ((BaseModule) this).mModuleIndex;
        if (i2 == 163 || i2 == 165 || i2 == 171 || i2 == 175) {
            boolean z3 = CameraSettings.isCameraQuickShotEnable() || CameraSettings.isCameraQuickShotAnimateEnable();
            boolean Kd = DataRepository.dataItemFeature().Kd();
            if (z3 || Kd) {
                z = true;
                ((BaseModule) this).mCamera2Device.setNeedSequence(z);
                if (enablePreviewAsThumbnail() || ((BaseModule) this).mMutexModePicker.isHdr()) {
                    this.mQuickShotAnimateEnable = false;
                } else {
                    this.mQuickShotAnimateEnable = CameraSettings.isCameraQuickShotAnimateEnable();
                }
                setWaterMark();
                setPictureOrientation();
                updateJpegQuality();
                updateAlgorithmName();
                if (needShowThumbProgressImmediately()) {
                    updateThumbProgress(false);
                }
                prepareSuperNight();
                prepareSuperNightInCaptureMode();
                prepareLLSInCaptureMode();
                checkMoreFrameCaptureLockAFAE(true);
            }
        }
        z = false;
        ((BaseModule) this).mCamera2Device.setNeedSequence(z);
        if (enablePreviewAsThumbnail()) {
        }
        this.mQuickShotAnimateEnable = false;
        setWaterMark();
        setPictureOrientation();
        updateJpegQuality();
        updateAlgorithmName();
        if (needShowThumbProgressImmediately()) {
        }
        prepareSuperNight();
        prepareSuperNightInCaptureMode();
        prepareLLSInCaptureMode();
        checkMoreFrameCaptureLockAFAE(true);
    }

    private void prepareSuperNight() {
        if (((BaseModule) this).mModuleIndex == 173) {
            ModeProtocol.RecordState recordState = (ModeProtocol.RecordState) ModeCoordinatorImpl.getInstance().getAttachProtocol(212);
            if (recordState != null) {
                recordState.onPrepare();
                recordState.onStart();
            }
            startCpuBoost();
            if (this.mSuperNightEventConsumer == null) {
                this.mSuperNightEventConsumer = new SuperNightEventConsumer();
            }
            this.mSuperNightDisposable = Observable.just(300, 2000).flatMap(new Function<Integer, ObservableSource<Integer>>() {
                /* class com.android.camera.module.Camera2Module.AnonymousClass7 */

                public ObservableSource<Integer> apply(Integer num) throws Exception {
                    return Observable.just(num).delaySubscription((long) num.intValue(), TimeUnit.MILLISECONDS);
                }
            }).observeOn(AndroidSchedulers.mainThread()).subscribe(this.mSuperNightEventConsumer);
        }
    }

    private void prepareSuperNightInCaptureMode() {
        if (CameraSettings.isSuperNightOn()) {
            this.mShowSuperNightHint = true;
            ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
            if (topAlert != null) {
                topAlert.alertAiDetectTipHint(0, R.string.super_night_hint, -1);
            }
        }
    }

    private void previewWhenSessionSuccess() {
        setCameraState(1);
        this.mFaceDetectionEnabled = false;
        updatePreferenceInWorkThread(UpdateConstant.CAMERA_TYPES_ON_PREVIEW_SUCCESS);
        if (ModuleManager.isProPhotoModule()) {
            updatePreferenceInWorkThread(UpdateConstant.CAMERA_TYPES_MANUALLY);
        }
    }

    private void realConsumeAiSceneResult(int i, boolean z) {
        int i2;
        if (i == 36) {
            i = 0;
        }
        if (this.mCurrentAiScene == i) {
            if (i == 0) {
                ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
                if (topAlert != null && topAlert.getCurrentAiSceneLevel() == i) {
                    return;
                }
            } else {
                return;
            }
        }
        if (!isDoingAction() && isAlive() && !((BaseModule) this).mActivity.isActivityPaused()) {
            if (!z || !this.isResetFromMutex) {
                if (!z) {
                    this.isResetFromMutex = false;
                }
                Log.d(TAG, "consumeAiSceneResult: " + i + "; isReset: " + z);
                ModeProtocol.TopAlert topAlert2 = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
                ModeProtocol.ConfigChanges configChanges = (ModeProtocol.ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164);
                boolean z2 = true;
                if (!isFrontCamera() && !b.gv) {
                    ((BaseModule) this).mCamera2Device.setCameraAI30(i == 25);
                }
                if (this.mIsGoogleLensAvailable) {
                    if (!LensAgent.isConflictAiScene(this.mCurrentAiScene) && LensAgent.isConflictAiScene(i)) {
                        this.mIsAiConflict = true;
                        showOrHideChip(false);
                    } else if (LensAgent.isConflictAiScene(this.mCurrentAiScene) && !LensAgent.isConflictAiScene(i)) {
                        this.mIsAiConflict = false;
                        showOrHideChip(true);
                    }
                }
                closeMoonMode(i, 8);
                closeBacklightTip(i, 8);
                if (i != -1) {
                    if (i == 1) {
                        int parseInt = Integer.parseInt(CameraSettings.getSharpness());
                        if (parseInt < 6) {
                            parseInt++;
                        }
                        this.mCurrentAiScene = i;
                        configChanges.restoreAllMutexElement("e");
                        ((BaseModule) this).mCamera2Device.setSharpness(parseInt);
                    } else if (i == 10) {
                        String componentValue = DataRepository.dataItemConfig().getComponentFlash().getComponentValue(((BaseModule) this).mModuleIndex);
                        if (DataRepository.dataItemFeature().vf() && (componentValue.equals("3") || componentValue.equals("1"))) {
                            configChanges.closeMutexElement("e", 193);
                            setFlashMode("0");
                        }
                        updateMfnr(true);
                        updateOIS();
                    } else if (i == 15 || i == 19) {
                        int parseInt2 = Integer.parseInt(CameraSettings.getSharpness());
                        if (parseInt2 < 6) {
                            parseInt2++;
                        }
                        ((BaseModule) this).mCamera2Device.setSharpness(parseInt2);
                        this.mCurrentAiScene = i;
                        configChanges.restoreAllMutexElement("e");
                    } else if (i != 3) {
                        if (i == 4) {
                            ((BaseModule) this).mCamera2Device.setContrast(Integer.parseInt(CameraSettings.getContrast()));
                            this.mCurrentAiScene = i;
                            configChanges.restoreAllMutexElement("e");
                            updateSuperResolution();
                        } else if (i == 7 || i == 8) {
                            this.mCurrentAiScene = i;
                            configChanges.restoreAllMutexElement("e");
                        } else {
                            if (i != 34) {
                                if (i == 35) {
                                    if (showMoonMode(false)) {
                                        topAlert2.setAiSceneImageLevel(i);
                                        trackAISceneChanged(((BaseModule) this).mModuleIndex, i);
                                        this.mCurrentAiScene = i;
                                        return;
                                    }
                                    z2 = false;
                                } else if (i != 37) {
                                    if (i != 38) {
                                        switch (i) {
                                            case 25:
                                                trackAISceneChanged(((BaseModule) this).mModuleIndex, 25);
                                                topAlert2.setAiSceneImageLevel(25);
                                                setAiSceneEffect(25);
                                                this.mCurrentAiScene = i;
                                                updateHDRPreference();
                                                configChanges.restoreAllMutexElement("e");
                                                resumePreviewInWorkThread();
                                                return;
                                            case 26:
                                            case 27:
                                            case 28:
                                            case 29:
                                            case 30:
                                            case 31:
                                                if (!DataRepository.dataItemFeature().Me()) {
                                                    configChanges.restoreAllMutexElement("e");
                                                    updatePreferenceInWorkThread(UpdateConstant.AI_SCENE_CONFIG);
                                                    break;
                                                } else {
                                                    this.mCurrentAiScene = i;
                                                    configChanges.restoreAllMutexElement("e");
                                                    break;
                                                }
                                            default:
                                                updateHDRPreference();
                                                configChanges.restoreAllMutexElement("e");
                                                updatePreferenceInWorkThread(UpdateConstant.AI_SCENE_CONFIG);
                                                break;
                                        }
                                    } else if (DataRepository.dataItemFeature().qd() && (((i2 = ((BaseModule) this).mModuleIndex) == 163 || i2 == 165) && !CameraSettings.isMacroModeEnabled(((BaseModule) this).mModuleIndex) && !CameraSettings.isUltraPixelPortraitFrontOn())) {
                                        trackAISceneChanged(((BaseModule) this).mModuleIndex, i);
                                        topAlert2.setAiSceneImageLevel(i);
                                        this.mCurrentAiScene = i;
                                        return;
                                    }
                                    i = 0;
                                }
                            }
                            this.mCurrentAiScene = i;
                            z2 = false;
                        }
                    } else if (DataRepository.dataItemFeature().hd() && isBackCamera() && !MiAlgoAsdSceneProfile.isAlreadyTip()) {
                        MiAlgoAsdSceneProfile.setTipEnable(MiAlgoAsdSceneProfile.COMPAT_FOOD, true);
                        updateTipMessage(20, R.string.recommend_soft_light_tip, 2);
                    }
                    trackAISceneChanged(((BaseModule) this).mModuleIndex, i);
                    topAlert2.setAiSceneImageLevel(i);
                    if (z2) {
                        setAiSceneEffect(i);
                    }
                    if (!z) {
                        this.mCurrentAiScene = i;
                    }
                    updateBeauty();
                    resumePreviewInWorkThread();
                    return;
                }
                showBacklightTip();
                topAlert2.setAiSceneImageLevel(23);
                this.mCurrentAiScene = i;
            }
        }
    }

    private void resetAiSceneInHdrOrFlashOn() {
        int i;
        if (this.mAiSceneEnabled && !this.isResetFromMutex && (i = this.mCurrentAiScene) != 0) {
            if (i == -1 || i == 10) {
                ((BaseModule) this).mHandler.post(new Runnable() {
                    /* class com.android.camera.module.Camera2Module.AnonymousClass18 */

                    public void run() {
                        Camera2Module.this.consumeAiSceneResult(0, true);
                        boolean unused = Camera2Module.this.isResetFromMutex = true;
                    }
                });
            }
        }
    }

    private void resetAsdSceneInHdrOrFlashChange() {
        int i;
        if (b.Ll() && isFrontCamera() && (i = this.mCurrentAsdScene) != -1 && i == 9) {
            ((BaseModule) this).mHandler.post(new Runnable() {
                /* class com.android.camera.module.Camera2Module.AnonymousClass19 */

                public void run() {
                    Camera2Module.this.consumeAsdSceneResult(-1);
                }
            });
        }
    }

    private void resetScreenOn() {
        Handler handler = ((BaseModule) this).mHandler;
        if (handler != null) {
            handler.removeMessages(17);
            ((BaseModule) this).mHandler.removeMessages(2);
        }
    }

    private void resumePreviewInWorkThread() {
        updatePreferenceInWorkThread(new int[0]);
    }

    private void setAiSceneEffect(int i) {
        String str = TAG;
        Log.d(str, "setAiSceneEffect: " + i);
        if (EffectController.getInstance().getAiColorCorrectionVersion() != 0 || !DataRepository.dataItemFeature().xe() || !CameraSettings.isBackCamera() || i != 25) {
            if (CameraSettings.isFrontCamera() || isPortraitMode()) {
                if (i != 0) {
                    Log.d(TAG, "setAiSceneEffect: front camera or portrait mode nonsupport!");
                    return;
                } else if (CameraSettings.getPortraitLightingPattern() != 0) {
                    Log.d(TAG, "setAiSceneEffect: scene = 0 but portrait lighting is on...");
                    return;
                }
            }
            int shaderEffect = CameraSettings.getShaderEffect();
            if (FilterInfo.getCategory(shaderEffect) == 5 || shaderEffect == FilterInfo.FILTER_ID_NONE) {
                ArrayList<FilterInfo> filterInfo = EffectController.getInstance().getFilterInfo(5);
                if (i < 0 || i > filterInfo.size()) {
                    String str2 = TAG;
                    Log.e(str2, "setAiSceneEffect: scene unknown: " + i);
                    return;
                }
                int id = filterInfo.get(i).getId();
                EffectController.getInstance().setAiSceneEffect(id);
                this.mHasAiSceneFilterEffect = id != FilterInfo.FILTER_ID_NONE;
                return;
            }
            return;
        }
        Log.d(TAG, "supportAi30: AI 3.0 back camera in HUMAN SCENE not apply filter!");
    }

    private void setEffectFilter(int i) {
        String str = TAG;
        Log.d(str, "setEffectFilter: " + i);
        EffectController.getInstance().setEffect(i);
        this.mFilterId = i;
        CircularMediaRecorder circularMediaRecorder = this.mCircularMediaRecorder;
        if (circularMediaRecorder != null) {
            circularMediaRecorder.setFilterId(i);
        }
    }

    private void setLightingEffect() {
        int shaderEffect = CameraSettings.getShaderEffect();
        if (FilterInfo.getCategory(shaderEffect) == 5 || shaderEffect == FilterInfo.FILTER_ID_NONE) {
            int portraitLightingPattern = CameraSettings.getPortraitLightingPattern();
            String str = TAG;
            Log.d(str, "setLightingEffect: " + portraitLightingPattern);
            EffectController.getInstance().setLightingEffect(EffectController.getInstance().getFilterInfo(6).get(portraitLightingPattern).getId());
        }
    }

    /* access modifiers changed from: private */
    public void setOrientation(int i, int i2) {
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

    /* access modifiers changed from: private */
    public void setOrientationParameter() {
        if (!isDeparted()) {
            if (!(((BaseModule) this).mCamera2Device == null || ((BaseModule) this).mOrientation == -1)) {
                if (!isFrameAvailable() || getCameraState() != 1) {
                    GlobalConstant.sCameraSetupScheduler.scheduleDirect(new r(this));
                } else {
                    updatePreferenceInWorkThread(35);
                }
            }
            CircularMediaRecorder circularMediaRecorder = this.mCircularMediaRecorder;
            if (circularMediaRecorder != null) {
                circularMediaRecorder.setOrientationHint(((BaseModule) this).mOrientationCompensation);
            }
        }
    }

    private void setPictureOrientation() {
        this.mShootRotation = ((BaseModule) this).mActivity.getSensorStateManager().isDeviceLying() ? (float) ((BaseModule) this).mOrientation : ((BaseModule) this).mDeviceRotation;
        int i = ((BaseModule) this).mOrientation;
        if (i == -1) {
            i = 0;
        }
        this.mShootOrientation = i;
    }

    private void setPortraitSuccessHintVisible(int i) {
        ((ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175)).setPortraitHintVisible(i);
    }

    private boolean setSceneMode(String str) {
        int parseInt = Util.parseInt(str, -1);
        ((BaseModule) this).mCamera2Device.setSceneMode(parseInt);
        if (!Util.isSupported(parseInt, ((BaseModule) this).mCameraCapabilities.getSupportedSceneModes())) {
            return false;
        }
        String str2 = TAG;
        Log.d(str2, "sceneMode=" + str);
        return true;
    }

    private void setVideoSize(int i, int i2) {
        if (((BaseModule) this).mCameraDisplayOrientation % 180 == 0) {
            this.mVideoSize = new CameraSize(i, i2);
        } else {
            this.mVideoSize = new CameraSize(i2, i);
        }
    }

    private void setWaterMark() {
        if (this.mMultiSnapStatus || DataRepository.dataItemFeature().Ve() || ((!this.mEnableParallelSession && (((BaseModule) this).mModuleIndex == 165 || CameraSettings.getShaderEffect() != FilterInfo.FILTER_ID_NONE || this.mHasAiSceneFilterEffect || CameraSettings.isTiltShiftOn())) || (this.mEnableParallelSession && (!DataRepository.dataItemFeature().We() || ((BaseModule) this).mModuleIndex == 171)))) {
            ((BaseModule) this).mCamera2Device.setDualCamWaterMarkEnable(false);
            ((BaseModule) this).mCamera2Device.setTimeWaterMarkEnable(false);
            return;
        }
        if (CameraSettings.isDualCameraWaterMarkOpen()) {
            ((BaseModule) this).mCamera2Device.setDualCamWaterMarkEnable(true);
        } else if (CameraSettings.isFrontCameraWaterMarkOpen()) {
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

    private boolean shouldApplyNormalWideLDC() {
        if (CameraSettings.shouldNormalWideLDCBeVisibleInMode(((BaseModule) this).mModuleIndex) && !CameraSettings.isMacroModeEnabled(((BaseModule) this).mModuleIndex) && ((BaseModule) this).mActualCameraId != Camera2DataContainer.getInstance().getUltraWideCameraId() && !CameraSettings.isUltraPixelOn() && !isZoomRatioBetweenUltraAndWide()) {
            return CameraSettings.isNormalWideLDCEnabled();
        }
        return false;
    }

    private boolean shouldApplyUltraWideLDC() {
        if (!CameraSettings.shouldUltraWideLDCBeVisibleInMode(((BaseModule) this).mModuleIndex)) {
            return false;
        }
        if (CameraSettings.isMacroModeEnabled(((BaseModule) this).mModuleIndex) && !DataRepository.dataItemFeature().m1if()) {
            return true;
        }
        if (((BaseModule) this).mActualCameraId == Camera2DataContainer.getInstance().getUltraWideCameraId()) {
            return CameraSettings.isUltraWideLDCEnabled();
        }
        if (isZoomRatioBetweenUltraAndWide()) {
            return CameraSettings.isUltraWideLDCEnabled();
        }
        return false;
    }

    private boolean shouldChangeAiScene(int i) {
        if (isDoingAction() || !isAlive() || this.mCurrentDetectedScene == i || System.currentTimeMillis() - this.mLastChangeSceneTime <= 300) {
            return false;
        }
        this.mCurrentDetectedScene = i;
        this.mLastChangeSceneTime = System.currentTimeMillis();
        return true;
    }

    private boolean shouldCheckLLS() {
        return ((BaseModule) this).mCameraCapabilities.isLLSSupported() && DataRepository.dataItemFeature().Se();
    }

    private boolean shouldDoMultiFrameCapture() {
        boolean z = false;
        if (!this.mIsMoonMode || DataRepository.dataItemFeature().Jd()) {
            Camera2Proxy camera2Proxy = ((BaseModule) this).mCamera2Device;
            if (camera2Proxy == null || ((BaseModule) this).mCameraCapabilities == null || !camera2Proxy.useLegacyFlashStrategy() || !((BaseModule) this).mCamera2Device.isNeedFlashOn() || !((BaseModule) this).mCameraCapabilities.isFlashSupported()) {
                if (((BaseModule) this).mModuleIndex == 167 && DataRepository.dataItemFeature().af() && ((BaseModule) this).mCamera2Device.getCameraConfigs().isSuperResolutionEnabled()) {
                    return true;
                }
                if (((BaseModule) this).mMutexModePicker.isHdr() || this.mShouldDoMFNR || ((BaseModule) this).mMutexModePicker.isSuperResolution() || CameraSettings.isGroupShotOn()) {
                    z = true;
                }
                Log.d(TAG, "shouldDoMultiFrameCapture: " + z);
                return z;
            }
            Log.d(TAG, "shouldDoMultiFrameCapture: return false in case of flash");
            return false;
        }
        Log.d(TAG, "shouldDoMultiFrameCapture: return false in moon mode");
        return false;
    }

    private void showDocumentDetectBlurHint() {
        FragmentTopConfig fragmentTopConfig;
        if (!Util.isGlobalVersion() && (fragmentTopConfig = (FragmentTopConfig) ModeCoordinatorImpl.getInstance().getAttachProtocol(172)) != null) {
            fragmentTopConfig.alertAiDetectTipHint(0, R.string.document_blur_warn, 3000);
            CameraStatUtils.trackDocumentDetectBlurHintShow();
        }
    }

    private boolean showMoonMode(boolean z) {
        ModeProtocol.TopAlert topAlert;
        if (CameraSettings.isUltraWideConfigOpen(getModuleIndex()) || CameraSettings.isMacroModeEnabled(((BaseModule) this).mModuleIndex) || (topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172)) == null) {
            return false;
        }
        this.mEnteringMoonMode = true;
        topAlert.alertMoonModeSelector(0, z);
        if (!z) {
            updateMoonNight();
        } else {
            updateMoon(true);
        }
        if (topAlert.isHDRShowing()) {
            topAlert.alertHDR(8, false, false);
        }
        String str = TAG;
        Log.d(str, "(moon_mode) show moon mode,button check status:" + z);
        return true;
    }

    /* access modifiers changed from: private */
    public void showOrHideLoadingProgress(boolean z) {
        ModeProtocol.ActionProcessing actionProcessing = (ModeProtocol.ActionProcessing) ModeCoordinatorImpl.getInstance().getAttachProtocol(162);
        if (actionProcessing != null) {
            actionProcessing.showOrHideLoadingProgress(z);
        }
    }

    private void showPostCaptureAlert() {
        enableCameraControls(false);
        this.mFocusManager.removeMessages();
        stopFaceDetection(true);
        pausePreview();
        ((BaseModule) this).mMainProtocol.setEffectViewVisible(false);
        ((ModeProtocol.BaseDelegate) ModeCoordinatorImpl.getInstance().getAttachProtocol(160)).delegateEvent(6);
        resetMetaDataManager();
    }

    private void startCount(final int i, int i2) {
        if (checkShutterCondition()) {
            setTriggerMode(i2);
            tryRemoveCountDownMessage();
            String str = TAG;
            Log.d(str, "startCount: " + i);
            Observable.interval(1, TimeUnit.SECONDS).take((long) i).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Long>() {
                /* class com.android.camera.module.Camera2Module.AnonymousClass9 */

                @Override // io.reactivex.Observer
                public void onComplete() {
                    Camera2Module.this.tryRemoveCountDownMessage();
                    if (Camera2Module.this.isAlive()) {
                        Camera2Module.this.onShutterButtonFocus(true, 3);
                        Camera2Module camera2Module = Camera2Module.this;
                        camera2Module.startNormalCapture(camera2Module.getTriggerMode());
                        Camera2Module.this.onShutterButtonFocus(false, 0);
                        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
                        if (topAlert != null) {
                            int unused = Camera2Module.this.mCurrentAsdScene = -1;
                            topAlert.reInitAlert(true);
                        }
                    }
                }

                @Override // io.reactivex.Observer
                public void onError(Throwable th) {
                }

                public void onNext(Long l) {
                    int intValue = i - (l.intValue() + 1);
                    if (intValue > 0) {
                        Camera2Module.this.playCameraSound(5);
                        ((BaseModule) Camera2Module.this).mMainProtocol.showDelayNumber(intValue);
                    }
                }

                @Override // io.reactivex.Observer
                public void onSubscribe(Disposable disposable) {
                    Disposable unused = Camera2Module.this.mCountdownDisposable = disposable;
                    Camera2Module.this.playCameraSound(7);
                    ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
                    if (topAlert != null) {
                        AndroidSchedulers.mainThread().scheduleDirect(new d(topAlert), 120, TimeUnit.MILLISECONDS);
                    }
                    ((BaseModule) Camera2Module.this).mMainProtocol.clearFocusView(7);
                    ((BaseModule) Camera2Module.this).mMainProtocol.showDelayNumber(i);
                }
            });
        }
    }

    private void startCpuBoost() {
        if (this.mCpuBoost == null && b.isMTKPlatform()) {
            this.mCpuBoost = new MtkBoost();
        }
        BaseBoostFramework baseBoostFramework = this.mCpuBoost;
        if (baseBoostFramework != null) {
            baseBoostFramework.startBoost();
        }
    }

    /* access modifiers changed from: private */
    public void startLensActivity() {
        if (CameraSettings.supportGoogleLens()) {
            Camera camera = ((BaseModule) this).mActivity;
            if (camera != null) {
                camera.startLensActivity();
                return;
            }
            return;
        }
        Log.d(TAG, "start ai lens");
        try {
            Intent intent = new Intent();
            intent.setAction("android.media.action.XIAOAI_CONTROL");
            intent.setPackage(CameraSettings.AI_LENS_PACKAGE);
            intent.putExtra("preview_width", ((BaseModule) this).mPreviewSize.width);
            intent.putExtra("preview_height", ((BaseModule) this).mPreviewSize.height);
            getActivity().startActivity(intent);
            getActivity().overridePendingTransition(R.anim.anim_fade_in, R.anim.anim_fade_out);
        } catch (Exception e2) {
            Log.e(TAG, "onClick: occur a exception", e2);
        }
    }

    private void startLiveShotAnimation() {
        synchronized (this.mCircularMediaRecorderStateLock) {
            if (!(this.mCircularMediaRecorder == null || ((BaseModule) this).mHandler == null)) {
                ((BaseModule) this).mHandler.post(new Runnable() {
                    /* class com.android.camera.module.Camera2Module.AnonymousClass5 */

                    public void run() {
                        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
                        if (topAlert != null) {
                            topAlert.startLiveShotAnimation();
                        }
                    }
                });
            }
        }
    }

    /* access modifiers changed from: private */
    public void startNormalCapture(int i) {
        String str = TAG;
        Log.d(str, "startNormalCapture mode -> " + i);
        ((BaseModule) this).mActivity.getScreenHint().updateHint();
        if (Storage.isLowStorageAtLastPoint()) {
            String str2 = TAG;
            Log.i(str2, "Not enough space or storage not ready. remaining=" + Storage.getLeftSpace());
            return;
        }
        prepareNormalCapture();
        if (!CameraSettings.isGroupShotOn() || isParallelSessionEnable()) {
            ((BaseModule) this).mHandler.sendEmptyMessageDelayed(50, calculateTimeout(((BaseModule) this).mModuleIndex));
            ((BaseModule) this).mCamera2Device.setQuickShotAnimation(this.mQuickShotAnimateEnable);
            if (DataRepository.dataItemFeature().Gd()) {
                if ((getModuleIndex() == 163 || getModuleIndex() == 165) && getZoomRatio() == 1.0f) {
                    ((BaseModule) this).mCamera2Device.setFlawDetectEnable(true);
                } else {
                    ((BaseModule) this).mCamera2Device.setFlawDetectEnable(false);
                }
            }
            ((BaseModule) this).mCamera2Device.takePicture(this, ((BaseModule) this).mActivity.getImageSaver());
            if (!needQuickShot() || i == 90 || this.mFixedShot2ShotTime != -1) {
                this.mBlockQuickShot = true;
                String str3 = TAG;
                Log.d(str3, "isParallelSessionEnable:" + isParallelSessionEnable() + ", and block quick shot");
            } else {
                Log.d(TAG, "startNormalCapture force set CameraStateConstant.IDLE");
                setCameraState(1);
                enableCameraControls(true);
            }
            if (isFrontCamera() && DataRepository.dataItemFeature().Kd() && 32775 != this.mOperatingMode) {
                ((BaseModule) this).mCamera2Device.registerCaptureCallback(new c(this));
                return;
            }
            return;
        }
        ((BaseModule) this).mCamera2Device.captureGroupShotPictures(this, ((BaseModule) this).mActivity.getImageSaver(), this.mTotalJpegCallbackNum, ((BaseModule) this).mActivity);
        this.mBlockQuickShot = true;
    }

    private void stopCpuBoost() {
        if (this.mCpuBoost != null && b.isMTKPlatform()) {
            this.mCpuBoost.stopBoost();
            this.mCpuBoost = null;
        }
    }

    /* access modifiers changed from: private */
    public void stopMultiSnap() {
        Log.d(TAG, "stopMultiSnap: start");
        ((BaseModule) this).mHandler.removeMessages(49);
        if (this.mMultiSnapStatus) {
            this.mLastCaptureTime = System.currentTimeMillis();
            Camera2Proxy camera2Proxy = ((BaseModule) this).mCamera2Device;
            if (camera2Proxy != null && this.mMultiSnapStatus) {
                camera2Proxy.captureAbortBurst();
                this.mMultiSnapStatus = false;
            }
            boolean z = true;
            int i = !((BaseModule) this).mMutexModePicker.isUbiFocus() ? this.mReceivedJpegCallbackNum : 1;
            boolean z2 = !((BaseModule) this).mMutexModePicker.isUbiFocus();
            HashMap hashMap = new HashMap();
            hashMap.put(MistatsConstants.Manual.PARAM_3A_LOCKED, Boolean.valueOf(this.m3ALocked));
            trackGeneralInfo(hashMap, i, z2, this.mBeautyValues, this.mLocation != null, this.mCurrentAiScene);
            MistatsWrapper.PictureTakenParameter pictureTakenParameter = new MistatsWrapper.PictureTakenParameter();
            pictureTakenParameter.takenNum = i;
            pictureTakenParameter.burst = z2;
            if (this.mLocation == null) {
                z = false;
            }
            pictureTakenParameter.location = z;
            pictureTakenParameter.aiSceneName = getCurrentAiSceneName();
            pictureTakenParameter.isEnteringMoon = this.mEnteringMoonMode;
            pictureTakenParameter.isSelectMoonMode = this.mIsMoonMode;
            pictureTakenParameter.isSuperNightInCaptureMode = this.mShowSuperNightHint;
            pictureTakenParameter.beautyValues = this.mBeautyValues;
            trackPictureTaken(pictureTakenParameter);
            animateCapture();
            this.mUpdateImageTitle = false;
            ((BaseModule) this).mHandler.sendEmptyMessageDelayed(48, 800);
        }
    }

    private void trackAISceneChanged(int i, int i2) {
        CameraStatUtils.trackAISceneChanged(i, i2, getResources());
    }

    private void trackBeautyInfo(int i, boolean z, BeautyValues beautyValues) {
        CameraStatUtils.trackBeautyInfo(i, z ? "front" : MistatsConstants.BaseEvent.BACK, beautyValues);
    }

    private void trackCaptureModuleInfo(Map map, int i, boolean z) {
        if (map == null) {
            map = new HashMap();
        }
        DataItemRunning dataItemRunning = DataRepository.dataItemRunning();
        DataRepository.dataItemConfig();
        int triggerMode = getTriggerMode();
        boolean isFrontCamera = isFrontCamera();
        int i2 = ((BaseModule) this).mModuleIndex;
        map.put("attr_trigger_mode", CameraStatUtils.triggerModeToName(triggerMode));
        String str = "on";
        map.put(MistatsConstants.LiveShotAttr.PARAM_LIVESHOT, CameraSettings.isLiveShotOn() ? str : "off");
        map.put(MistatsConstants.BaseEvent.QUALITY, CameraSettings.getEncodingQuality(z).name().toLowerCase());
        map.put(MistatsConstants.Setting.PARAM_TIME_WATERMARK, CameraSettings.isTimeWaterMarkOpen() ? str : "off");
        map.put(MistatsConstants.Setting.PARAM_DEVICE_WATERMARK, CameraSettings.isDualCameraWaterMarkOpen() || CameraSettings.isFrontCameraWaterMarkOpen() ? str : "off");
        if (!isFrontCamera) {
            map.put(MistatsConstants.Setting.PARAM_TILTSHIFT, (z || !CameraSettings.isTiltShiftOn()) ? "off" : dataItemRunning.getComponentRunningTiltValue().getComponentValue(i2));
            if (z || !CameraSettings.isGradienterOn()) {
                str = "off";
            }
            map.put(MistatsConstants.Setting.PARAM_GRADIENTER, str);
            if (DataRepository.dataItemFeature().ed()) {
                map.put(MistatsConstants.Setting.PARAM_DOCUMENT_MODE, CameraStatUtils.getDocumentModeValue(((BaseModule) this).mModuleIndex));
            }
        }
        if (EffectController.getInstance().getAiColorCorrectionVersion() == 1) {
            map.put(MistatsConstants.Setting.PARAM_AICC, Boolean.valueOf(this.mAiSceneEnabled));
        } else {
            map.put(MistatsConstants.Setting.PARAM_AICC, false);
        }
        if (isHeicPreferred()) {
            map.put(MistatsConstants.Setting.PARAM_HEIC, Boolean.valueOf(CompatibilityUtils.isHeicImageFormat(((BaseModule) this).mOutputPictureFormat)));
        } else {
            map.put(MistatsConstants.Setting.PARAM_HEIC, false);
        }
        MistatsWrapper.mistatEvent(MistatsConstants.ModuleName.CAPTURE, map);
        if (z) {
            if (CameraSettings.isPressDownCapture() && i > 1) {
                i--;
            }
            HashMap hashMap = new HashMap();
            hashMap.put(MistatsConstants.CaptureSence.PARAM_BURST_COUNT, CameraStatUtils.burstShotNumToName(i));
            MistatsWrapper.mistatEventSimple(MistatsConstants.CaptureSence.KEY_BURST_SHOT_TIMES, hashMap);
        }
    }

    private void trackManualInfo(int i) {
        CameraStatUtils.trackPictureTakenInManual(i, getManualValue(CameraSettings.KEY_WHITE_BALANCE, getString(R.string.pref_camera_whitebalance_default)), getManualValue(CameraSettings.KEY_QC_EXPOSURETIME, getString(R.string.pref_camera_exposuretime_default)), getManualValue(CameraSettings.KEY_QC_ISO, getString(R.string.pref_camera_iso_default)), getManualValue(CameraSettings.KEY_QC_MANUAL_EXPOSURE_VALUE, getString(R.string.pref_camera_iso_default)), ((BaseModule) this).mModuleIndex, getActualCameraId());
    }

    private boolean triggerHDR(boolean z) {
        Camera2Proxy camera2Proxy;
        if (!((BaseModule) this).isZooming && isDoingAction()) {
            return false;
        }
        ComponentConfigHdr componentHdr = DataRepository.dataItemConfig().getComponentHdr();
        if (componentHdr.isEmpty()) {
            return false;
        }
        String componentValue = componentHdr.getComponentValue(((BaseModule) this).mModuleIndex);
        if (!"auto".equals(componentValue) && !componentHdr.isHdrOnWithChecker(componentValue)) {
            return false;
        }
        if (z) {
            return getZoomRatio() <= 1.0f && this.mCurrentAiScene != -1 && ((camera2Proxy = ((BaseModule) this).mCamera2Device) == null || !camera2Proxy.isNeedFlashOn());
        }
        return true;
    }

    private void unlockAEAF() {
        Log.d(TAG, "unlockAEAF");
        this.m3ALocked = false;
        if (((BaseModule) this).mAeLockSupported) {
            if (isDeviceAlive()) {
                ((BaseModule) this).mCamera2Device.unlockExposure();
            }
            if (!DataRepository.dataItemFeature().pf() && this.mUltraWideAELocked) {
                String focusMode = CameraSettings.getFocusMode();
                String str = TAG;
                Log.d(str, "unlockAEAF: focusMode = " + focusMode);
                setFocusMode(focusMode);
                this.mUltraWideAELocked = false;
            }
        }
        FocusManager2 focusManager2 = this.mFocusManager;
        if (focusManager2 != null) {
            focusManager2.setAeAwbLock(false);
        }
    }

    private void unregisterSensor() {
        if (CameraSettings.isGradienterOn()) {
            ((BaseModule) this).mActivity.getSensorStateManager().setGradienterEnabled(false);
        }
        ((BaseModule) this).mActivity.getSensorStateManager().setLieIndicatorEnabled(false);
        if (DataRepository.dataItemFeature().Je()) {
            ((BaseModule) this).mActivity.getSensorStateManager().setMagneticFieldUncalibratedEnable(false);
        }
        this.mIsShowLyingDirectHintStatus = -1;
        ((BaseModule) this).mHandler.removeMessages(58);
    }

    private boolean updateAIWatermark() {
        boolean needActiveASD = needActiveASD();
        if (this.mAIWatermarkEnable != needActiveASD) {
            this.mAIWatermarkEnable = needActiveASD;
            ((BaseModule) this).mCamera2Device.setAiASDEnable(this.mAIWatermarkEnable);
            if (this.mAIWatermarkEnable) {
                ((BaseModule) this).mCamera2Device.setASDPeriod(300);
            }
        }
        return this.mAIWatermarkEnable;
    }

    private void updateASD() {
        Camera2Proxy camera2Proxy;
        if ((163 == getModuleIndex() || 165 == getModuleIndex() || 171 == getModuleIndex()) && (camera2Proxy = ((BaseModule) this).mCamera2Device) != null) {
            camera2Proxy.setASDEnable(true);
        }
    }

    private void updateASDDirtyDetect() {
        ((BaseModule) this).mCamera2Device.setAsdDirtyEnable(CameraSettings.isLensDirtyDetectEnabled() && DataRepository.dataItemGlobal().getBoolean("pref_lens_dirty_tip", getResources().getBoolean(R.bool.pref_lens_dirty_tip_default)) && CameraSettings.shouldShowLensDirtyDetectHint());
    }

    private void updateAiScene() {
        FunctionParseAiScene functionParseAiScene = this.mFunctionParseAiScene;
        if (functionParseAiScene != null) {
            functionParseAiScene.resetScene();
        }
        this.mCurrentAiScene = 0;
        this.mAiSceneEnabled = CameraSettings.getAiSceneOpen(((BaseModule) this).mModuleIndex);
        if (isFrontCamera() && ((BaseModule) this).mActivity.isScreenSlideOff()) {
            this.mAiSceneEnabled = false;
        }
        ((BaseModule) this).mCamera2Device.setAiASDEnable(this.mAiSceneEnabled);
        if ((isFrontCamera() && ModuleManager.isCapture()) || !this.mAiSceneEnabled) {
            ((BaseModule) this).mCamera2Device.setCameraAI30(this.mAiSceneEnabled);
        }
        setAiSceneEffect(this.mCurrentAiScene);
        ((BaseModule) this).mCamera2Device.setASDScene(this.mCurrentAiScene);
        if (this.mAiSceneEnabled) {
            ((BaseModule) this).mCamera2Device.setASDPeriod(300);
            return;
        }
        hideSceneSelector();
        updateHDRPreference();
        updateFlashPreference();
        updateBeauty();
    }

    private void updateAlgorithmName() {
        this.mAlgorithmName = !b.Rk() ? ((BaseModule) this).mCamera2Device.isBokehEnabled() ? DataRepository.dataItemFeature().xb() > 0 ? Util.ALGORITHM_NAME_SOFT_PORTRAIT_ENCRYPTED : Util.ALGORITHM_NAME_SOFT_PORTRAIT : isPortraitMode() ? Util.ALGORITHM_NAME_PORTRAIT : ((BaseModule) this).mMutexModePicker.getAlgorithmName() : null;
    }

    private void updateBeauty() {
        int i = ((BaseModule) this).mModuleIndex;
        if (i == 163 || i == 165 || i == 171) {
            if (this.mBeautyValues == null) {
                this.mBeautyValues = new BeautyValues();
            }
            CameraSettings.initBeautyValues(this.mBeautyValues, ((BaseModule) this).mModuleIndex);
            if (!DataRepository.dataItemConfig().getComponentConfigBeauty().isClosed(((BaseModule) this).mModuleIndex) && this.mCurrentAiScene == 25 && !isFaceBeautyOn(this.mBeautyValues)) {
                ComponentRunningShine componentRunningShine = DataRepository.dataItemRunning().getComponentRunningShine();
                if (componentRunningShine.supportBeautyLevel()) {
                    this.mBeautyValues.mBeautyLevel = BeautyConstant.LEVEL_LOW;
                } else {
                    componentRunningShine.supportSmoothLevel();
                }
                Log.d(TAG, String.format(Locale.ENGLISH, "Human scene mode detected, auto set beauty level from %s to %s", BeautyConstant.LEVEL_CLOSE, this.mBeautyValues.mBeautyLevel));
            }
            String str = TAG;
            Log.d(str, "updateBeauty(): " + this.mBeautyValues);
            ((BaseModule) this).mCamera2Device.setBeautyValues(this.mBeautyValues);
            this.mIsBeautyBodySlimOn = this.mBeautyValues.isBeautyBodyOn();
            updateFaceAgeAnalyze();
        }
    }

    private void updateBokeh() {
        ComponentConfigBokeh componentBokeh = DataRepository.dataItemConfig().getComponentBokeh();
        if (!ModuleManager.isPortraitModule() && !"on".equals(componentBokeh.getComponentValue(((BaseModule) this).mModuleIndex))) {
            ((BaseModule) this).mCamera2Device.setMiBokeh(false);
            ((BaseModule) this).mCamera2Device.setRearBokehEnable(false);
        } else if (isSingleCamera() || (DataRepository.dataItemFeature().mc() && ModuleManager.isPortraitModule() && isFrontCamera())) {
            ((BaseModule) this).mCamera2Device.setMiBokeh(true);
            ((BaseModule) this).mCamera2Device.setRearBokehEnable(false);
        } else {
            ((BaseModule) this).mCamera2Device.setMiBokeh(false);
            ((BaseModule) this).mCamera2Device.setRearBokehEnable(true);
        }
    }

    private void updateCaptureTriggerFlow() {
    }

    private void updateCinematicPhoto() {
        ((BaseModule) this).mCamera2Device.setCinematicPhotoEnabled(CameraSettings.isCinematicAspectRatioEnabled(((BaseModule) this).mModuleIndex));
    }

    private void updateColorEnhance() {
        if (DataRepository.dataItemFeature().supportColorEnhance() && ((BaseModule) this).mCamera2Device != null) {
            ((BaseModule) this).mCamera2Device.setColorEnhanceEnable(DataRepository.dataItemRunning().getComponentRunningColorEnhance().isEnabled(getModuleIndex()));
        }
    }

    private void updateContrast() {
        ((BaseModule) this).mCamera2Device.setContrast(Integer.parseInt(getString(R.string.pref_camera_contrast_default)));
    }

    private void updateDecodePreview() {
        if (this.mIsGoogleLensAvailable || scanQRCodeEnabled() || b.pd()) {
            String str = TAG;
            Log.d(str, "updateDecodePreview: PreviewDecodeManager AlgorithmPreviewSize = " + ((BaseModule) this).mCamera2Device.getAlgorithmPreviewSize());
            ((BaseModule) this).mCamera2Device.startPreviewCallback(PreviewDecodeManager.getInstance().getPreviewCallback());
            PreviewDecodeManager.getInstance().startDecode();
        }
    }

    private void updateDeviceOrientation() {
        ((BaseModule) this).mCamera2Device.setDeviceOrientation(((BaseModule) this).mOrientation);
    }

    private void updateEnablePreviewThumbnail(boolean z) {
        if (Util.isSaveToHidenFolder(((BaseModule) this).mModuleIndex)) {
            this.mEnabledPreviewThumbnail = false;
        } else if (((BaseModule) this).mModuleIndex != 167 || !DataRepository.dataItemRunning().isSwitchOn("pref_camera_peak_key")) {
            int i = ((BaseModule) this).mModuleIndex;
            if ((i == 165 || i == 163) && DataRepository.dataItemRunning().isSwitchOn("pref_camera_tilt_shift_mode")) {
                this.mEnabledPreviewThumbnail = false;
            } else if (!isPreviewThumbnailWhenFlash()) {
                this.mEnabledPreviewThumbnail = false;
            } else if (this.mIsImageCaptureIntent) {
                this.mEnabledPreviewThumbnail = false;
            } else if (this.mEnableParallelSession || this.mEnableShot2Gallery || z || (this.mReceivedJpegCallbackNum == 0 && enablePreviewAsThumbnail())) {
                this.mEnabledPreviewThumbnail = true;
            }
        } else {
            this.mEnabledPreviewThumbnail = false;
        }
        ((BaseModule) this).mActivity.setPreviewThumbnail(this.mEnabledPreviewThumbnail);
    }

    private void updateEvValue() {
        String manualValue = getManualValue(CameraSettings.KEY_QC_MANUAL_EXPOSURE_VALUE, "0");
        ((BaseModule) this).mEvValue = (int) (Float.parseFloat(manualValue) / Camera2DataContainer.getInstance().getCapabilitiesByBogusCameraId(((BaseModule) this).mActualCameraId, ((BaseModule) this).mModuleIndex).getExposureCompensationStep());
        ((BaseModule) this).mEvState = 3;
        setEvValue();
    }

    private void updateExposureTime() {
        ((BaseModule) this).mCamera2Device.setExposureTime(Long.parseLong(getManualValue(CameraSettings.KEY_QC_EXPOSURETIME, getString(R.string.pref_camera_exposuretime_default))));
    }

    private void updateEyeLight() {
        if (isFrontCamera() && DataRepository.dataItemFeature().Ee()) {
            ((BaseModule) this).mCamera2Device.setEyeLight(Integer.parseInt(CameraSettings.getEyeLightType()));
        }
    }

    private void updateFNumber() {
        if (DataRepository.dataItemFeature().isSupportBokehAdjust()) {
            ((BaseModule) this).mCamera2Device.setFNumber(CameraSettings.readFNumber());
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:20:0x004e  */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x005b  */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0065  */
    private void updateFace() {
        boolean z;
        boolean z2;
        ModeProtocol.MainContentProtocol mainContentProtocol;
        if (this.mMultiSnapStatus || ((BaseModule) this).mMutexModePicker.isUbiFocus()) {
            z2 = false;
        } else if (CameraSettings.isMagicMirrorOn() || CameraSettings.isPortraitModeBackOn() || CameraSettings.isGroupShotOn() || CameraSettings.showGenderAge()) {
            z2 = true;
            z = true;
            mainContentProtocol = ((BaseModule) this).mMainProtocol;
            if (mainContentProtocol != null) {
                mainContentProtocol.setSkipDrawFace(!z2 || !z);
            }
            if (z2) {
                if (!this.mFaceDetectionEnabled) {
                    this.mFaceDetectionEnabled = true;
                    startFaceDetection();
                    return;
                }
                return;
            } else if (this.mFaceDetectionEnabled) {
                stopFaceDetection(true);
                this.mFaceDetectionEnabled = false;
                return;
            } else {
                return;
            }
        } else {
            z2 = DataRepository.dataItemGlobal().getBoolean("pref_camera_facedetection_key", getResources().getBoolean(R.bool.pref_camera_facedetection_default));
            if (CameraSettings.isTiltShiftOn()) {
                z = false;
                mainContentProtocol = ((BaseModule) this).mMainProtocol;
                if (mainContentProtocol != null) {
                }
                if (z2) {
                }
            }
        }
        z = true;
        mainContentProtocol = ((BaseModule) this).mMainProtocol;
        if (mainContentProtocol != null) {
        }
        if (z2) {
        }
    }

    private void updateFaceAgeAnalyze() {
        boolean z;
        this.mIsGenderAgeOn = DataRepository.dataItemRunning().isSwitchOn("pref_camera_show_gender_age_key");
        if (this.mIsGenderAgeOn) {
            z = true;
        } else {
            BeautyValues beautyValues = this.mBeautyValues;
            z = beautyValues != null ? isFaceBeautyOn(beautyValues) : false;
        }
        ((BaseModule) this).mCamera2Device.setFaceAgeAnalyze(z);
    }

    private void updateFaceScore() {
        this.mIsMagicMirrorOn = DataRepository.dataItemRunning().isSwitchOn("pref_camera_magic_mirror_key");
        ((BaseModule) this).mCamera2Device.setFaceScore(this.mIsMagicMirrorOn);
    }

    private void updateFilter() {
        setEffectFilter(CameraSettings.getShaderEffect());
    }

    private void updateFocusArea() {
        Camera camera = ((BaseModule) this).mActivity;
        if (camera != null && !camera.isActivityPaused() && isAlive()) {
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

    private void updateFocusMode() {
        String str;
        if (this.mIsMoonMode) {
            this.mFocusManager.removeMessages();
            str = this.mFocusManager.setFocusMode("manual");
        } else {
            str = this.mFocusManager.setFocusMode(CameraSettings.getFocusMode());
        }
        setFocusMode(str);
        if (CameraSettings.isFocusModeSwitching() && isBackCamera()) {
            CameraSettings.setFocusModeSwitching(false);
            this.mFocusManager.resetFocusStateIfNeeded();
        }
        if (str.equals("manual")) {
            float minimumFocusDistance = (((BaseModule) this).mCameraCapabilities.getMinimumFocusDistance() * ((float) CameraSettings.getFocusPosition())) / 1000.0f;
            if (this.mIsMoonMode) {
                minimumFocusDistance = 0.5f;
            }
            ((BaseModule) this).mCamera2Device.setFocusDistance(minimumFocusDistance);
        }
    }

    private void updateFpsRange() {
        Range<Integer>[] supportedFpsRange = ((BaseModule) this).mCameraCapabilities.getSupportedFpsRange();
        Range<Integer> range = supportedFpsRange[0];
        for (Range<Integer> range2 : supportedFpsRange) {
            if (range.getUpper().intValue() < range2.getUpper().intValue() || (range.getUpper() == range2.getUpper() && range.getLower().intValue() < range2.getLower().intValue())) {
                range = range2;
            }
        }
        if (b.Ru && CameraSettings.isPortraitModeBackOn()) {
            ((BaseModule) this).mCamera2Device.setFpsRange(new Range<>(30, 30));
        }
    }

    private void updateFrontMirror() {
        ((BaseModule) this).mCamera2Device.setFrontMirror(isFrontMirror());
    }

    private void updateHDR(String str) {
        if ("auto".equals(str)) {
            this.isDetectedInHdr = false;
        }
        int mutexHdrMode = getMutexHdrMode(str);
        stopObjectTracking(true);
        if (mutexHdrMode != 0) {
            ((BaseModule) this).mMutexModePicker.setMutexMode(mutexHdrMode);
        } else if (((BaseModule) this).mMutexModePicker.isHdr()) {
            resetMutexModeManually();
        }
        if (isFrontCamera() && isTriggerQcfaModeChange(false, true)) {
            ((BaseModule) this).mHandler.sendEmptyMessage(44);
        }
        if (str != null && !str.equals(this.mLastHdrMode)) {
            updateScene();
            this.mLastHdrMode = str;
        }
    }

    private void updateISO() {
        String string = getString(R.string.pref_camera_iso_default);
        String manualValue = getManualValue(CameraSettings.KEY_QC_ISO, string);
        if (manualValue == null || manualValue.equals(string)) {
            ((BaseModule) this).mCamera2Device.setISO(0);
        } else {
            ((BaseModule) this).mCamera2Device.setISO(Math.min(Util.parseInt(manualValue, 0), ((BaseModule) this).mCameraCapabilities.getMaxIso()));
        }
    }

    private void updateJpegQuality() {
        ((BaseModule) this).mCamera2Device.setJpegQuality(clampQuality(CameraSettings.getEncodingQuality(this.mMultiSnapStatus).toInteger(false)));
    }

    private void updateJpegThumbnailSize() {
        CameraSize jpegThumbnailSize = getJpegThumbnailSize();
        ((BaseModule) this).mCamera2Device.setJpegThumbnailSize(jpegThumbnailSize);
        String str = TAG;
        Log.d(str, "thumbnailSize=" + jpegThumbnailSize);
    }

    private void updateLiveShot() {
        if (DataRepository.dataItemFeature().vd() && ((BaseModule) this).mModuleIndex == 163) {
            if (CameraSettings.isLiveShotOn()) {
                startLiveShot();
            } else {
                stopLiveShot(false);
            }
        }
    }

    private void updateMacroMode() {
        ((BaseModule) this).mCamera2Device.setMacroMode(CameraSettings.isMacroModeEnabled(((BaseModule) this).mModuleIndex));
    }

    /* JADX WARNING: Code restructure failed: missing block: B:30:0x007e, code lost:
        if (com.android.camera.data.DataRepository.dataItemRunning().getComponentUltraPixel().isRearSwitchOn() != false) goto L_0x0080;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x008a, code lost:
        if (isDualCamera() != false) goto L_0x008d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x00a7, code lost:
        if (enableFrontMFNR() != false) goto L_0x00aa;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x00b6, code lost:
        if (((com.android.camera.module.BaseModule) r7).mCameraCapabilities.isMfnrMacroZoomSupported() != false) goto L_0x0080;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x00d7, code lost:
        if (isZoomRatioBetweenUltraAndWide() != false) goto L_0x0080;
     */
    private void updateMfnr(boolean z) {
        boolean z2 = true;
        boolean z3 = false;
        if (!isUseSwMfnr() && z) {
            int i = ((BaseModule) this).mModuleIndex;
            if (i == 167) {
                boolean z4 = DataRepository.dataItemFeature().Sd() && (isSingleCamera() || isStandaloneMacroCamera() || isUltraWideBackCamera());
                if (isSensorRawStreamRequired() || ((!z4 && !DataRepository.dataItemFeature().Re()) || ((BaseModule) this).mCamera2Device == null || Long.parseLong(getManualValue(CameraSettings.KEY_QC_EXPOSURETIME, getString(R.string.pref_camera_exposuretime_default))) >= 250000000)) {
                    z2 = false;
                }
            } else {
                if (i == 175) {
                    if (DataRepository.dataItemFeature().Hd()) {
                    }
                }
                if (b.Qu) {
                }
                if (((BaseModule) this).mMutexModePicker.isNormal()) {
                    if (!CameraSettings.isGroupShotOn()) {
                        if (isFrontCamera()) {
                        }
                        if (isStandaloneMacroCamera()) {
                        }
                        if (!DataRepository.dataItemFeature().tf()) {
                            if (getZoomRatio() != 1.0f) {
                                if (!isUltraWideBackCamera()) {
                                }
                            }
                        }
                    }
                }
            }
            z3 = z2;
        }
        if (((BaseModule) this).mCamera2Device != null) {
            Log.d(TAG, "setMfnr to " + z3);
            ((BaseModule) this).mCamera2Device.setMfnr(z3);
        }
    }

    private void updateMute() {
    }

    private void updateNormalWideLDC() {
        ((BaseModule) this).mCamera2Device.setNormalWideLDC(shouldApplyNormalWideLDC());
    }

    private void updateOIS() {
        if (CameraSettings.isPortraitModeBackOn()) {
            ((BaseModule) this).mCamera2Device.setEnableOIS(false);
        } else if (((BaseModule) this).mModuleIndex == 167 && Long.parseLong(getManualValue(CameraSettings.KEY_QC_EXPOSURETIME, getString(R.string.pref_camera_exposuretime_default))) > 1000000000) {
            ((BaseModule) this).mCamera2Device.setEnableOIS(false);
        } else if (b.hv && isDualCamera() && getZoomRatio() > 1.0f) {
            ((BaseModule) this).mCamera2Device.setEnableOIS(true);
        } else if (!isDualCamera() || ((BaseModule) this).mCameraCapabilities.isTeleOISSupported() || getZoomRatio() <= 1.0f) {
            ((BaseModule) this).mCamera2Device.setEnableOIS(true);
        } else {
            ((BaseModule) this).mCamera2Device.setEnableOIS(false);
        }
    }

    private void updateOutputSize(CameraSize cameraSize) {
        if (165 == ((BaseModule) this).mModuleIndex) {
            int i = cameraSize.width;
            int i2 = cameraSize.height;
            if (i <= i2) {
                i2 = i;
            }
            ((BaseModule) this).mOutputPictureSize = new CameraSize(i2, i2);
            return;
        }
        ((BaseModule) this).mOutputPictureSize = cameraSize;
    }

    private void updatePictureAndPreviewSize() {
        CameraSize cameraSize;
        int Tb;
        int i = this.mEnableParallelSession ? 35 : 256;
        int[] sATSubCameraIds = ((BaseModule) this).mCamera2Device.getSATSubCameraIds();
        boolean z = sATSubCameraIds != null;
        if (z) {
            String str = TAG;
            Log.d(str, "[SAT] camera list: " + Arrays.toString(sATSubCameraIds));
            int length = sATSubCameraIds.length;
            for (int i2 = 0; i2 < length; i2++) {
                int i3 = sATSubCameraIds[i2];
                if (i3 == Camera2DataContainer.getInstance().getUltraWideCameraId()) {
                    if (((BaseModule) this).mUltraCameraCapabilities == null) {
                        ((BaseModule) this).mUltraCameraCapabilities = Camera2DataContainer.getInstance().getCapabilities(Camera2DataContainer.getInstance().getUltraWideCameraId());
                    }
                    CameraCapabilities cameraCapabilities = ((BaseModule) this).mUltraCameraCapabilities;
                    if (cameraCapabilities != null) {
                        cameraCapabilities.setOperatingMode(this.mOperatingMode);
                        ((BaseModule) this).mUltraWidePictureSize = getBestPictureSize(((BaseModule) this).mUltraCameraCapabilities.getSupportedOutputSizeWithAssignedMode(i));
                    }
                } else if (i3 == Camera2DataContainer.getInstance().getMainBackCameraId()) {
                    if (((BaseModule) this).mWideCameraCapabilities == null) {
                        ((BaseModule) this).mWideCameraCapabilities = Camera2DataContainer.getInstance().getCapabilities(Camera2DataContainer.getInstance().getMainBackCameraId());
                    }
                    CameraCapabilities cameraCapabilities2 = ((BaseModule) this).mWideCameraCapabilities;
                    if (cameraCapabilities2 != null) {
                        cameraCapabilities2.setOperatingMode(this.mOperatingMode);
                        List<CameraSize> supportedOutputSizeWithAssignedMode = ((BaseModule) this).mWideCameraCapabilities.getSupportedOutputSizeWithAssignedMode(i);
                        int Tb2 = DataRepository.dataItemFeature().Tb();
                        if (Tb2 != 0) {
                            PictureSizeManager.initializeLimitWidth(supportedOutputSizeWithAssignedMode, Tb2, ((BaseModule) this).mModuleIndex, ((BaseModule) this).mBogusCameraId);
                            ((BaseModule) this).mWidePictureSize = PictureSizeManager.getBestPictureSize();
                        } else {
                            ((BaseModule) this).mWidePictureSize = getBestPictureSize(supportedOutputSizeWithAssignedMode);
                        }
                    }
                } else if (i3 == Camera2DataContainer.getInstance().getAuxCameraId()) {
                    if (((BaseModule) this).mTeleCameraCapabilities == null) {
                        ((BaseModule) this).mTeleCameraCapabilities = Camera2DataContainer.getInstance().getCapabilities(Camera2DataContainer.getInstance().getAuxCameraId());
                    }
                    CameraCapabilities cameraCapabilities3 = ((BaseModule) this).mTeleCameraCapabilities;
                    if (cameraCapabilities3 != null) {
                        cameraCapabilities3.setOperatingMode(this.mOperatingMode);
                        ((BaseModule) this).mTelePictureSize = getBestPictureSize(((BaseModule) this).mTeleCameraCapabilities.getSupportedOutputSizeWithAssignedMode(i));
                    }
                } else if (i3 == Camera2DataContainer.getInstance().getUltraTeleCameraId()) {
                    if (((BaseModule) this).mUltraTeleCameraCapabilities == null) {
                        ((BaseModule) this).mUltraTeleCameraCapabilities = Camera2DataContainer.getInstance().getCapabilities(Camera2DataContainer.getInstance().getUltraTeleCameraId());
                    }
                    CameraCapabilities cameraCapabilities4 = ((BaseModule) this).mUltraTeleCameraCapabilities;
                    if (cameraCapabilities4 != null) {
                        cameraCapabilities4.setOperatingMode(this.mOperatingMode);
                        ((BaseModule) this).mUltraTelePictureSize = getBestPictureSize(((BaseModule) this).mUltraTeleCameraCapabilities.getSupportedOutputSizeWithAssignedMode(i));
                    }
                    ((BaseModule) this).mCamera2Device.setUltraTelePictureSize(((BaseModule) this).mUltraTelePictureSize);
                } else if (i3 == Camera2DataContainer.getInstance().getStandaloneMacroCameraId()) {
                    if (((BaseModule) this).mMacroCameraCapabilities == null) {
                        ((BaseModule) this).mMacroCameraCapabilities = Camera2DataContainer.getInstance().getCapabilities(Camera2DataContainer.getInstance().getStandaloneMacroCameraId());
                    }
                    CameraCapabilities cameraCapabilities5 = ((BaseModule) this).mMacroCameraCapabilities;
                    if (cameraCapabilities5 != null) {
                        cameraCapabilities5.setOperatingMode(this.mOperatingMode);
                        ((BaseModule) this).mMacroPitureSize = getBestPictureSize(((BaseModule) this).mMacroCameraCapabilities.getSupportedOutputSizeWithAssignedMode(i));
                    }
                    ((BaseModule) this).mCamera2Device.setMarcroPictureSize(((BaseModule) this).mMacroPitureSize);
                }
            }
            if (DataRepository.dataItemFeature().Nd()) {
                Log.d(TAG, String.format(Locale.ENGLISH, "ultraWideSize: %s, wideSize: %s, teleSize: %s, ultraTeleSize:%s", ((BaseModule) this).mUltraWidePictureSize, ((BaseModule) this).mWidePictureSize, ((BaseModule) this).mTelePictureSize, ((BaseModule) this).mUltraTelePictureSize));
            } else {
                Log.d(TAG, String.format(Locale.ENGLISH, "ultraWideSize: %s, wideSize: %s, teleSize: %s", ((BaseModule) this).mUltraWidePictureSize, ((BaseModule) this).mWidePictureSize, ((BaseModule) this).mTelePictureSize));
            }
            ((BaseModule) this).mCamera2Device.setUltraWidePictureSize(((BaseModule) this).mUltraWidePictureSize);
            ((BaseModule) this).mCamera2Device.setWidePictureSize(((BaseModule) this).mWidePictureSize);
            ((BaseModule) this).mCamera2Device.setTelePictureSize(((BaseModule) this).mTelePictureSize);
            ((BaseModule) this).mPictureSize = getSatPictureSize();
        } else {
            int Pb = (!isUltraTeleCamera() || ((BaseModule) this).mModuleIndex != 167) ? 0 : DataRepository.dataItemFeature().Pb();
            if (isSensorRawStreamRequired()) {
                List<CameraSize> supportedOutputSizeWithAssignedMode2 = ((BaseModule) this).mCameraCapabilities.getSupportedOutputSizeWithAssignedMode(32);
                if (((BaseModule) this).mModuleIndex != 167) {
                    ((BaseModule) this).mSensorRawImageSize = getBestPictureSize(supportedOutputSizeWithAssignedMode2);
                } else if (supportedOutputSizeWithAssignedMode2 == null || supportedOutputSizeWithAssignedMode2.size() == 0) {
                    Log.w(TAG, "The supported raw size list return from hal is null!");
                } else if (Pb == 0) {
                    ((BaseModule) this).mSensorRawImageSize = getBestPictureSize(supportedOutputSizeWithAssignedMode2, 1.3333333f);
                } else {
                    PictureSizeManager.initializeLimitWidth(supportedOutputSizeWithAssignedMode2, Pb, ((BaseModule) this).mModuleIndex, ((BaseModule) this).mBogusCameraId);
                    ((BaseModule) this).mSensorRawImageSize = PictureSizeManager.getBestPictureSize(1.3333333f);
                }
                String str2 = TAG;
                Log.d(str2, "The best sensor raw image size: " + ((BaseModule) this).mSensorRawImageSize);
            }
            if (!this.mEnableParallelSession || !isPortraitMode()) {
                List<CameraSize> supportedOutputSizeWithAssignedMode3 = ((BaseModule) this).mCameraCapabilities.getSupportedOutputSizeWithAssignedMode(i);
                CameraSize bestPictureSize = getBestPictureSize(supportedOutputSizeWithAssignedMode3);
                if (b.Wu && getOperatingMode() == 36867) {
                    bestPictureSize = new CameraSize(bestPictureSize.width / 2, bestPictureSize.height / 2);
                }
                if (b.isMTKPlatform() && isFrontCamera() && isParallelSessionEnable()) {
                    bestPictureSize = new CameraSize(bestPictureSize.width / 2, bestPictureSize.height / 2);
                }
                if (Pb != 0) {
                    PictureSizeManager.initializeLimitWidth(supportedOutputSizeWithAssignedMode3, Pb, ((BaseModule) this).mModuleIndex, ((BaseModule) this).mBogusCameraId);
                    bestPictureSize = PictureSizeManager.getBestPictureSize();
                }
                if (isLimitSize()) {
                    bestPictureSize = getLimitSize(supportedOutputSizeWithAssignedMode3);
                }
                if (((BaseModule) this).mModuleIndex == 173 && (Tb = DataRepository.dataItemFeature().Tb()) != 0) {
                    PictureSizeManager.initializeLimitWidth(supportedOutputSizeWithAssignedMode3, Tb, ((BaseModule) this).mModuleIndex, ((BaseModule) this).mBogusCameraId);
                    bestPictureSize = PictureSizeManager.getBestPictureSize();
                }
                ((BaseModule) this).mPictureSize = bestPictureSize;
                if (isEnableQcfaForAlgoUp()) {
                    CameraSize cameraSize2 = ((BaseModule) this).mPictureSize;
                    int i4 = cameraSize2.width / 2;
                    int i5 = cameraSize2.height / 2;
                    ((BaseModule) this).mBinningPictureSize = PictureSizeManager.getBestPictureSize(((BaseModule) this).mCameraCapabilities.getSupportedOutputStreamSizes(35), Util.getRatio(CameraSettings.getPictureSizeRatioString()), i4 * i5);
                    if (((BaseModule) this).mBinningPictureSize.isEmpty()) {
                        ((BaseModule) this).mBinningPictureSize = new CameraSize(i4, i5);
                    }
                }
            } else {
                updatePortraitPictureSize(i);
            }
        }
        List<CameraSize> supportedOutputSizeWithAssignedMode4 = ((BaseModule) this).mCameraCapabilities.getSupportedOutputSizeWithAssignedMode(SurfaceTexture.class);
        CameraSize cameraSize3 = ((BaseModule) this).mPictureSize;
        double previewAspectRatio = (double) CameraSettings.getPreviewAspectRatio(cameraSize3.width, cameraSize3.height);
        ((BaseModule) this).mPreviewSize = Util.getOptimalPreviewSize(false, ((BaseModule) this).mBogusCameraId, supportedOutputSizeWithAssignedMode4, previewAspectRatio);
        ((BaseModule) this).mCamera2Device.setPreviewSize(((BaseModule) this).mPreviewSize);
        if (this.mIsGoogleLensAvailable) {
            ((BaseModule) this).mCamera2Device.setAlgorithmPreviewSize(Util.getAlgorithmPreviewSize(supportedOutputSizeWithAssignedMode4, previewAspectRatio, ((BaseModule) this).mPreviewSize));
        } else {
            ((BaseModule) this).mCamera2Device.setAlgorithmPreviewSize(((BaseModule) this).mPreviewSize);
        }
        ((BaseModule) this).mCamera2Device.setAlgorithmPreviewFormat(35);
        ((BaseModule) this).mOutputPictureFormat = isHeicPreferred() ? CompatibilityUtils.IMAGE_FORMAT_HEIC : 256;
        String str3 = TAG;
        Locale locale = Locale.ENGLISH;
        Object[] objArr = new Object[1];
        String str4 = "HEIC";
        objArr[0] = CompatibilityUtils.isHeicImageFormat(((BaseModule) this).mOutputPictureFormat) ? str4 : "JPEG";
        Log.d(str3, String.format(locale, "updateSize: use %s as preferred output image format", objArr));
        if (this.mEnableParallelSession) {
            List<CameraSize> supportedHeicOutputStreamSizes = CompatibilityUtils.isHeicImageFormat(((BaseModule) this).mOutputPictureFormat) ? ((BaseModule) this).mCameraCapabilities.hasStandaloneHeicStreamConfigurations() ? ((BaseModule) this).mCameraCapabilities.getSupportedHeicOutputStreamSizes() : ((BaseModule) this).mCameraCapabilities.getSupportedOutputSizeWithAssignedMode(((BaseModule) this).mOutputPictureFormat) : ((BaseModule) this).mCameraCapabilities.getSupportedOutputSizeWithAssignedMode(256);
            if (((BaseModule) this).mModuleIndex == 165) {
                CameraSize cameraSize4 = ((BaseModule) this).mPictureSize;
                int min = Math.min(cameraSize4.width, cameraSize4.height);
                ((BaseModule) this).mOutputPictureSize = PictureSizeManager.getBestSquareSize(supportedHeicOutputStreamSizes, min, b.isMTKPlatform() && isFrontCamera());
                if (((BaseModule) this).mOutputPictureSize.isEmpty()) {
                    String str5 = TAG;
                    Log.w(str5, "Could not find a proper squared Jpeg size, defaults to: " + min + "x" + min);
                    ((BaseModule) this).mOutputPictureSize = new CameraSize(min, min);
                }
            } else if (z) {
                ((BaseModule) this).mOutputPictureSize = ((BaseModule) this).mPictureSize;
            } else if (isLimitSize()) {
                ((BaseModule) this).mOutputPictureSize = getLimitSize(supportedHeicOutputStreamSizes);
            } else {
                ((BaseModule) this).mOutputPictureSize = PictureSizeManager.getBestPictureSize(supportedHeicOutputStreamSizes);
            }
            String str6 = TAG;
            Locale locale2 = Locale.ENGLISH;
            Object[] objArr2 = new Object[2];
            objArr2[0] = CompatibilityUtils.isHeicImageFormat(((BaseModule) this).mOutputPictureFormat) ? str4 : "JPEG";
            objArr2[1] = ((BaseModule) this).mOutputPictureSize;
            Log.d(str6, String.format(locale2, "updateSize: algoUp picture size (%s): %s", objArr2));
        }
        if (this.mIsImageCaptureIntent && ((BaseModule) this).mCameraCapabilities.isSupportLightTripartite() && ((BaseModule) this).mPictureSize.width > 4100) {
            CameraSize cameraSize5 = null;
            try {
                PictureSizeManager.initializeLimitWidth(((BaseModule) this).mCameraCapabilities.getSupportedOutputSizeWithAssignedMode(i), 4100, ((BaseModule) this).mModuleIndex, ((BaseModule) this).mBogusCameraId);
                cameraSize5 = PictureSizeManager.getBestPictureSize();
            } catch (Exception unused) {
                Log.e(TAG, "No find tempSize for tripartite used");
            }
            if (cameraSize5 != null && cameraSize5.width >= 3000) {
                if (this.mEnableParallelSession) {
                    List<CameraSize> supportedOutputSizeWithAssignedMode5 = ((BaseModule) this).mCameraCapabilities.getSupportedOutputSizeWithAssignedMode(256);
                    if (((BaseModule) this).mModuleIndex == 165) {
                        int min2 = Math.min(cameraSize5.width, cameraSize5.height);
                        cameraSize = new CameraSize(min2, min2);
                    } else {
                        cameraSize = cameraSize5;
                    }
                    if (supportedOutputSizeWithAssignedMode5.contains(cameraSize)) {
                        ((BaseModule) this).mPictureSize = cameraSize5;
                        ((BaseModule) this).mOutputPictureSize = cameraSize;
                        Log.d(TAG, String.format(Locale.ENGLISH, "updateSize: algoUp picture size for tripartite (%s): %s", "JPEG", ((BaseModule) this).mOutputPictureSize));
                    }
                } else {
                    ((BaseModule) this).mPictureSize = cameraSize5;
                }
            }
        }
        String str7 = TAG;
        Locale locale3 = Locale.ENGLISH;
        Object[] objArr3 = new Object[4];
        if (this.mEnableParallelSession) {
            str4 = "YUV";
        } else if (!CompatibilityUtils.isHeicImageFormat(((BaseModule) this).mOutputPictureFormat)) {
            str4 = "JPEG";
        }
        objArr3[0] = str4;
        objArr3[1] = ((BaseModule) this).mPictureSize;
        objArr3[2] = ((BaseModule) this).mPreviewSize;
        objArr3[3] = ((BaseModule) this).mSensorRawImageSize;
        Log.d(str7, String.format(locale3, "updateSize: picture size (%s): %s, preview size: %s, sensor raw image size: %s", objArr3));
        CameraSize cameraSize6 = ((BaseModule) this).mPreviewSize;
        updateCameraScreenNailSize(cameraSize6.width, cameraSize6.height);
        checkDisplayOrientation();
        CameraSize cameraSize7 = ((BaseModule) this).mPreviewSize;
        setVideoSize(cameraSize7.width, cameraSize7.height);
    }

    private void updatePortraitLighting() {
        String valueOf = String.valueOf(CameraSettings.getPortraitLightingPattern());
        this.mIsPortraitLightingOn = !valueOf.equals("0");
        ((BaseModule) this).mCamera2Device.setPortraitLighting(Integer.parseInt(valueOf));
        setLightingEffect();
    }

    private void updatePortraitPictureSize(int i) {
        int i2;
        boolean z;
        boolean z2;
        if (!isFrontCamera()) {
            z2 = DataRepository.dataItemFeature()._b();
            z = DataRepository.dataItemRunning().isSwitchOn("pref_ultra_wide_bokeh_enabled");
            i2 = z ? Camera2DataContainer.getInstance().getUltraWideCameraId() : b.Bl() ? ((BaseModule) this).mCamera2Device.getBokehAuxCameraId() : Camera2DataContainer.getInstance().getAuxCameraId();
        } else if (isDualFrontCamera()) {
            i2 = Camera2DataContainer.getInstance().getAuxFrontCameraId();
            z2 = true;
            z = false;
        } else {
            i2 = -1;
            z2 = false;
            z = false;
        }
        Log.d(TAG, "BS = " + z2 + " UW = " + z + " id = " + i2);
        PictureSizeManager.initializeLimitWidth(((BaseModule) this).mCameraCapabilities.getSupportedOutputSizeWithAssignedMode(i), isBackCamera() ? DataRepository.dataItemFeature().vb() : 0, ((BaseModule) this).mModuleIndex, ((BaseModule) this).mBogusCameraId);
        CameraSize bestPictureSize = PictureSizeManager.getBestPictureSize();
        if (b.Wu && getOperatingMode() == 36867) {
            bestPictureSize = new CameraSize(bestPictureSize.width / 2, bestPictureSize.height / 2);
        }
        if (b.isMTKPlatform() && isFrontCamera()) {
            bestPictureSize = new CameraSize(bestPictureSize.width / 2, bestPictureSize.height / 2);
        }
        CameraSize cameraSize = null;
        if (-1 == i2) {
            ((BaseModule) this).mPictureSize = bestPictureSize;
            ((BaseModule) this).mSubPictureSize = null;
        } else {
            if (z2) {
                cameraSize = bestPictureSize;
            }
            CameraSize pictureSize = getPictureSize(i2, i, cameraSize);
            if (z || z2) {
                ((BaseModule) this).mPictureSize = bestPictureSize;
                ((BaseModule) this).mSubPictureSize = pictureSize;
            } else {
                ((BaseModule) this).mPictureSize = pictureSize;
                ((BaseModule) this).mSubPictureSize = bestPictureSize;
            }
        }
        Log.d(TAG, String.format(Locale.ENGLISH, "mainSize = %s subSize = %s", ((BaseModule) this).mPictureSize, ((BaseModule) this).mSubPictureSize));
    }

    private void updateSRAndMFNR() {
        if ((b.fv || b.vu.equals("cmi")) && !((BaseModule) this).mMutexModePicker.isHdr()) {
            if (isIn3OrMoreSatMode() && getZoomRatio() > 1.0f && ((BaseModule) this).mCamera2Device.getSatMasterCameraId() == 2) {
                ((BaseModule) this).mMutexModePicker.resetMutexMode();
            } else {
                updateSuperResolution();
            }
        }
    }

    private void updateSaturation() {
        ((BaseModule) this).mCamera2Device.setSaturation(Integer.parseInt(getString(R.string.pref_camera_saturation_default)));
    }

    private void updateScene() {
        DataItemRunning dataItemRunning = DataRepository.dataItemRunning();
        if (((BaseModule) this).mMutexModePicker.isSceneHdr()) {
            this.mSceneMode = CameraScene.HDR;
        } else if (!dataItemRunning.isSwitchOn("pref_camera_scenemode_setting_key")) {
            this.mSceneMode = "-1";
        } else {
            this.mSceneMode = dataItemRunning.getComponentRunningSceneValue().getComponentValue(((BaseModule) this).mModuleIndex);
        }
        String str = TAG;
        Log.d(str, "sceneMode=" + this.mSceneMode + " mutexMode=" + ((BaseModule) this).mMutexModePicker.getMutexMode());
        if (!setSceneMode(this.mSceneMode)) {
            this.mSceneMode = "-1";
        }
        ((BaseModule) this).mHandler.post(new Runnable() {
            /* class com.android.camera.module.Camera2Module.AnonymousClass13 */

            public void run() {
                Camera2Module.this.updateSceneModeUI();
            }
        });
        if ("0".equals(this.mSceneMode) || "-1".equals(this.mSceneMode)) {
            this.mFocusManager.overrideFocusMode(null);
        } else {
            this.mFocusManager.overrideFocusMode(AutoFocus.LEGACY_CONTINUOUS_PICTURE);
        }
    }

    /* access modifiers changed from: private */
    public void updateSceneModeUI() {
        boolean isSwitchOn = DataRepository.dataItemRunning().isSwitchOn("pref_camera_scenemode_setting_key");
        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        if (isSwitchOn) {
            DataRepository.dataItemConfig().getComponentHdr().setComponentValue(163, "off");
            String flashModeByScene = CameraSettings.getFlashModeByScene(this.mSceneMode);
            if (topAlert != null) {
                topAlert.disableMenuItem(false, 194);
                if (flashModeByScene != null) {
                    topAlert.disableMenuItem(false, 193);
                } else {
                    topAlert.enableMenuItem(false, 193);
                }
                topAlert.hideExtraMenu();
            }
        } else if (topAlert != null) {
            topAlert.enableMenuItem(false, 193, 194);
        }
        if (topAlert != null) {
            topAlert.updateConfigItem(193, 194);
        }
        updatePreferenceInWorkThread(11, 10);
    }

    private void updateSharpness() {
        ((BaseModule) this).mCamera2Device.setSharpness(Integer.parseInt(getString(R.string.pref_camera_sharpness_default)));
    }

    /* JADX WARNING: Code restructure failed: missing block: B:87:0x00ff, code lost:
        if (((com.android.camera.module.BaseModule) r9).mModuleIndex == 167) goto L_0x010b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:90:0x0108, code lost:
        if (shouldDoMultiFrameCapture() != false) goto L_0x00cf;
     */
    private void updateShotDetermine() {
        int i;
        int i2 = 1;
        boolean z = ((BaseModule) this).mModuleIndex == 171 && (!isBackCamera() ? DataRepository.dataItemFeature()._e() || DataRepository.dataItemFeature().Ge() : b.Cl() || DataRepository.dataItemFeature().Ze());
        this.mEnableParallelSession = isParallelSessionEnable();
        if (!this.mIsImageCaptureIntent) {
            this.mEnableShot2Gallery = !this.mEnableParallelSession && DataRepository.dataItemFeature().ff() && enablePreviewAsThumbnail() && !CameraSettings.isLiveShotOn();
            int i3 = ((BaseModule) this).mModuleIndex;
            if (!(i3 == 163 || i3 == 165 || i3 == 167)) {
                if (i3 != 171) {
                    if (i3 == 173) {
                        if (isCurrentRawDomainBasedSuperNight()) {
                            i = 10;
                        }
                        i2 = 0;
                        Log.d(TAG, "enableParallel=" + this.mEnableParallelSession + " mEnableShot2Gallery=" + this.mEnableShot2Gallery + " shotType=" + i2);
                        ((BaseModule) this).mCamera2Device.setShotType(i2);
                        ((BaseModule) this).mCamera2Device.setShot2Gallery(this.mEnableShot2Gallery);
                    } else if (!(i3 == 175 || i3 == 182)) {
                        this.mEnableParallelSession = false;
                        return;
                    }
                } else if (!this.mEnableParallelSession) {
                    if (z) {
                        i = 2;
                    }
                    i2 = 0;
                    Log.d(TAG, "enableParallel=" + this.mEnableParallelSession + " mEnableShot2Gallery=" + this.mEnableShot2Gallery + " shotType=" + i2);
                    ((BaseModule) this).mCamera2Device.setShotType(i2);
                    ((BaseModule) this).mCamera2Device.setShot2Gallery(this.mEnableShot2Gallery);
                } else if (!isBackCamera() || !DataRepository.dataItemFeature().Oe()) {
                    if (!shouldDoMultiFrameCapture()) {
                        if (isDualFrontCamera() || isDualCamera() || isBokehUltraWideBackCamera()) {
                            if (z) {
                                i = 6;
                            }
                            i2 = 5;
                            Log.d(TAG, "enableParallel=" + this.mEnableParallelSession + " mEnableShot2Gallery=" + this.mEnableShot2Gallery + " shotType=" + i2);
                            ((BaseModule) this).mCamera2Device.setShotType(i2);
                            ((BaseModule) this).mCamera2Device.setShot2Gallery(this.mEnableShot2Gallery);
                        }
                        if (z) {
                            i = 7;
                        }
                        i2 = 5;
                        Log.d(TAG, "enableParallel=" + this.mEnableParallelSession + " mEnableShot2Gallery=" + this.mEnableShot2Gallery + " shotType=" + i2);
                        ((BaseModule) this).mCamera2Device.setShotType(i2);
                        ((BaseModule) this).mCamera2Device.setShot2Gallery(this.mEnableShot2Gallery);
                    }
                    i2 = 8;
                    Log.d(TAG, "enableParallel=" + this.mEnableParallelSession + " mEnableShot2Gallery=" + this.mEnableShot2Gallery + " shotType=" + i2);
                    ((BaseModule) this).mCamera2Device.setShotType(i2);
                    ((BaseModule) this).mCamera2Device.setShot2Gallery(this.mEnableShot2Gallery);
                } else {
                    i2 = 11;
                    Log.d(TAG, "enableParallel=" + this.mEnableParallelSession + " mEnableShot2Gallery=" + this.mEnableShot2Gallery + " shotType=" + i2);
                    ((BaseModule) this).mCamera2Device.setShotType(i2);
                    ((BaseModule) this).mCamera2Device.setShot2Gallery(this.mEnableShot2Gallery);
                }
            }
            if (!this.mEnableParallelSession) {
                if (this.mConfigRawStream) {
                }
                i2 = 0;
                Log.d(TAG, "enableParallel=" + this.mEnableParallelSession + " mEnableShot2Gallery=" + this.mEnableShot2Gallery + " shotType=" + i2);
                ((BaseModule) this).mCamera2Device.setShotType(i2);
                ((BaseModule) this).mCamera2Device.setShot2Gallery(this.mEnableShot2Gallery);
            }
        } else if (this.mEnableParallelSession) {
            i2 = -5;
            if (isDualFrontCamera() || isDualCamera() || isBokehUltraWideBackCamera()) {
                if (z) {
                    i = -7;
                }
                Log.d(TAG, "enableParallel=" + this.mEnableParallelSession + " mEnableShot2Gallery=" + this.mEnableShot2Gallery + " shotType=" + i2);
                ((BaseModule) this).mCamera2Device.setShotType(i2);
                ((BaseModule) this).mCamera2Device.setShot2Gallery(this.mEnableShot2Gallery);
            }
            if (z) {
                i = -6;
            }
            Log.d(TAG, "enableParallel=" + this.mEnableParallelSession + " mEnableShot2Gallery=" + this.mEnableShot2Gallery + " shotType=" + i2);
            ((BaseModule) this).mCamera2Device.setShotType(i2);
            ((BaseModule) this).mCamera2Device.setShot2Gallery(this.mEnableShot2Gallery);
        } else {
            i = z ? -3 : -2;
        }
        i2 = i;
        Log.d(TAG, "enableParallel=" + this.mEnableParallelSession + " mEnableShot2Gallery=" + this.mEnableShot2Gallery + " shotType=" + i2);
        ((BaseModule) this).mCamera2Device.setShotType(i2);
        ((BaseModule) this).mCamera2Device.setShot2Gallery(this.mEnableShot2Gallery);
    }

    private void updateSuperNight() {
        boolean z = true;
        ((BaseModule) this).mCamera2Device.updateFlashAutoDetectionEnabled(true);
        if (((BaseModule) this).mModuleIndex != 173 && !CameraSettings.isSuperNightOn()) {
            z = false;
        }
        if (CameraSettings.isSuperNightOn()) {
            ((BaseModule) this).mCamera2Device.updateFlashAutoDetectionEnabled(false);
        }
        ((BaseModule) this).mCamera2Device.setSuperNight(z);
    }

    /* access modifiers changed from: private */
    public void updateSuperNightTip(boolean z) {
        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        if (topAlert == null) {
            return;
        }
        if (z) {
            int i = this.mCurrentAsdScene;
            if (i == 0) {
                exitAsdScene(i);
            }
            topAlert.alertSuperNightSeTip(0);
            return;
        }
        topAlert.alertSuperNightSeTip(8);
        int i2 = this.mCurrentAsdScene;
        if (i2 == 0) {
            enterAsdScene(i2);
        }
    }

    private void updateSuperResolution() {
        if (isFrontCamera() || ((BaseModule) this).mModuleIndex == 173) {
            return;
        }
        if (isUltraWideBackCamera()) {
            Log.d(TAG, "SR force off for ultra wide camera");
        } else if (isStandaloneMacroCamera() && !DataRepository.dataItemFeature().Qe()) {
            Log.d(TAG, "HAL doesn't support SR in macro mode.");
        } else if ((isStandaloneMacroCamera() && ((BaseModule) this).mCameraCapabilities.isMfnrMacroZoomSupported()) || !CameraSettings.isSREnable()) {
        } else {
            if (((BaseModule) this).mModuleIndex == 175 && DataRepository.dataItemFeature().Hd() && DataRepository.dataItemRunning().getComponentUltraPixel().isRearSwitchOn()) {
                Log.d(TAG, "108MP or 64MP doesn't support SR");
            } else if (((BaseModule) this).mModuleIndex == 167) {
                boolean Sd = DataRepository.dataItemFeature().Sd();
                if (isSensorRawStreamRequired() || !Sd || ((!isUltraTeleCamera() && !isAuxCamera()) || ((BaseModule) this).mCamera2Device == null || Long.parseLong(getManualValue(CameraSettings.KEY_QC_EXPOSURETIME, getString(R.string.pref_camera_exposuretime_default))) >= 250000000)) {
                    Camera2Proxy camera2Proxy = ((BaseModule) this).mCamera2Device;
                    if (camera2Proxy != null) {
                        camera2Proxy.setSuperResolution(false);
                        return;
                    }
                    return;
                }
                ((BaseModule) this).mCamera2Device.setSuperResolution(true);
            } else if (getZoomRatio() <= 1.0f) {
                if (DataRepository.dataItemRunning().isSwitchOn("pref_camera_super_resolution_key")) {
                    return;
                }
                if (((BaseModule) this).mMutexModePicker.isSuperResolution()) {
                    ((BaseModule) this).mMutexModePicker.resetMutexMode();
                    return;
                }
                Camera2Proxy camera2Proxy2 = ((BaseModule) this).mCamera2Device;
                if (camera2Proxy2 != null) {
                    camera2Proxy2.setSuperResolution(false);
                }
            } else if (CameraSettings.isGroupShotOn()) {
                if (((BaseModule) this).mMutexModePicker.isSuperResolution()) {
                    ((BaseModule) this).mMutexModePicker.resetMutexMode();
                }
            } else if (((BaseModule) this).mMutexModePicker.isNormal()) {
                ((BaseModule) this).mMutexModePicker.setMutexMode(10);
            }
        }
    }

    private void updateSwMfnr() {
        boolean isUseSwMfnr = isUseSwMfnr();
        String str = TAG;
        Log.d(str, "setSwMfnr to " + isUseSwMfnr);
        ((BaseModule) this).mCamera2Device.setSwMfnr(isUseSwMfnr);
    }

    private void updateThumbProgress(boolean z) {
        ModeProtocol.ActionProcessing actionProcessing = (ModeProtocol.ActionProcessing) ModeCoordinatorImpl.getInstance().getAttachProtocol(162);
        if (actionProcessing != null) {
            actionProcessing.updateLoading(z);
        }
    }

    private void updateUltraPixelPortrait() {
        ((BaseModule) this).mCamera2Device.setUltraPixelPortrait(CameraSettings.isUltraPixelPortraitFrontOn());
    }

    private void updateUltraWideLDC() {
        ((BaseModule) this).mCamera2Device.setUltraWideLDC(shouldApplyUltraWideLDC());
    }

    private void updateWatermarkTag() {
        Camera2Proxy camera2Proxy = ((BaseModule) this).mCamera2Device;
        if (camera2Proxy != null) {
            camera2Proxy.setNewWatermark(true);
            if (Util.isGlobalVersion()) {
                ((BaseModule) this).mCamera2Device.setGlobalWatermark();
            }
        }
    }

    private void updateWhiteBalance() {
        setAWBMode(getManualValue(CameraSettings.KEY_WHITE_BALANCE, "1"));
    }

    private void updateZsl() {
        ((BaseModule) this).mCamera2Device.setEnableZsl(isZslPreferred());
    }

    static /* synthetic */ void v(boolean z) {
        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        if (topAlert != null) {
            topAlert.alertHDR(z ? 0 : 8, false, false);
        }
    }

    public /* synthetic */ void Ef() {
        ((ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172)).alertAiDetectTipHint(8, R.string.super_night_hint, -1);
        this.mShowLLSHint = false;
    }

    public /* synthetic */ void Ff() {
        showOrHideLoadingProgress(false);
    }

    public /* synthetic */ void Hf() {
        showOrHideLoadingProgress(false);
    }

    public /* synthetic */ void If() {
        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        if (topAlert != null) {
            topAlert.alertAiDetectTipHint(8, R.string.super_night_hint, -1);
        }
        this.mShowSuperNightHint = false;
    }

    public /* synthetic */ void Kf() {
        this.mIsFaceConflict = false;
        this.mIsAiConflict = false;
        this.mIsUltraWideConflict = false;
        showOrHideChip(true);
    }

    public /* synthetic */ void Lf() {
        consumeAiSceneResult(0, true);
        this.isResetFromMutex = true;
    }

    public /* synthetic */ void Mf() {
        ((BaseModule) this).mActivity.getSensorStateManager().setLieIndicatorEnabled(true);
    }

    public /* synthetic */ void Nf() {
        ((BaseModule) this).mCamera2Device.setDeviceOrientation(((BaseModule) this).mOrientation);
    }

    public /* synthetic */ void Of() {
        showOrHideChip(false);
        this.mIsFaceConflict = false;
        this.mIsUltraWideConflict = false;
        this.mIsAiConflict = false;
    }

    public /* synthetic */ void Pf() {
        this.mFocusManager.cancelFocus();
    }

    public /* synthetic */ void a(float f2, float f3, int i, int i2, int i3) {
        String str = TAG;
        Log.d(str, "onOptionClick: which = " + i3);
        CameraStatUtils.trackGoogleLensPickerValue(i3 == 0);
        if (i3 == 0) {
            DataRepository.dataItemGlobal().editor().putBoolean(CameraSettings.KEY_GOOGLE_LENS_OOBE, true).apply();
            DataRepository.dataItemGlobal().editor().putString("pref_camera_long_press_viewfinder_key", getString(R.string.pref_camera_long_press_viewfinder_default)).apply();
            LensAgent.getInstance().onFocusChange(2, f2 / ((float) Util.sWindowWidth), f3 / ((float) Util.sWindowHeight));
        } else if (i3 == 1) {
            DataRepository.dataItemGlobal().editor().putString("pref_camera_long_press_viewfinder_key", getString(R.string.pref_camera_long_press_viewfinder_lock_ae_af)).apply();
            DataRepository.dataItemGlobal().editor().putBoolean(CameraSettings.KEY_EN_FIRST_CHOICE_LOCK_AE_AF_TOAST, true).apply();
            onSingleTapUp(i, i2, true);
            if (((BaseModule) this).m3ALockSupported) {
                lockAEAF();
            }
            ((BaseModule) this).mMainProtocol.performHapticFeedback(0);
        }
    }

    public /* synthetic */ void a(CameraHardwareFace[] cameraHardwareFaceArr) {
        if (cameraHardwareFaceArr.length > 0) {
            if (!this.mIsFaceConflict) {
                this.mIsFaceConflict = true;
                showOrHideChip(false);
            }
        } else if (this.mIsFaceConflict) {
            this.mIsFaceConflict = false;
            showOrHideChip(true);
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

    public void closeBacklightTip(int i, int i2) {
        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        int i3 = this.mCurrentAiScene;
        if (i3 == -1 && i3 != i) {
            topAlert.alertAiSceneSelector(i2);
        }
    }

    @Override // com.android.camera.module.Module
    public void closeCamera() {
        Log.d(TAG, "closeCamera: E");
        setCameraState(0);
        synchronized (this.mCameraDeviceLock) {
            if (((BaseModule) this).mCamera2Device != null) {
                if (this.mMultiSnapStatus) {
                    ((BaseModule) this).mCamera2Device.captureAbortBurst();
                    this.mMultiSnapStatus = false;
                }
                if (this.mBurstDisposable != null) {
                    this.mBurstDisposable.dispose();
                }
                if (this.mMetaDataFlowableEmitter != null) {
                    this.mMetaDataFlowableEmitter.onComplete();
                }
                if (this.mMetaDataDisposable != null) {
                    this.mMetaDataDisposable.dispose();
                }
                if (this.mAiSceneFlowableEmitter != null) {
                    this.mAiSceneFlowableEmitter.onComplete();
                }
                if (this.mAiSceneDisposable != null) {
                    this.mAiSceneDisposable.dispose();
                }
                if (this.mSuperNightDisposable != null) {
                    this.mSuperNightDisposable.dispose();
                }
                ((BaseModule) this).mCamera2Device.setScreenLightCallback(null);
                ((BaseModule) this).mCamera2Device.setMetaDataCallback(null);
                ((BaseModule) this).mCamera2Device.setErrorCallback(null);
                ((BaseModule) this).mCamera2Device.releaseCameraPreviewCallback(null);
                ((BaseModule) this).mCamera2Device.setFocusCallback(null);
                ((BaseModule) this).mCamera2Device.setAiASDEnable(false);
                if (scanQRCodeEnabled() || b.pd() || this.mIsGoogleLensAvailable) {
                    ((BaseModule) this).mCamera2Device.stopPreviewCallback(true);
                }
                if (this.mFaceDetectionStarted) {
                    this.mFaceDetectionStarted = false;
                }
                this.m3ALocked = false;
                ((BaseModule) this).mCamera2Device.setASDEnable(false);
                ((BaseModule) this).mCamera2Device.setColorEnhanceEnable(false);
                if (!isParallelCameraSessionMode()) {
                    ((BaseModule) this).mCamera2Device = null;
                }
            }
        }
        FocusManager2 focusManager2 = this.mFocusManager;
        if (focusManager2 != null) {
            focusManager2.setAeAwbLock(false);
            this.mFocusManager.destroy();
        }
        if (scanQRCodeEnabled() || b.pd() || this.mIsGoogleLensAvailable) {
            PreviewDecodeManager.getInstance().quit();
        }
        LocalParallelService.LocalBinder localBinder = AlgoConnector.getInstance().getLocalBinder();
        if (localBinder != null) {
            localBinder.setOnSessionStatusCallBackListener(null);
        }
        stopCpuBoost();
        Log.d(TAG, "closeCamera: X");
    }

    public void closeMoonMode(int i, int i2) {
        if (this.mEnteringMoonMode) {
            int i3 = this.mCurrentAiScene;
            if ((i3 == 10 || i3 == 35) && i != this.mCurrentAiScene) {
                ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
                if (topAlert != null) {
                    topAlert.alertMoonModeSelector(i2, false);
                    if (i2 != 0) {
                        this.mEnteringMoonMode = false;
                    }
                    if (8 == i2) {
                        Log.d(TAG, "(moon_mode) close moon mode");
                    }
                    ComponentConfigHdr componentHdr = DataRepository.dataItemConfig().getComponentHdr();
                    if (!componentHdr.isEmpty() && !topAlert.isHDRShowing()) {
                        String componentValue = componentHdr.getComponentValue(((BaseModule) this).mModuleIndex);
                        if ("on".equals(componentValue) || "normal".equals(componentValue)) {
                            topAlert.alertHDR(0, false, false);
                        }
                    }
                }
                updateMoon(false);
                if (((BaseModule) this).mMutexModePicker.isSuperResolution() && !DataRepository.dataItemFeature().Jd()) {
                    ((BaseModule) this).mCamera2Device.setSuperResolution(true);
                }
            }
        }
    }

    @Override // com.android.camera.module.BaseModule
    public void consumePreference(@UpdateConstant.UpdateType int... iArr) {
        for (int i : iArr) {
            switch (i) {
                case 1:
                    updatePictureAndPreviewSize();
                    break;
                case 2:
                    updateFilter();
                    break;
                case 3:
                    updateFocusArea();
                    break;
                case 4:
                    updateScene();
                    break;
                case 5:
                    updateFace();
                    break;
                case 6:
                    updateWhiteBalance();
                    break;
                case 7:
                    updateJpegQuality();
                    break;
                case 8:
                    updateJpegThumbnailSize();
                    break;
                case 9:
                    updateAntiBanding(CameraSettings.getAntiBanding());
                    break;
                case 10:
                    updateFlashPreference();
                    break;
                case 11:
                    updateHDRPreference();
                    break;
                case 12:
                    setEvValue();
                    break;
                case 13:
                    updateBeauty();
                    updateEyeLight();
                    break;
                case 14:
                    updateFocusMode();
                    break;
                case 15:
                    updateISO();
                    break;
                case 16:
                    updateExposureTime();
                    break;
                case 17:
                case 18:
                case 31:
                case 32:
                case 33:
                case 41:
                case 51:
                case 53:
                case 54:
                case 64:
                case 65:
                case 67:
                case 68:
                case 69:
                case 71:
                case 72:
                default:
                    throw new RuntimeException("no consumer for this updateType: " + i);
                case 19:
                    updateFpsRange();
                    break;
                case 20:
                    updateOIS();
                    break;
                case 21:
                    updateMute();
                    break;
                case 22:
                    updateZsl();
                    break;
                case 23:
                    updateDecodePreview();
                    break;
                case 24:
                    applyZoomRatio();
                    break;
                case 25:
                    focusCenter();
                    break;
                case 26:
                    updateContrast();
                    break;
                case 27:
                    updateSaturation();
                    break;
                case 28:
                    updateSharpness();
                    break;
                case 29:
                    updateExposureMeteringMode();
                    break;
                case 30:
                    updateSuperResolution();
                    break;
                case 34:
                    updateMfnr(CameraSettings.isMfnrSatEnable());
                    break;
                case 35:
                    updateDeviceOrientation();
                    break;
                case 36:
                    updateAiScene();
                    break;
                case 37:
                    updateBokeh();
                    break;
                case 38:
                    updateFaceAgeAnalyze();
                    break;
                case 39:
                    updateFaceScore();
                    break;
                case 40:
                    updateFrontMirror();
                    break;
                case 42:
                    updateSwMfnr();
                    break;
                case 43:
                    updatePortraitLighting();
                    break;
                case 44:
                    updateShotDetermine();
                    break;
                case 45:
                    updateEyeLight();
                    break;
                case 46:
                    updateNormalWideLDC();
                    break;
                case 47:
                    updateUltraWideLDC();
                    break;
                case 48:
                    updateFNumber();
                    break;
                case 49:
                    updateLiveShot();
                    break;
                case 50:
                    break;
                case 52:
                    updateMacroMode();
                    break;
                case 55:
                    updateModuleRelated();
                    break;
                case 56:
                    updateSuperNight();
                    break;
                case 57:
                    updateUltraPixelPortrait();
                    break;
                case 58:
                    updateBackSoftLightPreference();
                    break;
                case 59:
                    updateOnTripMode();
                    break;
                case 60:
                    updateCinematicPhoto();
                    break;
                case 61:
                    updateASDDirtyDetect();
                    break;
                case 62:
                    updateWatermarkTag();
                    break;
                case 63:
                    updateEvValue();
                    break;
                case 66:
                    updateThermalLevel();
                    break;
                case 70:
                    updateASD();
                    break;
                case 73:
                    updateAIWatermark();
                    break;
                case 74:
                    updateColorEnhance();
                    break;
            }
        }
    }

    @Override // com.android.camera.module.BaseModule, com.android.camera.MutexModeManager.MutexCallBack
    public void enterMutexMode(int i) {
        Camera2Proxy camera2Proxy = ((BaseModule) this).mCamera2Device;
        if (camera2Proxy == null) {
            Log.d(TAG, "enterMutexMode error, mCamera2Device is null");
            return;
        }
        if (i == 1) {
            camera2Proxy.setHDR(true);
        } else if (i == 3) {
            camera2Proxy.setHHT(true);
        } else if (i == 10) {
            camera2Proxy.setSuperResolution(true);
        }
        updateMfnr(CameraSettings.isMfnrSatEnable());
        updateSwMfnr();
    }

    @Override // com.android.camera.module.BaseModule, com.android.camera.MutexModeManager.MutexCallBack
    public void exitMutexMode(int i) {
        if (i == 1) {
            ((BaseModule) this).mCamera2Device.setHDR(false);
            updateSuperResolution();
        } else if (i == 3) {
            ((BaseModule) this).mCamera2Device.setHHT(false);
        } else if (i == 10) {
            ((BaseModule) this).mCamera2Device.setSuperResolution(false);
        }
        updateMfnr(CameraSettings.isMfnrSatEnable());
        updateSwMfnr();
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.BaseModule
    public void focusCenter() {
    }

    /* access modifiers changed from: protected */
    public CameraSize getBestPictureSize(List<CameraSize> list) {
        PictureSizeManager.initialize(list, getMaxPictureSize(), ((BaseModule) this).mModuleIndex, ((BaseModule) this).mBogusCameraId);
        return PictureSizeManager.getBestPictureSize();
    }

    /* access modifiers changed from: protected */
    public CameraSize getBestPictureSize(List<CameraSize> list, float f2) {
        PictureSizeManager.initialize(list, getMaxPictureSize(), ((BaseModule) this).mModuleIndex, ((BaseModule) this).mBogusCameraId);
        return PictureSizeManager.getBestPictureSize(f2);
    }

    public CircularMediaRecorder getCircularMediaRecorder() {
        CircularMediaRecorder circularMediaRecorder;
        synchronized (this.mCircularMediaRecorderStateLock) {
            circularMediaRecorder = this.mCircularMediaRecorder;
        }
        return circularMediaRecorder;
    }

    public int getCurrentAiScene() {
        return this.mCurrentAiScene;
    }

    @Override // com.android.camera.module.BaseModule
    public String getDebugInfo() {
        CameraConfigs cameraConfigs;
        MeteringRectangle[] aFRegions;
        String str;
        CameraCharacteristics cameraCharacteristics;
        StringBuilder sb = new StringBuilder();
        int moduleIndex = getModuleIndex();
        CameraCapabilities cameraCapabilities = getCameraCapabilities();
        if (!(cameraCapabilities == null || (cameraCharacteristics = cameraCapabilities.getCameraCharacteristics()) == null)) {
            float[] fArr = (float[]) cameraCharacteristics.get(CameraCharacteristics.LENS_INFO_AVAILABLE_FOCAL_LENGTHS);
            float[] fArr2 = (float[]) cameraCharacteristics.get(CameraCharacteristics.LENS_INFO_AVAILABLE_APERTURES);
            if (fArr != null && fArr.length > 0) {
                sb.append("LensFocal:" + fArr[0] + " ");
            }
            if (fArr2 != null && fArr2.length > 0) {
                sb.append("LensApertues:" + fArr2[0] + " ");
            }
        }
        if (moduleIndex == 167) {
            sb.append("SceneProfession:true");
        }
        sb.append("ZoomMultiple:" + getZoomRatio() + " ");
        Camera2Proxy cameraDevice = getCameraDevice();
        if (!(cameraDevice == null || (cameraConfigs = cameraDevice.getCameraConfigs()) == null || (aFRegions = cameraConfigs.getAFRegions()) == null || aFRegions.length <= 0)) {
            MeteringRectangle meteringRectangle = aFRegions[0];
            if (meteringRectangle == null) {
                str = "0";
            } else {
                int x = meteringRectangle.getX();
                int y = meteringRectangle.getY();
                str = "[" + x + "," + y + "," + (meteringRectangle.getWidth() + x) + "," + (meteringRectangle.getHeight() + y) + "]";
            }
            sb.append("afRoi:" + str + " ");
        }
        String faceInfoString = DebugInfoUtil.getFaceInfoString(((BaseModule) this).mMainProtocol.getFaces());
        if (!TextUtils.isEmpty(faceInfoString)) {
            sb.append("FaceRoi:" + faceInfoString + " ");
        }
        sb.append("FilterId:" + CameraSettings.getShaderEffect() + " ");
        sb.append("AIScene:" + getCurrentAiScene() + " ");
        return sb.toString();
    }

    @Override // com.android.camera2.Camera2Proxy.LivePhotoResultCallback
    public int getFilterId() {
        return this.mFilterId;
    }

    /* access modifiers changed from: protected */
    public int getMaxPictureSize() {
        return 0;
    }

    /* access modifiers changed from: protected */
    public int getMutexHdrMode(String str) {
        if ("normal".equals(str)) {
            return 1;
        }
        return (!b.Ol() || !"live".equals(str)) ? 0 : 2;
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:119:0x01f6, code lost:
        if (com.mi.config.b.hv == false) goto L_0x01b1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:92:0x01a2, code lost:
        if (((com.android.camera.module.BaseModule) r10).mCameraCapabilities.isSupportLightTripartite() != false) goto L_0x01a4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:97:0x01ae, code lost:
        if (com.mi.config.b.hv != false) goto L_0x01a4;
     */
    @Override // com.android.camera.module.BaseModule
    public int getOperatingMode() {
        int i;
        if (isParallelSessionEnable()) {
            boolean isInQCFAMode = isInQCFAMode();
            int i2 = CameraCapabilities.SESSION_OPERATION_MODE_ALGO_UP_NORMAL;
            if (isInQCFAMode) {
                Log.d(TAG, "getOperatingMode: SESSION_OPERATION_MODE_ALGO_UP_QCFA");
                i2 = CameraCapabilities.SESSION_OPERATION_MODE_ALGO_UP_QCFA;
            } else if (167 == getModuleIndex()) {
                if (CameraSettings.isUltraPixelOn()) {
                    Log.d(TAG, "getOperatingMode: SESSION_OPERATION_MODE_ALGO_UP_MANUAL_ULTRA_PIXEL");
                    i2 = CameraCapabilities.SESSION_OPERATION_MODE_ALGO_UP_MANUAL_ULTRA_PIXEL;
                } else {
                    Log.d(TAG, "getOperatingMode: SESSION_OPERATION_MODE_ALGO_UP_MANUAL");
                    i2 = (b.Xu || b.Zu) ? CameraCapabilities.SESSION_OPERATION_MODE_ALGO_UP_MANUAL_G7 : CameraCapabilities.SESSION_OPERATION_MODE_ALGO_UP_MANUAL;
                }
            } else if (171 == getModuleIndex()) {
                if (!isFrontCamera() || isDualFrontCamera()) {
                    Log.d(TAG, "getOperatingMode: SESSION_OPERATION_MODE_ALGO_UP_DUAL_BOKEH");
                    i2 = 36864;
                } else {
                    Log.d(TAG, "getOperatingMode: SESSION_OPERATION_MODE_ALGO_UP_SINGLE_BOKEH");
                    i2 = CameraCapabilities.SESSION_OPERATION_MODE_ALGO_UP_SINGLE_BOKEH;
                }
            } else if (182 == getModuleIndex()) {
                Log.d(TAG, "getOperatingMode: SESSION_OPERATION_MODE_ALGO_UP_NORMAL");
            } else if (CameraSettings.isUltraPixelOn()) {
                Log.d(TAG, "getOperatingMode: SESSION_OPERATION_MODE_ALGO_UP_HD");
                i2 = CameraCapabilities.SESSION_OPERATION_MODE_ALGO_UP_HD;
            } else if (isUltraWideBackCamera()) {
                Log.d(TAG, "getOperatingMode: SESSION_OPERATION_MODE_ALGO_UP_NORMAL");
            } else if (!CameraSettings.isSupportedOpticalZoom() || (this.mIsImageCaptureIntent && ((BaseModule) this).mCameraCapabilities.isSupportLightTripartite())) {
                Log.d(TAG, "getOperatingMode: SESSION_OPERATION_MODE_ALGO_UP_NORMAL");
            } else if (isFrontCamera() && DataRepository.dataItemFeature().dd()) {
                Log.d(TAG, "getOperatingMode: SESSION_OPERATION_MODE_ALGO_UP_NORMAL");
            } else if (DataRepository.dataItemRunning().getComponentRunningMacroMode().isSwitchOn(getModuleIndex())) {
                Log.d(TAG, "getOperatingMode: SESSION_OPERATION_MODE_ALGO_UP_NORMAL");
            } else {
                Log.d(TAG, "getOperatingMode: SESSION_OPERATION_MODE_ALGO_UP_SAT");
                i2 = CameraCapabilities.SESSION_OPERATION_MODE_ALGO_UP_SAT;
            }
            this.mOperatingMode = i2;
            return i2;
        }
        int i3 = 32775;
        if (isFrontCamera()) {
            mIsBeautyFrontOn = true;
            if (!isPortraitMode() || !DataRepository.dataItemFeature()._e()) {
                if (!isPortraitMode() || !isBokehFrontCamera()) {
                    i = (!((BaseModule) this).mCameraCapabilities.isSupportedQcfa() || mIsBeautyFrontOn || !"off".equals(DataRepository.dataItemConfig().getComponentHdr().getComponentValue(((BaseModule) this).mModuleIndex)) || DataRepository.dataItemFeature().Ab() >= 0) ? 32773 : 32775;
                    if (((BaseModule) this).mModuleIndex != 163 || !CameraSettings.isUltraPixelOn()) {
                        i3 = i;
                    }
                }
            } else if (!isBokehFrontCamera()) {
                i = 33009;
                i3 = i;
            }
            i = 32770;
            i3 = i;
        } else {
            ComponentRunningMacroMode componentRunningMacroMode = DataRepository.dataItemRunning().getComponentRunningMacroMode();
            int moduleIndex = getModuleIndex();
            if (moduleIndex != 163) {
                if (moduleIndex == 167) {
                    i3 = CameraSettings.isUltraPixelOn() ? CameraCapabilities.SESSION_OPERATION_MODE_PROFESSIONAL_ULTRA_PIXEL_PHOTOGRAPHY : 32771;
                } else if (moduleIndex == 171) {
                    i3 = 32770;
                } else if (moduleIndex != 173) {
                    if (moduleIndex != 175) {
                        if (this.mIsImageCaptureIntent) {
                        }
                        if (componentRunningMacroMode.isSwitchOn(moduleIndex)) {
                        }
                        i3 = 32769;
                    }
                    i3 = 33011;
                } else {
                    i3 = CameraCapabilities.SESSION_OPERATION_MODE_SUPER_NIGHT;
                }
            } else if (!this.mIsImageCaptureIntent || !((BaseModule) this).mCameraCapabilities.isSupportLightTripartite()) {
                if (!CameraSettings.isUltraPixelOn()) {
                    if (CameraSettings.isDualCameraSatEnable()) {
                        if (!DataRepository.dataItemFeature().kc()) {
                            if (componentRunningMacroMode.isSwitchOn(moduleIndex)) {
                            }
                            i3 = 32769;
                        }
                    }
                }
                i3 = 33011;
            }
            i3 = 0;
        }
        this.mOperatingMode = i3;
        Log.d(TAG, "getOperatingMode: " + String.format("operatingMode = 0x%x", Integer.valueOf(i3)));
        return i3;
    }

    @Override // com.android.camera.module.BaseModule
    public void initializeCapabilities() {
        super.initializeCapabilities();
        ((BaseModule) this).mContinuousFocusSupported = Util.isSupported(4, ((BaseModule) this).mCameraCapabilities.getSupportedFocusModes());
        ((BaseModule) this).mMaxFaceCount = ((BaseModule) this).mCameraCapabilities.getMaxFaceCount();
    }

    /* access modifiers changed from: protected */
    public boolean isAutoRestartInNonZSL() {
        return false;
    }

    @Override // com.android.camera2.Camera2Proxy.BeautyBodySlimCountCallback
    public boolean isBeautyBodySlimCountDetectStarted() {
        return this.mIsBeautyBodySlimOn;
    }

    @Override // com.android.camera.module.BaseModule, com.android.camera.module.Module, com.android.camera.protocol.ModeProtocol.CameraAction
    public boolean isBlockSnap() {
        Camera2Proxy camera2Proxy;
        boolean z = !isParallelCameraSessionMode() ? getCameraState() == 3 : !((camera2Proxy = ((BaseModule) this).mCamera2Device) == null || !camera2Proxy.isParallelBusy(false));
        if (((BaseModule) this).mPaused || ((BaseModule) this).isZooming || isKeptBitmapTexture() || this.mMultiSnapStatus || getCameraState() == 0 || z) {
            return true;
        }
        Camera2Proxy camera2Proxy2 = ((BaseModule) this).mCamera2Device;
        return (camera2Proxy2 != null && camera2Proxy2.isCaptureBusy(((BaseModule) this).mMutexModePicker.isHdr())) || isQueueFull() || isInCountDown() || this.mWaitSaveFinish || !isParallelSessionConfigured() || ((BaseModule) this).mHandler.hasMessages(62);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.BaseModule
    public boolean isCameraSwitchingDuringZoomingAllowed() {
        return CameraSettings.isSupportedOpticalZoom() ? super.isCameraSwitchingDuringZoomingAllowed() : HybridZoomingSystem.IS_3_OR_MORE_SAT && !CameraSettings.isMacroModeEnabled(((BaseModule) this).mModuleIndex) && isBackCamera();
    }

    /* access modifiers changed from: protected */
    public boolean isDetectedHHT() {
        return false;
    }

    @Override // com.android.camera.module.Module, com.android.camera.protocol.ModeProtocol.CameraAction
    public boolean isDoingAction() {
        Camera2Proxy camera2Proxy;
        Camera2Proxy camera2Proxy2;
        LocalParallelService.LocalBinder localBinder;
        if (!DataRepository.dataItemFeature().ze() || (localBinder = AlgoConnector.getInstance().getLocalBinder()) == null || localBinder.isIdle()) {
            return ((BaseModule) this).mPaused || ((BaseModule) this).isZooming || isKeptBitmapTexture() || this.mMultiSnapStatus || getCameraState() == 0 || (!isParallelCameraSessionMode() ? getCameraState() == 3 || ((camera2Proxy = ((BaseModule) this).mCamera2Device) != null && camera2Proxy.isCaptureBusy(true)) : getCameraState() == 3 || (((camera2Proxy2 = ((BaseModule) this).mCamera2Device) != null && camera2Proxy2.isParallelBusy(true)) || AlgoConnector.getInstance().getLocalBinder().isAnyRequestBlocked())) || isQueueFull() || this.mWaitSaveFinish || isInCountDown();
        }
        Log.i(TAG, "[ALGOUP|MMCAMERA] Doing action");
        return true;
    }

    /* access modifiers changed from: protected */
    public boolean isFaceBeautyMode() {
        return false;
    }

    @Override // com.android.camera2.Camera2Proxy.FaceDetectionCallback
    public boolean isFaceDetectStarted() {
        return this.mFaceDetectionStarted;
    }

    public boolean isGoogleLensAvailable() {
        return this.mIsGoogleLensAvailable;
    }

    @Override // com.android.camera2.Camera2Proxy.LivePhotoResultCallback
    public boolean isGyroStable() {
        return Util.isGyroscopeStable(this.curGyroscope, this.lastGyroscope);
    }

    @Override // com.android.camera2.Camera2Proxy.HdrCheckerCallback
    public boolean isHdrSceneDetectionStarted() {
        return this.mHdrCheckEnabled;
    }

    @Override // com.android.camera.module.BaseModule, com.android.camera.module.Module
    public boolean isKeptBitmapTexture() {
        return this.mKeepBitmapTexture;
    }

    @Override // com.android.camera2.Camera2Proxy.LivePhotoResultCallback
    public boolean isLivePhotoStarted() {
        return this.mLiveShotEnabled;
    }

    @Override // com.android.camera2.Camera2Proxy.MagneticDetectedCallback
    public boolean isLockHDRChecker() {
        ModeProtocol.MagneticSensorDetect magneticSensorDetect = this.mMagneticSensorDetect;
        if (magneticSensorDetect != null) {
            return magneticSensorDetect.isLockHDRChecker();
        }
        return false;
    }

    @Override // com.android.camera.module.BaseModule, com.android.camera.ui.FocusView.ExposureViewListener
    public boolean isMeteringAreaOnly() {
        Camera2Proxy camera2Proxy = ((BaseModule) this).mCamera2Device;
        if (camera2Proxy == null) {
            return false;
        }
        int focusMode = camera2Proxy.getFocusMode();
        return (!((BaseModule) this).mFocusAreaSupported && ((BaseModule) this).mMeteringAreaSupported && !((BaseModule) this).mFocusOrAELockSupported) || 5 == focusMode || focusMode == 0;
    }

    @Override // com.android.camera.module.BaseModule
    public boolean isNeedMute() {
        return CameraSettings.isLiveShotOn();
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.BaseModule
    public boolean isParallelSessionEnable() {
        if (!CameraSettings.isCameraParallelProcessEnable() || getModuleIndex() == 173) {
            return false;
        }
        boolean isSwitchOn = DataRepository.dataItemConfig().getComponentConfigRaw().isSwitchOn(getModuleIndex());
        if (getModuleIndex() == 167 && (!DataRepository.dataItemFeature().Hc() || isSwitchOn)) {
            return false;
        }
        if (getModuleIndex() == 175 && DataRepository.dataItemFeature().Gc()) {
            return false;
        }
        if (isStandaloneMacroCamera() && !DataRepository.dataItemFeature().Oc()) {
            return false;
        }
        if (!isUltraWideBackCamera() || DataRepository.dataItemFeature().he()) {
            return (!this.mIsImageCaptureIntent || DataRepository.dataItemFeature().cc()) && !DataRepository.dataItemGlobal().isForceMainBackCamera();
        }
        return false;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.BaseModule
    public boolean isRepeatingRequestInProgress() {
        return this.mMultiSnapStatus && 3 == getCameraState();
    }

    @Override // com.android.camera.module.BaseModule, com.android.camera.module.Module
    public boolean isSelectingCapturedResult() {
        ModeProtocol.BaseDelegate baseDelegate;
        return this.mIsImageCaptureIntent && (baseDelegate = (ModeProtocol.BaseDelegate) ModeCoordinatorImpl.getInstance().getAttachProtocol(160)) != null && baseDelegate.getActiveFragment(R.id.bottom_action) == 4083;
    }

    @Override // com.android.camera.module.BaseModule, com.android.camera.module.Module
    public boolean isShot2GalleryOrEnableParallel() {
        return this.mEnableShot2Gallery || this.mEnableParallelSession;
    }

    @Override // com.android.camera.module.BaseModule, com.android.camera.ui.FocusView.ExposureViewListener
    public boolean isShowAeAfLockIndicator() {
        return this.m3ALocked;
    }

    public boolean isShowBacklightTip() {
        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        if (topAlert != null) {
            return topAlert.isShowBacklightSelector();
        }
        return false;
    }

    @Override // com.android.camera.module.BaseModule, com.android.camera.ui.FocusView.ExposureViewListener
    public boolean isShowCaptureButton() {
        return !((BaseModule) this).mMutexModePicker.isBurstShoot() && isSupportFocusShoot();
    }

    @Override // com.android.camera.module.BaseModule, com.android.camera.ui.FocusView.ExposureViewListener
    public boolean isSupportFocusShoot() {
        return DataRepository.dataItemGlobal().isGlobalSwitchOn("pref_camera_focus_shoot_key");
    }

    /* access modifiers changed from: protected */
    public boolean isSupportSceneMode() {
        return false;
    }

    @Override // com.android.camera2.Camera2Proxy.SuperNightCallback
    public boolean isSupportSuperNight() {
        if (!DataRepository.dataItemFeature().Rd() || (b.gv && !Util.sSuperNightDefaultModeEnable)) {
            return false;
        }
        return (163 == getModuleIndex() || 165 == getModuleIndex()) && isBackCamera() && 1.0f == CameraSettings.readZoom() && !this.mIsMacroModeEnable && isSuperNightSeEnable();
    }

    @Override // com.android.camera.module.Module
    public boolean isUnInterruptable() {
        ((BaseModule) this).mUnInterruptableReason = null;
        if (isKeptBitmapTexture()) {
            ((BaseModule) this).mUnInterruptableReason = "bitmap cover";
        } else if (getCameraState() == 3) {
            ((BaseModule) this).mUnInterruptableReason = "snapshot";
        }
        return ((BaseModule) this).mUnInterruptableReason != null;
    }

    @Override // com.android.camera2.Camera2Proxy.FaceDetectionCallback
    public boolean isUseFaceInfo() {
        return this.mIsGenderAgeOn || this.mIsMagicMirrorOn;
    }

    @Override // com.android.camera.module.BaseModule
    public boolean isZoomEnabled() {
        Camera2Proxy camera2Proxy;
        return getCameraState() != 3 && !((BaseModule) this).mMutexModePicker.isUbiFocus() && !CameraSettings.isPortraitModeBackOn() && !isFrontCamera() && (camera2Proxy = ((BaseModule) this).mCamera2Device) != null && !camera2Proxy.isCaptureBusy(true) && (!CameraSettings.isUltraPixelOn() || DataRepository.dataItemFeature().Hd()) && ((BaseModule) this).mModuleIndex != 182 && isFrameAvailable();
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.BaseModule
    public boolean isZslPreferred() {
        if (b.isMTKPlatform()) {
            if (!isParallelSessionEnable()) {
                return false;
            }
            int i = ((BaseModule) this).mModuleIndex;
            if (i == 163 || i == 165 || i == 171) {
                return true;
            }
        } else if (((BaseModule) this).mModuleIndex != 167) {
            return true;
        }
        return false;
    }

    @Override // com.android.camera2.Camera2Proxy.MagneticDetectedCallback
    public void magneticDetectedUpdateCapture() {
        ModeProtocol.MagneticSensorDetect magneticSensorDetect = this.mMagneticSensorDetect;
        if (magneticSensorDetect != null) {
            magneticSensorDetect.recordMagneticInfo();
        }
    }

    @Override // com.android.camera.module.loader.camera2.FocusManager2.Listener
    public boolean multiCapture() {
        if (isDoingAction() || !this.mPendingMultiCapture) {
            return false;
        }
        this.mPendingMultiCapture = false;
        ((BaseModule) this).mActivity.getScreenHint().updateHint();
        if (Storage.isLowStorageAtLastPoint()) {
            String str = TAG;
            Log.i(str, "Not enough space or storage not ready. remaining=" + Storage.getLeftSpace());
            return false;
        } else if (((BaseModule) this).mActivity.getImageSaver().isBusy()) {
            Log.d(TAG, "ImageSaver is busy, wait for a moment!");
            RotateTextToast.getInstance(((BaseModule) this).mActivity).show(R.string.toast_saving, 0);
            return false;
        } else if (this.mIsMoonMode) {
            return false;
        } else {
            ((ModeProtocol.ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164)).closeMutexElement(SupportedConfigFactory.CLOSE_BY_BURST_SHOOT, 193, 194, 196, 239, 201, 206);
            ModeProtocol.BackStack backStack = (ModeProtocol.BackStack) ModeCoordinatorImpl.getInstance().getAttachProtocol(171);
            if (backStack != null) {
                backStack.handleBackStackFromShutter();
            }
            prepareMultiCapture();
            Observable.create(new ObservableOnSubscribe<Integer>() {
                /* class com.android.camera.module.Camera2Module.AnonymousClass2 */

                @Override // io.reactivex.ObservableOnSubscribe
                public void subscribe(ObservableEmitter<Integer> observableEmitter) throws Exception {
                    ObservableEmitter unused = Camera2Module.this.mBurstEmitter = (ObservableEmitter) observableEmitter;
                }
            }).observeOn(AndroidSchedulers.mainThread()).map(new Function<Integer, Integer>() {
                /* class com.android.camera.module.Camera2Module.AnonymousClass4 */

                public Integer apply(Integer num) throws Exception {
                    ModeProtocol.SnapShotIndicator snapShotIndicator = (ModeProtocol.SnapShotIndicator) ModeCoordinatorImpl.getInstance().getAttachProtocol(184);
                    if (snapShotIndicator != null) {
                        ModeProtocol.MiBeautyProtocol miBeautyProtocol = (ModeProtocol.MiBeautyProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(194);
                        snapShotIndicator.setSnapNumVisible(!(miBeautyProtocol != null && miBeautyProtocol.isBeautyPanelShow()), false);
                        snapShotIndicator.setSnapNumValue(num.intValue());
                    }
                    return num;
                }
            }).subscribe(new Observer<Integer>() {
                /* class com.android.camera.module.Camera2Module.AnonymousClass3 */

                @Override // io.reactivex.Observer
                public void onComplete() {
                    ModeProtocol.SnapShotIndicator snapShotIndicator = (ModeProtocol.SnapShotIndicator) ModeCoordinatorImpl.getInstance().getAttachProtocol(184);
                    if (snapShotIndicator != null) {
                        snapShotIndicator.setSnapNumVisible(false, true);
                    }
                    ModeProtocol.ConfigChanges configChanges = (ModeProtocol.ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164);
                    if (configChanges != null) {
                        configChanges.restoreAllMutexElement(SupportedConfigFactory.CLOSE_BY_BURST_SHOOT);
                    }
                }

                @Override // io.reactivex.Observer
                public void onError(Throwable th) {
                }

                public void onNext(Integer num) {
                }

                @Override // io.reactivex.Observer
                public void onSubscribe(Disposable disposable) {
                    long unused = Camera2Module.this.mBurstStartTime = System.currentTimeMillis();
                    Disposable unused2 = Camera2Module.this.mBurstDisposable = disposable;
                }
            });
            this.mBurstNextDelayTime = 0;
            if (isParallelSessionEnable()) {
                ((BaseModule) this).mCamera2Device.setShotType(9);
                ((BaseModule) this).mCamera2Device.captureBurstPictures(this.mTotalJpegCallbackNum, new JpegRepeatingCaptureCallback(this), ((BaseModule) this).mActivity.getImageSaver());
            } else {
                ScenarioTrackUtil.trackScenarioAbort(ScenarioTrackUtil.sShotToGalleryTimeScenario, String.valueOf(this.mCaptureStartTime));
                ScenarioTrackUtil.trackScenarioAbort(ScenarioTrackUtil.sShotToViewTimeScenario, String.valueOf(this.mCaptureStartTime));
                ((BaseModule) this).mCamera2Device.setShotType(3);
                ((BaseModule) this).mCamera2Device.setAWBLock(true);
                ((BaseModule) this).mCamera2Device.captureBurstPictures(this.mTotalJpegCallbackNum, new JpegQuickPictureCallback(LocationManager.instance().getCurrentLocation()), ((BaseModule) this).mActivity.getImageSaver());
            }
            return true;
        }
    }

    @Override // com.android.camera.module.loader.camera2.FocusManager2.Listener
    public void notifyFocusAreaUpdate() {
        updatePreferenceTrampoline(3);
    }

    @Override // com.android.camera.module.BaseModule, com.android.camera.module.Module
    public boolean onBackPressed() {
        if (!isFrameAvailable()) {
            return false;
        }
        tryRemoveCountDownMessage();
        if (this.mMultiSnapStatus) {
            onShutterButtonLongClickCancel(false);
            return true;
        }
        if (getCameraState() == 3) {
            long currentTimeMillis = System.currentTimeMillis();
            if (((BaseModule) this).mModuleIndex == 173) {
                if (currentTimeMillis - ((BaseModule) this).mLastBackPressedTime > 3000) {
                    ((BaseModule) this).mLastBackPressedTime = currentTimeMillis;
                    ToastUtils.showToast(((BaseModule) this).mActivity, (int) R.string.capture_back_pressed_hint);
                    return true;
                }
            } else if (currentTimeMillis - this.mLastCaptureTime < CAPTURE_DURATION_THRESHOLD) {
                return true;
            }
        }
        return super.onBackPressed();
    }

    @Override // com.android.camera2.Camera2Proxy.BeautyBodySlimCountCallback
    public void onBeautyBodySlimCountChange(final boolean z) {
        ((BaseModule) this).mHandler.post(new Runnable() {
            /* class com.android.camera.module.Camera2Module.AnonymousClass8 */

            public void run() {
                ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
                if (topAlert != null) {
                    if (z) {
                        topAlert.alertAiDetectTipHint(0, R.string.beauty_body_slim_count_tip, FunctionParseBeautyBodySlimCount.TIP_TIME);
                    } else {
                        topAlert.alertAiDetectTipHint(8, R.string.beauty_body_slim_count_tip, 0);
                    }
                }
            }
        });
    }

    @Override // com.android.camera.module.BaseModule, com.android.camera.module.Module
    public void onBroadcastReceived(Context context, Intent intent) {
        if (intent != null && isAlive()) {
            if (CameraIntentManager.ACTION_VOICE_CONTROL.equals(intent.getAction())) {
                if (context.checkCallingPermission(Manifest.permission.AUX_CONTROL) != 0) {
                    Log.d(TAG, "on Receive voice control broadcast action intent: insufficient permission.");
                    return;
                }
                Log.d(TAG, "on Receive voice control broadcast action intent");
                String voiceControlAction = CameraIntentManager.getInstance(intent).getVoiceControlAction();
                this.mBroadcastIntent = intent;
                char c2 = 65535;
                if (voiceControlAction.hashCode() == 1270567718 && voiceControlAction.equals("CAPTURE")) {
                    c2 = 0;
                }
                if (c2 == 0) {
                    onShutterButtonClick(getTriggerMode());
                    this.mBroadcastIntent = null;
                }
                CameraIntentManager.removeInstance(intent);
            }
            super.onBroadcastReceived(context, intent);
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.BaseModule
    public void onCameraOpened() {
        super.onCameraOpened();
        int i = 0;
        if (!isBackCamera() || ((BaseModule) this).mActualCameraId != Camera2DataContainer.getInstance().getSATCameraId()) {
            EffectController.getInstance().setAiColorCorrectionVersion(0);
        } else {
            EffectController instance = EffectController.getInstance();
            CameraCapabilities cameraCapabilities = ((BaseModule) this).mCameraCapabilities;
            if (cameraCapabilities != null) {
                i = cameraCapabilities.getAiColorCorrectionVersion();
            }
            instance.setAiColorCorrectionVersion(i);
        }
        initializeFocusManager();
        updatePreferenceTrampoline(UpdateConstant.CAMERA_TYPES_INIT);
        if (this.mEnableParallelSession && isPortraitMode()) {
            Util.saveCameraCalibrationToFile(((BaseModule) this).mCameraCapabilities.getCameraCalibrationData(), getCalibrationDataFileName(((BaseModule) this).mActualCameraId));
        }
        if (!isKeptBitmapTexture()) {
            startPreview();
        }
        initMetaParser();
        if (DataRepository.dataItemFeature().Wc()) {
            initAiSceneParser();
        }
        this.mOnResumeTime = SystemClock.uptimeMillis();
        ((BaseModule) this).mHandler.sendEmptyMessage(4);
        ((BaseModule) this).mHandler.sendEmptyMessage(31);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.BaseModule
    public void onCapabilityChanged(CameraCapabilities cameraCapabilities) {
        super.onCapabilityChanged(cameraCapabilities);
        this.mUltraWideAELocked = false;
        FocusManager2 focusManager2 = this.mFocusManager;
        if (focusManager2 != null) {
            focusManager2.setCharacteristics(cameraCapabilities);
        }
        Camera2Proxy camera2Proxy = ((BaseModule) this).mCamera2Device;
        if (camera2Proxy != null) {
            camera2Proxy.onCapabilityChanged(cameraCapabilities);
        }
    }

    @Override // com.android.camera2.Camera2Proxy.PictureCallback
    public void onCaptureCompleted(boolean z) {
        checkMoreFrameCaptureLockAFAE(false);
        handleLLSResultInCaptureMode();
    }

    @Override // com.android.camera2.Camera2Proxy.PictureCallback
    public void onCaptureShutter(boolean z) {
        String str = TAG;
        Log.d(str, "onCaptureShutter: cameraState = " + getCameraState() + ", isParallel = " + this.mEnableParallelSession);
        onShutter(z);
    }

    @Override // com.android.camera2.Camera2Proxy.PictureCallback
    public ParallelTaskData onCaptureStart(ParallelTaskData parallelTaskData, CameraSize cameraSize, boolean z) {
        Camera camera;
        ImageSaver imageSaver;
        List<WaterMarkData> faceWaterMarkInfos;
        if (isDeparted()) {
            Log.w(TAG, "onCaptureStart: departed");
            parallelTaskData.setAbandoned(true);
            return parallelTaskData;
        }
        parallelTaskData.setServiceStatusListener(this.mServiceStatusListener);
        int parallelType = parallelTaskData.getParallelType();
        boolean z2 = CameraSettings.isLiveShotOn() && isLiveShotAvailable(parallelType);
        if (z2) {
            startLiveShotAnimation();
        }
        if (!z || (CameraSettings.isGroupShotOn() && !this.mEnableParallelSession)) {
            if (!CameraSettings.isSupportedZslShutter()) {
                updateEnablePreviewThumbnail(z);
                if (this.mEnabledPreviewThumbnail) {
                    CameraSettings.setPlayToneOnCaptureStart(false);
                }
            }
            if (CameraSettings.isUltraPixelOn() && this.mEnabledPreviewThumbnail) {
                CameraSettings.setPlayToneOnCaptureStart(false);
            } else if (!this.mEnabledPreviewThumbnail) {
                onShutter(z);
                CameraSettings.setPlayToneOnCaptureStart(true);
            }
        }
        String str = null;
        ArrayList arrayList = (!CameraSettings.isAgeGenderAndMagicMirrorWaterOpen() || (faceWaterMarkInfos = ((ModeProtocol.MainContentProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(166)).getFaceWaterMarkInfos()) == null || faceWaterMarkInfos.isEmpty()) ? null : new ArrayList(faceWaterMarkInfos);
        Log.d(TAG, "onCaptureStart: inputSize = " + cameraSize);
        if ((isIn3OrMoreSatMode() || isInMultiSurfaceSatMode()) && !cameraSize.equals(((BaseModule) this).mPictureSize)) {
            ((BaseModule) this).mPictureSize = cameraSize;
            updateOutputSize(cameraSize);
        }
        CameraSize cameraSize2 = ((BaseModule) this).mOutputPictureSize;
        Size sizeObject = cameraSize2 == null ? cameraSize.toSizeObject() : cameraSize2.toSizeObject();
        Log.d(TAG, "onCaptureStart: outputSize = " + sizeObject);
        int pictureFormatSuitableForShot = getPictureFormatSuitableForShot(parallelType);
        boolean isHeicImageFormat = CompatibilityUtils.isHeicImageFormat(pictureFormatSuitableForShot);
        String str2 = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("onCaptureStart: outputFormat = ");
        sb.append(isHeicImageFormat ? "HEIC" : "JPEG");
        Log.d(str2, sb.toString());
        int clampQuality = clampQuality(CameraSettings.getEncodingQuality(false).toInteger(isHeicImageFormat));
        Log.d(TAG, "onCaptureStart: outputQuality = " + clampQuality);
        CameraCharacteristics cameraCharacteristics = ((BaseModule) this).mCameraCapabilities.getCameraCharacteristics();
        this.mFocalLengths = (float[]) cameraCharacteristics.get(CameraCharacteristics.LENS_INFO_AVAILABLE_FOCAL_LENGTHS);
        this.mApertures = (float[]) cameraCharacteristics.get(CameraCharacteristics.LENS_INFO_AVAILABLE_APERTURES);
        ParallelTaskDataParameter.Builder builder = new ParallelTaskDataParameter.Builder(((BaseModule) this).mPreviewSize.toSizeObject(), cameraSize.toSizeObject(), sizeObject, pictureFormatSuitableForShot);
        if (parallelType == 1) {
            CameraSize cameraSize3 = ((BaseModule) this).mSensorRawImageSize;
            builder.setRawSize(cameraSize3.width, cameraSize3.height);
        }
        boolean z3 = DataRepository.dataItemFeature().We() && (Util.isStringValueContained("device", ((BaseModule) this).mCamera2Device.getCameraConfigs().getWaterMarkAppliedList()) || Util.isStringValueContained(WatermarkConstant.ITEM_TAG, ((BaseModule) this).mCamera2Device.getCameraConfigs().getWaterMarkAppliedList()));
        WatermarkItem watermarkItem = CameraSettings.isAIWatermarkOn(((BaseModule) this).mModuleIndex) ? DataRepository.dataItemRunning().getComponentRunningAIWatermark().getWatermarkItem() : null;
        Location location = ((BaseModule) this).mActivity.getCameraIntentManager().checkIntentLocationPermission(((BaseModule) this).mActivity) ? this.mLocation : null;
        ParallelTaskDataParameter.Builder filterId = builder.setHasDualWaterMark(CameraSettings.isDualCameraWaterMarkOpen()).setVendorWaterMark(z3).setMirror(isFrontMirror()).setLightingPattern(CameraSettings.getPortraitLightingPattern()).setFilterId(EffectController.getInstance().getEffectForSaving(false));
        int i = ((BaseModule) this).mOrientation;
        if (-1 == i) {
            i = 0;
        }
        ParallelTaskDataParameter.Builder location2 = filterId.setOrientation(i).setJpegRotation(this.mJpegRotation).setShootRotation(this.mShootRotation).setShootOrientation(this.mShootOrientation).setLocation(location);
        if (CameraSettings.isTimeWaterMarkOpen()) {
            str = Util.getTimeWatermark();
        }
        parallelTaskData.fillParameter(location2.setTimeWaterMarkString(str).setFaceWaterMarkList(arrayList).setAgeGenderAndMagicMirrorWater(CameraSettings.isAgeGenderAndMagicMirrorWaterOpen()).setFrontCamera(isFrontCamera()).setBokehFrontCamera(isPictureUseDualFrontCamera()).setAlgorithmName(this.mAlgorithmName).setPictureInfo(getPictureInfo()).setSuffix(getSuffix()).setTiltShiftMode(getTiltShiftMode()).setSaveGroupshotPrimitive(CameraSettings.isSaveGroushotPrimitiveOn()).setDeviceWatermarkParam(getDeviceWaterMarkParam()).setJpegQuality(clampQuality).setPrefix(getPrefix()).setMoonMode(this.mIsMoonMode).setMiMovieOpen(CameraSettings.isCinematicAspectRatioEnabled(((BaseModule) this).mModuleIndex)).setAIWatermark(watermarkItem).build());
        parallelTaskData.setNeedThumbnail(!z && !this.mEnabledPreviewThumbnail);
        parallelTaskData.setCurrentModuleIndex(((BaseModule) this).mModuleIndex);
        CameraCapabilities cameraCapabilities = ((BaseModule) this).mCameraCapabilities;
        parallelTaskData.setAdaptiveSnapshotSize(cameraCapabilities != null && cameraCapabilities.isAdaptiveSnapshotSizeInSatModeSupported());
        parallelTaskData.setLiveShotTask(false);
        if (!(!z2 || (camera = ((BaseModule) this).mActivity) == null || (imageSaver = camera.getImageSaver()) == null)) {
            synchronized (this.mCircularMediaRecorderStateLock) {
                if (this.mCircularMediaRecorder != null) {
                    parallelTaskData.setLiveShotTask(true);
                    this.mCircularMediaRecorder.snapshot(((BaseModule) this).mOrientationCompensation, imageSaver, parallelTaskData, this.mFilterId);
                }
            }
        }
        Log.d(TAG, "onCaptureStart: isParallel = " + this.mEnableParallelSession + ", shotType = " + parallelTaskData.getParallelType() + ", isLiveShot = " + z2);
        if (this.mEnableParallelSession) {
            beginParallelProcess(parallelTaskData, true);
        }
        if (CameraSettings.isHandGestureOpen()) {
            Log.d(TAG, "send msg: reset hand gesture");
            ((BaseModule) this).mHandler.removeMessages(57);
            ((BaseModule) this).mHandler.sendEmptyMessageDelayed(57, 0);
        }
        return parallelTaskData;
    }

    @Override // com.android.camera.module.BaseModule, com.android.camera.module.Module
    public void onCreate(int i, int i2) {
        super.onCreate(i, i2);
        parseIntent();
        ((BaseModule) this).mHandler = new MainHandler(this, ((BaseModule) this).mActivity.getMainLooper());
        ((BaseModule) this).mActivity.getSensorStateManager().setSensorStateListener(this.mSensorStateListener);
        this.mIsGoogleLensAvailable = 163 == getModuleIndex() && !this.mIsImageCaptureIntent && isBackCamera() && CameraSettings.isAvailableGoogleLens();
        onCameraOpened();
    }

    @Override // com.android.camera.module.BaseModule, com.android.camera.module.Module
    public void onDestroy() {
        super.onDestroy();
        Handler handler = ((BaseModule) this).mHandler;
        if (handler != null) {
            handler.sendEmptyMessage(45);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopConfigProtocol
    public void onExtraMenuVisibilityChange(boolean z) {
        if (!z) {
            this.mCurrentAiScene = 0;
            this.mCurrentAsdScene = -1;
            this.isDetectedInHdr = false;
        }
    }

    @Override // com.android.camera2.Camera2Proxy.FaceDetectionCallback
    public void onFaceDetected(CameraHardwareFace[] cameraHardwareFaceArr, FaceAnalyzeInfo faceAnalyzeInfo) {
        FocusManager2 focusManager2;
        Camera camera;
        int i;
        if (isAlive() && ((BaseModule) this).mActivity.getCameraScreenNail().getFrameAvailableFlag() && cameraHardwareFaceArr != null) {
            if (b.hl()) {
                boolean z = cameraHardwareFaceArr.length > 0;
                if (z != this.mFaceDetected && isFrontCamera() && ((i = ((BaseModule) this).mModuleIndex) == 163 || i == 165 || i == 171)) {
                    ((BaseModule) this).mCamera2Device.resumePreview();
                }
                this.mFaceDetected = z;
            }
            this.mFaceInfo = faceAnalyzeInfo;
            if (!b.cm() || cameraHardwareFaceArr.length <= 0 || cameraHardwareFaceArr[0].faceType != 64206) {
                if (this.mIsGoogleLensAvailable && (camera = ((BaseModule) this).mActivity) != null) {
                    camera.runOnUiThread(new o(this, cameraHardwareFaceArr));
                }
                if (((BaseModule) this).mMainProtocol.setFaces(1, cameraHardwareFaceArr, getActiveArraySize(), getDeviceBasedZoomRatio())) {
                    if (this.mIsPortraitLightingOn) {
                        ((BaseModule) this).mMainProtocol.lightingDetectFace(cameraHardwareFaceArr, false);
                    }
                    if (!((BaseModule) this).mMainProtocol.isFaceExists(1) || !((BaseModule) this).mMainProtocol.isFocusViewVisible() || (focusManager2 = this.mFocusManager) == null || focusManager2.isFromTouch()) {
                        ((BaseModule) this).mHandler.removeMessages(56);
                    } else if (!((BaseModule) this).mHandler.hasMessages(56)) {
                        ((BaseModule) this).mHandler.sendEmptyMessage(56);
                    }
                }
            } else if (((BaseModule) this).mObjectTrackingStarted) {
                ((BaseModule) this).mMainProtocol.setFaces(3, cameraHardwareFaceArr, getActiveArraySize(), getDeviceBasedZoomRatio());
            }
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
        CameraCapabilities cameraCapabilities;
        if (isFrameAvailable() && !isDeparted()) {
            int focusTrigger = focusTask.getFocusTrigger();
            if (focusTrigger == 1) {
                Log.v(TAG, String.format(Locale.ENGLISH, "FocusTime=%1$dms focused=%2$b", Long.valueOf(focusTask.getElapsedTime()), Boolean.valueOf(focusTask.isSuccess())));
                if (!this.mFocusManager.isFocusingSnapOnFinish() && getCameraState() != 3) {
                    setCameraState(1);
                }
                this.mFocusManager.onFocusResult(focusTask);
                ((BaseModule) this).mActivity.getSensorStateManager().reset();
                if (focusTask.isSuccess() && this.m3ALocked) {
                    if (!DataRepository.dataItemFeature().pf() && isZoomRatioBetweenUltraAndWide() && (cameraCapabilities = ((BaseModule) this).mUltraCameraCapabilities) != null) {
                        boolean isAFRegionSupported = cameraCapabilities.isAFRegionSupported();
                        Log.d(TAG, "onFocusStateChanged: isUltraFocusAreaSupported = " + isAFRegionSupported);
                        if (!isAFRegionSupported) {
                            ((BaseModule) this).mCamera2Device.setFocusMode(0);
                            ((BaseModule) this).mCamera2Device.setFocusDistance(0.0f);
                            this.mUltraWideAELocked = true;
                        }
                    }
                    ((BaseModule) this).mCamera2Device.lockExposure(true);
                }
            } else if (focusTrigger == 2 || focusTrigger == 3) {
                String str = null;
                if (focusTask.isFocusing()) {
                    this.mAFEndLogTimes = 0;
                    str = "onAutoFocusMoving start";
                } else if (this.mAFEndLogTimes == 0) {
                    str = "onAutoFocusMoving end. result=" + focusTask.isSuccess();
                    this.mAFEndLogTimes++;
                }
                if (Util.sIsDumpLog && str != null) {
                    Log.v(TAG, str);
                }
                if (getCameraState() != 3 || focusTask.getFocusTrigger() == 3) {
                    if (!this.m3ALocked) {
                        this.mFocusManager.onFocusResult(focusTask);
                    }
                } else if (focusTask.isSuccess()) {
                    this.mFocusManager.onFocusResult(focusTask);
                }
            }
        }
    }

    public void onHanGestureSwitched(boolean z) {
        if (z) {
            PreviewDecodeManager.getInstance().init(((BaseModule) this).mBogusCameraId, 1);
            PreviewDecodeManager.getInstance().startDecode();
            return;
        }
        PreviewDecodeManager.getInstance().stopDecode(1);
    }

    @Override // com.android.camera2.Camera2Proxy.HdrCheckerCallback
    public void onHdrMotionDetectionResult(boolean z) {
        CameraCapabilities cameraCapabilities = ((BaseModule) this).mCameraCapabilities;
        if (cameraCapabilities != null && cameraCapabilities.isMotionDetectionSupported() && this.mMotionDetected != z) {
            this.mMotionDetected = z;
            updateHDRPreference();
        }
    }

    @Override // com.android.camera2.Camera2Proxy.HdrCheckerCallback
    public void onHdrSceneChanged(boolean z) {
        if (!((BaseModule) this).mPaused) {
            this.mIsInHDR = z;
            if (this.isDetectedInHdr == z) {
                boolean z2 = false;
                ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
                if (topAlert != null) {
                    z2 = topAlert.isHDRShowing();
                }
                if (z2 == this.isDetectedInHdr) {
                    return;
                }
            }
            if (triggerHDR(z)) {
                ModeProtocol.MagneticSensorDetect magneticSensorDetect = this.mMagneticSensorDetect;
                if (magneticSensorDetect == null || !magneticSensorDetect.isLockHDRChecker()) {
                    if (getCameraDevice().getCapabilities().getMiAlgoASDVersion() < 3.0f) {
                        updateHDRTip(z);
                    }
                    if (z) {
                        if (((BaseModule) this).mMutexModePicker.isNormal()) {
                            ((BaseModule) this).mMutexModePicker.setMutexMode(1);
                        }
                    } else if (((BaseModule) this).mMutexModePicker.isMorphoHdr()) {
                        ((BaseModule) this).mMutexModePicker.resetMutexMode();
                    }
                    this.isDetectedInHdr = z;
                    String str = TAG;
                    Log.d(str, "onHdrSceneChanged: " + z);
                }
            }
        }
    }

    @Override // com.android.camera.module.BaseModule, com.android.camera.module.Module
    public void onHostStopAndNotifyActionStop() {
        super.onHostStopAndNotifyActionStop();
        if (this.mMultiSnapStatus) {
            onBurstPictureTakenFinished(true);
        }
        FocusManager2 focusManager2 = this.mFocusManager;
        if (focusManager2 != null) {
            focusManager2.setAeAwbLock(false);
            this.mFocusManager.destroy();
        }
        if (handleSuperNightResultIfNeed()) {
            doLaterReleaseIfNeed();
        }
        handleSaveFinishIfNeed(null);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.BaseModule
    public boolean onInterceptZoomingEvent(float f2, float f3, int i) {
        ModeProtocol.MiAsdDetect miAsdDetect;
        if (f3 < 1.0f && (miAsdDetect = (ModeProtocol.MiAsdDetect) ModeCoordinatorImpl.getInstance().getAttachProtocol(235)) != null) {
            miAsdDetect.updateUltraWide(false, -1);
        }
        if (DataRepository.dataItemFeature().pf() && !this.mIsMoonMode) {
            if (this.m3ALocked) {
                unlockAEAF();
                FocusManager2 focusManager2 = this.mFocusManager;
                if (focusManager2 != null) {
                    focusManager2.cancelFocus();
                }
                ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
                if (bottomPopupTips != null) {
                    bottomPopupTips.directlyHideTips();
                }
            } else {
                Camera2Proxy camera2Proxy = ((BaseModule) this).mCamera2Device;
                if (!(camera2Proxy == null || 4 == camera2Proxy.getFocusMode())) {
                    Log.d(TAG, "onInterceptZoomingEvent: should cancel focus.");
                    FocusManager2 focusManager22 = this.mFocusManager;
                    if (focusManager22 != null) {
                        focusManager22.cancelFocus();
                    }
                }
            }
        }
        return super.onInterceptZoomingEvent(f2, f3, i);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0023, code lost:
        if (r6 != 88) goto L_0x0079;
     */
    @Override // com.android.camera.module.BaseModule, com.android.camera.module.Module
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        boolean z = false;
        if (!isFrameAvailable()) {
            return false;
        }
        if (!(i == 24 || i == 25)) {
            if (i == 27 || i == 66) {
                if (keyEvent.getRepeatCount() == 0) {
                    if (!Util.isFingerPrintKeyEvent(keyEvent)) {
                        performKeyClicked(40, getString(R.string.pref_camera_volumekey_function_entryvalue_shutter), keyEvent.getRepeatCount(), true);
                    } else if (CameraSettings.isFingerprintCaptureEnable()) {
                        performKeyClicked(30, getString(R.string.pref_camera_volumekey_function_entryvalue_shutter), keyEvent.getRepeatCount(), true);
                    }
                }
                return true;
            } else if (i == 80) {
                if (keyEvent.getRepeatCount() == 0) {
                    onShutterButtonFocus(true, 1);
                }
                return true;
            } else if (i != 87) {
            }
        }
        if (i == 24 || i == 88) {
            z = true;
        }
        if (handleVolumeKeyEvent(z, true, keyEvent.getRepeatCount(), keyEvent.getDevice().isExternal())) {
            return true;
        }
        return super.onKeyDown(i, keyEvent);
    }

    @Override // com.android.camera.module.BaseModule, com.android.camera.module.Module
    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        if (i == 4 && ((ModeProtocol.BackStack) ModeCoordinatorImpl.getInstance().getAttachProtocol(171)).handleBackStackFromKeyBack()) {
            return true;
        }
        return super.onKeyUp(i, keyEvent);
    }

    @Override // com.android.camera2.Camera2Proxy.LivePhotoResultCallback
    public void onLivePhotoResultCallback(LivePhotoResult livePhotoResult) {
        this.mLivePhotoQueue.offer(livePhotoResult);
    }

    @Override // com.android.camera.module.BaseModule, com.android.camera.module.Module
    public void onLongPress(float f2, float f3) {
        if (((BaseModule) this).mModuleIndex != 182) {
            int i = (int) f2;
            int i2 = (int) f3;
            if (isInTapableRect(i, i2)) {
                if (!this.mIsCurrentLensEnabled || !this.mIsGoogleLensAvailable || ((BaseModule) this).mActivity.startFromSecureKeyguard() || !CameraSettings.isAvailableLongPressGoogleLens()) {
                    onSingleTapUp(i, i2, true);
                    if (((BaseModule) this).m3ALockSupported && ((BaseModule) this).mCamera2Device.getFocusMode() != AutoFocus.convertToFocusMode("manual")) {
                        lockAEAF();
                    }
                    ((BaseModule) this).mMainProtocol.performHapticFeedback(0);
                } else if (DataRepository.dataItemGlobal().getString("pref_camera_long_press_viewfinder_key", null) == null) {
                    CameraStatUtils.trackGoogleLensPicker();
                    GoogleLensFragment.showOptions(((BaseModule) this).mActivity.getFragmentManager(), new f(this, f2, f3, i, i2));
                } else {
                    CameraStatUtils.trackGoogleLensTouchAndHold();
                    LensAgent.getInstance().onFocusChange(0, f2 / ((float) Util.sWindowWidth), f3 / ((float) Util.sWindowHeight));
                }
            }
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

    @Override // com.android.camera.module.BaseModule, com.android.camera.module.Module
    public void onNewUriArrived(Uri uri, String str) {
        if (uri == null) {
            handleSaveFinishIfNeed(str);
        }
    }

    @Override // com.android.camera.ui.ObjectView.ObjectViewListener
    public void onObjectStable() {
    }

    @Override // com.android.camera.module.BaseModule, com.android.camera.module.Module
    public void onOrientationChanged(int i, int i2, int i3) {
        if (!CameraSettings.isGradienterOn() || ((BaseModule) this).mActivity.getSensorStateManager().isDeviceLying()) {
            setOrientation(i, i2);
        }
    }

    @Override // com.android.camera.module.BaseModule, com.android.camera.module.Module
    public void onPause() {
        super.onPause();
        stopLiveShot(true);
        FocusManager2 focusManager2 = this.mFocusManager;
        if (focusManager2 != null) {
            focusManager2.removeMessages();
        }
        this.mWaitingSnapshot = false;
        unregisterSensor();
        tryRemoveCountDownMessage();
        ((BaseModule) this).mActivity.getSensorStateManager().reset();
        resetScreenOn();
        closeCamera();
        setAiSceneEffect(0);
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
        Log.d(TAG, "onPictureTakenFinished: succeed = " + z);
        if (z) {
            HashMap hashMap = new HashMap();
            hashMap.put(MistatsConstants.Manual.PARAM_3A_LOCKED, Boolean.valueOf(this.m3ALocked));
            trackGeneralInfo(hashMap, 1, false, this.mBeautyValues, this.mLocation != null, this.mCurrentAiScene);
            MistatsWrapper.PictureTakenParameter pictureTakenParameter = new MistatsWrapper.PictureTakenParameter();
            pictureTakenParameter.takenNum = 1;
            pictureTakenParameter.burst = false;
            pictureTakenParameter.location = this.mLocation != null;
            pictureTakenParameter.aiSceneName = getCurrentAiSceneName();
            pictureTakenParameter.isEnteringMoon = this.mEnteringMoonMode;
            pictureTakenParameter.isSelectMoonMode = this.mIsMoonMode;
            pictureTakenParameter.isSuperNightInCaptureMode = this.mShowSuperNightHint;
            pictureTakenParameter.beautyValues = this.mBeautyValues;
            trackPictureTaken(pictureTakenParameter);
            long currentTimeMillis = System.currentTimeMillis() - this.mCaptureStartTime;
            CameraStatUtils.trackTakePictureCost(currentTimeMillis, isFrontCamera(), ((BaseModule) this).mModuleIndex);
            ScenarioTrackUtil.trackCaptureTimeEnd();
            Log.d(TAG, "mCaptureStartTime(from onShutterButtonClick start to jpegCallback finished) = " + currentTimeMillis + d.H);
            if (this.mIsImageCaptureIntent) {
                if (this.mQuickCapture) {
                    doAttach();
                } else if (isAlive()) {
                    this.mKeepBitmapTexture = true;
                    showPostCaptureAlert();
                }
            } else if (this.mLongPressedAutoFocus) {
                this.mLongPressedAutoFocus = false;
                this.mFocusManager.cancelLongPressedAutoFocus();
            }
        }
        this.mReceivedJpegCallbackNum++;
        if (!isKeptBitmapTexture() && !this.mMultiSnapStatus && this.mBlockQuickShot && ((!CameraSettings.isGroupShotOn() || (CameraSettings.isGroupShotOn() && z)) && this.mFixedShot2ShotTime == -1)) {
            resetStatusToIdle();
        }
        ((BaseModule) this).mHandler.removeMessages(50);
        handleSuperNightResultIfNeed();
        handleSuperNightResultInCaptureMode();
        PreviewDecodeManager.getInstance().resetScanResult();
        doLaterReleaseIfNeed();
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
            Integer num = (Integer) captureResult.get(CaptureResult.SENSOR_SENSITIVITY);
            this.mIsISORight4HWMFNR = num != null && num.intValue() >= 800;
            if ((DataRepository.dataItemFeature().sc() || !isFrontCamera()) && !DataRepository.dataItemFeature().ze()) {
                this.mShouldDoMFNR = false;
            } else if (b.fl() || b.Zk()) {
                this.mShouldDoMFNR = true;
            } else {
                String str = TAG;
                Log.c(str, "onPreviewMetaDataUpdate: iso = " + num);
                this.mShouldDoMFNR = this.mIsISORight4HWMFNR;
            }
            if (shouldCheckSatFallbackState()) {
                checkSatFallback(captureResult);
            }
            FlowableEmitter<CaptureResult> flowableEmitter = this.mMetaDataFlowableEmitter;
            if (flowableEmitter != null) {
                flowableEmitter.onNext(captureResult);
            }
            if (this.mAiSceneFlowableEmitter != null && ((this.mAiSceneEnabled || this.mAIWatermarkEnable) && ((BaseModule) this).mCamera2Device != null)) {
                this.mAiSceneFlowableEmitter.onNext(captureResult);
            }
            if (this.mIsTheShutterTime) {
                this.mPreviewSuperNightExifInfo = CaptureResultParser.getSuperNightInfo(captureResult);
                this.mIsTheShutterTime = false;
            }
            if (shouldCheckLLS()) {
                checkLLS(captureResult);
            }
            if (this.mMagneticSensorDetect != null && ((BaseModule) this).mActivity.getSensorStateManager().isMagneticFieldUncalibratedEnable()) {
                this.mMagneticSensorDetect.updatePreview(captureResult);
            }
            this.mAECLux = CaptureResultParser.getAecLux(captureResult);
        }
    }

    @Override // com.android.camera.module.BaseModule, com.android.camera.module.Module
    public void onPreviewPixelsRead(byte[] bArr, int i, int i2) {
        animateCapture();
        playCameraSound(0);
        Bitmap createBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
        createBitmap.copyPixelsFromBuffer(ByteBuffer.wrap(bArr));
        boolean z = true;
        boolean z2 = isFrontCamera() && !isFrontMirror();
        synchronized (this.mCameraDeviceLock) {
            if (isAlive()) {
                if (isDeviceAlive()) {
                    if ((this.mEnableParallelSession || this.mEnableShot2Gallery) && !this.mIsImageCaptureIntent) {
                        Bitmap cropBitmap = Util.cropBitmap(createBitmap, this.mShootRotation, z2, (float) ((BaseModule) this).mOrientation, ((BaseModule) this).mModuleIndex == 165, CameraSettings.isCinematicAspectRatioEnabled(((BaseModule) this).mModuleIndex));
                        if (cropBitmap == null) {
                            Log.w(TAG, "onPreviewPixelsRead: bitmap is null!");
                            return;
                        }
                        byte[] bitmapData = Util.getBitmapData(cropBitmap, EncodingQuality.NORMAL.toInteger(false));
                        if (bitmapData == null) {
                            Log.w(TAG, "onPreviewPixelsRead: jpegData is null!");
                            return;
                        }
                        int pictureFormatSuitableForShot = getPictureFormatSuitableForShot(((BaseModule) this).mCamera2Device.getCameraConfigs().getShotType());
                        String str = TAG;
                        StringBuilder sb = new StringBuilder();
                        sb.append("onPreviewPixelsRead: isParallel = ");
                        sb.append(this.mEnableParallelSession);
                        sb.append(", shot2Gallery = ");
                        sb.append(this.mEnableShot2Gallery);
                        sb.append(", format = ");
                        sb.append(CompatibilityUtils.isHeicImageFormat(pictureFormatSuitableForShot) ? "HEIC" : "JPEG");
                        sb.append(", data = ");
                        sb.append(bitmapData);
                        Log.d(str, sb.toString());
                        ParallelTaskData parallelTaskData = new ParallelTaskData(((BaseModule) this).mActualCameraId, System.currentTimeMillis(), -1, ((BaseModule) this).mCamera2Device.getParallelShotSavePath());
                        if (!this.mEnableParallelSession) {
                            if (!this.mEnableShot2Gallery) {
                                z = false;
                            }
                        }
                        parallelTaskData.setNeedThumbnail(z);
                        parallelTaskData.fillJpegData(bitmapData, 0);
                        parallelTaskData.fillParameter(new ParallelTaskDataParameter.Builder(new Size(i, i2), new Size(i, i2), new Size(i, i2), pictureFormatSuitableForShot).setOrientation(((BaseModule) this).mOrientation).build());
                        ((BaseModule) this).mActivity.getImageSaver().onParallelProcessFinish(parallelTaskData, null, null);
                        return;
                    }
                    int i3 = this.mShootOrientation - ((BaseModule) this).mDisplayRotation;
                    if (isFrontCamera() && b.el() && i3 % 180 == 0) {
                        i3 = 0;
                    }
                    Thumbnail createThumbnail = Thumbnail.createThumbnail(null, createBitmap, i3, z2);
                    createThumbnail.startWaitingForUri();
                    ((BaseModule) this).mActivity.getThumbnailUpdater().setThumbnail(createThumbnail, true, true);
                    ((BaseModule) this).mCamera2Device.onPreviewThumbnailReceived(createThumbnail);
                    return;
                }
            }
            Log.d(TAG, "onPreviewPixelsRead: module is dead");
        }
    }

    @Override // com.android.camera2.Camera2Proxy.CameraPreviewCallback
    public void onPreviewSessionClosed(CameraCaptureSession cameraCaptureSession) {
        Log.d(TAG, "onPreviewSessionClosed: ");
        setCameraState(0);
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
        String str = TAG;
        Log.d(str, "onPreviewSessionSuccess: " + Thread.currentThread().getName() + " " + this);
        if (cameraCaptureSession == null) {
            String str2 = TAG;
            Log.d(str2, "onPreviewSessionSuccess null session." + Util.getCallers(1));
        } else if (!isAlive()) {
            String str3 = TAG;
            Log.d(str3, "onPreviewSessionSuccess module not alive." + Util.getCallers(1));
        } else {
            if (!isKeptBitmapTexture()) {
                ((BaseModule) this).mHandler.sendEmptyMessage(9);
            }
            if (this.mEnableParallelSession) {
                ParallelDataZipper.getInstance().getHandler().post(new Runnable() {
                    /* class com.android.camera.module.Camera2Module.AnonymousClass11 */

                    public void run() {
                        Camera2Module.this.configParallelSession();
                    }
                });
            }
            previewWhenSessionSuccess();
            if (((BaseModule) this).mActivity.getCameraIntentManager().checkCallerLegality() && !((BaseModule) this).mActivity.isActivityPaused()) {
                if (!((BaseModule) this).mActivity.getCameraIntentManager().isOpenOnly(((BaseModule) this).mActivity)) {
                    ((BaseModule) this).mActivity.getIntent().removeExtra(CameraIntentManager.CameraExtras.CAMERA_OPEN_ONLY);
                    if (!((BaseModule) this).mActivity.isIntentPhotoDone()) {
                        ((BaseModule) this).mHandler.sendEmptyMessageDelayed(52, 1000);
                        ((BaseModule) this).mActivity.setIntnetPhotoDone(true);
                        return;
                    }
                    return;
                }
                ((BaseModule) this).mActivity.getIntent().removeExtra(CameraIntentManager.CameraExtras.TIMER_DURATION_SECONDS);
            }
        }
    }

    @Override // com.android.camera.module.BaseModule
    public void onPreviewSizeChanged(int i, int i2) {
        FocusManager2 focusManager2 = this.mFocusManager;
        if (focusManager2 != null) {
            focusManager2.setPreviewSize(i, i2);
        }
    }

    @Override // com.android.camera.module.BaseModule, com.android.camera.module.Module
    public void onResume() {
        super.onResume();
        ((BaseModule) this).mHandler.removeMessages(50);
        if (!isSelectingCapturedResult()) {
            this.mKeepBitmapTexture = false;
            ((BaseModule) this).mActivity.getCameraScreenNail().releaseBitmapIfNeeded();
        }
        keepScreenOnAwhile();
    }

    @Override // com.android.camera.protocol.ModeProtocol.CameraAction
    public void onReviewCancelClicked() {
        this.mKeepBitmapTexture = false;
        if (isSelectingCapturedResult()) {
            ((BaseModule) this).mActivity.getCameraScreenNail().releaseBitmapIfNeeded();
            hidePostCaptureAlert();
            return;
        }
        ((BaseModule) this).mActivity.setResult(0, new Intent());
        ((BaseModule) this).mActivity.finish();
    }

    @Override // com.android.camera.protocol.ModeProtocol.CameraAction
    public void onReviewDoneClicked() {
        doAttach();
    }

    @Override // com.android.camera.module.BaseModule
    public void onShineChanged(int i) {
        if (i == 196) {
            updatePreferenceTrampoline(2);
            ((BaseModule) this).mMainProtocol.updateEffectViewVisible();
        } else if (i != 212 && i != 239) {
            throw new RuntimeException("unknown configItem changed");
        } else if (b.Dl()) {
            updatePreferenceInWorkThread(13, 34, 42);
        } else {
            updatePreferenceInWorkThread(13);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.CameraAction
    public void onShutterButtonClick(int i) {
        if (i == 100) {
            ((BaseModule) this).mActivity.onUserInteraction();
        }
        this.mIsTheShutterTime = true;
        int countDownTimes = getCountDownTimes(i);
        if (countDownTimes > 0) {
            startCount(countDownTimes, i);
            return;
        }
        MistatsWrapper.PictureTakenParameter pictureTakenParameter = new MistatsWrapper.PictureTakenParameter();
        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        if (topAlert != null) {
            pictureTakenParameter.isASDBacklitTip = topAlert.isShowBacklightSelector();
        }
        ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
        if (bottomPopupTips != null) {
            pictureTakenParameter.isASDPortraitTip = bottomPopupTips.containTips(R.string.recommend_portrait);
        }
        if (checkShutterCondition()) {
            if (isNeedFixedShotTime()) {
                this.mFixedShot2ShotTime = DataRepository.dataItemFeature().zb();
            } else {
                this.mFixedShot2ShotTime = -1;
            }
            if (this.mFixedShot2ShotTime != -1) {
                ((BaseModule) this).mCamera2Device.setFixShotTimeEnabled(true);
                if (this.mFixedShot2ShotTime > 0) {
                    ((BaseModule) this).mHandler.removeMessages(59);
                    ((BaseModule) this).mHandler.sendEmptyMessageDelayed(59, (long) this.mFixedShot2ShotTime);
                    String str = TAG;
                    Log.d(str, ":send MSG_FIXED_SHOT2SHOT_TIME_OUT" + this.mFixedShot2ShotTime);
                }
            } else {
                ((BaseModule) this).mCamera2Device.setFixShotTimeEnabled(false);
            }
            setTriggerMode(i);
            String str2 = TAG;
            Log.d(str2, "onShutterButtonClick " + getCameraState());
            if (CameraSettings.isDocumentModeOn(((BaseModule) this).mModuleIndex) || (((BaseModule) this).mModuleIndex == 182 && getCurrentIDCardPictureName().equals(Storage.ID_CARD_PICTURE_2))) {
                showOrHideLoadingProgress(true);
            }
            this.mFocusManager.prepareCapture(this.mNeedAutoFocus, 2);
            this.mFocusManager.doSnap();
            if (this.mFocusManager.isFocusingSnapOnFinish()) {
                enableCameraControls(false);
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.CameraAction
    public void onShutterButtonFocus(boolean z, int i) {
    }

    @Override // com.android.camera.protocol.ModeProtocol.CameraAction
    public boolean onShutterButtonLongClick() {
        if (isDoingAction()) {
            Log.d(TAG, "onShutterButtonLongClick: doing action");
            return false;
        } else if (this.mIsImageCaptureIntent) {
            return false;
        } else {
            if (((BaseModule) this).mIsSatFallback == 0 || !shouldCheckSatFallbackState()) {
                boolean z = b.Vu && isZoomRatioBetweenUltraAndWide();
                if (!CameraSettings.isBurstShootingEnable() || !ModuleManager.isCameraModule() || this.mIsImageCaptureIntent || CameraSettings.isGroupShotOn() || CameraSettings.isGradienterOn() || CameraSettings.isTiltShiftOn() || DataRepository.dataItemRunning().isSwitchOn("pref_camera_hand_night_key") || DataRepository.dataItemRunning().isSwitchOn("pref_camera_scenemode_setting_key") || CameraSettings.isPortraitModeBackOn() || !isBackCamera() || this.mMultiSnapStatus || ((BaseModule) this).mHandler.hasMessages(24) || this.mPendingMultiCapture || isUltraWideBackCamera() || z || CameraSettings.isUltraPixelOn() || isStandaloneMacroCamera() || CameraSettings.isAIWatermarkOn()) {
                    this.mLongPressedAutoFocus = true;
                    ((BaseModule) this).mMainProtocol.setFocusViewType(false);
                    unlockAEAF();
                    this.mFocusManager.requestAutoFocus();
                    ((BaseModule) this).mActivity.getScreenHint().updateHint();
                    return false;
                }
                if (b.Vl()) {
                    this.mUpdateImageTitle = true;
                }
                this.mPendingMultiCapture = true;
                this.mFocusManager.doMultiSnap(true);
                return true;
            }
            Log.d(TAG, "onShutterButtonLongClick: sat fallback");
            return false;
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.CameraAction
    public void onShutterButtonLongClickCancel(boolean z) {
        Log.d(TAG, "onShutterButtonLongClickCancel: start");
        this.mPendingMultiCapture = false;
        if (this.mMultiSnapStatus) {
            ((BaseModule) this).mHandler.sendEmptyMessageDelayed(49, 2000);
        }
        this.mMultiSnapStopRequest = true;
        if (!this.mLongPressedAutoFocus) {
            return;
        }
        if (z) {
            onShutterButtonClick(10);
            return;
        }
        this.mLongPressedAutoFocus = false;
        this.mFocusManager.cancelLongPressedAutoFocus();
    }

    @Override // com.android.camera.module.BaseModule, com.android.camera.module.Module
    public void onSingleTapUp(int i, int i2, boolean z) {
        ModeProtocol.BackStack backStack;
        Log.v(TAG, "onSingleTapUp mPaused: " + ((BaseModule) this).mPaused + "; mCamera2Device: " + ((BaseModule) this).mCamera2Device + "; isInCountDown: " + isInCountDown() + "; getCameraState: " + getCameraState() + "; mMultiSnapStatus: " + this.mMultiSnapStatus + "; Camera2Module: " + this);
        if (!((BaseModule) this).mPaused && ((BaseModule) this).mCamera2Device != null && !hasCameraException() && ((BaseModule) this).mCamera2Device.isSessionReady() && ((BaseModule) this).mCamera2Device.isPreviewReady() && isInTapableRect(i, i2) && getCameraState() != 3 && getCameraState() != 4 && getCameraState() != 0 && !isInCountDown() && !this.mMultiSnapStatus && ((BaseModule) this).mModuleIndex != 182) {
            if (this.mIsMoonMode) {
                ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
                ModeProtocol.MiBeautyProtocol miBeautyProtocol = (ModeProtocol.MiBeautyProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(194);
                boolean z2 = false;
                z2 = false;
                Object[] objArr = (miBeautyProtocol == null || !miBeautyProtocol.isBeautyPanelShow()) ? null : 1;
                if (topAlert != null && topAlert.isExtraMenuShowing()) {
                    z2 = true;
                }
                if (objArr == null && !z2) {
                    return;
                }
            }
            if (isFrameAvailable()) {
                if ((!isFrontCamera() || !((BaseModule) this).mActivity.isScreenSlideOff()) && (backStack = (ModeProtocol.BackStack) ModeCoordinatorImpl.getInstance().getAttachProtocol(171)) != null && !backStack.handleBackStackFromTapDown(i, i2)) {
                    tryRemoveCountDownMessage();
                    if ((((BaseModule) this).mFocusAreaSupported || ((BaseModule) this).mMeteringAreaSupported) && !((BaseModule) this).mMutexModePicker.isUbiFocus()) {
                        if (((BaseModule) this).mObjectTrackingStarted) {
                            stopObjectTracking(true);
                        }
                        ((BaseModule) this).mMainProtocol.setFocusViewType(true);
                        Point point = new Point(i, i2);
                        mapTapCoordinate(point);
                        unlockAEAF();
                        setCameraState(2);
                        this.mFocusManager.onSingleTapUp(point.x, point.y, z);
                        if (!((BaseModule) this).mFocusAreaSupported && ((BaseModule) this).mMeteringAreaSupported) {
                            ((BaseModule) this).mActivity.getSensorStateManager().reset();
                        }
                        CameraClickObservableImpl cameraClickObservableImpl = (CameraClickObservableImpl) ModeCoordinatorImpl.getInstance().getAttachProtocol(227);
                        ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
                        if (!z && cameraClickObservableImpl != null) {
                            cameraClickObservableImpl.subscribe(165);
                        }
                    }
                }
            }
        }
    }

    @Override // com.android.camera.module.BaseModule, com.android.camera.module.Module
    public void onStop() {
        super.onStop();
        Handler handler = ((BaseModule) this).mHandler;
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }

    @Override // com.android.camera2.Camera2Proxy.SuperNightCallback
    public void onSuperNightChanged(final boolean z) {
        if (z != CameraSettings.isSuperNightOn()) {
            CameraSettings.setSuperNightOn(z);
            if (z) {
                ((BaseModule) this).mCamera2Device.setSuperResolution(false);
            } else if (((BaseModule) this).mMutexModePicker.isSuperResolution()) {
                ((BaseModule) this).mCamera2Device.setSuperResolution(true);
            }
            ((BaseModule) this).mHandler.post(new Runnable() {
                /* class com.android.camera.module.Camera2Module.AnonymousClass6 */

                public void run() {
                    Camera2Module.this.updateSuperNightTip(z);
                }
            });
            ((BaseModule) this).mMainProtocol.setEvAdjustable(!z);
            updateHDRPreference();
            resumePreviewInWorkThread();
        }
    }

    @Override // com.android.camera.module.BaseModule, com.android.camera.module.Module
    public void onSurfaceTextureReleased() {
        Log.d(TAG, "onSurfaceTextureReleased: no further preview frame will be available");
    }

    @Override // com.android.camera.module.BaseModule, com.android.camera.module.Module
    public void onSurfaceTextureUpdated(DrawExtTexAttribute drawExtTexAttribute) {
        CircularMediaRecorder circularMediaRecorder = this.mCircularMediaRecorder;
        if (circularMediaRecorder != null) {
            circularMediaRecorder.onSurfaceTextureUpdated(drawExtTexAttribute);
        }
        Camera2Proxy camera2Proxy = ((BaseModule) this).mCamera2Device;
        if (camera2Proxy != null) {
            camera2Proxy.onPreviewComing();
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.CameraAction
    public void onThumbnailClicked(View view) {
        if (this.mWaitSaveFinish) {
            Log.d(TAG, "onThumbnailClicked: CannotGotoGallery...mWaitSaveFinish");
            return;
        }
        if (this.mEnableParallelSession || this.mEnableShot2Gallery) {
            if (isCannotGotoGallery()) {
                Log.d(TAG, "onThumbnailClicked: CannotGotoGallery...");
                return;
            }
        } else if (isDoingAction()) {
            Log.d(TAG, "onThumbnailClicked: DoingAction..");
            return;
        }
        if (((BaseModule) this).mActivity.getThumbnailUpdater().getThumbnail() != null) {
            ((BaseModule) this).mActivity.gotoGallery();
        }
    }

    public void onTiltShiftSwitched(boolean z) {
        if (z) {
            resetEvValue();
        }
        ((BaseModule) this).mMainProtocol.initEffectCropView();
        updatePreferenceTrampoline(2, 5);
        ((BaseModule) this).mMainProtocol.updateEffectViewVisible();
        ((BaseModule) this).mMainProtocol.setEvAdjustable(!z);
    }

    @Override // com.android.camera.module.BaseModule, com.android.camera.module.Module
    public void onUserInteraction() {
        super.onUserInteraction();
        if (!isDoingAction()) {
            keepScreenOnAwhile();
        }
    }

    @Override // com.android.camera.module.loader.camera2.FocusManager2.Listener
    public boolean onWaitingFocusFinished() {
        if (isBlockSnap() || !isAlive()) {
            return false;
        }
        if (((BaseModule) this).mIsSatFallback == 0 || !shouldCheckSatFallbackState()) {
            this.mWaitingSnapshot = false;
            startNormalCapture(getTriggerMode());
        } else {
            this.mWaitingSnapshot = true;
            Log.w(TAG, "capture check: sat fallback");
        }
        return true;
    }

    @Override // com.android.camera.module.BaseModule
    public void onZoomingActionEnd(int i) {
        String str = TAG;
        Log.d(str, "onZoomingActionEnd(): " + ZoomingAction.toString(i));
        ModeProtocol.DualController dualController = (ModeProtocol.DualController) ModeCoordinatorImpl.getInstance().getAttachProtocol(182);
        if (dualController != null && !dualController.isSlideVisible()) {
            if (i == 1 || i == 2) {
                dualController.setImmersiveModeEnabled(false);
            }
        }
    }

    @Override // com.android.camera.module.BaseModule
    public void onZoomingActionStart(int i) {
        ModeProtocol.BottomPopupTips bottomPopupTips;
        String str = TAG;
        Log.d(str, "onZoomingActionStart(): " + ZoomingAction.toString(i));
        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        if (topAlert != null && topAlert.isExtraMenuShowing()) {
            topAlert.hideExtraMenu();
        }
        if (!isZoomEnabled()) {
            if (CameraSettings.isUltraPixelOn() && !DataRepository.dataItemFeature().Hd() && (bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175)) != null) {
                bottomPopupTips.showTips(15, ComponentRunningUltraPixel.getNoSupportZoomTip(), 1);
            }
            Log.d(TAG, "onZoomingActionStart(): zoom is currently disallowed");
            return;
        }
        ModeProtocol.DualController dualController = (ModeProtocol.DualController) ModeCoordinatorImpl.getInstance().getAttachProtocol(182);
        if (dualController != null && (i == 1 || i == 2)) {
            dualController.setImmersiveModeEnabled(true);
        }
        ModeProtocol.CameraClickObservable cameraClickObservable = (ModeProtocol.CameraClickObservable) ModeCoordinatorImpl.getInstance().getAttachProtocol(227);
        if (cameraClickObservable != null) {
            cameraClickObservable.subscribe(168);
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.BaseModule
    public boolean onZoomingActionUpdate(float f2, int i) {
        if (this.mMagneticSensorDetect != null && ((BaseModule) this).mActivity.getSensorStateManager().isMagneticFieldUncalibratedEnable()) {
            this.mMagneticSensorDetect.resetMagneticInfo();
        }
        return super.onZoomingActionUpdate(f2, i);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.BaseModule
    public void openSettingActivity() {
        Intent intent = new Intent();
        intent.setClass(((BaseModule) this).mActivity, CameraPreferenceActivity.class);
        intent.putExtra(BasePreferenceActivity.FROM_WHERE, ((BaseModule) this).mModuleIndex);
        intent.putExtra(CameraPreferenceActivity.IS_IMAGE_CAPTURE_INTENT, this.mIsImageCaptureIntent);
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
        Log.v(TAG, "pausePreview");
        Camera2Proxy camera2Proxy = ((BaseModule) this).mCamera2Device;
        if (camera2Proxy != null) {
            camera2Proxy.pausePreview();
        }
        setCameraState(0);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.BaseModule
    public void performKeyClicked(int i, String str, int i2, boolean z) {
        if (!((BaseModule) this).mPaused && getCameraState() != 0) {
            if (!isDoingAction()) {
                restoreBottom();
            }
            if (i2 == 0) {
                if (z) {
                    onShutterButtonFocus(true, 1);
                    if (str.equals(getString(R.string.pref_camera_volumekey_function_entryvalue_timer))) {
                        startCount(2, 20);
                    } else {
                        onShutterButtonClick(i);
                    }
                } else {
                    onShutterButtonFocus(false, 0);
                    if (this.mVolumeLongPress) {
                        this.mVolumeLongPress = false;
                        onShutterButtonLongClickCancel(false);
                    }
                }
            } else if (z && !this.mVolumeLongPress) {
                this.mVolumeLongPress = onShutterButtonLongClick();
                if (!this.mVolumeLongPress && this.mLongPressedAutoFocus) {
                    this.mVolumeLongPress = true;
                }
            }
        }
    }

    @Override // com.android.camera.module.loader.camera2.FocusManager2.Listener
    public void playFocusSound(int i) {
        playCameraSound(i);
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopConfigProtocol
    public void reShowMoon() {
        if (this.mEnteringMoonMode) {
            showMoonMode(this.mIsMoonMode);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.BaseProtocol, com.android.camera.module.BaseModule, com.android.camera.module.Module
    public void registerProtocol() {
        Camera camera;
        ModeProtocol.CameraClickObservable cameraClickObservable;
        super.registerProtocol();
        ModeCoordinatorImpl.getInstance().attachProtocol(161, this);
        ModeCoordinatorImpl.getInstance().attachProtocol(169, this);
        ModeCoordinatorImpl.getInstance().attachProtocol(193, this);
        ModeCoordinatorImpl.getInstance().attachProtocol(195, this);
        getActivity().getImplFactory().initAdditional(getActivity(), 164, 174, 234, 227, 235, 254);
        boolean z = false;
        if (DataRepository.dataItemFeature().Je()) {
            getActivity().getImplFactory().initAdditional(getActivity(), 2576);
            this.mMagneticSensorDetect = (ModeProtocol.MagneticSensorDetect) ModeCoordinatorImpl.getInstance().getAttachProtocol(2576);
        }
        if (getModuleIndex() == 163 && (cameraClickObservable = (ModeProtocol.CameraClickObservable) ModeCoordinatorImpl.getInstance().getAttachProtocol(227)) != null) {
            cameraClickObservable.addObservable(new int[]{R.string.recommend_portrait, R.string.recommend_super_night, R.string.lens_dirty_detected_title_back, R.string.recommend_macro_mode, R.string.ultra_wide_recommend_tip_hint_sat}, this.mCameraClickObserverAction, 161, 162, 166, 163, 164, 165, 167, 168);
        }
        if (getModuleIndex() == 173) {
            getActivity().getImplFactory().initAdditional(getActivity(), 212);
        }
        String str = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("registerProtocol: mIsGoogleLensAvailable = ");
        sb.append(this.mIsGoogleLensAvailable);
        sb.append(", activity is null ? ");
        if (((BaseModule) this).mActivity == null) {
            z = true;
        }
        sb.append(z);
        Log.d(str, sb.toString());
        if (this.mIsGoogleLensAvailable && (camera = ((BaseModule) this).mActivity) != null) {
            camera.runOnUiThread(new b(this));
        }
        this.mIsMacroModeEnable = CameraSettings.isMacroModeEnabled(((BaseModule) this).mModuleIndex);
    }

    @Override // com.android.camera.module.BaseModule
    public void resetAiSceneInDocumentModeOn() {
        int i;
        if (this.mAiSceneEnabled && !this.isResetFromMutex && (i = this.mCurrentAiScene) != 0) {
            if (i == -1 || i == 10 || i == 35) {
                ((BaseModule) this).mHandler.post(new m(this));
            }
        }
    }

    /* access modifiers changed from: protected */
    public void resetMetaDataManager() {
        CameraSettings.isSupportedMetadata();
    }

    /* access modifiers changed from: protected */
    public void resetStatusToIdle() {
        Log.d(TAG, "reset Status to Idle");
        setCameraState(1);
        enableCameraControls(true);
        this.mBlockQuickShot = false;
        this.mFixedShot2ShotTime = -1;
    }

    @Override // com.android.camera.module.Module
    public void resumePreview() {
        Log.v(TAG, "resumePreview");
        previewWhenSessionSuccess();
        this.mBlockQuickShot = !CameraSettings.isCameraQuickShotEnable();
    }

    public boolean scanQRCodeEnabled() {
        int i;
        return CameraSettings.isScanQRCode(((BaseModule) this).mActivity) && ((i = ((BaseModule) this).mModuleIndex) == 163 || i == 165) && !this.mIsImageCaptureIntent && CameraSettings.isBackCamera() && !this.mMultiSnapStatus && !CameraSettings.isPortraitModeBackOn() && ((!DataRepository.dataItemFeature().Ic() || !CameraSettings.isUltraPixelOn()) && !CameraSettings.isUltraWideConfigOpen(((BaseModule) this).mModuleIndex) && !CameraSettings.isMacroModeEnabled(((BaseModule) this).mModuleIndex));
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.BaseModule
    public void sendOpenFailMessage() {
        ((BaseModule) this).mHandler.sendEmptyMessage(10);
    }

    public void setAsdScenes(MarshalQueryableASDScene.ASDScene[] aSDSceneArr) {
        this.mAsdScenes = aSDSceneArr;
    }

    @Override // com.android.camera.module.BaseModule, com.android.camera.module.Module
    public void setFrameAvailable(boolean z) {
        Camera2Proxy camera2Proxy;
        super.setFrameAvailable(z);
        if (z && ((BaseModule) this).mActivity != null && CameraSettings.isCameraSoundOpen()) {
            ((BaseModule) this).mActivity.loadCameraSound(1);
            ((BaseModule) this).mActivity.loadCameraSound(0);
            ((BaseModule) this).mActivity.loadCameraSound(4);
            ((BaseModule) this).mActivity.loadCameraSound(5);
            ((BaseModule) this).mActivity.loadCameraSound(7);
        }
        if (z && (camera2Proxy = ((BaseModule) this).mCamera2Device) != null) {
            camera2Proxy.releaseFakeSurfaceIfNeed();
        }
        if (z && isBackCamera()) {
            int i = ((BaseModule) this).mModuleIndex;
            if ((i == 165 || i == 163) && CameraSettings.isCameraLyingHintOn()) {
                ((BaseModule) this).mHandler.post(new e(this));
            }
        }
    }

    public void setIsUltraWideConflict(boolean z) {
        this.mIsUltraWideConflict = z;
    }

    @Override // com.android.camera.module.loader.camera2.FocusManager2.Listener
    public boolean shouldCaptureDirectly() {
        Camera2Proxy camera2Proxy;
        return ((BaseModule) this).mUseLegacyFlashMode && (camera2Proxy = ((BaseModule) this).mCamera2Device) != null && camera2Proxy.isNeedFlashOn();
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.BaseModule
    public boolean shouldCheckSatFallbackState() {
        return isIn3OrMoreSatMode() && DataRepository.dataItemFeature().shouldCheckSatFallbackState();
    }

    @Override // com.android.camera.module.BaseModule, com.android.camera.module.Module
    public boolean shouldReleaseLater() {
        Handler handler;
        Handler handler2;
        return !this.mIsImageCaptureIntent && (getCameraState() == 3 || (this.mEnableShot2Gallery && (handler2 = ((BaseModule) this).mHandler) != null && handler2.hasMessages(50))) && (((handler = ((BaseModule) this).mHandler) == null || (!handler.hasMessages(48) && !((BaseModule) this).mHandler.hasMessages(49))) && !this.mFocusManager.isFocusing() && (((BaseModule) this).mModuleIndex != 167 || getManualValue(CameraSettings.KEY_QC_EXPOSURETIME, getString(R.string.pref_camera_exposuretime_default)).equals(getString(R.string.pref_camera_exposuretime_default))));
    }

    public void showBacklightTip() {
        if (!CameraSettings.isMacroModeEnabled(((BaseModule) this).mModuleIndex)) {
            ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
            ((ModeProtocol.ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164)).closeMutexElement("e", 193);
            topAlert.alertHDR(8, false, false);
            topAlert.alertAiSceneSelector(0);
            applyBacklightEffect();
            resumePreviewInWorkThread();
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.CameraModuleSpecial, com.android.camera.module.BaseModule
    public void showOrHideChip(boolean z) {
        if (this.mIsGoogleLensAvailable) {
            ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
            boolean z2 = true;
            if (z) {
                boolean z3 = bottomPopupTips != null && bottomPopupTips.isTipShowing();
                ModeProtocol.DualController dualController = (ModeProtocol.DualController) ModeCoordinatorImpl.getInstance().getAttachProtocol(182);
                boolean z4 = dualController != null && dualController.isSlideVisible();
                ModeProtocol.MakeupProtocol makeupProtocol = (ModeProtocol.MakeupProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(180);
                boolean z5 = makeupProtocol != null && makeupProtocol.isSeekBarVisible();
                ModeProtocol.MiBeautyProtocol miBeautyProtocol = (ModeProtocol.MiBeautyProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(194);
                boolean z6 = miBeautyProtocol != null && miBeautyProtocol.isBeautyPanelShow();
                boolean z7 = !this.mIsAiConflict && !this.mIsFaceConflict && !this.mIsUltraWideConflict && !this.mIsMoonMode && !z3 && !z4 && !z5 && !z6;
                Log.d(TAG, "pre showOrHideChip: isTipsShow = " + z3 + ", isZoomSlideVisible = " + z4 + ", isSeekBarVisible = " + z5 + ", isMakeupVisible = " + z6 + ", mIsAiConflict = " + this.mIsAiConflict + ", mIsUltraWideConflict = " + this.mIsUltraWideConflict + ", mIsMoonMode = " + this.mIsMoonMode + ", mIsFaceConflict = " + this.mIsFaceConflict + ", final isShow = " + z7 + ", mIsCurrentLensEnabled = " + this.mIsCurrentLensEnabled);
                z = z7;
            }
            if (this.mIsCurrentLensEnabled != z) {
                this.mIsCurrentLensEnabled = z;
                Log.d(TAG, "showOrHideChip: show = " + z + ", isChipsEnabled = " + CameraSettings.isAvailableChipsGoogleLens());
                LensAgent instance = LensAgent.getInstance();
                if (!z || !CameraSettings.isAvailableChipsGoogleLens()) {
                    z2 = false;
                }
                instance.showOrHideChip(z2);
                if (bottomPopupTips != null) {
                    bottomPopupTips.reConfigQrCodeTip();
                }
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.CameraModuleSpecial, com.android.camera.module.BaseModule
    public void showQRCodeResult() {
        if (!((BaseModule) this).mPaused) {
            String scanResult = PreviewDecodeManager.getInstance().getScanResult();
            if (scanResult == null || scanResult.isEmpty()) {
                Log.e(TAG, "showQRCodeResult: get a null result!");
                return;
            }
            Camera camera = ((BaseModule) this).mActivity;
            camera.dismissKeyguard();
            Intent intent = new Intent(Util.QRCODE_RECEIVER_ACTION);
            intent.addFlags(32);
            intent.setPackage("com.xiaomi.scanner");
            intent.putExtra("result", scanResult);
            camera.sendBroadcast(intent);
            camera.setJumpFlag(3);
            PreviewDecodeManager.getInstance().resetScanResult();
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopConfigProtocol
    public void startAiLens() {
        ((BaseModule) this).mHandler.postDelayed(new g(this), 300);
    }

    @Override // com.android.camera.module.loader.camera2.FocusManager2.Listener
    public void startFaceDetection() {
        Camera camera;
        if (this.mFaceDetectionEnabled && !this.mFaceDetectionStarted && (camera = ((BaseModule) this).mActivity) != null && !camera.isActivityPaused() && isAlive() && ((BaseModule) this).mMaxFaceCount > 0 && ((BaseModule) this).mCamera2Device != null) {
            this.mFaceDetectionStarted = true;
            ((BaseModule) this).mMainProtocol.setActiveIndicator(1);
            ((BaseModule) this).mCamera2Device.startFaceDetection();
            updateFaceView(true, true);
        }
    }

    @Override // com.android.camera.module.loader.camera2.FocusManager2.Listener
    public void startFocus() {
        if (!isDeviceAlive() || !isFrameAvailable()) {
            return;
        }
        if (((BaseModule) this).mFocusOrAELockSupported) {
            ((BaseModule) this).mCamera2Device.startFocus(FocusTask.create(1), ((BaseModule) this).mModuleIndex);
        } else {
            ((BaseModule) this).mCamera2Device.resumePreview();
        }
    }

    public void startLiveShot() {
        synchronized (this.mCircularMediaRecorderStateLock) {
            try {
                if (this.mCircularMediaRecorder == null) {
                    this.mCircularMediaRecorder = new CircularMediaRecorder(this.mVideoSize.width, this.mVideoSize.height, getActivity().getGLView().getEGLContext14(), this.mIsMicrophoneEnabled, this.mLivePhotoQueue);
                }
                this.mLiveShotEnabled = true;
                this.mCircularMediaRecorder.setOrientationHint(((BaseModule) this).mOrientationCompensation);
                this.mCircularMediaRecorder.start();
            } catch (Exception e2) {
                String str = TAG;
                Log.w(str, "startLiveShot: " + e2.getMessage());
                return;
            }
        }
        ((BaseModule) this).mActivity.getSensorStateManager().setGyroscopeEnabled(true);
    }

    @Override // com.android.camera.ui.ObjectView.ObjectViewListener
    public void startObjectTracking() {
    }

    @Override // com.android.camera.module.Module
    public void startPreview() {
        Camera2Proxy camera2Proxy = ((BaseModule) this).mCamera2Device;
        if (camera2Proxy != null) {
            camera2Proxy.setActivityHashCode(((BaseModule) this).mActivity.hashCode());
            ((BaseModule) this).mCamera2Device.setFocusCallback(this);
            ((BaseModule) this).mCamera2Device.setMetaDataCallback(this);
            ((BaseModule) this).mCamera2Device.setScreenLightCallback(this);
            ((BaseModule) this).mCamera2Device.setErrorCallback(((BaseModule) this).mErrorCallback);
            Log.d(TAG, "startPreview: set PictureSize with " + ((BaseModule) this).mPictureSize);
            ((BaseModule) this).mCamera2Device.setPictureSize(((BaseModule) this).mPictureSize);
            ((BaseModule) this).mCamera2Device.setPictureFormat(((BaseModule) this).mOutputPictureFormat);
            String str = TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("startPreview: set PictureFormat to ");
            sb.append(CompatibilityUtils.isHeicImageFormat(((BaseModule) this).mOutputPictureFormat) ? "HEIC" : "JPEG");
            Log.d(str, sb.toString());
            if (isSensorRawStreamRequired() && ((BaseModule) this).mSensorRawImageSize != null) {
                Log.d(TAG, "startPreview: set SensorRawImageSize with " + ((BaseModule) this).mSensorRawImageSize);
                ((BaseModule) this).mCamera2Device.setSensorRawImageSize(((BaseModule) this).mSensorRawImageSize, isCurrentRawDomainBasedSuperNight());
            }
            if (this.mEnableParallelSession && isPortraitMode()) {
                Log.d(TAG, "startPreview: set SubPictureSize with " + ((BaseModule) this).mSubPictureSize);
                ((BaseModule) this).mCamera2Device.setSubPictureSize(((BaseModule) this).mSubPictureSize);
            }
            boolean isEnableQcfaForAlgoUp = isEnableQcfaForAlgoUp();
            Log.d(TAG, "[QCFA] startPreview: set qcfa enable " + isEnableQcfaForAlgoUp);
            ((BaseModule) this).mCamera2Device.setQcfaEnable(isEnableQcfaForAlgoUp);
            if (isEnableQcfaForAlgoUp) {
                Log.d(TAG, "startPreview: set binning picture size to " + ((BaseModule) this).mBinningPictureSize);
                ((BaseModule) this).mCamera2Device.setBinningPictureSize(((BaseModule) this).mBinningPictureSize);
            }
            boolean scanQRCodeEnabled = scanQRCodeEnabled();
            boolean supportHandGesture = DataRepository.dataItemRunning().supportHandGesture();
            boolean z = true;
            boolean z2 = this.mIsGoogleLensAvailable || scanQRCodeEnabled || supportHandGesture;
            if (this.mIsGoogleLensAvailable) {
                PreviewDecodeManager.getInstance().init(((BaseModule) this).mActualCameraId, 2);
            }
            if (scanQRCodeEnabled) {
                PreviewDecodeManager.getInstance().init(((BaseModule) this).mBogusCameraId, 0);
            }
            if (supportHandGesture) {
                PreviewDecodeManager.getInstance().init(((BaseModule) this).mBogusCameraId, 1);
            }
            SurfaceTexture surfaceTexture = ((BaseModule) this).mActivity.getCameraScreenNail().getSurfaceTexture();
            Log.d(TAG, "startPreview: surfaceTexture = " + surfaceTexture);
            if (surfaceTexture != null) {
                ((BaseModule) this).mSurfaceCreatedTimestamp = ((BaseModule) this).mActivity.getCameraScreenNail().getSurfaceCreatedTimestamp();
            }
            Surface surface = surfaceTexture != null ? new Surface(surfaceTexture) : null;
            if (!isSensorRawStreamRequired() || ((BaseModule) this).mSensorRawImageSize == null) {
                z = false;
            }
            this.mConfigRawStream = z;
            int operatingMode = getOperatingMode();
            if (CameraSettings.isMacro2Sat() && 36866 == operatingMode && DataRepository.dataItemFeature().yc()) {
                int lensIndex = CameraSettings.getLensIndex();
                operatingMode |= lensIndex << 8;
                Log.d(TAG, "getOperatingMode = " + operatingMode);
                Log.d(TAG, "Index = " + lensIndex);
            }
            if (CameraSettings.isMacro2Sat()) {
                CameraSettings.setMacro2Sat(false);
            }
            ((BaseModule) this).mCamera2Device.startPreviewSession(surface, z2, this.mConfigRawStream, isCurrentRawDomainBasedSuperNight(), operatingMode, this.mEnableParallelSession, this);
        }
        LocalParallelService.LocalBinder localBinder = AlgoConnector.getInstance().getLocalBinder();
        if (localBinder != null && CameraSettings.isPictureFlawCheckOn()) {
            localBinder.setOnSessionStatusCallBackListener(this.mSessionStatusCallbackListener);
        }
        ((BaseModule) this).mCamera2Device.setMagneticDetectedCallback(this);
    }

    @Override // com.android.camera2.Camera2Proxy.ScreenLightCallback
    public void startScreenLight(final int i, final int i2) {
        if (!((BaseModule) this).mPaused) {
            ((BaseModule) this).mHandler.post(new Runnable() {
                /* class com.android.camera.module.Camera2Module.AnonymousClass20 */

                public void run() {
                    ModeProtocol.FullScreenProtocol fullScreenProtocol = (ModeProtocol.FullScreenProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(196);
                    if (fullScreenProtocol != null) {
                        fullScreenProtocol.setScreenLightColor(i);
                        if (fullScreenProtocol.showScreenLight()) {
                            Camera2Module camera2Module = Camera2Module.this;
                            if (((BaseModule) camera2Module).mActivity != null) {
                                ((BaseModule) camera2Module).mCamera2Device.setAELock(true);
                                ((BaseModule) Camera2Module.this).mActivity.setWindowBrightness(i2);
                            }
                        }
                    }
                }
            });
        }
    }

    @Override // com.android.camera.module.loader.camera2.FocusManager2.Listener
    public void stopFaceDetection(boolean z) {
        if (this.mFaceDetectionEnabled && this.mFaceDetectionStarted) {
            if (!b.isMTKPlatform() || !(getCameraState() == 3 || getCameraState() == 0)) {
                ((BaseModule) this).mCamera2Device.stopFaceDetection();
            }
            this.mFaceDetectionStarted = false;
            ((BaseModule) this).mMainProtocol.setActiveIndicator(2);
            updateFaceView(false, z);
        }
    }

    public void stopLiveShot(boolean z) {
        synchronized (this.mCircularMediaRecorderStateLock) {
            if (this.mCircularMediaRecorder != null) {
                this.mCircularMediaRecorder.stop();
                if (z) {
                    this.mCircularMediaRecorder.release();
                    this.mCircularMediaRecorder = null;
                }
            }
            this.mLiveShotEnabled = false;
        }
        ((BaseModule) this).mActivity.getSensorStateManager().setGyroscopeEnabled(false);
        this.mLivePhotoQueue.clear();
    }

    @Override // com.android.camera.module.loader.camera2.FocusManager2.Listener, com.android.camera.ui.ObjectView.ObjectViewListener
    public void stopObjectTracking(boolean z) {
    }

    @Override // com.android.camera2.Camera2Proxy.ScreenLightCallback
    public void stopScreenLight() {
        ((BaseModule) this).mHandler.post(new Runnable() {
            /* class com.android.camera.module.Camera2Module.AnonymousClass21 */

            public void run() {
                ((BaseModule) Camera2Module.this).mCamera2Device.setAELock(false);
                Camera camera = ((BaseModule) Camera2Module.this).mActivity;
                if (camera != null) {
                    camera.restoreWindowBrightness();
                }
                ModeProtocol.FullScreenProtocol fullScreenProtocol = (ModeProtocol.FullScreenProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(196);
                String access$400 = Camera2Module.TAG;
                Log.d(access$400, "stopScreenLight: protocol = " + fullScreenProtocol + ", mHandler = " + ((BaseModule) Camera2Module.this).mHandler);
                if (fullScreenProtocol != null) {
                    fullScreenProtocol.hideScreenLight();
                }
            }
        });
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.BaseModule
    public void trackModeCustomInfo(Map map, boolean z, BeautyValues beautyValues, int i) {
        if (map == null) {
            map = new HashMap();
        }
        int i2 = ((BaseModule) this).mModuleIndex;
        if (i2 == 167) {
            trackManualInfo(i);
        } else if (i2 == 163 || i2 == 165) {
            CameraStatUtils.trackLyingDirectPictureTaken(map, this.mIsShowLyingDirectHintStatus);
            trackCaptureModuleInfo(map, i, z);
            trackBeautyInfo(i, isFrontCamera(), beautyValues);
        }
    }

    @Override // com.android.camera.module.BaseModule
    public void tryRemoveCountDownMessage() {
        Disposable disposable = this.mCountdownDisposable;
        if (disposable != null && !disposable.isDisposed()) {
            this.mCountdownDisposable.dispose();
            this.mCountdownDisposable = null;
            ((BaseModule) this).mHandler.post(new Runnable() {
                /* class com.android.camera.module.Camera2Module.AnonymousClass10 */

                public void run() {
                    Log.d(Camera2Module.TAG, "run: hide delay number in main thread");
                    ((BaseModule) Camera2Module.this).mMainProtocol.hideDelayNumber();
                }
            });
        }
    }

    public /* synthetic */ void u(boolean z) {
        String str = TAG;
        Log.d(str, "onCaptureCompleted and enable shot = " + z);
        if (DataRepository.dataItemFeature().Kd()) {
            if (!isKeptBitmapTexture() && !this.mMultiSnapStatus && z) {
                setCameraState(1);
                enableCameraControls(true);
            }
            Camera2Proxy camera2Proxy = ((BaseModule) this).mCamera2Device;
            if (camera2Proxy != null) {
                camera2Proxy.unRegisterCaptureCallback();
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.BaseProtocol, com.android.camera.module.BaseModule, com.android.camera.module.Module
    public void unRegisterProtocol() {
        Camera camera;
        super.unRegisterProtocol();
        String str = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("unRegisterProtocol: mIsGoogleLensAvailable = ");
        sb.append(this.mIsGoogleLensAvailable);
        sb.append(", activity is null ? ");
        sb.append(((BaseModule) this).mActivity == null);
        Log.d(str, sb.toString());
        if (this.mIsGoogleLensAvailable && (camera = ((BaseModule) this).mActivity) != null) {
            camera.runOnUiThread(new l(this));
        }
        ModeCoordinatorImpl.getInstance().detachProtocol(161, this);
        ModeCoordinatorImpl.getInstance().detachProtocol(169, this);
        ModeCoordinatorImpl.getInstance().detachProtocol(193, this);
        ModeCoordinatorImpl.getInstance().detachProtocol(195, this);
        getActivity().getImplFactory().detachAdditional();
    }

    public void updateBacklight() {
        if (!isDoingAction() && isAlive()) {
            this.isSilhouette = false;
            applyBacklightEffect();
            resumePreviewInWorkThread();
        }
    }

    /* access modifiers changed from: protected */
    public void updateFaceView(boolean z, boolean z2) {
        if (((BaseModule) this).mHandler.hasMessages(35)) {
            ((BaseModule) this).mHandler.removeMessages(35);
        }
        ((BaseModule) this).mHandler.obtainMessage(35, z ? 1 : 0, z2 ? 1 : 0).sendToTarget();
    }

    @Override // com.android.camera.module.BaseModule
    public void updateFlashPreference() {
        String componentValue = DataRepository.dataItemConfig().getComponentFlash().getComponentValue(((BaseModule) this).mModuleIndex);
        String requestFlashMode = getRequestFlashMode();
        if (Util.parseInt(requestFlashMode, 0) != 0) {
            resetAiSceneInHdrOrFlashOn();
        }
        setFlashMode(requestFlashMode);
        if (!TextUtils.equals(componentValue, this.mLastFlashMode) && (Util.parseInt(componentValue, 0) == 103 || Util.parseInt(componentValue, 0) == 0)) {
            resetAsdSceneInHdrOrFlashChange();
        }
        this.mLastFlashMode = componentValue;
        stopObjectTracking(true);
        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        if (topAlert == null) {
            return;
        }
        if (requestFlashMode.equals(ComponentConfigFlash.FLASH_VALUE_MANUAL_OFF)) {
            topAlert.disableMenuItem(false, 193);
            return;
        }
        topAlert.enableMenuItem(false, 193);
    }

    @Override // com.android.camera.module.BaseModule
    public void updateHDRPreference() {
        ComponentConfigHdr componentHdr = DataRepository.dataItemConfig().getComponentHdr();
        if (!componentHdr.isEmpty()) {
            String componentValue = componentHdr.getComponentValue(((BaseModule) this).mModuleIndex);
            if ((getZoomRatio() != 1.0f || this.mMotionDetected) && ((BaseModule) this).mMutexModePicker.isHdr() && "auto".equals(componentValue)) {
                onHdrSceneChanged(false);
            }
            boolean isHdrOnWithChecker = componentHdr.isHdrOnWithChecker(componentValue);
            if (this.mIsMoonMode || this.mMotionDetected) {
                updateHDR("off");
            } else if (isHdrOnWithChecker) {
                updateHDR("auto");
            } else {
                updateHDR(componentValue);
            }
            if ((!"off".equals(componentValue) || this.mAiSceneEnabled) && ((getZoomRatio() <= 1.0f || "normal".equals(componentValue)) && !this.mIsMoonMode && ((!DataRepository.dataItemFeature().Ac() || !isStandaloneMacroCamera() || !"auto".equals(componentValue)) && !CameraSettings.isSuperNightOn()))) {
                resetAiSceneInHdrOrFlashOn();
                resetAsdSceneInHdrOrFlashChange();
                if (isHdrOnWithChecker || "auto".equals(componentValue)) {
                    this.mHdrCheckEnabled = true;
                    if (DataRepository.dataItemFeature().Je()) {
                        ((BaseModule) this).mActivity.getSensorStateManager().setMagneticFieldUncalibratedEnable(true);
                    }
                } else {
                    this.mHdrCheckEnabled = false;
                    if (DataRepository.dataItemFeature().Je()) {
                        ((BaseModule) this).mActivity.getSensorStateManager().setMagneticFieldUncalibratedEnable(false);
                    }
                }
                ((BaseModule) this).mCamera2Device.setHDRCheckerEnable(true);
            } else {
                ((BaseModule) this).mCamera2Device.setHDRCheckerEnable(false);
                this.mHdrCheckEnabled = false;
                if (DataRepository.dataItemFeature().Je()) {
                    ((BaseModule) this).mActivity.getSensorStateManager().setMagneticFieldUncalibratedEnable(false);
                }
            }
            ((BaseModule) this).mCamera2Device.setHDRCheckerStatus(ComponentConfigHdr.getHdrUIStatus(componentValue));
            ((BaseModule) this).mCamera2Device.setHDRMode(ComponentConfigHdr.getHdrUIStatus(componentValue));
        }
    }

    public void updateHDRTip(boolean z) {
        ComponentConfigHdr componentHdr = DataRepository.dataItemConfig().getComponentHdr();
        boolean z2 = !componentHdr.isEmpty() && componentHdr.isHdrOnWithChecker(componentHdr.getComponentValue(((BaseModule) this).mModuleIndex));
        if (triggerHDR(z) && !z2) {
            ((BaseModule) this).mHandler.post(new i(z));
        }
    }

    public void updateManualEvAdjust() {
        if (((BaseModule) this).mModuleIndex == 167) {
            String manualValue = getManualValue(CameraSettings.KEY_QC_EXPOSURETIME, getString(R.string.pref_camera_exposuretime_default));
            String manualValue2 = getManualValue(CameraSettings.KEY_QC_ISO, getString(R.string.pref_camera_iso_default));
            String str = TAG;
            Log.d(str, "MODE_MANUAL: exposureTime = " + manualValue + "iso = " + manualValue2);
            boolean equals = b.sm() ? getString(R.string.pref_camera_exposuretime_default).equals(manualValue) : getString(R.string.pref_camera_iso_default).equals(manualValue2) || getString(R.string.pref_camera_exposuretime_default).equals(manualValue);
            Handler handler = ((BaseModule) this).mHandler;
            if (handler != null) {
                handler.post(new a(this, equals));
            }
            if (1 == ((BaseModule) this).mCamera2Device.getFocusMode() && this.m3ALocked) {
                Camera camera = ((BaseModule) this).mActivity;
                if (camera != null) {
                    camera.runOnUiThread(new t(this));
                }
                unlockAEAF();
            }
        }
    }

    public void updateMoon(boolean z) {
        if (z) {
            this.mIsMoonMode = true;
            if (!DataRepository.dataItemFeature().Jd()) {
                ((BaseModule) this).mCamera2Device.setSuperResolution(false);
            }
            updateFocusMode();
            updateHDRPreference();
            this.mCurrentAiScene = 35;
            ((BaseModule) this).mCamera2Device.setASDScene(35);
            resumePreviewInWorkThread();
            if (((BaseModule) this).mZoomSupported) {
                setMaxZoomRatio(Math.max(20.0f, ((BaseModule) this).mCameraCapabilities.getMaxZoomRatio()));
                String str = TAG;
                Log.d(str, "updateMoon(): Override zoom ratio range to: [" + getMinZoomRatio() + "," + getMaxZoomRatio() + "]");
            }
            ModeProtocol.MainContentProtocol mainContentProtocol = (ModeProtocol.MainContentProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(166);
            if (mainContentProtocol != null) {
                mainContentProtocol.clearFocusView(1);
            }
        } else if (this.mIsMoonMode) {
            this.mIsMoonMode = false;
            setFocusMode(this.mFocusManager.setFocusMode(CameraSettings.getFocusMode()));
            updateHDRPreference();
            ((BaseModule) this).mCamera2Device.setASDScene(-35);
            initializeZoomRangeFromCapabilities();
            String str2 = TAG;
            Log.d(str2, "updateMoon(): Restore zoom ratio range to: [" + getMinZoomRatio() + "," + getMaxZoomRatio() + "]");
            if (getZoomRatio() > getMaxZoomRatio()) {
                onZoomingActionUpdate(getMaxZoomRatio(), -1);
            }
        }
        ModeProtocol.DualController dualController = (ModeProtocol.DualController) ModeCoordinatorImpl.getInstance().getAttachProtocol(182);
        if (dualController != null) {
            dualController.hideSlideView();
        }
    }

    public void updateMoonNight() {
        this.mIsMoonMode = false;
        closeMoonMode(10, 0);
        ((ModeProtocol.ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164)).closeMutexElement("e", 193);
        setFlashMode("0");
        updateMfnr(true);
        updateOIS();
        setAiSceneEffect(10);
        this.mCurrentAiScene = 10;
        resumePreviewInWorkThread();
    }

    public void updateOnTripMode() {
        MarshalQueryableASDScene.ASDScene[] aSDSceneArr;
        Camera2Proxy camera2Proxy = ((BaseModule) this).mCamera2Device;
        if (camera2Proxy != null && (aSDSceneArr = this.mAsdScenes) != null) {
            camera2Proxy.setOnTripodModeStatus(aSDSceneArr);
        }
    }

    @Override // com.android.camera.module.BaseModule
    public void updatePreviewSurface() {
        ModeProtocol.MainContentProtocol mainContentProtocol = ((BaseModule) this).mMainProtocol;
        if (mainContentProtocol != null) {
            mainContentProtocol.initEffectCropView();
        }
        checkDisplayOrientation();
        if (((BaseModule) this).mActivity != null) {
            CameraSize cameraSize = ((BaseModule) this).mPreviewSize;
            if (cameraSize != null) {
                updateCameraScreenNailSize(cameraSize.width, cameraSize.height);
            }
            if (((BaseModule) this).mCamera2Device != null) {
                SurfaceTexture surfaceTexture = ((BaseModule) this).mActivity.getCameraScreenNail().getSurfaceTexture();
                String str = TAG;
                Log.d(str, "updatePreviewSurface: surfaceTexture = " + surfaceTexture);
                if (surfaceTexture != null) {
                    ((BaseModule) this).mSurfaceCreatedTimestamp = ((BaseModule) this).mActivity.getCameraScreenNail().getSurfaceCreatedTimestamp();
                }
                ((BaseModule) this).mCamera2Device.updateDeferPreviewSession(new Surface(surfaceTexture));
            }
        }
    }

    @Override // com.android.camera.module.BaseModule
    public void updateSATZooming(boolean z) {
        Camera2Proxy camera2Proxy;
        if (DataRepository.dataItemFeature().Qc() && HybridZoomingSystem.IS_3_OR_MORE_SAT && (camera2Proxy = ((BaseModule) this).mCamera2Device) != null) {
            camera2Proxy.setSatIsZooming(z);
            resumePreviewInWorkThread();
        }
    }

    public void updateSilhouette() {
        if (!isDoingAction() && isAlive()) {
            this.isSilhouette = true;
            trackAISceneChanged(((BaseModule) this).mModuleIndex, 24);
            setAiSceneEffect(24);
            updateHDR("off");
            ((BaseModule) this).mCamera2Device.setASDScene(24);
            resumePreviewInWorkThread();
        }
    }

    public /* synthetic */ void w(boolean z) {
        ((BaseModule) this).mMainProtocol.setEvAdjustable(z);
    }
}
