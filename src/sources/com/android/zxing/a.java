package com.android.zxing;

import io.reactivex.functions.Function;

/* compiled from: lambda */
public final /* synthetic */ class a implements Function {
    private final /* synthetic */ HandGestureDecoder Hi;

    public /* synthetic */ a(HandGestureDecoder handGestureDecoder) {
        this.Hi = handGestureDecoder;
    }

    @Override // io.reactivex.functions.Function
    public final Object apply(Object obj) {
        return this.Hi.a((PreviewImage) obj);
    }
}
