package com.android.camera.animation.type;

import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.view.View;

public class TranslateYAlphaInOnSubscribe extends BaseOnSubScribe {
    private int mDistanceY;

    public TranslateYAlphaInOnSubscribe(View view, int i) {
        super(view);
        this.mDistanceY = i;
    }

    public static void directSetResult(View view, int i) {
        BaseOnSubScribe.setAnimateViewVisible(view, 0);
        ViewCompat.setTranslationY(view, (float) i);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.animation.type.BaseOnSubScribe
    public ViewPropertyAnimatorCompat getAnimation() {
        BaseOnSubScribe.setAnimateViewVisible(((BaseOnSubScribe) this).mAniView, 0);
        return ViewCompat.animate(((BaseOnSubScribe) this).mAniView).translationY((float) this.mDistanceY).alpha(1.0f);
    }
}
