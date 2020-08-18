package com.bumptech.glide.manager;

import android.support.annotation.NonNull;
import com.bumptech.glide.request.target.o;
import com.bumptech.glide.util.l;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.WeakHashMap;

/* compiled from: TargetTracker */
public final class q implements j {
    private final Set<o<?>> targets = Collections.newSetFromMap(new WeakHashMap());

    public void clear() {
        this.targets.clear();
    }

    public void e(@NonNull o<?> oVar) {
        this.targets.remove(oVar);
    }

    public void f(@NonNull o<?> oVar) {
        this.targets.add(oVar);
    }

    @NonNull
    public List<o<?>> getAll() {
        return l.b(this.targets);
    }

    @Override // com.bumptech.glide.manager.j
    public void onDestroy() {
        for (o oVar : l.b(this.targets)) {
            oVar.onDestroy();
        }
    }

    @Override // com.bumptech.glide.manager.j
    public void onStart() {
        for (o oVar : l.b(this.targets)) {
            oVar.onStart();
        }
    }

    @Override // com.bumptech.glide.manager.j
    public void onStop() {
        for (o oVar : l.b(this.targets)) {
            oVar.onStop();
        }
    }
}
