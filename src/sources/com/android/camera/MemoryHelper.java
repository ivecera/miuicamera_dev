package com.android.camera;

import android.util.SparseIntArray;

public class MemoryHelper {
    private static SparseIntArray sCapturedNumberArray = new SparseIntArray(4);
    private static int sTrimLevel;

    public static void addCapturedNumber(int i, int i2) {
        sCapturedNumberArray.put(i, sCapturedNumberArray.get(i) + i2);
    }

    public static void clear() {
        sCapturedNumberArray.clear();
    }

    public static void resetCapturedNumber(int i) {
        sCapturedNumberArray.put(i, 0);
    }

    public static void setTrimLevel(int i) {
        sTrimLevel = i;
    }

    public static boolean shouldTrimMemory(int i) {
        int i2 = sTrimLevel;
        if (i2 == 5) {
            return sCapturedNumberArray.get(i) >= 30 ? true : false;
        }
        if (i2 == 10) {
            return sCapturedNumberArray.get(i) >= 20 ? true : false;
        }
        if (i2 != 15) {
            return false;
        }
        return sCapturedNumberArray.get(i) >= 10 ? true : false;
    }
}
