package com.arcsoft.camera.utils;

import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: MPoint */
public class b implements Parcelable {

    /* renamed from: c  reason: collision with root package name */
    public static final Parcelable.Creator<b> f210c = new f();

    /* renamed from: a  reason: collision with root package name */
    public int f211a;

    /* renamed from: b  reason: collision with root package name */
    public int f212b;

    public b() {
    }

    public b(int i, int i2) {
        this.f211a = i;
        this.f212b = i2;
    }

    public b(b bVar) {
        this.f211a = bVar.f211a;
        this.f212b = bVar.f212b;
    }

    public int a() {
        return 0;
    }

    public void a(Parcel parcel) {
        this.f211a = parcel.readInt();
        this.f212b = parcel.readInt();
    }

    public void a(Parcel parcel, int i) {
        parcel.writeInt(this.f211a);
        parcel.writeInt(this.f212b);
    }

    public int b() {
        return (this.f211a * 31) + this.f212b;
    }

    public final void c() {
        this.f211a = -this.f211a;
        this.f212b = -this.f212b;
    }

    public final boolean c(int i, int i2) {
        return this.f211a == i && this.f212b == i2;
    }

    public String d() {
        return "Point(" + this.f211a + ", " + this.f212b + ")";
    }

    public final void d(int i, int i2) {
        this.f211a += i;
        this.f212b += i2;
    }

    public void e(int i, int i2) {
        this.f211a = i;
        this.f212b = i2;
    }

    public boolean h(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && b.class == obj.getClass()) {
            b bVar = (b) obj;
            if (this.f211a == bVar.f211a && this.f212b == bVar.f212b) {
                return true;
            }
        }
        return false;
    }
}
