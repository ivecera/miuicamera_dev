package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.exceptions.MissingBackpressureException;
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
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableWindowBoundarySupplier<T, B> extends AbstractFlowableWithUpstream<T, Flowable<T>> {
    final int bufferSize;
    final Callable<? extends Publisher<B>> other;

    static final class WindowBoundaryInnerSubscriber<T, B> extends DisposableSubscriber<B> {
        boolean done;
        final WindowBoundaryMainSubscriber<T, B> parent;

        WindowBoundaryInnerSubscriber(WindowBoundaryMainSubscriber<T, B> windowBoundaryMainSubscriber) {
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
            this.parent.onError(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(B b2) {
            if (!this.done) {
                this.done = true;
                cancel();
                this.parent.next();
            }
        }
    }

    static final class WindowBoundaryMainSubscriber<T, B> extends QueueDrainSubscriber<T, Object, Flowable<T>> implements Subscription {
        static final Object NEXT = new Object();
        final AtomicReference<Disposable> boundary = new AtomicReference<>();
        final int bufferSize;
        final Callable<? extends Publisher<B>> other;
        Subscription s;
        UnicastProcessor<T> window;
        final AtomicLong windows = new AtomicLong();

        WindowBoundaryMainSubscriber(Subscriber<? super Flowable<T>> subscriber, Callable<? extends Publisher<B>> callable, int i) {
            super(subscriber, new MpscLinkedQueue());
            this.other = callable;
            this.bufferSize = i;
            this.windows.lazySet(1);
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            ((QueueDrainSubscriber) this).cancelled = true;
        }

        /* access modifiers changed from: package-private */
        public void drainLoop() {
            SimplePlainQueue<U> simplePlainQueue = ((QueueDrainSubscriber) this).queue;
            Subscriber<? super V> subscriber = ((QueueDrainSubscriber) this).actual;
            UnicastProcessor<T> unicastProcessor = this.window;
            int i = 1;
            while (true) {
                boolean z = ((QueueDrainSubscriber) this).done;
                U poll = simplePlainQueue.poll();
                boolean z2 = poll == null;
                if (z && z2) {
                    DisposableHelper.dispose(this.boundary);
                    Throwable th = ((QueueDrainSubscriber) this).error;
                    if (th != null) {
                        unicastProcessor.onError(th);
                        return;
                    } else {
                        unicastProcessor.onComplete();
                        return;
                    }
                } else if (z2) {
                    i = leave(-i);
                    if (i == 0) {
                        return;
                    }
                } else if (poll == NEXT) {
                    unicastProcessor.onComplete();
                    if (this.windows.decrementAndGet() == 0) {
                        DisposableHelper.dispose(this.boundary);
                        return;
                    } else if (!((QueueDrainSubscriber) this).cancelled) {
                        try {
                            Object call = this.other.call();
                            ObjectHelper.requireNonNull(call, "The publisher supplied is null");
                            Publisher publisher = (Publisher) call;
                            UnicastProcessor<T> create = UnicastProcessor.create(this.bufferSize);
                            long requested = requested();
                            if (requested != 0) {
                                this.windows.getAndIncrement();
                                subscriber.onNext(create);
                                if (requested != Long.MAX_VALUE) {
                                    produced(1);
                                }
                                this.window = create;
                                WindowBoundaryInnerSubscriber windowBoundaryInnerSubscriber = new WindowBoundaryInnerSubscriber(this);
                                AtomicReference<Disposable> atomicReference = this.boundary;
                                if (atomicReference.compareAndSet(atomicReference.get(), windowBoundaryInnerSubscriber)) {
                                    publisher.subscribe(windowBoundaryInnerSubscriber);
                                }
                            } else {
                                ((QueueDrainSubscriber) this).cancelled = true;
                                subscriber.onError(new MissingBackpressureException("Could not deliver new window due to lack of requests"));
                            }
                            unicastProcessor = create;
                        } catch (Throwable th2) {
                            Exceptions.throwIfFatal(th2);
                            DisposableHelper.dispose(this.boundary);
                            subscriber.onError(th2);
                            return;
                        }
                    }
                } else {
                    NotificationLite.getValue(poll);
                    unicastProcessor.onNext(poll);
                }
            }
        }

        /* access modifiers changed from: package-private */
        public void next() {
            ((QueueDrainSubscriber) this).queue.offer(NEXT);
            if (enter()) {
                drainLoop();
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (!((QueueDrainSubscriber) this).done) {
                ((QueueDrainSubscriber) this).done = true;
                if (enter()) {
                    drainLoop();
                }
                if (this.windows.decrementAndGet() == 0) {
                    DisposableHelper.dispose(this.boundary);
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
                DisposableHelper.dispose(this.boundary);
            }
            ((QueueDrainSubscriber) this).actual.onError(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (!((QueueDrainSubscriber) this).done) {
                if (fastEnter()) {
                    this.window.onNext(t);
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
                Subscriber<? super V> subscriber = ((QueueDrainSubscriber) this).actual;
                subscriber.onSubscribe(this);
                if (!((QueueDrainSubscriber) this).cancelled) {
                    try {
                        Object call = this.other.call();
                        ObjectHelper.requireNonNull(call, "The first window publisher supplied is null");
                        Publisher publisher = (Publisher) call;
                        UnicastProcessor<T> create = UnicastProcessor.create(this.bufferSize);
                        long requested = requested();
                        if (requested != 0) {
                            subscriber.onNext(create);
                            if (requested != Long.MAX_VALUE) {
                                produced(1);
                            }
                            this.window = create;
                            WindowBoundaryInnerSubscriber windowBoundaryInnerSubscriber = new WindowBoundaryInnerSubscriber(this);
                            if (this.boundary.compareAndSet(null, windowBoundaryInnerSubscriber)) {
                                this.windows.getAndIncrement();
                                subscription.request(Long.MAX_VALUE);
                                publisher.subscribe(windowBoundaryInnerSubscriber);
                                return;
                            }
                            return;
                        }
                        subscription.cancel();
                        subscriber.onError(new MissingBackpressureException("Could not deliver first window due to lack of requests"));
                    } catch (Throwable th) {
                        Exceptions.throwIfFatal(th);
                        subscription.cancel();
                        subscriber.onError(th);
                    }
                }
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            requested(j);
        }
    }

    public FlowableWindowBoundarySupplier(Flowable<T> flowable, Callable<? extends Publisher<B>> callable, int i) {
        super(flowable);
        this.other = callable;
        this.bufferSize = i;
    }

    /* access modifiers changed from: protected */
    @Override // io.reactivex.Flowable
    public void subscribeActual(Subscriber<? super Flowable<T>> subscriber) {
        ((AbstractFlowableWithUpstream) this).source.subscribe((FlowableSubscriber) new WindowBoundaryMainSubscriber(new SerializedSubscriber(subscriber), this.other, this.bufferSize));
    }
}
