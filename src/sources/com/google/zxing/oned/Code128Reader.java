package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitArray;
import java.util.ArrayList;
import java.util.Map;

public final class Code128Reader extends OneDReader {
    private static final int CODE_CODE_A = 101;
    private static final int CODE_CODE_B = 100;
    private static final int CODE_CODE_C = 99;
    private static final int CODE_FNC_1 = 102;
    private static final int CODE_FNC_2 = 97;
    private static final int CODE_FNC_3 = 96;
    private static final int CODE_FNC_4_A = 101;
    private static final int CODE_FNC_4_B = 100;
    static final int[][] CODE_PATTERNS = {new int[]{2, 1, 2, 2, 2, 2}, new int[]{2, 2, 2, 1, 2, 2}, new int[]{2, 2, 2, 2, 2, 1}, new int[]{1, 2, 1, 2, 2, 3}, new int[]{1, 2, 1, 3, 2, 2}, new int[]{1, 3, 1, 2, 2, 2}, new int[]{1, 2, 2, 2, 1, 3}, new int[]{1, 2, 2, 3, 1, 2}, new int[]{1, 3, 2, 2, 1, 2}, new int[]{2, 2, 1, 2, 1, 3}, new int[]{2, 2, 1, 3, 1, 2}, new int[]{2, 3, 1, 2, 1, 2}, new int[]{1, 1, 2, 2, 3, 2}, new int[]{1, 2, 2, 1, 3, 2}, new int[]{1, 2, 2, 2, 3, 1}, new int[]{1, 1, 3, 2, 2, 2}, new int[]{1, 2, 3, 1, 2, 2}, new int[]{1, 2, 3, 2, 2, 1}, new int[]{2, 2, 3, 2, 1, 1}, new int[]{2, 2, 1, 1, 3, 2}, new int[]{2, 2, 1, 2, 3, 1}, new int[]{2, 1, 3, 2, 1, 2}, new int[]{2, 2, 3, 1, 1, 2}, new int[]{3, 1, 2, 1, 3, 1}, new int[]{3, 1, 1, 2, 2, 2}, new int[]{3, 2, 1, 1, 2, 2}, new int[]{3, 2, 1, 2, 2, 1}, new int[]{3, 1, 2, 2, 1, 2}, new int[]{3, 2, 2, 1, 1, 2}, new int[]{3, 2, 2, 2, 1, 1}, new int[]{2, 1, 2, 1, 2, 3}, new int[]{2, 1, 2, 3, 2, 1}, new int[]{2, 3, 2, 1, 2, 1}, new int[]{1, 1, 1, 3, 2, 3}, new int[]{1, 3, 1, 1, 2, 3}, new int[]{1, 3, 1, 3, 2, 1}, new int[]{1, 1, 2, 3, 1, 3}, new int[]{1, 3, 2, 1, 1, 3}, new int[]{1, 3, 2, 3, 1, 1}, new int[]{2, 1, 1, 3, 1, 3}, new int[]{2, 3, 1, 1, 1, 3}, new int[]{2, 3, 1, 3, 1, 1}, new int[]{1, 1, 2, 1, 3, 3}, new int[]{1, 1, 2, 3, 3, 1}, new int[]{1, 3, 2, 1, 3, 1}, new int[]{1, 1, 3, 1, 2, 3}, new int[]{1, 1, 3, 3, 2, 1}, new int[]{1, 3, 3, 1, 2, 1}, new int[]{3, 1, 3, 1, 2, 1}, new int[]{2, 1, 1, 3, 3, 1}, new int[]{2, 3, 1, 1, 3, 1}, new int[]{2, 1, 3, 1, 1, 3}, new int[]{2, 1, 3, 3, 1, 1}, new int[]{2, 1, 3, 1, 3, 1}, new int[]{3, 1, 1, 1, 2, 3}, new int[]{3, 1, 1, 3, 2, 1}, new int[]{3, 3, 1, 1, 2, 1}, new int[]{3, 1, 2, 1, 1, 3}, new int[]{3, 1, 2, 3, 1, 1}, new int[]{3, 3, 2, 1, 1, 1}, new int[]{3, 1, 4, 1, 1, 1}, new int[]{2, 2, 1, 4, 1, 1}, new int[]{4, 3, 1, 1, 1, 1}, new int[]{1, 1, 1, 2, 2, 4}, new int[]{1, 1, 1, 4, 2, 2}, new int[]{1, 2, 1, 1, 2, 4}, new int[]{1, 2, 1, 4, 2, 1}, new int[]{1, 4, 1, 1, 2, 2}, new int[]{1, 4, 1, 2, 2, 1}, new int[]{1, 1, 2, 2, 1, 4}, new int[]{1, 1, 2, 4, 1, 2}, new int[]{1, 2, 2, 1, 1, 4}, new int[]{1, 2, 2, 4, 1, 1}, new int[]{1, 4, 2, 1, 1, 2}, new int[]{1, 4, 2, 2, 1, 1}, new int[]{2, 4, 1, 2, 1, 1}, new int[]{2, 2, 1, 1, 1, 4}, new int[]{4, 1, 3, 1, 1, 1}, new int[]{2, 4, 1, 1, 1, 2}, new int[]{1, 3, 4, 1, 1, 1}, new int[]{1, 1, 1, 2, 4, 2}, new int[]{1, 2, 1, 1, 4, 2}, new int[]{1, 2, 1, 2, 4, 1}, new int[]{1, 1, 4, 2, 1, 2}, new int[]{1, 2, 4, 1, 1, 2}, new int[]{1, 2, 4, 2, 1, 1}, new int[]{4, 1, 1, 2, 1, 2}, new int[]{4, 2, 1, 1, 1, 2}, new int[]{4, 2, 1, 2, 1, 1}, new int[]{2, 1, 2, 1, 4, 1}, new int[]{2, 1, 4, 1, 2, 1}, new int[]{4, 1, 2, 1, 2, 1}, new int[]{1, 1, 1, 1, 4, 3}, new int[]{1, 1, 1, 3, 4, 1}, new int[]{1, 3, 1, 1, 4, 1}, new int[]{1, 1, 4, 1, 1, 3}, new int[]{1, 1, 4, 3, 1, 1}, new int[]{4, 1, 1, 1, 1, 3}, new int[]{4, 1, 1, 3, 1, 1}, new int[]{1, 1, 3, 1, 4, 1}, new int[]{1, 1, 4, 1, 3, 1}, new int[]{3, 1, 1, 1, 4, 1}, new int[]{4, 1, 1, 1, 3, 1}, new int[]{2, 1, 1, 4, 1, 2}, new int[]{2, 1, 1, 2, 1, 4}, new int[]{2, 1, 1, 2, 3, 2}, new int[]{2, 3, 3, 1, 1, 1, 2}};
    private static final int CODE_SHIFT = 98;
    private static final int CODE_START_A = 103;
    private static final int CODE_START_B = 104;
    private static final int CODE_START_C = 105;
    private static final int CODE_STOP = 106;
    private static final float MAX_AVG_VARIANCE = 0.25f;
    private static final float MAX_INDIVIDUAL_VARIANCE = 0.7f;

