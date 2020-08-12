package com.android.camera;

import a.c.a;
import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Camera;
import android.hardware.input.InputManager;
import android.media.MediaCodecInfo;
import android.media.MediaCodecList;
import android.os.SystemProperties;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Range;
import android.util.Size;
import android.util.TypedValue;
import com.android.camera.constant.AutoFocus;
import com.android.camera.constant.BeautyConstant;
import com.android.camera.constant.CameraScene;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.config.ComponentConfigBeauty;
import com.android.camera.data.data.config.ComponentConfigFilter;
import com.android.camera.data.data.config.ComponentConfigUltraWide;
import com.android.camera.data.data.config.ComponentConfigVideoQuality;
import com.android.camera.data.data.config.ComponentRunningUltraPixel;
import com.android.camera.data.data.global.DataItemGlobal;
import com.android.camera.data.data.runing.ComponentRunningShine;
import com.android.camera.data.data.runing.DataItemRunning;
import com.android.camera.data.provider.DataProvider;
import com.android.camera.effect.FilterInfo;
import com.android.camera.fragment.beauty.BeautyValues;
import com.android.camera.log.Log;
import com.android.camera.module.BaseModule;
import com.android.camera.module.ModuleManager;
import com.android.camera.module.loader.camera2.Camera2DataContainer;
import com.android.camera.module.loader.camera2.Camera2OpenManager;
import com.android.camera.preferences.CameraSettingPreferences;
import com.android.camera.statistic.MistatsConstants;
import com.android.camera2.CameraCapabilities;
import com.android.camera2.MiCustomFpsRange;
import com.mi.config.b;
import java.io.File;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import miui.reflect.c;

