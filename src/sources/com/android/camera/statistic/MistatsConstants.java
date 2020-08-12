package com.android.camera.statistic;

public interface MistatsConstants {
    public static final String TAG = "MistatsConstants";
    public static final long UPLOAD_POLICY_INTERVAL = 300000;

    public interface AIWatermark {
        public static final String AI_WATERMARK = "ai_watermark";
        public static final String AI_WATERMARK_AI = "ai_watermark_ai";
        public static final String AI_WATERMARK_CATEGORY = "ai_watermark_category";
        public static final String AI_WATERMARK_CLOSE = "ai_watermark_close";
        public static final String AI_WATERMARK_KEY = "ai_watermark_key";
        public static final String AI_WATERMARK_LIST_HIDE = "ai_watermark_list_hide";
        public static final String AI_WATERMARK_LIST_SHOW = "ai_watermark_list_show";
        public static final String AI_WATERMARK_MANUAL = "ai_watermark_manual";
        public static final String AI_WATERMARK_MOVE = "ai_watermark_move";
        public static final String AI_WATERMARK_OPEN = "ai_watermark_open";
        public static final String AI_WATERMARK_ORIENTATION = "ai_watermark_orientation";
        public static final String AI_WATERMARK_SELECT = "ai_watermark_select";
        public static final String AI_WATERMARK_TYPE = "ai_watermark_type";
    }

    public interface AlgoAttr {
        public static final String PARAM_AI_SCENE = "attr_aiScene";
        public static final String PARAM_BOKEN = "attr_BOKEH";
        public static final String PARAM_HDR = "attr_HDR";
        public static final String VALUE_HDR_OUT_BUTTON = "hdr_out_button";
        public static final String VAULE_AI_CC = "aiCC";
        public static final String VAULE_AI_SCENE = "aiScene";
        public static final String VAULE_START_AI_DETECT_BUTTON = "start_ai_detect_googleLens";
    }

    public interface AutoZoom {
        public static final String AUTOZOOM_LOST_10_MORE = "lost_more_10";
        public static final String AUTO_ZOOM_STATE = "attr_autozoom_state";
        public static final String KEY_AUTO_ZOOM = "key_auto_zoom";
        public static final String PARAM_SELECT_OBJECT_STATE = "attr_select_object_state";
        public static final String PARAM_TRACKING_LOST_OBJECT = "attr_tracking_lost_object";
        public static final String VALUE_AUTOZOOM_NOT_ULTRA = "not_ultra";
        public static final String VALUE_AUTOZOOM_ULTRA = "use_ultra";
        public static final String VALUE_BEFORE_RECORDING = "before_record";
        public static final String VALUE_IN_RECORDING = "in_recording";
    }

    public interface BaseEvent {
        public static final String ACCEPT = "accept";
        public static final String ADD = "add";
        public static final String AUTO = "auto";
        public static final String AUTO_OFF = "auto-off";
        public static final String AUTO_ON = "auto-on";
        public static final String AWB = "attr_awb";
        public static final String BACK = "back";
        public static final String CALCULATE = "calculate";
        public static final String CAMERA_CALLER = "camera_caller";
        public static final String CAPTURE = "capture";
        public static final String CLICK = "click";
        public static final String COST_TIME = "attr_cost_time";
        public static final String COUNT = "attr_count";
        public static final String CREATE = "create";
        public static final String DELETE = "delete";
        public static final String DESTORY = "destory";
        public static final String EDIT = "edit";
        public static final String EVENT_NAME = "attr_event_name";
        public static final String EXIT = "exit";
        public static final String FEATURE_NAME = "attr_feature_name";
        public static final String FRONT = "front";
        public static final String GESTURE = "gesture";
        public static final String HIGH = "high";
        public static final String ISO = "attr_iso";
        public static final String KEY_CAPTURE = "key_capture";
        public static final String KEY_VIDEO = "key_video";
        public static final String LIFE_STATE = "attr_life_state";
        public static final String LOW = "low";
        public static final String MODE = "attr_mode";
        public static final String MODULE_NAME = "attr_module_name";
        public static final String NONE = "none";
        public static final String NORMAL = "normal";
        public static final String OPERATE_STATE = "attr_operate_state";
        public static final String OTHERS = "others";
        public static final String PARAM_LYING_DIRECT = "attr_lying_direct";
        public static final String PARAM_PHOTO_RATIO_MOVIE = "attr_picture_ration_movie";
        public static final String PARAM_PICTURE_RATIO = "attr_picture_ration";
        public static final String PARAM_VIDEO_RATIO_MOVIE = "attr_video_ratio_movie";
        public static final String PAUSE = "pause";
        public static final String PHOTO = "photo";
        public static final String PICTURE_RESOLUTION = "attr_picture_resolution";
        public static final String PREVIEW = "preview";
        public static final String QUALITY = "attr_quality";
        public static final String QUALITY_1080P = "1080p";
        public static final String QUALITY_1080P_60FPS = "1080p-60fps";
        public static final String QUALITY_2160P = "4k";
        public static final String QUALITY_480P = "480p";
        public static final String QUALITY_4K_60FPS = "4k-60fps";
        public static final String QUALITY_720P = "720p";
        public static final String QUALITY_8K_30FPS = "8k-30fps";
        public static final String QUERY = "query";
        public static final String REJECT = "reject";
        public static final String RESET = "reset";
        public static final String RESUME = "resume";
        public static final String SAVE = "save";
        public static final String SCHEDULE = "schedule";
        public static final String SENSOR_ID = "attr_sensor_id";
        public static final String SLIDE = "slide";
        public static final String SLIDER = "slider";
        public static final String START = "start";
        public static final String STOP = "stop";
        public static final String SWITCH_STATE = "attr_switch_state";
        public static final String TO = "attr_to";
        public static final String TRIGGER_MODE = "attr_trigger_mode";
        public static final String UNSPECIFIED = "unspecified";
        public static final String VALUE = "attr_value";
        public static final String VALUE_AFTER = "after";
        public static final String VALUE_BEFOR = "before";
        public static final String VALUE_CANCLE = "cancle";
        public static final String VALUE_CLOSE = "close";
        public static final String VALUE_CONFIRM = "confirm";
        public static final String VALUE_DOING = "doing";
        public static final String VALUE_ERROR_MSG = "attr_error_msg";
        public static final String VALUE_FAILED = "failed";
        public static final String VALUE_FALSE = "false";
        public static final String VALUE_NOT_NULL = "not_null";
        public static final String VALUE_NULL = "null";
        public static final String VALUE_OFF = "off";
        public static final String VALUE_ON = "on";
        public static final String VALUE_OPEN = "open";
        public static final String VALUE_SUCCESS = "success";
        public static final String VALUE_TRUE = "true";
        public static final String VIDEO = "video";
        public static final String VOLUME = "volume";
    }

