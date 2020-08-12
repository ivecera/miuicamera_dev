package com.android.camera2;

import android.content.Context;
import android.graphics.RectF;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.params.MeteringRectangle;
import android.location.Location;
import android.media.Image;
import android.media.ImageReader;
import android.media.ImageWriter;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Range;
import android.view.Surface;
import com.android.camera.CameraScreenNail;
import com.android.camera.CameraSize;
import com.android.camera.Thumbnail;
import com.android.camera.effect.FaceAnalyzeInfo;
import com.android.camera.fragment.beauty.BeautyValues;
import com.android.camera.module.loader.camera2.FocusTask;
import com.android.camera.storage.ImageSaver;
import com.android.camera2.vendortag.struct.MarshalQueryableASDScene;
import com.xiaomi.camera.core.ParallelCallback;
import com.xiaomi.camera.core.ParallelTaskData;
import com.xiaomi.camera.liveshot.LivePhotoResult;
import java.lang.ref.WeakReference;

public abstract class Camera2Proxy {
    public static final int INVALID_REQUEST_ID = -1;
    final int mActualCameraId;
    private final Object mCallbackLock = new Object();
    protected CameraErrorCallback mErrorCallback;
    private WeakReference<FocusCallback> mFocusCallback;
    private volatile boolean mIsFixedShotTimeEnabled = false;
    private WeakReference<MagneticDetectedCallback> mMagneticDetectedCallback;
    private WeakReference<CameraMetaDataCallback> mMetadataCallback;
    private ParallelCallback mParallelCallback;
    private PictureCallback mPictureCallBack;
    private PreviewCallback mPreviewCallback;
    private ScreenLightCallback mScreenLightCallback;

    public interface BeautyBodySlimCountCallback {
        boolean isBeautyBodySlimCountDetectStarted();

        void onBeautyBodySlimCountChange(boolean z);
    }

    public interface CameraErrorCallback {
        void onCameraError(Camera2Proxy camera2Proxy, int i);
    }

    public interface CameraMetaDataCallback {
        void onPreviewMetaDataUpdate(CaptureResult captureResult);
    }

    public interface CameraPreviewCallback {
        void onPreviewSessionClosed(CameraCaptureSession cameraCaptureSession);

        void onPreviewSessionFailed(CameraCaptureSession cameraCaptureSession);

        void onPreviewSessionSuccess(CameraCaptureSession cameraCaptureSession);
    }

    public interface CaptureBusyCallback {
        void onCaptureCompleted(boolean z);
    }

    public interface CaptureCallback {
        void onCaptureCompleted(boolean z);
    }

    public interface FaceDetectionCallback {
        boolean isFaceDetectStarted();

        boolean isUseFaceInfo();

        void onFaceDetected(CameraHardwareFace[] cameraHardwareFaceArr, FaceAnalyzeInfo faceAnalyzeInfo);
    }

    public interface FocusCallback {
        void onFocusStateChanged(FocusTask focusTask);
    }

    public interface HdrCheckerCallback {
        boolean isHdrSceneDetectionStarted();

        void onHdrMotionDetectionResult(boolean z);

        void onHdrSceneChanged(boolean z);
    }

    public interface LivePhotoResultCallback {
        int getFilterId();

        boolean isGyroStable();

        boolean isLivePhotoStarted();

        void onLivePhotoResultCallback(LivePhotoResult livePhotoResult);
    }

    public interface MagneticDetectedCallback {
        boolean isLockHDRChecker();

        void magneticDetectedUpdateCapture();
    }

    public interface PictureCallback {
        default void onCaptureCompleted(boolean z) {
        }

        void onCaptureShutter(boolean z);

        ParallelTaskData onCaptureStart(ParallelTaskData parallelTaskData, CameraSize cameraSize, boolean z);

        void onPictureTaken(byte[] bArr, CaptureResult captureResult);

        void onPictureTakenFinished(boolean z);

        boolean onPictureTakenImageConsumed(Image image);
    }

    public static class PictureCallbackWrapper implements PictureCallback {
        @Override // com.android.camera2.Camera2Proxy.PictureCallback
        public void onCaptureShutter(boolean z) {
        }

        @Override // com.android.camera2.Camera2Proxy.PictureCallback
        public ParallelTaskData onCaptureStart(ParallelTaskData parallelTaskData, CameraSize cameraSize, boolean z) {
            return null;
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
    }

