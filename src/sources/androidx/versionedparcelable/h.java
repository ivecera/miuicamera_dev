package androidx.versionedparcelable;

import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcelable;
import android.support.annotation.RestrictTo;
import android.util.SparseArray;
import androidx.versionedparcelable.VersionedParcel;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Set;

@RestrictTo({RestrictTo.Scope.LIBRARY})
/* compiled from: VersionedParcelStream */
class h extends VersionedParcel {
    private static final int Ai = 8;
    private static final int Bi = 10;
    private static final int Ci = 12;
    private static final int Di = 14;
    private static final int TYPE_BOOLEAN = 5;
    private static final int TYPE_DOUBLE = 7;
    private static final int TYPE_FLOAT = 13;
    private static final int TYPE_INT = 9;
    private static final int TYPE_LONG = 11;
    private static final int TYPE_NULL = 0;
    private static final int TYPE_STRING = 3;
    private static final Charset UTF_16 = Charset.forName("UTF-16");
    private static final int wi = 1;
    private static final int xi = 2;
    private static final int yi = 4;
    private static final int zi = 6;
    private final DataInputStream ni;
    private final DataOutputStream oi;
    private final SparseArray<b> qi = new SparseArray<>();
    private DataInputStream ri;
    private DataOutputStream ti;
    private a ui;
    private boolean vi;

    /* compiled from: VersionedParcelStream */
    private static class a {
        /* access modifiers changed from: private */
        public final ByteArrayOutputStream Ei = new ByteArrayOutputStream();
        /* access modifiers changed from: private */
        public final DataOutputStream Fi = new DataOutputStream(this.Ei);
        private final int Gi;
        private final DataOutputStream mTarget;

        a(int i, DataOutputStream dataOutputStream) {
            this.Gi = i;
            this.mTarget = dataOutputStream;
        }

        /* access modifiers changed from: package-private */
        public void mb() throws IOException {
            this.Fi.flush();
            int size = this.Ei.size();
            this.mTarget.writeInt((this.Gi << 16) | (size >= 65535 ? 65535 : size));
            if (size >= 65535) {
                this.mTarget.writeInt(size);
            }
            this.Ei.writeTo(this.mTarget);
        }
    }

    /* compiled from: VersionedParcelStream */
    private static class b {
        /* access modifiers changed from: private */
        public final int Gi;
        /* access modifiers changed from: private */
        public final DataInputStream mInputStream;
        private final int mSize;

        b(int i, int i2, DataInputStream dataInputStream) throws IOException {
            this.mSize = i2;
            this.Gi = i;
            byte[] bArr = new byte[this.mSize];
            dataInputStream.readFully(bArr);
            this.mInputStream = new DataInputStream(new ByteArrayInputStream(bArr));
        }
    }

    public h(InputStream inputStream, OutputStream outputStream) {
        DataOutputStream dataOutputStream = null;
        this.ni = inputStream != null ? new DataInputStream(inputStream) : null;
        this.oi = outputStream != null ? new DataOutputStream(outputStream) : dataOutputStream;
        this.ri = this.ni;
        this.ti = this.oi;
    }

    private void a(int i, String str, Bundle bundle) {
        switch (i) {
            case 0:
                bundle.putParcelable(str, null);
                return;
            case 1:
                bundle.putBundle(str, readBundle());
                return;
            case 2:
                bundle.putBundle(str, readBundle());
                return;
            case 3:
                bundle.putString(str, readString());
                return;
            case 4:
                bundle.putStringArray(str, (String[]) a(new String[0]));
                return;
            case 5:
                bundle.putBoolean(str, readBoolean());
                return;
            case 6:
                bundle.putBooleanArray(str, fb());
                return;
            case 7:
                bundle.putDouble(str, readDouble());
                return;
            case 8:
                bundle.putDoubleArray(str, gb());
                return;
            case 9:
                bundle.putInt(str, readInt());
                return;
            case 10:
                bundle.putIntArray(str, ib());
                return;
            case 11:
                bundle.putLong(str, readLong());
                return;
            case 12:
                bundle.putLongArray(str, jb());
                return;
            case 13:
                bundle.putFloat(str, readFloat());
                return;
            case 14:
                bundle.putFloatArray(str, hb());
                return;
            default:
                throw new RuntimeException("Unknown type " + i);
        }
    }

