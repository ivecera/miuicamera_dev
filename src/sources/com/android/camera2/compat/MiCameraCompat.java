package com.android.camera2.compat;

import android.graphics.Rect;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.params.StreamConfiguration;
import com.android.camera.fragment.beauty.BeautyValues;
import com.android.camera2.vendortag.VendorTag;
import com.android.camera2.vendortag.struct.MarshalQueryableASDScene;
import com.mi.config.b;
import java.util.HashSet;

public class MiCameraCompat {
    private static final MiCameraCompatBaseImpl IMPL;

    static {
        if (b.wl()) {
            IMPL = new MiCameraCompatQcomImpl();
        } else if (b.isMTKPlatform()) {
            IMPL = new MiCameraCompatMtkImpl();
        } else {
            IMPL = new MiCameraCompatBaseImpl();
        }
    }

    public static void applyASDEnable(CaptureRequest.Builder builder, boolean z) {
        IMPL.applyASDEnable(builder, z);
    }

    public static void applyASDScene(CaptureRequest.Builder builder, int i) {
        IMPL.applyASDScene(builder, i);
    }

    public static void applyAiASDEnable(CaptureRequest.Builder builder, boolean z) {
        IMPL.applyAiASDEnable(builder, z);
    }

    public static void applyAiScenePeriod(CaptureRequest.Builder builder, int i) {
        IMPL.applyAiScenePeriod(builder, i);
    }

    public static void applyAlgoUpEnabled(CaptureRequest.Builder builder, boolean z) {
        IMPL.applyAlgoUpEnabled(builder, z);
    }

    public static void applyAsdDirtyEnable(CaptureRequest.Builder builder, boolean z) {
        IMPL.applyAsdDirtyEnable(builder, z);
    }

    public static void applyAutoZoomMode(CaptureRequest.Builder builder, int i) {
        IMPL.applyAutoZoomMode(builder, i);
    }

    public static void applyAutoZoomScaleOffset(CaptureRequest.Builder builder, float f2) {
        IMPL.applyAutoZoomScaleOffset(builder, f2);
    }

    public static void applyBackSoftLight(CaptureRequest.Builder builder, byte b2) {
        IMPL.applyBackSoftLight(builder, b2);
    }

    public static void applyBackwardCaptureHint(CaptureRequest.Builder builder, byte b2) {
        IMPL.applyBackwardCaptureHint(builder, b2);
    }

    public static void applyBeautyParameter(CaptureRequest.Builder builder, HashSet<String> hashSet, BeautyValues beautyValues) {
        IMPL.applyBeautyParameter(builder, hashSet, beautyValues);
    }

    public static void applyBurstFps(CaptureRequest.Builder builder, int i) {
        IMPL.applyBurstFps(builder, i);
    }

    public static void applyBurstHint(CaptureRequest.Builder builder, int i) {
        IMPL.applyBurstHint(builder, i);
    }

    public static void applyCameraAi30Enable(CaptureRequest.Builder builder, boolean z) {
        IMPL.applyCameraAi30Enable(builder, z);
    }

    public static void applyCinematicPhoto(CaptureRequest.Builder builder, byte b2) {
        IMPL.applyCinematicPhoto(builder, b2);
    }

    public static void applyCinematicVideo(CaptureRequest.Builder builder, byte b2) {
        IMPL.applyCinematicVideo(builder, b2);
    }

    public static void applyColorEnhanceEnable(CaptureRequest.Builder builder, boolean z) {
        IMPL.applyColorEnhanceEnable(builder, z);
    }

    public static void applyContrast(CaptureRequest.Builder builder, int i) {
        IMPL.applyContrast(builder, i);
    }

    public static void applyCustomAWB(CaptureRequest.Builder builder, int i) {
        IMPL.applyCustomWB(builder, i);
    }

    public static void applyCustomWaterMark(CaptureRequest.Builder builder, String str) {
        IMPL.applyCustomWaterMark(builder, str);
    }

