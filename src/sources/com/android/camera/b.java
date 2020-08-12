package com.android.camera;

import com.google.lens.sdk.LensApi;

/* compiled from: lambda */
public final /* synthetic */ class b implements LensApi.LensAvailabilityCallback {
    private final /* synthetic */ Camera Hi;

    public /* synthetic */ b(Camera camera) {
        this.Hi = camera;
    }

    @Override // com.google.lens.sdk.LensApi.LensAvailabilityCallback
    public final void onAvailabilityStatusFetched(int i) {
        this.Hi.k(i);
    }
}
