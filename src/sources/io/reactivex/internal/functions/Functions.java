package io.reactivex.internal.functions;

import io.reactivex.Notification;
import io.reactivex.Scheduler;
import io.reactivex.exceptions.OnErrorNotImplementedException;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.BiFunction;
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
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Timed;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import org.reactivestreams.Subscription;

public final class Functions {
    static final Predicate<Object> ALWAYS_FALSE = new FalsePredicate();
    static final Predicate<Object> ALWAYS_TRUE = new TruePredicate();
    public static final Action EMPTY_ACTION = new EmptyAction();
    static final Consumer<Object> EMPTY_CONSUMER = new EmptyConsumer();
    public static final LongConsumer EMPTY_LONG_CONSUMER = new EmptyLongConsumer();
    public static final Runnable EMPTY_RUNNABLE = new EmptyRunnable();
    public static final Consumer<Throwable> ERROR_CONSUMER = new ErrorConsumer();
    static final Function<Object, Object> IDENTITY = new Identity();
    static final Comparator<Object> NATURAL_COMPARATOR = new NaturalObjectComparator();
    static final Callable<Object> NULL_SUPPLIER = new NullCallable();
    public static final Consumer<Throwable> ON_ERROR_MISSING = new OnErrorMissingConsumer();
    public static final Consumer<Subscription> REQUEST_MAX = new MaxRequestSubscription();

    static final class ActionConsumer<T> implements Consumer<T> {
        final Action action;

        ActionConsumer(Action action2) {
            this.action = action2;
        }

        @Override // io.reactivex.functions.Consumer
        public void accept(T t) throws Exception {
            this.action.run();
        }
    }

    static final class Array2Func<T1, T2, R> implements Function<Object[], R> {

        /* renamed from: f  reason: collision with root package name */
        final BiFunction<? super T1, ? super T2, ? extends R> f676f;

        Array2Func(BiFunction<? super T1, ? super T2, ? extends R> biFunction) {
            this.f676f = biFunction;
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v2, resolved type: io.reactivex.functions.BiFunction<? super T1, ? super T2, ? extends R>} */
        /* JADX WARN: Multi-variable type inference failed */
        public R apply(Object[] objArr) throws Exception {
            if (objArr.length == 2) {
                return this.f676f.apply(objArr[0], objArr[1]);
            }
            throw new IllegalArgumentException("Array of size 2 expected but got " + objArr.length);
        }
    }

    static final class Array3Func<T1, T2, T3, R> implements Function<Object[], R> {

        /* renamed from: f  reason: collision with root package name */
        final Function3<T1, T2, T3, R> f677f;

        Array3Func(Function3<T1, T2, T3, R> function3) {
            this.f677f = function3;
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v2, resolved type: io.reactivex.functions.Function3<T1, T2, T3, R>} */
        /* JADX WARN: Multi-variable type inference failed */
        public R apply(Object[] objArr) throws Exception {
            if (objArr.length == 3) {
                return this.f677f.apply(objArr[0], objArr[1], objArr[2]);
            }
            throw new IllegalArgumentException("Array of size 3 expected but got " + objArr.length);
        }
    }

    static final class Array4Func<T1, T2, T3, T4, R> implements Function<Object[], R> {

        /* renamed from: f  reason: collision with root package name */
        final Function4<T1, T2, T3, T4, R> f678f;

        Array4Func(Function4<T1, T2, T3, T4, R> function4) {
            this.f678f = function4;
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v2, resolved type: io.reactivex.functions.Function4<T1, T2, T3, T4, R>} */
        /* JADX WARN: Multi-variable type inference failed */
        public R apply(Object[] objArr) throws Exception {
            if (objArr.length == 4) {
                return this.f678f.apply(objArr[0], objArr[1], objArr[2], objArr[3]);
            }
            throw new IllegalArgumentException("Array of size 4 expected but got " + objArr.length);
        }
    }

    static final class Array5Func<T1, T2, T3, T4, T5, R> implements Function<Object[], R> {

