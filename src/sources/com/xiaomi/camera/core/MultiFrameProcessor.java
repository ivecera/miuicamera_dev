package com.xiaomi.camera.core;

import android.media.Image;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import com.android.camera.log.Log;
import com.xiaomi.camera.core.CaptureData;
import com.xiaomi.camera.imagecodec.ReprocessData;
import com.xiaomi.camera.imagecodec.ReprocessorFactory;
import com.xiaomi.camera.processor.ClearShotProcessor;
import com.xiaomi.camera.processor.GroupShotProcessor;
import com.xiaomi.camera.processor.ProcessResultListener;
import com.xiaomi.camera.processor.SuperResolutionProcessor;
import com.xiaomi.engine.TaskSession;
import d.d.a;
import java.io.File;

public class MultiFrameProcessor {
    private static final int REPROCESS_TIMEOUT_MS = 8000;
    /* access modifiers changed from: private */
    public static final String TAG = "MultiFrameProcessor";
    private final int MSG_PROCESS_DATA;
    private Handler mHandler;
    /* access modifiers changed from: private */
    public final Object mObjLock;
    private ProcessResultListener mProcessResultListener;
    /* access modifiers changed from: private */
    public long mReprocessStartTime;
    /* access modifiers changed from: private */
    public boolean mReprocessing;
    private HandlerThread mWorkThread;

    static class MultiFrameProcessorHolder {
        static MultiFrameProcessor INSTANCE = new MultiFrameProcessor();

        MultiFrameProcessorHolder() {
        }
    }

    private class ProcessDataAndTaskSession {
        CaptureData data;
        TaskSession taskSession;

        public ProcessDataAndTaskSession(CaptureData captureData, TaskSession taskSession2) {
            this.data = captureData;
            this.taskSession = taskSession2;
        }
    }

    private class WorkerHandler extends Handler {
        public WorkerHandler(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message message) {
            if (message.what != 1) {
                String access$100 = MultiFrameProcessor.TAG;
                Log.w(access$100, "unexpected message " + message.what);
                return;
            }
            ProcessDataAndTaskSession processDataAndTaskSession = (ProcessDataAndTaskSession) message.obj;
            MultiFrameProcessor.this.doProcess(processDataAndTaskSession.data, processDataAndTaskSession.taskSession);
        }
    }

    private MultiFrameProcessor() {
        this.mObjLock = new Object();
        this.mProcessResultListener = new ProcessResultListener() {
            /* class com.xiaomi.camera.core.MultiFrameProcessor.AnonymousClass1 */

            @Override // com.xiaomi.camera.processor.ProcessResultListener
            public void onProcessFinished(CaptureData captureData, boolean z) {
                String access$100 = MultiFrameProcessor.TAG;
                Log.d(access$100, "onProcessFinished: doReprocess = " + z);
                CaptureDataListener captureDataListener = captureData.getCaptureDataListener();
                if (captureDataListener == null) {
                    Log.w(MultiFrameProcessor.TAG, "onProcessFinished: null CaptureDataListener!");
                    CaptureData.CaptureDataBean multiFrameProcessResult = captureData.getMultiFrameProcessResult();
                    if (multiFrameProcessResult != null) {
                        multiFrameProcessResult.close();
                    }
                    for (CaptureData.CaptureDataBean captureDataBean : captureData.getCaptureDataBeanList()) {
                        if (captureDataBean != null) {
                            captureDataBean.close();
                        }
                    }
                    return;
                }
                if (z) {
                    MultiFrameProcessor.this.reprocessImage(captureData.getMultiFrameProcessResult(), captureData.isCapturedByFrontCamera());
                }
                Log.d(MultiFrameProcessor.TAG, "onProcessFinished: dispatch image to algorithm engine");
                captureDataListener.onCaptureDataAvailable(captureData);
            }
        };
        this.MSG_PROCESS_DATA = 1;
        this.mWorkThread = new HandlerThread("MultiFrameProcessorThread");
        this.mWorkThread.start();
        this.mHandler = new WorkerHandler(this.mWorkThread.getLooper());
    }

    /* access modifiers changed from: private */
    public void doProcess(CaptureData captureData, TaskSession taskSession) {
        String str = TAG;
        Log.d(str, "doProcess: start process task: " + captureData.getCaptureTimestamp());
        int algoType = captureData.getAlgoType();
        if (2 == algoType) {
            new ClearShotProcessor().doProcess(captureData, this.mProcessResultListener, null);
        } else if (5 == algoType) {
            new GroupShotProcessor().doProcess(captureData, this.mProcessResultListener, null);
        } else if (3 == algoType) {
            new SuperResolutionProcessor().doProcess(captureData, this.mProcessResultListener, taskSession);
        } else {
            throw new RuntimeException("unknown multi-frame process algorithm type: " + algoType);
        }
    }

