package io.reactivex.internal.subscribers;

import io.reactivex.FlowableSubscriber;
import io.reactivex.internal.subscriptions.DeferredScalarSubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public abstract class DeferredScalarSubscriber<T, R> extends DeferredScalarSubscription<R> implements FlowableSubscriber<T> {
    private static final long serialVersionUID = 2984505488220891551L;
    protected boolean hasValue;
    protected Subscription s;

    public DeferredScalarSubscriber(Subscriber<? super R> subscriber) {
        super(subscriber);
    }

    @Override // io.reactivex.internal.subscriptions.DeferredScalarSubscription, org.reactivestreams.Subscription
    public void cancel() {
        super.cancel();
        this.s.cancel();
    }

    @Override // org.reactivestreams.Subscriber
    public void onComplete() {
        if (this.hasValue) {
            complete(((DeferredScalarSubscription) this).value);
        } else {
            ((DeferredScalarSubscription) this).actual.onComplete();
        }
    }

    @Override // org.reactivestreams.Subscriber
    public void onError(Throwable th) {
        ((DeferredScalarSubscription) this).value = null;
        ((DeferredScalarSubscription) this).actual.onError(th);
    }

    @Override // io.reactivex.FlowableSubscriber, org.reactivestreams.Subscriber
    public void onSubscribe(Subscription subscription) {
        if (SubscriptionHelper.validate(this.s, subscription)) {
            this.s = subscription;
            ((DeferredScalarSubscription) this).actual.onSubscribe(this);
            subscription.request(Long.MAX_VALUE);
        }
    }
}
