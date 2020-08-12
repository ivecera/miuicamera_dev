package com.android.camera.module;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.utils.SurfaceUtils;
import android.location.Location;
import android.media.AudioSystem;
import android.media.CamcorderProfile;
import android.media.CameraProfile;
import android.media.MediaCodec;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.ParcelFileDescriptor;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.support.annotation.MainThread;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.media.MediaPlayer2;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Range;
import android.util.Size;
import android.util.TypedValue;
import android.view.Surface;
import android.widget.Toast;
import com.android.camera.AutoLockManager;
import com.android.camera.Camera;
import com.android.camera.CameraIntentManager;
import com.android.camera.CameraSettings;
import com.android.camera.CameraSize;
import com.android.camera.FileCompat;
import com.android.camera.HybridZoomingSystem;
import com.android.camera.LocationManager;
import com.android.camera.R;
import com.android.camera.ThermalHelper;
import com.android.camera.ToastUtils;
import com.android.camera.Util;
import com.android.camera.constant.AutoFocus;
import com.android.camera.constant.GlobalConstant;
import com.android.camera.constant.UpdateConstant;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.config.ComponentConfigBokeh;
import com.android.camera.data.data.config.ComponentConfigSlowMotion;
import com.android.camera.data.data.config.ComponentRunningMacroMode;
import com.android.camera.data.provider.DataProvider;
import com.android.camera.effect.EffectController;
import com.android.camera.effect.FilterInfo;
import com.android.camera.lib.compatibility.util.CompatibilityUtils;
import com.android.camera.log.Log;
import com.android.camera.module.loader.FunctionParseHistogramStats;
import com.android.camera.module.loader.camera2.Camera2DataContainer;
import com.android.camera.module.loader.camera2.FocusManager2;
import com.android.camera.preferences.CameraSettingPreferences;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera.statistic.CameraStatUtils;
import com.android.camera.statistic.MistatsConstants;
import com.android.camera.statistic.MistatsWrapper;
import com.android.camera.statistic.ScenarioTrackUtil;
import com.android.camera.storage.ImageSaver;
import com.android.camera.storage.Storage;
import com.android.camera.ui.ObjectView;
import com.android.camera.ui.PopupManager;
import com.android.camera.ui.RotateTextToast;
import com.android.camera.ui.zoom.ZoomingAction;
import com.android.camera2.Camera2Proxy;
import com.android.camera2.CameraCapabilities;
import com.android.camera2.CameraConfigs;
import com.android.camera2.autozoom.AutoZoomCaptureResult;
import com.android.gallery3d.exif.ExifHelper;
import com.mi.config.b;
import com.miui.extravideo.interpolation.VideoInterpolator;
import com.ss.android.ugc.effectmanager.link.model.configuration.LinkSelectorConfiguration;
import d.d.a;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import miui.reflect.c;

public class VideoModule extends VideoBase implements Camera2Proxy.VideoRecordStateCallback, ModeProtocol.AutoZoomModuleProtocol, ModeProtocol.TopConfigProtocol, MediaRecorder.OnErrorListener, MediaRecorder.OnInfoListener, ObjectView.ObjectViewListener {
    private static final HashMap<String, Integer> HEVC_VIDEO_ENCODER_BITRATE = new HashMap<>();
    private static final int MAX_DURATION_4K = 480000;
    private static final int MAX_DURATION_8K = 360000;
    private static final int RESET_VIDEO_AUTO_FOCUS_TIME = 3000;
    public static final Size SIZE_1080 = new Size(1920, 1080);
    public static final Size SIZE_720 = new Size(1280, Util.LIMIT_SURFACE_WIDTH);
    private static final long START_OFFSET_MS = 450;
    private static final int VIDEO_HFR_FRAME_RATE_120 = 120;
    private static final int VIDEO_HFR_FRAME_RATE_240 = 240;
    public static final long VIDEO_MAX_SINGLE_FILE_SIZE = 3670016000L;
    public static final long VIDEO_MIN_SINGLE_FILE_SIZE = Math.min(8388608L, (long) Storage.LOW_STORAGE_THRESHOLD);
    private static final int VIDEO_NORMAL_FRAME_RATE = 30;
    private AtomicBoolean isAutoZoomTracking = new AtomicBoolean(false);
    private AtomicBoolean isShowOrHideUltraWideHint = new AtomicBoolean(false);
    private Disposable mAutoZoomDataDisposable;
    /* access modifiers changed from: private */
    public FlowableEmitter<CaptureResult> mAutoZoomEmitter;
    private Disposable mAutoZoomUiDisposable;
    /* access modifiers changed from: private */
    public ModeProtocol.AutoZoomViewProtocol mAutoZoomViewProtocol;
    private boolean mCaptureTimeLapse;
    private CountDownTimer mCountDownTimer;
    private volatile int mCurrentFileNumber;
    private Boolean mDumpOrig960 = null;
    private boolean mFovcEnabled;
    /* access modifiers changed from: private */
    public int mFrameRate;
    private int mHfrFPSLower;
    private int mHfrFPSUpper;
    private Disposable mHistogramDisposable;
    /* access modifiers changed from: private */
    public FlowableEmitter<CaptureResult> mHistogramEmitter;
    private boolean mIsSubtitleSupported;
    private boolean mIsVideoTagSupported;
    private final Object mLock = new Object();
    /* access modifiers changed from: private */
    public MediaRecorder mMediaRecorder;
    /* access modifiers changed from: private */
    public boolean mMediaRecorderPostProcessing;
    private boolean mMediaRecorderWorking;
    private String mNextVideoFileName;
    private ContentValues mNextVideoValues;
    private long mPauseClickTime = 0;
    private volatile boolean mPendingStopRecorder;
    private CamcorderProfile mProfile;
    /* access modifiers changed from: private */
    public int mQuality = 5;
    private boolean mQuickCapture;
    private Surface mRecorderSurface;
    private String mRecordingTime;
    private boolean mRecordingTimeCountsDown;
    private String mSlowModeFps;
    /* access modifiers changed from: private */
    public boolean mSnapshotInProgress;
    /* access modifiers changed from: private */
    public String mSpeed = "normal";
    private boolean mSplitWhenReachMaxSize;
    /* access modifiers changed from: private */
    public CountDownLatch mStopRecorderDone;
    private ModeProtocol.SubtitleRecording mSubtitleRecording;
    private String mTemporaryVideoPath;
    private int mTimeBetweenTimeLapseFrameCaptureMs = 0;
    /* access modifiers changed from: private */
    public ModeProtocol.TopAlert mTopAlert;
    private int mTrackLostCount;
    private long mVideoRecordTime = 0;
    private long mVideoRecordedDuration;
    private String mVideoTagFileName;

    private final class JpegPictureCallback extends Camera2Proxy.PictureCallbackWrapper {
        Location mLocation;

        public JpegPictureCallback(Location location) {
            this.mLocation = location;
        }

        private void storeImage(byte[] bArr, Location location) {
            long currentTimeMillis = System.currentTimeMillis();
            int orientation = ExifHelper.getOrientation(bArr);
            ImageSaver imageSaver = ((BaseModule) VideoModule.this).mActivity.getImageSaver();
            boolean access$1900 = VideoModule.this.needImageThumbnail(12);
            String createJpegName = Util.createJpegName(currentTimeMillis);
            long currentTimeMillis2 = System.currentTimeMillis();
            CameraSize cameraSize = ((BaseModule) VideoModule.this).mPictureSize;
            imageSaver.addImage(bArr, access$1900, createJpegName, null, currentTimeMillis2, null, location, cameraSize.width, cameraSize.height, null, orientation, false, false, true, false, false, null, null, -1, null);
        }

        @Override // com.android.camera2.Camera2Proxy.PictureCallbackWrapper, com.android.camera2.Camera2Proxy.PictureCallback
        public void onPictureTaken(byte[] bArr, CaptureResult captureResult) {
            Log.v(VideoBase.TAG, "onPictureTaken");
            boolean unused = VideoModule.this.mSnapshotInProgress = false;
            if (!((BaseModule) VideoModule.this).mPaused) {
                Location location = null;
                if (((BaseModule) VideoModule.this).mActivity.getCameraIntentManager().checkIntentLocationPermission(((BaseModule) VideoModule.this).mActivity)) {
                    location = this.mLocation;
                }
                storeImage(bArr, location);
            }
        }
    }

    static {
        HEVC_VIDEO_ENCODER_BITRATE.put("3840x2160:30", 38500000);
        HEVC_VIDEO_ENCODER_BITRATE.put("1920x1080:30", 15400000);
        HEVC_VIDEO_ENCODER_BITRATE.put("1280x720:30", 10780000);
        HEVC_VIDEO_ENCODER_BITRATE.put("720x480:30", 1379840);
    }

    public VideoModule() {
        super(VideoModule.class.getSimpleName());
    }

    /* access modifiers changed from: private */
    public void consumeAutoZoomData(AutoZoomCaptureResult autoZoomCaptureResult) {
        if (isAlive() && !((BaseModule) this).mActivity.isActivityPaused() && this.isAutoZoomTracking.get()) {
            this.mAutoZoomViewProtocol.feedData(autoZoomCaptureResult);
        }
    }

    private void countDownForVideoBokeh() {
        if (((VideoBase) this).mMediaRecorderRecording) {
            CountDownTimer countDownTimer = this.mCountDownTimer;
            if (countDownTimer != null) {
                countDownTimer.cancel();
            }
            this.mCountDownTimer = new CountDownTimer(30450, 1000) {
                /* class com.android.camera.module.VideoModule.AnonymousClass7 */

                public void onFinish() {
                    VideoModule.this.stopVideoRecording(true, false);
                }

                public void onTick(long j) {
                    String millisecondToTimeString = Util.millisecondToTimeString((j + 950) - VideoModule.START_OFFSET_MS, false);
                    if (VideoModule.this.mTopAlert != null) {
                        VideoModule.this.mTopAlert.updateRecordingTime(millisecondToTimeString);
                    }
                }
            };
            this.mCountDownTimer.start();
        }
    }

    private void forceToNormalMode() {
        DataProvider.ProviderEditor editor = DataRepository.dataItemConfig().editor();
        editor.putString(CameraSettings.KEY_VIDEO_SPEED, "normal");
        editor.apply();
        this.mSpeed = "normal";
    }

    private int getHSRValue() {
        String hSRValue = CameraSettings.getHSRValue(isUltraWideBackCamera());
        if (hSRValue == null || hSRValue.isEmpty() || hSRValue.equals("off")) {
            return 0;
        }
        return Integer.parseInt(hSRValue);
    }

    private int getHevcVideoEncoderBitRate(CamcorderProfile camcorderProfile) {
        String str = camcorderProfile.videoFrameWidth + "x" + camcorderProfile.videoFrameHeight + ":" + camcorderProfile.videoFrameRate;
        if (HEVC_VIDEO_ENCODER_BITRATE.containsKey(str)) {
            return HEVC_VIDEO_ENCODER_BITRATE.get(str).intValue();
        }
        Log.d(VideoBase.TAG, "no pre-defined bitrate for " + str);
        return camcorderProfile.videoBitRate;
    }

    private String getManualValue(String str, String str2) {
        return ModuleManager.isProVideoModule() ? CameraSettingPreferences.instance().getString(str, str2) : str2;
    }

    private int getOperatingModeForPreview() {
        if (CameraSettings.isAutoZoomEnabled(((BaseModule) this).mModuleIndex)) {
            return CameraCapabilities.SESSION_OPERATION_MODE_AUTO_ZOOM;
        }
        return 0;
    }

    private long getRecorderMaxFileSize(int i) {
        long leftSpace = Storage.getLeftSpace() - Storage.LOW_STORAGE_THRESHOLD;
        if (i > 0) {
            long j = (long) i;
            if (j < leftSpace) {
                leftSpace = j;
            }
        }
        long j2 = VIDEO_MAX_SINGLE_FILE_SIZE;
        if (leftSpace <= VIDEO_MAX_SINGLE_FILE_SIZE || !DataRepository.dataItemFeature().s_s_v_OR_T()) {
            j2 = VIDEO_MIN_SINGLE_FILE_SIZE;
            if (leftSpace >= j2) {
                j2 = leftSpace;
            }
        }
        long j3 = ((VideoBase) this).mIntentRequestSize;
        return (j3 <= 0 || j3 >= j2) ? j2 : j3;
    }

    private int getRecorderOrientationHint() {
        int sensorOrientation = ((BaseModule) this).mCameraCapabilities.getSensorOrientation();
        return ((BaseModule) this).mOrientation != -1 ? isFrontCamera() ? ((sensorOrientation - ((BaseModule) this).mOrientation) + 360) % 360 : (sensorOrientation + ((BaseModule) this).mOrientation) % 360 : sensorOrientation;
    }

    private float getResourceFloat(int i, float f2) {
        TypedValue typedValue = new TypedValue();
        try {
            ((BaseModule) this).mActivity.getResources().getValue(i, typedValue, true);
            return typedValue.getFloat();
        } catch (Exception unused) {
            String str = VideoBase.TAG;
            Log.e(str, "Missing resource " + Integer.toHexString(i));
            return f2;
        }
    }

    private long getSpeedRecordVideoLength(long j, double d2) {
        if (d2 == 0.0d) {
            return 0;
        }
        return (long) (((((double) j) / d2) / ((double) getNormalVideoFrameRate())) * 1000.0d);
    }

    private void handleTempVideoFile() {
        if (isCaptureIntent()) {
            String str = this.mTemporaryVideoPath;
            if (str == null) {
                this.mTemporaryVideoPath = getActivity().getCacheDir().getPath() + "/temp_video.mp4";
                String str2 = VideoBase.TAG;
                android.util.Log.d(str2, "VideoModule: wq " + this.mTemporaryVideoPath);
                return;
            }
            File file = new File(str);
            if (file.exists()) {
                file.delete();
            }
        }
    }

    private void hideHint() {
        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        if (topAlert != null) {
            topAlert.alertMacroModeHint(8, R.string.macro_mode);
            topAlert.alertTopHint(8, R.string.super_eis);
            topAlert.alertVideoBeautifyHint(8, R.string.video_beauty_tip_beautification);
            topAlert.alertVideoBeautifyHint(8, R.string.video_beauty_tip);
            topAlert.hideSwitchHint();
        }
        ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
        if (bottomPopupTips != null) {
            bottomPopupTips.directlyHideTipsForce();
        }
    }

    private void initAutoZoom() {
        if (DataRepository.dataItemFeature().Yc()) {
            if (CameraSettings.isAutoZoomEnabled(((BaseModule) this).mModuleIndex)) {
                startAutoZoom();
            } else {
                stopAutoZoom();
            }
            this.mAutoZoomDataDisposable = Flowable.create(new FlowableOnSubscribe<CaptureResult>() {
                /* class com.android.camera.module.VideoModule.AnonymousClass3 */

                @Override // io.reactivex.FlowableOnSubscribe
                public void subscribe(FlowableEmitter<CaptureResult> flowableEmitter) throws Exception {
                    FlowableEmitter unused = VideoModule.this.mAutoZoomEmitter = (FlowableEmitter) flowableEmitter;
                }
            }, BackpressureStrategy.DROP).observeOn(GlobalConstant.sCameraSetupScheduler).map(new Function<CaptureResult, AutoZoomCaptureResult>() {
                /* class com.android.camera.module.VideoModule.AnonymousClass2 */

                public AutoZoomCaptureResult apply(CaptureResult captureResult) throws Exception {
                    return new AutoZoomCaptureResult(captureResult);
                }
            }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<AutoZoomCaptureResult>() {
                /* class com.android.camera.module.VideoModule.AnonymousClass1 */

                public void accept(AutoZoomCaptureResult autoZoomCaptureResult) throws Exception {
                    VideoModule.this.consumeAutoZoomData(autoZoomCaptureResult);
                }
            });
        }
    }

