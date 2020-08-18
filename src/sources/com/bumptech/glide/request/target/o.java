package com.bumptech.glide.request.target;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.bumptech.glide.manager.j;
import com.bumptech.glide.request.a.f;
import com.bumptech.glide.request.c;

/* compiled from: Target */
public interface o<R> extends j {
    public static final int Jz = Integer.MIN_VALUE;

    void a(@NonNull n nVar);

    void a(@NonNull R r, @Nullable f<? super R> fVar);

    void b(@Nullable Drawable drawable);

    void b(@NonNull n nVar);

    void c(@Nullable Drawable drawable);

    void d(@Nullable Drawable drawable);

    void f(@Nullable c cVar);

    @Nullable
    c getRequest();
}