    public interface BeautyAttr {
        public static final String BEAUTY_BLUSHER = "blusher";
        public static final String BEAUTY_CHIN = "chin";
        public static final String BEAUTY_ENLARGE_EYE = "enlarge_eye";
        public static final String BEAUTY_EYEBROW_DYE = "eyebrow_dye";
        public static final String BEAUTY_JELLY_LIPS = "jelly_lips";
        public static final String BEAUTY_LEVEL = "beauty_level";
        public static final String BEAUTY_LIPS = "lips";
        public static final String BEAUTY_NECK = "neck";
        public static final String BEAUTY_NOSE = "nose";
        public static final String BEAUTY_PUPIL_LINE = "pupil_line";
        public static final String BEAUTY_RISORIUS = "risorius";
        public static final String BEAUTY_SKIN_COLOR = "skin_color";
        public static final String BEAUTY_SKIN_SMOOTH = "skin_smooth";
        public static final String BEAUTY_SLIM_FACE = "slim_face";
        public static final String BEAUTY_SLIM_NOSE = "slim_nose";
        public static final String BEAUTY_SMILE = "smile";
        public static final String BEAUTY_TYPE_FACE = "beauty_face";
        public static final String EYE_LIGHT = "eye_sparkle";
        public static final String HAIRLINE = "hairline";
        public static final String KEY_BEAUTY = "key_beauty";
        public static final String KEY_BEAUTY_CLICK = "key_beauty_click";
        public static final String KEY_BEAUTY_FACE = "key_beauty_face";
        public static final String KEY_BEAUTY_OLD = "key_beauty_old";
        public static final String PARAM_BEAUTY_BLUSHER = "attr_blusher";
        public static final String PARAM_BEAUTY_CHIN = "attr_chin";
        public static final String PARAM_BEAUTY_ENLARGE_EYE = "attr_enlarge_eye";
        public static final String PARAM_BEAUTY_EYEBROW_DYE = "attr_eyebrow_dye";
        public static final String PARAM_BEAUTY_JELLY_LIPS = "attr_jelly_lips";
        public static final String PARAM_BEAUTY_LEVEL = "attr_beauty_level";
        public static final String PARAM_BEAUTY_LIPS = "attr_lips";
        public static final String PARAM_BEAUTY_MAKEUP_SWITCH = "attr_beauty_makeup_switch";
        public static final String PARAM_BEAUTY_NECK = "attr_neck";
        public static final String PARAM_BEAUTY_NOSE = "attr_nose";
        public static final String PARAM_BEAUTY_PUPIL_LINE = "attr_pupil_line";
        public static final String PARAM_BEAUTY_RISORIUS = "attr_risorius";
        public static final String PARAM_BEAUTY_SKIN_COLOR = "attr_skin_color";
        public static final String PARAM_BEAUTY_SKIN_SMOOTH = "attr_skin_smooth";
        public static final String PARAM_BEAUTY_SLIM_FACE = "attr_slim_face";
        public static final String PARAM_BEAUTY_SLIM_NOSE = "attr_slim_nose";
        public static final String PARAM_BEAUTY_SMILE = "attr_smile";
        public static final String PARAM_BEAUTY_SWITCH = "attr_beauty_switch";
        public static final String PARAM_BEAUTY_TYPE = "attr_beauty_type";
        public static final String PARAM_EYE_LIGHT = "attr_eye_sparkle";
        public static final String PARAM_HAIRLINE = "attr_hairline";
        public static final String VALUE_BEAUTY_BOTTOM_TAB = "beauty_bottom_tab";
        public static final String VALUE_BEAUTY_FACE_CLEAR = "beauty_face_clear";
        public static final String VALUE_BEAUTY_FACE_RESET = "beauty_face_reset";
        public static final String VALUE_BEAUTY_SHOW_BOTTOM_BUTTON = "beauty_show_bottom_button";
        public static final String VALUE_BEAUTY_TOP_BUTTON = "beauty_top_button";
        public static final String VALUE_BEAUTY_V1_BOTTOM_TAB = "beauty_v1_bottom_tab";
        public static final String VALUE_BOKEH_BOTTOM_TAB = "bokeh_bottom_tab";
        public static final String VALUE_MAKEUP_BOTTOM_TAB = "makeup_bottom_tab";
        public static final String VALUE_MAKEUP_CLEAR = "makeup_clear";
        public static final String VALUE_MAKEUP_RESET = "makeup_reset";
    }

    public interface BeautyBodySlimAttr {
        public static final String BEAUTY_HEAD_SLIM = "head_slim";
        public static final String BEAUTY_LEG_SLIM = "leg_slim";
        public static final String BEAUTY_PORT = "attr_port";
        public static final String BEAUTY_SHOULDER_SLIM = "shoulder_slim";
        public static final String BEAUTY_TYPE_BODY_SLIM = "body_slim";
        public static final String BEAUTY_WHOLE_BODY_SLIM = "whole_body_slim";
        public static final String BUTT_SLIM = "butt_slim";
        public static final String EIGHTTY = "80+";
        public static final String FIFTY = "50+";
        public static final String FOURTY = "40+";
        public static final String KEY_BODY_SLIM = "key_body_slim";
        public static final String NINETY = "90+";
        public static final String ONE = "1+";
        public static final String PARAM_BEAUTY_BODY_SLIM = "attr_body_slim";
        public static final String PARAM_BEAUTY_HEAD_SLIM = "attr_head_slim";
        public static final String PARAM_BEAUTY_LEG_SLIM = "attr_leg_slim";
        public static final String PARAM_BEAUTY_SHOULDER_SLIM = "attr_shoulder_slim";
        public static final String PARAM_BEAUTY_WHOLE_BODY_SLIM = "attr_whole_body_slim";
        public static final String PARAM_BUTT_SLIM = "attr_butt_slim";
        public static final String SEVENTY = "70+";
        public static final String SIXTY = "60+";
        public static final String TEN = "10+";
        public static final String THIRDTY = "30+";
        public static final String TWENTY = "20+";
        public static final String ZERO = "0+";
    }

    public interface CaptureAttr {
        public static final String PARAM_ASD_DETECT_TIP = "attr_asd_detect_tip";
        public static final String PARAM_TIMER = "attr_timer";
        public static final String PARAM_TIMER_CHANGED = "attr_timer_changed";
        public static final String VALUE_ASD_BACKLIT_TIP = "asd_backlit_tip";
        public static final String VALUE_ASD_DIRTY_TIP = "asd_dirty_tip";
        public static final String VALUE_ASD_DOCUMENT_BLUR_TIP = "asd_document_blur_tip";
        public static final String VALUE_ASD_MACRO_TIP = "asd_macro_tip";
        public static final String VALUE_ASD_PORTRAIT_TIP = "asd_portrait_tip";
        public static final String VALUE_ASD_SUPER_NIGHT_TIP = "asd_super_night_tip";
    }

