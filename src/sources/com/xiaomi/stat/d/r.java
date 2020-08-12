package com.xiaomi.stat.d;

import android.content.Context;
import android.os.SystemClock;
import com.xiaomi.stat.am;
import com.xiaomi.stat.b.e;
import java.util.Calendar;

public class r {

    /* renamed from: a  reason: collision with root package name */
    public static final long f613a = 86400000;

    /* renamed from: b  reason: collision with root package name */
    private static final String f614b = "TimeUtil";

    /* renamed from: c  reason: collision with root package name */
    private static final long f615c = 300000;

    /* renamed from: d  reason: collision with root package name */
    private static long f616d;

    /* renamed from: e  reason: collision with root package name */
    private static long f617e;

    /* renamed from: f  reason: collision with root package name */
    private static long f618f;

    public static void a() {
        boolean z;
        Context a2 = am.a();
        long f2 = p.f(a2);
        long g = p.g(a2);
        long h = p.h(a2);
        if (f2 == 0 || g == 0 || h == 0 || Math.abs((System.currentTimeMillis() - g) - (SystemClock.elapsedRealtime() - h)) > 300000) {
            z = true;
        } else {
            f616d = f2;
            f618f = g;
            f617e = h;
            z = false;
        }
        if (z) {
            e.a().execute(new s());
        }
        k.b(f614b, "syncTimeIfNeeded sync: " + z);
    }

    public static void a(long j) {
        if (j > 0) {
            k.b("MI_STAT_TEST", "update server time:" + j);
            f616d = j;
            f617e = SystemClock.elapsedRealtime();
            f618f = System.currentTimeMillis();
            Context a2 = am.a();
            p.a(a2, f616d);
            p.b(a2, f618f);
            p.c(a2, f617e);
        }
    }

    public static boolean a(long j, long j2) {
        return Math.abs(b() - j) >= j2;
    }

    public static long b() {
        long j = f616d;
        return (j == 0 || f617e == 0) ? System.currentTimeMillis() : (j + SystemClock.elapsedRealtime()) - f617e;
    }

    public static boolean b(long j) {
        k.b("MI_STAT_TEST", "inToday,current ts :" + b());
        Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(b());
        instance.set(11, 0);
        instance.set(12, 0);
        instance.set(13, 0);
        instance.set(14, 0);
        long timeInMillis = instance.getTimeInMillis();
        long j2 = timeInMillis + 86400000;
        k.b("MI_STAT_TEST", "[start]:" + timeInMillis + "\n[end]:" + j2 + "duration" + ((j2 - timeInMillis) - 86400000));
        StringBuilder sb = new StringBuilder();
        sb.append("is in today:");
        int i = (timeInMillis > j ? 1 : (timeInMillis == j ? 0 : -1));
        sb.append(i <= 0 && j < j2);
        k.b("MI_STAT_TEST", sb.toString());
        return i <= 0 && j < j2;
    }
}