    private void writeObject(Object obj) {
        if (obj == null) {
            writeInt(0);
        } else if (obj instanceof Bundle) {
            writeInt(1);
            writeBundle((Bundle) obj);
        } else if (obj instanceof String) {
            writeInt(3);
            writeString((String) obj);
        } else if (obj instanceof String[]) {
            writeInt(4);
            writeArray((String[]) obj);
        } else if (obj instanceof Boolean) {
            writeInt(5);
            writeBoolean(((Boolean) obj).booleanValue());
        } else if (obj instanceof boolean[]) {
            writeInt(6);
            writeBooleanArray((boolean[]) obj);
        } else if (obj instanceof Double) {
            writeInt(7);
            writeDouble(((Double) obj).doubleValue());
        } else if (obj instanceof double[]) {
            writeInt(8);
            writeDoubleArray((double[]) obj);
        } else if (obj instanceof Integer) {
            writeInt(9);
            writeInt(((Integer) obj).intValue());
        } else if (obj instanceof int[]) {
            writeInt(10);
            writeIntArray((int[]) obj);
        } else if (obj instanceof Long) {
            writeInt(11);
            writeLong(((Long) obj).longValue());
        } else if (obj instanceof long[]) {
            writeInt(12);
            writeLongArray((long[]) obj);
        } else if (obj instanceof Float) {
            writeInt(13);
            writeFloat(((Float) obj).floatValue());
        } else if (obj instanceof float[]) {
            writeInt(14);
            writeFloatArray((float[]) obj);
        } else {
            throw new IllegalArgumentException("Unsupported type " + obj.getClass());
        }
    }

    @Override // androidx.versionedparcelable.VersionedParcel
    public void b(Parcelable parcelable) {
        if (!this.vi) {
            throw new RuntimeException("Parcelables cannot be written to an OutputStream");
        }
    }

    @Override // androidx.versionedparcelable.VersionedParcel
    public void b(boolean z, boolean z2) {
        if (z) {
            this.vi = z2;
            return;
        }
        throw new RuntimeException("Serialization of this object is not allowed");
    }

    @Override // androidx.versionedparcelable.VersionedParcel
    public void bb() {
        a aVar = this.ui;
        if (aVar != null) {
            try {
                if (aVar.Ei.size() != 0) {
                    this.ui.mb();
                }
                this.ui = null;
            } catch (IOException e2) {
                throw new VersionedParcel.ParcelException(e2);
            }
        }
    }

    /* access modifiers changed from: protected */
    @Override // androidx.versionedparcelable.VersionedParcel
    public VersionedParcel db() {
        return new h(this.ri, this.ti);
    }

    @Override // androidx.versionedparcelable.VersionedParcel
    public boolean eb() {
        return true;
    }

    @Override // androidx.versionedparcelable.VersionedParcel
    public <T extends Parcelable> T kb() {
        return null;
    }

    @Override // androidx.versionedparcelable.VersionedParcel
    public boolean m(int i) {
        b bVar = this.qi.get(i);
        if (bVar != null) {
            this.qi.remove(i);
            this.ri = bVar.mInputStream;
            return true;
        }
        while (true) {
            try {
                int readInt = this.ni.readInt();
                int i2 = readInt & 65535;
                if (i2 == 65535) {
                    i2 = this.ni.readInt();
                }
                b bVar2 = new b((readInt >> 16) & 65535, i2, this.ni);
                if (bVar2.Gi == i) {
                    this.ri = bVar2.mInputStream;
                    return true;
                }
                this.qi.put(bVar2.Gi, bVar2);
            } catch (IOException unused) {
                return false;
            }
        }
    }

    @Override // androidx.versionedparcelable.VersionedParcel
    public void n(int i) {
        bb();
        this.ui = new a(i, this.oi);
        this.ti = this.ui.Fi;
    }

    @Override // androidx.versionedparcelable.VersionedParcel
    public boolean readBoolean() {
        try {
            return this.ri.readBoolean();
        } catch (IOException e2) {
            throw new VersionedParcel.ParcelException(e2);
        }
    }

    @Override // androidx.versionedparcelable.VersionedParcel
    public Bundle readBundle() {
        int readInt = readInt();
        if (readInt < 0) {
            return null;
        }
        Bundle bundle = new Bundle();
        for (int i = 0; i < readInt; i++) {
            a(readInt(), readString(), bundle);
        }
        return bundle;
    }

    @Override // androidx.versionedparcelable.VersionedParcel
    public byte[] readByteArray() {
        try {
            int readInt = this.ri.readInt();
            if (readInt <= 0) {
                return null;
            }
            byte[] bArr = new byte[readInt];
            this.ri.readFully(bArr);
            return bArr;
        } catch (IOException e2) {
            throw new VersionedParcel.ParcelException(e2);
        }
    }

    @Override // androidx.versionedparcelable.VersionedParcel
    public double readDouble() {
        try {
            return this.ri.readDouble();
        } catch (IOException e2) {
            throw new VersionedParcel.ParcelException(e2);
        }
    }