    public interface PreviewCallback {
        boolean onPreviewFrame(Image image, Camera2Proxy camera2Proxy, int i);
    }

    public interface ScreenLightCallback {
        void startScreenLight(int i, int i2);

        void stopScreenLight();
    }

    public interface SuperNightCallback {
        boolean isSupportSuperNight();

        void onSuperNightChanged(boolean z);
    }

    public interface VideoRecordStateCallback {
        void onVideoRecordStopped();
    }

    public Camera2Proxy(int i) {
        this.mActualCameraId = i;
    }

    public abstract void cancelContinuousShot();

    public abstract void cancelFocus(int i);

    public abstract void cancelSession();

    public abstract void captureAbortBurst();

    public abstract void captureBurstPictures(int i, @NonNull PictureCallback pictureCallback, @NonNull ParallelCallback parallelCallback);

    public abstract void captureGroupShotPictures(@NonNull PictureCallback pictureCallback, @NonNull ParallelCallback parallelCallback, int i, Context context);

    public abstract void captureVideoSnapshot(PictureCallback pictureCallback);

    public abstract void close();

    public abstract void forceTurnFlashONAndPausePreview();

    public abstract void forceTurnFlashOffAndPausePreview();

    public abstract int getActivityHashCode();

    public abstract int getAlgorithmPreviewFormat();

    public abstract CameraSize getAlgorithmPreviewSize();

    public abstract CameraSize getBinningPictureSize();

    public abstract int getBokehAuxCameraId();

    public abstract CameraConfigs getCameraConfigs();

    /* access modifiers changed from: protected */
    public abstract CameraDevice getCameraDevice();

    public abstract Handler getCameraHandler();

    public abstract CameraCapabilities getCapabilities();

    /* access modifiers changed from: protected */
    public abstract CameraCaptureSession getCaptureSession();

    public abstract Integer getCurrentAEState();

    /* access modifiers changed from: protected */
    public abstract ImageReader getDepthImageReader();

    public abstract int getExposureCompensation();

    public abstract int getFlashMode();

    /* access modifiers changed from: package-private */
    public FocusCallback getFocusCallback() {
        FocusCallback focusCallback;
        synchronized (this.mCallbackLock) {
            focusCallback = this.mFocusCallback != null ? this.mFocusCallback.get() : null;
        }
        return focusCallback;
    }

    public abstract int getFocusMode();

    public int getId() {
        return this.mActualCameraId;
    }

