package com.google.zxing.pdf417.decoder;

import com.google.zxing.common.detector.MathUtils;
import com.google.zxing.pdf417.PDF417Common;
import java.lang.reflect.Array;

final class PDF417CodewordDecoder {
    private static final float[][] RATIOS_TABLE = ((float[][]) Array.newInstance(float.class, PDF417Common.SYMBOL_TABLE.length, 8));

    static {
        int i;
        int i2 = 0;
        while (true) {
            int[] iArr = PDF417Common.SYMBOL_TABLE;
            if (i2 < iArr.length) {
                int i3 = iArr[i2];
                int i4 = i3 & 1;
                int i5 = i3;
                int i6 = 0;
                while (i6 < 8) {
                    float f2 = 0.0f;
                    while (true) {
                        i = i5 & 1;
                        if (i != i4) {
                            break;
                        }
                        f2 += 1.0f;
                        i5 >>= 1;
                    }
                    RATIOS_TABLE[i2][(8 - i6) - 1] = f2 / 17.0f;
                    i6++;
                    i4 = i;
                }
                i2++;
            } else {
                return;
            }
        }
    }

    private PDF417CodewordDecoder() {
    }

    private static int getBitValue(int[] iArr) {
        long j = 0;
        int i = 0;
        while (i < iArr.length) {
            long j2 = j;
            for (int i2 = 0; i2 < iArr[i]; i2++) {
                int i3 = 1;
                long j3 = j2 << 1;
                if (i % 2 != 0) {
                    i3 = 0;
                }
                j2 = j3 | ((long) i3);
            }
            i++;
            j = j2;
        }
        return (int) j;
    }

    private static int getClosestDecodedValue(int[] iArr) {
        int sum = MathUtils.sum(iArr);
        float[] fArr = new float[8];
        if (sum > 1) {
            for (int i = 0; i < 8; i++) {
                fArr[i] = ((float) iArr[i]) / ((float) sum);
            }
        }
        int i2 = -1;
        float f2 = Float.MAX_VALUE;
        int i3 = 0;
        while (true) {
            float[][] fArr2 = RATIOS_TABLE;
            if (i3 >= fArr2.length) {
                return i2;
            }
            float[] fArr3 = fArr2[i3];
            float f3 = 0.0f;
            for (int i4 = 0; i4 < 8; i4++) {
                float f4 = fArr3[i4] - fArr[i4];
                f3 += f4 * f4;
                if (f3 >= f2) {
                    break;
                }
            }
            if (f3 < f2) {
                i2 = PDF417Common.SYMBOL_TABLE[i3];
                f2 = f3;
            }
            i3++;
        }
    }

    private static int getDecodedCodewordValue(int[] iArr) {
        int bitValue = getBitValue(iArr);
        if (PDF417Common.getCodeword(bitValue) == -1) {
            return -1;
        }
        return bitValue;
    }

    static int getDecodedValue(int[] iArr) {
        int decodedCodewordValue = getDecodedCodewordValue(sampleBitCounts(iArr));
        return decodedCodewordValue != -1 ? decodedCodewordValue : getClosestDecodedValue(iArr);
    }

    private static int[] sampleBitCounts(int[] iArr) {
        float sum = (float) MathUtils.sum(iArr);
        int[] iArr2 = new int[8];
        int i = 0;
        int i2 = 0;
        for (int i3 = 0; i3 < 17; i3++) {
            if (((float) (iArr[i2] + i)) <= (sum / 34.0f) + ((((float) i3) * sum) / 17.0f)) {
                i += iArr[i2];
                i2++;
            }
            iArr2[i2] = iArr2[i2] + 1;
        }
        return iArr2;
    }
}
