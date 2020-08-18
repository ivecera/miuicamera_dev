package com.android.camera.fragment;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.annotation.UiThread;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.android.camera.ActivityBase;
import com.android.camera.CameraScreenNail;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.animation.type.AlphaInOnSubscribe;
import com.android.camera.animation.type.AlphaOutOnSubscribe;
import com.android.camera.effect.draw_mode.DrawExtTexAttribute;
import com.android.camera.effect.draw_mode.DrawRectAttribute;
import com.android.camera.log.Log;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera.ui.GLTextureView;
import com.android.camera.wideselfie.DrawImageView;
import com.android.camera.wideselfie.WideSelfieConfig;
import com.android.gallery3d.ui.GLCanvasImpl;
import com.android.gallery3d.ui.GLPaint;
import io.reactivex.Completable;
import java.util.List;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.opengles.GL10;

public class FragmentWideSelfie extends BaseFragment implements ModeProtocol.WideSelfieProtocol {
    private static final int ALPHA_ANIM_MILLIS = 300;
    public static final int FRAGMENT_INFO = 4094;
    private static final int GUIDE_HIDDEN_DELAY_MILLIS = 3000;
    private static final String TAG = "WideSelfie";
    /* access modifiers changed from: private */
    public ImageView mGuideImage;
    private boolean mIsShooting;
    private Handler mMainHandler;
    private DrawImageView mProgressImageView;
    /* access modifiers changed from: private */
    public GLTextureView mStillPreview;
    private int mStillPreviewHeight;
    private RelativeLayout mStillPreviewLayout;
    private int mStillPreviewWidth;
    private int mThumbBgHeight;
    private int mThumbBgHeightVertical;
    private int mThumbBgTopMargin;
    private int mThumbBgTopMarginVertical;
    private int mThumbBgWidth;
    private int mThumbBgWidthVertical;
    private ViewGroup mThumbNailBackground;
    private int mThumbViewHeight;
    private int mThumbViewHeightVertical;
    private int mThumbViewTopMargin;
    private int mThumbViewTopMarginVertical;
    private int mThumbViewWidth;
    private int mThumbViewWidthVertical;
    private TextView mUseHint;
    private TextView mUseHintLeft;
    private TextView mUseHintRight;
    private View mViewRoot;
    /* access modifiers changed from: private */
    public boolean mWaitingFirstFrame = false;

    private class StillPreviewRender implements GLSurfaceView.Renderer {
        private DrawExtTexAttribute mExtTexture;
        private GLPaint mGlPaint;
        private Handler mHandler;
        private DrawRectAttribute mRectAttribute;
        float[] mTransform;

        private StillPreviewRender() {
            this.mExtTexture = new DrawExtTexAttribute(true);
            this.mTransform = new float[16];
            this.mRectAttribute = new DrawRectAttribute();
            this.mGlPaint = new GLPaint(6.0f, FragmentWideSelfie.this.getContext().getColor(R.color.wide_selfie_thumbnail_border_color));
            this.mHandler = new Handler();
        }

        public void onDrawFrame(GL10 gl10) {
            CameraScreenNail cameraScreenNail = ((ActivityBase) FragmentWideSelfie.this.getContext()).getCameraScreenNail();
            GLCanvasImpl gLCanvas = ((ActivityBase) FragmentWideSelfie.this.getContext()).getGLView().getGLCanvas();
            if (cameraScreenNail != null && gLCanvas != null) {
                synchronized (gLCanvas) {
                    gLCanvas.clearBuffer();
                    int width = gLCanvas.getWidth();
                    int height = gLCanvas.getHeight();
                    gLCanvas.getState().pushState();
                    int width2 = FragmentWideSelfie.this.mStillPreview.getWidth();
                    int height2 = FragmentWideSelfie.this.mStillPreview.getHeight();
                    gLCanvas.setSize(width2, height2);
                    cameraScreenNail.getSurfaceTexture().getTransformMatrix(this.mTransform);
                    gLCanvas.draw(this.mExtTexture.init(cameraScreenNail.getExtTexture(), this.mTransform, 0, 0, width2, height2));
                    gLCanvas.draw(this.mRectAttribute.init(0.0f, 0.0f, (float) width2, (float) height2, this.mGlPaint));
                    gLCanvas.setSize(width, height);
                    gLCanvas.getState().popState();
                    gLCanvas.recycledResources();
                }
                if (FragmentWideSelfie.this.mWaitingFirstFrame) {
                    boolean unused = FragmentWideSelfie.this.mWaitingFirstFrame = false;
                    this.mHandler.post(new Runnable() {
                        /* class com.android.camera.fragment.FragmentWideSelfie.StillPreviewRender.AnonymousClass1 */

                        public void run() {
                            FragmentWideSelfie.this.mStillPreview.animate().alpha(1.0f);
                        }
                    });
                }
            }
        }

