package com.android.camera.fragment.beauty;

import android.view.View;
import android.widget.AdapterView;

/* compiled from: lambda */
public final /* synthetic */ class a implements AdapterView.OnItemClickListener {
    private final /* synthetic */ RemodelingParamsFragment Hi;

    public /* synthetic */ a(RemodelingParamsFragment remodelingParamsFragment) {
        this.Hi = remodelingParamsFragment;
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public final void onItemClick(AdapterView adapterView, View view, int i, long j) {
        this.Hi.a(adapterView, view, i, j);
    }
}
