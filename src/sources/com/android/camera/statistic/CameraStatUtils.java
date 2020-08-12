package com.android.camera.statistic;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.SparseArray;
import com.android.camera.CameraAppImpl;
import com.android.camera.CameraIntentManager;
import com.android.camera.CameraSettings;
import com.android.camera.HybridZoomingSystem;
import com.android.camera.MutexModeManager;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.constant.BeautyConstant;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.config.ComponentConfigFlash;
import com.android.camera.data.data.config.ComponentConfigHdr;
import com.android.camera.data.data.config.ComponentConfigSlowMotion;
import com.android.camera.data.data.config.ComponentConfigVideoQuality;
import com.android.camera.data.data.config.ComponentManuallyDualLens;
import com.android.camera.data.data.config.DataItemConfig;
import com.android.camera.data.data.runing.ComponentRunningShine;
import com.android.camera.data.data.runing.ComponentRunningTimer;
import com.android.camera.data.data.runing.DataItemRunning;
import com.android.camera.effect.EffectController;
import com.android.camera.effect.FilterInfo;
import com.android.camera.fragment.beauty.BeautyValues;
import com.android.camera.log.Log;
import com.android.camera.module.BaseModule;
import com.android.camera.module.ModuleManager;
import com.android.camera.module.loader.FunctionParseBeautyBodySlimCount;
import com.android.camera.module.loader.camera2.Camera2DataContainer;
import com.android.camera.statistic.MistatsConstants;
import com.mi.config.b;
import com.miui.filtersdk.filter.helper.FilterType;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class CameraStatUtils {
    private static final String TAG = "CameraStatUtils";
    private static HashMap<String, String> sBeautyTypeToName = new HashMap<>();
    private static HashMap<String, String> sBeautyTypeToValue = new HashMap<>();
    private static SparseArray<String> sCameraModeIdToName = new SparseArray<>();
    private static SparseArray<String> sExposureTimeLessThan1sToName = new SparseArray<>();
    private static SparseArray<String> sFilterTypeToName = new SparseArray<>();
    private static SparseArray<String> sPictureQualityIndexToName = new SparseArray<>();
    private static SparseArray<String> sSpeedToName = new SparseArray<>();
    private static SparseArray<String> sTriggerModeIdToName = new SparseArray<>();

    static {
        sCameraModeIdToName.put(161, MistatsConstants.ModuleName.FUN);
        sCameraModeIdToName.put(174, MistatsConstants.ModuleName.LIVE);
        sCameraModeIdToName.put(183, MistatsConstants.ModuleName.MI_LIVE);
        sCameraModeIdToName.put(177, MistatsConstants.ModuleName.FUN_AR);
        sCameraModeIdToName.put(184, MistatsConstants.ModuleName.FUN_AR2);
        sCameraModeIdToName.put(163, MistatsConstants.ModuleName.CAPTURE);
        sCameraModeIdToName.put(165, MistatsConstants.ModuleName.SQUARE);
        sCameraModeIdToName.put(167, MistatsConstants.ModuleName.MANUAL);
        sCameraModeIdToName.put(171, MistatsConstants.ModuleName.PORTRAIT);
        sCameraModeIdToName.put(166, MistatsConstants.ModuleName.PANORAMA);
        sCameraModeIdToName.put(176, MistatsConstants.ModuleName.WIDE_SELFIE);
        sCameraModeIdToName.put(172, MistatsConstants.ModuleName.NEW_SLOW_MOTION);
        sCameraModeIdToName.put(162, MistatsConstants.ModuleName.RECORD_VIDEO);
        sCameraModeIdToName.put(169, MistatsConstants.ModuleName.FAST_MOTION);
        sCameraModeIdToName.put(173, MistatsConstants.ModuleName.SUPER_NIGHT);
        sCameraModeIdToName.put(175, MistatsConstants.ModuleName.PIXEL);
        sTriggerModeIdToName.put(10, MistatsConstants.CaptureSence.SHUTTER_BUTTON);
        sTriggerModeIdToName.put(20, "volume");
        sTriggerModeIdToName.put(30, MistatsConstants.CaptureSence.FINGERPRINT);
        sTriggerModeIdToName.put(40, MistatsConstants.CaptureSence.KEYCODE_CAMERA);
        sTriggerModeIdToName.put(50, MistatsConstants.CaptureSence.KEYCODE_DPAD);
        sTriggerModeIdToName.put(60, MistatsConstants.CaptureSence.OBJECT_TRACK);
        sTriggerModeIdToName.put(70, MistatsConstants.CaptureSence.AUDIO_CAPTURE);
        sTriggerModeIdToName.put(80, MistatsConstants.CaptureSence.FOCUS_SHOOT);
        sTriggerModeIdToName.put(90, MistatsConstants.CaptureSence.EXPOSURE_VIEW);
        sTriggerModeIdToName.put(100, MistatsConstants.CaptureSence.HAND_GESTURE);
        sPictureQualityIndexToName.put(0, MistatsConstants.PictureQuality.LOWEST);
        sPictureQualityIndexToName.put(1, MistatsConstants.PictureQuality.LOWER);
        sPictureQualityIndexToName.put(2, "low");
        sPictureQualityIndexToName.put(3, "normal");
        sPictureQualityIndexToName.put(4, "high");
        sPictureQualityIndexToName.put(5, MistatsConstants.PictureQuality.HIGHER);
        sPictureQualityIndexToName.put(6, MistatsConstants.PictureQuality.HIGHEST);
        sExposureTimeLessThan1sToName.put(0, "auto");
        sExposureTimeLessThan1sToName.put(1000, "1/1000s");
        sExposureTimeLessThan1sToName.put(2000, "1/500s");
        sExposureTimeLessThan1sToName.put(4000, "1/250s");
        sExposureTimeLessThan1sToName.put(5000, "1/250s");
        sExposureTimeLessThan1sToName.put(BaseModule.LENS_DIRTY_DETECT_HINT_DURATION_8S, "1/125s");
        sExposureTimeLessThan1sToName.put(16667, "1/60s");
        sExposureTimeLessThan1sToName.put(33333, "1/30s");
        sExposureTimeLessThan1sToName.put(66667, "1/15s");
        sExposureTimeLessThan1sToName.put(125000, "1/8s");
        sExposureTimeLessThan1sToName.put(250000, "1/4s");
        sExposureTimeLessThan1sToName.put(500000, "1/2s");
        sExposureTimeLessThan1sToName.put(1000000, "1s");
        sExposureTimeLessThan1sToName.put(2000000, "2s");
        sExposureTimeLessThan1sToName.put(4000000, "4s");
        sExposureTimeLessThan1sToName.put(8000000, "8s");
        sExposureTimeLessThan1sToName.put(16000000, "16s");
        sExposureTimeLessThan1sToName.put(32000000, "32s");
        sBeautyTypeToName.put("pref_beautify_level_key_capture", MistatsConstants.BeautyAttr.BEAUTY_LEVEL);
        sBeautyTypeToName.put("pref_beautify_skin_smooth_ratio_key", MistatsConstants.BeautyAttr.PARAM_BEAUTY_SKIN_SMOOTH);
        sBeautyTypeToName.put("pref_beautify_skin_color_ratio_key", MistatsConstants.BeautyAttr.PARAM_BEAUTY_SKIN_COLOR);
        sBeautyTypeToName.put("pref_beautify_enlarge_eye_ratio_key", MistatsConstants.BeautyAttr.PARAM_BEAUTY_ENLARGE_EYE);
        sBeautyTypeToName.put("pref_beautify_slim_face_ratio_key", MistatsConstants.BeautyAttr.PARAM_BEAUTY_SLIM_FACE);
        sBeautyTypeToName.put("pref_beautify_nose_ratio_key", "attr_nose");
        sBeautyTypeToName.put("pref_beautify_risorius_ratio_key", MistatsConstants.BeautyAttr.PARAM_BEAUTY_RISORIUS);
        sBeautyTypeToName.put("pref_beautify_lips_ratio_key", MistatsConstants.BeautyAttr.PARAM_BEAUTY_LIPS);
        sBeautyTypeToName.put("pref_beautify_chin_ratio_key", MistatsConstants.BeautyAttr.PARAM_BEAUTY_CHIN);
        sBeautyTypeToName.put("pref_beautify_neck_ratio_key", MistatsConstants.BeautyAttr.PARAM_BEAUTY_NECK);
        sBeautyTypeToName.put("pref_beautify_smile_ratio_key", MistatsConstants.BeautyAttr.PARAM_BEAUTY_SMILE);
        sBeautyTypeToName.put("pref_beautify_slim_nose_ratio_key", MistatsConstants.BeautyAttr.PARAM_BEAUTY_SLIM_NOSE);
        sBeautyTypeToName.put("pref_beautify_hairline_ratio_key", MistatsConstants.BeautyAttr.PARAM_HAIRLINE);
        sBeautyTypeToName.put("pref_beautify_eyebrow_dye_ratio_key", MistatsConstants.BeautyAttr.PARAM_BEAUTY_EYEBROW_DYE);
        sBeautyTypeToName.put("pref_beautify_blusher_ratio_key", MistatsConstants.BeautyAttr.PARAM_BEAUTY_BLUSHER);
        sBeautyTypeToName.put("pref_beautify_pupil_line_ratio_key", MistatsConstants.BeautyAttr.PARAM_BEAUTY_PUPIL_LINE);
        sBeautyTypeToName.put("pref_beautify_jelly_lips_ratio_key", MistatsConstants.BeautyAttr.PARAM_BEAUTY_JELLY_LIPS);
        sBeautyTypeToName.put("pref_eye_light_type_key", MistatsConstants.BeautyAttr.PARAM_EYE_LIGHT);
        sBeautyTypeToName.put("pref_beauty_head_slim_ratio", MistatsConstants.BeautyBodySlimAttr.PARAM_BEAUTY_HEAD_SLIM);
        sBeautyTypeToName.put("pref_beauty_body_slim_ratio", MistatsConstants.BeautyBodySlimAttr.PARAM_BEAUTY_BODY_SLIM);
        sBeautyTypeToName.put("pref_beauty_shoulder_slim_ratio", MistatsConstants.BeautyBodySlimAttr.PARAM_BEAUTY_SHOULDER_SLIM);
        sBeautyTypeToName.put("key_beauty_leg_slim_ratio", MistatsConstants.BeautyBodySlimAttr.PARAM_BEAUTY_LEG_SLIM);
        sBeautyTypeToName.put("pref_beauty_whole_body_slim_ratio", MistatsConstants.BeautyBodySlimAttr.PARAM_BEAUTY_WHOLE_BODY_SLIM);
        sBeautyTypeToName.put("pref_beauty_butt_slim_ratio", MistatsConstants.BeautyBodySlimAttr.PARAM_BUTT_SLIM);
        sBeautyTypeToName.put(BeautyConstant.BEAUTY_RESET, MistatsConstants.BaseEvent.RESET);
        sBeautyTypeToValue.put("pref_beautify_level_key_capture", MistatsConstants.BeautyAttr.BEAUTY_LEVEL);
        sBeautyTypeToValue.put("pref_beautify_skin_smooth_ratio_key", MistatsConstants.BeautyAttr.BEAUTY_SKIN_SMOOTH);
        sBeautyTypeToValue.put("pref_beautify_skin_color_ratio_key", MistatsConstants.BeautyAttr.BEAUTY_SKIN_COLOR);
        sBeautyTypeToValue.put("pref_beautify_enlarge_eye_ratio_key", MistatsConstants.BeautyAttr.BEAUTY_ENLARGE_EYE);
        sBeautyTypeToValue.put("pref_beautify_slim_face_ratio_key", MistatsConstants.BeautyAttr.BEAUTY_SLIM_FACE);
        sBeautyTypeToValue.put("pref_beautify_nose_ratio_key", MistatsConstants.BeautyAttr.BEAUTY_NOSE);
        sBeautyTypeToValue.put("pref_beautify_risorius_ratio_key", MistatsConstants.BeautyAttr.BEAUTY_RISORIUS);
        sBeautyTypeToValue.put("pref_beautify_lips_ratio_key", MistatsConstants.BeautyAttr.BEAUTY_LIPS);
        sBeautyTypeToValue.put("pref_beautify_chin_ratio_key", MistatsConstants.BeautyAttr.BEAUTY_CHIN);
        sBeautyTypeToValue.put("pref_beautify_neck_ratio_key", MistatsConstants.BeautyAttr.BEAUTY_NECK);
        sBeautyTypeToValue.put("pref_beautify_smile_ratio_key", MistatsConstants.BeautyAttr.BEAUTY_SMILE);
        sBeautyTypeToValue.put("pref_beautify_slim_nose_ratio_key", MistatsConstants.BeautyAttr.BEAUTY_SLIM_NOSE);
        sBeautyTypeToValue.put("pref_beautify_hairline_ratio_key", MistatsConstants.BeautyAttr.HAIRLINE);
        sBeautyTypeToValue.put("pref_beautify_eyebrow_dye_ratio_key", MistatsConstants.BeautyAttr.BEAUTY_EYEBROW_DYE);
        sBeautyTypeToValue.put("pref_beautify_blusher_ratio_key", MistatsConstants.BeautyAttr.BEAUTY_BLUSHER);
        sBeautyTypeToValue.put("pref_beautify_pupil_line_ratio_key", MistatsConstants.BeautyAttr.BEAUTY_PUPIL_LINE);
        sBeautyTypeToValue.put("pref_beautify_jelly_lips_ratio_key", MistatsConstants.BeautyAttr.BEAUTY_JELLY_LIPS);
        sBeautyTypeToValue.put("pref_eye_light_type_key", MistatsConstants.BeautyAttr.EYE_LIGHT);
        sBeautyTypeToValue.put("pref_beauty_head_slim_ratio", MistatsConstants.BeautyBodySlimAttr.BEAUTY_HEAD_SLIM);
        sBeautyTypeToValue.put("pref_beauty_body_slim_ratio", MistatsConstants.BeautyBodySlimAttr.BEAUTY_TYPE_BODY_SLIM);
        sBeautyTypeToValue.put("pref_beauty_shoulder_slim_ratio", MistatsConstants.BeautyBodySlimAttr.BEAUTY_SHOULDER_SLIM);
        sBeautyTypeToValue.put("key_beauty_leg_slim_ratio", MistatsConstants.BeautyBodySlimAttr.BEAUTY_LEG_SLIM);
        sBeautyTypeToValue.put("pref_beauty_whole_body_slim_ratio", MistatsConstants.BeautyBodySlimAttr.BEAUTY_WHOLE_BODY_SLIM);
        sBeautyTypeToValue.put("pref_beauty_butt_slim_ratio", MistatsConstants.BeautyBodySlimAttr.BUTT_SLIM);
        sBeautyTypeToValue.put(BeautyConstant.BEAUTY_RESET, MistatsConstants.BaseEvent.RESET);
        sFilterTypeToName.put(FilterType.N_BERRY.ordinal(), MistatsConstants.FilterAttr.N_BERRY);
        sFilterTypeToName.put(FilterType.N_COOKIE.ordinal(), MistatsConstants.FilterAttr.N_COOKIE);
        sFilterTypeToName.put(FilterType.N_DELICACY.ordinal(), MistatsConstants.FilterAttr.N_DELICACY);
        sFilterTypeToName.put(FilterType.N_FADE.ordinal(), MistatsConstants.FilterAttr.N_FADE);
        sFilterTypeToName.put(FilterType.N_FILM.ordinal(), MistatsConstants.FilterAttr.N_FILM);
        sFilterTypeToName.put(FilterType.N_KOIZORA.ordinal(), MistatsConstants.FilterAttr.N_KOIZORA);
        sFilterTypeToName.put(FilterType.N_LATTE.ordinal(), MistatsConstants.FilterAttr.N_LATTE);
        sFilterTypeToName.put(FilterType.N_LIGHT.ordinal(), MistatsConstants.FilterAttr.N_LIGHT);
        sFilterTypeToName.put(FilterType.N_LIVELY.ordinal(), MistatsConstants.FilterAttr.N_LIVELY);
        sFilterTypeToName.put(FilterType.N_QUIET.ordinal(), MistatsConstants.FilterAttr.N_QUIET);
        sFilterTypeToName.put(FilterType.N_SODA.ordinal(), MistatsConstants.FilterAttr.N_SODA);
        sFilterTypeToName.put(FilterType.N_WARM.ordinal(), MistatsConstants.FilterAttr.N_WARM);
        sFilterTypeToName.put(FilterType.N_CLASSIC.ordinal(), MistatsConstants.FilterAttr.N_CLASSIC);
        sFilterTypeToName.put(FilterType.B_FAIRYTALE.ordinal(), MistatsConstants.FilterAttr.B_FAIRYTALE);
        sFilterTypeToName.put(FilterType.B_JAPANESE.ordinal(), MistatsConstants.FilterAttr.B_JAPANESE);
        sFilterTypeToName.put(FilterType.B_MINT.ordinal(), MistatsConstants.FilterAttr.B_MINT);
        sFilterTypeToName.put(FilterType.B_MOOD.ordinal(), MistatsConstants.FilterAttr.B_MOOD);
        sFilterTypeToName.put(FilterType.B_NATURE.ordinal(), MistatsConstants.FilterAttr.B_NATURE);
        sFilterTypeToName.put(FilterType.B_PINK.ordinal(), MistatsConstants.FilterAttr.B_PINK);
        sFilterTypeToName.put(FilterType.B_ROMANCE.ordinal(), MistatsConstants.FilterAttr.B_ROMANCE);
        sFilterTypeToName.put(FilterType.B_MAZE.ordinal(), MistatsConstants.FilterAttr.B_MAZE);
        sFilterTypeToName.put(FilterType.B_WHITEANDBLACK.ordinal(), MistatsConstants.FilterAttr.B_WHITEANDBLACK);
        sFilterTypeToName.put(FilterType.S_FILM.ordinal(), MistatsConstants.FilterAttr.S_FILM);
        sFilterTypeToName.put(FilterType.S_YEARS.ordinal(), MistatsConstants.FilterAttr.S_YEARS);
        sFilterTypeToName.put(FilterType.S_POLAROID.ordinal(), MistatsConstants.FilterAttr.S_POLAROID);
        sFilterTypeToName.put(FilterType.S_FOREST.ordinal(), MistatsConstants.FilterAttr.S_FOREST);
        sFilterTypeToName.put(FilterType.S_BYGONE.ordinal(), MistatsConstants.FilterAttr.S_BYGONE);
        sFilterTypeToName.put(FilterType.S_WHITEANDBLACK.ordinal(), MistatsConstants.FilterAttr.S_WHITEANDBLACK);
        sFilterTypeToName.put(FilterType.N_WHITEANDBLACK.ordinal(), MistatsConstants.FilterAttr.N_WHITEANDBLACK);
        sFilterTypeToName.put(FilterType.B_RIDDLE.ordinal(), MistatsConstants.FilterAttr.B_RIDDLE);
        sFilterTypeToName.put(FilterType.B_STORY.ordinal(), MistatsConstants.FilterAttr.B_STORY);
        sFilterTypeToName.put(FilterType.B_MOVIE.ordinal(), MistatsConstants.FilterAttr.B_MOVIE);
        sFilterTypeToName.put(FilterType.B_M_TEA.ordinal(), MistatsConstants.FilterAttr.B_M_TEA);
        sFilterTypeToName.put(FilterType.B_M_LILT.ordinal(), MistatsConstants.FilterAttr.B_M_LILT);
        sFilterTypeToName.put(FilterType.B_M_SEPIA.ordinal(), MistatsConstants.FilterAttr.B_M_SEPIA);
        sFilterTypeToName.put(FilterType.B_M_WHITEANDBLACK.ordinal(), MistatsConstants.FilterAttr.B_M_WHITEANDBLACK);
        sFilterTypeToName.put(FilterType.A_DOC.ordinal(), MistatsConstants.FilterAttr.A1_DOC);
        sFilterTypeToName.put(FilterType.A_FLOWER.ordinal(), MistatsConstants.FilterAttr.A2_FLOWER);
        sFilterTypeToName.put(FilterType.A_FOOD.ordinal(), MistatsConstants.FilterAttr.A3_FOOD);
        sFilterTypeToName.put(FilterType.A_PPT.ordinal(), MistatsConstants.FilterAttr.A4_PPT);
        sFilterTypeToName.put(FilterType.A_SKY.ordinal(), MistatsConstants.FilterAttr.A5_SKY);
        sFilterTypeToName.put(FilterType.A_SUNRISE_SUNSET.ordinal(), MistatsConstants.FilterAttr.A6_SUNRISE_SUNSET);
        sFilterTypeToName.put(FilterType.A_CAT.ordinal(), MistatsConstants.FilterAttr.A7_CAT);
        sFilterTypeToName.put(FilterType.A_DOG.ordinal(), MistatsConstants.FilterAttr.A8_DOG);
        sFilterTypeToName.put(FilterType.A_GREEN_PLANTS.ordinal(), MistatsConstants.FilterAttr.A9_GREEN_PLANTS);
        sFilterTypeToName.put(FilterType.A_NIGHT.ordinal(), MistatsConstants.FilterAttr.A10_NIGHT);
        sFilterTypeToName.put(FilterType.A_SNOW.ordinal(), MistatsConstants.FilterAttr.A11_SNOW);
        sFilterTypeToName.put(FilterType.A_SEA.ordinal(), MistatsConstants.FilterAttr.A12_SEA);
        sFilterTypeToName.put(FilterType.A_AUTUMN.ordinal(), MistatsConstants.FilterAttr.A13_AUTUMN);
        sFilterTypeToName.put(FilterType.A_CANDLELIGHT.ordinal(), MistatsConstants.FilterAttr.A14_CANDLELIGHT);
        sFilterTypeToName.put(FilterType.A_CAR.ordinal(), MistatsConstants.FilterAttr.A15_CAR);
        sFilterTypeToName.put(FilterType.A_GRASS.ordinal(), MistatsConstants.FilterAttr.A16_GRASS);
        sFilterTypeToName.put(FilterType.A_MAPLE_LEAVES.ordinal(), MistatsConstants.FilterAttr.A17_MAPLE_LEAVES);
        sFilterTypeToName.put(FilterType.A_SUCCULENT.ordinal(), MistatsConstants.FilterAttr.A18_SUCCULENT);
        sFilterTypeToName.put(FilterType.A_BUILDING.ordinal(), MistatsConstants.FilterAttr.A19_BUILDING);
        sFilterTypeToName.put(FilterType.A_CITY.ordinal(), MistatsConstants.FilterAttr.A20_CITY);
        sFilterTypeToName.put(FilterType.A_CLOUD.ordinal(), MistatsConstants.FilterAttr.A21_CLOUD);
        sFilterTypeToName.put(FilterType.A_OVERCAST.ordinal(), MistatsConstants.FilterAttr.A22_OVERCAST);
        sFilterTypeToName.put(FilterType.A_BACKLIGHT.ordinal(), MistatsConstants.FilterAttr.A23_BACKLIGHT);
        sFilterTypeToName.put(FilterType.A_SILHOUETTE.ordinal(), MistatsConstants.FilterAttr.A24_SILHOUETTE);
        sFilterTypeToName.put(FilterType.A_HUMAN.ordinal(), MistatsConstants.FilterAttr.A25_HUMAN);
        sFilterTypeToName.put(FilterType.A_JEWELRY.ordinal(), MistatsConstants.FilterAttr.A26_JEWELRY);
        sFilterTypeToName.put(FilterType.A_BUDDHA.ordinal(), MistatsConstants.FilterAttr.A27_BUDDHA);
        sFilterTypeToName.put(FilterType.A_COW.ordinal(), MistatsConstants.FilterAttr.A28_COW);
        sFilterTypeToName.put(FilterType.A_CURRY.ordinal(), MistatsConstants.FilterAttr.A29_CURRY);
        sFilterTypeToName.put(FilterType.A_MOTORBIKE.ordinal(), MistatsConstants.FilterAttr.A30_MOTORBIKE);
        sFilterTypeToName.put(FilterType.A_TEMPLE.ordinal(), MistatsConstants.FilterAttr.A31_TEMPLE);
        sFilterTypeToName.put(FilterType.A_BEACH.ordinal(), MistatsConstants.FilterAttr.A32_BEACH);
        sFilterTypeToName.put(FilterType.A_DIVING.ordinal(), MistatsConstants.FilterAttr.A33_DRIVING);
        sFilterTypeToName.put(FilterType.BI_SUNNY.ordinal(), MistatsConstants.FilterAttr.BI_SUNNY);
        sFilterTypeToName.put(FilterType.BI_PINK.ordinal(), MistatsConstants.FilterAttr.BI_PINK);
        sFilterTypeToName.put(FilterType.BI_MEMORY.ordinal(), MistatsConstants.FilterAttr.BI_MEMORY);
        sFilterTypeToName.put(FilterType.BI_STRONG.ordinal(), MistatsConstants.FilterAttr.BI_STRONG);
        sFilterTypeToName.put(FilterType.BI_WARM.ordinal(), MistatsConstants.FilterAttr.BI_WARM);
        sFilterTypeToName.put(FilterType.BI_RETRO.ordinal(), MistatsConstants.FilterAttr.BI_RETRO);
        sFilterTypeToName.put(FilterType.BI_ROMANTIC.ordinal(), MistatsConstants.FilterAttr.BI_ROMANTIC);
        sFilterTypeToName.put(FilterType.BI_SWEET.ordinal(), MistatsConstants.FilterAttr.BI_SWEET);
        sFilterTypeToName.put(FilterType.BI_PORTRAIT.ordinal(), MistatsConstants.FilterAttr.BI_PORTRAIT);
        sFilterTypeToName.put(FilterType.BI_YOUNG.ordinal(), MistatsConstants.FilterAttr.BI_YOUNG);
        sFilterTypeToName.put(FilterType.BI_M_DUSK.ordinal(), MistatsConstants.FilterAttr.BI_M_DUSK);
        sFilterTypeToName.put(FilterType.BI_M_LILT.ordinal(), MistatsConstants.FilterAttr.BI_M_LILT);
        sFilterTypeToName.put(FilterType.BI_M_TEA.ordinal(), MistatsConstants.FilterAttr.BI_M_TEA);
        sFilterTypeToName.put(FilterType.BI_M_SEPIA.ordinal(), MistatsConstants.FilterAttr.BI_M_SEPIA);
        sFilterTypeToName.put(FilterType.BI_M_WHITEANDBLACK.ordinal(), MistatsConstants.FilterAttr.BI_M_WHITEANDBLACK);
        sFilterTypeToName.put(FilterType.ML_BLUE.ordinal(), MistatsConstants.FilterAttr.ML_BLUE);
        sFilterTypeToName.put(FilterType.ML_CONTRAST.ordinal(), MistatsConstants.FilterAttr.ML_CONTRAST);
        sFilterTypeToName.put(FilterType.ML_DEEPBLACK.ordinal(), MistatsConstants.FilterAttr.ML_DEEPBLACK);
        sFilterTypeToName.put(FilterType.ML_FAIR.ordinal(), MistatsConstants.FilterAttr.ML_FAIR);
        sFilterTypeToName.put(FilterType.ML_HONGKONG.ordinal(), MistatsConstants.FilterAttr.ML_HONGKONG);
        sFilterTypeToName.put(FilterType.ML_MOUSSE.ordinal(), MistatsConstants.FilterAttr.ML_MOUSSE);
        sFilterTypeToName.put(FilterType.ML_SOLAR.ordinal(), MistatsConstants.FilterAttr.ML_SOLAR);
        sFilterTypeToName.put(FilterType.ML_YEARS.ordinal(), MistatsConstants.FilterAttr.ML_YEARS);
        sSpeedToName.put(0, "Super slow");
        sSpeedToName.put(1, "Slow");
        sSpeedToName.put(2, "Regular");
        sSpeedToName.put(3, "Fast");
        sSpeedToName.put(4, "Super fast");
    }

    private static String addCameraSuffix(String str) {
        if (str == null) {
            return str;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append("_");
        sb.append(CameraSettings.isFrontCamera() ? "front" : MistatsConstants.BaseEvent.BACK);
        return sb.toString();
    }

    private static void addUltraPixelParameter(Map<String, String> map) {
        boolean isUltraPixelOn = CameraSettings.isUltraPixelOn();
        String str = "off";
        if (!CameraSettings.isFrontCamera()) {
            int Hb = DataRepository.dataItemFeature().Hb();
            boolean isRear108MPSwitchOn = DataRepository.dataItemRunning().getComponentUltraPixel().isRear108MPSwitchOn();
            if (Hb == 1) {
                if (isUltraPixelOn) {
                    str = MistatsConstants.Manual.VALUE_SUPERME_PIXEL_48M_ON;
                }
                map.put(MistatsConstants.Manual.PARAM_SUPERME_PIXEL_VALUE, str);
            } else if (Hb == 2) {
                if (isUltraPixelOn) {
                    str = MistatsConstants.Manual.VALUE_SUPERME_PIXEL_64M_ON;
                }
                map.put(MistatsConstants.Manual.PARAM_SUPERME_PIXEL_VALUE, str);
            } else if (isRear108MPSwitchOn) {
                map.put(MistatsConstants.Manual.PARAM_SUPERME_PIXEL_VALUE, MistatsConstants.Manual.VALUE_SUPERME_PIXEL_108M_ON);
            } else {
                map.put(MistatsConstants.Manual.PARAM_SUPERME_PIXEL_VALUE, str);
            }
        } else if (DataRepository.dataItemFeature().Ab() == 0) {
            if (isUltraPixelOn) {
                str = MistatsConstants.Manual.VALUE_SUPERME_PIXEL_32M_ON;
            }
            map.put(MistatsConstants.Manual.PARAM_SUPERME_PIXEL_VALUE, str);
        }
    }

    private static void addUltraPixelParameter(boolean z, Map<String, String> map) {
        boolean isUltraPixelOn = CameraSettings.isUltraPixelOn();
        String str = "off";
        if (!z) {
            int Hb = DataRepository.dataItemFeature().Hb();
            if (Hb == 1) {
                if (isUltraPixelOn) {
                    str = MistatsConstants.PictureQuality.VALUE_ULTRA_PIXEL_48MP;
                }
                map.put(MistatsConstants.PictureQuality.PARAM_ULTRA_PIXEL, str);
            } else if (Hb == 2) {
                if (isUltraPixelOn) {
                    str = MistatsConstants.PictureQuality.VALUE_ULTRA_PIXEL_64MP;
                }
                map.put(MistatsConstants.PictureQuality.PARAM_ULTRA_PIXEL, str);
            } else if (Hb == 3) {
                if (isUltraPixelOn) {
                    str = MistatsConstants.PictureQuality.VALUE_ULTRA_PIXEL_108MP;
                }
                map.put(MistatsConstants.PictureQuality.PARAM_ULTRA_PIXEL, str);
            }
        } else if (DataRepository.dataItemFeature().Ab() == 0) {
            if (isUltraPixelOn) {
                str = MistatsConstants.PictureQuality.VALUE_ULTRA_PIXEL_32MP;
            }
            map.put(MistatsConstants.PictureQuality.PARAM_ULTRA_PIXEL, str);
        }
    }

    private static String antiBandingToName(String str) {
        if (str == null) {
            Log.e(TAG, "null antiBanding");
            return MistatsConstants.BaseEvent.OTHERS;
        }
        char c2 = 65535;
        switch (str.hashCode()) {
            case 48:
                if (str.equals("0")) {
                    c2 = 0;
                    break;
                }
                break;
            case 49:
                if (str.equals("1")) {
                    c2 = 1;
                    break;
                }
                break;
            case 50:
                if (str.equals("2")) {
                    c2 = 2;
                    break;
                }
                break;
            case 51:
                if (str.equals("3")) {
                    c2 = 3;
                    break;
                }
                break;
        }
        if (c2 == 0) {
            return "off";
        }
        if (c2 == 1) {
            return "50hz";
        }
        if (c2 == 2) {
            return "60hz";
        }
        if (c2 == 3) {
            return "auto";
        }
        Log.e(TAG, "unexpected antiBanding " + str);
        return MistatsConstants.BaseEvent.OTHERS;
    }

    private static String autoWhiteBalanceToName(String str) {
        if (str == null) {
            Log.e(TAG, "null awb");
            return MistatsConstants.BaseEvent.OTHERS;
        }
        char c2 = 65535;
        int hashCode = str.hashCode();
        if (hashCode != -1081415738) {
            if (hashCode != 53) {
                if (hashCode != 54) {
                    switch (hashCode) {
                        case 49:
                            if (str.equals("1")) {
                                c2 = 1;
                                break;
                            }
                            break;
                        case 50:
                            if (str.equals("2")) {
                                c2 = 2;
                                break;
                            }
                            break;
                        case 51:
                            if (str.equals("3")) {
                                c2 = 3;
                                break;
                            }
                            break;
                    }
                } else if (str.equals("6")) {
                    c2 = 5;
                }
            } else if (str.equals("5")) {
                c2 = 4;
            }
        } else if (str.equals("manual")) {
            c2 = 0;
        }
        if (c2 == 0) {
            return str;
        }
        if (c2 == 1) {
            return "auto";
        }
        if (c2 == 2) {
            return "incandescent";
        }
        if (c2 == 3) {
            return "fluorescent";
        }
        if (c2 == 4) {
            return "daylight";
        }
        if (c2 == 5) {
            return "cloudy-daylight";
        }
        Log.e(TAG, "unexpected awb " + str);
        return MistatsConstants.BaseEvent.OTHERS;
    }

    public static String burstShotNumToName(int i) {
        return divideTo10Section(i);
    }

    public static String cameraIdToName(boolean z) {
        return z ? "front" : MistatsConstants.BaseEvent.BACK;
    }

    private static String contrastToName(String str) {
        return pictureQualityToName(R.array.pref_camera_contrast_entryvalues, str);
    }

    private static String divideTo10Section(int i) {
        if (i == 0) {
            return MistatsConstants.BeautyBodySlimAttr.ZERO;
        }
        switch (i > 0 ? (i - 1) / 10 : 0) {
            case 0:
                return MistatsConstants.BeautyBodySlimAttr.ONE;
            case 1:
                return MistatsConstants.BeautyBodySlimAttr.TEN;
            case 2:
                return MistatsConstants.BeautyBodySlimAttr.TWENTY;
            case 3:
                return MistatsConstants.BeautyBodySlimAttr.THIRDTY;
            case 4:
                return MistatsConstants.BeautyBodySlimAttr.FOURTY;
            case 5:
                return MistatsConstants.BeautyBodySlimAttr.FIFTY;
            case 6:
                return MistatsConstants.BeautyBodySlimAttr.SIXTY;
            case 7:
                return MistatsConstants.BeautyBodySlimAttr.SEVENTY;
            case 8:
                return MistatsConstants.BeautyBodySlimAttr.EIGHTTY;
            default:
                return MistatsConstants.BeautyBodySlimAttr.NINETY;
        }
    }

    private static String exposureTimeToName(String str) {
        if (str != null) {
            try {
                int parseLong = (int) (Long.parseLong(str) / 1000);
                if (parseLong < 1000000) {
                    String str2 = sExposureTimeLessThan1sToName.get(parseLong);
                    if (str2 != null) {
                        return str2;
                    }
                } else {
                    return (parseLong / 1000000) + "s";
                }
            } catch (NumberFormatException unused) {
                String str3 = TAG;
                Log.e(str3, "invalid exposure time " + str);
            }
        }
        String str4 = TAG;
        Log.e(str4, "unexpected exposure time " + str);
        return "auto";
    }

    public static String faceBeautyRatioToName(int i) {
        return i == 0 ? "0" : divideTo10Section(i);
    }

    private static String filterIdToName(int i) {
        String str;
        if (FilterInfo.FILTER_ID_NONE == i) {
            return MistatsConstants.BaseEvent.RESET;
        }
        int category = FilterInfo.getCategory(i);
        if ((category == 1 || category == 2 || category == 3 || category == 8) && (str = sFilterTypeToName.get(FilterInfo.getIndex(i))) != null) {
            return str;
        }
        String str2 = TAG;
        Log.e(str2, "unexpected filter id: " + Integer.toHexString(i));
        return "none";
    }

    private static String flashModeToName(String str) {
        if (str == null) {
            Log.e(TAG, "null flash mode");
            return MistatsConstants.BaseEvent.OTHERS;
        }
        char c2 = 65535;
        int hashCode = str.hashCode();
        if (hashCode != 48626) {
            if (hashCode != 48628) {
                switch (hashCode) {
                    case 48:
                        if (str.equals("0")) {
                            c2 = 5;
                            break;
                        }
                        break;
                    case 49:
                        if (str.equals("1")) {
                            c2 = 1;
                            break;
                        }
                        break;
                    case 50:
                        if (str.equals("2")) {
                            c2 = 4;
                            break;
                        }
                        break;
                    case 51:
                        if (str.equals("3")) {
                            c2 = 0;
                            break;
                        }
                        break;
                }
            } else if (str.equals(ComponentConfigFlash.FLASH_VALUE_SCREEN_LIGHT_AUTO)) {
                c2 = 2;
            }
        } else if (str.equals(ComponentConfigFlash.FLASH_VALUE_SCREEN_LIGHT_ON)) {
            c2 = 3;
        }
        if (c2 == 0) {
            return "auto";
        }
        if (c2 == 1) {
            return "on";
        }
        if (c2 == 2) {
            return MistatsConstants.FlashAttr.FLASH_VALUE_SCREEN_LIGHT_AUTO;
        }
        if (c2 == 3) {
            return MistatsConstants.FlashAttr.FLASH_VALUE_SCREEN_LIGHT_ON;
        }
        if (c2 == 4) {
            return "torch";
        }
        if (c2 == 5) {
            return "off";
        }
        Log.e(TAG, "unexpected flash mode " + str);
        return MistatsConstants.BaseEvent.OTHERS;
    }

    private static String focusPositionToName(int i) {
        return 1000 == i ? "auto" : divideTo10Section((1000 - i) / 10);
    }

    public static String getDocumentModeValue(int i) {
        return CameraSettings.isDocumentModeOn(i) ? DataRepository.dataItemRunning().getComponentRunningDocument().getComponentValue(i) : "off";
    }

    private static String getDualZoomName(int i) {
        if (i == 166 || i == 167) {
            String cameraLensType = CameraSettings.getCameraLensType(i);
            if (ComponentManuallyDualLens.LENS_ULTRA.equals(cameraLensType)) {
                return HybridZoomingSystem.STRING_ZOOM_RATIO_ULTR;
            }
            if (ComponentManuallyDualLens.LENS_TELE.equals(cameraLensType)) {
                return HybridZoomingSystem.STRING_ZOOM_RATIO_TELE;
            }
            if (ComponentManuallyDualLens.LENS_WIDE.equals(cameraLensType)) {
                return HybridZoomingSystem.STRING_ZOOM_RATIO_WIDE;
            }
        }
        return HybridZoomingSystem.toString(CameraSettings.readZoom());
    }

    private static int indexOfString(String[] strArr, String str) {
        if (strArr == null || str == null) {
            return -1;
        }
        for (int i = 0; i < strArr.length; i++) {
            if (str.equals(strArr[i])) {
                return i;
            }
        }
        return -1;
    }

    private static String isoToName(String str) {
        return str != null ? "auto".equalsIgnoreCase(str) ? "auto" : str.toUpperCase(Locale.ENGLISH).indexOf("ISO") > -1 ? str.substring(3) : str : str;
    }

    public static String modeIdToName(int i) {
        String str = sCameraModeIdToName.get(i);
        return str == null ? MistatsConstants.ModuleName.UNSPECIFIED : str;
    }

    private static String pictureQualityToName(int i, String str) {
        String[] stringArray = CameraAppImpl.getAndroidContext().getResources().getStringArray(i);
        if (sPictureQualityIndexToName.size() >= stringArray.length) {
            int indexOfString = indexOfString(stringArray, str);
            if (indexOfString <= -1) {
                return MistatsConstants.BaseEvent.OTHERS;
            }
            return sPictureQualityIndexToName.get(indexOfString + ((sPictureQualityIndexToName.size() - stringArray.length) / 2));
        }
        throw new RuntimeException("picture quality array size is smaller than values size " + str.length());
    }

    private static long round(long j, int i) {
        if (i <= 0) {
            return j;
        }
        long j2 = (long) i;
        return ((j + ((long) (i / 2))) / j2) * j2;
    }

    private static String saturationToName(String str) {
        return pictureQualityToName(R.array.pref_camera_saturation_entryvalues, str);
    }

    private static String sharpnessToName(String str) {
        return pictureQualityToName(R.array.pref_camera_sharpness_entryvalues, str);
    }

    public static String slowMotionConfigToName(String str) {
        return ComponentConfigSlowMotion.DATA_CONFIG_NEW_SLOW_MOTION_120.equals(str) ? MistatsConstants.SlowMotion.FPS_120 : ComponentConfigSlowMotion.DATA_CONFIG_NEW_SLOW_MOTION_240.equals(str) ? MistatsConstants.SlowMotion.FPS_240 : MistatsConstants.SlowMotion.FPS_960;
    }

    private static String slowMotionQualityIdToName(String str) {
        if (str == null) {
            return MistatsConstants.BaseEvent.OTHERS;
        }
        char c2 = 65535;
        int hashCode = str.hashCode();
        if (hashCode != 53) {
            if (hashCode == 54 && str.equals("6")) {
                c2 = 1;
            }
        } else if (str.equals("5")) {
            c2 = 0;
        }
        return c2 != 0 ? c2 != 1 ? MistatsConstants.BaseEvent.OTHERS : MistatsConstants.BaseEvent.QUALITY_1080P : MistatsConstants.BaseEvent.QUALITY_720P;
    }

    private static String speedIdToName(int i) {
        String str = sSpeedToName.get(i);
        return str != null ? str : sSpeedToName.get(2);
    }

    public static void tarckBokenChanged(int i, String str) {
        if (!MistatsWrapper.isCounterEventDisabled()) {
            MistatsWrapper.commonKeyTriggerEvent(MistatsConstants.AlgoAttr.PARAM_BOKEN, str, null);
        }
    }

    public static String timeLapseIntervalToName(int i) {
        if (i < 1000) {
            return String.format(Locale.ENGLISH, "%.2fs", Float.valueOf(((float) i) / 1000.0f));
        }
        return String.format(Locale.ENGLISH, "%ds", Integer.valueOf(i / 1000));
    }

    public static void trackAISceneChanged(int i, int i2, Resources resources) {
        if (!MistatsWrapper.isCounterEventDisabled()) {
            TypedArray obtainTypedArray = resources.obtainTypedArray(R.array.ai_scene_names);
            String string = (i2 < 0 || i2 >= obtainTypedArray.length()) ? MistatsConstants.BaseEvent.UNSPECIFIED : obtainTypedArray.getString(i2);
            obtainTypedArray.recycle();
            MistatsWrapper.commonKeyTriggerEvent(MistatsConstants.AlgoAttr.VAULE_AI_SCENE, string, null);
        }
    }

    public static void trackAIWatermarkCapture(String str, String str2, String str3, String str4) {
        if (!MistatsWrapper.isCounterEventDisabled()) {
            HashMap hashMap = new HashMap();
            hashMap.put(MistatsConstants.AIWatermark.AI_WATERMARK_TYPE, str);
            hashMap.put(MistatsConstants.AIWatermark.AI_WATERMARK_KEY, str2);
            hashMap.put(MistatsConstants.AIWatermark.AI_WATERMARK_MOVE, str3);
            hashMap.put(MistatsConstants.AIWatermark.AI_WATERMARK_ORIENTATION, str4);
            MistatsWrapper.mistatEvent(MistatsConstants.AIWatermark.AI_WATERMARK, hashMap);
            trackAIWatermarkCategory(str);
        }
    }

    private static void trackAIWatermarkCategory(String str) {
        if (!MistatsWrapper.isCounterEventDisabled()) {
            try {
                int intValue = Integer.valueOf(str).intValue();
                HashMap hashMap = new HashMap();
                switch (intValue) {
                    case 0:
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                        hashMap.put(MistatsConstants.AIWatermark.AI_WATERMARK_CATEGORY, MistatsConstants.AIWatermark.AI_WATERMARK_MANUAL);
                        break;
                    case 5:
                    case 6:
                    case 7:
                    case 8:
                    case 9:
                    case 10:
                        hashMap.put(MistatsConstants.AIWatermark.AI_WATERMARK_CATEGORY, MistatsConstants.AIWatermark.AI_WATERMARK_AI);
                        break;
                }
                MistatsWrapper.mistatEvent(MistatsConstants.AIWatermark.AI_WATERMARK, hashMap);
            } catch (NumberFormatException unused) {
                Log.w(TAG, "NumberFormatException when parser type");
            }
        }
    }

    public static void trackAIWatermarkClick(String str) {
        if (!MistatsWrapper.isCounterEventDisabled()) {
            HashMap hashMap = new HashMap();
            hashMap.put(MistatsConstants.BaseEvent.OPERATE_STATE, str);
            MistatsWrapper.mistatEvent(MistatsConstants.AIWatermark.AI_WATERMARK, hashMap);
        }
    }

    public static void trackAIWatermarkKey(String str) {
        if (!MistatsWrapper.isCounterEventDisabled()) {
            HashMap hashMap = new HashMap();
            hashMap.put(MistatsConstants.AIWatermark.AI_WATERMARK_SELECT, str);
            MistatsWrapper.mistatEvent(MistatsConstants.AIWatermark.AI_WATERMARK, hashMap);
        }
    }

    public static void trackAwbChanged(String str, int i) {
        if (!MistatsWrapper.isCounterEventDisabled()) {
            MistatsWrapper.moduleUIClickEvent(i == 167 ? MistatsConstants.ModuleName.MANUAL : MistatsConstants.ModuleName.PROVIDEO, MistatsConstants.Manual.AWB, autoWhiteBalanceToName(str));
        }
    }

    public static void trackBeautyClick(@ComponentRunningShine.ShineType String str, String str2) {
        String str3;
        if (!TextUtils.isEmpty(str2)) {
            HashMap hashMap = new HashMap();
            hashMap.put(MistatsConstants.BaseEvent.MODULE_NAME, modeIdToName(DataRepository.dataItemGlobal().getCurrentMode()));
            hashMap.put(MistatsConstants.BaseEvent.MODE, MistatsConstants.BaseEvent.PHOTO);
            char c2 = 65535;
            switch (str.hashCode()) {
                case 49:
                    if (str.equals("1")) {
                        c2 = 0;
                        break;
                    }
                    break;
                case 50:
                    if (str.equals("2")) {
                        c2 = 1;
                        break;
                    }
                    break;
                case 51:
                    if (str.equals("3")) {
                        c2 = 2;
                        break;
                    }
                    break;
                case 52:
                    if (str.equals("4")) {
                        c2 = 3;
                        break;
                    }
                    break;
                case 53:
                    if (str.equals("5")) {
                        c2 = 4;
                        break;
                    }
                    break;
                case 54:
                    if (str.equals("6")) {
                        c2 = 5;
                        break;
                    }
                    break;
            }
            String str4 = null;
            if (c2 == 0 || c2 == 1 || c2 == 2 || c2 == 3 || c2 == 4) {
                str4 = sBeautyTypeToValue.get(str2);
                str3 = MistatsConstants.BeautyAttr.KEY_BEAUTY_FACE;
            } else if (c2 != 5) {
                str3 = null;
            } else {
                str4 = sBeautyTypeToValue.get(str2);
                str3 = MistatsConstants.BeautyBodySlimAttr.KEY_BODY_SLIM;
            }
            if (!TextUtils.isEmpty(str4)) {
                hashMap.put(MistatsConstants.BeautyBodySlimAttr.BEAUTY_PORT, str4);
            }
            if (!TextUtils.isEmpty(str3)) {
                MistatsWrapper.mistatEvent(str3, hashMap);
            }
        }
    }

    public static void trackBeautyInfo(int i, String str, BeautyValues beautyValues) {
        String str2;
        HashMap hashMap = new HashMap();
        if (beautyValues != null && beautyValues.isFaceBeautyOn()) {
            if (b.Il()) {
                String[] strArr = BeautyConstant.BEAUTY_CATEGORY_LEVEL;
                int i2 = 0;
                for (String str3 : strArr) {
                    String str4 = sBeautyTypeToName.get(str3);
                    if (str4 != null) {
                        hashMap.put(str4, faceBeautyRatioToName(beautyValues.getValueByType(str3)));
                    }
                }
                boolean equals = "front".equals(str);
                str2 = MistatsConstants.BeautyAttr.KEY_BEAUTY;
                if (equals) {
                    if (beautyValues.isFaceBeautyOn()) {
                        String[] strArr2 = BeautyConstant.BEAUTY_CATEGORY_FRONT_MAKEUP;
                        for (String str5 : strArr2) {
                            String str6 = sBeautyTypeToName.get(str5);
                            if (str6 != null) {
                                hashMap.put(str6, faceBeautyRatioToName(beautyValues.getValueByType(str5)));
                            }
                        }
                        if (beautyValues != null) {
                            hashMap.put(MistatsConstants.BeautyAttr.PARAM_BEAUTY_MAKEUP_SWITCH, beautyValues.isBeautyMakeUpOn() ? "on" : "off");
                        }
                    }
                    if (beautyValues.isBeautyMakeUpOn()) {
                        String[] strArr3 = BeautyConstant.BEAUTY_CATEGORY_FRONT_REMODELING;
                        int length = strArr3.length;
                        while (i2 < length) {
                            String str7 = strArr3[i2];
                            String str8 = sBeautyTypeToName.get(str7);
                            if (str8 != null) {
                                hashMap.put(str8, faceBeautyRatioToName(beautyValues.getValueByType(str7)));
                            }
                            i2++;
                        }
                    }
                } else if (beautyValues.isBeautyBodyOn()) {
                    String[] strArr4 = BeautyConstant.BEAUTY_CATEGORY_BACK_FIGURE;
                    int length2 = strArr4.length;
                    while (i2 < length2) {
                        String str9 = strArr4[i2];
                        String str10 = sBeautyTypeToName.get(str9);
                        if (str10 != null) {
                            hashMap.put(str10, faceBeautyRatioToName(beautyValues.getValueByType(str9)));
                        }
                        i2++;
                    }
                    str2 = MistatsConstants.BeautyBodySlimAttr.KEY_BODY_SLIM;
                }
                if (beautyValues != null) {
                    hashMap.put(MistatsConstants.BeautyAttr.PARAM_BEAUTY_LEVEL, String.valueOf(beautyValues.mBeautySkinSmooth));
                }
            } else {
                hashMap.put(MistatsConstants.BeautyAttr.PARAM_BEAUTY_SLIM_FACE, faceBeautyRatioToName(beautyValues.mBeautySlimFace));
                hashMap.put(MistatsConstants.BeautyAttr.PARAM_BEAUTY_ENLARGE_EYE, faceBeautyRatioToName(beautyValues.mBeautyEnlargeEye));
                hashMap.put(MistatsConstants.BeautyAttr.PARAM_BEAUTY_SKIN_COLOR, faceBeautyRatioToName(beautyValues.mBeautySkinColor));
                hashMap.put(MistatsConstants.BeautyAttr.PARAM_BEAUTY_SKIN_SMOOTH, faceBeautyRatioToName(beautyValues.mBeautySkinSmooth));
                hashMap.put(MistatsConstants.BeautyAttr.PARAM_BEAUTY_LEVEL, String.valueOf(beautyValues.mBeautyLevel));
                str2 = MistatsConstants.BeautyAttr.KEY_BEAUTY_OLD;
            }
            hashMap.put(MistatsConstants.BaseEvent.COUNT, String.valueOf(i));
            MistatsWrapper.mistatEvent(str2, hashMap);
        }
    }

    public static void trackBeautySwitchChanged(String str) {
        if (!MistatsWrapper.isCounterEventDisabled()) {
            HashMap hashMap = new HashMap();
            hashMap.put(MistatsConstants.BeautyAttr.PARAM_BEAUTY_LEVEL, str);
            MistatsWrapper.mistatEvent(MistatsConstants.BeautyAttr.KEY_BEAUTY_CLICK, hashMap);
        }
    }

    public static void trackBroadcastKillService() {
        HashMap hashMap = new HashMap();
        hashMap.put(MistatsConstants.BaseEvent.FEATURE_NAME, MistatsConstants.Error.CAMERA_BROADCAST_KILL_SERVICE);
        MistatsWrapper.mistatEvent(MistatsConstants.Error.KEY_CAMERA_EXCEPTION, hashMap);
    }

    public static void trackCallerControl(Intent intent, String str) {
        if (intent != null) {
            new HashMap();
            try {
                CameraIntentManager.getInstance(intent).isUseFrontCamera();
            } catch (Exception unused) {
            }
            MistatsWrapper.commonKeyTriggerEvent(MistatsConstants.BaseEvent.CAMERA_CALLER, str, null);
        }
    }

    public static void trackCameraError(String str) {
        HashMap hashMap = new HashMap();
        hashMap.put(MistatsConstants.BaseEvent.VALUE_ERROR_MSG, str);
        hashMap.put(MistatsConstants.BaseEvent.FEATURE_NAME, MistatsConstants.Error.CAMERA_HARDWARE_ERROR);
        MistatsWrapper.mistatEvent(MistatsConstants.Error.KEY_CAMERA_EXCEPTION, hashMap);
    }

    public static void trackCameraErrorDialogShow() {
        HashMap hashMap = new HashMap();
        hashMap.put(MistatsConstants.BaseEvent.FEATURE_NAME, MistatsConstants.Error.CAMERA_ERROR_DIALOG_SHOW);
        MistatsWrapper.mistatEvent(MistatsConstants.Error.KEY_CAMERA_EXCEPTION, hashMap);
    }

    public static void trackCapturePortrait(Map<String, String> map) {
        if (map == null) {
            map = new HashMap<>();
        }
        if (DataRepository.dataItemFeature().isSupportBokehAdjust()) {
            map.put(MistatsConstants.PortraitAttr.PARAM_BOKEH_RATIO, CameraSettings.readFNumber());
        }
        map.put(MistatsConstants.PortraitAttr.PARAM_ULTRA_WIDE_BOKEH, DataRepository.dataItemRunning().isSwitchOn("pref_ultra_wide_bokeh_enabled") ? "on" : "off");
        if (!CameraSettings.isFrontCamera()) {
            map.put(MistatsConstants.PortraitAttr.PARAM_PORTRAIT_LIGHTING, String.valueOf(CameraSettings.getPortraitLightingPattern()));
        }
        map.put(MistatsConstants.BaseEvent.MODE, MistatsConstants.BaseEvent.PHOTO);
        MistatsWrapper.moduleCaptureEvent(MistatsConstants.ModuleName.PORTRAIT, map);
    }

    public static void trackDirectionChanged(int i) {
        if (!MistatsWrapper.isCounterEventDisabled()) {
            MistatsWrapper.moduleUIClickEvent(MistatsConstants.ModuleName.PANORAMA, MistatsConstants.Panorama.PANORAMA_DIRECTION, 3 == i ? MistatsConstants.Panorama.PANORAMA_DIRECTION_L2R : MistatsConstants.Panorama.PANORAMA_DIRECTION_R2L);
        }
    }

    public static void trackDocumentDetectBlurHintShow() {
        HashMap hashMap = new HashMap();
        hashMap.put(MistatsConstants.CaptureAttr.PARAM_ASD_DETECT_TIP, MistatsConstants.CaptureAttr.VALUE_ASD_DOCUMENT_BLUR_TIP);
        MistatsWrapper.mistatEvent(MistatsConstants.FeatureName.KEY_COMMON_TIPS, hashMap);
    }

    public static void trackDocumentModeChanged(String str) {
        if (!MistatsWrapper.isCounterEventDisabled()) {
            MistatsWrapper.commonKeyTriggerEvent(MistatsConstants.FeatureName.VALUE_DOCUMENT_MODE, str, null);
        }
    }

    public static void trackDualWaterMarkChanged(boolean z) {
        MistatsWrapper.settingClickEvent(MistatsConstants.Setting.PARAM_DEVICE_WATERMARK, Boolean.valueOf(z));
    }

    public static void trackDualZoomChanged(int i, String str) {
        if (!MistatsWrapper.isCounterEventDisabled()) {
            HashMap hashMap = new HashMap();
            hashMap.put(MistatsConstants.BaseEvent.MODULE_NAME, modeIdToName(i));
            hashMap.put(MistatsConstants.Zoom.PARAM_ZOOM_RATIO, str);
            if (HybridZoomingSystem.IS_3_OR_MORE_SAT) {
                hashMap.put(MistatsConstants.Zoom.PARAM_SAT_ZOOM_RATIO, str);
            }
            MistatsWrapper.mistatEvent(MistatsConstants.Zoom.KEY_ZOOM, hashMap);
        }
    }

    public static void trackEVChanged(String str, int i) {
        if (!MistatsWrapper.isCounterEventDisabled()) {
            MistatsWrapper.moduleUIClickEvent(i == 167 ? MistatsConstants.ModuleName.MANUAL : MistatsConstants.ModuleName.PROVIDEO, "exposureValue", str);
        }
    }

    public static void trackEvAdjusted(float f2) {
        MistatsWrapper.commonKeyTriggerEvent(MistatsConstants.FeatureName.VALUE_EV_ADJUSTED, Float.valueOf(f2), null);
    }

    public static void trackExposureTimeChanged(String str, int i) {
        if (!MistatsWrapper.isCounterEventDisabled()) {
            MistatsWrapper.moduleUIClickEvent(i == 167 ? MistatsConstants.ModuleName.MANUAL : MistatsConstants.ModuleName.PROVIDEO, MistatsConstants.Manual.ET, exposureTimeToName(str));
        }
    }

    public static void trackFilterChanged(int i, boolean z) {
        if (!MistatsWrapper.isCounterEventDisabled()) {
            if (z) {
                MistatsWrapper.featureTriggerEvent(MistatsConstants.FilterAttr.KEY_FILTER_CHANGED, filterIdToName(i), "click");
            } else {
                MistatsWrapper.featureTriggerEvent(MistatsConstants.FilterAttr.KEY_FILTER_CHANGED, filterIdToName(i), MistatsConstants.BaseEvent.SLIDE);
            }
        }
    }

    public static void trackFlashChanged(int i, String str) {
        if (!MistatsWrapper.isCounterEventDisabled()) {
            MistatsWrapper.commonKeyTriggerEvent(MistatsConstants.FlashAttr.PARAM_FLASH_MODE, flashModeToName(str), null);
        }
    }

    public static void trackFocusPositionChanged(int i, int i2) {
        if (!MistatsWrapper.isCounterEventDisabled()) {
            MistatsWrapper.moduleUIClickEvent(i2 == 167 ? MistatsConstants.ModuleName.MANUAL : MistatsConstants.ModuleName.PROVIDEO, MistatsConstants.Manual.FOCUS_POSITION, focusPositionToName(i));
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:75:0x0158, code lost:
        if (r1 != false) goto L_0x015c;
     */
    /* JADX WARNING: Removed duplicated region for block: B:79:0x0163  */
    /* JADX WARNING: Removed duplicated region for block: B:80:0x0165  */
    public static void trackGeneralInfo(Map map, boolean z, boolean z2, int i, int i2, boolean z3, int i3, BeautyValues beautyValues, MutexModeManager mutexModeManager, String str) {
        ComponentRunningTimer componentRunningTimer;
        DataItemRunning dataItemRunning = DataRepository.dataItemRunning();
        DataItemConfig dataItemConfig = DataRepository.dataItemConfig();
        Map hashMap = map == null ? new HashMap() : map;
        hashMap.put(MistatsConstants.BaseEvent.MODULE_NAME, modeIdToName(i));
        hashMap.put("attr_trigger_mode", triggerModeToName(i2));
        String str2 = "0";
        String componentValue = (z || (componentRunningTimer = dataItemRunning.getComponentRunningTimer()) == null) ? str2 : componentRunningTimer.getComponentValue(i);
        hashMap.put("attr_reference_line", String.valueOf(DataRepository.dataItemGlobal().getBoolean("pref_camera_referenceline_key", false)));
        hashMap.put("attr_timer", componentValue);
        String str3 = "close";
        hashMap.put(MistatsConstants.Setting.PARAM_SAVE_LOCATION, z2 ? MistatsConstants.BaseEvent.VALUE_NOT_NULL : CameraSettings.isRecordLocation() ? "null" : str3);
        ComponentConfigFlash componentFlash = dataItemConfig.getComponentFlash();
        if (componentFlash != null) {
            String componentValue2 = componentFlash.getComponentValue(i);
            if (!z || "2".equals(componentValue2)) {
                str2 = componentValue2;
            }
        }
        if (str != null) {
            hashMap.put(MistatsConstants.FlashAttr.PARAM_FLASH_MODE, str);
        } else {
            hashMap.put(MistatsConstants.FlashAttr.PARAM_FLASH_MODE, flashModeToName(str2));
        }
        hashMap.put("attr_filter", z ? "none" : filterIdToName(EffectController.getInstance().getEffectForSaving(false)));
        String str4 = "on";
        if (beautyValues != null) {
            hashMap.put(MistatsConstants.BeautyAttr.PARAM_BEAUTY_SWITCH, (z || !beautyValues.isFaceBeautyOn()) ? "off" : str4);
        }
        hashMap.put(MistatsConstants.BaseEvent.PARAM_PICTURE_RATIO, CameraSettings.getPictureSizeRatioString());
        addUltraPixelParameter(z3, hashMap);
        hashMap.put(MistatsConstants.BaseEvent.QUALITY, CameraSettings.getEncodingQuality(z).name().toLowerCase());
        if (z3) {
            hashMap.put(MistatsConstants.FeatureName.VALUE_GENDER_AGE, CameraSettings.showGenderAge() ? str4 : "off");
            hashMap.put(MistatsConstants.FeatureName.VALUE_MAGIC_MIRROR, CameraSettings.isMagicMirrorOn() ? str4 : "off");
            if (DataRepository.dataItemRunning().supportHandGesture()) {
                hashMap.put("attr_palm_shutter", CameraSettings.isHandGestureOpen() ? str4 : "off");
            }
            if (DataRepository.dataItemRunning().supportUltraPixelPortrait()) {
                hashMap.put(MistatsConstants.PortraitAttr.PARAM_ULTRAPIXEL_PORTRAIT, CameraSettings.isUltraPixelPortraitFrontOn() ? str4 : "off");
            }
        } else {
            hashMap.put(MistatsConstants.Zoom.PARAM_ZOOM_RATIO, getDualZoomName(i));
            trackSatState(i3, i, hashMap);
        }
        if (!z && mutexModeManager != null) {
            boolean isHdr = mutexModeManager.isHdr();
            ComponentConfigHdr componentHdr = dataItemConfig.getComponentHdr();
            if (componentHdr != null && "auto".equals(componentHdr.getComponentValue(i))) {
                str4 = isHdr ? MistatsConstants.BaseEvent.AUTO_ON : MistatsConstants.BaseEvent.AUTO_OFF;
                hashMap.put(MistatsConstants.AlgoAttr.PARAM_HDR, str4);
                if (!z2) {
                    str3 = MistatsConstants.BaseEvent.VALUE_NOT_NULL;
                } else if (CameraSettings.isRecordLocation()) {
                    str3 = "null";
                }
                hashMap.put(MistatsConstants.Setting.PARAM_SAVE_LOCATION, str3);
                MistatsWrapper.mistatEvent(MistatsConstants.BaseEvent.KEY_CAPTURE, hashMap);
            }
        }
        str4 = "off";
        hashMap.put(MistatsConstants.AlgoAttr.PARAM_HDR, str4);
        if (!z2) {
        }
        hashMap.put(MistatsConstants.Setting.PARAM_SAVE_LOCATION, str3);
        MistatsWrapper.mistatEvent(MistatsConstants.BaseEvent.KEY_CAPTURE, hashMap);
    }

    public static void trackGoogleLensOobeContinue(boolean z) {
        HashMap hashMap = new HashMap();
        hashMap.put(MistatsConstants.GoogleLens.PARAM_OOBE_CONTINUE_CLICK, z ? MistatsConstants.BaseEvent.ACCEPT : MistatsConstants.BaseEvent.REJECT);
        MistatsWrapper.mistatEvent(MistatsConstants.GoogleLens.KEY_GOOGLE_LENS, hashMap);
    }

    public static void trackGoogleLensPicker() {
        HashMap hashMap = new HashMap();
        hashMap.put(MistatsConstants.BaseEvent.OPERATE_STATE, MistatsConstants.GoogleLens.GOOGLE_LENS_PICKER);
        MistatsWrapper.mistatEvent(MistatsConstants.GoogleLens.KEY_GOOGLE_LENS, hashMap);
    }

    public static void trackGoogleLensPickerValue(boolean z) {
        HashMap hashMap = new HashMap();
        hashMap.put(MistatsConstants.GoogleLens.PARAM_PICK_WHICH, z ? MistatsConstants.GoogleLens.VALUE_GOOGLE_LENS : MistatsConstants.GoogleLens.VALUE_LOCK_AEAF);
        MistatsWrapper.mistatEvent(MistatsConstants.GoogleLens.KEY_GOOGLE_LENS, hashMap);
    }

    public static void trackGoogleLensTouchAndHold() {
        HashMap hashMap = new HashMap();
        hashMap.put(MistatsConstants.BaseEvent.OPERATE_STATE, MistatsConstants.GoogleLens.GOOGLE_LENS_TOUCH_AND_HOLD);
        MistatsWrapper.mistatEvent(MistatsConstants.GoogleLens.KEY_GOOGLE_LENS, hashMap);
    }

    public static void trackGotoGallery(int i) {
        MistatsWrapper.commonKeyTriggerEvent(MistatsConstants.FeatureName.VALUE_GOTO_GALLERY, null, null);
    }

    public static void trackGotoIDCard() {
        MistatsWrapper.commonKeyTriggerEvent(MistatsConstants.FeatureName.VALUE_GOTO_ID_CARD, null, null);
    }

    public static void trackGotoSettings(int i) {
        MistatsWrapper.commonKeyTriggerEvent(MistatsConstants.FeatureName.VALUE_GOTO_SETTINGS, null, null);
    }

    public static void trackHdrChanged(int i, String str) {
        if (!MistatsWrapper.isCounterEventDisabled()) {
            MistatsWrapper.commonKeyTriggerEvent(MistatsConstants.AlgoAttr.PARAM_HDR, str, null);
        }
    }

    public static void trackInterruptionNetwork() {
        HashMap hashMap = new HashMap();
        hashMap.put(MistatsConstants.FeatureName.VALUE_SUBTITLE, MistatsConstants.FeatureName.VALUE_SUBTITLE_NETWORK_INTERRUPTION);
        MistatsWrapper.mistatEvent(MistatsConstants.VideoAttr.KEY_VIDEO_COMMON_CLICK, hashMap);
    }

    public static void trackIsoChanged(String str, int i) {
        if (!MistatsWrapper.isCounterEventDisabled()) {
            MistatsWrapper.moduleUIClickEvent(i == 167 ? MistatsConstants.ModuleName.MANUAL : MistatsConstants.ModuleName.PROVIDEO, MistatsConstants.Manual.ISO, isoToName(str));
        }
    }

    public static void trackKaleidoscopeClick(String str) {
        if (!MistatsWrapper.isCounterEventDisabled()) {
            HashMap hashMap = new HashMap();
            hashMap.put(MistatsConstants.BaseEvent.OPERATE_STATE, str);
            MistatsWrapper.mistatEvent(MistatsConstants.ModuleName.MI_LIVE, hashMap);
        }
    }

    public static void trackKaleidoscopeValue(String str) {
        if (!MistatsWrapper.isCounterEventDisabled()) {
            HashMap hashMap = new HashMap();
            hashMap.put(MistatsConstants.MiLive.PARAM_MI_LIVE_KALEIDOSCOPE_NAME, str);
            MistatsWrapper.mistatEvent(MistatsConstants.ModuleName.MI_LIVE, hashMap);
        }
    }

    public static void trackLensChanged(String str, int i) {
        if (!MistatsWrapper.isCounterEventDisabled()) {
            MistatsWrapper.moduleUIClickEvent(i == 167 ? MistatsConstants.ModuleName.MANUAL : MistatsConstants.ModuleName.PROVIDEO, MistatsConstants.Manual.LENS, str);
        }
    }

    public static void trackLightingChanged(int i, String str) {
        if (!MistatsWrapper.isCounterEventDisabled()) {
            MistatsWrapper.moduleUIClickEvent(MistatsConstants.ModuleName.PORTRAIT, MistatsConstants.PortraitAttr.PORTRAIT_LIGHTING, str);
        }
    }

    public static void trackLiveBeautyClick(@ComponentRunningShine.ShineType String str) {
        if (!MistatsWrapper.isCounterEventDisabled()) {
            String str2 = null;
            char c2 = 65535;
            int hashCode = str.hashCode();
            if (hashCode != 1567) {
                if (hashCode == 1568 && str.equals(ComponentRunningShine.SHINE_LIVE_BEAUTY)) {
                    c2 = 1;
                }
            } else if (str.equals("10")) {
                c2 = 0;
            }
            if (c2 == 0) {
                str2 = "attr_filter";
            } else if (c2 == 1) {
                str2 = MistatsConstants.BeautyAttr.BEAUTY_TYPE_FACE;
            }
            if (!TextUtils.isEmpty(str2)) {
                HashMap hashMap = new HashMap();
                hashMap.put(MistatsConstants.Live.PARAM_LIVE_BEAUTY_TYPE, str2);
                MistatsWrapper.mistatEvent(MistatsConstants.ModuleName.LIVE, hashMap);
            }
        }
    }

    public static void trackLiveBeautyCounter(String str) {
        if (str != null) {
            HashMap hashMap = new HashMap();
            String str2 = null;
            if ("key_live_shrink_face_ratio" == str) {
                str2 = MistatsConstants.Live.VALUE_LIVE_SHRINK_FACE_RATIO;
            } else if ("key_live_enlarge_eye_ratio" == str) {
                str2 = MistatsConstants.Live.VALUE_LIVE_ENLARGE_EYE_RATIO;
            } else if ("key_live_smooth_strength" == str) {
                str2 = MistatsConstants.Live.VALUE_LIVE_SMOOTH_RATIO;
            } else if (BeautyConstant.BEAUTY_RESET == str) {
                str2 = MistatsConstants.BaseEvent.RESET;
            }
            if (!TextUtils.isEmpty(str2)) {
                hashMap.put(MistatsConstants.Live.PARAM_LIVE_BEAUTY_PORT, str2);
                MistatsWrapper.mistatEvent(MistatsConstants.Live.KEY_LIVE_BEAUTY, hashMap);
            }
        }
    }

    public static void trackLiveClick(String str) {
        if (!MistatsWrapper.isCounterEventDisabled()) {
            HashMap hashMap = new HashMap();
            hashMap.put(MistatsConstants.BaseEvent.OPERATE_STATE, str);
            MistatsWrapper.mistatEvent(MistatsConstants.ModuleName.LIVE, hashMap);
        }
    }

    public static void trackLiveRecordingParams(boolean z, String str, boolean z2, String str2, boolean z3, String str3, String str4, boolean z4, int i, int i2, int i3, int i4, boolean z5) {
        if (!MistatsWrapper.isCounterEventDisabled()) {
            HashMap hashMap = new HashMap();
            String str5 = "on";
            hashMap.put(MistatsConstants.Live.PARAM_LIVE_MUSIC_ON, z ? str5 : "off");
            if (z) {
                hashMap.put(MistatsConstants.Live.PARAM_LIVE_MUSIC_NAME, str);
            }
            hashMap.put(MistatsConstants.Live.PARAM_LIVE_FILTER_SEGMENT_ON, z2 ? str5 : "off");
            if (z2) {
                hashMap.put(MistatsConstants.Live.PARAM_LIVE_FILTER_NAME, str2);
            }
            hashMap.put(MistatsConstants.Live.PARAM_LIVE_STICKER_SEGMENT_ON, z3 ? str5 : "off");
            if (z3) {
                hashMap.put(MistatsConstants.Live.PARAM_LIVE_STICKER_NAME, str3);
            }
            hashMap.put(MistatsConstants.Live.PARAM_LIVE_SPEED_LEVEL, str4);
            if (!z4) {
                str5 = "off";
            }
            hashMap.put(MistatsConstants.Live.PARAM_LIVE_BEAUTY_SEGMENT_ON, str5);
            hashMap.put(MistatsConstants.Live.PARAM_LIVE_SHRINK_FACE_RATIO, divideTo10Section(i));
            hashMap.put(MistatsConstants.Live.PARAM_LIVE_ENLARGE_EYE_RATIO, divideTo10Section(i2));
            hashMap.put(MistatsConstants.Live.PARAM_LIVE_SMOOTH_RATIO, divideTo10Section(i3));
            hashMap.put(MistatsConstants.BaseEvent.QUALITY, videoQualityToName("" + i4));
            MistatsWrapper.mistatEvent(MistatsConstants.Live.KEY_LIVE_VIDEO_SEGMENT, hashMap);
        }
    }

    public static void trackLiveStickerDownload(String str, boolean z) {
        if (!MistatsWrapper.isCounterEventDisabled()) {
            HashMap hashMap = new HashMap();
            hashMap.put(MistatsConstants.Live.PARAM_LIVE_STICKER_NAME, str);
            hashMap.put(MistatsConstants.Live.PARAM_LIVE_STICKER_DOWNLOAD, z ? MistatsConstants.BaseEvent.VALUE_SUCCESS : MistatsConstants.BaseEvent.VALUE_FAILED);
            MistatsWrapper.mistatEvent(MistatsConstants.ModuleName.LIVE, hashMap);
        }
    }

    public static void trackLiveStickerMore(boolean z) {
        if (!MistatsWrapper.isCounterEventDisabled()) {
            HashMap hashMap = new HashMap();
            hashMap.put(MistatsConstants.BaseEvent.TO, z ? MistatsConstants.Live.VALUE_LIVE_STICKER_MARKET : MistatsConstants.Live.VALUE_LIVE_STICKER_APP);
            MistatsWrapper.mistatEvent(MistatsConstants.Live.LIVE_STICKER_MORE, hashMap);
        }
    }

    public static void trackLiveVideoParams(int i, float f2, boolean z, boolean z2, boolean z3) {
        if (!MistatsWrapper.isCounterEventDisabled()) {
            HashMap hashMap = new HashMap();
            String str = "on";
            hashMap.put(MistatsConstants.Live.PARAM_LIVE_FILTER_ON, z ? str : "off");
            hashMap.put(MistatsConstants.Live.PARAM_LIVE_STICKER_ON, z2 ? str : "off");
            if (!z3) {
                str = "off";
            }
            hashMap.put(MistatsConstants.Live.PARAM_LIVE_BEAUTY_ON, str);
            hashMap.put(MistatsConstants.Live.PARAM_LIVE_RECORD_SEGMENTS, Integer.toString(i));
            hashMap.put(MistatsConstants.Live.PARAM_LIVE_RECORD_TIME, Integer.toString((int) f2));
            MistatsWrapper.mistatEvent(MistatsConstants.Live.KEY_LIVE_VIDEO_COMPLETE, hashMap);
        }
    }

    public static void trackLostCount(int i) {
        HashMap hashMap = new HashMap();
        hashMap.put(MistatsConstants.AutoZoom.PARAM_TRACKING_LOST_OBJECT, i < 10 ? String.valueOf(i) : MistatsConstants.AutoZoom.AUTOZOOM_LOST_10_MORE);
        MistatsWrapper.mistatEvent(MistatsConstants.AutoZoom.KEY_AUTO_ZOOM, hashMap);
    }

    public static void trackLyingDirectPictureTaken(Map map, int i) {
        if (i != -1) {
            int i2 = i - 1;
            int i3 = (360 - (i2 >= 0 ? i2 % 360 : (i2 % 360) + 360)) % 360;
            if (i % 2 == 0) {
                map.put(MistatsConstants.BaseEvent.PARAM_LYING_DIRECT, "none");
            } else {
                map.put(MistatsConstants.BaseEvent.PARAM_LYING_DIRECT, String.valueOf(i3));
            }
        }
    }

    public static void trackLyingDirectShow(int i) {
        HashMap hashMap = new HashMap();
        hashMap.put(MistatsConstants.BaseEvent.PARAM_LYING_DIRECT, String.valueOf(i));
        MistatsWrapper.mistatEvent(MistatsConstants.FeatureName.KEY_COMMON_TIPS, hashMap);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0054, code lost:
        if (r3 != 169) goto L_0x005c;
     */
    public static void trackMacroModeTaken(int i) {
        String str;
        if (DataRepository.dataItemRunning().supportMacroMode()) {
            boolean isSwitchOn = DataRepository.dataItemRunning().getComponentRunningMacroMode().isSwitchOn(i);
            HashMap hashMap = new HashMap();
            if (isSwitchOn) {
                hashMap.put(MistatsConstants.Manual.PARAM_FOCUS_POSITION, Util.getZoomRatioText(CameraSettings.readZoom()));
                str = "on";
            } else {
                str = "off";
            }
            if (DataRepository.dataItemFeature().m1if()) {
                hashMap.put(MistatsConstants.SensorAttr.PARAM_STANDALONE_MACRO_MODE, str);
            } else {
                hashMap.put(MistatsConstants.SensorAttr.PARAM_MACRO_MODE, str);
            }
            String str2 = null;
            if (i != 162) {
                if (i == 163 || i == 165) {
                    str2 = MistatsConstants.BaseEvent.PHOTO;
                    hashMap.put(MistatsConstants.BaseEvent.MODE, str2);
                    MistatsWrapper.mistatEvent(MistatsConstants.MacroAttr.FUCNAME_MACRO_MODE, hashMap);
                }
            }
            str2 = "video";
            hashMap.put(MistatsConstants.BaseEvent.MODE, str2);
            MistatsWrapper.mistatEvent(MistatsConstants.MacroAttr.FUCNAME_MACRO_MODE, hashMap);
        }
    }

    public static void trackManuallyResetClick() {
        MistatsWrapper.moduleUIClickEvent(167, MistatsConstants.Manual.RESET_PARAMS_CLICK, "none");
    }

    public static void trackManuallyResetDialogCancel() {
        MistatsWrapper.moduleUIClickEvent(167, MistatsConstants.Manual.RESET_PARAMS_CLICK, "off");
    }

    public static void trackManuallyResetDialogOk() {
        MistatsWrapper.moduleUIClickEvent(167, MistatsConstants.Manual.RESET_PARAMS_CLICK, "on");
    }

    public static void trackManuallyResetShow() {
        MistatsWrapper.moduleUIClickEvent(167, MistatsConstants.Manual.RESET_PARAMS_SHOW, "none");
    }

    public static void trackMeterClick() {
        if (!MistatsWrapper.isCounterEventDisabled()) {
            MistatsWrapper.commonKeyTriggerEvent(MistatsConstants.FeatureName.VALUE_METER_ICON_CLICK, 1, null);
        }
    }

    public static void trackMiLiveBeautyCounter(String str) {
        if (str != null) {
            char c2 = 65535;
            switch (str.hashCode()) {
                case -2110473153:
                    if (str.equals("key_live_smooth_strength")) {
                        c2 = 2;
                        break;
                    }
                    break;
                case 77866287:
                    if (str.equals(BeautyConstant.BEAUTY_RESET)) {
                        c2 = 3;
                        break;
                    }
                    break;
                case 175697132:
                    if (str.equals("key_live_shrink_face_ratio")) {
                        c2 = 0;
                        break;
                    }
                    break;
                case 1771202045:
                    if (str.equals("key_live_enlarge_eye_ratio")) {
                        c2 = 1;
                        break;
                    }
                    break;
            }
            if (c2 == 0) {
                trackMiLiveClick(MistatsConstants.MiLive.VALUE_MI_LIVE_CLICK_SHRINK_FACE);
            } else if (c2 == 1) {
                trackMiLiveClick(MistatsConstants.MiLive.VALUE_MI_LIVE_CLICK_ENLARGE_EYE);
            } else if (c2 == 2) {
                trackMiLiveClick(MistatsConstants.MiLive.VALUE_MI_LIVE_CLICK_SMOOTH);
            } else if (c2 == 3) {
                trackMiLiveClick(MistatsConstants.MiLive.VALUE_MI_LIVE_CLICK_BEAUTY_RESET);
            }
        }
    }

    public static void trackMiLiveClick(String str) {
        if (!MistatsWrapper.isCounterEventDisabled()) {
            HashMap hashMap = new HashMap();
            hashMap.put(MistatsConstants.BaseEvent.OPERATE_STATE, str);
            MistatsWrapper.mistatEvent(MistatsConstants.ModuleName.MI_LIVE, hashMap);
        }
    }

    public static void trackMiLiveRecordingParams(int i, String str, int i2, int i3, boolean z, boolean z2, int i4, int i5, int i6, int i7, String str2) {
        if (!MistatsWrapper.isCounterEventDisabled()) {
            HashMap hashMap = new HashMap();
            hashMap.put(MistatsConstants.MiLive.PARAM_MI_LIVE_CAMERA_QUALITY, videoQualityToName(String.valueOf(i7)));
            hashMap.put(MistatsConstants.MiLive.PARAM_MI_LIVE_CAMERA_FACING, z ? "front" : MistatsConstants.BaseEvent.BACK);
            hashMap.put(MistatsConstants.MiLive.PARAM_MI_LIVE_SEGMENT_COUNT, String.valueOf(i));
            String str3 = "none";
            if (TextUtils.isEmpty(str)) {
                str = str3;
            }
            hashMap.put(MistatsConstants.MiLive.PARAM_MI_LIVE_MUSIC_NAME, str);
            if (i2 != FilterInfo.FILTER_ID_NONE) {
                str3 = filterIdToName(i2);
            }
            hashMap.put(MistatsConstants.MiLive.PARAM_MI_LIVE_FILTER_NAME, str3);
            hashMap.put(MistatsConstants.MiLive.PARAM_MI_LIVE_SPEED, speedIdToName(i3));
            hashMap.put(MistatsConstants.MiLive.PARAM_MI_LIVE_BEAUTY_ON, z2 ? "on" : "off");
            hashMap.put(MistatsConstants.MiLive.PARAM_MI_LIVE_SHRINK_FACE_RATIO, String.valueOf(i4));
            hashMap.put(MistatsConstants.MiLive.PARAM_MI_LIVE_ENLARGE_EYE_RATIO, String.valueOf(i5));
            hashMap.put(MistatsConstants.MiLive.PARAM_MI_LIVE_SMOOTH_RATIO, String.valueOf(i6));
            hashMap.put(MistatsConstants.MiLive.PARAM_MI_LIVE_KALEIDOSCOPE_NAME, str2);
            MistatsWrapper.mistatEvent(MistatsConstants.MiLive.KEY_MI_LIVE_VIDEO_SEGMENT, hashMap);
        }
    }

    public static void trackMimojiCaptureOrRecord(Map<String, String> map, String str, boolean z, boolean z2) {
        if (!MistatsWrapper.isCounterEventDisabled()) {
            map.put(MistatsConstants.FlashAttr.PARAM_FLASH_MODE, flashModeToName(str));
            if (z) {
                map.put(MistatsConstants.BaseEvent.MODE, MistatsConstants.BaseEvent.PHOTO);
            } else {
                map.put(MistatsConstants.BaseEvent.MODE, "video");
            }
            MistatsWrapper.moduleMistatsEvent(MistatsConstants.ModuleName.FUN_AR2, map);
        }
    }

    public static void trackMimojiClick(String str, String str2) {
        if (!MistatsWrapper.isCounterEventDisabled()) {
            HashMap hashMap = new HashMap();
            hashMap.put(MistatsConstants.BaseEvent.FEATURE_NAME, str);
            hashMap.put(MistatsConstants.BaseEvent.OPERATE_STATE, str2);
            MistatsWrapper.mistatEvent(MistatsConstants.Mimoji.KEY_MIMOJI_CLICK, hashMap);
        }
    }

    public static void trackMimojiCount(String str) {
        if (!MistatsWrapper.isCounterEventDisabled()) {
            HashMap hashMap = new HashMap();
            hashMap.put(MistatsConstants.Mimoji.MIMOJI_HISTORY_EMOJI_COUNT, Long.valueOf(str));
            MistatsWrapper.mistatEventSimple(MistatsConstants.ModuleName.FUN_AR2, hashMap);
        }
    }

    public static void trackMimojiSavePara(String str, Map<String, String> map) {
        if (!MistatsWrapper.isCounterEventDisabled()) {
            MistatsWrapper.mistatEvent(str, map);
        }
    }

    public static void trackModeSwitch() {
        MistatsWrapper.commonKeyTriggerEvent(MistatsConstants.ModuleName.VALUE_TARGET_MODE, modeIdToName(ModuleManager.getActiveModuleIndex()), null);
    }

    public static void trackMoonMode(Map map, boolean z, boolean z2) {
        if (z) {
            if (map == null) {
                map = new HashMap();
            }
            map.put(MistatsConstants.MoonAndNightAttr.PARAM_NIGHT_MOON_MODE, z2 ? MistatsConstants.MoonAndNightAttr.VAULE_MOON_MODE : MistatsConstants.MoonAndNightAttr.VAULE_NIGHT_MODE);
            map.put(MistatsConstants.Zoom.PARAM_ZOOM_RATIO, String.valueOf(CameraSettings.readZoom()));
        }
    }

    public static void trackNewSlowMotionVideoRecorded(String str, int i, int i2, int i3, long j, boolean z) {
        HashMap hashMap = new HashMap();
        hashMap.put(MistatsConstants.BaseEvent.SENSOR_ID, MistatsConstants.BaseEvent.BACK);
        hashMap.put(MistatsConstants.VideoAttr.PARAM_VIDEO_MODE, str);
        hashMap.put(MistatsConstants.BaseEvent.QUALITY, slowMotionQualityIdToName(String.valueOf(i)));
        hashMap.put(MistatsConstants.FlashAttr.PARAM_FLASH_MODE, i2 == 2 ? "torch" : "off");
        hashMap.put(MistatsConstants.VideoAttr.PARAM_VIDEO_FPS, String.valueOf(i3));
        hashMap.put(MistatsConstants.VideoAttr.PARAM_VIDEO_TIME, String.valueOf(j));
        if (CameraSettings.VIDEO_MODE_960.equals(str)) {
            hashMap.put(MistatsConstants.Setting.PARAM_960_WATERMARK_STATUS, String.valueOf(z));
        }
        MistatsWrapper.mistatEvent(MistatsConstants.VideoAttr.KEY_NEW_SLOW_MOTION, hashMap);
    }

    public static void trackPauseVideoRecording(boolean z) {
        HashMap hashMap = new HashMap();
        hashMap.put(MistatsConstants.BaseEvent.SENSOR_ID, z ? "front" : MistatsConstants.BaseEvent.BACK);
        hashMap.put(MistatsConstants.BaseEvent.LIFE_STATE, MistatsConstants.VideoAttr.VALUE_VIDEO_PAUSE_RECORDING);
        MistatsWrapper.mistatEvent(MistatsConstants.VideoAttr.KEY_VIDEO_COMMON_CLICK, hashMap);
    }

    public static void trackPictureSize(int i, String str) {
        MistatsWrapper.commonKeyTriggerEvent(MistatsConstants.BaseEvent.PARAM_PICTURE_RATIO, str, null);
    }

    public static void trackPictureTakenInManual(int i, String str, String str2, String str3, String str4, int i2, int i3) {
        HashMap hashMap = new HashMap();
        hashMap.put(MistatsConstants.BaseEvent.MODE, MistatsConstants.BaseEvent.PHOTO);
        hashMap.put(MistatsConstants.Manual.PARAM_EV, str4);
        hashMap.put("attr_awb", autoWhiteBalanceToName(str));
        hashMap.put(MistatsConstants.Manual.PARAM_FOCUS_POSITION, focusPositionToName(CameraSettings.getFocusPosition()));
        hashMap.put(MistatsConstants.Manual.PARAM_ET, exposureTimeToName(str2));
        hashMap.put("attr_iso", isoToName(str3));
        hashMap.put(MistatsConstants.Manual.PARAM_LENS, CameraSettings.getCameraLensType(i2));
        String str5 = "on";
        hashMap.put(MistatsConstants.Manual.PARAM_FOCUS_PEAK, EffectController.getInstance().isNeedDrawPeaking() ? str5 : "off");
        hashMap.put(MistatsConstants.Manual.PARAM_EXPOSURE_FEEDBACk, EffectController.getInstance().isNeedDrawExposure() ? str5 : "off");
        hashMap.put("attr_reference_line", String.valueOf(DataRepository.dataItemGlobal().getBoolean("pref_camera_referenceline_key", false)));
        if (!CameraSettings.isGradienterOn()) {
            str5 = "off";
        }
        hashMap.put(MistatsConstants.Setting.PARAM_GRADIENTER, str5);
        hashMap.put(MistatsConstants.Zoom.PARAM_ZOOM_RATIO, HybridZoomingSystem.toString(CameraSettings.readZoom()));
        hashMap.put(MistatsConstants.Manual.PARAM_RAW, String.valueOf(DataRepository.dataItemConfig().getComponentConfigRaw().isSwitchOn(167)));
        hashMap.put(MistatsConstants.Manual.PARAM_AUTOEXPOSURE, DataRepository.dataItemConfig().getComponentConfigMeter().getTrackValue(167));
        hashMap.put(MistatsConstants.BaseEvent.COUNT, String.valueOf(i));
        trackSatState(i3, i2, hashMap);
        addUltraPixelParameter(hashMap);
        hashMap.put("attr_filter", filterIdToName(EffectController.getInstance().getEffectForSaving(false)));
        hashMap.put(MistatsConstants.Manual.PARAM_GRADIENT, String.valueOf(CameraSettings.isGradienterOn()));
        addUltraPixelParameter(false, hashMap);
        MistatsWrapper.moduleCaptureEvent(MistatsConstants.ModuleName.MANUAL, hashMap);
    }

    public static void trackPictureTakenInPanorama(Map map, Context context, BeautyValues beautyValues, int i) {
        if (map == null) {
            map = new HashMap();
        }
        if (beautyValues != null) {
            map.put(MistatsConstants.BeautyAttr.PARAM_BEAUTY_LEVEL, Integer.valueOf(beautyValues.mBeautySkinSmooth));
        }
        map.put(MistatsConstants.Panorama.PARAM_PANORAMA_DIRECTION, 3 == CameraSettings.getPanoramaMoveDirection(context) ? MistatsConstants.Panorama.PANORAMA_DIRECTION_L2R : MistatsConstants.Panorama.PANORAMA_DIRECTION_R2L);
        map.put(MistatsConstants.BaseEvent.COUNT, String.valueOf(i));
        map.put(MistatsConstants.BaseEvent.MODE, MistatsConstants.BaseEvent.PHOTO);
        MistatsWrapper.mistatEvent(MistatsConstants.ModuleName.PANORAMA, map);
    }

    public static void trackPictureTakenInWideSelfie(String str, BeautyValues beautyValues) {
        HashMap hashMap = new HashMap();
        hashMap.put(MistatsConstants.Panorama.PARAM_STOP_CAPTURE_MODE, str);
        if (beautyValues != null) {
            hashMap.put(MistatsConstants.BeautyAttr.PARAM_BEAUTY_LEVEL, String.valueOf(beautyValues.mBeautySkinSmooth));
        }
        hashMap.put(MistatsConstants.BaseEvent.MODE, MistatsConstants.BaseEvent.PHOTO);
        MistatsWrapper.mistatEvent(MistatsConstants.ModuleName.PANORAMA, hashMap);
    }

    public static void trackPocketModeEnter(String str) {
        HashMap hashMap = new HashMap();
        hashMap.put(MistatsConstants.BaseEvent.OPERATE_STATE, MistatsConstants.NonUI.VALUE_POCKET_MODE_ENTER);
        MistatsWrapper.mistatEvent(MistatsConstants.NonUI.KEY_ENTER_FAULT, hashMap);
    }

    public static void trackPocketModeExit(String str) {
        HashMap hashMap = new HashMap();
        hashMap.put(MistatsConstants.BaseEvent.OPERATE_STATE, str);
        MistatsWrapper.mistatEvent(MistatsConstants.NonUI.KEY_POCKET_MODE_KEYGUARD_EXIT, hashMap);
    }

    public static void trackPocketModeSensorDelay() {
        HashMap hashMap = new HashMap();
        hashMap.put(MistatsConstants.BaseEvent.COUNT, "1");
        MistatsWrapper.mistatEvent(MistatsConstants.NonUI.KEY_POCKET_MODE_SENSOR_DELAY, hashMap);
    }

    public static void trackRecordVideoInProMode(String str, String str2, String str3, String str4, int i, int i2) {
        HashMap hashMap = new HashMap();
        hashMap.put(MistatsConstants.BaseEvent.MODE, "video");
        hashMap.put(MistatsConstants.Manual.PARAM_EV, str4);
        hashMap.put("attr_awb", autoWhiteBalanceToName(str));
        hashMap.put(MistatsConstants.Manual.PARAM_FOCUS_POSITION, focusPositionToName(CameraSettings.getFocusPosition()));
        hashMap.put(MistatsConstants.Manual.PARAM_ET, exposureTimeToName(str2));
        hashMap.put("attr_iso", isoToName(str3));
        hashMap.put(MistatsConstants.Manual.PARAM_LENS, CameraSettings.getCameraLensType(i));
        String str5 = "on";
        hashMap.put(MistatsConstants.Manual.PARAM_FOCUS_PEAK, EffectController.getInstance().isNeedDrawPeaking() ? str5 : "off");
        hashMap.put(MistatsConstants.Manual.PARAM_EXPOSURE_FEEDBACk, EffectController.getInstance().isNeedDrawExposure() ? str5 : "off");
        hashMap.put("attr_reference_line", String.valueOf(DataRepository.dataItemGlobal().getBoolean("pref_camera_referenceline_key", false)));
        hashMap.put(MistatsConstants.Setting.PARAM_GRADIENTER, CameraSettings.isGradienterOn() ? str5 : "off");
        hashMap.put(MistatsConstants.Zoom.PARAM_ZOOM_RATIO, HybridZoomingSystem.toString(CameraSettings.readZoom()));
        hashMap.put(MistatsConstants.Manual.PARAM_AUTOEXPOSURE, DataRepository.dataItemConfig().getComponentConfigMeter().getTrackValue(180));
        trackSatState(i2, i, hashMap);
        addUltraPixelParameter(hashMap);
        hashMap.put("attr_filter", filterIdToName(EffectController.getInstance().getEffectForSaving(false)));
        hashMap.put(MistatsConstants.Manual.PARAM_GRADIENT, String.valueOf(CameraSettings.isGradienterOn()));
        addUltraPixelParameter(false, hashMap);
        hashMap.put(MistatsConstants.Manual.PARAM_LOG, CameraSettings.isProVideoLogOpen(i) ? str5 : "off");
        if (!CameraSettings.isProVideoHistogramOpen(i)) {
            str5 = "off";
        }
        hashMap.put(MistatsConstants.Manual.PARAM_HISTOGRAM, str5);
        MistatsWrapper.moduleCaptureEvent(MistatsConstants.ModuleName.PROVIDEO, hashMap);
    }

    private static void trackSatState(int i, int i2, Map map) {
        if (HybridZoomingSystem.IS_3_OR_MORE_SAT) {
            map.put(MistatsConstants.SensorAttr.PARAM_SAT_ZOOM, getDualZoomName(i2));
        } else if (i == Camera2DataContainer.getInstance().getUltraWideCameraId()) {
            map.put(MistatsConstants.SensorAttr.PARAM_SAT_ZOOM, i + "_RearUltra");
        } else if (i == 22) {
            map.put(MistatsConstants.SensorAttr.PARAM_SAT_ZOOM, i + "_RearMacro");
        } else if (i == 20) {
            map.put(MistatsConstants.SensorAttr.PARAM_SAT_ZOOM, i + MistatsConstants.SensorAttr.VALUE_SENSOR_TYPE_REAR_TELE);
        } else if (i == 23) {
            map.put(MistatsConstants.SensorAttr.PARAM_SAT_ZOOM, i + "_RearTele4x");
        } else if (i == Camera2DataContainer.getInstance().getMainBackCameraId()) {
            map.put(MistatsConstants.SensorAttr.PARAM_SAT_ZOOM, i + "_RearWide");
        }
    }

    public static void trackSelectObject(boolean z) {
        HashMap hashMap = new HashMap();
        hashMap.put(MistatsConstants.AutoZoom.PARAM_SELECT_OBJECT_STATE, z ? MistatsConstants.AutoZoom.VALUE_IN_RECORDING : MistatsConstants.AutoZoom.VALUE_BEFORE_RECORDING);
        MistatsWrapper.mistatEvent(MistatsConstants.AutoZoom.KEY_AUTO_ZOOM, hashMap);
    }

    public static void trackShowZoomBarByScroll(boolean z) {
        if (z) {
            HashMap hashMap = new HashMap();
            hashMap.put(MistatsConstants.Zoom.PARAM_ZOOM_ADJUSTED_MODE, MistatsConstants.Zoom.VALUE_SHOW_ZOOM_BAR_BY_SCROLL);
            MistatsWrapper.mistatEvent(MistatsConstants.Zoom.KEY_ZOOM, hashMap);
        }
    }

    public static void trackSlowMotionQuality(String str, String str2) {
        HashMap hashMap = new HashMap();
        hashMap.put(MistatsConstants.VideoAttr.PARAM_VIDEO_FPS, str);
        hashMap.put(MistatsConstants.VideoAttr.PARAM_VIDEO_QUALITY, slowMotionQualityIdToName(str2));
        MistatsWrapper.mistatEvent(MistatsConstants.VideoAttr.KEY_NEW_SLOW_MOTION, hashMap);
    }

    public static void trackSnapInfo(boolean z) {
        HashMap hashMap = new HashMap();
        hashMap.put(MistatsConstants.BaseEvent.MODULE_NAME, MistatsWrapper.getStatsModuleKey(DataRepository.dataItemGlobal().getCurrentMode()));
        MistatsWrapper.mistatEventSimple(MistatsConstants.CaptureSence.KEY_SNAP_CAMERA, hashMap);
    }

    public static void trackStartAppCost(long j) {
        if (j < 0 || j > FunctionParseBeautyBodySlimCount.TIP_INTERVAL_TIME) {
            throw new IllegalArgumentException("The time cost when start app is illegal: " + j);
        }
        HashMap hashMap = new HashMap();
        hashMap.put(MistatsConstants.BaseEvent.FEATURE_NAME, MistatsConstants.CostTime.START_APP_COST);
        hashMap.put(MistatsConstants.BaseEvent.COST_TIME, String.valueOf(round(j, 50)));
        MistatsWrapper.mistatEvent(MistatsConstants.CostTime.KEY_CAMERA_PERPORMANCE, hashMap);
    }

    public static void trackSubtitle(boolean z) {
        String str = z ? "on" : "off";
        HashMap hashMap = new HashMap();
        hashMap.put(MistatsConstants.FeatureName.VALUE_SUBTITLE, str);
        MistatsWrapper.mistatEvent(MistatsConstants.VideoAttr.KEY_VIDEO_COMMON_CLICK, hashMap);
    }

    public static void trackSubtitleRecordingStart() {
        HashMap hashMap = new HashMap();
        hashMap.put(MistatsConstants.FeatureName.VALUE_SUBTITLE, MistatsConstants.FeatureName.VALUE_SUBTITLE_START_RECIRDING);
        MistatsWrapper.mistatEvent(MistatsConstants.VideoAttr.KEY_VIDEO_COMMON_CLICK, hashMap);
    }

    public static void trackSuperNightInCaptureMode(Map map, boolean z) {
        if (DataRepository.dataItemFeature().Rd()) {
            if (map == null) {
                map = new HashMap();
            }
            map.put(MistatsConstants.MoonAndNightAttr.PARAM_SURPER_NIGHT, z ? "on" : "off");
        }
    }

    public static void trackTakePictureCost(long j, boolean z, int i) {
        HashMap hashMap = new HashMap();
        hashMap.put(MistatsConstants.BaseEvent.FEATURE_NAME, MistatsConstants.CostTime.TAKE_PICTURE_COST);
        hashMap.put(MistatsConstants.BaseEvent.COST_TIME, String.valueOf(round(j, 50)));
        MistatsWrapper.mistatEvent(MistatsConstants.CostTime.KEY_CAMERA_PERPORMANCE, hashMap);
    }

    public static void trackTiltShiftChanged(String str) {
        if (!MistatsWrapper.isCounterEventDisabled()) {
            MistatsWrapper.commonKeyTriggerEvent(MistatsConstants.FeatureName.VALUE_TILTSHIFT, str, null);
        }
    }

    public static void trackTimerChanged(String str) {
        if (!MistatsWrapper.isCounterEventDisabled()) {
            MistatsWrapper.commonKeyTriggerEvent(MistatsConstants.CaptureAttr.PARAM_TIMER_CHANGED, str, null);
        }
    }

    public static void trackTriggerSubtitle() {
        HashMap hashMap = new HashMap();
        hashMap.put(MistatsConstants.FeatureName.VALUE_SUBTITLE, MistatsConstants.FeatureName.VALUE_TRIGGER_SUBTITLE);
        MistatsWrapper.mistatEvent(MistatsConstants.VideoAttr.KEY_VIDEO_COMMON_CLICK, hashMap);
    }

    public static void trackUserDefineWatermark() {
        MistatsWrapper.settingClickEvent(MistatsConstants.Setting.PARAM_USERDEFINE_WATERMARK, 1);
    }

    public static void trackVVClick(String str) {
        if (!MistatsWrapper.isCounterEventDisabled()) {
            HashMap hashMap = new HashMap();
            hashMap.put(MistatsConstants.BaseEvent.OPERATE_STATE, str);
            MistatsWrapper.mistatEvent(MistatsConstants.VLogAttr.KEY_VLOG, hashMap);
        }
    }

    public static void trackVVRecordingParams(String str, boolean z) {
        if (!MistatsWrapper.isCounterEventDisabled()) {
            HashMap hashMap = new HashMap();
            hashMap.put(MistatsConstants.VLogAttr.PARAM_VV_TEMPLATE_NAME, str);
            MistatsWrapper.mistatEvent(MistatsConstants.VLogAttr.KEY_VLOG, hashMap);
        }
    }

    public static void trackVVStartClick(String str, boolean z) {
        HashMap hashMap = new HashMap();
        hashMap.put(MistatsConstants.VLogAttr.PARAM_VV_TEMPLATE_NAME, str);
        hashMap.put(MistatsConstants.VLogAttr.PARAM_VV_CLICK_TEMPLATE_PREVIEW, z ? MistatsConstants.BaseEvent.VALUE_TRUE : MistatsConstants.BaseEvent.VALUE_FALSE);
        MistatsWrapper.mistatEvent(MistatsConstants.VLogAttr.KEY_VLOG, hashMap);
    }

    public static void trackVVTemplateThumbnailClick(String str) {
        HashMap hashMap = new HashMap();
        hashMap.put(MistatsConstants.VLogAttr.PARAM_VV_TEMPLATE_NAME, str);
        MistatsWrapper.mistatEvent(MistatsConstants.VLogAttr.KEY_VLOG, hashMap);
    }

    public static void trackVideoModeChanged(String str) {
        if (!MistatsWrapper.isCounterEventDisabled()) {
            HashMap hashMap = new HashMap();
            hashMap.put(MistatsConstants.VideoAttr.PARAM_VIDEO_MODE, str);
            MistatsWrapper.mistatEvent(MistatsConstants.VideoAttr.KEY_VIDEO_COMMON_CLICK, hashMap);
        }
    }

    public static void trackVideoQuality(String str, boolean z, String str2) {
        HashMap hashMap = new HashMap();
        hashMap.put(MistatsConstants.VideoAttr.PARAM_VIDEO_QUALITY, videoQualityToName(str2));
        MistatsWrapper.mistatEvent(MistatsConstants.BaseEvent.KEY_VIDEO, hashMap);
    }

    public static void trackVideoRecorded(boolean z, int i, int i2, boolean z2, boolean z3, boolean z4, String str, int i3, int i4, int i5, int i6, BeautyValues beautyValues, long j, boolean z5) {
        HashMap hashMap = new HashMap();
        hashMap.put(MistatsConstants.BaseEvent.SENSOR_ID, z ? "front" : MistatsConstants.BaseEvent.BACK);
        hashMap.put(MistatsConstants.VideoAttr.PARAM_VIDEO_MODE, str);
        hashMap.put(MistatsConstants.BaseEvent.QUALITY, videoQualityToName("" + i3));
        String str2 = "off";
        hashMap.put(MistatsConstants.FlashAttr.PARAM_FLASH_MODE, i4 == 2 ? "torch" : str2);
        trackSatState(i, i2, hashMap);
        hashMap.put(MistatsConstants.VideoAttr.PARAM_VIDEO_FPS, String.valueOf(i5));
        if (i2 == 162 && !z) {
            if (z2) {
                hashMap.put(MistatsConstants.AutoZoom.AUTO_ZOOM_STATE, z4 ? MistatsConstants.AutoZoom.VALUE_AUTOZOOM_ULTRA : MistatsConstants.AutoZoom.VALUE_AUTOZOOM_NOT_ULTRA);
            } else {
                hashMap.put(MistatsConstants.AutoZoom.AUTO_ZOOM_STATE, str2);
            }
            hashMap.put(MistatsConstants.VideoAttr.PARAM_SUPER_EIS, z3 ? "on" : str2);
        }
        if (beautyValues != null) {
            hashMap.put(MistatsConstants.BeautyAttr.PARAM_BEAUTY_LEVEL, String.valueOf(beautyValues.mBeautySkinSmooth));
        }
        hashMap.put(MistatsConstants.VideoAttr.PARAM_VIDEO_TIME, String.valueOf(j));
        if (z5) {
            hashMap.put(MistatsConstants.FeatureName.VALUE_SUBTITLE_RECORDING, "on");
        } else {
            hashMap.put(MistatsConstants.FeatureName.VALUE_SUBTITLE_RECORDING, str2);
        }
        if (i2 == 180 || i2 == 162) {
            if (CameraSettings.isCinematicAspectRatioEnabled(i2)) {
                str2 = "on";
            }
            hashMap.put(MistatsConstants.VideoAttr.PARAM_CINEMATIC_RATIO, str2);
            hashMap.put(MistatsConstants.VideoAttr.PARAM_BEAUTY, String.valueOf(CameraSettings.getFaceBeautyRatio("pref_beautify_skin_smooth_ratio_key")));
            hashMap.put("attr_filter", String.valueOf(CameraSettings.getShaderEffect()));
            hashMap.put(MistatsConstants.VideoAttr.PARAM_BOKEH, String.valueOf(CameraSettings.getVideoBokehRatio()));
        }
        MistatsWrapper.mistatEvent(MistatsConstants.BaseEvent.KEY_VIDEO, hashMap);
        if (i6 > 0 && CameraSettings.VIDEO_SPEED_FAST.equals(str)) {
            HashMap hashMap2 = new HashMap();
            hashMap2.put(MistatsConstants.VideoAttr.PARAM_VIDEO_TIME_LAPSE_INTERVAL, timeLapseIntervalToName(i6));
            MistatsWrapper.mistatEvent(MistatsConstants.VideoAttr.KEY_VIDEO_QUICK, hashMap2);
        }
        trackMacroModeTaken(i2);
    }

    public static void trackVideoSnapshot(boolean z) {
        HashMap hashMap = new HashMap();
        DataRepository.dataItemGlobal().getCurrentMode();
        hashMap.put(MistatsConstants.VideoAttr.PARAM_VIDEO_SNAPSHOT_COUNT, String.valueOf(1));
        MistatsWrapper.mistatEvent(MistatsConstants.VideoAttr.KEY_VIDEO_COMMON_CLICK, hashMap);
    }

    public static void trackWithoutNetwork() {
        HashMap hashMap = new HashMap();
        hashMap.put(MistatsConstants.FeatureName.VALUE_SUBTITLE, MistatsConstants.FeatureName.VALUE_SUBTITLE_START_NO_NETWORK);
        MistatsWrapper.mistatEvent(MistatsConstants.VideoAttr.KEY_VIDEO_COMMON_CLICK, hashMap);
    }

    public static void trackZoomAdjusted(String str, boolean z) {
        HashMap hashMap = new HashMap();
        hashMap.put(MistatsConstants.Zoom.PARAM_ZOOM_ADJUSTED_MODE, str);
        hashMap.put(MistatsConstants.Zoom.PARAM_ZOOM_IN_RECORDING, z ? "on" : "off");
        MistatsWrapper.mistatEvent(MistatsConstants.Zoom.KEY_ZOOM, hashMap);
    }

    public static String triggerModeToName(int i) {
        return sTriggerModeIdToName.get(i);
    }

    private static String videoQualityToName(String str) {
        if (String.valueOf(8).equals(str)) {
            return MistatsConstants.BaseEvent.QUALITY_2160P;
        }
        if (String.valueOf(6).equals(str)) {
            return MistatsConstants.BaseEvent.QUALITY_1080P;
        }
        if (String.valueOf(5).equals(str)) {
            return MistatsConstants.BaseEvent.QUALITY_720P;
        }
        if (String.valueOf(4).equals(str)) {
            return MistatsConstants.BaseEvent.QUALITY_480P;
        }
        if (ComponentConfigVideoQuality.QUALITY_4K_60FPS.equals(str)) {
            return MistatsConstants.BaseEvent.QUALITY_4K_60FPS;
        }
        if (ComponentConfigVideoQuality.QUALITY_1080P_60FPS.equals(str)) {
            return MistatsConstants.BaseEvent.QUALITY_1080P_60FPS;
        }
        if (ComponentConfigVideoQuality.QUALITY_8K.equals(str)) {
            return MistatsConstants.BaseEvent.QUALITY_8K_30FPS;
        }
        String str2 = TAG;
        Log.e(str2, "unexpected video quality: " + str);
        return MistatsConstants.BaseEvent.OTHERS;
    }
}
