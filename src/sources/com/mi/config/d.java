package com.mi.config;

import com.android.camera.data.DataRepository;
import d.g.a;
import java.util.Collections;
import java.util.Map;

@Deprecated
/* compiled from: FeatureParserWrapper */
public class d {
    public static final String HIBERNATION_TIMEOUT = "camera_hibernation_timeout_in_minutes";
    public static final String VENDOR = "vendor";
    public static final String burst_shoot_count = "burst_shoot_count";
    public static final String camera_continuous_shot_callback_class = "camera_continuous_shot_callback_class";
    public static final String camera_continuous_shot_callback_setter = "camera_continuous_shot_callback_setter";
    public static final String camera_exposure_compensation_steps_num = "camera_exposure_compensation_steps_num";
    public static final String camera_extra_picture_size = "camera_extra_picture_size";
    public static final String camera_focus_success_flag = "camera_focus_success_flag";
    public static final String camera_front_count_down_margin = "camera_front_count_down_margin";
    public static final String camera_is_support_portrait_front = "camera_is_support_portrait_front";
    public static final String camera_reduce_preview_flag = "camera_reduce_preview_flag";
    public static final String camera_supported_ai_scene = "camera_supported_ai_scene";
    public static final String camera_supported_asd = "camera_supported_asd";
    public static final String camera_supported_scene = "camera_supported_scene";
    public static final String cmcc_strategic_phone = "cmcc_strategic_phone";
    public static final String enable_algorithm_in_file_suffix = "enable_algorithm_in_file_suffix";
    public static final String fp_nav_event_name_list = "fp_nav_event_name_list";
    public static final String front_fingerprint_sensor = "front_fingerprint_sensor";
    public static final String is_18x9_ratio_screen = "is_18x9_ratio_screen";
    public static final String is_camera_app_water_mark = "is_camera_app_water_mark";
    public static final String is_camera_face_detection_need_orientation = "is_camera_face_detection_need_orientation";
    public static final String is_camera_freeze_after_hdr_capture = "is_camera_freeze_after_hdr_capture";
    public static final String is_camera_hold_blur_background = "is_camera_hold_blur_background";
    public static final String is_camera_isp_rotated = "is_camera_isp_rotated";
    public static final String is_camera_lower_qrscan_frequency = "is_camera_lower_qrscan_frequency";
    public static final String is_camera_preview_with_subthread_looper = "is_camera_preview_with_subthread_looper";
    public static final String is_camera_replace_higher_cost_effect = "is_camera_replace_higher_cost_effect";
    public static final String is_camera_use_morpho_lib = "is_camera_use_morpho_lib";
    public static final String is_camera_use_still_effect_image = "is_camera_use_still_effect_image";
    public static final String is_capture_stop_face_detection = "is_capture_stop_face_detection";
    public static final String is_front_remosic_sensor = "is_front_remosic_sensor";
    public static final String is_front_video_quality_1080p = "is_front_video_quality_1080p";
    public static final String is_full_size_effect = "is_full_size_effect";
    public static final String is_hal_does_caf_when_flash_on = "is_hal_does_caf_when_flash_on";
    public static final String is_hongmi = "is_hongmi";
    public static final String is_hrf_video_capture_support = "is_hrf_video_capture_support";
    public static final String is_legacy_face_beauty = "is_legacy_face_beauty";
    public static final String is_lower_size_effect = "is_lower_size_effect";
    public static final String is_need_force_recycle_effect = "is_need_force_recycle_effect";
    public static final String is_new_hdr_param_key_used = "is_new_hdr_param_key_used";
    public static final String is_pad = "is_pad";
    public static final String is_rgb888_egl_prefer = "is_rgb888_egl_prefer";
    public static final String is_support_fhd_fhr = "is_support_fhd_fhr";
    public static final String is_support_optical_zoom = "is_support_optical_zoom";
    public static final String is_support_portrait = "is_support_portrait";
    public static final String is_support_stereo = "is_support_stereo";
    public static final String is_support_tele_asd_night = "is_support_tele_asd_night";
    public static final String is_surface_size_limit = "is_surface_size_limit";
    public static final String is_video_snapshot_size_limit = "is_video_snapshot_size_limit";
    public static final String is_xiaomi = "is_xiaomi";
    public static final String sensor_has_latency = "sensor_has_latency";
    public static final String support_3d_face_beauty = "support_3d_face_beauty";
    public static final String support_camera_4k_quality = "support_camera_4k_quality";
    public static final String support_camera_age_detection = "support_camera_age_detection";
    public static final String support_camera_aohdr = "support_camera_aohdr";
    public static final String support_camera_audio_focus = "support_camera_audio_focus";
    public static final String support_camera_boost_brightness = "support_camera_boost_brightness";
    public static final String support_camera_burst_shoot = "support_camera_burst_shoot";
    public static final String support_camera_burst_shoot_denoise = "support_camera_burst_shoot_denoise";
    public static final String support_camera_dynamic_light_spot = "support_camera_dynamic_light_spot";
    public static final String support_camera_face_info_water_mark = "support_camera_face_info_water_mark";
    public static final String support_camera_gradienter = "support_camera_gradienter";
    public static final String support_camera_groupshot = "support_camera_groupshot";
    public static final String support_camera_hfr = "support_camera_hfr";
    public static final String support_camera_magic_mirror = "support_camera_magic_mirror";
    public static final String support_camera_manual_function = "support_camera_manual_function";
    public static final String support_camera_movie_solid = "support_camera_movie_solid";
    public static final String support_camera_new_style_time_water_mark = "support_camera_new_style_time_water_mark";
    public static final String support_camera_peaking_mf = "support_camera_peaking_mf";
    public static final String support_camera_press_down_capture = "support_camera_press_down_capture";
    public static final String support_camera_quick_snap = "support_camera_quick_snap";
    public static final String support_camera_record_location = "support_camera_record_location";
    public static final String support_camera_role = "support_camera_role";
    public static final String support_camera_shader_effect = "support_camera_shader_effect";
    public static final String support_camera_skin_beauty = "support_camera_skin_beauty";
    public static final String support_camera_square_mode = "support_camera_square_mode";
    public static final String support_camera_tilt_shift = "support_camera_tilt_shift";
    public static final String support_camera_torch_capture = "support_camera_torch_capture";
    public static final String support_camera_ubifocus = "support_camera_ubifocus";
    public static final String support_camera_video_pause = "support_camera_video_pause";
    public static final String support_camera_water_mark = "support_camera_water_mark";
    public static final String support_chroma_flash = "support_chroma_flash";
    public static final String support_dual_sd_card = "support_dual_sd_card";
    public static final String support_edge_handgrip = "support_edge_handgrip";
    public static final String support_front_beauty_mfnr = "support_front_beauty_mfnr";
    public static final String support_front_flash = "support_front_flash";
    public static final String support_front_hht_enhance = "support_front_hht_enhance";
    public static final String support_full_size_panorama = "support_full_size_panorama";
    public static final String support_hfr_video_pause = "support_hfr_video_pause";
    public static final String support_lens_dirty_detect = "support_lens_dirty_detect";
    public static final String support_mi_face_beauty = "support_mi_face_beauty";
    public static final String support_object_track = "support_object_track";
    public static final String support_parallel_process = "support_parallel_process";
    public static final String support_picture_watermark = "support_picture_watermark";
    public static final String support_psensor_pocket_mode = "support_psensor_pocket_mode";
    public static final String support_realtime_manual_exposure_time = "support_realtime_manual_exposure_time";
    public static final String support_screen_light = "support_screen_light";
    public static final String support_super_resolution = "support_super_resolution";
    public static final String support_video_front_flash = "support_video_front_flash";
    public static final String support_video_hfr_mode = "support_video_hfr_mode";
    public static final String support_zoom_mfnr = "support_zoom_mfnr";
    public static final String use_legacy_normal_filter = "use_legacy_normal_filter";
    private static final Map<String, String> ux = Collections.unmodifiableMap(new FeatureParserWrapper$1());

    private static String X(String str) {
        return ux.get(str);
    }

    public static boolean getBoolean(String str, boolean z) {
        String X = X(str);
        return (X == null || !DataRepository.dataItemFeature().q(X)) ? a.getBoolean(str, z) : DataRepository.dataItemFeature().getBoolean(X, z);
    }

    public static Float getFloat(String str, float f2) {
        String X = X(str);
        return (X == null || !DataRepository.dataItemFeature().q(X)) ? a.getFloat(str, f2) : Float.valueOf(DataRepository.dataItemFeature().getFloat(X, f2));
    }

    public static int getInteger(String str, int i) {
        String X = X(str);
        return (X == null || !DataRepository.dataItemFeature().q(X)) ? a.getInteger(str, i) : DataRepository.dataItemFeature().getInt(X, i);
    }

    public static String getString(String str) {
        String X = X(str);
        return (X == null || !DataRepository.dataItemFeature().q(X)) ? a.getString(str) : DataRepository.dataItemFeature().getString(X, "N/A");
    }

    public static String[] getStringArray(String str) {
        return a.getStringArray(str);
    }
}
