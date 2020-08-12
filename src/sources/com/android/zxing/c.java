package com.android.zxing;

import io.reactivex.functions.Function;

/* compiled from: lambda */
public final /* synthetic */ class c implements Function {
    private final /* synthetic */ QrDecoder Hi;

    public /* synthetic */ c(QrDecoder qrDecoder) {
        this.Hi = qrDecoder;
    }

    @Override // io.reactivex.functions.Function
    public final Object apply(Object obj) {
        return this.Hi.b((PreviewImage) obj);
    }
}
