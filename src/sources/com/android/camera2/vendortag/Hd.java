package com.android.camera2.vendortag;

import java.util.function.Supplier;

/* compiled from: lambda */
public final /* synthetic */ class Hd implements Supplier {
    public static final /* synthetic */ Hd INSTANCE = new Hd();

    private /* synthetic */ Hd() {
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        return CaptureResultVendorTags.ei();
    }
}
