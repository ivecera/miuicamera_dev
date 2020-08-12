package com.xiaomi.stat;

import com.xiaomi.stat.a.l;

class q implements Runnable {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ int f648a;

    /* renamed from: b  reason: collision with root package name */
    final /* synthetic */ int f649b;

    /* renamed from: c  reason: collision with root package name */
    final /* synthetic */ long f650c;

    /* renamed from: d  reason: collision with root package name */
    final /* synthetic */ long f651d;

    /* renamed from: e  reason: collision with root package name */
    final /* synthetic */ e f652e;

    q(e eVar, int i, int i2, long j, long j2) {
        this.f652e = eVar;
        this.f648a = i;
        this.f649b = i2;
        this.f650c = j;
        this.f651d = j2;
    }

    public void run() {
        if (b.a() && this.f652e.g()) {
            this.f652e.a(l.a(this.f648a, this.f649b, this.f650c, this.f651d));
        }
    }
}