    @Override // androidx.versionedparcelable.VersionedParcel
    public float readFloat() {
        try {
            return this.ri.readFloat();
        } catch (IOException e2) {
            throw new VersionedParcel.ParcelException(e2);
        }
    }

    @Override // androidx.versionedparcelable.VersionedParcel
    public int readInt() {
        try {
            return this.ri.readInt();
        } catch (IOException e2) {
            throw new VersionedParcel.ParcelException(e2);
        }
    }

    @Override // androidx.versionedparcelable.VersionedParcel
    public long readLong() {
        try {
            return this.ri.readLong();
        } catch (IOException e2) {
            throw new VersionedParcel.ParcelException(e2);
        }
    }

    @Override // androidx.versionedparcelable.VersionedParcel
    public String readString() {
        try {
            int readInt = this.ri.readInt();
            if (readInt <= 0) {
                return null;
            }
            byte[] bArr = new byte[readInt];
            this.ri.readFully(bArr);
            return new String(bArr, UTF_16);
        } catch (IOException e2) {
            throw new VersionedParcel.ParcelException(e2);
        }
    }

    @Override // androidx.versionedparcelable.VersionedParcel
    public IBinder readStrongBinder() {
        return null;
    }

    @Override // androidx.versionedparcelable.VersionedParcel
    public void writeBoolean(boolean z) {
        try {
            this.ti.writeBoolean(z);
        } catch (IOException e2) {
            throw new VersionedParcel.ParcelException(e2);
        }
    }

    @Override // androidx.versionedparcelable.VersionedParcel
    public void writeBundle(Bundle bundle) {
        if (bundle != null) {
            try {
                Set<String> keySet = bundle.keySet();
                this.ti.writeInt(keySet.size());
                for (String str : keySet) {
                    writeString(str);
                    writeObject(bundle.get(str));
                }
            } catch (IOException e2) {
                throw new VersionedParcel.ParcelException(e2);
            }
        } else {
            this.ti.writeInt(-1);
        }
    }

    @Override // androidx.versionedparcelable.VersionedParcel
    public void writeByteArray(byte[] bArr) {
        if (bArr != null) {
            try {
                this.ti.writeInt(bArr.length);
                this.ti.write(bArr);
            } catch (IOException e2) {
                throw new VersionedParcel.ParcelException(e2);
            }
        } else {
            this.ti.writeInt(-1);
        }
    }

    @Override // androidx.versionedparcelable.VersionedParcel
    public void writeByteArray(byte[] bArr, int i, int i2) {
        if (bArr != null) {
            try {
                this.ti.writeInt(i2);
                this.ti.write(bArr, i, i2);
            } catch (IOException e2) {
                throw new VersionedParcel.ParcelException(e2);
            }
        } else {
            this.ti.writeInt(-1);
        }
    }

    @Override // androidx.versionedparcelable.VersionedParcel
    public void writeDouble(double d2) {
        try {
            this.ti.writeDouble(d2);
        } catch (IOException e2) {
            throw new VersionedParcel.ParcelException(e2);
        }
    }

    @Override // androidx.versionedparcelable.VersionedParcel
    public void writeFloat(float f2) {
        try {
            this.ti.writeFloat(f2);
        } catch (IOException e2) {
            throw new VersionedParcel.ParcelException(e2);
        }
    }

    @Override // androidx.versionedparcelable.VersionedParcel
    public void writeInt(int i) {
        try {
            this.ti.writeInt(i);
        } catch (IOException e2) {
            throw new VersionedParcel.ParcelException(e2);
        }
    }

    @Override // androidx.versionedparcelable.VersionedParcel
    public void writeLong(long j) {
        try {
            this.ti.writeLong(j);
        } catch (IOException e2) {
            throw new VersionedParcel.ParcelException(e2);
        }
    }

    @Override // androidx.versionedparcelable.VersionedParcel
    public void writeString(String str) {
        if (str != null) {
            try {
                byte[] bytes = str.getBytes(UTF_16);
                this.ti.writeInt(bytes.length);
                this.ti.write(bytes);
            } catch (IOException e2) {
                throw new VersionedParcel.ParcelException(e2);
            }
        } else {
            this.ti.writeInt(-1);
        }
    }

    @Override // androidx.versionedparcelable.VersionedParcel
    public void writeStrongBinder(IBinder iBinder) {
        if (!this.vi) {
            throw new RuntimeException("Binders cannot be written to an OutputStream");
        }
    }

    @Override // androidx.versionedparcelable.VersionedParcel
    public void writeStrongInterface(IInterface iInterface) {
        if (!this.vi) {
            throw new RuntimeException("Binders cannot be written to an OutputStream");
        }
    }
}
