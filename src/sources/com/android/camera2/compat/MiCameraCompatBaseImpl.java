package com.android.camera2.compat;

import android.annotation.TargetApi;
import android.graphics.Rect;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.params.StreamConfiguration;
import com.android.camera.constant.BeautyConstant;
import com.android.camera.fragment.beauty.BeautyValues;
import com.android.camera.lib.compatibility.util.CompatibilityUtils;
import com.android.camera.log.Log;
import com.android.camera2.vendortag.CaptureRequestVendorTags;
import com.android.camera2.vendortag.VendorTag;
import com.android.camera2.vendortag.VendorTagHelper;
import com.android.camera2.vendortag.struct.MarshalQueryableASDScene;
import com.mi.config.b;
import java.util.HashSet;
import java.util.Map;

@TargetApi(21)
public class MiCameraCompatBaseImpl {
    protected static final String TAG = "MiCameraCompat";

    public void applyASDEnable(CaptureRequest.Builder builder, boolean z) {
        VendorTagHelper.setValueQuietly(builder, CaptureRequestVendorTags.ASD_ENABLE, Boolean.valueOf(z));
    }

    public void applyASDScene(CaptureRequest.Builder builder, int i) {
        VendorTagHelper.setValue(builder, CaptureRequestVendorTags.AI_SCENE_APPLY, Integer.valueOf(i));
    }

    public void applyAiASDEnable(CaptureRequest.Builder builder, boolean z) {
        VendorTagHelper.setValue(builder, CaptureRequestVendorTags.AI_SCENE, Boolean.valueOf(z));
    }

    public void applyAiScenePeriod(CaptureRequest.Builder builder, int i) {
        VendorTagHelper.setValue(builder, CaptureRequestVendorTags.AI_SCENE_PERIOD, Integer.valueOf(i));
    }

    public void applyAlgoUpEnabled(CaptureRequest.Builder builder, boolean z) {
        VendorTagHelper.setValue(builder, CaptureRequestVendorTags.ALGO_UP_ENABLED, Boolean.valueOf(z));
    }

    public void applyAsdDirtyEnable(CaptureRequest.Builder builder, boolean z) {
        VendorTagHelper.setValueQuietly(builder, CaptureRequestVendorTags.ASD_DIRTY_ENABLE, Byte.valueOf(z ? (byte) 1 : 0));
    }

    public void applyAutoZoomMode(CaptureRequest.Builder builder, int i) {
        VendorTagHelper.setValue(builder, CaptureRequestVendorTags.AUTOZOOM_MODE, Integer.valueOf(i));
    }

    public void applyAutoZoomScaleOffset(CaptureRequest.Builder builder, float f2) {
        VendorTagHelper.setValue(builder, CaptureRequestVendorTags.AUTOZOOM_SCALE_OFFSET, Float.valueOf(f2));
    }

    public void applyBackSoftLight(CaptureRequest.Builder builder, byte b2) {
        VendorTagHelper.setValue(builder, CaptureRequestVendorTags.BACK_SOFT_LIGHT, Byte.valueOf(b2));
    }

    public void applyBackwardCaptureHint(CaptureRequest.Builder builder, byte b2) {
        VendorTagHelper.setValue(builder, CaptureRequestVendorTags.BACKWARD_CAPTURE_HINT, Byte.valueOf(b2));
    }

