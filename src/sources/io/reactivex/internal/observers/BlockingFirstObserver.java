package io.reactivex.internal.observers;

public final class BlockingFirstObserver<T> extends BlockingBaseObserver<T> {
    @Override // io.reactivex.Observer
    public void onError(Throwable th) {
        if (((BlockingBaseObserver) this).value == null) {
            ((BlockingBaseObserver) this).error = th;
        }
        countDown();
    }

    @Override // io.reactivex.Observer
    public void onNext(T t) {
        if (((BlockingBaseObserver) this).value == null) {
            ((BlockingBaseObserver) this).value = t;
            ((BlockingBaseObserver) this).f684d.dispose();
            countDown();
        }
    }
}
