package com.xiaomi.devicemanager;

import android.content.Context;
import android.media.AudioManager;
import android.util.Log;

public class DeviceManager {
    static final int AUTO = 0;
    static final int EARPIECE = 2;
    static final int HEADSET = 3;
    static final int SPEAKER = 1;
    private static final String TAG = "DeviceManager";
    private AudioManager audioManager;
    private int audioOutputDevice = 0;
    private DeviceCallback cc = null;
    private long devicemanagerInst = 0;
    private Context mContext;

    public enum DeviceErrorTypeT {
        DEVICE_START_CAMERA_FAILED,
        DEVICE_CAMERA_CHANGE_MODE_FAILED,
        DEVICE_SET_CAMERA_FRAMERATE_FAILED,
        DEVICE_NO_CAMERA,
        DEVICE_SETUP_CAMERA_ERROR,
        DEVICE_FAILED_GET_VOICEPRO_STATE,
        DEVICE_START_MIC_FAILED,
        DEVICE_START_SPEAKER_FAILED,
        DEVICE_UNKNOWN_ERROR
    }

    public enum VideoContentTypeT {
        KPeople,
        KScreen
    }

    private native boolean EnableHeadsetPlugAutoHandlerJni(boolean z);

    private void OnAudioMixedMusicFinished() {
        Log.d(TAG, " java OnAudioMixedMusicFinished");
        DeviceCallback deviceCallback = this.cc;
        if (deviceCallback != null) {
            deviceCallback.OnAudioMixedMusicFinished();
        }
    }

    private void OnCameraStartFailed() {
        Log.d(TAG, " java OnCameraStartFailed");
        DeviceCallback deviceCallback = this.cc;
        if (deviceCallback != null) {
            deviceCallback.OnCameraStartFailed();
        }
    }

    private void OnMicStartFailed() {
        Log.d(TAG, " java OnMicStartFailed");
        DeviceCallback deviceCallback = this.cc;
        if (deviceCallback != null) {
            deviceCallback.OnMicStartFailed();
        }
    }

    private void OnMixDataReady(short[] sArr, int i, int i2, int i3) {
        Log.d(TAG, " java OnMixDataReady");
        DeviceCallback deviceCallback = this.cc;
        if (deviceCallback != null) {
            deviceCallback.OnMixDataReady(sArr, i, i2, i3);
        }
    }

    private native boolean addVideoPreprocessFilterJni(long j, int i);

    private native long constructDeviceManagerJni(Context context, String str);

    private native boolean destructDeviceManagerJni();

    private native void enableMicMixMusicJni(boolean z);

    private native void enableRotationJni(boolean z);

    private native boolean enableVideoPreprocessJni(boolean z);

    private native int getBackgroundMaxVolumeJni();

    private native int getBackgroundMinVolumeJni();

    private native int getBackgroundVolumeJni();

    private native int getCurrentVideoZoomFactorJni();

    private native int getEyeZoomRatioJni();

    private native int getFaceThinRatioJni();

    private native int getForegroundMaxVolumeJni();

    private native int getForegroundMinVolumeJni();

    private native int getForegroundVolumeJni();

    private native int getLoopbackBackgroundMaxVolumeJni();

    private native int getLoopbackBackgroundMinVolumeJni();

    private native int getLoopbackBackgroundVolumeJni();

    private int getOutPutDevice() {
        if (this.audioManager.isSpeakerphoneOn()) {
            this.audioOutputDevice = 1;
        } else if (this.audioManager.isWiredHeadsetOn()) {
            this.audioOutputDevice = 3;
        } else {
            this.audioOutputDevice = 2;
        }
        return this.audioOutputDevice;
    }

    private native int getPlayoutMaxVolumeJni();

    private native int getPlayoutMinVolumeJni();

    private native int getPlayoutVolumeJni();

    private native int getSmoothLevelJni();

    private native int getSupportedVideoZoomMaxFactorJni();

    private native float getVideoFilterIntensityJni();

    private native int getWhiteLevelJni();

    private native boolean isSupportResolutionJni(int i, int i2, int i3);

    private native boolean isUsingFrontCameraJni();

    private native boolean isVideoZoomSupportedJni();

    private native void loopbackAudioJni(boolean z);

    private native void muteMicrophoneJni(boolean z);

    private native void muteSpeakerJni(boolean z);

    private void onStartCamera() {
        Log.d(TAG, " java onStartCamera");
        DeviceCallback deviceCallback = this.cc;
        if (deviceCallback != null) {
            deviceCallback.onStartCamera();
        }
    }

