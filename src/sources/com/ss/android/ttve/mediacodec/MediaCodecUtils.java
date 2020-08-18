package com.ss.android.ttve.mediacodec;

import android.media.MediaCodecInfo;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;

class MediaCodecUtils {
    MediaCodecUtils() {
    }

    @RequiresApi(api = 18)
    @Nullable
    static MediaCodecInfo.CodecProfileLevel findBestMatchedProfile(@NonNull MediaCodecInfo.CodecCapabilities codecCapabilities, int i) {
        MediaCodecInfo.CodecProfileLevel[] codecProfileLevelArr = codecCapabilities.profileLevels;
        MediaCodecInfo.CodecProfileLevel codecProfileLevel = null;
        for (MediaCodecInfo.CodecProfileLevel codecProfileLevel2 : codecProfileLevelArr) {
            int i2 = codecProfileLevel2.profile;
            if (i2 == i) {
                return codecProfileLevel2;
            }
            if (codecProfileLevel == null || codecProfileLevel.profile < i2) {
                codecProfileLevel = codecProfileLevel2;
            }
        }
        return codecProfileLevel;
    }
}
