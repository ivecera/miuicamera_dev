package com.arcsoft.avatar.recoder;

import android.media.MediaCodec;
import android.media.MediaFormat;
import android.media.MediaMuxer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.arcsoft.avatar.util.CodecLog;
import com.arcsoft.avatar.util.NotifyMessage;
import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.nio.ByteBuffer;

public class MuxerWrapper {

    /* renamed from: a  reason: collision with root package name */
    private static final String f160a = "Arc_MuxerWrapper";
    private static final String g = "video";
    private static final String h = ".mp4";

    /* renamed from: b  reason: collision with root package name */
    private int f161b;

    /* renamed from: c  reason: collision with root package name */
    private volatile int f162c;

    /* renamed from: d  reason: collision with root package name */
    private volatile boolean f163d;

    /* renamed from: e  reason: collision with root package name */
    private MediaMuxer f164e;

    /* renamed from: f  reason: collision with root package name */
    private String f165f;
    private long i;
    private long j;
    private RecordingListener k;

    public MuxerWrapper(@Nullable FileDescriptor fileDescriptor, int i2, RecordingListener recordingListener) {
        this.f165f = "";
        this.i = 0;
        this.j = 0;
        this.k = null;
        this.k = recordingListener;
        this.f161b = 0;
        this.f162c = 0;
        this.f163d = false;
        try {
            this.f164e = new MediaMuxer(fileDescriptor, 0);
            this.f164e.setOrientationHint(i2);
            CodecLog.d(f160a, "MuxerWrapper()-> screenOrientation=" + i2);
        } catch (IOException e2) {
            CodecLog.e(f160a, "MuxerWrapper()-> create MediaMuxer failed.");
            e2.printStackTrace();
            this.f164e = null;
            RecordingListener recordingListener2 = this.k;
            if (recordingListener2 != null) {
                recordingListener2.onRecordingListener(NotifyMessage.MSG_MEDIA_RECORDER_ERROR_MUXER_CREATE, 0);
            }
        }
    }

    public MuxerWrapper(@Nullable String str, int i2, int i3, RecordingListener recordingListener) {
        this(str, i3, recordingListener);
    }

    public MuxerWrapper(@Nullable String str, int i2, RecordingListener recordingListener) {
        this.f165f = "";
        this.i = 0;
        this.j = 0;
        this.k = null;
        this.k = recordingListener;
        this.f165f = str;
        this.f161b = 0;
        this.f162c = 0;
        this.f163d = false;
        CodecLog.d(f160a, "MuxerWrapper()-> video name=" + this.f165f);
        try {
            this.f164e = new MediaMuxer(this.f165f, 0);
            this.f164e.setOrientationHint(i2);
            CodecLog.d(f160a, "MuxerWrapper()-> screenOrientation=" + i2);
        } catch (IOException e2) {
            CodecLog.e(f160a, "MuxerWrapper()-> create MediaMuxer failed.");
            e2.printStackTrace();
            this.f164e = null;
            RecordingListener recordingListener2 = this.k;
            if (recordingListener2 != null) {
                recordingListener2.onRecordingListener(NotifyMessage.MSG_MEDIA_RECORDER_ERROR_MUXER_CREATE, 0);
            }
        }
    }

    private void a() {
        File file = new File(this.f165f);
        if (!file.exists()) {
            File file2 = new File(file.getParent());
            if (!file2.exists()) {
                file2.mkdirs();
                return;
            }
            return;
        }
        file.delete();
    }

    public synchronized int addTrack(@NonNull MediaFormat mediaFormat) {
        int i2;
        if (this.f164e == null) {
            CodecLog.e(f160a, "writeSampleData()-> mMuxer must be created , but it's null until now.");
            return -1;
        }
        i2 = 0;
        try {
            i2 = this.f164e.addTrack(mediaFormat);
        } catch (Exception e2) {
            if (this.k != null) {
                this.k.onRecordingListener(NotifyMessage.MSG_MEDIA_RECORDER_ERROR_MUXER_ADD_TRACK, 0);
            }
            e2.printStackTrace();
        }
        return i2;
    }

