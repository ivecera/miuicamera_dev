package io.reactivex.processors;

import io.reactivex.Scheduler;
import io.reactivex.annotations.CheckReturnValue;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class ReplayProcessor<T> extends FlowableProcessor<T> {
    static final ReplaySubscription[] EMPTY = new ReplaySubscription[0];
    private static final Object[] EMPTY_ARRAY = new Object[0];
    static final ReplaySubscription[] TERMINATED = new ReplaySubscription[0];
    final ReplayBuffer<T> buffer;
    boolean done;
    final AtomicReference<ReplaySubscription<T>[]> subscribers = new AtomicReference<>(EMPTY);

    static final class Node<T> extends AtomicReference<Node<T>> {
        private static final long serialVersionUID = 6404226426336033100L;
        final T value;

        Node(T t) {
            this.value = t;
        }
    }

    interface ReplayBuffer<T> {
        void complete();

        void error(Throwable th);

        Throwable getError();

        T getValue();

        T[] getValues(T[] tArr);

        boolean isDone();

        void next(T t);

        void replay(ReplaySubscription<T> replaySubscription);

        int size();
    }

    static final class ReplaySubscription<T> extends AtomicInteger implements Subscription {
        private static final long serialVersionUID = 466549804534799122L;
        final Subscriber<? super T> actual;
        volatile boolean cancelled;
        long emitted;
        Object index;
        final AtomicLong requested = new AtomicLong();
        final ReplayProcessor<T> state;

        ReplaySubscription(Subscriber<? super T> subscriber, ReplayProcessor<T> replayProcessor) {
            this.actual = subscriber;
            this.state = replayProcessor;
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            if (!this.cancelled) {
                this.cancelled = true;
                this.state.remove(this);
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (SubscriptionHelper.validate(j)) {
                BackpressureHelper.add(this.requested, j);
                this.state.buffer.replay(this);
            }
        }
    }

    static final class SizeAndTimeBoundReplayBuffer<T> implements ReplayBuffer<T> {
        volatile boolean done;
        Throwable error;
        volatile TimedNode<T> head;
        final long maxAge;
        final int maxSize;
        final Scheduler scheduler;
        int size;
        TimedNode<T> tail;
        final TimeUnit unit;

        SizeAndTimeBoundReplayBuffer(int i, long j, TimeUnit timeUnit, Scheduler scheduler2) {
            ObjectHelper.verifyPositive(i, "maxSize");
            this.maxSize = i;
            ObjectHelper.verifyPositive(j, "maxAge");
            this.maxAge = j;
            ObjectHelper.requireNonNull(timeUnit, "unit is null");
            this.unit = timeUnit;
            ObjectHelper.requireNonNull(scheduler2, "scheduler is null");
            this.scheduler = scheduler2;
            TimedNode<T> timedNode = new TimedNode<>(null, 0);
            this.tail = timedNode;
            this.head = timedNode;
        }

        @Override // io.reactivex.processors.ReplayProcessor.ReplayBuffer
        public void complete() {
            trimFinal();
            this.done = true;
        }

        @Override // io.reactivex.processors.ReplayProcessor.ReplayBuffer
        public void error(Throwable th) {
            trimFinal();
            this.error = th;
            this.done = true;
        }

        @Override // io.reactivex.processors.ReplayProcessor.ReplayBuffer
        public Throwable getError() {
            return this.error;
        }

        /* access modifiers changed from: package-private */
        public TimedNode<T> getHead() {
            TimedNode<T> timedNode = this.head;
            long now = this.scheduler.now(this.unit) - this.maxAge;
            Object obj = timedNode.get();
            while (true) {
                timedNode = (TimedNode) obj;
                if (timedNode == null || timedNode.time > now) {
                    return timedNode;
                }
                obj = timedNode.get();
            }
            return timedNode;
        }

        @Override // io.reactivex.processors.ReplayProcessor.ReplayBuffer
        public T getValue() {
            TimedNode<T> timedNode = this.head;
            while (true) {
                TimedNode<T> timedNode2 = (TimedNode) timedNode.get();
                if (timedNode2 == null) {
                    break;
                }
                timedNode = timedNode2;
            }
            if (timedNode.time < this.scheduler.now(this.unit) - this.maxAge) {
                return null;
            }
            return timedNode.value;
        }

        @Override // io.reactivex.processors.ReplayProcessor.ReplayBuffer
        public T[] getValues(T[] tArr) {
            TimedNode<T> head2 = getHead();
            int size2 = size(head2);
            if (size2 != 0) {
                if (tArr.length < size2) {
                    tArr = (Object[]) Array.newInstance(tArr.getClass().getComponentType(), size2);
                }
                for (int i = 0; i != size2; i++) {
                    head2 = (TimedNode) head2.get();
                    tArr[i] = head2.value;
                }
                if (tArr.length > size2) {
                    tArr[size2] = null;
                }
            } else if (tArr.length != 0) {
                tArr[0] = null;
            }
            return tArr;
        }

        @Override // io.reactivex.processors.ReplayProcessor.ReplayBuffer
        public boolean isDone() {
            return this.done;
        }

        @Override // io.reactivex.processors.ReplayProcessor.ReplayBuffer
        public void next(T t) {
            TimedNode<T> timedNode = new TimedNode<>(t, this.scheduler.now(this.unit));
            TimedNode<T> timedNode2 = this.tail;
            this.tail = timedNode;
            this.size++;
            timedNode2.set(timedNode);
            trim();
        }

        @Override // io.reactivex.processors.ReplayProcessor.ReplayBuffer
        public void replay(ReplaySubscription<T> replaySubscription) {
            int i;
            if (replaySubscription.getAndIncrement() == 0) {
                Subscriber<? super T> subscriber = replaySubscription.actual;
                TimedNode<T> timedNode = (TimedNode) replaySubscription.index;
                if (timedNode == null) {
                    timedNode = getHead();
                }
                long j = replaySubscription.emitted;
                int i2 = 1;
                do {
                    long j2 = replaySubscription.requested.get();
                    while (true) {
                        i = (j > j2 ? 1 : (j == j2 ? 0 : -1));
                        if (i == 0) {
                            break;
                        } else if (replaySubscription.cancelled) {
                            replaySubscription.index = null;
                            return;
                        } else {
                            boolean z = this.done;
                            TimedNode<T> timedNode2 = (TimedNode) timedNode.get();
                            boolean z2 = timedNode2 == null;
                            if (z && z2) {
                                replaySubscription.index = null;
                                replaySubscription.cancelled = true;
                                Throwable th = this.error;
                                if (th == null) {
                                    subscriber.onComplete();
                                    return;
                                } else {
                                    subscriber.onError(th);
                                    return;
                                }
                            } else if (z2) {
                                break;
                            } else {
                                subscriber.onNext(timedNode2.value);
                                j++;
                                timedNode = timedNode2;
                            }
                        }
                    }
                    if (i == 0) {
                        if (replaySubscription.cancelled) {
                            replaySubscription.index = null;
                            return;
                        } else if (this.done && timedNode.get() == null) {
                            replaySubscription.index = null;
                            replaySubscription.cancelled = true;
                            Throwable th2 = this.error;
                            if (th2 == null) {
                                subscriber.onComplete();
                                return;
                            } else {
                                subscriber.onError(th2);
                                return;
                            }
                        }
                    }
                    replaySubscription.index = timedNode;
                    replaySubscription.emitted = j;
                    i2 = replaySubscription.addAndGet(-i2);
                } while (i2 != 0);
            }
        }

        @Override // io.reactivex.processors.ReplayProcessor.ReplayBuffer
        public int size() {
            return size(getHead());
        }

        /* access modifiers changed from: package-private */
        public int size(TimedNode<T> timedNode) {
            int i = 0;
            while (i != Integer.MAX_VALUE && (timedNode = (TimedNode) timedNode.get()) != null) {
                i++;
            }
            return i;
        }

        /* access modifiers changed from: package-private */
        public void trim() {
            int i = this.size;
            if (i > this.maxSize) {
                this.size = i - 1;
                this.head = (TimedNode) this.head.get();
            }
            long now = this.scheduler.now(this.unit) - this.maxAge;
            TimedNode<T> timedNode = this.head;
            while (true) {
                TimedNode<T> timedNode2 = (TimedNode) timedNode.get();
                if (timedNode2 == null) {
                    this.head = timedNode;
                    return;
                } else if (timedNode2.time > now) {
                    this.head = timedNode;
                    return;
                } else {
                    timedNode = timedNode2;
                }
            }
        }

        /* access modifiers changed from: package-private */
        public void trimFinal() {
            long now = this.scheduler.now(this.unit) - this.maxAge;
            TimedNode<T> timedNode = this.head;
            while (true) {
                TimedNode<T> timedNode2 = (TimedNode) timedNode.get();
                if (timedNode2 == null) {
                    this.head = timedNode;
                    return;
                } else if (timedNode2.time > now) {
                    this.head = timedNode;
                    return;
                } else {
                    timedNode = timedNode2;
                }
            }
        }
    }

    static final class SizeBoundReplayBuffer<T> implements ReplayBuffer<T> {
        volatile boolean done;
        Throwable error;
        volatile Node<T> head;
        final int maxSize;
        int size;
        Node<T> tail;

        SizeBoundReplayBuffer(int i) {
            ObjectHelper.verifyPositive(i, "maxSize");
            this.maxSize = i;
            Node<T> node = new Node<>(null);
            this.tail = node;
            this.head = node;
        }

        @Override // io.reactivex.processors.ReplayProcessor.ReplayBuffer
        public void complete() {
            this.done = true;
        }

        @Override // io.reactivex.processors.ReplayProcessor.ReplayBuffer
        public void error(Throwable th) {
            this.error = th;
            this.done = true;
        }

        @Override // io.reactivex.processors.ReplayProcessor.ReplayBuffer
        public Throwable getError() {
            return this.error;
        }

        @Override // io.reactivex.processors.ReplayProcessor.ReplayBuffer
        public T getValue() {
            Node<T> node = this.head;
            while (true) {
                Node<T> node2 = (Node) node.get();
                if (node2 == null) {
                    return node.value;
                }
                node = node2;
            }
        }

        @Override // io.reactivex.processors.ReplayProcessor.ReplayBuffer
        public T[] getValues(T[] tArr) {
            Node<T> node = this.head;
            Node<T> node2 = node;
            int i = 0;
            while (true) {
                node2 = (Node) node2.get();
                if (node2 == null) {
                    break;
                }
                i++;
            }
            if (tArr.length < i) {
                tArr = (Object[]) Array.newInstance(tArr.getClass().getComponentType(), i);
            }
            for (int i2 = 0; i2 < i; i2++) {
                node = (Node) node.get();
                tArr[i2] = node.value;
            }
            if (tArr.length > i) {
                tArr[i] = null;
            }
            return tArr;
        }

        @Override // io.reactivex.processors.ReplayProcessor.ReplayBuffer
        public boolean isDone() {
            return this.done;
        }

        @Override // io.reactivex.processors.ReplayProcessor.ReplayBuffer
        public void next(T t) {
            Node<T> node = new Node<>(t);
            Node<T> node2 = this.tail;
            this.tail = node;
            this.size++;
            node2.set(node);
            trim();
        }

        @Override // io.reactivex.processors.ReplayProcessor.ReplayBuffer
        public void replay(ReplaySubscription<T> replaySubscription) {
            int i;
            if (replaySubscription.getAndIncrement() == 0) {
                Subscriber<? super T> subscriber = replaySubscription.actual;
                Node<T> node = (Node) replaySubscription.index;
                if (node == null) {
                    node = this.head;
                }
                long j = replaySubscription.emitted;
                int i2 = 1;
                do {
                    long j2 = replaySubscription.requested.get();
                    while (true) {
                        i = (j > j2 ? 1 : (j == j2 ? 0 : -1));
                        if (i == 0) {
                            break;
                        } else if (replaySubscription.cancelled) {
                            replaySubscription.index = null;
                            return;
                        } else {
                            boolean z = this.done;
                            Node<T> node2 = (Node) node.get();
                            boolean z2 = node2 == null;
                            if (z && z2) {
                                replaySubscription.index = null;
                                replaySubscription.cancelled = true;
                                Throwable th = this.error;
                                if (th == null) {
                                    subscriber.onComplete();
                                    return;
                                } else {
                                    subscriber.onError(th);
                                    return;
                                }
                            } else if (z2) {
                                break;
                            } else {
                                subscriber.onNext(node2.value);
                                j++;
                                node = node2;
                            }
                        }
                    }
                    if (i == 0) {
                        if (replaySubscription.cancelled) {
                            replaySubscription.index = null;
                            return;
                        } else if (this.done && node.get() == null) {
                            replaySubscription.index = null;
                            replaySubscription.cancelled = true;
                            Throwable th2 = this.error;
                            if (th2 == null) {
                                subscriber.onComplete();
                                return;
                            } else {
                                subscriber.onError(th2);
                                return;
                            }
                        }
                    }
                    replaySubscription.index = node;
                    replaySubscription.emitted = j;
                    i2 = replaySubscription.addAndGet(-i2);
                } while (i2 != 0);
            }
        }

        @Override // io.reactivex.processors.ReplayProcessor.ReplayBuffer
        public int size() {
            Node<T> node = this.head;
            int i = 0;
            while (i != Integer.MAX_VALUE && (node = (Node) node.get()) != null) {
                i++;
            }
            return i;
        }

        /* access modifiers changed from: package-private */
        public void trim() {
            int i = this.size;
            if (i > this.maxSize) {
                this.size = i - 1;
                this.head = (Node) this.head.get();
            }
        }
    }

    static final class TimedNode<T> extends AtomicReference<TimedNode<T>> {
        private static final long serialVersionUID = 6404226426336033100L;
        final long time;
        final T value;

        TimedNode(T t, long j) {
            this.value = t;
            this.time = j;
        }
    }

    static final class UnboundedReplayBuffer<T> implements ReplayBuffer<T> {
        final List<T> buffer;
        volatile boolean done;
        Throwable error;
        volatile int size;

        UnboundedReplayBuffer(int i) {
            ObjectHelper.verifyPositive(i, "capacityHint");
            this.buffer = new ArrayList(i);
        }

        @Override // io.reactivex.processors.ReplayProcessor.ReplayBuffer
        public void complete() {
            this.done = true;
        }

        @Override // io.reactivex.processors.ReplayProcessor.ReplayBuffer
        public void error(Throwable th) {
            this.error = th;
            this.done = true;
        }

        @Override // io.reactivex.processors.ReplayProcessor.ReplayBuffer
        public Throwable getError() {
            return this.error;
        }

        @Override // io.reactivex.processors.ReplayProcessor.ReplayBuffer
        public T getValue() {
            int i = this.size;
            if (i == 0) {
                return null;
            }
            return this.buffer.get(i - 1);
        }

        @Override // io.reactivex.processors.ReplayProcessor.ReplayBuffer
        public T[] getValues(T[] tArr) {
            int i = this.size;
            if (i == 0) {
                if (tArr.length != 0) {
                    tArr[0] = null;
                }
                return tArr;
            }
            List<T> list = this.buffer;
            if (tArr.length < i) {
                tArr = (Object[]) Array.newInstance(tArr.getClass().getComponentType(), i);
            }
            for (int i2 = 0; i2 < i; i2++) {
                tArr[i2] = list.get(i2);
            }
            if (tArr.length > i) {
                tArr[i] = null;
            }
            return tArr;
        }

        @Override // io.reactivex.processors.ReplayProcessor.ReplayBuffer
        public boolean isDone() {
            return this.done;
        }

        @Override // io.reactivex.processors.ReplayProcessor.ReplayBuffer
        public void next(T t) {
            this.buffer.add(t);
            this.size++;
        }

        @Override // io.reactivex.processors.ReplayProcessor.ReplayBuffer
        public void replay(ReplaySubscription<T> replaySubscription) {
            int i;
            if (replaySubscription.getAndIncrement() == 0) {
                List<T> list = this.buffer;
                Subscriber<? super T> subscriber = replaySubscription.actual;
                Integer num = (Integer) replaySubscription.index;
                int i2 = 0;
                if (num != null) {
                    i2 = num.intValue();
                } else {
                    replaySubscription.index = 0;
                }
                long j = replaySubscription.emitted;
                int i3 = 1;
                do {
                    long j2 = replaySubscription.requested.get();
                    while (true) {
                        i = (j > j2 ? 1 : (j == j2 ? 0 : -1));
                        if (i == 0) {
                            break;
                        } else if (replaySubscription.cancelled) {
                            replaySubscription.index = null;
                            return;
                        } else {
                            boolean z = this.done;
                            int i4 = this.size;
                            if (z && i2 == i4) {
                                replaySubscription.index = null;
                                replaySubscription.cancelled = true;
                                Throwable th = this.error;
                                if (th == null) {
                                    subscriber.onComplete();
                                    return;
                                } else {
                                    subscriber.onError(th);
                                    return;
                                }
                            } else if (i2 == i4) {
                                break;
                            } else {
                                subscriber.onNext(list.get(i2));
                                i2++;
                                j++;
                            }
                        }
                    }
                    if (i == 0) {
                        if (replaySubscription.cancelled) {
                            replaySubscription.index = null;
                            return;
                        }
                        boolean z2 = this.done;
                        int i5 = this.size;
                        if (z2 && i2 == i5) {
                            replaySubscription.index = null;
                            replaySubscription.cancelled = true;
                            Throwable th2 = this.error;
                            if (th2 == null) {
                                subscriber.onComplete();
                                return;
                            } else {
                                subscriber.onError(th2);
                                return;
                            }
                        }
                    }
                    replaySubscription.index = Integer.valueOf(i2);
                    replaySubscription.emitted = j;
                    i3 = replaySubscription.addAndGet(-i3);
                } while (i3 != 0);
            }
        }

        @Override // io.reactivex.processors.ReplayProcessor.ReplayBuffer
        public int size() {
            return this.size;
        }
    }

    ReplayProcessor(ReplayBuffer<T> replayBuffer) {
        this.buffer = replayBuffer;
    }

    @CheckReturnValue
    public static <T> ReplayProcessor<T> create() {
        return new ReplayProcessor<>(new UnboundedReplayBuffer(16));
    }

    @CheckReturnValue
    public static <T> ReplayProcessor<T> create(int i) {
        return new ReplayProcessor<>(new UnboundedReplayBuffer(i));
    }

    static <T> ReplayProcessor<T> createUnbounded() {
        return new ReplayProcessor<>(new SizeBoundReplayBuffer(Integer.MAX_VALUE));
    }

    @CheckReturnValue
    public static <T> ReplayProcessor<T> createWithSize(int i) {
        return new ReplayProcessor<>(new SizeBoundReplayBuffer(i));
    }

    @CheckReturnValue
    public static <T> ReplayProcessor<T> createWithTime(long j, TimeUnit timeUnit, Scheduler scheduler) {
        return new ReplayProcessor<>(new SizeAndTimeBoundReplayBuffer(Integer.MAX_VALUE, j, timeUnit, scheduler));
    }

    @CheckReturnValue
    public static <T> ReplayProcessor<T> createWithTimeAndSize(long j, TimeUnit timeUnit, Scheduler scheduler, int i) {
        return new ReplayProcessor<>(new SizeAndTimeBoundReplayBuffer(i, j, timeUnit, scheduler));
    }

    /* access modifiers changed from: package-private */
    public boolean add(ReplaySubscription<T> replaySubscription) {
        ReplaySubscription<T>[] replaySubscriptionArr;
        ReplaySubscription<T>[] replaySubscriptionArr2;
        do {
            replaySubscriptionArr = this.subscribers.get();
            if (replaySubscriptionArr == TERMINATED) {
                return false;
            }
            int length = replaySubscriptionArr.length;
            replaySubscriptionArr2 = new ReplaySubscription[(length + 1)];
            System.arraycopy(replaySubscriptionArr, 0, replaySubscriptionArr2, 0, length);
            replaySubscriptionArr2[length] = replaySubscription;
        } while (!this.subscribers.compareAndSet(replaySubscriptionArr, replaySubscriptionArr2));
        return true;
    }

    @Override // io.reactivex.processors.FlowableProcessor
    public Throwable getThrowable() {
        ReplayBuffer<T> replayBuffer = this.buffer;
        if (replayBuffer.isDone()) {
            return replayBuffer.getError();
        }
        return null;
    }

    public T getValue() {
        return this.buffer.getValue();
    }

    public Object[] getValues() {
        T[] values = getValues(EMPTY_ARRAY);
        return values == EMPTY_ARRAY ? new Object[0] : values;
    }

    public T[] getValues(T[] tArr) {
        return this.buffer.getValues(tArr);
    }

    @Override // io.reactivex.processors.FlowableProcessor
    public boolean hasComplete() {
        ReplayBuffer<T> replayBuffer = this.buffer;
        return replayBuffer.isDone() && replayBuffer.getError() == null;
    }

    @Override // io.reactivex.processors.FlowableProcessor
    public boolean hasSubscribers() {
        return this.subscribers.get().length != 0;
    }

    @Override // io.reactivex.processors.FlowableProcessor
    public boolean hasThrowable() {
        ReplayBuffer<T> replayBuffer = this.buffer;
        return replayBuffer.isDone() && replayBuffer.getError() != null;
    }

    public boolean hasValue() {
        return this.buffer.size() != 0;
    }

    @Override // org.reactivestreams.Subscriber
    public void onComplete() {
        if (!this.done) {
            this.done = true;
            ReplayBuffer<T> replayBuffer = this.buffer;
            replayBuffer.complete();
            for (ReplaySubscription<T> replaySubscription : this.subscribers.getAndSet(TERMINATED)) {
                replayBuffer.replay(replaySubscription);
            }
        }
    }

    @Override // org.reactivestreams.Subscriber
    public void onError(Throwable th) {
        ObjectHelper.requireNonNull(th, "onError called with null. Null values are generally not allowed in 2.x operators and sources.");
        if (this.done) {
            RxJavaPlugins.onError(th);
            return;
        }
        this.done = true;
        ReplayBuffer<T> replayBuffer = this.buffer;
        replayBuffer.error(th);
        for (ReplaySubscription<T> replaySubscription : this.subscribers.getAndSet(TERMINATED)) {
            replayBuffer.replay(replaySubscription);
        }
    }

    @Override // org.reactivestreams.Subscriber
    public void onNext(T t) {
        ObjectHelper.requireNonNull(t, "onNext called with null. Null values are generally not allowed in 2.x operators and sources.");
        if (!this.done) {
            ReplayBuffer<T> replayBuffer = this.buffer;
            replayBuffer.next(t);
            for (ReplaySubscription<T> replaySubscription : this.subscribers.get()) {
                replayBuffer.replay(replaySubscription);
            }
        }
    }

    @Override // io.reactivex.FlowableSubscriber, org.reactivestreams.Subscriber
    public void onSubscribe(Subscription subscription) {
        if (this.done) {
            subscription.cancel();
        } else {
            subscription.request(Long.MAX_VALUE);
        }
    }

    /* access modifiers changed from: package-private */
    public void remove(ReplaySubscription<T> replaySubscription) {
        ReplaySubscription<T>[] replaySubscriptionArr;
        ReplaySubscription<T>[] replaySubscriptionArr2;
        do {
            replaySubscriptionArr = this.subscribers.get();
            if (replaySubscriptionArr != TERMINATED && replaySubscriptionArr != EMPTY) {
                int length = replaySubscriptionArr.length;
                int i = -1;
                int i2 = 0;
                while (true) {
                    if (i2 >= length) {
                        break;
                    } else if (replaySubscriptionArr[i2] == replaySubscription) {
                        i = i2;
                        break;
                    } else {
                        i2++;
                    }
                }
                if (i >= 0) {
                    if (length == 1) {
                        replaySubscriptionArr2 = EMPTY;
                    } else {
                        ReplaySubscription<T>[] replaySubscriptionArr3 = new ReplaySubscription[(length - 1)];
                        System.arraycopy(replaySubscriptionArr, 0, replaySubscriptionArr3, 0, i);
                        System.arraycopy(replaySubscriptionArr, i + 1, replaySubscriptionArr3, i, (length - i) - 1);
                        replaySubscriptionArr2 = replaySubscriptionArr3;
                    }
                } else {
                    return;
                }
            } else {
                return;
            }
        } while (!this.subscribers.compareAndSet(replaySubscriptionArr, replaySubscriptionArr2));
    }

    /* access modifiers changed from: package-private */
    public int size() {
        return this.buffer.size();
    }

    /* access modifiers changed from: protected */
    @Override // io.reactivex.Flowable
    public void subscribeActual(Subscriber<? super T> subscriber) {
        ReplaySubscription<T> replaySubscription = new ReplaySubscription<>(subscriber, this);
        subscriber.onSubscribe(replaySubscription);
        if (!add(replaySubscription) || !replaySubscription.cancelled) {
            this.buffer.replay(replaySubscription);
        } else {
            remove(replaySubscription);
        }
    }

    /* access modifiers changed from: package-private */
    public int subscriberCount() {
        return this.subscribers.get().length;
    }
}
