package com.android.camera.features.mimoji2.module.impl;

/* compiled from: lambda */
public final /* synthetic */ class a implements Runnable {
    private final /* synthetic */ MimojiAvatarEngine2Impl Hi;
    private final /* synthetic */ int Li;
    private final /* synthetic */ int Mi;
    private final /* synthetic */ int Ni;
    private final /* synthetic */ int Oi;
    private final /* synthetic */ boolean Pi;

    public /* synthetic */ a(MimojiAvatarEngine2Impl mimojiAvatarEngine2Impl, int i, int i2, int i3, int i4, boolean z) {
        this.Hi = mimojiAvatarEngine2Impl;
        this.Li = i;
        this.Mi = i2;
        this.Ni = i3;
        this.Oi = i4;
        this.Pi = z;
    }

    public final void run() {
        this.Hi.b(this.Li, this.Mi, this.Ni, this.Oi, this.Pi);
    }
}