    public void applyBeautyParameter(CaptureRequest.Builder builder, HashSet<String> hashSet, BeautyValues beautyValues) {
        VendorTagHelper.setValue(builder, CaptureRequestVendorTags.BEAUTY_LEVEL, beautyValues.mBeautyLevel);
        if (b.Il()) {
            for (Map.Entry<String, VendorTag<CaptureRequest.Key<Integer>>> entry : BeautyConstant.BEAUTY_TYPE_VENDOR_TAG_MAP.entrySet()) {
                VendorTag<CaptureRequest.Key<Integer>> value = entry.getValue();
                if (hashSet.contains(value.getName())) {
                    VendorTagHelper.setValue(builder, value, Integer.valueOf(beautyValues.getValueByType(entry.getKey())));
                }
            }
            return;
        }
        VendorTagHelper.setValue(builder, CaptureRequestVendorTags.BEAUTY_SKIN_COLOR, Integer.valueOf(beautyValues.mBeautySkinColor));
        VendorTagHelper.setValue(builder, CaptureRequestVendorTags.BEAUTY_SLIM_FACE, Integer.valueOf(beautyValues.mBeautySlimFace));
        VendorTagHelper.setValue(builder, CaptureRequestVendorTags.BEAUTY_SKIN_SMOOTH, Integer.valueOf(beautyValues.mBeautySkinSmooth));
        VendorTagHelper.setValue(builder, CaptureRequestVendorTags.BEAUTY_ENLARGE_EYE, Integer.valueOf(beautyValues.mBeautyEnlargeEye));
    }

    public void applyBurstFps(CaptureRequest.Builder builder, int i) {
        VendorTagHelper.setValue(builder, CaptureRequestVendorTags.BURST_SHOOT_FPS, Integer.valueOf(i));
    }

    public void applyBurstHint(CaptureRequest.Builder builder, int i) {
        VendorTagHelper.setValue(builder, CaptureRequestVendorTags.BURST_CAPTURE_HINT, Integer.valueOf(i));
    }

    public void applyCameraAi30Enable(CaptureRequest.Builder builder, boolean z) {
        VendorTagHelper.setValue(builder, CaptureRequestVendorTags.CAMERA_AI_30, Byte.valueOf(z ? (byte) 1 : 0));
    }

    public void applyCinematicPhoto(CaptureRequest.Builder builder, byte b2) {
        VendorTagHelper.setValue(builder, CaptureRequestVendorTags.CINEMATIC_PHOTO_ENABLED, Byte.valueOf(b2));
    }

    public void applyCinematicVideo(CaptureRequest.Builder builder, byte b2) {
        VendorTagHelper.setValueQuietly(builder, CaptureRequestVendorTags.CINEMATIC_VIDEO_ENABLED, Byte.valueOf(b2));
    }

    public void applyColorEnhanceEnable(CaptureRequest.Builder builder, boolean z) {
        VendorTagHelper.setValueQuietly(builder, CaptureRequestVendorTags.COLOR_ENHANCE_ENABLED, Byte.valueOf(z ? (byte) 1 : 0));
    }

    public void applyContrast(CaptureRequest.Builder builder, int i) {
    }

    public void applyCustomWB(CaptureRequest.Builder builder, int i) {
    }

    public void applyCustomWaterMark(CaptureRequest.Builder builder, String str) {
        VendorTagHelper.setValue(builder, CaptureRequestVendorTags.CUSTOM_WATERMARK_TEXT, str);
    }

    public void applyDepurpleEnable(CaptureRequest.Builder builder, boolean z) {
        VendorTagHelper.setValue(builder, CaptureRequestVendorTags.DEPURPLE, Byte.valueOf(z ? (byte) 1 : 0));
    }

    public void applyDeviceOrientation(CaptureRequest.Builder builder, int i) {
        VendorTagHelper.setValue(builder, CaptureRequestVendorTags.DEVICE_ORIENTATION, Integer.valueOf(i));
    }

    public void applyExposureMeteringMode(CaptureRequest.Builder builder, int i) {
    }

    public void applyExposureTime(CaptureRequest.Builder builder, long j) {
        if (j > 0) {
            builder.set(CaptureRequest.CONTROL_MODE, 0);
            builder.set(CaptureRequest.SENSOR_EXPOSURE_TIME, Long.valueOf(j));
            return;
        }
        CaptureRequest.Key key = CaptureRequest.CONTROL_MODE;
        builder.set(key, (Integer) builder.get(key));
    }

