package com.xiaomi.stat.a;

import android.content.Context;
import android.text.TextUtils;
import com.xiaomi.stat.HttpEvent;
import com.xiaomi.stat.MiStatParams;
import com.xiaomi.stat.NetAvailableEvent;
import com.xiaomi.stat.al;
import com.xiaomi.stat.am;
import com.xiaomi.stat.b;
import com.xiaomi.stat.b.f;
import com.xiaomi.stat.d;
import com.xiaomi.stat.d.e;
import com.xiaomi.stat.d.p;
import com.xiaomi.stat.d.r;
import java.io.PrintWriter;
import java.io.StringWriter;

public class l {

    /* renamed from: a  reason: collision with root package name */
    public String f367a;

    /* renamed from: b  reason: collision with root package name */
    public String f368b;

    /* renamed from: c  reason: collision with root package name */
    public String f369c;

    /* renamed from: d  reason: collision with root package name */
    public al f370d;

    /* renamed from: e  reason: collision with root package name */
    public long f371e;

    /* renamed from: f  reason: collision with root package name */
    public String f372f;
    public boolean g;

    public static class a {
        public static final String A = "pc";
        public static final String B = "sts";
        public static final String C = "ets";
        private static final String D = "mistat_net_available";
        private static final String E = "mistat_pa";
        private static final String F = "mistat_dau";
        private static final String G = "mistat_app_exception";
        private static final String H = "mistat_app_update";
        private static final String I = "mistat_signin";
        private static final String J = "mistat_signout";
        private static final String K = "mistat_net_monitor";
        private static final String L = "mistat_page_monitor";
        private static final String M = "mistat_net_event";
        private static final String N = "mi_sai";
        private static final String O = "track";
        private static final String P = "track_signin";
        private static final String Q = "profile_set";
        private static final String R = "fo";
        private static final String S = "ia";
        private static final String T = "i1";
        private static final String U = "ib";
        private static final String V = "i2";
        private static final String W = "md";
        private static final String X = "ms";
        private static final String Y = "ii";
        private static final String Z = "mcm";

        /* renamed from: a  reason: collision with root package name */
        public static final int f373a = 30;
        private static final String aA = "ec";
        private static final String aB = "rt";
        private static final String aC = "rst";
        private static final String aD = "n";
        private static final String aE = "ns";
        private static final String aF = "rc";
        private static final String aG = "ext";
        private static final String aa = "mcs";
        private static final String ab = "bm";
        private static final String ac = "bs";
        private static final String ad = "aa";
        private static final String ae = "ai";
        private static final String af = "od";
        private static final String ag = "oa";
        private static final String ah = "va";
        private static final String ai = "pg";
        private static final String aj = "bt";
        private static final String ak = "et";
        private static final String al = "sk";
        private static final String am = "ek";
        private static final String an = "et";
        private static final String ao = "em";
        private static final String ap = "pvr";
        private static final String aq = "ud";
        private static final String ar = "ur";
        private static final String as = "dt";
        private static final String at = "rc";
        private static final String au = "nf";
        private static final String av = "ecn";
        private static final String aw = "ve";
        private static final String ax = "fg";
        private static final String ay = "rsc";
        private static final String az = "sc";

        /* renamed from: b  reason: collision with root package name */
        public static final String f374b = "e";

        /* renamed from: c  reason: collision with root package name */
        public static final String f375c = "eg";

        /* renamed from: d  reason: collision with root package name */
        public static final String f376d = "tp";

        /* renamed from: e  reason: collision with root package name */
        public static final String f377e = "ts";

        /* renamed from: f  reason: collision with root package name */
        public static final String f378f = "ps";
        public static final String g = "eid";
        public static final String h = "mistat_basic";
        public static final String i = "mistat_user_page";
        public static final String j = "mistat_crash";
        public static final String k = "mistat_network";
        public static final String l = "mistat_plain_text";
        public static final String m = "mistat_delete_event";
        public static final String n = "mi_av";
        public static final String o = "mi_sv";
        public static final String p = "mi_ov";
        public static final String q = "mi_ob";
        public static final String r = "mi_n";
        public static final String s = "mi_rd";
        public static final String t = "mi_mf";
        public static final String u = "mi_m";
        public static final String v = "mi_os";
        public static final String w = "profile_";
        public static final String x = "ca";
        public static final String y = "c_";
        public static final String z = "rc";
    }

