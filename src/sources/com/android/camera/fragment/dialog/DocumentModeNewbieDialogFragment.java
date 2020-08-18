package com.android.camera.fragment.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.android.camera.R;

public class DocumentModeNewbieDialogFragment extends AiSceneNewbieDialogFragment {
    public static final String TAG = "DocumentModeHint";

    @Override // com.android.camera.fragment.dialog.AiSceneNewbieDialogFragment, android.support.v4.app.Fragment
    @Nullable
    public View onCreateView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_dialog_document_mode_hint, viewGroup, false);
        initViewOnTouchListener(inflate);
        adjustViewHeight(inflate.findViewById(R.id.document_mode_use_hint_layout));
        return inflate;
    }
}
