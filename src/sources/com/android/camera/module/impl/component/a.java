package com.android.camera.module.impl.component;

import com.android.camera.module.BaseModule;
import java.util.function.Consumer;

/* compiled from: lambda */
public final /* synthetic */ class a implements Consumer {
    public static final /* synthetic */ a INSTANCE = new a();

    private /* synthetic */ a() {
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        ConfigChangeImpl.h((BaseModule) obj);
    }
}
