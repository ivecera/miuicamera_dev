package androidx.versionedparcelable;

import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: ParcelImpl */
class d implements Parcelable.Creator<ParcelImpl> {
    d() {
    }

    @Override // android.os.Parcelable.Creator
    public ParcelImpl createFromParcel(Parcel parcel) {
        return new ParcelImpl(parcel);
    }

    @Override // android.os.Parcelable.Creator
    public ParcelImpl[] newArray(int i) {
        return new ParcelImpl[i];
    }
}
