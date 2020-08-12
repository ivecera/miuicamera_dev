package com.ss.android.ttve.common;

import android.util.Log;
import java.io.File;
import java.util.ArrayList;

public class TESpdLogManager {
    private static volatile TESpdLogManager mTESpdManager;
    private TESpdLogInvoker mTESpdLogInvoker = new TESpdLogInvoker();

    public enum InfoLevel {
        LEVEL0,
        LEVEL1,
        LEVEL2,
        LEVEL3
    }

    private TESpdLogManager() {
    }

    public static TESpdLogManager getInstance() {
        if (mTESpdManager == null) {
            synchronized (TESpdLogManager.class) {
                if (mTESpdManager == null) {
                    mTESpdManager = new TESpdLogManager();
                }
            }
        }
        return mTESpdManager;
    }

    public static String[] getLogFiles(String str) {
        if (str.isEmpty()) {
            return new String[0];
        }
        File file = new File(str);
        if (!file.isDirectory()) {
            return new String[0];
        }
        String[] list = file.list();
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < list.length; i++) {
            if (list[i].endsWith(".txt")) {
                arrayList.add(str + File.separator + list[i]);
            }
        }
        return (String[]) arrayList.toArray(new String[0]);
    }

    public void close() {
        this.mTESpdLogInvoker.close();
    }

    public void error(String str) {
        this.mTESpdLogInvoker.error(str);
    }

    public void info(InfoLevel infoLevel, String str) {
        this.mTESpdLogInvoker.info(infoLevel.ordinal(), str);
    }

    public int initSpdLog(String str, int i, int i2) {
        Log.e("TESpdLogManager", "logDir: " + str);
        int initSpdLog = this.mTESpdLogInvoker.initSpdLog(str, i, i2);
        if (initSpdLog < 0) {
            return initSpdLog;
        }
        return 0;
    }

    public void setLevel(InfoLevel infoLevel) {
        this.mTESpdLogInvoker.setLevel(infoLevel.ordinal());
    }

    public void warn(String str) {
        this.mTESpdLogInvoker.warn(str);
    }
}
