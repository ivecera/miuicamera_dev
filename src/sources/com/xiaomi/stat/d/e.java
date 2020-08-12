package com.xiaomi.stat.d;

import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Point;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Process;
import android.os.RemoteException;
import android.provider.Settings;
import android.support.v4.os.EnvironmentCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.Display;
import android.view.WindowManager;
import com.mi.config.d;
import com.xiaomi.stat.am;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.UUID;

public class e {
    private static String A = null;
    private static String B = null;
    private static String C = null;
    private static String D = null;
    private static String E = null;
    private static Boolean F = null;
    private static String G = null;
    private static String H = null;
    private static String I = null;
    private static boolean J = false;

    /* renamed from: a  reason: collision with root package name */
    private static final String f542a = "DeviceUtil";

    /* renamed from: b  reason: collision with root package name */
    private static final int f543b = 15;

    /* renamed from: c  reason: collision with root package name */
    private static final int f544c = 14;

    /* renamed from: d  reason: collision with root package name */
    private static final String f545d = "";

    /* renamed from: e  reason: collision with root package name */
    private static final long f546e = 7776000000L;

    /* renamed from: f  reason: collision with root package name */
    private static final String f547f = "mistat";
    private static final String g = "device_id";
    private static final String h = "anonymous_id";
    private static Method i;
    private static Method j;
    private static Method k;
    private static Object l;
    private static Method m = Class.forName("android.telephony.TelephonyManager").getMethod("getImei", Integer.TYPE);
    private static String n;
    private static String o;
    private static String p;
    private static String q;
    private static String r;
    private static String s;
    private static String t;
    private static String u;
    private static String v;
    private static String w;
    private static String x;
    private static String y;
    private static String z;

    private static class a {

        /* renamed from: a  reason: collision with root package name */
        private static final String f548a = "GAIDClient";

        /* renamed from: com.xiaomi.stat.d.e$a$a  reason: collision with other inner class name */
        private static final class C0019a implements ServiceConnection {

            /* renamed from: a  reason: collision with root package name */
            private static final int f549a = 30000;

            /* renamed from: b  reason: collision with root package name */
            private boolean f550b;

            /* renamed from: c  reason: collision with root package name */
            private IBinder f551c;

            private C0019a() {
                this.f550b = false;
            }

            public IBinder a() throws InterruptedException {
                IBinder iBinder = this.f551c;
                if (iBinder != null) {
                    return iBinder;
                }
                if (iBinder == null && !this.f550b) {
                    synchronized (this) {
                        wait(30000);
                        if (this.f551c == null) {
                            throw new InterruptedException("Not connect or connect timeout to google play service");
                        }
                    }
                }
                return this.f551c;
            }

            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                synchronized (this) {
                    this.f551c = iBinder;
                    notifyAll();
                }
            }

            public void onServiceDisconnected(ComponentName componentName) {
                this.f550b = true;
                this.f551c = null;
            }
        }

        private static final class b implements IInterface {

            /* renamed from: a  reason: collision with root package name */
            private IBinder f552a;

            public b(IBinder iBinder) {
                this.f552a = iBinder;
            }

            public String a() throws RemoteException {
                if (this.f552a == null) {
                    return "";
                }
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.ads.identifier.internal.IAdvertisingIdService");
                    this.f552a.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public boolean a(boolean z) throws RemoteException {
                boolean z2 = false;
                if (this.f552a == null) {
                    return false;
                }
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.ads.identifier.internal.IAdvertisingIdService");
                    obtain.writeInt(z ? 1 : 0);
                    this.f552a.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        z2 = true;
                    }
                    return z2;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public IBinder asBinder() {
                return this.f552a;
            }
        }

        private a() {
        }

