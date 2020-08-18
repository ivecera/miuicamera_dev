package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.queue.MpscLinkedQueue;
import io.reactivex.internal.subscribers.QueueDrainSubscriber;
import io.reactivex.internal.subscriptions.EmptySubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.subscribers.DisposableSubscriber;
import io.reactivex.subscribers.SerializedSubscriber;
import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableBufferBoundarySupplier<T, U extends Collection<? super T>, B> extends AbstractFlowableWithUpstream<T, U> {
    final Callable<? extends Publisher<B>> boundarySupplier;
    final Callable<U> bufferSupplier;

    static final class BufferBoundarySubscriber<T, U extends Collection<? super T>, B> extends DisposableSubscriber<B> {
        boolean once;
        final BufferBoundarySupplierSubscriber<T, U, B> parent;

        BufferBoundarySubscriber(BufferBoundarySupplierSubscriber<T, U, B> bufferBoundarySupplierSubscriber) {
            this.parent = bufferBoundarySupplierSubscriber;
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (!this.once) {
                this.once = true;
                this.parent.next();
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.once) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.once = true;
            this.parent.onError(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(B b2) {
            if (!this.once) {
                this.once = true;
                cancel();
                this.parent.next();
            }
        }
    }

    static final class BufferBoundarySupplierSubscriber<T, U extends Collection<? super T>, B> extends QueueDrainSubscriber<T, U, U> implements FlowableSubscriber<T>, Subscription, Disposable {
        final Callable<? extends Publisher<B>> boundarySupplier;
        U buffer;
        final Callable<U> bufferSupplier;
        final AtomicReference<Disposable> other = new AtomicReference<>();
        Subscription s;

        BufferBoundarySupplierSubscriber(Subscriber<? super U> subscriber, Callable<U> callable, Callable<? extends Publisher<B>> callable2) {
            super(subscriber, new MpscLinkedQueue());
            this.bufferSupplier = callable;
            this.boundarySupplier = callable2;
        }

        @Override // io.reactivex.internal.subscribers.QueueDrainSubscriber, io.reactivex.internal.util.QueueDrain
        public /* bridge */ /* synthetic */ boolean accept(Subscriber subscriber, Object obj) {
            return accept(subscriber, (Collection) ((Collection) obj));
        }

        public boolean accept(Subscriber<? super U> subscriber, U u) {
            ((QueueDrainSubscriber) this).actual.onNext(u);
            return true;
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            if (!((QueueDrainSubscriber) this).cancelled) {
                ((QueueDrainSubscriber) this).cancelled = true;
                this.s.cancel();
                disposeOther();
                if (enter()) {
                    ((QueueDrainSubscriber) this).queue.clear();
                }
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            this.s.cancel();
            disposeOther();
        }

        /* access modifiers changed from: package-private */
        public void disposeOther() {
            DisposableHelper.dispose(this.other);
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.other.get() == DisposableHelper.DISPOSED;
        }

        /* access modifiers changed from: package-private */
        public void next() {
            try {
                U call = this.bufferSupplier.call();
                ObjectHelper.requireNonNull(call, "The buffer supplied is null");
                U u = call;
                try {
                    Object call2 = this.boundarySupplier.call();
                    ObjectHelper.requireNonNull(call2, "The boundary publisher supplied is null");
                    Publisher publisher = (Publisher) call2;
                    BufferBoundarySubscriber bufferBoundarySubscriber = new BufferBoundarySubscriber(this);
                    if (this.other.compareAndSet(this.other.get(), bufferBoundarySubscriber)) {
                        synchronized (this) {
                            U u2 = this.buffer;
                            if (u2 != null) {
                                this.buffer = u;
                                publisher.subscribe(bufferBoundarySubscriber);
                                fastPathEmitMax(u2, false, this);
                            }
                        }
                    }
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    ((QueueDrainSubscriber) this).cancelled = true;
                    this.s.cancel();
                    ((QueueDrainSubscriber) this).actual.onError(th);
                }
            } catch (Throwable th2) {
                Exceptions.throwIfFatal(th2);
                cancel();
                ((QueueDrainSubscriber) this).actual.onError(th2);
            }
        }

        /* JADX WARNING: Code restructure failed: missing block: B:10:0x0019, code lost:
            io.reactivex.internal.util.QueueDrainHelper.drainMaxLoop(((io.reactivex.internal.subscribers.QueueDrainSubscriber) r3).queue, ((io.reactivex.internal.subscribers.QueueDrainSubscriber) r3).actual, false, r3, r3);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:14:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:15:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:8:0x000b, code lost:
            ((io.reactivex.internal.subscribers.QueueDrainSubscriber) r3).queue.offer(r0);
            ((io.reactivex.internal.subscribers.QueueDrainSubscriber) r3).done = true;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:9:0x0017, code lost:
            if (enter() == false) goto L_?;
         */
        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            synchronized (this) {
                U u = this.buffer;
                if (u != null) {
                    this.buffer = null;
                }
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            cancel();
            ((QueueDrainSubscriber) this).actual.onError(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            synchronized (this) {
                U u = this.buffer;
                if (u != null) {
                    u.add(t);
                }
            }
        }

        @Override // io.reactivex.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.s, subscription)) {
                this.s = subscription;
                Subscriber<? super V> subscriber = ((QueueDrainSubscriber) this).actual;
                try {
                    U call = this.bufferSupplier.call();
                    ObjectHelper.requireNonNull(call, "The buffer supplied is null");
                    this.buffer = call;
                    try {
                        Object call2 = this.boundarySupplier.call();
                        ObjectHelper.requireNonNull(call2, "The boundary publisher supplied is null");
                        Publisher publisher = (Publisher) call2;
                        BufferBoundarySubscriber bufferBoundarySubscriber = new BufferBoundarySubscriber(this);
                        this.other.set(bufferBoundarySubscriber);
                        subscriber.onSubscribe(this);
                        if (!((QueueDrainSubscriber) this).cancelled) {
                            subscription.request(Long.MAX_VALUE);
                            publisher.subscribe(bufferBoundarySubscriber);
                        }
                    } catch (Throwable th) {
                        Exceptions.throwIfFatal(th);
                        ((QueueDrainSubscriber) this).cancelled = true;
                        subscription.cancel();
                        EmptySubscription.error(th, subscriber);
                    }
                } catch (Throwable th2) {
                    Exceptions.throwIfFatal(th2);
                    ((QueueDrainSubscriber) this).cancelled = true;
                    subscription.cancel();
                    EmptySubscription.error(th2, subscriber);
                }
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            requested(j);
        }
    }

    public FlowableBufferBoundarySupplier(Flowable<T> flowable, Callable<? extends Publisher<B>> callable, Callable<U> callable2) {
        super(flowable);
        this.boundarySupplier = callable;
        this.bufferSupplier = callable2;
    }

    /* access modifiers changed from: protected */
    @Override // io.reactivex.Flowable
    public void subscribeActual(Subscriber<? super U> subscriber) {
        ((AbstractFlowableWithUpstream) this).source.subscribe((FlowableSubscriber) new BufferBoundarySupplierSubscriber(new SerializedSubscriber(subscriber), this.bufferSupplier, this.boundarySupplier));
    }
}
