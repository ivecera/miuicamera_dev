package com.android.camera.module.encoder;

import android.media.AudioRecord;
import android.media.MediaCodec;
import android.media.MediaCodecInfo;
import android.media.MediaCodecList;
import android.media.MediaCrypto;
import android.media.MediaFormat;
import android.os.Process;
import android.view.Surface;
import com.android.camera.log.Log;
import com.android.camera.module.encoder.MediaEncoder;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class MediaAudioEncoder extends MediaEncoder {
    private static final int[] AUDIO_SOURCES = {1, 0, 5};
    private static final int BIT_RATE = 64000;
    public static final int FRAMES_PER_BUFFER = 25;
    private static final String MIME_TYPE = "audio/mp4a-latm";
    public static final int SAMPLES_PER_FRAME = 1024;
    private static final int SAMPLE_RATE = 44100;
    /* access modifiers changed from: private */
    public static final String TAG = "MediaAudioEncoder";
    /* access modifiers changed from: private */
    public AudioEffectThread mAudioEffectThread = null;
    private AudioThread mAudioThread = null;
    protected final Object mMediaCodecLock = new Object();

    /* access modifiers changed from: private */
    public class AudioEffectThread extends Thread {
        private short[] DUMMY_SAMPLE = new short[0];
        private BlockingQueue<short[]> mBufferQueue = new LinkedBlockingQueue();
        private SoundEffect mSoundEffect;
        private boolean mStopped;

        public AudioEffectThread(float f2) {
            if (f2 != 1.0f) {
                this.mSoundEffect = new SoundEffect(MediaAudioEncoder.SAMPLE_RATE, 1);
                this.mSoundEffect.setTempo(f2);
            }
        }

        private void encodeSamples(short[] sArr) {
            int length = sArr.length * 2;
            ByteBuffer allocateDirect = ByteBuffer.allocateDirect(length);
            allocateDirect.order(ByteOrder.nativeOrder()).asShortBuffer().put(sArr, 0, sArr.length);
            allocateDirect.rewind();
            synchronized (MediaAudioEncoder.this.mMediaCodecLock) {
                if (!((MediaEncoder) MediaAudioEncoder.this).mSkipFrame) {
                    MediaAudioEncoder.this.encode(allocateDirect, length, MediaAudioEncoder.this.getPTSUs());
                }
            }
            MediaAudioEncoder.this.frameAvailableSoon();
        }

        private void processBuffer() throws InterruptedException {
            short[] take = this.mBufferQueue.take();
            if (take != this.DUMMY_SAMPLE) {
                SoundEffect soundEffect = this.mSoundEffect;
                if (soundEffect != null) {
                    soundEffect.putSamples(take);
                } else {
                    encodeSamples(take);
                }
            }
        }

        private void writeSamples() {
            short[] receiveSamples;
            while (true) {
                SoundEffect soundEffect = this.mSoundEffect;
                if (soundEffect != null && (receiveSamples = soundEffect.receiveSamples(1024)) != null) {
                    encodeSamples(receiveSamples);
                } else {
                    return;
                }
            }
        }

        public void post(short[] sArr) {
            try {
                this.mBufferQueue.put(sArr);
            } catch (InterruptedException unused) {
            }
        }

        public void requestStop() {
            this.mStopped = true;
            post(this.DUMMY_SAMPLE);
        }

        public void run() {
            Log.d(MediaAudioEncoder.TAG, "audioEffectThread>>>");
            while (true) {
                if (this.mStopped && this.mBufferQueue.isEmpty()) {
                    break;
                }
                try {
                    processBuffer();
                } catch (InterruptedException unused) {
                }
                writeSamples();
            }
            SoundEffect soundEffect = this.mSoundEffect;
            if (soundEffect != null) {
                soundEffect.flush();
                writeSamples();
                this.mSoundEffect.release();
            }
            Log.d(MediaAudioEncoder.TAG, "audioEffectThread<<<");
        }
    }

    private class AudioThread extends Thread {
        private AudioRecord audioRecord;

        public AudioThread(AudioRecord audioRecord2) {
            this.audioRecord = audioRecord2;
        }

        /* JADX INFO: finally extract failed */
        /* JADX WARNING: Code restructure failed: missing block: B:14:0x0027, code lost:
            r1 = new short[1024];
         */
        /* JADX WARNING: Code restructure failed: missing block: B:15:0x0032, code lost:
            if (r4.audioRecord.read(r1, 0, 1024) <= 0) goto L_0x0014;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:16:0x0034, code lost:
            com.android.camera.module.encoder.MediaAudioEncoder.access$100(r4.this$0).post(r1);
         */
        public void run() {
            Process.setThreadPriority(-19);
            try {
                if (((MediaEncoder) MediaAudioEncoder.this).mIsCapturing) {
                    Log.d(MediaAudioEncoder.TAG, "audioThread>>>");
                    while (true) {
                        try {
                            synchronized (((MediaEncoder) MediaAudioEncoder.this).mSync) {
                                if (((MediaEncoder) MediaAudioEncoder.this).mRequestStop) {
                                }
                            }
                        } finally {
                            this.audioRecord.stop();
                        }
                    }
                }
                MediaAudioEncoder.this.mAudioEffectThread.requestStop();
                this.audioRecord.release();
                Log.d(MediaAudioEncoder.TAG, "audioThread<<<");
            } catch (Throwable th) {
                MediaAudioEncoder.this.mAudioEffectThread.requestStop();
                this.audioRecord.release();
                throw th;
            }
        }
    }

    public MediaAudioEncoder(MediaMuxerWrapper mediaMuxerWrapper, MediaEncoder.MediaEncoderListener mediaEncoderListener) {
        super(mediaMuxerWrapper, mediaEncoderListener);
    }

    private AudioRecord initAudioRecord() {
        int minBufferSize = AudioRecord.getMinBufferSize(SAMPLE_RATE, 16, 2);
        int i = 25600;
        if (25600 < minBufferSize) {
            i = ((minBufferSize / 1024) + 1) * 1024 * 2;
        }
        AudioRecord audioRecord = null;
        for (int i2 : AUDIO_SOURCES) {
            AudioRecord audioRecord2 = new AudioRecord(i2, SAMPLE_RATE, 16, 2, i);
            if (audioRecord2.getState() != 1) {
                audioRecord2.release();
                audioRecord = null;
            } else {
                audioRecord = audioRecord2;
            }
            if (audioRecord != null) {
                break;
            }
        }
        return audioRecord;
    }

    private static MediaCodecInfo selectAudioCodec(String str) {
        String[] supportedTypes;
        int codecCount = MediaCodecList.getCodecCount();
        for (int i = 0; i < codecCount; i++) {
            MediaCodecInfo codecInfoAt = MediaCodecList.getCodecInfoAt(i);
            if (codecInfoAt.isEncoder()) {
                for (String str2 : codecInfoAt.getSupportedTypes()) {
                    if (str2.equalsIgnoreCase(str)) {
                        return codecInfoAt;
                    }
                }
                continue;
            }
        }
        return null;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.encoder.MediaEncoder
    public void prepare() throws IOException {
        Log.v(TAG, "prepare>>>");
        ((MediaEncoder) this).mTrackIndex = -1;
        ((MediaEncoder) this).mMuxerStarted = false;
        ((MediaEncoder) this).mIsEOS = false;
        MediaCodecInfo selectAudioCodec = selectAudioCodec(MIME_TYPE);
        if (selectAudioCodec == null) {
            Log.e(TAG, "no appropriate codec for audio/mp4a-latm");
            return;
        }
        String str = TAG;
        Log.d(str, "selected codec: " + selectAudioCodec.getName());
        MediaFormat createAudioFormat = MediaFormat.createAudioFormat(MIME_TYPE, SAMPLE_RATE, 1);
        createAudioFormat.setInteger("aac-profile", 2);
        createAudioFormat.setInteger("channel-mask", 16);
        createAudioFormat.setInteger("bitrate", BIT_RATE);
        createAudioFormat.setInteger("channel-count", 1);
        String str2 = TAG;
        Log.d(str2, "format: " + createAudioFormat);
        ((MediaEncoder) this).mMediaCodec = MediaCodec.createEncoderByType(MIME_TYPE);
        ((MediaEncoder) this).mMediaCodec.configure(createAudioFormat, (Surface) null, (MediaCrypto) null, 1);
        ((MediaEncoder) this).mMediaCodec.start();
        MediaEncoder.MediaEncoderListener mediaEncoderListener = ((MediaEncoder) this).mListener;
        if (mediaEncoderListener != null) {
            mediaEncoderListener.onPrepared(this);
        }
        Log.v(TAG, "prepare<<<");
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.encoder.MediaEncoder
    public void release() {
        this.mAudioThread = null;
        synchronized (this.mMediaCodecLock) {
            super.release();
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.encoder.MediaEncoder
    public boolean startRecording(long j) {
        boolean startRecording = super.startRecording(j);
        if (!startRecording) {
            return false;
        }
        if (this.mAudioThread == null) {
            AudioRecord initAudioRecord = initAudioRecord();
            if (initAudioRecord == null) {
                Log.e(TAG, "failed to initialize AudioRecord");
                return false;
            }
            try {
                initAudioRecord.startRecording();
                if (3 == initAudioRecord.getRecordingState()) {
                    this.mAudioEffectThread = new AudioEffectThread(1.0f / ((MediaEncoder) this).mSpeed);
                    this.mAudioEffectThread.start();
                    this.mAudioThread = new AudioThread(initAudioRecord);
                    this.mAudioThread.start();
                    if (!startRecording && initAudioRecord != null) {
                        initAudioRecord.stop();
                        initAudioRecord.release();
                    }
                }
            } catch (IllegalStateException e2) {
                Log.e(TAG, e2.getMessage(), e2);
            }
            startRecording = false;
            initAudioRecord.stop();
            initAudioRecord.release();
        }
        return startRecording;
    }
}
