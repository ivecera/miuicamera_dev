package com.android.camera.network.live;

import com.android.camera.fragment.CtaNoticeFragment;
import com.android.camera.log.Log;
import com.android.camera.network.net.base.ErrorCode;
import com.android.camera.network.net.base.ResponseListener;
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
import okhttp3.Response;

public abstract class BaseRequest<T> {
    protected static final OkHttpClient CLIENT = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS).writeTimeout(10, TimeUnit.SECONDS).readTimeout(10, TimeUnit.SECONDS).build();
    protected static final String TAG = "BaseRequest";
    protected Map<String, String> mParams = new HashMap();
    protected String mUrl;

    public BaseRequest(String str) {
        this.mUrl = str;
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

    /* access modifiers changed from: protected */
    public void addParam(String str, String str2) {
        this.mParams.put(str, str2);
    }

    public void execute(final ResponseListener responseListener) {
        if (!CtaNoticeFragment.CTA.canConnectNetwork()) {
            responseListener.onResponseError(ErrorCode.NETWORK_NOT_CONNECTED, "CTA not confirmed.", null);
            return;
        }
        CLIENT.newCall(new Request.Builder().get().url(appendUrlParams()).build()).enqueue(new Callback() {
            /* class com.android.camera.network.live.BaseRequest.AnonymousClass1 */

            @Override // okhttp3.Callback
            public void onFailure(Call call, IOException iOException) {
                Log.e(BaseRequest.TAG, "execute failed", iOException);
                responseListener.onResponseError(ErrorCode.NET_ERROR, iOException.getMessage(), iOException);
            }

            @Override // okhttp3.Callback
            public void onResponse(Call call, Response response) {
                if (!response.isSuccessful()) {
                    responseListener.onResponseError(ErrorCode.SERVER_ERROR, response.message(), response);
                } else {
                    try {
                        T process = BaseRequest.this.process(response.body().string());
                        responseListener.onResponse(process);
                    } catch (BaseRequestException e2) {
                        Log.e(BaseRequest.TAG, "execute process failed", e2);
                        responseListener.onResponseError(e2.getErrorCode(), e2.getMessage(), response);
                    } catch (IOException e3) {
                        Log.e(BaseRequest.TAG, "execute process failed", e3);
                        responseListener.onResponseError(ErrorCode.NET_ERROR, e3.getMessage(), response);
                    }
                }
                response.close();
            }
        });
    }

    /* access modifiers changed from: protected */
    public abstract T process(String str) throws BaseRequestException;
}
