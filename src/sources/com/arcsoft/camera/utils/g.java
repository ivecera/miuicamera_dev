package com.arcsoft.camera.utils;

/* compiled from: MSize */
public class g implements Comparable<g> {

    /* renamed from: a  reason: collision with root package name */
    private int f220a;

    /* renamed from: b  reason: collision with root package name */
    private int f221b;

    public g() {
    }

    public g(int i, int i2) {
        this.f220a = i;
        this.f221b = i2;
    }

    /* renamed from: a */
    public int i(g gVar) {
        if (gVar == null) {
            return 1;
        }
        int i = this.f220a;
        int i2 = gVar.f220a;
        return i == i2 ? this.f221b - gVar.f221b : i - i2;
    }

    public String a() {
        return new String("[" + this.f220a + "," + this.f221b + "]");
    }

    public int b() {
        return (this.f220a * 31) + this.f221b;
    }

    public boolean c(int i, int i2) {
        return this.f220a == i && this.f221b == i2;
    }

    public boolean h(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof g) || obj == null) {
            return false;
        }
        g gVar = (g) obj;
        return c(gVar.f220a, gVar.f221b);
    }
}
