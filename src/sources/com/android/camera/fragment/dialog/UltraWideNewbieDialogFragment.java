package com.android.camera.fragment.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.android.camera.HybridZoomingSystem;
import com.android.camera.R;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;

public class UltraWideNewbieDialogFragment extends AiSceneNewbieDialogFragment {
    public static final String TAG = "UltraWideHint";

    @Override // com.android.camera.fragment.dialog.AiSceneNewbieDialogFragment, com.android.camera.fragment.dialog.BaseDialogFragment, com.android.camera.protocol.ModeProtocol.HandleBackTrace
    public boolean onBackEvent(int i) {
        boolean onBackEvent = super.onBackEvent(i);
        ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
        if (bottomPopupTips != null) {
            bottomPopupTips.directShowLeftImageIntro();
        }
        return onBackEvent;
    }

    @Override // com.android.camera.fragment.dialog.AiSceneNewbieDialogFragment, android.support.v4.app.Fragment
    @Nullable
    public View onCreateView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_dialog_ultra_wide_hint, viewGroup, false);
        if (HybridZoomingSystem.IS_3_OR_MORE_SAT) {
            ((TextView) inflate.findViewById(R.id.ultra_wide_use_hint_message_body)).setText(R.string.ultra_wide_mode_use_hint_text_sat);
        }
        initViewOnTouchListener(inflate);
        adjustViewHeight(inflate.findViewById(R.id.ultra_wide_use_hint_layout));
        return inflate;
    }
}
