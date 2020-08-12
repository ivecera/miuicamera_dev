package com.android.camera2;

import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CaptureFailure;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.TotalCaptureResult;
import android.media.Image;
import android.support.annotation.NonNull;
import android.view.Surface;
import com.android.camera.CameraSettings;
import com.android.camera.Util;
import com.android.camera.log.Log;
import com.android.camera2.Camera2Proxy;
import com.android.camera2.compat.MiCameraCompat;
import com.xiaomi.camera.base.PerformanceTracker;
import com.xiaomi.camera.core.ParallelCallback;
import com.xiaomi.camera.core.ParallelTaskData;
import com.xiaomi.stat.d;
import java.util.ArrayList;

public class MiCamera2ShotRawBurst extends MiCamera2Shot<ParallelTaskData> {
    public static final int BASE_EV_INDEX = 3;
    public static final int[] EV_LIST = {-24, -12, 0, 0, 0, 0, 0, 0};
    private static final String TAG = "SuperNightRawBurst";
    /* access modifiers changed from: private */
    public ParallelTaskData mCurrentParallelTaskData = null;
    /* access modifiers changed from: private */
    public final SuperNightReprocessHandler mReprocessHandler;

    public MiCamera2ShotRawBurst(MiCamera2 miCamera2, SuperNightReprocessHandler superNightReprocessHandler) {
        super(miCamera2);
        this.mReprocessHandler = superNightReprocessHandler;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera2.MiCamera2Shot
    public CameraCaptureSession.CaptureCallback generateCaptureCallback() {
        return new CameraCaptureSession.CaptureCallback() {
            /* class com.android.camera2.MiCamera2ShotRawBurst.AnonymousClass1 */

            public void onCaptureBufferLost(@NonNull CameraCaptureSession cameraCaptureSession, @NonNull CaptureRequest captureRequest, @NonNull Surface surface, long j) {
                super.onCaptureBufferLost(cameraCaptureSession, captureRequest, surface, j);
                Log.e(MiCamera2ShotRawBurst.TAG, "onCaptureBufferLost:<RAW>: frameNumber = " + j);
                MiCamera2ShotRawBurst.this.mReprocessHandler.cancel();
                if (((MiCamera2Shot) MiCamera2ShotRawBurst.this).mMiCamera.getSuperNight()) {
                    ((MiCamera2Shot) MiCamera2ShotRawBurst.this).mMiCamera.setAWBLock(false);
                }
                MiCamera2ShotRawBurst miCamera2ShotRawBurst = MiCamera2ShotRawBurst.this;
                ((MiCamera2Shot) miCamera2ShotRawBurst).mMiCamera.onCapturePictureFinished(false, miCamera2ShotRawBurst);
                ((MiCamera2Shot) MiCamera2ShotRawBurst.this).mMiCamera.setCaptureEnable(true);
            }

            public void onCaptureCompleted(@NonNull CameraCaptureSession cameraCaptureSession, @NonNull CaptureRequest captureRequest, @NonNull TotalCaptureResult totalCaptureResult) {
                Log.d(MiCamera2ShotRawBurst.TAG, "onCaptureCompleted:<RAW>: " + totalCaptureResult.getFrameNumber());
                MiCamera2ShotRawBurst miCamera2ShotRawBurst = MiCamera2ShotRawBurst.this;
                if (((MiCamera2Shot) miCamera2ShotRawBurst).mDeparted) {
                    Log.d(MiCamera2ShotRawBurst.TAG, "onCaptureCompleted:<RAW>: ignored as has departed");
                } else {
                    miCamera2ShotRawBurst.mReprocessHandler.queueCaptureResult(totalCaptureResult);
                }
            }

            public void onCaptureFailed(@NonNull CameraCaptureSession cameraCaptureSession, @NonNull CaptureRequest captureRequest, @NonNull CaptureFailure captureFailure) {
                super.onCaptureFailed(cameraCaptureSession, captureRequest, captureFailure);
                Log.e(MiCamera2ShotRawBurst.TAG, "onCaptureFailed:<RAW>: reason = " + captureFailure.getReason());
                MiCamera2ShotRawBurst.this.mReprocessHandler.cancel();
                if (((MiCamera2Shot) MiCamera2ShotRawBurst.this).mMiCamera.getSuperNight()) {
                    ((MiCamera2Shot) MiCamera2ShotRawBurst.this).mMiCamera.setAWBLock(false);
                }
                MiCamera2ShotRawBurst miCamera2ShotRawBurst = MiCamera2ShotRawBurst.this;
                ((MiCamera2Shot) miCamera2ShotRawBurst).mMiCamera.onCapturePictureFinished(false, miCamera2ShotRawBurst);
                ((MiCamera2Shot) MiCamera2ShotRawBurst.this).mMiCamera.setCaptureEnable(true);
            }

            public void onCaptureStarted(@NonNull CameraCaptureSession cameraCaptureSession, @NonNull CaptureRequest captureRequest, long j, long j2) {
                Log.d(MiCamera2ShotRawBurst.TAG, "onCaptureStarted:<RAW>: " + j2);
                super.onCaptureStarted(cameraCaptureSession, captureRequest, j, j2);
                if (!CameraSettings.isSupportedZslShutter() && !CameraSettings.getPlayToneOnCaptureStart()) {
                    Camera2Proxy.PictureCallback pictureCallback = MiCamera2ShotRawBurst.this.getPictureCallback();
                    if (pictureCallback != null) {
                        pictureCallback.onCaptureShutter(false);
                    } else {
                        Log.w(MiCamera2ShotRawBurst.TAG, "onCaptureStarted:<RAW>: null picture callback");
                    }
                }
                if (0 == MiCamera2ShotRawBurst.this.mCurrentParallelTaskData.getTimestamp()) {
                    MiCamera2ShotRawBurst.this.mCurrentParallelTaskData.setTimestamp(j);
                }
            }
        };
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera2.MiCamera2Shot
    public CaptureRequest.Builder generateRequestBuilder() throws CameraAccessException, IllegalStateException {
        CaptureRequest.Builder createCaptureRequest = ((MiCamera2Shot) this).mMiCamera.getCameraDevice().createCaptureRequest(2);
        createCaptureRequest.addTarget(((MiCamera2Shot) this).mMiCamera.getRawImageReader().getSurface());
        createCaptureRequest.set(CaptureRequest.CONTROL_AF_MODE, (Integer) ((MiCamera2Shot) this).mMiCamera.getPreviewRequestBuilder().get(CaptureRequest.CONTROL_AF_MODE));
        ((MiCamera2Shot) this).mMiCamera.applySettingsForCapture(createCaptureRequest, 3);
        return createCaptureRequest;
    }

    /* access modifiers changed from: protected */
    public void notifyResultData(ParallelTaskData parallelTaskData) {
        ParallelCallback parallelCallback = getParallelCallback();
        if (parallelCallback == null) {
            Log.w(TAG, "notifyResultData: null parallel callback");
            return;
        }
        long currentTimeMillis = System.currentTimeMillis();
        this.mCurrentParallelTaskData.setPreviewThumbnailHash(((MiCamera2Shot) this).mPreviewThumbnailHash);
        parallelCallback.onParallelProcessFinish(this.mCurrentParallelTaskData, null, null);
        long currentTimeMillis2 = System.currentTimeMillis() - currentTimeMillis;
        Log.d(TAG, "mJpegCallbackFinishTime = " + currentTimeMillis2 + d.H);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera2.MiCamera2Shot
    public void onImageReceived(Image image, int i) {
        Camera2Proxy.PictureCallback pictureCallback = getPictureCallback();
        if (pictureCallback == null || this.mCurrentParallelTaskData == null || ((MiCamera2Shot) this).mDeparted) {
            Log.w(TAG, "something wrong happened when image received: callback = " + pictureCallback + " mCurrentParallelTaskData = " + this.mCurrentParallelTaskData);
            image.close();
        } else if (i == 0) {
            Log.w(TAG, "onImageReceived:<JPEG>");
            if (0 == this.mCurrentParallelTaskData.getTimestamp()) {
                Log.w(TAG, "onImageReceived<JPEG>: image arrived first");
                this.mCurrentParallelTaskData.setTimestamp(image.getTimestamp());
            }
            byte[] firstPlane = Util.getFirstPlane(image);
            StringBuilder sb = new StringBuilder();
            sb.append("onImageReceived:<JPEG>: size = ");
            sb.append(firstPlane == null ? 0 : firstPlane.length);
            sb.append(", timeStamp = ");
            sb.append(image.getTimestamp());
            Log.d(TAG, sb.toString());
            image.close();
            this.mCurrentParallelTaskData.fillJpegData(firstPlane, i);
            if (this.mCurrentParallelTaskData.isJpegDataReady()) {
                pictureCallback.onPictureTakenFinished(true);
                notifyResultData(this.mCurrentParallelTaskData);
            }
        } else if (i == 3) {
            Log.w(TAG, "onImageReceived:<RAW>");
            this.mReprocessHandler.queueImage(image);
        } else {
            throw new IllegalArgumentException("Unknown image result type: " + i);
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera2.MiCamera2Shot
    public void prepare() {
        this.mReprocessHandler.prepare(this);
        if (((MiCamera2Shot) this).mMiCamera.getSuperNight()) {
            ((MiCamera2Shot) this).mMiCamera.setAWBLock(true);
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
            ArrayList arrayList = new ArrayList();
            int i = 0;
            while (i < EV_LIST.length) {
                MiCameraCompat.applyRawReprocessHint(generateRequestBuilder, i == 3);
                generateRequestBuilder.set(CaptureRequest.CONTROL_AE_LOCK, Boolean.TRUE);
                generateRequestBuilder.set(CaptureRequest.CONTROL_AE_EXPOSURE_COMPENSATION, Integer.valueOf(EV_LIST[i]));
                arrayList.add(generateRequestBuilder.build());
                i++;
            }
            PerformanceTracker.trackPictureCapture(0);
            Log.d(TAG, "start capture burst");
            ((MiCamera2Shot) this).mMiCamera.getCaptureSession().captureBurst(arrayList, generateCaptureCallback, ((MiCamera2Shot) this).mCameraHandler);
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
