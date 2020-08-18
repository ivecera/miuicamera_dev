package com.miui.whetstone.steganography;

import android.graphics.Bitmap;
import android.graphics.Color;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class BitmapEncoder {
    public static final int HEADER_SIZE = 12;

    public static long bytesToLong(byte[] bArr) {
        ByteBuffer allocate = ByteBuffer.allocate(8);
        allocate.put(bArr);
        allocate.flip();
        return allocate.getLong();
    }

    public static byte[] createHeader(long j) {
        byte[] bArr = new byte[12];
        int i = 0;
        bArr[0] = 91;
        bArr[1] = 91;
        byte[] longToBytes = longToBytes(j);
        int length = longToBytes.length;
        int i2 = 2;
        while (i < length) {
            bArr[i2] = longToBytes[i];
            i++;
            i2++;
        }
        bArr[i2] = 93;
        bArr[i2 + 1] = 93;
        return bArr;
    }

    public static byte[] decode(Bitmap bitmap) {
        return decodeBitmapToByteArray(bitmap, 12, (int) bytesToLong(Arrays.copyOfRange(decodeBitmapToByteArray(bitmap, 0, 12), 2, 10)));
    }

    private static byte[] decodeBitmapToByteArray(Bitmap bitmap, int i, int i2) {
        byte b2;
        byte[] bArr = new byte[i2];
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int i3 = 3;
        int[] iArr = new int[3];
        int i4 = 0;
        int i5 = 0;
        int i6 = 0;
        while (i4 < height) {
            int i7 = i6;
            int i8 = i5;
            int i9 = 0;
            while (i9 < width) {
                int pixel = bitmap.getPixel(i9, i4);
                iArr[0] = Color.red(pixel) % 2;
                iArr[1] = Color.green(pixel) % 2;
                iArr[2] = Color.blue(pixel) % 2;
                int i10 = i7;
                int i11 = i8;
                int i12 = 0;
                while (i12 < i3) {
                    if (i11 >= i) {
                        int i13 = i11 - i;
                        if (iArr[i12] == 1) {
                            b2 = (byte) (bArr[i13] | (1 << i10));
                        } else {
                            b2 = (byte) ((~(1 << i10)) & bArr[i13]);
                        }
                        bArr[i13] = b2;
                    }
                    int i14 = i10 + 1;
                    if (i14 == 8) {
                        i11++;
                        i10 = 0;
                    } else {
                        i10 = i14;
                    }
                    if (i11 - i >= bArr.length) {
                        break;
                    }
                    i12++;
                    i3 = 3;
                }
                i8 = i11;
                i7 = i10;
                if (i8 - i >= bArr.length) {
                    break;
                }
                i9++;
                i3 = 3;
            }
            i5 = i8;
            i6 = i7;
            if (i5 - i >= bArr.length) {
                break;
            }
            i4++;
            i3 = 3;
        }
        return bArr;
    }

    public static Bitmap encode(Bitmap bitmap, byte[] bArr) {
        byte[] createHeader = createHeader((long) bArr.length);
        if (bArr.length % 24 != 0) {
            bArr = Arrays.copyOf(bArr, bArr.length + (24 - (bArr.length % 24)));
        }
        return encodeByteArrayIntoBitmap(bitmap, createHeader, bArr);
    }

    private static Bitmap encodeByteArrayIntoBitmap(Bitmap bitmap, byte[] bArr, byte[] bArr2) {
        Bitmap copy = bitmap.copy(Bitmap.Config.ARGB_8888, true);
        int width = bitmap.getWidth();
        bitmap.getHeight();
        int[] iArr = {0, 0, 0};
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        while (i2 < bArr.length + bArr2.length) {
            int i6 = i4;
            int i7 = i3;
            int i8 = i;
            while (i8 < 8) {
                if (i2 < bArr.length) {
                    iArr[i5] = (bArr[i2] >> i8) & 1;
                } else {
                    iArr[i5] = (bArr2[i2 - bArr.length] >> i8) & 1;
                }
                if (i5 == 2) {
                    int pixel = bitmap.getPixel(i7, i6);
                    int red = Color.red(pixel);
                    int green = Color.green(pixel);
                    int blue = Color.blue(pixel);
                    if (red % 2 == 1 - iArr[i]) {
                        red++;
                    }
                    if (green % 2 == 1 - iArr[1]) {
                        green++;
                    }
                    if (blue % 2 == 1 - iArr[2]) {
                        blue++;
                    }
                    int i9 = 254;
                    if (red == 256) {
                        red = 254;
                    }
                    if (green == 256) {
                        green = 254;
                    }
                    if (blue != 256) {
                        i9 = blue;
                    }
                    copy.setPixel(i7, i6, Color.argb(255, red, green, i9));
                    int i10 = i7 + 1;
                    if (i10 == width) {
                        i6++;
                        i7 = 0;
                    } else {
                        i7 = i10;
                    }
                    i5 = 0;
                } else {
                    i5++;
                }
                i8++;
                i = 0;
            }
            i2++;
            i3 = i7;
            i4 = i6;
            i = 0;
        }
        return copy;
    }

    private static byte[] longToBytes(long j) {
        ByteBuffer allocate = ByteBuffer.allocate(8);
        allocate.putLong(j);
        return allocate.array();
    }

    public static String printBinaryString(byte[] bArr) {
        String str = "";
        for (byte b2 : bArr) {
            str = str + "" + ((int) b2) + ",";
        }
        return str;
    }
}
