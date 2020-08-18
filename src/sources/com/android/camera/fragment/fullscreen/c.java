package com.android.camera.fragment.fullscreen;

import com.android.camera.fragment.fullscreen.FragmentFullScreen;
import com.android.camera.protocol.ModeProtocol;

/* compiled from: lambda */
public final /* synthetic */ class c implements FragmentFullScreen.OnFrameUpdatedCallback {
    private final /* synthetic */ FragmentFullScreen Hi;
    private final /* synthetic */ ModeProtocol.LiveVideoEditor Li;

    public /* synthetic */ c(FragmentFullScreen fragmentFullScreen, ModeProtocol.LiveVideoEditor liveVideoEditor) {
        this.Hi = fragmentFullScreen;
        this.Li = liveVideoEditor;
    }

    @Override // com.android.camera.fragment.fullscreen.FragmentFullScreen.OnFrameUpdatedCallback
    public final void onUpdate() {
        this.Hi.a(this.Li);
    }
}
