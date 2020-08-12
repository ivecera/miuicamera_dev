package com.android.camera2;

import android.hardware.camera2.CaptureRequest;
import java.util.function.BiConsumer;

/* compiled from: lambda */
public final /* synthetic */ class b implements BiConsumer {
    private final /* synthetic */ CaptureRequest.Builder Hi;

    public /* synthetic */ b(CaptureRequest.Builder builder) {
        this.Hi = builder;
    }

    @Override // java.util.function.BiConsumer
    public final void accept(Object obj, Object obj2) {
        CaptureSessionConfigurations.a(this.Hi, obj, obj2);
    }
}
