package com.xiaomi.camera.liveshot.encoder;

import android.media.AudioRecord;
import android.media.MediaCodec;
import android.media.MediaCrypto;
import android.media.MediaFormat;
import android.os.Handler;
import android.view.Surface;
import com.android.camera.log.Log;
import com.xiaomi.camera.liveshot.LivePhotoResult;
import com.xiaomi.camera.liveshot.MediaCodecCapability;
import com.xiaomi.camera.liveshot.encoder.CircularMediaEncoder;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

public class CircularAudioEncoder extends CircularMediaEncoder {
    private static boolean DEBUG = true;
    private static final int NOTIFICATION_PERIOD_SAMPLE_COUNT = 1440;
    private static final String TAG = "CircularAudioEncoder";
    private AudioRecord mAudioRecord;
    private final int mAudioRecordBufferSize;
    private final int mChannelCount = ((CircularMediaEncoder) this).mDesiredMediaFormat.getInteger("channel-count");
    private final int mFrameBytes;
    private final int mNotificationPeriod;
    private byte[] mSampleBuffer;
    private long mSampleCount;
    private final int mSampleRate = ((CircularMediaEncoder) this).mDesiredMediaFormat.getInteger("sample-rate");

    public CircularAudioEncoder(MediaFormat mediaFormat, long j, long j2, Queue<LivePhotoResult> queue) {
        super(mediaFormat, j, j2, queue);
        int integer = ((CircularMediaEncoder) this).mDesiredMediaFormat.getInteger("pcm-encoding");
        this.mFrameBytes = sampleBytes(integer) * this.mChannelCount;
        this.mNotificationPeriod = NOTIFICATION_PERIOD_SAMPLE_COUNT;
        this.mAudioRecordBufferSize = Math.max(this.mNotificationPeriod * this.mFrameBytes * 4, AudioRecord.getMinBufferSize(this.mSampleRate, channelConfig(), integer));
        this.mSampleBuffer = new byte[this.mAudioRecordBufferSize];
        this.mAudioRecord = new AudioRecord(5, this.mSampleRate, channelConfig(), integer, this.mAudioRecordBufferSize);
        if (this.mAudioRecord.getRecordingState() != 0) {
            boolean z = false;
            try {
                ((CircularMediaEncoder) this).mMediaCodec = MediaCodec.createByCodecName(MediaCodecCapability.HW_AUDIO_CODEC_AAC);
                z = true;
            } catch (Exception e2) {
                Log.d(TAG, "HW AAC encoder not found fallback to default instead", e2);
            }
            if (!z) {
                try {
                    ((CircularMediaEncoder) this).mMediaCodec = MediaCodec.createEncoderByType(((CircularMediaEncoder) this).mDesiredMediaFormat.getString("mime"));
                } catch (IOException e3) {
                    throw new IllegalStateException("Failed to configure MediaCodec: " + e3);
                }
            }
            ((CircularMediaEncoder) this).mIsInitialized = true;
            return;
        }
        this.mAudioRecord.release();
        throw new IllegalStateException("Failed to initialize AudioRecord");
    }

    private void addSampleCount(long j) {
        this.mSampleCount += j;
    }

    private int channelConfig() {
        int i = this.mChannelCount;
        return (i == 1 || i != 2) ? 16 : 12;
    }

    private long getPresentationTime(long j) {
        return (TimeUnit.SECONDS.toMicros(1) * (this.mSampleCount + j)) / ((long) this.mSampleRate);
    }

    private int getSampleDataBytes() {
        return this.mFrameBytes;
    }

    private int sampleBytes(int i) {
        if (i == 2) {
            return 2;
        }
        if (i == 3) {
            return 1;
        }
        throw new IllegalStateException("Specified Audio format is not supported.");
    }

    @Override // com.xiaomi.camera.liveshot.encoder.CircularMediaEncoder
    public void doRelease() {
        if (((CircularMediaEncoder) this).mIsInitialized) {
            super.doRelease();
            try {
                this.mAudioRecord.release();
            } catch (IllegalStateException e2) {
                String str = TAG;
                Log.d(str, "Meet exception when mAudioRecord.release(): " + e2);
            }
            ((CircularMediaEncoder) this).mIsInitialized = false;
        }
    }

