package com.mi.config;

import android.os.Build;
import android.os.SystemProperties;
import android.text.TextUtils;
import com.android.camera.AutoLockManager;
import com.android.camera.Util;
import com.android.camera.data.DataRepository;
import d.d.a;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: Device */
public class b {
    public static final boolean Au = ("cancro".equals(vu) && Build.MODEL.startsWith("MI 3"));
    public static final boolean Bu = Au;
    public static final boolean Cu = (a.device_is_HM201302x && !a.device_is_armani && !a.device_is_HM201401x);
    public static final boolean Du = a.device_is_HM201401x;
    public static final boolean Eu = a.device_is_HM2014501;
    public static final boolean Fu = a.device_is_armani;
    public static final boolean Gu = a.device_is_lcsh92_wet_x;
    public static final boolean Hu = a.device_is_lte26007;
    public static final boolean Iu = a.device_is_cancro_mi4;
    public static final boolean Ju = a.device_is_virgo;
    public static final boolean Ku = "leo".equals(vu);
    public static final boolean Lu = "lithium".equals(vu);
    public static final boolean Mu = "chiron".equals(vu);
    public static final boolean Nu = "beryllium".equals(vu);
    public static final boolean Ou = "violet".equals(vu);
    public static final boolean Pu = d.getBoolean(d.is_hongmi, false);
    public static final boolean Qu = "polaris".equals(vu);
    public static final boolean Ru = "sirius".equals(vu);
    public static final boolean Su = "dipper".equals(vu);
    public static final boolean Tu = "andromeda".equals(vu);
    public static final boolean Uu = "perseus".equals(vu);
    public static final boolean Vu = "cepheus".equals(vu);
    public static final boolean Wu = "grus".equals(vu);
    public static final boolean Xu = "begonia".equals(vu);
    public static final boolean Yu = ("phoenix".equals(vu) || "phoenixin".equals(vu));
    public static final boolean Zu = "begoniain".equals(vu);
    public static final boolean _u = "ginkgo".equals(vu);
    public static final boolean bv = "pyxis".equals(vu);
    public static final boolean cv = "vela".equals(vu);
    public static final boolean dv = "laurus".equals(vu);
    public static final boolean ev = "laurel_sprout".equals(vu);
    public static final boolean fv = "tucana".equals(vu);
    public static final boolean gv = ("tucana".equals(vu) && SystemProperties.get("persist.camera.rearMain.vendorID", "03").equals("03"));
    public static final boolean hv = "umi".equals(vu);
    public static final boolean jv = "cmi".equals(vu);
    public static final boolean kv = ("lmi".equals(vu) || "lmiin".equals(vu));
    public static final boolean lg = a.device_is_aries_or_taurus;
    public static final boolean lv = ("lmipro".equals(vu) || "lmiinpro".equals(vu));
    public static final boolean mv = "draco".equals(vu);
    public static final boolean nv;
    public static final boolean og = a.model_is_m2ax;
    public static final boolean ov = "crux".equals(vu);
    public static final boolean pv = a.build_type_user_version_incremental_not_XYZ;
    public static final boolean qv = a.ro_cust_test_is_cm;
    public static final boolean rv = a.carrier_name_is_cm_variant_cn_chinamobile_cta;
    public static final boolean sg = d.getBoolean(d.is_xiaomi, false);
    private static final int sv = 1;
    private static final int tv = 4;
    private static final int uv = 8;
    public static final String vu = Build.DEVICE;
    private static ArrayList<String> vv = null;
    public static final String wu = "qcom";
    private static final String[] wv = {"KR", "JP"};
    public static final String xu = "mediatek";
    private static final String xv = "ro.boot.hwversion";
    private static final int yu = 100;
    private static final AtomicReference<Optional<Boolean>> yv = new AtomicReference<>(Optional.empty());
    public static final String zu = Build.MODEL;

    static {
        boolean z = true;
        if (!"picasso".equals(vu) && !"picassoin".equals(vu)) {
            z = false;
        }
        nv = z;
    }