public class CameraSettings {
    public static final String AI_LENS_PACKAGE = "com.xiaomi.lens";
    public static final int APP_V10_SETTINGS_VERSION = 1;
    public static final int APP_V20_SETTINGS_VERSION = 3;
    public static final int APP_V30_SETTINGS_VERSION = 4;
    public static final int ASPECT_RATIO_16_9 = 1;
    public static final int ASPECT_RATIO_18_9 = 3;
    public static final int ASPECT_RATIO_1_1 = 2;
    public static final int ASPECT_RATIO_4_3 = 0;
    public static final int AUTO_FOCUS_POSITION = 1000;
    public static int BOTTOM_CONTROL_HEIGHT = 0;
    private static final String CAMERA_WATER_MARK_FILE_PREFIX = "/mnt/vendor/persist/camera/";
    public static final String CLOUD_APPMARKET_SERVER = "http://file.market.xiaomi.com/download/";
    public static final int CURRENT_VERSION = 4;
    public static final String DATA_CONFIG_RATIO_CINEMATIC = "is_cinematic";
    public static final String DATA_CONFIG_RATIO_SQUARE = "is_square";
    private static final boolean DEBUG_FAST_SHOT = SystemProperties.getBoolean("debug.vendor.camera.app.quickshot.enable", false);
    public static final int DEFAULT_VIDEO_DURATION = 0;
    private static final String DUAL_CAMERA_WATER_MARK_FILE = "/vendor/etc/camera/dualcamera.png";
    private static final String DUAL_CAMERA_WATER_MARK_FILE_FROM_VENDOR_INDIA = "/vendor/etc/camera/dualcamera_india.png";
    private static final String EDGE_HANDGRIP_MODE = "edge_handgrip";
    private static final String EDGE_HANDGRIP_MODE_BACK = "edge_handgrip_back";
    private static final String EDGE_HANDGRIP_MODE_CLEAN = "edge_handgrip_clean";
    private static final String EDGE_HANDGRIP_MODE_PHOTO = "edge_handgrip_photo";
    private static final String EDGE_HANDGRIP_MODE_SCREENSHOT = "edge_handgrip_screenshot";
    public static final String EXPOSURE_DEFAULT_VALUE = "0";
    public static final String GOOGLE_LENS_PACKAGE = "com.google.ar.lens";
    public static final String KEY_960_WATERMARK_STATUS = "pref_960_watermark_status";
    public static final String KEY_AE_AF_LOCK_SUPPORT = "pref_camera_ae_af_lock_support_key";
    public static final String KEY_AI_DETECT_ENABLE = "pref_camera_ai_detect_enable_key";
    public static final String KEY_AI_SCENE_MODE = "pref_camera_ai_scene_mode_key";
    public static final String KEY_AI_WATERMARK_KEY = "pref_ai_watermark_key";
    public static final String KEY_ANTIBANDING = "pref_camera_antibanding_key";
    public static final String KEY_AUTOEXPOSURE = "pref_camera_autoexposure_key";
    public static final String KEY_AUTO_CHROMA_FLASH = "pref_auto_chroma_flash_key";
    public static final String KEY_AUTO_ZOOM = "pref_camera_auto_zoom";
    public static final String KEY_BEAUTIFY_BLUSHER_RATIO = "pref_beautify_blusher_ratio_key";
    public static final String KEY_BEAUTIFY_CHIN_RATIO = "pref_beautify_chin_ratio_key";
    public static final String KEY_BEAUTIFY_ENLARGE_EYE = "pref_skin_beautify_enlarge_eye_key";
    public static final String KEY_BEAUTIFY_ENLARGE_EYE_RATIO = "pref_beautify_enlarge_eye_ratio_key";
    public static final String KEY_BEAUTIFY_EYEBROW_DYE_RATIO = "pref_beautify_eyebrow_dye_ratio_key";
    public static final String KEY_BEAUTIFY_HAIRLINE_RATIO = "pref_beautify_hairline_ratio_key";
    public static final String KEY_BEAUTIFY_JELLY_LIPS_RATIO = "pref_beautify_jelly_lips_ratio_key";
    public static final String KEY_BEAUTIFY_LEVEL_CAPTURE = "pref_beautify_level_key_capture";
    public static final String KEY_BEAUTIFY_LEVEL_VIDEO = "pref_beautify_level_key_video";
    public static final String KEY_BEAUTIFY_LIPS_RATIO = "pref_beautify_lips_ratio_key";
    public static final String KEY_BEAUTIFY_NECK_RATIO = "pref_beautify_neck_ratio_key";
    public static final String KEY_BEAUTIFY_NOSE_RATIO = "pref_beautify_nose_ratio_key";
    public static final String KEY_BEAUTIFY_PUPIL_LINE_RATIO = "pref_beautify_pupil_line_ratio_key";
    public static final String KEY_BEAUTIFY_RISORIUS_RATIO = "pref_beautify_risorius_ratio_key";
    public static final String KEY_BEAUTIFY_SKIN_COLOR = "pref_skin_beautify_skin_color_key";
    public static final String KEY_BEAUTIFY_SKIN_COLOR_RATIO = "pref_beautify_skin_color_ratio_key";
    public static final String KEY_BEAUTIFY_SKIN_SMOOTH = "pref_skin_beautify_skin_smooth_key";
    public static final String KEY_BEAUTIFY_SKIN_SMOOTH_RATIO = "pref_beautify_skin_smooth_ratio_key";
    public static final String KEY_BEAUTIFY_SLIM_FACE = "pref_skin_beautify_slim_face_key";
    public static final String KEY_BEAUTIFY_SLIM_FACE_RATIO = "pref_beautify_slim_face_ratio_key";
    public static final String KEY_BEAUTIFY_SLIM_NOSE_RATIO = "pref_beautify_slim_nose_ratio_key";
    public static final String KEY_BEAUTIFY_SMILE_RATIO = "pref_beautify_smile_ratio_key";
    public static final String KEY_BEAUTY_BODY_SLIM_RATIO = "pref_beauty_body_slim_ratio";
    public static final String KEY_BEAUTY_BUTT_SLIM_RATIO = "pref_beauty_butt_slim_ratio";
    public static final String KEY_BEAUTY_HEAD_SLIM_RATIO = "pref_beauty_head_slim_ratio";
    public static final String KEY_BEAUTY_LEG_SLIM_RATIO = "key_beauty_leg_slim_ratio";
    public static final String KEY_BEAUTY_MAKEUP_CLICKED = "pref_beauty_makeup_clicked_key";
    public static final String KEY_BEAUTY_REMODELING_CLICKED = "pref_beauty_remodeling_clicked_key";
    public static final String KEY_BEAUTY_SHOULDER_SLIM_RATIO = "pref_beauty_shoulder_slim_ratio";
    public static final String KEY_BEAUTY_WHOLE_BODY_SLIM_RATIO = "pref_beauty_whole_body_slim_ratio";
    public static final String KEY_BROADCAST_KILL_SERVICE_TIME = "pref_broadcast_kill_service_key";
    public static final String KEY_BURST_SHOOT = "pref_camera_burst_shooting_key";
    public static final String KEY_CAMERA_ASD_MOTION = "pref_camera_asd_motion_key";
    public static final String KEY_CAMERA_ASD_NIGHT = "pref_camera_asd_night_key";
    public static final String KEY_CAMERA_BOKEH = "pref_camera_bokeh_key";
    public static final String KEY_CAMERA_CONFIRM_LOCATION_SHOWN = "pref_camera_confirm_location_shown_key";
    public static final String KEY_CAMERA_DUAL_ENABLE = "pref_camera_dual_enable_key";
    public static final String KEY_CAMERA_DUAL_SAT_ENABLE = "pref_camera_dual_sat_enable_key";
    public static final String KEY_CAMERA_EXPOSURE_FEEDBACK = "pref_camera_exposure_feedback";
    public static final String KEY_CAMERA_FACE_DETECTION_AUTO_HIDDEN = "pref_camera_facedetection_auto_hidden_key";
    public static final String KEY_CAMERA_FIRST_AI_SCENE_USE_HINT_SHOWN = "pref_camera_first_ai_scene_use_hint_shown_key";
    public static final String KEY_CAMERA_FIRST_ID_CARD_MODE_USE_HINT_SHOWN = "pref_camera_first_id_card_mode_use_hint_shown_key";
    public static final String KEY_CAMERA_FIRST_MACRO_MODE_USE_HINT_SHOWN = "pref_camera_first_macro_mode_use_hint_shown_key";
    public static final String KEY_CAMERA_FIRST_NOTIFICATION_SHOWN = "pref_camera_first_notification_shown_key";
    public static final String KEY_CAMERA_FIRST_PORTRAIT_USE_HINT_SHOWN = "pref_camera_first_portrait_use_hint_shown_key";
    public static final String KEY_CAMERA_FIRST_ULTRA_TELE_USE_HINT_SHOWN = "pref_camera_first_ultra_tele_use_hint_shown_key";
    public static final String KEY_CAMERA_FIRST_ULTRA_WIDE_SAT_USE_HINT_SHOWN = "pref_camera_first_ultra_wide_sat_use_hint_shown_key";
    public static final String KEY_CAMERA_FIRST_ULTRA_WIDE_USE_HINT_SHOWN = "pref_camera_first_ultra_wide_use_hint_shown_key";
    public static final String KEY_CAMERA_FIRST_USE_HINT_SHOWN = "pref_camera_first_use_hint_shown_key";
    public static final String KEY_CAMERA_FIRST_USE_PERMISSION_SHOWN = "pref_camera_first_use_permission_shown_key";
    public static final String KEY_CAMERA_FIRST_VV_USE_HINT_SHOWN = "pref_camera_first_vv_hint_shown_key";
    public static final String KEY_CAMERA_FOCUS_MODE = "pref_camera_focus_mode_key";
    public static final String KEY_CAMERA_FROM_PRO_VIDEO_MODULE = "pref_camera_from_pro_video_module";
    public static final String KEY_CAMERA_FUN_VIDEO_QUALITY = "pref_camera_fun_video_quality";
    public static final String KEY_CAMERA_GRADIENTER_KEY = "pref_camera_gradienter_key";
    public static final String KEY_CAMERA_GROUPSHOT_MODE = "pref_camera_groupshot_mode_key";
    public static final String KEY_CAMERA_GROUPSHOT_PRIMITIVE = "pref_groupshot_with_primitive_picture_key";
    public static final String KEY_CAMERA_HAND_NIGHT = "pref_camera_hand_night_key";
    public static final String KEY_CAMERA_HDR = "pref_camera_hdr_key";
    public static final String KEY_CAMERA_HSR_VALUE = "pref_camera_hsr_value_key";
    public static final String KEY_CAMERA_ID = "pref_camera_id_key";
    public static final String KEY_CAMERA_LAB_OPTIONS_VISIBLE = "pref_camera_lab_option_key_visible";
    public static final String KEY_CAMERA_LAB_SUB_OPTIONS = "pref_camera_lab_option_key";
    public static final String KEY_CAMERA_LYING_TIP_SWITCH = "pref_camera_lying_tip_switch_key";
    public static final String KEY_CAMERA_MAGIC_MIRROR_KEY = "pref_camera_magic_mirror_key";
    public static final String KEY_CAMERA_MANUALLY_DESCRIPTION_TIP = "pref_camera_manual_description_tip";
    public static final String KEY_CAMERA_MANUALLY_LENS = "pref_camera_manually_lens";
    public static final String KEY_CAMERA_MANUAL_MODE = "pref_camera_manual_mode_key";
    public static final String KEY_CAMERA_MFNR_SAT_ENABLE = "pref_camera_mfnr_sat_enable_key";
    public static final String KEY_CAMERA_MODE_SETTINGS = "pref_camera_mode_settings_key";
    public static final String KEY_CAMERA_OBJECT_TRACK_HINT_SHOWN = "pref_camera_first_tap_screen_hint_shown_key";
    public static final String KEY_CAMERA_OPTIMIZED_FLASH_ENABLE = "pref_camera_optimized_flash_enable_key";
    public static final String KEY_CAMERA_PARALLEL_PROCESS_ENABLE = "pref_camera_parallel_process_enable_key";
    public static final String KEY_CAMERA_PEAKING_FOCUS = "pref_camera_peak_key";
    public static final String KEY_CAMERA_PORTRAIT_MODE = "pref_camera_portrait_mode_key";
    public static final String KEY_CAMERA_PORTRAIT_WITH_FACEBEAUTY = "pref_camera_portrait_with_facebeauty_key";
    public static final String KEY_CAMERA_PROXIMITY_LOCK = "pref_camera_proximity_lock_key";
    public static final String KEY_CAMERA_PRO_PHOTO_AUXILIARY = "pref_camera_pro_photo_auxiliary";
    public static final String KEY_CAMERA_PRO_VIDEO_AUXILIARY = "pref_camera_pro_video_auxiliary";
    public static final String KEY_CAMERA_PRO_VIDEO_HISTOGRAM = "pref_camera_pro_video_histogram";
    public static final String KEY_CAMERA_PRO_VIDEO_LENS = "pref_camera_pro_video_lens";
    public static final String KEY_CAMERA_PRO_VIDEO_LOG_FROMAT = "pref_camera_pro_video_log_format";
    public static final String KEY_CAMERA_PRO_VIDEO_MOVIE = "pref_camera_pro_video_movie";
    public static final String KEY_CAMERA_PRO_VIDEO_QUALITY = "pref_camera_pro_video_quality";
    public static final String KEY_CAMERA_QUICK_SHOT_ANIM_ENABLE = "pref_camera_quick_shot_anim_enable_key";
    public static final String KEY_CAMERA_QUICK_SHOT_ENABLE = "pref_camera_quick_shot_enable_key";
    public static final String KEY_CAMERA_SNAP = "pref_camera_snap_key";
    public static final String KEY_CAMERA_SOUND = "pref_camerasound_key";
    public static final String KEY_CAMERA_SQUARE_MODE = "pref_camera_square_mode_key";
    public static final String KEY_CAMERA_SR_ENABLE = "pref_camera_sr_enable_key";
    public static final String KEY_CAMERA_STEREO = "pref_camera_stereo_key";
    public static final String KEY_CAMERA_SUPER_NIGHT = "pref_camera_super_night_mode";
    public static final String KEY_CAMERA_SUPER_RESOLUTION = "pref_camera_super_resolution_key";
    public static final String KEY_CAMERA_TILT_SHIFT = "pref_camera_tilt_shift_key";
    public static final String KEY_CAMERA_TILT_SHIFT_MODE = "pref_camera_tilt_shift_mode";
    public static final String KEY_CAMERA_TOUCH_FOCUS_DELAY_ENABLE = "pref_camera_touch_focus_delay_key";
    public static final String KEY_CAMERA_VIDEO_8K = "pref_camera_video_8k";
    public static final String KEY_CAMERA_VIDEO_SAT_ENABLE = "pref_camera_video_sat_enable_key";
    public static final String KEY_CAMERA_ZOOM_MODE = "pref_camera_zoom_mode_key";
    public static final String KEY_CAPTURE_ULTRA_WIDE_MODE = "pref_ultra_wide_status";
    public static final String KEY_CAPTURE_WHEN_STABLE = "pref_capture_when_stable_key";
    public static final String KEY_CATEGORY_ADVANCE_SETTING = "category_advance_setting";
    public static final String KEY_CATEGORY_CAMCORDER_SETTING = "category_camcorder_setting";
    public static final String KEY_CATEGORY_CAPTURE_SETTING = "category_camera_setting";
    public static final String KEY_CATEGORY_DEVICE_SETTING = "category_device_setting";
    public static final String KEY_CATEGORY_QUICK_SETTING = "category_quick_setting";
    public static final String KEY_COLOR_EFFECT = "pref_camera_coloreffect_key";
    public static final String KEY_COLOR_ENHANCE = "pref_color_enhance";
    public static final String KEY_CUSTOM_WATERMARK = "pref_custom_watermark";
    public static final String KEY_DEBUG_INFO_AS_WATERMARK = "pref_camera_debug_info_as_watermark";
    public static final String KEY_DELAY_CAPTURE = "pref_delay_capture_key";
    public static final String KEY_DELAY_CAPTURE_MODE = "pref_delay_capture_mode";
    public static final String KEY_DEVICE_WATERMARK = "pref_dualcamera_watermark_key";
    public static final String KEY_DOCUMENT_MODE = "pref_document_mode_key";
    public static final String KEY_DOCUMENT_MODE_VALUE = "pref_document_mode_value_key";
    public static final String KEY_DUAL_VIDEO = "pref_dual_video_key";
    public static final String KEY_EN_FIRST_CHOICE_LOCK_AE_AF_TOAST = "pref_en_first_choice_lock_ae_af_toast_key";
    public static final String KEY_EXPOSURE = "pref_camera_exposure_key";
    public static final String KEY_EYE_LIGHT_TYPE = "pref_eye_light_type_key";
    public static final String KEY_FACE_BEAUTY_ADVANCED = "pref_camera_face_beauty_advanced_key";
    public static final String KEY_FACE_BEAUTY_MODE = "pref_camera_face_beauty_mode_key";
    public static final String KEY_FACE_BEAUTY_SWITCH = "pref_camera_face_beauty_switch_key";
    public static final String KEY_FACE_DETECTION = "pref_camera_facedetection_key";
    public static final String KEY_FACE_INFO_WATERMARK = "pref_face_info_watermark_key";
    public static final String KEY_FINGERPRINT_CAPTURE = "pref_fingerprint_capture_key";
    public static final String KEY_FLASH_MODE = "pref_camera_flashmode_key";
    public static final String KEY_FOCUS_SHOOT = "pref_camera_focus_shoot_key";
    public static final String KEY_FRONT_CAMERA_FIRST_USE_HINT_SHOWN = "pref_front_camera_first_use_hint_shown_key";
    public static final String KEY_FRONT_MIRROR = "pref_front_mirror_key";
    public static final String KEY_FUN_AR2_PHOTO_FLASH_MODE = "pref_camera_fun_ar_photo_flashmode_key";
    public static final String KEY_FUN_AR2_VIDEO_FLASH_MODE = "pref_camera_fun_ar_video_flashmode_key";
    public static final String KEY_FUN_AR_FLASH_MODE = "pref_camera_fun_ar_flashmode_key";
    public static final String KEY_F_NUMBER = "pref_f_number";
    public static final String KEY_GOOGLE_LENS_OOBE = "pref_google_lens_oobe_key";
    public static final String KEY_GOOGLE_LENS_SUGGESTIONS = "pref_google_lens_suggestions_key";
    public static final String KEY_HAND_GESTURE = "pref_hand_gesture";
    public static final String KEY_HAND_GESTURE_STATUS = "pref_hand_gesture_status";
    public static final String KEY_HEIC_FORMAT = "pref_camera_heic_image_format_key";
    public static final String KEY_JPEG_QUALITY = "pref_camera_jpegquality_key";
    public static final String KEY_KALEIDOSCOPE = "pref_kaleidoscope";
    public static final String KEY_LAST_SETTING_UPDATE_TIME = "key_last_setting_update_time";
    public static final String KEY_LENS_DIRTY_DETECT_DATE = "pref_lens_dirty_detect_date_key";
    public static final String KEY_LENS_DIRTY_DETECT_ENABLED = "pref_lens_dirty_detect_enabled_key";
    public static final String KEY_LENS_DIRTY_DETECT_TIMES = "pref_lens_dirty_detect_times_key";
    public static final String KEY_LENS_DIRTY_TIP = "pref_lens_dirty_tip";
    public static final String KEY_LIVE_ALL_SWITCH_ADD_VALUE = "pref_live_all_switch_add_value";
    public static final String KEY_LIVE_BEAUTY_STATUS = "pref_live_beauty_status";
    public static final String KEY_LIVE_ENLARGE_EYE_RATIO = "key_live_enlarge_eye_ratio";
    public static final String KEY_LIVE_FILTER = "key_live_filter";
    public static final String KEY_LIVE_MODULE_CLICKED = "pref_live_module_clicked";
    public static final String KEY_LIVE_MUSIC_FIRST_REQUEST_TIME = "pref_key_live_music_first_request_time";
    public static final String KEY_LIVE_MUSIC_HINT = "pref_live_music_hint_key";
    public static final String KEY_LIVE_MUSIC_PATH = "pref_live_music_path_key";
    public static final String KEY_LIVE_RECORD_IS_SELECTED_BGM = "pref_key_live_record_is_selected_bgm";
    public static final String KEY_LIVE_SHOT = "pref_live_shot_enabled";
    public static final String KEY_LIVE_SHRINK_FACE_RATIO = "key_live_shrink_face_ratio";
    public static final String KEY_LIVE_SMOOTH_STRENGTH = "key_live_smooth_strength";
    public static final String KEY_LIVE_SPEED = "pref_live_speed_key";
    public static final String KEY_LIVE_STICKER = "pref_live_sticker_key";
    public static final String KEY_LIVE_STICKER_HINT = "pref_live_sticker_hint_key";
    public static final String KEY_LIVE_STICKER_INTERNAL = "pref_camera_live_sticker_internal";
    public static final String KEY_LIVE_STICKER_LAST_CACHE_TIME = "pref_live_sticker_last_cache_time";
    public static final String KEY_LIVE_STICKER_NAME = "pref_live_sticker_name_key";
    public static final String KEY_LIVE_STICKER_NEED_RED_DOT = "pref_live_sticker_need_red_dot";
    public static final String KEY_LIVE_STICKER_RED_DOT_TIME = "pref_live_sticker_red_dot_time";
    public static final String KEY_LOCALWORDS_VERSION = "pref_localwords_version";
    public static final String KEY_LONG_PRESS_SHUTTER = "pref_camera_long_press_shutter_key";
    public static final String KEY_LONG_PRESS_SHUTTER_FEATURE = "pref_camera_long_press_shutter_feature_key";
    public static final String KEY_LONG_PRESS_VIEWFINDER = "pref_camera_long_press_viewfinder_key";
    public static final String KEY_MACRO_SCENE_MODE = "pref_camera_macro_scene_mode_key";
    public static final String KEY_MIMOJI_INDEX = "pref_mimoji_index";
    public static final String KEY_MIMOJI_MODEL_VERSION = "pref_mimoji_model_verion";
    public static final String KEY_MIMOJI_OPERATE = "pref_mimoji_operate";
    public static final String KEY_MIMOJI_PANNEL_STATE = "pref_mimoji_pannel_state";
    public static final String KEY_MI_LIVE_QUALITY = "pref_mi_live_quality";
    public static final String KEY_MOVIE_SOLID = "pref_camera_movie_solid_key";
    public static final String KEY_NORMAL_WIDE_LDC = "pref_camera_normal_wide_ldc_key";
    public static final String KEY_OPEN_CAMERA_FAIL = "open_camera_fail_key";
    public static final String KEY_PANORAMA_MODE = "pref_camera_panoramamode_key";
    public static final String KEY_PANORAMA_MOVE_DIRECTION = "pref_panorana_move_direction_key";
    public static final String KEY_PHOTO_ASSISTANCE_TIPS = "pref_photo_assistance_tips";
    public static final String KEY_PICTURE_FLAW_TIP = "pref_pic_flaw_tip";
    public static final String KEY_PICTURE_SIZE = "pref_camera_picturesize_key";
    private static final String KEY_PLAY_TONE_ON_CAPTURE_START = "pref_play_tone_on_capture_start_key";
    public static final String KEY_POPUP_TIP_BEAUTY_INTRO_CLICKED = "pref_popup_tip_beauty_intro_clicked_key";
    public static final String KEY_POPUP_TIP_BEAUTY_INTRO_SHOW_TIMES = "pref_popup_tip_beauty_intro_show_times_key";
    public static final String KEY_POPUP_ULTRA_WIDE_INTRO_SHOW_TIMES = "pre_popup_ultra_wide_intro_show_times";
    public static final String KEY_PORTRAIT_FLASH_MODE = "pref_camera_portrait_flashmode_key";
    public static final String KEY_PORTRAIT_HDR = "pref_portrait_hdr_key";
    public static final String KEY_PORTRAIT_LIGHTING = "pref_portrait_lighting";
    public static final String KEY_PRIORITY_STORAGE = "pref_priority_storage";
    public static final String KEY_PRO_VIDEO_AUTOEXPOSURE = "pref_pro_video_autoexposure_key";
    public static final String KEY_PRO_VIDEO_WHITE_BALANCE = "pref_camera_video_whitebalance_key";
    public static final String KEY_QC_CONTRAST = "pref_qc_camera_contrast_key";
    public static final String KEY_QC_EXPOSURETIME = "pref_qc_camera_exposuretime_key";
    public static final String KEY_QC_FOCUS_MODE_SWITCHING = "pref_qc_focus_mode_switching_key";
    public static final String KEY_QC_FOCUS_POSITION = "pref_focus_position_key";
    public static final String KEY_QC_ISO = "pref_qc_camera_iso_key";
    public static final String KEY_QC_MANUAL_EXPOSURE_VALUE = "pref_qc_camera_manual_exposure_value_key";
    public static final String KEY_QC_MANUAL_WHITEBALANCE_VALUE = "pref_qc_manual_whitebalance_k_value_key";
    public static final String KEY_QC_PRO_VIDEO_EXPOSURETIME = "pref_qc_camera_pro_video_exposuretime_key";
    public static final String KEY_QC_PRO_VIDEO_EXPOSURE_VALUE = "pref_qc_camera_pro_video_exposure_value_key";
    public static final String KEY_QC_PRO_VIDEO_FOCUS_POSITION = "pref_pro_video_focus_position_key";
    public static final String KEY_QC_PRO_VIDEO_ISO = "pref_qc_pro_video_camera_iso_key";
    public static final String KEY_QC_PRO_VIDEO_WHITEBALANCE_VALUE = "pref_qc_pro_video_whitebalance_k_value_key";
    public static final String KEY_QC_SATURATION = "pref_qc_camera_saturation_key";
    public static final String KEY_QC_SHARPNESS = "pref_qc_camera_sharpness_key";
    public static final String KEY_RAW = "pref_camera_raw_key";
    public static final String KEY_RECORD_LOCATION = "pref_camera_recordlocation_key";
    public static final String KEY_REFERENCE_LINE = "pref_camera_referenceline_key";
    public static final String KEY_RESTORED_FLASH_MODE = "pref_camera_restored_flashmode_key";
    public static final String KEY_RETAIN_CAMERA_MODE = "pref_retain_camera_mode_key";
    public static final String KEY_SCAN_QRCODE = "pref_scan_qrcode_key";
    public static final String KEY_SCENE_MODE = "pref_camera_scenemode_key";
    public static final String KEY_SCENE_MODE_SETTINGS = "pref_camera_scenemode_setting_key";
    public static final String KEY_SHADER_COLOR_EFFECT = "pref_camera_shader_coloreffect_key";
    public static final String KEY_SHOW_GENDER_AGE = "pref_camera_show_gender_age_key";
    public static final String KEY_SMART_SHUTTER_POSITION = "pref_key_camera_smart_shutter_position";
    public static final String KEY_STICKER_PATH = "pref_sticker_path_key";
    public static final String KEY_SUPER_EIS = "pref_camera_super_eis";
    public static final String KEY_TIME_WATERMARK = "pref_time_watermark_key";
    public static final String KEY_TOUCH_AF_AEC = "pref_camera_touchafaec_key";
    public static final String KEY_TT_LIVE_MUSIC_JSON_CACHE = "pref_key_tt_live_music_json_cache";
    public static final String KEY_TT_LIVE_STICKER_JSON_CACHE = "pref_key_tt_live_sticker_json_cache";
    public static final String KEY_UBIFOCUS_KEY = "pref_camera_ubifocus_key";
    public static final String KEY_ULTRA_PIXEL = "pref_ultra_pixel";
    public static final String KEY_ULTRA_PIXEL_PHOTOGRAPHY = "pref_ultra_pixel_photography_enabled";
    public static final String KEY_ULTRA_PIXEL_PORTRAIT = "pref_camera_ultra_pixel_portrait_mode_key";
    public static final String KEY_ULTRA_WIDE_BOKEH = "pref_ultra_wide_bokeh_enabled";
    public static final String KEY_ULTRA_WIDE_LDC = "pref_camera_ultra_wide_ldc_key";
    public static final String KEY_ULTRA_WIDE_VIDEO_LDC = "pref_camera_ultra_wide_video_ldc_key";
    public static final String KEY_USERDEFINE_WATERMARK = "user_define_watermark_key";
    public static final String KEY_VERSION = "pref_version_key";
    public static final String KEY_VIDEOCAMERA_FLASH_MODE = "pref_camera_video_flashmode_key";
    public static final String KEY_VIDEO_AUTOEXPOSURE = "pref_video_autoexposure_key";
    public static final String KEY_VIDEO_BEAUTY_ADJUST = "pref_video_beauty_adjust_key";
    public static final String KEY_VIDEO_BOKEH = "pref_video_bokeh_key";
    public static final String KEY_VIDEO_BOKEH_ADJUST = "pref_video_bokeh_adjust_key";
    public static final String KEY_VIDEO_ENCODER = "pref_video_encoder_key";
    public static final String KEY_VIDEO_HDR = "pref_video_hdr_key";
    public static final int KEY_VIDEO_HSR_60 = 60;
    public static final String KEY_VIDEO_NEW_SLOW_MOTION = "pref_video_new_slow_motion_key";
    public static final String KEY_VIDEO_QUALITY = "pref_video_quality_key";
    public static final String KEY_VIDEO_SPEED = "pref_video_speed_key";
    public static final String KEY_VIDEO_SPEED_FAST = "pref_video_speed_fast_key";
    public static final String KEY_VIDEO_SUBTITLE = "pref_video_subtitle_key";
    public static final String KEY_VIDEO_SUBTITLE_INTERNET = "pref_video_subtitle_use_internet_key";
    public static final String KEY_VIDEO_TAG = "pref_camera_video_tag_key";
    public static final String KEY_VIDEO_TIME_LAPSE_FRAME_INTERVAL = "pref_video_time_lapse_frame_interval_key";
    public static final String KEY_VOLUME_CAMERA_FUNCTION = "pref_camera_volumekey_function_key";
    public static final String KEY_VOLUME_LIVE_FUNCTION = "pref_live_volumekey_function_key";
    public static final String KEY_VOLUME_PRO_VIDEO_FUNCTION = "pref_pro_video_volumekey_function_key";
    public static final String KEY_VOLUME_VIDEO_FUNCTION = "pref_video_volumekey_function_key";
    public static final String KEY_WATERMARK = "pref_watermark_key";
    public static final String KEY_WHITE_BALANCE = "pref_camera_whitebalance_key";
    public static final String KEY_ZOOM = "pref_camera_zoom_key";
    public static final int LIVE_SPEED_DEFAULT = 2;
    private static final int MAX_PREVIEW_FPS_TIMES_1000 = 400000;
    private static final int PREFERRED_PREVIEW_FPS_TIMES_1000 = 30000;
    public static final String PROP_AUTO_ZOOM = "camera.autozoom.enable";
    public static final String QRCODE_RECEIVER_PACKAGE = "com.xiaomi.scanner";
    public static final int QUALITY_MTK_FINE = 11;
    public static final int QUALITY_MTK_HIGH = 10;
    public static final int QUALITY_MTK_MEDIUM = 9;
    public static final String REMIND_SUFFIX = "_remind";
    public static final String SIZE_FPS_1080_240 = "1920x1080:240";
    public static final int SURFACE_LEFT_MARGIN_MDP_QUALITY_480P = CameraAppImpl.getAndroidContext().getResources().getDimensionPixelSize(R.dimen.v6_surface_view_left_margin_mdp_render_quality_480p);
    public static final int SURFACE_LEFT_MARGIN_MDP_QUALITY_LOW = CameraAppImpl.getAndroidContext().getResources().getDimensionPixelSize(R.dimen.v6_surface_view_left_margin_mdp_render_quality_low);
    private static final String TAG = "CameraSettings";
    public static final int TOP_CONTROL_HEIGHT = CameraAppImpl.getAndroidContext().getResources().getDimensionPixelSize(R.dimen.bottom_control_upper_panel_height);
    public static final int UI_STYLE_16_9 = 1;
    public static final int UI_STYLE_1_1 = 4;
    public static final int UI_STYLE_3_2 = 2;
    public static final int UI_STYLE_4_3 = 0;
    public static final int UI_STYLE_ERROR = -1;
    public static final int UI_STYLE_FULL_SCREEN = 3;
    public static final String VIDEO_MODE_120 = "fps120";
    public static final String VIDEO_MODE_240 = "fps240";
    public static final String VIDEO_MODE_960 = "fps960";
    public static final String VIDEO_MODE_FUN = "fun";
    public static final String VIDEO_MODE_LIVE = "live";
    public static final String VIDEO_SPEED_FAST = "fast";
    public static final String VIDEO_SPEED_NORMAL = "normal";
    public static final ChangeManager sCameraChangeManager = new ChangeManager();
    public static boolean sCroppedIfNeeded;
    private static boolean sEdgePhotoEnable;
    private static boolean sGoolgeLensAvilability;
    public static int sLensIndex = 0;
    public static final int[] sLiveSpeedTextList = {R.string.live_speed_extreme_slow, R.string.live_speed_slow, R.string.live_speed_normal, R.string.live_speed_fast, R.string.live_speed_extreme_fast};
    public static boolean sMacro2Sat = false;
    public static ArrayList<String> sRemindMode = new ArrayList<>();
    private static HashMap<String, String> sSceneToFlash = new HashMap<>(11);

