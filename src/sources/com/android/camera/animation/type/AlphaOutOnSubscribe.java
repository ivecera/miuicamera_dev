package com.android.camera.animation.type;

import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.view.View;

public class AlphaOutOnSubscribe extends BaseOnSubScribe {
    public AlphaOutOnSubscribe(View view) {
        super(view);
    }

    public static void directSetResult(View view) {
        BaseOnSubScribe.setAnimateViewVisible(view, 4);
    }

    public static boolean matchState(View view) {
        return ViewCompat.getAlpha(view) == 0.0f && view.getVisibility() != 0;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.animation.type.BaseOnSubScribe
    public ViewPropertyAnimatorCompat getAnimation() {
        BaseOnSubScribe.setAnimateViewVisible(((BaseOnSubScribe) this).mAniView, 0);
        ViewCompat.setAlpha(((BaseOnSubScribe) this).mAniView, 1.0f);
        return ViewCompat.animate(((BaseOnSubScribe) this).mAniView).alpha(0.0f);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.animation.type.BaseOnSubScribe
    public void onAnimationEnd() {
        super.onAnimationEnd();
        BaseOnSubScribe.setAnimateViewVisible(((BaseOnSubScribe) this).mAniView, ((BaseOnSubScribe) this).mTargetGone ? 8 : 4);
    }
}
