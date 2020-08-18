package com.xiaomi.stat;

import com.xiaomi.stat.a.l;

class m implements Runnable {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ HttpEvent f640a;

    /* renamed from: b  reason: collision with root package name */
    final /* synthetic */ e f641b;

    m(e eVar, HttpEvent httpEvent) {
        this.f641b = eVar;
        this.f640a = httpEvent;
    }

    public void run() {
        if (b.a() && this.f641b.h(false)) {
            e eVar = this.f641b;
            eVar.a(l.a(this.f640a, eVar.f620b));
        }
    }
}
