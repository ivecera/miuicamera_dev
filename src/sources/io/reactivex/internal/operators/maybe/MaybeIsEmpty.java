package io.reactivex.internal.operators.maybe;

import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;

public final class MaybeIsEmpty<T> extends AbstractMaybeWithUpstream<T, Boolean> {

    static final class IsEmptyMaybeObserver<T> implements MaybeObserver<T>, Disposable {
        final MaybeObserver<? super Boolean> actual;

        /* renamed from: d  reason: collision with root package name */
        Disposable f713d;

        IsEmptyMaybeObserver(MaybeObserver<? super Boolean> maybeObserver) {
            this.actual = maybeObserver;
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            this.f713d.dispose();
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.f713d.isDisposed();
        }

        @Override // io.reactivex.MaybeObserver
        public void onComplete() {
            this.actual.onSuccess(true);
        }

        @Override // io.reactivex.MaybeObserver
        public void onError(Throwable th) {
            this.actual.onError(th);
        }

        @Override // io.reactivex.MaybeObserver
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.f713d, disposable)) {
                this.f713d = disposable;
                this.actual.onSubscribe(this);
            }
        }

        @Override // io.reactivex.MaybeObserver
        public void onSuccess(T t) {
            this.actual.onSuccess(false);
        }
    }

    public MaybeIsEmpty(MaybeSource<T> maybeSource) {
        super(maybeSource);
    }

    /* access modifiers changed from: protected */
    @Override // io.reactivex.Maybe
    public void subscribeActual(MaybeObserver<? super Boolean> maybeObserver) {
        ((AbstractMaybeWithUpstream) this).source.subscribe(new IsEmptyMaybeObserver(maybeObserver));
    }
}
