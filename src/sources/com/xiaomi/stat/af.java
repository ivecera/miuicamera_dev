package com.xiaomi.stat;

import android.os.FileObserver;

class af extends FileObserver {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ ad f404a;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    af(ad adVar, String str) {
        super(str);
        this.f404a = adVar;
    }

    public void onEvent(int i, String str) {
        if (i == 2) {
            synchronized (this.f404a) {
                this.f404a.b();
            }
            b.o();
        }
    }
}
