package com.android.camera.module.loader;

import android.graphics.Rect;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.params.Face;
import com.android.camera.CameraSettings;
import com.android.camera.data.DataRepository;
import com.android.camera.log.Log;
import com.android.camera2.CameraCapabilities;
import com.android.camera2.CaptureResultParser;
import io.reactivex.functions.Function;

public class FunctionParseAiScene implements Function<CaptureResult, Integer> {
    private static final String TAG = "FunctionParseAiScene";
    private CameraCapabilities mCameraCapabilities;
    private int mCurrentFaceScene;
    private int mLatestFaceScene;
    private int mModuleIndex;
    private int mParsedAiScene;
    private int mSameFaceSceneDetectedTimes;
    private final boolean mSupportMoonMode = DataRepository.dataItemFeature().c_0x54();

    public FunctionParseAiScene(int i, CameraCapabilities cameraCapabilities) {
        this.mModuleIndex = i;
        this.mCameraCapabilities = cameraCapabilities;
    }

    private boolean faceSceneFiltering(int i) {
        int i2;
        int i3 = this.mLatestFaceScene;
        if (i3 != i) {
            this.mLatestFaceScene = i;
            this.mSameFaceSceneDetectedTimes = 0;
        } else {
            int i4 = this.mSameFaceSceneDetectedTimes;
            if (i4 < 20) {
                this.mSameFaceSceneDetectedTimes = i4 + 1;
                if (20 == this.mSameFaceSceneDetectedTimes && (i2 = this.mCurrentFaceScene) != i3) {
                    this.mLatestFaceScene = i2;
                    this.mCurrentFaceScene = this.mLatestFaceScene;
                    return true;
                }
            }
        }
        return false;
    }

    public Integer apply(CaptureResult captureResult) {
        int i;
        int i2;
        CameraCapabilities cameraCapabilities;
        Rect rect = (Rect) captureResult.get(CaptureResult.SCALER_CROP_REGION);
        Rect activeArraySize = this.mCameraCapabilities.getActiveArraySize();
        if (rect == null || activeArraySize == null) {
            return Integer.valueOf(this.mParsedAiScene);
        }
        float width = ((float) activeArraySize.width()) / ((float) rect.width());
        Face[] faceArr = (Face[]) captureResult.get(CaptureResult.STATISTICS_FACES);
        if (this.mModuleIndex == 171 || CameraSettings.isFrontCamera() || faceArr == null || faceArr.length <= 0) {
            i = Integer.MIN_VALUE;
        } else {
            i = Integer.MIN_VALUE;
            for (Face face : faceArr) {
                if (((float) face.getBounds().width()) > 300.0f / width) {
                    int hdrDetectedScene = CaptureResultParser.getHdrDetectedScene(captureResult);
                    Log.c(TAG, "parseAiSceneResult: AI_SCENE_MODE_HUMAN  face.length = " + faceArr.length + ";face.width = " + face.getBounds().width() + ";hdrMode = " + hdrDetectedScene);
                    i = (hdrDetectedScene != 1 || (cameraCapabilities = this.mCameraCapabilities) == null || cameraCapabilities.getMiAlgoASDVersion() >= 2.0f) ? 25 : -1;
                }
            }
        }
        if (faceSceneFiltering(i)) {
            int asdDetectedModes = CaptureResultParser.getAsdDetectedModes(captureResult);
            if (i == Integer.MIN_VALUE || asdDetectedModes == 38) {
                if (!this.mSupportMoonMode && asdDetectedModes == 35) {
                    Log.w(TAG, "detected moon mode on unsupported device, set scene negative");
                    asdDetectedModes = 0;
                }
                if (asdDetectedModes < 0) {
                    Log.e(TAG, "parseAiSceneResult: parse a error result: " + asdDetectedModes);
                    this.mParsedAiScene = 0;
                } else {
                    this.mParsedAiScene = asdDetectedModes;
                }
            } else {
                this.mParsedAiScene = i;
            }
        }
        if (CameraSettings.isDocumentModeOn(this.mModuleIndex) && ((i2 = this.mParsedAiScene) == -1 || i2 == 10 || i2 == 35)) {
            this.mParsedAiScene = 0;
        }
        return Integer.valueOf(this.mParsedAiScene);
    }

    public void resetScene() {
        this.mLatestFaceScene = 0;
        this.mParsedAiScene = 0;
    }
}