    public interface CaptureSence {
        public static final String AUDIO_CAPTURE = "audio_capture";
        public static final String BURST = "burst";
        public static final String EXPOSURE_VIEW = "exposure_view";
        public static final String FINGERPRINT = "fingerprint";
        public static final String FOCUS_SHOOT = "focus_shoot";
        public static final String HAND_GESTURE = "hand_gesture";
        public static final String KEYCODE_CAMERA = "keycode_camera";
        public static final String KEYCODE_DPAD = "keycode_dpad";
        public static final String KEY_BURST_SHOT_TIMES = "key_burst_shot_times";
        public static final String KEY_SNAP_CAMERA = "key_snap_camera";
        public static final String OBJECT_TRACK = "object_track";
        public static final String PARAM_BURST_COUNT = "attr_burst_count";
        public static final String PARAM_PALM_SHUTTER = "attr_palm_shutter";
        public static final String SHUTTER_BUTTON = "shutter_button";
        public static final String SNAP = "snap";
        public static final String VOLUME = "volume";
    }

    public interface CostTime {
        public static final String KEY_CAMERA_PERPORMANCE = "key_camera_performance";
        public static final String START_APP_COST = "start_app_cost";
        public static final String TAKE_PICTURE_COST = "take_picture_cost";
    }

    public interface Error {
        public static final String CAMERA_BROADCAST_KILL_SERVICE = "camera_broadcast_kill_service";
        public static final String CAMERA_ERROR_DIALOG_SHOW = "camera_error_dialog_show";
        public static final String CAMERA_HARDWARE_ERROR = "camera_hardware_error";
        public static final String KEY_CAMERA_EXCEPTION = "key_camera_exception";
    }

    public interface FeatureName {
        public static final String KEY_COMMON = "key_common";
        public static final String KEY_COMMON_TIPS = "key_common_tips";
        public static final String VALUE_AI_DETECT_CHANGED = "ai_detect_changed";
        public static final String VALUE_DOCUMENT_MODE = "value_document_mode";
        public static final String VALUE_EV_ADJUSTED = "ev_adjusted";
        public static final String VALUE_GENDER_AGE = "gender_age";
        public static final String VALUE_GOTO_GALLERY = "goto_gallery";
        public static final String VALUE_GOTO_ID_CARD = "goto_id_card";
        public static final String VALUE_GOTO_SETTINGS = "goto_settings";
        public static final String VALUE_GRADIENTER = "gradienter";
        public static final String VALUE_HHT = "HHT";
        public static final String VALUE_MAGIC_MIRROR = "magic_mirror";
        public static final String VALUE_MENU_MORE = "menu_more";
        public static final String VALUE_METER_ICON_CLICK = "meter_icon_click";
        public static final String VALUE_PHOTO_ASSISTANCE_TIP_CLICK = "photo_assistance_click";
        public static final String VALUE_QRCODE_DETECTED = "qrcode_detected";
        public static final String VALUE_REFERENCE_LINE = "reference_line";
        public static final String VALUE_SUBTITLE = "attr_video_subtitle";
        public static final String VALUE_SUBTITLE_NETWORK_INTERRUPTION = "subtitle_network_interruption";
        public static final String VALUE_SUBTITLE_RECORDING = "attr_subtitle_recording";
        public static final String VALUE_SUBTITLE_START_NO_NETWORK = "subtitle_start_no_network";
        public static final String VALUE_SUBTITLE_START_RECIRDING = "subtitle_start_recording";
        public static final String VALUE_T2T_TIMES = "value_t2t_times";
        public static final String VALUE_TILTSHIFT = "tiltshift";
        public static final String VALUE_TOPMENU_GRADIENTER = "topmenu_gradienter";
        public static final String VALUE_TRIGGER_SUBTITLE = "trigger_subtitle";
    }

