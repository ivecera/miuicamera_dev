package com.arcsoft.avatar.extrascene;

import java.util.ArrayList;

public class ExtraSceneInfo {
    public static final String EXTRA_ANGELWINGS_NAME = "AngelWings";
    public static final String EXTRA_CLOWNHAT_NAME = "ClownHat";
    public static final String EXTRA_RABBITTEETH_NAME = "RabbitTeeth";
    public static final int EXTRA_STATUS_AGAIN = 4;
    public static final int EXTRA_STATUS_BEGIN = 1;
    public static final int EXTRA_STATUS_END = 3;
    public static final int EXTRA_STATUS_RUN = 2;
    public static final int HEAD_PITCH_STATUS_DOWN = 2;
    public static final int HEAD_PITCH_STATUS_NORMAL = 0;
    public static final int HEAD_PITCH_STATUS_UP = 1;
    public static final int LOOP_LAST_EXP_ID_STATUS = 2;
    public static final int LOOP_NORAML_STATUS = 1;
    public static final int LOOP_ONCE_STATUS = 3;
    private ArrayList<HairMaskInfo> A = null;
    private int[] B = null;
    private int[] C = null;

    /* renamed from: a  reason: collision with root package name */
    private String f79a = "";

    /* renamed from: b  reason: collision with root package name */
    private int f80b = -1;

    /* renamed from: c  reason: collision with root package name */
    private int f81c = 0;

    /* renamed from: d  reason: collision with root package name */
    private boolean f82d = false;

    /* renamed from: e  reason: collision with root package name */
    private ArrayList<ExpressInfo> f83e = null;

    /* renamed from: f  reason: collision with root package name */
    private int f84f = 0;
    private String g = "";
    private ArrayList<String> h = null;
    private String i = "";
    private String j = "";
    private ArrayList<int[]> k = null;
    private ArrayList<float[]> l = null;
    private int m = 0;
    private int n = 1;
    private boolean o = true;
    private int p = -1;
    private int q = 0;
    private int r = 0;
    private int s = 0;
    private int t = 0;
    private int u = 0;
    private int v = 1;
    private int w = 0;
    private int x = 0;
    private int y = -1;
    private String z = "";

    public class ExpressInfo {
        public static final int LOOP_BEGIN_FRAME = 1;
        public static final int LOOP_END_FRAME = 2;

        /* renamed from: b  reason: collision with root package name */
        private int[] f86b = null;

        /* renamed from: c  reason: collision with root package name */
        private ArrayList<Float> f87c = null;

        /* renamed from: d  reason: collision with root package name */
        private boolean f88d = false;

        /* renamed from: e  reason: collision with root package name */
        private int f89e = 0;

        /* renamed from: f  reason: collision with root package name */
        private boolean f90f = false;

        public ExpressInfo() {
        }

        public int[] getExpressIndex() {
            return this.f86b;
        }

        public ArrayList<Float> getExpressValue() {
            return this.f87c;
        }

        public int getLoopStatus() {
            return this.f89e;
        }

        public boolean isLoopFrame() {
            return this.f88d;
        }

        public boolean isPauseFrame() {
            return this.f90f;
        }

        public void setExpressIndex(int[] iArr) {
            this.f86b = iArr;
        }

        public void setExpressValue(float f2) {
            if (this.f87c == null) {
                this.f87c = new ArrayList<>();
            }
            this.f87c.add(Float.valueOf(f2));
        }

        public void setLoopFrame(boolean z) {
            this.f88d = z;
        }

        public void setLoopStatus(int i) {
            this.f89e = i;
        }

        public void setPauseStatus(boolean z) {
            this.f90f = z;
        }
    }

    public class HairMaskInfo {

        /* renamed from: b  reason: collision with root package name */
        private int f92b = -1;

        /* renamed from: c  reason: collision with root package name */
        private int f93c = -1;

        /* renamed from: d  reason: collision with root package name */
        private boolean f94d = false;

        /* renamed from: e  reason: collision with root package name */
        private boolean f95e = false;

        public HairMaskInfo() {
        }

        public boolean getBlendShape() {
            return this.f94d;
        }

        public int getExpId() {
            return this.f93c;
        }

        public int getHairId() {
            return this.f92b;
        }

        public boolean getMask() {
            return this.f95e;
        }

        public void setBlendShape(boolean z) {
            this.f94d = z;
        }

        public void setExpId(int i) {
            this.f93c = i;
        }

        public void setHairId(int i) {
            this.f92b = i;
        }

        public void setMask(boolean z) {
            this.f95e = z;
        }

        public String toString() {
            return "mHairId = " + this.f92b + ", mExpId = " + this.f93c + ", bBlendShape = " + this.f94d + ", bMask = " + this.f95e;
        }
    }

    public String getAccPath() {
        return this.z;
    }

