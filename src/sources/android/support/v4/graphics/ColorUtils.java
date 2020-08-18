package android.support.v4.graphics;

import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.annotation.FloatRange;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.annotation.VisibleForTesting;
import android.support.v4.view.ViewCompat;
import java.util.Objects;

public final class ColorUtils {
    private static final int MIN_ALPHA_SEARCH_MAX_ITERATIONS = 10;
    private static final int MIN_ALPHA_SEARCH_PRECISION = 1;
    private static final ThreadLocal<double[]> TEMP_ARRAY = new ThreadLocal<>();
    private static final double XYZ_EPSILON = 0.008856d;
    private static final double XYZ_KAPPA = 903.3d;
    private static final double XYZ_WHITE_REFERENCE_X = 95.047d;
    private static final double XYZ_WHITE_REFERENCE_Y = 100.0d;
    private static final double XYZ_WHITE_REFERENCE_Z = 108.883d;

    private ColorUtils() {
    }

    @ColorInt
    public static int HSLToColor(@NonNull float[] fArr) {
        int i;
        int i2;
        int i3;
        float f2 = fArr[0];
        float f3 = fArr[1];
        float f4 = fArr[2];
        float abs = (1.0f - Math.abs((f4 * 2.0f) - 1.0f)) * f3;
        float f5 = f4 - (0.5f * abs);
        float abs2 = (1.0f - Math.abs(((f2 / 60.0f) % 2.0f) - 1.0f)) * abs;
        switch (((int) f2) / 60) {
            case 0:
                i3 = Math.round((abs + f5) * 255.0f);
                i2 = Math.round((abs2 + f5) * 255.0f);
                i = Math.round(f5 * 255.0f);
                break;
            case 1:
                i3 = Math.round((abs2 + f5) * 255.0f);
                i2 = Math.round((abs + f5) * 255.0f);
                i = Math.round(f5 * 255.0f);
                break;
            case 2:
                i3 = Math.round(f5 * 255.0f);
                i2 = Math.round((abs + f5) * 255.0f);
                i = Math.round((abs2 + f5) * 255.0f);
                break;
            case 3:
                i3 = Math.round(f5 * 255.0f);
                i2 = Math.round((abs2 + f5) * 255.0f);
                i = Math.round((abs + f5) * 255.0f);
                break;
            case 4:
                i3 = Math.round((abs2 + f5) * 255.0f);
                i2 = Math.round(f5 * 255.0f);
                i = Math.round((abs + f5) * 255.0f);
                break;
            case 5:
            case 6:
                i3 = Math.round((abs + f5) * 255.0f);
                i2 = Math.round(f5 * 255.0f);
                i = Math.round((abs2 + f5) * 255.0f);
                break;
            default:
                i = 0;
                i3 = 0;
                i2 = 0;
                break;
        }
        return Color.rgb(constrain(i3, 0, 255), constrain(i2, 0, 255), constrain(i, 0, 255));
    }

    @ColorInt
    public static int LABToColor(@FloatRange(from = 0.0d, to = 100.0d) double d2, @FloatRange(from = -128.0d, to = 127.0d) double d3, @FloatRange(from = -128.0d, to = 127.0d) double d4) {
        double[] tempDouble3Array = getTempDouble3Array();
        LABToXYZ(d2, d3, d4, tempDouble3Array);
        return XYZToColor(tempDouble3Array[0], tempDouble3Array[1], tempDouble3Array[2]);
    }

    public static void LABToXYZ(@FloatRange(from = 0.0d, to = 100.0d) double d2, @FloatRange(from = -128.0d, to = 127.0d) double d3, @FloatRange(from = -128.0d, to = 127.0d) double d4, @NonNull double[] dArr) {
        double d5 = (d2 + 16.0d) / 116.0d;
        double d6 = (d3 / 500.0d) + d5;
        double d7 = d5 - (d4 / 200.0d);
        double pow = Math.pow(d6, 3.0d);
        if (pow <= XYZ_EPSILON) {
            pow = ((d6 * 116.0d) - 16.0d) / XYZ_KAPPA;
        }
        double pow2 = d2 > 7.9996247999999985d ? Math.pow(d5, 3.0d) : d2 / XYZ_KAPPA;
        double pow3 = Math.pow(d7, 3.0d);
        if (pow3 <= XYZ_EPSILON) {
            pow3 = ((d7 * 116.0d) - 16.0d) / XYZ_KAPPA;
        }
        dArr[0] = pow * XYZ_WHITE_REFERENCE_X;
        dArr[1] = pow2 * XYZ_WHITE_REFERENCE_Y;
        dArr[2] = pow3 * XYZ_WHITE_REFERENCE_Z;
    }

