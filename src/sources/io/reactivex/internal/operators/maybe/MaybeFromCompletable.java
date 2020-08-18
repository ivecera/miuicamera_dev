package io.reactivex.internal.operators.maybe;

import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.Maybe;
import io.reactivex.MaybeObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.fuseable.HasUpstreamCompletableSource;

public final class MaybeFromCompletable<T> extends Maybe<T> implements HasUpstreamCompletableSource {
    final CompletableSource source;

    static final class FromCompletableObserver<T> implements CompletableObserver, Disposable {
        final MaybeObserver<? super T> actual;

        /* renamed from: d  reason: collision with root package name */
        Disposable f708d;

        FromCompletableObserver(MaybeObserver<? super T> maybeObserver) {
            this.actual = maybeObserver;
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            this.f708d.dispose();
            this.f708d = DisposableHelper.DISPOSED;
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.f708d.isDisposed();
        }

        @Override // io.reactivex.CompletableObserver
        public void onComplete() {
            this.f708d = DisposableHelper.DISPOSED;
            this.actual.onComplete();
        }

        @Override // io.reactivex.CompletableObserver
        public void onError(Throwable th) {
            this.f708d = DisposableHelper.DISPOSED;
            this.actual.onError(th);
        }

        @Override // io.reactivex.CompletableObserver
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.f708d, disposable)) {
                this.f708d = disposable;
                this.actual.onSubscribe(this);
            }
        }
    }

    public MaybeFromCompletable(CompletableSource completableSource) {
        this.source = completableSource;
    }

    @Override // io.reactivex.internal.fuseable.HasUpstreamCompletableSource
    public CompletableSource source() {
        return this.source;
    }

    /* access modifiers changed from: protected */
    @Override // io.reactivex.Maybe
    public void subscribeActual(MaybeObserver<? super T> maybeObserver) {
        this.source.subscribe(new FromCompletableObserver(maybeObserver));
    }
}
