package com.xiaomi.engine;

import android.util.Log;
import java.security.InvalidParameterException;

class Util {
    private static final String TAG = "Util";

    Util() {
    }

    static void assertOrNot(int i) {
        if (i == -2147483647) {
            throw new InvalidParameterException("Invalid parameter, check your input parameter please!");
        } else if (i == -2147483642) {
            throw new RuntimeException("Invalid call!");
        } else if (i != 0) {
            throw new RuntimeException("onErrorCaused: Error Code = " + i);
        }
    }

    static void dumpMetaDataToLog(Object obj) {
        Log.d(TAG, "dumpMetaDataToLog: ===============================================");
        try {
            obj.getClass().getMethod("dumpToLog", new Class[0]).invoke(obj, new Object[0]);
        } catch (Exception e2) {
            Log.e(TAG, "dumpMetaDataToLog: ", e2);
        }
        Log.d(TAG, "dumpMetaDataToLog: ===============================================");
    }
}
