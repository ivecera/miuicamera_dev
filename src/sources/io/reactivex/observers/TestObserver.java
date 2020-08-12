package io.reactivex.observers;

import com.android.camera.CameraIntentManager;
import io.reactivex.CompletableObserver;
import io.reactivex.MaybeObserver;
import io.reactivex.Observer;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.fuseable.QueueDisposable;
import io.reactivex.internal.util.ExceptionHelper;
import java.util.concurrent.atomic.AtomicReference;

public class TestObserver<T> extends BaseTestConsumer<T, TestObserver<T>> implements Observer<T>, Disposable, MaybeObserver<T>, SingleObserver<T>, CompletableObserver {
    private final Observer<? super T> actual;
    private QueueDisposable<T> qs;
    private final AtomicReference<Disposable> subscription;

    enum EmptyObserver implements Observer<Object> {
        INSTANCE;

        @Override // io.reactivex.Observer
        public void onComplete() {
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
        }

        @Override // io.reactivex.Observer
        public void onNext(Object obj) {
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable) {
        }
    }

    public TestObserver() {
        this(EmptyObserver.INSTANCE);
    }

    public TestObserver(Observer<? super T> observer) {
        this.subscription = new AtomicReference<>();
        this.actual = observer;
    }

    public static <T> TestObserver<T> create() {
        return new TestObserver<>();
    }

    public static <T> TestObserver<T> create(Observer<? super T> observer) {
        return new TestObserver<>(observer);
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
    public final TestObserver<T> assertFuseable() {
        if (this.qs != null) {
            return this;
        }
        throw new AssertionError("Upstream is not fuseable.");
    }

    /* access modifiers changed from: package-private */
    public final TestObserver<T> assertFusionMode(int i) {
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
    public final TestObserver<T> assertNotFuseable() {
        if (this.qs == null) {
            return this;
        }
        throw new AssertionError("Upstream is fuseable.");
    }

    @Override // io.reactivex.observers.BaseTestConsumer
    public final TestObserver<T> assertNotSubscribed() {
        if (this.subscription.get() != null) {
            throw fail("Subscribed!");
        } else if (((BaseTestConsumer) this).errors.isEmpty()) {
            return this;
        } else {
            throw fail("Not subscribed but errors found");
        }
    }

    public final TestObserver<T> assertOf(Consumer<? super TestObserver<T>> consumer) {
        try {
            consumer.accept(this);
            return this;
        } catch (Throwable th) {
            throw ExceptionHelper.wrapOrThrow(th);
        }
    }

    @Override // io.reactivex.observers.BaseTestConsumer
    public final TestObserver<T> assertSubscribed() {
        if (this.subscription.get() != null) {
            return this;
        }
        throw fail("Not subscribed!");
    }

    public final void cancel() {
        dispose();
    }

    @Override // io.reactivex.disposables.Disposable
    public final void dispose() {
        DisposableHelper.dispose(this.subscription);
    }

    public final boolean hasSubscription() {
        return this.subscription.get() != null;
    }

    public final boolean isCancelled() {
        return isDisposed();
    }

    @Override // io.reactivex.disposables.Disposable
    public final boolean isDisposed() {
        return DisposableHelper.isDisposed(this.subscription.get());
    }

    @Override // io.reactivex.MaybeObserver, io.reactivex.CompletableObserver, io.reactivex.Observer
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

    @Override // io.reactivex.MaybeObserver, io.reactivex.SingleObserver, io.reactivex.CompletableObserver, io.reactivex.Observer
    public void onError(Throwable th) {
        if (!((BaseTestConsumer) this).checkSubscriptionOnce) {
            ((BaseTestConsumer) this).checkSubscriptionOnce = true;
            if (this.subscription.get() == null) {
                ((BaseTestConsumer) this).errors.add(new IllegalStateException("onSubscribe not called in proper order"));
            }
        }
        try {
            ((BaseTestConsumer) this).lastThread = Thread.currentThread();
            if (th == null) {
                ((BaseTestConsumer) this).errors.add(new NullPointerException("onError received a null Throwable"));
            } else {
                ((BaseTestConsumer) this).errors.add(th);
            }
            this.actual.onError(th);
        } finally {
            ((BaseTestConsumer) this).done.countDown();
        }
    }

    @Override // io.reactivex.Observer
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
                    this.qs.dispose();
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

    @Override // io.reactivex.MaybeObserver, io.reactivex.SingleObserver, io.reactivex.CompletableObserver, io.reactivex.Observer
    public void onSubscribe(Disposable disposable) {
        ((BaseTestConsumer) this).lastThread = Thread.currentThread();
        if (disposable == null) {
            ((BaseTestConsumer) this).errors.add(new NullPointerException("onSubscribe received a null Subscription"));
        } else if (!this.subscription.compareAndSet(null, disposable)) {
            disposable.dispose();
            if (this.subscription.get() != DisposableHelper.DISPOSED) {
                ((BaseTestConsumer) this).errors.add(new IllegalStateException("onSubscribe received multiple subscriptions: " + disposable));
            }
        } else {
            int i = ((BaseTestConsumer) this).initialFusionMode;
            if (i != 0 && (disposable instanceof QueueDisposable)) {
                this.qs = (QueueDisposable) disposable;
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
                                this.subscription.lazySet(DisposableHelper.DISPOSED);
                                return;
                            }
                        } catch (Throwable th) {
                            ((BaseTestConsumer) this).errors.add(th);
                            return;
                        }
                    }
                }
            }
            this.actual.onSubscribe(disposable);
        }
    }

    @Override // io.reactivex.MaybeObserver, io.reactivex.SingleObserver
    public void onSuccess(T t) {
        onNext(t);
        onComplete();
    }

    /* access modifiers changed from: package-private */
    public final TestObserver<T> setInitialFusionMode(int i) {
        ((BaseTestConsumer) this).initialFusionMode = i;
        return this;
    }
}
