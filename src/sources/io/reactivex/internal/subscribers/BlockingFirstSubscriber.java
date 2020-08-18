package io.reactivex.internal.subscribers;

import io.reactivex.plugins.RxJavaPlugins;

public final class BlockingFirstSubscriber<T> extends BlockingBaseSubscriber<T> {
    @Override // org.reactivestreams.Subscriber
    public void onError(Throwable th) {
        if (((BlockingBaseSubscriber) this).value == null) {
            ((BlockingBaseSubscriber) this).error = th;
        } else {
            RxJavaPlugins.onError(th);
        }
        countDown();
    }

    @Override // org.reactivestreams.Subscriber
    public void onNext(T t) {
        if (((BlockingBaseSubscriber) this).value == null) {
            ((BlockingBaseSubscriber) this).value = t;
            ((BlockingBaseSubscriber) this).s.cancel();
            countDown();
        }
    }
}