    @Retention(RetentionPolicy.SOURCE)
    public @interface UiStyle {
    }

    static {
        sSceneToFlash.put("0", null);
        sSceneToFlash.put("3", null);
        sSceneToFlash.put("4", "0");
        sSceneToFlash.put("13", null);
        sSceneToFlash.put("5", "0");
        sSceneToFlash.put("6", "1");
        sSceneToFlash.put("8", "0");
        sSceneToFlash.put("9", "0");
        sSceneToFlash.put("10", "0");
        sSceneToFlash.put("12", "0");
        sSceneToFlash.put(CameraScene.BACKLIGHT, "0");
        sSceneToFlash.put(CameraScene.FLOWERS, "0");
        sRemindMode.add(KEY_CAMERA_MODE_SETTINGS);
        sRemindMode.add("pref_camera_magic_mirror_key");
        if (b.Gl()) {
            sRemindMode.add("pref_camera_groupshot_mode_key");
        }
    }

    public static void addLensDirtyDetectedTimes() {
        DataProvider.ProviderEditor editor = DataRepository.dataNormalItemConfig().editor();
        editor.putBoolean(KEY_LENS_DIRTY_DETECT_ENABLED, false);
        long currentTimeMillis = System.currentTimeMillis();
        Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(currentTimeMillis);
        Calendar instance2 = Calendar.getInstance();
        instance2.setTimeInMillis(DataRepository.dataNormalItemConfig().getLong(KEY_LENS_DIRTY_DETECT_DATE, currentTimeMillis));
        int i = 1;
        if (instance.get(1) == instance2.get(1) && instance.get(2) == instance2.get(2) && instance.get(5) == instance2.get(5)) {
            i = 1 + DataRepository.dataNormalItemConfig().getInt(KEY_LENS_DIRTY_DETECT_TIMES, 0);
        }
        editor.putInt(KEY_LENS_DIRTY_DETECT_TIMES, i);
        editor.putLong(KEY_LENS_DIRTY_DETECT_DATE, currentTimeMillis);
        editor.apply();
        Log.i(TAG, "add lens dirty detected times: " + i);
    }

    public static void addPopupTipBeautyIntroShowTimes() {
        DataRepository.dataItemGlobal().editor().putInt(KEY_POPUP_TIP_BEAUTY_INTRO_SHOW_TIMES, DataRepository.dataItemGlobal().getInt(KEY_POPUP_TIP_BEAUTY_INTRO_SHOW_TIMES, 0) + 1).apply();
    }

    public static void addPopupUltraWideIntroShowTimes() {
        DataRepository.dataItemGlobal().editor().putInt(KEY_POPUP_ULTRA_WIDE_INTRO_SHOW_TIMES, DataRepository.dataItemGlobal().getInt(KEY_POPUP_ULTRA_WIDE_INTRO_SHOW_TIMES, 0) + 1).apply();
    }

    public static void cancelRemind(String str) {
        if (isNeedRemind(str)) {
            DataProvider.ProviderEditor editor = DataRepository.dataItemGlobal().editor();
            editor.putBoolean(str + REMIND_SUFFIX, false);
            editor.apply();
        }
    }

    public static boolean checkLensAvailability(Context context) {
        return !Util.isGlobalVersion() ? DataRepository.dataItemFeature().s_m_l() && Util.isPackageAvailable(context, AI_LENS_PACKAGE) : DataRepository.dataItemGlobal().getBoolean(KEY_AI_DETECT_ENABLE, Boolean.valueOf(getString(R.bool.pref_ai_detect_enable)).booleanValue()) && Util.isPackageAvailable(context, "com.google.ar.lens") && sGoolgeLensAvilability;
    }

    public static int dealVideoQuality(String str, int i, int i2) {
        if (str == null) {
            return Integer.parseInt("5");
        }
        boolean z = isUltraWideConfigOpen(i2) || i == Camera2DataContainer.getInstance().getUltraWideCameraId();
        int indexOf = str.indexOf(",");
        String str2 = null;
        if (indexOf > 0) {
            String substring = str.substring(0, indexOf);
            str2 = str.substring(indexOf + 1);
            str = substring;
        }
        setHSRValue(z, str2);
        return Integer.parseInt(str);
    }

    private static void deleteObsoletePreferences() {
        Context androidContext = CameraAppImpl.getAndroidContext();
        int[] iArr = {0, 1};
        String str = "/data/data/" + androidContext.getPackageName().toString() + "/shared_prefs";
        int[] iArr2 = BaseModule.CAMERA_MODES;
        for (int i : iArr2) {
            if (i != 0) {
                for (int i2 : iArr) {
                    File file = new File(str, ("camera_settings_simple_mode_local_" + BaseModule.getPreferencesLocalId(i2, i)) + ".xml");
                    if (file.exists()) {
                        file.delete();
                    }
                }
            }
        }
        File file2 = new File(str, "camera_settings_simple_mode_global.xml");
        if (file2.exists()) {
            file2.delete();
        }
    }

    private static void filterPreference(Map<String, ?> map, String str, SharedPreferences.Editor editor, int i) {
        Object obj;
        if (editor != null && !TextUtils.isEmpty(str) && i != 0 && (obj = map.get(str)) != null && !Util.isStringValueContained(obj, i)) {
            editor.remove(str);
        }
    }

    public static int get4kProfile() {
        if (!b.nm()) {
            return -1;
        }
        return Integer.parseInt(getString(R.string.pref_video_quality_entry_value_4kuhd));
    }

    public static int get8kProfile() {
        if (!b.nm()) {
            return -1;
        }
        return Integer.parseInt(getString(R.string.pref_video_quality_entry_value_8kuhd));
    }

    public static boolean getAiSceneOpen(int i) {
        return DataRepository.dataItemConfig().getComponentConfigAi().isAiSceneOn(i);
    }

    public static String getAntiBanding() {
        return DataRepository.dataItemGlobal().getString("pref_camera_antibanding_key", getString(getDefaultPreferenceId(R.string.pref_camera_antibanding_default)));
    }

    public static int getAspectRatio(int i, int i2) {
        if (isNearRatio16_9(i, i2)) {
            return 1;
        }
        return isNearRatio18_9(i, i2) ? 3 : 0;
    }

    @Deprecated
    public static int[] getBeautifyValueRange() {
        return b.isMTKPlatform() ? new int[]{-12, 12} : new int[]{0, 10};
    }

    public static int getBeautyShowLevel() {
        return BeautyConstant.getLevelInteger(getFaceBeautifyLevel());
    }

    public static int getBogusCameraId() {
        return DataRepository.dataItemGlobal().getCurrentCameraId();
    }

    public static boolean getBool(int i) {
        return CameraAppImpl.getAndroidContext().getResources().getBoolean(i);
    }

    public static long getBroadcastKillServiceTime() {
        return DataRepository.dataItemGlobal().getLong(KEY_BROADCAST_KILL_SERVICE_TIME, 0);
    }

    public static int getCameraId() {
        return Camera2DataContainer.getInstance().getActualOpenCameraId(DataRepository.dataItemGlobal().getCurrentCameraId(), DataRepository.dataItemGlobal().getCurrentMode());
    }

    public static int getCameraId(int i) {
        return Camera2DataContainer.getInstance().getActualOpenCameraId(DataRepository.dataItemGlobal().getCurrentCameraId(), i);
    }

    public static String getCameraLensType(int i) {
        return DataRepository.dataItemConfig().getManuallyDualLens().getComponentValue(i);
    }

    public static boolean getCameraProximityLockSettingNeedRemove(int i, boolean z) {
        return b.cv || !ProximitySensorLock.supported();
    }

    public static boolean getCameraSnapSettingNeedRemove(int i, boolean z) {
        return z || isInAllRecordModeSet(i) || b.cv || !b.em();
    }

    public static String getColorEffect() {
        return DataRepository.dataItemConfig().getString(KEY_COLOR_EFFECT, getString(R.string.pref_camera_coloreffect_default));
    }

    public static String getContrast() {
        String string = getString(R.string.pref_camera_contrast_default);
        String string2 = DataRepository.dataItemGlobal().getString("pref_qc_camera_contrast_key", string);
        if (string.equals(string2) || Util.isStringValueContained(string2, (int) R.array.pref_camera_contrast_entryvalues)) {
            return string2;
        }
        Log.e(TAG, "reset invalid contrast " + string2);
        resetContrast();
        return string;
    }

    public static int getCountDownTimes() {
        return DataRepository.dataItemRunning().getComponentRunningTimer().getTimer();
    }

    public static String[] getCurrentLiveMusic() {
        return new String[]{DataRepository.dataItemLive().getString(KEY_LIVE_MUSIC_PATH, ""), DataRepository.dataItemLive().getString(KEY_LIVE_MUSIC_HINT, "")};
    }

    public static String getCurrentLiveSpeed() {
        return DataRepository.dataItemLive().getString(KEY_LIVE_SPEED, String.valueOf(2));
    }

    public static String getCurrentLiveSpeedText() {
        return getString(sLiveSpeedTextList[Integer.valueOf(getCurrentLiveSpeed()).intValue()]);
    }

    public static String getCurrentLiveSticker() {
        return DataRepository.dataItemLive().getString(KEY_LIVE_STICKER, "");
    }

    public static String getCurrentLiveStickerHint() {
        return DataRepository.dataItemLive().getString(KEY_LIVE_STICKER_HINT, "");
    }

    public static String getCurrentLiveStickerName() {
        return DataRepository.dataItemLive().getString(KEY_LIVE_STICKER_NAME, "");
    }

    public static int getCustomWB() {
        return DataRepository.dataItemConfig().getInt("pref_qc_manual_whitebalance_k_value_key", CameraAppImpl.getAndroidContext().getResources().getInteger(R.integer.default_manual_whitebalance_value));
    }

    public static boolean getCustomWaterMarkState(int i, boolean z) {
        if (i == 177 || i == 184) {
            return true;
        }
        return (z && !DataRepository.dataItemFeature().c_0x46()) || !DataRepository.dataItemFeature().s_c_w_m();
    }

    public static String getCustomWatermark() {
        return DataRepository.dataItemGlobal().getString(KEY_CUSTOM_WATERMARK, getDefaultWatermarkStr());
    }

    public static String getCustomWatermarkDefault() {
        return isUltraPixelRearOn() ? DataRepository.dataItemRunning().getComponentUltraPixel().isRearSwitchOn() ? getString(R.string.device_ultra_pixel_watermark_default_text) : getString(R.string.device_watermark_default_text) : getDefaultWatermarkStr();
    }

    public static String getDefaultFNumber() {
        return DataRepository.dataItemFeature().s(isFrontCamera());
    }

    public static int getDefaultPreferenceId(int i) {
        if (i != R.bool.pref_camera_auto_chroma_flash_default) {
            if (i != R.string.pref_camera_antibanding_default) {
                if (i == R.string.pref_video_quality_default) {
                    if (b.gl() && isFrontCamera()) {
                        return R.string.pref_mi_front_video_quality_default;
                    }
                    if (b.gv) {
                        return R.string.pref_video_quality_1080P;
                    }
                }
            } else if (Util.isAntibanding60()) {
                return R.string.pref_camera_antibanding_entryvalue_60hz;
            }
        } else if (b.Ju || b.Ku) {
            return R.bool.pref_camera_auto_chroma_flash_virgo_default;
        }
        return i;
    }

    public static String getDefaultWatermarkStr() {
        return b.ol() ? getString(R.string.device_india_watermark_default_text) : getString(R.string.device_watermark_default_text);
    }

    public static final String getDualCameraWaterMarkFilePathVendor() {
        return getDualCameraWaterMarkFilePathVendor(false);
    }

    public static final String getDualCameraWaterMarkFilePathVendor(boolean z) {
        if (b.Nu && "india".equalsIgnoreCase(SystemProperties.get("ro.boot.hwc"))) {
            return DUAL_CAMERA_WATER_MARK_FILE_FROM_VENDOR_INDIA;
        }
        if (!DataRepository.dataItemFeature().c_0x4a()) {
            return DUAL_CAMERA_WATER_MARK_FILE;
        }
        if (z) {
            return "/mnt/vendor/persist/camera/" + Util.WATERMARK_ULTRA_PIXEL_FILE_NAME;
        }
        return "/mnt/vendor/persist/camera/" + Util.getDefaultWatermarkFileName();
    }

    public static boolean getDualCameraWaterMarkState(int i, boolean z) {
        return (z && !DataRepository.dataItemFeature().c_0x46()) || !b.rm();
    }

