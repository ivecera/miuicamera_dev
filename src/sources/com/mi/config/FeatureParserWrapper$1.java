package com.mi.config;

import java.util.HashMap;

class FeatureParserWrapper$1 extends HashMap<String, String> {
    FeatureParserWrapper$1() {
        put(d.ex, "o_0x00_s_s_l");
        put(d.pw, "o_0x01_r_p_s_f");
        put(d.VENDOR, "o_0x02_soc_vendor");
        put(d.jx, "o_0x03_support_3d_face_beauty");
        put(d.kx, "o_0x04_support_mi_face_beauty");
        put(d.Ww, "o_0x05_is_support_optical_zoom");
        put(d.lw, "o_0x06_is_support_peaking_mf");
        put(d.nx, "o_0x08_is_support_dynamic_light_spot");
        put(d.Tv, "o_0x07_support_hfr");
        put(d.Jv, "o_0x08_support_movie_solid");
        put(d.tw, "o_0x09_support_tilt_shift");
        put(d.mw, "o_0x10_support_gradienter");
        put(d.rx, "o_0x11_picture_water_mark");
        put(d.uw, "o_0x12_magic_mirror");
        put(d.Lv, "o_0x13_age_detection");
        put(d.Iv, "o_0x14_burst_count");
        put(d.Av, "o_0x15_support_dual_sd_card");
        put(d.gx, "o_0x16_support_psensor_pocket_mode");
        put(d.px, "o_0x17_support_super_resolution");
        put(d.vw, "o_0x18_support_camera_quick_snap");
        put(d.sx, "o_0x19_camera_role");
        put(d.Nv, "o_0x20_time_water_mark");
        put(d.Gv, "o_0x21_long_press_shutter");
        put(d.Mv, "o_0x22_support_location");
        put(d.ew, "o_0x23_support_manual");
        put(d.nw, "o_0x24_support_qr_code");
    }

    public String put(String str, String str2) {
        if (str2 == null || !str2.startsWith("o_0x")) {
            throw new IllegalStateException("The key \"" + str + "\" must be mapped to non-null string starting with \"o_0x\"");
        }
        String str3 = (String) super.put((Object) str, (Object) str2);
        if (str3 == null) {
            return null;
        }
        throw new IllegalStateException("The key \"" + str + "\" has already be mapped to \"" + str3 + "\"");
    }
}
