package com.bumptech.glide.manager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

/* compiled from: DefaultConnectivityMonitor */
class e extends BroadcastReceiver {
    final /* synthetic */ f this$0;

    e(f fVar) {
        this.this$0 = fVar;
    }

    public void onReceive(@NonNull Context context, Intent intent) {
        f fVar = this.this$0;
        boolean z = fVar.isConnected;
        fVar.isConnected = fVar.isConnected(context);
        if (z != this.this$0.isConnected) {
            if (Log.isLoggable("ConnectivityMonitor", 3)) {
                Log.d("ConnectivityMonitor", "connectivity changed, isConnected: " + this.this$0.isConnected);
            }
            f fVar2 = this.this$0;
            fVar2.listener.m(fVar2.isConnected);
        }
    }
}
