package com.android.camera.fragment;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.v4.app.Fragment;
import android.view.View;

public abstract class BaseViewPagerFragment extends Fragment {
    private boolean isOnCreate;
    private boolean isViewCreated;
    private boolean isViewCreatedAndVisibleToUser;
    private boolean isVisibleToUser;

    private void beforeViewGoneToUser() {
        this.isViewCreatedAndVisibleToUser = false;
    }

    private void beforeViewVisibleToUser() {
        this.isViewCreatedAndVisibleToUser = true;
    }

    @Override // android.support.v4.app.Fragment
    @CallSuper
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.isOnCreate = true;
    }

    @Override // android.support.v4.app.Fragment
    @CallSuper
    public void onDestroyView() {
        super.onDestroyView();
        this.isViewCreated = false;
        if (this.isViewCreatedAndVisibleToUser) {
            beforeViewGoneToUser();
            onViewCreatedAndJumpOut();
        }
    }

    @Override // android.support.v4.app.Fragment
    @CallSuper
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        this.isViewCreated = true;
        if (!this.isVisibleToUser) {
            return;
        }
        if (this.isOnCreate) {
            this.isOnCreate = false;
            beforeViewVisibleToUser();
            onViewCreatedAndVisibleToUser(true);
            return;
        }
        beforeViewVisibleToUser();
        onViewCreatedAndVisibleToUser(false);
    }

    /* access modifiers changed from: protected */
    @CallSuper
    public void onViewCreatedAndJumpOut() {
    }

    /* access modifiers changed from: protected */
    @CallSuper
    public void onViewCreatedAndVisibleToUser(boolean z) {
    }

    @Override // android.support.v4.app.Fragment
    @CallSuper
    public void setUserVisibleHint(boolean z) {
        this.isVisibleToUser = z;
        super.setUserVisibleHint(z);
        if (this.isViewCreated && z) {
            if (this.isOnCreate) {
                this.isOnCreate = false;
                beforeViewVisibleToUser();
                onViewCreatedAndVisibleToUser(true);
            } else {
                beforeViewVisibleToUser();
                onViewCreatedAndVisibleToUser(false);
            }
        }
        if (!z && this.isViewCreatedAndVisibleToUser) {
            beforeViewGoneToUser();
            onViewCreatedAndJumpOut();
        }
    }
}