    public void applyEyeLight(CaptureRequest.Builder builder, int i, int i2) {
        VendorTagHelper.setValue(builder, CaptureRequestVendorTags.EYE_LIGHT_TYPE, Integer.valueOf(i));
        VendorTagHelper.setValue(builder, CaptureRequestVendorTags.EYE_LIGHT_STRENGTH, Integer.valueOf(i2));
    }

    public void applyFNumber(CaptureRequest.Builder builder, String str) {
        VendorTagHelper.setValue(builder, CaptureRequestVendorTags.BOKEH_F_NUMBER, str);
    }

    public void applyFaceAnalyzeAge(CaptureRequest.Builder builder, boolean z) {
        VendorTagHelper.setValue(builder, CaptureRequestVendorTags.FACE_AGE_ANALYZE_ENABLED, Boolean.valueOf(z));
    }

    public void applyFaceDetection(CaptureRequest.Builder builder, boolean z) {
        builder.set(CaptureRequest.STATISTICS_FACE_DETECT_MODE, Integer.valueOf(z ? 2 : 0));
    }

    public void applyFaceScore(CaptureRequest.Builder builder, boolean z) {
        VendorTagHelper.setValue(builder, CaptureRequestVendorTags.FACE_SCORE_ENABLED, Boolean.valueOf(z));
    }

    public void applyFaceWaterMark(CaptureRequest.Builder builder, String str) {
        VendorTagHelper.setValue(builder, CaptureRequestVendorTags.WATERMARK_FACE, str);
    }

    public void applyFlashCurrent(CaptureRequest.Builder builder, int i) {
        Log.d(TAG, "applyFlashCurrent: value = " + i);
        VendorTagHelper.setValueQuietly(builder, CaptureRequestVendorTags.FLASH_CURRENT, Integer.valueOf(i));
    }

    public void applyFlashMode(CaptureRequest.Builder builder, int i) {
        Log.d(TAG, "applyFlashMode: mode = " + i);
        VendorTagHelper.setValueQuietly(builder, CaptureRequestVendorTags.FLASH_MODE, Integer.valueOf(i));
    }

    public void applyFlawDetectEnable(CaptureRequest.Builder builder, boolean z) {
        VendorTagHelper.setValueQuietly(builder, CaptureRequestVendorTags.FLAW_DETECT_ENABLE, Boolean.valueOf(z));
    }

    public void applyFrontMirror(CaptureRequest.Builder builder, boolean z) {
        VendorTagHelper.setValue(builder, CaptureRequestVendorTags.FRONT_MIRROR, Boolean.valueOf(z));
    }

    public void applyHDR(CaptureRequest.Builder builder, boolean z) {
        VendorTagHelper.setValue(builder, CaptureRequestVendorTags.HDR_ENABLED, Boolean.valueOf(z));
    }

    public void applyHDRCheckerEnable(CaptureRequest.Builder builder, boolean z) {
        VendorTagHelper.setValue(builder, CaptureRequestVendorTags.HDR_CHECKER_ENABLE, Boolean.valueOf(z));
    }

    public void applyHDRCheckerStatus(CaptureRequest.Builder builder, int i) {
        VendorTagHelper.setValue(builder, CaptureRequestVendorTags.HDR_CHECKER_STATUS, Integer.valueOf(i));
    }

    public void applyHDRMode(CaptureRequest.Builder builder, int i) {
        Log.e(TAG, "applyHDRMode:" + i);
        VendorTagHelper.setValueQuietly(builder, CaptureRequestVendorTags.HDR_MODE, Integer.valueOf(i));
    }

    public void applyHFRDeflicker(CaptureRequest.Builder builder, boolean z) {
        VendorTagHelper.setValue(builder, CaptureRequestVendorTags.DEFLICKER_ENABLED, Boolean.valueOf(z));
    }

