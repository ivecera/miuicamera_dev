package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public final class ObservableSwitchMap<T, R> extends AbstractObservableWithUpstream<T, R> {
    final int bufferSize;
    final boolean delayErrors;
    final Function<? super T, ? extends ObservableSource<? extends R>> mapper;

    static final class SwitchMapInnerObserver<T, R> extends AtomicReference<Disposable> implements Observer<R> {
        private static final long serialVersionUID = 3837284832786408377L;
        volatile boolean done;
        final long index;
        final SwitchMapObserver<T, R> parent;
        final SpscLinkedArrayQueue<R> queue;

        SwitchMapInnerObserver(SwitchMapObserver<T, R> switchMapObserver, long j, int i) {
            this.parent = switchMapObserver;
            this.index = j;
            this.queue = new SpscLinkedArrayQueue<>(i);
        }

        public void cancel() {
            DisposableHelper.dispose(this);
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            if (this.index == this.parent.unique) {
                this.done = true;
                this.parent.drain();
            }
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            this.parent.innerError(this, th);
        }

        @Override // io.reactivex.Observer
        public void onNext(R r) {
            if (this.index == this.parent.unique) {
                this.queue.offer(r);
                this.parent.drain();
            }
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable) {
            DisposableHelper.setOnce(this, disposable);
        }
    }

    static final class SwitchMapObserver<T, R> extends AtomicInteger implements Observer<T>, Disposable {
        static final SwitchMapInnerObserver<Object, Object> CANCELLED = new SwitchMapInnerObserver<>(null, -1, 1);
        private static final long serialVersionUID = -3491074160481096299L;
        final AtomicReference<SwitchMapInnerObserver<T, R>> active = new AtomicReference<>();
        final Observer<? super R> actual;
        final int bufferSize;
        volatile boolean cancelled;
        final boolean delayErrors;
        volatile boolean done;
        final AtomicThrowable errors;
        final Function<? super T, ? extends ObservableSource<? extends R>> mapper;
        Disposable s;
        volatile long unique;

        static {
            CANCELLED.cancel();
        }

        SwitchMapObserver(Observer<? super R> observer, Function<? super T, ? extends ObservableSource<? extends R>> function, int i, boolean z) {
            this.actual = observer;
            this.mapper = function;
            this.bufferSize = i;
            this.delayErrors = z;
            this.errors = new AtomicThrowable();
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            if (!this.cancelled) {
                this.cancelled = true;
                this.s.dispose();
                disposeInner();
            }
        }

        /* access modifiers changed from: package-private */
        public void disposeInner() {
            SwitchMapInnerObserver<T, R> andSet;
            SwitchMapInnerObserver<T, R> switchMapInnerObserver = this.active.get();
            SwitchMapInnerObserver<T, R> switchMapInnerObserver2 = CANCELLED;
            if (switchMapInnerObserver != switchMapInnerObserver2 && (andSet = this.active.getAndSet(switchMapInnerObserver2)) != CANCELLED && andSet != null) {
                andSet.cancel();
            }
        }

        /* access modifiers changed from: package-private */
        /* JADX WARNING: Removed duplicated region for block: B:77:0x000b A[SYNTHETIC] */
        public void drain() {
            if (getAndIncrement() == 0) {
                Observer<? super R> observer = this.actual;
                int i = 1;
                while (!this.cancelled) {
                    boolean z = false;
                    if (this.done) {
                        boolean z2 = this.active.get() == null;
                        if (this.delayErrors) {
                            if (z2) {
                                Throwable th = (Throwable) this.errors.get();
                                if (th != null) {
                                    observer.onError(th);
                                    return;
                                } else {
                                    observer.onComplete();
                                    return;
                                }
                            }
                        } else if (((Throwable) this.errors.get()) != null) {
                            observer.onError(this.errors.terminate());
                            return;
                        } else if (z2) {
                            observer.onComplete();
                            return;
                        }
                    }
                    SwitchMapInnerObserver<T, R> switchMapInnerObserver = this.active.get();
                    if (switchMapInnerObserver != null) {
                        SpscLinkedArrayQueue<R> spscLinkedArrayQueue = switchMapInnerObserver.queue;
                        if (switchMapInnerObserver.done) {
                            boolean isEmpty = spscLinkedArrayQueue.isEmpty();
                            if (this.delayErrors) {
                                if (isEmpty) {
                                    this.active.compareAndSet(switchMapInnerObserver, null);
                                }
                            } else if (((Throwable) this.errors.get()) != null) {
                                observer.onError(this.errors.terminate());
                                return;
                            } else if (isEmpty) {
                                this.active.compareAndSet(switchMapInnerObserver, null);
                            }
                        }
                        while (!this.cancelled) {
                            if (switchMapInnerObserver == this.active.get()) {
                                if (this.delayErrors || ((Throwable) this.errors.get()) == null) {
                                    boolean z3 = switchMapInnerObserver.done;
                                    R poll = spscLinkedArrayQueue.poll();
                                    boolean z4 = poll == null;
                                    if (z3 && z4) {
                                        this.active.compareAndSet(switchMapInnerObserver, null);
                                    } else if (!z4) {
                                        observer.onNext(poll);
                                    } else if (z) {
                                        continue;
                                    }
                                } else {
                                    observer.onError(this.errors.terminate());
                                    return;
                                }
                            }
                            z = true;
                            if (z) {
                            }
                        }
                        return;
                    }
                    i = addAndGet(-i);
                    if (i == 0) {
                        return;
                    }
                }
            }
        }

        /* access modifiers changed from: package-private */
        public void innerError(SwitchMapInnerObserver<T, R> switchMapInnerObserver, Throwable th) {
            if (switchMapInnerObserver.index != this.unique || !this.errors.addThrowable(th)) {
                RxJavaPlugins.onError(th);
                return;
            }
            if (!this.delayErrors) {
                this.s.dispose();
            }
            switchMapInnerObserver.done = true;
            drain();
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.cancelled;
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            if (!this.done) {
                this.done = true;
                drain();
            }
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            if (this.done || !this.errors.addThrowable(th)) {
                if (!this.delayErrors) {
                    disposeInner();
                }
                RxJavaPlugins.onError(th);
                return;
            }
            this.done = true;
            drain();
        }

        @Override // io.reactivex.Observer
        public void onNext(T t) {
            SwitchMapInnerObserver<T, R> switchMapInnerObserver;
            long j = this.unique + 1;
            this.unique = j;
            SwitchMapInnerObserver<T, R> switchMapInnerObserver2 = this.active.get();
            if (switchMapInnerObserver2 != null) {
                switchMapInnerObserver2.cancel();
            }
            try {
                Object apply = this.mapper.apply(t);
                ObjectHelper.requireNonNull(apply, "The ObservableSource returned is null");
                ObservableSource observableSource = (ObservableSource) apply;
                SwitchMapInnerObserver<T, R> switchMapInnerObserver3 = new SwitchMapInnerObserver<>(this, j, this.bufferSize);
                do {
                    switchMapInnerObserver = this.active.get();
                    if (switchMapInnerObserver == CANCELLED) {
                        return;
                    }
                } while (!this.active.compareAndSet(switchMapInnerObserver, switchMapInnerObserver3));
                observableSource.subscribe(switchMapInnerObserver3);
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.s.dispose();
                onError(th);
            }
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.s, disposable)) {
                this.s = disposable;
                this.actual.onSubscribe(this);
            }
        }
    }

    public ObservableSwitchMap(ObservableSource<T> observableSource, Function<? super T, ? extends ObservableSource<? extends R>> function, int i, boolean z) {
        super(observableSource);
        this.mapper = function;
        this.bufferSize = i;
        this.delayErrors = z;
    }

    @Override // io.reactivex.Observable
    public void subscribeActual(Observer<? super R> observer) {
        if (!ObservableScalarXMap.tryScalarXMapSubscribe(((AbstractObservableWithUpstream) this).source, observer, this.mapper)) {
            ((AbstractObservableWithUpstream) this).source.subscribe(new SwitchMapObserver(observer, this.mapper, this.bufferSize, this.delayErrors));
        }
    }
}
