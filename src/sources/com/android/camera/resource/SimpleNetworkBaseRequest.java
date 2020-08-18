package com.android.camera.resource;

import android.util.Log;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public abstract class SimpleNetworkBaseRequest<T> extends BaseObservableRequest<T> {
    protected static final OkHttpClient CLIENT = new OkHttpClient.Builder().connectTimeout(15, TimeUnit.SECONDS).writeTimeout(15, TimeUnit.SECONDS).readTimeout(15, TimeUnit.SECONDS).build();
    protected static final String TAG = "SimpleNetworkBaseRequest";
    private Map<String, String> mHeaders;
    protected Map<String, String> mParams;
    protected String mUrl;

    public SimpleNetworkBaseRequest(String str) {
        setUrl(str);
    }

    private void addHeaders(Request.Builder builder) {
        Map<String, String> map = this.mHeaders;
        if (map != null && !map.isEmpty()) {
            for (Map.Entry<String, String> entry : this.mHeaders.entrySet()) {
                builder.addHeader(entry.getKey(), entry.getValue());
            }
        }
    }

    private String appendUrlParams() {
        Map<String, String> map;
        if (this.mUrl == null || (map = this.mParams) == null || map.isEmpty()) {
            return this.mUrl;
        }
        StringBuilder sb = new StringBuilder(this.mUrl);
        if (this.mUrl.indexOf(63) > 0) {
            if (!this.mUrl.endsWith("?") && !this.mUrl.endsWith("&")) {
                sb.append("&");
            }
            sb.append(encodeParameters(this.mParams, "UTF-8"));
            return sb.toString();
        }
        sb.append("?");
        sb.append(encodeParameters(this.mParams, "UTF-8"));
        return sb.toString();
    }

    private String encodeParameters(Map<String, String> map, String str) {
        StringBuilder sb = new StringBuilder();
        try {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                sb.append(URLEncoder.encode(entry.getKey(), str));
                sb.append('=');
                sb.append(URLEncoder.encode(entry.getValue(), str));
                sb.append('&');
            }
            return sb.toString();
        } catch (UnsupportedEncodingException e2) {
            throw new RuntimeException("Encoding not supported: " + str, e2);
        }
    }

    public final void addHeaders(String str, String str2) {
        if (this.mHeaders == null) {
            this.mHeaders = new HashMap();
        }
        this.mHeaders.put(str, str2);
    }

    /* access modifiers changed from: protected */
    public void addParam(String str, String str2) {
        if (this.mParams == null) {
            this.mParams = new HashMap();
        }
        this.mParams.put(str, str2);
    }

    /* access modifiers changed from: protected */
    public RequestBody generatePostBody() {
        return null;
    }

    /* access modifiers changed from: protected */
    public abstract T process(String str) throws BaseRequestException;

    /* access modifiers changed from: protected */
    @Override // com.android.camera.resource.BaseObservableRequest
    public void scheduleRequest(final ResponseListener<T> responseListener, Class<T> cls, T t) {
        Request.Builder url = new Request.Builder().get().url(appendUrlParams());
        addHeaders(url);
        RequestBody generatePostBody = generatePostBody();
        CLIENT.newCall(generatePostBody == null ? url.build() : url.post(generatePostBody).build()).enqueue(new Callback() {
            /* class com.android.camera.resource.SimpleNetworkBaseRequest.AnonymousClass1 */

            @Override // okhttp3.Callback
            public void onFailure(Call call, IOException iOException) {
                Log.e(SimpleNetworkBaseRequest.TAG, "scheduleRequest onFailure", iOException);
                ResponseListener responseListener = responseListener;
                if (responseListener != null) {
                    responseListener.onResponseError(0, iOException.getMessage(), iOException);
                }
            }

            @Override // okhttp3.Callback
            public void onResponse(Call call, Response response) {
                if (!response.isSuccessful()) {
                    ResponseListener responseListener = responseListener;
                    if (responseListener != null) {
                        responseListener.onResponseError(1, response.message(), response);
                    }
                } else {
                    try {
                        T process = SimpleNetworkBaseRequest.this.process(response.body().string());
                        if (responseListener != null) {
                            responseListener.onResponse(process, false);
                        }
                    } catch (BaseRequestException e2) {
                        ResponseListener responseListener2 = responseListener;
                        if (responseListener2 != null) {
                            responseListener2.onResponseError(e2.getErrorCode(), e2.getMessage(), response);
                        }
                    } catch (IOException e3) {
                        ResponseListener responseListener3 = responseListener;
                        if (responseListener3 != null) {
                            responseListener3.onResponseError(0, e3.getMessage(), response);
                        }
                    }
                }
                response.close();
            }
        });
    }

    public void setUrl(String str) {
        this.mUrl = str;
    }
}
