package com.ss.android.vesdk;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import com.android.camera.Util;

public class VECameraSettings {
    private CAMERA_FACING_ID facingID;
    private int orientation;
    private VESize size = new VESize(Util.LIMIT_SURFACE_WIDTH, 1280);

    public enum CAMERA_FACING_ID implements Parcelable {
        FACING_BACK,
        FACING_FRONT;
        
        public static final Parcelable.Creator<CAMERA_FACING_ID> CREATOR = new Parcelable.Creator<CAMERA_FACING_ID>() {
            /* class com.ss.android.vesdk.VECameraSettings.CAMERA_FACING_ID.AnonymousClass1 */

            @Override // android.os.Parcelable.Creator
            public CAMERA_FACING_ID createFromParcel(Parcel parcel) {
                return CAMERA_FACING_ID.values()[parcel.readInt()];
            }

            @Override // android.os.Parcelable.Creator
            public CAMERA_FACING_ID[] newArray(int i) {
                return new CAMERA_FACING_ID[i];
            }
        };

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(ordinal());
        }
    }

    public VECameraSettings(CAMERA_FACING_ID camera_facing_id, int i, @NonNull VESize vESize) {
        this.facingID = camera_facing_id;
        this.orientation = i;
        this.size = vESize;
    }

    public CAMERA_FACING_ID getFacingID() {
        return this.facingID;
    }

    public int getOrientation() {
        return this.orientation;
    }

    public VESize getSize() {
        return this.size;
    }
}
