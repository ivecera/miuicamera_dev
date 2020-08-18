package com.xiaomi.camera.base;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class Constants {

    @Retention(RetentionPolicy.SOURCE)
    public @interface AlgoType {
        public static final int CLEAR_SHOT = 2;
        public static final int GROUP_SHOT = 5;
        public static final int HDR = 1;
        public static final int HHT = 7;
        public static final int LLS = 8;
        public static final int LOW_LIGHT_BOKEH = 9;
        public static final int MULTI_SHOT = 4;
        public static final int QCFA_SHOT = 6;
        public static final int SUPER_RESOLUTION = 3;
        public static final int UNKNOWN = 0;
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface EngineProcessAlgoType {
        public static final int MIA_PROCESS_CLEARSHOT = 1;
        public static final int MIA_PROCESS_HHT = 2;
        public static final int MIA_PROCESS_SR = 0;
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ResultImage {
        public static final int RESULT_IMAGE_DEPTH = 2;
        public static final int RESULT_IMAGE_JPEG_RAW = 1;
        public static final int RESULT_IMAGE_NORMAL = 0;
        public static final int RESULT_IMAGE_SENSOR_RAW = 3;
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface SatMaterCameraId {
        public static final int TELE = 3;
        public static final int ULTRA_TELE = 4;
        public static final int ULTRA_WIDE = 1;
        public static final int WIDE = 2;
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ShotType {
        public static final int BURST_SHOT = 3;
        public static final int INTENT_NORMAL_DUAL_SHOT = -3;
        public static final int INTENT_NORMAL_SINGLE_SHOT = -2;
        public static final int INTENT_PARALLEL_DUAL_SHOT = -7;
        public static final int INTENT_PARALLEL_SINGLE_PORTRAIT = -6;
        public static final int INTENT_PARALLEL_SINGLE_SHOT = -5;
        public static final int MIMOJI_PREVIEW_SHOT = -4;
        public static final int NORMAL_DUAL_SHOT = 2;
        public static final int NORMAL_SINGLE_SHOT = 0;
        public static final int NORMAL_SINGLE_SHOT_JPEG_RAW = 1;
        public static final int PARALLEL_BURST = 8;
        public static final int PARALLEL_DUAL_PORTRAIT = 6;
        public static final int PARALLEL_DUAL_PORTRAIT_BURST = 11;
        public static final int PARALLEL_REPEATING = 9;
        public static final int PARALLEL_SINGLE = 5;
        public static final int PARALLEL_SINGLE_PORTRAIT = 7;
        public static final int PREVIEW_SHOT = -1;
        public static final int SIMPLE_PREVIEW_SHOT = -8;
        public static final int SUPER_NIGHT_RAW_BURST = 10;
        public static final int VIDEO_SHOT = 4;
    }

    public static boolean isIntentType(int i) {
        return i == -7 || i == -6 || i == -5 || i == -3 || i == -2;
    }
}
