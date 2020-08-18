package com.android.camera.fragment.beauty;

import android.support.v4.app.Fragment;
import java.util.ArrayList;
import java.util.List;

public class BackMainFragmentBusiness extends AbBeautyFragmentBusiness {
    List<Fragment> mFragments;

    @Override // com.android.camera.fragment.beauty.IBeautyFragmentBusiness
    public List<Fragment> getCurrentShowFragmentList() {
        List<Fragment> list = this.mFragments;
        if (list == null) {
            this.mFragments = new ArrayList();
        } else {
            list.clear();
        }
        this.mFragments.add(new BeautyLevelFragment());
        this.mFragments.add(new BeautyBodyFragment());
        return this.mFragments;
    }
}
