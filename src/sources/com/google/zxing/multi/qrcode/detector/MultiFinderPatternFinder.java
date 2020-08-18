package com.google.zxing.multi.qrcode.detector;

import com.google.zxing.DecodeHintType;
import com.google.zxing.NotFoundException;
import com.google.zxing.ResultPoint;
import com.google.zxing.ResultPointCallback;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.detector.FinderPattern;
import com.google.zxing.qrcode.detector.FinderPatternFinder;
import com.google.zxing.qrcode.detector.FinderPatternInfo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

final class MultiFinderPatternFinder extends FinderPatternFinder {
    private static final float DIFF_MODSIZE_CUTOFF = 0.5f;
    private static final float DIFF_MODSIZE_CUTOFF_PERCENT = 0.05f;
    private static final FinderPattern[][] EMPTY_FP_2D_ARRAY = new FinderPattern[0][];
    private static final FinderPattern[] EMPTY_FP_ARRAY = new FinderPattern[0];
    private static final FinderPatternInfo[] EMPTY_RESULT_ARRAY = new FinderPatternInfo[0];
    private static final float MAX_MODULE_COUNT_PER_EDGE = 180.0f;
    private static final float MIN_MODULE_COUNT_PER_EDGE = 9.0f;

    private static final class ModuleSizeComparator implements Serializable, Comparator<FinderPattern> {
        private ModuleSizeComparator() {
        }

        public int compare(FinderPattern finderPattern, FinderPattern finderPattern2) {
            double estimatedModuleSize = (double) (finderPattern2.getEstimatedModuleSize() - finderPattern.getEstimatedModuleSize());
            if (estimatedModuleSize < 0.0d) {
                return -1;
            }
            return estimatedModuleSize > 0.0d ? 1 : 0;
        }
    }

    MultiFinderPatternFinder(BitMatrix bitMatrix, ResultPointCallback resultPointCallback) {
        super(bitMatrix, resultPointCallback);
    }

    private FinderPattern[][] selectMultipleBestPatterns() throws NotFoundException {
        List<FinderPattern> possibleCenters = getPossibleCenters();
        int size = possibleCenters.size();
        int i = 3;
        if (size >= 3) {
            char c2 = 0;
            if (size == 3) {
                return new FinderPattern[][]{(FinderPattern[]) possibleCenters.toArray(EMPTY_FP_ARRAY)};
            }
            Collections.sort(possibleCenters, new ModuleSizeComparator());
            ArrayList arrayList = new ArrayList();
            int i2 = 0;
            while (i2 < size - 2) {
                FinderPattern finderPattern = possibleCenters.get(i2);
                if (finderPattern != null) {
                    int i3 = i2 + 1;
                    while (i3 < size - 1) {
                        FinderPattern finderPattern2 = possibleCenters.get(i3);
                        if (finderPattern2 != null) {
                            float estimatedModuleSize = (finderPattern.getEstimatedModuleSize() - finderPattern2.getEstimatedModuleSize()) / Math.min(finderPattern.getEstimatedModuleSize(), finderPattern2.getEstimatedModuleSize());
                            float f2 = 0.5f;
                            int i4 = (Math.abs(finderPattern.getEstimatedModuleSize() - finderPattern2.getEstimatedModuleSize()) > 0.5f ? 1 : (Math.abs(finderPattern.getEstimatedModuleSize() - finderPattern2.getEstimatedModuleSize()) == 0.5f ? 0 : -1));
                            float f3 = DIFF_MODSIZE_CUTOFF_PERCENT;
                            if (i4 > 0 && estimatedModuleSize >= DIFF_MODSIZE_CUTOFF_PERCENT) {
                                break;
                            }
                            int i5 = i3 + 1;
                            while (i5 < size) {
                                FinderPattern finderPattern3 = possibleCenters.get(i5);
                                if (finderPattern3 != null) {
                                    float estimatedModuleSize2 = (finderPattern2.getEstimatedModuleSize() - finderPattern3.getEstimatedModuleSize()) / Math.min(finderPattern2.getEstimatedModuleSize(), finderPattern3.getEstimatedModuleSize());
                                    if (Math.abs(finderPattern2.getEstimatedModuleSize() - finderPattern3.getEstimatedModuleSize()) > f2 && estimatedModuleSize2 >= f3) {
                                        break;
                                    }
                                    FinderPattern[] finderPatternArr = new FinderPattern[i];
                                    finderPatternArr[c2] = finderPattern;
                                    finderPatternArr[1] = finderPattern2;
                                    finderPatternArr[2] = finderPattern3;
                                    ResultPoint.orderBestPatterns(finderPatternArr);
                                    FinderPatternInfo finderPatternInfo = new FinderPatternInfo(finderPatternArr);
                                    float distance = ResultPoint.distance(finderPatternInfo.getTopLeft(), finderPatternInfo.getBottomLeft());
                                    float distance2 = ResultPoint.distance(finderPatternInfo.getTopRight(), finderPatternInfo.getBottomLeft());
                                    float distance3 = ResultPoint.distance(finderPatternInfo.getTopLeft(), finderPatternInfo.getTopRight());
                                    float estimatedModuleSize3 = (distance + distance3) / (finderPattern.getEstimatedModuleSize() * 2.0f);
                                    if (estimatedModuleSize3 <= MAX_MODULE_COUNT_PER_EDGE && estimatedModuleSize3 >= 9.0f && Math.abs((distance - distance3) / Math.min(distance, distance3)) < 0.1f) {
                                        double d2 = (double) distance;
                                        double d3 = (double) distance3;
                                        float sqrt = (float) Math.sqrt((d2 * d2) + (d3 * d3));
                                        if (Math.abs((distance2 - sqrt) / Math.min(distance2, sqrt)) < 0.1f) {
                                            arrayList.add(finderPatternArr);
                                        }
                                    }
                                }
                                i5++;
                                i = 3;
                                c2 = 0;
                                f2 = 0.5f;
                                f3 = DIFF_MODSIZE_CUTOFF_PERCENT;
                            }
                        }
                        i3++;
                        i = 3;
                        c2 = 0;
                    }
                }
                i2++;
                i = 3;
                c2 = 0;
            }
            if (!arrayList.isEmpty()) {
                return (FinderPattern[][]) arrayList.toArray(EMPTY_FP_2D_ARRAY);
            }
            throw NotFoundException.getNotFoundInstance();
        }
        throw NotFoundException.getNotFoundInstance();
    }

