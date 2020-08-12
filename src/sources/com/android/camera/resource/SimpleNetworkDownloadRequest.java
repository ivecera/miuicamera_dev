package com.android.camera.resource;

import com.android.camera.log.Log;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.concurrent.TimeUnit;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SimpleNetworkDownloadRequest<T> extends BaseObservableRequest<T> {
    protected static final OkHttpClient CLIENT = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS).writeTimeout(10, TimeUnit.SECONDS).readTimeout(10, TimeUnit.SECONDS).build();
    protected static final String TAG = "DownloadRequest";
    private String downloadUrl;
    /* access modifiers changed from: private */
    public String outputPath;

    public SimpleNetworkDownloadRequest(String str, String str2) {
        this.downloadUrl = str;
        this.outputPath = str2;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.resource.BaseObservableRequest
    public void scheduleRequest(final ResponseListener<T> responseListener, Class<T> cls, final T t) {
        CLIENT.newCall(new Request.Builder().get().url(this.downloadUrl).build()).enqueue(new Callback() {
            /* class com.android.camera.resource.SimpleNetworkDownloadRequest.AnonymousClass1 */

            @Override // okhttp3.Callback
            public void onFailure(Call call, IOException iOException) {
                Log.e(SimpleNetworkDownloadRequest.TAG, "download async failed with exception " + iOException.getMessage());
                ResponseListener responseListener = responseListener;
                if (responseListener != null) {
                    responseListener.onResponseError(0, iOException.getMessage(), iOException);
                }
            }

            @Override // okhttp3.Callback
            public void onResponse(Call call, Response response) {
                Log.d(SimpleNetworkDownloadRequest.TAG, "onResponse");
                try {
                    byte[] bytes = response.body().bytes();
                    PrintStream printStream = new PrintStream(SimpleNetworkDownloadRequest.this.outputPath);
                    printStream.write(bytes, 0, bytes.length);
                    printStream.close();
                    if (responseListener != null) {
                        responseListener.onResponse(t, false);
                    }
                } catch (IOException e2) {
                    Log.e(SimpleNetworkDownloadRequest.TAG, "download async failed with exception " + e2.getMessage());
                    File file = new File(SimpleNetworkDownloadRequest.this.outputPath);
                    if (file.exists()) {
                        file.delete();
                    }
                    ResponseListener responseListener = responseListener;
                    if (responseListener != null) {
                        responseListener.onResponseError(3, e2.getMessage(), response);
                    }
                }
            }
        });
    }
}
