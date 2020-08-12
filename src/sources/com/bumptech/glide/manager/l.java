package com.bumptech.glide.manager;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.util.Log;
import com.bumptech.glide.c;
import com.bumptech.glide.m;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Deprecated
/* compiled from: RequestManagerFragment */
public class l extends Fragment {
    private static final String TAG = "RMFragment";
    @Nullable
    private l Aa;
    @Nullable
    private Fragment Ba;
    private final a wa;
    private final o xa;
    private final Set<l> ya;
    @Nullable
    private m za;

    /* compiled from: RequestManagerFragment */
    private class a implements o {
        a() {
        }

        @Override // com.bumptech.glide.manager.o
        @NonNull
        public Set<m> ba() {
            Set<l> ka = l.this.ka();
            HashSet hashSet = new HashSet(ka.size());
            for (l lVar : ka) {
                if (lVar.ma() != null) {
                    hashSet.add(lVar.ma());
                }
            }
            return hashSet;
        }

        public String toString() {
            return super.toString() + "{fragment=" + l.this + "}";
        }
    }

    public l() {
        this(new a());
    }

    @VisibleForTesting
    @SuppressLint({"ValidFragment"})
    l(@NonNull a aVar) {
        this.xa = new a();
        this.ya = new HashSet();
        this.wa = aVar;
    }

    @TargetApi(17)
    @Nullable
    private Fragment Fm() {
        Fragment parentFragment = Build.VERSION.SDK_INT >= 17 ? getParentFragment() : null;
        return parentFragment != null ? parentFragment : this.Ba;
    }

    private void Gm() {
        l lVar = this.Aa;
        if (lVar != null) {
            lVar.b(this);
            this.Aa = null;
        }
    }

    private void a(l lVar) {
        this.ya.add(lVar);
    }

    private void b(l lVar) {
        this.ya.remove(lVar);
    }

    private void c(@NonNull Activity activity) {
        Gm();
        this.Aa = c.get(activity).Ii().b(activity);
        if (!equals(this.Aa)) {
            this.Aa.a(this);
        }
    }

    @TargetApi(17)
    private boolean d(@NonNull Fragment fragment) {
        Fragment parentFragment = getParentFragment();
        while (true) {
            Fragment parentFragment2 = fragment.getParentFragment();
            if (parentFragment2 == null) {
                return false;
            }
            if (parentFragment2.equals(parentFragment)) {
                return true;
            }
            fragment = fragment.getParentFragment();
        }
    }

    /* access modifiers changed from: package-private */
    public void a(@Nullable Fragment fragment) {
        this.Ba = fragment;
        if (fragment != null && fragment.getActivity() != null) {
            c(fragment.getActivity());
        }
    }

    public void a(@Nullable m mVar) {
        this.za = mVar;
    }

    /* access modifiers changed from: package-private */
    @NonNull
    @TargetApi(17)
    public Set<l> ka() {
        if (equals(this.Aa)) {
            return Collections.unmodifiableSet(this.ya);
        }
        if (this.Aa == null || Build.VERSION.SDK_INT < 17) {
            return Collections.emptySet();
        }
        HashSet hashSet = new HashSet();
        for (l lVar : this.Aa.ka()) {
            if (d(lVar.getParentFragment())) {
                hashSet.add(lVar);
            }
        }
        return Collections.unmodifiableSet(hashSet);
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public a la() {
        return this.wa;
    }

    @Nullable
    public m ma() {
        return this.za;
    }

    @NonNull
    public o na() {
        return this.xa;
    }

    @Override // android.app.Fragment
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            c(activity);
        } catch (IllegalStateException e2) {
            if (Log.isLoggable(TAG, 5)) {
                Log.w(TAG, "Unable to register fragment with root", e2);
            }
        }
    }

    public void onDestroy() {
        super.onDestroy();
        this.wa.onDestroy();
        Gm();
    }

    public void onDetach() {
        super.onDetach();
        Gm();
    }

    public void onStart() {
        super.onStart();
        this.wa.onStart();
    }

    public void onStop() {
        super.onStop();
        this.wa.onStop();
    }

    public String toString() {
        return super.toString() + "{parent=" + Fm() + "}";
    }
}
