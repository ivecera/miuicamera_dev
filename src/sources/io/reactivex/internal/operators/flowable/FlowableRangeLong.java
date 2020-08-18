package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.annotations.Nullable;
import io.reactivex.internal.fuseable.ConditionalSubscriber;
import io.reactivex.internal.subscriptions.BasicQueueSubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import org.reactivestreams.Subscriber;

public final class FlowableRangeLong extends Flowable<Long> {
    final long end;
    final long start;

    static abstract class BaseRangeSubscription extends BasicQueueSubscription<Long> {
        private static final long serialVersionUID = -2252972430506210021L;
        volatile boolean cancelled;
        final long end;
        long index;

        BaseRangeSubscription(long j, long j2) {
            this.index = j;
            this.end = j2;
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
        public final Long poll() {
            long j = this.index;
            if (j == this.end) {
                return null;
            }
            this.index = 1 + j;
            return Long.valueOf(j);
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
        final ConditionalSubscriber<? super Long> actual;

        RangeConditionalSubscription(ConditionalSubscriber<? super Long> conditionalSubscriber, long j, long j2) {
            super(j, j2);
            this.actual = conditionalSubscriber;
        }

        /* access modifiers changed from: package-private */
        @Override // io.reactivex.internal.operators.flowable.FlowableRangeLong.BaseRangeSubscription
        public void fastPath() {
            long j = ((BaseRangeSubscription) this).end;
            ConditionalSubscriber<? super Long> conditionalSubscriber = this.actual;
            long j2 = ((BaseRangeSubscription) this).index;
            while (j2 != j) {
                if (!((BaseRangeSubscription) this).cancelled) {
                    conditionalSubscriber.tryOnNext(Long.valueOf(j2));
                    j2++;
                } else {
                    return;
                }
            }
            if (!((BaseRangeSubscription) this).cancelled) {
                conditionalSubscriber.onComplete();
            }
        }

        /* access modifiers changed from: package-private */
        @Override // io.reactivex.internal.operators.flowable.FlowableRangeLong.BaseRangeSubscription
        public void slowPath(long j) {
            long j2 = ((BaseRangeSubscription) this).end;
            long j3 = ((BaseRangeSubscription) this).index;
            ConditionalSubscriber<? super Long> conditionalSubscriber = this.actual;
            long j4 = j3;
            long j5 = j;
            do {
                long j6 = 0;
                while (true) {
                    if (j6 == j5 || j4 == j2) {
                        if (j4 != j2) {
                            j5 = get();
                            if (j6 == j5) {
                                ((BaseRangeSubscription) this).index = j4;
                                j5 = addAndGet(-j6);
                            }
                        } else if (!((BaseRangeSubscription) this).cancelled) {
                            conditionalSubscriber.onComplete();
                            return;
                        } else {
                            return;
                        }
                    } else if (!((BaseRangeSubscription) this).cancelled) {
                        if (conditionalSubscriber.tryOnNext(Long.valueOf(j4))) {
                            j6++;
                        }
                        j4++;
                    } else {
                        return;
                    }
                }
            } while (j5 != 0);
        }
    }

    static final class RangeSubscription extends BaseRangeSubscription {
        private static final long serialVersionUID = 2587302975077663557L;
        final Subscriber<? super Long> actual;

        RangeSubscription(Subscriber<? super Long> subscriber, long j, long j2) {
            super(j, j2);
            this.actual = subscriber;
        }

        /* access modifiers changed from: package-private */
        @Override // io.reactivex.internal.operators.flowable.FlowableRangeLong.BaseRangeSubscription
        public void fastPath() {
            long j = ((BaseRangeSubscription) this).end;
            Subscriber<? super Long> subscriber = this.actual;
            long j2 = ((BaseRangeSubscription) this).index;
            while (j2 != j) {
                if (!((BaseRangeSubscription) this).cancelled) {
                    subscriber.onNext(Long.valueOf(j2));
                    j2++;
                } else {
                    return;
                }
            }
            if (!((BaseRangeSubscription) this).cancelled) {
                subscriber.onComplete();
            }
        }

        /* access modifiers changed from: package-private */
        @Override // io.reactivex.internal.operators.flowable.FlowableRangeLong.BaseRangeSubscription
        public void slowPath(long j) {
            long j2 = ((BaseRangeSubscription) this).end;
            long j3 = ((BaseRangeSubscription) this).index;
            Subscriber<? super Long> subscriber = this.actual;
            long j4 = j3;
            long j5 = j;
            do {
                long j6 = 0;
                while (true) {
                    if (j6 == j5 || j4 == j2) {
                        if (j4 != j2) {
                            j5 = get();
                            if (j6 == j5) {
                                ((BaseRangeSubscription) this).index = j4;
                                j5 = addAndGet(-j6);
                            }
                        } else if (!((BaseRangeSubscription) this).cancelled) {
                            subscriber.onComplete();
                            return;
                        } else {
                            return;
                        }
                    } else if (!((BaseRangeSubscription) this).cancelled) {
                        subscriber.onNext(Long.valueOf(j4));
                        j6++;
                        j4++;
                    } else {
                        return;
                    }
                }
            } while (j5 != 0);
        }
    }

    public FlowableRangeLong(long j, long j2) {
        this.start = j;
        this.end = j + j2;
    }

    @Override // io.reactivex.Flowable
    public void subscribeActual(Subscriber<? super Long> subscriber) {
        if (subscriber instanceof ConditionalSubscriber) {
            subscriber.onSubscribe(new RangeConditionalSubscription((ConditionalSubscriber) subscriber, this.start, this.end));
        } else {
            subscriber.onSubscribe(new RangeSubscription(subscriber, this.start, this.end));
        }
    }
}