    public static void applyDepurpleEnable(CaptureRequest.Builder builder, boolean z) {
        IMPL.applyDepurpleEnable(builder, z);
    }

    public static void applyDeviceOrientation(CaptureRequest.Builder builder, int i) {
        IMPL.applyDeviceOrientation(builder, i);
    }

    public static void applyExposureMeteringMode(CaptureRequest.Builder builder, int i) {
        IMPL.applyExposureMeteringMode(builder, i);
    }

    public static void applyExposureTime(CaptureRequest.Builder builder, long j) {
        IMPL.applyExposureTime(builder, j);
    }

    public static void applyEyeLight(CaptureRequest.Builder builder, int i, int i2) {
        IMPL.applyEyeLight(builder, i, i2);
    }

    public static void applyFNumber(CaptureRequest.Builder builder, String str) {
        IMPL.applyFNumber(builder, str);
    }

    public static void applyFaceAgeAnalyzeEnable(CaptureRequest.Builder builder, boolean z) {
        IMPL.applyFaceAnalyzeAge(builder, z);
    }

    public static void applyFaceDetection(CaptureRequest.Builder builder, boolean z) {
        IMPL.applyFaceDetection(builder, z);
    }

    public static void applyFaceScoreEnable(CaptureRequest.Builder builder, boolean z) {
        IMPL.applyFaceScore(builder, z);
    }

    public static void applyFaceWaterMark(CaptureRequest.Builder builder, String str) {
        IMPL.applyFaceWaterMark(builder, str);
    }

    public static void applyFlashCurrent(CaptureRequest.Builder builder, int i) {
        IMPL.applyFlashCurrent(builder, i);
    }

    public static void applyFlashMode(CaptureRequest.Builder builder, int i) {
        IMPL.applyFlashMode(builder, i);
    }

    public static void applyFlawDetectEnable(CaptureRequest.Builder builder, boolean z) {
        IMPL.applyFlawDetectEnable(builder, z);
    }

    public static void applyFrontMirror(CaptureRequest.Builder builder, boolean z) {
        IMPL.applyFrontMirror(builder, z);
    }

    public static void applyHDR(CaptureRequest.Builder builder, boolean z) {
        IMPL.applyHDR(builder, z);
    }

    public static void applyHDRCheckerEnable(CaptureRequest.Builder builder, boolean z) {
        IMPL.applyHDRCheckerEnable(builder, z);
    }

    public static void applyHDRCheckerStatus(CaptureRequest.Builder builder, int i) {
        IMPL.applyHDRCheckerStatus(builder, i);
    }

    public static void applyHDRMode(CaptureRequest.Builder builder, int i) {
        IMPL.applyHDRMode(builder, i);
    }

    public static void applyHFRDeflicker(CaptureRequest.Builder builder, boolean z) {
        IMPL.applyHFRDeflicker(builder, z);
    }

    public static void applyHHT(CaptureRequest.Builder builder, boolean z) {
        IMPL.applyHHT(builder, z);
    }

    public static void applyHdrBokeh(CaptureRequest.Builder builder, boolean z) {
        IMPL.applyHdrBokeh(builder, z);
    }

    public static void applyHdrBracketMode(CaptureRequest.Builder builder, byte b2) {
        IMPL.applyHdrBracketMode(builder, b2);
    }

    public static void applyHdrParameter(CaptureRequest.Builder builder, Integer num, Integer num2) {
        IMPL.applyHdrParameter(builder, num, num2);
    }

    public static void applyHighFpsVideoRecordingMode(CaptureRequest.Builder builder, boolean z) {
        IMPL.applyHighFpsVideoRecordingMode(builder, z);
    }

    public static void applyHistogramStats(CaptureRequest.Builder builder, byte b2) {
        IMPL.applyHistogramStats(builder, b2);
    }

