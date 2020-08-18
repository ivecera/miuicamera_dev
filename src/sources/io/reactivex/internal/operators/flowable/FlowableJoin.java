package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.SimpleQueue;
import io.reactivex.internal.operators.flowable.FlowableGroupJoin;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableJoin<TLeft, TRight, TLeftEnd, TRightEnd, R> extends AbstractFlowableWithUpstream<TLeft, R> {
    final Function<? super TLeft, ? extends Publisher<TLeftEnd>> leftEnd;
    final Publisher<? extends TRight> other;
    final BiFunction<? super TLeft, ? super TRight, ? extends R> resultSelector;
    final Function<? super TRight, ? extends Publisher<TRightEnd>> rightEnd;

    static final class JoinSubscription<TLeft, TRight, TLeftEnd, TRightEnd, R> extends AtomicInteger implements Subscription, FlowableGroupJoin.JoinSupport {
        static final Integer LEFT_CLOSE = 3;
        static final Integer LEFT_VALUE = 1;
        static final Integer RIGHT_CLOSE = 4;
        static final Integer RIGHT_VALUE = 2;
        private static final long serialVersionUID = -6071216598687999801L;
        final AtomicInteger active;
        final Subscriber<? super R> actual;
        volatile boolean cancelled;
        final CompositeDisposable disposables = new CompositeDisposable();
        final AtomicReference<Throwable> error = new AtomicReference<>();
        final Function<? super TLeft, ? extends Publisher<TLeftEnd>> leftEnd;
        int leftIndex;
        final Map<Integer, TLeft> lefts = new LinkedHashMap();
        final SpscLinkedArrayQueue<Object> queue = new SpscLinkedArrayQueue<>(Flowable.bufferSize());
        final AtomicLong requested = new AtomicLong();
        final BiFunction<? super TLeft, ? super TRight, ? extends R> resultSelector;
        final Function<? super TRight, ? extends Publisher<TRightEnd>> rightEnd;
        int rightIndex;
        final Map<Integer, TRight> rights = new LinkedHashMap();

        JoinSubscription(Subscriber<? super R> subscriber, Function<? super TLeft, ? extends Publisher<TLeftEnd>> function, Function<? super TRight, ? extends Publisher<TRightEnd>> function2, BiFunction<? super TLeft, ? super TRight, ? extends R> biFunction) {
            this.actual = subscriber;
            this.leftEnd = function;
            this.rightEnd = function2;
            this.resultSelector = biFunction;
            this.active = new AtomicInteger(2);
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            if (!this.cancelled) {
                this.cancelled = true;
                cancelAll();
                if (getAndIncrement() == 0) {
                    this.queue.clear();
                }
            }
        }

        /* access modifiers changed from: package-private */
        public void cancelAll() {
            this.disposables.dispose();
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v7, resolved type: TRight} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v3, resolved type: java.util.Map<java.lang.Integer, TRight>} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v4, resolved type: io.reactivex.functions.Function<? super TRight, ? extends org.reactivestreams.Publisher<TRightEnd>>} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v0, resolved type: io.reactivex.functions.BiFunction<? super TLeft, ? super TRight, ? extends R>} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v7, resolved type: ? super R} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v8, resolved type: java.util.Map<java.lang.Integer, TLeft>} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v9, resolved type: io.reactivex.functions.Function<? super TLeft, ? extends org.reactivestreams.Publisher<TLeftEnd>>} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v2, resolved type: io.reactivex.functions.BiFunction<? super TLeft, ? super TRight, ? extends R>} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v31, resolved type: ? super R} */
        /* JADX WARN: Multi-variable type inference failed */
        /* access modifiers changed from: package-private */
        public void drain() {
            if (getAndIncrement() == 0) {
                SpscLinkedArrayQueue<Object> spscLinkedArrayQueue = this.queue;
                Subscriber<? super R> subscriber = this.actual;
                boolean z = true;
                int i = 1;
                while (!this.cancelled) {
                    if (this.error.get() != null) {
                        spscLinkedArrayQueue.clear();
                        cancelAll();
                        errorAll(subscriber);
                        return;
                    }
                    boolean z2 = this.active.get() == 0 ? z : false;
                    Integer num = (Integer) spscLinkedArrayQueue.poll();
                    boolean z3 = num == null ? z : false;
                    if (z2 && z3) {
                        this.lefts.clear();
                        this.rights.clear();
                        this.disposables.dispose();
                        subscriber.onComplete();
                        return;
                    } else if (z3) {
                        i = addAndGet(-i);
                        if (i == 0) {
                            return;
                        }
                    } else {
                        Object poll = spscLinkedArrayQueue.poll();
                        if (num == LEFT_VALUE) {
                            int i2 = this.leftIndex;
                            this.leftIndex = i2 + 1;
                            this.lefts.put(Integer.valueOf(i2), poll);
                            try {
                                Object apply = this.leftEnd.apply(poll);
                                ObjectHelper.requireNonNull(apply, "The leftEnd returned a null Publisher");
                                Publisher publisher = (Publisher) apply;
                                FlowableGroupJoin.LeftRightEndSubscriber leftRightEndSubscriber = new FlowableGroupJoin.LeftRightEndSubscriber(this, z, i2);
                                this.disposables.add(leftRightEndSubscriber);
                                publisher.subscribe(leftRightEndSubscriber);
                                if (this.error.get() != null) {
                                    spscLinkedArrayQueue.clear();
                                    cancelAll();
                                    errorAll(subscriber);
                                    return;
                                }
                                long j = this.requested.get();
                                long j2 = 0;
                                for (TRight tright : this.rights.values()) {
                                    try {
                                        Object apply2 = this.resultSelector.apply(poll, tright);
                                        ObjectHelper.requireNonNull(apply2, "The resultSelector returned a null value");
                                        if (j2 != j) {
                                            subscriber.onNext(apply2);
                                            j2++;
                                        } else {
                                            ExceptionHelper.addThrowable(this.error, new MissingBackpressureException("Could not emit value due to lack of requests"));
                                            spscLinkedArrayQueue.clear();
                                            cancelAll();
                                            errorAll(subscriber);
                                            return;
                                        }
                                    } catch (Throwable th) {
                                        fail(th, subscriber, spscLinkedArrayQueue);
                                        return;
                                    }
                                }
                                if (j2 != 0) {
                                    BackpressureHelper.produced(this.requested, j2);
                                }
                            } catch (Throwable th2) {
                                fail(th2, subscriber, spscLinkedArrayQueue);
                                return;
                            }
                        } else if (num == RIGHT_VALUE) {
                            int i3 = this.rightIndex;
                            this.rightIndex = i3 + 1;
                            this.rights.put(Integer.valueOf(i3), poll);
                            try {
                                Object apply3 = this.rightEnd.apply(poll);
                                ObjectHelper.requireNonNull(apply3, "The rightEnd returned a null Publisher");
                                Publisher publisher2 = (Publisher) apply3;
                                FlowableGroupJoin.LeftRightEndSubscriber leftRightEndSubscriber2 = new FlowableGroupJoin.LeftRightEndSubscriber(this, false, i3);
                                this.disposables.add(leftRightEndSubscriber2);
                                publisher2.subscribe(leftRightEndSubscriber2);
                                if (this.error.get() != null) {
                                    spscLinkedArrayQueue.clear();
                                    cancelAll();
                                    errorAll(subscriber);
                                    return;
                                }
                                long j3 = this.requested.get();
                                long j4 = 0;
                                for (TLeft tleft : this.lefts.values()) {
                                    try {
                                        Object apply4 = this.resultSelector.apply(tleft, poll);
                                        ObjectHelper.requireNonNull(apply4, "The resultSelector returned a null value");
                                        if (j4 != j3) {
                                            subscriber.onNext(apply4);
                                            j4++;
                                        } else {
                                            ExceptionHelper.addThrowable(this.error, new MissingBackpressureException("Could not emit value due to lack of requests"));
                                            spscLinkedArrayQueue.clear();
                                            cancelAll();
                                            errorAll(subscriber);
                                            return;
                                        }
                                    } catch (Throwable th3) {
                                        fail(th3, subscriber, spscLinkedArrayQueue);
                                        return;
                                    }
                                }
                                if (j4 != 0) {
                                    BackpressureHelper.produced(this.requested, j4);
                                }
                            } catch (Throwable th4) {
                                fail(th4, subscriber, spscLinkedArrayQueue);
                                return;
                            }
                        } else if (num == LEFT_CLOSE) {
                            TRight tright2 = (FlowableGroupJoin.LeftRightEndSubscriber) poll;
                            this.lefts.remove(Integer.valueOf(((FlowableGroupJoin.LeftRightEndSubscriber) tright2).index));
                            this.disposables.remove(tright2);
                        } else if (num == RIGHT_CLOSE) {
                            TRight tright3 = (FlowableGroupJoin.LeftRightEndSubscriber) poll;
                            this.rights.remove(Integer.valueOf(((FlowableGroupJoin.LeftRightEndSubscriber) tright3).index));
                            this.disposables.remove(tright3);
                        }
                        z = true;
                    }
                }
                spscLinkedArrayQueue.clear();
            }
        }

        /* access modifiers changed from: package-private */
        public void errorAll(Subscriber<?> subscriber) {
            Throwable terminate = ExceptionHelper.terminate(this.error);
            this.lefts.clear();
            this.rights.clear();
            subscriber.onError(terminate);
        }

        /* access modifiers changed from: package-private */
        public void fail(Throwable th, Subscriber<?> subscriber, SimpleQueue<?> simpleQueue) {
            Exceptions.throwIfFatal(th);
            ExceptionHelper.addThrowable(this.error, th);
            simpleQueue.clear();
            cancelAll();
            errorAll(subscriber);
        }

        @Override // io.reactivex.internal.operators.flowable.FlowableGroupJoin.JoinSupport
        public void innerClose(boolean z, FlowableGroupJoin.LeftRightEndSubscriber leftRightEndSubscriber) {
            synchronized (this) {
                this.queue.offer(z ? LEFT_CLOSE : RIGHT_CLOSE, leftRightEndSubscriber);
            }
            drain();
        }

        @Override // io.reactivex.internal.operators.flowable.FlowableGroupJoin.JoinSupport
        public void innerCloseError(Throwable th) {
            if (ExceptionHelper.addThrowable(this.error, th)) {
                drain();
            } else {
                RxJavaPlugins.onError(th);
            }
        }

        @Override // io.reactivex.internal.operators.flowable.FlowableGroupJoin.JoinSupport
        public void innerComplete(FlowableGroupJoin.LeftRightSubscriber leftRightSubscriber) {
            this.disposables.delete(leftRightSubscriber);
            this.active.decrementAndGet();
            drain();
        }

        @Override // io.reactivex.internal.operators.flowable.FlowableGroupJoin.JoinSupport
        public void innerError(Throwable th) {
            if (ExceptionHelper.addThrowable(this.error, th)) {
                this.active.decrementAndGet();
                drain();
                return;
            }
            RxJavaPlugins.onError(th);
        }

        @Override // io.reactivex.internal.operators.flowable.FlowableGroupJoin.JoinSupport
        public void innerValue(boolean z, Object obj) {
            synchronized (this) {
                this.queue.offer(z ? LEFT_VALUE : RIGHT_VALUE, obj);
            }
            drain();
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (SubscriptionHelper.validate(j)) {
                BackpressureHelper.add(this.requested, j);
            }
        }
    }

    public FlowableJoin(Flowable<TLeft> flowable, Publisher<? extends TRight> publisher, Function<? super TLeft, ? extends Publisher<TLeftEnd>> function, Function<? super TRight, ? extends Publisher<TRightEnd>> function2, BiFunction<? super TLeft, ? super TRight, ? extends R> biFunction) {
        super(flowable);
        this.other = publisher;
        this.leftEnd = function;
        this.rightEnd = function2;
        this.resultSelector = biFunction;
    }

    /* access modifiers changed from: protected */
    @Override // io.reactivex.Flowable
    public void subscribeActual(Subscriber<? super R> subscriber) {
        JoinSubscription joinSubscription = new JoinSubscription(subscriber, this.leftEnd, this.rightEnd, this.resultSelector);
        subscriber.onSubscribe(joinSubscription);
        FlowableGroupJoin.LeftRightSubscriber leftRightSubscriber = new FlowableGroupJoin.LeftRightSubscriber(joinSubscription, true);
        joinSubscription.disposables.add(leftRightSubscriber);
        FlowableGroupJoin.LeftRightSubscriber leftRightSubscriber2 = new FlowableGroupJoin.LeftRightSubscriber(joinSubscription, false);
        joinSubscription.disposables.add(leftRightSubscriber2);
        ((AbstractFlowableWithUpstream) this).source.subscribe((FlowableSubscriber) leftRightSubscriber);
        this.other.subscribe(leftRightSubscriber2);
    }
}
