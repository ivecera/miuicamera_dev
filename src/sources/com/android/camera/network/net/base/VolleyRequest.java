package com.android.camera.network.net.base;

import android.os.Looper;
import com.android.camera.network.net.HttpManager;
import com.android.camera.network.threadpool.ThreadManager;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import java.util.concurrent.CountDownLatch;

public abstract class VolleyRequest<T, E> extends BaseRequest<E> implements Response.Listener<T>, Response.ErrorListener {
    private Request<T> mRequest;
    private CountDownLatch mSyncExecuteLock = null;

    private void releaseSyncExecuteLock() {
        CountDownLatch countDownLatch = this.mSyncExecuteLock;
        if (countDownLatch != null) {
            countDownLatch.countDown();
            this.mSyncExecuteLock = null;
        }
    }

    @Override // com.android.camera.network.net.base.BaseRequest
    public final void cancel() {
        releaseSyncExecuteLock();
        setOnResponseListener(null);
        Request<T> request = this.mRequest;
        if (request != null) {
            request.cancel();
        }
    }

    /* access modifiers changed from: protected */
    public abstract Request<T> createVolleyRequest(Response.Listener<T> listener, Response.ErrorListener errorListener);

    @Override // com.android.camera.network.net.base.BaseRequest
    public void execute() {
        this.mRequest = createVolleyRequest(this, this);
        Request<T> request = this.mRequest;
        if (request != null) {
            request.setRetryPolicy(new DefaultRetryPolicy(10000, 1, 1.0f));
            this.mRequest.setShouldCache(isUseCache());
            Object tag = getTag();
            if (this.mRequest.getTag() == null && tag != null) {
                this.mRequest.setTag(tag);
            }
            HttpManager.getInstance().addToRequestQueue(this.mRequest);
        }
    }

    @Override // com.android.camera.network.net.base.BaseRequest
    public Object[] executeSync() throws RequestError {
        if (Looper.myLooper() == Looper.getMainLooper() || Looper.myLooper() == ThreadManager.getRequestThreadLooper()) {
            throw new RuntimeException("executeSync could not call on main thread or request thread.");
        }
        this.mSyncExecuteLock = new CountDownLatch(1);
        execute();
        try {
            this.mSyncExecuteLock.await();
        } catch (InterruptedException unused) {
        }
        RequestError requestError = ((BaseRequest) this).mRequestError;
        if (requestError == null) {
            return ((BaseRequest) this).mResponse;
        }
        throw requestError;
    }

    /* access modifiers changed from: protected */
    public final void handleError(ErrorCode errorCode, String str, Object obj) {
        ((BaseRequest) this).mCacheExpires = -1;
        onRequestError(errorCode, str, obj);
    }

    /* access modifiers changed from: protected */
    public abstract void handleResponse(T t);

    @Override // com.android.volley.Response.ErrorListener
    public final void onErrorResponse(VolleyError volleyError) {
        ErrorCode errorCode = ErrorCode.NET_ERROR;
        if (volleyError instanceof RequestError) {
            errorCode = ((RequestError) volleyError).getErrorCode();
        }
        Throwable cause = volleyError.getCause();
        if (cause != null) {
            volleyError = cause;
        }
        handleError(errorCode, volleyError.getMessage(), volleyError);
        releaseSyncExecuteLock();
    }

    @Override // com.android.volley.Response.Listener
    public final void onResponse(T t) {
        handleResponse(t);
        releaseSyncExecuteLock();
        Request<T> request = this.mRequest;
        if (request instanceof Cacheable) {
            Cacheable cacheable = (Cacheable) request;
            if (((BaseRequest) this).mCacheExpires > 0 && !cacheable.isFromCache()) {
                HttpManager.getInstance().putToCache(this.mRequest.getCacheKey(), cacheable.getData(), ((BaseRequest) this).mCacheExpires, ((BaseRequest) this).mCacheSoftTtl);
            }
        }
    }
}
