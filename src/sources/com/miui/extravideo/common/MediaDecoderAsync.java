package com.miui.extravideo.common;

import android.media.MediaCodec;
import android.media.MediaCrypto;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Surface;
import java.nio.ByteBuffer;

public class MediaDecoderAsync {
    private static final String TAG = "MediaDecoderAsync";
    /* access modifiers changed from: private */
    public int mDecodeFrameIndex;
    private MediaCodec mDecoder;
    private Handler mHandler;
    private Exception mInitException;
    /* access modifiers changed from: private */
    public DecodeUpdateListener mListener;
    /* access modifiers changed from: private */
    public final MediaExtractor mMediaExtractor;
    private final MediaParamsHolder mMediaParamsHolder;
    /* access modifiers changed from: private */
    public int mSkipFrameTimes;
    private final String mTargetFile;

    private class CustomCallback extends MediaCodec.Callback {
        private CustomCallback() {
        }

        public void onError(@NonNull MediaCodec mediaCodec, @NonNull MediaCodec.CodecException codecException) {
            Log.d(MediaDecoderAsync.TAG, "onError", codecException);
            if (MediaDecoderAsync.this.mListener != null) {
                MediaDecoderAsync.this.mListener.onError();
            }
        }

        public void onInputBufferAvailable(@NonNull MediaCodec mediaCodec, int i) {
            try {
                int readSampleData = MediaDecoderAsync.this.mMediaExtractor.readSampleData(mediaCodec.getInputBuffer(i), 0);
                long sampleTime = MediaDecoderAsync.this.mMediaExtractor.getSampleTime();
                MediaDecoderAsync.access$208(MediaDecoderAsync.this);
                Log.d(MediaDecoderAsync.TAG, String.format("input  decode index : %d time : %d simple size : %d", Integer.valueOf(MediaDecoderAsync.this.mDecodeFrameIndex), Long.valueOf(sampleTime), Integer.valueOf(readSampleData)));
                if (MediaDecoderAsync.this.mListener != null) {
                    MediaDecoderAsync.this.mListener.onFrameDecodeBegin(MediaDecoderAsync.this.mDecodeFrameIndex, sampleTime);
                }
                if (readSampleData < 0) {
                    mediaCodec.queueInputBuffer(i, 0, 0, 0, 4);
                    return;
                }
                mediaCodec.queueInputBuffer(i, 0, readSampleData, sampleTime, 0);
                for (int i2 = 0; i2 < MediaDecoderAsync.this.mSkipFrameTimes; i2++) {
                    MediaDecoderAsync.this.mMediaExtractor.advance();
                }
            } catch (Exception e2) {
                Log.d(MediaDecoderAsync.TAG, "onInputBufferAvailable exception", e2);
            }
        }

        public void onOutputBufferAvailable(@NonNull MediaCodec mediaCodec, int i, @NonNull MediaCodec.BufferInfo bufferInfo) {
            try {
                boolean z = (bufferInfo.flags & 4) != 0;
                ByteBuffer outputBuffer = mediaCodec.getOutputBuffer(i);
                if (!z) {
                    if (MediaDecoderAsync.this.mListener != null) {
                        MediaDecoderAsync.this.mListener.onDecodeBuffer(outputBuffer, bufferInfo);
                        Log.d(MediaDecoderAsync.TAG, String.format("output decode presentation time : %d", Long.valueOf(bufferInfo.presentationTimeUs)));
                        outputBuffer.clear();
                        mediaCodec.releaseOutputBuffer(i, false);
                    }
                } else if (MediaDecoderAsync.this.mListener != null) {
                    MediaDecoderAsync.this.mListener.onDecodeStop(true);
                    Log.d(MediaDecoderAsync.TAG, "OutputBuffer BUFFER_FLAG_END_OF_STREAM");
                }
            } catch (Exception e2) {
                Log.d(MediaDecoderAsync.TAG, "onOutputBufferAvailable exception", e2);
            }
        }

