package com.arcsoft.camera.utils;

import android.content.Context;
import android.database.sqlite.SQLiteFullException;
import android.media.MediaScannerConnection;
import android.net.Uri;
import java.util.ArrayList;
import java.util.List;

/* compiled from: MediaUriManager */
public class c implements MediaScannerConnection.MediaScannerConnectionClient {

    /* renamed from: c  reason: collision with root package name */
    private static final int f213c = 100;

    /* renamed from: a  reason: collision with root package name */
    private Context f214a;

    /* renamed from: b  reason: collision with root package name */
    private MediaScannerConnection f215b;

    /* renamed from: d  reason: collision with root package name */
    private List<Uri> f216d = new ArrayList();

    /* renamed from: e  reason: collision with root package name */
    private String f217e;

    public c(Context context) {
        this.f214a = context;
        this.f215b = new MediaScannerConnection(this.f214a, this);
    }

    public void addPath(String str) {
        this.f217e = str;
        this.f215b.connect();
    }

    public void addUri(Uri uri) {
        if (uri != null) {
            this.f216d.add(uri);
        }
    }

    public void addUris(List<Uri> list) {
        if (list != null && !list.isEmpty()) {
            this.f216d.addAll(list);
        }
    }

    public Uri getCurrentMediaUri() {
        if (this.f216d.isEmpty()) {
            return null;
        }
        return this.f216d.get(0);
    }

    public List<Uri> getUris() {
        return this.f216d;
    }

    public boolean isEmpty() {
        List<Uri> list = this.f216d;
        return list == null || list.isEmpty();
    }

    public void onMediaScannerConnected() {
        try {
            this.f215b.scanFile(this.f217e, null);
        } catch (SQLiteFullException unused) {
        }
    }

    public void onScanCompleted(String str, Uri uri) {
        try {
            if (this.f216d.size() > 100) {
                this.f216d.remove(this.f216d.size() - 1);
            }
            this.f216d.add(0, uri);
        } finally {
            this.f215b.disconnect();
        }
    }

    public void release() {
        MediaScannerConnection mediaScannerConnection = this.f215b;
        if (mediaScannerConnection != null && mediaScannerConnection.isConnected()) {
            this.f215b.disconnect();
        }
    }
}
