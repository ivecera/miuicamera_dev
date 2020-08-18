package com.ss.android.ttve.nativePort;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.support.annotation.Keep;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.io.IOException;

@Keep
public class TEImageFactory {
    private static final String BITMAP = "bitmap";

    @Keep
    public static class ImageInfo {
        Bitmap bitmap;
        int height;
        String mimeType;
        int rotation = 0;
        int width;

        public Bitmap getBitmap() {
            return this.bitmap;
        }

        public int getHeight() {
            return this.height;
        }

        public String getMimeType() {
            return this.mimeType;
        }

        public int getRotation() {
            return this.rotation;
        }

        public int getWidth() {
            return this.width;
        }
    }

    public static ImageInfo decodeFile(@NonNull String str) {
        return decodeFile(str, null);
    }

    public static ImageInfo decodeFile(@NonNull String str, @Nullable BitmapFactory.Options options) {
        if (options == null) {
            options = new BitmapFactory.Options();
        }
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap decodeFile = BitmapFactory.decodeFile(str, options);
        if (decodeFile == null) {
            return null;
        }
        Bitmap.Config config = decodeFile.getConfig();
        Bitmap.Config config2 = Bitmap.Config.ARGB_8888;
        if (config != config2) {
            Bitmap copy = decodeFile.copy(config2, false);
            recycleBitmap(decodeFile);
            decodeFile = copy;
        }
        ImageInfo imageInfo = new ImageInfo();
        imageInfo.width = decodeFile.getWidth();
        imageInfo.height = decodeFile.getHeight();
        imageInfo.bitmap = decodeFile;
        imageInfo.mimeType = BITMAP;
        imageInfo.rotation = getRotation(str);
        return imageInfo;
    }

    public static ImageInfo getImageInfo(@NonNull String str) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(str, options);
        ImageInfo imageInfo = new ImageInfo();
        imageInfo.width = options.outWidth;
        imageInfo.height = options.outHeight;
        imageInfo.mimeType = options.outMimeType;
        return imageInfo;
    }

    public static int getRotation(String str) {
        ExifInterface exifInterface;
        try {
            exifInterface = new ExifInterface(str);
        } catch (IOException e2) {
            e2.printStackTrace();
            exifInterface = null;
        }
        if (exifInterface == null) {
            return 0;
        }
        int attributeInt = exifInterface.getAttributeInt("Orientation", 0);
        if (attributeInt == 3) {
            return 180;
        }
        if (attributeInt != 6) {
            return attributeInt != 8 ? 0 : 270;
        }
        return 90;
    }

    public static void recycleBitmap(Bitmap bitmap) {
        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
        }
    }
}
