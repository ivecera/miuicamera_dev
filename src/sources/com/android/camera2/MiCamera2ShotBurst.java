package com.android.camera2;

import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CaptureFailure;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.TotalCaptureResult;
import android.media.Image;
import android.support.annotation.NonNull;
import com.android.camera.Util;
import com.android.camera.log.Log;
import com.android.camera2.Camera2Proxy;
import com.android.camera2.compat.MiCameraCompat;
import com.mi.config.b;

public class MiCamera2ShotBurst extends MiCamera2Shot<byte[]> {
    private static final int INVALID_SEQUENCE_ID = -1;
    /* access modifiers changed from: private */
    public static final String TAG = "MiCamera2ShotBurst";
    /* access modifiers changed from: private */
    public TotalCaptureResult mCaptureResult;
    /* access modifiers changed from: private */
    public int mLatestSequenceId = 0;

    public MiCamera2ShotBurst(MiCamera2 miCamera2, int i) {
        super(miCamera2);
    }

    private void notifyResultData(byte[] bArr, CaptureResult captureResult) {
        Camera2Proxy.PictureCallback pictureCallback = getPictureCallback();
        if (pictureCallback != null) {
            pictureCallback.onPictureTaken(bArr, captureResult);
        } else {
            Log.w(TAG, "notifyResultData: null picture callback");
        }
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
            /* class com.android.camera2.MiCamera2ShotBurst.AnonymousClass1 */

            public void onCaptureCompleted(@NonNull CameraCaptureSession cameraCaptureSession, @NonNull CaptureRequest captureRequest, @NonNull TotalCaptureResult totalCaptureResult) {
                TotalCaptureResult unused = MiCamera2ShotBurst.this.mCaptureResult = totalCaptureResult;
                ((MiCamera2Shot) MiCamera2ShotBurst.this).mMiCamera.updateFrameNumber(totalCaptureResult.getFrameNumber());
            }

            public void onCaptureFailed(@NonNull CameraCaptureSession cameraCaptureSession, @NonNull CaptureRequest captureRequest, @NonNull CaptureFailure captureFailure) {
                super.onCaptureFailed(cameraCaptureSession, captureRequest, captureFailure);
                MiCamera2ShotBurst.this.onRepeatingEnd(false, -1);
                String access$100 = MiCamera2ShotBurst.TAG;
                Log.e(access$100, "onCaptureFailed: " + captureFailure.getReason());
            }

            public void onCaptureSequenceAborted(@NonNull CameraCaptureSession cameraCaptureSession, int i) {
                super.onCaptureSequenceAborted(cameraCaptureSession, i);
                String access$100 = MiCamera2ShotBurst.TAG;
                Log.w(access$100, "onCaptureSequenceAborted: " + i);
                if (MiCamera2ShotBurst.this.mLatestSequenceId == i) {
                    MiCamera2ShotBurst.this.onRepeatingEnd(false, i);
                }
            }

            public void onCaptureSequenceCompleted(@NonNull CameraCaptureSession cameraCaptureSession, int i, long j) {
                String access$100 = MiCamera2ShotBurst.TAG;
                Log.d(access$100, "onCaptureSequenceCompleted: sequenceId=" + i + " mLatestSequenceId = " + MiCamera2ShotBurst.this.mLatestSequenceId + " frameNumber=" + j);
                if (MiCamera2ShotBurst.this.mLatestSequenceId == i) {
                    MiCamera2ShotBurst.this.onRepeatingEnd(true, i);
                }
            }
        };
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera2.MiCamera2Shot
    public CaptureRequest.Builder generateRequestBuilder() throws CameraAccessException, IllegalStateException {
        CaptureRequest.Builder builder;
        if (b.isMTKPlatform()) {
            builder = ((MiCamera2Shot) this).mMiCamera.getCameraDevice().createCaptureRequest(1);
            builder.set(CaptureRequest.CONTROL_CAPTURE_INTENT, 1);
        } else {
            builder = ((MiCamera2Shot) this).mMiCamera.getCameraDevice().createCaptureRequest(2);
            builder.set(CaptureRequest.CONTROL_CAPTURE_INTENT, 2);
        }
        builder.addTarget(((MiCamera2Shot) this).mMiCamera.getPhotoImageReader().getSurface());
        builder.addTarget(((MiCamera2Shot) this).mMiCamera.getPreviewSurface());
        ((MiCamera2Shot) this).mMiCamera.applySettingsForCapture(builder, 4);
        if (((MiCamera2Shot) this).mMiCamera.getCapabilities().isSupportBurstHint()) {
            MiCameraCompat.applyBurstHint(builder, 1);
        }
        return builder;
    }

    /* access modifiers changed from: protected */
    public void notifyResultData(byte[] bArr) {
        notifyResultData(bArr, null);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera2.MiCamera2Shot
    public void onImageReceived(Image image, int i) {
        Camera2Proxy.PictureCallback pictureCallback = getPictureCallback();
        if (pictureCallback == null) {
            image.close();
        } else if (!pictureCallback.onPictureTakenImageConsumed(image)) {
            byte[] firstPlane = Util.getFirstPlane(image);
            image.close();
            notifyResultData(firstPlane, this.mCaptureResult);
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera2.MiCamera2Shot
    public void prepare() {
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera2.MiCamera2Shot
    public void startSessionCapture() {
        Log.d(TAG, "startSessionCapture");
        ((MiCamera2Shot) this).mMiCamera.pausePreview();
        try {
            this.mLatestSequenceId = ((MiCamera2Shot) this).mMiCamera.getCaptureSession().setRepeatingRequest(generateRequestBuilder().build(), generateCaptureCallback(), ((MiCamera2Shot) this).mCameraHandler);
            String str = TAG;
            Log.d(str, "repeating sequenceId: " + this.mLatestSequenceId);
        } catch (CameraAccessException e2) {
            e2.printStackTrace();
            ((MiCamera2Shot) this).mMiCamera.notifyOnError(e2.getReason());
        } catch (IllegalStateException e3) {
            Log.e(TAG, "Failed to capture burst, IllegalState", e3);
            ((MiCamera2Shot) this).mMiCamera.notifyOnError(256);
        }
    }
}
