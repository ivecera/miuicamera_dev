package io.reactivex.internal.schedulers;

import java.util.concurrent.Callable;

public final class ScheduledDirectTask extends AbstractDirectTask implements Callable<Void> {
    private static final long serialVersionUID = 1811839108042568751L;

    public ScheduledDirectTask(Runnable runnable) {
        super(runnable);
    }

    @Override // java.util.concurrent.Callable
    public Void call() throws Exception {
        ((AbstractDirectTask) this).runner = Thread.currentThread();
        try {
            ((AbstractDirectTask) this).runnable.run();
            return null;
        } finally {
            lazySet(AbstractDirectTask.FINISHED);
            ((AbstractDirectTask) this).runner = null;
        }
    }

    @Override // io.reactivex.schedulers.SchedulerRunnableIntrospection, io.reactivex.internal.schedulers.AbstractDirectTask
    public /* bridge */ /* synthetic */ Runnable getWrappedRunnable() {
        return super.getWrappedRunnable();
    }
}
