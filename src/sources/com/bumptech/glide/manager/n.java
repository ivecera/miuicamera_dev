package com.bumptech.glide.manager;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.app.FragmentManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.util.ArrayMap;
import android.util.Log;
import android.view.View;
import com.bumptech.glide.c;
import com.bumptech.glide.m;
import com.bumptech.glide.util.i;
import com.bumptech.glide.util.l;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/* compiled from: RequestManagerRetriever */
public class n implements Handler.Callback {
    private static final a El = new m();
    @VisibleForTesting
    static final String FRAGMENT_TAG = "com.bumptech.glide.manager";
    private static final String TAG = "RMRetriever";
    private static final int us = 1;
    private static final int vs = 2;
    private static final String xs = "key";
    private final a factory;
    private final Handler handler;
    private volatile m os;
    @VisibleForTesting
    final Map<FragmentManager, l> pendingRequestManagerFragments = new HashMap();
    @VisibleForTesting
    final Map<android.support.v4.app.FragmentManager, SupportRequestManagerFragment> pendingSupportRequestManagerFragments = new HashMap();
    private final ArrayMap<View, Fragment> ps = new ArrayMap<>();
    private final ArrayMap<View, android.app.Fragment> rs = new ArrayMap<>();
    private final Bundle ss = new Bundle();

    /* compiled from: RequestManagerRetriever */
    public interface a {
        @NonNull
        m a(@NonNull c cVar, @NonNull i iVar, @NonNull o oVar, @NonNull Context context);
    }

    public n(@Nullable a aVar) {
        this.factory = aVar == null ? El : aVar;
        this.handler = new Handler(Looper.getMainLooper(), this);
    }

    @Nullable
    private Activity M(@NonNull Context context) {
        if (context instanceof Activity) {
            return (Activity) context;
        }
        if (context instanceof ContextWrapper) {
            return M(((ContextWrapper) context).getBaseContext());
        }
        return null;
    }

    @NonNull
    private m N(@NonNull Context context) {
        if (this.os == null) {
            synchronized (this) {
                if (this.os == null) {
                    this.os = this.factory.a(c.get(context.getApplicationContext()), new b(), new h(), context.getApplicationContext());
                }
            }
        }
        return this.os;
    }

    @Nullable
    @Deprecated
    private android.app.Fragment a(@NonNull View view, @NonNull Activity activity) {
        this.rs.clear();
        a(activity.getFragmentManager(), this.rs);
        View findViewById = activity.findViewById(16908290);
        android.app.Fragment fragment = null;
        while (!view.equals(findViewById) && (fragment = this.rs.get(view)) == null && (view.getParent() instanceof View)) {
            view = (View) view.getParent();
        }
        this.rs.clear();
        return fragment;
    }

    @Nullable
    private Fragment a(@NonNull View view, @NonNull FragmentActivity fragmentActivity) {
        this.ps.clear();
        a(fragmentActivity.getSupportFragmentManager().getFragments(), this.ps);
        View findViewById = fragmentActivity.findViewById(16908290);
        Fragment fragment = null;
        while (!view.equals(findViewById) && (fragment = this.ps.get(view)) == null && (view.getParent() instanceof View)) {
            view = (View) view.getParent();
        }
        this.ps.clear();
        return fragment;
    }

    @NonNull
    @Deprecated
    private m a(@NonNull Context context, @NonNull FragmentManager fragmentManager, @Nullable android.app.Fragment fragment, boolean z) {
        l a2 = a(fragmentManager, fragment, z);
        m ma = a2.ma();
        if (ma != null) {
            return ma;
        }
        m a3 = this.factory.a(c.get(context), a2.la(), a2.na(), context);
        a2.a(a3);
        return a3;
    }

