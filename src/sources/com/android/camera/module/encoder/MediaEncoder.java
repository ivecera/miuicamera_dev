package com.android.camera.module.encoder;

import android.media.MediaCodec;
import com.android.camera.log.Log;
import com.android.camera.module.loader.FunctionParseBeautyBodySlimCount;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;

public abstract class MediaEncoder implements Runnable {
    protected static final int TIMEOUT_USEC = 10000;
    private final String TAG;
    private MediaCodec.BufferInfo mBufferInfo;
    protected long mFirstFrameTime;
    private int mFrame;
    protected volatile boolean mIsCapturing;
    protected boolean mIsEOS;
    protected boolean mIsReady;
    protected final MediaEncoderListener mListener;
    protected MediaCodec mMediaCodec;
    protected boolean mMuxerStarted;
    private int mRequestDrain;
    protected volatile boolean mRequestStop;
    protected boolean mSkipFrame;
    protected float mSpeed;
    protected final Object mSync = new Object();
    protected Thread mThread;
    protected int mTrackIndex;
    protected final WeakReference<MediaMuxerWrapper> mWeakMuxer;
    private long prevOutputPTSUs = 0;

    public interface MediaEncoderListener {
        void onPrepared(MediaEncoder mediaEncoder);

        void onStopped(MediaEncoder mediaEncoder, boolean z);
    }

