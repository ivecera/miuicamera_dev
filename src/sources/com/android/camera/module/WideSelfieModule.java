package com.android.camera.module;

import a.a.a;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CaptureResult;
import android.location.Location;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.text.format.DateFormat;
import android.view.KeyEvent;
import android.view.Surface;
import android.view.View;
import com.android.camera.AutoLockManager;
import com.android.camera.BasePreferenceActivity;
import com.android.camera.Camera;
import com.android.camera.CameraAppImpl;
import com.android.camera.CameraPreferenceActivity;
import com.android.camera.CameraScreenNail;
import com.android.camera.CameraSettings;
import com.android.camera.CameraSize;
import com.android.camera.ImageHelper;
import com.android.camera.LocationManager;
import com.android.camera.OnClickAttr;
import com.android.camera.PictureSizeManager;
import com.android.camera.R;
import com.android.camera.Thumbnail;
import com.android.camera.ToastUtils;
import com.android.camera.Util;
import com.android.camera.VibratorUtils;
import com.android.camera.beautyshot.BeautyShot;
import com.android.camera.constant.UpdateConstant;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.runing.ComponentRunningShine;
import com.android.camera.effect.EffectController;
import com.android.camera.effect.FaceAnalyzeInfo;
import com.android.camera.effect.FilterInfo;
import com.android.camera.fragment.beauty.BeautyValues;
import com.android.camera.log.Log;
import com.android.camera.module.loader.FunctionParseAsdFace;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera.statistic.CameraStatUtils;
import com.android.camera.statistic.MistatsConstants;
import com.android.camera.storage.MediaProviderUtil;
import com.android.camera.storage.Storage;
import com.android.camera.wideselfie.WideSelfieConfig;
import com.android.camera.wideselfie.WideSelfieEngineWrapper;
import com.android.camera2.Camera2Proxy;
import com.android.camera2.CameraHardwareFace;
import com.android.gallery3d.exif.ExifHelper;
import com.arcsoft.camera.utils.d;
import com.mi.config.b;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.disposables.Disposable;
import java.lang.ref.WeakReference;
import java.util.HashMap;

@TargetApi(21)
public class WideSelfieModule extends BaseModule implements ModeProtocol.CameraAction, Camera2Proxy.CameraPreviewCallback, WideSelfieEngineWrapper.WideSelfStateCallback, Camera2Proxy.FaceDetectionCallback {
    private static final int MIN_SHOOTING_TIME = 600;
    public static final int STOP_ROTATION_THRESHOLD = 60;
    private static final String TAG = "WideSelfieModule";
    private final int MAX_PICTURE_PIXEL = 6000000;
    private int MOVE_DISTANCE;
    private int MOVE_DISTANCE_VERTICAL;
    public int OFFSET_VERTICAL_X_STOP_CAPTURE_THRESHOLD;
    public int OFFSET_X_HINT_THRESHOLD;
    public int OFFSET_X_STOP_CAPTURE_THRESHOLD;
    public int OFFSET_Y_HINT_THRESHOLD;
    public int OFFSET_Y_STOP_CAPTURE_THRESHOLD;
    private BeautyValues mBeautyValues;
    private int mCaptureOrientation = -1;
    private final Object mDeviceLock = new Object();
    private boolean mFaceDetectionEnabled;
    private boolean mFaceDetectionStarted;
    private String mFileNameTemplate;
    /* access modifiers changed from: private */
    public byte[] mFirstFrameNv21Data;
    private boolean mIsContinuousVibratoring = false;
    private volatile boolean mIsPrepareSaveTask = false;
    private volatile boolean mIsShooting = false;
    private int mJpegRotation;
    private int mLastMoveDirection = -1;
    private int mLastVibratorProgress;
    private int mMaxMoveWidth;
    private Disposable mMetaDataDisposable;
    /* access modifiers changed from: private */
    public FlowableEmitter<CaptureResult> mMetaDataFlowableEmitter;
    private SaveOutputImageTask mSaveOutputImageTask;
    private long mShootingStartTime;
    /* access modifiers changed from: private */
    public boolean mShowWarningToast;
    private int mStillPreviewWidth;
    private String mStopCaptureMode;
    private boolean mStopedForRotation = false;
    private int mTargetFocusMode = 4;
    /* access modifiers changed from: private */
    public int mToastOffsetY;
    /* access modifiers changed from: private */
    public WideSelfieEngineWrapper mWideSelfEngine;

    private class MainHandler extends Handler {
        public MainHandler(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message message) {
            if (message.what == 45) {
                Log.d(WideSelfieModule.TAG, "onMessage MSG_ABANDON_HANDLER setActivity null");
                WideSelfieModule.this.setActivity(null);
            }
            if (!WideSelfieModule.this.isCreated()) {
                removeCallbacksAndMessages(null);
            } else if (WideSelfieModule.this.getActivity() != null) {
                int i = message.what;
                if (i == 2) {
                    WideSelfieModule.this.getWindow().clearFlags(128);
                } else if (i == 17) {
                    ((BaseModule) WideSelfieModule.this).mHandler.removeMessages(17);
                    ((BaseModule) WideSelfieModule.this).mHandler.removeMessages(2);
                    WideSelfieModule.this.getWindow().addFlags(128);
                    WideSelfieModule wideSelfieModule = WideSelfieModule.this;
                    ((BaseModule) wideSelfieModule).mHandler.sendEmptyMessageDelayed(2, (long) wideSelfieModule.getScreenDelay());
                } else if (i == 35) {
                    WideSelfieModule wideSelfieModule2 = WideSelfieModule.this;
                    boolean z = false;
                    boolean z2 = message.arg1 > 0;
                    if (message.arg2 > 0) {
                        z = true;
                    }
                    wideSelfieModule2.handleUpdateFaceView(z2, z);
                } else if (i == 51) {
                    Camera camera = ((BaseModule) WideSelfieModule.this).mActivity;
                    if (camera != null && !camera.isActivityPaused()) {
                        WideSelfieModule wideSelfieModule3 = WideSelfieModule.this;
                        ((BaseModule) wideSelfieModule3).mOpenCameraFail = true;
                        wideSelfieModule3.onCameraException();
                    }
                } else if (i == 9) {
                    WideSelfieModule.this.initPreviewLayout();
                    CameraScreenNail cameraScreenNail = ((BaseModule) WideSelfieModule.this).mActivity.getCameraScreenNail();
                    CameraSize cameraSize = ((BaseModule) WideSelfieModule.this).mPreviewSize;
                    cameraScreenNail.setPreviewSize(cameraSize.width, cameraSize.height);
                    WideSelfieEngineWrapper access$100 = WideSelfieModule.this.mWideSelfEngine;
                    WideSelfieModule wideSelfieModule4 = WideSelfieModule.this;
                    CameraSize cameraSize2 = ((BaseModule) wideSelfieModule4).mPreviewSize;
                    int i2 = cameraSize2.width;
                    int i3 = cameraSize2.height;
                    CameraSize cameraSize3 = ((BaseModule) wideSelfieModule4).mPictureSize;
                    access$100.setCameraParameter("1", i2, i3, cameraSize3.width, cameraSize3.height);
                } else if (i == 10) {
                    WideSelfieModule wideSelfieModule5 = WideSelfieModule.this;
                    ((BaseModule) wideSelfieModule5).mOpenCameraFail = true;
                    wideSelfieModule5.onCameraException();
                } else if (!BaseModule.DEBUG) {
                    Log.e(WideSelfieModule.TAG, "no consumer for this message: " + message.what);
                } else {
                    throw new RuntimeException("no consumer for this message: " + message.what);
                }
            }
        }
    }

