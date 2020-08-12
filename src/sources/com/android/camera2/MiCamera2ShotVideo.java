package com.android.camera2;

import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.TotalCaptureResult;
import android.media.Image;
import android.media.ImageReader;
import android.support.annotation.NonNull;
import com.android.camera.Util;
import com.android.camera.log.Log;
import com.android.camera2.Camera2Proxy;

public class MiCamera2ShotVideo extends MiCamera2Shot<byte[]> {
    /* access modifiers changed from: private */
    public static final String TAG = "MiCamera2ShotVideo";

    public MiCamera2ShotVideo(MiCamera2 miCamera2) {
        super(miCamera2);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera2.MiCamera2Shot
    public CameraCaptureSession.CaptureCallback generateCaptureCallback() {
        return new CameraCaptureSession.CaptureCallback() {
            /* class com.android.camera2.MiCamera2ShotVideo.AnonymousClass1 */

            public void onCaptureCompleted(@NonNull CameraCaptureSession cameraCaptureSession, @NonNull CaptureRequest captureRequest, @NonNull TotalCaptureResult totalCaptureResult) {
                String access$000 = MiCamera2ShotVideo.TAG;
                Log.d(access$000, "onCaptureCompleted: " + totalCaptureResult.getFrameNumber());
            }
        };
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera2.MiCamera2Shot
    public CaptureRequest.Builder generateRequestBuilder() throws CameraAccessException, IllegalStateException {
        CaptureRequest.Builder createCaptureRequest = 2 == ((MiCamera2Shot) this).mMiCamera.getCapabilities().getSupportedHardwareLevel() ? ((MiCamera2Shot) this).mMiCamera.getCameraDevice().createCaptureRequest(2) : ((MiCamera2Shot) this).mMiCamera.getCameraDevice().createCaptureRequest(4);
        ImageReader videoSnapshotImageReader = ((MiCamera2Shot) this).mMiCamera.getVideoSnapshotImageReader();
        createCaptureRequest.addTarget(videoSnapshotImageReader.getSurface());
        String str = TAG;
        Log.d(str, "size=" + videoSnapshotImageReader.getWidth() + "x" + videoSnapshotImageReader.getHeight());
        if (((MiCamera2Shot) this).mMiCamera.getPreviewSurface() != null) {
            createCaptureRequest.addTarget(((MiCamera2Shot) this).mMiCamera.getPreviewSurface());
        }
        if (((MiCamera2Shot) this).mMiCamera.getRecordSurface() != null) {
            createCaptureRequest.addTarget(((MiCamera2Shot) this).mMiCamera.getRecordSurface());
        }
        ((MiCamera2Shot) this).mMiCamera.applySettingsForVideoShot(createCaptureRequest, 3);
        return createCaptureRequest;
    }

    /* access modifiers changed from: protected */
    public void notifyResultData(byte[] bArr) {
        Camera2Proxy.PictureCallback pictureCallback = getPictureCallback();
        if (pictureCallback != null) {
            pictureCallback.onPictureTaken(bArr, null);
        } else {
            Log.w(TAG, "notifyResultData: null picture callback");
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera2.MiCamera2Shot
    public void onImageReceived(Image image, int i) {
        byte[] firstPlane = Util.getFirstPlane(image);
        image.close();
        notifyResultData(firstPlane);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera2.MiCamera2Shot
    public void prepare() {
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera2.MiCamera2Shot
    public void startSessionCapture() {
        try {
            CaptureRequest.Builder generateRequestBuilder = generateRequestBuilder();
            ((MiCamera2Shot) this).mMiCamera.getCaptureSession().capture(generateRequestBuilder.build(), generateCaptureCallback(), ((MiCamera2Shot) this).mCameraHandler);
        } catch (CameraAccessException e2) {
            e2.printStackTrace();
            Log.e(TAG, "Cannot capture a video snapshot");
            ((MiCamera2Shot) this).mMiCamera.notifyOnError(e2.getReason());
        } catch (IllegalStateException e3) {
            Log.e(TAG, "Failed to capture a video snapshot, IllegalState", e3);
            ((MiCamera2Shot) this).mMiCamera.notifyOnError(256);
        }
    }
}
