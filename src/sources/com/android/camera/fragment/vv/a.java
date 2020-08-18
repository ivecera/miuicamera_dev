package com.android.camera.fragment.vv;

import io.reactivex.functions.Consumer;

/* compiled from: lambda */
public final /* synthetic */ class a implements Consumer {
    public static final /* synthetic */ a INSTANCE = new a();

    private /* synthetic */ a() {
    }

    @Override // io.reactivex.functions.Consumer
    public final void accept(Object obj) {
        ((Throwable) obj).printStackTrace();
    }
}
