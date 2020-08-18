package com.xiaomi.Video2GifEditer;

public enum RecordingStatus {
    RecordingStopped(0),
    RecordingPlaying(1),
    RecordingPaused(2);
    
    private int nCode;

    private RecordingStatus(int i) {
        this.nCode = i;
    }

    public static RecordingStatus int2enum(int i) {
        RecordingStatus recordingStatus = RecordingStopped;
        RecordingStatus[] values = values();
        for (RecordingStatus recordingStatus2 : values) {
            if (recordingStatus2.ordinal() == i) {
                recordingStatus = recordingStatus2;
            }
        }
        return recordingStatus;
    }

    public String toString() {
        return String.valueOf(this.nCode);
    }
}