    public MagneticDetectedCallback getMagneticDetectedCallback() {
        WeakReference<MagneticDetectedCallback> weakReference = this.mMagneticDetectedCallback;
        if (weakReference != null) {
            return weakReference.get();
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public CameraMetaDataCallback getMetadataCallback() {
        synchronized (this.mCallbackLock) {
            if (this.mMetadataCallback == null) {
                return null;
            }
            CameraMetaDataCallback cameraMetaDataCallback = this.mMetadataCallback.get();
            return cameraMetaDataCallback;
        }
    }

    /* access modifiers changed from: package-private */
    public ParallelCallback getParallelCallback() {
        ParallelCallback parallelCallback;
        synchronized (this.mCallbackLock) {
            parallelCallback = this.mParallelCallback;
        }
        return parallelCallback;
    }

    public abstract String getParallelShotSavePath();

    /* access modifiers changed from: protected */
    public abstract ImageReader getPhotoImageReader();

    /* access modifiers changed from: package-private */
    public PictureCallback getPictureCallback() {
        PictureCallback pictureCallback;
        synchronized (this.mCallbackLock) {
            pictureCallback = this.mPictureCallBack;
        }
        return pictureCallback;
    }

    public abstract int getPictureFormat();

    public abstract int getPictureMaxImages();

    public abstract CameraSize getPictureSize();

    /* access modifiers changed from: protected */
    public abstract ImageReader getPortraitRawImageReader();

    /* access modifiers changed from: package-private */
    public PreviewCallback getPreviewCallback() {
        PreviewCallback previewCallback;
        synchronized (this.mCallbackLock) {
            previewCallback = this.mPreviewCallback;
        }
        return previewCallback;
    }

    public abstract int getPreviewMaxImages();

    /* access modifiers changed from: protected */
    public abstract CaptureRequest.Builder getPreviewRequestBuilder();

    public abstract CameraSize getPreviewSize();

    /* access modifiers changed from: protected */
    public abstract Surface getPreviewSurface();

    /* access modifiers changed from: protected */
    public abstract ImageReader getRawImageReader();

    /* access modifiers changed from: protected */
    public abstract ImageWriter getRawImageWriter();

    /* access modifiers changed from: protected */
    public abstract Surface getRawSurface();

    /* access modifiers changed from: protected */
    public abstract Surface getRecordSurface();

    public abstract int[] getSATSubCameraIds();

    public abstract int getSatMasterCameraId();

    public abstract int getSceneMode();

    /* access modifiers changed from: package-private */
    public ScreenLightCallback getScreenLightCallback() {
        ScreenLightCallback screenLightCallback;
        synchronized (this.mCallbackLock) {
            screenLightCallback = this.mScreenLightCallback;
        }
        return screenLightCallback;
    }

    public abstract CameraSize getSensorRawImageSize();

    public abstract boolean getSuperNight();

    /* access modifiers changed from: protected */
    public abstract ImageReader getVideoSnapshotImageReader();

    public abstract float getZoomRatio();

    public abstract boolean isBokehEnabled();

    public abstract boolean isCaptureBusy(boolean z);

    public abstract boolean isConfigRawStream();

    public abstract boolean isFacingFront();

    public boolean isFixShotTime() {
        return this.mIsFixedShotTimeEnabled;
    }

    public abstract boolean isInMultiSurfaceSatMode();

    public abstract boolean isMacroMode();

    public abstract boolean isNeedFlashOn();

    public abstract boolean isNeedPreviewThumbnail();

    public abstract boolean isParallelBusy(boolean z);

    public abstract boolean isPreviewReady();

    public abstract boolean isQcfaEnable();

    public abstract boolean isSessionReady();

    public abstract void lockExposure(boolean z);

    public abstract void lockExposure(boolean z, boolean z2);

    /* access modifiers changed from: protected */
    public void notifyOnError(int i) {
        CameraErrorCallback cameraErrorCallback = this.mErrorCallback;
        if (cameraErrorCallback != null) {
            cameraErrorCallback.onCameraError(this, i);
        }
    }

    public abstract void notifyVideoStreamEnd();

    public abstract void onCameraDisconnected();

    public abstract void onCapabilityChanged(CameraCapabilities cameraCapabilities);

    public abstract void onMultiSnapEnd(boolean z, MiCamera2Shot miCamera2Shot);

    public abstract void onParallelImagePostProcStart();

    public abstract void onPreviewComing();

    public abstract void onPreviewThumbnailReceived(Thumbnail thumbnail);

    public abstract void pausePreview();

    public abstract boolean registerCaptureCallback(CaptureCallback captureCallback);

    public abstract void releaseCameraPreviewCallback(@Nullable CameraPreviewCallback cameraPreviewCallback);

    public abstract void releaseFakeSurfaceIfNeed();

    public abstract void releasePreview(int i);

    public abstract void resetConfigs();

    public abstract int resumePreview();

    public abstract int sendSatFallbackRequest();

    public abstract void setAELock(boolean z);

    public abstract void setAERegions(MeteringRectangle[] meteringRectangleArr);

    public abstract void setAFRegions(MeteringRectangle[] meteringRectangleArr);

    public abstract void setASDEnable(boolean z);

    public abstract void setASDPeriod(int i);

    public abstract void setASDScene(int i);

    public abstract void setAWBLock(boolean z);

    public abstract void setAWBMode(int i);

    public abstract void setActivityHashCode(int i);

    public abstract void setAiASDEnable(boolean z);

    public abstract void setAlgorithmPreviewFormat(int i);

    public abstract void setAlgorithmPreviewSize(CameraSize cameraSize);

    public abstract void setAntiBanding(int i);

    public abstract void setAsdDirtyEnable(boolean z);

    public abstract void setAutoZoomMode(int i);

    public abstract void setAutoZoomScaleOffset(float f2);

    public abstract void setAutoZoomStartCapture(float[] fArr, boolean z);

    public abstract void setAutoZoomStopCapture(int i, boolean z);

    public abstract void setBeautyValues(BeautyValues beautyValues);

    public abstract void setBinningPictureSize(CameraSize cameraSize);

    public abstract void setBurstShotSpeed(int i);

    public abstract void setCameraAI30(boolean z);

    public abstract void setCaptureBusyCallback(CaptureBusyCallback captureBusyCallback);

    public abstract void setCaptureTime(long j);

    public abstract void setCaptureTriggerFlow(int[] iArr);

    public abstract void setCinematicPhotoEnabled(boolean z);

    public abstract void setCinematicVideoEnabled(boolean z);

    public abstract void setColorEffect(int i);

    public abstract void setColorEnhanceEnable(boolean z);

    public abstract void setContrast(int i);

    public abstract void setCustomAWB(int i);

    public abstract void setDeviceOrientation(int i);

    public abstract void setDisplayOrientation(int i);

    public abstract void setDualCamWaterMarkEnable(boolean z);

    public abstract void setEnableEIS(boolean z);

    public abstract void setEnableOIS(boolean z);

    public abstract void setEnableZsl(boolean z);

    public void setErrorCallback(@Nullable CameraErrorCallback cameraErrorCallback) {
        this.mErrorCallback = cameraErrorCallback;
    }

    public abstract void setExposureCompensation(int i);

    public abstract void setExposureMeteringMode(int i);

    public abstract void setExposureTime(long j);

    public abstract void setEyeLight(int i);

    public abstract void setFNumber(String str);

    public abstract void setFaceAgeAnalyze(boolean z);

    public abstract void setFaceScore(boolean z);

    public abstract void setFaceWaterMarkEnable(boolean z);

    public abstract void setFaceWaterMarkFormat(String str);

    public void setFixShotTimeEnabled(boolean z) {
        this.mIsFixedShotTimeEnabled = z;
    }

    public abstract void setFlashMode(int i);

    public abstract void setFlawDetectEnable(boolean z);

    public void setFocusCallback(FocusCallback focusCallback) {
        synchronized (this.mCallbackLock) {
            this.mFocusCallback = new WeakReference<>(focusCallback);
        }
    }

    public abstract void setFocusDistance(float f2);

    public abstract void setFocusMode(int i);

    public abstract void setFpsRange(Range<Integer> range);

    public abstract void setFrontMirror(boolean z);

    public abstract void setGlobalWatermark();

    public abstract void setGpsLocation(Location location);

    public abstract void setHDR(boolean z);

    public abstract void setHDRCheckerEnable(boolean z);

    public abstract void setHDRCheckerStatus(int i);

    public abstract void setHDRMode(int i);

    public abstract void setHFRDeflickerEnable(boolean z);

    public abstract void setHHT(boolean z);

    public abstract void setHistogramStatsEnabled(boolean z);

    public abstract void setISO(int i);

    public abstract void setIsFaceExist(boolean z);

    public abstract void setJpegQuality(int i);

    public abstract void setJpegRotation(int i);

    public abstract void setJpegThumbnailSize(CameraSize cameraSize);

    public abstract void setLLS(boolean z);

    public abstract void setLensDirtyDetect(boolean z);

    public abstract void setMFLockAfAe(boolean z);

    public abstract void setMacroMode(boolean z);

    public void setMagneticDetectedCallback(MagneticDetectedCallback magneticDetectedCallback) {
        this.mMagneticDetectedCallback = new WeakReference<>(magneticDetectedCallback);
    }

    public abstract void setMarcroPictureSize(CameraSize cameraSize);

    public void setMetaDataCallback(CameraMetaDataCallback cameraMetaDataCallback) {
        synchronized (this.mCallbackLock) {
            this.mMetadataCallback = new WeakReference<>(cameraMetaDataCallback);
        }
    }

    public abstract void setMfnr(boolean z);

    public abstract void setMiBokeh(boolean z);

    public abstract void setModuleParameter(int i, int i2);

    public abstract void setNeedPausePreview(boolean z);

    public abstract void setNeedSequence(boolean z);

    public abstract void setNewWatermark(boolean z);

    public abstract void setNormalWideLDC(boolean z);

    public abstract void setOnTripodModeStatus(MarshalQueryableASDScene.ASDScene[] aSDSceneArr);

    public abstract void setOpticalZoomToTele(boolean z);

    /* access modifiers changed from: package-private */
    public void setParallelCallback(ParallelCallback parallelCallback) {
        synchronized (this.mCallbackLock) {
            this.mParallelCallback = parallelCallback;
        }
    }

    /* access modifiers changed from: package-private */
    public void setPictureCallback(PictureCallback pictureCallback) {
        synchronized (this.mCallbackLock) {
            this.mPictureCallBack = pictureCallback;
        }
    }

    public abstract void setPictureFormat(int i);

    public abstract void setPictureMaxImages(int i);

    public abstract void setPictureSize(CameraSize cameraSize);

    public abstract void setPortraitLighting(int i);

    /* access modifiers changed from: package-private */
    public void setPreviewCallback(PreviewCallback previewCallback) {
        synchronized (this.mCallbackLock) {
            this.mPreviewCallback = previewCallback;
        }
    }

    public abstract void setPreviewMaxImages(int i);

    public abstract void setPreviewSize(CameraSize cameraSize);

    public abstract void setQcfaEnable(boolean z);

    public abstract void setQuickShotAnimation(boolean z);

    public abstract void setRearBokehEnable(boolean z);

    public abstract void setSatIsZooming(boolean z);

    public abstract void setSaturation(int i);

    public abstract void setSceneMode(int i);

    public void setScreenLightCallback(ScreenLightCallback screenLightCallback) {
        synchronized (this.mCallbackLock) {
            this.mScreenLightCallback = screenLightCallback;
        }
    }

    public abstract void setSensorRawImageSize(CameraSize cameraSize, boolean z);

    public abstract void setSharpness(int i);

    public abstract void setShot2Gallery(boolean z);

    public abstract void setShotSavePath(String str, boolean z);

    public abstract void setShotType(int i);

    public abstract void setSubPictureSize(CameraSize cameraSize);

    public abstract void setSuperNight(boolean z);

    public abstract void setSuperResolution(boolean z);

    public abstract void setSwMfnr(boolean z);

    public abstract void setTelePictureSize(CameraSize cameraSize);

    public abstract void setThermalLevel(int i);

    public abstract void setTimeWaterMarkEnable(boolean z);

    public abstract void setTimeWatermarkValue(String str);

    public abstract void setUltraPixelPortrait(boolean z);

    public abstract void setUltraTelePictureSize(CameraSize cameraSize);

    public abstract void setUltraWideLDC(boolean z);

    public abstract void setUltraWidePictureSize(CameraSize cameraSize);

    public abstract void setUseLegacyFlashMode(boolean z);

    public abstract <T> void setVendorSetting(CaptureRequest.Key<T> key, T t);

    public abstract void setVideoBokehLevelBack(int i);

    public abstract void setVideoBokehLevelFront(float f2);

    public abstract void setVideoFilterColorRetentionBack(boolean z);

    public abstract void setVideoFilterColorRetentionFront(boolean z);

    public abstract void setVideoFilterId(int i);

    public abstract void setVideoFpsRange(Range<Integer> range);

    public abstract void setVideoLogEnabled(boolean z);

    public abstract void setVideoSnapshotSize(CameraSize cameraSize);

    public abstract void setWidePictureSize(CameraSize cameraSize);

    public abstract void setZoomRatio(float f2);

    public abstract void startFaceDetection();

    public abstract void startFocus(FocusTask focusTask, int i);

    public abstract void startHighSpeedRecordPreview();

    public abstract void startHighSpeedRecordSession(@NonNull Surface surface, @NonNull Surface surface2, Range<Integer> range, CameraPreviewCallback cameraPreviewCallback);

    public abstract void startHighSpeedRecording();

    public abstract void startObjectTrack(RectF rectF);

    public abstract void startPreviewCallback(@NonNull PreviewCallback previewCallback);

    public abstract void startPreviewSession(Surface surface, boolean z, boolean z2, boolean z3, int i, boolean z4, CameraPreviewCallback cameraPreviewCallback);

    public abstract void startRecordPreview();

    public abstract void startRecordSession(@NonNull Surface surface, @NonNull Surface surface2, boolean z, int i, CameraPreviewCallback cameraPreviewCallback);

    public abstract void startRecording();

    public abstract void stopFaceDetection();

    public abstract void stopObjectTrack();

    public abstract void stopPreviewCallback(boolean z);

    public abstract void stopRecording(VideoRecordStateCallback videoRecordStateCallback);

    public abstract void takePicture(@NonNull PictureCallback pictureCallback, @NonNull ParallelCallback parallelCallback);

    public abstract void takeSimplePicture(@NonNull PictureCallback pictureCallback, @NonNull ImageSaver imageSaver, @NonNull CameraScreenNail cameraScreenNail);

    public String toString() {
        return super.toString() + " - cameraId=" + getId();
    }

    public abstract void unRegisterCaptureCallback();

    public abstract void unlockExposure();

    public abstract boolean updateDeferPreviewSession(Surface surface);

    public abstract void updateFlashAutoDetectionEnabled(boolean z);

    public abstract boolean useLegacyFlashStrategy();
}