    public static boolean Al() {
        return d.getBoolean(d.support_3d_face_beauty, false);
    }

    public static boolean Bl() {
        return d.getBoolean(d.support_camera_role, false);
    }

    public static boolean Cl() {
        return d.getBoolean(d.support_camera_dynamic_light_spot, false);
    }

    public static boolean Dl() {
        return d.getBoolean(d.support_front_beauty_mfnr, false);
    }

    public static boolean El() {
        return d.getBoolean(d.support_front_flash, false);
    }

    public static boolean Fl() {
        return d.getBoolean(d.support_camera_gradienter, false);
    }

    public static boolean G(boolean z) {
        String str = SystemProperties.get("ro.miui.customized.region");
        if ("fr_sfr".equals(str) || "fr_orange".equals(str)) {
            return false;
        }
        if (!"es_vodafone".equals(str) || !"NL".equals(getCountry())) {
            return z;
        }
        return false;
    }

    public static boolean Gl() {
        return false;
    }

    public static boolean Hl() {
        return d.getBoolean(d.support_mi_face_beauty, false);
    }

    public static boolean Il() {
        return Al() || Hl();
    }

    public static boolean Jl() {
        return d.getBoolean(d.support_psensor_pocket_mode, true);
    }

    public static boolean Kl() {
        return !DataRepository.dataItemFeature().s_b_a_OR_T() && Pu;
    }

    public static boolean Ll() {
        return d.getBoolean(d.support_screen_light, false);
    }

    public static boolean Ml() {
        return (d.getInteger(d.camera_supported_asd, 0) & 13) != 0;
    }

    public static boolean Nl() {
        return !a.ro_boot_hwc_contains_GLOBAL_or_is_android_one && d.getBoolean(d.support_camera_age_detection, false);
    }

    public static boolean Ol() {
        return d.getBoolean(d.support_camera_aohdr, false);
    }

    public static boolean Pl() {
        return (d.getInteger(d.camera_supported_asd, 0) & 1) != 0;
    }

    public static boolean Qk() {
        return !qv && d.getBoolean(d.support_camera_boost_brightness, false);
    }

    public static boolean Ql() {
        return (d.getInteger(d.camera_supported_asd, 0) & 4) != 0;
    }

    public static boolean Rk() {
        return d.getBoolean(d.enable_algorithm_in_file_suffix, false);
    }

    public static boolean Rl() {
        return false;
    }

    static String Sk() {
        int i = SystemProperties.getInt("ro.boot.camera.config", 1);
        if (i == 0) {
            return "_pro";
        }
        if (i != 1) {
        }
        return "";
    }

    public static boolean Sl() {
        return d.getBoolean(d.support_chroma_flash, false);
    }

    public static boolean Te() {
        return DataRepository.dataItemFeature().c_0x1a_ANDNOT_global();
    }

    public static ArrayList<String> Tk() {
        if (vv == null) {
            vv = new ArrayList<>();
            String[] stringArray = d.getStringArray(d.fp_nav_event_name_list);
            if (stringArray != null) {
                Collections.addAll(vv, stringArray);
            }
        }
        return vv;
    }

    public static boolean Tl() {
        return d.getBoolean(d.support_edge_handgrip, false);
    }

    public static String Uk() {
        return cl() ? "_l" : ol() ? "_in" : !DataRepository.dataItemFeature().getBoolean(c.c_0x18, false) ? "" : (Build.MODEL.contains("BROWN EDITION") || Build.MODEL.contains("Explorer")) ? "_a" : Build.MODEL.contains("ROY") ? "_b" : dl() ? "_s" : (jl() || il() || kl()) ? "_global" : ul() ? "_premium" : ll() ? vl() ? "_global_pro" : "_global" : "";
    }

    public static boolean Ul() {
        return d.getBoolean(d.support_camera_face_info_water_mark, false);
    }

    public static int Vk() {
        return d.getInteger(d.HIBERNATION_TIMEOUT, AutoLockManager.HIBERNATION_TIMEOUT);
    }

