package io.reactivex.internal.fuseable;

import java.util.concurrent.Callable;

public interface ScalarCallable<T> extends Callable<T> {
    @Override // java.util.concurrent.Callable
    T call();
}
