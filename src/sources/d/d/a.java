package d.d;

import android.content.Context;
import android.os.Build;
import android.os.SystemProperties;
import android.text.TextUtils;
import com.android.camera.data.DataRepository;

/* compiled from: Build */
public class a extends Build {
    public static final boolean Ag = "dior".equals(Build.DEVICE);
    public static final boolean Bg = (Ag && "LTETD".equals(SystemProperties.get("ro.boot.modem")));
    public static final boolean Cg = (Ag && "LTEW".equals(SystemProperties.get("ro.boot.modem")));
    public static final boolean Dg = "HM2014811".equals(Build.DEVICE);
    public static final boolean Eg = ("HM2014812".equals(Build.DEVICE) || "HM2014821".equals(Build.DEVICE));
    public static final boolean Fg = ("HM2014813".equals(Build.DEVICE) || "HM2014112".equals(Build.DEVICE));
    public static final boolean Gg = "HM2014818".equals(Build.DEVICE);
    public static final boolean Hg = "HM2014817".equals(Build.DEVICE);
    public static final boolean IS_DEBUGGABLE;
    public static final boolean Ig = "HM2014819".equals(Build.DEVICE);
    public static final boolean Jg = (Dg || Eg || Fg || Gg || Hg || Ig);
    public static final boolean Kg = "gucci".equals(Build.DEVICE);
    public static final boolean Lg = (Kg && "cm".equals(SystemProperties.get("persist.sys.modem")));
    public static final boolean Mg = (Kg && "cu".equals(SystemProperties.get("persist.sys.modem")));
    public static final boolean Ng = (Kg && "ct".equals(SystemProperties.get("persist.sys.modem")));
    public static final boolean Og = (jg && Om());
    public static final boolean Pg = (qg && "CDMA".equals(SystemProperties.get("persist.radio.modem")));
    public static final boolean Qg = (qg && "LTE-CMCC".equals(SystemProperties.get("persist.radio.modem")));
    public static final boolean Rg = (qg && "LTE-CU".equals(SystemProperties.get("persist.radio.modem")));
    public static final boolean Sg = (qg && "LTE-CT".equals(SystemProperties.get("persist.radio.modem")));
    public static final boolean Tg = (qg && "LTE-India".equals(SystemProperties.get("persist.radio.modem")));
    public static final boolean Ug = (qg && "LTE-SEAsa".equals(SystemProperties.get("persist.radio.modem")));
    public static final boolean Vg = "HM2013022".equals(Build.DEVICE);
    public static final boolean Wg = "cu".equals(SystemProperties.get("ro.carrier.name"));
    public static final boolean Xg = ("cm".equals(SystemProperties.get("ro.carrier.name")) && ("cn_chinamobile".equals(SystemProperties.get("ro.miui.cust_variant")) || "cn_cta".equals(SystemProperties.get("ro.miui.cust_variant"))));
    public static final boolean Yg = ("cm".equals(SystemProperties.get("ro.carrier.name")) && "cn_cmcooperation".equals(SystemProperties.get("ro.miui.cust_variant")));
    public static final boolean Zg = "ct".equals(SystemProperties.get("ro.carrier.name"));
    public static final boolean _g = (!TextUtils.isEmpty(Build.VERSION.INCREMENTAL) && Build.VERSION.INCREMENTAL.matches(hg));
    public static final boolean bh = ("user".equals(Build.TYPE) && !_g);
    public static final boolean dh = (_g || bh);
    public static final boolean eh = SystemProperties.get("ro.product.mod_device", "").endsWith("_alpha");
    public static final boolean fh = (!SystemProperties.getBoolean("persist.sys.miui_optimization", !"1".equals(SystemProperties.get("ro.miui.cts"))));
    public static final boolean gh = "1".equals(SystemProperties.get("ro.miui.cta"));
    private static final String hg = "\\d+.\\d+.\\d+(-internal)?";
    public static final boolean hh = "cm".equals(SystemProperties.get("ro.cust.test"));
    private static final String ig = "([A-Z]{3}|[A-Z]{7})\\d+.\\d+";
    public static final boolean ih = "cu".equals(SystemProperties.get("ro.cust.test"));
    public static final boolean jg = ("mione".equals(Build.DEVICE) || "mione_plus".equals(Build.DEVICE));
    public static final boolean jh = "ct".equals(SystemProperties.get("ro.cust.test"));
    public static final boolean kg = ("aries".equals(Build.DEVICE) || "taurus".equals(Build.DEVICE) || "taurus_td".equals(Build.DEVICE));
    public static final boolean kh = "1".equals(SystemProperties.get("persist.sys.func_limit_switch"));
    public static final boolean lg = kg;
    public static final boolean lh = (SystemProperties.get("ro.boot.hwc", "").contains("GLOBAL") || DataRepository.dataItemFeature().c_0x44());
    public static final boolean mg = "lte26007".equals(Build.DEVICE);
    public static final boolean mh = SystemProperties.get("ro.product.mod_device", "").endsWith("_global");
    public static final boolean ng = ("MI 1S".equals(Build.MODEL) || "MI 1SC".equals(Build.MODEL));
    public static final boolean nh = Pm();
    public static final boolean og = ("MI 2A".equals(Build.MODEL) || "MI 2A TD".equals(Build.MODEL));
    public static final String oh = "persist.sys.user_mode";
    public static final boolean pg = ("cancro".equals(Build.DEVICE) && Build.MODEL.startsWith("MI 3"));
    public static final int ph = 0;
    public static final boolean qg = ("cancro".equals(Build.DEVICE) && Build.MODEL.startsWith("MI 4"));
    public static final int qh = 1;
    public static final boolean rg = "virgo".equals(Build.DEVICE);
    public static final String rh = Nm();
    public static final boolean sg = (jg || pg || qg || rg);
    public static final boolean sh = SystemProperties.getBoolean("ro.miui.has_cust_partition", false);
    public static final boolean tg = "mocha".equals(Build.DEVICE);
    public static final boolean th = SystemProperties.get("ro.miui.cust_device", "").endsWith("_pro");
    public static final boolean ug = "flo".equals(Build.DEVICE);
    public static final boolean vg = "armani".equals(Build.DEVICE);
    public static final boolean wg = ("HM2014011".equals(Build.DEVICE) || "HM2014012".equals(Build.DEVICE));
    public static final boolean xg = "HM2014501".equals(Build.DEVICE);
    public static final boolean yg = ("HM2013022".equals(Build.DEVICE) || "HM2013023".equals(Build.DEVICE) || vg || wg);
    public static final boolean zg = ("lcsh92_wet_jb9".equals(Build.DEVICE) || "lcsh92_wet_tdd".equals(Build.DEVICE));

