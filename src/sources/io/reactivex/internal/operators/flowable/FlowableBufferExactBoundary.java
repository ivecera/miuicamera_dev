package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.queue.MpscLinkedQueue;
import io.reactivex.internal.subscribers.QueueDrainSubscriber;
import io.reactivex.internal.subscriptions.EmptySubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.subscribers.DisposableSubscriber;
import io.reactivex.subscribers.SerializedSubscriber;
import java.util.Collection;
import java.util.concurrent.Callable;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableBufferExactBoundary<T, U extends Collection<? super T>, B> extends AbstractFlowableWithUpstream<T, U> {
    final Publisher<B> boundary;
    final Callable<U> bufferSupplier;

    static final class BufferBoundarySubscriber<T, U extends Collection<? super T>, B> extends DisposableSubscriber<B> {
        final BufferExactBoundarySubscriber<T, U, B> parent;

        BufferBoundarySubscriber(BufferExactBoundarySubscriber<T, U, B> bufferExactBoundarySubscriber) {
            this.parent = bufferExactBoundarySubscriber;
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.parent.onComplete();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            this.parent.onError(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(B b2) {
            this.parent.next();
        }
    }

    static final class BufferExactBoundarySubscriber<T, U extends Collection<? super T>, B> extends QueueDrainSubscriber<T, U, U> implements FlowableSubscriber<T>, Subscription, Disposable {
        final Publisher<B> boundary;
        U buffer;
        final Callable<U> bufferSupplier;
        Disposable other;
        Subscription s;

        BufferExactBoundarySubscriber(Subscriber<? super U> subscriber, Callable<U> callable, Publisher<B> publisher) {
            super(subscriber, new MpscLinkedQueue());
            this.bufferSupplier = callable;
            this.boundary = publisher;
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
                this.other.dispose();
                this.s.cancel();
                if (enter()) {
                    ((QueueDrainSubscriber) this).queue.clear();
                }
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            cancel();
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return ((QueueDrainSubscriber) this).cancelled;
        }

        /* access modifiers changed from: package-private */
        public void next() {
            try {
                U call = this.bufferSupplier.call();
                ObjectHelper.requireNonNull(call, "The buffer supplied is null");
                U u = call;
                synchronized (this) {
                    U u2 = this.buffer;
                    if (u2 != null) {
                        this.buffer = u;
                        fastPathEmitMax(u2, false, this);
                    }
                }
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                cancel();
                ((QueueDrainSubscriber) this).actual.onError(th);
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
                try {
                    U call = this.bufferSupplier.call();
                    ObjectHelper.requireNonNull(call, "The buffer supplied is null");
                    this.buffer = call;
                    BufferBoundarySubscriber bufferBoundarySubscriber = new BufferBoundarySubscriber(this);
                    this.other = bufferBoundarySubscriber;
                    ((QueueDrainSubscriber) this).actual.onSubscribe(this);
                    if (!((QueueDrainSubscriber) this).cancelled) {
                        subscription.request(Long.MAX_VALUE);
                        this.boundary.subscribe(bufferBoundarySubscriber);
                    }
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    ((QueueDrainSubscriber) this).cancelled = true;
                    subscription.cancel();
                    EmptySubscription.error(th, ((QueueDrainSubscriber) this).actual);
                }
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            requested(j);
        }
    }

    public FlowableBufferExactBoundary(Flowable<T> flowable, Publisher<B> publisher, Callable<U> callable) {
        super(flowable);
        this.boundary = publisher;
        this.bufferSupplier = callable;
    }

    /* access modifiers changed from: protected */
    @Override // io.reactivex.Flowable
    public void subscribeActual(Subscriber<? super U> subscriber) {
        ((AbstractFlowableWithUpstream) this).source.subscribe((FlowableSubscriber) new BufferExactBoundarySubscriber(new SerializedSubscriber(subscriber), this.bufferSupplier, this.boundary));
    }
}