        public void onOutputFormatChanged(@NonNull MediaCodec mediaCodec, @NonNull MediaFormat mediaFormat) {
            Log.d(MediaDecoderAsync.TAG, String.format("onOutputFormatChanged : %s", mediaFormat));
            if (MediaDecoderAsync.this.mListener != null) {
                MediaDecoderAsync.this.mListener.onOutputFormatChange(mediaFormat);
            }
        }
    }

    public interface DecodeUpdateListener {
        void onDecodeBuffer(ByteBuffer byteBuffer, MediaCodec.BufferInfo bufferInfo);

        void onDecodeStop(boolean z);

        void onError();

        void onFrameDecodeBegin(int i, long j);

        void onOutputFormatChange(MediaFormat mediaFormat);
    }

    public MediaDecoderAsync(String str) {
        this(str, null);
    }

    public MediaDecoderAsync(String str, Handler handler) {
        this.mSkipFrameTimes = 1;
        this.mDecodeFrameIndex = 0;
        this.mHandler = handler;
        this.mTargetFile = str;
        this.mMediaParamsHolder = new MediaParamsHolder();
        this.mMediaExtractor = new MediaExtractor();
        try {
            this.mMediaExtractor.setDataSource(this.mTargetFile);
            for (int i = 0; i < this.mMediaExtractor.getTrackCount(); i++) {
                MediaFormat trackFormat = this.mMediaExtractor.getTrackFormat(i);
                String string = trackFormat.getString("mime");
                if (string.startsWith("video/")) {
                    this.mMediaParamsHolder.videoWidth = trackFormat.getInteger("width");
                    this.mMediaParamsHolder.videoHeight = trackFormat.getInteger("height");
                    this.mMediaParamsHolder.videoDegree = getInteger(trackFormat, "rotation-degrees", 0);
                    this.mMediaParamsHolder.mimeType = string;
                    this.mMediaExtractor.selectTrack(i);
                    this.mDecoder = MediaCodec.createDecoderByType(string);
                    this.mDecoder.setCallback(new CustomCallback(), handler);
                    this.mDecoder.configure(trackFormat, (Surface) null, (MediaCrypto) null, 0);
                    return;
                }
            }
        } catch (Exception e2) {
            this.mInitException = e2;
        }
    }

    static /* synthetic */ int access$208(MediaDecoderAsync mediaDecoderAsync) {
        int i = mediaDecoderAsync.mDecodeFrameIndex;
        mediaDecoderAsync.mDecodeFrameIndex = i + 1;
        return i;
    }

    private static final int getInteger(MediaFormat mediaFormat, String str, int i) {
        try {
            return mediaFormat.getInteger(str);
        } catch (ClassCastException | NullPointerException unused) {
            return i;
        }
    }

    public MediaParamsHolder getMediaParamsHolder() {
        return this.mMediaParamsHolder;
    }

    public void release() {
        MediaCodec mediaCodec = this.mDecoder;
        if (mediaCodec != null) {
            mediaCodec.stop();
            this.mDecoder.release();
            this.mDecoder = null;
        }
        MediaExtractor mediaExtractor = this.mMediaExtractor;
        if (mediaExtractor != null) {
            mediaExtractor.release();
        }
    }

    public void setListener(DecodeUpdateListener decodeUpdateListener) {
        this.mListener = decodeUpdateListener;
    }

    public void setSkipFrameTimes(int i) {
        this.mSkipFrameTimes = i;
    }

    public void start() throws Exception {
        Exception exc = this.mInitException;
        if (exc == null) {
            this.mDecoder.start();
            return;
        }
        throw exc;
    }

    public void stop() {
        this.mDecoder.stop();
        if (this.mListener != null) {
            Handler handler = this.mHandler;
            if (handler == null || handler.getLooper() == Looper.myLooper()) {
                this.mListener.onDecodeStop(false);
            } else {
                this.mHandler.post(new Runnable() {
                    /* class com.miui.extravideo.common.MediaDecoderAsync.AnonymousClass1 */

                    public void run() {
                        if (MediaDecoderAsync.this.mListener != null) {
                            MediaDecoderAsync.this.mListener.onDecodeStop(false);
                        }
                    }
                });
            }
        }
    }
}
