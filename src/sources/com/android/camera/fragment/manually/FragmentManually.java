package com.android.camera.fragment.manually;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import com.android.camera.CameraSettings;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.animation.type.AlphaOutOnSubscribe;
import com.android.camera.animation.type.BaseOnSubScribe;
import com.android.camera.animation.type.SlideInOnSubscribe;
import com.android.camera.animation.type.SlideOutOnSubscribe;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.ComponentData;
import com.android.camera.data.data.config.ComponentManuallyDualLens;
import com.android.camera.data.data.config.ComponentManuallyET;
import com.android.camera.data.data.config.ComponentManuallyEV;
import com.android.camera.data.data.config.ComponentManuallyFocus;
import com.android.camera.data.data.config.ComponentManuallyISO;
import com.android.camera.data.data.config.ComponentManuallyWB;
import com.android.camera.data.data.config.DataItemConfig;
import com.android.camera.data.data.runing.ComponentRunningTiltValue;
import com.android.camera.fragment.BaseFragment;
import com.android.camera.fragment.FragmentUtils;
import com.android.camera.fragment.beauty.LinearLayoutManagerWrapper;
import com.android.camera.fragment.manually.adapter.ExtraRecyclerViewAdapter;
import com.android.camera.fragment.manually.adapter.ManuallyAdapter;
import com.android.camera.fragment.manually.adapter.ManuallySingleAdapter;
import com.android.camera.lib.compatibility.related.vibrator.ViberatorContext;
import com.android.camera.module.loader.camera2.Camera2DataContainer;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera.statistic.MistatsConstants;
import com.android.camera.statistic.MistatsWrapper;
import com.android.camera2.CameraCapabilities;
import com.mi.config.b;
import io.reactivex.Completable;
import java.util.ArrayList;
import java.util.List;

public class FragmentManually extends BaseFragment implements View.OnClickListener, ModeProtocol.HandleBackTrace, ModeProtocol.ManuallyAdjust, ManuallyListener {
    private RecyclerView.Adapter mAdapter;
    private int mCurrentAdjustType = -1;
    private ManuallyDecoration mDecoration;
    private FragmentManuallyExtra mFragmentManuallyExtra;
    /* access modifiers changed from: private */
    public ImageView mIndicatorButton;
    /* access modifiers changed from: private */
    public boolean mIsHiding;
    private boolean mIsSuperEISEnabled;
    private boolean mIsVideoRecording;
    private List<ComponentData> mManuallyComponents;
    /* access modifiers changed from: private */
    public ViewGroup mManuallyParent;
    /* access modifiers changed from: private */
    public RecyclerView mRecyclerView;
    private ViewGroup mRecyclerViewLayout;
    private View mSplitLine;

    private void adjustViewBackground(int i) {
        if (this.mIsVideoRecording) {
            this.mRecyclerViewLayout.setBackgroundResource(R.color.transparent);
            return;
        }
        int uiStyle = DataRepository.dataItemRunning().getUiStyle();
        if (uiStyle == 0) {
            this.mRecyclerViewLayout.setBackgroundResource(R.color.halfscreen_background);
        } else if (uiStyle == 1 || uiStyle == 3) {
            this.mRecyclerViewLayout.setBackgroundResource(R.color.fullscreen_background);
        }
    }

    private FragmentManuallyExtra getExtraFragment() {
        FragmentManuallyExtra fragmentManuallyExtra = this.mFragmentManuallyExtra;
        if (fragmentManuallyExtra == null || !fragmentManuallyExtra.isAdded()) {
            return null;
        }
        return this.mFragmentManuallyExtra;
    }

    private void hideTips() {
        ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
        if (bottomPopupTips != null) {
            bottomPopupTips.directlyHideTips();
        }
    }

