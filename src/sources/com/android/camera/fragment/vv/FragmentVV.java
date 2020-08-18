package com.android.camera.fragment.vv;

import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.animation.FragmentAnimationFactory;
import com.android.camera.fragment.BaseFragment;
import com.android.camera.fragment.BaseFragmentDelegate;
import com.android.camera.fragment.FragmentUtils;
import com.android.camera.log.Log;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera.statistic.CameraStatUtils;

public class FragmentVV extends BaseFragment implements View.OnClickListener, ModeProtocol.LiveVVChooser, ModeProtocol.HandleBackTrace, ResourceSelectedListener {
    private VVItem mSelectedItem;
    private View mShotView;

    private void adjustViewBackground(View view, int i) {
        view.setBackgroundResource(R.color.halfscreen_background);
    }

    private void initFragment(int i) {
        FragmentVVGallery fragmentVVGallery = new FragmentVVGallery();
        fragmentVVGallery.setResourceSelectedListener(this);
        fragmentVVGallery.registerProtocol();
        fragmentVVGallery.setPreviewData(i);
        FragmentUtils.addFragmentWithTag(getChildFragmentManager(), (int) R.id.vv_lift, fragmentVVGallery, fragmentVVGallery.getFragmentTag());
    }

    @Override // com.android.camera.fragment.BaseFragment
    public int getFragmentInto() {
        return BaseFragmentDelegate.FRAGMENT_VV;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public int getLayoutResourceId() {
        return R.layout.fragment_vv;
    }

    @Override // com.android.camera.protocol.ModeProtocol.LiveVVChooser
    public void hide() {
        FragmentUtils.removeFragmentByTag(getChildFragmentManager(), String.valueOf((int) BaseFragmentDelegate.FRAGMENT_VV_GALLERY));
        FragmentUtils.removeFragmentByTag(getChildFragmentManager(), String.valueOf((int) BaseFragmentDelegate.FRAGMENT_VV_PREVIEW));
        getView().setVisibility(8);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void initView(View view) {
        ((ViewGroup.MarginLayoutParams) view.getLayoutParams()).height = -2;
        adjustViewBackground(view, ((BaseFragment) this).mCurrentMode);
        View findViewById = view.findViewById(R.id.vv_start_layout);
        ((ViewGroup.MarginLayoutParams) findViewById.getLayoutParams()).height = getResources().getDimensionPixelSize(R.dimen.vv_start_layout_height_extra) + Util.sBottomMargin;
        this.mShotView = findViewById.findViewById(R.id.vv_start);
        this.mShotView.setVisibility(4);
        this.mShotView.setOnClickListener(this);
        initFragment(0);
    }

    @Override // com.android.camera.protocol.ModeProtocol.LiveVVChooser
    public boolean isPreviewShow() {
        FragmentVVPreview fragmentVVPreview;
        return isShow() && (fragmentVVPreview = (FragmentVVPreview) FragmentUtils.getFragmentByTag(getChildFragmentManager(), String.valueOf(BaseFragmentDelegate.FRAGMENT_VV_PREVIEW))) != null && fragmentVVPreview.isVisible();
    }

    @Override // com.android.camera.protocol.ModeProtocol.LiveVVChooser
    public boolean isShow() {
        return isAdded() && getView().getVisibility() == 0;
    }

    @Override // com.android.camera.protocol.ModeProtocol.HandleBackTrace
    public boolean onBackEvent(int i) {
        return false;
    }

    public void onClick(View view) {
        if (isEnableClick() && view.getId() == R.id.vv_start) {
            startShot();
        }
    }

    @Override // com.android.camera.fragment.vv.ResourceSelectedListener
    public void onResourceReady() {
        this.mShotView.setVisibility(0);
    }

    @Override // com.android.camera.fragment.vv.ResourceSelectedListener
    public void onResourceSelected(VVItem vVItem) {
        Log.d("vvSelected:", vVItem.index + " | " + vVItem.name);
        this.mSelectedItem = vVItem;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public Animation provideEnterAnimation(int i) {
        return FragmentAnimationFactory.wrapperAnimation(167, 161);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public Animation provideExitAnimation(int i) {
        return FragmentAnimationFactory.wrapperAnimation(162, 168);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void register(ModeProtocol.ModeCoordinator modeCoordinator) {
        super.register(modeCoordinator);
        modeCoordinator.attachProtocol(229, this);
        registerBackStack(modeCoordinator, this);
    }

    @Override // com.android.camera.protocol.ModeProtocol.LiveVVChooser
    public void show(int i) {
        getView().setVisibility(0);
        initFragment(i);
    }

    @Override // com.android.camera.protocol.ModeProtocol.LiveVVChooser
    public void startShot() {
        ModeProtocol.ConfigChanges configChanges;
        if (this.mSelectedItem != null && (configChanges = (ModeProtocol.ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164)) != null) {
            CameraStatUtils.trackVVStartClick(this.mSelectedItem.name, isPreviewShow());
            configChanges.configLiveVV(this.mSelectedItem, true, false);
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void unRegister(ModeProtocol.ModeCoordinator modeCoordinator) {
        super.unRegister(modeCoordinator);
        modeCoordinator.detachProtocol(229, this);
        unRegisterBackStack(modeCoordinator, this);
    }
}
