package com.android.camera.network.net.json;

import com.android.camera.network.net.base.Cacheable;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.ss.android.vesdk.runtime.cloudconfig.HttpRequest;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonObjectRequest extends Request<JSONObject> implements Cacheable {
    private String mCacheKey = null;
    private volatile byte[] mData = null;
    private Map<String, String> mHeaders = null;
    private volatile boolean mIsFromCache = false;
    private Response.Listener<JSONObject> mListener;
    private Map<String, String> mParams = null;

    public JsonObjectRequest(int i, String str, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(i, str, errorListener);
        this.mListener = listener;
    }

    public static String parseCharset(Map<String, String> map, String str) {
        String str2 = map.get("Content-Type");
        if (str2 != null) {
            String[] split = str2.split(";");
            for (int i = 1; i < split.length; i++) {
                String[] split2 = split[i].trim().split("=");
                if (split2.length == 2 && split2[0].equals(HttpRequest.PARAM_CHARSET)) {
                    return split2[1];
                }
            }
        }
        return str;
    }

    /* access modifiers changed from: protected */
    public void deliverResponse(JSONObject jSONObject) {
        Response.Listener<JSONObject> listener = this.mListener;
        if (listener != null) {
            listener.onResponse(jSONObject);
        }
    }

    @Override // com.android.volley.Request
    public String getCacheKey() {
        String str = this.mCacheKey;
        return str == null ? super.getCacheKey() : str;
    }

    @Override // com.android.camera.network.net.base.Cacheable
    public byte[] getData() {
        return this.mData;
    }

    @Override // com.android.volley.Request
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> map = this.mHeaders;
        return map != null ? map : super.getHeaders();
    }

    @Override // com.android.volley.Request
    public Map<String, String> getParams() throws AuthFailureError {
        Map<String, String> map = this.mParams;
        return map != null ? map : super.getParams();
    }

    @Override // com.android.camera.network.net.base.Cacheable
    public boolean isFromCache() {
        return this.mIsFromCache;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.volley.Request
    public Response<JSONObject> parseNetworkResponse(NetworkResponse networkResponse) {
        try {
            this.mIsFromCache = networkResponse.headers.containsKey(Cacheable.HEADER_FROM_CACHE);
            this.mData = networkResponse.data;
            return Response.success(new JSONObject(new String(networkResponse.data, parseCharset(networkResponse.headers, "utf-8"))), HttpHeaderParser.parseCacheHeaders(networkResponse));
        } catch (UnsupportedEncodingException e2) {
            return Response.error(new ParseError(e2));
        } catch (JSONException e3) {
            return Response.error(new ParseError(e3));
        }
    }

    @Override // com.android.camera.network.net.base.Cacheable
    public void setCacheKey(String str) {
        this.mCacheKey = str;
    }

    public void setHeaders(Map<String, String> map) {
        this.mHeaders = map;
    }

    public void setParams(Map<String, String> map) {
        this.mParams = map;
    }
}
