package io.reactivex.internal.operators.maybe;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Maybe;
import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.fuseable.FuseToMaybe;
import io.reactivex.plugins.RxJavaPlugins;

public final class MaybeIgnoreElementCompletable<T> extends Completable implements FuseToMaybe<T> {
    final MaybeSource<T> source;

    static final class IgnoreMaybeObserver<T> implements MaybeObserver<T>, Disposable {
        final CompletableObserver actual;

        /* renamed from: d  reason: collision with root package name */
        Disposable f712d;

        IgnoreMaybeObserver(CompletableObserver completableObserver) {
            this.actual = completableObserver;
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            this.f712d.dispose();
            this.f712d = DisposableHelper.DISPOSED;
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.f712d.isDisposed();
        }

        @Override // io.reactivex.MaybeObserver
        public void onComplete() {
            this.f712d = DisposableHelper.DISPOSED;
            this.actual.onComplete();
        }

        @Override // io.reactivex.MaybeObserver
        public void onError(Throwable th) {
            this.f712d = DisposableHelper.DISPOSED;
            this.actual.onError(th);
        }

        @Override // io.reactivex.MaybeObserver
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.f712d, disposable)) {
                this.f712d = disposable;
                this.actual.onSubscribe(this);
            }
        }

        @Override // io.reactivex.MaybeObserver
        public void onSuccess(T t) {
            this.f712d = DisposableHelper.DISPOSED;
            this.actual.onComplete();
        }
    }

    public MaybeIgnoreElementCompletable(MaybeSource<T> maybeSource) {
        this.source = maybeSource;
    }

    @Override // io.reactivex.internal.fuseable.FuseToMaybe
    public Maybe<T> fuseToMaybe() {
        return RxJavaPlugins.onAssembly(new MaybeIgnoreElement(this.source));
    }

    /* access modifiers changed from: protected */
    @Override // io.reactivex.Completable
    public void subscribeActual(CompletableObserver completableObserver) {
        this.source.subscribe(new IgnoreMaybeObserver(completableObserver));
    }
}