        public void onSurfaceChanged(GL10 gl10, int i, int i2) {
        }

        public void onSurfaceCreated(GL10 gl10, EGLConfig eGLConfig) {
        }
    }

    private void updateGuideVisibility() {
        if (isLandScape()) {
            this.mGuideImage.setVisibility(8);
            this.mMainHandler.removeCallbacksAndMessages(null);
        }
    }

    private void updateProgressImageViewLayout(boolean z) {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.mProgressImageView.getLayoutParams();
        if (z) {
            layoutParams.width = this.mThumbViewWidthVertical;
            layoutParams.height = this.mThumbViewHeightVertical;
            layoutParams.topMargin = this.mThumbViewTopMarginVertical;
            this.mProgressImageView.requestLayout();
            return;
        }
        layoutParams.width = this.mThumbViewWidth;
        layoutParams.height = this.mThumbViewHeight;
        layoutParams.topMargin = this.mThumbViewTopMargin;
        this.mProgressImageView.requestLayout();
    }

    private void updateStillPreviewLayout(boolean z) {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.mStillPreviewLayout.getLayoutParams();
        if (z) {
            layoutParams.topMargin = this.mThumbBgTopMarginVertical + 1 + (((this.mThumbBgHeightVertical - 2) - this.mStillPreviewHeight) / 2);
            this.mStillPreviewLayout.requestLayout();
            return;
        }
        layoutParams.topMargin = this.mThumbBgTopMargin + 1;
        this.mStillPreviewLayout.requestLayout();
    }

    private void updateThumbnailBackgroundLayout(boolean z) {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.mThumbNailBackground.getLayoutParams();
        if (z) {
            layoutParams.width = this.mThumbBgWidthVertical;
            layoutParams.height = this.mThumbBgHeightVertical;
            layoutParams.leftMargin = 0;
            layoutParams.rightMargin = 0;
            layoutParams.topMargin = this.mThumbBgTopMarginVertical;
            layoutParams.addRule(14);
            this.mThumbNailBackground.requestLayout();
            return;
        }
        layoutParams.width = this.mThumbBgWidth;
        layoutParams.height = this.mThumbBgHeight;
        layoutParams.leftMargin = 0;
        layoutParams.rightMargin = 0;
        layoutParams.topMargin = this.mThumbBgTopMargin;
        layoutParams.addRule(14);
        this.mThumbNailBackground.requestLayout();
    }

    private void updateUiLayout(boolean z) {
        AlphaOutOnSubscribe.directSetResult(this.mUseHint);
        AlphaOutOnSubscribe.directSetResult(this.mUseHintLeft);
        AlphaOutOnSubscribe.directSetResult(this.mUseHintRight);
        DrawImageView drawImageView = this.mProgressImageView;
        if (drawImageView != null) {
            drawImageView.setOrientation(((BaseFragment) this).mDegree);
        }
        if (isLandScape()) {
            Log.d(TAG, "updateUiLayout LandScape");
            if (isLeftLandScape()) {
                startAnimateViewVisible(this.mUseHintLeft, z);
            } else if (isRightLandScape()) {
                startAnimateViewVisible(this.mUseHintRight, z);
            }
            updateThumbnailBackgroundLayout(true);
            updateProgressImageViewLayout(true);
            updateStillPreviewLayout(true);
            return;
        }
        Log.d(TAG, "updateUiLayout Portrait");
        startAnimateViewVisible(this.mUseHint, z);
        updateThumbnailBackgroundLayout(false);
        updateProgressImageViewLayout(false);
        updateStillPreviewLayout(false);
    }

