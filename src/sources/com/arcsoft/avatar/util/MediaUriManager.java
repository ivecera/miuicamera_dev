package com.arcsoft.avatar.util;

import android.content.Context;
import android.database.sqlite.SQLiteFullException;
import android.media.MediaScannerConnection;
import android.net.Uri;
import java.util.ArrayList;
import java.util.List;

public class MediaUriManager implements MediaScannerConnection.MediaScannerConnectionClient {

    /* renamed from: c  reason: collision with root package name */
    private static final int f173c = 100;

    /* renamed from: a  reason: collision with root package name */
    private Context f174a;

    /* renamed from: b  reason: collision with root package name */
    private MediaScannerConnection f175b;

    /* renamed from: d  reason: collision with root package name */
    private List<Uri> f176d = new ArrayList();

    /* renamed from: e  reason: collision with root package name */
    private String f177e;

    public MediaUriManager(Context context) {
        this.f174a = context;
        this.f175b = new MediaScannerConnection(this.f174a, this);
    }

    public void addPath(String str) {
        this.f177e = str;
        this.f175b.connect();
    }

    public void addUri(Uri uri) {
        if (uri != null) {
            this.f176d.add(uri);
        }
    }

    public void addUris(List<Uri> list) {
        if (list != null && !list.isEmpty()) {
            this.f176d.addAll(list);
        }
    }

    public Uri getCurrentMediaUri() {
        if (this.f176d.isEmpty()) {
            return null;
        }
        return this.f176d.get(0);
    }

    public List<Uri> getUris() {
        return this.f176d;
    }

    public boolean isEmpty() {
        List<Uri> list = this.f176d;
        return list == null || list.isEmpty();
    }

    public void onMediaScannerConnected() {
        try {
            this.f175b.scanFile(this.f177e, null);
        } catch (SQLiteFullException e2) {
            e2.printStackTrace();
        }
    }

    public void onScanCompleted(String str, Uri uri) {
        try {
            if (this.f176d.size() > 100) {
                this.f176d.remove(this.f176d.size() - 1);
            }
            this.f176d.add(0, uri);
        } finally {
            this.f175b.disconnect();
        }
    }

    public void release() {
        MediaScannerConnection mediaScannerConnection = this.f175b;
        if (mediaScannerConnection != null && mediaScannerConnection.isConnected()) {
            this.f175b.disconnect();
        }
    }
}
