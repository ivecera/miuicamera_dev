package com.xiaomi.stat;

public class HttpEvent {

    /* renamed from: a  reason: collision with root package name */
    private int f303a;

    /* renamed from: b  reason: collision with root package name */
    private long f304b;

    /* renamed from: c  reason: collision with root package name */
    private long f305c;

    /* renamed from: d  reason: collision with root package name */
    private String f306d;

    /* renamed from: e  reason: collision with root package name */
    private String f307e;

    public HttpEvent(String str, long j) {
        this(str, j, -1, (String) null);
    }

    public HttpEvent(String str, long j, int i, String str2) {
        this(str, j, 0, i, str2);
    }

    public HttpEvent(String str, long j, long j2) {
        this(str, j, j2, -1, null);
    }

    public HttpEvent(String str, long j, long j2, int i) {
        this(str, j, j2, i, null);
    }

    public HttpEvent(String str, long j, long j2, int i, String str2) {
        this.f305c = 0;
        this.f306d = str;
        this.f304b = j;
        this.f303a = i;
        this.f307e = str2;
        this.f305c = j2;
    }

    public HttpEvent(String str, long j, String str2) {
        this(str, j, -1, str2);
    }

    public String getExceptionName() {
        return this.f307e;
    }

    public long getNetFlow() {
        return this.f305c;
    }

    public int getResponseCode() {
        return this.f303a;
    }

    public long getTimeCost() {
        return this.f304b;
    }

    public String getUrl() {
        return this.f306d;
    }
}
