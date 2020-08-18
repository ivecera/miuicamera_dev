package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.annotations.Nullable;
import io.reactivex.functions.Predicate;
import io.reactivex.internal.observers.BasicFuseableObserver;

public final class ObservableFilter<T> extends AbstractObservableWithUpstream<T, T> {
    final Predicate<? super T> predicate;

    static final class FilterObserver<T> extends BasicFuseableObserver<T, T> {
        final Predicate<? super T> filter;

        FilterObserver(Observer<? super T> observer, Predicate<? super T> predicate) {
            super(observer);
            this.filter = predicate;
        }

        @Override // io.reactivex.Observer
        public void onNext(T t) {
            if (((BasicFuseableObserver) this).sourceMode == 0) {
                try {
                    if (this.filter.test(t)) {
                        ((BasicFuseableObserver) this).actual.onNext(t);
                    }
                } catch (Throwable th) {
                    fail(th);
                }
            } else {
                ((BasicFuseableObserver) this).actual.onNext(null);
            }
        }

        @Override // io.reactivex.internal.fuseable.SimpleQueue
        @Nullable
        public T poll() throws Exception {
            T poll;
            do {
                poll = ((BasicFuseableObserver) this).qs.poll();
                if (poll == null) {
                    break;
                }
            } while (!this.filter.test(poll));
            return poll;
        }

        @Override // io.reactivex.internal.fuseable.QueueFuseable
        public int requestFusion(int i) {
            return transitiveBoundaryFusion(i);
        }
    }

    public ObservableFilter(ObservableSource<T> observableSource, Predicate<? super T> predicate2) {
        super(observableSource);
        this.predicate = predicate2;
    }

    @Override // io.reactivex.Observable
    public void subscribeActual(Observer<? super T> observer) {
        ((AbstractObservableWithUpstream) this).source.subscribe(new FilterObserver(observer, this.predicate));
    }
}
