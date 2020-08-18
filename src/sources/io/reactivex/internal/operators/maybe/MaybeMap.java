package io.reactivex.internal.operators.maybe;

import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;

public final class MaybeMap<T, R> extends AbstractMaybeWithUpstream<T, R> {
    final Function<? super T, ? extends R> mapper;

    static final class MapMaybeObserver<T, R> implements MaybeObserver<T>, Disposable {
        final MaybeObserver<? super R> actual;

        /* renamed from: d  reason: collision with root package name */
        Disposable f715d;
        final Function<? super T, ? extends R> mapper;

        MapMaybeObserver(MaybeObserver<? super R> maybeObserver, Function<? super T, ? extends R> function) {
            this.actual = maybeObserver;
            this.mapper = function;
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            Disposable disposable = this.f715d;
            this.f715d = DisposableHelper.DISPOSED;
            disposable.dispose();
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.f715d.isDisposed();
        }

        @Override // io.reactivex.MaybeObserver
        public void onComplete() {
            this.actual.onComplete();
        }

        @Override // io.reactivex.MaybeObserver
        public void onError(Throwable th) {
            this.actual.onError(th);
        }

        @Override // io.reactivex.MaybeObserver
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.f715d, disposable)) {
                this.f715d = disposable;
                this.actual.onSubscribe(this);
            }
        }

        @Override // io.reactivex.MaybeObserver
        public void onSuccess(T t) {
            try {
                Object apply = this.mapper.apply(t);
                ObjectHelper.requireNonNull(apply, "The mapper returned a null item");
                this.actual.onSuccess(apply);
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.actual.onError(th);
            }
        }
    }

    public MaybeMap(MaybeSource<T> maybeSource, Function<? super T, ? extends R> function) {
        super(maybeSource);
        this.mapper = function;
    }

    /* access modifiers changed from: protected */
    @Override // io.reactivex.Maybe
    public void subscribeActual(MaybeObserver<? super R> maybeObserver) {
        ((AbstractMaybeWithUpstream) this).source.subscribe(new MapMaybeObserver(maybeObserver, this.mapper));
    }
}