    private static int decodeCode(BitArray bitArray, int[] iArr, int i) throws NotFoundException {
        OneDReader.recordPattern(bitArray, i, iArr);
        float f2 = MAX_AVG_VARIANCE;
        int i2 = -1;
        int i3 = 0;
        while (true) {
            int[][] iArr2 = CODE_PATTERNS;
            if (i3 >= iArr2.length) {
                break;
            }
            float patternMatchVariance = OneDReader.patternMatchVariance(iArr, iArr2[i3], 0.7f);
            if (patternMatchVariance < f2) {
                i2 = i3;
                f2 = patternMatchVariance;
            }
            i3++;
        }
        if (i2 >= 0) {
            return i2;
        }
        throw NotFoundException.getNotFoundInstance();
    }

    private static int[] findStartPattern(BitArray bitArray) throws NotFoundException {
        int size = bitArray.getSize();
        int nextSet = bitArray.getNextSet(0);
        int[] iArr = new int[6];
        boolean z = false;
        int i = 0;
        int i2 = nextSet;
        while (nextSet < size) {
            if (bitArray.get(nextSet) != z) {
                iArr[i] = iArr[i] + 1;
            } else {
                if (i == 5) {
                    float f2 = MAX_AVG_VARIANCE;
                    int i3 = -1;
                    for (int i4 = 103; i4 <= 105; i4++) {
                        float patternMatchVariance = OneDReader.patternMatchVariance(iArr, CODE_PATTERNS[i4], 0.7f);
                        if (patternMatchVariance < f2) {
                            i3 = i4;
                            f2 = patternMatchVariance;
                        }
                    }
                    if (i3 < 0 || !bitArray.isRange(Math.max(0, i2 - ((nextSet - i2) / 2)), i2, false)) {
                        i2 += iArr[0] + iArr[1];
                        int i5 = i - 1;
                        System.arraycopy(iArr, 2, iArr, 0, i5);
                        iArr[i5] = 0;
                        iArr[i] = 0;
                        i--;
                    } else {
                        return new int[]{i2, nextSet, i3};
                    }
                } else {
                    i++;
                }
                iArr[i] = 1;
                z = !z;
            }
            nextSet++;
        }
        throw NotFoundException.getNotFoundInstance();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:51:0x00e2, code lost:
        if (r5 != false) goto L_0x0136;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:75:0x0134, code lost:
        if (r5 != false) goto L_0x0136;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:76:0x0136, code lost:
        r5 = false;
        r10 = 'd';
        r11 = false;
     */
    /* JADX WARNING: Removed duplicated region for block: B:78:0x0140 A[PHI: r19 
      PHI: (r19v13 boolean) = (r19v18 boolean), (r19v20 boolean) binds: [B:64:0x010f, B:42:0x00c2] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARNING: Removed duplicated region for block: B:82:0x014a A[PHI: r6 r19 
      PHI: (r6v12 boolean) = (r6v13 boolean), (r6v2 boolean), (r6v2 boolean), (r6v2 boolean), (r6v2 boolean), (r6v2 boolean), (r6v2 boolean), (r6v2 boolean), (r6v2 boolean) binds: [B:81:0x0149, B:64:0x010f, B:65:0x0113, B:69:0x011f, B:68:0x011b, B:42:0x00c2, B:43:0x00c7, B:47:0x00d4, B:46:0x00cf] A[DONT_GENERATE, DONT_INLINE]
      PHI: (r19v10 boolean) = (r19v11 boolean), (r19v18 boolean), (r19v18 boolean), (r19v18 boolean), (r19v18 boolean), (r19v20 boolean), (r19v20 boolean), (r19v20 boolean), (r19v20 boolean) binds: [B:81:0x0149, B:64:0x010f, B:65:0x0113, B:69:0x011f, B:68:0x011b, B:42:0x00c2, B:43:0x00c7, B:47:0x00d4, B:46:0x00cf] A[DONT_GENERATE, DONT_INLINE]] */
    @Override // com.google.zxing.oned.OneDReader
    public Result decodeRow(int i, BitArray bitArray, Map<DecodeHintType, ?> map) throws NotFoundException, FormatException, ChecksumException {
        char c2;
        boolean z;
        char c3;
        boolean z2;
        boolean z3;
        boolean z4;
        boolean z5;
        boolean z6 = map != null && map.containsKey(DecodeHintType.ASSUME_GS1);
        int[] findStartPattern = findStartPattern(bitArray);
        int i2 = findStartPattern[2];
        ArrayList arrayList = new ArrayList(20);
        arrayList.add(Byte.valueOf((byte) i2));
        switch (i2) {
            case 103:
                c2 = 'e';
                break;
            case 104:
                c2 = 'd';
                break;
            case 105:
                c2 = 'c';
                break;
            default:
                throw FormatException.getFormatInstance();
        }
        StringBuilder sb = new StringBuilder(20);
        int i3 = 6;
        int[] iArr = new int[6];
        boolean z7 = false;
        int i4 = 0;
        boolean z8 = false;
        int i5 = 0;
        int i6 = 0;
        int i7 = i2;
        boolean z9 = true;
        boolean z10 = false;
        char c4 = c2;
        int i8 = findStartPattern[0];
        int i9 = findStartPattern[1];
        boolean z11 = false;
        while (!z10) {
            int decodeCode = decodeCode(bitArray, iArr, i9);
            arrayList.add(Byte.valueOf((byte) decodeCode));
            if (decodeCode != 106) {
                z9 = true;
            }
            if (decodeCode != 106) {
                i6++;
                i7 += i6 * decodeCode;
            }
            int i10 = i9;
            for (int i11 = 0; i11 < i3; i11++) {
                i10 += iArr[i11];
            }
            switch (decodeCode) {
                case 103:
                case 104:
                case 105:
                    throw FormatException.getFormatInstance();
                default:
                    switch (c4) {
                        case 'c':
                            c3 = 'd';
                            if (decodeCode >= 100) {
                                if (decodeCode != 106) {
                                    z9 = false;
                                }
                                if (decodeCode == 106) {
                                    z = z7;
                                    z2 = false;
                                    z10 = true;
                                    break;
                                } else {
                                    switch (decodeCode) {
                                        case 100:
                                            z3 = z7;
                                            c4 = 'd';
                                            break;
                                        case 101:
                                            z3 = z7;
                                            c4 = 'e';
                                            break;
                                        case 102:
                                            if (z6) {
                                                if (sb.length() == 0) {
                                                    sb.append("]C1");
                                                } else {
                                                    sb.append((char) 29);
                                                }
                                            }
                                        default:
                                            z3 = z7;
                                            break;
                                    }
                                    z2 = false;
                                    break;
                                }
                            } else {
                                if (decodeCode < 10) {
                                    sb.append('0');
                                }
                                sb.append(decodeCode);
                            }
                            z3 = z7;
                            z2 = false;
                        case 'd':
                            if (decodeCode < 96) {
                                if (z7 == z8) {
                                    sb.append((char) (decodeCode + 32));
                                } else {
                                    sb.append((char) (decodeCode + 32 + 128));
                                }
                                z2 = false;
                                c3 = 'd';
                                z = false;
                                break;
                            } else {
                                if (decodeCode != 106) {
                                    z9 = false;
                                }
                                if (decodeCode != 106) {
                                    switch (decodeCode) {
                                        case 96:
                                        case 97:
                                        default:
                                            z5 = z7;
                                            z4 = false;
                                            c3 = 'd';
                                            break;
                                        case 98:
                                            z = z7;
                                            c4 = 'e';
                                            z4 = true;
                                            c3 = 'd';
                                            break;
                                        case 99:
                                            z5 = z7;
                                            c4 = 'c';
                                            z4 = false;
                                            c3 = 'd';
                                            break;
                                        case 100:
                                            if (z8 || !z7) {
                                                if (z8) {
                                                }
                                                z2 = false;
                                                c3 = 'd';
                                                z = true;
                                                break;
                                            }
                                            z2 = false;
                                            c3 = 'd';
                                            z8 = true;
                                            z = false;
                                            break;
                                        case 101:
                                            z5 = z7;
                                            c4 = 'e';
                                            z4 = false;
                                            c3 = 'd';
                                            break;
                                        case 102:
                                            if (z6) {
                                                if (sb.length() == 0) {
                                                    sb.append("]C1");
                                                } else {
                                                    sb.append((char) 29);
                                                }
                                            }
                                            z5 = z7;
                                            z4 = false;
                                            c3 = 'd';
                                            break;
                                    }
                                }
                                z10 = true;
                                z5 = z7;
                                z4 = false;
                                c3 = 'd';
                            }
                            break;
                        case 'e':
                            if (decodeCode < 64) {
                                if (z7 == z8) {
                                    sb.append((char) (decodeCode + 32));
                                } else {
                                    sb.append((char) (decodeCode + 32 + 128));
                                }
                            } else if (decodeCode >= 96) {
                                if (decodeCode != 106) {
                                    z9 = false;
                                }
                                if (decodeCode != 106) {
                                    switch (decodeCode) {
                                        case 98:
                                            z = z7;
                                            c4 = 'd';
                                            z4 = true;
                                            c3 = 'd';
                                            break;
                                        case 100:
                                            z5 = z7;
                                            c4 = 'd';
                                            z4 = false;
                                            c3 = 'd';
                                            break;
                                        case 101:
                                            if (z8 || !z7) {
                                                if (z8) {
                                                }
                                                z2 = false;
                                                c3 = 'd';
                                                z = true;
                                                break;
                                            }
                                            z2 = false;
                                            c3 = 'd';
                                            z8 = true;
                                            z = false;
                                            break;
                                        case 102:
                                            if (z6) {
                                                if (sb.length() == 0) {
                                                    sb.append("]C1");
                                                } else {
                                                    sb.append((char) 29);
                                                }
                                            }
                                            z5 = z7;
                                            z4 = false;
                                            c3 = 'd';
                                            break;
                                    }
                                }
                                z10 = true;
                                z5 = z7;
                                z4 = false;
                                c3 = 'd';
                            } else if (z7 == z8) {
                                sb.append((char) (decodeCode - 64));
                            } else {
                                sb.append((char) (decodeCode + 64));
                            }
                            z2 = false;
                            c3 = 'd';
                            z = false;
                            break;
                        default:
                            c3 = 'd';
                            z3 = z7;
                            z2 = false;
                            break;
                    }
                    if (z11) {
                        c4 = c4 == 'e' ? c3 : 'e';
                    }
                    z11 = z2;
                    z7 = z;
                    i3 = 6;
                    i8 = i9;
                    i9 = i10;
                    i5 = decodeCode;
                    i4 = i5;
                    break;
            }
        }
        int i12 = i9 - i8;
        int nextUnset = bitArray.getNextUnset(i9);
        if (!bitArray.isRange(nextUnset, Math.min(bitArray.getSize(), ((nextUnset - i8) / 2) + nextUnset), false)) {
            throw NotFoundException.getNotFoundInstance();
        } else if ((i7 - (i6 * i4)) % 103 == i4) {
            int length = sb.length();
            if (length != 0) {
                if (length > 0 && z9) {
                    if (c4 == 'c') {
                        sb.delete(length - 2, length);
                    } else {
                        sb.delete(length - 1, length);
                    }
                }
                float f2 = ((float) (findStartPattern[1] + findStartPattern[0])) / 2.0f;
                float f3 = ((float) i8) + (((float) i12) / 2.0f);
                int size = arrayList.size();
                byte[] bArr = new byte[size];
                for (int i13 = 0; i13 < size; i13++) {
                    bArr[i13] = ((Byte) arrayList.get(i13)).byteValue();
                }
                float f4 = (float) i;
                return new Result(sb.toString(), bArr, new ResultPoint[]{new ResultPoint(f2, f4), new ResultPoint(f3, f4)}, BarcodeFormat.CODE_128);
            }
            throw NotFoundException.getNotFoundInstance();
        } else {
            throw ChecksumException.getChecksumInstance();
        }
    }
}
