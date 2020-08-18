package com.google.zxing.common;

import com.google.zxing.DecodeHintType;
import java.nio.charset.Charset;
import java.util.Map;

public final class StringUtils {
    private static final boolean ASSUME_SHIFT_JIS = (SHIFT_JIS.equalsIgnoreCase(PLATFORM_DEFAULT_ENCODING) || EUC_JP.equalsIgnoreCase(PLATFORM_DEFAULT_ENCODING));
    private static final String EUC_JP = "EUC_JP";
    public static final String GB2312 = "GB2312";
    private static final String ISO88591 = "ISO8859_1";
    private static final String PLATFORM_DEFAULT_ENCODING = Charset.defaultCharset().name();
    public static final String SHIFT_JIS = "SJIS";
    private static final String UTF8 = "UTF8";

    private StringUtils() {
    }

    public static String guessEncoding(byte[] bArr, Map<DecodeHintType, ?> map) {
        byte[] bArr2 = bArr;
        if (map != null && map.containsKey(DecodeHintType.CHARACTER_SET)) {
            return map.get(DecodeHintType.CHARACTER_SET).toString();
        }
        int length = bArr2.length;
        int i = 0;
        boolean z = bArr2.length > 3 && bArr2[0] == -17 && bArr2[1] == -69 && bArr2[2] == -65;
        boolean z2 = true;
        boolean z3 = true;
        boolean z4 = true;
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        int i6 = 0;
        int i7 = 0;
        int i8 = 0;
        int i9 = 0;
        int i10 = 0;
        int i11 = 0;
        int i12 = 0;
        while (i3 < length && (z2 || z3 || z4)) {
            byte b2 = bArr2[i3] & 255;
            if (z4) {
                if (i4 > 0) {
                    if ((b2 & 128) != 0) {
                        i4--;
                    }
                } else if ((b2 & 128) != 0) {
                    if ((b2 & 64) != 0) {
                        i4++;
                        if ((b2 & 32) == 0) {
                            i6++;
                        } else {
                            i4++;
                            if ((b2 & 16) == 0) {
                                i7++;
                            } else {
                                i4++;
                                if ((b2 & 8) == 0) {
                                    i8++;
                                }
                            }
                        }
                    }
                }
                z4 = false;
            }
            if (z2) {
                if (b2 > Byte.MAX_VALUE && b2 < 160) {
                    z2 = false;
                } else if (b2 > 159 && (b2 < 192 || b2 == 215 || b2 == 247)) {
                    i10++;
                }
            }
            if (z3) {
                if (i5 > 0) {
                    if (b2 >= 64 && b2 != Byte.MAX_VALUE && b2 <= 252) {
                        i5--;
                    }
                } else if (!(b2 == 128 || b2 == 160 || b2 > 239)) {
                    if (b2 <= 160 || b2 >= 224) {
                        if (b2 > Byte.MAX_VALUE) {
                            i5++;
                            int i13 = i11 + 1;
                            if (i13 > i) {
                                i = i13;
                                i11 = i;
                            } else {
                                i11 = i13;
                            }
                        } else {
                            i11 = 0;
                        }
                        i12 = 0;
                    } else {
                        i2++;
                        int i14 = i12 + 1;
                        if (i14 > i9) {
                            i9 = i14;
                            i12 = i9;
                        } else {
                            i12 = i14;
                        }
                        i11 = 0;
                    }
                }
                z3 = false;
            }
            i3++;
            bArr2 = bArr;
        }
        if (z4 && i4 > 0) {
            z4 = false;
        }
        if (z3 && i5 > 0) {
            z3 = false;
        }
        return (!z4 || (!z && (i6 + i7) + i8 <= 0)) ? (!z3 || (!ASSUME_SHIFT_JIS && i9 < 3 && i < 3)) ? (!z2 || !z3) ? z2 ? ISO88591 : z3 ? SHIFT_JIS : z4 ? UTF8 : PLATFORM_DEFAULT_ENCODING : (!(i9 == 2 && i2 == 2) && i10 * 10 < length) ? ISO88591 : SHIFT_JIS : SHIFT_JIS : UTF8;
    }
}
