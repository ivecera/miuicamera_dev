package io.reactivex.internal.schedulers;

import io.reactivex.Scheduler;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public final class TrampolineScheduler extends Scheduler {
    private static final TrampolineScheduler INSTANCE = new TrampolineScheduler();

    static final class SleepingRunnable implements Runnable {
        private final long execTime;
        private final Runnable run;
        private final TrampolineWorker worker;

        SleepingRunnable(Runnable runnable, TrampolineWorker trampolineWorker, long j) {
            this.run = runnable;
            this.worker = trampolineWorker;
            this.execTime = j;
        }

        public void run() {
            if (!this.worker.disposed) {
                long now = this.worker.now(TimeUnit.MILLISECONDS);
                long j = this.execTime;
                if (j > now) {
                    long j2 = j - now;
                    if (j2 > 0) {
                        try {
                            Thread.sleep(j2);
                        } catch (InterruptedException e2) {
                            Thread.currentThread().interrupt();
                            RxJavaPlugins.onError(e2);
                            return;
                        }
                    }
                }
                if (!this.worker.disposed) {
                    this.run.run();
                }
            }
        }
    }

    static final class TimedRunnable implements Comparable<TimedRunnable> {
        final int count;
        volatile boolean disposed;
        final long execTime;
        final Runnable run;

        TimedRunnable(Runnable runnable, Long l, int i) {
            this.run = runnable;
            this.execTime = l.longValue();
            this.count = i;
        }

        public int compareTo(TimedRunnable timedRunnable) {
            int compare = ObjectHelper.compare(this.execTime, timedRunnable.execTime);
            return compare == 0 ? ObjectHelper.compare(this.count, timedRunnable.count) : compare;
        }
    }

    static final class TrampolineWorker extends Scheduler.Worker implements Disposable {
        final AtomicInteger counter = new AtomicInteger();
        volatile boolean disposed;
        final PriorityBlockingQueue<TimedRunnable> queue = new PriorityBlockingQueue<>();
        private final AtomicInteger wip = new AtomicInteger();

        final class AppendToQueueTask implements Runnable {
            final TimedRunnable timedRunnable;

            AppendToQueueTask(TimedRunnable timedRunnable2) {
                this.timedRunnable = timedRunnable2;
            }

            public void run() {
                TimedRunnable timedRunnable2 = this.timedRunnable;
                timedRunnable2.disposed = true;
                TrampolineWorker.this.queue.remove(timedRunnable2);
            }
        }

        TrampolineWorker() {
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            this.disposed = true;
        }

        /* access modifiers changed from: package-private */
        public Disposable enqueue(Runnable runnable, long j) {
            if (this.disposed) {
                return EmptyDisposable.INSTANCE;
            }
            TimedRunnable timedRunnable = new TimedRunnable(runnable, Long.valueOf(j), this.counter.incrementAndGet());
            this.queue.add(timedRunnable);
            if (this.wip.getAndIncrement() != 0) {
                return Disposables.fromRunnable(new AppendToQueueTask(timedRunnable));
            }
            int i = 1;
            while (!this.disposed) {
                TimedRunnable poll = this.queue.poll();
                if (poll == null) {
                    i = this.wip.addAndGet(-i);
                    if (i == 0) {
                        return EmptyDisposable.INSTANCE;
                    }
                } else if (!poll.disposed) {
                    poll.run.run();
                }
            }
            this.queue.clear();
            return EmptyDisposable.INSTANCE;
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.disposed;
        }

        @Override // io.reactivex.Scheduler.Worker
        @NonNull
        public Disposable schedule(@NonNull Runnable runnable) {
            return enqueue(runnable, now(TimeUnit.MILLISECONDS));
        }

        @Override // io.reactivex.Scheduler.Worker
        @NonNull
        public Disposable schedule(@NonNull Runnable runnable, long j, @NonNull TimeUnit timeUnit) {
            long now = now(TimeUnit.MILLISECONDS) + timeUnit.toMillis(j);
            return enqueue(new SleepingRunnable(runnable, this, now), now);
        }
    }

    TrampolineScheduler() {
    }

    public static TrampolineScheduler instance() {
        return INSTANCE;
    }

    @Override // io.reactivex.Scheduler
    @NonNull
    public Scheduler.Worker createWorker() {
        return new TrampolineWorker();
    }

    @Override // io.reactivex.Scheduler
    @NonNull
    public Disposable scheduleDirect(@NonNull Runnable runnable) {
        RxJavaPlugins.onSchedule(runnable).run();
        return EmptyDisposable.INSTANCE;
    }

    @Override // io.reactivex.Scheduler
    @NonNull
    public Disposable scheduleDirect(@NonNull Runnable runnable, long j, TimeUnit timeUnit) {
        try {
            timeUnit.sleep(j);
            RxJavaPlugins.onSchedule(runnable).run();
        } catch (InterruptedException e2) {
            Thread.currentThread().interrupt();
            RxJavaPlugins.onError(e2);
        }
        return EmptyDisposable.INSTANCE;
    }
}