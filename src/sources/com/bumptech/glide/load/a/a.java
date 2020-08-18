package com.bumptech.glide.load.a;

import android.content.ContentResolver;
import android.content.res.AssetFileDescriptor;
import android.net.Uri;
import android.support.annotation.NonNull;
import java.io.FileNotFoundException;
import java.io.IOException;

/* compiled from: AssetFileDescriptorLocalUriFetcher */
public final class a extends m<AssetFileDescriptor> {
    public a(ContentResolver contentResolver, Uri uri) {
        super(contentResolver, uri);
    }

    /* access modifiers changed from: protected */
    @Override // com.bumptech.glide.load.a.m
    public AssetFileDescriptor a(Uri uri, ContentResolver contentResolver) throws FileNotFoundException {
        AssetFileDescriptor openAssetFileDescriptor = contentResolver.openAssetFileDescriptor(uri, "r");
        if (openAssetFileDescriptor != null) {
            return openAssetFileDescriptor;
        }
        throw new FileNotFoundException("FileDescriptor is null for: " + uri);
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public void e(AssetFileDescriptor assetFileDescriptor) throws IOException {
        assetFileDescriptor.close();
    }

    @Override // com.bumptech.glide.load.a.d
    @NonNull
    public Class<AssetFileDescriptor> ga() {
        return AssetFileDescriptor.class;
    }
}
