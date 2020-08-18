package com.android.camera.fragment.subtitle.recog.record;

public class VolumeUtil {
    private static final int VOLUME_LEVEL_0 = 329;
    private static final int VOLUME_LEVEL_1 = 421;
    private static final int VOLUME_LEVEL_10 = 4011;
    private static final int VOLUME_LEVEL_11 = 5142;
    private static final int VOLUME_LEVEL_12 = 6612;
    private static final int VOLUME_LEVEL_13 = 8478;
    private static final int VOLUME_LEVEL_14 = 10900;
    private static final int VOLUME_LEVEL_15 = 13982;
    private static final int VOLUME_LEVEL_16 = 17968;
    private static final int VOLUME_LEVEL_17 = 23054;
    private static final int VOLUME_LEVEL_18 = 29620;
    private static final int VOLUME_LEVEL_19 = 38014;
    private static final int VOLUME_LEVEL_2 = 543;
    private static final int VOLUME_LEVEL_20 = 48828;
    private static final int VOLUME_LEVEL_21 = 62654;
    private static final int VOLUME_LEVEL_22 = 80491;
    private static final int VOLUME_LEVEL_23 = 103294;
    private static final int VOLUME_LEVEL_24 = 132686;
    private static final int VOLUME_LEVEL_25 = 170366;
    private static final int VOLUME_LEVEL_26 = 218728;
    private static final int VOLUME_LEVEL_27 = 280830;
    private static final int VOLUME_LEVEL_3 = 694;
    private static final int VOLUME_LEVEL_4 = 895;
    private static final int VOLUME_LEVEL_5 = 1146;
    private static final int VOLUME_LEVEL_6 = 1476;
    private static final int VOLUME_LEVEL_7 = 1890;
    private static final int VOLUME_LEVEL_8 = 2433;
    private static final int VOLUME_LEVEL_9 = 3118;

    public static int computeVolume(byte[] bArr, int i) {
        int i2;
        if (bArr == null || i <= 2) {
            return 0;
        }
        int i3 = i / 2;
        long j = 0;
        int i4 = 0;
        long j2 = 0;
        while (true) {
            i2 = (i3 * 2) - 1;
            if (i4 >= i2) {
                break;
            }
            j2 += (long) (bArr[i4] + (bArr[i4 + 1] * 256));
            i4 += 2;
        }
        long j3 = (long) i3;
        long j4 = j2 / j3;
        for (int i5 = 0; i5 < i2; i5 += 2) {
            int i6 = (int) (((long) (bArr[i5] + (bArr[i5 + 1] * 256))) - j4);
            j += (long) ((i6 * i6) >> 9);
        }
        long j5 = j / j3;
        if (j5 < 329) {
            return 0;
        }
        if (j5 < 421) {
            return 1;
        }
        if (j5 < 543) {
            return 2;
        }
        if (j5 < 694) {
            return 3;
        }
        if (j5 < 895) {
            return 4;
        }
        if (j5 < 1146) {
            return 5;
        }
        if (j5 < 1476) {
            return 6;
        }
        if (j5 < 1890) {
            return 7;
        }
        if (j5 < 2433) {
            return 8;
        }
        if (j5 < 3118) {
            return 9;
        }
        if (j5 < 4011) {
            return 10;
        }
        if (j5 < 5142) {
            return 11;
        }
        if (j5 < 6612) {
            return 12;
        }
        if (j5 < 8478) {
            return 13;
        }
        if (j5 < 10900) {
            return 14;
        }
        if (j5 < 13982) {
            return 15;
        }
        if (j5 < 17968) {
            return 16;
        }
        if (j5 < 23054) {
            return 17;
        }
        if (j5 < 29620) {
            return 18;
        }
        if (j5 < 38014) {
            return 19;
        }
        if (j5 < 48828) {
            return 20;
        }
        if (j5 < 62654) {
            return 21;
        }
        if (j5 < 80491) {
            return 22;
        }
        if (j5 < 103294) {
            return 23;
        }
        if (j5 < 132686) {
            return 24;
        }
        if (j5 < 170366) {
            return 25;
        }
        if (j5 < 218728) {
            return 26;
        }
        return j5 < 280830 ? 27 : 30;
    }
}
