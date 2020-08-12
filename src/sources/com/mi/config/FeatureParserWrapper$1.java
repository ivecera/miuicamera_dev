package com.mi.config;

import java.util.HashMap;

class FeatureParserWrapper$1 extends HashMap<String, String> {
    FeatureParserWrapper$1() {
        put(d.support_screen_light, "o_0x00_s_s_l");
        put(d.camera_reduce_preview_flag, "o_0x01_r_p_s_f");
        put(d.VENDOR, "o_0x02_soc_vendor");
        put(d.support_3d_face_beauty, "o_0x03_support_3d_face_beauty");
        put(d.support_mi_face_beauty, "o_0x04_support_mi_face_beauty");
        put(d.is_support_optical_zoom, "o_0x05_is_support_optical_zoom");
        put(d.support_camera_peaking_mf, "o_0x06_is_support_peaking_mf");
        put(d.support_camera_dynamic_light_spot, "o_0x08_is_support_dynamic_light_spot");
        put(d.support_camera_hfr, "o_0x07_support_hfr");
        put(d.support_camera_movie_solid, "o_0x08_support_movie_solid");
        put(d.support_camera_tilt_shift, "o_0x09_support_tilt_shift");
        put(d.support_camera_gradienter, "o_0x10_support_gradienter");
        put(d.support_picture_watermark, "o_0x11_picture_water_mark");
        put(d.support_camera_magic_mirror, "o_0x12_magic_mirror");
        put(d.support_camera_age_detection, "o_0x13_age_detection");
        put(d.burst_shoot_count, "o_0x14_burst_count");
        put(d.support_dual_sd_card, "o_0x15_support_dual_sd_card");
        put(d.support_psensor_pocket_mode, "o_0x16_support_psensor_pocket_mode");
        put(d.support_super_resolution, "o_0x17_support_super_resolution");
        put(d.support_camera_quick_snap, "o_0x18_support_camera_quick_snap");
        put(d.support_camera_role, "o_0x19_camera_role");
        put(d.support_camera_water_mark, "o_0x20_time_water_mark");
        put(d.support_camera_burst_shoot, "o_0x21_long_press_shutter");
        put(d.support_camera_record_location, "o_0x22_support_location");
        put(d.support_camera_manual_function, "o_0x23_support_manual");
        put(d.is_camera_lower_qrscan_frequency, "o_0x24_support_qr_code");
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
