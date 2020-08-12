package com.android.camera.features.mimoji2.fragment.edit;

import com.android.camera.features.mimoji2.bean.MimojiTypeBean2;
import com.android.camera.features.mimoji2.widget.autoselectview.AutoSelectAdapter;

/* compiled from: lambda */
public final /* synthetic */ class b implements AutoSelectAdapter.OnSelectListener {
    private final /* synthetic */ FragmentMimojiEdit2 Hi;

    public /* synthetic */ b(FragmentMimojiEdit2 fragmentMimojiEdit2) {
        this.Hi = fragmentMimojiEdit2;
    }

    @Override // com.android.camera.features.mimoji2.widget.autoselectview.AutoSelectAdapter.OnSelectListener
    public final void onSelectListener(Object obj, int i) {
        this.Hi.a((MimojiTypeBean2) obj, i);
    }
}
