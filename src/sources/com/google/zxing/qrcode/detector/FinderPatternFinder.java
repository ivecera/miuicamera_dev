package com.google.zxing.qrcode.detector;

import com.google.zxing.DecodeHintType;
import com.google.zxing.NotFoundException;
import com.google.zxing.ResultPoint;
import com.google.zxing.ResultPointCallback;
import com.google.zxing.common.BitMatrix;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class FinderPatternFinder {
    private static final int CENTER_QUORUM = 2;
    protected static final int MAX_MODULES = 97;
    protected static final int MIN_SKIP = 3;
    private static final EstimatedModuleComparator moduleComparator = new EstimatedModuleComparator();
    private final int[] crossCheckStateCount;
    private boolean hasSkipped;
    private final BitMatrix image;
    private final List<FinderPattern> possibleCenters;
    private final ResultPointCallback resultPointCallback;

    private static final class EstimatedModuleComparator implements Serializable, Comparator<FinderPattern> {
        private EstimatedModuleComparator() {
        }

        public int compare(FinderPattern finderPattern, FinderPattern finderPattern2) {
            return Float.compare(finderPattern.getEstimatedModuleSize(), finderPattern2.getEstimatedModuleSize());
        }
    }

    public FinderPatternFinder(BitMatrix bitMatrix) {
        this(bitMatrix, null);
    }

    public FinderPatternFinder(BitMatrix bitMatrix, ResultPointCallback resultPointCallback2) {
        this.image = bitMatrix;
        this.possibleCenters = new ArrayList();
        this.crossCheckStateCount = new int[5];
        this.resultPointCallback = resultPointCallback2;
    }

    private static float centerFromEnd(int[] iArr, int i) {
        return ((float) ((i - iArr[4]) - iArr[3])) - (((float) iArr[2]) / 2.0f);
    }

    private boolean crossCheckDiagonal(int i, int i2) {
        int i3;
        int i4;
        int i5;
        int[] crossCheckStateCount2 = getCrossCheckStateCount();
        int i6 = 0;
        while (i >= i6 && i2 >= i6 && this.image.get(i2 - i6, i - i6)) {
            crossCheckStateCount2[2] = crossCheckStateCount2[2] + 1;
            i6++;
        }
        if (crossCheckStateCount2[2] == 0) {
            return false;
        }
        while (i >= i6 && i2 >= i6 && !this.image.get(i2 - i6, i - i6)) {
            crossCheckStateCount2[1] = crossCheckStateCount2[1] + 1;
            i6++;
        }
        if (crossCheckStateCount2[1] == 0) {
            return false;
        }
        while (i >= i6 && i2 >= i6 && this.image.get(i2 - i6, i - i6)) {
            crossCheckStateCount2[0] = crossCheckStateCount2[0] + 1;
            i6++;
        }
        if (crossCheckStateCount2[0] == 0) {
            return false;
        }
        int height = this.image.getHeight();
        int width = this.image.getWidth();
        int i7 = 1;
        while (true) {
            int i8 = i + i7;
            if (i8 < height && (i5 = i2 + i7) < width && this.image.get(i5, i8)) {
                crossCheckStateCount2[2] = crossCheckStateCount2[2] + 1;
                i7++;
            }
        }
        while (true) {
            int i9 = i + i7;
            if (i9 < height && (i4 = i2 + i7) < width && !this.image.get(i4, i9)) {
                crossCheckStateCount2[3] = crossCheckStateCount2[3] + 1;
                i7++;
            }
        }
        if (crossCheckStateCount2[3] == 0) {
            return false;
        }
        while (true) {
            int i10 = i + i7;
            if (i10 < height && (i3 = i2 + i7) < width && this.image.get(i3, i10)) {
                crossCheckStateCount2[4] = crossCheckStateCount2[4] + 1;
                i7++;
            }
        }
        if (crossCheckStateCount2[4] == 0) {
            return false;
        }
        return foundPatternDiagonal(crossCheckStateCount2);
    }

    private float crossCheckHorizontal(int i, int i2, int i3, int i4) {
        BitMatrix bitMatrix = this.image;
        int width = bitMatrix.getWidth();
        int[] crossCheckStateCount2 = getCrossCheckStateCount();
        int i5 = i;
        while (i5 >= 0 && bitMatrix.get(i5, i2)) {
            crossCheckStateCount2[2] = crossCheckStateCount2[2] + 1;
            i5--;
        }
        if (i5 < 0) {
            return Float.NaN;
        }
        while (i5 >= 0 && !bitMatrix.get(i5, i2) && crossCheckStateCount2[1] <= i3) {
            crossCheckStateCount2[1] = crossCheckStateCount2[1] + 1;
            i5--;
        }
        if (i5 >= 0 && crossCheckStateCount2[1] <= i3) {
            while (i5 >= 0 && bitMatrix.get(i5, i2) && crossCheckStateCount2[0] <= i3) {
                crossCheckStateCount2[0] = crossCheckStateCount2[0] + 1;
                i5--;
            }
            if (crossCheckStateCount2[0] > i3) {
                return Float.NaN;
            }
            int i6 = i + 1;
            while (i6 < width && bitMatrix.get(i6, i2)) {
                crossCheckStateCount2[2] = crossCheckStateCount2[2] + 1;
                i6++;
            }
            if (i6 == width) {
                return Float.NaN;
            }
            while (i6 < width && !bitMatrix.get(i6, i2) && crossCheckStateCount2[3] < i3) {
                crossCheckStateCount2[3] = crossCheckStateCount2[3] + 1;
                i6++;
            }
            if (i6 != width && crossCheckStateCount2[3] < i3) {
                while (i6 < width && bitMatrix.get(i6, i2) && crossCheckStateCount2[4] < i3) {
                    crossCheckStateCount2[4] = crossCheckStateCount2[4] + 1;
                    i6++;
                }
                if (crossCheckStateCount2[4] < i3 && Math.abs(((((crossCheckStateCount2[0] + crossCheckStateCount2[1]) + crossCheckStateCount2[2]) + crossCheckStateCount2[3]) + crossCheckStateCount2[4]) - i4) * 5 < i4 && foundPatternCross(crossCheckStateCount2)) {
                    return centerFromEnd(crossCheckStateCount2, i6);
                }
            }
        }
        return Float.NaN;
    }

    private float crossCheckVertical(int i, int i2, int i3, int i4) {
        BitMatrix bitMatrix = this.image;
        int height = bitMatrix.getHeight();
        int[] crossCheckStateCount2 = getCrossCheckStateCount();
        int i5 = i;
        while (i5 >= 0 && bitMatrix.get(i2, i5)) {
            crossCheckStateCount2[2] = crossCheckStateCount2[2] + 1;
            i5--;
        }
        if (i5 < 0) {
            return Float.NaN;
        }
        while (i5 >= 0 && !bitMatrix.get(i2, i5) && crossCheckStateCount2[1] <= i3) {
            crossCheckStateCount2[1] = crossCheckStateCount2[1] + 1;
            i5--;
        }
        if (i5 >= 0 && crossCheckStateCount2[1] <= i3) {
            while (i5 >= 0 && bitMatrix.get(i2, i5) && crossCheckStateCount2[0] <= i3) {
                crossCheckStateCount2[0] = crossCheckStateCount2[0] + 1;
                i5--;
            }
            if (crossCheckStateCount2[0] > i3) {
                return Float.NaN;
            }
            int i6 = i + 1;
            while (i6 < height && bitMatrix.get(i2, i6)) {
                crossCheckStateCount2[2] = crossCheckStateCount2[2] + 1;
                i6++;
            }
            if (i6 == height) {
                return Float.NaN;
            }
            while (i6 < height && !bitMatrix.get(i2, i6) && crossCheckStateCount2[3] < i3) {
                crossCheckStateCount2[3] = crossCheckStateCount2[3] + 1;
                i6++;
            }
            if (i6 != height && crossCheckStateCount2[3] < i3) {
                while (i6 < height && bitMatrix.get(i2, i6) && crossCheckStateCount2[4] < i3) {
                    crossCheckStateCount2[4] = crossCheckStateCount2[4] + 1;
                    i6++;
                }
                if (crossCheckStateCount2[4] < i3 && Math.abs(((((crossCheckStateCount2[0] + crossCheckStateCount2[1]) + crossCheckStateCount2[2]) + crossCheckStateCount2[3]) + crossCheckStateCount2[4]) - i4) * 5 < i4 * 2 && foundPatternCross(crossCheckStateCount2)) {
                    return centerFromEnd(crossCheckStateCount2, i6);
                }
            }
        }
        return Float.NaN;
    }

    private int findRowSkip() {
        if (this.possibleCenters.size() <= 1) {
            return 0;
        }
        FinderPattern finderPattern = null;
        for (FinderPattern finderPattern2 : this.possibleCenters) {
            if (finderPattern2.getCount() >= 2) {
                if (finderPattern == null) {
                    finderPattern = finderPattern2;
                } else {
                    this.hasSkipped = true;
                    return ((int) (Math.abs(finderPattern.getX() - finderPattern2.getX()) - Math.abs(finderPattern.getY() - finderPattern2.getY()))) / 2;
                }
            }
        }
        return 0;
    }

    protected static boolean foundPatternCross(int[] iArr) {
        int i = 0;
        for (int i2 = 0; i2 < 5; i2++) {
            int i3 = iArr[i2];
            if (i3 == 0) {
                return false;
            }
            i += i3;
        }
        if (i < 7) {
            return false;
        }
        float f2 = ((float) i) / 7.0f;
        float f3 = f2 / 2.0f;
        return Math.abs(f2 - ((float) iArr[0])) < f3 && Math.abs(f2 - ((float) iArr[1])) < f3 && Math.abs((f2 * 3.0f) - ((float) iArr[2])) < 3.0f * f3 && Math.abs(f2 - ((float) iArr[3])) < f3 && Math.abs(f2 - ((float) iArr[4])) < f3;
    }

    protected static boolean foundPatternDiagonal(int[] iArr) {
        int i = 0;
        for (int i2 = 0; i2 < 5; i2++) {
            int i3 = iArr[i2];
            if (i3 == 0) {
                return false;
            }
            i += i3;
        }
        if (i < 7) {
            return false;
        }
        float f2 = ((float) i) / 7.0f;
        float f3 = f2 / 1.333f;
        return Math.abs(f2 - ((float) iArr[0])) < f3 && Math.abs(f2 - ((float) iArr[1])) < f3 && Math.abs((f2 * 3.0f) - ((float) iArr[2])) < 3.0f * f3 && Math.abs(f2 - ((float) iArr[3])) < f3 && Math.abs(f2 - ((float) iArr[4])) < f3;
    }

    private int[] getCrossCheckStateCount() {
        clearCounts(this.crossCheckStateCount);
        return this.crossCheckStateCount;
    }

    private boolean haveMultiplyConfirmedCenters() {
        int size = this.possibleCenters.size();
        float f2 = 0.0f;
        float f3 = 0.0f;
        int i = 0;
        for (FinderPattern finderPattern : this.possibleCenters) {
            if (finderPattern.getCount() >= 2) {
                i++;
                f3 += finderPattern.getEstimatedModuleSize();
            }
        }
        if (i < 3) {
            return false;
        }
        float f4 = f3 / ((float) size);
        for (FinderPattern finderPattern2 : this.possibleCenters) {
            f2 += Math.abs(finderPattern2.getEstimatedModuleSize() - f4);
        }
        return f2 <= f3 * 0.05f;
    }

    private FinderPattern[] selectBestPatterns() throws NotFoundException {
        if (this.possibleCenters.size() >= 3) {
            this.possibleCenters.sort(moduleComparator);
            double[] dArr = new double[3];
            FinderPattern[] finderPatternArr = new FinderPattern[3];
            double d2 = Double.MAX_VALUE;
            int i = 0;
            while (i < this.possibleCenters.size() - 2) {
                FinderPattern finderPattern = this.possibleCenters.get(i);
                float estimatedModuleSize = finderPattern.getEstimatedModuleSize();
                i++;
                double d3 = d2;
                int i2 = i;
                while (i2 < this.possibleCenters.size() - 1) {
                    FinderPattern finderPattern2 = this.possibleCenters.get(i2);
                    double squaredDistance = squaredDistance(finderPattern, finderPattern2);
                    i2++;
                    double d4 = d3;
                    for (int i3 = i2; i3 < this.possibleCenters.size(); i3++) {
                        FinderPattern finderPattern3 = this.possibleCenters.get(i3);
                        if (finderPattern3.getEstimatedModuleSize() <= 1.4f * estimatedModuleSize) {
                            dArr[0] = squaredDistance;
                            dArr[1] = squaredDistance(finderPattern2, finderPattern3);
                            dArr[2] = squaredDistance(finderPattern, finderPattern3);
                            Arrays.sort(dArr);
                            double abs = Math.abs(dArr[2] - (dArr[1] * 2.0d)) + Math.abs(dArr[2] - (dArr[0] * 2.0d));
                            if (abs < d4) {
                                finderPatternArr[0] = finderPattern;
                                finderPatternArr[1] = finderPattern2;
                                finderPatternArr[2] = finderPattern3;
                                d4 = abs;
                            }
                        }
                    }
                    d3 = d4;
                }
                d2 = d3;
            }
            if (d2 != Double.MAX_VALUE) {
                return finderPatternArr;
            }
            throw NotFoundException.getNotFoundInstance();
        }
        throw NotFoundException.getNotFoundInstance();
    }

    private static double squaredDistance(FinderPattern finderPattern, FinderPattern finderPattern2) {
        double x = (double) (finderPattern.getX() - finderPattern2.getX());
        double y = (double) (finderPattern.getY() - finderPattern2.getY());
        return (x * x) + (y * y);
    }

    /* access modifiers changed from: protected */
    public final void clearCounts(int[] iArr) {
        for (int i = 0; i < iArr.length; i++) {
            iArr[i] = 0;
        }
    }

    /* access modifiers changed from: package-private */
    public final FinderPatternInfo find(Map<DecodeHintType, ?> map) throws NotFoundException {
        boolean z = map != null && map.containsKey(DecodeHintType.TRY_HARDER);
        int height = this.image.getHeight();
        int width = this.image.getWidth();
        int i = (height * 3) / 388;
        if (i < 3 || z) {
            i = 3;
        }
        int[] iArr = new int[5];
        int i2 = i - 1;
        int i3 = i;
        boolean z2 = false;
        while (i2 < height && !z2) {
            clearCounts(iArr);
            boolean z3 = z2;
            int i4 = i3;
            int i5 = 0;
            int i6 = 0;
            while (i5 < width) {
                if (this.image.get(i5, i2)) {
                    if ((i6 & 1) == 1) {
                        i6++;
                    }
                    iArr[i6] = iArr[i6] + 1;
                } else if ((i6 & 1) != 0) {
                    iArr[i6] = iArr[i6] + 1;
                } else if (i6 == 4) {
                    if (!foundPatternCross(iArr)) {
                        shiftCounts2(iArr);
                    } else if (handlePossibleCenter(iArr, i2, i5)) {
                        if (this.hasSkipped) {
                            z3 = haveMultiplyConfirmedCenters();
                        } else {
                            int findRowSkip = findRowSkip();
                            if (findRowSkip > iArr[2]) {
                                i2 += (findRowSkip - iArr[2]) - 2;
                                i5 = width - 1;
                            }
                        }
                        clearCounts(iArr);
                        i6 = 0;
                        i4 = 2;
                    } else {
                        shiftCounts2(iArr);
                    }
                    i6 = 3;
                } else {
                    i6++;
                    iArr[i6] = iArr[i6] + 1;
                }
                i5++;
            }
            if (!foundPatternCross(iArr) || !handlePossibleCenter(iArr, i2, width)) {
                i3 = i4;
            } else {
                int i7 = iArr[0];
                if (this.hasSkipped) {
                    i3 = i7;
                    z2 = haveMultiplyConfirmedCenters();
                    i2 += i3;
                } else {
                    i3 = i7;
                }
            }
            z2 = z3;
            i2 += i3;
        }
        FinderPattern[] selectBestPatterns = selectBestPatterns();
        ResultPoint.orderBestPatterns(selectBestPatterns);
        return new FinderPatternInfo(selectBestPatterns);
    }

    /* access modifiers changed from: protected */
    public final BitMatrix getImage() {
        return this.image;
    }

    /* access modifiers changed from: protected */
    public final List<FinderPattern> getPossibleCenters() {
        return this.possibleCenters;
    }

    /* access modifiers changed from: protected */
    public final boolean handlePossibleCenter(int[] iArr, int i, int i2) {
        boolean z = false;
        int i3 = iArr[0] + iArr[1] + iArr[2] + iArr[3] + iArr[4];
        int centerFromEnd = (int) centerFromEnd(iArr, i2);
        float crossCheckVertical = crossCheckVertical(i, centerFromEnd, iArr[2], i3);
        if (!Float.isNaN(crossCheckVertical)) {
            int i4 = (int) crossCheckVertical;
            float crossCheckHorizontal = crossCheckHorizontal(centerFromEnd, i4, iArr[2], i3);
            if (!Float.isNaN(crossCheckHorizontal) && crossCheckDiagonal(i4, (int) crossCheckHorizontal)) {
                float f2 = ((float) i3) / 7.0f;
                int i5 = 0;
                while (true) {
                    if (i5 >= this.possibleCenters.size()) {
                        break;
                    }
                    FinderPattern finderPattern = this.possibleCenters.get(i5);
                    if (finderPattern.aboutEquals(f2, crossCheckVertical, crossCheckHorizontal)) {
                        this.possibleCenters.set(i5, finderPattern.combineEstimate(crossCheckVertical, crossCheckHorizontal, f2));
                        z = true;
                        break;
                    }
                    i5++;
                }
                if (!z) {
                    FinderPattern finderPattern2 = new FinderPattern(crossCheckHorizontal, crossCheckVertical, f2);
                    this.possibleCenters.add(finderPattern2);
                    ResultPointCallback resultPointCallback2 = this.resultPointCallback;
                    if (resultPointCallback2 != null) {
                        resultPointCallback2.foundPossibleResultPoint(finderPattern2);
                    }
                }
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public final boolean handlePossibleCenter(int[] iArr, int i, int i2, boolean z) {
        return handlePossibleCenter(iArr, i, i2);
    }

    /* access modifiers changed from: protected */
    public final void shiftCounts2(int[] iArr) {
        iArr[0] = iArr[2];
        iArr[1] = iArr[3];
        iArr[2] = iArr[4];
        iArr[3] = 1;
        iArr[4] = 0;
    }
}
