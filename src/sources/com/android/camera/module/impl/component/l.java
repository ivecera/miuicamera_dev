package com.android.camera.module.impl.component;

import com.android.camera.module.BaseModule;
import java.util.function.Consumer;

/* compiled from: lambda */
public final /* synthetic */ class l implements Consumer {
    public static final /* synthetic */ l INSTANCE = new l();

    private /* synthetic */ l() {
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        ((BaseModule) obj).onSharedPreferenceChanged();
    }
}
