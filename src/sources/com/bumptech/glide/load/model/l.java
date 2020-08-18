package com.bumptech.glide.load.model;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.bumptech.glide.load.c;
import com.bumptech.glide.util.i;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.util.Map;

/* compiled from: GlideUrl */
public class l implements c {
    private static final String Up = "@#&=*+-_.,:!?()/~'%;$";
    @Nullable
    private final String Qp;
    @Nullable
    private String Rp;
    @Nullable
    private URL Sp;
    @Nullable
    private volatile byte[] Tp;
    private int hashCode;
    private final n headers;
    @Nullable
    private final URL url;

    public l(String str) {
        this(str, n.DEFAULT);
    }

    public l(String str, n nVar) {
        this.url = null;
        i.E(str);
        this.Qp = str;
        i.checkNotNull(nVar);
        this.headers = nVar;
    }

    public l(URL url2) {
        this(url2, n.DEFAULT);
    }

    public l(URL url2, n nVar) {
        i.checkNotNull(url2);
        this.url = url2;
        this.Qp = null;
        i.checkNotNull(nVar);
        this.headers = nVar;
    }

    private byte[] Bn() {
        if (this.Tp == null) {
            this.Tp = getCacheKey().getBytes(c.CHARSET);
        }
        return this.Tp;
    }

    private String Cn() {
        if (TextUtils.isEmpty(this.Rp)) {
            String str = this.Qp;
            if (TextUtils.isEmpty(str)) {
                URL url2 = this.url;
                i.checkNotNull(url2);
                str = url2.toString();
            }
            this.Rp = Uri.encode(str, Up);
        }
        return this.Rp;
    }

    private URL Dn() throws MalformedURLException {
        if (this.Sp == null) {
            this.Sp = new URL(Cn());
        }
        return this.Sp;
    }

    public String Gj() {
        return Cn();
    }

    @Override // com.bumptech.glide.load.c
    public boolean equals(Object obj) {
        if (!(obj instanceof l)) {
            return false;
        }
        l lVar = (l) obj;
        return getCacheKey().equals(lVar.getCacheKey()) && this.headers.equals(lVar.headers);
    }

    public String getCacheKey() {
        String str = this.Qp;
        if (str != null) {
            return str;
        }
        URL url2 = this.url;
        i.checkNotNull(url2);
        return url2.toString();
    }

    public Map<String, String> getHeaders() {
        return this.headers.getHeaders();
    }

    @Override // com.bumptech.glide.load.c
    public int hashCode() {
        if (this.hashCode == 0) {
            this.hashCode = getCacheKey().hashCode();
            this.hashCode = (this.hashCode * 31) + this.headers.hashCode();
        }
        return this.hashCode;
    }

    public String toString() {
        return getCacheKey();
    }

    public URL toURL() throws MalformedURLException {
        return Dn();
    }

    @Override // com.bumptech.glide.load.c
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        messageDigest.update(Bn());
    }
}
