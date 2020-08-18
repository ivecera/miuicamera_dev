package com.bumptech.glide;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.CheckResult;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RawRes;
import java.io.File;
import java.net.URL;

/* compiled from: ModelTypes */
interface g<T> {
    @CheckResult
    @NonNull
    T a(@Nullable Drawable drawable);

    @CheckResult
    @NonNull
    T a(@Nullable File file);

    @CheckResult
    @NonNull
    T a(@RawRes @DrawableRes @Nullable Integer num);

    @CheckResult
    @Deprecated
    T a(@Nullable URL url);

    @CheckResult
    @NonNull
    T c(@Nullable Uri uri);

    @CheckResult
    @NonNull
    T f(@Nullable byte[] bArr);

    @CheckResult
    @NonNull
    T g(@Nullable Bitmap bitmap);

    @CheckResult
    @NonNull
    T load(@Nullable Object obj);

    @CheckResult
    @NonNull
    T load(@Nullable String str);
}
