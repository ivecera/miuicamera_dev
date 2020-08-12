package com.xiaomi.stat;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import com.xiaomi.stat.d.r;

public class aj {

    /* renamed from: a  reason: collision with root package name */
    public static final int f414a = 1;

    /* renamed from: b  reason: collision with root package name */
    private static final int f415b = 10000;

    /* renamed from: c  reason: collision with root package name */
    private static final int f416c = 3;

    /* renamed from: d  reason: collision with root package name */
    private Handler f417d;
    /* access modifiers changed from: private */

    /* renamed from: e  reason: collision with root package name */
    public Runnable f418e;

    /* renamed from: f  reason: collision with root package name */
    private HandlerThread f419f;
    /* access modifiers changed from: private */
    public int g = 3;
    /* access modifiers changed from: private */
    public int h = 10000;
    private int i = 0;
    /* access modifiers changed from: private */
    public boolean j = false;

    class a implements Handler.Callback {

        /* renamed from: b  reason: collision with root package name */
        private Handler f421b;

        private a() {
            this.f421b = null;
        }

        /* access modifiers changed from: private */
        public void a(Handler handler) {
            this.f421b = handler;
        }

        public boolean handleMessage(Message message) {
            if (message.what == 1) {
                int intValue = ((Integer) message.obj).intValue();
                if (intValue < aj.this.g) {
                    aj.this.f418e.run();
                    if (aj.this.j) {
                        Message obtainMessage = this.f421b.obtainMessage(1);
                        obtainMessage.obj = Integer.valueOf(intValue + 1);
                        this.f421b.sendMessageDelayed(obtainMessage, (long) aj.this.h);
                    }
                } else {
                    aj.this.b();
                }
            }
            return true;
        }
    }

    public aj(Runnable runnable) {
        this.f418e = runnable;
    }

    private void d() {
        a aVar = new a();
        this.f419f = new HandlerThread("".concat("_").concat(String.valueOf(r.b())));
        this.f419f.start();
        this.f417d = new Handler(this.f419f.getLooper(), aVar);
        aVar.a(this.f417d);
    }

    public void a() {
        Handler handler = this.f417d;
        if (handler == null || !handler.hasMessages(1)) {
            d();
            Message obtainMessage = this.f417d.obtainMessage(1);
            obtainMessage.obj = 0;
            this.j = true;
            this.f417d.sendMessageDelayed(obtainMessage, (long) this.i);
        }
    }

    public void a(int i2) {
        this.i = i2;
    }

    public void b() {
        this.f417d.removeMessages(1);
        this.f417d.getLooper().quit();
        this.j = false;
    }

    public void b(int i2) {
        this.g = i2;
    }

    public void c(int i2) {
        this.h = i2;
    }

    public boolean c() {
        return this.j;
    }
}
