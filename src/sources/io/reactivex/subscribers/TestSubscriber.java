package io.reactivex.subscribers;

import com.android.camera.CameraIntentManager;
import io.reactivex.FlowableSubscriber;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.fuseable.QueueSubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.observers.BaseTestConsumer;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public class TestSubscriber<T> extends BaseTestConsumer<T, TestSubscriber<T>> implements FlowableSubscriber<T>, Subscription, Disposable {
    private final Subscriber<? super T> actual;
    private volatile boolean cancelled;
    private final AtomicLong missedRequested;
    private QueueSubscription<T> qs;
    private final AtomicReference<Subscription> subscription;

    enum EmptySubscriber implements FlowableSubscriber<Object> {
        INSTANCE;

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(Object obj) {
        }

        @Override // io.reactivex.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
        }
    }

    public TestSubscriber() {
        this(EmptySubscriber.INSTANCE, Long.MAX_VALUE);
    }

    public TestSubscriber(long j) {
        this(EmptySubscriber.INSTANCE, j);
    }

    public TestSubscriber(Subscriber<? super T> subscriber) {
        this(subscriber, Long.MAX_VALUE);
    }

    public TestSubscriber(Subscriber<? super T> subscriber, long j) {
        if (j >= 0) {
            this.actual = subscriber;
            this.subscription = new AtomicReference<>();
            this.missedRequested = new AtomicLong(j);
            return;
        }
        throw new IllegalArgumentException("Negative initial request not allowed");
    }

    public static <T> TestSubscriber<T> create() {
        return new TestSubscriber<>();
    }

    public static <T> TestSubscriber<T> create(long j) {
        return new TestSubscriber<>(j);
    }

    public static <T> TestSubscriber<T> create(Subscriber<? super T> subscriber) {
        return new TestSubscriber<>(subscriber);
    }

    static String fusionModeToString(int i) {
        if (i == 0) {
            return CameraIntentManager.ControlActions.CONTROL_ACTION_UNKNOWN;
        }
        if (i == 1) {
            return "SYNC";
        }
        if (i == 2) {
            return "ASYNC";
        }
        return "Unknown(" + i + ")";
    }

    /* access modifiers changed from: package-private */
    public final TestSubscriber<T> assertFuseable() {
        if (this.qs != null) {
            return this;
        }
        throw new AssertionError("Upstream is not fuseable.");
    }

    /* access modifiers changed from: package-private */
    public final TestSubscriber<T> assertFusionMode(int i) {
        int i2 = ((BaseTestConsumer) this).establishedFusionMode;
        if (i2 == i) {
            return this;
        }
        if (this.qs != null) {
            throw new AssertionError("Fusion mode different. Expected: " + fusionModeToString(i) + ", actual: " + fusionModeToString(i2));
        }
        throw fail("Upstream is not fuseable");
    }

    /* access modifiers changed from: package-private */
    public final TestSubscriber<T> assertNotFuseable() {
        if (this.qs == null) {
            return this;
        }
        throw new AssertionError("Upstream is fuseable.");
    }

    @Override // io.reactivex.observers.BaseTestConsumer
    public final TestSubscriber<T> assertNotSubscribed() {
        if (this.subscription.get() != null) {
            throw fail("Subscribed!");
        } else if (((BaseTestConsumer) this).errors.isEmpty()) {
            return this;
        } else {
            throw fail("Not subscribed but errors found");
        }
    }

    public final TestSubscriber<T> assertOf(Consumer<? super TestSubscriber<T>> consumer) {
        try {
            consumer.accept(this);
            return this;
        } catch (Throwable th) {
            throw ExceptionHelper.wrapOrThrow(th);
        }
    }

    @Override // io.reactivex.observers.BaseTestConsumer
    public final TestSubscriber<T> assertSubscribed() {
        if (this.subscription.get() != null) {
            return this;
        }
        throw fail("Not subscribed!");
    }

    @Override // org.reactivestreams.Subscription
    public final void cancel() {
        if (!this.cancelled) {
            this.cancelled = true;
            SubscriptionHelper.cancel(this.subscription);
        }
    }

    @Override // io.reactivex.disposables.Disposable
    public final void dispose() {
        cancel();
    }

    public final boolean hasSubscription() {
        return this.subscription.get() != null;
    }

    public final boolean isCancelled() {
        return this.cancelled;
    }

    @Override // io.reactivex.disposables.Disposable
    public final boolean isDisposed() {
        return this.cancelled;
    }

    @Override // org.reactivestreams.Subscriber
    public void onComplete() {
        if (!((BaseTestConsumer) this).checkSubscriptionOnce) {
            ((BaseTestConsumer) this).checkSubscriptionOnce = true;
            if (this.subscription.get() == null) {
                ((BaseTestConsumer) this).errors.add(new IllegalStateException("onSubscribe not called in proper order"));
            }
        }
        try {
            ((BaseTestConsumer) this).lastThread = Thread.currentThread();
            ((BaseTestConsumer) this).completions++;
            this.actual.onComplete();
        } finally {
            ((BaseTestConsumer) this).done.countDown();
        }
    }

    @Override // org.reactivestreams.Subscriber
    public void onError(Throwable th) {
        if (!((BaseTestConsumer) this).checkSubscriptionOnce) {
            ((BaseTestConsumer) this).checkSubscriptionOnce = true;
            if (this.subscription.get() == null) {
                ((BaseTestConsumer) this).errors.add(new NullPointerException("onSubscribe not called in proper order"));
            }
        }
        try {
            ((BaseTestConsumer) this).lastThread = Thread.currentThread();
            ((BaseTestConsumer) this).errors.add(th);
            if (th == null) {
                ((BaseTestConsumer) this).errors.add(new IllegalStateException("onError received a null Throwable"));
            }
            this.actual.onError(th);
        } finally {
            ((BaseTestConsumer) this).done.countDown();
        }
    }

    @Override // org.reactivestreams.Subscriber
    public void onNext(T t) {
        if (!((BaseTestConsumer) this).checkSubscriptionOnce) {
            ((BaseTestConsumer) this).checkSubscriptionOnce = true;
            if (this.subscription.get() == null) {
                ((BaseTestConsumer) this).errors.add(new IllegalStateException("onSubscribe not called in proper order"));
            }
        }
        ((BaseTestConsumer) this).lastThread = Thread.currentThread();
        if (((BaseTestConsumer) this).establishedFusionMode == 2) {
            while (true) {
                try {
                    T poll = this.qs.poll();
                    if (poll != null) {
                        ((BaseTestConsumer) this).values.add(poll);
                    } else {
                        return;
                    }
                } catch (Throwable th) {
                    ((BaseTestConsumer) this).errors.add(th);
                    this.qs.cancel();
                    return;
                }
            }
        } else {
            ((BaseTestConsumer) this).values.add(t);
            if (t == null) {
                ((BaseTestConsumer) this).errors.add(new NullPointerException("onNext received a null value"));
            }
            this.actual.onNext(t);
        }
    }

    /* access modifiers changed from: protected */
    public void onStart() {
    }

    @Override // io.reactivex.FlowableSubscriber, org.reactivestreams.Subscriber
    public void onSubscribe(Subscription subscription2) {
        ((BaseTestConsumer) this).lastThread = Thread.currentThread();
        if (subscription2 == null) {
            ((BaseTestConsumer) this).errors.add(new NullPointerException("onSubscribe received a null Subscription"));
        } else if (!this.subscription.compareAndSet(null, subscription2)) {
            subscription2.cancel();
            if (this.subscription.get() != SubscriptionHelper.CANCELLED) {
                ((BaseTestConsumer) this).errors.add(new IllegalStateException("onSubscribe received multiple subscriptions: " + subscription2));
            }
        } else {
            int i = ((BaseTestConsumer) this).initialFusionMode;
            if (i != 0 && (subscription2 instanceof QueueSubscription)) {
                this.qs = (QueueSubscription) subscription2;
                int requestFusion = this.qs.requestFusion(i);
                ((BaseTestConsumer) this).establishedFusionMode = requestFusion;
                if (requestFusion == 1) {
                    ((BaseTestConsumer) this).checkSubscriptionOnce = true;
                    ((BaseTestConsumer) this).lastThread = Thread.currentThread();
                    while (true) {
                        try {
                            T poll = this.qs.poll();
                            if (poll != null) {
                                ((BaseTestConsumer) this).values.add(poll);
                            } else {
                                ((BaseTestConsumer) this).completions++;
                                return;
                            }
                        } catch (Throwable th) {
                            ((BaseTestConsumer) this).errors.add(th);
                            return;
                        }
                    }
                }
            }
            this.actual.onSubscribe(subscription2);
            long andSet = this.missedRequested.getAndSet(0);
            if (andSet != 0) {
                subscription2.request(andSet);
            }
            onStart();
        }
    }

    @Override // org.reactivestreams.Subscription
    public final void request(long j) {
        SubscriptionHelper.deferredRequest(this.subscription, this.missedRequested, j);
    }

    public final TestSubscriber<T> requestMore(long j) {
        request(j);
        return this;
    }

    /* access modifiers changed from: package-private */
    public final TestSubscriber<T> setInitialFusionMode(int i) {
        ((BaseTestConsumer) this).initialFusionMode = i;
        return this;
    }
}
