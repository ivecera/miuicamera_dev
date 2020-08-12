package d.d;

import android.content.Context;
import android.os.Build;
import android.os.SystemProperties;
import android.text.TextUtils;
import com.android.camera.data.DataRepository;

/* compiled from: Build */
public class a extends Build {
    public static final boolean IS_DEBUGGABLE;
    public static final boolean build_type_user_or_version_incremental_XYZ = (version_incremental_is_XYZ || build_type_user_version_incremental_not_XYZ);
    public static final boolean build_type_user_version_incremental_not_XYZ = ("user".equals(Build.TYPE) && !version_incremental_is_XYZ);
    public static final boolean carrier_name_is_cm_variant_cn_chinamobile_cta = ("cm".equals(SystemProperties.get("ro.carrier.name")) && ("cn_chinamobile".equals(SystemProperties.get("ro.miui.cust_variant")) || "cn_cta".equals(SystemProperties.get("ro.miui.cust_variant"))));
    public static final boolean carrier_name_is_cm_variant_cn_cmcooperation = ("cm".equals(SystemProperties.get("ro.carrier.name")) && "cn_cmcooperation".equals(SystemProperties.get("ro.miui.cust_variant")));
    public static final boolean carrier_name_is_ct = "ct".equals(SystemProperties.get("ro.carrier.name"));
    public static final boolean carrier_name_is_cu = "cu".equals(SystemProperties.get("ro.carrier.name"));
    public static final boolean device_is_HM2013022 = "HM2013022".equals(Build.DEVICE);
    public static final boolean device_is_HM201302x = ("HM2013022".equals(Build.DEVICE) || "HM2013023".equals(Build.DEVICE) || device_is_armani || device_is_HM201401x);
    public static final boolean device_is_HM201401x = ("HM2014011".equals(Build.DEVICE) || "HM2014012".equals(Build.DEVICE));
    public static final boolean device_is_HM2014501 = "HM2014501".equals(Build.DEVICE);
    public static final boolean device_is_HM2014811 = "HM2014811".equals(Build.DEVICE);
    public static final boolean device_is_HM2014812_or_HM2014821 = ("HM2014812".equals(Build.DEVICE) || "HM2014821".equals(Build.DEVICE));
    public static final boolean device_is_HM2014813_or_HM2014112 = ("HM2014813".equals(Build.DEVICE) || "HM2014112".equals(Build.DEVICE));
    public static final boolean device_is_HM2014817 = "HM2014817".equals(Build.DEVICE);
    public static final boolean device_is_HM2014818 = "HM2014818".equals(Build.DEVICE);
    public static final boolean device_is_HM2014819 = "HM2014819".equals(Build.DEVICE);
    public static final boolean device_is_HM2014xxx = (device_is_HM2014811 || device_is_HM2014812_or_HM2014821 || device_is_HM2014813_or_HM2014112 || device_is_HM2014818 || device_is_HM2014817 || device_is_HM2014819);
    public static final boolean device_is_aries_or_taurus = ("aries".equals(Build.DEVICE) || "taurus".equals(Build.DEVICE) || "taurus_td".equals(Build.DEVICE));
    public static final boolean device_is_armani = "armani".equals(Build.DEVICE);
    public static final boolean device_is_cancro_mi4 = ("cancro".equals(Build.DEVICE) && Build.MODEL.startsWith("MI 4"));
    public static final boolean device_is_cancro_mi4_modem_CDMA = (device_is_cancro_mi4 && "CDMA".equals(SystemProperties.get("persist.radio.modem")));
    public static final boolean device_is_cancro_mi4_modem_LTE_CMCC = (device_is_cancro_mi4 && "LTE-CMCC".equals(SystemProperties.get("persist.radio.modem")));
    public static final boolean device_is_cancro_mi4_modem_LTE_CT = (device_is_cancro_mi4 && "LTE-CT".equals(SystemProperties.get("persist.radio.modem")));
    public static final boolean device_is_cancro_mi4_modem_LTE_CU = (device_is_cancro_mi4 && "LTE-CU".equals(SystemProperties.get("persist.radio.modem")));
    public static final boolean device_is_cancro_mi4_modem_LTE_India = (device_is_cancro_mi4 && "LTE-India".equals(SystemProperties.get("persist.radio.modem")));
    public static final boolean device_is_cancro_mi4_modem_LTE_SEAsa = (device_is_cancro_mi4 && "LTE-SEAsa".equals(SystemProperties.get("persist.radio.modem")));
    public static final boolean device_is_cancro_model_mi3 = ("cancro".equals(Build.DEVICE) && Build.MODEL.startsWith("MI 3"));
    public static final boolean device_is_dior = "dior".equals(Build.DEVICE);
    public static final boolean device_is_dior_modem_LTETD = (device_is_dior && "LTETD".equals(SystemProperties.get("ro.boot.modem")));
    public static final boolean device_is_dior_modem_LTEW = (device_is_dior && "LTEW".equals(SystemProperties.get("ro.boot.modem")));
    public static final boolean device_is_flo = "flo".equals(Build.DEVICE);
    public static final boolean device_is_gucci = "gucci".equals(Build.DEVICE);
    public static final boolean device_is_gucci_modem_cm = (device_is_gucci && "cm".equals(SystemProperties.get("persist.sys.modem")));
    public static final boolean device_is_gucci_modem_ct = (device_is_gucci && "ct".equals(SystemProperties.get("persist.sys.modem")));
    public static final boolean device_is_gucci_modem_cu = (device_is_gucci && "cu".equals(SystemProperties.get("persist.sys.modem")));
    public static final boolean device_is_lcsh92_wet_x = ("lcsh92_wet_jb9".equals(Build.DEVICE) || "lcsh92_wet_tdd".equals(Build.DEVICE));
    public static final boolean device_is_lte26007 = "lte26007".equals(Build.DEVICE);
    public static final boolean device_is_mione = ("mione".equals(Build.DEVICE) || "mione_plus".equals(Build.DEVICE));
    public static final boolean device_is_mione_cancro_virgo = (device_is_mione || device_is_cancro_model_mi3 || device_is_cancro_mi4 || device_is_virgo);
    public static final boolean device_is_mione_soc_msm9660 = (device_is_mione && soc_is_msm9660());
    public static final boolean device_is_mocha = "mocha".equals(Build.DEVICE);
    public static final boolean device_is_virgo = "virgo".equals(Build.DEVICE);
    private static final String digit_digit_digit = "\\d+.\\d+.\\d+(-internal)?";
    public static final boolean has_not_cust_part = SystemProperties.getBoolean("ro.miui.has_cust_partition", false);
    private static final String ig = "([A-Z]{3}|[A-Z]{7})\\d+.\\d+";
    public static final boolean miui_cust_device_ends_pro = SystemProperties.get("ro.miui.cust_device", "").endsWith("_pro");
    public static final boolean miui_optimization_disabled = (!SystemProperties.getBoolean("persist.sys.miui_optimization", !"1".equals(SystemProperties.get("ro.miui.cts"))));
    public static final boolean model_is_m1sx = ("MI 1S".equals(Build.MODEL) || "MI 1SC".equals(Build.MODEL));
    public static final boolean model_is_m2ax = ("MI 2A".equals(Build.MODEL) || "MI 2A TD".equals(Build.MODEL));
    public static final boolean nh = ro_build_characteristics_has_tablet();
    public static final boolean persist_sys_func_limit_switch_is_1 = "1".equals(SystemProperties.get("persist.sys.func_limit_switch"));
    public static final int ph = 0;
    public static final boolean product_mod_device_ends_alpha = SystemProperties.get("ro.product.mod_device", "").endsWith("_alpha");
    public static final boolean product_mod_device_ends_global = SystemProperties.get("ro.product.mod_device", "").endsWith("_global");
    public static final int qh = 1;
    public static final String rh = getUserdataVersionRegionCarrier();
    public static final boolean ro_boot_hwc_contains_GLOBAL_or_is_android_one = (SystemProperties.get("ro.boot.hwc", "").contains("GLOBAL") || DataRepository.dataItemFeature().c_0x44());
    public static final boolean ro_cust_test_is_cm = "cm".equals(SystemProperties.get("ro.cust.test"));
    public static final boolean ro_cust_test_is_ct = "ct".equals(SystemProperties.get("ro.cust.test"));
    public static final boolean ro_cust_test_is_cu = "cu".equals(SystemProperties.get("ro.cust.test"));
    public static final boolean ro_miui_cta_is_1 = "1".equals(SystemProperties.get("ro.miui.cta"));
    public static final String user_mode_key = "persist.sys.user_mode";
    public static final boolean version_incremental_is_XYZ = (!TextUtils.isEmpty(Build.VERSION.INCREMENTAL) && Build.VERSION.INCREMENTAL.matches(digit_digit_digit));

