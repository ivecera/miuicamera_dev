package io.reactivex.internal.operators.maybe;

import io.reactivex.Maybe;
import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.operators.maybe.MaybeMap;
import io.reactivex.internal.operators.maybe.MaybeZipArray;
import java.util.Arrays;
import java.util.Iterator;

public final class MaybeZipIterable<T, R> extends Maybe<R> {
    final Iterable<? extends MaybeSource<? extends T>> sources;
    final Function<? super Object[], ? extends R> zipper;

    final class SingletonArrayFunc implements Function<T, R> {
        SingletonArrayFunc() {
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v1, resolved type: java.lang.Object[]} */
        /* JADX WARN: Multi-variable type inference failed */
        @Override // io.reactivex.functions.Function
        public R apply(T t) throws Exception {
            R apply = MaybeZipIterable.this.zipper.apply(new Object[]{t});
            ObjectHelper.requireNonNull(apply, "The zipper returned a null value");
            return apply;
        }
    }

    public MaybeZipIterable(Iterable<? extends MaybeSource<? extends T>> iterable, Function<? super Object[], ? extends R> function) {
        this.sources = iterable;
        this.zipper = function;
    }

    /* access modifiers changed from: protected */
    @Override // io.reactivex.Maybe
    public void subscribeActual(MaybeObserver<? super R> maybeObserver) {
        MaybeSource[] maybeSourceArr = new MaybeSource[8];
        try {
            Iterator<? extends MaybeSource<? extends T>> it = this.sources.iterator();
            MaybeSource[] maybeSourceArr2 = maybeSourceArr;
            int i = 0;
            while (it.hasNext()) {
                MaybeSource maybeSource = (MaybeSource) it.next();
                if (maybeSource == null) {
                    EmptyDisposable.error(new NullPointerException("One of the sources is null"), maybeObserver);
                    return;
                }
                if (i == maybeSourceArr2.length) {
                    maybeSourceArr2 = (MaybeSource[]) Arrays.copyOf(maybeSourceArr2, (i >> 2) + i);
                }
                int i2 = i + 1;
                maybeSourceArr2[i] = maybeSource;
                i = i2;
            }
            if (i == 0) {
                EmptyDisposable.complete(maybeObserver);
            } else if (i == 1) {
                maybeSourceArr2[0].subscribe(new MaybeMap.MapMaybeObserver(maybeObserver, new SingletonArrayFunc()));
            } else {
                MaybeZipArray.ZipCoordinator zipCoordinator = new MaybeZipArray.ZipCoordinator(maybeObserver, i, this.zipper);
                maybeObserver.onSubscribe(zipCoordinator);
                for (int i3 = 0; i3 < i && !zipCoordinator.isDisposed(); i3++) {
                    maybeSourceArr2[i3].subscribe(zipCoordinator.observers[i3]);
                }
            }
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            EmptyDisposable.error(th, maybeObserver);
        }
    }
}
