package com.android.camera;

import android.support.v4.view.MotionEventCompat;
import com.android.camera.panorama.NativeMemoryAllocator;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PanoUtil {
    public static double calculateDifferenceBetweenAngles(double d2, double d3) {
        double d4 = (d3 - d2) % 360.0d;
        if (d4 < 0.0d) {
            d4 += 360.0d;
        }
        double d5 = (d2 - d3) % 360.0d;
        if (d5 < 0.0d) {
            d5 += 360.0d;
        }
        return Math.min(d4, d5);
    }

    public static ByteBuffer createByteBuffer(byte[] bArr) {
        ByteBuffer allocateBuffer = NativeMemoryAllocator.allocateBuffer(bArr.length);
        allocateBuffer.order(ByteOrder.nativeOrder());
        allocateBuffer.position(0);
        allocateBuffer.put(bArr);
        allocateBuffer.position(0);
        return allocateBuffer;
    }

    public static String createName(String str, long j) {
        return new SimpleDateFormat(str).format(new Date(j));
    }

    public static void decodeYUV420SPQuarterRes(int[] iArr, byte[] bArr, int i, int i2) {
        int i3 = i * i2;
        int i4 = 0;
        int i5 = 0;
        while (i4 < i2) {
            int i6 = ((i4 >> 1) * i) + i3;
            int i7 = 0;
            int i8 = 0;
            int i9 = i5;
            int i10 = 0;
            while (i10 < i) {
                int i11 = (bArr[(i4 * i) + i10] & 255) - 16;
                if (i11 < 0) {
                    i11 = 0;
                }
                if ((i10 & 1) == 0) {
                    int i12 = i6 + 1;
                    int i13 = i12 + 1;
                    i7 = (bArr[i12] & 255) - 128;
                    int i14 = i13 + 2;
                    i8 = (bArr[i6] & 255) - 128;
                    i6 = i14;
                }
                int i15 = i11 * 1192;
                int i16 = (i8 * 1634) + i15;
                int i17 = (i15 - (i8 * 833)) - (i7 * 400);
                int i18 = i15 + (i7 * 2066);
                int i19 = 262143;
                if (i16 < 0) {
                    i16 = 0;
                } else if (i16 > 262143) {
                    i16 = 262143;
                }
                if (i17 < 0) {
                    i17 = 0;
                } else if (i17 > 262143) {
                    i17 = 262143;
                }
                if (i18 < 0) {
                    i19 = 0;
                } else if (i18 <= 262143) {
                    i19 = i18;
                }
                iArr[i9] = -16777216 | ((i16 << 6) & 16711680) | ((i17 >> 2) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) | ((i19 >> 10) & 255);
                i10 += 4;
                i9++;
            }
            i4 += 4;
            i5 = i9;
        }
    }
}
