package com.google.zxing.common.detector;

public final class MathUtils {
    private MathUtils() {
    }

    public static float distance(float f2, float f3, float f4, float f5) {
        double d2 = (double) (f2 - f4);
        double d3 = (double) (f3 - f5);
        return (float) Math.sqrt((d2 * d2) + (d3 * d3));
    }

    public static float distance(int i, int i2, int i3, int i4) {
        double d2 = (double) (i - i3);
        double d3 = (double) (i2 - i4);
        return (float) Math.sqrt((d2 * d2) + (d3 * d3));
    }

    public static int round(float f2) {
        return (int) (f2 + (f2 < 0.0f ? -0.5f : 0.5f));
    }

    public static int sum(int[] iArr) {
        int i = 0;
        for (int i2 : iArr) {
            i += i2;
        }
        return i;
    }
}
