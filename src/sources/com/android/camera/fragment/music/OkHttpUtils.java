package com.android.camera.fragment.music;

import com.android.camera.fragment.music.FragmentLiveMusic;
import com.android.camera.log.Log;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Deprecated
public class OkHttpUtils {
    /* access modifiers changed from: private */
    public static final String TAG = OkHttpUtils.class.getSimpleName();
    private static final OkHttpClient client = new OkHttpClient();

    private static String buildUrlParams(Map<String, String> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (sb.length() > 0) {
                sb.append("&");
            }
            sb.append(entry.getKey());
            sb.append("=");
            sb.append(entry.getValue());
        }
        return sb.toString();
    }

    private static Call createGetCall(String str, Map<String, String> map) {
        String buildUrlParams = buildUrlParams(map);
        Request.Builder builder = new Request.Builder().get();
        return client.newCall(builder.url(str + '?' + buildUrlParams).build());
    }

    public static void downloadMp3Async(final LiveMusicInfo liveMusicInfo, final String str, final FragmentLiveMusic.Mp3DownloadCallback mp3DownloadCallback) {
        new OkHttpClient.Builder().connectTimeout(5, TimeUnit.SECONDS).writeTimeout(5, TimeUnit.SECONDS).readTimeout(5, TimeUnit.SECONDS).build().newCall(new Request.Builder().url(liveMusicInfo.mPlayUrl).build()).enqueue(new Callback() {
            /* class com.android.camera.fragment.music.OkHttpUtils.AnonymousClass1 */

            @Override // okhttp3.Callback
            public void onFailure(Call call, IOException iOException) {
                String access$000 = OkHttpUtils.TAG;
                Log.e(access$000, "download mp3 async failed with exception " + iOException.getMessage());
                FragmentLiveMusic.Mp3DownloadCallback.this.onFailed();
            }

            @Override // okhttp3.Callback
            public void onResponse(Call call, Response response) {
                Log.d(OkHttpUtils.TAG, "onResponse");
                try {
                    byte[] bytes = response.body().bytes();
                    PrintStream printStream = new PrintStream(str);
                    printStream.write(bytes, 0, bytes.length);
                    printStream.close();
                    FragmentLiveMusic.Mp3DownloadCallback.this.onCompleted(liveMusicInfo);
                } catch (IOException e2) {
                    String access$000 = OkHttpUtils.TAG;
                    Log.e(access$000, "download mp3 async failed with exception " + e2.getMessage());
                    File file = new File(str);
                    if (file.exists()) {
                        file.delete();
                    }
                    FragmentLiveMusic.Mp3DownloadCallback.this.onFailed();
                }
            }
        });
    }

    public static void get(String str, Map<String, String> map, Callback callback) {
        createGetCall(str, map).enqueue(callback);
    }
}
