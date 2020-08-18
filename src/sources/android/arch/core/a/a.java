package android.arch.core.a;

import java.util.concurrent.Executor;

/* compiled from: ArchTaskExecutor */
class a implements Executor {
    a() {
    }

    public void execute(Runnable runnable) {
        c.getInstance().d(runnable);
    }
}