    public interface FilterAttr {
        public static final String A10_NIGHT = "A10_NIGHT";
        public static final String A11_SNOW = "A11_SNOW";
        public static final String A12_SEA = "A12_SEA";
        public static final String A13_AUTUMN = "A13_AUTUMN";
        public static final String A14_CANDLELIGHT = "A14_CANDLELIGHT";
        public static final String A15_CAR = "A15_CAR";
        public static final String A16_GRASS = "A16_GRASS";
        public static final String A17_MAPLE_LEAVES = "A17_MAPLE_LEAVES";
        public static final String A18_SUCCULENT = "A18_SUCCULENT";
        public static final String A19_BUILDING = "A19_BUILDING";
        public static final String A1_DOC = "A1_DOC";
        public static final String A20_CITY = "A20_CITY";
        public static final String A21_CLOUD = "A21_CLOUD";
        public static final String A22_OVERCAST = "A22_OVERCAST";
        public static final String A23_BACKLIGHT = "A23_BACKLIGHT";
        public static final String A24_SILHOUETTE = "A24_SILHOUETTE";
        public static final String A25_HUMAN = "A25_HUMAN";
        public static final String A26_JEWELRY = "A26_JEWELRY";
        public static final String A27_BUDDHA = "A27_BUDDHA";
        public static final String A28_COW = "A28_COW";
        public static final String A29_CURRY = "A29_CURRY";
        public static final String A2_FLOWER = "A2_FLOWER";
        public static final String A30_MOTORBIKE = "A30_MOTORBIKE";
        public static final String A31_TEMPLE = "A31_TEMPLE";
        public static final String A32_BEACH = "A32_BEACH";
        public static final String A33_DRIVING = "A33_DRIVING";
        public static final String A3_FOOD = "A3_FOOD";
        public static final String A4_PPT = "A4_PPT";
        public static final String A5_SKY = "A5_SKY";
        public static final String A6_SUNRISE_SUNSET = "A6_SUNRISE_SUNSET";
        public static final String A7_CAT = "A7_CAT";
        public static final String A8_DOG = "A8_DOG";
        public static final String A9_GREEN_PLANTS = "A9_GREEN_PLANTS";
        public static final String BI_MEMORY = "bi_memory";
        public static final String BI_MONO = "bi_mono";
        public static final String BI_M_DUSK = "bi_m_dusk";
        public static final String BI_M_LILT = "bi_m_lilt";
        public static final String BI_M_SEPIA = "bi_m_sepia";
        public static final String BI_M_TEA = "bi_m_tea";
        public static final String BI_M_WHITEANDBLACK = "bi_m_whiteandblack";
        public static final String BI_PINK = "bi_pink";
        public static final String BI_PORTRAIT = "bi_portrait";
        public static final String BI_RETRO = "bi_retro";
        public static final String BI_ROMANTIC = "bi_remantic";
        public static final String BI_STRONG = "bi_strong";
        public static final String BI_SUNNY = "bi_sunny";
        public static final String BI_SWEET = "bi_sweet";
        public static final String BI_WARM = "bi_warm";
        public static final String BI_YOUNG = "bi_young";
        public static final String B_FAIRYTALE = "b_fairytale";
        public static final String B_JAPANESE = "b_japanese";
        public static final String B_MAZE = "b_maze";
        public static final String B_MINT = "b_mint";
        public static final String B_MOOD = "b_mood";
        public static final String B_MOVIE = "b_movie";
        public static final String B_M_LILT = "b_m_lilt";
        public static final String B_M_SEPIA = "b_m_sepia";
        public static final String B_M_TEA = "b_m_tea";
        public static final String B_M_WHITEANDBLACK = "b_m_whiteandblack";
        public static final String B_NATURE = "b_nature";
        public static final String B_PINK = "b_pink";
        public static final String B_RIDDLE = "b_riddle";
        public static final String B_ROMANCE = "b_romance";
        public static final String B_STORY = "b_story";
        public static final String B_WHITEANDBLACK = "b_whiteAndBlack";
        public static final String KEY_FILTER_CHANGED = "key_filter_changed";
        public static final String ML_BLUE = "ml_blue";
        public static final String ML_CONTRAST = "ml_contrast";
        public static final String ML_DEEPBLACK = "ml_deepblack";
        public static final String ML_FAIR = "ml_fair";
        public static final String ML_HONGKONG = "ml_hongkong";
        public static final String ML_MOUSSE = "ml_mousse";
        public static final String ML_SOLAR = "ml_solar";
        public static final String ML_YEARS = "ml_years";
        public static final String N_BERRY = "n_berry";
        public static final String N_CLASSIC = "n_classic";
        public static final String N_COOKIE = "n_cookie";
        public static final String N_DELICACY = "n_delicacy";
        public static final String N_FADE = "n_fade";
        public static final String N_FILM = "n_film";
        public static final String N_KOIZORA = "n_koizora";
        public static final String N_LATTE = "n_latte";
        public static final String N_LIGHT = "n_light";
        public static final String N_LIVELY = "n_lively";
        public static final String N_QUIET = "n_quiet";
        public static final String N_SODA = "n_soda";
        public static final String N_WARM = "n_warm";
        public static final String N_WHITEANDBLACK = "n_whiteAndBlack";
        public static final String PARAM_FILTER = "attr_filter";
        public static final String S_BYGONE = "s_bygone";
        public static final String S_FILM = "s_film";
        public static final String S_FOREST = "s_forest";
        public static final String S_POLAROID = "s_polaroid";
        public static final String S_WHITEANDBLACK = "s_whiteAndBlack";
        public static final String S_YEARS = "s_years";
        public static final String VALUE_FILTER_BOTTOM_TAB = "filter_bottom_tab";
    }

    public interface FlashAttr {
        public static final String FLASH_VALUE_SCREEN_LIGHT_AUTO = "screen-light-auto";
        public static final String FLASH_VALUE_SCREEN_LIGHT_ON = "screen-light-on";
        public static final String PARAM_FLASH_MODE = "attr_flash_mode";
        public static final String VALUE_FLASH_OUT_BUTTON = "flash_out_button";
    }

    public interface GoogleLens {
        public static final String GOOGLE_LENS_PICKER = "google_lens_picker";
        public static final String GOOGLE_LENS_TOUCH_AND_HOLD = "google_lens_touch_and_hold";
        public static final String KEY_GOOGLE_LENS = "key_google_lens";
        public static final String KEY_GOOGLE_LENS_OOBE_CONTINUE = "key_google_lens_oobe_continue";
        public static final String PARAM_OOBE_CONTINUE_CLICK = "attr_Google_OOBE_continue_button_click";
        public static final String PARAM_PICK_WHICH = "attr_longPress_preview_dialog_pick";
        public static final String VALUE_GOOGLE_LENS = "google_lens";
        public static final String VALUE_LOCK_AEAF = "lock_AE/AF";
    }

    public interface Live {
        public static final String KEY_LIVE_BEAUTY = "key_live_beauty";
        public static final String KEY_LIVE_VIDEO_COMPLETE = "key_live_video_complete";
        public static final String KEY_LIVE_VIDEO_SEGMENT = "key_live_video_segment";
        public static final String LIVE_CLICK_PAUSE = "live_pause";
        public static final String LIVE_CLICK_RESUME = "live_resume";
        public static final String LIVE_CLICK_START = "live_start";
        public static final String LIVE_STICKER_MORE = "key_see_more";
        public static final String PARAM_LIVE_BEAUTY_ON = "attr_beauty_on";
        public static final String PARAM_LIVE_BEAUTY_PORT = "attr_live_beauty_port";
        public static final String PARAM_LIVE_BEAUTY_SEGMENT_ON = "attr_live_beauty_segment_on";
        public static final String PARAM_LIVE_BEAUTY_TYPE = "attr_live_beautyType";
        public static final String PARAM_LIVE_ENLARGE_EYE_RATIO = "attr_enlarge_eye_ratio";
        public static final String PARAM_LIVE_FILTER_NAME = "attr_filter_name";
        public static final String PARAM_LIVE_FILTER_ON = "attr_filter_on";
        public static final String PARAM_LIVE_FILTER_SEGMENT_ON = "attr_filter_segment_on";
        public static final String PARAM_LIVE_MUSIC_NAME = "attr_live_music_name";
        public static final String PARAM_LIVE_MUSIC_ON = "attr_live_music_on";
        public static final String PARAM_LIVE_RECORD_SEGMENTS = "attr_record_segments";
        public static final String PARAM_LIVE_RECORD_TIME = "attr_record_time";
        public static final String PARAM_LIVE_SHRINK_FACE_RATIO = "attr_shrink_face_ratio";
        public static final String PARAM_LIVE_SMOOTH_RATIO = "attr_smooth_ratio";
        public static final String PARAM_LIVE_SPEED_LEVEL = "attr_live_speed_level";
        public static final String PARAM_LIVE_STICKER_DOWNLOAD = "attr_live_download";
        public static final String PARAM_LIVE_STICKER_NAME = "attr_live_sticker_name";
        public static final String PARAM_LIVE_STICKER_ON = "attr_sticker_on";
        public static final String PARAM_LIVE_STICKER_SEGMENT_ON = "attr_sticker_segment_on";
        public static final String VALUE_LIVE_CLICK_PLAY_EXIT = "exit";
        public static final String VALUE_LIVE_CLICK_PLAY_EXIT_CONFIRM = "live_exit";
        public static final String VALUE_LIVE_CLICK_PLAY_SAVE = "live_save";
        public static final String VALUE_LIVE_CLICK_PLAY_SHARE = "live_share";
        public static final String VALUE_LIVE_CLICK_PLAY_SHARE_SHEET = "live_preview_shareTo_";
        public static final String VALUE_LIVE_CLICK_REVERSE = "live_reverse";
        public static final String VALUE_LIVE_CLICK_REVERSE_CONFIRM = "live_reverse_confirm";
        public static final String VALUE_LIVE_CLICK_SWITCH = "live_swith_camera";
        public static final String VALUE_LIVE_ENLARGE_EYE_RATIO = "live_enlarge_eye_ratio";
        public static final String VALUE_LIVE_MUSIC_ICON_CLICK = "feature_live_music_click";
        public static final String VALUE_LIVE_SHRINK_FACE_RATIO = "live_shrink_face_ratio";
        public static final String VALUE_LIVE_SMOOTH_RATIO = "live_smooth_ratio";
        public static final String VALUE_LIVE_SPEED = "live_speed";
        public static final String VALUE_LIVE_STICKER = "live_sticker";
        public static final String VALUE_LIVE_STICKER_APP = "live_app";
        public static final String VALUE_LIVE_STICKER_MARKET = "market";
        public static final String VALUE_LIVE_STICKER_OFF = "live_sticker_off";
    }

