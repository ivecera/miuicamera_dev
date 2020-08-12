package com.bumptech.glide.load.engine.b;

import android.os.Process;
import android.os.StrictMode;
import com.bumptech.glide.load.engine.b.b;

/* compiled from: GlideExecutor */
class a extends Thread {
    final /* synthetic */ b.a this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    a(b.a aVar, Runnable runnable, String str) {
        super(runnable, str);
        this.this$0 = aVar;
    }

    public void run() {
        Process.setThreadPriority(9);
        if (this.this$0.ep) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectNetwork().penaltyDeath().build());
        }
        try {
            super.run();
        } catch (Throwable th) {
            this.this$0.dp.b(th);
        }
    }
}
