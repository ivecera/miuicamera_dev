package com.xiaomi.camera.imagecodec;

import android.annotation.TargetApi;
import android.media.Image;
import android.media.ImageReader;
import android.media.ImageWriter;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.LongSparseArray;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ImagePool {
    private static final String TAG = "ImagePool";
    private static final int TRIM_POOL_IMAGES_COUNT = 10;
    private static boolean sInited = false;
    private static int sMaxAcquireImageCount = 30;
    private static int sMaxDequeueImageCount = 4;
    private Map<ImageFormat, Integer> mAcquiredImageCountMap = new HashMap();
    private Map<Image, ImageFormat> mHoldImages = new HashMap();
    private ImageReader.OnImageAvailableListener mImageAvailableListener = new ImageReader.OnImageAvailableListener() {
        /* class com.xiaomi.camera.imagecodec.ImagePool.AnonymousClass1 */

        public void onImageAvailable(ImageReader imageReader) {
            synchronized (ImagePool.this.mImageLock) {
                Image acquireNextImage = imageReader.acquireNextImage();
                ImagePool.this.mImageLongSparseArray.append(acquireNextImage.getTimestamp(), acquireNextImage);
                ImagePool.this.mImageLock.notify();
            }
        }
    };
    /* access modifiers changed from: private */
    public final Object mImageLock = new Object();
    /* access modifiers changed from: private */
    public LongSparseArray<Image> mImageLongSparseArray = new LongSparseArray<>();
    /* access modifiers changed from: private */
    public HandlerThread mImageReaderHandlerThread = new HandlerThread("ImageReaderHandlerThread");
    private Map<ImageFormat, ImageReader> mImageReaderMap = new HashMap();
    /* access modifiers changed from: private */
    public HandlerThread mImageWriterHandlerThread = new HandlerThread("ImageWriterHandlerThread");
    private Map<ImageFormat, ImageWriter> mImageWriterMap = new HashMap();
    private Map<ImageFormat, Integer> mPooledImageCountMap = new HashMap();
    private final Object mQueueSizeLock = new Object();

    public static class ImageFormat {
        private int mFormat;
        private int mHeight;
        private int mWidth;

        public ImageFormat(int i, int i2, int i3) {
            this.mWidth = i;
            this.mHeight = i2;
            this.mFormat = i3;
        }

        public boolean equals(@Nullable Object obj) {
            if (!(obj instanceof ImageFormat)) {
                return super.equals(obj);
            }
            ImageFormat imageFormat = (ImageFormat) obj;
            return this.mWidth == imageFormat.getWidth() && this.mHeight == imageFormat.getHeight() && this.mFormat == imageFormat.getFormat();
        }

        public int getFormat() {
            return this.mFormat;
        }

        public int getHeight() {
            return this.mHeight;
        }

        public int getWidth() {
            return this.mWidth;
        }

        public int hashCode() {
            int i = this.mWidth;
            int i2 = (i >>> 8) | (i << 8);
            int i3 = this.mHeight;
            int i4 = i3 << 16;
            return (this.mFormat ^ i2) ^ ((i3 >>> 16) | i4);
        }
    }

    static class ImagePoolHolder {
        static ImagePool sInstance = new ImagePool();

        static {
            sInstance.mImageReaderHandlerThread.start();
            sInstance.mImageWriterHandlerThread.start();
        }

        ImagePoolHolder() {
        }
    }

    private int changeAcquiredImageCountLocked(ImageFormat imageFormat, int i) {
        Integer num = this.mAcquiredImageCountMap.get(imageFormat);
        if (num == null) {
            num = 0;
        }
        Integer valueOf = Integer.valueOf(num.intValue() + i);
        if (valueOf.intValue() < 0) {
            valueOf = 0;
        }
        this.mAcquiredImageCountMap.put(imageFormat, valueOf);
        return valueOf.intValue();
    }

    private int changePooledImageCountLocked(ImageFormat imageFormat, int i) {
        Integer num = this.mPooledImageCountMap.get(imageFormat);
        if (num == null) {
            num = 0;
        }
        Integer valueOf = Integer.valueOf(num.intValue() + i);
        if (valueOf.intValue() < 0) {
            valueOf = 0;
        }
        this.mPooledImageCountMap.put(imageFormat, valueOf);
        return valueOf.intValue();
    }

    private int getAcquiredImageCountLocked(ImageFormat imageFormat) {
        Integer num;
        if (imageFormat == null || (num = this.mAcquiredImageCountMap.get(imageFormat)) == null) {
            return 0;
        }
        return num.intValue();
    }

    private ImageWriter getImageWriter(@NonNull ImageFormat imageFormat) {
        if (this.mImageWriterMap.containsKey(imageFormat)) {
            return this.mImageWriterMap.get(imageFormat);
        }
        ImageReader newInstance = ImageReader.newInstance(imageFormat.getWidth(), imageFormat.getHeight(), imageFormat.getFormat(), sMaxAcquireImageCount);
        newInstance.setOnImageAvailableListener(this.mImageAvailableListener, new Handler(this.mImageReaderHandlerThread.getLooper()));
        ImageWriter newInstance2 = ImageWriter.newInstance(newInstance.getSurface(), sMaxDequeueImageCount);
        newInstance2.setOnImageReleasedListener(null, new Handler(this.mImageWriterHandlerThread.getLooper()));
        this.mImageReaderMap.put(imageFormat, newInstance);
        this.mImageWriterMap.put(imageFormat, newInstance2);
        String str = TAG;
        Log.d(str, "getImageWriter: mImageWriterMap.size = " + this.mImageWriterMap.size());
        if (this.mImageReaderMap.size() > 10) {
            String str2 = TAG;
            Log.e(str2, "getImageWriter: there are too much ImageWriter and ImageReader instance in map, size is : " + this.mImageReaderMap.size());
        }
        return newInstance2;
    }

    public static ImagePool getInstance() {
        return ImagePoolHolder.sInstance;
    }

    public static void init(int i, int i2) {
        if (!sInited) {
            if (i <= 0 || i2 <= 0) {
                String str = TAG;
                Log.e(str, "invalid parameter: maxAcquireCount=" + i + " maxDequeueCount=" + i2);
            } else if (i + i2 >= 64) {
                Log.e(TAG, String.format(Locale.ENGLISH, "maxAcquireCount(%d) + maxDequeueCount(%d) should not be larger than 64", Integer.valueOf(i), Integer.valueOf(i)));
            } else {
                sInited = true;
                sMaxAcquireImageCount = i;
                sMaxDequeueImageCount = i2;
                String str2 = TAG;
                Log.d(str2, "init: maxAcquireCount=" + i + " maxDequeueCount=" + i2);
            }
        }
    }

    private boolean needTrimPoolBuffer() {
        int min = Math.min(sMaxAcquireImageCount, 10);
        synchronized (this.mQueueSizeLock) {
            for (Map.Entry<ImageFormat, Integer> entry : this.mPooledImageCountMap.entrySet()) {
                if (entry.getValue() != null && entry.getValue().intValue() >= min) {
                    return true;
                }
            }
            for (Map.Entry<ImageFormat, Integer> entry2 : this.mAcquiredImageCountMap.entrySet()) {
                if (entry2.getValue() != null && entry2.getValue().intValue() >= min) {
                    return true;
                }
            }
            return false;
        }
    }

    public void clear() {
        Log.d(TAG, "clear: E");
        int i = 0;
        while (i < this.mImageLongSparseArray.size()) {
            try {
                this.mImageLongSparseArray.valueAt(i).close();
                i++;
            } catch (Exception e2) {
                Log.e(TAG, "clear ImagePool cause error: ", e2);
            }
        }
        for (ImageReader imageReader : this.mImageReaderMap.values()) {
            imageReader.close();
        }
        this.mImageReaderMap.clear();
        for (ImageWriter imageWriter : this.mImageWriterMap.values()) {
            imageWriter.close();
        }
        this.mImageWriterMap.clear();
        synchronized (this.mQueueSizeLock) {
            this.mAcquiredImageCountMap.clear();
            this.mPooledImageCountMap.clear();
        }
        Log.d(TAG, "clear: X");
    }

    public Image getAnEmptyImage(@NonNull ImageFormat imageFormat) {
        Image dequeueInputImage = getImageWriter(imageFormat).dequeueInputImage();
        dequeueInputImage.setTimestamp(System.currentTimeMillis());
        return dequeueInputImage;
    }

    public Image getImage(long j) {
        Image image = this.mImageLongSparseArray.get(j);
        this.mImageLongSparseArray.remove(j);
        return image;
    }

    public void holdImage(Image image) {
        synchronized (this.mQueueSizeLock) {
            if (image != null) {
                ImageFormat imageQueueKey = toImageQueueKey(image);
                this.mHoldImages.put(image, imageQueueKey);
                int changeAcquiredImageCountLocked = changeAcquiredImageCountLocked(imageQueueKey, 1);
                String str = TAG;
                Log.d(str, "holdImage: image: " + image + " | " + image.getTimestamp() + " qSize: " + changeAcquiredImageCountLocked);
                this.mQueueSizeLock.notify();
            }
        }
    }

    public boolean isImageQueueFull(ImageFormat imageFormat, int i) {
        boolean z;
        synchronized (this.mQueueSizeLock) {
            int i2 = sMaxAcquireImageCount - i;
            z = false;
            if (getAcquiredImageCountLocked(imageFormat) >= Math.max(0, i2)) {
                z = true;
            }
        }
        return z;
    }

    public void queueImage(Image image) {
        long timestamp = image.getTimestamp();
        if (this.mImageLongSparseArray.get(timestamp) == null) {
            ImageFormat imageQueueKey = toImageQueueKey(image);
            synchronized (this.mImageLock) {
                ImageWriter imageWriter = getImageWriter(imageQueueKey);
                String str = TAG;
                Log.d(str, "queueImage: start. image: " + image + " | " + image.getTimestamp());
                imageWriter.queueInputImage(image);
                try {
                    this.mImageLock.wait();
                } catch (InterruptedException e2) {
                    e2.printStackTrace();
                }
                Log.d(TAG, "queueImage: end");
            }
            synchronized (this.mQueueSizeLock) {
                changePooledImageCountLocked(imageQueueKey, 1);
            }
            return;
        }
        throw new RuntimeException("Image has exist: " + timestamp);
    }

    public void releaseImage(Image image) {
        synchronized (this.mQueueSizeLock) {
            if (image != null) {
                ImageFormat imageFormat = this.mHoldImages.get(image);
                if (imageFormat != null) {
                    this.mHoldImages.remove(image);
                    int changeAcquiredImageCountLocked = changeAcquiredImageCountLocked(imageFormat, -1);
                    String str = TAG;
                    Log.d(str, "releaseImage: image: " + image + " qSize: " + changeAcquiredImageCountLocked);
                    this.mQueueSizeLock.notify();
                } else {
                    String str2 = TAG;
                    Log.w(str2, "releaseImage: not hold image " + image);
                }
            }
        }
    }

    public ImageFormat toImageQueueKey(Image image) {
        if (image != null) {
            return new ImageFormat(image.getWidth(), image.getHeight(), image.getFormat());
        }
        return null;
    }

    @TargetApi(28)
    public void trimPoolBuffer() {
        Log.d(TAG, "trimPoolBuffer: E");
        for (ImageReader imageReader : this.mImageReaderMap.values()) {
            imageReader.discardFreeBuffers();
        }
        synchronized (this.mQueueSizeLock) {
            this.mPooledImageCountMap.clear();
        }
        Log.d(TAG, "trimPoolBuffer: X");
    }

    public void trimPoolBufferIfNeeded() {
        if (needTrimPoolBuffer()) {
            Log.d(TAG, "trimPoolBufferIfNeeded");
            trimPoolBuffer();
        }
    }

    public void waitIfImageQueueFull(ImageFormat imageFormat, int i, int i2) {
        int max = Math.max(0, sMaxAcquireImageCount - i);
        synchronized (this.mQueueSizeLock) {
            while (getAcquiredImageCountLocked(imageFormat) >= max) {
                if (i2 > 0) {
                    try {
                        this.mQueueSizeLock.wait((long) i2);
                    } catch (InterruptedException e2) {
                        Log.e(TAG, e2.getMessage(), e2);
                    }
                } else {
                    this.mQueueSizeLock.wait();
                }
            }
        }
    }
}
