package com.xiaomi.camera.liveshot.util;

import android.os.Looper;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BackgroundWorker {
    private static final int SHUTDOWN_TIMEOUT_MILLISECONDS = 2000;
    private final ExecutorService mExecutor = Executors.newSingleThreadExecutor();
    /* access modifiers changed from: private */
    public final Object mLock = new Object();
    /* access modifiers changed from: private */
    public Looper mLooper;
    /* access modifiers changed from: private */
    public volatile boolean mReady = false;

    public BackgroundWorker(final String str) {
        this.mExecutor.execute(new Runnable() {
            /* class com.xiaomi.camera.liveshot.util.BackgroundWorker.AnonymousClass1 */

            public void run() {
                Thread.currentThread().setName(str);
                Looper.prepare();
                Looper unused = BackgroundWorker.this.mLooper = Looper.myLooper();
                synchronized (BackgroundWorker.this.mLock) {
                    boolean unused2 = BackgroundWorker.this.mReady = true;
                    BackgroundWorker.this.mLock.notifyAll();
                }
                Looper.loop();
                synchronized (BackgroundWorker.this.mLock) {
                    boolean unused3 = BackgroundWorker.this.mReady = false;
                }
            }
        });
    }

    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* JADX WARNING: Missing exception handler attribute for start block: B:2:0x0003 */
    /* JADX WARNING: Removed duplicated region for block: B:2:0x0003 A[LOOP:0: B:2:0x0003->B:12:0x0003, LOOP_START, SYNTHETIC] */
    private void waitUntilReady() {
        synchronized (this.mLock) {
            while (!this.mReady) {
                this.mLock.wait();
            }
        }
    }

    public Looper getLooper() {
        waitUntilReady();
        return this.mLooper;
    }

    public void quit() throws InterruptedException {
        waitUntilReady();
        this.mLooper.quitSafely();
        this.mExecutor.shutdown();
    }
}