    private void initHistogramEmitter() {
        if (((BaseModule) this).mModuleIndex == 180 && ((BaseModule) this).mCameraCapabilities.isSupportHistogram()) {
            this.mHistogramDisposable = Flowable.create(new FlowableOnSubscribe<CaptureResult>() {
                /* class com.android.camera.module.VideoModule.AnonymousClass5 */

                @Override // io.reactivex.FlowableOnSubscribe
                public void subscribe(FlowableEmitter<CaptureResult> flowableEmitter) throws Exception {
                    FlowableEmitter unused = VideoModule.this.mHistogramEmitter = flowableEmitter;
                }
            }, BackpressureStrategy.DROP).observeOn(GlobalConstant.sCameraSetupScheduler).map(new FunctionParseHistogramStats(((BaseModule) this).mMainProtocol)).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<int[]>() {
                /* class com.android.camera.module.VideoModule.AnonymousClass4 */

                public void accept(int[] iArr) throws Exception {
                    ModeProtocol.MainContentProtocol mainContentProtocol = ((BaseModule) VideoModule.this).mMainProtocol;
                    if (mainContentProtocol != null) {
                        mainContentProtocol.refreshHistogramStatsView();
                    }
                }
            });
        }
    }

    private void initRecorderSurface() {
        this.mRecorderSurface = MediaCodec.createPersistentInputSurface();
    }

    private boolean initializeObjectTrack(RectF rectF, boolean z) {
        mapTapCoordinate(rectF);
        stopObjectTracking(false);
        return ((BaseModule) this).mMainProtocol.initializeObjectTrack(rectF, z);
    }

    private boolean initializeRecorder(boolean z) {
        Log.d(VideoBase.TAG, "initializeRecorder>>");
        long currentTimeMillis = System.currentTimeMillis();
        boolean z2 = false;
        if (getActivity() == null) {
            Log.w(VideoBase.TAG, "initializeRecorder: null host");
            return false;
        }
        closeVideoFileDescriptor();
        cleanupEmptyFile();
        if (isCaptureIntent()) {
            handleTempVideoFile();
            parseIntent(((BaseModule) this).mActivity.getIntent());
        }
        if (((VideoBase) this).mVideoFileDescriptor == null) {
            ((VideoBase) this).mCurrentVideoValues = genContentValues(((VideoBase) this).mOutputFormat, this.mCurrentFileNumber, this.mSlowModeFps, is8KCamcorder(), z);
            ((VideoBase) this).mCurrentVideoFilename = ((VideoBase) this).mCurrentVideoValues.getAsString("_data");
        }
        if (this.mStopRecorderDone != null) {
            long currentTimeMillis2 = System.currentTimeMillis();
            try {
                this.mStopRecorderDone.await(1000, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e2) {
                e2.printStackTrace();
            }
            Log.d(VideoBase.TAG, "initializeRecorder: waitTime=" + (System.currentTimeMillis() - currentTimeMillis2));
        }
        long currentTimeMillis3 = System.currentTimeMillis();
        synchronized (this.mLock) {
            if (this.mMediaRecorder == null) {
                this.mMediaRecorder = new MediaRecorder();
            } else {
                this.mMediaRecorder.reset();
                if (BaseModule.DEBUG) {
                    Log.v(VideoBase.TAG, "initializeRecorder: t1=" + (System.currentTimeMillis() - currentTimeMillis3));
                }
            }
        }
        ParcelFileDescriptor parcelFileDescriptor = null;
        try {
            setupRecorder(this.mMediaRecorder);
            if (((VideoBase) this).mVideoFileDescriptor == null) {
                if (Storage.isUseDocumentMode()) {
                    if (z) {
                        parcelFileDescriptor = FileCompat.getParcelFileDescriptor(((VideoBase) this).mCurrentVideoFilename, true);
                        this.mMediaRecorder.setOutputFile(parcelFileDescriptor.getFileDescriptor());
                    }
                }
                this.mMediaRecorder.setOutputFile(((VideoBase) this).mCurrentVideoFilename);
            } else if (z) {
                this.mMediaRecorder.setOutputFile(((VideoBase) this).mVideoFileDescriptor.getFileDescriptor());
            } else {
                this.mMediaRecorder.setOutputFile(this.mTemporaryVideoPath);
            }
            this.mMediaRecorder.setInputSurface(this.mRecorderSurface);
            long currentTimeMillis4 = System.currentTimeMillis();
            this.mMediaRecorder.prepare();
            this.mMediaRecorder.setOnErrorListener(this);
            this.mMediaRecorder.setOnInfoListener(this);
            if (BaseModule.DEBUG) {
                Log.v(VideoBase.TAG, "initializeRecorder: t2=" + (System.currentTimeMillis() - currentTimeMillis4));
            }
            Util.closeSilently(parcelFileDescriptor);
            z2 = true;
        } catch (Exception e3) {
            Log.e(VideoBase.TAG, "prepare failed for " + ((VideoBase) this).mCurrentVideoFilename, e3);
            releaseMediaRecorder();
            Util.closeSilently(null);
        } catch (Throwable th) {
            Util.closeSilently(null);
            throw th;
        }
        if (BaseModule.DEBUG) {
            showSurfaceInfo(this.mRecorderSurface);
        }
        Log.d(VideoBase.TAG, "initializeRecorder<<time=" + (System.currentTimeMillis() - currentTimeMillis));
        return z2;
    }

    private static boolean is4K30FpsEISSupported() {
        return DataRepository.dataItemFeature().is4K30FpsEISSupported();
    }

    private boolean is4K60FpsEISSupported() {
        return false;
    }

    private boolean is4KCamcorder() {
        return this.mQuality == CameraSettings.get4kProfile() || new CameraSize(3840, 2160).equals(((VideoBase) this).mVideoSize);
    }

    private boolean is8KCamcorder() {
        return this.mQuality == CameraSettings.get8kProfile() || new CameraSize(7680, 4320).equals(((VideoBase) this).mVideoSize);
    }

    /* access modifiers changed from: private */
    public boolean isActivityResumed() {
        Camera activity = getActivity();
        return activity != null && !activity.isActivityPaused();
    }

    private boolean isDump960Orig() {
        if (this.mDumpOrig960 == null) {
            this.mDumpOrig960 = SystemProperties.getBoolean("camera.record.960origdump", false) ? Boolean.TRUE : Boolean.FALSE;
        }
        return this.mDumpOrig960.booleanValue();
    }

    private boolean isEisOn() {
        if (!b._l()) {
            return false;
        }
        if (CameraSettings.isMacroModeEnabled(((BaseModule) this).mModuleIndex) && isUltraWideBackCamera()) {
            return true;
        }
        if (((BaseModule) this).mActualCameraId == Camera2DataContainer.getInstance().getStandaloneMacroCameraId()) {
            return false;
        }
        if (CameraSettings.isAutoZoomEnabled(((BaseModule) this).mModuleIndex)) {
            Log.d(VideoBase.TAG, "videoStabilization: disabled EIS and OIS when AutoZoom is opened");
            return true;
        } else if (CameraSettings.isSuperEISEnabled(((BaseModule) this).mModuleIndex)) {
            return true;
        } else {
            if (!CameraSettings.isMovieSolidOn()) {
                return false;
            }
            if ((!isNormalMode() && !isFastMode()) || needChooseVideoBeauty(((VideoBase) this).mBeautyValues) || this.mQuality == 0 || is8KCamcorder()) {
                return false;
            }
            if (getHSRValue() == 60) {
                if (!is4K60FpsEISSupported()) {
                    return false;
                }
            } else if (CameraSettings.is4KHigherVideoQuality(this.mQuality) && !is4K30FpsEISSupported()) {
                return false;
            }
            return b.hl() || !isFrontCamera();
        }
    }

    /* access modifiers changed from: private */
    public boolean isFPS120() {
        return ComponentConfigSlowMotion.DATA_CONFIG_NEW_SLOW_MOTION_120.equals(this.mSlowModeFps);
    }

    /* access modifiers changed from: private */
    public boolean isFPS240() {
        return ComponentConfigSlowMotion.DATA_CONFIG_NEW_SLOW_MOTION_240.equals(this.mSlowModeFps);
    }

    /* access modifiers changed from: private */
    public boolean isFPS960() {
        return ComponentConfigSlowMotion.DATA_CONFIG_NEW_SLOW_MOTION_960.equals(this.mSlowModeFps);
    }

    private boolean isFastMode() {
        return CameraSettings.VIDEO_SPEED_FAST.equals(this.mSpeed);
    }

    private boolean isNormalMode() {
        return "normal".equals(this.mSpeed);
    }

    private boolean isSplitWhenReachMaxSize() {
        return this.mSplitWhenReachMaxSize;
    }

    private boolean needDisableEISAndOIS() {
        if (!CameraSettings.isVideoBokehOn() || !isFrontCamera()) {
            return false;
        }
        Log.d(VideoBase.TAG, "videoStabilization: disabled EIS and OIS when VIDEO_BOKEH is opened");
        return true;
    }

    /* access modifiers changed from: private */
    public boolean needImageThumbnail(int i) {
        return i != 12;
    }

    private void notifyAutoZoomStartUiHint() {
        notifyAutoZoomStopUiHint();
        ModeProtocol.TopAlert topAlert = this.mTopAlert;
        if (topAlert == null || !topAlert.isExtraMenuShowing()) {
            this.mAutoZoomUiDisposable = Observable.timer(2000, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Long>() {
                /* class com.android.camera.module.VideoModule.AnonymousClass13 */

                public void accept(Long l) throws Exception {
                    if (VideoModule.this.mTopAlert != null) {
                        VideoModule.this.mTopAlert.alertAiDetectTipHint(0, R.string.autozoom_click_hint, -1);
                    }
                }
            });
        }
    }

    private void notifyAutoZoomStopUiHint() {
        Disposable disposable = this.mAutoZoomUiDisposable;
        if (disposable != null && !disposable.isDisposed()) {
            this.mAutoZoomUiDisposable.dispose();
            this.mAutoZoomUiDisposable = null;
        }
    }

    private void onMaxFileSizeReached() {
        String str = ((VideoBase) this).mCurrentVideoFilename;
        if (str != null) {
            saveVideo(str, ((VideoBase) this).mCurrentVideoValues, false, false);
            ((VideoBase) this).mCurrentVideoValues = null;
            ((VideoBase) this).mCurrentVideoFilename = null;
        }
    }

    /* access modifiers changed from: private */
    public void onMediaRecorderReleased() {
        ModeProtocol.RecordState recordState;
        String str;
        Log.d(VideoBase.TAG, "onMediaRecorderReleased>>");
        long currentTimeMillis = System.currentTimeMillis();
        restoreOuterAudio();
        if (isCaptureIntent() && !((BaseModule) this).mPaused) {
            if (((VideoBase) this).mCurrentVideoUri == null && (str = ((VideoBase) this).mCurrentVideoFilename) != null) {
                ((VideoBase) this).mCurrentVideoUri = saveVideo(str, ((VideoBase) this).mCurrentVideoValues, true, true);
                String str2 = VideoBase.TAG;
                Log.d(str2, "onMediaRecorderReleased: outputUri=" + ((VideoBase) this).mCurrentVideoUri);
            }
            boolean z = ((VideoBase) this).mCurrentVideoUri != null;
            if (this.mQuickCapture) {
                doReturnToCaller(z);
            } else if (z) {
                showAlert();
            }
        }
        if (((VideoBase) this).mCurrentVideoFilename != null) {
            if (!isCaptureIntent()) {
                saveVideo(((VideoBase) this).mCurrentVideoFilename, ((VideoBase) this).mCurrentVideoValues, true, false);
            }
            ((VideoBase) this).mCurrentVideoFilename = null;
            ((VideoBase) this).mCurrentVideoValues = null;
        } else if (!((BaseModule) this).mPaused && !((BaseModule) this).mActivity.isActivityPaused()) {
            ((BaseModule) this).mActivity.getThumbnailUpdater().getLastThumbnail();
        }
        if (this.mMediaRecorderPostProcessing && (recordState = (ModeProtocol.RecordState) ModeCoordinatorImpl.getInstance().getAttachProtocol(212)) != null) {
            recordState.onPostSavingFinish();
        }
        ((BaseModule) this).mActivity.sendBroadcast(new Intent(VideoBase.STOP_VIDEO_RECORDING_ACTION));
        ((VideoBase) this).mTelephonyManager.listen(((BaseModule) this).mPhoneStateListener, 0);
        Log.v(VideoBase.TAG, "listen none");
        enableCameraControls(true);
        if (!AutoFocus.LEGACY_CONTINUOUS_VIDEO.equals(((VideoBase) this).mVideoFocusMode)) {
            ((VideoBase) this).mFocusManager.resetFocusStateIfNeeded();
            if (!((BaseModule) this).mPaused && !((BaseModule) this).mActivity.isActivityPaused()) {
                ((BaseModule) this).mMainProtocol.clearFocusView(2);
                setVideoFocusMode(AutoFocus.LEGACY_CONTINUOUS_VIDEO, false);
                updatePreferenceInWorkThread(14);
            }
        }
        keepScreenOnAwhile();
        String str3 = VideoBase.TAG;
        Log.d(str3, "onMediaRecorderReleased<<time=" + (System.currentTimeMillis() - currentTimeMillis));
        ScenarioTrackUtil.trackStopVideoRecordEnd();
        doLaterReleaseIfNeed();
        if (this.mMediaRecorderPostProcessing) {
            this.mMediaRecorderPostProcessing = false;
        }
        this.mMediaRecorderWorking = false;
        ((BaseModule) this).mHandler.post(new Runnable() {
            /* class com.android.camera.module.VideoModule.AnonymousClass10 */

            public void run() {
                VideoModule.this.handlePendingScreenSlide();
            }
        });
    }

    private void onStartRecorderFail() {
        enableCameraControls(true);
        releaseMediaRecorder();
        restoreOuterAudio();
        ((ModeProtocol.RecordState) ModeCoordinatorImpl.getInstance().getAttachProtocol(212)).onFailed();
    }

    private void onStartRecorderSucceed() {
        int i;
        ModeProtocol.SubtitleRecording subtitleRecording;
        if (!isFPS960()) {
            enableCameraControls(true);
        }
        ((BaseModule) this).mActivity.sendBroadcast(new Intent(VideoBase.START_VIDEO_RECORDING_ACTION));
        this.mMediaRecorderWorking = true;
        ((VideoBase) this).mMediaRecorderRecording = true;
        hideHint();
        if (this.mIsSubtitleSupported && (subtitleRecording = this.mSubtitleRecording) != null) {
            subtitleRecording.handleSubtitleRecordingStart();
        }
        ModeProtocol.MainContentProtocol mainContentProtocol = ((BaseModule) this).mMainProtocol;
        if (mainContentProtocol != null) {
            this.mVideoTagFileName = ((VideoBase) this).mCurrentVideoFilename;
            mainContentProtocol.processingStart(this.mIsVideoTagSupported ? this.mVideoTagFileName : null);
        }
        if (HybridZoomingSystem.IS_3_OR_MORE_SAT && (i = ((BaseModule) this).mModuleIndex) != 172 && i != 180 && ((!CameraSettings.isMacroModeEnabled(i) || DataRepository.dataItemFeature().c_19039_0x0005_eq_2()) && !CameraSettings.isAutoZoomEnabled(((BaseModule) this).mModuleIndex) && !CameraSettings.isSuperEISEnabled(((BaseModule) this).mModuleIndex) && isBackCamera())) {
            ((BaseModule) this).mPreZoomRation = getZoomRatio();
            updateZoomRatioToggleButtonState(true);
            if (isUltraWideBackCamera()) {
                if (CameraSettings.isMacroModeEnabled(((BaseModule) this).mModuleIndex)) {
                    setMinZoomRatio(HybridZoomingSystem.getMinimumMacroOpticalZoomRatio());
                    setMaxZoomRatio(HybridZoomingSystem.getMaximumMacroOpticalZoomRatio());
                } else {
                    setMinZoomRatio(0.6f);
                    setMaxZoomRatio(2.0f);
                }
            } else if (isAuxCamera()) {
                setMinZoomRatio(HybridZoomingSystem.FLOAT_ZOOM_RATIO_TELE);
                setMaxZoomRatio(Math.min(6.0f, ((BaseModule) this).mCameraCapabilities.getMaxZoomRatio()));
            } else if (!DataRepository.dataItemFeature().sf()) {
                setMinZoomRatio(1.0f);
                setMaxZoomRatio(Math.min(6.0f, ((BaseModule) this).mCameraCapabilities.getMaxZoomRatio()));
            } else if (isInVideoSAT()) {
                setMinZoomRatio(0.6f);
                setMaxZoomRatio(12.0f);
            } else {
                setMinZoomRatio(1.0f);
                setMaxZoomRatio(Math.min(6.0f, ((BaseModule) this).mCameraCapabilities.getMaxZoomRatio()));
            }
        }
        ((VideoBase) this).mMediaRecorderRecordingPaused = false;
        ((VideoBase) this).mRecordingStartTime = SystemClock.uptimeMillis();
        this.mPauseClickTime = SystemClock.uptimeMillis();
        this.mRecordingTime = "";
        ((VideoBase) this).mTelephonyManager.listen(((BaseModule) this).mPhoneStateListener, 32);
        Log.v(VideoBase.TAG, "listen call state");
        if (CameraSettings.isVideoBokehOn()) {
            countDownForVideoBokeh();
        } else {
            updateRecordingTime();
        }
        keepScreenOn();
        AutoLockManager.getInstance(((BaseModule) this).mActivity).removeMessage();
        HashMap hashMap = new HashMap();
        hashMap.put(MistatsConstants.Manual.PARAM_3A_LOCKED, Boolean.valueOf(((VideoBase) this).m3ALocked));
        trackGeneralInfo(hashMap, 1, false, ((VideoBase) this).mBeautyValues, false, 0);
        keepPowerSave();
    }

    private void pauseVideoRecording() {
        Log.d(VideoBase.TAG, "pauseVideoRecording");
        if (((VideoBase) this).mMediaRecorderRecording && !((VideoBase) this).mMediaRecorderRecordingPaused) {
            try {
                pauseMediaRecorder(this.mMediaRecorder);
            } catch (IllegalStateException unused) {
                Log.e(VideoBase.TAG, "failed to pause media recorder");
            }
            this.mVideoRecordedDuration = SystemClock.uptimeMillis() - ((VideoBase) this).mRecordingStartTime;
            ((VideoBase) this).mMediaRecorderRecordingPaused = true;
            ((BaseModule) this).mHandler.removeMessages(42);
            updateRecordingTime();
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x00f6, code lost:
        if (r5 == false) goto L_0x00f8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x00f8, code lost:
        com.android.camera.log.Log.e(com.android.camera.module.VideoBase.TAG, "960fps processing failed. delete the files.");
        r2.delete();
        r3.delete();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x010c, code lost:
        if (0 != 0) goto L_0x010f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x010f, code lost:
        if (r5 == false) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:?, code lost:
        return r4.getAbsolutePath();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:?, code lost:
        return null;
     */
    public String postProcessVideo(String str) {
        boolean z;
        if (str == null) {
            return null;
        }
        File file = new File(str);
        File file2 = new File(str + ".bak");
        File file3 = Storage.isUseDocumentMode() ? new File(Storage.generatePrimaryFilepath(file.getName())) : new File(Storage.generateFilepath(file.getName()));
        boolean z2 = false;
        try {
            boolean z3 = !a.lh && CameraSettings.isSupport960VideoEditor();
            if (CameraSettings.is960WatermarkOn(getModuleIndex())) {
                Bitmap load960fpsCameraWatermark = Util.load960fpsCameraWatermark(((BaseModule) this).mActivity);
                float resourceFloat = getResourceFloat(R.dimen.global_fps960_watermark_size_ratio, 0.0f);
                if (!Util.isGlobalVersion() || resourceFloat == 0.0f) {
                    resourceFloat = getResourceFloat(R.dimen.fps960_watermark_size_ratio, 0.0f);
                }
                z = VideoInterpolator.doDecodeAndEncodeSyncWithWatermark(file.getAbsolutePath(), file2.getAbsolutePath(), DataRepository.dataItemFeature().ad(), load960fpsCameraWatermark, new float[]{resourceFloat, getResourceFloat(R.dimen.fps960_watermark_padding_x_ratio, 0.0f), getResourceFloat(R.dimen.fps960_watermark_padding_y_ratio, 0.0f)}, z3);
            } else {
                Log.d(VideoBase.TAG, "postProcessVideo: start ");
                z = VideoInterpolator.doDecodeAndEncodeSync(file.getAbsolutePath(), file2.getAbsolutePath(), DataRepository.dataItemFeature().ad(), z3);
                Log.d(VideoBase.TAG, "postProcessVideo: end ");
            }
            if (z && file2.renameTo(file3)) {
                z2 = true;
            }
            if (isDump960Orig()) {
                file.renameTo(new File(str + ".orig.mp4"));
            } else {
                file.delete();
            }
        } catch (Throwable th) {
            if (0 == 0) {
                Log.e(VideoBase.TAG, "960fps processing failed. delete the files.");
                file.delete();
                file2.delete();
            }
            throw th;
        }
    }

    /* access modifiers changed from: private */
    public void releaseMediaRecorder() {
        MediaRecorder mediaRecorder;
        Log.v(VideoBase.TAG, "releaseRecorder");
        synchronized (this.mLock) {
            mediaRecorder = this.mMediaRecorder;
            this.mMediaRecorder = null;
        }
        if (mediaRecorder != null) {
            cleanupEmptyFile();
            long currentTimeMillis = System.currentTimeMillis();
            mediaRecorder.reset();
            String str = VideoBase.TAG;
            Log.v(str, "releaseRecorder: t1=" + (System.currentTimeMillis() - currentTimeMillis));
            long currentTimeMillis2 = System.currentTimeMillis();
            mediaRecorder.release();
            String str2 = VideoBase.TAG;
            Log.v(str2, "releaseRecorder: t2=" + (System.currentTimeMillis() - currentTimeMillis2));
        }
    }

    private void releaseRecorderSurface() {
        Surface surface = this.mRecorderSurface;
        if (surface != null) {
            surface.release();
        }
    }

    private void releaseResources() {
        FlowableEmitter<CaptureResult> flowableEmitter = this.mAutoZoomEmitter;
        if (flowableEmitter != null) {
            flowableEmitter.onComplete();
        }
        Disposable disposable = this.mAutoZoomUiDisposable;
        if (disposable != null && !disposable.isDisposed()) {
            this.mAutoZoomUiDisposable.dispose();
            this.mAutoZoomUiDisposable = null;
        }
        Disposable disposable2 = this.mAutoZoomDataDisposable;
        if (disposable2 != null && !disposable2.isDisposed()) {
            this.mAutoZoomDataDisposable.dispose();
            this.mAutoZoomDataDisposable = null;
        }
        FlowableEmitter<CaptureResult> flowableEmitter2 = this.mHistogramEmitter;
        if (flowableEmitter2 != null) {
            flowableEmitter2.onComplete();
        }
        Disposable disposable3 = this.mHistogramDisposable;
        if (disposable3 != null && !disposable3.isDisposed()) {
            this.mHistogramDisposable.dispose();
            this.mHistogramDisposable = null;
        }
        stopTracking(0);
        stopAutoZoom();
        closeCamera();
        releaseMediaRecorder();
        handleTempVideoFile();
    }

    private Uri saveVideo(String str, ContentValues contentValues, boolean z, boolean z2) {
        if (((BaseModule) this).mActivity != null) {
            String str2 = VideoBase.TAG;
            Log.w(str2, "saveVideo: path=" + str + " isFinal=" + z);
            contentValues.put("datetaken", Long.valueOf(System.currentTimeMillis()));
            if (z2) {
                return ((BaseModule) this).mActivity.getImageSaver().addVideoSync(str, contentValues, false);
            }
            ((BaseModule) this).mActivity.getImageSaver().addVideo(str, contentValues, z);
        } else {
            String str3 = VideoBase.TAG;
            Log.w(str3, "saveVideo: failed to save " + str);
        }
        return null;
    }

    private void setJpegQuality() {
        if (isDeviceAlive()) {
            int jpegEncodingQualityParameter = CameraProfile.getJpegEncodingQualityParameter(((BaseModule) this).mBogusCameraId, 2);
            String str = VideoBase.TAG;
            Log.d(str, "jpegQuality=" + jpegEncodingQualityParameter);
            ((BaseModule) this).mCamera2Device.setJpegQuality(jpegEncodingQualityParameter);
        }
    }

    private boolean setNextOutputFile(String str) {
        if (TextUtils.isEmpty(str)) {
            Log.w(VideoBase.TAG, "setNextOutputFile, filePath is empty");
            return false;
        } else if (!Storage.isUseDocumentMode()) {
            return CompatibilityUtils.setNextOutputFile(this.mMediaRecorder, str);
        } else {
            ParcelFileDescriptor parcelFileDescriptor = null;
            try {
                parcelFileDescriptor = FileCompat.getParcelFileDescriptor(str, true);
                return CompatibilityUtils.setNextOutputFile(this.mMediaRecorder, parcelFileDescriptor.getFileDescriptor());
            } catch (Exception e2) {
                String str2 = VideoBase.TAG;
                Log.d(str2, "open file failed, filePath " + str, e2);
                return false;
            } finally {
                Util.closeSafely(parcelFileDescriptor);
            }
        }
    }

    private void setParameterExtra(MediaRecorder mediaRecorder, String str) {
        Class<?>[] clsArr = {MediaRecorder.class};
        c method = Util.getMethod(clsArr, "setParameter", "(Ljava/lang/String;)V");
        if (method != null) {
            method.a(clsArr[0], mediaRecorder, str);
        }
    }

    private void setSplitWhenReachMaxSize(boolean z) {
        this.mSplitWhenReachMaxSize = z;
    }

    private void setupRecorder(MediaRecorder mediaRecorder) throws FileNotFoundException {
        int i;
        boolean isNormalMode = isNormalMode();
        boolean z = isNormalMode || ((isFPS120() || isFPS240()) && !DataRepository.dataItemFeature().c_0x44());
        mediaRecorder.setVideoSource(2);
        if (z) {
            mediaRecorder.setAudioSource(5);
        }
        mediaRecorder.setOutputFormat(this.mProfile.fileFormat);
        mediaRecorder.setVideoEncoder(this.mProfile.videoCodec);
        CamcorderProfile camcorderProfile = this.mProfile;
        mediaRecorder.setVideoSize(camcorderProfile.videoFrameWidth, camcorderProfile.videoFrameHeight);
        int hSRValue = getHSRValue();
        if (hSRValue > 0) {
            mediaRecorder.setVideoFrameRate(hSRValue);
            String str = VideoBase.TAG;
            Log.d(str, "setVideoFrameRate: " + hSRValue);
        } else {
            mediaRecorder.setVideoFrameRate(this.mProfile.videoFrameRate);
            String str2 = VideoBase.TAG;
            Log.d(str2, "setVideoFrameRate: " + this.mProfile.videoFrameRate);
        }
        CamcorderProfile camcorderProfile2 = this.mProfile;
        if (5 == camcorderProfile2.videoCodec) {
            i = getHevcVideoEncoderBitRate(camcorderProfile2);
            String str3 = VideoBase.TAG;
            Log.d(str3, "H265 bitrate: " + i);
        } else {
            i = camcorderProfile2.videoBitRate;
            String str4 = VideoBase.TAG;
            Log.d(str4, "H264 bitrate: " + i);
        }
        mediaRecorder.setVideoEncodingBitRate(i);
        if (z) {
            mediaRecorder.setAudioEncodingBitRate(this.mProfile.audioBitRate);
            mediaRecorder.setAudioChannels(this.mProfile.audioChannels);
            mediaRecorder.setAudioSamplingRate(this.mProfile.audioSampleRate);
            mediaRecorder.setAudioEncoder(this.mProfile.audioCodec);
        }
        if (this.mCaptureTimeLapse) {
            mediaRecorder.setCaptureRate(1000.0d / ((double) this.mTimeBetweenTimeLapseFrameCaptureMs));
        } else if (!isNormalMode) {
            if (ModuleManager.isVideoNewSlowMotion() && !DataRepository.dataItemFeature().c_0x44()) {
                mediaRecorder.setVideoFrameRate(this.mFrameRate);
                mediaRecorder.setVideoEncodingBitRate(Build.VERSION.SDK_INT < 28 ? (int) ((((long) i) * ((long) this.mFrameRate)) / ((long) getNormalVideoFrameRate())) : ((this.mFrameRate / getNormalVideoFrameRate()) / 2) * i);
            }
            mediaRecorder.setCaptureRate((double) this.mFrameRate);
        } else if (hSRValue > 0) {
            mediaRecorder.setVideoFrameRate(hSRValue);
            mediaRecorder.setCaptureRate((double) hSRValue);
        }
        mediaRecorder.setMaxDuration(((VideoBase) this).mMaxVideoDurationInMs);
        Location location = null;
        if (((BaseModule) this).mActivity.getCameraIntentManager().checkIntentLocationPermission(((BaseModule) this).mActivity)) {
            location = LocationManager.instance().getCurrentLocation();
        }
        if (location != null) {
            mediaRecorder.setLocation((float) location.getLatitude(), (float) location.getLongitude());
        }
        int i2 = SystemProperties.getInt("camera.debug.video_max_size", 0);
        long recorderMaxFileSize = getRecorderMaxFileSize(i2);
        if (recorderMaxFileSize > 0) {
            String str5 = VideoBase.TAG;
            Log.v(str5, "maxFileSize=" + recorderMaxFileSize);
            mediaRecorder.setMaxFileSize(recorderMaxFileSize);
            if (recorderMaxFileSize > VIDEO_MAX_SINGLE_FILE_SIZE) {
                setParameterExtra(mediaRecorder, "param-use-64bit-offset=1");
            }
        }
        if (!DataRepository.dataItemFeature().s_s_v_OR_T() || (i2 <= 0 && recorderMaxFileSize != VIDEO_MAX_SINGLE_FILE_SIZE)) {
            setSplitWhenReachMaxSize(false);
        } else {
            setSplitWhenReachMaxSize(true);
        }
        if ((isFPS240() || isFPS960()) && !DataRepository.dataItemFeature().c_0x44()) {
            try {
                setParameterExtra(mediaRecorder, "video-param-i-frames-interval=0.033");
            } catch (Exception e2) {
                Log.e(VideoBase.TAG, e2.getMessage(), e2);
            }
        }
        mediaRecorder.setOrientationHint(getRecorderOrientationHint());
        AudioSystem.setParameters("video_rotation=" + ((BaseModule) this).mOrientation);
        ((VideoBase) this).mOrientationCompensationAtRecordStart = ((BaseModule) this).mOrientationCompensation;
    }

    private boolean shouldApplyUltraWideLDC() {
        if (CameraSettings.shouldUltraWideVideoLDCBeVisibleInMode(((BaseModule) this).mModuleIndex) && ((BaseModule) this).mActualCameraId == Camera2DataContainer.getInstance().getUltraWideCameraId()) {
            return CameraSettings.isUltraWideVideoLDCEnabled();
        }
        return false;
    }

    private void showSurfaceInfo(Surface surface) {
        boolean isValid = surface.isValid();
        boolean isSurfaceForHwVideoEncoder = SurfaceUtils.isSurfaceForHwVideoEncoder(surface);
        Size surfaceSize = SurfaceUtils.getSurfaceSize(surface);
        int surfaceFormat = SurfaceUtils.getSurfaceFormat(surface);
        String str = VideoBase.TAG;
        Log.d(str, "showSurfaceInfo: " + surface + "|" + isValid + "|" + surfaceSize + "|" + surfaceFormat + "|" + isSurfaceForHwVideoEncoder);
    }

    private void startHighSpeedRecordSession() {
        Log.d(VideoBase.TAG, "startHighSpeedRecordSession");
        if (isDeviceAlive()) {
            checkDisplayOrientation();
            ((BaseModule) this).mCamera2Device.setErrorCallback(((BaseModule) this).mErrorCallback);
            ((BaseModule) this).mCamera2Device.setPictureSize(((BaseModule) this).mPreviewSize);
            if (((BaseModule) this).mAELockOnlySupported) {
                ((BaseModule) this).mCamera2Device.setFocusCallback(this);
            }
            ((BaseModule) this).mSurfaceCreatedTimestamp = ((BaseModule) this).mActivity.getCameraScreenNail().getSurfaceCreatedTimestamp();
            ((BaseModule) this).mCamera2Device.startHighSpeedRecordSession(new Surface(((BaseModule) this).mActivity.getCameraScreenNail().getSurfaceTexture()), this.mRecorderSurface, new Range<>(Integer.valueOf(this.mHfrFPSLower), Integer.valueOf(this.mHfrFPSUpper)), this);
            ((VideoBase) this).mFocusManager.resetFocused();
            showSurfaceInfo(this.mRecorderSurface);
        }
    }

    private void startPreviewAfterRecord() {
        if (isDeviceAlive() && !((BaseModule) this).mActivity.isActivityPaused()) {
            unlockAEAF();
            if (isCaptureIntent()) {
                return;
            }
            if (ModuleManager.isVideoNewSlowMotion()) {
                ((BaseModule) this).mCamera2Device.startHighSpeedRecordPreview();
            } else {
                ((BaseModule) this).mCamera2Device.startRecordPreview();
            }
        }
    }

    private void startPreviewSession() {
        Log.d(VideoBase.TAG, "startPreviewSession");
        if (isDeviceAlive()) {
            checkDisplayOrientation();
            ((BaseModule) this).mCamera2Device.setFocusCallback(this);
            ((BaseModule) this).mCamera2Device.setErrorCallback(((BaseModule) this).mErrorCallback);
            ((BaseModule) this).mCamera2Device.setPictureSize(((BaseModule) this).mPreviewSize);
            Surface surface = new Surface(((BaseModule) this).mActivity.getCameraScreenNail().getSurfaceTexture());
            ((BaseModule) this).mSurfaceCreatedTimestamp = ((BaseModule) this).mActivity.getCameraScreenNail().getSurfaceCreatedTimestamp();
            ((BaseModule) this).mCamera2Device.startPreviewSession(surface, false, false, false, getOperatingModeForPreview(), false, this);
        }
    }

    private void startRecordSession() {
        String str = VideoBase.TAG;
        Log.d(str, "startRecordSession: mode=" + this.mSpeed);
        if (isDeviceAlive()) {
            checkDisplayOrientation();
            ((BaseModule) this).mCamera2Device.setErrorCallback(((BaseModule) this).mErrorCallback);
            ((BaseModule) this).mCamera2Device.setPictureSize(((BaseModule) this).mPreviewSize);
            ((BaseModule) this).mCamera2Device.setVideoSnapshotSize(((BaseModule) this).mPictureSize);
            if (((BaseModule) this).mAELockOnlySupported) {
                ((BaseModule) this).mCamera2Device.setFocusCallback(this);
            }
            int operatingMode = getOperatingMode();
            Log.d(VideoBase.TAG, String.format("startRecordSession: operatingMode = 0x%x", Integer.valueOf(operatingMode)));
            ((BaseModule) this).mSurfaceCreatedTimestamp = ((BaseModule) this).mActivity.getCameraScreenNail().getSurfaceCreatedTimestamp();
            updateFpsRange();
            ((BaseModule) this).mCamera2Device.startRecordSession(new Surface(((BaseModule) this).mActivity.getCameraScreenNail().getSurfaceTexture()), this.mRecorderSurface, true, operatingMode, this);
            ((VideoBase) this).mFocusManager.resetFocused();
            ((VideoBase) this).mPreviewing = true;
        }
    }

    private boolean startRecorder() {
        if (!initializeRecorder(true)) {
            return false;
        }
        if (DataRepository.dataItemFeature().c_27845_0x0001() && CameraSettings.is4KHigherVideoQuality(this.mQuality)) {
            int hSRValue = getHSRValue();
            if (hSRValue <= 0) {
                hSRValue = this.mProfile.videoFrameRate;
            }
            ThermalHelper.notifyThermalRecordStart(this.mQuality, hSRValue);
        }
        try {
            this.mMediaRecorder.start();
            this.mMediaRecorderWorking = true;
            return true;
        } catch (IllegalStateException e2) {
            Log.e(VideoBase.TAG, "could not start recorder", e2);
            showConfirmMessage(R.string.confirm_recording_fail_title, R.string.confirm_recording_fail_recorder_busy_alert);
            return false;
        }
    }

    private void startVideoRecordingIfNeeded() {
        if (!((BaseModule) this).mActivity.getCameraIntentManager().checkCallerLegality() || ((BaseModule) this).mActivity.isActivityPaused()) {
            return;
        }
        if (!((BaseModule) this).mActivity.getCameraIntentManager().isOpenOnly(((BaseModule) this).mActivity)) {
            ((BaseModule) this).mActivity.getIntent().removeExtra(CameraIntentManager.CameraExtras.CAMERA_OPEN_ONLY);
            if (((BaseModule) this).mActivity.getCameraIntentManager().getTimerDurationSeconds() > 0) {
                Log.e(VideoBase.TAG, "Video mode doesn't support Time duration!");
            } else if (!((BaseModule) this).mActivity.isIntentPhotoDone()) {
                ((BaseModule) this).mHandler.postDelayed(new Runnable() {
                    /* class com.android.camera.module.VideoModule.AnonymousClass6 */

                    public void run() {
                        VideoModule videoModule = VideoModule.this;
                        videoModule.onShutterButtonClick(videoModule.getTriggerMode());
                    }
                }, 1500);
                ((BaseModule) this).mActivity.setIntnetPhotoDone(true);
            }
        } else {
            ((BaseModule) this).mActivity.getIntent().removeExtra(CameraIntentManager.CameraExtras.TIMER_DURATION_SECONDS);
        }
    }

    @SuppressLint({"CheckResult"})
    private void stopRecorder() {
        this.mPendingStopRecorder = false;
        ((BaseModule) this).mHandler.removeMessages(46);
        if (DataRepository.dataItemFeature().c_27845_0x0001() && CameraSettings.is4KHigherVideoQuality(this.mQuality)) {
            int hSRValue = getHSRValue();
            if (hSRValue <= 0) {
                hSRValue = this.mProfile.videoFrameRate;
            }
            ThermalHelper.notifyThermalRecordStop(this.mQuality, hSRValue);
        }
        Single.create(new SingleOnSubscribe<Boolean>() {
            /* class com.android.camera.module.VideoModule.AnonymousClass9 */

            @Override // io.reactivex.SingleOnSubscribe
            public void subscribe(SingleEmitter<Boolean> singleEmitter) throws Exception {
                CountDownLatch unused = VideoModule.this.mStopRecorderDone = new CountDownLatch(1);
                long currentTimeMillis = System.currentTimeMillis();
                ScenarioTrackUtil.trackStopVideoRecordStart(VideoModule.this.mSpeed, VideoModule.this.isFrontCamera());
                try {
                    VideoModule.this.mMediaRecorder.setOnErrorListener(null);
                    VideoModule.this.mMediaRecorder.setOnInfoListener(null);
                    VideoModule.this.mMediaRecorder.stop();
                } catch (RuntimeException e2) {
                    Log.e(VideoBase.TAG, "failed to stop media recorder", e2);
                    VideoModule videoModule = VideoModule.this;
                    String str = ((VideoBase) videoModule).mCurrentVideoFilename;
                    if (str != null) {
                        videoModule.deleteVideoFile(str);
                        ((VideoBase) VideoModule.this).mCurrentVideoFilename = null;
                    }
                }
                if (!((BaseModule) VideoModule.this).mPaused && !((BaseModule) VideoModule.this).mActivity.isActivityPaused()) {
                    VideoModule.this.playCameraSound(3);
                }
                VideoModule.this.releaseMediaRecorder();
                VideoModule.this.mStopRecorderDone.countDown();
                String str2 = VideoBase.TAG;
                Log.d(str2, "releaseTime=" + (System.currentTimeMillis() - currentTimeMillis));
                long uptimeMillis = SystemClock.uptimeMillis();
                VideoModule videoModule2 = VideoModule.this;
                long j = uptimeMillis - ((VideoBase) videoModule2).mRecordingStartTime;
                if (((BaseModule) videoModule2).mCamera2Device != null && ModuleManager.isVideoNewSlowMotion() && (VideoModule.this.isFPS120() || VideoModule.this.isFPS240())) {
                    CameraStatUtils.trackNewSlowMotionVideoRecorded(VideoModule.this.isFPS120() ? CameraSettings.VIDEO_MODE_120 : CameraSettings.VIDEO_MODE_240, VideoModule.this.mQuality, ((BaseModule) VideoModule.this).mCamera2Device.getFlashMode(), VideoModule.this.mFrameRate, j / 1000, false);
                }
                VideoModule videoModule3 = VideoModule.this;
                if (((VideoBase) videoModule3).mCurrentVideoFilename != null && videoModule3.isFPS960()) {
                    if (!VideoModule.this.mMediaRecorderPostProcessing || !VideoModule.this.isActivityResumed()) {
                        String str3 = VideoBase.TAG;
                        Log.d(str3, "uncomplete video.=" + j);
                        VideoModule videoModule4 = VideoModule.this;
                        videoModule4.deleteVideoFile(((VideoBase) videoModule4).mCurrentVideoFilename);
                        ((VideoBase) VideoModule.this).mCurrentVideoFilename = null;
                        MistatsWrapper.keyTriggerEvent(MistatsConstants.VideoAttr.KEY_VIDEO_960, MistatsConstants.BaseEvent.FEATURE_NAME, MistatsConstants.VideoAttr.VALUE_FPS960_TOO_SHORT);
                    } else {
                        long currentTimeMillis2 = System.currentTimeMillis();
                        VideoModule videoModule5 = VideoModule.this;
                        String access$1600 = videoModule5.postProcessVideo(((VideoBase) videoModule5).mCurrentVideoFilename);
                        String str4 = VideoBase.TAG;
                        Log.d(str4, "processTime=" + (System.currentTimeMillis() - currentTimeMillis2));
                        if (access$1600 == null) {
                            VideoModule videoModule6 = VideoModule.this;
                            ((VideoBase) videoModule6).mCurrentVideoFilename = null;
                            ((VideoBase) videoModule6).mCurrentVideoValues = null;
                            MistatsWrapper.keyTriggerEvent(MistatsConstants.VideoAttr.KEY_VIDEO_960, MistatsConstants.BaseEvent.FEATURE_NAME, MistatsConstants.VideoAttr.VALUE_FPS960_PROCESS_FAILED);
                        } else {
                            VideoModule videoModule7 = VideoModule.this;
                            ((VideoBase) videoModule7).mCurrentVideoFilename = access$1600;
                            ((VideoBase) videoModule7).mCurrentVideoValues.put("_data", access$1600);
                            CameraStatUtils.trackNewSlowMotionVideoRecorded(CameraSettings.VIDEO_MODE_960, VideoModule.this.mQuality, ((BaseModule) VideoModule.this).mCamera2Device.getFlashMode(), 960, 10, CameraSettings.is960WatermarkOn(VideoModule.this.getModuleIndex()));
                        }
                    }
                }
                singleEmitter.onSuccess(Boolean.TRUE);
            }
        }).subscribeOn(GlobalConstant.sCameraSetupScheduler).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Boolean>() {
            /* class com.android.camera.module.VideoModule.AnonymousClass8 */

            public void accept(Boolean bool) throws Exception {
                VideoModule.this.onMediaRecorderReleased();
            }
        });
    }

    private void trackProVideoInfo() {
        CameraStatUtils.trackRecordVideoInProMode(getManualValue(CameraSettings.KEY_QC_PRO_VIDEO_WHITEBALANCE_VALUE, getString(R.string.pref_camera_whitebalance_default)), getManualValue(CameraSettings.KEY_QC_PRO_VIDEO_EXPOSURETIME, getString(R.string.pref_camera_exposuretime_default)), getManualValue(CameraSettings.KEY_QC_PRO_VIDEO_ISO, getString(R.string.pref_camera_iso_default)), getManualValue(CameraSettings.KEY_QC_PRO_VIDEO_EXPOSURE_VALUE, getString(R.string.pref_camera_iso_default)), ((BaseModule) this).mModuleIndex, getActualCameraId());
    }

    private void updateAutoZoomMode() {
        boolean isAutoZoomEnabled = CameraSettings.isAutoZoomEnabled(((BaseModule) this).mModuleIndex);
        if (((BaseModule) this).mCamera2Device != null && isAlive()) {
            ((BaseModule) this).mCamera2Device.setAutoZoomMode(isAutoZoomEnabled ? 1 : 0);
            if (isAutoZoomEnabled) {
                ((BaseModule) this).mCamera2Device.setAutoZoomScaleOffset(0.0f);
            }
        }
    }

    private void updateBokeh() {
        ComponentConfigBokeh componentBokeh = DataRepository.dataItemConfig().getComponentBokeh();
        if (!ModuleManager.isPortraitModule() && !"on".equals(componentBokeh.getComponentValue(((BaseModule) this).mModuleIndex))) {
            ((BaseModule) this).mCamera2Device.setMiBokeh(false);
            ((BaseModule) this).mCamera2Device.setRearBokehEnable(false);
        } else if (isSingleCamera()) {
            ((BaseModule) this).mCamera2Device.setMiBokeh(true);
            ((BaseModule) this).mCamera2Device.setRearBokehEnable(false);
        } else {
            ((BaseModule) this).mCamera2Device.setMiBokeh(false);
            ((BaseModule) this).mCamera2Device.setRearBokehEnable(true);
        }
    }

    private void updateCinematicVideo() {
        ((BaseModule) this).mCamera2Device.setCinematicVideoEnabled(CameraSettings.isCinematicAspectRatioEnabled(((BaseModule) this).mModuleIndex));
    }

    private void updateEvValue() {
        String manualValue = getManualValue(CameraSettings.KEY_QC_PRO_VIDEO_EXPOSURE_VALUE, "0");
        ((BaseModule) this).mEvValue = (int) (Float.parseFloat(manualValue) / ((BaseModule) this).mCameraCapabilities.getExposureCompensationStep());
        ((BaseModule) this).mEvState = 3;
        setEvValue();
    }

    private void updateExposureTime() {
        ((BaseModule) this).mCamera2Device.setExposureTime(Long.parseLong(getManualValue(CameraSettings.KEY_QC_PRO_VIDEO_EXPOSURETIME, getString(R.string.pref_camera_exposuretime_default))));
    }

    private void updateFilter() {
        EffectController.getInstance().setEffect(CameraSettings.getShaderEffect());
    }

    private void updateFpsRange() {
        if (isDeviceAlive()) {
            if (ModuleManager.isVideoNewSlowMotion()) {
                String str = VideoBase.TAG;
                Log.d(str, "mHfrFPSLower = " + this.mHfrFPSLower + ", mHfrFPSUpper = " + this.mHfrFPSUpper);
                ((BaseModule) this).mCamera2Device.setVideoFpsRange(new Range<>(Integer.valueOf(this.mHfrFPSLower), Integer.valueOf(this.mHfrFPSUpper)));
                return;
            }
            Range<Integer>[] supportedFpsRange = ((BaseModule) this).mCameraCapabilities.getSupportedFpsRange();
            int i = 0;
            Range<Integer> range = supportedFpsRange[0];
            int length = supportedFpsRange.length;
            while (true) {
                if (i >= length) {
                    break;
                }
                Range<Integer> range2 = supportedFpsRange[i];
                int hSRValue = getHSRValue();
                if (hSRValue == 60) {
                    range = new Range<>(Integer.valueOf(hSRValue), Integer.valueOf(hSRValue));
                    break;
                }
                if (range.getUpper().intValue() < range2.getUpper().intValue() || (range.getUpper() == range2.getUpper() && range.getLower().intValue() < range2.getLower().intValue())) {
                    range = range2;
                }
                i++;
            }
            String str2 = VideoBase.TAG;
            Log.d(str2, "bestRange = " + range);
            ((BaseModule) this).mCamera2Device.setFpsRange(range);
            ((BaseModule) this).mCamera2Device.setVideoFpsRange(range);
        }
    }

    private void updateFrontMirror() {
        ((BaseModule) this).mCamera2Device.setFrontMirror(isFrontCamera() && !b.hl() && DataRepository.dataItemFeature().ge() && CameraSettings.isFrontMirror());
    }

    private void updateHFRDeflicker() {
        if (DataRepository.dataItemFeature().ad() && isFPS960()) {
            ((BaseModule) this).mCamera2Device.setHFRDeflickerEnable(true);
        }
    }

    private void updateHfrFPSRange(Size size, int i) {
        Range<Integer>[] supportedHighSpeedVideoFPSRange = ((BaseModule) this).mCameraCapabilities.getSupportedHighSpeedVideoFPSRange(size);
        Range<Integer> range = null;
        for (Range<Integer> range2 : supportedHighSpeedVideoFPSRange) {
            if (range2.getUpper().intValue() == i && (range == null || range.getLower().intValue() < range2.getLower().intValue())) {
                range = range2;
            }
        }
        this.mHfrFPSLower = range.getLower().intValue();
        this.mHfrFPSUpper = range.getUpper().intValue();
    }

    private void updateHistogramStats() {
        ((BaseModule) this).mCamera2Device.setHistogramStatsEnabled(CameraSettings.isProVideoHistogramOpen(((BaseModule) this).mModuleIndex));
    }

    private void updateISO() {
        String string = getString(R.string.pref_camera_iso_default);
        String manualValue = getManualValue(CameraSettings.KEY_QC_PRO_VIDEO_ISO, string);
        if (manualValue == null || manualValue.equals(string)) {
            ((BaseModule) this).mCamera2Device.setISO(0);
        } else {
            ((BaseModule) this).mCamera2Device.setISO(Math.min(Util.parseInt(manualValue, 0), ((BaseModule) this).mCameraCapabilities.getMaxIso()));
        }
    }

    private void updateMacroMode() {
        ((BaseModule) this).mCamera2Device.setMacroMode(CameraSettings.isMacroModeEnabled(((BaseModule) this).mModuleIndex));
    }

    private void updateMutexModePreference() {
        if ("on".equals(DataRepository.dataItemConfig().getComponentHdr().getComponentValue(((BaseModule) this).mModuleIndex))) {
            ((BaseModule) this).mMutexModePicker.setMutexMode(2);
        }
    }

    private void updatePictureAndPreviewSize() {
        int i;
        int i2;
        CamcorderProfile camcorderProfile = this.mProfile;
        double d2 = ((double) camcorderProfile.videoFrameWidth) / ((double) camcorderProfile.videoFrameHeight);
        List<CameraSize> supportedOutputSizeWithAssignedMode = ((BaseModule) this).mCameraCapabilities.getSupportedOutputSizeWithAssignedMode(MediaRecorder.class);
        CamcorderProfile camcorderProfile2 = this.mProfile;
        CameraSize optimalVideoSnapshotPictureSize = Util.getOptimalVideoSnapshotPictureSize(supportedOutputSizeWithAssignedMode, d2, camcorderProfile2.videoFrameWidth, camcorderProfile2.videoFrameHeight);
        ((VideoBase) this).mVideoSize = optimalVideoSnapshotPictureSize;
        Log.d(VideoBase.TAG, "videoSize: " + optimalVideoSnapshotPictureSize.toString());
        int i3 = Integer.MAX_VALUE;
        if (b.qm()) {
            i3 = optimalVideoSnapshotPictureSize.width;
            i = optimalVideoSnapshotPictureSize.height;
        } else {
            i = Integer.MAX_VALUE;
        }
        ((BaseModule) this).mPictureSize = Util.getOptimalVideoSnapshotPictureSize(((BaseModule) this).mCameraCapabilities.getSupportedOutputSizeWithAssignedMode(256), d2, i3, i);
        Log.d(VideoBase.TAG, "pictureSize: " + ((BaseModule) this).mPictureSize);
        int i4 = optimalVideoSnapshotPictureSize.width;
        if (i4 > Util.sWindowHeight || i4 < 720) {
            i4 = Util.sWindowHeight;
            i2 = Util.sWindowWidth;
        } else {
            i2 = optimalVideoSnapshotPictureSize.height;
        }
        ((BaseModule) this).mPreviewSize = Util.getOptimalVideoSnapshotPictureSize(((BaseModule) this).mCameraCapabilities.getSupportedOutputSizeWithAssignedMode(SurfaceTexture.class), d2, i4, i2);
        CameraSize cameraSize = ((BaseModule) this).mPreviewSize;
        updateCameraScreenNailSize(cameraSize.width, cameraSize.height);
    }

    private void updateUltraWideLDC() {
        ((BaseModule) this).mCamera2Device.setUltraWideLDC(shouldApplyUltraWideLDC());
    }

    private void updateVideoBokeh() {
        float videoBokehRatio = CameraSettings.getVideoBokehRatio();
        if (isFrontCamera()) {
            String str = VideoBase.TAG;
            Log.i(str, "frontVideoBokeh: " + videoBokehRatio);
            ((BaseModule) this).mCamera2Device.setVideoBokehLevelFront(videoBokehRatio);
            return;
        }
        int i = (int) videoBokehRatio;
        String str2 = VideoBase.TAG;
        Log.i(str2, "backVideoBokeh: " + i);
        ((BaseModule) this).mCamera2Device.setVideoBokehLevelBack(i);
    }

    private void updateVideoColorRetention() {
        int shaderEffect = CameraSettings.getShaderEffect();
        boolean isFrontCamera = isFrontCamera();
        if (shaderEffect == 200) {
            if (isFrontCamera) {
                ((BaseModule) this).mCamera2Device.setVideoFilterColorRetentionFront(true);
            } else {
                ((BaseModule) this).mCamera2Device.setVideoFilterColorRetentionBack(true);
            }
        } else if (isFrontCamera) {
            ((BaseModule) this).mCamera2Device.setVideoFilterColorRetentionFront(false);
        } else {
            ((BaseModule) this).mCamera2Device.setVideoFilterColorRetentionBack(false);
        }
    }

    private void updateVideoFilter() {
        int shaderEffect = CameraSettings.getShaderEffect();
        if (shaderEffect == 200) {
            shaderEffect = FilterInfo.FILTER_ID_NONE;
        }
        if (shaderEffect == FilterInfo.FILTER_ID_NONE) {
            shaderEffect = 0;
        }
        ((BaseModule) this).mCamera2Device.setVideoFilterId(shaderEffect);
    }

    private void updateVideoLog() {
        ((BaseModule) this).mCamera2Device.setVideoLogEnabled(CameraSettings.isProVideoLogOpen(((BaseModule) this).mModuleIndex));
    }

    private void updateVideoStabilization() {
        if (isDeviceAlive()) {
            if (needDisableEISAndOIS()) {
                ((BaseModule) this).mCamera2Device.setEnableEIS(false);
                ((BaseModule) this).mCamera2Device.setEnableOIS(false);
                ((BaseModule) this).mActivity.getCameraScreenNail().setVideoStabilizationCropped(false);
            } else if (isEisOn()) {
                Log.d(VideoBase.TAG, "videoStabilization: EIS");
                ((BaseModule) this).mCamera2Device.setEnableOIS(false);
                ((BaseModule) this).mCamera2Device.setEnableEIS(true);
                if (!((BaseModule) this).mCameraCapabilities.isEISPreviewSupported()) {
                    ((BaseModule) this).mActivity.getCameraScreenNail().setVideoStabilizationCropped(true);
                }
            } else {
                Log.d(VideoBase.TAG, "videoStabilization: OIS");
                ((BaseModule) this).mCamera2Device.setEnableEIS(false);
                ((BaseModule) this).mCamera2Device.setEnableOIS(true);
                ((BaseModule) this).mActivity.getCameraScreenNail().setVideoStabilizationCropped(false);
            }
        }
    }

    private void updateVideoSubtitle() {
        this.mIsSubtitleSupported = DataRepository.dataItemRunning().getComponentRunningSubtitle().isEnabled(((BaseModule) this).mModuleIndex);
    }

    private void updateVideoTag() {
        this.mIsVideoTagSupported = !CameraSettings.getVideoTagSettingNeedRemove(((BaseModule) this).mModuleIndex, isFrontCamera()) && CameraSettings.isVideoTagOn();
    }

    private void updateWhiteBalance() {
        setAWBMode(getManualValue(CameraSettings.KEY_PRO_VIDEO_WHITE_BALANCE, "1"));
    }

    public /* synthetic */ void Vf() {
        RotateTextToast.getInstance(((BaseModule) this).mActivity).show(R.string.time_lapse_error, ((BaseModule) this).mOrientation);
    }

    public /* synthetic */ void Wf() {
        ((VideoBase) this).mFocusManager.cancelFocus();
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
                case 7:
                case 8:
                case 17:
                case 18:
                case 21:
                case 22:
                case 23:
                case 26:
                case 27:
                case 28:
                case 32:
                case 36:
                case 38:
                case 39:
                case 41:
                case 43:
                case 44:
                case 45:
                case 49:
                case 54:
                case 56:
                case 57:
                case 59:
                case 61:
                case 62:
                case 64:
                case 70:
                case 71:
                case 72:
                case 73:
                case 74:
                default:
                    if (!BaseModule.DEBUG) {
                        Log.w(VideoBase.TAG, "no consumer for this updateType: " + i);
                        break;
                    } else {
                        throw new RuntimeException("no consumer for this updateType: " + i);
                    }
                case 5:
                    updateFace();
                    break;
                case 6:
                    updateWhiteBalance();
                    break;
                case 9:
                    updateAntiBanding(DataRepository.dataItemFeature().c_0x44() ? "0" : CameraSettings.getAntiBanding());
                    break;
                case 10:
                    updateFlashPreference();
                    break;
                case 11:
                case 20:
                case 30:
                case 34:
                case 42:
                case 46:
                case 48:
                case 50:
                    break;
                case 12:
                    setEvValue();
                    break;
                case 13:
                    updateBeauty();
                    break;
                case 14:
                    updateVideoFocusMode();
                    break;
                case 15:
                    updateISO();
                    break;
                case 16:
                    updateExposureTime();
                    break;
                case 19:
                    updateFpsRange();
                    break;
                case 24:
                    applyZoomRatio();
                    break;
                case 25:
                    focusCenter();
                    break;
                case 29:
                    updateExposureMeteringMode();
                    break;
                case 31:
                    updateVideoStabilization();
                    break;
                case 33:
                    Camera2Proxy camera2Proxy = ((BaseModule) this).mCamera2Device;
                    if (camera2Proxy == null) {
                        break;
                    } else {
                        camera2Proxy.setVideoSnapshotSize(((BaseModule) this).mPictureSize);
                        break;
                    }
                case 35:
                    updateDeviceOrientation();
                    break;
                case 37:
                    updateBokeh();
                    break;
                case 40:
                    updateFrontMirror();
                    break;
                case 47:
                    updateUltraWideLDC();
                    break;
                case 51:
                    updateAutoZoomMode();
                    break;
                case 52:
                    updateMacroMode();
                    break;
                case 53:
                    updateHFRDeflicker();
                    break;
                case 55:
                    updateModuleRelated();
                    break;
                case 58:
                    updateBackSoftLightPreference();
                    break;
                case 60:
                    updateCinematicVideo();
                    break;
                case 63:
                    updateEvValue();
                    break;
                case 65:
                    updateVideoLog();
                    break;
                case 66:
                    updateThermalLevel();
                    break;
                case 67:
                    updateVideoBokeh();
                    break;
                case 68:
                    updateVideoFilter();
                    break;
                case 69:
                    updateVideoColorRetention();
                    break;
                case 75:
                    updateHistogramStats();
                    break;
            }
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.VideoBase
    public void doLaterReleaseIfNeed() {
        super.doLaterReleaseIfNeed();
        if (isFPS960() && !((BaseModule) this).mActivity.isActivityPaused()) {
            if (isTextureExpired()) {
                Log.d(VideoBase.TAG, "doLaterReleaseIfNeed: restartModule...");
                restartModule();
                return;
            }
            Log.d(VideoBase.TAG, "doLaterReleaseIfNeed: dismissBlurCover...");
            ((BaseModule) this).mActivity.dismissBlurCover();
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.VideoBase
    public boolean enableFaceDetection() {
        if (!DataRepository.dataItemFeature().je() || !isBackCamera()) {
            return (!ModuleManager.isVideoNewSlowMotion() || !isBackCamera()) && DataRepository.dataItemGlobal().getBoolean("pref_camera_facedetection_key", getResources().getBoolean(R.bool.pref_camera_facedetection_default));
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public CamcorderProfile fetchProfile(int i, int i2) {
        return CamcorderProfile.get(i, i2);
    }

    /* access modifiers changed from: protected */
    public int getNormalVideoFrameRate() {
        CamcorderProfile camcorderProfile;
        if (DataRepository.dataItemFeature().c_0x44() || (camcorderProfile = this.mProfile) == null) {
            return 30;
        }
        return camcorderProfile.videoFrameRate;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.BaseModule
    public int getOperatingMode() {
        int i = 32772;
        boolean z = false;
        if (!isFrontCamera()) {
            if (needChooseVideoBeauty(((VideoBase) this).mBeautyValues)) {
                i = 32777;
            } else if (this.mQuality == 0) {
                i = 0;
            } else if (CameraSettings.isSuperEISEnabled(((BaseModule) this).mModuleIndex)) {
                i = CameraCapabilities.SESSION_OPERATION_MODE_VIDEO_SUPEREIS;
            } else if (!isEisOn()) {
                i = 61456;
            }
            if (((BaseModule) this).mCameraCapabilities.isFovcSupported()) {
                if (i != 0) {
                    z = true;
                }
                this.mFovcEnabled = z;
            }
            if (getHSRValue() == 60) {
                i = 32828;
            }
            if (CameraSettings.isAutoZoomEnabled(((BaseModule) this).mModuleIndex)) {
                i = CameraCapabilities.SESSION_OPERATION_MODE_AUTO_ZOOM;
            }
        } else if (CameraSettings.isVideoBokehOn()) {
            i = 32770;
        } else if (!isEisOn()) {
            i = ((BaseModule) this).mCameraCapabilities.isSupportVideoBeauty() ? 32777 : 0;
        }
        Log.d(VideoBase.TAG, "getOperatingMode(): " + Integer.toHexString(i));
        return i;
    }

    @Override // com.android.camera.module.BaseModule, com.android.camera.module.Module
    public List<String> getSupportedSettingKeys() {
        ArrayList arrayList = new ArrayList();
        if (isBackCamera()) {
            arrayList.add("pref_video_speed_fast_key");
        }
        return arrayList;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.VideoBase
    public boolean isAEAFLockSupported() {
        return !((VideoBase) this).mMediaRecorderRecording || !isFPS960();
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.BaseModule
    public boolean isCameraSwitchingDuringZoomingAllowed() {
        int i;
        return HybridZoomingSystem.IS_3_OR_MORE_SAT && ((i = ((BaseModule) this).mModuleIndex) == 162 || i == 169) && !CameraSettings.isMacroModeEnabled(((BaseModule) this).mModuleIndex) && !CameraSettings.isSuperEISEnabled(((BaseModule) this).mModuleIndex) && isBackCamera() && !((VideoBase) this).mMediaRecorderRecording && !((VideoBase) this).mMediaRecorderRecordingPaused && (DataRepository.dataItemGlobal().isNormalIntent() || !((BaseModule) this).mCameraCapabilities.isSupportLightTripartite());
    }

    @Override // com.android.camera.module.BaseModule, com.android.camera.ui.FocusView.ExposureViewListener
    public boolean isNeedHapticFeedback() {
        return !((VideoBase) this).mMediaRecorderRecording || ((VideoBase) this).mMediaRecorderRecordingPaused;
    }

    @Override // com.android.camera.module.BaseModule
    public boolean isNeedMute() {
        return ((BaseModule) this).mObjectTrackingStarted || (((VideoBase) this).mMediaRecorderRecording && !((VideoBase) this).mMediaRecorderRecordingPaused);
    }

    @Override // com.android.camera.module.BaseModule, com.android.camera.module.Module
    public boolean isPostProcessing() {
        return this.mMediaRecorderPostProcessing;
    }

    /* access modifiers changed from: protected */
    public boolean isShowHFRDuration() {
        return true;
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.VideoBase
    public boolean isUnInterruptable() {
        if (!super.isUnInterruptable() && !isNormalMode() && this.mMediaRecorder != null && this.mMediaRecorderWorking) {
            ((BaseModule) this).mUnInterruptableReason = "recorder release";
        }
        return ((BaseModule) this).mUnInterruptableReason != null;
    }

    @Override // com.android.camera.module.BaseModule, com.android.camera.module.VideoBase
    public boolean isZoomEnabled() {
        if ((!isFPS960() || !((VideoBase) this).mMediaRecorderRecording) && !CameraSettings.isAutoZoomEnabled(((BaseModule) this).mModuleIndex) && !CameraSettings.isSuperEISEnabled(((BaseModule) this).mModuleIndex)) {
            return super.isZoomEnabled();
        }
        return false;
    }

    @Override // com.android.camera.module.BaseModule, com.android.camera.module.Module, com.android.camera.module.VideoBase
    public boolean onBackPressed() {
        if (!isFrameAvailable()) {
            return false;
        }
        if (((BaseModule) this).mPaused || ((BaseModule) this).mActivity.isActivityPaused()) {
            return true;
        }
        if (((VideoBase) this).mStereoSwitchThread != null) {
            return false;
        }
        if (isFPS960() && this.mMediaRecorderPostProcessing) {
            return true;
        }
        if (((VideoBase) this).mMediaRecorderRecording) {
            long currentTimeMillis = System.currentTimeMillis();
            if (currentTimeMillis - ((BaseModule) this).mLastBackPressedTime > 3000) {
                ((BaseModule) this).mLastBackPressedTime = currentTimeMillis;
                ToastUtils.showToast((Context) ((BaseModule) this).mActivity, (int) R.string.record_back_pressed_hint, true);
            } else {
                stopVideoRecording(true, false);
            }
            return true;
        } else if (!this.isAutoZoomTracking.get()) {
            return super.onBackPressed();
        } else {
            stopTracking(0);
            return true;
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.BaseModule, com.android.camera.module.VideoBase
    public void onCameraOpened() {
        super.onCameraOpened();
        updateBeauty();
        updateVideoSubtitle();
        updateVideoTag();
        readVideoPreferences();
        initializeFocusManager();
        updatePreferenceTrampoline(UpdateConstant.VIDEO_TYPES_INIT);
        if (!initializeRecorder(false)) {
            startPreviewSession();
        } else if (ModuleManager.isVideoNewSlowMotion()) {
            startHighSpeedRecordSession();
        } else {
            startRecordSession();
        }
        initAutoZoom();
        initHistogramEmitter();
    }

    @Override // com.android.camera.module.BaseModule, com.android.camera.module.Module
    public void onCreate(int i, int i2) {
        super.onCreate(i, i2);
        setCaptureIntent(((BaseModule) this).mActivity.getCameraIntentManager().isVideoCaptureIntent());
        EffectController.getInstance().setEffect(FilterInfo.FILTER_ID_NONE);
        ((BaseModule) this).mActivity.getSensorStateManager().setSensorStateListener(((VideoBase) this).mSensorStateListener);
        this.mQuickCapture = ((BaseModule) this).mActivity.getCameraIntentManager().isQuickCapture().booleanValue();
        enableCameraControls(false);
        ((VideoBase) this).mTelephonyManager = (TelephonyManager) ((BaseModule) this).mActivity.getSystemService("phone");
        ((VideoBase) this).mVideoFocusMode = AutoFocus.LEGACY_CONTINUOUS_VIDEO;
        this.mAutoZoomViewProtocol = (ModeProtocol.AutoZoomViewProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(214);
        this.mTopAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        this.mSubtitleRecording = (ModeProtocol.SubtitleRecording) ModeCoordinatorImpl.getInstance().getAttachProtocol(231);
        initRecorderSurface();
        onCameraOpened();
    }

    @Override // com.android.camera.module.BaseModule, com.android.camera.module.Module
    public void onDestroy() {
        super.onDestroy();
        releaseRecorderSurface();
        ((BaseModule) this).mHandler.sendEmptyMessage(45);
    }

    public void onError(MediaRecorder mediaRecorder, int i, int i2) {
        String str = VideoBase.TAG;
        Log.e(str, "MediaRecorder error. what=" + i + " extra=" + i2);
        if (i == 1 || i == 100) {
            if (((VideoBase) this).mMediaRecorderRecording) {
                stopVideoRecording(true, false);
            }
            ((BaseModule) this).mActivity.getScreenHint().updateHint();
        }
    }

    @Override // com.android.camera.module.BaseModule, com.android.camera.module.Module
    public boolean onGestureTrack(RectF rectF, boolean z) {
        if (((VideoBase) this).mInStartingFocusRecording || !isBackCamera() || !b.cm() || CameraSettings.is4KHigherVideoQuality(this.mQuality) || isCaptureIntent()) {
            return false;
        }
        return initializeObjectTrack(rectF, z);
    }

    @Override // com.android.camera.module.BaseModule
    public void onGradienterSwitched(boolean z) {
        ((BaseModule) this).mActivity.getSensorStateManager().setGradienterEnabled(z);
        updatePreferenceTrampoline(2, 5);
    }

    public void onInfo(MediaRecorder mediaRecorder, int i, int i2) {
        if (!((VideoBase) this).mMediaRecorderRecording) {
            Log.w(VideoBase.TAG, "onInfo: ignore event " + i);
            return;
        }
        switch (i) {
            case MediaPlayer2.MEDIA_INFO_BAD_INTERLEAVING:
                stopVideoRecording(true, false);
                return;
            case MediaPlayer2.MEDIA_INFO_NOT_SEEKABLE:
                Log.w(VideoBase.TAG, "reached max size. fileNumber=" + this.mCurrentFileNumber);
                stopVideoRecording(true, false);
                if (!((BaseModule) this).mActivity.getScreenHint().isScreenHintVisible()) {
                    Toast.makeText(((BaseModule) this).mActivity, (int) R.string.video_reach_size_limit, 1).show();
                    return;
                }
                return;
            case 802:
                boolean isSplitWhenReachMaxSize = isSplitWhenReachMaxSize();
                Log.d(VideoBase.TAG, "max file size is approaching. split: " + isSplitWhenReachMaxSize);
                if (isSplitWhenReachMaxSize) {
                    this.mCurrentFileNumber++;
                    ContentValues genContentValues = genContentValues(((VideoBase) this).mOutputFormat, this.mCurrentFileNumber, this.mSlowModeFps, is8KCamcorder(), true);
                    String asString = genContentValues.getAsString("_data");
                    Log.d(VideoBase.TAG, "nextVideoPath: " + asString);
                    if (setNextOutputFile(asString)) {
                        this.mNextVideoValues = genContentValues;
                        this.mNextVideoFileName = asString;
                        return;
                    }
                    return;
                }
                return;
            case 803:
                Log.d(VideoBase.TAG, "next output file started");
                onMaxFileSizeReached();
                ((VideoBase) this).mCurrentVideoValues = this.mNextVideoValues;
                ((VideoBase) this).mCurrentVideoFilename = this.mNextVideoFileName;
                this.mNextVideoValues = null;
                this.mNextVideoFileName = null;
                return;
            default:
                Log.w(VideoBase.TAG, "onInfo what : " + i);
                return;
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.BaseModule
    public boolean onInterceptZoomingEvent(float f2, float f3, int i) {
        int i2;
        if (!DataRepository.dataItemFeature().sf() || !DataRepository.dataItemConfig().getComponentConfigVideoQuality().supportVideoSATForVideoQuality(getModuleIndex()) || CameraSettings.isVideoQuality8KOpen(getModuleIndex()) || !HybridZoomingSystem.IS_3_OR_MORE_SAT || (((i2 = ((BaseModule) this).mModuleIndex) != 162 && i2 != 169) || CameraSettings.isMacroModeEnabled(((BaseModule) this).mModuleIndex) || CameraSettings.isSuperEISEnabled(((BaseModule) this).mModuleIndex) || !isBackCamera())) {
            return super.onInterceptZoomingEvent(f2, f3, i);
        }
        return false;
    }

    @Override // com.android.camera.module.BaseModule, com.android.camera.module.Module
    public void onNewUriArrived(Uri uri, String str) {
        String str2;
        ModeProtocol.MainContentProtocol mainContentProtocol;
        ModeProtocol.SubtitleRecording subtitleRecording;
        super.onNewUriArrived(uri, str);
        if (str.contains("VID")) {
            ArrayList<String> arrayList = new ArrayList<>();
            if (!this.mIsSubtitleSupported || (subtitleRecording = this.mSubtitleRecording) == null) {
                str2 = "";
            } else {
                str2 = subtitleRecording.getSubtitleContent();
                if (!TextUtils.isEmpty(str2)) {
                    arrayList.add("com.xiaomi.support_subtitle");
                } else {
                    Log.e(VideoBase.TAG, "video subtitle is empty ");
                }
            }
            if (this.mIsVideoTagSupported && (mainContentProtocol = ((BaseModule) this).mMainProtocol) != null) {
                str2 = mainContentProtocol.getVideoTagContent();
                if (!TextUtils.isEmpty(str2)) {
                    arrayList.add("com.xiaomi.support_tags");
                } else {
                    Log.e(VideoBase.TAG, "video tag is empty ");
                }
            }
            if (CameraSettings.isProVideoLogOpen(((BaseModule) this).mModuleIndex)) {
                arrayList.add("com.xiaomi.record_log");
            }
            if (CameraSettings.isCinematicAspectRatioEnabled(((BaseModule) this).mModuleIndex)) {
                arrayList.add("com.xiaomi.record_mimovie");
            }
            if (arrayList.size() != 0) {
                ((BaseModule) this).mActivity.getImageSaver().addVideoTag(this.mVideoTagFileName, str2, arrayList, this.mIsSubtitleSupported);
            }
        }
    }

    @Override // com.android.camera.ui.ObjectView.ObjectViewListener
    public void onObjectStable() {
    }

    @Override // com.android.camera.module.BaseModule, com.android.camera.module.Module, com.android.camera.module.VideoBase
    public void onPause() {
        if (((BaseModule) this).mCamera2Device != null && (this.mFovcEnabled || (((BaseModule) this).mCameraCapabilities.isEISPreviewSupported() && isEisOn()))) {
            ((BaseModule) this).mCamera2Device.notifyVideoStreamEnd();
        }
        super.onPause();
        waitStereoSwitchThread();
        stopObjectTracking(false);
        releaseResources();
        closeVideoFileDescriptor();
        ((BaseModule) this).mActivity.getSensorStateManager().reset();
        stopFaceDetection(true);
        resetScreenOn();
        ((BaseModule) this).mHandler.removeCallbacksAndMessages(null);
        FocusManager2 focusManager2 = ((VideoBase) this).mFocusManager;
        if (focusManager2 != null) {
            focusManager2.removeMessages();
        }
        if (!((BaseModule) this).mActivity.isActivityPaused()) {
            PopupManager.getInstance(((BaseModule) this).mActivity).notifyShowPopup(null, 1);
        }
    }

    public void onPauseButtonClick() {
        ModeProtocol.MainContentProtocol mainContentProtocol;
        ModeProtocol.SubtitleRecording subtitleRecording;
        String str = VideoBase.TAG;
        Log.d(str, "onPauseButtonClick: isRecordingPaused=" + ((VideoBase) this).mMediaRecorderRecordingPaused + " isRecording=" + ((VideoBase) this).mMediaRecorderRecording);
        long currentTimeMillis = System.currentTimeMillis();
        if (((VideoBase) this).mMediaRecorderRecording && currentTimeMillis - this.mPauseClickTime >= 500) {
            this.mPauseClickTime = currentTimeMillis;
            ModeProtocol.RecordState recordState = (ModeProtocol.RecordState) ModeCoordinatorImpl.getInstance().getAttachProtocol(212);
            if (((VideoBase) this).mMediaRecorderRecordingPaused) {
                try {
                    if (Build.VERSION.SDK_INT >= 24) {
                        CompatibilityUtils.resumeMediaRecorder(this.mMediaRecorder);
                    } else {
                        this.mMediaRecorder.start();
                    }
                    ((VideoBase) this).mRecordingStartTime = SystemClock.uptimeMillis() - this.mVideoRecordedDuration;
                    this.mVideoRecordedDuration = 0;
                    ((VideoBase) this).mMediaRecorderRecordingPaused = false;
                    ((BaseModule) this).mHandler.removeMessages(42);
                    this.mRecordingTime = "";
                    updateRecordingTime();
                    if (this.mIsSubtitleSupported && this.mSubtitleRecording != null) {
                        this.mSubtitleRecording.handleSubtitleRecordingResume();
                    }
                    if (this.mIsVideoTagSupported && ((BaseModule) this).mMainProtocol != null) {
                        ((BaseModule) this).mMainProtocol.processingResume();
                    }
                    recordState.onResume();
                } catch (IllegalStateException e2) {
                    Log.e(VideoBase.TAG, "failed to resume media recorder", e2);
                    releaseMediaRecorder();
                    recordState.onFailed();
                }
            } else {
                pauseVideoRecording();
                CameraStatUtils.trackPauseVideoRecording(isFrontCamera());
                if (this.mIsSubtitleSupported && (subtitleRecording = this.mSubtitleRecording) != null) {
                    subtitleRecording.handleSubtitleRecordingPause();
                }
                if (this.mIsVideoTagSupported && (mainContentProtocol = ((BaseModule) this).mMainProtocol) != null) {
                    mainContentProtocol.processingPause();
                }
                recordState.onPause();
            }
            Log.d(VideoBase.TAG, "onPauseButtonClick");
        }
    }

    @Override // com.android.camera.module.BaseModule
    public void onPreviewLayoutChanged(Rect rect) {
        String str = VideoBase.TAG;
        Log.v(str, "onPreviewLayoutChanged: " + rect);
        ((BaseModule) this).mActivity.onLayoutChange(rect);
        if (((VideoBase) this).mFocusManager != null && ((BaseModule) this).mActivity.getCameraScreenNail() != null) {
            ((BaseModule) this).mActivity.getCameraScreenNail().setDisplayArea(rect);
            ((VideoBase) this).mFocusManager.setRenderSize(((BaseModule) this).mActivity.getCameraScreenNail().getRenderWidth(), ((BaseModule) this).mActivity.getCameraScreenNail().getRenderHeight());
            ((VideoBase) this).mFocusManager.setPreviewSize(rect.width(), rect.height());
        }
    }

    @Override // com.android.camera.module.BaseModule, com.android.camera2.Camera2Proxy.CameraMetaDataCallback, com.android.camera.module.VideoBase
    public void onPreviewMetaDataUpdate(CaptureResult captureResult) {
        super.onPreviewMetaDataUpdate(captureResult);
        if (this.isAutoZoomTracking.get()) {
            this.mAutoZoomEmitter.onNext(captureResult);
        }
        FlowableEmitter<CaptureResult> flowableEmitter = this.mHistogramEmitter;
        if (flowableEmitter != null) {
            flowableEmitter.onNext(captureResult);
        }
    }

    @Override // com.android.camera2.Camera2Proxy.CameraPreviewCallback, com.android.camera.module.VideoBase
    public void onPreviewSessionFailed(CameraCaptureSession cameraCaptureSession) {
        super.onPreviewSessionFailed(cameraCaptureSession);
        enableCameraControls(true);
    }

    @Override // com.android.camera2.Camera2Proxy.CameraPreviewCallback, com.android.camera.module.VideoBase
    public void onPreviewSessionSuccess(CameraCaptureSession cameraCaptureSession) {
        if (((BaseModule) this).mCamera2Device != null) {
            super.onPreviewSessionSuccess(cameraCaptureSession);
            if (!isCreated()) {
                Log.w(VideoBase.TAG, "onPreviewSessionSuccess: module is not ready");
                enableCameraControls(true);
                return;
            }
            Log.d(VideoBase.TAG, "onPreviewSessionSuccess: session=" + cameraCaptureSession);
            boolean z = false;
            ((VideoBase) this).mFaceDetectionEnabled = false;
            CameraConfigs cameraConfigs = ((BaseModule) this).mCamera2Device.getCameraConfigs();
            if (((BaseModule) this).mCameraCapabilities.isSupportVideoBokehAdjust() && isVideoBokehEnabled()) {
                z = true;
            }
            cameraConfigs.setVideoBokehEnabled(z);
            updatePreferenceInWorkThread(UpdateConstant.VIDEO_TYPES_ON_PREVIEW_SUCCESS);
            enableCameraControls(true);
            if (((BaseModule) this).mModuleIndex == 180) {
                updatePreferenceInWorkThread(UpdateConstant.CAMERA_TYPES_MANUALLY);
            }
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.VideoBase
    @MainThread
    public void onPreviewStart() {
        if (((VideoBase) this).mPreviewing) {
            ((BaseModule) this).mMainProtocol.initializeFocusView(this);
            updateMutexModePreference();
            onShutterButtonFocus(true, 3);
            startVideoRecordingIfNeeded();
        }
    }

    @Override // com.android.camera.module.BaseModule
    public void onSharedPreferenceChanged() {
        if (!((BaseModule) this).mPaused && !((BaseModule) this).mActivity.isActivityPaused() && ((BaseModule) this).mCamera2Device != null) {
            CamcorderProfile camcorderProfile = this.mProfile;
            int i = camcorderProfile.videoFrameWidth;
            int i2 = camcorderProfile.videoFrameHeight;
            readVideoPreferences();
            CamcorderProfile camcorderProfile2 = this.mProfile;
            if (camcorderProfile2.videoFrameWidth != i || camcorderProfile2.videoFrameHeight != i2) {
                Log.d(VideoBase.TAG, String.format(Locale.ENGLISH, "profile size changed [%d %d]->[%d %d]", Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(this.mProfile.videoFrameWidth), Integer.valueOf(this.mProfile.videoFrameHeight)));
                updatePreferenceTrampoline(1);
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.CameraAction, com.android.camera.module.VideoBase
    public void onShutterButtonClick(int i) {
        String str = VideoBase.TAG;
        Log.v(str, "onShutterButtonClick isRecording=" + ((VideoBase) this).mMediaRecorderRecording + " inStartingFocusRecording=" + ((VideoBase) this).mInStartingFocusRecording);
        ((VideoBase) this).mInStartingFocusRecording = false;
        ((BaseModule) this).mLastBackPressedTime = 0;
        if (isIgnoreTouchEvent()) {
            Log.w(VideoBase.TAG, "onShutterButtonClick: ignore touch event");
        } else if (isFrontCamera() && ((BaseModule) this).mActivity.isScreenSlideOff()) {
        } else {
            if (((VideoBase) this).mMediaRecorderRecording) {
                stopVideoRecording(true, false);
                return;
            }
            ModeProtocol.RecordState recordState = (ModeProtocol.RecordState) ModeCoordinatorImpl.getInstance().getAttachProtocol(212);
            recordState.onPrepare();
            if (!checkCallingState()) {
                recordState.onFailed();
                return;
            }
            ((BaseModule) this).mActivity.getScreenHint().updateHint();
            if (Storage.isLowStorageAtLastPoint()) {
                recordState.onFailed();
                return;
            }
            setTriggerMode(i);
            enableCameraControls(false);
            playCameraSound(2);
            if (((VideoBase) this).mFocusManager.canRecord()) {
                startVideoRecording();
                return;
            }
            Log.v(VideoBase.TAG, "wait for autoFocus");
            ((VideoBase) this).mInStartingFocusRecording = true;
            ((BaseModule) this).mHandler.sendEmptyMessageDelayed(55, 3000);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.CameraAction, com.android.camera.module.VideoBase
    public void onShutterButtonFocus(boolean z, int i) {
    }

    @Override // com.android.camera.module.BaseModule, com.android.camera.module.Module
    public void onSingleTapUp(int i, int i2, boolean z) {
        ModeProtocol.BackStack backStack;
        if (!((BaseModule) this).mPaused && ((BaseModule) this).mCamera2Device != null && !hasCameraException() && ((BaseModule) this).mCamera2Device.isSessionReady() && !this.mSnapshotInProgress && isInTapableRect(i, i2)) {
            if (!isFrameAvailable()) {
                Log.w(VideoBase.TAG, "onSingleTapUp: frame not available");
            } else if ((!isFrontCamera() || !((BaseModule) this).mActivity.isScreenSlideOff()) && (backStack = (ModeProtocol.BackStack) ModeCoordinatorImpl.getInstance().getAttachProtocol(171)) != null && !backStack.handleBackStackFromTapDown(i, i2) && !this.isAutoZoomTracking.get()) {
                if (((BaseModule) this).mObjectTrackingStarted) {
                    stopObjectTracking(false);
                }
                unlockAEAF();
                ((BaseModule) this).mMainProtocol.setFocusViewType(true);
                ((VideoBase) this).mTouchFocusStartingTime = System.currentTimeMillis();
                Point point = new Point(i, i2);
                mapTapCoordinate(point);
                ((VideoBase) this).mFocusManager.onSingleTapUp(point.x, point.y, z);
            }
        }
    }

    @Override // com.android.camera.module.BaseModule, com.android.camera.module.Module
    public void onStop() {
        super.onStop();
        ((BaseModule) this).mHandler.removeCallbacksAndMessages(null);
        exitSavePowerMode();
    }

    @Override // com.android.camera.protocol.ModeProtocol.AutoZoomModuleProtocol
    public void onTrackLost() {
        notifyAutoZoomStartUiHint();
    }

    @Override // com.android.camera.protocol.ModeProtocol.AutoZoomModuleProtocol
    public void onTrackLosting() {
        this.mTrackLostCount++;
    }

    @Override // com.android.camera.module.BaseModule, com.android.camera.module.Module, com.android.camera.module.VideoBase
    public void onUserInteraction() {
        super.onUserInteraction();
        if (((VideoBase) this).mMediaRecorderRecording) {
            keepPowerSave();
        }
    }

    @Override // com.android.camera2.Camera2Proxy.VideoRecordStateCallback
    public void onVideoRecordStopped() {
        String str = VideoBase.TAG;
        Log.d(str, "onVideoRecordStopped: " + this.mPendingStopRecorder);
        if (this.mPendingStopRecorder) {
            stopRecorder();
            startPreviewAfterRecord();
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.VideoBase
    public void onWaitStopCallbackTimeout() {
        stopRecorder();
        startPreviewAfterRecord();
    }

    @Override // com.android.camera.module.BaseModule
    public void onZoomingActionEnd(int i) {
        String str = VideoBase.TAG;
        Log.d(str, "onZoomingActionEnd(): " + ZoomingAction.toString(i) + " @hash: " + hashCode());
        ModeProtocol.DualController dualController = (ModeProtocol.DualController) ModeCoordinatorImpl.getInstance().getAttachProtocol(182);
        if (dualController != null && !dualController.isSlideVisible()) {
            if ((i != 2 && i != 1) || ((VideoBase) this).mMediaRecorderRecording || ((VideoBase) this).mMediaRecorderRecordingPaused) {
                return;
            }
            if (DataRepository.dataItemFeature().c_19039_0x0005_eq_2() || !CameraSettings.isMacroModeEnabled(((BaseModule) this).mModuleIndex)) {
                dualController.setImmersiveModeEnabled(false);
            }
        }
    }

    @Override // com.android.camera.module.BaseModule
    public void onZoomingActionStart(int i) {
        String str = VideoBase.TAG;
        Log.d(str, "onZoomingActionStart(): " + ZoomingAction.toString(i) + " @hash: " + hashCode());
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
    public void pauseMediaRecorder(MediaRecorder mediaRecorder) {
        CompatibilityUtils.pauseMediaRecorder(mediaRecorder);
    }

    @Override // com.android.camera.module.Module
    public void pausePreview() {
        Log.v(VideoBase.TAG, "pausePreview");
        ((VideoBase) this).mPreviewing = false;
        if (currentIsMainThread()) {
            stopObjectTracking(false);
        }
        Camera2Proxy camera2Proxy = ((BaseModule) this).mCamera2Device;
        if (camera2Proxy != null) {
            camera2Proxy.pausePreview();
        }
        FocusManager2 focusManager2 = ((VideoBase) this).mFocusManager;
        if (focusManager2 != null) {
            focusManager2.resetFocused();
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopConfigProtocol
    public void reShowMoon() {
        if (CameraSettings.isAutoZoomEnabled(((BaseModule) this).mModuleIndex)) {
            notifyAutoZoomStartUiHint();
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.VideoBase
    public void readVideoPreferences() {
        int preferVideoQuality = !ModuleManager.isVideoNewSlowMotion() ? CameraSettings.getPreferVideoQuality(((BaseModule) this).mActualCameraId, ((BaseModule) this).mModuleIndex) : 6;
        try {
            int videoQuality = getActivity().getCameraIntentManager().getVideoQuality();
            preferVideoQuality = videoQuality == 1 ? CameraSettings.getPreferVideoQuality(((BaseModule) this).mActualCameraId, ((BaseModule) this).mModuleIndex) : videoQuality == 0 ? videoQuality : CameraSettings.getPreferVideoQuality(String.valueOf(videoQuality), ((BaseModule) this).mActualCameraId, ((BaseModule) this).mModuleIndex);
        } catch (RuntimeException unused) {
        }
        this.mSpeed = DataRepository.dataItemRunning().getVideoSpeed();
        if (ModuleManager.isVideoNewSlowMotion()) {
            this.mSpeed = CameraSettings.VIDEO_MODE_960;
        } else {
            this.mSlowModeFps = null;
        }
        this.mTimeBetweenTimeLapseFrameCaptureMs = 0;
        this.mCaptureTimeLapse = false;
        if (CameraSettings.VIDEO_SPEED_FAST.equals(this.mSpeed)) {
            if (isFrontCamera()) {
                this.mTimeBetweenTimeLapseFrameCaptureMs = getResources().getInteger(R.integer.front_pref_video_time_lapse_frame_interval_default);
            } else {
                this.mTimeBetweenTimeLapseFrameCaptureMs = Integer.parseInt(DataRepository.dataItemGlobal().getString("pref_video_time_lapse_frame_interval_key", getString(R.string.pref_video_time_lapse_frame_interval_default)));
            }
            this.mCaptureTimeLapse = this.mTimeBetweenTimeLapseFrameCaptureMs != 0;
            if (this.mCaptureTimeLapse && ((preferVideoQuality = preferVideoQuality + 1000) < 1000 || preferVideoQuality > 1008)) {
                preferVideoQuality += NotificationManagerCompat.IMPORTANCE_UNSPECIFIED;
                this.mCaptureTimeLapse = false;
                forceToNormalMode();
                ((BaseModule) this).mActivity.runOnUiThread(new F(this));
            }
            this.mQuality = preferVideoQuality % 1000;
        } else if (ModuleManager.isVideoNewSlowMotion()) {
            this.mQuality = 6;
            Size size = SIZE_1080;
            int parseInt = Integer.parseInt(DataRepository.dataItemConfig().getComponentConfigSlowMotionQuality().getComponentValue(172));
            if (parseInt == 5) {
                size = SIZE_720;
                this.mQuality = parseInt;
            }
            this.mSlowModeFps = DataRepository.dataItemConfig().getComponentConfigSlowMotion().getComponentValue(172);
            if (isFPS120()) {
                updateHfrFPSRange(size, 120);
            } else {
                updateHfrFPSRange(size, 240);
            }
            preferVideoQuality = parseInt;
        } else {
            this.mQuality = preferVideoQuality;
        }
        CamcorderProfile camcorderProfile = this.mProfile;
        if (!(camcorderProfile == null || camcorderProfile.quality % 1000 == this.mQuality)) {
            stopObjectTracking(false);
        }
        this.mProfile = fetchProfile(((BaseModule) this).mBogusCameraId, preferVideoQuality);
        this.mProfile.videoCodec = CameraSettings.getVideoEncoder();
        ((VideoBase) this).mOutputFormat = this.mProfile.fileFormat;
        String str = VideoBase.TAG;
        Locale locale = Locale.ENGLISH;
        Object[] objArr = new Object[4];
        objArr[0] = Integer.valueOf(getHSRValue() > 0 ? getHSRValue() : this.mProfile.videoFrameRate);
        objArr[1] = Integer.valueOf(this.mProfile.videoFrameWidth);
        objArr[2] = Integer.valueOf(this.mProfile.videoFrameHeight);
        objArr[3] = Integer.valueOf(this.mProfile.videoCodec);
        Log.d(str, String.format(locale, "frameRate=%d profileSize=%dx%d codec=%d", objArr));
        if (ModuleManager.isVideoNewSlowMotion()) {
            this.mFrameRate = this.mHfrFPSUpper;
        } else {
            this.mFrameRate = this.mProfile.videoFrameRate;
        }
        if (isFPS960()) {
            ((VideoBase) this).mMaxVideoDurationInMs = 2000;
            return;
        }
        try {
            ((VideoBase) this).mMaxVideoDurationInMs = ((BaseModule) this).mActivity.getCameraIntentManager().getVideoDurationTime() * 1000;
        } catch (RuntimeException unused2) {
            if (!CameraSettings.is4KHigherVideoQuality(this.mQuality) || this.mCaptureTimeLapse) {
                ((VideoBase) this).mMaxVideoDurationInMs = 0;
            } else {
                boolean c_0x27_OR_T = DataRepository.dataItemFeature().c_0x27_OR_T();
                if (DataRepository.dataItemFeature().c_19039_0x0016() && is8KCamcorder()) {
                    ((VideoBase) this).mMaxVideoDurationInMs = MAX_DURATION_8K;
                } else if (c_0x27_OR_T && is4KCamcorder()) {
                    ((VideoBase) this).mMaxVideoDurationInMs = MAX_DURATION_4K;
                }
            }
        }
        int i = ((VideoBase) this).mMaxVideoDurationInMs;
        if (i != 0 && i < 1000) {
            ((VideoBase) this).mMaxVideoDurationInMs = 1000;
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.BaseProtocol, com.android.camera.module.BaseModule, com.android.camera.module.Module
    public void registerProtocol() {
        super.registerProtocol();
        ModeCoordinatorImpl.getInstance().attachProtocol(161, this);
        ModeCoordinatorImpl.getInstance().attachProtocol(167, this);
        ModeCoordinatorImpl.getInstance().attachProtocol(169, this);
        ModeCoordinatorImpl.getInstance().attachProtocol(215, this);
        ModeCoordinatorImpl.getInstance().attachProtocol(193, this);
        if (HybridZoomingSystem.IS_3_OR_MORE_SAT) {
            getActivity().getImplFactory().initAdditional(getActivity(), 164, 174, 234, 212);
        } else {
            getActivity().getImplFactory().initAdditional(getActivity(), 164, 234, 212);
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.VideoBase
    public void resizeForPreviewAspectRatio() {
        if (((((BaseModule) this).mCameraCapabilities.getSensorOrientation() - Util.getDisplayRotation(((BaseModule) this).mActivity)) + 360) % 180 == 0) {
            ModeProtocol.MainContentProtocol mainContentProtocol = ((BaseModule) this).mMainProtocol;
            CameraSize cameraSize = ((VideoBase) this).mVideoSize;
            mainContentProtocol.setPreviewAspectRatio(((float) cameraSize.height) / ((float) cameraSize.width));
            return;
        }
        ModeProtocol.MainContentProtocol mainContentProtocol2 = ((BaseModule) this).mMainProtocol;
        CameraSize cameraSize2 = ((VideoBase) this).mVideoSize;
        mainContentProtocol2.setPreviewAspectRatio(((float) cameraSize2.width) / ((float) cameraSize2.height));
    }

    @Override // com.android.camera.module.Module
    public void resumePreview() {
        Log.v(VideoBase.TAG, "resumePreview");
        ((VideoBase) this).mPreviewing = true;
        Camera2Proxy camera2Proxy = ((BaseModule) this).mCamera2Device;
        if (camera2Proxy != null) {
            camera2Proxy.resumePreview();
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.AutoZoomModuleProtocol
    public void setAutoZoomMode(int i) {
        updatePreferenceInWorkThread(51);
    }

    @Override // com.android.camera.protocol.ModeProtocol.AutoZoomModuleProtocol
    public void setAutoZoomStartCapture(RectF rectF) {
        if (((BaseModule) this).mCamera2Device != null && isAlive()) {
            ((BaseModule) this).mCamera2Device.setAutoZoomStartCapture(new float[]{rectF.left, rectF.top, rectF.width(), rectF.height()}, ((VideoBase) this).mMediaRecorderRecording);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.AutoZoomModuleProtocol
    public void setAutoZoomStopCapture(int i) {
        if (((BaseModule) this).mCamera2Device != null && isAlive()) {
            ((BaseModule) this).mCamera2Device.setAutoZoomStopCapture(i, ((VideoBase) this).mMediaRecorderRecording);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopConfigProtocol
    public void startAiLens() {
    }

    @Override // com.android.camera.protocol.ModeProtocol.AutoZoomModuleProtocol
    public void startAutoZoom() {
        this.isShowOrHideUltraWideHint.getAndSet(true);
        this.isAutoZoomTracking.getAndSet(false);
        ((BaseModule) this).mHandler.post(new Runnable() {
            /* class com.android.camera.module.VideoModule.AnonymousClass11 */

            public void run() {
                if (VideoModule.this.mAutoZoomViewProtocol != null) {
                    VideoModule.this.mAutoZoomViewProtocol.onAutoZoomStarted();
                }
            }
        });
        notifyAutoZoomStartUiHint();
    }

    @Override // com.android.camera.ui.ObjectView.ObjectViewListener
    public void startObjectTracking() {
        String str = VideoBase.TAG;
        Log.d(str, "startObjectTracking: started=" + ((BaseModule) this).mObjectTrackingStarted);
    }

    @Override // com.android.camera.module.Module
    public void startPreview() {
        String str = VideoBase.TAG;
        Log.v(str, "startPreview: previewing=" + ((VideoBase) this).mPreviewing);
        checkDisplayOrientation();
        ((VideoBase) this).mPreviewing = true;
    }

    @Override // com.android.camera.protocol.ModeProtocol.AutoZoomModuleProtocol
    public void startTracking(RectF rectF) {
        if (((BaseModule) this).mCamera2Device != null && isAlive()) {
            ModeProtocol.TopAlert topAlert = this.mTopAlert;
            if (topAlert != null) {
                topAlert.alertAiDetectTipHint(4, 0, 0);
            }
            notifyAutoZoomStopUiHint();
            ((BaseModule) this).mCamera2Device.setAutoZoomStopCapture(-1, ((VideoBase) this).mMediaRecorderRecording);
            ((BaseModule) this).mCamera2Device.setAutoZoomStartCapture(new float[]{rectF.left, rectF.top, rectF.width(), rectF.height()}, ((VideoBase) this).mMediaRecorderRecording);
            ((BaseModule) this).mCamera2Device.setAutoZoomStartCapture(new float[]{0.0f, 0.0f, 0.0f, 0.0f}, ((VideoBase) this).mMediaRecorderRecording);
            this.isAutoZoomTracking.getAndSet(true);
            CameraStatUtils.trackSelectObject(((VideoBase) this).mMediaRecorderRecording);
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.VideoBase
    public void startVideoRecording() {
        String str = VideoBase.TAG;
        Log.v(str, "startVideoRecording: mode=" + this.mSpeed);
        if (isDeviceAlive()) {
            ScenarioTrackUtil.trackStartVideoRecordStart(this.mSpeed, isFrontCamera());
            this.mCurrentFileNumber = isCaptureIntent() ? -1 : 0;
            silenceOuterAudio();
            if (!startRecorder()) {
                onStartRecorderFail();
                if (DataRepository.dataItemFeature().c_27845_0x0001() && CameraSettings.is4KHigherVideoQuality(this.mQuality)) {
                    int hSRValue = getHSRValue();
                    if (hSRValue <= 0) {
                        hSRValue = this.mProfile.videoFrameRate;
                    }
                    ThermalHelper.notifyThermalRecordStop(this.mQuality, hSRValue);
                    return;
                }
                return;
            }
            ModeProtocol.RecordState recordState = (ModeProtocol.RecordState) ModeCoordinatorImpl.getInstance().getAttachProtocol(212);
            if (recordState != null) {
                recordState.onStart();
            }
            updatePreferenceTrampoline(UpdateConstant.VIDEO_TYPES_RECORD);
            if (ModuleManager.isVideoNewSlowMotion()) {
                ((BaseModule) this).mCamera2Device.startHighSpeedRecording();
            } else {
                ((BaseModule) this).mCamera2Device.startRecording();
            }
            Log.v(VideoBase.TAG, "startVideoRecording process done");
            this.mTrackLostCount = 0;
            ScenarioTrackUtil.trackStartVideoRecordEnd();
            onStartRecorderSucceed();
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.AutoZoomModuleProtocol
    public void stopAutoZoom() {
        this.isShowOrHideUltraWideHint.getAndSet(false);
        this.isAutoZoomTracking.getAndSet(false);
        ((BaseModule) this).mHandler.post(new Runnable() {
            /* class com.android.camera.module.VideoModule.AnonymousClass12 */

            public void run() {
                if (VideoModule.this.mAutoZoomViewProtocol != null) {
                    VideoModule.this.mAutoZoomViewProtocol.onAutoZoomStopped();
                }
            }
        });
        notifyAutoZoomStopUiHint();
    }

    @Override // com.android.camera.module.loader.camera2.FocusManager2.Listener, com.android.camera.ui.ObjectView.ObjectViewListener, com.android.camera.module.VideoBase
    public void stopObjectTracking(boolean z) {
        String str = VideoBase.TAG;
        Log.d(str, "stopObjectTracking: started=" + ((BaseModule) this).mObjectTrackingStarted);
    }

    @Override // com.android.camera.protocol.ModeProtocol.AutoZoomModuleProtocol
    public void stopTracking(int i) {
        if (this.isAutoZoomTracking.get()) {
            this.isAutoZoomTracking.getAndSet(false);
            if (((BaseModule) this).mCamera2Device != null && isAlive()) {
                ((BaseModule) this).mCamera2Device.setAutoZoomStopCapture(0, ((VideoBase) this).mMediaRecorderRecording);
                ((BaseModule) this).mCamera2Device.setAutoZoomStopCapture(-1, ((VideoBase) this).mMediaRecorderRecording);
            }
            this.mAutoZoomViewProtocol.onTrackingStopped(i);
        }
        notifyAutoZoomStartUiHint();
    }

    @Override // com.android.camera.module.VideoBase
    public void stopVideoRecording(boolean z, boolean z2) {
        long j;
        int i;
        ModeProtocol.SubtitleRecording subtitleRecording;
        String str = VideoBase.TAG;
        Log.v(str, "stopVideoRecording>>" + ((VideoBase) this).mMediaRecorderRecording + "|" + z);
        if (((VideoBase) this).mMediaRecorderRecording) {
            if (this.isAutoZoomTracking.get()) {
                stopTracking(0);
            }
            ((VideoBase) this).mMediaRecorderRecording = false;
            ((VideoBase) this).mMediaRecorderRecordingPaused = false;
            long currentTimeMillis = System.currentTimeMillis();
            if (isFPS960()) {
                if (2000 - (SystemClock.uptimeMillis() - ((VideoBase) this).mRecordingStartTime) <= 100) {
                    this.mMediaRecorderPostProcessing = true;
                }
            }
            if (this.mIsSubtitleSupported && (subtitleRecording = this.mSubtitleRecording) != null) {
                subtitleRecording.handleSubtitleRecordingStop();
            }
            ModeProtocol.MainContentProtocol mainContentProtocol = ((BaseModule) this).mMainProtocol;
            if (mainContentProtocol != null) {
                mainContentProtocol.processingFinish(this.mIsVideoTagSupported);
            }
            if (HybridZoomingSystem.IS_3_OR_MORE_SAT && (i = ((BaseModule) this).mModuleIndex) != 172 && i != 180 && ((!CameraSettings.isMacroModeEnabled(i) || DataRepository.dataItemFeature().c_19039_0x0005_eq_2()) && !CameraSettings.isAutoZoomEnabled(((BaseModule) this).mModuleIndex) && !CameraSettings.isSuperEISEnabled(((BaseModule) this).mModuleIndex) && isBackCamera())) {
                updateZoomRatioToggleButtonState(false);
                if (isUltraWideBackCamera()) {
                    if (CameraSettings.isMacroModeEnabled(((BaseModule) this).mModuleIndex)) {
                        setMinZoomRatio(HybridZoomingSystem.getMinimumMacroOpticalZoomRatio());
                        setMaxZoomRatio(HybridZoomingSystem.getMaximumMacroOpticalZoomRatio());
                    } else {
                        setMinZoomRatio(0.6f);
                        setMaxZoomRatio(((BaseModule) this).mCameraCapabilities.getMaxZoomRatio() * 0.6f);
                    }
                } else if (!DataRepository.dataItemGlobal().isNormalIntent() && ((BaseModule) this).mCameraCapabilities.isSupportLightTripartite()) {
                    setMinZoomRatio(1.0f);
                    setMaxZoomRatio(Math.min(6.0f, ((BaseModule) this).mCameraCapabilities.getMaxZoomRatio()));
                } else if (DataRepository.dataItemFeature().sf()) {
                    setMinZoomRatio(0.6f);
                    if (isInVideoSAT()) {
                        setMaxZoomRatio(12.0f);
                    } else {
                        setMaxZoomRatio(Math.min(6.0f, ((BaseModule) this).mCameraCapabilities.getMaxZoomRatio()));
                    }
                } else {
                    setMinZoomRatio(0.6f);
                    setMaxZoomRatio(Math.min(6.0f, ((BaseModule) this).mCameraCapabilities.getMaxZoomRatio()));
                }
            }
            enableCameraControls(false);
            Camera2Proxy camera2Proxy = ((BaseModule) this).mCamera2Device;
            if (camera2Proxy != null) {
                camera2Proxy.stopRecording(z ? null : this);
            }
            if (this.mCountDownTimer != null && CameraSettings.isVideoBokehOn()) {
                this.mCountDownTimer.cancel();
            }
            ModeProtocol.RecordState recordState = (ModeProtocol.RecordState) ModeCoordinatorImpl.getInstance().getAttachProtocol(212);
            if (recordState != null) {
                if (this.mMediaRecorderPostProcessing) {
                    recordState.onPostSavingStart();
                } else {
                    recordState.onFinish();
                }
            }
            if (((BaseModule) this).mCamera2Device == null || ModuleManager.isVideoNewSlowMotion()) {
                j = currentTimeMillis;
            } else {
                boolean isAutoZoomEnabled = CameraSettings.isAutoZoomEnabled(((BaseModule) this).mModuleIndex);
                boolean isSuperEISEnabled = CameraSettings.isSuperEISEnabled(((BaseModule) this).mModuleIndex);
                String str2 = this.mSpeed;
                if (isFPS120() || isFPS240() || isFPS960()) {
                    str2 = MistatsConstants.VideoAttr.VALUE_SPEED_SLOW;
                }
                ComponentRunningMacroMode componentRunningMacroMode = DataRepository.dataItemRunning().getComponentRunningMacroMode();
                if (componentRunningMacroMode != null && componentRunningMacroMode.isSwitchOn(getModuleIndex())) {
                    HashMap hashMap = new HashMap();
                    hashMap.put(MistatsConstants.MacroAttr.PARAM_SLOW_MOTION_MACRO, this.mSlowModeFps);
                    MistatsWrapper.mistatEvent(MistatsConstants.MacroAttr.FUCNAME_MACRO_MODE, hashMap);
                }
                if (((BaseModule) this).mModuleIndex == 180) {
                    trackProVideoInfo();
                    j = currentTimeMillis;
                } else {
                    j = currentTimeMillis;
                    CameraStatUtils.trackVideoRecorded(isFrontCamera(), getActualCameraId(), getModuleIndex(), isAutoZoomEnabled, isSuperEISEnabled, CameraSettings.isUltraWideConfigOpen(getModuleIndex()), str2, this.mQuality, ((BaseModule) this).mCamera2Device.getFlashMode(), this.mFrameRate, this.mTimeBetweenTimeLapseFrameCaptureMs, ((VideoBase) this).mBeautyValues, this.mVideoRecordTime, this.mIsSubtitleSupported);
                }
                if (isAutoZoomEnabled) {
                    String str3 = VideoBase.TAG;
                    Log.v(str3, "track count is " + this.mTrackLostCount);
                    CameraStatUtils.trackLostCount(this.mTrackLostCount);
                }
            }
            this.mVideoRecordTime = 0;
            if (z) {
                stopRecorder();
                startPreviewAfterRecord();
            } else {
                this.mPendingStopRecorder = true;
                ((BaseModule) this).mHandler.sendEmptyMessageDelayed(46, 300);
            }
            handleTempVideoFile();
            AutoLockManager.getInstance(((BaseModule) this).mActivity).hibernateDelayed();
            exitSavePowerMode();
            onInterceptZoomingEvent(((BaseModule) this).mPreZoomRation, getZoomRatio(), -1);
            String str4 = VideoBase.TAG;
            Log.v(str4, "stopVideoRecording<<time=" + (System.currentTimeMillis() - j));
        }
    }

    public boolean takeVideoSnapShoot() {
        if (((BaseModule) this).mPaused || ((BaseModule) this).mActivity.isActivityPaused() || this.mSnapshotInProgress || !((VideoBase) this).mMediaRecorderRecording || !isDeviceAlive()) {
            return false;
        }
        if (Storage.isLowStorageAtLastPoint()) {
            Log.w(VideoBase.TAG, "capture: low storage");
            stopVideoRecording(true, false);
            return false;
        } else if (((BaseModule) this).mActivity.getImageSaver().isBusy()) {
            Log.w(VideoBase.TAG, "capture: ImageSaver is full");
            RotateTextToast.getInstance(((BaseModule) this).mActivity).show(R.string.toast_saving, 0);
            return false;
        } else {
            ((BaseModule) this).mCamera2Device.setJpegRotation(Util.getJpegRotation(((BaseModule) this).mBogusCameraId, ((BaseModule) this).mOrientation));
            Location currentLocation = LocationManager.instance().getCurrentLocation();
            ((BaseModule) this).mCamera2Device.setGpsLocation(currentLocation);
            setJpegQuality();
            updateFrontMirror();
            if (!b.sl()) {
                ((BaseModule) this).mActivity.getCameraScreenNail().animateCapture(getCameraRotation());
            }
            Log.v(VideoBase.TAG, "capture: start");
            ((BaseModule) this).mCamera2Device.captureVideoSnapshot(new JpegPictureCallback(currentLocation));
            this.mSnapshotInProgress = true;
            CameraStatUtils.trackVideoSnapshot(isFrontCamera());
            return true;
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.BaseProtocol, com.android.camera.module.BaseModule, com.android.camera.module.Module
    public void unRegisterProtocol() {
        super.unRegisterProtocol();
        ModeCoordinatorImpl.getInstance().detachProtocol(215, this);
        ModeCoordinatorImpl.getInstance().detachProtocol(161, this);
        ModeCoordinatorImpl.getInstance().detachProtocol(167, this);
        ModeCoordinatorImpl.getInstance().detachProtocol(169, this);
        ModeCoordinatorImpl.getInstance().detachProtocol(193, this);
        getActivity().getImplFactory().detachAdditional();
    }

    public void updateManualEvAdjust() {
        if (((BaseModule) this).mModuleIndex == 167) {
            String manualValue = getManualValue(CameraSettings.KEY_QC_EXPOSURETIME, getString(R.string.pref_camera_exposuretime_default));
            String manualValue2 = getManualValue(CameraSettings.KEY_QC_ISO, getString(R.string.pref_camera_iso_default));
            String str = VideoBase.TAG;
            Log.d(str, "MODE_MANUAL: exposureTime = " + manualValue + "iso = " + manualValue2);
            boolean equals = b.sm() ? getString(R.string.pref_camera_exposuretime_default).equals(manualValue) : getString(R.string.pref_camera_iso_default).equals(manualValue2) || getString(R.string.pref_camera_exposuretime_default).equals(manualValue);
            Handler handler = ((BaseModule) this).mHandler;
            if (handler != null) {
                handler.post(new E(this, equals));
            }
            if (1 == ((BaseModule) this).mCamera2Device.getFocusMode()) {
                Camera camera = ((BaseModule) this).mActivity;
                if (camera != null) {
                    camera.runOnUiThread(new G(this));
                }
                unlockAEAF();
            }
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.VideoBase
    public void updateRecordingTime() {
        String str;
        long j;
        double d2;
        super.updateRecordingTime();
        if (((VideoBase) this).mMediaRecorderRecording && !isFPS960() && !CameraSettings.isVideoBokehOn()) {
            long uptimeMillis = SystemClock.uptimeMillis() - ((VideoBase) this).mRecordingStartTime;
            if (((VideoBase) this).mMediaRecorderRecordingPaused) {
                uptimeMillis = this.mVideoRecordedDuration;
            }
            int i = ((VideoBase) this).mMaxVideoDurationInMs;
            boolean z = i != 0 && uptimeMillis >= ((long) (i - LinkSelectorConfiguration.MS_OF_ONE_MIN));
            long max = z ? Math.max(0L, ((long) ((VideoBase) this).mMaxVideoDurationInMs) - uptimeMillis) + 999 : uptimeMillis;
            long j2 = 1000;
            if (isNormalMode()) {
                this.mVideoRecordTime = max / 1000;
                str = Util.millisecondToTimeString(max, false);
            } else {
                if (CameraSettings.VIDEO_SPEED_FAST.equals(this.mSpeed)) {
                    d2 = (double) this.mTimeBetweenTimeLapseFrameCaptureMs;
                    j = (long) d2;
                } else {
                    j = 1000;
                    d2 = 0.0d;
                }
                if (d2 != 0.0d) {
                    str = Util.millisecondToTimeString(getSpeedRecordVideoLength(uptimeMillis, d2), CameraSettings.VIDEO_SPEED_FAST.equals(this.mSpeed));
                    if (str.equals(this.mRecordingTime)) {
                        j2 = (long) d2;
                    }
                } else {
                    str = Util.millisecondToTimeString(max, false);
                }
                j2 = j;
            }
            ModeProtocol.TopAlert topAlert = this.mTopAlert;
            if (topAlert != null) {
                topAlert.updateRecordingTime(str);
            }
            this.mRecordingTime = str;
            if (this.mRecordingTimeCountsDown != z) {
                this.mRecordingTimeCountsDown = z;
            }
            long j3 = 500;
            if (!((VideoBase) this).mMediaRecorderRecordingPaused) {
                j3 = j2 - (uptimeMillis % j2);
            }
            ((BaseModule) this).mHandler.sendEmptyMessageDelayed(42, j3);
        }
    }

    @Override // com.android.camera.module.BaseModule
    public void updateSATZooming(boolean z) {
        if (DataRepository.dataItemFeature().c_19039_0x0006() && HybridZoomingSystem.IS_3_OR_MORE_SAT && ((BaseModule) this).mCamera2Device != null && DataRepository.dataItemFeature().sf() && isInVideoSAT()) {
            ((BaseModule) this).mCamera2Device.setSatIsZooming(z);
            resumePreview();
        }
    }

    public /* synthetic */ void x(boolean z) {
        ((BaseModule) this).mMainProtocol.setEvAdjustable(z);
    }
}
