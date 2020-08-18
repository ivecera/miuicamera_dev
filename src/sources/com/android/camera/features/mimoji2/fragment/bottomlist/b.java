package com.android.camera.features.mimoji2.fragment.bottomlist;

import com.android.camera.features.mimoji2.bean.MimojiBgInfo;
import com.android.camera.features.mimoji2.widget.baseview.OnRecyclerItemClickListener;

/* compiled from: lambda */
public final /* synthetic */ class b implements OnRecyclerItemClickListener {
    private final /* synthetic */ FragmentMimojiBottomList Hi;

    public /* synthetic */ b(FragmentMimojiBottomList fragmentMimojiBottomList) {
        this.Hi = fragmentMimojiBottomList;
    }

    @Override // com.android.camera.features.mimoji2.widget.baseview.OnRecyclerItemClickListener
    public final void OnRecyclerItemClickListener(Object obj, int i) {
        this.Hi.a((MimojiBgInfo) obj, i);
    }
}
