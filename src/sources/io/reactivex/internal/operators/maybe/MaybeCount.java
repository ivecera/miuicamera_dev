package io.reactivex.internal.operators.maybe;

import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.fuseable.HasUpstreamMaybeSource;

public final class MaybeCount<T> extends Single<Long> implements HasUpstreamMaybeSource<T> {
    final MaybeSource<T> source;

    static final class CountMaybeObserver implements MaybeObserver<Object>, Disposable {
        final SingleObserver<? super Long> actual;

        /* renamed from: d  reason: collision with root package name */
        Disposable f696d;

        CountMaybeObserver(SingleObserver<? super Long> singleObserver) {
            this.actual = singleObserver;
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            this.f696d.dispose();
            this.f696d = DisposableHelper.DISPOSED;
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.f696d.isDisposed();
        }

        @Override // io.reactivex.MaybeObserver
        public void onComplete() {
            this.f696d = DisposableHelper.DISPOSED;
            this.actual.onSuccess(0L);
        }

        @Override // io.reactivex.MaybeObserver
        public void onError(Throwable th) {
            this.f696d = DisposableHelper.DISPOSED;
            this.actual.onError(th);
        }

        @Override // io.reactivex.MaybeObserver
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.f696d, disposable)) {
                this.f696d = disposable;
                this.actual.onSubscribe(this);
            }
        }

        @Override // io.reactivex.MaybeObserver
        public void onSuccess(Object obj) {
            this.f696d = DisposableHelper.DISPOSED;
            this.actual.onSuccess(1L);
        }
    }

    public MaybeCount(MaybeSource<T> maybeSource) {
        this.source = maybeSource;
    }

    @Override // io.reactivex.internal.fuseable.HasUpstreamMaybeSource
    public MaybeSource<T> source() {
        return this.source;
    }

    /* access modifiers changed from: protected */
    @Override // io.reactivex.Single
    public void subscribeActual(SingleObserver<? super Long> singleObserver) {
        this.source.subscribe(new CountMaybeObserver(singleObserver));
    }
}
