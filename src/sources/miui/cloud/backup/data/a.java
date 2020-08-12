package miui.cloud.backup.data;

import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: DataPackage */
class a implements Parcelable.Creator<DataPackage> {
    a() {
    }

    @Override // android.os.Parcelable.Creator
    public DataPackage createFromParcel(Parcel parcel) {
        return DataPackage.e(parcel.readBundle());
    }

    @Override // android.os.Parcelable.Creator
    public DataPackage[] newArray(int i) {
        return new DataPackage[i];
    }
}
