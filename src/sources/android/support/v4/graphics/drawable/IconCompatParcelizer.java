package android.support.v4.graphics.drawable;

import android.support.annotation.RestrictTo;
import androidx.versionedparcelable.VersionedParcel;
import b.a.a.a.a;

@RestrictTo({RestrictTo.Scope.LIBRARY})
public final class IconCompatParcelizer extends a {
    @Override // b.a.a.a.a
    public static IconCompat read(VersionedParcel versionedParcel) {
        return a.read(versionedParcel);
    }

    @Override // b.a.a.a.a
    public static void write(IconCompat iconCompat, VersionedParcel versionedParcel) {
        a.write(iconCompat, versionedParcel);
    }
}
