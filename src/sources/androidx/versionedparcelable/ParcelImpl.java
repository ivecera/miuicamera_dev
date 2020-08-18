package androidx.versionedparcelable;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.RestrictTo;

@RestrictTo({RestrictTo.Scope.LIBRARY})
public class ParcelImpl implements Parcelable {
    public static final Parcelable.Creator<ParcelImpl> CREATOR = new d();
    private final i mParcel;

    protected ParcelImpl(Parcel parcel) {
        this.mParcel = new g(parcel).lb();
    }

    public ParcelImpl(i iVar) {
        this.mParcel = iVar;
    }

    public <T extends i> T ab() {
        return this.mParcel;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        new g(parcel).b(this.mParcel);
    }
}
