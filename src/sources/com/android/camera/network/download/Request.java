package com.android.camera.network.download;

import android.net.Uri;
import android.text.TextUtils;
import com.android.camera.log.Log;
import com.android.camera.network.download.DownloadTask;
import java.io.File;

public final class Request {
    private static final String TAG = "Request";
    private boolean mAllowedOverMetered;
    private File mDestination;
    private int mMaxRetryTimes = 3;
    private String mTag;
    private Uri mUri;
    private Verifier mVerifier;

    public interface Constants extends DownloadTask.OpenConstants {
    }

    public Request(String str, Uri uri, File file) {
        this.mTag = str;
        this.mUri = uri;
        String scheme = uri.getScheme();
        if (TextUtils.equals(scheme, "http") || TextUtils.equals(scheme, "https")) {
            if (file.exists()) {
                if (!file.isDirectory()) {
                    Log.w(TAG, "output file will be overwritten");
                } else {
                    Log.d(TAG, "output file is a directory");
                    throw new IllegalStateException("dst file exists, is a directory");
                }
            }
            this.mDestination = file;
            return;
        }
        throw new IllegalArgumentException("not support scheme: " + scheme);
    }

    public File getDestination() {
        return this.mDestination;
    }

    public int getMaxRetryTimes() {
        return this.mMaxRetryTimes;
    }

    /* access modifiers changed from: package-private */
    public int getNetworkType() {
        return !this.mAllowedOverMetered;
    }

    public String getTag() {
        return this.mTag;
    }

    public Uri getUri() {
        return this.mUri;
    }

    public Verifier getVerifier() {
        return this.mVerifier;
    }

    public boolean isAllowedOverMetered() {
        return this.mAllowedOverMetered;
    }

    public void setAllowedOverMetered(boolean z) {
        this.mAllowedOverMetered = z;
    }

    public void setMaxRetryTimes(int i) {
        this.mMaxRetryTimes = i;
    }

    public void setVerifier(Verifier verifier) {
        this.mVerifier = verifier;
    }
}