    public void applyHHT(CaptureRequest.Builder builder, boolean z) {
        VendorTagHelper.setValue(builder, CaptureRequestVendorTags.HHT_ENABLED, Boolean.valueOf(z));
    }

    public void applyHdrBokeh(CaptureRequest.Builder builder, boolean z) {
        Log.d(TAG, "applyHdrBokeh: enabled = " + z);
        VendorTagHelper.setValue(builder, CaptureRequestVendorTags.HDR_BOKEH_ENABLED, Boolean.valueOf(z));
    }

    public void applyHdrBracketMode(CaptureRequest.Builder builder, byte b2) {
    }

    public void applyHdrParameter(CaptureRequest.Builder builder, Integer num, Integer num2) {
        VendorTagHelper.setValueQuietly(builder, CaptureRequestVendorTags.HDR_CHECKER_SCENETYPE, num);
        VendorTagHelper.setValueQuietly(builder, CaptureRequestVendorTags.HDR_CHECKER_ADRC, num2);
    }

    public void applyHighFpsVideoRecordingMode(CaptureRequest.Builder builder, boolean z) {
    }

    public void applyHistogramStats(CaptureRequest.Builder builder, byte b2) {
        VendorTagHelper.setValueQuietly(builder, CaptureRequestVendorTags.HISTOGRAM_STATS_ENABLED, Byte.valueOf(b2));
    }

    public void applyISO(CaptureRequest.Builder builder, int i) {
        builder.set(CaptureRequest.SENSOR_SENSITIVITY, Integer.valueOf(i));
    }

    public void applyIsHfrPreview(CaptureRequest.Builder builder, boolean z) {
        VendorTagHelper.setValueQuietly(builder, CaptureRequestVendorTags.IS_HFR_PREVIEW, Byte.valueOf(z ? (byte) 1 : 0));
    }

    public void applyLensDirtyDetect(CaptureRequest.Builder builder, boolean z) {
        VendorTagHelper.setValue(builder, CaptureRequestVendorTags.LENS_DIRTY_DETECT, Boolean.valueOf(z));
    }

    public void applyMacroMode(CaptureRequest.Builder builder, boolean z) {
        VendorTagHelper.setValue(builder, CaptureRequestVendorTags.MACRO_MODE, Byte.valueOf(z ? (byte) 1 : 0));
    }

    public void applyMfnr(CaptureRequest.Builder builder, boolean z) {
        VendorTagHelper.setValue(builder, CaptureRequestVendorTags.MFNR_ENABLED, Boolean.valueOf(z));
    }

    public void applyMiBokeh(CaptureRequest.Builder builder, boolean z) {
        VendorTagHelper.setValue(builder, CaptureRequestVendorTags.FRONT_SINGLE_CAMERA_BOKEH, Boolean.valueOf(z));
    }

    public void applyMultiFrameInputNum(CaptureRequest.Builder builder, int i) {
        VendorTagHelper.setValue(builder, CaptureRequestVendorTags.MULTIFRAME_INPUTNUM, Integer.valueOf(i));
    }

    public void applyNormalWideLDC(CaptureRequest.Builder builder, boolean z) {
        Log.d(TAG, "applyNormalWideLDC: " + z);
        VendorTagHelper.setValue(builder, CaptureRequestVendorTags.NORMAL_WIDE_LENS_DISTORTION_CORRECTION_LEVEL, Byte.valueOf(z ? (byte) 1 : 0));
    }

    public void applyOnTripodModeStatus(CaptureRequest.Builder builder, MarshalQueryableASDScene.ASDScene[] aSDSceneArr) {
        VendorTagHelper.setValueQuietly(builder, CaptureRequestVendorTags.ON_TRIPOD_MODE, aSDSceneArr);
    }

    public void applyParallelProcessEnable(CaptureRequest.Builder builder, boolean z) {
        VendorTagHelper.setValue(builder, CaptureRequestVendorTags.PARALLEL_ENABLED, Boolean.valueOf(z));
    }

