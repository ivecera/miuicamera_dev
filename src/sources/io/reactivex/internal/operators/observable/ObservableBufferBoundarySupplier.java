package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.observers.QueueDrainObserver;
import io.reactivex.internal.queue.MpscLinkedQueue;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.SerializedObserver;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicReference;

public final class ObservableBufferBoundarySupplier<T, U extends Collection<? super T>, B> extends AbstractObservableWithUpstream<T, U> {
    final Callable<? extends ObservableSource<B>> boundarySupplier;
    final Callable<U> bufferSupplier;

    static final class BufferBoundaryObserver<T, U extends Collection<? super T>, B> extends DisposableObserver<B> {
        boolean once;
        final BufferBoundarySupplierObserver<T, U, B> parent;

        BufferBoundaryObserver(BufferBoundarySupplierObserver<T, U, B> bufferBoundarySupplierObserver) {
            this.parent = bufferBoundarySupplierObserver;
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            if (!this.once) {
                this.once = true;
                this.parent.next();
            }
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            if (this.once) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.once = true;
            this.parent.onError(th);
        }

        @Override // io.reactivex.Observer
        public void onNext(B b2) {
            if (!this.once) {
                this.once = true;
                dispose();
                this.parent.next();
            }
        }
    }

    static final class BufferBoundarySupplierObserver<T, U extends Collection<? super T>, B> extends QueueDrainObserver<T, U, U> implements Observer<T>, Disposable {
        final Callable<? extends ObservableSource<B>> boundarySupplier;
        U buffer;
        final Callable<U> bufferSupplier;
        final AtomicReference<Disposable> other = new AtomicReference<>();
        Disposable s;

        BufferBoundarySupplierObserver(Observer<? super U> observer, Callable<U> callable, Callable<? extends ObservableSource<B>> callable2) {
            super(observer, new MpscLinkedQueue());
            this.bufferSupplier = callable;
            this.boundarySupplier = callable2;
        }

        @Override // io.reactivex.internal.observers.QueueDrainObserver, io.reactivex.internal.util.ObservableQueueDrain
        public /* bridge */ /* synthetic */ void accept(Observer observer, Object obj) {
            accept(observer, (Collection) ((Collection) obj));
        }

        public void accept(Observer<? super U> observer, U u) {
            ((QueueDrainObserver) this).actual.onNext(u);
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            if (!((QueueDrainObserver) this).cancelled) {
                ((QueueDrainObserver) this).cancelled = true;
                this.s.dispose();
                disposeOther();
                if (enter()) {
                    ((QueueDrainObserver) this).queue.clear();
                }
            }
        }

        /* access modifiers changed from: package-private */
        public void disposeOther() {
            DisposableHelper.dispose(this.other);
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return ((QueueDrainObserver) this).cancelled;
        }

        /* access modifiers changed from: package-private */
        public void next() {
            try {
                U call = this.bufferSupplier.call();
                ObjectHelper.requireNonNull(call, "The buffer supplied is null");
                U u = call;
                try {
                    Object call2 = this.boundarySupplier.call();
                    ObjectHelper.requireNonNull(call2, "The boundary ObservableSource supplied is null");
                    ObservableSource observableSource = (ObservableSource) call2;
                    BufferBoundaryObserver bufferBoundaryObserver = new BufferBoundaryObserver(this);
                    if (this.other.compareAndSet(this.other.get(), bufferBoundaryObserver)) {
                        synchronized (this) {
                            U u2 = this.buffer;
                            if (u2 != null) {
                                this.buffer = u;
                                observableSource.subscribe(bufferBoundaryObserver);
                                fastPathEmit(u2, false, this);
                            }
                        }
                    }
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    ((QueueDrainObserver) this).cancelled = true;
                    this.s.dispose();
                    ((QueueDrainObserver) this).actual.onError(th);
                }
            } catch (Throwable th2) {
                Exceptions.throwIfFatal(th2);
                dispose();
                ((QueueDrainObserver) this).actual.onError(th2);
            }
        }

        /* JADX WARNING: Code restructure failed: missing block: B:10:0x0019, code lost:
            io.reactivex.internal.util.QueueDrainHelper.drainLoop(((io.reactivex.internal.observers.QueueDrainObserver) r3).queue, ((io.reactivex.internal.observers.QueueDrainObserver) r3).actual, false, r3, r3);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:14:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:15:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:8:0x000b, code lost:
            ((io.reactivex.internal.observers.QueueDrainObserver) r3).queue.offer(r0);
            ((io.reactivex.internal.observers.QueueDrainObserver) r3).done = true;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:9:0x0017, code lost:
            if (enter() == false) goto L_?;
         */
        @Override // io.reactivex.Observer
        public void onComplete() {
            synchronized (this) {
                U u = this.buffer;
                if (u != null) {
                    this.buffer = null;
                }
            }
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            dispose();
            ((QueueDrainObserver) this).actual.onError(th);
        }

        @Override // io.reactivex.Observer
        public void onNext(T t) {
            synchronized (this) {
                U u = this.buffer;
                if (u != null) {
                    u.add(t);
                }
            }
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.s, disposable)) {
                this.s = disposable;
                Observer<? super V> observer = ((QueueDrainObserver) this).actual;
                try {
                    U call = this.bufferSupplier.call();
                    ObjectHelper.requireNonNull(call, "The buffer supplied is null");
                    this.buffer = call;
                    try {
                        Object call2 = this.boundarySupplier.call();
                        ObjectHelper.requireNonNull(call2, "The boundary ObservableSource supplied is null");
                        ObservableSource observableSource = (ObservableSource) call2;
                        BufferBoundaryObserver bufferBoundaryObserver = new BufferBoundaryObserver(this);
                        this.other.set(bufferBoundaryObserver);
                        observer.onSubscribe(this);
                        if (!((QueueDrainObserver) this).cancelled) {
                            observableSource.subscribe(bufferBoundaryObserver);
                        }
                    } catch (Throwable th) {
                        Exceptions.throwIfFatal(th);
                        ((QueueDrainObserver) this).cancelled = true;
                        disposable.dispose();
                        EmptyDisposable.error(th, observer);
                    }
                } catch (Throwable th2) {
                    Exceptions.throwIfFatal(th2);
                    ((QueueDrainObserver) this).cancelled = true;
                    disposable.dispose();
                    EmptyDisposable.error(th2, observer);
                }
            }
        }
    }

    public ObservableBufferBoundarySupplier(ObservableSource<T> observableSource, Callable<? extends ObservableSource<B>> callable, Callable<U> callable2) {
        super(observableSource);
        this.boundarySupplier = callable;
        this.bufferSupplier = callable2;
    }

    /* access modifiers changed from: protected */
    @Override // io.reactivex.Observable
    public void subscribeActual(Observer<? super U> observer) {
        ((AbstractObservableWithUpstream) this).source.subscribe(new BufferBoundarySupplierObserver(new SerializedObserver(observer), this.bufferSupplier, this.boundarySupplier));
    }
}
