package com.android.camera.fragment.dialog;

import android.view.MotionEvent;
import android.view.View;

/* compiled from: lambda */
public final /* synthetic */ class a implements View.OnTouchListener {
    private final /* synthetic */ BaseDialogFragment Hi;

    public /* synthetic */ a(BaseDialogFragment baseDialogFragment) {
        this.Hi = baseDialogFragment;
    }

    public final boolean onTouch(View view, MotionEvent motionEvent) {
        return this.Hi.a(view, motionEvent);
    }
}
