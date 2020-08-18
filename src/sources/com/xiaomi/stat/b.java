package com.xiaomi.stat;

import android.os.Looper;
import android.text.TextUtils;
import com.xiaomi.stat.d.m;

public class b {
    private static final String A = "pref_instance_id";
    private static final String B = "pref_main_app_channel";
    private static final String C = "pref_instance_id_last_use_time";
    private static final String D = "pref_system_upload_intl_enabled";
    private static final String E = "pref_upload_enabled";
    private static final String F = "pref_sample_enabled";
    private static final String G = "pref_basic_enabled";
    private static final String H = "pref_custom_enabled";
    private static final String I = "pref_crash_enabled";
    private static final String J = "pref_http_event_enabled";
    private static int K = 31;
    private static int L = 15;
    private static String[] M = null;
    private static final String N = ",";
    private static boolean O = false;
    private static boolean P = false;
    private static String Q = null;
    private static boolean R = false;
    private static Object S = new Object();
    private static boolean T = false;

    /* renamed from: a  reason: collision with root package name */
    public static final int f428a = -1;

    /* renamed from: b  reason: collision with root package name */
    public static final int f429b = -1;

    /* renamed from: c  reason: collision with root package name */
    public static final int f430c = 0;

    /* renamed from: d  reason: collision with root package name */
    public static final int f431d = 1;

    /* renamed from: e  reason: collision with root package name */
    public static final int f432e = 2;

    /* renamed from: f  reason: collision with root package name */
    public static final int f433f = -1;
    public static final String g = "uploadInterval";
    public static final String h = "configNetwork";
    public static final String i = "configDelay";
    public static final String j = "time";
    public static final String k = "enableSample";
    public static final String l = "uploadSwitch";
    public static final String m = "0.0";
    public static final String n = "0-0";
    public static final int o = 0;
    private static final String p = "pref_statistic_enabled";
    private static final String q = "pref_anonymous_event_enabled";
    private static final String r = "pref_network_access_enabled";
    private static final String s = "pref_user_id";
    private static final String t = "pref_random_uuid";
    private static final String u = "pref_using_custom_policy_";
    private static final String v = "pref_custom_policy_state_";
    private static final String w = "pref_app_previous_version";
    private static final String x = "pref_is_first_usage";
    private static final String y = "pref_last_dau_event_time";
    private static final String z = "pref_all_sub_ids_data";

    public static boolean A() {
        return ad.a().a(G, true);
    }

    public static boolean B() {
        return ad.a().a(H, true);
    }

    public static boolean C() {
        return ad.a().a(E, true);
    }

    public static void a(int i2) {
        K = i2;
    }

    public static void a(long j2) {
        ad.a().b(y, j2);
    }

    public static void a(String str) {
        Q = str;
    }

    public static void a(String str, int i2) {
        if (!TextUtils.isEmpty(str)) {
            ad a2 = ad.a();
            a2.b(v + str, i2);
        }
    }

    public static void a(String str, boolean z2) {
        if (!TextUtils.isEmpty(str)) {
            ad a2 = ad.a();
            a2.b(u + str, z2);
        }
    }

    public static void a(boolean z2) {
        ad.a().b(p, z2);
    }

    public static boolean a() {
        return ad.a().a(p, true);
    }

    public static void b(int i2) {
        if (i2 <= 5) {
            i2 = 15;
        } else if (i2 > 86400) {
            i2 = 86400;
        }
        L = i2;
    }

    public static void b(long j2) {
        ad.a().b(C, j2);
    }

    public static void b(String str) {
        ad.a().b(s, str);
    }

    public static void b(boolean z2) {
        ad.a().b(q, z2);
    }

    public static boolean b() {
        return ad.a().a(q, false);
    }

    public static void c(int i2) {
        ad.a().b(h, i2);
    }

    public static void c(String str) {
        ad.a().b(i, str);
    }

    public static void c(boolean z2) {
        ad.a().b(r, z2);
    }