    static {
        boolean z = true;
        if (SystemProperties.getInt("ro.debuggable", 0) != 1) {
            z = false;
        }
        IS_DEBUGGABLE = z;
    }

    protected a() throws InstantiationException {
        throw new InstantiationException("Cannot instantiate utility class");
    }

    public static String Ca() {
        return !lh ? SystemProperties.get("ro.miui.cust_variant", "cn") : SystemProperties.get("ro.miui.cust_variant", "hk");
    }

    public static int Da() {
        return SystemProperties.getInt(oh, 0);
    }

    public static boolean F(Context context) {
        return d.g.a.getBoolean("support_torch", true);
    }

    private static String Nm() {
        String str = SystemProperties.get("ro.miui.userdata_version", "");
        if ("".equals(str)) {
            return "Unavailable";
        }
        String str2 = lh ? "global" : "cn";
        String str3 = SystemProperties.get("ro.carrier.name", "");
        if (!"".equals(str3)) {
            str3 = "_" + str3;
        }
        return String.format("%s(%s%s)", str, str2, str3);
    }

    private static boolean Om() {
        String str = SystemProperties.get("ro.soc.name");
        return "msm8660".equals(str) || "unkown".equals(str);
    }

    private static boolean Pm() {
        return SystemProperties.get("ro.build.characteristics").contains("tablet");
    }

    public static void a(Context context, int i) {
        SystemProperties.set(oh, Integer.toString(i));
    }

    public static String getRegion() {
        return SystemProperties.get("ro.miui.region", "CN");
    }

    public static boolean k(String str) {
        return getRegion().equalsIgnoreCase(str);
    }
}
