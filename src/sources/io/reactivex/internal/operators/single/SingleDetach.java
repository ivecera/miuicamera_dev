package io.reactivex.internal.operators.single;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.annotations.Experimental;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;

@Experimental
public final class SingleDetach<T> extends Single<T> {
    final SingleSource<T> source;

    static final class DetachSingleObserver<T> implements SingleObserver<T>, Disposable {
        SingleObserver<? super T> actual;

        /* renamed from: d  reason: collision with root package name */
        Disposable f746d;

        DetachSingleObserver(SingleObserver<? super T> singleObserver) {
            this.actual = singleObserver;
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            this.actual = null;
            this.f746d.dispose();
            this.f746d = DisposableHelper.DISPOSED;
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.f746d.isDisposed();
        }

        @Override // io.reactivex.SingleObserver
        public void onError(Throwable th) {
            this.f746d = DisposableHelper.DISPOSED;
            SingleObserver<? super T> singleObserver = this.actual;
            if (singleObserver != null) {
                this.actual = null;
                singleObserver.onError(th);
            }
        }

        @Override // io.reactivex.SingleObserver
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.f746d, disposable)) {
                this.f746d = disposable;
                this.actual.onSubscribe(this);
            }
        }

        @Override // io.reactivex.SingleObserver
        public void onSuccess(T t) {
            this.f746d = DisposableHelper.DISPOSED;
            SingleObserver<? super T> singleObserver = this.actual;
            if (singleObserver != null) {
                this.actual = null;
                singleObserver.onSuccess(t);
            }
        }
    }

    public SingleDetach(SingleSource<T> singleSource) {
        this.source = singleSource;
    }

    /* access modifiers changed from: protected */
    @Override // io.reactivex.Single
    public void subscribeActual(SingleObserver<? super T> singleObserver) {
        this.source.subscribe(new DetachSingleObserver(singleObserver));
    }
}
