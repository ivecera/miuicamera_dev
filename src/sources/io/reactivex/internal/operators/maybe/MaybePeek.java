package io.reactivex.internal.operators.maybe;

import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.plugins.RxJavaPlugins;

public final class MaybePeek<T> extends AbstractMaybeWithUpstream<T, T> {
    final Action onAfterTerminate;
    final Action onCompleteCall;
    final Action onDisposeCall;
    final Consumer<? super Throwable> onErrorCall;
    final Consumer<? super Disposable> onSubscribeCall;
    final Consumer<? super T> onSuccessCall;

    static final class MaybePeekObserver<T> implements MaybeObserver<T>, Disposable {
        final MaybeObserver<? super T> actual;

        /* renamed from: d  reason: collision with root package name */
        Disposable f719d;
        final MaybePeek<T> parent;

        MaybePeekObserver(MaybeObserver<? super T> maybeObserver, MaybePeek<T> maybePeek) {
            this.actual = maybeObserver;
            this.parent = maybePeek;
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            try {
                this.parent.onDisposeCall.run();
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                RxJavaPlugins.onError(th);
            }
            this.f719d.dispose();
            this.f719d = DisposableHelper.DISPOSED;
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.f719d.isDisposed();
        }

        /* access modifiers changed from: package-private */
        public void onAfterTerminate() {
            try {
                this.parent.onAfterTerminate.run();
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                RxJavaPlugins.onError(th);
            }
        }

        @Override // io.reactivex.MaybeObserver
        public void onComplete() {
            if (this.f719d != DisposableHelper.DISPOSED) {
                try {
                    this.parent.onCompleteCall.run();
                    this.f719d = DisposableHelper.DISPOSED;
                    this.actual.onComplete();
                    onAfterTerminate();
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    onErrorInner(th);
                }
            }
        }

        @Override // io.reactivex.MaybeObserver
        public void onError(Throwable th) {
            if (this.f719d == DisposableHelper.DISPOSED) {
                RxJavaPlugins.onError(th);
            } else {
                onErrorInner(th);
            }
        }

        /* access modifiers changed from: package-private */
        public void onErrorInner(Throwable th) {
            try {
                this.parent.onErrorCall.accept(th);
            } catch (Throwable th2) {
                Exceptions.throwIfFatal(th2);
                th = new CompositeException(th, th2);
            }
            this.f719d = DisposableHelper.DISPOSED;
            this.actual.onError(th);
            onAfterTerminate();
        }

        @Override // io.reactivex.MaybeObserver
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.f719d, disposable)) {
                try {
                    this.parent.onSubscribeCall.accept(disposable);
                    this.f719d = disposable;
                    this.actual.onSubscribe(this);
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    disposable.dispose();
                    this.f719d = DisposableHelper.DISPOSED;
                    EmptyDisposable.error(th, this.actual);
                }
            }
        }

        @Override // io.reactivex.MaybeObserver
        public void onSuccess(T t) {
            if (this.f719d != DisposableHelper.DISPOSED) {
                try {
                    this.parent.onSuccessCall.accept(t);
                    this.f719d = DisposableHelper.DISPOSED;
                    this.actual.onSuccess(t);
                    onAfterTerminate();
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    onErrorInner(th);
                }
            }
        }
    }

    public MaybePeek(MaybeSource<T> maybeSource, Consumer<? super Disposable> consumer, Consumer<? super T> consumer2, Consumer<? super Throwable> consumer3, Action action, Action action2, Action action3) {
        super(maybeSource);
        this.onSubscribeCall = consumer;
        this.onSuccessCall = consumer2;
        this.onErrorCall = consumer3;
        this.onCompleteCall = action;
        this.onAfterTerminate = action2;
        this.onDisposeCall = action3;
    }

    /* access modifiers changed from: protected */
    @Override // io.reactivex.Maybe
    public void subscribeActual(MaybeObserver<? super T> maybeObserver) {
        ((AbstractMaybeWithUpstream) this).source.subscribe(new MaybePeekObserver(maybeObserver, this));
    }
}
