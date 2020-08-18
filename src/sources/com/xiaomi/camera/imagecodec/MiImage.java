package com.xiaomi.camera.imagecodec;

import android.graphics.Rect;
import android.media.Image;
import android.util.Log;
import android.util.Size;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Locale;

public class MiImage {
    private static final String TAG = "MiImage";
    private Rect mCropRect;
    private int mFormat = -1;
    private int mHeight = -1;
    private Plane[] mPlanes;
    private long mTimeStamp;
    private int mWidth = -1;

    public class Plane {
        private ByteBuffer mBuffer;
        private final int mPixelStride;
        private final int mRowStride;

        public Plane(int i, int i2, ByteBuffer byteBuffer) {
            this.mRowStride = i;
            this.mPixelStride = i2;
            this.mBuffer = byteBuffer;
            this.mBuffer.order(ByteOrder.nativeOrder());
        }

        /* access modifiers changed from: private */
        public void clearBuffer() {
            if (this.mBuffer != null) {
                this.mBuffer = null;
            }
        }

        public ByteBuffer getBuffer() {
            return this.mBuffer;
        }

        public int getPixelStride() {
            return this.mPixelStride;
        }

        public int getRowStride() {
            return this.mRowStride;
        }
    }

    public MiImage(Image image) {
        this.mFormat = image.getFormat();
        this.mWidth = image.getWidth();
        this.mHeight = image.getHeight();
        this.mCropRect = image.getCropRect();
        this.mTimeStamp = image.getTimestamp();
        Image.Plane[] planes = image.getPlanes();
        int length = planes.length;
        this.mPlanes = new Plane[length];
        for (int i = 0; i < length; i++) {
            this.mPlanes[i] = new Plane(getEffectivePlaneSizeForImage(i).getWidth() * planes[i].getPixelStride(), planes[i].getPixelStride(), ByteBuffer.allocate(planes[i].getBuffer().limit()));
        }
        fromImage(image);
    }

    private void fromImage(Image image) {
        long j;
        Image.Plane[] planeArr;
        Plane[] planeArr2;
        Size size;
        int i;
        long currentTimeMillis = System.currentTimeMillis();
        Image.Plane[] planes = image.getPlanes();
        Plane[] planes2 = getPlanes();
        int i2 = 0;
        while (i2 < planes2.length) {
            int rowStride = planes[i2].getRowStride();
            int rowStride2 = planes2[i2].getRowStride();
            ByteBuffer buffer = planes[i2].getBuffer();
            ByteBuffer buffer2 = planes2[i2].getBuffer();
            if (planes[i2].getPixelStride() == planes2[i2].getPixelStride()) {
                int position = buffer.position();
                buffer.rewind();
                buffer2.rewind();
                if (rowStride == rowStride2) {
                    buffer2.put(buffer);
                    j = currentTimeMillis;
                } else {
                    int position2 = buffer.position();
                    int position3 = buffer2.position();
                    Size effectivePlaneSizeForImage = getEffectivePlaneSizeForImage(i2);
                    int width = effectivePlaneSizeForImage.getWidth() * planes[i2].getPixelStride();
                    byte[] bArr = new byte[width];
                    j = currentTimeMillis;
                    int i3 = width;
                    int i4 = position3;
                    int i5 = position2;
                    int i6 = 0;
                    while (i6 < effectivePlaneSizeForImage.getHeight()) {
                        buffer.position(i5);
                        buffer2.position(i4);
                        if (i6 == effectivePlaneSizeForImage.getHeight() - 1) {
                            int remaining = buffer.remaining();
                            if (i3 > remaining) {
                                size = effectivePlaneSizeForImage;
                                planeArr2 = planes2;
                                Log.d(TAG, String.format(Locale.ENGLISH, "srcPlane[%d].remain=%d", Integer.valueOf(i2), Integer.valueOf(remaining)));
                            } else {
                                planeArr2 = planes2;
                                size = effectivePlaneSizeForImage;
                                remaining = i3;
                            }
                            int remaining2 = buffer2.remaining();
                            if (remaining > remaining2) {
                                planeArr = planes;
                                i = 0;
                                Log.d(TAG, String.format(Locale.ENGLISH, "dstPlane[%d].remain=%d", Integer.valueOf(i2), Integer.valueOf(remaining2)));
                                i3 = remaining2;
                            } else {
                                planeArr = planes;
                                i = 0;
                                i3 = remaining;
                            }
                        } else {
                            planeArr = planes;
                            planeArr2 = planes2;
                            size = effectivePlaneSizeForImage;
                            i = 0;
                        }
                        buffer.get(bArr, i, i3);
                        buffer2.put(bArr, i, i3);
                        i5 += rowStride;
                        i4 += rowStride2;
                        i6++;
                        effectivePlaneSizeForImage = size;
                        planes2 = planeArr2;
                        planes = planeArr;
                    }
                }
                buffer.position(position);
                buffer2.rewind();
                i2++;
                currentTimeMillis = j;
                planes2 = planes2;
                planes = planes;
            } else {
                throw new IllegalArgumentException("source plane image pixel stride " + planes[i2].getPixelStride() + " must be same as destination image pixel stride " + planes2[i2].getPixelStride());
            }
        }
        String str = TAG;
        Log.d(str, "fromImage: cost=" + (System.currentTimeMillis() - currentTimeMillis));
    }

