package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.annotations.Experimental;
import io.reactivex.annotations.Nullable;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.fuseable.ConditionalSubscriber;
import io.reactivex.internal.subscribers.BasicFuseableConditionalSubscriber;
import io.reactivex.internal.subscribers.BasicFuseableSubscriber;
import org.reactivestreams.Subscriber;

@Experimental
public final class FlowableDoAfterNext<T> extends AbstractFlowableWithUpstream<T, T> {
    final Consumer<? super T> onAfterNext;

    static final class DoAfterConditionalSubscriber<T> extends BasicFuseableConditionalSubscriber<T, T> {
        final Consumer<? super T> onAfterNext;

        DoAfterConditionalSubscriber(ConditionalSubscriber<? super T> conditionalSubscriber, Consumer<? super T> consumer) {
            super(conditionalSubscriber);
            this.onAfterNext = consumer;
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            ((BasicFuseableConditionalSubscriber) this).actual.onNext(t);
            if (((BasicFuseableConditionalSubscriber) this).sourceMode == 0) {
                try {
                    this.onAfterNext.accept(t);
                } catch (Throwable th) {
                    fail(th);
                }
            }
        }

        @Override // io.reactivex.internal.fuseable.SimpleQueue
        @Nullable
        public T poll() throws Exception {
            T poll = ((BasicFuseableConditionalSubscriber) this).qs.poll();
            if (poll != null) {
                this.onAfterNext.accept(poll);
            }
            return poll;
        }

        @Override // io.reactivex.internal.fuseable.QueueFuseable
        public int requestFusion(int i) {
            return transitiveBoundaryFusion(i);
        }

        @Override // io.reactivex.internal.fuseable.ConditionalSubscriber
        public boolean tryOnNext(T t) {
            boolean tryOnNext = ((BasicFuseableConditionalSubscriber) this).actual.tryOnNext(t);
            try {
                this.onAfterNext.accept(t);
            } catch (Throwable th) {
                fail(th);
            }
            return tryOnNext;
        }
    }

    static final class DoAfterSubscriber<T> extends BasicFuseableSubscriber<T, T> {
        final Consumer<? super T> onAfterNext;

        DoAfterSubscriber(Subscriber<? super T> subscriber, Consumer<? super T> consumer) {
            super(subscriber);
            this.onAfterNext = consumer;
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (!((BasicFuseableSubscriber) this).done) {
                ((BasicFuseableSubscriber) this).actual.onNext(t);
                if (((BasicFuseableSubscriber) this).sourceMode == 0) {
                    try {
                        this.onAfterNext.accept(t);
                    } catch (Throwable th) {
                        fail(th);
                    }
                }
            }
        }

        @Override // io.reactivex.internal.fuseable.SimpleQueue
        @Nullable
        public T poll() throws Exception {
            T poll = ((BasicFuseableSubscriber) this).qs.poll();
            if (poll != null) {
                this.onAfterNext.accept(poll);
            }
            return poll;
        }

        @Override // io.reactivex.internal.fuseable.QueueFuseable
        public int requestFusion(int i) {
            return transitiveBoundaryFusion(i);
        }
    }

    public FlowableDoAfterNext(Flowable<T> flowable, Consumer<? super T> consumer) {
        super(flowable);
        this.onAfterNext = consumer;
    }

    /* access modifiers changed from: protected */
    @Override // io.reactivex.Flowable
    public void subscribeActual(Subscriber<? super T> subscriber) {
        if (subscriber instanceof ConditionalSubscriber) {
            ((AbstractFlowableWithUpstream) this).source.subscribe((FlowableSubscriber) new DoAfterConditionalSubscriber((ConditionalSubscriber) subscriber, this.onAfterNext));
        } else {
            ((AbstractFlowableWithUpstream) this).source.subscribe((FlowableSubscriber) new DoAfterSubscriber(subscriber, this.onAfterNext));
        }
    }
}