    public static void applyISO(CaptureRequest.Builder builder, int i) {
        IMPL.applyISO(builder, i);
    }

    public static void applyIsHfrPreview(CaptureRequest.Builder builder, boolean z) {
        IMPL.applyIsHfrPreview(builder, z);
    }

    public static void applyLensDirtyDetect(CaptureRequest.Builder builder, boolean z) {
        IMPL.applyLensDirtyDetect(builder, z);
    }

    public static void applyMacroMode(CaptureRequest.Builder builder, boolean z) {
        IMPL.applyMacroMode(builder, z);
    }

    public static void applyMfnrEnable(CaptureRequest.Builder builder, boolean z) {
        IMPL.applyMfnr(builder, z);
    }

    public static void applyMiBokehEnable(CaptureRequest.Builder builder, boolean z) {
        IMPL.applyMiBokeh(builder, z);
    }

    public static void applyMultiFrameInputNum(CaptureRequest.Builder builder, int i) {
        IMPL.applyMultiFrameInputNum(builder, i);
    }

    public static void applyNormalWideLDC(CaptureRequest.Builder builder, boolean z) {
        IMPL.applyNormalWideLDC(builder, z);
    }

    public static void applyOnTripodModeStatus(CaptureRequest.Builder builder, MarshalQueryableASDScene.ASDScene[] aSDSceneArr) {
        IMPL.applyOnTripodModeStatus(builder, aSDSceneArr);
    }

    public static void applyParallelProcessEnable(CaptureRequest.Builder builder, boolean z) {
        IMPL.applyParallelProcessEnable(builder, z);
    }

    public static void applyParallelProcessPath(CaptureRequest.Builder builder, String str) {
        IMPL.applyParallelProcessPath(builder, str);
    }

    public static void applyPortraitLighting(CaptureRequest.Builder builder, int i) {
        IMPL.applyPortraitLighting(builder, i);
    }

    public static void applyPostProcessCropRegion(CaptureRequest.Builder builder, Rect rect) {
        IMPL.applyPostProcessCropRegion(builder, rect);
    }

    public static void applyPqFeature(CaptureRequest.Builder builder, boolean z) {
        IMPL.applyPqFeature(builder, z);
    }

    public static void applyQuickPreview(CaptureRequest.Builder builder, boolean z) {
        IMPL.applyQuickPreview(builder, z);
    }

    public static void applyRawReprocessHint(CaptureRequest.Builder builder, boolean z) {
        IMPL.applyRawReprocessHint(builder, z);
    }

    public static void applyRearBokehEnable(CaptureRequest.Builder builder, boolean z) {
        IMPL.applyRearBokeh(builder, z);
    }

    public static void applyRemosaicEnabled(CaptureRequest.Builder builder, boolean z) {
        IMPL.applyRemosaicEnabled(builder, z);
    }

    public static void applyRemosaicHint(CaptureRequest.Builder builder, boolean z) {
        IMPL.applyRemosaicHint(builder, z);
    }

    public static void applySatFallback(CaptureRequest.Builder builder, boolean z) {
        IMPL.applySatFallback(builder, z);
    }

    public static void applySatIsZooming(CaptureRequest.Builder builder, boolean z) {
        IMPL.applySatIsZooming(builder, z);
    }

    public static void applySaturation(CaptureRequest.Builder builder, int i) {
        IMPL.applySaturation(builder, i);
    }

    public static void applyScreenLightHint(CaptureRequest.Builder builder, byte b2) {
        IMPL.applyScreenLightHint(builder, b2);
    }

    public static void applySharpness(CaptureRequest.Builder builder, int i) {
        IMPL.applySharpness(builder, i);
    }

    public static void applyShrinkMemoryMode(CaptureRequest.Builder builder, int i) {
        IMPL.applyShrinkMemoryMode(builder, i);
    }

