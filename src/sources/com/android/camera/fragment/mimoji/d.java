package com.android.camera.fragment.mimoji;

import com.android.camera.features.mimoji2.widget.autoselectview.AutoSelectAdapter;

/* compiled from: lambda */
public final /* synthetic */ class d implements AutoSelectAdapter.OnSelectListener {
    private final /* synthetic */ FragmentMimojiEdit Hi;

    public /* synthetic */ d(FragmentMimojiEdit fragmentMimojiEdit) {
        this.Hi = fragmentMimojiEdit;
    }

    @Override // com.android.camera.features.mimoji2.widget.autoselectview.AutoSelectAdapter.OnSelectListener
    public final void onSelectListener(Object obj, int i) {
        this.Hi.a((MimojiTypeBean) obj, i);
    }
}
