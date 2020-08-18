package com.android.camera.module.impl.component;

import com.android.camera.module.BaseModule;
import java.util.function.Consumer;

/* compiled from: lambda */
public final /* synthetic */ class d implements Consumer {
    public static final /* synthetic */ d INSTANCE = new d();

    private /* synthetic */ d() {
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        ((BaseModule) obj).onSharedPreferenceChanged();
    }
}
