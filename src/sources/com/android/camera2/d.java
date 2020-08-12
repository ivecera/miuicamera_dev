package com.android.camera2;

/* compiled from: lambda */
public final /* synthetic */ class d implements Runnable {
    private final /* synthetic */ MiCamera2 Hi;
    private final /* synthetic */ boolean Li;

    public /* synthetic */ d(MiCamera2 miCamera2, boolean z) {
        this.Hi = miCamera2;
        this.Li = z;
    }

    public final void run() {
        this.Hi.y(this.Li);
    }
}
