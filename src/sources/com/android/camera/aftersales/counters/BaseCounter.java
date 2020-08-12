package com.android.camera.aftersales.counters;

import android.os.SystemProperties;
import com.android.camera.Util;
import com.android.camera.aftersales.interfaces.ICounter;
import com.android.camera.aftersales.interfaces.ITrimStrategy;
import com.android.camera.log.Log;
import com.android.camera.module.impl.component.FileUtils;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public abstract class BaseCounter implements ICounter {
    public static final int CAMERA_ID_UNKNOWN = -1;
    public static final int COUNTER_ID_ALL = 0;
    public static final int COUNTER_ID_ANR = 2;
    public static final int COUNTER_ID_BLACK = 8;
    public static final int COUNTER_ID_CAMERA_ERROR = 4;
    public static final int COUNTER_ID_CRASH = 1;
    public static final int COUNTER_ID_GAUSSIAN = 3;
    public static final int COUNTER_ID_LOEW_FRAME = 9;
    public static final int COUNTER_ID_POPUP_FAILED = 6;
    public static final int COUNTER_ID_RECORD_FAIL = 7;
    public static final int COUNTER_ID_SESSION_ERROR = 5;
    public static final int COUNTER_ID_TAKEDOWN_FAILED = 10;
    public static final long PREVIEW_GAUSSIAN_TIME_OUT = 3000;
    public static final String ROM_VERSION = (SystemProperties.get("ro.miui.ui.version.name") + "." + SystemProperties.get("ro.build.version.incremental"));
    public static final String TAG = "BaseCounter";
    private String mFilePath;
    private int mId;
    private ITrimStrategy mStrategy;

    public BaseCounter(int i, ITrimStrategy iTrimStrategy, String str) {
        this.mId = i;
        this.mStrategy = iTrimStrategy;
        this.mFilePath = str;
    }

    private void appendNewLine(long j, int i, int i2) {
        BufferedWriter bufferedWriter;
        FileOutputStream fileOutputStream;
        OutputStreamWriter outputStreamWriter;
        IOException e2;
        BufferedWriter bufferedWriter2;
        try {
            fileOutputStream = new FileOutputStream(new File(this.mFilePath), true);
            try {
                outputStreamWriter = new OutputStreamWriter(fileOutputStream, "UTF-8");
                try {
                    bufferedWriter = new BufferedWriter(outputStreamWriter);
                    try {
                        bufferedWriter.write(ROM_VERSION + "," + i + "," + Util.toHumanString(j) + "," + i2);
                        bufferedWriter.newLine();
                    } catch (IOException e3) {
                        e2 = e3;
                    }
                } catch (IOException e4) {
                    e = e4;
                    bufferedWriter2 = null;
                    e2 = e;
                    try {
                        e2.printStackTrace();
                        FileUtils.close(bufferedWriter);
                        FileUtils.close(outputStreamWriter);
                        FileUtils.close(fileOutputStream);
                    } catch (Throwable th) {
                        th = th;
                        FileUtils.close(bufferedWriter);
                        FileUtils.close(outputStreamWriter);
                        FileUtils.close(fileOutputStream);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    bufferedWriter = null;
                    th = th;
                    FileUtils.close(bufferedWriter);
                    FileUtils.close(outputStreamWriter);
                    FileUtils.close(fileOutputStream);
                    throw th;
                }
            } catch (IOException e5) {
                e = e5;
                outputStreamWriter = null;
                bufferedWriter2 = null;
                e2 = e;
                e2.printStackTrace();
                FileUtils.close(bufferedWriter);
                FileUtils.close(outputStreamWriter);
                FileUtils.close(fileOutputStream);
            } catch (Throwable th3) {
                th = th3;
                outputStreamWriter = null;
                bufferedWriter = null;
                th = th;
                FileUtils.close(bufferedWriter);
                FileUtils.close(outputStreamWriter);
                FileUtils.close(fileOutputStream);
                throw th;
            }
        } catch (IOException e6) {
            e = e6;
            outputStreamWriter = null;
            fileOutputStream = null;
            bufferedWriter2 = null;
            e2 = e;
            e2.printStackTrace();
            FileUtils.close(bufferedWriter);
            FileUtils.close(outputStreamWriter);
            FileUtils.close(fileOutputStream);
        } catch (Throwable th4) {
            th = th4;
            outputStreamWriter = null;
            fileOutputStream = null;
            bufferedWriter = null;
            th = th;
            FileUtils.close(bufferedWriter);
            FileUtils.close(outputStreamWriter);
            FileUtils.close(fileOutputStream);
            throw th;
        }
        FileUtils.close(bufferedWriter);
        FileUtils.close(outputStreamWriter);
        FileUtils.close(fileOutputStream);
    }

    @Override // com.android.camera.aftersales.interfaces.ICounter
    public final void clean() {
        FileUtils.deleteFile(this.mFilePath);
    }

    @Override // com.android.camera.aftersales.interfaces.ICounter
    public void count(long j, int i, int i2) {
        File file = new File(this.mFilePath);
        if (!file.exists()) {
            try {
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                file.createNewFile();
                appendNewLine(j, i, i2);
            } catch (IOException e2) {
                Log.e(TAG, "count fail", e2);
            }
        } else {
            if (this.mStrategy.exceedLimit()) {
                this.mStrategy.doTrim();
            }
            appendNewLine(j, i, i2);
        }
    }

    @Override // com.android.camera.aftersales.interfaces.ICounter
    public boolean exceedLimit() {
        return this.mStrategy.exceedLimit();
    }

    @Override // com.android.camera.aftersales.interfaces.ICounter
    public void trim() {
        this.mStrategy.doTrim();
    }
}