    public MediaEncoder(MediaMuxerWrapper mediaMuxerWrapper, MediaEncoderListener mediaEncoderListener) {
        this.mWeakMuxer = new WeakReference<>(mediaMuxerWrapper);
        mediaMuxerWrapper.addEncoder(this);
        this.mListener = mediaEncoderListener;
        this.TAG = getClass().getSimpleName();
        synchronized (this.mSync) {
            this.mBufferInfo = new MediaCodec.BufferInfo();
            this.mIsReady = false;
            this.mThread = new Thread(this, getClass().getSimpleName());
            this.mThread.start();
            if (!this.mIsReady) {
                try {
                    this.mSync.wait();
                } catch (InterruptedException e2) {
                    String str = this.TAG;
                    Log.e(str, "Exception occurred: " + e2.getMessage(), e2);
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void drain() {
        boolean z;
        boolean z2;
        MediaMuxerWrapper mediaMuxerWrapper = this.mWeakMuxer.get();
        if (mediaMuxerWrapper == null) {
            Log.w(this.TAG, "muxer is unexpectedly null");
            return;
        }
        MediaCodec mediaCodec = this.mMediaCodec;
        if (mediaCodec != null) {
            ByteBuffer[] outputBuffers = mediaCodec.getOutputBuffers();
            int i = 0;
            while (this.mIsCapturing) {
                if (this.mSkipFrame) {
                    this.mSkipFrame = System.currentTimeMillis() < this.mFirstFrameTime;
                }
                try {
                    int dequeueOutputBuffer = this.mMediaCodec.dequeueOutputBuffer(this.mBufferInfo, FunctionParseBeautyBodySlimCount.TIP_INTERVAL_TIME);
                    if (dequeueOutputBuffer == -1) {
                        if (!this.mIsEOS && (i = i + 1) > 5) {
                            return;
                        }
                    } else if (dequeueOutputBuffer == -3) {
                        Log.d(this.TAG, "INFO_OUTPUT_BUFFERS_CHANGED");
                        outputBuffers = this.mMediaCodec.getOutputBuffers();
                    } else if (dequeueOutputBuffer == -2) {
                        Log.d(this.TAG, "INFO_OUTPUT_FORMAT_CHANGED");
                        if (!this.mMuxerStarted) {
                            this.mTrackIndex = mediaMuxerWrapper.addTrack(this.mMediaCodec.getOutputFormat());
                            synchronized (this.mSync) {
                                z2 = this.mRequestStop;
                            }
                            if (!z2) {
                                this.mMuxerStarted = true;
                                if (!mediaMuxerWrapper.start()) {
                                    synchronized (mediaMuxerWrapper) {
                                        while (!mediaMuxerWrapper.isStarted()) {
                                            try {
                                                mediaMuxerWrapper.wait(100);
                                                if (this.mRequestStop) {
                                                    return;
                                                }
                                            } catch (InterruptedException unused) {
                                                return;
                                            }
                                        }
                                    }
                                } else {
                                    continue;
                                }
                            } else {
                                continue;
                            }
                        } else {
                            throw new RuntimeException("format changed twice");
                        }
                    } else if (dequeueOutputBuffer < 0) {
                        Log.w(this.TAG, "drain: unexpected status: " + dequeueOutputBuffer);
                    } else {
                        ByteBuffer byteBuffer = outputBuffers[dequeueOutputBuffer];
                        if (byteBuffer != null) {
                            if ((this.mBufferInfo.flags & 2) != 0) {
                                Log.d(this.TAG, "drain: BUFFER_FLAG_CODEC_CONFIG");
                                this.mBufferInfo.size = 0;
                            }
                            if (this.mBufferInfo.size != 0) {
                                if (this.mMuxerStarted) {
                                    synchronized (this.mSync) {
                                        z = this.mRequestStop;
                                    }
                                    if (!z && !this.mSkipFrame) {
                                        long pTSUs = getPTSUs();
                                        MediaCodec.BufferInfo bufferInfo = this.mBufferInfo;
                                        bufferInfo.presentationTimeUs = (long) (((double) pTSUs) * ((double) this.mSpeed));
                                        mediaMuxerWrapper.writeSampleData(this.mTrackIndex, byteBuffer, bufferInfo);
                                        this.mFrame++;
                                        this.prevOutputPTSUs = pTSUs;
                                    }
                                }
                                i = 0;
                            }
                            this.mMediaCodec.releaseOutputBuffer(dequeueOutputBuffer, false);
                            if ((this.mBufferInfo.flags & 4) != 0) {
                                this.mIsCapturing = false;
                                return;
                            }
                        } else {
                            throw new RuntimeException("encoderOutputBuffer " + dequeueOutputBuffer + " was null");
                        }
                    }
                } catch (IllegalStateException e2) {
                    Log.e(this.TAG, "dequeueOutputBuffer() failed:" + e2.getMessage());
                    return;
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void encode(ByteBuffer byteBuffer, int i, long j) {
        if (this.mIsCapturing) {
            ByteBuffer[] inputBuffers = this.mMediaCodec.getInputBuffers();
            while (this.mIsCapturing) {
                int dequeueInputBuffer = this.mMediaCodec.dequeueInputBuffer(FunctionParseBeautyBodySlimCount.TIP_INTERVAL_TIME);
                if (dequeueInputBuffer >= 0) {
                    ByteBuffer byteBuffer2 = inputBuffers[dequeueInputBuffer];
                    byteBuffer2.clear();
                    if (byteBuffer != null) {
                        byteBuffer2.put(byteBuffer);
                    }
                    if (i <= 0) {
                        this.mIsEOS = true;
                        Log.d(this.TAG, "send BUFFER_FLAG_END_OF_STREAM");
                        this.mMediaCodec.queueInputBuffer(dequeueInputBuffer, 0, 0, j, 4);
                        return;
                    }
                    this.mMediaCodec.queueInputBuffer(dequeueInputBuffer, 0, i, j, 0);
                    return;
                }
            }
        }
    }

    public boolean frameAvailableSoon() {
        synchronized (this.mSync) {
            if (this.mIsCapturing) {
                if (!this.mRequestStop) {
                    this.mRequestDrain++;
                    this.mSync.notifyAll();
                    return true;
                }
            }
            Log.w(this.TAG, "frameAvailableSoon: requestStop=" + this.mRequestStop);
            return false;
        }
    }

    /* access modifiers changed from: protected */
    public long getPTSUs() {
        long nanoTime = System.nanoTime() / 1000;
        long j = this.prevOutputPTSUs;
        return nanoTime < j ? nanoTime + (j - nanoTime) : nanoTime;
    }

    /* access modifiers changed from: package-private */
    public void join() {
        Thread thread = this.mThread;
        if (thread != null) {
            try {
                thread.join(FunctionParseBeautyBodySlimCount.TIP_TIME);
                this.mThread = null;
            } catch (InterruptedException e2) {
                Log.e(this.TAG, "join interrupted", e2);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public abstract void prepare() throws IOException;

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x0041  */
    public void release() {
        boolean z;
        MediaEncoderListener mediaEncoderListener;
        MediaCodec mediaCodec = this.mMediaCodec;
        if (mediaCodec != null) {
            try {
                mediaCodec.stop();
                this.mMediaCodec.release();
                this.mMediaCodec = null;
            } catch (Exception e2) {
                Log.e(this.TAG, "failed releasing MediaCodec", e2);
            }
        }
        if (this.mMuxerStarted) {
            WeakReference<MediaMuxerWrapper> weakReference = this.mWeakMuxer;
            MediaMuxerWrapper mediaMuxerWrapper = weakReference != null ? weakReference.get() : null;
            if (mediaMuxerWrapper != null) {
                try {
                    z = mediaMuxerWrapper.stop();
                } catch (Exception e3) {
                    Log.e(this.TAG, "failed stopping muxer", e3);
                    z = true;
                }
                this.mIsCapturing = false;
                mediaEncoderListener = this.mListener;
                if (mediaEncoderListener != null) {
                    mediaEncoderListener.onStopped(this, z);
                }
                this.mBufferInfo = null;
            }
        }
        z = false;
        this.mIsCapturing = false;
        mediaEncoderListener = this.mListener;
        if (mediaEncoderListener != null) {
        }
        this.mBufferInfo = null;
    }

    public void run() {
        boolean z;
        boolean z2;
        synchronized (this.mSync) {
            this.mRequestStop = false;
            this.mRequestDrain = 0;
            this.mIsReady = true;
            this.mSync.notify();
        }
        while (true) {
            synchronized (this.mSync) {
                z = this.mRequestStop;
                z2 = this.mRequestDrain > 0;
                if (z2) {
                    this.mRequestDrain--;
                }
            }
            if (z) {
                drain();
                signalEndOfInputStream();
                drain();
                release();
                break;
            } else if (z2) {
                drain();
            } else {
                synchronized (this.mSync) {
                    if (!this.mRequestStop) {
                        try {
                            this.mSync.wait();
                        } catch (InterruptedException e2) {
                            Log.e(this.TAG, "Exception occurred: " + e2.getMessage());
                        }
                    }
                }
            }
        }
        Log.d(this.TAG, "encoder thread exiting");
        synchronized (this.mSync) {
            this.mRequestStop = true;
            this.mIsCapturing = false;
        }
    }

    public void setRecordSpeed(float f2) {
        this.mSpeed = f2;
    }

    /* access modifiers changed from: protected */
    public void signalEndOfInputStream() {
        Log.d(this.TAG, "signalEndOfInputStream");
        encode(null, 0, getPTSUs());
    }

    /* access modifiers changed from: package-private */
    public boolean startRecording(long j) {
        Log.d(this.TAG, "startRecording");
        synchronized (this.mSync) {
            this.mFirstFrameTime = System.currentTimeMillis() + j;
            this.mSkipFrame = true;
            this.mFrame = 0;
            this.mIsCapturing = true;
            this.mRequestStop = false;
            this.mSync.notifyAll();
        }
        return true;
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0021, code lost:
        return;
     */
    public void stopRecording() {
        Log.d(this.TAG, "stopRecording");
        synchronized (this.mSync) {
            if (this.mIsCapturing) {
                if (!this.mRequestStop) {
                    this.mSkipFrame = false;
                    this.mRequestStop = true;
                    this.mSync.notifyAll();
                }
            }
        }
    }
}
