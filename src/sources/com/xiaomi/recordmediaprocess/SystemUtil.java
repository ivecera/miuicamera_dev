package com.xiaomi.recordmediaprocess;

import android.content.Context;

public class SystemUtil {
    public static void Init(Context context, int i) {
        Init(context, i, context.getPackageName());
    }

    public static native void Init(Context context, int i, String str);

    public static native void UnInit();
}
