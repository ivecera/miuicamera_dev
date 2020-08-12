package com.google.zxing.oned.rss;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.ResultPoint;
import com.google.zxing.ResultPointCallback;
import com.google.zxing.common.BitArray;
import com.google.zxing.common.detector.MathUtils;
import com.google.zxing.oned.OneDReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public final class RSS14Reader extends AbstractRSSReader {
    private static final int[][] FINDER_PATTERNS = {new int[]{3, 8, 2, 1}, new int[]{3, 5, 5, 1}, new int[]{3, 3, 7, 1}, new int[]{3, 1, 9, 1}, new int[]{2, 7, 4, 1}, new int[]{2, 5, 6, 1}, new int[]{2, 3, 8, 1}, new int[]{1, 5, 7, 1}, new int[]{1, 3, 9, 1}};
    private static final int[] INSIDE_GSUM = {0, 336, 1036, 1516};
    private static final int[] INSIDE_ODD_TOTAL_SUBSET = {4, 20, 48, 81};
    private static final int[] INSIDE_ODD_WIDEST = {2, 4, 6, 8};
    private static final int[] OUTSIDE_EVEN_TOTAL_SUBSET = {1, 10, 34, 70, 126};
    private static final int[] OUTSIDE_GSUM = {0, 161, 961, 2015, 2715};
    private static final int[] OUTSIDE_ODD_WIDEST = {8, 6, 4, 3, 1};
    private final List<Pair> possibleLeftPairs = new ArrayList();
    private final List<Pair> possibleRightPairs = new ArrayList();

    private static void addOrTally(Collection<Pair> collection, Pair pair) {
        if (pair != null) {
            boolean z = false;
            Iterator<Pair> it = collection.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                Pair next = it.next();
                if (next.getValue() == pair.getValue()) {
                    next.incrementCount();
                    z = true;
                    break;
                }
            }
            if (!z) {
                collection.add(pair);
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:30:0x0058  */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x005a  */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x005f  */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x0063  */
    /* JADX WARNING: Removed duplicated region for block: B:60:0x0099  */
    /* JADX WARNING: Removed duplicated region for block: B:68:0x00a9  */
    /* JADX WARNING: Removed duplicated region for block: B:73:0x00be  */
    /* JADX WARNING: Removed duplicated region for block: B:75:0x00cb  */
    /* JADX WARNING: Removed duplicated region for block: B:80:0x00e0  */
    /* JADX WARNING: Removed duplicated region for block: B:84:? A[RETURN, SYNTHETIC] */
    private void adjustOddEvenCounts(boolean z, int i) throws NotFoundException {
        boolean z2;
        boolean z3;
        boolean z4;
        boolean z5;
        int i2;
        boolean z6;
        boolean z7;
        boolean z8;
        int sum = MathUtils.sum(getOddCounts());
        int sum2 = MathUtils.sum(getEvenCounts());
        boolean z9 = false;
        if (z) {
            if (sum > 12) {
                z8 = false;
                z7 = true;
            } else if (sum < 4) {
                z7 = false;
                z8 = true;
            } else {
                z8 = false;
                z7 = false;
            }
            if (sum2 > 12) {
                z5 = false;
                z4 = z8;
                z3 = z7;
            } else if (sum2 < 4) {
                z5 = true;
                z4 = z8;
                z3 = z7;
                z2 = false;
                i2 = (sum + sum2) - i;
                boolean z10 = (sum & 1) == z;
                if ((sum2 & 1) == 1) {
                    z9 = true;
                }
                if (i2 != -1) {
                    if (i2 != 0) {
                        if (i2 != 1) {
                            throw NotFoundException.getNotFoundInstance();
                        } else if (z10) {
                            if (!z9) {
                                z3 = true;
                            } else {
                                throw NotFoundException.getNotFoundInstance();
                            }
                        } else if (z9) {
                            z2 = true;
                        } else {
                            throw NotFoundException.getNotFoundInstance();
                        }
                    } else if (z10) {
                        if (!z9) {
                            throw NotFoundException.getNotFoundInstance();
                        } else if (sum < sum2) {
                            z4 = true;
                            z2 = true;
                        } else {
                            z5 = true;
                            z3 = true;
                        }
                    } else if (z9) {
                        throw NotFoundException.getNotFoundInstance();
                    }
                } else if (z10) {
                    if (!z9) {
                        z4 = true;
                    } else {
                        throw NotFoundException.getNotFoundInstance();
                    }
                } else if (z9) {
                    z5 = true;
                } else {
                    throw NotFoundException.getNotFoundInstance();
                }
                if (z4) {
                    if (!z3) {
                        AbstractRSSReader.increment(getOddCounts(), getOddRoundingErrors());
                    } else {
                        throw NotFoundException.getNotFoundInstance();
                    }
                }
                if (z3) {
                    AbstractRSSReader.decrement(getOddCounts(), getOddRoundingErrors());
                }
                if (z5) {
                    if (!z2) {
                        AbstractRSSReader.increment(getEvenCounts(), getOddRoundingErrors());
                    } else {
                        throw NotFoundException.getNotFoundInstance();
                    }
                }
                if (z2) {
                    AbstractRSSReader.decrement(getEvenCounts(), getEvenRoundingErrors());
                    return;
                }
                return;
            } else {
                z6 = false;
                z4 = z8;
                z3 = z7;
                z2 = z5;
                i2 = (sum + sum2) - i;
                if ((sum & 1) == z) {
                }
                if ((sum2 & 1) == 1) {
                }
                if (i2 != -1) {
                }
                if (z4) {
                }
                if (z3) {
                }
                if (z5) {
                }
                if (z2) {
                }
            }
        } else {
            if (sum > 11) {
                z4 = false;
                z3 = true;
            } else if (sum < 5) {
                z3 = false;
                z4 = true;
            } else {
                z4 = false;
                z3 = false;
            }
            if (sum2 > 10) {
                z5 = false;
            } else if (sum2 < 4) {
                z2 = false;
                z5 = true;
                i2 = (sum + sum2) - i;
                if ((sum & 1) == z) {
                }
                if ((sum2 & 1) == 1) {
                }
                if (i2 != -1) {
                }
                if (z4) {
                }
                if (z3) {
                }
                if (z5) {
                }
                if (z2) {
                }
            } else {
                z6 = false;
                z2 = z5;
                i2 = (sum + sum2) - i;
                if ((sum & 1) == z) {
                }
                if ((sum2 & 1) == 1) {
                }
                if (i2 != -1) {
                }
                if (z4) {
                }
                if (z3) {
                }
                if (z5) {
                }
                if (z2) {
                }
            }
        }
        z2 = true;
        i2 = (sum + sum2) - i;
        if ((sum & 1) == z) {
        }
        if ((sum2 & 1) == 1) {
        }
        if (i2 != -1) {
        }
        if (z4) {
        }
        if (z3) {
        }
        if (z5) {
        }
        if (z2) {
        }
    }

    private static boolean checkChecksum(Pair pair, Pair pair2) {
        int checksumPortion = (pair.getChecksumPortion() + (pair2.getChecksumPortion() * 16)) % 79;
        int value = (pair.getFinderPattern().getValue() * 9) + pair2.getFinderPattern().getValue();
        if (value > 72) {
            value--;
        }
        if (value > 8) {
            value--;
        }
        return checksumPortion == value;
    }

    private static Result constructResult(Pair pair, Pair pair2) {
        String valueOf = String.valueOf((((long) pair.getValue()) * 4537077) + ((long) pair2.getValue()));
        StringBuilder sb = new StringBuilder(14);
        for (int length = 13 - valueOf.length(); length > 0; length--) {
            sb.append('0');
        }
        sb.append(valueOf);
        int i = 0;
        for (int i2 = 0; i2 < 13; i2++) {
            int charAt = sb.charAt(i2) - '0';
            if ((i2 & 1) == 0) {
                charAt *= 3;
            }
            i += charAt;
        }
        int i3 = 10 - (i % 10);
        if (i3 == 10) {
            i3 = 0;
        }
        sb.append(i3);
        ResultPoint[] resultPoints = pair.getFinderPattern().getResultPoints();
        ResultPoint[] resultPoints2 = pair2.getFinderPattern().getResultPoints();
        return new Result(sb.toString(), null, new ResultPoint[]{resultPoints[0], resultPoints[1], resultPoints2[0], resultPoints2[1]}, BarcodeFormat.RSS_14);
    }

    private DataCharacter decodeDataCharacter(BitArray bitArray, FinderPattern finderPattern, boolean z) throws NotFoundException {
        int[] dataCharacterCounters = getDataCharacterCounters();
        for (int i = 0; i < dataCharacterCounters.length; i++) {
            dataCharacterCounters[i] = 0;
        }
        if (z) {
            OneDReader.recordPatternInReverse(bitArray, finderPattern.getStartEnd()[0], dataCharacterCounters);
        } else {
            OneDReader.recordPattern(bitArray, finderPattern.getStartEnd()[1] + 1, dataCharacterCounters);
            int i2 = 0;
            for (int length = dataCharacterCounters.length - 1; i2 < length; length--) {
                int i3 = dataCharacterCounters[i2];
                dataCharacterCounters[i2] = dataCharacterCounters[length];
                dataCharacterCounters[length] = i3;
                i2++;
            }
        }
        int i4 = z ? 16 : 15;
        float sum = ((float) MathUtils.sum(dataCharacterCounters)) / ((float) i4);
        int[] oddCounts = getOddCounts();
        int[] evenCounts = getEvenCounts();
        float[] oddRoundingErrors = getOddRoundingErrors();
        float[] evenRoundingErrors = getEvenRoundingErrors();
        for (int i5 = 0; i5 < dataCharacterCounters.length; i5++) {
            float f2 = ((float) dataCharacterCounters[i5]) / sum;
            int i6 = (int) (0.5f + f2);
            if (i6 <= 0) {
                i6 = 1;
            } else if (i6 > 8) {
                i6 = 8;
            }
            int i7 = i5 / 2;
            if ((i5 & 1) == 0) {
                oddCounts[i7] = i6;
                oddRoundingErrors[i7] = f2 - ((float) i6);
            } else {
                evenCounts[i7] = i6;
                evenRoundingErrors[i7] = f2 - ((float) i6);
            }
        }
        adjustOddEvenCounts(z, i4);
        int i8 = 0;
        int i9 = 0;
        for (int length2 = oddCounts.length - 1; length2 >= 0; length2--) {
            i8 = (i8 * 9) + oddCounts[length2];
            i9 += oddCounts[length2];
        }
        int i10 = 0;
        int i11 = 0;
        for (int length3 = evenCounts.length - 1; length3 >= 0; length3--) {
            i10 = (i10 * 9) + evenCounts[length3];
            i11 += evenCounts[length3];
        }
        int i12 = i8 + (i10 * 3);
        if (z) {
            if ((i9 & 1) != 0 || i9 > 12 || i9 < 4) {
                throw NotFoundException.getNotFoundInstance();
            }
            int i13 = (12 - i9) / 2;
            int i14 = OUTSIDE_ODD_WIDEST[i13];
            return new DataCharacter((RSSUtils.getRSSvalue(oddCounts, i14, false) * OUTSIDE_EVEN_TOTAL_SUBSET[i13]) + RSSUtils.getRSSvalue(evenCounts, 9 - i14, true) + OUTSIDE_GSUM[i13], i12);
        } else if ((i11 & 1) != 0 || i11 > 10 || i11 < 4) {
            throw NotFoundException.getNotFoundInstance();
        } else {
            int i15 = (10 - i11) / 2;
            int i16 = INSIDE_ODD_WIDEST[i15];
            return new DataCharacter((RSSUtils.getRSSvalue(evenCounts, 9 - i16, false) * INSIDE_ODD_TOTAL_SUBSET[i15]) + RSSUtils.getRSSvalue(oddCounts, i16, true) + INSIDE_GSUM[i15], i12);
        }
    }

    private Pair decodePair(BitArray bitArray, boolean z, int i, Map<DecodeHintType, ?> map) {
        try {
            int[] findFinderPattern = findFinderPattern(bitArray, z);
            FinderPattern parseFoundFinderPattern = parseFoundFinderPattern(bitArray, i, z, findFinderPattern);
            ResultPointCallback resultPointCallback = map == null ? null : (ResultPointCallback) map.get(DecodeHintType.NEED_RESULT_POINT_CALLBACK);
            if (resultPointCallback != null) {
                float f2 = ((float) (findFinderPattern[0] + findFinderPattern[1])) / 2.0f;
                if (z) {
                    f2 = ((float) (bitArray.getSize() - 1)) - f2;
                }
                resultPointCallback.foundPossibleResultPoint(new ResultPoint(f2, (float) i));
            }
            DataCharacter decodeDataCharacter = decodeDataCharacter(bitArray, parseFoundFinderPattern, true);
            DataCharacter decodeDataCharacter2 = decodeDataCharacter(bitArray, parseFoundFinderPattern, false);
            return new Pair((decodeDataCharacter.getValue() * 1597) + decodeDataCharacter2.getValue(), decodeDataCharacter.getChecksumPortion() + (decodeDataCharacter2.getChecksumPortion() * 4), parseFoundFinderPattern);
        } catch (NotFoundException unused) {
            return null;
        }
    }

    private int[] findFinderPattern(BitArray bitArray, boolean z) throws NotFoundException {
        int[] decodeFinderCounters = getDecodeFinderCounters();
        decodeFinderCounters[0] = 0;
        decodeFinderCounters[1] = 0;
        decodeFinderCounters[2] = 0;
        decodeFinderCounters[3] = 0;
        int size = bitArray.getSize();
        int i = 0;
        boolean z2 = false;
        while (i < size) {
            z2 = !bitArray.get(i);
            if (z == z2) {
                break;
            }
            i++;
        }
        int i2 = 0;
        int i3 = i;
        while (i < size) {
            if (bitArray.get(i) != z2) {
                decodeFinderCounters[i2] = decodeFinderCounters[i2] + 1;
            } else {
                if (i2 != 3) {
                    i2++;
                } else if (AbstractRSSReader.isFinderPattern(decodeFinderCounters)) {
                    return new int[]{i3, i};
                } else {
                    i3 += decodeFinderCounters[0] + decodeFinderCounters[1];
                    decodeFinderCounters[0] = decodeFinderCounters[2];
                    decodeFinderCounters[1] = decodeFinderCounters[3];
                    decodeFinderCounters[2] = 0;
                    decodeFinderCounters[3] = 0;
                    i2--;
                }
                decodeFinderCounters[i2] = 1;
                z2 = !z2;
            }
            i++;
        }
        throw NotFoundException.getNotFoundInstance();
    }

    private FinderPattern parseFoundFinderPattern(BitArray bitArray, int i, boolean z, int[] iArr) throws NotFoundException {
        int i2;
        int i3;
        boolean z2 = bitArray.get(iArr[0]);
        int i4 = iArr[0] - 1;
        while (i4 >= 0 && z2 != bitArray.get(i4)) {
            i4--;
        }
        int i5 = i4 + 1;
        int[] decodeFinderCounters = getDecodeFinderCounters();
        System.arraycopy(decodeFinderCounters, 0, decodeFinderCounters, 1, decodeFinderCounters.length - 1);
        decodeFinderCounters[0] = iArr[0] - i5;
        int parseFinderValue = AbstractRSSReader.parseFinderValue(decodeFinderCounters, FINDER_PATTERNS);
        int i6 = iArr[1];
        if (z) {
            i2 = (bitArray.getSize() - 1) - i6;
            i3 = (bitArray.getSize() - 1) - i5;
        } else {
            i2 = i6;
            i3 = i5;
        }
        return new FinderPattern(parseFinderValue, new int[]{i5, iArr[1]}, i3, i2, i);
    }

    @Override // com.google.zxing.oned.OneDReader
    public Result decodeRow(int i, BitArray bitArray, Map<DecodeHintType, ?> map) throws NotFoundException {
        addOrTally(this.possibleLeftPairs, decodePair(bitArray, false, i, map));
        bitArray.reverse();
        addOrTally(this.possibleRightPairs, decodePair(bitArray, true, i, map));
        bitArray.reverse();
        for (Pair pair : this.possibleLeftPairs) {
            if (pair.getCount() > 1) {
                for (Pair pair2 : this.possibleRightPairs) {
                    if (pair2.getCount() > 1 && checkChecksum(pair, pair2)) {
                        return constructResult(pair, pair2);
                    }
                }
                continue;
            }
        }
        throw NotFoundException.getNotFoundInstance();
    }

    @Override // com.google.zxing.Reader, com.google.zxing.oned.OneDReader
    public void reset() {
        this.possibleLeftPairs.clear();
        this.possibleRightPairs.clear();
    }
}