        static String a(Context context) {
            if (!b(context)) {
                k.b(f548a, "Google play service is not available");
                return "";
            }
            C0019a aVar = new C0019a();
            try {
                Intent intent = new Intent("com.google.android.gms.ads.identifier.service.START");
                intent.setPackage("com.google.android.gms");
                if (context.bindService(intent, aVar, 1)) {
                    String a2 = new b(aVar.a()).a();
                    context.unbindService(aVar);
                    return a2;
                }
            } catch (Exception e2) {
                k.b(f548a, "Query Google ADID failed ", e2);
            } catch (Throwable th) {
                context.unbindService(aVar);
                throw th;
            }
            context.unbindService(aVar);
            return "";
        }

        private static boolean b(Context context) {
            try {
                context.getPackageManager().getPackageInfo("com.android.vending", 16384);
                return true;
            } catch (PackageManager.NameNotFoundException unused) {
                return false;
            }
        }
    }

    private static class b {

        /* renamed from: a  reason: collision with root package name */
        private static final String f553a = "box";

        /* renamed from: b  reason: collision with root package name */
        private static final String f554b = "tvbox";

        /* renamed from: c  reason: collision with root package name */
        private static final String f555c = "projector";

        /* renamed from: d  reason: collision with root package name */
        private static final String f556d = "tv";

        /* renamed from: e  reason: collision with root package name */
        private static final String f557e = "mi_device_mac";

        /* renamed from: f  reason: collision with root package name */
        private static Signature[] f558f;
        private static final Signature g = new Signature("3082033b30820223a003020102020900a07a328482f70d2a300d06092a864886f70d01010505003035310b30090603550406130255533113301106035504080c0a43616c69666f726e69613111300f06035504070c084d6f756e7461696e301e170d3133303430313033303831325a170d3430303831373033303831325a3035310b30090603550406130255533113301106035504080c0a43616c69666f726e69613111300f06035504070c084d6f756e7461696e30820120300d06092a864886f70d01010105000382010d00308201080282010100ac678c9234a0226edbeb75a43e8e18f632d8c8a094c087fffbbb0b5e4429d845e36bffbe2d7098e320855258aa777368c18c538f968063d5d61663dc946ab03acbb31d00a27d452e12e6d42865e27d6d0ad2d8b12cf3b3096a7ec66a21db2a6a697857fd4d29fb4cdf294b3371d7601f2e3f190c0164efa543897026c719b334808e4f612fe3a3da589115fc30f9ca89862feefdf31a9164ecb295dcf7767e673be2192dda64f88189fd6e6ebd62e572c7997c2385a0ea9292ec799dee8f87596fc73aa123fb6f577d09ac0c123179c3bdbc978c2fe6194eb9fa4ab3658bfe8b44040bb13fe7809409e622189379fbc63966ab36521793547b01673ecb5f15cf020103a350304e301d0603551d0e0416041447203684e562385ada79108c4c94c5055037592f301f0603551d2304183016801447203684e562385ada79108c4c94c5055037592f300c0603551d13040530030101ff300d06092a864886f70d010105050003820101008d530fe05c6fe694c7559ddb5dd2c556528dd3cad4f7580f439f9a90a4681d37ce246b9a6681bdd5a5437f0b8bba903e39bac309fc0e9ee5553681612e723e9ec4f6abab6b643b33013f09246a9b5db7703b96f838fb27b00612f5fcd431bea32f68350ae51a4a1d012c520c401db7cccc15a7b19c4310b0c3bfc625ce5744744d0b9eeb02b0a4e7d51ed59849ce580b9f7c3062c84b9a0b13cc211e1c916c289820266a610801e3316c915649804571b147beadbf88d3b517ee04121d40630853f2f2a506bb788620de9648faeacff568e5033a666316bc2046526674ed3de25ceefdc4ad3628f1a230fd41bf9ca9f6a078173850dba555768fe1c191483ad9");

        private b() {
        }

        private static Signature a(PackageInfo packageInfo) {
            Signature[] signatureArr;
            if (packageInfo == null || (signatureArr = packageInfo.signatures) == null || signatureArr.length <= 0) {
                return null;
            }
            return signatureArr[0];
        }