    public int getDuration() {
        return this.f84f;
    }

    public int getEmojiHelmetLeftRandomNum() {
        return this.u;
    }

    public int getEmojiHelmetRightRandomNum() {
        return this.v;
    }

    public ArrayList<int[]> getExpCheckIdList() {
        return this.k;
    }

    public ArrayList<float[]> getExpCheckValueList() {
        return this.l;
    }

    public int getExpCount() {
        return this.f81c;
    }

    public int[] getExpIdInPut() {
        return this.B;
    }

    public int[] getExpIdOutPut() {
        return this.C;
    }

    public ArrayList<ExpressInfo> getExpressInfoList() {
        return this.f83e;
    }

    public int getExtraStatus() {
        return this.n;
    }

    public ArrayList<HairMaskInfo> getHairMaskInfoList() {
        return this.A;
    }

    public int getHeadPitchStatus() {
        return this.x;
    }

    public int getHeadWear() {
        return this.f80b;
    }

    public int getHeadWearColor() {
        return this.p;
    }

    public int getLoopBeginFrameIndex() {
        return this.q;
    }

    public int getLoopCount() {
        return this.s;
    }

    public int getLoopEndFrameIndex() {
        return this.r;
    }

    public int getLoopNum() {
        return this.t;
    }

    public int getLoopType() {
        return this.m;
    }

    public String getName() {
        return this.f79a;
    }

    public ArrayList<String> getNewHeadWearPathList() {
        return this.h;
    }

    public String getNewToothPath() {
        return this.j;
    }

    public String getOldHeadWearPath() {
        return this.g;
    }

    public String getOldToothPath() {
        return this.i;
    }

    public int getPauseIndex() {
        return this.y;
    }

    public int getRandom() {
        return this.w;
    }

    public boolean isNeedOutLine() {
        return this.f82d;
    }

    public boolean isOldHeadWearAcc() {
        return this.o;
    }

    public void setAccPath(String str) {
        this.z = str;
    }

    public void setDuration(int i2) {
        this.f84f = i2;
    }

    public void setEmojiHelmetLeftRandomNum(int i2) {
        this.u = i2;
    }

    public void setEmojiHelmetRightRandomNum(int i2) {
        this.v = i2;
    }

    public void setExpCheckId(int[] iArr) {
        if (this.k == null) {
            this.k = new ArrayList<>();
        }
        this.k.add(iArr);
    }

    public void setExpCheckValue(float[] fArr) {
        if (this.l == null) {
            this.l = new ArrayList<>();
        }
        this.l.add(fArr);
    }

    public void setExpCount(int i2) {
        this.f81c = i2;
    }

    public void setExpIdInPut(int[] iArr) {
        this.B = iArr;
    }

    public void setExpIdOutPut(int[] iArr) {
        this.C = iArr;
    }

    public void setExpressInfo(ExpressInfo expressInfo) {
        if (this.f83e == null) {
            this.f83e = new ArrayList<>();
        }
        this.f83e.add(expressInfo);
    }

    public void setExtraStatus(int i2) {
        this.n = i2;
    }

    public void setHairMaskInfo(HairMaskInfo hairMaskInfo) {
        if (this.A == null) {
            this.A = new ArrayList<>();
        }
        this.A.add(hairMaskInfo);
    }

    public void setHeadPitchStatus(int i2) {
        this.x = i2;
    }

    public void setHeadWear(int i2) {
        this.f80b = i2;
    }

    public void setHeadWearColor(int i2) {
        this.p = i2;
    }

    public void setIsOldHeadWearAcc(boolean z2) {
        this.o = z2;
    }

    public void setLoopBeginFrameIndex(int i2) {
        this.q = i2;
    }

    public void setLoopCount(int i2) {
        this.s = i2;
    }

    public void setLoopEndFrameIndex(int i2) {
        this.r = i2;
    }

    public void setLoopNum(int i2) {
        this.t = i2;
    }

    public void setLoopType(int i2) {
        this.m = i2;
    }

    public void setName(String str) {
        this.f79a = str;
    }

    public void setNeedOutLine(boolean z2) {
        this.f82d = z2;
    }

    public void setNewHeadWearPath(String str) {
        if (this.h == null) {
            this.h = new ArrayList<>();
        }
        this.h.add(str);
    }

    public void setNewToothPath(String str) {
        this.j = str;
    }

    public void setOldHeadWearPath(String str) {
        this.g = str;
    }

    public void setOldToothPath(String str) {
        this.i = str;
    }

    public void setPauseIndex(int i2) {
        this.y = i2;
    }

    public void setRandom(int i2) {
        this.w = i2;
    }

    public String toString() {
        return "name = " + this.f79a + ", headWear = " + this.f80b + ", mNeedOutLine = " + this.f82d + ", mExpressIndex = , mExpressValue = ";
    }
}
