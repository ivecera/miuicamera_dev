package d.e;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* compiled from: IPopupCameraManager */
public interface a extends IInterface {

    /* renamed from: d.e.a$a  reason: collision with other inner class name */
    /* compiled from: IPopupCameraManager */
    public static class C0020a implements a {
        @Override // d.e.a
        public void D() throws RemoteException {
        }

        @Override // d.e.a
        public boolean a(int i, int i2, String str) throws RemoteException {
            return false;
        }

        public IBinder asBinder() {
            return null;
        }

        @Override // d.e.a
        public int getMotorStatus() throws RemoteException {
            return 0;
        }

        @Override // d.e.a
        public boolean popupMotor() throws RemoteException {
            return false;
        }

        @Override // d.e.a
        public boolean takebackMotor() throws RemoteException {
            return false;
        }
    }

    /* compiled from: IPopupCameraManager */
    public static abstract class b extends Binder implements a {
        private static final String DESCRIPTOR = "miui.popupcamera.IPopupCameraManager";
        static final int cg = 1;
        static final int dg = 2;
        static final int eg = 3;
        static final int fg = 4;
        static final int gg = 5;

        /* renamed from: d.e.a$b$a  reason: collision with other inner class name */
        /* compiled from: IPopupCameraManager */
        private static class C0021a implements a {
            public static a sDefaultImpl;
            private IBinder mRemote;

            C0021a(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // d.e.a
            public void D() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(b.DESCRIPTOR);
                    if (this.mRemote.transact(5, obtain, obtain2, 0) || b.getDefaultImpl() == null) {
                        obtain2.readException();
                        obtain2.recycle();
                        obtain.recycle();
                        return;
                    }
                    b.getDefaultImpl().D();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // d.e.a
            public boolean a(int i, int i2, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(b.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    boolean z = false;
                    if (!this.mRemote.transact(1, obtain, obtain2, 0) && b.getDefaultImpl() != null) {
                        return b.getDefaultImpl().a(i, i2, str);
                    }
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        z = true;
                    }
                    obtain2.recycle();
                    obtain.recycle();
                    return z;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return b.DESCRIPTOR;
            }

            @Override // d.e.a
            public int getMotorStatus() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(b.DESCRIPTOR);
                    if (!this.mRemote.transact(4, obtain, obtain2, 0) && b.getDefaultImpl() != null) {
                        return b.getDefaultImpl().getMotorStatus();
                    }
                    obtain2.readException();
                    int readInt = obtain2.readInt();
                    obtain2.recycle();
                    obtain.recycle();
                    return readInt;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // d.e.a
            public boolean popupMotor() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(b.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(2, obtain, obtain2, 0) && b.getDefaultImpl() != null) {
                        return b.getDefaultImpl().popupMotor();
                    }
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        z = true;
                    }
                    obtain2.recycle();
                    obtain.recycle();
                    return z;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // d.e.a
            public boolean takebackMotor() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(b.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(3, obtain, obtain2, 0) && b.getDefaultImpl() != null) {
                        return b.getDefaultImpl().takebackMotor();
                    }
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        z = true;
                    }
                    obtain2.recycle();
                    obtain.recycle();
                    return z;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public b() {
            attachInterface(this, DESCRIPTOR);
        }

        public static boolean a(a aVar) {
            if (C0021a.sDefaultImpl != null || aVar == null) {
                return false;
            }
            C0021a.sDefaultImpl = aVar;
            return true;
        }

        public static a asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof a)) ? new C0021a(iBinder) : (a) queryLocalInterface;
        }

        public static a getDefaultImpl() {
            return C0021a.sDefaultImpl;
        }

        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            int i3 = 0;
            if (i == 1) {
                parcel.enforceInterface(DESCRIPTOR);
                boolean a2 = a(parcel.readInt(), parcel.readInt(), parcel.readString());
                parcel2.writeNoException();
                if (a2) {
                    i3 = 1;
                }
                parcel2.writeInt(i3);
                return true;
            } else if (i == 2) {
                parcel.enforceInterface(DESCRIPTOR);
                boolean popupMotor = popupMotor();
                parcel2.writeNoException();
                if (popupMotor) {
                    i3 = 1;
                }
                parcel2.writeInt(i3);
                return true;
            } else if (i == 3) {
                parcel.enforceInterface(DESCRIPTOR);
                boolean takebackMotor = takebackMotor();
                parcel2.writeNoException();
                if (takebackMotor) {
                    i3 = 1;
                }
                parcel2.writeInt(i3);
                return true;
            } else if (i == 4) {
                parcel.enforceInterface(DESCRIPTOR);
                int motorStatus = getMotorStatus();
                parcel2.writeNoException();
                parcel2.writeInt(motorStatus);
                return true;
            } else if (i == 5) {
                parcel.enforceInterface(DESCRIPTOR);
                D();
                parcel2.writeNoException();
                return true;
            } else if (i != 1598968902) {
                return super.onTransact(i, parcel, parcel2, i2);
            } else {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
        }
    }

    void D() throws RemoteException;

    boolean a(int i, int i2, String str) throws RemoteException;

    int getMotorStatus() throws RemoteException;

    boolean popupMotor() throws RemoteException;

    boolean takebackMotor() throws RemoteException;
}
