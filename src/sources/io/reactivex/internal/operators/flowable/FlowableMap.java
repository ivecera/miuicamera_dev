package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.annotations.Nullable;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.ConditionalSubscriber;
import io.reactivex.internal.subscribers.BasicFuseableConditionalSubscriber;
import io.reactivex.internal.subscribers.BasicFuseableSubscriber;
import org.reactivestreams.Subscriber;

public final class FlowableMap<T, U> extends AbstractFlowableWithUpstream<T, U> {
    final Function<? super T, ? extends U> mapper;

    static final class MapConditionalSubscriber<T, U> extends BasicFuseableConditionalSubscriber<T, U> {
        final Function<? super T, ? extends U> mapper;

        MapConditionalSubscriber(ConditionalSubscriber<? super U> conditionalSubscriber, Function<? super T, ? extends U> function) {
            super(conditionalSubscriber);
            this.mapper = function;
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (!((BasicFuseableConditionalSubscriber) this).done) {
                if (((BasicFuseableConditionalSubscriber) this).sourceMode != 0) {
                    ((BasicFuseableConditionalSubscriber) this).actual.onNext(null);
                    return;
                }
                try {
                    Object apply = this.mapper.apply(t);
                    ObjectHelper.requireNonNull(apply, "The mapper function returned a null value.");
                    ((BasicFuseableConditionalSubscriber) this).actual.onNext(apply);
                } catch (Throwable th) {
                    fail(th);
                }
            }
        }

        @Override // io.reactivex.internal.fuseable.SimpleQueue
        @Nullable
        public U poll() throws Exception {
            T poll = ((BasicFuseableConditionalSubscriber) this).qs.poll();
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

        @Override // io.reactivex.internal.fuseable.ConditionalSubscriber
        public boolean tryOnNext(T t) {
            if (((BasicFuseableConditionalSubscriber) this).done) {
                return false;
            }
            try {
                Object apply = this.mapper.apply(t);
                ObjectHelper.requireNonNull(apply, "The mapper function returned a null value.");
                return ((BasicFuseableConditionalSubscriber) this).actual.tryOnNext(apply);
            } catch (Throwable th) {
                fail(th);
                return true;
            }
        }
    }

    static final class MapSubscriber<T, U> extends BasicFuseableSubscriber<T, U> {
        final Function<? super T, ? extends U> mapper;

        MapSubscriber(Subscriber<? super U> subscriber, Function<? super T, ? extends U> function) {
            super(subscriber);
            this.mapper = function;
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (!((BasicFuseableSubscriber) this).done) {
                if (((BasicFuseableSubscriber) this).sourceMode != 0) {
                    ((BasicFuseableSubscriber) this).actual.onNext(null);
                    return;
                }
                try {
                    Object apply = this.mapper.apply(t);
                    ObjectHelper.requireNonNull(apply, "The mapper function returned a null value.");
                    ((BasicFuseableSubscriber) this).actual.onNext(apply);
                } catch (Throwable th) {
                    fail(th);
                }
            }
        }

        @Override // io.reactivex.internal.fuseable.SimpleQueue
        @Nullable
        public U poll() throws Exception {
            T poll = ((BasicFuseableSubscriber) this).qs.poll();
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

    public FlowableMap(Flowable<T> flowable, Function<? super T, ? extends U> function) {
        super(flowable);
        this.mapper = function;
    }

    /* access modifiers changed from: protected */
    @Override // io.reactivex.Flowable
    public void subscribeActual(Subscriber<? super U> subscriber) {
        if (subscriber instanceof ConditionalSubscriber) {
            ((AbstractFlowableWithUpstream) this).source.subscribe((FlowableSubscriber) new MapConditionalSubscriber((ConditionalSubscriber) subscriber, this.mapper));
        } else {
            ((AbstractFlowableWithUpstream) this).source.subscribe((FlowableSubscriber) new MapSubscriber(subscriber, this.mapper));
        }
    }
}
