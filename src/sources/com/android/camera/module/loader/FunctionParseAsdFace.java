package com.android.camera.module.loader;

import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.params.Face;
import com.android.camera.effect.FaceAnalyzeInfo;
import com.android.camera2.Camera2Proxy;
import com.android.camera2.CameraHardwareFace;
import com.android.camera2.vendortag.CaptureResultVendorTags;
import com.android.camera2.vendortag.VendorTagHelper;
import io.reactivex.functions.Function;
import java.lang.ref.WeakReference;

public class FunctionParseAsdFace implements Function<CaptureResult, CaptureResult> {
    private WeakReference<Camera2Proxy.FaceDetectionCallback> mFaceCallbackReference;
    private FaceAnalyzeInfo mFaceInfo;
    private boolean mNeedFaceInfo;

    public FunctionParseAsdFace(Camera2Proxy.FaceDetectionCallback faceDetectionCallback, boolean z) {
        this.mFaceCallbackReference = new WeakReference<>(faceDetectionCallback);
        this.mNeedFaceInfo = z;
    }

    public CaptureResult apply(CaptureResult captureResult) throws Exception {
        Face[] faceArr;
        CameraHardwareFace[] cameraHardwareFaceArr;
        Camera2Proxy.FaceDetectionCallback faceDetectionCallback = this.mFaceCallbackReference.get();
        if (faceDetectionCallback == null || !faceDetectionCallback.isFaceDetectStarted() || (faceArr = (Face[]) captureResult.get(CaptureResult.STATISTICS_FACES)) == null) {
            return captureResult;
        }
        boolean isUseFaceInfo = faceDetectionCallback.isUseFaceInfo();
        if (this.mNeedFaceInfo && isUseFaceInfo) {
            if (this.mFaceInfo == null) {
                this.mFaceInfo = new FaceAnalyzeInfo();
            }
            this.mFaceInfo.mAge = (float[]) VendorTagHelper.getValueQuietly(captureResult, CaptureResultVendorTags.STATISTICS_FACE_AGE);
            this.mFaceInfo.mGender = (float[]) VendorTagHelper.getValueQuietly(captureResult, CaptureResultVendorTags.STATISTICS_FACE_GENDER);
            this.mFaceInfo.mFaceScore = (float[]) VendorTagHelper.getValueQuietly(captureResult, CaptureResultVendorTags.STATISTICS_FACE_FACESCORE);
            this.mFaceInfo.mProp = (float[]) VendorTagHelper.getValueQuietly(captureResult, CaptureResultVendorTags.STATISTICS_FACE_PROP);
        }
        if (this.mNeedFaceInfo && isUseFaceInfo && faceArr.length > 0) {
            FaceAnalyzeInfo faceAnalyzeInfo = this.mFaceInfo;
            if (faceAnalyzeInfo.mAge != null) {
                cameraHardwareFaceArr = CameraHardwareFace.convertExCameraHardwareFace(faceArr, faceAnalyzeInfo);
                faceDetectionCallback.onFaceDetected(cameraHardwareFaceArr, this.mFaceInfo);
                return captureResult;
            }
        }
        cameraHardwareFaceArr = CameraHardwareFace.convertCameraHardwareFace(faceArr);
        faceDetectionCallback.onFaceDetected(cameraHardwareFaceArr, this.mFaceInfo);
        return captureResult;
    }
}
