package com.xiaomi.stat;

import com.xiaomi.stat.a.l;

class aa implements Runnable {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ boolean f379a;

    /* renamed from: b  reason: collision with root package name */
    final /* synthetic */ String f380b;

    /* renamed from: c  reason: collision with root package name */
    final /* synthetic */ String f381c;

    /* renamed from: d  reason: collision with root package name */
    final /* synthetic */ MiStatParams f382d;

    /* renamed from: e  reason: collision with root package name */
    final /* synthetic */ e f383e;

    aa(e eVar, boolean z, String str, String str2, MiStatParams miStatParams) {
        this.f383e = eVar;
        this.f379a = z;
        this.f380b = str;
        this.f381c = str2;
        this.f382d = miStatParams;
    }

    public void run() {
        if (b.a() && this.f383e.h(this.f379a) && b.B()) {
            e eVar = this.f383e;
            eVar.a(l.a(this.f380b, this.f381c, this.f382d, eVar.f620b, this.f379a));
        }
    }
}
