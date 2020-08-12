package com.android.camera.features.mimoji2.fragment.bottomlist;

import android.view.View;
import com.android.camera.features.mimoji2.bean.MimojiInfo2;
import com.android.camera.features.mimoji2.fragment.bottomlist.MimojiCreateItemAdapter2;

/* compiled from: lambda */
public final /* synthetic */ class c implements MimojiCreateItemAdapter2.OnItemClickListener {
    private final /* synthetic */ FragmentMimojiBottomList Hi;

    public /* synthetic */ c(FragmentMimojiBottomList fragmentMimojiBottomList) {
        this.Hi = fragmentMimojiBottomList;
    }

    @Override // com.android.camera.features.mimoji2.fragment.bottomlist.MimojiCreateItemAdapter2.OnItemClickListener
    public final void onItemClick(MimojiInfo2 mimojiInfo2, int i, View view) {
        this.Hi.a(mimojiInfo2, i, view);
    }
}
