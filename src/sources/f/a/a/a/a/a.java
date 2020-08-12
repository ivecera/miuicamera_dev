package f.a.a.a.a;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* compiled from: IFileInfo */
public final class a {
    public long Qy;
    public long fileSize;
    public String name = new String();

    public static final ArrayList<a> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<a> arrayList = new ArrayList<>();
        HwBlob readBuffer = hwParcel.readBuffer(16);
        int int32 = readBuffer.getInt32(8);
        HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer((long) (int32 * 32), readBuffer.handle(), 0, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            a aVar = new a();
            aVar.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, (long) (i * 32));
            arrayList.add(aVar);
        }
        return arrayList;
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<a> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8, size);
        hwBlob.putBool(12, false);
        HwBlob hwBlob2 = new HwBlob(size * 32);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, (long) (i * 32));
        }
        hwBlob.putBlob(0, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != a.class) {
            return false;
        }
        a aVar = (a) obj;
        return HidlSupport.deepEquals(this.name, aVar.name) && this.Qy == aVar.Qy && this.fileSize == aVar.fileSize;
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(this.name)), Integer.valueOf(HidlSupport.deepHashCode(Long.valueOf(this.Qy))), Integer.valueOf(HidlSupport.deepHashCode(Long.valueOf(this.fileSize))));
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        long j2 = j + 0;
        this.name = hwBlob.getString(j2);
        hwParcel.readEmbeddedBuffer((long) (this.name.getBytes().length + 1), hwBlob.handle(), j2 + 0, false);
        this.Qy = hwBlob.getInt64(j + 16);
        this.fileSize = hwBlob.getInt64(j + 24);
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(32), 0);
    }

    public final String toString() {
        return "{" + ".name = " + this.name + ", .mtime = " + this.Qy + ", .fileSize = " + this.fileSize + "}";
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long j) {
        hwBlob.putString(0 + j, this.name);
        hwBlob.putInt64(16 + j, this.Qy);
        hwBlob.putInt64(j + 24, this.fileSize);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(32);
        writeEmbeddedToBlob(hwBlob, 0);
        hwParcel.writeBuffer(hwBlob);
    }
}
