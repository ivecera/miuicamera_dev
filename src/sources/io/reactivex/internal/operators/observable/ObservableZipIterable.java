package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.BiFunction;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.Iterator;

public final class ObservableZipIterable<T, U, V> extends Observable<V> {
    final Iterable<U> other;
    final Observable<? extends T> source;
    final BiFunction<? super T, ? super U, ? extends V> zipper;

    static final class ZipIterableObserver<T, U, V> implements Observer<T>, Disposable {
        final Observer<? super V> actual;
        boolean done;
        final Iterator<U> iterator;
        Disposable s;
        final BiFunction<? super T, ? super U, ? extends V> zipper;

        ZipIterableObserver(Observer<? super V> observer, Iterator<U> it, BiFunction<? super T, ? super U, ? extends V> biFunction) {
            this.actual = observer;
            this.iterator = it;
            this.zipper = biFunction;
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            this.s.dispose();
        }

        /* access modifiers changed from: package-private */
        public void error(Throwable th) {
            this.done = true;
            this.s.dispose();
            this.actual.onError(th);
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.s.isDisposed();
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            if (!this.done) {
                this.done = true;
                this.actual.onComplete();
            }
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            if (this.done) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.done = true;
            this.actual.onError(th);
        }

        @Override // io.reactivex.Observer
        public void onNext(T t) {
            if (!this.done) {
                try {
                    U next = this.iterator.next();
                    ObjectHelper.requireNonNull(next, "The iterator returned a null value");
                    try {
                        Object apply = this.zipper.apply(t, next);
                        ObjectHelper.requireNonNull(apply, "The zipper function returned a null value");
                        this.actual.onNext(apply);
                        try {
                            if (!this.iterator.hasNext()) {
                                this.done = true;
                                this.s.dispose();
                                this.actual.onComplete();
                            }
                        } catch (Throwable th) {
                            Exceptions.throwIfFatal(th);
                            error(th);
                        }
                    } catch (Throwable th2) {
                        Exceptions.throwIfFatal(th2);
                        error(th2);
                    }
                } catch (Throwable th3) {
                    Exceptions.throwIfFatal(th3);
                    error(th3);
                }
            }
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.s, disposable)) {
                this.s = disposable;
                this.actual.onSubscribe(this);
            }
        }
    }

    public ObservableZipIterable(Observable<? extends T> observable, Iterable<U> iterable, BiFunction<? super T, ? super U, ? extends V> biFunction) {
        this.source = observable;
        this.other = iterable;
        this.zipper = biFunction;
    }

    @Override // io.reactivex.Observable
    public void subscribeActual(Observer<? super V> observer) {
        try {
            Iterator<U> it = this.other.iterator();
            ObjectHelper.requireNonNull(it, "The iterator returned by other is null");
            Iterator<U> it2 = it;
            try {
                if (!it2.hasNext()) {
                    EmptyDisposable.complete(observer);
                } else {
                    this.source.subscribe(new ZipIterableObserver(observer, it2, this.zipper));
                }
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                EmptyDisposable.error(th, observer);
            }
        } catch (Throwable th2) {
            Exceptions.throwIfFatal(th2);
            EmptyDisposable.error(th2, observer);
        }
    }
}
