package com.android.camera.module.loader.camera2;

import android.annotation.TargetApi;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.TotalCaptureResult;
import android.support.annotation.NonNull;

@TargetApi(21)
public abstract class PictureCaptureCallback extends CameraCaptureSession.CaptureCallback {
    static final int STATE_CAPTURING = 5;
    static final int STATE_LOCKED = 2;
    static final int STATE_LOCKING = 1;
    static final int STATE_PRECAPTURE = 3;
    static final int STATE_PREVIEW = 0;
    static final int STATE_WAITING = 4;
    private int mState;

    PictureCaptureCallback() {
    }

    private void process(@NonNull CaptureResult captureResult) {
        int i = this.mState;
        if (i == 1) {
            Integer num = (Integer) captureResult.get(CaptureResult.CONTROL_AF_STATE);
            if (num != null) {
                if (num.intValue() == 4 || num.intValue() == 5) {
                    Integer num2 = (Integer) captureResult.get(CaptureResult.CONTROL_AE_STATE);
                    if (num2 == null || num2.intValue() == 2) {
                        setState(5);
                        onReady();
                        return;
                    }
                    setState(2);
                    onPrecaptureRequired();
                }
            }
        } else if (i == 3) {
            Integer num3 = (Integer) captureResult.get(CaptureResult.CONTROL_AE_STATE);
            if (num3 == null || num3.intValue() == 5 || num3.intValue() == 4 || num3.intValue() == 2) {
                setState(4);
            }
        } else if (i == 4) {
            Integer num4 = (Integer) captureResult.get(CaptureResult.CONTROL_AE_STATE);
            if (num4 == null || num4.intValue() != 5) {
                setState(5);
                onReady();
            }
        }
    }

    public void onCaptureCompleted(@NonNull CameraCaptureSession cameraCaptureSession, @NonNull CaptureRequest captureRequest, @NonNull TotalCaptureResult totalCaptureResult) {
        process(totalCaptureResult);
    }

    public void onCaptureProgressed(@NonNull CameraCaptureSession cameraCaptureSession, @NonNull CaptureRequest captureRequest, @NonNull CaptureResult captureResult) {
        process(captureResult);
    }

    public abstract void onPrecaptureRequired();

    public abstract void onReady();

    /* access modifiers changed from: package-private */
    public void setState(int i) {
        this.mState = i;
    }
}
