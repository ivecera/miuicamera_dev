package com.android.camera.fragment.beauty;

import android.support.v4.app.Fragment;
import com.android.camera.CameraSettings;
import com.android.camera.data.DataRepository;
import com.mi.config.b;
import java.util.ArrayList;
import java.util.List;

@Deprecated
public class FrontFragmentBusiness extends AbBeautyFragmentBusiness {
    List<Fragment> mFragments;

    @Override // com.android.camera.fragment.beauty.IBeautyFragmentBusiness
    public List<Fragment> getCurrentShowFragmentList() {
        List<Fragment> list = this.mFragments;
        if (list == null) {
            this.mFragments = new ArrayList();
        } else {
            list.clear();
        }
        if (b.Il()) {
            this.mFragments.add(new BeautyLevelFragment());
        } else {
            this.mFragments.add(new BeautyLevelFragment());
            this.mFragments.add(new MakeupParamsFragment());
        }
        if (DataRepository.dataItemFeature().s_b_m() && CameraSettings.isSupportBeautyMakeup()) {
            this.mFragments.add(new MakeupBeautyFragment());
        }
        return this.mFragments;
    }
}
