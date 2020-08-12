package com.android.camera.ui;

import android.animation.ValueAnimator;

/* compiled from: lambda */
public final /* synthetic */ class a implements ValueAnimator.AnimatorUpdateListener {
    private final /* synthetic */ MutiStateButton Hi;
    private final /* synthetic */ float Li;
    private final /* synthetic */ float Mi;
    private final /* synthetic */ float Ni;
    private final /* synthetic */ float Oi;

    public /* synthetic */ a(MutiStateButton mutiStateButton, float f2, float f3, float f4, float f5) {
        this.Hi = mutiStateButton;
        this.Li = f2;
        this.Mi = f3;
        this.Ni = f4;
        this.Oi = f5;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        this.Hi.a(this.Li, this.Mi, this.Ni, this.Oi, valueAnimator);
    }
}
