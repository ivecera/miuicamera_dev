package com.xiaomi.stat;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.os.SystemClock;

class r implements Application.ActivityLifecycleCallbacks {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ e f653a;

    /* renamed from: b  reason: collision with root package name */
    private int f654b;

    r(e eVar) {
        this.f653a = eVar;
    }

    public void onActivityCreated(Activity activity, Bundle bundle) {
    }

    public void onActivityDestroyed(Activity activity) {
    }

    public void onActivityPaused(Activity activity) {
        e.j(this.f653a);
        if (this.f654b == System.identityHashCode(activity)) {
            long elapsedRealtime = SystemClock.elapsedRealtime() - this.f653a.f624f;
            long l = this.f653a.d();
            this.f653a.a(activity.getClass().getName(), l - elapsedRealtime, l);
            this.f653a.h();
        }
    }

    public void onActivityResumed(Activity activity) {
        e.h(this.f653a);
        this.f654b = System.identityHashCode(activity);
        long unused = this.f653a.f624f = SystemClock.elapsedRealtime();
        this.f653a.h();
    }

    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }

    public void onActivityStarted(Activity activity) {
        if (this.f653a.i == 0) {
            long unused = this.f653a.l = SystemClock.elapsedRealtime();
            int unused2 = this.f653a.j = 0;
            int unused3 = this.f653a.k = 0;
            this.f653a.f623e.execute(new s(this));
        }
        e.g(this.f653a);
    }

    public void onActivityStopped(Activity activity) {
        e.m(this.f653a);
        if (this.f653a.i == 0) {
            long elapsedRealtime = SystemClock.elapsedRealtime() - this.f653a.l;
            long b2 = com.xiaomi.stat.d.r.b();
            e eVar = this.f653a;
            eVar.a(eVar.j, this.f653a.k, b2 - elapsedRealtime, b2);
            this.f653a.f623e.execute(new t(this));
        }
    }
}
