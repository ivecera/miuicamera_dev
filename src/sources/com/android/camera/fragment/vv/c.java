package com.android.camera.fragment.vv;

import com.android.camera.data.observeable.RxData;
import io.reactivex.functions.Consumer;

/* compiled from: lambda */
public final /* synthetic */ class c implements Consumer {
    private final /* synthetic */ FragmentVVProcess Hi;

    public /* synthetic */ c(FragmentVVProcess fragmentVVProcess) {
        this.Hi = fragmentVVProcess;
    }

    @Override // io.reactivex.functions.Consumer
    public final void accept(Object obj) {
        this.Hi.a((RxData.DataWrap) obj);
    }
}
