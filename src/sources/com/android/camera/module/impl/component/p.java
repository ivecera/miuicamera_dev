package com.android.camera.module.impl.component;

import com.android.camera.module.BaseModule;
import java.util.function.Consumer;

/* compiled from: lambda */
public final /* synthetic */ class p implements Consumer {
    public static final /* synthetic */ p INSTANCE = new p();

    private /* synthetic */ p() {
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        ((BaseModule) obj).updatePreferenceInWorkThread(11, 10);
    }
}
