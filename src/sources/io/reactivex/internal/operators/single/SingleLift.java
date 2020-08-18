package io.reactivex.internal.operators.single;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleOperator;
import io.reactivex.SingleSource;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.functions.ObjectHelper;

public final class SingleLift<T, R> extends Single<R> {
    final SingleOperator<? extends R, ? super T> onLift;
    final SingleSource<T> source;

    public SingleLift(SingleSource<T> singleSource, SingleOperator<? extends R, ? super T> singleOperator) {
        this.source = singleSource;
        this.onLift = singleOperator;
    }

    /* access modifiers changed from: protected */
    @Override // io.reactivex.Single
    public void subscribeActual(SingleObserver<? super R> singleObserver) {
        try {
            SingleObserver<? super T> apply = this.onLift.apply(singleObserver);
            ObjectHelper.requireNonNull(apply, "The onLift returned a null SingleObserver");
            this.source.subscribe(apply);
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            EmptyDisposable.error(th, singleObserver);
        }
    }
}
