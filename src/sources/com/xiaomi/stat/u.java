package com.xiaomi.stat;

import com.xiaomi.stat.a.l;

class u implements Runnable {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ String f657a;

    /* renamed from: b  reason: collision with root package name */
    final /* synthetic */ String f658b;

    /* renamed from: c  reason: collision with root package name */
    final /* synthetic */ e f659c;

    u(e eVar, String str, String str2) {
        this.f659c = eVar;
        this.f657a = str;
        this.f658b = str2;
    }

    public void run() {
        if (b.a() && this.f659c.h(false)) {
            e eVar = this.f659c;
            eVar.a(l.a(this.f657a, this.f658b, eVar.f620b));
        }
    }
}
