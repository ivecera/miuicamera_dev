package com.android.camera.fragment;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.RectF;
import android.hardware.camera2.params.MeteringRectangle;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.MainThread;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.TextAppearanceSpan;
import android.util.Size;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.camera.ActivityBase;
import com.android.camera.CameraSettings;
import com.android.camera.HybridZoomingSystem;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.aiwatermark.DragListener;
import com.android.camera.aiwatermark.data.WatermarkItem;
import com.android.camera.animation.type.AlphaInOnSubscribe;
import com.android.camera.animation.type.AlphaOutOnSubscribe;
import com.android.camera.animation.type.SlideInOnSubscribe;
import com.android.camera.animation.type.SlideOutOnSubscribe;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.config.ComponentConfigRatio;
import com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol;
import com.android.camera.features.mimoji2.widget.helper.MimojiHelper2;
import com.android.camera.features.mimoji2.widget.helper.MimojiStatusManager2;
import com.android.camera.fragment.mimoji.MimojiHelper;
import com.android.camera.log.Log;
import com.android.camera.module.ModuleManager;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera.statistic.CameraStatUtils;
import com.android.camera.ui.AfRegionsView;
import com.android.camera.ui.FaceView;
import com.android.camera.ui.FocusIndicator;
import com.android.camera.ui.FocusView;
import com.android.camera.ui.HistogramView;
import com.android.camera.ui.LightingView;
import com.android.camera.ui.ObjectView;
import com.android.camera.ui.V6EffectCropView;
import com.android.camera.ui.V6PreviewFrame;
import com.android.camera.ui.V6PreviewPanel;
import com.android.camera.ui.VideoTagView;
import com.android.camera.ui.ZoomView;
import com.android.camera.watermark.WaterMarkData;
import com.android.camera2.CameraHardwareFace;
import com.android.camera2.autozoom.AutoZoomCaptureResult;
import com.android.camera2.autozoom.AutoZoomView;
import com.bumptech.glide.c;
import com.mi.config.b;
import d.h.a.v;
import io.reactivex.Completable;
import java.util.List;

public class FragmentMainContent extends BaseFragment implements ModeProtocol.MainContentProtocol, ModeProtocol.SnapShotIndicator, ModeProtocol.AutoZoomViewProtocol, ModeProtocol.HandleBackTrace, ZoomView.zoomValueChangeListener {
    public static final int FRAGMENT_INFO = 243;
    public static final int FRONT_CAMERA_ID = 1;
    private static final String TAG = "FragmentMainContent";
    private long lastConfirmTime;
    /* access modifiers changed from: private */
    public int lastFaceResult;
    private int mActiveIndicator = 2;
    private AfRegionsView mAfRegionsView;
    private AutoZoomView mAutoZoomOverlay;
    private View mBottomCover;
    private TextView mCaptureDelayNumber;
    private ImageView mCenterHintIcon;
    private TextView mCenterHintText;
    private ViewGroup mCoverParent;
    /* access modifiers changed from: private */
    public int mCurrentMimojiFaceResult;
    private int mDegree = -1;
    private int mDisplayRectTopMargin;
    private V6EffectCropView mEffectCropView;
    private FaceView mFaceView;
    private FocusView mFocusView;
    private Handler mHandler = new Handler();
    private ValueAnimator mHistogramAnimator;
    private HistogramView mHistogramView;
    private boolean mIsHorizontal;
    private boolean mIsIntentAction;
    private boolean mIsMimojiCreateLowLight;
    private boolean mIsMimojiFaceDetectTip;
    private boolean mIsRecording;
    private boolean mIsShowMainLyingDirectHint;
    private int mLastCameraId = -1;
    /* access modifiers changed from: private */
    public boolean mLastFaceSuccess;
    private int mLastTranslateY;
    private View mLeftCover;
    private LightingView mLightingView;
    private DragListener mListener = null;
    private TextView mLyingDirectHint;
    private int mMimojiDetectTipType;
    private int mMimojiFaceDetect;
    private boolean mMimojiLastFaceSuccess;
    /* access modifiers changed from: private */
    public LightingView mMimojiLightingView;
    private TextView mMultiSnapNum;
    private int mNormalCoverHeight;
    private ObjectView mObjectView;
    /* access modifiers changed from: private */
    public ViewGroup mPreviewCenterHint;
    private V6PreviewFrame mPreviewFrame;
    private ViewGroup mPreviewPage;
    private V6PreviewPanel mPreviewPanel;
    private View mRightCover;
    private TextAppearanceSpan mSnapStyle;
    private SpannableStringBuilder mStringBuilder;
    private View mTopCover;
    private VideoTagView mVideoTagView;
    private FrameLayout mWatermarkLayout;
    private ViewStub mWatermarkViewStub;
    private AnimatorSet mZoomInAnimator;
    private AnimatorSet mZoomOutAnimator;
    private ZoomView mZoomView;
    private ZoomView mZoomViewHorizontal;
    private RectF mergeRectF = new RectF();

    /* renamed from: com.android.camera.fragment.FragmentMainContent$6  reason: invalid class name */
    static /* synthetic */ class AnonymousClass6 {
        static final /* synthetic */ int[] $SwitchMap$com$android$camera$fragment$FragmentMainContent$CoverState = new int[CoverState.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(8:0|1|2|3|4|5|6|8) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001f */
        static {
            $SwitchMap$com$android$camera$fragment$FragmentMainContent$CoverState[CoverState.NONE.ordinal()] = 1;
            $SwitchMap$com$android$camera$fragment$FragmentMainContent$CoverState[CoverState.TB.ordinal()] = 2;
            try {
                $SwitchMap$com$android$camera$fragment$FragmentMainContent$CoverState[CoverState.LR.ordinal()] = 3;
            } catch (NoSuchFieldError unused) {
            }
        }
    }

    enum CoverState {
        NONE,
        TB,
        LR,
        TOP,
        BOTTOM,
        LEFT,
        RIGHT
    }

    private void adjustViewHeight() {
        V6PreviewPanel v6PreviewPanel;
        if (getContext() != null && (v6PreviewPanel = this.mPreviewPanel) != null) {
            ViewGroup viewGroup = (ViewGroup) v6PreviewPanel.getParent();
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) viewGroup.getLayoutParams();
            ViewGroup.MarginLayoutParams marginLayoutParams2 = (ViewGroup.MarginLayoutParams) this.mPreviewPanel.getLayoutParams();
            ViewGroup.MarginLayoutParams marginLayoutParams3 = (ViewGroup.MarginLayoutParams) this.mPreviewCenterHint.getLayoutParams();
            Rect previewRect = Util.getPreviewRect(getContext());
            if (marginLayoutParams2.height != previewRect.height() || previewRect.top != this.mDisplayRectTopMargin) {
                this.mDisplayRectTopMargin = previewRect.top;
                marginLayoutParams2.height = previewRect.height();
                marginLayoutParams2.topMargin = previewRect.top;
                this.mPreviewPanel.setLayoutParams(marginLayoutParams2);
                marginLayoutParams3.height = (previewRect.width() * 4) / 3;
                this.mPreviewCenterHint.setLayoutParams(marginLayoutParams3);
                marginLayoutParams.height = previewRect.height() + this.mDisplayRectTopMargin;
                viewGroup.setLayoutParams(marginLayoutParams);
                setDisplaySize(previewRect.width(), previewRect.height());
            }
        }
    }

