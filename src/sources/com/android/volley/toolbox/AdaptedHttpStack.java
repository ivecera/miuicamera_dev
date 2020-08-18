package com.android.volley.toolbox;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Map;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.conn.ConnectTimeoutException;

class AdaptedHttpStack extends BaseHttpStack {
    private final HttpStack mHttpStack;

    AdaptedHttpStack(HttpStack httpStack) {
        this.mHttpStack = httpStack;
    }

    @Override // com.android.volley.toolbox.BaseHttpStack
    public HttpResponse executeRequest(Request<?> request, Map<String, String> map) throws IOException, AuthFailureError {
        try {
            HttpResponse performRequest = this.mHttpStack.performRequest(request, map);
            int statusCode = performRequest.getStatusLine().getStatusCode();
            Header[] allHeaders = performRequest.getAllHeaders();
            ArrayList arrayList = new ArrayList(allHeaders.length);
            for (Header header : allHeaders) {
                arrayList.add(new com.android.volley.Header(header.getName(), header.getValue()));
            }
            if (performRequest.getEntity() == null) {
                return new HttpResponse(statusCode, arrayList);
            }
            long contentLength = performRequest.getEntity().getContentLength();
            if (((long) ((int) contentLength)) == contentLength) {
                return new HttpResponse(statusCode, arrayList, (int) performRequest.getEntity().getContentLength(), performRequest.getEntity().getContent());
            }
            throw new IOException("Response too large: " + contentLength);
        } catch (ConnectTimeoutException e2) {
            throw new SocketTimeoutException(e2.getMessage());
        }
    }
}