        /* renamed from: f  reason: collision with root package name */
        private final Function5<T1, T2, T3, T4, T5, R> f679f;

        Array5Func(Function5<T1, T2, T3, T4, T5, R> function5) {
            this.f679f = function5;
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v0, resolved type: io.reactivex.functions.Function5<T1, T2, T3, T4, T5, R>} */
        /* JADX WARN: Multi-variable type inference failed */
        public R apply(Object[] objArr) throws Exception {
            if (objArr.length == 5) {
                return this.f679f.apply(objArr[0], objArr[1], objArr[2], objArr[3], objArr[4]);
            }
            throw new IllegalArgumentException("Array of size 5 expected but got " + objArr.length);
        }
    }

    static final class Array6Func<T1, T2, T3, T4, T5, T6, R> implements Function<Object[], R> {

        /* renamed from: f  reason: collision with root package name */
        final Function6<T1, T2, T3, T4, T5, T6, R> f680f;

        Array6Func(Function6<T1, T2, T3, T4, T5, T6, R> function6) {
            this.f680f = function6;
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v0, resolved type: io.reactivex.functions.Function6<T1, T2, T3, T4, T5, T6, R>} */
        /* JADX WARN: Multi-variable type inference failed */
        public R apply(Object[] objArr) throws Exception {
            if (objArr.length == 6) {
                return this.f680f.apply(objArr[0], objArr[1], objArr[2], objArr[3], objArr[4], objArr[5]);
            }
            throw new IllegalArgumentException("Array of size 6 expected but got " + objArr.length);
        }
    }

    static final class Array7Func<T1, T2, T3, T4, T5, T6, T7, R> implements Function<Object[], R> {

        /* renamed from: f  reason: collision with root package name */
        final Function7<T1, T2, T3, T4, T5, T6, T7, R> f681f;

        Array7Func(Function7<T1, T2, T3, T4, T5, T6, T7, R> function7) {
            this.f681f = function7;
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v0, resolved type: io.reactivex.functions.Function7<T1, T2, T3, T4, T5, T6, T7, R>} */
        /* JADX WARN: Multi-variable type inference failed */
        public R apply(Object[] objArr) throws Exception {
            if (objArr.length == 7) {
                return this.f681f.apply(objArr[0], objArr[1], objArr[2], objArr[3], objArr[4], objArr[5], objArr[6]);
            }
            throw new IllegalArgumentException("Array of size 7 expected but got " + objArr.length);
        }
    }

    static final class Array8Func<T1, T2, T3, T4, T5, T6, T7, T8, R> implements Function<Object[], R> {

        /* renamed from: f  reason: collision with root package name */
        final Function8<T1, T2, T3, T4, T5, T6, T7, T8, R> f682f;

        Array8Func(Function8<T1, T2, T3, T4, T5, T6, T7, T8, R> function8) {
            this.f682f = function8;
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v0, resolved type: io.reactivex.functions.Function8<T1, T2, T3, T4, T5, T6, T7, T8, R>} */
        /* JADX WARN: Multi-variable type inference failed */
        public R apply(Object[] objArr) throws Exception {
            if (objArr.length == 8) {
                return this.f682f.apply(objArr[0], objArr[1], objArr[2], objArr[3], objArr[4], objArr[5], objArr[6], objArr[7]);
            }
            throw new IllegalArgumentException("Array of size 8 expected but got " + objArr.length);
        }
    }

    static final class Array9Func<T1, T2, T3, T4, T5, T6, T7, T8, T9, R> implements Function<Object[], R> {

        /* renamed from: f  reason: collision with root package name */
        final Function9<T1, T2, T3, T4, T5, T6, T7, T8, T9, R> f683f;

