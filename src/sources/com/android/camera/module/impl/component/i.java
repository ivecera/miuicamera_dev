package com.android.camera.module.impl.component;

import com.android.camera.module.BaseModule;
import java.util.function.Consumer;

/* compiled from: lambda */
public final /* synthetic */ class i implements Consumer {
    private final /* synthetic */ int[] Hi;

    public /* synthetic */ i(int[] iArr) {
        this.Hi = iArr;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        ConfigChangeImpl.a(this.Hi, (BaseModule) obj);
    }
}
