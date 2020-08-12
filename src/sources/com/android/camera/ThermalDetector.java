package com.android.camera;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.SystemProperties;
import android.text.TextUtils;
import com.android.camera.data.DataRepository;
import com.android.camera.log.Log;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class ThermalDetector {
    public static String ACTION_TEMP_CHANGED = "action_temp_state_change";
    public static String EXTRA_TEMP_STAGE = "temp_state";
    public static final int GETSTAGE_CLOSE_BOTH = 3;
    public static final int STAGE_CLOSE_FLASH = 4;
    public static final int STAGE_CLOSE_FRONT = 2;
    public static final int STAGE_CONTRAINT = 1;
    public static final int STAGE_FREE = 0;
    public static final int STAGE_INVALID = -1;
    /* access modifiers changed from: private */
    public static final String TAG = "ThermalDetector";
    public static String TEMP_STAGE_NODE = "/sys/class/thermal/thermal_message/temp_state";
    private Context mContext;
    private IntentFilter mFilter;
    private boolean mIsRegister;
    private OnThermalNotificationListener mListener;
    private BroadcastReceiver mReceiver;
    /* access modifiers changed from: private */
    public volatile int mTempStage;

    private static class InstanceHolder {
        /* access modifiers changed from: private */
        public static ThermalDetector sInstance = new ThermalDetector();

        private InstanceHolder() {
        }
    }

    public interface OnThermalNotificationListener {
        void onThermalNotification(int i);
    }

    private ThermalDetector() {
        this.mTempStage = 0;
        this.mIsRegister = false;
        this.mFilter = new IntentFilter(ACTION_TEMP_CHANGED);
        this.mReceiver = new BroadcastReceiver() {
            /* class com.android.camera.ThermalDetector.AnonymousClass1 */

            public void onReceive(Context context, Intent intent) {
                int currentMode;
                int i = 0;
                if (!SystemProperties.getBoolean("camera.test.auto", false) && intent != null && TextUtils.equals(intent.getAction(), ThermalDetector.ACTION_TEMP_CHANGED) == 1) {
                    int intExtra = intent.getIntExtra(ThermalDetector.EXTRA_TEMP_STAGE, -1);
                    String access$200 = ThermalDetector.TAG;
                    Log.d(access$200, "onReceive stage = " + intExtra);
                    int i2 = intExtra % 10;
                    if (!DataRepository.dataItemFeature().pb() || !(((currentMode = DataRepository.dataItemGlobal().getCurrentMode()) == 163 || currentMode == 165 || currentMode == 167 || currentMode == 175 || currentMode == 177 || currentMode == 182 || currentMode == 184) && i2 == 4)) {
                        i = i2;
                    }
                    if (ThermalDetector.this.mTempStage != i) {
                        int unused = ThermalDetector.this.mTempStage = i;
                        ThermalDetector thermalDetector = ThermalDetector.this;
                        thermalDetector.onThermalNotification(thermalDetector.mTempStage);
                    }
                }
            }
        };
    }

    public static ThermalDetector getInstance() {
        return InstanceHolder.sInstance;
    }

    public static boolean isTempStageFree(int i) {
        return (i == 0 || i == -1) ? true : false;
    }

    /* access modifiers changed from: private */
    public void onThermalNotification(int i) {
        OnThermalNotificationListener onThermalNotificationListener;
        String str = TAG;
        Log.d(str, "onThermalNotification stage=" + i);
        if (i != -1 && (onThermalNotificationListener = this.mListener) != null) {
            onThermalNotificationListener.onThermalNotification(i);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0031, code lost:
        r3 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:?, code lost:
        r1.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0036, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0037, code lost:
        r2.addSuppressed(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x003a, code lost:
        throw r3;
     */
    private static int readStageFromFile() {
        String str = "";
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(TEMP_STAGE_NODE))));
            StringBuffer stringBuffer = new StringBuffer();
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine == null) {
                    break;
                }
                stringBuffer.append(readLine);
            }
            str = stringBuffer.toString();
            bufferedReader.close();
        } catch (IOException unused) {
            Log.e(TAG, "readStageFromFile IOException");
        }
        try {
            int parseInt = Integer.parseInt(str);
            String str2 = TAG;
            Log.d(str2, "readStageFromFile value = " + parseInt);
            if (parseInt < 0 || parseInt > 3) {
                return 0;
            }
            return parseInt;
        } catch (Exception e2) {
            Log.d(TAG, "failed to readStageFromFile ", e2);
            return 0;
        }
    }

    public static boolean thermalCloseFlash(int i) {
        return (i == 4 || i == 1) ? true : false;
    }

    public static boolean thermalConstrained(int i) {
        return i == 1 ? true : false;
    }

    public void onCreate(Context context) {
        Log.d(TAG, "onCreate");
        this.mContext = context.getApplicationContext();
    }

    public void onDestroy() {
        Log.d(TAG, "onDestroy");
    }

    public void onThermalNotification() {
        onThermalNotification(this.mTempStage);
    }

    public void registerReceiver(OnThermalNotificationListener onThermalNotificationListener) {
        Log.d(TAG, "registerReceiver");
        this.mListener = onThermalNotificationListener;
        Context context = this.mContext;
        if (context != null && !this.mIsRegister) {
            context.registerReceiver(this.mReceiver, this.mFilter);
            this.mIsRegister = true;
        }
    }

    public boolean thermalCloseFlash() {
        return (this.mTempStage == 4 || this.mTempStage == 1) ? true : false;
    }

    public boolean thermalConstrained() {
        return this.mTempStage == 1 ? true : false;
    }

    public void unregisterReceiver() {
        Log.d(TAG, "unregisterReceiver");
        this.mListener = null;
        Context context = this.mContext;
        if (context != null && this.mIsRegister == 1) {
            context.unregisterReceiver(this.mReceiver);
            this.mIsRegister = false;
            this.mTempStage = 0;
        }
    }
}
