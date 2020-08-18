package com.ss.android.vesdk.settings;

import android.os.Parcel;
import android.os.Parcelable;

public enum VEVideoEncodePreset implements Parcelable {
    ENCODE_LEVEL_ULTRAFAST,
    ENCODE_LEVEL_SUPERFAST,
    ENCODE_LEVEL_VERYFAST,
    ENCODE_LEVEL_FASTER,
    ENCODE_LEVEL_FAST,
    ENCODE_LEVEL_MEDIUM,
    ENCODE_LEVEL_SLOW,
    ENCODE_LEVEL_SLOWER,
    ENCODE_LEVEL_VERYSLOW,
    ENCODE_LEVEL_PLACEBO;
    
    public static final Parcelable.Creator<VEVideoEncodePreset> CREATOR = new Parcelable.Creator<VEVideoEncodePreset>() {
        /* class com.ss.android.vesdk.settings.VEVideoEncodePreset.AnonymousClass1 */

        @Override // android.os.Parcelable.Creator
        public VEVideoEncodePreset createFromParcel(Parcel parcel) {
            return VEVideoEncodePreset.values()[parcel.readInt()];
        }

        @Override // android.os.Parcelable.Creator
        public VEVideoEncodePreset[] newArray(int i) {
            return new VEVideoEncodePreset[i];
        }
    };

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(ordinal());
    }
}
