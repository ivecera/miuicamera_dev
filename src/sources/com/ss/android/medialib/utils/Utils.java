package com.ss.android.medialib.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import com.ss.android.vesdk.VEEditor;

public class Utils {
    private static final String TAG = "Utils";

    public static Pair<Integer, Integer> getSystemAudioInfo(Context context) {
        PackageManager packageManager = context.getPackageManager();
        boolean hasSystemFeature = packageManager == null ? false : packageManager.hasSystemFeature("android.hardware.audio.low_latency");
        String str = TAG;
        Log.d(str, "has low latency ? " + hasSystemFeature);
        int i = 44100;
        int i2 = 256;
        if (Build.VERSION.SDK_INT >= 17) {
            AudioManager audioManager = (AudioManager) context.getSystemService(VEEditor.MVConsts.TYPE_AUDIO);
            i = str2Int(audioManager.getProperty("android.media.property.OUTPUT_SAMPLE_RATE"), 44100);
            i2 = str2Int(audioManager.getProperty("android.media.property.OUTPUT_FRAMES_PER_BUFFER"), 256);
        }
        return new Pair<>(Integer.valueOf(i), Integer.valueOf(i2));
    }

    public static int str2Int(String str, int i) {
        if (TextUtils.isEmpty(str)) {
            return i;
        }
        try {
            return Integer.parseInt(str);
        } catch (Exception unused) {
            return i;
        }
    }
}