    public void applyParallelProcessPath(CaptureRequest.Builder builder, String str) {
        VendorTagHelper.setValue(builder, CaptureRequestVendorTags.PARALLEL_PATH, str.getBytes());
    }

    public void applyPortraitLighting(CaptureRequest.Builder builder, int i) {
        VendorTagHelper.setValue(builder, CaptureRequestVendorTags.PORTRAIT_LIGHTING, Integer.valueOf(i));
    }

    public void applyPostProcessCropRegion(CaptureRequest.Builder builder, Rect rect) {
    }

    public void applyPqFeature(CaptureRequest.Builder builder, boolean z) {
    }

    public void applyQuickPreview(CaptureRequest.Builder builder, boolean z) {
    }

    public void applyRawReprocessHint(CaptureRequest.Builder builder, boolean z) {
    }

    public void applyRearBokeh(CaptureRequest.Builder builder, boolean z) {
        VendorTagHelper.setValue(builder, CaptureRequestVendorTags.REAR_BOKEH_ENABLE, Boolean.valueOf(z));
    }

    public void applyRemosaicEnabled(CaptureRequest.Builder builder, boolean z) {
        VendorTagHelper.setValueSafely(builder, CaptureRequestVendorTags.CONTROL_ENABLE_REMOSAIC, Boolean.valueOf(z));
    }

    public void applyRemosaicHint(CaptureRequest.Builder builder, boolean z) {
    }

    public void applySatFallback(CaptureRequest.Builder builder, boolean z) {
    }

    public void applySatIsZooming(CaptureRequest.Builder builder, boolean z) {
        VendorTagHelper.setValueSafely(builder, CaptureRequestVendorTags.SAT_IS_ZOOMING, Boolean.valueOf(z));
    }

    public void applySaturation(CaptureRequest.Builder builder, int i) {
    }

    public void applyScreenLightHint(CaptureRequest.Builder builder, byte b2) {
        VendorTagHelper.setValue(builder, CaptureRequestVendorTags.SCREEN_LIGHT_HINT, Byte.valueOf(b2));
    }

    public void applySharpness(CaptureRequest.Builder builder, int i) {
    }

    public void applyShrinkMemoryMode(CaptureRequest.Builder builder, int i) {
        Log.d(TAG, "applyShrinkMemoryMode: mode = " + i);
        VendorTagHelper.setValueQuietly(builder, CaptureRequestVendorTags.SHRINK_MEMORY_MODE, Integer.valueOf(i));
    }

    public void applySlowMotionVideoRecordingMode(CaptureRequest.Builder builder, int[] iArr) {
    }

    public void applySmoothTransition(CaptureRequest.Builder builder, boolean z) {
    }

    public void applySnapshotTorch(CaptureRequest.Builder builder, boolean z) {
        VendorTagHelper.setValue(builder, CaptureRequestVendorTags.SNAP_SHOT_TORCH, Boolean.valueOf(z));
    }

    public void applyStFastZoomIn(CaptureRequest.Builder builder, boolean z) {
        VendorTagHelper.setValue(builder, CaptureRequestVendorTags.ST_FAST_ZOOM_IN, Boolean.valueOf(z));
    }

    public void applySuperNightScene(CaptureRequest.Builder builder, boolean z) {
        VendorTagHelper.setValue(builder, CaptureRequestVendorTags.SUPER_NIGHT_SCENE_ENABLED, Boolean.valueOf(z));
    }

    public void applySuperResolution(CaptureRequest.Builder builder, boolean z) {
        VendorTagHelper.setValue(builder, CaptureRequestVendorTags.SUPER_RESOLUTION_ENABLED, Boolean.valueOf(z));
    }

    public void applySwMfnr(CaptureRequest.Builder builder, boolean z) {
        VendorTagHelper.setValue(builder, CaptureRequestVendorTags.SW_MFNR_ENABLED, Boolean.valueOf(z));
    }