    public FinderPatternInfo[] findMulti(Map<DecodeHintType, ?> map) throws NotFoundException {
        boolean z = map != null && map.containsKey(DecodeHintType.TRY_HARDER);
        BitMatrix image = getImage();
        int height = image.getHeight();
        int width = image.getWidth();
        int i = (height * 3) / 388;
        if (i < 3 || z) {
            i = 3;
        }
        int[] iArr = new int[5];
        for (int i2 = i - 1; i2 < height; i2 += i) {
            clearCounts(iArr);
            int i3 = 0;
            for (int i4 = 0; i4 < width; i4++) {
                if (image.get(i4, i2)) {
                    if ((i3 & 1) == 1) {
                        i3++;
                    }
                    iArr[i3] = iArr[i3] + 1;
                } else if ((i3 & 1) != 0) {
                    iArr[i3] = iArr[i3] + 1;
                } else if (i3 != 4) {
                    i3++;
                    iArr[i3] = iArr[i3] + 1;
                } else if (!FinderPatternFinder.foundPatternCross(iArr) || !handlePossibleCenter(iArr, i2, i4)) {
                    shiftCounts2(iArr);
                    i3 = 3;
                } else {
                    clearCounts(iArr);
                    i3 = 0;
                }
            }
            if (FinderPatternFinder.foundPatternCross(iArr)) {
                handlePossibleCenter(iArr, i2, width);
            }
        }
        FinderPattern[][] selectMultipleBestPatterns = selectMultipleBestPatterns();
        ArrayList arrayList = new ArrayList();
        for (FinderPattern[] finderPatternArr : selectMultipleBestPatterns) {
            ResultPoint.orderBestPatterns(finderPatternArr);
            arrayList.add(new FinderPatternInfo(finderPatternArr));
        }
        return arrayList.isEmpty() ? EMPTY_RESULT_ARRAY : (FinderPatternInfo[]) arrayList.toArray(EMPTY_RESULT_ARRAY);
    }
}
