package com.android.camera.fragment.aiwatermark;

import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import com.android.camera.R;
import com.android.camera.animation.type.AlphaOutOnSubscribe;
import com.android.camera.animation.type.SlideInOnSubscribe;
import com.android.camera.animation.type.SlideOutOnSubscribe;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.ComponentDataItem;
import com.android.camera.data.data.runing.ComponentRunningAIWatermark;
import com.android.camera.fragment.BaseFragment;
import com.android.camera.fragment.BaseFragmentPagerAdapter;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera.statistic.CameraStatUtils;
import com.android.camera.statistic.MistatsConstants;
import com.android.camera.ui.NoScrollViewPager;
import d.h.a.A;
import d.h.a.C;
import io.reactivex.Completable;
import java.util.ArrayList;
import java.util.List;

public class FragmentWatermark extends BaseFragment implements ModeProtocol.WatermarkProtocol, ModeProtocol.HandleBackTrace {
    public static final int FRAGMENT_INFO = 65534;
    private static final String TAG = "FragmentWatermark";
    private ComponentRunningAIWatermark mComponentAIWatermark;
    private int mCurrentState = -1;
    private BaseFragmentPagerAdapter mPagerAdapter;
    private NoScrollViewPager mViewPager;

    static /* synthetic */ boolean b(View view, MotionEvent motionEvent) {
        return true;
    }

    private void checkAIWatermark() {
        if (DataRepository.dataItemRunning().getComponentRunningAIWatermark().needActive()) {
            resetFragment();
        }
    }

    private void initViewPager() {
        ArrayList arrayList = new ArrayList();
        for (ComponentDataItem componentDataItem : this.mComponentAIWatermark.getItems()) {
            int intValue = Integer.valueOf(componentDataItem.mValue).intValue();
            if (intValue == 0) {
                arrayList.add(new FragmentGenWatermark());
            } else if (intValue == 1) {
                arrayList.add(new FragmentSpotsWatermark());
            } else if (intValue == 2) {
                arrayList.add(new FragmentFestivalWatermark());
            } else if (intValue == 3) {
                arrayList.add(new FragmentASDWatermark());
            } else if (intValue == 4) {
                arrayList.add(new FragmentCityWatermark());
            }
        }
        this.mPagerAdapter = new BaseFragmentPagerAdapter(getChildFragmentManager(), arrayList);
        this.mViewPager.setAdapter(this.mPagerAdapter);
        this.mViewPager.setOffscreenPageLimit(2);
        this.mViewPager.setFocusable(false);
        this.mViewPager.setOnTouchListener(b.INSTANCE);
    }

    private void initWatermarkType() {
        this.mComponentAIWatermark = DataRepository.dataItemRunning().getComponentRunningAIWatermark();
        this.mCurrentState = 1;
        initViewPager();
        String currentType = this.mComponentAIWatermark.getCurrentType();
        initWatermarkType(currentType, true);
        setViewPagerPageByType(currentType);
    }

    private void initWatermarkType(String str, boolean z) {
        if (!TextUtils.isEmpty(str)) {
            this.mComponentAIWatermark.setCurrentType(str);
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: onDismissFinished */
    public void La() {
        resetFragment();
        ModeProtocol.CameraAction cameraAction = (ModeProtocol.CameraAction) ModeCoordinatorImpl.getInstance().getAttachProtocol(161);
        if (cameraAction != null && !cameraAction.isDoingAction() && !cameraAction.isRecording()) {
            resetTips();
        }
    }

    private void resetFragment() {
        this.mViewPager.setAdapter(null);
        BaseFragmentPagerAdapter baseFragmentPagerAdapter = this.mPagerAdapter;
        if (baseFragmentPagerAdapter != null) {
            baseFragmentPagerAdapter.recycleFragmentList(getChildFragmentManager());
            this.mPagerAdapter = null;
        }
    }

    private void resetTips() {
    }

    private void setViewPagerPageByType(String str) {
        List<ComponentDataItem> items = this.mComponentAIWatermark.getItems();
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).mValue.equals(str)) {
                this.mViewPager.setCurrentItem(i, false);
                return;
            }
        }
    }

    private void setViewPagerSelect(int i) {
        this.mComponentAIWatermark.getItems();
        this.mViewPager.setCurrentItem(i, false);
    }

    @Override // com.android.camera.protocol.ModeProtocol.WatermarkProtocol
    public boolean dismiss(int i) {
        View view = getView();
        if (this.mCurrentState == -1 || view == null) {
            return false;
        }
        this.mCurrentState = -1;
        if (i == 1) {
            resetFragment();
            view.setVisibility(4);
        } else if (i == 2) {
            ((ModeProtocol.BottomMenuProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(197)).onSwitchCameraActionMenu(0);
            Completable.create(new SlideOutOnSubscribe(view, 80).setDurationTime(140).setInterpolator(new A())).subscribe(new c(this));
            ((ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175)).directShowOrHideLeftTipImage(true);
            CameraStatUtils.trackAIWatermarkClick(MistatsConstants.AIWatermark.AI_WATERMARK_LIST_HIDE);
        } else if (i == 3) {
            ((ModeProtocol.BottomMenuProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(197)).onSwitchCameraActionMenu(0);
            Completable.create(new AlphaOutOnSubscribe(view).setDurationTime(250).setInterpolator(new DecelerateInterpolator())).subscribe(new a(this));
        }
        return true;
    }

    @Override // com.android.camera.fragment.BaseFragment
    public int getFragmentInto() {
        return 65534;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public int getLayoutResourceId() {
        return R.layout.fragment_aiwatermark;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void initView(View view) {
        this.mViewPager = (NoScrollViewPager) view.findViewById(R.id.beauty_viewPager);
        initWatermarkType();
    }

    @Override // com.android.camera.protocol.ModeProtocol.WatermarkProtocol
    public boolean isWatermarkPanelShow() {
        return this.mCurrentState == 1;
    }

    @Override // com.android.camera.protocol.ModeProtocol.HandleBackTrace
    public boolean onBackEvent(int i) {
        if (i == 3) {
            return false;
        }
        int i2 = i != 4 ? 2 : 1;
        this.mComponentAIWatermark.setClosed(true);
        return dismiss(i2);
    }

    @Override // com.android.camera.fragment.BaseFragment, com.android.camera.animation.AnimationDelegate.AnimationResource
    public void provideAnimateElement(int i, List<Completable> list, int i2) {
        super.provideAnimateElement(i, list, i2);
        if (this.mCurrentState != -1) {
            if (i2 == 5) {
                checkAIWatermark();
            }
            onBackEvent(4);
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void register(ModeProtocol.ModeCoordinator modeCoordinator) {
        super.register(modeCoordinator);
        registerBackStack(modeCoordinator, this);
        modeCoordinator.attachProtocol(253, this);
    }

    @Override // com.android.camera.protocol.ModeProtocol.WatermarkProtocol
    public void show() {
        if (this.mCurrentState != 1) {
            initWatermarkType();
            Completable.create(new SlideInOnSubscribe(getView(), 80).setDurationTime(280).setInterpolator(new C())).subscribe();
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.WatermarkProtocol
    public void switchType(String str, boolean z) {
        initWatermarkType(str, z);
        setViewPagerPageByType(str);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void unRegister(ModeProtocol.ModeCoordinator modeCoordinator) {
        super.unRegister(modeCoordinator);
        unRegisterBackStack(modeCoordinator, this);
        modeCoordinator.detachProtocol(253, this);
    }
}
