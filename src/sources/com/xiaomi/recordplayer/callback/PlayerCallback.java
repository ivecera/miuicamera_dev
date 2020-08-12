package com.xiaomi.recordplayer.callback;

import com.xiaomi.recordplayer.datastruct.VideoSize;
import com.xiaomi.recordplayer.enums.AVErrorState;

public interface PlayerCallback {
    void onAudioRenderingStart();

    void onFirstPacketRecved();

    void onOpenStreamFailed(AVErrorState aVErrorState);

    void onPlayerPaused();

    void onPlayerResumed();

    void onPlayerStarted();

    void onPlayerStoped();

    void onSeekCompleted();

    void onStartBuffering();

    void onStartPlaying();

    void onStartWithTimeInvalid(long j);

    void onStreamEOF();

    void onVideoRenderingStart();

    void onVideoSizeChanged(VideoSize videoSize);
}
