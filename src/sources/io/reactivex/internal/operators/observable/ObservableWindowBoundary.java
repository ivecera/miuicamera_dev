package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.fuseable.SimplePlainQueue;
import io.reactivex.internal.observers.QueueDrainObserver;
import io.reactivex.internal.queue.MpscLinkedQueue;
import io.reactivex.internal.util.NotificationLite;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.SerializedObserver;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.subjects.UnicastSubject;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

public final class ObservableWindowBoundary<T, B> extends AbstractObservableWithUpstream<T, Observable<T>> {
    final int bufferSize;
    final ObservableSource<B> other;

    static final class WindowBoundaryInnerObserver<T, B> extends DisposableObserver<B> {
        boolean done;
        final WindowBoundaryMainObserver<T, B> parent;

        WindowBoundaryInnerObserver(WindowBoundaryMainObserver<T, B> windowBoundaryMainObserver) {
            this.parent = windowBoundaryMainObserver;
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            if (!this.done) {
                this.done = true;
                this.parent.onComplete();
            }
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            if (this.done) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.done = true;
            this.parent.onError(th);
        }

        @Override // io.reactivex.Observer
        public void onNext(B b2) {
            if (!this.done) {
                this.parent.next();
            }
        }
    }

    static final class WindowBoundaryMainObserver<T, B> extends QueueDrainObserver<T, Object, Observable<T>> implements Disposable {
        static final Object NEXT = new Object();
        final AtomicReference<Disposable> boundary = new AtomicReference<>();
        final int bufferSize;
        final ObservableSource<B> other;
        Disposable s;
        UnicastSubject<T> window;
        final AtomicLong windows = new AtomicLong();

        WindowBoundaryMainObserver(Observer<? super Observable<T>> observer, ObservableSource<B> observableSource, int i) {
            super(observer, new MpscLinkedQueue());
            this.other = observableSource;
            this.bufferSize = i;
            this.windows.lazySet(1);
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            ((QueueDrainObserver) this).cancelled = true;
        }

        /* access modifiers changed from: package-private */
        public void drainLoop() {
            MpscLinkedQueue mpscLinkedQueue = (MpscLinkedQueue) ((QueueDrainObserver) this).queue;
            Observer<? super V> observer = ((QueueDrainObserver) this).actual;
            UnicastSubject<T> unicastSubject = this.window;
            int i = 1;
            while (true) {
                boolean z = ((QueueDrainObserver) this).done;
                T poll = mpscLinkedQueue.poll();
                boolean z2 = poll == null;
                if (z && z2) {
                    DisposableHelper.dispose(this.boundary);
                    Throwable th = ((QueueDrainObserver) this).error;
                    if (th != null) {
                        unicastSubject.onError(th);
                        return;
                    } else {
                        unicastSubject.onComplete();
                        return;
                    }
                } else if (z2) {
                    i = leave(-i);
                    if (i == 0) {
                        return;
                    }
                } else if (poll == NEXT) {
                    unicastSubject.onComplete();
                    if (this.windows.decrementAndGet() == 0) {
                        DisposableHelper.dispose(this.boundary);
                        return;
                    } else if (!((QueueDrainObserver) this).cancelled) {
                        unicastSubject = UnicastSubject.create(this.bufferSize);
                        this.windows.getAndIncrement();
                        this.window = unicastSubject;
                        observer.onNext(unicastSubject);
                    }
                } else {
                    NotificationLite.getValue(poll);
                    unicastSubject.onNext(poll);
                }
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return ((QueueDrainObserver) this).cancelled;
        }

        /* access modifiers changed from: package-private */
        public void next() {
            ((QueueDrainObserver) this).queue.offer(NEXT);
            if (enter()) {
                drainLoop();
            }
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            if (!((QueueDrainObserver) this).done) {
                ((QueueDrainObserver) this).done = true;
                if (enter()) {
                    drainLoop();
                }
                if (this.windows.decrementAndGet() == 0) {
                    DisposableHelper.dispose(this.boundary);
                }
                ((QueueDrainObserver) this).actual.onComplete();
            }
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            if (((QueueDrainObserver) this).done) {
                RxJavaPlugins.onError(th);
                return;
            }
            ((QueueDrainObserver) this).error = th;
            ((QueueDrainObserver) this).done = true;
            if (enter()) {
                drainLoop();
            }
            if (this.windows.decrementAndGet() == 0) {
                DisposableHelper.dispose(this.boundary);
            }
            ((QueueDrainObserver) this).actual.onError(th);
        }

        @Override // io.reactivex.Observer
        public void onNext(T t) {
            if (fastEnter()) {
                this.window.onNext(t);
                if (leave(-1) == 0) {
                    return;
                }
            } else {
                SimplePlainQueue<U> simplePlainQueue = ((QueueDrainObserver) this).queue;
                NotificationLite.next(t);
                simplePlainQueue.offer(t);
                if (!enter()) {
                    return;
                }
            }
            drainLoop();
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.s, disposable)) {
                this.s = disposable;
                Observer<? super V> observer = ((QueueDrainObserver) this).actual;
                observer.onSubscribe(this);
                if (!((QueueDrainObserver) this).cancelled) {
                    UnicastSubject<T> create = UnicastSubject.create(this.bufferSize);
                    this.window = create;
                    observer.onNext(create);
                    WindowBoundaryInnerObserver windowBoundaryInnerObserver = new WindowBoundaryInnerObserver(this);
                    if (this.boundary.compareAndSet(null, windowBoundaryInnerObserver)) {
                        this.windows.getAndIncrement();
                        this.other.subscribe(windowBoundaryInnerObserver);
                    }
                }
            }
        }
    }

    public ObservableWindowBoundary(ObservableSource<T> observableSource, ObservableSource<B> observableSource2, int i) {
        super(observableSource);
        this.other = observableSource2;
        this.bufferSize = i;
    }

    @Override // io.reactivex.Observable
    public void subscribeActual(Observer<? super Observable<T>> observer) {
        ((AbstractObservableWithUpstream) this).source.subscribe(new WindowBoundaryMainObserver(new SerializedObserver(observer), this.other, this.bufferSize));
    }
}
