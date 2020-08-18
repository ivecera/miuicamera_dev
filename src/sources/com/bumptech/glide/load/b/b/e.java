package com.bumptech.glide.load.b.b;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.bumptech.glide.load.engine.A;
import com.bumptech.glide.load.g;
import com.bumptech.glide.load.h;
import java.util.List;

/* compiled from: ResourceDrawableDecoder */
public class e implements h<Uri, Drawable> {
    private static final int Hr = 2;
    private static final int Ir = 0;
    private static final int Jr = 1;
    private static final int Kr = 1;
    private static final int Lr = 0;
    private final Context context;

    public e(Context context2) {
        this.context = context2.getApplicationContext();
    }

    @NonNull
    private Context a(Uri uri, String str) {
        try {
            return this.context.createPackageContext(str, 0);
        } catch (PackageManager.NameNotFoundException e2) {
            throw new IllegalArgumentException("Failed to obtain context or unrecognized Uri format for: " + uri, e2);
        }
    }

    @DrawableRes
    private int l(Uri uri) {
        Integer num;
        List<String> pathSegments = uri.getPathSegments();
        if (pathSegments.size() == 2) {
            String authority = uri.getAuthority();
            String str = pathSegments.get(1);
            num = Integer.valueOf(this.context.getResources().getIdentifier(str, pathSegments.get(0), authority));
        } else {
            if (pathSegments.size() == 1) {
                try {
                    num = Integer.valueOf(pathSegments.get(0));
                } catch (NumberFormatException unused) {
                }
            }
            num = null;
        }
        if (num == null) {
            throw new IllegalArgumentException("Unrecognized Uri format: " + uri);
        } else if (num.intValue() != 0) {
            return num.intValue();
        } else {
            throw new IllegalArgumentException("Failed to obtain resource id for: " + uri);
        }
    }

    public boolean a(@NonNull Uri uri, @NonNull g gVar) {
        return uri.getScheme().equals("android.resource");
    }

    @Nullable
    public A<Drawable> b(@NonNull Uri uri, int i, int i2, @NonNull g gVar) {
        int l = l(uri);
        String authority = uri.getAuthority();
        return d.e(a.a(this.context, authority.equals(this.context.getPackageName()) ? this.context : a(uri, authority), l));
    }
}
