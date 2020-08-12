package com.bumptech.glide.load.engine;

import android.os.Process;

/* compiled from: ActiveResources */
class b implements Runnable {
    final /* synthetic */ ActiveResources this$0;

    b(ActiveResources activeResources) {
        this.this$0 = activeResources;
    }

    public void run() {
        Process.setThreadPriority(10);
        this.this$0.ej();
    }
}
