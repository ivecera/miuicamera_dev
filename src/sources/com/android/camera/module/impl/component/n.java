package com.android.camera.module.impl.component;

import com.android.camera.module.BaseModule;
import java.util.function.Consumer;

/* compiled from: lambda */
public final /* synthetic */ class n implements Consumer {
    public static final /* synthetic */ n INSTANCE = new n();

    private /* synthetic */ n() {
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        ((BaseModule) obj).resetAiSceneInDocumentModeOn();
    }
}
