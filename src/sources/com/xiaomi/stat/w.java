package com.xiaomi.stat;

import com.xiaomi.stat.a.l;

class w implements Runnable {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ String f662a;

    /* renamed from: b  reason: collision with root package name */
    final /* synthetic */ long f663b;

    /* renamed from: c  reason: collision with root package name */
    final /* synthetic */ long f664c;

    /* renamed from: d  reason: collision with root package name */
    final /* synthetic */ e f665d;

    w(e eVar, String str, long j, long j2) {
        this.f665d = eVar;
        this.f662a = str;
        this.f663b = j;
        this.f664c = j2;
    }

    public void run() {
        if (b.a() && this.f665d.g() && b.A()) {
            this.f665d.a(l.a(this.f662a, this.f663b, this.f664c));
        }
    }
}
