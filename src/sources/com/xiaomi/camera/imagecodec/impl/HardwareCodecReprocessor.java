package com.xiaomi.camera.imagecodec.impl;

import android.content.Context;
import android.hardware.camera2.params.InputConfiguration;
import android.media.Image;
import android.media.ImageReader;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.support.annotation.NonNull;
import android.support.annotation.WorkerThread;
import android.util.Log;
import com.xiaomi.camera.imagecodec.ImagePool;
import com.xiaomi.camera.imagecodec.OutputConfiguration;
import com.xiaomi.camera.imagecodec.ReprocessData;
import com.xiaomi.camera.imagecodec.Reprocessor;
import com.xiaomi.media.imagecodec.ImageCodec;
import java.nio.ByteBuffer;
import java.util.LinkedList;

public class HardwareCodecReprocessor implements Reprocessor {
    private static final int MAX_IMAGE_BUFFER_SIZE = 2;
    /* access modifiers changed from: private */
    public static final String TAG = "HardwareCodecReprocessor";
    public static final Reprocessor.Singleton<HardwareCodecReprocessor> sInstance = new Reprocessor.Singleton<HardwareCodecReprocessor>() {
        /* class com.xiaomi.camera.imagecodec.impl.HardwareCodecReprocessor.AnonymousClass1 */

        /* access modifiers changed from: protected */
        @Override // com.xiaomi.camera.imagecodec.Reprocessor.Singleton
        public HardwareCodecReprocessor create() {
            return new HardwareCodecReprocessor();
        }
    };
    /* access modifiers changed from: private */
    public final Object mCodecLock;
    private Handler mCodecOperationHandler;
    private HandlerThread mCodecOperationThread;
    /* access modifiers changed from: private */
    public ReprocessData mCurrentProcessingData;
    /* access modifiers changed from: private */
    public final Object mDataLock;
    /* access modifiers changed from: private */
    public ImageCodec mHardwareImageEncoder;
    private boolean mInitialized;
    private ImageReader mJpegImageReader;
    private OutputConfiguration mJpegOutputConfiguration;
    /* access modifiers changed from: private */
    public long mReprocessStartTime;
    private Handler mRequestDispatchHandler;
    private HandlerThread mRequestDispatchThread;
    private LinkedList<ReprocessData> mTaskDataList;
    private PowerManager.WakeLock mWakeLock;

    private class ReprocessHandler extends Handler {
        private static final int MSG_DESTROY_ENCODER = 2;
        private static final int MSG_REPROCESS_IMAGE = 1;

        ReprocessHandler(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message message) {
            int i = message.what;
            if (i == 1) {
                Log.d(HardwareCodecReprocessor.TAG, "recv MSG_REPROCESS_IMAGE");
                if (HardwareCodecReprocessor.this.checkConditionIsReady()) {
                    HardwareCodecReprocessor.this.reprocessImage();
                }
            } else if (i != 2) {
                super.handleMessage(message);
            } else {
                Log.d(HardwareCodecReprocessor.TAG, "recv MSG_DESTROY_ENCODER");
                synchronized (HardwareCodecReprocessor.this.mCodecLock) {
                    if (HardwareCodecReprocessor.this.mHardwareImageEncoder != null) {
                        String access$100 = HardwareCodecReprocessor.TAG;
                        Log.d(access$100, "release current codec: " + HardwareCodecReprocessor.this.mHardwareImageEncoder);
                        HardwareCodecReprocessor.this.mHardwareImageEncoder.release();
                        ImageCodec unused = HardwareCodecReprocessor.this.mHardwareImageEncoder = null;
                    }
                }
                HardwareCodecReprocessor.this.releaseWakeLock();
            }
        }
    }

    private HardwareCodecReprocessor() {
        this.mCodecLock = new Object();
        this.mDataLock = new Object();
        this.mTaskDataList = new LinkedList<>();
    }

