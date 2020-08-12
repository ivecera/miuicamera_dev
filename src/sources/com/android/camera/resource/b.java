package com.android.camera.resource;

import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/* compiled from: lambda */
public final /* synthetic */ class b implements ObservableOnSubscribe {
    private final /* synthetic */ BaseObservableRequest Hi;
    private final /* synthetic */ Class Li;

    public /* synthetic */ b(BaseObservableRequest baseObservableRequest, Class cls) {
        this.Hi = baseObservableRequest;
        this.Li = cls;
    }

    @Override // io.reactivex.ObservableOnSubscribe
    public final void subscribe(ObservableEmitter observableEmitter) {
        this.Hi.a(this.Li, observableEmitter);
    }
}