        Array9Func(Function9<T1, T2, T3, T4, T5, T6, T7, T8, T9, R> function9) {
            this.f683f = function9;
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v0, resolved type: io.reactivex.functions.Function9<T1, T2, T3, T4, T5, T6, T7, T8, T9, R>} */
        /* JADX WARN: Multi-variable type inference failed */
        public R apply(Object[] objArr) throws Exception {
            if (objArr.length == 9) {
                return this.f683f.apply(objArr[0], objArr[1], objArr[2], objArr[3], objArr[4], objArr[5], objArr[6], objArr[7], objArr[8]);
            }
            throw new IllegalArgumentException("Array of size 9 expected but got " + objArr.length);
        }
    }

    static final class ArrayListCapacityCallable<T> implements Callable<List<T>> {
        final int capacity;

        ArrayListCapacityCallable(int i) {
            this.capacity = i;
        }

        @Override // java.util.concurrent.Callable
        public List<T> call() throws Exception {
            return new ArrayList(this.capacity);
        }
    }

    static final class BooleanSupplierPredicateReverse<T> implements Predicate<T> {
        final BooleanSupplier supplier;

        BooleanSupplierPredicateReverse(BooleanSupplier booleanSupplier) {
            this.supplier = booleanSupplier;
        }

        @Override // io.reactivex.functions.Predicate
        public boolean test(T t) throws Exception {
            return !this.supplier.getAsBoolean();
        }
    }

    static final class CastToClass<T, U> implements Function<T, U> {
        final Class<U> clazz;

        CastToClass(Class<U> cls) {
            this.clazz = cls;
        }

        @Override // io.reactivex.functions.Function
        public U apply(T t) throws Exception {
            return this.clazz.cast(t);
        }
    }

    static final class ClassFilter<T, U> implements Predicate<T> {
        final Class<U> clazz;

        ClassFilter(Class<U> cls) {
            this.clazz = cls;
        }

        @Override // io.reactivex.functions.Predicate
        public boolean test(T t) throws Exception {
            return this.clazz.isInstance(t);
        }
    }

    static final class EmptyAction implements Action {
        EmptyAction() {
        }

        @Override // io.reactivex.functions.Action
        public void run() {
        }

        public String toString() {
            return "EmptyAction";
        }
    }

    static final class EmptyConsumer implements Consumer<Object> {
        EmptyConsumer() {
        }

        @Override // io.reactivex.functions.Consumer
        public void accept(Object obj) {
        }

        public String toString() {
            return "EmptyConsumer";
        }
    }

    static final class EmptyLongConsumer implements LongConsumer {
        EmptyLongConsumer() {
        }

        @Override // io.reactivex.functions.LongConsumer
        public void accept(long j) {
        }
    }

    static final class EmptyRunnable implements Runnable {
        EmptyRunnable() {
        }

        public void run() {
        }

        public String toString() {
            return "EmptyRunnable";
        }
    }

    static final class EqualsPredicate<T> implements Predicate<T> {
        final T value;

        EqualsPredicate(T t) {
            this.value = t;
        }

        @Override // io.reactivex.functions.Predicate
        public boolean test(T t) throws Exception {
            return ObjectHelper.equals(t, this.value);
        }
    }

    static final class ErrorConsumer implements Consumer<Throwable> {
        ErrorConsumer() {
        }

        public void accept(Throwable th) {
            RxJavaPlugins.onError(th);
        }
    }

    static final class FalsePredicate implements Predicate<Object> {
        FalsePredicate() {
        }

        @Override // io.reactivex.functions.Predicate
        public boolean test(Object obj) {
            return false;
        }
    }

    static final class FutureAction implements Action {
        final Future<?> future;

        FutureAction(Future<?> future2) {
            this.future = future2;
        }

        @Override // io.reactivex.functions.Action
        public void run() throws Exception {
            this.future.get();
        }
    }

    enum HashSetCallable implements Callable<Set<Object>> {
        INSTANCE;

        @Override // java.util.concurrent.Callable
        public Set<Object> call() throws Exception {
            return new HashSet();
        }
    }

    static final class Identity implements Function<Object, Object> {
        Identity() {
        }

        @Override // io.reactivex.functions.Function
        public Object apply(Object obj) {
            return obj;
        }

        public String toString() {
            return "IdentityFunction";
        }
    }

