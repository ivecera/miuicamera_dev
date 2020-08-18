package com.xiaomi.stat;

import com.xiaomi.stat.a.l;

class j implements Runnable {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ int f633a;

    /* renamed from: b  reason: collision with root package name */
    final /* synthetic */ int f634b;

    /* renamed from: c  reason: collision with root package name */
    final /* synthetic */ e f635c;

    j(e eVar, int i, int i2) {
        this.f635c = eVar;
        this.f633a = i;
        this.f634b = i2;
    }

    public void run() {
        if (b.a() && this.f635c.g()) {
            b.e(this.f633a);
            this.f635c.a(l.a(this.f634b));
        }
    }
}
