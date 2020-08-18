package com.android.camera2.vendortag;

import android.hardware.camera2.CaptureResult;
import android.util.Log;
import com.android.camera2.vendortag.struct.AECFrameControl;
import com.android.camera2.vendortag.struct.AFFrameControl;
import com.android.camera2.vendortag.struct.AWBFrameControl;
import com.android.camera2.vendortag.struct.MarshalQueryableASDScene;
import com.android.camera2.vendortag.struct.MarshalQueryableSuperNightExif;
import com.mi.config.b;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.function.Supplier;

public class CaptureResultVendorTags {
    public static final VendorTag<CaptureResult.Key<AECFrameControl>> AEC_FRAME_CONTROL = create(rc.INSTANCE, AECFrameControl.class);
    public static final VendorTag<CaptureResult.Key<Float>> AEC_LUX = create(Gc.INSTANCE, Float.class);
    public static final VendorTag<CaptureResult.Key<AFFrameControl>> AF_FRAME_CONTROL = create(Fd.INSTANCE, AFFrameControl.class);
    public static final VendorTag<CaptureResult.Key<Byte>> AI_HDR_DETECTED = create(Yc.INSTANCE, Byte.class);
    public static final VendorTag<CaptureResult.Key<Integer>> AI_SCENE_DETECTED = create(Sc.INSTANCE, Integer.class);
    public static final VendorTag<CaptureResult.Key<Byte>> AI_SCENE_ENABLE = create(Kd.INSTANCE, Byte.class);
    public static final VendorTag<CaptureResult.Key<int[]>> AUTOZOOM_ACTIVE_OBJECTS = create(ld.INSTANCE, int[].class);
    public static final VendorTag<CaptureResult.Key<float[]>> AUTOZOOM_BOUNDS = create(wd.INSTANCE, float[].class);
    public static final VendorTag<CaptureResult.Key<float[]>> AUTOZOOM_DELAYED_TARGET_BOUNDS_STABILIZED = create(Qc.INSTANCE, float[].class);
    public static final VendorTag<CaptureResult.Key<float[]>> AUTOZOOM_DELAYED_TARGET_BOUNDS_ZOOMED = create(Dd.INSTANCE, float[].class);
    public static final VendorTag<CaptureResult.Key<float[]>> AUTOZOOM_OBJECT_BOUNDS_STABILIZED = create(lc.INSTANCE, float[].class);
    public static final VendorTag<CaptureResult.Key<float[]>> AUTOZOOM_OBJECT_BOUNDS_ZOOMED = create(qd.INSTANCE, float[].class);
    public static final VendorTag<CaptureResult.Key<int[]>> AUTOZOOM_PAUSED_OBJECTS = create(vd.INSTANCE, int[].class);
    public static final VendorTag<CaptureResult.Key<int[]>> AUTOZOOM_SELECTED_OBJECTS = create(xc.INSTANCE, int[].class);
    public static final VendorTag<CaptureResult.Key<Integer>> AUTOZOOM_STATUS = create(Md.INSTANCE, Integer.class);
    public static final VendorTag<CaptureResult.Key<float[]>> AUTOZOOM_TARGET_BOUNDS_STABILIZED = create(Ac.INSTANCE, float[].class);
    public static final VendorTag<CaptureResult.Key<float[]>> AUTOZOOM_TARGET_BOUNDS_ZOOMED = create(oc.INSTANCE, float[].class);
    public static final VendorTag<CaptureResult.Key<AWBFrameControl>> AWB_FRAME_CONTROL = create(Gd.INSTANCE, AWBFrameControl.class);
    public static final VendorTag<CaptureResult.Key<Integer>> BEAUTY_BLUSHER = create(Xc.INSTANCE, Integer.class);
    public static final VendorTag<CaptureResult.Key<Integer>> BEAUTY_BODY_SLIM = create(Oc.INSTANCE, Integer.class);
    public static final VendorTag<CaptureResult.Key<Integer>> BEAUTY_BODY_SLIM_COUNT = create(Zc.INSTANCE, Integer.class);
    public static final VendorTag<CaptureResult.Key<Integer>> BEAUTY_CHIN = create(qc.INSTANCE, Integer.class);
    public static final VendorTag<CaptureResult.Key<Integer>> BEAUTY_ENLARGE_EYE = create(jd.INSTANCE, Integer.class);
    public static final VendorTag<CaptureResult.Key<Integer>> BEAUTY_EYEBROW_DYE = create(ed.INSTANCE, Integer.class);
    public static final VendorTag<CaptureResult.Key<Integer>> BEAUTY_HAIRLINE = create(Uc.INSTANCE, Integer.class);
    public static final VendorTag<CaptureResult.Key<Integer>> BEAUTY_HEAD_SLIM = create(Bc.INSTANCE, Integer.class);
    public static final VendorTag<CaptureResult.Key<Integer>> BEAUTY_JELLY_LIPS = create(hd.INSTANCE, Integer.class);
    public static final VendorTag<CaptureResult.Key<Integer>> BEAUTY_LEG_SLIM = create(Jc.INSTANCE, Integer.class);
    public static final VendorTag<CaptureResult.Key<String>> BEAUTY_LEVEL = create(id.INSTANCE, String.class);
    public static final VendorTag<CaptureResult.Key<Integer>> BEAUTY_LIPS = create(Vc.INSTANCE, Integer.class);
    public static final VendorTag<CaptureResult.Key<Integer>> BEAUTY_NECK = create(Ic.INSTANCE, Integer.class);
    public static final VendorTag<CaptureResult.Key<Integer>> BEAUTY_NOSE = create(zc.INSTANCE, Integer.class);
    public static final VendorTag<CaptureResult.Key<Integer>> BEAUTY_PUPIL_LINE = create(Cc.INSTANCE, Integer.class);
    public static final VendorTag<CaptureResult.Key<Integer>> BEAUTY_RISORIUS = create(mc.INSTANCE, Integer.class);
    public static final VendorTag<CaptureResult.Key<Integer>> BEAUTY_SHOULDER_SLIM = create(pd.INSTANCE, Integer.class);
    public static final VendorTag<CaptureResult.Key<Integer>> BEAUTY_SKIN_COLOR = create(sc.INSTANCE, Integer.class);
    public static final VendorTag<CaptureResult.Key<Integer>> BEAUTY_SKIN_SMOOTH = create(Kc.INSTANCE, Integer.class);
    public static final VendorTag<CaptureResult.Key<Integer>> BEAUTY_SLIM_FACE = create(Rc.INSTANCE, Integer.class);
    public static final VendorTag<CaptureResult.Key<Integer>> BEAUTY_SLIM_NOSE = create(dd.INSTANCE, Integer.class);
    public static final VendorTag<CaptureResult.Key<Integer>> BEAUTY_SMILE = create(Nd.INSTANCE, Integer.class);
    public static final VendorTag<CaptureResult.Key<Integer>> BUTT_SLIM = create(Fc.INSTANCE, Integer.class);
    public static final VendorTag<CaptureResult.Key<Boolean>> CONTROL_ENABLE_REMOSAIC = create(uc.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureResult.Key<Byte>> DEPURPLE = create(Ec.INSTANCE, Byte.class);
    public static final VendorTag<CaptureResult.Key<byte[]>> DISTORTION_FPC_DATA = create(Hd.INSTANCE, byte[].class);
    public static VendorTag<CaptureResult.Key<byte[]>> EXIF_INFO_VALUES = create(fd.INSTANCE, byte[].class);
    public static final VendorTag<CaptureResult.Key<Integer>> EYE_LIGHT_STRENGTH = create(yd.INSTANCE, Integer.class);
    public static final VendorTag<CaptureResult.Key<Integer>> EYE_LIGHT_TYPE = create(vc.INSTANCE, Integer.class);
    public static final VendorTag<CaptureResult.Key<Byte>> FAST_ZOOM_RESULT = create(Wc.INSTANCE, Byte.class);
    public static final VendorTag<CaptureResult.Key<Boolean>> FRONT_SINGLE_CAMERA_BOKEH = create(zd.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureResult.Key<Integer>> HDR_CHECKER_ADRC = create(nc.INSTANCE, Integer.class);
    public static final VendorTag<CaptureResult.Key<byte[]>> HDR_CHECKER_EV_VALUES = create(td.INSTANCE, byte[].class);
    public static final VendorTag<CaptureResult.Key<Integer>> HDR_CHECKER_SCENETYPE = create(wc.INSTANCE, Integer.class);
    public static final VendorTag<CaptureResult.Key<Byte>> HDR_MOTION_DETECTED = create(xd.INSTANCE, Byte.class);
    public static final VendorTag<CaptureResult.Key<int[]>> HISTOGRAM_STATS = create(nd.INSTANCE, int[].class);
    public static final CaptureResult.Key<Integer> ISO_VALUE = new CaptureResult.Key<>("xiaomi.algoup.iso_value", Integer.TYPE);
    public static final VendorTag<CaptureResult.Key<Integer>> IS_DEPTH_FOCUS = create(gd.INSTANCE, Integer.class);
    public static final VendorTag<CaptureResult.Key<Boolean>> IS_HDR_ENABLE = create(Mc.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureResult.Key<Integer>> IS_LLS_NEEDED = create(ud.INSTANCE, Integer.class);
    public static final VendorTag<CaptureResult.Key<Boolean>> IS_SR_ENABLE = create(Nc.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureResult.Key<Integer>> LENS_DIRTY_DETECTED = create(Id.INSTANCE, Integer.class);
    public static final VendorTag<CaptureResult.Key<Boolean>> MFNR_ENABLED = create(Ad.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureResult.Key<MarshalQueryableASDScene.ASDScene[]>> NON_SEMANTIC_SCENE = create(Lc.INSTANCE, MarshalQueryableASDScene.ASDScene[].class);
    public static final VendorTag<CaptureResult.Key<Boolean>> REAR_BOKEH_ENABLE = create(ad.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureResult.Key<Boolean>> REMOSAIC_DETECTED = create(Dc.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureResult.Key<byte[]>> SAT_DBG_INFO = create(bd.INSTANCE, byte[].class);
    public static final VendorTag<CaptureResult.Key<Boolean>> SAT_FALLBACK_DETECTED = create(cd.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureResult.Key<Integer>> SAT_MATER_CAMERA_ID = create(_c.INSTANCE, Integer.class);
    public static final VendorTag<CaptureResult.Key<Integer>> SCENE_DETECTION_RESULT = create(Ld.INSTANCE, Integer.class);
    public static final VendorTag<CaptureResult.Key<MarshalQueryableASDScene.ASDScene[]>> SEMANTIC_SCENE = create(od.INSTANCE, MarshalQueryableASDScene.ASDScene[].class);
    public static final VendorTag<CaptureResult.Key<Byte>> SENSOR_HDR_ENABLE = create(yc.INSTANCE, Byte.class);
    public static final VendorTag<CaptureResult.Key<MarshalQueryableASDScene.ASDScene[]>> STATE_SCENE = create(Pc.INSTANCE, MarshalQueryableASDScene.ASDScene[].class);
    public static final VendorTag<CaptureResult.Key<float[]>> STATISTICS_FACE_AGE = create(Tc.INSTANCE, float[].class);
    public static final VendorTag<CaptureResult.Key<float[]>> STATISTICS_FACE_FACESCORE = create(tc.INSTANCE, float[].class);
    public static final VendorTag<CaptureResult.Key<float[]>> STATISTICS_FACE_GENDER = create(sd.INSTANCE, float[].class);
    public static final VendorTag<CaptureResult.Key<byte[]>> STATISTICS_FACE_INFO = create(rd.INSTANCE, byte[].class);
    public static final VendorTag<CaptureResult.Key<float[]>> STATISTICS_FACE_PROP = create(Ed.INSTANCE, float[].class);
    public static final VendorTag<CaptureResult.Key<MarshalQueryableSuperNightExif.SuperNightExif>> SUPER_NIGHT_EXIF = create(Jd.INSTANCE, MarshalQueryableSuperNightExif.SuperNightExif.class);
    public static final VendorTag<CaptureResult.Key<Boolean>> SUPER_NIGHT_SCENE_ENABLED = create(kd.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureResult.Key<Boolean>> SW_MFNR_ENABLED = create(Cd.INSTANCE, Boolean.class);
    private static final String TAG = "CaptureResultVendorTags";
    public static final VendorTag<CaptureResult.Key<Byte>> ULTRA_WIDE_LENS_DISTORTION_CORRECTION_LEVEL = create(Hc.INSTANCE, Byte.class);
    public static final VendorTag<CaptureResult.Key<Integer>> ULTRA_WIDE_RECOMMENDED_RESULT = create(pc.INSTANCE, Integer.class);
    public static final int VALUE_SAT_MATER_CAMERA_ID_TELE = 3;
    public static final int VALUE_SAT_MATER_CAMERA_ID_ULTRA_WIDE = 1;
    public static final int VALUE_SAT_MATER_CAMERA_ID_WIDE = 2;
    public static final int VALUE_VIDEO_RECORD_STATE_IDLE = 2;
    public static final int VALUE_VIDEO_RECORD_STATE_PROCESS = 1;
    public static final VendorTag<CaptureResult.Key<Integer>> VIDEO_RECORD_STATE = create(Bd.INSTANCE, Integer.class);
    public static final VendorTag<CaptureResult.Key<Integer>> WHOLE_BODY_SLIM = create(md.INSTANCE, Integer.class);
    private static Constructor<CaptureResult.Key> resultConstructor;

    static /* synthetic */ String Ag() {
        return "xiaomi.beauty.eyeLightType";
    }

    static /* synthetic */ String Ah() {
        return "xiaomi.video.recordState";
    }

    static /* synthetic */ String Bg() {
        return "xiaomi.beauty.eyeLightStrength";
    }

    static /* synthetic */ String Bh() {
        return "xiaomi.faceAnalyzeResult.result";
    }

    static /* synthetic */ String Cg() {
        return "com.vidhance.autozoom.status";
    }

    static /* synthetic */ String Ch() {
        return "xiaomi.faceAnalyzeResult.age";
    }

    static /* synthetic */ String Dg() {
        return "xiaomi.supernight.enabled";
    }

    static /* synthetic */ String Dh() {
        return "xiaomi.faceAnalyzeResult.gender";
    }

    static /* synthetic */ String Eg() {
        return "xiaomi.beauty.headSlimRatio";
    }

    static /* synthetic */ String Eh() {
        return "xiaomi.faceAnalyzeResult.score";
    }

    static /* synthetic */ String Fg() {
        return "xiaomi.beauty.bodySlimRatio";
    }

    static /* synthetic */ String Fh() {
        return "xiaomi.faceAnalyzeResult.prop";
    }

    static /* synthetic */ String Gg() {
        return "xiaomi.beauty.shoulderSlimRatio";
    }

    static /* synthetic */ String Gh() {
        return "org.quic.camera2.statsconfigs.AECIsInsensorHDR";
    }

    static /* synthetic */ String Hg() {
        return "xiaomi.beauty.legSlimRatio";
    }

    static /* synthetic */ String Hh() {
        return "xiaomi.scene.result";
    }

    static /* synthetic */ String Ig() {
        return "xiaomi.beauty.oneKeySlimRatio";
    }

    static /* synthetic */ String Ih() {
        return b.isMTKPlatform() ? "com.xiaomi.statsconfigs.AecLux" : "com.qti.chi.statsaec.AecLux";
    }

    static /* synthetic */ String Jg() {
        return "xiaomi.beauty.buttPlumpSlimRatio";
    }

    static /* synthetic */ String Jh() {
        return "xiaomi.ai.asd.enabled";
    }

    static /* synthetic */ String Kg() {
        return "xiaomi.bokeh.enabled";
    }

    static /* synthetic */ String Kh() {
        return "xiaomi.ai.asd.sceneDetected";
    }

    static /* synthetic */ String Lg() {
        return "xiaomi.remosaic.enabled";
    }

    static /* synthetic */ String Lh() {
        return "xiaomi.hdr.hdrDetected";
    }

    static /* synthetic */ String Mg() {
        return "com.vidhance.autozoom.active_objects";
    }

    static /* synthetic */ String Mh() {
        return "xiaomi.ai.add.lensDirtyDetected";
    }

    static /* synthetic */ String Ng() {
        return "com.vidhance.autozoom.selected_objects";
    }

    static /* synthetic */ String Nh() {
        return b.isMTKPlatform() ? "xiaomi.camera.awb.colorTemperature" : "org.quic.camera2.statsconfigs.AWBFrameControl";
    }

    static /* synthetic */ String Og() {
        return "com.vidhance.autozoom.paused_objects";
    }

    static /* synthetic */ String Oh() {
        return "org.quic.camera2.statsconfigs.AECFrameControl";
    }

    static /* synthetic */ String Pg() {
        return "com.vidhance.autozoom.object_bounds_stabilized";
    }

    static /* synthetic */ String Ph() {
        return "org.quic.camera2.statsconfigs.AFFrameControl";
    }

    static /* synthetic */ String Qg() {
        return "com.vidhance.autozoom.object_bounds_zoomed";
    }

    static /* synthetic */ String Qh() {
        return b.isMTKPlatform() ? "xiaomi.histogram.stats" : "org.codeaurora.qcamera3.histogram.stats";
    }

    static /* synthetic */ String Rg() {
        return "com.vidhance.autozoom.delayed_target_bounds_stabilized";
    }

    static /* synthetic */ String Rh() {
        return "org.quic.camera.isDepthFocus.isDepthFocus";
    }

    static /* synthetic */ String Sh() {
        return "xiaomi.smoothTransition.result";
    }

    static /* synthetic */ String Th() {
        return "xiaomi.hdr.hdrChecker";
    }

    static /* synthetic */ String Uh() {
        return "xiaomi.hdr.hdrChecker.sceneType";
    }

    static /* synthetic */ String Vh() {
        return "xiaomi.hdr.hdrChecker.adrc";
    }

    static /* synthetic */ String Wh() {
        return "xiaomi.debugInfo.info";
    }

    static /* synthetic */ String Xh() {
        return "xiaomi.ai.misd.ultraWideRecommended";
    }

    static /* synthetic */ String Yh() {
        return "xiaomi.beauty.bodySlimCnt";
    }

    static /* synthetic */ String Zh() {
        return "xiaomi.superResolution.enabled";
    }

    static /* synthetic */ String _h() {
        return "xiaomi.hdr.enabled";
    }

    static /* synthetic */ String ai() {
        return "xiaomi.remosaic.detected";
    }

    static /* synthetic */ String bi() {
        return "xiaomi.ai.misd.SemanticScene";
    }

    static /* synthetic */ String ci() {
        return "xiaomi.ai.misd.NonSemanticScene";
    }

    private static <T> VendorTag<CaptureResult.Key<T>> create(final Supplier<String> supplier, final Class<T> cls) {
        return new VendorTag<CaptureResult.Key<T>>() {
            /* class com.android.camera2.vendortag.CaptureResultVendorTags.AnonymousClass1 */

            /* access modifiers changed from: protected */
            @Override // com.android.camera2.vendortag.VendorTag
            public CaptureResult.Key<T> create() {
                return CaptureResultVendorTags.resultKey(getName(), cls);
            }

            @Override // com.android.camera2.vendortag.VendorTag
            public String getName() {
                return (String) supplier.get();
            }
        };
    }

    static /* synthetic */ String di() {
        return "xiaomi.ai.misd.StateScene";
    }

    static /* synthetic */ String ei() {
        return "xiaomi.distortion.distortioFpcData";
    }

    static /* synthetic */ String fg() {
        return "com.vidhance.autozoom.bounds";
    }

    static /* synthetic */ String fi() {
        return "xiaomi.smoothTransition.masterCameraId";
    }

    static /* synthetic */ String gg() {
        return "com.vidhance.autozoom.target_bounds_stabilized";
    }

    static /* synthetic */ String gi() {
        return "xiaomi.smoothTransition.detected";
    }

    static /* synthetic */ String hg() {
        return "com.vidhance.autozoom.delayed_target_bounds_zoomed";
    }

    static /* synthetic */ String hi() {
        return "xiaomi.sat.dbg.satDbgInfo";
    }

    static /* synthetic */ String ig() {
        return "xiaomi.beauty.beautyLevelApplied";
    }

    static /* synthetic */ String ii() {
        return "xiaomi.ai.misd.hdrmotionDetected";
    }

    static /* synthetic */ String jg() {
        return "xiaomi.beauty.skinColorRatio";
    }

    static /* synthetic */ String ji() {
        return "xiaomi.ai.misd.SuperNightExif";
    }

    static /* synthetic */ String kg() {
        return "xiaomi.beauty.slimFaceRatio";
    }

    static /* synthetic */ String ki() {
        return "com.qti.stats_control.is_lls_needed";
    }

    static /* synthetic */ String lg() {
        return "xiaomi.beauty.skinSmoothRatio";
    }

    static /* synthetic */ String mg() {
        return "xiaomi.beauty.enlargeEyeRatio";
    }

    static /* synthetic */ String ng() {
        return "xiaomi.beauty.noseRatio";
    }

    static /* synthetic */ String og() {
        return "xiaomi.beauty.risoriusRatio";
    }

    static /* synthetic */ String pg() {
        return "xiaomi.beauty.lipsRatio";
    }

    public static void preload() {
        Log.d(TAG, "preloading...");
    }

    static /* synthetic */ String qg() {
        return "xiaomi.beauty.chinRatio";
    }

    /* access modifiers changed from: private */
    public static <T> CaptureResult.Key<T> resultKey(String str, Class<T> cls) {
        try {
            if (resultConstructor == null) {
                resultConstructor = CaptureResult.Key.class.getConstructor(String.class, cls.getClass());
                resultConstructor.setAccessible(true);
            }
            return resultConstructor.newInstance(str, cls);
        } catch (IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e2) {
            Log.d(TAG, "Cannot find/call Key constructor: " + e2.getMessage());
            return null;
        }
    }

    static /* synthetic */ String rg() {
        return "com.vidhance.autozoom.target_bounds_zoomed";
    }

    static /* synthetic */ String sg() {
        return "xiaomi.beauty.neckRatio";
    }

    static /* synthetic */ String tg() {
        return "xiaomi.beauty.smileRatio";
    }

    static /* synthetic */ String ug() {
        return "xiaomi.beauty.slimNoseRatio";
    }

    static /* synthetic */ String vg() {
        return "xiaomi.beauty.hairlineRatio";
    }

    static /* synthetic */ String vh() {
        return "xiaomi.distortion.ultraWideDistortionLevel";
    }

    static /* synthetic */ String wg() {
        return "xiaomi.beauty.eyeBrowDyeRatio";
    }

    static /* synthetic */ String wh() {
        return "xiaomi.depurple.enabled";
    }

    static /* synthetic */ String xg() {
        return "xiaomi.beauty.pupilLineRatio";
    }

    static /* synthetic */ String xh() {
        return "xiaomi.bokehrear.enabled";
    }

    static /* synthetic */ String yg() {
        return "xiaomi.beauty.lipGlossRatio";
    }

    static /* synthetic */ String yh() {
        return "xiaomi.mfnr.enabled";
    }

    static /* synthetic */ String zg() {
        return "xiaomi.beauty.blushRatio";
    }

    static /* synthetic */ String zh() {
        return "xiaomi.swmf.enabled";
    }
}
