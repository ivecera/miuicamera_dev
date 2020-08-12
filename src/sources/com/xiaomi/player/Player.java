package com.xiaomi.player;

import android.util.Log;
import android.view.Surface;
import com.xiaomi.player.callback.PlayerCallback;
import com.xiaomi.player.datastruct.VideoSize;
import com.xiaomi.player.enums.AVErrorState;
import com.xiaomi.player.enums.PlayerCacheType;
import com.xiaomi.player.enums.PlayerPlaybackState;
import com.xiaomi.player.enums.PlayerSeekingMode;
import com.xiaomi.player.enums.PlayerWorkingMode;
import com.xiaomi.stat.d;

public class Player {
    private static String TAG = "Player";
    private PlayerCallback pc = null;
    private long playerInst = 0;
    private String url = "";

    public enum SurfaceGravity {
        SurfaceGravityResizeAspect(0),
        SurfaceGravityResizeAspectFit(1),
        SurfaceGravityResizeAspectFill(2);
        
        private int code;

        private SurfaceGravity(int i) {
            this.code = i;
        }

        public int toInt() {
            return this.code;
        }
    }

    private native long GetCurrentStreamPositionJni();

    private native void addFlashFilterJni(int i);

    private native void addGrayscaleFilterJni();

    private native void addMotionFlowFilterJni();

    private native void addPngMixFilterJni(String str, float f2, float f3, float f4, float f5);

    private native void addRecordingSessionJni(long j);

    private native void addShakeFilterJni();

    private native void addSlowDownFilterJni();

    private native void addSobelEdgeDetectionFilterJni();

    private native void addSoulFilterJni();

    private native void addVideoMapFilterJni(String str, String str2, String str3);

    private native long bufferTimeMaxJni();

    public static int cancelCompressingMP4File(String str) {
        Log.d(TAG, "cancel compressing MP4 files");
        return cancelCompressingMP4FileJni(str);
    }

    private static native int cancelCompressingMP4FileJni(String str);

    public static int compressMP4File(String str, String str2, long j) {
        Log.d(TAG, "compress MP4 files");
        return compressMP4FileJni(str, str2, j);
    }

    private static native int compressMP4FileJni(String str, String str2, long j);

    private native long constructPlayerJni(String str, int i, long j);

    private native long currentPlaybackTimeJni();

    private native String debugReportJni();

    private native void destructPlayerJni();

    private native long durationJni();

    private native void enableVideoFilterJni(boolean z);

    private native long getAudioTransferJni();

    private native long getCurrentAudioTimestampJni();

    private native long getCurrentCachePositionJni();

    private native long getStreamIdJni();

    private native long getTimestampOfCurrentVideoFrameJni();

    private native boolean isPausedJni();

    private native void muteAudioJni();

    private native void pauseCacheThreadOtherJni(String str);

    private native void pauseCacheThreadUriAllJni();

    private native void pauseCacheThreadUriJni(String str);

    private native void pauseJni();

    private native int playbackStateJni();

    private native void redrawJni();

    private native boolean reloadJni(String str, boolean z);

    private native void removeRecordingSessionJni(long j);

    private native boolean resumeJni();

    private native boolean seekToJni(long j, int i);

    private native void setAllFileSizeJni(long j);

    private native void setBufferTimeMaxJni(long j);

    private native void setCachePathJni(String str);

    private native void setCacheSizeJni(long j);

    private native void setCacheSpeedJni(long j);

    private native void setEnableCacheTypeJni(int i);

    private native void setFrameLoopJni(boolean z);

    private native void setGravityJni(int i, int i2, int i3);

    private native void setIpListJni(String[] strArr);

    private native void setLocalCacheJni(String str, long j);

    private native void setResolveDnsJni(boolean z);

    private native void setSpeakerJni(boolean z);

    private native void setSpeakerVolumeJni(float f2);

    private native boolean setSpeedRatioJni(double d2);

    private native void setUserIdandClienIpJni(String str, String str2);

    private native void setVideoFilterIntensityJni(float f2);

    private native void setVideoFilterJni(String str);

    private native void setVideoSurfaceJni(Surface surface);

    private native void setWifiStatusJni(boolean z);

    private native void shiftUpJni(float f2, float f3, float f4, float f5, float f6);

    private native void startCacheUriAllJni();

    private native void startCacheUriJni(String str);

    private native void startCacheUriOtherJni(String str);

    private native boolean startJni(String str, String str2, boolean z);

    private native void startRecordingJni();

    private native boolean startWithTimeJni(String str, String str2, boolean z, long j);

    private native void stopJni();

    private native void stopRecordingJni();

    private native void unMuteAudioJni();

    private native void updateCacheUriJni(String[] strArr);

    public static String version() {
        return versionJni();
    }

    private static native String versionJni();

    private native VideoSize videoSizeJni();

    public long GetCurrentStreamPosition() {
        return GetCurrentStreamPositionJni();
    }

    public void addFlashFilter(int i) {
        addFlashFilterJni(i);
    }

