package com.android.camera.module.impl.component;

import com.android.camera.module.BaseModule;
import java.util.function.Consumer;

/* compiled from: lambda */
public final /* synthetic */ class f implements Consumer {
    public static final /* synthetic */ f INSTANCE = new f();

    private /* synthetic */ f() {
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        ((BaseModule) obj).updatePreferenceInWorkThread(11, 37);
    }
}
