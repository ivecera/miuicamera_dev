package com.xiaomi.camera.liveshot.writer;

import android.media.MediaCodec;
import android.media.MediaMuxer;
import com.android.camera.log.Log;
import com.xiaomi.camera.liveshot.encoder.CircularMediaEncoder;
import com.xiaomi.camera.liveshot.writer.SampleWriter;
import java.nio.ByteBuffer;

public class AudioSampleWriter extends SampleWriter {
    private static final boolean DEBUG = true;
    private static final String TAG = "AudioSampleWriter";
    private final CircularMediaEncoder.Snapshot mAudioSnapshot;
    private final int mAudioTrackId;
    private final MediaMuxer mMediaMuxer;
    private final SampleWriter.StatusNotifier<Long> mVideoFirstKeyFrameArrivedNotifier;

    public AudioSampleWriter(MediaMuxer mediaMuxer, CircularMediaEncoder.Snapshot snapshot, int i, SampleWriter.StatusNotifier<Long> statusNotifier) {
        this.mMediaMuxer = mediaMuxer;
        this.mAudioSnapshot = snapshot;
        this.mAudioTrackId = i;
        this.mVideoFirstKeyFrameArrivedNotifier = statusNotifier;
    }

    /* access modifiers changed from: protected */
    @Override // com.xiaomi.camera.liveshot.writer.SampleWriter
    public void writeSample() {
        ByteBuffer byteBuffer;
        Log.d(TAG, "writeAudioSamples: E");
        SampleWriter.StatusNotifier<Long> statusNotifier = this.mVideoFirstKeyFrameArrivedNotifier;
        long longValue = statusNotifier != null ? statusNotifier.getStatus().longValue() : 0;
        long j = this.mAudioSnapshot.head;
        if (longValue < 0) {
            longValue = 0;
        }
        long j2 = j + longValue;
        long j3 = this.mAudioSnapshot.tail;
        Log.d(TAG, "writeAudioSamples: head timestamp: " + this.mAudioSnapshot.head + ":" + j2);
        Log.d(TAG, "writeAudioSamples: tail timestamp: " + this.mAudioSnapshot.tail + ":" + j3);
        long j4 = -1;
        long j5 = 0;
        boolean z = false;
        boolean z2 = false;
        while (!z) {
            Log.d(TAG, "writeAudioSamples: take: E");
            try {
                CircularMediaEncoder.Sample take = this.mAudioSnapshot.samples.take();
                Log.d(TAG, "writeAudioSamples: take: X");
                ByteBuffer byteBuffer2 = take.data;
                MediaCodec.BufferInfo bufferInfo = take.info;
                long j6 = bufferInfo.presentationTimeUs;
                if (j6 < j2 || j4 >= j6 - j5) {
                    byteBuffer = byteBuffer2;
                } else {
                    if (!z2) {
                        CircularMediaEncoder.Snapshot snapshot = this.mAudioSnapshot;
                        snapshot.offset = j6 - snapshot.head;
                        Log.d(TAG, "writeAudioSamples: first audio sample timestamp: " + j6);
                        j5 = j6;
                        z2 = true;
                    }
                    if (bufferInfo.presentationTimeUs >= j3) {
                        Log.d(TAG, "writeAudioSamples: stop writing as reaching the ending timestamp");
                        bufferInfo.flags = 4;
                    }
                    bufferInfo.presentationTimeUs -= j5;
                    this.mMediaMuxer.writeSampleData(this.mAudioTrackId, byteBuffer2, bufferInfo);
                    j4 = bufferInfo.presentationTimeUs;
                    String str = TAG;
                    StringBuilder sb = new StringBuilder();
                    sb.append("writeAudioSamples: audio sample timestamp: ");
                    byteBuffer = byteBuffer2;
                    sb.append(bufferInfo.presentationTimeUs + j5);
                    Log.d(str, sb.toString());
                }
                z = byteBuffer.limit() == 0 || (bufferInfo.flags & 4) != 0;
            } catch (InterruptedException unused) {
                Log.d(TAG, "writeAudioSamples: take: meet interrupted exception");
            }
        }
        Log.d(TAG, "writeAudioSamples: X: duration: " + j4);
        Log.d(TAG, "writeAudioSamples: X: offset: " + this.mAudioSnapshot.offset);
    }
}
