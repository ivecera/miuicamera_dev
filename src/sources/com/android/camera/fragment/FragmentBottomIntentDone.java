package com.android.camera.fragment;

import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.animation.FragmentAnimationFactory;
import com.android.camera.data.DataRepository;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;

public class FragmentBottomIntentDone extends BaseFragment implements View.OnClickListener, ModeProtocol.HandleBackTrace {
    public static final int FRAGMENT_INFO = 4083;
    private ImageView mApplyView;
    private View mMainView;
    private ImageView mRetryView;

    private void adjustViewBackground(View view, int i) {
        if (i == 165) {
            view.setBackgroundResource(R.color.black);
            return;
        }
        int uiStyle = DataRepository.dataItemRunning().getUiStyle();
        if (uiStyle == 1 || uiStyle == 3) {
            view.setBackgroundResource(R.color.fullscreen_background);
        } else {
            view.setBackgroundResource(R.color.black);
        }
    }

    @Override // com.android.camera.fragment.BaseFragment
    public int getFragmentInto() {
        return 4083;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public int getLayoutResourceId() {
        return R.layout.fragment_bottom_intent_done;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void initView(View view) {
        this.mMainView = view;
        ((ViewGroup.MarginLayoutParams) view.getLayoutParams()).height = Util.getBottomHeight();
        adjustViewBackground(this.mMainView, ((BaseFragment) this).mCurrentMode);
        this.mRetryView = (ImageView) view.findViewById(R.id.intent_done_retry);
        this.mApplyView = (ImageView) view.findViewById(R.id.intent_done_apply);
        this.mRetryView.setOnClickListener(this);
        this.mApplyView.setOnClickListener(this);
    }

    @Override // com.android.camera.fragment.BaseFragment, com.android.camera.animation.AnimationDelegate.AnimationResource
    public void notifyDataChanged(int i, int i2) {
        super.notifyDataChanged(i, i2);
        if (i == 2) {
            adjustViewBackground(this.mMainView, ((BaseFragment) this).mCurrentMode);
        } else if (i == 3) {
            adjustViewBackground(this.mMainView, ((BaseFragment) this).mCurrentMode);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.HandleBackTrace
    public boolean onBackEvent(int i) {
        ModeProtocol.CameraAction cameraAction;
        if (i != 1 || !canProvide() || (cameraAction = (ModeProtocol.CameraAction) ModeCoordinatorImpl.getInstance().getAttachProtocol(161)) == null) {
            return false;
        }
        cameraAction.onReviewCancelClicked();
        return true;
    }

    public void onClick(View view) {
        ModeProtocol.CameraAction cameraAction = (ModeProtocol.CameraAction) ModeCoordinatorImpl.getInstance().getAttachProtocol(161);
        if (cameraAction != null) {
            switch (view.getId()) {
                case R.id.intent_done_apply:
                    cameraAction.onReviewDoneClicked();
                    return;
                case R.id.intent_done_retry:
                    cameraAction.onReviewCancelClicked();
                    return;
                default:
                    return;
            }
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public Animation provideEnterAnimation(int i) {
        return FragmentAnimationFactory.wrapperAnimation(167, 161);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public Animation provideExitAnimation(int i) {
        return FragmentAnimationFactory.wrapperAnimation(168, 162);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void register(ModeProtocol.ModeCoordinator modeCoordinator) {
        super.register(modeCoordinator);
        registerBackStack(modeCoordinator, this);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void unRegister(ModeProtocol.ModeCoordinator modeCoordinator) {
        super.unRegister(modeCoordinator);
        unRegisterBackStack(modeCoordinator, this);
    }
}
