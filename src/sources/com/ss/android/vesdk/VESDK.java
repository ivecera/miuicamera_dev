package com.ss.android.vesdk;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import com.bef.effectsdk.ResourceFinder;
import com.ss.android.medialib.VideoSdkCore;
import com.ss.android.medialib.common.LogUtil;
import com.ss.android.ttve.log.TELog2Client;
import com.ss.android.ttve.monitor.MonitorUtils;
import com.ss.android.ttve.monitor.TEMonitor;
import com.ss.android.ttve.nativePort.TEEffectUtils;
import com.ss.android.ttve.nativePort.TENativeLibsLoader;
import com.ss.android.vesdk.VEListener;
import com.ss.android.vesdk.runtime.VECloudCtrlManager;
import com.ss.android.vesdk.runtime.VEEnv;
import com.ss.android.vesdk.runtime.VEExternalMonitorListener;
import com.ss.android.vesdk.runtime.VERuntime;
import com.ss.android.vesdk.runtime.cloudconfig.PerformanceConfig;
import com.ss.android.vesdk.runtime.persistence.VESP;
import com.ss.android.vesdk.vecore.BuildConfig;

public class VESDK {
    public static int MONITOR_ACTION_CANCEL = TEMonitor.MONITOR_ACTION_CANCEL;

    public static void deInit() {
        VECloudCtrlManager.getInstance().closeCloudControlRes();
    }

    public static void enableGLES3(boolean z) {
        VERuntime.getInstance().enableGLES3(z);
        VideoSdkCore.enableGLES3(z);
    }

    public static void enableHDH264HWDecoder(boolean z, int i) throws VEException {
        int enableHDH264HWDecoder = VERuntime.getInstance().enableHDH264HWDecoder(z, i);
        if (enableHDH264HWDecoder == -108) {
            throw new VEException(enableHDH264HWDecoder, "please set VEEnv or VEEnv#init");
        }
    }

    public static void enableTT265Decoder(boolean z) throws VEException {
        int enableTT265Decoder = VERuntime.getInstance().enableTT265Decoder(z);
        if (enableTT265Decoder == -108) {
            throw new VEException(enableTT265Decoder, "please set VEEnv or VEEnv#init");
        }
    }

    public static String getCurrentLoadModule() {
        return BuildConfig.FLAVOR;
    }

    public static String getEffectSDKVer() {
        String effectVersion = TEEffectUtils.getEffectVersion();
        Log.d("Steven", "Effect Ver is : " + effectVersion);
        return effectVersion;
    }

    public static int getVeLoadLib() {
        return TENativeLibsLoader.getLibraryLoadedVersion();
    }

    @Deprecated
    public static void init(@NonNull Context context, @NonNull VEEnv vEEnv) {
        String str = context.getExternalFilesDir(null).getAbsolutePath().toString();
        VERuntime.getInstance().init(context, vEEnv);
        VECloudCtrlManager.getInstance().execStoredCommands(str);
    }

    public static void init(@NonNull Context context, @NonNull String str) {
        VERuntime.getInstance().init(context, str);
        VECloudCtrlManager.getInstance().execStoredCommands(str);
    }

    public static void monitorClear() {
        TEMonitor.clear();
    }

    public static void monitorRegister(VEListener.VEMonitorListener vEMonitorListener) {
        VERuntime.getInstance().registerMonitor(vEMonitorListener);
    }

    public static void monitorReport(int i) {
        TEMonitor.report(i);
    }

    public static boolean needUpdateEffectModelFiles() throws VEException {
        int needUpdateEffectModelFiles = VERuntime.getInstance().needUpdateEffectModelFiles();
        if (needUpdateEffectModelFiles != -108) {
            return needUpdateEffectModelFiles == 0;
        }
        throw new VEException(needUpdateEffectModelFiles, "please set setWorkspace in VESDK init");
    }

    public static void registerLogger(VELogProtocol vELogProtocol) {
        TELog2Client.registerLogger(vELogProtocol);
        TELog2Client.init();
        TELog2Client.setLog2ClientSwitch(true);
    }

    @Deprecated
    public static void setAB(@NonNull VEAB veab) {
        VERuntime.getInstance().setAB(veab);
    }

    public static void setAppFiled(@NonNull VEAppField vEAppField) {
        MonitorUtils.setUserId(vEAppField.userId);
        MonitorUtils.setDeviceId(vEAppField.deviceId);
        VESP.getInstance().put(VESP.KEY_DEVICEID, vEAppField.deviceId, true);
        MonitorUtils.setAppVersion(vEAppField.version);
    }

    public static boolean setAssetManagerEnable(boolean z) {
        return VERuntime.getInstance().setAssetManagerEnable(z);
    }

    public static void setCloudConfigEnable(boolean z) {
        VERuntime.getInstance().setCloudConfigEnable(z);
    }

    public static void setCloudConfigServer(int i) {
        PerformanceConfig.setServerLocation(i);
    }

    public static void setEffectAmazingShareDir(@NonNull String str) {
        VERuntime.getInstance().setEffectAmazingShareDir(str);
    }

    public static void setEffectModelsPath(@NonNull String str) throws VEException {
        int effectModelsPath = VERuntime.getInstance().setEffectModelsPath(str);
        if (effectModelsPath == -108) {
            throw new VEException(effectModelsPath, "please set VEEnv or VEEnv#init");
        }
    }

    public static void setEffectResourceFinder(@NonNull ResourceFinder resourceFinder) {
        VERuntime.getInstance().setEffectResourceFinder(resourceFinder);
    }

    public static void setExternalMonitorListener(@NonNull VEExternalMonitorListener vEExternalMonitorListener) {
        VERuntime.getInstance().setExternalMonitorListener(vEExternalMonitorListener);
    }

    public static void setLogLevel(byte b2) {
        VELogUtil.setUp(null, b2);
        VideoSdkCore.setLogLevel(LogUtil.getAndroidLogLevel(b2));
    }

    public static void setMonitorServer(int i) {
        MonitorUtils.setServerLocation(i);
    }

    public static void setSDKMonitorEnable(boolean z) {
        TEMonitor.setSDKMonitorEnable(z);
    }

    public static void setVeLoadLib(int i) {
        boolean z = true;
        if (i != 1) {
            z = false;
        }
        TENativeLibsLoader.enableLoadOptLibrary(z);
    }

    public static void transfCloudControlCommand(@NonNull Context context, @NonNull String str) {
        VECloudCtrlManager.getInstance().storeCloudControlCommand(context, str);
    }

    public static void updateEffectModelFiles() throws VEException {
        if (needUpdateEffectModelFiles()) {
            int updateEffectModelFiles = VERuntime.getInstance().updateEffectModelFiles();
            if (updateEffectModelFiles == -108) {
                throw new VEException(updateEffectModelFiles, "please set VEEnv or VEEnv#init");
            } else if (updateEffectModelFiles == -1) {
                throw new VEException(updateEffectModelFiles, "fail when updating model files");
            }
        }
    }
}
