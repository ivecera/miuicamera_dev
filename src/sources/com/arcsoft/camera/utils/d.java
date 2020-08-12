package com.arcsoft.camera.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.camera2.CameraCharacteristics;
import android.media.Image;
import android.net.Uri;
import android.text.TextUtils;
import android.util.SizeF;
import android.webkit.MimeTypeMap;
import com.android.camera.storage.Storage;
import com.ss.android.ugc.effectmanager.effect.model.ComposerHelper;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Properties;

/* compiled from: ToolUtil */
public class d {
    public static final int COLOR_FormatI420 = 1;
    public static final int COLOR_FormatNV21 = 2;

    public static Bitmap a(Bitmap bitmap, float f2) {
        if (bitmap == null) {
            return null;
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Matrix matrix = new Matrix();
        matrix.preScale(f2, f2);
        matrix.setRotate(180.0f);
        Bitmap createBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, false);
        if (createBitmap.equals(bitmap)) {
            return createBitmap;
        }
        bitmap.recycle();
        return createBitmap;
    }

    public static Bitmap a(Bitmap bitmap, int i, int i2) {
        if (bitmap == null) {
            return null;
        }
        int height = bitmap.getHeight();
        int width = bitmap.getWidth();
        Matrix matrix = new Matrix();
        matrix.postScale(((float) i) / ((float) width), ((float) i2) / ((float) height));
        Bitmap createBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, false);
        if (!bitmap.isRecycled()) {
            bitmap.recycle();
        }
        return createBitmap;
    }

    public static String a(Context context, Uri uri) {
        String lowerCase;
        if (uri.getScheme() == null) {
            return null;
        }
        if (uri.getScheme().equals("content")) {
            return context.getContentResolver().getType(uri);
        }
        if (!uri.getScheme().equals(ComposerHelper.COMPOSER_PATH) || (lowerCase = MimeTypeMap.getFileExtensionFromUrl(uri.getPath()).toLowerCase()) == null) {
            return null;
        }
        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(lowerCase);
    }

    public static void a(String str, Bitmap bitmap) {
        File file = new File(str);
        if (file.exists()) {
            file.delete();
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (FileNotFoundException e2) {
            e2.printStackTrace();
        } catch (IOException e3) {
            e3.printStackTrace();
        }
    }

    public static boolean a(Image image) {
        int format = image.getFormat();
        return format == 17 || format == 35 || format == 842094169;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:16:?, code lost:
        return false;
     */
    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    public static boolean a(String str, String str2, byte[] bArr) {
        if (str2 == null || bArr == null) {
            return false;
        }
        File file = new File(str);
        if (!file.exists()) {
            file.mkdir();
        }
        File file2 = new File(str2);
        int i = 0;
        while (file2.exists()) {
            i++;
            file2 = new File(str2 + i);
        }
        FileOutputStream fileOutputStream = new FileOutputStream(str2);
        fileOutputStream.write(bArr);
        fileOutputStream.close();
        return true;
    }

    public static byte[] a(byte[] bArr, int i, int i2) {
        try {
            YuvImage yuvImage = new YuvImage(bArr, 17, i, i2, null);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            yuvImage.compressToJpeg(new Rect(0, 0, i, i2), 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.close();
            return byteArray;
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:22:0x002a A[SYNTHETIC, Splitter:B:22:0x002a] */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x0035 A[SYNTHETIC, Splitter:B:28:0x0035] */
    public static int b(String str, byte[] bArr) {
        if (TextUtils.isEmpty(str) || bArr == null) {
            return -1;
        }
        FileOutputStream fileOutputStream = null;
        try {
            FileOutputStream fileOutputStream2 = new FileOutputStream(str);
            try {
                fileOutputStream2.write(bArr);
                try {
                    fileOutputStream2.close();
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
                return 0;
            } catch (Exception e3) {
                e = e3;
                fileOutputStream = fileOutputStream2;
                try {
                    e.printStackTrace();
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.close();
                        } catch (IOException e4) {
                            e4.printStackTrace();
                        }
                    }
                    return 0;
                } catch (Throwable th) {
                    th = th;
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.close();
                        } catch (IOException e5) {
                            e5.printStackTrace();
                        }
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                fileOutputStream = fileOutputStream2;
                if (fileOutputStream != null) {
                }
                throw th;
            }
        } catch (Exception e6) {
            e = e6;
            e.printStackTrace();
            if (fileOutputStream != null) {
            }
            return 0;
        }
    }

    public static Bitmap b(byte[] bArr, int i, int i2) {
        Bitmap bitmap = null;
        try {
            YuvImage yuvImage = new YuvImage(bArr, 17, i, i2, null);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            yuvImage.compressToJpeg(new Rect(0, 0, i, i2), 90, byteArrayOutputStream);
            bitmap = BitmapFactory.decodeByteArray(byteArrayOutputStream.toByteArray(), 0, byteArrayOutputStream.size());
            byteArrayOutputStream.close();
            return bitmap;
        } catch (Exception e2) {
            e2.printStackTrace();
            return bitmap;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:24:0x002e  */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0034 A[SYNTHETIC, Splitter:B:27:0x0034] */
    public static int c(String str, byte[] bArr) {
        int i = -1;
        if (TextUtils.isEmpty(str) || bArr == null || bArr.length <= 0) {
            return -1;
        }
        FileOutputStream fileOutputStream = null;
        try {
            FileOutputStream fileOutputStream2 = new FileOutputStream(str);
            try {
                fileOutputStream2.write(bArr);
                i = 0;
                try {
                    fileOutputStream2.close();
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
            } catch (Exception e3) {
                e = e3;
                fileOutputStream = fileOutputStream2;
                try {
                    e.printStackTrace();
                    if (fileOutputStream != null) {
                        fileOutputStream.close();
                    }
                    return i;
                } catch (Throwable th) {
                    th = th;
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.close();
                        } catch (IOException e4) {
                            e4.printStackTrace();
                        }
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                fileOutputStream = fileOutputStream2;
                if (fileOutputStream != null) {
                }
                throw th;
            }
        } catch (Exception e5) {
            e = e5;
            e.printStackTrace();
            if (fileOutputStream != null) {
            }
            return i;
        }
        return i;
    }

    public static SizeF getAngleValue(CameraCharacteristics cameraCharacteristics) {
        SizeF sizeF = (SizeF) cameraCharacteristics.get(CameraCharacteristics.SENSOR_INFO_PHYSICAL_SIZE);
        float[] fArr = (float[]) cameraCharacteristics.get(CameraCharacteristics.LENS_INFO_AVAILABLE_FOCAL_LENGTHS);
        if (fArr == null || fArr.length <= 0) {
            return null;
        }
        return new SizeF((float) Math.toDegrees(StrictMath.atan((double) (sizeF.getWidth() / (fArr[0] * 2.0f))) * 2.0d), (float) Math.toDegrees(StrictMath.atan((double) (sizeF.getHeight() / (fArr[0] * 2.0f))) * 2.0d));
    }

    public static byte[] getDataFromImage(Image image, int i) {
        Rect rect;
        int i2;
        int i3 = i;
        int i4 = 2;
        int i5 = 1;
        if (i3 != 1 && i3 != 2) {
            throw new IllegalArgumentException("only support COLOR_FormatI420 and COLOR_FormatNV21");
        } else if (a(image)) {
            Rect cropRect = image.getCropRect();
            int format = image.getFormat();
            int width = cropRect.width();
            int height = cropRect.height();
            Image.Plane[] planes = image.getPlanes();
            int i6 = width * height;
            byte[] bArr = new byte[((ImageFormat.getBitsPerPixel(format) * i6) / 8)];
            int i7 = 0;
            byte[] bArr2 = new byte[planes[0].getRowStride()];
            int i8 = 1;
            int i9 = 0;
            int i10 = 0;
            while (i9 < planes.length) {
                if (i9 != 0) {
                    if (i9 != i5) {
                        if (i9 == i4) {
                            if (i3 == i5) {
                                i10 = (int) (((double) i6) * 1.25d);
                                i8 = i5;
                            } else if (i3 == i4) {
                                i8 = i4;
                            }
                        }
                    } else if (i3 == i5) {
                        i8 = i5;
                    } else if (i3 == i4) {
                        i10 = i6 + 1;
                        i8 = i4;
                    }
                    i10 = i6;
                } else {
                    i8 = i5;
                    i10 = i7;
                }
                ByteBuffer buffer = planes[i9].getBuffer();
                int rowStride = planes[i9].getRowStride();
                int pixelStride = planes[i9].getPixelStride();
                int i11 = i9 == 0 ? i7 : i5;
                int i12 = width >> i11;
                int i13 = height >> i11;
                buffer.position(((cropRect.top >> i11) * rowStride) + ((cropRect.left >> i11) * pixelStride));
                int i14 = 0;
                while (i14 < i13) {
                    if (pixelStride == 1 && i8 == 1) {
                        buffer.get(bArr, i10, i12);
                        i10 += i12;
                        rect = cropRect;
                        i2 = i12;
                    } else {
                        rect = cropRect;
                        i2 = ((i12 - 1) * pixelStride) + 1;
                        buffer.get(bArr2, 0, i2);
                        int i15 = i10;
                        for (int i16 = 0; i16 < i12; i16++) {
                            bArr[i15] = bArr2[i16 * pixelStride];
                            i15 += i8;
                        }
                        i10 = i15;
                    }
                    if (i14 < i13 - 1) {
                        buffer.position((buffer.position() + rowStride) - i2);
                    }
                    i14++;
                    cropRect = rect;
                }
                i9++;
                i3 = i;
                width = width;
                i4 = 2;
                i5 = 1;
                i7 = 0;
            }
            return bArr;
        } else {
            throw new UnsupportedOperationException("can't convert Image to byte array, format " + image.getFormat());
        }
    }

    public static String m(String str, String str2) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return "";
        }
        File file = new File(str);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            file.mkdirs();
        }
        String str3 = str + File.separator + str2 + Storage.JPEG_SUFFIX;
        int i = 0;
        File file2 = new File(str3);
        while (file2.exists()) {
            i++;
            str2 = str2 + "_" + i;
            str3 = str + File.separator + str2 + Storage.JPEG_SUFFIX;
            file2 = new File(str3);
        }
        return str3;
    }

    public static String n(String str, String str2) {
        String str3;
        Properties properties = new Properties();
        try {
            properties.load(new BufferedInputStream(new FileInputStream(str)));
            str3 = properties.getProperty(str2);
            try {
                if (TextUtils.isEmpty(str3)) {
                    return null;
                }
                return str3;
            } catch (Exception e2) {
                e = e2;
                e.printStackTrace();
                return str3;
            }
        } catch (Exception e3) {
            e = e3;
            str3 = null;
            e.printStackTrace();
            return str3;
        }
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
            return bitmap;
        }
    }
}
