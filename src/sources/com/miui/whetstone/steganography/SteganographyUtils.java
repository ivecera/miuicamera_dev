package com.miui.whetstone.steganography;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;
import java.io.File;

public class SteganographyUtils {
    private static String TAG = "Whet_SteganographyUtils";

    public static String decodeWatermark(Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }
        try {
            return Steg.withInput(bitmap).decode().intoString();
        } catch (Exception e2) {
            String str = TAG;
            Log.w(str, "decodeWatermark Exception e:" + e2.getMessage());
            return null;
        }
    }

    public static String decodeWatermark(File file) {
        if (file == null) {
            return null;
        }
        try {
            return Steg.withInput(file).decode().intoString();
        } catch (Exception e2) {
            String str = TAG;
            Log.w(str, "decodeWatermark Exception e:" + e2.getMessage());
            return null;
        }
    }

    public static String decodeWatermark(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            return Steg.withInput(str).decode().intoString();
        } catch (Exception e2) {
            String str2 = TAG;
            Log.w(str2, "decodeWatermark Exception e:" + e2.getMessage());
            return null;
        }
    }

    public static Bitmap encodeWatermark(Bitmap bitmap, String str) {
        if (bitmap == null || TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            return Steg.withInput(bitmap).encode(str).intoBitmap();
        } catch (Exception e2) {
            String str2 = TAG;
            Log.w(str2, "encodeWatermark Exception e:" + e2.getMessage());
            return null;
        }
    }

    public static Bitmap encodeWatermark(File file, String str) {
        if (file == null || TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            return Steg.withInput(file).encode(str).intoBitmap();
        } catch (Exception e2) {
            String str2 = TAG;
            Log.w(str2, "encodeWatermark Exception e:" + e2.getMessage());
            return null;
        }
    }

    public static Bitmap encodeWatermark(String str, String str2) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return null;
        }
        try {
            return Steg.withInput(str).encode(str2).intoBitmap();
        } catch (Exception e2) {
            String str3 = TAG;
            Log.w(str3, "encodeWatermark Exception e:" + e2.getMessage());
            return null;
        }
    }

    public static File encodeWatermark(Bitmap bitmap, File file, String str) {
        if (bitmap == null || file == null || TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            return Steg.withInput(bitmap).encode(str).intoFile(file);
        } catch (Exception e2) {
            String str2 = TAG;
            Log.w(str2, "encodeWatermark Exception e:" + e2.getMessage());
            return null;
        }
    }

    public static File encodeWatermark(Bitmap bitmap, String str, String str2) {
        if (bitmap == null || str == null || TextUtils.isEmpty(str2)) {
            return null;
        }
        try {
            return Steg.withInput(bitmap).encode(str2).intoFile(str);
        } catch (Exception e2) {
            String str3 = TAG;
            Log.w(str3, "encodeWatermark Exception e:" + e2.getMessage());
            return null;
        }
    }

    public static File encodeWatermark(File file, File file2, String str) {
        if (file == null || file2 == null || TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            return Steg.withInput(file).encode(str).intoFile(file2);
        } catch (Exception e2) {
            String str2 = TAG;
            Log.w(str2, "encodeWatermark Exception e:" + e2.getMessage());
            return null;
        }
    }

    public static File encodeWatermark(String str, String str2, String str3) {
        if (str == null || str2 == null || TextUtils.isEmpty(str3)) {
            return null;
        }
        try {
            return Steg.withInput(str).encode(str3).intoFile(str2);
        } catch (Exception e2) {
            String str4 = TAG;
            Log.w(str4, "encodeWatermark Exception e:" + e2.getMessage());
            return null;
        }
    }
}
