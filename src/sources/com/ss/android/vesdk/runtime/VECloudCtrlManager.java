package com.ss.android.vesdk.runtime;

import android.content.Context;
import android.os.Environment;
import android.support.annotation.NonNull;
import com.android.camera.statistic.MistatsConstants;
import com.ss.android.ttve.common.TECloudCtrlInvoker;
import com.ss.android.ttve.common.TESpdLogManager;
import com.ss.android.vesdk.VELogUtil;
import com.ss.android.vesdk.runtime.persistence.VESP;
import com.xiaomi.stat.MiStat;
import java.text.SimpleDateFormat;
import miui.reflect.b;
import org.json.JSONObject;

public class VECloudCtrlManager {
    private static String[] COMMANDS = {"vesdk_log_command"};
    private static String TAG = "VECloudCtrlManager";
    private static volatile VECloudCtrlManager mTECloudCtrlManager;
    private TECloudCtrlInvoker mCloudCtrlInvoker;
    private boolean mLogStatus;
    private String mWorkpsace;

    private VECloudCtrlManager() {
        this.mLogStatus = false;
        this.mWorkpsace = Environment.getExternalStorageDirectory().toString();
        this.mLogStatus = false;
        this.mCloudCtrlInvoker = new TECloudCtrlInvoker();
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x0030 A[Catch:{ Exception -> 0x0170 }] */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x003b A[Catch:{ Exception -> 0x0170 }] */
    private int doCommand(@NonNull String str, @NonNull String str2) {
        boolean z;
        try {
            JSONObject jSONObject = new JSONObject(str2);
            if (str.hashCode() == 2043549244) {
                if (str.equals("vesdk_log_command")) {
                    z = false;
                    if (!z) {
                        VELogUtil.e(TAG, " json contail invalid command ");
                        return -2;
                    }
                    String string = jSONObject.has("enable") ? jSONObject.getString("enable") : MistatsConstants.BaseEvent.VALUE_FALSE;
                    String str3 = "2018-12-08 00:00:00";
                    String string2 = jSONObject.has("starttime") ? jSONObject.getString("starttime") : str3;
                    if (jSONObject.has("endtime")) {
                        str3 = jSONObject.getString("endtime");
                    }
                    String string3 = jSONObject.has(MiStat.Param.LEVEL) ? jSONObject.getString(MiStat.Param.LEVEL) : "";
                    if (!this.mCloudCtrlInvoker.verifyJson(str + string + string2 + str3 + string3, jSONObject.has("sign") ? jSONObject.getString("sign") : "")) {
                        storeCommand(str, "");
                        VELogUtil.e(TAG, "Cloud Ctrl Command Json is doctored");
                        return -1;
                    }
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    long time = simpleDateFormat.parse(string2).getTime();
                    long time2 = simpleDateFormat.parse(str3).getTime();
                    long currentTimeMillis = System.currentTimeMillis();
                    TESpdLogManager.InfoLevel infoLevel = TESpdLogManager.InfoLevel.LEVEL3;
                    TESpdLogManager.InfoLevel infoLevel2 = (string3.hashCode() == 68 && string3.equals(b.ly)) ? false : true ? TESpdLogManager.InfoLevel.LEVEL3 : TESpdLogManager.InfoLevel.LEVEL0;
                    if (!this.mLogStatus && string.equals(MistatsConstants.BaseEvent.VALUE_TRUE) && currentTimeMillis >= time && currentTimeMillis < time2) {
                        String str4 = this.mWorkpsace + "/vesdklog";
                        VELogUtil.d(TAG, str4);
                        int initSpdLog = TESpdLogManager.getInstance().initSpdLog(str4, infoLevel2.ordinal(), 3);
                        if (initSpdLog < 0) {
                            VELogUtil.e(TAG, " TESpdLog init fail " + initSpdLog);
                            return -3;
                        }
                        TESpdLogManager.getInstance().setLevel(infoLevel2);
                        this.mLogStatus = true;
                    } else if (string.equals(MistatsConstants.BaseEvent.VALUE_FALSE) || (currentTimeMillis < time && currentTimeMillis >= time2)) {
                        if (this.mLogStatus) {
                            TESpdLogManager.getInstance().close();
                            this.mLogStatus = false;
                        }
                        VELogUtil.d(TAG, str + " expired");
                        storeCommand(str, "");
                    }
                    return 0;
                }
            }
            z = true;
            if (!z) {
            }
        } catch (Exception e2) {
            VELogUtil.e(TAG, " json parse failed " + e2.toString());
            storeCommand(str, "");
            return -1;
        }
    }

    public static VECloudCtrlManager getInstance() {
        if (mTECloudCtrlManager == null) {
            synchronized (VECloudCtrlManager.class) {
                if (mTECloudCtrlManager == null) {
                    mTECloudCtrlManager = new VECloudCtrlManager();
                }
            }
        }
        return mTECloudCtrlManager;
    }

    private void storeCommand(@NonNull String str, @NonNull String str2) {
        VESP.getInstance().put(str, str2);
    }

    public void closeCloudControlRes() {
        if (this.mLogStatus) {
            TESpdLogManager.getInstance().close();
            this.mLogStatus = false;
        }
    }

    public void execStoredCommands(@NonNull String str) {
        this.mWorkpsace = str;
        String[] strArr = COMMANDS;
        for (String str2 : strArr) {
            String str3 = (String) VESP.getInstance().get(str2, "");
            if (!str3.isEmpty()) {
                doCommand(str2, str3);
            }
        }
    }

    public void storeCloudControlCommand(@NonNull Context context, @NonNull String str) {
        VELogUtil.d(TAG, str);
        try {
            JSONObject jSONObject = new JSONObject(str);
            String string = jSONObject.getString("command");
            VESP.getInstance().init(context);
            storeCommand(string, jSONObject.toString());
        } catch (Exception e2) {
            String str2 = TAG;
            VELogUtil.e(str2, " json parse failed " + e2.toString());
        }
    }
}
