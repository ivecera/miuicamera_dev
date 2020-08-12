package com.android.lens;

import com.google.android.libraries.lens.lenslite.StatusUpdateCallback;

/* compiled from: lambda */
public final /* synthetic */ class a implements StatusUpdateCallback {
    public static final /* synthetic */ a INSTANCE = new a();

    private /* synthetic */ a() {
    }

    @Override // com.google.android.libraries.lens.lenslite.StatusUpdateCallback
    public final void onOobeStatusUpdated(int i) {
        LensAgent.r(i);
    }
}
