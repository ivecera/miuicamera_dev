package android.support.v4.util;

public class SparseArrayCompat<E> implements Cloneable {
    private static final Object DELETED = new Object();
    private boolean mGarbage;
    private int[] mKeys;
    private int mSize;
    private Object[] mValues;

    public SparseArrayCompat() {
        this(10);
    }

    public SparseArrayCompat(int i) {
        this.mGarbage = false;
        if (i == 0) {
            this.mKeys = ContainerHelpers.EMPTY_INTS;
            this.mValues = ContainerHelpers.EMPTY_OBJECTS;
        } else {
            int idealIntArraySize = ContainerHelpers.idealIntArraySize(i);
            this.mKeys = new int[idealIntArraySize];
            this.mValues = new Object[idealIntArraySize];
        }
        this.mSize = 0;
    }

    private void gc() {
        int i = this.mSize;
        int[] iArr = this.mKeys;
        Object[] objArr = this.mValues;
        int i2 = 0;
        for (int i3 = 0; i3 < i; i3++) {
            Object obj = objArr[i3];
            if (obj != DELETED) {
                if (i3 != i2) {
                    iArr[i2] = iArr[i3];
                    objArr[i2] = obj;
                    objArr[i3] = null;
                }
                i2++;
            }
        }
        this.mGarbage = false;
        this.mSize = i2;
    }

    public void append(int i, E e2) {
        int i2 = this.mSize;
        if (i2 == 0 || i > this.mKeys[i2 - 1]) {
            if (this.mGarbage && this.mSize >= this.mKeys.length) {
                gc();
            }
            int i3 = this.mSize;
            if (i3 >= this.mKeys.length) {
                int idealIntArraySize = ContainerHelpers.idealIntArraySize(i3 + 1);
                int[] iArr = new int[idealIntArraySize];
                Object[] objArr = new Object[idealIntArraySize];
                int[] iArr2 = this.mKeys;
                System.arraycopy(iArr2, 0, iArr, 0, iArr2.length);
                Object[] objArr2 = this.mValues;
                System.arraycopy(objArr2, 0, objArr, 0, objArr2.length);
                this.mKeys = iArr;
                this.mValues = objArr;
            }
            this.mKeys[i3] = i;
            this.mValues[i3] = e2;
            this.mSize = i3 + 1;
            return;
        }
        put(i, e2);
    }

    public void clear() {
        int i = this.mSize;
        Object[] objArr = this.mValues;
        for (int i2 = 0; i2 < i; i2++) {
            objArr[i2] = null;
        }
        this.mSize = 0;
        this.mGarbage = false;
    }

    @Override // java.lang.Object
    public SparseArrayCompat<E> clone() {
        try {
            SparseArrayCompat<E> sparseArrayCompat = (SparseArrayCompat) super.clone();
            try {
                sparseArrayCompat.mKeys = (int[]) this.mKeys.clone();
                sparseArrayCompat.mValues = (Object[]) this.mValues.clone();
                return sparseArrayCompat;
            } catch (CloneNotSupportedException unused) {
                return sparseArrayCompat;
            }
        } catch (CloneNotSupportedException unused2) {
            return null;
        }
    }

    public void delete(int i) {
        Object[] objArr;
        Object obj;
        int binarySearch = ContainerHelpers.binarySearch(this.mKeys, this.mSize, i);
        if (binarySearch >= 0 && (objArr = this.mValues)[binarySearch] != (obj = DELETED)) {
            objArr[binarySearch] = obj;
            this.mGarbage = true;
        }
    }

    public E get(int i) {
        return get(i, null);
    }

    public E get(int i, E e2) {
        int binarySearch = ContainerHelpers.binarySearch(this.mKeys, this.mSize, i);
        if (binarySearch >= 0) {
            E[] eArr = this.mValues;
            if (eArr[binarySearch] != DELETED) {
                return eArr[binarySearch];
            }
        }
        return e2;
    }

    public int indexOfKey(int i) {
        if (this.mGarbage) {
            gc();
        }
        return ContainerHelpers.binarySearch(this.mKeys, this.mSize, i);
    }

    public int indexOfValue(E e2) {
        if (this.mGarbage) {
            gc();
        }
        for (int i = 0; i < this.mSize; i++) {
            if (this.mValues[i] == e2) {
                return i;
            }
        }
        return -1;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public int keyAt(int i) {
        if (this.mGarbage) {
            gc();
        }
        return this.mKeys[i];
    }

    public void put(int i, E e2) {
        int binarySearch = ContainerHelpers.binarySearch(this.mKeys, this.mSize, i);
        if (binarySearch >= 0) {
            this.mValues[binarySearch] = e2;
            return;
        }
        int i2 = ~binarySearch;
        if (i2 < this.mSize) {
            Object[] objArr = this.mValues;
            if (objArr[i2] == DELETED) {
                this.mKeys[i2] = i;
                objArr[i2] = e2;
                return;
            }
        }
        if (this.mGarbage && this.mSize >= this.mKeys.length) {
            gc();
            i2 = ~ContainerHelpers.binarySearch(this.mKeys, this.mSize, i);
        }
        int i3 = this.mSize;
        if (i3 >= this.mKeys.length) {
            int idealIntArraySize = ContainerHelpers.idealIntArraySize(i3 + 1);
            int[] iArr = new int[idealIntArraySize];
            Object[] objArr2 = new Object[idealIntArraySize];
            int[] iArr2 = this.mKeys;
            System.arraycopy(iArr2, 0, iArr, 0, iArr2.length);
            Object[] objArr3 = this.mValues;
            System.arraycopy(objArr3, 0, objArr2, 0, objArr3.length);
            this.mKeys = iArr;
            this.mValues = objArr2;
        }
        int i4 = this.mSize;
        if (i4 - i2 != 0) {
            int[] iArr3 = this.mKeys;
            int i5 = i2 + 1;
            System.arraycopy(iArr3, i2, iArr3, i5, i4 - i2);
            Object[] objArr4 = this.mValues;
            System.arraycopy(objArr4, i2, objArr4, i5, this.mSize - i2);
        }
        this.mKeys[i2] = i;
        this.mValues[i2] = e2;
        this.mSize++;
    }

    public void remove(int i) {
        delete(i);
    }

    public void removeAt(int i) {
        Object[] objArr = this.mValues;
        Object obj = objArr[i];
        Object obj2 = DELETED;
        if (obj != obj2) {
            objArr[i] = obj2;
            this.mGarbage = true;
        }
    }

    public void removeAtRange(int i, int i2) {
        int min = Math.min(this.mSize, i2 + i);
        while (i < min) {
            removeAt(i);
            i++;
        }
    }

    public void setValueAt(int i, E e2) {
        if (this.mGarbage) {
            gc();
        }
        this.mValues[i] = e2;
    }

    public int size() {
        if (this.mGarbage) {
            gc();
        }
        return this.mSize;
    }

    public String toString() {
        if (size() <= 0) {
            return "{}";
        }
        StringBuilder sb = new StringBuilder(this.mSize * 28);
        sb.append('{');
        for (int i = 0; i < this.mSize; i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(keyAt(i));
            sb.append('=');
            E valueAt = valueAt(i);
            if (valueAt != this) {
                sb.append((Object) valueAt);
            } else {
                sb.append("(this Map)");
            }
        }
        sb.append('}');
        return sb.toString();
    }

    public E valueAt(int i) {
        if (this.mGarbage) {
            gc();
        }
        return this.mValues[i];
    }
}