    public static EncodingQuality getEncodingQuality(boolean z) {
        EncodingQuality enumOf = EncodingQuality.enumOf(DataRepository.dataItemConfig().getString("pref_camera_jpegquality_key", CameraAppImpl.getAndroidContext().getResources().getString(R.string.pref_camera_jpegquality_default)));
        EncodingQuality encodingQuality = z ? EncodingQuality.NORMAL : EncodingQuality.HIGH;
        return (enumOf == null || enumOf.ordinal() > encodingQuality.ordinal()) ? encodingQuality : enumOf;
    }

    public static int getExposureMeteringMode() {
        String str;
        if (ModuleManager.isProModule()) {
            str = DataRepository.dataItemConfig().getComponentConfigMeter().getComponentValue(DataRepository.dataItemGlobal().getCurrentMode());
        } else {
            str = getString(R.string.pref_camera_autoexposure_default);
        }
        return Util.parseInt(str, 1);
    }

    public static long getExposureTime() {
        CameraSettingPreferences instance = CameraSettingPreferences.instance();
        String string = getString(R.string.pref_camera_exposuretime_default);
        if (ModuleManager.isProModule()) {
            string = instance.getString(KEY_QC_EXPOSURETIME, string);
        }
        return Long.parseLong(string);
    }

    public static String getEyeLightType() {
        return DataRepository.dataItemRunning().getComponentRunningEyeLight().getComponentValue(160);
    }

    public static String getFaceBeautifyLevel() {
        if (!b.hm()) {
            return BeautyConstant.LEVEL_CLOSE;
        }
        int faceBeautyRatio = getFaceBeautyRatio("pref_beautify_level_key_capture");
        return BeautyConstant.PREFIX_BEAUTY_LEVEL + faceBeautyRatio;
    }

    public static int getFaceBeautyRatio(String str) {
        int defaultValueByKey = BeautyConstant.getDefaultValueByKey(str);
        if (BeautyConstant.isLiveBeautyModeKey(str)) {
            return DataRepository.dataItemLive().getInt(BeautyConstant.wrappedSettingKey(str), defaultValueByKey);
        }
        if (!"pref_beautify_skin_smooth_ratio_key".equals(str) || !ModuleManager.isVideoModule()) {
            return DataRepository.dataItemConfig().getInt(BeautyConstant.wrappedSettingKey(str), defaultValueByKey);
        }
        return DataRepository.dataItemRunning().getInt(BeautyConstant.wrappedSettingKey(str), defaultValueByKey);
    }

    public static boolean getFingerprintCaptureSettingNeedRemove(int i, boolean z) {
        return !b.Yk() || i == 177 || i == 184;
    }

    public static String getFlashMode(int i) {
        return DataRepository.dataItemConfig().getComponentFlash().getComponentValue(i);
    }

    public static String getFlashModeByScene(String str) {
        return sSceneToFlash.get(str);
    }

    public static String getFocusMode() {
        return ModuleManager.isProModule() ? getMappingFocusMode(getFocusPosition()) : (ModuleManager.isVideoModule() || ModuleManager.isFunModule() || ModuleManager.isMiLiveModule()) ? getString(R.string.pref_video_focusmode_entryvalue_default) : getString(R.string.pref_camera_focusmode_value_default);
    }

    public static int getFocusPosition() {
        return Integer.parseInt(DataRepository.dataItemConfig().getManuallyFocus().getComponentValue(ModuleManager.getActiveModuleIndex()));
    }

    public static boolean getFocusShootSettingNeedRemove(int i, boolean z) {
        return i == 177 || i == 184 || isInAllRecordModeSet(i);
    }

    public static boolean getGoogleLensSuggestionsSettingNeedRemove(int i, boolean z) {
        return z || !b.Te() || isInAllRecordModeSet(i);
    }

    public static int getHSRIntegerValue() {
        String hSRValue = getHSRValue(DataRepository.dataItemGlobal().getCurrentCameraId() == Camera2DataContainer.getInstance().getUltraWideCameraId());
        if (hSRValue == null || hSRValue.isEmpty() || hSRValue.equals("off")) {
            return 0;
        }
        return Integer.parseInt(hSRValue);
    }

    public static String getHSRValue(boolean z) {
        return DataRepository.dataItemConfig().getString(z ? "pref_camera_hsr_value_key_u" : KEY_CAMERA_HSR_VALUE, null);
    }

    public static String getKeyCloudSenstiveWordsPath(String str) {
        return CLOUD_APPMARKET_SERVER + str + "/sensi_words.txt";
    }

    public static int getLensIndex() {
        return sLensIndex;
    }

    public static int getLiveAllSwitchAllValue() {
        return DataRepository.dataItemLive().getInt(KEY_LIVE_ALL_SWITCH_ADD_VALUE, 0);
    }

    public static long getLiveStickerLastCacheTime() {
        return DataRepository.dataItemGlobal().getLong(KEY_LIVE_STICKER_LAST_CACHE_TIME, 0);
    }

    public static long getLiveStickerRedDotTime() {
        return DataRepository.dataItemGlobal().getLong(KEY_LIVE_STICKER_RED_DOT_TIME, 0);
    }

    public static int getLocalWordsVersion() {
        return DataRepository.dataItemGlobal().getInt(KEY_LOCALWORDS_VERSION, 0);
    }

    public static boolean getLongPressShutterSettingNeedRemove(int i, boolean z) {
        return z || b.cv || !b.Xl() || isInAllRecordModeSet(i);
    }

    public static boolean getLongPressViewFinderSettingNeedRemove(int i, boolean z, boolean z2) {
        return !b.Te() || z2 || isInAllRecordModeSet(i);
    }

    public static String getManualFocusName(Context context, int i) {
        if (i == 1000) {
            return context.getString(R.string.pref_camera_focusmode_entry_auto_abbr);
        }
        double d2 = (double) i;
        return d2 >= 600.0d ? context.getString(R.string.pref_camera_focusmode_entry_macro) : d2 >= 200.0d ? context.getString(R.string.pref_camera_focusmode_entry_normal) : context.getString(R.string.pref_camera_focusmode_entry_infinity);
    }

    public static String getMappingFocusMode(int i) {
        return i == 1000 ? ModuleManager.isProVideoModule() ? AutoFocus.LEGACY_CONTINUOUS_VIDEO : AutoFocus.LEGACY_CONTINUOUS_PICTURE : "manual";
    }

    public static int[] getMaxPreviewFpsRange(Camera.Parameters parameters) {
        List<int[]> supportedPreviewFpsRange = parameters.getSupportedPreviewFpsRange();
        return (supportedPreviewFpsRange == null || supportedPreviewFpsRange.size() <= 0) ? new int[0] : supportedPreviewFpsRange.get(supportedPreviewFpsRange.size() - 1);
    }

    public static String getMimojiModleVersion() {
        return DataRepository.dataItemLive().getString(KEY_MIMOJI_MODEL_VERSION, "v0");
    }

    public static boolean getMirrorSettingUiNeedRemove(int i, boolean z) {
        if (!z || b.pm() || i == 161 || i == 174 || i == 183 || i == 179 || i == 169 || i == 172 || i == 184 || i == 177) {
            return true;
        }
        boolean s_v_f_m = DataRepository.dataItemFeature().s_v_f_m();
        Log.d(TAG, "filterByConfig: isSupportVideoFrontMirror = " + s_v_f_m);
        if (i != 162 || s_v_f_m) {
            return b.hl() && i == 162;
        }
        return true;
    }

    public static String getMiuiSettingsKeyForStreetSnap(String str) {
        return (str.equals(getString(R.string.pref_camera_snap_value_take_picture)) || str.equals(getString(R.string.pref_camera_snap_value_take_movie))) ? a.b.Oh : "none";
    }

    public static boolean getMovieSolidSettingNeedRemove(int i, boolean z) {
        if (!b._l() || i == 177 || i == 184 || i == 172 || z || isVideoQuality8KOpen(i) || isVideoBokehOn() || DataRepository.dataItemRunning().getComponentRunningShine().isVideoBeautyOpen(i)) {
            return true;
        }
        boolean isMacroModeEnabled = isMacroModeEnabled(DataRepository.dataItemGlobal().getCurrentMode());
        if (!b.cl() && !isMacroModeEnabled) {
            return getHSRIntegerValue() == 60;
        }
        return true;
    }

    public static boolean getNormalWideLDCNeedRemove(int i, boolean z) {
        return !shouldNormalWideLDCBeVisibleInMode(i) || isUltraWideConfigOpen(i) || isUltraPixelOn() || isMacroModeEnabled(i);
    }

    public static int getPanoramaMoveDirection(Context context) {
        return DataRepository.dataItemRunning().getInt("pref_panorana_move_direction_key", Util.isLayoutRTL(context) ? 4 : 3);
    }

    public static int[] getPhotoPreviewFpsRange(Camera.Parameters parameters) {
        List<int[]> supportedPreviewFpsRange = parameters.getSupportedPreviewFpsRange();
        if (supportedPreviewFpsRange == null || supportedPreviewFpsRange.isEmpty()) {
            Log.e(TAG, "No supported frame rates returned!");
            return null;
        }
        int i = MAX_PREVIEW_FPS_TIMES_1000;
        for (int[] iArr : supportedPreviewFpsRange) {
            int i2 = iArr[0];
            if (iArr[1] >= 30000 && i2 <= 30000 && i2 < i) {
                i = i2;
            }
        }
        int i3 = -1;
        int i4 = 0;
        for (int i5 = 0; i5 < supportedPreviewFpsRange.size(); i5++) {
            int[] iArr2 = supportedPreviewFpsRange.get(i5);
            int i6 = iArr2[0];
            int i7 = iArr2[1];
            if (i6 == i && i4 < i7) {
                i3 = i5;
                i4 = i7;
            }
        }
        if (i3 >= 0) {
            return supportedPreviewFpsRange.get(i3);
        }
        Log.e(TAG, "Can't find an appropriate frame rate range!");
        return null;
    }

    public static String getPictureSizeRatioString() {
        return DataRepository.dataItemConfig().getComponentConfigRatio().getPictureSizeRatioString(DataRepository.dataItemGlobal().getCurrentMode());
    }

    public static boolean getPlayToneOnCaptureStart() {
        return DataRepository.dataItemConfig().getBoolean(KEY_PLAY_TONE_ON_CAPTURE_START, true);
    }

    public static int getPortraitLightingPattern() {
        return Integer.valueOf(DataRepository.dataItemRunning().getComponentRunningLighting().getComponentValue(171)).intValue();
    }

    public static int getPreferVideoQuality(int i, int i2) {
        return dealVideoQuality(DataRepository.dataItemConfig().getComponentConfigVideoQuality().getComponentValue(i2), i, i2);
    }

    public static int getPreferVideoQuality(String str, int i, int i2) {
        return dealVideoQuality(DataRepository.dataItemConfig().getComponentConfigVideoQuality().getComponentValue(i2, str), i, i2);
    }

    public static float getPreviewAspectRatio(float f2) {
        double d2 = (double) f2;
        double d3 = d2 - 1.7777777777777777d;
        if (Math.abs(d2 - 1.3333333333333333d) <= Math.abs(d3)) {
            return 1.3333334f;
        }
        double d4 = d2 - 2.1666666666666665d;
        if (Math.abs(d4) > Math.abs(d2 - 2.2222222222222223d)) {
            return 2.2222223f;
        }
        double d5 = d2 - 2.0d;
        if (Math.abs(d5) > Math.abs(d4)) {
            return 2.1666667f;
        }
        if (Math.abs(d3) > Math.abs(d5)) {
            return 2.0f;
        }
        return Math.abs(d2 - 2.0833333333333335d) < Math.abs(d3) ? 2.0833333f : 1.7777778f;
    }

    public static float getPreviewAspectRatio(int i, int i2) {
        return getPreviewAspectRatio(((float) i) / ((float) i2));
    }

    public static boolean getPriorityStoragePreference() {
        return DataRepository.dataItemGlobal().getBoolean("pref_priority_storage", Boolean.valueOf(getString(R.bool.priority_storage)).booleanValue());
    }

    public static int getRenderAspectRatio(int i, int i2) {
        return getAspectRatio(i, i2);
    }

    public static float getResourceFloat(int i, float f2) {
        Context androidContext = CameraAppImpl.getAndroidContext();
        TypedValue typedValue = new TypedValue();
        try {
            androidContext.getResources().getValue(i, typedValue, true);
            return typedValue.getFloat();
        } catch (Exception unused) {
            Log.e(TAG, "Missing resource " + Integer.toHexString(i));
            return f2;
        }
    }

    public static String getRestoredFlashMode() {
        return DataRepository.dataItemConfig().getString(KEY_RESTORED_FLASH_MODE, null);
    }

    public static boolean getRetainCameraModeSettingNeedRemove(int i, boolean z) {
        return b.cv;
    }

    public static String getSaturation() {
        String string = getString(R.string.pref_camera_saturation_default);
        String string2 = DataRepository.dataItemGlobal().getString("pref_qc_camera_saturation_key", string);
        if (string.equals(string2) || Util.isStringValueContained(string2, (int) R.array.pref_camera_saturation_entryvalues)) {
            return string2;
        }
        Log.e(TAG, "reset invalid saturation " + string2);
        resetSaturation();
        return string;
    }

    public static boolean getScanQrcodeSettingNeedRemove(int i, boolean z) {
        return z || b.cv || DataRepository.dataItemFeature().c_0x44() || b.Te() || isInAllRecordModeSet(i);
    }

    public static int getScreenLightBrightness() {
        int screenLightBrightness = Camera2DataContainer.getInstance().getCapabilities(getCameraId()).getScreenLightBrightness();
        return screenLightBrightness <= 0 ? SystemProperties.getInt("camera_screen_light_brightness", DataRepository.dataItemFeature().c_0x0b_OR_180()) : screenLightBrightness;
    }

    public static int getShaderEffect() {
        int currentMode = DataRepository.dataItemGlobal().getCurrentMode();
        ComponentConfigFilter componentConfigFilter = DataRepository.dataItemRunning().getComponentConfigFilter();
        Log.d(TAG, "getShaderEffect: mode = " + currentMode + ", value = " + componentConfigFilter.getComponentValue(currentMode));
        return Util.parseInt(componentConfigFilter.getComponentValue(currentMode), DataRepository.dataItemRunning().getComponentRunningShine().isSmoothBarVideoBeautyVersion(currentMode) ? 0 : FilterInfo.FILTER_ID_NONE);
    }

    public static String getSharpness() {
        String string = getString(R.string.pref_camera_sharpness_default);
        String string2 = DataRepository.dataItemGlobal().getString("pref_qc_camera_sharpness_key", string);
        if (string.equals(string2) || Util.isStringValueContained(string2, (int) R.array.pref_camera_sharpness_entryvalues)) {
            return string2;
        }
        Log.e(TAG, "reset invalid sharpness " + string2);
        resetSharpness();
        return string;
    }

    public static int getSkipFrameNumber() {
        if (isFrontCamera() || getCameraId() == Camera2DataContainer.getInstance().getUltraWideCameraId()) {
            return CameraAppImpl.getAndroidContext().getResources().getInteger(R.integer.camera_skip_frame_num);
        }
        return 0;
    }

    public static String getSmartShutterPosition() {
        return DataRepository.dataItemGlobal().getString(KEY_SMART_SHUTTER_POSITION, "");
    }

    public static String getStereoDofLevel() {
        return DataRepository.dataItemConfig().getString(KEY_CAMERA_STEREO, getString(R.string.pref_camera_stereo_default));
    }

    public static int getStrictAspectRatio(int i, int i2) {
        if (isAspectRatio16_9(i, i2)) {
            return 1;
        }
        if (isAspectRatio4_3(i, i2)) {
            return 0;
        }
        return isAspectRatio1_1(i, i2) ? 2 : -1;
    }

    public static String getString(int i) {
        return CameraAppImpl.getAndroidContext().getString(i);
    }

