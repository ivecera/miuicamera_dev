package com.android.camera.module.impl.component;

import android.graphics.SurfaceTexture;
import java.util.List;

public interface ILive {

    public interface ILivePlayer {

        public interface ComposeState {
            public static final int COMPOSED = 3;
            public static final int COMPOSING = 2;
            public static final int ERROR = -1;
            public static final int IDLE = 0;
            public static final int PREPARE = 1;
        }

        public interface PlayerState {
            public static final int ERROR = -1;
            public static final int IDLE = 0;
            public static final int PAUSE = 4;
            public static final int PENDING_START = 2;
            public static final int PREPARE = 1;
            public static final int PREVIEWING = 3;
        }

        void cancelCompose();

        void init(int i, int i2, int i3, int i4, List<ILiveSegmentData> list, String str);

        void pausePlayer();

        void release();

        void resumePlayer();

        void setStateListener(ILivePlayerStateListener iLivePlayerStateListener);

        void startCompose(String str);

        void startPlayer(SurfaceTexture surfaceTexture);

        void stopPlayer();
    }

    public interface ILivePlayerStateListener {
        void onComposeStateChange(int i);

        void onPlayStateChange(int i);
    }

    public interface ILiveRecorder {

        public interface State {
            public static final int IDLE = 0;
            public static final int PENDING_PAUSE_RECORDING = 6;
            public static final int PENDING_RESUME_RECORDING = 7;
            public static final int PENDING_START_RECORDING = 4;
            public static final int PENDING_STOP_RECORDING = 5;
            public static final int PREVIEWING = 1;
            public static final int RECORDING = 2;
            public static final int RECORDING_DONE = 8;
            public static final int RECORDING_ERROR = 9;
            public static final int RECORDING_PAUSED = 3;
        }

        void deletePreSegment();

        SurfaceTexture genInputSurfaceTexture();

        String getAudioPath();

        List<ILiveSegmentData> getLiveSegments();

        long getStartTime();

        void init(int i, int i2);

        void pauseRecording();

        void release();

        void resumeRecording();

        void setAudioPath(String str);

        void setFilterPath(String str);

        void setOrientation(int i);

        void setSpeed(float f2);

        void startRecording();

        void stopRecording();
    }

    public interface ILiveRecorderStateListener {
        void onStateChange(int i);
    }

    public interface ILiveRecordingTimeListener {
        void onRecordingTimeFinish();

        void onRecordingTimeUpdate(long j, float f2);
    }

    public interface ILiveSegmentData {
        long getDuration();

        long getNextPos();

        String getPath();

        float getSpeed();
    }

    static default long getTotalDuration(List<ILiveSegmentData> list) {
        long j = 0;
        if (list != null && !list.isEmpty()) {
            for (ILiveSegmentData iLiveSegmentData : list) {
                j += iLiveSegmentData.getDuration();
            }
        }
        return j;
    }
}
