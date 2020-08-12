package com.xiaomi.camera.liveshot.encoder;

import android.media.MediaCodec;
import android.media.MediaCrypto;
import android.media.MediaFormat;
import android.opengl.EGLContext;
import android.os.Handler;
import android.view.Surface;
import com.android.camera.effect.draw_mode.DrawExtTexAttribute;
import com.android.camera.log.Log;
import com.xiaomi.camera.liveshot.LivePhotoResult;
import com.xiaomi.camera.liveshot.encoder.CircularMediaEncoder;
import com.xiaomi.camera.liveshot.gles.RenderThread;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

public class CircularVideoEncoder extends CircularMediaEncoder {
    private static final boolean DEBUG_FPS = true;
    private static final String TAG = "CircularVideoEncoder";
    protected long mFirstPresentationTimeUs;
    private int mFpsOutputInterval = 500;
    private long mFrameStartTimestampNs = 0;
    private int mFramesRendered = 0;
    private Surface mInputSurface;
    protected long mLastPresentationTimeUs;
    private long mMinFrameRenderPeriodNs;
    private long mNextFrameTimestampNs;
    private final int mPreviewHeight;
    private final int mPreviewWidth;
    private RenderThread mRenderThread;
    private EGLContext mSharedEGLContext;

    public CircularVideoEncoder(MediaFormat mediaFormat, EGLContext eGLContext, long j, long j2, Queue<LivePhotoResult> queue) {
        super(mediaFormat, j, j2, queue);
        float f2 = ((CircularMediaEncoder) this).mDesiredMediaFormat.getFloat("i-frame-interval");
        long millis = TimeUnit.MICROSECONDS.toMillis(((CircularMediaEncoder) this).mBufferingDurationUs);
        float f3 = f2 * 1000.0f * 2.0f;
        if (((float) millis) < f3) {
            throw new IllegalArgumentException("Requested time span is too short: " + millis + " vs. " + f3);
        } else if (eGLContext != null) {
            this.mSharedEGLContext = eGLContext;
            int integer = ((CircularMediaEncoder) this).mDesiredMediaFormat.getInteger("width");
            int integer2 = ((CircularMediaEncoder) this).mDesiredMediaFormat.getInteger("height");
            this.mPreviewWidth = Math.min(integer, integer2);
            this.mPreviewHeight = Math.max(integer, integer2);
            try {
                ((CircularMediaEncoder) this).mMediaCodec = MediaCodec.createEncoderByType(((CircularMediaEncoder) this).mDesiredMediaFormat.getString("mime"));
                ((CircularMediaEncoder) this).mIsInitialized = true;
            } catch (IOException e2) {
                throw new IllegalStateException("Failed to configure MediaCodec: " + e2);
            }
        } else {
            throw new IllegalArgumentException("The shared EGLContext must not be null");
        }
    }

    @Override // com.xiaomi.camera.liveshot.encoder.CircularMediaEncoder
    public void doRelease() {
        if (((CircularMediaEncoder) this).mIsInitialized) {
            super.doRelease();
            ((CircularMediaEncoder) this).mIsInitialized = false;
        }
    }

    @Override // com.xiaomi.camera.liveshot.encoder.CircularMediaEncoder
    public void doStart() {
        Log.d(TAG, "start(): E");
        if (!((CircularMediaEncoder) this).mIsInitialized) {
            Log.d(TAG, "start(): not initialized yet");
        } else if (((CircularMediaEncoder) this).mIsBuffering) {
            Log.d(TAG, "start(): encoder is already running");
        } else {
            ((CircularMediaEncoder) this).mCyclicBuffer.clear();
            ((CircularMediaEncoder) this).mMediaCodec.configure(((CircularMediaEncoder) this).mDesiredMediaFormat, (Surface) null, (MediaCrypto) null, 1);
            this.mInputSurface = ((CircularMediaEncoder) this).mMediaCodec.createInputSurface();
            this.mRenderThread = new RenderThread(TAG, this.mPreviewWidth, this.mPreviewHeight, this.mSharedEGLContext, this.mInputSurface, true);
            this.mRenderThread.start();
            this.mRenderThread.waitUntilReady();
            ((CircularMediaEncoder) this).mMediaCodec.setCallback(this, new Handler(((CircularMediaEncoder) this).mEncodingThread.getLooper()));
            ((CircularMediaEncoder) this).mCurrentPresentationTimeUs = 0;
            this.mFirstPresentationTimeUs = 0;
            this.mLastPresentationTimeUs = 0;
            super.doStart();
            ((CircularMediaEncoder) this).mIsBuffering = true;
            Log.d(TAG, "start(): X");
        }
    }

