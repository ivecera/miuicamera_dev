package android.arch.core.a;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* compiled from: DefaultTaskExecutor */
public class d extends e {
    private ExecutorService Fa = Executors.newFixedThreadPool(2);
    private final Object mLock = new Object();
    @Nullable
    private volatile Handler mMainHandler;

    @Override // android.arch.core.a.e
    public void b(Runnable runnable) {
        this.Fa.execute(runnable);
    }

    @Override // android.arch.core.a.e
    public void d(Runnable runnable) {
        if (this.mMainHandler == null) {
            synchronized (this.mLock) {
                if (this.mMainHandler == null) {
                    this.mMainHandler = new Handler(Looper.getMainLooper());
                }
            }
        }
        this.mMainHandler.post(runnable);
    }

    @Override // android.arch.core.a.e
    public boolean isMainThread() {
        return Looper.getMainLooper().getThread() == Thread.currentThread();
    }
}
