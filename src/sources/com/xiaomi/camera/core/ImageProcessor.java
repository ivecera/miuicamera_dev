package com.xiaomi.camera.core;

import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.params.OutputConfiguration;
import android.media.Image;
import android.media.ImageReader;
import android.os.ConditionVariable;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.annotation.NonNull;
import android.util.Size;
import com.android.camera.log.Log;
import com.xiaomi.camera.core.CaptureData;
import com.xiaomi.camera.imagecodec.ImagePool;
import com.xiaomi.engine.BufferFormat;
import com.xiaomi.engine.TaskSession;
import com.xiaomi.protocol.ICustomCaptureResult;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;
import miui.reflect.b;

public abstract class ImageProcessor {
    private static final int DEFAULT_IMAGE_BUFFER_QUEUE_SIZE = 4;
    private static final int MSG_IMAGE_DOFILTER = 2;
    private static final int MSG_IMAGE_RECEIVED = 1;
    /* access modifiers changed from: private */
    public static final String TAG = "ImageProcessor";
    protected ConditionVariable mBlockVariable = new ConditionVariable();
    ImageReader mDepthImageReaderHolder;
    ImageReader mEffectImageReaderHolder;
    AtomicInteger mEngineProcessingImageSize = new AtomicInteger(0);
    protected FilterProcessor mFilterProcessor;
    private int mImageBufferQueueSize = 4;
    ImageProcessorStatusCallback mImageProcessorStatusCallback;
    private Handler mImageReaderHandler;
    private HandlerThread mImageReaderThread;
    boolean mIsBokehMode;
    private boolean mIsNeedStopWork;
    private int mMaxParallelRequestNumber = 10;
    AtomicInteger mNeedProcessDepthImageSize = new AtomicInteger(0);
    AtomicInteger mNeedProcessNormalImageSize = new AtomicInteger(0);
    AtomicInteger mNeedProcessRawImageSize = new AtomicInteger(0);
    ImageReader mRawImageReaderHolder;
    /* access modifiers changed from: private */
    public LinkedList<List<CaptureData.CaptureDataBean>> mTaskDataList = new LinkedList<>();
    /* access modifiers changed from: private */
    public Object mTaskDataLock = new Object();
    TaskSession mTaskSession;
    private Handler mWorkHandler;
    private HandlerThread mWorkThread;

    public class FilterTaskData {
        public Image image;
        public boolean isPoolImage;
        public int target;

        public FilterTaskData(Image image2, int i, boolean z) {
            this.image = image2;
            this.target = i;
            this.isPoolImage = z;
        }
    }

    public interface ImageProcessorStatusCallback {
        ParallelTaskData getParallelTaskData(long j);

        boolean isAnyFrontProcessing(ImageProcessor imageProcessor);

        void onImageProcessFailed(Image image, String str);

        void onImageProcessStart(long j);

        void onImageProcessed(Image image, int i, boolean z);

        void onOriginalImageClosed(Image image);
    }

    public ImageProcessor(ImageProcessorStatusCallback imageProcessorStatusCallback, boolean z, BufferFormat bufferFormat) {
        this.mImageProcessorStatusCallback = imageProcessorStatusCallback;
        this.mIsBokehMode = z;
        Locale locale = Locale.ENGLISH;
        Object[] objArr = new Object[4];
        objArr[0] = this.mIsBokehMode ? b.ly : "S";
        objArr[1] = Integer.valueOf(bufferFormat.getBufferWidth());
        objArr[2] = Integer.valueOf(bufferFormat.getBufferHeight());
        objArr[3] = Integer.valueOf(hashCode());
        String format = String.format(locale, "_%s_%dx%d_%d", objArr);
        this.mWorkThread = new HandlerThread("WorkerThread" + format);
        this.mImageReaderThread = new HandlerThread("ReaderThread" + format);
        this.mFilterProcessor = new FilterProcessor(this.mBlockVariable);
        this.mFilterProcessor.init(new Size(bufferFormat.getBufferWidth(), bufferFormat.getBufferHeight()));
    }

    private boolean isAlive() {
        HandlerThread handlerThread = this.mWorkThread;
        return handlerThread != null && handlerThread.isAlive();
    }

