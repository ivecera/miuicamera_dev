package com.android.camera.fragment.subtitle.recog;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import com.android.camera.fragment.subtitle.FragmentSubtitle;
import com.android.camera.fragment.subtitle.Util;
import com.android.camera.fragment.subtitle.recog.record.PcmRecorder;
import com.android.camera.statistic.CameraStatUtils;
import com.google.android.apps.photos.api.PhotosOemApi;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ss.android.ugc.effectmanager.EffectConfiguration;
import com.ss.android.vesdk.VEEditor;
import com.xiaomi.stat.d;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Base64;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class VoiceOnlineRecog {
    private static final String FINAL_RESULT_TYPE = "0";
    private static final String HOSTURL = "wss://xiaomi-ist-api.xfyun.cn/v2/ist";
    private static final int SOCKET_TIME_OUT = 20000;
    private static final String TAG = "VoiceOnlineRecog";
    private final int SAMPLE_RATE = PcmRecorder.RATE16K;
    public final int StatusContinueFrame = 1;
    public final int StatusFirstFrame = 0;
    public final int StatusLastFrame = 2;
    private Context context;
    /* access modifiers changed from: private */
    public int currentStatus = 0;
    protected Handler handler = new Handler();
    /* access modifiers changed from: private */
    public boolean isDisConnect;
    /* access modifiers changed from: private */
    public boolean isPauseRecording;
    /* access modifiers changed from: private */
    public boolean isStopRecording;
    public final Gson json = new Gson();
    /* access modifiers changed from: private */
    public boolean mCanStartRecord = true;
    /* access modifiers changed from: private */
    public String mEdTime;
    private long mNeedRemoveTime;
    /* access modifiers changed from: private */
    public long mPauseRecordingTime;
    private PcmRecorder.PcmRecordListener mPcmRecordListener = new PcmRecorder.PcmRecordListener() {
        /* class com.android.camera.fragment.subtitle.recog.VoiceOnlineRecog.AnonymousClass1 */

        @Override // com.android.camera.fragment.subtitle.recog.record.PcmRecorder.PcmRecordListener
        public void onError(int i) {
            boolean unused = VoiceOnlineRecog.this.mCanStartRecord = false;
        }

        @Override // com.android.camera.fragment.subtitle.recog.record.PcmRecorder.PcmRecordListener
        public void onRecordBuffer(byte[] bArr, int i, int i2, int i3) {
            if (bArr != null) {
                try {
                    JsonObject jsonObject = new JsonObject();
                    if (VoiceOnlineRecog.this.currentStatus == 0) {
                        JsonObject jsonObject2 = new JsonObject();
                        JsonObject jsonObject3 = new JsonObject();
                        jsonObject3.addProperty("app_id", Util.getAccessAppID());
                        jsonObject2.addProperty("aue", "raw");
                        jsonObject2.addProperty(EffectConfiguration.KEY_SYS_LANGUAGE, "cn_en");
                        jsonObject2.addProperty("accent", "mandarin");
                        jsonObject2.addProperty("domain", "xiaomi");
                        jsonObject2.addProperty("rf", "deserted");
                        jsonObject2.addProperty("dwa", "wpgs");
                        jsonObject2.addProperty("rate", "16000");
                        jsonObject2.addProperty("vgap", (Number) 15);
                        jsonObject.add("common", jsonObject3);
                        jsonObject.add("business", jsonObject2);
                    }
                    JsonObject jsonObject4 = new JsonObject();
                    jsonObject4.addProperty("status", Integer.valueOf(VoiceOnlineRecog.this.currentStatus));
                    jsonObject4.addProperty(VEEditor.MVConsts.TYPE_AUDIO, Base64.getEncoder().encodeToString(Arrays.copyOf(bArr, i2)));
                    jsonObject.add(PhotosOemApi.PATH_SPECIAL_TYPE_DATA, jsonObject4);
                    VoiceOnlineRecog.this.webSocket.send(jsonObject.toString());
                    int unused = VoiceOnlineRecog.this.currentStatus = 1;
                } catch (Exception e2) {
                    Log.e(VoiceOnlineRecog.TAG, "onRecordBuffer Exception: " + e2);
                }
            } else {
                Log.e(VoiceOnlineRecog.TAG, "onRecordBuffer data was null");
            }
        }

        @Override // com.android.camera.fragment.subtitle.recog.record.PcmRecorder.PcmRecordListener
        public void onRecordReleased() {
            boolean unused = VoiceOnlineRecog.this.mCanStartRecord = true;
            Log.d(VoiceOnlineRecog.TAG, "onRecordReleased ");
            JsonObject jsonObject = new JsonObject();
            JsonObject jsonObject2 = new JsonObject();
            jsonObject2.addProperty("status", (Number) 2);
            jsonObject2.addProperty("encoding", "raw");
            jsonObject.add(PhotosOemApi.PATH_SPECIAL_TYPE_DATA, jsonObject2);
            VoiceOnlineRecog.this.webSocket.send(jsonObject.toString());
            int unused2 = VoiceOnlineRecog.this.currentStatus = 0;
        }

        @Override // com.android.camera.fragment.subtitle.recog.record.PcmRecorder.PcmRecordListener
        public void onRecordStarted(boolean z) {
            if (z) {
                boolean unused = VoiceOnlineRecog.this.mCanStartRecord = false;
            }
        }
    };
    /* access modifiers changed from: private */
    public PcmRecorder mPcmRecorder;
    /* access modifiers changed from: private */
    public long mResumeRecordingTime;
    /* access modifiers changed from: private */
    public String mStTime;
    /* access modifiers changed from: private */
    public long mStartRecordingTime;
    /* access modifiers changed from: private */
    public FragmentSubtitle.RecognitionListener recognitionListener;
    /* access modifiers changed from: private */
    public StringBuilder srtBuilder = new StringBuilder();
    private int srtRowNum = 1;
    /* access modifiers changed from: private */
    public String type = "";
    /* access modifiers changed from: private */
    public WebSocket webSocket;

    class Listener extends WebSocketListener {
        Listener() {
        }

        @Override // okhttp3.WebSocketListener
        public void onClosed(WebSocket webSocket, int i, String str) {
            super.onClosed(webSocket, i, str);
            Log.d(VoiceOnlineRecog.TAG, "onClosed: " + str);
        }

        @Override // okhttp3.WebSocketListener
        public void onClosing(WebSocket webSocket, int i, String str) {
            super.onClosing(webSocket, i, str);
            Log.d(VoiceOnlineRecog.TAG, "onClosing: " + str);
        }

        @Override // okhttp3.WebSocketListener
        public void onFailure(WebSocket webSocket, Throwable th, Response response) {
            super.onFailure(webSocket, th, response);
            Log.d(VoiceOnlineRecog.TAG, "onFailure: t " + th);
            if (VoiceOnlineRecog.this.mPcmRecorder != null) {
                Log.d(VoiceOnlineRecog.TAG, "stop recorder on disconnect ");
                VoiceOnlineRecog.this.mPcmRecorder.stopRecord(true);
                webSocket.cancel();
                PcmRecorder unused = VoiceOnlineRecog.this.mPcmRecorder = null;
            }
            boolean unused2 = VoiceOnlineRecog.this.isDisConnect = true;
            if (!VoiceOnlineRecog.this.isStopRecording) {
                VoiceOnlineRecog.this.recognitionListener.onFailure();
                CameraStatUtils.trackInterruptionNetwork();
                if (response != null) {
                    try {
                        Log.d(VoiceOnlineRecog.TAG, response.code() + "");
                        Log.d(VoiceOnlineRecog.TAG, response.body().string());
                    } catch (IOException e2) {
                        Log.e(VoiceOnlineRecog.TAG, "IOException: " + e2);
                    }
                }
            }
        }

        @Override // okhttp3.WebSocketListener
        public void onMessage(WebSocket webSocket, String str) {
            super.onMessage(webSocket, str);
            String access$700 = VoiceOnlineRecog.this.getContent(str);
            if (TextUtils.isEmpty(access$700)) {
                Log.e(VoiceOnlineRecog.TAG, "subitle is empty  ");
                return;
            }
            if ("0".equals(VoiceOnlineRecog.this.type)) {
                long access$900 = VoiceOnlineRecog.this.mPauseRecordingTime - VoiceOnlineRecog.this.mStartRecordingTime;
                long access$1100 = VoiceOnlineRecog.this.mResumeRecordingTime - VoiceOnlineRecog.this.mStartRecordingTime;
                Long valueOf = Long.valueOf(VoiceOnlineRecog.this.mEdTime);
                Long valueOf2 = Long.valueOf(VoiceOnlineRecog.this.mStTime);
                if ((VoiceOnlineRecog.this.isPauseRecording && valueOf2.longValue() > access$900) || valueOf.longValue() < access$1100) {
                    Log.e(VoiceOnlineRecog.TAG, "Subtitles in pause: ");
                    return;
                }
            }
            String trim = access$700.trim();
            String replaceAll = trim.replaceAll("[^a-z^A-Z^0-9]", "");
            int i = 30;
            if (replaceAll.length() != 0) {
                i = (trim.length() - replaceAll.length()) + trim.split("\\s+").length == 30 ? trim.length() : 53;
            }
            if (trim.length() > i) {
                trim = trim.substring(0, i);
            }
            String replace = trim.replace("。", "").replace(".", "");
            VoiceOnlineRecog.this.showSubtitleContent(replace);
            if ("0".equals(VoiceOnlineRecog.this.type)) {
                CameraStatUtils.trackTriggerSubtitle();
                VoiceOnlineRecog voiceOnlineRecog = VoiceOnlineRecog.this;
                String access$1600 = voiceOnlineRecog.getTime(voiceOnlineRecog.mStTime);
                VoiceOnlineRecog voiceOnlineRecog2 = VoiceOnlineRecog.this;
                String access$16002 = voiceOnlineRecog2.getTime(voiceOnlineRecog2.mEdTime);
                StringBuilder access$1800 = VoiceOnlineRecog.this.srtBuilder;
                access$1800.append(VoiceOnlineRecog.access$1708(VoiceOnlineRecog.this) + "\n");
                VoiceOnlineRecog.this.srtBuilder.append(String.format("%s --> %s\n", access$1600, access$16002));
                VoiceOnlineRecog.this.srtBuilder.append(String.format("%s\n", replace));
                VoiceOnlineRecog.this.srtBuilder.append("\n");
                if (VoiceOnlineRecog.this.isStopRecording && VoiceOnlineRecog.this.mPcmRecorder != null) {
                    Log.d(VoiceOnlineRecog.TAG, "stop recorder on final result return");
                    VoiceOnlineRecog.this.mPcmRecorder.stopRecord(true);
                    webSocket.cancel();
                    PcmRecorder unused = VoiceOnlineRecog.this.mPcmRecorder = null;
                }
            }
        }

        @Override // okhttp3.WebSocketListener
        public void onMessage(WebSocket webSocket, ByteString byteString) {
            super.onMessage(webSocket, byteString);
        }

        @Override // okhttp3.WebSocketListener
        public void onOpen(WebSocket webSocket, Response response) {
            super.onOpen(webSocket, response);
        }
    }

    public VoiceOnlineRecog(Context context2, FragmentSubtitle.RecognitionListener recognitionListener2) {
        this.context = context2;
        this.recognitionListener = recognitionListener2;
    }

    static /* synthetic */ int access$1708(VoiceOnlineRecog voiceOnlineRecog) {
        int i = voiceOnlineRecog.srtRowNum;
        voiceOnlineRecog.srtRowNum = i + 1;
        return i;
    }

    /* access modifiers changed from: private */
    public String getContent(String str) {
        JsonObject asJsonObject;
        JsonObject asJsonObject2;
        JsonObject asJsonObject3;
        JsonObject asJsonObject4;
        JsonObject asJsonObject5 = new JsonParser().parse(str).getAsJsonObject();
        if (asJsonObject5 == null || (asJsonObject = asJsonObject5.getAsJsonObject(PhotosOemApi.PATH_SPECIAL_TYPE_DATA)) == null || (asJsonObject2 = asJsonObject.getAsJsonObject("result")) == null || (asJsonObject3 = asJsonObject2.getAsJsonObject("cn")) == null || (asJsonObject4 = asJsonObject3.getAsJsonObject(d.n)) == null) {
            return "";
        }
        this.type = asJsonObject4.getAsJsonPrimitive("type").getAsString();
        this.mStTime = asJsonObject4.getAsJsonPrimitive("bg").getAsString();
        this.mEdTime = asJsonObject4.getAsJsonPrimitive("ed").getAsString();
        JsonArray asJsonArray = asJsonObject4.getAsJsonArray("rt").get(0).getAsJsonObject().getAsJsonArray("ws");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < asJsonArray.size(); i++) {
            sb.append(asJsonArray.get(i).getAsJsonObject().getAsJsonArray("cw").get(0).getAsJsonObject().getAsJsonPrimitive("w").getAsString());
        }
        return sb.toString();
    }

    /* access modifiers changed from: private */
    public String getTime(String str) {
        long longValue = Long.valueOf(str).longValue();
        long j = this.mNeedRemoveTime;
        if (longValue > j) {
            longValue -= j;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss,SSS");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));
        return simpleDateFormat.format(Long.valueOf(longValue));
    }

    private void initWebSocket() {
        this.webSocket = new OkHttpClient.Builder().connectTimeout(20000, TimeUnit.MILLISECONDS).readTimeout(20000, TimeUnit.MILLISECONDS).build().newWebSocket(new Request.Builder().url(AuthUtils.assembleRequestUrl(HOSTURL, Util.getAccessAppKey(), Util.getAccessAppSecret())).build(), new Listener());
    }

    /* access modifiers changed from: private */
    public void showSubtitleContent(final String str) {
        if (!this.isPauseRecording) {
            ((Activity) this.context).runOnUiThread(new Runnable() {
                /* class com.android.camera.fragment.subtitle.recog.VoiceOnlineRecog.AnonymousClass2 */

                public void run() {
                    VoiceOnlineRecog.this.recognitionListener.onRecognitionListener(str);
                }
            });
        }
    }

    public String getSubtitleContent() {
        String sb = this.srtBuilder.toString();
        StringBuilder sb2 = this.srtBuilder;
        sb2.delete(0, sb2.length());
        return sb;
    }

    public void onDestroy() {
        this.isPauseRecording = true;
    }

    public void pauseRecording() {
        this.mPauseRecordingTime = System.currentTimeMillis();
        this.isPauseRecording = true;
    }

    public void resumeRecording() {
        this.mResumeRecordingTime = System.currentTimeMillis();
        this.mNeedRemoveTime += this.mResumeRecordingTime - this.mPauseRecordingTime;
        this.isPauseRecording = false;
    }

    public void startRecording() {
        this.mStartRecordingTime = System.currentTimeMillis();
        this.isPauseRecording = false;
        this.isStopRecording = false;
        this.isDisConnect = false;
        this.mNeedRemoveTime = 0;
        if (this.srtBuilder.length() != 0) {
            StringBuilder sb = this.srtBuilder;
            sb.delete(0, sb.length());
        }
        this.srtRowNum = 1;
        if (this.mCanStartRecord) {
            try {
                initWebSocket();
                this.mPcmRecorder = new PcmRecorder(PcmRecorder.RATE16K, 40);
                this.mPcmRecorder.startRecording(this.mPcmRecordListener);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public void stopRecording() {
        this.mCanStartRecord = true;
        this.isStopRecording = true;
        Log.d(TAG, "stop: type " + this.type + " isDisConnect : " + this.isDisConnect);
        if ((this.isDisConnect || "0".equals(this.type)) && this.mPcmRecorder != null) {
            Log.d(TAG, "stop recorder onstop");
            this.mPcmRecorder.stopRecord(true);
            this.webSocket.cancel();
            this.mPcmRecorder = null;
        }
    }
}
