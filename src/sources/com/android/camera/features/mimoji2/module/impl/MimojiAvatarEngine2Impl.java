package com.android.camera.features.mimoji2.module.impl;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.location.Location;
import android.media.Image;
import android.opengl.GLES20;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Size;
import android.widget.Toast;
import com.android.camera.ActivityBase;
import com.android.camera.CameraSettings;
import com.android.camera.LocationManager;
import com.android.camera.R;
import com.android.camera.SurfaceTextureScreenNail;
import com.android.camera.Thumbnail;
import com.android.camera.Util;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.config.ComponentConfigRatio;
import com.android.camera.data.data.runing.ComponentRunningShine;
import com.android.camera.effect.FilterInfo;
import com.android.camera.effect.renders.DeviceWatermarkParam;
import com.android.camera.features.mimoji2.bean.MimojiBgInfo;
import com.android.camera.features.mimoji2.bean.MimojiInfo2;
import com.android.camera.features.mimoji2.bean.MimojiTimbreInfo;
import com.android.camera.features.mimoji2.module.MimojiModule;
import com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol;
import com.android.camera.features.mimoji2.utils.BitmapUtils2;
import com.android.camera.features.mimoji2.widget.helper.AvatarEngineManager2;
import com.android.camera.features.mimoji2.widget.helper.MimojiHelper2;
import com.android.camera.features.mimoji2.widget.helper.MimojiStatusManager2;
import com.android.camera.log.Log;
import com.android.camera.module.BaseModule;
import com.android.camera.module.Module;
import com.android.camera.module.impl.component.FileUtils;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera.statistic.CameraStatUtils;
import com.android.camera.statistic.MistatsConstants;
import com.android.camera.ui.V6CameraGLSurfaceView;
import com.android.camera2.Camera2Proxy;
import com.android.camera2.CameraCapabilities;
import com.arcsoft.avatar.AvatarConfig;
import com.arcsoft.avatar.AvatarEngine;
import com.arcsoft.avatar.BackgroundInfo;
import com.arcsoft.avatar.RecordModule;
import com.arcsoft.avatar.recoder.RecordingListener;
import com.arcsoft.avatar.util.ASVLOFFSCREEN;
import com.arcsoft.avatar.util.AsvloffscreenUtil;
import com.arcsoft.avatar.util.LOG;
import com.xiaomi.camera.core.ParallelTaskData;
import com.xiaomi.camera.core.ParallelTaskDataParameter;
import com.xiaomi.camera.core.PictureInfo;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

public class MimojiAvatarEngine2Impl implements MimojiModeProtocol.MimojiAvatarEngine2, SurfaceTextureScreenNail.ExternalFrameProcessor, Camera2Proxy.PreviewCallback {
    public static final int DELETE_GIF = 3;
    public static final int DELETE_MIMOJI_ALL = 0;
    public static final int DELETE_MIMOJI_EMOTICON = 2;
    public static final int DELETE_MIMOJI_VIDEO = 1;
    private static final int FLAG_HAS_FACE = 5;
    private static final int FLAG_INIT_FACE = 0;
    private static final int HANDLER_RECORDING_CURRENT_FILE_SIZE = 3;
    private static final int HANDLER_RECORDING_CURRENT_TIME = 1;
    private static final int HANDLER_RECORDING_MAX_DURATION_REACHED = 2;
    private static final int HANDLER_RECORDING_MAX_FILE_SIZE_REACHED = 4;
    private static final int HANDLER_RESOURCE_ERROR_BROKEN = 0;
    private static final long START_OFFSET_MS = 450;
    /* access modifiers changed from: private */
    public static final String TAG = "MimojiAvatarEngine2Impl";
    /* access modifiers changed from: private */
    public ActivityBase mActivityBase;
    /* access modifiers changed from: private */
    public AvatarEngine mAvatar;
    /* access modifiers changed from: private */
    public final Object mAvatarLock = new Object();
    private String mAvatarTemplatePath = "";
    private V6CameraGLSurfaceView mCameraView;
    private RecordModule.MediaResultCallback mCaptureCallback = new RecordModule.MediaResultCallback() {
        /* class com.android.camera.features.mimoji2.module.impl.MimojiAvatarEngine2Impl.AnonymousClass5 */

        @Override // com.arcsoft.avatar.RecordModule.MediaResultCallback
        public void onCaptureResult(final ByteBuffer byteBuffer) {
            Log.d(MimojiAvatarEngine2Impl.TAG, "onCapture Result");
            MimojiAvatarEngine2Impl.this.mLoadHandler.post(new Runnable() {
                /* class com.android.camera.features.mimoji2.module.impl.MimojiAvatarEngine2Impl.AnonymousClass5.AnonymousClass2 */

                public void run() {
                    MimojiAvatarEngine2Impl.this.CaptureCallback(byteBuffer);
                }
            });
        }

        @Override // com.arcsoft.avatar.RecordModule.MediaResultCallback
        public void onVideoResult(boolean z) {
            Log.d(MimojiAvatarEngine2Impl.TAG, "stop video record callback");
            if (MimojiAvatarEngine2Impl.this.mVideoFileStream != null) {
                try {
                    MimojiAvatarEngine2Impl.this.mVideoFileStream.close();
                } catch (IOException e2) {
                    Log.e(MimojiAvatarEngine2Impl.TAG, "fail to close file stream", e2);
                }
                FileOutputStream unused = MimojiAvatarEngine2Impl.this.mVideoFileStream = null;
            }
            if (MimojiAvatarEngine2Impl.this.mGetThumCountDownLatch != null) {
                MimojiAvatarEngine2Impl.this.mGetThumCountDownLatch.countDown();
            }
            boolean unused2 = MimojiAvatarEngine2Impl.this.mIsRecording = false;
            boolean unused3 = MimojiAvatarEngine2Impl.this.mIsRecordStopping = false;
            if (MimojiAvatarEngine2Impl.this.isShowGifOperate()) {
                MimojiAvatarEngine2Impl.this.mHandler.post(new Runnable() {
                    /* class com.android.camera.features.mimoji2.module.impl.MimojiAvatarEngine2Impl.AnonymousClass5.AnonymousClass1 */

                    public void run() {
                        ModeProtocol.CameraAction cameraAction = (ModeProtocol.CameraAction) ModeCoordinatorImpl.getInstance().getAttachProtocol(161);
                        if (cameraAction != null) {
                            cameraAction.onReviewCancelClicked();
                            return;
                        }
                        ModeProtocol.RecordState recordState = (ModeProtocol.RecordState) ModeCoordinatorImpl.getInstance().getAttachProtocol(212);
                        if (recordState != null) {
                            recordState.onFinish();
                        }
                    }
                });
                if (MimojiAvatarEngine2Impl.this.mTotalRecordingTime >= 1000 || MimojiAvatarEngine2Impl.this.mTotalRecordingTime == 0) {
                    ModeProtocol.BaseDelegate baseDelegate = (ModeProtocol.BaseDelegate) ModeCoordinatorImpl.getInstance().getAttachProtocol(160);
                    if (baseDelegate != null) {
                        baseDelegate.delegateEvent(20);
                    }
                    ModeProtocol.MimojiGifEditor mimojiGifEditor = (ModeProtocol.MimojiGifEditor) ModeCoordinatorImpl.getInstance().getAttachProtocol(251);
                    if (mimojiGifEditor != null) {
                        mimojiGifEditor.completeVideoRecord(MimojiAvatarEngine2Impl.this.getVideoCache(), z);
                        return;
                    }
                    return;
                }
                return;
            }
            MimojiModeProtocol.MimojiVideoEditor mimojiVideoEditor = (MimojiModeProtocol.MimojiVideoEditor) ModeCoordinatorImpl.getInstance().getAttachProtocol(252);
            if (mimojiVideoEditor != null) {
                mimojiVideoEditor.combineVideoAudio(MimojiAvatarEngine2Impl.this.getVideoCache());
            }
        }
    };
    private Context mContext;
    private CountDownTimer mCountDownTimer;
    private int mCurrentScreenOrientation = 0;
    private MimojiBgInfo mCurrentTempMimojiBgInfo;
    private int mDeviceRotation = 90;
    private int mDisplayOrientation;
    private Size mDrawSize;
    private int mFaceDectectResult = 1;
    /* access modifiers changed from: private */
    public CountDownLatch mGetThumCountDownLatch;
    /* access modifiers changed from: private */
    public Handler mHandler = new Handler(Looper.getMainLooper()) {
        /* class com.android.camera.features.mimoji2.module.impl.MimojiAvatarEngine2Impl.AnonymousClass8 */

        public void handleMessage(Message message) {
            super.handleMessage(message);
            int i = message.what;
            if (i != 0 && i != 1 && i != 2) {
            }
        }
    };
    private volatile boolean mIsAvatarInited;
    private boolean mIsFaceDetectSuccess = false;
    private boolean mIsFrontCamera;
    private int mIsNoFaceResult = -1;
    /* access modifiers changed from: private */
    public boolean mIsRecordStopping = false;
    /* access modifiers changed from: private */
    public volatile boolean mIsRecording;
    private boolean mIsShutterButtonClick = false;
    private boolean mIsStopRender = true;
    private boolean mLastNeedBeauty = false;
    /* access modifiers changed from: private */
    public Handler mLoadHandler;
    private Handler mLoadResourceHandler;
    private HandlerThread mLoadResourceThread = new HandlerThread("LoadResource");
    private HandlerThread mLoadThread = new HandlerThread("LoadConfig");
    private ModeProtocol.MainContentProtocol mMainProtocol;
    /* access modifiers changed from: private */
    public int mMaxVideoDurationInMs;
    private MimojiModeProtocol.MimojiEditor2 mMimojiEditor2;
    private MimojiStatusManager2 mMimojiStatusManager2;
    private boolean mNeedCapture = false;
    private int mOrientation;
    private int mPreviewHeight;
    private int mPreviewWidth;
    /* access modifiers changed from: private */
    public RecordModule mRecordModule;
    private RecordingListener mRecordingListener = new RecordingListener() {
        /* class com.android.camera.features.mimoji2.module.impl.MimojiAvatarEngine2Impl.AnonymousClass7 */

        @Override // com.arcsoft.avatar.recoder.RecordingListener
        public void onRecordingListener(int i, Object obj) {
            Message obtainMessage = MimojiAvatarEngine2Impl.this.mHandler.obtainMessage();
            switch (i) {
                case 257:
                    obtainMessage.arg1 = (int) ((((Long) obj).longValue() / 1000) / 1000);
                    obtainMessage.what = 2;
                    break;
                case 258:
                    long longValue = (((Long) obj).longValue() / 1000) / 1000;
                    obtainMessage.arg1 = (int) longValue;
                    String access$200 = MimojiAvatarEngine2Impl.TAG;
                    Log.d(access$200, "onRecordingListener_time = " + longValue);
                    obtainMessage.what = 1;
                    break;
                case 259:
                    obtainMessage.arg1 = (int) (((Long) obj).longValue() / 1024);
                    obtainMessage.what = 4;
                    break;
                case 260:
                    obtainMessage.arg1 = (int) (((Long) obj).longValue() / 1024);
                    obtainMessage.what = 3;
                    break;
            }
            obtainMessage.sendToTarget();
        }
    };
    private boolean mSquare = false;
    private int[] mTextureId = new int[1];
    /* access modifiers changed from: private */
    public long mTotalRecordingTime;
    private Handler mUiHandler;
    /* access modifiers changed from: private */
    public FileOutputStream mVideoFileStream;

