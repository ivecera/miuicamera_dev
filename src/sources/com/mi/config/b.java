package com.mi.config;

import android.os.Build;
import android.os.SystemProperties;
import android.text.TextUtils;
import com.android.camera.AutoLockManager;
import com.android.camera.Util;
import com.android.camera.data.DataRepository;
import d.d.a;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: Device */
public class b {
    public static final boolean Au = ("cancro".equals(vu) && Build.MODEL.startsWith("MI 3"));
    public static final boolean Bu = Au;
    public static final boolean Cu = (a.yg && !a.vg && !a.wg);
    public static final boolean Du = a.wg;
    public static final boolean Eu = a.xg;
    public static final boolean Fu = a.vg;
    public static final boolean Gu = a.zg;
    public static final boolean Hu = a.mg;
    public static final boolean Iu = a.qg;
    public static final boolean Ju = a.rg;
    public static final boolean Ku = "leo".equals(vu);
    public static final boolean Lu = "lithium".equals(vu);
    public static final boolean Mu = "chiron".equals(vu);
    public static final boolean Nu = "beryllium".equals(vu);
    public static final boolean Ou = "violet".equals(vu);
    public static final boolean Pu = d.getBoolean(d.Pu, false);
    public static final boolean Qu = "polaris".equals(vu);
    public static final boolean Ru = "sirius".equals(vu);
    public static final boolean Su = "dipper".equals(vu);
    public static final boolean Tu = "andromeda".equals(vu);
    public static final boolean Uu = "perseus".equals(vu);
    public static final boolean Vu = "cepheus".equals(vu);
    public static final boolean Wu = "grus".equals(vu);
    public static final boolean Xu = "begonia".equals(vu);
    public static final boolean Yu = ("phoenix".equals(vu) || "phoenixin".equals(vu));
    public static final boolean Zu = "begoniain".equals(vu);
    public static final boolean _u = "ginkgo".equals(vu);
    public static final boolean bv = "pyxis".equals(vu);
    public static final boolean cv = "vela".equals(vu);
    public static final boolean dv = "laurus".equals(vu);
    public static final boolean ev = "laurel_sprout".equals(vu);
    public static final boolean fv = "tucana".equals(vu);
    public static final boolean gv = ("tucana".equals(vu) && SystemProperties.get("persist.camera.rearMain.vendorID", "03").equals("03"));
    public static final boolean hv = "umi".equals(vu);
    public static final boolean jv = "cmi".equals(vu);
    public static final boolean kv = ("lmi".equals(vu) || "lmiin".equals(vu));
    public static final boolean lg = a.kg;
    public static final boolean lv = ("lmipro".equals(vu) || "lmiinpro".equals(vu));
    public static final boolean mv = "draco".equals(vu);
    public static final boolean nv;
    public static final boolean og = a.og;
    public static final boolean ov = "crux".equals(vu);
    public static final boolean pv = a.bh;
    public static final boolean qv = a.hh;
    public static final boolean rv = a.Xg;
    public static final boolean sg = d.getBoolean(d.sg, false);
    private static final int sv = 1;
    private static final int tv = 4;
    private static final int uv = 8;
    public static final String vu = Build.DEVICE;
    private static ArrayList<String> vv = null;
    public static final String wu = "qcom";
    private static final String[] wv = {"KR", "JP"};
    public static final String xu = "mediatek";
    private static final String xv = "ro.boot.hwversion";
    private static final int yu = 100;
    private static final AtomicReference<Optional<Boolean>> yv = new AtomicReference<>(Optional.empty());
    public static final String zu = Build.MODEL;

    static {
        boolean z = true;
        if (!"picasso".equals(vu) && !"picassoin".equals(vu)) {
            z = false;
        }
        nv = z;
    }

    public static boolean Al() {
        return d.getBoolean(d.jx, false);
    }

    public static boolean Bl() {
        return d.getBoolean(d.sx, false);
    }

    public static boolean Cl() {
        return d.getBoolean(d.nx, false);
    }

    public static boolean Dl() {
        return d.getBoolean(d.hx, false);
    }

    public static boolean El() {
        return d.getBoolean(d.Cw, false);
    }

    public static boolean Fl() {
        return d.getBoolean(d.mw, false);
    }

    public static boolean G(boolean z) {
        String str = SystemProperties.get("ro.miui.customized.region");
        if ("fr_sfr".equals(str) || "fr_orange".equals(str)) {
            return false;
        }
        if (!"es_vodafone".equals(str) || !"NL".equals(getCountry())) {
            return z;
        }
        return false;
    }

    public static boolean Gl() {
        return false;
    }

