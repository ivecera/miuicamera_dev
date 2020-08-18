package com.xiaomi.stat;

import com.xiaomi.stat.a.l;

class ab implements Runnable {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ Throwable f384a;

    /* renamed from: b  reason: collision with root package name */
    final /* synthetic */ String f385b;

    /* renamed from: c  reason: collision with root package name */
    final /* synthetic */ boolean f386c;

    /* renamed from: d  reason: collision with root package name */
    final /* synthetic */ e f387d;

    ab(e eVar, Throwable th, String str, boolean z) {
        this.f387d = eVar;
        this.f384a = th;
        this.f385b = str;
        this.f386c = z;
    }

    public void run() {
        if (b.a() && this.f387d.h(false)) {
            e eVar = this.f387d;
            eVar.a(l.a(this.f384a, this.f385b, this.f386c, eVar.f620b));
        }
    }
}