    public long getSizeRecordFile() {
        if (TextUtils.isEmpty(this.f165f)) {
            return 0;
        }
        File file = new File(this.f165f);
        if (!file.exists() || !file.isFile()) {
            return 0;
        }
        return file.length();
    }

    public long getTimeElapse() {
        return this.j - this.i;
    }

    public boolean isStarted() {
        return this.f163d;
    }

    public void setCurrentTime(long j2) {
        this.j = j2;
    }

    public void setEncoderCount(int i2) {
        if (i2 <= 0 || i2 > 2) {
            throw new RuntimeException("The encoder count must between 1 and 2.");
        }
        this.f161b = i2;
    }

    public void setStartTime(long j2) {
        this.i = j2;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:19:0x004f, code lost:
        return;
     */
    public synchronized void startMuxer() {
        if (this.f164e == null) {
            CodecLog.e(f160a, "startMuxer()-> mMuxer must be created , but it's null until now");
            return;
        }
        this.f162c++;
        if (this.f162c == this.f161b) {
            try {
                CodecLog.d(f160a, "startMuxer()-> Muxerstart");
                this.f164e.start();
            } catch (Exception e2) {
                CodecLog.e(f160a, "startMuxer()-> Muxer start failed");
                if (this.k != null) {
                    this.k.onRecordingListener(NotifyMessage.MSG_MEDIA_RECORDER_ERROR_MUXER_START, 0);
                }
                e2.printStackTrace();
            }
            this.f163d = true;
            notifyAll();
            CodecLog.d(f160a, "startMuxer()-> mMuxer is started");
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:23:0x009f, code lost:
        return;
     */
    public synchronized void stopMuxer() {
        if (this.f164e != null) {
            this.f162c--;
            CodecLog.d(f160a, "stopMuxer()-> mEncoderCount=" + this.f162c + " ,maxCount=" + this.f161b);
            if (this.f162c == 0) {
                try {
                    this.f164e.stop();
                } catch (Exception e2) {
                    CodecLog.e(f160a, "stopMuxer()-> muxer.stop() error=" + e2.getMessage());
                    if (this.k != null) {
                        this.k.onRecordingListener(NotifyMessage.MSG_MEDIA_RECORDER_ERROR_MUXER_STOP, 0);
                    }
                }
                try {
                    this.f164e.release();
                } catch (Exception e3) {
                    CodecLog.e(f160a, "stopMuxer()-> muxer.release() error=" + e3.getMessage());
                    if (this.k != null) {
                        this.k.onRecordingListener(NotifyMessage.MSG_MEDIA_RECORDER_ERROR_MUXER_RELEASE, 0);
                    }
                }
                this.f164e = null;
                CodecLog.d(f160a, "stopMuxer()-> Muxer is released.");
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0037, code lost:
        return;
     */
    public synchronized void writeSampleData(int i2, @NonNull ByteBuffer byteBuffer, @NonNull MediaCodec.BufferInfo bufferInfo) {
        if (this.f164e == null) {
            CodecLog.e(f160a, "writeSampleData()-> mMuxer must be created , but it's null until now.");
            return;
        }
        try {
            this.f164e.writeSampleData(i2, byteBuffer, bufferInfo);
            CodecLog.d(f160a, "writeSampleData()-> writeSampleData done");
        } catch (Exception e2) {
            CodecLog.e(f160a, "writeSampleData()-> writeSampleData failed");
            e2.printStackTrace();
            if (this.k != null) {
                this.k.onRecordingListener(NotifyMessage.MSG_MEDIA_RECORDER_ERROR_MUXER_WRITE_SAMPLE_DATA, 0);
            }
        }
    }
}
