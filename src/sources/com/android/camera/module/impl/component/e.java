package com.android.camera.module.impl.component;

import com.android.camera.module.BaseModule;
import java.util.function.Consumer;

/* compiled from: lambda */
public final /* synthetic */ class e implements Consumer {
    private final /* synthetic */ ConfigChangeImpl Hi;

    public /* synthetic */ e(ConfigChangeImpl configChangeImpl) {
        this.Hi = configChangeImpl;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        this.Hi.a((BaseModule) obj);
    }
}
