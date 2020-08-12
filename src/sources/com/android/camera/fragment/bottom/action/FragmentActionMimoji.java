package com.android.camera.fragment.bottom.action;

import android.view.View;
import com.android.camera.R;
import com.android.camera.constant.ColorConstant;
import com.android.camera.fragment.BaseFragment;
import com.android.camera.fragment.BaseFragmentDelegate;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera.ui.ColorActivateTextView;

public class FragmentActionMimoji extends BaseFragment implements View.OnClickListener {
    private ColorActivateTextView mTextViewMimoji;

    @Override // com.android.camera.fragment.BaseFragment
    public int getFragmentInto() {
        return BaseFragmentDelegate.FRAGMENT_LIGHTING;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public int getLayoutResourceId() {
        return R.layout.fragment_bottom_action_mimoji;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void initView(View view) {
        this.mTextViewMimoji = (ColorActivateTextView) view.findViewById(R.id.text_item_title);
        this.mTextViewMimoji.setNormalCor(-1711276033);
        this.mTextViewMimoji.setActivateColor(ColorConstant.COLOR_COMMON_NORMAL);
        this.mTextViewMimoji.setText(R.string.mimoji_fragment_tab_name);
    }

    public void onClick(View view) {
        ModeProtocol.CameraAction cameraAction;
        if (isEnableClick() && (cameraAction = (ModeProtocol.CameraAction) ModeCoordinatorImpl.getInstance().getAttachProtocol(161)) != null && cameraAction.isDoingAction()) {
        }
    }

    public void reInit() {
    }
}
