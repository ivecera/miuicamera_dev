package com.android.camera2.vendortag;

import android.graphics.Rect;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.params.StreamConfiguration;
import android.util.Log;
import com.android.camera2.vendortag.struct.SlowMotionVideoConfiguration;
import com.mi.config.b;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.function.Supplier;

public class CameraCharacteristicsVendorTags {
    public static final VendorTag<CameraCharacteristics.Key<Boolean>> ADAPTIVE_SNAPSHOT_SIZE_IN_SAT_MODE_SUPPORTED = create(C.INSTANCE, Boolean.class);
    public static final VendorTag<CameraCharacteristics.Key<int[]>> AI_SCENE_AVAILABLE_MODES = create(r.INSTANCE, int[].class);
    public static final VendorTag<CameraCharacteristics.Key<Boolean>> BEAUTY_MAKEUP = create(l.INSTANCE, Boolean.class);
    public static final VendorTag<CameraCharacteristics.Key<Byte>> BEAUTY_VERSION = create(a.INSTANCE, Byte.class);
    public static final VendorTag<CameraCharacteristics.Key<Integer>> CAMERA_ROLE_ID = create(j.INSTANCE, Integer.class);
    public static final VendorTag<CameraCharacteristics.Key<byte[]>> CAM_CALIBRATION_DATA = create(y.INSTANCE, byte[].class);
    public static final VendorTag<CameraCharacteristics.Key<Boolean>> COLOR_ENHANCE = create(m.INSTANCE, Boolean.class);
    public static final VendorTag<CameraCharacteristics.Key<int[]>> CUSTOM_HFR_FPS_TABLE = create(G.INSTANCE, int[].class);
    public static final VendorTag<CameraCharacteristics.Key<Byte>> EIS_PREVIEW_SUPPORTED = create(I.INSTANCE, Byte.class);
    public static final VendorTag<CameraCharacteristics.Key<int[]>> EXTRA_HIGH_SPEED_VIDEO_CONFIGURATIONS = create(D.INSTANCE, int[].class);
    public static final VendorTag<CameraCharacteristics.Key<Integer>> EXTRA_HIGH_SPEED_VIDEO_NUMBER = create(c.INSTANCE, Integer.class);
    public static final VendorTag<CameraCharacteristics.Key<Byte>> FOVC_SUPPORTED = create(w.INSTANCE, Byte.class);
    public static final VendorTag<CameraCharacteristics.Key<Byte>> IS_QCFA_SENSOR = create(A.INSTANCE, Byte.class);
    public static final VendorTag<CameraCharacteristics.Key<Boolean>> LOG_FORMAT = create(t.INSTANCE, Boolean.class);
    public static final VendorTag<CameraCharacteristics.Key<Byte>> MACRO_ZOOM_FEATURE = create(k.INSTANCE, Byte.class);
    public static final VendorTag<CameraCharacteristics.Key<Byte>> MFNR_BOKEH_SUPPORTED = create(H.INSTANCE, Byte.class);
    public static final VendorTag<CameraCharacteristics.Key<Float>> MI_ALGO_ASD_VERSION = create(E.INSTANCE, Float.class);
    public static final VendorTag<CameraCharacteristics.Key<Byte>> PARALLEL_CAMERA_DEVICE = create(q.INSTANCE, Byte.class);
    public static final VendorTag<CameraCharacteristics.Key<Rect>> QCFA_ACTIVE_ARRAY_SIZE = create(K.INSTANCE, Rect.class);
    public static final VendorTag<CameraCharacteristics.Key<StreamConfiguration[]>> QCFA_STREAM_CONFIGURATIONS = create(u.INSTANCE, StreamConfiguration[].class);
    public static final VendorTag<CameraCharacteristics.Key<StreamConfiguration[]>> SCALER_AVAILABLE_LIMIT_STREAM_CONFIGURATIONS = create(z.INSTANCE, StreamConfiguration[].class);
    public static final VendorTag<CameraCharacteristics.Key<StreamConfiguration[]>> SCALER_AVAILABLE_SR_STREAM_CONFIGURATIONS = create(F.INSTANCE, StreamConfiguration[].class);
    public static final VendorTag<CameraCharacteristics.Key<StreamConfiguration[]>> SCALER_AVAILABLE_STREAM_CONFIGURATIONS = create(M.INSTANCE, StreamConfiguration[].class);
    public static final VendorTag<CameraCharacteristics.Key<Integer>> SCREEN_LIGHT_BRIGHTNESS = create(b.INSTANCE, Integer.class);
    public static final VendorTag<CameraCharacteristics.Key<SlowMotionVideoConfiguration[]>> SLOW_MOTION_VIDEO_CONFIGURATIONS = create(d.INSTANCE, SlowMotionVideoConfiguration[].class);
    public static final VendorTag<CameraCharacteristics.Key<Boolean>> SUPER_PORTRAIT_SUPPORTED = create(g.INSTANCE, Boolean.class);
    private static final String TAG = "CameraCharacteristicsVendorTags";
    public static final VendorTag<CameraCharacteristics.Key<Byte>> TELE_OIS_SUPPORTED = create(i.INSTANCE, Byte.class);
    public static final VendorTag<CameraCharacteristics.Key<Boolean>> TRIPARTITE_LIGHT = create(J.INSTANCE, Boolean.class);
    public static final VendorTag<CameraCharacteristics.Key<Boolean>> VIDEO_BEAUTY = create(v.INSTANCE, Boolean.class);
    public static final VendorTag<CameraCharacteristics.Key<Boolean>> VIDEO_BOKEH_ADJUEST = create(n.INSTANCE, Boolean.class);
    public static final VendorTag<CameraCharacteristics.Key<Boolean>> VIDEO_BOKEH_FRONT_ADJUEST = create(e.INSTANCE, Boolean.class);
    public static final VendorTag<CameraCharacteristics.Key<Boolean>> VIDEO_COLOR_RENTENTION_BACK = create(p.INSTANCE, Boolean.class);
    public static final VendorTag<CameraCharacteristics.Key<Boolean>> VIDEO_COLOR_RENTENTION_FRONT = create(s.INSTANCE, Boolean.class);
    public static final VendorTag<CameraCharacteristics.Key<Boolean>> VIDEO_FILTER = create(x.INSTANCE, Boolean.class);
    public static final VendorTag<CameraCharacteristics.Key<Boolean>> VIDEO_MIMOVIE = create(f.INSTANCE, Boolean.class);
    public static final VendorTag<CameraCharacteristics.Key<Byte>> XIAOMI_AI_COLOR_CORRECTION_VERSION = create(h.INSTANCE, Byte.class);
    public static final VendorTag<CameraCharacteristics.Key<StreamConfiguration[]>> XIAOMI_SCALER_HEIC_STREAM_CONFIGURATIONS = create(o.INSTANCE, StreamConfiguration[].class);
    public static final VendorTag<CameraCharacteristics.Key<int[]>> XIAOMI_SENSOR_INFO_SENSITIVITY_RANGE = create(L.INSTANCE, int[].class);
    public static final VendorTag<CameraCharacteristics.Key<Integer>> XIAOMI_YUV_FORMAT = create(B.INSTANCE, Integer.class);
    private static Constructor<CameraCharacteristics.Key> characteristicsConstructor;