    static final class JustValue<T, U> implements Callable<U>, Function<T, U> {
        final U value;

        JustValue(U u) {
            this.value = u;
        }

        @Override // io.reactivex.functions.Function
        public U apply(T t) throws Exception {
            return this.value;
        }

        @Override // java.util.concurrent.Callable
        public U call() throws Exception {
            return this.value;
        }
    }

    static final class ListSorter<T> implements Function<List<T>, List<T>> {
        final Comparator<? super T> comparator;

        ListSorter(Comparator<? super T> comparator2) {
            this.comparator = comparator2;
        }

        @Override // io.reactivex.functions.Function
        public /* bridge */ /* synthetic */ Object apply(Object obj) throws Exception {
            return apply((List) ((List) obj));
        }

        public List<T> apply(List<T> list) {
            Collections.sort(list, this.comparator);
            return list;
        }
    }

    static final class MaxRequestSubscription implements Consumer<Subscription> {
        MaxRequestSubscription() {
        }

        public void accept(Subscription subscription) throws Exception {
            subscription.request(Long.MAX_VALUE);
        }
    }

    enum NaturalComparator implements Comparator<Object> {
        INSTANCE;

        @Override // java.util.Comparator
        public int compare(Object obj, Object obj2) {
            return ((Comparable) obj).compareTo(obj2);
        }
    }

    static final class NaturalObjectComparator implements Comparator<Object> {
        NaturalObjectComparator() {
        }

        @Override // java.util.Comparator
        public int compare(Object obj, Object obj2) {
            return ((Comparable) obj).compareTo(obj2);
        }
    }

    static final class NotificationOnComplete<T> implements Action {
        final Consumer<? super Notification<T>> onNotification;

        NotificationOnComplete(Consumer<? super Notification<T>> consumer) {
            this.onNotification = consumer;
        }

        @Override // io.reactivex.functions.Action
        public void run() throws Exception {
            this.onNotification.accept(Notification.createOnComplete());
        }
    }

    static final class NotificationOnError<T> implements Consumer<Throwable> {
        final Consumer<? super Notification<T>> onNotification;

        NotificationOnError(Consumer<? super Notification<T>> consumer) {
            this.onNotification = consumer;
        }

        public void accept(Throwable th) throws Exception {
            this.onNotification.accept(Notification.createOnError(th));
        }
    }

    static final class NotificationOnNext<T> implements Consumer<T> {
        final Consumer<? super Notification<T>> onNotification;

        NotificationOnNext(Consumer<? super Notification<T>> consumer) {
            this.onNotification = consumer;
        }

        @Override // io.reactivex.functions.Consumer
        public void accept(T t) throws Exception {
            this.onNotification.accept(Notification.createOnNext(t));
        }
    }

    static final class NullCallable implements Callable<Object> {
        NullCallable() {
        }

        @Override // java.util.concurrent.Callable
        public Object call() {
            return null;
        }
    }

    static final class OnErrorMissingConsumer implements Consumer<Throwable> {
        OnErrorMissingConsumer() {
        }

        public void accept(Throwable th) {
            RxJavaPlugins.onError(new OnErrorNotImplementedException(th));
        }
    }

    static final class TimestampFunction<T> implements Function<T, Timed<T>> {
        final Scheduler scheduler;
        final TimeUnit unit;

        TimestampFunction(TimeUnit timeUnit, Scheduler scheduler2) {
            this.unit = timeUnit;
            this.scheduler = scheduler2;
        }

        @Override // io.reactivex.functions.Function
        public Timed<T> apply(T t) throws Exception {
            return new Timed<>(t, this.scheduler.now(this.unit), this.unit);
        }
    }

    static final class ToMapKeySelector<K, T> implements BiConsumer<Map<K, T>, T> {
        private final Function<? super T, ? extends K> keySelector;

        ToMapKeySelector(Function<? super T, ? extends K> function) {
            this.keySelector = function;
        }

