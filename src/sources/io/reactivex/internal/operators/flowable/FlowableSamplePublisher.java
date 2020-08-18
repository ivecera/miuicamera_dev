package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.subscribers.SerializedSubscriber;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableSamplePublisher<T> extends Flowable<T> {
    final boolean emitLast;
    final Publisher<?> other;
    final Publisher<T> source;

    static final class SampleMainEmitLast<T> extends SamplePublisherSubscriber<T> {
        private static final long serialVersionUID = -3029755663834015785L;
        volatile boolean done;
        final AtomicInteger wip = new AtomicInteger();

        SampleMainEmitLast(Subscriber<? super T> subscriber, Publisher<?> publisher) {
            super(subscriber, publisher);
        }

        /* access modifiers changed from: package-private */
        @Override // io.reactivex.internal.operators.flowable.FlowableSamplePublisher.SamplePublisherSubscriber
        public void completeMain() {
            this.done = true;
            if (this.wip.getAndIncrement() == 0) {
                emit();
                ((SamplePublisherSubscriber) this).actual.onComplete();
            }
        }

        /* access modifiers changed from: package-private */
        @Override // io.reactivex.internal.operators.flowable.FlowableSamplePublisher.SamplePublisherSubscriber
        public void completeOther() {
            this.done = true;
            if (this.wip.getAndIncrement() == 0) {
                emit();
                ((SamplePublisherSubscriber) this).actual.onComplete();
            }
        }

        /* access modifiers changed from: package-private */
        @Override // io.reactivex.internal.operators.flowable.FlowableSamplePublisher.SamplePublisherSubscriber
        public void run() {
            if (this.wip.getAndIncrement() == 0) {
                do {
                    boolean z = this.done;
                    emit();
                    if (z) {
                        ((SamplePublisherSubscriber) this).actual.onComplete();
                        return;
                    }
                } while (this.wip.decrementAndGet() != 0);
            }
        }
    }

    static final class SampleMainNoLast<T> extends SamplePublisherSubscriber<T> {
        private static final long serialVersionUID = -3029755663834015785L;

        SampleMainNoLast(Subscriber<? super T> subscriber, Publisher<?> publisher) {
            super(subscriber, publisher);
        }

        /* access modifiers changed from: package-private */
        @Override // io.reactivex.internal.operators.flowable.FlowableSamplePublisher.SamplePublisherSubscriber
        public void completeMain() {
            ((SamplePublisherSubscriber) this).actual.onComplete();
        }

        /* access modifiers changed from: package-private */
        @Override // io.reactivex.internal.operators.flowable.FlowableSamplePublisher.SamplePublisherSubscriber
        public void completeOther() {
            ((SamplePublisherSubscriber) this).actual.onComplete();
        }

        /* access modifiers changed from: package-private */
        @Override // io.reactivex.internal.operators.flowable.FlowableSamplePublisher.SamplePublisherSubscriber
        public void run() {
            emit();
        }
    }

    static abstract class SamplePublisherSubscriber<T> extends AtomicReference<T> implements FlowableSubscriber<T>, Subscription {
        private static final long serialVersionUID = -3517602651313910099L;
        final Subscriber<? super T> actual;
        final AtomicReference<Subscription> other = new AtomicReference<>();
        final AtomicLong requested = new AtomicLong();
        Subscription s;
        final Publisher<?> sampler;

        SamplePublisherSubscriber(Subscriber<? super T> subscriber, Publisher<?> publisher) {
            this.actual = subscriber;
            this.sampler = publisher;
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            SubscriptionHelper.cancel(this.other);
            this.s.cancel();
        }

        public void complete() {
            this.s.cancel();
            completeOther();
        }

        /* access modifiers changed from: package-private */
        public abstract void completeMain();

        /* access modifiers changed from: package-private */
        public abstract void completeOther();

        /* access modifiers changed from: package-private */
        public void emit() {
            Object andSet = getAndSet(null);
            if (andSet == null) {
                return;
            }
            if (this.requested.get() != 0) {
                this.actual.onNext(andSet);
                BackpressureHelper.produced(this.requested, 1);
                return;
            }
            cancel();
            this.actual.onError(new MissingBackpressureException("Couldn't emit value due to lack of requests!"));
        }

        public void error(Throwable th) {
            this.s.cancel();
            this.actual.onError(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            SubscriptionHelper.cancel(this.other);
            completeMain();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            SubscriptionHelper.cancel(this.other);
            this.actual.onError(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            lazySet(t);
        }

        @Override // io.reactivex.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.s, subscription)) {
                this.s = subscription;
                this.actual.onSubscribe(this);
                if (this.other.get() == null) {
                    this.sampler.subscribe(new SamplerSubscriber(this));
                    subscription.request(Long.MAX_VALUE);
                }
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (SubscriptionHelper.validate(j)) {
                BackpressureHelper.add(this.requested, j);
            }
        }

        /* access modifiers changed from: package-private */
        public abstract void run();

        /* access modifiers changed from: package-private */
        public boolean setOther(Subscription subscription) {
            return SubscriptionHelper.setOnce(this.other, subscription);
        }
    }

    static final class SamplerSubscriber<T> implements FlowableSubscriber<Object> {
        final SamplePublisherSubscriber<T> parent;

        SamplerSubscriber(SamplePublisherSubscriber<T> samplePublisherSubscriber) {
            this.parent = samplePublisherSubscriber;
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.parent.complete();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            this.parent.error(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(Object obj) {
            this.parent.run();
        }

        @Override // io.reactivex.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (this.parent.setOther(subscription)) {
                subscription.request(Long.MAX_VALUE);
            }
        }
    }

    public FlowableSamplePublisher(Publisher<T> publisher, Publisher<?> publisher2, boolean z) {
        this.source = publisher;
        this.other = publisher2;
        this.emitLast = z;
    }

    /* access modifiers changed from: protected */
    @Override // io.reactivex.Flowable
    public void subscribeActual(Subscriber<? super T> subscriber) {
        SerializedSubscriber serializedSubscriber = new SerializedSubscriber(subscriber);
        if (this.emitLast) {
            this.source.subscribe(new SampleMainEmitLast(serializedSubscriber, this.other));
        } else {
            this.source.subscribe(new SampleMainNoLast(serializedSubscriber, this.other));
        }
    }
}