    @Override // com.xiaomi.camera.liveshot.encoder.CircularMediaEncoder
    public synchronized void doStop() {
        Log.d(TAG, "stop(): E");
        if (((CircularMediaEncoder) this).mIsInitialized) {
            if (((CircularMediaEncoder) this).mIsBuffering) {
                ((CircularMediaEncoder) this).mIsBuffering = false;
                if (this.mRenderThread != null) {
                    this.mRenderThread.quit();
                    this.mRenderThread = null;
                }
                if (this.mInputSurface != null) {
                    this.mInputSurface.release();
                    this.mInputSurface = null;
                }
                super.doStop();
                Log.d(TAG, "clear pending snapshot requests: E");
                ArrayList<CircularMediaEncoder.Snapshot> arrayList = new ArrayList();
                synchronized (((CircularMediaEncoder) this).mSnapshots) {
                    arrayList.addAll(((CircularMediaEncoder) this).mSnapshots);
                    ((CircularMediaEncoder) this).mSnapshots.clear();
                }
                String str = TAG;
                Log.d(str, "cleared " + arrayList.size() + " snapshot requests.");
                for (CircularMediaEncoder.Snapshot snapshot : arrayList) {
                    try {
                        snapshot.putEos();
                    } catch (InterruptedException e2) {
                        String str2 = TAG;
                        Log.d(str2, "Failed to putEos: " + e2);
                    }
                }
                Log.d(TAG, "clear pending snapshot requests: X");
                Log.d(TAG, "stop(): X");
            }
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.xiaomi.camera.liveshot.encoder.CircularMediaEncoder
    public long getNextPresentationTimeUs(long j) {
        long j2 = this.mFirstPresentationTimeUs;
        if (j2 == 0) {
            this.mFirstPresentationTimeUs = j;
            return 0;
        }
        long j3 = j - j2;
        long j4 = this.mLastPresentationTimeUs;
        if (j4 >= j3) {
            this.mLastPresentationTimeUs = j4 + 9643;
            return this.mLastPresentationTimeUs;
        }
        this.mLastPresentationTimeUs = j3;
        return j3;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0083, code lost:
        return;
     */
    public synchronized void onSurfaceTextureUpdated(DrawExtTexAttribute drawExtTexAttribute) {
        if (((CircularMediaEncoder) this).mIsInitialized) {
            if (((CircularMediaEncoder) this).mIsBuffering) {
                if (this.mMinFrameRenderPeriodNs > 0) {
                    long nanoTime = System.nanoTime();
                    if (nanoTime < this.mNextFrameTimestampNs) {
                        Log.d(TAG, "Dropping frame - fps reduction is active.");
                        return;
                    } else {
                        this.mNextFrameTimestampNs += this.mMinFrameRenderPeriodNs;
                        this.mNextFrameTimestampNs = Math.max(this.mNextFrameTimestampNs, nanoTime);
                    }
                }
                this.mRenderThread.draw(drawExtTexAttribute);
                long millis = TimeUnit.NANOSECONDS.toMillis(System.nanoTime());
                if (this.mFrameStartTimestampNs > 0) {
                    long j = millis - this.mFrameStartTimestampNs;
                    this.mFramesRendered++;
                    if (j > ((long) this.mFpsOutputInterval)) {
                        double d2 = ((double) (this.mFramesRendered * 1000)) / ((double) j);
                        Log.d(TAG, "onSurfaceTextureUpdated(): " + d2);
                        this.mFrameStartTimestampNs = millis;
                        this.mFramesRendered = 0;
                    }
                } else {
                    this.mFrameStartTimestampNs = millis;
                }
            }
        }
    }

    public synchronized void setFilterId(int i) {
        if (((CircularMediaEncoder) this).mIsInitialized) {
            if (((CircularMediaEncoder) this).mIsBuffering) {
                this.mRenderThread.setFilterId(i);
            }
        }
    }

    public void setFpsReduction(float f2) {
        String str = TAG;
        Log.d(str, "setFpsReduction: " + f2);
        if (f2 <= 0.0f) {
            this.mMinFrameRenderPeriodNs = Long.MAX_VALUE;
        } else {
            this.mMinFrameRenderPeriodNs = (long) (((float) TimeUnit.SECONDS.toNanos(1)) / f2);
        }
    }
}
