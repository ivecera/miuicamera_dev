package io.reactivex.disposables;

import io.reactivex.annotations.NonNull;
import io.reactivex.internal.functions.ObjectHelper;
import java.util.concurrent.atomic.AtomicReference;

abstract class ReferenceDisposable<T> extends AtomicReference<T> implements Disposable {
    private static final long serialVersionUID = 6537757548749041217L;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    ReferenceDisposable(T t) {
        super(t);
        ObjectHelper.requireNonNull(t, "value is null");
    }

    @Override // io.reactivex.disposables.Disposable
    public final void dispose() {
        T andSet;
        if (get() != null && (andSet = getAndSet(null)) != null) {
            onDisposed(andSet);
        }
    }

    @Override // io.reactivex.disposables.Disposable
    public final boolean isDisposed() {
        return get() == null;
    }

    /* access modifiers changed from: protected */
    public abstract void onDisposed(@NonNull T t);
}
