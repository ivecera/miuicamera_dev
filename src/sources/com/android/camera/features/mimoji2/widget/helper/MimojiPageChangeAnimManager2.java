package com.android.camera.features.mimoji2.widget.helper;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.features.mimoji2.widget.MimojiEditGLTextureView2;

public class MimojiPageChangeAnimManager2 {
    private int MOVE_TOP_DISPLACEMENT_OF_EDIT_LAYOUT;
    private int MOVE_TOP_DISPLACEMENT_OF_GL;
    private int editTabHeight;
    private int heightEditLayout;
    private int heightEditParentLayout;
    private int heightSurfacePixelBuffer;
    private RelativeLayout.LayoutParams layoutParamsEditLayout;
    /* access modifiers changed from: private */
    public RelativeLayout.LayoutParams layoutParamsTextureView;
    private Context mContext;
    public int[] mLocationAllEditContent = new int[3];
    public int[] mLocationTextureView = new int[3];
    /* access modifiers changed from: private */
    public MimojiEditGLTextureView2 mMimojiEditGLTextureView2;
    /* access modifiers changed from: private */
    public LinearLayout mRlAllEditContent;
    int navigationBackHeight;
    private Resources resources;
    private int screenHeight;
    private int screenWidth;
    private int widthSurfacePixel;
    int xCoordinateStartTexture;

    public int getNavigationBarHeight(Context context) {
        if (Util.checkDeviceHasNavigationBar(context)) {
            return Util.getNavigationBarHeight(context);
        }
        return 0;
    }

