package com.miui.extravideo.common;

import android.media.MediaCodec;
import android.media.MediaCrypto;
import android.media.MediaFormat;
import android.media.MediaMuxer;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.Surface;
import com.miui.extravideo.interpolation.EncodeBufferHolder;
import java.lang.Thread;
import java.nio.ByteBuffer;

public class MediaEncoderAsync {
    private static final int FRAME_RATE = 30;
    private static final float I_FRAME_INTERVAL = 1.0f;
    private static final String MIME_TYPE = "video/avc";
    private static final String TAG = "MediaEncoderAsync";
    private final int mDegree;
    /* access modifiers changed from: private */
    public EncodeBufferHolder mEncodeBufferHolder;
    /* access modifiers changed from: private */
    public MediaCodec mEncoder;
    private Handler mHandler;
    private final int mHeight;
    private Exception mInitException;
    /* access modifiers changed from: private */
    public EncodeUpdateListener mListener;
    /* access modifiers changed from: private */
    public int mTrackIndex;
    private final int mWidth;
    /* access modifiers changed from: private */
    public MediaMuxer mediaMuxer;

    private class CustomCallback extends MediaCodec.Callback {
        private CustomCallback() {
        }

        public void onError(@NonNull MediaCodec mediaCodec, @NonNull MediaCodec.CodecException codecException) {
            Log.d(MediaEncoderAsync.TAG, "onError", codecException);
            try {
                if (MediaEncoderAsync.this.mListener != null) {
                    MediaEncoderAsync.this.mListener.onError();
                }
            } catch (Exception e2) {
                Log.d(MediaEncoderAsync.TAG, "onError exception", e2);
            }
        }

        public void onInputBufferAvailable(@NonNull MediaCodec mediaCodec, int i) {
            try {
                if (MediaEncoderAsync.this.mListener != null) {
                    MediaEncoderAsync.this.mListener.onInputBufferAvailable(MediaEncoderAsync.this.mEncodeBufferHolder);
                }
                Log.d(MediaEncoderAsync.TAG, "onInputBufferAvailable");
                if (MediaEncoderAsync.this.mEncodeBufferHolder.flag == 4) {
                    MediaEncoderAsync.this.mEncoder.queueInputBuffer(i, 0, 0, 0, 4);
                } else if (MediaEncoderAsync.this.mEncodeBufferHolder.data != null) {
                    ByteBuffer inputBuffer = MediaEncoderAsync.this.mEncoder.getInputBuffer(i);
                    inputBuffer.clear();
                    inputBuffer.put(MediaEncoderAsync.this.mEncodeBufferHolder.data);
                    MediaEncoderAsync.this.mEncoder.queueInputBuffer(i, 0, MediaEncoderAsync.this.mEncodeBufferHolder.data.length, MediaEncoderAsync.this.mEncodeBufferHolder.presentationTimeUs, MediaEncoderAsync.this.mEncodeBufferHolder.flag);
                } else {
                    MediaEncoderAsync.this.mEncoder.queueInputBuffer(i, 0, 0, 0, 0);
                }
            } catch (Exception e2) {
                Log.d(MediaEncoderAsync.TAG, "onInputBufferAvailable exception", e2);
            }
        }

        public void onOutputBufferAvailable(@NonNull MediaCodec mediaCodec, int i, @NonNull MediaCodec.BufferInfo bufferInfo) {
            try {
                ByteBuffer outputBuffer = MediaEncoderAsync.this.mEncoder.getOutputBuffer(i);
                if (bufferInfo.size != 0) {
                    outputBuffer.position(bufferInfo.offset);
                    outputBuffer.limit(bufferInfo.offset + bufferInfo.size);
                    Log.d(MediaEncoderAsync.TAG, "BufferInfo: " + bufferInfo.offset + "," + bufferInfo.size + "," + bufferInfo.presentationTimeUs);
                    try {
                        MediaEncoderAsync.this.mediaMuxer.writeSampleData(MediaEncoderAsync.this.mTrackIndex, outputBuffer, bufferInfo);
                    } catch (Exception e2) {
                        Log.i(MediaEncoderAsync.TAG, "Too many frames", e2);
                    }
                    MediaEncoderAsync.this.mEncoder.releaseOutputBuffer(i, false);
                }
                if ((bufferInfo.flags & 4) != 0) {
                    Log.i(MediaEncoderAsync.TAG, "end of stream reached");
                    if (MediaEncoderAsync.this.mListener != null) {
                        MediaEncoderAsync.this.mListener.onEncodeEnd(true);
                    }
                }
            } catch (Exception e3) {
                Log.d(MediaEncoderAsync.TAG, "onOutputBufferAvailable exception", e3);
            }
        }

