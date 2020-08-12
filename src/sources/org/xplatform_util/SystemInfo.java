package org.xplatform_util;

import android.content.Context;
import android.os.Environment;
import java.io.File;

public class SystemInfo {
    static String getStoragePath(Context context) {
        File filesDir;
        return Environment.getExternalStorageState().equals("mounted") ? Environment.getExternalStorageDirectory().getAbsolutePath() : (context == null || (filesDir = context.getFilesDir()) == null) ? "" : filesDir.toString();
    }
}
