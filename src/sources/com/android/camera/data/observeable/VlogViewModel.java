package com.android.camera.data.observeable;

import com.android.camera.fragment.vv.VVList;
import com.android.camera.module.impl.component.LiveSubVVImpl;
import com.android.camera.resource.SimpleNativeResourceInfoListRequest;
import io.reactivex.Observable;

public class VlogViewModel extends VMBase {
    private VVList mVVList;

    /* access modifiers changed from: protected */
    @Override // com.android.camera.data.observeable.VMBase
    public boolean achieveEndOfCycle() {
        return false;
    }

    public /* synthetic */ VVList b(VVList vVList) throws Exception {
        this.mVVList = vVList;
        return this.mVVList;
    }

    public VVList getVVList() {
        return this.mVVList;
    }

    public Observable<VVList> getVVListObservable() {
        return new SimpleNativeResourceInfoListRequest("vv/info.json", LiveSubVVImpl.TEMPLATE_PATH).startObservable(VVList.class).map(new a(this));
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.data.observeable.VMBase
    public void rollbackData() {
        this.mVVList = null;
    }
}
