package com.android.camera.resource;

import android.support.annotation.NonNull;
import com.android.camera.log.Log;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.Scheduler;

public abstract class BaseObservableRequest<T> implements ResponseListener<T> {
    protected ObservableEmitter mEmitter;
    private Observable<T> mOutPutObservable;

    public /* synthetic */ void a(Class cls, ObservableEmitter observableEmitter) throws Exception {
        this.mEmitter = observableEmitter;
        scheduleRequest(this, cls, null);
    }

    public /* synthetic */ void a(Object obj, ObservableEmitter observableEmitter) throws Exception {
        this.mEmitter = observableEmitter;
        scheduleRequest(this, obj.getClass(), obj);
    }

    /* access modifiers changed from: protected */
    @NonNull
    public final T create(@NonNull Class<T> cls) {
        try {
            return cls.newInstance();
        } catch (InstantiationException unused) {
            Log.e("newInstanceError", "Cannot create an instance of " + cls);
            return null;
        } catch (IllegalAccessException unused2) {
            Log.e("newInstanceError", "Cannot create an instance of " + cls);
            return null;
        }
    }

    /* access modifiers changed from: protected */
    public Scheduler getWorkThread() {
        return null;
    }

    @Override // com.android.camera.resource.ResponseListener
    public void onResponse(T t, boolean z) {
        ObservableEmitter observableEmitter = this.mEmitter;
        if (observableEmitter != null) {
            if (!z) {
                observableEmitter.onNext(t);
            }
            this.mEmitter.onComplete();
        }
    }

    @Override // com.android.camera.resource.ResponseListener
    public void onResponseError(int i, String str, Object obj) {
        this.mEmitter.onError(new BaseRequestException(i, str));
    }

    /* access modifiers changed from: protected */
    public abstract void scheduleRequest(ResponseListener<T> responseListener, Class<T> cls, T t);

    public Observable<T> startObservable(@NonNull Class<T> cls) {
        this.mOutPutObservable = Observable.create(new b(this, cls));
        return this.mOutPutObservable;
    }

    public Observable<T> startObservable(@NonNull T t) {
        this.mOutPutObservable = Observable.create(new a(this, t));
        return this.mOutPutObservable;
    }
}
