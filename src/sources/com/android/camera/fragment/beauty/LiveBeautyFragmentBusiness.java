package com.android.camera.fragment.beauty;

import android.support.v4.app.Fragment;
import java.util.ArrayList;
import java.util.List;

public class LiveBeautyFragmentBusiness implements IBeautyFragmentBusiness {
    List<Fragment> mFragments;

    @Override // com.android.camera.fragment.beauty.IBeautyFragmentBusiness
    public List<Fragment> getCurrentShowFragmentList() {
        List<Fragment> list = this.mFragments;
        if (list == null) {
            this.mFragments = new ArrayList();
        } else {
            list.clear();
        }
        this.mFragments.add(new LiveBeautyFilterFragment());
        this.mFragments.add(new LiveBeautyModeFragment());
        return this.mFragments;
    }

    @Override // com.android.camera.fragment.beauty.IBeautyBusiness
    public Object operate(Object obj) {
        return null;
    }
}
