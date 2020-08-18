package com.android.camera.fragment.live;

import android.view.animation.Animation;
import com.android.camera.HybridZoomingSystem;
import com.android.camera.R;
import com.android.camera.animation.FragmentAnimationFactory;
import com.android.camera.data.DataRepository;
import com.android.camera.fragment.BaseFragment;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import d.h.a.A;
import d.h.a.C;
import io.reactivex.Completable;
import java.util.List;

public abstract class FragmentLiveBase extends BaseFragment implements ModeProtocol.HandleBackTrace {
    protected boolean mIsNeedShowWhenExit = true;
    protected boolean mRemoveFragment;

    private class ExitAnimationListener implements Animation.AnimationListener {
        private ExitAnimationListener() {
        }

        public void onAnimationEnd(Animation animation) {
            ModeProtocol.DualController dualController;
            ModeProtocol.BaseDelegate baseDelegate = (ModeProtocol.BaseDelegate) ModeCoordinatorImpl.getInstance().getAttachProtocol(160);
            if (baseDelegate != null && baseDelegate.getActiveFragment(R.id.bottom_beauty) == 4090) {
                baseDelegate.delegateEvent(10);
            }
            if (FragmentLiveBase.this.mRemoveFragment) {
                ModeProtocol.CameraAction cameraAction = (ModeProtocol.CameraAction) ModeCoordinatorImpl.getInstance().getAttachProtocol(161);
                if (cameraAction != null && !cameraAction.isDoingAction() && FragmentLiveBase.this.mIsNeedShowWhenExit) {
                    ((ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175)).reInitTipImage();
                }
                FragmentLiveBase.this.mRemoveFragment = false;
            }
            if (!HybridZoomingSystem.IS_3_OR_MORE_SAT) {
                return;
            }
            if ((((BaseFragment) FragmentLiveBase.this).mCurrentMode == 174 || ((BaseFragment) FragmentLiveBase.this).mCurrentMode == 161 || ((BaseFragment) FragmentLiveBase.this).mCurrentMode == 183) && DataRepository.dataItemGlobal().getCurrentCameraId() == 0 && (dualController = (ModeProtocol.DualController) ModeCoordinatorImpl.getInstance().getAttachProtocol(182)) != null) {
                dualController.showZoomButton();
                ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
                if (topAlert != null) {
                    topAlert.clearAlertStatus();
                }
            }
        }

        public void onAnimationRepeat(Animation animation) {
        }

        public void onAnimationStart(Animation animation) {
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.HandleBackTrace
    public boolean onBackEvent(int i) {
        ModeProtocol.BaseDelegate baseDelegate = (ModeProtocol.BaseDelegate) ModeCoordinatorImpl.getInstance().getAttachProtocol(160);
        if (baseDelegate == null || baseDelegate.getActiveFragment(R.id.bottom_beauty) != getFragmentInto()) {
            return false;
        }
        this.mRemoveFragment = true;
        baseDelegate.delegateEvent(10);
        ((ModeProtocol.BottomMenuProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(197)).onSwitchCameraActionMenu(0);
        return true;
    }

    @Override // android.support.v4.app.Fragment
    public void onPause() {
        super.onPause();
        onBackEvent(5);
    }

    @Override // com.android.camera.fragment.BaseFragment, com.android.camera.animation.AnimationDelegate.AnimationResource
    public void provideAnimateElement(int i, List<Completable> list, int i2) {
        super.provideAnimateElement(i, list, i2);
        if (i2 != 3) {
            onBackEvent(5);
        } else {
            onBackEvent(4);
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public Animation provideEnterAnimation(int i) {
        Animation wrapperAnimation = FragmentAnimationFactory.wrapperAnimation(167, 161);
        wrapperAnimation.setDuration(280);
        wrapperAnimation.setInterpolator(new C());
        return wrapperAnimation;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public Animation provideExitAnimation(int i) {
        Animation wrapperAnimation = FragmentAnimationFactory.wrapperAnimation(new ExitAnimationListener(), 168, 162);
        wrapperAnimation.setDuration(140);
        wrapperAnimation.setInterpolator(new A());
        return wrapperAnimation;
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