    @NonNull
    private m a(@NonNull Context context, @NonNull android.support.v4.app.FragmentManager fragmentManager, @Nullable Fragment fragment, boolean z) {
        SupportRequestManagerFragment a2 = a(fragmentManager, fragment, z);
        m ma = a2.ma();
        if (ma != null) {
            return ma;
        }
        m a3 = this.factory.a(c.get(context), a2.la(), a2.na(), context);
        a2.a(a3);
        return a3;
    }

    @NonNull
    private SupportRequestManagerFragment a(@NonNull android.support.v4.app.FragmentManager fragmentManager, @Nullable Fragment fragment, boolean z) {
        SupportRequestManagerFragment supportRequestManagerFragment = (SupportRequestManagerFragment) fragmentManager.findFragmentByTag(FRAGMENT_TAG);
        if (supportRequestManagerFragment == null && (supportRequestManagerFragment = this.pendingSupportRequestManagerFragments.get(fragmentManager)) == null) {
            supportRequestManagerFragment = new SupportRequestManagerFragment();
            supportRequestManagerFragment.a(fragment);
            if (z) {
                supportRequestManagerFragment.la().onStart();
            }
            this.pendingSupportRequestManagerFragments.put(fragmentManager, supportRequestManagerFragment);
            fragmentManager.beginTransaction().add(supportRequestManagerFragment, FRAGMENT_TAG).commitAllowingStateLoss();
            this.handler.obtainMessage(2, fragmentManager).sendToTarget();
        }
        return supportRequestManagerFragment;
    }

    @NonNull
    private l a(@NonNull FragmentManager fragmentManager, @Nullable android.app.Fragment fragment, boolean z) {
        l lVar = (l) fragmentManager.findFragmentByTag(FRAGMENT_TAG);
        if (lVar == null && (lVar = this.pendingRequestManagerFragments.get(fragmentManager)) == null) {
            lVar = new l();
            lVar.a(fragment);
            if (z) {
                lVar.la().onStart();
            }
            this.pendingRequestManagerFragments.put(fragmentManager, lVar);
            fragmentManager.beginTransaction().add(lVar, FRAGMENT_TAG).commitAllowingStateLoss();
            this.handler.obtainMessage(1, fragmentManager).sendToTarget();
        }
        return lVar;
    }

    @TargetApi(26)
    @Deprecated
    private void a(@NonNull FragmentManager fragmentManager, @NonNull ArrayMap<View, android.app.Fragment> arrayMap) {
        if (Build.VERSION.SDK_INT >= 26) {
            for (android.app.Fragment fragment : fragmentManager.getFragments()) {
                if (fragment.getView() != null) {
                    arrayMap.put(fragment.getView(), fragment);
                    a(fragment.getChildFragmentManager(), arrayMap);
                }
            }
            return;
        }
        b(fragmentManager, arrayMap);
    }

    private static void a(@Nullable Collection<Fragment> collection, @NonNull Map<View, Fragment> map) {
        if (collection != null) {
            for (Fragment fragment : collection) {
                if (!(fragment == null || fragment.getView() == null)) {
                    map.put(fragment.getView(), fragment);
                    a(fragment.getChildFragmentManager().getFragments(), map);
                }
            }
        }
    }

    @Deprecated
    private void b(@NonNull FragmentManager fragmentManager, @NonNull ArrayMap<View, android.app.Fragment> arrayMap) {
        int i = 0;
        while (true) {
            int i2 = i + 1;
            this.ss.putInt("key", i);
            android.app.Fragment fragment = null;
            try {
                fragment = fragmentManager.getFragment(this.ss, "key");
            } catch (Exception unused) {
            }
            if (fragment != null) {
                if (fragment.getView() != null) {
                    arrayMap.put(fragment.getView(), fragment);
                    if (Build.VERSION.SDK_INT >= 17) {
                        a(fragment.getChildFragmentManager(), arrayMap);
                    }
                }
                i = i2;
            } else {
                return;
            }
        }
    }

    @TargetApi(17)
    private static void d(@NonNull Activity activity) {
        if (Build.VERSION.SDK_INT >= 17 && activity.isDestroyed()) {
            throw new IllegalArgumentException("You cannot start a load for a destroyed activity");
        }
    }

