package com.xiaomi.camera.liveshot;

import android.media.MediaCodecInfo;
import android.media.MediaCodecList;
import android.media.MediaFormat;
import com.android.camera.log.Log;

public class MediaCodecCapability {
    public static final String HW_AUDIO_CODEC_AAC = "OMX.qcom.audio.encoder.aac";
    public static final String HW_VIDEO_CODEC_HEVC = "OMX.qcom.video.encoder.hevc";
    private static final String TAG = "MediaCodecCapability";
    private static MediaCodecList sRegularCodecList;

    private static MediaCodecList getRegularCodecList() {
        if (sRegularCodecList == null) {
            sRegularCodecList = new MediaCodecList(0);
        }
        return sRegularCodecList;
    }

    public static boolean isFormatSupported(MediaFormat mediaFormat, String str) {
        MediaCodecInfo[] codecInfos = getRegularCodecList().getCodecInfos();
        int i = 0;
        boolean z = false;
        while (i < codecInfos.length && !z) {
            String[] supportedTypes = codecInfos[i].getSupportedTypes();
            boolean z2 = z;
            for (int i2 = 0; i2 < supportedTypes.length && !z2; i2++) {
                if (supportedTypes[i2].equals(str)) {
                    z2 = codecInfos[i].getCapabilitiesForType(str).isFormatSupported(mediaFormat);
                }
            }
            i++;
            z = z2;
        }
        Log.d(TAG, "isFormatSupported(): format = " + mediaFormat + ", mimeType = " + str + ", supported = " + z);
        return z;
    }

    public static boolean isH265EncodingSupported() {
        MediaCodecInfo[] codecInfos = getRegularCodecList().getCodecInfos();
        boolean z = false;
        for (int i = 0; i < codecInfos.length && !z; i++) {
            MediaCodecInfo mediaCodecInfo = codecInfos[i];
            Log.d(TAG, "codec.name = " + mediaCodecInfo.getName());
            if (mediaCodecInfo.getName().equals(HW_VIDEO_CODEC_HEVC)) {
                z = true;
            }
        }
        Log.d(TAG, "isH265EncodingSupported(): " + z);
        return z;
    }
}