        private static <T> T a(Class<?> cls, String str) {
            try {
                Field declaredField = cls.getDeclaredField(str);
                declaredField.setAccessible(true);
                return declaredField.get(null);
            } catch (Exception e2) {
                k.d(e.f542a, "getStaticVariableValue exception", e2);
                return null;
            }
        }

        private static String a() {
            try {
                Class<?> cls = Class.forName("mitv.common.ConfigurationManager");
                int parseInt = Integer.parseInt(String.valueOf(cls.getMethod("getProductCategory", new Class[0]).invoke(cls.getMethod("getInstance", new Class[0]).invoke(cls, new Object[0]), new Object[0])));
                Class<?> cls2 = Class.forName("mitv.tv.TvContext");
                return parseInt == Integer.parseInt(String.valueOf(a(cls2, "PRODUCT_CATEGORY_MITV"))) ? "tv" : parseInt == Integer.parseInt(String.valueOf(a(cls2, "PRODUCT_CATEGORY_MIBOX"))) ? f553a : parseInt == Integer.parseInt(String.valueOf(a(cls2, "PRODUCT_CATEGORY_MITVBOX"))) ? f554b : parseInt == Integer.parseInt(String.valueOf(a(cls2, "PRODUCT_CATEGORY_MIPROJECTOR"))) ? f555c : "";
            } catch (Exception e2) {
                k.b(e.f542a, "getMiTvProductCategory exception", e2);
                return "";
            }
        }

        private static String a(String str) {
            String str2 = "";
            BufferedReader bufferedReader = null;
            try {
                BufferedReader bufferedReader2 = new BufferedReader(new InputStreamReader(new FileInputStream(str)), 512);
                try {
                    String readLine = bufferedReader2.readLine();
                    if (readLine != null) {
                        str2 = str2 + readLine;
                    }
                    j.a((Reader) bufferedReader2);
                } catch (Exception e2) {
                    e = e2;
                    bufferedReader = bufferedReader2;
                    try {
                        k.d(e.f542a, "catEntry exception", e);
                        j.a((Reader) bufferedReader);
                        return str2;
                    } catch (Throwable th) {
                        th = th;
                        j.a((Reader) bufferedReader);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    bufferedReader = bufferedReader2;
                    j.a((Reader) bufferedReader);
                    throw th;
                }
            } catch (Exception e3) {
                e = e3;
                k.d(e.f542a, "catEntry exception", e);
                j.a((Reader) bufferedReader);
                return str2;
            }
            return str2;
        }

        static boolean a(Context context) {
            if (f558f == null) {
                f558f = new Signature[]{c(context)};
            }
            Signature[] signatureArr = f558f;
            return signatureArr[0] != null && signatureArr[0].equals(g);
        }

        public static String b(Context context) {
            if (Build.VERSION.SDK_INT >= 17) {
                try {
                    String string = Settings.Global.getString(context.getContentResolver(), f557e);
                    if (!TextUtils.isEmpty(string)) {
                        return string;
                    }
                } catch (Exception unused) {
                }
            }
            try {
                String str = Build.PRODUCT;
                String a2 = e.b("ro.product.model");
                boolean z = true;
                if (!TextUtils.equals("tv", a()) || "batman".equals(str) || "conan".equals(str)) {
                    if (!"augustrush".equals(str)) {
                        if (!"casablanca".equals(str)) {
                            z = false;
                        }
                    }
                }
                String a3 = TextUtils.equals("me2", str) ? e.b("persist.service.bdroid.bdaddr") : ((!TextUtils.equals("transformers", str) || !TextUtils.equals("MiBOX4C", a2)) && !TextUtils.equals("dolphin-zorro", str)) ? z ? a("/sys/class/net/eth0/address") : a("ro.boot.btmac") : a("/sys/class/net/wlan0/address");
                return !TextUtils.isEmpty(a3) ? a3.trim() : "";
            } catch (Exception e2) {
                k.b(e.f542a, "getMiTvMac exception", e2);
                return "";
            }
        }

        private static Signature c(Context context) {
            try {
                return a(context.getPackageManager().getPackageInfo("android", 64));
            } catch (Exception unused) {
                return null;
            }
        }
    }

    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x0041 */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0047  */
    /* JADX WARNING: Removed duplicated region for block: B:15:? A[RETURN, SYNTHETIC] */
    static {
        try {
            i = Class.forName("android.os.SystemProperties").getMethod("get", String.class);
        } catch (Exception unused) {
        }
        Class<?> cls = Class.forName("miui.telephony.TelephonyManagerEx");
        l = cls.getMethod("getDefault", new Class[0]).invoke(null, new Object[0]);
        j = cls.getMethod("getImeiList", new Class[0]);
        k = cls.getMethod("getMeidList", new Class[0]);
        try {
            if (Build.VERSION.SDK_INT < 21) {
            }
        } catch (Exception unused2) {
        }
    }

