package com.android.camera.fragment.lifeCircle;

import java.util.Collections;
import java.util.Set;
import java.util.WeakHashMap;

public class BaseFragmentLifecycle implements BaseLifeCycle {
    private boolean mIsDestroyed;
    private boolean mIsStarted;
    private final Set<BaseLifecycleListener> mLifecycleListeners = Collections.newSetFromMap(new WeakHashMap());

    @Override // com.android.camera.fragment.lifeCircle.BaseLifeCycle
    public void addListener(BaseLifecycleListener baseLifecycleListener, String str) {
        this.mLifecycleListeners.add(baseLifecycleListener);
        if (this.mIsDestroyed) {
            baseLifecycleListener.onLifeDestroy(str);
        } else if (this.mIsStarted) {
            baseLifecycleListener.onLifeStart(str);
        } else {
            baseLifecycleListener.onLifeStop(str);
        }
    }

    /* access modifiers changed from: package-private */
    public void onDestroy(String str) {
        this.mIsDestroyed = true;
        for (BaseLifecycleListener baseLifecycleListener : this.mLifecycleListeners) {
            baseLifecycleListener.onLifeDestroy(str);
        }
    }

    /* access modifiers changed from: package-private */
    public void onStart(String str) {
        this.mIsStarted = true;
        for (BaseLifecycleListener baseLifecycleListener : this.mLifecycleListeners) {
            baseLifecycleListener.onLifeStart(str);
        }
    }

    /* access modifiers changed from: package-private */
    public void onStop(String str) {
        this.mIsStarted = false;
        for (BaseLifecycleListener baseLifecycleListener : this.mLifecycleListeners) {
            baseLifecycleListener.onLifeStop(str);
        }
    }
}
