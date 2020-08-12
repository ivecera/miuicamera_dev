package f.a.a.a.a;

import android.os.HidlSupport;
import android.os.HwBinder;
import android.os.HwBlob;
import android.os.HwParcel;
import android.os.IHwBinder;
import android.os.IHwInterface;
import android.os.NativeHandle;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;

/* compiled from: IMiSys */
public interface c extends a.b.a.a.b {
    public static final String kInterfaceName = "vendor.xiaomi.hardware.misys@1.0::IMiSys";

    /* compiled from: IMiSys */
    public static final class a implements c {
        private IHwBinder mRemote;

        public a(IHwBinder iHwBinder) {
            this.mRemote = (IHwBinder) Objects.requireNonNull(iHwBinder);
        }

        @Override // f.a.a.a.a.c
        public int a(String str, String str2, String str3, int i, byte b2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(c.kInterfaceName);
            hwParcel.writeString(str);
            hwParcel.writeString(str2);
            hwParcel.writeString(str3);
            hwParcel.writeInt32(i);
            hwParcel.writeInt8(b2);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(2, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return hwParcel2.readInt32();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // f.a.a.a.a.c, a.b.a.a.b
        public IHwBinder asBinder() {
            return this.mRemote;
        }

        @Override // f.a.a.a.a.c, a.b.a.a.b
        public void debug(NativeHandle nativeHandle, ArrayList<String> arrayList) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(a.b.a.a.b.kInterfaceName);
            hwParcel.writeNativeHandle(nativeHandle);
            hwParcel.writeStringVector(arrayList);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(256131655, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        public final boolean equals(Object obj) {
            return HidlSupport.interfacesEqual(this, obj);
        }

        @Override // f.a.a.a.a.c
        public d f(String str, String str2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(c.kInterfaceName);
            hwParcel.writeString(str);
            hwParcel.writeString(str2);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(3, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                d dVar = new d();
                dVar.readFromParcel(hwParcel2);
                return dVar;
            } finally {
                hwParcel2.release();
            }
        }

        @Override // f.a.a.a.a.c, a.b.a.a.b
        public a.b.a.a.a getDebugInfo() throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(a.b.a.a.b.kInterfaceName);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(257049926, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                a.b.a.a.a aVar = new a.b.a.a.a();
                aVar.readFromParcel(hwParcel2);
                return aVar;
            } finally {
                hwParcel2.release();
            }
        }

        @Override // f.a.a.a.a.c, a.b.a.a.b
        public ArrayList<byte[]> getHashChain() throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(a.b.a.a.b.kInterfaceName);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(256398152, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                ArrayList<byte[]> arrayList = new ArrayList<>();
                HwBlob readBuffer = hwParcel2.readBuffer(16);
                int int32 = readBuffer.getInt32(8);
                HwBlob readEmbeddedBuffer = hwParcel2.readEmbeddedBuffer((long) (int32 * 32), readBuffer.handle(), 0, true);
                arrayList.clear();
                for (int i = 0; i < int32; i++) {
                    byte[] bArr = new byte[32];
                    readEmbeddedBuffer.copyToInt8Array((long) (i * 32), bArr, 32);
                    arrayList.add(bArr);
                }
                return arrayList;
            } finally {
                hwParcel2.release();
            }
        }

        public final int hashCode() {
            return asBinder().hashCode();
        }

        @Override // f.a.a.a.a.c, a.b.a.a.b
        public ArrayList<String> interfaceChain() throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(a.b.a.a.b.kInterfaceName);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(256067662, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return hwParcel2.readStringVector();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // f.a.a.a.a.c, a.b.a.a.b
        public String interfaceDescriptor() throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(a.b.a.a.b.kInterfaceName);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(256136003, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return hwParcel2.readString();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // f.a.a.a.a.c
        public b j(String str) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(c.kInterfaceName);
            hwParcel.writeString(str);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(1, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                b bVar = new b();
                bVar.readFromParcel(hwParcel2);
                return bVar;
            } finally {
                hwParcel2.release();
            }
        }

        @Override // f.a.a.a.a.c
        public int k(String str, String str2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(c.kInterfaceName);
            hwParcel.writeString(str);
            hwParcel.writeString(str2);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(4, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return hwParcel2.readInt32();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // f.a.a.a.a.c, a.b.a.a.b
        public boolean linkToDeath(IHwBinder.DeathRecipient deathRecipient, long j) throws RemoteException {
            return this.mRemote.linkToDeath(deathRecipient, j);
        }

        @Override // f.a.a.a.a.c, a.b.a.a.b
        public void notifySyspropsChanged() throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(a.b.a.a.b.kInterfaceName);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(257120595, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // f.a.a.a.a.c, a.b.a.a.b
        public void ping() throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(a.b.a.a.b.kInterfaceName);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(256921159, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // f.a.a.a.a.c, a.b.a.a.b
        public void setHALInstrumentation() throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(a.b.a.a.b.kInterfaceName);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(256462420, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        public String toString() {
            try {
                return interfaceDescriptor() + "@Proxy";
            } catch (RemoteException unused) {
                return "[class or subclass of vendor.xiaomi.hardware.misys@1.0::IMiSys]@Proxy";
            }
        }

        @Override // f.a.a.a.a.c, a.b.a.a.b
        public boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) throws RemoteException {
            return this.mRemote.unlinkToDeath(deathRecipient);
        }
    }

    /* compiled from: IMiSys */
    public static abstract class b extends HwBinder implements c {
        @Override // f.a.a.a.a.c, a.b.a.a.b
        public IHwBinder asBinder() {
            return this;
        }

        @Override // f.a.a.a.a.c, a.b.a.a.b
        public void debug(NativeHandle nativeHandle, ArrayList<String> arrayList) {
        }

        @Override // f.a.a.a.a.c, a.b.a.a.b
        public final a.b.a.a.a getDebugInfo() {
            a.b.a.a.a aVar = new a.b.a.a.a();
            aVar.pid = HidlSupport.getPidIfSharable();
            aVar.ptr = 0;
            aVar.arch = 0;
            return aVar;
        }

        @Override // f.a.a.a.a.c, a.b.a.a.b
        public final ArrayList<byte[]> getHashChain() {
            return new ArrayList<>(Arrays.asList(new byte[]{-15, -58, 51, -24, -22, -111, -37, -51, -90, -31, 106, 108, 63, -2, -53, -94, 35, 106, 80, -61, 8, 71, -10, 15, 72, 84, -117, 116, 34, -21, -75, -91}, new byte[]{-20, Byte.MAX_VALUE, -41, -98, -48, 45, -6, -123, -68, 73, -108, 38, -83, -82, 62, -66, 35, -17, 5, 36, -13, -51, 105, 87, 19, -109, 36, -72, 59, 24, -54, 76}));
        }

        @Override // f.a.a.a.a.c, a.b.a.a.b
        public final ArrayList<String> interfaceChain() {
            return new ArrayList<>(Arrays.asList(c.kInterfaceName, a.b.a.a.b.kInterfaceName));
        }

        @Override // f.a.a.a.a.c, a.b.a.a.b
        public final String interfaceDescriptor() {
            return c.kInterfaceName;
        }

        @Override // f.a.a.a.a.c, a.b.a.a.b
        public final boolean linkToDeath(IHwBinder.DeathRecipient deathRecipient, long j) {
            return true;
        }

        @Override // f.a.a.a.a.c, a.b.a.a.b
        public final void notifySyspropsChanged() {
            HwBinder.enableInstrumentation();
        }

        public void onTransact(int i, HwParcel hwParcel, HwParcel hwParcel2, int i2) throws RemoteException {
            int i3 = 0;
            boolean z = true;
            if (i == 1) {
                if ((i2 & 1) == 0) {
                    z = false;
                }
                if (z) {
                    hwParcel2.writeStatus(Integer.MIN_VALUE);
                    hwParcel2.send();
                    return;
                }
                hwParcel.enforceInterface(c.kInterfaceName);
                b j = j(hwParcel.readString());
                hwParcel2.writeStatus(0);
                j.writeToParcel(hwParcel2);
                hwParcel2.send();
            } else if (i == 2) {
                if ((i2 & 1) == 0) {
                    z = false;
                }
                if (z) {
                    hwParcel2.writeStatus(Integer.MIN_VALUE);
                    hwParcel2.send();
                    return;
                }
                hwParcel.enforceInterface(c.kInterfaceName);
                int a2 = a(hwParcel.readString(), hwParcel.readString(), hwParcel.readString(), hwParcel.readInt32(), hwParcel.readInt8());
                hwParcel2.writeStatus(0);
                hwParcel2.writeInt32(a2);
                hwParcel2.send();
            } else if (i == 3) {
                if ((i2 & 1) == 0) {
                    z = false;
                }
                if (z) {
                    hwParcel2.writeStatus(Integer.MIN_VALUE);
                    hwParcel2.send();
                    return;
                }
                hwParcel.enforceInterface(c.kInterfaceName);
                d f2 = f(hwParcel.readString(), hwParcel.readString());
                hwParcel2.writeStatus(0);
                f2.writeToParcel(hwParcel2);
                hwParcel2.send();
            } else if (i != 4) {
                switch (i) {
                    case 256067662:
                        if ((i2 & 1) == 0) {
                            z = false;
                        }
                        if (z) {
                            hwParcel2.writeStatus(Integer.MIN_VALUE);
                            hwParcel2.send();
                            return;
                        }
                        hwParcel.enforceInterface(a.b.a.a.b.kInterfaceName);
                        ArrayList<String> interfaceChain = interfaceChain();
                        hwParcel2.writeStatus(0);
                        hwParcel2.writeStringVector(interfaceChain);
                        hwParcel2.send();
                        return;
                    case 256131655:
                        if ((i2 & 1) == 0) {
                            z = false;
                        }
                        if (z) {
                            hwParcel2.writeStatus(Integer.MIN_VALUE);
                            hwParcel2.send();
                            return;
                        }
                        hwParcel.enforceInterface(a.b.a.a.b.kInterfaceName);
                        debug(hwParcel.readNativeHandle(), hwParcel.readStringVector());
                        hwParcel2.writeStatus(0);
                        hwParcel2.send();
                        return;
                    case 256136003:
                        if ((i2 & 1) == 0) {
                            z = false;
                        }
                        if (z) {
                            hwParcel2.writeStatus(Integer.MIN_VALUE);
                            hwParcel2.send();
                            return;
                        }
                        hwParcel.enforceInterface(a.b.a.a.b.kInterfaceName);
                        String interfaceDescriptor = interfaceDescriptor();
                        hwParcel2.writeStatus(0);
                        hwParcel2.writeString(interfaceDescriptor);
                        hwParcel2.send();
                        return;
                    case 256398152:
                        if ((i2 & 1) == 0) {
                            z = false;
                        }
                        if (z) {
                            hwParcel2.writeStatus(Integer.MIN_VALUE);
                            hwParcel2.send();
                            return;
                        }
                        hwParcel.enforceInterface(a.b.a.a.b.kInterfaceName);
                        ArrayList<byte[]> hashChain = getHashChain();
                        hwParcel2.writeStatus(0);
                        HwBlob hwBlob = new HwBlob(16);
                        int size = hashChain.size();
                        hwBlob.putInt32(8, size);
                        hwBlob.putBool(12, false);
                        HwBlob hwBlob2 = new HwBlob(size * 32);
                        while (i3 < size) {
                            long j2 = (long) (i3 * 32);
                            byte[] bArr = hashChain.get(i3);
                            if (bArr == null || bArr.length != 32) {
                                throw new IllegalArgumentException("Array element is not of the expected length");
                            }
                            hwBlob2.putInt8Array(j2, bArr);
                            i3++;
                        }
                        hwBlob.putBlob(0, hwBlob2);
                        hwParcel2.writeBuffer(hwBlob);
                        hwParcel2.send();
                        return;
                    case 256462420:
                        if ((i2 & 1) != 0) {
                            i3 = 1;
                        }
                        if (i3 != 1) {
                            hwParcel2.writeStatus(Integer.MIN_VALUE);
                            hwParcel2.send();
                            return;
                        }
                        hwParcel.enforceInterface(a.b.a.a.b.kInterfaceName);
                        setHALInstrumentation();
                        return;
                    case 256660548:
                        if ((i2 & 1) != 0) {
                            i3 = 1;
                        }
                        if (i3 != 0) {
                            hwParcel2.writeStatus(Integer.MIN_VALUE);
                            hwParcel2.send();
                            return;
                        }
                        return;
                    case 256921159:
                        if ((i2 & 1) == 0) {
                            z = false;
                        }
                        if (z) {
                            hwParcel2.writeStatus(Integer.MIN_VALUE);
                            hwParcel2.send();
                            return;
                        }
                        hwParcel.enforceInterface(a.b.a.a.b.kInterfaceName);
                        ping();
                        hwParcel2.writeStatus(0);
                        hwParcel2.send();
                        return;
                    case 257049926:
                        if ((i2 & 1) == 0) {
                            z = false;
                        }
                        if (z) {
                            hwParcel2.writeStatus(Integer.MIN_VALUE);
                            hwParcel2.send();
                            return;
                        }
                        hwParcel.enforceInterface(a.b.a.a.b.kInterfaceName);
                        a.b.a.a.a debugInfo = getDebugInfo();
                        hwParcel2.writeStatus(0);
                        debugInfo.writeToParcel(hwParcel2);
                        hwParcel2.send();
                        return;
                    case 257120595:
                        if ((i2 & 1) != 0) {
                            i3 = 1;
                        }
                        if (i3 != 1) {
                            hwParcel2.writeStatus(Integer.MIN_VALUE);
                            hwParcel2.send();
                            return;
                        }
                        hwParcel.enforceInterface(a.b.a.a.b.kInterfaceName);
                        notifySyspropsChanged();
                        return;
                    case 257250372:
                        if ((i2 & 1) != 0) {
                            i3 = 1;
                        }
                        if (i3 != 0) {
                            hwParcel2.writeStatus(Integer.MIN_VALUE);
                            hwParcel2.send();
                            return;
                        }
                        return;
                    default:
                        return;
                }
            } else {
                if ((i2 & 1) == 0) {
                    z = false;
                }
                if (z) {
                    hwParcel2.writeStatus(Integer.MIN_VALUE);
                    hwParcel2.send();
                    return;
                }
                hwParcel.enforceInterface(c.kInterfaceName);
                int k = k(hwParcel.readString(), hwParcel.readString());
                hwParcel2.writeStatus(0);
                hwParcel2.writeInt32(k);
                hwParcel2.send();
            }
        }

        @Override // f.a.a.a.a.c, a.b.a.a.b
        public final void ping() {
        }

        public IHwInterface queryLocalInterface(String str) {
            if (c.kInterfaceName.equals(str)) {
                return this;
            }
            return null;
        }

        public void registerAsService(String str) throws RemoteException {
            registerService(str);
        }

        @Override // f.a.a.a.a.c, a.b.a.a.b
        public final void setHALInstrumentation() {
        }

        public String toString() {
            return interfaceDescriptor() + "@Stub";
        }

        @Override // f.a.a.a.a.c, a.b.a.a.b
        public final boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) {
            return true;
        }
    }

    @Override // a.b.a.a.b
    static default c asInterface(IHwBinder iHwBinder) {
        if (iHwBinder == null) {
            return null;
        }
        c queryLocalInterface = iHwBinder.queryLocalInterface(kInterfaceName);
        if (queryLocalInterface != null && (queryLocalInterface instanceof c)) {
            return queryLocalInterface;
        }
        a aVar = new a(iHwBinder);
        try {
            Iterator<String> it = aVar.interfaceChain().iterator();
            while (it.hasNext()) {
                if (it.next().equals(kInterfaceName)) {
                    return aVar;
                }
            }
        } catch (RemoteException unused) {
        }
        return null;
    }

    @Override // a.b.a.a.b
    static default c castFrom(IHwInterface iHwInterface) {
        if (iHwInterface == null) {
            return null;
        }
        return asInterface(iHwInterface.asBinder());
    }

    @Override // a.b.a.a.b
    static default c getService() throws RemoteException {
        return getService("default");
    }

    @Override // a.b.a.a.b
    static default c getService(String str) throws RemoteException {
        return asInterface(HwBinder.getService(kInterfaceName, str));
    }

    @Override // a.b.a.a.b
    static default c getService(String str, boolean z) throws RemoteException {
        return asInterface(HwBinder.getService(kInterfaceName, str, z));
    }

    @Override // a.b.a.a.b
    static default c getService(boolean z) throws RemoteException {
        return getService("default", z);
    }

    int a(String str, String str2, String str3, int i, byte b2) throws RemoteException;

    @Override // a.b.a.a.b
    IHwBinder asBinder();

    @Override // a.b.a.a.b
    void debug(NativeHandle nativeHandle, ArrayList<String> arrayList) throws RemoteException;

    d f(String str, String str2) throws RemoteException;

    @Override // a.b.a.a.b
    a.b.a.a.a getDebugInfo() throws RemoteException;

    @Override // a.b.a.a.b
    ArrayList<byte[]> getHashChain() throws RemoteException;

    @Override // a.b.a.a.b
    ArrayList<String> interfaceChain() throws RemoteException;

    @Override // a.b.a.a.b
    String interfaceDescriptor() throws RemoteException;

    b j(String str) throws RemoteException;

    int k(String str, String str2) throws RemoteException;

    @Override // a.b.a.a.b
    boolean linkToDeath(IHwBinder.DeathRecipient deathRecipient, long j) throws RemoteException;

    @Override // a.b.a.a.b
    void notifySyspropsChanged() throws RemoteException;

    @Override // a.b.a.a.b
    void ping() throws RemoteException;

    @Override // a.b.a.a.b
    void setHALInstrumentation() throws RemoteException;

    @Override // a.b.a.a.b
    boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) throws RemoteException;
}
