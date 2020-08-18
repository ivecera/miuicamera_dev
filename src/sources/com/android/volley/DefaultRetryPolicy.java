package com.android.volley;

public class DefaultRetryPolicy implements RetryPolicy {
    public static final float DEFAULT_BACKOFF_MULT = 1.0f;
    public static final int DEFAULT_MAX_RETRIES = 1;
    public static final int DEFAULT_TIMEOUT_MS = 2500;
    private final float mBackoffMultiplier;
    private int mCurrentRetryCount;
    private int mCurrentTimeoutMs;
    private final int mMaxNumRetries;

    public DefaultRetryPolicy() {
        this(DEFAULT_TIMEOUT_MS, 1, 1.0f);
    }

    public DefaultRetryPolicy(int i, int i2, float f2) {
        this.mCurrentTimeoutMs = i;
        this.mMaxNumRetries = i2;
        this.mBackoffMultiplier = f2;
    }

    public float getBackoffMultiplier() {
        return this.mBackoffMultiplier;
    }

    @Override // com.android.volley.RetryPolicy
    public int getCurrentRetryCount() {
        return this.mCurrentRetryCount;
    }

    @Override // com.android.volley.RetryPolicy
    public int getCurrentTimeout() {
        return this.mCurrentTimeoutMs;
    }

    /* access modifiers changed from: protected */
    public boolean hasAttemptRemaining() {
        return this.mCurrentRetryCount <= this.mMaxNumRetries;
    }

    @Override // com.android.volley.RetryPolicy
    public void retry(VolleyError volleyError) throws VolleyError {
        this.mCurrentRetryCount++;
        int i = this.mCurrentTimeoutMs;
        this.mCurrentTimeoutMs = i + ((int) (((float) i) * this.mBackoffMultiplier));
        if (!hasAttemptRemaining()) {
            throw volleyError;
        }
    }
}
