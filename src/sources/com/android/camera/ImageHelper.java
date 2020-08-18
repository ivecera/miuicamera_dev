package com.android.camera;

import android.graphics.Rect;
import android.graphics.YuvImage;
import com.android.camera.log.Log;
import com.android.camera.storage.Storage;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class ImageHelper {
    public static final String TAG = "ImageHelper";

    private static void compressYuv(byte[] bArr, int i, int i2, int[] iArr, int i3, OutputStream outputStream) {
        new YuvImage(bArr, 17, i, i2, iArr).compressToJpeg(new Rect(0, 0, i, i2), i3, outputStream);
    }

    public static byte[] encodeNv21ToJpeg(byte[] bArr, int i, int i2, int i3) {
        ByteArrayOutputStream byteArrayOutputStream;
        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            try {
                compressYuv(bArr, i, i2, null, i3, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                Util.closeSafely(byteArrayOutputStream);
                return byteArray;
            } catch (Exception e2) {
                e = e2;
                try {
                    Log.w(TAG, "encodeNv21 error, " + e.getMessage());
                    Util.closeSafely(byteArrayOutputStream);
                    return null;
                } catch (Throwable th) {
                    th = th;
                    Util.closeSafely(byteArrayOutputStream);
                    throw th;
                }
            }
        } catch (Exception e3) {
            e = e3;
            byteArrayOutputStream = null;
            Log.w(TAG, "encodeNv21 error, " + e.getMessage());
            Util.closeSafely(byteArrayOutputStream);
            return null;
        } catch (Throwable th2) {
            th = th2;
            byteArrayOutputStream = null;
            Util.closeSafely(byteArrayOutputStream);
            throw th;
        }
    }

    public static void saveYuvToJpg(byte[] bArr, int i, int i2, int[] iArr, long j) {
        if (bArr == null) {
            Log.w(TAG, "saveYuvToJpg: null data");
            return;
        }
        String str = "sdcard/DCIM/Camera/dump_" + j + Storage.JPEG_SUFFIX;
        Log.v(TAG, "saveYuvToJpg: " + str);
        FileOutputStream fileOutputStream = null;
        try {
            FileOutputStream fileOutputStream2 = new FileOutputStream(str);
            try {
                compressYuv(bArr, i, i2, iArr, 100, new FileOutputStream(str));
                Util.closeSafely(fileOutputStream2);
            } catch (FileNotFoundException e2) {
                e = e2;
                fileOutputStream = fileOutputStream2;
                try {
                    Log.e(TAG, e.getMessage(), e);
                    Util.closeSafely(fileOutputStream);
                } catch (Throwable th) {
                    th = th;
                    Util.closeSafely(fileOutputStream);
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                fileOutputStream = fileOutputStream2;
                Util.closeSafely(fileOutputStream);
                throw th;
            }
        } catch (FileNotFoundException e3) {
            e = e3;
            Log.e(TAG, e.getMessage(), e);
            Util.closeSafely(fileOutputStream);
        }
    }
}
