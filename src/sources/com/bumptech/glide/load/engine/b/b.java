package com.bumptech.glide.load.engine.b;

import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* compiled from: GlideExecutor */
public final class b implements ExecutorService {
    private static final String TAG = "GlideExecutor";
    private static final String hp = "source";
    private static final String ip = "disk-cache";
    private static final int jp = 1;
    private static final String kp = "source-unlimited";
    private static final String lp = "animation";
    private static final long mp = TimeUnit.SECONDS.toMillis(10);
    private static final int np = 4;
    private static volatile int qp;
    private final ExecutorService delegate;

    /* access modifiers changed from: private */
    /* compiled from: GlideExecutor */
    public static final class a implements ThreadFactory {
        private static final int gp = 9;
        final C0010b dp;
        final boolean ep;
        private int fp;
        private final String name;

        a(String str, C0010b bVar, boolean z) {
            this.name = str;
            this.dp = bVar;
            this.ep = z;
        }

        public synchronized Thread newThread(@NonNull Runnable runnable) {
            a aVar;
            aVar = new a(this, runnable, "glide-" + this.name + "-thread-" + this.fp);
            this.fp = this.fp + 1;
            return aVar;
        }
    }

    /* renamed from: com.bumptech.glide.load.engine.b.b$b  reason: collision with other inner class name */
    /* compiled from: GlideExecutor */
    public interface C0010b {
        public static final C0010b DEFAULT = LOG;
        public static final C0010b IGNORE = new c();
        public static final C0010b Iz = new e();
        public static final C0010b LOG = new d();

        void b(Throwable th);
    }

    @VisibleForTesting
    b(ExecutorService executorService) {
        this.delegate = executorService;
    }

    public static int Bj() {
        if (qp == 0) {
            qp = Math.min(4, g.availableProcessors());
        }
        return qp;
    }

    public static b Cj() {
        return a(Bj() >= 4 ? 2 : 1, C0010b.DEFAULT);
    }

    public static b Dj() {
        return a(1, ip, C0010b.DEFAULT);
    }

    public static b Ej() {
        return b(Bj(), hp, C0010b.DEFAULT);
    }

    public static b Fj() {
        return new b(new ThreadPoolExecutor(0, Integer.MAX_VALUE, mp, TimeUnit.MILLISECONDS, new SynchronousQueue(), new a(kp, C0010b.DEFAULT, false)));
    }

    public static b a(int i, C0010b bVar) {
        return new b(new ThreadPoolExecutor(0, i, mp, TimeUnit.MILLISECONDS, new PriorityBlockingQueue(), new a(lp, bVar, true)));
    }

    public static b a(int i, String str, C0010b bVar) {
        return new b(new ThreadPoolExecutor(i, i, 0, TimeUnit.MILLISECONDS, new PriorityBlockingQueue(), new a(str, bVar, true)));
    }

    public static b a(C0010b bVar) {
        return a(1, ip, bVar);
    }

    public static b b(int i, String str, C0010b bVar) {
        return new b(new ThreadPoolExecutor(i, i, 0, TimeUnit.MILLISECONDS, new PriorityBlockingQueue(), new a(str, bVar, false)));
    }

    public static b b(C0010b bVar) {
        return b(Bj(), hp, bVar);
    }

    @Override // java.util.concurrent.ExecutorService
    public boolean awaitTermination(long j, @NonNull TimeUnit timeUnit) throws InterruptedException {
        return this.delegate.awaitTermination(j, timeUnit);
    }

    public void execute(@NonNull Runnable runnable) {
        this.delegate.execute(runnable);
    }

    @Override // java.util.concurrent.ExecutorService
    @NonNull
    public <T> List<Future<T>> invokeAll(@NonNull Collection<? extends Callable<T>> collection) throws InterruptedException {
        return this.delegate.invokeAll(collection);
    }

    @Override // java.util.concurrent.ExecutorService
    @NonNull
    public <T> List<Future<T>> invokeAll(@NonNull Collection<? extends Callable<T>> collection, long j, @NonNull TimeUnit timeUnit) throws InterruptedException {
        return this.delegate.invokeAll(collection, j, timeUnit);
    }

    @Override // java.util.concurrent.ExecutorService
    @NonNull
    public <T> T invokeAny(@NonNull Collection<? extends Callable<T>> collection) throws InterruptedException, ExecutionException {
        return this.delegate.invokeAny(collection);
    }

    @Override // java.util.concurrent.ExecutorService
    public <T> T invokeAny(@NonNull Collection<? extends Callable<T>> collection, long j, @NonNull TimeUnit timeUnit) throws InterruptedException, ExecutionException, TimeoutException {
        return this.delegate.invokeAny(collection, j, timeUnit);
    }

    public boolean isShutdown() {
        return this.delegate.isShutdown();
    }

    public boolean isTerminated() {
        return this.delegate.isTerminated();
    }

    public void shutdown() {
        this.delegate.shutdown();
    }

    @Override // java.util.concurrent.ExecutorService
    @NonNull
    public List<Runnable> shutdownNow() {
        return this.delegate.shutdownNow();
    }

    @Override // java.util.concurrent.ExecutorService
    @NonNull
    public Future<?> submit(@NonNull Runnable runnable) {
        return this.delegate.submit(runnable);
    }

    @Override // java.util.concurrent.ExecutorService
    @NonNull
    public <T> Future<T> submit(@NonNull Runnable runnable, T t) {
        return this.delegate.submit(runnable, t);
    }

    @Override // java.util.concurrent.ExecutorService
    public <T> Future<T> submit(@NonNull Callable<T> callable) {
        return this.delegate.submit(callable);
    }

    public String toString() {
        return this.delegate.toString();
    }
}
