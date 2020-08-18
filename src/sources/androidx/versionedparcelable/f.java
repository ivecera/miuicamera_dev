package androidx.versionedparcelable;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;

/* compiled from: VersionedParcel */
class f extends ObjectInputStream {
    final /* synthetic */ VersionedParcel this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    f(VersionedParcel versionedParcel, InputStream inputStream) {
        super(inputStream);
        this.this$0 = versionedParcel;
    }

    /* access modifiers changed from: protected */
    @Override // java.io.ObjectInputStream
    public Class<?> resolveClass(ObjectStreamClass objectStreamClass) throws IOException, ClassNotFoundException {
        Class<?> cls = Class.forName(objectStreamClass.getName(), false, f.class.getClassLoader());
        return cls != null ? cls : super.resolveClass(objectStreamClass);
    }
}
