package io.reactivex.internal.operators.maybe;

import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.HasUpstreamMaybeSource;

public final class MaybeContains<T> extends Single<Boolean> implements HasUpstreamMaybeSource<T> {
    final MaybeSource<T> source;
    final Object value;

    static final class ContainsMaybeObserver implements MaybeObserver<Object>, Disposable {
        final SingleObserver<? super Boolean> actual;

        /* renamed from: d  reason: collision with root package name */
        Disposable f695d;
        final Object value;

        ContainsMaybeObserver(SingleObserver<? super Boolean> singleObserver, Object obj) {
            this.actual = singleObserver;
            this.value = obj;
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            this.f695d.dispose();
            this.f695d = DisposableHelper.DISPOSED;
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.f695d.isDisposed();
        }

        @Override // io.reactivex.MaybeObserver
        public void onComplete() {
            this.f695d = DisposableHelper.DISPOSED;
            this.actual.onSuccess(false);
        }

        @Override // io.reactivex.MaybeObserver
        public void onError(Throwable th) {
            this.f695d = DisposableHelper.DISPOSED;
            this.actual.onError(th);
        }

        @Override // io.reactivex.MaybeObserver
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.f695d, disposable)) {
                this.f695d = disposable;
                this.actual.onSubscribe(this);
            }
        }

        @Override // io.reactivex.MaybeObserver
        public void onSuccess(Object obj) {
            this.f695d = DisposableHelper.DISPOSED;
            this.actual.onSuccess(Boolean.valueOf(ObjectHelper.equals(obj, this.value)));
        }
    }

    public MaybeContains(MaybeSource<T> maybeSource, Object obj) {
        this.source = maybeSource;
        this.value = obj;
    }

    @Override // io.reactivex.internal.fuseable.HasUpstreamMaybeSource
    public MaybeSource<T> source() {
        return this.source;
    }

    /* access modifiers changed from: protected */
    @Override // io.reactivex.Single
    public void subscribeActual(SingleObserver<? super Boolean> singleObserver) {
        this.source.subscribe(new ContainsMaybeObserver(singleObserver, this.value));
    }
}
