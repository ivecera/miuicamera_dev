package com.android.camera.fragment.mimoji;

import android.view.View;
import com.android.camera.fragment.mimoji.MimojiCreateItemAdapter;

/* compiled from: lambda */
public final /* synthetic */ class a implements MimojiCreateItemAdapter.OnItemClickListener {
    private final /* synthetic */ FragmentMimoji Hi;

    public /* synthetic */ a(FragmentMimoji fragmentMimoji) {
        this.Hi = fragmentMimoji;
    }

    @Override // com.android.camera.fragment.mimoji.MimojiCreateItemAdapter.OnItemClickListener
    public final void onItemClick(MimojiInfo mimojiInfo, int i, View view) {
        this.Hi.a(mimojiInfo, i, view);
    }
}
