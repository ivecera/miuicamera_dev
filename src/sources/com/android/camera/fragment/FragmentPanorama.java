package com.android.camera.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.opengl.GLSurfaceView;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.android.camera.ActivityBase;
import com.android.camera.CameraScreenNail;
import com.android.camera.CameraSettings;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.animation.type.AlphaInOnSubscribe;
import com.android.camera.animation.type.AlphaOutOnSubscribe;
import com.android.camera.effect.draw_mode.DrawExtTexAttribute;
import com.android.camera.log.Log;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera.statistic.CameraStatUtils;
import com.android.camera.ui.GLTextureView;
import com.android.camera.ui.PanoMovingIndicatorView;
import com.android.camera.ui.drawable.PanoramaArrowAnimateDrawable;
import com.android.gallery3d.ui.GLCanvasImpl;
import d.h.a.k;
import io.reactivex.Completable;
import java.util.List;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.opengles.GL10;

public class FragmentPanorama extends BaseFragment implements ModeProtocol.PanoramaProtocol, CameraScreenNail.RequestRenderListener, View.OnClickListener {
    public static final int FRAGMENT_INFO = 4080;
    public static final String TAG = "FragmentPanorama";
    private int mArrowMargin;
    private Interpolator mCubicEaseInOutInterpolator = new k();
    /* access modifiers changed from: private */
    public Handler mHandler = new Handler();
    private ViewGroup mHintFrame;
    /* access modifiers changed from: private */
    public ImageView mIndicator;
    /* access modifiers changed from: private */
    public int mMoveDirection = -1;
    /* access modifiers changed from: private */
    public View mMoveReferenceLine;
    private PanoMovingIndicatorView mMovingDirectionView;
    private PanoramaArrowAnimateDrawable mPanoramaArrowAnimateDrawable = new PanoramaArrowAnimateDrawable();
    private ImageView mPanoramaPreview;
    private View mPanoramaViewRoot;
    /* access modifiers changed from: private */
    public GLTextureView mStillPreview;
    private View mStillPreviewHintArea;
    /* access modifiers changed from: private */
    public int mStillPreviewTextureHeight;
    /* access modifiers changed from: private */
    public int mStillPreviewTextureOffsetX;
    /* access modifiers changed from: private */
    public int mStillPreviewTextureOffsetY;
    /* access modifiers changed from: private */
    public int mStillPreviewTextureWidth;
    private TextView mUseHint;
    private View mUseHintArea;
    /* access modifiers changed from: private */
    public boolean mWaitingFirstFrame = false;

    private class StillPreviewRender implements GLSurfaceView.Renderer {
        private DrawExtTexAttribute mExtTexture;
        float[] mTransform;

        private StillPreviewRender() {
            this.mExtTexture = new DrawExtTexAttribute(true);
            this.mTransform = new float[16];
        }

