package com.ss.android.vesdk;

public class VEReverb2Params {
    public float bassb = 0.0f;
    public float basslpf = 1050.0f;
    public float damplpf = 18000.0f;
    public float delay = 0.0f;
    public float dry = 0.0f;
    public boolean enable = true;
    public boolean enableExciter = true;
    public float ereffactor = 1.0f;
    public float erefwet = 0.0f;
    public float erefwidth = 0.0f;
    public float ertolate = 0.0f;
    public float inputlpf = 18000.0f;
    public float outputlpf = 18000.0f;
    public int oversamplefactor = 1;
    public int rate = 44100;
    public float rt60 = 0.1f;
    public float spin = 0.0f;
    public float wander = 0.1f;
    public float wet = 0.0f;
    public float width = 0.0f;

    public static class VEPresets {
        public static final VEReverb2Params CRISTAL = new VEReverb2Params();
        public static final VEReverb2Params KTV = new VEReverb2Params();
        public static final VEReverb2Params NONE = new VEReverb2Params();
        public static final VEReverb2Params POP = new VEReverb2Params();
        public static final VEReverb2Params ROCK = new VEReverb2Params();

        static {
            NONE.enable = false;
            VEReverb2Params vEReverb2Params = POP;
            vEReverb2Params.rate = 44100;
            vEReverb2Params.oversamplefactor = 2;
            vEReverb2Params.ertolate = 0.25f;
            vEReverb2Params.erefwet = -26.0f;
            vEReverb2Params.dry = -10.0f;
            vEReverb2Params.ereffactor = 0.9f;
            vEReverb2Params.erefwidth = -0.68f;
            vEReverb2Params.width = 0.22f;
            vEReverb2Params.wet = -11.66f;
            vEReverb2Params.wander = 0.18f;
            vEReverb2Params.bassb = 0.07f;
            vEReverb2Params.spin = 4.57f;
            vEReverb2Params.inputlpf = 18000.0f;
            vEReverb2Params.basslpf = 93.0f;
            vEReverb2Params.damplpf = 14570.0f;
            vEReverb2Params.outputlpf = 17140.0f;
            vEReverb2Params.rt60 = 3.9999998f;
            vEReverb2Params.delay = 0.19f;
            VEReverb2Params vEReverb2Params2 = KTV;
            vEReverb2Params2.rate = 44100;
            vEReverb2Params2.oversamplefactor = 2;
            vEReverb2Params2.ertolate = 0.1f;
            vEReverb2Params2.erefwet = -28.0f;
            vEReverb2Params2.dry = -7.0f;
            vEReverb2Params2.ereffactor = 1.3199999f;
            vEReverb2Params2.erefwidth = 0.110000014f;
            vEReverb2Params2.width = 0.42f;
            vEReverb2Params2.wet = -15.0f;
            vEReverb2Params2.wander = 0.38f;
            vEReverb2Params2.bassb = 0.075f;
            vEReverb2Params2.spin = 7.3f;
            vEReverb2Params2.inputlpf = 9560.001f;
            vEReverb2Params2.basslpf = 136.0f;
            vEReverb2Params2.damplpf = 11690.0f;
            vEReverb2Params2.outputlpf = 7100.0f;
            vEReverb2Params2.rt60 = 3.9f;
            vEReverb2Params2.delay = -0.42000002f;
            VEReverb2Params vEReverb2Params3 = ROCK;
            vEReverb2Params3.rate = 44100;
            vEReverb2Params3.oversamplefactor = 2;
            vEReverb2Params3.ertolate = 0.0f;
            vEReverb2Params3.erefwet = -26.0f;
            vEReverb2Params3.dry = -8.0f;
            vEReverb2Params3.ereffactor = 1.36f;
            vEReverb2Params3.erefwidth = 1.0f;
            vEReverb2Params3.width = 0.81f;
            vEReverb2Params3.wet = -22.0f;
            vEReverb2Params3.wander = 0.495f;
            vEReverb2Params3.bassb = 0.02f;
            vEReverb2Params3.spin = 7.0f;
            vEReverb2Params3.inputlpf = 18000.0f;
            vEReverb2Params3.basslpf = 84.0f;
            vEReverb2Params3.damplpf = 18000.0f;
            vEReverb2Params3.outputlpf = 18000.0f;
            vEReverb2Params3.rt60 = 3.9f;
            vEReverb2Params3.delay = -0.00999999f;
            VEReverb2Params vEReverb2Params4 = CRISTAL;
            vEReverb2Params4.rate = 44100;
            vEReverb2Params4.oversamplefactor = 2;
            vEReverb2Params4.ertolate = 0.0f;
            vEReverb2Params4.erefwet = -42.0f;
            vEReverb2Params4.dry = -19.0f;
            vEReverb2Params4.ereffactor = 0.5f;
            vEReverb2Params4.erefwidth = 1.0f;
            vEReverb2Params4.width = 0.81f;
            vEReverb2Params4.wet = -12.0f;
            vEReverb2Params4.wander = 0.17f;
            vEReverb2Params4.bassb = 0.0f;
            vEReverb2Params4.spin = 0.0f;
            vEReverb2Params4.inputlpf = 5890.0f;
            vEReverb2Params4.basslpf = 143.0f;
            vEReverb2Params4.damplpf = 5690.0f;
            vEReverb2Params4.outputlpf = 7650.0f;
            vEReverb2Params4.rt60 = 3.6f;
            vEReverb2Params4.delay = 0.5f;
        }
    }

