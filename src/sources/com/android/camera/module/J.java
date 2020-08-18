package com.android.camera.module;

/* compiled from: lambda */
public final /* synthetic */ class J implements Runnable {
    private final /* synthetic */ BaseModule Hi;

    public /* synthetic */ J(BaseModule baseModule) {
        this.Hi = baseModule;
    }

    public final void run() {
        this.Hi.onThermalConstrained();
    }
}
