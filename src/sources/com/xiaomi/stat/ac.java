package com.xiaomi.stat;

import com.xiaomi.stat.a.l;

class ac implements Runnable {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ boolean f388a;

    /* renamed from: b  reason: collision with root package name */
    final /* synthetic */ MiStatParams f389b;

    /* renamed from: c  reason: collision with root package name */
    final /* synthetic */ e f390c;

    ac(e eVar, boolean z, MiStatParams miStatParams) {
        this.f390c = eVar;
        this.f388a = z;
        this.f389b = miStatParams;
    }

    public void run() {
        if (b.a() && this.f390c.h(this.f388a)) {
            e eVar = this.f390c;
            eVar.a(l.a(this.f389b, this.f388a, eVar.f620b));
        }
    }
}
