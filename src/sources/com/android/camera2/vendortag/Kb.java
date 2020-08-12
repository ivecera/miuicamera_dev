package com.android.camera2.vendortag;

import java.util.function.Supplier;

/* compiled from: lambda */
public final /* synthetic */ class Kb implements Supplier {
    public static final /* synthetic */ Kb INSTANCE = new Kb();

    private /* synthetic */ Kb() {
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        return CaptureRequestVendorTags.hh();
    }
}
