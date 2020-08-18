package com.bumptech.glide.manager;

import android.support.annotation.NonNull;
import com.bumptech.glide.util.l;
import java.util.Collections;
import java.util.Set;
import java.util.WeakHashMap;

/* compiled from: ActivityFragmentLifecycle */
class a implements i {
    private boolean Rf;
    private final Set<j> ks = Collections.newSetFromMap(new WeakHashMap());
    private boolean ls;

    a() {
    }

    @Override // com.bumptech.glide.manager.i
    public void a(@NonNull j jVar) {
        this.ks.remove(jVar);
    }

    @Override // com.bumptech.glide.manager.i
    public void b(@NonNull j jVar) {
        this.ks.add(jVar);
        if (this.ls) {
            jVar.onDestroy();
        } else if (this.Rf) {
            jVar.onStart();
        } else {
            jVar.onStop();
        }
    }

    /* access modifiers changed from: package-private */
    public void onDestroy() {
        this.ls = true;
        for (j jVar : l.b(this.ks)) {
            jVar.onDestroy();
        }
    }

    /* access modifiers changed from: package-private */
    public void onStart() {
        this.Rf = true;
        for (j jVar : l.b(this.ks)) {
            jVar.onStart();
        }
    }

    /* access modifiers changed from: package-private */
    public void onStop() {
        this.Rf = false;
        for (j jVar : l.b(this.ks)) {
            jVar.onStop();
        }
    }
}
