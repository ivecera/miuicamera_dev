package com.bumptech.glide.request.target;

import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import com.bumptech.glide.request.c;
import com.bumptech.glide.util.i;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class ViewTarget<T extends View, Z> extends b<Z> {
    private static boolean Qt = false;
    @Nullable
    private static Integer Rt = null;
    private static final String TAG = "ViewTarget";
    private final SizeDeterminer Mt;
    @Nullable
    private View.OnAttachStateChangeListener Nt;
    private boolean Ot;
    private boolean Pt;
    protected final T view;

    @VisibleForTesting
    static final class SizeDeterminer {
        private static final int Wt = 0;
        @VisibleForTesting
        @Nullable
        static Integer maxDisplayLength;
        boolean Ut;
        @Nullable
        private a Vt;
        private final List<n> rn = new ArrayList();
        private final View view;

        private static final class a implements ViewTreeObserver.OnPreDrawListener {
            private final WeakReference<SizeDeterminer> Tt;

            a(@NonNull SizeDeterminer sizeDeterminer) {
                this.Tt = new WeakReference<>(sizeDeterminer);
            }

            public boolean onPreDraw() {
                if (Log.isLoggable(ViewTarget.TAG, 2)) {
                    Log.v(ViewTarget.TAG, "OnGlobalLayoutListener called attachStateListener=" + this);
                }
                SizeDeterminer sizeDeterminer = this.Tt.get();
                if (sizeDeterminer == null) {
                    return true;
                }
                sizeDeterminer.Hk();
                return true;
            }
        }

        SizeDeterminer(@NonNull View view2) {
            this.view = view2;
        }

        private static int O(@NonNull Context context) {
            if (maxDisplayLength == null) {
                WindowManager windowManager = (WindowManager) context.getSystemService("window");
                i.checkNotNull(windowManager);
                Display defaultDisplay = windowManager.getDefaultDisplay();
                Point point = new Point();
                defaultDisplay.getSize(point);
                maxDisplayLength = Integer.valueOf(Math.max(point.x, point.y));
            }
            return maxDisplayLength.intValue();
        }

        private int d(int i, int i2, int i3) {
            int i4 = i2 - i3;
            if (i4 > 0) {
                return i4;
            }
            if (this.Ut && this.view.isLayoutRequested()) {
                return 0;
            }
            int i5 = i - i3;
            if (i5 > 0) {
                return i5;
            }
            if (this.view.isLayoutRequested() || i2 != -2) {
                return 0;
            }
            if (Log.isLoggable(ViewTarget.TAG, 4)) {
                Log.i(ViewTarget.TAG, "Glide treats LayoutParams.WRAP_CONTENT as a request for an image the size of this device's screen dimensions. If you want to load the original image and are ok with the corresponding memory cost and OOMs (depending on the input size), use .override(Target.SIZE_ORIGINAL). Otherwise, use LayoutParams.MATCH_PARENT, set layout_width and layout_height to fixed dimension, or use .override() with fixed dimensions.");
            }
            return O(this.view.getContext());
        }

        private boolean ea(int i) {
            return i > 0 || i == Integer.MIN_VALUE;
        }

        private int eo() {
            int paddingTop = this.view.getPaddingTop() + this.view.getPaddingBottom();
            ViewGroup.LayoutParams layoutParams = this.view.getLayoutParams();
            return d(this.view.getHeight(), layoutParams != null ? layoutParams.height : 0, paddingTop);
        }

        private int fo() {
            int paddingLeft = this.view.getPaddingLeft() + this.view.getPaddingRight();
            ViewGroup.LayoutParams layoutParams = this.view.getLayoutParams();
            return d(this.view.getWidth(), layoutParams != null ? layoutParams.width : 0, paddingLeft);
        }

        private boolean s(int i, int i2) {
            return ea(i) && ea(i2);
        }

        private void t(int i, int i2) {
            Iterator it = new ArrayList(this.rn).iterator();
            while (it.hasNext()) {
                ((n) it.next()).a(i, i2);
            }
        }

        /* access modifiers changed from: package-private */
        public void Hk() {
            if (!this.rn.isEmpty()) {
                int fo = fo();
                int eo = eo();
                if (s(fo, eo)) {
                    t(fo, eo);
                    Ik();
                }
            }
        }

        /* access modifiers changed from: package-private */
        public void Ik() {
            ViewTreeObserver viewTreeObserver = this.view.getViewTreeObserver();
            if (viewTreeObserver.isAlive()) {
                viewTreeObserver.removeOnPreDrawListener(this.Vt);
            }
            this.Vt = null;
            this.rn.clear();
        }

        /* access modifiers changed from: package-private */
        public void a(@NonNull n nVar) {
            this.rn.remove(nVar);
        }

        /* access modifiers changed from: package-private */
        public void b(@NonNull n nVar) {
            int fo = fo();
            int eo = eo();
            if (s(fo, eo)) {
                nVar.a(fo, eo);
                return;
            }
            if (!this.rn.contains(nVar)) {
                this.rn.add(nVar);
            }
            if (this.Vt == null) {
                ViewTreeObserver viewTreeObserver = this.view.getViewTreeObserver();
                this.Vt = new a(this);
                viewTreeObserver.addOnPreDrawListener(this.Vt);
            }
        }
    }

    public ViewTarget(@NonNull T t) {
        i.checkNotNull(t);
        this.view = t;
        this.Mt = new SizeDeterminer(t);
    }

    @Deprecated
    public ViewTarget(@NonNull T t, boolean z) {
        this(t);
        if (z) {
            Gk();
        }
    }

    public static void P(int i) {
        if (Rt != null || Qt) {
            throw new IllegalArgumentException("You cannot set the tag id more than once or change the tag id after the first request has been made");
        }
        Rt = Integer.valueOf(i);
    }

    private void co() {
        View.OnAttachStateChangeListener onAttachStateChangeListener = this.Nt;
        if (onAttachStateChangeListener != null && !this.Pt) {
            this.view.addOnAttachStateChangeListener(onAttachStateChangeListener);
            this.Pt = true;
        }
    }

    /* renamed from: do  reason: not valid java name */
    private void m0do() {
        View.OnAttachStateChangeListener onAttachStateChangeListener = this.Nt;
        if (onAttachStateChangeListener != null && this.Pt) {
            this.view.removeOnAttachStateChangeListener(onAttachStateChangeListener);
            this.Pt = false;
        }
    }

    @Nullable
    private Object getTag() {
        Integer num = Rt;
        return num == null ? this.view.getTag() : this.view.getTag(num.intValue());
    }

    private void setTag(@Nullable Object obj) {
        Integer num = Rt;
        if (num == null) {
            Qt = true;
            this.view.setTag(obj);
            return;
        }
        this.view.setTag(num.intValue(), obj);
    }

    @NonNull
    public final ViewTarget<T, Z> Dk() {
        if (this.Nt != null) {
            return this;
        }
        this.Nt = new q(this);
        co();
        return this;
    }

    /* access modifiers changed from: package-private */
    public void Ek() {
        c request = getRequest();
        if (request != null && !request.isCancelled() && !request.isPaused()) {
            this.Ot = true;
            request.pause();
            this.Ot = false;
        }
    }

    /* access modifiers changed from: package-private */
    public void Fk() {
        c request = getRequest();
        if (request != null && request.isPaused()) {
            request.begin();
        }
    }

    @NonNull
    public final ViewTarget<T, Z> Gk() {
        this.Mt.Ut = true;
        return this;
    }

    @Override // com.bumptech.glide.request.target.o
    @CallSuper
    public void a(@NonNull n nVar) {
        this.Mt.a(nVar);
    }

    @Override // com.bumptech.glide.request.target.b, com.bumptech.glide.request.target.o
    @CallSuper
    public void b(@Nullable Drawable drawable) {
        super.b(drawable);
        this.Mt.Ik();
        if (!this.Ot) {
            m0do();
        }
    }

    @Override // com.bumptech.glide.request.target.o
    @CallSuper
    public void b(@NonNull n nVar) {
        this.Mt.b(nVar);
    }

    @Override // com.bumptech.glide.request.target.b, com.bumptech.glide.request.target.o
    @CallSuper
    public void c(@Nullable Drawable drawable) {
        super.c(drawable);
        co();
    }

    @Override // com.bumptech.glide.request.target.b, com.bumptech.glide.request.target.o
    public void f(@Nullable c cVar) {
        setTag(cVar);
    }

    @Override // com.bumptech.glide.request.target.b, com.bumptech.glide.request.target.o
    @Nullable
    public c getRequest() {
        Object tag = getTag();
        if (tag == null) {
            return null;
        }
        if (tag instanceof c) {
            return (c) tag;
        }
        throw new IllegalArgumentException("You must not call setTag() on a view Glide is targeting");
    }

    @NonNull
    public T getView() {
        return this.view;
    }

    public String toString() {
        return "Target for: " + ((Object) this.view);
    }
}
