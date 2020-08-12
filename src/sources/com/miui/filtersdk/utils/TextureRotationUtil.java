package com.miui.filtersdk.utils;

import java.nio.FloatBuffer;

public class TextureRotationUtil {
    public static final float[] CUBE = {-1.0f, -1.0f, 1.0f, -1.0f, -1.0f, 1.0f, 1.0f, 1.0f};
    public static final float[] TEXTURE_NO_ROTATION = {0.0f, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f};
    public static final float[] TEXTURE_ROTATED_180 = {1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f};
    public static final float[] TEXTURE_ROTATED_270 = {0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f, 1.0f};
    public static final float[] TEXTURE_ROTATED_90 = {1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f};

    /* renamed from: com.miui.filtersdk.utils.TextureRotationUtil$1  reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$miui$filtersdk$utils$Rotation = new int[Rotation.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(10:0|1|2|3|4|5|6|7|8|10) */
        /* JADX WARNING: Can't wrap try/catch for region: R(8:0|1|2|3|4|5|6|(3:7|8|10)) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001f */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x002a */
        static {
            $SwitchMap$com$miui$filtersdk$utils$Rotation[Rotation.ROTATION_90.ordinal()] = 1;
            $SwitchMap$com$miui$filtersdk$utils$Rotation[Rotation.ROTATION_180.ordinal()] = 2;
            $SwitchMap$com$miui$filtersdk$utils$Rotation[Rotation.ROTATION_270.ordinal()] = 3;
            try {
                $SwitchMap$com$miui$filtersdk$utils$Rotation[Rotation.NORMAL.ordinal()] = 4;
            } catch (NoSuchFieldError unused) {
            }
        }
    }

    private TextureRotationUtil() {
    }

    public static void adjustSize(int i, boolean z, boolean z2, FloatBuffer floatBuffer, FloatBuffer floatBuffer2) {
        float[] rotation = getRotation(Rotation.fromInt(i), z, z2);
        float[] fArr = CUBE;
        floatBuffer.clear();
        floatBuffer.put(fArr).position(0);
        floatBuffer2.clear();
        floatBuffer2.put(rotation).position(0);
    }

    private static float flip(float f2) {
        return f2 == 0.0f ? 1.0f : 0.0f;
    }

    public static float[] getRotation(Rotation rotation, boolean z, boolean z2) {
        int i = AnonymousClass1.$SwitchMap$com$miui$filtersdk$utils$Rotation[rotation.ordinal()];
        float[] fArr = i != 1 ? i != 2 ? i != 3 ? TEXTURE_NO_ROTATION : TEXTURE_ROTATED_270 : TEXTURE_ROTATED_180 : TEXTURE_ROTATED_90;
        if (z) {
            fArr = new float[]{flip(fArr[0]), fArr[1], flip(fArr[2]), fArr[3], flip(fArr[4]), fArr[5], flip(fArr[6]), fArr[7]};
        }
        if (!z2) {
            return fArr;
        }
        return new float[]{fArr[0], flip(fArr[1]), fArr[2], flip(fArr[3]), fArr[4], flip(fArr[5]), fArr[6], flip(fArr[7])};
    }
}
