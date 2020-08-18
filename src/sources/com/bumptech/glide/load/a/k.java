package com.bumptech.glide.load.a;

import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.text.TextUtils;
import android.util.Log;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.HttpException;
import com.bumptech.glide.load.a.d;
import com.bumptech.glide.load.model.l;
import com.bumptech.glide.util.e;
import com.ss.android.vesdk.runtime.cloudconfig.HttpRequest;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;

/* compiled from: HttpUrlFetcher */
public class k implements d<InputStream> {
    @VisibleForTesting
    static final b DEFAULT_CONNECTION_FACTORY = new a();
    private static final int Jl = 5;
    private static final int Kl = -1;
    private static final String TAG = "HttpUrlFetcher";
    private final l Fl;
    private final b Gl;
    private HttpURLConnection Hl;
    private volatile boolean Il;
    private InputStream stream;
    private final int timeout;

    /* compiled from: HttpUrlFetcher */
    private static class a implements b {
        a() {
        }

        @Override // com.bumptech.glide.load.a.k.b
        public HttpURLConnection b(URL url) throws IOException {
            return (HttpURLConnection) url.openConnection();
        }
    }

    /* compiled from: HttpUrlFetcher */
    interface b {
        HttpURLConnection b(URL url) throws IOException;
    }

    public k(l lVar, int i) {
        this(lVar, i, DEFAULT_CONNECTION_FACTORY);
    }

    @VisibleForTesting
    k(l lVar, int i, b bVar) {
        this.Fl = lVar;
        this.timeout = i;
        this.Gl = bVar;
    }

    private static boolean X(int i) {
        return i / 100 == 2;
    }

    private static boolean Y(int i) {
        return i / 100 == 3;
    }

    private InputStream a(URL url, int i, URL url2, Map<String, String> map) throws IOException {
        if (i < 5) {
            if (url2 != null) {
                try {
                    if (url.toURI().equals(url2.toURI())) {
                        throw new HttpException("In re-direct loop");
                    }
                } catch (URISyntaxException unused) {
                }
            }
            this.Hl = this.Gl.b(url);
            for (Map.Entry<String, String> entry : map.entrySet()) {
                this.Hl.addRequestProperty(entry.getKey(), entry.getValue());
            }
            this.Hl.setConnectTimeout(this.timeout);
            this.Hl.setReadTimeout(this.timeout);
            this.Hl.setUseCaches(false);
            this.Hl.setDoInput(true);
            this.Hl.setInstanceFollowRedirects(false);
            this.Hl.connect();
            this.stream = this.Hl.getInputStream();
            if (this.Il) {
                return null;
            }
            int responseCode = this.Hl.getResponseCode();
            if (X(responseCode)) {
                return b(this.Hl);
            }
            if (Y(responseCode)) {
                String headerField = this.Hl.getHeaderField(HttpRequest.HEADER_LOCATION);
                if (!TextUtils.isEmpty(headerField)) {
                    URL url3 = new URL(url, headerField);
                    cleanup();
                    return a(url3, i + 1, url, map);
                }
                throw new HttpException("Received empty or null redirect url");
            } else if (responseCode == -1) {
                throw new HttpException(responseCode);
            } else {
                throw new HttpException(this.Hl.getResponseMessage(), responseCode);
            }
        } else {
            throw new HttpException("Too many (> 5) redirects!");
        }
    }

    private InputStream b(HttpURLConnection httpURLConnection) throws IOException {
        if (TextUtils.isEmpty(httpURLConnection.getContentEncoding())) {
            this.stream = com.bumptech.glide.util.b.a(httpURLConnection.getInputStream(), (long) httpURLConnection.getContentLength());
        } else {
            if (Log.isLoggable(TAG, 3)) {
                Log.d(TAG, "Got non empty content encoding: " + httpURLConnection.getContentEncoding());
            }
            this.stream = httpURLConnection.getInputStream();
        }
        return this.stream;
    }

    @Override // com.bumptech.glide.load.a.d
    @NonNull
    public DataSource L() {
        return DataSource.REMOTE;
    }

    @Override // com.bumptech.glide.load.a.d
    public void a(@NonNull Priority priority, @NonNull d.a<? super InputStream> aVar) {
        StringBuilder sb;
        long Jk = e.Jk();
        try {
            aVar.b(a(this.Fl.toURL(), 0, null, this.Fl.getHeaders()));
            if (Log.isLoggable(TAG, 2)) {
                sb = new StringBuilder();
                sb.append("Finished http url fetcher fetch in ");
                sb.append(e.e(Jk));
                Log.v(TAG, sb.toString());
            }
        } catch (IOException e2) {
            if (Log.isLoggable(TAG, 3)) {
                Log.d(TAG, "Failed to load data for url", e2);
            }
            aVar.a(e2);
            if (Log.isLoggable(TAG, 2)) {
                sb = new StringBuilder();
            }
        } catch (Throwable th) {
            if (Log.isLoggable(TAG, 2)) {
                Log.v(TAG, "Finished http url fetcher fetch in " + e.e(Jk));
            }
            throw th;
        }
    }

    @Override // com.bumptech.glide.load.a.d
    public void cancel() {
        this.Il = true;
    }

    @Override // com.bumptech.glide.load.a.d
    public void cleanup() {
        InputStream inputStream = this.stream;
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException unused) {
            }
        }
        HttpURLConnection httpURLConnection = this.Hl;
        if (httpURLConnection != null) {
            httpURLConnection.disconnect();
        }
        this.Hl = null;
    }

    @Override // com.bumptech.glide.load.a.d
    @NonNull
    public Class<InputStream> ga() {
        return InputStream.class;
    }
}
