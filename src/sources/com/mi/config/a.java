package com.mi.config;

import android.content.res.Resources;
import android.os.Build;
import android.os.SystemProperties;
import android.support.v4.util.SimpleArrayMap;
import android.text.TextUtils;
import android.util.Size;
import com.android.camera.CameraAppImpl;
import com.android.camera.CameraSettings;
import com.android.camera.HybridZoomingSystem;
import com.android.camera.Util;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.DataItemBase;
import com.android.camera.log.Log;
import com.xiaomi.stat.d;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Locale;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: DataItemFeature */
public class a extends DataItemBase implements c {
    private static final String Ji = "feature_";
    private static final boolean Ki = false;
    private static final String TAG = "DataFeature";
    private String Ii;

    public a() {
        d(Ji + b.vu, true);
    }

    private int N(String str) {
        if (TextUtils.isEmpty(str)) {
            return -1;
        }
        char charAt = str.charAt(0);
        if (Character.isDigit(charAt)) {
            return Integer.parseInt(String.valueOf(charAt));
        }
        return -1;
    }

    private Size O(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        String substring = str.substring(str.indexOf(58) + 1);
        if (TextUtils.isEmpty(substring)) {
            return null;
        }
        String[] split = substring.replace(" ", "").split("x");
        if (split.length >= 2) {
            return new Size(Integer.valueOf(split[0]).intValue(), Integer.valueOf(split[1]).intValue());
        }
        return null;
    }

    private boolean Rm() {
        if (b.jl() || b.kl()) {
            return false;
        }
        return getBoolean(c.c_16001_0x0001, false);
    }

    private void c(String str, boolean z) throws JSONException {
        JSONObject jSONObject = new JSONObject(str);
        Iterator<String> keys = jSONObject.keys();
        SimpleArrayMap<String, Object> values = getValues();
        String str2 = null;
        while (keys.hasNext()) {
            String next = keys.next();
            if (TextUtils.equals("parent", next)) {
                if (z) {
                    Object opt = jSONObject.opt(next);
                    if (opt instanceof String) {
                        str2 = opt.toString();
                    }
                } else {
                    throw new UnsupportedOperationException("Parent json file can't nest parent");
                }
            } else if (z) {
                if (values.put(next, jSONObject.opt(next)) != null) {
                    throw new IllegalStateException("Duplicate key is found in the configuration file: " + next);
                }
            } else if (!values.containsKey(next)) {
                values.put(next, jSONObject.opt(next));
            } else {
                Log.w(TAG, "parseJson: ignore key = " + next + ", value = " + jSONObject.opt(next));
            }
        }
        if (!TextUtils.isEmpty(str2)) {
            d(str2, false);
        }
    }

    private int c_28041_0x0004_or_0() {
        return getInt(c.c_28041_0x0004, 0);
    }

