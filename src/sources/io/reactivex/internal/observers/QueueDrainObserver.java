package io.reactivex.internal.observers;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.fuseable.SimplePlainQueue;
import io.reactivex.internal.util.ObservableQueueDrain;
import io.reactivex.internal.util.QueueDrainHelper;

public abstract class QueueDrainObserver<T, U, V> extends QueueDrainSubscriberPad2 implements Observer<T>, ObservableQueueDrain<U, V> {
    protected final Observer<? super V> actual;
    protected volatile boolean cancelled;
    protected volatile boolean done;
    protected Throwable error;
    protected final SimplePlainQueue<U> queue;

    public QueueDrainObserver(Observer<? super V> observer, SimplePlainQueue<U> simplePlainQueue) {
        this.actual = observer;
        this.queue = simplePlainQueue;
    }

    @Override // io.reactivex.internal.util.ObservableQueueDrain
    public void accept(Observer<? super V> observer, U u) {
    }

    @Override // io.reactivex.internal.util.ObservableQueueDrain
    public final boolean cancelled() {
        return this.cancelled;
    }

    @Override // io.reactivex.internal.util.ObservableQueueDrain
    public final boolean done() {
        return this.done;
    }

    @Override // io.reactivex.internal.util.ObservableQueueDrain
    public final boolean enter() {
        return ((QueueDrainSubscriberWip) this).wip.getAndIncrement() == 0;
    }

    @Override // io.reactivex.internal.util.ObservableQueueDrain
    public final Throwable error() {
        return this.error;
    }

    public final boolean fastEnter() {
        return ((QueueDrainSubscriberWip) this).wip.get() == 0 && ((QueueDrainSubscriberWip) this).wip.compareAndSet(0, 1);
    }

    /* access modifiers changed from: protected */
    public final void fastPathEmit(U u, boolean z, Disposable disposable) {
        Observer<? super V> observer = this.actual;
        SimplePlainQueue<U> simplePlainQueue = this.queue;
        if (((QueueDrainSubscriberWip) this).wip.get() != 0 || !((QueueDrainSubscriberWip) this).wip.compareAndSet(0, 1)) {
            simplePlainQueue.offer(u);
            if (!enter()) {
                return;
            }
        } else {
            accept(observer, u);
            if (leave(-1) == 0) {
                return;
            }
        }
        QueueDrainHelper.drainLoop(simplePlainQueue, observer, z, disposable, this);
    }

    /* access modifiers changed from: protected */
    public final void fastPathOrderedEmit(U u, boolean z, Disposable disposable) {
        Observer<? super V> observer = this.actual;
        SimplePlainQueue<U> simplePlainQueue = this.queue;
        if (((QueueDrainSubscriberWip) this).wip.get() != 0 || !((QueueDrainSubscriberWip) this).wip.compareAndSet(0, 1)) {
            simplePlainQueue.offer(u);
            if (!enter()) {
                return;
            }
        } else if (simplePlainQueue.isEmpty()) {
            accept(observer, u);
            if (leave(-1) == 0) {
                return;
            }
        } else {
            simplePlainQueue.offer(u);
        }
        QueueDrainHelper.drainLoop(simplePlainQueue, observer, z, disposable, this);
    }

    @Override // io.reactivex.internal.util.ObservableQueueDrain
    public final int leave(int i) {
        return ((QueueDrainSubscriberWip) this).wip.addAndGet(i);
    }
}
