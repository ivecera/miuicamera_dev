package com.android.camera.module;

import android.content.Context;
import android.media.AudioManager;
import com.android.camera.log.Log;
import com.ss.android.vesdk.VEEditor;

public class AudioController {
    private static final String TAG = "AudioController";
    AudioManager mAudioManager = ((AudioManager) this.mContext.getSystemService(VEEditor.MVConsts.TYPE_AUDIO));
    Context mContext;

    public AudioController(Context context) {
        this.mContext = context.getApplicationContext();
    }

    public void restoreAudio() {
        Log.d(TAG, "restoreAudio: ");
        this.mAudioManager.abandonAudioFocus(null);
    }

    public void silenceAudio() {
        Log.d(TAG, "silenceAudio: ");
        this.mAudioManager.requestAudioFocus(null, 3, 2);
    }

    public void stopAudio() {
        Log.d(TAG, "stopAudio: ");
        this.mAudioManager.requestAudioFocus(null, 3, 1);
    }
}
