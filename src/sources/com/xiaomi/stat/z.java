package com.xiaomi.stat;

import com.xiaomi.stat.a.l;

class z implements Runnable {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ String f670a;

    /* renamed from: b  reason: collision with root package name */
    final /* synthetic */ long f671b;

    /* renamed from: c  reason: collision with root package name */
    final /* synthetic */ long f672c;

    /* renamed from: d  reason: collision with root package name */
    final /* synthetic */ MiStatParams f673d;

    /* renamed from: e  reason: collision with root package name */
    final /* synthetic */ e f674e;

    z(e eVar, String str, long j, long j2, MiStatParams miStatParams) {
        this.f674e = eVar;
        this.f670a = str;
        this.f671b = j;
        this.f672c = j2;
        this.f673d = miStatParams;
    }

    public void run() {
        if (b.a() && this.f674e.h(false) && b.A()) {
            e eVar = this.f674e;
            String str = this.f670a;
            long j = this.f671b;
            eVar.a(l.a(str, j - this.f672c, j, this.f673d, eVar.f620b));
        }
    }
}