    static /* synthetic */ String Ag() {
        return "com.xiaomi.cameraid.role.cameraId";
    }

    static /* synthetic */ String Bg() {
        return "com.xiaomi.camera.supportedfeatures.superportraitSupported";
    }

    static /* synthetic */ String Cg() {
        return "com.xiaomi.camera.supportedfeatures.videoBokeh";
    }

    static /* synthetic */ String Dg() {
        return "com.xiaomi.camera.supportedfeatures.videomimovie";
    }

    static /* synthetic */ String Eg() {
        return "com.xiaomi.camera.supportedfeatures.videologformat";
    }

    static /* synthetic */ String Fg() {
        return "com.xiaomi.camera.supportedfeatures.3rdLightWeightSupported";
    }

    static /* synthetic */ String Gg() {
        return "xiaomi.scaler.availableHeicStreamConfigurations";
    }

    static /* synthetic */ String Hg() {
        return "xiaomi.yuv.format";
    }

    static /* synthetic */ String Ig() {
        return "com.xiaomi.camera.supportedfeatures.AIEnhancementVersion";
    }

    static /* synthetic */ String Jg() {
        return "xiaomi.sensor.info.sensitivityRange";
    }

    static /* synthetic */ String Kg() {
        return "com.xiaomi.camera.supportedfeatures.colorenhancement";
    }

    static /* synthetic */ String Lg() {
        return "com.xiaomi.camera.supportedfeatures.parallelCameraDevice";
    }

    static /* synthetic */ String Mg() {
        return "com.xiaomi.camera.supportedfeatures.videoBokehFront";
    }

    static /* synthetic */ String Ng() {
        return "com.xiaomi.camera.supportedfeatures.videoColorRetentionFront";
    }

    static /* synthetic */ String Og() {
        return "com.xiaomi.camera.supportedfeatures.videoColorRetentionBack";
    }

    static /* synthetic */ String Pg() {
        return "com.xiaomi.camera.supportedfeatures.videoBeauty";
    }

    static /* synthetic */ String Qg() {
        return "com.xiaomi.camera.supportedfeatures.beautyMakeup";
    }

    static /* synthetic */ String Rg() {
        return "org.codeaurora.qcamera3.additional_hfr_video_sizes.hfr_video_size";
    }

