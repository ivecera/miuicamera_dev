package com.bumptech.glide.load.a;

import android.content.res.AssetManager;
import android.os.ParcelFileDescriptor;
import android.support.annotation.NonNull;
import java.io.IOException;

/* compiled from: FileDescriptorAssetPathFetcher */
public class i extends b<ParcelFileDescriptor> {
    public i(AssetManager assetManager, String str) {
        super(assetManager, str);
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public void e(ParcelFileDescriptor parcelFileDescriptor) throws IOException {
        parcelFileDescriptor.close();
    }

    /* access modifiers changed from: protected */
    @Override // com.bumptech.glide.load.a.b
    public ParcelFileDescriptor b(AssetManager assetManager, String str) throws IOException {
        return assetManager.openFd(str).getParcelFileDescriptor();
    }

    @Override // com.bumptech.glide.load.a.d
    @NonNull
    public Class<ParcelFileDescriptor> ga() {
        return ParcelFileDescriptor.class;
    }
}
