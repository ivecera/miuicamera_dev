package com.google.zxing.common;

import com.google.zxing.Binarizer;
import com.google.zxing.LuminanceSource;
import com.google.zxing.NotFoundException;
import java.lang.reflect.Array;

public final class HybridBinarizer extends GlobalHistogramBinarizer {
    private static final int BLOCK_SIZE = 8;
    private static final int BLOCK_SIZE_MASK = 7;
    private static final int BLOCK_SIZE_POWER = 3;
    private static final int MINIMUM_DIMENSION = 40;
    private static final int MIN_DYNAMIC_RANGE = 24;
    private BitMatrix matrix;

    public HybridBinarizer(LuminanceSource luminanceSource) {
        super(luminanceSource);
    }

    private static int[][] calculateBlackPoints(byte[] bArr, int i, int i2, int i3, int i4) {
        int i5;
        int i6;
        int i7 = 8;
        int i8 = i4 - 8;
        int i9 = i3 - 8;
        int[][] iArr = (int[][]) Array.newInstance(int.class, i2, i);
        for (int i10 = 0; i10 < i2; i10++) {
            int i11 = i10 << 3;
            if (i11 > i8) {
                i11 = i8;
            }
            for (int i12 = 0; i12 < i; i12++) {
                int i13 = i12 << 3;
                if (i13 > i9) {
                    i13 = i9;
                }
                byte b2 = 255;
                int i14 = (i11 * i3) + i13;
                int i15 = 0;
                int i16 = 0;
                byte b3 = 0;
                while (i6 < i7) {
                    int i17 = i16;
                    int i18 = 0;
                    while (i18 < i7) {
                        byte b4 = bArr[i5 + i18] & 255;
                        i17 += b4;
                        if (b4 < b2) {
                            b2 = b4;
                        }
                        if (b4 > b3) {
                            b3 = b4;
                        }
                        i18++;
                        i7 = 8;
                    }
                    if (b3 - b2 > 24) {
                        i6++;
                        i5 += i3;
                        i7 = 8;
                        while (i6 < 8) {
                            for (int i19 = 0; i19 < 8; i19++) {
                                i17 += bArr[i5 + i19] & 255;
                            }
                            i6++;
                            i5 += i3;
                        }
                    } else {
                        i7 = 8;
                    }
                    i16 = i17;
                    i15 = i6 + 1;
                    i14 = i5 + i3;
                }
                int i20 = i16 >> 6;
                if (b3 - b2 <= 24) {
                    i20 = b2 / 2;
                    if (i10 > 0 && i12 > 0) {
                        int i21 = i10 - 1;
                        int i22 = i12 - 1;
                        int i23 = ((iArr[i21][i12] + (iArr[i10][i22] * 2)) + iArr[i21][i22]) / 4;
                        if (b2 < i23) {
                            i20 = i23;
                        }
                    }
                }
                iArr[i10][i12] = i20;
            }
        }
        return iArr;
    }

    private static void calculateThresholdForBlock(byte[] bArr, int i, int i2, int i3, int i4, int[][] iArr, BitMatrix bitMatrix) {
        int i5 = i4 - 8;
        int i6 = i3 - 8;
        for (int i7 = 0; i7 < i2; i7++) {
            int i8 = i7 << 3;
            int i9 = i8 > i5 ? i5 : i8;
            int cap = cap(i7, i2 - 3);
            for (int i10 = 0; i10 < i; i10++) {
                int i11 = i10 << 3;
                int i12 = i11 > i6 ? i6 : i11;
                int cap2 = cap(i10, i - 3);
                int i13 = 0;
                for (int i14 = -2; i14 <= 2; i14++) {
                    int[] iArr2 = iArr[cap + i14];
                    i13 += iArr2[cap2 - 2] + iArr2[cap2 - 1] + iArr2[cap2] + iArr2[cap2 + 1] + iArr2[2 + cap2];
                }
                thresholdBlock(bArr, i12, i9, i13 / 25, i3, bitMatrix);
            }
        }
    }

    private static int cap(int i, int i2) {
        if (i < 2) {
            return 2;
        }
        return i > i2 ? i2 : i;
    }

    private static void thresholdBlock(byte[] bArr, int i, int i2, int i3, int i4, BitMatrix bitMatrix) {
        int i5 = (i2 * i4) + i;
        int i6 = 0;
        while (i6 < 8) {
            for (int i7 = 0; i7 < 8; i7++) {
                if ((bArr[i5 + i7] & 255) <= i3) {
                    bitMatrix.set(i + i7, i2 + i6);
                }
            }
            i6++;
            i5 += i4;
        }
    }

    @Override // com.google.zxing.common.GlobalHistogramBinarizer, com.google.zxing.Binarizer
    public Binarizer createBinarizer(LuminanceSource luminanceSource) {
        return new HybridBinarizer(luminanceSource);
    }

    @Override // com.google.zxing.common.GlobalHistogramBinarizer, com.google.zxing.Binarizer
    public BitMatrix getBlackMatrix() throws NotFoundException {
        BitMatrix bitMatrix = this.matrix;
        if (bitMatrix != null) {
            return bitMatrix;
        }
        LuminanceSource luminanceSource = getLuminanceSource();
        int width = luminanceSource.getWidth();
        int height = luminanceSource.getHeight();
        if (width < 40 || height < 40) {
            this.matrix = super.getBlackMatrix();
        } else {
            byte[] matrix2 = luminanceSource.getMatrix();
            int i = width >> 3;
            if ((width & 7) != 0) {
                i++;
            }
            int i2 = height >> 3;
            if ((height & 7) != 0) {
                i2++;
            }
            int[][] calculateBlackPoints = calculateBlackPoints(matrix2, i, i2, width, height);
            BitMatrix bitMatrix2 = new BitMatrix(width, height);
            calculateThresholdForBlock(matrix2, i, i2, width, height, calculateBlackPoints, bitMatrix2);
            this.matrix = bitMatrix2;
        }
        return this.matrix;
    }
}
