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

    private int Qm() {
        return getInt(c.yD, 0);
    }

    private boolean Rm() {
        if (b.jl() || b.kl()) {
            return false;
        }
        return getBoolean(c.UC, false);
    }

    private boolean Sm() {
        return getBoolean(c.wC, false);
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
        return N(getString(c.JC, ""));
    }

    public boolean Ac() {
        return getInt(c.MC, -1) == 2;
    }

    public boolean Ad() {
        return getBoolean(c.ED, false);
    }

    public boolean Ae() {
        return Build.VERSION.SDK_INT > 28 ? Sm() : getBoolean(c.fA, false);
    }

    public String Bb() {
        return getString(c.qA, "");
    }

    public boolean Bc() {
        return getInt(c.MC, -1) == 1;
    }

    public boolean Bd() {
        return getBoolean(c.NB, false) && !d.d.a.lh;
    }

    public boolean Be() {
        return getBoolean(c.GD, false) && !SystemProperties.getBoolean("close.append.yuv", false);
    }

    public String Cb() {
        return getString(c.fB, null);
    }

    public boolean Cc() {
        return getBoolean(c.cA, false);
    }

    public boolean Cd() {
        return getBoolean(c.kC, false);
    }

    public boolean Ce() {
        return getBoolean(c.eA, false);
    }

    public float Db() {
        return (float) getDoubleFromValues(c.CA, 0.8766000270843506d);
    }

    public boolean Dc() {
        return getBoolean(c.DD, false);
    }

    public boolean Dd() {
        return getBoolean(c.TC, false);
    }

    public boolean De() {
        return getBoolean(c.wA, true);
    }

    public int Eb() {
        return getInt(c.BA, 280);
    }

    public boolean Ec() {
        return getBoolean(c.nA, false);
    }

    public boolean Ed() {
        return getBoolean(c.yC, false);
    }

    public boolean Ee() {
        return getBoolean(c.pA, false);
    }

    public int Fb() {
        return getInt(c.EC, 0);
    }

    public boolean Fc() {
        return getBoolean(c.eB, false);
    }

    public boolean Fd() {
        return getBoolean(c.RA, true);
    }

    public boolean Fe() {
        return getBoolean(c.Rz, false);
    }

    public int Gb() {
        return getInt(c.LC, -1);
    }

    public boolean Gc() {
        return Arrays.asList(getString(c.gA, "").toUpperCase().split(":")).contains("NO_PIXEL");
    }

    public boolean Gd() {
        return getBoolean(c.RC, false);
    }

    public boolean Ge() {
        return getBoolean(c.XD, false);
    }

    public int Hb() {
        return N(getString(c.IC, ""));
    }

    public boolean Hc() {
        return Arrays.asList(getString(c.gA, "").toUpperCase().split(":")).contains("PRO");
    }

    public boolean Hd() {
        return getBoolean(c.hB, false);
    }

    public boolean He() {
        return getBoolean(c._B, false);
    }

    public int Ib() {
        if (b.gv) {
            return 6;
        }
        return getInt(c.tC, 5);
    }

    public boolean Ic() {
        return getBoolean(c.qB, false);
    }

    public boolean Id() {
        return getBoolean(c.BB, false);
    }

    public boolean Ie() {
        return getBoolean(c.ZD, false);
    }

    public int Jb() {
        return getInt(c.AB, -1);
    }

    public boolean Jc() {
        return getBoolean(c.jD, false);
    }

    public boolean Jd() {
        return getBoolean(c.gD, false);
    }

    public boolean Je() {
        return getBoolean(c.OD, false);
    }

    public long Kb() {
        return (long) getInt(c.zB, -1);
    }

    public boolean Kc() {
        return qb();
    }

    public boolean Kd() {
        return getBoolean(c.yB, false);
    }

    public boolean Ke() {
        return getBoolean(c.YD, false);
    }

    public int Lb() {
        return getInt(c.DC, 0);
    }

    public boolean Lc() {
        return getBoolean(c.sA, false);
    }

    public boolean Ld() {
        return getBoolean(c.FC, false);
    }

    public boolean Le() {
        return getBoolean(c.uD, false);
    }

    public int Mb() {
        return getInt(c.Kz, 20);
    }

    public boolean Mc() {
        return getBoolean(c.nB, false);
    }

    public boolean Md() {
        return getBoolean(c.GC, false);
    }

    public boolean Me() {
        return getBoolean(c.Lz, false) && tc();
    }

    public Size Nb() {
        return O(getString(c.JC, ""));
    }

    public boolean Nc() {
        return getBoolean(c.rA, true);
    }

    public boolean Nd() {
        return getBoolean(c.xC, false);
    }

    public boolean Ne() {
        return getBoolean(c.OA, false) && tc();
    }

    public Size Ob() {
        return O(getString(c.IC, ""));
    }

    public boolean Oc() {
        return Arrays.asList(getString(c.gA, "").toUpperCase().split(":")).contains("MACRO");
    }

    public boolean Od() {
        return getBoolean(c.rB, false);
    }

    public boolean Oe() {
        return getBoolean(c.UD, false);
    }

    public int Pb() {
        return getInt(c.hD, 0);
    }

    public boolean Pc() {
        return getBoolean(c.iB, false);
    }

    public boolean Pd() {
        if (Util.isGlobalVersion()) {
            return false;
        }
        if (!b.bv || Build.VERSION.SDK_INT != 28) {
            return getBoolean(c.AC, false);
        }
        return false;
    }

    public boolean Pe() {
        return getBoolean(c.cC, false);
    }

    public String Qb() {
        return getString(c.cD, null);
    }

    public boolean Qc() {
        return getBoolean(c.OC, false);
    }

    public boolean Qd() {
        return getBoolean(c.eD, false);
    }

    public boolean Qe() {
        return getBoolean(c.zD, true);
    }

    public int Rb() {
        return getInt(c.QD, 1);
    }

    public boolean Rc() {
        return getBoolean(c.wB, true);
    }

    public boolean Rd() {
        return getBoolean(c.QC, false);
    }

    public boolean Re() {
        return getBoolean(c.BD, false);
    }

    public int Sb() {
        return getInt(c.vC, 0);
    }

    public boolean Sc() {
        return getBoolean(c.xB, false);
    }

    public boolean Sd() {
        return 3 == Hb();
    }

    public boolean Se() {
        return getBoolean(c.jC, false);
    }

    public int Tb() {
        return getInt(c.JD, 0);
    }

    public boolean Tc() {
        return getBoolean(c.ZC, false);
    }

    public boolean Td() {
        return getBoolean(c.LA, false);
    }

    public boolean Te() {
        if (!Util.isGlobalVersion()) {
            return false;
        }
        return getBoolean(c.ZA, false);
    }

    public int Ub() {
        return getInt(c.VD, 0);
    }

    public boolean Uc() {
        return getBoolean(c.GB, false);
    }

    public boolean Ud() {
        return getBoolean(c.KC, true);
    }

    public boolean Ue() {
        return getBoolean(c.CD, false);
    }

    public boolean Vb() {
        return Hb() < 0 || getBoolean(c.QA, false);
    }

    public boolean Vc() {
        return getBoolean(c.zC, false);
    }

    public boolean Vd() {
        return getBoolean(c.CC, false);
    }

    public boolean Ve() {
        return getBoolean(c.Vz, false);
    }

    public boolean Wb() {
        return getBoolean(c.YB, false);
    }

    public boolean Wc() {
        return getBoolean(c.Sz, true);
    }

    public boolean Wd() {
        return getBoolean(c.tB, false);
    }

    public boolean We() {
        return getBoolean(c.Xz, false);
    }

    public boolean Xb() {
        return getBoolean(c.dD, false);
    }

    public boolean Xc() {
        return getBoolean(c._v, false);
    }

    public boolean Xd() {
        return getBoolean(c.JB, true);
    }

    public boolean Xe() {
        return getBoolean(c.Tz, false);
    }

    public boolean Yb() {
        return getInt(c.fD, 0) == 1;
    }

    public boolean Yc() {
        return getBoolean(c.WA, false);
    }

    public boolean Yd() {
        return !Zd() && getBoolean(c.iA, false);
    }

    public boolean Ye() {
        return (tc() || uc()) && getBoolean(c.Qz, false);
    }

    public boolean Zb() {
        return getInt(c.fD, 0) == 0;
    }

    public boolean Zc() {
        return getBoolean(c.LD, false);
    }

    public boolean Zd() {
        return getBoolean(c.hA, false);
    }

    public boolean Ze() {
        return getBoolean(c.Oz, false);
    }

    public boolean _b() {
        return getBoolean(c.uC, false);
    }

    public boolean _c() {
        return getBoolean(c.xA, false);
    }

    public boolean _d() {
        return getBoolean(c.kA, false);
    }

    public boolean _e() {
        return getBoolean(c.Pz, false);
    }

    public boolean ac() {
        return getBoolean(c.ND, false);
    }

    public boolean ad() {
        return getBoolean(c.YA, true);
    }

    public boolean ae() {
        return getBoolean(c.oC, false);
    }

    public boolean af() {
        return getBoolean(c.WD, false);
    }

    public boolean bc() {
        if (this.Ii == null) {
            this.Ii = SystemProperties.get("ro.boot.hwc");
        }
        return "cn".equalsIgnoreCase(this.Ii);
    }

    public boolean bd() {
        return getBoolean(c.bD, false);
    }

    public boolean be() {
        return getBoolean(c.KB, true);
    }

    public boolean bf() {
        return getBoolean(c.vD, false);
    }

    public boolean cc() {
        return Arrays.asList(getString(c.gA, "").toUpperCase().split(":")).contains("CAPTURE_INTENT");
    }

    public boolean cd() {
        return getBoolean(c.jB, false);
    }

    public boolean ce() {
        return !Zd() && !Yd() && getBoolean(c.nC, false);
    }

    public boolean cf() {
        return getBoolean(c.iC, false);
    }

    public boolean dc() {
        if (Build.VERSION.SDK_INT < 28) {
            return false;
        }
        return getBoolean(c.lB, false);
    }

    public boolean dd() {
        return getBoolean(c.HC, false);
    }

    public boolean de() {
        if (b.cl()) {
            return false;
        }
        return getBoolean(c.RB, true);
    }

    public boolean df() {
        return getBoolean(c.dE, false);
    }

    public boolean ec() {
        return getBoolean(c.nD, false);
    }

    public boolean ed() {
        return getBoolean(c.pC, false);
    }

    public boolean ee() {
        return Build.VERSION.SDK_INT >= 28 && getBoolean(c.lA, false);
    }

    public boolean ef() {
        return getBoolean(c.bE, false);
    }

    public boolean fc() {
        return getBoolean(c.lC, true);
    }

    public boolean fd() {
        return getBoolean(c.mD, false);
    }

    public boolean fe() {
        return getBoolean(c.YC, false);
    }

    public boolean ff() {
        return getBoolean(c.bB, false);
    }

    public boolean gd() {
        return getBoolean(c.LB, true);
    }

    public boolean ge() {
        return getBoolean(c.jA, false);
    }

    public boolean gf() {
        return getBoolean(c.cE, false);
    }

    public boolean hc() {
        return getBoolean(c.sB, true);
    }

    public boolean hd() {
        return getBoolean(c.WC, false);
    }

    public boolean he() {
        return Arrays.asList(getString(c.gA, "").toUpperCase().split(":")).contains("ULTRA_WIDE");
    }

    public boolean hf() {
        return getBoolean(c._D, false);
    }

    public boolean ic() {
        return getBoolean(c.oA, false);
    }

    public boolean ie() {
        return getBoolean(c.SA, false);
    }

    /* renamed from: if  reason: not valid java name */
    public boolean m1if() {
        return getBoolean(c.eC, false);
    }

    public boolean is4K30FpsEISSupported() {
        return getBoolean(c.HA, false);
    }

    public boolean isCinematicPhotoSupported() {
        if (!b.bv || Build.VERSION.SDK_INT != 28) {
            return getBoolean(c.XC, false);
        }
        return false;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.data.data.DataItemBase
    public boolean isMutable() {
        return false;
    }

    public boolean isSRRequireReprocess() {
        return getBoolean(c.oD, false);
    }

    public boolean isSupport960VideoEditor() {
        return getBoolean(c.iD, true);
    }

    public boolean isSupportBeautyBody() {
        return getBoolean(c.vA, false);
    }

    public boolean isSupportBokehAdjust() {
        return getBoolean(c.kB, false);
    }

    public boolean isSupportMacroMode() {
        return getBoolean(c.HB, false);
    }

    public boolean isSupportNormalWideLDC() {
        return getBoolean(c.FA, false);
    }

    public boolean isSupportShortVideoBeautyBody() {
        return getBoolean(c.DB, false);
    }

    public boolean isSupportUltraWide() {
        return getBoolean(c.tA, false);
    }

    public boolean isSupportUltraWideLDC() {
        return getBoolean(c.GA, false);
    }

    @Override // com.android.camera.data.provider.DataProvider.ProviderEvent
    public boolean isTransient() {
        return true;
    }

    public boolean jc() {
        return getBoolean(c.cB, true);
    }

    public boolean jd() {
        return getBoolean(c.VA, false);
    }

    public boolean je() {
        return getBoolean(c.yA, false);
    }

    public boolean jf() {
        return getBoolean(c.hC, false);
    }

    public boolean kc() {
        return getBoolean(c.dB, false);
    }

    public boolean kd() {
        return getBoolean(c.OB, false);
    }

    public boolean ke() {
        return (tc() && rd()) || Zc();
    }

    public boolean kf() {
        return getBoolean(c.fC, false);
    }

    public boolean lc() {
        return getBoolean(c.xD, false);
    }

    public boolean ld() {
        return getBoolean(c.SC, false);
    }

    public boolean le() {
        return getBoolean(c.UB, false);
    }

    public boolean lf() {
        return getBoolean(c.eE, false);
    }

    public boolean mc() {
        return getBoolean(c.pD, false);
    }

    public boolean md() {
        return getBoolean(c.pB, false);
    }

    public boolean me() {
        return getBoolean(c.SB, !vf());
    }

    public boolean mf() {
        return getBoolean(c.Nz, false);
    }

    public String n(String str) {
        return getString(c.WB, str);
    }

    public boolean nb() {
        return getBoolean(c.FD, false);
    }

    public boolean nc() {
        return getBoolean(c.PB, false);
    }

    public boolean nd() {
        return getBoolean(c.gB, false);
    }

    public boolean ne() {
        return getBoolean(c.PA, false);
    }

    public boolean nf() {
        return getBoolean(c.sC, false);
    }

    public String o(String str) {
        return getString(c.VB, str);
    }

    public boolean ob() {
        return getBoolean(c.uB, false);
    }

    public boolean oc() {
        return getBoolean(c._A, false);
    }

    public boolean od() {
        return getBoolean(c.MB, false) && !d.d.a.lh;
    }

    public boolean oe() {
        return getBoolean(c.TD, false);
    }

    public boolean of() {
        int Hb = Hb();
        if (Hb == 1 || Hb == 2 || Hb == 3) {
            return re();
        }
        return false;
    }

    public boolean pb() {
        return getBoolean(c.RD, false);
    }

    public boolean pc() {
        return getBoolean(c.kD, false);
    }

    public boolean pd() {
        return getBoolean(c.UA, false);
    }

    public int pe() {
        return getInt(c.MA, 180);
    }

    public boolean pf() {
        if (!HybridZoomingSystem.IS_3_OR_MORE_SAT) {
            return false;
        }
        return getBoolean(c.dC, false);
    }

    @Override // com.android.camera.data.provider.DataProvider.ProviderEvent
    public String provideKey() {
        return null;
    }

    public boolean q(String str) {
        return getValues().containsKey(str);
    }

    public boolean qb() {
        return getBoolean(c.rC, false);
    }

    public boolean qc() {
        return getBoolean(c.ZB, false);
    }

    public boolean qd() {
        if (Util.isGlobalVersion() || !DataRepository.dataItemGlobal().isNormalIntent()) {
            return false;
        }
        return getBoolean(c.qC, false);
    }

    public boolean qe() {
        return getBoolean(c.wD, false);
    }

    public boolean qf() {
        return getBoolean(c.Wz, false);
    }

    public boolean r(boolean z) {
        return z && getBoolean(c.XB, false);
    }

    public boolean rb() {
        return getBoolean(c.vB, false);
    }

    public boolean rc() {
        if (getBoolean(c.qD, false)) {
            return tc() || xc();
        }
        return false;
    }

    public boolean rd() {
        return getBoolean(c.KD, false);
    }

    public boolean re() {
        return getBoolean(c.IB, true);
    }

    public boolean rf() {
        return getBoolean(c.Uz, false);
    }

    public String s(boolean z) {
        return z ? getString(c.EB, "4.5") : getString(c.FB, "4");
    }

    public int sb() {
        return getInt(c.AEC_LUX_HEIGHT_LIGHT, 300);
    }

    public boolean sc() {
        return getBoolean(c.oB, false);
    }

    public boolean sd() {
        return getBoolean(c.JA, false);
    }

    public boolean se() {
        return ((double) Math.abs((((float) Util.sWindowHeight) / ((float) Util.sWindowWidth)) - 2.0833333f)) < 0.02d && getBoolean(c._z, false);
    }

    public boolean sf() {
        boolean z = SystemProperties.getBoolean("miuicamera.sat.video", false);
        if (!z) {
            return getBoolean(c.HD, false);
        }
        Log.d(TAG, "sat video debug prop:" + z);
        return z;
    }

    public boolean shouldCheckSatFallbackState() {
        return getBoolean(c.tD, false);
    }

    public boolean supportColorEnhance() {
        return getBoolean(c.SD, false) && tc();
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
        return getBoolean(c.KA, false);
    }

    public boolean te() {
        return ((float) Util.sWindowHeight) / ((float) Util.sWindowWidth) >= 2.1666667f && getBoolean(c.Yz, false);
    }

    public boolean tf() {
        return getBoolean(c.ox, false);
    }

    public boolean ub() {
        return getBoolean(c.Mz, false);
    }

    public boolean uc() {
        return d.d.a.getRegion().endsWith("IN");
    }

    public boolean ud() {
        return getBoolean(c.DA, false);
    }

    public boolean ue() {
        return ((double) Math.abs((((float) Util.sWindowHeight) / ((float) Util.sWindowWidth)) - 2.1111112f)) <= 0.02d && getBoolean(c.Zz, false);
    }

    public boolean uf() {
        return getBoolean(c.gC, true);
    }

    public int vb() {
        return getInt(c._C, 0);
    }

    public boolean vc() {
        return getBoolean(c.EA, false);
    }

    public boolean vd() {
        if (Util.isGlobalVersion()) {
            return false;
        }
        return getBoolean(c.zA, false);
    }

    public boolean ve() {
        return ((float) Util.sWindowHeight) / ((float) Util.sWindowWidth) >= 2.2222223f && getBoolean(c.bA, false);
    }

    public boolean vf() {
        return getBoolean(c.mC, false);
    }

    public String wb() {
        return getString(c.QB, "common");
    }

    public boolean wc() {
        return getBoolean(c.uA, false);
    }

    public boolean wd() {
        return Rm() && Qm() == 1;
    }

    public boolean we() {
        if (getBoolean(c.PD, false)) {
            return tc() || bc();
        }
        return false;
    }

    public boolean wf() {
        return getBoolean(c.hA, false) || getBoolean(c.iA, false) || getBoolean(c.nC, false) || getBoolean(c.oC, false);
    }

    public int xb() {
        return getInt(c.TA, 0);
    }

    public boolean xc() {
        return getBoolean(c.rD, false);
    }

    public boolean xd() {
        return Rm() && Qm() == 0;
    }

    public boolean xe() {
        return getBoolean(c.dA, false);
    }

    public String yb() {
        return getString(c.bC, "v0");
    }

    public boolean yc() {
        return getBoolean(c.lD, false);
    }

    public boolean yd() {
        return getBoolean(c.VC, true);
    }

    public boolean ye() {
        if (!getBoolean(c.MD, false)) {
            return Ae();
        }
        if (!getBoolean(c.fA, false) || !getBoolean(c.MD, false) || (!(163 == DataRepository.dataItemGlobal().getCurrentMode() || 165 == DataRepository.dataItemGlobal().getCurrentMode()) || CameraSettings.getCameraId() != 0 || CameraSettings.isUltraPixelOn() || ((double) CameraSettings.readZoom()) < 1.0d)) {
            Log.i(TAG, "Algo up disabled for mm-camera");
            return false;
        }
        Log.i(TAG, "Algo up enabled for mm-camera");
        return true;
    }

    public int zb() {
        return getInt(c.sD, -1);
    }

    public boolean zc() {
        return getInt(c.MC, -1) == 0;
    }

    public boolean zd() {
        return getBoolean(c.PC, false);
    }

    public boolean ze() {
        return getBoolean(c.MD, false);
    }
}
