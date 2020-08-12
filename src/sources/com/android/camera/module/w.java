package com.android.camera.module;

import com.android.camera.data.observeable.RxData;
import io.reactivex.functions.Consumer;

/* compiled from: lambda */
public final /* synthetic */ class w implements Consumer {
    private final /* synthetic */ LiveModuleSubVV Hi;

    public /* synthetic */ w(LiveModuleSubVV liveModuleSubVV) {
        this.Hi = liveModuleSubVV;
    }

    @Override // io.reactivex.functions.Consumer
    public final void accept(Object obj) {
        this.Hi.b((RxData.DataWrap) obj);
    }
}
