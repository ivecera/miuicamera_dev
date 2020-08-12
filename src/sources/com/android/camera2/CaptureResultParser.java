package com.android.camera2;

import android.annotation.TargetApi;
import android.hardware.camera2.CaptureResult;
import com.android.camera.data.DataRepository;
import com.android.camera.log.Log;
import com.android.camera2.vendortag.CaptureResultVendorTags;
import com.android.camera2.vendortag.VendorTagHelper;
import com.android.camera2.vendortag.struct.AECFrameControl;
import com.android.camera2.vendortag.struct.AFFrameControl;
import com.android.camera2.vendortag.struct.AWBFrameControl;
import com.android.camera2.vendortag.struct.MarshalQueryableSuperNightExif;

@TargetApi(21)
public class CaptureResultParser {
    private static final float AECGAIN_THRESHOLD = 2.0f;
    private static final String TAG = "CaptureResultParser";

    public static AECFrameControl getAECFrameControl(CaptureResult captureResult) {
        return (AECFrameControl) VendorTagHelper.getValueSafely(captureResult, CaptureResultVendorTags.AEC_FRAME_CONTROL);
    }

    public static AFFrameControl getAFFrameControl(CaptureResult captureResult) {
        return (AFFrameControl) VendorTagHelper.getValueSafely(captureResult, CaptureResultVendorTags.AF_FRAME_CONTROL);
    }

    public static AWBFrameControl getAWBFrameControl(CaptureResult captureResult) {
        return (AWBFrameControl) VendorTagHelper.getValueSafely(captureResult, CaptureResultVendorTags.AWB_FRAME_CONTROL);
    }

    public static float getAecLux(CaptureResult captureResult) {
        Float f2 = (Float) VendorTagHelper.getValueSafely(captureResult, CaptureResultVendorTags.AEC_LUX);
        if (f2 == null) {
            return 0.0f;
        }
        return f2.floatValue();
    }

    public static int getAsdDetectedModes(CaptureResult captureResult) {
        Integer num = (Integer) VendorTagHelper.getValueQuietly(captureResult, CaptureResultVendorTags.AI_SCENE_DETECTED);
        if (num != null) {
            return num.intValue();
        }
        return 0;
    }

    public static int getBeautyBodySlimCountResult(CaptureResult captureResult) {
        Integer num = (Integer) VendorTagHelper.getValueSafely(captureResult, CaptureResultVendorTags.BEAUTY_BODY_SLIM_COUNT);
        if (num != null) {
            return num.intValue();
        }
        return 0;
    }

    public static byte[] getExifValues(CaptureResult captureResult) {
        return (byte[]) VendorTagHelper.getValueQuietly(captureResult, CaptureResultVendorTags.EXIF_INFO_VALUES);
    }

    public static boolean getFastZoomResult(CaptureResult captureResult) {
        Byte b2 = (Byte) VendorTagHelper.getValueSafely(captureResult, CaptureResultVendorTags.FAST_ZOOM_RESULT);
        String str = TAG;
        Log.d(str, "FAST_ZOOM_RESULT = " + b2);
        return b2 != null && b2.byteValue() == 1;
    }

    public static int getHdrCheckerAdrc(CaptureResult captureResult) {
        Integer num = (Integer) VendorTagHelper.getValueSafely(captureResult, CaptureResultVendorTags.HDR_CHECKER_ADRC);
        if (num != null) {
            return num.intValue();
        }
        return 0;
    }

    public static int getHdrCheckerSceneType(CaptureResult captureResult) {
        Integer num = (Integer) VendorTagHelper.getValueSafely(captureResult, CaptureResultVendorTags.HDR_CHECKER_SCENETYPE);
        if (num != null) {
            return num.intValue();
        }
        return 0;
    }

    public static byte[] getHdrCheckerValues(CaptureResult captureResult) {
        return (byte[]) VendorTagHelper.getValueSafely(captureResult, CaptureResultVendorTags.HDR_CHECKER_EV_VALUES);
    }

    public static int getHdrDetectedScene(CaptureResult captureResult) {
        Byte b2 = (Byte) VendorTagHelper.getValueQuietly(captureResult, CaptureResultVendorTags.AI_HDR_DETECTED);
        if (b2 != null) {
            return b2.byteValue();
        }
        return 0;
    }

    public static int[] getHistogramStats(CaptureResult captureResult) {
        return (int[]) VendorTagHelper.getValueQuietly(captureResult, CaptureResultVendorTags.HISTOGRAM_STATS);
    }

    public static byte[] getSatDbgInfo(CaptureResult captureResult) {
        return (byte[]) VendorTagHelper.getValueSafely(captureResult, CaptureResultVendorTags.SAT_DBG_INFO);
    }