    private static class SaveOutputImageTask extends AsyncTask<Void, Integer, Integer> {
        private WeakReference<Camera> mActivityRef;
        private int mActualCameraId;
        private BeautyValues mBeautyValues;
        private SaveStateCallback mCallback;
        private final String mFileName;
        private int mHeight;
        private boolean mMirror;
        private byte[] mNv21Data;
        private int mOrientation;
        private String mStopMode;
        private int mTriggerMode;
        private int mWidth;

        public SaveOutputImageTask(@Nullable Camera camera, String str, byte[] bArr, int i, int i2, boolean z, int i3, int i4, int i5, BeautyValues beautyValues, String str2, SaveStateCallback saveStateCallback) {
            this.mFileName = str;
            this.mNv21Data = bArr;
            this.mWidth = i;
            this.mHeight = i2;
            this.mMirror = z;
            this.mActualCameraId = i4;
            this.mOrientation = i3;
            this.mTriggerMode = i5;
            this.mBeautyValues = beautyValues;
            this.mStopMode = str2;
            this.mCallback = saveStateCallback;
            this.mActivityRef = new WeakReference<>(camera);
        }

        private void addImageAsApplication(String str, String str2, byte[] bArr, int i, int i2, int i3) {
            Uri uri;
            long currentTimeMillis = System.currentTimeMillis();
            Location currentLocation = LocationManager.instance().getCurrentLocation();
            if (bArr != null) {
                uri = Storage.addImage(CameraAppImpl.getAndroidContext(), str2, currentTimeMillis, currentLocation, i3, bArr, false, i, i2, false, false, false, true, false, "", null);
            } else {
                ExifHelper.writeExifByFilePath(str, i3, currentLocation, currentTimeMillis);
                uri = Storage.addImageForGroupOrPanorama(CameraAppImpl.getAndroidContext(), str, i3, currentTimeMillis, currentLocation, i, i2);
            }
            if (uri == null) {
                Log.w(WideSelfieModule.TAG, "insert MediaProvider failed, attempt to find uri by path, " + str);
                uri = MediaProviderUtil.getContentUriFromPath(CameraAppImpl.getAndroidContext(), str);
            }
            Log.d(WideSelfieModule.TAG, "addImageAsApplication uri = " + uri + ", path = " + str);
            Camera camera = this.mActivityRef.get();
            if (camera != null) {
                camera.getScreenHint().updateHint();
                if (uri != null) {
                    camera.onNewUriArrived(uri, str2);
                    Thumbnail createThumbnailFromUri = Thumbnail.createThumbnailFromUri(camera, uri, false);
                    Log.d(WideSelfieModule.TAG, "addImageAsApplication Thumbnail = " + createThumbnailFromUri);
                    Util.broadcastNewPicture(camera, uri);
                    camera.getThumbnailUpdater().setThumbnail(createThumbnailFromUri, true, false);
                }
            }
        }

        /* access modifiers changed from: protected */
        public Integer doInBackground(Void... voidArr) {
            int i;
            int i2;
            ComponentRunningShine componentRunningShine = DataRepository.dataItemRunning().getComponentRunningShine();
            if (componentRunningShine.supportBeautyLevel()) {
                i2 = CameraSettings.getBeautyShowLevel();
                i = 0;
            } else if (componentRunningShine.supportSmoothLevel()) {
                i = CameraSettings.getFaceBeautyRatio("pref_beautify_skin_smooth_ratio_key");
                i2 = 0;
            } else {
                i2 = 0;
                i = 0;
            }
            if (i2 > 0 || i > 0) {
                int i3 = DataRepository.dataItemFeature().uc() ? 2 : Util.isGlobalVersion() ? 0 : 1;
                long currentTimeMillis = System.currentTimeMillis();
                BeautyShot beautyShot = new BeautyShot();
                beautyShot.init(CameraAppImpl.getAndroidContext());
                Log.d(WideSelfieModule.TAG, "beautyShot start  mWidth " + this.mWidth + ", mHeight = " + this.mHeight);
                if (i2 > 0) {
                    int i4 = i2 - 1;
                    Log.d(WideSelfieModule.TAG, "beautyLevel " + i4);
                    beautyShot.processByBeautyLevel(this.mNv21Data, this.mWidth, this.mHeight, 270, i3, i4);
                } else if (i > 0) {
                    Log.d(WideSelfieModule.TAG, "beautyLevel smooth " + i);
                    beautyShot.processBySmoothLevel(this.mNv21Data, this.mWidth, this.mHeight, 270, i3, i);
                }
                beautyShot.uninit();
                Log.d(WideSelfieModule.TAG, "beautyShot end, time = " + (System.currentTimeMillis() - currentTimeMillis));
            }
            if (this.mMirror) {
                if (this.mOrientation % 180 == 90) {
                    BeautyShot.flipYuvVertical(this.mNv21Data, this.mWidth, this.mHeight);
                } else {
                    BeautyShot.flipYuvHorizontal(this.mNv21Data, this.mWidth, this.mHeight);
                }
            }
            byte[] encodeNv21ToJpeg = ImageHelper.encodeNv21ToJpeg(this.mNv21Data, this.mWidth, this.mHeight, CameraSettings.getEncodingQuality(false).toInteger(false));
            if (encodeNv21ToJpeg == null) {
                Log.w(WideSelfieModule.TAG, "jpegData is null, can't save");
                return null;
            }
            String generateFilepath4Image = Storage.generateFilepath4Image(this.mFileName, false);
            if (Storage.isUseDocumentMode()) {
                addImageAsApplication(generateFilepath4Image, this.mFileName, encodeNv21ToJpeg, this.mWidth, this.mHeight, this.mOrientation);
            } else {
                d.b(generateFilepath4Image, encodeNv21ToJpeg);
                addImageAsApplication(generateFilepath4Image, this.mFileName, null, this.mWidth, this.mHeight, this.mOrientation);
            }
            HashMap hashMap = new HashMap();
            hashMap.put(MistatsConstants.BaseEvent.COUNT, String.valueOf(1));
            CameraStatUtils.trackGeneralInfo(hashMap, false, false, 176, this.mTriggerMode, true, this.mActualCameraId, this.mBeautyValues, null, MistatsConstants.BaseEvent.AUTO_OFF);
            CameraStatUtils.trackPictureTakenInWideSelfie(this.mStopMode, this.mBeautyValues);
            return null;
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Integer num) {
            super.onPostExecute((Object) num);
            SaveStateCallback saveStateCallback = this.mCallback;
            if (saveStateCallback != null) {
                saveStateCallback.onSaveCompleted();
            }
        }

