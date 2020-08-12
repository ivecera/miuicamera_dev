package com.ss.android.vesdk.settings;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Keep;
import com.ss.android.vesdk.VEVideoEncodeSettings;

@Keep
public class VEVideoSWEncodeSettings implements Parcelable {
    public static final Parcelable.Creator<VEVideoSWEncodeSettings> CREATOR = new Parcelable.Creator<VEVideoSWEncodeSettings>() {
        /* class com.ss.android.vesdk.settings.VEVideoSWEncodeSettings.AnonymousClass1 */

        @Override // android.os.Parcelable.Creator
        public VEVideoSWEncodeSettings createFromParcel(Parcel parcel) {
            return new VEVideoSWEncodeSettings(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public VEVideoSWEncodeSettings[] newArray(int i) {
            return new VEVideoSWEncodeSettings[i];
        }
    };
    public int mBitrateMode = VEVideoEncodeSettings.ENCODE_BITRATE_MODE.ENCODE_BITRATE_CRF.ordinal();
    public int mBps = 4000000;
    public int mCrf = 15;
    public int mGop = 35;
    public long mMaxRate = 15000000;
    public int mPreset = VEVideoEncodePreset.ENCODE_LEVEL_ULTRAFAST.ordinal();
    public int mProfile = VEVideoEncodeProfile.ENCODE_PROFILE_UNKNOWN.ordinal();

    public VEVideoSWEncodeSettings() {
    }

    protected VEVideoSWEncodeSettings(Parcel parcel) {
        this.mCrf = parcel.readInt();
        this.mPreset = parcel.readInt();
        this.mProfile = parcel.readInt();
        this.mMaxRate = parcel.readLong();
        this.mBps = parcel.readInt();
        this.mBitrateMode = parcel.readInt();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mCrf);
        parcel.writeInt(this.mPreset);
        parcel.writeInt(this.mProfile);
        parcel.writeLong(this.mMaxRate);
        parcel.writeInt(this.mBps);
        parcel.writeInt(this.mBitrateMode);
    }
}
