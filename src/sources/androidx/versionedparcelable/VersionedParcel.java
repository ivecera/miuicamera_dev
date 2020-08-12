package androidx.versionedparcelable;

import android.os.BadParcelableException;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.NetworkOnMainThreadException;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.util.Size;
import android.util.SizeF;
import android.util.SparseBooleanArray;
import com.xiaomi.camera.base.Constants;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public abstract class VersionedParcel {
    private static final String TAG = "VersionedParcel";
    private static final int TYPE_STRING = 4;
    private static final int Xh = -1;
    private static final int Yh = -2;
    private static final int Zh = -3;
    private static final int _h = -4;
    private static final int bi = -5;
    private static final int di = -6;
    private static final int ei = -7;
    private static final int fi = -9;
    private static final int gi = 1;
    private static final int hi = 2;
    private static final int ii = 3;
    private static final int ji = 5;

    public static class ParcelException extends RuntimeException {
        public ParcelException(Throwable th) {
            super(th);
        }
    }

    protected static <T extends i> T a(String str, VersionedParcel versionedParcel) {
        try {
            return Class.forName(str, true, VersionedParcel.class.getClassLoader()).getDeclaredMethod("read", VersionedParcel.class).invoke(null, versionedParcel);
        } catch (IllegalAccessException e2) {
            throw new RuntimeException("VersionedParcel encountered IllegalAccessException", e2);
        } catch (InvocationTargetException e3) {
            if (e3.getCause() instanceof RuntimeException) {
                throw ((RuntimeException) e3.getCause());
            }
            throw new RuntimeException("VersionedParcel encountered InvocationTargetException", e3);
        } catch (NoSuchMethodException e4) {
            throw new RuntimeException("VersionedParcel encountered NoSuchMethodException", e4);
        } catch (ClassNotFoundException e5) {
            throw new RuntimeException("VersionedParcel encountered ClassNotFoundException", e5);
        }
    }

    protected static <T extends i> void a(T t, VersionedParcel versionedParcel) {
        try {
            c(t).getDeclaredMethod("write", t.getClass(), VersionedParcel.class).invoke(null, t, versionedParcel);
        } catch (IllegalAccessException e2) {
            throw new RuntimeException("VersionedParcel encountered IllegalAccessException", e2);
        } catch (InvocationTargetException e3) {
            if (e3.getCause() instanceof RuntimeException) {
                throw ((RuntimeException) e3.getCause());
            }
            throw new RuntimeException("VersionedParcel encountered InvocationTargetException", e3);
        } catch (NoSuchMethodException e4) {
            throw new RuntimeException("VersionedParcel encountered NoSuchMethodException", e4);
        } catch (ClassNotFoundException e5) {
            throw new RuntimeException("VersionedParcel encountered ClassNotFoundException", e5);
        }
    }

    private Exception b(int i, String str) {
        switch (i) {
            case fi /*{ENCODED_INT: -9}*/:
                return (Exception) kb();
            case Constants.ShotType.SIMPLE_PREVIEW_SHOT /*{ENCODED_INT: -8}*/:
            default:
                return new RuntimeException("Unknown exception code: " + i + " msg " + str);
            case -7:
                return new UnsupportedOperationException(str);
            case -6:
                return new NetworkOnMainThreadException();
            case -5:
                return new IllegalStateException(str);
            case -4:
                return new NullPointerException(str);
            case -3:
                return new IllegalArgumentException(str);
            case -2:
                return new BadParcelableException(str);
            case -1:
                return new SecurityException(str);
        }
    }

    private static <T extends i> Class c(T t) throws ClassNotFoundException {
        return l(t.getClass());
    }

    private void d(i iVar) {
        try {
            writeString(l(iVar.getClass()).getName());
        } catch (ClassNotFoundException e2) {
            throw new RuntimeException(iVar.getClass().getSimpleName() + " does not have a Parcelizer", e2);
        }
    }

    @NonNull
    protected static Throwable getRootCause(@NonNull Throwable th) {
        while (th.getCause() != null) {
            th = th.getCause();
        }
        return th;
    }

    private static Class l(Class<? extends i> cls) throws ClassNotFoundException {
        return Class.forName(String.format("%s.%sParcelizer", cls.getPackage().getName(), cls.getSimpleName()), false, cls.getClassLoader());
    }

    private Exception readException(int i, String str) {
        return b(i, str);
    }

    private int readExceptionCode() {
        return readInt();
    }

    private <T> int t(T t) {
        if (t instanceof String) {
            return 4;
        }
        if (t instanceof Parcelable) {
            return 2;
        }
        if (t instanceof i) {
            return 1;
        }
        if (t instanceof Serializable) {
            return 3;
        }
        if (t instanceof IBinder) {
            return 5;
        }
        throw new IllegalArgumentException(t.getClass().getName() + " cannot be VersionedParcelled");
    }

    private void writeSerializable(Serializable serializable) {
        if (serializable == null) {
            writeString(null);
            return;
        }
        String name = serializable.getClass().getName();
        writeString(name);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(serializable);
            objectOutputStream.close();
            writeByteArray(byteArrayOutputStream.toByteArray());
        } catch (IOException e2) {
            throw new RuntimeException("VersionedParcelable encountered IOException writing serializable object (name = " + name + ")", e2);
        }
    }

    public byte a(byte b2, int i) {
        return !m(i) ? b2 : (byte) (readInt() & 255);
    }

    public double a(double d2, int i) {
        return !m(i) ? d2 : readDouble();
    }

    public float a(float f2, int i) {
        return !m(i) ? f2 : readFloat();
    }

    public long a(long j, int i) {
        return !m(i) ? j : readLong();
    }

    public Bundle a(Bundle bundle, int i) {
        return !m(i) ? bundle : readBundle();
    }

    public IBinder a(IBinder iBinder, int i) {
        return !m(i) ? iBinder : readStrongBinder();
    }

    public <T extends Parcelable> T a(T t, int i) {
        return !m(i) ? t : kb();
    }

    @RequiresApi(api = 21)
    public Size a(Size size, int i) {
        if (!m(i)) {
            return size;
        }
        if (readBoolean()) {
            return new Size(readInt(), readInt());
        }
        return null;
    }

    @RequiresApi(api = 21)
    public SizeF a(SizeF sizeF, int i) {
        if (!m(i)) {
            return sizeF;
        }
        if (readBoolean()) {
            return new SizeF(readFloat(), readFloat());
        }
        return null;
    }

    public SparseBooleanArray a(SparseBooleanArray sparseBooleanArray, int i) {
        if (!m(i)) {
            return sparseBooleanArray;
        }
        int readInt = readInt();
        if (readInt < 0) {
            return null;
        }
        SparseBooleanArray sparseBooleanArray2 = new SparseBooleanArray(readInt);
        for (int i2 = 0; i2 < readInt; i2++) {
            sparseBooleanArray2.put(readInt(), readBoolean());
        }
        return sparseBooleanArray2;
    }

    public <T extends i> T a(T t, int i) {
        return !m(i) ? t : lb();
    }

    public Exception a(Exception exc, int i) {
        int readExceptionCode;
        return (m(i) && (readExceptionCode = readExceptionCode()) != 0) ? readException(readExceptionCode, readString()) : exc;
    }

    public <T> List<T> a(List<T> list, int i) {
        if (!m(i)) {
            return list;
        }
        int readInt = readInt();
        if (readInt < 0) {
            return null;
        }
        ArrayList arrayList = new ArrayList(readInt);
        if (readInt != 0) {
            int readInt2 = readInt();
            if (readInt < 0) {
                return null;
            }
            if (readInt2 == 1) {
                while (readInt > 0) {
                    arrayList.add(lb());
                    readInt--;
                }
            } else if (readInt2 == 2) {
                while (readInt > 0) {
                    arrayList.add(kb());
                    readInt--;
                }
            } else if (readInt2 == 3) {
                while (readInt > 0) {
                    arrayList.add(readSerializable());
                    readInt--;
                }
            } else if (readInt2 == 4) {
                while (readInt > 0) {
                    arrayList.add(readString());
                    readInt--;
                }
            } else if (readInt2 == 5) {
                while (readInt > 0) {
                    arrayList.add(readStrongBinder());
                    readInt--;
                }
            }
        }
        return arrayList;
    }

    public void a(IInterface iInterface, int i) {
        n(i);
        writeStrongInterface(iInterface);
    }

    public void a(Serializable serializable, int i) {
        n(i);
        writeSerializable(serializable);
    }

    public void a(byte[] bArr, int i, int i2, int i3) {
        n(i3);
        writeByteArray(bArr, i, i2);
    }

    public boolean a(boolean z, int i) {
        return !m(i) ? z : readBoolean();
    }

    public char[] a(char[] cArr, int i) {
        if (!m(i)) {
            return cArr;
        }
        int readInt = readInt();
        if (readInt < 0) {
            return null;
        }
        char[] cArr2 = new char[readInt];
        for (int i2 = 0; i2 < readInt; i2++) {
            cArr2[i2] = (char) readInt();
        }
        return cArr2;
    }

    public double[] a(double[] dArr, int i) {
        return !m(i) ? dArr : gb();
    }

    public float[] a(float[] fArr, int i) {
        return !m(i) ? fArr : hb();
    }

    public int[] a(int[] iArr, int i) {
        return !m(i) ? iArr : ib();
    }

    public long[] a(long[] jArr, int i) {
        return !m(i) ? jArr : jb();
    }

    /* access modifiers changed from: protected */
    public <T> T[] a(T[] tArr) {
        int readInt = readInt();
        if (readInt < 0) {
            return null;
        }
        ArrayList arrayList = new ArrayList(readInt);
        if (readInt != 0) {
            int readInt2 = readInt();
            if (readInt < 0) {
                return null;
            }
            if (readInt2 == 1) {
                while (readInt > 0) {
                    arrayList.add(lb());
                    readInt--;
                }
            } else if (readInt2 == 2) {
                while (readInt > 0) {
                    arrayList.add(kb());
                    readInt--;
                }
            } else if (readInt2 == 3) {
                while (readInt > 0) {
                    arrayList.add(readSerializable());
                    readInt--;
                }
            } else if (readInt2 == 4) {
                while (readInt > 0) {
                    arrayList.add(readString());
                    readInt--;
                }
            } else if (readInt2 == 5) {
                while (readInt > 0) {
                    arrayList.add(readStrongBinder());
                    readInt--;
                }
            }
        }
        return arrayList.toArray(tArr);
    }

    public <T> T[] a(T[] tArr, int i) {
        return !m(i) ? tArr : a(tArr);
    }

    public boolean[] a(boolean[] zArr, int i) {
        return !m(i) ? zArr : fb();
    }

    public void b(byte b2, int i) {
        n(i);
        writeInt(b2);
    }

    public void b(double d2, int i) {
        n(i);
        writeDouble(d2);
    }

    public void b(float f2, int i) {
        n(i);
        writeFloat(f2);
    }

    public void b(int i, int i2) {
        n(i2);
        writeInt(i);
    }

    public void b(long j, int i) {
        n(i);
        writeLong(j);
    }

    public void b(Bundle bundle, int i) {
        n(i);
        writeBundle(bundle);
    }

    public void b(IBinder iBinder, int i) {
        n(i);
        writeStrongBinder(iBinder);
    }

    /* access modifiers changed from: protected */
    public abstract void b(Parcelable parcelable);

    @RequiresApi(api = 21)
    public void b(Size size, int i) {
        n(i);
        writeBoolean(size != null);
        if (size != null) {
            writeInt(size.getWidth());
            writeInt(size.getHeight());
        }
    }

    @RequiresApi(api = 21)
    public void b(SizeF sizeF, int i) {
        n(i);
        writeBoolean(sizeF != null);
        if (sizeF != null) {
            writeFloat(sizeF.getWidth());
            writeFloat(sizeF.getHeight());
        }
    }

    public void b(SparseBooleanArray sparseBooleanArray, int i) {
        n(i);
        if (sparseBooleanArray == null) {
            writeInt(-1);
            return;
        }
        int size = sparseBooleanArray.size();
        writeInt(size);
        for (int i2 = 0; i2 < size; i2++) {
            writeInt(sparseBooleanArray.keyAt(i2));
            writeBoolean(sparseBooleanArray.valueAt(i2));
        }
    }

    /* access modifiers changed from: protected */
    public void b(i iVar) {
        if (iVar == null) {
            writeString(null);
            return;
        }
        d(iVar);
        VersionedParcel db = db();
        a(iVar, db);
        db.bb();
    }

    public void b(i iVar, int i) {
        n(i);
        b(iVar);
    }

    public void b(Exception exc, int i) {
        n(i);
        if (exc == null) {
            writeNoException();
            return;
        }
        int i2 = 0;
        if ((exc instanceof Parcelable) && exc.getClass().getClassLoader() == Parcelable.class.getClassLoader()) {
            i2 = fi;
        } else if (exc instanceof SecurityException) {
            i2 = -1;
        } else if (exc instanceof BadParcelableException) {
            i2 = -2;
        } else if (exc instanceof IllegalArgumentException) {
            i2 = -3;
        } else if (exc instanceof NullPointerException) {
            i2 = -4;
        } else if (exc instanceof IllegalStateException) {
            i2 = -5;
        } else if (exc instanceof NetworkOnMainThreadException) {
            i2 = -6;
        } else if (exc instanceof UnsupportedOperationException) {
            i2 = -7;
        }
        writeInt(i2);
        if (i2 != 0) {
            writeString(exc.getMessage());
            if (i2 == fi) {
                b((Parcelable) exc);
            }
        } else if (exc instanceof RuntimeException) {
            throw ((RuntimeException) exc);
        } else {
            throw new RuntimeException(exc);
        }
    }

    public <T> void b(List<T> list, int i) {
        n(i);
        if (list == null) {
            writeInt(-1);
            return;
        }
        int size = list.size();
        writeInt(size);
        if (size > 0) {
            int i2 = 0;
            int t = t(list.get(0));
            writeInt(t);
            if (t == 1) {
                while (i2 < size) {
                    b((i) list.get(i2));
                    i2++;
                }
            } else if (t == 2) {
                while (i2 < size) {
                    b((Parcelable) list.get(i2));
                    i2++;
                }
            } else if (t == 3) {
                while (i2 < size) {
                    writeSerializable(list.get(i2));
                    i2++;
                }
            } else if (t == 4) {
                while (i2 < size) {
                    writeString(list.get(i2));
                    i2++;
                }
            } else if (t == 5) {
                while (i2 < size) {
                    writeStrongBinder(list.get(i2));
                    i2++;
                }
            }
        }
    }

    public void b(boolean z, int i) {
        n(i);
        writeBoolean(z);
    }

    public void b(boolean z, boolean z2) {
    }

    public void b(char[] cArr, int i) {
        n(i);
        if (cArr != null) {
            int length = cArr.length;
            writeInt(length);
            for (char c2 : cArr) {
                writeInt(c2);
            }
            return;
        }
        writeInt(-1);
    }

    public void b(double[] dArr, int i) {
        n(i);
        writeDoubleArray(dArr);
    }

    public void b(float[] fArr, int i) {
        n(i);
        writeFloatArray(fArr);
    }

    public void b(int[] iArr, int i) {
        n(i);
        writeIntArray(iArr);
    }

    public void b(long[] jArr, int i) {
        n(i);
        writeLongArray(jArr);
    }

    public <T> void b(T[] tArr, int i) {
        n(i);
        writeArray(tArr);
    }

    public void b(boolean[] zArr, int i) {
        n(i);
        writeBooleanArray(zArr);
    }

    public byte[] b(byte[] bArr, int i) {
        return !m(i) ? bArr : readByteArray();
    }

    /* access modifiers changed from: protected */
    public abstract void bb();

    public String c(String str, int i) {
        return !m(i) ? str : readString();
    }

    public void c(byte[] bArr, int i) {
        n(i);
        writeByteArray(bArr);
    }

    public void d(String str, int i) {
        n(i);
        writeString(str);
    }

    /* access modifiers changed from: protected */
    public abstract VersionedParcel db();

    public boolean eb() {
        return false;
    }

    /* access modifiers changed from: protected */
    public boolean[] fb() {
        int readInt = readInt();
        if (readInt < 0) {
            return null;
        }
        boolean[] zArr = new boolean[readInt];
        for (int i = 0; i < readInt; i++) {
            zArr[i] = readInt() != 0;
        }
        return zArr;
    }

    /* access modifiers changed from: protected */
    public double[] gb() {
        int readInt = readInt();
        if (readInt < 0) {
            return null;
        }
        double[] dArr = new double[readInt];
        for (int i = 0; i < readInt; i++) {
            dArr[i] = readDouble();
        }
        return dArr;
    }

    /* access modifiers changed from: protected */
    public float[] hb() {
        int readInt = readInt();
        if (readInt < 0) {
            return null;
        }
        float[] fArr = new float[readInt];
        for (int i = 0; i < readInt; i++) {
            fArr[i] = readFloat();
        }
        return fArr;
    }

    /* access modifiers changed from: protected */
    public int[] ib() {
        int readInt = readInt();
        if (readInt < 0) {
            return null;
        }
        int[] iArr = new int[readInt];
        for (int i = 0; i < readInt; i++) {
            iArr[i] = readInt();
        }
        return iArr;
    }

    /* access modifiers changed from: protected */
    public long[] jb() {
        int readInt = readInt();
        if (readInt < 0) {
            return null;
        }
        long[] jArr = new long[readInt];
        for (int i = 0; i < readInt; i++) {
            jArr[i] = readLong();
        }
        return jArr;
    }

    /* access modifiers changed from: protected */
    public abstract <T extends Parcelable> T kb();

    /* access modifiers changed from: protected */
    public <T extends i> T lb() {
        String readString = readString();
        if (readString == null) {
            return null;
        }
        return a(readString, db());
    }

    /* access modifiers changed from: protected */
    public abstract boolean m(int i);

    /* access modifiers changed from: protected */
    public abstract void n(int i);

    /* access modifiers changed from: protected */
    public abstract boolean readBoolean();

    /* access modifiers changed from: protected */
    public abstract Bundle readBundle();

    /* access modifiers changed from: protected */
    public abstract byte[] readByteArray();

    /* access modifiers changed from: protected */
    public abstract double readDouble();

    /* access modifiers changed from: protected */
    public abstract float readFloat();

    /* access modifiers changed from: protected */
    public abstract int readInt();

    public int readInt(int i, int i2) {
        return !m(i2) ? i : readInt();
    }

    /* access modifiers changed from: protected */
    public abstract long readLong();

    /* access modifiers changed from: protected */
    public Serializable readSerializable() {
        String readString = readString();
        if (readString == null) {
            return null;
        }
        try {
            return (Serializable) new f(this, new ByteArrayInputStream(readByteArray())).readObject();
        } catch (IOException e2) {
            throw new RuntimeException("VersionedParcelable encountered IOException reading a Serializable object (name = " + readString + ")", e2);
        } catch (ClassNotFoundException e3) {
            throw new RuntimeException("VersionedParcelable encountered ClassNotFoundException reading a Serializable object (name = " + readString + ")", e3);
        }
    }

    /* access modifiers changed from: protected */
    public abstract String readString();

    /* access modifiers changed from: protected */
    public abstract IBinder readStrongBinder();

    /* access modifiers changed from: protected */
    public <T> void writeArray(T[] tArr) {
        if (tArr == null) {
            writeInt(-1);
            return;
        }
        int length = tArr.length;
        writeInt(length);
        if (length > 0) {
            int i = 0;
            int t = t(tArr[0]);
            writeInt(t);
            if (t == 1) {
                while (i < length) {
                    b((i) tArr[i]);
                    i++;
                }
            } else if (t == 2) {
                while (i < length) {
                    b((Parcelable) tArr[i]);
                    i++;
                }
            } else if (t == 3) {
                while (i < length) {
                    writeSerializable(tArr[i]);
                    i++;
                }
            } else if (t == 4) {
                while (i < length) {
                    writeString(tArr[i]);
                    i++;
                }
            } else if (t == 5) {
                while (i < length) {
                    writeStrongBinder(tArr[i]);
                    i++;
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public abstract void writeBoolean(boolean z);

    /* access modifiers changed from: protected */
    public void writeBooleanArray(boolean[] zArr) {
        if (zArr != null) {
            int length = zArr.length;
            writeInt(length);
            for (boolean z : zArr) {
                writeInt(z ? 1 : 0);
            }
            return;
        }
        writeInt(-1);
    }

    /* access modifiers changed from: protected */
    public abstract void writeBundle(Bundle bundle);

    /* access modifiers changed from: protected */
    public abstract void writeByteArray(byte[] bArr);

    /* access modifiers changed from: protected */
    public abstract void writeByteArray(byte[] bArr, int i, int i2);

    /* access modifiers changed from: protected */
    public abstract void writeDouble(double d2);

    /* access modifiers changed from: protected */
    public void writeDoubleArray(double[] dArr) {
        if (dArr != null) {
            int length = dArr.length;
            writeInt(length);
            for (double d2 : dArr) {
                writeDouble(d2);
            }
            return;
        }
        writeInt(-1);
    }

    /* access modifiers changed from: protected */
    public abstract void writeFloat(float f2);

    /* access modifiers changed from: protected */
    public void writeFloatArray(float[] fArr) {
        if (fArr != null) {
            int length = fArr.length;
            writeInt(length);
            for (float f2 : fArr) {
                writeFloat(f2);
            }
            return;
        }
        writeInt(-1);
    }

    /* access modifiers changed from: protected */
    public abstract void writeInt(int i);

    /* access modifiers changed from: protected */
    public void writeIntArray(int[] iArr) {
        if (iArr != null) {
            int length = iArr.length;
            writeInt(length);
            for (int i : iArr) {
                writeInt(i);
            }
            return;
        }
        writeInt(-1);
    }

    /* access modifiers changed from: protected */
    public abstract void writeLong(long j);

    /* access modifiers changed from: protected */
    public void writeLongArray(long[] jArr) {
        if (jArr != null) {
            int length = jArr.length;
            writeInt(length);
            for (long j : jArr) {
                writeLong(j);
            }
            return;
        }
        writeInt(-1);
    }

    /* access modifiers changed from: protected */
    public void writeNoException() {
        writeInt(0);
    }

    public void writeParcelable(Parcelable parcelable, int i) {
        n(i);
        b(parcelable);
    }

    /* access modifiers changed from: protected */
    public abstract void writeString(String str);

    /* access modifiers changed from: protected */
    public abstract void writeStrongBinder(IBinder iBinder);

    /* access modifiers changed from: protected */
    public abstract void writeStrongInterface(IInterface iInterface);
}
