package com.xiaomi.engine;

import android.media.Image;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.NonNull;
import android.util.Log;
import com.android.camera.module.loader.FunctionParseBeautyBodySlimCount;
import java.util.List;

public class MiaNodeJNI {
    private static final String LIB_NAME = "com.xiaomi.camera.mianodejni";
    private static final String TAG = "MiaNodeJNI";
    private static boolean sInitialized;
    /* access modifiers changed from: private */
    public static int sType;
    private Handler mHandler;
    private HandlerThread mHandlerThread;
    private final Object mObjLock;

    static class InstanceHolder {
        static MiaNodeJNI INSTANCE = new MiaNodeJNI();

        InstanceHolder() {
        }
    }

    private MiaNodeJNI() {
        this.mHandlerThread = new HandlerThread("MiaNodeLifeCycle");
        this.mObjLock = new Object();
        try {
            Log.e(TAG, "start loading com.xiaomi.camera.mianodejni");
            System.loadLibrary(LIB_NAME);
            Log.d(TAG, "static initializer: loadLibrary com.xiaomi.camera.mianodejni");
        } catch (UnsatisfiedLinkError e2) {
            String str = TAG;
            Log.e(str, "can not load library:com.xiaomi.camera.mianodejni : " + Log.getStackTraceString(e2));
        }
        this.mHandlerThread.start();
        this.mHandler = new Handler(this.mHandlerThread.getLooper());
    }

    private static native int deInit(int i);

    /* access modifiers changed from: private */
    public void deInitialize(int i) {
        synchronized (this.mObjLock) {
            if (sInitialized) {
                deInit(i);
                String str = TAG;
                Log.i(str, "MiaNode: " + i + " has been deInitialized");
                sInitialized = false;
            }
        }
    }

    public static MiaNodeJNI getInstance() {
        return InstanceHolder.INSTANCE;
    }

    private static native int init(int i);

    private void initialize(int i) {
        synchronized (this.mObjLock) {
            long currentTimeMillis = System.currentTimeMillis();
            if (!(!sInitialized || sType == i || sType == 0)) {
                deInitialize(sType);
            }
            if (!sInitialized) {
                sType = i;
                init(i);
                String str = TAG;
                Log.i(str, "MiaNode: " + i + " has been initialized, cost :" + (System.currentTimeMillis() - currentTimeMillis));
                sInitialized = true;
            }
        }
    }

    private static native int processRequest(@NonNull List<FrameData> list, @NonNull Image image, int i, boolean z);

    private void tryToCloseNode() {
        this.mHandler.removeCallbacksAndMessages(null);
        this.mHandler.postDelayed(new Runnable() {
            /* class com.xiaomi.engine.MiaNodeJNI.AnonymousClass1 */

            public void run() {
                MiaNodeJNI.this.deInitialize(MiaNodeJNI.sType);
            }
        }, FunctionParseBeautyBodySlimCount.TIP_INTERVAL_TIME);
    }

    public int process(@NonNull List<FrameData> list, @NonNull Image image, int i, boolean z) {
        int processRequest;
        synchronized (this.mObjLock) {
            if (!sInitialized) {
                initialize(i);
            }
            long currentTimeMillis = System.currentTimeMillis();
            Log.d(TAG, "processRequest: start");
            processRequest = processRequest(list, image, i, z);
            String str = TAG;
            Log.d(str, "processRequest: end, cost: " + (System.currentTimeMillis() - currentTimeMillis));
            tryToCloseNode();
        }
        return processRequest;
    }
}