    public void addGrayscaleFilter() {
        addGrayscaleFilterJni();
    }

    public void addMotionFlowFilter() {
        addMotionFlowFilterJni();
    }

    public void addPngMixFilter(String str, float f2, float f3, float f4, float f5) {
        addPngMixFilterJni(str, f2, f3, f4, f5);
    }

    public void addRecordingSession(long j) {
        addRecordingSessionJni(j);
    }

    public void addShakeFilter() {
        addShakeFilterJni();
    }

    public void addSlowDownFilter() {
        addSlowDownFilterJni();
    }

    public void addSobelEdgeDetectionFilter() {
        addSobelEdgeDetectionFilterJni();
    }

    public void addSoulFilter() {
        addSoulFilterJni();
    }

    public void addVideoMapFilter(String str, String str2, String str3) {
        addVideoMapFilterJni(str, str2, str3);
    }

    public long bufferTimeMax() {
        return bufferTimeMaxJni();
    }

    public void constructPlayer(String str, PlayerCallback playerCallback, PlayerWorkingMode playerWorkingMode, long j) {
        Log.d(TAG, "constructPlayer");
        this.pc = playerCallback;
        this.playerInst = constructPlayerJni(str, playerWorkingMode.ordinal(), j);
    }

    public long currentPlaybackTime() {
        return currentPlaybackTimeJni();
    }

    public String debugReport() {
        return debugReportJni();
    }

    public void destructPlayer() {
        Log.d(TAG, "destructPlayer");
        setVideoSurface(null);
        destructPlayerJni();
        this.pc = null;
        this.playerInst = 0;
    }

    public long duration() {
        return durationJni();
    }

    public boolean editorPlayerSetInnerVolume(float f2) {
        return editorPlayerSetVolumeInnerJni(f2);
    }

    public boolean editorPlayerSetMP3Volume(float f2) {
        return editorPlayerSetVolumeExternalMp3Jni(f2);
    }

    public native boolean editorPlayerSetVolumeExternalMp3Jni(float f2);

    public native boolean editorPlayerSetVolumeInnerJni(float f2);

    public boolean editorPlayerStart(String str, String str2, long j, long j2) {
        this.url = str;
        return editorPlayerStartJni(str, str2, j, j2);
    }

    public native boolean editorPlayerStartJni(String str, String str2, long j, long j2);

    public void enableEqWithMode(boolean z, int i) {
        enableEqWithModeJni(z, i);
    }

    public native void enableEqWithModeJni(boolean z, int i);

    public void enableExtremeLargeVolume(boolean z) {
        enableExtremeLargeVolumeJni(z);
    }

    public native void enableExtremeLargeVolumeJni(boolean z);

    public void enableFixedVolume(boolean z) {
        enableFixedVolumeJni(z);
    }

    public native void enableFixedVolumeJni(boolean z);

    public void enableVideoFilter(boolean z) {
        enableVideoFilterJni(z);
    }

    public long getAudioTransfer() {
        Log.d(TAG, "get audio transfer");
        return getAudioTransferJni();
    }

    public long getCurrentAudioTimestamp() {
        return getCurrentAudioTimestampJni();
    }

    public long getCurrentCachePosition() {
        return getCurrentCachePositionJni();
    }

    public long getStreamId() {
        Log.d(TAG, "get stream ID");
        return getStreamIdJni();
    }

    public long getTimestampOfCurrentVideoFrame() {
        return getTimestampOfCurrentVideoFrameJni();
    }

    public boolean isPaused() {
        return isPausedJni();
    }

    public void muteAudio() {
        muteAudioJni();
    }

    public void onAudioRenderingStart() {
        Log.d(TAG, "callback:onAudioRenderingStart");
        this.pc.onAudioRenderingStart();
    }

    public void onFirstPacketRecved() {
        Log.d(TAG, "callback:onFirstPacketRecved");
        this.pc.onFirstPacketRecved();
    }

    public void onOpenStreamFailed(int i) {
        String str = TAG;
        Log.d(str, "callback:onOpenStreamFailed" + i);
        this.pc.onOpenStreamFailed(AVErrorState.int2enum(i));
    }

    public void onPlayerPaused() {
        Log.d(TAG, "callback:onPlayerPaused");
        this.pc.onPlayerPaused();
    }

    public void onPlayerResumed() {
        Log.d(TAG, "callback:onPlayerResumed");
        this.pc.onPlayerResumed();
    }

    public void onPlayerStarted() {
        Log.d(TAG, "callback:onPlayerStarted");
        this.pc.onPlayerStarted();
    }

    public void onPlayerStoped() {
        Log.d(TAG, "callback:onPlayerStoped");
        this.pc.onPlayerStoped();
    }

    public void onSeekCompleted() {
        Log.d(TAG, "callback:onSeekCompleted");
        this.pc.onSeekCompleted();
    }

    public void onStartBuffering() {
        Log.d(TAG, "callback:onStartBuffering");
        this.pc.onStartBuffering();
    }

