package com.android.camera.module.impl.component;

import com.android.camera.module.BaseModule;
import java.util.function.Consumer;

/* compiled from: lambda */
public final /* synthetic */ class j implements Consumer {
    public static final /* synthetic */ j INSTANCE = new j();

    private /* synthetic */ j() {
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        ((BaseModule) obj).updatePreferenceInWorkThread(45);
    }
}
