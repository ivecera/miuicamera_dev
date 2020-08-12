package com.android.camera.features.mimoji2.module.impl;

/* compiled from: lambda */
public final /* synthetic */ class d implements Runnable {
    private final /* synthetic */ MimojiAvatarEngine2Impl Hi;
    private final /* synthetic */ String Li;

    public /* synthetic */ d(MimojiAvatarEngine2Impl mimojiAvatarEngine2Impl, String str) {
        this.Hi = mimojiAvatarEngine2Impl;
        this.Li = str;
    }

    public final void run() {
        this.Hi.r(this.Li);
    }
}
