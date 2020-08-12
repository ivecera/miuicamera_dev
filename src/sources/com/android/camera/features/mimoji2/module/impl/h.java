package com.android.camera.features.mimoji2.module.impl;

import com.xiaomi.Video2GifEditer.MediaProcess;
import java.util.List;

/* compiled from: lambda */
public final /* synthetic */ class h implements MediaProcess.Callback {
    private final /* synthetic */ int Hi;
    private final /* synthetic */ List Li;

    public /* synthetic */ h(int i, List list) {
        this.Hi = i;
        this.Li = list;
    }

    @Override // com.xiaomi.Video2GifEditer.MediaProcess.Callback
    public final void OnConvertProgress(int i) {
        MimojiVideoEditorImpl.a(this.Hi, this.Li, i);
    }
}