        public void onOutputFormatChanged(@NonNull MediaCodec mediaCodec, @NonNull MediaFormat mediaFormat) {
            MediaFormat outputFormat = MediaEncoderAsync.this.mEncoder.getOutputFormat();
            MediaEncoderAsync mediaEncoderAsync = MediaEncoderAsync.this;
            int unused = mediaEncoderAsync.mTrackIndex = mediaEncoderAsync.mediaMuxer.addTrack(outputFormat);
            MediaEncoderAsync.this.mediaMuxer.start();
        }
    }

    public interface EncodeUpdateListener {
        void onEncodeEnd(boolean z);

        void onError();

        void onInputBufferAvailable(EncodeBufferHolder encodeBufferHolder);
    }

    public MediaEncoderAsync(int i, int i2, int i3, String str, String str2) {
        this(i, i2, i3, str, str2, null);
    }

    public MediaEncoderAsync(int i, int i2, int i3, String str, String str2, Handler handler) {
        this.mTrackIndex = -1;
        this.mEncodeBufferHolder = new EncodeBufferHolder();
        this.mHandler = handler;
        this.mWidth = i;
        this.mHeight = i2;
        this.mDegree = i3;
        str = TextUtils.isEmpty(str) ? MIME_TYPE : str;
        MediaFormat createVideoFormat = MediaFormat.createVideoFormat(str, this.mWidth, this.mHeight);
        createVideoFormat.setInteger("color-format", 2135033992);
        createVideoFormat.setInteger("frame-rate", 30);
        createVideoFormat.setInteger("bitrate", this.mWidth * this.mHeight * 10);
        createVideoFormat.setFloat("i-frame-interval", 1.0f);
        try {
            this.mEncoder = MediaCodec.createEncoderByType(str);
            this.mEncoder.setCallback(new CustomCallback(), handler);
            this.mEncoder.configure(createVideoFormat, (Surface) null, (MediaCrypto) null, 1);
            this.mediaMuxer = new MediaMuxer(str2, 0);
            this.mediaMuxer.setOrientationHint(this.mDegree);
        } catch (Exception e2) {
            this.mInitException = e2;
        }
    }

    public int getFrameRate() {
        return 30;
    }

    public void release() {
        MediaCodec mediaCodec = this.mEncoder;
        if (mediaCodec != null) {
            mediaCodec.stop();
            this.mEncoder.release();
            this.mEncoder = null;
        }
        MediaMuxer mediaMuxer2 = this.mediaMuxer;
        if (mediaMuxer2 != null) {
            try {
                mediaMuxer2.stop();
            } catch (Exception unused) {
            }
            this.mediaMuxer.release();
            this.mediaMuxer = null;
        }
    }

    public void setListener(EncodeUpdateListener encodeUpdateListener) {
        this.mListener = encodeUpdateListener;
    }

    public void start() throws Exception {
        Exception exc = this.mInitException;
        if (exc == null) {
            this.mEncoder.start();
            return;
        }
        throw exc;
    }

    public void stop() {
        this.mEncoder.stop();
        if (this.mListener != null) {
            Handler handler = this.mHandler;
            if (handler == null || handler.getLooper() == Looper.myLooper()) {
                this.mListener.onEncodeEnd(false);
                return;
            }
            this.mHandler.post(new Runnable() {
                /* class com.miui.extravideo.common.MediaEncoderAsync.AnonymousClass1 */

                public void run() {
                    if (MediaEncoderAsync.this.mListener != null) {
                        MediaEncoderAsync.this.mListener.onEncodeEnd(false);
                    }
                }
            });
            Thread thread = this.mHandler.getLooper().getThread();
            if (thread.getState() == Thread.State.WAITING) {
                thread.interrupt();
            }
        }
    }
}