    public void initView(Context context, MimojiEditGLTextureView2 mimojiEditGLTextureView2, LinearLayout linearLayout, int i) {
        this.mContext = context;
        this.mMimojiEditGLTextureView2 = mimojiEditGLTextureView2;
        this.mRlAllEditContent = linearLayout;
        this.resources = this.mContext.getResources();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) this.mContext.getSystemService("window")).getDefaultDisplay().getMetrics(displayMetrics);
        this.screenWidth = displayMetrics.widthPixels;
        this.screenHeight = displayMetrics.heightPixels;
        this.widthSurfacePixel = this.resources.getDimensionPixelSize(R.dimen.mimoji_edit_surface_width);
        this.heightSurfacePixelBuffer = this.resources.getDimensionPixelSize(R.dimen.mimoji_edit_surface_buffer);
        this.editTabHeight = this.resources.getDimensionPixelSize(R.dimen.mimoji_edit_tab_height);
        this.heightEditParentLayout = this.screenHeight - this.navigationBackHeight;
        resetViewsCorridinate(i);
    }

    public void resetEditLayoutViewPosition() {
        if (this.mLocationAllEditContent[1] == 0 || this.layoutParamsEditLayout == null) {
            this.heightEditLayout = (this.screenHeight / 2) + this.editTabHeight + getNavigationBarHeight(this.mContext);
            int[] iArr = this.mLocationAllEditContent;
            iArr[0] = 0;
            int i = this.heightEditParentLayout;
            int i2 = this.heightEditLayout;
            iArr[1] = i - i2;
            this.MOVE_TOP_DISPLACEMENT_OF_EDIT_LAYOUT = i2;
            this.layoutParamsEditLayout = new RelativeLayout.LayoutParams(this.screenWidth, i2);
        }
        this.layoutParamsEditLayout.setMarginStart(this.mLocationAllEditContent[0]);
        RelativeLayout.LayoutParams layoutParams = this.layoutParamsEditLayout;
        layoutParams.topMargin = this.mLocationAllEditContent[1];
        this.mRlAllEditContent.setLayoutParams(layoutParams);
    }

    public void resetLayoutPosition(int i) {
        resetEditLayoutViewPosition();
        resetTextureViewPosition(i);
    }

    public void resetTextureViewPosition(int i) {
        if (this.layoutParamsTextureView == null) {
            int i2 = this.widthSurfacePixel;
            this.layoutParamsTextureView = new RelativeLayout.LayoutParams(i2, i2);
        }
        if (i == 6) {
            this.navigationBackHeight = this.resources.getDimensionPixelSize(R.dimen.mimoji_edit_back_height);
            this.resources.getDimensionPixelSize(R.dimen.mimoji_edit_back_top);
            this.xCoordinateStartTexture = (this.screenWidth / 2) - (this.widthSurfacePixel / 2);
            int i3 = this.xCoordinateStartTexture;
            int[] iArr = this.mLocationTextureView;
            iArr[0] = i3;
            iArr[1] = this.heightSurfacePixelBuffer + i3;
        } else if (i == 4 || i == 2) {
            this.xCoordinateStartTexture = (this.screenWidth / 2) - (this.widthSurfacePixel / 2);
            int[] iArr2 = this.mLocationTextureView;
            iArr2[0] = this.xCoordinateStartTexture;
            iArr2[1] = 0;
        }
        updateTextureViewPosition();
    }

    public void resetViewsCorridinate(int i) {
        resetTextureViewPosition(i);
        resetEditLayoutViewPosition();
    }

    public void translateYEditLayout() {
        TranslateAnimation translateAnimation = new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, 1.0f, 1, 0.0f);
        translateAnimation.setDuration(500);
        translateAnimation.setAnimationListener(new Animation.AnimationListener() {
            /* class com.android.camera.features.mimoji2.widget.helper.MimojiPageChangeAnimManager2.AnonymousClass3 */

            public void onAnimationEnd(Animation animation) {
            }

            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationStart(Animation animation) {
                MimojiPageChangeAnimManager2.this.mRlAllEditContent.setVisibility(0);
            }
        });
        this.mRlAllEditContent.startAnimation(translateAnimation);
    }

    public void translateYTextureView() {
        final ValueAnimator ofFloat = ValueAnimator.ofFloat((float) this.mLocationTextureView[1], 0.0f);
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            /* class com.android.camera.features.mimoji2.widget.helper.MimojiPageChangeAnimManager2.AnonymousClass1 */

            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                MimojiPageChangeAnimManager2.this.layoutParamsTextureView.topMargin = (int) ((Float) ofFloat.getAnimatedValue()).floatValue();
                MimojiPageChangeAnimManager2.this.mMimojiEditGLTextureView2.setLayoutParams(MimojiPageChangeAnimManager2.this.layoutParamsTextureView);
            }
        });
        ofFloat.addListener(new Animator.AnimatorListener() {
            /* class com.android.camera.features.mimoji2.widget.helper.MimojiPageChangeAnimManager2.AnonymousClass2 */

            public void onAnimationCancel(Animator animator) {
            }

            public void onAnimationEnd(Animator animator) {
                MimojiPageChangeAnimManager2 mimojiPageChangeAnimManager2 = MimojiPageChangeAnimManager2.this;
                mimojiPageChangeAnimManager2.mLocationTextureView[1] = 0;
                RelativeLayout.LayoutParams access$000 = mimojiPageChangeAnimManager2.layoutParamsTextureView;
                MimojiPageChangeAnimManager2 mimojiPageChangeAnimManager22 = MimojiPageChangeAnimManager2.this;
                access$000.topMargin = mimojiPageChangeAnimManager22.mLocationTextureView[1];
                mimojiPageChangeAnimManager22.mMimojiEditGLTextureView2.setLayoutParams(MimojiPageChangeAnimManager2.this.layoutParamsTextureView);
            }

            public void onAnimationRepeat(Animator animator) {
            }

            public void onAnimationStart(Animator animator) {
            }
        });
        ofFloat.setDuration(500L);
        ofFloat.start();
    }

    public void updateEditLayoutViewPosition() {
        this.layoutParamsEditLayout.setMarginStart(this.mLocationAllEditContent[0]);
        RelativeLayout.LayoutParams layoutParams = this.layoutParamsEditLayout;
        layoutParams.topMargin = this.mLocationAllEditContent[1];
        this.mRlAllEditContent.setLayoutParams(layoutParams);
    }

    public void updateLayoutPosition() {
        updateTextureViewPosition();
        updateEditLayoutViewPosition();
    }

    public void updateOperateState(int i) {
        if (i == 2) {
            translateYTextureView();
            translateYEditLayout();
        }
    }

    public void updateTextureViewPosition() {
        this.layoutParamsTextureView.setMarginStart(this.mLocationTextureView[0]);
        RelativeLayout.LayoutParams layoutParams = this.layoutParamsTextureView;
        layoutParams.topMargin = this.mLocationTextureView[1];
        this.mMimojiEditGLTextureView2.setLayoutParams(layoutParams);
    }
}