    public static void RGBToHSL(@IntRange(from = 0, to = 255) int i, @IntRange(from = 0, to = 255) int i2, @IntRange(from = 0, to = 255) int i3, @NonNull float[] fArr) {
        float f2;
        float f3;
        float f4 = ((float) i) / 255.0f;
        float f5 = ((float) i2) / 255.0f;
        float f6 = ((float) i3) / 255.0f;
        float max = Math.max(f4, Math.max(f5, f6));
        float min = Math.min(f4, Math.min(f5, f6));
        float f7 = max - min;
        float f8 = (max + min) / 2.0f;
        if (max == min) {
            f3 = 0.0f;
            f2 = 0.0f;
        } else {
            f3 = max == f4 ? ((f5 - f6) / f7) % 6.0f : max == f5 ? ((f6 - f4) / f7) + 2.0f : ((f4 - f5) / f7) + 4.0f;
            f2 = f7 / (1.0f - Math.abs((2.0f * f8) - 1.0f));
        }
        float f9 = (f3 * 60.0f) % 360.0f;
        if (f9 < 0.0f) {
            f9 += 360.0f;
        }
        fArr[0] = constrain(f9, 0.0f, 360.0f);
        fArr[1] = constrain(f2, 0.0f, 1.0f);
        fArr[2] = constrain(f8, 0.0f, 1.0f);
    }

    public static void RGBToLAB(@IntRange(from = 0, to = 255) int i, @IntRange(from = 0, to = 255) int i2, @IntRange(from = 0, to = 255) int i3, @NonNull double[] dArr) {
        RGBToXYZ(i, i2, i3, dArr);
        XYZToLAB(dArr[0], dArr[1], dArr[2], dArr);
    }

    public static void RGBToXYZ(@IntRange(from = 0, to = 255) int i, @IntRange(from = 0, to = 255) int i2, @IntRange(from = 0, to = 255) int i3, @NonNull double[] dArr) {
        if (dArr.length == 3) {
            double d2 = ((double) i) / 255.0d;
            double pow = d2 < 0.04045d ? d2 / 12.92d : Math.pow((d2 + 0.055d) / 1.055d, 2.4d);
            double d3 = ((double) i2) / 255.0d;
            double pow2 = d3 < 0.04045d ? d3 / 12.92d : Math.pow((d3 + 0.055d) / 1.055d, 2.4d);
            double d4 = ((double) i3) / 255.0d;
            double pow3 = d4 < 0.04045d ? d4 / 12.92d : Math.pow((d4 + 0.055d) / 1.055d, 2.4d);
            dArr[0] = ((0.4124d * pow) + (0.3576d * pow2) + (0.1805d * pow3)) * XYZ_WHITE_REFERENCE_Y;
            dArr[1] = ((0.2126d * pow) + (0.7152d * pow2) + (0.0722d * pow3)) * XYZ_WHITE_REFERENCE_Y;
            dArr[2] = ((pow * 0.0193d) + (pow2 * 0.1192d) + (pow3 * 0.9505d)) * XYZ_WHITE_REFERENCE_Y;
            return;
        }
        throw new IllegalArgumentException("outXyz must have a length of 3.");
    }

    @ColorInt
    public static int XYZToColor(@FloatRange(from = 0.0d, to = 95.047d) double d2, @FloatRange(from = 0.0d, to = 100.0d) double d3, @FloatRange(from = 0.0d, to = 108.883d) double d4) {
        double d5 = (((3.2406d * d2) + (-1.5372d * d3)) + (-0.4986d * d4)) / XYZ_WHITE_REFERENCE_Y;
        double d6 = (((-0.9689d * d2) + (1.8758d * d3)) + (0.0415d * d4)) / XYZ_WHITE_REFERENCE_Y;
        double d7 = (((0.0557d * d2) + (-0.204d * d3)) + (1.057d * d4)) / XYZ_WHITE_REFERENCE_Y;
        return Color.rgb(constrain((int) Math.round((d5 > 0.0031308d ? (Math.pow(d5, 0.4166666666666667d) * 1.055d) - 0.055d : d5 * 12.92d) * 255.0d), 0, 255), constrain((int) Math.round((d6 > 0.0031308d ? (Math.pow(d6, 0.4166666666666667d) * 1.055d) - 0.055d : d6 * 12.92d) * 255.0d), 0, 255), constrain((int) Math.round((d7 > 0.0031308d ? (Math.pow(d7, 0.4166666666666667d) * 1.055d) - 0.055d : d7 * 12.92d) * 255.0d), 0, 255));
    }