    public interface LiveShotAttr {
        public static final String FUCNAME_LIVESHOT_MODE = "key_liveshot_mode";
        public static final String PARAM_LIVESHOT = "attr_liveshot";
        public static final String VALUE_TOPMENU_LIVESHOT_CLICK = "liveshot_topmenu_click";
    }

    public interface MacroAttr {
        public static final String FUCNAME_MACRO_MODE = "key_macro_mode";
        public static final String PARAM_SLOW_MOTION_MACRO = "attr_slow_motion_macro";
        public static final String PARAM_SWITCH_MACRO = "attr_switch_macro";
    }

    public interface Manual {
        public static final String AE_AF_AWB_LOCKED = "3A_Locked";
        public static final String AUTOEXPOSURE = "auto_exposure";
        public static final String AUXILIARY = "auxiliary";
        public static final String AUXILIAYR_CLOSE = "auxiliary_close";
        public static final String AVERAGE_PHOTOMETRY = "average_photometry";
        public static final String AWB = "awb";
        public static final String CENTER_PHOTOMETRY = "center_photometry";
        public static final String CENTER_WEIGHT = "center_weight";
        public static final String ET = "exposureTime";
        public static final String EV = "exposureValue";
        public static final String EXPOSURE_FEEDBACK = "exposure_feedback";
        public static final String FOCUS_POSITION = "focus_position";
        public static final String GRADIENT = "gradient";
        public static final String HISTOGRAM = "histogram";
        public static final String ISO = "iso";
        public static final String LENS = "lens";
        public static final String LOG = "log";
        public static final String MANUAL_EDIT_TAB_HIDE = "manual_edit_tab_hide";
        public static final String MANUAL_EDIT_TAB_SHOW = "manual_edit_tab_show";
        public static final String MANUAL_FOCUS_PEAK = "manual_focus_peak";
        public static final String METERING_FOCUS_SPLIT = "metering_focus_split";
        public static final String PARAMETER_DESCRIPTION = "parameter_description";
        public static final String PARAM_3A_LOCKED = "attr_3A_Locked";
        public static final String PARAM_AUTOEXPOSURE = "attr_auto_exposure";
        public static final String PARAM_AWB = "attr_awb";
        public static final String PARAM_ET = "attr_et";
        public static final String PARAM_EV = "attr_ev";
        public static final String PARAM_EXPOSURE_FEEDBACk = "attr_exposure_feedback";
        public static final String PARAM_FLASH = "attr_flash";
        public static final String PARAM_FOCUS_PEAK = "attr_focus_peak";
        public static final String PARAM_FOCUS_POSITION = "attr_focus_position";
        public static final String PARAM_GRADIENT = "attr_gradient";
        public static final String PARAM_HISTOGRAM = "attr_histogram";
        public static final String PARAM_ISO = "attr_iso";
        public static final String PARAM_LENS = "attr_lens";
        public static final String PARAM_LOG = "attr_log";
        public static final String PARAM_PALM_SHUTTER = "attr_palm_shutter";
        public static final String PARAM_RAW = "attr_raw";
        public static final String PARAM_REFERENCE_LINE = "attr_reference_line";
        public static final String PARAM_SUPERME_PIXEL_VALUE = "attr_supreme_pixel_value";
        public static final String PARAM_SUPER_RESOLUTION = "attr_super_resolution";
        public static final String PARAM_TIMER = "attr_timer";
        public static final String RAW = "raw";
        public static final String REFERENCE_LINE = "reference_line";
        public static final String RESET_PARAMS_CLICK = "reset_params_click";
        public static final String RESET_PARAMS_SHOW = "reset_params_show";
        public static final String SUPERME_PIXEL = "supreme_pixel";
        public static final String SUPER_RESOLUTION = "super_resolution";
        public static final String VALUE_SUPERME_PIXEL_108M_ON = "108M_ON";
        public static final String VALUE_SUPERME_PIXEL_32M_ON = "32M_ON";
        public static final String VALUE_SUPERME_PIXEL_48M_ON = "48M_ON";
        public static final String VALUE_SUPERME_PIXEL_64M_ON = "64M_ON";
        public static final String VIDEO_8K = "video_8k";
    }

