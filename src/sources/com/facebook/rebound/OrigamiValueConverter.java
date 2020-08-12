package com.facebook.rebound;

public class OrigamiValueConverter {
    public static double frictionFromOrigamiValue(double d2) {
        if (d2 == 0.0d) {
            return 0.0d;
        }
        return 25.0d + ((d2 - 8.0d) * 3.0d);
    }

    public static double origamiValueFromFriction(double d2) {
        if (d2 == 0.0d) {
            return 0.0d;
        }
        return 8.0d + ((d2 - 25.0d) / 3.0d);
    }

    public static double origamiValueFromTension(double d2) {
        if (d2 == 0.0d) {
            return 0.0d;
        }
        return 30.0d + ((d2 - 194.0d) / 3.62d);
    }

    public static double tensionFromOrigamiValue(double d2) {
        if (d2 == 0.0d) {
            return 0.0d;
        }
        return 194.0d + ((d2 - 30.0d) * 3.62d);
    }
}