    public static void XYZToLAB(@FloatRange(from = 0.0d, to = 95.047d) double d2, @FloatRange(from = 0.0d, to = 100.0d) double d3, @FloatRange(from = 0.0d, to = 108.883d) double d4, @NonNull double[] dArr) {
        if (dArr.length == 3) {
            double pivotXyzComponent = pivotXyzComponent(d2 / XYZ_WHITE_REFERENCE_X);
            double pivotXyzComponent2 = pivotXyzComponent(d3 / XYZ_WHITE_REFERENCE_Y);
            double pivotXyzComponent3 = pivotXyzComponent(d4 / XYZ_WHITE_REFERENCE_Z);
            dArr[0] = Math.max(0.0d, (116.0d * pivotXyzComponent2) - 16.0d);
            dArr[1] = (pivotXyzComponent - pivotXyzComponent2) * 500.0d;
            dArr[2] = (pivotXyzComponent2 - pivotXyzComponent3) * 200.0d;
            return;
        }
        throw new IllegalArgumentException("outLab must have a length of 3.");
    }

    @ColorInt
    public static int blendARGB(@ColorInt int i, @ColorInt int i2, @FloatRange(from = 0.0d, to = 1.0d) float f2) {
        float f3 = 1.0f - f2;
        return Color.argb((int) ((((float) Color.alpha(i)) * f3) + (((float) Color.alpha(i2)) * f2)), (int) ((((float) Color.red(i)) * f3) + (((float) Color.red(i2)) * f2)), (int) ((((float) Color.green(i)) * f3) + (((float) Color.green(i2)) * f2)), (int) ((((float) Color.blue(i)) * f3) + (((float) Color.blue(i2)) * f2)));
    }

    public static void blendHSL(@NonNull float[] fArr, @NonNull float[] fArr2, @FloatRange(from = 0.0d, to = 1.0d) float f2, @NonNull float[] fArr3) {
        if (fArr3.length == 3) {
            float f3 = 1.0f - f2;
            fArr3[0] = circularInterpolate(fArr[0], fArr2[0], f2);
            fArr3[1] = (fArr[1] * f3) + (fArr2[1] * f2);
            fArr3[2] = (fArr[2] * f3) + (fArr2[2] * f2);
            return;
        }
        throw new IllegalArgumentException("result must have a length of 3.");
    }

    public static void blendLAB(@NonNull double[] dArr, @NonNull double[] dArr2, @FloatRange(from = 0.0d, to = 1.0d) double d2, @NonNull double[] dArr3) {
        if (dArr3.length == 3) {
            double d3 = 1.0d - d2;
            dArr3[0] = (dArr[0] * d3) + (dArr2[0] * d2);
            dArr3[1] = (dArr[1] * d3) + (dArr2[1] * d2);
            dArr3[2] = (dArr[2] * d3) + (dArr2[2] * d2);
            return;
        }
        throw new IllegalArgumentException("outResult must have a length of 3.");
    }

    public static double calculateContrast(@ColorInt int i, @ColorInt int i2) {
        if (Color.alpha(i2) == 255) {
            if (Color.alpha(i) < 255) {
                i = compositeColors(i, i2);
            }
            double calculateLuminance = calculateLuminance(i) + 0.05d;
            double calculateLuminance2 = calculateLuminance(i2) + 0.05d;
            return Math.max(calculateLuminance, calculateLuminance2) / Math.min(calculateLuminance, calculateLuminance2);
        }
        throw new IllegalArgumentException("background can not be translucent: #" + Integer.toHexString(i2));
    }

    @FloatRange(from = 0.0d, to = 1.0d)
    public static double calculateLuminance(@ColorInt int i) {
        double[] tempDouble3Array = getTempDouble3Array();
        colorToXYZ(i, tempDouble3Array);
        return tempDouble3Array[1] / XYZ_WHITE_REFERENCE_Y;
    }

    public static int calculateMinimumAlpha(@ColorInt int i, @ColorInt int i2, float f2) {
        int i3 = 255;
        if (Color.alpha(i2) == 255) {
            double d2 = (double) f2;
            if (calculateContrast(setAlphaComponent(i, 255), i2) < d2) {
                return -1;
            }
            int i4 = 0;
            for (int i5 = 0; i5 <= 10 && i3 - i4 > 1; i5++) {
                int i6 = (i4 + i3) / 2;
                if (calculateContrast(setAlphaComponent(i, i6), i2) < d2) {
                    i4 = i6;
                } else {
                    i3 = i6;
                }
            }
            return i3;
        }
        throw new IllegalArgumentException("background can not be translucent: #" + Integer.toHexString(i2));
    }

