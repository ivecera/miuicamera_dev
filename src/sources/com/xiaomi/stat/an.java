package com.xiaomi.stat;

import java.lang.Thread;

public class an implements Thread.UncaughtExceptionHandler {

    /* renamed from: a  reason: collision with root package name */
    private e f425a;

    /* renamed from: b  reason: collision with root package name */
    private Thread.UncaughtExceptionHandler f426b;

    /* renamed from: c  reason: collision with root package name */
    private boolean f427c = true;

    public an(e eVar) {
        this.f425a = eVar;
    }

    public void a() {
        Thread.UncaughtExceptionHandler defaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        if (!(defaultUncaughtExceptionHandler instanceof an)) {
            this.f426b = defaultUncaughtExceptionHandler;
            Thread.setDefaultUncaughtExceptionHandler(this);
        }
    }

    public void a(boolean z) {
        this.f427c = z;
    }

    public void uncaughtException(Thread thread, Throwable th) {
        if (this.f427c) {
            this.f425a.a(th, (String) null, false);
        }
        Thread.UncaughtExceptionHandler uncaughtExceptionHandler = this.f426b;
        if (uncaughtExceptionHandler != null) {
            uncaughtExceptionHandler.uncaughtException(thread, th);
        }
    }
}
