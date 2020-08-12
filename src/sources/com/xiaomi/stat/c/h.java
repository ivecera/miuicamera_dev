package com.xiaomi.stat.c;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

class h extends BroadcastReceiver {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ g f512a;

    h(g gVar) {
        this.f512a = gVar;
    }

    public void onReceive(Context context, Intent intent) {
        this.f512a.sendEmptyMessage(3);
    }
}