    static {
        boolean z = true;
        boolean z2 = device_is_aries_or_taurus;
        if (SystemProperties.getInt("ro.debuggable", 0) != 1) {
            z = false;
        }
        IS_DEBUGGABLE = z;
    }

    protected a() throws InstantiationException {
        throw new InstantiationException("Cannot instantiate utility class");
    }

    public static boolean F(Context context) {
        return d.g.a.getBoolean("support_torch", true);
    }

    public static String getCustVariant() {
        return !ro_boot_hwc_contains_GLOBAL_or_is_android_one ? SystemProperties.get("ro.miui.cust_variant", "cn") : SystemProperties.get("ro.miui.cust_variant", "hk");
    }

    public static String getRegion() {
        return SystemProperties.get("ro.miui.region", "CN");
    }

    public static int getUserMode() {
        return SystemProperties.getInt(user_mode_key, 0);
    }

    private static String getUserdataVersionRegionCarrier() {
        String str = SystemProperties.get("ro.miui.userdata_version", "");
        if ("".equals(str)) {
            return "Unavailable";
        }
        String str2 = ro_boot_hwc_contains_GLOBAL_or_is_android_one ? "global" : "cn";
        String str3 = SystemProperties.get("ro.carrier.name", "");
        if (!"".equals(str3)) {
            str3 = "_" + str3;
        }
        return String.format("%s(%s%s)", str, str2, str3);
    }

    public static boolean isRegionEqualTo(String str) {
        return getRegion().equalsIgnoreCase(str);
    }

    private static boolean ro_build_characteristics_has_tablet() {
        return SystemProperties.get("ro.build.characteristics").contains("tablet");
    }

    public static void setUserMode(Context context, int i) {
        SystemProperties.set(user_mode_key, Integer.toString(i));
    }

    private static boolean soc_is_msm9660() {
        String str = SystemProperties.get("ro.soc.name");
        return "msm8660".equals(str) || "unkown".equals(str);
    }
}
