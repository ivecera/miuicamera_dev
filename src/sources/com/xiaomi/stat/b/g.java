package com.xiaomi.stat.b;

import android.content.Context;
import android.os.Build;
import android.os.Looper;
import android.text.TextUtils;
import com.xiaomi.stat.ad;
import com.xiaomi.stat.am;
import com.xiaomi.stat.b;
import com.xiaomi.stat.d.h;
import com.xiaomi.stat.d.k;
import com.xiaomi.stat.d.m;
import com.xiaomi.stat.d.r;
import java.util.HashMap;
import org.json.JSONObject;

public class g {

    /* renamed from: a  reason: collision with root package name */
    private static final String f455a = "RDM";

    /* renamed from: b  reason: collision with root package name */
    private static final Object f456b = new Object();

    /* renamed from: c  reason: collision with root package name */
    private static final String f457c = "CN";

    /* renamed from: d  reason: collision with root package name */
    private static final String f458d = "INTL";

    /* renamed from: e  reason: collision with root package name */
    private static final String f459e = "IN";

    /* renamed from: f  reason: collision with root package name */
    private static final String f460f = "data.mistat.xiaomi.com";
    private static final String g = "data.mistat.intl.xiaomi.com";
    private static final String h = "data.mistat.india.xiaomi.com";
    private static final String i = "region-url";
    private static final String j = "/map_domain";
    private static final String k = "region";
    private static HashMap<String, String> l = new HashMap<>();
    private static g r;
    private String m = f457c;
    private String n = f460f;
    private String o = null;
    private String p;
    private h q;

    static {
        l.put(f457c, f460f);
        l.put(f458d, g);
        l.put(f459e, h);
    }

    private g() {
        a(am.a());
    }

    public static g a() {
        if (r == null) {
            synchronized (f456b) {
                if (r == null) {
                    r = new g();
                }
            }
        }
        return r;
    }

    private String b(String str) {
        int i2 = am.a().getApplicationInfo().targetSdkVersion;
        String str2 = "https://";
        if (!b.f() && (Build.VERSION.SDK_INT < 28 || i2 < 28)) {
            str2 = "http://";
        }
        return str2.concat(this.n).concat("/").concat(str);
    }

    private boolean c(String str) {
        boolean z;
        if (l.keySet().contains(str)) {
            this.m = str;
            this.n = l.get(this.m);
            z = true;
        } else {
            this.m = f458d;
            this.n = l.get(this.m);
            k.d(f455a, "unknown region,set to unknown(singapore)'s domain");
            z = false;
        }
        ad.a().b("region", str);
        return z;
    }

    private void d(String str) {
        this.o = str;
    }

    private static void e() {
        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            k.d(f455a, "can not init in main thread!", null);
        }
    }

    private void f() {
        l = this.q.a(l, (HashMap) h.a(this.p.concat(j)));
        String str = l.get(this.m);
        if (!TextUtils.isEmpty(str)) {
            this.n = str;
        } else if (b.f()) {
            this.m = f458d;
            this.n = l.get(this.m);
        }
    }

    public void a(Context context) {
        e();
        this.q = new h();
        this.p = context.getFilesDir().getPath();
        if (!b.f()) {
            this.m = f457c;
            this.n = f460f;
        } else {
            String g2 = m.g();
            k.b(f455a, "[SystemRegion]:" + g2);
            String a2 = ad.a().a("region", (String) null);
            if (!TextUtils.isEmpty(g2)) {
                this.m = g2;
            }
            if (!TextUtils.isEmpty(a2)) {
                this.m = a2;
            }
            f();
        }
        k.b(f455a, "[file-dir]:" + this.p + "\n[CurrentRegion]:" + this.m + "\n[domain]:" + this.n);
    }

    public void a(String str) {
        d(str);
        c(str);
    }

    public void a(JSONObject jSONObject) {
        HashMap<String, String> a2 = this.q.a(i, jSONObject);
        String str = TextUtils.isEmpty(this.o) ? this.m : this.o;
        if (a2 != null) {
            l = this.q.a(l, a2);
            if (!TextUtils.isEmpty(str)) {
                String str2 = l.get(str);
                if (!TextUtils.isEmpty(str2)) {
                    this.m = str;
                    this.n = str2;
                }
            } else if (b.f()) {
                this.m = f458d;
                this.n = l.get(this.m);
            }
            h.a(l, this.p.concat(j));
        }
    }

    public void a(boolean z) {
        if (z) {
            this.m = f458d;
            this.n = g;
            String str = TextUtils.isEmpty(this.o) ? this.m : this.o;
            if (!TextUtils.isEmpty(str)) {
                String str2 = l.get(str);
                if (!TextUtils.isEmpty(str2)) {
                    this.m = str;
                    this.n = str2;
                    return;
                }
                return;
            }
            return;
        }
        this.m = f457c;
        this.n = f460f;
    }

    /* access modifiers changed from: package-private */
    public String b() {
        return b("get_all_config");
    }

    public boolean b(Context context) {
        return r.b(ad.a().a("key_update_time", 0)) || !b.f();
    }

    public String c() {
        return b("mistats/v3");
    }

    /* access modifiers changed from: protected */
    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException("Cannot clone instance of this class");
    }

    public String d() {
        return b("key_get");
    }
}
