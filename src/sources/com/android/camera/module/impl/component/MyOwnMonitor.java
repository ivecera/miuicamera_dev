package com.android.camera.module.impl.component;

import android.util.Log;
import com.ss.android.vesdk.runtime.VEExternalMonitorListener;

public class MyOwnMonitor implements VEExternalMonitorListener {
    public static MyOwnMonitor Instance = new MyOwnMonitor();

    private MyOwnMonitor() {
    }

    @Override // com.ss.android.vesdk.runtime.VEExternalMonitorListener
    public void onMonitorInvoked(String str, int i, String str2) {
        Log.i("LiveLog", str + i + str2);
    }
}
