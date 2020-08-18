package com.android.camera.aftersales;

import android.content.Context;
import com.android.camera.R;
import com.android.camera.aftersales.counters.BaseCounter;
import com.android.camera.aftersales.counters.SimpleCounter;
import com.android.camera.aftersales.interfaces.ITrimStrategy;
import com.android.camera.aftersales.strategies.SimpleTrimStrategy;
import com.android.camera.log.Log;
import com.android.camera.module.impl.component.FileUtils;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class AftersalesManager {
    public static final String FILE_PATH = (FileUtils.ROOT_DIR + "aftersales.csv");
    public static final String TAG = "AfterSales";
    private static boolean mEnabled;
    private volatile boolean isInit;
    /* access modifiers changed from: private */
    public BaseCounter mCounters;
    private ITrimStrategy mTrimStrategy;
    private ExecutorService service;

    private class AftersalesRunnable implements Runnable {
        int cameraId;
        int id;
        long time;

        public AftersalesRunnable(long j, int i, int i2) {
            this.time = j;
            this.id = i;
            this.cameraId = i2;
        }

        public void run() {
            switch (this.id) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                    AftersalesManager.this.mCounters.count(this.time, this.id, this.cameraId);
                    return;
                default:
                    Log.d(AftersalesManager.TAG, "could not count this event");
                    return;
            }
        }
    }

    private static class Holder {
        /* access modifiers changed from: private */
        public static AftersalesManager manager = new AftersalesManager();

        private Holder() {
        }
    }

    private AftersalesManager() {
        if (!mEnabled) {
            this.isInit = false;
        } else if (!this.isInit) {
            this.service = Executors.newSingleThreadExecutor(new ThreadFactory() {
                /* class com.android.camera.aftersales.AftersalesManager.AnonymousClass1 */

                public Thread newThread(Runnable runnable) {
                    Thread thread = new Thread(runnable, "aftersales");
                    thread.setPriority(1);
                    return thread;
                }
            });
            this.mTrimStrategy = new SimpleTrimStrategy(FILE_PATH);
            this.mCounters = new SimpleCounter(0, this.mTrimStrategy, FILE_PATH);
            this.isInit = true;
        }
    }

    public static void checkSelf(Context context) {
        mEnabled = context.getResources().getBoolean(R.bool.aftersales_enabled);
    }

    public static AftersalesManager getInstance() {
        return Holder.manager;
    }

    public final void clean() {
        if (this.isInit && mEnabled) {
            this.service.execute(new Runnable() {
                /* class com.android.camera.aftersales.AftersalesManager.AnonymousClass2 */

                public void run() {
                    FileUtils.deleteFile(AftersalesManager.FILE_PATH);
                }
            });
        }
    }

    public final void count(long j, int i) {
        if (!this.isInit || !mEnabled) {
            Log.d(TAG, "aftersales record not enable in this device");
        } else {
            this.service.execute(new AftersalesRunnable(j, i, -1));
        }
    }

    public final void count(long j, int i, int i2) {
        if (!this.isInit || !mEnabled) {
            Log.d(TAG, "aftersales record not enable in this device");
        } else {
            this.service.execute(new AftersalesRunnable(j, i, i2));
        }
    }
}
