package com.android.camera2.vendortag;

import java.util.function.Supplier;

/* compiled from: lambda */
public final /* synthetic */ class Nd implements Supplier {
    public static final /* synthetic */ Nd INSTANCE = new Nd();

    private /* synthetic */ Nd() {
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        return CaptureResultVendorTags.tg();
    }
}