    public static int getSatMasterCameraId(CaptureResult captureResult) {
        Integer num;
        if (captureResult != null) {
            num = (Integer) VendorTagHelper.getValueQuietly(captureResult, CaptureResultVendorTags.SAT_MATER_CAMERA_ID);
            String str = TAG;
            Log.d(str, "getSatMasterCameraId: " + num);
        } else {
            num = null;
        }
        if (num == null) {
            Log.w(TAG, "getSatMasterCameraId: not found");
            num = 2;
        }
        return num.intValue();
    }

    public static MarshalQueryableSuperNightExif.SuperNightExif getSuperNightInfo(CaptureResult captureResult) {
        return (MarshalQueryableSuperNightExif.SuperNightExif) VendorTagHelper.getValueQuietly(captureResult, CaptureResultVendorTags.SUPER_NIGHT_EXIF);
    }

    public static int getUltraWideDetectedResult(CaptureResult captureResult) {
        Integer num = (Integer) VendorTagHelper.getValueQuietly(captureResult, CaptureResultVendorTags.ULTRA_WIDE_RECOMMENDED_RESULT);
        if (num != null) {
            return num.intValue();
        }
        return 0;
    }

    public static boolean isASDEnable(CaptureResult captureResult) {
        Byte b2 = (Byte) VendorTagHelper.getValueSafely(captureResult, CaptureResultVendorTags.AI_SCENE_ENABLE);
        if (b2 == null) {
            return false;
        }
        String str = TAG;
        Log.d(str, "isASDEnable: " + b2);
        return b2.byteValue() == 1;
    }

    public static boolean isDepthFocus(CaptureResult captureResult, CameraCapabilities cameraCapabilities) {
        Integer num;
        if (captureResult == null) {
            return false;
        }
        if ((cameraCapabilities == null || cameraCapabilities.isTagDefined(CaptureResultVendorTags.IS_DEPTH_FOCUS.getName())) && (num = (Integer) VendorTagHelper.getValueQuietly(captureResult, CaptureResultVendorTags.IS_DEPTH_FOCUS)) != null) {
            return num.intValue() == 1;
        }
        return false;
    }

    public static boolean isHdrMotionDetected(CaptureResult captureResult) {
        Byte b2 = (Byte) VendorTagHelper.getValueQuietly(captureResult, CaptureResultVendorTags.HDR_MOTION_DETECTED);
        return (b2 == null || b2.byteValue() == 0) ? false : true;
    }

    public static boolean isLLSNeeded(CaptureResult captureResult) {
        Integer num = (Integer) VendorTagHelper.getValueQuietly(captureResult, CaptureResultVendorTags.IS_LLS_NEEDED);
        return num != null && num.intValue() == 1;
    }

    public static boolean isLensDirtyDetected(CaptureResult captureResult) {
        Integer num = (Integer) VendorTagHelper.getValueSafely(captureResult, CaptureResultVendorTags.LENS_DIRTY_DETECTED);
        return num != null && num.intValue() == 1;
    }

    public static boolean isQuadCfaRunning(CaptureResult captureResult) {
        AECFrameControl aECFrameControl;
        boolean i_s_q_c = DataRepository.dataItemFeature().i_s_q_c();
        String str = TAG;
        Log.d(str, "isQuadCfaRunning: support=" + i_s_q_c);
        float linearGain = (!i_s_q_c || (aECFrameControl = (AECFrameControl) VendorTagHelper.getValueSafely(captureResult, CaptureResultVendorTags.AEC_FRAME_CONTROL)) == null || aECFrameControl.getAecExposureDatas() == null || aECFrameControl.getAecExposureDatas().length <= 0) ? 3.0f : aECFrameControl.getAecExposureDatas()[0].getLinearGain();
        String str2 = TAG;
        Log.d(str2, "isQuadCfaRunning: gain=" + linearGain);
        return linearGain < 2.0f;
    }

    public static boolean isRemosaicDetected(CaptureResult captureResult) {
        Boolean bool = (Boolean) VendorTagHelper.getValueSafely(captureResult, CaptureResultVendorTags.REMOSAIC_DETECTED);
        String str = TAG;
        Log.d(str, "isRemosaicDetected: " + bool);
        return bool != null && bool.booleanValue();
    }

    public static boolean isSREnable(CaptureResult captureResult) {
        Boolean bool = (Boolean) VendorTagHelper.getValueSafely(captureResult, CaptureResultVendorTags.IS_SR_ENABLE);
        return bool != null && bool.booleanValue();
    }

    public static boolean isSatFallbackDetected(CaptureResult captureResult) {
        Boolean bool;
        if (captureResult == null || (bool = (Boolean) VendorTagHelper.getValueSafely(captureResult, CaptureResultVendorTags.SAT_FALLBACK_DETECTED)) == null) {
            return false;
        }
        return bool.booleanValue();
    }
}