    public static ArrayList<String> getSupportedHfrSettings(int i) {
        ArrayList<String> arrayList = new ArrayList<>();
        CameraCapabilities capabilities = Camera2DataContainer.getInstance().getCapabilities(i);
        if (capabilities == null) {
            return arrayList;
        }
        Size[] supportedHighSpeedVideoSize = capabilities.getSupportedHighSpeedVideoSize();
        for (Size size : supportedHighSpeedVideoSize) {
            if (size.getWidth() == 1920 || size.getWidth() == 1280) {
                for (Range<Integer> range : capabilities.getSupportedHighSpeedVideoFPSRange(size)) {
                    String format = String.format(Locale.ENGLISH, "%dx%d:%d", Integer.valueOf(size.getWidth()), Integer.valueOf(size.getHeight()), range.getUpper());
                    if (!arrayList.contains(format)) {
                        arrayList.add(format);
                    }
                }
            }
        }
        return arrayList;
    }

    public static ArrayList<String> getSupportedVideoSpeed(Context context, int i) {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("normal");
        arrayList.add(VIDEO_SPEED_FAST);
        return arrayList;
    }

    public static int getSystemEdgeMode(Context context) {
        if (!b.Tl()) {
            return 0;
        }
        return (Settings.System.getInt(context.getContentResolver(), EDGE_HANDGRIP_MODE_SCREENSHOT, 0) | ((Settings.System.getInt(context.getContentResolver(), EDGE_HANDGRIP_MODE, 0) | Settings.System.getInt(context.getContentResolver(), EDGE_HANDGRIP_MODE_CLEAN, 0)) | Settings.System.getInt(context.getContentResolver(), EDGE_HANDGRIP_MODE_BACK, 0))) == 1 ? 2 : 0;
    }

    public static String getTTLiveMusicJsonCache() {
        return DataRepository.dataItemLive().getString(KEY_TT_LIVE_MUSIC_JSON_CACHE, "");
    }

    public static String getTTLiveStickerJsonCache() {
        return DataRepository.dataItemLive().getString(KEY_TT_LIVE_STICKER_JSON_CACHE, "");
    }

    public static boolean getTTLiveStickerNeedRedDot() {
        return DataRepository.dataItemLive().getBoolean(KEY_LIVE_STICKER_NEED_RED_DOT, false);
    }

    public static boolean getTimeWaterMarkState(int i, boolean z) {
        return !b.km();
    }

    public static int getUIStyleByPreview(int i, int i2) {
        if (b.tl()) {
            return 0;
        }
        if (sCroppedIfNeeded) {
            return 1;
        }
        double d2 = ((double) i) / ((double) i2);
        if (b.sl() && Math.abs(d2 - 1.5d) < 0.02d) {
            return 2;
        }
        if (Math.abs((((double) Util.sWindowHeight) / ((double) Util.sWindowWidth)) - d2) >= 0.02d) {
            double d3 = d2 - 1.7777777777777777d;
            if (Math.abs(d2 - 1.3333333333333333d) <= Math.abs(d3) && Math.abs(d2 - 1.5d) >= 0.02d) {
                return d2 == 1.0d ? 4 : 0;
            }
            if (Math.abs(d3) <= Math.abs(d2 - 2.0d)) {
                return 1;
            }
        }
        return 3;
    }

    public static float getVideoBokehRatio() {
        return DataRepository.dataItemRunning().getFloat(KEY_VIDEO_BOKEH_ADJUST, 0.0f);
    }

    public static int getVideoEncoder() {
        return "h265".equals(DataRepository.dataItemGlobal().getString("pref_video_encoder_key", getString(R.string.pref_video_encoder_default))) ? 5 : 2;
    }

    public static String getVideoSpeed() {
        return DataRepository.dataItemRunning().getVideoSpeed();
    }

    public static boolean getVideoTagSettingNeedRemove(int i, boolean z) {
        if (DataRepository.dataItemGlobal().isIntentAction()) {
            return true;
        }
        com.mi.config.a dataItemFeature = DataRepository.dataItemFeature();
        if (isInAllCaptureModeSet(i) || i == 172 || i == 168 || i == 161 || i == 179 || i == 169 || !dataItemFeature.c_32889_0x0001() || isSubtitleEnabled(i)) {
            return true;
        }
        return (isVideoQuality8KOpen(i) && Camera2OpenManager.getInstance().getPendingCameraId() == 0) || ComponentConfigVideoQuality.QUALITY_8K.equals(DataRepository.dataItemConfig().getComponentConfigVideoQuality().getComponentValue(i));
    }

    public static boolean getVideoTimeLapseFrameIntervalNeedRemove(int i, boolean z) {
        return !DataRepository.dataItemGlobal().isNormalIntent() || z || isVideoQuality8KOpen(i) || i == 177 || i == 184 || isInAllCaptureModeSet(i);
    }

    public static boolean getVolumeCameraSettingNeedRemove(int i, boolean z) {
        return isInAllRecordModeSet(i) || b.mv;
    }

    public static boolean getVolumeLiveSettingNeedRemove(int i, boolean z) {
        return isInAllCaptureModeSet(i) || i == 0 || b.mv || i == 161 || i == 162 || i == 169 || i == 172 || i == 180 || i == 177 || i == 184;
    }

    public static boolean getVolumeProVideoSettingNeedRemove(int i, boolean z) {
        return isInAllCaptureModeSet(i) || i != 180 || i == 0 || b.mv;
    }

    public static boolean getVolumeVideoSettingNeedRemove(int i, boolean z) {
        if (isInAllCaptureModeSet(i)) {
            return true;
        }
        if (isAutoZoomEnabled(i)) {
            return false;
        }
        return i == 174 || i == 183 || i == 179 || i == 177 || i == 184 || i == 180 || i == 0 || b.mv;
    }

    public static String getWhiteBalance() {
        return DataRepository.dataItemConfig().getString(KEY_WHITE_BALANCE, getString(R.string.pref_camera_whitebalance_default));
    }

    private static void initBeautyComponentBody(BeautyValues beautyValues) {
        beautyValues.mBeautyHeadSlim = getFaceBeautyRatio("pref_beauty_head_slim_ratio");
        beautyValues.mBeautyBodySlim = getFaceBeautyRatio("pref_beauty_body_slim_ratio");
        beautyValues.mBeautyShoulderSlim = getFaceBeautyRatio("pref_beauty_shoulder_slim_ratio");
        beautyValues.mBeautyLegSlim = getFaceBeautyRatio("key_beauty_leg_slim_ratio");
        beautyValues.mBeautyWholeBodySlim = getFaceBeautyRatio("pref_beauty_whole_body_slim_ratio");
        beautyValues.mBeautyButtSlim = getFaceBeautyRatio("pref_beauty_butt_slim_ratio");
    }

    private static void initBeautyComponentLegacy(BeautyValues beautyValues) {
        beautyValues.mBeautySlimFace = getFaceBeautyRatio("pref_beautify_slim_face_ratio_key");
        beautyValues.mBeautyEnlargeEye = getFaceBeautyRatio("pref_beautify_enlarge_eye_ratio_key");
        beautyValues.mBeautySkinColor = getFaceBeautyRatio("pref_beautify_skin_color_ratio_key");
        beautyValues.mBeautySkinSmooth = getFaceBeautyRatio("pref_beautify_skin_smooth_ratio_key");
    }

    private static void initBeautyComponentMakeUp(BeautyValues beautyValues) {
        beautyValues.mBeautyEyebrowDye = getFaceBeautyRatio("pref_beautify_eyebrow_dye_ratio_key");
        beautyValues.mBeautyPupilLine = getFaceBeautyRatio("pref_beautify_pupil_line_ratio_key");
        beautyValues.mBeautyJellyLips = getFaceBeautyRatio("pref_beautify_jelly_lips_ratio_key");
        beautyValues.mBeautyBlusher = getFaceBeautyRatio("pref_beautify_blusher_ratio_key");
    }

    private static void initBeautyComponentModel(BeautyValues beautyValues) {
        beautyValues.mBeautySlimFace = getFaceBeautyRatio("pref_beautify_slim_face_ratio_key");
        beautyValues.mBeautyEnlargeEye = getFaceBeautyRatio("pref_beautify_enlarge_eye_ratio_key");
        beautyValues.mBeautyNose = getFaceBeautyRatio("pref_beautify_nose_ratio_key");
        beautyValues.mBeautyRisorius = getFaceBeautyRatio("pref_beautify_risorius_ratio_key");
        beautyValues.mBeautyLips = getFaceBeautyRatio("pref_beautify_lips_ratio_key");
        beautyValues.mBeautyChin = getFaceBeautyRatio("pref_beautify_chin_ratio_key");
        beautyValues.mBeautyNeck = getFaceBeautyRatio("pref_beautify_neck_ratio_key");
        beautyValues.mBeautySmile = getFaceBeautyRatio("pref_beautify_smile_ratio_key");
        beautyValues.mBeautySlimNose = getFaceBeautyRatio("pref_beautify_slim_nose_ratio_key");
        beautyValues.mBeautyHairLine = getFaceBeautyRatio("pref_beautify_hairline_ratio_key");
    }

    public static void initBeautyValues(BeautyValues beautyValues, int i) {
        if (beautyValues != null) {
            ComponentConfigBeauty componentConfigBeauty = DataRepository.dataItemConfig().getComponentConfigBeauty();
            ComponentRunningShine componentRunningShine = DataRepository.dataItemRunning().getComponentRunningShine();
            beautyValues.resetAll();
            if (!componentConfigBeauty.isClosed(i) && !componentRunningShine.isClosed()) {
                int beautyVersion = componentRunningShine.getBeautyVersion();
                if (beautyVersion == 1 || beautyVersion == 2) {
                    if (componentRunningShine.supportBeautyLevel()) {
                        beautyValues.mBeautyLevel = getFaceBeautifyLevel();
                    }
                    if (!beautyValues.isBeautyLevelOn()) {
                        return;
                    }
                } else if (beautyVersion == 3 && componentRunningShine.supportSmoothLevel()) {
                    beautyValues.mBeautySkinSmooth = getFaceBeautyRatio("pref_beautify_skin_smooth_ratio_key");
                }
                if (!componentRunningShine.isLegacyBeautyVersion()) {
                    if (componentRunningShine.supportBeautyModel()) {
                        initBeautyComponentModel(beautyValues);
                    }
                    if (componentRunningShine.supportBeautyMakeUp()) {
                        initBeautyComponentMakeUp(beautyValues);
                    }
                    if (componentRunningShine.supportBeautyBody()) {
                        initBeautyComponentBody(beautyValues);
                    }
                } else if (componentRunningShine.supportBeautyModel()) {
                    initBeautyComponentLegacy(beautyValues);
                }
            }
        }
    }

    public static boolean is4KHigherVideoQuality(int i) {
        if (!b.nm()) {
            return false;
        }
        return get4kProfile() <= i || i == 1;
    }

    public static boolean is960WatermarkOn(int i) {
        return DataRepository.dataItemFeature().c_19039_0x0010() && DataRepository.dataItemGlobal().getBoolean("pref_960_watermark_status", CameraAppImpl.getAndroidContext().getResources().getBoolean(R.bool.pref_960_watermark_default));
    }

    public static boolean isAEAFLockSupport() {
        if (ModuleManager.isSuperNightScene() || isSuperNightOn()) {
            return false;
        }
        return DataRepository.dataItemGlobal().getBoolean(KEY_AE_AF_LOCK_SUPPORT, CameraAppImpl.getAndroidContext().getResources().getBoolean(R.bool.pref_camera_AEAF_lock_support));
    }

    public static boolean isAIWatermarkOn() {
        return DataRepository.dataItemRunning().getComponentRunningAIWatermark().getAIWatermarkEnable();
    }

    public static boolean isAIWatermarkOn(int i) {
        return DataRepository.dataItemRunning().getComponentRunningAIWatermark().getAIWatermarkEnable(i);
    }

    public static boolean isAgeGenderAndMagicMirrorWaterOpen() {
        return DataRepository.dataItemFeature().s_o_a_w() && isFrontCamera() && (showGenderAge() || isMagicMirrorOn());
    }

    public static boolean isAsdMotionEnable() {
        return b.Ql() && DataRepository.dataItemGlobal().getBoolean("pref_camera_asd_night_key", Boolean.valueOf(getString(R.bool.pref_camera_asd_night_default)).booleanValue());
    }

    public static boolean isAsdNightEnable() {
        return b.Rl() && DataRepository.dataItemGlobal().getBoolean("pref_camera_asd_night_key", Boolean.valueOf(getString(R.bool.pref_camera_asd_night_default)).booleanValue());
    }

    public static boolean isAsdPopupEnable() {
        return b.Pl();
    }

    public static boolean isAspectRatio16_9(int i, int i2) {
        if (i < i2) {
            i2 = i;
            i = i2;
        }
        return Math.abs((((double) i) / ((double) i2)) - 1.7777777777777777d) < 0.02d;
    }

    public static boolean isAspectRatio18_9(int i, int i2) {
        if (i < i2) {
            i2 = i;
            i = i2;
        }
        return Math.abs((((double) i) / ((double) i2)) - 2.0d) < 0.02d;
    }

    public static boolean isAspectRatio19_5_9(int i, int i2) {
        if (i < i2) {
            i2 = i;
            i = i2;
        }
        return Math.abs((((double) i) / ((double) i2)) - 2.1666666666666665d) < 0.02d;
    }

    public static boolean isAspectRatio1_1(int i, int i2) {
        return i == i2;
    }

    public static boolean isAspectRatio4_3(int i, int i2) {
        if (i < i2) {
            i2 = i;
            i = i2;
        }
        return Math.abs((((double) i) / ((double) i2)) - 1.3333333333333333d) < 0.02d;
    }

    public static boolean isAutoZoomEnabled(int i) {
        return DataRepository.dataItemRunning().getComponentRunningAutoZoom().isEnabled(i);
    }

    public static boolean isAvailableChipsGoogleLens() {
        return isAvailableGoogleLens() && DataRepository.dataItemGlobal().getBoolean("pref_google_lens_suggestions_key", Boolean.valueOf(getString(R.string.pref_google_lens_default)).booleanValue()) && !isGradienterOn() && !isTiltShiftOn() && !isUltraWideConfigOpen(163) && !isGroupShotOn();
    }

    public static boolean isAvailableGoogleLens() {
        return !isFrontCamera() && b.Te() && (DataRepository.dataItemGlobal().getBoolean("pref_google_lens_suggestions_key", Boolean.valueOf(getString(R.string.pref_google_lens_default)).booleanValue()) || getString(R.string.pref_camera_long_press_viewfinder_default).equals(DataRepository.dataItemGlobal().getString("pref_camera_long_press_viewfinder_key", getString(R.string.pref_camera_long_press_viewfinder_default))));
    }

    public static boolean isAvailableLongPressGoogleLens() {
        return isAvailableGoogleLens() && getString(R.string.pref_camera_long_press_viewfinder_default).equals(DataRepository.dataItemGlobal().getString("pref_camera_long_press_viewfinder_key", getString(R.string.pref_camera_long_press_viewfinder_default))) && !isGradienterOn() && !isTiltShiftOn() && !isUltraWideConfigOpen(163) && !isGroupShotOn();
    }

    public static boolean isBackCamera() {
        return DataRepository.dataItemGlobal().getCurrentCameraId() == 0;
    }

    public static boolean isBackPortrait() {
        return isBackCamera() && ((DataItemGlobal) DataRepository.provider().dataGlobal()).getCurrentMode() == 171;
    }

    public static boolean isBeautyMakeupClicked() {
        return DataRepository.dataItemGlobal().getBoolean(KEY_BEAUTY_MAKEUP_CLICKED, false);
    }

    public static boolean isBeautyRemodelingClicked() {
        return DataRepository.dataItemGlobal().getBoolean(KEY_BEAUTY_REMODELING_CLICKED, false);
    }

    public static boolean isBurstShootingEnable() {
        return b.Xl() && MistatsConstants.CaptureSence.BURST.equals(DataRepository.dataItemGlobal().getString("pref_camera_long_press_shutter_feature_key", getString(R.string.pref_camera_long_press_shutter_feature_default)));
    }

