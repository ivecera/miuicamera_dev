package com.android.camera.resource;

import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/* compiled from: lambda */
public final /* synthetic */ class a implements ObservableOnSubscribe {
    private final /* synthetic */ BaseObservableRequest Hi;
    private final /* synthetic */ Object Li;

    public /* synthetic */ a(BaseObservableRequest baseObservableRequest, Object obj) {
        this.Hi = baseObservableRequest;
        this.Li = obj;
    }

    @Override // io.reactivex.ObservableOnSubscribe
    public final void subscribe(ObservableEmitter observableEmitter) {
        this.Hi.a(this.Li, observableEmitter);
    }
}
