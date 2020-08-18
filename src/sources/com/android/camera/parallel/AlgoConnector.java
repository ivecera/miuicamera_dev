package com.android.camera.parallel;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import com.android.camera.LocalParallelService;
import com.android.camera.log.Log;

public class AlgoConnector {
    /* access modifiers changed from: private */
    public static final String TAG = "AlgoConnector";
    /* access modifiers changed from: private */
    public static final AlgoConnector ourInstance = new AlgoConnector();
    /* access modifiers changed from: private */
    public LocalParallelService.LocalBinder mLocalBinder;
    private final ServiceConnection mServiceConnection = new ServiceConnection() {
        /* class com.android.camera.parallel.AlgoConnector.AnonymousClass1 */

        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            String access$000 = AlgoConnector.TAG;
            Log.d(access$000, "onServiceConnected: " + componentName);
            synchronized (AlgoConnector.ourInstance) {
                LocalParallelService.LocalBinder unused = AlgoConnector.this.mLocalBinder = (LocalParallelService.LocalBinder) iBinder;
                AlgoConnector.ourInstance.notify();
            }
        }

        public void onServiceDisconnected(ComponentName componentName) {
            Log.d(AlgoConnector.TAG, "onServiceDisconnected");
            synchronized (AlgoConnector.ourInstance) {
                LocalParallelService.LocalBinder unused = AlgoConnector.this.mLocalBinder = null;
                AlgoConnector.ourInstance.notify();
            }
        }
    };

    private AlgoConnector() {
    }

    public static AlgoConnector getInstance() {
        return ourInstance;
    }

    public LocalParallelService.LocalBinder getLocalBinder() {
        return getLocalBinder(false);
    }

    public LocalParallelService.LocalBinder getLocalBinder(boolean z) {
        if (z) {
            int i = 0;
            synchronized (ourInstance) {
                while (this.mLocalBinder == null && i < 50) {
                    try {
                        ourInstance.wait(100);
                        i++;
                        String str = TAG;
                        Log.w(str, "waiting service..." + i);
                    } catch (InterruptedException e2) {
                        Log.e(TAG, e2.getMessage(), e2);
                    }
                }
            }
        }
        return this.mLocalBinder;
    }

    public void setServiceStatusListener(LocalParallelService.ServiceStatusListener serviceStatusListener) {
        LocalParallelService.LocalBinder localBinder;
        if (serviceStatusListener != null && (localBinder = this.mLocalBinder) != null) {
            localBinder.setOnPictureTakenListener(serviceStatusListener);
        }
    }

    public void startService(Context context) {
        if (this.mLocalBinder == null) {
            Log.d(TAG, "startService: bind LocalParallelService start!");
            context.bindService(new Intent(context, LocalParallelService.class), this.mServiceConnection, 67108929);
        }
    }
}
