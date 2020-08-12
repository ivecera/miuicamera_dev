package com.xiaomi.camera.base;

import android.media.Image;
import android.util.Size;
import com.android.camera.log.Log;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.util.Locale;
import libcore.io.Memory;

public final class ImageUtil {
    private static final String TAG = "ImageUtil";

    private ImageUtil() {
    }

    private static void directByteBufferCopy(ByteBuffer byteBuffer, int i, ByteBuffer byteBuffer2, int i2, int i3) {
        Memory.memmove(byteBuffer2, i2, byteBuffer, i, (long) i3);
    }

    /* JADX WARNING: Removed duplicated region for block: B:23:0x00fe A[SYNTHETIC, Splitter:B:23:0x00fe] */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x010e A[SYNTHETIC, Splitter:B:28:0x010e] */
    /* JADX WARNING: Removed duplicated region for block: B:34:? A[RETURN, SYNTHETIC] */
    public static boolean dumpYuvImage(Image image, String str) {
        Log.d(TAG, "dumpYuvImage start");
        String str2 = "sdcard/DCIM/Camera/" + str + "_" + image.getWidth() + "x" + image.getHeight();
        int format = image.getFormat();
        if (format != 17 && format != 35) {
            return false;
        }
        FileOutputStream fileOutputStream = null;
        try {
            FileOutputStream fileOutputStream2 = new FileOutputStream(str2 + ".yuv");
            try {
                ByteBuffer buffer = image.getPlanes()[0].getBuffer();
                ByteBuffer buffer2 = image.getPlanes()[2].getBuffer();
                image.getWidth();
                int rowStride = image.getPlanes()[0].getRowStride() * image.getHeight();
                int limit = buffer.limit();
                int limit2 = buffer2.limit();
                buffer.rewind();
                buffer2.rewind();
                Log.d(TAG, "dumpingYuvImage: size=" + image.getWidth() + "x" + image.getHeight() + " stride=" + image.getPlanes()[2].getRowStride());
                byte[] bArr = new byte[((rowStride >> 1) + rowStride)];
                buffer.rewind();
                buffer2.rewind();
                buffer.get(bArr, 0, limit);
                buffer2.get(bArr, rowStride, limit2);
                fileOutputStream2.write(bArr);
                buffer.rewind();
                buffer2.rewind();
                try {
                    fileOutputStream2.flush();
                    fileOutputStream2.close();
                } catch (Exception e2) {
                    Log.e(TAG, "Failed to flush/close stream", e2);
                }
                return true;
            } catch (Exception e3) {
                e = e3;
                fileOutputStream = fileOutputStream2;
                try {
                    Log.e(TAG, "Failed to write image", e);
                    if (fileOutputStream != null) {
                        return false;
                    }
                    try {
                        fileOutputStream.flush();
                        fileOutputStream.close();
                        return false;
                    } catch (Exception e4) {
                        Log.e(TAG, "Failed to flush/close stream", e4);
                        return false;
                    }
                } catch (Throwable th) {
                    th = th;
                    fileOutputStream2 = fileOutputStream;
                    if (fileOutputStream2 != null) {
                        try {
                            fileOutputStream2.flush();
                            fileOutputStream2.close();
                        } catch (Exception e5) {
                            Log.e(TAG, "Failed to flush/close stream", e5);
                        }
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                if (fileOutputStream2 != null) {
                }
                throw th;
            }
        } catch (Exception e6) {
            e = e6;
            Log.e(TAG, "Failed to write image", e);
            if (fileOutputStream != null) {
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:23:0x0109 A[SYNTHETIC, Splitter:B:23:0x0109] */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x011a A[SYNTHETIC, Splitter:B:29:0x011a] */
    public static boolean dumpYuvImageAppendWH(Image image, String str) {
        FileOutputStream fileOutputStream;
        Log.d(TAG, "dumpYuvImageAppendWH start :" + str);
        String str2 = "sdcard/DCIM/Camera/" + str;
        int format = image.getFormat();
        if (format == 17 || format == 35) {
            FileOutputStream fileOutputStream2 = null;
            try {
                fileOutputStream = new FileOutputStream(str2 + ".yuv", true);
            } catch (Exception e2) {
                e = e2;
                try {
                    Log.e(TAG, "Failed to write image", e);
                    if (fileOutputStream2 != null) {
                        try {
                            fileOutputStream2.flush();
                            fileOutputStream2.close();
                        } catch (Exception e3) {
                            Log.e(TAG, "Failed to flush/close stream", e3);
                        }
                    }
                    return false;
                } catch (Throwable th) {
                    th = th;
                    fileOutputStream = fileOutputStream2;
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.flush();
                            fileOutputStream.close();
                        } catch (Exception e4) {
                            Log.e(TAG, "Failed to flush/close stream", e4);
                        }
                    }
                    throw th;
                }
            }
            try {
                ByteBuffer buffer = image.getPlanes()[0].getBuffer();
                ByteBuffer buffer2 = image.getPlanes()[2].getBuffer();
                int width = image.getWidth();
                int height = image.getHeight();
                int rowStride = image.getPlanes()[0].getRowStride() * height;
                int limit = buffer.limit();
                int limit2 = buffer2.limit();
                buffer.rewind();
                buffer2.rewind();
                Log.d(TAG, "dumpYuvImageAppendWH: size=" + image.getWidth() + "x" + image.getHeight() + " stride=" + image.getPlanes()[2].getRowStride());
                byte[] bArr = new byte[((rowStride >> 1) + rowStride)];
                buffer.rewind();
                buffer2.rewind();
                buffer.get(bArr, 0, limit);
                buffer2.get(bArr, rowStride, limit2);
                fileOutputStream.write(toBytes(width));
                fileOutputStream.write(toBytes(height));
                fileOutputStream.write(bArr);
                buffer.rewind();
                buffer2.rewind();
                try {
                    fileOutputStream.flush();
                    fileOutputStream.close();
                    return true;
                } catch (Exception e5) {
                    Log.e(TAG, "Failed to flush/close stream", e5);
                    return true;
                }
            } catch (Exception e6) {
                e = e6;
                fileOutputStream2 = fileOutputStream;
                Log.e(TAG, "Failed to write image", e);
                if (fileOutputStream2 != null) {
                }
                return false;
            } catch (Throwable th2) {
                th = th2;
                if (fileOutputStream != null) {
                }
                throw th;
            }
        }
        return false;
    }

    private static Size getEffectivePlaneSizeForImage(Image image, int i) {
        int format = image.getFormat();
        if (!(format == 1 || format == 2 || format == 3 || format == 4)) {
            if (format == 16) {
                return i == 0 ? new Size(image.getWidth(), image.getHeight()) : new Size(image.getWidth(), image.getHeight() / 2);
            }
            if (format != 17) {
                if (!(format == 20 || format == 32 || format == 256)) {
                    if (format != 842094169) {
                        if (format == 34) {
                            return new Size(0, 0);
                        }
                        if (format != 35) {
                            if (!(format == 37 || format == 38)) {
                                throw new UnsupportedOperationException(String.format("Invalid image format %d", Integer.valueOf(image.getFormat())));
                            }
                        }
                    }
                }
            }
            return i == 0 ? new Size(image.getWidth(), image.getHeight()) : new Size(image.getWidth() / 2, image.getHeight() / 2);
        }
        return new Size(image.getWidth(), image.getHeight());
    }

    public static byte[] getFirstPlane(Image image) {
        Image.Plane[] planes = image.getPlanes();
        if (planes.length <= 0) {
            return null;
        }
        ByteBuffer buffer = planes[0].getBuffer();
        byte[] bArr = new byte[buffer.remaining()];
        buffer.get(bArr);
        return bArr;
    }

    public static byte[] getYuvData(Image image) {
        if (image == null || 35 != image.getFormat()) {
            String str = TAG;
            Log.e(str, "getYuvData: " + image);
            return null;
        }
        ByteBuffer buffer = image.getPlanes()[0].getBuffer();
        ByteBuffer buffer2 = image.getPlanes()[2].getBuffer();
        int limit = buffer.limit();
        int limit2 = buffer2.limit();
        byte[] bArr = new byte[(limit + limit2 + 1)];
        buffer.get(bArr, 0, limit);
        buffer2.get(bArr, limit, limit2);
        bArr[bArr.length - 1] = bArr[bArr.length - 3];
        return bArr;
    }

    public static void imageCopy(Image image, Image image2) {
        int remaining;
        if (image == null || image2 == null) {
            throw new IllegalArgumentException("Images should be non-null");
        } else if (image.getFormat() != image2.getFormat()) {
            throw new IllegalArgumentException("Src and dst images should have the same format");
        } else if (image.getFormat() == 34 || image2.getFormat() == 34) {
            throw new IllegalArgumentException("PRIVATE format images are not copyable");
        } else if (image.getFormat() != 36) {
            Size size = new Size(image.getWidth(), image.getHeight());
            Size size2 = new Size(image2.getWidth(), image2.getHeight());
            if (size.equals(size2)) {
                Image.Plane[] planes = image.getPlanes();
                Image.Plane[] planes2 = image2.getPlanes();
                int i = 0;
                while (i < planes.length) {
                    int rowStride = planes[i].getRowStride();
                    int rowStride2 = planes2[i].getRowStride();
                    ByteBuffer buffer = planes[i].getBuffer();
                    ByteBuffer buffer2 = planes2[i].getBuffer();
                    if (!buffer.isDirect() || !buffer2.isDirect()) {
                        throw new IllegalArgumentException("Source and destination ByteBuffers must be direct byteBuffer!");
                    } else if (planes[i].getPixelStride() == planes2[i].getPixelStride()) {
                        int position = buffer.position();
                        buffer.rewind();
                        buffer2.rewind();
                        if (rowStride == rowStride2) {
                            buffer2.put(buffer);
                        } else {
                            int position2 = buffer.position();
                            int position3 = buffer2.position();
                            Size effectivePlaneSizeForImage = getEffectivePlaneSizeForImage(image, i);
                            int width = effectivePlaneSizeForImage.getWidth() * planes[i].getPixelStride();
                            int i2 = position3;
                            int i3 = position2;
                            for (int i4 = 0; i4 < effectivePlaneSizeForImage.getHeight(); i4++) {
                                if (i4 == effectivePlaneSizeForImage.getHeight() - 1 && width > (remaining = buffer.remaining() - i3)) {
                                    width = remaining;
                                }
                                directByteBufferCopy(buffer, i3, buffer2, i2, width);
                                i3 += rowStride;
                                i2 += rowStride2;
                            }
                        }
                        buffer.position(position);
                        buffer2.rewind();
                        i++;
                    } else {
                        throw new IllegalArgumentException("Source plane image pixel stride " + planes[i].getPixelStride() + " must be same as destination image pixel stride " + planes2[i].getPixelStride());
                    }
                }
                return;
            }
            throw new IllegalArgumentException("source image size " + size + " is different with destination image size " + size2);
        } else {
            throw new IllegalArgumentException("Copy of RAW_OPAQUE format has not been implemented");
        }
    }

    public static ByteBuffer removePadding(Image.Plane plane, int i, int i2) {
        int remaining;
        long currentTimeMillis = System.currentTimeMillis();
        ByteBuffer buffer = plane.getBuffer();
        int rowStride = plane.getRowStride();
        int pixelStride = plane.getPixelStride();
        Log.d(TAG, String.format(Locale.ENGLISH, "removePadding: rowStride=%d pixelStride=%d size=%dx%d", Integer.valueOf(rowStride), Integer.valueOf(pixelStride), Integer.valueOf(i), Integer.valueOf(i2)));
        int i3 = i * pixelStride;
        if (rowStride == i3) {
            return buffer;
        }
        int i4 = i3 * i2;
        ByteBuffer allocateDirect = ByteBuffer.allocateDirect(i4);
        int position = buffer.position();
        int position2 = allocateDirect.position();
        for (int i5 = 0; i5 < i2; i5++) {
            if (i5 == i2 - 1 && i3 > (remaining = buffer.remaining() - position)) {
                String str = TAG;
                Log.d(str, "removePadding: " + remaining + "/" + i3);
                i3 = remaining;
            }
            directByteBufferCopy(buffer, position, allocateDirect, position2, i3);
            position += rowStride;
            position2 += i3;
        }
        if (position2 < i4) {
            String str2 = TAG;
            Log.d(str2, "removePadding: add data: " + position2 + "|" + i4);
            while (position2 < i4) {
                allocateDirect.put(position2, allocateDirect.get(position2 - 2));
                position2++;
            }
        }
        String str3 = TAG;
        Log.v(str3, "removePadding: cost=" + (System.currentTimeMillis() - currentTimeMillis));
        return allocateDirect;
    }

    private static byte[] toBytes(int i) {
        return new byte[]{(byte) (i >> 24), (byte) (i >> 16), (byte) (i >> 8), (byte) i};
    }

    private static void updateImagePlane(Image.Plane plane, int i, int i2, byte[] bArr, boolean z, int i3) {
        int i4 = i3;
        ByteBuffer buffer = plane.getBuffer();
        buffer.rewind();
        int rowStride = plane.getRowStride();
        int pixelStride = plane.getPixelStride();
        int i5 = i * pixelStride;
        Log.d(TAG, String.format(Locale.ENGLISH, "updateImagePlane: size=%dx%d offset=%d length=%d rowStride=%d pixelStride=%d", Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3), Integer.valueOf(bArr.length), Integer.valueOf(rowStride), Integer.valueOf(pixelStride)));
        int i6 = i5 * i2;
        int length = bArr.length - i4;
        if (length >= i6) {
            if (rowStride == i5) {
                int min = Math.min(buffer.remaining(), i6);
                String str = TAG;
                Log.d(str, "updateImagePlane: " + min);
                buffer.put(bArr, i4, min);
            } else if (z) {
                buffer.put(bArr, i4, Math.min(buffer.remaining(), (rowStride * (i2 - 1)) + i));
            } else {
                int position = buffer.position();
                int i7 = i5;
                for (int i8 = 0; i8 < i2; i8++) {
                    buffer.position(position);
                    if (i8 == i2 - 1 && (i7 = Math.min(buffer.remaining(), i5)) < i5) {
                        String str2 = TAG;
                        Log.d(str2, "updateImagePlane: " + i7 + "/" + i5);
                        i7 = buffer.remaining();
                    }
                    buffer.put(bArr, i4, i7);
                    i4 += i5;
                    position += rowStride;
                }
            }
            buffer.rewind();
            return;
        }
        throw new RuntimeException(String.format(Locale.ENGLISH, "buffer size should be at least %d but actual size is %d", Integer.valueOf(i6), Integer.valueOf(length)));
    }

    public static void updateYuvImage(Image image, byte[] bArr, boolean z) {
        if (image == null || 35 != image.getFormat()) {
            String str = TAG;
            Log.e(str, "updateYuvImage: " + image);
            return;
        }
        Image.Plane[] planes = image.getPlanes();
        updateImagePlane(planes[0], image.getWidth(), image.getHeight(), bArr, z, 0);
        int width = image.getWidth() * image.getHeight();
        if (z) {
            width = (planes[2].getRowStride() * (image.getHeight() - 1)) + image.getWidth();
        }
        updateImagePlane(planes[2], image.getWidth() / 2, image.getHeight() / 2, bArr, z, width);
    }
}
