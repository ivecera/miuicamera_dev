package com.android.camera.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.SeekBar;
import com.android.camera.CameraSettings;
import com.android.camera.HybridZoomingSystem;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.animation.FragmentAnimationFactory;
import com.android.camera.animation.type.AlphaInOnSubscribe;
import com.android.camera.animation.type.AlphaOutOnSubscribe;
import com.android.camera.animation.type.SlideInOnSubscribe;
import com.android.camera.animation.type.SlideOutOnSubscribe;
import com.android.camera.constant.BeautyConstant;
import com.android.camera.constant.GlobalConstant;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.ComponentDataItem;
import com.android.camera.data.data.TypeItem;
import com.android.camera.data.data.runing.ComponentRunningShine;
import com.android.camera.fragment.beauty.BeautyBodyFragment;
import com.android.camera.fragment.beauty.BeautyEyeLightFragment;
import com.android.camera.fragment.beauty.BeautyLevelFragment;
import com.android.camera.fragment.beauty.BeautySettingManager;
import com.android.camera.fragment.beauty.BeautySmoothLevelFragment;
import com.android.camera.fragment.beauty.BokehSmoothLevelFragment;
import com.android.camera.fragment.beauty.IBeautySettingBusiness;
import com.android.camera.fragment.beauty.LiveBeautyFilterFragment;
import com.android.camera.fragment.beauty.LiveBeautyModeFragment;
import com.android.camera.fragment.beauty.MakeupBeautyFragment;
import com.android.camera.fragment.beauty.MakeupParamsFragment;
import com.android.camera.fragment.beauty.RemodelingParamsFragment;
import com.android.camera.fragment.live.FragmentLiveSpeed;
import com.android.camera.fragment.live.FragmentLiveSticker;
import com.android.camera.log.Log;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera.statistic.MistatsConstants;
import com.android.camera.statistic.MistatsWrapper;
import com.android.camera.ui.NoScrollViewPager;
import com.android.camera.ui.SeekBarCompat;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class FragmentBeauty extends BaseFragment implements ModeProtocol.HandleBackTrace, ModeProtocol.MiBeautyProtocol, ModeProtocol.MakeupProtocol, Consumer<Integer>, View.OnClickListener {
    public static final int FRAGMENT_INFO = 251;
    private static final int INTERVAL = 5;
    private static final int SEEKBAR_PROGRESS_RATIO = 1;
    /* access modifiers changed from: private */
    public int mActiveProgress;
    /* access modifiers changed from: private */
    public SeekBarCompat mAdjustSeekBar;
    private View mBeautyContent;
    private View mBeautyExtraView;
    private BaseFragmentPagerAdapter mBeautyPagerAdapter;
    private BeautySettingManager mBeautySettingManager;
    private ComponentRunningShine mComponentRunningShine;
    private IBeautySettingBusiness mCurrentSettingBusiness;
    private int mCurrentState = -1;
    /* access modifiers changed from: private */
    public BeautyEyeLightFragment mEyeLightFragment;
    /* access modifiers changed from: private */
    public FlowableEmitter<Integer> mFlowableEmitter;
    private boolean mIsEyeLightShow;
    /* access modifiers changed from: private */
    public boolean mIsRTL;
    private Disposable mSeekBarDisposable;
    /* access modifiers changed from: private */
    public int mSeekBarMaxProgress = 100;
    private NoScrollViewPager mViewPager;

    static /* synthetic */ boolean b(View view, MotionEvent motionEvent) {
        return true;
    }

    private void extraEnterAnim() {
        this.mViewPager.setTranslationX(0.0f);
        this.mViewPager.setAlpha(1.0f);
        ViewCompat.animate(this.mViewPager).translationX(-100.0f).alpha(0.0f).setDuration(120).setStartDelay(0).setInterpolator(new AccelerateDecelerateInterpolator()).start();
    }

    private void extraExitAnim() {
        this.mViewPager.setTranslationX(-100.0f);
        this.mViewPager.setAlpha(0.0f);
        ViewCompat.animate(this.mViewPager).translationX(0.0f).alpha(1.0f).setDuration(280).setInterpolator(new AccelerateDecelerateInterpolator()).setStartDelay(120).start();
    }

    private boolean hideBeautyLayout(int i, int i2) {
        if (this.mCurrentState == -1) {
            return false;
        }
        if (3 == i && !isHiddenBeautyPanelOnShutter()) {
            return false;
        }
        this.mCurrentState = -1;
        View view = getView();
        if (view == null) {
            return false;
        }
        if (i2 == 1) {
            resetFragment();
            view.setVisibility(4);
        } else if (i2 == 2) {
            ((ModeProtocol.BottomMenuProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(197)).onSwitchCameraActionMenu(0);
            Completable.create(new SlideOutOnSubscribe(view, 80).setDurationTime(140).setInterpolator(new AccelerateDecelerateInterpolator())).subscribe(new e(this));
        } else if (i2 == 3) {
            ((ModeProtocol.BottomMenuProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(197)).onSwitchCameraActionMenu(0);
            Completable.create(new AlphaOutOnSubscribe(view).setDurationTime(250).setInterpolator(new DecelerateInterpolator())).subscribe(new c(this));
        }
        ModeProtocol.ConfigChanges configChanges = (ModeProtocol.ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164);
        if (configChanges != null) {
            configChanges.onShineDismiss(i);
        }
        return true;
    }

    private void initAdjustSeekBar() {
        if (this.mSeekBarDisposable == null) {
            this.mSeekBarDisposable = Flowable.create(new FlowableOnSubscribe<Integer>() {
                /* class com.android.camera.fragment.FragmentBeauty.AnonymousClass2 */

                @Override // io.reactivex.FlowableOnSubscribe
                public void subscribe(FlowableEmitter<Integer> flowableEmitter) throws Exception {
                    FlowableEmitter unused = FragmentBeauty.this.mFlowableEmitter = flowableEmitter;
                }
            }, BackpressureStrategy.DROP).observeOn(GlobalConstant.sCameraSetupScheduler).onBackpressureDrop(new Consumer<Integer>() {
                /* class com.android.camera.fragment.FragmentBeauty.AnonymousClass1 */

                public void accept(@NonNull Integer num) throws Exception {
                    Log.e(Log.VIEW_TAG, "seekbar change too fast :" + num.toString());
                }
            }).subscribe(this);
            this.mAdjustSeekBar.setProgressDrawable(getResources().getDrawable(R.drawable.seekbar_style));
            this.mAdjustSeekBar.setOnSeekBarChangeListener(new SeekBarCompat.OnSeekBarCompatChangeListener() {
                /* class com.android.camera.fragment.FragmentBeauty.AnonymousClass3 */

                @Override // com.android.camera.ui.SeekBarCompat.OnSeekBarCompatChangeListener
                public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                    if (i == 0 || i == FragmentBeauty.this.mSeekBarMaxProgress || Math.abs(i - FragmentBeauty.this.mActiveProgress) > 5) {
                        int unused = FragmentBeauty.this.mActiveProgress = i;
                        FragmentBeauty.this.mFlowableEmitter.onNext(Integer.valueOf(i / 1));
                    }
                }

                @Override // com.android.camera.ui.SeekBarCompat.OnSeekBarCompatChangeListener
                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                @Override // com.android.camera.ui.SeekBarCompat.OnSeekBarCompatChangeListener
                public void onStopTrackingTouch(SeekBar seekBar) {
                }
            });
            this.mAdjustSeekBar.setOnSeekBarCompatTouchListener(new SeekBarCompat.OnSeekBarCompatTouchListener() {
                /* class com.android.camera.fragment.FragmentBeauty.AnonymousClass4 */

                private int getNextProgress(MotionEvent motionEvent) {
                    int width = FragmentBeauty.this.mAdjustSeekBar.getWidth();
                    int x = (int) (((FragmentBeauty.this.mIsRTL ? ((float) width) - motionEvent.getX() : motionEvent.getX()) / ((float) width)) * ((float) FragmentBeauty.this.mSeekBarMaxProgress));
                    int pinProgress = FragmentBeauty.this.mAdjustSeekBar.getPinProgress();
                    if (pinProgress > 0 && (motionEvent.getAction() == 2 || motionEvent.getAction() == 1)) {
                        if (Math.abs(x + 0) <= 5) {
                            x = 0;
                        } else if (Math.abs(x - pinProgress) <= 5) {
                            x = pinProgress;
                        } else if (Math.abs(x - FragmentBeauty.this.mSeekBarMaxProgress) <= 5) {
                            x = FragmentBeauty.this.mSeekBarMaxProgress;
                        }
                    }
                    return FragmentBeauty.this.mAdjustSeekBar.isCenterTwoWayMode() ? Util.clamp(x - pinProgress, 0 - pinProgress, FragmentBeauty.this.mSeekBarMaxProgress - pinProgress) : Util.clamp(x, 0, FragmentBeauty.this.mSeekBarMaxProgress);
                }

                @Override // com.android.camera.ui.SeekBarCompat.OnSeekBarCompatTouchListener
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    int action = motionEvent.getAction();
                    if (action == 0) {
                        if (!FragmentBeauty.this.mAdjustSeekBar.getThumb().getBounds().contains((int) motionEvent.getX(), (int) motionEvent.getY())) {
                            return true;
                        }
                    } else if (!(action == 1 || action == 2)) {
                        return false;
                    }
                    FragmentBeauty.this.mAdjustSeekBar.setProgress(getNextProgress(motionEvent));
                    return true;
                }
            });
        }
    }

    private void initShineType() {
        int i;
        int i2;
        int uiStyle = DataRepository.dataItemRunning().getUiStyle();
        if (uiStyle == 3 || uiStyle == 1 || (i2 = ((BaseFragment) this).mCurrentMode) == 171 || i2 == 176) {
            this.mBeautyContent.setBackgroundResource(R.color.fullscreen_background);
        } else {
            this.mBeautyContent.setBackgroundResource(R.color.beauty_panel_bg);
        }
        if (((BaseFragment) this).mCurrentMode == 165) {
            int i3 = Util.sWindowWidth;
            i = ((((int) (((float) i3) / 0.75f)) - i3) / 2) + getResources().getDimensionPixelSize(R.dimen.square_mode_bottom_cover_extra_margin);
        } else {
            i = getResources().getDimensionPixelSize(R.dimen.beauty_fragment_height);
        }
        ((ViewGroup.MarginLayoutParams) this.mViewPager.getLayoutParams()).height = i;
        ((ViewGroup.MarginLayoutParams) this.mBeautyExtraView.getLayoutParams()).height = i;
        if (this.mBeautySettingManager == null) {
            this.mBeautySettingManager = new BeautySettingManager();
        }
        this.mCurrentState = 1;
        this.mComponentRunningShine = DataRepository.dataItemRunning().getComponentRunningShine();
        initAdjustSeekBar();
        String currentType = this.mComponentRunningShine.getCurrentType();
        initShineType(currentType, false);
        initViewPager();
        setViewPagerPageByType(currentType);
    }

    private void initShineType(String str, boolean z) {
        if (!TextUtils.isEmpty(str)) {
            this.mComponentRunningShine.setCurrentType(str);
            if (isEyeLightShow()) {
                showHideEyeLighting(false);
            }
            char c2 = 65535;
            int hashCode = str.hashCode();
            if (hashCode != 57) {
                if (hashCode != 1568) {
                    switch (hashCode) {
                        case 49:
                            if (str.equals("1")) {
                                c2 = 1;
                                break;
                            }
                            break;
                        case 50:
                            if (str.equals("2")) {
                                c2 = 2;
                                break;
                            }
                            break;
                        case 51:
                            if (str.equals("3")) {
                                c2 = 3;
                                break;
                            }
                            break;
                        case 52:
                            if (str.equals("4")) {
                                c2 = 4;
                                break;
                            }
                            break;
                        case 53:
                            if (str.equals("5")) {
                                c2 = 5;
                                break;
                            }
                            break;
                        case 54:
                            if (str.equals("6")) {
                                c2 = 6;
                                break;
                            }
                            break;
                    }
                } else if (str.equals(ComponentRunningShine.SHINE_LIVE_BEAUTY)) {
                    c2 = 7;
                }
            } else if (str.equals("9")) {
                c2 = 0;
            }
            switch (c2) {
                case 0:
                    throw new RuntimeException("not allowed, see onMakeupItemSelected");
                case 1:
                case 2:
                    HashMap hashMap = new HashMap();
                    hashMap.put(MistatsConstants.BaseEvent.OPERATE_STATE, MistatsConstants.BeautyAttr.VALUE_BEAUTY_V1_BOTTOM_TAB);
                    MistatsWrapper.mistatEvent(MistatsConstants.BeautyAttr.KEY_BEAUTY_CLICK, hashMap);
                    this.mCurrentSettingBusiness = null;
                    setAdjustSeekBarVisible(false, true);
                    return;
                case 3:
                case 4:
                    this.mCurrentSettingBusiness = this.mBeautySettingManager.constructAndGetSetting(str, this.mComponentRunningShine.getTypeElementsBeauty());
                    HashMap hashMap2 = new HashMap();
                    hashMap2.put(MistatsConstants.BaseEvent.OPERATE_STATE, MistatsConstants.BeautyAttr.VALUE_BEAUTY_BOTTOM_TAB);
                    MistatsWrapper.mistatEvent(MistatsConstants.BeautyAttr.KEY_BEAUTY_CLICK, hashMap2);
                    return;
                case 5:
                    this.mCurrentSettingBusiness = this.mBeautySettingManager.constructAndGetSetting(str, this.mComponentRunningShine.getTypeElementsBeauty());
                    HashMap hashMap3 = new HashMap();
                    hashMap3.put(MistatsConstants.BaseEvent.OPERATE_STATE, MistatsConstants.BeautyAttr.VALUE_MAKEUP_BOTTOM_TAB);
                    MistatsWrapper.mistatEvent(MistatsConstants.BeautyAttr.KEY_BEAUTY_CLICK, hashMap3);
                    return;
                case 6:
                case 7:
                    this.mCurrentSettingBusiness = this.mBeautySettingManager.constructAndGetSetting(str, this.mComponentRunningShine.getTypeElementsBeauty());
                    return;
                default:
                    this.mCurrentSettingBusiness = null;
                    setAdjustSeekBarVisible(false, true);
                    return;
            }
        }
    }

    /* JADX INFO: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x00a6, code lost:
        if (r2.equals("1") != false) goto L_0x00aa;
     */
    private void initViewPager() {
        ArrayList arrayList = new ArrayList();
        Iterator<ComponentDataItem> it = this.mComponentRunningShine.getItems().iterator();
        while (true) {
            char c2 = 0;
            if (it.hasNext()) {
                String str = it.next().mValue;
                int hashCode = str.hashCode();
                switch (hashCode) {
                    case 49:
                        break;
                    case 50:
                        if (str.equals("2")) {
                            c2 = 1;
                            break;
                        }
                        c2 = 65535;
                        break;
                    case 51:
                        if (str.equals("3")) {
                            c2 = 2;
                            break;
                        }
                        c2 = 65535;
                        break;
                    case 52:
                        if (str.equals("4")) {
                            c2 = 3;
                            break;
                        }
                        c2 = 65535;
                        break;
                    case 53:
                        if (str.equals("5")) {
                            c2 = 4;
                            break;
                        }
                        c2 = 65535;
                        break;
                    case 54:
                        if (str.equals("6")) {
                            c2 = 5;
                            break;
                        }
                        c2 = 65535;
                        break;
                    case 55:
                        if (str.equals("7")) {
                            c2 = 6;
                            break;
                        }
                        c2 = 65535;
                        break;
                    default:
                        switch (hashCode) {
                            case 1567:
                                if (str.equals("10")) {
                                    c2 = 7;
                                    break;
                                }
                                c2 = 65535;
                                break;
                            case 1568:
                                if (str.equals(ComponentRunningShine.SHINE_LIVE_BEAUTY)) {
                                    c2 = '\b';
                                    break;
                                }
                                c2 = 65535;
                                break;
                            case 1569:
                                if (str.equals("12")) {
                                    c2 = '\t';
                                    break;
                                }
                                c2 = 65535;
                                break;
                            case 1570:
                                if (str.equals("13")) {
                                    c2 = '\n';
                                    break;
                                }
                                c2 = 65535;
                                break;
                            case 1571:
                                if (str.equals(ComponentRunningShine.SHINE_VIDEO_BOKEH_LEVEL)) {
                                    c2 = 11;
                                    break;
                                }
                                c2 = 65535;
                                break;
                            default:
                                c2 = 65535;
                                break;
                        }
                }
                switch (c2) {
                    case 0:
                        arrayList.add(new BeautyLevelFragment());
                        break;
                    case 1:
                        arrayList.add(new BeautySmoothLevelFragment());
                        break;
                    case 2:
                        arrayList.add(new MakeupParamsFragment());
                        break;
                    case 3:
                        arrayList.add(new RemodelingParamsFragment());
                        break;
                    case 4:
                        arrayList.add(new MakeupBeautyFragment());
                        break;
                    case 5:
                        arrayList.add(new BeautyBodyFragment());
                        break;
                    case 6:
                        arrayList.add(new FragmentFilter());
                        break;
                    case 7:
                        arrayList.add(new LiveBeautyFilterFragment());
                        break;
                    case '\b':
                        arrayList.add(new LiveBeautyModeFragment());
                        break;
                    case '\t':
                        arrayList.add(new FragmentLiveSticker());
                        break;
                    case '\n':
                        arrayList.add(new FragmentLiveSpeed());
                        break;
                    case 11:
                        arrayList.add(new BokehSmoothLevelFragment());
                        break;
                    default:
                        throw new RuntimeException("unknown beauty type");
                }
            } else {
                this.mBeautyPagerAdapter = new BaseFragmentPagerAdapter(getChildFragmentManager(), arrayList);
                this.mViewPager.setAdapter(this.mBeautyPagerAdapter);
                this.mViewPager.setOffscreenPageLimit(2);
                this.mViewPager.setFocusable(false);
                this.mViewPager.setOnTouchListener(d.INSTANCE);
                return;
            }
        }
    }

    private boolean isHiddenBeautyPanelOnShutter() {
        int i = ((BaseFragment) this).mCurrentMode;
        return i == 162 || i == 161 || i == 174 || i == 183 || i == 176;
    }

    /* access modifiers changed from: private */
    /* renamed from: onDismissFinished */
    public void Ja() {
        resetFragment();
        ModeProtocol.CameraAction cameraAction = (ModeProtocol.CameraAction) ModeCoordinatorImpl.getInstance().getAttachProtocol(161);
        if (!isHiddenBeautyPanelOnShutter() || (cameraAction != null && !cameraAction.isDoingAction() && !cameraAction.isRecording())) {
            resetTips();
        }
    }

    private void resetFragment() {
        this.mAdjustSeekBar.setVisibility(4);
        this.mViewPager.setAdapter(null);
        BaseFragmentPagerAdapter baseFragmentPagerAdapter = this.mBeautyPagerAdapter;
        if (baseFragmentPagerAdapter != null) {
            baseFragmentPagerAdapter.recycleFragmentList(getChildFragmentManager());
            this.mBeautyPagerAdapter = null;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:31:0x0088  */
    /* JADX WARNING: Removed duplicated region for block: B:33:? A[RETURN, SYNTHETIC] */
    private void resetTips() {
        ModeProtocol.ConfigChanges configChanges;
        ModeProtocol.BokehFNumberController bokehFNumberController;
        ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
        if (bottomPopupTips != null) {
            bottomPopupTips.reInitTipImage();
        }
        if (CameraSettings.shouldShowUltraWideStickyTip(((BaseFragment) this).mCurrentMode) && bottomPopupTips != null) {
            bottomPopupTips.directlyShowTips(13, R.string.ultra_wide_open_tip);
        }
        if (bottomPopupTips != null) {
            bottomPopupTips.updateTipBottomMargin(0, true);
        }
        showZoomTipImage();
        int i = ((BaseFragment) this).mCurrentMode;
        if (i == 163) {
            ModeProtocol.CameraModuleSpecial cameraModuleSpecial = (ModeProtocol.CameraModuleSpecial) ModeCoordinatorImpl.getInstance().getAttachProtocol(195);
            if (cameraModuleSpecial != null) {
                cameraModuleSpecial.showOrHideChip(true);
            }
        } else if (i != 165) {
            if (i == 167) {
                ModeProtocol.ManuallyAdjust manuallyAdjust = (ModeProtocol.ManuallyAdjust) ModeCoordinatorImpl.getInstance().getAttachProtocol(181);
                if (manuallyAdjust != null) {
                    manuallyAdjust.setManuallyLayoutVisible(true);
                }
            } else if (i == 171 && (bokehFNumberController = (ModeProtocol.BokehFNumberController) ModeCoordinatorImpl.getInstance().getAttachProtocol(210)) != null) {
                bokehFNumberController.showFNumberPanel(true);
            }
            configChanges = (ModeProtocol.ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164);
            if (configChanges == null) {
                configChanges.reCheckEyeLight();
                return;
            }
            return;
        }
        if (bottomPopupTips != null) {
            bottomPopupTips.updateLyingDirectHint(false, true);
        }
        configChanges = (ModeProtocol.ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164);
        if (configChanges == null) {
        }
    }

    private void setAdjustSeekBarVisible(boolean z, boolean z2) {
        if (z) {
            if (this.mAdjustSeekBar.getVisibility() != 0) {
                if (z2) {
                    Completable.create(new AlphaInOnSubscribe(this.mAdjustSeekBar)).subscribe();
                } else {
                    AlphaInOnSubscribe.directSetResult(this.mAdjustSeekBar);
                }
            }
        } else if (this.mAdjustSeekBar.getVisibility() == 0) {
            if (z2) {
                Completable.create(new AlphaOutOnSubscribe(this.mAdjustSeekBar)).subscribe();
            } else {
                AlphaOutOnSubscribe.directSetResult(this.mAdjustSeekBar);
            }
        }
    }

    private void setSeekBarMode(int i, int i2) {
        if (this.mCurrentSettingBusiness != null) {
            boolean z = false;
            if (i == 1) {
                this.mSeekBarMaxProgress = 100;
                this.mAdjustSeekBar.setProgressDrawable(getResources().getDrawable(R.drawable.seekbar_style));
            } else if (i == 2) {
                this.mSeekBarMaxProgress = 200;
                this.mAdjustSeekBar.setProgressDrawable(getResources().getDrawable(R.drawable.center_seekbar_style));
                i2 = 100;
                z = true;
            }
            this.mAdjustSeekBar.setCenterTwoWayMode(z);
            this.mAdjustSeekBar.setMax(this.mSeekBarMaxProgress);
            this.mAdjustSeekBar.setSeekBarPinProgress(i2);
            this.mAdjustSeekBar.setProgress(this.mCurrentSettingBusiness.getProgressByCurrentItem() * 1);
        }
    }

    private void setViewPagerPageByType(String str) {
        List<ComponentDataItem> items = this.mComponentRunningShine.getItems();
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).mValue.equals(str)) {
                this.mViewPager.setCurrentItem(i, false);
                return;
            }
        }
    }

    /* access modifiers changed from: private */
    public void showHideExtraLayout(boolean z, Fragment fragment, String str) {
        if (z) {
            FragmentUtils.addFragmentWithTag(getFragmentManager(), (int) R.id.beauty_extra, fragment, str);
        } else {
            FragmentUtils.removeFragmentByTag(getFragmentManager(), str);
        }
    }

    private void showHideEyeLighting(boolean z) {
        setAdjustSeekBarVisible(false, false);
        if (DataRepository.dataItemFeature().s_b_m() && CameraSettings.isSupportBeautyMakeup()) {
            if (this.mEyeLightFragment == null) {
                this.mEyeLightFragment = new BeautyEyeLightFragment();
            }
            ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
            if (z) {
                topAlert.alertTopHint(0, R.string.eye_light);
                BeautyEyeLightFragment beautyEyeLightFragment = this.mEyeLightFragment;
                showHideExtraLayout(true, beautyEyeLightFragment, beautyEyeLightFragment.getFragmentTag());
                extraEnterAnim();
                this.mEyeLightFragment.enterAnim(this.mBeautyExtraView, new ViewPropertyAnimatorListener() {
                    /* class com.android.camera.fragment.FragmentBeauty.AnonymousClass5 */

                    @Override // android.support.v4.view.ViewPropertyAnimatorListener
                    public void onAnimationCancel(View view) {
                    }

                    @Override // android.support.v4.view.ViewPropertyAnimatorListener
                    public void onAnimationEnd(View view) {
                        if (FragmentBeauty.this.mEyeLightFragment != null && FragmentBeauty.this.mEyeLightFragment.isAdded()) {
                            FragmentBeauty.this.mEyeLightFragment.userVisibleHint();
                        }
                    }

                    @Override // android.support.v4.view.ViewPropertyAnimatorListener
                    public void onAnimationStart(View view) {
                    }
                });
            } else {
                topAlert.alertTopHint(8, 0);
                this.mEyeLightFragment.exitAnim(this.mBeautyExtraView, new ViewPropertyAnimatorListener() {
                    /* class com.android.camera.fragment.FragmentBeauty.AnonymousClass6 */

                    @Override // android.support.v4.view.ViewPropertyAnimatorListener
                    public void onAnimationCancel(View view) {
                    }

                    @Override // android.support.v4.view.ViewPropertyAnimatorListener
                    public void onAnimationEnd(View view) {
                        FragmentBeauty fragmentBeauty = FragmentBeauty.this;
                        fragmentBeauty.showHideExtraLayout(false, fragmentBeauty.mEyeLightFragment, FragmentBeauty.this.mEyeLightFragment.getFragmentTag());
                        FragmentBeauty.this.mEyeLightFragment.userInvisibleHit();
                        ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
                        if (bottomPopupTips != null) {
                            bottomPopupTips.directlyHideTips();
                        }
                    }

                    @Override // android.support.v4.view.ViewPropertyAnimatorListener
                    public void onAnimationStart(View view) {
                    }
                });
                extraExitAnim();
            }
            this.mIsEyeLightShow = z;
        }
    }

    private void showZoomTipImage() {
        int i = ((BaseFragment) this).mCurrentMode;
        if (i != 165) {
            if (i != 166) {
                if (i != 183) {
                    switch (i) {
                        case 161:
                        case 162:
                            break;
                        case 163:
                            break;
                        default:
                            switch (i) {
                                case 173:
                                case 175:
                                    break;
                                case 174:
                                    break;
                                default:
                                    return;
                            }
                    }
                }
            } else if (!DataRepository.dataItemFeature().Fd()) {
                return;
            }
            if (!HybridZoomingSystem.IS_3_OR_MORE_SAT) {
                return;
            }
        }
        ModeProtocol.DualController dualController = (ModeProtocol.DualController) ModeCoordinatorImpl.getInstance().getAttachProtocol(182);
        if (dualController != null && !CameraSettings.isFrontCamera() && !CameraSettings.isUltraWideConfigOpen(((BaseFragment) this).mCurrentMode)) {
            if (CameraSettings.isUltraPixelOn() && !DataRepository.dataItemFeature().Hd()) {
                return;
            }
            if (!CameraSettings.isMacroModeEnabled(((BaseFragment) this).mCurrentMode) || DataRepository.dataItemFeature().c_19039_0x0005_eq_2()) {
                dualController.showZoomButton();
                ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
                if (topAlert != null) {
                    topAlert.clearAlertStatus();
                }
            }
        }
    }

    public void accept(@NonNull Integer num) throws Exception {
        IBeautySettingBusiness iBeautySettingBusiness = this.mCurrentSettingBusiness;
        if (iBeautySettingBusiness != null) {
            iBeautySettingBusiness.setProgressForCurrentItem(num.intValue());
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.MiBeautyProtocol
    public void clearBeauty() {
        IBeautySettingBusiness iBeautySettingBusiness = this.mCurrentSettingBusiness;
        if (iBeautySettingBusiness != null) {
            iBeautySettingBusiness.clearBeauty();
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.MiBeautyProtocol
    public void dismiss(int i) {
        hideBeautyLayout(6, i);
    }

    @Override // com.android.camera.fragment.BaseFragment
    public int getFragmentInto() {
        return 251;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public int getLayoutResourceId() {
        return R.layout.fragment_beauty;
    }

    @Override // com.android.camera.protocol.ModeProtocol.MiBeautyProtocol
    public List<TypeItem> getSupportedBeautyItems(@ComponentRunningShine.ShineType String str) {
        return this.mBeautySettingManager.constructAndGetSetting(str, this.mComponentRunningShine.getTypeElementsBeauty()).getSupportedTypeArray(str);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void initView(View view) {
        this.mIsRTL = Util.isLayoutRTL(getContext());
        this.mBeautyContent = view.findViewById(R.id.beauty_content);
        this.mBeautyExtraView = view.findViewById(R.id.beauty_extra);
        this.mViewPager = (NoScrollViewPager) view.findViewById(R.id.beauty_viewPager);
        this.mAdjustSeekBar = (SeekBarCompat) view.findViewById(R.id.beauty_adjust_seekbar);
        initShineType();
    }

    @Override // com.android.camera.protocol.ModeProtocol.MiBeautyProtocol
    public boolean isBeautyPanelShow() {
        return this.mCurrentState == 1;
    }

    @Override // com.android.camera.protocol.ModeProtocol.MiBeautyProtocol
    public boolean isEyeLightShow() {
        return this.mIsEyeLightShow;
    }

    @Override // com.android.camera.protocol.ModeProtocol.MakeupProtocol
    public boolean isSeekBarVisible() {
        SeekBarCompat seekBarCompat = this.mAdjustSeekBar;
        return seekBarCompat != null && seekBarCompat.getVisibility() == 0;
    }

    @Override // com.android.camera.fragment.BaseFragment, com.android.camera.animation.AnimationDelegate.AnimationResource
    public boolean needViewClear() {
        if (CameraSettings.isUltraPixelRearOn()) {
            return true;
        }
        return super.needViewClear();
    }

    @Override // com.android.camera.protocol.ModeProtocol.HandleBackTrace
    public boolean onBackEvent(int i) {
        int i2 = 3;
        if (i != 3) {
            i2 = i != 4 ? 2 : 1;
        }
        return hideBeautyLayout(i, i2);
    }

    public void onClick(View view) {
    }

    @Override // com.android.camera.protocol.ModeProtocol.MakeupProtocol
    public void onMakeupItemSelected(String str, boolean z) {
        if (str != "pref_eye_light_type_key") {
            IBeautySettingBusiness iBeautySettingBusiness = this.mCurrentSettingBusiness;
            if (iBeautySettingBusiness != null) {
                iBeautySettingBusiness.setCurrentType(str);
                this.mActiveProgress = this.mCurrentSettingBusiness.getProgressByCurrentItem() * 1;
                int defaultProgressByCurrentItem = this.mCurrentSettingBusiness.getDefaultProgressByCurrentItem() * 1;
                if (BeautyConstant.isSupportTwoWayAdjustable(str)) {
                    setSeekBarMode(2, defaultProgressByCurrentItem);
                } else {
                    setSeekBarMode(1, defaultProgressByCurrentItem);
                }
                setAdjustSeekBarVisible(true, true);
            }
        } else if (z) {
            showHideEyeLighting(!isEyeLightShow());
        } else {
            setAdjustSeekBarVisible(false, false);
        }
    }

    @Override // android.support.v4.app.Fragment
    public void onPause() {
        super.onPause();
        hideBeautyLayout(5, 2);
    }

    @Override // com.android.camera.fragment.BaseFragment, android.support.v4.app.Fragment
    public void onViewCreated(View view, @Nullable Bundle bundle) {
        ModeProtocol.CameraModuleSpecial cameraModuleSpecial;
        super.onViewCreated(view, bundle);
        if (((BaseFragment) this).mCurrentMode == 163 && (cameraModuleSpecial = (ModeProtocol.CameraModuleSpecial) ModeCoordinatorImpl.getInstance().getAttachProtocol(195)) != null) {
            cameraModuleSpecial.showOrHideChip(false);
        }
    }

    @Override // com.android.camera.fragment.BaseFragment, com.android.camera.animation.AnimationDelegate.AnimationResource
    public void provideAnimateElement(int i, List<Completable> list, int i2) {
        super.provideAnimateElement(i, list, i2);
        if (this.mCurrentState != -1) {
            onBackEvent(4);
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public Animation provideEnterAnimation(int i) {
        Animation wrapperAnimation = FragmentAnimationFactory.wrapperAnimation(167, 161);
        wrapperAnimation.setDuration(280);
        wrapperAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        return wrapperAnimation;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public Animation provideExitAnimation(int i) {
        return null;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void register(ModeProtocol.ModeCoordinator modeCoordinator) {
        super.register(modeCoordinator);
        registerBackStack(modeCoordinator, this);
        modeCoordinator.attachProtocol(194, this);
        modeCoordinator.attachProtocol(180, this);
        this.mIsEyeLightShow = false;
    }

    @Override // com.android.camera.protocol.ModeProtocol.MiBeautyProtocol
    public void resetBeauty() {
        IBeautySettingBusiness iBeautySettingBusiness = this.mCurrentSettingBusiness;
        if (iBeautySettingBusiness != null) {
            iBeautySettingBusiness.resetBeauty();
        }
    }

    @Override // com.android.camera.fragment.BaseFragment, com.android.camera.animation.AnimationDelegate.AnimationResource
    public void setClickEnable(boolean z) {
        List<Fragment> fragmentList;
        super.setClickEnable(z);
        BaseFragmentPagerAdapter baseFragmentPagerAdapter = this.mBeautyPagerAdapter;
        if (baseFragmentPagerAdapter != null && (fragmentList = baseFragmentPagerAdapter.getFragmentList()) != null) {
            for (Fragment fragment : fragmentList) {
                if (fragment instanceof BeautyLevelFragment) {
                    ((BeautyLevelFragment) fragment).setEnableClick(z);
                    return;
                }
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.MiBeautyProtocol
    public void show() {
        ModeProtocol.CameraModuleSpecial cameraModuleSpecial;
        if (this.mCurrentState != 1) {
            if (((BaseFragment) this).mCurrentMode == 163 && (cameraModuleSpecial = (ModeProtocol.CameraModuleSpecial) ModeCoordinatorImpl.getInstance().getAttachProtocol(195)) != null) {
                cameraModuleSpecial.showOrHideChip(false);
            }
            initShineType();
            Completable.create(new SlideInOnSubscribe(getView(), 80).setDurationTime(280).setInterpolator(new AccelerateDecelerateInterpolator())).subscribe();
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.MiBeautyProtocol
    public void switchShineType(String str, boolean z) {
        initShineType(str, z);
        setViewPagerPageByType(str);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void unRegister(ModeProtocol.ModeCoordinator modeCoordinator) {
        super.unRegister(modeCoordinator);
        unRegisterBackStack(modeCoordinator, this);
        modeCoordinator.detachProtocol(194, this);
        modeCoordinator.detachProtocol(180, this);
        Disposable disposable = this.mSeekBarDisposable;
        if (disposable != null && !disposable.isDisposed()) {
            this.mSeekBarDisposable.dispose();
        }
        this.mIsEyeLightShow = false;
    }
}
