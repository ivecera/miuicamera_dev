package com.android.camera.scene;

/* compiled from: lambda */
public final /* synthetic */ class b implements Runnable {
    private final /* synthetic */ ASDResultParse Hi;
    private final /* synthetic */ int Li;
    private final /* synthetic */ int Mi;
    private final /* synthetic */ int Ni;

    public /* synthetic */ b(ASDResultParse aSDResultParse, int i, int i2, int i3) {
        this.Hi = aSDResultParse;
        this.Li = i;
        this.Mi = i2;
        this.Ni = i3;
    }

    public final void run() {
        this.Hi.a(this.Li, this.Mi, this.Ni);
    }
}