    @Override // com.xiaomi.camera.liveshot.encoder.CircularMediaEncoder
    public void doStart() {
        if (DEBUG) {
            Log.d(TAG, "start(): X");
        }
        if (!((CircularMediaEncoder) this).mIsInitialized) {
            Log.d(TAG, "start(): not initialized yet");
        } else if (((CircularMediaEncoder) this).mIsBuffering) {
            Log.d(TAG, "start(): encoder is already running");
        } else {
            ((CircularMediaEncoder) this).mCyclicBuffer.clear();
            ((CircularMediaEncoder) this).mMediaCodec.configure(((CircularMediaEncoder) this).mDesiredMediaFormat, (Surface) null, (MediaCrypto) null, 1);
            ((CircularMediaEncoder) this).mMediaCodec.setCallback(this, new Handler(((CircularMediaEncoder) this).mEncodingThread.getLooper()));
            super.doStart();
            ((CircularMediaEncoder) this).mIsBuffering = true;
            ((CircularMediaEncoder) this).mCurrentPresentationTimeUs = 0;
            try {
                this.mAudioRecord.startRecording();
                if (DEBUG) {
                    Log.d(TAG, "start(): X");
                }
            } catch (IllegalStateException e2) {
                String str = TAG;
                Log.e(str, "startRecording(): failed " + e2);
            }
        }
    }

    @Override // com.xiaomi.camera.liveshot.encoder.CircularMediaEncoder
    public void doStop() {
        if (DEBUG) {
            Log.d(TAG, "stop(): E");
        }
        if (!((CircularMediaEncoder) this).mIsInitialized) {
            Log.d(TAG, "stop(): not initialized yet");
        } else if (((CircularMediaEncoder) this).mIsBuffering) {
            ((CircularMediaEncoder) this).mIsBuffering = false;
            super.doStop();
            if (DEBUG) {
                Log.d(TAG, "mAudioRecord.stop(): E");
            }
            try {
                this.mAudioRecord.stop();
            } catch (IllegalStateException e2) {
                String str = TAG;
                Log.d(str, "Meet exception when mAudioRecord.stop(): " + e2);
            }
            if (DEBUG) {
                Log.d(TAG, "mAudioRecord.stop(): X");
            }
            if (DEBUG) {
                Log.d(TAG, "clear pending snapshot requests: E");
            }
            ArrayList<CircularMediaEncoder.Snapshot> arrayList = new ArrayList();
            synchronized (((CircularMediaEncoder) this).mSnapshots) {
                arrayList.addAll(((CircularMediaEncoder) this).mSnapshots);
                ((CircularMediaEncoder) this).mSnapshots.clear();
            }
            if (DEBUG) {
                String str2 = TAG;
                Log.d(str2, "cleared " + arrayList.size() + " snapshot requests.");
            }
            for (CircularMediaEncoder.Snapshot snapshot : arrayList) {
                try {
                    snapshot.putEos();
                } catch (InterruptedException e3) {
                    String str3 = TAG;
                    Log.d(str3, "Failed to putEos: " + e3);
                }
            }
            if (DEBUG) {
                Log.d(TAG, "clear pending snapshot requests: X");
            }
            if (DEBUG) {
                Log.d(TAG, "stop() X");
            }
        }
    }

    @Override // com.xiaomi.camera.liveshot.encoder.CircularMediaEncoder
    public void onInputBufferAvailable(MediaCodec mediaCodec, int i) {
        if (DEBUG) {
            Log.d(TAG, "audioCodec.onInputBufferAvailable(): E");
        }
        if (!((CircularMediaEncoder) this).mIsBuffering) {
            Log.d(TAG, "audioCodec: already End of Stream");
            mediaCodec.queueInputBuffer(i, 0, 0, 0, 4);
            return;
        }
        Log.d(TAG, "audioCodec.dequeueInputBuffer(): E");
        ByteBuffer inputBuffer = mediaCodec.getInputBuffer(i);
        String str = TAG;
        Log.d(str, "audioCodec.dequeueInputBuffer(" + i + "): X");
        int i2 = 0;
        int read = this.mAudioRecord.read(this.mSampleBuffer, 0, Math.min(inputBuffer.limit(), this.mNotificationPeriod * this.mFrameBytes));
        if (read != -3) {
            if (read != -2) {
                if (read != 0) {
                    Log.d(TAG, "audioCodec.queueInputBuffer(): E");
                    inputBuffer.clear();
                    inputBuffer.put(this.mSampleBuffer, 0, read);
                    int position = inputBuffer.position() + 0;
                    long sampleDataBytes = (long) (position / getSampleDataBytes());
                    long presentationTime = getPresentationTime(sampleDataBytes);
                    if (!((CircularMediaEncoder) this).mIsBuffering) {
                        i2 = 4;
                    }
                    ((CircularMediaEncoder) this).mMediaCodec.queueInputBuffer(i, 0, position, presentationTime, i2);
                    addSampleCount(sampleDataBytes);
                    Log.d(TAG, "audioCodec.queueInputBuffer(): X");
                    if (DEBUG) {
                        Log.d(TAG, "audioCodec.onInputBufferAvailable(): X");
                    }
                } else if (DEBUG) {
                    Log.d(TAG, "  END_OF_BUFFER");
                }
            } else if (DEBUG) {
                Log.d(TAG, "  ERROR_BAD_VALUE");
            }
        } else if (DEBUG) {
            Log.d(TAG, "  ERROR_INVALID_OP");
        }
    }
}
