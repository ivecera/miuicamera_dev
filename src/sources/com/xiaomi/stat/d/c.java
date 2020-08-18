package com.xiaomi.stat.d;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import com.xiaomi.stat.am;

public class c {

    /* renamed from: a  reason: collision with root package name */
    private static boolean f536a;

    /* renamed from: b  reason: collision with root package name */
    private static int f537b;

    /* renamed from: c  reason: collision with root package name */
    private static String f538c;

    public static int a() {
        if (!f536a) {
            c();
        }
        return f537b;
    }

    public static String b() {
        if (!f536a) {
            c();
        }
        return f538c;
    }

    private static void c() {
        if (!f536a) {
            f536a = true;
            Context a2 = am.a();
            try {
                PackageInfo packageInfo = a2.getPackageManager().getPackageInfo(a2.getPackageName(), 0);
                f537b = packageInfo.versionCode;
                f538c = packageInfo.versionName;
            } catch (PackageManager.NameNotFoundException unused) {
            }
        }
    }
}
