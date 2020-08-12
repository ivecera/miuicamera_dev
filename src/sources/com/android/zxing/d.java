package com.android.zxing;

import io.reactivex.functions.Consumer;

/* compiled from: lambda */
public final /* synthetic */ class d implements Consumer {
    private final /* synthetic */ QrDecoder Hi;

    public /* synthetic */ d(QrDecoder qrDecoder) {
        this.Hi = qrDecoder;
    }

    @Override // io.reactivex.functions.Consumer
    public final void accept(Object obj) {
        this.Hi.u((String) obj);
    }
}
