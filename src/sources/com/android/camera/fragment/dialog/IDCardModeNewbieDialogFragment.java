package com.android.camera.fragment.dialog;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.android.camera.R;
import com.android.camera.Util;

public class IDCardModeNewbieDialogFragment extends AiSceneNewbieDialogFragment {
    public static final String TAG = "IDCardModeHint";

    @Override // com.android.camera.fragment.dialog.AiSceneNewbieDialogFragment, android.support.v4.app.Fragment
    @Nullable
    public View onCreateView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_dialog_id_card_mode_hint, viewGroup, false);
        initViewOnTouchListener(inflate);
        adjustViewHeight(inflate.findViewById(R.id.id_card_mode_use_hint_layout));
        Rect displayRect = Util.getDisplayRect(0);
        ((LinearLayout.LayoutParams) inflate.findViewById(R.id.id_card_drawable_layout).getLayoutParams()).topMargin = (int) (getResources().getDimension(R.dimen.id_card_drawable_margin_top) - ((float) displayRect.top));
        return inflate;
    }
}
