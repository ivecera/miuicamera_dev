package com.android.camera.fragment.beauty;

import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.animation.FragmentAnimationFactory;
import com.android.camera.fragment.BaseFragment;

@Deprecated
public class FragmentPopupBeautyLevel extends BaseFragment {
    public static final int FRAGMENT_INFO = 4082;

    @Override // com.android.camera.fragment.BaseFragment
    public int getFragmentInto() {
        return 4082;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public int getLayoutResourceId() {
        return R.layout.fragment_popup_beautylevel;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void initView(View view) {
        ((ViewGroup.MarginLayoutParams) view.getLayoutParams()).bottomMargin = Util.getBottomHeight();
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public Animation provideEnterAnimation(int i) {
        return FragmentAnimationFactory.wrapperAnimation(161);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public Animation provideExitAnimation(int i) {
        return FragmentAnimationFactory.wrapperAnimation(162);
    }
}