    /* access modifiers changed from: private */
    public static <T> CameraCharacteristics.Key<T> characteristicsKey(String str, Class<T> cls) {
        try {
            if (characteristicsConstructor == null) {
                characteristicsConstructor = CameraCharacteristics.Key.class.getConstructor(String.class, cls.getClass());
                characteristicsConstructor.setAccessible(true);
            }
            return characteristicsConstructor.newInstance(str, cls);
        } catch (IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e2) {
            Log.d(TAG, "Cannot find/call Key constructor: " + e2.getMessage());
            return null;
        }
    }

    private static <T> VendorTag<CameraCharacteristics.Key<T>> create(final Supplier<String> supplier, final Class<T> cls) {
        return new VendorTag<CameraCharacteristics.Key<T>>() {
            /* class com.android.camera2.vendortag.CameraCharacteristicsVendorTags.AnonymousClass1 */

            /* access modifiers changed from: protected */
            @Override // com.android.camera2.vendortag.VendorTag
            public CameraCharacteristics.Key<T> create() {
                return CameraCharacteristicsVendorTags.characteristicsKey(getName(), cls);
            }

            @Override // com.android.camera2.vendortag.VendorTag
            public String getName() {
                return (String) supplier.get();
            }
        };
    }

    static /* synthetic */ String fg() {
        return b.isMTKPlatform() ? "com.xiaomi.ai.asd.availableSceneMode" : "xiaomi.ai.asd.availableSceneMode";
    }

    static /* synthetic */ String gg() {
        return b.isMTKPlatform() ? "com.xiaomi.qcfa.supported" : "org.codeaurora.qcamera3.quadra_cfa.is_qcfa_sensor";
    }

    static /* synthetic */ String hg() {
        return b.isMTKPlatform() ? "com.mediatek.streamingfeature.availableHfpsMaxResolutions" : "org.quic.camera2.customhfrfps.info.CustomHFRFpsTable";
    }

    static /* synthetic */ String ig() {
        return b.isMTKPlatform() ? "com.xiaomi.scaler.availableStreamConfigurations" : "xiaomi.scaler.availableStreamConfigurations";
    }

    static /* synthetic */ String jg() {
        return b.isMTKPlatform() ? "com.xiaomi.scaler.availableLimitStreamConfigurations" : "xiaomi.scaler.availableLimitStreamConfigurations";
    }

    static /* synthetic */ String kg() {
        return "org.codeaurora.qcamera3.quadra_cfa.activeArraySize";
    }

    static /* synthetic */ String lg() {
        return "org.codeaurora.qcamera3.quadra_cfa.availableStreamConfigurations";
    }

    static /* synthetic */ String mg() {
        return b.isMTKPlatform() ? "com.xiaomi.scaler.availableSuperResolutionStreamConfigurations" : "xiaomi.scaler.availableSuperResolutionStreamConfigurations";
    }

    static /* synthetic */ String ng() {
        return "com.xiaomi.camera.algoup.dualCalibrationData";
    }

    static /* synthetic */ String og() {
        return b.isMTKPlatform() ? "com.xiaomi.capabilities.videoStabilization.previewSupported" : "xiaomi.capabilities.videoStabilization.previewSupported";
    }

    static /* synthetic */ String pg() {
        return b.isMTKPlatform() ? "com.xiaomi.flash.screenLight.brightness" : "xiaomi.flash.screenLight.brightness";
    }

    public static void preload() {
        Log.d(TAG, "preloading...");
    }

    static /* synthetic */ String qg() {
        return b.isMTKPlatform() ? "com.xiaomi.capabilities.mfnr_bokeh_supported" : "xiaomi.capabilities.mfnr_bokeh_supported";
    }

    static /* synthetic */ String rg() {
        return "com.xiaomi.camera.supportedfeatures.videofilter";
    }

    static /* synthetic */ String sg() {
        return "xiaomi.capabilities.macro_zoom_feature";
    }

    static /* synthetic */ String tg() {
        return "com.xiaomi.camera.supportedfeatures.fovcEnable";
    }

    static /* synthetic */ String ug() {
        return "com.xiaomi.camera.supportedfeatures.beautyVersion";
    }

    static /* synthetic */ String vg() {
        return b.isMTKPlatform() ? "com.xiaomi.capabilities.satAdaptiveSnapshotSizeSupported" : "xiaomi.capabilities.satAdaptiveSnapshotSizeSupported";
    }

    static /* synthetic */ String wg() {
        return "org.codeaurora.qcamera3.additional_hfr_video_sizes.valid_number";
    }

    static /* synthetic */ String xg() {
        return "com.mediatek.smvrfeature.availableSmvrModes";
    }

    static /* synthetic */ String yg() {
        return "xiaomi.ai.misd.MiAlgoAsdVersion";
    }

    static /* synthetic */ String zg() {
        return "com.xiaomi.camera.supportedfeatures.TeleOisSupported";
    }
}