    @Override // com.android.camera.fragment.BaseFragment
    public int getFragmentInto() {
        return 4094;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public int getLayoutResourceId() {
        return R.layout.wideselfie_view;
    }

    @Override // com.android.camera.protocol.ModeProtocol.WideSelfieProtocol
    public void initPreviewLayout(int i, int i2, int i3, int i4) {
        ViewGroup.LayoutParams layoutParams = this.mStillPreview.getLayoutParams();
        layoutParams.width = i;
        layoutParams.height = i2;
        this.mStillPreview.requestLayout();
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void initView(View view) {
        Log.d(TAG, "initView");
        this.mViewRoot = view;
        this.mUseHint = (TextView) this.mViewRoot.findViewById(R.id.wideselfie_use_hint_text);
        this.mUseHintLeft = (TextView) this.mViewRoot.findViewById(R.id.wideselfie_use_hint_text_left);
        this.mUseHintRight = (TextView) this.mViewRoot.findViewById(R.id.wideselfie_use_hint_text_right);
        this.mGuideImage = (ImageView) this.mViewRoot.findViewById(R.id.wideselfie_guide_image);
        ViewCompat.setRotation(this.mUseHintLeft, 90.0f);
        ViewCompat.setRotation(this.mUseHintRight, -90.0f);
        this.mProgressImageView = (DrawImageView) this.mViewRoot.findViewById(R.id.wideselfie_progress_image);
        this.mStillPreview = (GLTextureView) this.mViewRoot.findViewById(R.id.wideselfie_still_preview);
        this.mStillPreviewLayout = (RelativeLayout) this.mViewRoot.findViewById(R.id.wideselfie_still_preview_layout);
        this.mThumbNailBackground = (ViewGroup) this.mViewRoot.findViewById(R.id.wideselfie_thumbnail_layout);
        this.mProgressImageView.setParams(this.mStillPreviewWidth, this.mStillPreviewHeight, this.mThumbBgWidth, this.mThumbBgHeightVertical);
        if (this.mStillPreview.getRenderer() == null) {
            StillPreviewRender stillPreviewRender = new StillPreviewRender();
            this.mStillPreview.setEGLContextClientVersion(2);
            this.mStillPreview.setEGLShareContextGetter(new GLTextureView.EGLShareContextGetter() {
                /* class com.android.camera.fragment.FragmentWideSelfie.AnonymousClass1 */

                @Override // com.android.camera.ui.GLTextureView.EGLShareContextGetter
                public EGLContext getShareContext() {
                    return ((ActivityBase) FragmentWideSelfie.this.getContext()).getGLView().getEGLContext();
                }
            });
            this.mStillPreview.setRenderer(stillPreviewRender);
            this.mStillPreview.setRenderMode(0);
            this.mStillPreview.onPause();
        }
        updateThumbnailBackgroundLayout(false);
        updateProgressImageViewLayout(false);
    }

    @Override // com.android.camera.fragment.BaseFragment, com.android.camera.animation.AnimationDelegate.AnimationResource
    public void notifyAfterFrameAvailable(int i) {
        super.notifyAfterFrameAvailable(i);
        if (((BaseFragment) this).mCurrentMode == 176 && this.mViewRoot.getVisibility() != 0) {
            showSmallPreview(true);
        }
    }

    @Override // android.support.v4.app.Fragment
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        WideSelfieConfig instance = WideSelfieConfig.getInstance(getContext());
        this.mStillPreviewWidth = instance.getStillPreviewWidth();
        this.mStillPreviewHeight = instance.getStillPreviewHeight();
        this.mThumbBgWidth = instance.getThumbBgWidth();
        this.mThumbBgHeight = instance.getThumbBgHeight();
        this.mThumbBgTopMargin = instance.getThumbBgTopMargin();
        this.mThumbBgWidthVertical = instance.getThumbBgWidthVertical();
        this.mThumbBgHeightVertical = instance.getThumbBgHeightVertical();
        this.mThumbBgTopMarginVertical = instance.getThumbBgTopMarginVertical();
        this.mThumbViewWidth = instance.getThumbViewWidth();
        this.mThumbViewHeight = instance.getThumbViewHeight();
        this.mThumbViewTopMargin = instance.getThumbViewTopMargin();
        this.mThumbViewWidthVertical = instance.getThumbViewWidthVertical();
        this.mThumbViewHeightVertical = instance.getThumbViewHeightVertical();
        this.mThumbViewTopMarginVertical = instance.getThumbViewTopMarginVertical();
        this.mMainHandler = new Handler();
    }

    @Override // android.support.v4.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        this.mMainHandler.removeCallbacksAndMessages(null);
    }

    @Override // android.support.v4.app.Fragment
    public void onPause() {
        super.onPause();
        this.mStillPreview.onPause();
    }

    @Override // com.android.camera.fragment.BaseFragment, com.android.camera.animation.AnimationDelegate.AnimationResource
    public void provideAnimateElement(int i, List<Completable> list, int i2) {
        super.provideAnimateElement(i, list, i2);
        if (i == 176) {
            this.mStillPreview.setAlpha(0.0f);
            this.mViewRoot.setVisibility(4);
        } else if (this.mViewRoot.getVisibility() != 0) {
        } else {
            if (list == null) {
                AlphaOutOnSubscribe.directSetResult(this.mViewRoot);
            } else {
                list.add(Completable.create(new AlphaOutOnSubscribe(this.mViewRoot).setDurationTime(150)));
            }
        }
    }