    private void hideVideoFilter() {
        ModeProtocol.MiBeautyProtocol miBeautyProtocol = (ModeProtocol.MiBeautyProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(194);
        if (miBeautyProtocol != null && miBeautyProtocol.isBeautyPanelShow()) {
            miBeautyProtocol.dismiss(2);
        }
    }

    private void initManually() {
        initManuallyDataList();
        this.mRecyclerView.removeItemDecoration(this.mDecoration);
        this.mDecoration.setStyle(this.mManuallyComponents.size());
        this.mRecyclerView.addItemDecoration(this.mDecoration);
        ManuallyAdapter manuallyAdapter = new ManuallyAdapter(((BaseFragment) this).mCurrentMode, this, this.mManuallyComponents);
        this.mRecyclerView.getLayoutParams().height = getResources().getDimensionPixelSize(R.dimen.settings_screen_height);
        FragmentManuallyExtra extraFragment = getExtraFragment();
        if (extraFragment != null) {
            extraFragment.updateData();
            manuallyAdapter.setSelectedTitle(extraFragment.getCurrentTitle());
        }
        this.mAdapter = manuallyAdapter;
    }

    private List<ComponentData> initManuallyDataList() {
        List<ComponentData> list = this.mManuallyComponents;
        if (list == null) {
            this.mManuallyComponents = new ArrayList();
        } else {
            list.clear();
        }
        DataItemConfig dataItemConfig = DataRepository.dataItemConfig();
        this.mManuallyComponents.add(dataItemConfig.getmComponentManuallyWB());
        if (b.Zl()) {
            ComponentManuallyFocus manuallyFocus = dataItemConfig.getManuallyFocus();
            CameraCapabilities currentCameraCapabilities = Camera2DataContainer.getInstance().getCurrentCameraCapabilities();
            if (currentCameraCapabilities != null) {
                manuallyFocus.setFixedFocusLens(!currentCameraCapabilities.isAFRegionSupported());
            }
            this.mManuallyComponents.add(manuallyFocus);
            this.mManuallyComponents.add(dataItemConfig.getmComponentManuallyET());
        }
        this.mManuallyComponents.add(dataItemConfig.getmComponentManuallyISO());
        this.mManuallyComponents.add(dataItemConfig.getComponentManuallyEV());
        if ((CameraSettings.isSupportedOpticalZoom() || DataRepository.dataItemFeature().isSupportUltraWide()) && !this.mIsVideoRecording) {
            this.mManuallyComponents.add(dataItemConfig.getManuallyDualLens());
        }
        return this.mManuallyComponents;
    }

    private int initRecyclerView(int i, ManuallyListener manuallyListener) {
        this.mCurrentAdjustType = i;
        if (i != 0) {
            if (i == 1) {
                this.mCurrentAdjustType = 1;
                initManually();
            } else if (i == 2) {
                this.mCurrentAdjustType = 2;
                initScene(manuallyListener);
            } else if (i == 3) {
                this.mCurrentAdjustType = 3;
                initTilt(manuallyListener);
            } else if (i == 4) {
                this.mCurrentAdjustType = 4;
                initManually();
            }
            return this.mRecyclerView.getLayoutParams().height;
        }
        this.mCurrentAdjustType = 0;
        this.mManuallyParent.setVisibility(4);
        return 0;
    }

    private void initScene(ManuallyListener manuallyListener) {
        this.mRecyclerView.removeItemDecoration(this.mDecoration);
        ExtraRecyclerViewAdapter extraRecyclerViewAdapter = new ExtraRecyclerViewAdapter(DataRepository.dataItemRunning().getComponentRunningSceneValue(), ((BaseFragment) this).mCurrentMode, manuallyListener, (int) (((float) getResources().getDisplayMetrics().widthPixels) / 5.5f));
        this.mRecyclerView.getLayoutParams().height = getResources().getDimensionPixelSize(R.dimen.manual_popup_layout_height);
        this.mAdapter = extraRecyclerViewAdapter;
    }

    private void initTilt(ManuallyListener manuallyListener) {
        ComponentRunningTiltValue componentRunningTiltValue = DataRepository.dataItemRunning().getComponentRunningTiltValue();
        this.mRecyclerView.removeItemDecoration(this.mDecoration);
        this.mDecoration.setStyle(componentRunningTiltValue.getItems().size());
        this.mRecyclerView.addItemDecoration(this.mDecoration);
        ManuallySingleAdapter manuallySingleAdapter = new ManuallySingleAdapter(componentRunningTiltValue, ((BaseFragment) this).mCurrentMode, manuallyListener, getResources().getDisplayMetrics().widthPixels / componentRunningTiltValue.getItems().size());
        this.mRecyclerView.getLayoutParams().height = getResources().getDimensionPixelSize(R.dimen.settings_screen_height);
        this.mAdapter = manuallySingleAdapter;
    }

    private void notifyTipsMargin(int i) {
        ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
        if (bottomPopupTips != null) {
            bottomPopupTips.updateTipBottomMargin(i, true);
        }
    }

    private void performFocusValueChangedViberator(String str, String str2) {
        if (!TextUtils.equals(str, str2)) {
            try {
                if (Integer.parseInt(str2) % 100 == 0) {
                    ViberatorContext.getInstance(getContext().getApplicationContext()).performFocusValueLargeChangedInManual();
                } else {
                    ViberatorContext.getInstance(getContext().getApplicationContext()).performFocusValueLightChangedInManual();
                }
            } catch (NumberFormatException e2) {
                e2.printStackTrace();
            }
        }
    }

    private void reInitManuallyLayout(int i, List<Completable> list) {
        boolean isMovieSolidOn = CameraSettings.isMovieSolidOn();
        if (this.mCurrentAdjustType != i || this.mIsSuperEISEnabled != isMovieSolidOn) {
            this.mIsSuperEISEnabled = isMovieSolidOn;
            if (i == 0) {
                initRecyclerView(0, this);
            } else if (i == 1) {
                initRecyclerView(1, this);
            } else if (i == 4) {
                initRecyclerView(4, this);
            }
            if (i != 0) {
                return;
            }
            if (list == null) {
                AlphaOutOnSubscribe.directSetResult(this.mIndicatorButton);
            } else if (this.mIndicatorButton.getVisibility() == 0) {
                list.add(Completable.create(new AlphaOutOnSubscribe(this.mIndicatorButton)));
            } else {
                list.add(Completable.create(new SlideOutOnSubscribe(this.mManuallyParent, 80)));
            }
        }
    }

    private void removeExtra() {
        if (this.mFragmentManuallyExtra != null) {
            FragmentTransaction beginTransaction = getChildFragmentManager().beginTransaction();
            beginTransaction.remove(this.mFragmentManuallyExtra);
            beginTransaction.commitAllowingStateLoss();
        }
    }

    private void setManuallyLayoutViewVisible(int i) {
        FragmentUtils.removeFragmentByTag(getChildFragmentManager(), String.valueOf(254));
        if (i != 1) {
            if (i == 2) {
                this.mCurrentAdjustType = 0;
                if (this.mIndicatorButton.getVisibility() == 0) {
                    Completable.create(new AlphaOutOnSubscribe(this.mIndicatorButton)).subscribe();
                    AlphaOutOnSubscribe.directSetResult(this.mIndicatorButton);
                    return;
                }
                Completable.create(new SlideOutOnSubscribe(this.mManuallyParent, 80)).subscribe();
            } else if (i == 3) {
                this.mCurrentAdjustType = 0;
                if (this.mIndicatorButton.getVisibility() == 0) {
                    AlphaOutOnSubscribe.directSetResult(this.mIndicatorButton);
                } else {
                    SlideOutOnSubscribe.directSetResult(this.mManuallyParent, 80);
                }
            } else if (i == 4) {
                this.mCurrentAdjustType = 0;
                if (this.mIndicatorButton.getVisibility() == 0) {
                    AlphaOutOnSubscribe.directSetResult(this.mIndicatorButton);
                } else {
                    BaseOnSubScribe.setAnimateViewVisible(this.mManuallyParent, 4);
                }
            }
        } else if (this.mIndicatorButton.getVisibility() != 0) {
            Completable.create(new SlideInOnSubscribe(this.mManuallyParent, 80)).subscribe();
        }
    }

    private void updateEVState(int i) {
        FragmentManuallyExtra fragmentManuallyExtra;
        DataItemConfig dataItemConfig = DataRepository.dataItemConfig();
        ComponentManuallyEV componentManuallyEV = dataItemConfig.getComponentManuallyEV();
        ComponentManuallyET componentManuallyET = dataItemConfig.getmComponentManuallyET();
        ComponentManuallyISO componentManuallyISO = dataItemConfig.getmComponentManuallyISO();
        String componentValue = componentManuallyISO.getComponentValue(i);
        String componentValue2 = componentManuallyET.getComponentValue(i);
        boolean z = true;
        if (Long.parseLong(componentValue2) <= 125000000 && (componentValue2.equals(componentManuallyET.getDefaultValue(i)) || componentValue.equals(componentManuallyISO.getDefaultValue(i)))) {
            z = false;
        }
        componentManuallyEV.setDisabled(z);
        if (z && (fragmentManuallyExtra = this.mFragmentManuallyExtra) != null && fragmentManuallyExtra.getCurrentTitle() == R.string.pref_camera_manually_exposure_value_abbr) {
            removeExtra();
        }
    }

    @Override // com.android.camera.fragment.BaseFragment
    public int getFragmentInto() {
        return 247;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public int getLayoutResourceId() {
        return R.layout.fragment_manually;
    }

    @Override // com.android.camera.protocol.ModeProtocol.ManuallyAdjust
    public void hideManuallyParent() {
        float height = (float) this.mManuallyParent.getHeight();
        ViewCompat.animate(this.mManuallyParent).setStartDelay(0).translationY(height).setInterpolator(new OvershootInterpolator()).withEndAction(new Runnable() {
            /* class com.android.camera.fragment.manually.FragmentManually.AnonymousClass1 */

            public void run() {
                boolean unused = FragmentManually.this.mIsHiding = false;
                FragmentManually.this.mManuallyParent.setVisibility(4);
            }
        }).start();
        this.mIndicatorButton.setVisibility(0);
        ViewCompat.setTranslationY(this.mIndicatorButton, height);
        ViewCompat.setAlpha(this.mIndicatorButton, 0.0f);
        ViewCompat.animate(this.mIndicatorButton).setStartDelay(100).setDuration(300).alpha(1.0f).start();
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void initView(View view) {
        ((ViewGroup.MarginLayoutParams) view.getLayoutParams()).bottomMargin = Util.getBottomHeight();
        this.mIndicatorButton = (ImageView) view.findViewById(R.id.manually_indicator);
        this.mIndicatorButton.setOnClickListener(this);
        this.mManuallyParent = (ViewGroup) view.findViewById(R.id.manually_adjust_layout);
        this.mRecyclerViewLayout = (ViewGroup) this.mManuallyParent.findViewById(R.id.manually_recycler_view_layout);
        this.mRecyclerView = (RecyclerView) this.mRecyclerViewLayout.findViewById(R.id.manually_recycler_view);
        this.mRecyclerView.setFocusable(false);
        this.mSplitLine = this.mRecyclerViewLayout.findViewById(R.id.manually_recycler_split_line);
        this.mDecoration = new ManuallyDecoration(1, getResources().getColor(R.color.effect_divider_color));
        LinearLayoutManagerWrapper linearLayoutManagerWrapper = new LinearLayoutManagerWrapper(getContext(), "manually_recycler_view");
        linearLayoutManagerWrapper.setOrientation(0);
        this.mRecyclerView.setLayoutManager(linearLayoutManagerWrapper);
        adjustViewBackground(((BaseFragment) this).mCurrentMode);
        provideAnimateElement(((BaseFragment) this).mCurrentMode, null, 2);
    }

    @Override // com.android.camera.protocol.ModeProtocol.ManuallyAdjust
    public boolean isProVideoPannelHiding() {
        return this.mIsHiding;
    }

    @Override // com.android.camera.fragment.BaseFragment, com.android.camera.animation.AnimationDelegate.AnimationResource
    public void notifyAfterFrameAvailable(int i) {
        super.notifyAfterFrameAvailable(i);
        ModeProtocol.MiBeautyProtocol miBeautyProtocol = (ModeProtocol.MiBeautyProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(194);
        boolean z = miBeautyProtocol != null && miBeautyProtocol.isBeautyPanelShow();
        int i2 = ((BaseFragment) this).mCurrentMode;
        if ((i2 == 167 || i2 == 180) && this.mManuallyParent.getVisibility() != 0 && !z) {
            AlphaOutOnSubscribe.directSetResult(this.mIndicatorButton);
            Completable.create(new SlideInOnSubscribe(this.mManuallyParent, 80).setInterpolator(new AccelerateDecelerateInterpolator())).subscribe();
        }
    }

    @Override // com.android.camera.fragment.BaseFragment, com.android.camera.animation.AnimationDelegate.AnimationResource
    public void notifyDataChanged(int i, int i2) {
        super.notifyDataChanged(i, i2);
        adjustViewBackground(((BaseFragment) this).mCurrentMode);
        int i3 = this.mCurrentAdjustType;
        if ((i3 == 1 || i3 == 4) && this.mAdapter != null) {
            initManuallyDataList();
            this.mRecyclerView.setAdapter(this.mAdapter);
            this.mAdapter.notifyDataSetChanged();
        }
        FragmentManuallyExtra extraFragment = getExtraFragment();
        if (extraFragment != null) {
            extraFragment.notifyDataChanged(i, ((BaseFragment) this).mCurrentMode);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.HandleBackTrace
    public boolean onBackEvent(int i) {
        FragmentManuallyExtra fragmentManuallyExtra;
        ModeProtocol.MiBeautyProtocol miBeautyProtocol = (ModeProtocol.MiBeautyProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(194);
        boolean isBeautyPanelShow = miBeautyProtocol != null ? miBeautyProtocol.isBeautyPanelShow() : false;
        if (i == 3) {
            int i2 = ((BaseFragment) this).mCurrentMode;
            if (i2 == 180) {
                if (isBeautyPanelShow) {
                    miBeautyProtocol.dismiss(2);
                }
            } else if (i2 == 167) {
                return false;
            }
        }
        if (this.mManuallyParent.getVisibility() != 0 || this.mIsHiding) {
            return false;
        }
        if (i == 2) {
            MistatsWrapper.moduleUIClickEvent(MistatsConstants.ModuleName.MANUAL, MistatsConstants.Manual.MANUAL_EDIT_TAB_HIDE, (Object) 1);
        }
        if (i == 2 && (fragmentManuallyExtra = this.mFragmentManuallyExtra) != null && fragmentManuallyExtra.isAnimateIng()) {
            return false;
        }
        this.mIsHiding = true;
        hideManuallyParent();
        return true;
    }

    public void onClick(View view) {
        if (isEnableClick()) {
            if (view.getId() != R.id.manually_indicator) {
                ComponentData componentData = (ComponentData) view.getTag();
                int displayTitleString = componentData.getDisplayTitleString();
                this.mFragmentManuallyExtra = getExtraFragment();
                getResources().getDimensionPixelSize(R.dimen.manual_popup_layout_height);
                FragmentManuallyExtra fragmentManuallyExtra = this.mFragmentManuallyExtra;
                if (fragmentManuallyExtra == null) {
                    hideTips();
                    this.mFragmentManuallyExtra = new FragmentManuallyExtra();
                    this.mFragmentManuallyExtra.setmFragmentManually(this);
                    this.mFragmentManuallyExtra.updateRecordingState(this.mIsVideoRecording);
                    this.mFragmentManuallyExtra.setComponentData(componentData, ((BaseFragment) this).mCurrentMode, true, this);
                    FragmentManager childFragmentManager = getChildFragmentManager();
                    FragmentManuallyExtra fragmentManuallyExtra2 = this.mFragmentManuallyExtra;
                    FragmentUtils.addFragmentWithTag(childFragmentManager, (int) R.id.manually_popup, fragmentManuallyExtra2, fragmentManuallyExtra2.getFragmentTag());
                    ((ManuallyAdapter) this.mRecyclerView.getAdapter()).setSelectedTitle(displayTitleString);
                } else if (fragmentManuallyExtra.getCurrentTitle() == displayTitleString) {
                    this.mFragmentManuallyExtra.animateOut();
                    ((ManuallyAdapter) this.mRecyclerView.getAdapter()).setSelectedTitle(-1);
                } else {
                    hideTips();
                    this.mFragmentManuallyExtra.updateRecordingState(this.mIsVideoRecording);
                    this.mFragmentManuallyExtra.resetData(componentData);
                    ((ManuallyAdapter) this.mRecyclerView.getAdapter()).setSelectedTitle(displayTitleString);
                }
            } else {
                MistatsWrapper.moduleUIClickEvent(MistatsConstants.ModuleName.MANUAL, MistatsConstants.Manual.MANUAL_EDIT_TAB_SHOW, (Object) 1);
                hideTips();
                this.mManuallyParent.setVisibility(0);
                ViewCompat.animate(this.mManuallyParent).setStartDelay(100).translationY(0.0f).setInterpolator(new DecelerateInterpolator()).start();
                ViewCompat.animate(this.mIndicatorButton).setInterpolator(new DecelerateInterpolator()).alpha(0.0f).setDuration(100).withEndAction(new Runnable() {
                    /* class com.android.camera.fragment.manually.FragmentManually.AnonymousClass2 */

                    public void run() {
                        ViewCompat.setTranslationY(FragmentManually.this.mIndicatorButton, 0.0f);
                        FragmentManually.this.mIndicatorButton.setVisibility(4);
                    }
                }).start();
            }
        }
    }

    @Override // com.android.camera.fragment.BaseFragment, android.support.v4.app.Fragment
    public Animation onCreateAnimation(int i, boolean z, int i2) {
        return super.onCreateAnimation(i, z, i2);
    }

    @Override // com.android.camera.fragment.manually.ManuallyListener
    public void onManuallyDataChanged(ComponentData componentData, String str, String str2, boolean z, int i) {
        ModeProtocol.ManuallyValueChanged manuallyValueChanged;
        if (isEnableClick() && i == ((BaseFragment) this).mCurrentMode && (manuallyValueChanged = (ModeProtocol.ManuallyValueChanged) ModeCoordinatorImpl.getInstance().getAttachProtocol(174)) != null) {
            boolean z2 = true;
            switch (componentData.getDisplayTitleString()) {
                case R.string.pref_camera_iso_title_abbr:
                    manuallyValueChanged.onISOValueChanged((ComponentManuallyISO) componentData, str2);
                    break;
                case R.string.pref_camera_manually_exposure_value_abbr:
                    manuallyValueChanged.onEVValueChanged((ComponentManuallyEV) componentData, str2);
                    break;
                case R.string.pref_camera_whitebalance_title_abbr:
                    if (str2.equals("manual")) {
                        getExtraFragment().showCustomWB((ComponentManuallyWB) componentData, b.sm());
                    }
                    manuallyValueChanged.onWBValueChanged((ComponentManuallyWB) componentData, str2, z);
                    break;
                case R.string.pref_camera_zoom_mode_title_abbr:
                    manuallyValueChanged.onDualLensSwitch((ComponentManuallyDualLens) componentData, ((BaseFragment) this).mCurrentMode);
                    z2 = false;
                    break;
                case R.string.pref_manual_exposure_title_abbr:
                    manuallyValueChanged.onETValueChanged((ComponentManuallyET) componentData, str2);
                    break;
                case R.string.pref_qc_focus_position_title_abbr:
                    manuallyValueChanged.onFocusValueChanged((ComponentManuallyFocus) componentData, str, str2);
                    if (!this.mIsVideoRecording) {
                        performFocusValueChangedViberator(str, str2);
                        break;
                    }
                    break;
            }
            updateEVState(i);
            if (z2) {
                ((ModeProtocol.ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164)).reCheckParameterResetTip(false);
            } else {
                ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
                if (topAlert != null) {
                    topAlert.refreshExtraMenu();
                }
            }
            if (this.mRecyclerView.getAdapter() != null) {
                this.mRecyclerView.post(new Runnable() {
                    /* class com.android.camera.fragment.manually.FragmentManually.AnonymousClass3 */

                    public void run() {
                        FragmentManually.this.mRecyclerView.getAdapter().notifyDataSetChanged();
                    }
                });
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ManuallyAdjust
    public void onRecordingStart() {
        this.mIsVideoRecording = true;
        this.mRecyclerViewLayout.getLayoutParams().width = getResources().getDimensionPixelOffset(R.dimen.pro_video_recording_para_width);
        FragmentManuallyExtra fragmentManuallyExtra = this.mFragmentManuallyExtra;
        if (fragmentManuallyExtra != null) {
            fragmentManuallyExtra.updateRecordingState(this.mIsVideoRecording);
            if (R.string.pref_camera_zoom_mode_title_abbr == this.mFragmentManuallyExtra.getCurrentTitle()) {
                this.mFragmentManuallyExtra.resetData(DataRepository.dataItemConfig().getComponentManuallyEV());
            }
        }
        initRecyclerView(4, this);
        ((ManuallyAdapter) this.mAdapter).updateRecordingState(this.mIsVideoRecording);
        this.mRecyclerView.removeItemDecoration(this.mDecoration);
        notifyDataChanged(1, 180);
        this.mSplitLine.setVisibility(4);
    }

    @Override // com.android.camera.protocol.ModeProtocol.ManuallyAdjust
    public void onRecordingStop() {
        this.mIsVideoRecording = false;
        this.mRecyclerViewLayout.getLayoutParams().width = -1;
        FragmentManuallyExtra fragmentManuallyExtra = this.mFragmentManuallyExtra;
        if (fragmentManuallyExtra != null) {
            fragmentManuallyExtra.updateRecordingState(this.mIsVideoRecording);
        }
        initRecyclerView(4, this);
        ((ManuallyAdapter) this.mAdapter).updateRecordingState(this.mIsVideoRecording);
        this.mSplitLine.setVisibility(0);
        notifyDataChanged(1, 180);
    }

    @Override // com.android.camera.fragment.BaseFragment, com.android.camera.animation.AnimationDelegate.AnimationResource
    public void provideAnimateElement(int i, List<Completable> list, int i2) {
        super.provideAnimateElement(i, list, i2);
        int i3 = i != 167 ? i != 180 ? 0 : 4 : 1;
        if (i3 != 0) {
            updateEVState(i);
        }
        reInitManuallyLayout(i3, list);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public Animation provideEnterAnimation(int i) {
        return null;
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
        modeCoordinator.attachProtocol(181, this);
        registerBackStack(modeCoordinator, this);
    }

    @Override // com.android.camera.protocol.ModeProtocol.ManuallyAdjust
    public void resetManually() {
        if (this.mManuallyComponents != null && this.mAdapter != null) {
            FragmentManuallyExtra extraFragment = getExtraFragment();
            int currentTitle = extraFragment != null ? extraFragment.getCurrentTitle() : -1;
            ArrayList arrayList = new ArrayList();
            int i = -1;
            for (int i2 = 0; i2 < this.mManuallyComponents.size(); i2++) {
                ComponentData componentData = this.mManuallyComponents.get(i2);
                if (!(componentData instanceof ComponentManuallyDualLens)) {
                    if (componentData.isModified(((BaseFragment) this).mCurrentMode)) {
                        arrayList.add(componentData);
                    }
                    componentData.reset(((BaseFragment) this).mCurrentMode);
                    if (componentData.getDisplayTitleString() == currentTitle) {
                        i = i2;
                    }
                }
            }
            this.mAdapter.notifyDataSetChanged();
            if (!(currentTitle == -1 || i == -1)) {
                extraFragment.resetData(this.mManuallyComponents.get(i));
                ((ManuallyAdapter) this.mAdapter).setSelectedTitle(currentTitle);
            }
            ModeProtocol.ManuallyValueChanged manuallyValueChanged = (ModeProtocol.ManuallyValueChanged) ModeCoordinatorImpl.getInstance().getAttachProtocol(174);
            if (manuallyValueChanged != null) {
                manuallyValueChanged.resetManuallyParameters(arrayList);
                updateEVState(((BaseFragment) this).mCurrentMode);
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ManuallyAdjust
    public void setManuallyLayoutVisible(boolean z) {
        int i = 4;
        if (z) {
            int i2 = ((BaseFragment) this).mCurrentMode;
            if (i2 == 167) {
                i = 1;
            } else if (i2 != 180) {
                i = 0;
            }
            this.mCurrentAdjustType = i;
            if (((BaseFragment) this).mCurrentMode == 180) {
                ViewGroup viewGroup = (ViewGroup) this.mManuallyParent.getParent();
                if (viewGroup != null) {
                    viewGroup.setVisibility(0);
                    return;
                }
                return;
            }
            this.mManuallyParent.setVisibility(0);
            return;
        }
        this.mCurrentAdjustType = 0;
        if (((BaseFragment) this).mCurrentMode == 180) {
            ViewGroup viewGroup2 = (ViewGroup) this.mManuallyParent.getParent();
            if (viewGroup2 != null) {
                viewGroup2.setVisibility(4);
                return;
            }
            return;
        }
        this.mManuallyParent.setVisibility(4);
    }

    @Override // com.android.camera.protocol.ModeProtocol.ManuallyAdjust
    public int setManuallyVisible(int i, int i2, ManuallyListener manuallyListener) {
        int initRecyclerView = initRecyclerView(i, manuallyListener);
        RecyclerView.Adapter adapter = this.mAdapter;
        if (adapter != null) {
            this.mRecyclerView.setAdapter(adapter);
        }
        setManuallyLayoutViewVisible(i2);
        return initRecyclerView;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void unRegister(ModeProtocol.ModeCoordinator modeCoordinator) {
        super.unRegister(modeCoordinator);
        modeCoordinator.detachProtocol(181, this);
        unRegisterBackStack(modeCoordinator, this);
        removeExtra();
    }

    @Override // com.android.camera.protocol.ModeProtocol.ManuallyAdjust
    public int visibleHeight() {
        int i = this.mCurrentAdjustType;
        if (i == 0 || i == -1) {
            return 0;
        }
        return this.mIndicatorButton.getVisibility() == 0 ? this.mIndicatorButton.getHeight() : this.mManuallyParent.getHeight() + (getResources().getDimensionPixelSize(R.dimen.tips_margin_bottom_normal) / 2);
    }
}
