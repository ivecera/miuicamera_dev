package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.subscribers.SerializedSubscriber;
import org.reactivestreams.Subscriber;

public final class FlowableSerialized<T> extends AbstractFlowableWithUpstream<T, T> {
    public FlowableSerialized(Flowable<T> flowable) {
        super(flowable);
    }

    /* access modifiers changed from: protected */
    @Override // io.reactivex.Flowable
    public void subscribeActual(Subscriber<? super T> subscriber) {
        ((AbstractFlowableWithUpstream) this).source.subscribe((FlowableSubscriber) new SerializedSubscriber(subscriber));
    }
}
