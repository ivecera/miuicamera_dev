package com.bumptech.glide.e;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.util.Log;
import com.bumptech.glide.load.c;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/* compiled from: ApplicationVersionSignature */
public final class a {
    private static final String TAG = "AppVersionSignature";
    private static final ConcurrentMap<String, c> fu = new ConcurrentHashMap();

    private a() {
    }

    @Nullable
    private static PackageInfo P(@NonNull Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e2) {
            Log.e(TAG, "Cannot resolve info for" + context.getPackageName(), e2);
            return null;
        }
    }

    @NonNull
    private static c Q(@NonNull Context context) {
        return new d(b(P(context)));
    }

    @NonNull
    private static String b(@Nullable PackageInfo packageInfo) {
        return packageInfo != null ? String.valueOf(packageInfo.versionCode) : UUID.randomUUID().toString();
    }

    @NonNull
    public static c obtain(@NonNull Context context) {
        String packageName = context.getPackageName();
        c cVar = fu.get(packageName);
        if (cVar != null) {
            return cVar;
        }
        c Q = Q(context);
        c putIfAbsent = fu.putIfAbsent(packageName, Q);
        return putIfAbsent == null ? Q : putIfAbsent;
    }

    @VisibleForTesting
    static void reset() {
        fu.clear();
    }
}
