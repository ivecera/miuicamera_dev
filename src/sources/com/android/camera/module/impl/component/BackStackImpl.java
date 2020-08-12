package com.android.camera.module.impl.component;

import com.android.camera.ActivityBase;
import com.android.camera.log.Log;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import java.util.Iterator;
import java.util.Stack;

public class BackStackImpl implements ModeProtocol.BackStack {
    private static final String TAG = "BackStack";
    private ActivityBase mActivity;
    private Stack<ModeProtocol.HandleBackTrace> mStacks = new Stack<>();

    public BackStackImpl(ActivityBase activityBase) {
        this.mActivity = activityBase;
    }

    public static BackStackImpl create(ActivityBase activityBase) {
        return new BackStackImpl(activityBase);
    }

    private final boolean handleBackStack(int i) {
        if (this.mStacks.isEmpty()) {
            return false;
        }
        Iterator<ModeProtocol.HandleBackTrace> it = this.mStacks.iterator();
        while (it.hasNext()) {
            ModeProtocol.HandleBackTrace next = it.next();
            if (next.canProvide() && next.onBackEvent(i)) {
                Log.d(TAG, "consume global backEvent " + i + " | " + next.getClass().getSimpleName());
                return true;
            }
        }
        return false;
    }

    @Override // com.android.camera.protocol.ModeProtocol.BackStack
    public <P extends ModeProtocol.HandleBackTrace> void addInBackStack(P p) {
        this.mStacks.add(p);
    }

    @Override // com.android.camera.protocol.ModeProtocol.BackStack
    public boolean handleBackStackFromKeyBack() {
        return handleBackStack(1);
    }

    @Override // com.android.camera.protocol.ModeProtocol.BackStack
    public void handleBackStackFromShutter() {
        if (!this.mStacks.isEmpty()) {
            Iterator<ModeProtocol.HandleBackTrace> it = this.mStacks.iterator();
            while (it.hasNext()) {
                ModeProtocol.HandleBackTrace next = it.next();
                if (next.canProvide()) {
                    next.onBackEvent(3);
                }
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.BackStack
    public boolean handleBackStackFromTapDown(int i, int i2) {
        if (!this.mActivity.getCameraScreenNail().getDisplayRect().contains(i, i2)) {
            return false;
        }
        return handleBackStack(2);
    }

    @Override // com.android.camera.protocol.ModeProtocol.BaseProtocol
    public void registerProtocol() {
        ModeCoordinatorImpl.getInstance().attachProtocol(171, this);
    }

    @Override // com.android.camera.protocol.ModeProtocol.BackStack
    public <P extends ModeProtocol.HandleBackTrace> void removeBackStack(P p) {
        this.mStacks.remove(p);
    }

    @Override // com.android.camera.protocol.ModeProtocol.BaseProtocol
    public void unRegisterProtocol() {
        this.mStacks.clear();
        this.mActivity = null;
        ModeCoordinatorImpl.getInstance().detachProtocol(171, this);
    }
}
