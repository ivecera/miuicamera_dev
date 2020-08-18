package com.ss.android.vesdk;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Keep;

@Keep
public class VECherEffectParam implements Parcelable {
    public static final Parcelable.Creator<VECherEffectParam> CREATOR = new Parcelable.Creator<VECherEffectParam>() {
        /* class com.ss.android.vesdk.VECherEffectParam.AnonymousClass1 */

        @Override // android.os.Parcelable.Creator
        public VECherEffectParam createFromParcel(Parcel parcel) {
            int readInt = parcel.readInt();
            int readInt2 = parcel.readInt();
            String[] strArr = new String[readInt];
            parcel.readStringArray(strArr);
            double[] dArr = new double[(readInt * 2)];
            parcel.readDoubleArray(dArr);
            boolean[] zArr = new boolean[readInt2];
            parcel.readBooleanArray(zArr);
            return new VECherEffectParam(strArr, dArr, zArr);
        }

        @Override // android.os.Parcelable.Creator
        public VECherEffectParam[] newArray(int i) {
            return new VECherEffectParam[i];
        }
    };
    private double[] duration;
    private int length;
    private String[] matrix;
    private boolean[] segUseCher;
    private int totalNum;

    private VECherEffectParam(String[] strArr, double[] dArr, int i, boolean[] zArr) {
        this.matrix = strArr;
        this.duration = dArr;
        this.length = i;
        this.segUseCher = zArr;
        this.totalNum = zArr.length;
    }

    public VECherEffectParam(String[] strArr, double[] dArr, boolean[] zArr) {
        this(strArr, dArr, strArr.length, zArr);
    }

    public int describeContents() {
        return 0;
    }

    public double[] getDuration() {
        return this.duration;
    }

    public String[] getMatrix() {
        return this.matrix;
    }

    public boolean[] getSegUseCher() {
        return this.segUseCher;
    }

    public void setDuration(double[] dArr) {
        this.duration = dArr;
    }

    public void setMatrix(String[] strArr) {
        this.matrix = strArr;
    }

    public void setSegUseCher(boolean[] zArr) {
        this.segUseCher = zArr;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.matrix.length);
        parcel.writeInt(this.totalNum);
        parcel.writeStringArray(this.matrix);
        parcel.writeDoubleArray(this.duration);
        parcel.writeBooleanArray(this.segUseCher);
    }
}