    private void onStopCamera() {
        Log.d(TAG, " java onStopCamera");
        DeviceCallback deviceCallback = this.cc;
        if (deviceCallback != null) {
            deviceCallback.onStopCamera();
        }
    }

    private native void pauseMixMusicJni();

    private native void playEffectiveJni(String str);

    private native void removeVideoPreprocessFilterJni(long j, int i);

    private native void resumeMixMusicJni();

    private native void setAudioOutputModelJni(int i);

    private native void setAudioTypeJni(int i);

    private native void setBackgroundVolumeJni(int i);

    private native boolean setCameraParamJni(int i, int i2, int i3);

    private native void setChatblurLevelJni(int i);

    private native void setEyeZoomRatioJni(int i);

    private native void setFaceThinRatioJni(int i);

    private native void setFlashStateJni(boolean z);

    private native void setFocusPointJni(float f2, float f3);

    private native void setForegroundVolumeJni(int i);

    private native void setLoopbackBackgroundVolumeJni(int i);

    private native void setOrientationJni(int i, int i2);

    private native void setPlayoutVolumeJni(int i);

    private native void setSmoothLevelJni(int i);

    private native void setSpeakerJni(boolean z);

    private native boolean setStickerPathJni(String str);

    private native void setVideoFilterIntensityJni(float f2);

    private native void setVideoFilterJni(String str);

    private native boolean setVideoOutputParamJni(int i, int i2, int i3);

    private native void setVideoPreprocessParametersJni(float f2, float f3);

    private native void setVideoZoomFactorJni(int i);

    private native void setVoiceChangeModeJni(int i);

    private native void setWhiteLevelJni(int i);

    private native boolean startCameraJni(boolean z);

    private native void startMicrophoneJni(boolean z);

    private native void startMixMusicJni(String str, boolean z);

    private native long startPlayBackgroundMusicJni(String str, boolean z);

    private native void startReverberationJni(int i);

    private native void startSpeakerJni(boolean z);

    private native void stopMixMusicJni();

    private native void stopPlayBackgroundMusicJni(long j);

    private native void stopReverberationJni();

    private native boolean switchCameraJni();

    public boolean EnableHeadsetPlugAutoHandler(boolean z) {
        Log.i(TAG, "setting  headset plug ...");
        return EnableHeadsetPlugAutoHandlerJni(z);
    }

    public void EnableMicMixMusic(boolean z) {
        Log.i(TAG, " EnableMicMixMusic ");
        if (this.mContext == null) {
            Log.e(TAG, " EnableMicMixMusic error, please init the engine first");
        } else {
            enableMicMixMusicJni(z);
        }
    }

    public void SetOrientation(int i, int i2) {
        Log.i(TAG, "setting orientation with deviceOrientation:" + i + " UI orientation:" + i2);
        if (this.mContext == null) {
            Log.e(TAG, "SetOrientation error, please init the engine first");
            return;
        }
        setOrientationJni(i, i2);
        Log.i(TAG, "set orientation done");
    }

    public boolean addVideoPreprocessFilter(long j, int i) {
        Log.d(TAG, "addVideoPreprocessFilter filter:" + j + " filterType:" + i);
        return addVideoPreprocessFilterJni(j, i);
    }

    public boolean attachCallback(DeviceCallback deviceCallback) {
        Log.d(TAG, " java attachCallback");
        this.cc = deviceCallback;
        return true;
    }

    public boolean constructDeviceManager(Context context) {
        Log.d(TAG, "construct DeviceManager...");
        if (this.mContext != null) {
            Log.d(TAG, "construct error, please destroy the engine first");
            return false;
        }
        this.mContext = context;
        this.devicemanagerInst = constructDeviceManagerJni(context, context.getPackageName());
        return true;
    }

    public boolean destructDeviceManager() {
        Log.d(TAG, "destruct DeviceManager...");
        if (this.mContext == null) {
            Log.d(TAG, "destruct error, please destroy the engine first");
            return false;
        }
        destructDeviceManagerJni();
        this.mContext = null;
        this.devicemanagerInst = 0;
        return true;
    }

    public boolean disattachCallback() {
        Log.d(TAG, " java attachCallback");
        this.cc = null;
        return true;
    }

    public void enableMicMixMusic(boolean z) {
        Log.d(TAG, "enableMicMixMusic");
        enableMicMixMusicJni(z);
    }

    public void enableRotation(boolean z) {
        Log.i(TAG, "Eanble rotate with:" + z);
        if (this.mContext == null) {
            Log.e(TAG, "enableRotation error, please init the engine first");
            return;
        }
        enableRotationJni(z);
        Log.i(TAG, "set rotate done");
    }

