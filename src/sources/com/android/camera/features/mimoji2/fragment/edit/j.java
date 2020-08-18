package com.android.camera.features.mimoji2.fragment.edit;

import com.android.camera.features.mimoji2.bean.MimojiEmoticonInfo;
import com.android.camera.features.mimoji2.widget.baseview.OnRecyclerItemClickListener;

/* compiled from: lambda */
public final /* synthetic */ class j implements OnRecyclerItemClickListener {
    private final /* synthetic */ FragmentMimojiEmoticon Hi;

    public /* synthetic */ j(FragmentMimojiEmoticon fragmentMimojiEmoticon) {
        this.Hi = fragmentMimojiEmoticon;
    }

    @Override // com.android.camera.features.mimoji2.widget.baseview.OnRecyclerItemClickListener
    public final void OnRecyclerItemClickListener(Object obj, int i) {
        this.Hi.a((MimojiEmoticonInfo) obj, i);
    }
}
