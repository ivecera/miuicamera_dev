package io.reactivex.internal.operators.observable;

import io.reactivex.Notification;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.internal.util.BlockingHelper;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicReference;

public final class BlockingObservableLatest<T> implements Iterable<T> {
    final ObservableSource<T> source;

    static final class BlockingObservableLatestIterator<T> extends DisposableObserver<Notification<T>> implements Iterator<T> {
        Notification<T> iteratorNotification;
        final Semaphore notify = new Semaphore(0);
        final AtomicReference<Notification<T>> value = new AtomicReference<>();

        BlockingObservableLatestIterator() {
        }

        public boolean hasNext() {
            Notification<T> notification = this.iteratorNotification;
            if (notification == null || !notification.isOnError()) {
                if (this.iteratorNotification == null) {
                    try {
                        BlockingHelper.verifyNonBlocking();
                        this.notify.acquire();
                        Notification<T> andSet = this.value.getAndSet(null);
                        this.iteratorNotification = andSet;
                        if (andSet.isOnError()) {
                            throw ExceptionHelper.wrapOrThrow(andSet.getError());
                        }
                    } catch (InterruptedException e2) {
                        dispose();
                        this.iteratorNotification = Notification.createOnError(e2);
                        throw ExceptionHelper.wrapOrThrow(e2);
                    }
                }
                return this.iteratorNotification.isOnNext();
            }
            throw ExceptionHelper.wrapOrThrow(this.iteratorNotification.getError());
        }

        @Override // java.util.Iterator
        public T next() {
            if (hasNext()) {
                T value2 = this.iteratorNotification.getValue();
                this.iteratorNotification = null;
                return value2;
            }
            throw new NoSuchElementException();
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            RxJavaPlugins.onError(th);
        }

        public void onNext(Notification<T> notification) {
            if (this.value.getAndSet(notification) == null) {
                this.notify.release();
            }
        }

        @Override // io.reactivex.Observer
        public /* bridge */ /* synthetic */ void onNext(Object obj) {
            onNext((Notification) ((Notification) obj));
        }

        public void remove() {
            throw new UnsupportedOperationException("Read-only iterator.");
        }
    }

    public BlockingObservableLatest(ObservableSource<T> observableSource) {
        this.source = observableSource;
    }

    @Override // java.lang.Iterable
    public Iterator<T> iterator() {
        BlockingObservableLatestIterator blockingObservableLatestIterator = new BlockingObservableLatestIterator();
        Observable.wrap(this.source).materialize().subscribe(blockingObservableLatestIterator);
        return blockingObservableLatestIterator;
    }
}