    public static MultiFrameProcessor getInstance() {
        return MultiFrameProcessorHolder.INSTANCE;
    }

    /* access modifiers changed from: private */
    public void reprocessImage(final CaptureData.CaptureDataBean captureDataBean, boolean z) {
        AnonymousClass2 r8 = new ReprocessData.OnDataAvailableListener() {
            /* class com.xiaomi.camera.core.MultiFrameProcessor.AnonymousClass2 */

            @Override // com.xiaomi.camera.imagecodec.ReprocessData.OnDataAvailableListener
            public void onError(String str, String str2) {
                synchronized (MultiFrameProcessor.this.mObjLock) {
                    String access$100 = MultiFrameProcessor.TAG;
                    Log.v(access$100, "onError>>tag=" + str2 + " reason=" + str);
                    if (!a.IS_DEBUGGABLE) {
                        boolean unused = MultiFrameProcessor.this.mReprocessing = false;
                        MultiFrameProcessor.this.mObjLock.notify();
                        String access$1002 = MultiFrameProcessor.TAG;
                        Log.v(access$1002, "onError<<cost=" + (System.currentTimeMillis() - MultiFrameProcessor.this.mReprocessStartTime));
                    } else {
                        throw new RuntimeException("reprocessImage failed image = " + str2 + " reason = " + str);
                    }
                }
            }

            @Override // com.xiaomi.camera.imagecodec.ReprocessData.OnDataAvailableListener
            public void onJpegAvailable(byte[] bArr, byte[] bArr2, String str) {
            }

            @Override // com.xiaomi.camera.imagecodec.ReprocessData.OnDataAvailableListener
            public void onYuvAvailable(Image image, String str) {
                synchronized (MultiFrameProcessor.this.mObjLock) {
                    String access$100 = MultiFrameProcessor.TAG;
                    Log.v(access$100, "onYuvAvailable>>tag=" + str);
                    boolean unused = MultiFrameProcessor.this.mReprocessing = false;
                    captureDataBean.setMainImage(image);
                    MultiFrameProcessor.this.mObjLock.notify();
                    String access$1002 = MultiFrameProcessor.TAG;
                    Log.v(access$1002, "onYuvAvailable<<cost=" + (System.currentTimeMillis() - MultiFrameProcessor.this.mReprocessStartTime));
                }
            }
        };
        Image mainImage = captureDataBean.getMainImage();
        String str = captureDataBean.getResult().getTimeStamp() + File.separator + 0;
        int width = mainImage.getWidth();
        int height = mainImage.getHeight();
        Log.d(TAG, "reprocessImage: timestamp = " + mainImage.getTimestamp());
        synchronized (this.mObjLock) {
            this.mReprocessStartTime = System.currentTimeMillis();
            this.mReprocessing = true;
            ReprocessData reprocessData = new ReprocessData(mainImage, str, captureDataBean.getResult(), z, width, height, 35, r8);
            reprocessData.setImageFromPool(true);
            try {
                ReprocessorFactory.getDefaultReprocessor().submit(reprocessData);
                while (this.mReprocessing) {
                    this.mObjLock.wait(8000);
                    this.mReprocessing = false;
                }
            } catch (IllegalArgumentException | IllegalStateException | InterruptedException e2) {
                this.mReprocessing = false;
                Log.e(TAG, e2.getMessage(), e2);
            }
        }
    }

    public void processData(CaptureData captureData, TaskSession taskSession) {
        if (captureData.getBurstNum() != captureData.getCaptureDataBeanList().size()) {
            throw new RuntimeException("Loss some capture data, burstNum is: " + captureData.getBurstNum() + "; but data bean list size is: " + captureData.getCaptureDataBeanList().size());
        } else if (!this.mWorkThread.isAlive() || this.mHandler == null) {
            Log.w(TAG, "processData: sync mode");
            doProcess(captureData, taskSession);
        } else {
            String str = TAG;
            Log.v(str, "processData: queue task: " + captureData.getCaptureTimestamp());
            this.mHandler.obtainMessage(1, new ProcessDataAndTaskSession(captureData, taskSession)).sendToTarget();
        }
    }
}