    public boolean enableVideoPreprocess(boolean z) {
        Log.d(TAG, "enableVideoPreprocess");
        return enableVideoPreprocessJni(z);
    }

    public int getBackgroundMaxVolume() {
        Log.d(TAG, "getBackgroundMaxVolume");
        return getBackgroundMaxVolumeJni();
    }

    public int getBackgroundMinVolume() {
        Log.d(TAG, "getBackgroundMinVolume");
        return getBackgroundMinVolumeJni();
    }

    public int getBackgroundVolume() {
        Log.d(TAG, "getBackgroundVolume");
        return getBackgroundVolumeJni();
    }

    public int getCurrentVideoZoomFactor() {
        Log.d(TAG, "getCurrentVideoZoomFactor");
        return getCurrentVideoZoomFactorJni();
    }

    public int getEyeZoomRatio() {
        Log.d(TAG, "getEyeZoomRatio");
        return getEyeZoomRatioJni();
    }

    public int getFaceThinRatio() {
        Log.d(TAG, "getFaceThinRatio");
        return getFaceThinRatioJni();
    }

    public int getForegroundMaxVolume() {
        Log.d(TAG, "getForegroundMaxVolume");
        return getForegroundMaxVolumeJni();
    }

    public int getForegroundMinVolume() {
        Log.d(TAG, "getForegroundMinVolume");
        return getForegroundMinVolumeJni();
    }

    public int getForegroundVolume() {
        Log.d(TAG, "getForegroundVolume");
        return getForegroundVolumeJni();
    }

    public long getInstance() {
        return this.devicemanagerInst;
    }

    public int getLoopbackBackgroundMaxVolume() {
        Log.d(TAG, "getLoopbackBackgroundMaxVolume");
        return getLoopbackBackgroundMaxVolumeJni();
    }

    public int getLoopbackBackgroundMinVolume() {
        Log.d(TAG, "getLoopbackBackgroundMinVolume");
        return getLoopbackBackgroundMinVolumeJni();
    }

    public int getLoopbackBackgroundVolume() {
        Log.d(TAG, "getLoopbackBackgroundVolume");
        return getLoopbackBackgroundVolumeJni();
    }

    public int getMicMaxVolume() {
        Log.d(TAG, "getMicMaxVolume");
        return getForegroundMaxVolumeJni();
    }

    public int getMicMinVolume() {
        Log.d(TAG, "getMicMinVolume");
        return getForegroundMinVolumeJni();
    }

    public int getMicVolume() {
        Log.d(TAG, "getMicVolume");
        return getForegroundVolumeJni();
    }

    public int getPlayoutMaxVolume() {
        Log.d(TAG, "getPlayoutMaxVolume");
        return getPlayoutMaxVolumeJni();
    }

    public int getPlayoutMinVolume() {
        Log.d(TAG, "getPlayoutMinVolume");
        return getPlayoutMinVolumeJni();
    }

    public int getPlayoutVolume() {
        Log.d(TAG, "getPlayoutVolume");
        return getPlayoutVolumeJni();
    }

    public int getSmoothLevel() {
        Log.d(TAG, "getSmoothLevel");
        return getSmoothLevelJni();
    }

    public int getSupportedVideoZoomMaxFactor() {
        Log.d(TAG, "getSupportedVideoZoomMaxFactor");
        return getSupportedVideoZoomMaxFactorJni();
    }

    public float getVideoFilterIntensity() {
        Log.d(TAG, "getVideoFilterIntensity");
        return getVideoFilterIntensityJni();
    }

    public int getWhiteLevel() {
        Log.d(TAG, "getWhiteLevel");
        return getWhiteLevelJni();
    }

    public boolean isSupportResolution(int i, int i2, int i3) {
        Log.d(TAG, " isSupportResolution:" + i + "x" + i2 + ":" + i3);
        return isSupportResolutionJni(i, i2, i3);
    }

    public boolean isUsingFrontCamera() {
        Log.d(TAG, "isUsingFrontCamera enter");
        return isUsingFrontCameraJni();
    }

    public boolean isVideoZoomSupported() {
        Log.d(TAG, "isVideoZoomSupported");
        return isVideoZoomSupportedJni();
    }

    public void loopbackAudio(boolean z) {
        Log.i(TAG, " loopbackAudio ");
        if (this.mContext == null) {
            Log.e(TAG, " loopbackAudio error, please init the engine first");
        } else {
            loopbackAudioJni(z);
        }
    }