    public static boolean Vl() {
        return false;
    }

    private static boolean W(String str) {
        for (String str2 : wv) {
            if (TextUtils.equals(str, str2)) {
                return true;
            }
        }
        return false;
    }

    public static boolean Wk() {
        return d.getBoolean(d.sensor_has_latency, false);
    }

    public static boolean Wl() {
        return d.getBoolean(d.support_camera_record_location, false);
    }

    public static boolean Xk() {
        return ((float) Util.sWindowHeight) / ((float) Util.sWindowWidth) >= 2.0f && d.getBoolean(d.is_18x9_ratio_screen, false);
    }

    public static boolean Xl() {
        return d.getBoolean(d.support_camera_burst_shoot, false);
    }

    public static boolean Yk() {
        return !lo() && DataRepository.dataItemFeature().c_0x36() && Tk() != null && !Tk().isEmpty();
    }

    public static boolean Yl() {
        return !a.ro_boot_hwc_contains_GLOBAL_or_is_android_one && d.getBoolean(d.support_camera_magic_mirror, false);
    }

    public static boolean Zk() {
        return DataRepository.dataItemFeature().s_a_u_e_f_m() || Yu;
    }

    public static boolean Zl() {
        return d.getBoolean(d.support_camera_manual_function, false);
    }

    public static boolean _k() {
        if (a.ro_boot_hwc_contains_GLOBAL_or_is_android_one) {
            return W(getCountry());
        }
        return false;
    }

    public static boolean _l() {
        if (cl()) {
            return false;
        }
        return d.getBoolean(d.support_camera_movie_solid, false);
    }

    public static boolean al() {
        return d.getBoolean(d.is_camera_app_water_mark, false);
    }

    public static boolean am() {
        return !_k();
    }

    public static boolean bl() {
        String str = SystemProperties.get(xv);
        return ov && (TextUtils.equals(str, "7.1.7") || TextUtils.equals(str, "7.2.0"));
    }

    public static boolean bm() {
        return d.getBoolean(d.support_camera_new_style_time_water_mark, false);
    }

    public static boolean cl() {
        if (!"onc".equals(vu)) {
            return false;
        }
        String str = SystemProperties.get(xv);
        return !TextUtils.isEmpty(str) && '2' == str.charAt(0);
    }

    public static boolean cm() {
        return d.getBoolean(d.support_object_track, false);
    }

    public static boolean dl() {
        return vu.equalsIgnoreCase("lavender") && "India_48_5".equalsIgnoreCase(SystemProperties.get("ro.boot.hwc"));
    }

    public static boolean dm() {
        return d.getBoolean(d.support_camera_peaking_mf, false);
    }

    public static boolean el() {
        return Lu || Mu || Qu;
    }

    public static boolean em() {
        return !a.ro_boot_hwc_contains_GLOBAL_or_is_android_one && d.getBoolean(d.support_camera_quick_snap, false);
    }

    public static boolean fl() {
        return hl() || bv || Wu || DataRepository.dataItemFeature().s_a_u_e_f_m();
    }

    public static boolean fm() {
        return d.getBoolean(d.support_dual_sd_card, false);
    }

    public static int getBurstShootCount() {
        return d.getInteger(d.burst_shoot_count, 100);
    }

    public static String getCountry() {
        String str = Util.sRegion;
        return !TextUtils.isEmpty(str) ? str : Locale.getDefault().getCountry();
    }

    public static boolean gl() {
        return !Fu && !Hu && !a.device_is_HM2014xxx && !Au && !Gu && !Cu && !Du && !Eu && !lg && !og && !Bu && d.getBoolean(d.is_front_video_quality_1080p, true);
    }

    public static boolean gm() {
        return d.getBoolean(d.support_camera_shader_effect, false);
    }

    public static boolean hl() {
        return Uu && a.ro_boot_hwc_contains_GLOBAL_or_is_android_one;
    }

    public static boolean hm() {
        return d.getBoolean(d.support_camera_skin_beauty, false);
    }

