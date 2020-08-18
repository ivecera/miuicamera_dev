package io.reactivex.internal.schedulers;

public final class ScheduledDirectPeriodicTask extends AbstractDirectTask implements Runnable {
    private static final long serialVersionUID = 1811839108042568751L;

    public ScheduledDirectPeriodicTask(Runnable runnable) {
        super(runnable);
    }

    @Override // io.reactivex.schedulers.SchedulerRunnableIntrospection, io.reactivex.internal.schedulers.AbstractDirectTask
    public /* bridge */ /* synthetic */ Runnable getWrappedRunnable() {
        return super.getWrappedRunnable();
    }

    public void run() {
        ((AbstractDirectTask) this).runner = Thread.currentThread();
        try {
            ((AbstractDirectTask) this).runnable.run();
        } catch (Throwable th) {
            ((AbstractDirectTask) this).runner = null;
            throw th;
        }
        ((AbstractDirectTask) this).runner = null;
    }
}
