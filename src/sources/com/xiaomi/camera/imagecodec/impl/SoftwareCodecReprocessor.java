package com.xiaomi.camera.imagecodec.impl;

import android.content.Context;
import android.media.Image;
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
import java.nio.ByteBuffer;
import java.util.LinkedList;

public class SoftwareCodecReprocessor implements Reprocessor {
    /* access modifiers changed from: private */
    public static final String TAG = "SoftwareCodecReprocessor";
    public static final Reprocessor.Singleton<SoftwareCodecReprocessor> sInstance = new Reprocessor.Singleton<SoftwareCodecReprocessor>() {
        /* class com.xiaomi.camera.imagecodec.impl.SoftwareCodecReprocessor.AnonymousClass1 */

        /* access modifiers changed from: protected */
        @Override // com.xiaomi.camera.imagecodec.Reprocessor.Singleton
        public SoftwareCodecReprocessor create() {
            return new SoftwareCodecReprocessor();
        }
    };
    private ReprocessData mCurrentProcessingData;
    private final Object mDataLock;
    private boolean mInitialized;
    private OutputConfiguration mJpegOutputConfiguration;
    private long mReprocessStartTime;
    private Handler mRequestDispatchHandler;
    private HandlerThread mRequestDispatchThread;
    private LinkedList<ReprocessData> mTaskDataList;
    private PowerManager.WakeLock mWakeLock;

    private class JobHandler extends Handler {
        private static final int MSG_DESTROY_ENCODER = 2;
        private static final int MSG_DO_NEXT_JOB = 1;

        JobHandler(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message message) {
            int i = message.what;
            if (i == 1) {
                Log.d(SoftwareCodecReprocessor.TAG, "recv MSG_DO_NEXT_JOB");
                if (SoftwareCodecReprocessor.this.checkAndPrepare()) {
                    SoftwareCodecReprocessor.this.doCompress();
                }
            } else if (i != 2) {
                super.handleMessage(message);
            } else {
                Log.d(SoftwareCodecReprocessor.TAG, "recv MSG_DESTROY_ENCODER");
                SoftwareCodecReprocessor.this.releaseWakeLock();
            }
        }
    }