    public static boolean il() {
        return vu.equalsIgnoreCase("davinci") && a.ro_boot_hwc_contains_GLOBAL_or_is_android_one;
    }

    public static boolean im() {
        return d.getBoolean(d.is_support_stereo, false);
    }

    public static boolean isMTKPlatform() {
        if (!yv.get().isPresent()) {
            synchronized (yv) {
                if (!yv.get().isPresent()) {
                    yv.set(Optional.of(Boolean.valueOf(xu.equals(d.getString(d.VENDOR)))));
                }
            }
        }
        return yv.get().get().booleanValue();
    }

    public static boolean isSupportSuperResolution() {
        return d.getBoolean(d.support_super_resolution, false);
    }

    public static boolean isSupportedOpticalZoom() {
        return d.getBoolean(d.is_support_optical_zoom, false);
    }

    public static boolean jl() {
        return vu.equalsIgnoreCase("raphael") && a.ro_boot_hwc_contains_GLOBAL_or_is_android_one;
    }

    public static boolean jm() {
        return d.getBoolean(d.support_camera_tilt_shift, false);
    }

    public static boolean kl() {
        return bv && a.ro_boot_hwc_contains_GLOBAL_or_is_android_one;
    }

    public static boolean km() {
        return d.getBoolean(d.support_camera_water_mark, false);
    }

    private static boolean ko() {
        return SystemProperties.getBoolean("ro.hardware.fp.fod", false);
    }

    public static boolean ll() {
        return fv && a.ro_boot_hwc_contains_GLOBAL_or_is_android_one;
    }

    public static boolean lm() {
        return d.getBoolean(d.support_camera_torch_capture, false);
    }

    private static boolean lo() {
        return d.getBoolean(d.front_fingerprint_sensor, false) || ko();
    }

    public static boolean ml() {
        return d.getBoolean(d.is_hal_does_caf_when_flash_on, false);
    }

    public static boolean mm() {
        return d.getBoolean(d.support_camera_video_pause, false);
    }

    public static boolean nl() {
        return d.getBoolean(d.is_camera_hold_blur_background, false);
    }

    public static boolean nm() {
        return d.getBoolean(d.support_camera_4k_quality, false);
    }

    public static boolean ol() {
        return Nu && "India".equalsIgnoreCase(SystemProperties.get("ro.boot.hwc"));
    }

    public static boolean om() {
        return d.getBoolean(d.is_surface_size_limit, false);
    }

    public static boolean pd() {
        return DataRepository.dataItemFeature().c_0x14();
    }

    public static boolean pl() {
        return d.getBoolean(d.is_camera_isp_rotated, true);
    }

    public static boolean pm() {
        return !sg && !Pu;
    }

    public static boolean ql() {
        return kv || lv;
    }

    public static boolean qm() {
        return !Fu && !Hu && !a.device_is_HM2014xxx && !Au && !Gu && !Cu && !Du && !Eu && !lg && !og && !Bu && !Iu && d.getBoolean(d.is_video_snapshot_size_limit, true);
    }

    public static boolean rl() {
        return d.getBoolean(d.is_camera_lower_qrscan_frequency, false);
    }

    public static boolean rm() {
        return d.getBoolean(d.support_picture_watermark, false);
    }

    public static boolean sl() {
        return false;
    }

    public static boolean sm() {
        return d.getBoolean(d.support_realtime_manual_exposure_time, true);
    }

    public static boolean tl() {
        return d.getBoolean(d.is_pad, false);
    }

    public static boolean ul() {
        return vu.equalsIgnoreCase("raphael") && Build.MODEL.endsWith("Premium Edition");
    }

    public static boolean vl() {
        return fv && !gv;
    }

    public static boolean wl() {
        return wu.equals(d.getString(d.VENDOR));
    }

    public static boolean xl() {
        return d.getBoolean(d.is_rgb888_egl_prefer, true);
    }

    public static boolean yl() {
        return false;
    }

    public static boolean zl() {
        return d.getBoolean(d.is_camera_preview_with_subthread_looper, false);
    }
}
