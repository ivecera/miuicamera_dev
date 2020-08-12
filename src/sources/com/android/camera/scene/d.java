package com.android.camera.scene;

import com.android.camera.protocol.ModeProtocol;

/* compiled from: lambda */
public final /* synthetic */ class d implements Runnable {
    private final /* synthetic */ ModeProtocol.TopAlert Hi;

    public /* synthetic */ d(ModeProtocol.TopAlert topAlert) {
        this.Hi = topAlert;
    }

    public final void run() {
        this.Hi.alertAiSceneSelector(8);
    }
}