    public static void applySlowMotionVideoRecordingMode(CaptureRequest.Builder builder, int[] iArr) {
        IMPL.applySlowMotionVideoRecordingMode(builder, iArr);
    }

    public static void applySmoothTransition(CaptureRequest.Builder builder, boolean z) {
        IMPL.applySmoothTransition(builder, z);
    }

    public static void applySnapshotTorch(CaptureRequest.Builder builder, boolean z) {
        IMPL.applySnapshotTorch(builder, z);
    }

    public static void applyStFastZoomIn(CaptureRequest.Builder builder, boolean z) {
        IMPL.applyStFastZoomIn(builder, z);
    }

    public static void applySuperNightScene(CaptureRequest.Builder builder, boolean z) {
        IMPL.applySuperNightScene(builder, z);
    }

    public static void applySuperResolution(CaptureRequest.Builder builder, boolean z) {
        IMPL.applySuperResolution(builder, z);
    }

    public static void applySwMfnrEnable(CaptureRequest.Builder builder, boolean z) {
        IMPL.applySwMfnr(builder, z);
    }

    public static void applyThermalLevel(CaptureRequest.Builder builder, Integer num) {
        IMPL.applyThermalLevel(builder, num.intValue());
    }

    public static void applyTimeWaterMark(CaptureRequest.Builder builder, String str) {
        IMPL.applyTimeWaterMark(builder, str);
    }

    public static void applyUltraPixelPortrait(CaptureRequest.Builder builder, boolean z) {
        IMPL.applyUltraPixelPortrait(builder, z);
    }

    public static void applyUltraWideLDC(CaptureRequest.Builder builder, boolean z) {
        IMPL.applyUltraWideLDC(builder, z);
    }

    public static void applyVideoBokehBackLevel(CaptureRequest.Builder builder, int i) {
        IMPL.applyVideoBokehBackLevel(builder, i);
    }

    public static void applyVideoBokehFrontLevel(CaptureRequest.Builder builder, float f2) {
        IMPL.applyVideoBokehFrontLevel(builder, f2);
    }

    public static void applyVideoFilterColorRetentionBack(CaptureRequest.Builder builder, boolean z) {
        IMPL.applyVideoFilterColorRetentionBack(builder, z);
    }

    public static void applyVideoFilterColorRetentionFront(CaptureRequest.Builder builder, boolean z) {
        IMPL.applyVideoFilterColorRetentionFront(builder, z);
    }

    public static void applyVideoFilterId(CaptureRequest.Builder builder, int i) {
        IMPL.applyVideoFilterId(builder, i);
    }

    public static void applyVideoLogEnable(CaptureRequest.Builder builder, byte b2) {
        IMPL.applyVideoLogEnable(builder, b2);
    }

    public static void applyVideoStreamState(CaptureRequest.Builder builder, boolean z) {
        IMPL.applyVideoStreamState(builder, z);
    }

    public static void applyWaterMarkAppliedList(CaptureRequest.Builder builder, String str) {
        IMPL.applyWaterMarkAppliedList(builder, str);
    }

    public static void applyZsd(CaptureRequest.Builder builder, boolean z) {
        IMPL.applyZsd(builder, z);
    }

    public static void applyZsl(CaptureRequest.Builder builder, boolean z) {
        IMPL.applyZsl(builder, z);
    }

    public static void copyAiSceneFromCaptureResultToRequest(CaptureResult captureResult, CaptureRequest.Builder builder) {
        IMPL.copyAiSceneFromCaptureResultToRequest(captureResult, builder);
    }

    public static void copyFpcDataFromCaptureResultToRequest(CaptureResult captureResult, CaptureRequest.Builder builder) {
        IMPL.copyFpcDataFromCaptureResultToRequest(captureResult, builder);
    }

    public static VendorTag<CameraCharacteristics.Key<StreamConfiguration[]>> getDefaultSteamConfigurationsTag() {
        return IMPL.getDefaultSteamConfigurationsTag();
    }
}