    private void sendProcessImageMessage() {
        if (isAlive()) {
            Message obtainMessage = this.mWorkHandler.obtainMessage();
            obtainMessage.what = 1;
            this.mWorkHandler.sendMessage(obtainMessage);
            Log.c(TAG, "sendProcessImageMessage");
            return;
        }
        Object[] objArr = new Object[1];
        HandlerThread handlerThread = this.mWorkThread;
        objArr[0] = handlerThread == null ? "Thread" : handlerThread.getName();
        throw new RuntimeException(String.format("%s already die!", objArr));
    }

    public abstract List<OutputConfiguration> configOutputConfigurations(BufferFormat bufferFormat);

    /* access modifiers changed from: protected */
    public void dispatchFilterTask(@NonNull FilterTaskData filterTaskData) {
        if (isAlive()) {
            Message obtainMessage = this.mWorkHandler.obtainMessage();
            obtainMessage.what = 2;
            obtainMessage.obj = filterTaskData;
            this.mWorkHandler.sendMessage(obtainMessage);
            return;
        }
        throw new RuntimeException("Thread already die!");
    }

    public void dispatchTask(@NonNull List<CaptureData.CaptureDataBean> list) {
        synchronized (this.mTaskDataLock) {
            this.mTaskDataList.add(list);
            if (!isAlgorithmEngineBusy()) {
                sendProcessImageMessage();
            } else {
                String str = TAG;
                Log.v(str, "dispatchTask: taskSize = " + this.mTaskDataList.size());
            }
        }
    }

    /* access modifiers changed from: protected */
    public void doFilter(@NonNull FilterTaskData filterTaskData) {
        Image image = filterTaskData.image;
        long timestamp = image.getTimestamp();
        int i = filterTaskData.target;
        ImageProcessorStatusCallback imageProcessorStatusCallback = this.mImageProcessorStatusCallback;
        if (imageProcessorStatusCallback != null) {
            ParallelTaskData parallelTaskData = imageProcessorStatusCallback.getParallelTaskData(timestamp);
            if (parallelTaskData != null && (i == 0 || 1 == i)) {
                if (com.mi.config.b.isMTKPlatform() && (1 == parallelTaskData.getAlgoType() || 3 == parallelTaskData.getAlgoType())) {
                    TotalCaptureResult totalCaptureResult = ICustomCaptureResult.toTotalCaptureResult(parallelTaskData.getCaptureResult(), 0);
                    parallelTaskData.getCameraId();
                    Integer num = (Integer) totalCaptureResult.get(CaptureResult.SENSOR_SENSITIVITY);
                    if (num != null) {
                        num.intValue();
                    }
                }
                String str = TAG;
                Log.d(str, "doFilter: " + timestamp + "/" + i);
                this.mFilterProcessor.doFilterSync(parallelTaskData, image, i);
            } else if (parallelTaskData == null) {
                String str2 = TAG;
                Log.w(str2, "doFilter: no task data found for image " + timestamp);
            }
            this.mImageProcessorStatusCallback.onImageProcessed(image, i, filterTaskData.isPoolImage);
        } else if (filterTaskData.isPoolImage) {
            String str3 = TAG;
            Log.w(str3, "doFilter: release pool image " + timestamp);
            image.close();
            ImagePool.getInstance().releaseImage(image);
        }
        if (i == 0) {
            this.mNeedProcessNormalImageSize.getAndDecrement();
        } else if (i != 1) {
            if (i == 2) {
                this.mNeedProcessDepthImageSize.getAndDecrement();
            }
            String str4 = TAG;
            Log.e(str4, "invalid target: " + i);
        } else {
            this.mNeedProcessRawImageSize.getAndDecrement();
        }
        tryToStopWork();
    }

    /* access modifiers changed from: protected */
    public int getImageBufferQueueSize() {
        return this.mImageBufferQueueSize;
    }

    /* access modifiers changed from: protected */
    public Handler getImageReaderHandler() {
        return this.mImageReaderHandler;
    }

    public TaskSession getTaskSession() {
        return this.mTaskSession;
    }

    public boolean isAlgorithmEngineBusy() {
        return this.mEngineProcessingImageSize.get() >= this.mMaxParallelRequestNumber;
    }

    /* access modifiers changed from: package-private */
    public abstract boolean isIdle();

