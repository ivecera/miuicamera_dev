package com.android.camera.features.mimoji2.widget.baseview;

import android.view.View;

/* compiled from: lambda */
public final /* synthetic */ class a implements View.OnClickListener {
    private final /* synthetic */ OnRecyclerItemClickListener Hi;
    private final /* synthetic */ Object Li;
    private final /* synthetic */ int Mi;

    public /* synthetic */ a(OnRecyclerItemClickListener onRecyclerItemClickListener, Object obj, int i) {
        this.Hi = onRecyclerItemClickListener;
        this.Li = obj;
        this.Mi = i;
    }

    public final void onClick(View view) {
        this.Hi.OnRecyclerItemClickListener(this.Li, this.Mi);
    }
}
