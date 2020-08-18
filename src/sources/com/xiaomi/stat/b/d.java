package com.xiaomi.stat.b;

import android.content.Context;
import android.text.TextUtils;
import com.android.camera.module.loader.FunctionParseBeautyBodySlimCount;
import com.xiaomi.stat.a;
import com.xiaomi.stat.ad;
import com.xiaomi.stat.am;
import com.xiaomi.stat.b;
import com.xiaomi.stat.c.c;
import com.xiaomi.stat.d.e;
import com.xiaomi.stat.d.g;
import com.xiaomi.stat.d.k;
import com.xiaomi.stat.d.l;
import com.xiaomi.stat.d.r;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class d {

    /* renamed from: a  reason: collision with root package name */
    private static final Object f442a = new Object();

    /* renamed from: b  reason: collision with root package name */
    private static String f443b = " http://data.mistat.xiaomi.com/idservice/deviceid_get";

    /* renamed from: c  reason: collision with root package name */
    private static final String f444c = "DeviceIdManager";

    /* renamed from: d  reason: collision with root package name */
    private static final String f445d = "ia";

    /* renamed from: e  reason: collision with root package name */
    private static final String f446e = "ib";

    /* renamed from: f  reason: collision with root package name */
    private static final String f447f = "md";
    private static final String g = "mm";
    private static final String h = "bm";
    private static final String i = "aa";
    private static final String j = "ai";
    private static final String k = "oa";
    private static final int l = 0;
    private static final int m = 1;
    private static final int n = 2;
    private static final int o = 3;
    private static final int p = 4;
    private static final int q = 5;
    private static final int r = 6;
    private static final int s = 7;
    private static final int t = 1;
    private static final String u = "pref_key_device_id";
    private static final String v = "pref_key_restore_ts";
    private static d w;
    private String x = ad.a().a(u, "");
    private Context y = am.a();

    private d() {
    }

    public static d a() {
        if (w == null) {
            synchronized (f442a) {
                if (w == null) {
                    w = new d();
                }
            }
        }
        return w;
    }

    private String b(boolean z) {
        return z ? e.d() : TextUtils.isEmpty(this.x) ? e() : this.x;
    }

    private String c(boolean z) {
        if (z) {
            return e.d();
        }
        String r2 = e.r(am.a());
        return !TextUtils.isEmpty(r2) ? r2 : e.d();
    }

    private void d() {
        if (!b.a() || !b.c()) {
            k.c(f444c, "request abort: statistic or network is not enabled");
        } else if (l.a()) {
            int i2 = 1;
            while (i2 <= 3 && TextUtils.isEmpty(e()) && i2 != 3) {
                try {
                    Thread.sleep(FunctionParseBeautyBodySlimCount.TIP_INTERVAL_TIME);
                } catch (InterruptedException e2) {
                    e2.printStackTrace();
                }
                i2++;
            }
        } else {
            k.b(f444c, "network is not connected!");
        }
    }

    private String e() {
        try {
            if (k.b()) {
                f443b = k.f570b;
            }
            String a2 = c.a(f443b, (Map<String, String>) g(), true);
            k.b(f444c, a2);
            if (!TextUtils.isEmpty(a2)) {
                JSONObject jSONObject = new JSONObject(a2);
                long optLong = jSONObject.optLong("timestamp");
                int optInt = jSONObject.optInt("code");
                String optString = jSONObject.optString("device_id");
                if (optInt == 1) {
                    this.x = optString;
                    ad a3 = ad.a();
                    if (!TextUtils.isEmpty(this.x)) {
                        a3.b(u, optString);
                        a3.b(v, optLong);
                    }
                    r.a(optLong);
                    return this.x;
                }
            }
        } catch (IOException e2) {
            k.b(f444c, "[getDeviceIdLocal IOException]:", e2);
        } catch (JSONException e3) {
            k.b(f444c, "[getDeviceIdLocal JSONException]:", e3);
        }
        return this.x;
    }

    private String[] f() {
        return new String[]{e.b(this.y), e.e(this.y), e.h(this.y), e.k(this.y), e.n(this.y), e.q(this.y), e.p(this.y), f.b(this.y)};
    }

    private HashMap<String, String> g() {
        HashMap<String, String> hashMap = new HashMap<>();
        String[] f2 = f();
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("ia", f2[0]);
            jSONObject.put("ib", f2[1]);
            jSONObject.put("md", f2[2]);
            jSONObject.put("mm", f2[3]);
            jSONObject.put("bm", f2[4]);
            jSONObject.put("aa", f2[5]);
            jSONObject.put("ai", f2[6]);
            jSONObject.put(k, f2[7]);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        k.b(f444c, "[pay-load]:" + jSONObject.toString());
        byte[] bArr = new byte[0];
        try {
            bArr = i.a().a(jSONObject.toString().getBytes("utf-8"));
        } catch (UnsupportedEncodingException e3) {
            e3.printStackTrace();
        }
        String str = null;
        if (bArr != null) {
            str = com.xiaomi.stat.d.d.a(g.a(bArr, true).getBytes());
        }
        hashMap.put(com.xiaomi.stat.d.f521b, a.g);
        if (str == null) {
            str = "";
        }
        hashMap.put("p", str);
        hashMap.put("ai", am.b());
        hashMap.put("gzip", "0");
        hashMap.put(com.xiaomi.stat.d.ak, i.a().c());
        hashMap.put(com.xiaomi.stat.d.g, i.a().b());
        return hashMap;
    }

    public String a(boolean z) {
        return b.f() ? c(z) : b(z);
    }

    public synchronized void b() {
        if (!b.f() && !c()) {
            d();
        }
    }

    public boolean c() {
        String a2 = ad.a().a(u, (String) null);
        return !TextUtils.isEmpty(a2) && !TextUtils.isEmpty(this.x) && this.x.equals(a2);
    }
}