    public void applyThermalLevel(CaptureRequest.Builder builder, int i) {
        VendorTagHelper.setValueQuietly(builder, CaptureRequestVendorTags.THERMAL_LEVEL, Integer.valueOf(i));
    }

    public void applyTimeWaterMark(CaptureRequest.Builder builder, String str) {
        VendorTagHelper.setValue(builder, CaptureRequestVendorTags.WATERMARK_TIME, str);
    }

    public void applyUltraPixelPortrait(CaptureRequest.Builder builder, boolean z) {
        VendorTagHelper.setValue(builder, CaptureRequestVendorTags.ULTRA_PIXEL_PORTRAIT_ENABLED, Boolean.valueOf(z));
    }

    public void applyUltraWideLDC(CaptureRequest.Builder builder, boolean z) {
        Log.d(TAG, "applyUltraWideLDC: " + z);
        VendorTagHelper.setValue(builder, CaptureRequestVendorTags.ULTRA_WIDE_LENS_DISTORTION_CORRECTION_LEVEL, Byte.valueOf(z ? (byte) 1 : 0));
    }

    public void applyVideoBokehBackLevel(CaptureRequest.Builder builder, int i) {
        VendorTagHelper.setValue(builder, CaptureRequestVendorTags.VIDEO_BOKEH_BACK_LEVEL, Integer.valueOf(i));
    }

    public void applyVideoBokehFrontLevel(CaptureRequest.Builder builder, float f2) {
        VendorTagHelper.setValue(builder, CaptureRequestVendorTags.VIDEO_BOKEH_FRONT_LEVEL, Float.valueOf(f2));
    }

    public void applyVideoFilterColorRetentionBack(CaptureRequest.Builder builder, boolean z) {
        VendorTagHelper.setValue(builder, CaptureRequestVendorTags.VIDEO_FILTER_COLOR_RETENTION_BACK, Boolean.valueOf(z));
    }

    public void applyVideoFilterColorRetentionFront(CaptureRequest.Builder builder, boolean z) {
        VendorTagHelper.setValue(builder, CaptureRequestVendorTags.VIDEO_FILTER_COLOR_RETENTION_FRONT, Boolean.valueOf(z));
    }

    public void applyVideoFilterId(CaptureRequest.Builder builder, int i) {
        VendorTagHelper.setValue(builder, CaptureRequestVendorTags.VIDEO_FILTER_ID, Integer.valueOf(i));
    }

    public void applyVideoLogEnable(CaptureRequest.Builder builder, byte b2) {
        VendorTagHelper.setValueQuietly(builder, CaptureRequestVendorTags.PRO_VIDEO_LOG_ENABLED, Byte.valueOf(b2));
    }

    public void applyVideoStreamState(CaptureRequest.Builder builder, boolean z) {
    }

    public void applyWaterMarkAppliedList(CaptureRequest.Builder builder, String str) {
        VendorTagHelper.setValue(builder, CaptureRequestVendorTags.WATERMARK_APPLIEDTYPE, str);
    }

    public void applyWaterMarkType(CaptureRequest.Builder builder, String str) {
        VendorTagHelper.setValue(builder, CaptureRequestVendorTags.WATERMARK_APPLIEDTYPE, str);
    }

    public void applyZsd(CaptureRequest.Builder builder, boolean z) {
    }

    public void applyZsl(CaptureRequest.Builder builder, boolean z) {
        CompatibilityUtils.setZsl(builder, z);
    }

    public void copyAiSceneFromCaptureResultToRequest(CaptureResult captureResult, CaptureRequest.Builder builder) {
    }

    public void copyFpcDataFromCaptureResultToRequest(CaptureResult captureResult, CaptureRequest.Builder builder) {
    }

    public VendorTag<CameraCharacteristics.Key<StreamConfiguration[]>> getDefaultSteamConfigurationsTag() {
        return null;
    }
}
