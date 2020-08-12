package com.xiaomi.stat;

public class NetAvailableEvent {
    public static final int RESULT_TYPE_0 = 0;
    public static final int RESULT_TYPE_1 = 1;
    public static final int RESULT_TYPE_2 = 2;

    /* renamed from: a  reason: collision with root package name */
    private String f315a;

    /* renamed from: b  reason: collision with root package name */
    private int f316b;

    /* renamed from: c  reason: collision with root package name */
    private int f317c;

    /* renamed from: d  reason: collision with root package name */
    private String f318d;

    /* renamed from: e  reason: collision with root package name */
    private int f319e;

    /* renamed from: f  reason: collision with root package name */
    private long f320f;
    private int g;
    private String h;

    public static final class Builder {
        /* access modifiers changed from: private */

        /* renamed from: a  reason: collision with root package name */
        public String f321a;
        /* access modifiers changed from: private */

        /* renamed from: b  reason: collision with root package name */
        public int f322b;
        /* access modifiers changed from: private */

        /* renamed from: c  reason: collision with root package name */
        public int f323c;
        /* access modifiers changed from: private */

        /* renamed from: d  reason: collision with root package name */
        public String f324d;
        /* access modifiers changed from: private */

        /* renamed from: e  reason: collision with root package name */
        public int f325e;
        /* access modifiers changed from: private */

        /* renamed from: f  reason: collision with root package name */
        public long f326f;
        /* access modifiers changed from: private */
        public int g;
        /* access modifiers changed from: private */
        public String h;

        public NetAvailableEvent build() {
            return new NetAvailableEvent(this);
        }

        public Builder exception(String str) {
            this.f324d = str;
            return this;
        }

        public Builder ext(String str) {
            this.h = str;
            return this;
        }

        public Builder flag(String str) {
            this.f321a = str;
            return this;
        }

        public Builder requestStartTime(long j) {
            this.f326f = j;
            return this;
        }

        public Builder responseCode(int i) {
            this.f322b = i;
            return this;
        }

        public Builder resultType(int i) {
            this.f325e = i;
            return this;
        }

        public Builder retryCount(int i) {
            this.g = i;
            return this;
        }

        public Builder statusCode(int i) {
            this.f323c = i;
            return this;
        }
    }

    private NetAvailableEvent(Builder builder) {
        this.f315a = builder.f321a;
        this.f316b = builder.f322b;
        this.f317c = builder.f323c;
        this.f318d = builder.f324d;
        this.f319e = builder.f325e;
        this.f320f = builder.f326f;
        this.g = builder.g;
        this.h = builder.h;
    }

    public String getException() {
        return this.f318d;
    }

    public String getExt() {
        return this.h;
    }

    public String getFlag() {
        return this.f315a;
    }

    public long getRequestStartTime() {
        return this.f320f;
    }

    public int getResponseCode() {
        return this.f316b;
    }

    public int getResultType() {
        return this.f319e;
    }

    public int getRetryCount() {
        return this.g;
    }

    public int getStatusCode() {
        return this.f317c;
    }
}
