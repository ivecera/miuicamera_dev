package com.xiaomi.conferencemanager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.Build;
import android.util.Log;
import com.ss.android.vesdk.VEEditor;
import com.xiaomi.conferencemanager.ConferenceEngine;
import com.xiaomi.conferencemanager.Model.MonitorData;
import com.xiaomi.conferencemanager.callback.ConferenceCallback;
import com.xiaomi.utils.Logger;

public class ConferenceManager {
    static final int AUTO = 0;
    static final int EARPIECE = 2;
    static final int HEADSET = 3;
    static final int SPEAKER = 1;
    private static final String TAG = "ConferenceManager";
    private AudioManager audioManager;
    private int audioOutputDevice = 0;
    final BroadcastReceiver audioStateChangeReceiver = new BroadcastReceiver() {
        /* class com.xiaomi.conferencemanager.ConferenceManager.AnonymousClass1 */

        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.i(ConferenceManager.TAG, "audioStateChangeReceiver: get action " + action + " extra: " + intent.getIntExtra("state", 0));
            int unused = ConferenceManager.this.getOutPutDevice();
        }
    };
    byte[] gslb_test_config = {1};
    private Context mContext;

    public enum ConferenceManagerRole {
        CLIENT_ROLE_PROXY,
        CLIENT_ROLE_HOST,
        CLIENT_ROLE_BROADCASTVIEWER,
        CLIENT_ROLE_ATTENDEE
    }

    public enum DynamicViewPolicyT {
        KShowIfNotStaticallyViewed,
        KShowEvenIfStaticallyViewed
    }

    public enum EngineErrorTypeT {
        ENGINE_NO_ERROR,
        ENGINE_CONNECTION_FAILED,
        ENGINE_CONNECTION_LOST,
        ENGINE_PEER_BUSY,
        ENGINE_PEER_REJECTED,
        ENGINE_PEER_ENDED,
        ENGINE_CONFERENCE_PARAM_INVALID,
        ENGINE_START_CAMERA_FAILED,
        ENGINE_CAMERA_CHANGE_MODE_FAILED,
        ENGINE_SET_CAMERA_FRAMERATE_FAILED,
        ENGINE_NO_CAMERA,
        ENGINE_SETUP_CAMERA_ERROR,
        ENGINE_FAILED_GET_VOICEPRO_STATE,
        ENGINE_START_MIC_FAILED,
        ENGINE_START_SPEAKER_FAILED,
        ENGINE_SHARE_WINDOW_FAILED,
        ENGINE_SHARE_DESKTOP_FAILED,
        ENGINE_CONSTRUT_CLIENT_FAILED,
        ENGINE_RUN_CRASHED,
        ENGINE_UNKNOWN_ERROR
    }

    public enum VideoContentTypeT {
        KPeople,
        KScreen
    }

    public static boolean WriteSettings(String str, Context context) {
        Logger.LogI("ConferenceManagerwrite setting files");
        return ConferenceEngine.WriteSettings(str, context.getPackageName());
    }

    private int getAudioManagerMode() {
        if (ConferenceEngine.getInstance().getAECType() == 2) {
        }
        return 3;
    }

    public static String getEngineVersion() {
        Logger.LogI("ConferenceManager get Engine Version!!!!");
        return ConferenceEngine.getEngineVersion();
    }

    public static int getEngineVersionInt() {
        Logger.LogI("getting getEngineVersionInt");
        return ConferenceEngine.getEngineVersionInt();
    }

    /* access modifiers changed from: private */
    public int getOutPutDevice() {
        if (this.audioManager.isSpeakerphoneOn()) {
            this.audioOutputDevice = 1;
        } else if (this.audioManager.isWiredHeadsetOn()) {
            this.audioOutputDevice = 3;
        } else {
            this.audioOutputDevice = 2;
        }
        return this.audioOutputDevice;
    }

    public long addVideoStream(int i, int i2, VideoContentTypeT videoContentTypeT) {
        Logger.LogI("addVideoStream width:" + i + " height:" + i2 + " type:" + videoContentTypeT.ordinal());
        if (this.mContext != null) {
            return ConferenceEngine.getInstance().addVideoStream(i, i2, videoContentTypeT.ordinal());
        }
        Logger.LogE("ConferenceManageraddVideoStream error, please init the engine first");
        return -1;
    }

    public void destroy() {
        Logger.LogI("destorying voip engine...");
        Context context = this.mContext;
        if (context == null) {
            Logger.LogE("ConferenceManagerdestroy error, please init the engine first");
            return;
        }
        try {
            context.unregisterReceiver(this.audioStateChangeReceiver);
        } catch (Exception unused) {
            Log.e(TAG, "audioStateChangeReceiver is not registered!");
            Logger.LogE("audioStateChangeReceiver is not registered!");
        }
        ConferenceEngine.getInstance().uninitialize();
        this.mContext = null;
        Logger.LogI("destory voip engine done");
    }

    public void enableCameraRotation(boolean z) {
        Logger.LogI("Eanble camera rotate with:" + z);
        if (this.mContext == null) {
            Logger.LogE("ConferenceManagerenableCameraRotation error, please init the engine first");
            return;
        }
        ConferenceEngine.getInstance().enableCameraRotation(z);
        Logger.LogI("set camera rotate done");
    }

    public void enableMonitorTraffic(boolean z) {
        Logger.LogI("setting enableMonitorTraffic " + z);
        ConferenceEngine.getInstance().enableMonitorTraffic(z);
    }

    public int getAECType() {
        return ConferenceEngine.getInstance().getAECType();
    }

    public long getAddress() {
        return ConferenceEngine.getInstance().getAddress();
    }

    public String getIceStat() {
        Logger.LogI("getting ice Stat");
        if (this.mContext != null) {
            return ConferenceEngine.getInstance().getIceStat();
        }
        Logger.LogE("ConferenceManagergetIceStat error, please init the engine first");
        return null;
    }

    public ConferenceEngine.InVideoStat getInVideoStat() {
        return ConferenceEngine.getInstance().getInVideoStat();
    }

    public MonitorData getMonitorData() {
        Logger.LogI("getting monitor data");
        if (this.mContext != null) {
            return ConferenceEngine.getInstance().getMonitorData();
        }
        Logger.LogE("ConferenceManagergetMonitorData error, please init the engine first");
        return null;
    }

    public String getNetworkEnv() {
        if (this.mContext != null) {
            return ConferenceEngine.getInstance().getNetworkEnv(this.mContext);
        }
        Logger.LogE("ConferenceManagerget network environment error, please init the engine first");
        return null;
    }

    public ConferenceEngine.OutVideoStat getOutVideoStat() {
        return ConferenceEngine.getInstance().getOutVideoStat();
    }

    public int getRunHorseTime() {
        Logger.LogI("getting RunHorseTime");
        if (this.mContext != null) {
            return ConferenceEngine.getInstance().getRunHorseTime();
        }
        Logger.LogE("ConferenceManagergetRunHorseTime error, please init the engine first");
        return -1;
    }

    public int getUserPlayoutVolume(String str) {
        Logger.LogI("ConferenceManager get participants name: " + str + " volume!");
        if (this.mContext != null) {
            return ConferenceEngine.getInstance().getUserPlayoutVolume(str);
        }
        Logger.LogE("ConferenceManager getParticipantsVolume error, please init the engine first");
        return 0;
    }

    public boolean init(Context context, long j, String str, ConferenceCallback conferenceCallback) {
        Logger.LogI("Initializing voip engine...");
        if (this.mContext != null) {
            Logger.LogE("ConferenceManagerinit error, please destroy the engine first");
            return false;
        }
        this.audioManager = (AudioManager) context.getSystemService(VEEditor.MVConsts.TYPE_AUDIO);
        String packageName = context.getPackageName();
        getOutPutDevice();
        context.registerReceiver(this.audioStateChangeReceiver, new IntentFilter("android.intent.action.HEADSET_PLUG"));
        if (ConferenceEngine.getInstance().initialize(context, j, str, this.gslb_test_config, conferenceCallback, packageName)) {
            Logger.LogI("Monitor Successed: On load succeeded.");
            this.mContext = context;
            conferenceCallback.onLoad(true);
            return true;
        }
        Logger.LogE("Monitor Failed:On load failed.");
        conferenceCallback.onLoad(false);
        this.mContext = null;
        return false;
    }

    public boolean isInConference() {
        Logger.LogI("ConferenceManagerisInConference");
        if (this.mContext != null) {
            return ConferenceEngine.getInstance().isInConference();
        }
        Logger.LogE("ConferenceManager isInConference error, please init the engine first");
        return false;
    }

    public boolean joinRoom(String str, String str2, boolean z, boolean z2, boolean z3, String str3, String str4, String str5, ConferenceManagerRole conferenceManagerRole, String str6, String str7) {
        Logger.LogI("joining room, roomId:" + str + " server:" + str2);
        Context context = this.mContext;
        if (context == null) {
            Logger.LogE("ConferenceManager joinRoom error, please init the engine first");
            return false;
        }
        this.audioManager = (AudioManager) context.getSystemService(VEEditor.MVConsts.TYPE_AUDIO);
        Build.MODEL.toLowerCase();
        this.audioManager.setMode(getAudioManagerMode());
        if (this.audioManager == null) {
            Logger.LogE("ConferenceManager Could not change audio routing - no audio manager");
            return false;
        }
        String networkEnv = ConferenceEngine.getInstance().getNetworkEnv(this.mContext);
        Logger.LogI("ConferenceManager Current network environment is " + networkEnv);
        ConferenceEngine.getInstance().AutoStartCamera(false);
        if (!ConferenceEngine.getInstance().Join(str2, str, z, z2, z3, str3, str4, str5, conferenceManagerRole.ordinal(), false, str6, str7)) {
            Logger.LogE("ConferenceManagerjoin room failed");
            return false;
        }
        getOutPutDevice();
        Logger.LogI("join room succeeded");
        return true;
    }

    public void leaveRoom() {
        Logger.LogI("leaving conference room");
        if (this.mContext == null) {
            Logger.LogE("ConferenceManagerleaveRoom error, please init the engine first");
        } else if (this.audioManager == null) {
            Logger.LogE("ConferenceManagerCould not change audio routing - no audio manager");
        } else {
            ConferenceEngine.getInstance().Leave();
            Logger.LogI("Set the mode to normal");
            this.audioManager.setMode(0);
            Logger.LogI("leave conference room done");
        }
    }

    public void localParticipantSetDynamicViewPolicy(DynamicViewPolicyT dynamicViewPolicyT) {
        Logger.LogI("setting local participant dynamic view policy");
        if (this.mContext == null) {
            Logger.LogE("ConferenceManagerlocalParticipantSetDynamicViewPolicy error, please init the engine first");
            return;
        }
        ConferenceEngine.getInstance().localParticipantSetDynamicViewPolicy(dynamicViewPolicyT);
        Logger.LogI("set local participant dynamic view policy done");
    }

    public boolean muteMicrophone() {
        Logger.LogI("muting Microphone...");
        if (this.mContext == null) {
            Logger.LogE("ConferenceManagermuteMic error, please init the engine first");
            return false;
        }
        ConferenceEngine.getInstance().MuteMicrophone(true);
        Logger.LogI("mute Microphone succeeded");
        return true;
    }

    public void muteUserAudio(String str, boolean z) {
        Logger.LogI("ConferenceManager set user name: " + str + " mute: " + z);
        if (this.mContext == null) {
            Logger.LogE("ConferenceManager muteUserAudio error, please init the engine first");
        } else {
            ConferenceEngine.getInstance().muteUserAudio(str, z);
        }
    }

    public boolean muteVideo() {
        Logger.LogI("muting video...");
        if (this.mContext == null) {
            Logger.LogE("ConferenceManagermuteVideo error, please init the engine first");
            return false;
        } else if (!ConferenceEngine.getInstance().MuteVideo(true)) {
            Logger.LogE("ConferenceManagermute video failed");
            return false;
        } else {
            Logger.LogI("mute video succeeded");
            return true;
        }
    }

    public void pushVideoFrame(int i, int i2, byte[] bArr, int i3, int i4, int i5, long j, long j2) {
        if (this.mContext == null) {
            Logger.LogE("ConferenceManagerpush extra YUV frame error, please init the engine first");
        } else {
            ConferenceEngine.getInstance().pushVideoFrameJni(i, i2, bArr, i3, i4, i5, j, j2);
        }
    }

    public void removeVideoStream(long j) {
        Logger.LogI("removeVideoStream stream id:" + j);
        if (this.mContext == null) {
            Logger.LogE("ConferenceManagerremoveVideoStream error, please init the engine first");
        } else {
            ConferenceEngine.getInstance().removeVideoStream(j);
        }
    }

    public void setAutoStartDevice(boolean z) {
        Logger.LogI("ConferenceManager setAutoStartDevice " + z);
        if (this.mContext == null) {
            Logger.LogE("ConferenceManagerset AutoStart Device error, please init the engine first");
            return;
        }
        ConferenceEngine.getInstance().AutoStartMicrophone(z);
        ConferenceEngine.getInstance().AutoStartSpeaker(z);
    }

    public void setCallResolutionMode(int i, int i2) {
        Logger.LogI("ConferenceManager setCallResolutionMode to width:" + i + " height:" + i2);
        if (this.mContext == null) {
            Logger.LogE("ConferenceManager setCallResolutionMode error, please init the engine first");
        } else {
            ConferenceEngine.getInstance().setCallResolutionMode(i, i2);
        }
    }

    public int setEncoderMaxBitRate(int i) {
        Logger.LogI("setting encoder max bitrate to " + i);
        if (this.mContext == null) {
            Logger.LogE("ConferenceManagersetEncoderMaxBitRate error, please init the engine first");
            return -1;
        }
        ConferenceEngine.getInstance().setEncoderMaxBitRate(i);
        Logger.LogI("set encoder max bitrate done");
        return 0;
    }

    public boolean setGslbConfig(byte[] bArr) {
        Logger.LogI("setGslbConfig, config_str pb pb_config length :" + bArr.length);
        if (this.mContext == null) {
            Logger.LogE("ConferenceManager setGslbConfig, please init the engine first");
            return false;
        }
        this.gslb_test_config = bArr;
        if (!ConferenceEngine.getInstance().SetGslbConfig(bArr)) {
            Logger.LogE("ConferenceManagersetGslbConfig failed");
            return false;
        }
        Logger.LogI("setGslbConfig succeeded");
        return true;
    }

    public int setLocalNetWork(String str, String str2) {
        Logger.LogI("setting local netWork to " + str + " netID: " + str2);
        if (this.mContext == null) {
            Logger.LogE("ConferenceManagersetLocalNetwork error, please init the engine first");
            return -1;
        }
        ConferenceEngine.getInstance().setLocalNetWork(str, str2);
        Logger.LogI("set local netWork done");
        return 0;
    }

    public void setMirrorCamera(boolean z) {
        Logger.LogI("ConferenceManager setMirrorCamera :" + z);
        if (this.mContext == null) {
            Logger.LogE("ConferenceManager setMirrorCamera error, please init the engine first");
        } else {
            ConferenceEngine.getInstance().setMirrorCamera(z);
        }
    }

    public void setPlayoutVolume(int i) {
        Logger.LogI("ConferenceManager setPlayoutVolume:" + i);
        if (this.mContext == null) {
            Logger.LogE("ConferenceManager setVolume error, please init the engine first");
        } else {
            ConferenceEngine.getInstance().setPlayoutVolume(i);
        }
    }

    public int setPowerStatus(int i, boolean z) {
        Logger.LogI("setting power status: power left to " + i + " isCharge: " + z);
        if (this.mContext == null) {
            Logger.LogE("ConferenceManagersetPowerStatus error, please init the engine first");
            return -1;
        }
        ConferenceEngine.getInstance().setPowerStatus(i, z);
        Logger.LogI("set power status done");
        return 0;
    }

    public int setRemoteNetWork(String str, String str2) {
        Logger.LogI("setting remotel netWork to " + str + " netID: " + str2);
        if (this.mContext == null) {
            Logger.LogE("ConferenceManagersetRemoteNetwork error, please init the engine first");
            return -1;
        }
        ConferenceEngine.getInstance().setRemoteNetWork(str, str2);
        Logger.LogI("set remote netWork done");
        return 0;
    }

    public boolean setScreenFps(int i) {
        Logger.LogI("set Screen fps to:" + i);
        if (this.mContext == null) {
            Logger.LogE("ConferenceManagersetScreenFps error, please init the engine first");
            return false;
        } else if (!ConferenceEngine.getInstance().setScreenFps(i)) {
            Logger.LogE("ConferenceManagerset screen fps false");
            return false;
        } else {
            Logger.LogI("set screen fps done");
            return true;
        }
    }

    public boolean setScreenResolution(int i, int i2) {
        Logger.LogI("set Screen Resotuon to:" + i + "x" + i2);
        if (this.mContext == null) {
            Logger.LogE("ConferenceManagersetScreenResolution error, please init the engine first");
            return false;
        } else if (!ConferenceEngine.getInstance().setScreenResolution(i, i2)) {
            Logger.LogE("ConferenceManagerset screen resolution false");
            return false;
        } else {
            Logger.LogI("set screen resolution done");
            return true;
        }
    }

    public void setUserPlayoutVolume(String str, double d2) {
        Logger.LogI("ConferenceManager set user name: " + str + " volume: " + d2);
        if (this.mContext == null) {
            Logger.LogE("ConferenceManager setVolume error, please init the engine first");
        } else {
            ConferenceEngine.getInstance().setUserPlayoutVolume(str, d2);
        }
    }

    public void startSpeakingMonitor() {
        Logger.LogI("ConferenceManager startSpeakingMonitor ");
        if (this.mContext == null) {
            Logger.LogE("ConferenceManager startSpeakingMonitor error, please init the engine first");
        } else {
            ConferenceEngine.getInstance().startSpeakingMonitor();
        }
    }

    public boolean startVideo() {
        Logger.LogI("starting video...");
        if (this.mContext == null) {
            Logger.LogE("ConferenceManagerstartVideo error, please init the engine first");
            return false;
        } else if (!ConferenceEngine.getInstance().StartVideo(true)) {
            Logger.LogE("ConferenceManagerstart video failed");
            return false;
        } else {
            Logger.LogI("start video succeeded");
            return true;
        }
    }

    public void startVolumeMonitor(int i) {
        Logger.LogI("ConferenceManager startVolumeMonitor, monitorInterval: " + i);
        if (this.mContext == null) {
            Logger.LogE("ConferenceManager startVolumeMonitor error, please init the engine first");
        } else {
            ConferenceEngine.getInstance().startVolumeMonitor(i);
        }
    }

    public void stopSpeakingMonitor() {
        Logger.LogI("ConferenceManager stopSpeakingMonitor ");
        if (this.mContext == null) {
            Logger.LogE("ConferenceManager stopSpeakingMonitor error, please init the engine first");
        } else {
            ConferenceEngine.getInstance().stopSpeakingMonitor();
        }
    }

    public boolean stopVideo() {
        Logger.LogI("stopping video...");
        if (this.mContext == null) {
            Logger.LogE("ConferenceManagerstopVideo error, please init the engine first");
            return false;
        } else if (!ConferenceEngine.getInstance().StartVideo(false)) {
            Logger.LogE("ConferenceManagerstop video failed");
            return false;
        } else {
            Logger.LogI("stop video succeeded");
            return true;
        }
    }

    public void stopVolumeMonitor() {
        Logger.LogI("ConferenceManager stopVolumeMonitor ");
        if (this.mContext == null) {
            Logger.LogE("ConferenceManager stopVolumeMonitor error, please init the engine first");
        } else {
            ConferenceEngine.getInstance().stopVolumeMonitor();
        }
    }

    public boolean switchVideoContent(VideoContentTypeT videoContentTypeT) {
        Logger.LogI("switch video content type to:" + videoContentTypeT);
        if (this.mContext == null) {
            Logger.LogE("ConferenceManagerswitch video content error, please init the engine first");
            return false;
        } else if (!ConferenceEngine.getInstance().switchVideoContent(videoContentTypeT)) {
            Logger.LogE("ConferenceManagerswitch video content false");
            return false;
        } else {
            Logger.LogI("switch video content done");
            return true;
        }
    }

    public boolean unMuteMicrophone() {
        Logger.LogI("unmuting unMuteMicrophone...");
        if (this.mContext == null) {
            Logger.LogE("ConferenceManagerunMuteMic error, please init the engine first");
            return false;
        }
        ConferenceEngine.getInstance().MuteMicrophone(false);
        Logger.LogI("unmute unMuteMicrophone succeeded");
        return true;
    }

    public boolean unMuteVideo() {
        Logger.LogI("unmuting video...");
        if (this.mContext == null) {
            Logger.LogE("ConferenceManagerunMuteVideo error, please init the engine first");
            return false;
        } else if (!ConferenceEngine.getInstance().MuteVideo(false)) {
            Logger.LogE("ConferenceManagerunmute video failed");
            return false;
        } else {
            Logger.LogI("unmute video succeeded");
            return true;
        }
    }
}
