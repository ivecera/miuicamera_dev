package org.webrtc.voiceengine;

import android.content.Context;
import android.media.AudioRecord;
import android.os.Process;
import android.util.Log;
import java.nio.ByteBuffer;
import java.util.concurrent.locks.ReentrantLock;

class WebRtcAudioRecord {
    private AudioRecord _audioRecord = null;
    private int _bufferedRecSamples = 0;
    private Context _context;
    private boolean _doRecInit = true;
    private boolean _isRecording = false;
    private ByteBuffer _recBuffer;
    private final ReentrantLock _recLock = new ReentrantLock();
    private byte[] _tempBufRec;
    final String logTag = "WebRTC AD java";

    WebRtcAudioRecord() {
        try {
            this._recBuffer = ByteBuffer.allocateDirect(1920);
        } catch (Exception e2) {
            DoLog(e2.getMessage());
        }
        this._tempBufRec = new byte[1920];
    }

    private void DoLog(String str) {
        Log.d("WebRTC AD java", str);
    }

    private void DoLogErr(String str) {
        Log.e("WebRTC AD java", str);
    }

    private int InitRecording(int i, int i2, int i3) {
        int minBufferSize = AudioRecord.getMinBufferSize(i2, 16, 2) * 2;
        this._bufferedRecSamples = i2 / 200;
        AudioRecord audioRecord = this._audioRecord;
        if (audioRecord != null) {
            audioRecord.release();
            this._audioRecord = null;
        }
        try {
            this._audioRecord = new AudioRecord(i, i2, i3, 2, minBufferSize);
            if (this._audioRecord.getState() != 1) {
                return -1;
            }
            return this._bufferedRecSamples;
        } catch (Exception e2) {
            DoLog(e2.getMessage());
            return -1;
        }
    }

    private int RecordAudio(int i) {
        int i2;
        this._recLock.lock();
        try {
            if (this._audioRecord == null) {
                i2 = -2;
            } else {
                if (this._doRecInit) {
                    try {
                        Process.setThreadPriority(-19);
                    } catch (Exception e2) {
                        DoLog("Set rec thread priority failed: " + e2.getMessage());
                    }
                    this._doRecInit = false;
                }
                this._recBuffer.rewind();
                int read = this._audioRecord.read(this._tempBufRec, 0, i);
                this._recBuffer.put(this._tempBufRec);
                if (read != i) {
                    i2 = -1;
                }
                this._recLock.unlock();
                return this._bufferedRecSamples;
            }
            this._recLock.unlock();
            return i2;
        } catch (Exception e3) {
            DoLogErr("RecordAudio try failed: " + e3.getMessage());
        } catch (Throwable th) {
            this._recLock.unlock();
            throw th;
        }
    }

    private int StartRecording() {
        try {
            this._audioRecord.startRecording();
            this._isRecording = true;
            return 0;
        } catch (IllegalStateException e2) {
            e2.printStackTrace();
            return -1;
        }
    }

    private int StopRecording() {
        this._recLock.lock();
        try {
            if (this._audioRecord.getRecordingState() == 3) {
                try {
                    this._audioRecord.stop();
                } catch (IllegalStateException e2) {
                    e2.printStackTrace();
                    return -1;
                }
            }
            this._audioRecord.release();
            this._audioRecord = null;
            this._doRecInit = true;
            this._recLock.unlock();
            this._isRecording = false;
            return 0;
        } finally {
            this._doRecInit = true;
            this._recLock.unlock();
        }
    }
}
