package com.android.camera2;

import android.media.ImageReader;

/* compiled from: lambda */
public final /* synthetic */ class e implements ImageReader.OnImageAvailableListener {
    private final /* synthetic */ MiCamera2 Hi;

    public /* synthetic */ e(MiCamera2 miCamera2) {
        this.Hi = miCamera2;
    }

    public final void onImageAvailable(ImageReader imageReader) {
        this.Hi.a(imageReader);
    }
}