    public interface MiLive {
        public static final String KEY_MI_LIVE_VIDEO_SEGMENT = "key_mi_live_video_segment";
        public static final String PARAM_MI_LIVE_BEAUTY_ON = "attr_mi_live_beauty_on";
        public static final String PARAM_MI_LIVE_CAMERA_FACING = "attr_mi_live_facing";
        public static final String PARAM_MI_LIVE_CAMERA_QUALITY = "attr_mi_live_quality";
        public static final String PARAM_MI_LIVE_ENLARGE_EYE_RATIO = "attr_mi_live_enlarge_eye_ratio";
        public static final String PARAM_MI_LIVE_FILTER_NAME = "attr_mi_live_filter_name";
        public static final String PARAM_MI_LIVE_KALEIDOSCOPE_NAME = "attr_mi_live_kaleidoscope_name";
        public static final String PARAM_MI_LIVE_MUSIC_NAME = "attr_mi_live_music_name";
        public static final String PARAM_MI_LIVE_SEGMENT_COUNT = "attr_mi_live_segment_count";
        public static final String PARAM_MI_LIVE_SHRINK_FACE_RATIO = "attr_mi_live_shrink_face_ratio";
        public static final String PARAM_MI_LIVE_SMOOTH_RATIO = "attr_mi_live_smooth_ratio";
        public static final String PARAM_MI_LIVE_SPEED = "attr_mi_live_speed";
        public static final String VALUE_MI_LIVE_CLICK_BEAUTY = "mi_live_click_beauty";
        public static final String VALUE_MI_LIVE_CLICK_BEAUTY_RESET = "mi_live_click_beauty_reset";
        public static final String VALUE_MI_LIVE_CLICK_CANCEL = "mi_live_click_cancel";
        public static final String VALUE_MI_LIVE_CLICK_DONE = "mi_live_click_done";
        public static final String VALUE_MI_LIVE_CLICK_ENLARGE_EYE = "mi_live_click_enlarge_eye";
        public static final String VALUE_MI_LIVE_CLICK_FILTER = "mi_live_click_filter";
        public static final String VALUE_MI_LIVE_CLICK_KALEIDOSCOPE = "mi_live_click_kaleidoscope";
        public static final String VALUE_MI_LIVE_CLICK_KALEIDOSCOPE_CAPTURE = "mi_live_click_kaleidoscope_capture";
        public static final String VALUE_MI_LIVE_CLICK_MUSIC = "mi_live_click_music";
        public static final String VALUE_MI_LIVE_CLICK_PAUSE = "mi_live_pause_recording";
        public static final String VALUE_MI_LIVE_CLICK_SHARE = "mi_live_click_share";
        public static final String VALUE_MI_LIVE_CLICK_SHRINK_FACE = "mi_live_click_shrink_face";
        public static final String VALUE_MI_LIVE_CLICK_SMOOTH = "mi_live_click_smooth";
        public static final String VALUE_MI_LIVE_CLICK_SPEED = "mi_live_click_speed";
        public static final String VALUE_MI_LIVE_CLICK_START = "mi_live_start_recording";
        public static final String VALUE_MI_LIVE_CLICK_SWITCH = "mi_live_switch_camera";
    }

    public interface Mimoji {
        public static final String CATEGORY_BEAR = "bear";
        public static final String CATEGORY_CUSTOM = "custom";
        public static final String CATEGORY_PIG = "pig";
        public static final String CATEGORY_RABBIT = "rabbit";
        public static final String CATEGORY_ROYAN = "royan";
        public static final String KEY_MIMOJI_CLICK = "key_mimoji_click";
        public static final String MIMOJI_CATEGORY = "attr_mimoji_category";
        public static final String MIMOJI_CLICK_ADD = "mimoji_click_add";
        public static final String MIMOJI_CLICK_CREATE_BACK = "mimoji_click_create_back";
        public static final String MIMOJI_CLICK_CREATE_CAPTURE = "mimoji_click_create_capture";
        public static final String MIMOJI_CLICK_CREATE_SOFT_BACK = "mimoji_click_create_soft_back";
        public static final String MIMOJI_CLICK_CREATE_SWITCH = "mimoji_click_create_switch";
        public static final String MIMOJI_CLICK_DELETE = "mimoji_click_delete";
        public static final String MIMOJI_CLICK_EDIT = "mimoji_click_edit";
        public static final String MIMOJI_CLICK_EDIT_CANCEL = "edit_cancel";
        public static final String MIMOJI_CLICK_EDIT_RESET = "edit_reset";
        public static final String MIMOJI_CLICK_EDIT_SAVE = "key_edit_save";
        public static final String MIMOJI_CLICK_EDIT_SOFT_BACK = "edit_soft_back";
        public static final String MIMOJI_CLICK_NULL = "mimoji_click_null";
        public static final String MIMOJI_CLICK_PREVIEW_MID_BACK = "mimoji_click_preview_mid_back";
        public static final String MIMOJI_CLICK_PREVIEW_MID_EDIT = "key_preview_mid_edit";
        public static final String MIMOJI_CLICK_PREVIEW_MID_RECAPTURE = "preview_mid_recapture";
        public static final String MIMOJI_CLICK_PREVIEW_MID_SAVE = "preview_mid_save";
        public static final String MIMOJI_CLICK_PREVIEW_MID_SOFT_BACK = "preview_mid_soft_back";
        public static final String MIMOJI_CONFIG_EAR = "attr_ear";
        public static final String MIMOJI_CONFIG_EARING = "attr_earing";
        public static final String MIMOJI_CONFIG_EYEBROW_SHAPE = "attr_eyebrow_shape";
        public static final String MIMOJI_CONFIG_EYEGLASS = "attr_eyeglass";
        public static final String MIMOJI_CONFIG_EYELASH = "attr_eyelash";
        public static final String MIMOJI_CONFIG_EYE_SHAPE = "attr_eye_shape";
        public static final String MIMOJI_CONFIG_FEATURE_FACE = "attr_feature_face";
        public static final String MIMOJI_CONFIG_FRECKLE = "attr_freckle";
        public static final String MIMOJI_CONFIG_HARISTYLE = "attr_hairstyle";
        public static final String MIMOJI_CONFIG_HEADWEAR = "attr_headwear";
        public static final String MIMOJI_CONFIG_MOUTH_SHAPE = "attr_mouth_shape";
        public static final String MIMOJI_CONFIG_MUSTACHE = "attr_mustache";
        public static final String MIMOJI_CONFIG_NOSE = "attr_nose";
        public static final String MIMOJI_HISTORY_EMOJI_COUNT = "attr_mimoji_emoji_history_count";
        public static final String PARAM_MIMOJI_EDIT_COUNT = "attr_mimoji_edit_count";
        public static final String PREVIEW_MID = "preview_mid";
    }

    public interface ModuleName {
        public static final String BEAUTY = "M_beauty_";
        public static final String CAPTURE = "M_capture_";
        public static final String FAST_MOTION = "M_fastMotion_";
        public static final String FUN = "M_funTinyVideo_";
        public static final String FUN_AR = "M_funArMimoji_";
        public static final String FUN_AR2 = "M_funArMimoji2_";
        public static final String ID_CARD = "M_idCard_";
        public static final String LIVE = "M_liveDouyin_";
        public static final String LIVE_VV = "M_liveVlog_";
        public static final String MANUAL = "M_manual_";
        public static final String MI_LIVE = "M_miLive_";
        public static final String NEW_SLOW_MOTION = "M_newSlowMotion_";
        public static final String PANORAMA = "M_panorama_";
        public static final String PIXEL = "M_48mPixel_";
        public static final String PORTRAIT = "M_portrait_";
        public static final String PROVIDEO = "M_proVideo_";
        public static final String RECORD_VIDEO = "M_recordVideo_";
        public static final String SLOW_MOTION = "M_slowMotion_";
        public static final String SQUARE = "M_square_";
        public static final String STANDALONE_MACRO = "M_standaloneMacro_";
        public static final String SUPER_NIGHT = "M_superNight_";
        public static final String UNSPECIFIED = "M_unspecified_";
        public static final String VALUE_TARGET_MODE = "target_mode";
        public static final String VIDEO_HFR = "M_videoHfr_";
        public static final String WIDE_SELFIE = "M_wideSelfie_";
    }

