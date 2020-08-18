package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import java.util.concurrent.Callable;

public final class ObservableMapNotification<T, R> extends AbstractObservableWithUpstream<T, ObservableSource<? extends R>> {
    final Callable<? extends ObservableSource<? extends R>> onCompleteSupplier;
    final Function<? super Throwable, ? extends ObservableSource<? extends R>> onErrorMapper;
    final Function<? super T, ? extends ObservableSource<? extends R>> onNextMapper;

    static final class MapNotificationObserver<T, R> implements Observer<T>, Disposable {
        final Observer<? super ObservableSource<? extends R>> actual;
        final Callable<? extends ObservableSource<? extends R>> onCompleteSupplier;
        final Function<? super Throwable, ? extends ObservableSource<? extends R>> onErrorMapper;
        final Function<? super T, ? extends ObservableSource<? extends R>> onNextMapper;
        Disposable s;

        MapNotificationObserver(Observer<? super ObservableSource<? extends R>> observer, Function<? super T, ? extends ObservableSource<? extends R>> function, Function<? super Throwable, ? extends ObservableSource<? extends R>> function2, Callable<? extends ObservableSource<? extends R>> callable) {
            this.actual = observer;
            this.onNextMapper = function;
            this.onErrorMapper = function2;
            this.onCompleteSupplier = callable;
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            this.s.dispose();
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.s.isDisposed();
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            try {
                Object call = this.onCompleteSupplier.call();
                ObjectHelper.requireNonNull(call, "The onComplete ObservableSource returned is null");
                this.actual.onNext((ObservableSource) call);
                this.actual.onComplete();
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.actual.onError(th);
            }
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            try {
                Object apply = this.onErrorMapper.apply(th);
                ObjectHelper.requireNonNull(apply, "The onError ObservableSource returned is null");
                this.actual.onNext((ObservableSource) apply);
                this.actual.onComplete();
            } catch (Throwable th2) {
                Exceptions.throwIfFatal(th2);
                this.actual.onError(new CompositeException(th, th2));
            }
        }

        @Override // io.reactivex.Observer
        public void onNext(T t) {
            try {
                Object apply = this.onNextMapper.apply(t);
                ObjectHelper.requireNonNull(apply, "The onNext ObservableSource returned is null");
                this.actual.onNext((ObservableSource) apply);
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.actual.onError(th);
            }
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.s, disposable)) {
                this.s = disposable;
                this.actual.onSubscribe(this);
            }
        }
    }

    public ObservableMapNotification(ObservableSource<T> observableSource, Function<? super T, ? extends ObservableSource<? extends R>> function, Function<? super Throwable, ? extends ObservableSource<? extends R>> function2, Callable<? extends ObservableSource<? extends R>> callable) {
        super(observableSource);
        this.onNextMapper = function;
        this.onErrorMapper = function2;
        this.onCompleteSupplier = callable;
    }

    @Override // io.reactivex.Observable
    public void subscribeActual(Observer<? super ObservableSource<? extends R>> observer) {
        ((AbstractObservableWithUpstream) this).source.subscribe(new MapNotificationObserver(observer, this.onNextMapper, this.onErrorMapper, this.onCompleteSupplier));
    }
}