    private void consumeResult(int i, boolean z) {
        if (System.currentTimeMillis() - this.lastConfirmTime >= ((long) (z ? 700 : 1000))) {
            this.lastConfirmTime = System.currentTimeMillis();
            Log.d("faceResult:", i + "");
            if (z) {
                mimojiFaceDetectSync(161, i);
            } else if (this.lastFaceResult != i) {
                this.lastFaceResult = i;
                final LightingView lightingView = this.mLightingView;
                lightingView.post(new Runnable() {
                    /* class com.android.camera.fragment.FragmentMainContent.AnonymousClass1 */

                    public void run() {
                        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
                        if (topAlert != null) {
                            topAlert.alertLightingHint(FragmentMainContent.this.lastFaceResult);
                        }
                        ModeProtocol.VerticalProtocol verticalProtocol = (ModeProtocol.VerticalProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(198);
                        if (verticalProtocol != null) {
                            verticalProtocol.alertLightingHint(FragmentMainContent.this.lastFaceResult);
                        }
                    }
                });
                boolean z2 = i == 6;
                if (this.mLastFaceSuccess != z2) {
                    this.mLastFaceSuccess = z2;
                    lightingView.post(new Runnable() {
                        /* class com.android.camera.fragment.FragmentMainContent.AnonymousClass2 */

                        public void run() {
                            if (FragmentMainContent.this.mLastFaceSuccess) {
                                lightingView.triggerAnimateSuccess();
                            } else {
                                lightingView.triggerAnimateFocusing();
                            }
                        }
                    });
                }
            }
        }
    }

    private FrameLayout.LayoutParams getLayoutParams(FrameLayout.LayoutParams layoutParams, int i, Rect rect, Size size, int i2) {
        if (i2 == -1 || i2 == 0) {
            int i3 = rect.left;
            int i4 = rect.top;
            size.getWidth();
            size.getHeight();
            if ((i & 1) != 0) {
                i3 = rect.left;
                size.getWidth();
            }
            if ((i & 2) != 0) {
                i4 = rect.top;
                size.getHeight();
            }
            if ((i & 4) != 0) {
                i3 = rect.right - size.getWidth();
            }
            if ((i & 8) != 0) {
                i4 = rect.bottom - size.getHeight();
            }
            if ((i & 16) != 0) {
                int i5 = rect.top;
                i4 = i5 + (((rect.bottom - i5) - size.getHeight()) / 2);
                size.getHeight();
            }
            if ((i & 32) != 0) {
                int i6 = rect.left;
                i3 = i6 + (((rect.right - i6) - size.getWidth()) / 2);
                size.getWidth();
            }
            layoutParams.leftMargin = i3;
            layoutParams.topMargin = i4;
            layoutParams.width = size.getWidth();
            layoutParams.height = size.getHeight();
        } else if (i2 == 90) {
            int i7 = rect.left;
            int i8 = rect.top;
            size.getHeight();
            size.getWidth();
            if ((i & 1) != 0) {
                i8 = rect.top;
                size.getWidth();
            }
            if ((i & 2) != 0) {
                i7 = rect.right - size.getHeight();
            }
            if ((i & 4) != 0) {
                i8 = rect.bottom - size.getWidth();
            }
            if ((i & 8) != 0) {
                i7 = rect.left;
                size.getHeight();
            }
            if ((i & 16) != 0) {
                int i9 = rect.left;
                i7 = i9 + (((rect.right - i9) - size.getHeight()) / 2);
                size.getHeight();
            }
            if ((i & 32) != 0) {
                int i10 = rect.top;
                i8 = i10 + (((rect.bottom - i10) - size.getWidth()) / 2);
                size.getWidth();
            }
            layoutParams.leftMargin = i7;
            layoutParams.topMargin = i8;
            layoutParams.width = size.getHeight();
            layoutParams.height = size.getWidth();
        } else if (i2 == 180) {
            int i11 = rect.left;
            int i12 = rect.top;
            size.getWidth();
            size.getHeight();
            if ((i & 1) != 0) {
                i11 = rect.right - size.getWidth();
            }
            if ((i & 2) != 0) {
                i12 = rect.bottom - size.getHeight();
            }
            if ((i & 4) != 0) {
                i11 = rect.left;
                size.getWidth();
            }
            if ((i & 8) != 0) {
                i12 = rect.top;
                size.getHeight();
            }
            if ((i & 16) != 0) {
                int i13 = rect.top;
                i12 = i13 + (((rect.bottom - i13) - size.getHeight()) / 2);
                size.getHeight();
            }
            if ((i & 32) != 0) {
                int i14 = rect.left;
                i11 = i14 + (((rect.right - i14) - size.getWidth()) / 2);
                size.getWidth();
            }
            layoutParams.leftMargin = i11;
            layoutParams.topMargin = i12;
            layoutParams.width = size.getWidth();
            layoutParams.height = size.getHeight();
        } else if (i2 == 270) {
            int i15 = rect.left;
            int i16 = rect.top;
            size.getHeight();
            size.getWidth();
            if ((i & 1) != 0) {
                i16 = rect.bottom - size.getWidth();
            }
            if ((i & 2) != 0) {
                i15 = rect.left;
                size.getHeight();
            }
            if ((i & 4) != 0) {
                i16 = rect.top;
                size.getWidth();
            }
            if ((i & 8) != 0) {
                i15 = rect.right - size.getHeight();
            }
            if ((i & 16) != 0) {
                int i17 = rect.left;
                i15 = i17 + (((rect.right - i17) - size.getHeight()) / 2);
                size.getHeight();
            }
            if ((i & 32) != 0) {
                int i18 = rect.top;
                i16 = i18 + (((rect.bottom - i18) - size.getWidth()) / 2);
                size.getWidth();
            }
            layoutParams.leftMargin = i15;
            layoutParams.topMargin = i16;
            layoutParams.width = size.getHeight();
            layoutParams.height = size.getWidth();
        }
        return layoutParams;
    }

    private RectF getMergeRect(RectF rectF, RectF rectF2) {
        float max = Math.max(rectF.left, rectF2.left);
        float min = Math.min(rectF.right, rectF2.right);
        this.mergeRectF.set(max, Math.max(rectF.top, rectF2.top), min, Math.min(rectF.bottom, rectF2.bottom));
        return this.mergeRectF;
    }

    private void initSnapNumAnimator() {
        this.mZoomInAnimator = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(), R.animator.zoom_button_zoom_in);
        this.mZoomInAnimator.setTarget(this.mMultiSnapNum);
        this.mZoomInAnimator.setInterpolator(new v());
        this.mZoomOutAnimator = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(), R.animator.zoom_button_zoom_out);
        this.mZoomOutAnimator.setTarget(this.mMultiSnapNum);
        this.mZoomOutAnimator.setInterpolator(new v());
    }

    private void initWatermarkLayout(ViewStub viewStub) {
        if (viewStub != null) {
            this.mWatermarkLayout = (FrameLayout) viewStub.inflate();
            this.mListener = new DragListener(Util.getDisplayRect());
            this.mWatermarkLayout.setOnTouchListener(this.mListener);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:8:0x001e, code lost:
        if (r8 != 270) goto L_0x0046;
     */
    private void initWatermarkLocation(FrameLayout.LayoutParams layoutParams, Size size, Rect rect, int i) {
        int[] iArr = new int[4];
        iArr[0] = layoutParams.leftMargin;
        iArr[1] = layoutParams.topMargin;
        if (!(i == -1 || i == 0)) {
            if (i != 90) {
                if (i != 180) {
                }
            }
            iArr[2] = layoutParams.leftMargin + size.getHeight();
            iArr[3] = layoutParams.topMargin + size.getWidth();
            this.mListener.reInit(rect, iArr);
        }
        iArr[2] = layoutParams.leftMargin + size.getWidth();
        iArr[3] = layoutParams.topMargin + size.getHeight();
        this.mListener.reInit(rect, iArr);
    }

    private boolean isMimojiFaceDetectTip() {
        boolean z = this.mIsMimojiFaceDetectTip;
        this.mIsMimojiFaceDetectTip = false;
        return z;
    }

    private boolean isRectIntersect(RectF rectF, RectF rectF2) {
        return rectF2.right >= rectF.left && rectF2.left <= rectF.right && rectF2.bottom >= rectF.top && rectF2.top <= rectF.bottom;
    }

    private boolean isReferenceLineEnabled() {
        return DataRepository.dataItemGlobal().getBoolean("pref_camera_referenceline_key", false);
    }

    /* access modifiers changed from: private */
    public synchronized void mimojiFaceDetectSync(int i, int i2) {
        int i3;
        int i4;
        if (((BaseFragment) this).mCurrentMode == 184) {
            i4 = MimojiHelper2.getTipsResIdFace(i2);
            i3 = MimojiHelper2.getTipsResId(i2);
        } else {
            i4 = MimojiHelper.getTipsResIdFace(i2);
            i3 = MimojiHelper.getTipsResId(i2);
        }
        if (160 == i && i3 == -1 && i2 != 6) {
            Log.c(TAG, "mimojiFaceDetectSync 0, faceResult = " + i2 + ", mimoji tips resId = " + i3);
        } else if (161 == i && i4 == -1 && i2 != 6) {
            Log.c(TAG, "mimojiFaceDetectSync 1, faceResult = " + i2 + ", miface tips resId = " + i4);
        } else if (i2 == this.mMimojiFaceDetect && i == this.mMimojiDetectTipType) {
            Log.c(TAG, "mimojiFaceDetectSync 2, faceResult = " + i2 + "type:" + i);
        } else {
            this.mMimojiDetectTipType = i;
            this.mMimojiFaceDetect = i2;
            setMimojiFaceDetectTip();
            if (((BaseFragment) this).mCurrentMode == 184) {
                if (i2 == 6 && MimojiHelper2.getTipsResId(this.mCurrentMimojiFaceResult) == -1) {
                    this.mLastFaceSuccess = true;
                } else {
                    this.mLastFaceSuccess = false;
                }
            } else if (i2 == 6 && MimojiHelper.getTipsResId(this.mCurrentMimojiFaceResult) == -1) {
                this.mLastFaceSuccess = true;
            } else {
                this.mLastFaceSuccess = false;
            }
            Log.d("mimojiFaceDetectSync", "face_detect_type:" + i + ",result:" + i2 + ",is_face_location_ok:" + this.mLastFaceSuccess);
        }
    }

    private boolean needReferenceLineMode() {
        return true;
    }

    private boolean needShowZoomView(int i) {
        if (i != 180) {
            return i == 162 && this.mIsRecording && DataRepository.dataItemFeature().c_26813_0x0003() && !isAutoZoomEnabled() && !CameraSettings.isSuperEISEnabled(162) && DataRepository.dataItemGlobal().getCurrentCameraId() != 1;
        }
        return true;
    }

    private void onZoomViewOrientationChanged(int i) {
        boolean z = (i + 180) % 180 != 0;
        if (z == this.mIsHorizontal || !needShowZoomView(((BaseFragment) this).mCurrentMode)) {
            this.mIsHorizontal = z;
            return;
        }
        if (this.mIsHorizontal) {
            this.mZoomView.show();
            this.mZoomViewHorizontal.hide();
            this.mZoomView.setCurrentZoomRatio(this.mZoomViewHorizontal.getCurrentZoomRatio());
        } else {
            this.mZoomView.hide();
            this.mZoomViewHorizontal.show();
            this.mZoomViewHorizontal.setCurrentZoomRatio(this.mZoomView.getCurrentZoomRatio());
        }
        this.mIsHorizontal = z;
    }

    private void setMimojiFaceDetectTip() {
        this.mIsMimojiFaceDetectTip = true;
    }

    private void showIndicator(FocusIndicator focusIndicator, int i) {
        if (i == 1) {
            focusIndicator.showStart();
        } else if (i == 2) {
            focusIndicator.showSuccess();
        } else if (i == 3) {
            focusIndicator.showFail();
        }
    }

    private void updateReferenceGradienterSwitched() {
        if (this.mPreviewFrame != null) {
            this.mPreviewFrame.updateReferenceGradienterSwitched(isReferenceLineEnabled() && !((ActivityBase) getContext()).getCameraIntentManager().isScanQRCodeIntent() && needReferenceLineMode(), CameraSettings.isGradienterOn(), ModuleManager.isSquareModule());
        }
    }

    private void updateWatermarkRotation(int i) {
        Log.d(TAG, "updateWatermarkRotation degree = " + i);
        WatermarkItem watermarkItem = DataRepository.dataItemRunning().getComponentRunningAIWatermark().getWatermarkItem();
        if (watermarkItem != null && this.mWatermarkLayout != null) {
            int location = watermarkItem.getLocation();
            Rect displayRect = Util.getDisplayRect();
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.mWatermarkLayout.getLayoutParams();
            Size bitmapSize = Util.getBitmapSize(watermarkItem.getResId());
            FrameLayout frameLayout = this.mWatermarkLayout;
            getLayoutParams(layoutParams, location, displayRect, bitmapSize, this.mDegree);
            frameLayout.setLayoutParams(layoutParams);
            this.mWatermarkLayout.invalidate();
            initWatermarkLocation(layoutParams, bitmapSize, displayRect, i);
            ((ImageView) this.mWatermarkLayout.findViewById(R.id.watermark_sample_image_f)).setImageBitmap(Util.rotate(Util.convertResToBitmap(watermarkItem.getResId()), i));
        }
    }

    private void updateWatermarkVisible() {
        boolean aIWatermarkEnable = DataRepository.dataItemRunning().getComponentRunningAIWatermark().getAIWatermarkEnable();
        boolean z = true;
        int i = 0;
        boolean z2 = ((BaseFragment) this).mCurrentMode == 163;
        boolean equals = TextUtils.equals(ComponentConfigRatio.RATIO_4X3, DataRepository.dataItemConfig().getComponentConfigRatio().getComponentValue(((BaseFragment) this).mCurrentMode));
        boolean isMacroModeEnabled = CameraSettings.isMacroModeEnabled(((BaseFragment) this).mCurrentMode);
        if (!aIWatermarkEnable || !z2 || !equals || isMacroModeEnabled) {
            z = false;
        }
        if (!z) {
            i = 4;
        }
        setWatermarkVisible(i);
    }

    @Override // com.android.camera.protocol.ModeProtocol.MainContentProtocol
    public void adjustHistogram(int i) {
        if (i == 0) {
            ViewCompat.animate(this.mHistogramView).setDuration(300).translationY(0.0f).setInterpolator(new DecelerateInterpolator()).start();
        } else {
            ViewCompat.animate(this.mHistogramView).setDuration(300).translationY((float) (-getResources().getDimensionPixelSize(R.dimen.histogram_move_distance))).setInterpolator(new DecelerateInterpolator()).start();
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.IndicatorProtocol
    public void clearFocusView(int i) {
        this.mFocusView.clear(i);
    }

    @Override // com.android.camera.protocol.ModeProtocol.IndicatorProtocol
    public void clearIndicator(int i) {
        if (i == 1) {
            this.mFaceView.clear();
        } else if (i == 2) {
            throw new RuntimeException("not allowed call in this method");
        } else if (i == 3) {
            this.mObjectView.clear();
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.EffectCropViewController
    public void destroyEffectCropView() {
        this.mEffectCropView.onDestroy();
    }

    @Override // com.android.camera.protocol.ModeProtocol.LyingDirectHint
    public void directHideLyingDirectHint() {
        this.mLyingDirectHint.setVisibility(8);
    }

    @Override // com.android.camera.protocol.ModeProtocol.AutoZoomViewProtocol
    public void feedData(AutoZoomCaptureResult autoZoomCaptureResult) {
        this.mAutoZoomOverlay.feedData(autoZoomCaptureResult);
    }

    @Override // com.android.camera.protocol.ModeProtocol.IndicatorProtocol
    public int getActiveIndicator() {
        return this.mActiveIndicator;
    }

    @Override // com.android.camera.protocol.ModeProtocol.MainContentProtocol
    public List<WaterMarkData> getFaceWaterMarkInfos() {
        return this.mFaceView.getFaceWaterMarkInfos();
    }

    @Override // com.android.camera.protocol.ModeProtocol.IndicatorProtocol
    public CameraHardwareFace[] getFaces() {
        return this.mFaceView.getFaces();
    }

    @Override // com.android.camera.protocol.ModeProtocol.IndicatorProtocol
    public RectF getFocusRect(int i) {
        if (i == 1) {
            return this.mFaceView.getFocusRect();
        }
        if (i == 3) {
            return this.mObjectView.getFocusRect();
        }
        Log.w(TAG, getFragmentTag() + ": unexpected type " + i);
        return new RectF();
    }

    @Override // com.android.camera.protocol.ModeProtocol.IndicatorProtocol
    public RectF getFocusRectInPreviewFrame() {
        return this.mObjectView.getFocusRectInPreviewFrame();
    }

    @Override // com.android.camera.fragment.BaseFragment
    public int getFragmentInto() {
        return 243;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public int getLayoutResourceId() {
        return R.layout.fragment_main_content;
    }

    @Override // com.android.camera.protocol.ModeProtocol.MainContentProtocol
    public String getVideoTagContent() {
        return this.mVideoTagView.getVideoTagContent();
    }

    @Override // com.android.camera.protocol.ModeProtocol.MainContentProtocol
    public void hideDelayNumber() {
        if (this.mCaptureDelayNumber.getVisibility() != 8) {
            this.mCaptureDelayNumber.setVisibility(8);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.MainContentProtocol
    public void hideReviewViews() {
        if (this.mPreviewPanel.mVideoReviewImage.getVisibility() == 0) {
            Util.fadeOut(this.mPreviewPanel.mVideoReviewImage);
        }
        Util.fadeOut(this.mPreviewPanel.mVideoReviewPlay);
    }

    @Override // com.android.camera.protocol.ModeProtocol.EffectCropViewController
    public void initEffectCropView() {
        this.mEffectCropView.onCreate();
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void initView(View view) {
        this.mCoverParent = (ViewGroup) view.findViewById(R.id.cover_parent);
        this.mMultiSnapNum = (TextView) this.mCoverParent.findViewById(R.id.v6_multi_snap_number);
        this.mCaptureDelayNumber = (TextView) this.mCoverParent.findViewById(R.id.v6_capture_delay_number);
        this.mTopCover = this.mCoverParent.findViewById(R.id.top_cover_layout);
        this.mBottomCover = this.mCoverParent.findViewById(R.id.bottom_cover_layout);
        this.mLeftCover = this.mCoverParent.findViewById(R.id.left_cover_layout);
        this.mRightCover = this.mCoverParent.findViewById(R.id.right_cover_layout);
        this.mPreviewPage = (ViewGroup) view.findViewById(R.id.v6_preview_page);
        this.mPreviewPanel = (V6PreviewPanel) this.mPreviewPage.findViewById(R.id.v6_preview_panel);
        this.mPreviewFrame = (V6PreviewFrame) this.mPreviewPanel.findViewById(R.id.v6_frame_layout);
        this.mPreviewCenterHint = (ViewGroup) this.mPreviewPanel.findViewById(R.id.center_hint_placeholder);
        this.mCenterHintIcon = (ImageView) this.mPreviewCenterHint.findViewById(R.id.center_hint_icon);
        this.mCenterHintText = (TextView) this.mPreviewCenterHint.findViewById(R.id.center_hint_text);
        this.mEffectCropView = (V6EffectCropView) this.mPreviewPanel.findViewById(R.id.v6_effect_crop_view);
        this.mFaceView = (FaceView) this.mPreviewPanel.findViewById(R.id.v6_face_view);
        this.mFocusView = (FocusView) this.mPreviewPanel.findViewById(R.id.v6_focus_view);
        this.mZoomView = (ZoomView) view.findViewById(R.id.v6_zoom_view);
        this.mZoomViewHorizontal = (ZoomView) view.findViewById(R.id.v6_zoom_view_horizontal);
        this.mZoomViewHorizontal.setIsHorizonal(true);
        this.mZoomView.setZoomValueChangeListener(this);
        this.mZoomViewHorizontal.setZoomValueChangeListener(this);
        this.mAutoZoomOverlay = (AutoZoomView) this.mPreviewPanel.findViewById(R.id.autozoom_overlay);
        this.mHistogramView = (HistogramView) view.findViewById(R.id.rgb_histogram);
        this.mLightingView = (LightingView) this.mPreviewPanel.findChildrenById(R.id.lighting_view);
        this.mObjectView = (ObjectView) this.mPreviewPanel.findViewById(R.id.object_view);
        this.mAfRegionsView = (AfRegionsView) this.mPreviewPanel.findViewById(R.id.afregions_view);
        this.mMimojiLightingView = (LightingView) this.mPreviewPanel.findChildrenById(R.id.mimoji_lighting_view);
        this.mLyingDirectHint = (TextView) view.findViewById(R.id.main_lying_direct_hint_text);
        this.mMimojiLightingView.setCircleRatio(1.18f);
        this.mMimojiLightingView.setCircleHeightRatio(1.12f);
        if (DataRepository.dataItemFeature().c_35955_0x0001_IF_india_OR_china()) {
            this.mWatermarkViewStub = (ViewStub) view.findViewById(R.id.watermark_viewstub);
            initWatermarkLayout(this.mWatermarkViewStub);
        }
        this.mVideoTagView = new VideoTagView();
        this.mVideoTagView.init(view, getContext());
        this.mLightingView.setRotation(this.mDegree);
        adjustViewHeight();
        this.mNormalCoverHeight = Util.sWindowHeight - Util.getBottomHeight();
        this.mCoverParent.getLayoutParams().height = this.mNormalCoverHeight;
        ViewGroup.LayoutParams layoutParams = this.mBottomCover.getLayoutParams();
        int i = Util.sWindowWidth;
        layoutParams.height = (((int) (((float) i) / 0.75f)) - i) / 2;
        this.mTopCover.getLayoutParams().height = (this.mCoverParent.getLayoutParams().height - Util.sWindowWidth) - this.mBottomCover.getLayoutParams().height;
        this.mLeftCover.getLayoutParams().width = Util.getCinematicAspectRatioMargin();
        this.mRightCover.getLayoutParams().width = Util.getCinematicAspectRatioMargin();
        this.mIsIntentAction = DataRepository.dataItemGlobal().isIntentAction();
        provideAnimateElement(((BaseFragment) this).mCurrentMode, null, 2);
    }

    @Override // com.android.camera.protocol.ModeProtocol.IndicatorProtocol
    public void initializeFocusView(FocusView.ExposureViewListener exposureViewListener) {
        this.mFocusView.initialize(exposureViewListener);
    }

    @Override // com.android.camera.protocol.ModeProtocol.IndicatorProtocol
    public boolean initializeObjectTrack(RectF rectF, boolean z) {
        this.mFocusView.clear();
        this.mObjectView.clear();
        this.mObjectView.setVisibility(0);
        return this.mObjectView.initializeTrackView(rectF, z);
    }

    @Override // com.android.camera.protocol.ModeProtocol.IndicatorProtocol
    public boolean initializeObjectView(RectF rectF, boolean z) {
        return this.mObjectView.initializeTrackView(rectF, z);
    }

    @Override // com.android.camera.protocol.ModeProtocol.IndicatorProtocol
    public boolean isAdjustingObjectView() {
        return this.mObjectView.isAdjusting();
    }

    @Override // com.android.camera.protocol.ModeProtocol.AutoZoomViewProtocol
    public boolean isAutoZoomActive() {
        return this.mAutoZoomOverlay.isViewActive();
    }

    @Override // com.android.camera.protocol.ModeProtocol.AutoZoomViewProtocol
    public boolean isAutoZoomEnabled() {
        return this.mAutoZoomOverlay.isViewEnabled();
    }

    @Override // com.android.camera.protocol.ModeProtocol.EffectCropViewController
    public boolean isAutoZoomViewEnabled() {
        return this.mAutoZoomOverlay.isViewEnabled();
    }

    @Override // com.android.camera.protocol.ModeProtocol.EffectCropViewController
    public boolean isEffectViewMoved() {
        return this.mEffectCropView.isMoved();
    }

    @Override // com.android.camera.protocol.ModeProtocol.EffectCropViewController
    public boolean isEffectViewVisible() {
        return this.mEffectCropView.isVisible();
    }

    @Override // com.android.camera.protocol.ModeProtocol.IndicatorProtocol
    public boolean isEvAdjusted(boolean z) {
        return z ? this.mFocusView.isEvAdjustedTime() : this.mFocusView.isEvAdjusted();
    }

    @Override // com.android.camera.protocol.ModeProtocol.IndicatorProtocol
    public boolean isFaceExists(int i) {
        if (i == 1) {
            return this.mFaceView.faceExists();
        }
        if (i != 3) {
            return false;
        }
        return this.mObjectView.faceExists();
    }

    @Override // com.android.camera.protocol.ModeProtocol.MainContentProtocol
    public boolean isFaceLocationOK() {
        return this.mLastFaceSuccess;
    }

    @Override // com.android.camera.protocol.ModeProtocol.IndicatorProtocol
    public boolean isFaceStable(int i) {
        if (i == 1) {
            return this.mFaceView.isFaceStable();
        }
        if (i != 3) {
            return false;
        }
        return this.mObjectView.isFaceStable();
    }

    @Override // com.android.camera.protocol.ModeProtocol.MainContentProtocol
    public boolean isFocusViewMoving() {
        return this.mFocusView.isFocusViewMoving();
    }

    @Override // com.android.camera.protocol.ModeProtocol.IndicatorProtocol
    public boolean isFocusViewVisible() {
        return this.mFocusView.isVisible();
    }

    @Override // com.android.camera.protocol.ModeProtocol.IndicatorProtocol
    public boolean isIndicatorVisible(int i) {
        return i != 1 ? i != 2 ? i == 3 && this.mObjectView.getVisibility() == 0 : this.mFocusView.getVisibility() == 0 : this.mFaceView.getVisibility() == 0;
    }

    @Override // com.android.camera.protocol.ModeProtocol.IndicatorProtocol
    public boolean isNeedExposure(int i) {
        if (i == 1) {
            return this.mFaceView.isNeedExposure();
        }
        if (i != 3) {
            return false;
        }
        return this.mObjectView.isNeedExposure();
    }

    @Override // com.android.camera.protocol.ModeProtocol.IndicatorProtocol
    public boolean isObjectTrackFailed() {
        return this.mObjectView.isTrackFailed();
    }

    @Override // com.android.camera.protocol.ModeProtocol.MainContentProtocol
    public boolean isShowReviewViews() {
        return this.mPreviewPanel.mVideoReviewImage.getVisibility() == 0;
    }

    @Override // com.android.camera.protocol.ModeProtocol.EffectCropViewController
    public boolean isZoomAdjustVisible() {
        return this.mZoomView.isVisible() || this.mZoomViewHorizontal.isVisible();
    }

    @Override // com.android.camera.protocol.ModeProtocol.MainContentProtocol
    public boolean isZoomViewMoving() {
        return this.mZoomView.isZoomMoving() || this.mZoomViewHorizontal.isZoomMoving();
    }

    @Override // com.android.camera.protocol.ModeProtocol.IndicatorProtocol
    public void lightingCancel() {
        this.mLightingView.triggerAnimateExit();
        this.lastConfirmTime = -1;
        this.mFaceView.setLightingOn(false);
        this.mAfRegionsView.setLightingOn(false);
    }

    @Override // com.android.camera.protocol.ModeProtocol.IndicatorProtocol
    public void lightingDetectFace(CameraHardwareFace[] cameraHardwareFaceArr, boolean z) {
        LightingView lightingView = z ? this.mMimojiLightingView : this.mLightingView;
        int i = 5;
        if (cameraHardwareFaceArr == null || cameraHardwareFaceArr.length == 0 || cameraHardwareFaceArr.length > 1) {
            consumeResult(5, z);
        } else if (this.lastConfirmTime != -1) {
            this.mFaceView.transToViewRect(cameraHardwareFaceArr[0].rect, lightingView.getFaceViewRectF());
            RectF faceViewRectF = lightingView.getFaceViewRectF();
            RectF focusRectF = lightingView.getFocusRectF();
            if (isRectIntersect(faceViewRectF, focusRectF)) {
                getMergeRect(faceViewRectF, focusRectF);
                float width = faceViewRectF.width() * faceViewRectF.height();
                float width2 = this.mergeRectF.width() * this.mergeRectF.height();
                float width3 = focusRectF.width() * focusRectF.height();
                float f2 = 1.0f;
                float f3 = z ? 0.5f : 1.0f;
                if (z) {
                    f2 = 1.5f;
                }
                float f4 = 0.2f * width3 * f3;
                float f5 = width3 * 0.5f * f2;
                if (width2 >= 0.5f * width) {
                    i = width2 < f4 ? 4 : (width2 >= f5 || width >= f5) ? 3 : 6;
                }
            }
            consumeResult(i, z);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.IndicatorProtocol
    public void lightingFocused() {
        this.mLightingView.triggerAnimateSuccess();
    }

    @Override // com.android.camera.protocol.ModeProtocol.IndicatorProtocol
    public void lightingStart() {
        this.mLightingView.setCinematicAspectRatio(CameraSettings.isCinematicAspectRatioEnabled(((BaseFragment) this).mCurrentMode));
        this.mLightingView.triggerAnimateStart();
        this.lastFaceResult = -1;
        this.mLastFaceSuccess = false;
        this.lastConfirmTime = System.currentTimeMillis();
        this.mFaceView.setLightingOn(true);
        this.mAfRegionsView.setLightingOn(true);
    }

    @Override // com.android.camera.protocol.ModeProtocol.IndicatorProtocol
    public void mimojiEnd() {
        if (getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() {
                /* class com.android.camera.fragment.FragmentMainContent.AnonymousClass4 */

                public void run() {
                    FragmentMainContent.this.mMimojiLightingView.triggerAnimateExit();
                }
            });
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.IndicatorProtocol
    public void mimojiFaceDetect(final int i) {
        this.mMimojiLightingView.post(new Runnable() {
            /* class com.android.camera.fragment.FragmentMainContent.AnonymousClass3 */

            public void run() {
                int unused = FragmentMainContent.this.mCurrentMimojiFaceResult = i;
                FragmentMainContent.this.mimojiFaceDetectSync(160, i);
            }
        });
    }

    @Override // com.android.camera.protocol.ModeProtocol.IndicatorProtocol
    public void mimojiStart() {
        this.lastFaceResult = -1;
        this.mLastFaceSuccess = false;
        this.lastConfirmTime = System.currentTimeMillis();
        this.mFaceView.setLightingOn(true);
        this.mAfRegionsView.setLightingOn(true);
        this.mMimojiLightingView.triggerAnimateStart();
    }

    @Override // com.android.camera.fragment.BaseFragment, com.android.camera.animation.AnimationDelegate.AnimationResource
    public boolean needViewClear() {
        return true;
    }

    @Override // com.android.camera.fragment.BaseFragment, com.android.camera.animation.AnimationDelegate.AnimationResource
    public void notifyAfterFrameAvailable(int i) {
        super.notifyAfterFrameAvailable(i);
        this.mPreviewFrame.updateReferenceLineAccordSquare();
        updateReferenceGradienterSwitched();
        updateCinematicAspectRatioSwitched(CameraSettings.isCinematicAspectRatioEnabled(((BaseFragment) this).mCurrentMode));
        updateWatermarkVisible();
        this.mFocusView.reInit();
        this.mZoomView.reInit();
        this.mZoomViewHorizontal.reInit();
        this.mEffectCropView.updateVisible();
        updateFocusMode(CameraSettings.getFocusMode());
        if (CameraSettings.isProVideoHistogramOpen(((BaseFragment) this).mCurrentMode)) {
            this.mHistogramView.setVisibility(0);
            ViewCompat.animate(this.mHistogramView).setDuration(300).alpha(1.0f).start();
        }
    }

    @Override // com.android.camera.fragment.BaseFragment, com.android.camera.animation.AnimationDelegate.AnimationResource
    public void notifyDataChanged(int i, int i2) {
        super.notifyDataChanged(i, i2);
        boolean isIntentAction = DataRepository.dataItemGlobal().isIntentAction();
        if (isIntentAction != this.mIsIntentAction) {
            this.mIsIntentAction = isIntentAction;
            hideReviewViews();
        }
        if (DataRepository.dataItemGlobal().getCurrentCameraId() != this.mLastCameraId) {
            this.mLastCameraId = DataRepository.dataItemGlobal().getCurrentCameraId();
            if (Util.isAccessible()) {
                if (this.mLastCameraId != 1) {
                    this.mPreviewFrame.setContentDescription(getString(R.string.accessibility_back_preview_status));
                    this.mPreviewFrame.announceForAccessibility(getString(R.string.accessibility_back_preview_status));
                } else if (Util.isScreenSlideOff(getActivity())) {
                    this.mPreviewFrame.setContentDescription(getString(R.string.accessibility_pull_down_to_open_camera));
                    this.mPreviewFrame.announceForAccessibility(getString(R.string.accessibility_pull_down_to_open_camera));
                } else {
                    this.mPreviewFrame.setContentDescription(getString(R.string.accessibility_front_preview_status));
                    this.mPreviewFrame.announceForAccessibility(getString(R.string.accessibility_front_preview_status));
                }
            }
        }
        if (i == 2) {
            adjustViewHeight();
        } else if (i == 3) {
            adjustViewHeight();
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.AutoZoomViewProtocol
    public void onAutoZoomStarted() {
        if (!this.mAutoZoomOverlay.isViewEnabled()) {
            this.mAutoZoomOverlay.setViewEnable(true);
            this.mAutoZoomOverlay.setViewActive(false);
            this.mAutoZoomOverlay.clear(0);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.AutoZoomViewProtocol
    public void onAutoZoomStopped() {
        if (this.mAutoZoomOverlay.isViewEnabled()) {
            this.mAutoZoomOverlay.setViewEnable(false);
            this.mAutoZoomOverlay.setViewActive(false);
            this.mAutoZoomOverlay.clear(4);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.HandleBackTrace
    public boolean onBackEvent(int i) {
        return false;
    }

    @Override // android.support.v4.app.Fragment
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override // android.support.v4.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        destroyEffectCropView();
    }

    @Override // com.android.camera.protocol.ModeProtocol.EffectCropViewController
    public boolean onEffectViewTouchEvent(MotionEvent motionEvent) {
        return this.mEffectCropView.onTouchEvent(motionEvent);
    }

    @Override // android.support.v4.app.Fragment
    public void onPause() {
        super.onPause();
        this.mLastFaceSuccess = false;
        FaceView faceView = this.mFaceView;
        if (faceView != null) {
            faceView.setVisibility(8);
        }
        this.mHandler.removeCallbacksAndMessages(null);
    }

    @Override // com.android.camera.fragment.BaseFragment, android.support.v4.app.Fragment
    public void onResume() {
        super.onResume();
        FaceView faceView = this.mFaceView;
        if (faceView != null) {
            faceView.setVisibility(0);
        }
    }

    @Override // com.android.camera.fragment.BaseFragment, android.support.v4.app.Fragment
    public void onStop() {
        super.onStop();
        this.mLightingView.clear();
    }

    @Override // com.android.camera.protocol.ModeProtocol.IndicatorProtocol
    public void onStopObjectTrack() {
        this.mObjectView.clear();
        this.mObjectView.setVisibility(8);
    }

    @Override // com.android.camera.protocol.ModeProtocol.AutoZoomViewProtocol
    public void onTrackingStarted(RectF rectF) {
        ModeProtocol.AutoZoomModuleProtocol autoZoomModuleProtocol = (ModeProtocol.AutoZoomModuleProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(215);
        if (autoZoomModuleProtocol != null) {
            autoZoomModuleProtocol.startTracking(rectF);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.AutoZoomViewProtocol
    public void onTrackingStopped(int i) {
        if (this.mAutoZoomOverlay.isViewActive()) {
            this.mAutoZoomOverlay.setViewActive(false);
            this.mAutoZoomOverlay.clear(0);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.MainContentProtocol
    public boolean onViewTouchEvent(int i, MotionEvent motionEvent) {
        if (i == this.mFocusView.getId()) {
            return this.mFocusView.onViewTouchEvent(motionEvent);
        }
        if (i == this.mEffectCropView.getId()) {
            return this.mEffectCropView.onViewTouchEvent(motionEvent);
        }
        if (i == this.mAutoZoomOverlay.getId()) {
            return this.mAutoZoomOverlay.onViewTouchEvent(motionEvent);
        }
        if (i == this.mZoomView.getId()) {
            return this.mZoomView.onViewTouchEvent(motionEvent);
        }
        if (i == this.mZoomViewHorizontal.getId()) {
            return this.mZoomViewHorizontal.onViewTouchEvent(motionEvent);
        }
        return false;
    }

    @Override // com.android.camera.ui.ZoomView.zoomValueChangeListener
    public void onZoomValueChanged(float f2) {
        ModeProtocol.ManuallyValueChanged manuallyValueChanged = (ModeProtocol.ManuallyValueChanged) ModeCoordinatorImpl.getInstance().getAttachProtocol(174);
        if (manuallyValueChanged != null) {
            manuallyValueChanged.onDualZoomValueChanged(f2, 1);
        }
    }

    public /* synthetic */ void p(boolean z) {
        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        boolean z2 = true;
        switch (this.mMimojiDetectTipType) {
            case 160:
                if (isMimojiFaceDetectTip()) {
                    int tipsResId = ((BaseFragment) this).mCurrentMode == 184 ? MimojiHelper2.getTipsResId(this.mMimojiFaceDetect) : MimojiHelper.getTipsResId(this.mMimojiFaceDetect);
                    if (topAlert != null && tipsResId > 0) {
                        topAlert.alertMimojiFaceDetect(true, tipsResId);
                        break;
                    }
                } else {
                    return;
                }
            case 161:
                if (isMimojiFaceDetectTip()) {
                    int tipsResIdFace = ((BaseFragment) this).mCurrentMode == 184 ? MimojiHelper2.getTipsResIdFace(this.mMimojiFaceDetect) : MimojiHelper.getTipsResIdFace(this.mMimojiFaceDetect);
                    if (topAlert != null && tipsResIdFace > 0) {
                        topAlert.alertMimojiFaceDetect(true, tipsResIdFace);
                        break;
                    }
                } else {
                    return;
                }
            case 162:
                int tipsResIdFace2 = ((BaseFragment) this).mCurrentMode == 184 ? MimojiHelper2.getTipsResIdFace(7) : MimojiHelper.getTipsResIdFace(7);
                if (topAlert == null || tipsResIdFace2 == -1 || !z) {
                    if (topAlert != null) {
                        topAlert.alertMimojiFaceDetect(false, -1);
                        break;
                    }
                } else {
                    topAlert.alertMimojiFaceDetect(true, tipsResIdFace2);
                    break;
                }
                break;
        }
        if (((BaseFragment) this).mCurrentMode == 184) {
            MimojiModeProtocol.MimojiAvatarEngine2 mimojiAvatarEngine2 = (MimojiModeProtocol.MimojiAvatarEngine2) ModeCoordinatorImpl.getInstance().getAttachProtocol(246);
            if (mimojiAvatarEngine2 != null) {
                mimojiAvatarEngine2.setDetectSuccess(this.mLastFaceSuccess);
                ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
                if (this.mLastFaceSuccess && !z) {
                    MimojiStatusManager2 mimojiStatusManager2 = DataRepository.dataItemLive().getMimojiStatusManager2();
                    if (bottomPopupTips != null) {
                        if (mimojiStatusManager2.getMimojiPanelState() == 0 || mimojiStatusManager2.isInMimojiCreate()) {
                            bottomPopupTips.showTips(19, R.string.mimoji_check_normal, 2);
                        } else {
                            bottomPopupTips.directlyHideTips();
                        }
                    }
                    if (topAlert != null) {
                        topAlert.alertMimojiFaceDetect(false, -1);
                    }
                } else if (bottomPopupTips != null) {
                    bottomPopupTips.directlyHideTips();
                }
            }
        } else {
            ModeProtocol.MimojiAvatarEngine mimojiAvatarEngine = (ModeProtocol.MimojiAvatarEngine) ModeCoordinatorImpl.getInstance().getAttachProtocol(217);
            if (mimojiAvatarEngine != null) {
                mimojiAvatarEngine.setDetectSuccess(this.mLastFaceSuccess);
                ModeProtocol.BottomPopupTips bottomPopupTips2 = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
                if (this.mLastFaceSuccess && !z) {
                    if (bottomPopupTips2 != null) {
                        bottomPopupTips2.showTips(19, R.string.mimoji_check_normal, 2);
                    }
                    if (topAlert != null) {
                        topAlert.alertMimojiFaceDetect(false, -1);
                    }
                } else if (bottomPopupTips2 != null) {
                    bottomPopupTips2.directlyHideTips();
                }
            }
        }
        if (!this.mLastFaceSuccess || z) {
            z2 = false;
        }
        if (z2) {
            this.mMimojiLightingView.triggerAnimateSuccess();
        } else if (!(this.mMimojiLastFaceSuccess == this.mLastFaceSuccess && this.mIsMimojiCreateLowLight == z)) {
            this.mMimojiLightingView.triggerAnimateStart();
        }
        this.mIsMimojiCreateLowLight = z;
        this.mMimojiLastFaceSuccess = this.mLastFaceSuccess;
    }

    @Override // com.android.camera.protocol.ModeProtocol.MainContentProtocol
    public void performHapticFeedback(int i) {
        this.mPreviewFrame.performHapticFeedback(i);
    }

    @Override // com.android.camera.protocol.ModeProtocol.MainContentProtocol
    public void processingFinish(boolean z) {
        setZoomViewVisible(false, false);
        if (z) {
            this.mVideoTagView.stop();
        }
        this.mFocusView.processingFinish();
        this.mIsRecording = false;
    }

    @Override // com.android.camera.protocol.ModeProtocol.MainContentProtocol
    public void processingPause() {
        this.mVideoTagView.pause();
    }

    @Override // com.android.camera.protocol.ModeProtocol.MainContentProtocol
    public void processingResume() {
        this.mVideoTagView.resume();
    }

    @Override // com.android.camera.protocol.ModeProtocol.MainContentProtocol
    public void processingStart(String str) {
        this.mIsRecording = true;
        if (needShowZoomView(((BaseFragment) this).mCurrentMode)) {
            setZoomViewVisible(true, false);
        }
        if (str != null) {
            this.mVideoTagView.start(str);
        }
        this.mFocusView.processingStart();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:8:0x001c, code lost:
        if (r10 != 180) goto L_0x001e;
     */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x005e  */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0060  */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x00ce  */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x00db  */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x00f9  */
    /* JADX WARNING: Removed duplicated region for block: B:72:0x01a3  */
    @Override // com.android.camera.fragment.BaseFragment, com.android.camera.animation.AnimationDelegate.AnimationResource
    public void provideAnimateElement(int i, List<Completable> list, int i2) {
        CoverState coverState;
        boolean z;
        int i3;
        int i4 = ((BaseFragment) this).mCurrentMode;
        super.provideAnimateElement(i, list, i2);
        CoverState coverState2 = CoverState.NONE;
        boolean z2 = true;
        if (i != 162) {
            if (i == 163) {
                boolean isMacroModeEnabled = CameraSettings.isMacroModeEnabled(i);
                if (CameraSettings.isAIWatermarkOn() && !isMacroModeEnabled) {
                    coverState = coverState2;
                    z = true;
                    setWatermarkVisible(z ? 0 : 4);
                    if (isInModeChanging() || i2 == 3) {
                        this.mLyingDirectHint.setVisibility(8);
                        this.mIsShowMainLyingDirectHint = false;
                    }
                    setSnapNumVisible(false, true);
                    hideDelayNumber();
                    this.mPreviewFrame.hidePreviewReferenceLine();
                    this.mPreviewFrame.hidePreviewGradienter();
                    this.mFaceView.clear();
                    this.mFaceView.clearFaceFlags();
                    this.mFocusView.clear();
                    this.mLightingView.clear();
                    this.mAfRegionsView.clear();
                    this.mMimojiLightingView.clear();
                    if (!(((BaseFragment) this).mCurrentMode == 180 || this.mHistogramView.getVisibility() != 0 || list == null)) {
                        list.add(Completable.create(new AlphaOutOnSubscribe(this.mHistogramView)));
                    }
                    if ((i4 == 162 || i4 == 169) && (i == 162 || i == 169)) {
                        z2 = false;
                    }
                    if (z2) {
                        this.mFocusView.releaseListener();
                    }
                    if (this.mTopCover.getTag() != null || this.mTopCover.getTag() != coverState) {
                        this.mTopCover.setTag(coverState);
                        i3 = AnonymousClass6.$SwitchMap$com$android$camera$fragment$FragmentMainContent$CoverState[coverState.ordinal()];
                        if (i3 == 2) {
                            if (this.mCoverParent.getLayoutParams().height != this.mNormalCoverHeight) {
                                this.mCoverParent.getLayoutParams().height = this.mNormalCoverHeight;
                                this.mCoverParent.requestLayout();
                            }
                            SlideOutOnSubscribe.directSetResult(this.mLeftCover, 3);
                            SlideOutOnSubscribe.directSetResult(this.mRightCover, 5);
                            if (list == null) {
                                SlideInOnSubscribe.directSetResult(this.mTopCover, 48);
                                SlideInOnSubscribe.directSetResult(this.mBottomCover, 80);
                                return;
                            }
                            list.add(Completable.create(new SlideInOnSubscribe(this.mTopCover, 48)));
                            list.add(Completable.create(new SlideInOnSubscribe(this.mBottomCover, 80)));
                            return;
                        } else if (i3 != 3) {
                            if (list == null) {
                                SlideOutOnSubscribe.directSetResult(this.mTopCover, 48);
                                SlideOutOnSubscribe.directSetResult(this.mBottomCover, 80);
                                SlideOutOnSubscribe.directSetResult(this.mLeftCover, 3);
                                SlideOutOnSubscribe.directSetResult(this.mRightCover, 5);
                            } else {
                                if (this.mTopCover.getVisibility() == 0) {
                                    list.add(Completable.create(new SlideOutOnSubscribe(this.mTopCover, 48).setDurationTime(200)));
                                }
                                if (this.mBottomCover.getVisibility() == 0) {
                                    list.add(Completable.create(new SlideOutOnSubscribe(this.mBottomCover, 80).setDurationTime(200)));
                                }
                                if (this.mLeftCover.getVisibility() == 0) {
                                    list.add(Completable.create(new SlideOutOnSubscribe(this.mLeftCover, 3).setDurationTime(200)));
                                }
                                if (this.mRightCover.getVisibility() == 0) {
                                    list.add(Completable.create(new SlideOutOnSubscribe(this.mRightCover, 5).setDurationTime(200)));
                                }
                            }
                            if (this.mCoverParent.getLayoutParams().height != this.mNormalCoverHeight) {
                                this.mCoverParent.getLayoutParams().height = this.mNormalCoverHeight;
                                this.mCoverParent.requestLayout();
                                return;
                            }
                            return;
                        } else {
                            SlideOutOnSubscribe.directSetResult(this.mTopCover, 48);
                            SlideOutOnSubscribe.directSetResult(this.mBottomCover, 80);
                            return;
                        }
                    } else {
                        return;
                    }
                }
            } else if (i == 165) {
                coverState2 = CoverState.TB;
            } else if (i == 171) {
                coverState2 = (!CameraSettings.isCinematicAspectRatioEnabled(((BaseFragment) this).mCurrentMode) || i2 == 3) ? CoverState.NONE : CoverState.LR;
            }
            coverState = coverState2;
            z = false;
            setWatermarkVisible(z ? 0 : 4);
            this.mLyingDirectHint.setVisibility(8);
            this.mIsShowMainLyingDirectHint = false;
            setSnapNumVisible(false, true);
            hideDelayNumber();
            this.mPreviewFrame.hidePreviewReferenceLine();
            this.mPreviewFrame.hidePreviewGradienter();
            this.mFaceView.clear();
            this.mFaceView.clearFaceFlags();
            this.mFocusView.clear();
            this.mLightingView.clear();
            this.mAfRegionsView.clear();
            this.mMimojiLightingView.clear();
            list.add(Completable.create(new AlphaOutOnSubscribe(this.mHistogramView)));
            z2 = false;
            if (z2) {
            }
            if (this.mTopCover.getTag() != null) {
            }
            this.mTopCover.setTag(coverState);
            i3 = AnonymousClass6.$SwitchMap$com$android$camera$fragment$FragmentMainContent$CoverState[coverState.ordinal()];
            if (i3 == 2) {
            }
        }
        if (DataRepository.dataItemFeature().c_26813_0x0003() || ((BaseFragment) this).mCurrentMode == 180) {
            this.mZoomViewHorizontal.init();
            this.mZoomView.init();
        }
        coverState = coverState2;
        z = false;
        setWatermarkVisible(z ? 0 : 4);
        this.mLyingDirectHint.setVisibility(8);
        this.mIsShowMainLyingDirectHint = false;
        setSnapNumVisible(false, true);
        hideDelayNumber();
        this.mPreviewFrame.hidePreviewReferenceLine();
        this.mPreviewFrame.hidePreviewGradienter();
        this.mFaceView.clear();
        this.mFaceView.clearFaceFlags();
        this.mFocusView.clear();
        this.mLightingView.clear();
        this.mAfRegionsView.clear();
        this.mMimojiLightingView.clear();
        list.add(Completable.create(new AlphaOutOnSubscribe(this.mHistogramView)));
        z2 = false;
        if (z2) {
        }
        if (this.mTopCover.getTag() != null) {
        }
        this.mTopCover.setTag(coverState);
        i3 = AnonymousClass6.$SwitchMap$com$android$camera$fragment$FragmentMainContent$CoverState[coverState.ordinal()];
        if (i3 == 2) {
        }
    }

    @Override // com.android.camera.fragment.BaseFragment, com.android.camera.animation.AnimationDelegate.AnimationResource
    public void provideRotateItem(List<View> list, int i) {
        super.provideRotateItem(list, i);
        if (this.mDegree != i) {
            this.mDegree = i;
            updateWatermarkRotation(i);
        }
        this.mFaceView.setOrientation((360 - i) % 360, false);
        this.mAfRegionsView.setOrientation(i, false);
        this.mLightingView.setOrientation(i, false);
        this.mFocusView.setOrientation(i, false);
        this.mHistogramView.setOrientation(i, true);
        list.add(this.mFocusView);
        list.add(this.mMultiSnapNum);
        list.add(this.mCaptureDelayNumber);
    }

    @Override // com.android.camera.protocol.ModeProtocol.IndicatorProtocol
    public void reShowFaceRect() {
        this.mFaceView.reShowFaceRect();
    }

    @Override // com.android.camera.protocol.ModeProtocol.MainContentProtocol
    @MainThread
    public void refreshHistogramStatsView() {
        HistogramView histogramView = this.mHistogramView;
        if (histogramView != null && histogramView.getVisibility() == 0) {
            this.mHistogramView.refresh();
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void register(ModeProtocol.ModeCoordinator modeCoordinator) {
        super.register(modeCoordinator);
        modeCoordinator.attachProtocol(166, this);
        modeCoordinator.attachProtocol(214, this);
        registerBackStack(modeCoordinator, this);
        if (!b.isSupportedOpticalZoom() && !HybridZoomingSystem.IS_3_OR_MORE_SAT) {
            modeCoordinator.attachProtocol(184, this);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.EffectCropViewController
    public void removeTiltShiftMask() {
        this.mEffectCropView.removeTiltShiftMask();
    }

    @Override // com.android.camera.protocol.ModeProtocol.IndicatorProtocol
    public void setActiveIndicator(int i) {
        this.mActiveIndicator = i;
    }

    @Override // com.android.camera.protocol.ModeProtocol.IndicatorProtocol
    public void setAfRegionView(MeteringRectangle[] meteringRectangleArr, Rect rect, float f2) {
        this.mAfRegionsView.setAfRegionRect(meteringRectangleArr, rect, f2);
    }

    @Override // com.android.camera.protocol.ModeProtocol.IndicatorProtocol
    public void setCameraDisplayOrientation(int i) {
        FaceView faceView = this.mFaceView;
        if (faceView != null && this.mAfRegionsView != null) {
            faceView.setCameraDisplayOrientation(i);
            this.mAfRegionsView.setCameraDisplayOrientation(i);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.MainContentProtocol
    public void setCenterHint(int i, String str, String str2, int i2) {
        this.mHandler.removeCallbacksAndMessages(this.mPreviewCenterHint);
        if (i == 0) {
            this.mCenterHintText.setText(str);
            if (str == null || str.equals("")) {
                this.mCenterHintText.setVisibility(8);
            } else {
                this.mCenterHintText.setVisibility(0);
            }
            if (str2 == null || str2.equals("")) {
                this.mCenterHintIcon.setVisibility(8);
            } else {
                c.b(this).load(str2).a(this.mCenterHintIcon);
                this.mCenterHintIcon.setVisibility(0);
            }
            if (i2 > 0) {
                this.mHandler.postAtTime(new Runnable() {
                    /* class com.android.camera.fragment.FragmentMainContent.AnonymousClass5 */

                    public void run() {
                        FragmentMainContent.this.mPreviewCenterHint.setVisibility(8);
                    }
                }, this.mPreviewCenterHint, SystemClock.uptimeMillis() + ((long) i2));
            }
        }
        this.mPreviewCenterHint.setVisibility(i);
    }

    @Override // com.android.camera.protocol.ModeProtocol.IndicatorProtocol
    public void setDisplaySize(int i, int i2) {
        this.mObjectView.setDisplaySize(i, i2);
    }

    @Override // com.android.camera.protocol.ModeProtocol.EffectCropViewController
    public void setEffectViewVisible(boolean z) {
        if (z) {
            this.mEffectCropView.show();
        } else {
            this.mEffectCropView.hide();
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.IndicatorProtocol
    public void setEvAdjustable(boolean z) {
        this.mFocusView.setEvAdjustable(z);
    }

    @Override // com.android.camera.protocol.ModeProtocol.IndicatorProtocol
    public boolean setFaces(int i, CameraHardwareFace[] cameraHardwareFaceArr, Rect rect, float f2) {
        if (i == 1) {
            return this.mFaceView.setFaces(cameraHardwareFaceArr, rect, f2);
        }
        if (i != 3) {
            return false;
        }
        if (cameraHardwareFaceArr != null && cameraHardwareFaceArr.length > 0) {
            this.mObjectView.setObject(cameraHardwareFaceArr[0]);
        }
        return true;
    }

    @Override // com.android.camera.protocol.ModeProtocol.IndicatorProtocol
    public void setFocusViewPosition(int i, int i2, int i3) {
        this.mFocusView.setPosition(i, i2, i3);
        this.mFaceView.forceHideRect();
    }

    @Override // com.android.camera.protocol.ModeProtocol.IndicatorProtocol
    public void setFocusViewType(boolean z) {
        this.mFocusView.setFocusType(z);
    }

    @Override // com.android.camera.protocol.ModeProtocol.MainContentProtocol
    public void setMimojiDetectTipType(int i) {
        if (i != this.mMimojiDetectTipType) {
            this.mMimojiDetectTipType = i;
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.IndicatorProtocol
    public void setObjectViewListener(ObjectView.ObjectViewListener objectViewListener) {
        ObjectView objectView = this.mObjectView;
        if (objectView != null) {
            objectView.setObjectViewListener(objectViewListener);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.MainContentProtocol
    public void setPreviewAspectRatio(float f2) {
        adjustViewHeight();
    }

    @Override // com.android.camera.protocol.ModeProtocol.IndicatorProtocol
    public void setPreviewSize(int i, int i2) {
        AutoZoomView autoZoomView = this.mAutoZoomOverlay;
        if (autoZoomView != null) {
            autoZoomView.setPreviewSize(new Size(i, i2));
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.IndicatorProtocol
    public void setShowGenderAndAge(boolean z) {
        this.mFaceView.setShowGenderAndAge(z);
    }

    @Override // com.android.camera.protocol.ModeProtocol.IndicatorProtocol
    public void setShowMagicMirror(boolean z) {
        this.mFaceView.setShowMagicMirror(z);
    }

    @Override // com.android.camera.protocol.ModeProtocol.IndicatorProtocol
    public void setSkipDrawFace(boolean z) {
        this.mFaceView.setSkipDraw(z);
    }

    @Override // com.android.camera.protocol.ModeProtocol.SnapShotIndicator
    @TargetApi(21)
    public void setSnapNumValue(int i) {
        if (this.mSnapStyle == null) {
            this.mSnapStyle = new TextAppearanceSpan(getContext(), R.style.SnapTipTextStyle);
        }
        SpannableStringBuilder spannableStringBuilder = this.mStringBuilder;
        if (spannableStringBuilder == null) {
            this.mStringBuilder = new SpannableStringBuilder();
        } else {
            spannableStringBuilder.clear();
        }
        this.mStringBuilder.append(String.format("%02d", Integer.valueOf(i)), this.mSnapStyle, 33);
        this.mMultiSnapNum.setText(this.mStringBuilder);
    }

    @Override // com.android.camera.protocol.ModeProtocol.SnapShotIndicator
    public void setSnapNumVisible(boolean z, boolean z2) {
        if (z != (this.mMultiSnapNum.getVisibility() == 0)) {
            if (this.mZoomInAnimator == null) {
                initSnapNumAnimator();
            }
            if (z) {
                AlphaInOnSubscribe.directSetResult(this.mMultiSnapNum);
                setSnapNumValue(0);
                this.mZoomInAnimator.start();
                return;
            }
            this.mZoomOutAnimator.start();
            Completable.create(new AlphaOutOnSubscribe(this.mMultiSnapNum).setStartDelayTime(500)).subscribe();
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.MainContentProtocol
    public void setWatermarkVisible(int i) {
        FrameLayout frameLayout = this.mWatermarkLayout;
        if (frameLayout != null) {
            frameLayout.setVisibility(i);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.EffectCropViewController
    public void setZoomViewVisible(boolean z, boolean z2) {
        if (z) {
            this.mZoomViewHorizontal.show();
            this.mZoomViewHorizontal.setCurrentZoomRatio(CameraSettings.readZoom());
            return;
        }
        this.mZoomViewHorizontal.hide();
        if (z2) {
            this.mZoomViewHorizontal.reset();
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.MainContentProtocol
    public void showDelayNumber(int i) {
        long j;
        long round;
        if (this.mCaptureDelayNumber.getVisibility() != 0) {
            int topHeight = Util.getTopHeight();
            int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.capture_delay_number_text_size);
            int round2 = Math.round(((float) dimensionPixelSize) * 1.327f);
            int i2 = round2 - dimensionPixelSize;
            if (((BaseFragment) this).mCurrentMode == 165) {
                j = (long) topHeight;
                round = Math.round(((double) Util.sWindowWidth) * 0.0148d);
            } else if (this.mDisplayRectTopMargin == 0) {
                j = (long) topHeight;
                round = Math.round(((double) Util.sWindowWidth) * 0.0574d);
            } else {
                j = (long) topHeight;
                round = Math.round(((double) Util.sWindowWidth) * 0.0889d);
            }
            int i3 = ((int) (j + round)) - i2;
            Log.d(TAG, "showDelayNumber: topMargin = " + i3 + ", topHeight = " + Util.getTopHeight() + ", fontHeight = " + dimensionPixelSize + ", viewHeight = " + round2 + ", offset = " + i2);
            ((ViewGroup.MarginLayoutParams) this.mCaptureDelayNumber.getLayoutParams()).topMargin = i3;
            int i4 = this.mDegree;
            if (i4 > 0) {
                ViewCompat.setRotation(this.mCaptureDelayNumber, (float) i4);
            }
            Completable.create(new AlphaInOnSubscribe(this.mCaptureDelayNumber)).subscribe();
        }
        this.mCaptureDelayNumber.setText(String.valueOf(i));
    }

    @Override // com.android.camera.protocol.ModeProtocol.IndicatorProtocol
    public void showIndicator(int i, int i2) {
        if (i == 1) {
            showIndicator(this.mFaceView, i2);
        } else if (i == 2) {
            showIndicator(this.mFocusView, i2);
        } else if (i == 3) {
            showIndicator(this.mObjectView, i2);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.MainContentProtocol
    public void showReviewViews(Bitmap bitmap) {
        if (bitmap != null) {
            this.mPreviewPanel.mVideoReviewImage.setImageBitmap(bitmap);
            this.mPreviewPanel.mVideoReviewImage.setVisibility(0);
        }
        Util.fadeIn(this.mPreviewPanel.mVideoReviewPlay);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void unRegister(ModeProtocol.ModeCoordinator modeCoordinator) {
        super.unRegister(modeCoordinator);
        modeCoordinator.detachProtocol(166, this);
        unRegisterBackStack(modeCoordinator, this);
        modeCoordinator.detachProtocol(214, this);
        if (!b.isSupportedOpticalZoom() && !HybridZoomingSystem.IS_3_OR_MORE_SAT) {
            modeCoordinator.detachProtocol(184, this);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.MainContentProtocol
    public void updateCinematicAspectRatioSwitched(boolean z) {
        Log.i(TAG, "updateCinematicPhotoSwitched isSwitchOn : " + z);
        if (z) {
            if (this.mCoverParent.getLayoutParams().height == this.mNormalCoverHeight) {
                this.mCoverParent.getLayoutParams().height = -1;
                this.mCoverParent.requestLayout();
            }
            Completable.create(new SlideInOnSubscribe(this.mLeftCover, 3)).subscribe();
            Completable.create(new SlideInOnSubscribe(this.mRightCover, 5)).subscribe();
            return;
        }
        if (this.mLeftCover.getVisibility() == 0) {
            Completable.create(new SlideOutOnSubscribe(this.mLeftCover, 3).setDurationTime(200)).subscribe();
        }
        if (this.mRightCover.getVisibility() == 0) {
            Completable.create(new SlideOutOnSubscribe(this.mRightCover, 5).setDurationTime(200)).subscribe();
        }
        if (this.mCoverParent.getLayoutParams().height != this.mNormalCoverHeight) {
            this.mCoverParent.getLayoutParams().height = this.mNormalCoverHeight;
            this.mCoverParent.requestLayout();
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.MainContentProtocol
    public void updateContentDescription() {
        this.mPreviewFrame.setContentDescription(getString(R.string.accessibility_front_preview_status));
        this.mPreviewFrame.announceForAccessibility(getString(R.string.accessibility_front_preview_status));
    }

    @Override // com.android.camera.protocol.ModeProtocol.MainContentProtocol
    public void updateCurrentZoomRatio(float f2) {
        ZoomView zoomView = this.mZoomView;
        if (zoomView != null) {
            zoomView.setCurrentZoomRatio(f2);
        }
        ZoomView zoomView2 = this.mZoomViewHorizontal;
        if (zoomView2 != null) {
            zoomView2.setCurrentZoomRatio(f2);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.EffectCropViewController
    public void updateEffectViewVisible() {
        this.mEffectCropView.updateVisible();
    }

    @Override // com.android.camera.protocol.ModeProtocol.EffectCropViewController
    public void updateEffectViewVisible(int i) {
        this.mEffectCropView.updateVisible(i);
    }

    @Override // com.android.camera.protocol.ModeProtocol.IndicatorProtocol
    public void updateFaceView(boolean z, boolean z2, boolean z3, boolean z4, int i) {
        if (z2) {
            this.mFaceView.clear();
        }
        this.mFaceView.setVisibility(z ? 0 : 8);
        if (i > 0) {
            this.mFaceView.setCameraDisplayOrientation(i);
        }
        this.mFaceView.setMirror(z3);
        if (z4) {
            this.mFaceView.resume();
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.MainContentProtocol
    public void updateFocusMode(String str) {
        this.mFocusView.updateFocusMode(str);
    }

    @Override // com.android.camera.protocol.ModeProtocol.MainContentProtocol
    public void updateGradienterSwitched(boolean z) {
        updateReferenceGradienterSwitched();
    }

    @Override // com.android.camera.protocol.ModeProtocol.MainContentProtocol
    public void updateHistogramStatsData(int[] iArr) {
        HistogramView histogramView = this.mHistogramView;
        if (histogramView != null) {
            histogramView.updateStats(iArr);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.MainContentProtocol
    public void updateHistogramStatsData(int[] iArr, int[] iArr2, int[] iArr3) {
        this.mHistogramView.updateStats(iArr, iArr2, iArr3);
    }

    @Override // com.android.camera.protocol.ModeProtocol.LyingDirectHint
    public void updateLyingDirectHint(boolean z, boolean z2) {
        int i = this.mDegree;
        if ((i != 0 && i != 180) || (!z && !z2)) {
            if (!z2) {
                this.mIsShowMainLyingDirectHint = z;
            }
            int dimensionPixelSize = Util.sWindowHeight - ((((Util.sWindowWidth / 3) * 2) + CameraSettings.BOTTOM_CONTROL_HEIGHT) + (getResources().getDimensionPixelSize(R.dimen.lying_direct_hint_height) / 2));
            int dimensionPixelSize2 = getResources().getDimensionPixelSize(R.dimen.lying_direct_hint_margin_top);
            int dimensionPixelSize3 = (Util.sWindowWidth / 2) - (getResources().getDimensionPixelSize(R.dimen.lying_direct_hint_height) / 2);
            if (this.mIsShowMainLyingDirectHint && this.mLyingDirectHint.getVisibility() != 0) {
                ViewCompat.setTranslationX(this.mLyingDirectHint, (float) (this.mDegree == 90 ? dimensionPixelSize3 - dimensionPixelSize2 : dimensionPixelSize2 - dimensionPixelSize3));
                ViewCompat.setTranslationY(this.mLyingDirectHint, (float) dimensionPixelSize);
                ViewCompat.setRotation(this.mLyingDirectHint, (float) this.mDegree);
                this.mLyingDirectHint.setVisibility(0);
                CameraStatUtils.trackLyingDirectShow(this.mDegree);
            } else if (this.mLyingDirectHint.getVisibility() == 0) {
                this.mLyingDirectHint.setVisibility(8);
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.MainContentProtocol
    public void updateMimojiFaceDetectResultTip(boolean z) {
        this.mMimojiLightingView.post(new f(this, z));
    }

    @Override // com.android.camera.protocol.ModeProtocol.MainContentProtocol
    public void updateRGBHistogramSwitched(boolean z) {
        if (z) {
            AlphaInOnSubscribe.directSetResult(this.mHistogramView);
        } else {
            AlphaOutOnSubscribe.directSetResult(this.mHistogramView);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.MainContentProtocol
    public void updateReferenceLineSwitched(boolean z) {
        updateReferenceGradienterSwitched();
    }

    @Override // com.android.camera.protocol.ModeProtocol.MainContentProtocol
    public void updateWatermarkSample(WatermarkItem watermarkItem) {
        FrameLayout frameLayout;
        DataRepository.dataItemRunning().getComponentRunningAIWatermark().updateWatermarkItem(watermarkItem);
        if (watermarkItem != null && (frameLayout = this.mWatermarkLayout) != null && frameLayout.getVisibility() == 0) {
            updateWatermarkRotation(this.mDegree);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.MainContentProtocol
    public void updateZoomRatio(float f2, float f3) {
        ZoomView zoomView = this.mZoomView;
        if (zoomView != null) {
            zoomView.updateZoomRatio(f2, f3);
        }
        ZoomView zoomView2 = this.mZoomViewHorizontal;
        if (zoomView2 != null) {
            zoomView2.updateZoomRatio(f2, f3);
        }
    }
}
