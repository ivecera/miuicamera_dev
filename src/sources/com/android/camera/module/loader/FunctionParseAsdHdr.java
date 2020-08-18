package com.android.camera.module.loader;

import android.hardware.camera2.CaptureResult;
import com.android.camera2.Camera2Proxy;
import com.android.camera2.CaptureResultParser;
import io.reactivex.functions.Function;
import java.lang.ref.WeakReference;

public class FunctionParseAsdHdr implements Function<CaptureResult, CaptureResult> {
    private WeakReference<Camera2Proxy.HdrCheckerCallback> mHdrCheckerCallback;

    public FunctionParseAsdHdr(Camera2Proxy.HdrCheckerCallback hdrCheckerCallback) {
        this.mHdrCheckerCallback = new WeakReference<>(hdrCheckerCallback);
    }

    public CaptureResult apply(CaptureResult captureResult) throws Exception {
        Camera2Proxy.HdrCheckerCallback hdrCheckerCallback = this.mHdrCheckerCallback.get();
        if (hdrCheckerCallback == null) {
            return captureResult;
        }
        boolean isHdrMotionDetected = CaptureResultParser.isHdrMotionDetected(captureResult);
        hdrCheckerCallback.onHdrMotionDetectionResult(isHdrMotionDetected);
        if (!hdrCheckerCallback.isHdrSceneDetectionStarted()) {
            return captureResult;
        }
        boolean z = true;
        if (CaptureResultParser.getHdrDetectedScene(captureResult) != 1 || isHdrMotionDetected) {
            z = false;
        }
        hdrCheckerCallback.onHdrSceneChanged(z);
        return captureResult;
    }
}
