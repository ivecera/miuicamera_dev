package com.android.camera2;

import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CaptureFailure;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.utils.SurfaceUtils;
import android.media.Image;
import android.support.annotation.NonNull;
import android.util.Size;
import android.view.Surface;
import com.android.camera.log.Log;
import com.android.camera.parallel.AlgoConnector;
import com.android.camera2.Camera2Proxy;
import com.android.camera2.compat.MiCameraCompat;
import com.mi.config.b;
import com.xiaomi.camera.base.CameraDeviceUtil;
import com.xiaomi.camera.base.PerformanceTracker;
import com.xiaomi.camera.core.ParallelTaskData;
import com.xiaomi.camera.imagecodec.ImagePool;
import java.util.Locale;

public class MiCamera2ShotParallelRepeating extends MiCamera2ShotParallel<byte[]> {
    private static final int INVALID_SEQUENCE_ID = -1;
    private static final String TAG = "ParallelRepeating";
    /* access modifiers changed from: private */
    public CaptureResult mRepeatingCaptureResult;

    public MiCamera2ShotParallelRepeating(MiCamera2 miCamera2, int i) {
        super(miCamera2);
    }

    /* access modifiers changed from: private */
    public void onRepeatingEnd(boolean z, int i) {
        ((MiCamera2Shot) this).mMiCamera.setAWBLock(false);
        ((MiCamera2Shot) this).mMiCamera.resumePreview();
        if (-1 != i) {
            Camera2Proxy.PictureCallback pictureCallback = getPictureCallback();
            if (pictureCallback != null) {
                pictureCallback.onPictureTakenFinished(z);
            } else {
                Log.w(TAG, "onRepeatingEnd: null picture callback");
            }
            ((MiCamera2Shot) this).mMiCamera.onMultiSnapEnd(z, this);
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera2.MiCamera2Shot
    public CameraCaptureSession.CaptureCallback generateCaptureCallback() {
        return new CameraCaptureSession.CaptureCallback() {
            /* class com.android.camera2.MiCamera2ShotParallelRepeating.AnonymousClass1 */
            long mCaptureTimestamp = -1;

            public void onCaptureCompleted(@NonNull CameraCaptureSession cameraCaptureSession, @NonNull CaptureRequest captureRequest, @NonNull TotalCaptureResult totalCaptureResult) {
                super.onCaptureCompleted(cameraCaptureSession, captureRequest, totalCaptureResult);
                Log.d(MiCamera2ShotParallelRepeating.TAG, "onCaptureCompleted: frameNumber=" + totalCaptureResult.getFrameNumber());
                CaptureResult unused = MiCamera2ShotParallelRepeating.this.mRepeatingCaptureResult = totalCaptureResult;
                AlgoConnector.getInstance().getLocalBinder().onCaptureCompleted(CameraDeviceUtil.getCustomCaptureResult(MiCamera2ShotParallelRepeating.this.mRepeatingCaptureResult), true);
            }

            public void onCaptureFailed(@NonNull CameraCaptureSession cameraCaptureSession, @NonNull CaptureRequest captureRequest, @NonNull CaptureFailure captureFailure) {
                super.onCaptureFailed(cameraCaptureSession, captureRequest, captureFailure);
                Log.e(MiCamera2ShotParallelRepeating.TAG, "onCaptureFailed: reason=" + captureFailure.getReason());
                MiCamera2ShotParallelRepeating.this.onRepeatingEnd(false, -1);
                if (this.mCaptureTimestamp != -1) {
                    AlgoConnector.getInstance().getLocalBinder().onCaptureFailed(this.mCaptureTimestamp, captureFailure.getReason());
                }
            }

            public void onCaptureSequenceAborted(@NonNull CameraCaptureSession cameraCaptureSession, int i) {
                super.onCaptureSequenceAborted(cameraCaptureSession, i);
                Log.w(MiCamera2ShotParallelRepeating.TAG, "onCaptureSequenceAborted: sequenceId=" + i);
                MiCamera2ShotParallelRepeating.this.onRepeatingEnd(false, i);
                ImagePool.getInstance().trimPoolBuffer();
            }

            public void onCaptureSequenceCompleted(@NonNull CameraCaptureSession cameraCaptureSession, int i, long j) {
                Log.d(MiCamera2ShotParallelRepeating.TAG, "onCaptureSequenceCompleted: sequenceId=" + i + " frameNumber=" + j);
                MiCamera2ShotParallelRepeating.this.onRepeatingEnd(true, i);
                ImagePool.getInstance().trimPoolBuffer();
            }

            public void onCaptureStarted(@NonNull CameraCaptureSession cameraCaptureSession, @NonNull CaptureRequest captureRequest, long j, long j2) {
                super.onCaptureStarted(cameraCaptureSession, captureRequest, j, j2);
                Log.d(MiCamera2ShotParallelRepeating.TAG, "onCaptureStarted: timestamp=" + j + " frameNumber=" + j2);
                Camera2Proxy.PictureCallback pictureCallback = MiCamera2ShotParallelRepeating.this.getPictureCallback();
                if (pictureCallback != null) {
                    ParallelTaskData parallelTaskData = new ParallelTaskData(((MiCamera2Shot) MiCamera2ShotParallelRepeating.this).mMiCamera.getId(), j, ((MiCamera2Shot) MiCamera2ShotParallelRepeating.this).mMiCamera.getCameraConfigs().getShotType(), ((MiCamera2Shot) MiCamera2ShotParallelRepeating.this).mMiCamera.getCameraConfigs().getShotPath());
                    MiCamera2ShotParallelRepeating miCamera2ShotParallelRepeating = MiCamera2ShotParallelRepeating.this;
                    ParallelTaskData onCaptureStart = pictureCallback.onCaptureStart(parallelTaskData, ((MiCamera2ShotParallel) miCamera2ShotParallelRepeating).mCapturedImageSize, miCamera2ShotParallelRepeating.isQuickShotAnimation());
                    if (onCaptureStart != null) {
                        onCaptureStart.setAlgoType(4);
                        onCaptureStart.setBurstNum(1);
                        this.mCaptureTimestamp = j;
                        AlgoConnector.getInstance().getLocalBinder().onCaptureStarted(onCaptureStart);
                        return;
                    }
                    Log.w(MiCamera2ShotParallelRepeating.TAG, "onCaptureStarted: null task data");
                    return;
                }
                Log.w(MiCamera2ShotParallelRepeating.TAG, "onCaptureStarted: null picture callback");
            }
        };
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera2.MiCamera2Shot
    public CaptureRequest.Builder generateRequestBuilder() throws CameraAccessException, IllegalStateException {
        CaptureRequest.Builder createCaptureRequest = b.isMTKPlatform() ? ((MiCamera2Shot) this).mMiCamera.getCameraDevice().createCaptureRequest(1) : ((MiCamera2Shot) this).mMiCamera.getCameraDevice().createCaptureRequest(2);
        if (isIn3OrMoreSatMode() || isInMultiSurfaceSatMode()) {
            Surface mainCaptureSurface = getMainCaptureSurface();
            Size surfaceSize = SurfaceUtils.getSurfaceSize(mainCaptureSurface);
            Log.d(TAG, String.format(Locale.ENGLISH, "add surface %s to capture request, size is: %s", mainCaptureSurface, surfaceSize));
            createCaptureRequest.addTarget(mainCaptureSurface);
            int i = 513;
            if (mainCaptureSurface == ((MiCamera2Shot) this).mMiCamera.getUltraWideRemoteSurface()) {
                i = 3;
            }
            Log.d(TAG, "combinationMode: " + i);
            configParallelSession(surfaceSize, i);
        } else {
            for (Surface surface : ((MiCamera2Shot) this).mMiCamera.getRemoteSurfaceList()) {
                Log.d(TAG, String.format(Locale.ENGLISH, "add surface %s to capture request, size is: %s", surface, SurfaceUtils.getSurfaceSize(surface)));
                createCaptureRequest.addTarget(surface);
            }
            ((MiCamera2ShotParallel) this).mCapturedImageSize = ((MiCamera2Shot) this).mMiCamera.getPictureSize();
        }
        createCaptureRequest.addTarget(((MiCamera2Shot) this).mMiCamera.getPreviewSurface());
        createCaptureRequest.set(CaptureRequest.CONTROL_AF_MODE, (Integer) ((MiCamera2Shot) this).mMiCamera.getPreviewRequestBuilder().get(CaptureRequest.CONTROL_AF_MODE));
        ((MiCamera2Shot) this).mMiCamera.applySettingsForCapture(createCaptureRequest, 3);
        MiCameraCompat.applyMfnrEnable(createCaptureRequest, false);
        MiCameraCompat.applyHDR(createCaptureRequest, false);
        MiCameraCompat.applySuperResolution(createCaptureRequest, false);
        MiCameraCompat.applyDepurpleEnable(createCaptureRequest, false);
        if (isIn3OrMoreSatMode() && !b.isMTKPlatform()) {
            CaptureRequestBuilder.applySmoothTransition(createCaptureRequest, ((MiCamera2Shot) this).mMiCamera.getCapabilities(), false);
            CaptureRequestBuilder.applySatFallback(createCaptureRequest, ((MiCamera2Shot) this).mMiCamera.getCapabilities(), false);
        }
        MiCameraCompat.applyMultiFrameInputNum(createCaptureRequest, 1);
        if (((MiCamera2Shot) this).mMiCamera.getCapabilities().isSupportBurstHint()) {
            MiCameraCompat.applyBurstHint(createCaptureRequest, 1);
        }
        return createCaptureRequest;
    }

    /* access modifiers changed from: protected */
    public void notifyResultData(byte[] bArr) {
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera2.MiCamera2ShotParallel, com.android.camera2.MiCamera2Shot
    public void onImageReceived(Image image, int i) {
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera2.MiCamera2Shot
    public void prepare() {
        ((MiCamera2Shot) this).mMiCamera.setAWBLock(true);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera2.MiCamera2Shot
    public void startSessionCapture() {
        Log.d(TAG, "startSessionCapture");
        PerformanceTracker.trackPictureCapture(0);
        ((MiCamera2Shot) this).mMiCamera.pausePreview();
        try {
            int repeatingRequest = ((MiCamera2Shot) this).mMiCamera.getCaptureSession().setRepeatingRequest(generateRequestBuilder().build(), generateCaptureCallback(), ((MiCamera2Shot) this).mCameraHandler);
            Log.d(TAG, "repeating sequenceId: " + repeatingRequest);
        } catch (CameraAccessException e2) {
            e2.printStackTrace();
            ((MiCamera2Shot) this).mMiCamera.notifyOnError(e2.getReason());
        } catch (IllegalStateException e3) {
            Log.e(TAG, "Failed to capture burst, IllegalState", e3);
            ((MiCamera2Shot) this).mMiCamera.notifyOnError(256);
        } catch (IllegalArgumentException e4) {
            Log.e(TAG, "Failed to capture a still picture, IllegalArgument", e4);
            ((MiCamera2Shot) this).mMiCamera.notifyOnError(256);
        }
    }
}
