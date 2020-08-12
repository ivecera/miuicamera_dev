package com.android.camera.animation;

import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;

public class FragmentAnimationFactory {
    public static Animation wrapperAnimation(Animation.AnimationListener animationListener, int... iArr) {
        Animation animation;
        AnimationSet animationSet = new AnimationSet(true);
        boolean z = false;
        for (int i : iArr) {
            if (i == 161) {
                animation = new AlphaAnimation(0.0f, 1.0f);
            } else if (i == 162) {
                animation = new AlphaAnimation(1.0f, 0.0f);
            } else if (i == 167) {
                animation = new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, 1.0f, 1, 0.0f);
            } else if (i != 168) {
                return null;
            } else {
                animation = new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, 0.0f, 1, 1.0f);
            }
            if (animationListener != null && !z) {
                animation.setAnimationListener(animationListener);
                z = true;
            }
            animation.setDuration(200);
            animation.setInterpolator(AnimationDelegate.DEFAULT_INTERPOLATOR);
            animationSet.addAnimation(animation);
        }
        return animationSet;
    }

    public static Animation wrapperAnimation(int... iArr) {
        return wrapperAnimation(null, iArr);
    }
}