    public static boolean c() {
        return ad.a().a(r, true);
    }

    public static void d() {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            long j2 = 5000;
            if (ad.a().a(r)) {
                j2 = 1000;
            }
            try {
                Thread.sleep(j2);
            } catch (InterruptedException unused) {
            }
        } else {
            throw new IllegalStateException("don't call this on main thread");
        }
    }

    public static void d(int i2) {
        ad.a().b(g, i2);
    }

    public static void d(boolean z2) {
        P = z2;
    }

    public static boolean d(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        ad a2 = ad.a();
        return a2.a(u + str, false);
    }

    public static int e(String str) {
        if (TextUtils.isEmpty(str)) {
            return 0;
        }
        ad a2 = ad.a();
        return a2.a(v + str, 0);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0029, code lost:
        return;
     */
    public static void e() {
        synchronized (S) {
            if (!R) {
                R = true;
                P = m.i();
                Q = m.g();
                if (!P && !TextUtils.equals(Q, "CN")) {
                    P = true;
                }
            }
        }
    }

    public static void e(int i2) {
        if (i2 > 0) {
            ad.a().b(w, i2);
        }
    }

    public static void e(boolean z2) {
        T = z2;
    }

    public static void f(String str) {
        String[] strArr = M;
        if (strArr != null) {
            int length = strArr.length;
            int i2 = 0;
            while (i2 < length) {
                if (!TextUtils.equals(str, strArr[i2])) {
                    i2++;
                } else {
                    return;
                }
            }
        }
        if (M == null) {
            ad.a().b(z, str);
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(M[0]);
        int length2 = M.length;
        for (int i3 = 1; i3 < length2; i3++) {
            sb.append(N);
            sb.append(M[i3]);
        }
        sb.append(N);
        sb.append(str);
        ad.a().b(z, sb.toString());
    }

    public static void f(boolean z2) {
        ad.a().b(x, z2);
    }

    public static boolean f() {
        if (!R) {
            e();
        }
        return P;
    }

    public static String g() {
        return Q;
    }

    public static void g(String str) {
        ad.a().b(A, str);
    }

    public static void g(boolean z2) {
        O = z2;
    }

    public static void h(String str) {
        ad.a().b(B, str);
    }

    public static void h(boolean z2) {
        ad.a().b(D, z2);
    }

    public static boolean h() {
        return T;
    }

    public static String i() {
        return ad.a().a(s, (String) null);
    }

    public static void i(String str) {
        ad.a().b(t, str);
    }

    public static void i(boolean z2) {
        ad.a().b(F, z2);
    }

    public static int j() {
        return K;
    }

    public static void j(boolean z2) {
        ad.a().b(G, z2);
    }

    public static int k() {
        return L;
    }

    public static void k(boolean z2) {
        ad.a().b(H, z2);
    }

    public static String l() {
        return ad.a().a(i, n);
    }

    public static void l(boolean z2) {
        ad.a().b(E, z2);
    }

    public static int m() {
        return ad.a().a(h, -1);
    }

    public static int n() {
        return ad.a().a(g, -1);
    }

    public static void o() {
        String a2 = ad.a().a(z, (String) null);
        if (!TextUtils.isEmpty(a2)) {
            M = a2.split(N);
        }
    }

    public static String[] p() {
        return M;
    }

    public static int q() {
        return ad.a().a(w, -1);
    }

    public static boolean r() {
        return ad.a().a(x, true);
    }

    public static long s() {
        return ad.a().a(y, -1L);
    }

    public static String t() {
        return ad.a().a(A, (String) null);
    }

    public static String u() {
        return ad.a().a(B, (String) null);
    }

    public static boolean v() {
        return O;
    }

    public static long w() {
        return ad.a().a(C, 0L);
    }

    public static String x() {
        return ad.a().a(t, (String) null);
    }

    public static boolean y() {
        return ad.a().a(D, false);
    }

    public static boolean z() {
        return ad.a().a(F, false);
    }
}
