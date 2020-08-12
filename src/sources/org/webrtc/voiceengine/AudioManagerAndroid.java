package org.webrtc.voiceengine;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHeadset;
import android.bluetooth.BluetoothProfile;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.Build;
import android.util.Log;
import com.ss.android.vesdk.VEEditor;

class AudioManagerAndroid implements HeadsetPlugHandler {
    private static final int DEFAULT_FRAMES_PER_BUFFER = 256;
    private static final int DEFAULT_SAMPLING_RATE = 44100;
    private AudioManager audioManager;
    private final Context context;
    private HeadsetPlugReceiver headsetPlugReceiver;
    private int mAudioLowLatencyOutputFrameSize = 256;
    private boolean mAudioLowLatencySupported;
    private int mNativeOutputSampleRate = DEFAULT_SAMPLING_RATE;
    private long native_manager;

    private class HeadsetPlugReceiver extends BroadcastReceiver {
        public static final String ACTION_MEDIA_KEYEVENT_HEADSETHOOT_RECEIVER = "action_media_keyevent_headsethoot_recevier";
        public static final int MSG_ACCEPT_BY_EARPHONE = 202;
        public static final int MSG_BLUETOOTH_HEADSET_CONNECTED = 210;
        public static final int MSG_BLUETOOTH_HEADSET_DISCONNECTED = 211;
        public static final int MSG_EARPHONE_PLUGGED = 200;
        public static final int MSG_EARPHONE_UNPLUGGED = 201;
        public static final int MSG_SCO_AUDIO_STATE_CONNECTED = 212;
        public static final int MSG_SCO_AUDIO_STATE_DISCONNECTED = 213;
        private HeadsetPlugHandler callback;
        private final Context context;
        private AudioManager mAudioManager = null;
        /* access modifiers changed from: private */
        public BluetoothHeadset mBluetoothHeadset;
        private boolean mIsBluetoothHeadsetAvailable = false;
        private BluetoothProfile.ServiceListener mProfileListener = new BluetoothProfile.ServiceListener() {
            /* class org.webrtc.voiceengine.AudioManagerAndroid.HeadsetPlugReceiver.AnonymousClass1 */

            public void onServiceConnected(int i, BluetoothProfile bluetoothProfile) {
                if (i == 1) {
                    Log.i("AudioManagerAndroid", "updateAudioDevice onServiceConnected");
                    BluetoothHeadset unused = HeadsetPlugReceiver.this.mBluetoothHeadset = (BluetoothHeadset) bluetoothProfile;
                    if (HeadsetPlugReceiver.this.isBluetoothHeadsetConnected()) {
                        HeadsetPlugReceiver.this.HeadsetMessageHandler(210);
                    }
                }
            }

            public void onServiceDisconnected(int i) {
                if (i == 1) {
                    Log.i("AudioManagerAndroid", "updateAudioDevice onServiceDisconnected");
                    HeadsetPlugReceiver.this.HeadsetMessageHandler(213);
                    BluetoothHeadset unused = HeadsetPlugReceiver.this.mBluetoothHeadset = null;
                }
            }
        };

        HeadsetPlugReceiver(HeadsetPlugHandler headsetPlugHandler, Context context2) {
            Log.i("AudioManagerAndroid", "HeadsetPlugReceiver ");
            this.callback = headsetPlugHandler;
            this.context = context2;
            this.mAudioManager = (AudioManager) context2.getSystemService(VEEditor.MVConsts.TYPE_AUDIO);
            try {
                BluetoothAdapter.getDefaultAdapter().getProfileProxy(context2, this.mProfileListener, 1);
            } catch (SecurityException unused) {
                Log.i("AudioManagerAndroid", "The app  bluetooth permissions denied");
            }
            IntentFilter intentFilter = new IntentFilter(ACTION_MEDIA_KEYEVENT_HEADSETHOOT_RECEIVER);
            intentFilter.addAction("android.intent.action.HEADSET_PLUG");
            intentFilter.addAction("android.bluetooth.headset.profile.action.CONNECTION_STATE_CHANGED");
            intentFilter.addAction("android.media.ACTION_SCO_AUDIO_STATE_UPDATED");
            intentFilter.setPriority(1000);
            context2.registerReceiver(this, intentFilter);
        }

        public void HeadsetMessageHandler(int i) {
            if (i == 200) {
                Log.i("AudioManagerAndroid", "updateAudioDevice: MSG_EARPHONE_PLUGGED");
                this.callback.onHeadsetPlugChange(true);
            } else if (i != 201) {
                switch (i) {
                    case 210:
                        Log.i("AudioManagerAndroid", "updateAudioDevice: MSG_BLUETOOTH_HEADSET_CONNECTED");
                        return;
                    case 211:
                        Log.i("AudioManagerAndroid", "updateAudioDevice: MSG_BLUETOOTH_HEADSET_DISCONNECTED");
                        return;
                    case 212:
                        Log.i("AudioManagerAndroid", "updateAudioDevice: MSG_SCO_AUDIO_STATE_CONNECTED");
                        this.mIsBluetoothHeadsetAvailable = true;
                        this.mAudioManager.setBluetoothScoOn(true);
                        this.callback.onBluetoothHeadsetPlugChange(true);
                        return;
                    case 213:
                        Log.i("AudioManagerAndroid", "updateAudioDevice: MSG_SCO_AUDIO_STATE_DISCONNECTED");
                        if (this.mIsBluetoothHeadsetAvailable) {
                            this.mIsBluetoothHeadsetAvailable = false;
                            this.callback.onBluetoothHeadsetPlugChange(false);
                            return;
                        }
                        return;
                    default:
                        return;
                }
            } else {
                Log.i("AudioManagerAndroid", "updateAudioDevice: MSG_EARPHONE_UNPLUGGED");
                if (!this.mAudioManager.isBluetoothA2dpOn()) {
                    this.callback.onHeadsetPlugChange(false);
                }
            }
        }

