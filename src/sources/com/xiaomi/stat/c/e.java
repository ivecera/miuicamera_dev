package com.xiaomi.stat.c;

import android.os.IBinder;
import android.os.RemoteException;
import com.xiaomi.a.a.a.a;
import com.xiaomi.stat.b;
import com.xiaomi.stat.d.k;

class e implements Runnable {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ IBinder f498a;

    /* renamed from: b  reason: collision with root package name */
    final /* synthetic */ d f499b;

    e(d dVar, IBinder iBinder) {
        this.f499b = dVar;
        this.f498a = iBinder;
    }

    public void run() {
        a a2 = a.C0017a.a(this.f498a);
        try {
            if (!b.f()) {
                this.f499b.f495a[0] = a2.a(this.f499b.f496b, this.f499b.f497c);
            } else if (b.y()) {
                this.f499b.f495a[0] = a2.b(this.f499b.f496b, this.f499b.f497c);
            } else {
                this.f499b.f495a[0] = null;
            }
            k.b("UploadMode", " connected, do remote http post " + this.f499b.f495a[0]);
            synchronized (i.class) {
                try {
                    i.class.notify();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        } catch (RemoteException e3) {
            k.e("UploadMode", " error while uploading the data by IPC." + e3.toString());
            this.f499b.f495a[0] = null;
        }
    }
}
