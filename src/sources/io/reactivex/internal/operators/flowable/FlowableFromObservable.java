package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableFromObservable<T> extends Flowable<T> {
    private final Observable<T> upstream;

    static class SubscriberObserver<T> implements Observer<T>, Subscription {

        /* renamed from: d  reason: collision with root package name */
        private Disposable f694d;
        private final Subscriber<? super T> s;

        SubscriberObserver(Subscriber<? super T> subscriber) {
            this.s = subscriber;
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.f694d.dispose();
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            this.s.onComplete();
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            this.s.onError(th);
        }

        @Override // io.reactivex.Observer
        public void onNext(T t) {
            this.s.onNext(t);
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable) {
            this.f694d = disposable;
            this.s.onSubscribe(this);
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
        }
    }

    public FlowableFromObservable(Observable<T> observable) {
        this.upstream = observable;
    }

    /* access modifiers changed from: protected */
    @Override // io.reactivex.Flowable
    public void subscribeActual(Subscriber<? super T> subscriber) {
        this.upstream.subscribe(new SubscriberObserver(subscriber));
    }
}