    private void acquireWakeLock() {
        if (!this.mWakeLock.isHeld()) {
            Log.d(TAG, "acquireWakeLock");
            this.mWakeLock.acquire();
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:10:0x001a, code lost:
        if (r1 != null) goto L_0x0024;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x001c, code lost:
        android.util.Log.w(com.xiaomi.camera.imagecodec.impl.HardwareCodecReprocessor.TAG, "checkConditionIsReady: ignore null request!");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0023, code lost:
        return false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0028, code lost:
        return createImageCodecIfNeed(r1);
     */
    @WorkerThread
    public boolean checkConditionIsReady() {
        synchronized (this.mDataLock) {
            if (this.mCurrentProcessingData != null) {
                Log.d(TAG, "checkConditionIsReady: processor is busy!");
                return false;
            }
            ReprocessData peek = this.mTaskDataList.peek();
        }
    }

    @WorkerThread
    private boolean createImageCodecIfNeed(@NonNull ReprocessData reprocessData) {
        Image yuvImage = reprocessData.getYuvImage();
        InputConfiguration inputConfiguration = new InputConfiguration(yuvImage.getWidth(), yuvImage.getHeight(), yuvImage.getFormat());
        OutputConfiguration outputConfiguration = new OutputConfiguration(reprocessData.getOutputWidth(), reprocessData.getOutputHeight(), reprocessData.getOutputFormat());
        String str = TAG;
        Log.d(str, " YUV  INPUT: " + inputConfiguration);
        String str2 = TAG;
        Log.d(str2, " YUV OUTPUT: " + outputConfiguration);
        synchronized (this.mCodecLock) {
            if (256 == outputConfiguration.getFormat()) {
                initImageReaderAndImageCodec(inputConfiguration, outputConfiguration);
            }
        }
        return true;
    }

    /* access modifiers changed from: private */
    public static byte[] getJpegData(Image image) {
        Image.Plane[] planes = image.getPlanes();
        if (planes.length <= 0) {
            return null;
        }
        ByteBuffer buffer = planes[0].getBuffer();
        byte[] bArr = new byte[buffer.remaining()];
        buffer.get(bArr);
        return bArr;
    }

    @WorkerThread
    private void initImageReaderAndImageCodec(InputConfiguration inputConfiguration, OutputConfiguration outputConfiguration) {
        Log.d(TAG, "initImageReaderAndImageCodec: E");
        ImageReader imageReader = this.mJpegImageReader;
        if (!(imageReader == null || (imageReader.getWidth() == outputConfiguration.getWidth() && this.mJpegImageReader.getHeight() == outputConfiguration.getHeight()))) {
            if (this.mHardwareImageEncoder != null) {
                Log.d(TAG, "initImageReader: closing obsolete image codec");
                this.mHardwareImageEncoder.release();
                this.mHardwareImageEncoder = null;
            }
            Log.d(TAG, "initImageReader: closing obsolete reprocess reader");
            this.mJpegImageReader.close();
            this.mJpegImageReader = null;
        }
        if (this.mJpegImageReader == null) {
            Log.d(TAG, "initImageReader: create new one");
            this.mJpegImageReader = ImageReader.newInstance(outputConfiguration.getWidth(), outputConfiguration.getHeight(), 256, 2);
            this.mJpegImageReader.setOnImageAvailableListener(new ImageReader.OnImageAvailableListener() {
                /* class com.xiaomi.camera.imagecodec.impl.HardwareCodecReprocessor.AnonymousClass3 */

                public void onImageAvailable(ImageReader imageReader) {
                    Image acquireNextImage = imageReader.acquireNextImage();
                    byte[] access$700 = HardwareCodecReprocessor.getJpegData(acquireNextImage);
                    String access$100 = HardwareCodecReprocessor.TAG;
                    StringBuilder sb = new StringBuilder();
                    sb.append("onImageAvailable: received reprocessed image: ");
                    sb.append(acquireNextImage);
                    sb.append(", size: ");
                    sb.append(access$700 == null ? 0 : access$700.length);
                    Log.d(access$100, sb.toString());
                    acquireNextImage.close();
                    synchronized (HardwareCodecReprocessor.this.mDataLock) {
                        HardwareCodecReprocessor.this.mCurrentProcessingData.getResultListener().onJpegAvailable(access$700, null, HardwareCodecReprocessor.this.mCurrentProcessingData.getImageTag());
                        Log.d(HardwareCodecReprocessor.TAG, String.format("jpeg return for %s. cost=%d", HardwareCodecReprocessor.this.mCurrentProcessingData.getImageTag(), Long.valueOf(System.currentTimeMillis() - HardwareCodecReprocessor.this.mReprocessStartTime)));
                        ReprocessData unused = HardwareCodecReprocessor.this.mCurrentProcessingData = null;
                    }
                    HardwareCodecReprocessor.this.sendReprocessRequest();
                }
            }, this.mCodecOperationHandler);
        } else {
            Log.d(TAG, "initImageReader: reuse old one");
        }
        ImageCodec imageCodec = this.mHardwareImageEncoder;
        if (!(imageCodec == null || (imageCodec.getInputSpec().width == inputConfiguration.getWidth() && this.mHardwareImageEncoder.getInputSpec().height == inputConfiguration.getHeight()))) {
            Log.d(TAG, "initImageCodec: closing obsolete image codec");
            this.mHardwareImageEncoder.release();
            this.mHardwareImageEncoder = null;
        }
        if (this.mHardwareImageEncoder == null) {
            Log.d(TAG, "initImageCodec: create new one");
            this.mHardwareImageEncoder = ImageCodec.create(inputConfiguration.getWidth(), inputConfiguration.getHeight(), 35);
            this.mHardwareImageEncoder.setOutputSurface(this.mJpegImageReader.getSurface());
        } else {
            Log.d(TAG, "initImageCodec: reuse old one");
        }
        Log.d(TAG, "initImageReaderAndImageCodec: X");
    }

    /* access modifiers changed from: private */
    public void releaseWakeLock() {
        if (this.mWakeLock.isHeld()) {
            Log.d(TAG, "releaseWakeLock");
            this.mWakeLock.release();
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0056, code lost:
        r9.mReprocessStartTime = java.lang.System.currentTimeMillis();
        r0 = r9.mCodecOperationHandler;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x005e, code lost:
        if (r0 == null) goto L_0x006a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0060, code lost:
        r0.post(new com.xiaomi.camera.imagecodec.impl.HardwareCodecReprocessor.AnonymousClass2(r9, false));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x006a, code lost:
        android.util.Log.d(com.xiaomi.camera.imagecodec.impl.HardwareCodecReprocessor.TAG, "reprocessImage: X");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0071, code lost:
        return;
     */
    @WorkerThread
    public void reprocessImage() {
        Log.d(TAG, "reprocessImage: E");
        synchronized (this.mDataLock) {
            this.mCurrentProcessingData = this.mTaskDataList.poll();
            if (this.mCurrentProcessingData.getTotalCaptureResult() == null) {
                Log.wtf(TAG, "reprocessImage: null metadata!");
                return;
            }
            String str = TAG;
            Log.d(str, "reprocessImage: tag=" + this.mCurrentProcessingData.getImageTag());
            final byte jpegQuality = (byte) this.mCurrentProcessingData.getJpegQuality();
            final int outputFormat = this.mCurrentProcessingData.getOutputFormat();
            final Image yuvImage = this.mCurrentProcessingData.getYuvImage();
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0044, code lost:
        if (r5.mRequestDispatchHandler.hasMessages(1) == false) goto L_0x004e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0046, code lost:
        android.util.Log.d(com.xiaomi.camera.imagecodec.impl.HardwareCodecReprocessor.TAG, "sendReprocessRequest: BUSY");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x004e, code lost:
        android.util.Log.d(com.xiaomi.camera.imagecodec.impl.HardwareCodecReprocessor.TAG, "sendReprocessRequest: send MSG_REPROCESS_IMAGE");
        r5.mRequestDispatchHandler.sendEmptyMessageDelayed(1, 0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:?, code lost:
        return;
     */
    public void sendReprocessRequest() {
        Log.i(TAG, "=============================================================");
        if (!this.mInitialized) {
            Log.w(TAG, "sendReprocessRequest: NOT initialized!");
            return;
        }
        synchronized (this.mDataLock) {
            if (this.mTaskDataList.isEmpty()) {
                Log.d(TAG, "sendReprocessRequest: idle. Try to close device 30s later.");
                this.mRequestDispatchHandler.sendEmptyMessageDelayed(2, 30000);
            } else if (this.mRequestDispatchHandler.hasMessages(2)) {
                this.mRequestDispatchHandler.removeMessages(2);
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0019, code lost:
        monitor-enter(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:?, code lost:
        r3.mJpegImageReader = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x001e, code lost:
        if (r3.mHardwareImageEncoder == null) goto L_0x0027;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0020, code lost:
        r3.mHardwareImageEncoder.release();
        r3.mHardwareImageEncoder = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0027, code lost:
        monitor-exit(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0028, code lost:
        r0 = r3.mCodecOperationThread;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x002a, code lost:
        if (r0 == null) goto L_0x003d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x002c, code lost:
        r0.quitSafely();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:?, code lost:
        r3.mCodecOperationThread.join();
        r3.mCodecOperationThread = null;
        r3.mCodecOperationHandler = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0039, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x003a, code lost:
        r0.printStackTrace();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0017, code lost:
        r2 = r3.mCodecLock;
     */
    @Override // com.xiaomi.camera.imagecodec.Reprocessor
    public void deInit() {
        Log.d(TAG, "deInit: E");
        synchronized (this.mDataLock) {
            if (this.mInitialized) {
                this.mInitialized = false;
                this.mCurrentProcessingData = null;
            } else {
                return;
            }
        }
        Log.d(TAG, "deInit: X");
        HandlerThread handlerThread = this.mRequestDispatchThread;
        if (handlerThread != null) {
            handlerThread.quitSafely();
            try {
                this.mRequestDispatchThread.join();
                this.mRequestDispatchThread = null;
                this.mRequestDispatchHandler = null;
            } catch (InterruptedException e2) {
                e2.printStackTrace();
            }
        }
        Log.d(TAG, "deInit: X");
    }

    @Override // com.xiaomi.camera.imagecodec.Reprocessor
    public void init(Context context) {
        Log.d(TAG, "init: E");
        synchronized (this.mDataLock) {
            if (!this.mInitialized) {
                this.mWakeLock = ((PowerManager) context.getSystemService("power")).newWakeLock(1, TAG);
                this.mWakeLock.setReferenceCounted(false);
                this.mCodecOperationThread = new HandlerThread("ImageCodecThread");
                this.mCodecOperationThread.start();
                this.mCodecOperationHandler = new Handler(this.mCodecOperationThread.getLooper());
                this.mRequestDispatchThread = new HandlerThread("RequestDispatcher");
                this.mRequestDispatchThread.start();
                this.mRequestDispatchHandler = new ReprocessHandler(this.mRequestDispatchThread.getLooper());
                this.mInitialized = true;
            }
        }
        Log.d(TAG, "init: X");
    }

    @Override // com.xiaomi.camera.imagecodec.Reprocessor
    public void setOutputPictureSpec(int i, int i2, int i3) {
        if (i3 != 256) {
            throw new IllegalArgumentException("Only supports JPEG encoding");
        } else if (this.mJpegOutputConfiguration == null) {
            String str = TAG;
            Log.d(str, "setOutputPictureSpec: " + i + "x" + i2 + "@" + Integer.toHexString(i3));
            this.mJpegOutputConfiguration = new OutputConfiguration(i, i2, i3);
        }
    }

    @Override // com.xiaomi.camera.imagecodec.Reprocessor
    public void setVirtualCameraIds(@NonNull String str, @NonNull String str2) {
        Log.d(TAG, String.format("setVTCameraIds: backId=%s frontId=%s", str, str2));
    }

    @Override // com.xiaomi.camera.imagecodec.Reprocessor
    public void submit(ReprocessData reprocessData) {
        String str = TAG;
        Log.d(str, "submit: " + reprocessData.getImageTag());
        if (reprocessData.getResultListener() == null) {
            Log.d(TAG, "submit: drop this request due to no callback was provided!");
        } else if (this.mInitialized) {
            acquireWakeLock();
            if (!reprocessData.isImageFromPool()) {
                Image yuvImage = reprocessData.getYuvImage();
                ImagePool.ImageFormat imageQueueKey = ImagePool.getInstance().toImageQueueKey(yuvImage);
                if (ImagePool.getInstance().isImageQueueFull(imageQueueKey, 2)) {
                    Log.w(TAG, "submit: wait image pool>>");
                    ImagePool.getInstance().waitIfImageQueueFull(imageQueueKey, 2, 0);
                    Log.w(TAG, "submit: wait image pool<<");
                }
                long timestamp = yuvImage.getTimestamp();
                ImagePool.getInstance().queueImage(yuvImage);
                Image image = ImagePool.getInstance().getImage(timestamp);
                String str2 = TAG;
                Log.d(str2, "submit: image: " + image + " | " + timestamp);
                reprocessData.setYuvImage(image);
                ImagePool.getInstance().holdImage(image);
            }
            synchronized (this.mDataLock) {
                this.mTaskDataList.add(reprocessData);
            }
            sendReprocessRequest();
        } else {
            throw new RuntimeException("NOT initialized. Call init() first!");
        }
    }
}
