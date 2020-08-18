package com.xiaomi.stat.c;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import com.xiaomi.stat.a.k;
import com.xiaomi.stat.am;
import com.xiaomi.stat.b;
import com.xiaomi.stat.b.g;
import com.xiaomi.stat.d;
import com.xiaomi.stat.d.c;
import com.xiaomi.stat.d.e;
import com.xiaomi.stat.d.j;
import com.xiaomi.stat.d.l;
import com.xiaomi.stat.d.m;
import com.xiaomi.stat.d.r;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.zip.GZIPOutputStream;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class i {

    /* renamed from: a  reason: collision with root package name */
    private static final String f513a = "3.0";

    /* renamed from: b  reason: collision with root package name */
    private static final String f514b = "UploaderEngine";

    /* renamed from: c  reason: collision with root package name */
    private static final String f515c = "code";

    /* renamed from: d  reason: collision with root package name */
    private static final String f516d = "UTF-8";

    /* renamed from: e  reason: collision with root package name */
    private static final String f517e = "mistat";

    /* renamed from: f  reason: collision with root package name */
    private static final String f518f = "uploader";
    private static final String g = "3.0.13";
    private static final String h = "Android";
    private static final int i = 200;
    private static final int j = 1;
    private static final int k = -1;
    private static final int l = 3;
    private static volatile i m;
    private final byte[] n = new byte[0];
    private FileLock o;
    private FileChannel p;
    private g q;
    private a r;

    private class a extends Handler {
        public a(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message message) {
            super.handleMessage(message);
            if (message.what == 1) {
                i.this.g();
            }
        }
    }

    private i() {
        e();
    }

    private int a(int i2) {
        if (i2 == 1) {
            return -1;
        }
        return i2 == 3 ? 0 : 1;
    }

    public static i a() {
        if (m == null) {
            synchronized (i.class) {
                if (m == null) {
                    m = new i();
                }
            }
        }
        return m;
    }

    private String a(JSONArray jSONArray, String str) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("id", str);
            a(str, jSONObject);
            jSONObject.put(d.I, e.d());
            jSONObject.put("rc", m.h());
            jSONObject.put(d.j, c.b());
            jSONObject.put(d.k, b.u());
            jSONObject.put(d.l, h);
            jSONObject.put(d.Z, m.a(am.a()));
            jSONObject.put(d.m, this.q != null ? this.q.a() : 0);
            jSONObject.put(d.n, String.valueOf(r.b()));
            jSONObject.put(d.o, m.e());
            jSONObject.put(d.p, a.a(am.b()));
            String[] p2 = b.p();
            if (p2 != null && p2.length > 0) {
                jSONObject.put(d.v, a(p2));
            }
            jSONObject.put(d.q, m.d());
            jSONObject.put("n", l.b(am.a()));
            jSONObject.put(d.t, b.i());
            jSONObject.put(d.u, jSONArray);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        return jSONObject.toString();
    }

    private JSONArray a(String[] strArr) {
        JSONArray jSONArray = new JSONArray();
        for (int i2 = 0; i2 < strArr.length; i2++) {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put(strArr[i2], a.a(strArr[i2]));
                jSONArray.put(jSONObject);
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
        return jSONArray;
    }

    private void a(Message message) {
        synchronized (this.n) {
            if (this.r == null || this.q == null) {
                e();
            }
            this.r.sendMessage(message);
        }
    }

    private void a(String str, JSONObject jSONObject) {
        try {
            if (!b.f() && TextUtils.isEmpty(str)) {
                Context a2 = am.a();
                jSONObject.put(d.C, e.b(a2));
                jSONObject.put(d.J, e.k(a2));
                jSONObject.put(d.L, e.n(a2));
                jSONObject.put(d.O, e.q(a2));
                jSONObject.put("ai", e.p(a2));
            }
        } catch (Exception unused) {
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:22:0x0080  */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x0088  */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x00c1  */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x00c3 A[SYNTHETIC] */
    private void a(com.xiaomi.stat.a.b[] bVarArr, String str) {
        boolean z;
        k a2;
        if (bVarArr.length == 0) {
            com.xiaomi.stat.d.k.e(f514b, "privacy policy or network state not matched");
            return;
        }
        k a3 = com.xiaomi.stat.a.c.a().a(bVarArr);
        AtomicInteger atomicInteger = new AtomicInteger();
        boolean z2 = a3 != null ? a3.f366c : true;
        com.xiaomi.stat.d.k.b(f514b + a3);
        boolean z3 = z2;
        boolean z4 = false;
        while (true) {
            if (a3 == null) {
                z = z4;
                break;
            }
            ArrayList<Long> arrayList = a3.f365b;
            try {
                String a4 = a(a3.f364a, str);
                com.xiaomi.stat.d.k.a(f514b, " payload:", a4);
                String b2 = b(a(a(a4)));
                com.xiaomi.stat.d.k.a(f514b, " encodePayload ", b2);
                String c2 = g.a().c();
                if (com.xiaomi.stat.d.k.b()) {
                    c2 = com.xiaomi.stat.d.k.f571c;
                }
                String a5 = c.a(c2, (Map<String, String>) c(b2), true);
                com.xiaomi.stat.d.k.a(f514b, " sendDataToServer response: ", a5);
                if (TextUtils.isEmpty(a5)) {
                    z = false;
                    if (!z) {
                        com.xiaomi.stat.a.c.a().a(arrayList);
                    } else {
                        atomicInteger.addAndGet(1);
                    }
                    com.xiaomi.stat.d.k.b(f514b, " deleteData= " + z + " retryCount.get()= " + atomicInteger.get());
                    if (z3 || (!z && atomicInteger.get() > 3)) {
                        break;
                    }
                    a2 = com.xiaomi.stat.a.c.a().a(bVarArr);
                    if (a2 == null) {
                        z3 = a2.f366c;
                    }
                    z4 = z;
                    a3 = a2;
                } else {
                    z = b(a5);
                    if (!z) {
                    }
                    com.xiaomi.stat.d.k.b(f514b, " deleteData= " + z + " retryCount.get()= " + atomicInteger.get());
                    a2 = com.xiaomi.stat.a.c.a().a(bVarArr);
                    if (a2 == null) {
                    }
                    z4 = z;
                    a3 = a2;
                }
            } catch (Exception unused) {
            }
        }
        g gVar = this.q;
        if (gVar != null) {
            gVar.b(z);
        }
    }

    public static byte[] a(String str) {
        GZIPOutputStream gZIPOutputStream;
        ByteArrayOutputStream byteArrayOutputStream;
        byte[] bArr = null;
        try {
            byteArrayOutputStream = new ByteArrayOutputStream(str.getBytes("UTF-8").length);
            try {
                gZIPOutputStream = new GZIPOutputStream(byteArrayOutputStream);
                try {
                    gZIPOutputStream.write(str.getBytes("UTF-8"));
                    gZIPOutputStream.finish();
                    bArr = byteArrayOutputStream.toByteArray();
                } catch (Exception e2) {
                    e = e2;
                }
            } catch (Exception e3) {
                e = e3;
                gZIPOutputStream = null;
                try {
                    com.xiaomi.stat.d.k.e(f514b, " zipData failed! " + e.toString());
                    j.a((OutputStream) byteArrayOutputStream);
                    j.a((OutputStream) gZIPOutputStream);
                    return bArr;
                } catch (Throwable th) {
                    th = th;
                    j.a((OutputStream) byteArrayOutputStream);
                    j.a((OutputStream) gZIPOutputStream);
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                gZIPOutputStream = null;
                j.a((OutputStream) byteArrayOutputStream);
                j.a((OutputStream) gZIPOutputStream);
                throw th;
            }
        } catch (Exception e4) {
            e = e4;
            byteArrayOutputStream = null;
            gZIPOutputStream = null;
            com.xiaomi.stat.d.k.e(f514b, " zipData failed! " + e.toString());
            j.a((OutputStream) byteArrayOutputStream);
            j.a((OutputStream) gZIPOutputStream);
            return bArr;
        } catch (Throwable th3) {
            th = th3;
            byteArrayOutputStream = null;
            gZIPOutputStream = null;
            j.a((OutputStream) byteArrayOutputStream);
            j.a((OutputStream) gZIPOutputStream);
            throw th;
        }
        j.a((OutputStream) byteArrayOutputStream);
        j.a((OutputStream) gZIPOutputStream);
        return bArr;
    }

    private byte[] a(byte[] bArr) {
        return com.xiaomi.stat.b.i.a().a(bArr);
    }

    private String b(byte[] bArr) {
        return com.xiaomi.stat.d.d.a(bArr);
    }

    private void b(boolean z) {
        a(c(z), com.xiaomi.stat.b.d.a().a(z));
    }

    private boolean b(String str) {
        try {
            int optInt = new JSONObject(str).optInt(f515c);
            if (optInt != 200) {
                if (!(optInt == 1002 || optInt == 1004 || optInt == 1005 || optInt == 1006 || optInt == 1007)) {
                    if (optInt != 1011) {
                        if (optInt == 2002 || optInt == 1012) {
                            com.xiaomi.stat.b.i.a().a(true);
                            com.xiaomi.stat.b.d.a().b();
                        }
                    }
                }
                com.xiaomi.stat.b.i.a().a(true);
                com.xiaomi.stat.b.d.a().b();
                return false;
            }
            return true;
        } catch (Exception e2) {
            com.xiaomi.stat.d.k.d(f514b, "parseUploadingResult exception ", e2);
            return false;
        }
    }

    private HashMap<String, String> c(String str) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("ai", am.b());
        hashMap.put(d.f521b, "3.0.13");
        hashMap.put(d.f522c, f513a);
        hashMap.put(d.f523d, m.g());
        hashMap.put("p", str);
        hashMap.put(d.ak, com.xiaomi.stat.b.i.a().c());
        hashMap.put(d.g, com.xiaomi.stat.b.i.a().b());
        return hashMap;
    }

    private com.xiaomi.stat.a.b[] c(boolean z) {
        ArrayList<String> h2 = h();
        int size = h2.size();
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < size; i2++) {
            String str = h2.get(i2);
            int a2 = a(new f(str, z).a());
            if (a2 != -1) {
                arrayList.add(new com.xiaomi.stat.a.b(str, a2, z));
            }
        }
        com.xiaomi.stat.a.b d2 = d(z);
        if (d2 != null) {
            arrayList.add(d2);
        }
        return (com.xiaomi.stat.a.b[]) arrayList.toArray(new com.xiaomi.stat.a.b[arrayList.size()]);
    }

    private com.xiaomi.stat.a.b d(boolean z) {
        int a2 = new f(z).a();
        com.xiaomi.stat.d.k.b(f514b, " createMainAppFilter: " + a2);
        int a3 = a(a2);
        if (a3 != -1) {
            return new com.xiaomi.stat.a.b(null, a3, z);
        }
        return null;
    }

    private void e() {
        HandlerThread handlerThread = new HandlerThread("mi_analytics_uploader_worker");
        handlerThread.start();
        this.r = new a(handlerThread.getLooper());
        this.q = new g(handlerThread.getLooper());
    }

    private void f() {
        g gVar = this.q;
        if (gVar != null) {
            gVar.c();
        }
    }

    /* access modifiers changed from: private */
    public void g() {
        if (i()) {
            b(false);
            b(true);
            j();
        }
    }

    private ArrayList<String> h() {
        String[] p2 = b.p();
        int length = p2 != null ? p2.length : 0;
        ArrayList<String> arrayList = new ArrayList<>();
        for (int i2 = 0; i2 < length; i2++) {
            if (!TextUtils.isEmpty(p2[i2])) {
                arrayList.add(p2[i2]);
            }
        }
        return arrayList;
    }

    private boolean i() {
        File file = new File(am.a().getFilesDir(), f517e);
        if (!file.exists()) {
            file.mkdir();
        }
        try {
            this.p = new FileOutputStream(new File(file, f518f)).getChannel();
            try {
                this.o = this.p.tryLock();
                if (this.o != null) {
                    com.xiaomi.stat.d.k.c(f514b, " acquire lock for uploader");
                    if (this.o == null) {
                        try {
                            this.p.close();
                            this.p = null;
                        } catch (Exception unused) {
                        }
                    }
                    return true;
                }
                com.xiaomi.stat.d.k.c(f514b, " acquire lock for uploader failed");
                if (this.o == null) {
                    try {
                        this.p.close();
                        this.p = null;
                    } catch (Exception unused2) {
                    }
                }
                return false;
            } catch (Exception e2) {
                com.xiaomi.stat.d.k.c(f514b, " acquire lock for uploader failed with " + e2);
                if (this.o == null) {
                    try {
                        this.p.close();
                        this.p = null;
                    } catch (Exception unused3) {
                    }
                }
                return false;
            } catch (Throwable th) {
                if (this.o == null) {
                    try {
                        this.p.close();
                        this.p = null;
                    } catch (Exception unused4) {
                    }
                }
                throw th;
            }
        } catch (FileNotFoundException e3) {
            com.xiaomi.stat.d.k.c(f514b, " acquire lock for uploader failed with " + e3);
            return false;
        }
    }

    private void j() {
        try {
            if (this.o != null) {
                this.o.release();
                this.o = null;
            }
            if (this.p != null) {
                this.p.close();
                this.p = null;
            }
            com.xiaomi.stat.d.k.c(f514b, " releaseLock lock for uploader");
        } catch (IOException e2) {
            com.xiaomi.stat.d.k.c(f514b, " releaseLock lock for uploader failed with " + e2);
        }
    }

    public void a(boolean z) {
        g gVar = this.q;
        if (gVar != null) {
            gVar.a(z);
        }
    }

    public void b() {
        this.q.b();
        c();
    }

    public void c() {
        if (!l.a()) {
            f();
        } else if (!b.a() || !b.c()) {
            com.xiaomi.stat.d.k.b(f514b, " postToServer statistic disable or network disable access! ");
        } else if (!b.C()) {
            com.xiaomi.stat.d.k.b(f514b, " postToServer can not upload data because of configuration!");
        } else {
            Message obtain = Message.obtain();
            obtain.what = 1;
            a(obtain);
        }
    }

    public synchronized void d() {
        if (this.q != null) {
            this.q.d();
        }
    }
}
