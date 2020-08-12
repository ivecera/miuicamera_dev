package com.ss.android.vesdk;

import android.os.Parcel;
import android.os.Parcelable;
import com.android.camera.Util;

public class VESize implements Parcelable {
    public static final Parcelable.Creator<VESize> CREATOR = new Parcelable.Creator<VESize>() {
        /* class com.ss.android.vesdk.VESize.AnonymousClass1 */

        @Override // android.os.Parcelable.Creator
        public VESize createFromParcel(Parcel parcel) {
            return new VESize(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public VESize[] newArray(int i) {
            return new VESize[i];
        }
    };
    public int height = 1280;
    public int width = Util.LIMIT_SURFACE_WIDTH;

    public VESize(int i, int i2) {
        this.width = i;
        this.height = i2;
    }

    protected VESize(Parcel parcel) {
        this.width = parcel.readInt();
        this.height = parcel.readInt();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.width);
        parcel.writeInt(this.height);
    }
}
