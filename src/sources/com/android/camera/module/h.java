package com.android.camera.module;

import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;

/* compiled from: lambda */
public final /* synthetic */ class h implements Runnable {
    public static final /* synthetic */ h INSTANCE = new h();

    private /* synthetic */ h() {
    }

    public final void run() {
        ((ModeProtocol.IDCardModeProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(233)).switchNextPage();
    }
}
