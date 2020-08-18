package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Notification;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableDematerialize<T> extends AbstractFlowableWithUpstream<Notification<T>, T> {

    static final class DematerializeSubscriber<T> implements FlowableSubscriber<Notification<T>>, Subscription {
        final Subscriber<? super T> actual;
        boolean done;
        Subscription s;

        DematerializeSubscriber(Subscriber<? super T> subscriber) {
            this.actual = subscriber;
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.s.cancel();
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (!this.done) {
                this.done = true;
                this.actual.onComplete();
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.done) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.done = true;
            this.actual.onError(th);
        }

        public void onNext(Notification<T> notification) {
            if (this.done) {
                if (notification.isOnError()) {
                    RxJavaPlugins.onError(notification.getError());
                }
            } else if (notification.isOnError()) {
                this.s.cancel();
                onError(notification.getError());
            } else if (notification.isOnComplete()) {
                this.s.cancel();
                onComplete();
            } else {
                this.actual.onNext(notification.getValue());
            }
        }

        @Override // org.reactivestreams.Subscriber
        public /* bridge */ /* synthetic */ void onNext(Object obj) {
            onNext((Notification) ((Notification) obj));
        }

        @Override // io.reactivex.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.s, subscription)) {
                this.s = subscription;
                this.actual.onSubscribe(this);
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            this.s.request(j);
        }
    }

    public FlowableDematerialize(Flowable<Notification<T>> flowable) {
        super(flowable);
    }

    /* access modifiers changed from: protected */
    @Override // io.reactivex.Flowable
    public void subscribeActual(Subscriber<? super T> subscriber) {
        ((AbstractFlowableWithUpstream) this).source.subscribe((FlowableSubscriber) new DematerializeSubscriber(subscriber));
    }
}
