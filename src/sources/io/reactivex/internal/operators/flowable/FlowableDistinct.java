package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.annotations.Nullable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.subscribers.BasicFuseableSubscriber;
import io.reactivex.internal.subscriptions.EmptySubscription;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.Collection;
import java.util.concurrent.Callable;
import org.reactivestreams.Subscriber;

public final class FlowableDistinct<T, K> extends AbstractFlowableWithUpstream<T, T> {
    final Callable<? extends Collection<? super K>> collectionSupplier;
    final Function<? super T, K> keySelector;

    static final class DistinctSubscriber<T, K> extends BasicFuseableSubscriber<T, T> {
        final Collection<? super K> collection;
        final Function<? super T, K> keySelector;

        DistinctSubscriber(Subscriber<? super T> subscriber, Function<? super T, K> function, Collection<? super K> collection2) {
            super(subscriber);
            this.keySelector = function;
            this.collection = collection2;
        }

        @Override // io.reactivex.internal.fuseable.SimpleQueue, io.reactivex.internal.subscribers.BasicFuseableSubscriber
        public void clear() {
            this.collection.clear();
            super.clear();
        }

        @Override // io.reactivex.internal.subscribers.BasicFuseableSubscriber, org.reactivestreams.Subscriber
        public void onComplete() {
            if (!((BasicFuseableSubscriber) this).done) {
                ((BasicFuseableSubscriber) this).done = true;
                this.collection.clear();
                ((BasicFuseableSubscriber) this).actual.onComplete();
            }
        }

        @Override // io.reactivex.internal.subscribers.BasicFuseableSubscriber, org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (((BasicFuseableSubscriber) this).done) {
                RxJavaPlugins.onError(th);
                return;
            }
            ((BasicFuseableSubscriber) this).done = true;
            this.collection.clear();
            ((BasicFuseableSubscriber) this).actual.onError(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (!((BasicFuseableSubscriber) this).done) {
                if (((BasicFuseableSubscriber) this).sourceMode == 0) {
                    try {
                        K apply = this.keySelector.apply(t);
                        ObjectHelper.requireNonNull(apply, "The keySelector returned a null key");
                        if (this.collection.add(apply)) {
                            ((BasicFuseableSubscriber) this).actual.onNext(t);
                        } else {
                            ((BasicFuseableSubscriber) this).s.request(1);
                        }
                    } catch (Throwable th) {
                        fail(th);
                    }
                } else {
                    ((BasicFuseableSubscriber) this).actual.onNext(null);
                }
            }
        }

        @Override // io.reactivex.internal.fuseable.SimpleQueue
        @Nullable
        public T poll() throws Exception {
            T poll;
            while (true) {
                poll = ((BasicFuseableSubscriber) this).qs.poll();
                if (poll == null) {
                    break;
                }
                Collection<? super K> collection2 = this.collection;
                K apply = this.keySelector.apply(poll);
                ObjectHelper.requireNonNull(apply, "The keySelector returned a null key");
                if (collection2.add(apply)) {
                    break;
                } else if (((BasicFuseableSubscriber) this).sourceMode == 2) {
                    ((BasicFuseableSubscriber) this).s.request(1);
                }
            }
            return poll;
        }

        @Override // io.reactivex.internal.fuseable.QueueFuseable
        public int requestFusion(int i) {
            return transitiveBoundaryFusion(i);
        }
    }

    public FlowableDistinct(Flowable<T> flowable, Function<? super T, K> function, Callable<? extends Collection<? super K>> callable) {
        super(flowable);
        this.keySelector = function;
        this.collectionSupplier = callable;
    }

    /* access modifiers changed from: protected */
    @Override // io.reactivex.Flowable
    public void subscribeActual(Subscriber<? super T> subscriber) {
        try {
            Object call = this.collectionSupplier.call();
            ObjectHelper.requireNonNull(call, "The collectionSupplier returned a null collection. Null values are generally not allowed in 2.x operators and sources.");
            ((AbstractFlowableWithUpstream) this).source.subscribe((FlowableSubscriber) new DistinctSubscriber(subscriber, this.keySelector, (Collection) call));
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            EmptySubscription.error(th, subscriber);
        }
    }
}
