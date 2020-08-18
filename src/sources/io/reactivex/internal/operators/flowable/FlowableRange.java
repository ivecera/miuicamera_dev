package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.annotations.Nullable;
import io.reactivex.internal.fuseable.ConditionalSubscriber;
import io.reactivex.internal.subscriptions.BasicQueueSubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import org.reactivestreams.Subscriber;

public final class FlowableRange extends Flowable<Integer> {
    final int end;
    final int start;

    static abstract class BaseRangeSubscription extends BasicQueueSubscription<Integer> {
        private static final long serialVersionUID = -2252972430506210021L;
        volatile boolean cancelled;
        final int end;
        int index;

        BaseRangeSubscription(int i, int i2) {
            this.index = i;
            this.end = i2;
        }

        @Override // org.reactivestreams.Subscription
        public final void cancel() {
            this.cancelled = true;
        }

        @Override // io.reactivex.internal.fuseable.SimpleQueue
        public final void clear() {
            this.index = this.end;
        }

        /* access modifiers changed from: package-private */
        public abstract void fastPath();

        @Override // io.reactivex.internal.fuseable.SimpleQueue
        public final boolean isEmpty() {
            return this.index == this.end;
        }

        @Override // io.reactivex.internal.fuseable.SimpleQueue
        @Nullable
        public final Integer poll() {
            int i = this.index;
            if (i == this.end) {
                return null;
            }
            this.index = i + 1;
            return Integer.valueOf(i);
        }

        @Override // org.reactivestreams.Subscription
        public final void request(long j) {
            if (SubscriptionHelper.validate(j) && BackpressureHelper.add(this, j) == 0) {
                if (j == Long.MAX_VALUE) {
                    fastPath();
                } else {
                    slowPath(j);
                }
            }
        }

        @Override // io.reactivex.internal.fuseable.QueueFuseable
        public final int requestFusion(int i) {
            return i & 1;
        }

        /* access modifiers changed from: package-private */
        public abstract void slowPath(long j);
    }

    static final class RangeConditionalSubscription extends BaseRangeSubscription {
        private static final long serialVersionUID = 2587302975077663557L;
        final ConditionalSubscriber<? super Integer> actual;

        RangeConditionalSubscription(ConditionalSubscriber<? super Integer> conditionalSubscriber, int i, int i2) {
            super(i, i2);
            this.actual = conditionalSubscriber;
        }

        /* access modifiers changed from: package-private */
        @Override // io.reactivex.internal.operators.flowable.FlowableRange.BaseRangeSubscription
        public void fastPath() {
            int i = ((BaseRangeSubscription) this).end;
            ConditionalSubscriber<? super Integer> conditionalSubscriber = this.actual;
            int i2 = ((BaseRangeSubscription) this).index;
            while (i2 != i) {
                if (!((BaseRangeSubscription) this).cancelled) {
                    conditionalSubscriber.tryOnNext(Integer.valueOf(i2));
                    i2++;
                } else {
                    return;
                }
            }
            if (!((BaseRangeSubscription) this).cancelled) {
                conditionalSubscriber.onComplete();
            }
        }

        /* access modifiers changed from: package-private */
        @Override // io.reactivex.internal.operators.flowable.FlowableRange.BaseRangeSubscription
        public void slowPath(long j) {
            int i = ((BaseRangeSubscription) this).end;
            int i2 = ((BaseRangeSubscription) this).index;
            ConditionalSubscriber<? super Integer> conditionalSubscriber = this.actual;
            long j2 = j;
            do {
                long j3 = 0;
                while (true) {
                    if (j3 == j2 || i2 == i) {
                        if (i2 != i) {
                            j2 = get();
                            if (j3 == j2) {
                                ((BaseRangeSubscription) this).index = i2;
                                j2 = addAndGet(-j3);
                            }
                        } else if (!((BaseRangeSubscription) this).cancelled) {
                            conditionalSubscriber.onComplete();
                            return;
                        } else {
                            return;
                        }
                    } else if (!((BaseRangeSubscription) this).cancelled) {
                        if (conditionalSubscriber.tryOnNext(Integer.valueOf(i2))) {
                            j3++;
                        }
                        i2++;
                    } else {
                        return;
                    }
                }
            } while (j2 != 0);
        }
    }

    static final class RangeSubscription extends BaseRangeSubscription {
        private static final long serialVersionUID = 2587302975077663557L;
        final Subscriber<? super Integer> actual;

        RangeSubscription(Subscriber<? super Integer> subscriber, int i, int i2) {
            super(i, i2);
            this.actual = subscriber;
        }

        /* access modifiers changed from: package-private */
        @Override // io.reactivex.internal.operators.flowable.FlowableRange.BaseRangeSubscription
        public void fastPath() {
            int i = ((BaseRangeSubscription) this).end;
            Subscriber<? super Integer> subscriber = this.actual;
            int i2 = ((BaseRangeSubscription) this).index;
            while (i2 != i) {
                if (!((BaseRangeSubscription) this).cancelled) {
                    subscriber.onNext(Integer.valueOf(i2));
                    i2++;
                } else {
                    return;
                }
            }
            if (!((BaseRangeSubscription) this).cancelled) {
                subscriber.onComplete();
            }
        }

        /* access modifiers changed from: package-private */
        @Override // io.reactivex.internal.operators.flowable.FlowableRange.BaseRangeSubscription
        public void slowPath(long j) {
            int i = ((BaseRangeSubscription) this).end;
            int i2 = ((BaseRangeSubscription) this).index;
            Subscriber<? super Integer> subscriber = this.actual;
            long j2 = j;
            do {
                long j3 = 0;
                while (true) {
                    if (j3 == j2 || i2 == i) {
                        if (i2 != i) {
                            j2 = get();
                            if (j3 == j2) {
                                ((BaseRangeSubscription) this).index = i2;
                                j2 = addAndGet(-j3);
                            }
                        } else if (!((BaseRangeSubscription) this).cancelled) {
                            subscriber.onComplete();
                            return;
                        } else {
                            return;
                        }
                    } else if (!((BaseRangeSubscription) this).cancelled) {
                        subscriber.onNext(Integer.valueOf(i2));
                        j3++;
                        i2++;
                    } else {
                        return;
                    }
                }
            } while (j2 != 0);
        }
    }

    public FlowableRange(int i, int i2) {
        this.start = i;
        this.end = i + i2;
    }

    @Override // io.reactivex.Flowable
    public void subscribeActual(Subscriber<? super Integer> subscriber) {
        if (subscriber instanceof ConditionalSubscriber) {
            subscriber.onSubscribe(new RangeConditionalSubscription((ConditionalSubscriber) subscriber, this.start, this.end));
        } else {
            subscriber.onSubscribe(new RangeSubscription(subscriber, this.start, this.end));
        }
    }
}
