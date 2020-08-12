package com.xiaomi.camera.liveshot;

import android.media.MediaFormat;
import android.media.MediaMuxer;
import android.opengl.EGLContext;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import com.android.camera.CameraSettings;
import com.android.camera.effect.draw_mode.DrawExtTexAttribute;
import com.android.camera.log.Log;
import com.xiaomi.camera.liveshot.encoder.CircularAudioEncoder;
import com.xiaomi.camera.liveshot.encoder.CircularMediaEncoder;
import com.xiaomi.camera.liveshot.encoder.CircularVideoEncoder;
import com.xiaomi.camera.liveshot.util.BackgroundTaskScheduler;
import com.xiaomi.camera.liveshot.writer.AudioSampleWriter;
import com.xiaomi.camera.liveshot.writer.SampleWriter;
import com.xiaomi.camera.liveshot.writer.VideoSampleWriter;
import java.io.File;
import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CircularMediaRecorder {
    private static final int AUDIO_BIT_RATE = 64000;
    private static final int AUDIO_CHANNELS = 1;
    private static final int AUDIO_FORMAT = 2;
    private static final String AUDIO_MIME_TYPE = "audio/mp4a-latm";
    private static final int AUDIO_SAMPLE_RATE = 44100;
    private static final long CAPTURE_DURATION_IN_MICROSECOND = 2000000;
    private static final boolean DEBUG = false;
    private static final int MOVIE_FILE_FORMAT = 0;
    private static final long PRE_CAPTURE_DURATION_IN_MICROSECOND = 1000000;
    /* access modifiers changed from: private */
    public static final String TAG = "CircularMediaRecorder";
    private static final int VIDEO_BIT_RATE = 35000000;
    private static final int VIDEO_FRAME_RATE = 30;
    private static final float VIDEO_I_FRAME_INTERVAL = 0.1f;
    private final CircularAudioEncoder mCircularAudioEncoder;
    private final CircularVideoEncoder mCircularVideoEncoder;
    private int mOrientationHint = 0;
    private final BackgroundTaskScheduler mSnapshotRequestScheduler;

    private static final class SnapshotRequest extends BackgroundTaskScheduler.CancellableTask {
        private final CircularMediaEncoder.Snapshot mAudioSnapshot;
        private final int mOrientationHint;
        private final ExecutorService mSampleWriterExecutor;
        private final Object mTag;
        private final VideoClipSavingCallback mVideoClipSavingCallback;
        private final CircularMediaEncoder.Snapshot mVideoSnapshot;

        private SnapshotRequest(CircularMediaEncoder.Snapshot snapshot, CircularMediaEncoder.Snapshot snapshot2, int i, Object obj, VideoClipSavingCallback videoClipSavingCallback) {
            if (snapshot2 == null && snapshot == null) {
                throw new IllegalStateException("At least one non-null snapshot should be provided");
            }
            this.mAudioSnapshot = snapshot;
            this.mVideoSnapshot = snapshot2;
            this.mOrientationHint = i;
            this.mTag = obj;
            this.mVideoClipSavingCallback = videoClipSavingCallback;
            this.mSampleWriterExecutor = Executors.newFixedThreadPool(2);
        }

        /* JADX WARNING: Removed duplicated region for block: B:53:0x012a A[Catch:{ all -> 0x0153 }] */
        /* JADX WARNING: Removed duplicated region for block: B:55:0x0133 A[SYNTHETIC, Splitter:B:55:0x0133] */
        /* JADX WARNING: Removed duplicated region for block: B:64:0x0156 A[SYNTHETIC, Splitter:B:64:0x0156] */
        public void run() {
            MediaMuxer mediaMuxer;
            StringBuilder sb;
            String str;
            Exception e2;
            if (isCancelled()) {
                Log.d(CircularMediaRecorder.TAG, "Saving request is cancelled before executing");
                this.mSampleWriterExecutor.shutdown();
                VideoClipSavingCallback videoClipSavingCallback = this.mVideoClipSavingCallback;
                if (videoClipSavingCallback != null) {
                    videoClipSavingCallback.onVideoClipSavingCancelled(this.mTag);
                    return;
                }
                return;
            }
            SampleWriter.StatusNotifier statusNotifier = null;
            try {
                File createTempFile = File.createTempFile("microvideo_" + this.mTag.hashCode(), ".mp4");
                mediaMuxer = new MediaMuxer(createTempFile.getPath(), 0);
                try {
                    mediaMuxer.setOrientationHint(this.mOrientationHint);
                    int addTrack = this.mVideoSnapshot != null ? mediaMuxer.addTrack(this.mVideoSnapshot.format) : -1;
                    int addTrack2 = this.mAudioSnapshot != null ? mediaMuxer.addTrack(this.mAudioSnapshot.format) : -1;
                    mediaMuxer.start();
                    ArrayList<Future> arrayList = new ArrayList(2);
                    if (!(this.mVideoSnapshot == null || addTrack == -1)) {
                        statusNotifier = new SampleWriter.StatusNotifier(0L);
                        arrayList.add(this.mSampleWriterExecutor.submit(new VideoSampleWriter(mediaMuxer, this.mVideoSnapshot, addTrack, statusNotifier)));
                    }
                    if (!(this.mAudioSnapshot == null || addTrack2 == -1)) {
                        arrayList.add(this.mSampleWriterExecutor.submit(new AudioSampleWriter(mediaMuxer, this.mAudioSnapshot, addTrack2, statusNotifier)));
                    }
                    for (Future future : arrayList) {
                        if (future != null) {
                            try {
                                future.get();
                            } catch (InterruptedException e3) {
                                Log.d(CircularMediaRecorder.TAG, "Writing is interrupted and the generated video may be corrupted: " + e3);
                            }
                        }
                    }
                    mediaMuxer.stop();
                    if (this.mVideoClipSavingCallback != null) {
                        this.mVideoClipSavingCallback.onVideoClipSavingCompleted(this.mTag, createTempFile.getPath(), this.mVideoSnapshot == null ? -1 : this.mVideoSnapshot.time);
                    }
                    try {
                        mediaMuxer.release();
                    } catch (Exception unused) {
                        str = CircularMediaRecorder.TAG;
                        sb = new StringBuilder();
                    }
                } catch (Exception e4) {
                    e2 = e4;
                    try {
                        Log.d(CircularMediaRecorder.TAG, "Failed to save the videoclip as an mp4 file: " + e2);
                        if (this.mVideoClipSavingCallback != null) {
                        }
                        if (mediaMuxer != null) {
                        }
                        this.mSampleWriterExecutor.shutdown();
                    } catch (Throwable th) {
                        th = th;
                        if (mediaMuxer != null) {
                            try {
                                mediaMuxer.release();
                            } catch (Exception unused2) {
                                Log.d(CircularMediaRecorder.TAG, "Failed to release the media muxer: " + mediaMuxer);
                            }
                        }
                        this.mSampleWriterExecutor.shutdown();
                        throw th;
                    }
                }
            } catch (Exception e5) {
                mediaMuxer = null;
                e2 = e5;
                Log.d(CircularMediaRecorder.TAG, "Failed to save the videoclip as an mp4 file: " + e2);
                if (this.mVideoClipSavingCallback != null) {
                    this.mVideoClipSavingCallback.onVideoClipSavingException(this.mTag, e2);
                }
                if (mediaMuxer != null) {
                    try {
                        mediaMuxer.release();
                    } catch (Exception unused3) {
                        str = CircularMediaRecorder.TAG;
                        sb = new StringBuilder();
                    }
                }
                this.mSampleWriterExecutor.shutdown();
            } catch (Throwable th2) {
                mediaMuxer = null;
                th = th2;
                if (mediaMuxer != null) {
                }
                this.mSampleWriterExecutor.shutdown();
                throw th;
            }
            this.mSampleWriterExecutor.shutdown();
            sb.append("Failed to release the media muxer: ");
            sb.append(mediaMuxer);
            Log.d(str, sb.toString());
            this.mSampleWriterExecutor.shutdown();
        }
    }

    public interface VideoClipSavingCallback {
        public static final String EMPTY_VIDEO_PATH = "empty";

        @WorkerThread
        void onVideoClipSavingCancelled(@Nullable Object obj);

        @WorkerThread
        void onVideoClipSavingCompleted(@Nullable Object obj, String str, long j);

        @WorkerThread
        void onVideoClipSavingException(@Nullable Object obj, @NonNull Throwable th);
    }

    public CircularMediaRecorder(int i, int i2, EGLContext eGLContext, boolean z, Queue<LivePhotoResult> queue) {
        this.mCircularVideoEncoder = new CircularVideoEncoder(createVideoFormat(i, i2), eGLContext, CAPTURE_DURATION_IN_MICROSECOND, PRE_CAPTURE_DURATION_IN_MICROSECOND, queue);
        if (z) {
            this.mCircularAudioEncoder = new CircularAudioEncoder(createAudioFormat(AUDIO_SAMPLE_RATE, 1), CAPTURE_DURATION_IN_MICROSECOND, PRE_CAPTURE_DURATION_IN_MICROSECOND, null);
        } else {
            this.mCircularAudioEncoder = null;
        }
        this.mSnapshotRequestScheduler = new BackgroundTaskScheduler(Executors.newSingleThreadExecutor());
    }

    private static MediaFormat createAudioFormat(int i, int i2) {
        MediaFormat createAudioFormat = MediaFormat.createAudioFormat(AUDIO_MIME_TYPE, i, i2);
        createAudioFormat.setInteger("aac-profile", 2);
        createAudioFormat.setInteger("bitrate", AUDIO_BIT_RATE);
        createAudioFormat.setInteger("channel-count", i2);
        createAudioFormat.setInteger("pcm-encoding", 2);
        return createAudioFormat;
    }

    private static MediaFormat createVideoFormat(int i, int i2) {
        MediaFormat createVideoFormat = MediaFormat.createVideoFormat(isH265EncodingPreferred() ? "video/hevc" : "video/avc", i, i2);
        createVideoFormat.setInteger("color-format", 2130708361);
        createVideoFormat.setInteger("bitrate", VIDEO_BIT_RATE);
        createVideoFormat.setInteger("frame-rate", 30);
        createVideoFormat.setFloat("i-frame-interval", 0.1f);
        return createVideoFormat;
    }

    private static boolean isH265EncodingPreferred() {
        return CameraSettings.getVideoEncoder() == 5 && MediaCodecCapability.isH265EncodingSupported();
    }

    public void onSurfaceTextureUpdated(DrawExtTexAttribute drawExtTexAttribute) {
        CircularVideoEncoder circularVideoEncoder = this.mCircularVideoEncoder;
        if (circularVideoEncoder != null) {
            circularVideoEncoder.onSurfaceTextureUpdated(drawExtTexAttribute);
        }
    }

    public void release() {
        Log.d(TAG, "release(): E");
        this.mSnapshotRequestScheduler.shutdown();
        CircularVideoEncoder circularVideoEncoder = this.mCircularVideoEncoder;
        if (circularVideoEncoder != null) {
            circularVideoEncoder.release();
        }
        CircularAudioEncoder circularAudioEncoder = this.mCircularAudioEncoder;
        if (circularAudioEncoder != null) {
            circularAudioEncoder.release();
        }
        Log.d(TAG, "release(): X");
    }

    public void setFilterId(int i) {
        CircularVideoEncoder circularVideoEncoder = this.mCircularVideoEncoder;
        if (circularVideoEncoder != null) {
            circularVideoEncoder.setFilterId(i);
        }
    }

    public void setFpsReduction(float f2) {
        CircularVideoEncoder circularVideoEncoder = this.mCircularVideoEncoder;
        if (circularVideoEncoder != null) {
            circularVideoEncoder.setFpsReduction(f2);
        }
    }

    public void setOrientationHint(int i) {
        String str = TAG;
        Log.d(str, "setOrientationHint(): " + i);
        this.mOrientationHint = i;
    }

    public void snapshot(int i, VideoClipSavingCallback videoClipSavingCallback, Object obj, int i2) {
        CircularAudioEncoder circularAudioEncoder = this.mCircularAudioEncoder;
        CircularMediaEncoder.Snapshot snapshot = circularAudioEncoder == null ? null : circularAudioEncoder.snapshot(i2);
        CircularVideoEncoder circularVideoEncoder = this.mCircularVideoEncoder;
        CircularMediaEncoder.Snapshot snapshot2 = circularVideoEncoder == null ? null : circularVideoEncoder.snapshot(i2);
        if (i == -1) {
            i = this.mOrientationHint;
        }
        this.mSnapshotRequestScheduler.execute(new SnapshotRequest(snapshot, snapshot2, i, obj, videoClipSavingCallback));
    }

    public void start() {
        Log.d(TAG, "start(): E");
        CircularVideoEncoder circularVideoEncoder = this.mCircularVideoEncoder;
        if (circularVideoEncoder != null) {
            circularVideoEncoder.start();
        }
        CircularAudioEncoder circularAudioEncoder = this.mCircularAudioEncoder;
        if (circularAudioEncoder != null) {
            circularAudioEncoder.start();
        }
        Log.d(TAG, "start(): X");
    }

    public void stop() {
        Log.d(TAG, "stop(): E");
        this.mSnapshotRequestScheduler.abortRemainingTasks();
        CircularVideoEncoder circularVideoEncoder = this.mCircularVideoEncoder;
        if (circularVideoEncoder != null) {
            circularVideoEncoder.stop();
        }
        CircularAudioEncoder circularAudioEncoder = this.mCircularAudioEncoder;
        if (circularAudioEncoder != null) {
            circularAudioEncoder.stop();
        }
        Log.d(TAG, "stop(): X");
    }
}
