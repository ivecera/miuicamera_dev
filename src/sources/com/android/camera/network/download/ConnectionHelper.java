package com.android.camera.network.download;

import android.net.Uri;
import android.text.TextUtils;
import com.android.camera.fragment.CtaNoticeFragment;
import com.android.camera.log.Log;
import com.android.camera.network.util.NetworkUtils;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

class ConnectionHelper {
    public static final int NETWORK_TYPE_METERED = 0;
    public static final int NETWORK_TYPE_UNMETERED = 1;
    public static final int REASON_ILLEGAL_ACTIVE_NETWORK = 3;
    public static final int REASON_IO_EXCEPTION = 4;
    public static final int REASON_NETWORK_DENIED = 2;
    public static final int REASON_NO_NETWORK = 1;
    public static final int REASON_SUCCESS = 0;
    private static final String TAG = "ConnectionManager";

    static final class Holder<T> {
        public final int reason;
        public final T value;

        public Holder(int i) {
            this.value = null;
            this.reason = i;
        }

        public Holder(T t) {
            this.value = t;
            this.reason = 0;
        }
    }

    ConnectionHelper() {
    }

    static Holder<HttpURLConnection> open(Uri uri, int i) {
        String scheme = uri.getScheme();
        if (TextUtils.equals(scheme, "http") || TextUtils.equals(scheme, "https")) {
            return open(uri.toString(), i);
        }
        throw new IllegalArgumentException("not support scheme " + scheme);
    }

    static Holder<HttpURLConnection> open(String str, int i) {
        int verify = verify(i);
        if (verify != 0) {
            return new Holder<>(verify);
        }
        try {
            Log.d(TAG, "try open http connection");
            return new Holder<>((HttpURLConnection) new URL(str).openConnection());
        } catch (MalformedURLException e2) {
            throw new IllegalArgumentException("invalid url " + str, e2);
        } catch (IOException e3) {
            Log.w(TAG, e3);
            return new Holder<>(4);
        }
    }

    private static int verify(int i) {
        Log.d(TAG, "refreshing network state");
        if (!NetworkUtils.isNetworkConnected()) {
            return 1;
        }
        if (!CtaNoticeFragment.CTA.canConnectNetwork()) {
            return 2;
        }
        return (i != 1 || !NetworkUtils.isActiveNetworkMetered()) ? 0 : 3;
    }
}