    @VisibleForTesting
    static float circularInterpolate(float f2, float f3, float f4) {
        if (Math.abs(f3 - f2) > 180.0f) {
            if (f3 > f2) {
                f2 += 360.0f;
            } else {
                f3 += 360.0f;
            }
        }
        return (f2 + ((f3 - f2) * f4)) % 360.0f;
    }

    public static void colorToHSL(@ColorInt int i, @NonNull float[] fArr) {
        RGBToHSL(Color.red(i), Color.green(i), Color.blue(i), fArr);
    }

    public static void colorToLAB(@ColorInt int i, @NonNull double[] dArr) {
        RGBToLAB(Color.red(i), Color.green(i), Color.blue(i), dArr);
    }

    public static void colorToXYZ(@ColorInt int i, @NonNull double[] dArr) {
        RGBToXYZ(Color.red(i), Color.green(i), Color.blue(i), dArr);
    }

    private static int compositeAlpha(int i, int i2) {
        return 255 - (((255 - i2) * (255 - i)) / 255);
    }

    public static int compositeColors(@ColorInt int i, @ColorInt int i2) {
        int alpha = Color.alpha(i2);
        int alpha2 = Color.alpha(i);
        int compositeAlpha = compositeAlpha(alpha2, alpha);
        return Color.argb(compositeAlpha, compositeComponent(Color.red(i), alpha2, Color.red(i2), alpha, compositeAlpha), compositeComponent(Color.green(i), alpha2, Color.green(i2), alpha, compositeAlpha), compositeComponent(Color.blue(i), alpha2, Color.blue(i2), alpha, compositeAlpha));
    }

    @NonNull
    @RequiresApi(26)
    public static Color compositeColors(@NonNull Color color, @NonNull Color color2) {
        if (Objects.equals(color.getModel(), color2.getModel())) {
            if (!Objects.equals(color2.getColorSpace(), color.getColorSpace())) {
                color = color.convert(color2.getColorSpace());
            }
            float[] components = color.getComponents();
            float[] components2 = color2.getComponents();
            float alpha = color.alpha();
            float alpha2 = color2.alpha() * (1.0f - alpha);
            int componentCount = color2.getComponentCount() - 1;
            components2[componentCount] = alpha + alpha2;
            if (components2[componentCount] > 0.0f) {
                alpha /= components2[componentCount];
                alpha2 /= components2[componentCount];
            }
            for (int i = 0; i < componentCount; i++) {
                components2[i] = (components[i] * alpha) + (components2[i] * alpha2);
            }
            return Color.valueOf(components2, color2.getColorSpace());
        }
        throw new IllegalArgumentException("Color models must match (" + color.getModel() + " vs. " + color2.getModel() + ")");
    }

    private static int compositeComponent(int i, int i2, int i3, int i4, int i5) {
        if (i5 == 0) {
            return 0;
        }
        return (((i * 255) * i2) + ((i3 * i4) * (255 - i2))) / (i5 * 255);
    }

    private static float constrain(float f2, float f3, float f4) {
        return f2 < f3 ? f3 : f2 > f4 ? f4 : f2;
    }

    private static int constrain(int i, int i2, int i3) {
        return i < i2 ? i2 : i > i3 ? i3 : i;
    }

    public static double distanceEuclidean(@NonNull double[] dArr, @NonNull double[] dArr2) {
        return Math.sqrt(Math.pow(dArr[0] - dArr2[0], 2.0d) + Math.pow(dArr[1] - dArr2[1], 2.0d) + Math.pow(dArr[2] - dArr2[2], 2.0d));
    }

    private static double[] getTempDouble3Array() {
        double[] dArr = TEMP_ARRAY.get();
        if (dArr != null) {
            return dArr;
        }
        double[] dArr2 = new double[3];
        TEMP_ARRAY.set(dArr2);
        return dArr2;
    }

    private static double pivotXyzComponent(double d2) {
        return d2 > XYZ_EPSILON ? Math.pow(d2, 0.3333333333333333d) : ((d2 * XYZ_KAPPA) + 16.0d) / 116.0d;
    }

    @ColorInt
    public static int setAlphaComponent(@ColorInt int i, @IntRange(from = 0, to = 255) int i2) {
        if (i2 >= 0 && i2 <= 255) {
            return (i & ViewCompat.MEASURED_SIZE_MASK) | (i2 << 24);
        }
        throw new IllegalArgumentException("alpha must be between 0 and 255.");
    }
}
