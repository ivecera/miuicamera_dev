package com.android.camera.fragment;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.PathInterpolator;
import com.android.camera.Util;
import com.android.camera.animation.AnimationDelegate;
import com.android.camera.animation.type.AlphaInOnSubscribe;
import com.android.camera.animation.type.AlphaOutOnSubscribe;
import com.android.camera.data.DataRepository;
import com.android.camera.fragment.lifeCircle.BaseLifecycleListener;
import com.android.camera.log.Log;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import io.reactivex.Completable;
import java.util.List;

public abstract class BaseFragment extends Fragment implements AnimationDelegate.AnimationResource, ModeProtocol.BaseProtocol {
    protected static final int LEFT_LANDSCAPE = 90;
    protected static final int RIGHT_LANDSCAPE = 270;
    protected static final int ROTATE_FILP = 180;
    protected static final int STATE_HIDE = -1;
    protected static final int STATE_SHOW = 1;
    private static final String TAG = "BaseFragment";
    /* access modifiers changed from: protected */
    public int mCurrentMode;
    protected int mDegree;
    private boolean mEnableClick = true;
    private boolean mInModeChanging;
    private boolean mIsFullScreenNavBarHidden;
    private boolean mIsnotchScreenHidden;
    private int mLaseFragmentInfo = 240;
    private BaseLifecycleListener mLifecycleListener;
    private int mNewFragmentInfo = 240;
    private boolean mRegistered;
    protected int mResetType = 2;
    private boolean mSilentRemove = false;

    @Override // com.android.camera.animation.AnimationDelegate.AnimationResource
    public final boolean canProvide() {
        return isAdded();
    }

    public abstract int getFragmentInto();

    @NonNull
    public final String getFragmentTag() {
        return String.valueOf(getFragmentInto());
    }

    /* access modifiers changed from: protected */
    @LayoutRes
    public abstract int getLayoutResourceId();

    /* access modifiers changed from: protected */
    public abstract void initView(View view);

    @Override // com.android.camera.animation.AnimationDelegate.AnimationResource
    public boolean isEnableClick() {
        return this.mEnableClick;
    }

    public final boolean isFlipRotate() {
        return this.mDegree == 180;
    }

    /* access modifiers changed from: protected */
    public boolean isInModeChanging() {
        return this.mInModeChanging;
    }

    public final boolean isLandScape() {
        int i = this.mDegree;
        return i == 90 || i == 270;
    }

    public final boolean isLeftLandScape() {
        return this.mDegree == 90;
    }

    public final boolean isRightLandScape() {
        return this.mDegree == 270;
    }

    @Override // com.android.camera.animation.AnimationDelegate.AnimationResource
    public boolean needViewClear() {
        return false;
    }

    @Override // com.android.camera.animation.AnimationDelegate.AnimationResource
    @CallSuper
    public void notifyAfterFrameAvailable(int i) {
    }

    @Override // com.android.camera.animation.AnimationDelegate.AnimationResource
    @CallSuper
    public void notifyDataChanged(int i, int i2) {
        this.mInModeChanging = false;
        this.mCurrentMode = i2;
    }

    @Override // android.support.v4.app.Fragment
    public Animation onCreateAnimation(int i, boolean z, int i2) {
        if (z) {
            return provideEnterAnimation(this.mLaseFragmentInfo);
        }
        if (!this.mSilentRemove) {
            return provideExitAnimation(this.mNewFragmentInfo);
        }
        return null;
    }

