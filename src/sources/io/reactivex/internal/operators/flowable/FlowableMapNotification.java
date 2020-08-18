package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.subscribers.SinglePostCompleteSubscriber;
import java.util.concurrent.Callable;
import org.reactivestreams.Subscriber;

public final class FlowableMapNotification<T, R> extends AbstractFlowableWithUpstream<T, R> {
    final Callable<? extends R> onCompleteSupplier;
    final Function<? super Throwable, ? extends R> onErrorMapper;
    final Function<? super T, ? extends R> onNextMapper;

    static final class MapNotificationSubscriber<T, R> extends SinglePostCompleteSubscriber<T, R> {
        private static final long serialVersionUID = 2757120512858778108L;
        final Callable<? extends R> onCompleteSupplier;
        final Function<? super Throwable, ? extends R> onErrorMapper;
        final Function<? super T, ? extends R> onNextMapper;

        MapNotificationSubscriber(Subscriber<? super R> subscriber, Function<? super T, ? extends R> function, Function<? super Throwable, ? extends R> function2, Callable<? extends R> callable) {
            super(subscriber);
            this.onNextMapper = function;
            this.onErrorMapper = function2;
            this.onCompleteSupplier = callable;
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            try {
                R call = this.onCompleteSupplier.call();
                ObjectHelper.requireNonNull(call, "The onComplete publisher returned is null");
                complete(call);
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                ((SinglePostCompleteSubscriber) this).actual.onError(th);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            try {
                R apply = this.onErrorMapper.apply(th);
                ObjectHelper.requireNonNull(apply, "The onError publisher returned is null");
                complete(apply);
            } catch (Throwable th2) {
                Exceptions.throwIfFatal(th2);
                ((SinglePostCompleteSubscriber) this).actual.onError(new CompositeException(th, th2));
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            try {
                Object apply = this.onNextMapper.apply(t);
                ObjectHelper.requireNonNull(apply, "The onNext publisher returned is null");
                ((SinglePostCompleteSubscriber) this).produced++;
                ((SinglePostCompleteSubscriber) this).actual.onNext(apply);
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                ((SinglePostCompleteSubscriber) this).actual.onError(th);
            }
        }
    }

    public FlowableMapNotification(Flowable<T> flowable, Function<? super T, ? extends R> function, Function<? super Throwable, ? extends R> function2, Callable<? extends R> callable) {
        super(flowable);
        this.onNextMapper = function;
        this.onErrorMapper = function2;
        this.onCompleteSupplier = callable;
    }

    /* access modifiers changed from: protected */
    @Override // io.reactivex.Flowable
    public void subscribeActual(Subscriber<? super R> subscriber) {
        ((AbstractFlowableWithUpstream) this).source.subscribe((FlowableSubscriber) new MapNotificationSubscriber(subscriber, this.onNextMapper, this.onErrorMapper, this.onCompleteSupplier));
    }
}