    /* access modifiers changed from: protected */
    public void onProcessImageDone() {
        this.mEngineProcessingImageSize.getAndDecrement();
        if (!isAlgorithmEngineBusy()) {
            synchronized (this.mTaskDataLock) {
                int size = this.mTaskDataList.size();
                if (size > 0) {
                    String str = TAG;
                    Log.v(str, "onProcessImageDone: taskSize = " + size);
                    sendProcessImageMessage();
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onProcessImageStart() {
        this.mEngineProcessingImageSize.getAndIncrement();
    }

    /* access modifiers changed from: package-private */
    public abstract void processImage(List<CaptureData.CaptureDataBean> list);

    /* access modifiers changed from: protected */
    public Image queueImageToPool(ImagePool imagePool, Image image) {
        ImagePool.ImageFormat imageQueueKey = imagePool.toImageQueueKey(image);
        if (imagePool.isImageQueueFull(imageQueueKey, 2)) {
            Log.w(TAG, "queueImageToPool: wait E");
            imagePool.waitIfImageQueueFull(imageQueueKey, 2, 0);
            Log.w(TAG, "queueImageToPool: wait X");
        }
        long timestamp = image.getTimestamp();
        imagePool.queueImage(image);
        Image image2 = imagePool.getImage(timestamp);
        imagePool.holdImage(image2);
        return image2;
    }

    public void releaseResource() {
        this.mImageProcessorStatusCallback = null;
        ImageReader imageReader = this.mEffectImageReaderHolder;
        if (imageReader != null) {
            imageReader.close();
            this.mEffectImageReaderHolder = null;
        }
        ImageReader imageReader2 = this.mRawImageReaderHolder;
        if (imageReader2 != null) {
            imageReader2.close();
            this.mRawImageReaderHolder = null;
        }
        ImageReader imageReader3 = this.mDepthImageReaderHolder;
        if (imageReader3 != null) {
            imageReader3.close();
            this.mDepthImageReaderHolder = null;
        }
    }

    public void setImageBufferQueueSize(int i) {
        this.mImageBufferQueueSize = i;
    }

    public void setMaxParallelRequestNumber(int i) {
        if (i > 0) {
            this.mMaxParallelRequestNumber = i;
        }
    }

    public void setTaskSession(@NonNull TaskSession taskSession) {
        this.mTaskSession = taskSession;
    }

    public void startWork() {
        this.mWorkThread.start();
        this.mWorkHandler = new Handler(this.mWorkThread.getLooper()) {
            /* class com.xiaomi.camera.core.ImageProcessor.AnonymousClass1 */

            public void handleMessage(Message message) {
                List<CaptureData.CaptureDataBean> list;
                int i = message.what;
                if (i == 1) {
                    synchronized (ImageProcessor.this.mTaskDataLock) {
                        list = (List) ImageProcessor.this.mTaskDataList.poll();
                    }
                    if (list != null) {
                        String access$200 = ImageProcessor.TAG;
                        Log.c(access$200, "processImage: ts = " + list.get(0).getResult().getTimeStamp());
                        ImageProcessor.this.processImage(list);
                    }
                } else if (i != 2) {
                    String access$2002 = ImageProcessor.TAG;
                    Log.d(access$2002, "handleMessage: unknown message: " + message.what);
                } else {
                    ImageProcessor.this.doFilter((FilterTaskData) message.obj);
                }
            }
        };
        this.mImageReaderThread.start();
        this.mImageReaderHandler = new Handler(this.mImageReaderThread.getLooper());
        Log.d(TAG, String.format(Locale.ENGLISH, "startWork: %s started", this.mWorkThread.getName()));
    }

    public synchronized void stopWork() {
        this.mWorkThread.quitSafely();
        Log.d(TAG, String.format(Locale.ENGLISH, "stopWork: %s stopped", this.mWorkThread.getName()));
        if (this.mWorkHandler != null) {
            this.mWorkHandler.removeCallbacksAndMessages(null);
            this.mWorkHandler = null;
        }
        this.mImageReaderThread.quitSafely();
        if (this.mImageReaderHandler != null) {
            this.mImageReaderHandler.removeCallbacksAndMessages(null);
            this.mImageReaderHandler = null;
        }
        if (this.mFilterProcessor != null) {
            this.mFilterProcessor = null;
        }
        if (this.mTaskSession != null) {
            this.mTaskSession.close();
        }
        releaseResource();
    }

    public synchronized void stopWorkWhenIdle() {
        Log.d(TAG, "stopWorkWhenIdle");
        this.mIsNeedStopWork = true;
        tryToStopWork();
    }

    public synchronized void tryToStopWork() {
        if (this.mIsNeedStopWork && isIdle()) {
            stopWork();
        }
    }
}
