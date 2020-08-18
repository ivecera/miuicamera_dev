package com.android.camera.module;

import com.android.camera.fragment.GoogleLensFragment;

/* compiled from: lambda */
public final /* synthetic */ class f implements GoogleLensFragment.OnClickListener {
    private final /* synthetic */ Camera2Module Hi;
    private final /* synthetic */ float Li;
    private final /* synthetic */ float Mi;
    private final /* synthetic */ int Ni;
    private final /* synthetic */ int Oi;

    public /* synthetic */ f(Camera2Module camera2Module, float f2, float f3, int i, int i2) {
        this.Hi = camera2Module;
        this.Li = f2;
        this.Mi = f3;
        this.Ni = i;
        this.Oi = i2;
    }

    @Override // com.android.camera.fragment.GoogleLensFragment.OnClickListener
    public final void onOptionClick(int i) {
        this.Hi.a(this.Li, this.Mi, this.Ni, this.Oi, i);
    }
}