    public interface MoonAndNightAttr {
        public static final String KEY_MOON_MODE = "key_moon_mode";
        public static final String PARAM_NIGHT_MOON_MODE = "attr_night_moon_mode";
        public static final String PARAM_SURPER_NIGHT = "attr_supernight_in_M_capture_";
        public static final String VAULE_MOON_MODE = "in_moon_mode";
        public static final String VAULE_NIGHT_MODE = "in_night_mode";
    }

    public interface NonUI {
        public static final String KEY_ENTER_FAULT = "key_enter_fault";
        public static final String KEY_POCKET_MODE_KEYGUARD_EXIT = "key_pocket_mode_keyguard_exit";
        public static final String KEY_POCKET_MODE_SENSOR_DELAY = "key_pocket_mode_sensor_delay";
        public static final String POCKET_MODE_KEYGUARD_EXIT_DISMISS = "keyguard_exit_dismiss";
        public static final String POCKET_MODE_KEYGUARD_EXIT_TIMEOUT = "keyguard_exit_timeout";
        public static final String POCKET_MODE_KEYGUARD_EXIT_UNLOCK = "keyguard_exit_unlock";
        public static final String POCKET_MODE_NONUI_ENTER_SNAP = "NonUI_snap";
        public static final String POCKET_MODE_NONUI_ENTER_VOLUME = "NonUI_volume";
        public static final String POCKET_MODE_PSENSOR_ENTER_KEYGUARD = "Psensor_keyguard";
        public static final String POCKET_MODE_PSENSOR_ENTER_SNAP = "Psensor_snap";
        public static final String POCKET_MODE_PSENSOR_ENTER_VOLUME = "Psensor_volume";
        public static final String VALUE_POCKET_MODE_ENTER = "pocket_mode_enter";
    }

    public interface Panorama {
        public static final String PANORAMA_DIRECTION = "panorama_direction";
        public static final String PANORAMA_DIRECTION_L2R = "L2R";
        public static final String PANORAMA_DIRECTION_R2L = "R2L";
        public static final String PARAM_PANORAMA_DIRECTION = "attr_panorama_direction";
        public static final String PARAM_STOP_CAPTURE_MODE = "attr_stop_capture_mode";
        public static final String STOP_CAPTURE_MODE_FILL = "stop_capture_fill_preview";
        public static final String STOP_CAPTURE_MODE_HORIZONTAL_OUT = "stop_capture_horizontal_out";
        public static final String STOP_CAPTURE_MODE_ON_HOME_OR_BACK = "stop_capture_on_home_or_back";
        public static final String STOP_CAPTURE_MODE_ON_SHUTTER_BUTTON = "stop_capture_on_shutter_button";
        public static final String STOP_CAPTURE_MODE_ROTATE_OUT = "stop_capture_rotate_out";
        public static final String STOP_CAPTURE_MODE_VERTICAL_OUT = "stop_capture_vertical_out";
    }

    public interface PictureQuality {
        public static final String HIGH = "high";
        public static final String HIGHER = "higher";
        public static final String HIGHEST = "highest";
        public static final String LOW = "low";
        public static final String LOWER = "lower";
        public static final String LOWEST = "lowest";
        public static final String NORMAL = "normal";
        public static final String PARAM_ULTRA_PIXEL = "attr_ultra_pixel";
        public static final String VALUE_ULTRA_PIXEL_108MP = "108MP";
        public static final String VALUE_ULTRA_PIXEL_32MP = "32MP";
        public static final String VALUE_ULTRA_PIXEL_48MP = "48MP";
        public static final String VALUE_ULTRA_PIXEL_64MP = "64MP";
    }

    public interface PortraitAttr {
        public static final String PARAM_BOKEH_RATIO = "attr_bokeh_ratio";
        public static final String PARAM_PORTRAIT_LIGHTING = "attr_portrait_lighting";
        public static final String PARAM_ULTRAPIXEL_PORTRAIT = "attr_ultrapixel_portrait";
        public static final String PARAM_ULTRA_WIDE_BOKEH = "attr_whole_body";
        public static final String PORTRAIT_LIGHTING = "portrait_lighting";
        public static final String ULTRAPIXEL_PORTRAIT = "ultrapixel_portrait";
        public static final String VALUE_BOKEH_ADJUST_ENTRY = "bokeh_adjust_entry";
        public static final String VALUE_LIGHTING_OUT_BUTTON = "lighting_out_button";
        public static final String VALUE_ULTRA_WIDE_BOKEH = "whole_body";
    }

    public interface SensorAttr {
        public static final String PARAM_MACRO_MODE = "attr_macro_mode";
        public static final String PARAM_SAT_ZOOM = "attr_sat_device";
        public static final String PARAM_STANDALONE_MACRO_MODE = "attr_standlone_macroMode";
        public static final String VALUE_SENSOR_TYPE_REAR_MACRO = "_RearMacro";
        public static final String VALUE_SENSOR_TYPE_REAR_TELE = "_RearTele2x";
        public static final String VALUE_SENSOR_TYPE_REAR_TELE4x = "_RearTele4x";
        public static final String VALUE_SENSOR_TYPE_REAR_ULTRA = "_RearUltra";
        public static final String VALUE_SENSOR_TYPE_REAR_WIDE = "_RearWide";
    }

