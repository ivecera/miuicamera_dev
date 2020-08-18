package com.android.camera2.vendortag;

import java.util.function.Supplier;

/* compiled from: lambda */
public final /* synthetic */ class Jb implements Supplier {
    public static final /* synthetic */ Jb INSTANCE = new Jb();

    private /* synthetic */ Jb() {
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        return CaptureRequestVendorTags.mi();
    }
}