    @Override // android.support.v4.app.Fragment
    @Nullable
    public View onCreateView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        this.mCurrentMode = DataRepository.dataItemGlobal().getCurrentMode();
        this.mIsFullScreenNavBarHidden = Util.sIsFullScreenNavBarHidden;
        this.mIsnotchScreenHidden = Util.sIsnotchScreenHidden;
        View inflate = layoutInflater.inflate(getLayoutResourceId(), viewGroup, false);
        initView(inflate);
        return inflate;
    }

    @Override // android.support.v4.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        if (this.mRegistered) {
            this.mRegistered = false;
            unRegisterProtocol();
            BaseLifecycleListener baseLifecycleListener = this.mLifecycleListener;
            if (baseLifecycleListener != null) {
                baseLifecycleListener.onLifeDestroy(getFragmentTag());
                this.mLifecycleListener = null;
            }
        }
    }

    @Override // android.support.v4.app.Fragment
    public void onResume() {
        super.onResume();
        if (!(this.mIsnotchScreenHidden == Util.sIsnotchScreenHidden && this.mIsFullScreenNavBarHidden == Util.sIsFullScreenNavBarHidden)) {
            initView(getView());
        }
        this.mIsnotchScreenHidden = Util.sIsnotchScreenHidden;
        this.mIsFullScreenNavBarHidden = Util.sIsFullScreenNavBarHidden;
    }

    @Override // android.support.v4.app.Fragment
    public void onStart() {
        super.onStart();
        BaseLifecycleListener baseLifecycleListener = this.mLifecycleListener;
        if (baseLifecycleListener != null) {
            baseLifecycleListener.onLifeStart(getFragmentTag());
        }
    }

    @Override // android.support.v4.app.Fragment
    public void onStop() {
        super.onStop();
        BaseLifecycleListener baseLifecycleListener = this.mLifecycleListener;
        if (baseLifecycleListener != null) {
            baseLifecycleListener.onLifeStop(getFragmentTag());
        }
    }

    @Override // android.support.v4.app.Fragment
    public void onViewCreated(View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);
    }

    public void pendingGone(boolean z) {
        setClickEnable(false);
        this.mSilentRemove = z;
    }

    public void pendingShow() {
        setClickEnable(true);
    }

    @Override // com.android.camera.animation.AnimationDelegate.AnimationResource
    @CallSuper
    public void provideAnimateElement(int i, List<Completable> list, int i2) {
        if (list != null) {
            this.mInModeChanging = true;
        }
        this.mCurrentMode = i;
        this.mResetType = i2;
    }

    /* access modifiers changed from: protected */
    public Animation provideEnterAnimation(int i) {
        return null;
    }

    /* access modifiers changed from: protected */
    public Animation provideExitAnimation(int i) {
        return null;
    }

    @Override // com.android.camera.animation.AnimationDelegate.AnimationResource
    @CallSuper
    public void provideRotateItem(List<View> list, int i) {
        setDegree(i);
    }

    /* access modifiers changed from: protected */
    @CallSuper
    public void register(ModeProtocol.ModeCoordinator modeCoordinator) {
    }

    /* access modifiers changed from: protected */
    public final void registerBackStack(ModeProtocol.ModeCoordinator modeCoordinator, ModeProtocol.HandleBackTrace handleBackTrace) {
        ((ModeProtocol.BackStack) modeCoordinator.getAttachProtocol(171)).addInBackStack(handleBackTrace);
    }

    @Override // com.android.camera.protocol.ModeProtocol.BaseProtocol
    public final void registerProtocol() {
        this.mRegistered = true;
        register(ModeCoordinatorImpl.getInstance());
    }

    @Override // com.android.camera.animation.AnimationDelegate.AnimationResource
    @CallSuper
    public void setClickEnable(boolean z) {
        String str = TAG;
        Log.d(str, "setClickEnable: " + z);
        this.mEnableClick = z;
    }

    public final void setDegree(int i) {
        this.mDegree = i;
    }

    public void setEnableClickInitValue(boolean z) {
        this.mEnableClick = z;
    }

    public void setLastFragmentInfo(int i) {
        this.mLaseFragmentInfo = i;
    }

    public void setLifecycleListener(BaseLifecycleListener baseLifecycleListener) {
        this.mLifecycleListener = baseLifecycleListener;
    }

    public void setNewFragmentInfo(int i) {
        this.mNewFragmentInfo = i;
    }

    /* access modifiers changed from: protected */
    public void starAnimatetViewGone(View view, boolean z) {
        if (view.getVisibility() == 0) {
            if (z) {
                Completable.create(new AlphaOutOnSubscribe(view).setDurationTime(260).setInterpolator(new PathInterpolator(0.25f, 0.1f, 0.25f, 1.0f))).subscribe();
            } else {
                AlphaOutOnSubscribe.directSetResult(view);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void startAnimateViewVisible(View view, boolean z) {
        if (view.getVisibility() != 0) {
            if (z) {
                Completable.create(new AlphaInOnSubscribe(view).setStartDelayTime(240).setDurationTime(300).setInterpolator(new PathInterpolator(0.25f, 0.1f, 0.25f, 1.0f))).subscribe();
            } else {
                AlphaInOnSubscribe.directSetResult(view);
            }
        }
    }

    /* access modifiers changed from: protected */
    @CallSuper
    public void unRegister(ModeProtocol.ModeCoordinator modeCoordinator) {
    }

    /* access modifiers changed from: protected */
    public final void unRegisterBackStack(ModeProtocol.ModeCoordinator modeCoordinator, ModeProtocol.HandleBackTrace handleBackTrace) {
        ModeProtocol.BackStack backStack = (ModeProtocol.BackStack) modeCoordinator.getAttachProtocol(171);
        if (backStack != null) {
            backStack.removeBackStack(handleBackTrace);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.BaseProtocol
    public final void unRegisterProtocol() {
        unRegister(ModeCoordinatorImpl.getInstance());
    }
}
