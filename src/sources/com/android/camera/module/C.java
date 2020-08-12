package com.android.camera.module;

import android.net.Uri;

/* compiled from: lambda */
public final /* synthetic */ class C implements Runnable {
    private final /* synthetic */ MiLiveModule Hi;
    private final /* synthetic */ String Li;
    private final /* synthetic */ Uri Mi;

    public /* synthetic */ C(MiLiveModule miLiveModule, String str, Uri uri) {
        this.Hi = miLiveModule;
        this.Li = str;
        this.Mi = uri;
    }

    public final void run() {
        this.Hi.a(this.Li, this.Mi);
    }
}