    private MimojiAvatarEngine2Impl(ActivityBase activityBase) {
        this.mActivityBase = activityBase;
        this.mCameraView = activityBase.getGLView();
        if (CameraSettings.isGifOn()) {
            this.mMaxVideoDurationInMs = 5000;
        } else {
            this.mMaxVideoDurationInMs = 15000;
        }
        this.mContext = activityBase.getCameraAppImpl().getApplicationContext();
        this.mMainProtocol = (ModeProtocol.MainContentProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(166);
        this.mMimojiEditor2 = (MimojiModeProtocol.MimojiEditor2) ModeCoordinatorImpl.getInstance().getAttachProtocol(247);
        this.mLoadResourceThread.start();
        this.mLoadResourceHandler = new Handler(this.mLoadResourceThread.getLooper());
        this.mLoadThread.start();
        this.mLoadHandler = new Handler(this.mLoadThread.getLooper());
        this.mUiHandler = new Handler(activityBase.getMainLooper());
        this.mMimojiStatusManager2 = DataRepository.dataItemLive().getMimojiStatusManager2();
        setIsAvatarInited(false);
        Log.w(TAG, "MimojiAvatarEngine2Impl:  constructor");
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0077  */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x0079  */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x0098  */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x009e  */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x00ee  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x00f9  */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x00fe  */
    public void CaptureCallback(ByteBuffer byteBuffer) {
        int i;
        int i2;
        int i3;
        if (this.mActivityBase != null) {
            Bitmap createBitmap = Bitmap.createBitmap(this.mDrawSize.getWidth(), this.mDrawSize.getHeight(), Bitmap.Config.ARGB_8888);
            createBitmap.copyPixelsFromBuffer(byteBuffer);
            Matrix matrix = new Matrix();
            boolean z = this.mIsFrontCamera;
            if (!z || (z && ((i3 = this.mDeviceRotation) == 90 || i3 == 270))) {
                matrix.postScale(1.0f, -1.0f);
            } else if (this.mDeviceRotation % 180 == 0) {
                matrix.postScale(-1.0f, 1.0f);
            }
            Bitmap createBitmap2 = Bitmap.createBitmap(createBitmap, 0, 0, this.mDrawSize.getWidth(), this.mDrawSize.getHeight(), matrix, false);
            int i4 = 0;
            byte[] bitmapData = Util.getBitmapData(createBitmap2, CameraSettings.getEncodingQuality(false).toInteger(false));
            if (this.mIsFrontCamera) {
                int i5 = this.mDeviceRotation;
                if (i5 % 180 == 0) {
                    i = (i5 + 180) % 360;
                    Thumbnail createThumbnail = Thumbnail.createThumbnail(null, !this.mIsFrontCamera ? createBitmap : createBitmap2, i, this.mIsFrontCamera);
                    createThumbnail.startWaitingForUri();
                    this.mActivityBase.getThumbnailUpdater().setThumbnail(createThumbnail, true, true);
                    MimojiModule mimojiModule = (MimojiModule) this.mActivityBase.getCurrentModule();
                    ParallelTaskData parallelTaskData = new ParallelTaskData(mimojiModule == null ? mimojiModule.getActualCameraId() : 0, System.currentTimeMillis(), -4, null);
                    parallelTaskData.fillJpegData(bitmapData, 0);
                    boolean z2 = this.mIsFrontCamera;
                    int i6 = this.mDeviceRotation;
                    Size size = this.mDrawSize;
                    ParallelTaskDataParameter.Builder builder = new ParallelTaskDataParameter.Builder(size, size, size, 256);
                    Location currentLocation = LocationManager.instance().getCurrentLocation();
                    ParallelTaskDataParameter.Builder filterId = builder.setHasDualWaterMark(CameraSettings.isDualCameraWaterMarkOpen()).setJpegRotation((Util.getJpegRotation(z2 ? 1 : 0, i6) + 270) % 360).setJpegQuality(CameraSettings.getEncodingQuality(false).toInteger(false)).setFilterId(FilterInfo.FILTER_ID_NONE);
                    i2 = this.mOrientation;
                    if (-1 != i2) {
                        i4 = i2;
                    }
                    parallelTaskData.fillParameter(filterId.setOrientation(i4).setTimeWaterMarkString(!CameraSettings.isTimeWaterMarkOpen() ? Util.getTimeWatermark() : null).setDeviceWatermarkParam(getDeviceWaterMarkParam()).setPictureInfo(getPictureInfo()).setLocation(currentLocation).build());
                    this.mActivityBase.getImageSaver().onParallelProcessFinish(parallelTaskData, null, null);
                    createBitmap.recycle();
                    createBitmap2.recycle();
                    ((MimojiModule) this.mActivityBase.getCurrentModule()).onMimojiCaptureCallback();
                }
            }
            i = this.mDeviceRotation;
            Thumbnail createThumbnail2 = Thumbnail.createThumbnail(null, !this.mIsFrontCamera ? createBitmap : createBitmap2, i, this.mIsFrontCamera);
            createThumbnail2.startWaitingForUri();
            this.mActivityBase.getThumbnailUpdater().setThumbnail(createThumbnail2, true, true);
            MimojiModule mimojiModule2 = (MimojiModule) this.mActivityBase.getCurrentModule();
            ParallelTaskData parallelTaskData2 = new ParallelTaskData(mimojiModule2 == null ? mimojiModule2.getActualCameraId() : 0, System.currentTimeMillis(), -4, null);
            parallelTaskData2.fillJpegData(bitmapData, 0);
            boolean z22 = this.mIsFrontCamera;
            int i62 = this.mDeviceRotation;
            Size size2 = this.mDrawSize;
            ParallelTaskDataParameter.Builder builder2 = new ParallelTaskDataParameter.Builder(size2, size2, size2, 256);
            Location currentLocation2 = LocationManager.instance().getCurrentLocation();
            ParallelTaskDataParameter.Builder filterId2 = builder2.setHasDualWaterMark(CameraSettings.isDualCameraWaterMarkOpen()).setJpegRotation((Util.getJpegRotation(z22 ? 1 : 0, i62) + 270) % 360).setJpegQuality(CameraSettings.getEncodingQuality(false).toInteger(false)).setFilterId(FilterInfo.FILTER_ID_NONE);
            i2 = this.mOrientation;
            if (-1 != i2) {
            }
            parallelTaskData2.fillParameter(filterId2.setOrientation(i4).setTimeWaterMarkString(!CameraSettings.isTimeWaterMarkOpen() ? Util.getTimeWatermark() : null).setDeviceWatermarkParam(getDeviceWaterMarkParam()).setPictureInfo(getPictureInfo()).setLocation(currentLocation2).build());
            this.mActivityBase.getImageSaver().onParallelProcessFinish(parallelTaskData2, null, null);
            createBitmap.recycle();
            createBitmap2.recycle();
            ((MimojiModule) this.mActivityBase.getCurrentModule()).onMimojiCaptureCallback();
        }
    }

    private void animateCapture() {
        if (CameraSettings.isCameraSoundOpen()) {
            this.mActivityBase.playCameraSound(0);
        }
    }

    public static MimojiAvatarEngine2Impl create(ActivityBase activityBase) {
        return new MimojiAvatarEngine2Impl(activityBase);
    }

    private void createAvatar(byte[] bArr, int i, int i2) {
        int avatarProfile;
        String str = this.mAvatarTemplatePath;
        String str2 = AvatarEngineManager2.PersonTemplatePath;
        if (str != str2) {
            this.mAvatarTemplatePath = str2;
            this.mAvatar.setTemplatePath(str2);
        }
        AvatarConfig.ASAvatarProfileResult aSAvatarProfileResult = new AvatarConfig.ASAvatarProfileResult();
        synchronized (this.mAvatarLock) {
            avatarProfile = this.mAvatar.avatarProfile(AvatarEngineManager2.PersonTemplatePath, i, i2, i * 4, bArr, 0, false, aSAvatarProfileResult, null, c.INSTANCE);
        }
        String str3 = TAG;
        LOG.d(str3, "avatarProfile res: " + avatarProfile + ", status:" + aSAvatarProfileResult.status + ", gender: " + aSAvatarProfileResult.gender);
        int i3 = aSAvatarProfileResult.status;
        if (i3 == 254 || i3 == 246) {
            String str4 = TAG;
            Log.d(str4, "result = " + avatarProfile);
            this.mUiHandler.post(new e(this));
            return;
        }
        if (i3 == 1) {
            Toast.makeText(this.mContext, (int) R.string.mimoji_detect_no_face_failed, 0).show();
        } else if ((i3 & 2) == 0) {
            Toast.makeText(this.mContext, (int) R.string.mimoji_detect_facial_failed, 0).show();
        } else if ((i3 & 4) == 0) {
            Toast.makeText(this.mContext, (int) R.string.mimoji_detect_hairstyle_failed, 0).show();
        } else if ((i3 & 8) == 0) {
            Toast.makeText(this.mContext, (int) R.string.mimoji_detect_haircolor_failed, 0).show();
        } else if ((i3 & 16) == 0) {
            Toast.makeText(this.mContext, (int) R.string.mimoji_detect_gender_failed, 0).show();
        } else if ((i3 & 32) == 0) {
            Toast.makeText(this.mContext, (int) R.string.mimoji_detect_skincolor_failed, 0).show();
        } else if ((i3 & 64) == 0) {
            Toast.makeText(this.mContext, (int) R.string.mimoji_detect_glass_failed, 0).show();
        } else if ((i3 & 128) == 0) {
            Toast.makeText(this.mContext, (int) R.string.mimoji_detect_faceshape_failed, 0).show();
        } else {
            Toast.makeText(this.mContext, (int) R.string.mimoji_detect_unknow_failed, 0).show();
        }
        ActivityBase activityBase = this.mActivityBase;
        if (activityBase != null) {
            activityBase.runOnUiThread(new f(this));
        }
    }

    private DeviceWatermarkParam getDeviceWaterMarkParam() {
        float f2;
        float f3;
        float f4;
        float resourceFloat;
        float resourceFloat2;
        float resourceFloat3;
        boolean isDualCameraWaterMarkOpen = CameraSettings.isDualCameraWaterMarkOpen();
        boolean isFrontCameraWaterMarkOpen = CameraSettings.isFrontCameraWaterMarkOpen();
        if (isDualCameraWaterMarkOpen || isFrontCameraWaterMarkOpen) {
            isDualCameraWaterMarkOpen = false;
            isFrontCameraWaterMarkOpen = true;
        }
        if (isDualCameraWaterMarkOpen) {
            resourceFloat = CameraSettings.getResourceFloat(R.dimen.dualcamera_watermark_size_ratio, 0.0f);
            resourceFloat2 = CameraSettings.getResourceFloat(R.dimen.dualcamera_watermark_padding_x_ratio, 0.0f);
            resourceFloat3 = CameraSettings.getResourceFloat(R.dimen.dualcamera_watermark_padding_y_ratio, 0.0f);
        } else if (isFrontCameraWaterMarkOpen) {
            resourceFloat = CameraSettings.getResourceFloat(R.dimen.frontcamera_watermark_size_ratio, 0.0f);
            resourceFloat2 = CameraSettings.getResourceFloat(R.dimen.frontcamera_watermark_padding_x_ratio, 0.0f);
            resourceFloat3 = CameraSettings.getResourceFloat(R.dimen.frontcamera_watermark_padding_y_ratio, 0.0f);
        } else {
            f4 = 0.0f;
            f3 = 0.0f;
            f2 = 0.0f;
            return new DeviceWatermarkParam(isDualCameraWaterMarkOpen, isFrontCameraWaterMarkOpen, CameraSettings.isUltraPixelRearOn(), CameraSettings.getDualCameraWaterMarkFilePathVendor(), f4, f3, f2);
        }
        f2 = resourceFloat3;
        f4 = resourceFloat;
        f3 = resourceFloat2;
        return new DeviceWatermarkParam(isDualCameraWaterMarkOpen, isFrontCameraWaterMarkOpen, CameraSettings.isUltraPixelRearOn(), CameraSettings.getDualCameraWaterMarkFilePathVendor(), f4, f3, f2);
    }

    private Map<String, String> getMimojiPara() {
        Map<String, String> hashMap = new HashMap<>();
        if (isNeedShowAvatar()) {
            AvatarConfig.ASAvatarConfigValue aSAvatarConfigValue = new AvatarConfig.ASAvatarConfigValue();
            this.mAvatar.getConfigValue(aSAvatarConfigValue);
            hashMap = AvatarEngineManager2.getMimojiConfigValue(aSAvatarConfigValue);
            hashMap.put(MistatsConstants.Mimoji.MIMOJI_CATEGORY, this.mAvatarTemplatePath.equals(AvatarEngineManager2.PersonTemplatePath) ? "custom" : this.mAvatarTemplatePath.equals(AvatarEngineManager2.PigTemplatePath) ? "pig" : this.mAvatarTemplatePath.equals(AvatarEngineManager2.BearTemplatePath) ? "bear" : this.mAvatarTemplatePath.equals(AvatarEngineManager2.RoyanTemplatePath) ? "royan" : this.mAvatarTemplatePath.equals(AvatarEngineManager2.RabbitTemplatePath) ? "rabbit" : "");
        } else {
            hashMap.put(MistatsConstants.Mimoji.MIMOJI_CATEGORY, "null");
        }
        return hashMap;
    }

    private PictureInfo getPictureInfo() {
        PictureInfo opMode = new PictureInfo().setFrontMirror(isFrontMirror()).setSensorType(true).setBokehFrontCamera(false).setHdrType("off").setOpMode(getOperatingMode());
        opMode.end();
        return opMode;
    }

    private void initMimojiResource() {
        String yb = DataRepository.dataItemFeature().yb();
        if (!yb.equals(CameraSettings.getMimojiModleVersion()) || !FileUtils.checkFileDirectoryConsist(MimojiHelper2.DATA_DIR) || !FileUtils.checkFileDirectoryConsist(MimojiHelper2.MODEL_PATH)) {
            Log.w(TAG, "MimojiAvatarEngine2Impl: initMimojiResource unzip...");
            boolean z = true;
            DataRepository.dataItemLive().getMimojiStatusManager2().setIsLoading(true);
            FileUtils.delDir(MimojiHelper2.MIMOJI_DIR);
            try {
                Util.verifyFileZip(this.mContext, "vendor/camera/mimoji/data.zip", MimojiHelper2.MIMOJI_DIR, 32768);
            } catch (Exception e2) {
                Log.e(TAG, "verify asset data zip failed...", e2);
                z = false;
            }
            if (!z) {
                CameraSettings.setMimojiModleVersion(null);
                if (!this.mIsStopRender) {
                    initMimojiResource();
                    return;
                }
            }
            this.mLoadResourceHandler.post(new d(this, yb));
        }
    }

    private boolean isFrontMirror() {
        if (!this.mIsFrontCamera) {
            return false;
        }
        if (CameraSettings.isLiveShotOn()) {
            return true;
        }
        return CameraSettings.isFrontMirror();
    }

    static /* synthetic */ void o(int i) {
    }

    private void onProfileFinish() {
        Log.d(TAG, "onProfileFinish");
        ModeProtocol.RecordState recordState = (ModeProtocol.RecordState) ModeCoordinatorImpl.getInstance().getAttachProtocol(212);
        if (recordState != null) {
            recordState.onPostSavingFinish();
        }
        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        if (topAlert != null) {
            topAlert.alertMimojiFaceDetect(false, -1);
        }
        this.mMimojiEditor2 = (MimojiModeProtocol.MimojiEditor2) ModeCoordinatorImpl.getInstance().getAttachProtocol(247);
        this.mMainProtocol.mimojiEnd();
        this.mMimojiStatusManager2.setMode(6);
        MimojiModeProtocol.MimojiEditor2 mimojiEditor2 = this.mMimojiEditor2;
        if (mimojiEditor2 != null) {
            mimojiEditor2.startMimojiEdit(203);
        }
        ActivityBase activityBase = this.mActivityBase;
        if (activityBase != null) {
            ((MimojiModule) activityBase.getCurrentModule()).onMimojiCreateCompleted(true);
        }
        CameraStatUtils.trackMimojiClick(MistatsConstants.Mimoji.MIMOJI_CLICK_CREATE_CAPTURE, MistatsConstants.BaseEvent.CREATE);
    }

    private void quit() {
        this.mLoadThread.quitSafely();
        this.mLoadResourceThread.quitSafely();
    }

    private void reloadConfig() {
        Log.d(TAG, "MimojiAvatarEngine2Impl reloadConfig");
        int mode = this.mMimojiStatusManager2.getMode();
        if (mode == 2 || mode == 0) {
            this.mMimojiStatusManager2.setMode(2);
            MimojiInfo2 currentMimojiInfo = this.mMimojiStatusManager2.getCurrentMimojiInfo();
            if (this.mRecordModule != null) {
                Log.d(TAG, "mimoji void reloadConfig[]　extrascene init");
                this.mRecordModule.resetExtraScene();
                this.mRecordModule.setExtraSceneTemplatePath(AvatarEngineManager2.PersonTemplatePath);
            }
            if (isNeedShowAvatar()) {
                if (!this.mAvatarTemplatePath.equals(currentMimojiInfo.mAvatarTemplatePath)) {
                    this.mAvatar.setTemplatePath(currentMimojiInfo.mAvatarTemplatePath);
                    this.mAvatarTemplatePath = currentMimojiInfo.mAvatarTemplatePath;
                }
                String str = currentMimojiInfo.mConfigPath;
                if (!AvatarEngineManager2.isPrefabModel(str)) {
                    this.mAvatar.loadConfig(str);
                }
                this.mRecordModule.updateAvatarConfigInfo(this.mAvatar);
            }
        } else if (mode == 6) {
            String str2 = AvatarEngineManager2.PersonTemplatePath;
            this.mAvatarTemplatePath = str2;
            this.mAvatar.setTemplatePath(str2);
            this.mAvatar.loadConfig(AvatarEngineManager2.TempEditConfigPath);
            this.mMimojiEditor2 = (MimojiModeProtocol.MimojiEditor2) ModeCoordinatorImpl.getInstance().getAttachProtocol(247);
            MimojiModeProtocol.MimojiEditor2 mimojiEditor2 = this.mMimojiEditor2;
            if (mimojiEditor2 != null) {
                mimojiEditor2.resetClickEnable(false);
                this.mMimojiEditor2.resetConfig();
                return;
            }
            Log.e(TAG, "MimojiAvatarEngine2Impl reloadConfig: error mimojiEditor is null");
        }
    }

    private void updateBeauty() {
        ActivityBase activityBase = this.mActivityBase;
        if (activityBase != null) {
            BaseModule baseModule = (BaseModule) activityBase.getCurrentModule();
            if (baseModule instanceof MimojiModule) {
                ComponentRunningShine componentRunningShine = DataRepository.dataItemRunning().getComponentRunningShine();
                if (componentRunningShine.supportBeautyLevel()) {
                    CameraSettings.setFaceBeautyLevel(3);
                } else if (componentRunningShine.supportSmoothLevel()) {
                    CameraSettings.setFaceBeautySmoothLevel(40);
                }
                baseModule.updatePreferenceInWorkThread(13);
            }
        }
    }

    private void updateVideoOrientation(int i) {
        if ((i > 315 && i <= 360) || (i >= 0 && i <= 45)) {
            this.mCurrentScreenOrientation = 0;
        } else if (i > 45 && i <= 135) {
            this.mCurrentScreenOrientation = 90;
        } else if (i > 135 && i <= 225) {
            this.mCurrentScreenOrientation = 180;
        } else if (i > 225 && i <= 315) {
            this.mCurrentScreenOrientation = 270;
        }
    }

    static /* synthetic */ void zf() {
        MimojiModeProtocol.MimojiBottomList mimojiBottomList = (MimojiModeProtocol.MimojiBottomList) ModeCoordinatorImpl.getInstance().getAttachProtocol(248);
        if (mimojiBottomList != null) {
            mimojiBottomList.firstProgressShow(false);
        } else {
            Log.i(TAG, "mimojiBottomList finish == null");
        }
    }

    public /* synthetic */ void Af() {
        if (this.mRecordModule != null) {
            deleteMimojiCache(1);
            FileUtils.makeNoMediaDir(MimojiHelper2.VIDEO_CACHE_DIR);
            this.mRecordModule.startRecording(getVideoCache(), this.mRecordingListener, this.mCurrentScreenOrientation, this.mPreviewWidth, this.mPreviewHeight, 10000000, CameraSettings.getVideoEncoder() == 5 ? "video/hevc" : "video/avc");
        }
    }

    public /* synthetic */ void b(int i, int i2, int i3, int i4, boolean z) {
        synchronized (this.mAvatarLock) {
            String str = TAG;
            Log.d(str, "avatar start init | " + i);
            this.mAvatar = AvatarEngineManager2.getInstance().queryAvatar();
            if (this.mRecordModule == null) {
                this.mRecordModule = new RecordModule(this.mContext, this.mCaptureCallback);
                this.mRecordModule.init(i2, i3, i4, this.mAvatar, z);
            } else {
                this.mRecordModule.setmImageOrientation(i2);
                this.mRecordModule.setMirror(z);
                this.mRecordModule.setPreviewSize(i3, i4);
            }
            Rect previewRect = Util.getPreviewRect(this.mContext);
            boolean isGifOn = CameraSettings.isGifOn();
            this.mRecordModule.setBackgroundToSquare(isGifOn);
            if (isGifOn) {
                this.mRecordModule.setDrawScope(0, (Util.sWindowHeight - previewRect.bottom) - (Util.sWindowHeight / 4), 500, 500);
            } else {
                this.mRecordModule.setDrawScope(0, Util.sWindowHeight - previewRect.bottom, previewRect.right, previewRect.bottom - previewRect.top);
            }
            ArrayList<BackgroundInfo> backgroundBmpInfo = this.mRecordModule.getBackgroundBmpInfo(AvatarEngineManager2.BgTemplatePath);
            if (backgroundBmpInfo != null) {
                AvatarEngineManager2.getInstance().setBackgroundInfos(backgroundBmpInfo);
            }
            this.mDrawSize = new Size(previewRect.right, previewRect.bottom - previewRect.top);
            this.mIsStopRender = false;
            if (!this.mIsAvatarInited) {
                Log.d(TAG, "avatar need really init");
                this.mAvatar.init(AvatarEngineManager2.TRACK_DATA, AvatarEngineManager2.FACE_MODEL);
                this.mAvatar.setRenderScene(true, 1.0f);
                this.mAvatar.createOutlineEngine(AvatarEngineManager2.TRACK_DATA);
                reloadConfig();
            }
            onMimojiInitFinish();
        }
    }

    @Override // com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiAvatarEngine2
    public void backToPreview(boolean z, boolean z2) {
        MimojiInfo2 currentMimojiInfo = this.mMimojiStatusManager2.getCurrentMimojiInfo();
        this.mMimojiStatusManager2.setMode(2);
        this.mIsStopRender = false;
        onMimojiSelect(currentMimojiInfo);
        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
        ((ModeProtocol.ActionProcessing) ModeCoordinatorImpl.getInstance().getAttachProtocol(162)).processingMimojiBack();
        bottomPopupTips.reInitTipImage();
        topAlert.alertMimojiFaceDetect(false, -1);
        if (this.mMimojiStatusManager2.getMimojiRecordState() != 1 || !CameraSettings.isFrontCamera()) {
            topAlert.enableMenuItem(true, 197, 193, 162);
        } else {
            topAlert.enableMenuItem(true, 197, 162);
        }
        if (z2) {
            if (DataRepository.dataItemLive().getMimojiStatusManager2().getMimojiPanelState() != 1) {
                bottomPopupTips.showMimojiPanel(1);
            }
            bottomPopupTips.updateMimojiBottomTipImage();
            bottomPopupTips.hideCenterTipImage();
        }
        AvatarEngine avatarEngine = this.mAvatar;
        if (avatarEngine != null) {
            avatarEngine.setRenderScene(true, 1.0f);
        }
        setDisableSingleTapUp(false);
    }

    public boolean changeIsNoFaceResult(boolean z) {
        if (!z) {
            int i = this.mIsNoFaceResult;
            if (i > 5) {
                return true;
            }
            this.mIsNoFaceResult = i + 1;
        } else if (this.mIsNoFaceResult != 0) {
            this.mIsNoFaceResult = 0;
            return true;
        }
        return false;
    }

    @Override // com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiAvatarEngine2
    public void changeToSquare(boolean z) {
        this.mSquare = z;
        this.mRecordModule.setBackgroundToSquare(this.mSquare);
        Rect previewRect = Util.getPreviewRect(this.mContext);
        if (z) {
            RecordModule recordModule = this.mRecordModule;
            int i = Util.sWindowHeight;
            recordModule.setDrawScope(0, (i - previewRect.bottom) - (i / 4), 500, 500);
        }
    }

    public void checkIsNeedChangBg() {
        if (!DataRepository.dataItemLive().getMimojiStatusManager2().IsLoading()) {
            String componentValue = DataRepository.dataItemConfig().getComponentConfigRatio().getComponentValue(181);
            MimojiBgInfo currentMimojiBgInfo = this.mMimojiStatusManager2.getCurrentMimojiBgInfo();
            boolean isGifOn = CameraSettings.isGifOn();
            if (currentMimojiBgInfo != null && !this.mMimojiStatusManager2.isInMimojiCreate() && !isGifOn) {
                MimojiBgInfo mimojiBgInfo = this.mCurrentTempMimojiBgInfo;
                if (mimojiBgInfo == null || !mimojiBgInfo.getBackgroundInfo().getName().equals(currentMimojiBgInfo.getBackgroundInfo().getName())) {
                    this.mCurrentTempMimojiBgInfo = currentMimojiBgInfo.clone();
                    this.mAvatar.setRenderScene(false, 1.0f);
                }
                if (this.mCurrentTempMimojiBgInfo.getIsNeedRefresh()) {
                    this.mRecordModule.setBackgroundToSquare(false);
                    char c2 = 65535;
                    int hashCode = componentValue.hashCode();
                    if (hashCode != 53743) {
                        if (hashCode == 1515430 && componentValue.equals(ComponentConfigRatio.RATIO_16X9)) {
                            c2 = 1;
                        }
                    } else if (componentValue.equals(ComponentConfigRatio.RATIO_4X3)) {
                        c2 = 0;
                    }
                    if (c2 == 0) {
                        this.mCurrentTempMimojiBgInfo.getBackgroundInfo().setResolutionMode(1);
                    } else if (c2 != 1) {
                        this.mCurrentTempMimojiBgInfo.getBackgroundInfo().setResolutionMode(3);
                    } else {
                        this.mCurrentTempMimojiBgInfo.getBackgroundInfo().setResolutionMode(2);
                    }
                    try {
                        Bitmap decodeStream = BitmapFactory.decodeStream(new FileInputStream(this.mCurrentTempMimojiBgInfo.getBackgroundInfo().getBackGroundPath(this.mCurrentTempMimojiBgInfo.nextFrame())));
                        this.mRecordModule.setBackground(decodeStream, this.mCurrentTempMimojiBgInfo.getBackgroundInfo());
                        decodeStream.recycle();
                    } catch (FileNotFoundException e2) {
                        this.mCurrentTempMimojiBgInfo = null;
                        Log.e(TAG, "checkIsNeedChangBg  : " + e2.getMessage());
                    }
                }
            } else if (this.mCurrentTempMimojiBgInfo != null) {
                this.mCurrentTempMimojiBgInfo = null;
                this.mRecordModule.setBackground(null, null);
            }
        }
    }

    @Override // com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiAvatarEngine2
    public boolean deleteMimojiCache(int i) {
        String str = TAG;
        Log.d(str, "mimoji boolean deleteMimojiCache[type] : " + i);
        if (i == 0) {
            FileUtils.deleteFile(MimojiHelper2.VIDEO_CACHE_DIR);
            FileUtils.deleteFile(MimojiHelper2.EMOTICON_CACHE_DIR);
        } else if (i == 1) {
            FileUtils.deleteFile(MimojiHelper2.VIDEO_CACHE_DIR);
        } else if (i == 2) {
            try {
                FileUtils.deleteFile(MimojiHelper2.EMOTICON_CACHE_DIR);
            } catch (Exception e2) {
                String str2 = TAG;
                Log.e(str2, "mimoji void deleteMimojiCache[] " + e2.getMessage());
                return false;
            }
        }
        return true;
    }

    public boolean getIsNoFaceResult() {
        return this.mIsNoFaceResult <= 5;
    }

    /* access modifiers changed from: protected */
    public int getOperatingMode() {
        return CameraCapabilities.SESSION_OPERATION_MODE_MIMOJI;
    }

    @Override // com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiAvatarEngine2
    public String getTimeValue() {
        return Util.millisecondToTimeString(Util.clamp(this.mTotalRecordingTime, 1000, 15000), false, true);
    }

    @Override // com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiAvatarEngine2
    public String getVideoCache() {
        return CameraSettings.isGifOn() ? MimojiHelper2.GIF_CACHE_FILE : MimojiHelper2.VIDEO_NORMAL_CACHE_FILE;
    }

    @Override // com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiAvatarEngine2
    public void initAvatarEngine(int i, int i2, int i3, int i4, boolean z) {
        String str = TAG;
        Log.d(str, "initAvatarEngine with parameters : displayOrientation = " + i + ", width = " + i3 + ", height = " + i4 + ", isFrontCamera = " + z);
        this.mDisplayOrientation = i;
        this.mPreviewWidth = i3;
        this.mPreviewHeight = i4;
        this.mIsFrontCamera = z;
        this.mOrientation = i2;
        initMimojiResource();
        this.mLoadHandler.post(new a(this, hashCode(), i, i3, i4, z));
    }

    public boolean isNeedShowAvatar() {
        MimojiInfo2 currentMimojiInfo = this.mMimojiStatusManager2.getCurrentMimojiInfo();
        return currentMimojiInfo != null && this.mAvatar != null && !TextUtils.isEmpty(currentMimojiInfo.mConfigPath) && !currentMimojiInfo.mConfigPath.equals("add_state") && !currentMimojiInfo.mConfigPath.equals("close_state") && !this.mMimojiStatusManager2.isInMimojiCreate();
    }

    @Override // com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiAvatarEngine2
    public boolean isOnCreateMimoji() {
        return this.mMimojiStatusManager2.isInMimojiCreate();
    }

    @Override // com.android.camera.SurfaceTextureScreenNail.ExternalFrameProcessor
    public boolean isProcessorReady() {
        return this.mRecordModule != null;
    }

    @Override // com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiAvatarEngine2
    public boolean isRecordStopping() {
        return this.mIsRecordStopping;
    }

    @Override // com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiAvatarEngine2
    public boolean isRecording() {
        String str = TAG;
        Log.d(str, "Recording = " + this.mIsRecording);
        return this.mIsRecording;
    }

    public boolean isShowGifOperate() {
        return CameraSettings.isGifOn();
    }

    @Override // com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiAvatarEngine2
    public void onCaptureImage() {
        ActivityBase activityBase;
        if (this.mRecordModule != null && (activityBase = this.mActivityBase) != null) {
            this.mNeedCapture = true;
            CameraStatUtils.trackMimojiCaptureOrRecord(getMimojiPara(), CameraSettings.getFlashMode(activityBase.getCurrentModuleIndex()), true, this.mIsFrontCamera);
        }
    }

    @Override // com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiAvatarEngine2
    public boolean onCreateCapture() {
        Log.d(TAG, "onCreateCapture");
        if (this.mFaceDectectResult != 0 || !this.mIsFaceDetectSuccess || this.mActivityBase == null) {
            return false;
        }
        releaseRender();
        Module currentModule = this.mActivityBase.getCurrentModule();
        if (currentModule instanceof MimojiModule) {
            CameraSettings.setFaceBeautyLevel(0);
            ((MimojiModule) currentModule).updatePreferenceInWorkThread(13);
        }
        ((ModeProtocol.ActionProcessing) ModeCoordinatorImpl.getInstance().getAttachProtocol(162)).showOrHideMimojiProgress(true);
        ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
        if (bottomPopupTips != null) {
            bottomPopupTips.showTips(19, R.string.mimoji_start_create, 2);
        }
        this.mIsShutterButtonClick = true;
        animateCapture();
        return true;
    }

    @Override // com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiAvatarEngine2
    public void onDeviceRotationChange(int i) {
        this.mDeviceRotation = i;
        updateVideoOrientation(i);
        MimojiModeProtocol.MimojiEditor2 mimojiEditor2 = this.mMimojiEditor2;
        if (mimojiEditor2 != null) {
            mimojiEditor2.onDeviceRotationChange(i);
        }
    }

    @Override // com.android.camera.SurfaceTextureScreenNail.ExternalFrameProcessor, com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiAvatarEngine2
    public void onDrawFrame(Rect rect, int i, int i2, boolean z) {
        if (this.mRecordModule != null && rect != null && !this.mIsStopRender) {
            boolean z2 = false;
            if (z) {
                GLES20.glViewport(0, 0, i, i2);
            } else {
                int i3 = Util.sWindowHeight;
                int i4 = rect.bottom;
                GLES20.glViewport(0, i3 - i4, rect.right, i4 - rect.top);
                if (this.mNeedCapture) {
                    Log.d(TAG, "onCapture start");
                    this.mRecordModule.capture();
                    ActivityBase activityBase = this.mActivityBase;
                    if (activityBase != null) {
                        ((MimojiModule) activityBase.getCurrentModule()).setCameraStatePublic(3);
                    }
                    this.mNeedCapture = false;
                }
            }
            GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
            GLES20.glClear(16384);
            RecordModule recordModule = this.mRecordModule;
            boolean z3 = this.mIsFrontCamera;
            int i5 = this.mDeviceRotation;
            int[] iArr = this.mTextureId;
            if (isNeedShowAvatar() && !getIsNoFaceResult()) {
                z2 = true;
            }
            recordModule.startRender(90, z3, i5, 0, false, iArr, null, z2);
        }
    }

    @Override // com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiAvatarEngine2
    public void onMimojiChangeBg(MimojiBgInfo mimojiBgInfo) {
        this.mMimojiStatusManager2.setCurrentMimojiBgInfo(mimojiBgInfo);
        String str = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append(" onMimojiChangeBg  : ");
        sb.append(mimojiBgInfo == null ? "null" : mimojiBgInfo.getBackgroundInfo().getName());
        Log.d(str, sb.toString());
    }

    @Override // com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiAvatarEngine2
    public void onMimojiChangeTimbre(MimojiTimbreInfo mimojiTimbreInfo) {
        this.mMimojiStatusManager2.setCurrentMimojiTimbreInfo(mimojiTimbreInfo);
        String str = TAG;
        Log.d(str, "mimoji void onMimojiChangeTimbre[mimojiTimbreInfo]" + mimojiTimbreInfo);
    }

    @Override // com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiAvatarEngine2
    public void onMimojiCreate() {
        Log.d(TAG, "start create mimoji");
        this.mMimojiStatusManager2.setMode(4);
        this.mMainProtocol.mimojiStart();
        ((ModeProtocol.RecordState) ModeCoordinatorImpl.getInstance().getAttachProtocol(212)).prepareCreateMimoji();
        ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
        if (bottomPopupTips != null) {
            bottomPopupTips.showTips(19, R.string.mimoji_create_tips, 2);
        }
    }

    @Override // com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiAvatarEngine2
    public void onMimojiDeleted() {
        this.mMimojiStatusManager2.setCurrentMimojiInfo(null);
        this.mRecordModule.resetExtraScene();
    }

    @Override // com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiAvatarEngine2
    public void onMimojiInitFinish() {
        Log.d(TAG, "onMimojiInitFinish");
        this.mCameraView.requestRender();
        setIsAvatarInited(true);
        this.mRecordModule.updateAvatarConfigInfo(this.mAvatar);
    }

    @Override // com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiAvatarEngine2
    public void onMimojiSelect(MimojiInfo2 mimojiInfo2) {
        Log.d(TAG, "mimoji void onMimojiSelect[mimojiInfo2]");
        if (mimojiInfo2 == null || TextUtils.isEmpty(mimojiInfo2.mConfigPath) || mimojiInfo2.mConfigPath.equals("add_state") || mimojiInfo2.mConfigPath.equals("close_state") || this.mAvatar == null) {
            this.mMimojiStatusManager2.setCurrentMimojiInfo(null);
            MimojiModeProtocol.MimojiBottomList mimojiBottomList = (MimojiModeProtocol.MimojiBottomList) ModeCoordinatorImpl.getInstance().getAttachProtocol(248);
            if (mimojiBottomList != null) {
                mimojiBottomList.refreshMimojiList();
                return;
            }
            return;
        }
        this.mMimojiStatusManager2.setCurrentMimojiInfo(mimojiInfo2);
        String str = mimojiInfo2.mAvatarTemplatePath;
        String str2 = mimojiInfo2.mConfigPath;
        String str3 = TAG;
        Log.d(str3, "change mimoji with path = " + str + ", and config = " + str2);
        synchronized (this.mAvatarLock) {
            this.mRecordModule.resetExtraScene();
            boolean equals = this.mAvatarTemplatePath.equals(str);
            this.mAvatarTemplatePath = str;
            if (str2.isEmpty() || AvatarEngineManager2.isPrefabModel(str2)) {
                if (!equals) {
                    this.mAvatar.setTemplatePath(str);
                }
            } else if (!equals) {
                this.mRecordModule.changeHumanTemplate(str, str2);
            } else {
                this.mAvatar.loadConfig(str2);
            }
            this.mRecordModule.updateAvatarConfigInfo(this.mAvatar);
        }
    }

    @Override // com.android.camera2.Camera2Proxy.PreviewCallback
    public boolean onPreviewFrame(Image image, Camera2Proxy camera2Proxy, int i) {
        boolean startProcess;
        if (this.mRecordModule == null) {
            Log.d(TAG, "MimojiAvatarEngine2Impl onPreviewFrame mRecordModule null");
            return true;
        }
        this.mMimojiEditor2 = (MimojiModeProtocol.MimojiEditor2) ModeCoordinatorImpl.getInstance().getAttachProtocol(247);
        if (!this.mMimojiStatusManager2.isInMimojiEdit() || (this.mIsAvatarInited && this.mAvatar != null)) {
            ASVLOFFSCREEN buildNV21SingleBuffer = AsvloffscreenUtil.buildNV21SingleBuffer(image);
            if (this.mIsShutterButtonClick) {
                this.mIsShutterButtonClick = false;
                setIsAvatarInited(false);
                Bitmap rotateBitmap = BitmapUtils2.rotateBitmap(BitmapUtils2.rawByteArray2RGBABitmap(buildNV21SingleBuffer.getYData(), image.getWidth(), image.getHeight(), image.getPlanes()[0].getRowStride()), this.mIsFrontCamera ? -90 : 90);
                int width = rotateBitmap.getWidth();
                int height = rotateBitmap.getHeight();
                ByteBuffer order = ByteBuffer.allocate(rotateBitmap.getRowBytes() * rotateBitmap.getHeight()).order(ByteOrder.nativeOrder());
                rotateBitmap.copyPixelsToBuffer(order);
                createAvatar(order.array(), width, height);
            }
            if (!this.mMimojiStatusManager2.isInMimojiEdit()) {
                if (this.mMimojiEditor2 != null && !this.mMimojiStatusManager2.isInMimojiEmoticon()) {
                    this.mMimojiEditor2.requestRender(true);
                    this.mMimojiEditor2.resetClickEnable(false);
                }
                synchronized (this.mAvatarLock) {
                    checkIsNeedChangBg();
                    startProcess = this.mRecordModule.startProcess(buildNV21SingleBuffer, MimojiHelper2.getOutlineOrientation(this.mOrientation, this.mDeviceRotation, this.mIsFrontCamera), isNeedShowAvatar());
                }
                if (changeIsNoFaceResult(startProcess) && !this.mMimojiStatusManager2.isInMimojiCreate()) {
                    this.mUiHandler.post(new Runnable() {
                        /* class com.android.camera.features.mimoji2.module.impl.MimojiAvatarEngine2Impl.AnonymousClass6 */

                        public void run() {
                            ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
                            if (topAlert != null) {
                                topAlert.alertMimojiFaceDetect(MimojiAvatarEngine2Impl.this.getIsNoFaceResult() && MimojiAvatarEngine2Impl.this.isNeedShowAvatar(), R.string.mimoji_check_no_face);
                            }
                        }
                    });
                }
                boolean isNoFaceResult = getIsNoFaceResult();
                if (this.mLastNeedBeauty != isNoFaceResult) {
                    this.mLastNeedBeauty = isNoFaceResult;
                    updateBeauty();
                }
                this.mCameraView.requestRender();
            } else if (this.mMimojiEditor2 != null) {
                AvatarConfig.ASAvatarProcessInfo aSAvatarProcessInfo = new AvatarConfig.ASAvatarProcessInfo();
                synchronized (this.mAvatarLock) {
                    this.mAvatar.avatarProcessWithInfoEx(buildNV21SingleBuffer, 90, this.mIsFrontCamera, this.mOrientation, aSAvatarProcessInfo, true);
                }
                this.mMimojiEditor2.requestRender(false);
                this.mMimojiEditor2.resetClickEnable(true);
            }
            if (this.mMimojiStatusManager2.isInMimojiCreate()) {
                synchronized (this.mAvatarLock) {
                    this.mFaceDectectResult = this.mAvatar.outlineProcessEx(buildNV21SingleBuffer, MimojiHelper2.getOutlineOrientation(this.mOrientation, this.mDeviceRotation, this.mIsFrontCamera));
                }
                ModeProtocol.MainContentProtocol mainContentProtocol = this.mMainProtocol;
                if (mainContentProtocol != null) {
                    mainContentProtocol.mimojiFaceDetect(this.mFaceDectectResult);
                }
            }
            return true;
        }
        MimojiModeProtocol.MimojiEditor2 mimojiEditor2 = this.mMimojiEditor2;
        if (mimojiEditor2 != null) {
            mimojiEditor2.resetClickEnable(false);
            this.mMimojiEditor2.requestRender(true);
        }
        Log.d(TAG, "MimojiAvatarEngine2Impl onPreviewFrame need init, waiting......");
        return true;
    }

    @Override // com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiAvatarEngine2
    public void onRecordStart() {
        ActivityBase activityBase;
        Log.d(TAG, "start record...");
        if (!this.mIsRecording && (activityBase = this.mActivityBase) != null) {
            CameraStatUtils.trackMimojiCaptureOrRecord(getMimojiPara(), CameraSettings.getFlashMode(activityBase.getCurrentModuleIndex()), false, this.mIsFrontCamera);
            this.mIsRecording = true;
            this.mIsRecordStopping = false;
            this.mCameraView.queueEvent(new g(this));
            updateRecordingTime();
        }
    }

    @Override // com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiAvatarEngine2
    public void onRecordStop(boolean z) {
        Log.d(TAG, "stop record...");
        this.mIsRecordStopping = true;
        if (z) {
            this.mGetThumCountDownLatch = new CountDownLatch(1);
        }
        CountDownTimer countDownTimer = this.mCountDownTimer;
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        this.mCameraView.queueEvent(new Runnable() {
            /* class com.android.camera.features.mimoji2.module.impl.MimojiAvatarEngine2Impl.AnonymousClass4 */

            public void run() {
                if (MimojiAvatarEngine2Impl.this.mRecordModule != null) {
                    new Thread(new Runnable() {
                        /* class com.android.camera.features.mimoji2.module.impl.MimojiAvatarEngine2Impl.AnonymousClass4.AnonymousClass1 */

                        public void run() {
                            MimojiAvatarEngine2Impl.this.mRecordModule.stopRecording();
                        }
                    }).start();
                }
            }
        });
    }

    @Override // com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiAvatarEngine2
    public void onResume() {
        Log.d(TAG, MistatsConstants.BaseEvent.RESET);
        RecordModule recordModule = this.mRecordModule;
        if (recordModule != null) {
            recordModule.reset();
        }
    }

    public /* synthetic */ void r(String str) {
        long currentTimeMillis = System.currentTimeMillis();
        try {
            Util.verifyFileZip(this.mContext, "vendor/camera/mimoji/model2.zip", MimojiHelper2.MIMOJI_DIR, 32768);
            String str2 = TAG;
            Log.d(str2, "init model spend time = " + (System.currentTimeMillis() - currentTimeMillis));
            DataRepository.dataItemLive().getMimojiStatusManager2().setIsLoading(false);
            CameraSettings.setMimojiModleVersion(str);
            String str3 = TAG;
            Log.i(str3, "mAvatarTemplatePath = " + this.mAvatarTemplatePath);
            this.mUiHandler.post(b.INSTANCE);
        } catch (Exception e2) {
            Log.e(TAG, "verify asset model zip failed...", e2);
            FileUtils.delDir(MimojiHelper2.MIMOJI_DIR);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.BaseProtocol
    public void registerProtocol() {
        ModeCoordinatorImpl.getInstance().attachProtocol(246, this);
    }

    @Override // com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiAvatarEngine2
    public void release() {
        Log.d(TAG, "avatar release");
        DataRepository.dataItemLive().getMimojiStatusManager2().setCurrentMimojiBgInfo(null);
        CountDownLatch countDownLatch = this.mGetThumCountDownLatch;
        if (countDownLatch != null) {
            try {
                countDownLatch.await();
            } catch (InterruptedException e2) {
                e2.printStackTrace();
            }
        }
        final int hashCode = hashCode();
        this.mLoadHandler.post(new Runnable() {
            /* class com.android.camera.features.mimoji2.module.impl.MimojiAvatarEngine2Impl.AnonymousClass2 */

            public void run() {
                synchronized (MimojiAvatarEngine2Impl.this.mAvatarLock) {
                    if (MimojiAvatarEngine2Impl.this.mAvatar != null) {
                        String access$200 = MimojiAvatarEngine2Impl.TAG;
                        Log.d(access$200, "avatar destroy | " + hashCode);
                        MimojiAvatarEngine2Impl.this.mAvatar.saveConfig(AvatarEngineManager2.TempEditConfigPath);
                        MimojiAvatarEngine2Impl.this.mAvatar.destroyOutlineEngine();
                        MimojiAvatarEngine2Impl.this.mAvatar.unInit();
                        if (MimojiAvatarEngine2Impl.this.mRecordModule != null) {
                            MimojiAvatarEngine2Impl.this.mRecordModule.resetExtraScene();
                            MimojiAvatarEngine2Impl.this.mRecordModule.unInit();
                        }
                        AvatarEngineManager2.getInstance().release();
                    }
                }
            }
        });
        FileOutputStream fileOutputStream = this.mVideoFileStream;
        if (fileOutputStream != null) {
            try {
                fileOutputStream.close();
            } catch (IOException e3) {
                Log.e(TAG, "fail to close file stream", e3);
            }
            this.mVideoFileStream = null;
        }
        this.mActivityBase = null;
    }

    @Override // com.android.camera.SurfaceTextureScreenNail.ExternalFrameProcessor, com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiAvatarEngine2
    public void releaseRender() {
        final int hashCode = hashCode();
        this.mIsStopRender = true;
        if (this.mMimojiStatusManager2.isInPreviewSurface()) {
            this.mCameraView.queueEvent(new Runnable() {
                /* class com.android.camera.features.mimoji2.module.impl.MimojiAvatarEngine2Impl.AnonymousClass1 */

                public void run() {
                    synchronized (MimojiAvatarEngine2Impl.this.mAvatarLock) {
                        if (MimojiAvatarEngine2Impl.this.mAvatar != null) {
                            String access$200 = MimojiAvatarEngine2Impl.TAG;
                            Log.d(access$200, "releaseRender | " + hashCode);
                            MimojiAvatarEngine2Impl.this.mAvatar.releaseRender();
                        }
                    }
                }
            });
            return;
        }
        MimojiModeProtocol.MimojiEditor2 mimojiEditor2 = this.mMimojiEditor2;
        if (mimojiEditor2 != null) {
            mimojiEditor2.releaseRender();
        }
    }

    @Override // com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiAvatarEngine2
    public void setDetectSuccess(boolean z) {
        this.mIsFaceDetectSuccess = z;
    }

    @Override // com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiAvatarEngine2
    public void setDisableSingleTapUp(boolean z) {
        ActivityBase activityBase = this.mActivityBase;
        if (activityBase != null) {
            ((MimojiModule) activityBase.getCurrentModule()).setDisableSingleTapUp(z);
        }
    }

    public void setIsAvatarInited(boolean z) {
        this.mIsAvatarInited = z;
    }

    @Override // com.android.camera.protocol.ModeProtocol.BaseProtocol
    public void unRegisterProtocol() {
        ModeCoordinatorImpl.getInstance().detachProtocol(246, this);
        release();
        quit();
    }

    /* access modifiers changed from: protected */
    public void updateRecordingTime() {
        CountDownTimer countDownTimer = this.mCountDownTimer;
        if (countDownTimer != null) {
            this.mTotalRecordingTime = 0;
            countDownTimer.cancel();
        }
        if (CameraSettings.isGifOn()) {
            this.mMaxVideoDurationInMs = 5000;
        } else {
            this.mMaxVideoDurationInMs = 15000;
        }
        this.mCountDownTimer = new CountDownTimer(START_OFFSET_MS + ((long) this.mMaxVideoDurationInMs), 1000) {
            /* class com.android.camera.features.mimoji2.module.impl.MimojiAvatarEngine2Impl.AnonymousClass3 */

            public void onFinish() {
                if (MimojiAvatarEngine2Impl.this.mActivityBase != null) {
                    ((MimojiModule) MimojiAvatarEngine2Impl.this.mActivityBase.getCurrentModule()).stopVideoRecording(false);
                }
            }

            public void onTick(long j) {
                String millisecondToTimeString = Util.millisecondToTimeString((950 + j) - MimojiAvatarEngine2Impl.START_OFFSET_MS, false);
                MimojiAvatarEngine2Impl mimojiAvatarEngine2Impl = MimojiAvatarEngine2Impl.this;
                long unused = mimojiAvatarEngine2Impl.mTotalRecordingTime = (((long) mimojiAvatarEngine2Impl.mMaxVideoDurationInMs) - j) + MimojiAvatarEngine2Impl.START_OFFSET_MS;
                ModeProtocol.ActionProcessing actionProcessing = (ModeProtocol.ActionProcessing) ModeCoordinatorImpl.getInstance().getAttachProtocol(162);
                if (actionProcessing != null) {
                    actionProcessing.updateRecordingTime(millisecondToTimeString);
                }
            }
        };
        this.mCountDownTimer.start();
    }

    public /* synthetic */ void xf() {
        setDisableSingleTapUp(true);
        onProfileFinish();
    }

    public /* synthetic */ void yf() {
        ModeProtocol.RecordState recordState = (ModeProtocol.RecordState) ModeCoordinatorImpl.getInstance().getAttachProtocol(212);
        if (recordState != null) {
            recordState.onPostSavingFinish();
        }
        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        if (topAlert != null) {
            topAlert.alertMimojiFaceDetect(false, -1);
        }
        this.mMainProtocol.mimojiEnd();
        ((MimojiModule) this.mActivityBase.getCurrentModule()).onMimojiCreateCompleted(false);
        ((ModeProtocol.ActionProcessing) ModeCoordinatorImpl.getInstance().getAttachProtocol(162)).showOrHideMimojiProgress(false);
        backToPreview(false, false);
    }
}
