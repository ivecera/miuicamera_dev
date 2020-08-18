package com.android.camera.fragment.dual;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.annotation.TargetApi;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.text.SpannableStringBuilder;
import android.text.style.TextAppearanceSpan;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.camera.ActivityBase;
import com.android.camera.CameraSettings;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.animation.type.AlphaInOnSubscribe;
import com.android.camera.animation.type.AlphaOutOnSubscribe;
import com.android.camera.animation.type.TranslateYAlphaInOnSubscribe;
import com.android.camera.animation.type.TranslateYAlphaOutOnSubscribe;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.ComponentData;
import com.android.camera.fragment.BaseFragment;
import com.android.camera.fragment.manually.ManuallyListener;
import com.android.camera.fragment.manually.adapter.ExtraSlideFNumberAdapter;
import com.android.camera.lib.compatibility.related.vibrator.ViberatorContext;
import com.android.camera.log.Log;
import com.android.camera.module.loader.FunctionParseBeautyBodySlimCount;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera.statistic.MistatsConstants;
import com.android.camera.statistic.MistatsWrapper;
import com.android.camera.ui.HorizontalSlideView;
import d.h.a.G;
import d.h.a.c;
import d.h.a.v;
import d.h.a.w;
import io.reactivex.Completable;
import java.util.List;

