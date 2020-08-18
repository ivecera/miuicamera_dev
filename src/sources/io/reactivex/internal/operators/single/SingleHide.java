package io.reactivex.internal.operators.single;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;

public final class SingleHide<T> extends Single<T> {
    final SingleSource<? extends T> source;

    static final class HideSingleObserver<T> implements SingleObserver<T>, Disposable {
        final SingleObserver<? super T> actual;

        /* renamed from: d  reason: collision with root package name */
        Disposable f753d;

        HideSingleObserver(SingleObserver<? super T> singleObserver) {
            this.actual = singleObserver;
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            this.f753d.dispose();
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.f753d.isDisposed();
        }

        @Override // io.reactivex.SingleObserver
        public void onError(Throwable th) {
            this.actual.onError(th);
        }

        @Override // io.reactivex.SingleObserver
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.f753d, disposable)) {
                this.f753d = disposable;
                this.actual.onSubscribe(this);
            }
        }

        @Override // io.reactivex.SingleObserver
        public void onSuccess(T t) {
            this.actual.onSuccess(t);
        }
    }

    public SingleHide(SingleSource<? extends T> singleSource) {
        this.source = singleSource;
    }

    /* access modifiers changed from: protected */
    @Override // io.reactivex.Single
    public void subscribeActual(SingleObserver<? super T> singleObserver) {
        this.source.subscribe(new HideSingleObserver(singleObserver));
    }
}
