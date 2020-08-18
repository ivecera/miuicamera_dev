package com.xiaomi.stat.c;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.net.TrafficStats;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.android.camera.snap.SnapTrigger;
import com.ss.android.ugc.effectmanager.link.model.configuration.LinkSelectorConfiguration;
import com.xiaomi.stat.a.c;
import com.xiaomi.stat.am;
import com.xiaomi.stat.b;
import com.xiaomi.stat.d.k;
import com.xiaomi.stat.d.l;
import com.xiaomi.stat.d.p;
import com.xiaomi.stat.d.r;
import java.util.concurrent.atomic.AtomicBoolean;

public class g extends Handler {

    /* renamed from: c  reason: collision with root package name */
    private static final String f506c = "UploadTimer";

    /* renamed from: d  reason: collision with root package name */
    private static final int f507d = 15000;

    /* renamed from: e  reason: collision with root package name */
    private static final int f508e = 5;

    /* renamed from: f  reason: collision with root package name */
    private static final int f509f = 86400;
    private static final int g = 1;
    private static final int h = 2;
    private static final int i = 3;

    /* renamed from: a  reason: collision with root package name */
    public AtomicBoolean f510a;

    /* renamed from: b  reason: collision with root package name */
    BroadcastReceiver f511b;
    private int j;
    private int k;
    private long l;
    private boolean m;
    private int n;

    public g(Looper looper) {
        super(looper);
        this.j = SnapTrigger.MAX_VIDEO_DURATION;
        this.f510a = new AtomicBoolean(true);
        this.k = 15000;
        this.l = r.b();
        this.m = true;
        this.f511b = new h(this);
        this.k = LinkSelectorConfiguration.MS_OF_ONE_MIN;
        sendEmptyMessageDelayed(1, (long) this.k);
        a(am.a());
        k.b(f506c, " UploadTimer: " + this.k);
    }

    private int a(int i2) {
        if (i2 < 0) {
            return 0;
        }
        if (i2 <= 0 || i2 >= 5) {
            return i2 > f509f ? f509f : i2;
        }
        return 5;
    }

    private void a(Context context) {
        if (context != null) {
            try {
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
                context.registerReceiver(this.f511b, intentFilter);
            } catch (Exception e2) {
                k.b(f506c, "registerNetReceiver: " + e2);
            }
        }
    }

    private int f() {
        int a2 = a(b.n());
        if (a2 > 0) {
            return a2 * 1000;
        }
        int a3 = a(b.k());
        if (a3 > 0) {
            return a3 * 1000;
        }
        return 15000;
    }

    private void g() {
        i.a().c();
        e();
    }

    private void h() {
        if (l.a()) {
            b();
        }
    }

    private void i() {
        int i2 = (c.a().c() > 0 ? 1 : (c.a().c() == 0 ? 0 : -1));
        if (i2 >= 0) {
            if (i2 > 0) {
                b();
                this.f510a.set(false);
            } else {
                this.f510a.set(true);
            }
            k.b(f506c, " checkDatabase mIsDatabaseEmpty=" + this.f510a.get());
        }
    }

    public long a() {
        return (long) this.k;
    }

    public void a(boolean z) {
        if (!z && !this.m) {
            b();
        }
        this.m = false;
    }

    public void b() {
        if (this.k != this.n) {
            this.n = f();
            this.k = this.n;
            if (r.b() - this.l > ((long) this.k)) {
                removeMessages(1);
                sendEmptyMessageDelayed(1, (long) this.k);
                this.l = r.b();
            }
            k.b(f506c, " resetBackgroundInterval: " + this.k);
        }
    }

    public void b(boolean z) {
        if (z) {
            b();
        }
        long c2 = c.a().c();
        int i2 = (c2 > 0 ? 1 : (c2 == 0 ? 0 : -1));
        if (i2 == 0) {
            this.f510a.set(true);
        }
        k.b(f506c, " totalCount=" + c2 + " deleteData=" + z);
        if (this.k < this.j) {
            if (i2 == 0 || !z) {
                this.k += 15000;
            }
        }
    }

    public void c() {
        this.k = this.j;
    }

    public void d() {
        if (this.f510a.get()) {
            sendEmptyMessage(2);
        }
    }

    public void e() {
        Context a2 = am.a();
        long n2 = p.n(a2);
        long m2 = p.m(a2);
        long totalRxBytes = TrafficStats.getTotalRxBytes() == -1 ? 0 : TrafficStats.getTotalRxBytes() / 1024;
        long b2 = r.b();
        p.e(a2, b2);
        p.d(a2, totalRxBytes);
        p.a(a2, ((float) ((totalRxBytes - m2) * 1000)) / ((float) (b2 - n2)));
    }

    public void handleMessage(Message message) {
        super.handleMessage(message);
        int i2 = message.what;
        if (i2 == 1) {
            g();
            sendEmptyMessageDelayed(1, (long) this.k);
        } else if (i2 == 2) {
            i();
        } else if (i2 == 3) {
            h();
        }
    }
}