    private SoftwareCodecReprocessor() {
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
        if (r3 != null) goto L_0x0024;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x001c, code lost:
        android.util.Log.w(com.xiaomi.camera.imagecodec.impl.SoftwareCodecReprocessor.TAG, "checkConditionIsReady: ignore null request!");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0023, code lost:
        return false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0024, code lost:
        return true;
     */
    @WorkerThread
    public boolean checkAndPrepare() {
        synchronized (this.mDataLock) {
            if (this.mCurrentProcessingData != null) {
                Log.d(TAG, "checkConditionIsReady: processor is busy!");
                return false;
            }
            ReprocessData peek = this.mTaskDataList.peek();
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0055, code lost:
        r15.mReprocessStartTime = java.lang.System.currentTimeMillis();
        r0 = com.xiaomi.camera.imagecodec.impl.SoftwareCodecReprocessor.TAG;
        android.util.Log.d(r0, "doCompress: " + r3 + " | " + r3.getTimestamp());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0080, code lost:
        if (256 != r2) goto L_0x0144;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0082, code lost:
        r0 = r3.getPlanes();
        r5 = r0[0].getBuffer();
        r7 = r0[2].getBuffer();
        r13 = new int[]{r0[0].getRowStride(), r0[2].getRowStride()};
        r0 = (r5.limit() - r3.getWidth()) + r13[0];
        r8 = r7.limit();
        r9 = new byte[(r0 + r8)];
        r5.rewind();
        r7.rewind();
        r5.get(r9, 0, r5.limit());
        r7.get(r9, r0, r8);
        r0 = new android.graphics.YuvImage(r9, 17, r3.getWidth(), r3.getHeight(), r13);
        r5 = new java.io.ByteArrayOutputStream();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:?, code lost:
        r0.compressToJpeg(new android.graphics.Rect(0, 0, r3.getWidth(), r3.getHeight()), r1, r5);
        r15.mCurrentProcessingData.getResultListener().onJpegAvailable(r5.toByteArray(), null, r15.mCurrentProcessingData.getImageTag());
        android.util.Log.d(com.xiaomi.camera.imagecodec.impl.SoftwareCodecReprocessor.TAG, java.lang.String.format("jpeg quality %d return for %s. cost=%d", java.lang.Byte.valueOf(r1), r15.mCurrentProcessingData.getImageTag(), java.lang.Long.valueOf(java.lang.System.currentTimeMillis() - r15.mReprocessStartTime)));
        r15.mCurrentProcessingData = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x012f, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0130, code lost:
        android.util.Log.e(com.xiaomi.camera.imagecodec.impl.SoftwareCodecReprocessor.TAG, r0.getMessage(), r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0144, code lost:
        r1 = r15.mDataLock;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0146, code lost:
        monitor-enter(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:?, code lost:
        r15.mCurrentProcessingData.getResultListener().onYuvAvailable(r3, r15.mCurrentProcessingData.getImageTag());
        r15.mCurrentProcessingData = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0158, code lost:
        monitor-exit(r1);
     */
    @WorkerThread
    public void doCompress() {
        Image yuvImage;
        Log.d(TAG, "doCompress: E");
        synchronized (this.mDataLock) {
            this.mCurrentProcessingData = this.mTaskDataList.poll();
            if (this.mCurrentProcessingData.getTotalCaptureResult() == null) {
                Log.wtf(TAG, "doCompress: null metadata!");
                return;
            }
            String str = TAG;
            Log.d(str, "doCompress: tag=" + this.mCurrentProcessingData.getImageTag());
            byte jpegQuality = (byte) this.mCurrentProcessingData.getJpegQuality();
            int outputFormat = this.mCurrentProcessingData.getOutputFormat();
            yuvImage = this.mCurrentProcessingData.getYuvImage();
        }
        yuvImage.close();
        ImagePool.getInstance().releaseImage(yuvImage);
        doNextJob();
        Log.d(TAG, "doCompress: X");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0044, code lost:
        if (r5.mRequestDispatchHandler.hasMessages(1) == false) goto L_0x004e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0046, code lost:
        android.util.Log.d(com.xiaomi.camera.imagecodec.impl.SoftwareCodecReprocessor.TAG, "doNextJob: BUSY");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x004e, code lost:
        android.util.Log.d(com.xiaomi.camera.imagecodec.impl.SoftwareCodecReprocessor.TAG, "doNextJob: send MSG_DO_NEXT_JOB");
        r5.mRequestDispatchHandler.sendEmptyMessageDelayed(1, 0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:?, code lost:
        return;
     */
    private void doNextJob() {
        Log.i(TAG, "=============================================================");
        if (!this.mInitialized) {
            Log.w(TAG, "doNextJob: NOT initialized!");
            return;
        }
        synchronized (this.mDataLock) {
            if (this.mTaskDataList.isEmpty()) {
                Log.d(TAG, "doNextJob: idle. Try to close device 30s later.");
                this.mRequestDispatchHandler.sendEmptyMessageDelayed(2, 30000);
            } else if (this.mRequestDispatchHandler.hasMessages(2)) {
                this.mRequestDispatchHandler.removeMessages(2);
            }
        }
    }

    private static byte[] getImageBuffer(Image image) {
        Image.Plane[] planes = image.getPlanes();
        if (planes.length <= 0) {
            return null;
        }
        ByteBuffer buffer = planes[0].getBuffer();
        byte[] bArr = new byte[buffer.remaining()];
        buffer.get(bArr);
        return bArr;
    }

    /* access modifiers changed from: private */
    public void releaseWakeLock() {
        if (this.mWakeLock.isHeld()) {
            Log.d(TAG, "releaseWakeLock");
            this.mWakeLock.release();
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0019, code lost:
        if (r0 == null) goto L_0x002c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x001b, code lost:
        r0.quitSafely();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:?, code lost:
        r2.mRequestDispatchThread.join();
        r2.mRequestDispatchThread = null;
        r2.mRequestDispatchHandler = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0028, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0029, code lost:
        r2.printStackTrace();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0017, code lost:
        r0 = r2.mRequestDispatchThread;
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
    }

    @Override // com.xiaomi.camera.imagecodec.Reprocessor
    public void init(Context context) {
        Log.d(TAG, "init: E");
        synchronized (this.mDataLock) {
            if (!this.mInitialized) {
                this.mWakeLock = ((PowerManager) context.getSystemService("power")).newWakeLock(1, TAG);
                this.mWakeLock.setReferenceCounted(false);
                this.mRequestDispatchThread = new HandlerThread("RequestDispatcher");
                this.mRequestDispatchThread.start();
                this.mRequestDispatchHandler = new JobHandler(this.mRequestDispatchThread.getLooper());
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
            doNextJob();
        } else {
            throw new RuntimeException("NOT initialized. Call init() first!");
        }
    }
}
