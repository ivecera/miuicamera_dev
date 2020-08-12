package io.reactivex;

import io.reactivex.annotations.BackpressureKind;
import io.reactivex.annotations.BackpressureSupport;
import io.reactivex.annotations.Beta;
import io.reactivex.annotations.CheckReturnValue;
import io.reactivex.annotations.Experimental;
import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.SchedulerSupport;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.flowables.ConnectableFlowable;
import io.reactivex.flowables.GroupedFlowable;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.BiPredicate;
import io.reactivex.functions.BooleanSupplier;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Function3;
import io.reactivex.functions.Function4;
import io.reactivex.functions.Function5;
import io.reactivex.functions.Function6;
import io.reactivex.functions.Function7;
import io.reactivex.functions.Function8;
import io.reactivex.functions.Function9;
import io.reactivex.functions.LongConsumer;
import io.reactivex.functions.Predicate;
import io.reactivex.internal.functions.Functions;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.ScalarCallable;
import io.reactivex.internal.operators.flowable.BlockingFlowableIterable;
import io.reactivex.internal.operators.flowable.BlockingFlowableLatest;
import io.reactivex.internal.operators.flowable.BlockingFlowableMostRecent;
import io.reactivex.internal.operators.flowable.BlockingFlowableNext;
import io.reactivex.internal.operators.flowable.FlowableAllSingle;
import io.reactivex.internal.operators.flowable.FlowableAmb;
import io.reactivex.internal.operators.flowable.FlowableAnySingle;
import io.reactivex.internal.operators.flowable.FlowableBlockingSubscribe;
import io.reactivex.internal.operators.flowable.FlowableBuffer;
import io.reactivex.internal.operators.flowable.FlowableBufferBoundary;
import io.reactivex.internal.operators.flowable.FlowableBufferBoundarySupplier;
import io.reactivex.internal.operators.flowable.FlowableBufferExactBoundary;
import io.reactivex.internal.operators.flowable.FlowableBufferTimed;
import io.reactivex.internal.operators.flowable.FlowableCache;
import io.reactivex.internal.operators.flowable.FlowableCollectSingle;
import io.reactivex.internal.operators.flowable.FlowableCombineLatest;
import io.reactivex.internal.operators.flowable.FlowableConcatArray;
import io.reactivex.internal.operators.flowable.FlowableConcatMap;
import io.reactivex.internal.operators.flowable.FlowableConcatMapEager;
import io.reactivex.internal.operators.flowable.FlowableConcatMapEagerPublisher;
import io.reactivex.internal.operators.flowable.FlowableCountSingle;
import io.reactivex.internal.operators.flowable.FlowableCreate;
import io.reactivex.internal.operators.flowable.FlowableDebounce;
import io.reactivex.internal.operators.flowable.FlowableDebounceTimed;
import io.reactivex.internal.operators.flowable.FlowableDefer;
import io.reactivex.internal.operators.flowable.FlowableDelay;
import io.reactivex.internal.operators.flowable.FlowableDelaySubscriptionOther;
import io.reactivex.internal.operators.flowable.FlowableDematerialize;
import io.reactivex.internal.operators.flowable.FlowableDetach;
import io.reactivex.internal.operators.flowable.FlowableDistinct;
import io.reactivex.internal.operators.flowable.FlowableDistinctUntilChanged;
import io.reactivex.internal.operators.flowable.FlowableDoAfterNext;
import io.reactivex.internal.operators.flowable.FlowableDoFinally;
import io.reactivex.internal.operators.flowable.FlowableDoOnEach;
import io.reactivex.internal.operators.flowable.FlowableDoOnLifecycle;
import io.reactivex.internal.operators.flowable.FlowableElementAtMaybe;
import io.reactivex.internal.operators.flowable.FlowableElementAtSingle;
import io.reactivex.internal.operators.flowable.FlowableEmpty;
import io.reactivex.internal.operators.flowable.FlowableError;
import io.reactivex.internal.operators.flowable.FlowableFilter;
import io.reactivex.internal.operators.flowable.FlowableFlatMap;
import io.reactivex.internal.operators.flowable.FlowableFlatMapCompletableCompletable;
import io.reactivex.internal.operators.flowable.FlowableFlatMapMaybe;
import io.reactivex.internal.operators.flowable.FlowableFlatMapSingle;
import io.reactivex.internal.operators.flowable.FlowableFlattenIterable;
import io.reactivex.internal.operators.flowable.FlowableFromArray;
import io.reactivex.internal.operators.flowable.FlowableFromCallable;
import io.reactivex.internal.operators.flowable.FlowableFromFuture;
import io.reactivex.internal.operators.flowable.FlowableFromIterable;
import io.reactivex.internal.operators.flowable.FlowableFromPublisher;
import io.reactivex.internal.operators.flowable.FlowableGenerate;
import io.reactivex.internal.operators.flowable.FlowableGroupBy;
import io.reactivex.internal.operators.flowable.FlowableGroupJoin;
import io.reactivex.internal.operators.flowable.FlowableHide;
import io.reactivex.internal.operators.flowable.FlowableIgnoreElements;
import io.reactivex.internal.operators.flowable.FlowableIgnoreElementsCompletable;
import io.reactivex.internal.operators.flowable.FlowableInternalHelper;
import io.reactivex.internal.operators.flowable.FlowableInterval;
import io.reactivex.internal.operators.flowable.FlowableIntervalRange;
import io.reactivex.internal.operators.flowable.FlowableJoin;
import io.reactivex.internal.operators.flowable.FlowableJust;
import io.reactivex.internal.operators.flowable.FlowableLastMaybe;
import io.reactivex.internal.operators.flowable.FlowableLastSingle;
import io.reactivex.internal.operators.flowable.FlowableLift;
import io.reactivex.internal.operators.flowable.FlowableLimit;
import io.reactivex.internal.operators.flowable.FlowableMap;
import io.reactivex.internal.operators.flowable.FlowableMapNotification;
import io.reactivex.internal.operators.flowable.FlowableMaterialize;
import io.reactivex.internal.operators.flowable.FlowableNever;
import io.reactivex.internal.operators.flowable.FlowableObserveOn;
import io.reactivex.internal.operators.flowable.FlowableOnBackpressureBuffer;
import io.reactivex.internal.operators.flowable.FlowableOnBackpressureBufferStrategy;
import io.reactivex.internal.operators.flowable.FlowableOnBackpressureDrop;
import io.reactivex.internal.operators.flowable.FlowableOnBackpressureLatest;
import io.reactivex.internal.operators.flowable.FlowableOnErrorNext;
import io.reactivex.internal.operators.flowable.FlowableOnErrorReturn;
import io.reactivex.internal.operators.flowable.FlowablePublish;
import io.reactivex.internal.operators.flowable.FlowablePublishMulticast;
import io.reactivex.internal.operators.flowable.FlowableRange;
import io.reactivex.internal.operators.flowable.FlowableRangeLong;
import io.reactivex.internal.operators.flowable.FlowableReduceMaybe;
import io.reactivex.internal.operators.flowable.FlowableReduceSeedSingle;
import io.reactivex.internal.operators.flowable.FlowableReduceWithSingle;
import io.reactivex.internal.operators.flowable.FlowableRepeat;
import io.reactivex.internal.operators.flowable.FlowableRepeatUntil;
import io.reactivex.internal.operators.flowable.FlowableRepeatWhen;
import io.reactivex.internal.operators.flowable.FlowableReplay;
import io.reactivex.internal.operators.flowable.FlowableRetryBiPredicate;
import io.reactivex.internal.operators.flowable.FlowableRetryPredicate;
import io.reactivex.internal.operators.flowable.FlowableRetryWhen;
import io.reactivex.internal.operators.flowable.FlowableSamplePublisher;
import io.reactivex.internal.operators.flowable.FlowableSampleTimed;
import io.reactivex.internal.operators.flowable.FlowableScalarXMap;
import io.reactivex.internal.operators.flowable.FlowableScan;
import io.reactivex.internal.operators.flowable.FlowableScanSeed;
import io.reactivex.internal.operators.flowable.FlowableSequenceEqualSingle;
import io.reactivex.internal.operators.flowable.FlowableSerialized;
import io.reactivex.internal.operators.flowable.FlowableSingleMaybe;
import io.reactivex.internal.operators.flowable.FlowableSingleSingle;
import io.reactivex.internal.operators.flowable.FlowableSkip;
import io.reactivex.internal.operators.flowable.FlowableSkipLast;
import io.reactivex.internal.operators.flowable.FlowableSkipLastTimed;
import io.reactivex.internal.operators.flowable.FlowableSkipUntil;
import io.reactivex.internal.operators.flowable.FlowableSkipWhile;
import io.reactivex.internal.operators.flowable.FlowableSubscribeOn;
import io.reactivex.internal.operators.flowable.FlowableSwitchIfEmpty;
import io.reactivex.internal.operators.flowable.FlowableSwitchMap;
import io.reactivex.internal.operators.flowable.FlowableTake;
import io.reactivex.internal.operators.flowable.FlowableTakeLast;
import io.reactivex.internal.operators.flowable.FlowableTakeLastOne;
import io.reactivex.internal.operators.flowable.FlowableTakeLastTimed;
import io.reactivex.internal.operators.flowable.FlowableTakeUntil;
import io.reactivex.internal.operators.flowable.FlowableTakeUntilPredicate;
import io.reactivex.internal.operators.flowable.FlowableTakeWhile;
import io.reactivex.internal.operators.flowable.FlowableThrottleFirstTimed;
import io.reactivex.internal.operators.flowable.FlowableTimeInterval;
import io.reactivex.internal.operators.flowable.FlowableTimeout;
import io.reactivex.internal.operators.flowable.FlowableTimeoutTimed;
import io.reactivex.internal.operators.flowable.FlowableTimer;
import io.reactivex.internal.operators.flowable.FlowableToListSingle;
import io.reactivex.internal.operators.flowable.FlowableUnsubscribeOn;
import io.reactivex.internal.operators.flowable.FlowableUsing;
import io.reactivex.internal.operators.flowable.FlowableWindow;
import io.reactivex.internal.operators.flowable.FlowableWindowBoundary;
import io.reactivex.internal.operators.flowable.FlowableWindowBoundarySelector;
import io.reactivex.internal.operators.flowable.FlowableWindowBoundarySupplier;
import io.reactivex.internal.operators.flowable.FlowableWindowTimed;
import io.reactivex.internal.operators.flowable.FlowableWithLatestFrom;
import io.reactivex.internal.operators.flowable.FlowableWithLatestFromMany;
import io.reactivex.internal.operators.flowable.FlowableZip;
import io.reactivex.internal.operators.flowable.FlowableZipIterable;
import io.reactivex.internal.operators.observable.ObservableFromPublisher;
import io.reactivex.internal.schedulers.ImmediateThinScheduler;
import io.reactivex.internal.subscribers.BlockingFirstSubscriber;
import io.reactivex.internal.subscribers.BlockingLastSubscriber;
import io.reactivex.internal.subscribers.ForEachWhileSubscriber;
import io.reactivex.internal.subscribers.FutureSubscriber;
import io.reactivex.internal.subscribers.LambdaSubscriber;
import io.reactivex.internal.subscribers.StrictSubscriber;
import io.reactivex.internal.util.ArrayListSupplier;
import io.reactivex.internal.util.ErrorMode;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.internal.util.HashMapSupplier;
import io.reactivex.parallel.ParallelFlowable;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.schedulers.Timed;
import io.reactivex.subscribers.SafeSubscriber;
import io.reactivex.subscribers.TestSubscriber;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public abstract class Flowable<T> implements Publisher<T> {
    static final int BUFFER_SIZE = Math.max(1, Integer.getInteger("rx2.buffer-size", 128).intValue());

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @CheckReturnValue
    public static <T> Flowable<T> amb(Iterable<? extends Publisher<? extends T>> iterable) {
        ObjectHelper.requireNonNull(iterable, "sources is null");
        return RxJavaPlugins.onAssembly(new FlowableAmb(null, iterable));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @CheckReturnValue
    public static <T> Flowable<T> ambArray(Publisher<? extends T>... publisherArr) {
        ObjectHelper.requireNonNull(publisherArr, "sources is null");
        int length = publisherArr.length;
        return length == 0 ? empty() : length == 1 ? fromPublisher(publisherArr[0]) : RxJavaPlugins.onAssembly(new FlowableAmb(publisherArr, null));
    }

    public static int bufferSize() {
        return BUFFER_SIZE;
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public static <T, R> Flowable<R> combineLatest(Function<? super Object[], ? extends R> function, Publisher<? extends T>... publisherArr) {
        return combineLatest(publisherArr, function, bufferSize());
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public static <T, R> Flowable<R> combineLatest(Iterable<? extends Publisher<? extends T>> iterable, Function<? super Object[], ? extends R> function) {
        return combineLatest(iterable, function, bufferSize());
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public static <T, R> Flowable<R> combineLatest(Iterable<? extends Publisher<? extends T>> iterable, Function<? super Object[], ? extends R> function, int i) {
        ObjectHelper.requireNonNull(iterable, "sources is null");
        ObjectHelper.requireNonNull(function, "combiner is null");
        ObjectHelper.verifyPositive(i, "bufferSize");
        return RxJavaPlugins.onAssembly(new FlowableCombineLatest((Iterable) iterable, (Function) function, i, false));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public static <T1, T2, R> Flowable<R> combineLatest(Publisher<? extends T1> publisher, Publisher<? extends T2> publisher2, BiFunction<? super T1, ? super T2, ? extends R> biFunction) {
        ObjectHelper.requireNonNull(publisher, "source1 is null");
        ObjectHelper.requireNonNull(publisher2, "source2 is null");
        return combineLatest(Functions.toFunction(biFunction), publisher, publisher2);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public static <T1, T2, T3, R> Flowable<R> combineLatest(Publisher<? extends T1> publisher, Publisher<? extends T2> publisher2, Publisher<? extends T3> publisher3, Function3<? super T1, ? super T2, ? super T3, ? extends R> function3) {
        ObjectHelper.requireNonNull(publisher, "source1 is null");
        ObjectHelper.requireNonNull(publisher2, "source2 is null");
        ObjectHelper.requireNonNull(publisher3, "source3 is null");
        return combineLatest(Functions.toFunction(function3), publisher, publisher2, publisher3);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public static <T1, T2, T3, T4, R> Flowable<R> combineLatest(Publisher<? extends T1> publisher, Publisher<? extends T2> publisher2, Publisher<? extends T3> publisher3, Publisher<? extends T4> publisher4, Function4<? super T1, ? super T2, ? super T3, ? super T4, ? extends R> function4) {
        ObjectHelper.requireNonNull(publisher, "source1 is null");
        ObjectHelper.requireNonNull(publisher2, "source2 is null");
        ObjectHelper.requireNonNull(publisher3, "source3 is null");
        ObjectHelper.requireNonNull(publisher4, "source4 is null");
        return combineLatest(Functions.toFunction(function4), publisher, publisher2, publisher3, publisher4);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public static <T1, T2, T3, T4, T5, R> Flowable<R> combineLatest(Publisher<? extends T1> publisher, Publisher<? extends T2> publisher2, Publisher<? extends T3> publisher3, Publisher<? extends T4> publisher4, Publisher<? extends T5> publisher5, Function5<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? extends R> function5) {
        ObjectHelper.requireNonNull(publisher, "source1 is null");
        ObjectHelper.requireNonNull(publisher2, "source2 is null");
        ObjectHelper.requireNonNull(publisher3, "source3 is null");
        ObjectHelper.requireNonNull(publisher4, "source4 is null");
        ObjectHelper.requireNonNull(publisher5, "source5 is null");
        return combineLatest(Functions.toFunction(function5), publisher, publisher2, publisher3, publisher4, publisher5);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public static <T1, T2, T3, T4, T5, T6, R> Flowable<R> combineLatest(Publisher<? extends T1> publisher, Publisher<? extends T2> publisher2, Publisher<? extends T3> publisher3, Publisher<? extends T4> publisher4, Publisher<? extends T5> publisher5, Publisher<? extends T6> publisher6, Function6<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? extends R> function6) {
        ObjectHelper.requireNonNull(publisher, "source1 is null");
        ObjectHelper.requireNonNull(publisher2, "source2 is null");
        ObjectHelper.requireNonNull(publisher3, "source3 is null");
        ObjectHelper.requireNonNull(publisher4, "source4 is null");
        ObjectHelper.requireNonNull(publisher5, "source5 is null");
        ObjectHelper.requireNonNull(publisher6, "source6 is null");
        return combineLatest(Functions.toFunction(function6), publisher, publisher2, publisher3, publisher4, publisher5, publisher6);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public static <T1, T2, T3, T4, T5, T6, T7, R> Flowable<R> combineLatest(Publisher<? extends T1> publisher, Publisher<? extends T2> publisher2, Publisher<? extends T3> publisher3, Publisher<? extends T4> publisher4, Publisher<? extends T5> publisher5, Publisher<? extends T6> publisher6, Publisher<? extends T7> publisher7, Function7<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? extends R> function7) {
        ObjectHelper.requireNonNull(publisher, "source1 is null");
        ObjectHelper.requireNonNull(publisher2, "source2 is null");
        ObjectHelper.requireNonNull(publisher3, "source3 is null");
        ObjectHelper.requireNonNull(publisher4, "source4 is null");
        ObjectHelper.requireNonNull(publisher5, "source5 is null");
        ObjectHelper.requireNonNull(publisher6, "source6 is null");
        ObjectHelper.requireNonNull(publisher7, "source7 is null");
        return combineLatest(Functions.toFunction(function7), publisher, publisher2, publisher3, publisher4, publisher5, publisher6, publisher7);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public static <T1, T2, T3, T4, T5, T6, T7, T8, R> Flowable<R> combineLatest(Publisher<? extends T1> publisher, Publisher<? extends T2> publisher2, Publisher<? extends T3> publisher3, Publisher<? extends T4> publisher4, Publisher<? extends T5> publisher5, Publisher<? extends T6> publisher6, Publisher<? extends T7> publisher7, Publisher<? extends T8> publisher8, Function8<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? extends R> function8) {
        ObjectHelper.requireNonNull(publisher, "source1 is null");
        ObjectHelper.requireNonNull(publisher2, "source2 is null");
        ObjectHelper.requireNonNull(publisher3, "source3 is null");
        ObjectHelper.requireNonNull(publisher4, "source4 is null");
        ObjectHelper.requireNonNull(publisher5, "source5 is null");
        ObjectHelper.requireNonNull(publisher6, "source6 is null");
        ObjectHelper.requireNonNull(publisher7, "source7 is null");
        ObjectHelper.requireNonNull(publisher8, "source8 is null");
        return combineLatest(Functions.toFunction(function8), publisher, publisher2, publisher3, publisher4, publisher5, publisher6, publisher7, publisher8);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, R> Flowable<R> combineLatest(Publisher<? extends T1> publisher, Publisher<? extends T2> publisher2, Publisher<? extends T3> publisher3, Publisher<? extends T4> publisher4, Publisher<? extends T5> publisher5, Publisher<? extends T6> publisher6, Publisher<? extends T7> publisher7, Publisher<? extends T8> publisher8, Publisher<? extends T9> publisher9, Function9<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? super T9, ? extends R> function9) {
        ObjectHelper.requireNonNull(publisher, "source1 is null");
        ObjectHelper.requireNonNull(publisher2, "source2 is null");
        ObjectHelper.requireNonNull(publisher3, "source3 is null");
        ObjectHelper.requireNonNull(publisher4, "source4 is null");
        ObjectHelper.requireNonNull(publisher5, "source5 is null");
        ObjectHelper.requireNonNull(publisher6, "source6 is null");
        ObjectHelper.requireNonNull(publisher7, "source7 is null");
        ObjectHelper.requireNonNull(publisher8, "source8 is null");
        ObjectHelper.requireNonNull(publisher9, "source9 is null");
        return combineLatest(Functions.toFunction(function9), publisher, publisher2, publisher3, publisher4, publisher5, publisher6, publisher7, publisher8, publisher9);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public static <T, R> Flowable<R> combineLatest(Publisher<? extends T>[] publisherArr, Function<? super Object[], ? extends R> function) {
        return combineLatest(publisherArr, function, bufferSize());
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public static <T, R> Flowable<R> combineLatest(Publisher<? extends T>[] publisherArr, Function<? super Object[], ? extends R> function, int i) {
        ObjectHelper.requireNonNull(publisherArr, "sources is null");
        if (publisherArr.length == 0) {
            return empty();
        }
        ObjectHelper.requireNonNull(function, "combiner is null");
        ObjectHelper.verifyPositive(i, "bufferSize");
        return RxJavaPlugins.onAssembly(new FlowableCombineLatest((Publisher[]) publisherArr, (Function) function, i, false));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public static <T, R> Flowable<R> combineLatestDelayError(Function<? super Object[], ? extends R> function, int i, Publisher<? extends T>... publisherArr) {
        return combineLatestDelayError(publisherArr, function, i);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public static <T, R> Flowable<R> combineLatestDelayError(Function<? super Object[], ? extends R> function, Publisher<? extends T>... publisherArr) {
        return combineLatestDelayError(publisherArr, function, bufferSize());
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public static <T, R> Flowable<R> combineLatestDelayError(Iterable<? extends Publisher<? extends T>> iterable, Function<? super Object[], ? extends R> function) {
        return combineLatestDelayError(iterable, function, bufferSize());
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public static <T, R> Flowable<R> combineLatestDelayError(Iterable<? extends Publisher<? extends T>> iterable, Function<? super Object[], ? extends R> function, int i) {
        ObjectHelper.requireNonNull(iterable, "sources is null");
        ObjectHelper.requireNonNull(function, "combiner is null");
        ObjectHelper.verifyPositive(i, "bufferSize");
        return RxJavaPlugins.onAssembly(new FlowableCombineLatest((Iterable) iterable, (Function) function, i, true));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public static <T, R> Flowable<R> combineLatestDelayError(Publisher<? extends T>[] publisherArr, Function<? super Object[], ? extends R> function) {
        return combineLatestDelayError(publisherArr, function, bufferSize());
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public static <T, R> Flowable<R> combineLatestDelayError(Publisher<? extends T>[] publisherArr, Function<? super Object[], ? extends R> function, int i) {
        ObjectHelper.requireNonNull(publisherArr, "sources is null");
        ObjectHelper.requireNonNull(function, "combiner is null");
        ObjectHelper.verifyPositive(i, "bufferSize");
        return publisherArr.length == 0 ? empty() : RxJavaPlugins.onAssembly(new FlowableCombineLatest((Publisher[]) publisherArr, (Function) function, i, true));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public static <T> Flowable<T> concat(Iterable<? extends Publisher<? extends T>> iterable) {
        ObjectHelper.requireNonNull(iterable, "sources is null");
        return fromIterable(iterable).concatMapDelayError(Functions.identity(), 2, false);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public static <T> Flowable<T> concat(Publisher<? extends Publisher<? extends T>> publisher) {
        return concat(publisher, bufferSize());
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public static <T> Flowable<T> concat(Publisher<? extends Publisher<? extends T>> publisher, int i) {
        return fromPublisher(publisher).concatMap(Functions.identity(), i);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public static <T> Flowable<T> concat(Publisher<? extends T> publisher, Publisher<? extends T> publisher2) {
        ObjectHelper.requireNonNull(publisher, "source1 is null");
        ObjectHelper.requireNonNull(publisher2, "source2 is null");
        return concatArray(publisher, publisher2);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public static <T> Flowable<T> concat(Publisher<? extends T> publisher, Publisher<? extends T> publisher2, Publisher<? extends T> publisher3) {
        ObjectHelper.requireNonNull(publisher, "source1 is null");
        ObjectHelper.requireNonNull(publisher2, "source2 is null");
        ObjectHelper.requireNonNull(publisher3, "source3 is null");
        return concatArray(publisher, publisher2, publisher3);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public static <T> Flowable<T> concat(Publisher<? extends T> publisher, Publisher<? extends T> publisher2, Publisher<? extends T> publisher3, Publisher<? extends T> publisher4) {
        ObjectHelper.requireNonNull(publisher, "source1 is null");
        ObjectHelper.requireNonNull(publisher2, "source2 is null");
        ObjectHelper.requireNonNull(publisher3, "source3 is null");
        ObjectHelper.requireNonNull(publisher4, "source4 is null");
        return concatArray(publisher, publisher2, publisher3, publisher4);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public static <T> Flowable<T> concatArray(Publisher<? extends T>... publisherArr) {
        return publisherArr.length == 0 ? empty() : publisherArr.length == 1 ? fromPublisher(publisherArr[0]) : RxJavaPlugins.onAssembly(new FlowableConcatArray(publisherArr, false));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public static <T> Flowable<T> concatArrayDelayError(Publisher<? extends T>... publisherArr) {
        return publisherArr.length == 0 ? empty() : publisherArr.length == 1 ? fromPublisher(publisherArr[0]) : RxJavaPlugins.onAssembly(new FlowableConcatArray(publisherArr, true));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public static <T> Flowable<T> concatArrayEager(int i, int i2, Publisher<? extends T>... publisherArr) {
        ObjectHelper.requireNonNull(publisherArr, "sources is null");
        ObjectHelper.verifyPositive(i, "maxConcurrency");
        ObjectHelper.verifyPositive(i2, "prefetch");
        return RxJavaPlugins.onAssembly(new FlowableConcatMapEager(new FlowableFromArray(publisherArr), Functions.identity(), i, i2, ErrorMode.IMMEDIATE));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public static <T> Flowable<T> concatArrayEager(Publisher<? extends T>... publisherArr) {
        return concatArrayEager(bufferSize(), bufferSize(), publisherArr);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public static <T> Flowable<T> concatDelayError(Iterable<? extends Publisher<? extends T>> iterable) {
        ObjectHelper.requireNonNull(iterable, "sources is null");
        return fromIterable(iterable).concatMapDelayError(Functions.identity());
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public static <T> Flowable<T> concatDelayError(Publisher<? extends Publisher<? extends T>> publisher) {
        return concatDelayError(publisher, bufferSize(), true);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public static <T> Flowable<T> concatDelayError(Publisher<? extends Publisher<? extends T>> publisher, int i, boolean z) {
        return fromPublisher(publisher).concatMapDelayError(Functions.identity(), i, z);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public static <T> Flowable<T> concatEager(Iterable<? extends Publisher<? extends T>> iterable) {
        return concatEager(iterable, bufferSize(), bufferSize());
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public static <T> Flowable<T> concatEager(Iterable<? extends Publisher<? extends T>> iterable, int i, int i2) {
        ObjectHelper.requireNonNull(iterable, "sources is null");
        ObjectHelper.verifyPositive(i, "maxConcurrency");
        ObjectHelper.verifyPositive(i2, "prefetch");
        return RxJavaPlugins.onAssembly(new FlowableConcatMapEager(new FlowableFromIterable(iterable), Functions.identity(), i, i2, ErrorMode.IMMEDIATE));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public static <T> Flowable<T> concatEager(Publisher<? extends Publisher<? extends T>> publisher) {
        return concatEager(publisher, bufferSize(), bufferSize());
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public static <T> Flowable<T> concatEager(Publisher<? extends Publisher<? extends T>> publisher, int i, int i2) {
        ObjectHelper.requireNonNull(publisher, "sources is null");
        ObjectHelper.verifyPositive(i, "maxConcurrency");
        ObjectHelper.verifyPositive(i2, "prefetch");
        return RxJavaPlugins.onAssembly(new FlowableConcatMapEagerPublisher(publisher, Functions.identity(), i, i2, ErrorMode.IMMEDIATE));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.SPECIAL)
    @CheckReturnValue
    public static <T> Flowable<T> create(FlowableOnSubscribe<T> flowableOnSubscribe, BackpressureStrategy backpressureStrategy) {
        ObjectHelper.requireNonNull(flowableOnSubscribe, "source is null");
        ObjectHelper.requireNonNull(backpressureStrategy, "mode is null");
        return RxJavaPlugins.onAssembly(new FlowableCreate(flowableOnSubscribe, backpressureStrategy));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @CheckReturnValue
    public static <T> Flowable<T> defer(Callable<? extends Publisher<? extends T>> callable) {
        ObjectHelper.requireNonNull(callable, "supplier is null");
        return RxJavaPlugins.onAssembly(new FlowableDefer(callable));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @CheckReturnValue
    private Flowable<T> doOnEach(Consumer<? super T> consumer, Consumer<? super Throwable> consumer2, Action action, Action action2) {
        ObjectHelper.requireNonNull(consumer, "onNext is null");
        ObjectHelper.requireNonNull(consumer2, "onError is null");
        ObjectHelper.requireNonNull(action, "onComplete is null");
        ObjectHelper.requireNonNull(action2, "onAfterTerminate is null");
        return RxJavaPlugins.onAssembly(new FlowableDoOnEach(this, consumer, consumer2, action, action2));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @CheckReturnValue
    public static <T> Flowable<T> empty() {
        return RxJavaPlugins.onAssembly(FlowableEmpty.INSTANCE);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @CheckReturnValue
    public static <T> Flowable<T> error(Throwable th) {
        ObjectHelper.requireNonNull(th, "throwable is null");
        return error(Functions.justCallable(th));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @CheckReturnValue
    public static <T> Flowable<T> error(Callable<? extends Throwable> callable) {
        ObjectHelper.requireNonNull(callable, "errorSupplier is null");
        return RxJavaPlugins.onAssembly(new FlowableError(callable));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public static <T> Flowable<T> fromArray(T... tArr) {
        ObjectHelper.requireNonNull(tArr, "items is null");
        return tArr.length == 0 ? empty() : tArr.length == 1 ? just(tArr[0]) : RxJavaPlugins.onAssembly(new FlowableFromArray(tArr));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public static <T> Flowable<T> fromCallable(Callable<? extends T> callable) {
        ObjectHelper.requireNonNull(callable, "supplier is null");
        return RxJavaPlugins.onAssembly(new FlowableFromCallable(callable));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public static <T> Flowable<T> fromFuture(Future<? extends T> future) {
        ObjectHelper.requireNonNull(future, "future is null");
        return RxJavaPlugins.onAssembly(new FlowableFromFuture(future, 0, null));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public static <T> Flowable<T> fromFuture(Future<? extends T> future, long j, TimeUnit timeUnit) {
        ObjectHelper.requireNonNull(future, "future is null");
        ObjectHelper.requireNonNull(timeUnit, "unit is null");
        return RxJavaPlugins.onAssembly(new FlowableFromFuture(future, j, timeUnit));
    }

    @SchedulerSupport("custom")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public static <T> Flowable<T> fromFuture(Future<? extends T> future, long j, TimeUnit timeUnit, Scheduler scheduler) {
        ObjectHelper.requireNonNull(scheduler, "scheduler is null");
        return fromFuture(future, j, timeUnit).subscribeOn(scheduler);
    }

    @SchedulerSupport("custom")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public static <T> Flowable<T> fromFuture(Future<? extends T> future, Scheduler scheduler) {
        ObjectHelper.requireNonNull(scheduler, "scheduler is null");
        return fromFuture(future).subscribeOn(scheduler);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public static <T> Flowable<T> fromIterable(Iterable<? extends T> iterable) {
        ObjectHelper.requireNonNull(iterable, "source is null");
        return RxJavaPlugins.onAssembly(new FlowableFromIterable(iterable));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @CheckReturnValue
    public static <T> Flowable<T> fromPublisher(Publisher<? extends T> publisher) {
        if (publisher instanceof Flowable) {
            return RxJavaPlugins.onAssembly((Flowable) publisher);
        }
        ObjectHelper.requireNonNull(publisher, "publisher is null");
        return RxJavaPlugins.onAssembly(new FlowableFromPublisher(publisher));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public static <T> Flowable<T> generate(Consumer<Emitter<T>> consumer) {
        ObjectHelper.requireNonNull(consumer, "generator is null");
        return generate(Functions.nullSupplier(), FlowableInternalHelper.simpleGenerator(consumer), Functions.emptyConsumer());
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public static <T, S> Flowable<T> generate(Callable<S> callable, BiConsumer<S, Emitter<T>> biConsumer) {
        ObjectHelper.requireNonNull(biConsumer, "generator is null");
        return generate(callable, FlowableInternalHelper.simpleBiGenerator(biConsumer), Functions.emptyConsumer());
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public static <T, S> Flowable<T> generate(Callable<S> callable, BiConsumer<S, Emitter<T>> biConsumer, Consumer<? super S> consumer) {
        ObjectHelper.requireNonNull(biConsumer, "generator is null");
        return generate(callable, FlowableInternalHelper.simpleBiGenerator(biConsumer), consumer);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public static <T, S> Flowable<T> generate(Callable<S> callable, BiFunction<S, Emitter<T>, S> biFunction) {
        return generate(callable, biFunction, Functions.emptyConsumer());
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public static <T, S> Flowable<T> generate(Callable<S> callable, BiFunction<S, Emitter<T>, S> biFunction, Consumer<? super S> consumer) {
        ObjectHelper.requireNonNull(callable, "initialState is null");
        ObjectHelper.requireNonNull(biFunction, "generator is null");
        ObjectHelper.requireNonNull(consumer, "disposeState is null");
        return RxJavaPlugins.onAssembly(new FlowableGenerate(callable, biFunction, consumer));
    }

    @SchedulerSupport(SchedulerSupport.COMPUTATION)
    @BackpressureSupport(BackpressureKind.ERROR)
    @CheckReturnValue
    public static Flowable<Long> interval(long j, long j2, TimeUnit timeUnit) {
        return interval(j, j2, timeUnit, Schedulers.computation());
    }

    @SchedulerSupport("custom")
    @BackpressureSupport(BackpressureKind.ERROR)
    @CheckReturnValue
    public static Flowable<Long> interval(long j, long j2, TimeUnit timeUnit, Scheduler scheduler) {
        ObjectHelper.requireNonNull(timeUnit, "unit is null");
        ObjectHelper.requireNonNull(scheduler, "scheduler is null");
        return RxJavaPlugins.onAssembly(new FlowableInterval(Math.max(0L, j), Math.max(0L, j2), timeUnit, scheduler));
    }

    @SchedulerSupport(SchedulerSupport.COMPUTATION)
    @BackpressureSupport(BackpressureKind.ERROR)
    @CheckReturnValue
    public static Flowable<Long> interval(long j, TimeUnit timeUnit) {
        return interval(j, j, timeUnit, Schedulers.computation());
    }

    @SchedulerSupport("custom")
    @BackpressureSupport(BackpressureKind.ERROR)
    @CheckReturnValue
    public static Flowable<Long> interval(long j, TimeUnit timeUnit, Scheduler scheduler) {
        return interval(j, j, timeUnit, scheduler);
    }

    @SchedulerSupport(SchedulerSupport.COMPUTATION)
    @BackpressureSupport(BackpressureKind.ERROR)
    @CheckReturnValue
    public static Flowable<Long> intervalRange(long j, long j2, long j3, long j4, TimeUnit timeUnit) {
        return intervalRange(j, j2, j3, j4, timeUnit, Schedulers.computation());
    }

    @SchedulerSupport("custom")
    @BackpressureSupport(BackpressureKind.ERROR)
    @CheckReturnValue
    public static Flowable<Long> intervalRange(long j, long j2, long j3, long j4, TimeUnit timeUnit, Scheduler scheduler) {
        int i = (j2 > 0 ? 1 : (j2 == 0 ? 0 : -1));
        if (i < 0) {
            throw new IllegalArgumentException("count >= 0 required but it was " + j2);
        } else if (i == 0) {
            return empty().delay(j3, timeUnit, scheduler);
        } else {
            long j5 = j + (j2 - 1);
            if (j <= 0 || j5 >= 0) {
                ObjectHelper.requireNonNull(timeUnit, "unit is null");
                ObjectHelper.requireNonNull(scheduler, "scheduler is null");
                return RxJavaPlugins.onAssembly(new FlowableIntervalRange(j, j5, Math.max(0L, j3), Math.max(0L, j4), timeUnit, scheduler));
            }
            throw new IllegalArgumentException("Overflow! start + count is bigger than Long.MAX_VALUE");
        }
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public static <T> Flowable<T> just(T t) {
        ObjectHelper.requireNonNull(t, "item is null");
        return RxJavaPlugins.onAssembly(new FlowableJust(t));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public static <T> Flowable<T> just(T t, T t2) {
        ObjectHelper.requireNonNull(t, "The first item is null");
        ObjectHelper.requireNonNull(t2, "The second item is null");
        return fromArray(t, t2);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public static <T> Flowable<T> just(T t, T t2, T t3) {
        ObjectHelper.requireNonNull(t, "The first item is null");
        ObjectHelper.requireNonNull(t2, "The second item is null");
        ObjectHelper.requireNonNull(t3, "The third item is null");
        return fromArray(t, t2, t3);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public static <T> Flowable<T> just(T t, T t2, T t3, T t4) {
        ObjectHelper.requireNonNull(t, "The first item is null");
        ObjectHelper.requireNonNull(t2, "The second item is null");
        ObjectHelper.requireNonNull(t3, "The third item is null");
        ObjectHelper.requireNonNull(t4, "The fourth item is null");
        return fromArray(t, t2, t3, t4);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public static <T> Flowable<T> just(T t, T t2, T t3, T t4, T t5) {
        ObjectHelper.requireNonNull(t, "The first item is null");
        ObjectHelper.requireNonNull(t2, "The second item is null");
        ObjectHelper.requireNonNull(t3, "The third item is null");
        ObjectHelper.requireNonNull(t4, "The fourth item is null");
        ObjectHelper.requireNonNull(t5, "The fifth item is null");
        return fromArray(t, t2, t3, t4, t5);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public static <T> Flowable<T> just(T t, T t2, T t3, T t4, T t5, T t6) {
        ObjectHelper.requireNonNull(t, "The first item is null");
        ObjectHelper.requireNonNull(t2, "The second item is null");
        ObjectHelper.requireNonNull(t3, "The third item is null");
        ObjectHelper.requireNonNull(t4, "The fourth item is null");
        ObjectHelper.requireNonNull(t5, "The fifth item is null");
        ObjectHelper.requireNonNull(t6, "The sixth item is null");
        return fromArray(t, t2, t3, t4, t5, t6);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public static <T> Flowable<T> just(T t, T t2, T t3, T t4, T t5, T t6, T t7) {
        ObjectHelper.requireNonNull(t, "The first item is null");
        ObjectHelper.requireNonNull(t2, "The second item is null");
        ObjectHelper.requireNonNull(t3, "The third item is null");
        ObjectHelper.requireNonNull(t4, "The fourth item is null");
        ObjectHelper.requireNonNull(t5, "The fifth item is null");
        ObjectHelper.requireNonNull(t6, "The sixth item is null");
        ObjectHelper.requireNonNull(t7, "The seventh item is null");
        return fromArray(t, t2, t3, t4, t5, t6, t7);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public static <T> Flowable<T> just(T t, T t2, T t3, T t4, T t5, T t6, T t7, T t8) {
        ObjectHelper.requireNonNull(t, "The first item is null");
        ObjectHelper.requireNonNull(t2, "The second item is null");
        ObjectHelper.requireNonNull(t3, "The third item is null");
        ObjectHelper.requireNonNull(t4, "The fourth item is null");
        ObjectHelper.requireNonNull(t5, "The fifth item is null");
        ObjectHelper.requireNonNull(t6, "The sixth item is null");
        ObjectHelper.requireNonNull(t7, "The seventh item is null");
        ObjectHelper.requireNonNull(t8, "The eighth item is null");
        return fromArray(t, t2, t3, t4, t5, t6, t7, t8);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public static <T> Flowable<T> just(T t, T t2, T t3, T t4, T t5, T t6, T t7, T t8, T t9) {
        ObjectHelper.requireNonNull(t, "The first item is null");
        ObjectHelper.requireNonNull(t2, "The second item is null");
        ObjectHelper.requireNonNull(t3, "The third item is null");
        ObjectHelper.requireNonNull(t4, "The fourth item is null");
        ObjectHelper.requireNonNull(t5, "The fifth item is null");
        ObjectHelper.requireNonNull(t6, "The sixth item is null");
        ObjectHelper.requireNonNull(t7, "The seventh item is null");
        ObjectHelper.requireNonNull(t8, "The eighth item is null");
        ObjectHelper.requireNonNull(t9, "The ninth is null");
        return fromArray(t, t2, t3, t4, t5, t6, t7, t8, t9);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public static <T> Flowable<T> just(T t, T t2, T t3, T t4, T t5, T t6, T t7, T t8, T t9, T t10) {
        ObjectHelper.requireNonNull(t, "The first item is null");
        ObjectHelper.requireNonNull(t2, "The second item is null");
        ObjectHelper.requireNonNull(t3, "The third item is null");
        ObjectHelper.requireNonNull(t4, "The fourth item is null");
        ObjectHelper.requireNonNull(t5, "The fifth item is null");
        ObjectHelper.requireNonNull(t6, "The sixth item is null");
        ObjectHelper.requireNonNull(t7, "The seventh item is null");
        ObjectHelper.requireNonNull(t8, "The eighth item is null");
        ObjectHelper.requireNonNull(t9, "The ninth item is null");
        ObjectHelper.requireNonNull(t10, "The tenth item is null");
        return fromArray(t, t2, t3, t4, t5, t6, t7, t8, t9, t10);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public static <T> Flowable<T> merge(Iterable<? extends Publisher<? extends T>> iterable) {
        return fromIterable(iterable).flatMap(Functions.identity());
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public static <T> Flowable<T> merge(Iterable<? extends Publisher<? extends T>> iterable, int i) {
        return fromIterable(iterable).flatMap(Functions.identity(), i);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public static <T> Flowable<T> merge(Iterable<? extends Publisher<? extends T>> iterable, int i, int i2) {
        return fromIterable(iterable).flatMap((Function) Functions.identity(), false, i, i2);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public static <T> Flowable<T> merge(Publisher<? extends Publisher<? extends T>> publisher) {
        return merge(publisher, bufferSize());
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public static <T> Flowable<T> merge(Publisher<? extends Publisher<? extends T>> publisher, int i) {
        return fromPublisher(publisher).flatMap(Functions.identity(), i);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public static <T> Flowable<T> merge(Publisher<? extends T> publisher, Publisher<? extends T> publisher2) {
        ObjectHelper.requireNonNull(publisher, "source1 is null");
        ObjectHelper.requireNonNull(publisher2, "source2 is null");
        return fromArray(publisher, publisher2).flatMap((Function) Functions.identity(), false, 2);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public static <T> Flowable<T> merge(Publisher<? extends T> publisher, Publisher<? extends T> publisher2, Publisher<? extends T> publisher3) {
        ObjectHelper.requireNonNull(publisher, "source1 is null");
        ObjectHelper.requireNonNull(publisher2, "source2 is null");
        ObjectHelper.requireNonNull(publisher3, "source3 is null");
        return fromArray(publisher, publisher2, publisher3).flatMap((Function) Functions.identity(), false, 3);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public static <T> Flowable<T> merge(Publisher<? extends T> publisher, Publisher<? extends T> publisher2, Publisher<? extends T> publisher3, Publisher<? extends T> publisher4) {
        ObjectHelper.requireNonNull(publisher, "source1 is null");
        ObjectHelper.requireNonNull(publisher2, "source2 is null");
        ObjectHelper.requireNonNull(publisher3, "source3 is null");
        ObjectHelper.requireNonNull(publisher4, "source4 is null");
        return fromArray(publisher, publisher2, publisher3, publisher4).flatMap((Function) Functions.identity(), false, 4);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public static <T> Flowable<T> mergeArray(int i, int i2, Publisher<? extends T>... publisherArr) {
        return fromArray(publisherArr).flatMap((Function) Functions.identity(), false, i, i2);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public static <T> Flowable<T> mergeArray(Publisher<? extends T>... publisherArr) {
        return fromArray(publisherArr).flatMap(Functions.identity(), publisherArr.length);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public static <T> Flowable<T> mergeArrayDelayError(int i, int i2, Publisher<? extends T>... publisherArr) {
        return fromArray(publisherArr).flatMap((Function) Functions.identity(), true, i, i2);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public static <T> Flowable<T> mergeArrayDelayError(Publisher<? extends T>... publisherArr) {
        return fromArray(publisherArr).flatMap((Function) Functions.identity(), true, publisherArr.length);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public static <T> Flowable<T> mergeDelayError(Iterable<? extends Publisher<? extends T>> iterable) {
        return fromIterable(iterable).flatMap((Function) Functions.identity(), true);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public static <T> Flowable<T> mergeDelayError(Iterable<? extends Publisher<? extends T>> iterable, int i) {
        return fromIterable(iterable).flatMap((Function) Functions.identity(), true, i);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public static <T> Flowable<T> mergeDelayError(Iterable<? extends Publisher<? extends T>> iterable, int i, int i2) {
        return fromIterable(iterable).flatMap((Function) Functions.identity(), true, i, i2);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public static <T> Flowable<T> mergeDelayError(Publisher<? extends Publisher<? extends T>> publisher) {
        return mergeDelayError(publisher, bufferSize());
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public static <T> Flowable<T> mergeDelayError(Publisher<? extends Publisher<? extends T>> publisher, int i) {
        return fromPublisher(publisher).flatMap((Function) Functions.identity(), true, i);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public static <T> Flowable<T> mergeDelayError(Publisher<? extends T> publisher, Publisher<? extends T> publisher2) {
        ObjectHelper.requireNonNull(publisher, "source1 is null");
        ObjectHelper.requireNonNull(publisher2, "source2 is null");
        return fromArray(publisher, publisher2).flatMap((Function) Functions.identity(), true, 2);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public static <T> Flowable<T> mergeDelayError(Publisher<? extends T> publisher, Publisher<? extends T> publisher2, Publisher<? extends T> publisher3) {
        ObjectHelper.requireNonNull(publisher, "source1 is null");
        ObjectHelper.requireNonNull(publisher2, "source2 is null");
        ObjectHelper.requireNonNull(publisher3, "source3 is null");
        return fromArray(publisher, publisher2, publisher3).flatMap((Function) Functions.identity(), true, 3);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public static <T> Flowable<T> mergeDelayError(Publisher<? extends T> publisher, Publisher<? extends T> publisher2, Publisher<? extends T> publisher3, Publisher<? extends T> publisher4) {
        ObjectHelper.requireNonNull(publisher, "source1 is null");
        ObjectHelper.requireNonNull(publisher2, "source2 is null");
        ObjectHelper.requireNonNull(publisher3, "source3 is null");
        ObjectHelper.requireNonNull(publisher4, "source4 is null");
        return fromArray(publisher, publisher2, publisher3, publisher4).flatMap((Function) Functions.identity(), true, 4);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @CheckReturnValue
    public static <T> Flowable<T> never() {
        return RxJavaPlugins.onAssembly(FlowableNever.INSTANCE);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public static Flowable<Integer> range(int i, int i2) {
        if (i2 < 0) {
            throw new IllegalArgumentException("count >= 0 required but it was " + i2);
        } else if (i2 == 0) {
            return empty();
        } else {
            if (i2 == 1) {
                return just(Integer.valueOf(i));
            }
            if (((long) i) + ((long) (i2 - 1)) <= 2147483647L) {
                return RxJavaPlugins.onAssembly(new FlowableRange(i, i2));
            }
            throw new IllegalArgumentException("Integer overflow");
        }
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public static Flowable<Long> rangeLong(long j, long j2) {
        int i = (j2 > 0 ? 1 : (j2 == 0 ? 0 : -1));
        if (i < 0) {
            throw new IllegalArgumentException("count >= 0 required but it was " + j2);
        } else if (i == 0) {
            return empty();
        } else {
            if (j2 == 1) {
                return just(Long.valueOf(j));
            }
            long j3 = (j2 - 1) + j;
            if (j <= 0 || j3 >= 0) {
                return RxJavaPlugins.onAssembly(new FlowableRangeLong(j, j2));
            }
            throw new IllegalArgumentException("Overflow! start + count is bigger than Long.MAX_VALUE");
        }
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public static <T> Single<Boolean> sequenceEqual(Publisher<? extends T> publisher, Publisher<? extends T> publisher2) {
        return sequenceEqual(publisher, publisher2, ObjectHelper.equalsPredicate(), bufferSize());
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public static <T> Single<Boolean> sequenceEqual(Publisher<? extends T> publisher, Publisher<? extends T> publisher2, int i) {
        return sequenceEqual(publisher, publisher2, ObjectHelper.equalsPredicate(), i);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public static <T> Single<Boolean> sequenceEqual(Publisher<? extends T> publisher, Publisher<? extends T> publisher2, BiPredicate<? super T, ? super T> biPredicate) {
        return sequenceEqual(publisher, publisher2, biPredicate, bufferSize());
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public static <T> Single<Boolean> sequenceEqual(Publisher<? extends T> publisher, Publisher<? extends T> publisher2, BiPredicate<? super T, ? super T> biPredicate, int i) {
        ObjectHelper.requireNonNull(publisher, "source1 is null");
        ObjectHelper.requireNonNull(publisher2, "source2 is null");
        ObjectHelper.requireNonNull(biPredicate, "isEqual is null");
        ObjectHelper.verifyPositive(i, "bufferSize");
        return RxJavaPlugins.onAssembly(new FlowableSequenceEqualSingle(publisher, publisher2, biPredicate, i));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public static <T> Flowable<T> switchOnNext(Publisher<? extends Publisher<? extends T>> publisher) {
        return fromPublisher(publisher).switchMap(Functions.identity());
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public static <T> Flowable<T> switchOnNext(Publisher<? extends Publisher<? extends T>> publisher, int i) {
        return fromPublisher(publisher).switchMap(Functions.identity(), i);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public static <T> Flowable<T> switchOnNextDelayError(Publisher<? extends Publisher<? extends T>> publisher) {
        return switchOnNextDelayError(publisher, bufferSize());
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public static <T> Flowable<T> switchOnNextDelayError(Publisher<? extends Publisher<? extends T>> publisher, int i) {
        return fromPublisher(publisher).switchMapDelayError(Functions.identity(), i);
    }

    private Flowable<T> timeout0(long j, TimeUnit timeUnit, Publisher<? extends T> publisher, Scheduler scheduler) {
        ObjectHelper.requireNonNull(timeUnit, "timeUnit is null");
        ObjectHelper.requireNonNull(scheduler, "scheduler is null");
        return RxJavaPlugins.onAssembly(new FlowableTimeoutTimed(this, j, timeUnit, scheduler, publisher));
    }

    private <U, V> Flowable<T> timeout0(Publisher<U> publisher, Function<? super T, ? extends Publisher<V>> function, Publisher<? extends T> publisher2) {
        ObjectHelper.requireNonNull(function, "itemTimeoutIndicator is null");
        return RxJavaPlugins.onAssembly(new FlowableTimeout(this, publisher, function, publisher2));
    }

    @SchedulerSupport(SchedulerSupport.COMPUTATION)
    @BackpressureSupport(BackpressureKind.ERROR)
    @CheckReturnValue
    public static Flowable<Long> timer(long j, TimeUnit timeUnit) {
        return timer(j, timeUnit, Schedulers.computation());
    }

    @SchedulerSupport("custom")
    @BackpressureSupport(BackpressureKind.ERROR)
    @CheckReturnValue
    public static Flowable<Long> timer(long j, TimeUnit timeUnit, Scheduler scheduler) {
        ObjectHelper.requireNonNull(timeUnit, "unit is null");
        ObjectHelper.requireNonNull(scheduler, "scheduler is null");
        return RxJavaPlugins.onAssembly(new FlowableTimer(Math.max(0L, j), timeUnit, scheduler));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.NONE)
    @CheckReturnValue
    public static <T> Flowable<T> unsafeCreate(Publisher<T> publisher) {
        ObjectHelper.requireNonNull(publisher, "onSubscribe is null");
        if (!(publisher instanceof Flowable)) {
            return RxJavaPlugins.onAssembly(new FlowableFromPublisher(publisher));
        }
        throw new IllegalArgumentException("unsafeCreate(Flowable) should be upgraded");
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @CheckReturnValue
    public static <T, D> Flowable<T> using(Callable<? extends D> callable, Function<? super D, ? extends Publisher<? extends T>> function, Consumer<? super D> consumer) {
        return using(callable, function, consumer, true);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @CheckReturnValue
    public static <T, D> Flowable<T> using(Callable<? extends D> callable, Function<? super D, ? extends Publisher<? extends T>> function, Consumer<? super D> consumer, boolean z) {
        ObjectHelper.requireNonNull(callable, "resourceSupplier is null");
        ObjectHelper.requireNonNull(function, "sourceSupplier is null");
        ObjectHelper.requireNonNull(consumer, "disposer is null");
        return RxJavaPlugins.onAssembly(new FlowableUsing(callable, function, consumer, z));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public static <T, R> Flowable<R> zip(Iterable<? extends Publisher<? extends T>> iterable, Function<? super Object[], ? extends R> function) {
        ObjectHelper.requireNonNull(function, "zipper is null");
        ObjectHelper.requireNonNull(iterable, "sources is null");
        return RxJavaPlugins.onAssembly(new FlowableZip(null, iterable, function, bufferSize(), false));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public static <T, R> Flowable<R> zip(Publisher<? extends Publisher<? extends T>> publisher, Function<? super Object[], ? extends R> function) {
        ObjectHelper.requireNonNull(function, "zipper is null");
        return fromPublisher(publisher).toList().flatMapPublisher(FlowableInternalHelper.zipIterable(function));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public static <T1, T2, R> Flowable<R> zip(Publisher<? extends T1> publisher, Publisher<? extends T2> publisher2, BiFunction<? super T1, ? super T2, ? extends R> biFunction) {
        ObjectHelper.requireNonNull(publisher, "source1 is null");
        ObjectHelper.requireNonNull(publisher2, "source2 is null");
        return zipArray(Functions.toFunction(biFunction), false, bufferSize(), publisher, publisher2);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public static <T1, T2, R> Flowable<R> zip(Publisher<? extends T1> publisher, Publisher<? extends T2> publisher2, BiFunction<? super T1, ? super T2, ? extends R> biFunction, boolean z) {
        ObjectHelper.requireNonNull(publisher, "source1 is null");
        ObjectHelper.requireNonNull(publisher2, "source2 is null");
        return zipArray(Functions.toFunction(biFunction), z, bufferSize(), publisher, publisher2);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public static <T1, T2, R> Flowable<R> zip(Publisher<? extends T1> publisher, Publisher<? extends T2> publisher2, BiFunction<? super T1, ? super T2, ? extends R> biFunction, boolean z, int i) {
        ObjectHelper.requireNonNull(publisher, "source1 is null");
        ObjectHelper.requireNonNull(publisher2, "source2 is null");
        return zipArray(Functions.toFunction(biFunction), z, i, publisher, publisher2);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public static <T1, T2, T3, R> Flowable<R> zip(Publisher<? extends T1> publisher, Publisher<? extends T2> publisher2, Publisher<? extends T3> publisher3, Function3<? super T1, ? super T2, ? super T3, ? extends R> function3) {
        ObjectHelper.requireNonNull(publisher, "source1 is null");
        ObjectHelper.requireNonNull(publisher2, "source2 is null");
        ObjectHelper.requireNonNull(publisher3, "source3 is null");
        return zipArray(Functions.toFunction(function3), false, bufferSize(), publisher, publisher2, publisher3);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public static <T1, T2, T3, T4, R> Flowable<R> zip(Publisher<? extends T1> publisher, Publisher<? extends T2> publisher2, Publisher<? extends T3> publisher3, Publisher<? extends T4> publisher4, Function4<? super T1, ? super T2, ? super T3, ? super T4, ? extends R> function4) {
        ObjectHelper.requireNonNull(publisher, "source1 is null");
        ObjectHelper.requireNonNull(publisher2, "source2 is null");
        ObjectHelper.requireNonNull(publisher3, "source3 is null");
        ObjectHelper.requireNonNull(publisher4, "source4 is null");
        return zipArray(Functions.toFunction(function4), false, bufferSize(), publisher, publisher2, publisher3, publisher4);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public static <T1, T2, T3, T4, T5, R> Flowable<R> zip(Publisher<? extends T1> publisher, Publisher<? extends T2> publisher2, Publisher<? extends T3> publisher3, Publisher<? extends T4> publisher4, Publisher<? extends T5> publisher5, Function5<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? extends R> function5) {
        ObjectHelper.requireNonNull(publisher, "source1 is null");
        ObjectHelper.requireNonNull(publisher2, "source2 is null");
        ObjectHelper.requireNonNull(publisher3, "source3 is null");
        ObjectHelper.requireNonNull(publisher4, "source4 is null");
        ObjectHelper.requireNonNull(publisher5, "source5 is null");
        return zipArray(Functions.toFunction(function5), false, bufferSize(), publisher, publisher2, publisher3, publisher4, publisher5);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public static <T1, T2, T3, T4, T5, T6, R> Flowable<R> zip(Publisher<? extends T1> publisher, Publisher<? extends T2> publisher2, Publisher<? extends T3> publisher3, Publisher<? extends T4> publisher4, Publisher<? extends T5> publisher5, Publisher<? extends T6> publisher6, Function6<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? extends R> function6) {
        ObjectHelper.requireNonNull(publisher, "source1 is null");
        ObjectHelper.requireNonNull(publisher2, "source2 is null");
        ObjectHelper.requireNonNull(publisher3, "source3 is null");
        ObjectHelper.requireNonNull(publisher4, "source4 is null");
        ObjectHelper.requireNonNull(publisher5, "source5 is null");
        ObjectHelper.requireNonNull(publisher6, "source6 is null");
        return zipArray(Functions.toFunction(function6), false, bufferSize(), publisher, publisher2, publisher3, publisher4, publisher5, publisher6);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public static <T1, T2, T3, T4, T5, T6, T7, R> Flowable<R> zip(Publisher<? extends T1> publisher, Publisher<? extends T2> publisher2, Publisher<? extends T3> publisher3, Publisher<? extends T4> publisher4, Publisher<? extends T5> publisher5, Publisher<? extends T6> publisher6, Publisher<? extends T7> publisher7, Function7<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? extends R> function7) {
        ObjectHelper.requireNonNull(publisher, "source1 is null");
        ObjectHelper.requireNonNull(publisher2, "source2 is null");
        ObjectHelper.requireNonNull(publisher3, "source3 is null");
        ObjectHelper.requireNonNull(publisher4, "source4 is null");
        ObjectHelper.requireNonNull(publisher5, "source5 is null");
        ObjectHelper.requireNonNull(publisher6, "source6 is null");
        ObjectHelper.requireNonNull(publisher7, "source7 is null");
        return zipArray(Functions.toFunction(function7), false, bufferSize(), publisher, publisher2, publisher3, publisher4, publisher5, publisher6, publisher7);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public static <T1, T2, T3, T4, T5, T6, T7, T8, R> Flowable<R> zip(Publisher<? extends T1> publisher, Publisher<? extends T2> publisher2, Publisher<? extends T3> publisher3, Publisher<? extends T4> publisher4, Publisher<? extends T5> publisher5, Publisher<? extends T6> publisher6, Publisher<? extends T7> publisher7, Publisher<? extends T8> publisher8, Function8<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? extends R> function8) {
        ObjectHelper.requireNonNull(publisher, "source1 is null");
        ObjectHelper.requireNonNull(publisher2, "source2 is null");
        ObjectHelper.requireNonNull(publisher3, "source3 is null");
        ObjectHelper.requireNonNull(publisher4, "source4 is null");
        ObjectHelper.requireNonNull(publisher5, "source5 is null");
        ObjectHelper.requireNonNull(publisher6, "source6 is null");
        ObjectHelper.requireNonNull(publisher7, "source7 is null");
        ObjectHelper.requireNonNull(publisher8, "source8 is null");
        return zipArray(Functions.toFunction(function8), false, bufferSize(), publisher, publisher2, publisher3, publisher4, publisher5, publisher6, publisher7, publisher8);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, R> Flowable<R> zip(Publisher<? extends T1> publisher, Publisher<? extends T2> publisher2, Publisher<? extends T3> publisher3, Publisher<? extends T4> publisher4, Publisher<? extends T5> publisher5, Publisher<? extends T6> publisher6, Publisher<? extends T7> publisher7, Publisher<? extends T8> publisher8, Publisher<? extends T9> publisher9, Function9<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? super T9, ? extends R> function9) {
        ObjectHelper.requireNonNull(publisher, "source1 is null");
        ObjectHelper.requireNonNull(publisher2, "source2 is null");
        ObjectHelper.requireNonNull(publisher3, "source3 is null");
        ObjectHelper.requireNonNull(publisher4, "source4 is null");
        ObjectHelper.requireNonNull(publisher5, "source5 is null");
        ObjectHelper.requireNonNull(publisher6, "source6 is null");
        ObjectHelper.requireNonNull(publisher7, "source7 is null");
        ObjectHelper.requireNonNull(publisher8, "source8 is null");
        ObjectHelper.requireNonNull(publisher9, "source9 is null");
        return zipArray(Functions.toFunction(function9), false, bufferSize(), publisher, publisher2, publisher3, publisher4, publisher5, publisher6, publisher7, publisher8, publisher9);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public static <T, R> Flowable<R> zipArray(Function<? super Object[], ? extends R> function, boolean z, int i, Publisher<? extends T>... publisherArr) {
        if (publisherArr.length == 0) {
            return empty();
        }
        ObjectHelper.requireNonNull(function, "zipper is null");
        ObjectHelper.verifyPositive(i, "bufferSize");
        return RxJavaPlugins.onAssembly(new FlowableZip(publisherArr, null, function, i, z));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public static <T, R> Flowable<R> zipIterable(Iterable<? extends Publisher<? extends T>> iterable, Function<? super Object[], ? extends R> function, boolean z, int i) {
        ObjectHelper.requireNonNull(function, "zipper is null");
        ObjectHelper.requireNonNull(iterable, "sources is null");
        ObjectHelper.verifyPositive(i, "bufferSize");
        return RxJavaPlugins.onAssembly(new FlowableZip(null, iterable, function, i, z));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @CheckReturnValue
    public final Single<Boolean> all(Predicate<? super T> predicate) {
        ObjectHelper.requireNonNull(predicate, "predicate is null");
        return RxJavaPlugins.onAssembly(new FlowableAllSingle(this, predicate));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final Flowable<T> ambWith(Publisher<? extends T> publisher) {
        ObjectHelper.requireNonNull(publisher, "other is null");
        return ambArray(this, publisher);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @CheckReturnValue
    public final Single<Boolean> any(Predicate<? super T> predicate) {
        ObjectHelper.requireNonNull(predicate, "predicate is null");
        return RxJavaPlugins.onAssembly(new FlowableAnySingle(this, predicate));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.SPECIAL)
    @CheckReturnValue
    @Experimental
    public final <R> R as(@NonNull FlowableConverter<T, ? extends R> flowableConverter) {
        ObjectHelper.requireNonNull(flowableConverter, "converter is null");
        return flowableConverter.apply(this);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @CheckReturnValue
    public final T blockingFirst() {
        BlockingFirstSubscriber blockingFirstSubscriber = new BlockingFirstSubscriber();
        subscribe((FlowableSubscriber) blockingFirstSubscriber);
        T blockingGet = blockingFirstSubscriber.blockingGet();
        if (blockingGet != null) {
            return blockingGet;
        }
        throw new NoSuchElementException();
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @CheckReturnValue
    public final T blockingFirst(T t) {
        BlockingFirstSubscriber blockingFirstSubscriber = new BlockingFirstSubscriber();
        subscribe((FlowableSubscriber) blockingFirstSubscriber);
        T blockingGet = blockingFirstSubscriber.blockingGet();
        return blockingGet != null ? blockingGet : t;
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    public final void blockingForEach(Consumer<? super T> consumer) {
        Iterator<T> it = blockingIterable().iterator();
        while (it.hasNext()) {
            try {
                consumer.accept(it.next());
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                ((Disposable) it).dispose();
                throw ExceptionHelper.wrapOrThrow(th);
            }
        }
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final Iterable<T> blockingIterable() {
        return blockingIterable(bufferSize());
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final Iterable<T> blockingIterable(int i) {
        ObjectHelper.verifyPositive(i, "bufferSize");
        return new BlockingFlowableIterable(this, i);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @CheckReturnValue
    public final T blockingLast() {
        BlockingLastSubscriber blockingLastSubscriber = new BlockingLastSubscriber();
        subscribe((FlowableSubscriber) blockingLastSubscriber);
        T blockingGet = blockingLastSubscriber.blockingGet();
        if (blockingGet != null) {
            return blockingGet;
        }
        throw new NoSuchElementException();
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @CheckReturnValue
    public final T blockingLast(T t) {
        BlockingLastSubscriber blockingLastSubscriber = new BlockingLastSubscriber();
        subscribe((FlowableSubscriber) blockingLastSubscriber);
        T blockingGet = blockingLastSubscriber.blockingGet();
        return blockingGet != null ? blockingGet : t;
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @CheckReturnValue
    public final Iterable<T> blockingLatest() {
        return new BlockingFlowableLatest(this);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @CheckReturnValue
    public final Iterable<T> blockingMostRecent(T t) {
        return new BlockingFlowableMostRecent(this, t);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @CheckReturnValue
    public final Iterable<T> blockingNext() {
        return new BlockingFlowableNext(this);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @CheckReturnValue
    public final T blockingSingle() {
        return singleOrError().blockingGet();
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @CheckReturnValue
    public final T blockingSingle(T t) {
        return single(t).blockingGet();
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    public final void blockingSubscribe() {
        FlowableBlockingSubscribe.subscribe(this);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    public final void blockingSubscribe(Consumer<? super T> consumer) {
        FlowableBlockingSubscribe.subscribe(this, consumer, Functions.ON_ERROR_MISSING, Functions.EMPTY_ACTION);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    public final void blockingSubscribe(Consumer<? super T> consumer, Consumer<? super Throwable> consumer2) {
        FlowableBlockingSubscribe.subscribe(this, consumer, consumer2, Functions.EMPTY_ACTION);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    public final void blockingSubscribe(Consumer<? super T> consumer, Consumer<? super Throwable> consumer2, Action action) {
        FlowableBlockingSubscribe.subscribe(this, consumer, consumer2, action);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.SPECIAL)
    public final void blockingSubscribe(Subscriber<? super T> subscriber) {
        FlowableBlockingSubscribe.subscribe(this, subscriber);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final Flowable<List<T>> buffer(int i) {
        return buffer(i, i);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final Flowable<List<T>> buffer(int i, int i2) {
        return buffer(i, i2, ArrayListSupplier.asCallable());
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final <U extends Collection<? super T>> Flowable<U> buffer(int i, int i2, Callable<U> callable) {
        ObjectHelper.verifyPositive(i, "count");
        ObjectHelper.verifyPositive(i2, "skip");
        ObjectHelper.requireNonNull(callable, "bufferSupplier is null");
        return RxJavaPlugins.onAssembly(new FlowableBuffer(this, i, i2, callable));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final <U extends Collection<? super T>> Flowable<U> buffer(int i, Callable<U> callable) {
        return buffer(i, i, callable);
    }

    @SchedulerSupport(SchedulerSupport.COMPUTATION)
    @BackpressureSupport(BackpressureKind.ERROR)
    @CheckReturnValue
    public final Flowable<List<T>> buffer(long j, long j2, TimeUnit timeUnit) {
        return buffer(j, j2, timeUnit, Schedulers.computation(), ArrayListSupplier.asCallable());
    }

    @SchedulerSupport("custom")
    @BackpressureSupport(BackpressureKind.ERROR)
    @CheckReturnValue
    public final Flowable<List<T>> buffer(long j, long j2, TimeUnit timeUnit, Scheduler scheduler) {
        return buffer(j, j2, timeUnit, scheduler, ArrayListSupplier.asCallable());
    }

    @SchedulerSupport("custom")
    @BackpressureSupport(BackpressureKind.ERROR)
    @CheckReturnValue
    public final <U extends Collection<? super T>> Flowable<U> buffer(long j, long j2, TimeUnit timeUnit, Scheduler scheduler, Callable<U> callable) {
        ObjectHelper.requireNonNull(timeUnit, "unit is null");
        ObjectHelper.requireNonNull(scheduler, "scheduler is null");
        ObjectHelper.requireNonNull(callable, "bufferSupplier is null");
        return RxJavaPlugins.onAssembly(new FlowableBufferTimed(this, j, j2, timeUnit, scheduler, callable, Integer.MAX_VALUE, false));
    }

    @SchedulerSupport(SchedulerSupport.COMPUTATION)
    @BackpressureSupport(BackpressureKind.ERROR)
    @CheckReturnValue
    public final Flowable<List<T>> buffer(long j, TimeUnit timeUnit) {
        return buffer(j, timeUnit, Schedulers.computation(), Integer.MAX_VALUE);
    }

    @SchedulerSupport(SchedulerSupport.COMPUTATION)
    @BackpressureSupport(BackpressureKind.ERROR)
    @CheckReturnValue
    public final Flowable<List<T>> buffer(long j, TimeUnit timeUnit, int i) {
        return buffer(j, timeUnit, Schedulers.computation(), i);
    }

    @SchedulerSupport("custom")
    @BackpressureSupport(BackpressureKind.ERROR)
    @CheckReturnValue
    public final Flowable<List<T>> buffer(long j, TimeUnit timeUnit, Scheduler scheduler) {
        return buffer(j, timeUnit, scheduler, Integer.MAX_VALUE, ArrayListSupplier.asCallable(), false);
    }

    @SchedulerSupport("custom")
    @BackpressureSupport(BackpressureKind.ERROR)
    @CheckReturnValue
    public final Flowable<List<T>> buffer(long j, TimeUnit timeUnit, Scheduler scheduler, int i) {
        return buffer(j, timeUnit, scheduler, i, ArrayListSupplier.asCallable(), false);
    }

    @SchedulerSupport("custom")
    @BackpressureSupport(BackpressureKind.ERROR)
    @CheckReturnValue
    public final <U extends Collection<? super T>> Flowable<U> buffer(long j, TimeUnit timeUnit, Scheduler scheduler, int i, Callable<U> callable, boolean z) {
        ObjectHelper.requireNonNull(timeUnit, "unit is null");
        ObjectHelper.requireNonNull(scheduler, "scheduler is null");
        ObjectHelper.requireNonNull(callable, "bufferSupplier is null");
        ObjectHelper.verifyPositive(i, "count");
        return RxJavaPlugins.onAssembly(new FlowableBufferTimed(this, j, j, timeUnit, scheduler, callable, i, z));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.ERROR)
    @CheckReturnValue
    public final <TOpening, TClosing> Flowable<List<T>> buffer(Flowable<? extends TOpening> flowable, Function<? super TOpening, ? extends Publisher<? extends TClosing>> function) {
        return buffer(flowable, function, ArrayListSupplier.asCallable());
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.ERROR)
    @CheckReturnValue
    public final <TOpening, TClosing, U extends Collection<? super T>> Flowable<U> buffer(Flowable<? extends TOpening> flowable, Function<? super TOpening, ? extends Publisher<? extends TClosing>> function, Callable<U> callable) {
        ObjectHelper.requireNonNull(flowable, "openingIndicator is null");
        ObjectHelper.requireNonNull(function, "closingIndicator is null");
        ObjectHelper.requireNonNull(callable, "bufferSupplier is null");
        return RxJavaPlugins.onAssembly(new FlowableBufferBoundary(this, flowable, function, callable));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.ERROR)
    @CheckReturnValue
    public final <B> Flowable<List<T>> buffer(Callable<? extends Publisher<B>> callable) {
        return buffer(callable, ArrayListSupplier.asCallable());
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.ERROR)
    @CheckReturnValue
    public final <B, U extends Collection<? super T>> Flowable<U> buffer(Callable<? extends Publisher<B>> callable, Callable<U> callable2) {
        ObjectHelper.requireNonNull(callable, "boundaryIndicatorSupplier is null");
        ObjectHelper.requireNonNull(callable2, "bufferSupplier is null");
        return RxJavaPlugins.onAssembly(new FlowableBufferBoundarySupplier(this, callable, callable2));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.ERROR)
    @CheckReturnValue
    public final <B> Flowable<List<T>> buffer(Publisher<B> publisher) {
        return buffer(publisher, ArrayListSupplier.asCallable());
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.ERROR)
    @CheckReturnValue
    public final <B> Flowable<List<T>> buffer(Publisher<B> publisher, int i) {
        ObjectHelper.verifyPositive(i, "initialCapacity");
        return buffer(publisher, Functions.createArrayList(i));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.ERROR)
    @CheckReturnValue
    public final <B, U extends Collection<? super T>> Flowable<U> buffer(Publisher<B> publisher, Callable<U> callable) {
        ObjectHelper.requireNonNull(publisher, "boundaryIndicator is null");
        ObjectHelper.requireNonNull(callable, "bufferSupplier is null");
        return RxJavaPlugins.onAssembly(new FlowableBufferExactBoundary(this, publisher, callable));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final Flowable<T> cache() {
        return cacheWithInitialCapacity(16);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final Flowable<T> cacheWithInitialCapacity(int i) {
        ObjectHelper.verifyPositive(i, "initialCapacity");
        return RxJavaPlugins.onAssembly(new FlowableCache(this, i));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @CheckReturnValue
    public final <U> Flowable<U> cast(Class<U> cls) {
        ObjectHelper.requireNonNull(cls, "clazz is null");
        return map(Functions.castFunction(cls));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @CheckReturnValue
    public final <U> Single<U> collect(Callable<? extends U> callable, BiConsumer<? super U, ? super T> biConsumer) {
        ObjectHelper.requireNonNull(callable, "initialItemSupplier is null");
        ObjectHelper.requireNonNull(biConsumer, "collector is null");
        return RxJavaPlugins.onAssembly(new FlowableCollectSingle(this, callable, biConsumer));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @CheckReturnValue
    public final <U> Single<U> collectInto(U u, BiConsumer<? super U, ? super T> biConsumer) {
        ObjectHelper.requireNonNull(u, "initialItem is null");
        return collect(Functions.justCallable(u), biConsumer);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @CheckReturnValue
    public final <R> Flowable<R> compose(FlowableTransformer<? super T, ? extends R> flowableTransformer) {
        ObjectHelper.requireNonNull(flowableTransformer, "composer is null");
        return fromPublisher(flowableTransformer.apply(this));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final <R> Flowable<R> concatMap(Function<? super T, ? extends Publisher<? extends R>> function) {
        return concatMap(function, 2);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final <R> Flowable<R> concatMap(Function<? super T, ? extends Publisher<? extends R>> function, int i) {
        ObjectHelper.requireNonNull(function, "mapper is null");
        ObjectHelper.verifyPositive(i, "prefetch");
        if (!(this instanceof ScalarCallable)) {
            return RxJavaPlugins.onAssembly(new FlowableConcatMap(this, function, i, ErrorMode.IMMEDIATE));
        }
        T call = ((ScalarCallable) this).call();
        return call == null ? empty() : FlowableScalarXMap.scalarXMap(call, function);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final <R> Flowable<R> concatMapDelayError(Function<? super T, ? extends Publisher<? extends R>> function) {
        return concatMapDelayError(function, 2, true);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final <R> Flowable<R> concatMapDelayError(Function<? super T, ? extends Publisher<? extends R>> function, int i, boolean z) {
        ObjectHelper.requireNonNull(function, "mapper is null");
        ObjectHelper.verifyPositive(i, "prefetch");
        if (this instanceof ScalarCallable) {
            T call = ((ScalarCallable) this).call();
            return call == null ? empty() : FlowableScalarXMap.scalarXMap(call, function);
        }
        return RxJavaPlugins.onAssembly(new FlowableConcatMap(this, function, i, z ? ErrorMode.END : ErrorMode.BOUNDARY));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final <R> Flowable<R> concatMapEager(Function<? super T, ? extends Publisher<? extends R>> function) {
        return concatMapEager(function, bufferSize(), bufferSize());
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final <R> Flowable<R> concatMapEager(Function<? super T, ? extends Publisher<? extends R>> function, int i, int i2) {
        ObjectHelper.requireNonNull(function, "mapper is null");
        ObjectHelper.verifyPositive(i, "maxConcurrency");
        ObjectHelper.verifyPositive(i2, "prefetch");
        return RxJavaPlugins.onAssembly(new FlowableConcatMapEager(this, function, i, i2, ErrorMode.IMMEDIATE));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final <R> Flowable<R> concatMapEagerDelayError(Function<? super T, ? extends Publisher<? extends R>> function, int i, int i2, boolean z) {
        ObjectHelper.requireNonNull(function, "mapper is null");
        ObjectHelper.verifyPositive(i, "maxConcurrency");
        ObjectHelper.verifyPositive(i2, "prefetch");
        return RxJavaPlugins.onAssembly(new FlowableConcatMapEager(this, function, i, i2, z ? ErrorMode.END : ErrorMode.BOUNDARY));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final <R> Flowable<R> concatMapEagerDelayError(Function<? super T, ? extends Publisher<? extends R>> function, boolean z) {
        return concatMapEagerDelayError(function, bufferSize(), bufferSize(), z);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final <U> Flowable<U> concatMapIterable(Function<? super T, ? extends Iterable<? extends U>> function) {
        return concatMapIterable(function, 2);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final <U> Flowable<U> concatMapIterable(Function<? super T, ? extends Iterable<? extends U>> function, int i) {
        ObjectHelper.requireNonNull(function, "mapper is null");
        ObjectHelper.verifyPositive(i, "prefetch");
        return RxJavaPlugins.onAssembly(new FlowableFlattenIterable(this, function, i));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final Flowable<T> concatWith(Publisher<? extends T> publisher) {
        ObjectHelper.requireNonNull(publisher, "other is null");
        return concat(this, publisher);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @CheckReturnValue
    public final Single<Boolean> contains(Object obj) {
        ObjectHelper.requireNonNull(obj, "item is null");
        return any(Functions.equalsWith(obj));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @CheckReturnValue
    public final Single<Long> count() {
        return RxJavaPlugins.onAssembly(new FlowableCountSingle(this));
    }

    @SchedulerSupport(SchedulerSupport.COMPUTATION)
    @BackpressureSupport(BackpressureKind.ERROR)
    @CheckReturnValue
    public final Flowable<T> debounce(long j, TimeUnit timeUnit) {
        return debounce(j, timeUnit, Schedulers.computation());
    }

    @SchedulerSupport("custom")
    @BackpressureSupport(BackpressureKind.ERROR)
    @CheckReturnValue
    public final Flowable<T> debounce(long j, TimeUnit timeUnit, Scheduler scheduler) {
        ObjectHelper.requireNonNull(timeUnit, "unit is null");
        ObjectHelper.requireNonNull(scheduler, "scheduler is null");
        return RxJavaPlugins.onAssembly(new FlowableDebounceTimed(this, j, timeUnit, scheduler));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.ERROR)
    @CheckReturnValue
    public final <U> Flowable<T> debounce(Function<? super T, ? extends Publisher<U>> function) {
        ObjectHelper.requireNonNull(function, "debounceIndicator is null");
        return RxJavaPlugins.onAssembly(new FlowableDebounce(this, function));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final Flowable<T> defaultIfEmpty(T t) {
        ObjectHelper.requireNonNull(t, "item is null");
        return switchIfEmpty(just(t));
    }

    @SchedulerSupport(SchedulerSupport.COMPUTATION)
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final Flowable<T> delay(long j, TimeUnit timeUnit) {
        return delay(j, timeUnit, Schedulers.computation(), false);
    }

    @SchedulerSupport("custom")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final Flowable<T> delay(long j, TimeUnit timeUnit, Scheduler scheduler) {
        return delay(j, timeUnit, scheduler, false);
    }

    @SchedulerSupport("custom")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final Flowable<T> delay(long j, TimeUnit timeUnit, Scheduler scheduler, boolean z) {
        ObjectHelper.requireNonNull(timeUnit, "unit is null");
        ObjectHelper.requireNonNull(scheduler, "scheduler is null");
        return RxJavaPlugins.onAssembly(new FlowableDelay(this, Math.max(0L, j), timeUnit, scheduler, z));
    }

    @SchedulerSupport(SchedulerSupport.COMPUTATION)
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final Flowable<T> delay(long j, TimeUnit timeUnit, boolean z) {
        return delay(j, timeUnit, Schedulers.computation(), z);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final <U> Flowable<T> delay(Function<? super T, ? extends Publisher<U>> function) {
        ObjectHelper.requireNonNull(function, "itemDelayIndicator is null");
        return flatMap(FlowableInternalHelper.itemDelay(function));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final <U, V> Flowable<T> delay(Publisher<U> publisher, Function<? super T, ? extends Publisher<V>> function) {
        return delaySubscription(publisher).delay(function);
    }

    @SchedulerSupport(SchedulerSupport.COMPUTATION)
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final Flowable<T> delaySubscription(long j, TimeUnit timeUnit) {
        return delaySubscription(j, timeUnit, Schedulers.computation());
    }

    @SchedulerSupport("custom")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final Flowable<T> delaySubscription(long j, TimeUnit timeUnit, Scheduler scheduler) {
        return delaySubscription(timer(j, timeUnit, scheduler));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final <U> Flowable<T> delaySubscription(Publisher<U> publisher) {
        ObjectHelper.requireNonNull(publisher, "subscriptionIndicator is null");
        return RxJavaPlugins.onAssembly(new FlowableDelaySubscriptionOther(this, publisher));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final <T2> Flowable<T2> dematerialize() {
        return RxJavaPlugins.onAssembly(new FlowableDematerialize(this));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final Flowable<T> distinct() {
        return distinct(Functions.identity(), Functions.createHashSet());
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final <K> Flowable<T> distinct(Function<? super T, K> function) {
        return distinct(function, Functions.createHashSet());
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final <K> Flowable<T> distinct(Function<? super T, K> function, Callable<? extends Collection<? super K>> callable) {
        ObjectHelper.requireNonNull(function, "keySelector is null");
        ObjectHelper.requireNonNull(callable, "collectionSupplier is null");
        return RxJavaPlugins.onAssembly(new FlowableDistinct(this, function, callable));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final Flowable<T> distinctUntilChanged() {
        return distinctUntilChanged(Functions.identity());
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final Flowable<T> distinctUntilChanged(BiPredicate<? super T, ? super T> biPredicate) {
        ObjectHelper.requireNonNull(biPredicate, "comparer is null");
        return RxJavaPlugins.onAssembly(new FlowableDistinctUntilChanged(this, Functions.identity(), biPredicate));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final <K> Flowable<T> distinctUntilChanged(Function<? super T, K> function) {
        ObjectHelper.requireNonNull(function, "keySelector is null");
        return RxJavaPlugins.onAssembly(new FlowableDistinctUntilChanged(this, function, ObjectHelper.equalsPredicate()));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @CheckReturnValue
    public final Flowable<T> doAfterNext(Consumer<? super T> consumer) {
        ObjectHelper.requireNonNull(consumer, "onAfterNext is null");
        return RxJavaPlugins.onAssembly(new FlowableDoAfterNext(this, consumer));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @CheckReturnValue
    public final Flowable<T> doAfterTerminate(Action action) {
        return doOnEach(Functions.emptyConsumer(), Functions.emptyConsumer(), Functions.EMPTY_ACTION, action);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @CheckReturnValue
    public final Flowable<T> doFinally(Action action) {
        ObjectHelper.requireNonNull(action, "onFinally is null");
        return RxJavaPlugins.onAssembly(new FlowableDoFinally(this, action));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @CheckReturnValue
    public final Flowable<T> doOnCancel(Action action) {
        return doOnLifecycle(Functions.emptyConsumer(), Functions.EMPTY_LONG_CONSUMER, action);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @CheckReturnValue
    public final Flowable<T> doOnComplete(Action action) {
        return doOnEach(Functions.emptyConsumer(), Functions.emptyConsumer(), action, Functions.EMPTY_ACTION);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @CheckReturnValue
    public final Flowable<T> doOnEach(Consumer<? super Notification<T>> consumer) {
        ObjectHelper.requireNonNull(consumer, "consumer is null");
        return doOnEach(Functions.notificationOnNext(consumer), Functions.notificationOnError(consumer), Functions.notificationOnComplete(consumer), Functions.EMPTY_ACTION);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @CheckReturnValue
    public final Flowable<T> doOnEach(Subscriber<? super T> subscriber) {
        ObjectHelper.requireNonNull(subscriber, "subscriber is null");
        return doOnEach(FlowableInternalHelper.subscriberOnNext(subscriber), FlowableInternalHelper.subscriberOnError(subscriber), FlowableInternalHelper.subscriberOnComplete(subscriber), Functions.EMPTY_ACTION);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @CheckReturnValue
    public final Flowable<T> doOnError(Consumer<? super Throwable> consumer) {
        Consumer<? super T> emptyConsumer = Functions.emptyConsumer();
        Action action = Functions.EMPTY_ACTION;
        return doOnEach(emptyConsumer, consumer, action, action);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @CheckReturnValue
    public final Flowable<T> doOnLifecycle(Consumer<? super Subscription> consumer, LongConsumer longConsumer, Action action) {
        ObjectHelper.requireNonNull(consumer, "onSubscribe is null");
        ObjectHelper.requireNonNull(longConsumer, "onRequest is null");
        ObjectHelper.requireNonNull(action, "onCancel is null");
        return RxJavaPlugins.onAssembly(new FlowableDoOnLifecycle(this, consumer, longConsumer, action));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @CheckReturnValue
    public final Flowable<T> doOnNext(Consumer<? super T> consumer) {
        Consumer<? super Throwable> emptyConsumer = Functions.emptyConsumer();
        Action action = Functions.EMPTY_ACTION;
        return doOnEach(consumer, emptyConsumer, action, action);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @CheckReturnValue
    public final Flowable<T> doOnRequest(LongConsumer longConsumer) {
        return doOnLifecycle(Functions.emptyConsumer(), longConsumer, Functions.EMPTY_ACTION);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @CheckReturnValue
    public final Flowable<T> doOnSubscribe(Consumer<? super Subscription> consumer) {
        return doOnLifecycle(consumer, Functions.EMPTY_LONG_CONSUMER, Functions.EMPTY_ACTION);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @CheckReturnValue
    public final Flowable<T> doOnTerminate(Action action) {
        return doOnEach(Functions.emptyConsumer(), Functions.actionConsumer(action), action, Functions.EMPTY_ACTION);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @CheckReturnValue
    public final Maybe<T> elementAt(long j) {
        if (j >= 0) {
            return RxJavaPlugins.onAssembly(new FlowableElementAtMaybe(this, j));
        }
        throw new IndexOutOfBoundsException("index >= 0 required but it was " + j);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @CheckReturnValue
    public final Single<T> elementAt(long j, T t) {
        if (j >= 0) {
            ObjectHelper.requireNonNull(t, "defaultItem is null");
            return RxJavaPlugins.onAssembly(new FlowableElementAtSingle(this, j, t));
        }
        throw new IndexOutOfBoundsException("index >= 0 required but it was " + j);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @CheckReturnValue
    public final Single<T> elementAtOrError(long j) {
        if (j >= 0) {
            return RxJavaPlugins.onAssembly(new FlowableElementAtSingle(this, j, null));
        }
        throw new IndexOutOfBoundsException("index >= 0 required but it was " + j);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @CheckReturnValue
    public final Flowable<T> filter(Predicate<? super T> predicate) {
        ObjectHelper.requireNonNull(predicate, "predicate is null");
        return RxJavaPlugins.onAssembly(new FlowableFilter(this, predicate));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.SPECIAL)
    @CheckReturnValue
    public final Single<T> first(T t) {
        return elementAt(0, t);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.SPECIAL)
    @CheckReturnValue
    public final Maybe<T> firstElement() {
        return elementAt(0);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.SPECIAL)
    @CheckReturnValue
    public final Single<T> firstOrError() {
        return elementAtOrError(0);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final <R> Flowable<R> flatMap(Function<? super T, ? extends Publisher<? extends R>> function) {
        return flatMap((Function) function, false, bufferSize(), bufferSize());
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final <R> Flowable<R> flatMap(Function<? super T, ? extends Publisher<? extends R>> function, int i) {
        return flatMap((Function) function, false, i, bufferSize());
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final <U, R> Flowable<R> flatMap(Function<? super T, ? extends Publisher<? extends U>> function, BiFunction<? super T, ? super U, ? extends R> biFunction) {
        return flatMap(function, biFunction, false, bufferSize(), bufferSize());
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final <U, R> Flowable<R> flatMap(Function<? super T, ? extends Publisher<? extends U>> function, BiFunction<? super T, ? super U, ? extends R> biFunction, int i) {
        return flatMap(function, biFunction, false, i, bufferSize());
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final <U, R> Flowable<R> flatMap(Function<? super T, ? extends Publisher<? extends U>> function, BiFunction<? super T, ? super U, ? extends R> biFunction, boolean z) {
        return flatMap(function, biFunction, z, bufferSize(), bufferSize());
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final <U, R> Flowable<R> flatMap(Function<? super T, ? extends Publisher<? extends U>> function, BiFunction<? super T, ? super U, ? extends R> biFunction, boolean z, int i) {
        return flatMap(function, biFunction, z, i, bufferSize());
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final <U, R> Flowable<R> flatMap(Function<? super T, ? extends Publisher<? extends U>> function, BiFunction<? super T, ? super U, ? extends R> biFunction, boolean z, int i, int i2) {
        ObjectHelper.requireNonNull(function, "mapper is null");
        ObjectHelper.requireNonNull(biFunction, "combiner is null");
        ObjectHelper.verifyPositive(i, "maxConcurrency");
        ObjectHelper.verifyPositive(i2, "bufferSize");
        return flatMap(FlowableInternalHelper.flatMapWithCombiner(function, biFunction), z, i, i2);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final <R> Flowable<R> flatMap(Function<? super T, ? extends Publisher<? extends R>> function, Function<? super Throwable, ? extends Publisher<? extends R>> function2, Callable<? extends Publisher<? extends R>> callable) {
        ObjectHelper.requireNonNull(function, "onNextMapper is null");
        ObjectHelper.requireNonNull(function2, "onErrorMapper is null");
        ObjectHelper.requireNonNull(callable, "onCompleteSupplier is null");
        return merge(new FlowableMapNotification(this, function, function2, callable));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final <R> Flowable<R> flatMap(Function<? super T, ? extends Publisher<? extends R>> function, Function<Throwable, ? extends Publisher<? extends R>> function2, Callable<? extends Publisher<? extends R>> callable, int i) {
        ObjectHelper.requireNonNull(function, "onNextMapper is null");
        ObjectHelper.requireNonNull(function2, "onErrorMapper is null");
        ObjectHelper.requireNonNull(callable, "onCompleteSupplier is null");
        return merge(new FlowableMapNotification(this, function, function2, callable), i);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final <R> Flowable<R> flatMap(Function<? super T, ? extends Publisher<? extends R>> function, boolean z) {
        return flatMap(function, z, bufferSize(), bufferSize());
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final <R> Flowable<R> flatMap(Function<? super T, ? extends Publisher<? extends R>> function, boolean z, int i) {
        return flatMap(function, z, i, bufferSize());
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final <R> Flowable<R> flatMap(Function<? super T, ? extends Publisher<? extends R>> function, boolean z, int i, int i2) {
        ObjectHelper.requireNonNull(function, "mapper is null");
        ObjectHelper.verifyPositive(i, "maxConcurrency");
        ObjectHelper.verifyPositive(i2, "bufferSize");
        if (!(this instanceof ScalarCallable)) {
            return RxJavaPlugins.onAssembly(new FlowableFlatMap(this, function, z, i, i2));
        }
        T call = ((ScalarCallable) this).call();
        return call == null ? empty() : FlowableScalarXMap.scalarXMap(call, function);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @CheckReturnValue
    public final Completable flatMapCompletable(Function<? super T, ? extends CompletableSource> function) {
        return flatMapCompletable(function, false, Integer.MAX_VALUE);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @CheckReturnValue
    public final Completable flatMapCompletable(Function<? super T, ? extends CompletableSource> function, boolean z, int i) {
        ObjectHelper.requireNonNull(function, "mapper is null");
        ObjectHelper.verifyPositive(i, "maxConcurrency");
        return RxJavaPlugins.onAssembly(new FlowableFlatMapCompletableCompletable(this, function, z, i));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final <U> Flowable<U> flatMapIterable(Function<? super T, ? extends Iterable<? extends U>> function) {
        return flatMapIterable(function, bufferSize());
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final <U> Flowable<U> flatMapIterable(Function<? super T, ? extends Iterable<? extends U>> function, int i) {
        ObjectHelper.requireNonNull(function, "mapper is null");
        ObjectHelper.verifyPositive(i, "bufferSize");
        return RxJavaPlugins.onAssembly(new FlowableFlattenIterable(this, function, i));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final <U, V> Flowable<V> flatMapIterable(Function<? super T, ? extends Iterable<? extends U>> function, BiFunction<? super T, ? super U, ? extends V> biFunction) {
        ObjectHelper.requireNonNull(function, "mapper is null");
        ObjectHelper.requireNonNull(biFunction, "resultSelector is null");
        return flatMap(FlowableInternalHelper.flatMapIntoIterable(function), biFunction, false, bufferSize(), bufferSize());
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final <U, V> Flowable<V> flatMapIterable(Function<? super T, ? extends Iterable<? extends U>> function, BiFunction<? super T, ? super U, ? extends V> biFunction, int i) {
        ObjectHelper.requireNonNull(function, "mapper is null");
        ObjectHelper.requireNonNull(biFunction, "resultSelector is null");
        return flatMap(FlowableInternalHelper.flatMapIntoIterable(function), biFunction, false, bufferSize(), i);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @CheckReturnValue
    public final <R> Flowable<R> flatMapMaybe(Function<? super T, ? extends MaybeSource<? extends R>> function) {
        return flatMapMaybe(function, false, Integer.MAX_VALUE);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @CheckReturnValue
    public final <R> Flowable<R> flatMapMaybe(Function<? super T, ? extends MaybeSource<? extends R>> function, boolean z, int i) {
        ObjectHelper.requireNonNull(function, "mapper is null");
        ObjectHelper.verifyPositive(i, "maxConcurrency");
        return RxJavaPlugins.onAssembly(new FlowableFlatMapMaybe(this, function, z, i));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @CheckReturnValue
    public final <R> Flowable<R> flatMapSingle(Function<? super T, ? extends SingleSource<? extends R>> function) {
        return flatMapSingle(function, false, Integer.MAX_VALUE);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @CheckReturnValue
    public final <R> Flowable<R> flatMapSingle(Function<? super T, ? extends SingleSource<? extends R>> function, boolean z, int i) {
        ObjectHelper.requireNonNull(function, "mapper is null");
        ObjectHelper.verifyPositive(i, "maxConcurrency");
        return RxJavaPlugins.onAssembly(new FlowableFlatMapSingle(this, function, z, i));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.NONE)
    @CheckReturnValue
    public final Disposable forEach(Consumer<? super T> consumer) {
        return subscribe(consumer);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.NONE)
    @CheckReturnValue
    public final Disposable forEachWhile(Predicate<? super T> predicate) {
        return forEachWhile(predicate, Functions.ON_ERROR_MISSING, Functions.EMPTY_ACTION);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.NONE)
    @CheckReturnValue
    public final Disposable forEachWhile(Predicate<? super T> predicate, Consumer<? super Throwable> consumer) {
        return forEachWhile(predicate, consumer, Functions.EMPTY_ACTION);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.NONE)
    @CheckReturnValue
    public final Disposable forEachWhile(Predicate<? super T> predicate, Consumer<? super Throwable> consumer, Action action) {
        ObjectHelper.requireNonNull(predicate, "onNext is null");
        ObjectHelper.requireNonNull(consumer, "onError is null");
        ObjectHelper.requireNonNull(action, "onComplete is null");
        ForEachWhileSubscriber forEachWhileSubscriber = new ForEachWhileSubscriber(predicate, consumer, action);
        subscribe((FlowableSubscriber) forEachWhileSubscriber);
        return forEachWhileSubscriber;
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final <K> Flowable<GroupedFlowable<K, T>> groupBy(Function<? super T, ? extends K> function) {
        return groupBy(function, Functions.identity(), false, bufferSize());
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final <K, V> Flowable<GroupedFlowable<K, V>> groupBy(Function<? super T, ? extends K> function, Function<? super T, ? extends V> function2) {
        return groupBy(function, function2, false, bufferSize());
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final <K, V> Flowable<GroupedFlowable<K, V>> groupBy(Function<? super T, ? extends K> function, Function<? super T, ? extends V> function2, boolean z) {
        return groupBy(function, function2, z, bufferSize());
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final <K, V> Flowable<GroupedFlowable<K, V>> groupBy(Function<? super T, ? extends K> function, Function<? super T, ? extends V> function2, boolean z, int i) {
        ObjectHelper.requireNonNull(function, "keySelector is null");
        ObjectHelper.requireNonNull(function2, "valueSelector is null");
        ObjectHelper.verifyPositive(i, "bufferSize");
        return RxJavaPlugins.onAssembly(new FlowableGroupBy(this, function, function2, i, z));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final <K> Flowable<GroupedFlowable<K, T>> groupBy(Function<? super T, ? extends K> function, boolean z) {
        return groupBy(function, Functions.identity(), z, bufferSize());
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.ERROR)
    @CheckReturnValue
    public final <TRight, TLeftEnd, TRightEnd, R> Flowable<R> groupJoin(Publisher<? extends TRight> publisher, Function<? super T, ? extends Publisher<TLeftEnd>> function, Function<? super TRight, ? extends Publisher<TRightEnd>> function2, BiFunction<? super T, ? super Flowable<TRight>, ? extends R> biFunction) {
        ObjectHelper.requireNonNull(publisher, "other is null");
        ObjectHelper.requireNonNull(function, "leftEnd is null");
        ObjectHelper.requireNonNull(function2, "rightEnd is null");
        ObjectHelper.requireNonNull(biFunction, "resultSelector is null");
        return RxJavaPlugins.onAssembly(new FlowableGroupJoin(this, publisher, function, function2, biFunction));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @CheckReturnValue
    public final Flowable<T> hide() {
        return RxJavaPlugins.onAssembly(new FlowableHide(this));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @CheckReturnValue
    public final Completable ignoreElements() {
        return RxJavaPlugins.onAssembly(new FlowableIgnoreElementsCompletable(this));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @CheckReturnValue
    public final Single<Boolean> isEmpty() {
        return all(Functions.alwaysFalse());
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.ERROR)
    @CheckReturnValue
    public final <TRight, TLeftEnd, TRightEnd, R> Flowable<R> join(Publisher<? extends TRight> publisher, Function<? super T, ? extends Publisher<TLeftEnd>> function, Function<? super TRight, ? extends Publisher<TRightEnd>> function2, BiFunction<? super T, ? super TRight, ? extends R> biFunction) {
        ObjectHelper.requireNonNull(publisher, "other is null");
        ObjectHelper.requireNonNull(function, "leftEnd is null");
        ObjectHelper.requireNonNull(function2, "rightEnd is null");
        ObjectHelper.requireNonNull(biFunction, "resultSelector is null");
        return RxJavaPlugins.onAssembly(new FlowableJoin(this, publisher, function, function2, biFunction));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @CheckReturnValue
    public final Single<T> last(T t) {
        ObjectHelper.requireNonNull(t, "defaultItem");
        return RxJavaPlugins.onAssembly(new FlowableLastSingle(this, t));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @CheckReturnValue
    public final Maybe<T> lastElement() {
        return RxJavaPlugins.onAssembly(new FlowableLastMaybe(this));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @CheckReturnValue
    public final Single<T> lastOrError() {
        return RxJavaPlugins.onAssembly(new FlowableLastSingle(this, null));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.SPECIAL)
    @CheckReturnValue
    public final <R> Flowable<R> lift(FlowableOperator<? extends R, ? super T> flowableOperator) {
        ObjectHelper.requireNonNull(flowableOperator, "lifter is null");
        return RxJavaPlugins.onAssembly(new FlowableLift(this, flowableOperator));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.SPECIAL)
    @CheckReturnValue
    @Experimental
    public final Flowable<T> limit(long j) {
        if (j >= 0) {
            return RxJavaPlugins.onAssembly(new FlowableLimit(this, j));
        }
        throw new IllegalArgumentException("count >= 0 required but it was " + j);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @CheckReturnValue
    public final <R> Flowable<R> map(Function<? super T, ? extends R> function) {
        ObjectHelper.requireNonNull(function, "mapper is null");
        return RxJavaPlugins.onAssembly(new FlowableMap(this, function));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final Flowable<Notification<T>> materialize() {
        return RxJavaPlugins.onAssembly(new FlowableMaterialize(this));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final Flowable<T> mergeWith(Publisher<? extends T> publisher) {
        ObjectHelper.requireNonNull(publisher, "other is null");
        return merge(this, publisher);
    }

    @SchedulerSupport("custom")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final Flowable<T> observeOn(Scheduler scheduler) {
        return observeOn(scheduler, false, bufferSize());
    }

    @SchedulerSupport("custom")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final Flowable<T> observeOn(Scheduler scheduler, boolean z) {
        return observeOn(scheduler, z, bufferSize());
    }

    @SchedulerSupport("custom")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final Flowable<T> observeOn(Scheduler scheduler, boolean z, int i) {
        ObjectHelper.requireNonNull(scheduler, "scheduler is null");
        ObjectHelper.verifyPositive(i, "bufferSize");
        return RxJavaPlugins.onAssembly(new FlowableObserveOn(this, scheduler, z, i));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @CheckReturnValue
    public final <U> Flowable<U> ofType(Class<U> cls) {
        ObjectHelper.requireNonNull(cls, "clazz is null");
        return filter(Functions.isInstanceOf(cls)).cast(cls);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @CheckReturnValue
    public final Flowable<T> onBackpressureBuffer() {
        return onBackpressureBuffer(bufferSize(), false, true);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.ERROR)
    @CheckReturnValue
    public final Flowable<T> onBackpressureBuffer(int i) {
        return onBackpressureBuffer(i, false, false);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.ERROR)
    @CheckReturnValue
    public final Flowable<T> onBackpressureBuffer(int i, Action action) {
        return onBackpressureBuffer(i, false, false, action);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.ERROR)
    @CheckReturnValue
    public final Flowable<T> onBackpressureBuffer(int i, boolean z) {
        return onBackpressureBuffer(i, z, false);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.SPECIAL)
    @CheckReturnValue
    public final Flowable<T> onBackpressureBuffer(int i, boolean z, boolean z2) {
        ObjectHelper.verifyPositive(i, "bufferSize");
        return RxJavaPlugins.onAssembly(new FlowableOnBackpressureBuffer(this, i, z2, z, Functions.EMPTY_ACTION));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.SPECIAL)
    @CheckReturnValue
    public final Flowable<T> onBackpressureBuffer(int i, boolean z, boolean z2, Action action) {
        ObjectHelper.requireNonNull(action, "onOverflow is null");
        ObjectHelper.verifyPositive(i, "capacity");
        return RxJavaPlugins.onAssembly(new FlowableOnBackpressureBuffer(this, i, z2, z, action));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.SPECIAL)
    @CheckReturnValue
    public final Flowable<T> onBackpressureBuffer(long j, Action action, BackpressureOverflowStrategy backpressureOverflowStrategy) {
        ObjectHelper.requireNonNull(backpressureOverflowStrategy, "strategy is null");
        ObjectHelper.verifyPositive(j, "capacity");
        return RxJavaPlugins.onAssembly(new FlowableOnBackpressureBufferStrategy(this, j, action, backpressureOverflowStrategy));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @CheckReturnValue
    public final Flowable<T> onBackpressureBuffer(boolean z) {
        return onBackpressureBuffer(bufferSize(), z, true);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @CheckReturnValue
    public final Flowable<T> onBackpressureDrop() {
        return RxJavaPlugins.onAssembly(new FlowableOnBackpressureDrop(this));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @CheckReturnValue
    public final Flowable<T> onBackpressureDrop(Consumer<? super T> consumer) {
        ObjectHelper.requireNonNull(consumer, "onDrop is null");
        return RxJavaPlugins.onAssembly(new FlowableOnBackpressureDrop(this, consumer));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @CheckReturnValue
    public final Flowable<T> onBackpressureLatest() {
        return RxJavaPlugins.onAssembly(new FlowableOnBackpressureLatest(this));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final Flowable<T> onErrorResumeNext(Function<? super Throwable, ? extends Publisher<? extends T>> function) {
        ObjectHelper.requireNonNull(function, "resumeFunction is null");
        return RxJavaPlugins.onAssembly(new FlowableOnErrorNext(this, function, false));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final Flowable<T> onErrorResumeNext(Publisher<? extends T> publisher) {
        ObjectHelper.requireNonNull(publisher, "next is null");
        return onErrorResumeNext(Functions.justFunction(publisher));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final Flowable<T> onErrorReturn(Function<? super Throwable, ? extends T> function) {
        ObjectHelper.requireNonNull(function, "valueSupplier is null");
        return RxJavaPlugins.onAssembly(new FlowableOnErrorReturn(this, function));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final Flowable<T> onErrorReturnItem(T t) {
        ObjectHelper.requireNonNull(t, "item is null");
        return onErrorReturn(Functions.justFunction(t));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final Flowable<T> onExceptionResumeNext(Publisher<? extends T> publisher) {
        ObjectHelper.requireNonNull(publisher, "next is null");
        return RxJavaPlugins.onAssembly(new FlowableOnErrorNext(this, Functions.justFunction(publisher), true));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @CheckReturnValue
    public final Flowable<T> onTerminateDetach() {
        return RxJavaPlugins.onAssembly(new FlowableDetach(this));
    }

    @Beta
    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final ParallelFlowable<T> parallel() {
        return ParallelFlowable.from(this);
    }

    @Beta
    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final ParallelFlowable<T> parallel(int i) {
        ObjectHelper.verifyPositive(i, "parallelism");
        return ParallelFlowable.from(this, i);
    }

    @Beta
    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final ParallelFlowable<T> parallel(int i, int i2) {
        ObjectHelper.verifyPositive(i, "parallelism");
        ObjectHelper.verifyPositive(i2, "prefetch");
        return ParallelFlowable.from(this, i, i2);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final <R> Flowable<R> publish(Function<? super Flowable<T>, ? extends Publisher<R>> function) {
        return publish(function, bufferSize());
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final <R> Flowable<R> publish(Function<? super Flowable<T>, ? extends Publisher<? extends R>> function, int i) {
        ObjectHelper.requireNonNull(function, "selector is null");
        ObjectHelper.verifyPositive(i, "prefetch");
        return RxJavaPlugins.onAssembly(new FlowablePublishMulticast(this, function, i, false));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final ConnectableFlowable<T> publish() {
        return publish(bufferSize());
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final ConnectableFlowable<T> publish(int i) {
        ObjectHelper.verifyPositive(i, "bufferSize");
        return FlowablePublish.create(this, i);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final Flowable<T> rebatchRequests(int i) {
        return observeOn(ImmediateThinScheduler.INSTANCE, true, i);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @CheckReturnValue
    public final Maybe<T> reduce(BiFunction<T, T, T> biFunction) {
        ObjectHelper.requireNonNull(biFunction, "reducer is null");
        return RxJavaPlugins.onAssembly(new FlowableReduceMaybe(this, biFunction));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @CheckReturnValue
    public final <R> Single<R> reduce(R r, BiFunction<R, ? super T, R> biFunction) {
        ObjectHelper.requireNonNull(r, "seed is null");
        ObjectHelper.requireNonNull(biFunction, "reducer is null");
        return RxJavaPlugins.onAssembly(new FlowableReduceSeedSingle(this, r, biFunction));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @CheckReturnValue
    public final <R> Single<R> reduceWith(Callable<R> callable, BiFunction<R, ? super T, R> biFunction) {
        ObjectHelper.requireNonNull(callable, "seedSupplier is null");
        ObjectHelper.requireNonNull(biFunction, "reducer is null");
        return RxJavaPlugins.onAssembly(new FlowableReduceWithSingle(this, callable, biFunction));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final Flowable<T> repeat() {
        return repeat(Long.MAX_VALUE);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final Flowable<T> repeat(long j) {
        int i = (j > 0 ? 1 : (j == 0 ? 0 : -1));
        if (i >= 0) {
            return i == 0 ? empty() : RxJavaPlugins.onAssembly(new FlowableRepeat(this, j));
        }
        throw new IllegalArgumentException("times >= 0 required but it was " + j);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final Flowable<T> repeatUntil(BooleanSupplier booleanSupplier) {
        ObjectHelper.requireNonNull(booleanSupplier, "stop is null");
        return RxJavaPlugins.onAssembly(new FlowableRepeatUntil(this, booleanSupplier));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final Flowable<T> repeatWhen(Function<? super Flowable<Object>, ? extends Publisher<?>> function) {
        ObjectHelper.requireNonNull(function, "handler is null");
        return RxJavaPlugins.onAssembly(new FlowableRepeatWhen(this, function));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final <R> Flowable<R> replay(Function<? super Flowable<T>, ? extends Publisher<R>> function) {
        ObjectHelper.requireNonNull(function, "selector is null");
        return FlowableReplay.multicastSelector(FlowableInternalHelper.replayCallable(this), function);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final <R> Flowable<R> replay(Function<? super Flowable<T>, ? extends Publisher<R>> function, int i) {
        ObjectHelper.requireNonNull(function, "selector is null");
        ObjectHelper.verifyPositive(i, "bufferSize");
        return FlowableReplay.multicastSelector(FlowableInternalHelper.replayCallable(this, i), function);
    }

    @SchedulerSupport(SchedulerSupport.COMPUTATION)
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final <R> Flowable<R> replay(Function<? super Flowable<T>, ? extends Publisher<R>> function, int i, long j, TimeUnit timeUnit) {
        return replay(function, i, j, timeUnit, Schedulers.computation());
    }

    @SchedulerSupport("custom")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final <R> Flowable<R> replay(Function<? super Flowable<T>, ? extends Publisher<R>> function, int i, long j, TimeUnit timeUnit, Scheduler scheduler) {
        ObjectHelper.requireNonNull(function, "selector is null");
        ObjectHelper.requireNonNull(timeUnit, "unit is null");
        ObjectHelper.verifyPositive(i, "bufferSize");
        ObjectHelper.requireNonNull(scheduler, "scheduler is null");
        return FlowableReplay.multicastSelector(FlowableInternalHelper.replayCallable(this, i, j, timeUnit, scheduler), function);
    }

    @SchedulerSupport("custom")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final <R> Flowable<R> replay(Function<? super Flowable<T>, ? extends Publisher<R>> function, int i, Scheduler scheduler) {
        ObjectHelper.requireNonNull(function, "selector is null");
        ObjectHelper.requireNonNull(scheduler, "scheduler is null");
        ObjectHelper.verifyPositive(i, "bufferSize");
        return FlowableReplay.multicastSelector(FlowableInternalHelper.replayCallable(this, i), FlowableInternalHelper.replayFunction(function, scheduler));
    }

    @SchedulerSupport(SchedulerSupport.COMPUTATION)
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final <R> Flowable<R> replay(Function<? super Flowable<T>, ? extends Publisher<R>> function, long j, TimeUnit timeUnit) {
        return replay(function, j, timeUnit, Schedulers.computation());
    }

    @SchedulerSupport("custom")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final <R> Flowable<R> replay(Function<? super Flowable<T>, ? extends Publisher<R>> function, long j, TimeUnit timeUnit, Scheduler scheduler) {
        ObjectHelper.requireNonNull(function, "selector is null");
        ObjectHelper.requireNonNull(timeUnit, "unit is null");
        ObjectHelper.requireNonNull(scheduler, "scheduler is null");
        return FlowableReplay.multicastSelector(FlowableInternalHelper.replayCallable(this, j, timeUnit, scheduler), function);
    }

    @SchedulerSupport("custom")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final <R> Flowable<R> replay(Function<? super Flowable<T>, ? extends Publisher<R>> function, Scheduler scheduler) {
        ObjectHelper.requireNonNull(function, "selector is null");
        ObjectHelper.requireNonNull(scheduler, "scheduler is null");
        return FlowableReplay.multicastSelector(FlowableInternalHelper.replayCallable(this), FlowableInternalHelper.replayFunction(function, scheduler));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final ConnectableFlowable<T> replay() {
        return FlowableReplay.createFrom(this);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final ConnectableFlowable<T> replay(int i) {
        ObjectHelper.verifyPositive(i, "bufferSize");
        return FlowableReplay.create(this, i);
    }

    @SchedulerSupport(SchedulerSupport.COMPUTATION)
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final ConnectableFlowable<T> replay(int i, long j, TimeUnit timeUnit) {
        return replay(i, j, timeUnit, Schedulers.computation());
    }

    @SchedulerSupport("custom")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final ConnectableFlowable<T> replay(int i, long j, TimeUnit timeUnit, Scheduler scheduler) {
        ObjectHelper.verifyPositive(i, "bufferSize");
        ObjectHelper.requireNonNull(timeUnit, "unit is null");
        ObjectHelper.requireNonNull(scheduler, "scheduler is null");
        ObjectHelper.verifyPositive(i, "bufferSize");
        return FlowableReplay.create(this, j, timeUnit, scheduler, i);
    }

    @SchedulerSupport("custom")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final ConnectableFlowable<T> replay(int i, Scheduler scheduler) {
        ObjectHelper.requireNonNull(scheduler, "scheduler is null");
        return FlowableReplay.observeOn(replay(i), scheduler);
    }

    @SchedulerSupport(SchedulerSupport.COMPUTATION)
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final ConnectableFlowable<T> replay(long j, TimeUnit timeUnit) {
        return replay(j, timeUnit, Schedulers.computation());
    }

    @SchedulerSupport("custom")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final ConnectableFlowable<T> replay(long j, TimeUnit timeUnit, Scheduler scheduler) {
        ObjectHelper.requireNonNull(timeUnit, "unit is null");
        ObjectHelper.requireNonNull(scheduler, "scheduler is null");
        return FlowableReplay.create(this, j, timeUnit, scheduler);
    }

    @SchedulerSupport("custom")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final ConnectableFlowable<T> replay(Scheduler scheduler) {
        ObjectHelper.requireNonNull(scheduler, "scheduler is null");
        return FlowableReplay.observeOn(replay(), scheduler);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final Flowable<T> retry() {
        return retry(Long.MAX_VALUE, Functions.alwaysTrue());
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final Flowable<T> retry(long j) {
        return retry(j, Functions.alwaysTrue());
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final Flowable<T> retry(long j, Predicate<? super Throwable> predicate) {
        if (j >= 0) {
            ObjectHelper.requireNonNull(predicate, "predicate is null");
            return RxJavaPlugins.onAssembly(new FlowableRetryPredicate(this, j, predicate));
        }
        throw new IllegalArgumentException("times >= 0 required but it was " + j);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final Flowable<T> retry(BiPredicate<? super Integer, ? super Throwable> biPredicate) {
        ObjectHelper.requireNonNull(biPredicate, "predicate is null");
        return RxJavaPlugins.onAssembly(new FlowableRetryBiPredicate(this, biPredicate));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final Flowable<T> retry(Predicate<? super Throwable> predicate) {
        return retry(Long.MAX_VALUE, predicate);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final Flowable<T> retryUntil(BooleanSupplier booleanSupplier) {
        ObjectHelper.requireNonNull(booleanSupplier, "stop is null");
        return retry(Long.MAX_VALUE, Functions.predicateReverseFor(booleanSupplier));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final Flowable<T> retryWhen(Function<? super Flowable<Throwable>, ? extends Publisher<?>> function) {
        ObjectHelper.requireNonNull(function, "handler is null");
        return RxJavaPlugins.onAssembly(new FlowableRetryWhen(this, function));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    public final void safeSubscribe(Subscriber<? super T> subscriber) {
        ObjectHelper.requireNonNull(subscriber, "s is null");
        if (subscriber instanceof SafeSubscriber) {
            subscribe((FlowableSubscriber) ((SafeSubscriber) subscriber));
        } else {
            subscribe((FlowableSubscriber) new SafeSubscriber(subscriber));
        }
    }

    @SchedulerSupport(SchedulerSupport.COMPUTATION)
    @BackpressureSupport(BackpressureKind.ERROR)
    @CheckReturnValue
    public final Flowable<T> sample(long j, TimeUnit timeUnit) {
        return sample(j, timeUnit, Schedulers.computation());
    }

    @SchedulerSupport("custom")
    @BackpressureSupport(BackpressureKind.ERROR)
    @CheckReturnValue
    public final Flowable<T> sample(long j, TimeUnit timeUnit, Scheduler scheduler) {
        ObjectHelper.requireNonNull(timeUnit, "unit is null");
        ObjectHelper.requireNonNull(scheduler, "scheduler is null");
        return RxJavaPlugins.onAssembly(new FlowableSampleTimed(this, j, timeUnit, scheduler, false));
    }

    @SchedulerSupport("custom")
    @BackpressureSupport(BackpressureKind.ERROR)
    @CheckReturnValue
    public final Flowable<T> sample(long j, TimeUnit timeUnit, Scheduler scheduler, boolean z) {
        ObjectHelper.requireNonNull(timeUnit, "unit is null");
        ObjectHelper.requireNonNull(scheduler, "scheduler is null");
        return RxJavaPlugins.onAssembly(new FlowableSampleTimed(this, j, timeUnit, scheduler, z));
    }

    @SchedulerSupport(SchedulerSupport.COMPUTATION)
    @BackpressureSupport(BackpressureKind.ERROR)
    @CheckReturnValue
    public final Flowable<T> sample(long j, TimeUnit timeUnit, boolean z) {
        return sample(j, timeUnit, Schedulers.computation(), z);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.ERROR)
    @CheckReturnValue
    public final <U> Flowable<T> sample(Publisher<U> publisher) {
        ObjectHelper.requireNonNull(publisher, "sampler is null");
        return RxJavaPlugins.onAssembly(new FlowableSamplePublisher(this, publisher, false));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.ERROR)
    @CheckReturnValue
    public final <U> Flowable<T> sample(Publisher<U> publisher, boolean z) {
        ObjectHelper.requireNonNull(publisher, "sampler is null");
        return RxJavaPlugins.onAssembly(new FlowableSamplePublisher(this, publisher, z));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final Flowable<T> scan(BiFunction<T, T, T> biFunction) {
        ObjectHelper.requireNonNull(biFunction, "accumulator is null");
        return RxJavaPlugins.onAssembly(new FlowableScan(this, biFunction));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final <R> Flowable<R> scan(R r, BiFunction<R, ? super T, R> biFunction) {
        ObjectHelper.requireNonNull(r, "seed is null");
        return scanWith(Functions.justCallable(r), biFunction);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final <R> Flowable<R> scanWith(Callable<R> callable, BiFunction<R, ? super T, R> biFunction) {
        ObjectHelper.requireNonNull(callable, "seedSupplier is null");
        ObjectHelper.requireNonNull(biFunction, "accumulator is null");
        return RxJavaPlugins.onAssembly(new FlowableScanSeed(this, callable, biFunction));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @CheckReturnValue
    public final Flowable<T> serialize() {
        return RxJavaPlugins.onAssembly(new FlowableSerialized(this));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final Flowable<T> share() {
        return publish().refCount();
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @CheckReturnValue
    public final Single<T> single(T t) {
        ObjectHelper.requireNonNull(t, "defaultItem is null");
        return RxJavaPlugins.onAssembly(new FlowableSingleSingle(this, t));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @CheckReturnValue
    public final Maybe<T> singleElement() {
        return RxJavaPlugins.onAssembly(new FlowableSingleMaybe(this));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @CheckReturnValue
    public final Single<T> singleOrError() {
        return RxJavaPlugins.onAssembly(new FlowableSingleSingle(this, null));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final Flowable<T> skip(long j) {
        return j <= 0 ? RxJavaPlugins.onAssembly(this) : RxJavaPlugins.onAssembly(new FlowableSkip(this, j));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final Flowable<T> skip(long j, TimeUnit timeUnit) {
        return skipUntil(timer(j, timeUnit));
    }

    @SchedulerSupport("custom")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final Flowable<T> skip(long j, TimeUnit timeUnit, Scheduler scheduler) {
        return skipUntil(timer(j, timeUnit, scheduler));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final Flowable<T> skipLast(int i) {
        if (i >= 0) {
            return i == 0 ? RxJavaPlugins.onAssembly(this) : RxJavaPlugins.onAssembly(new FlowableSkipLast(this, i));
        }
        throw new IndexOutOfBoundsException("count >= 0 required but it was " + i);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @CheckReturnValue
    public final Flowable<T> skipLast(long j, TimeUnit timeUnit) {
        return skipLast(j, timeUnit, Schedulers.computation(), false, bufferSize());
    }

    @SchedulerSupport("custom")
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @CheckReturnValue
    public final Flowable<T> skipLast(long j, TimeUnit timeUnit, Scheduler scheduler) {
        return skipLast(j, timeUnit, scheduler, false, bufferSize());
    }

    @SchedulerSupport("custom")
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @CheckReturnValue
    public final Flowable<T> skipLast(long j, TimeUnit timeUnit, Scheduler scheduler, boolean z) {
        return skipLast(j, timeUnit, scheduler, z, bufferSize());
    }

    @SchedulerSupport("custom")
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @CheckReturnValue
    public final Flowable<T> skipLast(long j, TimeUnit timeUnit, Scheduler scheduler, boolean z, int i) {
        ObjectHelper.requireNonNull(timeUnit, "unit is null");
        ObjectHelper.requireNonNull(scheduler, "scheduler is null");
        ObjectHelper.verifyPositive(i, "bufferSize");
        return RxJavaPlugins.onAssembly(new FlowableSkipLastTimed(this, j, timeUnit, scheduler, i << 1, z));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @CheckReturnValue
    public final Flowable<T> skipLast(long j, TimeUnit timeUnit, boolean z) {
        return skipLast(j, timeUnit, Schedulers.computation(), z, bufferSize());
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final <U> Flowable<T> skipUntil(Publisher<U> publisher) {
        ObjectHelper.requireNonNull(publisher, "other is null");
        return RxJavaPlugins.onAssembly(new FlowableSkipUntil(this, publisher));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final Flowable<T> skipWhile(Predicate<? super T> predicate) {
        ObjectHelper.requireNonNull(predicate, "predicate is null");
        return RxJavaPlugins.onAssembly(new FlowableSkipWhile(this, predicate));
    }

    /* JADX DEBUG: Type inference failed for r1v4. Raw type applied. Possible types: io.reactivex.Flowable<U>, io.reactivex.Flowable<T> */
    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final Flowable<T> sorted() {
        return toList().toFlowable().map(Functions.listSorter(Functions.naturalComparator())).flatMapIterable(Functions.identity());
    }

    /* JADX DEBUG: Type inference failed for r1v4. Raw type applied. Possible types: io.reactivex.Flowable<U>, io.reactivex.Flowable<T> */
    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final Flowable<T> sorted(Comparator<? super T> comparator) {
        ObjectHelper.requireNonNull(comparator, "sortFunction");
        return toList().toFlowable().map(Functions.listSorter(comparator)).flatMapIterable(Functions.identity());
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final Flowable<T> startWith(Iterable<? extends T> iterable) {
        return concatArray(fromIterable(iterable), this);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final Flowable<T> startWith(T t) {
        ObjectHelper.requireNonNull(t, "item is null");
        return concatArray(just(t), this);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final Flowable<T> startWith(Publisher<? extends T> publisher) {
        ObjectHelper.requireNonNull(publisher, "other is null");
        return concatArray(publisher, this);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final Flowable<T> startWithArray(T... tArr) {
        Flowable fromArray = fromArray(tArr);
        if (fromArray == empty()) {
            return RxJavaPlugins.onAssembly(this);
        }
        return concatArray(fromArray, this);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    public final Disposable subscribe() {
        return subscribe(Functions.emptyConsumer(), Functions.ON_ERROR_MISSING, Functions.EMPTY_ACTION, FlowableInternalHelper.RequestMax.INSTANCE);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @CheckReturnValue
    public final Disposable subscribe(Consumer<? super T> consumer) {
        return subscribe(consumer, Functions.ON_ERROR_MISSING, Functions.EMPTY_ACTION, FlowableInternalHelper.RequestMax.INSTANCE);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @CheckReturnValue
    public final Disposable subscribe(Consumer<? super T> consumer, Consumer<? super Throwable> consumer2) {
        return subscribe(consumer, consumer2, Functions.EMPTY_ACTION, FlowableInternalHelper.RequestMax.INSTANCE);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @CheckReturnValue
    public final Disposable subscribe(Consumer<? super T> consumer, Consumer<? super Throwable> consumer2, Action action) {
        return subscribe(consumer, consumer2, action, FlowableInternalHelper.RequestMax.INSTANCE);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.SPECIAL)
    @CheckReturnValue
    public final Disposable subscribe(Consumer<? super T> consumer, Consumer<? super Throwable> consumer2, Action action, Consumer<? super Subscription> consumer3) {
        ObjectHelper.requireNonNull(consumer, "onNext is null");
        ObjectHelper.requireNonNull(consumer2, "onError is null");
        ObjectHelper.requireNonNull(action, "onComplete is null");
        ObjectHelper.requireNonNull(consumer3, "onSubscribe is null");
        LambdaSubscriber lambdaSubscriber = new LambdaSubscriber(consumer, consumer2, action, consumer3);
        subscribe((FlowableSubscriber) lambdaSubscriber);
        return lambdaSubscriber;
    }

    @Beta
    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.SPECIAL)
    public final void subscribe(FlowableSubscriber<? super T> flowableSubscriber) {
        ObjectHelper.requireNonNull(flowableSubscriber, "s is null");
        try {
            Subscriber<? super T> onSubscribe = RxJavaPlugins.onSubscribe(this, flowableSubscriber);
            ObjectHelper.requireNonNull(onSubscribe, "Plugin returned null Subscriber");
            subscribeActual(onSubscribe);
        } catch (NullPointerException e2) {
            throw e2;
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            RxJavaPlugins.onError(th);
            NullPointerException nullPointerException = new NullPointerException("Actually not, but can't throw other exceptions due to RS");
            nullPointerException.initCause(th);
            throw nullPointerException;
        }
    }

    @Override // org.reactivestreams.Publisher
    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.SPECIAL)
    public final void subscribe(Subscriber<? super T> subscriber) {
        if (subscriber instanceof FlowableSubscriber) {
            subscribe((FlowableSubscriber) ((FlowableSubscriber) subscriber));
            return;
        }
        ObjectHelper.requireNonNull(subscriber, "s is null");
        subscribe((FlowableSubscriber) new StrictSubscriber(subscriber));
    }

    /* access modifiers changed from: protected */
    public abstract void subscribeActual(Subscriber<? super T> subscriber);

    @SchedulerSupport("custom")
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @CheckReturnValue
    public final Flowable<T> subscribeOn(@NonNull Scheduler scheduler) {
        ObjectHelper.requireNonNull(scheduler, "scheduler is null");
        return subscribeOn(scheduler, !(this instanceof FlowableCreate));
    }

    @SchedulerSupport("custom")
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @CheckReturnValue
    @Experimental
    public final Flowable<T> subscribeOn(@NonNull Scheduler scheduler, boolean z) {
        ObjectHelper.requireNonNull(scheduler, "scheduler is null");
        return RxJavaPlugins.onAssembly(new FlowableSubscribeOn(this, scheduler, z));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.SPECIAL)
    @CheckReturnValue
    public final <E extends Subscriber<? super T>> E subscribeWith(E e2) {
        subscribe((Subscriber) e2);
        return e2;
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final Flowable<T> switchIfEmpty(Publisher<? extends T> publisher) {
        ObjectHelper.requireNonNull(publisher, "other is null");
        return RxJavaPlugins.onAssembly(new FlowableSwitchIfEmpty(this, publisher));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final <R> Flowable<R> switchMap(Function<? super T, ? extends Publisher<? extends R>> function) {
        return switchMap(function, bufferSize());
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final <R> Flowable<R> switchMap(Function<? super T, ? extends Publisher<? extends R>> function, int i) {
        return switchMap0(function, i, false);
    }

    /* access modifiers changed from: package-private */
    public <R> Flowable<R> switchMap0(Function<? super T, ? extends Publisher<? extends R>> function, int i, boolean z) {
        ObjectHelper.requireNonNull(function, "mapper is null");
        ObjectHelper.verifyPositive(i, "bufferSize");
        if (!(this instanceof ScalarCallable)) {
            return RxJavaPlugins.onAssembly(new FlowableSwitchMap(this, function, i, z));
        }
        T call = ((ScalarCallable) this).call();
        return call == null ? empty() : FlowableScalarXMap.scalarXMap(call, function);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.SPECIAL)
    @CheckReturnValue
    public final <R> Flowable<R> switchMapDelayError(Function<? super T, ? extends Publisher<? extends R>> function) {
        return switchMapDelayError(function, bufferSize());
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.SPECIAL)
    @CheckReturnValue
    public final <R> Flowable<R> switchMapDelayError(Function<? super T, ? extends Publisher<? extends R>> function, int i) {
        return switchMap0(function, i, true);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.SPECIAL)
    @CheckReturnValue
    public final Flowable<T> take(long j) {
        if (j >= 0) {
            return RxJavaPlugins.onAssembly(new FlowableTake(this, j));
        }
        throw new IllegalArgumentException("count >= 0 required but it was " + j);
    }

    @SchedulerSupport(SchedulerSupport.COMPUTATION)
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @CheckReturnValue
    public final Flowable<T> take(long j, TimeUnit timeUnit) {
        return takeUntil(timer(j, timeUnit));
    }

    @SchedulerSupport("custom")
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @CheckReturnValue
    public final Flowable<T> take(long j, TimeUnit timeUnit, Scheduler scheduler) {
        return takeUntil(timer(j, timeUnit, scheduler));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final Flowable<T> takeLast(int i) {
        if (i >= 0) {
            return i == 0 ? RxJavaPlugins.onAssembly(new FlowableIgnoreElements(this)) : i == 1 ? RxJavaPlugins.onAssembly(new FlowableTakeLastOne(this)) : RxJavaPlugins.onAssembly(new FlowableTakeLast(this, i));
        }
        throw new IndexOutOfBoundsException("count >= 0 required but it was " + i);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final Flowable<T> takeLast(long j, long j2, TimeUnit timeUnit) {
        return takeLast(j, j2, timeUnit, Schedulers.computation(), false, bufferSize());
    }

    @SchedulerSupport("custom")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final Flowable<T> takeLast(long j, long j2, TimeUnit timeUnit, Scheduler scheduler) {
        return takeLast(j, j2, timeUnit, scheduler, false, bufferSize());
    }

    @SchedulerSupport("custom")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final Flowable<T> takeLast(long j, long j2, TimeUnit timeUnit, Scheduler scheduler, boolean z, int i) {
        ObjectHelper.requireNonNull(timeUnit, "unit is null");
        ObjectHelper.requireNonNull(scheduler, "scheduler is null");
        ObjectHelper.verifyPositive(i, "bufferSize");
        if (j >= 0) {
            return RxJavaPlugins.onAssembly(new FlowableTakeLastTimed(this, j, j2, timeUnit, scheduler, i, z));
        }
        throw new IndexOutOfBoundsException("count >= 0 required but it was " + j);
    }

    @SchedulerSupport(SchedulerSupport.COMPUTATION)
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final Flowable<T> takeLast(long j, TimeUnit timeUnit) {
        return takeLast(j, timeUnit, Schedulers.computation(), false, bufferSize());
    }

    @SchedulerSupport("custom")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final Flowable<T> takeLast(long j, TimeUnit timeUnit, Scheduler scheduler) {
        return takeLast(j, timeUnit, scheduler, false, bufferSize());
    }

    @SchedulerSupport("custom")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final Flowable<T> takeLast(long j, TimeUnit timeUnit, Scheduler scheduler, boolean z) {
        return takeLast(j, timeUnit, scheduler, z, bufferSize());
    }

    @SchedulerSupport("custom")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final Flowable<T> takeLast(long j, TimeUnit timeUnit, Scheduler scheduler, boolean z, int i) {
        return takeLast(Long.MAX_VALUE, j, timeUnit, scheduler, z, i);
    }

    @SchedulerSupport(SchedulerSupport.COMPUTATION)
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final Flowable<T> takeLast(long j, TimeUnit timeUnit, boolean z) {
        return takeLast(j, timeUnit, Schedulers.computation(), z, bufferSize());
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @CheckReturnValue
    public final Flowable<T> takeUntil(Predicate<? super T> predicate) {
        ObjectHelper.requireNonNull(predicate, "stopPredicate is null");
        return RxJavaPlugins.onAssembly(new FlowableTakeUntilPredicate(this, predicate));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @CheckReturnValue
    public final <U> Flowable<T> takeUntil(Publisher<U> publisher) {
        ObjectHelper.requireNonNull(publisher, "other is null");
        return RxJavaPlugins.onAssembly(new FlowableTakeUntil(this, publisher));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @CheckReturnValue
    public final Flowable<T> takeWhile(Predicate<? super T> predicate) {
        ObjectHelper.requireNonNull(predicate, "predicate is null");
        return RxJavaPlugins.onAssembly(new FlowableTakeWhile(this, predicate));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @CheckReturnValue
    public final TestSubscriber<T> test() {
        TestSubscriber<T> testSubscriber = new TestSubscriber<>();
        subscribe((FlowableSubscriber) testSubscriber);
        return testSubscriber;
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final TestSubscriber<T> test(long j) {
        TestSubscriber<T> testSubscriber = new TestSubscriber<>(j);
        subscribe((FlowableSubscriber) testSubscriber);
        return testSubscriber;
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final TestSubscriber<T> test(long j, boolean z) {
        TestSubscriber<T> testSubscriber = new TestSubscriber<>(j);
        if (z) {
            testSubscriber.cancel();
        }
        subscribe((FlowableSubscriber) testSubscriber);
        return testSubscriber;
    }

    @SchedulerSupport(SchedulerSupport.COMPUTATION)
    @BackpressureSupport(BackpressureKind.ERROR)
    @CheckReturnValue
    public final Flowable<T> throttleFirst(long j, TimeUnit timeUnit) {
        return throttleFirst(j, timeUnit, Schedulers.computation());
    }

    @SchedulerSupport("custom")
    @BackpressureSupport(BackpressureKind.ERROR)
    @CheckReturnValue
    public final Flowable<T> throttleFirst(long j, TimeUnit timeUnit, Scheduler scheduler) {
        ObjectHelper.requireNonNull(timeUnit, "unit is null");
        ObjectHelper.requireNonNull(scheduler, "scheduler is null");
        return RxJavaPlugins.onAssembly(new FlowableThrottleFirstTimed(this, j, timeUnit, scheduler));
    }

    @SchedulerSupport(SchedulerSupport.COMPUTATION)
    @BackpressureSupport(BackpressureKind.ERROR)
    @CheckReturnValue
    public final Flowable<T> throttleLast(long j, TimeUnit timeUnit) {
        return sample(j, timeUnit);
    }

    @SchedulerSupport("custom")
    @BackpressureSupport(BackpressureKind.ERROR)
    @CheckReturnValue
    public final Flowable<T> throttleLast(long j, TimeUnit timeUnit, Scheduler scheduler) {
        return sample(j, timeUnit, scheduler);
    }

    @SchedulerSupport(SchedulerSupport.COMPUTATION)
    @BackpressureSupport(BackpressureKind.ERROR)
    @CheckReturnValue
    public final Flowable<T> throttleWithTimeout(long j, TimeUnit timeUnit) {
        return debounce(j, timeUnit);
    }

    @SchedulerSupport("custom")
    @BackpressureSupport(BackpressureKind.ERROR)
    @CheckReturnValue
    public final Flowable<T> throttleWithTimeout(long j, TimeUnit timeUnit, Scheduler scheduler) {
        return debounce(j, timeUnit, scheduler);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @CheckReturnValue
    public final Flowable<Timed<T>> timeInterval() {
        return timeInterval(TimeUnit.MILLISECONDS, Schedulers.computation());
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @CheckReturnValue
    public final Flowable<Timed<T>> timeInterval(Scheduler scheduler) {
        return timeInterval(TimeUnit.MILLISECONDS, scheduler);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @CheckReturnValue
    public final Flowable<Timed<T>> timeInterval(TimeUnit timeUnit) {
        return timeInterval(timeUnit, Schedulers.computation());
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @CheckReturnValue
    public final Flowable<Timed<T>> timeInterval(TimeUnit timeUnit, Scheduler scheduler) {
        ObjectHelper.requireNonNull(timeUnit, "unit is null");
        ObjectHelper.requireNonNull(scheduler, "scheduler is null");
        return RxJavaPlugins.onAssembly(new FlowableTimeInterval(this, timeUnit, scheduler));
    }

    @SchedulerSupport(SchedulerSupport.COMPUTATION)
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @CheckReturnValue
    public final Flowable<T> timeout(long j, TimeUnit timeUnit) {
        return timeout0(j, timeUnit, null, Schedulers.computation());
    }

    @SchedulerSupport("custom")
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @CheckReturnValue
    public final Flowable<T> timeout(long j, TimeUnit timeUnit, Scheduler scheduler) {
        return timeout0(j, timeUnit, null, scheduler);
    }

    @SchedulerSupport("custom")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final Flowable<T> timeout(long j, TimeUnit timeUnit, Scheduler scheduler, Publisher<? extends T> publisher) {
        ObjectHelper.requireNonNull(publisher, "other is null");
        return timeout0(j, timeUnit, publisher, scheduler);
    }

    @SchedulerSupport(SchedulerSupport.COMPUTATION)
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final Flowable<T> timeout(long j, TimeUnit timeUnit, Publisher<? extends T> publisher) {
        ObjectHelper.requireNonNull(publisher, "other is null");
        return timeout0(j, timeUnit, publisher, Schedulers.computation());
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @CheckReturnValue
    public final <V> Flowable<T> timeout(Function<? super T, ? extends Publisher<V>> function) {
        return timeout0(null, function, null);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final <V> Flowable<T> timeout(Function<? super T, ? extends Publisher<V>> function, Flowable<? extends T> flowable) {
        ObjectHelper.requireNonNull(flowable, "other is null");
        return timeout0(null, function, flowable);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @CheckReturnValue
    public final <U, V> Flowable<T> timeout(Publisher<U> publisher, Function<? super T, ? extends Publisher<V>> function) {
        ObjectHelper.requireNonNull(publisher, "firstTimeoutIndicator is null");
        return timeout0(publisher, function, null);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final <U, V> Flowable<T> timeout(Publisher<U> publisher, Function<? super T, ? extends Publisher<V>> function, Publisher<? extends T> publisher2) {
        ObjectHelper.requireNonNull(publisher, "firstTimeoutSelector is null");
        ObjectHelper.requireNonNull(publisher2, "other is null");
        return timeout0(publisher, function, publisher2);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @CheckReturnValue
    public final Flowable<Timed<T>> timestamp() {
        return timestamp(TimeUnit.MILLISECONDS, Schedulers.computation());
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @CheckReturnValue
    public final Flowable<Timed<T>> timestamp(Scheduler scheduler) {
        return timestamp(TimeUnit.MILLISECONDS, scheduler);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @CheckReturnValue
    public final Flowable<Timed<T>> timestamp(TimeUnit timeUnit) {
        return timestamp(timeUnit, Schedulers.computation());
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @CheckReturnValue
    public final Flowable<Timed<T>> timestamp(TimeUnit timeUnit, Scheduler scheduler) {
        ObjectHelper.requireNonNull(timeUnit, "unit is null");
        ObjectHelper.requireNonNull(scheduler, "scheduler is null");
        return map(Functions.timestampWith(timeUnit, scheduler));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.SPECIAL)
    @CheckReturnValue
    public final <R> R to(Function<? super Flowable<T>, R> function) {
        try {
            ObjectHelper.requireNonNull(function, "converter is null");
            return function.apply(this);
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            throw ExceptionHelper.wrapOrThrow(th);
        }
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @CheckReturnValue
    public final Future<T> toFuture() {
        return (Future) subscribeWith(new FutureSubscriber());
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @CheckReturnValue
    public final Single<List<T>> toList() {
        return RxJavaPlugins.onAssembly(new FlowableToListSingle(this));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @CheckReturnValue
    public final Single<List<T>> toList(int i) {
        ObjectHelper.verifyPositive(i, "capacityHint");
        return RxJavaPlugins.onAssembly(new FlowableToListSingle(this, Functions.createArrayList(i)));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @CheckReturnValue
    public final <U extends Collection<? super T>> Single<U> toList(Callable<U> callable) {
        ObjectHelper.requireNonNull(callable, "collectionSupplier is null");
        return RxJavaPlugins.onAssembly(new FlowableToListSingle(this, callable));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @CheckReturnValue
    public final <K> Single<Map<K, T>> toMap(Function<? super T, ? extends K> function) {
        ObjectHelper.requireNonNull(function, "keySelector is null");
        return collect(HashMapSupplier.asCallable(), Functions.toMapKeySelector(function));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @CheckReturnValue
    public final <K, V> Single<Map<K, V>> toMap(Function<? super T, ? extends K> function, Function<? super T, ? extends V> function2) {
        ObjectHelper.requireNonNull(function, "keySelector is null");
        ObjectHelper.requireNonNull(function2, "valueSelector is null");
        return collect(HashMapSupplier.asCallable(), Functions.toMapKeyValueSelector(function, function2));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @CheckReturnValue
    public final <K, V> Single<Map<K, V>> toMap(Function<? super T, ? extends K> function, Function<? super T, ? extends V> function2, Callable<? extends Map<K, V>> callable) {
        ObjectHelper.requireNonNull(function, "keySelector is null");
        ObjectHelper.requireNonNull(function2, "valueSelector is null");
        return collect(callable, Functions.toMapKeyValueSelector(function, function2));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @CheckReturnValue
    public final <K> Single<Map<K, Collection<T>>> toMultimap(Function<? super T, ? extends K> function) {
        return toMultimap(function, Functions.identity(), HashMapSupplier.asCallable(), ArrayListSupplier.asFunction());
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @CheckReturnValue
    public final <K, V> Single<Map<K, Collection<V>>> toMultimap(Function<? super T, ? extends K> function, Function<? super T, ? extends V> function2) {
        return toMultimap(function, function2, HashMapSupplier.asCallable(), ArrayListSupplier.asFunction());
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @CheckReturnValue
    public final <K, V> Single<Map<K, Collection<V>>> toMultimap(Function<? super T, ? extends K> function, Function<? super T, ? extends V> function2, Callable<Map<K, Collection<V>>> callable) {
        return toMultimap(function, function2, callable, ArrayListSupplier.asFunction());
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @CheckReturnValue
    public final <K, V> Single<Map<K, Collection<V>>> toMultimap(Function<? super T, ? extends K> function, Function<? super T, ? extends V> function2, Callable<? extends Map<K, Collection<V>>> callable, Function<? super K, ? extends Collection<? super V>> function3) {
        ObjectHelper.requireNonNull(function, "keySelector is null");
        ObjectHelper.requireNonNull(function2, "valueSelector is null");
        ObjectHelper.requireNonNull(callable, "mapSupplier is null");
        ObjectHelper.requireNonNull(function3, "collectionFactory is null");
        return collect(callable, Functions.toMultimapKeyValueSelector(function, function2, function3));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.NONE)
    @CheckReturnValue
    public final Observable<T> toObservable() {
        return RxJavaPlugins.onAssembly(new ObservableFromPublisher(this));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @CheckReturnValue
    public final Single<List<T>> toSortedList() {
        return toSortedList(Functions.naturalComparator());
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @CheckReturnValue
    public final Single<List<T>> toSortedList(int i) {
        return toSortedList(Functions.naturalComparator(), i);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @CheckReturnValue
    public final Single<List<T>> toSortedList(Comparator<? super T> comparator) {
        ObjectHelper.requireNonNull(comparator, "comparator is null");
        return toList().map(Functions.listSorter(comparator));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
    @CheckReturnValue
    public final Single<List<T>> toSortedList(Comparator<? super T> comparator, int i) {
        ObjectHelper.requireNonNull(comparator, "comparator is null");
        return toList(i).map(Functions.listSorter(comparator));
    }

    @SchedulerSupport("custom")
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @CheckReturnValue
    public final Flowable<T> unsubscribeOn(Scheduler scheduler) {
        ObjectHelper.requireNonNull(scheduler, "scheduler is null");
        return RxJavaPlugins.onAssembly(new FlowableUnsubscribeOn(this, scheduler));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final Flowable<Flowable<T>> window(long j) {
        return window(j, j, bufferSize());
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final Flowable<Flowable<T>> window(long j, long j2) {
        return window(j, j2, bufferSize());
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final Flowable<Flowable<T>> window(long j, long j2, int i) {
        ObjectHelper.verifyPositive(j2, "skip");
        ObjectHelper.verifyPositive(j, "count");
        ObjectHelper.verifyPositive(i, "bufferSize");
        return RxJavaPlugins.onAssembly(new FlowableWindow(this, j, j2, i));
    }

    @SchedulerSupport(SchedulerSupport.COMPUTATION)
    @BackpressureSupport(BackpressureKind.ERROR)
    @CheckReturnValue
    public final Flowable<Flowable<T>> window(long j, long j2, TimeUnit timeUnit) {
        return window(j, j2, timeUnit, Schedulers.computation(), bufferSize());
    }

    @SchedulerSupport("custom")
    @BackpressureSupport(BackpressureKind.ERROR)
    @CheckReturnValue
    public final Flowable<Flowable<T>> window(long j, long j2, TimeUnit timeUnit, Scheduler scheduler) {
        return window(j, j2, timeUnit, scheduler, bufferSize());
    }

    @SchedulerSupport("custom")
    @BackpressureSupport(BackpressureKind.ERROR)
    @CheckReturnValue
    public final Flowable<Flowable<T>> window(long j, long j2, TimeUnit timeUnit, Scheduler scheduler, int i) {
        ObjectHelper.verifyPositive(i, "bufferSize");
        ObjectHelper.verifyPositive(j, "timespan");
        ObjectHelper.verifyPositive(j2, "timeskip");
        ObjectHelper.requireNonNull(scheduler, "scheduler is null");
        ObjectHelper.requireNonNull(timeUnit, "unit is null");
        return RxJavaPlugins.onAssembly(new FlowableWindowTimed(this, j, j2, timeUnit, scheduler, Long.MAX_VALUE, i, false));
    }

    @SchedulerSupport(SchedulerSupport.COMPUTATION)
    @BackpressureSupport(BackpressureKind.ERROR)
    @CheckReturnValue
    public final Flowable<Flowable<T>> window(long j, TimeUnit timeUnit) {
        return window(j, timeUnit, Schedulers.computation(), Long.MAX_VALUE, false);
    }

    @SchedulerSupport(SchedulerSupport.COMPUTATION)
    @BackpressureSupport(BackpressureKind.ERROR)
    @CheckReturnValue
    public final Flowable<Flowable<T>> window(long j, TimeUnit timeUnit, long j2) {
        return window(j, timeUnit, Schedulers.computation(), j2, false);
    }

    @SchedulerSupport(SchedulerSupport.COMPUTATION)
    @BackpressureSupport(BackpressureKind.ERROR)
    @CheckReturnValue
    public final Flowable<Flowable<T>> window(long j, TimeUnit timeUnit, long j2, boolean z) {
        return window(j, timeUnit, Schedulers.computation(), j2, z);
    }

    @SchedulerSupport("custom")
    @BackpressureSupport(BackpressureKind.ERROR)
    @CheckReturnValue
    public final Flowable<Flowable<T>> window(long j, TimeUnit timeUnit, Scheduler scheduler) {
        return window(j, timeUnit, scheduler, Long.MAX_VALUE, false);
    }

    @SchedulerSupport("custom")
    @BackpressureSupport(BackpressureKind.ERROR)
    @CheckReturnValue
    public final Flowable<Flowable<T>> window(long j, TimeUnit timeUnit, Scheduler scheduler, long j2) {
        return window(j, timeUnit, scheduler, j2, false);
    }

    @SchedulerSupport("custom")
    @BackpressureSupport(BackpressureKind.ERROR)
    @CheckReturnValue
    public final Flowable<Flowable<T>> window(long j, TimeUnit timeUnit, Scheduler scheduler, long j2, boolean z) {
        return window(j, timeUnit, scheduler, j2, z, bufferSize());
    }

    @SchedulerSupport("custom")
    @BackpressureSupport(BackpressureKind.ERROR)
    @CheckReturnValue
    public final Flowable<Flowable<T>> window(long j, TimeUnit timeUnit, Scheduler scheduler, long j2, boolean z, int i) {
        ObjectHelper.verifyPositive(i, "bufferSize");
        ObjectHelper.requireNonNull(scheduler, "scheduler is null");
        ObjectHelper.requireNonNull(timeUnit, "unit is null");
        ObjectHelper.verifyPositive(j2, "count");
        return RxJavaPlugins.onAssembly(new FlowableWindowTimed(this, j, j, timeUnit, scheduler, j2, i, z));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.ERROR)
    @CheckReturnValue
    public final <B> Flowable<Flowable<T>> window(Callable<? extends Publisher<B>> callable) {
        return window(callable, bufferSize());
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.ERROR)
    @CheckReturnValue
    public final <B> Flowable<Flowable<T>> window(Callable<? extends Publisher<B>> callable, int i) {
        ObjectHelper.requireNonNull(callable, "boundaryIndicatorSupplier is null");
        ObjectHelper.verifyPositive(i, "bufferSize");
        return RxJavaPlugins.onAssembly(new FlowableWindowBoundarySupplier(this, callable, i));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.ERROR)
    @CheckReturnValue
    public final <B> Flowable<Flowable<T>> window(Publisher<B> publisher) {
        return window(publisher, bufferSize());
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.ERROR)
    @CheckReturnValue
    public final <B> Flowable<Flowable<T>> window(Publisher<B> publisher, int i) {
        ObjectHelper.requireNonNull(publisher, "boundaryIndicator is null");
        ObjectHelper.verifyPositive(i, "bufferSize");
        return RxJavaPlugins.onAssembly(new FlowableWindowBoundary(this, publisher, i));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.ERROR)
    @CheckReturnValue
    public final <U, V> Flowable<Flowable<T>> window(Publisher<U> publisher, Function<? super U, ? extends Publisher<V>> function) {
        return window(publisher, function, bufferSize());
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.ERROR)
    @CheckReturnValue
    public final <U, V> Flowable<Flowable<T>> window(Publisher<U> publisher, Function<? super U, ? extends Publisher<V>> function, int i) {
        ObjectHelper.requireNonNull(publisher, "openingIndicator is null");
        ObjectHelper.requireNonNull(function, "closingIndicator is null");
        ObjectHelper.verifyPositive(i, "bufferSize");
        return RxJavaPlugins.onAssembly(new FlowableWindowBoundarySelector(this, publisher, function, i));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @CheckReturnValue
    public final <R> Flowable<R> withLatestFrom(Iterable<? extends Publisher<?>> iterable, Function<? super Object[], R> function) {
        ObjectHelper.requireNonNull(iterable, "others is null");
        ObjectHelper.requireNonNull(function, "combiner is null");
        return RxJavaPlugins.onAssembly(new FlowableWithLatestFromMany(this, iterable, function));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @CheckReturnValue
    public final <U, R> Flowable<R> withLatestFrom(Publisher<? extends U> publisher, BiFunction<? super T, ? super U, ? extends R> biFunction) {
        ObjectHelper.requireNonNull(publisher, "other is null");
        ObjectHelper.requireNonNull(biFunction, "combiner is null");
        return RxJavaPlugins.onAssembly(new FlowableWithLatestFrom(this, biFunction, publisher));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @CheckReturnValue
    public final <T1, T2, R> Flowable<R> withLatestFrom(Publisher<T1> publisher, Publisher<T2> publisher2, Function3<? super T, ? super T1, ? super T2, R> function3) {
        ObjectHelper.requireNonNull(publisher, "source1 is null");
        ObjectHelper.requireNonNull(publisher2, "source2 is null");
        return withLatestFrom(new Publisher[]{publisher, publisher2}, Functions.toFunction(function3));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @CheckReturnValue
    public final <T1, T2, T3, R> Flowable<R> withLatestFrom(Publisher<T1> publisher, Publisher<T2> publisher2, Publisher<T3> publisher3, Function4<? super T, ? super T1, ? super T2, ? super T3, R> function4) {
        ObjectHelper.requireNonNull(publisher, "source1 is null");
        ObjectHelper.requireNonNull(publisher2, "source2 is null");
        ObjectHelper.requireNonNull(publisher3, "source3 is null");
        return withLatestFrom(new Publisher[]{publisher, publisher2, publisher3}, Functions.toFunction(function4));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @CheckReturnValue
    public final <T1, T2, T3, T4, R> Flowable<R> withLatestFrom(Publisher<T1> publisher, Publisher<T2> publisher2, Publisher<T3> publisher3, Publisher<T4> publisher4, Function5<? super T, ? super T1, ? super T2, ? super T3, ? super T4, R> function5) {
        ObjectHelper.requireNonNull(publisher, "source1 is null");
        ObjectHelper.requireNonNull(publisher2, "source2 is null");
        ObjectHelper.requireNonNull(publisher3, "source3 is null");
        ObjectHelper.requireNonNull(publisher4, "source4 is null");
        return withLatestFrom(new Publisher[]{publisher, publisher2, publisher3, publisher4}, Functions.toFunction(function5));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @CheckReturnValue
    public final <R> Flowable<R> withLatestFrom(Publisher<?>[] publisherArr, Function<? super Object[], R> function) {
        ObjectHelper.requireNonNull(publisherArr, "others is null");
        ObjectHelper.requireNonNull(function, "combiner is null");
        return RxJavaPlugins.onAssembly(new FlowableWithLatestFromMany(this, publisherArr, function));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final <U, R> Flowable<R> zipWith(Iterable<U> iterable, BiFunction<? super T, ? super U, ? extends R> biFunction) {
        ObjectHelper.requireNonNull(iterable, "other is null");
        ObjectHelper.requireNonNull(biFunction, "zipper is null");
        return RxJavaPlugins.onAssembly(new FlowableZipIterable(this, iterable, biFunction));
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final <U, R> Flowable<R> zipWith(Publisher<? extends U> publisher, BiFunction<? super T, ? super U, ? extends R> biFunction) {
        ObjectHelper.requireNonNull(publisher, "other is null");
        return zip(this, publisher, biFunction);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final <U, R> Flowable<R> zipWith(Publisher<? extends U> publisher, BiFunction<? super T, ? super U, ? extends R> biFunction, boolean z) {
        return zip(this, publisher, biFunction, z);
    }

    @SchedulerSupport("none")
    @BackpressureSupport(BackpressureKind.FULL)
    @CheckReturnValue
    public final <U, R> Flowable<R> zipWith(Publisher<? extends U> publisher, BiFunction<? super T, ? super U, ? extends R> biFunction, boolean z, int i) {
        return zip(this, publisher, biFunction, z, i);
    }
}
