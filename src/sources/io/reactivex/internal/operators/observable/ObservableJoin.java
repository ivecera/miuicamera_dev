package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.operators.observable.ObservableGroupJoin;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public final class ObservableJoin<TLeft, TRight, TLeftEnd, TRightEnd, R> extends AbstractObservableWithUpstream<TLeft, R> {
    final Function<? super TLeft, ? extends ObservableSource<TLeftEnd>> leftEnd;
    final ObservableSource<? extends TRight> other;
    final BiFunction<? super TLeft, ? super TRight, ? extends R> resultSelector;
    final Function<? super TRight, ? extends ObservableSource<TRightEnd>> rightEnd;

    static final class JoinDisposable<TLeft, TRight, TLeftEnd, TRightEnd, R> extends AtomicInteger implements Disposable, ObservableGroupJoin.JoinSupport {
        static final Integer LEFT_CLOSE = 3;
        static final Integer LEFT_VALUE = 1;
        static final Integer RIGHT_CLOSE = 4;
        static final Integer RIGHT_VALUE = 2;
        private static final long serialVersionUID = -6071216598687999801L;
        final AtomicInteger active;
        final Observer<? super R> actual;
        volatile boolean cancelled;
        final CompositeDisposable disposables = new CompositeDisposable();
        final AtomicReference<Throwable> error = new AtomicReference<>();
        final Function<? super TLeft, ? extends ObservableSource<TLeftEnd>> leftEnd;
        int leftIndex;
        final Map<Integer, TLeft> lefts = new LinkedHashMap();
        final SpscLinkedArrayQueue<Object> queue = new SpscLinkedArrayQueue<>(Observable.bufferSize());
        final BiFunction<? super TLeft, ? super TRight, ? extends R> resultSelector;
        final Function<? super TRight, ? extends ObservableSource<TRightEnd>> rightEnd;
        int rightIndex;
        final Map<Integer, TRight> rights = new LinkedHashMap();

        JoinDisposable(Observer<? super R> observer, Function<? super TLeft, ? extends ObservableSource<TLeftEnd>> function, Function<? super TRight, ? extends ObservableSource<TRightEnd>> function2, BiFunction<? super TLeft, ? super TRight, ? extends R> biFunction) {
            this.actual = observer;
            this.leftEnd = function;
            this.rightEnd = function2;
            this.resultSelector = biFunction;
            this.active = new AtomicInteger(2);
        }

        /* access modifiers changed from: package-private */
        public void cancelAll() {
            this.disposables.dispose();
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            if (!this.cancelled) {
                this.cancelled = true;
                cancelAll();
                if (getAndIncrement() == 0) {
                    this.queue.clear();
                }
            }
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v7, resolved type: ?} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v5, resolved type: io.reactivex.functions.Function<? super TRight, ? extends io.reactivex.ObservableSource<TRightEnd>>} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v8, resolved type: io.reactivex.functions.BiFunction<? super TLeft, ? super TRight, ? extends R>} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v9, resolved type: ? super R} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v12, resolved type: io.reactivex.functions.Function<? super TLeft, ? extends io.reactivex.ObservableSource<TLeftEnd>>} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v12, resolved type: io.reactivex.functions.BiFunction<? super TLeft, ? super TRight, ? extends R>} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v17, resolved type: ? super R} */
        /* JADX WARN: Multi-variable type inference failed */
        /* access modifiers changed from: package-private */
        public void drain() {
            if (getAndIncrement() == 0) {
                SpscLinkedArrayQueue<?> spscLinkedArrayQueue = this.queue;
                Observer<? super R> observer = this.actual;
                int i = 1;
                while (!this.cancelled) {
                    if (this.error.get() != null) {
                        spscLinkedArrayQueue.clear();
                        cancelAll();
                        errorAll(observer);
                        return;
                    }
                    boolean z = this.active.get() == 0;
                    Integer num = (Integer) spscLinkedArrayQueue.poll();
                    boolean z2 = num == null;
                    if (z && z2) {
                        this.lefts.clear();
                        this.rights.clear();
                        this.disposables.dispose();
                        observer.onComplete();
                        return;
                    } else if (z2) {
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
                                ObjectHelper.requireNonNull(apply, "The leftEnd returned a null ObservableSource");
                                ObservableSource observableSource = (ObservableSource) apply;
                                ObservableGroupJoin.LeftRightEndObserver leftRightEndObserver = new ObservableGroupJoin.LeftRightEndObserver(this, true, i2);
                                this.disposables.add(leftRightEndObserver);
                                observableSource.subscribe(leftRightEndObserver);
                                if (this.error.get() != null) {
                                    spscLinkedArrayQueue.clear();
                                    cancelAll();
                                    errorAll(observer);
                                    return;
                                }
                                for (TRight tright : this.rights.values()) {
                                    try {
                                        Object apply2 = this.resultSelector.apply(poll, tright);
                                        ObjectHelper.requireNonNull(apply2, "The resultSelector returned a null value");
                                        observer.onNext(apply2);
                                    } catch (Throwable th) {
                                        fail(th, observer, spscLinkedArrayQueue);
                                        return;
                                    }
                                }
                            } catch (Throwable th2) {
                                fail(th2, observer, spscLinkedArrayQueue);
                                return;
                            }
                        } else if (num == RIGHT_VALUE) {
                            int i3 = this.rightIndex;
                            this.rightIndex = i3 + 1;
                            this.rights.put(Integer.valueOf(i3), poll);
                            try {
                                Object apply3 = this.rightEnd.apply(poll);
                                ObjectHelper.requireNonNull(apply3, "The rightEnd returned a null ObservableSource");
                                ObservableSource observableSource2 = (ObservableSource) apply3;
                                ObservableGroupJoin.LeftRightEndObserver leftRightEndObserver2 = new ObservableGroupJoin.LeftRightEndObserver(this, false, i3);
                                this.disposables.add(leftRightEndObserver2);
                                observableSource2.subscribe(leftRightEndObserver2);
                                if (this.error.get() != null) {
                                    spscLinkedArrayQueue.clear();
                                    cancelAll();
                                    errorAll(observer);
                                    return;
                                }
                                for (TLeft tleft : this.lefts.values()) {
                                    try {
                                        Object apply4 = this.resultSelector.apply(tleft, poll);
                                        ObjectHelper.requireNonNull(apply4, "The resultSelector returned a null value");
                                        observer.onNext(apply4);
                                    } catch (Throwable th3) {
                                        fail(th3, observer, spscLinkedArrayQueue);
                                        return;
                                    }
                                }
                            } catch (Throwable th4) {
                                fail(th4, observer, spscLinkedArrayQueue);
                                return;
                            }
                        } else if (num == LEFT_CLOSE) {
                            ObservableGroupJoin.LeftRightEndObserver leftRightEndObserver3 = (ObservableGroupJoin.LeftRightEndObserver) poll;
                            this.lefts.remove(Integer.valueOf(leftRightEndObserver3.index));
                            this.disposables.remove(leftRightEndObserver3);
                        } else {
                            ObservableGroupJoin.LeftRightEndObserver leftRightEndObserver4 = (ObservableGroupJoin.LeftRightEndObserver) poll;
                            this.rights.remove(Integer.valueOf(leftRightEndObserver4.index));
                            this.disposables.remove(leftRightEndObserver4);
                        }
                    }
                }
                spscLinkedArrayQueue.clear();
            }
        }

        /* access modifiers changed from: package-private */
        public void errorAll(Observer<?> observer) {
            Throwable terminate = ExceptionHelper.terminate(this.error);
            this.lefts.clear();
            this.rights.clear();
            observer.onError(terminate);
        }

        /* access modifiers changed from: package-private */
        public void fail(Throwable th, Observer<?> observer, SpscLinkedArrayQueue<?> spscLinkedArrayQueue) {
            Exceptions.throwIfFatal(th);
            ExceptionHelper.addThrowable(this.error, th);
            spscLinkedArrayQueue.clear();
            cancelAll();
            errorAll(observer);
        }

        @Override // io.reactivex.internal.operators.observable.ObservableGroupJoin.JoinSupport
        public void innerClose(boolean z, ObservableGroupJoin.LeftRightEndObserver leftRightEndObserver) {
            synchronized (this) {
                this.queue.offer(z ? LEFT_CLOSE : RIGHT_CLOSE, leftRightEndObserver);
            }
            drain();
        }

        @Override // io.reactivex.internal.operators.observable.ObservableGroupJoin.JoinSupport
        public void innerCloseError(Throwable th) {
            if (ExceptionHelper.addThrowable(this.error, th)) {
                drain();
            } else {
                RxJavaPlugins.onError(th);
            }
        }

        @Override // io.reactivex.internal.operators.observable.ObservableGroupJoin.JoinSupport
        public void innerComplete(ObservableGroupJoin.LeftRightObserver leftRightObserver) {
            this.disposables.delete(leftRightObserver);
            this.active.decrementAndGet();
            drain();
        }

        @Override // io.reactivex.internal.operators.observable.ObservableGroupJoin.JoinSupport
        public void innerError(Throwable th) {
            if (ExceptionHelper.addThrowable(this.error, th)) {
                this.active.decrementAndGet();
                drain();
                return;
            }
            RxJavaPlugins.onError(th);
        }

        @Override // io.reactivex.internal.operators.observable.ObservableGroupJoin.JoinSupport
        public void innerValue(boolean z, Object obj) {
            synchronized (this) {
                this.queue.offer(z ? LEFT_VALUE : RIGHT_VALUE, obj);
            }
            drain();
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.cancelled;
        }
    }

    public ObservableJoin(ObservableSource<TLeft> observableSource, ObservableSource<? extends TRight> observableSource2, Function<? super TLeft, ? extends ObservableSource<TLeftEnd>> function, Function<? super TRight, ? extends ObservableSource<TRightEnd>> function2, BiFunction<? super TLeft, ? super TRight, ? extends R> biFunction) {
        super(observableSource);
        this.other = observableSource2;
        this.leftEnd = function;
        this.rightEnd = function2;
        this.resultSelector = biFunction;
    }

    /* access modifiers changed from: protected */
    @Override // io.reactivex.Observable
    public void subscribeActual(Observer<? super R> observer) {
        JoinDisposable joinDisposable = new JoinDisposable(observer, this.leftEnd, this.rightEnd, this.resultSelector);
        observer.onSubscribe(joinDisposable);
        ObservableGroupJoin.LeftRightObserver leftRightObserver = new ObservableGroupJoin.LeftRightObserver(joinDisposable, true);
        joinDisposable.disposables.add(leftRightObserver);
        ObservableGroupJoin.LeftRightObserver leftRightObserver2 = new ObservableGroupJoin.LeftRightObserver(joinDisposable, false);
        joinDisposable.disposables.add(leftRightObserver2);
        ((AbstractObservableWithUpstream) this).source.subscribe(leftRightObserver);
        this.other.subscribe(leftRightObserver2);
    }
}