    public static boolean Hl() {
        return d.getBoolean(d.kx, false);
    }

    public static boolean Il() {
        return Al() || Hl();
    }

    public static boolean Jl() {
        return d.getBoolean(d.gx, true);
    }

    public static boolean Kl() {
        return !DataRepository.dataItemFeature().Wc() && Pu;
    }

    public static boolean Ll() {
        return d.getBoolean(d.ex, false);
    }

    public static boolean Ml() {
        return (d.getInteger(d._v, 0) & 13) != 0;
    }

    public static boolean Nl() {
        return !a.lh && d.getBoolean(d.Lv, false);
    }

    public static boolean Ol() {
        return d.getBoolean(d.Sv, false);
    }

    public static boolean Pl() {
        return (d.getInteger(d._v, 0) & 1) != 0;
    }

    public static boolean Qk() {
        return !qv && d.getBoolean(d.Qv, false);
    }

    public static boolean Ql() {
        return (d.getInteger(d._v, 0) & 4) != 0;
    }

    public static boolean Rk() {
        return d.getBoolean(d.mx, false);
    }

    public static boolean Rl() {
        return false;
    }

    static String Sk() {
        int i = SystemProperties.getInt("ro.boot.camera.config", 1);
        if (i == 0) {
            return "_pro";
        }
        if (i != 1) {
        }
        return "";
    }

    public static boolean Sl() {
        return d.getBoolean(d.Uv, false);
    }

    public static boolean Te() {
        return DataRepository.dataItemFeature().Te();
    }

    public static ArrayList<String> Tk() {
        if (vv == null) {
            vv = new ArrayList<>();
            String[] stringArray = d.getStringArray(d.Ev);
            if (stringArray != null) {
                Collections.addAll(vv, stringArray);
            }
        }
        return vv;
    }

    public static boolean Tl() {
        return d.getBoolean(d.Bv, false);
    }

    public static String Uk() {
        return cl() ? "_l" : ol() ? "_in" : !DataRepository.dataItemFeature().getBoolean(c.c_0x18, false) ? "" : (Build.MODEL.contains("BROWN EDITION") || Build.MODEL.contains("Explorer")) ? "_a" : Build.MODEL.contains("ROY") ? "_b" : dl() ? "_s" : (jl() || il() || kl()) ? "_global" : ul() ? "_premium" : ll() ? vl() ? "_global_pro" : "_global" : "";
    }

    public static boolean Ul() {
        return d.getBoolean(d.zw, false);
    }

    public static int Vk() {
        return d.getInteger(d.HIBERNATION_TIMEOUT, AutoLockManager.HIBERNATION_TIMEOUT);
    }

    public static boolean Vl() {
        return false;
    }

    private static boolean W(String str) {
        for (String str2 : wv) {
            if (TextUtils.equals(str, str2)) {
                return true;
            }
        }
        return false;
    }

    public static boolean Wk() {
        return d.getBoolean(d.tx, false);
    }

    public static boolean Wl() {
        return d.getBoolean(d.Mv, false);
    }

    public static boolean Xk() {
        return ((float) Util.sWindowHeight) / ((float) Util.sWindowWidth) >= 2.0f && d.getBoolean(d.Lw, false);
    }

    public static boolean Xl() {
        return d.getBoolean(d.Gv, false);
    }

    public static boolean Yk() {
        return !lo() && DataRepository.dataItemFeature().gd() && Tk() != null && !Tk().isEmpty();
    }

    public static boolean Yl() {
        return !a.lh && d.getBoolean(d.uw, false);
    }

    public static boolean Zk() {
        return DataRepository.dataItemFeature().ze() || Yu;
    }

    public static boolean Zl() {
        return d.getBoolean(d.ew, false);
    }

    public static boolean _k() {
        if (a.lh) {
            return W(getCountry());
        }
        return false;
    }

    public static boolean _l() {
        if (cl()) {
            return false;
        }
        return d.getBoolean(d.Jv, false);
    }

    public static boolean al() {
        return d.getBoolean(d.sw, false);
    }

    public static boolean am() {
        return !_k();
    }

    public static boolean bl() {
        String str = SystemProperties.get(xv);
        return ov && (TextUtils.equals(str, "7.1.7") || TextUtils.equals(str, "7.2.0"));
    }

    public static boolean bm() {
        return d.getBoolean(d.Ov, false);
    }

    public static boolean cl() {
        if (!"onc".equals(vu)) {
            return false;
        }
        String str = SystemProperties.get(xv);
        return !TextUtils.isEmpty(str) && '2' == str.charAt(0);
    }

    public static boolean cm() {
        return d.getBoolean(d.Vv, false);
    }

