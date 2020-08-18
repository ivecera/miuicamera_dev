package com.xiaomi.stat;

import com.xiaomi.stat.a.l;

class n implements Runnable {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ NetAvailableEvent f642a;

    /* renamed from: b  reason: collision with root package name */
    final /* synthetic */ e f643b;

    n(e eVar, NetAvailableEvent netAvailableEvent) {
        this.f643b = eVar;
        this.f642a = netAvailableEvent;
    }

    public void run() {
        if (b.a() && this.f643b.h(false) && b.z()) {
            e eVar = this.f643b;
            eVar.a(l.a(this.f642a, eVar.f620b));
        }
    }
}