    private static boolean e(Activity activity) {
        return !activity.isFinishing();
    }

    @NonNull
    public m b(@NonNull FragmentActivity fragmentActivity) {
        if (l.Mk()) {
            return get(fragmentActivity.getApplicationContext());
        }
        d(fragmentActivity);
        return a(fragmentActivity, fragmentActivity.getSupportFragmentManager(), (Fragment) null, e(fragmentActivity));
    }

    /* access modifiers changed from: package-private */
    @NonNull
    @Deprecated
    public l b(Activity activity) {
        return a(activity.getFragmentManager(), (android.app.Fragment) null, e(activity));
    }

    @NonNull
    @TargetApi(17)
    @Deprecated
    public m c(@NonNull android.app.Fragment fragment) {
        if (fragment.getActivity() == null) {
            throw new IllegalArgumentException("You cannot start a load on a fragment before it is attached");
        } else if (l.Mk() || Build.VERSION.SDK_INT < 17) {
            return get(fragment.getActivity().getApplicationContext());
        } else {
            return a(fragment.getActivity(), fragment.getChildFragmentManager(), fragment, fragment.isVisible());
        }
    }

    @NonNull
    public m c(@NonNull Fragment fragment) {
        i.b(fragment.getActivity(), "You cannot start a load on a fragment before it is attached or after it is destroyed");
        if (l.Mk()) {
            return get(fragment.getActivity().getApplicationContext());
        }
        return a(fragment.getActivity(), fragment.getChildFragmentManager(), fragment, fragment.isVisible());
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public SupportRequestManagerFragment c(FragmentActivity fragmentActivity) {
        return a(fragmentActivity.getSupportFragmentManager(), (Fragment) null, e(fragmentActivity));
    }

    @NonNull
    public m get(@NonNull Activity activity) {
        if (l.Mk()) {
            return get(activity.getApplicationContext());
        }
        d(activity);
        return a(activity, activity.getFragmentManager(), (android.app.Fragment) null, e(activity));
    }

    @NonNull
    public m get(@NonNull Context context) {
        if (context != null) {
            if (l.Nk() && !(context instanceof Application)) {
                if (context instanceof FragmentActivity) {
                    return b((FragmentActivity) context);
                }
                if (context instanceof Activity) {
                    return get((Activity) context);
                }
                if (context instanceof ContextWrapper) {
                    return get(((ContextWrapper) context).getBaseContext());
                }
            }
            return N(context);
        }
        throw new IllegalArgumentException("You cannot start a load on a null Context");
    }

    @NonNull
    public m get(@NonNull View view) {
        if (l.Mk()) {
            return get(view.getContext().getApplicationContext());
        }
        i.checkNotNull(view);
        i.b(view.getContext(), "Unable to obtain a request manager for a view without a Context");
        Activity M = M(view.getContext());
        if (M == null) {
            return get(view.getContext().getApplicationContext());
        }
        if (M instanceof FragmentActivity) {
            Fragment a2 = a(view, (FragmentActivity) M);
            return a2 != null ? c(a2) : get(M);
        }
        android.app.Fragment a3 = a(view, M);
        return a3 == null ? get(M) : c(a3);
    }

    public boolean handleMessage(Message message) {
        Object obj;
        int i = message.what;
        Object obj2 = null;
        boolean z = true;
        if (i == 1) {
            obj2 = (FragmentManager) message.obj;
            obj = this.pendingRequestManagerFragments.remove(obj2);
        } else if (i != 2) {
            z = false;
            obj = null;
        } else {
            obj2 = (android.support.v4.app.FragmentManager) message.obj;
            obj = this.pendingSupportRequestManagerFragments.remove(obj2);
        }
        if (z && obj == null && Log.isLoggable(TAG, 5)) {
            Log.w(TAG, "Failed to remove expected request manager fragment, manager: " + obj2);
        }
        return z;
    }
}