        public void onDrawFrame(GL10 gl10) {
            CameraScreenNail cameraScreenNail = ((ActivityBase) FragmentPanorama.this.getContext()).getCameraScreenNail();
            GLCanvasImpl gLCanvas = ((ActivityBase) FragmentPanorama.this.getContext()).getGLView().getGLCanvas();
            if (cameraScreenNail != null && gLCanvas != null) {
                synchronized (gLCanvas) {
                    gLCanvas.clearBuffer();
                    int width = gLCanvas.getWidth();
                    int height = gLCanvas.getHeight();
                    gLCanvas.getState().pushState();
                    gLCanvas.setSize(FragmentPanorama.this.mStillPreview.getWidth(), FragmentPanorama.this.mStillPreview.getHeight());
                    cameraScreenNail.getSurfaceTexture().getTransformMatrix(this.mTransform);
                    gLCanvas.draw(this.mExtTexture.init(cameraScreenNail.getExtTexture(), this.mTransform, FragmentPanorama.this.mStillPreviewTextureOffsetX, FragmentPanorama.this.mStillPreviewTextureOffsetY, FragmentPanorama.this.mStillPreviewTextureWidth, FragmentPanorama.this.mStillPreviewTextureHeight));
                    gLCanvas.setSize(width, height);
                    gLCanvas.getState().popState();
                    gLCanvas.recycledResources();
                }
                if (FragmentPanorama.this.mWaitingFirstFrame) {
                    boolean unused = FragmentPanorama.this.mWaitingFirstFrame = false;
                    FragmentPanorama.this.mHandler.post(new Runnable() {
                        /* class com.android.camera.fragment.FragmentPanorama.StillPreviewRender.AnonymousClass1 */

                        public void run() {
                            FragmentPanorama.this.mStillPreview.animate().alpha(1.0f);
                            FragmentPanorama.this.mMoveReferenceLine.setVisibility(0);
                            FragmentPanorama.this.mIndicator.setVisibility(0);
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

    private void setViewMargin(View view, int i) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        marginLayoutParams.topMargin = i;
        view.setLayoutParams(marginLayoutParams);
    }

    @Override // com.android.camera.fragment.BaseFragment
    public int getFragmentInto() {
        return 4080;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public int getLayoutResourceId() {
        return R.layout.pano_view;
    }

    @Override // com.android.camera.protocol.ModeProtocol.PanoramaProtocol
    public ViewGroup getPreivewContainer() {
        return this.mHintFrame;
    }

    @Override // com.android.camera.protocol.ModeProtocol.PanoramaProtocol
    public void initPreviewLayout(int i, int i2, int i3, int i4) {
        this.mMoveDirection = CameraSettings.getPanoramaMoveDirection(getContext());
        ViewGroup.LayoutParams layoutParams = this.mStillPreview.getLayoutParams();
        layoutParams.width = i;
        layoutParams.height = i2;
        int i5 = layoutParams.width;
        this.mStillPreviewTextureWidth = i5;
        this.mStillPreviewTextureHeight = (i5 * i3) / i4;
        this.mStillPreviewTextureOffsetX = 0;
        this.mStillPreviewTextureOffsetY = (-(this.mStillPreviewTextureHeight - layoutParams.height)) / 2;
        this.mStillPreview.requestLayout();
        this.mUseHint.setText(R.string.pano_how_to_use_prompt_start);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void initView(View view) {
        this.mPanoramaViewRoot = view;
        this.mIndicator = (ImageView) this.mPanoramaViewRoot.findViewById(R.id.pano_arrow);
        this.mUseHint = (TextView) this.mPanoramaViewRoot.findViewById(R.id.pano_use_hint);
        this.mIndicator.setImageDrawable(this.mPanoramaArrowAnimateDrawable);
        this.mArrowMargin = getResources().getDimensionPixelSize(R.dimen.pano_arrow_margin);
        setViewMargin(this.mPanoramaViewRoot, Util.sTopMargin);
        this.mPanoramaPreview = (ImageView) this.mPanoramaViewRoot.findViewById(R.id.panorama_image_preview);
        this.mStillPreview = (GLTextureView) this.mPanoramaViewRoot.findViewById(R.id.panorama_still_preview);
        this.mMovingDirectionView = (PanoMovingIndicatorView) this.mPanoramaViewRoot.findViewById(R.id.pano_move_direction_view);
        this.mMoveReferenceLine = this.mPanoramaViewRoot.findViewById(R.id.pano_move_reference_line);
        this.mStillPreviewHintArea = this.mPanoramaViewRoot.findViewById(R.id.pano_still_preview_hint_area);
        this.mHintFrame = (ViewGroup) this.mPanoramaViewRoot.findViewById(R.id.pano_preview_hint_frame);
        setViewMargin(this.mHintFrame, Util.sTopBarHeight + Math.round(((float) Util.sCenterDisplayHeight) * 0.25f));
        this.mHintFrame.setOnClickListener(this);
        if (this.mStillPreview.getRenderer() == null) {
            StillPreviewRender stillPreviewRender = new StillPreviewRender();
            this.mStillPreview.setEGLContextClientVersion(2);
            this.mStillPreview.setEGLShareContextGetter(new GLTextureView.EGLShareContextGetter() {
                /* class com.android.camera.fragment.FragmentPanorama.AnonymousClass1 */

                @Override // com.android.camera.ui.GLTextureView.EGLShareContextGetter
                public EGLContext getShareContext() {
                    return ((ActivityBase) FragmentPanorama.this.getContext()).getGLView().getEGLContext();
                }
            });
            this.mStillPreview.setRenderer(stillPreviewRender);
            this.mStillPreview.setRenderMode(0);
            this.mStillPreview.onPause();
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.PanoramaProtocol
    public boolean isShown() {
        return this.mPanoramaViewRoot.isShown();
    }

    public void moveToDirection(int i) {
        this.mMoveDirection = i;
        float translationX = this.mIndicator.getTranslationX();
        float transformationRatio = this.mPanoramaArrowAnimateDrawable.getTransformationRatio();
        int i2 = this.mMoveDirection;
        if (i2 == 3) {
            translationX = (float) (this.mStillPreviewTextureWidth + this.mArrowMargin);
            transformationRatio = 2.0f;
        } else if (i2 == 4) {
            translationX = (float) (((Util.sWindowWidth - this.mStillPreviewTextureWidth) - this.mArrowMargin) - this.mIndicator.getWidth());
            transformationRatio = 0.0f;
        }
        ImageView imageView = this.mIndicator;
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(imageView, "translationX", imageView.getTranslationX(), translationX);
        ofFloat.setDuration(500L);
        PanoramaArrowAnimateDrawable panoramaArrowAnimateDrawable = this.mPanoramaArrowAnimateDrawable;
        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(panoramaArrowAnimateDrawable, "transformationRatio", panoramaArrowAnimateDrawable.getTransformationRatio(), transformationRatio);
        ofFloat2.setDuration(500L);
        GLTextureView gLTextureView = this.mStillPreview;
        ObjectAnimator ofFloat3 = ObjectAnimator.ofFloat(gLTextureView, "alpha", gLTextureView.getAlpha(), 0.0f);
        ofFloat3.setDuration(250L);
        ObjectAnimator ofFloat4 = ObjectAnimator.ofFloat(this.mStillPreview, "alpha", 0.0f, 1.0f);
        ofFloat4.setDuration(250L);
        ofFloat4.addListener(new AnimatorListenerAdapter() {
            /* class com.android.camera.fragment.FragmentPanorama.AnonymousClass2 */

            public void onAnimationStart(Animator animator) {
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) FragmentPanorama.this.mStillPreview.getLayoutParams();
                if (FragmentPanorama.this.mMoveDirection == 4) {
                    layoutParams.removeRule(9);
                    layoutParams.addRule(11);
                } else if (FragmentPanorama.this.mMoveDirection == 3) {
                    layoutParams.removeRule(11);
                    layoutParams.addRule(9);
                }
                FragmentPanorama.this.mStillPreview.requestLayout();
            }
        });
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(ofFloat3, ofFloat4);
        AnimatorSet animatorSet2 = new AnimatorSet();
        animatorSet2.playTogether(ofFloat, ofFloat2, animatorSet);
        animatorSet2.setInterpolator(this.mCubicEaseInOutInterpolator);
        animatorSet2.start();
    }

    @Override // com.android.camera.fragment.BaseFragment, com.android.camera.animation.AnimationDelegate.AnimationResource
    public void notifyAfterFrameAvailable(int i) {
        super.notifyAfterFrameAvailable(i);
        if (((BaseFragment) this).mCurrentMode == 166 && this.mPanoramaViewRoot.getVisibility() != 0) {
            showSmallPreview(true);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.PanoramaProtocol
    public void onCaptureOrientationDecided(int i, int i2, int i3) {
        this.mStillPreviewHintArea.setVisibility(8);
        this.mStillPreview.onPause();
        this.mUseHint.setText(R.string.pano_how_to_use_prompt_go_on_moving);
        this.mMovingDirectionView.setVisibility(0);
        this.mMovingDirectionView.setDisplayCenterY(Util.getRelativeLocation(this.mMovingDirectionView, this.mMoveReferenceLine)[1] + (this.mMoveReferenceLine.getHeight() / 2));
        this.mMovingDirectionView.setMovingAttribute(i, i2, i3);
    }

    public void onClick(View view) {
        if (isEnableClick()) {
            int i = this.mMoveDirection;
            if (i == 4) {
                CameraStatUtils.trackDirectionChanged(3);
                moveToDirection(3);
            } else if (i == 3) {
                CameraStatUtils.trackDirectionChanged(4);
                moveToDirection(4);
            }
            CameraSettings.setPanoramaMoveDirection(this.mMoveDirection);
        }
    }

    @Override // android.support.v4.app.Fragment
    public void onPause() {
        super.onPause();
        this.mStillPreview.onPause();
        this.mPanoramaPreview.setImageDrawable(null);
    }

    @Override // com.android.camera.protocol.ModeProtocol.PanoramaProtocol
    public void onPreviewMoving() {
        if (this.mStillPreviewHintArea.getVisibility() == 0) {
            return;
        }
        if (this.mMovingDirectionView.isTooFast()) {
            this.mUseHint.setText(R.string.pano_how_to_use_prompt_slow_down);
        } else if (this.mMovingDirectionView.isFar()) {
            this.mUseHint.setText(R.string.pano_how_to_use_prompt_align_reference_line);
        } else {
            this.mUseHint.setText(R.string.pano_how_to_use_prompt_go_on_moving);
        }
    }

    @Override // com.android.camera.fragment.BaseFragment, android.support.v4.app.Fragment
    public void onResume() {
        super.onResume();
        this.mStillPreview.setAlpha(0.0f);
        this.mPanoramaViewRoot.setVisibility(4);
        this.mMoveReferenceLine.setVisibility(4);
        this.mIndicator.setVisibility(4);
    }

    @Override // com.android.camera.fragment.BaseFragment, com.android.camera.animation.AnimationDelegate.AnimationResource
    public void provideAnimateElement(int i, List<Completable> list, int i2) {
        super.provideAnimateElement(i, list, i2);
        if (i != 166 && this.mPanoramaViewRoot.getVisibility() == 0) {
            if (list == null) {
                AlphaOutOnSubscribe.directSetResult(this.mPanoramaViewRoot);
            } else {
                list.add(Completable.create(new AlphaOutOnSubscribe(this.mPanoramaViewRoot).setDurationTime(150)));
            }
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void register(ModeProtocol.ModeCoordinator modeCoordinator) {
        super.register(modeCoordinator);
        modeCoordinator.attachProtocol(176, this);
    }

    @Override // com.android.camera.protocol.ModeProtocol.PanoramaProtocol, com.android.camera.CameraScreenNail.RequestRenderListener
    public void requestRender() {
        View view = this.mStillPreviewHintArea;
        if (view != null && view.isShown()) {
            this.mStillPreview.requestRender();
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.PanoramaProtocol
    public void resetShootUI() {
        Log.d(TAG, "resetShootUI");
        setClickEnable(true);
        this.mStillPreviewHintArea.setVisibility(8);
        this.mStillPreview.onPause();
        this.mMovingDirectionView.setVisibility(8);
        this.mUseHint.setText(R.string.pano_how_to_use_prompt_start);
    }

    @Override // com.android.camera.protocol.ModeProtocol.PanoramaProtocol
    public void setDirectionPosition(Point point, int i) {
        this.mMovingDirectionView.setPosition(point, i);
    }

    @Override // com.android.camera.protocol.ModeProtocol.PanoramaProtocol
    public void setDirectionTooFast(boolean z, int i) {
        this.mMovingDirectionView.setTooFast(z, i);
    }

    @Override // com.android.camera.protocol.ModeProtocol.PanoramaProtocol
    public void setDisplayPreviewBitmap(Bitmap bitmap) {
        if (bitmap == null) {
            this.mPanoramaPreview.setImageDrawable(null);
        } else {
            this.mPanoramaPreview.setImageBitmap(bitmap);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.PanoramaProtocol
    public void setShootUI() {
        Log.d(TAG, "setShootUI");
        setClickEnable(false);
        this.mMovingDirectionView.setVisibility(8);
        this.mStillPreviewHintArea.setVisibility(0);
    }

    @Override // com.android.camera.protocol.ModeProtocol.PanoramaProtocol
    public void showSmallPreview(boolean z) {
        this.mMovingDirectionView.setVisibility(8);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.mStillPreview.getLayoutParams();
        int i = this.mMoveDirection;
        if (i == 4) {
            layoutParams.removeRule(9);
            layoutParams.addRule(11);
            ImageView imageView = this.mIndicator;
            imageView.setTranslationX((float) (((Util.sWindowWidth - this.mStillPreviewTextureWidth) - this.mArrowMargin) - imageView.getWidth()));
            this.mPanoramaArrowAnimateDrawable.setTransformationRatio(0.0f);
        } else if (i == 3) {
            layoutParams.removeRule(11);
            layoutParams.addRule(9);
            this.mIndicator.setTranslationX((float) (this.mStillPreviewTextureWidth + this.mArrowMargin));
            this.mPanoramaArrowAnimateDrawable.setTransformationRatio(2.0f);
        }
        this.mStillPreview.requestLayout();
        this.mStillPreview.onResume();
        this.mStillPreview.requestRender();
        this.mStillPreviewHintArea.setVisibility(0);
        this.mWaitingFirstFrame = true;
        if (z) {
            Completable.create(new AlphaInOnSubscribe(this.mPanoramaViewRoot).setDurationTime(600)).subscribe();
        } else {
            AlphaInOnSubscribe.directSetResult(this.mPanoramaViewRoot);
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void unRegister(ModeProtocol.ModeCoordinator modeCoordinator) {
        super.unRegister(modeCoordinator);
        modeCoordinator.detachProtocol(176, this);
    }
}
