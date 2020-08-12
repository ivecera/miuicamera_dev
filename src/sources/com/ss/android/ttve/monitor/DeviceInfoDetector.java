package com.ss.android.ttve.monitor;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import com.ss.android.ttve.monitor.GPUModelDetector;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class DeviceInfoDetector {
    private static String abi;
    private static String appid;
    private static String cpu;
    private static String cpu_core;
    private static String cpu_freq;
    private static String device_brand;
    private static String external_storage;
    private static String gpu;
    private static String gpu_ver;
    private static boolean inited;
    private static String locale;
    private static String memory;
    private static String model;
    private static boolean nativeInited;
    private static String opengl_version;
    private static String os_sdk_int;
    public static List<String> sNumberKeys = new ArrayList<String>() {
        /* class com.ss.android.ttve.monitor.DeviceInfoDetector.AnonymousClass1 */

        {
            add("os_sdk_int");
            add("external_storage");
            add("screen_height");
            add("screen_width");
            add("storage");
            add("memory");
            add("cpu_core");
            add("cpu_freq");
        }
    };
    private static String screen_height;
    private static String screen_width;
    private static String sim_operator;
    private static String storage;
    private static String ve_version;

    public static String get(String str) {
        return toMap().get(str);
    }

    private static String getCpuAbi() {
        if (Build.VERSION.SDK_INT >= 21) {
            StringBuilder sb = new StringBuilder();
            String[] strArr = Build.SUPPORTED_ABIS;
            if (strArr != null) {
                for (int i = 0; i < strArr.length; i++) {
                    sb.append(strArr[i]);
                    if (i != strArr.length - 1) {
                        sb.append(",");
                    }
                }
            }
            return sb.toString();
        }
        return Build.CPU_ABI + "," + Build.CPU_ABI2;
    }

    public static void init(Context context) {
        if (!inited) {
            synchronized (DeviceInfoDetector.class) {
                if (!inited) {
                    initInternal(context.getApplicationContext());
                    inited = true;
                }
            }
        }
    }

    private static void initInternal(Context context) {
        String str;
        String language = Locale.getDefault().getLanguage();
        String country = Locale.getDefault().getCountry();
        if (TextUtils.isEmpty(country)) {
            str = "";
        } else {
            str = "_" + country.toUpperCase();
        }
        model = Build.MODEL;
        cpu = DeviceInfoUtils.readCpuHardware();
        cpu_freq = DeviceInfoUtils.getMaxCpuFreq();
        cpu_core = String.valueOf(DeviceInfoUtils.getNumOfCores());
        memory = String.valueOf(DeviceInfoUtils.getTotalMemory());
        storage = String.valueOf(DeviceInfoUtils.getInternalStorage());
        external_storage = String.valueOf(DeviceInfoUtils.getExternalStorage(context));
        os_sdk_int = String.valueOf(Build.VERSION.SDK_INT);
        screen_width = String.valueOf(DeviceInfoUtils.getScreenWidth(context));
        screen_height = String.valueOf(DeviceInfoUtils.getRealScreenHeight(context));
        appid = context.getPackageName();
        abi = getCpuAbi();
        locale = language + str;
        sim_operator = DeviceInfoUtils.getSimOperator(context);
        device_brand = Build.BRAND;
    }

    public static Map<String, String> toMap() {
        HashMap hashMap = new HashMap();
        hashMap.put(MonitorUtils.KEY_MODEL, model);
        hashMap.put("cpu", cpu);
        hashMap.put("cpu_freq", cpu_freq);
        hashMap.put("cpu_core", cpu_core);
        hashMap.put("memory", memory);
        hashMap.put("storage", storage);
        hashMap.put("external_storage", external_storage);
        hashMap.put("screen_width", screen_width);
        hashMap.put("screen_height", screen_height);
        hashMap.put("os_sdk_int", os_sdk_int);
        hashMap.put("appid", appid);
        hashMap.put("abi", abi);
        hashMap.put("locale", locale);
        hashMap.put("sim_operator", sim_operator);
        hashMap.put("brand", device_brand);
        if (!nativeInited) {
            GPUModelDetector.GPUModelInfo gPUModel = GPUModelDetector.getInstance().getGPUModel();
            GPUModelDetector.ENvGpuSubModel gPUSubModel = gPUModel.getGPUSubModel();
            int gPUModelNumber = gPUModel.getGPUModelNumber();
            gpu = gPUSubModel == null ? "" : gPUSubModel.name();
            gpu_ver = String.valueOf(gPUModelNumber);
            opengl_version = GPUModelDetector.getInstance().getGLVersion();
            ve_version = "4.4.0.34_7";
            nativeInited = true;
        }
        hashMap.put("gpu", gpu);
        hashMap.put("gpu_ver", gpu_ver);
        hashMap.put("opengl_version", opengl_version);
        hashMap.put(MonitorUtils.KEY_VE_VERSION, ve_version);
        return hashMap;
    }
}
