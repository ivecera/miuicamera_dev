package com.android.camera;

import android.graphics.Rect;
import com.android.camera.data.DataRepository;
import com.android.camera.log.Log;
import com.mi.config.b;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class HybridZoomingSystem {
    private static final String DEFAULT_OPTICAL_ZOOM_RATIO_COMBINATION = (b.isSupportedOpticalZoom() ? "1.0:2.0" : "1.0");
    public static final float FLOAT_MICRO_SCENE_ZOOM_MAX = 1.0f;
    public static final float FLOAT_MOON_MODE_ZOOM_MAX = 20.0f;
    public static final float FLOAT_MOON_MODE_ZOOM_MIN = 1.0f;
    public static final float FLOAT_STEP_FOR_ZOOM_RATIO_CHANGE = 0.1f;
    public static final float FLOAT_ULTRA_WIDE_ZOOM_MAX = 6.0f;
    public static final float FLOAT_VIDEO_SAT_ZOOM_RATIO = 12.0f;
    public static final float FLOAT_ZOOM_RATIO_10X = 10.0f;
    public static final float FLOAT_ZOOM_RATIO_2X = 2.0f;
    public static final float FLOAT_ZOOM_RATIO_30X = 30.0f;
    public static final float FLOAT_ZOOM_RATIO_3X = 3.0f;
    public static final float FLOAT_ZOOM_RATIO_5X = 5.0f;
    public static final float FLOAT_ZOOM_RATIO_MACRO = 0.0f;
    public static final float FLOAT_ZOOM_RATIO_NONE = 1.0f;
    public static final float FLOAT_ZOOM_RATIO_TELE = (b.lv ? 3.0f : 2.0f);
    public static final float FLOAT_ZOOM_RATIO_ULTR = 0.6f;
    public static final float FLOAT_ZOOM_RATIO_ULTRA_TELE_THRESHHOLD = 3.7f;
    public static final float FLOAT_ZOOM_RATIO_WIDE = 1.0f;
    public static final boolean IS_2_OR_MORE_SAT = OPTICAL_ZOOM_RATIO_COMBINATION.matches("^\\d+\\.\\d+\\s*:\\s*\\d+\\.\\d+(\\s*:\\s*\\d+\\.\\d+)*$");
    public static final boolean IS_2_SAT = OPTICAL_ZOOM_RATIO_COMBINATION.matches("^\\d+\\.\\d+\\s*:\\s*\\d+\\.\\d+$");
    public static final boolean IS_3_OR_MORE_SAT = OPTICAL_ZOOM_RATIO_COMBINATION.matches("^\\d+\\.\\d+\\s*:\\s*\\d+\\.\\d+\\s*:\\s*\\d+\\.\\d+(\\s*:\\s*\\d+\\.\\d+)*$");
    public static final boolean IS_3_SAT = OPTICAL_ZOOM_RATIO_COMBINATION.matches("^\\d+\\.\\d+\\s*:\\s*\\d+\\.\\d+\\s*:\\s*\\d+\\.\\d+$");
    public static final boolean IS_4_OR_MORE_SAT = OPTICAL_ZOOM_RATIO_COMBINATION.matches("^\\d+\\.\\d+\\s*:\\s*\\d+\\.\\d+\\s*:\\s*\\d+\\.\\d+\\s*:\\s*\\d+\\.\\d+(\\s*:\\s*\\d+\\.\\d+)*$");
    public static final boolean IS_4_SAT = OPTICAL_ZOOM_RATIO_COMBINATION.matches("^\\d+\\.\\d+\\s*:\\s*\\d+\\.\\d+\\s*:\\s*\\d+\\.\\d+\\s*:\\s*\\d+\\.\\d+$");
    private static final String MACRO_OPTICAL_ZOOM_RATIO_COMBINATION = DataRepository.dataItemFeature().n(DEFAULT_OPTICAL_ZOOM_RATIO_COMBINATION);
    private static final String OPTICAL_ZOOM_RATIO_COMBINATION = DataRepository.dataItemFeature().o(DEFAULT_OPTICAL_ZOOM_RATIO_COMBINATION);
    public static final String STRING_ZOOM_RATIO_NONE = "1.0";
    public static final String STRING_ZOOM_RATIO_TELE = String.format(Locale.US, "%.1fx", Float.valueOf(FLOAT_ZOOM_RATIO_TELE));
    public static final String STRING_ZOOM_RATIO_TELE_5X = String.format(Locale.US, "%.1fx", Float.valueOf(5.0f));
    public static final String STRING_ZOOM_RATIO_ULTR = String.format(Locale.US, "%.1fx", Float.valueOf(0.6f));
    public static final String STRING_ZOOM_RATIO_WIDE = String.format(Locale.US, "%.1fx", Float.valueOf(1.0f));
    private static final String TAG = "HybridZoomingSystem";
    public static final float TOLERANCE_FOR_ZOOM_RATIO_CHANGED = 0.01f;
    public static float sDefaultMacroOpticalZoomRatio;
    private static int sDefaultOpticalZoomRatioIndex;
    private static float[] sFunOrLiveSupportedOpticalZoomRatios;
    private static int sFunOrLiveSupportedZoomRatioIndex = 1;
    private static float[] sMacroSupportedOpticalZoomRatios;
    private static int sMacroZoomRatioIndex;
    private static float[] sNightSupportedOpticalZoomRatios;
    private static int sNightSupportedZoomRatioIndex;
    private static float[] sPixelSupportedOpticalZoomRatios = {1.0f, 2.0f};
    private static int sPixelSupportedZoomRatioIndex = 0;
    private static float[] sSupportedOpticalZoomRatios;
    private static final Map<Integer, String> sZoomRatioHistory = new ConcurrentHashMap();
    private static final AtomicInteger sZoomingSourceIdentity = new AtomicInteger(0);

    private static /* synthetic */ void $closeResource(Throwable th, AutoCloseable autoCloseable) {
        if (th != null) {
            try {
                autoCloseable.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
        } else {
            autoCloseable.close();
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:58:0x0183, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:59:0x0184, code lost:
        $closeResource(r0, r8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:60:0x0187, code lost:
        throw r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:79:0x0245, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:80:0x0246, code lost:
        $closeResource(r0, r8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:81:0x0249, code lost:
        throw r1;
     */
    static {
        int i;
        int i2 = -1;
        sMacroZoomRatioIndex = -1;
        sFunOrLiveSupportedOpticalZoomRatios = new float[]{0.6f, 1.0f, 2.0f};
        sNightSupportedOpticalZoomRatios = new float[]{0.6f, 1.0f, 2.0f};
        sNightSupportedZoomRatioIndex = 1;
        sDefaultOpticalZoomRatioIndex = -1;
        sDefaultMacroOpticalZoomRatio = 1.0f;
        ArrayList<String> arrayList = new ArrayList();
        Scanner scanner = new Scanner(OPTICAL_ZOOM_RATIO_COMBINATION);
        scanner.useDelimiter("\\s*[:,]\\s*");
        if (DataRepository.dataItemFeature().c_19039_0x0005_eq_2() == 1) {
            sMacroZoomRatioIndex = 0;
            arrayList.add("0");
            i = 0;
        } else {
            i = -1;
        }
        while (scanner.hasNext()) {
            String next = scanner.next();
            if (next != null && next.length() > 0) {
                arrayList.add(next);
                i++;
                if (next.equals("1.0") == 1 && sDefaultOpticalZoomRatioIndex == -1) {
                    sDefaultOpticalZoomRatioIndex = i;
                }
            }
        }
        $closeResource(null, scanner);
        if (sDefaultOpticalZoomRatioIndex < 0 || arrayList.size() < 1) {
            throw new IllegalStateException("The supported optical zoom ratios are probably not configured correctly");
        }
        sSupportedOpticalZoomRatios = new float[arrayList.size()];
        int i3 = 0;
        for (String str : arrayList) {
            sSupportedOpticalZoomRatios[i3] = Float.parseFloat(str);
            i3++;
        }
        Log.d(TAG, Arrays.toString(sSupportedOpticalZoomRatios) + "[" + sDefaultOpticalZoomRatioIndex + "]");
        if (b.lv == 1) {
            sFunOrLiveSupportedOpticalZoomRatios = sSupportedOpticalZoomRatios;
        }
        if (DataRepository.dataItemFeature().isSupportMacroMode() == 1) {
            arrayList.clear();
            Scanner scanner2 = new Scanner(MACRO_OPTICAL_ZOOM_RATIO_COMBINATION);
            scanner2.useDelimiter("\\s*[:,]\\s*");
            while (scanner2.hasNext()) {
                String next2 = scanner2.next();
                if (next2 != null && next2.length() > 0) {
                    arrayList.add(next2);
                    i2++;
                    if (i2 == 0) {
                        sDefaultMacroOpticalZoomRatio = Float.valueOf(next2).floatValue();
                    }
                }
            }
            $closeResource(null, scanner2);
            if (arrayList.size() >= 1) {
                sMacroSupportedOpticalZoomRatios = new float[arrayList.size()];
                int i4 = 0;
                for (String str2 : arrayList) {
                    sMacroSupportedOpticalZoomRatios[i4] = Float.parseFloat(str2);
                    i4++;
                }
                Log.d(TAG, Arrays.toString(sMacroSupportedOpticalZoomRatios));
            } else {
                throw new IllegalStateException("The supported optical zoom ratios are probably not configured correctly");
            }
        }
        if (DataRepository.dataItemFeature().fe() == 1) {
            sNightSupportedOpticalZoomRatios = new float[]{0.6f, 1.0f, 2.0f};
            if (b.lv == 1) {
                sNightSupportedOpticalZoomRatios = sSupportedOpticalZoomRatios;
            }
            sNightSupportedZoomRatioIndex = 1;
        } else {
            sNightSupportedOpticalZoomRatios = sSupportedOpticalZoomRatios;
            sNightSupportedZoomRatioIndex = sDefaultOpticalZoomRatioIndex;
        }
    }

    private HybridZoomingSystem() {
    }

    public static float add(float f2, float f3) {
        return ((float) (((int) (f2 * 10.0f)) + ((int) (f3 * 10.0f)))) / 10.0f;
    }

    public static float clamp(float f2, float f3, float f4) {
        return f2 > f4 ? f4 : f2 < f3 ? f3 : f2;
    }

    public static void clearZoomRatioHistory() {
        Log.d(TAG, "clearZoomRatioHistory()");
        sZoomRatioHistory.clear();
        sZoomingSourceIdentity.set(0);
    }

    public static int getDefaultOpticalZoomRatioIndex(int i) {
        if (!(i == 161 || i == 183)) {
            switch (i) {
                case 173:
                    return sNightSupportedZoomRatioIndex;
                case 174:
                    break;
                case 175:
                    return sPixelSupportedZoomRatioIndex;
                default:
                    return sDefaultOpticalZoomRatioIndex;
            }
        }
        return sFunOrLiveSupportedZoomRatioIndex;
    }

    public static int getMacroZoomRatioIndex(int i) {
        if (i == 161 || i == 183) {
            return -1;
        }
        switch (i) {
            case 173:
            case 174:
            case 175:
                return -1;
            default:
                return sMacroZoomRatioIndex;
        }
    }

    public static float getMaximumMacroOpticalZoomRatio() {
        float[] fArr = sMacroSupportedOpticalZoomRatios;
        return fArr[fArr.length - 1];
    }

    public static float getMaximumOpticalZoomRatio(int i) {
        float[] fArr;
        if (!(i == 161 || i == 183)) {
            switch (i) {
                case 173:
                    fArr = sNightSupportedOpticalZoomRatios;
                    break;
                case 174:
                    break;
                case 175:
                    fArr = sPixelSupportedOpticalZoomRatios;
                    break;
                default:
                    fArr = sSupportedOpticalZoomRatios;
                    break;
            }
            return fArr[fArr.length - 1];
        }
        fArr = sFunOrLiveSupportedOpticalZoomRatios;
        return fArr[fArr.length - 1];
    }

    public static float getMinimumMacroOpticalZoomRatio() {
        return sMacroSupportedOpticalZoomRatios[0];
    }

    public static float getMinimumOpticalZoomRatio(int i) {
        if (!(i == 161 || i == 183)) {
            switch (i) {
                case 173:
                    return sNightSupportedOpticalZoomRatios[0];
                case 174:
                    break;
                case 175:
                    return sPixelSupportedOpticalZoomRatios[0];
                default:
                    return sMacroZoomRatioIndex != -1 ? sSupportedOpticalZoomRatios[1] : sSupportedOpticalZoomRatios[0];
            }
        }
        return sFunOrLiveSupportedOpticalZoomRatios[0];
    }

    /* JADX WARNING: Removed duplicated region for block: B:11:0x001f  */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x0039  */
    public static float getOpticalZoomRatioAt(int i, int i2) {
        float[] fArr;
        if (!(i == 161 || i == 183)) {
            switch (i) {
                case 173:
                    fArr = sNightSupportedOpticalZoomRatios;
                    break;
                case 174:
                    break;
                case 175:
                    fArr = sPixelSupportedOpticalZoomRatios;
                    break;
                default:
                    fArr = sSupportedOpticalZoomRatios;
                    break;
            }
            int length = fArr.length;
            if (i2 >= 0) {
                Log.e(TAG, "The given index must be in range [0, " + length + ")   curIndex error : " + i2);
                i2 = 0;
            } else if (i2 >= length) {
                Log.e(TAG, "The given index must be in range [0, " + length + ")   curIndex error : " + i2);
                i2 = length + -1;
            }
            return fArr[i2];
        }
        fArr = sFunOrLiveSupportedOpticalZoomRatios;
        int length2 = fArr.length;
        if (i2 >= 0) {
        }
        return fArr[i2];
    }

    /* JADX WARNING: Removed duplicated region for block: B:11:0x001b  */
    public static int getOpticalZoomRatioIndex(int i, float f2) {
        float[] fArr;
        int length;
        if (!(i == 161 || i == 183)) {
            switch (i) {
                case 173:
                    fArr = sNightSupportedOpticalZoomRatios;
                    break;
                case 174:
                    break;
                case 175:
                    fArr = sPixelSupportedOpticalZoomRatios;
                    break;
                default:
                    fArr = sSupportedOpticalZoomRatios;
                    break;
            }
            for (length = fArr.length - 1; length >= 0; length--) {
                if (f2 >= fArr[length]) {
                    return length;
                }
            }
            throw new IllegalArgumentException("Illegal zoom ratio: " + f2);
        }
        fArr = sFunOrLiveSupportedOpticalZoomRatios;
        while (length >= 0) {
        }
        throw new IllegalArgumentException("Illegal zoom ratio: " + f2);
    }

    public static float[] getSupportedOpticalZoomRatios(int i) {
        if (!(i == 161 || i == 183)) {
            switch (i) {
                case 173:
                    return sNightSupportedOpticalZoomRatios;
                case 174:
                    break;
                case 175:
                    return sPixelSupportedOpticalZoomRatios;
                default:
                    return sSupportedOpticalZoomRatios;
            }
        }
        return sFunOrLiveSupportedOpticalZoomRatios;
    }

    public static String getZoomRatioHistory(int i, String str) {
        if (i == 165) {
            i = 163;
        }
        String str2 = sZoomRatioHistory.get(Integer.valueOf(i));
        return str2 != null ? str2 : str;
    }

    public static int getZoomingSourceIdentity() {
        return sZoomingSourceIdentity.get();
    }

    public static boolean isOpticalZoomRatio(float f2) {
        return Arrays.binarySearch(sSupportedOpticalZoomRatios, f2) >= 0 ? true : false;
    }

    static void preload() {
        Log.d(TAG, "preload");
    }

    public static void setZoomRatioHistory(int i, String str) {
        if (i == 165) {
            i = 163;
        }
        sZoomRatioHistory.put(Integer.valueOf(i), str);
    }

    public static void setZoomingSourceIdentity(int i) {
        Log.d(TAG, "setZoomingSourceIdentity(): " + i);
        sZoomingSourceIdentity.set(i);
    }

    public static float sub(float f2, float f3) {
        return ((float) (((int) (f2 * 10.0f)) - ((int) (f3 * 10.0f)))) / 10.0f;
    }

    public static Rect toCropRegion(float f2, Rect rect) {
        if (f2 <= 0.0f) {
            throw new IllegalArgumentException("Zoom ratio must be greater than 0.0f");
        } else if (rect != null) {
            int width = rect.width() / 2;
            int height = rect.height() / 2;
            float f3 = 2.0f * f2;
            int width2 = (int) (((float) rect.width()) / f3);
            int height2 = (int) (((float) rect.height()) / f3);
            Rect rect2 = new Rect();
            rect2.set(width - width2, height - height2, width + width2, height + height2);
            Log.d(TAG, "toCropRegion(): zoom ratio = " + f2 + ", crop region = " + rect2);
            return rect2;
        } else {
            throw new IllegalArgumentException("activeArraySize must be non null");
        }
    }

    public static float toDecimal(float f2) {
        return ((float) ((int) (f2 * 10.0f))) / 10.0f;
    }

    public static float toFloat(String str, float f2) {
        try {
            return Float.parseFloat(str);
        } catch (Exception unused) {
            Log.e(TAG, "Invalid zoom: " + str);
            return f2;
        }
    }

    public static String toString(float f2) {
        if (0.6f == f2) {
            return STRING_ZOOM_RATIO_ULTR;
        }
        if (1.0f == f2) {
            return STRING_ZOOM_RATIO_WIDE;
        }
        if (FLOAT_ZOOM_RATIO_TELE == f2) {
            return STRING_ZOOM_RATIO_TELE;
        }
        if (5.0f == f2) {
            return STRING_ZOOM_RATIO_TELE_5X;
        }
        if (0.0f == f2) {
            return "macro";
        }
        float decimal = toDecimal(f2);
        return String.format(Locale.US, "%.1fx", Float.valueOf(decimal));
    }
}
