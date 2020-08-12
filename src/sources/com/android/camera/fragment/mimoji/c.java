package com.android.camera.fragment.mimoji;

import com.android.camera.fragment.mimoji.EditLevelListAdapter;

/* compiled from: lambda */
public final /* synthetic */ class c implements EditLevelListAdapter.ItfGvOnItemClickListener {
    private final /* synthetic */ FragmentMimojiEdit Hi;

    public /* synthetic */ c(FragmentMimojiEdit fragmentMimojiEdit) {
        this.Hi = fragmentMimojiEdit;
    }

    @Override // com.android.camera.fragment.mimoji.EditLevelListAdapter.ItfGvOnItemClickListener
    public final void notifyUIChanged() {
        this.Hi.Ra();
    }
}
