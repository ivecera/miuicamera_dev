package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.SimplePlainQueue;
import io.reactivex.internal.queue.MpscLinkedQueue;
import io.reactivex.internal.subscribers.QueueDrainSubscriber;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.NotificationLite;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.processors.UnicastProcessor;
import io.reactivex.subscribers.DisposableSubscriber;
import io.reactivex.subscribers.SerializedSubscriber;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableWindowBoundarySelector<T, B, V> extends AbstractFlowableWithUpstream<T, Flowable<T>> {
    final int bufferSize;
    final Function<? super B, ? extends Publisher<V>> close;
    final Publisher<B> open;

    static final class OperatorWindowBoundaryCloseSubscriber<T, V> extends DisposableSubscriber<V> {
        boolean done;
        final WindowBoundaryMainSubscriber<T, ?, V> parent;
        final UnicastProcessor<T> w;

        OperatorWindowBoundaryCloseSubscriber(WindowBoundaryMainSubscriber<T, ?, V> windowBoundaryMainSubscriber, UnicastProcessor<T> unicastProcessor) {
            this.parent = windowBoundaryMainSubscriber;
            this.w = unicastProcessor;
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (!this.done) {
                this.done = true;
                this.parent.close(this);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.done) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.done = true;
            this.parent.error(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(V v) {
            if (!this.done) {
                this.done = true;
                cancel();
                this.parent.close(this);
            }
        }
    }

    static final class OperatorWindowBoundaryOpenSubscriber<T, B> extends DisposableSubscriber<B> {
        boolean done;
        final WindowBoundaryMainSubscriber<T, B, ?> parent;

        OperatorWindowBoundaryOpenSubscriber(WindowBoundaryMainSubscriber<T, B, ?> windowBoundaryMainSubscriber) {
            this.parent = windowBoundaryMainSubscriber;
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (!this.done) {
                this.done = true;
                this.parent.onComplete();
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.done) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.done = true;
            this.parent.error(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(B b2) {
            if (!this.done) {
                this.parent.open(b2);
            }
        }
    }

    static final class WindowBoundaryMainSubscriber<T, B, V> extends QueueDrainSubscriber<T, Object, Flowable<T>> implements Subscription {
        final AtomicReference<Disposable> boundary = new AtomicReference<>();
        final int bufferSize;
        final Function<? super B, ? extends Publisher<V>> close;
        final Publisher<B> open;
        final CompositeDisposable resources;
        Subscription s;
        final AtomicLong windows = new AtomicLong();
        final List<UnicastProcessor<T>> ws;

        WindowBoundaryMainSubscriber(Subscriber<? super Flowable<T>> subscriber, Publisher<B> publisher, Function<? super B, ? extends Publisher<V>> function, int i) {
            super(subscriber, new MpscLinkedQueue());
            this.open = publisher;
            this.close = function;
            this.bufferSize = i;
            this.resources = new CompositeDisposable();
            this.ws = new ArrayList();
            this.windows.lazySet(1);
        }

        @Override // io.reactivex.internal.subscribers.QueueDrainSubscriber, io.reactivex.internal.util.QueueDrain
        public boolean accept(Subscriber<? super Flowable<T>> subscriber, Object obj) {
            return false;
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            ((QueueDrainSubscriber) this).cancelled = true;
        }

        /* access modifiers changed from: package-private */
        public void close(OperatorWindowBoundaryCloseSubscriber<T, V> operatorWindowBoundaryCloseSubscriber) {
            this.resources.delete(operatorWindowBoundaryCloseSubscriber);
            ((QueueDrainSubscriber) this).queue.offer(new WindowOperation(operatorWindowBoundaryCloseSubscriber.w, null));
            if (enter()) {
                drainLoop();
            }
        }

        /* access modifiers changed from: package-private */
        public void dispose() {
            this.resources.dispose();
            DisposableHelper.dispose(this.boundary);
        }

        /* access modifiers changed from: package-private */
        public void drainLoop() {
            SimplePlainQueue<U> simplePlainQueue = ((QueueDrainSubscriber) this).queue;
            Subscriber<? super V> subscriber = ((QueueDrainSubscriber) this).actual;
            List<UnicastProcessor<T>> list = this.ws;
            int i = 1;
            while (true) {
                boolean z = ((QueueDrainSubscriber) this).done;
                WindowOperation poll = simplePlainQueue.poll();
                boolean z2 = poll == null;
                if (z && z2) {
                    dispose();
                    Throwable th = ((QueueDrainSubscriber) this).error;
                    if (th != null) {
                        for (UnicastProcessor<T> unicastProcessor : list) {
                            unicastProcessor.onError(th);
                        }
                    } else {
                        for (UnicastProcessor<T> unicastProcessor2 : list) {
                            unicastProcessor2.onComplete();
                        }
                    }
                    list.clear();
                    return;
                } else if (z2) {
                    i = leave(-i);
                    if (i == 0) {
                        return;
                    }
                } else if (poll instanceof WindowOperation) {
                    WindowOperation windowOperation = poll;
                    UnicastProcessor<T> unicastProcessor3 = windowOperation.w;
                    if (unicastProcessor3 != null) {
                        if (list.remove(unicastProcessor3)) {
                            windowOperation.w.onComplete();
                            if (this.windows.decrementAndGet() == 0) {
                                dispose();
                                return;
                            }
                        } else {
                            continue;
                        }
                    } else if (!((QueueDrainSubscriber) this).cancelled) {
                        UnicastProcessor<T> create = UnicastProcessor.create(this.bufferSize);
                        long requested = requested();
                        if (requested != 0) {
                            list.add(create);
                            subscriber.onNext(create);
                            if (requested != Long.MAX_VALUE) {
                                produced(1);
                            }
                            try {
                                Object apply = this.close.apply(windowOperation.open);
                                ObjectHelper.requireNonNull(apply, "The publisher supplied is null");
                                Publisher publisher = (Publisher) apply;
                                OperatorWindowBoundaryCloseSubscriber operatorWindowBoundaryCloseSubscriber = new OperatorWindowBoundaryCloseSubscriber(this, create);
                                if (this.resources.add(operatorWindowBoundaryCloseSubscriber)) {
                                    this.windows.getAndIncrement();
                                    publisher.subscribe(operatorWindowBoundaryCloseSubscriber);
                                }
                            } catch (Throwable th2) {
                                ((QueueDrainSubscriber) this).cancelled = true;
                                subscriber.onError(th2);
                            }
                        } else {
                            ((QueueDrainSubscriber) this).cancelled = true;
                            subscriber.onError(new MissingBackpressureException("Could not deliver new window due to lack of requests"));
                        }
                    }
                } else {
                    for (UnicastProcessor<T> unicastProcessor4 : list) {
                        NotificationLite.getValue(poll);
                        unicastProcessor4.onNext(poll);
                    }
                }
            }
        }

        /* access modifiers changed from: package-private */
        public void error(Throwable th) {
            this.s.cancel();
            this.resources.dispose();
            DisposableHelper.dispose(this.boundary);
            ((QueueDrainSubscriber) this).actual.onError(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (!((QueueDrainSubscriber) this).done) {
                ((QueueDrainSubscriber) this).done = true;
                if (enter()) {
                    drainLoop();
                }
                if (this.windows.decrementAndGet() == 0) {
                    this.resources.dispose();
                }
                ((QueueDrainSubscriber) this).actual.onComplete();
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (((QueueDrainSubscriber) this).done) {
                RxJavaPlugins.onError(th);
                return;
            }
            ((QueueDrainSubscriber) this).error = th;
            ((QueueDrainSubscriber) this).done = true;
            if (enter()) {
                drainLoop();
            }
            if (this.windows.decrementAndGet() == 0) {
                this.resources.dispose();
            }
            ((QueueDrainSubscriber) this).actual.onError(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (!((QueueDrainSubscriber) this).done) {
                if (fastEnter()) {
                    for (UnicastProcessor<T> unicastProcessor : this.ws) {
                        unicastProcessor.onNext(t);
                    }
                    if (leave(-1) == 0) {
                        return;
                    }
                } else {
                    SimplePlainQueue<U> simplePlainQueue = ((QueueDrainSubscriber) this).queue;
                    NotificationLite.next(t);
                    simplePlainQueue.offer(t);
                    if (!enter()) {
                        return;
                    }
                }
                drainLoop();
            }
        }

        @Override // io.reactivex.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.s, subscription)) {
                this.s = subscription;
                ((QueueDrainSubscriber) this).actual.onSubscribe(this);
                if (!((QueueDrainSubscriber) this).cancelled) {
                    OperatorWindowBoundaryOpenSubscriber operatorWindowBoundaryOpenSubscriber = new OperatorWindowBoundaryOpenSubscriber(this);
                    if (this.boundary.compareAndSet(null, operatorWindowBoundaryOpenSubscriber)) {
                        this.windows.getAndIncrement();
                        subscription.request(Long.MAX_VALUE);
                        this.open.subscribe(operatorWindowBoundaryOpenSubscriber);
                    }
                }
            }
        }

        /* access modifiers changed from: package-private */
        public void open(B b2) {
            ((QueueDrainSubscriber) this).queue.offer(new WindowOperation(null, b2));
            if (enter()) {
                drainLoop();
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            requested(j);
        }
    }

    static final class WindowOperation<T, B> {
        final B open;
        final UnicastProcessor<T> w;

        WindowOperation(UnicastProcessor<T> unicastProcessor, B b2) {
            this.w = unicastProcessor;
            this.open = b2;
        }
    }

    public FlowableWindowBoundarySelector(Flowable<T> flowable, Publisher<B> publisher, Function<? super B, ? extends Publisher<V>> function, int i) {
        super(flowable);
        this.open = publisher;
        this.close = function;
        this.bufferSize = i;
    }

    /* access modifiers changed from: protected */
    @Override // io.reactivex.Flowable
    public void subscribeActual(Subscriber<? super Flowable<T>> subscriber) {
        ((AbstractFlowableWithUpstream) this).source.subscribe((FlowableSubscriber) new WindowBoundaryMainSubscriber(new SerializedSubscriber(subscriber), this.open, this.close, this.bufferSize));
    }
}
