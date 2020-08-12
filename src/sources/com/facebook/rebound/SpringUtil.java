package com.facebook.rebound;

public class SpringUtil {
    public static double clamp(double d2, double d3, double d4) {
        return Math.min(Math.max(d2, d3), d4);
    }

    public static double mapValueFromRangeToRange(double d2, double d3, double d4, double d5, double d6) {
        return d5 + (((d2 - d3) / (d4 - d3)) * (d6 - d5));
    }
}