    public static boolean isCameraFaceDetectionAutoHidden() {
        return DataRepository.dataItemGlobal().getBoolean("pref_camera_facedetection_auto_hidden_key", CameraAppImpl.getAndroidContext().getResources().getBoolean(R.bool.pref_camera_facedetection_auto_hidden_default));
    }

    public static boolean isCameraLyingHintOn() {
        return DataRepository.dataItemFeature().c_0x48() && DataRepository.dataItemGlobal().getBoolean("pref_camera_lying_tip_switch_key", true);
    }

    public static boolean isCameraParallelProcessEnable() {
        return DataRepository.dataItemFeature().ye() && DataRepository.dataItemGlobal().getBoolean("pref_camera_parallel_process_enable_key", CameraAppImpl.getAndroidContext().getResources().getBoolean(R.bool.pref_camera_parallel_process_enable_default));
    }

    public static boolean isCameraPortraitWithFaceBeauty() {
        return DataRepository.dataItemGlobal().getBoolean("pref_camera_portrait_with_facebeauty_key", CameraAppImpl.getAndroidContext().getResources().getBoolean(R.bool.pref_camera_portrait_with_facebeauty_default));
    }

    public static boolean isCameraQuickShotAnimateEnable() {
        if (isSupportQuickShot()) {
            return DataRepository.dataItemGlobal().getBoolean("pref_camera_quick_shot_anim_enable_key", CameraAppImpl.getAndroidContext().getResources().getBoolean(R.bool.pref_camera_quick_shot_anim_enable_default));
        }
        return false;
    }

    public static boolean isCameraQuickShotEnable() {
        if (isSupportQuickShot()) {
            return DataRepository.dataItemGlobal().getBoolean("pref_camera_quick_shot_enable_key", CameraAppImpl.getAndroidContext().getResources().getBoolean(R.bool.pref_camera_quick_shot_enable_default));
        }
        return false;
    }

    public static boolean isCameraSoundOpen() {
        return !b.am() || DataRepository.dataItemGlobal().getBoolean("pref_camerasound_key", true);
    }

    public static boolean isCameraSpecific(String str) {
        return str.equals("pref_camera_jpegquality_key") || str.equals("pref_video_quality_key") || str.equals("pref_sticker_path_key") || str.equals(KEY_FLASH_MODE) || str.equals(KEY_VIDEOCAMERA_FLASH_MODE) || str.equals(KEY_CAMERA_HDR) || str.equals(KEY_VIDEO_HDR) || str.equals(KEY_WHITE_BALANCE) || str.equals("pref_qc_manual_whitebalance_k_value_key") || str.equals(KEY_QC_FOCUS_POSITION) || str.equals(KEY_QC_EXPOSURETIME) || str.equals(KEY_QC_ISO) || str.equals(KEY_CAMERA_ZOOM_MODE) || str.equals(KEY_CAMERA_MANUALLY_LENS) || str.equals("pref_beautify_level_key_capture") || str.equals(KEY_FACE_BEAUTY_SWITCH) || str.equals("pref_beautify_slim_face_ratio_key") || str.equals("pref_beautify_skin_smooth_ratio_key") || str.equals("pref_beautify_skin_color_ratio_key") || str.equals("pref_beautify_enlarge_eye_ratio_key") || str.equals(KEY_QC_MANUAL_EXPOSURE_VALUE) || str.equals(KEY_PRO_VIDEO_WHITE_BALANCE) || str.equals(KEY_QC_PRO_VIDEO_FOCUS_POSITION) || str.equals(KEY_QC_PRO_VIDEO_EXPOSURETIME) || str.equals(KEY_QC_PRO_VIDEO_EXPOSURE_VALUE) || str.equals(KEY_QC_PRO_VIDEO_ISO);
    }

    public static boolean isCameraTouchFocusDelayEnable() {
        return DataRepository.dataItemGlobal().getBoolean("pref_camera_touch_focus_delay_key", false);
    }

    public static boolean isCinematicAspectRatioEnabled(int i) {
        return DataRepository.dataItemConfig().getCinematicAspectRatio().isEnabled(i);
    }

    public static boolean isCustomWatermarkOpen() {
        return DataRepository.dataItemGlobal().getString(KEY_CUSTOM_WATERMARK, getDefaultWatermarkStr()).equals(getCustomWatermarkDefault());
    }

    public static boolean isDebugInfoAsWatermarkEnabled() {
        return DataRepository.dataItemGlobal().getBoolean(KEY_DEBUG_INFO_AS_WATERMARK, false);
    }

    public static boolean isDocumentModeOn(int i) {
        return DataRepository.dataItemRunning().getComponentRunningDocument().isEnabled(i);
    }

    public static boolean isDualCameraEnable() {
        return DataRepository.dataItemGlobal().getBoolean("pref_camera_dual_enable_key", CameraAppImpl.getAndroidContext().getResources().getBoolean(R.bool.pref_camera_dual_enable_default));
    }

    public static boolean isDualCameraSatEnable() {
        return DataRepository.dataItemGlobal().getBoolean("pref_camera_dual_sat_enable_key", CameraAppImpl.getAndroidContext().getResources().getBoolean(R.bool.pref_camera_dual_sat_enable_default));
    }

    public static boolean isDualCameraWaterMarkOpen() {
        if (ModuleManager.isDocumentMode() || ModuleManager.isIDCardMode() || !isSupportedDualCameraWaterMark() || !isBackCamera()) {
            return false;
        }
        return DataRepository.dataItemGlobal().getBoolean("pref_dualcamera_watermark_key", b.G(getBool(R.bool.pref_device_watermark_default)));
    }

    public static boolean isEdgePhotoEnable() {
        return b.Tl() && sEdgePhotoEnable;
    }

    public static boolean isEnableDNG() {
        return false;
    }

    public static boolean isEvAdjustable() {
        return !isTiltShiftOn() && !ModuleManager.isSuperNightScene() && !isSuperNightOn() && !ModuleManager.isProModule();
    }

    public static boolean isEyeLightOpen() {
        return !"-1".equals(getEyeLightType());
    }

    public static boolean isFPS960(int i) {
        return i == 172 && DataRepository.dataItemFeature().enSlowMotion_960_240_120() && DataRepository.dataItemConfig().getComponentConfigSlowMotion().isSlowMotionFps960();
    }

    public static boolean isFaceBeautyOn(int i, @Nullable BeautyValues beautyValues) {
        if (beautyValues == null) {
            beautyValues = new BeautyValues();
        }
        initBeautyValues(beautyValues, i);
        return DataRepository.dataItemRunning().getComponentRunningShine().isSmoothBarVideoBeautyVersion(i) ? DataRepository.dataItemRunning().getComponentRunningShine().isVideoBeautyOpen(i) : beautyValues.isFaceBeautyOn();
    }

    public static boolean isFaceWaterMarkOpen() {
        return DataRepository.dataItemGlobal().getBoolean(KEY_FACE_INFO_WATERMARK, false);
    }

    public static boolean isFingerprintCaptureEnable() {
        return DataRepository.dataItemGlobal().getBoolean("pref_fingerprint_capture_key", false);
    }

    public static boolean isFlashSupportedInManualMode() {
        if (!DataRepository.dataItemFeature().c_13254_0x01()) {
            return true;
        }
        CameraSettingPreferences instance = CameraSettingPreferences.instance();
        return instance.getString(KEY_QC_EXPOSURETIME, getString(R.string.pref_camera_exposuretime_default)).equals(getString(R.string.pref_camera_exposuretime_default)) && instance.getString(KEY_QC_ISO, getString(R.string.pref_camera_iso_default)).equals(getString(R.string.pref_camera_iso_default));
    }

    public static boolean isFocusModeSwitching() {
        return DataRepository.dataItemConfig().getBoolean("pref_qc_focus_mode_switching_key", false);
    }

    public static boolean isFromProVideoMudule() {
        return DataRepository.dataItemConfig().getBoolean(KEY_CAMERA_FROM_PRO_VIDEO_MODULE, false);
    }

    public static boolean isFrontCamera() {
        return DataRepository.dataItemGlobal().getCurrentCameraId() == 1;
    }

    public static boolean isFrontCameraWaterMarkOpen() {
        if (!DataRepository.dataItemFeature().c_0x46() || !isFrontCamera()) {
            return false;
        }
        return DataRepository.dataItemGlobal().getBoolean("pref_dualcamera_watermark_key", b.G(getBool(R.bool.pref_device_watermark_default)));
    }

    public static boolean isFrontMirror() {
        DataItemGlobal dataItemGlobal = DataRepository.dataItemGlobal();
        String string = dataItemGlobal.getString("pref_front_mirror_key", b.hl() ? getString(R.string.pref_front_mirror_entryvalue_off) : getString(R.string.pref_front_mirror_default));
        if ("auto".equals(string)) {
            dataItemGlobal.editor().remove("pref_front_mirror_key").apply();
        }
        return getString(R.string.pref_front_mirror_entryvalue_on).equals(string);
    }

    public static boolean isFrontPortrait() {
        return isFrontCamera() && ((DataItemGlobal) DataRepository.provider().dataGlobal()).getCurrentMode() == 171;
    }

    public static boolean isGifOn() {
        return DataRepository.dataItemLive().getMimojiStatusManager2().isGifState();
    }

    public static boolean isGradienterOn() {
        if (isFrontCamera() || !b.Fl()) {
            return false;
        }
        return "on".equals(DataRepository.dataItemConfig().getComponentConfigGradienter().getComponentValue(DataRepository.dataItemGlobal().getCurrentMode()));
    }

    public static boolean isGroupShotOn() {
        return DataRepository.dataItemRunning().isSwitchOn("pref_camera_groupshot_mode_key");
    }

    public static boolean isH265EncoderSupport() {
        int codecCount = MediaCodecList.getCodecCount();
        for (int i = 0; i < codecCount; i++) {
            MediaCodecInfo codecInfoAt = MediaCodecList.getCodecInfoAt(i);
            if (codecInfoAt.isEncoder() && codecInfoAt.getName().toLowerCase().contains("hevc")) {
                return true;
            }
        }
        return false;
    }

    public static boolean isHandGestureOpen() {
        if (!DataRepository.dataItemRunning().supportHandGesture()) {
            return false;
        }
        return DataRepository.dataItemRunning().getBoolean("pref_hand_gesture", false);
    }

    public static boolean isHeicImageFormatSelected() {
        return DataRepository.dataItemGlobal().getBoolean(KEY_HEIC_FORMAT, CameraAppImpl.getAndroidContext().getResources().getBoolean(R.bool.pref_camera_heic_image_format_default));
    }

    public static boolean isInAllCaptureModeSet(int i) {
        return i == 163 || i == 165 || i == 166 || i == 176 || i == 167 || i == 173 || i == 175 || i == 171;
    }

    public static boolean isInAllRecordModeSet(int i) {
        return i == 161 || i == 174 || i == 183 || i == 179 || i == 162 || i == 169 || i == 172 || i == 180;
    }

    public static boolean isLabOptionsVisible() {
        return DataRepository.dataItemGlobal().getBoolean(KEY_CAMERA_LAB_OPTIONS_VISIBLE, false);
    }

    public static boolean isLensDirtyDetectEnabled() {
        return DataRepository.dataNormalItemConfig().getBoolean(KEY_LENS_DIRTY_DETECT_ENABLED, true);
    }

    public static boolean isLiveBeautyOpen() {
        return DataRepository.dataItemLive().getBoolean(KEY_LIVE_BEAUTY_STATUS, false) || DataRepository.dataItemLive().getLiveFilter() != 0;
    }

    public static boolean isLiveModuleClicked() {
        return DataRepository.dataItemGlobal().getBoolean(KEY_LIVE_MODULE_CLICKED, false);
    }

    public static boolean isLiveShotOn() {
        if (!DataRepository.dataItemFeature().vd()) {
            return false;
        }
        return DataRepository.dataItemRunning().getComponentRunningLiveShot().isLiveShotOn();
    }

    public static boolean isLiveStickerInternalChannel() {
        return DataRepository.dataItemGlobal().getBoolean("pref_camera_live_sticker_internal", getBool(R.bool.pref_camera_live_sticker_internal_default));
    }

    public static boolean isMacro2Sat() {
        return sMacro2Sat;
    }

    public static boolean isMacroModeEnabled(int i) {
        return DataRepository.dataItemRunning().supportMacroMode() && DataRepository.dataItemRunning().getComponentRunningMacroMode().isSwitchOn(i);
    }

    public static boolean isMacroModeEnabled(int i, int i2) {
        return DataRepository.dataItemRunning().supportMacroMode() && DataRepository.dataItemRunning().getComponentRunningMacroMode().isSwitchOn(i, i2);
    }

    public static boolean isMagicMirrorOn() {
        return DataRepository.dataItemRunning().isSwitchOn("pref_camera_magic_mirror_key");
    }

    public static boolean isMfnrSatEnable() {
        return DataRepository.dataItemGlobal().getBoolean("pref_camera_mfnr_sat_enable_key", CameraAppImpl.getAndroidContext().getResources().getBoolean(R.bool.pref_camera_mfnr_sat_enable_default));
    }

    public static boolean isMiLiveBeautyOpen() {
        return ModuleManager.isMiLiveModule() && DataRepository.dataItemLive().getBoolean(KEY_LIVE_BEAUTY_STATUS, false);
    }

    public static boolean isMovieSolidOn() {
        return b._l() && DataRepository.dataItemGlobal().getBoolean("pref_camera_movie_solid_key", Boolean.valueOf(getString(R.string.pref_camera_movie_solid_default)).booleanValue());
    }

    public static boolean isNearAspectRatio(int i, int i2, int i3, int i4) {
        return getAspectRatio(i, i2) == getAspectRatio(i3, i4);
    }

    public static boolean isNearRatio16_9(int i, int i2) {
        if (i < i2) {
            i2 = i;
            i = i2;
        }
        double d2 = ((double) i) / ((double) i2);
        double d3 = d2 - 1.7777777777777777d;
        return (Math.abs(d2 - 1.3333333333333333d) > Math.abs(d3) || Math.abs(d2 - 1.5d) < 0.02d) && Math.abs(d3) <= Math.abs(d2 - 2.0d);
    }

    public static boolean isNearRatio18_9(int i, int i2) {
        if (i < i2) {
            i2 = i;
            i = i2;
        }
        double d2 = ((double) i) / ((double) i2);
        double d3 = d2 - 1.7777777777777777d;
        return (Math.abs(d2 - 1.3333333333333333d) > Math.abs(d3) || Math.abs(d2 - 1.5d) < 0.02d) && Math.abs(d3) > Math.abs(d2 - 2.0d);
    }

    public static boolean isNeedRemind(String str) {
        if (!sRemindMode.contains(str)) {
            return false;
        }
        DataItemGlobal dataItemGlobal = DataRepository.dataItemGlobal();
        return dataItemGlobal.getBoolean(str + REMIND_SUFFIX, true);
    }

