package com.google.zxing.pdf417.detector;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.NotFoundException;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitMatrix;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public final class Detector {
    private static final int BARCODE_MIN_HEIGHT = 10;
    private static final int[] INDEXES_START_PATTERN = {0, 4, 1, 5};
    private static final int[] INDEXES_STOP_PATTERN = {6, 2, 7, 3};
    private static final float MAX_AVG_VARIANCE = 0.42f;
    private static final float MAX_INDIVIDUAL_VARIANCE = 0.8f;
    private static final int MAX_PATTERN_DRIFT = 5;
    private static final int MAX_PIXEL_DRIFT = 3;
    private static final int ROW_STEP = 5;
    private static final int SKIPPED_ROW_COUNT_MAX = 25;
    private static final int[] START_PATTERN = {8, 1, 1, 1, 1, 1, 1, 3};
    private static final int[] STOP_PATTERN = {7, 1, 1, 3, 1, 1, 1, 2, 1};

    private Detector() {
    }

    private static void copyToResult(ResultPoint[] resultPointArr, ResultPoint[] resultPointArr2, int[] iArr) {
        for (int i = 0; i < iArr.length; i++) {
            resultPointArr[iArr[i]] = resultPointArr2[i];
        }
    }

    public static PDF417DetectorResult detect(BinaryBitmap binaryBitmap, Map<DecodeHintType, ?> map, boolean z) throws NotFoundException {
        BitMatrix blackMatrix = binaryBitmap.getBlackMatrix();
        List<ResultPoint[]> detect = detect(z, blackMatrix);
        if (detect.isEmpty()) {
            blackMatrix = blackMatrix.clone();
            blackMatrix.rotate180();
            detect = detect(z, blackMatrix);
        }
        return new PDF417DetectorResult(blackMatrix, detect);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0027, code lost:
        if (r4.hasNext() == false) goto L_0x004f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0029, code lost:
        r5 = (com.google.zxing.ResultPoint[]) r4.next();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0031, code lost:
        if (r5[1] == null) goto L_0x003f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0033, code lost:
        r3 = (int) java.lang.Math.max((float) r3, r5[1].getY());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0041, code lost:
        if (r5[3] == null) goto L_0x0023;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0043, code lost:
        r3 = java.lang.Math.max(r3, (int) r5[3].getY());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x001d, code lost:
        if (r5 == 0) goto L_0x007d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x001f, code lost:
        r4 = r0.iterator();
     */
    private static List<ResultPoint[]> detect(boolean z, BitMatrix bitMatrix) {
        int i;
        float f2;
        ArrayList arrayList = new ArrayList();
        int i2 = 0;
        int i3 = 0;
        loop0:
        while (true) {
            int i4 = i3;
            while (true) {
                if (i2 >= bitMatrix.getHeight()) {
                    break loop0;
                }
                ResultPoint[] findVertices = findVertices(bitMatrix, i2, i3);
                if (findVertices[0] != null || findVertices[3] != null) {
                    arrayList.add(findVertices);
                    if (!z) {
                        break loop0;
                    }
                    if (findVertices[2] != null) {
                        i = (int) findVertices[2].getX();
                        f2 = findVertices[2].getY();
                    } else {
                        i = (int) findVertices[4].getX();
                        f2 = findVertices[4].getY();
                    }
                    i2 = (int) f2;
                    i3 = i;
                    i4 = 1;
                } else {
                    break;
                }
            }
            i2 += 5;
            i3 = 0;
        }
        return arrayList;
    }

    private static int[] findGuardPattern(BitMatrix bitMatrix, int i, int i2, int i3, boolean z, int[] iArr, int[] iArr2) {
        Arrays.fill(iArr2, 0, iArr2.length, 0);
        int i4 = 0;
        while (bitMatrix.get(i, i2) && i > 0) {
            int i5 = i4 + 1;
            if (i4 >= 3) {
                break;
            }
            i--;
            i4 = i5;
        }
        int length = iArr.length;
        int i6 = i;
        boolean z2 = z;
        int i7 = 0;
        while (true) {
            boolean z3 = true;
            if (i < i3) {
                if (bitMatrix.get(i, i2) != z2) {
                    iArr2[i7] = iArr2[i7] + 1;
                } else {
                    if (i7 != length - 1) {
                        i7++;
                    } else if (patternMatchVariance(iArr2, iArr, MAX_INDIVIDUAL_VARIANCE) < MAX_AVG_VARIANCE) {
                        return new int[]{i6, i};
                    } else {
                        i6 += iArr2[0] + iArr2[1];
                        int i8 = i7 - 1;
                        System.arraycopy(iArr2, 2, iArr2, 0, i8);
                        iArr2[i8] = 0;
                        iArr2[i7] = 0;
                        i7--;
                    }
                    iArr2[i7] = 1;
                    if (z2) {
                        z3 = false;
                    }
                    z2 = z3;
                }
                i++;
            } else if (i7 != length - 1 || patternMatchVariance(iArr2, iArr, MAX_INDIVIDUAL_VARIANCE) >= MAX_AVG_VARIANCE) {
                return null;
            } else {
                return new int[]{i6, i - 1};
            }
        }
    }

    private static ResultPoint[] findRowsWithPattern(BitMatrix bitMatrix, int i, int i2, int i3, int i4, int[] iArr) {
        boolean z;
        int i5;
        ResultPoint[] resultPointArr = new ResultPoint[4];
        int[] iArr2 = new int[iArr.length];
        int i6 = i3;
        while (true) {
            if (i6 >= i) {
                z = false;
                break;
            }
            int[] findGuardPattern = findGuardPattern(bitMatrix, i4, i6, i2, false, iArr, iArr2);
            if (findGuardPattern != null) {
                while (true) {
                    if (i6 <= 0) {
                        break;
                    }
                    i6--;
                    findGuardPattern = findGuardPattern(bitMatrix, i4, i6, i2, false, iArr, iArr2);
                    if (findGuardPattern == null) {
                        i6++;
                        break;
                    }
                }
                float f2 = (float) i6;
                resultPointArr[0] = new ResultPoint((float) findGuardPattern[0], f2);
                resultPointArr[1] = new ResultPoint((float) findGuardPattern[1], f2);
                z = true;
            } else {
                i6 += 5;
            }
        }
        int i7 = i6 + 1;
        if (z) {
            int[] iArr3 = {(int) resultPointArr[0].getX(), (int) resultPointArr[1].getX()};
            int i8 = i7;
            int i9 = 0;
            while (true) {
                if (i8 >= i) {
                    i5 = i9;
                    break;
                }
                i5 = i9;
                int[] findGuardPattern2 = findGuardPattern(bitMatrix, iArr3[0], i8, i2, false, iArr, iArr2);
                if (findGuardPattern2 == null || Math.abs(iArr3[0] - findGuardPattern2[0]) >= 5 || Math.abs(iArr3[1] - findGuardPattern2[1]) >= 5) {
                    if (i5 > 25) {
                        break;
                    }
                    i9 = i5 + 1;
                } else {
                    iArr3 = findGuardPattern2;
                    i9 = 0;
                }
                i8++;
            }
            i7 = i8 - (i5 + 1);
            float f3 = (float) i7;
            resultPointArr[2] = new ResultPoint((float) iArr3[0], f3);
            resultPointArr[3] = new ResultPoint((float) iArr3[1], f3);
        }
        if (i7 - i6 < 10) {
            Arrays.fill(resultPointArr, (Object) null);
        }
        return resultPointArr;
    }

    private static ResultPoint[] findVertices(BitMatrix bitMatrix, int i, int i2) {
        int height = bitMatrix.getHeight();
        int width = bitMatrix.getWidth();
        ResultPoint[] resultPointArr = new ResultPoint[8];
        copyToResult(resultPointArr, findRowsWithPattern(bitMatrix, height, width, i, i2, START_PATTERN), INDEXES_START_PATTERN);
        if (resultPointArr[4] != null) {
            i2 = (int) resultPointArr[4].getX();
            i = (int) resultPointArr[4].getY();
        }
        copyToResult(resultPointArr, findRowsWithPattern(bitMatrix, height, width, i, i2, STOP_PATTERN), INDEXES_STOP_PATTERN);
        return resultPointArr;
    }

    private static float patternMatchVariance(int[] iArr, int[] iArr2, float f2) {
        int length = iArr.length;
        int i = 0;
        int i2 = 0;
        for (int i3 = 0; i3 < length; i3++) {
            i += iArr[i3];
            i2 += iArr2[i3];
        }
        if (i < i2) {
            return Float.POSITIVE_INFINITY;
        }
        float f3 = (float) i;
        float f4 = f3 / ((float) i2);
        float f5 = f2 * f4;
        float f6 = 0.0f;
        for (int i4 = 0; i4 < length; i4++) {
            int i5 = iArr[i4];
            float f7 = ((float) iArr2[i4]) * f4;
            float f8 = (float) i5;
            float f9 = f8 > f7 ? f8 - f7 : f7 - f8;
            if (f9 > f5) {
                return Float.POSITIVE_INFINITY;
            }
            f6 += f9;
        }
        return f6 / f3;
    }
}
