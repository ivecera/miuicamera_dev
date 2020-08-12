package com.android.zxing;

import io.reactivex.functions.Consumer;

/* compiled from: lambda */
public final /* synthetic */ class b implements Consumer {
    private final /* synthetic */ HandGestureDecoder Hi;

    public /* synthetic */ b(HandGestureDecoder handGestureDecoder) {
        this.Hi = handGestureDecoder;
    }

    @Override // io.reactivex.functions.Consumer
    public final void accept(Object obj) {
        this.Hi.e((Integer) obj);
    }
}
