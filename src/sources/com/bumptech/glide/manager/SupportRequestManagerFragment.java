package com.bumptech.glide.manager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import com.bumptech.glide.c;
import com.bumptech.glide.m;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class SupportRequestManagerFragment extends Fragment {
    private static final String TAG = "SupportRMFragment";
    @Nullable
    private SupportRequestManagerFragment Aa;
    @Nullable
    private Fragment Ba;
    private final a wa;
    private final o xa;
    private final Set<SupportRequestManagerFragment> ya;
    @Nullable
    private m za;

    private class a implements o {
        a() {
        }

        @Override // com.bumptech.glide.manager.o
        @NonNull
        public Set<m> ba() {
            Set<SupportRequestManagerFragment> ka = SupportRequestManagerFragment.this.ka();
            HashSet hashSet = new HashSet(ka.size());
            for (SupportRequestManagerFragment supportRequestManagerFragment : ka) {
                if (supportRequestManagerFragment.ma() != null) {
                    hashSet.add(supportRequestManagerFragment.ma());
                }
            }
            return hashSet;
        }

        public String toString() {
            return super.toString() + "{fragment=" + SupportRequestManagerFragment.this + "}";
        }
    }

    public SupportRequestManagerFragment() {
        this(new a());
    }

    @VisibleForTesting
    @SuppressLint({"ValidFragment"})
    public SupportRequestManagerFragment(@NonNull a aVar) {
        this.xa = new a();
        this.ya = new HashSet();
        this.wa = aVar;
    }

    @Nullable
    private Fragment Fm() {
        Fragment parentFragment = getParentFragment();
        return parentFragment != null ? parentFragment : this.Ba;
    }

    private void Gm() {
        SupportRequestManagerFragment supportRequestManagerFragment = this.Aa;
        if (supportRequestManagerFragment != null) {
            supportRequestManagerFragment.b(this);
            this.Aa = null;
        }
    }

    private void a(SupportRequestManagerFragment supportRequestManagerFragment) {
        this.ya.add(supportRequestManagerFragment);
    }

    private void b(SupportRequestManagerFragment supportRequestManagerFragment) {
        this.ya.remove(supportRequestManagerFragment);
    }

    private void d(@NonNull FragmentActivity fragmentActivity) {
        Gm();
        this.Aa = c.get(fragmentActivity).Ii().c(fragmentActivity);
        if (!equals(this.Aa)) {
            this.Aa.a(this);
        }
    }

    private boolean d(@NonNull Fragment fragment) {
        Fragment Fm = Fm();
        while (true) {
            Fragment parentFragment = fragment.getParentFragment();
            if (parentFragment == null) {
                return false;
            }
            if (parentFragment.equals(Fm)) {
                return true;
            }
            fragment = fragment.getParentFragment();
        }
    }

    /* access modifiers changed from: package-private */
    public void a(@Nullable Fragment fragment) {
        this.Ba = fragment;
        if (fragment != null && fragment.getActivity() != null) {
            d(fragment.getActivity());
        }
    }

    public void a(@Nullable m mVar) {
        this.za = mVar;
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public Set<SupportRequestManagerFragment> ka() {
        SupportRequestManagerFragment supportRequestManagerFragment = this.Aa;
        if (supportRequestManagerFragment == null) {
            return Collections.emptySet();
        }
        if (equals(supportRequestManagerFragment)) {
            return Collections.unmodifiableSet(this.ya);
        }
        HashSet hashSet = new HashSet();
        for (SupportRequestManagerFragment supportRequestManagerFragment2 : this.Aa.ka()) {
            if (d(supportRequestManagerFragment2.Fm())) {
                hashSet.add(supportRequestManagerFragment2);
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

    @Override // android.support.v4.app.Fragment
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            d(getActivity());
        } catch (IllegalStateException e2) {
            if (Log.isLoggable(TAG, 5)) {
                Log.w(TAG, "Unable to register fragment with root", e2);
            }
        }
    }

    @Override // android.support.v4.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        this.wa.onDestroy();
        Gm();
    }

    @Override // android.support.v4.app.Fragment
    public void onDetach() {
        super.onDetach();
        this.Ba = null;
        Gm();
    }

    @Override // android.support.v4.app.Fragment
    public void onStart() {
        super.onStart();
        this.wa.onStart();
    }

    @Override // android.support.v4.app.Fragment
    public void onStop() {
        super.onStop();
        this.wa.onStop();
    }

    @Override // android.support.v4.app.Fragment
    public String toString() {
        return super.toString() + "{parent=" + Fm() + "}";
    }
}
