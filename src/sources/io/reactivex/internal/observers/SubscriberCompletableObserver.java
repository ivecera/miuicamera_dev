package io.reactivex.internal.observers;

import io.reactivex.CompletableObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class SubscriberCompletableObserver<T> implements CompletableObserver, Subscription {

    /* renamed from: d  reason: collision with root package name */
    Disposable f686d;
    final Subscriber<? super T> subscriber;

    public SubscriberCompletableObserver(Subscriber<? super T> subscriber2) {
        this.subscriber = subscriber2;
    }

    @Override // org.reactivestreams.Subscription
    public void cancel() {
        this.f686d.dispose();
    }

    @Override // io.reactivex.CompletableObserver
    public void onComplete() {
        this.subscriber.onComplete();
    }

    @Override // io.reactivex.CompletableObserver
    public void onError(Throwable th) {
        this.subscriber.onError(th);
    }

    @Override // io.reactivex.CompletableObserver
    public void onSubscribe(Disposable disposable) {
        if (DisposableHelper.validate(this.f686d, disposable)) {
            this.f686d = disposable;
            this.subscriber.onSubscribe(this);
        }
    }

    @Override // org.reactivestreams.Subscription
    public void request(long j) {
    }
}
