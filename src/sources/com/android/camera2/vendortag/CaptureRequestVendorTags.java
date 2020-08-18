package com.android.camera2.vendortag;

import android.graphics.Rect;
import android.hardware.camera2.CaptureRequest;
import android.util.Log;
import com.android.camera2.vendortag.struct.MarshalQueryableASDScene;
import com.mi.config.b;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.function.Supplier;

public class CaptureRequestVendorTags {
    public static final VendorTag<CaptureRequest.Key<Boolean>> AI_SCENE = create(Pa.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> AI_SCENE_APPLY = create(Ia.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> AI_SCENE_PERIOD = create(Xb.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Boolean>> ALGO_UP_ENABLED = create(Pb.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureRequest.Key<Byte>> ASD_DIRTY_ENABLE = create(N.INSTANCE, Byte.class);
    public static final VendorTag<CaptureRequest.Key<Boolean>> ASD_ENABLE = create(yb.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> AUTOZOOM_APPLY_IN_PREVIEW = create(vb.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<float[]>> AUTOZOOM_CENTER_OFFSET = create(Aa.INSTANCE, float[].class);
    public static final VendorTag<CaptureRequest.Key<Integer>> AUTOZOOM_FORCE_LOCK = create(va.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Float>> AUTOZOOM_MINIMUM_SCALING = create(Ja.INSTANCE, Float.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> AUTOZOOM_MODE = create(Rb.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Float>> AUTOZOOM_SCALE_OFFSET = create(V.INSTANCE, Float.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> AUTOZOOM_SELECT = create(wb.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<float[]>> AUTOZOOM_START = create(ha.INSTANCE, float[].class);
    public static final VendorTag<CaptureRequest.Key<Integer>> AUTOZOOM_STOP = create(P.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> AUTOZOOM_UNSELECT = create(wa.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Byte>> BACKWARD_CAPTURE_HINT = create(Fa.INSTANCE, Byte.class);
    public static final VendorTag<CaptureRequest.Key<Byte>> BACK_SOFT_LIGHT = create(Ea.INSTANCE, Byte.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> BEAUTY_BLUSHER = create(Vb.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> BEAUTY_BODY_SLIM = create(W.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> BEAUTY_CHIN = create(Sa.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> BEAUTY_ENLARGE_EYE = create(Oa.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> BEAUTY_EYEBROW_DYE = create(gc.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> BEAUTY_HAIRLINE = create(Eb.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> BEAUTY_HEAD_SLIM = create(Z.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> BEAUTY_JELLY_LIPS = create(ka.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> BEAUTY_LEG_SLIM = create(T.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<String>> BEAUTY_LEVEL = create(ra.INSTANCE, String.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> BEAUTY_LIPS = create(Wa.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> BEAUTY_NECK = create(Ra.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> BEAUTY_NOSE = create(Ob.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> BEAUTY_PUPIL_LINE = create(eb.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> BEAUTY_RISORIUS = create(rb.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> BEAUTY_SHOULDER_SLIM = create(za.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> BEAUTY_SKIN_COLOR = create(qb.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> BEAUTY_SKIN_SMOOTH = create(da.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> BEAUTY_SLIM_FACE = create(zb.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> BEAUTY_SLIM_NOSE = create(hc.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> BEAUTY_SMILE = create(ma.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<String>> BOKEH_F_NUMBER = create(Ab.INSTANCE, String.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> BURST_CAPTURE_HINT = create(dc.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> BURST_SHOOT_FPS = create(Db.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> BUTT_SLIM = create(ic.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Byte>> CAMERA_AI_30 = create(ac.INSTANCE, Byte.class);
    public static final VendorTag<CaptureRequest.Key<Byte>> CINEMATIC_PHOTO_ENABLED = create(la.INSTANCE, Byte.class);
    public static final VendorTag<CaptureRequest.Key<Byte>> CINEMATIC_VIDEO_ENABLED = create(pb.INSTANCE, Byte.class);
    public static final VendorTag<CaptureRequest.Key<Byte>> COLOR_ENHANCE_ENABLED = create(bb.INSTANCE, Byte.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> CONTRAST_LEVEL = create(_b.INSTANCE, Integer.class);
    public static VendorTag<CaptureRequest.Key<Integer>> CONTROL_AI_SCENE_MODE = create(ea.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<byte[]>> CONTROL_DISTORTION_FPC_DATA = create(hb.INSTANCE, byte[].class);
    public static final VendorTag<CaptureRequest.Key<Boolean>> CONTROL_ENABLE_REMOSAIC = create(Bb.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureRequest.Key<int[]>> CONTROL_QUICK_PREVIEW = create(Zb.INSTANCE, int[].class);
    public static final int[] CONTROL_QUICK_PREVIEW_OFF = {0};
    public static final int[] CONTROL_QUICK_PREVIEW_ON = {1};
    public static final VendorTag<CaptureRequest.Key<int[]>> CONTROL_REMOSAIC_HINT = create(jc.INSTANCE, int[].class);
    public static final int[] CONTROL_REMOSAIC_HINT_OFF = {0};
    public static final int[] CONTROL_REMOSAIC_HINT_ON = {1};
    public static final VendorTag<CaptureRequest.Key<String>> CUSTOM_WATERMARK_TEXT = create(db.INSTANCE, String.class);
    public static final VendorTag<CaptureRequest.Key<Boolean>> DEBUG_INFO_AS_WATERMARK = create(Gb.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureRequest.Key<Boolean>> DEFLICKER_ENABLED = create(Tb.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureRequest.Key<Byte>> DEPURPLE = create(Sb.INSTANCE, Byte.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> DEVICE_ORIENTATION = create(mb.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> EXPOSURE_METERING = create(tb.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> EYE_LIGHT_STRENGTH = create(Xa.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> EYE_LIGHT_TYPE = create(Ta.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Boolean>> FACE_AGE_ANALYZE_ENABLED = create(ua.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureRequest.Key<Boolean>> FACE_SCORE_ENABLED = create(ob.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> FLASH_CURRENT = create(ya.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> FLASH_MODE = create(ab.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Boolean>> FLAW_DETECT_ENABLE = create(Ha.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureRequest.Key<Boolean>> FRONT_MIRROR = create(gb.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureRequest.Key<Boolean>> FRONT_SINGLE_CAMERA_BOKEH = create(_a.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureRequest.Key<Boolean>> HDR_BOKEH_ENABLED = create(xa.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureRequest.Key<Byte>> HDR_BRACKET_MODE = create(Ga.INSTANCE, Byte.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> HDR_CHECKER_ADRC = create(Wb.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Boolean>> HDR_CHECKER_ENABLE = create(Hb.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> HDR_CHECKER_SCENETYPE = create(X.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> HDR_CHECKER_STATUS = create(cb.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Boolean>> HDR_ENABLED = create(Mb.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> HDR_MODE = create(S.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> HFPSVR_MODE = create(ca.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Boolean>> HHT_ENABLED = create(Qb.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureRequest.Key<Boolean>> HINT_FOR_RAW_REPROCESS = create(Cb.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureRequest.Key<Byte>> HISTOGRAM_STATS_ENABLED = create(Nb.INSTANCE, Byte.class);
    public static final VendorTag<CaptureRequest.Key<Long>> ISO_EXP = create(Fb.INSTANCE, Long.class);
    public static final VendorTag<CaptureRequest.Key<Byte>> IS_HFR_PREVIEW = create(xb.INSTANCE, Byte.class);
    public static final VendorTag<CaptureRequest.Key<Boolean>> LENS_DIRTY_DETECT = create(Ba.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureRequest.Key<Byte>> MACRO_MODE = create(Za.INSTANCE, Byte.class);
    public static final VendorTag<CaptureRequest.Key<Boolean>> MFNR_ENABLED = create(Va.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureRequest.Key<int[]>> MTK_CONFIGURE_SETTING_PROPRIETARY = create(oa.INSTANCE, int[].class);
    public static final int[] MTK_CONFIGURE_SETTING_PROPRIETARY_OFF = {0};
    public static final int[] MTK_CONFIGURE_SETTING_PROPRIETARY_ON = {1};
    public static final VendorTag<CaptureRequest.Key<Byte>> MTK_EXPOSURE_METERING_MODE = create(ec.INSTANCE, Byte.class);
    public static final byte MTK_EXPOSURE_METERING_MODE_AVERAGE = 2;
    public static final byte MTK_EXPOSURE_METERING_MODE_CENTER_WEIGHT = 0;
    public static final byte MTK_EXPOSURE_METERING_MODE_SOPT = 1;
    public static final VendorTag<CaptureRequest.Key<Integer>> MULTIFRAME_INPUTNUM = create(Ka.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Byte>> NORMAL_WIDE_LENS_DISTORTION_CORRECTION_LEVEL = create(sb.INSTANCE, Byte.class);
    public static final VendorTag<CaptureRequest.Key<MarshalQueryableASDScene.ASDScene[]>> ON_TRIPOD_MODE = create(Kb.INSTANCE, MarshalQueryableASDScene.ASDScene[].class);
    public static final VendorTag<CaptureRequest.Key<Boolean>> PARALLEL_ENABLED = create(sa.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureRequest.Key<byte[]>> PARALLEL_PATH = create(qa.INSTANCE, byte[].class);
    public static final VendorTag<CaptureRequest.Key<Integer>> PORTRAIT_LIGHTING = create(O.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Rect>> POST_PROCESS_CROP_REGION = create(na.INSTANCE, Rect.class);
    public static final VendorTag<CaptureRequest.Key<Byte>> PRO_VIDEO_LOG_ENABLED = create(Q.INSTANCE, Byte.class);
    public static final VendorTag<CaptureRequest.Key<Boolean>> REAR_BOKEH_ENABLE = create(Y.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureRequest.Key<Byte>> RECORDING_END_STREAM = create(jb.INSTANCE, Byte.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> SANPSHOT_FLIP_MODE = create(Na.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> SATURATION = create(Yb.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Boolean>> SAT_FALLBACK_ENABLE = create(Lb.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureRequest.Key<Boolean>> SAT_IS_ZOOMING = create(Ua.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureRequest.Key<Byte>> SCREEN_LIGHT_HINT = create(fa.INSTANCE, Byte.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> SELECT_PRIORITY = create(Jb.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> SHARPNESS_CONTROL = create(bc.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> SHRINK_MEMORY_MODE = create(cc.INSTANCE, Integer.class);
    public static final int SHRINK_MEMORY_MODE_ALL = 2;
    public static final int SHRINK_MEMORY_MODE_INACTIVE = 1;
    public static final int SHRINK_MEMORY_MODE_NONE = 0;
    public static final VendorTag<CaptureRequest.Key<int[]>> SMVR_MODE = create(ib.INSTANCE, int[].class);
    public static final VendorTag<CaptureRequest.Key<Boolean>> SNAP_SHOT_TORCH = create(ga.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureRequest.Key<Boolean>> ST_ENABLED = create(lb.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureRequest.Key<Boolean>> ST_FAST_ZOOM_IN = create(ta.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureRequest.Key<Boolean>> SUPER_NIGHT_SCENE_ENABLED = create(Ya.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureRequest.Key<Boolean>> SUPER_RESOLUTION_ENABLED = create(Ca.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureRequest.Key<Boolean>> SW_MFNR_ENABLED = create(ja.INSTANCE, Boolean.class);
    private static final String TAG = "CaptureRequestVendorTags";
    public static final VendorTag<CaptureRequest.Key<Integer>> THERMAL_LEVEL = create(Ib.INSTANCE, Integer.class);
    public static VendorTag<CaptureRequest.Key<Boolean>> ULTRA_PIXEL_PORTRAIT_ENABLED = create(fb.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureRequest.Key<Byte>> ULTRA_WIDE_LENS_DISTORTION_CORRECTION_LEVEL = create(Qa.INSTANCE, Byte.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> USE_CUSTOM_WB = create(Ma.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> USE_ISO_VALUE = create(ia.INSTANCE, Integer.class);
    public static final int VALUE_HFPSVR_MODE_OFF = 0;
    public static final int VALUE_HFPSVR_MODE_ON = 1;
    public static final int VALUE_SANPSHOT_FLIP_MODE_OFF = 0;
    public static final int VALUE_SANPSHOT_FLIP_MODE_ON = 1;
    public static final int VALUE_SELECT_PRIORITY_EXP_TIME_PRIORITY = 1;
    public static final int VALUE_SELECT_PRIORITY_ISO_PRIORITY = 0;
    public static final int[] VALUE_SMVR_MODE_120FPS = {120, 4};
    public static final int[] VALUE_SMVR_MODE_240FPS = {240, 8};
    public static final int VALUE_VIDEO_RECORD_CONTROL_PREPARE = 0;
    public static final int VALUE_VIDEO_RECORD_CONTROL_START = 1;
    public static final int VALUE_VIDEO_RECORD_CONTROL_STOP = 2;
    public static final byte VALUE_ZSL_CAPTURE_MODE_OFF = 0;
    public static final byte VALUE_ZSL_CAPTURE_MODE_ON = 1;
    public static final VendorTag<CaptureRequest.Key<Integer>> VIDEO_BOKEH_BACK_LEVEL = create(ub.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Float>> VIDEO_BOKEH_FRONT_LEVEL = create(kc.INSTANCE, Float.class);
    public static final VendorTag<CaptureRequest.Key<Boolean>> VIDEO_FILTER_COLOR_RETENTION_BACK = create(kb.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureRequest.Key<Boolean>> VIDEO_FILTER_COLOR_RETENTION_FRONT = create(aa.INSTANCE, Boolean.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> VIDEO_FILTER_ID = create(Ub.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> VIDEO_RECORD_CONTROL = create(ba.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<String>> WATERMARK_APPLIEDTYPE = create(pa.INSTANCE, String.class);
    public static final VendorTag<CaptureRequest.Key<String>> WATERMARK_AVAILABLETYPE = create(Da.INSTANCE, String.class);
    public static final VendorTag<CaptureRequest.Key<String>> WATERMARK_FACE = create(U.INSTANCE, String.class);
    public static final VendorTag<CaptureRequest.Key<String>> WATERMARK_TIME = create(La.INSTANCE, String.class);
    public static final VendorTag<CaptureRequest.Key<Integer>> WHOLE_BODY_SLIM = create(fc.INSTANCE, Integer.class);
    public static final VendorTag<CaptureRequest.Key<Byte>> ZSL_CAPTURE_MODE = create(nb.INSTANCE, Byte.class);
    private static Constructor<CaptureRequest.Key> requestConstructor;

    static /* synthetic */ String Ag() {
        return "xiaomi.bokeh.fNumberApplied";
    }

    static /* synthetic */ String Ah() {
        return "xiaomi.beauty.skinColorRatio";
    }

    static /* synthetic */ String Ai() {
        return "org.codeaurora.qcamera3.ae_bracket.mode";
    }

    static /* synthetic */ String Bg() {
        return "xiaomi.videoBokehParam.back";
    }

    static /* synthetic */ String Bh() {
        return "xiaomi.beauty.slimFaceRatio";
    }

    static /* synthetic */ String Bi() {
        return "xiaomi.multiframe.inputNum";
    }

    static /* synthetic */ String Cg() {
        return "com.vidhance.autozoom.stop";
    }

    static /* synthetic */ String Ch() {
        return "xiaomi.beauty.skinSmoothRatio";
    }

    static /* synthetic */ String Ci() {
        return "xiaomi.depurple.enabled";
    }

    static /* synthetic */ String Dg() {
        return "xiaomi.videoBokehParam.front";
    }

    static /* synthetic */ String Dh() {
        return "xiaomi.beauty.enlargeEyeRatio";
    }

    static /* synthetic */ String Di() {
        return "xiaomi.MacroMode.enabled";
    }

    static /* synthetic */ String Eg() {
        return "xiaomi.videofilter.filterApplied";
    }

    static /* synthetic */ String Eh() {
        return "xiaomi.beauty.noseRatio";
    }

    static /* synthetic */ String Fg() {
        return "xiaomi.colorRetention.enable";
    }

    static /* synthetic */ String Fh() {
        return "xiaomi.beauty.risoriusRatio";
    }

    static /* synthetic */ String Gg() {
        return "xiaomi.colorRetention.frontEnable";
    }

    static /* synthetic */ String Gh() {
        return "xiaomi.beauty.lipsRatio";
    }

    static /* synthetic */ String Hg() {
        return "xiaomi.smoothTransition.enabled";
    }

    static /* synthetic */ String Hh() {
        return "xiaomi.beauty.chinRatio";
    }

    static /* synthetic */ String Ig() {
        return "xiaomi.smoothTransition.fallback";
    }

    static /* synthetic */ String Ih() {
        return "xiaomi.beauty.neckRatio";
    }

    static /* synthetic */ String Jg() {
        return "xiaomi.smoothTransition.fastZoomIn";
    }

    static /* synthetic */ String Jh() {
        return "xiaomi.beauty.smileRatio";
    }

    static /* synthetic */ String Kg() {
        return "xiaomi.ai.add.enabled";
    }

    static /* synthetic */ String Kh() {
        return "xiaomi.beauty.slimNoseRatio";
    }

    static /* synthetic */ String Lg() {
        return "xiaomi.portrait.lighting";
    }

    static /* synthetic */ String Lh() {
        return "xiaomi.beauty.hairlineRatio";
    }

    static /* synthetic */ String Mg() {
        return "com.vidhance.autozoom.start_region";
    }

    static /* synthetic */ String Mh() {
        return "xiaomi.watermark.availableType";
    }

    static /* synthetic */ String Ng() {
        return "com.vidhance.autozoom.select";
    }

    static /* synthetic */ String Nh() {
        return "xiaomi.watermark.typeApplied";
    }

    static /* synthetic */ String Og() {
        return "com.vidhance.autozoom.unselect";
    }

    static /* synthetic */ String Oh() {
        return "xiaomi.watermark.time";
    }

    static /* synthetic */ String Pg() {
        return "com.vidhance.autozoom.force_lock";
    }

    static /* synthetic */ String Ph() {
        return "xiaomi.watermark.face";
    }

    static /* synthetic */ String Qg() {
        return "com.vidhance.autozoom.center_offset";
    }

    static /* synthetic */ String Qh() {
        return "xiaomi.snapshotTorch.enabled";
    }

    static /* synthetic */ String Rg() {
        return "com.vidhance.autozoom.scale_offset";
    }

    static /* synthetic */ String Rh() {
        return "xiaomi.flip.enabled";
    }

    static /* synthetic */ String Sg() {
        return "xiaomi.watermark.custom";
    }

    static /* synthetic */ String Sh() {
        return "xiaomi.burst.captureHint";
    }

    static /* synthetic */ String Tg() {
        return "xiaomi.satIsZooming.satIsZooming";
    }

    static /* synthetic */ String Th() {
        return "xiaomi.burst.shootFPS";
    }

    static /* synthetic */ String Ug() {
        return "com.mediatek.streamingfeature.hfpsMode";
    }

    static /* synthetic */ String Uh() {
        return "xiaomi.beauty.eyeBrowDyeRatio";
    }

    static /* synthetic */ String Vg() {
        return "com.mediatek.smvrfeature.smvrMode";
    }

    static /* synthetic */ String Vh() {
        return "xiaomi.beauty.pupilLineRatio";
    }

    static /* synthetic */ String Wg() {
        return "com.mediatek.control.capture.zsl.mode";
    }

    static /* synthetic */ String Wh() {
        return "xiaomi.beauty.lipGlossRatio";
    }

    static /* synthetic */ String Xg() {
        return "com.mediatek.control.capture.flipmode";
    }

    static /* synthetic */ String Xh() {
        return "xiaomi.beauty.blushRatio";
    }

    static /* synthetic */ String Yg() {
        return "com.mediatek.control.capture.remosaicenable";
    }

    static /* synthetic */ String Yh() {
        return "xiaomi.beauty.eyeLightType";
    }

    static /* synthetic */ String Zg() {
        return "xiaomi.remosaic.enabled";
    }

    static /* synthetic */ String Zh() {
        return "xiaomi.beauty.eyeLightStrength";
    }

    static /* synthetic */ String _g() {
        return "xiaomi.distortion.distortioFpcData";
    }

    static /* synthetic */ String _h() {
        return "xiaomi.supernight.enabled";
    }

    static /* synthetic */ String ah() {
        return "com.mediatek.control.capture.hintForRawReprocess";
    }

    static /* synthetic */ String ai() {
        return "xiaomi.mimovie.enabled";
    }

    static /* synthetic */ String bh() {
        return "xiaomi.superResolution.cropRegionMtk";
    }

    static /* synthetic */ String bi() {
        return "xiaomi.beauty.headSlimRatio";
    }

    static /* synthetic */ String ci() {
        return "xiaomi.beauty.bodySlimRatio";
    }

    private static <T> VendorTag<CaptureRequest.Key<T>> create(final Supplier<String> supplier, final Class<T> cls) {
        return new VendorTag<CaptureRequest.Key<T>>() {
            /* class com.android.camera2.vendortag.CaptureRequestVendorTags.AnonymousClass1 */

            /* access modifiers changed from: protected */
            @Override // com.android.camera2.vendortag.VendorTag
            public CaptureRequest.Key<T> create() {
                return CaptureRequestVendorTags.requestKey(getName(), cls);
            }

            @Override // com.android.camera2.vendortag.VendorTag
            public String getName() {
                return (String) supplier.get();
            }
        };
    }

    static /* synthetic */ String dh() {
        return "com.mediatek.3afeature.aeMeteringMode";
    }

    static /* synthetic */ String di() {
        return "xiaomi.beauty.shoulderSlimRatio";
    }

    static /* synthetic */ String eh() {
        return "com.mediatek.configure.setting.initrequest";
    }

    static /* synthetic */ String ei() {
        return "xiaomi.beauty.legSlimRatio";
    }

    static /* synthetic */ String fg() {
        return "com.vidhance.autozoom.mode";
    }

    static /* synthetic */ String fh() {
        return "com.mediatek.configure.setting.proprietaryRequest";
    }

    static /* synthetic */ String fi() {
        return "xiaomi.beauty.oneKeySlimRatio";
    }

    static /* synthetic */ String gg() {
        return "com.vidhance.autozoom.applyinpreview";
    }

    static /* synthetic */ String gh() {
        return "xiaomi.ai.asd.sceneDetected";
    }

    static /* synthetic */ String gi() {
        return "xiaomi.beauty.buttPlumpSlimRatio";
    }

    static /* synthetic */ String hg() {
        return "xiaomi.video.recordControl";
    }

    static /* synthetic */ String hh() {
        return "xiaomi.ai.misd.StateScene";
    }

    static /* synthetic */ String hi() {
        return "xiaomi.distortion.distortionLevelApplied";
    }

    static /* synthetic */ String ig() {
        return "xiaomi.hdr.enabled";
    }

    static /* synthetic */ String ih() {
        return "xiaomi.ai.flaw.enabled";
    }

    static /* synthetic */ String ii() {
        return "xiaomi.distortion.ultraWideDistortionLevel";
    }

    static /* synthetic */ String jg() {
        return "xiaomi.algoup.enabled";
    }

    static /* synthetic */ String jh() {
        return "xiaomi.ai.asd.dirtyEnable";
    }

    static /* synthetic */ String ji() {
        return "xiaomi.snapshot.front.ScreenLighting.enabled";
    }

    static /* synthetic */ String kg() {
        return "xiaomi.hdr.hdrChecker.enabled";
    }

    static /* synthetic */ String kh() {
        return "xiaomi.pro.video.log.enabled";
    }

    static /* synthetic */ String ki() {
        return "xiaomi.softlightMode.enabled";
    }

    static /* synthetic */ String lg() {
        return "xiaomi.hdr.hdrChecker.status";
    }

    static /* synthetic */ String lh() {
        return "xiaomi.thermal.thermalLevel";
    }

    static /* synthetic */ String li() {
        return "xiaomi.snapshot.backwardfetchframe.enabled";
    }

    static /* synthetic */ String mg() {
        return "xiaomi.hdr.hdrChecker.sceneType";
    }

    static /* synthetic */ String mh() {
        return "xiaomi.pro.video.movie.enabled";
    }

    static /* synthetic */ String mi() {
        return "org.codeaurora.qcamera3.iso_exp_priority.select_priority";
    }

    static /* synthetic */ String ng() {
        return "xiaomi.hdr.hdrChecker.adrc";
    }

    static /* synthetic */ String nh() {
        return "xiaomi.pro.video.histogram.stats.enabled";
    }

    static /* synthetic */ String og() {
        return "xiaomi.hdr.hdrMode";
    }

    static /* synthetic */ String oh() {
        return "xiaomi.memory.shrinkMode";
    }

    static /* synthetic */ String oi() {
        return "org.codeaurora.qcamera3.iso_exp_priority.use_iso_exp_priority";
    }

    static /* synthetic */ String pg() {
        return "xiaomi.parallel.path";
    }

    static /* synthetic */ String ph() {
        return "xiaomi.asd.enabled";
    }

    static /* synthetic */ String pi() {
        return "org.codeaurora.qcamera3.iso_exp_priority.use_iso_value";
    }

    public static void preload() {
        Log.d(TAG, "preloading...");
    }

    static /* synthetic */ String qg() {
        return "xiaomi.parallel.enabled";
    }

    static /* synthetic */ String qh() {
        return "xiaomi.colorenhancement.enabled";
    }

    static /* synthetic */ String qi() {
        return b.isMTKPlatform() ? "xiaomi.camera.awb.cct" : "com.qti.stats.awbwrapper.AWBCCT";
    }

    static <T> CaptureRequest.Key<T> requestKey(String str, Class<T> cls) {
        try {
            if (requestConstructor == null) {
                requestConstructor = CaptureRequest.Key.class.getConstructor(String.class, cls.getClass());
                requestConstructor.setAccessible(true);
            }
            return requestConstructor.newInstance(str, cls);
        } catch (IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e2) {
            Log.d(TAG, "Cannot find/call Key constructor: " + e2.getMessage());
            return null;
        }
    }

    static /* synthetic */ String rg() {
        return "com.vidhance.autozoom.minimumscaling";
    }

    static /* synthetic */ String rh() {
        return "xiaomi.bokeh.hdrEnabled";
    }

    static /* synthetic */ String ri() {
        return "org.codeaurora.qcamera3.saturation.use_saturation";
    }

    static /* synthetic */ String sg() {
        return "xiaomi.hht.enabled";
    }

    static /* synthetic */ String sh() {
        return "xiaomi.softlightMode.current";
    }

    static /* synthetic */ String si() {
        return "org.codeaurora.qcamera3.sharpness.strength";
    }

    static /* synthetic */ String tg() {
        return "xiaomi.node.hfr.deflicker.enabled";
    }

    static /* synthetic */ String th() {
        return "xiaomi.watermark.debug";
    }

    static /* synthetic */ String ti() {
        return "org.codeaurora.qcamera3.exposure_metering.exposure_metering_mode";
    }

    static /* synthetic */ String ug() {
        return "xiaomi.superportrait.enabled";
    }

    static /* synthetic */ String uh() {
        return "xiaomi.flash.mode";
    }

    static /* synthetic */ String ui() {
        return "org.quic.camera.recording.endOfStream";
    }

    static /* synthetic */ String vg() {
        return "xiaomi.superResolution.enabled";
    }

    static /* synthetic */ String vh() {
        return "xiaomi.ai.segment.enabled";
    }

    static /* synthetic */ String vi() {
        return "xiaomi.ai.asd.enabled";
    }

    static /* synthetic */ String wg() {
        return "xiaomi.mfnr.enabled";
    }

    static /* synthetic */ String wh() {
        return "xiaomi.faceGenderAndAge.enabled";
    }

    static /* synthetic */ String wi() {
        return "xiaomi.ai.asd.sceneApplied";
    }

    static /* synthetic */ String xg() {
        return "xiaomi.swmf.enabled";
    }

    static /* synthetic */ String xh() {
        return "xiaomi.faceScore.enabled";
    }

    static /* synthetic */ String xi() {
        return "xiaomi.ai.asd.period";
    }

    static /* synthetic */ String yg() {
        return "xiaomi.bokeh.enabled";
    }

    static /* synthetic */ String yh() {
        return "xiaomi.device.orientation";
    }

    static /* synthetic */ String yi() {
        return "org.codeaurora.qcamera3.contrast.level";
    }

    static /* synthetic */ String zg() {
        return "xiaomi.bokehrear.enabled";
    }

    static /* synthetic */ String zh() {
        return "xiaomi.beauty.beautyLevelApplied";
    }

    static /* synthetic */ String zi() {
        return "xiaomi.hfrPreview.isHFRPreview";
    }
}
