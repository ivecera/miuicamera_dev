package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.annotations.Nullable;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.observers.BasicFuseableObserver;

public final class ObservableMap<T, U> extends AbstractObservableWithUpstream<T, U> {
    final Function<? super T, ? extends U> function;

    static final class MapObserver<T, U> extends BasicFuseableObserver<T, U> {
        final Function<? super T, ? extends U> mapper;

        MapObserver(Observer<? super U> observer, Function<? super T, ? extends U> function) {
            super(observer);
            this.mapper = function;
        }

        @Override // io.reactivex.Observer
        public void onNext(T t) {
            if (!((BasicFuseableObserver) this).done) {
                if (((BasicFuseableObserver) this).sourceMode != 0) {
                    ((BasicFuseableObserver) this).actual.onNext(null);
                    return;
                }
                try {
                    Object apply = this.mapper.apply(t);
                    ObjectHelper.requireNonNull(apply, "The mapper function returned a null value.");
                    ((BasicFuseableObserver) this).actual.onNext(apply);
                } catch (Throwable th) {
                    fail(th);
                }
            }
        }

        @Override // io.reactivex.internal.fuseable.SimpleQueue
        @Nullable
        public U poll() throws Exception {
            T poll = ((BasicFuseableObserver) this).qs.poll();
            if (poll == null) {
                return null;
            }
            U apply = this.mapper.apply(poll);
            ObjectHelper.requireNonNull(apply, "The mapper function returned a null value.");
            return apply;
        }

        @Override // io.reactivex.internal.fuseable.QueueFuseable
        public int requestFusion(int i) {
            return transitiveBoundaryFusion(i);
        }
    }

    public ObservableMap(ObservableSource<T> observableSource, Function<? super T, ? extends U> function2) {
        super(observableSource);
        this.function = function2;
    }

    @Override // io.reactivex.Observable
    public void subscribeActual(Observer<? super U> observer) {
        ((AbstractObservableWithUpstream) this).source.subscribe(new MapObserver(observer, this.function));
    }
}