    private boolean c_s_a_u_q() {
        return getBoolean(c.c_s_a_u_q, false);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0073, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:?, code lost:
        r6.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0078, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0079, code lost:
        r8.addSuppressed(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x007c, code lost:
        throw r0;
     */
    private void d(String str, boolean z) {
        Resources resources = CameraAppImpl.getAndroidContext().getResources();
        int identifier = resources.getIdentifier(str, "raw", "com.android.camera");
        if (identifier <= 0) {
            Log.e(TAG, "feature list default, firstInit = " + z + ", name = " + str);
            return;
        }
        Log.d(TAG, "parseJsonFile: start >>> " + str);
        long currentTimeMillis = System.currentTimeMillis();
        StringBuilder sb = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(resources.openRawResource(identifier)));
        while (true) {
            String readLine = bufferedReader.readLine();
            if (readLine != null) {
                sb.append(readLine);
            } else {
                c(sb.toString(), z);
                try {
                    bufferedReader.close();
                } catch (IOException | JSONException e2) {
                    e2.printStackTrace();
                }
                Log.d(TAG, "parseJsonFile: end >>> " + str + ", firstInit = " + z + ", cost " + (System.currentTimeMillis() - currentTimeMillis) + d.H);
                return;
            }
        }
    }

    public int Ab() {
        return N(getString(c.c_19040_0x0005, ""));
    }

    public boolean Bd() {
        return getBoolean(c.c_33066_0x0004, false) && !d.d.a.lh;
    }

    public boolean Be() {
        return getBoolean(c.c_26813_0x0001, false) && !SystemProperties.getBoolean("close.append.yuv", false);
    }

    public float Db() {
        return (float) getDoubleFromValues(c.c_0x02_p_c_r_v, 0.8766000270843506d);
    }

    public int Hb() {
        return N(getString(c.c_19039_0x0004, ""));
    }

    public int Ib() {
        if (b.gv) {
            return 6;
        }
        return getInt(c.c_9006_0x0001, 5);
    }

    public Size Nb() {
        return O(getString(c.c_19040_0x0005, ""));
    }

    public Size Ob() {
        return O(getString(c.c_19039_0x0004, ""));
    }

    public boolean Pd() {
        if (Util.isGlobalVersion()) {
            return false;
        }
        if (!b.bv || Build.VERSION.SDK_INT != 28) {
            return getBoolean(c.c_0x60, false);
        }
        return false;
    }

    public boolean Sd() {
        return 3 == Hb();
    }

    public boolean Vb() {
        return Hb() < 0 || getBoolean(c.c_0x0f, false);
    }

    public boolean Ye() {
        return (tc() || uc()) && getBoolean(c.s_p_l_i_e, false);
    }

    public int c_0x01_p_g_a_v_OR_280() {
        return getInt(c.c_0x01_p_g_a_v, 280);
    }

    public boolean c_0x0a() {
        return getBoolean(c.c_0x0a, false);
    }

    public boolean c_0x0c() {
        return getBoolean(c.c_0x0c, false) && tc();
    }

    public boolean c_0x0e() {
        return getBoolean(c.c_0x0e, false);
    }

    public boolean c_0x10_OR_T() {
        return getBoolean(c.c_0x10, true);
    }

    public boolean c_0x11() {
        return getBoolean(c.c_0x11, false);
    }

    public boolean c_0x16() {
        return getBoolean(c.c_0x16, false);
    }

    public boolean c_0x17() {
        return getBoolean(c.c_0x17, false);
    }

    public boolean c_0x19_OR_T() {
        return getBoolean(c.c_0x19, true);
    }

    public boolean c_0x1a_ANDNOT_global() {
        if (!Util.isGlobalVersion()) {
            return false;
        }
        return getBoolean(c.c_0x1a, false);
    }

    public boolean c_0x1b() {
        return getBoolean(c.c_0x1b, false);
    }

    public boolean c_0x1c() {
        return getBoolean(c.c_0x1c, false);
    }

    public boolean c_0x20_OR_T() {
        return getBoolean(c.c_0x20, true);
    }

    public boolean c_0x21() {
        return getBoolean(c.c_0x21, false);
    }

    public boolean c_0x23() {
        if (Build.VERSION.SDK_INT < 28) {
            return false;
        }
        return getBoolean(c.c_0x23, false);
    }

    public boolean c_0x25() {
        return getBoolean(c.c_0x25, false);
    }

    public boolean c_0x26() {
        return getBoolean(c.c_0x26, false);
    }

    public boolean c_0x27_OR_T() {
        return getBoolean(c.c_0x27, true);
    }

    public boolean c_0x31() {
        return getBoolean(c.c_0x31, false);
    }

    public boolean c_0x34_OR_T() {
        return getBoolean(c.c_0x34, true);
    }

    public boolean c_0x35_OR_T() {
        return getBoolean(c.c_0x35, true);
    }

    public boolean c_0x36() {
        return getBoolean(c.c_0x36, true);
    }

    public boolean c_0x38() {
        return getBoolean(c.c_0x38, false);
    }

    public boolean c_0x39() {
        return getBoolean(c.c_0x39, false);
    }

    public boolean c_0x42_m() {
        return getBoolean(c.c_0x42_m, false);
    }

    public boolean c_0x44() {
        return getBoolean(c.c_0x44, false);
    }

    public boolean c_0x46() {
        return getBoolean(c.c_0x46, false);
    }

    public boolean c_0x48() {
        return getBoolean(c.c_0x48, false);
    }

    public boolean c_0x4a_1() {
        return getBoolean(c.c_0x4a_1, false);
    }

    public boolean c_0x50() {
        return getBoolean(c.c_0x50, false);
    }

    public boolean c_0x51() {
        return getBoolean(c.c_0x51, false);
    }

    public boolean c_0x53() {
        return getBoolean(c.c_0x53, false);
    }

    public boolean c_0x54() {
        return getBoolean(c.c_0x54, false);
    }

    public boolean c_0x55_OR_T() {
        return getBoolean(c.c_0x55, true);
    }

    public int c_0x57_OR_0() {
        return getInt(c.c_0x57, 0);
    }

    public boolean c_0x58() {
        return getBoolean(c.c_0x58, false);
    }

    public boolean c_0x59() {
        return getBoolean(c.c_0x59, false);
    }

    public int c_0x5a_OR_0() {
        return getInt(c.c_0x5a, 0);
    }

    public boolean c_0x5c() {
        return getBoolean(c.c_0x5c, false);
    }

    public boolean c_0x5e() {
        return getBoolean(c.c_0x5e, false);
    }

    public boolean c_13254_0x01() {
        return getBoolean(c.c_13254_0x01, false);
    }

    public boolean c_19039_0x0000() {
        return getBoolean(c.c_19039_0x0000, false);
    }

    public boolean c_19039_0x0001() {
        return getBoolean(c.c_19039_0x0001, false);
    }

    public boolean c_19039_0x0002() {
        return getBoolean(c.c_19039_0x0002, false);
    }

    public boolean c_19039_0x0003() {
        return getBoolean(c.c_19039_0x0003, false);
    }

    public boolean c_19039_0x0005_EQ_1() {
        return getInt(c.c_19039_0x0005, -1) == 1;
    }

    public boolean c_19039_0x0005_eq_2() {
        return getInt(c.c_19039_0x0005, -1) == 2;
    }

    public boolean c_19039_0x0006() {
        return getBoolean(c.c_19039_0x0006, false);
    }

    public boolean c_19039_0x0008() {
        return getBoolean(c.c_19039_0x0008, false);
    }

    public boolean c_19039_0x0009() {
        return getBoolean(c.c_19039_0x0009, false);
    }

    public boolean c_19039_0x0010() {
        return getBoolean(c.c_19039_0x0010, false);
    }

    public boolean c_19039_0x0011() {
        return getBoolean(c.c_19039_0x0011, false);
    }

    public boolean c_19039_0x0012() {
        return getBoolean(c.c_19039_0x0012, false);
    }

    public boolean c_19039_0x0013() {
        return getBoolean(c.c_19039_0x0013, false);
    }

    public boolean c_19039_0x0015() {
        return getBoolean(c.c_19039_0x0015, false);
    }

    public boolean c_19039_0x0016() {
        return getBoolean(c.c_19039_0x0016, false);
    }

    public boolean c_19039_0x0017() {
        return getBoolean(c.c_19039_0x0017, false);
    }

    public boolean c_19039_0x0018() {
        return getBoolean(c.c_19039_0x0018, false);
    }

    public boolean c_19039_0x0019() {
        return getBoolean(c.c_19039_0x0019, false);
    }

    public boolean c_19039_0x0020() {
        return getBoolean(c.c_19039_0x0020, false);
    }

    public boolean c_19039_0x0021() {
        return getBoolean(c.c_19039_0x0021, false);
    }

    public boolean c_19086_0x0001() {
        return getBoolean(c.c_19086_0x0001, false);
    }

    public boolean c_22367_0x0001() {
        return getBoolean(c.c_22367_0x0001, false);
    }

    public boolean c_22367_0x0002_OR_T() {
        return getBoolean(c.c_22367_0x0002, true);
    }

    public boolean c_22367_0x0003() {
        return getBoolean(c.c_22367_0x0003, false);
    }

    public boolean c_22367_0x0005() {
        return getBoolean(c.c_22367_0x0005, false);
    }

    public String c_22367_0x0006() {
        return getString(c.c_22367_0x0006, null);
    }

    public boolean c_22367_0x0007() {
        return getBoolean(c.c_22367_0x0007, false);
    }

    public boolean c_22367_0x0008_OR_0_EQ_0() {
        return getInt(c.c_22367_0x0008, 0) == 0;
    }

    public boolean c_22367_0x0008_OR_0_EQ_1() {
        return getInt(c.c_22367_0x0008, 0) == 1;
    }

    public boolean c_22367_0x0009() {
        return getBoolean(c.c_22367_0x0009, false);
    }

    public int c_22367_0x000A_OR_0() {
        return getInt(c.c_22367_0x000A, 0);
    }

    public int c_26813_0x0002_OR_0() {
        return getInt(c.c_26813_0x0002, 0);
    }

    public boolean c_26813_0x0003() {
        return getBoolean(c.c_26813_0x0003, false);
    }

    public boolean c_27810_0x0001() {
        return getBoolean(c.c_27810_0x0001, false);
    }

    public boolean c_27810_0x0002_HAS_CAPTURE_INTENT() {
        return Arrays.asList(getString(c.c_27810_0x0002, "").toUpperCase().split(":")).contains("CAPTURE_INTENT");
    }

    public boolean c_27810_0x0002_HAS_MACRO() {
        return Arrays.asList(getString(c.c_27810_0x0002, "").toUpperCase().split(":")).contains("MACRO");
    }

    public boolean c_27810_0x0002_HAS_NO_PIXEL() {
        return Arrays.asList(getString(c.c_27810_0x0002, "").toUpperCase().split(":")).contains("NO_PIXEL");
    }

    public boolean c_27810_0x0002_HAS_PRO() {
        return Arrays.asList(getString(c.c_27810_0x0002, "").toUpperCase().split(":")).contains("PRO");
    }

    public boolean c_27810_0x0002_HAS_ULTRA_WIDE() {
        return Arrays.asList(getString(c.c_27810_0x0002, "").toUpperCase().split(":")).contains("ULTRA_WIDE");
    }

    public boolean c_27810_0x0003() {
        return getBoolean(c.c_27810_0x0003, false);
    }

    public String c_27810_0x0004() {
        return getString(c.c_27810_0x0004, null);
    }

    public boolean c_27810_0x0005() {
        return getBoolean(c.c_27810_0x0005, false);
    }

    public boolean c_27810_0x0006() {
        return getBoolean(c.c_27810_0x0006, false);
    }

    public boolean c_27810_0x0007() {
        return getBoolean(c.c_27810_0x0007, false);
    }

    public boolean c_27845_0x0001() {
        return getBoolean(c.c_27845_0x0001, false);
    }

    public boolean c_27845_0x0002() {
        return getBoolean(c.c_27845_0x0002, false);
    }

    public boolean c_27845_0x0003() {
        return getBoolean(c.c_27845_0x0003, false);
    }

    public boolean c_28041_0x0001() {
        return getBoolean(c.c_28041_0x0001, false);
    }

    public boolean c_28041_0x0005() {
        return getBoolean(c.c_28041_0x0005, false);
    }

    public boolean c_28041_0x0007() {
        return getBoolean(c.c_28041_0x0007, false);
    }

    public int c_28041_0x0008_OR_0() {
        return getInt(c.c_28041_0x0008, 0);
    }

    public boolean c_28196_0x0002() {
        return getBoolean(c.c_28196_0x0002, false);
    }

    public boolean c_28196_0x0003() {
        return getBoolean(c.c_28196_0x0003, true);
    }

    public boolean c_32889_0x0001() {
        return getBoolean(c.c_32889_0x0001, false);
    }

    public int c_32889_0x0002_OR_0() {
        return getInt(c.c_32889_0x0002, 0);
    }

    public boolean c_33066_0x0001() {
        return getBoolean(c.c_33066_0x0001, false);
    }

    public boolean c_33066_0x0003() {
        return getBoolean(c.c_33066_0x0003, false);
    }

    public boolean c_35893_0x0001() {
        return getBoolean(c.c_35893_0x0001, false);
    }

    public boolean c_35893_0x0002() {
        return getBoolean(c.c_35893_0x0002, false);
    }

    public boolean c_35905_0x0001() {
        return getBoolean(c.c_35905_0x0001, false);
    }

    public int c_35955_0x0002_OR_1() {
        return getInt(c.c_35955_0x0002, 1);
    }

    public boolean c_35955_0x0004() {
        return getBoolean(c.c_35955_0x0004, false);
    }

    public boolean c_36211_0x0001() {
        return getBoolean(c.c_36211_0x0001, false);
    }

    public boolean c_36658_0x0002() {
        return getBoolean(c.c_36658_0x0002, false);
    }

    public boolean c_9006_0x0000() {
        return getBoolean(c.c_9006_0x0000, false);
    }

    public boolean c_9006_0x0002() {
        return getBoolean(c.c_9006_0x0002, false);
    }

    public boolean c_9006_0x0003() {
        return getBoolean(c.c_9006_0x0003, false);
    }

    public boolean c_9006_0x0006() {
        return getBoolean(c.c_9006_0x0006, false);
    }

    public boolean c_9006_0x0007() {
        return getBoolean(c.c_9006_0x0007, false);
    }

    public boolean c_d_e_f_w() {
        return getBoolean(c.c_d_e_f_w, false);
    }

    public boolean c_e_f_a_l() {
        return getBoolean(c.c_e_f_a_l, true);
    }

    public boolean c_r_i_m_m() {
        return getBoolean(c.c_r_i_m_m, false);
    }

    public boolean c_s_a_u_q_OR_s_a_u() {
        return Build.VERSION.SDK_INT > 28 ? c_s_a_u_q() : getBoolean(c.s_a_u, false);
    }

    public int c_t_r_OR_20() {
        return getInt(c.c_t_r, 20);
    }

    public boolean de() {
        if (b.cl()) {
            return false;
        }
        return getBoolean(c.c_0x41, true);
    }

    public boolean e_m_a_u_q_s() {
        return getBoolean(c.e_m_a_u_q_s, false);
    }

    public boolean enSlowMotion_120_only() {
        return !enSlowMotion_960_240_120() && !enSlowMotion_240_120_only() && getBoolean(c.c_22367_0x0000, false);
    }

    public boolean enSlowMotion_240_120_only() {
        return !enSlowMotion_960_240_120() && getBoolean(c.s_s_m_t, false);
    }

    public boolean enSlowMotion_960_240_120() {
        return getBoolean(c.s_f_9, false);
    }

    public boolean f_a_u_u() {
        return getBoolean(c.f_a_u_u, false);
    }

    public String h_d_v() {
        return getString(c.h_d_v, "");
    }

    public boolean i_q_a_u_m() {
        return getBoolean(c.i_q_a_u_m, false);
    }

    public boolean i_s_e_r() {
        return getBoolean(c.i_s_e_r, false);
    }

    public boolean i_s_q_c() {
        return getBoolean(c.i_s_q_c, false);
    }

    public boolean i_s_s_b() {
        return getBoolean(c.i_s_s_b, false);
    }

    public boolean i_v_b_c_f_d() {
        return getBoolean(c.i_v_b_c_f_d, false);
    }

    public boolean is4K30FpsEISSupported() {
        return getBoolean(c.c_0x07, false);
    }

    public boolean isCinematicPhotoSupported() {
        if (!b.bv || Build.VERSION.SDK_INT != 28) {
            return getBoolean(c.c_33066_0x0002, false);
        }
        return false;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.data.data.DataItemBase
    public boolean isMutable() {
        return false;
    }

    public boolean isSRRequireReprocess() {
        return getBoolean(c.c_9006_0x0004, false);
    }

    public boolean isSupport960VideoEditor() {
        return getBoolean(c.c_0x5b, true);
    }

    public boolean isSupportBeautyBody() {
        return getBoolean(c.s_b_b, false);
    }

    public boolean isSupportBokehAdjust() {
        return getBoolean(c.c_0x22, false);
    }

    public boolean isSupportMacroMode() {
        return getBoolean(c.c_0x32, false);
    }

    public boolean isSupportNormalWideLDC() {
        return getBoolean(c.c_0x05, false);
    }

    public boolean isSupportShortVideoBeautyBody() {
        return getBoolean(c.c_0x28, false);
    }

    public boolean isSupportUltraWide() {
        return getBoolean(c.s_u_w, false);
    }

    public boolean isSupportUltraWideLDC() {
        return getBoolean(c.c_0x06, false);
    }

    @Override // com.android.camera.data.provider.DataProvider.ProviderEvent
    public boolean isTransient() {
        return true;
    }

    public boolean ke() {
        return (tc() && rd()) || c_36658_0x0002();
    }

    public boolean me() {
        return getBoolean(c.c_0x42, !vf());
    }

    public String n(String str) {
        return getString(c.c_22756_0x0001, str);
    }

    public String o(String str) {
        return getString(c.c_28041_0x0000, str);
    }

    public boolean od() {
        return getBoolean(c.c_0x37, false) && !d.d.a.lh;
    }

    public boolean of() {
        int Hb = Hb();
        if (Hb == 1 || Hb == 2 || Hb == 3) {
            return re();
        }
        return false;
    }

    public boolean pb() {
        return getBoolean(c.c_19039_0x0014, false);
    }

    public boolean pc() {
        return getBoolean(c.c_0x5d, false);
    }

    public boolean pd() {
        return getBoolean(c.c_0x14, false);
    }

    public int pe() {
        return getInt(c.c_0x0b, 180);
    }

    public boolean pf() {
        if (!HybridZoomingSystem.IS_3_OR_MORE_SAT) {
            return false;
        }
        return getBoolean(c.c_0x49, false);
    }

    @Override // com.android.camera.data.provider.DataProvider.ProviderEvent
    public String provideKey() {
        return null;
    }

    public boolean q(String str) {
        return getValues().containsKey(str);
    }

    public boolean qc() {
        return getBoolean(c.c_0x45, false);
    }

    public boolean qd() {
        if (Util.isGlobalVersion() || !DataRepository.dataItemGlobal().isNormalIntent()) {
            return false;
        }
        return getBoolean(c.c_35893_0x0003, false);
    }

    public boolean qe() {
        return getBoolean(c.c_28041_0x0003, false);
    }

    public boolean qf() {
        return getBoolean(c.c_0x4a, false);
    }

    public boolean r(boolean z) {
        return z && getBoolean(c.c_0x43, false);
    }

    public boolean rb() {
        return getBoolean(c.e_p_p_l_t, false);
    }

    public boolean rc() {
        if (getBoolean(c.c_13254_0x02, false)) {
            return tc() || xc();
        }
        return false;
    }

    public boolean rd() {
        return getBoolean(c.c_36658_0x0001, false);
    }

    public boolean re() {
        return getBoolean(c.c_0x33, true);
    }

    public boolean rf() {
        return getBoolean(c.s_v_b, false);
    }

    public boolean ro_boot_hwc_EQ_cn() {
        if (this.Ii == null) {
            this.Ii = SystemProperties.get("ro.boot.hwc");
        }
        return "cn".equalsIgnoreCase(this.Ii);
    }

    public String s(boolean z) {
        return z ? getString(c.c_0x29, "4.5") : getString(c.c_0x30, "4");
    }

    public boolean s_a() {
        return getBoolean(c.s_a, false);
    }

    public boolean s_b_a_OR_T() {
        return getBoolean(c.s_b_a, true);
    }

    public boolean s_b_m() {
        return getBoolean(c.s_b_m, false);
    }

    public long s_b_m_d_t_OR_M1() {
        return (long) getInt(c.s_b_m_d_t, -1);
    }

    public int s_b_m_s_c_OR_M1() {
        return getInt(c.s_b_m_s_c, -1);
    }

    public boolean s_c_w_b_OR_T() {
        return getBoolean(c.s_c_w_b, true);
    }

    public boolean s_c_w_m() {
        return getBoolean(c.s_c_w_m, false);
    }

    public boolean s_e_l() {
        return getBoolean(c.s_e_l, false);
    }

    public boolean s_f_a() {
        return getBoolean(c.s_f_a, false);
    }

    public boolean s_f_s_c() {
        return getBoolean(c.s_f_s_c, false);
    }

    public boolean s_f_z_i() {
        return getBoolean(c.s_f_z_i, false);
    }

    public boolean s_i_a_AND_india() {
        return getBoolean(c.s_i_a, false) && tc();
    }

    public boolean s_m_c_t_f() {
        return getBoolean(c.s_m_c_t_f, false);
    }

    public boolean s_m_f() {
        return getBoolean(c.s_m_f, false);
    }

    public boolean s_m_l() {
        return getBoolean(c.s_m_l, false);
    }

    public boolean s_o_a_w() {
        return getBoolean(c.s_o_a_w, false);
    }

    public boolean s_p_a() {
        return getBoolean(c.s_p_a, false);
    }

    public boolean s_p_l_b() {
        return getBoolean(c.s_p_l_b, false);
    }

    public boolean s_p_l_f() {
        return getBoolean(c.s_p_l_f, false);
    }

    public int s_p_r_n_OR_M1() {
        return getInt(c.s_p_r_n, -1);
    }

    public boolean s_s_n() {
        return Build.VERSION.SDK_INT >= 28 && getBoolean(c.s_s_n, false);
    }

    public boolean s_s_s() {
        return getBoolean(c.s_s_s, false);
    }

    public boolean s_s_v_OR_T() {
        return getBoolean(c.s_s_v, true);
    }

    public boolean s_v_f_m() {
        return getBoolean(c.s_v_f_m, false);
    }

    public int sb() {
        return getInt(c.AEC_LUX_HEIGHT_LIGHT, 300);
    }

    public boolean sc() {
        return getBoolean(c.c_0x24, false);
    }

    public boolean sd() {
        return getBoolean(c.c_0x08, false);
    }

    public boolean se() {
        return ((double) Math.abs((((float) Util.sWindowHeight) / ((float) Util.sWindowWidth)) - 2.0833333f)) < 0.02d && getBoolean(c.s_18_7_5_9_s, false);
    }

    public boolean sf() {
        boolean z = SystemProperties.getBoolean("miuicamera.sat.video", false);
        if (!z) {
            return getBoolean(c.c_28041_0x0006, false);
        }
        Log.d(TAG, "sat video debug prop:" + z);
        return z;
    }

    public boolean shouldCheckSatFallbackState() {
        return getBoolean(c.c_9006_0x0005, false);
    }

    public boolean supportColorEnhance() {
        return getBoolean(c.c_35955_0x0003, false) && tc();
    }

    public int tb() {
        return getInt(c.AEC_LUX_LAST_LIGHT, 350);
    }

    public boolean tc() {
        if (this.Ii == null) {
            this.Ii = SystemProperties.get("ro.boot.hwc");
        }
        if ("india".equalsIgnoreCase(this.Ii)) {
            return true;
        }
        return !TextUtils.isEmpty(this.Ii) && this.Ii.toLowerCase(Locale.ENGLISH).startsWith("india_");
    }

    public boolean td() {
        return getBoolean(c.c_0x09, false);
    }

    public boolean te() {
        return ((float) Util.sWindowHeight) / ((float) Util.sWindowWidth) >= 2.1666667f && getBoolean(c.s_f_s, false);
    }

    public boolean tf() {
        return getBoolean(c.s_z_m, false);
    }

    public boolean ub() {
        return getBoolean(c.a_e_d, false);
    }

    public boolean uc() {
        return d.d.a.getRegion().endsWith("IN");
    }

    public boolean ud() {
        return getBoolean(c.c_0x03, false);
    }

    public boolean ue() {
        return ((double) Math.abs((((float) Util.sWindowHeight) / ((float) Util.sWindowWidth)) - 2.1111112f)) <= 0.02d && getBoolean(c.s_19_9_s, false);
    }

    public boolean uf() {
        return getBoolean(c.c_0x52, true);
    }

    public int vb() {
        return getInt(c.c_22367_0x0004, 0);
    }

    public boolean vc() {
        return getBoolean(c.c_0x04_i_l_h_d, false);
    }

    public boolean vd() {
        if (Util.isGlobalVersion()) {
            return false;
        }
        return getBoolean(c.c_0x00_s_l_s, false);
    }

    public boolean ve() {
        return ((float) Util.sWindowHeight) / ((float) Util.sWindowWidth) >= 2.2222223f && getBoolean(c.s_20_9_s, false);
    }

    public boolean vf() {
        return getBoolean(c.c_0x56, false);
    }

    public String wb() {
        return getString(c.c_0x40, "common");
    }

    public boolean wc() {
        return getBoolean(c.i_l_m_d, false);
    }

    public boolean wd() {
        return Rm() && c_28041_0x0004_or_0() == 1;
    }

    public boolean we() {
        if (getBoolean(c.c_35955_0x0001, false)) {
            return tc() || ro_boot_hwc_EQ_cn();
        }
        return false;
    }

    public boolean wf() {
        return getBoolean(c.s_f_9, false) || getBoolean(c.s_s_m_t, false) || getBoolean(c.c_22367_0x0000, false) || getBoolean(c.c_35893_0x0001, false);
    }

    public int xb() {
        return getInt(c.c_0x13, 0);
    }

    public boolean xc() {
        return getBoolean(c.c_28041_0x0002, false);
    }

    public boolean xd() {
        return Rm() && c_28041_0x0004_or_0() == 0;
    }

    public boolean xe() {
        return getBoolean(c.s_a_3, false);
    }

    public String yb() {
        return getString(c.c_0x47, "v0");
    }

    public boolean yc() {
        return getBoolean(c.c_190920, false);
    }

    public boolean yd() {
        return getBoolean(c.c_16001_0x0002, true);
    }

    public boolean ye() {
        if (!getBoolean(c.s_a_u_e_f_m, false)) {
            return c_s_a_u_q_OR_s_a_u();
        }
        if (!getBoolean(c.s_a_u, false) || !getBoolean(c.s_a_u_e_f_m, false) || (!(163 == DataRepository.dataItemGlobal().getCurrentMode() || 165 == DataRepository.dataItemGlobal().getCurrentMode()) || CameraSettings.getCameraId() != 0 || CameraSettings.isUltraPixelOn() || ((double) CameraSettings.readZoom()) < 1.0d)) {
            Log.i(TAG, "Algo up disabled for mm-camera");
            return false;
        }
        Log.i(TAG, "Algo up enabled for mm-camera");
        return true;
    }

    public int zb() {
        return getInt(c.s_f_s_2_s_t, -1);
    }

    public boolean zc() {
        return getInt(c.c_19039_0x0005, -1) == 0;
    }

    public boolean zd() {
        return getBoolean(c.c_19039_0x0007, false);
    }

    public boolean ze() {
        return getBoolean(c.s_a_u_e_f_m, false);
    }
}