        @Override // io.reactivex.functions.BiConsumer
        public /* bridge */ /* synthetic */ void accept(Object obj, Object obj2) throws Exception {
            accept((Map) ((Map) obj), obj2);
        }

        public void accept(Map<K, T> map, T t) throws Exception {
            map.put(this.keySelector.apply(t), t);
        }
    }

    static final class ToMapKeyValueSelector<K, V, T> implements BiConsumer<Map<K, V>, T> {
        private final Function<? super T, ? extends K> keySelector;
        private final Function<? super T, ? extends V> valueSelector;

        ToMapKeyValueSelector(Function<? super T, ? extends V> function, Function<? super T, ? extends K> function2) {
            this.valueSelector = function;
            this.keySelector = function2;
        }

        @Override // io.reactivex.functions.BiConsumer
        public /* bridge */ /* synthetic */ void accept(Object obj, Object obj2) throws Exception {
            accept((Map) ((Map) obj), obj2);
        }

        public void accept(Map<K, V> map, T t) throws Exception {
            map.put(this.keySelector.apply(t), this.valueSelector.apply(t));
        }
    }

    static final class ToMultimapKeyValueSelector<K, V, T> implements BiConsumer<Map<K, Collection<V>>, T> {
        private final Function<? super K, ? extends Collection<? super V>> collectionFactory;
        private final Function<? super T, ? extends K> keySelector;
        private final Function<? super T, ? extends V> valueSelector;

        ToMultimapKeyValueSelector(Function<? super K, ? extends Collection<? super V>> function, Function<? super T, ? extends V> function2, Function<? super T, ? extends K> function3) {
            this.collectionFactory = function;
            this.valueSelector = function2;
            this.keySelector = function3;
        }

        @Override // io.reactivex.functions.BiConsumer
        public /* bridge */ /* synthetic */ void accept(Object obj, Object obj2) throws Exception {
            accept((Map) ((Map) obj), obj2);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v0, resolved type: io.reactivex.functions.Function<? super T, ? extends K>} */
        /* JADX WARN: Multi-variable type inference failed */
        public void accept(Map<K, Collection<V>> map, T t) throws Exception {
            K apply = this.keySelector.apply(t);
            Collection<V> collection = map.get(apply);
            if (collection == null) {
                collection = (Collection) this.collectionFactory.apply(apply);
                map.put(apply, collection);
            }
            collection.add(this.valueSelector.apply(t));
        }
    }

    static final class TruePredicate implements Predicate<Object> {
        TruePredicate() {
        }

        @Override // io.reactivex.functions.Predicate
        public boolean test(Object obj) {
            return true;
        }
    }

    private Functions() {
        throw new IllegalStateException("No instances!");
    }

    public static <T> Consumer<T> actionConsumer(Action action) {
        return new ActionConsumer(action);
    }

    public static <T> Predicate<T> alwaysFalse() {
        return ALWAYS_FALSE;
    }

    public static <T> Predicate<T> alwaysTrue() {
        return ALWAYS_TRUE;
    }

    public static <T, U> Function<T, U> castFunction(Class<U> cls) {
        return new CastToClass(cls);
    }

    public static <T> Callable<List<T>> createArrayList(int i) {
        return new ArrayListCapacityCallable(i);
    }

    public static <T> Callable<Set<T>> createHashSet() {
        return HashSetCallable.INSTANCE;
    }

    public static <T> Consumer<T> emptyConsumer() {
        return EMPTY_CONSUMER;
    }

    public static <T> Predicate<T> equalsWith(T t) {
        return new EqualsPredicate(t);
    }

    public static Action futureAction(Future<?> future) {
        return new FutureAction(future);
    }

    public static <T> Function<T, T> identity() {
        return IDENTITY;
    }

    public static <T, U> Predicate<T> isInstanceOf(Class<U> cls) {
        return new ClassFilter(cls);
    }

    public static <T> Callable<T> justCallable(T t) {
        return new JustValue(t);
    }

    public static <T, U> Function<T, U> justFunction(U u) {
        return new JustValue(u);
    }

