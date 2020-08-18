package com.android.camera.module.loader;

import android.support.annotation.IdRes;
import com.android.camera.fragment.BaseFragmentOperation;
import com.android.camera.module.interceptor.BaseModuleInterceptor;
import java.util.ArrayList;
import java.util.List;

public class StartControlFeatureDetail {
    private List<BaseFragmentOperation> fragmentAlias;
    private List<BaseModuleInterceptor> interceptorList;
    private int[] topConfigItems;

    public void addFragmentInfo(@IdRes int i, String str) {
        if (this.fragmentAlias == null) {
            this.fragmentAlias = new ArrayList();
        }
        this.fragmentAlias.add(new BaseFragmentOperation(i).featureWith(str));
    }

    public void addInterceptor(BaseModuleInterceptor baseModuleInterceptor) {
        if (this.interceptorList == null) {
            this.interceptorList = new ArrayList();
        }
        this.interceptorList.add(baseModuleInterceptor);
    }

    public List<BaseFragmentOperation> getFragmentAlias() {
        return this.fragmentAlias;
    }

    public List<BaseModuleInterceptor> getInterceptorList() {
        return this.interceptorList;
    }

    public int[] getTopConfigItems() {
        return this.topConfigItems;
    }

    public void setTopConfigItems(int... iArr) {
        this.topConfigItems = iArr;
    }
}
