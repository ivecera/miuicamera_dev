package com.android.camera2;

import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CaptureFailure;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.TotalCaptureResult;
import android.media.Image;
import android.media.ImageReader;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.android.camera.CameraSettings;
import com.android.camera.Util;
import com.android.camera.log.Log;
import com.android.camera.module.loader.camera2.Camera2DataContainer;
import com.android.camera2.Camera2Proxy;
import com.xiaomi.camera.base.PerformanceTracker;
import com.xiaomi.camera.core.ParallelCallback;
import com.xiaomi.camera.core.ParallelTaskData;
import com.xiaomi.stat.d;

public class MiCamera2ShotStill extends MiCamera2Shot<ParallelTaskData> {
    /* access modifiers changed from: private */
    public static final String TAG = "MiCamera2ShotStill";
    /* access modifiers changed from: private */
    public TotalCaptureResult mCaptureResult;
    /* access modifiers changed from: private */
    public ParallelTaskData mCurrentParallelTaskData;
    private boolean mHasDepth;
    /* access modifiers changed from: private */
    public boolean mIsIntent;
    /* access modifiers changed from: private */
    public boolean mNeedCaptureResult;

    public MiCamera2ShotStill(MiCamera2 miCamera2) {
        super(miCamera2);
    }

    /* access modifiers changed from: private */
    public void notifyResultData(ParallelTaskData parallelTaskData, @Nullable CaptureResult captureResult, @Nullable CameraCharacteristics cameraCharacteristics) {
        ParallelCallback parallelCallback = getParallelCallback();
        if (parallelCallback == null) {
            Log.w(TAG, "notifyResultData: null parallel callback");
            return;
        }
        long currentTimeMillis = System.currentTimeMillis();
        this.mCurrentParallelTaskData.setPreviewThumbnailHash(((MiCamera2Shot) this).mPreviewThumbnailHash);
        parallelCallback.onParallelProcessFinish(parallelTaskData, captureResult, cameraCharacteristics);
        String str = TAG;
        Log.d(str, "mJpegCallbackFinishTime = " + (System.currentTimeMillis() - currentTimeMillis) + d.H);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera2.MiCamera2Shot
    public CameraCaptureSession.CaptureCallback generateCaptureCallback() {
        return new CameraCaptureSession.CaptureCallback() {
            /* class com.android.camera2.MiCamera2ShotStill.AnonymousClass1 */

            public void onCaptureCompleted(@NonNull CameraCaptureSession cameraCaptureSession, @NonNull CaptureRequest captureRequest, @NonNull TotalCaptureResult totalCaptureResult) {
                Log.d(MiCamera2ShotStill.TAG, "onCaptureCompleted: " + totalCaptureResult.getFrameNumber());
                boolean z = false;
                if (((MiCamera2Shot) MiCamera2ShotStill.this).mMiCamera.getSuperNight()) {
                    ((MiCamera2Shot) MiCamera2ShotStill.this).mMiCamera.setAWBLock(false);
                }
                MiCamera2ShotStill miCamera2ShotStill = MiCamera2ShotStill.this;
                ((MiCamera2Shot) miCamera2ShotStill).mMiCamera.onCapturePictureFinished(true, miCamera2ShotStill);
                ((MiCamera2Shot) MiCamera2ShotStill.this).mMiCamera.setCaptureEnable(true);
                TotalCaptureResult unused = MiCamera2ShotStill.this.mCaptureResult = totalCaptureResult;
                if (MiCamera2ShotStill.this.mNeedCaptureResult) {
                    Camera2Proxy.PictureCallback pictureCallback = MiCamera2ShotStill.this.getPictureCallback();
                    if (pictureCallback == null || MiCamera2ShotStill.this.mCurrentParallelTaskData == null) {
                        Log.w(MiCamera2ShotStill.TAG, "onCaptureCompleted: something wrong: callback = " + pictureCallback + " mCurrentParallelTaskData = " + MiCamera2ShotStill.this.mCurrentParallelTaskData);
                        return;
                    }
                    if (MiCamera2ShotStill.this.mCurrentParallelTaskData.isJpegDataReady() && MiCamera2ShotStill.this.mCaptureResult != null) {
                        z = true;
                    }
                    if (!z) {
                        return;
                    }
                    if (MiCamera2ShotStill.this.mIsIntent) {
                        MiCamera2ShotStill miCamera2ShotStill2 = MiCamera2ShotStill.this;
                        miCamera2ShotStill2.notifyResultData(miCamera2ShotStill2.mCurrentParallelTaskData);
                        pictureCallback.onPictureTakenFinished(true);
                        return;
                    }
                    pictureCallback.onPictureTakenFinished(true);
                    MiCamera2ShotStill miCamera2ShotStill3 = MiCamera2ShotStill.this;
                    miCamera2ShotStill3.notifyResultData(miCamera2ShotStill3.mCurrentParallelTaskData, MiCamera2ShotStill.this.mCaptureResult, ((MiCamera2Shot) MiCamera2ShotStill.this).mMiCamera.getCapabilities().getCameraCharacteristics());
                }
            }

            public void onCaptureFailed(@NonNull CameraCaptureSession cameraCaptureSession, @NonNull CaptureRequest captureRequest, @NonNull CaptureFailure captureFailure) {
                super.onCaptureFailed(cameraCaptureSession, captureRequest, captureFailure);
                String access$000 = MiCamera2ShotStill.TAG;
                Log.e(access$000, "onCaptureFailed: reason=" + captureFailure.getReason());
                if (((MiCamera2Shot) MiCamera2ShotStill.this).mMiCamera.getSuperNight()) {
                    ((MiCamera2Shot) MiCamera2ShotStill.this).mMiCamera.setAWBLock(false);
                }
                MiCamera2ShotStill miCamera2ShotStill = MiCamera2ShotStill.this;
                ((MiCamera2Shot) miCamera2ShotStill).mMiCamera.onCapturePictureFinished(false, miCamera2ShotStill);
                ((MiCamera2Shot) MiCamera2ShotStill.this).mMiCamera.setCaptureEnable(true);
            }

            public void onCaptureStarted(@NonNull CameraCaptureSession cameraCaptureSession, @NonNull CaptureRequest captureRequest, long j, long j2) {
                super.onCaptureStarted(cameraCaptureSession, captureRequest, j, j2);
                if ((!CameraSettings.isSupportedZslShutter() || CameraSettings.isUltraPixelOn()) && !CameraSettings.getPlayToneOnCaptureStart()) {
                    Camera2Proxy.PictureCallback pictureCallback = MiCamera2ShotStill.this.getPictureCallback();
                    if (pictureCallback != null) {
                        pictureCallback.onCaptureShutter(false);
                    } else {
                        Log.w(MiCamera2ShotStill.TAG, "onCaptureStarted: null picture callback");
                    }
                }
                if (0 == MiCamera2ShotStill.this.mCurrentParallelTaskData.getTimestamp()) {
                    MiCamera2ShotStill.this.mCurrentParallelTaskData.setTimestamp(j);
                }
                String access$000 = MiCamera2ShotStill.TAG;
                Log.d(access$000, "onCaptureStarted: mCurrentParallelTaskData: " + MiCamera2ShotStill.this.mCurrentParallelTaskData.getTimestamp());
            }
        };
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera2.MiCamera2Shot
    public CaptureRequest.Builder generateRequestBuilder() throws CameraAccessException, IllegalStateException {
        CaptureRequest.Builder createCaptureRequest = ((MiCamera2Shot) this).mMiCamera.getCameraDevice().createCaptureRequest(2);
        ImageReader photoImageReader = ((MiCamera2Shot) this).mMiCamera.getPhotoImageReader();
        createCaptureRequest.addTarget(photoImageReader.getSurface());
        String str = TAG;
        Log.d(str, "size=" + photoImageReader.getWidth() + "x" + photoImageReader.getHeight());
        if (!isInQcfaMode() || Camera2DataContainer.getInstance().getBokehFrontCameraId() == ((MiCamera2Shot) this).mMiCamera.getId()) {
            createCaptureRequest.addTarget(((MiCamera2Shot) this).mMiCamera.getPreviewSurface());
        }
        if (((MiCamera2Shot) this).mMiCamera.isConfigRawStream()) {
            createCaptureRequest.addTarget(((MiCamera2Shot) this).mMiCamera.getRawSurface());
        }
        if (this.mHasDepth) {
            createCaptureRequest.addTarget(((MiCamera2Shot) this).mMiCamera.getDepthImageReader().getSurface());
            createCaptureRequest.addTarget(((MiCamera2Shot) this).mMiCamera.getPortraitRawImageReader().getSurface());
        }
        createCaptureRequest.set(CaptureRequest.CONTROL_AF_MODE, (Integer) ((MiCamera2Shot) this).mMiCamera.getPreviewRequestBuilder().get(CaptureRequest.CONTROL_AF_MODE));
        ((MiCamera2Shot) this).mMiCamera.applySettingsForCapture(createCaptureRequest, 3);
        if (((MiCamera2Shot) this).mMiCamera.useLegacyFlashStrategy() && ((MiCamera2Shot) this).mMiCamera.isNeedFlashOn()) {
            ((MiCamera2Shot) this).mMiCamera.pausePreview();
        }
        return createCaptureRequest;
    }

    /* access modifiers changed from: protected */
    public long getTimeStamp() {
        ParallelTaskData parallelTaskData = this.mCurrentParallelTaskData;
        if (parallelTaskData == null) {
            return 0;
        }
        return parallelTaskData.getTimestamp();
    }

    /* access modifiers changed from: protected */
    public void notifyResultData(ParallelTaskData parallelTaskData) {
        notifyResultData(parallelTaskData, null, null);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera2.MiCamera2Shot
    public void onImageReceived(Image image, int i) {
        ParallelTaskData parallelTaskData;
        Camera2Proxy.PictureCallback pictureCallback = getPictureCallback();
        if (pictureCallback == null || (parallelTaskData = this.mCurrentParallelTaskData) == null) {
            String str = TAG;
            Log.w(str, "onImageReceived: something wrong happened when image received: callback = " + pictureCallback + " mCurrentParallelTaskData = " + this.mCurrentParallelTaskData);
            image.close();
            return;
        }
        if (0 == parallelTaskData.getTimestamp()) {
            Log.w(TAG, "onImageReceived: image arrived first");
            this.mCurrentParallelTaskData.setTimestamp(image.getTimestamp());
        }
        if (this.mCurrentParallelTaskData.getTimestamp() == image.getTimestamp() || !this.mCurrentParallelTaskData.isDataFilled(i)) {
            String str2 = TAG;
            Log.d(str2, "onImageReceived mCurrentParallelTaskData timestamp:" + this.mCurrentParallelTaskData.getTimestamp() + " image timestamp:" + image.getTimestamp());
            byte[] firstPlane = Util.getFirstPlane(image);
            String str3 = TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("onImageReceived: dataLen=");
            sb.append(firstPlane == null ? "null" : Integer.valueOf(firstPlane.length));
            sb.append(" resultType = ");
            sb.append(i);
            sb.append(" timeStamp=");
            sb.append(image.getTimestamp());
            sb.append(" holder=");
            sb.append(hashCode());
            Log.d(str3, sb.toString());
            image.close();
            this.mCurrentParallelTaskData.fillJpegData(firstPlane, i);
            if (!(this.mNeedCaptureResult ? this.mCurrentParallelTaskData.isJpegDataReady() && this.mCaptureResult != null : this.mCurrentParallelTaskData.isJpegDataReady())) {
                return;
            }
            if (this.mIsIntent) {
                notifyResultData(this.mCurrentParallelTaskData);
                pictureCallback.onPictureTakenFinished(true);
                return;
            }
            pictureCallback.onPictureTakenFinished(true);
            notifyResultData(this.mCurrentParallelTaskData, this.mCaptureResult, ((MiCamera2Shot) this).mMiCamera.getCapabilities().getCameraCharacteristics());
            return;
        }
        String str4 = TAG;
        Log.e(str4, "image has been filled " + i);
        image.close();
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera2.MiCamera2Shot
    public void prepare() {
        if (((MiCamera2Shot) this).mMiCamera.getSuperNight()) {
            ((MiCamera2Shot) this).mMiCamera.setAWBLock(true);
        }
        int shotType = ((MiCamera2Shot) this).mMiCamera.getCameraConfigs().getShotType();
        if (shotType == -3) {
            this.mHasDepth = true;
            this.mIsIntent = true;
        } else if (shotType == -2) {
            this.mIsIntent = true;
        } else if (shotType == 1) {
            this.mNeedCaptureResult = true;
        } else if (shotType == 2) {
            this.mHasDepth = true;
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera2.MiCamera2Shot
    public void startSessionCapture() {
        try {
            this.mCurrentParallelTaskData = generateParallelTaskData(0);
            if (this.mCurrentParallelTaskData == null) {
                Log.w(TAG, "startSessionCapture: null task data");
                return;
            }
            this.mCurrentParallelTaskData.setShot2Gallery(((MiCamera2Shot) this).mMiCamera.getCameraConfigs().isShot2Gallery());
            CameraCaptureSession.CaptureCallback generateCaptureCallback = generateCaptureCallback();
            CaptureRequest.Builder generateRequestBuilder = generateRequestBuilder();
            PerformanceTracker.trackPictureCapture(0);
            Log.dumpRequest("shotstill  for camera " + ((MiCamera2Shot) this).mMiCamera.getId(), generateRequestBuilder.build());
            ((MiCamera2Shot) this).mMiCamera.getCaptureSession().capture(generateRequestBuilder.build(), generateCaptureCallback, ((MiCamera2Shot) this).mCameraHandler);
        } catch (CameraAccessException e2) {
            e2.printStackTrace();
            Log.e(TAG, "Cannot capture a still picture");
            ((MiCamera2Shot) this).mMiCamera.notifyOnError(e2.getReason());
        } catch (IllegalStateException e3) {
            Log.e(TAG, "Failed to capture a still picture, IllegalState", e3);
            ((MiCamera2Shot) this).mMiCamera.notifyOnError(256);
        }
    }
}