    public void onStartPlaying() {
        Log.d(TAG, "callback:onStartPlaying");
        this.pc.onStartPlaying();
    }

    public void onStartWithTimeInvalid(long j) {
        String str = TAG;
        Log.d(str, "debug::onStartWithTimeInvalid, the file duration is " + j + d.H);
        this.pc.onStartWithTimeInvalid(j);
    }

    public void onStreamEOF() {
        Log.d(TAG, "callback:onStreamEOF");
        this.pc.onStreamEOF();
    }

    public void onVideoRenderingStart() {
        Log.d(TAG, "callback:onVideoRenderingStart");
        this.pc.onVideoRenderingStart();
    }

    public void onVideoSizeChanged(int i, int i2) {
        Log.d(TAG, "callback:onVideoSizeChanged");
        VideoSize videoSize = new VideoSize(0.0f, 0.0f);
        videoSize.video_width = (float) i;
        videoSize.video_height = (float) i2;
        this.pc.onVideoSizeChanged(videoSize);
    }

    public void pause() {
        pauseJni();
    }

    public void pauseCacheThreadOther(String str) {
        pauseCacheThreadOtherJni(str);
    }

    public void pauseCacheThreadUri(String str) {
        pauseCacheThreadUriJni(str);
    }

    public void pauseCacheThreadUriAll() {
        pauseCacheThreadUriAllJni();
    }

    public PlayerPlaybackState playbackState() {
        return PlayerPlaybackState.int2enum(playbackStateJni());
    }

    public void redraw() {
        redrawJni();
    }

    public boolean reload(String str, boolean z) {
        this.url = str;
        return reloadJni(this.url, z);
    }

    public void removeRecordingSession(long j) {
        removeRecordingSessionJni(j);
    }

    public boolean resume() {
        return resumeJni();
    }

    public boolean seekTo(long j, PlayerSeekingMode playerSeekingMode) {
        return seekToJni(j, playerSeekingMode.ordinal());
    }

    public void setAllFileSize(long j) {
        setAllFileSizeJni(j);
    }

    public void setBufferTimeMax(long j) {
        if (j >= 120) {
            setBufferTimeMaxJni(j);
        }
    }

    public void setCachePath(String str) {
        setCachePathJni(str);
    }

    public void setCacheSize(long j) {
        setCacheSizeJni(j);
    }

    public void setCacheSpeed(long j) {
        setCacheSpeedJni(j);
    }

    public void setEnableCacheType(PlayerCacheType playerCacheType) {
        setEnableCacheTypeJni(playerCacheType.ordinal());
    }

    public void setFrameLoop(boolean z) {
        setFrameLoopJni(z);
    }

    public void setGravity(SurfaceGravity surfaceGravity, int i, int i2) {
        setGravityJni(surfaceGravity.toInt(), i, i2);
    }

    public void setIpList(String[] strArr, String[] strArr2) {
        setIpListJni(strArr);
    }

    public void setMaxDownloadBufferTime(long j) {
        setMaxDownloadBufferTimeJni(j);
    }

    public native void setMaxDownloadBufferTimeJni(long j);

    public void setResolveDns(boolean z) {
        setResolveDnsJni(z);
    }

    public void setSpeaker(boolean z) {
        setSpeakerJni(z);
    }

    public void setSpeakerVolume(float f2) {
        setSpeakerVolumeJni(f2);
    }

    public boolean setSpeedRatio(double d2) {
        return setSpeedRatioJni(d2);
    }

    public void setSpeedUpThreshold(long j) {
        setSpeedUpThresholdJni(j);
    }

    public native void setSpeedUpThresholdJni(long j);

    public void setUserIdandClienIp(String str, String str2) {
        setUserIdandClienIpJni(str, str2);
    }

    public void setVideoFilter(String str) {
        setVideoFilterJni(str);
    }

    public void setVideoFilterIntensity(float f2) {
        setVideoFilterIntensityJni(f2);
    }

    public void setVideoSurface(Surface surface) {
        setVideoSurfaceJni(surface);
    }

    public void setWifiStatus(boolean z) {
        setWifiStatusJni(z);
    }

    public void shiftUp(float f2, float f3, float f4, float f5, float f6) {
        shiftUpJni(f2, f3, f4, f5, f6);
    }

    public boolean start(String str, String str2, boolean z) {
        this.url = str;
        return startJni(this.url, str2, z);
    }

    public boolean start(String str, String str2, boolean z, long j) {
        this.url = str;
        return startWithTimeJni(this.url, str2, z, j);
    }

    public void startCacheUri(String str) {
        startCacheUriJni(str);
    }

    public void startCacheUriAll() {
        startCacheUriAllJni();
    }

    public void startCacheUriOther(String str) {
        startCacheUriOtherJni(str);
    }

    public void stop() {
        stopJni();
    }

    public void unMuteAudio() {
        unMuteAudioJni();
    }

    public void updateCacheUri(String[] strArr) {
        updateCacheUriJni(strArr);
    }

    public VideoSize videoSize() {
        return videoSizeJni();
    }
}