        public void destroy() {
            Log.i("AudioManagerAndroid", "destroy ");
            this.context.unregisterReceiver(this);
            if (this.mIsBluetoothHeadsetAvailable) {
                this.mAudioManager.setMode(0);
            }
        }

        public boolean isBluetoothHeadsetConnected() {
            BluetoothHeadset bluetoothHeadset = this.mBluetoothHeadset;
            if (bluetoothHeadset == null) {
                return false;
            }
            for (BluetoothDevice bluetoothDevice : bluetoothHeadset.getConnectedDevices()) {
                if (this.mBluetoothHeadset.getConnectionState(bluetoothDevice) == 2) {
                    return true;
                }
            }
            return false;
        }

        public void onReceive(Context context2, Intent intent) {
            if (this.callback != null) {
                int i = -1;
                if ("android.intent.action.HEADSET_PLUG".equals(intent.getAction())) {
                    int intExtra = intent.getIntExtra("state", 0);
                    if (intExtra == 0) {
                        i = 201;
                    } else if (intExtra == 1) {
                        i = 200;
                    }
                } else if ("android.bluetooth.headset.profile.action.CONNECTION_STATE_CHANGED".equals(intent.getAction())) {
                    int intExtra2 = intent.getIntExtra("android.bluetooth.profile.extra.STATE", 10);
                    if (intExtra2 == 0) {
                        i = 211;
                    } else if (intExtra2 == 2) {
                        i = 210;
                    }
                } else if ("android.media.ACTION_SCO_AUDIO_STATE_UPDATED".equals(intent.getAction())) {
                    int intExtra3 = intent.getIntExtra("android.media.extra.SCO_AUDIO_STATE", 0);
                    if (intExtra3 == 0) {
                        i = 213;
                    } else if (intExtra3 == 1) {
                        i = 212;
                    }
                }
                HeadsetMessageHandler(i);
            }
        }
    }

    private AudioManagerAndroid(Context context2) {
        this.context = context2;
        AudioManager audioManager2 = (AudioManager) context2.getSystemService(VEEditor.MVConsts.TYPE_AUDIO);
        if (Build.VERSION.SDK_INT >= 17) {
            String property = audioManager2.getProperty("android.media.property.OUTPUT_SAMPLE_RATE");
            if (property != null) {
                this.mNativeOutputSampleRate = Integer.parseInt(property);
            }
            String property2 = audioManager2.getProperty("android.media.property.OUTPUT_FRAMES_PER_BUFFER");
            if (property2 != null) {
                this.mAudioLowLatencyOutputFrameSize = Integer.parseInt(property2);
            }
        }
        this.mAudioLowLatencySupported = context2.getPackageManager().hasSystemFeature("android.hardware.audio.low_latency");
        registerHeadsetPlugReceiver(context2);
    }

    private native void bluetoothHeadsetPlugStateChanged(boolean z, long j);

    private int getAudioLowLatencyOutputFrameSize() {
        return this.mAudioLowLatencyOutputFrameSize;
    }

    private int getNativeOutputSampleRate() {
        return this.mNativeOutputSampleRate;
    }

    private native void headsetPlugStateChanged(boolean z, long j);

    private boolean isAudioLowLatencySupported() {
        return this.mAudioLowLatencySupported;
    }

    private void registerHeadsetPlugReceiver(Context context2) {
        this.headsetPlugReceiver = new HeadsetPlugReceiver(this, context2);
    }

    public synchronized void bindNativeObjectNativeManager(long j) {
        this.native_manager = j;
    }

    /* access modifiers changed from: protected */
    public void finalize() {
        try {
            Log.i("AudioManagerAndroid", "Unregister the plugin event receiver.");
            this.headsetPlugReceiver.destroy();
        } catch (Exception unused) {
        }
    }

    @Override // org.webrtc.voiceengine.HeadsetPlugHandler
    public synchronized void onBluetoothHeadsetPlugChange(boolean z) {
        bluetoothHeadsetPlugStateChanged(z, this.native_manager);
    }

    @Override // org.webrtc.voiceengine.HeadsetPlugHandler
    public synchronized void onHeadsetPlugChange(boolean z) {
        headsetPlugStateChanged(z, this.native_manager);
    }

    public void setLoudspeakerStatus(boolean z) {
        AudioManager audioManager2 = (AudioManager) this.context.getSystemService(VEEditor.MVConsts.TYPE_AUDIO);
        audioManager2.setMicrophoneMute(false);
        boolean isSpeakerphoneOn = audioManager2.isSpeakerphoneOn();
        if (z) {
            if (!isSpeakerphoneOn) {
                Log.i("AudioManagerAndroid", "setSpeakerphoneOn true");
                audioManager2.setSpeakerphoneOn(true);
            }
        } else if (isSpeakerphoneOn) {
            Log.i("AudioManagerAndroid", "setSpeakerphoneOn false");
            audioManager2.setSpeakerphoneOn(false);
        }
    }

    public int setSpeakerVolume(int i) {
        Log.i("set speaker volume:", "set volume level:" + Integer.toString(i));
        ((AudioManager) this.context.getSystemService(VEEditor.MVConsts.TYPE_AUDIO)).setStreamVolume(3, i, 4);
        return 0;
    }
}
