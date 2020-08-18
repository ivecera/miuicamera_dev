package io.reactivex.internal.observers;

public final class BlockingLastObserver<T> extends BlockingBaseObserver<T> {
    @Override // io.reactivex.Observer
    public void onError(Throwable th) {
        ((BlockingBaseObserver) this).value = null;
        ((BlockingBaseObserver) this).error = th;
        countDown();
    }

    @Override // io.reactivex.Observer
    public void onNext(T t) {
        ((BlockingBaseObserver) this).value = t;
    }
}