    public static <T> Function<List<T>, List<T>> listSorter(Comparator<? super T> comparator) {
        return new ListSorter(comparator);
    }

    public static <T> Comparator<T> naturalComparator() {
        return NaturalComparator.INSTANCE;
    }

    public static <T> Comparator<T> naturalOrder() {
        return NATURAL_COMPARATOR;
    }

    public static <T> Action notificationOnComplete(Consumer<? super Notification<T>> consumer) {
        return new NotificationOnComplete(consumer);
    }

    public static <T> Consumer<Throwable> notificationOnError(Consumer<? super Notification<T>> consumer) {
        return new NotificationOnError(consumer);
    }

    public static <T> Consumer<T> notificationOnNext(Consumer<? super Notification<T>> consumer) {
        return new NotificationOnNext(consumer);
    }

    public static <T> Callable<T> nullSupplier() {
        return NULL_SUPPLIER;
    }

    public static <T> Predicate<T> predicateReverseFor(BooleanSupplier booleanSupplier) {
        return new BooleanSupplierPredicateReverse(booleanSupplier);
    }

    public static <T> Function<T, Timed<T>> timestampWith(TimeUnit timeUnit, Scheduler scheduler) {
        return new TimestampFunction(timeUnit, scheduler);
    }

    public static <T1, T2, R> Function<Object[], R> toFunction(BiFunction<? super T1, ? super T2, ? extends R> biFunction) {
        ObjectHelper.requireNonNull(biFunction, "f is null");
        return new Array2Func(biFunction);
    }

    public static <T1, T2, T3, R> Function<Object[], R> toFunction(Function3<T1, T2, T3, R> function3) {
        ObjectHelper.requireNonNull(function3, "f is null");
        return new Array3Func(function3);
    }

    public static <T1, T2, T3, T4, R> Function<Object[], R> toFunction(Function4<T1, T2, T3, T4, R> function4) {
        ObjectHelper.requireNonNull(function4, "f is null");
        return new Array4Func(function4);
    }

    public static <T1, T2, T3, T4, T5, R> Function<Object[], R> toFunction(Function5<T1, T2, T3, T4, T5, R> function5) {
        ObjectHelper.requireNonNull(function5, "f is null");
        return new Array5Func(function5);
    }

    public static <T1, T2, T3, T4, T5, T6, R> Function<Object[], R> toFunction(Function6<T1, T2, T3, T4, T5, T6, R> function6) {
        ObjectHelper.requireNonNull(function6, "f is null");
        return new Array6Func(function6);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, R> Function<Object[], R> toFunction(Function7<T1, T2, T3, T4, T5, T6, T7, R> function7) {
        ObjectHelper.requireNonNull(function7, "f is null");
        return new Array7Func(function7);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, R> Function<Object[], R> toFunction(Function8<T1, T2, T3, T4, T5, T6, T7, T8, R> function8) {
        ObjectHelper.requireNonNull(function8, "f is null");
        return new Array8Func(function8);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, R> Function<Object[], R> toFunction(Function9<T1, T2, T3, T4, T5, T6, T7, T8, T9, R> function9) {
        ObjectHelper.requireNonNull(function9, "f is null");
        return new Array9Func(function9);
    }

    public static <T, K> BiConsumer<Map<K, T>, T> toMapKeySelector(Function<? super T, ? extends K> function) {
        return new ToMapKeySelector(function);
    }

    public static <T, K, V> BiConsumer<Map<K, V>, T> toMapKeyValueSelector(Function<? super T, ? extends K> function, Function<? super T, ? extends V> function2) {
        return new ToMapKeyValueSelector(function2, function);
    }

    public static <T, K, V> BiConsumer<Map<K, Collection<V>>, T> toMultimapKeyValueSelector(Function<? super T, ? extends K> function, Function<? super T, ? extends V> function2, Function<? super K, ? extends Collection<? super V>> function3) {
        return new ToMultimapKeyValueSelector(function3, function2, function);
    }
}