    private l() {
    }

    public static l a() {
        l lVar = new l();
        lVar.f367a = "mistat_dau";
        lVar.f368b = a.h;
        lVar.f369c = "track";
        lVar.f371e = r.b();
        al alVar = new al();
        boolean r = b.r();
        if (r) {
            b.f(false);
        }
        alVar.putInt(d.B, r ? 1 : 0);
        Context a2 = am.a();
        alVar.putString(d.C, e.b(a2));
        alVar.putString(d.D, e.c(a2));
        alVar.putString(d.E, e.e(a2));
        alVar.putString(d.F, e.f(a2));
        alVar.putString(d.G, e.h(a2));
        alVar.putString(d.H, e.i(a2));
        alVar.putString("ii", e.d());
        alVar.putString(d.J, e.k(a2));
        alVar.putString(d.K, e.l(a2));
        alVar.putString(d.L, e.n(a2));
        alVar.putString("bs", e.o(a2));
        alVar.putString(d.O, e.q(a2));
        alVar.putString("ai", e.p(a2));
        alVar.putString(d.P, e.x(a2));
        alVar.putString("oa", f.b(a2));
        alVar.putString("va", f.c(a2));
        lVar.f370d = alVar;
        a(lVar);
        return lVar;
    }

    public static l a(int i) {
        l lVar = new l();
        lVar.f367a = "mistat_app_update";
        lVar.f368b = a.h;
        lVar.f369c = "track";
        lVar.f371e = r.b();
        al alVar = new al();
        alVar.putInt(d.S, i);
        lVar.f370d = alVar;
        a(lVar);
        return lVar;
    }

    public static l a(int i, int i2, long j, long j2) {
        l lVar = new l();
        lVar.f367a = "mistat_page_monitor";
        lVar.f368b = a.h;
        lVar.f369c = "track";
        lVar.f371e = r.b();
        al alVar = new al();
        alVar.putInt("rc", i);
        alVar.putInt(a.A, i2);
        alVar.putLong(a.B, j);
        alVar.putLong(a.C, j2);
        lVar.f370d = alVar;
        a(lVar);
        return lVar;
    }

    public static l a(HttpEvent httpEvent, String str) {
        l lVar = new l();
        lVar.f367a = "mistat_net_monitor";
        lVar.f368b = a.k;
        lVar.f369c = "track";
        lVar.f371e = r.b();
        al alVar = new al();
        alVar.putString("ur", httpEvent.getUrl());
        alVar.putLong(d.Q, httpEvent.getTimeCost());
        alVar.putInt("rc", httpEvent.getResponseCode());
        alVar.putLong("nf", httpEvent.getNetFlow());
        alVar.putString("ecn", httpEvent.getExceptionName());
        lVar.f370d = alVar;
        a(lVar);
        a(lVar, str);
        return lVar;
    }

    public static l a(MiStatParams miStatParams, boolean z, String str) {
        l lVar = new l();
        lVar.f369c = "profile_set";
        lVar.f371e = r.b();
        lVar.f370d = new al(miStatParams);
        if (z) {
            lVar.g = false;
        } else {
            a(lVar);
        }
        a(lVar, str);
        return lVar;
    }

    public static l a(NetAvailableEvent netAvailableEvent, String str) {
        l lVar = new l();
        lVar.f367a = "mistat_net_event";
        lVar.f368b = "mistat_net_available";
        lVar.f369c = "track";
        lVar.f371e = r.b();
        al alVar = new al();
        alVar.putString("fg", netAvailableEvent.getFlag());
        alVar.putInt("rsc", netAvailableEvent.getResponseCode());
        alVar.putInt("sc", netAvailableEvent.getStatusCode());
        alVar.putString("ec", netAvailableEvent.getException());
        alVar.putInt("rt", netAvailableEvent.getResultType());
        alVar.putLong("rst", netAvailableEvent.getRequestStartTime());
        Context a2 = am.a();
        alVar.putString("n", a2 != null ? com.xiaomi.stat.d.l.b(a2) : "UNKNOWN");
        alVar.putString("ns", String.valueOf(a2 != null ? p.l(a2) : 0.0f));
        alVar.putInt("rc", netAvailableEvent.getRetryCount());
        alVar.putString("ext", netAvailableEvent.getExt());
        lVar.f370d = alVar;
        a(lVar);
        a(lVar, str);
        return lVar;
    }