    @Override // com.android.camera.fragment.BaseFragment, com.android.camera.animation.AnimationDelegate.AnimationResource
    public void provideRotateItem(List<View> list, int i) {
        super.provideRotateItem(list, i);
        updateGuideVisibility();
        if (!this.mIsShooting) {
            updateUiLayout(false);
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void register(ModeProtocol.ModeCoordinator modeCoordinator) {
        super.register(modeCoordinator);
        modeCoordinator.attachProtocol(216, this);
    }

    @Override // com.android.camera.protocol.ModeProtocol.WideSelfieProtocol
    public void requestRender() {
        RelativeLayout relativeLayout = this.mStillPreviewLayout;
        if (relativeLayout != null && relativeLayout.isShown()) {
            this.mStillPreview.requestRender();
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.WideSelfieProtocol
    public void resetShootUI() {
        Log.d(TAG, "resetShootUI");
        this.mIsShooting = false;
        setClickEnable(true);
        this.mProgressImageView.setVisibility(8);
        this.mStillPreviewLayout.setVisibility(0);
        updateUiLayout(false);
        this.mStillPreview.onResume();
        this.mUseHint.setText(R.string.wideselfie_press_shoot_key_to_start);
        this.mUseHintLeft.setText(R.string.wideselfie_press_shoot_key_to_start);
        this.mUseHintRight.setText(R.string.wideselfie_press_shoot_key_to_start);
        this.mMainHandler.removeCallbacksAndMessages(null);
        if (this.mGuideImage.getVisibility() == 0) {
            Completable.create(new AlphaOutOnSubscribe(this.mGuideImage).setDurationTime(300)).subscribe();
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.WideSelfieProtocol
    public void setShootingUI() {
        Log.d(TAG, "setShootingUI");
        this.mIsShooting = true;
        setClickEnable(false);
        this.mProgressImageView.setImageBitmap(null, null, null);
        this.mProgressImageView.setVisibility(0);
        this.mMainHandler.removeCallbacksAndMessages(null);
        if (!isLandScape()) {
            Completable.create(new AlphaInOnSubscribe(this.mGuideImage).setDurationTime(300)).subscribe();
            this.mMainHandler.postDelayed(new Runnable() {
                /* class com.android.camera.fragment.FragmentWideSelfie.AnonymousClass2 */

                public void run() {
                    if (FragmentWideSelfie.this.isAdded()) {
                        Completable.create(new AlphaOutOnSubscribe(FragmentWideSelfie.this.mGuideImage).setDurationTime(300)).subscribe();
                    }
                }
            }, 3000);
        }
    }

    public void showSmallPreview(boolean z) {
        this.mStillPreview.requestLayout();
        this.mStillPreview.onResume();
        this.mStillPreview.requestRender();
        this.mStillPreviewLayout.setVisibility(0);
        this.mWaitingFirstFrame = true;
        if (z) {
            Completable.create(new AlphaInOnSubscribe(this.mViewRoot).setDurationTime(600)).subscribe();
        } else {
            AlphaInOnSubscribe.directSetResult(this.mViewRoot);
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void unRegister(ModeProtocol.ModeCoordinator modeCoordinator) {
        super.unRegister(modeCoordinator);
        modeCoordinator.detachProtocol(216, this);
    }

    @Override // com.android.camera.protocol.ModeProtocol.WideSelfieProtocol
    public void updateHintText(@StringRes int i) {
        this.mUseHint.setText(i);
        int i2 = R.string.wideselfie_rotate_to_right_slowly;
        if (i != R.string.wideselfie_rotate_to_left_slowly) {
            i2 = i == R.string.wideselfie_rotate_to_right_slowly ? R.string.wideselfie_rotate_to_left_slowly : i;
        }
        this.mUseHintRight.setText(i2);
        if (i == R.string.wideselfie_rotate_to_front) {
            i = R.string.wideselfie_rotate_to_back;
        } else if (i == R.string.wideselfie_rotate_to_back) {
            i = R.string.wideselfie_rotate_to_front;
        }
        this.mUseHintLeft.setText(i);
    }

    @Override // com.android.camera.protocol.ModeProtocol.WideSelfieProtocol
    @UiThread
    public void updatePreviewBitmap(@Nullable Bitmap bitmap, Rect rect, Rect rect2) {
        if (bitmap != null && this.mStillPreviewLayout.getVisibility() == 0) {
            this.mStillPreviewLayout.setVisibility(8);
        }
        this.mProgressImageView.setImageBitmap(bitmap, rect, rect2);
    }

    @Override // com.android.camera.protocol.ModeProtocol.WideSelfieProtocol
    public void updateThumbBackgroudLayout(boolean z, boolean z2, int i) {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.mThumbNailBackground.getLayoutParams();
        if (z) {
            layoutParams.height -= i;
            if (z2) {
                layoutParams.topMargin += i;
            }
            this.mThumbNailBackground.requestLayout();
            return;
        }
        int i2 = layoutParams.width;
        layoutParams.width = i2 - i;
        if (z2) {
            layoutParams.leftMargin = ((Util.sWindowWidth - i2) / 2) + i;
            layoutParams.removeRule(14);
        } else {
            layoutParams.leftMargin = (Util.sWindowWidth - i2) / 2;
            layoutParams.removeRule(14);
        }
        this.mThumbNailBackground.requestLayout();
    }
}
