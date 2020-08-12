package com.android.camera.fragment.mimoji;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.YuvImage;
import com.arcsoft.avatar.util.LOG;
import java.io.ByteArrayOutputStream;

public class BitmapUtils {
    private static final String OUT_OF_MEMORY_STRING = "Out of memory error.";
    private static final String TAG = "BitmapUtils";

    public static Bitmap createBitmapFromStream(byte[] bArr, int i) {
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            int i2 = 1;
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeByteArray(bArr, 0, bArr.length, options);
            if (!options.mCancel && options.outWidth != -1) {
                if (options.outHeight != -1) {
                    if (i > 0) {
                        i2 = getBitmapSampleSize(options, i);
                    }
                    options.inSampleSize = i2;
                    options.inJustDecodeBounds = false;
                    options.inDither = false;
                    options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                    return BitmapFactory.decodeByteArray(bArr, 0, bArr.length, options);
                }
            }
            return null;
        } catch (OutOfMemoryError e2) {
            LOG.d(TAG, "Got oom exception " + e2);
            return null;
        }
    }

    public static int getBitmapSampleSize(BitmapFactory.Options options, int i) {
        int i2 = 1;
        int ceil = i < 0 ? 1 : (int) Math.ceil(Math.sqrt((double) ((((float) options.outWidth) * ((float) options.outHeight)) / ((float) i))));
        if (i < 0) {
            ceil = 1;
        }
        if (ceil > 8) {
            return ((ceil + 7) / 8) * 8;
        }
        while (i2 < ceil) {
            i2 <<= 1;
        }
        return i2;
    }

    public static Bitmap horizMirrorBitmap(Bitmap bitmap) {
        if (bitmap == null) {
            return bitmap;
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Matrix matrix = new Matrix();
        matrix.postScale(-1.0f, 1.0f);
        try {
            Bitmap createBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
            if (bitmap == createBitmap) {
                return bitmap;
            }
            bitmap.recycle();
            return createBitmap;
        } catch (OutOfMemoryError unused) {
            LOG.d(TAG, OUT_OF_MEMORY_STRING);
            return bitmap;
        }
    }

    public static Bitmap rawByteArray2RGBABitmap(byte[] bArr, int i, int i2) {
        Bitmap bitmap = null;
        try {
            YuvImage yuvImage = new YuvImage(bArr, 17, i, i2, null);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            yuvImage.compressToJpeg(new Rect(0, 0, i, i2), 100, byteArrayOutputStream);
            bitmap = createBitmapFromStream(byteArrayOutputStream.toByteArray(), 0);
            byteArrayOutputStream.close();
            return bitmap;
        } catch (Exception e2) {
            e2.printStackTrace();
            return bitmap;
        }
    }

    public static Bitmap rawByteArray2RGBABitmap(byte[] bArr, int i, int i2, int i3) {
        Bitmap bitmap = null;
        try {
            YuvImage yuvImage = new YuvImage(bArr, 17, i3, i2, null);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            yuvImage.compressToJpeg(new Rect(0, 0, i, i2), 100, byteArrayOutputStream);
            bitmap = createBitmapFromStream(byteArrayOutputStream.toByteArray(), 0);
            byteArrayOutputStream.close();
            return bitmap;
        } catch (Exception e2) {
            e2.printStackTrace();
            return bitmap;
        }
    }

    public static Bitmap rotateBitmap(Bitmap bitmap, int i) {
        LOG.d(TAG, "Bitmap rotateBitmap <-----");
        if (i == 0 || bitmap == null) {
            return bitmap;
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Matrix matrix = new Matrix();
        matrix.setRotate((float) i, ((float) width) / 2.0f, ((float) height) / 2.0f);
        Bitmap bitmap2 = null;
        try {
            Bitmap createBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
            if (bitmap != createBitmap) {
                bitmap.recycle();
                bitmap2 = createBitmap;
            } else {
                bitmap2 = bitmap;
            }
        } catch (OutOfMemoryError unused) {
            LOG.d(TAG, OUT_OF_MEMORY_STRING);
        } catch (Exception unused2) {
        }
        LOG.d(TAG, "Bitmap rotateBitmap ----->");
        return bitmap2;
    }

    public static Bitmap rotateBitmap(Bitmap bitmap, int i, boolean z) {
        if (i == 0 || bitmap == null) {
            return bitmap;
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Matrix matrix = new Matrix();
        matrix.setRotate((float) i, ((float) width) / 2.0f, ((float) height) / 2.0f);
        try {
            Bitmap createBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
            if (bitmap == createBitmap) {
                return bitmap;
            }
            if (z) {
                bitmap.recycle();
            }
            return createBitmap;
        } catch (OutOfMemoryError unused) {
            LOG.d(TAG, OUT_OF_MEMORY_STRING);
            return bitmap;
        }
    }
}
