package android.arch.core.a;

import java.util.concurrent.Executor;

/* compiled from: ArchTaskExecutor */
class b implements Executor {
    b() {
    }

    public void execute(Runnable runnable) {
        c.getInstance().b(runnable);
    }
}
