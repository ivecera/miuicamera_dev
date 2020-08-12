package org.jcodec.common;

import com.android.camera.data.data.config.ComponentManuallyDualLens;
import org.jcodec.platform.Platform;

public class Fourcc {
    public static final int free = intFourcc("free");
    public static final int ftyp = intFourcc("ftyp");
    public static final int mdat = intFourcc("mdat");
    public static final int moov = intFourcc("moov");
    public static final int wide = intFourcc(ComponentManuallyDualLens.LENS_WIDE);

    public static int intFourcc(String str) {
        byte[] bytes = Platform.getBytes(str);
        return makeInt(bytes[0], bytes[1], bytes[2], bytes[3]);
    }

    public static int makeInt(byte b2, byte b3, byte b4, byte b5) {
        return (b2 << 24) | ((b3 & 255) << 16) | ((b4 & 255) << 8) | (b5 & 255);
    }
}
