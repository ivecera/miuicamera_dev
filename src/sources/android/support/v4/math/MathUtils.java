package android.support.v4.math;

public class MathUtils {
    private MathUtils() {
    }

    public static double clamp(double d2, double d3, double d4) {
        return d2 < d3 ? d3 : d2 > d4 ? d4 : d2;
    }

    public static float clamp(float f2, float f3, float f4) {
        return f2 < f3 ? f3 : f2 > f4 ? f4 : f2;
    }

    public static int clamp(int i, int i2, int i3) {
        return i < i2 ? i2 : i > i3 ? i3 : i;
    }
}
