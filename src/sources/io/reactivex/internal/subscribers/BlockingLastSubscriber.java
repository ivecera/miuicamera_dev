package io.reactivex.internal.subscribers;

public final class BlockingLastSubscriber<T> extends BlockingBaseSubscriber<T> {
    @Override // org.reactivestreams.Subscriber
    public void onError(Throwable th) {
        ((BlockingBaseSubscriber) this).value = null;
        ((BlockingBaseSubscriber) this).error = th;
        countDown();
    }

    @Override // org.reactivestreams.Subscriber
    public void onNext(T t) {
        ((BlockingBaseSubscriber) this).value = t;
    }
}
