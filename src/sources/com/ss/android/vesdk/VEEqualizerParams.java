package com.ss.android.vesdk;

import android.os.Parcel;
import android.os.Parcelable;

public class VEEqualizerParams implements Parcelable {
    public static final Parcelable.Creator<VEEqualizerParams> CREATOR = new Parcelable.Creator<VEEqualizerParams>() {
        /* class com.ss.android.vesdk.VEEqualizerParams.AnonymousClass1 */

        @Override // android.os.Parcelable.Creator
        public VEEqualizerParams createFromParcel(Parcel parcel) {
            return new VEEqualizerParams(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public VEEqualizerParams[] newArray(int i) {
            return new VEEqualizerParams[i];
        }
    };
    public float amp1000;
    public float amp125;
    public float amp16000;
    public float amp2000;
    public float amp250;
    public float amp31;
    public float amp4000;
    public float amp500;
    public float amp63;
    public float amp8000;
    public float freqWidth1000;
    public float freqWidth125;
    public float freqWidth16000;
    public float freqWidth2000;
    public float freqWidth250;
    public float freqWidth31;
    public float freqWidth4000;
    public float freqWidth500;
    public float freqWidth63;
    public float freqWidth8000;
    public String name;
    public float preamp;

    public static class Presets {
        public static final VEEqualizerParams CLASSICAL = new VEEqualizerParams(12.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, -7.2f, -7.2f, -7.2f, -9.6f);
        public static final VEEqualizerParams CLUB = new VEEqualizerParams(6.0f, 0.0f, 0.0f, 8.0f, 5.6f, 5.6f, 5.6f, 3.2f, 0.0f, 0.0f, 0.0f);
        public static final VEEqualizerParams DANCE = new VEEqualizerParams(5.0f, 9.6f, 7.2f, 2.4f, 0.0f, 0.0f, -5.6f, -7.2f, -7.2f, 0.0f, 0.0f);
        public static final VEEqualizerParams FLAT = new VEEqualizerParams(12.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f);
        public static final VEEqualizerParams FULLBASS = new VEEqualizerParams(5.0f, -8.0f, 9.6f, 9.6f, 5.6f, 1.6f, -4.0f, -8.0f, -10.4f, -11.2f, -11.2f);
        public static final VEEqualizerParams FULLBASSTREBLE = new VEEqualizerParams(4.0f, 7.2f, 5.6f, 0.0f, -7.2f, -4.8f, 1.6f, 8.0f, 11.2f, 12.0f, 12.0f);
        public static final VEEqualizerParams FULLTREBLE = new VEEqualizerParams(3.0f, -9.6f, -9.6f, -9.6f, -4.0f, 2.4f, 11.2f, 16.0f, 16.0f, 16.0f, 16.8f);
        public static final VEEqualizerParams HEADPHONES = new VEEqualizerParams(4.0f, 4.8f, 11.2f, 5.6f, -3.2f, -2.4f, 1.6f, 4.8f, 9.6f, 12.8f, 14.4f);
        public static final VEEqualizerParams LARGEHALL = new VEEqualizerParams(5.0f, 10.4f, 10.4f, 5.6f, 5.6f, 0.0f, -4.8f, -4.8f, -4.8f, 0.0f, 0.0f);
        public static final VEEqualizerParams LIVE = new VEEqualizerParams(7.0f, -4.8f, 0.0f, 4.0f, 5.6f, 5.6f, 5.6f, 4.0f, 2.4f, 2.4f, 2.4f);
        public static final VEEqualizerParams NONE = new VEEqualizerParams();
        public static final VEEqualizerParams PARTY = new VEEqualizerParams(6.0f, 7.2f, 7.2f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 7.2f, 7.2f);
        public static final VEEqualizerParams POP = new VEEqualizerParams(6.0f, -1.6f, 4.8f, 7.2f, 8.0f, 5.6f, 0.0f, -2.4f, -2.4f, -1.6f, -1.6f);
        public static final VEEqualizerParams ROCK = new VEEqualizerParams(5.0f, 8.0f, 4.8f, -5.6f, -8.0f, -3.2f, 4.0f, 8.8f, 11.2f, 11.2f, 11.2f);
        public static final VEEqualizerParams SOFT = new VEEqualizerParams(5.0f, 4.8f, 1.6f, 0.0f, -2.4f, 0.0f, 4.0f, 8.0f, 9.6f, 11.2f, 12.0f);

        public static VEEqualizerParams fromValue(int i) {
            switch (i) {
                case 0:
                    return FLAT;
                case 1:
                    return CLASSICAL;
                case 2:
                    return CLUB;
                case 3:
                    return DANCE;
                case 4:
                    return FULLBASS;
                case 5:
                    return FULLBASSTREBLE;
                case 6:
                    return FULLTREBLE;
                case 7:
                    return HEADPHONES;
                case 8:
                    return LARGEHALL;
                case 9:
                    return LIVE;
                case 10:
                    return PARTY;
                case 11:
                    return POP;
                case 12:
                    return ROCK;
                case 13:
                    return SOFT;
                default:
                    return NONE;
            }
        }
    }

    public VEEqualizerParams() {
        this.name = "";
        this.preamp = 12.0f;
        this.freqWidth31 = 1.0f;
        this.freqWidth63 = 1.0f;
        this.freqWidth125 = 1.0f;
        this.freqWidth250 = 1.0f;
        this.freqWidth500 = 1.0f;
        this.freqWidth1000 = 1.0f;
        this.freqWidth2000 = 1.0f;
        this.freqWidth4000 = 1.0f;
        this.freqWidth8000 = 1.0f;
        this.freqWidth16000 = 1.0f;
        this.amp31 = 0.0f;
        this.amp63 = 0.0f;
        this.amp125 = 0.0f;
        this.amp250 = 0.0f;
        this.amp500 = 0.0f;
        this.amp1000 = 0.0f;
        this.amp2000 = 0.0f;
        this.amp4000 = 0.0f;
        this.amp8000 = 0.0f;
        this.amp16000 = 0.0f;
    }

    private VEEqualizerParams(float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, float f10, float f11, float f12) {
        this.name = "";
        this.preamp = 12.0f;
        this.freqWidth31 = 1.0f;
        this.freqWidth63 = 1.0f;
        this.freqWidth125 = 1.0f;
        this.freqWidth250 = 1.0f;
        this.freqWidth500 = 1.0f;
        this.freqWidth1000 = 1.0f;
        this.freqWidth2000 = 1.0f;
        this.freqWidth4000 = 1.0f;
        this.freqWidth8000 = 1.0f;
        this.freqWidth16000 = 1.0f;
        this.amp31 = 0.0f;
        this.amp63 = 0.0f;
        this.amp125 = 0.0f;
        this.amp250 = 0.0f;
        this.amp500 = 0.0f;
        this.amp1000 = 0.0f;
        this.amp2000 = 0.0f;
        this.amp4000 = 0.0f;
        this.amp8000 = 0.0f;
        this.amp16000 = 0.0f;
        this.name = "custom";
        this.preamp = f2;
        this.amp31 = f3;
        this.amp63 = f4;
        this.amp125 = f5;
        this.amp250 = f6;
        this.amp500 = f7;
        this.amp1000 = f8;
        this.amp2000 = f9;
        this.amp4000 = f10;
        this.amp8000 = f11;
        this.amp16000 = f12;
    }

    private VEEqualizerParams(float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, float f10, float f11, float f12, float f13, float f14, float f15, float f16, float f17, float f18, float f19, float f20, float f21, float f22) {
        this.name = "";
        this.preamp = 12.0f;
        this.freqWidth31 = 1.0f;
        this.freqWidth63 = 1.0f;
        this.freqWidth125 = 1.0f;
        this.freqWidth250 = 1.0f;
        this.freqWidth500 = 1.0f;
        this.freqWidth1000 = 1.0f;
        this.freqWidth2000 = 1.0f;
        this.freqWidth4000 = 1.0f;
        this.freqWidth8000 = 1.0f;
        this.freqWidth16000 = 1.0f;
        this.amp31 = 0.0f;
        this.amp63 = 0.0f;
        this.amp125 = 0.0f;
        this.amp250 = 0.0f;
        this.amp500 = 0.0f;
        this.amp1000 = 0.0f;
        this.amp2000 = 0.0f;
        this.amp4000 = 0.0f;
        this.amp8000 = 0.0f;
        this.amp16000 = 0.0f;
        this.name = "custom";
        this.preamp = f12;
        this.freqWidth31 = f2;
        this.freqWidth63 = f3;
        this.freqWidth125 = f4;
        this.freqWidth250 = f5;
        this.freqWidth500 = f6;
        this.freqWidth1000 = f7;
        this.freqWidth2000 = f8;
        this.freqWidth4000 = f9;
        this.freqWidth8000 = f10;
        this.freqWidth16000 = f11;
        this.amp31 = f13;
        this.amp63 = f14;
        this.amp125 = f15;
        this.amp250 = f16;
        this.amp500 = f17;
        this.amp1000 = f18;
        this.amp2000 = f19;
        this.amp4000 = f20;
        this.amp8000 = f21;
        this.amp16000 = f22;
    }

    protected VEEqualizerParams(Parcel parcel) {
        this.name = "";
        this.preamp = 12.0f;
        this.freqWidth31 = 1.0f;
        this.freqWidth63 = 1.0f;
        this.freqWidth125 = 1.0f;
        this.freqWidth250 = 1.0f;
        this.freqWidth500 = 1.0f;
        this.freqWidth1000 = 1.0f;
        this.freqWidth2000 = 1.0f;
        this.freqWidth4000 = 1.0f;
        this.freqWidth8000 = 1.0f;
        this.freqWidth16000 = 1.0f;
        this.amp31 = 0.0f;
        this.amp63 = 0.0f;
        this.amp125 = 0.0f;
        this.amp250 = 0.0f;
        this.amp500 = 0.0f;
        this.amp1000 = 0.0f;
        this.amp2000 = 0.0f;
        this.amp4000 = 0.0f;
        this.amp8000 = 0.0f;
        this.amp16000 = 0.0f;
        this.name = parcel.readString();
        this.preamp = parcel.readFloat();
        this.freqWidth31 = parcel.readFloat();
        this.freqWidth63 = parcel.readFloat();
        this.freqWidth125 = parcel.readFloat();
        this.freqWidth250 = parcel.readFloat();
        this.freqWidth500 = parcel.readFloat();
        this.freqWidth1000 = parcel.readFloat();
        this.freqWidth2000 = parcel.readFloat();
        this.freqWidth4000 = parcel.readFloat();
        this.freqWidth8000 = parcel.readFloat();
        this.freqWidth16000 = parcel.readFloat();
        this.amp31 = parcel.readFloat();
        this.amp63 = parcel.readFloat();
        this.amp125 = parcel.readFloat();
        this.amp250 = parcel.readFloat();
        this.amp500 = parcel.readFloat();
        this.amp1000 = parcel.readFloat();
        this.amp2000 = parcel.readFloat();
        this.amp4000 = parcel.readFloat();
        this.amp8000 = parcel.readFloat();
        this.amp16000 = parcel.readFloat();
    }

    public static VEEqualizerParams fromString(String str) {
        String[] split = str.split(",");
        try {
            VEEqualizerParams vEEqualizerParams = new VEEqualizerParams();
            vEEqualizerParams.name = split[0];
            vEEqualizerParams.preamp = Float.parseFloat(split[1]);
            vEEqualizerParams.freqWidth31 = Float.parseFloat(split[2]);
            vEEqualizerParams.freqWidth63 = Float.parseFloat(split[3]);
            vEEqualizerParams.freqWidth125 = Float.parseFloat(split[4]);
            vEEqualizerParams.freqWidth250 = Float.parseFloat(split[5]);
            vEEqualizerParams.freqWidth500 = Float.parseFloat(split[6]);
            vEEqualizerParams.freqWidth1000 = Float.parseFloat(split[7]);
            vEEqualizerParams.freqWidth2000 = Float.parseFloat(split[8]);
            vEEqualizerParams.freqWidth4000 = Float.parseFloat(split[9]);
            vEEqualizerParams.freqWidth8000 = Float.parseFloat(split[10]);
            vEEqualizerParams.freqWidth16000 = Float.parseFloat(split[11]);
            vEEqualizerParams.amp31 = Float.parseFloat(split[12]);
            vEEqualizerParams.amp63 = Float.parseFloat(split[13]);
            vEEqualizerParams.amp125 = Float.parseFloat(split[14]);
            vEEqualizerParams.amp250 = Float.parseFloat(split[15]);
            vEEqualizerParams.amp500 = Float.parseFloat(split[16]);
            vEEqualizerParams.amp1000 = Float.parseFloat(split[17]);
            vEEqualizerParams.amp2000 = Float.parseFloat(split[18]);
            vEEqualizerParams.amp4000 = Float.parseFloat(split[19]);
            vEEqualizerParams.amp8000 = Float.parseFloat(split[20]);
            vEEqualizerParams.amp16000 = Float.parseFloat(split[21]);
            return vEEqualizerParams;
        } catch (Exception unused) {
            return null;
        }
    }

    public VEEqualizerParams copy() {
        return fromString(getParamsAsString());
    }

    public int describeContents() {
        return 0;
    }

    public String getParamsAsString() {
        return this.name + "," + this.preamp + "," + this.freqWidth31 + "," + this.freqWidth63 + "," + this.freqWidth125 + "," + this.freqWidth250 + "," + this.freqWidth500 + "," + this.freqWidth1000 + "," + this.freqWidth2000 + "," + this.freqWidth4000 + "," + this.freqWidth8000 + "," + this.freqWidth16000 + "," + this.amp31 + "," + this.amp63 + "," + this.amp125 + "," + this.amp250 + "," + this.amp500 + "," + this.amp1000 + "," + this.amp2000 + "," + this.amp4000 + "," + this.amp8000 + "," + this.amp16000;
    }

    public String toString() {
        return "VEEqualizerParams{name='" + this.name + '\'' + ", preamp=" + this.preamp + ", amp31=" + this.amp31 + ", amp63=" + this.amp63 + ", amp125=" + this.amp125 + ", amp250=" + this.amp250 + ", amp500=" + this.amp500 + ", amp1000=" + this.amp1000 + ", amp2000=" + this.amp2000 + ", amp4000=" + this.amp4000 + ", amp8000=" + this.amp8000 + ", amp16000=" + this.amp16000 + ", freqWidth31=" + this.freqWidth31 + ", freqWidth63=" + this.freqWidth63 + ", freqWidth125=" + this.freqWidth125 + ", freqWidth250=" + this.freqWidth250 + ", freqWidth500=" + this.freqWidth500 + ", freqWidth1000=" + this.freqWidth1000 + ", freqWidth2000=" + this.freqWidth2000 + ", freqWidth4000=" + this.freqWidth4000 + ", freqWidth8000=" + this.freqWidth8000 + ", freqWidth16000=" + this.freqWidth16000 + '}';
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.name);
        parcel.writeFloat(this.preamp);
        parcel.writeFloat(this.freqWidth31);
        parcel.writeFloat(this.freqWidth63);
        parcel.writeFloat(this.freqWidth125);
        parcel.writeFloat(this.freqWidth250);
        parcel.writeFloat(this.freqWidth500);
        parcel.writeFloat(this.freqWidth1000);
        parcel.writeFloat(this.freqWidth2000);
        parcel.writeFloat(this.freqWidth4000);
        parcel.writeFloat(this.freqWidth8000);
        parcel.writeFloat(this.freqWidth16000);
        parcel.writeFloat(this.amp31);
        parcel.writeFloat(this.amp63);
        parcel.writeFloat(this.amp125);
        parcel.writeFloat(this.amp250);
        parcel.writeFloat(this.amp500);
        parcel.writeFloat(this.amp1000);
        parcel.writeFloat(this.amp2000);
        parcel.writeFloat(this.amp4000);
        parcel.writeFloat(this.amp8000);
        parcel.writeFloat(this.amp16000);
    }
}
