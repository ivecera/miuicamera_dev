package com.android.camera.module.loader.camera2;

import io.reactivex.functions.Function;

/* compiled from: lambda */
public final /* synthetic */ class d implements Function {
    private final /* synthetic */ ParallelSnapshotManager Hi;

    public /* synthetic */ d(ParallelSnapshotManager parallelSnapshotManager) {
        this.Hi = parallelSnapshotManager;
    }

    @Override // io.reactivex.functions.Function
    public final Object apply(Object obj) {
        return this.Hi.i((Throwable) obj);
    }
}
