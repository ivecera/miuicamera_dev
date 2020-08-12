package com.bumptech.glide.request.a;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.request.a.f;

/* compiled from: BitmapContainerTransitionFactory */
public abstract class a<R> implements g<R> {
    private final g<Drawable> Xt;

    /* renamed from: com.bumptech.glide.request.a.a$a  reason: collision with other inner class name */
    /* compiled from: BitmapContainerTransitionFactory */
    private final class C0014a implements f<R> {
        private final f<Drawable> transition;

        C0014a(f<Drawable> fVar) {
            this.transition = fVar;
        }

        @Override // com.bumptech.glide.request.a.f
        public boolean a(R r, f.a aVar) {
            return this.transition.a(new BitmapDrawable(aVar.getView().getResources(), a.this.q(r)), aVar);
        }
    }

    public a(g<Drawable> gVar) {
        this.Xt = gVar;
    }

    @Override // com.bumptech.glide.request.a.g
    public f<R> a(DataSource dataSource, boolean z) {
        return new C0014a(this.Xt.a(dataSource, z));
    }

    /* access modifiers changed from: protected */
    public abstract Bitmap q(R r);
}
