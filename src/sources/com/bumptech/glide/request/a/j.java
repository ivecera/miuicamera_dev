package com.bumptech.glide.request.a;

import android.view.View;
import com.bumptech.glide.request.a.f;

/* compiled from: ViewPropertyTransition */
public class j<R> implements f<R> {
    private final a animator;

    /* compiled from: ViewPropertyTransition */
    public interface a {
        void animate(View view);
    }

    public j(a aVar) {
        this.animator = aVar;
    }

    @Override // com.bumptech.glide.request.a.f
    public boolean a(R r, f.a aVar) {
        if (aVar.getView() == null) {
            return false;
        }
        this.animator.animate(aVar.getView());
        return false;
    }
}
