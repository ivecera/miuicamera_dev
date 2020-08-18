package f.a.a.a.a;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* compiled from: IReadResult */
public final class d {
    public String data = new String();
    public int value;

    public static final ArrayList<d> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<d> arrayList = new ArrayList<>();
        HwBlob readBuffer = hwParcel.readBuffer(16);
        int int32 = readBuffer.getInt32(8);
        HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer((long) (int32 * 24), readBuffer.handle(), 0, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            d dVar = new d();
            dVar.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, (long) (i * 24));
            arrayList.add(dVar);
        }
        return arrayList;
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<d> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8, size);
        hwBlob.putBool(12, false);
        HwBlob hwBlob2 = new HwBlob(size * 24);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, (long) (i * 24));
        }
        hwBlob.putBlob(0, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != d.class) {
            return false;
        }
        d dVar = (d) obj;
        return this.value == dVar.value && HidlSupport.deepEquals(this.data, dVar.data);
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.value))), Integer.valueOf(HidlSupport.deepHashCode(this.data)));
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.value = hwBlob.getInt32(j + 0);
        long j2 = j + 8;
        this.data = hwBlob.getString(j2);
        hwParcel.readEmbeddedBuffer((long) (this.data.getBytes().length + 1), hwBlob.handle(), j2 + 0, false);
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(24), 0);
    }

    public final String toString() {
        return "{" + ".value = " + e.toString(this.value) + ", .data = " + this.data + "}";
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long j) {
        hwBlob.putInt32(0 + j, this.value);
        hwBlob.putString(j + 8, this.data);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(24);
        writeEmbeddedToBlob(hwBlob, 0);
        hwParcel.writeBuffer(hwBlob);
    }
}