public class FragmentDualCameraBokehAdjust extends BaseFragment implements View.OnClickListener, ManuallyListener, ModeProtocol.HandleBackTrace, ModeProtocol.BokehFNumberController {
    private static final int DEFAULT_SCROLL_DURATION = 250;
    public static final int FRAGMENT_INFO = 4091;
    private static final int HIDE_POPUP = 1;
    public static final String TAG = "BokehAdjust";
    private int mBokehFButtonHeight;
    private int mCurrentState = -1;
    private TextAppearanceSpan mDigitsTextStyle;
    /* access modifiers changed from: private */
    public View mDualBokehFButton;
    private ViewGroup mDualParentLayout;
    private TextView mFNumberTextView;
    private Handler mHandler = new Handler() {
        /* class com.android.camera.fragment.dual.FragmentDualCameraBokehAdjust.AnonymousClass1 */

        public void handleMessage(Message message) {
            if (message.what == 1) {
                FragmentDualCameraBokehAdjust.this.onBackEvent(5);
            }
        }
    };
    private ViewGroup mHorizontalSlideLayout;
    private HorizontalSlideView mHorizontalSlideView;
    /* access modifiers changed from: private */
    public ImageView mImageIndicator;
    private HorizontalSlideView.OnItemSelectListener mItemSelectListener = new HorizontalSlideView.OnItemSelectListener() {
        /* class com.android.camera.fragment.dual.FragmentDualCameraBokehAdjust.AnonymousClass3 */
        private int oldIndex = -1;

        @Override // com.android.camera.ui.HorizontalSlideView.OnItemSelectListener
        public void onItemSelect(HorizontalSlideView horizontalSlideView, int i) {
            if (this.oldIndex != i && SystemClock.elapsedRealtime() - FragmentDualCameraBokehAdjust.this.mLastTs > 250) {
                FragmentDualCameraBokehAdjust.this.playSound(horizontalSlideView);
                ViberatorContext.getInstance(FragmentDualCameraBokehAdjust.this.getContext().getApplicationContext()).performBokehAdjust();
            }
            this.oldIndex = i;
        }
    };
    /* access modifiers changed from: private */
    public long mLastTs = 0;
    private boolean mShowImageIndicator = false;
    private int mSlideLayoutHeight;
    private ExtraSlideFNumberAdapter mSlidingAdapter;
    private TextAppearanceSpan mSnapStyle;
    private SpannableStringBuilder mStringBuilder;
    private HorizontalSlideView.OnTabListener mTabListener = new HorizontalSlideView.OnTabListener() {
        /* class com.android.camera.fragment.dual.FragmentDualCameraBokehAdjust.AnonymousClass4 */

        @Override // com.android.camera.ui.HorizontalSlideView.OnTabListener
        public void onTab(View view) {
            long unused = FragmentDualCameraBokehAdjust.this.mLastTs = SystemClock.elapsedRealtime();
            FragmentDualCameraBokehAdjust.this.playSound(view);
        }
    };
    private TextAppearanceSpan mXTextStyle;
    /* access modifiers changed from: private */
    public AnimatorSet mZoomInAnimator;
    private AnimatorSet mZoomInOutAnimator;
    /* access modifiers changed from: private */
    public AnimatorSet mZoomOutAnimator;
    private View.OnTouchListener mZoomPopupTouchListener = new View.OnTouchListener() {
        /* class com.android.camera.fragment.dual.FragmentDualCameraBokehAdjust.AnonymousClass2 */
        private boolean mAnimated = false;

        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == 2) {
                if (!this.mAnimated) {
                    FragmentDualCameraBokehAdjust.this.mZoomInAnimator.start();
                    this.mAnimated = true;
                }
            } else if ((motionEvent.getAction() == 1 || motionEvent.getAction() == 3) && this.mAnimated) {
                FragmentDualCameraBokehAdjust.this.mZoomOutAnimator.start();
                this.mAnimated = false;
            }
            FragmentDualCameraBokehAdjust.this.sendHideMessage();
            return false;
        }
    };

    private void adjustViewBackground(View view, int i) {
        view.setBackgroundResource(R.color.fullscreen_background);
    }

    private void hideSlideView() {
        boolean z;
        boolean z2 = true;
        this.mShowImageIndicator = true;
        ExtraSlideFNumberAdapter extraSlideFNumberAdapter = this.mSlidingAdapter;
        if (extraSlideFNumberAdapter != null) {
            extraSlideFNumberAdapter.setEnable(false);
        }
        boolean isVisible = isVisible(this.mDualParentLayout);
        if (!isVisible) {
            Completable.create(new AlphaInOnSubscribe(this.mDualParentLayout).setDurationTime(200).setInterpolator(new G()).setStartDelayTime(140)).subscribe();
            z = true;
        } else {
            z = false;
        }
        if (isVisible(this.mHorizontalSlideLayout)) {
            if (!isVisible) {
                AlphaOutOnSubscribe.directSetResult(this.mHorizontalSlideLayout);
            } else {
                Completable.create(new TranslateYAlphaOutOnSubscribe(this.mHorizontalSlideLayout, this.mSlideLayoutHeight).setInterpolator(new OvershootInterpolator())).subscribe();
            }
            z = true;
        }
        if (!isVisible(this.mDualBokehFButton)) {
            z2 = z;
        } else if (!isVisible) {
            AlphaOutOnSubscribe.directSetResult(this.mDualBokehFButton);
        } else {
            Completable.create(new TranslateYAlphaOutOnSubscribe(this.mDualBokehFButton, this.mSlideLayoutHeight).setInterpolator(new OvershootInterpolator())).subscribe();
        }
        if (DataRepository.dataItemGlobal().isNormalIntent()) {
            Completable.create(new TranslateYAlphaInOnSubscribe(this.mImageIndicator, this.mHorizontalSlideLayout.getVisibility() != 8 ? this.mSlideLayoutHeight : 0).setInterpolator(new OvershootInterpolator())).subscribe();
        }
        if (z2) {
            notifyTipsMargin(0);
            ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
            if (bottomPopupTips != null) {
                bottomPopupTips.reInitTipImage();
            }
        }
    }

    private void initSlideFNumberView() {
        this.mSlidingAdapter = new ExtraSlideFNumberAdapter(getContext(), ((BaseFragment) this).mCurrentMode, this);
        this.mHorizontalSlideView.setOnPositionSelectListener(this.mSlidingAdapter);
        this.mHorizontalSlideView.setJustifyEnabled(true);
        this.mHorizontalSlideView.setDrawAdapter(this.mSlidingAdapter);
        this.mHorizontalSlideView.setSelection(this.mSlidingAdapter.mapFNumberToPosition(CameraSettings.readFNumber()), true);
        resetFNumber();
    }

    private boolean isVisible(View view) {
        return view.getVisibility() == 0 && view.getAlpha() != 0.0f;
    }

    private void notifyTipsMargin(int i) {
        ((ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175)).updateTipBottomMargin(i, true);
    }

    /* access modifiers changed from: private */
    public void playSound(View view) {
        if (canProvide()) {
            ((ActivityBase) view.getContext()).playCameraSound(6, 0.5f);
        }
    }

    private void requestFNumber(String str) {
        ModeProtocol.CameraAction cameraAction = (ModeProtocol.CameraAction) ModeCoordinatorImpl.getInstance().getAttachProtocol(161);
        if (cameraAction == null || !cameraAction.isDoingAction()) {
            ModeProtocol.ManuallyValueChanged manuallyValueChanged = (ModeProtocol.ManuallyValueChanged) ModeCoordinatorImpl.getInstance().getAttachProtocol(174);
            if (manuallyValueChanged != null) {
                manuallyValueChanged.onBokehFNumberValueChanged(str);
            }
            setFNumberText();
            return;
        }
        Log.d(TAG, "new f number is ignored: " + str);
    }

    private void resetFNumber() {
        setFNumberText();
    }

    /* access modifiers changed from: private */
    public void sendHideMessage() {
        this.mHandler.removeMessages(1);
        this.mHandler.sendEmptyMessageDelayed(1, FunctionParseBeautyBodySlimCount.TIP_INTERVAL_TIME);
    }

    @TargetApi(21)
    private void setFNumberText() {
        this.mStringBuilder.clear();
        String readFNumber = CameraSettings.readFNumber();
        Util.appendInApi26(this.mStringBuilder, readFNumber, this.mDigitsTextStyle, 33);
        if (Util.isAccessible()) {
            this.mDualBokehFButton.setContentDescription(getString(R.string.accessibility_bokeh_level, readFNumber));
            this.mDualBokehFButton.postDelayed(new Runnable() {
                /* class com.android.camera.fragment.dual.FragmentDualCameraBokehAdjust.AnonymousClass6 */

                public void run() {
                    if (FragmentDualCameraBokehAdjust.this.isAdded()) {
                        FragmentDualCameraBokehAdjust.this.mDualBokehFButton.sendAccessibilityEvent(4);
                    }
                }
            }, 3000);
        }
        this.mFNumberTextView.setText(this.mStringBuilder);
    }

    private void showSlideView() {
        ModeProtocol.MiBeautyProtocol miBeautyProtocol = (ModeProtocol.MiBeautyProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(194);
        if (miBeautyProtocol == null || !miBeautyProtocol.isBeautyPanelShow()) {
            this.mSlidingAdapter.setEnable(true);
            AlphaInOnSubscribe.directSetResult(this.mDualParentLayout);
            this.mHorizontalSlideLayout.setVisibility(0);
            this.mHorizontalSlideLayout.setTranslationY((float) this.mSlideLayoutHeight);
            Completable.create(new TranslateYAlphaInOnSubscribe(this.mHorizontalSlideLayout, 0).setInterpolator(new DecelerateInterpolator())).subscribe();
            this.mDualBokehFButton.setRotation((float) ((BaseFragment) this).mDegree);
            this.mDualBokehFButton.setTranslationY((float) (this.mSlideLayoutHeight + this.mBokehFButtonHeight));
            Completable.create(new TranslateYAlphaInOnSubscribe(this.mDualBokehFButton, this.mBokehFButtonHeight).setInterpolator(new c())).subscribe();
            notifyTipsMargin(this.mSlideLayoutHeight);
            ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
            if (bottomPopupTips != null) {
                bottomPopupTips.directHideTipImage();
                bottomPopupTips.directShowOrHideLeftTipImage(false);
            }
            this.mImageIndicator.setRotation((float) ((BaseFragment) this).mDegree);
            this.mImageIndicator.setTranslationY((float) this.mSlideLayoutHeight);
            Completable.create(new TranslateYAlphaOutOnSubscribe(this.mImageIndicator, 0).setInterpolator(new c())).subscribe();
            this.mShowImageIndicator = false;
            return;
        }
        Log.v(TAG, "beauty panel shown. do not show the slide view.");
    }

    @Override // com.android.camera.protocol.ModeProtocol.BokehFNumberController
    public int getBokehFButtonHeight() {
        if (this.mCurrentState != -1 && isVisible(this.mDualParentLayout)) {
            return this.mBokehFButtonHeight;
        }
        return 0;
    }

    @Override // com.android.camera.fragment.BaseFragment
    public int getFragmentInto() {
        return 4091;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public int getLayoutResourceId() {
        return R.layout.fragment_dual_camera_bokeh_adjust;
    }

    @Override // com.android.camera.protocol.ModeProtocol.BokehFNumberController
    public void hideFNumberPanel(boolean z) {
        if (this.mCurrentState != -1) {
            this.mCurrentState = -1;
            if (z) {
                hideSlideView();
                Completable.create(new AlphaOutOnSubscribe(this.mDualParentLayout).targetGone()).subscribe();
                return;
            }
            this.mDualParentLayout.setVisibility(8);
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void initView(View view) {
        this.mStringBuilder = new SpannableStringBuilder();
        ((ViewGroup.MarginLayoutParams) view.getLayoutParams()).bottomMargin = Util.getBottomHeight();
        this.mDualParentLayout = (ViewGroup) view.findViewById(R.id.bokeh_adjust_layout_parent);
        this.mDualBokehFButton = view.findViewById(R.id.dual_camera_bokeh_button);
        this.mFNumberTextView = (TextView) this.mDualBokehFButton.findViewById(R.id.f_number);
        this.mImageIndicator = (ImageView) view.findViewById(R.id.dual_camera_bokeh_indicator);
        this.mHorizontalSlideLayout = (ViewGroup) view.findViewById(R.id.dual_camera_bokeh_slideview_layout);
        this.mHorizontalSlideView = (HorizontalSlideView) this.mHorizontalSlideLayout.findViewById(R.id.dual_camera_bokeh_slideview);
        this.mHorizontalSlideView.setOnTouchListener(this.mZoomPopupTouchListener);
        this.mHorizontalSlideView.setOnItemSelectListener(this.mItemSelectListener);
        this.mHorizontalSlideView.setOnTabListener(this.mTabListener);
        this.mImageIndicator.setOnClickListener(this);
        if (Util.isAccessible()) {
            this.mImageIndicator.setContentDescription(getString(R.string.accessibility_bokeh_adjust));
            this.mImageIndicator.postDelayed(new Runnable() {
                /* class com.android.camera.fragment.dual.FragmentDualCameraBokehAdjust.AnonymousClass5 */

                public void run() {
                    if (FragmentDualCameraBokehAdjust.this.isAdded()) {
                        FragmentDualCameraBokehAdjust.this.mImageIndicator.sendAccessibilityEvent(4);
                    }
                }
            }, 3000);
        }
        this.mDualBokehFButton.setOnClickListener(this);
        this.mBokehFButtonHeight = getResources().getDimensionPixelSize(R.dimen.manually_indicator_layout_height);
        this.mDigitsTextStyle = new TextAppearanceSpan(getContext(), R.style.ZoomButtonDigitsTextStyle);
        this.mXTextStyle = new TextAppearanceSpan(getContext(), R.style.ZoomButtonXTextStyle);
        this.mZoomInOutAnimator = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(), R.animator.zoom_button_zoom_in_out);
        this.mZoomInOutAnimator.setTarget(this.mDualBokehFButton);
        this.mZoomInOutAnimator.setInterpolator(new w());
        this.mZoomInAnimator = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(), R.animator.zoom_button_zoom_in);
        this.mZoomInAnimator.setTarget(this.mDualBokehFButton);
        this.mZoomInAnimator.setInterpolator(new v());
        this.mZoomOutAnimator = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(), R.animator.zoom_button_zoom_out);
        this.mZoomOutAnimator.setTarget(this.mDualBokehFButton);
        this.mZoomOutAnimator.setInterpolator(new v());
        int i = getResources().getDisplayMetrics().widthPixels;
        float f2 = (float) i;
        this.mHorizontalSlideLayout.getLayoutParams().height = ((int) (((f2 / 0.75f) - f2) / 2.0f)) + getResources().getDimensionPixelSize(R.dimen.square_mode_bottom_cover_extra_margin);
        this.mSlideLayoutHeight = this.mHorizontalSlideLayout.getLayoutParams().height;
        adjustViewBackground(this.mHorizontalSlideLayout, ((BaseFragment) this).mCurrentMode);
        provideAnimateElement(((BaseFragment) this).mCurrentMode, null, 2);
    }

    @Override // com.android.camera.protocol.ModeProtocol.BokehFNumberController
    public boolean isFNumberVisible() {
        return this.mCurrentState == 1 && isVisible(this.mDualParentLayout) && (isVisible(this.mImageIndicator) || isVisible(this.mDualBokehFButton));
    }

    @Override // com.android.camera.fragment.BaseFragment, com.android.camera.animation.AnimationDelegate.AnimationResource
    public void notifyAfterFrameAvailable(int i) {
        super.notifyAfterFrameAvailable(i);
        if (((BaseFragment) this).mCurrentMode == 171) {
            if (!((ModeProtocol.ActionProcessing) ModeCoordinatorImpl.getInstance().getAttachProtocol(162)).isShowLightingView()) {
                ModeProtocol.MiBeautyProtocol miBeautyProtocol = (ModeProtocol.MiBeautyProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(194);
                if (miBeautyProtocol != null && miBeautyProtocol.isBeautyPanelShow()) {
                    return;
                }
            } else {
                return;
            }
        }
        provideAnimateElement(((BaseFragment) this).mCurrentMode, null, 2);
    }

    @Override // com.android.camera.fragment.BaseFragment, com.android.camera.animation.AnimationDelegate.AnimationResource
    public void notifyDataChanged(int i, int i2) {
        super.notifyDataChanged(i, i2);
        if (i == 3) {
            adjustViewBackground(this.mHorizontalSlideLayout, ((BaseFragment) this).mCurrentMode);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.HandleBackTrace
    public boolean onBackEvent(int i) {
        if (this.mShowImageIndicator || ((BaseFragment) this).mCurrentMode != 171 || !isVisible(this.mDualParentLayout)) {
            return false;
        }
        if (i == 3) {
            this.mHorizontalSlideView.stopScroll();
            return false;
        }
        hideSlideView();
        return true;
    }

    public void onClick(View view) {
        if (isEnableClick()) {
            ModeProtocol.CameraAction cameraAction = (ModeProtocol.CameraAction) ModeCoordinatorImpl.getInstance().getAttachProtocol(161);
            if (cameraAction == null || !cameraAction.isDoingAction()) {
                switch (view.getId()) {
                    case R.id.dual_camera_bokeh_button:
                        if (this.mDualBokehFButton.getAlpha() != 0.0f) {
                            hideSlideView();
                            return;
                        }
                        return;
                    case R.id.dual_camera_bokeh_indicator:
                        if (this.mImageIndicator.getAlpha() != 0.0f) {
                            MistatsWrapper.moduleUIClickEvent(MistatsConstants.ModuleName.PORTRAIT, MistatsConstants.PortraitAttr.VALUE_BOKEH_ADJUST_ENTRY, (Object) 1);
                            initSlideFNumberView();
                            sendHideMessage();
                            ModeProtocol.ActionProcessing actionProcessing = (ModeProtocol.ActionProcessing) ModeCoordinatorImpl.getInstance().getAttachProtocol(162);
                            ModeProtocol.ConfigChanges configChanges = (ModeProtocol.ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164);
                            if (!(actionProcessing == null || !actionProcessing.isShowFilterView() || configChanges == null)) {
                                configChanges.showOrHideFilter();
                            }
                            showSlideView();
                            return;
                        }
                        hideSlideView();
                        return;
                    default:
                        return;
                }
            }
        }
    }

    @Override // com.android.camera.fragment.manually.ManuallyListener
    public void onManuallyDataChanged(ComponentData componentData, String str, String str2, boolean z, int i) {
        if (!isInModeChanging()) {
            requestFNumber(str2);
        }
    }

    @Override // android.support.v4.app.Fragment
    public void onPause() {
        super.onPause();
        if (((BaseFragment) this).mCurrentMode == 171 && isVisible(this.mDualParentLayout) && isVisible(this.mHorizontalSlideLayout)) {
            hideSlideView();
        }
    }

    @Override // com.android.camera.fragment.BaseFragment, com.android.camera.animation.AnimationDelegate.AnimationResource
    public void provideAnimateElement(int i, List<Completable> list, int i2) {
        int i3;
        super.provideAnimateElement(i, list, i2);
        if (i == 171 && DataRepository.dataItemGlobal().isNormalIntent()) {
            this.mDualBokehFButton.setRotation((float) ((BaseFragment) this).mDegree);
            this.mImageIndicator.setRotation((float) ((BaseFragment) this).mDegree);
            i3 = 1;
        } else {
            i3 = -1;
        }
        int i4 = this.mCurrentState;
        if (i4 != i3) {
            this.mCurrentState = i3;
            if (i3 == -1) {
                if (isVisible(this.mDualParentLayout)) {
                    if (list == null) {
                        this.mDualParentLayout.setVisibility(8);
                    } else {
                        list.add(Completable.create(new AlphaOutOnSubscribe(this.mDualParentLayout).targetGone()));
                    }
                }
                this.mShowImageIndicator = false;
            } else if (i3 == 1) {
                AlphaInOnSubscribe.directSetResult(this.mDualParentLayout);
                if (this.mHorizontalSlideLayout.getVisibility() == 0) {
                    this.mHorizontalSlideLayout.setVisibility(4);
                }
                this.mDualBokehFButton.setVisibility(4);
                this.mImageIndicator.setTranslationY(this.mHorizontalSlideLayout.getVisibility() != 8 ? (float) this.mSlideLayoutHeight : 0.0f);
                resetFNumber();
                if (isVisible(this.mImageIndicator)) {
                    return;
                }
                if (list == null) {
                    AlphaInOnSubscribe.directSetResult(this.mImageIndicator);
                } else {
                    list.add(Completable.create(new AlphaInOnSubscribe(this.mImageIndicator)));
                }
            }
        } else if (1 == i4 && isVisible(this.mDualParentLayout)) {
            hideSlideView();
        }
    }

    @Override // com.android.camera.fragment.BaseFragment, com.android.camera.animation.AnimationDelegate.AnimationResource
    public void provideRotateItem(List<View> list, int i) {
        super.provideRotateItem(list, i);
        if (this.mDualBokehFButton.getVisibility() == 0) {
            list.add(this.mDualBokehFButton);
            list.add(this.mImageIndicator);
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void register(ModeProtocol.ModeCoordinator modeCoordinator) {
        super.register(modeCoordinator);
        modeCoordinator.attachProtocol(210, this);
        registerBackStack(modeCoordinator, this);
    }

    @Override // com.android.camera.protocol.ModeProtocol.BokehFNumberController
    public void showFNumberPanel(boolean z) {
        if (this.mCurrentState != 1 || this.mShowImageIndicator != z) {
            this.mCurrentState = 1;
            Log.d(TAG, "showFNumber");
            if (z) {
                hideSlideView();
            } else {
                showSlideView();
            }
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void unRegister(ModeProtocol.ModeCoordinator modeCoordinator) {
        super.unRegister(modeCoordinator);
        this.mHandler.removeCallbacksAndMessages(null);
        modeCoordinator.detachProtocol(210, this);
        unRegisterBackStack(modeCoordinator, this);
    }

    @Override // com.android.camera.protocol.ModeProtocol.BokehFNumberController
    public void updateFNumberValue() {
        setFNumberText();
    }

    @Override // com.android.camera.protocol.ModeProtocol.BokehFNumberController
    public int visibleHeight() {
        if (this.mCurrentState != -1 && isVisible(this.mDualParentLayout)) {
            return this.mShowImageIndicator ? this.mBokehFButtonHeight : this.mBokehFButtonHeight + this.mSlideLayoutHeight;
        }
        return 0;
    }
}
