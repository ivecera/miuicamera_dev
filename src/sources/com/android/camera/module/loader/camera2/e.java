package com.android.camera.module.loader.camera2;

import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/* compiled from: lambda */
public final /* synthetic */ class e implements ObservableOnSubscribe {
    private final /* synthetic */ ParallelSnapshotManager Hi;

    public /* synthetic */ e(ParallelSnapshotManager parallelSnapshotManager) {
        this.Hi = parallelSnapshotManager;
    }

    @Override // io.reactivex.ObservableOnSubscribe
    public final void subscribe(ObservableEmitter observableEmitter) {
        this.Hi.e(observableEmitter);
    }
}
