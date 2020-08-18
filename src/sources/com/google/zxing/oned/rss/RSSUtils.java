package com.google.zxing.oned.rss;

public final class RSSUtils {
    private RSSUtils() {
    }

    private static int combins(int i, int i2) {
        int i3 = i - i2;
        if (i3 > i2) {
            i3 = i2;
            i2 = i3;
        }
        int i4 = 1;
        int i5 = 1;
        while (i > i2) {
            i4 *= i;
            if (i5 <= i3) {
                i4 /= i5;
                i5++;
            }
            i--;
        }
        while (i5 <= i3) {
            i4 /= i5;
            i5++;
        }
        return i4;
    }

    public static int getRSSvalue(int[] iArr, int i, boolean z) {
        int[] iArr2 = iArr;
        int i2 = 0;
        for (int i3 : iArr2) {
            i2 += i3;
        }
        int length = iArr2.length;
        int i4 = i2;
        int i5 = 0;
        int i6 = 0;
        int i7 = 0;
        while (true) {
            int i8 = length - 1;
            if (i5 >= i8) {
                return i6;
            }
            int i9 = 1 << i5;
            int i10 = i7 | i9;
            int i11 = i6;
            int i12 = 1;
            while (i12 < iArr2[i5]) {
                int i13 = i4 - i12;
                int i14 = length - i5;
                int i15 = i14 - 2;
                int combins = combins(i13 - 1, i15);
                if (z && i10 == 0) {
                    int i16 = i14 - 1;
                    if (i13 - i16 >= i16) {
                        combins -= combins(i13 - i14, i15);
                    }
                }
                if (i14 - 1 > 1) {
                    int i17 = 0;
                    for (int i18 = i13 - i15; i18 > i; i18--) {
                        i17 += combins((i13 - i18) - 1, i14 - 3);
                    }
                    combins -= i17 * (i8 - i5);
                } else if (i13 > i) {
                    combins--;
                }
                i11 += combins;
                i12++;
                i10 &= ~i9;
                iArr2 = iArr;
            }
            i4 -= i12;
            i5++;
            iArr2 = iArr;
            i6 = i11;
            i7 = i10;
        }
    }
}
