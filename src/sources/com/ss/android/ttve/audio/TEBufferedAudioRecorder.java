package com.ss.android.ttve.audio;

import android.media.AudioRecord;
import android.util.Log;
import com.android.camera.fragment.subtitle.recog.record.PcmRecorder;
import com.android.camera.module.BaseModule;

public class TEBufferedAudioRecorder {
    private static final String TAG = "TEBufferedAudioRecorder";
    protected static int channelConfigOffset = -1;
    protected static int[] channelConfigSuggested = {12, 16, 1};
    protected static int sampleRateOffset = -1;
    protected static int[] sampleRateSuggested = {44100, BaseModule.LENS_DIRTY_DETECT_HINT_DURATION_8S, 11025, PcmRecorder.RATE16K, 22050};
    AudioRecord audio;
    TEAudioWriterInterface audioCaller;
    int audioFormat = 2;
    int bufferSizeInBytes = 0;
    int channelConfig = -1;
    boolean isRecording = false;
    int sampleRateInHz = -1;

    public TEBufferedAudioRecorder(TEAudioWriterInterface tEAudioWriterInterface) {
        this.audioCaller = tEAudioWriterInterface;
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        AudioRecord audioRecord = this.audio;
        if (audioRecord != null) {
            try {
                if (audioRecord.getState() != 0) {
                    this.audio.stop();
                }
                this.audio.release();
            } catch (Exception unused) {
            }
            this.audio = null;
        }
        super.finalize();
    }

    public int getSampleRateInHz() {
        return this.sampleRateInHz;
    }

    public void init(int i) {
        int i2;
        if (this.audio != null) {
            Log.e(TAG, "second time audio init(), skip");
            return;
        }
        int i3 = -1;
        try {
            if (!(channelConfigOffset == -1 || sampleRateOffset == -1)) {
                this.channelConfig = channelConfigSuggested[channelConfigOffset];
                this.sampleRateInHz = sampleRateSuggested[sampleRateOffset];
                this.bufferSizeInBytes = AudioRecord.getMinBufferSize(this.sampleRateInHz, this.channelConfig, this.audioFormat);
                this.audio = new AudioRecord(i, this.sampleRateInHz, this.channelConfig, this.audioFormat, this.bufferSizeInBytes);
            }
        } catch (Exception e2) {
            Log.e(TAG, "使用预设配置" + channelConfigOffset + "," + sampleRateOffset + "实例化audio recorder失败，重新测试配置。" + e2);
        }
        if (this.audio == null) {
            channelConfigOffset = -1;
            int[] iArr = channelConfigSuggested;
            int length = iArr.length;
            int i4 = 0;
            boolean z = false;
            while (i4 < length) {
                this.channelConfig = iArr[i4];
                int i5 = 1;
                channelConfigOffset++;
                sampleRateOffset = i3;
                int[] iArr2 = sampleRateSuggested;
                int length2 = iArr2.length;
                int i6 = 0;
                while (true) {
                    if (i6 >= length2) {
                        break;
                    }
                    int i7 = iArr2[i6];
                    sampleRateOffset += i5;
                    try {
                        this.bufferSizeInBytes = AudioRecord.getMinBufferSize(i7, this.channelConfig, this.audioFormat);
                        Log.i(TAG, "试用hz " + i7 + " " + this.channelConfig + " " + this.audioFormat);
                        if (this.bufferSizeInBytes > 0) {
                            this.sampleRateInHz = i7;
                            this.audio = new AudioRecord(i, this.sampleRateInHz, this.channelConfig, this.audioFormat, this.bufferSizeInBytes);
                            z = true;
                            break;
                        }
                        sampleRateOffset++;
                        i2 = 1;
                        i6++;
                        i5 = i2;
                    } catch (Exception e3) {
                        this.sampleRateInHz = 0;
                        this.audio = null;
                        Log.e(TAG, "apply audio record sample rate " + i7 + " failed: " + e3.getMessage());
                        i2 = 1;
                        sampleRateOffset = sampleRateOffset + 1;
                    }
                }
                if (z) {
                    break;
                }
                i4++;
                i3 = -1;
            }
        }
        if (this.sampleRateInHz <= 0) {
            Log.e(TAG, "!Init audio recorder failed, hz " + this.sampleRateInHz);
            return;
        }
        Log.i(TAG, "Init audio recorder succeed, apply audio record sample rate " + this.sampleRateInHz + " buffer " + this.bufferSizeInBytes + " state " + this.audio.getState());
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0015, code lost:
        r10 = r9.audioCaller.initWavFile(r10, r9.sampleRateInHz, 2, r11, r13, r14);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0022, code lost:
        if (r10 == 0) goto L_0x003b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0024, code lost:
        android.util.Log.e(com.ss.android.ttve.audio.TEBufferedAudioRecorder.TAG, "init wav file failed, ret = " + r10);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x003a, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x003b, code lost:
        new java.lang.Thread(new com.ss.android.ttve.audio.TEBufferedAudioRecorder.AnonymousClass1(r9)).start();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0048, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x004a, code lost:
        return;
     */
    public void startRecording(String str, double d2, int i, int i2) {
        Log.e(TAG, "audio startRecording");
        synchronized (this) {
            if (!this.isRecording) {
                if (this.audio != null) {
                    this.isRecording = true;
                }
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0014, code lost:
        if (r3.audio.getState() == 0) goto L_0x001b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0016, code lost:
        r3.audio.stop();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x001b, code lost:
        r3.audioCaller.closeWavFile();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0021, code lost:
        return true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0033, code lost:
        return false;
     */
    public boolean stopRecording() {
        synchronized (this) {
            if (this.isRecording) {
                if (this.audio != null) {
                    this.isRecording = false;
                }
            }
            Log.e(TAG, "未启动音频模块但调用stopRecording");
            if (this.audio != null) {
                this.audio.release();
            }
        }
    }

    public void unInit() {
        AudioRecord audioRecord = this.audio;
        if (audioRecord != null) {
            try {
                if (audioRecord.getState() != 0) {
                    this.audio.stop();
                }
                this.audio.release();
            } catch (Exception unused) {
            }
            this.audio = null;
        }
        TEAudioWriterInterface tEAudioWriterInterface = this.audioCaller;
        if (tEAudioWriterInterface != null) {
            tEAudioWriterInterface.destroy();
        }
    }
}
