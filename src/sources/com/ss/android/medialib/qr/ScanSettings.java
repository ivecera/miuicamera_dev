package com.ss.android.medialib.qr;

import android.support.annotation.Keep;

@Keep
public class ScanSettings {
    public int buildChainFlag = 1;
    public String detectModelDir;
    public long detectRequirement = 65536;
    public int height = -1;
    public int width = -1;

    public interface Requirement {
        public static final long REQUIREMENT_2HAND = -9223372036854774784L;
        public static final long REQUIREMENT_2HAND_KEYPOINT = 4611686018427388928L;
        public static final long REQUIREMENT_AR_PLANE = 65536;
        public static final long REQUIREMENT_BODY = 16;
        public static final long REQUIREMENT_ENIGMA_DETECT = 65536;
        public static final long REQUIREMENT_EXPRESSION_DETECT = 4096;
        public static final long REQUIREMENT_FACE_240 = 256;
        public static final long REQUIREMENT_FACE_3D_DETECT = 8192;
        public static final long REQUIREMENT_FACE_CAT_DETECT = 128;
        public static final long REQUIREMENT_FACE_DETECT = 1;
        public static final long REQUIREMENT_FACE_TRACK = 32;
        public static final long REQUIREMENT_HAIR = 4;
        public static final long REQUIREMENT_HAND = 1152921504606848000L;
        public static final long REQUIREMENT_HAND_BASE = 1024;
        public static final long REQUIREMENT_HAND_KEYPOINT = 2305843009213694976L;
        public static final long REQUIREMENT_JOINT = 64;
        public static final long REQUIREMENT_MATTING = 2;
        public static final long REQUIREMENT_NONE = 0;
        public static final long REQUIREMENT_SKELETON2 = 2048;
        public static final long REQUIREMENT_SKELETON_HUAWEI = 32768;
        public static final long REQUIREMENT_SKY_SEG = 16384;
        public static final long REQUIREMENT_SLAM = 8;
    }

    public int getBuildChainFlag() {
        return this.buildChainFlag;
    }

    public String getDetectModelDir() {
        return this.detectModelDir;
    }

    public long getDetectRequirement() {
        return this.detectRequirement;
    }

    public int getHeight() {
        return this.height;
    }

    public int getWidth() {
        return this.width;
    }
}