    public static boolean isNoCameraModeSelected(Context context) {
        for (String str : ((ActivityBase) context).getCurrentModule().getSupportedSettingKeys()) {
            if (isSwitchOn(str)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNormalWideLDCEnabled() {
        return DataRepository.dataItemGlobal().getBoolean("pref_camera_normal_wide_ldc_key", getBool(R.bool.pref_camera_normal_wide_ldc_default));
    }

    public static boolean isParameterDescriptionEnable() {
        return DataRepository.dataItemGlobal().getBoolean(KEY_CAMERA_MANUALLY_DESCRIPTION_TIP, true);
    }

    public static boolean isPictureFlawCheckOn() {
        return DataRepository.dataItemFeature().c_19039_0x0008() && DataRepository.dataItemGlobal().getBoolean("pref_pic_flaw_tip", CameraAppImpl.getAndroidContext().getResources().getBoolean(R.bool.pref_pic_flaw_tip_default));
    }

    public static boolean isPopupTipBeautyIntroEnable() {
        return b.Al() && !DataRepository.dataItemGlobal().getBoolean(KEY_POPUP_TIP_BEAUTY_INTRO_CLICKED, false) && DataRepository.dataItemGlobal().getInt(KEY_POPUP_TIP_BEAUTY_INTRO_SHOW_TIMES, 0) < 3;
    }

    public static boolean isPortraitModeBackOn() {
        return DataRepository.dataItemRunning().isSwitchOn("pref_camera_portrait_mode_key");
    }

    public static boolean isPressDownCapture() {
        if (b.Vl()) {
            return isFrontCamera() || !"focus".equals(DataRepository.dataItemGlobal().getString("pref_camera_long_press_shutter_feature_key", getString(R.string.pref_camera_long_press_shutter_feature_default)));
        }
        return false;
    }

    public static boolean isProVideoHistogramOpen(int i) {
        return i == 180 && DataRepository.dataItemGlobal().getBoolean(KEY_CAMERA_PRO_VIDEO_HISTOGRAM, false);
    }

    public static boolean isProVideoLogOpen(int i) {
        return i == 180 && DataRepository.dataItemGlobal().getBoolean(KEY_CAMERA_PRO_VIDEO_LOG_FROMAT, false);
    }

    public static boolean isProximityLockOpen() {
        return DataRepository.dataItemGlobal().getBoolean("pref_camera_proximity_lock_key", true);
    }

    public static boolean isQRCodeReceiverAvailable(Context context) {
        return Util.isPackageAvailable(context, "com.xiaomi.scanner");
    }

    public static boolean isRecordLocation() {
        return b.Wl() && DataRepository.dataItemGlobal().getBoolean("pref_camera_recordlocation_key", false);
    }

    public static boolean isSREnable() {
        return b.isSupportSuperResolution() && DataRepository.dataItemGlobal().getBoolean("pref_camera_sr_enable_key", CameraAppImpl.getAndroidContext().getResources().getBoolean(R.bool.pref_camera_sr_enable_default));
    }

    public static boolean isSaveGroushotPrimitiveOn() {
        return DataRepository.dataItemGlobal().getBoolean(KEY_CAMERA_GROUPSHOT_PRIMITIVE, false);
    }

    public static boolean isScanQRCode(Context context) {
        return !isFrontCamera() && isQRCodeReceiverAvailable(context) && b.rl() && !b.Te() && DataRepository.dataItemGlobal().getBoolean("pref_scan_qrcode_key", Boolean.valueOf(getString(R.string.pref_scan_qrcode_default)).booleanValue());
    }

    public static boolean isSettingsVideoSATEnable() {
        return DataRepository.dataItemGlobal().getBoolean(KEY_CAMERA_VIDEO_SAT_ENABLE, true);
    }

    public static boolean isShowFirstUseHint() {
        return DataRepository.dataItemGlobal().getBoolean("pref_camera_first_use_hint_shown_key", true);
    }

    public static boolean isShowUltraWideIntro() {
        return DataRepository.dataItemGlobal().getInt(KEY_POPUP_ULTRA_WIDE_INTRO_SHOW_TIMES, 0) < 2;
    }

    public static boolean isSubtitleEnabled(int i) {
        return DataRepository.dataItemRunning().getComponentRunningSubtitle().isEnabled(i);
    }

    public static boolean isSuperEISEnabled(int i) {
        return DataRepository.dataItemRunning().getComponentRunningSuperEIS().isEnabled(i);
    }

    public static boolean isSuperNightOn() {
        return DataRepository.dataItemRunning().isSwitchOn(KEY_CAMERA_SUPER_NIGHT);
    }

    public static boolean isSuperNightUWOpen(int i) {
        return DataRepository.dataItemFeature().c_33066_0x0003() && i == 173;
    }

    public static boolean isSupport960VideoEditor() {
        return DataRepository.dataItemFeature().isSupport960VideoEditor();
    }

    public static boolean isSupportBeautyBody() {
        return DataRepository.dataItemRunning().getComponentRunningShine().supportBeautyBody();
    }

    public static boolean isSupportBeautyMakeup() {
        CameraCapabilities currentCameraCapabilities = Camera2DataContainer.getInstance().getCurrentCameraCapabilities();
        Log.d(TAG, "isSupportBeautyMakeup: " + currentCameraCapabilities.isSupportBeautyMakeup());
        return currentCameraCapabilities.isSupportBeautyMakeup();
    }

    public static boolean isSupportFpsRange(int i, int i2, int i3) {
        List<MiCustomFpsRange> supportedCustomFpsRange;
        if (DataRepository.dataItemGlobal().getIntentType() == 0 && (supportedCustomFpsRange = Camera2DataContainer.getInstance().getCapabilities(getCameraId(i3)).getSupportedCustomFpsRange()) != null && !supportedCustomFpsRange.isEmpty()) {
            for (MiCustomFpsRange miCustomFpsRange : supportedCustomFpsRange) {
                if (miCustomFpsRange.getWidth() == i && miCustomFpsRange.getHeight() == i2) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isSupportQuickShot() {
        return DataRepository.dataItemFeature().s_f_s_c() || (DEBUG_FAST_SHOT && !b.pv);
    }

    public static boolean isSupportedDualCameraWaterMark() {
        return b.rm();
    }

    public static boolean isSupportedMetadata() {
        return b.Ml() || isSupportedPortrait();
    }

    public static boolean isSupportedOpticalZoom() {
        return b.isSupportedOpticalZoom() && Camera2DataContainer.getInstance().hasSATCamera();
    }

    public static boolean isSupportedPortrait() {
        return DataRepository.dataItemFeature().c_0x34_OR_T() && Camera2DataContainer.getInstance().hasPortraitCamera();
    }

    public static boolean isSupportedZslShutter() {
        return DataRepository.dataItemFeature().uf();
    }

    public static boolean isSwitchOn(String str) {
        return isTransient(str) ? DataRepository.dataItemRunning().isSwitchOn(str) : isCameraSpecific(str) ? "on".equals(DataRepository.dataItemConfig().getString(str, "off")) : "on".equals(DataRepository.dataItemGlobal().getString(str, "off"));
    }

    public static boolean isTiltShiftOn() {
        return DataRepository.dataItemRunning().isSwitchOn("pref_camera_tilt_shift_mode");
    }

    public static boolean isTimeWaterMarkOpen() {
        return !ModuleManager.isDocumentMode() && !ModuleManager.isIDCardMode() && DataRepository.dataItemGlobal().getBoolean("pref_time_watermark_key", false) && !isUltraPixelFront32MPOn();
    }

    public static boolean isTransient(String str) {
        return str.equals("pref_delay_capture_mode") || str.equals("pref_camera_shader_coloreffect_key") || str.equals("pref_camera_scenemode_key") || str.equals("pref_camera_scenemode_setting_key") || str.equals("pref_camera_ubifocus_key") || str.equals("pref_camera_magic_mirror_key") || str.equals("pref_camera_groupshot_mode_key") || str.equals("pref_camera_super_resolution_key") || str.equals("pref_camera_tilt_shift_key") || str.equals("pref_camera_tilt_shift_mode") || str.equals("pref_camera_hand_night_key") || str.equals("pref_video_speed_fast_key") || str.equals("pref_camera_portrait_mode_key") || str.equals("pref_camera_manual_mode_key") || str.equals("pref_camera_square_mode_key") || str.equals("pref_camera_show_gender_age_key") || str.equals("pref_camera_peak_key");
    }

    public static boolean isUltraPixelFront32MPOn() {
        return DataRepository.dataItemRunning().getComponentUltraPixel().isFront32MPSwitchOn();
    }

    public static boolean isUltraPixelOn() {
        if (DataRepository.dataItemRunning().supportUltraPixel() && !ModuleManager.isSquareModule()) {
            return DataRepository.dataItemRunning().getComponentUltraPixel().isSwitchOn();
        }
        return false;
    }

    public static boolean isUltraPixelPortraitFrontOn() {
        if (!DataRepository.dataItemRunning().supportUltraPixelPortrait()) {
            return false;
        }
        return DataRepository.dataItemRunning().isSwitchOn("pref_camera_ultra_pixel_portrait_mode_key");
    }

    public static boolean isUltraPixelRearOn() {
        if (DataRepository.dataItemRunning().supportUltraPixel() && !ModuleManager.isSquareModule()) {
            return DataRepository.dataItemRunning().getComponentUltraPixel().isRearSwitchOn();
        }
        return false;
    }

    public static boolean isUltraWideAngleCameraEnable(int i) {
        return DataRepository.dataItemConfig().getComponentConfigUltraWide().isUltraWideOnInMode(i);
    }

    public static boolean isUltraWideConfigOpen(int i) {
        ComponentConfigUltraWide componentConfigUltraWide = DataRepository.dataItemConfig().getComponentConfigUltraWide();
        if (!componentConfigUltraWide.isSupportUltraWide() || HybridZoomingSystem.IS_3_OR_MORE_SAT) {
            return false;
        }
        return componentConfigUltraWide.isUltraWideOnInMode(i);
    }

    public static boolean isUltraWideLDCEnabled() {
        return DataRepository.dataItemGlobal().getBoolean("pref_camera_ultra_wide_ldc_key", getBool(R.bool.pref_camera_ultra_wide_ldc_default));
    }

    public static boolean isUltraWideVideoLDCEnabled() {
        return DataRepository.dataItemGlobal().getBoolean("pref_camera_ultra_wide_video_ldc_key", getBool(R.bool.pref_camera_ultra_wide_ldc_default));
    }

    public static boolean isVideoBokehOn() {
        return DataRepository.dataItemConfig().isSwitchOn("pref_video_bokeh_key");
    }

    public static boolean isVideoCaptureVisible() {
        return (!b.Uu && !b.Tu) || !isVideoBokehOn();
    }

    public static boolean isVideoQuality8KOpen(int i) {
        return DataRepository.dataItemRunning().getComponentRunning8KVideoQuality().isEnabled(i);
    }

    public static boolean isVideoTagOn() {
        return DataRepository.dataItemFeature().c_32889_0x0001() && DataRepository.dataItemGlobal().getBoolean("pref_camera_video_tag_key", true);
    }

    public static boolean isZoomByCameraSwitchingSupported() {
        return ModuleManager.isPanoramaModule() || ModuleManager.isProModule() || ModuleManager.isWideSelfieModule();
    }

    public static <T> ArrayList<T> listToArrayList(List<T> list) {
        if (list == null) {
            return null;
        }
        if (list instanceof ArrayList) {
            return (ArrayList) list;
        }
        ArrayList<T> arrayList = new ArrayList<>(list.size());
        arrayList.addAll(list);
        return arrayList;
    }

    public static void readEdgePhotoSetting(Context context) {
        if (b.Tl()) {
            boolean z = false;
            if (Settings.System.getInt(context.getContentResolver(), EDGE_HANDGRIP_MODE_PHOTO, 0) == 1) {
                z = true;
            }
            sEdgePhotoEnable = z;
        }
    }

    public static int readExposure() {
        return Util.parseInt(DataRepository.dataItemConfig().getString(KEY_EXPOSURE, "0"), 0);
    }

    public static String readFNumber() {
        return DataRepository.dataItemRunning().getString(KEY_F_NUMBER, getDefaultFNumber());
    }

    public static int readPreferredCameraId() {
        return DataRepository.dataItemGlobal().getCurrentCameraId();
    }

    public static float readZoom() {
        return HybridZoomingSystem.toFloat(DataRepository.dataItemConfig().getString(KEY_ZOOM, "1.0"), 1.0f);
    }

    public static void resetColorEffect() {
        DataProvider.ProviderEditor editor = DataRepository.dataItemConfig().editor();
        editor.remove(KEY_COLOR_EFFECT);
        editor.remove("pref_camera_shader_coloreffect_key");
        editor.apply();
    }

    public static void resetContrast() {
        DataRepository.dataItemGlobal().editor().remove("pref_qc_camera_contrast_key").apply();
    }

    public static void resetExposure() {
        DataProvider.ProviderEditor editor = DataRepository.dataItemConfig().editor();
        editor.remove(KEY_EXPOSURE);
        editor.apply();
    }

    public static void resetEyeLight() {
        setEyeLight("-1");
    }

    public static void resetOpenCameraFailTimes() {
        DataProvider.ProviderEditor editor = DataRepository.dataItemGlobal().editor();
        editor.putLong(KEY_OPEN_CAMERA_FAIL, 0);
        editor.apply();
    }

    public static void resetSaturation() {
        DataRepository.dataItemGlobal().editor().remove("pref_qc_camera_saturation_key").apply();
    }

    public static void resetSharpness() {
        DataRepository.dataItemGlobal().editor().remove("pref_qc_camera_sharpness_key").apply();
    }

    public static void resetWhiteBalance() {
        DataProvider.ProviderEditor editor = DataRepository.dataItemConfig().editor();
        editor.remove(KEY_WHITE_BALANCE);
        editor.apply();
    }

    public static void resetZoom() {
        DataProvider.ProviderEditor editor = DataRepository.dataItemConfig().editor();
        editor.remove(KEY_ZOOM);
        editor.apply();
    }

    public static boolean retainCameraMode() {
        return DataRepository.dataItemGlobal().getBoolean("pref_retain_camera_mode_key", false);
    }

    public static void setAiSceneOpen(int i, boolean z) {
        DataRepository.dataItemConfig().getComponentConfigAi().setAiScene(i, z);
    }

    public static void setAutoZoomEnabled(int i, boolean z) {
        DataRepository.dataItemRunning().getComponentRunningAutoZoom().setEnabled(i, z);
    }

    public static void setBeautyMakeupClicked() {
        DataRepository.dataItemGlobal().editor().putBoolean(KEY_BEAUTY_MAKEUP_CLICKED, true).apply();
    }

    public static void setBeautyRemodelingClicked() {
        DataRepository.dataItemGlobal().editor().putBoolean(KEY_BEAUTY_REMODELING_CLICKED, true).apply();
    }

    public static void setBroadcastKillServiceTime(long j) {
        DataRepository.dataItemGlobal().editor().putLong(KEY_BROADCAST_KILL_SERVICE_TIME, j).apply();
    }

    public static void setCinematicAspectRatioEnabled(int i, boolean z) {
        DataRepository.dataItemConfig().getCinematicAspectRatio().setEnabled(i, z);
    }

    public static void setCurrentLiveMusic(String str, String str2) {
        DataRepository.dataItemLive().editor().putString(KEY_LIVE_MUSIC_PATH, str).apply();
        DataRepository.dataItemLive().editor().putString(KEY_LIVE_MUSIC_HINT, str2).apply();
    }

    public static void setCurrentLiveSpeed(String str) {
        DataRepository.dataItemLive().editor().putString(KEY_LIVE_SPEED, str).apply();
    }

    public static void setCurrentLiveSticker(String str, String str2, String str3) {
        DataRepository.dataItemLive().editor().putString(KEY_LIVE_STICKER, str).apply();
        DataRepository.dataItemLive().editor().putString(KEY_LIVE_STICKER_NAME, str2).apply();
        DataRepository.dataItemLive().editor().putString(KEY_LIVE_STICKER_HINT, str3).apply();
    }

    public static void setCustomWB(int i) {
        DataProvider.ProviderEditor editor = DataRepository.dataItemConfig().editor();
        editor.putInt("pref_qc_manual_whitebalance_k_value_key", i);
        editor.apply();
    }

    public static void setCustomWatermark(String str) {
        DataProvider.ProviderEditor editor = DataRepository.dataItemGlobal().editor();
        editor.putString(KEY_CUSTOM_WATERMARK, str);
        editor.apply();
    }

    public static void setDocumentModeOn(int i, boolean z) {
        DataRepository.dataItemRunning().getComponentRunningDocument().setEnabled(i, z);
    }

    public static void setDualCameraWaterMarkOpen(boolean z) {
        if (isSupportedDualCameraWaterMark() && isBackCamera()) {
            DataRepository.dataItemGlobal().editor().putBoolean("pref_dualcamera_watermark_key", z).apply();
        }
    }

    public static void setEdgeMode(Context context, boolean z) {
        if (context != null) {
            if (z) {
                readEdgePhotoSetting(context);
            }
            if (isEdgePhotoEnable()) {
                InputManager inputManager = (InputManager) context.getSystemService("input");
                int i = 1;
                Class<?>[] clsArr = {InputManager.class};
                c method = Util.getMethod(clsArr, "switchTouchEdgeMode", "(I)V");
                if (method != null) {
                    Class<?> cls = clsArr[0];
                    Object[] objArr = new Object[1];
                    if (!z) {
                        i = getSystemEdgeMode(context);
                    }
                    objArr[0] = Integer.valueOf(i);
                    method.a(cls, inputManager, objArr);
                }
            }
        }
    }

    public static void setEyeLight(String str) {
        DataRepository.dataItemRunning().getComponentRunningEyeLight().setComponentValue(160, str);
    }

    public static void setFaceBeautyLevel(int i) {
        setFaceBeautyRatio("pref_beautify_level_key_capture", i);
    }

    public static void setFaceBeautyRatio(String str, int i) {
        if (BeautyConstant.isLiveBeautyModeKey(str)) {
            DataRepository.dataItemLive().editor().putInt(BeautyConstant.wrappedSettingKey(str), i).apply();
        } else if (!"pref_beautify_skin_smooth_ratio_key".equals(str) || !ModuleManager.isVideoModule()) {
            DataRepository.dataItemConfig().editor().putInt(BeautyConstant.wrappedSettingKey(str), i).apply();
        } else {
            DataRepository.dataItemRunning().putInt(BeautyConstant.wrappedSettingKey(str), i);
        }
    }

    public static void setFaceBeautySmoothLevel(int i) {
        setFaceBeautyRatio("pref_beautify_skin_smooth_ratio_key", i);
    }

    public static void setFaceBeautySwitch(String str) {
        DataRepository.dataItemConfig().editor().putString(KEY_FACE_BEAUTY_SWITCH, str).apply();
    }

    public static void setFlashMode(int i, String str) {
        DataRepository.dataItemConfig().getComponentFlash().setComponentValue(i, str);
    }

    public static void setFocusMode(String str) {
        DataRepository.dataItemConfig().editor().putString("pref_camera_focus_mode_key", str).apply();
    }

    public static void setFocusModeSwitching(boolean z) {
        DataRepository.dataItemConfig().editor().putBoolean("pref_qc_focus_mode_switching_key", z).apply();
    }

    public static void setFocusPosition(int i) {
        DataProvider.ProviderEditor editor = DataRepository.dataItemConfig().editor();
        editor.putString(KEY_QC_FOCUS_POSITION, String.valueOf(i));
        editor.apply();
    }

    public static void setGifSwitch(boolean z) {
        DataRepository.dataItemLive().getMimojiStatusManager2().setIsGifState(z);
    }

    public static void setGoogleLensAvailability(boolean z) {
        sGoolgeLensAvilability = z;
    }

    public static void setGradienterOn(boolean z) {
        if (!isFrontCamera() && b.Fl()) {
            DataRepository.dataItemConfig().getComponentConfigGradienter().setComponentValue(DataRepository.dataItemGlobal().getCurrentMode(), z ? "on" : "off");
        }
    }

    public static void setHSRValue(boolean z, String str) {
        DataRepository.dataItemConfig().editor().putString(z ? "pref_camera_hsr_value_key_u" : KEY_CAMERA_HSR_VALUE, str).apply();
    }

    public static void setHandGestureStatus(boolean z) {
        if (DataRepository.dataItemRunning().getBoolean("pref_hand_gesture", false) != z) {
            DataRepository.dataItemRunning().putBoolean("pref_hand_gesture", z);
        }
    }

    public static void setIsFromProVideoMudule(boolean z) {
        DataRepository.dataItemConfig().putBoolean(KEY_CAMERA_FROM_PRO_VIDEO_MODULE, z);
    }

    public static void setLabOptionsVisible(boolean z) {
        DataRepository.dataItemGlobal().editor().putBoolean(KEY_CAMERA_LAB_OPTIONS_VISIBLE, z).apply();
    }

    public static void setLensDirtyDetectEnable(boolean z) {
        DataRepository.dataNormalItemConfig().editor().putBoolean(KEY_LENS_DIRTY_DETECT_ENABLED, z).apply();
    }

    public static void setLensIndex(int i) {
        sLensIndex = i;
    }

    public static void setLiveAllSwitchAddValue(int i) {
        DataRepository.dataItemLive().editor().putInt(KEY_LIVE_ALL_SWITCH_ADD_VALUE, i).apply();
    }

    public static void setLiveBeautyStatus(boolean z) {
        if (DataRepository.dataItemLive().getBoolean(KEY_LIVE_BEAUTY_STATUS, false) != z) {
            DataRepository.dataItemLive().putBoolean(KEY_LIVE_BEAUTY_STATUS, z);
        }
    }

    public static void setLiveModuleClicked(boolean z) {
        DataRepository.dataItemGlobal().editor().putBoolean(KEY_LIVE_MODULE_CLICKED, z).apply();
    }

    public static void setLiveShotOn(boolean z) {
        if (DataRepository.dataItemFeature().vd()) {
            DataRepository.dataItemRunning().getComponentRunningLiveShot().setLiveShotOn(z);
        }
    }

    public static void setLiveStickerLastCacheTime(long j) {
        DataProvider.ProviderEditor editor = DataRepository.dataItemGlobal().editor();
        editor.putLong(KEY_LIVE_STICKER_LAST_CACHE_TIME, j);
        editor.apply();
    }

    public static void setLiveStickerRedDotTime(long j) {
        DataProvider.ProviderEditor editor = DataRepository.dataItemGlobal().editor();
        editor.putLong(KEY_LIVE_STICKER_RED_DOT_TIME, j);
        editor.apply();
    }

    public static void setLocalWordsVersion(int i) {
        DataRepository.dataItemGlobal().editor().putInt(KEY_LOCALWORDS_VERSION, i).apply();
    }

    public static void setMacro2Sat(boolean z) {
        sMacro2Sat = z;
    }

    public static void setMimojiModleVersion(String str) {
        DataRepository.dataItemLive().editor().putString(KEY_MIMOJI_MODEL_VERSION, str).apply();
    }

    public static void setPanoramaMoveDirection(int i) {
        DataRepository.dataItemRunning().putInt("pref_panorana_move_direction_key", i);
    }

    public static void setPlayToneOnCaptureStart(boolean z) {
        DataRepository.dataItemConfig().editor().putBoolean(KEY_PLAY_TONE_ON_CAPTURE_START, z).apply();
    }

    public static void setPopupTipBeautyIntroClicked() {
        DataRepository.dataItemGlobal().editor().putBoolean(KEY_POPUP_TIP_BEAUTY_INTRO_CLICKED, true).apply();
    }

    public static void setPopupUltraWideIntroClicked() {
        DataRepository.dataItemGlobal().editor().putInt(KEY_POPUP_ULTRA_WIDE_INTRO_SHOW_TIMES, 2).apply();
    }

    public static void setPriorityStoragePreference(boolean z) {
        DataProvider.ProviderEditor editor = DataRepository.dataItemGlobal().editor();
        editor.putBoolean("pref_priority_storage", z);
        editor.apply();
    }

    public static void setProVideoHistogramOpen(boolean z) {
        DataRepository.dataItemGlobal().putBoolean(KEY_CAMERA_PRO_VIDEO_HISTOGRAM, z);
    }

    public static void setProVideoLog(boolean z) {
        DataRepository.dataItemGlobal().putBoolean(KEY_CAMERA_PRO_VIDEO_LOG_FROMAT, z);
    }

    public static void setRestoredFlashMode(String str) {
        DataProvider.ProviderEditor editor = DataRepository.dataItemConfig().editor();
        if (str == null) {
            editor.remove(KEY_RESTORED_FLASH_MODE);
        } else {
            editor.putString(KEY_RESTORED_FLASH_MODE, str);
        }
        editor.apply();
    }

    public static void setShaderEffect(int i) {
        int currentMode = DataRepository.dataItemGlobal().getCurrentMode();
        DataItemRunning dataItemRunning = DataRepository.dataItemRunning();
        ComponentConfigFilter componentConfigFilter = dataItemRunning.getComponentConfigFilter();
        Log.d(TAG, "setShaderEffect: mode = " + currentMode + ", value = " + i);
        componentConfigFilter.setComponentValue(currentMode, String.valueOf(i));
        if (currentMode == 180) {
            dataItemRunning.getComponentRunningShine().setVideoBeautySwitch(180, i != 0);
        }
        Log.d(TAG, "setShaderEffect: getValue = " + componentConfigFilter.getComponentValue(currentMode));
    }

    public static void setSmartShutterPosition(String str) {
        DataProvider.ProviderEditor editor = DataRepository.dataItemGlobal().editor();
        editor.putString(KEY_SMART_SHUTTER_POSITION, str);
        editor.apply();
    }

    public static void setSubtitleEnabled(int i, boolean z) {
        DataRepository.dataItemRunning().getComponentRunningSubtitle().setEnabled(i, z);
    }

    public static void setSuperEISEnabled(int i, boolean z) {
        DataRepository.dataItemRunning().getComponentRunningSuperEIS().setEnabled(i, z);
    }

    public static void setSuperNightOn(boolean z) {
        DataRepository.dataItemRunning().putBoolean(KEY_CAMERA_SUPER_NIGHT, z);
    }

    public static void setTTLiveMusicJsonCache(String str) {
        DataProvider.ProviderEditor editor = DataRepository.dataItemLive().editor();
        editor.putString(KEY_TT_LIVE_MUSIC_JSON_CACHE, str);
        editor.apply();
    }

    public static void setTTLiveStickerJsonCache(String str) {
        DataProvider.ProviderEditor editor = DataRepository.dataItemLive().editor();
        editor.putString(KEY_TT_LIVE_STICKER_JSON_CACHE, str);
        editor.apply();
    }

    public static void setTTLiveStickerNeedRedDot(boolean z) {
        DataProvider.ProviderEditor editor = DataRepository.dataItemLive().editor();
        editor.putBoolean(KEY_LIVE_STICKER_NEED_RED_DOT, z);
        editor.apply();
    }

    public static void setUltraWideConfig(int i, boolean z) {
        DataRepository.dataItemConfig().getComponentConfigUltraWide().setComponentValue(i, z ? "ON" : "OFF");
    }

    public static void setVideoBokehRatio(float f2) {
        DataRepository.dataItemRunning().putFloat(KEY_VIDEO_BOKEH_ADJUST, f2);
    }

    public static void setVideoQuality8K(int i, boolean z) {
        DataRepository.dataItemRunning().getComponentRunning8KVideoQuality().setEnabled(i, z);
    }

    public static boolean shouldNormalWideLDCBeVisibleInMode(int i) {
        boolean isSupportNormalWideLDC = DataRepository.dataItemFeature().isSupportNormalWideLDC();
        if (i == 163 || i == 165 || i == 167) {
            return isSupportNormalWideLDC;
        }
        return false;
    }

    public static boolean shouldShowLensDirtyDetectHint() {
        long currentTimeMillis = System.currentTimeMillis();
        Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(currentTimeMillis);
        Calendar instance2 = Calendar.getInstance();
        instance2.setTimeInMillis(DataRepository.dataNormalItemConfig().getLong(KEY_LENS_DIRTY_DETECT_DATE, currentTimeMillis));
        return (instance.get(1) == instance2.get(1) && instance.get(2) == instance2.get(2) && instance.get(5) == instance2.get(5) && DataRepository.dataNormalItemConfig().getInt(KEY_LENS_DIRTY_DETECT_TIMES, 0) >= 3) ? false : true;
    }

    public static boolean shouldShowUltraWideSatTip(int i) {
        if (i == 169) {
            i = 162;
        }
        if (i == 165) {
            i = 163;
        }
        String str = "pref_camera_first_ultra_wide_sat_use_hint_shown_key_" + Integer.toHexString(i);
        boolean z = DataRepository.dataItemGlobal().getBoolean(str, true);
        if (z) {
            DataRepository.dataItemGlobal().editor().putBoolean(str, false).apply();
        }
        return z;
    }

    public static boolean shouldShowUltraWideStickyTip(int i) {
        return (163 == i || 165 == i || 162 == i) && isUltraWideConfigOpen(i);
    }

    public static boolean shouldUltraWideLDCBeVisibleInMode(int i) {
        boolean isSupportUltraWideLDC = DataRepository.dataItemFeature().isSupportUltraWideLDC();
        if (i == 163 || i == 165 || i == 167) {
            return isSupportUltraWideLDC && isBackCamera();
        }
        if (i != 173) {
            return false;
        }
        return isSuperNightUWOpen(i) && isSupportUltraWideLDC && isBackCamera();
    }

    public static boolean shouldUltraWideVideoLDCBeVisibleInMode(int i) {
        return false;
    }

    public static boolean showGenderAge() {
        return DataRepository.dataItemRunning().isSwitchOn("pref_camera_show_gender_age_key");
    }

    public static List<String> sizeListToStringList(List<Camera.Size> list) {
        if (list == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (Camera.Size size : list) {
            arrayList.add(String.format(Locale.ENGLISH, "%dx%d", Integer.valueOf(size.width), Integer.valueOf(size.height)));
        }
        return arrayList;
    }

    public static boolean supportGoogleLens() {
        return Util.isGlobalVersion() && !b.Te();
    }

    public static void switchOffUltraPixel() {
        DataRepository.dataItemRunning().getComponentUltraPixel().switchOff();
    }

    public static void switchOnUltraPixel(@ComponentRunningUltraPixel.UltraPixelSupport String str) {
        DataRepository.dataItemRunning().getComponentUltraPixel().switchOn(str);
    }

    public static void updateFocusMode() {
        String focusMode = getFocusMode();
        String str = (!ModuleManager.isProModule() || getFocusPosition() == 1000) ? AutoFocus.LEGACY_CONTINUOUS_PICTURE : "manual";
        if (!str.equals(focusMode)) {
            setFocusModeSwitching(true);
            setFocusMode(str);
        }
    }

    public static long updateOpenCameraFailTimes() {
        DataProvider.ProviderEditor editor = DataRepository.dataItemGlobal().editor();
        long j = DataRepository.dataItemGlobal().getLong(KEY_OPEN_CAMERA_FAIL, 0) + 1;
        editor.putLong(KEY_OPEN_CAMERA_FAIL, j);
        editor.apply();
        return j;
    }

    public static void updateRecordLocationPreference(boolean z) {
        DataProvider.ProviderEditor editor = DataRepository.dataItemGlobal().editor();
        editor.putBoolean("pref_camera_recordlocation_key", z);
        editor.apply();
    }

    public static void upgradeGlobalPreferences() {
        DataItemGlobal dataItemGlobal = DataRepository.dataItemGlobal();
        int i = dataItemGlobal.getInt(KEY_VERSION, 4);
        if (i < 4) {
            DataRepository.dataItemGlobal().editor().clear().apply();
            int[] iArr = {0, 1};
            for (int i2 : iArr) {
                DataProvider provider = DataRepository.provider();
                provider.dataConfig(0, i2).editor().clear().apply();
                provider.dataConfig(1, i2).editor().clear().apply();
            }
            dataItemGlobal.editor().putInt(KEY_VERSION, 4).apply();
            if (i == 1) {
                deleteObsoletePreferences();
            }
        }
    }

    public static void writeExposure(int i) {
        DataProvider.ProviderEditor editor = DataRepository.dataItemConfig().editor();
        editor.putString(KEY_EXPOSURE, Integer.toString(i));
        editor.apply();
    }

    public static void writeFNumber(String str) {
        DataRepository.dataItemRunning().putString(KEY_F_NUMBER, str);
    }

    public static void writeZoom(float f2) {
        DataProvider.ProviderEditor editor = DataRepository.dataItemConfig().editor();
        editor.putString(KEY_ZOOM, Float.toString(f2));
        editor.apply();
    }
}
