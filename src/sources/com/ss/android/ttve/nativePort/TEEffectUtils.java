package com.ss.android.ttve.nativePort;

import android.support.annotation.Keep;
import java.util.Map;

@Keep
public class TEEffectUtils {

    @Keep
    public interface ImageListener {
        void onData(int[] iArr, int i, int i2, int i3);
    }

    static {
        TENativeLibsLoader.loadLibrary();
    }

    public static native String getEffectVersion();

    public static int getQREncodedData(String str, Map<Integer, Integer> map, ImageListener imageListener) {
        int[] iArr;
        if (map != null) {
            iArr = new int[(map.size() * 2)];
            int i = 0;
            for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
                iArr[i] = entry.getKey().intValue();
                iArr[i + 1] = entry.getValue().intValue();
                i += 2;
            }
        } else {
            iArr = null;
        }
        return nativeGetQREncodedData(str, iArr, imageListener);
    }

    private static native int nativeGetQREncodedData(String str, int[] iArr, ImageListener imageListener);
}
