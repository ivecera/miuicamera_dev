package com.android.camera.data.observeable;

public abstract class VMBase {
    /* access modifiers changed from: protected */
    public abstract boolean achieveEndOfCycle();

    /* access modifiers changed from: protected */
    public final void judge() {
        if (achieveEndOfCycle()) {
            rollbackData();
        }
    }

    /* access modifiers changed from: protected */
    public void onCleared() {
    }

    /* access modifiers changed from: protected */
    public abstract void rollbackData();
}
