package com.xiaomi.a.a.a;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.Map;

public interface a extends IInterface {

    /* renamed from: com.xiaomi.a.a.a.a$a  reason: collision with other inner class name */
    public static abstract class C0017a extends Binder implements a {

        /* renamed from: a  reason: collision with root package name */
        static final int f296a = 1;

        /* renamed from: b  reason: collision with root package name */
        static final int f297b = 2;

        /* renamed from: c  reason: collision with root package name */
        private static final String f298c = "com.xiaomi.xmsf.push.service.IHttpService";

        /* renamed from: com.xiaomi.a.a.a.a$a$a  reason: collision with other inner class name */
        private static class C0018a implements a {

            /* renamed from: a  reason: collision with root package name */
            private IBinder f299a;

            C0018a(IBinder iBinder) {
                this.f299a = iBinder;
            }

            public String a() {
                return C0017a.f298c;
            }

            @Override // com.xiaomi.a.a.a.a
            public String a(String str, Map map) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(C0017a.f298c);
                    obtain.writeString(str);
                    obtain.writeMap(map);
                    this.f299a.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public IBinder asBinder() {
                return this.f299a;
            }

            @Override // com.xiaomi.a.a.a.a
            public String b(String str, Map map) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(C0017a.f298c);
                    obtain.writeString(str);
                    obtain.writeMap(map);
                    this.f299a.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public C0017a() {
            attachInterface(this, f298c);
        }

        public static a a(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(f298c);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof a)) ? new C0018a(iBinder) : (a) queryLocalInterface;
        }

        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i == 1) {
                parcel.enforceInterface(f298c);
                String a2 = a(parcel.readString(), parcel.readHashMap(C0017a.class.getClassLoader()));
                parcel2.writeNoException();
                parcel2.writeString(a2);
                return true;
            } else if (i == 2) {
                parcel.enforceInterface(f298c);
                String b2 = b(parcel.readString(), parcel.readHashMap(C0017a.class.getClassLoader()));
                parcel2.writeNoException();
                parcel2.writeString(b2);
                return true;
            } else if (i != 1598968902) {
                return super.onTransact(i, parcel, parcel2, i2);
            } else {
                parcel2.writeString(f298c);
                return true;
            }
        }
    }

    String a(String str, Map map) throws RemoteException;

    String b(String str, Map map) throws RemoteException;
}