    private static List<String> A(Context context) {
        try {
            ArrayList arrayList = new ArrayList();
            Class<?> cls = Class.forName("android.telephony.TelephonyManager");
            if (!h()) {
                String deviceId = ((TelephonyManager) cls.getMethod("getDefault", new Class[0]).invoke(null, new Object[0])).getDeviceId();
                if (c(deviceId)) {
                    arrayList.add(deviceId);
                }
                return arrayList;
            }
            String deviceId2 = ((TelephonyManager) cls.getMethod("getDefault", Integer.TYPE).invoke(null, 0)).getDeviceId();
            String deviceId3 = ((TelephonyManager) cls.getMethod("getDefault", Integer.TYPE).invoke(null, 1)).getDeviceId();
            if (c(deviceId2)) {
                arrayList.add(deviceId2);
            }
            if (c(deviceId3)) {
                arrayList.add(deviceId3);
            }
            return arrayList;
        } catch (Exception e2) {
            k.b(f542a, "getImeiListBelowLollipop failed ex: " + e2.getMessage());
            return null;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:18:0x0041 A[Catch:{ Exception -> 0x0091 }] */
    private static String B(Context context) {
        String str;
        Enumeration<NetworkInterface> networkInterfaces;
        if (a(context, "android.permission.ACCESS_WIFI_STATE")) {
            if (Build.VERSION.SDK_INT < 23) {
                try {
                    str = ((WifiManager) context.getSystemService("wifi")).getConnectionInfo().getMacAddress();
                } catch (Exception e2) {
                    k.d(f542a, "getMAC exception: ", e2);
                }
                if (TextUtils.isEmpty(str) || "02:00:00:00:00:00".equals(str)) {
                    networkInterfaces = NetworkInterface.getNetworkInterfaces();
                    while (networkInterfaces.hasMoreElements()) {
                        NetworkInterface nextElement = networkInterfaces.nextElement();
                        byte[] hardwareAddress = nextElement.getHardwareAddress();
                        if (hardwareAddress != null) {
                            if (hardwareAddress.length != 0) {
                                StringBuilder sb = new StringBuilder();
                                int length = hardwareAddress.length;
                                for (int i2 = 0; i2 < length; i2++) {
                                    sb.append(String.format("%02x:", Byte.valueOf(hardwareAddress[i2])));
                                }
                                if (sb.length() > 0) {
                                    sb.deleteCharAt(sb.length() - 1);
                                }
                                if ("wlan0".equals(nextElement.getName())) {
                                    return sb.toString();
                                }
                            }
                        }
                    }
                }
            }
            str = null;
            try {
                networkInterfaces = NetworkInterface.getNetworkInterfaces();
                while (networkInterfaces.hasMoreElements()) {
                }
            } catch (Exception e3) {
                k.b(f542a, "queryMac failed ex: " + e3.getMessage());
            }
        }
        return null;
    }

    private static boolean C(Context context) {
        return (context.getResources().getConfiguration().screenLayout & 15) >= 3;
    }

    private static boolean D(Context context) {
        return b.a(context) || (context.getResources().getConfiguration().uiMode & 15) == 4;
    }

    public static String a(Context context) {
        if (com.xiaomi.stat.b.f()) {
            return "";
        }
        if (!TextUtils.isEmpty(n)) {
            return n;
        }
        String a2 = p.a(context);
        if (!TextUtils.isEmpty(a2)) {
            n = a2;
            return n;
        }
        y(context);
        if (TextUtils.isEmpty(n)) {
            return "";
        }
        p.a(context, n);
        return n;
    }

    public static void a() {
        boolean z2 = r.b() - com.xiaomi.stat.b.w() > f546e;
        if (TextUtils.isEmpty(com.xiaomi.stat.b.x()) || z2) {
            com.xiaomi.stat.b.i(UUID.randomUUID().toString());
        }
    }

    private static boolean a(Context context, String str) {
        return context.checkPermission(str, Process.myPid(), Process.myUid()) == 0;
    }

    private static boolean a(List<String> list) {
        for (String str : list) {
            if (!c(str)) {
                return true;
            }
        }
        return false;
    }

    public static String b() {
        return Build.MODEL;
    }

    public static String b(Context context) {
        if (!TextUtils.isEmpty(v)) {
            return v;
        }
        String a2 = a(context);
        if (TextUtils.isEmpty(a2)) {
            return "";
        }
        v = g.c(a2);
        return v;
    }

    /* access modifiers changed from: private */
    public static String b(String str) {
        try {
            if (i != null) {
                return String.valueOf(i.invoke(null, str));
            }
        } catch (Exception e2) {
            k.b(f542a, "getProp failed ex: " + e2.getMessage());
        }
        return null;
    }

    private static boolean b(List<String> list) {
        for (String str : list) {
            if (!d(str)) {
                return true;
            }
        }
        return false;
    }

    public static String c() {
        return Build.MANUFACTURER;
    }

    public static String c(Context context) {
        if (!TextUtils.isEmpty(A)) {
            return A;
        }
        String a2 = a(context);
        if (TextUtils.isEmpty(a2)) {
            return "";
        }
        A = g.d(a2);
        return A;
    }

    private static boolean c(String str) {
        return str != null && str.length() == 15 && !str.matches("^0*$");
    }

    public static String d() {
        if (!TextUtils.isEmpty(H)) {
            return H;
        }
        boolean f2 = com.xiaomi.stat.b.f();
        String t2 = com.xiaomi.stat.b.t();
        if (!TextUtils.isEmpty(t2)) {
            if (!f2) {
                H = t2;
                return H;
            }
            long b2 = r.b();
            if (b2 - com.xiaomi.stat.b.w() <= f546e) {
                H = t2;
                com.xiaomi.stat.b.b(b2);
                return H;
            }
        }
        if (f2 && !p.k(am.a())) {
            Context a2 = am.a();
            p.b(a2, true);
            String string = a2.getSharedPreferences(f547f, 0).getString(h, null);
            k.c(f542a, "last version instance id: " + string);
            H = string;
        }
        if (TextUtils.isEmpty(H)) {
            H = e();
        }
        com.xiaomi.stat.b.g(H);
        if (f2) {
            com.xiaomi.stat.b.b(r.b());
        }
        return H;
    }

    public static String d(Context context) {
        if (com.xiaomi.stat.b.f()) {
            return "";
        }
        if (!TextUtils.isEmpty(o)) {
            return o;
        }
        String b2 = p.b(context);
        if (!TextUtils.isEmpty(b2)) {
            o = b2;
            return o;
        }
        y(context);
        if (TextUtils.isEmpty(o)) {
            return "";
        }
        p.b(context, o);
        return o;
    }

    private static boolean d(String str) {
        return str != null && str.length() == 14 && !str.matches("^0*$");
    }

    private static String e() {
        String x2 = com.xiaomi.stat.b.x();
        if (!TextUtils.isEmpty(x2)) {
            return x2;
        }
        String uuid = UUID.randomUUID().toString();
        com.xiaomi.stat.b.i(uuid);
        return uuid;
    }

    public static String e(Context context) {
        if (!TextUtils.isEmpty(w)) {
            return w;
        }
        String d2 = d(context);
        if (TextUtils.isEmpty(d2)) {
            return "";
        }
        w = g.c(d2);
        return w;
    }

    public static String f(Context context) {
        if (!TextUtils.isEmpty(B)) {
            return B;
        }
        String d2 = d(context);
        if (TextUtils.isEmpty(d2)) {
            return "";
        }
        B = g.d(d2);
        return B;
    }

    private static List<String> f() {
        if (j == null || i()) {
            return null;
        }
        try {
            List<String> list = (List) j.invoke(l, new Object[0]);
            if (list == null || list.size() <= 0 || a(list)) {
                return null;
            }
            return list;
        } catch (Exception e2) {
            k.b(f542a, "getImeiListFromMiui failed ex: " + e2.getMessage());
            return null;
        }
    }

    public static String g(Context context) {
        if (com.xiaomi.stat.b.f()) {
            return "";
        }
        if (!TextUtils.isEmpty(p)) {
            return p;
        }
        String c2 = p.c(context);
        if (!TextUtils.isEmpty(c2)) {
            p = c2;
            return p;
        }
        String s2 = s(context);
        if (TextUtils.isEmpty(s2)) {
            return "";
        }
        p = s2;
        p.c(context, p);
        return p;
    }

    private static List<String> g() {
        ArrayList arrayList = new ArrayList();
        String b2 = b("ro.ril.miui.imei0");
        if (TextUtils.isEmpty(b2)) {
            b2 = b("ro.ril.oem.imei");
        }
        if (TextUtils.isEmpty(b2)) {
            b2 = b("persist.radio.imei");
        }
        if (c(b2)) {
            arrayList.add(b2);
        }
        if (h()) {
            String b3 = b("ro.ril.miui.imei1");
            if (TextUtils.isEmpty(b3)) {
                b3 = b("ro.ril.oem.imei2");
            }
            if (TextUtils.isEmpty(b3)) {
                b3 = b("persist.radio.imei2");
            }
            if (c(b3)) {
                arrayList.add(b3);
            }
        }
        return arrayList;
    }

    public static String h(Context context) {
        if (!TextUtils.isEmpty(x)) {
            return x;
        }
        String g2 = g(context);
        if (TextUtils.isEmpty(g2)) {
            return "";
        }
        x = g.c(g2);
        return x;
    }

    private static boolean h() {
        if ("dsds".equals(b("persist.radio.multisim.config"))) {
            return true;
        }
        String str = Build.DEVICE;
        return "lcsh92_wet_jb9".equals(str) || "lcsh92_wet_tdd".equals(str) || "HM2013022".equals(str) || "HM2013023".equals(str) || "armani".equals(str) || "HM2014011".equals(str) || "HM2014012".equals(str);
    }

    public static String i(Context context) {
        if (!TextUtils.isEmpty(C)) {
            return C;
        }
        String g2 = g(context);
        if (TextUtils.isEmpty(g2)) {
            return "";
        }
        C = g.d(g2);
        return C;
    }

    private static boolean i() {
        if (Build.VERSION.SDK_INT >= 21) {
            return false;
        }
        String str = Build.DEVICE;
        String b2 = b("persist.radio.modem");
        if ("HM2014812".equals(str) || "HM2014821".equals(str)) {
            return true;
        }
        return ("gucci".equals(str) && "ct".equals(b("persist.sys.modem"))) || "CDMA".equals(b2) || "HM1AC".equals(b2) || "LTE-X5-ALL".equals(b2) || "LTE-CT".equals(b2) || "MI 3C".equals(Build.MODEL);
    }

    public static String j(Context context) {
        if (com.xiaomi.stat.b.f()) {
            return "";
        }
        if (!TextUtils.isEmpty(q)) {
            return q;
        }
        String d2 = p.d(context);
        if (!TextUtils.isEmpty(d2)) {
            q = d2;
            return q;
        }
        String b2 = w(context) ? b.b(context) : B(context);
        if (TextUtils.isEmpty(b2)) {
            return "";
        }
        q = b2;
        p.d(context, q);
        return q;
    }

    private static boolean j() {
        try {
            Class<?> cls = Class.forName("d.d.a");
            if (cls != null) {
                return ((Boolean) cls.getField("IS_TABLET").get(null)).booleanValue();
            }
        } catch (Exception unused) {
        }
        try {
            Class<?> cls2 = Class.forName("d.g.a");
            if (cls2 != null) {
                return ((Boolean) cls2.getMethod("getBoolean", String.class, Boolean.TYPE).invoke(null, d.zv, false)).booleanValue();
            }
        } catch (Exception unused2) {
        }
        return false;
    }

    public static String k(Context context) {
        if (!TextUtils.isEmpty(y)) {
            return y;
        }
        String j2 = j(context);
        if (TextUtils.isEmpty(j2)) {
            return "";
        }
        y = g.c(j2);
        return y;
    }

    public static String l(Context context) {
        if (!TextUtils.isEmpty(D)) {
            return D;
        }
        String j2 = j(context);
        if (TextUtils.isEmpty(j2)) {
            return "";
        }
        D = g.d(j2);
        return D;
    }

    public static String m(Context context) {
        if (com.xiaomi.stat.b.f()) {
            return "";
        }
        if (!TextUtils.isEmpty(r)) {
            return r;
        }
        String e2 = p.e(context);
        if (!TextUtils.isEmpty(e2)) {
            r = e2;
            return r;
        }
        String t2 = t(context);
        if (TextUtils.isEmpty(t2)) {
            return "";
        }
        r = t2;
        p.e(context, r);
        return r;
    }

    public static String n(Context context) {
        if (!TextUtils.isEmpty(z)) {
            return z;
        }
        String m2 = m(context);
        if (TextUtils.isEmpty(m2)) {
            return "";
        }
        z = g.c(m2);
        return z;
    }

    public static String o(Context context) {
        if (!TextUtils.isEmpty(E)) {
            return E;
        }
        String m2 = m(context);
        if (TextUtils.isEmpty(m2)) {
            return "";
        }
        E = g.d(m2);
        return E;
    }

    public static String p(Context context) {
        if (!TextUtils.isEmpty(s)) {
            return s;
        }
        String string = Settings.System.getString(context.getContentResolver(), "android_id");
        if (TextUtils.isEmpty(string)) {
            return "";
        }
        s = string;
        return s;
    }

    public static String q(Context context) {
        if (!TextUtils.isEmpty(t)) {
            return t;
        }
        try {
            String type = context.getContentResolver().getType(Uri.parse("content://com.miui.analytics.server.AnalyticsProvider/aaid"));
            if (!TextUtils.isEmpty(type)) {
                t = type;
                return type;
            }
            Object invoke = Class.forName("android.provider.MiuiSettings$Ad").getDeclaredMethod("getAaid", ContentResolver.class).invoke(null, context.getContentResolver());
            if (!(invoke instanceof String) || TextUtils.isEmpty((String) invoke)) {
                return "";
            }
            t = (String) invoke;
            return t;
        } catch (Exception e2) {
            k.b(f542a, "getAaid failed ex: " + e2.getMessage());
            return "";
        }
    }

    public static String r(Context context) {
        if (!TextUtils.isEmpty(u)) {
            return u;
        }
        String a2 = a.a(context);
        if (TextUtils.isEmpty(a2)) {
            return "";
        }
        u = a2;
        return u;
    }

    public static String s(Context context) {
        if (a(context, "android.permission.READ_PHONE_STATE")) {
            Method method = k;
            if (method != null) {
                try {
                    List list = (List) method.invoke(l, new Object[0]);
                    if (list != null && list.size() > 0 && !b(list)) {
                        Collections.sort(list);
                        return (String) list.get(0);
                    }
                } catch (Exception e2) {
                    k.b(f542a, "queryMeid failed ex: " + e2.getMessage());
                }
            }
            try {
                Class<?> cls = Class.forName("android.telephony.TelephonyManager");
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
                String str = null;
                if (cls != null) {
                    if (Build.VERSION.SDK_INT >= 26) {
                        Method method2 = cls.getMethod("getMeid", new Class[0]);
                        if (method2 != null) {
                            str = (String) method2.invoke(telephonyManager, new Object[0]);
                        }
                    } else {
                        Method method3 = cls.getMethod("getDeviceId", new Class[0]);
                        if (method3 != null) {
                            str = (String) method3.invoke(telephonyManager, new Object[0]);
                        }
                    }
                }
                if (d(str)) {
                    return str;
                }
            } catch (Exception e3) {
                k.b(f542a, "queryMeid->getMeid failed ex: " + e3.getMessage());
            }
        }
        String b2 = b("persist.radio.meid");
        if (d(b2)) {
            return b2;
        }
        String b3 = b("ro.ril.oem.meid");
        return d(b3) ? b3 : "";
    }

    public static String t(Context context) {
        String str = null;
        if (Build.VERSION.SDK_INT < 26) {
            str = Build.SERIAL;
        } else if (a(context, "android.permission.READ_PHONE_STATE")) {
            try {
                Method method = Class.forName("android.os.Build").getMethod("getSerial", new Class[0]);
                if (method != null) {
                    str = (String) method.invoke(null, new Object[0]);
                }
            } catch (Exception e2) {
                k.b(f542a, "querySerial failed ex: " + e2.getMessage());
            }
        }
        if (TextUtils.isEmpty(str) || EnvironmentCompat.MEDIA_UNKNOWN.equals(str)) {
            return "";
        }
        r = str;
        return str;
    }

    public static String u(Context context) {
        Display defaultDisplay = ((WindowManager) context.getSystemService("window")).getDefaultDisplay();
        Point point = new Point();
        if (Build.VERSION.SDK_INT >= 17) {
            defaultDisplay.getRealSize(point);
        } else {
            defaultDisplay.getSize(point);
        }
        return String.format("%d*%d", Integer.valueOf(point.y), Integer.valueOf(point.x));
    }

    public static String v(Context context) {
        if (!TextUtils.isEmpty(G)) {
            return G;
        }
        if (j()) {
            G = "Pad";
            return G;
        } else if (C(context)) {
            G = "Pad";
            return G;
        } else if (!D(context)) {
            return "Phone";
        } else {
            G = "Tv";
            return G;
        }
    }

    public static boolean w(Context context) {
        if (F == null) {
            F = Boolean.valueOf(b.a(context));
        }
        return F.booleanValue();
    }

    public static String x(Context context) {
        if (!J) {
            J = true;
            if (!p.i(context)) {
                p.a(context, true);
                p.f(context, context.getSharedPreferences(f547f, 0).getString("device_id", null));
            }
            I = p.j(context);
        }
        return I;
    }

    private static List<String> y(Context context) {
        List<String> list;
        if (a(context, "android.permission.READ_PHONE_STATE")) {
            list = f();
            if (list == null || list.isEmpty()) {
                list = Build.VERSION.SDK_INT >= 21 ? z(context) : A(context);
            }
        } else {
            list = null;
        }
        if (list == null || list.isEmpty()) {
            list = g();
        }
        if (list != null && !list.isEmpty()) {
            Collections.sort(list);
            n = list.get(0);
            if (list.size() >= 2) {
                o = list.get(1);
            }
        }
        return list;
    }

    private static List<String> z(Context context) {
        if (m == null) {
            return null;
        }
        try {
            ArrayList arrayList = new ArrayList();
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
            String str = (String) m.invoke(telephonyManager, 0);
            if (c(str)) {
                arrayList.add(str);
            }
            if (h()) {
                String str2 = (String) m.invoke(telephonyManager, 1);
                if (c(str2)) {
                    arrayList.add(str2);
                }
            }
            return arrayList;
        } catch (Exception e2) {
            k.b(f542a, "getImeiListAboveLollipop failed ex: " + e2.getMessage());
            return null;
        }
    }
}