        /* access modifiers changed from: protected */
        public void onPreExecute() {
            super.onPreExecute();
            Log.w(WideSelfieModule.TAG, "onPreExecute");
            ModeProtocol.RecordState recordState = (ModeProtocol.RecordState) ModeCoordinatorImpl.getInstance().getAttachProtocol(212);
            if (recordState == null) {
                Log.w(WideSelfieModule.TAG, "onPreExecute recordState is null");
            } else {
                recordState.onPostSavingStart();
            }
        }
    }

    public interface SaveStateCallback {
        void onSaveCompleted();
    }

    private void doLaterReleaseIfNeed() {
        Camera camera = ((BaseModule) this).mActivity;
        if (camera != null && camera.isActivityPaused()) {
            ((BaseModule) this).mActivity.pause();
            ((BaseModule) this).mActivity.releaseAll(true, true);
        }
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
            /* class com.android.camera.module.WideSelfieModule.AnonymousClass2 */

            @Override // io.reactivex.FlowableOnSubscribe
            public void subscribe(FlowableEmitter<CaptureResult> flowableEmitter) throws Exception {
                FlowableEmitter unused = WideSelfieModule.this.mMetaDataFlowableEmitter = flowableEmitter;
            }
        }, BackpressureStrategy.DROP).map(new FunctionParseAsdFace(this, isFrontCamera())).subscribe();
    }

    /* access modifiers changed from: private */
    public void initPreviewLayout() {
        if (isAlive()) {
            CameraScreenNail cameraScreenNail = ((BaseModule) this).mActivity.getCameraScreenNail();
            CameraSize cameraSize = ((BaseModule) this).mPreviewSize;
            cameraScreenNail.setPreviewSize(cameraSize.width, cameraSize.height);
            CameraScreenNail cameraScreenNail2 = ((BaseModule) this).mActivity.getCameraScreenNail();
            int width = cameraScreenNail2.getWidth();
            int height = cameraScreenNail2.getHeight();
            int dimensionPixelSize = ((BaseModule) this).mActivity.getResources().getDimensionPixelSize(R.dimen.wide_selfie_still_preview_height);
            int i = (width * dimensionPixelSize) / height;
            ModeProtocol.WideSelfieProtocol wideSelfieProtocol = (ModeProtocol.WideSelfieProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(216);
            if (wideSelfieProtocol != null) {
                CameraSize cameraSize2 = ((BaseModule) this).mPreviewSize;
                wideSelfieProtocol.initPreviewLayout(i, dimensionPixelSize, cameraSize2.width, cameraSize2.height);
                wideSelfieProtocol.resetShootUI();
            }
        }
    }

    private boolean isProcessingSaveTask() {
        SaveOutputImageTask saveOutputImageTask = this.mSaveOutputImageTask;
        return (saveOutputImageTask == null || saveOutputImageTask.getStatus() == AsyncTask.Status.FINISHED) ? false : true;
    }

    private boolean isShootingTooShort() {
        return SystemClock.elapsedRealtime() - this.mShootingStartTime < 600;
    }

    private void keepScreenOnAwhile() {
        Handler handler = ((BaseModule) this).mHandler;
        if (handler != null) {
            handler.sendEmptyMessageDelayed(17, 1000);
        }
    }

    /* access modifiers changed from: private */
    public void onSaveFinish() {
        Log.d(TAG, "onSaveFinish E");
        if (isAlive() && ((BaseModule) this).mCamera2Device != null) {
            enableCameraControls(true);
            if (((BaseModule) this).mAeLockSupported) {
                ((BaseModule) this).mCamera2Device.setAELock(false);
            }
            if (((BaseModule) this).mAwbLockSupported) {
                ((BaseModule) this).mCamera2Device.setAWBLock(false);
            }
            ((BaseModule) this).mCamera2Device.setFocusMode(this.mTargetFocusMode);
            startPreview();
            ModeProtocol.RecordState recordState = (ModeProtocol.RecordState) ModeCoordinatorImpl.getInstance().getAttachProtocol(212);
            if (recordState != null) {
                recordState.onPostSavingFinish();
            }
            Log.d(TAG, "onSaveFinish X");
        }
    }

    private void resetScreenOn() {
        ((BaseModule) this).mHandler.removeMessages(17);
        ((BaseModule) this).mHandler.removeMessages(2);
    }

    private void setupCaptureParams() {
        Camera2Proxy camera2Proxy = ((BaseModule) this).mCamera2Device;
        if (camera2Proxy == null) {
            Log.e(TAG, "camera device is not ready");
            return;
        }
        camera2Proxy.setFocusMode(this.mTargetFocusMode);
        ((BaseModule) this).mCamera2Device.setZoomRatio(1.0f);
        ((BaseModule) this).mCamera2Device.setFlashMode(0);
        String antiBanding = CameraSettings.getAntiBanding();
        ((BaseModule) this).mCamera2Device.setAntiBanding(Integer.valueOf(antiBanding).intValue());
        Log.d(TAG, "antiBanding=" + antiBanding);
        ((BaseModule) this).mCamera2Device.setEnableZsl(isZslPreferred());
        ((BaseModule) this).mCamera2Device.setHHT(false);
        ((BaseModule) this).mCamera2Device.setEnableOIS(false);
        ((BaseModule) this).mCamera2Device.setTimeWaterMarkEnable(false);
        ((BaseModule) this).mCamera2Device.setFaceWaterMarkEnable(false);
    }

    @UiThread
    private void startSaveTask(byte[] bArr, int i, int i2, int i3) {
        boolean z;
        int i4;
        int i5;
        byte[] bArr2;
        Log.v(TAG, "startSaveTask stitchResult " + i3);
        keepScreenOnAwhile();
        synchronized (this.mDeviceLock) {
            if (((BaseModule) this).mCamera2Device != null) {
                ((BaseModule) this).mCamera2Device.captureAbortBurst();
            }
        }
        if (this.mShowWarningToast) {
            byte[] bArr3 = this.mFirstFrameNv21Data;
            CameraSize cameraSize = ((BaseModule) this).mPictureSize;
            int i6 = cameraSize.width;
            z = true;
            bArr2 = bArr3;
            i4 = cameraSize.height;
            i5 = i6;
        } else {
            bArr2 = bArr;
            i5 = i;
            i4 = i2;
            z = false;
        }
        this.mSaveOutputImageTask = new SaveOutputImageTask(((BaseModule) this).mActivity, DateFormat.format(this.mFileNameTemplate, System.currentTimeMillis()).toString(), bArr2, i5, i4, z, this.mJpegRotation, ((BaseModule) this).mActualCameraId, ((BaseModule) this).mTriggerMode, this.mBeautyValues, this.mStopCaptureMode, new SaveStateCallback() {
            /* class com.android.camera.module.WideSelfieModule.AnonymousClass4 */

            @Override // com.android.camera.module.WideSelfieModule.SaveStateCallback
            public void onSaveCompleted() {
                Log.d(WideSelfieModule.TAG, "onSaveCompleted");
                if (WideSelfieModule.this.mShowWarningToast) {
                    String string = CameraAppImpl.getAndroidContext().getResources().getString(R.string.wideselfie_result_image_lossless);
                    WideSelfieModule wideSelfieModule = WideSelfieModule.this;
                    ToastUtils.showToast(((BaseModule) wideSelfieModule).mActivity, string, 80, 0, wideSelfieModule.mToastOffsetY);
                }
                WideSelfieModule.this.onSaveFinish();
            }
        });
        this.mSaveOutputImageTask.execute(new Void[0]);
        this.mIsPrepareSaveTask = false;
    }

    private void stopWideSelfieShooting(boolean z, boolean z2, String str) {
        if (!this.mIsShooting) {
            Log.w(TAG, "stopWideSelfieShooting return, is not shooting");
            return;
        }
        Log.d(TAG, "stopWideSelfieShooting");
        if (this.mWideSelfEngine.stopCapture()) {
            this.mIsPrepareSaveTask = true;
            this.mIsShooting = false;
            this.mShowWarningToast = z2;
            this.mStopCaptureMode = str;
            if (z) {
                VibratorUtils.getInstance(CameraAppImpl.getAndroidContext()).vibrate();
            }
            playCameraSound(3);
            this.mWideSelfEngine.stopCapture();
        }
    }

    private void updateBeauty() {
        if (this.mBeautyValues == null) {
            this.mBeautyValues = new BeautyValues();
        }
        CameraSettings.initBeautyValues(this.mBeautyValues, ((BaseModule) this).mModuleIndex);
        Log.d(TAG, "updateBeauty(): " + this.mBeautyValues);
        ((BaseModule) this).mCamera2Device.setBeautyValues(this.mBeautyValues);
    }

    private void updateFaceView(boolean z, boolean z2) {
        if (((BaseModule) this).mHandler.hasMessages(35)) {
            ((BaseModule) this).mHandler.removeMessages(35);
        }
        ((BaseModule) this).mHandler.obtainMessage(35, z ? 1 : 0, z2 ? 1 : 0).sendToTarget();
    }

    private void updatePictureAndPreviewSize() {
        PictureSizeManager.initialize(((BaseModule) this).mCameraCapabilities.getSupportedOutputSizeWithAssignedMode(35), DataRepository.dataItemFeature().Sb(), 176, ((BaseModule) this).mBogusCameraId);
        CameraSize bestPictureSize = PictureSizeManager.getBestPictureSize();
        if (b.Wu) {
            bestPictureSize = new CameraSize(bestPictureSize.width / 2, bestPictureSize.height / 2);
        }
        ((BaseModule) this).mPreviewSize = Util.getOptimalPreviewSize(false, ((BaseModule) this).mBogusCameraId, ((BaseModule) this).mCameraCapabilities.getSupportedOutputSizeWithAssignedMode(SurfaceTexture.class), (double) CameraSettings.getPreviewAspectRatio(bestPictureSize.width, bestPictureSize.height));
        ((BaseModule) this).mPictureSize = bestPictureSize;
        Log.d(TAG, "pictureSize= " + bestPictureSize.width + "X" + bestPictureSize.height + " previewSize=" + ((BaseModule) this).mPreviewSize.width + "X" + ((BaseModule) this).mPreviewSize.height);
        CameraSize cameraSize = ((BaseModule) this).mPreviewSize;
        updateCameraScreenNailSize(cameraSize.width, cameraSize.height);
    }

    @Override // com.android.camera.module.Module
    public void closeCamera() {
        Log.d(TAG, "closeCamera: start");
        FlowableEmitter<CaptureResult> flowableEmitter = this.mMetaDataFlowableEmitter;
        if (flowableEmitter != null) {
            flowableEmitter.onComplete();
        }
        Disposable disposable = this.mMetaDataDisposable;
        if (disposable != null) {
            disposable.dispose();
        }
        synchronized (this.mDeviceLock) {
            setCameraState(0);
            if (((BaseModule) this).mCamera2Device != null) {
                ((BaseModule) this).mCamera2Device.setErrorCallback(null);
                ((BaseModule) this).mCamera2Device.stopPreviewCallback(true);
                ((BaseModule) this).mCamera2Device = null;
            }
        }
        Log.d(TAG, "closeCamera: end");
    }

    @Override // com.android.camera.module.BaseModule
    public void consumePreference(@UpdateConstant.UpdateType int... iArr) {
        for (int i : iArr) {
            if (i == 1) {
                updatePictureAndPreviewSize();
            } else if (i == 5) {
                updateFace();
            } else if (i == 13) {
                updateBeauty();
            } else if (i == 24) {
                applyZoomRatio();
            } else if (i == 32) {
                setupCaptureParams();
            } else if (i == 55) {
                updateModuleRelated();
            } else if (i == 66) {
                updateThermalLevel();
            } else if (!(i == 46 || i == 47)) {
                if (!BaseModule.DEBUG) {
                    Log.w(TAG, "no consumer for this updateType: " + i);
                } else {
                    throw new RuntimeException("no consumer for this updateType: " + i);
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
        return 32776;
    }

    @Override // com.android.camera.module.Module, com.android.camera.protocol.ModeProtocol.CameraAction
    public boolean isDoingAction() {
        return isProcessingSaveTask() || this.mIsPrepareSaveTask;
    }

    @Override // com.android.camera2.Camera2Proxy.FaceDetectionCallback
    public boolean isFaceDetectStarted() {
        return this.mFaceDetectionStarted;
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule, com.android.camera.protocol.ModeProtocol.CameraAction
    public boolean isRecording() {
        return this.mIsShooting || this.mIsPrepareSaveTask;
    }

    @Override // com.android.camera.module.Module
    public boolean isUnInterruptable() {
        ((BaseModule) this).mUnInterruptableReason = null;
        if (this.mIsShooting) {
            ((BaseModule) this).mUnInterruptableReason = "shooting";
        }
        return ((BaseModule) this).mUnInterruptableReason != null;
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
    public boolean isZslPreferred() {
        return !b.isMTKPlatform();
    }

    /* access modifiers changed from: protected */
    public void keepScreenOn() {
        ((BaseModule) this).mHandler.removeMessages(17);
        ((BaseModule) this).mHandler.removeMessages(2);
        getWindow().addFlags(128);
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public boolean onBackPressed() {
        if (!this.mIsShooting) {
            return false;
        }
        if (!isProcessingSaveTask()) {
            playCameraSound(3);
            stopWideSelfieShooting(true, false, MistatsConstants.Panorama.STOP_CAPTURE_MODE_ON_HOME_OR_BACK);
        }
        return true;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.BaseModule
    public void onCameraOpened() {
        super.onCameraOpened();
        checkDisplayOrientation();
        updatePreferenceTrampoline(UpdateConstant.WIDESELFIE_TYPES_INIT);
        startPreviewSession();
        ((BaseModule) this).mHandler.sendEmptyMessage(9);
        initMetaParser();
        Log.v(TAG, "SetupCameraThread done");
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void onCreate(int i, int i2) {
        super.onCreate(i, i2);
        ((BaseModule) this).mHandler = new MainHandler(((BaseModule) this).mActivity.getMainLooper());
        this.mStillPreviewWidth = WideSelfieConfig.getInstance(getActivity()).getStillPreviewWidth();
        this.OFFSET_X_HINT_THRESHOLD = 0;
        int i3 = this.mStillPreviewWidth;
        this.OFFSET_Y_HINT_THRESHOLD = i3 / 4;
        this.OFFSET_X_STOP_CAPTURE_THRESHOLD = i3 / 3;
        this.OFFSET_VERTICAL_X_STOP_CAPTURE_THRESHOLD = Math.max(i3 / 5, 36);
        this.OFFSET_Y_STOP_CAPTURE_THRESHOLD = this.mStillPreviewWidth / 2;
        this.mToastOffsetY = ((BaseModule) this).mActivity.getResources().getDimensionPixelOffset(R.dimen.wideselfie_toast_offset_y);
        this.MOVE_DISTANCE = (WideSelfieConfig.getInstance(((BaseModule) this).mActivity).getThumbBgWidth() - WideSelfieConfig.getInstance(((BaseModule) this).mActivity).getStillPreviewWidth()) / 2;
        this.MOVE_DISTANCE_VERTICAL = (WideSelfieConfig.getInstance(((BaseModule) this).mActivity).getThumbBgHeightVertical() - WideSelfieConfig.getInstance(((BaseModule) this).mActivity).getStillPreviewHeight()) / 2;
        Log.d(TAG, "MOVE_DISTANCE " + this.MOVE_DISTANCE + ", MOVE_DISTANCE_VERTICAL =  " + this.MOVE_DISTANCE_VERTICAL);
        this.mWideSelfEngine = new WideSelfieEngineWrapper(((BaseModule) this).mActivity.getApplicationContext(), this);
        EffectController.getInstance().setEffect(FilterInfo.FILTER_ID_NONE);
        onCameraOpened();
        this.mFileNameTemplate = ((BaseModule) this).mActivity.getString(R.string.pano_file_name_format);
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void onDestroy() {
        super.onDestroy();
        ((BaseModule) this).mHandler.sendEmptyMessage(45);
        this.mWideSelfEngine.onDestroy();
    }

    @Override // com.android.camera2.Camera2Proxy.FaceDetectionCallback
    public void onFaceDetected(CameraHardwareFace[] cameraHardwareFaceArr, FaceAnalyzeInfo faceAnalyzeInfo) {
        if (!isCreated() || cameraHardwareFaceArr == null || isRecording()) {
            return;
        }
        if (!b.cm() || cameraHardwareFaceArr.length <= 0 || cameraHardwareFaceArr[0].faceType != 64206) {
            if (!((BaseModule) this).mMainProtocol.setFaces(1, cameraHardwareFaceArr, getActiveArraySize(), getDeviceBasedZoomRatio())) {
            }
        } else if (((BaseModule) this).mObjectTrackingStarted) {
            ((BaseModule) this).mMainProtocol.setFaces(3, cameraHardwareFaceArr, getActiveArraySize(), getDeviceBasedZoomRatio());
        }
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void onHostStopAndNotifyActionStop() {
        if (this.mIsShooting) {
            ModeProtocol.RecordState recordState = (ModeProtocol.RecordState) ModeCoordinatorImpl.getInstance().getAttachProtocol(212);
            if (recordState != null) {
                recordState.onFinish();
            }
            playCameraSound(3);
            stopWideSelfieShooting(false, false, MistatsConstants.Panorama.STOP_CAPTURE_MODE_ON_HOME_OR_BACK);
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
        if (i == 27 || i == 66) {
            if (keyEvent.getRepeatCount() == 0) {
                if (!Util.isFingerPrintKeyEvent(keyEvent)) {
                    performKeyClicked(40, getString(R.string.pref_camera_volumekey_function_entryvalue_shutter), keyEvent.getRepeatCount(), true);
                } else if (CameraSettings.isFingerprintCaptureEnable()) {
                    performKeyClicked(30, getString(R.string.pref_camera_volumekey_function_entryvalue_shutter), keyEvent.getRepeatCount(), true);
                }
                return true;
            }
        } else if (i != 700) {
            if (!(i == 87 || i == 88)) {
                switch (i) {
                    case 23:
                        if (keyEvent.getRepeatCount() == 0) {
                            onShutterButtonClick(50);
                            return true;
                        }
                        break;
                }
            }
            if (i == 24 || i == 88) {
                z = true;
            }
            if (handleVolumeKeyEvent(z, true, keyEvent.getRepeatCount(), keyEvent.getDevice().isExternal())) {
                return true;
            }
        } else if (this.mIsShooting) {
            stopWideSelfieShooting(false, false, MistatsConstants.Panorama.STOP_CAPTURE_MODE_ON_HOME_OR_BACK);
        }
        return super.onKeyDown(i, keyEvent);
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        if (((BaseModule) this).mPaused) {
            return true;
        }
        if (i != 4) {
            if (i == 27 || i == 66) {
                return true;
            }
        } else if (((ModeProtocol.BackStack) ModeCoordinatorImpl.getInstance().getAttachProtocol(171)).handleBackStackFromKeyBack()) {
            return true;
        }
        return super.onKeyUp(i, keyEvent);
    }

    @Override // com.android.camera.wideselfie.WideSelfieEngineWrapper.WideSelfStateCallback
    @UiThread
    public void onMove(int i, int i2, Point point, Point point2, boolean z) {
        ModeProtocol.WideSelfieProtocol wideSelfieProtocol = (ModeProtocol.WideSelfieProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(216);
        if (wideSelfieProtocol == null) {
            VibratorUtils.getInstance(CameraAppImpl.getAndroidContext()).cancel();
            return;
        }
        int i3 = point.x;
        int i4 = point.y;
        int i5 = this.mJpegRotation % 180 == 90 ? this.OFFSET_X_STOP_CAPTURE_THRESHOLD : this.OFFSET_VERTICAL_X_STOP_CAPTURE_THRESHOLD;
        int i6 = 0;
        boolean z2 = Math.abs(i4) >= this.OFFSET_Y_STOP_CAPTURE_THRESHOLD;
        boolean z3 = Math.abs(i3) >= i5;
        if (z || z3 || z2) {
            String str = z3 ? MistatsConstants.Panorama.STOP_CAPTURE_MODE_HORIZONTAL_OUT : z2 ? MistatsConstants.Panorama.STOP_CAPTURE_MODE_VERTICAL_OUT : MistatsConstants.Panorama.STOP_CAPTURE_MODE_FILL;
            Log.w(TAG, "stop shooting completed = " + z);
            stopWideSelfieShooting(true, false, str);
            return;
        }
        if (Math.abs(i4) >= this.OFFSET_Y_HINT_THRESHOLD) {
            int i7 = i4 < 0 ? R.string.wideselfie_rotate_to_front : R.string.wideselfie_rotate_to_back;
            if (i7 != 0) {
                if (!this.mIsContinuousVibratoring) {
                    VibratorUtils.getInstance(((BaseModule) this).mActivity).vibrate();
                    this.mIsContinuousVibratoring = true;
                }
                wideSelfieProtocol.updateHintText(i7);
                return;
            }
            return;
        }
        if (this.mIsContinuousVibratoring) {
            VibratorUtils.getInstance(((BaseModule) this).mActivity).cancel();
            this.mIsContinuousVibratoring = false;
        }
        if (i == -1) {
            wideSelfieProtocol.updateHintText(R.string.wideselfie_rotate_slowly);
            return;
        }
        if ((i2 == 50 || i2 == 100) && this.mLastVibratorProgress != i2) {
            VibratorUtils.getInstance(((BaseModule) this).mActivity).vibrate();
            this.mLastVibratorProgress = i2;
        }
        if (this.mJpegRotation % 180 == 90) {
            this.mMaxMoveWidth = Math.max(this.mMaxMoveWidth, Math.abs(point2.y));
            boolean z4 = i2 == 50 || i2 == 100;
            if (!z4 && this.mLastMoveDirection == 1 && i == 0) {
                wideSelfieProtocol.updateThumbBackgroudLayout(false, true, this.MOVE_DISTANCE - this.mMaxMoveWidth);
            } else if (!z4 && this.mLastMoveDirection == 0 && i == 1) {
                wideSelfieProtocol.updateThumbBackgroudLayout(false, false, this.MOVE_DISTANCE - this.mMaxMoveWidth);
            }
        } else {
            this.mMaxMoveWidth = Math.max(this.mMaxMoveWidth, Math.abs(point2.x));
            boolean z5 = i2 == 50 || i2 == 100;
            if (!z5 && this.mLastMoveDirection == 1 && i == 0) {
                wideSelfieProtocol.updateThumbBackgroudLayout(true, true, this.MOVE_DISTANCE_VERTICAL - this.mMaxMoveWidth);
            } else if (!z5 && this.mLastMoveDirection == 0 && i == 1) {
                wideSelfieProtocol.updateThumbBackgroudLayout(true, false, this.MOVE_DISTANCE_VERTICAL - this.mMaxMoveWidth);
            }
        }
        this.mLastMoveDirection = i;
        if (i == 1) {
            i6 = R.string.wideselfie_rotate_to_left_slowly;
        } else if (i == 0) {
            i6 = R.string.wideselfie_rotate_to_right_slowly;
        }
        if (i6 != 0) {
            wideSelfieProtocol.updateHintText(i6);
        }
    }

    @Override // com.android.camera.wideselfie.WideSelfieEngineWrapper.WideSelfStateCallback
    public void onNv21Available(byte[] bArr, int i, int i2, int i3) {
        Log.d(TAG, "onNv21Available");
        startSaveTask(bArr, i, i2, i3);
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void onOrientationChanged(int i, int i2, int i3) {
        super.onOrientationChanged(i, i2, i3);
        if (this.mIsShooting && !this.mStopedForRotation) {
            int abs = Math.abs(this.mCaptureOrientation - i3);
            if (abs > 180) {
                abs = 360 - abs;
            }
            if (abs >= 60) {
                this.mStopedForRotation = true;
                Log.w(TAG, "onOrientationChanged stop shooting mCaptureOrientation " + this.mCaptureOrientation + ", orientation = " + i + ", realTimeOrientation = " + i3);
                stopWideSelfieShooting(false, true, MistatsConstants.Panorama.STOP_CAPTURE_MODE_ROTATE_OUT);
            }
        }
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void onPause() {
        super.onPause();
        ((BaseModule) this).mHandler.removeCallbacksAndMessages(null);
        closeCamera();
        resetScreenOn();
        this.mWideSelfEngine.pause();
    }

    @Override // com.android.camera.module.BaseModule
    public void onPreviewLayoutChanged(Rect rect) {
        ((BaseModule) this).mActivity.onLayoutChange(rect);
    }

    @Override // com.android.camera.module.BaseModule, com.android.camera2.Camera2Proxy.CameraMetaDataCallback
    public void onPreviewMetaDataUpdate(CaptureResult captureResult) {
        if (captureResult != null) {
            super.onPreviewMetaDataUpdate(captureResult);
            if (this.mMetaDataFlowableEmitter != null && !this.mIsShooting) {
                this.mMetaDataFlowableEmitter.onNext(captureResult);
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
            setCameraState(1);
            updatePreferenceInWorkThread(UpdateConstant.WIDESELFIE_ON_PREVIEW_SUCCESS);
        }
    }

    @Override // com.android.camera.module.BaseModule
    public void onPreviewSizeChanged(int i, int i2) {
    }

    @Override // com.android.camera.wideselfie.WideSelfieEngineWrapper.WideSelfStateCallback
    public void onPreviewUpdate(byte[] bArr, int i, int i2, final Rect rect, final Rect rect2) {
        if (bArr != null) {
            final Bitmap rotateBitmap = d.rotateBitmap(d.b(bArr, i, i2), CameraAppImpl.getAndroidContext().getResources().getInteger(R.integer.front_lens_orientation), true);
            ((BaseModule) this).mHandler.post(new Runnable() {
                /* class com.android.camera.module.WideSelfieModule.AnonymousClass1 */

                public void run() {
                    ModeProtocol.WideSelfieProtocol wideSelfieProtocol = (ModeProtocol.WideSelfieProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(216);
                    if (wideSelfieProtocol != null) {
                        wideSelfieProtocol.updatePreviewBitmap(rotateBitmap, rect, rect2);
                    }
                }
            });
        }
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void onResume() {
        super.onResume();
        keepScreenOnAwhile();
        this.mWideSelfEngine.resume();
    }

    @Override // com.android.camera.protocol.ModeProtocol.CameraAction
    public void onReviewCancelClicked() {
    }

    @Override // com.android.camera.protocol.ModeProtocol.CameraAction
    public void onReviewDoneClicked() {
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
        if (!((BaseModule) this).mPaused && getCameraState() != 0 && !isIgnoreTouchEvent() && !((BaseModule) this).mActivity.isScreenSlideOff()) {
            if (this.mIsPrepareSaveTask || isProcessingSaveTask()) {
                Log.w(TAG, "onShutterButtonClick return, mIsPrepareSaveTask " + this.mIsPrepareSaveTask);
                return;
            }
            setTriggerMode(i);
            if (!this.mIsShooting) {
                ((BaseModule) this).mActivity.getScreenHint().updateHint();
                if (Storage.isLowStorageAtLastPoint()) {
                    ((ModeProtocol.RecordState) ModeCoordinatorImpl.getInstance().getAttachProtocol(212)).onFailed();
                    Log.w(TAG, "onShutterButtonClick return, isLowStorageAtLastPoint");
                    return;
                }
                playCameraSound(2);
                startWideSelfieShooting();
                this.mShootingStartTime = SystemClock.elapsedRealtime();
            } else if (isShootingTooShort()) {
                Log.w(TAG, "shooting is too short, ignore this click");
            } else {
                stopWideSelfieShooting(true, false, MistatsConstants.Panorama.STOP_CAPTURE_MODE_ON_SHUTTER_BUTTON);
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
        onShutterButtonFocus(false, 2);
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void onSingleTapUp(int i, int i2, boolean z) {
        ModeProtocol.BackStack backStack;
        if (!((BaseModule) this).mPaused && ((BaseModule) this).mCamera2Device != null && !hasCameraException() && ((BaseModule) this).mCamera2Device.isSessionReady() && isInTapableRect(i, i2)) {
            if (!isFrameAvailable()) {
                Log.w(TAG, "onSingleTapUp: frame not available");
            } else if ((!isFrontCamera() || !((BaseModule) this).mActivity.isScreenSlideOff()) && (backStack = (ModeProtocol.BackStack) ModeCoordinatorImpl.getInstance().getAttachProtocol(171)) != null && !backStack.handleBackStackFromTapDown(i, i2)) {
                ((BaseModule) this).mMainProtocol.setFocusViewType(true);
            }
        }
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void onStop() {
        super.onStop();
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.BaseModule
    public void onThermalConstrained() {
        super.onThermalConstrained();
        if (!this.mIsShooting) {
            return;
        }
        if (isShootingTooShort()) {
            Log.w(TAG, "shooting is too short, ignore this click");
        } else {
            stopWideSelfieShooting(true, false, MistatsConstants.Panorama.STOP_CAPTURE_MODE_ON_SHUTTER_BUTTON);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.CameraAction
    @OnClickAttr
    public void onThumbnailClicked(View view) {
        if (!((BaseModule) this).mPaused && !isProcessingSaveTask() && ((BaseModule) this).mActivity.getThumbnailUpdater().getThumbnail() != null) {
            ((BaseModule) this).mActivity.gotoGallery();
        }
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void onUserInteraction() {
        super.onUserInteraction();
        if (!((BaseModule) this).mPaused && !this.mIsShooting) {
            keepScreenOnAwhile();
        }
    }

    @Override // com.android.camera.wideselfie.WideSelfieEngineWrapper.WideSelfStateCallback
    public void onWideSelfCompleted() {
        Log.d(TAG, "onWideSelfCompleted");
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
        Log.v(TAG, "pausePreview");
        ((BaseModule) this).mCamera2Device.pausePreview();
        setCameraState(0);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.BaseModule
    public void performKeyClicked(int i, String str, int i2, boolean z) {
        if (i2 == 0 && z) {
            onShutterButtonClick(i);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.BaseProtocol, com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void registerProtocol() {
        super.registerProtocol();
        ModeCoordinatorImpl.getInstance().attachProtocol(161, this);
        getActivity().getImplFactory().initAdditional(getActivity(), 164, 234, 212);
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void requestRender() {
        ModeProtocol.WideSelfieProtocol wideSelfieProtocol = (ModeProtocol.WideSelfieProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(216);
        if (wideSelfieProtocol != null) {
            wideSelfieProtocol.requestRender();
        }
    }

    @Override // com.android.camera.module.Module
    public void resumePreview() {
        Log.v(TAG, "resumePreview");
        ((BaseModule) this).mCamera2Device.resumePreview();
        setCameraState(1);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.BaseModule
    public void sendOpenFailMessage() {
        ((BaseModule) this).mHandler.sendEmptyMessage(10);
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void setFrameAvailable(boolean z) {
        Camera camera;
        super.setFrameAvailable(z);
        if (z && CameraSettings.isCameraSoundOpen() && (camera = ((BaseModule) this).mActivity) != null) {
            camera.loadCameraSound(2);
            camera.loadCameraSound(3);
        }
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public boolean shouldReleaseLater() {
        return isRecording();
    }

    public void startFaceDetection() {
        Camera2Proxy camera2Proxy;
        if (this.mFaceDetectionEnabled && !this.mFaceDetectionStarted && isAlive() && ((BaseModule) this).mMaxFaceCount > 0 && (camera2Proxy = ((BaseModule) this).mCamera2Device) != null) {
            this.mFaceDetectionStarted = true;
            camera2Proxy.startFaceDetection();
            updateFaceView(true, true);
        }
    }

    @Override // com.android.camera.module.Module
    public void startPreview() {
        synchronized (this.mDeviceLock) {
            if (((BaseModule) this).mCamera2Device == null) {
                Log.w(TAG, "startPreview: camera has been closed");
                return;
            }
            checkDisplayOrientation();
            ((BaseModule) this).mCamera2Device.setDisplayOrientation(((BaseModule) this).mCameraDisplayOrientation);
            if (((BaseModule) this).mAeLockSupported) {
                ((BaseModule) this).mCamera2Device.setAELock(false);
            }
            if (((BaseModule) this).mAwbLockSupported) {
                ((BaseModule) this).mCamera2Device.setAWBLock(false);
            }
            ((BaseModule) this).mCamera2Device.setFocusMode(this.mTargetFocusMode);
            ((BaseModule) this).mCamera2Device.resumePreview();
            setCameraState(1);
        }
    }

    public void startPreviewSession() {
        Camera2Proxy camera2Proxy = ((BaseModule) this).mCamera2Device;
        if (camera2Proxy == null) {
            Log.e(TAG, "startPreview: camera has been closed");
            return;
        }
        camera2Proxy.setDualCamWaterMarkEnable(false);
        ((BaseModule) this).mCamera2Device.setErrorCallback(((BaseModule) this).mErrorCallback);
        ((BaseModule) this).mCamera2Device.setPreviewSize(((BaseModule) this).mPreviewSize);
        ((BaseModule) this).mCamera2Device.setAlgorithmPreviewSize(((BaseModule) this).mPreviewSize);
        ((BaseModule) this).mCamera2Device.setPictureSize(((BaseModule) this).mPictureSize);
        ((BaseModule) this).mCamera2Device.setPictureMaxImages(3);
        ((BaseModule) this).mCamera2Device.setPictureFormat(35);
        ((BaseModule) this).mSurfaceCreatedTimestamp = ((BaseModule) this).mActivity.getCameraScreenNail().getSurfaceCreatedTimestamp();
        ((BaseModule) this).mCamera2Device.startPreviewSession(new Surface(((BaseModule) this).mActivity.getCameraScreenNail().getSurfaceTexture()), !b.isMTKPlatform(), false, false, getOperatingMode(), false, this);
    }

    public void startWideSelfieShooting() {
        this.mShowWarningToast = false;
        this.mFirstFrameNv21Data = null;
        int i = ((BaseModule) this).mOrientation;
        this.mCaptureOrientation = i;
        this.mJpegRotation = Util.getJpegRotation(((BaseModule) this).mBogusCameraId, i);
        Log.v(TAG, "startWideSelfieShooting mJpegRotation = " + this.mJpegRotation);
        this.mIsShooting = true;
        this.mStopedForRotation = false;
        this.mLastVibratorProgress = -1;
        this.mLastMoveDirection = -1;
        this.mMaxMoveWidth = 0;
        ModeProtocol.RecordState recordState = (ModeProtocol.RecordState) ModeCoordinatorImpl.getInstance().getAttachProtocol(212);
        recordState.onPrepare();
        this.mWideSelfEngine.setOrientation(this.mJpegRotation);
        this.mWideSelfEngine.startCapture();
        synchronized (this.mDeviceLock) {
            if (((BaseModule) this).mAeLockSupported) {
                ((BaseModule) this).mCamera2Device.setAELock(true);
            }
            if (((BaseModule) this).mAwbLockSupported) {
                ((BaseModule) this).mCamera2Device.setAWBLock(true);
            }
            ((BaseModule) this).mCamera2Device.setGpsLocation(LocationManager.instance().getCurrentLocation());
            ((BaseModule) this).mCamera2Device.setFocusMode(1);
            ((BaseModule) this).mCamera2Device.setEnableZsl(isZslPreferred());
            ((BaseModule) this).mCamera2Device.setNeedPausePreview(false);
            ((BaseModule) this).mCamera2Device.setShotType(3);
            ((BaseModule) this).mCamera2Device.captureBurstPictures(-1, new Camera2Proxy.PictureCallbackWrapper() {
                /* class com.android.camera.module.WideSelfieModule.AnonymousClass3 */

                @Override // com.android.camera2.Camera2Proxy.PictureCallbackWrapper, com.android.camera2.Camera2Proxy.PictureCallback
                public void onPictureTakenFinished(boolean z) {
                    Log.d(WideSelfieModule.TAG, "onPictureBurstFinished success = " + z);
                }

                @Override // com.android.camera2.Camera2Proxy.PictureCallbackWrapper, com.android.camera2.Camera2Proxy.PictureCallback
                public boolean onPictureTakenImageConsumed(Image image) {
                    Log.v(WideSelfieModule.TAG, "onPictureTaken>>image=" + image);
                    if (image == null) {
                        return true;
                    }
                    byte[] dataFromImage = d.getDataFromImage(image, 2);
                    if (WideSelfieModule.this.mFirstFrameNv21Data == null) {
                        byte[] unused = WideSelfieModule.this.mFirstFrameNv21Data = dataFromImage;
                    }
                    WideSelfieModule.this.mWideSelfEngine.onBurstCapture(dataFromImage);
                    image.close();
                    return true;
                }
            }, null);
        }
        recordState.onStart();
        keepScreenOn();
        AutoLockManager.getInstance(((BaseModule) this).mActivity).removeMessage();
    }

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

    @Override // com.android.camera.protocol.ModeProtocol.BaseProtocol, com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void unRegisterProtocol() {
        super.unRegisterProtocol();
        ModeCoordinatorImpl.getInstance().detachProtocol(161, this);
        Camera camera = ((BaseModule) this).mActivity;
        if (camera != null) {
            camera.getImplFactory().detachAdditional();
        }
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
}
