package com.ss.android.vesdk.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.support.annotation.Keep;
import android.support.annotation.Nullable;
import com.ss.android.medialib.common.LogUtil;
import java.io.IOException;

@Keep
public class BitmapLoader {
    private static int calculateInSampleSize(String str, int i, int i2) {
        if (i == -1 || i2 == -1) {
            return 1;
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(str, options);
        int i3 = options.outHeight;
        int i4 = options.outWidth;
        int i5 = 0;
        if (i3 > i2 || i4 > i) {
            i5 = (int) Math.min(((float) i3) / ((float) i2), ((float) i4) / ((float) i));
        }
        return Math.max(1, i5);
    }

    @Keep
    public static Bitmap loadBitmap(String str, int i, int i2) {
        ExifInterface exifInterface;
        Bitmap bitmap;
        BitmapFactory.Options options = new BitmapFactory.Options();
        if (i > 0 && i2 > 0) {
            options.inSampleSize = calculateInSampleSize(str, i, i2);
        }
        try {
            Bitmap decodeFile = BitmapFactory.decodeFile(str, options);
            try {
                exifInterface = new ExifInterface(str);
            } catch (IOException e2) {
                e2.printStackTrace();
                exifInterface = null;
            }
            int i3 = 0;
            if (exifInterface != null) {
                int attributeInt = exifInterface.getAttributeInt("Orientation", 0);
                if (attributeInt == 3) {
                    i3 = 180;
                } else if (attributeInt == 6) {
                    i3 = 90;
                } else if (attributeInt == 8) {
                    i3 = 270;
                }
            }
            if (i3 != 0) {
                Matrix matrix = new Matrix();
                matrix.postRotate((float) i3);
                bitmap = Bitmap.createBitmap(decodeFile, 0, 0, decodeFile.getWidth(), decodeFile.getHeight(), matrix, true);
                if (decodeFile != null && !decodeFile.isRecycled()) {
                    decodeFile.recycle();
                }
            } else {
                bitmap = decodeFile;
            }
            return makeDimensionEven(bitmap);
        } catch (OutOfMemoryError e3) {
            LogUtil.e("loadBitmap", e3.getMessage());
            return null;
        }
    }

    private static Bitmap makeDimensionEven(@Nullable Bitmap bitmap) {
        int i;
        boolean z;
        if (bitmap == null) {
            return null;
        }
        try {
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            if ((bitmap.getWidth() & 1) == 1) {
                i = bitmap.getWidth() - 1;
                z = true;
            } else {
                i = width;
                z = false;
            }
            if ((bitmap.getHeight() & 1) == 1) {
                height = bitmap.getHeight() - 1;
                z = true;
            }
            if (!z) {
                return bitmap;
            }
            Bitmap createBitmap = Bitmap.createBitmap(bitmap, 0, 0, i, height);
            bitmap.recycle();
            return createBitmap;
        } catch (OutOfMemoryError e2) {
            LogUtil.e("makeDimensionEven", e2.getMessage());
            return null;
        }
    }
}
