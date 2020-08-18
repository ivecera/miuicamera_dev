package com.android.camera.features.mimoji2.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.YuvImage;
import com.arcsoft.avatar.util.LOG;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public class BitmapUtils2 {
    private static final String OUT_OF_MEMORY_STRING = "Out of memory error.";
    private static final String TAG = "BitmapUtils2";

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

    /* JADX WARNING: Removed duplicated region for block: B:21:0x004b A[SYNTHETIC, Splitter:B:21:0x004b] */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x0061 A[SYNTHETIC, Splitter:B:30:0x0061] */
    public static void saveARGBToFile(String str, int i, int i2, ByteBuffer byteBuffer) {
        Bitmap createBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
        createBitmap.copyPixelsFromBuffer(byteBuffer);
        Matrix matrix = new Matrix();
        matrix.postScale(1.0f, -1.0f);
        Bitmap createBitmap2 = Bitmap.createBitmap(createBitmap, 0, 0, i, i2, matrix, false);
        FileOutputStream fileOutputStream = null;
        try {
            FileOutputStream fileOutputStream2 = new FileOutputStream(str);
            try {
                createBitmap2.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream2);
                try {
                    fileOutputStream2.close();
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
                if (createBitmap == null || createBitmap.isRecycled()) {
                    return;
                }
            } catch (FileNotFoundException e3) {
                e = e3;
                fileOutputStream = fileOutputStream2;
                try {
                    e.printStackTrace();
                    if (fileOutputStream != null) {
                    }
                    return;
                } catch (Throwable th) {
                    th = th;
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.close();
                        } catch (IOException e4) {
                            e4.printStackTrace();
                        }
                    }
                    if (createBitmap != null && !createBitmap.isRecycled()) {
                        createBitmap.recycle();
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                fileOutputStream = fileOutputStream2;
                if (fileOutputStream != null) {
                }
                createBitmap.recycle();
                throw th;
            }
        } catch (FileNotFoundException e5) {
            e = e5;
            e.printStackTrace();
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e6) {
                    e6.printStackTrace();
                }
            }
            if (createBitmap == null || createBitmap.isRecycled()) {
                return;
            }
            createBitmap.recycle();
        }
        createBitmap.recycle();
    }
}
