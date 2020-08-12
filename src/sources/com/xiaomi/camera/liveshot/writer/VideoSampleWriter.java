package com.xiaomi.camera.liveshot.writer;

import android.media.MediaCodec;
import android.media.MediaMuxer;
import com.android.camera.Util;
import com.android.camera.log.Log;
import com.xiaomi.camera.liveshot.LivePhotoResult;
import com.xiaomi.camera.liveshot.encoder.CircularMediaEncoder;
import com.xiaomi.camera.liveshot.writer.SampleWriter;
import java.nio.ByteBuffer;

public class VideoSampleWriter extends SampleWriter {
    private static final boolean DEBUG = true;
    private static final long MIN_DURATION = 500000;
    private static final String TAG = "VideoSampleWriter";
    private final MediaMuxer mMediaMuxer;
    private final SampleWriter.StatusNotifier<Long> mVideoFirstKeyFrameArrivedNotifier;
    private final CircularMediaEncoder.Snapshot mVideoSnapshot;
    private final int mVideoTrackId;

    public VideoSampleWriter(MediaMuxer mediaMuxer, CircularMediaEncoder.Snapshot snapshot, int i, SampleWriter.StatusNotifier<Long> statusNotifier) {
        this.mMediaMuxer = mediaMuxer;
        this.mVideoSnapshot = snapshot;
        this.mVideoTrackId = i;
        this.mVideoFirstKeyFrameArrivedNotifier = statusNotifier;
    }

    /* access modifiers changed from: protected */
    @Override // com.xiaomi.camera.liveshot.writer.SampleWriter
    public void writeSample() {
        boolean z;
        boolean z2;
        long j;
        long j2;
        long j3;
        boolean z3;
        boolean z4;
        Log.d(TAG, "writeVideoSamples: E");
        CircularMediaEncoder.Snapshot snapshot = this.mVideoSnapshot;
        long j4 = snapshot.head;
        long j5 = snapshot.tail;
        long j6 = snapshot.time;
        int i = snapshot.filterId;
        Log.d(TAG, "writeVideoSamples: head timestamp: " + this.mVideoSnapshot.head + ":" + j4);
        Log.d(TAG, "writeVideoSamples: snap timestamp: " + this.mVideoSnapshot.time + ":" + j6);
        Log.d(TAG, "writeVideoSamples: tail timestamp: " + this.mVideoSnapshot.tail + ":" + j5);
        Log.d(TAG, "writeVideoSamples: curr filterId: " + this.mVideoSnapshot.filterId + ":" + i);
        long j7 = -1;
        boolean z5 = false;
        long j8 = 0;
        boolean z6 = false;
        boolean z7 = false;
        loop0:
        while (true) {
            boolean z8 = false;
            while (true) {
                if (z2) {
                    break loop0;
                }
                Log.d(TAG, "writeVideoSamples: take: E");
                try {
                    CircularMediaEncoder.Sample take = this.mVideoSnapshot.samples.take();
                    Log.d(TAG, "writeVideoSamples: take: X");
                    if (take == null) {
                        Log.e(TAG, "sample null return");
                        break loop0;
                    }
                    ByteBuffer byteBuffer = take.data;
                    MediaCodec.BufferInfo bufferInfo = take.info;
                    LivePhotoResult livePhotoResult = take.livePhotoResult;
                    z3 = z2;
                    j3 = j;
                    Log.d(TAG, "writeVideoSamples: livePhotoResult " + livePhotoResult);
                    if (byteBuffer.limit() == 0 || (bufferInfo.flags & 4) != 0) {
                        Log.d(TAG, "writeVideoSamples: EOF");
                    } else if (j6 - bufferInfo.presentationTimeUs < MIN_DURATION || z) {
                        if ((bufferInfo.flags & 1) != 0 || z7) {
                            j2 = j6;
                            long j9 = bufferInfo.presentationTimeUs;
                            if (j9 >= j4 && j7 < j9 - j8) {
                                if (!z7) {
                                    CircularMediaEncoder.Snapshot snapshot2 = this.mVideoSnapshot;
                                    snapshot2.offset = j9 - snapshot2.head;
                                    SampleWriter.StatusNotifier<Long> statusNotifier = this.mVideoFirstKeyFrameArrivedNotifier;
                                    if (statusNotifier != null) {
                                        statusNotifier.notify(Long.valueOf(snapshot2.offset));
                                    }
                                    Log.d(TAG, "writeVideoSamples: first video sample timestamp: " + j9);
                                    z7 = true;
                                } else {
                                    j9 = j8;
                                }
                                if (bufferInfo.presentationTimeUs >= j3) {
                                    Log.d(TAG, "writeVideoSamples: stop writing as reaching the ending timestamp");
                                    bufferInfo.flags = 4;
                                }
                                bufferInfo.presentationTimeUs -= j9;
                                this.mMediaMuxer.writeSampleData(this.mVideoTrackId, byteBuffer, bufferInfo);
                                long j10 = bufferInfo.presentationTimeUs;
                                Log.d(TAG, "writeVideoSamples: video sample timestamp: " + (bufferInfo.presentationTimeUs + j9));
                                j8 = j9;
                                j7 = j10;
                            }
                            z4 = byteBuffer.limit() == 0 || (bufferInfo.flags & 4) != 0;
                            j = j3;
                            j6 = j2;
                        } else {
                            Log.d(TAG, "writeVideoSamples: drop non-key frame sample timestamp: " + bufferInfo.presentationTimeUs);
                            j2 = j6;
                            z4 = z3;
                            j = j3;
                            j6 = j2;
                        }
                    } else if (!z8) {
                        if (!Util.isLivePhotoStable(livePhotoResult, i)) {
                            Log.d(TAG, "writeVideoSamples: drop non-stable frame sample timestamp: " + bufferInfo.presentationTimeUs);
                            break;
                        }
                        Log.d(TAG, "writeVideoSamples: drop first stable frame sample timestamp: " + bufferInfo.presentationTimeUs);
                        z2 = z3;
                        j = j3;
                        z = false;
                        z8 = true;
                    } else if (!Util.isLivePhotoStable(livePhotoResult, i)) {
                        Log.d(TAG, "writeVideoSamples: drop second non-stable frame sample timestamp: " + bufferInfo.presentationTimeUs);
                        break;
                    } else {
                        Log.d(TAG, "writeVideoSamples: drop first and second stable frame sample timestamp: " + bufferInfo.presentationTimeUs);
                        z2 = z3;
                        j = j3;
                        z = true;
                        z8 = true;
                    }
                } catch (InterruptedException unused) {
                    j3 = j;
                    j2 = j6;
                    z3 = z2;
                    Log.d(TAG, "writeVideoSamples: take: meet interrupted exception");
                }
            }
            z5 = z3;
            j5 = j3;
            z6 = false;
        }
        Log.d(TAG, "writeVideoSamples: EOF");
        CircularMediaEncoder.Snapshot snapshot3 = this.mVideoSnapshot;
        snapshot3.time = Math.max(0L, snapshot3.time - j8);
        Log.d(TAG, "writeVideoSamples: cover frame timestamp: " + this.mVideoSnapshot.time);
        Log.d(TAG, "writeVideoSamples: X: duration: " + j7);
        Log.d(TAG, "writeVideoSamples: X: offset: " + this.mVideoSnapshot.offset);
    }
}