    public boolean muteMicrophone() {
        Log.i(TAG, "starting muteMicrophone...");
        if (this.mContext == null) {
            Log.e(TAG, "muteMicrophone error, please init the engine first");
            return false;
        }
        muteMicrophoneJni(true);
        Log.i(TAG, "muteMicrophone succeeded");
        return true;
    }

    public boolean muteSpeaker() {
        Log.i(TAG, "starting muteSpeaker...");
        if (this.mContext == null) {
            Log.e(TAG, "muteSpeaker error, please init the engine first");
            return false;
        }
        muteSpeakerJni(true);
        Log.i(TAG, "muteSpeaker succeeded");
        return true;
    }

    public void pauseMixMusic() {
        Log.d(TAG, "pauseMixMusic");
        pauseMixMusicJni();
    }

    public void playEffective(String str) {
        Log.d(TAG, "playEffective");
        playEffectiveJni(str);
    }

    public void removeVideoPreprocessFilter(long j, int i) {
        Log.d(TAG, "removeVideoPreprocessFilter filter:" + j + " filterType:" + i);
        removeVideoPreprocessFilterJni(j, i);
    }

    public void resumeMixMusic() {
        Log.d(TAG, "resumeMixMusic");
        resumeMixMusicJni();
    }

    public void setAudioType(int i) {
        Log.d(TAG, "setAudioType");
        setAudioTypeJni(i);
    }

    public void setBackgroundVolume(int i) {
        Log.d(TAG, "setBackgroundVolume");
        setBackgroundVolumeJni(i);
    }

    public boolean setCameraParam(int i, int i2, int i3) {
        Log.i(TAG, "setting  camera param ...");
        if (this.mContext == null) {
            Log.e(TAG, "setCameraParam error, please init the engine first");
            return false;
        } else if (!setCameraParamJni(i, i2, i3)) {
            Log.e(TAG, "set camera param failed");
            return false;
        } else {
            Log.i(TAG, " set camera param succeeded");
            return true;
        }
    }

    public void setChatblurLevel(int i) {
        Log.d(TAG, "setChatblurLevel");
        setChatblurLevelJni(i);
    }

    public void setEyeZoomRatio(int i) {
        Log.d(TAG, "setEyeZoomRatio");
        setEyeZoomRatioJni(i);
    }

    public void setFaceThinRatio(int i) {
        Log.d(TAG, "setFaceThinRatio");
        setFaceThinRatioJni(i);
    }

    public void setFlashState(boolean z) {
        Log.d(TAG, "setFlashState");
        setFlashStateJni(z);
    }

    public void setFocusPoint(float f2, float f3) {
        Log.d(TAG, "setFocusPoint");
        setFocusPointJni(f2, f3);
    }

    public void setForegroundVolume(int i) {
        Log.d(TAG, "setForegroundVolume");
        setForegroundVolumeJni(i);
    }

    public void setLoopbackBackgroundVolume(int i) {
        Log.d(TAG, "setBackgroundVolume");
        setLoopbackBackgroundVolumeJni(i);
    }

    public void setMicVolume(int i) {
        Log.d(TAG, "setMicVolume");
        setForegroundVolumeJni(i);
    }

    public void setPlayoutVolume(int i) {
        Log.d(TAG, "setPlayoutVolume");
        setPlayoutVolumeJni(i);
    }

    public void setSmoothLevel(int i) {
        Log.d(TAG, "setSmoothLevel");
        setSmoothLevelJni(i);
    }

    public void setSpeaker(boolean z) {
        Log.i(TAG, "set speaker phone on: " + z);
        setSpeakerJni(z);
    }

    public boolean setStickerPath(String str) {
        Log.d(TAG, "setStickerPath:" + str);
        return setStickerPathJni(str);
    }

    public void setVideoFilter(String str) {
        Log.d(TAG, "setVideoFilter");
        setVideoFilterJni(str);
    }

    public void setVideoFilterIntensity(float f2) {
        Log.d(TAG, "setVideoFilterIntensity");
        setVideoFilterIntensityJni(f2);
    }

    public boolean setVideoOutputParam(int i, int i2, int i3) {
        Log.i(TAG, "setting  video output param ...");
        if (this.mContext == null) {
            Log.e(TAG, "setVideoOutputParam error, please init the engine first");
            return false;
        } else if (!setVideoOutputParamJni(i, i2, i3)) {
            Log.e(TAG, "set video output param failed");
            return false;
        } else {
            Log.i(TAG, " set video output param succeeded");
            return true;
        }
    }