    public interface Setting {
        public static final String KEY_SETTING = "key_settings";
        public static final String PARAM_960_WATERMARK_STATUS = "attr_960_watermark_status";
        public static final String PARAM_AICC = "attr_aicc";
        public static final String PARAM_ANTIBANDING = "attr_antibanding";
        public static final String PARAM_CAMERA_SNAP = "attr_snap_enable";
        public static final String PARAM_CAMERA_SOUND = "attr_camera_sound";
        public static final String PARAM_DEVICE_WATERMARK = "attr_watermark_device";
        public static final String PARAM_DOCUMENT_MODE = "attr_document_mode";
        public static final String PARAM_FOCUS_SHOOT = "attr_focus_shoot";
        public static final String PARAM_FRONT_MIRROR = "attr_front_mirror";
        public static final String PARAM_GRADIENTER = "attr_gradiente";
        public static final String PARAM_HEIC = "attr_heic";
        public static final String PARAM_HEIC_FORMAT = "attr_heic_format";
        public static final String PARAM_JPEG_QUALITY = "attr_jpeg_quality";
        public static final String PARAM_LENS_DIRTY_SWITCH = "attr_lens_dirty_switch";
        public static final String PARAM_LONG_PRESS_SHUTTER_FEATURE = "attr_long_press_shutter";
        public static final String PARAM_LYING_TIP_SWITCH = "attr_lying_tip_switch";
        public static final String PARAM_MOVIE_SOLID = "attr_movie_solid";
        public static final String PARAM_NORMAL_WIDE_LDC = "attr_normal_wide_ldc";
        public static final String PARAM_REFERENCE_LINE = "attr_reference_line";
        public static final String PARAM_RETAIN_CAMERA_MODE = "attr_retain_camera_mode";
        public static final String PARAM_SAVE_LOCATION = "attr_save_location";
        public static final String PARAM_SCAN_QRCODE = "attr_scan_qrcode";
        public static final String PARAM_SUB_MODULE = "attr_sub_module";
        public static final String PARAM_TILTSHIFT = "attr_tiltshift";
        public static final String PARAM_TIME_WATERMARK = "attr_watermark_time";
        public static final String PARAM_ULTRA_WIDE_LDC = "attr_ultra_wide_ldc";
        public static final String PARAM_USERDEFINE_WATERMARK = "attr_watermark_custom";
        public static final String PARAM_VIDEO_ENCODER = "attr_video_encoder";
        public static final String PARAM_VIDEO_TIME_LAPSE_FRAME_INTERVAL = "attr_video_time_lapse_frame_interval";
        public static final String PARAM_VOLUME_CAMERA_FUNCTION = "attr_volume_camera_fuction";
        public static final String PARAM_WATERMARK = "attr_watermark";
        public static final String PREF_KEY_POPUP_CAMERA = "attr_popup_camera";
        public static final String PREF_KEY_PRIVACY = "attr_privacy";
        public static final String PREF_KEY_RESTORE = "attr_restore";
        public static final String VALUE_SETTING_ADVANCE = "settings_advance";
        public static final String VALUE_SETTING_CAPTURE = "settings_capture";
        public static final String VALUE_SETTING_GOLBAL = "settings_global";
        public static final String VALUE_SETTING_VIDEO_RECORD = "settings_video_record";
    }

    public interface SlowMotion {
        public static final String FPS_120 = "fps_120";
        public static final String FPS_240 = "fps_240";
        public static final String FPS_960 = "fps_960";
    }

    public interface VLogAttr {
        public static final String KEY_VLOG = "key_vlog";
        public static final String PARAM_VV_CLICK_TEMPLATE_PREVIEW = "attr_vv_click_template_preview";
        public static final String PARAM_VV_TEMPLATE_NAME = "attr_vv_template_name";
        public static final String VALUE_VV_CLICK_EXIT = "value_vv_exit";
        public static final String VALUE_VV_CLICK_EXIT_CONFIRM = "value_vv_exit_confirm";
        public static final String VALUE_VV_CLICK_FINISH_EXIT = "value_vv_click_finish_exit";
        public static final String VALUE_VV_CLICK_FINISH_EXIT_CONFIRM = "value_vv_click_finish_exit_confirm";
        public static final String VALUE_VV_CLICK_PLAY_ALL = "value_vv_click_play_all";
        public static final String VALUE_VV_CLICK_PLAY_ALL_PAUSE = "value_vv_click_play_all_pause";
        public static final String VALUE_VV_CLICK_PLAY_ALL_RESUME = "value_vv_click_play_all_resume";
        public static final String VALUE_VV_CLICK_PLAY_SAVE = "value_vv_click_play_save";
        public static final String VALUE_VV_CLICK_PLAY_SEGMENT = "value_vv_click_play_segment";
        public static final String VALUE_VV_CLICK_PLAY_SEGMENT_EXIT = "value_vv_click_play_segement_exit";
        public static final String VALUE_VV_CLICK_PLAY_SHARE = "value_vv_click_play_share";
        public static final String VALUE_VV_CLICK_REVERSE = "value_vv_reverse";
        public static final String VALUE_VV_CLICK_REVERSE_CONFIRM = "value_vv_reverse_confirm";
        public static final String VALUE_VV_CLICK_START_SEGMENT = "value_vv_start_segment";
        public static final String VALUE_VV_ICON_CLICK = "value_vv_icon_click";
    }

    public interface VideoAttr {
        public static final String KEY_NEW_SLOW_MOTION = "key_slow_motion_mode";
        public static final String KEY_VIDEO_960 = "key_video_960";
        public static final String KEY_VIDEO_COMMON_CLICK = "key_video_common_click";
        public static final String KEY_VIDEO_QUICK = "key_video_quick";
        public static final String NEW_SLOW_MOTION_SWITCH_FPS = "new_slow_motion_switch_fps";
        public static final String PARAM_BEAUTY = "attr_beauty";
        public static final String PARAM_BOKEH = "attr_bokeh";
        public static final String PARAM_CINEMATIC_RATIO = "attr_cinematic";
        public static final String PARAM_FILTER = "attr_filter";
        public static final String PARAM_SUPER_EIS = "attr_super_eis";
        public static final String PARAM_VIDEO_FPS = "attr_video_fps";
        public static final String PARAM_VIDEO_MODE = "attr_video_mode";
        public static final String PARAM_VIDEO_QUALITY = "attr_video_quality";
        public static final String PARAM_VIDEO_SNAPSHOT_COUNT = "attr_video_snapshot_count";
        public static final String PARAM_VIDEO_TIME = "attr_video_time";
        public static final String PARAM_VIDEO_TIME_LAPSE_INTERVAL = "attr_video_time_lapse_interval";
        public static final String VALUE_FPS960_PROCESS_FAILED = "fps_960_process_failed";
        public static final String VALUE_FPS960_TOO_SHORT = "fps_960_too_short";
        public static final String VALUE_SPEED_SLOW = "video_slow";
        public static final String VALUE_VIDEO_PAUSE_RECORDING = "video_pause_recording";
    }

    public interface Zoom {
        public static final String KEY_ZOOM = "key_zoom";
        public static final String PARAM_SAT_ZOOM_RATIO = "attr_SAT_ratio";
        public static final String PARAM_ZOOM_ADJUSTED_MODE = "attr_zoom_adjusted_mode";
        public static final String PARAM_ZOOM_IN_RECORDING = "attr_in_recording";
        public static final String PARAM_ZOOM_RATIO = "attr_zoom_ratio";
        public static final String TRIGGER_MODE = "attr_trigger_mode";
        public static final String VALUE_SHOW_ZOOM_BAR_BY_SCROLL = "show_zoom_bar_by_scroll";
    }
}
