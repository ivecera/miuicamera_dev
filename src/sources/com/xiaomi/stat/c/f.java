package com.xiaomi.stat.c;

import com.xiaomi.stat.am;
import com.xiaomi.stat.b;
import com.xiaomi.stat.d.k;
import com.xiaomi.stat.d.l;
import com.xiaomi.stat.d.m;

public class f {

    /* renamed from: a  reason: collision with root package name */
    public static final int f500a = 1;

    /* renamed from: b  reason: collision with root package name */
    public static final int f501b = 2;

    /* renamed from: c  reason: collision with root package name */
    public static final int f502c = 3;

    /* renamed from: e  reason: collision with root package name */
    private static final String f503e = "UploadPolicy";

    /* renamed from: d  reason: collision with root package name */
    boolean f504d;

    /* renamed from: f  reason: collision with root package name */
    private String f505f;

    public f(String str, boolean z) {
        this.f504d = z;
        this.f505f = str;
    }

    public f(boolean z) {
        this.f504d = z;
        this.f505f = am.b();
    }

    private boolean a(int i) {
        return (i & -32) == 0;
    }

    private int b() {
        boolean b2 = m.b(am.a());
        k.b(f503e, " getExperiencePlanPolicy: " + b2 + " isInternationalVersion= " + b.f() + " isAnonymous= " + this.f504d);
        return (!b2 && !this.f504d) ? 2 : 3;
    }

    private int c() {
        int e2 = b.e(this.f505f);
        k.b(f503e, " getCustomPrivacyPolicy: state=" + e2);
        return e2 == 1 ? 3 : 1;
    }

    private int d() {
        return b.d(this.f505f) ? c() : b();
    }

    private int e() {
        int a2 = l.a(am.a());
        int m = a(b.m()) ? b.m() : b.j();
        StringBuilder sb = new StringBuilder();
        sb.append(" getHttpServicePolicy: currentNet= ");
        sb.append(a2);
        sb.append(" Config.getServerNetworkType= ");
        sb.append(b.m());
        sb.append(" Config.getUserNetworkType()= ");
        sb.append(b.j());
        sb.append(" (configNet & currentNet) == currentNet ");
        int i = m & a2;
        sb.append(i == a2);
        k.b(f503e, sb.toString());
        return i == a2 ? 3 : 1;
    }

    public int a() {
        return Math.min(d(), e());
    }
}