    public void setVideoPreprocessParameters(float f2, float f3) {
        Log.d(TAG, "setVideoPreprocessParameters");
        setVideoPreprocessParametersJni(f2, f3);
    }

    public void setVideoZoomFactor(int i) {
        Log.d(TAG, "setVideoZoomFactor");
        setVideoZoomFactorJni(i);
    }

    public void setVoiceChangeMode(int i) {
        Log.i(TAG, " setVoiceChangeMode, mode: " + i);
        if (this.mContext == null) {
            Log.e(TAG, "setVoiceChangeMode AudioDevice error, please init the engine first");
        } else {
            setVoiceChangeModeJni(i);
        }
    }

    public void setWhiteLevel(int i) {
        Log.d(TAG, "setWhiteLevel");
        setWhiteLevelJni(i);
    }

    public void startAudioDevice() {
        Log.i(TAG, " startAudioDevice ");
        if (this.mContext == null) {
            Log.e(TAG, "start AudioDevice error, please init the engine first");
            return;
        }
        startSpeakerJni(true);
        startMicrophoneJni(true);
    }

    public boolean startCamera() {
        Log.i(TAG, "starting camera...");
        if (this.mContext == null) {
            Log.e(TAG, "startCamera error, please init the engine first");
            return false;
        } else if (!startCameraJni(true)) {
            Log.e(TAG, "start camera failed");
            return false;
        } else {
            Log.i(TAG, " start camera succeeded");
            return true;
        }
    }

    public void startMixMusic(String str, boolean z) {
        Log.i(TAG, " startMixMusic ");
        if (this.mContext == null) {
            Log.e(TAG, " startMixMusic error, please init the engine first");
        } else {
            startMixMusicJni(str, z);
        }
    }

    public long startPlayBackgroundMusic(String str, boolean z) {
        Log.i(TAG, " startMixMusic ");
        if (this.mContext != null) {
            return startPlayBackgroundMusicJni(str, z);
        }
        Log.e(TAG, " startMixMusic error, please init the engine first");
        return 0;
    }

    public void startReverberation(int i) {
        Log.d(TAG, "startReverberation");
        startReverberationJni(i);
    }

    public void stopAudioDevice() {
        Log.i(TAG, " stopAudioDevice ");
        if (this.mContext == null) {
            Log.e(TAG, "stop AudioDevice error, please init the engine first");
            return;
        }
        startMicrophoneJni(false);
        startSpeakerJni(false);
    }

    public boolean stopCamera() {
        Log.i(TAG, "stopping camera...");
        if (this.mContext == null) {
            Log.e(TAG, "stopCamera error, please init the engine first");
            return false;
        } else if (!startCameraJni(false)) {
            Log.e(TAG, "stop camera failed");
            return false;
        } else {
            Log.i(TAG, "stop camera succeeded");
            return true;
        }
    }

    public void stopMixMusic() {
        Log.i(TAG, " stopMixMusic ");
        if (this.mContext == null) {
            Log.e(TAG, " stopMixMusic error, please init the engine first");
        } else {
            stopMixMusicJni();
        }
    }

    public void stopPlayBackgroundMusic(long j) {
        Log.i(TAG, " stopPlayBackgroundMusic");
        if (this.mContext == null) {
            Log.e(TAG, " stopPlayBackgroundMusic error, please init the engine first");
        } else {
            stopPlayBackgroundMusicJni(j);
        }
    }

    public void stopReverberation() {
        Log.d(TAG, "stopReverberation");
        stopReverberationJni();
    }

    public boolean switchCamera() {
        Log.i(TAG, "switching camera...");
        if (this.mContext == null) {
            Log.e(TAG, "switchCamera error, please init the engine first");
            return false;
        } else if (!switchCameraJni()) {
            Log.e(TAG, "switch camera failed");
            return false;
        } else {
            Log.i(TAG, "switch camera succeeded");
            return true;
        }
    }

    public boolean unMuteMicrophone() {
        Log.i(TAG, "unMuting Microphone...");
        if (this.mContext == null) {
            Log.e(TAG, "unMuteMicrophone error, please init the engine first");
            return false;
        }
        muteMicrophoneJni(false);
        Log.i(TAG, "unMute Microphone succeeded");
        return true;
    }

    public boolean unMuteSpeaker() {
        Log.i(TAG, "unMuting Speaker...");
        if (this.mContext == null) {
            Log.e(TAG, "unMuteSpeaker error, please init the engine first");
            return false;
        }
        muteSpeakerJni(false);
        Log.i(TAG, "unMute Speaker succeeded");
        return true;
    }
}
