package com.bumptech.glide.request.target;

import android.view.View;

/* compiled from: ViewTarget */
class q implements View.OnAttachStateChangeListener {
    final /* synthetic */ ViewTarget this$0;

    q(ViewTarget viewTarget) {
        this.this$0 = viewTarget;
    }

    public void onViewAttachedToWindow(View view) {
        this.this$0.Fk();
    }

    public void onViewDetachedFromWindow(View view) {
        this.this$0.Ek();
    }
}
