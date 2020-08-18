package com.android.camera.module.loader.camera2;

import io.reactivex.functions.Function;

/* compiled from: lambda */
public final /* synthetic */ class b implements Function {
    private final /* synthetic */ Camera2OpenManager Hi;

    public /* synthetic */ b(Camera2OpenManager camera2OpenManager) {
        this.Hi = camera2OpenManager;
    }

    @Override // io.reactivex.functions.Function
    public final Object apply(Object obj) {
        return this.Hi.h((Throwable) obj);
    }
}