    public static VEReverb2Params fromString(String str) {
        String[] split = str.split(",");
        try {
            VEReverb2Params vEReverb2Params = new VEReverb2Params();
            boolean z = false;
            vEReverb2Params.enableExciter = Integer.parseInt(split[0]) == 1;
            if (Integer.parseInt(split[1]) == 1) {
                z = true;
            }
            vEReverb2Params.enable = z;
            vEReverb2Params.rate = Integer.parseInt(split[2]);
            vEReverb2Params.oversamplefactor = Integer.parseInt(split[3]);
            vEReverb2Params.ertolate = Float.parseFloat(split[4]);
            vEReverb2Params.erefwet = Float.parseFloat(split[5]);
            vEReverb2Params.dry = Float.parseFloat(split[6]);
            vEReverb2Params.ereffactor = Float.parseFloat(split[7]);
            vEReverb2Params.erefwidth = Float.parseFloat(split[8]);
            vEReverb2Params.width = Float.parseFloat(split[9]);
            vEReverb2Params.wet = Float.parseFloat(split[10]);
            vEReverb2Params.wander = Float.parseFloat(split[11]);
            vEReverb2Params.bassb = Float.parseFloat(split[12]);
            vEReverb2Params.spin = Float.parseFloat(split[13]);
            vEReverb2Params.inputlpf = Float.parseFloat(split[14]);
            vEReverb2Params.basslpf = Float.parseFloat(split[15]);
            vEReverb2Params.damplpf = Float.parseFloat(split[16]);
            vEReverb2Params.outputlpf = Float.parseFloat(split[17]);
            vEReverb2Params.rt60 = Float.parseFloat(split[18]);
            vEReverb2Params.delay = Float.parseFloat(split[19]);
            return vEReverb2Params;
        } catch (Exception unused) {
            return null;
        }
    }

    public VEReverb2Params copy() {
        return fromString(getParamsAsString());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || VEReverb2Params.class != obj.getClass()) {
            return false;
        }
        VEReverb2Params vEReverb2Params = (VEReverb2Params) obj;
        return this.enableExciter == vEReverb2Params.enableExciter && this.enable == vEReverb2Params.enable && this.rate == vEReverb2Params.rate && this.oversamplefactor == vEReverb2Params.oversamplefactor && Float.compare(vEReverb2Params.ertolate, this.ertolate) == 0 && Float.compare(vEReverb2Params.erefwet, this.erefwet) == 0 && Float.compare(vEReverb2Params.dry, this.dry) == 0 && Float.compare(vEReverb2Params.ereffactor, this.ereffactor) == 0 && Float.compare(vEReverb2Params.erefwidth, this.erefwidth) == 0 && Float.compare(vEReverb2Params.width, this.width) == 0 && Float.compare(vEReverb2Params.wet, this.wet) == 0 && Float.compare(vEReverb2Params.wander, this.wander) == 0 && Float.compare(vEReverb2Params.bassb, this.bassb) == 0 && Float.compare(vEReverb2Params.spin, this.spin) == 0 && Float.compare(vEReverb2Params.inputlpf, this.inputlpf) == 0 && Float.compare(vEReverb2Params.basslpf, this.basslpf) == 0 && Float.compare(vEReverb2Params.damplpf, this.damplpf) == 0 && Float.compare(vEReverb2Params.outputlpf, this.outputlpf) == 0 && Float.compare(vEReverb2Params.rt60, this.rt60) == 0 && Float.compare(vEReverb2Params.delay, this.delay) == 0;
    }

    public String getParamsAsString() {
        return (this.enableExciter ? 1 : 0) + "," + (this.enable ? 1 : 0) + "," + this.rate + "," + this.oversamplefactor + "," + this.ertolate + "," + this.erefwet + "," + this.dry + "," + this.ereffactor + "," + this.erefwidth + "," + this.width + "," + this.wet + "," + this.wander + "," + this.bassb + "," + this.spin + "," + this.inputlpf + "," + this.basslpf + "," + this.damplpf + "," + this.outputlpf + "," + this.rt60 + "," + this.delay;
    }

    public String toString() {
        return "Reverb2Params{enableExciter=" + this.enableExciter + "enable=" + this.enable + "rate=" + this.rate + ", oversamplefactor=" + this.oversamplefactor + ", ertolate=" + this.ertolate + ", erefwet=" + this.erefwet + ", dry=" + this.dry + ", ereffactor=" + this.ereffactor + ", erefwidth=" + this.erefwidth + ", width=" + this.width + ", wet=" + this.wet + ", wander=" + this.wander + ", bassb=" + this.bassb + ", spin=" + this.spin + ", inputlpf=" + this.inputlpf + ", basslpf=" + this.basslpf + ", damplpf=" + this.damplpf + ", outputlpf=" + this.outputlpf + ", rt60=" + this.rt60 + ", delay=" + this.delay + '}';
    }
}
