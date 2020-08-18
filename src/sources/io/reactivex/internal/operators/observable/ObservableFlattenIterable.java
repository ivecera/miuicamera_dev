package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.plugins.RxJavaPlugins;

public final class ObservableFlattenIterable<T, R> extends AbstractObservableWithUpstream<T, R> {
    final Function<? super T, ? extends Iterable<? extends R>> mapper;

    static final class FlattenIterableObserver<T, R> implements Observer<T>, Disposable {
        final Observer<? super R> actual;

        /* renamed from: d  reason: collision with root package name */
        Disposable f733d;
        final Function<? super T, ? extends Iterable<? extends R>> mapper;

        FlattenIterableObserver(Observer<? super R> observer, Function<? super T, ? extends Iterable<? extends R>> function) {
            this.actual = observer;
            this.mapper = function;
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            this.f733d.dispose();
            this.f733d = DisposableHelper.DISPOSED;
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.f733d.isDisposed();
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            Disposable disposable = this.f733d;
            DisposableHelper disposableHelper = DisposableHelper.DISPOSED;
            if (disposable != disposableHelper) {
                this.f733d = disposableHelper;
                this.actual.onComplete();
            }
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            Disposable disposable = this.f733d;
            DisposableHelper disposableHelper = DisposableHelper.DISPOSED;
            if (disposable == disposableHelper) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.f733d = disposableHelper;
            this.actual.onError(th);
        }

        @Override // io.reactivex.Observer
        public void onNext(T t) {
            if (this.f733d != DisposableHelper.DISPOSED) {
                try {
                    Observer<? super R> observer = this.actual;
                    for (T t2 : (Iterable) this.mapper.apply(t)) {
                        try {
                            try {
                                ObjectHelper.requireNonNull(t2, "The iterator returned a null value");
                                observer.onNext(t2);
                            } catch (Throwable th) {
                                Exceptions.throwIfFatal(th);
                                this.f733d.dispose();
                                onError(th);
                                return;
                            }
                        } catch (Throwable th2) {
                            Exceptions.throwIfFatal(th2);
                            this.f733d.dispose();
                            onError(th2);
                            return;
                        }
                    }
                } catch (Throwable th3) {
                    Exceptions.throwIfFatal(th3);
                    this.f733d.dispose();
                    onError(th3);
                }
            }
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.f733d, disposable)) {
                this.f733d = disposable;
                this.actual.onSubscribe(this);
            }
        }
    }

    public ObservableFlattenIterable(ObservableSource<T> observableSource, Function<? super T, ? extends Iterable<? extends R>> function) {
        super(observableSource);
        this.mapper = function;
    }

    /* access modifiers changed from: protected */
    @Override // io.reactivex.Observable
    public void subscribeActual(Observer<? super R> observer) {
        ((AbstractObservableWithUpstream) this).source.subscribe(new FlattenIterableObserver(observer, this.mapper));
    }
}
