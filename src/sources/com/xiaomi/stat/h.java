package com.xiaomi.stat;

import android.text.TextUtils;
import com.xiaomi.stat.a.l;

class h implements Runnable {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ String f629a;

    /* renamed from: b  reason: collision with root package name */
    final /* synthetic */ e f630b;

    h(e eVar, String str) {
        this.f630b = eVar;
        this.f629a = str;
    }

    public void run() {
        if (b.a() && !TextUtils.equals(b.i(), this.f629a)) {
            b.b(this.f629a);
            if (this.f630b.g()) {
                this.f630b.a(l.a(this.f629a));
            }
        }
    }
}