    public static l a(al alVar) {
        l lVar = new l();
        lVar.f367a = a.m;
        lVar.f368b = a.h;
        lVar.f369c = "track";
        lVar.f371e = r.b();
        lVar.f370d = alVar;
        a(lVar);
        return lVar;
    }

    public static l a(String str) {
        l lVar = new l();
        boolean isEmpty = TextUtils.isEmpty(str);
        lVar.f367a = isEmpty ? "mistat_signout" : "mistat_signin";
        lVar.f368b = a.h;
        lVar.f369c = "track_signin";
        lVar.f371e = r.b();
        al alVar = new al();
        if (!isEmpty) {
            alVar.putString(d.t, str);
        }
        lVar.f370d = alVar;
        a(lVar);
        return lVar;
    }

    public static l a(String str, long j, long j2) {
        return a(str, j, j2, true, null, null);
    }

    public static l a(String str, long j, long j2, MiStatParams miStatParams, String str2) {
        return a(str, j, j2, false, miStatParams, str2);
    }

    private static l a(String str, long j, long j2, boolean z, MiStatParams miStatParams, String str2) {
        l lVar = new l();
        lVar.f367a = "mistat_pa";
        lVar.f368b = z ? a.h : a.i;
        lVar.f369c = "track";
        lVar.f371e = r.b();
        al alVar = new al(miStatParams);
        alVar.putString(d.aa, str);
        alVar.putLong("bt", j);
        alVar.putLong("et", j2);
        lVar.f370d = alVar;
        a(lVar);
        if (!z) {
            a(lVar, str2);
        }
        return lVar;
    }

    public static l a(String str, String str2, MiStatParams miStatParams, String str3, boolean z) {
        l lVar = new l();
        lVar.f367a = str;
        lVar.f368b = str2;
        lVar.f369c = "track";
        lVar.f371e = r.b();
        lVar.f370d = new al(miStatParams);
        if (z) {
            lVar.g = false;
        } else {
            a(lVar);
        }
        a(lVar, str3);
        return lVar;
    }

    public static l a(String str, String str2, String str3) {
        l lVar = new l();
        lVar.f367a = str;
        lVar.f368b = a.l;
        lVar.f369c = "track";
        lVar.f371e = r.b();
        al alVar = new al();
        alVar.putString("ve", str2);
        lVar.f370d = alVar;
        a(lVar);
        a(lVar, str3);
        return lVar;
    }

    public static l a(Throwable th, String str, boolean z, String str2) {
        l lVar = new l();
        lVar.f367a = "mistat_app_exception";
        lVar.f368b = a.j;
        lVar.f369c = "track";
        lVar.f371e = r.b();
        al alVar = new al();
        lVar.f370d = alVar;
        alVar.putString(d.ah, str);
        alVar.putInt("et", z ? 1 : 0);
        StringWriter stringWriter = new StringWriter();
        th.printStackTrace(new PrintWriter(stringWriter));
        alVar.putString(d.af, stringWriter.toString());
        alVar.putString(d.ag, th.getMessage());
        a(lVar);
        a(lVar, str2);
        return lVar;
    }

    private static void a(l lVar) {
        if (b.b()) {
            lVar.g = true;
        } else if (b.f()) {
            lVar.g = true;
        } else {
            lVar.g = false;
        }
    }

    private static void a(l lVar, String str) {
        if (!TextUtils.isEmpty(str)) {
            lVar.f372f = str;
            lVar.f370d.putString("mi_sai", str);
        }
    }
}
