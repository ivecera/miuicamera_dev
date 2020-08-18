package io.reactivex.internal.operators.single;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.ObjectHelper;

public final class SingleMap<T, R> extends Single<R> {
    final Function<? super T, ? extends R> mapper;
    final SingleSource<? extends T> source;

    static final class MapSingleObserver<T, R> implements SingleObserver<T> {
        final Function<? super T, ? extends R> mapper;
        final SingleObserver<? super R> t;

        MapSingleObserver(SingleObserver<? super R> singleObserver, Function<? super T, ? extends R> function) {
            this.t = singleObserver;
            this.mapper = function;
        }

        @Override // io.reactivex.SingleObserver
        public void onError(Throwable th) {
            this.t.onError(th);
        }

        @Override // io.reactivex.SingleObserver
        public void onSubscribe(Disposable disposable) {
            this.t.onSubscribe(disposable);
        }

        @Override // io.reactivex.SingleObserver
        public void onSuccess(T t2) {
            try {
                Object apply = this.mapper.apply(t2);
                ObjectHelper.requireNonNull(apply, "The mapper function returned a null value.");
                this.t.onSuccess(apply);
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                onError(th);
            }
        }
    }

    public SingleMap(SingleSource<? extends T> singleSource, Function<? super T, ? extends R> function) {
        this.source = singleSource;
        this.mapper = function;
    }

    /* access modifiers changed from: protected */
    @Override // io.reactivex.Single
    public void subscribeActual(SingleObserver<? super R> singleObserver) {
        this.source.subscribe(new MapSingleObserver(singleObserver, this.mapper));
    }
}