    public static boolean dl() {
        return vu.equalsIgnoreCase("lavender") && "India_48_5".equalsIgnoreCase(SystemProperties.get("ro.boot.hwc"));
    }

    public static boolean dm() {
        return d.getBoolean(d.lw, false);
    }

    public static boolean el() {
        return Lu || Mu || Qu;
    }

    public static boolean em() {
        return !a.lh && d.getBoolean(d.vw, false);
    }

    public static boolean fl() {
        return hl() || bv || Wu || DataRepository.dataItemFeature().ze();
    }

    public static boolean fm() {
        return d.getBoolean(d.Av, false);
    }

    public static int getBurstShootCount() {
        return d.getInteger(d.Iv, 100);
    }

    public static String getCountry() {
        String str = Util.sRegion;
        return !TextUtils.isEmpty(str) ? str : Locale.getDefault().getCountry();
    }

    public static boolean gl() {
        return !Fu && !Hu && !a.Jg && !Au && !Gu && !Cu && !Du && !Eu && !lg && !og && !Bu && d.getBoolean(d.Ow, true);
    }

    public static boolean gm() {
        return d.getBoolean(d.Fv, false);
    }

    public static boolean hl() {
        return Uu && a.lh;
    }

    public static boolean hm() {
        return d.getBoolean(d.Kv, false);
    }

    public static boolean il() {
        return vu.equalsIgnoreCase("davinci") && a.lh;
    }

    public static boolean im() {
        return d.getBoolean(d.Vw, false);
    }

    public static boolean isMTKPlatform() {
        if (!yv.get().isPresent()) {
            synchronized (yv) {
                if (!yv.get().isPresent()) {
                    yv.set(Optional.of(Boolean.valueOf(xu.equals(d.getString(d.VENDOR)))));
                }
            }
        }
        return yv.get().get().booleanValue();
    }

    public static boolean isSupportSuperResolution() {
        return d.getBoolean(d.px, false);
    }

    public static boolean isSupportedOpticalZoom() {
        return d.getBoolean(d.Ww, false);
    }

    public static boolean jl() {
        return vu.equalsIgnoreCase("raphael") && a.lh;
    }

    public static boolean jm() {
        return d.getBoolean(d.tw, false);
    }

    public static boolean kl() {
        return bv && a.lh;
    }

    public static boolean km() {
        return d.getBoolean(d.Nv, false);
    }

    private static boolean ko() {
        return SystemProperties.getBoolean("ro.hardware.fp.fod", false);
    }

    public static boolean ll() {
        return fv && a.lh;
    }

    public static boolean lm() {
        return d.getBoolean(d.hw, false);
    }

    private static boolean lo() {
        return d.getBoolean(d.Iw, false) || ko();
    }

    public static boolean ml() {
        return d.getBoolean(d.Sw, false);
    }

    public static boolean mm() {
        return d.getBoolean(d.Pv, false);
    }

    public static boolean nl() {
        return d.getBoolean(d.kw, false);
    }

    public static boolean nm() {
        return d.getBoolean(d.Wv, false);
    }

    public static boolean ol() {
        return Nu && "India".equalsIgnoreCase(SystemProperties.get("ro.boot.hwc"));
    }

    public static boolean om() {
        return d.getBoolean(d.Rw, false);
    }

    public static boolean pd() {
        return DataRepository.dataItemFeature().pd();
    }

    public static boolean pl() {
        return d.getBoolean(d.Ew, true);
    }

    public static boolean pm() {
        return !sg && !Pu;
    }

    public static boolean ql() {
        return kv || lv;
    }

    public static boolean qm() {
        return !Fu && !Hu && !a.Jg && !Au && !Gu && !Cu && !Du && !Eu && !lg && !og && !Bu && !Iu && d.getBoolean(d.Qw, true);
    }

    public static boolean rl() {
        return d.getBoolean(d.nw, false);
    }

    public static boolean rm() {
        return d.getBoolean(d.rx, false);
    }

    public static boolean sl() {
        return false;
    }

    public static boolean sm() {
        return d.getBoolean(d.qx, true);
    }

    public static boolean tl() {
        return d.getBoolean(d.zv, false);
    }

    public static boolean ul() {
        return vu.equalsIgnoreCase("raphael") && Build.MODEL.endsWith("Premium Edition");
    }

    public static boolean vl() {
        return fv && !gv;
    }

    public static boolean wl() {
        return wu.equals(d.getString(d.VENDOR));
    }

    public static boolean xl() {
        return d.getBoolean(d._w, true);
    }

    public static boolean yl() {
        return false;
    }

    public static boolean zl() {
        return d.getBoolean(d.ow, false);
    }
}
