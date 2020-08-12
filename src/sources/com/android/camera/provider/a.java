package com.android.camera.provider;

import android.content.Context;
import java.io.File;

/* compiled from: lambda */
public final /* synthetic */ class a implements Runnable {
    private final /* synthetic */ SplashProvider Hi;
    private final /* synthetic */ Context Li;
    private final /* synthetic */ File Mi;

    public /* synthetic */ a(SplashProvider splashProvider, Context context, File file) {
        this.Hi = splashProvider;
        this.Li = context;
        this.Mi = file;
    }

    public final void run() {
        this.Hi.a(this.Li, this.Mi);
    }
}
