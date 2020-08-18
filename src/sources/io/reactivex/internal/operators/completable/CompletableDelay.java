package io.reactivex.internal.operators.completable;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import java.util.concurrent.TimeUnit;

public final class CompletableDelay extends Completable {
    final long delay;
    final boolean delayError;
    final Scheduler scheduler;
    final CompletableSource source;
    final TimeUnit unit;

    final class Delay implements CompletableObserver {
        final CompletableObserver s;
        private final CompositeDisposable set;

        final class OnComplete implements Runnable {
            OnComplete() {
            }

            public void run() {
                Delay.this.s.onComplete();
            }
        }

        final class OnError implements Runnable {

            /* renamed from: e  reason: collision with root package name */
            private final Throwable f687e;

            OnError(Throwable th) {
                this.f687e = th;
            }

            public void run() {
                Delay.this.s.onError(this.f687e);
            }
        }

        Delay(CompositeDisposable compositeDisposable, CompletableObserver completableObserver) {
            this.set = compositeDisposable;
            this.s = completableObserver;
        }

        @Override // io.reactivex.CompletableObserver
        public void onComplete() {
            CompositeDisposable compositeDisposable = this.set;
            Scheduler scheduler = CompletableDelay.this.scheduler;
            OnComplete onComplete = new OnComplete();
            CompletableDelay completableDelay = CompletableDelay.this;
            compositeDisposable.add(scheduler.scheduleDirect(onComplete, completableDelay.delay, completableDelay.unit));
        }

        @Override // io.reactivex.CompletableObserver
        public void onError(Throwable th) {
            CompositeDisposable compositeDisposable = this.set;
            Scheduler scheduler = CompletableDelay.this.scheduler;
            OnError onError = new OnError(th);
            CompletableDelay completableDelay = CompletableDelay.this;
            compositeDisposable.add(scheduler.scheduleDirect(onError, completableDelay.delayError ? completableDelay.delay : 0, CompletableDelay.this.unit));
        }

        @Override // io.reactivex.CompletableObserver
        public void onSubscribe(Disposable disposable) {
            this.set.add(disposable);
            this.s.onSubscribe(this.set);
        }
    }

    public CompletableDelay(CompletableSource completableSource, long j, TimeUnit timeUnit, Scheduler scheduler2, boolean z) {
        this.source = completableSource;
        this.delay = j;
        this.unit = timeUnit;
        this.scheduler = scheduler2;
        this.delayError = z;
    }

    /* access modifiers changed from: protected */
    @Override // io.reactivex.Completable
    public void subscribeActual(CompletableObserver completableObserver) {
        this.source.subscribe(new Delay(new CompositeDisposable(), completableObserver));
    }
}