    private Size getEffectivePlaneSizeForImage(int i) {
        int format = getFormat();
        if (!(format == 1 || format == 2 || format == 3 || format == 4)) {
            if (format == 16) {
                return i == 0 ? new Size(getWidth(), getHeight()) : new Size(getWidth(), getHeight() / 2);
            }
            if (format != 17) {
                if (!(format == 20 || format == 32 || format == 256)) {
                    if (format != 842094169) {
                        if (format == 34) {
                            return new Size(0, 0);
                        }
                        if (format != 35) {
                            if (!(format == 37 || format == 38)) {
                                throw new UnsupportedOperationException(String.format("Invalid image format %d", Integer.valueOf(getFormat())));
                            }
                        }
                    }
                }
            }
            return i == 0 ? new Size(getWidth(), getHeight()) : new Size(getWidth() / 2, getHeight() / 2);
        }
        return new Size(getWidth(), getHeight());
    }

    public void close() {
        if (this.mPlanes != null) {
            int i = 0;
            while (true) {
                Plane[] planeArr = this.mPlanes;
                if (i < planeArr.length) {
                    if (planeArr[i] != null) {
                        planeArr[i].clearBuffer();
                        this.mPlanes[i] = null;
                    }
                    i++;
                } else {
                    return;
                }
            }
        }
    }

    public int getFormat() {
        return this.mFormat;
    }

    public int getHeight() {
        return this.mHeight;
    }

    public Plane[] getPlanes() {
        return this.mPlanes;
    }

    public int getWidth() {
        return this.mWidth;
    }

    public void toImage(Image image) {
        Plane[] planeArr;
        Image.Plane[] planeArr2;
        long j;
        boolean z;
        Plane[] planeArr3;
        Image.Plane[] planeArr4;
        Size size;
        int i;
        if (getFormat() == image.getFormat()) {
            Size size2 = new Size(getWidth(), getHeight());
            Size size3 = new Size(image.getWidth(), image.getHeight());
            if (size2.equals(size3)) {
                long currentTimeMillis = System.currentTimeMillis();
                Plane[] planes = getPlanes();
                Image.Plane[] planes2 = image.getPlanes();
                boolean z2 = true;
                int length = planes.length - 1;
                while (length >= 0) {
                    int rowStride = planes[length].getRowStride();
                    int rowStride2 = planes2[length].getRowStride();
                    ByteBuffer buffer = planes[length].getBuffer();
                    ByteBuffer buffer2 = planes2[length].getBuffer();
                    if (planes[length].getPixelStride() == planes2[length].getPixelStride()) {
                        int position = buffer.position();
                        buffer.rewind();
                        buffer2.rewind();
                        if (rowStride == rowStride2) {
                            buffer2.put(buffer);
                            j = currentTimeMillis;
                            planeArr = planes;
                            planeArr2 = planes2;
                            z = z2;
                        } else {
                            int position2 = buffer.position();
                            int position3 = buffer2.position();
                            Size effectivePlaneSizeForImage = getEffectivePlaneSizeForImage(length);
                            int width = effectivePlaneSizeForImage.getWidth() * planes[length].getPixelStride();
                            byte[] bArr = new byte[width];
                            j = currentTimeMillis;
                            int i2 = position3;
                            int i3 = width;
                            int i4 = position2;
                            int i5 = 0;
                            while (i5 < effectivePlaneSizeForImage.getHeight()) {
                                buffer.position(i4);
                                buffer2.position(i2);
                                if (i5 == effectivePlaneSizeForImage.getHeight() - 1) {
                                    int remaining = buffer.remaining();
                                    if (i3 > remaining) {
                                        size = effectivePlaneSizeForImage;
                                        planeArr4 = planes2;
                                        Log.d(TAG, String.format(Locale.ENGLISH, "srcPlane[%d].remain=%d", Integer.valueOf(length), Integer.valueOf(remaining)));
                                    } else {
                                        planeArr4 = planes2;
                                        size = effectivePlaneSizeForImage;
                                        remaining = i3;
                                    }
                                    int remaining2 = buffer2.remaining();
                                    if (remaining > remaining2) {
                                        planeArr3 = planes;
                                        i = 0;
                                        Log.d(TAG, String.format(Locale.ENGLISH, "dstPlane[%d].remain=%d", Integer.valueOf(length), Integer.valueOf(remaining2)));
                                        i3 = remaining2;
                                    } else {
                                        planeArr3 = planes;
                                        i = 0;
                                        i3 = remaining;
                                    }
                                } else {
                                    planeArr3 = planes;
                                    planeArr4 = planes2;
                                    size = effectivePlaneSizeForImage;
                                    i = 0;
                                }
                                buffer.get(bArr, i, i3);
                                buffer2.put(bArr, i, i3);
                                i4 += rowStride;
                                i2 += rowStride2;
                                i5++;
                                effectivePlaneSizeForImage = size;
                                planes2 = planeArr4;
                                planes = planeArr3;
                            }
                            planeArr = planes;
                            planeArr2 = planes2;
                            z = true;
                        }
                        buffer.position(position);
                        buffer2.rewind();
                        length--;
                        z2 = z;
                        currentTimeMillis = j;
                        planes2 = planeArr2;
                        planes = planeArr;
                    } else {
                        throw new IllegalArgumentException("source plane image pixel stride " + planes[length].getPixelStride() + " must be same as destination image pixel stride " + planes2[length].getPixelStride());
                    }
                }
                Log.d(TAG, "toImage: cost=" + (System.currentTimeMillis() - currentTimeMillis));
                return;
            }
            throw new IllegalArgumentException("source image size " + size2 + " is different with destination image size " + size3);
        }
        throw new IllegalArgumentException("src and dst images should have the same format");
    }
}
