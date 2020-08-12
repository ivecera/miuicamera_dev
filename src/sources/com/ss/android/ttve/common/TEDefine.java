package com.ss.android.ttve.common;

public class TEDefine {
    public static final String FACE_BEAUTY_NULL = "null";

    public interface TEFileType {
        public static final int Audio = 2;
        public static final int AudioVideo = 0;
        public static final int Video = 1;
    }

    public class TEFilter {
        public static final int TEFilterType_Audio = 1;
        public static final int TEFilterType_Caption = 4;
        public static final int TEFilterType_Color = 2;
        public static final int TEFilterType_Effect_Color = 7;
        public static final int TEFilterType_Effect_Filter = 8;
        public static final int TEFilterType_Info_Sticker = 9;
        public static final int TEFilterType_MV = 11;
        public static final int TEFilterType_Music_Srt_Effect_filter = 10;
        public static final int TEFilterType_Sticker = 0;
        public static final int TEFilterType_TimeEffect = 6;
        public static final int TEFilterType_Transform = 3;
        public static final int TEFilterType_Unknown = -1;
        public static final int TEFilterType_WaterMark = 5;
        public static final String TRANSFORM2D = "transform_2d";

        public TEFilter() {
        }
    }

    public interface TETransCodeLevel {
        public static final int ReEncode = 1;
        public static final int ReMux = 0;
    }

    public class TETransition {
        public static final String BLACK = "black";
        public static final String BLEND = "blend";
        public static final String FADE = "fade";
        public static final String WHITE = "white";

        public TETransition() {
        }
    }
}
