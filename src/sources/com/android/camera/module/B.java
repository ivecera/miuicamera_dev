package com.android.camera.module;

import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;

/* compiled from: lambda */
public final /* synthetic */ class B implements FlowableOnSubscribe {
    private final /* synthetic */ MiLiveModule Hi;

    public /* synthetic */ B(MiLiveModule miLiveModule) {
        this.Hi = miLiveModule;
    }

    @Override // io.reactivex.FlowableOnSubscribe
    public final void subscribe(FlowableEmitter flowableEmitter) {
        this.Hi.a(flowableEmitter);
    }
}
