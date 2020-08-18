package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.annotations.Nullable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.observers.BasicFuseableObserver;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.Collection;
import java.util.concurrent.Callable;

public final class ObservableDistinct<T, K> extends AbstractObservableWithUpstream<T, T> {
    final Callable<? extends Collection<? super K>> collectionSupplier;
    final Function<? super T, K> keySelector;

    static final class DistinctObserver<T, K> extends BasicFuseableObserver<T, T> {
        final Collection<? super K> collection;
        final Function<? super T, K> keySelector;

        DistinctObserver(Observer<? super T> observer, Function<? super T, K> function, Collection<? super K> collection2) {
            super(observer);
            this.keySelector = function;
            this.collection = collection2;
        }

        @Override // io.reactivex.internal.fuseable.SimpleQueue, io.reactivex.internal.observers.BasicFuseableObserver
        public void clear() {
            this.collection.clear();
            super.clear();
        }

        @Override // io.reactivex.Observer, io.reactivex.internal.observers.BasicFuseableObserver
        public void onComplete() {
            if (!((BasicFuseableObserver) this).done) {
                ((BasicFuseableObserver) this).done = true;
                this.collection.clear();
                ((BasicFuseableObserver) this).actual.onComplete();
            }
        }

        @Override // io.reactivex.Observer, io.reactivex.internal.observers.BasicFuseableObserver
        public void onError(Throwable th) {
            if (((BasicFuseableObserver) this).done) {
                RxJavaPlugins.onError(th);
                return;
            }
            ((BasicFuseableObserver) this).done = true;
            this.collection.clear();
            ((BasicFuseableObserver) this).actual.onError(th);
        }

        @Override // io.reactivex.Observer
        public void onNext(T t) {
            if (!((BasicFuseableObserver) this).done) {
                if (((BasicFuseableObserver) this).sourceMode == 0) {
                    try {
                        K apply = this.keySelector.apply(t);
                        ObjectHelper.requireNonNull(apply, "The keySelector returned a null key");
                        if (this.collection.add(apply)) {
                            ((BasicFuseableObserver) this).actual.onNext(t);
                        }
                    } catch (Throwable th) {
                        fail(th);
                    }
                } else {
                    ((BasicFuseableObserver) this).actual.onNext(null);
                }
            }
        }

        @Override // io.reactivex.internal.fuseable.SimpleQueue
        @Nullable
        public T poll() throws Exception {
            T poll;
            Collection<? super K> collection2;
            K apply;
            do {
                poll = ((BasicFuseableObserver) this).qs.poll();
                if (poll == null) {
                    break;
                }
                collection2 = this.collection;
                apply = this.keySelector.apply(poll);
                ObjectHelper.requireNonNull(apply, "The keySelector returned a null key");
            } while (!collection2.add(apply));
            return poll;
        }

        @Override // io.reactivex.internal.fuseable.QueueFuseable
        public int requestFusion(int i) {
            return transitiveBoundaryFusion(i);
        }
    }

    public ObservableDistinct(ObservableSource<T> observableSource, Function<? super T, K> function, Callable<? extends Collection<? super K>> callable) {
        super(observableSource);
        this.keySelector = function;
        this.collectionSupplier = callable;
    }

    /* access modifiers changed from: protected */
    @Override // io.reactivex.Observable
    public void subscribeActual(Observer<? super T> observer) {
        try {
            Object call = this.collectionSupplier.call();
            ObjectHelper.requireNonNull(call, "The collectionSupplier returned a null collection. Null values are generally not allowed in 2.x operators and sources.");
            ((AbstractObservableWithUpstream) this).source.subscribe(new DistinctObserver(observer, this.keySelector, (Collection) call));
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            EmptyDisposable.error(th, observer);
        }
    }
}
