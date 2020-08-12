package com.android.camera.fragment.top;

import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.view.ViewCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.camera.CameraSettings;
import com.android.camera.HybridZoomingSystem;
import com.android.camera.R;
import com.android.camera.RotateDialogController;
import com.android.camera.Util;
import com.android.camera.animation.type.AlphaInOnSubscribe;
import com.android.camera.animation.type.AlphaOutOnSubscribe;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.runing.ComponentRunningDocument;
import com.android.camera.effect.FilterInfo;
import com.android.camera.fragment.BaseFragment;
import com.android.camera.fragment.FragmentUtils;
import com.android.camera.fragment.manually.FragmentParameterDescription;
import com.android.camera.log.Log;
import com.android.camera.module.loader.camera2.Camera2DataContainer;
import com.android.camera.module.loader.camera2.Camera2OpenManager;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera.statistic.CameraStatUtils;
import com.android.camera.statistic.MistatsConstants;
import com.android.camera.statistic.MistatsWrapper;
import com.android.camera.ui.MutiStateButton;
import com.android.camera.ui.ToggleSwitch;
import com.android.camera2.Camera2Proxy;
import com.mi.config.b;
import io.reactivex.Completable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class FragmentTopAlert extends BaseFragment implements View.OnClickListener {
    public static final int FRAGMENT_INFO = 253;
    public static final long HINT_DELAY_TIME_3S = 3000;
    private static final String TAG = "FragmentTopAlert";
    private static int sPendingRecordingTimeState;
    /* access modifiers changed from: private */
    public ToggleSwitch mAiSceneSelectView;
    private Runnable mAlertAiDetectTipHitRunable = new TopAlertRunnable() {
        /* class com.android.camera.fragment.top.FragmentTopAlert.AnonymousClass6 */

        /* access modifiers changed from: package-private */
        @Override // com.android.camera.fragment.top.FragmentTopAlert.TopAlertRunnable
        public void runToSafe() {
            FragmentTopAlert fragmentTopAlert = FragmentTopAlert.this;
            fragmentTopAlert.removeViewToTipLayout(fragmentTopAlert.mRecommendTip);
        }
    };
    private long mAlertAiSceneSwitchHintTime;
    private AlertDialog mAlertDialog;
    private int mAlertImageType = -1;
    private TextView mAlertRecordingText;
    private Runnable mAlertTopHintHideRunnable = new Runnable() {
        /* class com.android.camera.fragment.top.FragmentTopAlert.AnonymousClass7 */

        public void run() {
            FragmentTopAlert fragmentTopAlert = FragmentTopAlert.this;
            fragmentTopAlert.removeViewToToastLayout(fragmentTopAlert.mPermanentTip);
        }
    };
    private AlphaAnimation mBlingAnimation;
    private LayoutTransition mCustomStubTransition;
    private LayoutTransition mCustomToastTransition;
    private MutiStateButton mDocumentStateButton;
    private LinkedHashMap<String, Integer> mDocumentStateMaps;
    private TextView mHandGestureTip;
    private Handler mHandler;
    private boolean mIsParameterDescriptionVisibleBeforeRecording;
    private boolean mIsParameterResetVisibleBeforeRecording;
    private boolean mIsVideoRecording;
    private TextView mLiveMusiHintText;
    private ImageView mLiveMusicClose;
    private LinearLayout mLiveMusicHintLayout;
    /* access modifiers changed from: private */
    public TextView mLiveShotTip;
    public final Runnable mLiveShotTipHideRunnable = new TopAlertRunnable() {
        /* class com.android.camera.fragment.top.FragmentTopAlert.AnonymousClass5 */

        /* access modifiers changed from: package-private */
        @Override // com.android.camera.fragment.top.FragmentTopAlert.TopAlertRunnable
        public void runToSafe() {
            FragmentTopAlert fragmentTopAlert = FragmentTopAlert.this;
            fragmentTopAlert.removeViewToToastLayout(fragmentTopAlert.mLiveShotTip);
        }
    };
    private TextView mLyingDirectHintText;
    /* access modifiers changed from: private */
    public TextView mMacroModeTip;
    public final Runnable mMacroModeTipHideRunnable = new TopAlertRunnable() {
        /* class com.android.camera.fragment.top.FragmentTopAlert.AnonymousClass3 */

        /* access modifiers changed from: package-private */
        @Override // com.android.camera.fragment.top.FragmentTopAlert.TopAlertRunnable
        public void runToSafe() {
            FragmentTopAlert fragmentTopAlert = FragmentTopAlert.this;
            fragmentTopAlert.removeViewToToastLayout(fragmentTopAlert.mMacroModeTip);
        }
    };
    private ImageView mManualParameterDescriptionTip;
    private ImageView mManualParameterResetTip;
    private View.OnClickListener mOnClickListener = new b(this);
    /* access modifiers changed from: private */
    public TextView mPermanentTip;
    /* access modifiers changed from: private */
    public TextView mRecommendTip;
    private boolean mShow;
    private Runnable mShowAction = new TopAlertRunnable() {
        /* class com.android.camera.fragment.top.FragmentTopAlert.AnonymousClass1 */

        @Override // com.android.camera.fragment.top.FragmentTopAlert.TopAlertRunnable
        public void runToSafe() {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, FragmentTopAlert.this.getResources().getDimensionPixelOffset(R.dimen.ai_scene_selector_layout_height));
            FragmentTopAlert fragmentTopAlert = FragmentTopAlert.this;
            fragmentTopAlert.addViewToTipLayout(fragmentTopAlert.mAiSceneSelectView, layoutParams, -1);
        }
    };
    private String mStateValueText = "";
    private boolean mStateValueTextFromLighting;
    private TextView mSubtitleTip;
    private TextView mSuperNightSeTip;
    /* access modifiers changed from: private */
    public TextView mToastAiSwitchTip;
    private ImageView mToastTipFlashHDR;
    private int mToastTipType = -1;
    private LinearLayout mToastTopTipLayout;
    private int mTopHintTextResource = 0;
    private LinearLayout mTopTipLayout;
    /* access modifiers changed from: private */
    public TextView mVideoBeautifyTip;
    public final Runnable mVideoBeautifyTipHideRunnable = new TopAlertRunnable() {
        /* class com.android.camera.fragment.top.FragmentTopAlert.AnonymousClass4 */

        /* access modifiers changed from: package-private */
        @Override // com.android.camera.fragment.top.FragmentTopAlert.TopAlertRunnable
        public void runToSafe() {
            FragmentTopAlert fragmentTopAlert = FragmentTopAlert.this;
            fragmentTopAlert.removeViewToToastLayout(fragmentTopAlert.mVideoBeautifyTip);
        }
    };
    private TextView mVideoUltraClearTip;
    public final Runnable mViewHideRunnable = new TopAlertRunnable() {
        /* class com.android.camera.fragment.top.FragmentTopAlert.AnonymousClass2 */

        /* access modifiers changed from: package-private */
        @Override // com.android.camera.fragment.top.FragmentTopAlert.TopAlertRunnable
        public void runToSafe() {
            FragmentTopAlert fragmentTopAlert = FragmentTopAlert.this;
            fragmentTopAlert.removeViewToToastLayout(fragmentTopAlert.mToastAiSwitchTip);
        }
    };
    private TextView mZoomTip;

    private abstract class TopAlertRunnable implements Runnable {
        private TopAlertRunnable() {
        }

        public void run() {
            if (FragmentTopAlert.this.isAdded()) {
                runToSafe();
            }
        }

        /* access modifiers changed from: package-private */
        public abstract void runToSafe();
    }

    static /* synthetic */ void a(ToggleSwitch toggleSwitch, boolean z) {
        ModeProtocol.ConfigChanges configChanges = (ModeProtocol.ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164);
        if (z) {
            if (configChanges != null) {
                configChanges.onConfigChanged(248);
            }
        } else if (configChanges != null) {
            configChanges.onConfigChanged(249);
        }
    }

    private void addViewToTipLayout(View view) {
        addViewToTipLayout(view, (LinearLayout.LayoutParams) null, -1);
    }

    /* access modifiers changed from: private */
    public void addViewToTipLayout(View view, LinearLayout.LayoutParams layoutParams, int i) {
        LinearLayout linearLayout;
        if (view != null && (linearLayout = this.mTopTipLayout) != null && linearLayout.indexOfChild(view) == -1) {
            if (this.mTopTipLayout.getLayoutTransition() != customStubViewTransition()) {
                this.mTopTipLayout.setLayoutTransition(customStubViewTransition());
            }
            if (i < 0) {
                try {
                    this.mTopTipLayout.addView(view);
                } catch (Exception unused) {
                    Log.w(TAG, "The specified child already has a parent. You must call removeView() on the child's parent first");
                }
            } else {
                this.mTopTipLayout.addView(view, i);
            }
            if (layoutParams == null) {
                layoutParams = new LinearLayout.LayoutParams(-2, -2);
            }
            view.setLayoutParams(layoutParams);
            checkChildMargin();
        }
    }

    private void addViewToTipLayout(View view, LinearLayout.LayoutParams layoutParams, Runnable runnable, Runnable runnable2) {
        LinearLayout linearLayout;
        if (view != null && (linearLayout = this.mTopTipLayout) != null && linearLayout.indexOfChild(view) == -1) {
            if (this.mTopTipLayout.getLayoutTransition() != customStubViewTransition()) {
                this.mTopTipLayout.setLayoutTransition(customStubViewTransition());
            }
            if (runnable != null) {
                runnable.run();
            }
            try {
                this.mTopTipLayout.addView(view);
            } catch (IllegalStateException unused) {
                Log.w(TAG, "The specified child already has a parent. You must call removeView() on the child's parent first");
            }
            if (layoutParams == null) {
                layoutParams = new LinearLayout.LayoutParams(-2, -2);
            }
            view.setLayoutParams(layoutParams);
            if (runnable2 != null) {
                runnable2.run();
            }
            checkChildMargin();
        }
    }

    private void addViewToTipLayout(View view, Runnable runnable, Runnable runnable2) {
        addViewToTipLayout(view, null, runnable, runnable2);
    }

    private void addViewToToastLayout(View view) {
        addViewToToastLayout(view, -1);
    }

    private void addViewToToastLayout(View view, int i) {
        LinearLayout linearLayout;
        if (view != 0 && (linearLayout = this.mToastTopTipLayout) != null && linearLayout.indexOfChild(view) == -1) {
            if (this.mTopTipLayout.getLayoutTransition() != customStubViewTransition()) {
                this.mTopTipLayout.setLayoutTransition(customStubViewTransition());
            }
            if (i < 0) {
                this.mToastTopTipLayout.addView(view);
            } else {
                this.mToastTopTipLayout.addView(view, i);
            }
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) view.getLayoutParams();
            layoutParams.width = -2;
            layoutParams.height = -2;
            view.setLayoutParams(layoutParams);
            if (this.mToastTopTipLayout.getChildCount() > 0) {
                this.mToastTopTipLayout.setVisibility(0);
            }
            checkChildMargin();
        }
    }

    private void alertAiSceneSelector(String str, String str2, int i, ToggleSwitch.OnCheckedChangeListener onCheckedChangeListener, boolean z) {
        if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
            this.mAiSceneSelectView.setTextOnAndOff(str, str2);
        }
        if (i == 0) {
            long currentTimeMillis = 3000 - (System.currentTimeMillis() - this.mAlertAiSceneSwitchHintTime);
            if (CameraSettings.getShaderEffect() == FilterInfo.FILTER_ID_NONE) {
                Handler handler = this.mHandler;
                Runnable runnable = this.mShowAction;
                if (currentTimeMillis < 0) {
                    currentTimeMillis = 0;
                }
                handler.postDelayed(runnable, currentTimeMillis);
            }
        } else {
            this.mTopTipLayout.removeCallbacks(this.mShowAction);
            removeViewToTipLayout(this.mAiSceneSelectView);
        }
        this.mAiSceneSelectView.setOnCheckedChangeListener(onCheckedChangeListener);
        this.mAiSceneSelectView.setChecked(z);
    }

    static /* synthetic */ void b(ToggleSwitch toggleSwitch, boolean z) {
        ModeProtocol.ConfigChanges configChanges = (ModeProtocol.ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164);
        if (z) {
            if (configChanges != null) {
                configChanges.onConfigChanged(246);
            }
        } else if (configChanges != null) {
            configChanges.onConfigChanged(247);
        }
    }

    private void checkChildMargin() {
        if (DataRepository.dataItemFeature().c_35893_0x0002() && this.mTopTipLayout != null && this.mDocumentStateButton != null && this.mToastTopTipLayout != null) {
            int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.top_tip_vertical_divider);
            int dimensionPixelSize2 = getResources().getDimensionPixelSize(R.dimen.top_tip_margin_top);
            boolean z = true;
            boolean z2 = this.mToastTopTipLayout.getChildCount() > 0;
            if (this.mTopTipLayout.indexOfChild(this.mDocumentStateButton) == -1) {
                z = false;
            }
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.mTopTipLayout.getLayoutParams();
            ViewGroup.MarginLayoutParams marginLayoutParams2 = (ViewGroup.MarginLayoutParams) this.mDocumentStateButton.getLayoutParams();
            if (!z) {
                marginLayoutParams.topMargin = getAlertTopMargin();
            } else if (z2) {
                marginLayoutParams.topMargin = getAlertTopMargin();
                marginLayoutParams2.topMargin = dimensionPixelSize2 - dimensionPixelSize;
            } else {
                marginLayoutParams.topMargin = getAlertTopMarginIDCardCase();
                marginLayoutParams2.topMargin = 0;
            }
        }
    }

    private LayoutTransition customStubViewTransition() {
        if (this.mCustomStubTransition == null) {
            this.mCustomStubTransition = new LayoutTransition();
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat((Object) null, "alpha", 0.0f, 1.0f);
            ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat((Object) null, "alpha", 1.0f, 0.0f);
            this.mCustomStubTransition.setStartDelay(2, 0);
            this.mCustomStubTransition.setDuration(2, 250);
            this.mCustomStubTransition.setAnimator(2, ofFloat);
            this.mCustomStubTransition.setStartDelay(3, 0);
            this.mCustomStubTransition.setDuration(3, 10);
            this.mCustomStubTransition.setAnimator(3, ofFloat2);
        }
        return this.mCustomStubTransition;
    }

    private LayoutTransition customToastLayoutTransition() {
        if (this.mCustomToastTransition == null) {
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat((Object) null, "alpha", 0.0f, 1.0f);
            this.mCustomToastTransition = new LayoutTransition();
            this.mCustomToastTransition.setStartDelay(2, 0);
            this.mCustomToastTransition.setDuration(2, 250);
            this.mCustomToastTransition.setAnimator(2, ofFloat);
            this.mCustomToastTransition.setStartDelay(3, 0);
            this.mCustomToastTransition.setDuration(3, 10);
        }
        return this.mCustomToastTransition;
    }

    private int getAlertTopMargin() {
        return Util.getDisplayRect(DataRepository.dataItemGlobal().getDisplayMode() == 2 ? 1 : 0).top + getResources().getDimensionPixelSize(R.dimen.video_ultra_tip_margin_top);
    }

    private int getAlertTopMarginIDCardCase() {
        return Util.getDisplayRect(DataRepository.dataItemGlobal().getDisplayMode() == 2 ? 1 : 0).top + getResources().getDimensionPixelSize(R.dimen.top_tip_vertical_divider);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:59:0x00cf, code lost:
        if (com.android.camera.HybridZoomingSystem.sDefaultMacroOpticalZoomRatio == 1.0f) goto L_0x00d1;
     */
    private String getZoomRatioTipText(boolean z) {
        int i;
        float decimal = HybridZoomingSystem.toDecimal(CameraSettings.readZoom());
        Camera2Proxy currentCamera2Device = Camera2OpenManager.getInstance().getCurrentCamera2Device();
        if (currentCamera2Device == null) {
            return null;
        }
        int id = currentCamera2Device.getId();
        if (decimal == 1.0f) {
            if (id == Camera2DataContainer.getInstance().getMainBackCameraId() || id == Camera2DataContainer.getInstance().getSATCameraId() || id == Camera2DataContainer.getInstance().getBokehCameraId() || id == Camera2DataContainer.getInstance().getUltraWideBokehCameraId() || id == Camera2DataContainer.getInstance().getFrontCameraId() || id == Camera2DataContainer.getInstance().getBokehFrontCameraId() || id == Camera2DataContainer.getInstance().getStandaloneMacroCameraId()) {
                return null;
            }
            if (DataRepository.dataItemFeature().c_28041_0x0006() && id == Camera2DataContainer.getInstance().getVideoSATCameraId()) {
                return null;
            }
            if (((HybridZoomingSystem.IS_2_SAT || (!HybridZoomingSystem.IS_3_OR_MORE_SAT && !CameraSettings.isSupportedOpticalZoom())) && id == Camera2DataContainer.getInstance().getUltraWideCameraId()) || (i = ((BaseFragment) this).mCurrentMode) == 167 || i == 180 || i == 166 || i == 179) {
                return null;
            }
            if (DataRepository.dataItemFeature().c_19039_0x0002() && ((BaseFragment) this).mCurrentMode == 172) {
                return null;
            }
            if (id == Camera2DataContainer.getInstance().getUltraWideCameraId()) {
                if (z) {
                }
            }
        }
        if (decimal == 0.6f && HybridZoomingSystem.IS_3_OR_MORE_SAT && id == Camera2DataContainer.getInstance().getUltraWideCameraId()) {
            return null;
        }
        if (id == Camera2DataContainer.getInstance().getAuxCameraId() && decimal <= HybridZoomingSystem.FLOAT_ZOOM_RATIO_TELE && ((BaseFragment) this).mCurrentMode != 167) {
            return null;
        }
        return "x " + decimal;
    }

    private void initDocumentStateButton() {
        this.mDocumentStateButton = (MutiStateButton) LayoutInflater.from(getContext()).inflate(R.layout.document_state_button, (ViewGroup) null);
        this.mDocumentStateMaps = new LinkedHashMap<>();
        this.mDocumentStateMaps.put("raw", Integer.valueOf((int) R.string.document_origin));
        this.mDocumentStateMaps.put(ComponentRunningDocument.DOCUMENT_BLACK_WHITE, Integer.valueOf((int) R.string.document_blackwhite));
        this.mDocumentStateMaps.put(ComponentRunningDocument.DOCUMENT_STRENGTHEN, Integer.valueOf((int) R.string.document_strengthen));
        this.mDocumentStateButton.initItems(this.mDocumentStateMaps, this.mOnClickListener);
    }

    private TextView initHandGestureTip() {
        return (TextView) LayoutInflater.from(getContext()).inflate(R.layout.hand_gesture_top_tip_layout, (ViewGroup) null);
    }

    private void initHandler() {
        this.mHandler = new Handler();
    }

    private TextView initHorizonDirectTipText() {
        return (TextView) LayoutInflater.from(getContext()).inflate(R.layout.top_tip_lying_direct_hint_layout, (ViewGroup) null);
    }

    private TextView initLiveShotTip() {
        return (TextView) LayoutInflater.from(getContext()).inflate(R.layout.live_shot_top_tip_layout, (ViewGroup) null);
    }

    private TextView initMacroModeTip() {
        return (TextView) LayoutInflater.from(getContext()).inflate(R.layout.macro_mode_top_tip_layout, (ViewGroup) null);
    }

    private LinearLayout initMusicTipText() {
        return (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.top_tip_music_layout, (ViewGroup) null);
    }

    private TextView initPermanentTip() {
        return (TextView) LayoutInflater.from(getContext()).inflate(R.layout.permanent_top_tip_layout, (ViewGroup) null);
    }

    private TextView initRecommendTipText() {
        return (TextView) LayoutInflater.from(getContext()).inflate(R.layout.recommend_top_tip_layout, (ViewGroup) null);
    }

    private TextView initSubtitleTip() {
        return (TextView) LayoutInflater.from(getContext()).inflate(R.layout.subtitle_top_tip_layout, (ViewGroup) null);
    }

    private void initToastTipLayout() {
        setToastTipLayoutParams();
        this.mToastTopTipLayout.setVisibility(8);
        this.mToastTipFlashHDR = initToastTopTipImage();
        this.mToastAiSwitchTip = initToastTopTipText();
        this.mSuperNightSeTip = initToastTopTipText();
    }

    private ImageView initToastTopTipImage() {
        return (ImageView) LayoutInflater.from(getContext()).inflate(R.layout.toast_top_tip_image_layout, (ViewGroup) null);
    }

    private TextView initToastTopTipText() {
        return (TextView) LayoutInflater.from(getContext()).inflate(R.layout.toast_top_tip_text_layout, (ViewGroup) null);
    }

    private ToggleSwitch initTopTipToggleSwitch() {
        return (ToggleSwitch) LayoutInflater.from(getContext()).inflate(R.layout.top_tip_toggleswitch_layout, (ViewGroup) null);
    }

    private TextView initVideoBeautifyTipText() {
        return (TextView) LayoutInflater.from(getContext()).inflate(R.layout.top_tip_video_beautify_hint_layout, (ViewGroup) null);
    }

    private TextView initZoomTipText() {
        return (TextView) LayoutInflater.from(getContext()).inflate(R.layout.zoom_top_tip_layout, (ViewGroup) null);
    }

    /* access modifiers changed from: private */
    public void removeViewToTipLayout(View view) {
        LinearLayout linearLayout;
        if (view != null && (linearLayout = this.mTopTipLayout) != null && linearLayout.indexOfChild(view) != -1) {
            if (this.mTopTipLayout.getLayoutTransition() != customStubViewTransition()) {
                this.mTopTipLayout.setLayoutTransition(customStubViewTransition());
            }
            this.mTopTipLayout.removeView(view);
            if (this.mTopTipLayout.getChildCount() <= 0) {
                this.mTopTipLayout.removeAllViews();
            }
            checkChildMargin();
        }
    }

    /* access modifiers changed from: private */
    public void removeViewToToastLayout(View view) {
        LinearLayout linearLayout;
        if (view != null && (linearLayout = this.mToastTopTipLayout) != null && linearLayout.indexOfChild(view) != -1) {
            if (this.mTopTipLayout.getLayoutTransition() != customStubViewTransition()) {
                this.mTopTipLayout.setLayoutTransition(customStubViewTransition());
            }
            this.mToastTopTipLayout.removeView(view);
            if (this.mToastTopTipLayout.getChildCount() <= 0) {
                this.mToastTopTipLayout.removeAllViews();
                setToastTipLayoutParams();
                this.mToastTopTipLayout.setVisibility(8);
            }
            checkChildMargin();
        }
    }

    public static void setPendingRecordingState(int i) {
        sPendingRecordingTimeState = i;
    }

    private void setToastTipLayoutParams() {
        LinearLayout linearLayout = this.mToastTopTipLayout;
        if (linearLayout != null) {
            linearLayout.setDividerDrawable(getResources().getDrawable(R.drawable.top_tip_horizontal_divider));
            this.mToastTopTipLayout.setShowDividers(2);
            this.mToastTopTipLayout.setLayoutTransition(customToastLayoutTransition());
            this.mToastTopTipLayout.setGravity(17);
        }
    }

    private void setViewMargin(View view, int i) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        marginLayoutParams.topMargin = i;
        view.setLayoutParams(marginLayoutParams);
        ViewCompat.setTranslationY(view, 0.0f);
    }

    private void showCloseConfirm() {
        if (this.mAlertDialog == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            this.mAlertDialog = builder.create();
            builder.setMessage(R.string.live_music_close_message);
            builder.setCancelable(false);
            builder.setPositiveButton(R.string.live_music_close_sure_message, new j(this));
            builder.setNegativeButton(R.string.live_music_close_cancel_message, new k(this));
            builder.show();
        }
    }

    private void showManualParameterResetDialog() {
        if (this.mAlertDialog == null) {
            this.mAlertDialog = RotateDialogController.showSystemAlertDialog(getContext(), null, getString(R.string.confirm_reset_manually_parameter_message), getString(R.string.reset_manually_parameter_confirmed), new h(this), getString(17039360), new f(this));
        }
    }

    private void updateAlertStatusView(boolean z) {
        if (!DataRepository.dataItemRunning().getComponentRunningLighting().getComponentValue(171).equals("0")) {
            if (isLandScape()) {
                starAnimatetViewGone(this.mZoomTip, z);
            } else if (!TextUtils.isEmpty(this.mStateValueText)) {
                startAnimateViewVisible(this.mZoomTip, z);
            }
        }
    }

    private void updateDocumentState(boolean z) {
        ComponentRunningDocument componentRunningDocument = DataRepository.dataItemRunning().getComponentRunningDocument();
        if (componentRunningDocument != null && this.mDocumentStateButton != null) {
            this.mDocumentStateButton.updateCurrentIndex(componentRunningDocument.getComponentValue(((BaseFragment) this).mCurrentMode), z);
        }
    }

    private void updateStateText(boolean z) {
        if (!TextUtils.isEmpty(this.mStateValueText)) {
            this.mStateValueTextFromLighting = z;
            this.mZoomTip.setText(this.mStateValueText);
            if (this.mTopTipLayout.indexOfChild(this.mZoomTip) != -1 && this.mZoomTip.getVisibility() == 0) {
                return;
            }
            if (!z || !isLandScape()) {
                addViewToTipLayout(this.mZoomTip);
                return;
            }
            return;
        }
        this.mStateValueTextFromLighting = false;
        removeViewToTipLayout(this.mZoomTip);
    }

    private void updateTopHint(int i, String str, long j) {
        this.mHandler.removeCallbacks(this.mAlertTopHintHideRunnable);
        if (i == 0) {
            this.mPermanentTip.setText(str);
            this.mPermanentTip.setContentDescription(str);
            addViewToToastLayout(this.mPermanentTip);
            if (j > 0) {
                this.mHandler.postDelayed(this.mAlertTopHintHideRunnable, j);
                return;
            }
            return;
        }
        removeViewToToastLayout(this.mPermanentTip);
    }

    public /* synthetic */ void Ua() {
        if (isAdded()) {
            this.mHandGestureTip.sendAccessibilityEvent(32768);
        }
    }

    public /* synthetic */ void Va() {
        if (isAdded()) {
            this.mLiveShotTip.sendAccessibilityEvent(32768);
        }
    }

    public /* synthetic */ void Wa() {
        if (isAdded()) {
            this.mToastAiSwitchTip.sendAccessibilityEvent(32768);
        }
    }

    public /* synthetic */ void Xa() {
        this.mAlertDialog = null;
        ModeProtocol.ManuallyAdjust manuallyAdjust = (ModeProtocol.ManuallyAdjust) ModeCoordinatorImpl.getInstance().getAttachProtocol(181);
        if (manuallyAdjust != null) {
            manuallyAdjust.resetManually();
        }
        ModeProtocol.ConfigChanges configChanges = (ModeProtocol.ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164);
        if (configChanges != null) {
            configChanges.resetMeter(((BaseFragment) this).mCurrentMode);
        }
        alertParameterResetTip(false, 8, 0, 0, false);
        CameraStatUtils.trackManuallyResetDialogOk();
    }

    public /* synthetic */ void Ya() {
        CameraStatUtils.trackManuallyResetDialogCancel();
        this.mAlertDialog = null;
    }

    public /* synthetic */ void Za() {
        this.mTopTipLayout.setLayoutTransition(null);
    }

    public /* synthetic */ void a(View view) {
        showCloseConfirm();
    }

    public void alertAiSceneSelector(int i) {
        alertAiSceneSelector(getResources().getString(R.string.text_ai_scene_selector_text_on), getResources().getString(R.string.text_ai_scene_selector_text_off), i, i == 0 ? c.INSTANCE : null, false);
    }

    /* JADX WARNING: Removed duplicated region for block: B:19:0x0039  */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x0043  */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0048 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x0049  */
    public void alertFlash(int i, String str) {
        boolean z;
        int i2;
        if (i == 0) {
            int hashCode = str.hashCode();
            if (hashCode != 49) {
                if (hashCode != 50) {
                    if (hashCode == 53 && str.equals("5")) {
                        z = true;
                        i2 = !z ? !z ? !z ? -1 : 5 : 2 : 1;
                        if (this.mAlertImageType == i2) {
                            this.mAlertImageType = i2;
                            if (CameraSettings.isFrontCamera() && b.El()) {
                                i2 = 1;
                            }
                            if (i2 == 1) {
                                this.mToastTipFlashHDR.setImageResource(R.drawable.ic_alert_flash);
                                addViewToToastLayout(this.mToastTipFlashHDR, 0);
                                this.mToastTipType = 2;
                                return;
                            } else if (i2 == 2) {
                                this.mToastTipFlashHDR.setImageResource(R.drawable.ic_alert_flash_torch);
                                addViewToToastLayout(this.mToastTipFlashHDR, 0);
                                this.mToastTipType = 2;
                                return;
                            } else if (i2 != 5) {
                                removeViewToToastLayout(this.mToastTipFlashHDR);
                                this.mToastTipType = -1;
                                return;
                            } else {
                                this.mToastTipFlashHDR.setImageResource(R.drawable.ic_alert_flash_back_soft_light);
                                addViewToToastLayout(this.mToastTipFlashHDR, 0);
                                this.mToastTipType = 2;
                                return;
                            }
                        } else {
                            return;
                        }
                    }
                } else if (str.equals("2")) {
                    z = true;
                    if (!z) {
                    }
                    if (this.mAlertImageType == i2) {
                    }
                }
            } else if (str.equals("1")) {
                z = false;
                if (!z) {
                }
                if (this.mAlertImageType == i2) {
                }
            }
            z = true;
            if (!z) {
            }
            if (this.mAlertImageType == i2) {
            }
        } else {
            int i3 = this.mAlertImageType;
            if (i3 == 2 || i3 == 1 || i3 == 5) {
                this.mAlertImageType = -1;
                removeViewToToastLayout(this.mToastTipFlashHDR);
                this.mToastTipType = -1;
            }
        }
    }

    public void alertHDR(int i, boolean z) {
        int i2 = 3;
        if (i == 0) {
            if (z) {
                i2 = 4;
            }
            if (this.mAlertImageType != i2) {
                this.mAlertImageType = i2;
                this.mToastTipFlashHDR.setImageResource(z ? R.drawable.ic_alert_hdr_live : R.drawable.ic_alert_hdr);
                addViewToToastLayout(this.mToastTipFlashHDR, 0);
                this.mToastTipType = 1;
                return;
            }
            return;
        }
        int i3 = this.mAlertImageType;
        if (i3 == 4 || i3 == 3) {
            this.mAlertImageType = -1;
            removeViewToToastLayout(this.mToastTipFlashHDR);
            this.mToastTipType = -1;
        }
    }

    public void alertHandGestureHint(int i) {
        String string = getString(i);
        this.mHandGestureTip.setText(string);
        this.mHandGestureTip.setContentDescription(string);
        addViewToToastLayout(this.mHandGestureTip);
        this.mHandler.postDelayed(new a(this), 300);
    }

    /* JADX WARNING: Removed duplicated region for block: B:14:0x001d  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x0022  */
    public void alertLightingHint(int i) {
        int i2;
        if (i != -1) {
            if (i == 3) {
                i2 = R.string.lighting_hint_too_close;
            } else if (i == 4) {
                i2 = R.string.lighting_hint_too_far;
            } else if (i == 5) {
                i2 = R.string.lighting_hint_needed;
            }
            if (i2 != -1) {
                this.mStateValueText = "";
            } else {
                this.mStateValueText = getResources().getString(i2);
            }
            updateStateText(true);
        }
        i2 = -1;
        if (i2 != -1) {
        }
        updateStateText(true);
    }

    public void alertLightingTitle(boolean z) {
        if (z) {
            this.mToastAiSwitchTip.setText(R.string.lighting_hint_title);
            this.mToastAiSwitchTip.setVisibility(0);
            addViewToToastLayout(this.mToastAiSwitchTip);
            this.mTopTipLayout.removeCallbacks(this.mViewHideRunnable);
            this.mHandler.postDelayed(this.mViewHideRunnable, 3000);
        } else if (this.mToastAiSwitchTip.getVisibility() != 8) {
            this.mTopTipLayout.removeCallbacks(this.mViewHideRunnable);
            removeViewToToastLayout(this.mToastAiSwitchTip);
        }
    }

    public void alertLiveShotHint(int i, int i2, long j) {
        String string = getString(i2);
        this.mLiveShotTip.setTag(Integer.valueOf(i));
        this.mLiveShotTip.setText(string);
        this.mLiveShotTip.setContentDescription(string);
        addViewToToastLayout(this.mLiveShotTip);
        this.mHandler.postDelayed(new m(this), 300);
        if (j > 0) {
            this.mHandler.removeCallbacks(this.mLiveShotTipHideRunnable);
            this.mHandler.postDelayed(this.mLiveShotTipHideRunnable, j);
        }
    }

    public void alertMacroModeHint(int i, int i2) {
        if (i == 0) {
            this.mMacroModeTip.setText(getString(i2));
            this.mMacroModeTip.setContentDescription(getString(i2));
            addViewToToastLayout(this.mMacroModeTip);
            this.mHandler.removeCallbacks(this.mMacroModeTipHideRunnable);
            this.mHandler.postDelayed(this.mMacroModeTipHideRunnable, 3000);
            return;
        }
        removeViewToToastLayout(this.mMacroModeTip);
    }

    public void alertMimojiFaceDetect(boolean z, int i) {
        if (z) {
            this.mToastAiSwitchTip.setText(i);
            this.mToastAiSwitchTip.setVisibility(0);
            addViewToToastLayout(this.mToastAiSwitchTip);
        } else if (this.mToastAiSwitchTip.getVisibility() != 8) {
            this.mTopTipLayout.removeCallbacks(this.mViewHideRunnable);
            removeViewToToastLayout(this.mToastAiSwitchTip);
        }
    }

    public void alertMoonSelector(String str, String str2, int i, boolean z) {
        alertAiSceneSelector(str, str2, i, i == 0 ? i.INSTANCE : null, z);
    }

    public void alertMusicClose(boolean z) {
        ImageView imageView = this.mLiveMusicClose;
        if (imageView == null) {
            return;
        }
        if (z) {
            imageView.setAlpha(1.0f);
            this.mLiveMusicClose.setClickable(true);
            return;
        }
        imageView.setAlpha(0.4f);
        this.mLiveMusicClose.setClickable(false);
    }

    public void alertMusicTip(int i, String str) {
        if (i != 0 || TextUtils.isEmpty(str)) {
            removeViewToTipLayout(this.mLiveMusicHintLayout);
            return;
        }
        this.mLiveMusiHintText.setText(str);
        addViewToTipLayout(this.mLiveMusicHintLayout);
    }

    public void alertParameterDescriptionTip(int i, boolean z) {
        if (i != 0 || !z) {
            this.mManualParameterDescriptionTip.setVisibility(i);
        } else {
            this.mManualParameterDescriptionTip.setVisibility(i);
            ViewCompat.setAlpha(this.mManualParameterDescriptionTip, 0.0f);
            ViewCompat.animate(this.mManualParameterDescriptionTip).alpha(1.0f).setDuration(320).start();
        }
        if (i == 8) {
            setViewMargin(this.mManualParameterResetTip, 0);
        } else {
            setViewMargin(this.mManualParameterResetTip, getResources().getDimensionPixelSize(R.dimen.parameter_reset_margin_top));
        }
    }

    public void alertParameterResetTip(boolean z, int i, @StringRes int i2, int i3, boolean z2) {
        if (this.mManualParameterResetTip.getVisibility() != i && !this.mIsVideoRecording) {
            if (i != 0 || !z2) {
                this.mManualParameterResetTip.setVisibility(i);
            } else {
                this.mManualParameterResetTip.setVisibility(i);
                ViewCompat.setAlpha(this.mManualParameterResetTip, 0.0f);
                ViewCompat.animate(this.mManualParameterResetTip).alpha(1.0f).setDuration(320).start();
            }
            if (i == 0) {
                this.mManualParameterResetTip.setContentDescription(getString(i2));
                if (!z) {
                    CameraStatUtils.trackManuallyResetShow();
                }
            }
        }
    }

    public void alertRecommendTipHint(int i, @StringRes int i2, long j) {
        if (i == 0) {
            this.mRecommendTip.setText(i2);
            this.mRecommendTip.setContentDescription(getString(i2));
            this.mTopTipLayout.removeCallbacks(this.mAlertAiDetectTipHitRunable);
            addViewToTipLayout(this.mRecommendTip);
            if (j >= 0) {
                this.mHandler.postDelayed(this.mAlertAiDetectTipHitRunable, j);
                return;
            }
            return;
        }
        this.mTopTipLayout.removeCallbacks(this.mAlertAiDetectTipHitRunable);
        removeViewToTipLayout(this.mRecommendTip);
    }

    public void alertSubtitleHint(int i, int i2) {
        if (i == 0) {
            this.mSubtitleTip.setText(getString(i2));
            this.mSubtitleTip.setContentDescription(getString(i2));
            addViewToToastLayout(this.mSubtitleTip);
            return;
        }
        removeViewToToastLayout(this.mSubtitleTip);
    }

    public void alertSuperNightSeTip(int i) {
        if (i == 0) {
            this.mSuperNightSeTip.setText(R.string.ai_scene_top_moon_off);
            addViewToToastLayout(this.mSuperNightSeTip);
            return;
        }
        removeViewToToastLayout(this.mSuperNightSeTip);
    }

    public void alertSwitchHint(int i, @StringRes int i2, long j) {
        alertSwitchHint(i, getString(i2), j);
    }

    public void alertSwitchHint(int i, String str, long j) {
        this.mToastAiSwitchTip.setTag(Integer.valueOf(i));
        this.mToastAiSwitchTip.setText(str);
        this.mToastAiSwitchTip.setContentDescription(str);
        addViewToToastLayout(this.mToastAiSwitchTip);
        this.mAlertAiSceneSwitchHintTime = System.currentTimeMillis();
        this.mHandler.postDelayed(new e(this), 300);
        if (j > 0) {
            this.mHandler.removeCallbacks(this.mViewHideRunnable);
            this.mHandler.postDelayed(this.mViewHideRunnable, j);
        }
    }

    public void alertTopHint(int i, @StringRes int i2) {
        alertTopHint(i, i2, 0);
    }

    public void alertTopHint(int i, @StringRes int i2, long j) {
        if (i2 > 0 && i == 0) {
            this.mTopHintTextResource = i2;
        } else if (i == 8) {
            this.mTopHintTextResource = 0;
        }
        String str = null;
        if (this.mTopHintTextResource == 0) {
            i = 8;
        } else {
            str = getString(i2);
        }
        updateTopHint(i, str, j);
    }

    public void alertTopHint(int i, String str) {
        if (TextUtils.isEmpty(str) && i == 0) {
            i = 8;
        }
        updateTopHint(i, str, 0);
    }

    public void alertUpdateValue(int i) {
        this.mStateValueText = "";
        boolean isMacroModeEnabled = CameraSettings.isMacroModeEnabled(((BaseFragment) this).mCurrentMode);
        ModeProtocol.DualController dualController = (ModeProtocol.DualController) ModeCoordinatorImpl.getInstance().getAttachProtocol(182);
        if (dualController == null || !dualController.isZoomVisible() || isMacroModeEnabled) {
            String zoomRatioTipText = getZoomRatioTipText(isMacroModeEnabled);
            if (zoomRatioTipText != null) {
                this.mStateValueText += zoomRatioTipText;
            }
            updateStateText(false);
        }
    }

    public void alertVideoBeautifyHint(int i, int i2) {
        if (i == 0) {
            this.mVideoBeautifyTip.setText(getString(i2));
            this.mVideoBeautifyTip.setContentDescription(getString(i2));
            addViewToToastLayout(this.mVideoBeautifyTip);
            this.mHandler.removeCallbacks(this.mVideoBeautifyTipHideRunnable);
            this.mHandler.postDelayed(this.mVideoBeautifyTipHideRunnable, 3000);
            return;
        }
        removeViewToToastLayout(this.mVideoBeautifyTip);
    }

    public void alertVideoUltraClear(int i, @StringRes int i2, int i3, boolean z) {
        if (this.mVideoUltraClearTip.getVisibility() != 8 || i != 8) {
            String string = getString(i2);
            if (i != 0 || !z) {
                this.mVideoUltraClearTip.setVisibility(i);
            } else {
                this.mVideoUltraClearTip.setVisibility(i);
                ViewCompat.setAlpha(this.mVideoUltraClearTip, 0.0f);
                ViewCompat.animate(this.mVideoUltraClearTip).alpha(1.0f).setDuration(320).start();
            }
            setViewMargin(this.mVideoUltraClearTip, i3);
            if (i == 0) {
                if (Util.isEnglishOrNum(string)) {
                    this.mVideoUltraClearTip.setTextSize(0, (float) getResources().getDimensionPixelSize(R.dimen.top_left_english_tip_size));
                } else {
                    this.mVideoUltraClearTip.setTextSize(0, (float) getResources().getDimensionPixelSize(R.dimen.top_left_chinese_tip_size));
                }
                this.mVideoUltraClearTip.setText(string);
                this.mVideoUltraClearTip.setContentDescription(string);
            }
        }
    }

    public /* synthetic */ void b(View view) {
        LinkedHashMap<String, Integer> linkedHashMap;
        String str = (String) view.getTag();
        if (str != null && (linkedHashMap = this.mDocumentStateMaps) != null && linkedHashMap.containsKey(str)) {
            DataRepository.dataItemRunning().getComponentRunningDocument().setComponentValue(((BaseFragment) this).mCurrentMode, str);
            CameraStatUtils.trackDocumentModeChanged(str);
            updateDocumentState(true);
        }
    }

    public void clear(boolean z) {
        clearAlertStatus();
        this.mAlertImageType = -1;
        int childCount = this.mToastTopTipLayout.getChildCount();
        ArrayList<View> arrayList = new ArrayList();
        for (int i = 0; i < childCount; i++) {
            View childAt = this.mToastTopTipLayout.getChildAt(i);
            Object tag = childAt.getTag();
            if (tag == null || !(tag == null || !(tag instanceof Integer) || ((Integer) tag).intValue() == 2)) {
                arrayList.add(childAt);
            }
            if (z) {
                arrayList.add(childAt);
            }
        }
        for (View view : arrayList) {
            this.mToastTopTipLayout.removeView(view);
        }
        if (this.mToastTopTipLayout.getChildCount() <= 0) {
            this.mToastTopTipLayout.removeAllViews();
            setToastTipLayoutParams();
            this.mToastTopTipLayout.setVisibility(8);
        }
        arrayList.clear();
        int childCount2 = this.mTopTipLayout.getChildCount();
        for (int i2 = 0; i2 < childCount2; i2++) {
            View childAt2 = this.mTopTipLayout.getChildAt(i2);
            if (i2 != 0) {
                arrayList.add(childAt2);
            } else {
                setToastTipLayoutParams();
            }
        }
        for (View view2 : arrayList) {
            this.mTopTipLayout.removeView(view2);
        }
        clearVideoUltraClear();
        ImageView imageView = this.mManualParameterResetTip;
        if (!(imageView == null || imageView.getVisibility() == 8)) {
            this.mManualParameterResetTip.setVisibility(8);
        }
        ImageView imageView2 = this.mManualParameterDescriptionTip;
        if (!(imageView2 == null || imageView2.getVisibility() == 8)) {
            this.mManualParameterDescriptionTip.setVisibility(8);
        }
        this.mToastTipType = -1;
    }

    public void clearAlertStatus() {
        this.mStateValueText = "";
        this.mStateValueTextFromLighting = false;
        removeViewToTipLayout(this.mZoomTip);
    }

    public void clearVideoUltraClear() {
        TextView textView = this.mVideoUltraClearTip;
        if (textView != null && textView.getVisibility() != 8) {
            this.mVideoUltraClearTip.setText("");
            this.mVideoUltraClearTip.setVisibility(8);
        }
    }

    @Override // com.android.camera.fragment.BaseFragment
    public int getFragmentInto() {
        return 253;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public int getLayoutResourceId() {
        return R.layout.fragment_top_alert;
    }

    public void hideSwitchHint() {
        removeViewToToastLayout(this.mToastAiSwitchTip);
        if (this.mTopTipLayout.getVisibility() == 0) {
            this.mTopTipLayout.removeCallbacks(this.mViewHideRunnable);
        }
    }

    public /* synthetic */ void i(DialogInterface dialogInterface, int i) {
        this.mAlertDialog = null;
        removeViewToTipLayout(this.mLiveMusicHintLayout);
        ModeProtocol.LiveAudioChanges liveAudioChanges = (ModeProtocol.LiveAudioChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(211);
        ModeProtocol.LiveRecordState liveRecordState = (ModeProtocol.LiveRecordState) ModeCoordinatorImpl.getInstance().getAttachProtocol(245);
        if (liveAudioChanges != null && liveRecordState != null && !liveRecordState.isRecording() && !liveRecordState.isRecordingPaused()) {
            liveAudioChanges.clearAudio();
            ((ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172)).updateConfigItem(245);
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void initView(View view) {
        initHandler();
        this.mAlertRecordingText = (TextView) view.findViewById(R.id.alert_recording_time_view);
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.mAlertRecordingText.getLayoutParams();
        marginLayoutParams.height = Util.sTopBarHeight;
        marginLayoutParams.topMargin = Util.sTopMargin;
        this.mVideoUltraClearTip = (TextView) view.findViewById(R.id.video_ultra_clear_tip);
        this.mManualParameterResetTip = (ImageView) view.findViewById(R.id.reset_manually_parameter_tip);
        this.mManualParameterResetTip.setOnClickListener(this);
        this.mManualParameterDescriptionTip = (ImageView) view.findViewById(R.id.manually_parameter_description_tip);
        this.mManualParameterDescriptionTip.setOnClickListener(this);
        setViewMargin((LinearLayout) this.mManualParameterDescriptionTip.getParent(), Util.getDisplayRect(0).top + getResources().getDimensionPixelSize(R.dimen.video_ultra_tip_margin_top));
        setViewMargin(this.mAlertRecordingText, Util.sTopMargin);
        ViewCompat.setAlpha(this.mAlertRecordingText, 0.0f);
        int i = sPendingRecordingTimeState;
        if (i != 0) {
            setRecordingTimeState(i);
            setPendingRecordingState(0);
        }
        this.mTopTipLayout = (LinearLayout) view.findViewById(R.id.top_tip_layout);
        this.mToastTopTipLayout = (LinearLayout) this.mTopTipLayout.findViewById(R.id.top_toast_layout);
        ViewGroup.MarginLayoutParams marginLayoutParams2 = (ViewGroup.MarginLayoutParams) this.mTopTipLayout.getLayoutParams();
        marginLayoutParams2.topMargin = getAlertTopMargin();
        marginLayoutParams2.height = (int) (((float) (Util.sWindowWidth * 4)) / 3.0f);
        this.mTopTipLayout.setLayoutParams(marginLayoutParams2);
        this.mTopTipLayout.setDividerDrawable(getResources().getDrawable(R.drawable.top_tip_vertical_divider));
        this.mTopTipLayout.setShowDividers(2);
        initToastTipLayout();
        this.mAiSceneSelectView = initTopTipToggleSwitch();
        this.mRecommendTip = initRecommendTipText();
        this.mZoomTip = initZoomTipText();
        updateAlertStatusView(false);
        this.mLiveMusicHintLayout = initMusicTipText();
        this.mLiveMusiHintText = (TextView) this.mLiveMusicHintLayout.findViewById(R.id.live_music_title_hint);
        this.mLiveMusicClose = (ImageView) this.mLiveMusicHintLayout.findViewById(R.id.live_music_close);
        this.mLiveMusicClose.setOnClickListener(new l(this));
        this.mPermanentTip = initPermanentTip();
        this.mSubtitleTip = initSubtitleTip();
        this.mLyingDirectHintText = initHorizonDirectTipText();
        this.mMacroModeTip = initMacroModeTip();
        this.mVideoBeautifyTip = initVideoBeautifyTipText();
        this.mLiveShotTip = initLiveShotTip();
        this.mHandGestureTip = initHandGestureTip();
    }

    public boolean isContainAlertRecommendTip(@StringRes int... iArr) {
        TextView textView;
        LinearLayout linearLayout = this.mTopTipLayout;
        if (!(linearLayout == null || (textView = this.mRecommendTip) == null || linearLayout.indexOfChild(textView) == -1 || iArr == null || iArr.length <= 0)) {
            for (int i : iArr) {
                if (i > 0 && getResources().getString(i).equals(this.mRecommendTip.getText())) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isCurrentRecommendTipText(int i) {
        if (i <= 0) {
            return false;
        }
        String string = getResources().getString(i);
        return !TextUtils.isEmpty(string) && isShowTopLayoutSpecifyTip(this.mRecommendTip) && string.equals(this.mRecommendTip.getText());
    }

    public boolean isHDRShowing() {
        ImageView imageView;
        LinearLayout linearLayout = this.mToastTopTipLayout;
        return linearLayout != null && (imageView = this.mToastTipFlashHDR) != null && this.mToastTipType == 1 && linearLayout.indexOfChild(imageView) > -1 && this.mToastTopTipLayout.getVisibility() == 0;
    }

    public boolean isShow() {
        return this.mShow;
    }

    public boolean isShowBacklightSelector() {
        LinearLayout linearLayout = this.mTopTipLayout;
        return (linearLayout == null || linearLayout.indexOfChild(this.mAiSceneSelectView) == -1 || !getResources().getString(R.string.text_ai_scene_selector_text_on).equals(this.mAiSceneSelectView.getTextOn())) ? false : true;
    }

    public boolean isShowMoonSelector() {
        LinearLayout linearLayout = this.mTopTipLayout;
        return (linearLayout == null || linearLayout.indexOfChild(this.mAiSceneSelectView) == -1 || !getResources().getString(R.string.ai_scene_top_tip).equals(this.mAiSceneSelectView.getTextOn())) ? false : true;
    }

    public boolean isShowTopLayoutSpecifyTip(View view) {
        LinearLayout linearLayout;
        return (view == null || (linearLayout = this.mTopTipLayout) == null || linearLayout.indexOfChild(view) == -1) ? false : true;
    }

    public boolean isTopToastShowing() {
        LinearLayout linearLayout = this.mToastTopTipLayout;
        return (linearLayout == null || linearLayout.getVisibility() == 8 || this.mToastTopTipLayout.getChildCount() == 0) ? false : true;
    }

    public /* synthetic */ void j(DialogInterface dialogInterface, int i) {
        this.mAlertDialog = null;
    }

    @Override // android.support.v4.app.Fragment
    public void onActivityCreated(@Nullable Bundle bundle) {
        super.onActivityCreated(bundle);
    }

    public void onClick(View view) {
        if (isEnableClick()) {
            ModeProtocol.CameraAction cameraAction = (ModeProtocol.CameraAction) ModeCoordinatorImpl.getInstance().getAttachProtocol(161);
            if (cameraAction == null || !cameraAction.isDoingAction()) {
                int id = view.getId();
                if (id == R.id.manually_parameter_description_tip) {
                    MistatsWrapper.moduleUIClickEvent(((BaseFragment) this).mCurrentMode == 167 ? MistatsConstants.ModuleName.MANUAL : MistatsConstants.ModuleName.PROVIDEO, MistatsConstants.Manual.PARAMETER_DESCRIPTION, "on");
                    if (FragmentUtils.getFragmentByTag(getFragmentManager(), FragmentParameterDescription.TAG) == null) {
                        FragmentParameterDescription fragmentParameterDescription = new FragmentParameterDescription();
                        fragmentParameterDescription.setStyle(2, R.style.ManuallyDescriptionFragment);
                        getFragmentManager().beginTransaction().add(fragmentParameterDescription, FragmentParameterDescription.TAG).commitAllowingStateLoss();
                    }
                } else if (id == R.id.reset_manually_parameter_tip) {
                    CameraStatUtils.trackManuallyResetClick();
                    showManualParameterResetDialog();
                }
            } else {
                Log.d(TAG, "cameraAction.isDoingAction return");
            }
        }
    }

    @Override // com.android.camera.fragment.BaseFragment, android.support.v4.app.Fragment
    public void onStop() {
        super.onStop();
        clear(true);
        AlertDialog alertDialog = this.mAlertDialog;
        if (alertDialog != null) {
            alertDialog.dismiss();
            this.mAlertDialog = null;
        }
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
        AlphaAnimation alphaAnimation = this.mBlingAnimation;
        if (alphaAnimation != null) {
            alphaAnimation.cancel();
            this.mBlingAnimation = null;
        }
    }

    @Override // com.android.camera.fragment.BaseFragment, com.android.camera.animation.AnimationDelegate.AnimationResource
    public void provideAnimateElement(int i, List<Completable> list, int i2) {
        int i3 = ((BaseFragment) this).mCurrentMode;
        super.provideAnimateElement(i, list, i2);
        clear((i3 == i || (i3 == 163 && i == 165) || (i3 == 165 && i == 163)) ? false : true);
        setShow(true);
    }

    @Override // com.android.camera.fragment.BaseFragment, com.android.camera.animation.AnimationDelegate.AnimationResource
    public void provideRotateItem(List<View> list, int i) {
        super.provideRotateItem(list, i);
        updateAlertStatusView(true);
    }

    public void setLiveShotHintVisibility(int i) {
        this.mLiveShotTip.setVisibility(i);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:36:0x00a0, code lost:
        if (r5 != 184) goto L_0x00d4;
     */
    public void setRecordingTimeState(int i) {
        if (i == 1) {
            this.mIsVideoRecording = true;
            int i2 = ((BaseFragment) this).mCurrentMode;
            if (i2 != 161) {
                if (!(i2 == 162 || i2 == 169 || i2 == 172)) {
                    if (i2 != 177) {
                        if (i2 != 180) {
                        }
                    }
                }
                if (((BaseFragment) this).mCurrentMode == 180) {
                    if (this.mManualParameterResetTip.getVisibility() == 0) {
                        this.mManualParameterResetTip.setVisibility(4);
                        this.mIsParameterResetVisibleBeforeRecording = true;
                    }
                    if (this.mManualParameterDescriptionTip.getVisibility() == 0) {
                        this.mManualParameterDescriptionTip.setVisibility(4);
                        this.mIsParameterDescriptionVisibleBeforeRecording = true;
                    }
                }
                this.mAlertRecordingText.setText("00:00");
                Completable.create(new AlphaInOnSubscribe(this.mAlertRecordingText)).subscribe();
            }
            this.mAlertRecordingText.setText("00:15");
            Completable.create(new AlphaInOnSubscribe(this.mAlertRecordingText)).subscribe();
        } else if (i == 2) {
            this.mIsVideoRecording = false;
            if (((BaseFragment) this).mCurrentMode == 180) {
                if (this.mIsParameterResetVisibleBeforeRecording) {
                    this.mIsParameterResetVisibleBeforeRecording = false;
                    this.mManualParameterResetTip.setVisibility(0);
                }
                if (this.mIsParameterDescriptionVisibleBeforeRecording) {
                    this.mIsParameterDescriptionVisibleBeforeRecording = false;
                    this.mManualParameterDescriptionTip.setVisibility(0);
                }
            }
            AlphaAnimation alphaAnimation = this.mBlingAnimation;
            if (alphaAnimation != null) {
                alphaAnimation.cancel();
            }
            Completable.create(new AlphaOutOnSubscribe(this.mAlertRecordingText)).subscribe();
        } else if (i == 3) {
            if (this.mBlingAnimation == null) {
                this.mBlingAnimation = new AlphaAnimation(1.0f, 0.0f);
                this.mBlingAnimation.setDuration(400);
                this.mBlingAnimation.setStartOffset(100);
                this.mBlingAnimation.setInterpolator(new DecelerateInterpolator());
                this.mBlingAnimation.setRepeatMode(2);
                this.mBlingAnimation.setRepeatCount(-1);
            }
            this.mAlertRecordingText.startAnimation(this.mBlingAnimation);
        } else if (i == 4) {
            this.mBlingAnimation.cancel();
        }
    }

    public void setShow(boolean z) {
        this.mShow = z;
    }

    public void showDocumentStateButton(int i) {
        if (this.mDocumentStateButton == null) {
            initDocumentStateButton();
        }
        if (i == 0) {
            addViewToTipLayout(this.mDocumentStateButton, (LinearLayout.LayoutParams) null, this.mTopTipLayout.getChildCount() > 0 ? 1 : -1);
            updateDocumentState(false);
            return;
        }
        removeViewToTipLayout(this.mDocumentStateButton);
    }

    public void updateLyingDirectHint(boolean z) {
        if (z && this.mTopTipLayout.indexOfChild(this.mLyingDirectHintText) == -1) {
            addViewToTipLayout(this.mLyingDirectHintText, new d(this), g.INSTANCE);
        } else if (!z && this.mTopTipLayout.indexOfChild(this.mLyingDirectHintText) != -1) {
            removeViewToTipLayout(this.mLyingDirectHintText);
        }
    }

    public void updateRecordingTime(String str) {
        this.mAlertRecordingText.setText(str);
    }
}
