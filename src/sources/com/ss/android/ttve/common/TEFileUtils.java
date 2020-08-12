package com.ss.android.ttve.common;

import android.os.Environment;
import java.io.File;

public class TEFileUtils {
    private static final String DEFAULT_FOLDER_NAME = "BDMedia";
    protected static String msFolderPath;

    public static String getPath() {
        if (msFolderPath == null) {
            msFolderPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + DEFAULT_FOLDER_NAME;
            File file = new File(msFolderPath);
            if (!file.exists() && !file.mkdirs()) {
                return null;
            }
        }
        return msFolderPath;
    }
}
